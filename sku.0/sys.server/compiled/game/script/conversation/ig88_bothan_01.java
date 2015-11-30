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
import script.library.groundquests;
import script.library.utils;

public class ig88_bothan_01 extends script.base_script
{
    public ig88_bothan_01()
    {
    }
    public static String c_stringFile = "conversation/ig88_bothan_01";
    public boolean ig88_bothan_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ig88_bothan_01_condition_onTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "feeder_ig88_03", "feeder_loot_ig88_01") || groundquests.isTaskActive(player, "feeder_ig88_03", "feeder_retrieve_ig88_01") || groundquests.isTaskActive(player, "feeder_ig88_03", "feeder_retrieve_ig88_02"));
    }
    public boolean ig88_bothan_01_condition_endQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "feeder_ig88_03", "feeder_signal_ig88_02");
    }
    public boolean ig88_bothan_01_condition_needsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "feeder_ig88_01", "feeder_singal_ig88_03");
    }
    public boolean ig88_bothan_01_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "feeder_ig88_01", "feeder_ig88_wait_02");
    }
    public boolean ig88_bothan_01_condition_finishedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "feeder_ig88_03");
    }
    public void ig88_bothan_01_action_completeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "feeder_signal_ig88_03_02");
    }
    public void ig88_bothan_01_action_grantQuest03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "feeder_ig88_03");
        groundquests.sendSignal(player, "feeder_signal_ig88_01_03");
    }
    public int ig88_bothan_01_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_16"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                ig88_bothan_01_action_completeQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                ig88_bothan_01_action_grantQuest03(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            doAnimationAction(player, "pose_proudly");
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ig88_bothan_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ig88_bothan_01_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                ig88_bothan_01_action_grantQuest03(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
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
            detachScript(self, "conversation.ig88_bothan_01");
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
        detachScript(self, "conversation.ig88_bothan_01");
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
        if (ig88_bothan_01_condition_finishedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ig88_bothan_01_condition_endQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ig88_bothan_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 2);
                npcStartConversation(player, npc, "ig88_bothan_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ig88_bothan_01_condition_onQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ig88_bothan_01_condition_onTask(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!ig88_bothan_01_condition_onTask(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 5);
                npcStartConversation(player, npc, "ig88_bothan_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ig88_bothan_01_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ig88_bothan_01_condition_needsQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.ig88_bothan_01.branchId", 8);
                npcStartConversation(player, npc, "ig88_bothan_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ig88_bothan_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ig88_bothan_01.branchId");
        if (branchId == 2 && ig88_bothan_01_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ig88_bothan_01_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ig88_bothan_01_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ig88_bothan_01_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ig88_bothan_01_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ig88_bothan_01_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ig88_bothan_01_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ig88_bothan_01_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ig88_bothan_01_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ig88_bothan_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
