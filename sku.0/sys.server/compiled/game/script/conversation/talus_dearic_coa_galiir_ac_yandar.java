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

public class talus_dearic_coa_galiir_ac_yandar extends script.base_script
{
    public talus_dearic_coa_galiir_ac_yandar()
    {
    }
    public static String c_stringFile = "conversation/talus_dearic_coa_galiir_ac_yandar";
    public boolean talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean talus_dearic_coa_galiir_ac_yandar_condition_speak_galiir(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_imperial_view", "talus_dearic_coa_speak_galiir_wfs");
    }
    public boolean talus_dearic_coa_galiir_ac_yandar_condition_on_nsh_tasks(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "talus_dearic_coa_deep_cover") || (groundquests.isQuestActive(player, "talus_dearic_coa_imperial_view") && groundquests.hasCompletedTask(player, "talus_dearic_coa_imperial_view", "talus_dearic_coa_speak_galiir_wfs")))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_galiir_ac_yandar_condition_nsh_tasks_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_imperial_view_end", "talus_dearic_coa_lag_tasks_reported_wfs");
    }
    public boolean talus_dearic_coa_galiir_ac_yandar_condition_nsh_tasks_reported(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "talus_dearic_coa_imperial_view_end", "talus_dearic_coa_lag_tasks_reported_wfs");
    }
    public boolean talus_dearic_coa_galiir_ac_yandar_condition_abandoned_deep_cover(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.isQuestActiveOrComplete(player, "talus_dearic_coa_deep_cover") && groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view"));
    }
    public void talus_dearic_coa_galiir_ac_yandar_action_accept_nsh_tasks(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_lag_tasks_accepted");
    }
    public void talus_dearic_coa_galiir_ac_yandar_action_complete_nsh_tasks(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_lag_tasks_reported");
    }
    public void talus_dearic_coa_galiir_ac_yandar_action_grant_deep_cover_qst(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_dearic_coa_deep_cover");
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            doAnimationAction(player, "cough_polite");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            doAnimationAction(player, "expect_tip");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            doAnimationAction(player, "giveup");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                talus_dearic_coa_galiir_ac_yandar_action_complete_nsh_tasks(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "huh");
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            doAnimationAction(player, "shrug_hands");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_galiir_ac_yandar_action_grant_deep_cover_qst(player, npc);
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            doAnimationAction(player, "elbow");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                doAnimationAction(player, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            doAnimationAction(player, "shrug_hands");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                doAnimationAction(player, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            doAnimationAction(player, "huh");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            doAnimationAction(player, "dismiss");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                talus_dearic_coa_galiir_ac_yandar_action_accept_nsh_tasks(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_galiir_ac_yandar_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.talus_dearic_coa_galiir_ac_yandar");
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
        detachScript(self, "conversation.talus_dearic_coa_galiir_ac_yandar");
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
        if (talus_dearic_coa_galiir_ac_yandar_condition_nsh_tasks_reported(player, npc))
        {
            doAnimationAction(npc, "fakepunch");
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 1);
                npcStartConversation(player, npc, "talus_dearic_coa_galiir_ac_yandar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_galiir_ac_yandar_condition_nsh_tasks_complete(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 3);
                npcStartConversation(player, npc, "talus_dearic_coa_galiir_ac_yandar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_galiir_ac_yandar_condition_on_nsh_tasks(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 8);
                npcStartConversation(player, npc, "talus_dearic_coa_galiir_ac_yandar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_galiir_ac_yandar_condition_abandoned_deep_cover(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_69");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 11);
                npcStartConversation(player, npc, "talus_dearic_coa_galiir_ac_yandar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_galiir_ac_yandar_condition_speak_galiir(player, npc))
        {
            doAnimationAction(npc, "giveup");
            string_id message = new string_id(c_stringFile, "s_27");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId", 13);
                npcStartConversation(player, npc, "talus_dearic_coa_galiir_ac_yandar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_galiir_ac_yandar_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "threaten");
            string_id message = new string_id(c_stringFile, "s_74");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("talus_dearic_coa_galiir_ac_yandar"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
        if (branchId == 1 && talus_dearic_coa_galiir_ac_yandar_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && talus_dearic_coa_galiir_ac_yandar_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && talus_dearic_coa_galiir_ac_yandar_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && talus_dearic_coa_galiir_ac_yandar_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && talus_dearic_coa_galiir_ac_yandar_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && talus_dearic_coa_galiir_ac_yandar_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && talus_dearic_coa_galiir_ac_yandar_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && talus_dearic_coa_galiir_ac_yandar_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && talus_dearic_coa_galiir_ac_yandar_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && talus_dearic_coa_galiir_ac_yandar_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && talus_dearic_coa_galiir_ac_yandar_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && talus_dearic_coa_galiir_ac_yandar_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.talus_dearic_coa_galiir_ac_yandar.branchId");
        return SCRIPT_CONTINUE;
    }
}
