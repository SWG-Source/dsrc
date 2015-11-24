package script.npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class legacy_kleetuz_timeout extends script.base_script
{
    public legacy_kleetuz_timeout()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgCheckCombat", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int msgCheckCombat(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIncapacitated(self) || !isDead(self) || !ai_lib.isInCombat(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
