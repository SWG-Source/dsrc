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

public class legacy_head_ev9d9 extends script.base_script
{
    public legacy_head_ev9d9()
    {
    }
    public static String c_stringFile = "conversation/legacy_head_ev9d9";
    public boolean legacy_head_ev9d9_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_head_ev9d9_condition_failedRunaways(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "legacy_head_remodel") && !groundquests.isQuestActiveOrComplete(player, "legacy_head_runaway") && !groundquests.isQuestActiveOrComplete(player, "legacy_head_space"));
    }
    public boolean legacy_head_ev9d9_condition_failedRod(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_start");
        int questId2 = questGetQuestId("quest/legacy_head_rod");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_head_ev9d9_condition_failedRemodel(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_rod");
        int questId2 = questGetQuestId("quest/legacy_head_remodel");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_head_ev9d9_condition_onRunawayGround(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_head_runaway");
    }
    public boolean legacy_head_ev9d9_condition_onRods(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_rod");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean legacy_head_ev9d9_condition_onRemodel(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_remodel");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean legacy_head_ev9d9_condition_failedReward(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_remodel");
        int questId2 = questGetQuestId("quest/legacy_head_ground");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_head_ev9d9_condition_onStartEV(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_start");
        int ground = groundquests.getTaskId(questId1, "legacy_head_start_e11");
        boolean onTask = (questIsTaskActive(questId1, ground, player));
        return onTask;
    }
    public boolean legacy_head_ev9d9_condition_hasPilotSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice") || hasSkill(player, "pilot_imperial_navy_novice") || hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean legacy_head_ev9d9_condition_onRemodelReward(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_remodel");
        int ground = groundquests.getTaskId(questId1, "legacy_head_remodel_e67");
        boolean onTask = (questIsTaskActive(questId1, ground, player));
        return onTask;
    }
    public boolean legacy_head_ev9d9_condition_failedR3Space(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuestRecursive(player, "assassinate", "tatooine_legacy_head_runaway1_space") || space_quest.hasAbortedQuestRecursive(player, "assassinate", "tatooine_legacy_head_runaway1_space");
    }
    public boolean legacy_head_ev9d9_condition_onSpaceQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_head_space");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean legacy_head_ev9d9_condition_hasHead(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean legacy_head_ev9d9_condition_hasHead = false;
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = getTemplateName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("object/tangible/loot/simple_kit/legacy_droid_head.iff"))
                {
                    legacy_head_ev9d9_condition_hasHead = true;
                }
            }
        }
        return legacy_head_ev9d9_condition_hasHead;
    }
    public boolean legacy_head_ev9d9_condition_remodelComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((groundquests.isTaskActive(player, "legacy_head_remodel", "legacy_head_remodel_e67")) || (groundquests.hasCompletedQuest(player, "legacy_head_remodel") && !groundquests.isQuestActiveOrComplete(player, "legacy_head_runaway")))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean legacy_head_ev9d9_condition_winR3Space(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((space_quest.hasWonQuestRecursive(player, "assassinate", "tatooine_legacy_head_runaway3_space")) && (groundquests.isQuestActive(player, "legacy_head_space")));
    }
    public boolean legacy_head_ev9d9_condition_endEV9D9(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "legacy_together_2");
    }
    public boolean legacy_head_ev9d9_condition_QuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((groundquests.hasCompletedQuest(player, "quest/legacy_head_runaway") || groundquests.hasCompletedQuest(player, "quest/legacy_head_space")) && !groundquests.hasCompletedQuest(player, "legacy_together_2"));
    }
    public boolean legacy_head_ev9d9_condition_rewardQuestground(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "legacy_head_runaway", "legacy_head_runaway_e91");
    }
    public void legacy_head_ev9d9_action_spaceQuestGranted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_head_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway1_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway2_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway3_space");
        groundquests.sendSignal(player, "legacy_head_start_launch_e2");
    }
    public void legacy_head_ev9d9_action_r3SpaceCompletedSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_head_space_launch_e3");
    }
    public void legacy_head_ev9d9_action_grantR3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "legacy_head_space");
        groundquests.grantQuest(player, "legacy_head_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway1_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway2_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway3_space");
    }
    public void legacy_head_ev9d9_action_regrantGround(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_head_runaway");
        return;
    }
    public void legacy_head_ev9d9_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void legacy_head_ev9d9_action_regrantSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "legacy_head_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway1_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway2_space");
        space_quest.clearQuestFlags(player, "assassinate", "tatooine_legacy_head_runaway3_space");
        groundquests.grantQuest(player, "legacy_head_space");
    }
    public void legacy_head_ev9d9_action_grantReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_head_ground");
    }
    public void legacy_head_ev9d9_action_rewardRemodel(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "legacy_head_remodel_launch_e8");
    }
    public void legacy_head_ev9d9_action_foundev9d9(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_head_start_launch_e2");
    }
    public void legacy_head_ev9d9_action_regrantTogether(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActiveOrComplete(player, "legacy_together_2"))
        {
            groundquests.clearQuest(player, "legacy_together");
            groundquests.clearQuest(player, "legacy_together_2");
            groundquests.grantQuest(player, "legacy_together_2");
        }
    }
    public void legacy_head_ev9d9_action_regrantTogetherWithNewHead(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "legacy_together");
        groundquests.clearQuest(player, "legacy_together_2");
        groundquests.grantQuest(player, "legacy_together_2");
        obj_id[] head = new obj_id[1];
        head[0] = createObjectInInventoryAllowOverload("object/tangible/loot/simple_kit/legacy_droid_head.iff", player);
        showLootBox(player, head);
    }
    public void legacy_head_ev9d9_action_grantTogetherQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_together_2");
    }
    public void legacy_head_ev9d9_action_spaceRewardSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "legacy_head_ground_launch_e92");
    }
    public void legacy_head_ev9d9_action_grantRunawayGround(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_head_runaway");
        return;
    }
    public void legacy_head_ev9d9_action_grantShock(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_head_rod");
    }
    public void legacy_head_ev9d9_action_grantRemodel(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_head_remodel");
    }
    public void legacy_head_ev9d9_action_cleanOldQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActiveOrComplete(player, "legacy_together_2") || groundquests.isQuestActiveOrComplete(player, "legacy_together"))
        {
            if (groundquests.isQuestActive(player, "legacy_head_remodel"))
            {
                groundquests.completeQuest(player, "legacy_head_remodel");
            }
            if (groundquests.isQuestActive(player, "legacy_head_rod"))
            {
                groundquests.completeQuest(player, "legacy_head_rod");
            }
            if (groundquests.isQuestActive(player, "legacy_head_runaway"))
            {
                groundquests.completeQuest(player, "legacy_head_runaway");
            }
            if (groundquests.isQuestActive(player, "legacy_head_space"))
            {
                groundquests.completeQuest(player, "legacy_head_space");
            }
        }
    }
    public void legacy_head_ev9d9_action_givePainBolts(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasBolt_1 = false;
        boolean hasBolt_2 = false;
        boolean hasBolt_3 = false;
        Vector boltsResizable = new Vector();
        boltsResizable.setSize(0);
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = getTemplateName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("object/tangible/quest/legacy_head_pain1.iff"))
                {
                    hasBolt_1 = true;
                }
                if (templateName.equals("object/tangible/quest/legacy_head_pain2.iff"))
                {
                    hasBolt_2 = true;
                }
                if (templateName.equals("object/tangible/quest/legacy_head_pain3.iff"))
                {
                    hasBolt_3 = true;
                }
            }
        }
        if (hasBolt_1 == false)
        {
            utils.addElement(boltsResizable, createObjectInInventoryAllowOverload("object/tangible/quest/legacy_head_pain1.iff", player));
        }
        if (hasBolt_2 == false)
        {
            utils.addElement(boltsResizable, createObjectInInventoryAllowOverload("object/tangible/quest/legacy_head_pain2.iff", player));
        }
        if (hasBolt_3 == false)
        {
            utils.addElement(boltsResizable, createObjectInInventoryAllowOverload("object/tangible/quest/legacy_head_pain3.iff", player));
        }
        obj_id[] bolts = new obj_id[0];
        if (boltsResizable != null)
        {
            bolts = new obj_id[boltsResizable.size()];
            boltsResizable.toArray(bolts);
        }
        if (bolts.length > 0)
        {
            showLootBox(player, bolts);
        }
        return;
    }
    public int legacy_head_ev9d9_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_regrantTogetherWithNewHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_regrantTogether(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_117"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_spaceRewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantTogetherQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_r3SpaceCompletedSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantR3(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_r3SpaceCompletedSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_103");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_spaceRewardSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantRunawayGround(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_139"))
        {
            if (legacy_head_ev9d9_condition_hasPilotSkill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_149");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!legacy_head_ev9d9_condition_hasPilotSkill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_255");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_259");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_141"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_149"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_153");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_199"))
        {
            legacy_head_ev9d9_action_facePlayer(player, npc);
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_201");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_157"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantRunawayGround(player, npc);
                string_id message = new string_id(c_stringFile, "s_161");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_260"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_203"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_spaceQuestGranted(player, npc);
                string_id message = new string_id(c_stringFile, "s_205");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_207"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_257"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_153");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_259"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_rewardRemodel(player, npc);
                string_id message = new string_id(c_stringFile, "s_263");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_141");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_139"))
        {
            if (legacy_head_ev9d9_condition_hasPilotSkill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_149");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!legacy_head_ev9d9_condition_hasPilotSkill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_255");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_259");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_141"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_226"))
        {
            if (legacy_head_ev9d9_condition_hasPilotSkill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_232");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!legacy_head_ev9d9_condition_hasPilotSkill(player, npc))
            {
                legacy_head_ev9d9_action_grantRunawayGround(player, npc);
                string_id message = new string_id(c_stringFile, "s_228");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_232"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_regrantSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_234");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_236"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_regrantGround(player, npc);
                string_id message = new string_id(c_stringFile, "s_238");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_240"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantRemodel(player, npc);
                string_id message = new string_id(c_stringFile, "s_242");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_216"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantShock(player, npc);
                string_id message = new string_id(c_stringFile, "s_218");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_246"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_248");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            legacy_head_ev9d9_action_foundev9d9(player, npc);
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_174"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantShock(player, npc);
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_93");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_135"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_head_ev9d9_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                legacy_head_ev9d9_action_grantShock(player, npc);
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_95"))
        {
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
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
            detachScript(self, "conversation.legacy_head_ev9d9");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "EV-9D9");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "EV-9D9");
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
        detachScript(self, "conversation.legacy_head_ev9d9");
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
        if (legacy_head_ev9d9_condition_endEV9D9(player, npc))
        {
            legacy_head_ev9d9_action_cleanOldQuests(player, npc);
            string_id message = new string_id(c_stringFile, "s_99");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_QuestComplete(player, npc))
        {
            legacy_head_ev9d9_action_spaceRewardSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!legacy_head_ev9d9_condition_hasHead(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_head_ev9d9_condition_hasHead(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 2);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_rewardQuestground(player, npc))
        {
            legacy_head_ev9d9_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 5);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_onSpaceQuests(player, npc))
        {
            legacy_head_ev9d9_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_47");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_head_ev9d9_condition_failedR3Space(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (legacy_head_ev9d9_condition_winR3Space(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 8);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_onRunawayGround(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_123");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 12);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_remodelComplete(player, npc))
        {
            legacy_head_ev9d9_action_rewardRemodel(player, npc);
            string_id message = new string_id(c_stringFile, "s_133");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_141");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 14);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_onRemodelReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_261");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 21);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_onRemodel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_127");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_onRods(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_125");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_failedRunaways(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_224");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 25);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_failedRemodel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_223");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition_failedRemodel(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 30);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_failedRod(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_196");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition_failedRod(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_216");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 32);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_failedReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_244");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 34);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition_onStartEV(player, npc))
        {
            legacy_head_ev9d9_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_79");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_81");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                }
                utils.setScriptVar(player, "conversation.legacy_head_ev9d9.branchId", 36);
                npcStartConversation(player, npc, "legacy_head_ev9d9", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_head_ev9d9_condition__defaultCondition(player, npc))
        {
            legacy_head_ev9d9_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_180");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("legacy_head_ev9d9"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
        if (branchId == 2 && legacy_head_ev9d9_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && legacy_head_ev9d9_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && legacy_head_ev9d9_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && legacy_head_ev9d9_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && legacy_head_ev9d9_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && legacy_head_ev9d9_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && legacy_head_ev9d9_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && legacy_head_ev9d9_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && legacy_head_ev9d9_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && legacy_head_ev9d9_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && legacy_head_ev9d9_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && legacy_head_ev9d9_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && legacy_head_ev9d9_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && legacy_head_ev9d9_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && legacy_head_ev9d9_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && legacy_head_ev9d9_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && legacy_head_ev9d9_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && legacy_head_ev9d9_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && legacy_head_ev9d9_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && legacy_head_ev9d9_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && legacy_head_ev9d9_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_head_ev9d9.branchId");
        return SCRIPT_CONTINUE;
    }
}
