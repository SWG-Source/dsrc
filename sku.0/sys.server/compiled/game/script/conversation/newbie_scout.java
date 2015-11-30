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

public class newbie_scout extends script.base_script
{
    public newbie_scout()
    {
    }
    public static String c_stringFile = "conversation/newbie_scout";
    public boolean newbie_scout_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean newbie_scout_condition_completedCensus(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_v2");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_completedRills(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt5");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_completedMites(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt6");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onCensus(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_v2");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onMites(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt6");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onMitesReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/newbie_scout_pt6", "newbie_scout_e60"))
        {
            return true;
        }
        return false;
    }
    public boolean newbie_scout_condition_mayorSent(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "c_scout_trainer");
    }
    public boolean newbie_scout_condition_onCensusReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/newbie_scout_v2", "newbie_scout_e100"))
        {
            return true;
        }
        return false;
    }
    public boolean newbie_scout_condition_onWomp(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt2");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onWompReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/newbie_scout_pt2", "newbie_scout_e21"))
        {
            return true;
        }
        return false;
    }
    public boolean newbie_scout_condition_completedWomp(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt2");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onKreetle(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt3");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onKreetleReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/newbie_scout_pt3", "newbie_scout_e31"))
        {
            return true;
        }
        return false;
    }
    public boolean newbie_scout_condition_completedKreetle(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt3");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onWorrt(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt4");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onWorrtReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/newbie_scout_pt4", "newbie_scout_e41"))
        {
            return true;
        }
        return false;
    }
    public boolean newbie_scout_condition_completeWorrts(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt4");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onRills(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/newbie_scout_pt5");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean newbie_scout_condition_onRillsReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/newbie_scout_pt5", "newbie_scout_e50"))
        {
            return true;
        }
        return false;
    }
    public void newbie_scout_action_startCensus(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/newbie_scout_v2");
    }
    public void newbie_scout_action_startKreetle(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/newbie_scout_pt3");
    }
    public void newbie_scout_action_startMites(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/newbie_scout_pt6");
    }
    public void newbie_scout_action_sendRillSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_scout_trap_rills");
    }
    public void newbie_scout_action_endFromMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_speak_entha");
    }
    public void newbie_scout_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void newbie_scout_action_signalCensusReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_scout_launch_e9");
    }
    public void newbie_scout_action_startWomp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/newbie_scout_pt2");
    }
    public void newbie_scout_action_signalWompReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_scout_launch_e24");
    }
    public void newbie_scout_action_signalKreetleReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_scout_launch_e34");
    }
    public void newbie_scout_action_startWorrts(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/newbie_scout_pt4");
    }
    public void newbie_scout_action_signalWorrtReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_scout_launch_e44");
    }
    public void newbie_scout_action_startRills(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/newbie_scout_pt5");
    }
    public void newbie_scout_action_signalRillsReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_scout_launch_e53");
    }
    public int newbie_scout_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_722"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_724");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startMites(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_133"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_136");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_138"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startMites(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_144"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_sendRillSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_127"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_signalRillsReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_sendRillSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startRills(player, npc);
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startRills(player, npc);
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_121"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startRills(player, npc);
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_120"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startRills(player, npc);
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_sendRillSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_312"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startWorrts(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_103"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startWorrts(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_108"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_109");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startWorrts(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_123"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_signalKreetleReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_124");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_sendRillSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_698"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startKreetle(player, npc);
                string_id message = new string_id(c_stringFile, "s_700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_701");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_97"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_699"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_701"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_704");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startKreetle(player, npc);
                string_id message = new string_id(c_stringFile, "s_700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_701");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_signalWompReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startWomp(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_137");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_134"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startWomp(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_137"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_sendRillSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_145"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_signalCensusReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_149");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startWomp(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_137");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_154"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_endFromMayor(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_158");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_349"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_179");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_158"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startCensus(player, npc);
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_162"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_176"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_166"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (newbie_scout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.newbie_scout.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int newbie_scout_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170"))
        {
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                newbie_scout_action_startCensus(player, npc);
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_172"))
        {
            doAnimationAction(player, "dismiss");
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
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
            detachScript(self, "conversation.newbie_scout");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.newbie_scout");
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
        if (newbie_scout_condition_completedMites(player, npc))
        {
            doAnimationAction(npc, "thank");
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_148");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onMitesReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_713");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_722");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 2);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onMites(player, npc))
        {
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_146");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_completedRills(player, npc))
        {
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_129");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 7);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onRillsReward(player, npc))
        {
            doAnimationAction(npc, "thank");
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_126");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 11);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onRills(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_712");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 13);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_completeWorrts(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_711");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 15);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onWorrtReward(player, npc))
        {
            doAnimationAction(npc, "throwat");
            newbie_scout_action_signalWorrtReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_125");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 15);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onWorrt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_710");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 20);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_completedKreetle(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_709");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_312");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 21);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onKreetleReward(player, npc))
        {
            doAnimationAction(npc, "thank");
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_96");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 25);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onKreetle(player, npc))
        {
            doAnimationAction(npc, "paper");
            string_id message = new string_id(c_stringFile, "s_708");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 27);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_completedWomp(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_697");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_698");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_699");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 28);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onWompReward(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_93");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 33);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onWomp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_696");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 35);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_completedCensus(player, npc))
        {
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_84");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 37);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onCensusReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_141");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_145");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 40);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition_onCensus(player, npc))
        {
            doAnimationAction(npc, "bang");
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_151");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (newbie_scout_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "explain");
            newbie_scout_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_343");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (newbie_scout_condition_mayorSent(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!newbie_scout_condition_mayorSent(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_349");
                }
                utils.setScriptVar(player, "conversation.newbie_scout.branchId", 43);
                npcStartConversation(player, npc, "newbie_scout", message, responses);
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
        if (!conversationId.equals("newbie_scout"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.newbie_scout.branchId");
        if (branchId == 2 && newbie_scout_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && newbie_scout_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && newbie_scout_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && newbie_scout_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && newbie_scout_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && newbie_scout_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && newbie_scout_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && newbie_scout_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && newbie_scout_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && newbie_scout_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && newbie_scout_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && newbie_scout_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && newbie_scout_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && newbie_scout_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && newbie_scout_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && newbie_scout_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && newbie_scout_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && newbie_scout_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && newbie_scout_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && newbie_scout_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && newbie_scout_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && newbie_scout_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && newbie_scout_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && newbie_scout_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && newbie_scout_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && newbie_scout_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && newbie_scout_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && newbie_scout_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && newbie_scout_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && newbie_scout_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && newbie_scout_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.newbie_scout.branchId");
        return SCRIPT_CONTINUE;
    }
}
