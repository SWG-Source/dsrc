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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class legacy_button_jawatracks_b3p0 extends script.base_script
{
    public legacy_button_jawatracks_b3p0()
    {
    }
    public static String c_stringFile = "conversation/legacy_button_jawatracks_b3p0";
    public boolean legacy_button_jawatracks_b3p0_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_button_jawatracks_b3p0_condition_keysBack(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/legacy_button_jawatracks_pt3_v2", "legacy_button_jawatracks_e27") || groundquests.isTaskActive(player, "quest/legacy_button_jawatracks_reb_pt3_v2", "legacy_button_jawatracks_e232r"));
    }
    public boolean legacy_button_jawatracks_b3p0_condition_onDataRetrival(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt3");
        int onBody = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e295");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt3");
        int onBody2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e295r");
        boolean onTask = questIsTaskActive(questId1, onBody, player) || questIsTaskActive(questId2, onBody2, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_b3p0_condition_onsaveJawas(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/legacy_button_jawatracks_pt3_v2", "legacy_button_jawatracks_e22") || groundquests.isTaskActive(player, "quest/legacy_button_jawatracks_reb_pt3_v2", "legacy_button_jawatracks_e22r"));
    }
    public boolean legacy_button_jawatracks_b3p0_condition_questTracks3Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt3");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt3");
        boolean OnTask = questIsQuestComplete(questId1, player) || questIsQuestComplete(questId2, player);
        return OnTask;
    }
    public boolean legacy_button_jawatracks_b3p0_condition_raidersDead(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt3");
        int onSaved = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e23");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt3");
        int onSaved2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e23r");
        boolean onTask = questIsTaskComplete(questId1, onSaved, player) || questIsTaskComplete(questId2, onSaved2, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_b3p0_condition_onRaiders(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt3");
        int onSaved = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e232");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt3");
        int onSaved2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e232r");
        boolean onTask = questIsTaskActive(questId1, onSaved, player) || questIsTaskActive(questId2, onSaved2, player);
        return onTask;
    }
    public void legacy_button_jawatracks_b3p0_action_giveDataPad(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_jawatracks_launch_e28");
    }
    public void legacy_button_jawatracks_b3p0_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void legacy_button_jawatracks_b3p0_action_signalKeys(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_jawatracks_launch_e23");
    }
    public void legacy_button_jawatracks_b3p0_action_signalCounterattack(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_jawatracks_launch_e23");
    }
    public int legacy_button_jawatracks_b3p0_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_152");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_153"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                legacy_button_jawatracks_b3p0_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                legacy_button_jawatracks_b3p0_action_giveDataPad(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                legacy_button_jawatracks_b3p0_action_signalKeys(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                legacy_button_jawatracks_b3p0_action_signalKeys(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                legacy_button_jawatracks_b3p0_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_b3p0_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
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
            detachScript(self, "conversation.legacy_button_jawatracks_b3p0");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "FA-2PO");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "FA-2PO");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.legacy_button_jawatracks_b3p0");
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
        if (legacy_button_jawatracks_b3p0_condition_questTracks3Complete(player, npc))
        {
            doAnimationAction(npc, "greet");
            legacy_button_jawatracks_b3p0_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_101");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 1);
                npcStartConversation(player, npc, "legacy_button_jawatracks_b3p0", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_b3p0_condition_onDataRetrival(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_150");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 3);
                npcStartConversation(player, npc, "legacy_button_jawatracks_b3p0", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_b3p0_condition_keysBack(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            legacy_button_jawatracks_b3p0_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_145");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 6);
                npcStartConversation(player, npc, "legacy_button_jawatracks_b3p0", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_b3p0_condition_onRaiders(player, npc))
        {
            doAnimationAction(npc, "blame");
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_b3p0_condition_onsaveJawas(player, npc))
        {
            legacy_button_jawatracks_b3p0_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 10);
                npcStartConversation(player, npc, "legacy_button_jawatracks_b3p0", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_b3p0_condition_raidersDead(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            legacy_button_jawatracks_b3p0_action_signalCounterattack(player, npc);
            string_id message = new string_id(c_stringFile, "s_144");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 14);
                npcStartConversation(player, npc, "legacy_button_jawatracks_b3p0", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shush");
            legacy_button_jawatracks_b3p0_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_805");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_b3p0_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId", 16);
                npcStartConversation(player, npc, "legacy_button_jawatracks_b3p0", message, responses);
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
        if (!conversationId.equals("legacy_button_jawatracks_b3p0"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
        if (branchId == 1 && legacy_button_jawatracks_b3p0_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && legacy_button_jawatracks_b3p0_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && legacy_button_jawatracks_b3p0_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && legacy_button_jawatracks_b3p0_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && legacy_button_jawatracks_b3p0_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && legacy_button_jawatracks_b3p0_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && legacy_button_jawatracks_b3p0_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && legacy_button_jawatracks_b3p0_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && legacy_button_jawatracks_b3p0_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && legacy_button_jawatracks_b3p0_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_b3p0.branchId");
        return SCRIPT_CONTINUE;
    }
}
