package script.theme_park.dungeon;

import script.obj_id;

public class monster_maker_tracker extends script.base_script
{
    public monster_maker_tracker()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "parent"))
        {
            messageTo(getObjIdObjVar(self, "parent"), "removeMonsterBlock", null, 120, false);
        }
        return SCRIPT_CONTINUE;
    }
}
