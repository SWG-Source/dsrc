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

public class talus_dearic_coa_vayde_quellim extends script.base_script
{
    public talus_dearic_coa_vayde_quellim()
    {
    }
    public static String c_stringFile = "conversation/talus_dearic_coa_vayde_quellim";
    public boolean talus_dearic_coa_vayde_quellim_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean talus_dearic_coa_vayde_quellim_condition_supplies_deliverable(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_rebel_view_end", "talus_dearic_coa_med_supplies_delivered_wfs");
    }
    public boolean talus_dearic_coa_vayde_quellim_condition_rebel_quest_accepted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "talus_dearic_coa_rebel_view_end", "talus_dearic_coa_report_to_trezya_wfs") || groundquests.isQuestActive(player, "talus_dearic_coa_rebel_view"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_vayde_quellim_condition_rebel_quest_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end");
    }
    public boolean talus_dearic_coa_vayde_quellim_condition_rebel_tasks_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_rebel_view_end", "talus_dearic_coa_rebel_gratitude_wfs");
    }
    public boolean talus_dearic_coa_vayde_quellim_condition_main_quest_accepted(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "talus_dearic_crisis_of_allegiance");
    }
    public void talus_dearic_coa_vayde_quellim_action_accept_rebel_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_dearic_coa_rebel_view");
        groundquests.sendSignal(player, "talus_dearic_coa_spoke_with_vayde");
    }
    public void talus_dearic_coa_vayde_quellim_action_deliver_supplies(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_med_supplies_delivered");
    }
    public void talus_dearic_coa_vayde_quellim_action_complete_rebel_quests(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_rebel_gratitude_accepted");
        groundquests.sendSignal(player, "talus_dearic_aclo_investigated");
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            doAnimationAction(player, "bow");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_vayde_quellim_action_deliver_supplies(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            doAnimationAction(player, "apologize");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                talus_dearic_coa_vayde_quellim_action_complete_rebel_quests(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            doAnimationAction(player, "explain");
            talus_dearic_coa_vayde_quellim_action_deliver_supplies(player, npc);
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            talus_dearic_coa_vayde_quellim_action_deliver_supplies(player, npc);
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            doAnimationAction(player, "shrug_hands");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "fakepunch");
                doAnimationAction(player, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "beckon");
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "udaman");
                talus_dearic_coa_vayde_quellim_action_accept_rebel_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_84"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            doAnimationAction(player, "shrug_hands");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            doAnimationAction(player, "nod");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                string_id message = new string_id(c_stringFile, "s_83");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_vayde_quellim_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "udaman");
                talus_dearic_coa_vayde_quellim_action_accept_rebel_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.talus_dearic_coa_vayde_quellim");
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
        detachScript(self, "conversation.talus_dearic_coa_vayde_quellim");
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
        if (talus_dearic_coa_vayde_quellim_condition_rebel_quest_complete(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 1);
                npcStartConversation(player, npc, "talus_dearic_coa_vayde_quellim", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_vayde_quellim_condition_rebel_tasks_complete(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 5);
                npcStartConversation(player, npc, "talus_dearic_coa_vayde_quellim", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_vayde_quellim_condition_supplies_deliverable(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_61");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 11);
                npcStartConversation(player, npc, "talus_dearic_coa_vayde_quellim", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_vayde_quellim_condition_rebel_quest_accepted(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_60");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_vayde_quellim_condition_supplies_deliverable(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 13);
                npcStartConversation(player, npc, "talus_dearic_coa_vayde_quellim", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_vayde_quellim_condition_main_quest_accepted(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId", 15);
                npcStartConversation(player, npc, "talus_dearic_coa_vayde_quellim", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_vayde_quellim_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_96");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("talus_dearic_coa_vayde_quellim"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
        if (branchId == 1 && talus_dearic_coa_vayde_quellim_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && talus_dearic_coa_vayde_quellim_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && talus_dearic_coa_vayde_quellim_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && talus_dearic_coa_vayde_quellim_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && talus_dearic_coa_vayde_quellim_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && talus_dearic_coa_vayde_quellim_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && talus_dearic_coa_vayde_quellim_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && talus_dearic_coa_vayde_quellim_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && talus_dearic_coa_vayde_quellim_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && talus_dearic_coa_vayde_quellim_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && talus_dearic_coa_vayde_quellim_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && talus_dearic_coa_vayde_quellim_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && talus_dearic_coa_vayde_quellim_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.talus_dearic_coa_vayde_quellim.branchId");
        return SCRIPT_CONTINUE;
    }
}
