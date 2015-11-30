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

public class npe_station_chewie extends script.base_script
{
    public npe_station_chewie()
    {
    }
    public static String c_stringFile = "conversation/npe_station_chewie";
    public boolean npe_station_chewie_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_station_chewie_condition_isWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        int pSpecies = getSpecies(player);
        if (pSpecies == SPECIES_WOOKIEE)
        {
            return true;
        }
        return false;
    }
    public boolean npe_station_chewie_condition_isWookieeFinishedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (npe_station_chewie_condition_isWookiee(player, npc) && groundquests.hasCompletedQuest(player, "npe_side_jolka"));
    }
    public void npe_station_chewie_action_playRoar(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(player, "clienteffect/npe_wookiee_voc_01.cef", npc, "");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.npe_station_chewie");
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
        detachScript(self, "conversation.npe_station_chewie");
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
        if (npe_station_chewie_condition_isWookieeFinishedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_chewie_condition_isWookiee(player, npc))
        {
            doAnimationAction(npc, "point_right");
            npe_station_chewie_action_playRoar(player, npc);
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_chewie_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "point_right");
            npe_station_chewie_action_playRoar(player, npc);
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_station_chewie"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_station_chewie.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_station_chewie.branchId");
        return SCRIPT_CONTINUE;
    }
}
