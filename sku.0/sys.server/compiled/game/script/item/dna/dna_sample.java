package script.item.dna;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dna_sample extends script.base_script
{
    public dna_sample()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isCrafted(self))
        {
            setCraftedId(self, self);
        }
        return SCRIPT_CONTINUE;
    }
}
