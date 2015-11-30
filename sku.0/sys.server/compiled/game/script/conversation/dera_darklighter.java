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

public class dera_darklighter extends script.base_script
{
    public dera_darklighter()
    {
    }
    public static String c_stringFile = "conversation/dera_darklighter";
    public boolean dera_darklighter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean dera_darklighter_condition_startWithDera(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "huff_darklighter_dera", "speakToDera");
    }
    public boolean dera_darklighter_condition_workingWithDera(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "huff_darklighter_dera", "eliminateVileTuskens");
    }
    public boolean dera_darklighter_condition_returningToDera(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "huff_darklighter_dera", "returnToDera");
    }
    public boolean dera_darklighter_condition_doneWithDera(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "huff_darklighter_dera", "returnToDera");
    }
    public void dera_darklighter_action_signal_startDera(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "speakToDera");
    }
    public void dera_darklighter_action_signal_endDera(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnToDera");
    }
    public int dera_darklighter_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (dera_darklighter_condition__defaultCondition(player, npc))
            {
                dera_darklighter_action_signal_startDera(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.dera_darklighter.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (dera_darklighter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.dera_darklighter.branchId");
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
            detachScript(self, "conversation.dera_darklighter");
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
        detachScript(self, "conversation.dera_darklighter");
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
        if (dera_darklighter_condition_doneWithDera(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (dera_darklighter_condition_returningToDera(player, npc))
        {
            dera_darklighter_action_signal_endDera(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (dera_darklighter_condition_workingWithDera(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (dera_darklighter_condition_startWithDera(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (dera_darklighter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (dera_darklighter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.dera_darklighter.branchId", 4);
                npcStartConversation(player, npc, "dera_darklighter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (dera_darklighter_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("dera_darklighter"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.dera_darklighter.branchId");
        if (branchId == 4 && dera_darklighter_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.dera_darklighter.branchId");
        return SCRIPT_CONTINUE;
    }
}
