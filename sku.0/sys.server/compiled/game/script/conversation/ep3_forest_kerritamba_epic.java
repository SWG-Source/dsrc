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

public class ep3_forest_kerritamba_epic extends script.base_script
{
    public ep3_forest_kerritamba_epic()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_kerritamba_epic";
    public boolean ep3_forest_kerritamba_epic_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_kerritamba_epic_condition_isBadGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_2"));
    }
    public boolean ep3_forest_kerritamba_epic_condition_isGoodGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_3") || groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7"));
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedInitial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_forest_meust_quest_3") && groundquests.hasCompletedQuest(player, "ep3_forest_perusta_quest_2") && groundquests.hasCompletedQuest(player, "ep3_forest_athnalu_quest_2"));
    }
    public boolean ep3_forest_kerritamba_epic_condition_isTaskActiveOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_ardon_quest_1", 0) || groundquests.hasCompletedTask(player, "ep3_forest_ardon_quest_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_ardon_quest_1", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_ardon_quest_1"));
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_ardon_quest_1");
    }
    public boolean ep3_forest_kerritamba_epic_condition_isTaskActiveTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_ardon_quest_2", 0) || groundquests.hasCompletedTask(player, "ep3_forest_ardon_quest_2", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_ardon_quest_2", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_ardon_quest_2"));
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_ardon_quest_2");
    }
    public boolean ep3_forest_kerritamba_epic_condition_isTaskActiveThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_ardon_quest_3", 0) || groundquests.hasCompletedTask(player, "ep3_forest_ardon_quest_3", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_ardon_quest_3", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_ardon_quest_3"));
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_ardon_quest_3");
    }
    public boolean ep3_forest_kerritamba_epic_condition_hasCompletedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        if (badge.hasBadge(player, "bdg_kash_kerritamba"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_kerritamba_epic_condition_temp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "temp", 0) && !groundquests.hasCompletedQuest(player, "temp"));
    }
    public void ep3_forest_kerritamba_epic_action_addAssassins(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.hasCompletedQuest(player, "ep3_forest_ardon_assassin"))
        {
            groundquests.grantQuest(player, "ep3_forest_ardon_assassin");
        }
        return;
    }
    public void ep3_forest_kerritamba_epic_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_ardon_quest_1");
    }
    public void ep3_forest_kerritamba_epic_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "outcasts");
    }
    public void ep3_forest_kerritamba_epic_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_ardon_quest_2");
    }
    public void ep3_forest_kerritamba_epic_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_ardon_quest_3");
    }
    public void ep3_forest_kerritamba_epic_action_giveSignalTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "exemplar");
    }
    public void ep3_forest_kerritamba_epic_action_giveSignalThree(obj_id player, obj_id npc) throws InterruptedException
    {
        badge.grantBadge(player, "bdg_kash_kerritamba");
    }
    public int ep3_forest_kerritamba_epic_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1522"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1524");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1528"))
        {
            ep3_forest_kerritamba_epic_action_giveSignalThree(player, npc);
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_epic_action_giveSignalThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_1530");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1534"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_epic_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1536");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1538");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1538"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1540");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1544"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1546");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1550"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1554");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1554"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1556");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1558");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1562");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1558"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_epic_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1560");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1562"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1564");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1568"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_epic_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1570");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1572");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1572"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1574");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1578"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1580");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1584"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1586");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1588");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1588"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1590");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1592");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1592"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1594");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1596");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1596"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1598");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1600");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1604");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1600"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1602");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1604"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_epic_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1606");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_kerritamba_epic_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1612"))
        {
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
            {
                ep3_forest_kerritamba_epic_action_addAssassins(player, npc);
                string_id message = new string_id(c_stringFile, "s_1614");
                utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
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
            detachScript(self, "conversation.ep3_forest_kerritamba_epic");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.ep3_forest_kerritamba_epic");
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
        if (ep3_forest_kerritamba_epic_condition_hasCompletedAll(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1520");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1522");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 1);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1526");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1528");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 3);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_hasCompletedTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1532");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1534");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 5);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_isTaskActiveTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1542");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1544");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 8);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1548");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1550");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 10);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_hasCompletedTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1566");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1568");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 15);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_isTaskActiveOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1576");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1578");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 18);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_hasCompletedInitial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1582");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1584");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 20);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_isGoodGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1608");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition_isBadGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1610");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1612");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId", 28);
                npcStartConversation(player, npc, "ep3_forest_kerritamba_epic", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_kerritamba_epic_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1616");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_forest_kerritamba_epic"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
        if (branchId == 1 && ep3_forest_kerritamba_epic_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_forest_kerritamba_epic_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_forest_kerritamba_epic_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_forest_kerritamba_epic_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_forest_kerritamba_epic_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_forest_kerritamba_epic_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_forest_kerritamba_epic_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_forest_kerritamba_epic_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_forest_kerritamba_epic_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_forest_kerritamba_epic_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_forest_kerritamba_epic_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_forest_kerritamba_epic_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_forest_kerritamba_epic_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_forest_kerritamba_epic_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_forest_kerritamba_epic_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_forest_kerritamba_epic_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_forest_kerritamba_epic_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_kerritamba_epic.branchId");
        return SCRIPT_CONTINUE;
    }
}
