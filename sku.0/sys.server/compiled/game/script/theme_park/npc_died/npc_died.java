package script.theme_park.npc_died;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class npc_died extends script.base_script
{
    public npc_died()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, "useOnIncapTrigger"))
        {
            obj_id spawner = getObjIdObjVar(self, "spawner");
            final String npc_type = getCreatureName(self);
            dictionary npcParams = new dictionary();
            if (npc_type == null)
            {
                npcParams.put("npc_type", "nothing");
            }
            else 
            {
                npcParams.put("npc_type", npc_type);
            }
            messageTo(spawner, "npcDied", npcParams, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "useOnIncapTrigger"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id spawner = getObjIdObjVar(self, "spawner");
        String npc_type = getCreatureName(self);
        dictionary npcParams = new dictionary();
        if (npc_type == null)
        {
            npcParams.put("npc_type", "nothing");
        }
        else 
        {
            npcParams.put("npc_type", npc_type);
        }
        messageTo(spawner, "npcDied", npcParams, 5, false);
        return SCRIPT_CONTINUE;
    }
}
