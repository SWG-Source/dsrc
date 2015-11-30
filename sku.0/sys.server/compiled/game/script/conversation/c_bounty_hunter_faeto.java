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

public class c_bounty_hunter_faeto extends script.base_script
{
    public c_bounty_hunter_faeto()
    {
    }
    public static String c_stringFile = "conversation/c_bounty_hunter_faeto";
    public boolean c_bounty_hunter_faeto_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_bounty_hunter_faeto_condition_isTaskActiveBobaLink(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_bounty_hunter_kill_1", "bounty_hunter_kill_1_e100");
    }
    public boolean c_bounty_hunter_faeto_condition_isTaskActiveBounty1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_bounty_hunter_kill_1");
        int ground = groundquests.getTaskId(questId1, "bounty_hunter_kill_1_e100");
        boolean onTask = (questIsTaskComplete(questId1, ground, player));
        return onTask;
    }
    public boolean c_bounty_hunter_faeto_condition_hasCompletedTaskBounty1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_bounty_hunter_kill_1", "bounty_hunter_kill_1_e13");
    }
    public boolean c_bounty_hunter_faeto_condition_hasCompletedQuestAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "c_bounty_hunter_kill_1"));
    }
    public void c_bounty_hunter_faeto_action_grantBounty1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "bounty_hunter_kill_1_launch_e0");
    }
    public void c_bounty_hunter_faeto_action_grantRewardBounty1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "bounty_hunter_kill_1_launch_e14");
    }
    public void c_bounty_hunter_faeto_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_bounty_hunter_faeto_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_23"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                c_bounty_hunter_faeto_action_grantRewardBounty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                c_bounty_hunter_faeto_action_grantBounty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "poke");
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_103"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                c_bounty_hunter_faeto_action_grantBounty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            if (c_bounty_hunter_faeto_condition_hasCompletedQuestAll(player, npc))
            {
                c_bounty_hunter_faeto_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (c_bounty_hunter_faeto_condition_hasCompletedTaskBounty1(player, npc))
            {
                c_bounty_hunter_faeto_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (c_bounty_hunter_faeto_condition_isTaskActiveBounty1(player, npc))
            {
                c_bounty_hunter_faeto_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (c_bounty_hunter_faeto_condition_isTaskActiveBobaLink(player, npc))
            {
                c_bounty_hunter_faeto_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_bounty_hunter_faeto_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                c_bounty_hunter_faeto_action_grantBounty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_bounty_hunter_faeto");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Bosshand Faeto");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Bosshand Faeto");
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
        detachScript(self, "conversation.c_bounty_hunter_faeto");
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
        if (c_bounty_hunter_faeto_condition_hasCompletedQuestAll(player, npc))
        {
            c_bounty_hunter_faeto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_bounty_hunter_faeto_condition_hasCompletedTaskBounty1(player, npc))
        {
            c_bounty_hunter_faeto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_22");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                }
                utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 2);
                npcStartConversation(player, npc, "c_bounty_hunter_faeto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_bounty_hunter_faeto_condition_isTaskActiveBounty1(player, npc))
        {
            c_bounty_hunter_faeto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 4);
                npcStartConversation(player, npc, "c_bounty_hunter_faeto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_bounty_hunter_faeto_condition_isTaskActiveBobaLink(player, npc))
        {
            c_bounty_hunter_faeto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                }
                utils.setScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId", 6);
                npcStartConversation(player, npc, "c_bounty_hunter_faeto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_bounty_hunter_faeto_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_bounty_hunter_faeto"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
        if (branchId == 2 && c_bounty_hunter_faeto_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_bounty_hunter_faeto_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_bounty_hunter_faeto_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_bounty_hunter_faeto_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_bounty_hunter_faeto_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_bounty_hunter_faeto_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && c_bounty_hunter_faeto_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && c_bounty_hunter_faeto_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_bounty_hunter_faeto_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_bounty_hunter_faeto.branchId");
        return SCRIPT_CONTINUE;
    }
}
