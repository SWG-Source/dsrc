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
import script.library.utils;

public class emp_day_garrison_historian extends script.base_script
{
    public emp_day_garrison_historian()
    {
    }
    public static String c_stringFile = "conversation/emp_day_garrison_historian";
    public boolean emp_day_garrison_historian_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_garrison_historian_condition_hasTask2or3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        return (questIsTaskActive(questId, task2, player) && !questIsTaskComplete(questId, task3c, player)) || (questIsTaskActive(questId, task3a, player) && !questIsTaskComplete(questId, task2, player)) || (questIsTaskActive(questId, task3b, player) && !questIsTaskComplete(questId, task2, player)) || (questIsTaskActive(questId, task3c, player) && !questIsTaskComplete(questId, task2, player));
    }
    public boolean emp_day_garrison_historian_condition_pastMyTasks(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task1 = groundquests.getTaskId(questId, "converseHistorian");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        int task4 = groundquests.getTaskId(questId, "disksToHistorian");
        return questIsQuestComplete(questId, player) || (!questIsTaskActive(questId, task1, player) && !questIsTaskActive(questId, task2, player) && !questIsTaskActive(questId, task3a, player) && !questIsTaskActive(questId, task3b, player) && !questIsTaskActive(questId, task3c, player) && !questIsTaskActive(questId, task4, player) && questIsQuestActive(questId, player));
    }
    public boolean emp_day_garrison_historian_condition_hasDisks(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        return questIsTaskComplete(questId, task2, player) || (questIsTaskComplete(questId, task3a, player) && questIsTaskComplete(questId, task3b, player) && questIsTaskComplete(questId, task3c, player));
    }
    public boolean emp_day_garrison_historian_condition_hasStartingTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        return questIsTaskActive(questId, 0, player);
    }
    public boolean emp_day_garrison_historian_condition_hasTask2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        return questIsTaskActive(questId, task2, player);
    }
    public boolean emp_day_garrison_historian_condition_hasTask3a(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        return questIsTaskActive(questId, task3a, player);
    }
    public boolean emp_day_garrison_historian_condition_hasTask3b(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        return questIsTaskActive(questId, task3b, player);
    }
    public boolean emp_day_garrison_historian_condition_hasTask3c(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        return questIsTaskActive(questId, task3c, player);
    }
    public boolean emp_day_garrison_historian_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = pvpGetAlignedFaction(player);
        if (factionHashCode == 0)
        {
            return true;
        }
        return false;
    }
    public void emp_day_garrison_historian_action_historianConversed(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "historian_conversed");
    }
    public void emp_day_garrison_historian_action_diskGuy1Wp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(-1006, 10, -3527, "tatooine");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Cantina Contact");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public void emp_day_garrison_historian_action_dataUplinkWp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(-92, 18, -1630, "dathomir");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Data Officer");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public void emp_day_garrison_historian_action_diskGuy3Wp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(4912, 362, -1498, "naboo");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Theater Contact");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public void emp_day_garrison_historian_action_diskGuy2Wp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(6711, 330, -5940, "corellia");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Med Center Contact");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public void emp_day_garrison_historian_action_disksToHistorian(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        groundquests.sendSignal(player, "disk_to_historian");
        if (questIsTaskActive(questId, task2, player))
        {
            
        }
        questCompleteTask(questId, task2, player);
        if (questIsTaskActive(questId, task3a, player))
        {
            
        }
        questCompleteTask(questId, task3a, player);
        if (questIsTaskActive(questId, task3b, player))
        {
            
        }
        questCompleteTask(questId, task3b, player);
        if (questIsTaskActive(questId, task3c, player))
        {
            
        }
        questCompleteTask(questId, task3c, player);
    }
    public void emp_day_garrison_historian_action_dataOfficerWp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(-92, 18, -1630, "dathomir");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Data Officer");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public int emp_day_garrison_historian_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_105");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_109"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                emp_day_garrison_historian_action_diskGuy1Wp(player, npc);
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                emp_day_garrison_historian_action_diskGuy2Wp(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                emp_day_garrison_historian_action_diskGuy3Wp(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_130");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_152");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_140"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_142");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_152"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_136"))
        {
            emp_day_garrison_historian_action_historianConversed(player, npc);
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                emp_day_garrison_historian_action_diskGuy1Wp(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_144"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (!emp_day_garrison_historian_condition_isNeutral(player, npc))
            {
                emp_day_garrison_historian_action_historianConversed(player, npc);
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (emp_day_garrison_historian_condition_isNeutral(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_136"))
        {
            emp_day_garrison_historian_action_historianConversed(player, npc);
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                emp_day_garrison_historian_action_diskGuy1Wp(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_158"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_160");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_garrison_historian_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            emp_day_garrison_historian_action_disksToHistorian(player, npc);
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                emp_day_garrison_historian_action_dataOfficerWp(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_166"))
        {
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.emp_day_garrison_historian");
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
        detachScript(self, "conversation.emp_day_garrison_historian");
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
        if (emp_day_garrison_historian_condition_pastMyTasks(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_95");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_garrison_historian_condition_hasTask2or3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_97");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_garrison_historian_condition_hasTask2(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (emp_day_garrison_historian_condition_hasTask3a(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (emp_day_garrison_historian_condition_hasTask3b(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (emp_day_garrison_historian_condition_hasTask3c(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_109");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                }
                utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 2);
                npcStartConversation(player, npc, "emp_day_garrison_historian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_garrison_historian_condition_hasStartingTask(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_122");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 7);
                npcStartConversation(player, npc, "emp_day_garrison_historian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_garrison_historian_condition_hasDisks(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_156");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.emp_day_garrison_historian.branchId", 17);
                npcStartConversation(player, npc, "emp_day_garrison_historian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_garrison_historian_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_170");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("emp_day_garrison_historian"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
        if (branchId == 2 && emp_day_garrison_historian_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && emp_day_garrison_historian_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && emp_day_garrison_historian_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && emp_day_garrison_historian_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && emp_day_garrison_historian_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && emp_day_garrison_historian_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && emp_day_garrison_historian_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && emp_day_garrison_historian_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && emp_day_garrison_historian_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && emp_day_garrison_historian_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_garrison_historian.branchId");
        return SCRIPT_CONTINUE;
    }
}
