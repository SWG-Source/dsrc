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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class rancor_boss_fight_controller extends script.base_script
{
    public rancor_boss_fight_controller()
    {
    }
    public static String c_stringFile = "conversation/rancor_boss_fight_controller";
    public boolean rancor_boss_fight_controller_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean rancor_boss_fight_controller_condition_hasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "outbreak_undead_rancor_boss_fight");
    }
    public boolean rancor_boss_fight_controller_condition_hasGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id groupid = getGroupObject(player);
        if (!isValidId(groupid))
        {
            return false;
        }
        return true;
    }
    public boolean rancor_boss_fight_controller_condition_hasCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "outbreak_undead_rancor_boss_fight");
    }
    public boolean rancor_boss_fight_controller_condition_hasQuestAndGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!rancor_boss_fight_controller_condition_hasGroup(player, npc))
        {
            return false;
        }
        if (!rancor_boss_fight_controller_condition_hasQuest(player, npc))
        {
            return false;
        }
        return groundquests.isTaskActive(player, "outbreak_undead_rancor_boss_fight", "defeatUndeadRancor");
    }
    public boolean rancor_boss_fight_controller_condition_wave_event_active(obj_id player, obj_id npc) throws InterruptedException
    {
        int wave = utils.getIntScriptVar(npc, "waveEventCurrentWave");
        return wave > 0;
    }
    public void rancor_boss_fight_controller_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location loc = new location(-7342, 505, -7536, "dathomir");
        obj_id wpt = createWaypointInDatapad(player, loc);
        setWaypointName(wpt, "Command Console");
        setWaypointActive(wpt, true);
    }
    public void rancor_boss_fight_controller_action_bringRancorOut(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id groupid = getGroupObject(player);
        if (!isValidId(groupid))
        {
            return;
        }
        dictionary webster = new dictionary();
        webster.put("player", player);
        messageTo(npc, "waveEventControllerNPCStart", webster, 0, false);
    }
    public void rancor_boss_fight_controller_action_getQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "outbreak_undead_rancor_boss_fight");
    }
    public void rancor_boss_fight_controller_action_removeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "outbreak_undead_rancor_boss_fight");
    }
    public int rancor_boss_fight_controller_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_176"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_177");
                utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rancor_boss_fight_controller_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                rancor_boss_fight_controller_action_getQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_19");
                utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rancor_boss_fight_controller_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                rancor_boss_fight_controller_action_removeQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rancor_boss_fight_controller_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_171"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_172");
                utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rancor_boss_fight_controller_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "scream");
                string_id message = new string_id(c_stringFile, "s_153");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_173");
                    }
                    utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rancor_boss_fight_controller_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_173"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                rancor_boss_fight_controller_action_bringRancorOut(player, npc);
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rancor_boss_fight_controller_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
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
    public int handleQuestFlavorObjectCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "rancor_boss_fight_controller.handleQuestFlavorObjectCleanup() Params invalid.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "rancor_boss_fight_controller.handleQuestFlavorObjectCleanup() player param not found.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("taskCompleted"))
        {
            CustomerServiceLog("outbreak_themepark", "rancor_boss_fight_controller.handleQuestFlavorObjectCleanup() taskCompleted param not found.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "rancor_boss_fight_controller.handleQuestFlavorObjectCleanup() player OID invalid.");
            return SCRIPT_CONTINUE;
        }
        boolean taskCompleted = params.getBoolean("taskCompleted");
        if (!taskCompleted)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] groupMembersInRange = groundquests.getGroupMembersInRange(self, player);
        if (groupMembersInRange == null || groupMembersInRange.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "rancor_boss_fight_controller.handleQuestFlavorObjectCleanup() groupMembersInRange was NULL. Still rewarding player:" + player);
            if (!hasCompletedCollectionSlot(player, "undead_rancor_killed"))
            {
                modifyCollectionSlotValue(player, "undead_rancor_killed", 1);
            }
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < groupMembersInRange.length; i++)
        {
            if (!hasCompletedCollectionSlot(groupMembersInRange[i], "undead_rancor_killed"))
            {
                modifyCollectionSlotValue(groupMembersInRange[i], "undead_rancor_killed", 1);
            }
        }
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
        if (!rancor_boss_fight_controller_condition_hasGroup(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_175");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                }
                utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 1);
                npcStartConversation(player, npc, "rancor_boss_fight_controller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!rancor_boss_fight_controller_condition_hasQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!rancor_boss_fight_controller_condition_hasCompleted(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rancor_boss_fight_controller_condition_hasCompleted(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 3);
                npcStartConversation(player, npc, "rancor_boss_fight_controller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rancor_boss_fight_controller_condition_wave_event_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_165");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_171");
                }
                utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 7);
                npcStartConversation(player, npc, "rancor_boss_fight_controller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rancor_boss_fight_controller_condition_hasQuestAndGroup(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 9);
                npcStartConversation(player, npc, "rancor_boss_fight_controller", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rancor_boss_fight_controller_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                }
                utils.setScriptVar(player, "conversation.rancor_boss_fight_controller.branchId", 12);
                npcStartConversation(player, npc, "rancor_boss_fight_controller", message, responses);
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
        if (!conversationId.equals("rancor_boss_fight_controller"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
        if (branchId == 1 && rancor_boss_fight_controller_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && rancor_boss_fight_controller_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && rancor_boss_fight_controller_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && rancor_boss_fight_controller_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && rancor_boss_fight_controller_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && rancor_boss_fight_controller_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && rancor_boss_fight_controller_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rancor_boss_fight_controller.branchId");
        return SCRIPT_CONTINUE;
    }
}
