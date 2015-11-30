package script.theme_park.script_spawner.spawner_methods;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class ss_setup extends script.base_script
{
    public ss_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginSetup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int beginSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "spawn_objvar"))
        {
            String objVarOne = getStringObjVar(self, "spawn_objvar");
            setObjVarScript(self, objVarOne);
        }
        if (hasObjVar(self, "spawn_objvar2"))
        {
            String objVarTwo = getStringObjVar(self, "spawn_objvar2");
            setObjVarScript(self, objVarTwo);
        }
        return SCRIPT_CONTINUE;
    }
    public void setObjVarScript(obj_id self, String sSentObjVar) throws InterruptedException
    {
        if (sSentObjVar.equals("static"))
        {
            if (hasObjVar(self, "spawn_objvar2"))
            {
                if ("customs" == getStringObjVar(self, "spawn_objvar2"))
                {
                    String script = "city.city_wander";
                    attachScript(self, script);
                    return;
                }
            }
            if (!hasScript(self, "ai.ai"))
            {
                attachScript(self, "ai.ai");
            }
            stop(self);
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        }
        else if (sSentObjVar.equals("default"))
        {
            if (!hasScript(self, "ai.ai"))
            {
                attachScript(self, "ai.ai");
            }
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_WANDER);
        }
        else if (sSentObjVar.equals("city_wander"))
        {
            String script = "city.city_wander";
            attachScript(self, script);
        }
        else if (sSentObjVar.equals("covert_droid"))
        {
        }
        else if (sSentObjVar.equals("customs"))
        {
            String script = "ai.imperial_presence.harass";
            attachScript(self, script);
        }
        else if (sSentObjVar.equals("scan_follow"))
        {
            String script = "city.imperial_crackdown.st_static";
            attachScript(self, script);
        }
        return;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_CONTINUE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        if (spawnNum < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int respawnTime = getIntObjVar(self, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 300;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
}
