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
import script.library.locations;
import script.library.factions;
import script.library.ai_lib;
import script.ai.ai_combat;

public class mediator_structure extends script.theme_park.poi.base
{
    public mediator_structure()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put(scenario.DICT_NAME, scenario.MEDIATOR);
        d.put(scenario.DICT_STATUS, scenario.ONLINE);
        messageTo(poiMaster, scenario.HANDLER_ONLINE_STATUS_UPDATE, d, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put(scenario.DICT_NAME, scenario.MEDIATOR);
        d.put(scenario.DICT_STATUS, scenario.OFFLINE);
        messageTo(poiMaster, scenario.HANDLER_ONLINE_STATUS_UPDATE, d, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnActors(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String scenario_name = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_NAME);
        if (scenario_name.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if ((params == null) || (params.isEmpty()))
        {
            scenario.cleanup(poiMaster);
            return SCRIPT_CONTINUE;
        }
        location baseLoc = getLocation(poiMaster);
        location myLoc = getLocation(self);
        params.put(scenario.DICT_LOCATION, myLoc);
        String leaderType = params.getString(scenario.COL_MEDIATOR);
        if (leaderType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = locations.getGoodLocationAroundLocation(myLoc, 2f, 2f, 10f, 10f);
        float dx = loc.x - baseLoc.x;
        float dz = loc.z - baseLoc.z;
        String myName = scenario.MEDIATOR + "_0";
        obj_id leader = poi.createNpc(poiMaster, myName, leaderType, dx, dz);
        if ((leader == null) || (leader == obj_id.NULL_ID))
        {
        }
        else 
        {
            persistObject(leader);
            factions.setFaction(leader, poiMaster.toString() + "_mediator", false);
            attachScript(leader, "poi." + scenario_name + ".mediator");
            setObjVar(leader, scenario.VAR_MY_NAME, myName);
            setObjVar(leader, scenario.VAR_RANK, scenario.SR_MEDIATOR);
            ai_lib.setDefaultCalmBehavior(leader, ai_lib.BEHAVIOR_LOITER);
        }
        String minionType = params.getString(scenario.COL_MEDIATOR_MINION);
        if (!minionType.equals(""))
        {
            int roll = rand(3, 6);
            int count = 0;
            for (int i = 0; i < roll; i++)
            {
                myName = scenario.MEDIATOR + "_" + (i + 1);
                loc = locations.getGoodLocationAroundLocation(myLoc, 2f, 2f, 10f, 10f);
                dx = loc.x - baseLoc.x;
                dz = loc.z - baseLoc.z;
                obj_id m = poi.createNpc(poiMaster, myName, minionType, dx, dz);
                if ((m == null) || (m == obj_id.NULL_ID))
                {
                }
                else 
                {
                    persistObject(m);
                    setYaw(m, rand(0, 359));
                    factions.setFaction(m, poiMaster.toString() + "_mediator", false);
                    attachScript(m, "poi." + scenario_name + ".mediator");
                    setObjVar(m, scenario.VAR_MY_NAME, myName);
                    setObjVar(m, scenario.VAR_RANK, scenario.SR_MEDIATOR_MINION);
                    ai_lib.setDefaultCalmBehavior(m, ai_lib.BEHAVIOR_LOITER);
                    count++;
                }
            }
            setObjVar(poiMaster, scenario.VAR_MEDIATOR_COUNT, count + 1);
        }
        messageTo(poiMaster, scenario.HANDLER_INIT_SCENARIO, params, 1, false);
        return SCRIPT_CONTINUE;
    }
}
