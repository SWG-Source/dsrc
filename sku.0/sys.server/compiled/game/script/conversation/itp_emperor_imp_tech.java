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

public class itp_emperor_imp_tech extends script.base_script
{
    public itp_emperor_imp_tech()
    {
    }
    public static String c_stringFile = "conversation/itp_emperor_imp_tech";
    public boolean itp_emperor_imp_tech_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean itp_emperor_imp_tech_condition_emperorTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "itp_emperor_01", "itp_emperor_01_03");
    }
    public void itp_emperor_imp_tech_action_emperorSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "itp_emperor_01_03");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.itp_emperor_imp_tech");
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
        detachScript(self, "conversation.itp_emperor_imp_tech");
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
        if (itp_emperor_imp_tech_condition_emperorTaskActive(player, npc))
        {
            itp_emperor_imp_tech_action_emperorSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_imp_tech_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("itp_emperor_imp_tech"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.itp_emperor_imp_tech.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.itp_emperor_imp_tech.branchId");
        return SCRIPT_CONTINUE;
    }
}
