package script.npc.celebrity;

import script.library.ai_lib;
import script.library.create;
import script.library.events;
import script.obj_id;

public class lifeday_spawner extends script.base_script
{
    public lifeday_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (events.isEventActive(events.LIFEDAY))
        {
            String spawn = getStringObjVar(self, "spawns");
            obj_id celeb = create.object(spawn, getLocation(self));
            if (!isIdValid(celeb))
            {
                return SCRIPT_CONTINUE;
            }
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
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
