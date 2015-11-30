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

public class mirla extends script.base_script
{
    public mirla()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String PASSKEYCODE = "object/tangible/mission/quest_item/warren_passkey_s01.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "");
        setName(self, new string_id("theme_park/warren/warren_system_messages", "name_mirla"));
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
        if (badge.hasBadge(speaker, "warren_compassion"))
        {
            if (badge.hasBadge(speaker, "warren_hero"))
            {
                chat.chat(self, new string_id(CONVO_FILE, "mirla_done"));
            }
            else 
            {
                chat.chat(self, new string_id(CONVO_FILE, "mirla_get_teraud"));
            }
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(CONVO_FILE, "mirla_start");
        string_id response[] = new string_id[1];
        response[0] = new string_id(CONVO_FILE, "mirla_reply_1");
        npcStartConversation(speaker, self, CONVO_FILE, greeting, response);
        doAnimationAction(self, "weeping");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(CONVO_FILE))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("mirla_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "mirla_1");
            npcSpeak(player, message);
            doAnimationAction(self, "gesticulate_wildly");
            if (hasLetter(player))
            {
                npcAddConversationResponse(player, new string_id(CONVO_FILE, "mirla_1_reply_1"));
            }
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "mirla_1_reply_2"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("mirla_1_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "mirla_2");
            npcSpeak(player, message);
            if (takeLetter(player))
            {
                badge.grantBadge(player, "warren_compassion");
            }
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "mirla_2_reply_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("mirla_1_reply_2"))
        {
            string_id message = new string_id(CONVO_FILE, "mirla_3");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("mirla_2_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "mirla_4");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "mirla_4_reply_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("mirla_4_reply_1"))
        {
            prose_package pp = prose.getPackage(new string_id(SYSTEM_MESSAGES, "mirla_password"), getCellPassword(self));
            npcSpeak(player, pp);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id warren = getObjIdObjVar(self, "warren.bldg");
        messageTo(warren, "handleMirlaDied", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public boolean hasLetter(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return false;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if ((getTemplateName(contents[i])).equals("object/tangible/mission/quest_item/warren_farewell_letter.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean takeLetter(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return false;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if ((getTemplateName(contents[i])).equals("object/tangible/mission/quest_item/warren_farewell_letter.iff"))
            {
                destroyObject(contents[i]);
                return true;
            }
        }
        return false;
    }
    public String getCellPassword(obj_id npc) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(npc);
        return getStringObjVar(bldg, "warren.cellPassword");
    }
}
