package script.systems.crafting.repair;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.utils;

public class clothing_repair extends script.base_script
{
    public clothing_repair()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "crafting.type", GOT_clothing);
        return SCRIPT_CONTINUE;
    }
}
