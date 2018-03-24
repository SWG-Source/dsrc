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
import script.library.features;
import script.library.groundquests;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;

public class greeter_coronet_merchant extends script.base_script
{
    public greeter_coronet_merchant()
    {
    }
    public static String c_stringFile = "conversation/greeter_coronet_merchant";
    public boolean greeter_coronet_merchant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean greeter_coronet_merchant_condition_getInvoiceAndSchedule(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_getSchedule") && (groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_getInvoice") || groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftTool") || groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftedTool")))
        {
            return true;
        }
        return false;
    }
    public boolean greeter_coronet_merchant_condition_getSchedule(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_getSchedule") && groundquests.hasCompletedTask(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftedTool"))
        {
            return true;
        }
        return false;
    }
    public boolean greeter_coronet_merchant_condition_getInvoice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "corellia_coronet_find_missing_shipment", "ralMundi_getSchedule") && (groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_getInvoice") || groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftTool") || groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftedTool")))
        {
            return true;
        }
        return false;
    }
    public boolean greeter_coronet_merchant_condition_faliedSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_missing_shipment_intro") && !(groundquests.isQuestActiveOrComplete(player, "corellia_coronet_capitol_problems_missing_shipment")));
    }
    public boolean greeter_coronet_merchant_condition_playerHasShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_utils.hasShip(player);
    }
    public boolean greeter_coronet_merchant_condition_gotBothInvoiceAndSchedule(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_gotScheduleAndInvoice") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment_intro", "ralMundi_gotScheduleAndInvoice");
    }
    public boolean greeter_coronet_merchant_condition_sawRagtagFighters(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_traceTransportRoute");
    }
    public boolean greeter_coronet_merchant_condition_failedSpacePatrol(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_failed_patrol");
    }
    public boolean greeter_coronet_merchant_condition_foundTomiBunker(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_defeatTomi") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_defeatTomi");
    }
    public boolean greeter_coronet_merchant_condition_intimidateRagtags(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_intimidateRagtags") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_intimidateRagtags");
    }
    public boolean greeter_coronet_merchant_condition_goGetShipment(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_shipmentAtCapitol") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_shipmentAtCapitol");
    }
    public boolean greeter_coronet_merchant_condition_foundShipment(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_shipmentFound") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_shipmentFound");
    }
    public boolean greeter_coronet_merchant_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_coronet_find_missing_shipment") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "ralMundi_reportToJasper") || groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_missing_shipment");
    }
    public boolean greeter_coronet_merchant_condition_hasSpacePatrol(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment", "givespacequest");
    }
    public boolean greeter_coronet_merchant_condition_hasOtherSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean greeter_coronet_merchant_condition_notOnMissingShipment(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestDisabled("corellia_coronet_capitol_problems_missing_shipment") || (!groundquests.isQuestActive(player, "corellia_coronet_capitol_problems_missing_shipment") && !groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_missing_shipment") && !groundquests.isQuestActive(player, "corellia_coronet_capitol_problems_missing_shipment_intro") && !groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_missing_shipment_intro"));
    }
    public void greeter_coronet_merchant_action_beginMissingShipment(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_begin");
    }
    public void greeter_coronet_merchant_action_givePatrolMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_coronet_capitol_problems_missing_shipment");
    }
    public void greeter_coronet_merchant_action_goAfterRagtags(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_traceTransportRoute");
    }
    public void greeter_coronet_merchant_action_endQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_shipmentFound");
    }
    public void greeter_coronet_merchant_action_signalPatrolMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_gotScheduleAndInvoice");
    }
    public void greeter_coronet_merchant_action_giveShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantNewbieShip(player, "neutral");
    }
    public void greeter_coronet_merchant_action_clearFailedPatrol(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "failed_ral");
    }
    public int greeter_coronet_merchant_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_125"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_endQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_126");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_114"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_goAfterRagtags(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_givePatrolMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (!greeter_coronet_merchant_condition_playerHasShip(player, npc))
            {
                greeter_coronet_merchant_action_giveShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_givePatrolMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            if (greeter_coronet_merchant_condition_hasOtherSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!greeter_coronet_merchant_condition_playerHasShip(player, npc))
            {
                greeter_coronet_merchant_action_giveShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_givePatrolMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (greeter_coronet_merchant_condition_hasOtherSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!greeter_coronet_merchant_condition_playerHasShip(player, npc))
            {
                greeter_coronet_merchant_action_giveShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_givePatrolMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80e2ba26"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_82cd91e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d48167e1");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d48167e1"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                string_id message = new string_id(c_stringFile, "s_8ae971c3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_91");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_coronet_merchant_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                greeter_coronet_merchant_action_beginMissingShipment(player, npc);
                string_id message = new string_id(c_stringFile, "s_98");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_97"))
        {
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
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
            detachScript(self, "conversation.greeter_coronet_merchant");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.greeter_coronet_merchant");
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
        if (greeter_coronet_merchant_condition_notOnMissingShipment(player, npc))
        {
            doAnimationAction(npc, "laugh_titter");
            doAnimationAction(player, "shrug_shoulders");
            string_id message = new string_id(c_stringFile, "s_198d9a41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_questComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_156");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_foundShipment(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_124");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                }
                utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 3);
                npcStartConversation(player, npc, "greeter_coronet_merchant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_goGetShipment(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_123");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_foundTomiBunker(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_122");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_intimidateRagtags(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_155");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_sawRagtagFighters(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_109");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 8);
                npcStartConversation(player, npc, "greeter_coronet_merchant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_failedSpacePatrol(player, npc))
        {
            greeter_coronet_merchant_action_clearFailedPatrol(player, npc);
            string_id message = new string_id(c_stringFile, "s_108");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 13);
                npcStartConversation(player, npc, "greeter_coronet_merchant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_hasSpacePatrol(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_159");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_faliedSpaceQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 17);
                npcStartConversation(player, npc, "greeter_coronet_merchant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_gotBothInvoiceAndSchedule(player, npc))
        {
            greeter_coronet_merchant_action_signalPatrolMission(player, npc);
            string_id message = new string_id(c_stringFile, "s_103");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 20);
                npcStartConversation(player, npc, "greeter_coronet_merchant", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_getSchedule(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_getInvoice(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_102");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition_getInvoiceAndSchedule(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_101");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_4a019574");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_coronet_merchant_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80e2ba26");
                }
                utils.setScriptVar(player, "conversation.greeter_coronet_merchant.branchId", 28);
                npcStartConversation(player, npc, "greeter_coronet_merchant", message, responses);
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
        if (!conversationId.equals("greeter_coronet_merchant"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
        if (branchId == 3 && greeter_coronet_merchant_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && greeter_coronet_merchant_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && greeter_coronet_merchant_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && greeter_coronet_merchant_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && greeter_coronet_merchant_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && greeter_coronet_merchant_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && greeter_coronet_merchant_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && greeter_coronet_merchant_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && greeter_coronet_merchant_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && greeter_coronet_merchant_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && greeter_coronet_merchant_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && greeter_coronet_merchant_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && greeter_coronet_merchant_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.greeter_coronet_merchant.branchId");
        return SCRIPT_CONTINUE;
    }
}
