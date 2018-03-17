package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.chat;
import script.ai.ai_combat;

public class escaped_worker extends script.base_script
{
    public escaped_worker()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_SCARED);
        setName(self, "");
        setName(self, new string_id("theme_park/warren/warren_system_messages", "name_phy"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        chat.setChatMood(self, chat.MOOD_SCARED);
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(CONVO_FILE, "worker_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(CONVO_FILE, "worker_reply_1");
        response[1] = new string_id(CONVO_FILE, "worker_reply_2");
        npcStartConversation(speaker, self, CONVO_FILE, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(CONVO_FILE))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("worker_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "worker_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "worker_1_reply_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("worker_1_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "worker_3");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "worker_3_reply_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("worker_3_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "worker_4");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "worker_4_reply_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("worker_4_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "worker_5");
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("worker_reply_2"))
        {
            string_id message = new string_id(CONVO_FILE, "worker_2");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "worker_2_reply_2"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("worker_2_reply_2"))
        {
            string_id message = new string_id(CONVO_FILE, "worker_6");
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id warren = getObjIdObjVar(self, "warren.bldg");
        messageTo(warren, "handleEscapedWorkerDied", null, 800, false);
        return SCRIPT_CONTINUE;
    }
}
