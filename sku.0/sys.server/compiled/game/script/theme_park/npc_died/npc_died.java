package script.theme_park.npc_died;

import script.dictionary;
import script.obj_id;

import java.util.Objects;

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
            npcParams.put("npc_type", Objects.requireNonNullElse(npc_type, "nothing"));
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
        npcParams.put("npc_type", Objects.requireNonNullElse(npc_type, "nothing"));
        messageTo(spawner, "npcDied", npcParams, 5, false);
        return SCRIPT_CONTINUE;
    }
}
