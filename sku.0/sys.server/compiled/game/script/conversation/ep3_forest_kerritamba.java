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
import script.library.space_dungeon;
import script.library.utils;

public class ep3_forest_kerritamba extends script.base_script
{
    public ep3_forest_kerritamba()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_kerritamba";
    public boolean ep3_forest_kerritamba_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_1", "cure");
    }
    public boolean ep3_forest_kerritamba_condition_EpicTaskCompletedOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_1", "return");
    }
    public boolean ep3_forest_kerritamba_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_1");
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_2", "waiting");
    }
    public boolean ep3_forest_kerritamba_condition_EpicTaskCompletedTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_2", "return");
    }
    public boolean ep3_forest_kerritamba_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_2");
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_3", 0);
    }
    public boolean ep3_forest_kerritamba_condition_EpicTaskCompletedThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_3", "return");
    }
    public boolean ep3_forest_kerritamba_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_3");
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_4", 0);
    }
    public boolean ep3_forest_kerritamba_condition_EpicTaskCompletedFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_kerritamba_epic_4", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_4"));
    }
    public boolean ep3_forest_kerritamba_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_4");
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveFive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_5", "squeen");
    }
    public boolean ep3_forest_kerritamba_condition_EpicTaskCompletedFive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_kerritamba_epic_5", "squeen") && !groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_5"));
    }
    public boolean ep3_forest_kerritamba_condition_hasCompletedQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_5");
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveSix(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean isEpicActive = groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_6", 0);
        if (isEpicActive)
        {
            groundquests.grantQuest(player, "ep3_arena_challenge");
        }
        return isEpicActive;
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveGood(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0);
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveBad(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedTask(player, "ep3_forest_wirartu_epic_2", 0));
    }
    public boolean ep3_forest_kerritamba_condition_isEpicTaskActiveSeven(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_7", 1) && groundquests.isTaskActive(player, "ep3_forest_kerritamba_epic_7", 2));
    }
    public boolean ep3_forest_kerritamba_condition_EpicTaskCompletedSeven(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_kerritamba_epic_7", 1) && groundquests.hasCompletedTask(player, "ep3_forest_kerritamba_epic_7", 2) && !groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7"));
    }
    public boolean ep3_forest_kerritamba_condition_AllComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7") || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_3"));
    }
    public boolean ep3_forest_kerritamba_condition_isOnHold(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_on_hold", 0);
    }
    public boolean ep3_forest_kerritamba_condition_AAlwaysTrue(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_kerritamba_condition_hasLanguage(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_forest_kerritamba_condition_hasCure(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] invAndEquip = getInventoryAndEquipment(player);
        boolean ep3_forest_kerritamba_condition_hasCure = false;
        for (int i = 0; i < invAndEquip.length; i++)
        {
            String templateName = getTemplateName(invAndEquip[i]);
            if (templateName.equals("object/tangible/loot/quest/ep3_forest_cure.iff"))
            {
                ep3_forest_kerritamba_condition_hasCure = true;
            }
        }
        return ep3_forest_kerritamba_condition_hasCure;
    }
    public boolean ep3_forest_kerritamba_condition_badQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "ep3_forest_kerritamba_assassin");
    }
    public void ep3_forest_kerritamba_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_1");
    }
    public void ep3_forest_kerritamba_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_2");
    }
    public void ep3_forest_kerritamba_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "steps");
    }
    public void ep3_forest_kerritamba_action_giveSignalTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "curestuff");
    }
    public void ep3_forest_kerritamba_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_3");
        if (!ep3_forest_kerritamba_condition_hasCure(player, npc))
        {
            ep3_forest_kerritamba_action_giveCure(player, npc);
        }
    }
    public void ep3_forest_kerritamba_action_giveSignalThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "curereward");
    }
    public void ep3_forest_kerritamba_action_giveQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_4");
    }
    public void ep3_forest_kerritamba_action_giveSignalFour(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sayormi");
    }
    public void ep3_forest_kerritamba_action_giveQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_5");
    }
    public void ep3_forest_kerritamba_action_giveSignalFive(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "epic5");
    }
    public void ep3_forest_kerritamba_action_giveQuestSix(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "ep3_forest_kerritamba_epic_6");
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_6");
        groundquests.grantQuest(player, "ep3_arena_challenge");
    }
    public void ep3_forest_kerritamba_action_giveSignalGood(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "goodguys");
        badge.grantBadge(player, "bdg_kash_arena_champ");
    }
    public void ep3_forest_kerritamba_action_giveSignalBad(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "badguys");
    }
    public void ep3_forest_kerritamba_action_giveQuestAssassin(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_assassin", false);
    }
    public void ep3_forest_kerritamba_action_giveQuestSeven(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_kerritamba_epic_7");
    }
    public void ep3_forest_kerritamba_action_giveSignalSeven(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "alldone");
    }
    public void ep3_forest_kerritamba_action_giveCure(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id cureItem = createObjectInInventoryAllowOverload("object/tangible/loot/quest/ep3_forest_cure.iff", player);
        obj_id[] cure = new obj_id[1];
        cure[0] = cureItem;
        showLootBox(player, cure);
    }
    public void ep3_forest_kerritamba_action_Language(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public void ep3_forest_kerritamba_action_fixBrokenPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) && groundquests.isQuestActiveOrComplete(player, "ep3_forest_kerritamba_epic_7"))
        {
            groundquests.sendSignal(player, "badguys");
        }
    }
    public int ep3_forest_kerritamba_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_607"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalSeven(player, npc);
                string_id message = new string_id(c_stringFile, "s_609");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_611");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_611"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_613");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_619"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_621");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_623");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_627"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_629");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_631");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_623"))
        {
            ep3_forest_kerritamba_action_giveSignalBad(player, npc);
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestAssassin(player, npc);
                string_id message = new string_id(c_stringFile, "s_625");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_631"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_633");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_635");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_635"))
        {
            ep3_forest_kerritamba_action_giveSignalBad(player, npc);
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestSeven(player, npc);
                string_id message = new string_id(c_stringFile, "s_637");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_641"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalGood(player, npc);
                string_id message = new string_id(c_stringFile, "s_643");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_649"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_651");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_653");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_653"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_655");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_657");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_657"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_659");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_661");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_661"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestSix(player, npc);
                string_id message = new string_id(c_stringFile, "s_663");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_665");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_669"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_671");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_665"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_667");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_675"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalFive(player, npc);
                string_id message = new string_id(c_stringFile, "s_677");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_679");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_679"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_681");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_685"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_687");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_691"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_693");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_695");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_695"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_697");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_699");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_699"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_701");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_703");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_703"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_705");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_707");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_711");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_707"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestFive(player, npc);
                string_id message = new string_id(c_stringFile, "s_709");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_711"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_713");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_717"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_719");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_721");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_721"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_723");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_727"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_729");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_733"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_735");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_737");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_737"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_739");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_741");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_745");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_741"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_743");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_745"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_747");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_751"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_753");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_757"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_759");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_761"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveCure(player, npc);
                string_id message = new string_id(c_stringFile, "s_763");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_767"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_769");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_771"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_773");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_775");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_775"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_777");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_779");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_779"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_781");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_783");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_783"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_785");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_789"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_791");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_793");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_793"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_795");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_799"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_801");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_805"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_809");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_809"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_811");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_813");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_817");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_813"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_815");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_817"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_819");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_823"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_827"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_829");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_833"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_835");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_839"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_841");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_843");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_843"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_845");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_847");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_847"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_849");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_867");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_851"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_853");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_855");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_867"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_869");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_871");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_855"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_857");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_859");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_863");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_859"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_861");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_863"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_865");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_871"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_873");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_875");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_875"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_877");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_879");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_879"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_881");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_883");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_883"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_885");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_887");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_891");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_887"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_889");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_891"))
        {
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_893");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
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
            detachScript(self, "conversation.ep3_forest_kerritamba");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "kerritamba"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "kerritamba"));
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
        detachScript(self, "conversation.ep3_forest_kerritamba");
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
        if (ep3_forest_kerritamba_condition_hasLanguage(player, npc))
        {
            ep3_forest_kerritamba_action_Language(player, npc);
            string_id message = new string_id(c_stringFile, "s_690");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_badQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_149");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_AllComplete(player, npc))
        {
            ep3_forest_kerritamba_action_fixBrokenPlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_603");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_EpicTaskCompletedSeven(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_605");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_607");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 4);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveSeven(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_615");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveBad(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_617");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_619");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_627");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 8);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveGood(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_639");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_641");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 14);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveSix(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_645");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_hasCompletedQuestFive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_647");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_649");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 17);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_EpicTaskCompletedFive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_673");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_675");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 24);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveFive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_683");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_685");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 27);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_hasCompletedQuestFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_689");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_691");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 29);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_EpicTaskCompletedFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_715");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_717");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 36);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_725");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_727");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 39);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_731");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_733");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 41);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_EpicTaskCompletedThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_749");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_751");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 46);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_755");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!ep3_forest_kerritamba_condition_hasCure(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_757");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_761");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 48);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_765");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_767");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_771");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 51);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_EpicTaskCompletedTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_787");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_789");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 57);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_797");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_799");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 60);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_803");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_805");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 62);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_EpicTaskCompletedOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_821");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_823");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 67);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition_isEpicTaskActiveOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_831");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_833");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 70);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_837");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_839");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba.branchId", 72);
                npcStartConversation(player, npc, "ep3_forest_kerritamba", message, responses);
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
        if (!conversationId.equals("ep3_forest_kerritamba"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
        if (branchId == 4 && ep3_forest_kerritamba_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_forest_kerritamba_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_forest_kerritamba_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_forest_kerritamba_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_forest_kerritamba_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_forest_kerritamba_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_forest_kerritamba_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_forest_kerritamba_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_forest_kerritamba_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_forest_kerritamba_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_forest_kerritamba_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_forest_kerritamba_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_forest_kerritamba_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_forest_kerritamba_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_forest_kerritamba_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_forest_kerritamba_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_forest_kerritamba_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && ep3_forest_kerritamba_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_forest_kerritamba_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && ep3_forest_kerritamba_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && ep3_forest_kerritamba_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && ep3_forest_kerritamba_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && ep3_forest_kerritamba_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && ep3_forest_kerritamba_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_forest_kerritamba_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && ep3_forest_kerritamba_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && ep3_forest_kerritamba_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && ep3_forest_kerritamba_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && ep3_forest_kerritamba_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && ep3_forest_kerritamba_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && ep3_forest_kerritamba_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && ep3_forest_kerritamba_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && ep3_forest_kerritamba_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && ep3_forest_kerritamba_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && ep3_forest_kerritamba_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && ep3_forest_kerritamba_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && ep3_forest_kerritamba_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && ep3_forest_kerritamba_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && ep3_forest_kerritamba_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && ep3_forest_kerritamba_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && ep3_forest_kerritamba_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && ep3_forest_kerritamba_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && ep3_forest_kerritamba_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && ep3_forest_kerritamba_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && ep3_forest_kerritamba_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && ep3_forest_kerritamba_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && ep3_forest_kerritamba_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && ep3_forest_kerritamba_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && ep3_forest_kerritamba_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && ep3_forest_kerritamba_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && ep3_forest_kerritamba_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && ep3_forest_kerritamba_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba.branchId");
        return SCRIPT_CONTINUE;
    }
}
