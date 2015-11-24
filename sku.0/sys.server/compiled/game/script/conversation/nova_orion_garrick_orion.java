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
import script.library.conversation;
import script.library.groundquests;
import script.library.space_quest;
import script.library.township;
import script.library.utils;

public class nova_orion_garrick_orion extends script.base_script
{
    public nova_orion_garrick_orion()
    {
    }
    public static String c_stringFile = "conversation/nova_orion_garrick_orion";
    public boolean nova_orion_garrick_orion_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nova_orion_garrick_orion_condition_qualifiesForRank4Quests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "nova_orion_rank1_03") && hasCompletedCollectionSlot(player, "orion_rank_01_04");
    }
    public boolean nova_orion_garrick_orion_condition_returnRank4_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank4_orion_01", "no_rank4_orion_01_02") || groundquests.hasCompletedQuest(player, "no_rank4_orion_01");
    }
    public boolean nova_orion_garrick_orion_condition_returnRank4_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank4_orion_02", "no_rank4_orion_02_02") || groundquests.hasCompletedQuest(player, "no_rank4_orion_02");
    }
    public boolean nova_orion_garrick_orion_condition_returnRank4_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank4_orion_03", "no_rank4_orion_03_02") || groundquests.hasCompletedQuest(player, "no_rank4_orion_03");
    }
    public boolean nova_orion_garrick_orion_condition_hasKatiarasToy(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank2_04", "no_rank2_04_03b") && !groundquests.hasCompletedTask(player, "no_rank2_04", "no_rank2_04_03a");
    }
    public boolean nova_orion_garrick_orion_condition_onRank4_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank4_orion_01");
    }
    public boolean nova_orion_garrick_orion_condition_onRank4_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank4_orion_02");
    }
    public boolean nova_orion_garrick_orion_condition_onRank4_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank4_orion_03");
    }
    public boolean nova_orion_garrick_orion_condition_qualifiesForRank5Quests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "no_rank4_orion_03") && badge.hasBadge(player, "nova_finished_faction") && badge.hasBadge(player, "orion_finished_faction");
    }
    public boolean nova_orion_garrick_orion_condition_onRank5_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank5_01_orion");
    }
    public boolean nova_orion_garrick_orion_condition_onRank5_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank5_02_orion");
    }
    public boolean nova_orion_garrick_orion_condition_returnRank5_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank5_01_orion", "no_rank5_01_02") || groundquests.hasCompletedQuest(player, "no_rank5_01_orion");
    }
    public boolean nova_orion_garrick_orion_condition_returnRank5_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank5_02_orion", "no_rank5_02_02") || groundquests.hasCompletedQuest(player, "no_rank5_02_orion");
    }
    public boolean nova_orion_garrick_orion_condition_gaveKatiarasToy(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (!groundquests.isQuestActive(player, "orion_rank3_01") && hasCompletedCollectionSlot(player, "orion_rank_01_03"))
        {
            if (groundquests.isTaskActive(player, "no_rank2_04", "no_rank2_04_04b"))
            {
                result = true;
            }
            else if (groundquests.hasCompletedQuest(player, "no_rank2_04") && (getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION)).equals("orion"))
            {
                result = true;
            }
        }
        return result;
    }
    public boolean nova_orion_garrick_orion_condition_gaveKatiarasToyNoRank(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (!groundquests.isQuestActive(player, "orion_rank3_01") && !hasCompletedCollectionSlot(player, "orion_rank_01_03"))
        {
            if (groundquests.isTaskActive(player, "no_rank2_04", "no_rank2_04_04b"))
            {
                result = true;
            }
            else if (groundquests.hasCompletedQuest(player, "no_rank2_04") && (getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION)).equals("orion"))
            {
                result = true;
            }
        }
        return result;
    }
    public boolean nova_orion_garrick_orion_condition_preRank4GarrickBlurb(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "nova_rank3_01") && !hasCompletedCollectionSlot(player, "nova_rank_01_04");
    }
    public boolean nova_orion_garrick_orion_condition_onRank5Jonas_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank5_01");
    }
    public boolean nova_orion_garrick_orion_condition_onRank5Jonas_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank5_02");
    }
    public boolean nova_orion_garrick_orion_condition_completedRank5Jonas(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank5_02", "no_rank5_02_02") || groundquests.hasCompletedQuest(player, "no_rank5_02");
    }
    public boolean nova_orion_garrick_orion_condition_eligilbeForRank6Finale(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "no_rank6_katiara_toy") && !groundquests.isQuestActive(player, "no_rank6_katiara_finale") && !hasObjVar(player, township.NOVA_ORION_FINALE_COMPLETED);
    }
    public void nova_orion_garrick_orion_action_grantRank4_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank4_orion_01");
        space_quest.clearQuestFlags(player, "survival", "no_rank4_orion_01_01");
        space_quest.clearQuestFlags(player, "survival", "no_rank4_orion_01_02");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank4_orion_01_03");
        groundquests.grantQuest(player, "no_rank4_orion_01");
    }
    public void nova_orion_garrick_orion_action_grantRank4_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank4_orion_02");
        space_quest.clearQuestFlags(player, "recovery", "no_rank4_orion_02_01");
        space_quest.clearQuestFlags(player, "destroy_surpriseattack", "no_rank4_orion_02_02");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank4_orion_02_03");
        groundquests.grantQuest(player, "no_rank4_orion_02");
    }
    public void nova_orion_garrick_orion_action_grantRank4_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank4_orion_03");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank4_orion_03_01");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank4_orion_03_02");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank4_orion_03_03");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank4_orion_03_04");
        groundquests.grantQuest(player, "no_rank4_orion_03");
    }
    public void nova_orion_garrick_orion_action_sendRank4_03Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank4_orion_03_02");
        modifyCollectionSlotValue(player, "orion_rank_01_05", 1);
        if (!badge.hasBadge(player, "orion_finished_faction"))
        {
            badge.grantBadge(player, "orion_finished_faction");
        }
        return;
    }
    public void nova_orion_garrick_orion_action_sendRank4_02Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank4_orion_02_02");
    }
    public void nova_orion_garrick_orion_action_sendRank4_01Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank4_orion_01_02");
    }
    public void nova_orion_garrick_orion_action_grantRank5_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank5_01_orion");
        space_quest.clearQuestFlags(player, "patrol", "no_rank5_01_01");
        space_quest.clearQuestFlags(player, "destroy_surpriseattack", "no_rank5_01_02");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank5_01_03");
        space_quest.clearQuestFlags(player, "recover", "no_rank5_01_04");
        groundquests.grantQuest(player, "no_rank5_01_orion");
    }
    public void nova_orion_garrick_orion_action_grantRank5_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank5_02_orion");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank5_02_01");
        space_quest.clearQuestFlags(player, "space_battle", "no_rank5_02_02");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank5_02_03");
        groundquests.grantQuest(player, "no_rank5_02_orion");
    }
    public void nova_orion_garrick_orion_action_sendRank5_01Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank5_01_02");
    }
    public void nova_orion_garrick_orion_action_sendRank5_02Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank5_02_02");
        modifyCollectionSlotValue(player, "orion_rank_01_06", 1);
        if (!badge.hasBadge(player, "nova_orion_peace_keeper"))
        {
            badge.grantBadge(player, "nova_orion_peace_keeper");
        }
        return;
    }
    public void nova_orion_garrick_orion_action_sendRank2_04Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank2_04_04b");
        setObjVar(player, township.OBJVAR_NOVA_ORION_FACTION, "orion");
        if (!badge.hasBadge(player, "orion_joined_faction"))
        {
            badge.grantBadge(player, "orion_joined_faction");
        }
        return;
    }
    public void nova_orion_garrick_orion_action_grantRank6Finale(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActiveOrComplete(player, "no_rank6_katiara_toy"))
        {
            groundquests.grantQuest(player, "no_rank6_katiara_toy");
        }
        else if (!groundquests.isQuestActive(player, "no_rank6_katiara_toy") && !groundquests.isQuestActiveOrComplete(player, "no_rank6_katiara_finale"))
        {
            groundquests.grantQuest(player, "no_rank6_katiara_finale");
        }
        return;
    }
    public int nova_orion_garrick_orion_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_garrick_orion_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                nova_orion_garrick_orion_action_grantRank6Finale(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_garrick_orion_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                nova_orion_garrick_orion_action_grantRank5_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_garrick_orion_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                nova_orion_garrick_orion_action_grantRank5_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_garrick_orion_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                nova_orion_garrick_orion_action_grantRank4_03(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_garrick_orion_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                nova_orion_garrick_orion_action_grantRank4_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_garrick_orion_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
            {
                nova_orion_garrick_orion_action_grantRank4_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.nova_orion_garrick_orion");
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
        detachScript(self, "conversation.nova_orion_garrick_orion");
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
        if (nova_orion_garrick_orion_condition_returnRank5_02(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank5_02Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_garrick_orion_condition_eligilbeForRank6Finale(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 1);
                npcStartConversation(player, npc, "nova_orion_garrick_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank5_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_returnRank5_01(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank5_01Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 5);
                npcStartConversation(player, npc, "nova_orion_garrick_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank5_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_qualifiesForRank5Quests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 8);
                npcStartConversation(player, npc, "nova_orion_garrick_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_completedRank5Jonas(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_56");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank5Jonas_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_58");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank5Jonas_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_returnRank4_03(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank4_03Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank4_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_returnRank4_02(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank4_02Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_24");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 15);
                npcStartConversation(player, npc, "nova_orion_garrick_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank4_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_30");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_returnRank4_01(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank4_01Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_32");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 18);
                npcStartConversation(player, npc, "nova_orion_garrick_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_onRank4_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_qualifiesForRank4Quests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                utils.setScriptVar(player, "conversation.nova_orion_garrick_orion.branchId", 21);
                npcStartConversation(player, npc, "nova_orion_garrick_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_preRank4GarrickBlurb(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_gaveKatiarasToy(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank2_04Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_60");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_gaveKatiarasToyNoRank(player, npc))
        {
            nova_orion_garrick_orion_action_sendRank2_04Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_62");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition_hasKatiarasToy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_68");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_garrick_orion_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_70");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nova_orion_garrick_orion"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
        if (branchId == 1 && nova_orion_garrick_orion_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && nova_orion_garrick_orion_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && nova_orion_garrick_orion_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && nova_orion_garrick_orion_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && nova_orion_garrick_orion_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && nova_orion_garrick_orion_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && nova_orion_garrick_orion_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nova_orion_garrick_orion.branchId");
        return SCRIPT_CONTINUE;
    }
}
