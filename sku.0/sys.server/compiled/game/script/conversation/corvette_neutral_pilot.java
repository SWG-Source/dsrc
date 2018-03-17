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
import script.library.group;
import script.library.space_dungeon;

public class corvette_neutral_pilot extends script.base_script
{
    public corvette_neutral_pilot()
    {
    }
    public static String c_stringFile = "conversation/corvette_neutral_pilot";
    public boolean corvette_neutral_pilot_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_neutral_pilot_condition_hasNeutralTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Neutral");
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
    public boolean corvette_neutral_pilot_condition_tooManyInGroup(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_neutral_pilot_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void corvette_neutral_pilot_action_sendToDungeon(obj_id player, obj_id npc) throws InterruptedException
    {
        space_dungeon.selectDungeonTicket(npc, player);
        return;
    }
    public void corvette_neutral_pilot_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.corvette_neutral_pilot");
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
        detachScript(self, "npc.conversation.corvette_neutral_pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_neutral_pilot_condition__defaultCondition(player, self))
        {
            corvette_neutral_pilot_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_1e10bbe3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_neutral_pilot_condition_hasNeutralTicket(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_neutral_pilot_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4fb4580f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_facfb7c0");
                }
                setObjVar(player, "conversation.corvette_neutral_pilot.branchId", 1);
                npcStartConversation(player, self, "corvette_neutral_pilot", message, responses);
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
        if (!conversationId.equals("corvette_neutral_pilot"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.corvette_neutral_pilot.branchId");
        if (branchId == 1 && response.equals("s_4fb4580f"))
        {
            if (corvette_neutral_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fc033618");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_neutral_pilot_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_neutral_pilot_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.corvette_neutral_pilot.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you have to run around out here, don't touch anything! I'm dealing with some highly sensitive equipment in this area and don't want your clumsy fingers breaking something.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_facfb7c0"))
        {
            if (corvette_neutral_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ba55259a");
                removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you have to run around out here, don't touch anything! I'm dealing with some highly sensitive equipment in this area and don't want your clumsy fingers breaking something.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_1ab00f5e"))
        {
            if (corvette_neutral_pilot_condition_tooManyInGroup(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f2d8c3c4");
                removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corvette_neutral_pilot_condition_tooManyInGroup(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c4c8b476");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_neutral_pilot_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_neutral_pilot_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.corvette_neutral_pilot.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Me? And just how exactly would I do that? Wait... wait. Don't go. Why do you want to do something like that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_1a71d76e"))
        {
            if (corvette_neutral_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f0008c9b");
                removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Me? And just how exactly would I do that? Wait... wait. Don't go. Why do you want to do something like that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_62aa5014"))
        {
            if (corvette_neutral_pilot_condition_tooManyInGroup(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6423c2d5");
                removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (corvette_neutral_pilot_condition__defaultCondition(player, self))
            {
                corvette_neutral_pilot_action_sendToDungeon(player, self);
                string_id message = new string_id(c_stringFile, "s_801d58df");
                removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmmm... reminds me of some information I overheard recently. Don't you worry about where I heard it! Just be glad that I did, because I think I can help you. Oh, something you should know - after you get to the corvette, you'll have an hour to conclude your business. After an hour, it's too late. Game over. Mission failed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_d553beb4"))
        {
            if (corvette_neutral_pilot_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_18d816d4");
                removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmmm... reminds me of some information I overheard recently. Don't you worry about where I heard it! Just be glad that I did, because I think I can help you. Oh, something you should know - after you get to the corvette, you'll have an hour to conclude your business. After an hour, it's too late. Game over. Mission failed.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corvette_neutral_pilot.branchId");
        return SCRIPT_CONTINUE;
    }
}
