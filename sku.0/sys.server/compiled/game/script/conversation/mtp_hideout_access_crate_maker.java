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

public class mtp_hideout_access_crate_maker extends script.base_script
{
    public mtp_hideout_access_crate_maker()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_access_crate_maker";
    public boolean mtp_hideout_access_crate_maker_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_access_crate_maker_condition_mtpHideout01_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mtp_hideout_access_01", "mtp_hideout_access_01_01") || groundquests.isTaskActive(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_01");
    }
    public boolean mtp_hideout_access_crate_maker_condition_mtpHideout01_02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "mtp_hideout_access_01", "mtp_hideout_access_01_01") && groundquests.isTaskActive(player, "mtp_hideout_access_01", "mtp_hideout_access_01_02"))
        {
            return true;
        }
        if (groundquests.hasCompletedTask(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_01") && groundquests.isTaskActive(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_02"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_access_crate_maker_condition_mtpHideout01_04(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "mtp_hideout_access_01", "mtp_hideout_access_01_02") && !groundquests.hasCompletedTask(player, "mtp_hideout_access_01", "mtp_hideout_access_01_04"))
        {
            return true;
        }
        if (groundquests.hasCompletedTask(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_02") && !groundquests.hasCompletedTask(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_04"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_access_crate_maker_condition_mtpHideout01_06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "mtp_hideout_access_01", "mtp_hideout_access_01_04") && !groundquests.hasCompletedTask(player, "mtp_hideout_access_01", "mtp_hideout_access_01_06"))
        {
            return true;
        }
        if (groundquests.hasCompletedTask(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_04") && !groundquests.hasCompletedTask(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_06"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_access_crate_maker_condition_mtpHideout01_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "mtp_hideout_access_01", "mtp_hideout_access_01_07") && !groundquests.hasCompletedQuest(player, "mtp_hideout_access_01"))
        {
            return true;
        }
        if (groundquests.isTaskActive(player, "mtp_hideout_access_high_01", "mtp_hideout_access_01_07") && !groundquests.hasCompletedQuest(player, "mtp_hideout_access_high_01"))
        {
            return true;
        }
        return false;
    }
    public void mtp_hideout_access_crate_maker_action_signal_mtpHideout01_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_01_01");
    }
    public int mtp_hideout_access_crate_maker_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_crate_maker_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_crate_maker_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
            {
                mtp_hideout_access_crate_maker_action_signal_mtpHideout01_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId");
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
            detachScript(self, "conversation.mtp_hideout_access_crate_maker");
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
        detachScript(self, "conversation.mtp_hideout_access_crate_maker");
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
        if (mtp_hideout_access_crate_maker_condition_mtpHideout01_complete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_crate_maker_condition_mtpHideout01_06(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_crate_maker_condition_mtpHideout01_04(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_crate_maker_condition_mtpHideout01_02(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_crate_maker_condition_mtpHideout01_01(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId", 5);
                npcStartConversation(player, npc, "mtp_hideout_access_crate_maker", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_crate_maker_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_hideout_access_crate_maker"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId");
        if (branchId == 5 && mtp_hideout_access_crate_maker_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mtp_hideout_access_crate_maker_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && mtp_hideout_access_crate_maker_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_access_crate_maker.branchId");
        return SCRIPT_CONTINUE;
    }
}
