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

public class emp_day_reb_disk_guy extends script.base_script
{
    public emp_day_reb_disk_guy()
    {
    }
    public static String c_stringFile = "conversation/emp_day_reb_disk_guy";
    public boolean emp_day_reb_disk_guy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_reb_disk_guy_condition_hasTask3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_rebel");
        int task3 = groundquests.getTaskId(questId, "diskGuy1");
        return questIsTaskActive(questId, task3, player);
    }
    public void emp_day_reb_disk_guy_action_triggerAndWp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "disk_guy_1");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_reb_disk"));
    }
    public int emp_day_reb_disk_guy_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_250"))
        {
            if (emp_day_reb_disk_guy_condition__defaultCondition(player, npc))
            {
                emp_day_reb_disk_guy_action_triggerAndWp(player, npc);
                string_id message = new string_id(c_stringFile, "s_251");
                utils.removeScriptVar(player, "conversation.emp_day_reb_disk_guy.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_252"))
        {
            if (emp_day_reb_disk_guy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_253");
                utils.removeScriptVar(player, "conversation.emp_day_reb_disk_guy.branchId");
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
            detachScript(self, "conversation.emp_day_reb_disk_guy");
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
        detachScript(self, "conversation.emp_day_reb_disk_guy");
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
        if (emp_day_reb_disk_guy_condition_hasTask3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_248");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_reb_disk_guy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (emp_day_reb_disk_guy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                }
                utils.setScriptVar(player, "conversation.emp_day_reb_disk_guy.branchId", 1);
                npcStartConversation(player, npc, "emp_day_reb_disk_guy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_disk_guy_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_249");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("emp_day_reb_disk_guy"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_reb_disk_guy.branchId");
        if (branchId == 1 && emp_day_reb_disk_guy_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_reb_disk_guy.branchId");
        return SCRIPT_CONTINUE;
    }
}
