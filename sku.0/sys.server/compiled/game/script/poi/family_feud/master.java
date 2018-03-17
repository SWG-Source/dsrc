package script.poi.family_feud;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.scenario;
import script.library.utils;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "family_feud";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_MASTER = BASE_PATH + ".master";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String SCRIPT_MEDIATOR_STRUCTURE = BASE_PATH + ".mediator_structure";
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_ANTAGONIST_STRUCTURE = BASE_PATH + ".antagonist_structure";
    public static final int BASE_LAG_TIME = 20;
    public static final int DEFAULT_ANTAGONIST_COUNT = 2;
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final String HANDLER_SPAWN_ACTORS = "handleSpawnActors";
    public static final String VAR_UNLOAD_NEXT_INIT = "unloadNextInit";
    public static final String VAR_ONLINE_STATUS = "scenario.onlineStatus";
    public static final String VAR_MEDIATOR_ONLINE = VAR_ONLINE_STATUS + ".mediator";
    public static final String VAR_ANTAGONIST_ONLINE = VAR_ONLINE_STATUS + ".antagonist";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "************ POI LAUNCH: " + getGameTime() + " ************");
        persistObject(self);
        setObjVar(self, VAR_ONLINE_STATUS, 0);
        dictionary params = scenario.getRandomScenario(TBL);
        if (params == null)
        {
            messageTo(self, scenario.HANDLER_DESTROY_SELF, null, 3, false);
            return SCRIPT_CONTINUE;
        }
        String name = params.getString(scenario.COL_NAME);
        debugServerConsoleMsg(self, "**** SCENARIO: " + name + " ***");
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
        String mStructureTemplate = params.getString(scenario.COL_MEDIATOR_STRUCTURE);
        if (mStructureTemplate.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        location myLoc = getLocation(self);
        location loc = utils.getRandomLocationInRing(myLoc, 2, 3);
        float dx = loc.x - myLoc.x;
        float dz = loc.z - myLoc.z;
        obj_id mStructure = poiCreateObject(self, "structure_m", mStructureTemplate, dx, dz);
        if ((mStructure == null) || (mStructure == obj_id.NULL_ID))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            persistObject(mStructure);
            attachScript(mStructure, SCRIPT_MEDIATOR_STRUCTURE);
            messageTo(mStructure, HANDLER_SPAWN_ACTORS, params, 1, false);
            setObjVar(self, scenario.VAR_MEDIATOR_LOC, loc);
        }
        messageTo(self, scenario.HANDLER_TIMER, null, 86400, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, VAR_UNLOAD_NEXT_INIT))
        {
            boolean mol = getBooleanObjVar(self, VAR_MEDIATOR_ONLINE);
            boolean aol = getBooleanObjVar(self, VAR_ANTAGONIST_ONLINE);
            if (!mol && !aol)
            {
                scenario.cleanup(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int initScenario(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "initScenario: entered...");
        if (params == null)
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        String aStructureTemplate = params.getString(scenario.COL_MEDIATOR_STRUCTURE);
        if (aStructureTemplate.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        location myLoc = getLocation(self);
        location loc = utils.getRandomLocationInRing(myLoc, 96, 128);
        float dx = loc.x - myLoc.x;
        float dz = loc.z - myLoc.z;
        obj_id aStructure = poiCreateObject(self, "structure_a", aStructureTemplate, dx, dz);
        if ((aStructure == null) || (aStructure == obj_id.NULL_ID))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            persistObject(aStructure);
            attachScript(aStructure, SCRIPT_ANTAGONIST_STRUCTURE);
            messageTo(aStructure, HANDLER_SPAWN_ACTORS, params, 1, false);
            setObjVar(self, scenario.VAR_ANTAGONIST_LOC, loc);
        }
        debugServerConsoleMsg(self, "initScenario: exiting...");
        return SCRIPT_CONTINUE;
    }
    public int runScenario(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "runScenario: entered...");
        debugServerConsoleMsg(self, "runScenario: exiting...");
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
        int cnt = 10;
        obj_id[] victors = new obj_id[0];
        if (name.startsWith(scenario.MEDIATOR))
        {
            cnt = getIntObjVar(self, scenario.VAR_MEDIATOR_COUNT);
            cnt--;
            setObjVar(self, scenario.VAR_MEDIATOR_COUNT, cnt);
            victors = scenario.getActorsWithNamePrefix(self, scenario.ANTAGONIST);
        }
        else if (name.startsWith(scenario.ANTAGONIST))
        {
            cnt = getIntObjVar(self, scenario.VAR_ANTAGONIST_COUNT);
            cnt--;
            setObjVar(self, scenario.VAR_ANTAGONIST_COUNT, cnt);
            victors = scenario.getActorsWithNamePrefix(self, scenario.MEDIATOR);
        }
        else 
        {
            debugServerConsoleMsg(self, "Unknown actor death!");
        }
        if (cnt < 1)
        {
            setObjVar(self, VAR_UNLOAD_NEXT_INIT, true);
            messageTo(self, scenario.HANDLER_TIMER, null, 1800, true);
            if ((victors == null) || (victors.length == 0))
            {
            }
            else 
            {
                for (int i = 0; i < victors.length; i++)
                {
                    switch (rand(1, 2))
                    {
                        case 1:
                        messageTo(victors[i], scenario.HANDLER_VICTORY, null, 1, false);
                        break;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, VAR_UNLOAD_NEXT_INIT))
        {
            boolean mol = getBooleanObjVar(self, VAR_MEDIATOR_ONLINE);
            boolean aol = getBooleanObjVar(self, VAR_ANTAGONIST_ONLINE);
            if (!mol && !aol)
            {
                scenario.cleanup(self);
            }
        }
        else 
        {
            setObjVar(self, VAR_UNLOAD_NEXT_INIT, true);
            messageTo(self, scenario.HANDLER_TIMER, null, 600 + rand(-300, 300), true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleOnlineStatusUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String name = params.getString(scenario.DICT_NAME);
        int status = params.getInt(scenario.DICT_STATUS);
        String varPath = "";
        if (name.equals(scenario.ANTAGONIST))
        {
            varPath = VAR_ANTAGONIST_ONLINE;
        }
        else 
        {
            varPath = VAR_MEDIATOR_ONLINE;
        }
        switch (status)
        {
            case scenario.ONLINE:
            setObjVar(self, varPath, true);
            break;
            default:
            setObjVar(self, varPath, false);
            break;
        }
        return SCRIPT_CONTINUE;
    }
}
