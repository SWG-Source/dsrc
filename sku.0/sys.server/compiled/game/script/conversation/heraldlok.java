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
import script.library.utils;

public class heraldlok extends script.base_script
{
    public heraldlok()
    {
    }
    public static String c_stringFile = "conversation/heraldlok";
    public boolean heraldlok_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean heraldlok_condition_hasKimogilaTownWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location kimogilaLoc = new location(-70, 0, 2600);
        String kimogilaName = "Kimogila Town";
        obj_id[] playerWaypoints = getWaypointsInDatapad(player);
        if (playerWaypoints != null && playerWaypoints.length > 0)
        {
            for (int i = 0; i < playerWaypoints.length; i++)
            {
                String waypointName = getWaypointName(playerWaypoints[i]);
                location waypointLoc = getWaypointLocation(playerWaypoints[i]);
                if (waypointName.equals(kimogilaName) || waypointLoc.equals(kimogilaLoc))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean heraldlok_condition_hasCanyonCorsairWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location corsairLoc = new location(-3692, 0, -3800);
        String corsairName = "Canyon Corsair Stronghold";
        obj_id[] playerWaypoints = getWaypointsInDatapad(player);
        if (playerWaypoints != null && playerWaypoints.length > 0)
        {
            for (int i = 0; i < playerWaypoints.length; i++)
            {
                String waypointName = getWaypointName(playerWaypoints[i]);
                location waypointLoc = getWaypointLocation(playerWaypoints[i]);
                if (waypointName.equals(corsairName) || waypointLoc.equals(corsairLoc))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean heraldlok_condition_hasBothWaypoints(obj_id player, obj_id npc) throws InterruptedException
    {
        return (heraldlok_condition_hasKimogilaTownWaypoint(player, npc) && heraldlok_condition_hasCanyonCorsairWaypoint(player, npc));
    }
    public void heraldlok_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location kimogila = new location(-70, 0, 2600);
        obj_id waypoint = createWaypointInDatapad(player, kimogila);
        setWaypointName(waypoint, "Kimogila Town");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "shrug_shoulders");
    }
    public void heraldlok_action_waypoint2(obj_id player, obj_id npc) throws InterruptedException
    {
        location corsair = new location(-3692, 0, -3800);
        obj_id waypoint = createWaypointInDatapad(player, corsair);
        setWaypointName(waypoint, "Canyon Corsair Stronghold");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldlok_action_waypoint3(obj_id player, obj_id npc) throws InterruptedException
    {
        location bloodrazor = new location(3508, 0, 2084);
        obj_id waypoint = createWaypointInDatapad(player, bloodrazor);
        setWaypointName(waypoint, "Downed Bloodrazor Smuggling Ship");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldlok_action_waypoint4(obj_id player, obj_id npc) throws InterruptedException
    {
        location droid = new location(3172, 0, -4772);
        obj_id waypoint = createWaypointInDatapad(player, droid);
        setWaypointName(waypoint, "Droid Engineer's Cave");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public int heraldlok_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36a4e374"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ff7f26e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!heraldlok_condition_hasKimogilaTownWaypoint(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!heraldlok_condition_hasCanyonCorsairWaypoint(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldlok_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95bfd0f3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_434a59e6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e804346");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c5a66e82"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6336e957");
                utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95bfd0f3"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                heraldlok_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_a5343515");
                utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_434a59e6"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                heraldlok_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_be60eda6");
                utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5e804346"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e933930f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!heraldlok_condition_hasBothWaypoints(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8387526");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119ff40f");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a8387526"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7117043f");
                utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119ff40f"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57d2d366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!heraldlok_condition_hasKimogilaTownWaypoint(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!heraldlok_condition_hasCanyonCorsairWaypoint(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                heraldlok_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                heraldlok_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.heraldlok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.heraldlok");
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
        detachScript(self, "conversation.heraldlok");
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
        if (heraldlok_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_599fe081");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldlok_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldlok_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c5a66e82");
                }
                utils.setScriptVar(player, "conversation.heraldlok.branchId", 1);
                npcStartConversation(player, npc, "heraldlok", message, responses);
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
        if (!conversationId.equals("heraldlok"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.heraldlok.branchId");
        if (branchId == 1 && heraldlok_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && heraldlok_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && heraldlok_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && heraldlok_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.heraldlok.branchId");
        return SCRIPT_CONTINUE;
    }
}
