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
import script.library.utils;

public class tatooine_knives_parry_ind extends script.base_script
{
    public tatooine_knives_parry_ind()
    {
    }
    public static String c_stringFile = "conversation/tatooine_knives_parry_ind";
    public boolean tatooine_knives_parry_ind_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_knives_parry_ind_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_parry");
        int questId2 = questGetQuestId("quest/tatooine_knives_parry_pt2");
        boolean OnTask = (questIsQuestActive(questId1, player)) || (questIsQuestActive(questId2, player));
        return OnTask;
    }
    public boolean tatooine_knives_parry_ind_condition_onTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_parry_pt2");
        int finishBank = groundquests.getTaskId(questId1, "tatooine_knives_parry_e14");
        boolean tatooine_knives_parry_ind_condition_onTask = questIsTaskActive(questId1, finishBank, player);
        return tatooine_knives_parry_ind_condition_onTask;
    }
    public boolean tatooine_knives_parry_ind_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_parry_pt2");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_knives_parry_ind_condition_questBlocked(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestDisabled("tatooine_knives_parry");
    }
    public boolean tatooine_knives_parry_ind_condition_isThisDisabled(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestDisabled("tatooine_knives_thrust") || groundquests.isQuestDisabled("tatooine_knives_parry") || groundquests.isQuestDisabled("tatooine_knives_fencing");
    }
    public void tatooine_knives_parry_ind_action_signalReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatoonie_knives_parry_launch_e97");
    }
    public void tatooine_knives_parry_ind_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_knives_parry");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_knives_parry_ind_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int tatooine_knives_parry_ind_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                tatooine_knives_parry_ind_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_ind_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
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
            detachScript(self, "conversation.tatooine_knives_parry_ind");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Ind");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Ind");
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
        detachScript(self, "conversation.tatooine_knives_parry_ind");
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
        if (tatooine_knives_parry_ind_condition_isThisDisabled(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_ind_condition_questComplete(player, npc))
        {
            doAnimationAction(npc, "slump_head");
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 2);
                npcStartConversation(player, npc, "tatooine_knives_parry_ind", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_ind_condition_onTask(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            tatooine_knives_parry_ind_action_signalReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 4);
                npcStartConversation(player, npc, "tatooine_knives_parry_ind", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_ind_condition_onQuest(player, npc))
        {
            tatooine_knives_parry_ind_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 6);
                npcStartConversation(player, npc, "tatooine_knives_parry_ind", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_parry_ind_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId", 8);
                npcStartConversation(player, npc, "tatooine_knives_parry_ind", message, responses);
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
        if (!conversationId.equals("tatooine_knives_parry_ind"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
        if (branchId == 2 && tatooine_knives_parry_ind_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tatooine_knives_parry_ind_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && tatooine_knives_parry_ind_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && tatooine_knives_parry_ind_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tatooine_knives_parry_ind_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tatooine_knives_parry_ind_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_knives_parry_ind_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_knives_parry_ind_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_knives_parry_ind_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_knives_parry_ind_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_knives_parry_ind.branchId");
        return SCRIPT_CONTINUE;
    }
}
