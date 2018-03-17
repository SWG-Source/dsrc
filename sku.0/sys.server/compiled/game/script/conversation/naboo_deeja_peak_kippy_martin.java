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
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class naboo_deeja_peak_kippy_martin extends script.base_script
{
    public naboo_deeja_peak_kippy_martin()
    {
    }
    public static String c_stringFile = "conversation/naboo_deeja_peak_kippy_martin";
    public boolean naboo_deeja_peak_kippy_martin_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_deeja_peak_kippy_martin_condition_speakToKippy_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_darkness", "deeja_peak_beacon_darkness_01");
    }
    public boolean naboo_deeja_peak_kippy_martin_condition_speakToKippy_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_darkness", "deeja_peak_beacon_darkness_03");
    }
    public boolean naboo_deeja_peak_kippy_martin_condition_killFlewts(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_darkness", "deeja_peak_beacon_darkness_02");
    }
    public boolean naboo_deeja_peak_kippy_martin_condition_doneWithKippy(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "naboo_deeja_peak_beacon_darkness", "deeja_peak_beacon_darkness_03");
    }
    public void naboo_deeja_peak_kippy_martin_action_signal_darkness_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_darkness_01");
    }
    public void naboo_deeja_peak_kippy_martin_action_signal_darkness_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_darkness_03");
    }
    public int naboo_deeja_peak_kippy_martin_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (naboo_deeja_peak_kippy_martin_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_kippy_martin_action_signal_darkness_03(player, npc);
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_kippy_martin.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_kippy_martin_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (naboo_deeja_peak_kippy_martin_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_kippy_martin_action_signal_darkness_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_kippy_martin.branchId");
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
            detachScript(self, "conversation.naboo_deeja_peak_kippy_martin");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.naboo_deeja_peak_kippy_martin");
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
        if (naboo_deeja_peak_kippy_martin_condition_doneWithKippy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_kippy_martin_condition_speakToKippy_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_kippy_martin_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_kippy_martin.branchId", 2);
                npcStartConversation(player, npc, "naboo_deeja_peak_kippy_martin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_kippy_martin_condition_killFlewts(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_kippy_martin_condition_speakToKippy_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_kippy_martin_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_kippy_martin.branchId", 5);
                npcStartConversation(player, npc, "naboo_deeja_peak_kippy_martin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_kippy_martin_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_deeja_peak_kippy_martin"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_deeja_peak_kippy_martin.branchId");
        if (branchId == 2 && naboo_deeja_peak_kippy_martin_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && naboo_deeja_peak_kippy_martin_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_deeja_peak_kippy_martin.branchId");
        return SCRIPT_CONTINUE;
    }
}
