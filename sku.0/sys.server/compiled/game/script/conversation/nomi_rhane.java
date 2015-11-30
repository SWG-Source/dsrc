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

public class nomi_rhane extends script.base_script
{
    public nomi_rhane()
    {
    }
    public static String c_stringFile = "conversation/nomi_rhane";
    public boolean nomi_rhane_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nomi_rhane_condition_isEnrouteToSurvivors(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "outbreak_quest_facility_04", "speakNomi");
    }
    public boolean nomi_rhane_condition_hasntLeft(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "outbreak_quest_facility_05_imperial") || groundquests.isQuestActive(player, "outbreak_quest_facility_05_rebel") || groundquests.isQuestActive(player, "outbreak_quest_facility_05_neutral")) || (groundquests.hasCompletedQuest(player, "outbreak_quest_facility_04"));
    }
    public boolean nomi_rhane_condition_hasCompleted4Del5(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.isQuestActive(player, "outbreak_quest_facility_05_imperial") && !groundquests.isQuestActive(player, "outbreak_quest_facility_05_rebel") && !groundquests.isQuestActive(player, "outbreak_quest_facility_05_neutral") && groundquests.hasCompletedQuest(player, "outbreak_quest_facility_04"));
    }
    public void nomi_rhane_action_spokenNomi(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.hasCompletedQuest(player, "outbreak_quest_facility_04"))
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() the player has not completed the outbreak_quest_facility_04 quest. Sending appropriate signal to complete quest. Player: " + player);
            groundquests.sendSignal(player, "hasSpokenNomi");
        }
        if (!groundquests.isQuestActiveOrComplete(player, "outbreak_quest_facility_05_imperial") && !groundquests.isQuestActiveOrComplete(player, "outbreak_quest_facility_05_rebel") && !groundquests.isQuestActiveOrComplete(player, "outbreak_quest_facility_05_neutral"))
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() the player has found never received and not completed the next factional mission. Giving quest to player: " + player);
            String faction = "";
            String questPrefix = "outbreak_quest_facility_05_";
            if (groundquests.hasCompletedQuest(player, "outbreak_quest_02_imperial"))
            {
                CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() the player is receiving the imperial faction quest: outbreak_quest_facility_05_imperial. Player: " + player);
                faction = "imperial";
            }
            else if (groundquests.hasCompletedQuest(player, "outbreak_quest_02_rebel"))
            {
                CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() the player is receiving the rebel faction quest: outbreak_quest_facility_05_rebel. Player: " + player);
                faction = "rebel";
            }
            else 
            {
                CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() the player is receiving the neutral faction quest: outbreak_quest_facility_05_neutral. Player: " + player);
                faction = "neutral";
            }
            if (faction == null || faction.length() <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() Previous faction quest was not found for player: " + player + " (" + getPlayerName(player) + "). This may be due to the player somehow having a previous quest removed.");
                faction = "neutral";
            }
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() a message handler is being sent to reward the player with the correct quest. Player: " + player);
            String questString = questPrefix + faction;
            dictionary params = new dictionary();
            params.put("faction_quest", questString);
            params.put("player", player);
            messageTo(npc, "rewardPlayerFactionalQuest", params, 1, true);
        }
        else 
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.completeQuest() Player has already received or completed quest. No update or quest needed. Player: " + player);
        }
    }
    public int nomi_rhane_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            if (nomi_rhane_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nomi_rhane_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.nomi_rhane.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nomi_rhane.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nomi_rhane_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (nomi_rhane_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nomi_rhane_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                    }
                    utils.setScriptVar(player, "conversation.nomi_rhane.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nomi_rhane.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nomi_rhane_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (nomi_rhane_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nomi_rhane_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    utils.setScriptVar(player, "conversation.nomi_rhane.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nomi_rhane.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nomi_rhane_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (nomi_rhane_condition__defaultCondition(player, npc))
            {
                nomi_rhane_action_spokenNomi(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.nomi_rhane.branchId");
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
            detachScript(self, "conversation.nomi_rhane");
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
        detachScript(self, "conversation.nomi_rhane");
        return SCRIPT_CONTINUE;
    }
    public int rewardPlayerFactionalQuest(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.rewardPlayerFactionalQuest() messageHandler received no data. Catestrophic error.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.rewardPlayerFactionalQuest() messageHandler received data but without critical PLAYER key. Catestrophic error.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.rewardPlayerFactionalQuest() messageHandler received data but Player is no longer valid or no longer exists. Catestrophic error for player: " + player + ".");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("faction_quest"))
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.rewardPlayerFactionalQuest() messageHandler received data but without critical faction key. Catestrophic error for player: " + player + ".");
            return SCRIPT_CONTINUE;
        }
        String factionQuest = params.getString("faction_quest");
        if (factionQuest == null || factionQuest.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.rewardPlayerFactionalQuest() messageHandler received data but faction quest is not valid. Catestrophic error for player: " + player);
            return SCRIPT_CONTINUE;
        }
        groundquests.requestGrantQuest(player, factionQuest);
        CustomerServiceLog("outbreak_themepark", "nomi_rhane conversation script.rewardPlayerFactionalQuest() messageHandler requesting that player: " + player + " be granted quest: " + factionQuest);
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
        if (nomi_rhane_condition_isEnrouteToSurvivors(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nomi_rhane_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                utils.setScriptVar(player, "conversation.nomi_rhane.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "nomi_rhane", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (nomi_rhane_condition_hasCompleted4Del5(player, npc))
        {
            nomi_rhane_action_spokenNomi(player, npc);
            string_id message = new string_id(c_stringFile, "s_25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nomi_rhane_condition_hasntLeft(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nomi_rhane_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nomi_rhane"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nomi_rhane.branchId");
        if (branchId == 1 && nomi_rhane_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && nomi_rhane_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && nomi_rhane_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && nomi_rhane_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nomi_rhane.branchId");
        return SCRIPT_CONTINUE;
    }
}
