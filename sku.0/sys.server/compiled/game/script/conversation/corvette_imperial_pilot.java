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
import script.library.factions;
import script.library.groundquests;
import script.library.group;
import script.library.space_dungeon;
import script.library.utils;

public class corvette_imperial_pilot extends script.base_script
{
    public corvette_imperial_pilot()
    {
    }
    public static String c_stringFile = "conversation/corvette_imperial_pilot";
    public boolean corvette_imperial_pilot_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_imperial_pilot_condition_hasImperialTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Imperial");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return false;
        }
        obj_id[] tickets = space_dungeon.findValidDungeonTickets(player, npc);
        if (tickets != null && tickets.length > 0)
        {
            return true;
        }
        return false;
    }
    public boolean corvette_imperial_pilot_condition_tooManyInGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            if (isIdValid(groupObj))
            {
                int numGroupMembers = getGroupSize(groupObj);
                if (numGroupMembers > 10)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean corvette_imperial_pilot_condition_tooManyAndNotAllImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (corvette_imperial_pilot_condition_tooManyInGroup(player, npc) && corvette_imperial_pilot_condition_notAllImperial(player, npc));
    }
    public boolean corvette_imperial_pilot_condition_notAllImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            if (isIdValid(groupObj))
            {
                obj_id[] groupMembers = getGroupMemberIds(groupObj);
                int numGroupMembers = groupMembers.length;
                for (int f = 0; f < numGroupMembers; f++)
                {
                    obj_id groupie = groupMembers[f];
                    if (isIdValid(groupie))
                    {
                        String groupieFaction = factions.getFaction(groupie);
                        if (groupieFaction == null || !groupieFaction.equals("Imperial"))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean corvette_imperial_pilot_condition_itp_kaja_01_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "itp_kaja_01", "itp_kaja_01_01a");
    }
    public void corvette_imperial_pilot_action_sendToDungeon(obj_id player, obj_id npc) throws InterruptedException
    {
        space_dungeon.selectDungeonTicket(npc, player);
        return;
    }
    public void corvette_imperial_pilot_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public void corvette_imperial_pilot_action_itp_kaja_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "itp_kaja_01_01a");
    }
    public int corvette_imperial_pilot_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                corvette_imperial_pilot_action_itp_kaja_signal(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a7ad2e69"))
        {
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6cca850b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ab00f5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a71d76e");
                    }
                    utils.setScriptVar(player, "conversation.corvette_imperial_pilot.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8bb955f8"))
        {
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3fa1e397");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_imperial_pilot_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1ab00f5e"))
        {
            if (corvette_imperial_pilot_condition_tooManyAndNotAllImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d5421c5c");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corvette_imperial_pilot_condition_notAllImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_db4a97a9");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corvette_imperial_pilot_condition_tooManyInGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37545a64");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_500c4dc0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62aa5014");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d553beb4");
                    }
                    utils.setScriptVar(player, "conversation.corvette_imperial_pilot.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a71d76e"))
        {
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e84669ef");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_imperial_pilot_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62aa5014"))
        {
            if (corvette_imperial_pilot_condition_tooManyAndNotAllImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4044790f");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corvette_imperial_pilot_condition_notAllImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dd163c95");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corvette_imperial_pilot_condition_tooManyInGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c51b25");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                corvette_imperial_pilot_action_sendToDungeon(player, npc);
                string_id message = new string_id(c_stringFile, "s_fbc421e2");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d553beb4"))
        {
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b5aa0f29");
                utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
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
            detachScript(self, "conversation.corvette_imperial_pilot");
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
        detachScript(self, "conversation.corvette_imperial_pilot");
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
        if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
        {
            corvette_imperial_pilot_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_e1b151c2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_imperial_pilot_condition_itp_kaja_01_active(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_imperial_pilot_condition_hasImperialTicket(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_imperial_pilot_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a7ad2e69");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8bb955f8");
                }
                utils.setScriptVar(player, "conversation.corvette_imperial_pilot.branchId", 1);
                npcStartConversation(player, npc, "corvette_imperial_pilot", message, responses);
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
        if (!conversationId.equals("corvette_imperial_pilot"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
        if (branchId == 1 && corvette_imperial_pilot_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corvette_imperial_pilot_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corvette_imperial_pilot_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corvette_imperial_pilot.branchId");
        return SCRIPT_CONTINUE;
    }
}
