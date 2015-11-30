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

public class ep3_myyydril_lorn_servant extends script.base_script
{
    public ep3_myyydril_lorn_servant()
    {
    }
    public static String c_stringFile = "conversation/ep3_myyydril_lorn_servant";
    public boolean ep3_myyydril_lorn_servant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_myyydril_lorn_servant_condition_isTaskOneActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_myyydril_lorn_retrieve_6", "taskRetrieveCrystal");
    }
    public boolean ep3_myyydril_lorn_servant_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_myyydril_lorn_retrieve_6", "lornRetrieveCompleted");
    }
    public boolean ep3_myyydril_lorn_servant_condition_hasBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public boolean ep3_myyydril_lorn_servant_condition_isValidforEncounter(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isGod(player))
        {
            return true;
        }
        if (!hasObjVar(player, "instance_lockout.myyydril_grievous.lockout_end"))
        {
            return true;
        }
        int lockoutEnd = getIntObjVar(player, "instance_lockout.myyydril_grievous.lockout_end");
        if (getGameTime() >= lockoutEnd)
        {
            return true;
        }
        return false;
    }
    public boolean ep3_myyydril_lorn_servant_condition_IfisGod(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public void ep3_myyydril_lorn_servant_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_lorn_retrieve_6");
    }
    public void ep3_myyydril_lorn_servant_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("player", player);
        obj_id top = getTopMostContainer(player);
        messageTo(top, "beginEncounter", dict, 1.0f, false);
        groundquests.grantQuest(player, "ep3_myyydril_lorn_talkto");
    }
    public int ep3_myyydril_lorn_servant_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_446"))
        {
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_450");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_454");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_492"))
        {
            ep3_myyydril_lorn_servant_action_grantQuestTwo(player, npc);
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_496");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_lorn_servant_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_454"))
        {
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_460");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_468");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_lorn_servant_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_468"))
        {
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_476");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_lorn_servant_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_476"))
        {
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_480");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_484");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_lorn_servant_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_484"))
        {
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_lorn_servant_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_488");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
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
            detachScript(self, "conversation.ep3_myyydril_lorn_servant");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_myyydril_lorn_servant");
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
        if (!ep3_myyydril_lorn_servant_condition_isValidforEncounter(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_418");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_lorn_servant_condition_hasBadge(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_430");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_lorn_servant_condition_hasCompletedTaskOne(player, npc))
        {
            ep3_myyydril_lorn_servant_action_grantQuestTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_434");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_lorn_servant_condition_isTaskOneActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_438");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_442");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_lorn_servant_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_lorn_servant_condition_IfisGod(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_446");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_492");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId", 5);
                npcStartConversation(player, npc, "ep3_myyydril_lorn_servant", message, responses);
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
        if (!conversationId.equals("ep3_myyydril_lorn_servant"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
        if (branchId == 5 && ep3_myyydril_lorn_servant_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_myyydril_lorn_servant_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_myyydril_lorn_servant_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_myyydril_lorn_servant_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_myyydril_lorn_servant_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_myyydril_lorn_servant.branchId");
        return SCRIPT_CONTINUE;
    }
}
