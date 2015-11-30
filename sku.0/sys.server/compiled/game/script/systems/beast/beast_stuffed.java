package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class beast_stuffed extends script.base_script
{
    public beast_stuffed()
    {
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
