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
import script.library.groundquests;
import script.library.utils;

public class lf_safetyquestgiver extends script.base_script
{
    public lf_safetyquestgiver()
    {
    }
    public static String c_stringFile = "conversation/lf_safetyquestgiver";
    public boolean lf_safetyquestgiver_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lf_safetyquestgiver_condition_part1complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/lf_safety1");
        int questId2 = questGetQuestId("quest/lf_safety2");
        boolean hasQuest2 = questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player);
        return questIsQuestComplete(questId1, player) && !hasQuest2;
    }
    public boolean lf_safetyquestgiver_condition_part5complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety5");
        return questIsQuestComplete(questId, player);
    }
    public boolean lf_safetyquestgiver_condition_part4complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/lf_safety4");
        int questId2 = questGetQuestId("quest/lf_safety5");
        boolean hasQuest5 = questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player);
        return questIsQuestComplete(questId1, player) && !hasQuest5;
    }
    public boolean lf_safetyquestgiver_condition_part3complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/lf_safety3");
        int questId2 = questGetQuestId("quest/lf_safety4");
        boolean hasQuest4 = questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player);
        return questIsQuestComplete(questId1, player) && !hasQuest4;
    }
    public boolean lf_safetyquestgiver_condition_part2complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/lf_safety2");
        int questId2 = questGetQuestId("quest/lf_safety3");
        boolean hasQuest3 = questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player);
        return questIsQuestComplete(questId1, player) && !hasQuest3;
    }
    public boolean lf_safetyquestgiver_condition_playeronquest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/lf_safety1");
        int questId2 = questGetQuestId("quest/lf_safety2");
        int questId3 = questGetQuestId("quest/lf_safety3");
        int questId4 = questGetQuestId("quest/lf_safety4");
        int questId5 = questGetQuestId("quest/lf_safety5");
        boolean onQuest = false;
        onQuest = questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player);
        onQuest |= questIsQuestActive(questId2, player) || questIsQuestComplete(questId2, player);
        onQuest |= questIsQuestActive(questId3, player) || questIsQuestComplete(questId3, player);
        onQuest |= questIsQuestActive(questId4, player) || questIsQuestComplete(questId4, player);
        onQuest |= questIsQuestActive(questId5, player) || questIsQuestComplete(questId5, player);
        return onQuest;
    }
    public boolean lf_safetyquestgiver_condition_part1active(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety1");
        return questIsQuestActive(questId, player);
    }
    public boolean lf_safetyquestgiver_condition_part2active(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety2");
        return questIsQuestActive(questId, player);
    }
    public boolean lf_safetyquestgiver_condition_part3active(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety3");
        return questIsQuestActive(questId, player);
    }
    public boolean lf_safetyquestgiver_condition_part4active(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety4");
        return questIsQuestActive(questId, player);
    }
    public boolean lf_safetyquestgiver_condition_part5active(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety5");
        return questIsQuestActive(questId, player);
    }
    public boolean lf_safetyquestgiver_condition_isquestcomplete1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety1");
        return questIsTaskComplete(questId, 0, player);
    }
    public boolean lf_safetyquestgiver_condition_isquestcomplete2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety2");
        return questIsTaskComplete(questId, 0, player);
    }
    public boolean lf_safetyquestgiver_condition_isquestcomplete3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety3");
        return questIsTaskComplete(questId, 0, player);
    }
    public boolean lf_safetyquestgiver_condition_isquestcomplete4(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety4");
        return questIsTaskComplete(questId, 0, player);
    }
    public boolean lf_safetyquestgiver_condition_isquestcomplete5(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety5");
        return questIsTaskComplete(questId, 0, player);
    }
    public void lf_safetyquestgiver_action_givequest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety1");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -1254;
        loc.y = 12;
        loc.z = -3617;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety5.questgiver", waypoint);
        setWaypointName(waypoint, "Gerak Vertimis");
    }
    public void lf_safetyquestgiver_action_givequest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety2");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void lf_safetyquestgiver_action_givequest5(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety5");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 1500;
        loc.y = 180;
        loc.z = -2000;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety5.waypoint", waypoint);
        setWaypointName(waypoint, "Brigand Leader");
        setWaypointActive(waypoint, true);
    }
    public void lf_safetyquestgiver_action_givequest4(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety4");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 1002;
        loc.y = 187;
        loc.z = -3019;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety4.waypoint", waypoint);
        setWaypointName(waypoint, "Brigand Camp 2");
        setWaypointActive(waypoint, true);
    }
    public void lf_safetyquestgiver_action_givequest3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety3");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 1145;
        loc.y = 149;
        loc.z = -3920;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety3.waypoint", waypoint);
        setWaypointName(waypoint, "Brigand Camp 1");
        setWaypointActive(waypoint, true);
    }
    public void lf_safetyquestgiver_action_completequest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety1");
        questCompleteTask(questId, 1, player);
    }
    public void lf_safetyquestgiver_action_completequest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_safety2");
        questCompleteTask(questId, 1, player);
    }
    public void lf_safetyquestgiver_action_completequest3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_safety3.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, "quest.general.quest/lf_safety3.waypoint");
        int questId = questGetQuestId("quest/lf_safety3");
        questCompleteTask(questId, 1, player);
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/skill_buff/skill_buff_pistol_speed.iff";
        obj_id newItem = createObject(templateName, playerInventory, "");
    }
    public void lf_safetyquestgiver_action_completequest4(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_safety4.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, "quest.general.quest/lf_safety4.waypoint");
        int questId = questGetQuestId("quest/lf_safety4");
        questCompleteTask(questId, 1, player);
    }
    public void lf_safetyquestgiver_action_completequest5(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_safety5.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, "quest.general.quest/lf_safety5.waypoint");
        obj_id waypoint2 = getObjIdObjVar(player, "quest.general.quest/lf_safety5.questgiver");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint2, player);
        }
        removeObjVar(player, "quest.general.quest/lf_safety5.questgiver");
        int questId = questGetQuestId("quest/lf_safety5");
        questCompleteTask(questId, 1, player);
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/skill_buff/skill_buff_pistol_accuracy.iff";
        obj_id newItem = createObject(templateName, playerInventory, "");
        if (!isIdValid(newItem))
        {
        }
    }
    public void lf_safetyquestgiver_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void lf_safetyquestgiver_action_givewaypointcamp1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_safety3.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 1145;
        loc.y = 149;
        loc.z = -3920;
        waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety3.waypoint", waypoint);
        setWaypointName(waypoint, "Brigand Camp 1");
        setWaypointActive(waypoint, true);
    }
    public void lf_safetyquestgiver_action_givewaypointcamp2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_safety4.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 1002;
        loc.y = 187;
        loc.z = -3019;
        waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety4.waypoint", waypoint);
        setWaypointName(waypoint, "Brigand Camp 2");
        setWaypointActive(waypoint, true);
    }
    public void lf_safetyquestgiver_action_givewaypointleader(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_safety5.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 1500;
        loc.y = 180;
        loc.z = -2000;
        waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_safety5.waypoint", waypoint);
        setWaypointName(waypoint, "Brigand Leader");
        setWaypointActive(waypoint, true);
    }
    public int lf_safetyquestgiver_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            doAnimationAction(player, "shrug_hands");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_123"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_125");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_131");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_138"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "mock");
                string_id message = new string_id(c_stringFile, "s_142");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            doAnimationAction(player, "nod");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                lf_safetyquestgiver_action_givequest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_117");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_127"))
        {
            doAnimationAction(player, "nod");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                lf_safetyquestgiver_action_givequest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_131"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            doAnimationAction(player, "shrug_hands");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                lf_safetyquestgiver_action_givequest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_154"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_158");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_166"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_completequest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_170");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_174"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_178");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givequest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_190");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_194"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_198");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_210");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_214"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_completequest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_218");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_226"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_233");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_265"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_233");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_233"))
        {
            doAnimationAction(player, "taken_aback");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                string_id message = new string_id(c_stringFile, "s_236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_244");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_249"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "poke");
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_238"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givequest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_241");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_244"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_246");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_254"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                lf_safetyquestgiver_action_givequest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_257");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_260"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_262");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_270"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_273");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_276"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_completequest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_280");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_284"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                lf_safetyquestgiver_action_givewaypointcamp1(player, npc);
                string_id message = new string_id(c_stringFile, "s_288");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_296"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_304");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_312");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_320"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_324");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_304"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givequest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_308");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_312"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_316");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_328"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givequest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_332");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_336"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_340");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_344"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_346");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_348"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                lf_safetyquestgiver_action_completequest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_350");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_352"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givewaypointcamp2(player, npc);
                string_id message = new string_id(c_stringFile, "s_354");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_358"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givequest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_360");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_362"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_364");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_368"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_completequest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_370");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_372"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_completequest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_374");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_376"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_378");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_380");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_388"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_390");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_392"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_givewaypointleader(player, npc);
                string_id message = new string_id(c_stringFile, "s_394");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_safetyquestgiver_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_380"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                lf_safetyquestgiver_action_completequest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_382");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_384"))
        {
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                lf_safetyquestgiver_action_completequest5(player, npc);
                string_id message = new string_id(c_stringFile, "s_386");
                utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
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
            detachScript(self, "conversation.lf_safetyquestgiver");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Gerak Vurtimis");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Gerak Vurtimis");
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
        detachScript(self, "conversation.lf_safetyquestgiver");
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
        if (!lf_safetyquestgiver_condition_playeronquest(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_109");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 1);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part1active(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_162");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete1(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 11);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part1complete(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_182");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 14);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part2active(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_202");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete2(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 17);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part2complete(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_222");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 20);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part3active(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_268");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete3(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_276");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_284");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 28);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part3complete(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_292");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_296");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_320");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 32);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part4active(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_342");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete4(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 39);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part4complete(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_356");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_358");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 43);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part5active(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_366");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete5(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete5(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lf_safetyquestgiver_condition_isquestcomplete5(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (lf_safetyquestgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_368");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_372");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_376");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_392");
                }
                utils.setScriptVar(player, "conversation.lf_safetyquestgiver.branchId", 46);
                npcStartConversation(player, npc, "lf_safetyquestgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_safetyquestgiver_condition_part5complete(player, npc))
        {
            lf_safetyquestgiver_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_396");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("lf_safetyquestgiver"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
        if (branchId == 1 && lf_safetyquestgiver_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && lf_safetyquestgiver_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && lf_safetyquestgiver_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && lf_safetyquestgiver_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && lf_safetyquestgiver_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && lf_safetyquestgiver_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && lf_safetyquestgiver_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && lf_safetyquestgiver_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && lf_safetyquestgiver_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && lf_safetyquestgiver_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && lf_safetyquestgiver_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && lf_safetyquestgiver_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && lf_safetyquestgiver_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && lf_safetyquestgiver_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && lf_safetyquestgiver_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && lf_safetyquestgiver_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && lf_safetyquestgiver_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && lf_safetyquestgiver_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && lf_safetyquestgiver_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lf_safetyquestgiver.branchId");
        return SCRIPT_CONTINUE;
    }
}
