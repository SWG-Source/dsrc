package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class cell_door_setup_06 extends script.base_script
{
    public cell_door_setup_06()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        setObjVar(structure, "avatar_platform.cell_door_06", self);
        return SCRIPT_CONTINUE;
    }
}
