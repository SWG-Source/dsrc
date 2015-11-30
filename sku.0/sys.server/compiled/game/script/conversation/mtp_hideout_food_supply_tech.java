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
import script.library.utils;

public class mtp_hideout_food_supply_tech extends script.base_script
{
    public mtp_hideout_food_supply_tech()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_food_supply_tech";
    public boolean mtp_hideout_food_supply_tech_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_food_supply_tech_condition_hasDefeatedRagtag(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/mtp_hideout_ragtag", "goToClerk");
    }
    public boolean mtp_hideout_food_supply_tech_condition_questNotActiveCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.isQuestActiveOrComplete(player, "quest/mtp_camp_quest_tatooine")) && (!groundquests.isQuestActiveOrComplete(player, "quest/mtp_hideout_ragtag"));
    }
    public boolean mtp_hideout_food_supply_tech_condition_hasCompletedRagtagNoCoords(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "quest/mtp_camp_quest_tatooine") && groundquests.hasCompletedQuest(player, "quest/mtp_hideout_ragtag");
    }
    public boolean mtp_hideout_food_supply_tech_condition_hasCompletedRagTagHasCoords(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "quest/mtp_camp_quest_tatooine") && groundquests.hasCompletedQuest(player, "quest/mtp_hideout_ragtag");
    }
    public boolean mtp_hideout_food_supply_tech_condition_hasFailQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "quest/mtp_hideout_ragtag_fail");
    }
    public void mtp_hideout_food_supply_tech_action_giveTatooineCoords(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/mtp_camp_quest_tatooine");
    }
    public void mtp_hideout_food_supply_tech_action_giveRagtagQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/mtp_hideout_ragtag");
    }
    public void mtp_hideout_food_supply_tech_action_sendClerkSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "spokeToClerk");
    }
    public void mtp_hideout_food_supply_tech_action_removeFailRegrantQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "quest/mtp_hideout_ragtag_fail");
        groundquests.grantQuest(player, "quest/mtp_hideout_ragtag");
    }
    public int mtp_hideout_food_supply_tech_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_food_supply_tech_action_removeFailRegrantQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_food_supply_tech_action_giveRagtagQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_food_supply_tech_action_giveTatooineCoords(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_food_supply_tech_action_sendClerkSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_food_supply_tech_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
            {
                mtp_hideout_food_supply_tech_action_giveTatooineCoords(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
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
            detachScript(self, "conversation.mtp_hideout_food_supply_tech");
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
        detachScript(self, "conversation.mtp_hideout_food_supply_tech");
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
        if (mtp_hideout_food_supply_tech_condition_hasFailQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_69");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 1);
                npcStartConversation(player, npc, "mtp_hideout_food_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_food_supply_tech_condition_questNotActiveCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 3);
                npcStartConversation(player, npc, "mtp_hideout_food_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_food_supply_tech_condition_hasCompletedRagtagNoCoords(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 15);
                npcStartConversation(player, npc, "mtp_hideout_food_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_food_supply_tech_condition_hasDefeatedRagtag(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId", 17);
                npcStartConversation(player, npc, "mtp_hideout_food_supply_tech", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_food_supply_tech_condition_hasCompletedRagTagHasCoords(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_food_supply_tech_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_74");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_hideout_food_supply_tech"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
        if (branchId == 1 && mtp_hideout_food_supply_tech_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mtp_hideout_food_supply_tech_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mtp_hideout_food_supply_tech_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && mtp_hideout_food_supply_tech_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mtp_hideout_food_supply_tech_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mtp_hideout_food_supply_tech_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && mtp_hideout_food_supply_tech_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && mtp_hideout_food_supply_tech_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && mtp_hideout_food_supply_tech_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && mtp_hideout_food_supply_tech_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && mtp_hideout_food_supply_tech_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_food_supply_tech.branchId");
        return SCRIPT_CONTINUE;
    }
}
