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

public class c_sink_reb extends script.base_script
{
    public c_sink_reb()
    {
    }
    public static String c_stringFile = "conversation/c_sink_reb";
    public boolean c_sink_reb_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_sink_reb_condition_IsRebel1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        int questId1 = questGetQuestId("quest/c_sink_reb");
        boolean OnQuest = questIsQuestActive(questId1, player);
        return !OnQuest;
    }
    public boolean c_sink_reb_condition_IsImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_sink_reb_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) || factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_sink_reb_condition_broughtPilotBack(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int safesignal = groundquests.getTaskId(questId, "safesignal");
        boolean onTask = questIsTaskActive(questId, safesignal, player);
        return onTask;
    }
    public boolean c_sink_reb_condition_firstStepofQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int gotocrash = groundquests.getTaskId(questId, "gotocrash");
        boolean onTask = questIsTaskActive(questId, gotocrash, player);
        return onTask;
    }
    public boolean c_sink_reb_condition_gangKillStepDone(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        int killloot = groundquests.getTaskId(questId, "killloot");
        int kill = groundquests.getTaskId(questId, "kill");
        return questIsTaskActive(questId, spacesignal, player) && questIsTaskComplete(questId, killloot, player) && questIsTaskComplete(questId, kill, player);
    }
    public boolean c_sink_reb_condition_gangKillStepNotDone(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        int killloot = groundquests.getTaskId(questId, "killloot");
        int kill = groundquests.getTaskId(questId, "kill");
        return questIsTaskActive(questId, spacesignal, player) && (questIsTaskActive(questId, killloot, player) || questIsTaskActive(questId, kill, player));
    }
    public boolean c_sink_reb_condition_gangKillStepActive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        return questIsTaskActive(questId, spacesignal, player);
    }
    public boolean c_sink_reb_condition_noGangsKilledYet(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int spacesignal = groundquests.getTaskId(questId, "spacesignal");
        int killloot = groundquests.getTaskId(questId, "killloot");
        int kill = groundquests.getTaskId(questId, "kill");
        return questIsTaskActive(questId, spacesignal, player) && questIsTaskActive(questId, killloot, player) && questIsTaskActive(questId, kill, player);
    }
    public boolean c_sink_reb_condition_hasJTL(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean c_sink_reb_condition_spaceWon(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "c_sink_reb_space"))
        {
            return true;
        }
        return false;
    }
    public boolean c_sink_reb_condition_spaceLost(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_quest.hasAbortedQuest(player, "assassinate", "c_sink_reb_space") || space_quest.hasFailedQuest(player, "assassinate", "c_sink_reb_space")) && (!space_quest.hasWonQuest(player, "assassinate", "c_sink_reb_space")))
        {
            return true;
        }
        return false;
    }
    public boolean c_sink_reb_condition_spaceStepActive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        int spaceactive = groundquests.getTaskId(questId, "spaceactive");
        return questIsTaskActive(questId, spaceactive, player);
    }
    public void c_sink_reb_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.pilot");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.pilot");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.questgiver");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.questgiver");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -5463;
        loc.y = 0;
        loc.z = -930;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_sink_reb.pilot", waypoint);
        setWaypointName(waypoint, "Escape Pod Crash Site");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -5200;
        loc1.y = 0;
        loc1.z = -2575;
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_sink_reb.questgiver", waypoint1);
        setWaypointName(waypoint1, "Acun Solari");
        setWaypointActive(waypoint, true);
    }
    public void c_sink_reb_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_sink_reb_action_forwardQuestAfterEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_sink_reb");
        groundquests.sendSignal(player, "c_sink_reb_safe");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.pilot");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.pilot");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -4737;
        loc.y = 0;
        loc.z = -1519;
        loc.area = "corellia";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_sink_reb.daggercamp", waypoint);
        setWaypointName(waypoint, "Flail Camp");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -1622;
        loc1.y = 0;
        loc1.z = 618;
        loc1.area = "talus";
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_sink_reb.binayrecamp", waypoint1);
        setWaypointName(waypoint1, "Binayre Camp");
        setWaypointActive(waypoint, true);
    }
    public void c_sink_reb_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.pilot");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.pilot");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.questgiver");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.questgiver");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -5463;
        loc.y = 0;
        loc.z = -930;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_sink_reb.pilot", waypoint);
        setWaypointName(waypoint, "Escape Pod Crash Site");
        setWaypointActive(waypoint, true);
        location loc1 = getLocation(top);
        loc1.x = -5200;
        loc1.y = 0;
        loc1.z = -2565;
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/c_sink_reb.questgiver", waypoint1);
        setWaypointName(waypoint1, "Acun Solari");
        setWaypointActive(waypoint, true);
    }
    public void c_sink_reb_action_rewardSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_sink_reb_gospace");
        groundquests.sendSignal(player, "c_sink_reb_spacedone");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.questgiver");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.questgiver");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.daggercamp");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.daggercamp");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.binayrecamp");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.binayrecamp");
    }
    public void c_sink_reb_action_giveSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "c_sink_reb_space");
        groundquests.sendSignal(player, "c_sink_reb_gospace");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.daggercamp");
        if (waypoint2 != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.daggercamp");
        obj_id waypoint3 = getObjIdObjVar(player, "quest.general.quest/c_sink_reb.binayrecamp");
        if (waypoint3 != null)
        {
            destroyWaypointInDatapad(waypoint3, player);
        }
        removeObjVar(player, "quest.general.quest/c_sink_reb.binayrecamp");
    }
    public int c_sink_reb_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1118"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_1120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1122");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1126");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1122"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                c_sink_reb_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1124");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1126"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_1128");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1132"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_1134");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1136");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1156");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1160");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1136"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1138");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1140");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1156"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_forwardQuestAfterEscort(player, npc);
                string_id message = new string_id(c_stringFile, "s_1158");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1160"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1162");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1140"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1142");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1144");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1144"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1148");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1152");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1148"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_forwardQuestAfterEscort(player, npc);
                string_id message = new string_id(c_stringFile, "s_1150");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1152"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1154");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1166"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                c_sink_reb_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_1168");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1172"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1174");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1176"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1178");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1180"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1182");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition_hasJTL(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!c_sink_reb_condition_hasJTL(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1184");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1188");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1184"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_giveSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_1186");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1188"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_rewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1190");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1194"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1196");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1198"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_giveSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_1200");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1202"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_sink_reb_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1206");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                    }
                    utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1206"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_rewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1208");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1210"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                c_sink_reb_action_rewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1212");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_sink_reb_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1216"))
        {
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1218");
                utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
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
            detachScript(self, "conversation.c_sink_reb");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Acun Solari");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Acun Solari");
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
        detachScript(self, "conversation.c_sink_reb");
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
        if (c_sink_reb_condition_IsImperial(player, npc))
        {
            c_sink_reb_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1112");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition_isNeutral(player, npc))
        {
            c_sink_reb_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1114");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition_IsRebel1(player, npc))
        {
            c_sink_reb_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1116");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_reb_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1118");
                }
                utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 3);
                npcStartConversation(player, npc, "c_sink_reb", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition_broughtPilotBack(player, npc))
        {
            c_sink_reb_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1130");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_reb_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1132");
                }
                utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 7);
                npcStartConversation(player, npc, "c_sink_reb", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition_firstStepofQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1164");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_reb_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1166");
                }
                utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 16);
                npcStartConversation(player, npc, "c_sink_reb", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition_gangKillStepActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1170");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_reb_condition_noGangsKilledYet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!c_sink_reb_condition_noGangsKilledYet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_sink_reb_condition_gangKillStepDone(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1172");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1176");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1180");
                }
                utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 18);
                npcStartConversation(player, npc, "c_sink_reb", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition_spaceStepActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1192");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_reb_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_sink_reb_condition_spaceLost(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_sink_reb_condition_spaceWon(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1194");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1198");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1202");
                }
                utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 24);
                npcStartConversation(player, npc, "c_sink_reb", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_sink_reb_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1214");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_sink_reb_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1216");
                }
                utils.setScriptVar(player, "conversation.c_sink_reb.branchId", 30);
                npcStartConversation(player, npc, "c_sink_reb", message, responses);
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
        if (!conversationId.equals("c_sink_reb"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_sink_reb.branchId");
        if (branchId == 3 && c_sink_reb_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_sink_reb_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_sink_reb_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && c_sink_reb_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_sink_reb_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_sink_reb_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && c_sink_reb_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && c_sink_reb_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && c_sink_reb_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && c_sink_reb_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && c_sink_reb_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && c_sink_reb_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && c_sink_reb_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_sink_reb.branchId");
        return SCRIPT_CONTINUE;
    }
}
