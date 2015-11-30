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

public class talus_nashal_kiki extends script.base_script
{
    public talus_nashal_kiki()
    {
    }
    public static String c_stringFile = "conversation/talus_nashal_kiki";
    public boolean talus_nashal_kiki_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean talus_nashal_kiki_condition_completeGotoKiki(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_nashal_goto_kiki", "selonian_goto_kiki") || groundquests.hasCompletedQuest(player, "talus_nashal_goto_kiki");
    }
    public boolean talus_nashal_kiki_condition_onFindRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_recon_hill_1") || groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_recon_landin_pad") || groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_recon_hill_2") || groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_recon_hill_3"));
    }
    public boolean talus_nashal_kiki_condition_onReturnKiki1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_return_kiki_1");
    }
    public boolean talus_nashal_kiki_condition_onFindFlight(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_gather_missions") || groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_cargo_flight_plan") || groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_kiki_comm"));
    }
    public boolean talus_nashal_kiki_condition_onReturnKiki2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_nashal_find_weakness", "selonian_return_kiki_2");
    }
    public boolean talus_nashal_kiki_condition_completeFindWeakness(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "talus_nashal_find_weakness");
    }
    public boolean talus_nashal_kiki_condition_completeGotoMother(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "talus_nashal_goto_mother");
    }
    public void talus_nashal_kiki_action_grantFindWeakness(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_nashal_find_weakness");
    }
    public void talus_nashal_kiki_action_signalFindFlight(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "selonian_return_kiki_1");
    }
    public void talus_nashal_kiki_action_signalCompleteFindWeakness(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "selonian_return_kiki_2");
    }
    public void talus_nashal_kiki_action_grantGotoMother(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_nashal_goto_mother");
    }
    public void talus_nashal_kiki_action_signalTalkKiki(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "selonian_goto_kiki");
    }
    public int talus_nashal_kiki_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                talus_nashal_kiki_action_grantGotoMother(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            doAnimationAction(player, "explain");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "expect_tip");
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            doAnimationAction(player, "paper");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                talus_nashal_kiki_action_signalCompleteFindWeakness(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            doAnimationAction(player, "expect_tip");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                talus_nashal_kiki_action_signalCompleteFindWeakness(player, npc);
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                talus_nashal_kiki_action_grantGotoMother(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                talus_nashal_kiki_action_grantGotoMother(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            doAnimationAction(player, "paper");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                doAnimationAction(player, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                talus_nashal_kiki_action_signalFindFlight(player, npc);
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            doAnimationAction(player, "bow");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_nashal_kiki_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            doAnimationAction(player, "nod");
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "survey");
                talus_nashal_kiki_action_grantFindWeakness(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
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
            detachScript(self, "conversation.talus_nashal_kiki");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.talus_nashal_kiki");
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
        if (talus_nashal_kiki_condition_completeGotoMother(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition_completeFindWeakness(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 2);
                npcStartConversation(player, npc, "talus_nashal_kiki", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition_onReturnKiki2(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 4);
                npcStartConversation(player, npc, "talus_nashal_kiki", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition_onFindFlight(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_21");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition_onReturnKiki1(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_23");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 11);
                npcStartConversation(player, npc, "talus_nashal_kiki", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition_onFindRecon(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition_completeGotoKiki(player, npc))
        {
            doAnimationAction(npc, "explain");
            doAnimationAction(player, "listen");
            talus_nashal_kiki_action_signalTalkKiki(player, npc);
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_nashal_kiki_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                utils.setScriptVar(player, "conversation.talus_nashal_kiki.branchId", 16);
                npcStartConversation(player, npc, "talus_nashal_kiki", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_nashal_kiki_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("talus_nashal_kiki"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.talus_nashal_kiki.branchId");
        if (branchId == 2 && talus_nashal_kiki_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && talus_nashal_kiki_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && talus_nashal_kiki_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && talus_nashal_kiki_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && talus_nashal_kiki_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && talus_nashal_kiki_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && talus_nashal_kiki_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && talus_nashal_kiki_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && talus_nashal_kiki_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && talus_nashal_kiki_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.talus_nashal_kiki.branchId");
        return SCRIPT_CONTINUE;
    }
}
