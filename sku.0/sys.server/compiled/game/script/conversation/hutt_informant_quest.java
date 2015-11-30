package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
