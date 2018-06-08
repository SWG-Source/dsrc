package script.conversation;

import script.dictionary;
import script.obj_id;

public class sean_contact_quest extends script.base_script
{
    public sean_contact_quest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "attachMissingScript", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int attachMissingScript(obj_id self, dictionary params) throws InterruptedException
    {
        attachScript(self, "conversation.sean_contact");
        return SCRIPT_CONTINUE;
    }
}
