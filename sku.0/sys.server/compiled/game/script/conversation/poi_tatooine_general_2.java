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
import script.library.factions;
import script.library.space_combat;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.space_transition;
import script.library.utils;

public class poi_tatooine_general_2 extends script.base_script
{
    public poi_tatooine_general_2()
    {
    }
    public static String c_stringFile = "conversation/poi_tatooine_general_2";
    public boolean poi_tatooine_general_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean poi_tatooine_general_2_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "escort", "poi_tatooine_general2_1") || space_quest.hasAbortedQuestRecursive(player, "escort", "poi_tatooine_general2_1"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_2_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "destroy", "poi_tatooine_general2_2") || space_quest.hasAbortedQuestRecursive(player, "destroy", "poi_tatooine_general2_2"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_2_condition_failedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "recovery", "poi_tatooine_general2_3") || space_quest.hasAbortedQuestRecursive(player, "recovery", "poi_tatooine_general2_3"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_2_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "escort", "poi_tatooine_general2_1"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_2_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "destroy", "poi_tatooine_general2_2"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_2_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "recovery", "poi_tatooine_general2_3"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_2_condition_collectingQuestOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "escort", "poi_tatooine_general2_1") && !space_quest.hasReceivedReward(player, "escort", "poi_tatooine_general2_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_general_2_condition_collectingQuestTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "destroy", "poi_tatooine_general2_2") && !space_quest.hasReceivedReward(player, "destroy", "poi_tatooine_general2_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_general_2_condition_collectingQuestThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "recovery", "poi_tatooine_general2_3") && !space_quest.hasReceivedReward(player, "recovery", "poi_tatooine_general2_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_general_2_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean poi_tatooine_general_2_condition_isNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, "jabba") < 0.0f);
    }
    public boolean poi_tatooine_general_2_condition_hasAnyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean poi_tatooine_general_2_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public void poi_tatooine_general_2_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "poi_tatooine_general2_1");
    }
    public void poi_tatooine_general_2_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "poi_tatooine_general2_2");
    }
    public void poi_tatooine_general_2_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "poi_tatooine_general2_3");
    }
    public void poi_tatooine_general_2_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "poi_tatooine_general2_1"))
        {
            space_quest.giveReward(player, "escort", "poi_tatooine_general2_1", 7000);
        }
    }
    public void poi_tatooine_general_2_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "poi_tatooine_general2_2"))
        {
            space_quest.giveReward(player, "destroy", "poi_tatooine_general2_2", 15000);
        }
    }
    public void poi_tatooine_general_2_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "poi_tatooine_general2_3"))
        {
            space_quest.giveReward(player, "recovery", "poi_tatooine_general2_3", 25000);
        }
    }
    public int poi_tatooine_general_2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14b13184"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_2d25ead");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d215c307"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_70fb2f96");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5517628d"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_2d737038");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6613e8a2"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_c5afcca4");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6fc5551a"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_c6f02543");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e9aeb150"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_b755a869");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44e3e0d3"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fa21b24b");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5680cda2"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2961542d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b034d9ae");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d73b3f1");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b034d9ae"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_692ea4e6");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d73b3f1"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f54925e");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3988ce81"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40f49d32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87d3a522");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a18139bb");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87d3a522"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f0016326");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ec4258cd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c914ce3");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a18139bb"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61657d0f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ec4258cd"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_8f164fcb");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1c914ce3"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2c9b283");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2613d24b"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f9fe110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a401c759");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a401c759"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f6936e2a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c27d920f");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c27d920f"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53ed657a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a99397a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31881ef");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_2_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a99397a"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_2_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_62d05d14");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31881ef"))
        {
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56b87409");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.poi_tatooine_general_2");
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
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.poi_tatooine_general_2");
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
        if (poi_tatooine_general_2_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_630c1e1a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_hasAnyQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c4a3c855");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_failedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_255d3625");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14b13184");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 3);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_failedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_102ed493");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d215c307");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 5);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_failedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9f495275");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5517628d");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 7);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_collectingQuestOneReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_bb5aa311");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6613e8a2");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 9);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_collectingQuestThreeReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d55f7726");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6fc5551a");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 11);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_collectingQuestTwoReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_544993c3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e9aeb150");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 13);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3bb73ee3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44e3e0d3");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 15);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1a834cf5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5680cda2");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 17);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d70603e1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3988ce81");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 21);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_75040efd");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2613d24b");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_2.branchId", 27);
                npcStartConversation(player, npc, "poi_tatooine_general_2", message, responses);
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
        if (!conversationId.equals("poi_tatooine_general_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
        if (branchId == 3 && poi_tatooine_general_2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && poi_tatooine_general_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && poi_tatooine_general_2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && poi_tatooine_general_2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && poi_tatooine_general_2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && poi_tatooine_general_2_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && poi_tatooine_general_2_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && poi_tatooine_general_2_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && poi_tatooine_general_2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && poi_tatooine_general_2_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && poi_tatooine_general_2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && poi_tatooine_general_2_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && poi_tatooine_general_2_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && poi_tatooine_general_2_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && poi_tatooine_general_2_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && poi_tatooine_general_2_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.poi_tatooine_general_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
