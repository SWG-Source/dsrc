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

public class ep3_etyyy_ryoo_finn extends script.base_script
{
    public ep3_etyyy_ryoo_finn()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_ryoo_finn";
    public boolean ep3_etyyy_ryoo_finn_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_ryoo_finn_condition_hasCompletedRyooQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_johnson_retrieve_ryoos_stash");
    }
    public boolean ep3_etyyy_ryoo_finn_condition_foundRyoosSalt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_johnson_retrieve_ryoos_stash", "johnson_ryoosSalt");
    }
    public boolean ep3_etyyy_ryoo_finn_condition_isRetrievingRyoosSalt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_johnson_retrieve_ryoos_stash", "johnson_findRyoosStash");
    }
    public boolean ep3_etyyy_ryoo_finn_condition_speakWithRyoo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_johnson_retrieve_ryoos_stash", "johnson_talkToRyoo");
    }
    public void ep3_etyyy_ryoo_finn_action_retrieveRyoosStash(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "johnson_talkToRyoo");
    }
    public int ep3_etyyy_ryoo_finn_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1205"))
        {
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1207");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1215");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1209"))
        {
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1211");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ryoo_finn_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1214"))
        {
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1218");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1219");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1215"))
        {
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1217");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ryoo_finn_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1218"))
        {
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ryoo_finn_action_retrieveRyoosStash(player, npc);
                string_id message = new string_id(c_stringFile, "s_1221");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1219"))
        {
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1220");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_ryoo_finn");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.ep3_etyyy_ryoo_finn");
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
        if (ep3_etyyy_ryoo_finn_condition_hasCompletedRyooQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1179");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ryoo_finn_condition_foundRyoosSalt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1222");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ryoo_finn_condition_isRetrievingRyoosSalt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1197");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ryoo_finn_condition_speakWithRyoo(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1203");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1205");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1209");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId", 4);
                npcStartConversation(player, npc, "ep3_etyyy_ryoo_finn", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ryoo_finn_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1213");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_ryoo_finn"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
        if (branchId == 4 && ep3_etyyy_ryoo_finn_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_ryoo_finn_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_etyyy_ryoo_finn_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_ryoo_finn.branchId");
        return SCRIPT_CONTINUE;
    }
}
