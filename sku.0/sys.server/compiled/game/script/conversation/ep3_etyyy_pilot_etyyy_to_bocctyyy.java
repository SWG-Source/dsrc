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
import script.library.group;
import script.library.space_dungeon;
import script.library.utils;

public class ep3_etyyy_pilot_etyyy_to_bocctyyy extends script.base_script
{
    public ep3_etyyy_pilot_etyyy_to_bocctyyy()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_pilot_etyyy_to_bocctyyy";
    public boolean ep3_etyyy_pilot_etyyy_to_bocctyyy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_canGoToBocctyyy(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet")) || (groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet")) || (groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet")) || (groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet")))
        {
            obj_id[] tickets = space_dungeon.findValidDungeonTickets(player, npc);
            if (tickets != null && tickets.length > 0)
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_hasBocctyyyTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] tickets = space_dungeon.findValidDungeonTickets(player, npc);
        if (tickets != null && tickets.length > 0)
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_isFemaleCharacter(obj_id player, obj_id npc) throws InterruptedException
    {
        return getGender(player) == GENDER_FEMALE;
    }
    public boolean ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_tooManyInGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            if (isIdValid(groupObj))
            {
                int numGroupMembers = getGroupSize(groupObj);
                if (numGroupMembers > 6)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_isInGodeMode(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public void ep3_etyyy_pilot_etyyy_to_bocctyyy_action_sendToBocctyyy(obj_id player, obj_id npc) throws InterruptedException
    {
        space_dungeon.selectDungeonTicket(npc, player);
        return;
    }
    public void ep3_etyyy_pilot_etyyy_to_bocctyyy_action_godModeEntry(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isGod(player))
        {
            String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
            obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_THE_BET, ticketTemplate);
            if (isIdValid(ticket))
            {
                setObjVar(ticket, "space_dungeon.ticket.quest_type", "none");
                setObjVar(ticket, "kashyyyk.ticket_owner", player);
                space_dungeon.selectDungeonTicket(npc, player);
            }
        }
        return;
    }
    public int ep3_etyyy_pilot_etyyy_to_bocctyyy_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_690"))
        {
            if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_tooManyInGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_692");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_isFemaleCharacter(player, npc))
            {
                ep3_etyyy_pilot_etyyy_to_bocctyyy_action_sendToBocctyyy(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_pilot_etyyy_to_bocctyyy_action_sendToBocctyyy(player, npc);
                string_id message = new string_id(c_stringFile, "s_694");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_696"))
        {
            if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_698");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy");
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
        if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_canGoToBocctyyy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_688");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_690");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_696");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_pilot_etyyy_to_bocctyyy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition_isFemaleCharacter(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_700");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_pilot_etyyy_to_bocctyyy_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_706");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_pilot_etyyy_to_bocctyyy"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId");
        if (branchId == 1 && ep3_etyyy_pilot_etyyy_to_bocctyyy_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_pilot_etyyy_to_bocctyyy.branchId");
        return SCRIPT_CONTINUE;
    }
}
