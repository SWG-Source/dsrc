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
import script.library.group;
import script.library.space_dungeon;

public class corvette_rebel_pilot extends script.base_script
{
    public corvette_rebel_pilot()
    {
    }
    public static String c_stringFile = "conversation/corvette_rebel_pilot";
    public boolean corvette_rebel_pilot_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_rebel_pilot_condition_hasRebelTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Rebel");
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
    public boolean corvette_rebel_pilot_condition_tooManyInGroup(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corvette_rebel_pilot_condition_tooManyAndNotAllRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (corvette_rebel_pilot_condition_tooManyInGroup(player, npc) && corvette_rebel_pilot_condition_notAllRebel(player, npc));
    }
    public boolean corvette_rebel_pilot_condition_notAllRebel(obj_id player, obj_id npc) throws InterruptedException
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
                        if (groupieFaction == null || !groupieFaction.equals("Rebel"))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public void corvette_rebel_pilot_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void corvette_rebel_pilot_action_sendToDungeon(obj_id player, obj_id npc) throws InterruptedException
    {
        space_dungeon.selectDungeonTicket(npc, player);
        return;
    }
    public void corvette_rebel_pilot_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.corvette_rebel_pilot");
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
        detachScript(self, "npc.conversation.corvette_rebel_pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_rebel_pilot_condition__defaultCondition(player, self))
        {
            corvette_rebel_pilot_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_669e781c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_rebel_pilot_condition_hasRebelTicket(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1a003c0e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6e257495");
                }
                setObjVar(player, "conversation.corvette_rebel_pilot.branchId", 1);
                npcStartConversation(player, self, "corvette_rebel_pilot", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corvette_rebel_pilot"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.corvette_rebel_pilot.branchId");
        if (branchId == 1 && response.equals("s_1a003c0e"))
        {
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e2aa2143");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_rebel_pilot_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_rebel_pilot_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.corvette_rebel_pilot.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am so bored, they've kept me in here managing flight schedules for what feels like an eternity now. Please tell me you're here with my reassignment orders?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_6e257495"))
        {
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1674c48");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am so bored, they've kept me in here managing flight schedules for what feels like an eternity now. Please tell me you're here with my reassignment orders?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_1ab00f5e"))
        {
            if (corvette_rebel_pilot_condition_tooManyAndNotAllRebel(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_221a046");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_rebel_pilot_condition_notAllRebel(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_808bd5e8");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_rebel_pilot_condition_tooManyInGroup(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a538ed35");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4816807a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_rebel_pilot_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_rebel_pilot_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e851c3db");
                    }
                    setObjVar(player, "conversation.corvette_rebel_pilot.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, there's lots of things I could help you with. And possibly even something like that. What's your story?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_1a71d76e"))
        {
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_29e528c6");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, there's lots of things I could help you with. And possibly even something like that. What's your story?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_62aa5014"))
        {
            if (corvette_rebel_pilot_condition_tooManyAndNotAllRebel(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e1cf0c03");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_rebel_pilot_condition_notAllRebel(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_84d3ed4b");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_rebel_pilot_condition_tooManyInGroup(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7c51b25");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
            {
                corvette_rebel_pilot_action_sendToDungeon(player, self);
                string_id message = new string_id(c_stringFile, "s_52a4b87d");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah yes, I was briefed about something like that earlier today. It was one of our Corellian CR-90 corvettes, I believe. I'm glad we're going to do something about it. I can definitely help you. But we're working against the clock here. Once on the corvette, you'll only have an hour to complete your mission. After that, it's too late.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_e851c3db"))
        {
            if (corvette_rebel_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_35e19d9c");
                removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah yes, I was briefed about something like that earlier today. It was one of our Corellian CR-90 corvettes, I believe. I'm glad we're going to do something about it. I can definitely help you. But we're working against the clock here. Once on the corvette, you'll only have an hour to complete your mission. After that, it's too late.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corvette_rebel_pilot.branchId");
        return SCRIPT_CONTINUE;
    }
}
