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
import script.library.space_battlefield;
import script.library.space_quest;
import script.library.space_transition;

public class pvp_station_rebel extends script.base_script
{
    public pvp_station_rebel()
    {
    }
    public static String c_stringFile = "conversation/pvp_station_rebel";
    public boolean pvp_station_rebel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean pvp_station_rebel_condition_isPlayerRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_battlefield.isInRebelShip(player);
    }
    public boolean pvp_station_rebel_condition_isPlayerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_battlefield.isInImperialShip(player) || pvpGetAlignedFaction(player) == (-615855020));
    }
    public boolean pvp_station_rebel_condition_isRebelFactionWithMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_battlefield.isInNeutralShip(player))
        {
            return ((pvpGetAlignedFaction(player) == (370444368)) && space_quest.hasQuest(player));
        }
        else 
        {
            return false;
        }
    }
    public boolean pvp_station_rebel_condition_isInYacht(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean pvp_station_rebel_condition_isPlayerNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_battlefield.isInNeutralShip(player))
        {
            if (pvpGetAlignedFaction(player) == (370444368))
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
    public boolean pvp_station_rebel_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public void pvp_station_rebel_action_setPlayerToOvertStatus(obj_id player, obj_id npc) throws InterruptedException
    {
        space_transition.setPlayerOvert(player, (370444368));
    }
    public int pvp_station_rebel_tokenDI_prestigeCost(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_battlefield.getPrestigeCostForTransition(player, npc);
    }
    public int pvp_station_rebel_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32da2cf3"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1ef0f7f");
                removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int pvp_station_rebel_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370822d1"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                pvp_station_rebel_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_62e7ee");
                removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_457a7010"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b780e818");
                removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dd67013"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d2cd41a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (pvp_station_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_626fe576");
                    }
                    setObjVar(player, "conversation.pvp_station_rebel.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int pvp_station_rebel_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_626fe576"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_392e22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (pvp_station_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc5b544f");
                    }
                    setObjVar(player, "conversation.pvp_station_rebel.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int pvp_station_rebel_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dc5b544f"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e4046084");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (pvp_station_rebel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (pvp_station_rebel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (pvp_station_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_370822d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_457a7010");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd67013");
                    }
                    setObjVar(player, "conversation.pvp_station_rebel.branchId", 7);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int pvp_station_rebel_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370822d1"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                pvp_station_rebel_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_62e7ee");
                removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_457a7010"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b780e818");
                removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dd67013"))
        {
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d2cd41a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (pvp_station_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_626fe576");
                    }
                    setObjVar(player, "conversation.pvp_station_rebel.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.pvp_station_rebel.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_rebel_transport_01.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_rebel_transport_01.iff");
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
        detachScript(self, "conversation.pvp_station_rebel");
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
        if (pvp_station_rebel_condition_isInYacht(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_358253b2");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (pvp_station_rebel_condition_isPlayerImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_514ace33");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (pvp_station_rebel_condition_isRebelFactionWithMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_223d8c5f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32da2cf3");
                }
                setObjVar(player, "conversation.pvp_station_rebel.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "pvp_station_rebel", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (pvp_station_rebel_condition_isPlayerNeutral(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_17456755");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (pvp_station_rebel_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48f82131");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (pvp_station_rebel_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e7fb4e63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (pvp_station_rebel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_370822d1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_457a7010");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dd67013");
                }
                setObjVar(player, "conversation.pvp_station_rebel.branchId", 7);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "pvp_station_rebel", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("pvp_station_rebel"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.pvp_station_rebel.branchId");
        if (branchId == 3 && pvp_station_rebel_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && pvp_station_rebel_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && pvp_station_rebel_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && pvp_station_rebel_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && pvp_station_rebel_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.pvp_station_rebel.branchId");
        return SCRIPT_CONTINUE;
    }
}
