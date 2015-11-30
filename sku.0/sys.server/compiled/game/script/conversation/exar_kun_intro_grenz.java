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
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class exar_kun_intro_grenz extends script.base_script
{
    public exar_kun_intro_grenz()
    {
    }
    public static String c_stringFile = "conversation/exar_kun_intro_grenz";
    public boolean exar_kun_intro_grenz_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean exar_kun_intro_grenz_condition_exar_kun_01_07_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "exar_kun_intro_01", "exar_kun_intro_01_07");
    }
    public boolean exar_kun_intro_grenz_condition_exar_kun_01_08_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "exar_kun_intro_01", "exar_kun_intro_01_08");
    }
    public boolean exar_kun_intro_grenz_condition_wave_event_active(obj_id player, obj_id npc) throws InterruptedException
    {
        int wave = utils.getIntScriptVar(npc, "waveEventCurrentWave");
        return wave > 0;
    }
    public void exar_kun_intro_grenz_action_exar_kun_01_wave(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary webster = new dictionary();
        webster.put("player", player);
        messageTo(npc, "waveEventControllerNPCStart", webster, 0, false);
    }
    public void exar_kun_intro_grenz_action_exar_kun_01_08(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "exar_kun_intro_01_08");
        setPosture(npc, POSTURE_INCAPACITATED);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.exar_kun_intro_grenz");
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
        detachScript(self, "conversation.exar_kun_intro_grenz");
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
        if (exar_kun_intro_grenz_condition_exar_kun_01_08_active(player, npc))
        {
            exar_kun_intro_grenz_action_exar_kun_01_08(player, npc);
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (exar_kun_intro_grenz_condition_wave_event_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (exar_kun_intro_grenz_condition_exar_kun_01_07_active(player, npc))
        {
            exar_kun_intro_grenz_action_exar_kun_01_wave(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (exar_kun_intro_grenz_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("exar_kun_intro_grenz"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.exar_kun_intro_grenz.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.exar_kun_intro_grenz.branchId");
        return SCRIPT_CONTINUE;
    }
}
