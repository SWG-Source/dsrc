package script.theme_park.dungeon.avatar_platform;

import script.obj_id;

public class cell_door_setup_02 extends script.base_script
{
    public cell_door_setup_02()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        setObjVar(structure, "avatar_platform.cell_door_02", self);
        return SCRIPT_CONTINUE;
    }
}
