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

public class ep3_etyyy_mada_johnson extends script.base_script
{
    public ep3_etyyy_mada_johnson()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_mada_johnson";
    public boolean ep3_etyyy_mada_johnson_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_mada_johnson_condition_hasCompletedBrodyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_hunt_johnson_brody_johnson"));
    }
    public boolean ep3_etyyy_mada_johnson_condition_hasCompletedWrelaac(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "ep3_hunt_wrelaac_to_chrilooc"))
        {
            return true;
        }
        else if (groundquests.hasCompletedQuest(player, "ep3_hunt_wrelaac_to_chrilooc"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_mada_johnson_condition_isShownPendant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_hunt_johnson_brody_johnson", "johnson_talkToMada"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_mada_johnson_condition_seekingWrelaac(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_hunt_mada_johnson_to_wrelaac"));
    }
    public boolean ep3_etyyy_mada_johnson_condition_needsProofForWrelaac(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_wrelaac_proof_of_mada", "wrelaac_getProofFromMada"));
    }
    public boolean ep3_etyyy_mada_johnson_condition_hasProof(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_wrelaac_proof_of_mada", "wrelaac_presentProofToWrelaac");
    }
    public boolean ep3_etyyy_mada_johnson_condition_returnToJohnsonSmith(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_johnson_brody_johnson", "johnson_returnToSmith");
    }
    public void ep3_etyyy_mada_johnson_action_proofForWrelaac(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "wrelaac_getProofFromMada");
    }
    public void ep3_etyyy_mada_johnson_action_sendBackToSmith(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "johnson_talkToMada");
    }
    public void ep3_etyyy_mada_johnson_action_grantMadaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_mada_johnson_to_wrelaac");
    }
    public int ep3_etyyy_mada_johnson_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_475"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_664");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_474"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_mada_johnson_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_266"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_268");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_mada_johnson_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_mada_johnson_action_sendBackToSmith(player, npc);
                string_id message = new string_id(c_stringFile, "s_264");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_mada_johnson_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_276"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_278");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_mada_johnson_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_284"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_288");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_292");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_296"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_298");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_mada_johnson_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_288"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_mada_johnson_action_grantMadaQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_290");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_292"))
        {
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_294");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_mada_johnson");
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
        detachScript(self, "conversation.ep3_etyyy_mada_johnson");
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
        if (ep3_etyyy_mada_johnson_condition_hasCompletedBrodyQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_252");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_475");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_474");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_mada_johnson", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition_returnToJohnsonSmith(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_254");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition_isShownPendant(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_256");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_266");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId", 5);
                npcStartConversation(player, npc, "ep3_etyyy_mada_johnson", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition_hasCompletedWrelaac(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_270");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition_hasProof(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_272");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition_needsProofForWrelaac(player, npc))
        {
            ep3_etyyy_mada_johnson_action_proofForWrelaac(player, npc);
            string_id message = new string_id(c_stringFile, "s_274");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_276");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId", 11);
                npcStartConversation(player, npc, "ep3_etyyy_mada_johnson", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition_seekingWrelaac(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_280");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_282");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_mada_johnson_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_284");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_296");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId", 14);
                npcStartConversation(player, npc, "ep3_etyyy_mada_johnson", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_mada_johnson"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
        if (branchId == 1 && ep3_etyyy_mada_johnson_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_mada_johnson_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_etyyy_mada_johnson_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_mada_johnson_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_etyyy_mada_johnson_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_etyyy_mada_johnson_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_mada_johnson.branchId");
        return SCRIPT_CONTINUE;
    }
}
