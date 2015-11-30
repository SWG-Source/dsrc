package script.systems.crafting.bio_engineer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dna_component_attrib extends script.base_script
{
    public dna_component_attrib()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "");
        return SCRIPT_CONTINUE;
    }
}
