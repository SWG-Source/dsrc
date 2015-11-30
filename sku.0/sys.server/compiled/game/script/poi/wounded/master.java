package script.poi.wounded;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.scenario;
import script.library.locations;
import script.library.utils;
import script.library.ai_lib;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "wounded";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_MASTER = BASE_PATH + ".master";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String SCRIPT_LEADER_INV = BASE_PATH + ".leader_inv";
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_THEATER_COMPLETE = 1;
    public static final int STATE_INIT_ENTER = 2;
    public static final int STATE_INIT_COMPLETE = 3;
    public static final int STATE_RUN_ENTER = 4;
    public static final int STATE_RUN_COMPLETE = 5;
    public static final int STATE_SCENARIO_COMPLETE = 6;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("poiWounded", "************ POI(" + SCENARIO_NAME + ") LAUNCH: " + getGameTime() + " ************");
        dictionary params = scenario.getRandomScenario(TBL);
        if (params == null)
        {
            messageTo(self, scenario.HANDLER_DESTROY_SELF, null, 3, false);
            return SCRIPT_CONTINUE;
        }
        String name = params.getString(scenario.COL_NAME);
        LOG("poiWounded", "**** SCENARIO: " + name + " ***");
        int idx = params.getInt(scenario.DICT_IDX);
        String convo = params.getString(scenario.COL_CONVO);
        if (convo.equals(""))
        {
            messageTo(self, scenario.HANDLER_DESTROY_SELF, null, 3, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, scenario.VAR_SCENARIO_IDX, idx);
        setObjVar(self, scenario.VAR_SCENARIO_NAME, SCENARIO_NAME);
        setObjVar(self, scenario.VAR_SCENARIO_CONVO, convo);
        String theaterTemplate = params.getString(scenario.COL_THEATER);
        if (theaterTemplate.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id theater = poi.createObject(self, "theater", theaterTemplate, 0, 0);
        if ((theater == null) || (theater == obj_id.NULL_ID))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiWounded", "handleTheaterComplete: entered...");
        int idx = getIntObjVar(self, scenario.VAR_SCENARIO_IDX);
        dictionary d = dataTableGetRow(TBL, idx);
        obj_id[] buildings = new obj_id[0];
        java.util.Enumeration keys = params.keys();
        if (keys.hasMoreElements())
        {
            buildings = params.getObjIdArray(keys.nextElement());
        }
        if ((buildings == null) || (buildings.length == 0))
        {
        }
        else 
        {
        }
        messageTo(self, scenario.HANDLER_INIT_SCENARIO, d, 1, false);
        LOG("poiWounded", "handleTheaterComplete: exiting...");
        return SCRIPT_CONTINUE;
    }
    public int initScenario(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiWounded", "initScenario: entered...");
        if ((params == null) || (params.isEmpty()))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        location myLoc = getLocation(self);
        String mType = params.getString(scenario.COL_MEDIATOR);
        if ((mType == null) || (mType.equals("")))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int cnt = rand(3, 7);
            for (int i = 0; i < cnt; i++)
            {
                location loc = locations.getGoodLocationAroundLocation(myLoc, 2f, 2f, 8f, 8f);
                if (loc == null)
                {
                    loc = myLoc;
                }
                obj_id actor = poi.createNpc(self, scenario.MEDIATOR + "_" + i, mType, loc);
                if ((actor == null) || (actor == obj_id.NULL_ID))
                {
                }
                else 
                {
                    attachScript(actor, SCRIPT_MEDIATOR);
                    if (i == 0)
                    {
                        obj_id inv = utils.getInventoryContainer(actor);
                        if ((inv == null) || (inv == obj_id.NULL_ID))
                        {
                        }
                        else 
                        {
                            attachScript(inv, SCRIPT_LEADER_INV);
                        }
                    }
                    ai_lib.setDefaultCalmBehavior(actor, ai_lib.BEHAVIOR_LOITER);
                    setYaw(actor, rand(-180, 180));
                }
            }
        }
        LOG("poiWounded", "initScenario: exiting...");
        return SCRIPT_CONTINUE;
    }
    public int runScenario(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiWounded", "runScenario: entered...");
        LOG("poiWounded", "runScenario: exiting...");
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
    public int handleTimer(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
