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

public class lf_killmite extends script.base_script
{
    public lf_killmite()
    {
    }
    public static String c_stringFile = "conversation/lf_killmite";
    public boolean lf_killmite_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lf_killmite_condition_ifquestiscompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_deadmite");
        boolean tasksCompleted = true;
        tasksCompleted &= questIsTaskComplete(questId, 0, player);
        tasksCompleted &= questIsTaskComplete(questId, 1, player);
        tasksCompleted &= questIsTaskComplete(questId, 2, player);
        tasksCompleted &= questIsTaskComplete(questId, 3, player);
        tasksCompleted &= questIsTaskComplete(questId, 4, player);
        tasksCompleted &= questIsTaskComplete(questId, 5, player);
        return tasksCompleted;
    }
    public boolean lf_killmite_condition_ifquestinprogress(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_deadmite");
        return questIsQuestActive(questId, player);
    }
    public boolean lf_killmite_condition_questtaken(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_deadmite");
        return questIsQuestActive(questId, player) || questIsQuestComplete(questId, player);
    }
    public boolean lf_killmite_condition_questrewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_deadmite");
        return questIsQuestComplete(questId, player);
    }
    public void lf_killmite_action_givequest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/lf_deadmite");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 3572;
        loc.y = 91;
        loc.z = 5424;
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_deadmite.waypoint", waypoint);
        setWaypointName(waypoint, "Giant Mite Cave");
        setWaypointActive(waypoint, true);
        obj_id top1 = getTopMostContainer(player);
        location loc1 = getLocation(top1);
        loc1.x = 5335;
        loc1.y = 80;
        loc1.z = 5637;
        obj_id waypoint1 = createWaypointInDatapad(player, loc1);
        setObjVar(player, "quest.general.quest/lf_deadmite.questgiver", waypoint1);
        setWaypointName(waypoint1, "Risha Sinan");
    }
    public void lf_killmite_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void lf_killmite_action_giverewardcraft(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_deadmite.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, "quest.general.quest/lf_deadmite.waypoint");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/lf_deadmite.questgiver");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/lf_deadmite.questgiver");
        int questId = questGetQuestId("quest/lf_deadmite");
        questCompleteTask(questId, 6, player);
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/loot/loot_schematic/bubble_tank_schematic.iff";
        createObject(templateName, playerInventory, "");
    }
    public void lf_killmite_action_giverewardcombat(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_deadmite.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, "quest.general.quest/lf_deadmite.waypoint");
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/lf_deadmite.questgiver");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/lf_deadmite.questgiver");
        int questId = questGetQuestId("quest/lf_deadmite");
        questCompleteTask(questId, 6, player);
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/skill_buff/skill_buff_pistol_accuracy.iff";
        createObject(templateName, playerInventory, "");
    }
    public void lf_killmite_action_givewaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "quest.general.quest/lf_deadmite.waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 3572;
        loc.y = 91;
        loc.z = 5424;
        waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/lf_deadmite.waypoint", waypoint);
        setWaypointName(waypoint, "Giant Mite Cave");
        setWaypointActive(waypoint, true);
    }
    public int lf_killmite_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.lf_killmite.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_177"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_183"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_185");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_177");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                    }
                    utils.setScriptVar(player, "conversation.lf_killmite.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_killmite_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_167"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                lf_killmite_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_169");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_172"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                lf_killmite_action_givequest(player, npc);
                string_id message = new string_id(c_stringFile, "s_175");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_killmite_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.lf_killmite.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_177"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_183"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_185");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_177");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                    }
                    utils.setScriptVar(player, "conversation.lf_killmite.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_killmite_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_191"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_193");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lf_killmite_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_196");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                    }
                    utils.setScriptVar(player, "conversation.lf_killmite.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_207"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_209");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_212"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lf_killmite_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_196"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                lf_killmite_action_giverewardcraft(player, npc);
                string_id message = new string_id(c_stringFile, "s_199");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_201"))
        {
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                lf_killmite_action_giverewardcombat(player, npc);
                string_id message = new string_id(c_stringFile, "s_204");
                utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
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
            detachScript(self, "conversation.lf_killmite");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Risha Sinan");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Risha Sinan");
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
        detachScript(self, "conversation.lf_killmite");
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
        if (!lf_killmite_condition_questtaken(player, npc))
        {
            lf_killmite_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_159");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lf_killmite_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_177");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                }
                utils.setScriptVar(player, "conversation.lf_killmite.branchId", 1);
                npcStartConversation(player, npc, "lf_killmite", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_killmite_condition_ifquestinprogress(player, npc))
        {
            lf_killmite_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_188");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lf_killmite_condition_ifquestiscompleted(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lf_killmite_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lf_killmite_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                }
                utils.setScriptVar(player, "conversation.lf_killmite.branchId", 7);
                npcStartConversation(player, npc, "lf_killmite", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lf_killmite_condition_questrewarded(player, npc))
        {
            lf_killmite_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_217");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("lf_killmite"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lf_killmite.branchId");
        if (branchId == 1 && lf_killmite_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && lf_killmite_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && lf_killmite_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lf_killmite_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && lf_killmite_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lf_killmite.branchId");
        return SCRIPT_CONTINUE;
    }
}
