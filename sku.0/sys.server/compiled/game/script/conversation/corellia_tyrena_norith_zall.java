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

public class corellia_tyrena_norith_zall extends script.base_script
{
    public corellia_tyrena_norith_zall()
    {
    }
    public static String c_stringFile = "conversation/corellia_tyrena_norith_zall";
    public boolean corellia_tyrena_norith_zall_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_tyrena_norith_zall_condition_completeForgotten(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_tyrena_forgotten_child");
    }
    public boolean corellia_tyrena_norith_zall_condition_completeMissing(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_tyrena_missing_persons");
    }
    public boolean corellia_tyrena_norith_zall_condition_onForgotten(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "forgotten_child_talk_gordo") || groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "forgotten_child_slice_admin_term") || groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "get_info_from_guards") || groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "forgotten_return_to_gordo") || groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "forgotten_get_location") || groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "kill_neekas_guards") || groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "forgotten_talk_neeka"));
    }
    public boolean corellia_tyrena_norith_zall_condition_onForgottenReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_tyrena_forgotten_child", "forgotten_return_zorgom");
    }
    public int corellia_tyrena_norith_zall_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (corellia_tyrena_norith_zall_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11");
                utils.removeScriptVar(player, "conversation.corellia_tyrena_norith_zall.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_tyrena_norith_zall_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            doAnimationAction(player, "shrug_hands");
            if (corellia_tyrena_norith_zall_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.corellia_tyrena_norith_zall.branchId");
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
            detachScript(self, "conversation.corellia_tyrena_norith_zall");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.corellia_tyrena_norith_zall");
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
        if (corellia_tyrena_norith_zall_condition_completeForgotten(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_tyrena_norith_zall_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                utils.setScriptVar(player, "conversation.corellia_tyrena_norith_zall.branchId", 1);
                npcStartConversation(player, npc, "corellia_tyrena_norith_zall", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_tyrena_norith_zall_condition_onForgotten(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_tyrena_norith_zall_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_tyrena_norith_zall.branchId", 3);
                npcStartConversation(player, npc, "corellia_tyrena_norith_zall", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_tyrena_norith_zall_condition_completeMissing(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_16");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_tyrena_norith_zall_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_17");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_tyrena_norith_zall"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_tyrena_norith_zall.branchId");
        if (branchId == 1 && corellia_tyrena_norith_zall_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corellia_tyrena_norith_zall_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_tyrena_norith_zall.branchId");
        return SCRIPT_CONTINUE;
    }
}
