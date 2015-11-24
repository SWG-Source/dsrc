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
import script.library.utils;

public class corellia_coronet_pol_handerin extends script.base_script
{
    public corellia_coronet_pol_handerin()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_pol_handerin";
    public boolean corellia_coronet_pol_handerin_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_pol_handerin_condition_farmAidQuestBegin(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_1");
    }
    public boolean corellia_coronet_pol_handerin_condition_carrionSpatsAllDefeated(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_3");
    }
    public boolean corellia_coronet_pol_handerin_condition_carrionSpatsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_2a") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_2b");
    }
    public boolean corellia_coronet_pol_handerin_condition_gravefeatherActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_2a") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_2b");
    }
    public boolean corellia_coronet_pol_handerin_condition_spatsAndGravefeatherActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_2a") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_2b");
    }
    public boolean corellia_coronet_pol_handerin_condition_allThreeBanditTasks(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_grainAndTrae(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_TraeAndBandits(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_grainAndBandits(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_justGrain(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_justTrae(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_justBandits(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4a") && groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4b") && groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_4c");
    }
    public boolean corellia_coronet_pol_handerin_condition_allBanditTasksComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_farm_aid", "farm_aid_5");
    }
    public void corellia_coronet_pol_handerin_action_sendAfterSpats(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "farm_aid_1");
    }
    public void corellia_coronet_pol_handerin_action_sendAfterBandits(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "farm_aid_3");
    }
    public void corellia_coronet_pol_handerin_action_endFarmAid(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "farm_aid_5");
    }
    public int corellia_coronet_pol_handerin_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                corellia_coronet_pol_handerin_action_endFarmAid(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_pol_handerin_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                corellia_coronet_pol_handerin_action_sendAfterBandits(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_pol_handerin_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                corellia_coronet_pol_handerin_action_sendAfterSpats(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
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
            detachScript(self, "conversation.corellia_coronet_pol_handerin");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.corellia_coronet_pol_handerin");
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
        if (corellia_coronet_pol_handerin_condition_allBanditTasksComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_17");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId", 1);
                npcStartConversation(player, npc, "corellia_coronet_pol_handerin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_justBandits(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_justTrae(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_19");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_justGrain(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_TraeAndBandits(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_grainAndBandits(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_grainAndTrae(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_allThreeBanditTasks(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_carrionSpatsAllDefeated(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId", 10);
                npcStartConversation(player, npc, "corellia_coronet_pol_handerin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_carrionSpatsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_gravefeatherActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_spatsAndGravefeatherActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition_farmAidQuestBegin(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_40");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId", 16);
                npcStartConversation(player, npc, "corellia_coronet_pol_handerin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_pol_handerin_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("corellia_coronet_pol_handerin"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
        if (branchId == 1 && corellia_coronet_pol_handerin_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_coronet_pol_handerin_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && corellia_coronet_pol_handerin_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_pol_handerin.branchId");
        return SCRIPT_CONTINUE;
    }
}
