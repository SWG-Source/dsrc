package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class singing_mt_clan_guardian extends script.base_script
{
    public singing_mt_clan_guardian()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Singing Mountain Clan Guardian");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
