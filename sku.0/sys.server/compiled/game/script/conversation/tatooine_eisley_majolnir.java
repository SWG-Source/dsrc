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
import script.library.content;
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class tatooine_eisley_majolnir extends script.base_script
{
    public tatooine_eisley_majolnir()
    {
    }
    public static String c_stringFile = "conversation/tatooine_eisley_majolnir";
    public boolean tatooine_eisley_majolnir_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_eisley_majolnir_condition_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return true;
    }
    public boolean tatooine_eisley_majolnir_condition_readyForKit(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (groundquests.isTaskActive(player, "quest/find_majolnir", "talkToPall"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_majolnir_condition_doneWithMe(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (groundquests.hasCompletedQuest(player, "quest/find_majolnir"))
        {
            return true;
        }
        return false;
    }
    public void tatooine_eisley_majolnir_action_sendKitSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveCustomizationKit");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.tatooine_eisley_majolnir");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.tatooine_eisley_majolnir");
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
        if (tatooine_eisley_majolnir_condition_doneWithMe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_majolnir_condition_readyForKit(player, npc))
        {
            tatooine_eisley_majolnir_action_sendKitSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_majolnir_condition_facePlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_65");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tatooine_eisley_majolnir"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_eisley_majolnir.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_eisley_majolnir.branchId");
        return SCRIPT_CONTINUE;
    }
}
