package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;

public class afflicted_npc extends script.base_script
{
    public afflicted_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stopFloating(self);
        messageTo(self, "handleAfflictedNpcResilience", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAfflictedNpcResilience(obj_id self, dictionary params) throws InterruptedException
    {
        buff.applyBuff(self, "death_troopers_afflicted_npc", -1.0f);
        buff.applyBuff(self, "death_troopers_afflicted_npc_visible", -1.0f);
        stopFloating(self);
        return SCRIPT_CONTINUE;
    }
}
