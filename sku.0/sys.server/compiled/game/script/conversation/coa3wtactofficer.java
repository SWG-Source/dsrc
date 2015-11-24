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
import script.library.chat;
import script.library.utils;

public class coa3wtactofficer extends script.base_script
{
    public coa3wtactofficer()
    {
    }
    public static String c_stringFile = "conversation/coa3wtactofficer";
    public boolean coa3wtactofficer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3wtactofficer_condition_extraFloraMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.extraFlora") == -1);
    }
    public boolean coa3wtactofficer_condition_hasWonStory(obj_id player, obj_id npc) throws InterruptedException
    {
        return (badge.hasBadge(player, "event_coa3_rebel"));
    }
    public boolean coa3wtactofficer_condition_storyFloraMissionComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") < -302);
    }
    public boolean coa3wtactofficer_condition_talkedToCoordinator(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == -301);
    }
    public boolean coa3wtactofficer_condition_storyFloraMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == -302);
    }
    public void coa3wtactofficer_action_refreshExtraFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3wtactofficer_action_abortExtraFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "coa3.extraFlora");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", -1);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public void coa3wtactofficer_action_getExtraFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.extraFlora", -1);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3wtactofficer_action_getStoryFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", -302);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3wtactofficer_action_refreshStoryFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", -302);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3wtactofficer_action_abortStoryFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", -301);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public int coa3wtactofficer_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_688721b0"))
        {
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38f00205");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2fe6350"))
        {
            coa3wtactofficer_action_refreshExtraFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_268282e6");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2e791c9d"))
        {
            coa3wtactofficer_action_abortExtraFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3d82703f");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3wtactofficer_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1cb2157b"))
        {
            coa3wtactofficer_action_getExtraFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1f281089");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5166bfb2"))
        {
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_885ae409");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3wtactofficer_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            coa3wtactofficer_action_getExtraFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1b8e1fa4"))
        {
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29fae614");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3wtactofficer_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f96e5eed"))
        {
            coa3wtactofficer_action_getStoryFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1b5eac32");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e4daff30"))
        {
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83258fb0");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3wtactofficer_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ba1a3080"))
        {
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e40779f7");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d1b9304"))
        {
            coa3wtactofficer_action_refreshStoryFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            coa3wtactofficer_action_abortStoryFloraMission(player, npc);
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a582260");
                utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
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
            detachScript(self, "conversation.coa3wtactofficer");
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
        detachScript(self, "conversation.coa3wtactofficer");
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
        if (coa3wtactofficer_condition_extraFloraMissionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_743c9f18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_688721b0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f2fe6350");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2e791c9d");
                }
                utils.setScriptVar(player, "conversation.coa3wtactofficer.branchId", 1);
                npcStartConversation(player, npc, "coa3wtactofficer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wtactofficer_condition_hasWonStory(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ddef5a7b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1cb2157b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5166bfb2");
                }
                utils.setScriptVar(player, "conversation.coa3wtactofficer.branchId", 5);
                npcStartConversation(player, npc, "coa3wtactofficer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wtactofficer_condition_storyFloraMissionComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_acadf21d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1b8e1fa4");
                }
                utils.setScriptVar(player, "conversation.coa3wtactofficer.branchId", 8);
                npcStartConversation(player, npc, "coa3wtactofficer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wtactofficer_condition_talkedToCoordinator(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b8e8976a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f96e5eed");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e4daff30");
                }
                utils.setScriptVar(player, "conversation.coa3wtactofficer.branchId", 11);
                npcStartConversation(player, npc, "coa3wtactofficer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wtactofficer_condition_storyFloraMissionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3c57b03");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3wtactofficer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ba1a3080");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8d1b9304");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.coa3wtactofficer.branchId", 14);
                npcStartConversation(player, npc, "coa3wtactofficer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wtactofficer_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f64d217f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3wtactofficer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.coa3wtactofficer.branchId");
        if (branchId == 1 && coa3wtactofficer_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && coa3wtactofficer_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && coa3wtactofficer_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && coa3wtactofficer_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && coa3wtactofficer_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.coa3wtactofficer.branchId");
        return SCRIPT_CONTINUE;
    }
}
