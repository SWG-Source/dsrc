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
import script.library.factions;
import script.library.utils;

public class gcw_imperial_buff_reward_npc extends script.base_script
{
    public gcw_imperial_buff_reward_npc()
    {
    }
    public static String c_stringFile = "conversation/gcw_imperial_buff_reward_npc";
    public boolean gcw_imperial_buff_reward_npc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean gcw_imperial_buff_reward_npc_condition_playerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public int gcw_imperial_buff_reward_npc_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            doAnimationAction(player, "nod");
            if (gcw_imperial_buff_reward_npc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.gcw_imperial_buff_reward_npc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (gcw_imperial_buff_reward_npc_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.gcw_imperial_buff_reward_npc.branchId");
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
            detachScript(self, "conversation.gcw_imperial_buff_reward_npc");
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
        detachScript(self, "conversation.gcw_imperial_buff_reward_npc");
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
        if (gcw_imperial_buff_reward_npc_condition_playerImperial(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (gcw_imperial_buff_reward_npc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (gcw_imperial_buff_reward_npc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                utils.setScriptVar(player, "conversation.gcw_imperial_buff_reward_npc.branchId", 1);
                npcStartConversation(player, npc, "gcw_imperial_buff_reward_npc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (gcw_imperial_buff_reward_npc_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("gcw_imperial_buff_reward_npc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.gcw_imperial_buff_reward_npc.branchId");
        if (branchId == 1 && gcw_imperial_buff_reward_npc_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.gcw_imperial_buff_reward_npc.branchId");
        return SCRIPT_CONTINUE;
    }
}
