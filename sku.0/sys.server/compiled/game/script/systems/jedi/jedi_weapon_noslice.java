package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class jedi_weapon_noslice extends script.base_script
{
    public jedi_weapon_noslice()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "slicing.slicing_weapon");
        detachScript(self, "systems.jedi.jedi_weapon_noslice");
        return SCRIPT_CONTINUE;
    }
}
