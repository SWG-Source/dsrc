package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.buff;
import script.library.chat;
import script.library.collection;
import script.library.conversation;
import script.library.create;
import script.library.factions;
import script.library.groundquests;
import script.library.holiday;
import script.library.static_item;
import script.library.utils;

public class rebel_remembrance_day_wedge_antilles extends script.base_script
{
    public rebel_remembrance_day_wedge_antilles()
    {
    }
    public static String c_stringFile = "conversation/rebel_remembrance_day_wedge_antilles";
    public boolean rebel_remembrance_day_wedge_antilles_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isBelow60thLevelOrTraderEntertainer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (rebel_remembrance_day_wedge_antilles_condition_isBelow60thLevel(player, npc) || rebel_remembrance_day_wedge_antilles_condition_isEntertainer(player, npc) || rebel_remembrance_day_wedge_antilles_condition_isTrader(player, npc));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isEntertainer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return utils.isProfession(player, utils.ENTERTAINER);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isTrader(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return utils.isProfession(player, utils.TRADER);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isBelow60thLevel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (60 > getLevel(player));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasAMisionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        for (int i = 0; i < holiday.ALL_REBEL_QUESTS.length; i++)
        {
            if (groundquests.isQuestActive(player, holiday.ALL_REBEL_QUESTS[i]))
            {
                return true;
            }
        }
        return false;
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasAnIncompleteCombatMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!rebel_remembrance_day_wedge_antilles_condition_completed7thQuest(player, npc) && (rebel_remembrance_day_wedge_antilles_condition_hasCompleted1stMissionNot2nd(player, npc) || rebel_remembrance_day_wedge_antilles_condition_hasCompleted2ndMissionNot3rd(player, npc) || rebel_remembrance_day_wedge_antilles_condition_hasCompleted3rdMissionNot4th(player, npc) || rebel_remembrance_day_wedge_antilles_condition_hasCompleted4thMissionNot5th(player, npc) || rebel_remembrance_day_wedge_antilles_condition_hasCompleted5thMissionNot6th(player, npc) || rebel_remembrance_day_wedge_antilles_condition_hasCompleted6thMissionNot7th(player, npc)));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasNotStartedSecondMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02) && groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_01));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isChampion(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return hasCompletedCollectionSlot(player, holiday.REMEMBRANCE_DAY_CHAMPION_BADGE);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_completed7thQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_07);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_completed7thQuestNoAutograph(obj_id player, obj_id npc) throws InterruptedException
    {
        return rebel_remembrance_day_wedge_antilles_condition_completed7thQuest(player, npc) && !groundquests.isQuestActiveOrComplete(player, holiday.EMPIREDAYQUEST_REB_COMBAT_AUTOGRAPH);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isImperialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (factions.isImperial(player));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_isNeutralPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (!factions.isImperial(player) && !factions.isRebel(player));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_completed7thQuestHasAutograph(obj_id player, obj_id npc) throws InterruptedException
    {
        return rebel_remembrance_day_wedge_antilles_condition_completed7thQuest(player, npc) && groundquests.isQuestActiveOrComplete(player, holiday.EMPIREDAYQUEST_REB_COMBAT_AUTOGRAPH);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted7thMission(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_07));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasNotCompletedMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_01) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_01);
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted1stMissionNot2nd(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_01) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02) && groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted2ndMissionNot3rd(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_03));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted3rdMissionNot4th(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_03) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_04));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted4thMissionNot5th(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_04) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_05));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted5thMissionNot6th(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_05) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_06));
    }
    public boolean rebel_remembrance_day_wedge_antilles_condition_hasCompleted6thMissionNot7th(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_06) && !groundquests.hasCompletedQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_07);
    }
    public void rebel_remembrance_day_wedge_antilles_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_01);
    }
    public void rebel_remembrance_day_wedge_antilles_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getCollectionSlotValue(player, holiday.REBEL_RESCUE_START_SLOT) <= 0)
        {
            modifyCollectionSlotValue(player, holiday.REBEL_RESCUE_START_SLOT, 1);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02);
    }
    public void rebel_remembrance_day_wedge_antilles_action_revokeMission2Regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02))
        {
            groundquests.clearQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_02);
    }
    public void rebel_remembrance_day_wedge_antilles_action_revokeMission3Regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_03))
        {
            groundquests.clearQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_03);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_03);
    }
    public void rebel_remembrance_day_wedge_antilles_action_revokeMission4Regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_04))
        {
            groundquests.clearQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_04);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_04);
    }
    public void rebel_remembrance_day_wedge_antilles_action_spawnTroopers(obj_id player, obj_id npc) throws InterruptedException
    {
        for (int i = 0; i < 3; i++)
        {
            location guardLocation = groundquests.getRandom2DLocationAroundLocation(npc, 1, 1, 5, 12);
            int mobLevel = getLevel(player);
            obj_id guard = create.object("rebel_emperorsday_ceremony_sentry", guardLocation, mobLevel);
            attachScript(guard, "event.emp_day.factional_guard_self_destruct");
            startCombat(guard, player);
        }
    }
    public void rebel_remembrance_day_wedge_antilles_action_revokeMission5Regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_05))
        {
            groundquests.clearQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_05);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_05);
    }
    public void rebel_remembrance_day_wedge_antilles_action_revokeMission6Regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_06))
        {
            groundquests.clearQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_06);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_06);
    }
    public void rebel_remembrance_day_wedge_antilles_action_revokeMission7Regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, holiday.EMPIREDAYQUEST_REB_COMBAT_07))
        {
            groundquests.clearQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_07);
        }
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_07);
    }
    public void rebel_remembrance_day_wedge_antilles_action_grantAutograph(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, holiday.EMPIREDAYQUEST_REB_COMBAT_AUTOGRAPH);
    }
    public void rebel_remembrance_day_wedge_antilles_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location loc = new location(4000, 37, -6168, "yavin4");
        obj_id wpt = createWaypointInDatapad(player, loc);
        setWaypointName(wpt, "Detainment Facility");
        setWaypointActive(wpt, true);
    }
    public void rebel_remembrance_day_wedge_antilles_action_sendSignalToSpeak(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasSpokenToRecoveryQuestNpc");
    }
    public String rebel_remembrance_day_wedge_antilles_tokenTO_timeLeftRecruitment(obj_id player, obj_id npc) throws InterruptedException
    {
        String returnString = holiday.getTimeRemainingBeforeLockoutRemoved(player, holiday.EMPIRE_DAY_RECRUITMENT_TIMESTAMP);
        if (returnString == null || returnString.equals(""))
        {
            return "might have to contact Customer Service. I can't seem to get any time data off your character.";
        }
        return returnString;
    }
    public String rebel_remembrance_day_wedge_antilles_tokenTO_timeLeftPropaganda(obj_id player, obj_id npc) throws InterruptedException
    {
        String returnString = holiday.getTimeRemainingBeforeLockoutRemoved(player, holiday.EMPIRE_DAY_PROPAGANDA_TIMESTAMP);
        if (returnString == null || returnString.equals(""))
        {
            return "might have to contact Customer Service. I can't seem to get any time data off your character.";
        }
        return returnString;
    }
    public int rebel_remembrance_day_wedge_antilles_tokenDI_notUsed(obj_id player, obj_id npc) throws InterruptedException
    {
        return 0;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_84"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition_hasCompleted1stMissionNot2nd(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_remembrance_day_wedge_antilles_condition_hasCompleted2ndMissionNot3rd(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_remembrance_day_wedge_antilles_condition_hasCompleted3rdMissionNot4th(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_remembrance_day_wedge_antilles_condition_hasCompleted4thMissionNot5th(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_remembrance_day_wedge_antilles_condition_hasCompleted5thMissionNot6th(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (rebel_remembrance_day_wedge_antilles_condition_hasCompleted6thMissionNot7th(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_103");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_101"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_112"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_127");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_140"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_149");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            rebel_remembrance_day_wedge_antilles_action_revokeMission2Regrant(player, npc);
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_175");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            rebel_remembrance_day_wedge_antilles_action_revokeMission3Regrant(player, npc);
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_123"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            rebel_remembrance_day_wedge_antilles_action_revokeMission4Regrant(player, npc);
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_117"))
        {
            rebel_remembrance_day_wedge_antilles_action_revokeMission5Regrant(player, npc);
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_125"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_129"))
        {
            rebel_remembrance_day_wedge_antilles_action_revokeMission6Regrant(player, npc);
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_131");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_135"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_149"))
        {
            rebel_remembrance_day_wedge_antilles_action_revokeMission7Regrant(player, npc);
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_153");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_158"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_160");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "huh");
                string_id message = new string_id(c_stringFile, "s_162");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_163"))
        {
            doAnimationAction(player, "embarrassed");
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "handshake_tandem");
                doAnimationAction(player, "handshake_tandem");
                rebel_remembrance_day_wedge_antilles_action_grantAutograph(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
    public int rebel_remembrance_day_wedge_antilles_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition_isBelow60thLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 24);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_remembrance_day_wedge_antilles_condition_isTrader(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 28);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_remembrance_day_wedge_antilles_condition_isEntertainer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 30);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 31);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
    public int rebel_remembrance_day_wedge_antilles_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 31);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
    public int rebel_remembrance_day_wedge_antilles_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_72"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_120"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "backhand_threaten");
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_136"))
        {
            doAnimationAction(player, "shake_head_no");
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
    public int rebel_remembrance_day_wedge_antilles_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
    public int rebel_remembrance_day_wedge_antilles_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
    public int rebel_remembrance_day_wedge_antilles_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_144"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_147");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_150"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_152");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_155");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_155"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_166");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_168"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_170");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_remembrance_day_wedge_antilles_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                rebel_remembrance_day_wedge_antilles_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
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
            detachScript(self, "conversation.rebel_remembrance_day_wedge_antilles");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.rebel_remembrance_day_wedge_antilles");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
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
        if (rebel_remembrance_day_wedge_antilles_condition_isImperialPlayer(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_133");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_remembrance_day_wedge_antilles_condition_isNeutralPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_134");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_remembrance_day_wedge_antilles_condition_hasAnIncompleteCombatMission(player, npc))
        {
            rebel_remembrance_day_wedge_antilles_action_sendSignalToSpeak(player, npc);
            string_id message = new string_id(c_stringFile, "s_79");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                }
                utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 3);
                npcStartConversation(player, npc, "rebel_remembrance_day_wedge_antilles", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rebel_remembrance_day_wedge_antilles_condition_completed7thQuestHasAutograph(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_165");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (rebel_remembrance_day_wedge_antilles_condition_completed7thQuestNoAutograph(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_157");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_158");
                }
                utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 19);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "rebel_remembrance_day_wedge_antilles", null, pp, responses);
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
        if (rebel_remembrance_day_wedge_antilles_condition_hasNotStartedSecondMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 23);
                npcStartConversation(player, npc, "rebel_remembrance_day_wedge_antilles", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rebel_remembrance_day_wedge_antilles_condition_hasNotCompletedMission1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_173");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_142");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_remembrance_day_wedge_antilles_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                }
                utils.setScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId", 39);
                npcStartConversation(player, npc, "rebel_remembrance_day_wedge_antilles", message, responses);
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
        if (!conversationId.equals("rebel_remembrance_day_wedge_antilles"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
        if (branchId == 3 && rebel_remembrance_day_wedge_antilles_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && rebel_remembrance_day_wedge_antilles_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && rebel_remembrance_day_wedge_antilles_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && rebel_remembrance_day_wedge_antilles_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && rebel_remembrance_day_wedge_antilles_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && rebel_remembrance_day_wedge_antilles_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && rebel_remembrance_day_wedge_antilles_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && rebel_remembrance_day_wedge_antilles_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && rebel_remembrance_day_wedge_antilles_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && rebel_remembrance_day_wedge_antilles_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && rebel_remembrance_day_wedge_antilles_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && rebel_remembrance_day_wedge_antilles_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && rebel_remembrance_day_wedge_antilles_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && rebel_remembrance_day_wedge_antilles_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && rebel_remembrance_day_wedge_antilles_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && rebel_remembrance_day_wedge_antilles_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && rebel_remembrance_day_wedge_antilles_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && rebel_remembrance_day_wedge_antilles_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && rebel_remembrance_day_wedge_antilles_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && rebel_remembrance_day_wedge_antilles_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && rebel_remembrance_day_wedge_antilles_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && rebel_remembrance_day_wedge_antilles_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && rebel_remembrance_day_wedge_antilles_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && rebel_remembrance_day_wedge_antilles_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && rebel_remembrance_day_wedge_antilles_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && rebel_remembrance_day_wedge_antilles_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && rebel_remembrance_day_wedge_antilles_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && rebel_remembrance_day_wedge_antilles_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && rebel_remembrance_day_wedge_antilles_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && rebel_remembrance_day_wedge_antilles_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && rebel_remembrance_day_wedge_antilles_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && rebel_remembrance_day_wedge_antilles_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && rebel_remembrance_day_wedge_antilles_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rebel_remembrance_day_wedge_antilles.branchId");
        return SCRIPT_CONTINUE;
    }
}
