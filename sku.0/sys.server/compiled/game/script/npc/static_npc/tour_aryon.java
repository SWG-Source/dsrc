package script.npc.static_npc;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class tour_aryon extends script.base_script
{
    public tour_aryon()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "npcScript_setHerName", null, 5, false);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int npcScript_setHerName(obj_id self, dictionary params) throws InterruptedException
    {
        setName(self, "");
        setName(self, "Tour Aryon (governor)");
        return SCRIPT_CONTINUE;
    }
}
