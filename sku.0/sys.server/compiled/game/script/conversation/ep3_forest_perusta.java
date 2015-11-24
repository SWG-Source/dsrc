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

public class ep3_forest_perusta extends script.base_script
{
    public ep3_forest_perusta()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_perusta";
    public boolean ep3_forest_perusta_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_perusta_condition_isGoodGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_3") || groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7"));
    }
    public boolean ep3_forest_perusta_condition_isBadGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_2"));
    }
    public boolean ep3_forest_perusta_condition_isTaskOneActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_perusta_quest_1", 0) || groundquests.hasCompletedTask(player, "ep3_forest_perusta_quest_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_perusta_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_perusta_quest_1", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_perusta_quest_1"));
    }
    public boolean ep3_forest_perusta_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_perusta_quest_1");
    }
    public boolean ep3_forest_perusta_condition_isTaskActiveTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_forest_perusta_quest_2", 0) || groundquests.hasCompletedTask(player, "ep3_forest_perusta_quest_2", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_forest_perusta_condition_hasCompletedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_forest_perusta_quest_2", 0) && !groundquests.hasCompletedQuest(player, "ep3_forest_perusta_quest_2"));
    }
    public boolean ep3_forest_perusta_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_perusta_quest_2");
    }
    public boolean ep3_forest_perusta_condition_hasLanguage(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public void ep3_forest_perusta_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_perusta_quest_1");
    }
    public void ep3_forest_perusta_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "moss");
    }
    public void ep3_forest_perusta_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_perusta_quest_2");
    }
    public void ep3_forest_perusta_action_giveSignalTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mysess");
    }
    public void ep3_forest_perusta_action_Language(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_forest_perusta_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2770"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2772");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2776"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                ep3_forest_perusta_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_2778");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2782"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2784");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2788"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                ep3_forest_perusta_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_2790");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2792"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2794");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2798"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                ep3_forest_perusta_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_2800");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2804"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2806");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2808");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2808"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2810");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2814"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2816");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2818");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2818"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2820");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2822");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2822"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2824");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_perusta_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2826");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2830");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_perusta_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2826"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                ep3_forest_perusta_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_2828");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2830"))
        {
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2832");
                utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
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
            detachScript(self, "conversation.ep3_forest_perusta");
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
        detachScript(self, "conversation.ep3_forest_perusta");
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
        if (ep3_forest_perusta_condition_hasLanguage(player, npc))
        {
            ep3_forest_perusta_action_Language(player, npc);
            string_id message = new string_id(c_stringFile, "s_1676");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2768");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2770");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 2);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_hasCompletedTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2774");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2776");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 4);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_isTaskActiveTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2780");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2782");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 6);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2786");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2788");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2792");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 8);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_hasCompletedTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2796");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2798");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 11);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_isTaskOneActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2802");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2804");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 13);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_isGoodGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2812");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_perusta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2814");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_perusta.branchId", 16);
                npcStartConversation(player, npc, "ep3_forest_perusta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition_isBadGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2834");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_perusta_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2836");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_forest_perusta"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_perusta.branchId");
        if (branchId == 2 && ep3_forest_perusta_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_forest_perusta_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_forest_perusta_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_forest_perusta_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_forest_perusta_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_forest_perusta_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_forest_perusta_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_forest_perusta_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_forest_perusta_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_forest_perusta_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_forest_perusta_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_perusta.branchId");
        return SCRIPT_CONTINUE;
    }
}
