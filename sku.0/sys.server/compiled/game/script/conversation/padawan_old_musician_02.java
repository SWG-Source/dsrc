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
import script.library.jedi_trials;
import script.library.utils;

public class padawan_old_musician_02 extends script.base_script
{
    public padawan_old_musician_02()
    {
    }
    public static String c_stringFile = "conversation/padawan_old_musician_02";
    public boolean padawan_old_musician_02_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_old_musician_02_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && trialName.equals("old_musician"))
        {
            return (!hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01") && hasObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask"));
        }
        return false;
    }
    public void padawan_old_musician_02_action_questFailure(obj_id player, obj_id npc) throws InterruptedException
    {
        messageTo(player, "handleQuestFailure", null, 1, false);
        return;
    }
    public void padawan_old_musician_02_action_sendToThirdNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01", true);
        messageTo(player, "handleSetThirdLoc", null, 1, false);
        return;
    }
    public void padawan_old_musician_02_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int padawan_old_musician_02_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83a3fa55"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_8593cffc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71918486");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e9a5dbe");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_old_musician_02_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71918486"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c8ad3417");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91a67d52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ce5abed");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60181c57");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e9a5dbe"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_d920c8d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79a946b4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51ea4418");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_old_musician_02_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91a67d52"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                padawan_old_musician_02_action_sendToThirdNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_d80ca537");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ce5abed"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_bdb08f0b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8db3d0fb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf3cdb9a");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60181c57"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                padawan_old_musician_02_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_old_musician_02_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8db3d0fb"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                padawan_old_musician_02_action_sendToThirdNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_c7b21593");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cf3cdb9a"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b38f136f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fafa8703");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e50f991");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_old_musician_02_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fafa8703"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58a6b3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7d951f7");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1e50f991"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                padawan_old_musician_02_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_284c1131");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_old_musician_02_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                padawan_old_musician_02_action_sendToThirdNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c7d951f7"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                padawan_old_musician_02_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_26888d70");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_old_musician_02_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79a946b4"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                padawan_old_musician_02_action_sendToThirdNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_bdb08f0b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8db3d0fb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf3cdb9a");
                    }
                    utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51ea4418"))
        {
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                padawan_old_musician_02_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_46660c8b");
                utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
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
            detachScript(self, "conversation.padawan_old_musician_02");
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
        detachScript(self, "conversation.padawan_old_musician_02");
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
        if (padawan_old_musician_02_condition_isTrialPlayer(player, npc))
        {
            doAnimationAction(npc, "point_to_self");
            padawan_old_musician_02_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_cefcce06");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_old_musician_02_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_83a3fa55");
                }
                utils.setScriptVar(player, "conversation.padawan_old_musician_02.branchId", 1);
                npcStartConversation(player, npc, "padawan_old_musician_02", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_old_musician_02_condition__defaultCondition(player, npc))
        {
            padawan_old_musician_02_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_5d25f30f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_old_musician_02"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.padawan_old_musician_02.branchId");
        if (branchId == 1 && padawan_old_musician_02_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && padawan_old_musician_02_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && padawan_old_musician_02_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && padawan_old_musician_02_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && padawan_old_musician_02_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && padawan_old_musician_02_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && padawan_old_musician_02_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.padawan_old_musician_02.branchId");
        return SCRIPT_CONTINUE;
    }
}
