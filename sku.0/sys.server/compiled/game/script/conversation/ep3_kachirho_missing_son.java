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

public class ep3_kachirho_missing_son extends script.base_script
{
    public ep3_kachirho_missing_son()
    {
    }
    public static String c_stringFile = "conversation/ep3_kachirho_missing_son";
    public boolean ep3_kachirho_missing_son_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_kachirho_missing_son_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_kachirho_missing_son");
    }
    public boolean ep3_kachirho_missing_son_condition_hasCompletedMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_kachirho_missing_son");
    }
    public boolean ep3_kachirho_missing_son_condition_hasTask01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_kachirho_missing_son", "tellChatook");
    }
    public boolean ep3_kachirho_missing_son_condition_cannotSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "class_smuggler_phase1_novice"))
        {
            return false;
        }
        if (hasSkill(player, "social_language_wookiee_comprehend"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public void ep3_kachirho_missing_son_action_grantMission(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
        groundquests.grantQuest(player, "ep3_kachirho_missing_son");
    }
    public void ep3_kachirho_missing_son_action_doSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_6sec.cef", player, "");
        groundquests.sendSignal(player, "tellSadTaleChatook");
    }
    public void ep3_kachirho_missing_son_action_vocalizeLong(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_6sec.cef", player, "");
    }
    public void ep3_kachirho_missing_son_action_vocalizeMed(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
    }
    public void ep3_kachirho_missing_son_action_vocalizeShort(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_2sec.cef", player, "");
    }
    public void ep3_kachirho_missing_son_action_emoteConfusion(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
        chat.thinkTo(player, player, new string_id("ep3/sidequests", "wke_convo_failure"));
    }
    public int ep3_kachirho_missing_son_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_240"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_mouth");
                ep3_kachirho_missing_son_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_242");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_244");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_244"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                ep3_kachirho_missing_son_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_246");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_248");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                ep3_kachirho_missing_son_action_vocalizeLong(player, npc);
                string_id message = new string_id(c_stringFile, "s_250");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                ep3_kachirho_missing_son_action_doSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_254");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_256"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                ep3_kachirho_missing_son_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_270"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                ep3_kachirho_missing_son_action_vocalizeLong(player, npc);
                string_id message = new string_id(c_stringFile, "s_272");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_279");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_291"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                ep3_kachirho_missing_son_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_293");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_279"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                ep3_kachirho_missing_son_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_281");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_283");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_missing_son_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_283"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow2");
                ep3_kachirho_missing_son_action_grantMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_287"))
        {
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                ep3_kachirho_missing_son_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_289");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
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
            detachScript(self, "conversation.ep3_kachirho_missing_son");
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
        detachScript(self, "conversation.ep3_kachirho_missing_son");
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
        if (ep3_kachirho_missing_son_condition_cannotSpeakWookiee(player, npc))
        {
            ep3_kachirho_missing_son_action_emoteConfusion(player, npc);
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_missing_son_condition_hasCompletedMission(player, npc))
        {
            doAnimationAction(npc, "weeping");
            ep3_kachirho_missing_son_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_236");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_missing_son_condition_hasTask01(player, npc))
        {
            doAnimationAction(npc, "slump_head");
            ep3_kachirho_missing_son_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_238");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                }
                utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 3);
                npcStartConversation(player, npc, "ep3_kachirho_missing_son", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_missing_son_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "implore");
            ep3_kachirho_missing_son_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_260");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "beckon");
            ep3_kachirho_missing_son_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_262");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_kachirho_missing_son_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_291");
                }
                utils.setScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId", 10);
                npcStartConversation(player, npc, "ep3_kachirho_missing_son", message, responses);
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
        if (!conversationId.equals("ep3_kachirho_missing_son"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
        if (branchId == 3 && ep3_kachirho_missing_son_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_kachirho_missing_son_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_kachirho_missing_son_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_kachirho_missing_son_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_kachirho_missing_son_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_kachirho_missing_son_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_kachirho_missing_son_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_kachirho_missing_son_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_kachirho_missing_son.branchId");
        return SCRIPT_CONTINUE;
    }
}
