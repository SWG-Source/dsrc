package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_crafting;
import script.library.space_utils;
import script.library.space_combat;

public class ship_interior_autocleanup extends script.base_script
{
    public ship_interior_autocleanup()
    {
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
