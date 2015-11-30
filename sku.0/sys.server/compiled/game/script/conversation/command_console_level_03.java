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

public class command_console_level_03 extends script.base_script
{
    public command_console_level_03()
    {
    }
    public static String c_stringFile = "conversation/command_console_level_03";
    public boolean command_console_level_03_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean command_console_level_03_condition_tracingCall(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_04", "griffaxJinComm") || groundquests.isTaskActive(player, "outbreak_quest_facility_04", "traceCall"));
    }
    public boolean command_console_level_03_condition_openingHatch(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_04", "scientistCom") || groundquests.isTaskActive(player, "outbreak_quest_facility_04", "unlockSeal"));
    }
    public boolean command_console_level_03_condition_answeringCall(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_03", "goToNextConsole"));
    }
    public boolean command_console_level_03_condition_hasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_03", "goToNextConsole") || groundquests.isQuestActiveOrComplete(player, "outbreak_quest_facility_04"));
    }
    public boolean command_console_level_03_condition_playerDeletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "outbreak_quest_facility_03") && !groundquests.isQuestActiveOrComplete(player, "outbreak_quest_facility_04");
    }
    public void command_console_level_03_action_answerCall(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "outbreak_quest_facility_03", "goToNextConsole"))
        {
            groundquests.sendSignal(player, "hasFoundNextConsole");
        }
    }
    public void command_console_level_03_action_openHatch(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "outbreak_quest_facility_04", "scientistCom") || groundquests.isTaskActive(player, "outbreak_quest_facility_04", "unlockSeal"))
        {
            groundquests.sendSignal(player, "sealUnlocked");
        }
    }
    public void command_console_level_03_action_tracedCall(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "outbreak_quest_facility_04", "griffaxJinComm") || groundquests.isTaskActive(player, "outbreak_quest_facility_04", "traceCall"))
        {
            groundquests.sendSignal(player, "hasTracedCall");
        }
    }
    public void command_console_level_03_action_getRancorPoint(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "outbreak_undead_rancor_boss_fight");
    }
    public void command_console_level_03_action_fixDeletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "outbreak_quest_facility_04");
    }
    public int command_console_level_03_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                command_console_level_03_action_answerCall(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                command_console_level_03_action_fixDeletedQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_87");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_45"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int command_console_level_03_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                command_console_level_03_action_openHatch(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int command_console_level_03_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int command_console_level_03_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (command_console_level_03_condition_tracingCall(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (command_console_level_03_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                    }
                    utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int command_console_level_03_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                command_console_level_03_action_tracedCall(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int command_console_level_03_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                command_console_level_03_action_getRancorPoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int command_console_level_03_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (command_console_level_03_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
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
            detachScript(self, "conversation.command_console_level_03");
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
        detachScript(self, "conversation.command_console_level_03");
        return SCRIPT_CONTINUE;
    }
    public int rewardPlayerFactionalQuest(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received no data. Catestrophic error.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but without critical PLAYER key. Catestrophic error.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but Player is no longer valid or no longer exists. Catestrophic error for player: " + player + ".");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("faction_quest"))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but without critical faction key. Catestrophic error for player: " + player + ".");
            return SCRIPT_CONTINUE;
        }
        String factionQuest = params.getString("faction_quest");
        if (factionQuest == null || factionQuest.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but faction quest is not valid. Catestrophic error for player: " + player);
            return SCRIPT_CONTINUE;
        }
        groundquests.requestGrantQuest(player, factionQuest);
        CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler requesting that player: " + player + " be granted quest: " + factionQuest);
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
        if (command_console_level_03_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (command_console_level_03_condition_openingHatch(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (command_console_level_03_condition_answeringCall(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (command_console_level_03_condition_playerDeletedQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (command_console_level_03_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                utils.setScriptVar(player, "conversation.command_console_level_03.branchId", 1);
                npcStartConversation(player, npc, "command_console_level_03", message, responses);
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
        if (!conversationId.equals("command_console_level_03"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.command_console_level_03.branchId");
        if (branchId == 1 && command_console_level_03_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && command_console_level_03_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && command_console_level_03_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && command_console_level_03_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && command_console_level_03_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && command_console_level_03_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && command_console_level_03_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.command_console_level_03.branchId");
        return SCRIPT_CONTINUE;
    }
}
