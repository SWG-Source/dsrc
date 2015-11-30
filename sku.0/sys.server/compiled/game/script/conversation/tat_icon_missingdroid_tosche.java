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

public class tat_icon_missingdroid_tosche extends script.base_script
{
    public tat_icon_missingdroid_tosche()
    {
    }
    public static String c_stringFile = "conversation/tat_icon_missingdroid_tosche";
    public boolean tat_icon_missingdroid_tosche_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tat_icon_missingdroid_tosche_condition_fixingJawa_e5_lyrissa2Tosche(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_lyrissa_to_tosche_e5 = groundquests.getTaskId(questId1, "tat_icon_lyrissa_to_tosche_e5");
        boolean onTask = questIsTaskActive(questId1, tat_icon_lyrissa_to_tosche_e5, player);
        return onTask;
    }
    public boolean tat_icon_missingdroid_tosche_condition_fixingJawa_e6_tosche2Lyrissa(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_tosche_to_lyrissa_e6 = groundquests.getTaskId(questId1, "tat_icon_tosche_to_lyrissa_e6");
        boolean onTask = questIsTaskActive(questId1, tat_icon_tosche_to_lyrissa_e6, player);
        return onTask;
    }
    public void tat_icon_missingdroid_tosche_action_signal_gotoLyrissa_e5(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_icon_fixingjawa_lyrissa_to_tosche_e5");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.tat_icon_missingdroid_tosche");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Speeder Parts Dealer");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Speeder Parts Dealer");
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
        detachScript(self, "conversation.tat_icon_missingdroid_tosche");
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
        if (tat_icon_missingdroid_tosche_condition_fixingJawa_e6_tosche2Lyrissa(player, npc))
        {
            doAnimationAction(npc, "point_up");
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tat_icon_missingdroid_tosche_condition_fixingJawa_e5_lyrissa2Tosche(player, npc))
        {
            tat_icon_missingdroid_tosche_action_signal_gotoLyrissa_e5(player, npc);
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tat_icon_missingdroid_tosche_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "point_up");
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tat_icon_missingdroid_tosche"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tat_icon_missingdroid_tosche.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_tosche.branchId");
        return SCRIPT_CONTINUE;
    }
}
