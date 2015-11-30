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
import script.library.groundquests;
import script.library.money;
import script.library.static_item;
import script.library.trial;
import script.library.utils;

public class tatooine_espa_watto extends script.base_script
{
    public tatooine_espa_watto()
    {
    }
    public static String c_stringFile = "conversation/tatooine_espa_watto";
    public boolean tatooine_espa_watto_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_espa_watto_condition_startWatto(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_button_imp_pt2") || groundquests.hasCompletedQuest(player, "legacy_button_reb_pt2") || groundquests.isTaskActive(player, "legacy_watto_pointer", "legacy_watto_e1") || groundquests.hasCompletedQuest(player, "legacy_watto_pointer");
    }
    public boolean tatooine_espa_watto_condition_onBargain(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_wattos_bargain");
        int tat_espa_bargain_e1 = groundquests.getTaskId(questId1, "tat_espa_bargain_e1");
        boolean onTask = questIsTaskActive(questId1, tat_espa_bargain_e1, player);
        return onTask;
    }
    public boolean tatooine_espa_watto_condition_bargainTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_wattos_bargain");
        int tat_espa_bargain_e1 = groundquests.getTaskId(questId1, "tat_espa_bargain_e2");
        boolean onTask = questIsTaskActive(questId1, tat_espa_bargain_e1, player);
        return onTask;
    }
    public boolean tatooine_espa_watto_condition_onSquill(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_squill_diving");
        int tat_espa_bargain_e1 = groundquests.getTaskId(questId1, "tat_espa_squill_e1");
        boolean onTask = questIsTaskActive(questId1, tat_espa_bargain_e1, player);
        return onTask;
    }
    public boolean tatooine_espa_watto_condition_squillComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/tatooine_espa_squill_diving", "tat_espa_squill_e2") || groundquests.hasCompletedQuest(player, "quest/tatooine_espa_squill_diving"));
    }
    public boolean tatooine_espa_watto_condition_onHead(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "legacy_head_start");
    }
    public boolean tatooine_espa_watto_condition_onTogether(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "quest/legacy_together") || groundquests.isQuestActive(player, "quest/legacy_together_2");
    }
    public boolean tatooine_espa_watto_condition_completedKick(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasCompleted = false;
        if (groundquests.isQuestActiveOrComplete(player, "naboo_kadaraa_tipping_the_balance_1"))
        {
            hasCompleted = true;
        }
        if (groundquests.hasCompletedQuest(player, "quest/legacy_naboo_kick_imp") || groundquests.hasCompletedQuest(player, "quest/legacy_naboo_kick_reb"))
        {
            hasCompleted = true;
        }
        return hasCompleted;
    }
    public boolean tatooine_espa_watto_condition_bodyTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_v2", "tat_espa_body_building_e5");
    }
    public boolean tatooine_espa_watto_condition_onBody(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/tatooine_espa_body_building", "tat_espa_body_building_e1") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building", "tat_espa_body_building_e2") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building", "tat_espa_body_building_e3") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building", "tat_espa_body_building_e4") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_2", "tat_espa_body_building_e1") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_2", "tat_espa_body_building_e2") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_2", "tat_espa_body_building_e3") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_2", "tat_espa_body_building_e4") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_v2", "tat_espa_body_building_e1") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_v2", "tat_espa_body_building_e2") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_v2", "tat_espa_body_building_e3") || groundquests.isTaskActive(player, "quest/tatooine_espa_body_building_v2", "tat_espa_body_building_e4"));
    }
    public boolean tatooine_espa_watto_condition_onLegs(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work", "tat_espa_leg_work_e1") || groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work", "tat_espa_leg_work_e2") || groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work", "tat_espa_leg_work_e3") || groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work_2", "tat_espa_leg_work_e2") || groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work_2", "tat_espa_leg_work_e3"));
    }
    public boolean tatooine_espa_watto_condition_legTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work", "tat_espa_leg_work_e4") || groundquests.isTaskActive(player, "quest/tatooine_espa_leg_work_2", "tat_espa_leg_work_e4"));
    }
    public boolean tatooine_espa_watto_condition_onPod(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_espa_pod_retrieval_v2");
        int tat_espa_pod_retrieval_e1 = groundquests.getTaskId(questId, "tat_espa_pod_retrieval_e1");
        int tat_espa_pod_retrieval_e2 = groundquests.getTaskId(questId, "tat_espa_pod_retrieval_e2");
        boolean onTask1 = questIsTaskActive(questId, tat_espa_pod_retrieval_e1, player);
        boolean onTask2 = questIsTaskActive(questId, tat_espa_pod_retrieval_e2, player);
        if (onTask1 || onTask2)
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_espa_watto_condition_podTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_pod_retrieval_v2");
        int tat_espa_pod_retrieval_e3 = groundquests.getTaskId(questId1, "tat_espa_pod_retrieval_e5");
        boolean onTask = questIsTaskActive(questId1, tat_espa_pod_retrieval_e3, player);
        return onTask;
    }
    public boolean tatooine_espa_watto_condition_onRepo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "tatooine_espa_repo_man", "tat_espa_repo_man_e3");
    }
    public boolean tatooine_espa_watto_condition_repoTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_repo_man");
        int tat_espa_repo_man_e4 = groundquests.getTaskId(questId1, "tat_espa_repo_man_e4");
        boolean onTask = questIsTaskActive(questId1, tat_espa_repo_man_e4, player);
        return onTask;
    }
    public boolean tatooine_espa_watto_condition_onJuiceDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "legacy_together_2", "legacy_together_e5") || groundquests.hasCompletedTask(player, "legacy_together_2", "legacy_together_e5"));
    }
    public boolean tatooine_espa_watto_condition_wattoComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "legacy_together_2") || groundquests.hasCompletedQuest(player, "legacy_together"));
    }
    public boolean tatooine_espa_watto_condition_startWattoComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_watto_pointer");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_espa_watto_condition_bargainComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_wattos_bargain");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_espa_watto_condition_bodyComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "quest/tatooine_espa_body_building") || groundquests.hasCompletedQuest(player, "quest/tatooine_espa_body_building_2") || groundquests.hasCompletedQuest(player, "quest/tatooine_espa_body_building_v2"));
    }
    public boolean tatooine_espa_watto_condition_legsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "quest/tatooine_espa_leg_work") || groundquests.hasCompletedQuest(player, "quest/tatooine_espa_leg_work_2") || groundquests.hasCompletedQuest(player, "quest/tatooine_espa_leg_work_v2"));
    }
    public boolean tatooine_espa_watto_condition_podComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_pod_retrieval");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_espa_watto_condition_repoComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_espa_repo_man");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_espa_watto_condition_lifeDayStapQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "boughtStap"))
        {
            return false;
        }
        if (groundquests.isTaskActive(player, "lifeday_stap_1", "talkSaun_needsStap") || groundquests.isTaskActive(player, "lifeday_stap_1", "talkSaun_hasStap"))
        {
            return true;
        }
        if (groundquests.hasCompletedQuest(player, "lifeday_stap_1"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_espa_watto_condition_lifedayStapCheap(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.hasFunds(player, money.MT_TOTAL, 10000))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_espa_watto_condition_lifedayStapExpensive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.hasFunds(player, money.MT_TOTAL, 20000))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_espa_watto_condition_gotAnyTokens(obj_id player, obj_id npc) throws InterruptedException
    {
        if (trial.getTokenTotal(player, trial.SPACE_DUTY_TOKEN) > 0)
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_espa_watto_condition_gotEnoughTokens(obj_id player, obj_id npc) throws InterruptedException
    {
        if (trial.getTokenTotal(player, trial.SPACE_DUTY_TOKEN) >= 50)
        {
            return true;
        }
        return false;
    }
    public void tatooine_espa_watto_action_grantBargain(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_espa_wattos_bargain");
        groundquests.requestGrantQuest(questId, player, npc, true);
    }
    public void tatooine_espa_watto_action_signalGotoWatto(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "legacy_watto_e1");
    }
    public void tatooine_espa_watto_action_grantBody(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "tatooine_espa_body_building_v2");
    }
    public void tatooine_espa_watto_action_signalBargainComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_espa_bargain_e2");
    }
    public void tatooine_espa_watto_action_grantLegs(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/tatooine_espa_leg_work");
    }
    public void tatooine_espa_watto_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void tatooine_espa_watto_action_signalBodyComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_espa_body_building_e5");
    }
    public void tatooine_espa_watto_action_grantPod(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_espa_pod_retrieval_v2");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_espa_watto_action_signalLegsReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_espa_leg_work_e4");
    }
    public void tatooine_espa_watto_action_grantRepo(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_espa_repo_man");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_espa_watto_action_grantSquillQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_espa_squill_diving");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_espa_watto_action_grantHead(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/legacy_head_start");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_espa_watto_action_signalBuildDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_together_launch_e2");
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = getTemplateName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("object/tangible/loot/simple_kit/legacy_droid_head.iff"))
                {
                    destroyObject(playerStuff[i]);
                }
            }
        }
    }
    public void tatooine_espa_watto_action_signalReturnDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_together_launch_e6");
    }
    public void tatooine_espa_watto_action_signalPoweredDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_together_launch_e8");
    }
    public void tatooine_espa_watto_action_signalWattoPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_watto_e1");
    }
    public void tatooine_espa_watto_action_signalPodReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_espa_pod_retrieval_e5");
    }
    public void tatooine_espa_watto_action_grantRepoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_espa_repo_man_e4");
    }
    public void tatooine_espa_watto_action_grantSquillReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_espa_squill_e2");
    }
    public void tatooine_espa_watto_action_grantGotoKick(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "legacy.faction.imperialPath"))
        {
            int questId1 = questGetQuestId("quest/legacy_naboo_kick_imp");
            groundquests.grantQuest(questId1, player, npc, true);
        }
        else if (hasObjVar(player, "legacy.faction.rebelPath"))
        {
            int questId2 = questGetQuestId("quest/legacy_naboo_kick_reb");
            groundquests.grantQuest(questId2, player, npc, true);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "ERROR : No Naboo quest ObjVar Found!");
        }
    }
    public void tatooine_espa_watto_action_signalDroidRetrieved(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_together_retrieved");
    }
    public void tatooine_espa_watto_action_clearOldTogether(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "quest/legacy_together"))
        {
            groundquests.clearQuest(player, "quest/legacy_together");
            groundquests.grantQuest(player, "quest/legacy_together_2");
        }
    }
    public void tatooine_espa_watto_action_stapCheap(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "boughtStap"))
        {
            obj_id speeder = static_item.createNewItemFunction("item_event_lifeday_stap_speeder", player);
            if (exists(speeder) && isIdValid(speeder))
            {
                money.requestPayment(player, npc, 10000, "no_handler", null, false);
                setObjVar(player, "boughtStap", 1);
            }
        }
    }
    public void tatooine_espa_watto_action_stapExpensive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "boughtStap"))
        {
            obj_id speeder = static_item.createNewItemFunction("item_event_lifeday_stap_speeder", player);
            if (exists(speeder) && isIdValid(speeder))
            {
                money.requestPayment(player, npc, 20000, "no_handler", null, false);
                setObjVar(player, "boughtStap", 1);
            }
        }
    }
    public void tatooine_espa_watto_action_lifedayGreeting(obj_id player, obj_id npc) throws InterruptedException
    {
        playMusic(player, "sound/lifeday_watto_greeting.snd");
    }
    public void tatooine_espa_watto_action_buyInterfaceScanner(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id scanner = static_item.createNewItemFunction("item_interface_scanner_schematic", inventory);
        if (!isIdValid(scanner))
        {
            return;
        }
        if (!trial.purchaseTokenItem(player, 50, trial.SPACE_DUTY_TOKEN))
        {
            destroyObject(scanner);
        }
    }
    public void tatooine_espa_watto_action_buyInterdictionGenerator(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id generator = static_item.createNewItemFunction("item_interdiction_terminal_schematic", inventory);
        if (!isIdValid(generator))
        {
            return;
        }
        if (!trial.purchaseTokenItem(player, 50, trial.SPACE_DUTY_TOKEN))
        {
            destroyObject(generator);
        }
    }
    public int tatooine_espa_watto_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_307"))
        {
            doAnimationAction(player, "nod");
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_308");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_lifedayStapExpensive(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition_lifedayStapCheap(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_309");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_310");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_315");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_309"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_stapExpensive(player, npc);
                string_id message = new string_id(c_stringFile, "s_311");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_310"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_stapCheap(player, npc);
                string_id message = new string_id(c_stringFile, "s_313");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_315"))
        {
            doAnimationAction(player, "shake_head_no");
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_349");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_241"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_242");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!tatooine_espa_watto_condition_completedKick(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_301");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_301"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantGotoKick(player, npc);
                string_id message = new string_id(c_stringFile, "s_302");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_243"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_244");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_399");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_245"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_246");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_247");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_399"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_400");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_375");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_247"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_251");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_251"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_253");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_375"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotAnyTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_422");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_232"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_234"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_235");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_236"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                tatooine_espa_watto_action_signalDroidRetrieved(player, npc);
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_238"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_espa_watto_action_grantGotoKick(player, npc);
                string_id message = new string_id(c_stringFile, "s_239");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_217"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shakefist");
                string_id message = new string_id(c_stringFile, "s_218");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                tatooine_espa_watto_action_signalBuildDroid(player, npc);
                string_id message = new string_id(c_stringFile, "s_220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_381");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_381"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_signalPoweredDroid(player, npc);
                string_id message = new string_id(c_stringFile, "s_382");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_232");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_232"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_212"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_213");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_214"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_171"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_173");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_180"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_183");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_185");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_185"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_186");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_187");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_187"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                string_id message = new string_id(c_stringFile, "s_188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_189");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_189"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_190");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_163"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "beckon");
                string_id message = new string_id(c_stringFile, "s_165");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_167"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_169");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_343"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_344");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_345"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_346");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_347");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_347"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantSquillQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_348");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_135"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_136");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_137");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_137"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "scared");
                string_id message = new string_id(c_stringFile, "s_138");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_139"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_153"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_espa_watto_action_grantSquillQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_158");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                string_id message = new string_id(c_stringFile, "s_131");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_340"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                tatooine_espa_watto_action_grantRepo(player, npc);
                string_id message = new string_id(c_stringFile, "s_341");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_signalPodReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_125"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantRepo(player, npc);
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_127"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_102"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_104");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_333"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wtf");
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_335");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_335"))
        {
            doAnimationAction(player, "embarrassed");
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_336");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_337");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_337"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantPod(player, npc);
                string_id message = new string_id(c_stringFile, "s_338");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_191"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_193");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_signalLegsReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_249");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_250"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_254"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_256");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_264");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_266");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_266"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_268");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_270"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantPod(player, npc);
                string_id message = new string_id(c_stringFile, "s_272");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_274"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_276");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_328"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_329");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_330");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_330"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                tatooine_espa_watto_action_grantLegs(player, npc);
                string_id message = new string_id(c_stringFile, "s_331");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_175"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantLegs(player, npc);
                string_id message = new string_id(c_stringFile, "s_177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_192"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_195");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_199"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_201");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_203"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_325"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                tatooine_espa_watto_action_grantBody(player, npc);
                string_id message = new string_id(c_stringFile, "s_326");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_257"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_261");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_265"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_269");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_273");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_273"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_277");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_279"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                tatooine_espa_watto_action_grantBody(player, npc);
                string_id message = new string_id(c_stringFile, "s_281");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_283");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_283"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_285");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_289"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_291");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_293");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_293"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_295");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch93(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_318"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_319");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_320"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_321");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_322");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_322"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantBargain(player, npc);
                string_id message = new string_id(c_stringFile, "s_323");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_312"))
        {
            tatooine_espa_watto_action_signalWattoPointer(player, npc);
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_314");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 98);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch98(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_316"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_350");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 99);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_352"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_354");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_356");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 100);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch100(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_356"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_358");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_360");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_360"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_362");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_364");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 102);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch102(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_364"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_grantBargain(player, npc);
                string_id message = new string_id(c_stringFile, "s_366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_368");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch103(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_368"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_370");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_374"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                string_id message = new string_id(c_stringFile, "s_376");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_375");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_375"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotAnyTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_422");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch107(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_384"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_386");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_391");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_418");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_422"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_424");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_388"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_buyInterfaceScanner(player, npc);
                string_id message = new string_id(c_stringFile, "s_392");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_391"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_buyInterdictionGenerator(player, npc);
                string_id message = new string_id(c_stringFile, "s_394");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_396"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_398");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_402"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_404");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_391");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_418");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_406"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_408");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_410");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_418"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_420");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch112(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_388"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_buyInterfaceScanner(player, npc);
                string_id message = new string_id(c_stringFile, "s_392");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_391"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_buyInterdictionGenerator(player, npc);
                string_id message = new string_id(c_stringFile, "s_394");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_396"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_398");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_402"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_404");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_391");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_418");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_406"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_408");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_410");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_418"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_420");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_410"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_412");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_414");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 114);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch114(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_414"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_416");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_391");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_418");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_espa_watto_handleBranch115(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_388"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_buyInterfaceScanner(player, npc);
                string_id message = new string_id(c_stringFile, "s_392");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_391"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                tatooine_espa_watto_action_buyInterdictionGenerator(player, npc);
                string_id message = new string_id(c_stringFile, "s_394");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_396"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_398");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_402"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_404");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_espa_watto_condition_gotEnoughTokens(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_391");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_418");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_406"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_408");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_410");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_418"))
        {
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_420");
                utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
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
            detachScript(self, "conversation.tatooine_espa_watto");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.tatooine_espa_watto");
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
        if (tatooine_espa_watto_condition_lifeDayStapQuest(player, npc))
        {
            tatooine_espa_watto_action_lifedayGreeting(player, npc);
            string_id message = new string_id(c_stringFile, "s_306");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_espa_watto", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_wattoComplete(player, npc))
        {
            doAnimationAction(npc, "shrug_hands");
            string_id message = new string_id(c_stringFile, "s_240");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_241");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 6);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onJuiceDroid(player, npc))
        {
            doAnimationAction(npc, "squirm");
            tatooine_espa_watto_action_signalPoweredDroid(player, npc);
            string_id message = new string_id(c_stringFile, "s_231");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_232");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 14);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onTogether(player, npc))
        {
            doAnimationAction(npc, "listen");
            tatooine_espa_watto_action_clearOldTogether(player, npc);
            string_id message = new string_id(c_stringFile, "s_216");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_217");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 19);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onHead(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_211");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 23);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_squillComplete(player, npc))
        {
            doAnimationAction(npc, "greet");
            tatooine_espa_watto_action_grantSquillReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_162");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_171");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 26);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onSquill(player, npc))
        {
            doAnimationAction(npc, "shrug_shoulders");
            string_id message = new string_id(c_stringFile, "s_160");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 32);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_repoComplete(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_342");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_343");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 35);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_repoTaskComplete(player, npc))
        {
            doAnimationAction(npc, "greet");
            tatooine_espa_watto_action_grantRepoReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_134");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 39);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onRepo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_129");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 44);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_podComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_339");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 47);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_podTaskComplete(player, npc))
        {
            doAnimationAction(npc, "huh");
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_121");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 49);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onPod(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_96");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 53);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_legsComplete(player, npc))
        {
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_332");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_333");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 57);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_legTaskComplete(player, npc))
        {
            doAnimationAction(npc, "greet");
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_181");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 61);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onLegs(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_179");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 71);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_bodyComplete(player, npc))
        {
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_327");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 73);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_bodyTaskComplete(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            tatooine_espa_watto_action_signalBodyComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_174");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_175");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 76);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onBody(player, npc))
        {
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_197");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 79);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_bargainComplete(player, npc))
        {
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_324");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_325");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 82);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_bargainTaskComplete(player, npc))
        {
            doAnimationAction(npc, "greet");
            tatooine_espa_watto_action_signalBargainComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_210");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 84);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_onBargain(player, npc))
        {
            doAnimationAction(npc, "laugh");
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_287");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_289");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 90);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_startWattoComplete(player, npc))
        {
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_317");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 93);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition_startWatto(player, npc))
        {
            doAnimationAction(npc, "greet");
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_304");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_312");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 97);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_espa_watto_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            tatooine_espa_watto_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_372");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_espa_watto_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_374");
                }
                utils.setScriptVar(player, "conversation.tatooine_espa_watto.branchId", 105);
                npcStartConversation(player, npc, "tatooine_espa_watto", message, responses);
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
        if (!conversationId.equals("tatooine_espa_watto"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_espa_watto.branchId");
        if (branchId == 1 && tatooine_espa_watto_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && tatooine_espa_watto_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && tatooine_espa_watto_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_espa_watto_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tatooine_espa_watto_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tatooine_espa_watto_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_espa_watto_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tatooine_espa_watto_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_espa_watto_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_espa_watto_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_espa_watto_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_espa_watto_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tatooine_espa_watto_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_espa_watto_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && tatooine_espa_watto_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tatooine_espa_watto_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_espa_watto_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && tatooine_espa_watto_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_espa_watto_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && tatooine_espa_watto_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_espa_watto_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_espa_watto_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && tatooine_espa_watto_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_espa_watto_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tatooine_espa_watto_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && tatooine_espa_watto_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && tatooine_espa_watto_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tatooine_espa_watto_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && tatooine_espa_watto_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_espa_watto_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && tatooine_espa_watto_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && tatooine_espa_watto_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && tatooine_espa_watto_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && tatooine_espa_watto_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && tatooine_espa_watto_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && tatooine_espa_watto_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && tatooine_espa_watto_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && tatooine_espa_watto_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && tatooine_espa_watto_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && tatooine_espa_watto_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && tatooine_espa_watto_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && tatooine_espa_watto_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && tatooine_espa_watto_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && tatooine_espa_watto_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && tatooine_espa_watto_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && tatooine_espa_watto_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && tatooine_espa_watto_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && tatooine_espa_watto_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && tatooine_espa_watto_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && tatooine_espa_watto_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && tatooine_espa_watto_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && tatooine_espa_watto_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && tatooine_espa_watto_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && tatooine_espa_watto_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && tatooine_espa_watto_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && tatooine_espa_watto_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && tatooine_espa_watto_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && tatooine_espa_watto_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && tatooine_espa_watto_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && tatooine_espa_watto_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && tatooine_espa_watto_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && tatooine_espa_watto_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && tatooine_espa_watto_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && tatooine_espa_watto_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && tatooine_espa_watto_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && tatooine_espa_watto_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && tatooine_espa_watto_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && tatooine_espa_watto_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 93 && tatooine_espa_watto_handleBranch93(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && tatooine_espa_watto_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && tatooine_espa_watto_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && tatooine_espa_watto_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 98 && tatooine_espa_watto_handleBranch98(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && tatooine_espa_watto_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 100 && tatooine_espa_watto_handleBranch100(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && tatooine_espa_watto_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 102 && tatooine_espa_watto_handleBranch102(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 103 && tatooine_espa_watto_handleBranch103(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && tatooine_espa_watto_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && tatooine_espa_watto_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 107 && tatooine_espa_watto_handleBranch107(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && tatooine_espa_watto_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 112 && tatooine_espa_watto_handleBranch112(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && tatooine_espa_watto_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 114 && tatooine_espa_watto_handleBranch114(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 115 && tatooine_espa_watto_handleBranch115(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_espa_watto.branchId");
        return SCRIPT_CONTINUE;
    }
}
