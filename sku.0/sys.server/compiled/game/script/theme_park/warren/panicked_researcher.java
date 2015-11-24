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

public class panicked_researcher extends script.base_script
{
    public panicked_researcher()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_SCARED);
        setInvulnerable(self, true);
        setName(self, "");
        setName(self, new string_id("theme_park/warren/warren_system_messages", "name_manx"));
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
        if (isReactorDisabled(self))
        {
            string_id greeting = new string_id(CONVO_FILE, "researcher_2");
            chat.chat(self, greeting);
            return SCRIPT_CONTINUE;
        }
        if (hasBothCores(speaker))
        {
            string_id greeting = new string_id(CONVO_FILE, "researcher_3");
            chat.chat(self, greeting);
            return SCRIPT_CONTINUE;
        }
        string_id greeting = new string_id(CONVO_FILE, "researcher_start");
        string_id response[] = new string_id[1];
        response[0] = new string_id(CONVO_FILE, "researcher_reply_1");
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
        if ((response.getAsciiId()).equals("researcher_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "researcher_1");
            npcSpeak(player, message);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id warren = getObjIdObjVar(self, "warren.bldg");
        messageTo(warren, "handleResearcherDied", null, 800, false);
        return SCRIPT_CONTINUE;
    }
    public boolean isReactorDisabled(obj_id self) throws InterruptedException
    {
        obj_id warren = getObjIdObjVar(self, "warren.bldg");
        if (!isIdValid(warren))
        {
            return false;
        }
        if (utils.hasScriptVar(warren, "warren.reactorOverriden"))
        {
            return true;
        }
        return false;
    }
    public boolean hasBothCores(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return false;
        }
        int countCores = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.reactorCore"))
            {
                countCores++;
                if (countCores > 1)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
