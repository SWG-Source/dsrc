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
import script.library.jedi_trials;
import script.library.utils;

public class padawan_pannaqa_01 extends script.base_script
{
    public padawan_pannaqa_01()
    {
    }
    public static String c_stringFile = "conversation/padawan_pannaqa_01";
    public boolean padawan_pannaqa_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_pannaqa_01_condition_acceptedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && trialName.equals("pannaqa"))
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask");
        }
        return false;
    }
    public boolean padawan_pannaqa_01_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && trialName.equals("pannaqa"))
        {
            return !hasObjVar(player, "jedi_trials.padawan_trials.pannaqa.failed");
        }
        return false;
    }
    public boolean padawan_pannaqa_01_condition_spokeToTarget01(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && trialName.equals("pannaqa"))
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01");
        }
        return false;
    }
    public boolean padawan_pannaqa_01_condition_spokeToTarget02(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && trialName.equals("pannaqa"))
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_02");
        }
        return false;
    }
    public boolean padawan_pannaqa_01_condition_completedSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && !trialName.equals("pannaqa"))
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.pannaqa.success");
        }
        return false;
    }
    public void padawan_pannaqa_01_action_sendToSecondNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerAccepted", true);
        setObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask", true);
        messageTo(player, "handleSetSecondLoc", null, 1, false);
        return;
    }
    public void padawan_pannaqa_01_action_questFailure(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "jedi_trials.padawan_trials.pannaqa.failed", true);
        messageTo(player, "handleQuestFailure", null, 1, false);
        return;
    }
    public void padawan_pannaqa_01_action_questSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "jedi_trials.padawan_trials.pannaqa.success", true);
        if (hasObjVar(player, "jedi_trials.padawan_trials.pannaqa.failed"))
        {
            removeObjVar(player, "jedi_trials.padawan_trials.pannaqa.failed");
        }
        messageTo(player, "handleTrialComplete", null, 1, false);
        return;
    }
    public int padawan_pannaqa_01_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3a3e0d5d"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                padawan_pannaqa_01_action_questSuccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_42e0a11e");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_pannaqa_01_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30546869"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52da7aa");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b336be8b"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                padawan_pannaqa_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_24a1738d");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_pannaqa_01_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_387d18ec"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                padawan_pannaqa_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_pannaqa_01_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_df607c49"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_53c84ebd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd5fc3e4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ed2f6bdb");
                    }
                    utils.setScriptVar(player, "conversation.padawan_pannaqa_01.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fd022a50"))
        {
            doAnimationAction(player, "point_accusingly");
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                padawan_pannaqa_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_pannaqa_01_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dd5fc3e4"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194a6a4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69af28a6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad0eb2bb");
                    }
                    utils.setScriptVar(player, "conversation.padawan_pannaqa_01.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ed2f6bdb"))
        {
            doAnimationAction(player, "belly_laugh");
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                padawan_pannaqa_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_pannaqa_01_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69af28a6"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "accept_affection");
                padawan_pannaqa_01_action_sendToSecondNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_bbfeec70");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ad0eb2bb"))
        {
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                padawan_pannaqa_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_72d5a837");
                utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
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
            detachScript(self, "conversation.padawan_pannaqa_01");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Pannaqa (a farmer)");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Pannaqa (a farmer)");
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
        detachScript(self, "conversation.padawan_pannaqa_01");
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
        if (padawan_pannaqa_01_condition_completedSuccess(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            string_id message = new string_id(c_stringFile, "s_be0f3920");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (padawan_pannaqa_01_condition_spokeToTarget02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b2216c3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a3e0d5d");
                }
                utils.setScriptVar(player, "conversation.padawan_pannaqa_01.branchId", 2);
                npcStartConversation(player, npc, "padawan_pannaqa_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_pannaqa_01_condition_spokeToTarget01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b9c3a3be");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30546869");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b336be8b");
                }
                utils.setScriptVar(player, "conversation.padawan_pannaqa_01.branchId", 4);
                npcStartConversation(player, npc, "padawan_pannaqa_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_pannaqa_01_condition_acceptedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6d11135b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_387d18ec");
                }
                utils.setScriptVar(player, "conversation.padawan_pannaqa_01.branchId", 7);
                npcStartConversation(player, npc, "padawan_pannaqa_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_pannaqa_01_condition_isTrialPlayer(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_41aab3ed");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_df607c49");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fd022a50");
                }
                utils.setScriptVar(player, "conversation.padawan_pannaqa_01.branchId", 10);
                npcStartConversation(player, npc, "padawan_pannaqa_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_pannaqa_01_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            string_id message = new string_id(c_stringFile, "s_1843ab7e");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_pannaqa_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
        if (branchId == 2 && padawan_pannaqa_01_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && padawan_pannaqa_01_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && padawan_pannaqa_01_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && padawan_pannaqa_01_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && padawan_pannaqa_01_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && padawan_pannaqa_01_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.padawan_pannaqa_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
