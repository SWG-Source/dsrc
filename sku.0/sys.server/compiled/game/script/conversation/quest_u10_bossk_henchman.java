package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class quest_u10_bossk_henchman extends script.base_script
{
    public quest_u10_bossk_henchman()
    {
    }
    public static String c_stringFile = "conversation/quest_u10_bossk_henchman";
    public boolean quest_u10_bossk_henchman_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_u10_bossk_henchman_condition_quest_u10_03_02_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_03", "quest_u10_03_02");
    }
    public boolean quest_u10_bossk_henchman_condition_quest_u10_03_04_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_03", "quest_u10_03_04");
    }
    public boolean quest_u10_bossk_henchman_condition_quest_u10_03_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest_u10_03");
    }
    public boolean quest_u10_bossk_henchman_condition_quest_u10_03_01_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_03", "quest_u10_03_01");
    }
    public boolean quest_u10_bossk_henchman_condition_quest_u10_03_05_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_03", "quest_u10_03_05");
    }
    public boolean quest_u10_bossk_henchman_condition_quest_u10_complete_deleted_u16(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest_u10_03") && !groundquests.isQuestActiveOrComplete(player, "quest_u16_nym_themepark_pointer_01") && !groundquests.isQuestActiveOrComplete(player, "quest_u16_nym_themepark_pointer_02");
    }
    public void quest_u10_bossk_henchman_action_quest_u10_03_01_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "quest_u10_03_01");
    }
    public void quest_u10_bossk_henchman_action_attack(obj_id player, obj_id npc) throws InterruptedException
    {
        setInvulnerable(npc, false);
        startCombat(npc, player);
        clearCondition(npc, CONDITION_CONVERSABLE);
        if (hasScript(npc, "conversation.quest_u10_bossk_henchman"))
        {
            detachScript(npc, "conversation.quest_u10_bossk_henchman");
        }
        return;
    }
    public void quest_u10_bossk_henchman_action_grant_u16_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_pointer_02");
    }
    public void quest_u10_bossk_henchman_action_quest_u10_03_05_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "quest_u10_03_05");
    }
    public int quest_u10_bossk_henchman_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (quest_u10_bossk_henchman_condition__defaultCondition(player, npc))
            {
                quest_u10_bossk_henchman_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_11");
                utils.removeScriptVar(player, "conversation.quest_u10_bossk_henchman.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.quest_u10_bossk_henchman");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        if (!hasScript(self, "theme_park.corellia.content.quest_u10_bossk_henchman"))
        {
            attachScript(self, "theme_park.corellia.content.quest_u10_bossk_henchman");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.quest_u10_bossk_henchman");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (quest_u10_bossk_henchman_condition_quest_u10_complete_deleted_u16(player, npc))
        {
            quest_u10_bossk_henchman_action_grant_u16_quest(player, npc);
            string_id message = new string_id(c_stringFile, "s_16");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_bossk_henchman_condition_quest_u10_03_complete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_bossk_henchman_condition_quest_u10_03_05_active(player, npc))
        {
            quest_u10_bossk_henchman_action_quest_u10_03_05_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_12");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_bossk_henchman_condition_quest_u10_03_04_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_u10_bossk_henchman_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                utils.setScriptVar(player, "conversation.quest_u10_bossk_henchman.branchId", 4);
                npcStartConversation(player, npc, "quest_u10_bossk_henchman", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_bossk_henchman_condition_quest_u10_03_02_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_bossk_henchman_condition_quest_u10_03_01_active(player, npc))
        {
            quest_u10_bossk_henchman_action_quest_u10_03_01_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_15");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_bossk_henchman_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_u10_bossk_henchman"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_u10_bossk_henchman.branchId");
        if (branchId == 4 && quest_u10_bossk_henchman_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_u10_bossk_henchman.branchId");
        return SCRIPT_CONTINUE;
    }
}
