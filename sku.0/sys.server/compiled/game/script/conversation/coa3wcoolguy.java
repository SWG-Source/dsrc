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
import script.library.factions;
import script.library.utils;

public class coa3wcoolguy extends script.base_script
{
    public coa3wcoolguy()
    {
    }
    public static String c_stringFile = "conversation/coa3wcoolguy";
    public boolean coa3wcoolguy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3wcoolguy_condition_spokeCoordinatorLast(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == -401);
    }
    public boolean coa3wcoolguy_condition_finalMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == -402);
    }
    public boolean coa3wcoolguy_condition_hasWonStory(obj_id player, obj_id npc) throws InterruptedException
    {
        return (badge.hasBadge(player, "event_coa3_rebel"));
    }
    public boolean coa3wcoolguy_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        String pvpFaction = factions.getFaction(player);
        if (!pvpFaction.equals("Rebel"))
        {
            return false;
        }
        return true;
    }
    public void coa3wcoolguy_action_getFinalMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", -402);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 5);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3wcoolguy_action_refreshFinalMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", -402);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 5);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3wcoolguy_action_abortFinalMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", -401);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 5);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public int coa3wcoolguy_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62241cb7"))
        {
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9ebad40b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wcoolguy_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3wcoolguy_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3wcoolguy_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d768eb98");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c3cd444");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1ae6871");
                    }
                    utils.setScriptVar(player, "conversation.coa3wcoolguy.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_14"))
        {
            if (!coa3wcoolguy_condition_isCorrectFaction(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                coa3wcoolguy_action_getFinalMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3wcoolguy_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d768eb98"))
        {
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f3080e5");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c3cd444"))
        {
            if (!coa3wcoolguy_condition_isCorrectFaction(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                coa3wcoolguy_action_getFinalMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0e4cb7a");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d1ae6871"))
        {
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cc92be25");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3wcoolguy_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83b68081"))
        {
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cf3aed9b");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dc616180"))
        {
            coa3wcoolguy_action_refreshFinalMission(player, npc);
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bb33eb73");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6428dedb"))
        {
            coa3wcoolguy_action_abortFinalMission(player, npc);
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
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
            detachScript(self, "conversation.coa3wcoolguy");
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
        detachScript(self, "conversation.coa3wcoolguy");
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
        if (coa3wcoolguy_condition_hasWonStory(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c500ae34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3wcoolguy_condition_spokeCoordinatorLast(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a85b1da");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62241cb7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                utils.setScriptVar(player, "conversation.coa3wcoolguy.branchId", 2);
                npcStartConversation(player, npc, "coa3wcoolguy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wcoolguy_condition_finalMissionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a4053724");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3wcoolguy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_83b68081");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dc616180");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6428dedb");
                }
                utils.setScriptVar(player, "conversation.coa3wcoolguy.branchId", 11);
                npcStartConversation(player, npc, "coa3wcoolguy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wcoolguy_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a3621ded");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3wcoolguy"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.coa3wcoolguy.branchId");
        if (branchId == 2 && coa3wcoolguy_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && coa3wcoolguy_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && coa3wcoolguy_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.coa3wcoolguy.branchId");
        return SCRIPT_CONTINUE;
    }
}
