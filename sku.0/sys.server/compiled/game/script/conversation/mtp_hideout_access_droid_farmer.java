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

public class mtp_hideout_access_droid_farmer extends script.base_script
{
    public mtp_hideout_access_droid_farmer()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_access_droid_farmer";
    public boolean mtp_hideout_access_droid_farmer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_access_droid_farmer_condition_mtpHideout02_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mtp_hideout_access_02", "mtp_hideout_access_02_01");
    }
    public boolean mtp_hideout_access_droid_farmer_condition_mtpHideout02_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "mtp_hideout_access_02", "mtp_hideout_access_02_03") || groundquests.hasCompletedTask(player, "mtp_hideout_access_02", "mtp_hideout_access_02_03");
    }
    public void mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_02_01");
    }
    public void mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_hideout_access_02_03");
    }
    public int mtp_hideout_access_droid_farmer_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_droid_farmer_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_03(player, npc);
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_03(player, npc);
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_03(player, npc);
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_03(player, npc);
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_droid_farmer_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_droid_farmer_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_access_droid_farmer_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                mtp_hideout_access_droid_farmer_action_signal_mtpHideout02_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
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
            detachScript(self, "conversation.mtp_hideout_access_droid_farmer");
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
        detachScript(self, "conversation.mtp_hideout_access_droid_farmer");
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
        if (mtp_hideout_access_droid_farmer_condition_mtpHideout02_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId", 1);
                npcStartConversation(player, npc, "mtp_hideout_access_droid_farmer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_droid_farmer_condition_mtpHideout02_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId", 7);
                npcStartConversation(player, npc, "mtp_hideout_access_droid_farmer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_hideout_access_droid_farmer_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("mtp_hideout_access_droid_farmer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
        if (branchId == 1 && mtp_hideout_access_droid_farmer_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && mtp_hideout_access_droid_farmer_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && mtp_hideout_access_droid_farmer_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mtp_hideout_access_droid_farmer_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && mtp_hideout_access_droid_farmer_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_access_droid_farmer.branchId");
        return SCRIPT_CONTINUE;
    }
}
