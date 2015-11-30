package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class serialize_component extends script.base_script
{
    public serialize_component()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        boolean success = true;
        success &= setCraftedId(self, self);
        success &= setCrafter(self, self);
        if (success)
        {
            detachScript(self, "item.special.serialize_component");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        boolean success = true;
        success &= setCraftedId(self, self);
        success &= setCrafter(self, self);
        if (success)
        {
            detachScript(self, "item.special.serialize_component");
        }
        return SCRIPT_CONTINUE;
    }
}
