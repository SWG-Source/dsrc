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

public class loveday_disillusion_blaire extends script.base_script
{
    public loveday_disillusion_blaire()
    {
    }
    public static String c_stringFile = "conversation/loveday_disillusion_blaire";
    public boolean loveday_disillusion_blaire_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean loveday_disillusion_blaire_condition_petLoverActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "loveday_disillusion_pet_lover_v2", "loveday_disillusion_pet_lover_02") || groundquests.isTaskActive(player, "loveday_disillusion_pet_lover_v2", "loveday_disillusion_pet_lover_03") || groundquests.isTaskActive(player, "loveday_disillusion_pet_lover_v2", "loveday_disillusion_pet_lover_complete");
    }
    public boolean loveday_disillusion_blaire_condition_questStart(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "loveday_disillusion_pet_lover_v2", "loveday_disillusion_pet_lover_01");
    }
    public boolean loveday_disillusion_blaire_condition_petLoverReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "loveday_disillusion_pet_lover_v2", "loveday_disillusion_pet_lover_complete"))
        {
            return true;
        }
        if (groundquests.hasCompletedQuest(player, "loveday_disillusion_pet_lover_v2"))
        {
            if (!groundquests.isQuestActive(player, "loveday_disillusion_love_note_v2"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean loveday_disillusion_blaire_condition_loveNoteActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "loveday_disillusion_love_note_v2");
    }
    public boolean loveday_disillusion_blaire_condition_loveNoteReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "loveday_disillusion_love_note_v2", "loveday_disillusion_love_note_complete"))
        {
            return true;
        }
        if (groundquests.hasCompletedQuest(player, "loveday_disillusion_love_note_v2"))
        {
            if (!groundquests.isQuestActive(player, "loveday_disillusion_mr_hate_v2") && !groundquests.isQuestActive(player, "loveday_disillusion_mr_hate_v2_noloot"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean loveday_disillusion_blaire_condition_mrHateActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "loveday_disillusion_mr_hate_v2") || groundquests.isQuestActive(player, "loveday_disillusion_mr_hate_v2_noloot");
    }
    public boolean loveday_disillusion_blaire_condition_mrHateReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2", "loveday_disillusion_mr_hate_complete") || groundquests.isTaskActive(player, "loveday_disillusion_mr_hate_v2_noloot", "loveday_disillusion_mr_hate_complete"))
        {
            return true;
        }
        return false;
    }
    public boolean loveday_disillusion_blaire_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "loveday_2010_disillusion_quest");
    }
    public boolean loveday_disillusion_blaire_condition_undisillusioned(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(npc, "unDisillusioned");
    }
    public void loveday_disillusion_blaire_action_grantLoveNote(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "loveday_disillusion_love_note_v2"))
        {
            groundquests.clearQuest(player, "loveday_disillusion_love_note_v2");
        }
        groundquests.requestGrantQuest(player, "loveday_disillusion_love_note_v2");
    }
    public void loveday_disillusion_blaire_action_grantMrHate(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "loveday_disillusion_mr_hate_v2"))
        {
            groundquests.requestGrantQuest(player, "loveday_disillusion_mr_hate_v2_noloot");
        }
        else 
        {
            groundquests.requestGrantQuest(player, "loveday_disillusion_mr_hate_v2");
        }
    }
    public void loveday_disillusion_blaire_action_signalPetLover(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "loveday_disillusion_pet_lover_complete");
    }
    public void loveday_disillusion_blaire_action_signalStart(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "loveday_disillusion_pet_lover_01");
    }
    public void loveday_disillusion_blaire_action_signalLoveNote(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "loveday_disillusion_love_note_complete");
    }
    public void loveday_disillusion_blaire_action_signalMrHate(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "loveday_disillusion_mr_hate_complete");
        if (!hasCompletedCollectionSlot(player, "loveday_2010_disillusion_quest"))
        {
            modifyCollectionSlotValue(player, "loveday_2010_disillusion_quest", 1);
        }
        location here = getLocation(npc);
        setObjectAppearance(npc, "object/mobile/shared_loveday_ewok_cupid.iff");
        setName(npc, "Benjamin (a Festival of Love Sage)");
        messageTo(npc, "handleResetDisillusionAppearance", null, 19, false);
        utils.setScriptVar(npc, "unDisillusioned", true);
    }
    public void loveday_disillusion_blaire_action_grantCollectionSlot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollectionSlot(player, "loveday_2010_disillusion_quest"))
        {
            modifyCollectionSlotValue(player, "loveday_2010_disillusion_quest", 1);
        }
        return;
    }
    public int loveday_disillusion_blaire_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_disillusion_blaire_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                loveday_disillusion_blaire_action_signalMrHate(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_disillusion_blaire_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_disillusion_blaire_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_disillusion_blaire_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                loveday_disillusion_blaire_action_grantMrHate(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_disillusion_blaire_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                loveday_disillusion_blaire_action_grantLoveNote(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_disillusion_blaire_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
            {
                loveday_disillusion_blaire_action_signalStart(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
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
            detachScript(self, "conversation.loveday_disillusion_blaire");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int handleResetDisillusionAppearance(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "unDisillusioned");
        setName(self, "Benjamin (the disillisioned Festival of Love Sage)");
        revertObjectAppearance(self);
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
        detachScript(self, "conversation.loveday_disillusion_blaire");
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
        if (loveday_disillusion_blaire_condition_questComplete(player, npc))
        {
            loveday_disillusion_blaire_action_grantCollectionSlot(player, npc);
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_undisillusioned(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_mrHateReturn(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 3);
                npcStartConversation(player, npc, "loveday_disillusion_blaire", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_mrHateActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_loveNoteReturn(player, npc))
        {
            loveday_disillusion_blaire_action_signalLoveNote(player, npc);
            string_id message = new string_id(c_stringFile, "s_21");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                }
                utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 7);
                npcStartConversation(player, npc, "loveday_disillusion_blaire", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_loveNoteActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_petLoverReturn(player, npc))
        {
            loveday_disillusion_blaire_action_signalPetLover(player, npc);
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 12);
                npcStartConversation(player, npc, "loveday_disillusion_blaire", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_petLoverActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition_questStart(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.loveday_disillusion_blaire.branchId", 15);
                npcStartConversation(player, npc, "loveday_disillusion_blaire", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_disillusion_blaire_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("loveday_disillusion_blaire"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
        if (branchId == 3 && loveday_disillusion_blaire_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && loveday_disillusion_blaire_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && loveday_disillusion_blaire_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && loveday_disillusion_blaire_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && loveday_disillusion_blaire_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && loveday_disillusion_blaire_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && loveday_disillusion_blaire_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_disillusion_blaire.branchId");
        return SCRIPT_CONTINUE;
    }
}
