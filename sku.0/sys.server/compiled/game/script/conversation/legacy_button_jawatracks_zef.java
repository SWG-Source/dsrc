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

public class legacy_button_jawatracks_zef extends script.base_script
{
    public legacy_button_jawatracks_zef()
    {
    }
    public static String c_stringFile = "conversation/legacy_button_jawatracks_zef";
    public boolean legacy_button_jawatracks_zef_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_button_jawatracks_zef_condition_onDataDownload(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int onBody = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e18");
        int onRats = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e120");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int onBody2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e18r");
        int onRats2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e120r");
        boolean onTask = questIsTaskActive(questId1, onBody, player) || questIsTaskActive(questId1, onRats, player) || questIsTaskActive(questId2, onBody2, player) || questIsTaskActive(questId2, onRats2, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_onDroidDead(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int onBody = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e16");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int onBody2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e16r");
        boolean onTask = questIsTaskActive(questId1, onBody, player) || questIsTaskActive(questId2, onBody2, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_onKillDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int onDroid = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e10");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int onDroid2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e10r");
        boolean onTask = questIsTaskActive(questId2, onDroid2, player) || questIsTaskActive(questId1, onDroid, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_failSandCrawlerImp(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_pt3_v2");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_questTracks2Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        boolean OnTask = questIsQuestComplete(questId1, player) || questIsQuestComplete(questId2, player);
        return OnTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_failSandCrawlerReb(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt3_v2");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_onWompratsDead(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int onBody = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e118");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int onBody2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e118r");
        boolean onTask = questIsTaskActive(questId1, onBody, player) || questIsTaskActive(questId2, onBody2, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_playerDeletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((groundquests.hasCompletedQuest(player, "legacy_button_jawatracks") && !(groundquests.isQuestActiveOrComplete(player, "legacy_button_jawatracks_pt2"))) || (groundquests.hasCompletedQuest(player, "legacy_button_jawatracks_reb") && !(groundquests.isQuestActiveOrComplete(player, "legacy_button_jawatracks_reb_pt2"))));
    }
    public boolean legacy_button_jawatracks_zef_condition_onFailedHarvestor(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int onfailedHarvestor = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e116");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int onfailedHarvestor2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e116r");
        boolean onTask = questIsTaskActive(questId1, onfailedHarvestor, player) || questIsTaskActive(questId2, onfailedHarvestor2, player);
        return onTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_onTracks2Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        boolean OnTask = (questIsQuestActive(questId1, player)) || (questIsQuestActive(questId2, player));
        return OnTask;
    }
    public boolean legacy_button_jawatracks_zef_condition_onkillWomprats(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_jawatracks_pt2");
        int onBody = groundquests.getTaskId(questId1, "legacy_button_jawatracks_e117");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb_pt2");
        int onBody2 = groundquests.getTaskId(questId2, "legacy_button_jawatracks_e117r");
        boolean onTask = questIsTaskActive(questId1, onBody, player) || questIsTaskActive(questId2, onBody2, player);
        return onTask;
    }
    public void legacy_button_jawatracks_zef_action_signaldrioid(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_jawatracks_launch_e14");
    }
    public void legacy_button_jawatracks_zef_action_giveDataPad(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_jawatracks_launch_e17");
    }
    public void legacy_button_jawatracks_zef_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void legacy_button_jawatracks_zef_action_giveSandCrawlerImp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_button_jawatracks_pt3_v2");
    }
    public void legacy_button_jawatracks_zef_action_giveSandCrawlerReb(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_button_jawatracks_reb_pt3_v2");
    }
    public void legacy_button_jawatracks_zef_action_giveZefQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "legacy_button_jawatracks"))
        {
            groundquests.grantQuest(player, "legacy_button_jawatracks_pt2");
        }
        else if (groundquests.hasCompletedQuest(player, "legacy_button_jawatracks_reb"))
        {
            groundquests.grantQuest(player, "legacy_button_jawatracks_reb_pt2");
        }
    }
    public void legacy_button_jawatracks_zef_action_signalWomprats(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_jawatracks_launch_e117");
    }
    public int legacy_button_jawatracks_zef_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                legacy_button_jawatracks_zef_action_giveSandCrawlerReb(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                legacy_button_jawatracks_zef_action_giveSandCrawlerImp(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_152");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_153"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                legacy_button_jawatracks_zef_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                legacy_button_jawatracks_zef_action_giveDataPad(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                legacy_button_jawatracks_zef_action_signalWomprats(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                legacy_button_jawatracks_zef_action_giveDataPad(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                legacy_button_jawatracks_zef_action_signaldrioid(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            doAnimationAction(player, "embarrassed");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_eyes");
                legacy_button_jawatracks_zef_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                legacy_button_jawatracks_zef_action_signaldrioid(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                legacy_button_jawatracks_zef_action_signaldrioid(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                legacy_button_jawatracks_zef_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_91"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                legacy_button_jawatracks_zef_action_giveZefQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_93");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_jawatracks_zef_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
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
            detachScript(self, "conversation.legacy_button_jawatracks_zef");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Zef Ando");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Zef Ando");
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
        detachScript(self, "conversation.legacy_button_jawatracks_zef");
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
        if (legacy_button_jawatracks_zef_condition_questTracks2Complete(player, npc))
        {
            doAnimationAction(npc, "greet");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_101");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_zef_condition_failSandCrawlerReb(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (legacy_button_jawatracks_zef_condition_failSandCrawlerImp(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 1);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onDataDownload(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_150");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 5);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onWompratsDead(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_83");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 8);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onkillWomprats(player, npc))
        {
            doAnimationAction(npc, "backhand");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_82");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onFailedHarvestor(player, npc))
        {
            doAnimationAction(npc, "claw");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_70");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 10);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onDroidDead(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_145");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 16);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onKillDroid(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_144");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition_onTracks2Quest(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 19);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shush");
            legacy_button_jawatracks_zef_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_805");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_jawatracks_zef_condition_onTracks2Quest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (legacy_button_jawatracks_zef_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_91");
                }
                utils.setScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId", 24);
                npcStartConversation(player, npc, "legacy_button_jawatracks_zef", message, responses);
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
        if (!conversationId.equals("legacy_button_jawatracks_zef"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
        if (branchId == 1 && legacy_button_jawatracks_zef_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && legacy_button_jawatracks_zef_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && legacy_button_jawatracks_zef_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && legacy_button_jawatracks_zef_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && legacy_button_jawatracks_zef_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && legacy_button_jawatracks_zef_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && legacy_button_jawatracks_zef_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && legacy_button_jawatracks_zef_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && legacy_button_jawatracks_zef_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && legacy_button_jawatracks_zef_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && legacy_button_jawatracks_zef_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && legacy_button_jawatracks_zef_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && legacy_button_jawatracks_zef_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_button_jawatracks_zef.branchId");
        return SCRIPT_CONTINUE;
    }
}
