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

public class worker extends script.base_script
{
    public worker()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_SCARED);
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
        string_id greeting = new string_id(CONVO_FILE, "worker_bark" + rand(1, 4));
        chat.chat(self, greeting);
        return SCRIPT_CONTINUE;
    }
}
