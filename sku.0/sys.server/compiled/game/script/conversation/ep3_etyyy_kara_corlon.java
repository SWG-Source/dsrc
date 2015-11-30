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
import script.library.space_quest;
import script.library.utils;

public class ep3_etyyy_kara_corlon extends script.base_script
{
    public ep3_etyyy_kara_corlon()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_kara_corlon";
    public boolean ep3_etyyy_kara_corlon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_kara_corlon_condition_completedFirstDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_01");
    }
    public boolean ep3_etyyy_kara_corlon_condition_completedThirdDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_03");
    }
    public boolean ep3_etyyy_kara_corlon_condition_completedSecondDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_02");
    }
    public boolean ep3_etyyy_kara_corlon_condition_talkToKara(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_johnson_help_kara", "johnson_talkToKara");
    }
    public boolean ep3_etyyy_kara_corlon_condition_karaKnowsYou(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_johnson_help_kara", "johnson_talkToKara");
    }
    public boolean ep3_etyyy_kara_corlon_condition_workingOnDeliveries(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_johnson_help_kara", "johnson_karaDeliveries");
    }
    public boolean ep3_etyyy_kara_corlon_condition_alreadyHasSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public void ep3_etyyy_kara_corlon_action_giveThirdDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_03");
        space_quest.grantQuest(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_03");
    }
    public void ep3_etyyy_kara_corlon_action_deliveriesDone(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "johnson_karaDeliveries");
    }
    public void ep3_etyyy_kara_corlon_action_giveSecondDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_02");
        space_quest.grantQuest(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_02");
    }
    public void ep3_etyyy_kara_corlon_action_giveFirstDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_hunt_johnson_help_kara", "johnson_talkToKara"))
        {
            groundquests.sendSignal(player, "johnson_talkToKara");
        }
        space_quest.clearQuestFlags(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_01");
        space_quest.grantQuest(player, "delivery_no_pickup", "ep3_hunting_kara_poacher_delivery_01");
    }
    public int ep3_etyyy_kara_corlon_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (ep3_etyyy_kara_corlon_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kara_corlon_condition_completedThirdDelivery(player, npc))
            {
                ep3_etyyy_kara_corlon_action_deliveriesDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kara_corlon_condition_completedSecondDelivery(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kara_corlon_condition_completedFirstDelivery(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kara_corlon_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kara_corlon_action_giveThirdDelivery(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kara_corlon_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kara_corlon_action_giveSecondDelivery(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kara_corlon_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kara_corlon_action_giveFirstDelivery(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kara_corlon_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kara_corlon_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (ep3_etyyy_kara_corlon_condition_alreadyHasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                ep3_etyyy_kara_corlon_action_giveFirstDelivery(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_etyyy_kara_corlon");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.ep3_etyyy_kara_corlon");
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
        if (ep3_etyyy_kara_corlon_condition_karaKnowsYou(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kara_corlon_condition_workingOnDeliveries(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_kara_corlon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_78");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kara_corlon_condition_talkToKara(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kara_corlon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId", 14);
                npcStartConversation(player, npc, "ep3_etyyy_kara_corlon", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_kara_corlon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
        if (branchId == 1 && ep3_etyyy_kara_corlon_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_etyyy_kara_corlon_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_kara_corlon_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_etyyy_kara_corlon_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_etyyy_kara_corlon_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_etyyy_kara_corlon_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_kara_corlon.branchId");
        return SCRIPT_CONTINUE;
    }
}
