package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class qa_stealth extends script.base_script
{
    public qa_stealth()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_stealth");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_stealth");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals("qa_hide") || (toLower(text)).equals("qahide"))
            {
                sendSystemMessageTestingOnly(self, "Your character has been hidden from other clients.");
                hideFromClient(self, true);
            }
            else if ((toLower(text)).equals("qa_unhide") || (toLower(text)).equals("qaunhide"))
            {
                sendSystemMessageTestingOnly(self, "Your character is now visible to other clients.");
                hideFromClient(self, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        hideFromClient(self, false);
        detachScript(self, "test.qa_stealth");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "test.qa_stealth"))
        {
            hideFromClient(self, false);
            detachScript(self, "test.qa_stealth");
        }
        return SCRIPT_CONTINUE;
    }
}
