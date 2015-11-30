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

public class quest_u10_draykken_bender extends script.base_script
{
    public quest_u10_draykken_bender()
    {
    }
    public static String c_stringFile = "conversation/quest_u10_draykken_bender";
    public boolean quest_u10_draykken_bender_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_u10_draykken_bender_condition_quest_u10_01_02_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_01", "quest_u10_01_02");
    }
    public boolean quest_u10_draykken_bender_condition_quest_u10_01_03_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_01", "quest_u10_01_03") || groundquests.hasCompletedTask(player, "quest_u10_01", "quest_u10_01_03");
    }
    public boolean quest_u10_draykken_bender_condition_quest_u10_02_needed(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "quest_u10_02");
    }
    public boolean quest_u10_draykken_bender_condition_quest_u10_01_01_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest_u10_01", "quest_u10_01_01");
    }
    public void quest_u10_draykken_bender_action_quest_u10_01_01_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "quest_u10_01_01");
    }
    public void quest_u10_draykken_bender_action_quest_u10_02_grant(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest_u10_02");
    }
    public void quest_u10_draykken_bender_action_quest_u10_01_03_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "quest_u10_01_03");
    }
    public int quest_u10_draykken_bender_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (quest_u10_draykken_bender_condition__defaultCondition(player, npc))
            {
                quest_u10_draykken_bender_action_quest_u10_02_grant(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.quest_u10_draykken_bender.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_u10_draykken_bender_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (quest_u10_draykken_bender_condition__defaultCondition(player, npc))
            {
                quest_u10_draykken_bender_action_quest_u10_01_01_signal(player, npc);
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.quest_u10_draykken_bender.branchId");
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
            detachScript(self, "conversation.quest_u10_draykken_bender");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.quest_u10_draykken_bender");
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
        if (quest_u10_draykken_bender_condition_quest_u10_01_03_active(player, npc))
        {
            quest_u10_draykken_bender_action_quest_u10_01_03_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_u10_draykken_bender_condition_quest_u10_02_needed(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                }
                utils.setScriptVar(player, "conversation.quest_u10_draykken_bender.branchId", 1);
                npcStartConversation(player, npc, "quest_u10_draykken_bender", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_draykken_bender_condition_quest_u10_01_02_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_draykken_bender_condition_quest_u10_01_01_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_u10_draykken_bender_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.quest_u10_draykken_bender.branchId", 4);
                npcStartConversation(player, npc, "quest_u10_draykken_bender", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_u10_draykken_bender_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("quest_u10_draykken_bender"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_u10_draykken_bender.branchId");
        if (branchId == 1 && quest_u10_draykken_bender_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && quest_u10_draykken_bender_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_u10_draykken_bender.branchId");
        return SCRIPT_CONTINUE;
    }
}
