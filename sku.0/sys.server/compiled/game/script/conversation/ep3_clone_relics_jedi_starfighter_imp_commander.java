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
import script.library.factions;
import script.library.groundquests;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class ep3_clone_relics_jedi_starfighter_imp_commander extends script.base_script
{
    public ep3_clone_relics_jedi_starfighter_imp_commander()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_jedi_starfighter_imp_commander";
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_onTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_jedi_starfighter_1", "talkToCommander"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_isWookie(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getSpecies(player) == SPECIES_WOOKIEE);
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_jedi_starfighter_1"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_jedi_starfighter_2"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_jedi_starfighter_2", "attackDefeated"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_jedi_starfighter_3"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_jedi_starfighter_1"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_jedi_starfighter_3"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_jedi_starfighter_4"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_jedi_starfighter_4"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasObjVar(player, "gm"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_clone_relics_jedi_starfighter_1_failed");
    }
    public boolean ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestTwoFinal(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_clone_relics_jedi_starfighter_2"));
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_signalGetAtst(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkToOfficer");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_clone_relics_jedi_starfighter_3");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_1");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_grantGroundQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_clone_relics_jedi_starfighter_4");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_signalAfterAttack(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToKrieg");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_clearQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_3");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_clone_relics_jedi_starfighter_2");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_2");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_3");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_4");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuestAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_1");
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_2");
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_3");
        groundquests.clearQuest(player, "ep3_clone_relics_jedi_starfighter_4");
        space_quest.clearQuestFlags(player, "space_battle", "ep3_clone_relics_jedi_starfighter_4");
        space_quest.clearQuestFlags(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuestSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "space_battle", "ep3_clone_relics_jedi_starfighter_4");
        space_quest.clearQuestFlags(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5");
    }
    public void ep3_clone_relics_jedi_starfighter_imp_commander_action_clearFailAndReGrantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "cloneRelics_jedistarfighter_failedEscort");
        groundquests.grantQuest(player, "ep3_clone_relics_jedi_starfighter_1");
        groundquests.sendSignal(player, "talkToOfficer");
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1111"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_940"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_942");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1109"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1107"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_948"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_950");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_952");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_956");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1105"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_952"))
        {
            doAnimationAction(player, "salute2");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantGroundQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_954");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_956"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantGroundQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_958");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_962"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_964");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_966"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_clearQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_968");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1103"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_112"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_972"))
        {
            doAnimationAction(player, "hair_flip");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_974");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_976");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_980");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1101"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_976"))
        {
            doAnimationAction(player, "shake_head_no");
            ep3_clone_relics_jedi_starfighter_imp_commander_action_signalAfterAttack(player, npc);
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_978");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_980"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_982");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_984");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_988");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_984"))
        {
            doAnimationAction(player, "salute2");
            ep3_clone_relics_jedi_starfighter_imp_commander_action_signalAfterAttack(player, npc);
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_986");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_988"))
        {
            doAnimationAction(player, "shake_head_no");
            ep3_clone_relics_jedi_starfighter_imp_commander_action_signalAfterAttack(player, npc);
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_990");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1099"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_996"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                string_id message = new string_id(c_stringFile, "s_998");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1000");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1004"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_1006");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1008");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1010");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1097"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1000"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                doAnimationAction(player, "salute1");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1002");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1008"))
        {
            doAnimationAction(player, "nod");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                string_id message = new string_id(c_stringFile, "s_998");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1000");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1010"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_1012");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_clearFailAndReGrantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1016"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1018");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1020");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1095"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1020"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shush");
                string_id message = new string_id(c_stringFile, "s_1022");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1024");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1024"))
        {
            doAnimationAction(player, "tap_foot");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_1026");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1028");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1072");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1028"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_1030");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1032");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1052");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1072"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_1074");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1076");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1032"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1034");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1036");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1048");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1052"))
        {
            doAnimationAction(player, "salute2");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_1054");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1056");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1058");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1036"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1038");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1040");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1044");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1048"))
        {
            doAnimationAction(player, "salute2");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_signalGetAtst(player, npc);
                string_id message = new string_id(c_stringFile, "s_1050");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1040"))
        {
            doAnimationAction(player, "cuckoo");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1042");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1044"))
        {
            doAnimationAction(player, "salute2");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_signalGetAtst(player, npc);
                string_id message = new string_id(c_stringFile, "s_1046");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1056"))
        {
            doAnimationAction(player, "salute1");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1034");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1036");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1048");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1058"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_1060");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1062");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1066");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1062"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1064");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1066"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_1068");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1070");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1070"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1034");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1036");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1048");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1076"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_1030");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1032");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1052");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1080"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_1082");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1084"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_1086");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1093"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1113");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1116");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1117");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1127");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1113"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1118");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1114"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_1120");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1115"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_1122");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1116"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_1124");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1117"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuestSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_1126");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1127"))
        {
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_imp_commander_action_removeQuestAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1129");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1090"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_1092");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_admiral_krieg"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_admiral_krieg"));
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
        detachScript(self, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander");
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
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedSpaceQuest(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_936");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1111");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", null, pp, responses);
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
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestFour(player, npc))
        {
            doAnimationAction(npc, "nervous");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_938");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_940");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1109");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 2);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestFour(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_944");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 4);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestThree(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_946");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_948");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1105");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 5);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestThree(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_960");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_962");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_966");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1103");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 9);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestTwoFinal(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_110");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 12);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_970");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_972");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1101");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 15);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestTwo(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_992");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 21);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_hasCompletedQuestOne(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_994");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_996");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1004");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1097");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 22);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_failedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_104");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 27);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_onTaskOne(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_1014");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1016");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1095");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 29);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_onQuestOne(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_1078");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1080");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1084");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1093");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 44);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_1088");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_imp_commander_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1090");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId", 54);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_imp_commander", message, responses);
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
        if (!conversationId.equals("ep3_clone_relics_jedi_starfighter_imp_commander"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
        if (branchId == 1 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && ep3_clone_relics_jedi_starfighter_imp_commander_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_imp_commander.branchId");
        return SCRIPT_CONTINUE;
    }
}
