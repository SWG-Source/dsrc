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

public class mtp_hideout_access_lieutenant_kaiya extends script.base_script
{
    public mtp_hideout_access_lieutenant_kaiya()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_access_lieutenant_kaiya";
    public boolean mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_access_lieutenant_kaiya_condition_mtpHideout05_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mtp_hideout_access_05", "mtp_hideout_access_05_02") || groundquests.hasCompletedQuest(player, "mtp_hideout_access_05");
    }
    public boolean mtp_hideout_access_lieutenant_kaiya_condition_active_mtpHideout06(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mtp_hideout_access_06") || groundquests.isQuestActive(player, "mtp_hideout_access_high_06");
    }
    public boolean mtp_hideout_access_lieutenant_kaiya_condition_return_mtpHideout06(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mtp_hideout_access_06", "mtp_hideout_access_06_02") || groundquests.hasCompletedQuest(player, "mtp_hideout_access_06") || groundquests.isTaskActive(player, "mtp_hideout_access_high_06", "mtp_hideout_access_06_02") || groundquests.hasCompletedQuest(player, "mtp_hideout_access_high_06");
    }
    public boolean mtp_hideout_access_lieutenant_kaiya_condition_active_mtpHideout07(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mtp_hideout_access_07") || groundquests.isQuestActive(player, "mtp_hideout_access_high_07");
    }
    public boolean mtp_hideout_access_lieutenant_kaiya_condition_return_mtpHideout07(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mtp_hideout_access_07", "mtp_hideout_access_07_03") || groundquests.isTaskActive(player, "mtp_hideout_access_high_07", "mtp_hideout_access_07_03");
    }
    public boolean mtp_hideout_access_lieutenant_kaiya_condition_doneWithKaiya(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "mtp_hideout_access_07", "mtp_hideout_access_07_03") || groundquests.hasCompletedTask(player, "mtp_hideout_access_high_07", "mtp_hideout_access_07_03");
    }
    public void mtp_hideout_access_lieutenant_kaiya_action_signal_mtpHideout05_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_05_02");
    }
    public void mtp_hideout_access_lieutenant_kaiya_action_grant_mtpHideout06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getLevel(player) >= 82)
        {
            groundquests.grantQuest(player, "mtp_hideout_access_high_06");
        }
        else 
        {
            groundquests.grantQuest(player, "mtp_hideout_access_06");
        }
        return;
    }
    public void mtp_hideout_access_lieutenant_kaiya_action_signal_mtpHideout06(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_06_02");
    }
    public void mtp_hideout_access_lieutenant_kaiya_action_grant_mtpHideout07(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getLevel(player) >= 82)
        {
            groundquests.grantQuest(player, "mtp_hideout_access_high_07");
        }
        else 
        {
            groundquests.grantQuest(player, "mtp_hideout_access_07");
        }
        return;
    }
    public void mtp_hideout_access_lieutenant_kaiya_action_signal_mtpHideout07(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_07_03");
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bounce");
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumbs_up");
                mtp_hideout_access_lieutenant_kaiya_action_signal_mtpHideout07(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "catchbreath");
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                mtp_hideout_access_lieutenant_kaiya_action_signal_mtpHideout06(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                mtp_hideout_access_lieutenant_kaiya_action_grant_mtpHideout07(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "yawn");
                mtp_hideout_access_lieutenant_kaiya_action_signal_mtpHideout05_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "chopped_liver");
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_lieutenant_kaiya_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
            {
                mtp_hideout_access_lieutenant_kaiya_action_grant_mtpHideout06(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
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
            detachScript(self, "conversation.mtp_hideout_access_lieutenant_kaiya");
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
        detachScript(self, "conversation.mtp_hideout_access_lieutenant_kaiya");
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
        if (mtp_hideout_access_lieutenant_kaiya_condition_doneWithKaiya(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_lieutenant_kaiya_condition_return_mtpHideout07(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 2);
                npcStartConversation(player, npc, "mtp_hideout_access_lieutenant_kaiya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_lieutenant_kaiya_condition_active_mtpHideout07(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_lieutenant_kaiya_condition_return_mtpHideout06(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 6);
                npcStartConversation(player, npc, "mtp_hideout_access_lieutenant_kaiya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_lieutenant_kaiya_condition_active_mtpHideout06(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_lieutenant_kaiya_condition_mtpHideout05_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId", 11);
                npcStartConversation(player, npc, "mtp_hideout_access_lieutenant_kaiya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_lieutenant_kaiya_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_hideout_access_lieutenant_kaiya"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
        if (branchId == 2 && mtp_hideout_access_lieutenant_kaiya_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mtp_hideout_access_lieutenant_kaiya_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mtp_hideout_access_lieutenant_kaiya_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && mtp_hideout_access_lieutenant_kaiya_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mtp_hideout_access_lieutenant_kaiya_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && mtp_hideout_access_lieutenant_kaiya_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && mtp_hideout_access_lieutenant_kaiya_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && mtp_hideout_access_lieutenant_kaiya_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_access_lieutenant_kaiya.branchId");
        return SCRIPT_CONTINUE;
    }
}
