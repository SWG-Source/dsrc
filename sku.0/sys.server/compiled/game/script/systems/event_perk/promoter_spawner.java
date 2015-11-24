package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class promoter_spawner extends script.base_script
{
    public promoter_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String spawn = getStringObjVar(self, "spawns");
        obj_id celeb = create.object(spawn, getLocation(self));
        setInvulnerable(celeb, true);
        ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setMood(celeb, "calm");
        if (hasObjVar(self, "quest_script"))
        {
            String script = getStringObjVar(self, "quest_script");
            attachScript(celeb, script);
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
        String myType = getStringObjVar(self, "promoter_type");
        setObjVar(celeb, "event_perk.promoter.promoter_type", myType);
        return SCRIPT_CONTINUE;
    }
}
