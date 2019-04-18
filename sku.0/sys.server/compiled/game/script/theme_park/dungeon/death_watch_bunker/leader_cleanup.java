package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class leader_cleanup extends script.base_script
{
    public leader_cleanup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAttackerCleanUp", null, 1500.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAttackerCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleAttackerCleanUp", null, 180.0f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id spawner = getObjIdObjVar(self, "spawner");
        setObjVar(spawner, "death_watch_herald.boss.died", getGameTime());
        removeObjVar(spawner, "death_watch_herald.boss.spawned");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
