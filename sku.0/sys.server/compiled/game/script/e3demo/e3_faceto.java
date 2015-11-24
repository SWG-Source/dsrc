package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class e3_faceto extends script.base_script
{
    public e3_faceto()
    {
    }
    public int OnStartNpcConversation(obj_id self, obj_id conversant) throws InterruptedException
    {
        faceTo(self, conversant);
        return SCRIPT_CONTINUE;
    }
}
