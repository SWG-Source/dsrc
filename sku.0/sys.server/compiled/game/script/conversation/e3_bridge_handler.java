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
import script.library.space_transition;
import script.library.utils;

public class e3_bridge_handler extends script.base_script
{
    public e3_bridge_handler()
    {
    }
    public static String c_stringFile = "conversation/e3_bridge_handler";
    public boolean e3_bridge_handler_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void e3_bridge_handler_action_goOutside(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(player);
        transform trTest = getTransformObjVar(objShip, "trLaunchLocation");
        space_transition.exitCapitalShip(player, trTest);
    }
    public int e3_bridge_handler_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9"))
        {
            doAnimationAction(player, "point_to_self");
            if (e3_bridge_handler_condition__defaultCondition(player, npc))
            {
                e3_bridge_handler_action_goOutside(player, npc);
                string_id message = new string_id(c_stringFile, "s_11");
                utils.removeScriptVar(player, "conversation.e3_bridge_handler.branchId");
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
            detachScript(self, "conversation.e3_bridge_handler");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Captain Harmond");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        if (!hasScript(self, "e3demo.e3_faceto"))
        {
            attachScript(self, "e3demo.e3_faceto");
        }
        factions.setFaction(self, "Imperial");
        setName(self, "Captain Harmond");
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
        detachScript(self, "conversation.e3_bridge_handler");
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
        if (e3_bridge_handler_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "salute1");
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (e3_bridge_handler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                }
                utils.setScriptVar(player, "conversation.e3_bridge_handler.branchId", 1);
                npcStartConversation(player, npc, "e3_bridge_handler", message, responses);
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
        if (!conversationId.equals("e3_bridge_handler"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.e3_bridge_handler.branchId");
        if (branchId == 1 && e3_bridge_handler_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.e3_bridge_handler.branchId");
        return SCRIPT_CONTINUE;
    }
}
