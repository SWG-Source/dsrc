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

public class kachirho_destroyed_camp_radio extends script.base_script
{
    public kachirho_destroyed_camp_radio()
    {
    }
    public static final String STF = "ep3/sidequests";
    public static final string_id CONTACT = new string_id(STF, "destroyed_camp_radio");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setRadioAttributes(self);
        setInvulnerable(self, true);
        attachScript(self, "conversation.destroyed_camp_radio");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setRadioAttributes(self);
        setInvulnerable(self, true);
        attachScript(self, "conversation.destroyed_camp_radio");
        return SCRIPT_CONTINUE;
    }
    public void setRadioAttributes(obj_id self) throws InterruptedException
    {
        createTriggerVolume("chat_range", 5, true);
        setAttributeInterested(self, attrib.ALL);
        return;
    }
    public void beginChatting(obj_id radio, obj_id player) throws InterruptedException
    {
        chat.chat(radio, CONTACT);
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
            beginChatting(self, who);
            playClientEffectLoc(who, "clienteffect/ep3_radio_static.cef", getLocation(self), 0f);
            utils.setScriptVar(self, "already_chatting", 1);
            messageTo(self, "handleReset", null, 120f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
