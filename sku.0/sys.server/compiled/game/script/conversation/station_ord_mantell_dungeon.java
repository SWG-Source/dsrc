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
import script.library.npe;
import script.library.space_combat;
import script.library.space_crafting;
import script.library.space_transition;
import script.library.utils;

public class station_ord_mantell_dungeon extends script.base_script
{
    public station_ord_mantell_dungeon()
    {
    }
    public static String c_stringFile = "conversation/station_ord_mantell_dungeon";
    public boolean station_ord_mantell_dungeon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean station_ord_mantell_dungeon_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        location stationLoc = getLocation(npc);
        location playerLoc = getLocation(space_transition.getContainingShip(player));
        return (getDistance(stationLoc, playerLoc) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public boolean station_ord_mantell_dungeon_condition_canAfford100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, 1.0f);
    }
    public boolean station_ord_mantell_dungeon_condition_canAfford25(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_crafting.canAffordShipRepairs(player, npc, .25f) && space_crafting.isDamaged(player));
    }
    public boolean station_ord_mantell_dungeon_condition_needsRepairs(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.isDamaged(player);
    }
    public void station_ord_mantell_dungeon_action_land(obj_id player, obj_id npc) throws InterruptedException
    {
        location stationLoc = getLocation(npc);
        location playerLoc = getLocation(space_transition.getContainingShip(player));
        float fltDistance = getDistance(stationLoc, playerLoc);
        if (fltDistance > 350.0f)
        {
            string_id strSpam = new string_id("space/space_interaction", "too_far");
            sendSystemMessage(player, strSpam);
        }
        else 
        {
            npe.movePlayerFromOrdMantellSpaceToOrdMantellDungeon(player);
            groundquests.sendSignal(player, "leaving_station");
        }
    }
    public void station_ord_mantell_dungeon_action_fix100(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, 1.0f);
    }
    public void station_ord_mantell_dungeon_action_fix25(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .25f);
    }
    public int station_ord_mantell_dungeon_tokenDI_get100FixCost(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, 1.0f);
    }
    public int station_ord_mantell_dungeon_tokenDI_get25FixCost(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .25f);
    }
    public int station_ord_mantell_dungeon_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                station_ord_mantell_dungeon_action_land(player, npc);
                string_id message = new string_id(c_stringFile, "s_10");
                utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_13"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_ord_mantell_dungeon_condition_canAfford100(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_ord_mantell_dungeon_condition_canAfford25(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_ord_mantell_dungeon_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_ord_mantell_dungeon_tokenDI_get100FixCost(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_ord_mantell_dungeon_tokenDI_get100FixCost(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    utils.setScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId", 8);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_ord_mantell_dungeon_tokenDI_get25FixCost(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_ord_mantell_dungeon_tokenDI_get25FixCost(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_ord_mantell_dungeon_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            station_ord_mantell_dungeon_action_fix100(player, npc);
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_ord_mantell_dungeon_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            station_ord_mantell_dungeon_action_fix25(player, npc);
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
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
            detachScript(self, "conversation.station_ord_mantell_dungeon");
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
        detachScript(self, "conversation.station_ord_mantell_dungeon");
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
        if (station_ord_mantell_dungeon_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_ord_mantell_dungeon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_ord_mantell_dungeon_condition_needsRepairs(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                utils.setScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId", 2);
                npcStartConversation(player, npc, "station_ord_mantell_dungeon", message, responses);
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
        if (!conversationId.equals("station_ord_mantell_dungeon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
        if (branchId == 2 && station_ord_mantell_dungeon_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && station_ord_mantell_dungeon_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && station_ord_mantell_dungeon_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && station_ord_mantell_dungeon_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.station_ord_mantell_dungeon.branchId");
        return SCRIPT_CONTINUE;
    }
}
