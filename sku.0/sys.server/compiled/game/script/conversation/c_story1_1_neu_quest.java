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

public class c_story1_1_neu_quest extends script.base_script
{
    public c_story1_1_neu_quest()
    {
    }
    public static String c_stringFile = "conversation/c_story1_1_neu_quest";
    public boolean c_story1_1_neu_quest_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_1_neu_quest_condition_playerStartedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        boolean onQuest = (questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player)) || (questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return onQuest;
    }
    public boolean c_story1_1_neu_quest_condition_playerIsReb(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_story1_1_neu_quest_condition_questActivePostData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int unlock = groundquests.getTaskId(questId1, "unlock");
        boolean onQuest = (questIsQuestActive(questId1, player) && questIsTaskComplete(questId1, unlock, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return onQuest;
    }
    public boolean c_story1_1_neu_quest_condition_questActivePreData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int unlock = groundquests.getTaskId(questId1, "unlock");
        boolean onQuest = (questIsQuestActive(questId1, player) && !questIsTaskComplete(questId1, unlock, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return onQuest;
    }
    public boolean c_story1_1_neu_quest_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int cliffhanger = groundquests.getTaskId(questId, "cliffhanger");
        return (questIsQuestComplete(questId, player) || questIsTaskActive(questId, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
    }
    public boolean c_story1_1_neu_quest_condition_foundData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int unlock = groundquests.getTaskId(questId1, "unlock");
        boolean onQuest = false;
        onQuest = questIsTaskActive(questId1, unlock, player) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return onQuest;
    }
    public boolean c_story1_1_neu_quest_condition_playerIsImp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_story1_1_neu_quest_condition_notFoundData(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int search = groundquests.getTaskId(questId1, "search");
        boolean onQuest = false;
        onQuest = questIsTaskActive(questId1, search, player) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return onQuest;
    }
    public boolean c_story1_1_neu_quest_condition_completeds1Imp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_imp");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean quest1Complete = (questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return quest1Complete;
    }
    public boolean c_story1_1_neu_quest_condition_completedS1Neu(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean quest1Complete = (questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return quest1Complete;
    }
    public boolean c_story1_1_neu_quest_condition_completedS1Reb(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_reb");
        int questId2 = questGetQuestId("quest/c_story1_2_neu");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean quest1Complete = (questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player)) && !(questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player));
        return quest1Complete;
    }
    public boolean c_story1_1_neu_quest_condition_completedS1NewFacImp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean Quest1Complete = questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player);
        return Quest1Complete;
    }
    public boolean c_story1_1_neu_quest_condition_completedS1NewFacReb(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_story1_1_neu");
        int cliffhanger = groundquests.getTaskId(questId1, "cliffhanger");
        boolean Quest1Complete = questIsQuestComplete(questId1, player) || questIsTaskActive(questId1, cliffhanger, player);
        return Quest1Complete;
    }
    public boolean c_story1_1_neu_quest_condition_quest2Active(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((groundquests.isTaskActive(player, "c_story1_2_neu", "cliff2") || groundquests.hasCompletedQuest(player, "c_story1_2_neu")) && (!groundquests.hasCompletedQuest(player, "c_story1_3_neu")))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean c_story1_1_neu_quest_condition_questDisabled(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_1_neu_quest_condition_quest3Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "c_story1_3_neu"));
    }
    public boolean c_story1_1_neu_quest_condition_quest3Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "c_story1_3_neu") && (!groundquests.hasCompletedQuest(player, "c_story1_4a") && !groundquests.isQuestActive(player, "c_story1_4a"));
    }
    public boolean c_story1_1_neu_quest_condition_onTaskReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_story1_3_neu", "return"));
    }
    public void c_story1_1_neu_quest_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_story1_1_neu");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_story1_1_neu_quest_action_unlockData(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_1_neu_unlockeddata");
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_neu.crash");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_neu.crash");
        obj_id remwaypoint1 = getObjIdObjVar(player, "quest.general.quest/c_story1_1_neu.crash");
        if (remwaypoint1 != null)
        {
            destroyWaypointInDatapad(remwaypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_neu.crash");
    }
    public void c_story1_1_neu_quest_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_story1_1_neu_quest_action_giveCrashWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_neu.crash");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_neu.crash");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 0;
        loc.y = 0;
        loc.z = 2012;
        loc.area = "rori";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_story1_1_neu.crash", waypoint);
        setWaypointName(waypoint, "Missing Pilot Coordinates");
    }
    public void c_story1_1_neu_quest_action_giveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_1_imp_cliffhanger");
        groundquests.sendSignal(player, "c_story1_1_neu_cliffhanger");
        groundquests.sendSignal(player, "c_story1_1_reb_cliffhanger");
        int questId = questGetQuestId("quest/c_story1_2_neu");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id remwaypoint = getObjIdObjVar(player, "quest.general.quest/c_story1_1_neu.corpse");
        if (remwaypoint != null)
        {
            destroyWaypointInDatapad(remwaypoint, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_neu.corpse");
        obj_id remwaypoint1 = getObjIdObjVar(player, "quest.general.quest/c_story1_1_neu.neutralQG");
        if (remwaypoint1 != null)
        {
            destroyWaypointInDatapad(remwaypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_story1_1_neu.neutralQG");
    }
    public void c_story1_1_neu_quest_action_giveQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "c_story1_3_neu");
        groundquests.sendSignal(player, "c_story1_2_neu_cliff2");
    }
    public void c_story1_1_neu_quest_action_removeQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "c_story1_3_neu");
        space_quest.clearQuestFlags(player, "escort", "c_story1_3_space");
    }
    public void c_story1_1_neu_quest_action_signalReward3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returned");
    }
    public void c_story1_1_neu_quest_action_giveQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "c_story1_4a");
    }
    public void c_story1_1_neu_quest_action_signalQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_2_neu_cliff2");
    }
    public int c_story1_1_neu_quest_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_659"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_661");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_667");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_671"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_neu_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_673");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_675"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_677");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_663"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_neu_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_665");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_667"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_669");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_659"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_661");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_667");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_671"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_neu_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_673");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_675"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_677");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_659"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_661");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_667");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_671"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_neu_quest_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_673");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_675"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_677");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_685"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_687");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_689");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_693");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_697"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                c_story1_1_neu_quest_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_699");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_701"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                string_id message = new string_id(c_stringFile, "s_703");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_689"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                c_story1_1_neu_quest_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_691");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_693"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "backhand_threaten");
                string_id message = new string_id(c_stringFile, "s_695");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_707"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_709");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_711"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_715");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_719");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_750"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_752");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_754"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                c_story1_1_neu_quest_action_giveCrashWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_756");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_719"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_723");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_727");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_727"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_731");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_734");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_742");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_734"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                c_story1_1_neu_quest_action_unlockData(player, npc);
                string_id message = new string_id(c_stringFile, "s_738");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_742"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_746");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                c_story1_1_neu_quest_action_giveQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                c_story1_1_neu_quest_action_signalReward3(player, npc);
                string_id message = new string_id(c_stringFile, "s_81");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83"))
        {
            doAnimationAction(player, "nod");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
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
    public int c_story1_1_neu_quest_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_764"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_766");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_768"))
        {
            doAnimationAction(player, "apologize");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                c_story1_1_neu_quest_action_removeQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_770");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
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
    public int c_story1_1_neu_quest_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_774"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                string_id message = new string_id(c_stringFile, "s_776");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_778");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_792");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_796"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                string_id message = new string_id(c_stringFile, "s_798");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_800");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_804");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_806"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_808");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_778"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_782");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_790");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 45);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_792"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_794");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_1_neu_quest_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_782"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_784");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_786");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_790"))
        {
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_story1_1_neu_quest_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_788");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
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
    public int c_story1_1_neu_quest_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_786"))
        {
            doAnimationAction(player, "nod");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_story1_1_neu_quest_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_788");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
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
    public int c_story1_1_neu_quest_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_800"))
        {
            doAnimationAction(player, "dismiss");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_802");
                utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_804"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_782");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_790");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 45);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
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
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_story1_1_neu_quest");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Tyla Jinn");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Tyla Jinn");
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
        detachScript(self, "conversation.c_story1_1_neu_quest");
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
        if (c_story1_1_neu_quest_condition_completedS1NewFacImp(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_649");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_completedS1NewFacReb(player, npc))
        {
            doAnimationAction(npc, "shoo");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_651");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_questDisabled(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_109");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_playerIsImp(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_653");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_playerIsReb(player, npc))
        {
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_655");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_completedS1Neu(player, npc))
        {
            doAnimationAction(npc, "shoo");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_657");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_659");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_671");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_675");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 6);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_completeds1Imp(player, npc))
        {
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_679");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_659");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_671");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_675");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 6);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_completedS1Reb(player, npc))
        {
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_681");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_659");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_671");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_675");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 6);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!c_story1_1_neu_quest_condition_playerStartedQuest(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_683");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_685");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_697");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_701");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 14);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_questActivePreData(player, npc))
        {
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_705");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition_notFoundData(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition_foundData(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_707");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_711");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_750");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 20);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_questActivePostData(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_758");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_quest3Complete(player, npc))
        {
            doAnimationAction(npc, "bow");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_74");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 30);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_1_neu_quest_condition_onTaskReturn(player, npc))
        {
            doAnimationAction(npc, "bow");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_75");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 34);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", null, pp, responses);
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
        if (c_story1_1_neu_quest_condition_quest3Active(player, npc))
        {
            doAnimationAction(npc, "bow");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_762");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_764");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_768");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 40);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", null, pp, responses);
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
        if (c_story1_1_neu_quest_condition_quest2Active(player, npc))
        {
            doAnimationAction(npc, "bow");
            doAnimationAction(player, "nod_head_once");
            c_story1_1_neu_quest_action_signalQuest2(player, npc);
            string_id message = new string_id(c_stringFile, "s_772");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_774");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_796");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_806");
                }
                utils.setScriptVar(player, "conversation.c_story1_1_neu_quest.branchId", 43);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_story1_1_neu_quest", null, pp, responses);
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
        if (c_story1_1_neu_quest_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "implore");
            c_story1_1_neu_quest_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_760");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_story1_1_neu_quest"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
        if (branchId == 6 && c_story1_1_neu_quest_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_story1_1_neu_quest_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && c_story1_1_neu_quest_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_story1_1_neu_quest_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && c_story1_1_neu_quest_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && c_story1_1_neu_quest_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && c_story1_1_neu_quest_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && c_story1_1_neu_quest_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && c_story1_1_neu_quest_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && c_story1_1_neu_quest_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && c_story1_1_neu_quest_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && c_story1_1_neu_quest_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && c_story1_1_neu_quest_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && c_story1_1_neu_quest_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && c_story1_1_neu_quest_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && c_story1_1_neu_quest_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && c_story1_1_neu_quest_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && c_story1_1_neu_quest_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && c_story1_1_neu_quest_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && c_story1_1_neu_quest_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && c_story1_1_neu_quest_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && c_story1_1_neu_quest_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && c_story1_1_neu_quest_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_story1_1_neu_quest.branchId");
        return SCRIPT_CONTINUE;
    }
}
