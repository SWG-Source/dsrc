package script.npc.junk_dealer;

import script.library.ai_lib;
import script.obj_id;

public class junk_dealer_arms extends script.base_script
{
    public junk_dealer_arms()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.junk_dealer.junk_dealer_arms");
        detachScript(self, "conversation.junk_dealer_arms");
        attachScript(self, "npc.converse.junk_dealer");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.junk_dealer.junk_dealer_arms");
        detachScript(self, "conversation.junk_dealer_arms");
        attachScript(self, "npc.converse.junk_dealer");
        return SCRIPT_CONTINUE;
    }
}
