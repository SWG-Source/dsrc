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
import script.library.group;
import script.library.instance;
import script.library.space_dungeon;
import script.library.space_dungeon_data;
import script.library.utils;

public class story_arc_chapter_three_pilot extends script.base_script
{
    public story_arc_chapter_three_pilot()
    {
    }
    public static String c_stringFile = "conversation/story_arc_chapter_three_pilot";
    public boolean story_arc_chapter_three_pilot_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_arc_chapter_three_pilot_condition_travelToVolcano(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_03", "volcano_arena_one"))
        {
            instance.flagPlayerForInstance(player, "mustafar_volcano");
            return true;
        }
        return false;
    }
    public boolean story_arc_chapter_three_pilot_condition_travelToVolcanoTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "som_story_arc_chapter_three_03", "volcano_arena_one") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_three_03"))
        {
            instance.flagPlayerForInstance(player, "mustafar_volcano");
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void story_arc_chapter_three_pilot_action_sendFirstSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 80f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                if (numInGroup < 1)
                {
                    return;
                }
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    if (groundquests.isTaskActive(thisMember, "som_story_arc_chapter_three_03", "volcano_arena_one"))
                    {
                        groundquests.sendSignal(thisMember, "volcano_arena_pilot");
                    }
                }
            }
        }
        else 
        {
            if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_03", "volcano_arena_one"))
            {
                groundquests.sendSignal(player, "volcano_arena_pilot");
                return;
            }
        }
        return;
    }
    public void story_arc_chapter_three_pilot_action_sendGroupToVolcano(obj_id player, obj_id npc) throws InterruptedException
    {
        instance.requestInstanceMovement(player, "mustafar_volcano");
    }
    public int story_arc_chapter_three_pilot_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            story_arc_chapter_three_pilot_action_sendFirstSignal(player, npc);
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                story_arc_chapter_three_pilot_action_sendGroupToVolcano(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_three_pilot_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            doAnimationAction(player, "wave_on_dismissing");
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_three_pilot_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger1");
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_three_pilot_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int story_arc_chapter_three_pilot_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            story_arc_chapter_three_pilot_action_sendFirstSignal(player, npc);
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                story_arc_chapter_three_pilot_action_sendGroupToVolcano(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
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
            detachScript(self, "conversation.story_arc_chapter_three_pilot");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Master Pilot Menddle");
        if (!hasObjVar(self, "space_dungeon.ticket.point"))
        {
            setObjVar(self, "space_dungeon.ticket.point", "mustafar");
        }
        if (!hasObjVar(self, "space_dungeon.ticket.dungeon"))
        {
            setObjVar(self, "space_dungeon.ticket.dungeon", "volcano_battlefield");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Master Pilot Menddle");
        if (!hasObjVar(self, "space_dungeon.ticket.point"))
        {
            setObjVar(self, "space_dungeon.ticket.point", "mustafar");
        }
        if (!hasObjVar(self, "space_dungeon.ticket.dungeon"))
        {
            setObjVar(self, "space_dungeon.ticket.dungeon", "volcano_battlefield");
        }
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
        detachScript(self, "conversation.story_arc_chapter_three_pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_type, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (!isIdValid(player) || !player.isAuthoritative())
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- player is invalid or not authoritative.");
            releaseClusterWideDataLock("dungeon", lock_key);
            if (isIdValid(player))
            {
                space_dungeon.cleanupPlayerTicketObjvars(player);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id ticket = null;
        if (hasObjVar(player, space_dungeon.VAR_TICKET_USED))
        {
            ticket = getObjIdObjVar(player, space_dungeon.VAR_TICKET_USED);
        }
        else 
        {
            ticket = player;
        }
        if (!isIdValid(ticket) || !ticket.isAuthoritative())
        {
            sendSystemMessage(player, space_dungeon.SID_ILLEGAL_TICKET);
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (!manage_name.equals("dungeon"))
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- ignorning manage_name " + manage_name + " because it is not dungeon.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (request_id < 1)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- invalid request_id value of " + request_id);
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_data == null)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon_data is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (element_name_list == null)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- element_name_list is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_type == null)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon_type is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = dungeon_type.substring(0, dungeon_type.length() - 1);
        for (int i = 0; i < dungeon_data.length; i++)
        {
            if (false == space_dungeon_data.isValidDungeon(dungeon_name))
            {
                LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon name of " + dungeon_name + " is not in the dungeon datatable.");
                break;
            }
            dictionary dungeon = dungeon_data[i];
            obj_id dungeon_id = dungeon.getObjId("dungeon_id");
            int session_id = dungeon.getInt("session_id");
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- session_id ->" + session_id + " dungeon_instance ->" + element_name_list[i]);
            float[] dungeon_position = 
            {
                dungeon.getInt("position_x"),
                dungeon.getInt("position_y"),
                dungeon.getInt("position_z")
            };
            if (!isIdValid(dungeon_id))
            {
                LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- bad data found for dungeon entry " + i + ". Ignoring.");
                continue;
            }
            if (session_id < 1)
            {
                setObjVar(player, space_dungeon.VAR_SESSION_ID, lock_key);
                dictionary dungeon_update = new dictionary();
                dungeon_update.put("session_id", lock_key);
                updateClusterWideData("dungeon", element_name_list[i], dungeon_update, lock_key);
                releaseClusterWideDataLock("dungeon", lock_key);
                dictionary d = new dictionary();
                d.put("session_id", lock_key);
                d.put("request_id", request_id);
                d.put("player", player);
                d.put("ticket_collector", self);
                if (hasObjVar(ticket, space_dungeon.VAR_TICKET_QUEST_TYPE))
                {
                    d.put("quest_type", getStringObjVar(ticket, space_dungeon.VAR_TICKET_QUEST_TYPE));
                }
                messageTo(dungeon_id, "msgSetSessionId", d, 0.0f, false);
                return SCRIPT_CONTINUE;
            }
        }
        releaseClusterWideDataLock("dungeon", lock_key);
        space_dungeon.cleanupPlayerTicketObjvars(player);
        space_dungeon.removeDungeonTraveler(self, request_id);
        string_id success = space_dungeon_data.getDungeonFailureString(dungeon_name);
        if (success == null)
        {
            sendSystemMessage(player, space_dungeon.SID_UNABLE_TO_FIND_DUNGEON);
        }
        else 
        {
            sendSystemMessage(player, success);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgStartDungeonTravel(obj_id self, dictionary params) throws InterruptedException
    {
        int session_id = params.getInt("session_id");
        LOG("space_dungeon", "msgStartDungeonTravel -- session_id ->" + session_id);
        obj_id dungeon_id = params.getObjId("dungeon_id");
        if (!isIdValid(dungeon_id))
        {
            LOG("space_dungeon", "player_travel.msgStartDungeonTravel -- dungeon_id is invalid for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = params.getString("dungeon_name");
        if (dungeon_id == null)
        {
            LOG("space_dungeon", "player_travel.msgStartDungeonTravel -- dungeon_name is null for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        int request_id = params.getInt("request_id");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "player_travel.msgStartDungeonTravel -- player is null for " + self);
            return SCRIPT_CONTINUE;
        }
        space_dungeon.removeDungeonTraveler(self, request_id);
        if (player.isAuthoritative())
        {
            location dungeon_loc = params.getLocation("dungeon_loc");
            if (dungeon_loc == null)
            {
                LOG("space_dungeon", "travel_space_dungeon.msgStartDungeonTravel -- location is null for " + self + ".");
                return SCRIPT_CONTINUE;
            }
            space_dungeon.movePlayerGroupToDungeon(player, dungeon_id, dungeon_name, dungeon_loc);
        }
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
        if (story_arc_chapter_three_pilot_condition_travelToVolcanoTwo(player, npc))
        {
            doAnimationAction(npc, "snap_finger1");
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId", 1);
                npcStartConversation(player, npc, "story_arc_chapter_three_pilot", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_three_pilot_condition_travelToVolcano(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId", 4);
                npcStartConversation(player, npc, "story_arc_chapter_three_pilot", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_arc_chapter_three_pilot_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "pound_fist_chest");
            string_id message = new string_id(c_stringFile, "s_42");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_arc_chapter_three_pilot"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
        if (branchId == 1 && story_arc_chapter_three_pilot_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && story_arc_chapter_three_pilot_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && story_arc_chapter_three_pilot_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && story_arc_chapter_three_pilot_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && story_arc_chapter_three_pilot_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.story_arc_chapter_three_pilot.branchId");
        return SCRIPT_CONTINUE;
    }
}
