package script.theme_park.dungeon.myyydril;

import script.obj_id;

public class grievous_guard extends script.base_script
{
    public grievous_guard()
    {
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id contentManager = getObjIdObjVar(self, "grievous_encounter.contentManager");
        messageTo(contentManager, "handleNpcDeath", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
