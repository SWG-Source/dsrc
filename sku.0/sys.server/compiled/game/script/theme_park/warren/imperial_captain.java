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
import script.library.badge;
import script.library.prose;
import script.library.factions;

public class imperial_captain extends script.base_script
{
    public imperial_captain()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String PASSKEYCODE = "object/tangible/mission/quest_item/warren_passkey_s01.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "");
        setName(self, new string_id("theme_park/warren/warren_system_messages", "name_heff"));
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
        if (badge.hasBadge(speaker, "warren_hero"))
        {
            chat.chat(self, new string_id(CONVO_FILE, "heff_done"));
            return SCRIPT_CONTINUE;
        }
        else if (!hasEvidence(speaker))
        {
            chat.chat(self, new string_id(CONVO_FILE, "heff_bye"));
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(CONVO_FILE, "heff_start");
        string_id response[] = new string_id[1];
        response[0] = new string_id(CONVO_FILE, "heff_reply_1");
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
        if ((response.getAsciiId()).equals("heff_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "heff_1");
            npcSpeak(player, message);
            if (takeAllEvidence(player))
            {
                if (!badge.hasBadge(player, "warren_hero"))
                {
                    badge.grantBadge(player, "warren_hero");
                    factions.addFactionStanding(player, "Imperial", 500.0f);
                }
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasEvidence(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getPlayerDatapad(player);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return false;
        }
        int[] evCount = new int[5];
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.evidence"))
            {
                evCount[getIntObjVar(contents[i], "warren.evidence")] = 1;
            }
        }
        for (int i = 1; i < 5; i++)
        {
            if (evCount[i] == 0)
            {
                return false;
            }
        }
        return true;
    }
    public boolean takeAllEvidence(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getPlayerDatapad(player);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return false;
        }
        int[] evCount = new int[5];
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.evidence"))
            {
                evCount[getIntObjVar(contents[i], "warren.evidence")] = 1;
                destroyObject(contents[i]);
            }
        }
        for (int i = 1; i < 5; i++)
        {
            if (evCount[i] == 0)
            {
                return false;
            }
        }
        return true;
    }
}
