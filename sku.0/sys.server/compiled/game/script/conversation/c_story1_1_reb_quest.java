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
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class c_story1_1_reb_quest extends script.base_script
{
    public c_story1_1_reb_quest()
    {
    }
    public static String c_stringFile = "conversation/c_story1_1_reb_quest";
    public boolean c_story1_1_reb_quest_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_1_reb_quest_condition_playerStartedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_reb");
        boolean onQuest1 = (questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player)) || (questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player));
        return onQuest1;
    }
    public boolean c_story1_1_reb_quest_condition_playerIsImp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_story1_1_reb_quest_condition_questActivePostData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_reb");
        int unlock = groundquests.getTaskId(questId2, "unlock");
        boolean onQuest1 = (questIsQuestActive(questId2, player) && questIsTaskComplete(questId2, unlock, player)) && !(questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player));
        return onQuest1;
    }
    public boolean c_story1_1_reb_quest_condition_questActivePreData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_reb");
        int unlock = groundquests.getTaskId(questId2, "unlock");
        boolean onQuest1 = (questIsQuestActive(questId2, player) && !questIsTaskComplete(questId2, unlock, player)) && !(questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player));
        return onQuest1;
    }
    public boolean c_story1_1_reb_quest_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_reb");
        int questId = questGetQuestId("quest/c_story1_1_reb");
        int cliffhanger = groundquests.getTaskId(questId, "cliffhanger");
        return (questIsQuestComplete(questId, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player));
    }
    public boolean c_story1_1_reb_quest_condition_foundData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_reb");
        int unlock = groundquests.getTaskId(questId2, "unlock");
        boolean onQuest1 = false;
        onQuest1 = questIsTaskActive(questId2, unlock, player) && !(questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player));
        return onQuest1;
    }
    public boolean c_story1_1_reb_quest_condition_playerIsNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_story1_1_reb_quest_condition_notFoundData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_2_reb");
        int questId2 = questGetQuestId("quest/c_story1_1_reb");
        int search = groundquests.getTaskId(questId2, "search");
        boolean onQuest1 = false;
        onQuest1 = questIsTaskActive(questId2, search, player) && !(questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player));
        return onQuest1;
    }
    public boolean c_story1_1_reb_quest_condition_completedS1Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_imp");
        int questId2 = questGetQuestId("quest/c_story1_2_reb");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean quest1Complete = (questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return quest1Complete;
    }
    public boolean c_story1_1_reb_quest_condition_completedS1Neu(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_reb");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean Quest1Complete = (questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return Quest1Complete;
    }
    public boolean c_story1_1_reb_quest_condition_completedS1Reb(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int questId2 = questGetQuestId("quest/c_story1_2_reb");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean Quest1Complete = (questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return Quest1Complete;
    }
    public boolean c_story1_1_reb_quest_condition_completedS1NewFacImp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean Quest1Complete = questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player);
        return Quest1Complete;
    }
    public boolean c_story1_1_reb_quest_condition_completedS1NewFacNeu(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean Quest1Complete = questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player);
        return Quest1Complete;
    }
    public boolean c_story1_1_reb_quest_condition_quest2Active(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((groundquests.isTaskActive(player, "c_story1_2_reb", "cliff2") || groundquests.hasCompletedQuest(player, "c_story1_2_reb")) && (!groundquests.hasCompletedQuest(player, "c_story1_3_reb")))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_story1_1_reb_quest_condition_quest4ActiveOrComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "c_story1_4a") || groundquests.isQuestActive(player, "c_story1_4a"));
    }
    public boolean c_story1_1_reb_quest_condition_quest3Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "c_story1_3_reb"));
    }
    public boolean c_story1_1_reb_quest_condition_quest3Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "c_story1_3_reb") && (!groundquests.hasCompletedQuest(player, "c_story1_4a") && !groundquests.isQuestActive(player, "c_story1_4a"));
    }
    public boolean c_story1_1_reb_quest_condition_onTaskReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_story1_3_reb", "return"));
    }
    public void c_story1_1_reb_quest_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_story1_1_reb");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_story1_1_reb_quest_action_unlockData(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_1_reb_unlockeddata");
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_reb.crash");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_reb.crash");
        obj_id remwaypoint1 = getObjIdObjVar(player, "quest.general.quest/c_story1_1_reb.rebelQG");
        if (remwaypoint1 != null)
        {
            destroyWaypointInDatapad(remwaypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_reb.rebelQG");
    }
    public void c_story1_1_reb_quest_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_story1_1_reb_quest_action_giveCrashWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_reb.crash");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_reb.crash");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 0;
        loc.y = 0;
        loc.z = 2012;
        loc.area = "rori";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_story1_1_reb.crash", waypoint);
        setWaypointName(waypoint, "Missing Pilot Coordinates");
        setWaypointActive(waypoint, true);
    }
    public void c_story1_1_reb_quest_action_giveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_1_imp_cliffhanger");
        groundquests.sendSignal(player, "c_story1_1_neu_cliffhanger");
        groundquests.sendSignal(player, "c_story1_1_reb_cliffhanger");
        int questId = questGetQuestId("quest/c_story1_2_reb");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_reb.corpse");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_reb.corpse");
        obj_id remwaypoint1 = getObjIdObjVar(player, "quest.general.quest/c_story1_1_reb.rebelQG");
        if (remwaypoint1 != null)
        {
            destroyWaypointInDatapad(remwaypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_reb.rebelQG");
    }
    public void c_story1_1_reb_quest_action_giveQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "c_story1_3_reb");
    }
    public void c_story1_1_reb_quest_action_removeQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "c_story1_3_reb");
        space_quest.clearQuestFlags(player, "escort", "c_story1_3_space");
    }
    public void c_story1_1_reb_quest_action_signalReward3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returned");
    }
    public void c_story1_1_reb_quest_action_giveQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "c_story1_4a");
    }
    public void c_story1_1_reb_quest_action_completeQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_2_reb_cliff2");
    }
    public int c_story1_1_reb_quest_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_824"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_826");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_828");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_832");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_836"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_838");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_840"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_842");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_828"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_830");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_832"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_834");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_824"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_826");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_828");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_832");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_836"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_838");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_840"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_842");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_824"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_826");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_828");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_832");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_836"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_838");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_840"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_842");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_850"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_852");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_854");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_858");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_862"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_864");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_866"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                string_id message = new string_id(c_stringFile, "s_868");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_854"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_856");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_858"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_860");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_872"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_874");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_876"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_878");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_880");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_892");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_896");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_900"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_902");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_904"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveCrashWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_906");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_880"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_882");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_884");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_892"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_unlockData(player, npc);
                string_id message = new string_id(c_stringFile, "s_894");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_896"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_898");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_884"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_886");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_888");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_888"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_unlockData(player, npc);
                string_id message = new string_id(c_stringFile, "s_890");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                c_story1_1_reb_quest_action_giveQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                c_story1_1_reb_quest_action_signalReward3(player, npc);
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            doAnimationAction(player, "nod");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_912"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_914");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_916"))
        {
            doAnimationAction(player, "apologize");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_story1_1_reb_quest_action_removeQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_918");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_922"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_924");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_926");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_930");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_938"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_940");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_942");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_946");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 49);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_948"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_950");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_926"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_928");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_930"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_932");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_934");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_934"))
        {
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_reb_quest_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_936");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_reb_quest_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_942"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_944");
                utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_946"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_932");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_934");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_story1_1_reb_quest");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Veega Madish");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Veega Madish");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.c_story1_1_reb_quest");
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
        if (c_story1_1_reb_quest_condition_completedS1NewFacImp(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_814");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_completedS1NewFacNeu(player, npc))
        {
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_816");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_playerIsNeutral(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_818");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_playerIsImp(player, npc))
        {
            doAnimationAction(npc, "shoo");
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_820");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_completedS1Reb(player, npc))
        {
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_822");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_824");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_836");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_840");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 5);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_completedS1Imp(player, npc))
        {
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_844");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_824");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_836");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_840");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 5);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_completedS1Neu(player, npc))
        {
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_846");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_824");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_836");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_840");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 5);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!c_story1_1_reb_quest_condition_playerStartedQuest(player, npc))
        {
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_848");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_850");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_862");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_866");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 13);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_questActivePreData(player, npc))
        {
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_870");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!c_story1_1_reb_quest_condition_foundData(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition_foundData(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_872");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_876");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_900");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_904");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 19);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_questActivePostData(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_908");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_quest3Complete(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_74");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 30);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_onTaskReturn(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_73");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 36);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_reb_quest_condition_quest3Active(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_910");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_912");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_916");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 41);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", null, pp, responses);
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
        if (c_story1_1_reb_quest_condition_quest2Active(player, npc))
        {
            doAnimationAction(npc, "greet");
            c_story1_1_reb_quest_action_completeQuest2(player, npc);
            string_id message = new string_id(c_stringFile, "s_920");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_922");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_938");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_948");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_reb_quest.branchId", 44);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_story1_1_reb_quest", null, pp, responses);
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
        if (c_story1_1_reb_quest_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "salute2");
            c_story1_1_reb_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_952");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_story1_1_reb_quest"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
        if (branchId == 5 && c_story1_1_reb_quest_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_story1_1_reb_quest_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && c_story1_1_reb_quest_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && c_story1_1_reb_quest_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_story1_1_reb_quest_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && c_story1_1_reb_quest_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && c_story1_1_reb_quest_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && c_story1_1_reb_quest_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && c_story1_1_reb_quest_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && c_story1_1_reb_quest_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && c_story1_1_reb_quest_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && c_story1_1_reb_quest_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && c_story1_1_reb_quest_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && c_story1_1_reb_quest_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && c_story1_1_reb_quest_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && c_story1_1_reb_quest_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && c_story1_1_reb_quest_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && c_story1_1_reb_quest_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && c_story1_1_reb_quest_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && c_story1_1_reb_quest_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && c_story1_1_reb_quest_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && c_story1_1_reb_quest_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && c_story1_1_reb_quest_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_story1_1_reb_quest.branchId");
        return SCRIPT_CONTINUE;
    }
}
