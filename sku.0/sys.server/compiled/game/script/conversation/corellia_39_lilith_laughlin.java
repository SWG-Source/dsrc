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

public class corellia_39_lilith_laughlin extends script.base_script
{
    public corellia_39_lilith_laughlin()
    {
    }
    public static String c_stringFile = "conversation/corellia_39_lilith_laughlin";
    public boolean corellia_39_lilith_laughlin_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_39_lilith_laughlin_condition_seekCouncilOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_01", "chirq_council_01_01");
    }
    public boolean corellia_39_lilith_laughlin_condition_seekCouncilOneProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_39_chirq_council_01", "chirq_council_01_01");
    }
    public boolean corellia_39_lilith_laughlin_condition_sekCouncilTwoProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_39_chirq_council_01", "chirq_council_01_02");
    }
    public boolean corellia_39_lilith_laughlin_condition_seekCouncilThreeProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_39_chirq_council_01", "chirq_council_01_04");
    }
    public boolean corellia_39_lilith_laughlin_condition_seekCouncilComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_01");
    }
    public boolean corellia_39_lilith_laughlin_condition_movingUp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_03", "chirq_council_03_05");
    }
    public boolean corellia_39_lilith_laughlin_condition_movingUpProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_39_chirq_council_03", "chirq_council_03_05");
    }
    public boolean corellia_39_lilith_laughlin_condition_movingUpComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_39_chirq_council_03b", "chirq_council_03_09");
    }
    public void corellia_39_lilith_laughlin_action_signal_seekCouncilOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_01_01");
    }
    public void corellia_39_lilith_laughlin_action_signal_movingUp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_03_05");
    }
    public int corellia_39_lilith_laughlin_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (corellia_39_lilith_laughlin_condition__defaultCondition(player, npc))
            {
                corellia_39_lilith_laughlin_action_signal_movingUp(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.corellia_39_lilith_laughlin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_lilith_laughlin_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (corellia_39_lilith_laughlin_condition__defaultCondition(player, npc))
            {
                corellia_39_lilith_laughlin_action_signal_seekCouncilOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.corellia_39_lilith_laughlin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.corellia_39_lilith_laughlin");
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
        detachScript(self, "conversation.corellia_39_lilith_laughlin");
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
        if (corellia_39_lilith_laughlin_condition_movingUpComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_movingUpProgress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_movingUp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_lilith_laughlin_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_39_lilith_laughlin.branchId", 3);
                npcStartConversation(player, npc, "corellia_39_lilith_laughlin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_seekCouncilComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_seekCouncilThreeProgress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_sekCouncilTwoProgress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_seekCouncilOneProgress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition_seekCouncilOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_lilith_laughlin_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_39_lilith_laughlin.branchId", 9);
                npcStartConversation(player, npc, "corellia_39_lilith_laughlin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_lilith_laughlin_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("corellia_39_lilith_laughlin"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_39_lilith_laughlin.branchId");
        if (branchId == 3 && corellia_39_lilith_laughlin_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corellia_39_lilith_laughlin_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_39_lilith_laughlin.branchId");
        return SCRIPT_CONTINUE;
    }
}
