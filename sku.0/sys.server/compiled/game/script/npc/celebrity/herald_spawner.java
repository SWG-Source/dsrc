package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class herald_spawner extends script.base_script
{
    public herald_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "switch"))
        {
            String serverSwitch = getStringObjVar(self, "switch");
            String switchValue = getConfigSetting("Dungeon", serverSwitch);
            if (switchValue == null || switchValue.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            if ((toLower(switchValue)).equals("false"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        String spawn = getStringObjVar(self, "spawns");
        obj_id celeb = create.object(spawn, getLocation(self));
        setInvulnerable(celeb, true);
        ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
        if (hasObjVar(self, "quest_script"))
        {
            String script = getStringObjVar(self, "quest_script");
            attachScript(celeb, script);
        }
        if (hasObjVar(self, "quest_table"))
        {
            String table = getStringObjVar(self, "quest_table");
            setObjVar(celeb, "quest_table", table);
        }
        if (hasObjVar(self, "npc_name"))
        {
            String name = getStringObjVar(self, "npc_name");
            if (name != null)
            {
                if (!name.equals(""))
                {
                    setName(celeb, name);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
