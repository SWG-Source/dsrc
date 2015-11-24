package script.poi.deliverance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.scenario;
import script.library.ai_lib;
import script.library.utils;
import script.library.locations;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "deliverance";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_MASTER = BASE_PATH + ".master";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final int BASE_LAG_TIME = 20;
    public static final int DEFAULT_ANTAGONIST_COUNT = 2;
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final int NOT_STARTED = 0;
    public static final float LOAD_RANGE_MIN = 64f;
    public static final float LOAD_RANGE_MAX = 80f;
    public static final float SPAWN_RANGE = 80f;
    public static final float CORRAL_RADIUS = 16f;
    public static final float OBJ_RADIUS = 2f;
    public static final float LOC_TARGET_RADIUS = 7.5f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("poiDeliverance", "************ POI LAUNCH: " + getGameTime() + " ************");
        dictionary params = scenario.getRandomScenario(TBL);
        if (params == null)
        {
            messageTo(self, scenario.HANDLER_DESTROY_SELF, null, 3, true);
            return SCRIPT_CONTINUE;
        }
        String name = params.getString(scenario.COL_NAME);
        LOG("poiDeliverance", "**** SCENARIO: " + name + " ***");
        int idx = params.getInt(scenario.DICT_IDX);
        String convo = params.getString(scenario.COL_CONVO);
        if (convo.equals(""))
        {
            messageTo(self, scenario.HANDLER_DESTROY_SELF, null, 3, true);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, scenario.VAR_SCENARIO_IDX, idx);
        setObjVar(self, scenario.VAR_SCENARIO_CONVO, convo);
        messageTo(self, scenario.HANDLER_INIT_SCENARIO, params, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        scenario.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        scenario.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int initScenario(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "initScenario: entered...");
        if (params == null)
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        String type = params.getString(scenario.COL_MEDIATOR);
        String myName = "mediator_0";
        location loc = getLocation(self);
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, myName, loc);
        if (mediator == null)
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        attachScript(mediator, SCRIPT_MEDIATOR);
        setObjVar(mediator, scenario.VAR_MY_NAME, myName);
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int runScenario(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "runScenario: entered...");
        if (hasObjVar(self, scenario.VAR_SCENARIO_IDX))
        {
            int idx = getIntObjVar(self, scenario.VAR_SCENARIO_IDX);
            if (dataTableOpen(TBL))
            {
                LOG("poiDeliverance", "runScenario: retrieving scenario parameters...");
                params = dataTableGetRow(TBL, idx);
            }
            else 
            {
                LOG("poiDeliverance", "runScenario: unable to open datatable!");
            }
        }
        if (params == null)
        {
            LOG("poiDeliverance", "runScenario: unable to retrieve scenario!");
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        int diff = poiGetIntDifficulty();
        int aCount = DEFAULT_ANTAGONIST_COUNT;
        switch (diff)
        {
            case poi.DIFF_EASY:
            aCount += 2;
            break;
            case poi.DIFF_MEDIUM:
            aCount += 3;
            break;
            case poi.DIFF_HARD:
            aCount += 5;
            break;
            case poi.DIFF_VHARD:
            aCount += 6;
            break;
        }
        location myLoc = getLocation(self);
        location tmpLoc = myLoc;
        location c = utils.getRandomLocationInRing(tmpLoc, LOAD_RANGE_MIN, LOAD_RANGE_MAX);
        LOG("poiDeliverance", "antagonist center loc = " + c.toString());
        location loc = locations.getGoodLocationAroundLocation(c, 3f, 3f, 16f, 16f);
        if (loc == null)
        {
            LOG("poiDeliverance", "Unable to get good location!");
            messageTo(self, scenario.HANDLER_CLEANUP_SCENARIO, null, 10, true);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            LOG("poiDeliverance", "good loc = " + loc.toString());
        }
        String type = params.getString(scenario.COL_ANTAGONIST);
        Vector antagonists = new Vector();
        antagonists.setSize(0);
        for (int i = 0; i < aCount; i++)
        {
            location spawnLoc = new location();
            switch (i / 3)
            {
                case 0:
                spawnLoc.z = loc.z + 3f;
                break;
                case 1:
                spawnLoc.z = loc.z;
                break;
                case 2:
                spawnLoc.z = loc.z - 3f;
                break;
            }
            switch (i % 3)
            {
                case 0:
                spawnLoc.x = loc.x + 3f;
                break;
                case 1:
                spawnLoc.x = loc.x;
                break;
                case 2:
                spawnLoc.x = loc.x - 3f;
                break;
            }
            spawnLoc.y = getHeightAtLocation(spawnLoc.x, spawnLoc.y);
            LOG("poiDeliverance", "[" + i + "] antagonist spawn loc = " + spawnLoc.x + ", " + spawnLoc.z);
            float dx = loc.x - myLoc.x;
            float dz = loc.z - myLoc.z;
            String myName = "antagonist_" + i;
            LOG("poiDeliverance", "[" + i + "] antagonist: " + myName + " -> " + type);
            obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, myName, spawnLoc);
            if (antagonist != null)
            {
                attachScript(antagonist, SCRIPT_ANTAGONIST);
                setObjVar(antagonist, scenario.VAR_MY_NAME, myName);
                antagonists = utils.addElement(antagonists, antagonist);
            }
        }
        if ((antagonists == null) || (antagonists.size() == 0))
        {
            LOG("poiDeliverance", "Unable to load antagonists... aborting poi in 10 seconds");
            messageTo(self, scenario.HANDLER_CLEANUP_SCENARIO, null, 10, true);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            params.put("antagonists", antagonists);
            messageTo(self, "startPathing", params, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int startPathing(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "********* handler = startPathing *************");
        if (params == null)
        {
            LOG("poiDeliverance", "runScenario: unable to retrieve scenario!");
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        obj_id[] antagonists = params.getObjIdArray("antagonists");
        if ((antagonists == null) || (antagonists.length == 0))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        obj_id leader = antagonists[0];
        obj_id mediator = poiFindObject(scenario.MEDIATOR);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
        }
        else 
        {
            faceTo(mediator, leader);
        }
        location mLoc = getLocation(mediator);
        if (mLoc == null)
        {
            return SCRIPT_CONTINUE;
        }
        location pathLoc = utils.getRandomLocationInRing(mLoc, 2f, 5f);
        String locName = self.toString() + "_" + scenario.ANTAGONIST;
        scenario.requestAddTargetLocation(leader, locName, mLoc, LOC_TARGET_RADIUS);
        setObjVar(leader, scenario.VAR_TARGET_LOCATION_NAME, locName);
        LOG("poiDeliverance", "(" + mediator + ") mediator loc = " + mLoc.toString());
        LOG("poiDeliverance", "(" + antagonists[0] + ") leader pathing to: " + pathLoc.toString());
        ai_lib.aiPathTo(leader, pathLoc);
        LOG("poiDeliverance", "setting minion follow...");
        int formation = rand(0, 1);
        for (int i = 1; i < antagonists.length; i++)
        {
            LOG("poiDeliverance", "(" + antagonists[i] + ") setting follow for position " + i);
            ai_lib.followInFormation(antagonists[i], leader, formation, i);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanupScenario(obj_id self, dictionary params) throws InterruptedException
    {
        scenario.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleActorDeath(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiDeliverance", "handleActorDeath: entered...");
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id actor = params.getObjId(scenario.DICT_OBJID);
        String name = params.getString(scenario.DICT_NAME);
        Vector dead = getResizeableStringArrayObjVar(self, scenario.VAR_SCENARIO_DEAD);
        dead = utils.addElement(dead, name);
        setObjVar(self, scenario.VAR_SCENARIO_DEAD, dead);
        return SCRIPT_CONTINUE;
    }
}
