package script.npc.celebrity;

import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class celebrity_spawner extends script.base_script
{
    public celebrity_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        here.y = here.y + 0.5f;
        obj_id celeb = create.object(getStringObjVar(self, "spawns"), here);
        setInvulnerable(celeb, true);
        ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
        if (hasObjVar(self, "quest_script"))
        {
            attachScript(celeb, getStringObjVar(self, "quest_script"));
        }
        if (hasObjVar(self, "quest_table"))
        {
            setObjVar(celeb, "quest_table", getStringObjVar(self, "quest_table"));
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
