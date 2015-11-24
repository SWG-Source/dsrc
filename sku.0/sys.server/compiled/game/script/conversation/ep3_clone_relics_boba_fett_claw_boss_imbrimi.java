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

public class ep3_clone_relics_boba_fett_claw_boss_imbrimi extends script.base_script
{
    public ep3_clone_relics_boba_fett_claw_boss_imbrimi()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_boba_fett_claw_boss_imbrimi";
    public boolean ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition_onQuestSevenTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_boba_fett_7", "imbrimi"));
    }
    public void ep3_clone_relics_boba_fett_claw_boss_imbrimi_action_signalTalked(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToImbrimi");
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1540"))
        {
            doAnimationAction(player, "belly_laugh");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1679");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_308");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_338"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1681");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_341");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_308"))
        {
            doAnimationAction(player, "backhand_threaten");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_310");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1685");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1685"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_313");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1687");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_333");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1687"))
        {
            doAnimationAction(player, "slit_throat");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_317");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_333"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1695");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_317"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1691");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_320");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_320"))
        {
            doAnimationAction(player, "point_accusingly");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1693");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_323");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_323"))
        {
            doAnimationAction(player, "shrug_hands");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1697");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_326"))
        {
            doAnimationAction(player, "tiphat");
            ep3_clone_relics_boba_fett_claw_boss_imbrimi_action_signalTalked(player, npc);
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1699");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_317");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_341"))
        {
            doAnimationAction(player, "backhand_threaten");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_310");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1685");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_345"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_347");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_claw_boss_imbrimi"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_claw_boss_imbrimi"));
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
        detachScript(self, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi");
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
        if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition_onQuestSevenTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1538");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1540");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 1);
                npcStartConversation(player, npc, "ep3_clone_relics_boba_fett_claw_boss_imbrimi", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_343");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_boba_fett_claw_boss_imbrimi_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_345");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId", 12);
                npcStartConversation(player, npc, "ep3_clone_relics_boba_fett_claw_boss_imbrimi", message, responses);
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
        if (!conversationId.equals("ep3_clone_relics_boba_fett_claw_boss_imbrimi"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
        if (branchId == 1 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_clone_relics_boba_fett_claw_boss_imbrimi_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_boba_fett_claw_boss_imbrimi.branchId");
        return SCRIPT_CONTINUE;
    }
}
