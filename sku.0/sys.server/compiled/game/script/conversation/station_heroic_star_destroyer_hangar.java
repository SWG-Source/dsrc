package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.collection;
import script.library.conversation;
import script.library.instance;
import script.library.space_combat;
import script.library.space_transition;
import script.library.space_utils;
import script.library.township;
import script.library.utils;

public class station_heroic_star_destroyer_hangar extends script.base_script
{
    public station_heroic_star_destroyer_hangar()
    {
    }
    public static String c_stringFile = "conversation/station_heroic_star_destroyer_hangar";
    public boolean station_heroic_star_destroyer_hangar_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean station_heroic_star_destroyer_hangar_condition_tooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        location stationLoc = getLocation(npc);
        location playerLoc = getLocation(space_transition.getContainingShip(player));
        return (getDistance(stationLoc, playerLoc) > township.STAR_DESTROYER_COMM_DISTANCE);
    }
    public void station_heroic_star_destroyer_hangar_action_moveToSD(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(player);
        if (!isIdValid(ship))
        {
            return;
        }
        obj_id pilot = space_utils.getPilotForRealsies(ship);
        obj_id[] players = space_utils.getAllPlayersInShip(ship);
        boolean isSmuggler = utils.isProfession(pilot, utils.SMUGGLER);
        if (players == null || players.length == 0)
        {
            return;
        }
        boolean inYacht = false;
        String strChassisType = getShipChassisType(ship);
        if ((strChassisType != null) && strChassisType.equals("player_sorosuub_space_yacht"))
        {
            inYacht = true;
        }
        if (!inYacht && players.length > 3)
        {
            if (isSmuggler)
            {
                buff.applyBuff(players, "sm_smuggled");
            }
            else 
            {
                buff.applyBuff(players, "sm_smuggled_01");
            }
        }
        for (int q = 0; q < players.length; q++)
        {
            collection.pilotSmuggleTimeCheck(pilot, players[q], ship, strChassisType);
        }
        boolean[] groupResults = instance.requestInstancePobGroup(players, "heroic_star_destroyer");
    }
    public int station_heroic_star_destroyer_hangar_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_457a7010"))
        {
            if (station_heroic_star_destroyer_hangar_condition__defaultCondition(player, npc))
            {
                station_heroic_star_destroyer_hangar_action_moveToSD(player, npc);
                string_id message = new string_id(c_stringFile, "s_b780e818");
                utils.removeScriptVar(player, "conversation.station_heroic_star_destroyer_hangar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (station_heroic_star_destroyer_hangar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.station_heroic_star_destroyer_hangar.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "space.content_tools.spacestation");
        setName(self, "Blackguard Hangar Bay");
        setObjVar(self, "intInvincible", 1);
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
        detachScript(self, "conversation.station_heroic_star_destroyer_hangar");
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
        if (station_heroic_star_destroyer_hangar_condition_tooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_heroic_star_destroyer_hangar_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e7fb4e63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_heroic_star_destroyer_hangar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_heroic_star_destroyer_hangar_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_457a7010");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.station_heroic_star_destroyer_hangar.branchId", 2);
                npcStartConversation(player, npc, "station_heroic_star_destroyer_hangar", message, responses);
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
        if (!conversationId.equals("station_heroic_star_destroyer_hangar"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.station_heroic_star_destroyer_hangar.branchId");
        if (branchId == 2 && station_heroic_star_destroyer_hangar_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.station_heroic_star_destroyer_hangar.branchId");
        return SCRIPT_CONTINUE;
    }
}
