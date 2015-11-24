package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.chat;
import script.library.attrib;
import script.library.utils;

public class forest_outcast_barker extends script.base_script
{
    public forest_outcast_barker()
    {
    }
    public static final String STF = "ep3/sidequests";
    public static final string_id CONTACT = new string_id(STF, "outcast");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setBarkAttributes(self);
        setInvulnerable(self, true);
        attachScript(self, "conversation.ep3_forest_outcast_informant");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setBarkAttributes(self);
        setInvulnerable(self, true);
        attachScript(self, "conversation.ep3_forest_outcast_informant");
        return SCRIPT_CONTINUE;
    }
    public void setBarkAttributes(obj_id self) throws InterruptedException
    {
        createTriggerVolume("chat_range", 10, true);
        setAttributeInterested(self, attrib.ALL);
        return;
    }
    public void beginChatting(obj_id self, obj_id player) throws InterruptedException
    {
        chat.chat(self, CONTACT);
        return;
    }
    public int handleReset(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "already_chatting");
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "already_chatting"))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("chat_range"))
        {
            if (groundquests.isTaskActive(who, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedQuest(who, "ep3_forest_wirartu_epic_2"))
            {
                beginChatting(self, who);
                utils.setScriptVar(self, "already_chatting", 1);
                messageTo(self, "handleReset", null, 60f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
