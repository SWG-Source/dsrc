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
import script.library.npe;
import script.library.skill;
import script.library.space_quest;
import script.library.utils;

public class npe_side_dungeon_soldier extends script.base_script
{
    public npe_side_dungeon_soldier()
    {
    }
    public static String c_stringFile = "conversation/npe_side_dungeon_soldier";
    public boolean npe_side_dungeon_soldier_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_side_dungeon_soldier_condition_hasFinishedTemplate(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.finishedTemplate");
    }
    public boolean npe_side_dungeon_soldier_condition_stillOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_side_dungeon_soldier", "kill_soldier");
    }
    public boolean npe_side_dungeon_soldier_condition_taskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((groundquests.isTaskActive(player, "npe_side_dungeon_soldier", "return")) && !(groundquests.hasCompletedQuest(player, "npe_side_dungeon_soldier")));
    }
    public boolean npe_side_dungeon_soldier_condition_canTakeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "npe_side_dungeon_soldier");
    }
    public void npe_side_dungeon_soldier_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_side_dungeon_soldier");
        npe.giveStationWaypoint(player);
        if (!hasObjVar(player, npe.QUEST_REWORK_VAR))
        {
            setObjVar(player, npe.QUEST_REWORK_VAR, npe.QUEST_ENUMERATION);
        }
    }
    public void npe_side_dungeon_soldier_action_singalEnd(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_side_soldier_signal");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_side_dungeon_soldier_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int npe_side_dungeon_soldier_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_dungeon_soldier_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                npe_side_dungeon_soldier_action_singalEnd(player, npc);
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_dungeon_soldier_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_dungeon_soldier_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_dungeon_soldier_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_dungeon_soldier_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                npe_side_dungeon_soldier_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_dungeon_soldier_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
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
            detachScript(self, "conversation.npe_side_dungeon_soldier");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Ysanna");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Ysanna");
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
        detachScript(self, "conversation.npe_side_dungeon_soldier");
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
        if (npe_side_dungeon_soldier_condition_taskComplete(player, npc))
        {
            npe_side_dungeon_soldier_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 1);
                npcStartConversation(player, npc, "npe_side_dungeon_soldier", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_side_dungeon_soldier_condition_stillOnTask(player, npc))
        {
            npe_side_dungeon_soldier_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 4);
                npcStartConversation(player, npc, "npe_side_dungeon_soldier", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!npe_side_dungeon_soldier_condition_hasFinishedTemplate(player, npc))
        {
            npe_side_dungeon_soldier_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_side_dungeon_soldier_condition_canTakeQuest(player, npc))
        {
            npe_side_dungeon_soldier_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_22");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId", 7);
                npcStartConversation(player, npc, "npe_side_dungeon_soldier", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_side_dungeon_soldier_condition__defaultCondition(player, npc))
        {
            npe_side_dungeon_soldier_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_side_dungeon_soldier"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
        if (branchId == 1 && npe_side_dungeon_soldier_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && npe_side_dungeon_soldier_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && npe_side_dungeon_soldier_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_side_dungeon_soldier_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_side_dungeon_soldier_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_side_dungeon_soldier_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && npe_side_dungeon_soldier_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_side_dungeon_soldier.branchId");
        return SCRIPT_CONTINUE;
    }
}
