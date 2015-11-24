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
import script.library.space_battlefield;
import script.library.space_combat;
import script.library.space_flags;
import script.library.space_transition;
import script.library.utils;

public class battlefield_entry_station_neutral extends script.base_script
{
    public battlefield_entry_station_neutral()
    {
    }
    public static String c_stringFile = "conversation/battlefield_entry_station_neutral";
    public boolean battlefield_entry_station_neutral_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean battlefield_entry_station_neutral_condition_isPlayerRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_battlefield.isInRebelShip(player);
    }
    public boolean battlefield_entry_station_neutral_condition_isPlayerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_battlefield.isInImperialShip(player);
    }
    public boolean battlefield_entry_station_neutral_condition_canAffordEntry(obj_id player, obj_id npc) throws InterruptedException
    {
        int intCost = space_battlefield.canAffordPrestigePointCost(space_transition.getContainingShip(player), npc);
        if (intCost == 0)
        {
            return false;
        }
        return true;
    }
    public boolean battlefield_entry_station_neutral_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public boolean battlefield_entry_station_neutral_condition_isPlayerNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_battlefield.isInNeutralShip(player);
    }
    public boolean battlefield_entry_station_neutral_condition_isRebelFactionPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        int playerFactionID = pvpGetAlignedFaction(player);
        if (playerFactionID == (370444368) || space_flags.isRebelHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean battlefield_entry_station_neutral_condition_isImperialFactionPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        int playerFactionID = pvpGetAlignedFaction(player);
        if (playerFactionID == (-615855020) || space_flags.isImperialHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean battlefield_entry_station_neutral_condition_isInYacht(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(player);
        if (isIdValid(ship))
        {
            String template = getTemplateName(ship);
            if (template != null && template.endsWith("_yacht.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public void battlefield_entry_station_neutral_action_goToRebelBattlefield(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(player);
        setObjVar(ship, "spaceFaction.FactionOverride", (370444368));
        space_battlefield.doBattleFieldTransition(space_transition.getContainingShip(player), npc, "rebel_entry");
    }
    public void battlefield_entry_station_neutral_action_goToKessel(obj_id player, obj_id npc) throws InterruptedException
    {
        space_battlefield.doKesselTransition(space_transition.getContainingShip(player), npc);
    }
    public void battlefield_entry_station_neutral_action_goToImperialBattlefield(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(player);
        setObjVar(ship, "spaceFaction.FactionOverride", (-615855020));
        space_battlefield.doBattleFieldTransition(space_transition.getContainingShip(player), npc, "imperial_entry");
    }
    public int battlefield_entry_station_neutral_tokenDI_prestigeCost(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_battlefield.getPrestigeCostForTransition(player, npc);
    }
    public int battlefield_entry_station_neutral_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fa22e207"))
        {
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a99b5897");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43be1790");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e10b20e");
                    }
                    utils.setScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c4588d4d"))
        {
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                battlefield_entry_station_neutral_action_goToKessel(player, npc);
                string_id message = new string_id(c_stringFile, "s_f61543c9");
                utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int battlefield_entry_station_neutral_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43be1790"))
        {
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a785faa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!battlefield_entry_station_neutral_condition_isImperialFactionPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!battlefield_entry_station_neutral_condition_isRebelFactionPlayer(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d62f971d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5f09f8fe");
                    }
                    utils.setScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1e10b20e"))
        {
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11e15256");
                utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int battlefield_entry_station_neutral_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d62f971d"))
        {
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                battlefield_entry_station_neutral_action_goToRebelBattlefield(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5f09f8fe"))
        {
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                battlefield_entry_station_neutral_action_goToImperialBattlefield(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_hutt_02.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_hutt_02.iff");
        detachScript(self, "space.content_tools.spacestation");
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
        detachScript(self, "conversation.battlefield_entry_station_neutral");
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
        if (battlefield_entry_station_neutral_condition_isInYacht(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3693fe50");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (battlefield_entry_station_neutral_condition_isPlayerImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_993453d7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (battlefield_entry_station_neutral_condition_isPlayerRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45e440c8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (battlefield_entry_station_neutral_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3b257674");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_729b53ce");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (battlefield_entry_station_neutral_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fa22e207");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c4588d4d");
                }
                utils.setScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId", 5);
                npcStartConversation(player, npc, "battlefield_entry_station_neutral", message, responses);
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
        if (!conversationId.equals("battlefield_entry_station_neutral"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
        if (branchId == 5 && battlefield_entry_station_neutral_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && battlefield_entry_station_neutral_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && battlefield_entry_station_neutral_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.battlefield_entry_station_neutral.branchId");
        return SCRIPT_CONTINUE;
    }
}
