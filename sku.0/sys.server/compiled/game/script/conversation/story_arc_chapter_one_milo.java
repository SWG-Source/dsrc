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

public class story_arc_chapter_one_milo extends script.base_script
{
    public story_arc_chapter_one_milo()
    {
    }
    public static String c_stringFile = "conversation/story_arc_chapter_one_milo";
    public boolean story_arc_chapter_one_milo_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_arc_chapter_one_milo_condition_hasCompletedPrelude(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_story_arc_prelude_03");
    }
    public boolean story_arc_chapter_one_milo_condition_isOnFirstMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_story_arc_chapter_one_01") || groundquests.isQuestActive(player, "som_story_arc_chapter_one_02") || groundquests.isQuestActive(player, "som_story_arc_chapter_one_03"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean story_arc_chapter_one_milo_condition_isOnSecondMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_story_arc_chapter_two_01");
    }
    public boolean story_arc_chapter_one_milo_condition_hk47IsAlive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_two_01", "mustafar_factory_five") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_two_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean story_arc_chapter_one_milo_condition_isFightingDroids(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_story_arc_chapter_three_01");
    }
    public boolean story_arc_chapter_one_milo_condition_hasWonFactory(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_milo_report") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_three_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean story_arc_chapter_one_milo_condition_hasWonStoryArc(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_story_arc_chapter_three_03");
    }
    public boolean story_arc_chapter_one_milo_condition_isFightingHK(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_story_arc_chapter_three_03");
    }
    public boolean story_arc_chapter_one_milo_condition_hasDefeatedHK(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_story_arc_chapter_three_03", "volcano_arena_four");
    }
    public boolean story_arc_chapter_one_milo_condition_messageWaiting(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_story_arc_chapter_three_03", "volcano_arena_five");
    }
    public boolean story_arc_chapter_one_milo_condition_hasCompletedFirstMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_story_arc_chapter_one_01");
    }
    public boolean story_arc_chapter_one_milo_condition_hasCompletedSecondMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_story_arc_chapter_one_02");
    }
    public boolean story_arc_chapter_one_milo_condition_hasCompletedThirdMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_story_arc_chapter_one_03");
    }
    public boolean story_arc_chapter_one_milo_condition_hasCompletedFourthMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_story_arc_chapter_two_01");
    }
    public void story_arc_chapter_one_milo_action_grantFirstMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_story_arc_chapter_one_01");
    }
    public void story_arc_chapter_one_milo_action_grantFinalChapter(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mustafar_factory_finish");
        groundquests.grantQuest(player, "som_story_arc_chapter_three_01");
    }
    public void story_arc_chapter_one_milo_action_grantFinalReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hk_story_arc_completed");
    }
    public void story_arc_chapter_one_milo_action_startVolcanoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mustafar_droidfactory_victory");
        groundquests.grantQuest(player, "som_story_arc_chapter_three_03");
    }
    public void story_arc_chapter_one_milo_action_checkForError(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_story_arc_chapter_one_01"))
        {
            groundquests.completeQuest(player, "som_story_arc_chapter_one_01");
        }
    }
    public int story_arc_chapter_one_milo_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            doAnimationAction(player, "laugh_titter");
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                story_arc_chapter_one_milo_action_grantFinalReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            doAnimationAction(player, "explain");
            story_arc_chapter_one_milo_action_checkForError(player, npc);
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                story_arc_chapter_one_milo_action_startVolcanoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_113");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            doAnimationAction(player, "embarrassed");
            story_arc_chapter_one_milo_action_checkForError(player, npc);
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "huh");
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            doAnimationAction(player, "explain");
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            doAnimationAction(player, "shrug_hands");
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shakefist");
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                story_arc_chapter_one_milo_action_grantFinalChapter(player, npc);
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_133"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                story_arc_chapter_one_milo_action_checkForError(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                story_arc_chapter_one_milo_action_checkForError(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_127"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                story_arc_chapter_one_milo_action_checkForError(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_109");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "small");
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_109"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_117");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_one_milo_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                story_arc_chapter_one_milo_action_grantFirstMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
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
            detachScript(self, "conversation.story_arc_chapter_one_milo");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Milo Mensix");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Milo Mensix");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.story_arc_chapter_one_milo");
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
        if (story_arc_chapter_one_milo_condition_hasWonStoryArc(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_101");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_messageWaiting(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_123");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasDefeatedHK(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_114");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 3);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_isFightingHK(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_111");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasWonFactory(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_94");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 6);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_isFightingDroids(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_93");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 10);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hk47IsAlive(player, npc))
        {
            doAnimationAction(npc, "sweat");
            story_arc_chapter_one_milo_action_checkForError(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 12);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasCompletedFourthMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_132");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 18);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_isOnSecondMission(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_32");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 20);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasCompletedThirdMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_129");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 22);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasCompletedSecondMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_126");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 24);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasCompletedFirstMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_125");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_isOnFirstMission(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 27);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition_hasCompletedPrelude(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_40");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId", 29);
                npcStartConversation(player, npc, "story_arc_chapter_one_milo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_one_milo_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shoo");
            string_id message = new string_id(c_stringFile, "s_124");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_arc_chapter_one_milo"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
        if (branchId == 3 && story_arc_chapter_one_milo_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && story_arc_chapter_one_milo_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && story_arc_chapter_one_milo_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && story_arc_chapter_one_milo_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && story_arc_chapter_one_milo_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && story_arc_chapter_one_milo_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && story_arc_chapter_one_milo_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && story_arc_chapter_one_milo_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && story_arc_chapter_one_milo_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && story_arc_chapter_one_milo_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && story_arc_chapter_one_milo_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && story_arc_chapter_one_milo_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && story_arc_chapter_one_milo_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && story_arc_chapter_one_milo_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && story_arc_chapter_one_milo_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && story_arc_chapter_one_milo_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && story_arc_chapter_one_milo_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && story_arc_chapter_one_milo_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && story_arc_chapter_one_milo_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && story_arc_chapter_one_milo_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && story_arc_chapter_one_milo_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && story_arc_chapter_one_milo_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && story_arc_chapter_one_milo_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && story_arc_chapter_one_milo_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.story_arc_chapter_one_milo.branchId");
        return SCRIPT_CONTINUE;
    }
}
