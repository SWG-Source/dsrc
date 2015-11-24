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

public class padawan_chef_01 extends script.base_script
{
    public padawan_chef_01()
    {
    }
    public static String c_stringFile = "conversation/padawan_chef_01";
    public boolean padawan_chef_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_chef_01_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            String trialName = jedi_trials.getJediTrialName(player);
            if (trialName != null && trialName.equals("chef"))
            {
                return !hasObjVar(npc, "padawan_trials.playerFailed");
            }
        }
        return false;
    }
    public boolean padawan_chef_01_condition_acceptedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask");
        }
        return false;
    }
    public boolean padawan_chef_01_condition_wasTargetEliminated(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.killedTarget");
        }
        return false;
    }
    public boolean padawan_chef_01_condition_completedSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            String trialName = jedi_trials.getJediTrialName(player);
            if (trialName != null && !trialName.equals("chef"))
            {
                return hasObjVar(npc, "padawan_trials.playerSucceeded");
            }
        }
        return false;
    }
    public boolean padawan_chef_01_condition_spokeToTarget(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01");
        }
        return false;
    }
    public void padawan_chef_01_action_questFailure(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerFailed", true);
        messageTo(player, "handleQuestFailure", null, 1, false);
        return;
    }
    public void padawan_chef_01_action_questSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerSucceeded", true);
        messageTo(player, "handleTrialComplete", null, 1, false);
        return;
    }
    public void padawan_chef_01_action_sendToNextNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerAccepted", true);
        setObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask", true);
        messageTo(player, "handleSetSecondLoc", null, 1, false);
        return;
    }
    public int padawan_chef_01_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7c9388b6"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                padawan_chef_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_edc10a84");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_01_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53e26aec"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                padawan_chef_01_action_questSuccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_9bf6042f");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_01_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bc5c7c4a"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d5ab69d1");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3f4dfda1"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                padawan_chef_01_action_questSuccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_7c44e561");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_01_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a7c3aea6"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_959fc17d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94e89675");
                    }
                    utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9bcbdc35"))
        {
            doAnimationAction(player, "laugh_cackle");
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56b146aa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_chef_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78228c87");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ba9c89d");
                    }
                    utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_01_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94e89675"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8a0862a1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_chef_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_01_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d70dba34"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                padawan_chef_01_action_sendToNextNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_b654720c");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c695dbd"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                padawan_chef_01_action_questSuccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_f68302e8");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_01_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78228c87"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_959fc17d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94e89675");
                    }
                    utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1ba9c89d"))
        {
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                padawan_chef_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_8cddb8eb");
                utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
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
            detachScript(self, "conversation.padawan_chef_01");
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
        detachScript(self, "conversation.padawan_chef_01");
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
        if (padawan_chef_01_condition_completedSuccess(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_e15ebf39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (padawan_chef_01_condition_wasTargetEliminated(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_be6b7d06");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7c9388b6");
                }
                utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 2);
                npcStartConversation(player, npc, "padawan_chef_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_chef_01_condition_spokeToTarget(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_236213e1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53e26aec");
                }
                utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 4);
                npcStartConversation(player, npc, "padawan_chef_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_chef_01_condition_acceptedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_39d844c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bc5c7c4a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3f4dfda1");
                }
                utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 6);
                npcStartConversation(player, npc, "padawan_chef_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_chef_01_condition_isTrialPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_58e01006");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_chef_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_chef_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a7c3aea6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9bcbdc35");
                }
                utils.setScriptVar(player, "conversation.padawan_chef_01.branchId", 9);
                npcStartConversation(player, npc, "padawan_chef_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_chef_01_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_32e459b5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_chef_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.padawan_chef_01.branchId");
        if (branchId == 2 && padawan_chef_01_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && padawan_chef_01_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && padawan_chef_01_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && padawan_chef_01_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && padawan_chef_01_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && padawan_chef_01_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && padawan_chef_01_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.padawan_chef_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
