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

public class mtp_meatlumps_supplies_giver extends script.base_script
{
    public mtp_meatlumps_supplies_giver()
    {
    }
    public static String c_stringFile = "conversation/mtp_meatlumps_supplies_giver";
    public boolean mtp_meatlumps_supplies_giver_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_meatlumps_supplies_giver_condition_alreadyHasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mtp_hideout_instance_recover_supplies");
    }
    public boolean mtp_meatlumps_supplies_giver_condition_returningQuestSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mtp_hideout_instance_recover_supplies_success");
    }
    public boolean mtp_meatlumps_supplies_giver_condition_notYetReadyForAnother(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "mtp_hideout_instance_recover_supplies_success") && hasObjVar(player, "mtp_hideout.eligibleRecoverSupplies"))
        {
            int eligibleForNextQuestAt = getIntObjVar(player, "mtp_hideout.eligibleRecoverSupplies");
            if (getCalendarTime() < eligibleForNextQuestAt)
            {
                return true;
            }
        }
        return false;
    }
    public boolean mtp_meatlumps_supplies_giver_condition_returningQuestFailure(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mtp_hideout_instance_recover_supplies_fail");
    }
    public void mtp_meatlumps_supplies_giver_action_grantSuppliesQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "mtp_hideout_instance_recover_supplies");
        groundquests.clearQuest(player, "mtp_hideout_instance_recover_supplies_fail");
        groundquests.clearQuest(player, "mtp_hideout_instance_recover_supplies_success");
        groundquests.grantQuest(player, "mtp_hideout_instance_recover_supplies");
        if (hasObjVar(player, "mtp_hideout.eligibleRecoverSupplies"))
        {
            removeObjVar(player, "mtp_hideout.eligibleRecoverSupplies");
        }
        return;
    }
    public void mtp_meatlumps_supplies_giver_action_sendSuppliesSuccessSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_recover_supplies_success");
        int now = getCalendarTime();
        int secondsUntil = secondsUntilNextDailyTime(4, 0, 0);
        int then = now + secondsUntil;
        setObjVar(player, "mtp_hideout.eligibleRecoverSupplies", then);
    }
    public void mtp_meatlumps_supplies_giver_action_sendSuppliesFailureSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mtp_recover_supplies_failed");
    }
    public int mtp_meatlumps_supplies_giver_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (mtp_meatlumps_supplies_giver_condition__defaultCondition(player, npc))
            {
                mtp_meatlumps_supplies_giver_action_grantSuppliesQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.mtp_meatlumps_supplies_giver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_17"))
        {
            if (mtp_meatlumps_supplies_giver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.mtp_meatlumps_supplies_giver.branchId");
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
            detachScript(self, "conversation.mtp_meatlumps_supplies_giver");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        clearCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.mtp_meatlumps_supplies_giver");
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
        if (mtp_meatlumps_supplies_giver_condition_returningQuestSuccess(player, npc))
        {
            mtp_meatlumps_supplies_giver_action_sendSuppliesSuccessSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlumps_supplies_giver_condition_returningQuestFailure(player, npc))
        {
            mtp_meatlumps_supplies_giver_action_sendSuppliesFailureSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_19");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlumps_supplies_giver_condition_alreadyHasQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlumps_supplies_giver_condition_notYetReadyForAnother(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlumps_supplies_giver_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_meatlumps_supplies_giver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mtp_meatlumps_supplies_giver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                utils.setScriptVar(player, "conversation.mtp_meatlumps_supplies_giver.branchId", 5);
                npcStartConversation(player, npc, "mtp_meatlumps_supplies_giver", message, responses);
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
        if (!conversationId.equals("mtp_meatlumps_supplies_giver"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_meatlumps_supplies_giver.branchId");
        if (branchId == 5 && mtp_meatlumps_supplies_giver_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_meatlumps_supplies_giver.branchId");
        return SCRIPT_CONTINUE;
    }
}
