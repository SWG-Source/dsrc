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
import script.library.static_item;
import script.library.utils;

public class mtp_hideout_weapon_supply_tech extends script.base_script
{
    public mtp_hideout_weapon_supply_tech()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_weapon_supply_tech";
    public boolean mtp_hideout_weapon_supply_tech_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_questNotActiveComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!mtp_hideout_weapon_supply_tech_condition_hasCompletedOrActiveNabooCamp(player, npc) && !mtp_hideout_weapon_supply_tech_condition_hasCompletedOrActiveRetrievalQuest(player, npc));
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_hasCompletedOrActiveNabooCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActiveOrComplete(player, "quest/mtp_camp_quest_naboo"));
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_hasCompletedOrActiveRetrievalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "quest/mtp_hideout_retrieve_delivery");
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_retrieveQuestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "quest/mtp_hideout_retrieve_delivery");
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_retrieveTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "quest/mtp_hideout_retrieve_delivery", "speakSmuggler");
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_waitingForArmorerSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/mtp_hideout_retrieve_delivery", "seeArmorer");
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_playerDelieveredPackageAndReturned(obj_id player, obj_id npc) throws InterruptedException
    {
        return (mtp_hideout_weapon_supply_tech_condition_retrieveQuestActive(player, npc) && mtp_hideout_weapon_supply_tech_condition_retrieveTaskComplete(player, npc) && mtp_hideout_weapon_supply_tech_condition_waitingForArmorerSignal(player, npc));
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_playerWaitingForNabooCoords(obj_id player, obj_id npc) throws InterruptedException
    {
        return (mtp_hideout_weapon_supply_tech_condition_hasCompletedRetrievalQuest(player, npc) && !mtp_hideout_weapon_supply_tech_condition_hasCompletedOrActiveNabooCamp(player, npc));
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_hasCompletedRetrievalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest/mtp_hideout_retrieve_delivery");
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_hasCompletedNabooCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "quest/mtp_camp_quest_naboo"));
    }
    public boolean mtp_hideout_weapon_supply_tech_condition_hasComlinkNotActiveCompleteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasStaticItemInBankOrInventory(player, "item_mtp_hideout_quest_comlink") && mtp_hideout_weapon_supply_tech_condition_questNotActiveComplete(player, npc);
    }
    public void mtp_hideout_weapon_supply_tech_action_giveQuestComlink(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        static_item.createNewItemFunction("item_mtp_hideout_quest_comlink", inv);
        groundquests.grantQuest(player, "quest/mtp_hideout_retrieve_delivery");
    }
    public void mtp_hideout_weapon_supply_tech_action_sendCompletionSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "spokenToArmorer");
    }
    public void mtp_hideout_weapon_supply_tech_action_giveNabooCoordsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/mtp_camp_quest_naboo");
    }
    public void mtp_hideout_weapon_supply_tech_action_deleteComlink(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id comlink = utils.getStaticItemInBankOrInventory(player, "item_mtp_hideout_quest_comlink");
        detachScript(comlink, "item.special.nodestroy");
        destroyObject(comlink);
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_weapon_supply_tech_action_giveQuestComlink(player, npc);
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cuckoo");
                doAnimationAction(player, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_weapon_supply_tech_action_sendCompletionSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_weapon_supply_tech_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_weapon_supply_tech_action_giveNabooCoordsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
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
            detachScript(self, "conversation.mtp_hideout_weapon_supply_tech");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        detachScript(self, "conversation.mtp_hideout_weapon_supply_tech");
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
        if (mtp_hideout_weapon_supply_tech_condition_hasComlinkNotActiveCompleteQuest(player, npc))
        {
            mtp_hideout_weapon_supply_tech_action_deleteComlink(player, npc);
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_weapon_supply_tech_condition_questNotActiveComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 2);
                npcStartConversation(player, npc, "mtp_hideout_weapon_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_weapon_supply_tech_condition_playerDelieveredPackageAndReturned(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 7);
                npcStartConversation(player, npc, "mtp_hideout_weapon_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_weapon_supply_tech_condition_playerWaitingForNabooCoords(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId", 12);
                npcStartConversation(player, npc, "mtp_hideout_weapon_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_weapon_supply_tech_condition_hasCompletedNabooCamp(player, npc))
        {
            doAnimationAction(npc, "scared");
            doAnimationAction(player, "shakefist");
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_weapon_supply_tech_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_hideout_weapon_supply_tech"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
        if (branchId == 2 && mtp_hideout_weapon_supply_tech_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mtp_hideout_weapon_supply_tech_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mtp_hideout_weapon_supply_tech_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && mtp_hideout_weapon_supply_tech_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mtp_hideout_weapon_supply_tech_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && mtp_hideout_weapon_supply_tech_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && mtp_hideout_weapon_supply_tech_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && mtp_hideout_weapon_supply_tech_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_weapon_supply_tech.branchId");
        return SCRIPT_CONTINUE;
    }
}
