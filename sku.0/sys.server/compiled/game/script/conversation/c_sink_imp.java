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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class c_sink_imp extends script.base_script
{
    public c_sink_imp()
    {
    }
    public static String c_stringFile = "conversation/c_sink_imp";
    public boolean c_sink_imp_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_sink_imp_condition_IsImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        return !groundquests.isQuestActiveOrComplete(player, "c_sink_imp_v2");
    }
    public boolean c_sink_imp_condition_IsRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_sink_imp_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_sink_imp_condition_broughtPilotBack(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        int safesignal = groundquests.getTaskId(questId, "safesignal");
        boolean onTask = questIsTaskActive(questId, safesignal, player);
        return onTask;
    }
    public boolean c_sink_imp_condition_firstStepofQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        int gotocrash = groundquests.getTaskId(questId, "gotocrash");
        boolean onTask = questIsTaskActive(questId, gotocrash, player);
        return onTask;
    }
    public boolean c_sink_imp_condition_gangKillStepDone(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        int killloot = groundquests.getTaskId(questId, "killloot");
        int kill = groundquests.getTaskId(questId, "kill");
        return questIsTaskActive(questId, spacesignal, player) && questIsTaskComplete(questId, killloot, player) && questIsTaskComplete(questId, kill, player);
    }
    public boolean c_sink_imp_condition_gangKillStepNotDone(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        int killloot = groundquests.getTaskId(questId, "killloot");
        int kill = groundquests.getTaskId(questId, "kill");
        return questIsTaskActive(questId, spacesignal, player) && (questIsTaskActive(questId, killloot, player) || questIsTaskActive(questId, kill, player));
    }
    public boolean c_sink_imp_condition_gangKillStepActive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        return questIsTaskActive(questId, spacesignal, player);
    }
    public boolean c_sink_imp_condition_noGangsKilledYet(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        int killloot = groundquests.getTaskId(questId, "killloot");
        int kill = groundquests.getTaskId(questId, "kill");
        return questIsTaskActive(questId, spacesignal, player) && questIsTaskActive(questId, killloot, player) && questIsTaskActive(questId, kill, player);
    }
    public boolean c_sink_imp_condition_hasJTL(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean c_sink_imp_condition_spaceWon(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_sink_imp_pt2", "c_sink_imp_kill_binyare_success_wfs");
    }
    public boolean c_sink_imp_condition_spaceLost(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_sink_imp_pt2", "c_sink_imp_kill_binyare_failed_wfs");
    }
    public boolean c_sink_imp_condition_spaceStepActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "c_sink_imp_pt2");
    }
    public boolean c_sink_imp_condition_spaceTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_sink_imp_pt2", "c_sink_imp_kill_banyare_gsq");
    }
    public boolean c_sink_imp_condition_spaceNotActiveOrCmplt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "c_sink_imp_v2") && !groundquests.isQuestActiveOrComplete(player, "c_sink_imp_pt2"))
        {
            return true;
        }
        return false;
    }
    public void c_sink_imp_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -2449;
        loc.y = 0;
        loc.z = 3843;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot", waypoint);
        setWaypointName(waypoint, "Tie Fighter Crash Site");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -2179;
        loc1.y = 0;
        loc1.z = 2273;
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver", waypoint1);
        setWaypointName(waypoint1, "Ceth Laike");
        setWaypointActive(waypoint, true);
    }
    public void c_sink_imp_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_sink_imp_action_forwardQuestAfterEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_imp_v2");
        groundquests.sendSignal(player, "c_sink_imp_safe");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -4737;
        loc.y = 0;
        loc.z = -1519;
        loc.area = "corellia";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_sink_imp_v2.daggercamp", waypoint);
        setWaypointName(waypoint, "Flail Camp");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -1622;
        loc1.y = 0;
        loc1.z = 618;
        loc1.area = "talus";
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_sink_imp_v2.binayrecamp", waypoint1);
        setWaypointName(waypoint1, "Binayre Camp");
        setWaypointActive(waypoint, true);
    }
    public void c_sink_imp_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -2449;
        loc.y = 0;
        loc.z = 3843;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_sink_imp_v2.pilot", waypoint);
        setWaypointName(waypoint, "Tie Fighter Crash Site");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -2179;
        loc1.y = 0;
        loc1.z = 2273;
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver", waypoint1);
        setWaypointName(waypoint1, "Ceth Laike");
        setWaypointActive(waypoint, true);
    }
    public void c_sink_imp_action_rewardSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_sink_imp_kill_binyare_report_success");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.questgiver");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.daggercamp");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.daggercamp");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.binayrecamp");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.binayrecamp");
    }
    public void c_sink_imp_action_giveSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActiveOrComplete(player, "c_sink_imp_pt2"))
        {
            groundquests.grantQuest(player, "c_sink_imp_pt2");
        }
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.daggercamp");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.daggercamp");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_imp_v2.binayrecamp");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_imp_v2.binayrecamp");
    }
    public void c_sink_imp_action_complete_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_sink_imp_gospace");
    }
    public void c_sink_imp_action_clearSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "c_sink_imp_pt2");
    }
    public int c_sink_imp_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1338"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_1340");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1342");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1346");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1342"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                c_sink_imp_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1344");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1346"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_1348");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1352"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_1354");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1356");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1356"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1358");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1360");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1372");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1376");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1360"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1362");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1364");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1368");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1372"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_forwardQuestAfterEscort(player, npc);
                string_id message = new string_id(c_stringFile, "s_1374");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1376"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1378");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1364"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_forwardQuestAfterEscort(player, npc);
                string_id message = new string_id(c_stringFile, "s_1366");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1368"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1370");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1382"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                c_sink_imp_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_1384");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1388"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1390");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1392"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1394");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1396"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1398");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition_hasJTL(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!c_sink_imp_condition_hasJTL(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1400");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1404");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1400"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_complete_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1402");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1404"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_rewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1406");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_giveSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1410"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1412");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1414"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_clearSpaceQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1416");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1418"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1420");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_imp_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1422");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1426");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_giveSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1422"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_rewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1424");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1426"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_rewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1428");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                c_sink_imp_action_giveSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_imp_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1432"))
        {
            if (c_sink_imp_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1434");
                utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
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
            detachScript(self, "conversation.c_sink_imp");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Ceth Laike");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Ceth Laike");
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
        detachScript(self, "conversation.c_sink_imp");
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
        if (c_sink_imp_condition_IsRebel(player, npc))
        {
            c_sink_imp_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1332");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_isNeutral(player, npc))
        {
            c_sink_imp_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1334");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_IsImperial(player, npc))
        {
            c_sink_imp_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1336");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1338");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 3);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_broughtPilotBack(player, npc))
        {
            c_sink_imp_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1350");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1352");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 7);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_firstStepofQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1380");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1382");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 15);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_gangKillStepActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1386");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition_noGangsKilledYet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!c_sink_imp_condition_noGangsKilledYet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_sink_imp_condition_gangKillStepDone(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1388");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1392");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1396");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 17);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_spaceStepActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1408");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition_spaceTaskActive(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_sink_imp_condition_spaceLost(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_sink_imp_condition_spaceWon(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1410");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1418");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 24);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition_spaceNotActiveOrCmplt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_64");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 32);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_imp_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1430");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_imp_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1432");
                }
                utils.setScriptVar(player, "conversation.c_sink_imp.branchId", 34);
                npcStartConversation(player, npc, "c_sink_imp", message, responses);
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
        if (!conversationId.equals("c_sink_imp"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_sink_imp.branchId");
        if (branchId == 3 && c_sink_imp_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_sink_imp_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_sink_imp_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && c_sink_imp_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_sink_imp_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_sink_imp_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && c_sink_imp_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && c_sink_imp_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && c_sink_imp_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && c_sink_imp_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && c_sink_imp_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && c_sink_imp_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && c_sink_imp_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && c_sink_imp_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && c_sink_imp_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_sink_imp.branchId");
        return SCRIPT_CONTINUE;
    }
}
