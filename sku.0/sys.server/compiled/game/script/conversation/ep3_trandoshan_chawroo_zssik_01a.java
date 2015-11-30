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

public class ep3_trandoshan_chawroo_zssik_01a extends script.base_script
{
    public ep3_trandoshan_chawroo_zssik_01a()
    {
    }
    public static String c_stringFile = "conversation/ep3_trandoshan_chawroo_zssik_01a";
    public boolean ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_trandoshan_chawroo_zssik_01a_condition_isOnTask01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_trando_boshaz_zssik_02", "blackscaleAmbush");
    }
    public boolean ep3_trandoshan_chawroo_zssik_01a_condition_isOnTask02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_trando_boshaz_zssik_02", "talkToChawroo");
    }
    public boolean ep3_trandoshan_chawroo_zssik_01a_condition_hasCompletedTask01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_trando_boshaz_zssik_02", "talkToChawroo");
    }
    public boolean ep3_trandoshan_chawroo_zssik_01a_condition_cannotSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "combat_smuggler_underworld_01"))
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
    public void ep3_trandoshan_chawroo_zssik_01a_action_doSignal01(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_2sec.cef", player, "");
        groundquests.sendSignal(player, "chawrooLifeDebt");
    }
    public void ep3_trandoshan_chawroo_zssik_01a_action_emoteConfusion(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
        chat.thinkTo(player, player, new string_id("ep3/sidequests", "wke_convo_failure"));
    }
    public void ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_2sec.cef", player, "");
    }
    public void ep3_trandoshan_chawroo_zssik_01a_action_vocalizeLong(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_6sec.cef", player, "");
    }
    public void ep3_trandoshan_chawroo_zssik_01a_action_vocalizeMed(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_749"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_751");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_755"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow4");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_759"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_761");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_763");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_763"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeLong(player, npc);
                string_id message = new string_id(c_stringFile, "s_765");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_767");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_767"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_769");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_771");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_771"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_773");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_775");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_775"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_777");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_779");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_779"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_781");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_783");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_787"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                ep3_trandoshan_chawroo_zssik_01a_action_doSignal01(player, npc);
                string_id message = new string_id(c_stringFile, "s_789");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_chawroo_zssik_01a_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_783"))
        {
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                ep3_trandoshan_chawroo_zssik_01a_action_doSignal01(player, npc);
                string_id message = new string_id(c_stringFile, "s_785");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
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
            detachScript(self, "conversation.ep3_trandoshan_chawroo_zssik_01a");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Chawroo");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Chawroo");
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
        detachScript(self, "conversation.ep3_trandoshan_chawroo_zssik_01a");
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
        if (ep3_trandoshan_chawroo_zssik_01a_condition_cannotSpeakWookiee(player, npc))
        {
            ep3_trandoshan_chawroo_zssik_01a_action_emoteConfusion(player, npc);
            string_id message = new string_id(c_stringFile, "s_70");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_chawroo_zssik_01a_condition_hasCompletedTask01(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_747");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_749");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 2);
                npcStartConversation(player, npc, "ep3_trandoshan_chawroo_zssik_01a", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_chawroo_zssik_01a_condition_isOnTask02(player, npc))
        {
            doAnimationAction(npc, "embarrassed");
            ep3_trandoshan_chawroo_zssik_01a_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_753");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_755");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId", 4);
                npcStartConversation(player, npc, "ep3_trandoshan_chawroo_zssik_01a", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_chawroo_zssik_01a_condition_isOnTask01(player, npc))
        {
            doAnimationAction(npc, "standing_raise_fist");
            ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_791");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_chawroo_zssik_01a_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            ep3_trandoshan_chawroo_zssik_01a_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_793");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_trandoshan_chawroo_zssik_01a"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
        if (branchId == 2 && ep3_trandoshan_chawroo_zssik_01a_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_trandoshan_chawroo_zssik_01a_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_trandoshan_chawroo_zssik_01a_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_trandoshan_chawroo_zssik_01a_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_trandoshan_chawroo_zssik_01a_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_trandoshan_chawroo_zssik_01a_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_trandoshan_chawroo_zssik_01a_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_trandoshan_chawroo_zssik_01a_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_trandoshan_chawroo_zssik_01a_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_trandoshan_chawroo_zssik_01a.branchId");
        return SCRIPT_CONTINUE;
    }
}
