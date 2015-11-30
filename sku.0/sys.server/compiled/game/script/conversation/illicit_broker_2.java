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
import script.library.factions;
import script.library.groundquests;
import script.library.money;
import script.library.smuggler;
import script.library.static_item;
import script.library.utils;

public class illicit_broker_2 extends script.base_script
{
    public illicit_broker_2()
    {
    }
    public static String c_stringFile = "conversation/illicit_broker_2";
    public boolean illicit_broker_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean illicit_broker_2_condition_isNonSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getPlayerProfession(player) != utils.SMUGGLER)
        {
            return true;
        }
        return false;
    }
    public boolean illicit_broker_2_condition_isDoingMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isDoingSmugglerMission(player))
        {
            return true;
        }
        return false;
    }
    public boolean illicit_broker_2_condition_smugglerAboveMissionTier(obj_id player, obj_id npc) throws InterruptedException
    {
        float underworldFaction = factions.getFactionStanding(player, "underworld");
        int brokerTier = 2;
        if (smuggler.getSmuggleTier(underworldFaction) > brokerTier)
        {
            return true;
        }
        return false;
    }
    public boolean illicit_broker_2_condition_hasMissionContrabandBusiness(obj_id player, obj_id npc) throws InterruptedException
    {
        int brokerTier = 2;
        if (smuggler.hasMissionContrabandForTransaction(player, brokerTier))
        {
            return true;
        }
        return false;
    }
    public boolean illicit_broker_2_condition_hasSomeContraband(obj_id player, obj_id npc) throws InterruptedException
    {
        if (smuggler.hasIllicitContraband(player))
        {
            return true;
        }
        return false;
    }
    public boolean illicit_broker_2_condition_hasMissionContraband(obj_id player, obj_id npc) throws InterruptedException
    {
        if (smuggler.hasIllicitMissionContraband(player))
        {
            return true;
        }
        return false;
    }
    public boolean illicit_broker_2_condition_hasMissionContrabandToDeliver(obj_id player, obj_id npc) throws InterruptedException
    {
        float underworldFaction = factions.getFactionStanding(player, "underworld");
        int brokerTier = 2;
        if (smuggler.getSmuggleTier(underworldFaction) == brokerTier)
        {
            return true;
        }
        return false;
    }
    public void illicit_broker_2_action_startGiveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        String illicit_broker_2_tokenTO_contrabandName = utils.getStringScriptVar(player, "illicitContrabandTrade");
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] missionContraband = utils.getAllStaticItemsInPlayerInventory(player, illicit_broker_2_tokenTO_contrabandName);
        int contrabandSize = utils.countOfStackedItemsInArray(missionContraband);
        if (contrabandSize >= 5)
        {
            static_item.decrementUnstackedStaticItemAmount(missionContraband, 5);
            utils.removeScriptVar(player, "illicitContrabandTrade");
            CustomerServiceLog("SMUGGLER_SYSTEM", player + "Received Tier 2 Illicit Contraband mission from " + npc);
            groundquests.requestGrantQuest(player, "quest/smuggle_illicit_2", true);
        }
    }
    public void illicit_broker_2_action_giveIllicitPointerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int missionTier = smuggler.getMissionCotrabandPointerTier(player);
        String pointerQuest = "";
        switch (missionTier)
        {
            case 1:
            pointerQuest = "quest/smuggle_pointer_illicit_1";
            break;
            case 2:
            pointerQuest = "quest/smuggle_pointer_illicit_2";
            break;
            case 3:
            pointerQuest = "quest/smuggle_pointer_illicit_3";
            break;
            case 4:
            pointerQuest = "quest/smuggle_pointer_illicit_4";
            break;
            case 5:
            pointerQuest = "quest/smuggle_pointer_illicit_5";
            break;
            default:
            break;
        }
        if (!groundquests.isQuestActive(player, pointerQuest))
        {
            groundquests.requestGrantQuest(player, pointerQuest, true);
        }
    }
    public void illicit_broker_2_action_sellContraband(obj_id player, obj_id npc) throws InterruptedException
    {
        String illicit_broker_2_tokenTO_contrabandName = utils.getStringScriptVar(player, "illicitContrabandTrade");
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] tradeContraband = utils.getAllStaticItemsInPlayerInventory(player, illicit_broker_2_tokenTO_contrabandName);
        int contrabandSize = utils.countOfStackedItemsInArray(tradeContraband);
        if (contrabandSize >= 5)
        {
            static_item.decrementUnstackedStaticItemAmount(tradeContraband, 5);
            dictionary params = new dictionary();
            money.systemPayout(money.ACCT_RELIC_DEALER, player, smuggler.TIER_2_ILLICIT_SELL_VALUE, null, params);
            utils.removeScriptVar(player, "illicitContrabandTrade");
        }
    }
    public String illicit_broker_2_tokenTO_contrabandName(obj_id player, obj_id npc) throws InterruptedException
    {
        int brokerTier = 2;
        String contraband = smuggler.getMissionContrabandForTransactionName(player, brokerTier);
        if (contraband != null)
        {
            utils.setScriptVar(player, "illicitContrabandTrade", contraband);
            string_id contrabandId = new string_id("static_item_n", contraband);
            String contrabandItemName = getString(contrabandId);
            return contrabandItemName;
        }
        return "";
    }
    public int illicit_broker_2_tokenDI_cashValue(obj_id player, obj_id npc) throws InterruptedException
    {
        return (smuggler.TIER_2_ILLICIT_SELL_VALUE);
    }
    public float illicit_broker_2_tokenDF_tokenDF0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return 0.f;
    }
    public int illicit_broker_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 6);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                    pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                    pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (illicit_broker_2_condition_smugglerAboveMissionTier(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!illicit_broker_2_condition_smugglerAboveMissionTier(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int illicit_broker_2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            illicit_broker_2_action_sellContraband(player, npc);
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int illicit_broker_2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            illicit_broker_2_action_startGiveQuest(player, npc);
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int illicit_broker_2_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 6);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                    pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                    pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (illicit_broker_2_condition_smugglerAboveMissionTier(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!illicit_broker_2_condition_smugglerAboveMissionTier(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int illicit_broker_2_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 6);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                    pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                    pp.digitInteger = illicit_broker_2_tokenDI_cashValue(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (illicit_broker_2_condition_smugglerAboveMissionTier(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!illicit_broker_2_condition_smugglerAboveMissionTier(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (illicit_broker_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int illicit_broker_2_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            illicit_broker_2_action_giveIllicitPointerQuest(player, npc);
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
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
            detachScript(self, "conversation.illicit_broker_2");
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
        groundquests.sendSignal(player, "dalyaPointer");
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.illicit_broker_2");
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
        if (illicit_broker_2_condition_isNonSmuggler(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (illicit_broker_2_condition_isDoingMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!illicit_broker_2_condition_hasSomeContraband(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!illicit_broker_2_condition_hasMissionContraband(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (illicit_broker_2_condition_hasMissionContrabandBusiness(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!illicit_broker_2_condition_hasMissionContrabandToDeliver(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 5);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                npcStartConversation(player, npc, "illicit_broker_2", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(illicit_broker_2_tokenTO_contrabandName(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (illicit_broker_2_condition_hasMissionContraband(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (illicit_broker_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (illicit_broker_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.illicit_broker_2.branchId", 14);
                npcStartConversation(player, npc, "illicit_broker_2", message, responses);
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
        if (!conversationId.equals("illicit_broker_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.illicit_broker_2.branchId");
        if (branchId == 5 && illicit_broker_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && illicit_broker_2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && illicit_broker_2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && illicit_broker_2_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && illicit_broker_2_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && illicit_broker_2_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.illicit_broker_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
