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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class tatooine_knives_fencing_valarian extends script.base_script
{
    public tatooine_knives_fencing_valarian()
    {
    }
    public static String c_stringFile = "conversation/tatooine_knives_fencing_valarian";
    public boolean tatooine_knives_fencing_valarian_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_knives_fencing_valarian_condition_failSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "assassinate", "tatooine_knives_riposte_assassinate") || space_quest.hasAbortedQuest(player, "assassinate", "tatooine_knives_riposte_assassinate");
    }
    public boolean tatooine_knives_fencing_valarian_condition_TandPrewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int finishTPreward = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e3");
        boolean onTask = questIsTaskActive(questId1, finishTPreward, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_QuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int questId2 = questGetQuestId("quest/tatooine_knives_fencing_pt_2");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && !((features.isSpaceEdition(player))) || ((questIsQuestComplete(questId1, player)) && (questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_completeTandP(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_thrust");
        int questId2 = questGetQuestId("quest/tatooine_knives_parry_pt2");
        int questId3 = questGetQuestId("quest/tatooine_knives_fencing_reward");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (questIsQuestComplete(questId2, player)) && (questIsQuestActive(questId3, player));
        return OnTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_physcoHasJTL(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        boolean onTask = questIsQuestComplete(questId1, player) && features.isSpaceEdition(player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_onPhysco(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int finishBank = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e85");
        boolean onTask = questIsTaskActive(questId1, finishBank, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_thrustComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_thrust");
        int questId2 = questGetQuestId("quest/tatooine_knives_fencing_reward");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (questIsQuestActive(questId2, player));
        return OnTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_parryComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_parry_pt2");
        int questId2 = questGetQuestId("quest/tatooine_knives_fencing_reward");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (questIsQuestActive(questId2, player));
        return OnTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_onUna(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int finishBank = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e45");
        boolean onTask = questIsTaskActive(questId1, finishBank, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_unaComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int keelaQuestioned = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e7");
        boolean onTask = questIsTaskActive(questId1, keelaQuestioned, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_onKeela(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int finishBank = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e6");
        boolean onTask = questIsTaskActive(questId1, finishBank, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_spaceCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "tatooine_knives_riposte_assassinate"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_knives_fencing_valarian_condition_squadComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing_pt_2");
        int onspace = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e17");
        boolean onTask = questIsTaskActive(questId1, onspace, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_physcoDead(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int tatooine_knives_fencing_valarian_condition_physcoDead = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e85");
        boolean onTask = (questIsTaskComplete(questId1, tatooine_knives_fencing_valarian_condition_physcoDead, player));
        return onTask;
    }
    public boolean tatooine_knives_fencing_valarian_condition_isThisDisabled(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestDisabled("tatooine_knives_thrust") || groundquests.isQuestDisabled("tatooine_knives_parry") || groundquests.isQuestDisabled("tatooine_knives_fencing");
    }
    public boolean tatooine_knives_fencing_valarian_condition_onIntercept(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "quest/tatooine_knives_fencing_pt_2") && !space_quest.hasWonQuest(player, "assassinate", "tatooine_knives_riposte_assassinate"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_knives_fencing_valarian_condition_hasPrologContentQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "quest/quest_05_frame_valarians", "defeatAssassinLadyValarian");
    }
    public boolean tatooine_knives_fencing_valarian_condition_wave_event_active(obj_id player, obj_id npc) throws InterruptedException
    {
        int wave = utils.getIntScriptVar(npc, "waveEventCurrentWave");
        return wave > 0;
    }
    public boolean tatooine_knives_fencing_valarian_condition_hasGetReparations(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "quest/quest_05_frame_valarians_success_assassin", "getReparations");
    }
    public void tatooine_knives_fencing_valarian_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void tatooine_knives_fencing_valarian_action_giveIntercept(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "tatooine_knives_fencing_pt_2");
        groundquests.grantQuest(player, "tatooine_knives_fencing_pt_2");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_knives_riposte_assassinate");
        space_quest.grantQuest(player, "assassinate", "tatooine_knives_riposte_assassinate");
    }
    public void tatooine_knives_fencing_valarian_action_signalPhyscoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatooine_knives_fencing_launch_e10");
    }
    public void tatooine_knives_fencing_valarian_action_signalPhysco(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatooine_knives_fencing_launch_e8");
    }
    public void tatooine_knives_fencing_valarian_action_signalReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatooine_knives_fencing_launch_e18");
    }
    public void tatooine_knives_fencing_valarian_action_signalUna(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatooine_knives_fencing_launch_e4");
    }
    public void tatooine_knives_fencing_valarian_action_content_valarian_assassin(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary webster = new dictionary();
        webster.put("player", player);
        messageTo(npc, "waveEventControllerNPCStart", webster, 0, false);
    }
    public void tatooine_knives_fencing_valarian_action_payReparations(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasReceivedReparations");
    }
    public int tatooine_knives_fencing_valarian_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_166"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_payReparations(player, npc);
                string_id message = new string_id(c_stringFile, "s_167");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            doAnimationAction(player, "backhand");
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "scream");
                tatooine_knives_fencing_valarian_action_content_valarian_assassin(player, npc);
                string_id message = new string_id(c_stringFile, "s_153");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_155"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "scream");
                tatooine_knives_fencing_valarian_action_content_valarian_assassin(player, npc);
                string_id message = new string_id(c_stringFile, "s_157");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_158"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_159");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_161");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "scream");
                tatooine_knives_fencing_valarian_action_content_valarian_assassin(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_141"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_142"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_143"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_giveIntercept(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_giveIntercept(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_131"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_134"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_133"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_giveIntercept(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_135"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_136");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_137");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_137"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_138"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_giveIntercept(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_signalPhysco(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_121"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_signalPhysco(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_79");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_108");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_114"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_valarian_action_signalUna(player, npc);
                string_id message = new string_id(c_stringFile, "s_115");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_168"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_149");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_170"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_149");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            doAnimationAction(player, "standing_placate");
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_149");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_valarian_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151"))
        {
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                tatooine_knives_fencing_valarian_action_signalReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
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
            detachScript(self, "conversation.tatooine_knives_fencing_valarian");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Lady Valarian");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Lady Valarian");
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
        detachScript(self, "conversation.tatooine_knives_fencing_valarian");
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
        if (tatooine_knives_fencing_valarian_condition_hasGetReparations(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_163");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 1);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_wave_event_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_165");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_hasPrologContentQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_155");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_158");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 4);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_isThisDisabled(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_QuestComplete(player, npc))
        {
            doAnimationAction(npc, "bow3");
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_105");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 11);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_squadComplete(player, npc))
        {
            tatooine_knives_fencing_valarian_action_signalReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_148");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_spaceCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_140");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_141");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 14);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_failSpace(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_54");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 17);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_onIntercept(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_139");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_physcoHasJTL(player, npc))
        {
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_127");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_131");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 20);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_physcoDead(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            tatooine_knives_fencing_valarian_action_signalPhyscoReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_130");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 24);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_onPhysco(player, npc))
        {
            doAnimationAction(npc, "scream");
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_126");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 26);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_unaComplete(player, npc))
        {
            doAnimationAction(npc, "shiver");
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_117");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 28);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_onKeela(player, npc))
        {
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_118");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 32);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_onUna(player, npc))
        {
            doAnimationAction(npc, "backhand");
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_116");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 34);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition_TandPrewarded(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_106");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 36);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "bow");
            tatooine_knives_fencing_valarian_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_94");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_valarian_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_knives_fencing_valarian_condition_thrustComplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_knives_fencing_valarian_condition_parryComplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (tatooine_knives_fencing_valarian_condition_completeTandP(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId", 40);
                npcStartConversation(player, npc, "tatooine_knives_fencing_valarian", message, responses);
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
        if (!conversationId.equals("tatooine_knives_fencing_valarian"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
        if (branchId == 1 && tatooine_knives_fencing_valarian_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tatooine_knives_fencing_valarian_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_knives_fencing_valarian_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && tatooine_knives_fencing_valarian_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_knives_fencing_valarian_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_knives_fencing_valarian_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_knives_fencing_valarian_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_knives_fencing_valarian_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_knives_fencing_valarian_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tatooine_knives_fencing_valarian_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_knives_fencing_valarian_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && tatooine_knives_fencing_valarian_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_knives_fencing_valarian_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_knives_fencing_valarian_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_knives_fencing_valarian_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tatooine_knives_fencing_valarian_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_knives_fencing_valarian_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && tatooine_knives_fencing_valarian_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && tatooine_knives_fencing_valarian_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tatooine_knives_fencing_valarian_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_knives_fencing_valarian_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && tatooine_knives_fencing_valarian_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && tatooine_knives_fencing_valarian_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_valarian.branchId");
        return SCRIPT_CONTINUE;
    }
}
