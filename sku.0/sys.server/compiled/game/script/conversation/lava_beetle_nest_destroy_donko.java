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

public class lava_beetle_nest_destroy_donko extends script.base_script
{
    public lava_beetle_nest_destroy_donko()
    {
    }
    public static String c_stringFile = "conversation/lava_beetle_nest_destroy_donko";
    public boolean lava_beetle_nest_destroy_donko_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lava_beetle_nest_destroy_donko_condition_isMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "som_lava_beetle_nest_destroy") || groundquests.isQuestActive(player, "som_lava_beetle_nest_destroy_2"));
    }
    public boolean lava_beetle_nest_destroy_donko_condition_isOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "mustafar_lava_beetle_nest_four") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "mustafar_lava_beetle_nest_four"));
    }
    public boolean lava_beetle_nest_destroy_donko_condition_isMissionComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "som_lava_beetle_nest_destroy") || groundquests.hasCompletedQuest(player, "som_lava_beetle_nest_destroy_2"));
    }
    public boolean lava_beetle_nest_destroy_donko_condition_failedMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "lava_beetle_nest_tasks") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "lava_beetle_nest_tasks") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "mustafar_lava_beetle_nest_two") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "mustafar_lava_beetle_nest_two"));
    }
    public void lava_beetle_nest_destroy_donko_action_grantMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_lava_beetle_nest_destroy_2");
        if (utils.hasScriptVarTree(player, "beetle_nest"))
        {
            debugSpeakMsg(player, "I have the scriptvar");
            utils.removeScriptVarTree(player, "beetle_nest");
        }
    }
    public void lava_beetle_nest_destroy_donko_action_grantReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mustafar_lava_beetle_nest_reward");
    }
    public void lava_beetle_nest_destroy_donko_action_regrantMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_lava_beetle_nest_destroy_2");
    }
    public void lava_beetle_nest_destroy_donko_action_clearMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "som_lava_beetle_nest_destroy");
        groundquests.clearQuest(player, "som_lava_beetle_nest_destroy_2");
        if (utils.hasScriptVarTree(player, "beetle_nest"))
        {
            utils.removeScriptVarTree(player, "beetle_nest");
        }
    }
    public int lava_beetle_nest_destroy_donko_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            doAnimationAction(player, "explain");
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wtf");
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                    }
                    utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lava_beetle_nest_destroy_donko_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shush");
                lava_beetle_nest_destroy_donko_action_grantReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lava_beetle_nest_destroy_donko_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35"))
        {
            doAnimationAction(player, "explain");
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lava_beetle_nest_destroy_donko_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                lava_beetle_nest_destroy_donko_action_clearMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            lava_beetle_nest_destroy_donko_action_clearMission(player, npc);
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "manipulate_medium");
                lava_beetle_nest_destroy_donko_action_grantMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lava_beetle_nest_destroy_donko_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            doAnimationAction(player, "catchbreath");
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shakefist");
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lava_beetle_nest_destroy_donko_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "shrug_hands");
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "explain");
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lava_beetle_nest_destroy_donko_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            doAnimationAction(player, "catchbreath");
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                lava_beetle_nest_destroy_donko_action_grantMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
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
            detachScript(self, "conversation.lava_beetle_nest_destroy_donko");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Foreman Donko Jen");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Foreman Donko Jen");
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
        detachScript(self, "conversation.lava_beetle_nest_destroy_donko");
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
        if (lava_beetle_nest_destroy_donko_condition_isMissionComplete(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lava_beetle_nest_destroy_donko_condition_isOnTask(player, npc))
        {
            doAnimationAction(npc, "thumbs_up");
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 2);
                npcStartConversation(player, npc, "lava_beetle_nest_destroy_donko", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lava_beetle_nest_destroy_donko_condition_isMissionActive(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lava_beetle_nest_destroy_donko_condition_failedMission(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                }
                utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 5);
                npcStartConversation(player, npc, "lava_beetle_nest_destroy_donko", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "handshake_tandem");
            doAnimationAction(player, "handshake_tandem");
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lava_beetle_nest_destroy_donko_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId", 10);
                npcStartConversation(player, npc, "lava_beetle_nest_destroy_donko", message, responses);
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
        if (!conversationId.equals("lava_beetle_nest_destroy_donko"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
        if (branchId == 2 && lava_beetle_nest_destroy_donko_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && lava_beetle_nest_destroy_donko_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && lava_beetle_nest_destroy_donko_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lava_beetle_nest_destroy_donko_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && lava_beetle_nest_destroy_donko_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && lava_beetle_nest_destroy_donko_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && lava_beetle_nest_destroy_donko_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lava_beetle_nest_destroy_donko.branchId");
        return SCRIPT_CONTINUE;
    }
}
