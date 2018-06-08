package script.theme_park.outbreak;

import script.dictionary;
import script.library.buff;
import script.obj_id;

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
