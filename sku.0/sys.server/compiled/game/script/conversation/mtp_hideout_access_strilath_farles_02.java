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

public class mtp_hideout_access_strilath_farles_02 extends script.base_script
{
    public mtp_hideout_access_strilath_farles_02()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_access_strilath_farles_02";
    public boolean mtp_hideout_access_strilath_farles_02_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_access_strilath_farles_02_condition_mtpHideout04_03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "mtp_hideout_access_04", "mtp_hideout_access_04_03") || groundquests.isTaskActive(player, "mtp_hideout_access_high_04", "mtp_hideout_access_04_03"))
        {
            obj_id myPlayer = utils.getObjIdScriptVar(npc, "waveEventPlayer");
            if (isIdValid(myPlayer) && myPlayer == player)
            {
                return true;
            }
        }
        return false;
    }
    public boolean mtp_hideout_access_strilath_farles_02_condition_active_mtpHideout04(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "mtp_hideout_access_04", "mtp_hideout_access_04_03") || groundquests.hasCompletedTask(player, "mtp_hideout_access_high_04", "mtp_hideout_access_04_03");
    }
    public boolean mtp_hideout_access_strilath_farles_02_condition_needs_mtpHideout04(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((groundquests.hasCompletedQuest(player, "mtp_hideout_access_03") && !groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_04")) || (groundquests.hasCompletedQuest(player, "mtp_hideout_access_high_03") && !groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_high_04")));
    }
    public void mtp_hideout_access_strilath_farles_02_action_signal_mtpHideout03_04(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_04_03");
    }
    public void mtp_hideout_access_strilath_farles_02_action_fakeSmoke(obj_id player, obj_id npc) throws InterruptedException
    {
        location here = getLocation(npc);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "appearance/pt_smoke_puff_noloop.prt", here, 0.0f);
    }
    public void mtp_hideout_access_strilath_farles_02_action_actualSmoke(obj_id player, obj_id npc) throws InterruptedException
    {
        messageTo(npc, "makeNpcDisappear", null, 1, false);
    }
    public void mtp_hideout_access_strilath_farles_02_action_regrant_mtpHideout04(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_04_pointer");
        if (getLevel(player) >= 82)
        {
            if (!groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_high_04"))
            {
                groundquests.grantQuest(player, "mtp_hideout_access_high_04");
            }
        }
        else 
        {
            if (!groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_04"))
            {
                groundquests.grantQuest(player, "mtp_hideout_access_04");
            }
        }
        messageTo(npc, "makeNpcDisappear", null, rand(5, 9), false);
        return;
    }
    public int mtp_hideout_access_strilath_farles_02_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                mtp_hideout_access_strilath_farles_02_action_signal_mtpHideout03_04(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_strilath_farles_02_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9"))
        {
            doAnimationAction(player, "threaten");
            if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
            {
                mtp_hideout_access_strilath_farles_02_action_fakeSmoke(player, npc);
                string_id message = new string_id(c_stringFile, "s_11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_strilath_farles_02_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_strilath_farles_02_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
            {
                mtp_hideout_access_strilath_farles_02_action_actualSmoke(player, npc);
                string_id message = new string_id(c_stringFile, "s_19");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
            {
                mtp_hideout_access_strilath_farles_02_action_actualSmoke(player, npc);
                string_id message = new string_id(c_stringFile, "s_23");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
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
            detachScript(self, "conversation.mtp_hideout_access_strilath_farles_02");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        messageTo(self, "makeNpcDisappear", null, rand(130, 150), false);
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
        detachScript(self, "conversation.mtp_hideout_access_strilath_farles_02");
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
        if (mtp_hideout_access_strilath_farles_02_condition_active_mtpHideout04(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_strilath_farles_02_condition_mtpHideout04_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId", 2);
                npcStartConversation(player, npc, "mtp_hideout_access_strilath_farles_02", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_strilath_farles_02_condition_needs_mtpHideout04(player, npc))
        {
            mtp_hideout_access_strilath_farles_02_action_regrant_mtpHideout04(player, npc);
            string_id message = new string_id(c_stringFile, "s_25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_strilath_farles_02_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_hideout_access_strilath_farles_02"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
        if (branchId == 2 && mtp_hideout_access_strilath_farles_02_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mtp_hideout_access_strilath_farles_02_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mtp_hideout_access_strilath_farles_02_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && mtp_hideout_access_strilath_farles_02_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_access_strilath_farles_02.branchId");
        return SCRIPT_CONTINUE;
    }
}
