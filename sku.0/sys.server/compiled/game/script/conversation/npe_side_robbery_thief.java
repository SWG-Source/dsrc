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

public class npe_side_robbery_thief extends script.base_script
{
    public npe_side_robbery_thief()
    {
    }
    public static String c_stringFile = "conversation/npe_side_robbery_thief";
    public boolean npe_side_robbery_thief_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_side_robbery_thief_condition_playerOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_side_robbery");
    }
    public boolean npe_side_robbery_thief_condition_playerDoneWithQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_side_robbery");
    }
    public boolean npe_side_robbery_thief_condition_playerdecided(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "npe_side_robbery", "temptation");
    }
    public boolean npe_side_robbery_thief_condition_playertookbribe(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_side_robbery", "hasfake");
    }
    public boolean npe_side_robbery_thief_condition_gimmeproto(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_side_robbery", "gimmeproto");
    }
    public void npe_side_robbery_thief_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_side_robbery_thief_action_signalfind(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_side1_found_thief");
    }
    public void npe_side_robbery_thief_action_takebribe(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.failTask(player, "npe_side_robbery", "temptation");
    }
    public void npe_side_robbery_thief_action_nobribe(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_side1_bribe");
    }
    public void npe_side_robbery_thief_action_signalprototaken(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_side1_gimme_proto");
    }
    public int npe_side_robbery_thief_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_robbery_thief_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                npe_side_robbery_thief_action_signalfind(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_robbery_thief_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                npe_side_robbery_thief_action_signalfind(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_robbery_thief_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_robbery_thief_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                npe_side_robbery_thief_action_nobribe(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                npe_side_robbery_thief_action_takebribe(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_side_robbery_thief_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                npe_side_robbery_thief_action_nobribe(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
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
            detachScript(self, "conversation.npe_side_robbery_thief");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Thief");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Thief");
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
        detachScript(self, "conversation.npe_side_robbery_thief");
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
        if (npe_side_robbery_thief_condition_playerDoneWithQuest(player, npc))
        {
            npe_side_robbery_thief_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_23");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_side_robbery_thief_condition_playertookbribe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_side_robbery_thief_condition_playerdecided(player, npc))
        {
            npe_side_robbery_thief_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_side_robbery_thief_condition_gimmeproto(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 3);
                npcStartConversation(player, npc, "npe_side_robbery_thief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shoo");
            npe_side_robbery_thief_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_side_robbery_thief_condition_playerOnQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_side_robbery_thief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.npe_side_robbery_thief.branchId", 6);
                npcStartConversation(player, npc, "npe_side_robbery_thief", message, responses);
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
        if (!conversationId.equals("npe_side_robbery_thief"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
        if (branchId == 3 && npe_side_robbery_thief_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_side_robbery_thief_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_side_robbery_thief_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_side_robbery_thief_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_side_robbery_thief_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_side_robbery_thief_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_side_robbery_thief.branchId");
        return SCRIPT_CONTINUE;
    }
}
