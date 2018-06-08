package script.conversation;

import script.dictionary;
import script.obj_id;

public class hutt_informant_quest extends script.base_script
{
    public hutt_informant_quest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "attachMissingScript", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int attachMissingScript(obj_id self, dictionary params) throws InterruptedException
    {
        attachScript(self, "conversation.hutt_informant");
        return SCRIPT_CONTINUE;
    }
}
