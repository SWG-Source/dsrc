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
import script.library.utils;

public class generic_broker_5 extends script.base_script
{
    public generic_broker_5()
    {
    }
    public static String c_stringFile = "conversation/generic_broker_5";
    public boolean generic_broker_5_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean generic_broker_5_condition_isNonSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getPlayerProfession(player) != utils.SMUGGLER)
        {
            return true;
        }
        return false;
    }
    public boolean generic_broker_5_condition_isDoingMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isDoingSmugglerMission(player))
        {
            return true;
        }
        return false;
    }
    public boolean generic_broker_5_condition_missionTierRequirement(obj_id player, obj_id npc) throws InterruptedException
    {
        float underworldFaction = factions.getFactionStanding(player, "underworld");
        if (smuggler.getSmuggleTier(underworldFaction) == 5)
        {
            return true;
        }
        return false;
    }
    public boolean generic_broker_5_condition_hasMissionFunds(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.hasFunds(player, money.MT_TOTAL, smuggler.TIER_5_GENERIC_FRONT_COST))
        {
            return true;
        }
        return false;
    }
    public boolean generic_broker_5_condition_hasSomeContraband(obj_id player, obj_id npc) throws InterruptedException
    {
        if (smuggler.hasIllicitContraband(player))
        {
            return true;
        }
        return false;
    }
    public boolean generic_broker_5_condition_hasMissionContraband(obj_id player, obj_id npc) throws InterruptedException
    {
        if (smuggler.hasIllicitMissionContraband(player))
        {
            return true;
        }
        return false;
    }
    public boolean generic_broker_5_condition_hasPvpMissionFunds(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.hasFunds(player, money.MT_TOTAL, smuggler.TIER_5_GENERIC_PVP_FRONT_COST))
        {
            return true;
        }
        return false;
    }
    public void generic_broker_5_action_startGiveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        money.requestPayment(player, npc, smuggler.TIER_5_GENERIC_FRONT_COST, "none", null, true);
        groundquests.requestGrantQuest(player, "quest/smuggle_generic_5", true);
    }
    public void generic_broker_5_action_givePointerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        float underworldFaction = factions.getFactionStanding(player, "underworld");
        int missionTier = smuggler.getSmuggleTier(underworldFaction);
        String pointerQuest = "";
        switch (missionTier)
        {
            case 1:
            pointerQuest = "quest/smuggle_pointer_generic_1";
            break;
            case 2:
            pointerQuest = "quest/smuggle_pointer_generic_2";
            break;
            case 3:
            pointerQuest = "quest/smuggle_pointer_generic_3";
            break;
            case 4:
            pointerQuest = "quest/smuggle_pointer_generic_4";
            break;
            case 5:
            pointerQuest = "quest/smuggle_pointer_generic_5";
            break;
            default:
            break;
        }
        if (!groundquests.isQuestActive(player, pointerQuest))
        {
            groundquests.requestGrantQuest(player, pointerQuest, true);
        }
    }
    public void generic_broker_5_action_giveIllicitPointerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        float underworldFaction = factions.getFactionStanding(player, "underworld");
        int smuggleTier = smuggler.getSmuggleTier(underworldFaction);
        String illicitContraband = smuggler.getIllicitMissionContrabandSmugglerTier(player, smuggleTier);
        int illicitTier = smuggler.getIllicitContrabandTier(illicitContraband);
        String pointerQuest = "";
        switch (illicitTier)
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
    public void generic_broker_5_action_startGivePvpQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.hasFunds(player, money.MT_TOTAL, smuggler.TIER_5_GENERIC_PVP_FRONT_COST))
        {
            money.requestPayment(player, npc, smuggler.TIER_5_GENERIC_PVP_FRONT_COST, "none", null, true);
            groundquests.requestGrantQuest(player, "quest/smuggle_pvp_5", true);
            int mission_bounty = 20000;
            int current_bounty = 0;
            mission_bounty += rand(1, 2000);
            if (hasObjVar(player, "bounty.amount"))
            {
                current_bounty = getIntObjVar(player, "bounty.amount");
            }
            current_bounty += mission_bounty;
            setObjVar(player, "bounty.amount", current_bounty);
            setObjVar(player, "smuggler.bounty", mission_bounty);
            setJediBountyValue(player, current_bounty);
            updateJediScriptData(player, "smuggler", 1);
        }
    }
    public int generic_broker_5_tokenDI_frontMoney(obj_id player, obj_id npc) throws InterruptedException
    {
        return (smuggler.TIER_5_GENERIC_FRONT_COST);
    }
    public int generic_broker_5_tokenDI_pvpFrontMoney(obj_id player, obj_id npc) throws InterruptedException
    {
        return (smuggler.TIER_5_GENERIC_PVP_FRONT_COST);
    }
    public int generic_broker_5_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                generic_broker_5_action_givePointerQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_broker_5_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_broker_5_condition_hasMissionFunds(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_broker_5_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.generic_broker_5.branchId", 7);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = generic_broker_5_tokenDI_frontMoney(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = generic_broker_5_tokenDI_frontMoney(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_broker_5_condition_hasPvpMissionFunds(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_broker_5_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    utils.setScriptVar(player, "conversation.generic_broker_5.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = generic_broker_5_tokenDI_pvpFrontMoney(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = generic_broker_5_tokenDI_pvpFrontMoney(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            if (!generic_broker_5_condition_hasMissionContraband(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (generic_broker_5_condition_hasMissionContraband(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_broker_5_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_broker_5_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    utils.setScriptVar(player, "conversation.generic_broker_5.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_broker_5_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            generic_broker_5_action_startGiveQuest(player, npc);
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_broker_5_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            generic_broker_5_action_startGivePvpQuest(player, npc);
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_broker_5_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                generic_broker_5_action_giveIllicitPointerQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
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
            detachScript(self, "conversation.generic_broker_5");
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
        groundquests.sendSignal(player, "russellPointer");
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.generic_broker_5");
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
        if (generic_broker_5_condition_isNonSmuggler(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (generic_broker_5_condition_isDoingMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!generic_broker_5_condition_missionTierRequirement(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (generic_broker_5_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.generic_broker_5.branchId", 3);
                npcStartConversation(player, npc, "generic_broker_5", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (generic_broker_5_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (generic_broker_5_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (generic_broker_5_condition_hasSomeContraband(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                utils.setScriptVar(player, "conversation.generic_broker_5.branchId", 6);
                npcStartConversation(player, npc, "generic_broker_5", message, responses);
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
        if (!conversationId.equals("generic_broker_5"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.generic_broker_5.branchId");
        if (branchId == 3 && generic_broker_5_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && generic_broker_5_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && generic_broker_5_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && generic_broker_5_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && generic_broker_5_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.generic_broker_5.branchId");
        return SCRIPT_CONTINUE;
    }
}
