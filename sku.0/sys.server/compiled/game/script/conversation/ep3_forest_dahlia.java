package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

public class ep3_forest_dahlia extends script.base_script
{
    public ep3_forest_dahlia()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_dahlia";
    public boolean ep3_forest_dahlia_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_dahlia_condition_isGoodGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_3") || groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7"));
    }
    public boolean ep3_forest_dahlia_condition_isBadGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_2"));
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedInitial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_forest_aveso_quest_2") && groundquests.hasCompletedQuest(player, "ep3_forest_cryl_quest_2") && groundquests.hasCompletedQuest(player, "ep3_forest_rhiek_quest_3"));
    }
    public boolean ep3_forest_dahlia_condition_isFemale(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getGender(player) == GENDER_FEMALE);
    }
    public boolean ep3_forest_dahlia_condition_isMale(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getGender(player) == GENDER_MALE);
    }
    public boolean ep3_forest_dahlia_condition_isTaskActiveOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_dahlia_epic_1", 0) || groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_1", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_dahlia_epic_1"));
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_dahlia_epic_1");
    }
    public boolean ep3_forest_dahlia_condition_isTaskActiveTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_dahlia_epic_2", 0) || groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_2", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_2", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_dahlia_epic_2"));
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_dahlia_epic_2");
    }
    public boolean ep3_forest_dahlia_condition_isTaskActiveThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_dahlia_epic_4", 0) || groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_4", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_4", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_dahlia_epic_4"));
    }
    public boolean ep3_forest_dahlia_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_dahlia_epic_4");
    }
    public boolean ep3_forest_dahlia_condition_isTaskStillActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_dahlia_epic_3", 0) || groundquests.hasCompletedTask(player, "ep3_forest_dahlia_epic_3", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void ep3_forest_dahlia_action_giveQuestAssassin(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_outcast_assassin_2");
    }
    public void ep3_forest_dahlia_action_applyDamage(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void ep3_forest_dahlia_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_dahlia_epic_1");
    }
    public void ep3_forest_dahlia_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "warriors");
    }
    public void ep3_forest_dahlia_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_dahlia_epic_2");
    }
    public void ep3_forest_dahlia_action_giveSignalTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "war");
    }
    public void ep3_forest_dahlia_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_dahlia_epic_3");
    }
    public void ep3_forest_dahlia_action_giveSignalThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "win");
        badge.grantBadge(player, "bdg_kash_outcast_story");
    }
    public int ep3_forest_dahlia_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3136"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3138");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3142"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3146");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3154"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3158");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3170"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3172");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3174");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3146"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3150");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3150"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3152");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3158"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3160");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3162");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3162"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3166");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3166"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3168");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3174"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3178");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3178"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3180");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3188"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3190");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3200");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3208"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3210");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3212");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3220");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3224"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3226");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3228");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3240");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3192"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3196");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3200"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3202");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3204");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3196"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3198");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3204"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3206");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3212"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3214");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3216");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3220"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3222");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3216"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3218");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3228"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3232");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3240"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3242");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3232"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_applyDamage(player, npc);
                string_id message = new string_id(c_stringFile, "s_3234");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3236");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3236"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3238");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3246"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3250");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3254"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3256");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3258");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3262"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3264");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3266");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3250"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3252");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3258"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3260");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3266"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3268");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3272"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3274");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3278"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3280");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3282");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3294"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3298");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3314"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_applyDamage(player, npc);
                string_id message = new string_id(c_stringFile, "s_3316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3318");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3282"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3284");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3286");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3290");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3286"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3288");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3290"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3292");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3298"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3302");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3302"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3304");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3306");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3310");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3306"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3308");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3310"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3312");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3318"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3320");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3322");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3322"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3324");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3326");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3326"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3338");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3330"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3332");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3334");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3338"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3340");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3334"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3336");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3344"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3346");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3348");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3352"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3354");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3356");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3360"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3362");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3364");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3348"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3350");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3356"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3358");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3364"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3366");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3370"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3372");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3376"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3378");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3380");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3396"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3398");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3400");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3412"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3414");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3416");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3428");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3380"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3382");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3384");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3392");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3384"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3386");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3388");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3392"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3394");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3388"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3390");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3400"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3402");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3404");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3408");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3404"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3406");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3408"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3410");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3416"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3418");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3420");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3424");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3428"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_applyDamage(player, npc);
                string_id message = new string_id(c_stringFile, "s_3430");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3432");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3420"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3422");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3424"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3426");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3432"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3436");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3448");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3436"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3438");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3440");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3444");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3448"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3450");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3440"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                ep3_forest_dahlia_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3442");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3444"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3446");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3454"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3456");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_dahlia_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3462"))
        {
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3464");
                utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
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
            detachScript(self, "conversation.ep3_forest_dahlia");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "dahlia"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "dahlia"));
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.ep3_forest_dahlia");
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
        if (ep3_forest_dahlia_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3134");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3136");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 1);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_hasCompletedTaskThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3140");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition_isFemale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_dahlia_condition_isMale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3142");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3154");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3170");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 3);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_isTaskActiveThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3182");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_isTaskStillActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3184");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3186");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition_isMale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_dahlia_condition_isFemale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3188");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3208");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3224");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 16);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_hasCompletedTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3244");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition_isMale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_dahlia_condition_isFemale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3246");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3254");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3262");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 31);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_isTaskActiveTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3270");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3272");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 38);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3276");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition_isMale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_dahlia_condition_isFemale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3278");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3294");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3314");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 40);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_hasCompletedTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3342");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition_isFemale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_dahlia_condition_isMale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3344");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3352");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3360");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 57);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_isTaskActiveOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3368");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3370");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 64);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_hasCompletedInitial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3374");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition_isFemale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_dahlia_condition_isMale(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3376");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3396");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3412");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 66);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_isBadGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3452");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3454");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 86);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition_isGoodGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3458");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3460");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_dahlia_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3462");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_dahlia.branchId", 89);
                npcStartConversation(player, npc, "ep3_forest_dahlia", message, responses);
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
        if (!conversationId.equals("ep3_forest_dahlia"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
        if (branchId == 1 && ep3_forest_dahlia_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_forest_dahlia_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_forest_dahlia_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_forest_dahlia_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_forest_dahlia_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_forest_dahlia_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_forest_dahlia_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_forest_dahlia_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_forest_dahlia_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_forest_dahlia_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_forest_dahlia_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_forest_dahlia_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_forest_dahlia_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_forest_dahlia_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_forest_dahlia_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_forest_dahlia_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_forest_dahlia_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_forest_dahlia_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && ep3_forest_dahlia_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_forest_dahlia_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_forest_dahlia_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && ep3_forest_dahlia_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && ep3_forest_dahlia_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && ep3_forest_dahlia_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && ep3_forest_dahlia_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_forest_dahlia_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && ep3_forest_dahlia_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && ep3_forest_dahlia_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && ep3_forest_dahlia_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && ep3_forest_dahlia_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && ep3_forest_dahlia_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && ep3_forest_dahlia_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && ep3_forest_dahlia_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && ep3_forest_dahlia_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && ep3_forest_dahlia_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && ep3_forest_dahlia_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && ep3_forest_dahlia_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && ep3_forest_dahlia_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && ep3_forest_dahlia_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && ep3_forest_dahlia_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && ep3_forest_dahlia_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && ep3_forest_dahlia_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && ep3_forest_dahlia_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && ep3_forest_dahlia_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && ep3_forest_dahlia_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && ep3_forest_dahlia_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && ep3_forest_dahlia_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && ep3_forest_dahlia_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && ep3_forest_dahlia_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && ep3_forest_dahlia_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && ep3_forest_dahlia_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && ep3_forest_dahlia_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_dahlia.branchId");
        return SCRIPT_CONTINUE;
    }
}
