package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.attrib;

public class victim extends script.base_script
{
    public victim()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAttributeAttained(self, attrib.VICTIM);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAttributeAttained(self, attrib.VICTIM);
        return SCRIPT_CONTINUE;
    }
}
