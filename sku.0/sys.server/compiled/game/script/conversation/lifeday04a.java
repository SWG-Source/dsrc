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

public class lifeday04a extends script.base_script
{
    public lifeday04a()
    {
    }
    public static String c_stringFile = "conversation/lifeday04a";
    public boolean lifeday04a_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lifeday04a_condition_hasBeenRewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        int convTracker = getIntObjVar(player, "lifeday04.convTracker");
        if (hasObjVar(player, "lifeday04.rewarded") || convTracker == 15)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04a_condition_hasSpokenOnce(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "lifeday04.convTracker"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04a_condition_isaWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        int species = getSpecies(player);
        if (species == SPECIES_WOOKIEE)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void lifeday04a_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        int[] coordinates = 
        {
            -2579,
            0,
            -5520,
            -1090,
            0,
            -1000,
            -5,
            0,
            -3922
        };
        String[] planet = 
        {
            "dathomir",
            "endor",
            "yavin4"
        };
        string_id waypointName = new string_id("quest/lifeday/lifeday", "waypoint_name");
        string_id waypointUpdated = new string_id("quest/lifeday/lifeday", "waypoint_updated");
        int roll = rand(0, 2);
        location lifedayLoc = new location(coordinates[(roll * 3)], coordinates[(roll * 3) + 1], coordinates[(roll * 3) + 2], planet[roll]);
        obj_id waypoint = createWaypointInDatapad(player, lifedayLoc);
        if (isIdValid(waypoint))
        {
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, utils.packStringId(waypointName));
        }
        sendSystemMessage(player, waypointUpdated);
    }
    public void lifeday04a_action_finishedConversation(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "lifeday04.convTracker", 0);
        int[] coordinates = 
        {
            -2579,
            0,
            -5520,
            -1090,
            0,
            -1000,
            -5,
            0,
            -3922
        };
        String[] planet = 
        {
            "dathomir",
            "endor",
            "yavin4"
        };
        string_id waypointName = new string_id("quest/lifeday/lifeday", "waypoint_name");
        string_id waypointUpdated = new string_id("quest/lifeday/lifeday", "waypoint_updated");
        int roll = rand(0, 2);
        location lifedayLoc = new location(coordinates[(roll * 3)], coordinates[(roll * 3) + 1], coordinates[(roll * 3) + 2], planet[roll]);
        obj_id waypoint = createWaypointInDatapad(player, lifedayLoc);
        if (isIdValid(waypoint))
        {
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, utils.packStringId(waypointName));
        }
        sendSystemMessage(player, waypointUpdated);
    }
    public int lifeday04a_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8fa670"))
        {
            lifeday04a_action_giveWaypoint(player, npc);
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_1d62f66a");
                utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday04a_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20e6f0a5"))
        {
            lifeday04a_action_finishedConversation(player, npc);
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_f9bf77a0");
                utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday04a_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_643fa34"))
        {
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_50a0c963");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday04a_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lifeday04a_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a85dd74");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20268f64");
                    }
                    utils.setScriptVar(player, "conversation.lifeday04a.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_acf458b4"))
        {
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_b3f5b52e");
                utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday04a_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2a85dd74"))
        {
            lifeday04a_action_finishedConversation(player, npc);
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_dbcb339");
                utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20268f64"))
        {
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_b3f5b52e");
                utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
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
            detachScript(self, "conversation.lifeday04a");
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
        detachScript(self, "conversation.lifeday04a");
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
        if (lifeday04a_condition_hasBeenRewarded(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5c9098df");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lifeday04a_condition_hasSpokenOnce(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            string_id message = new string_id(c_stringFile, "s_da120aad");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday04a_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8fa670");
                }
                utils.setScriptVar(player, "conversation.lifeday04a.branchId", 2);
                npcStartConversation(player, npc, "lifeday04a", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday04a_condition_isaWookiee(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            string_id message = new string_id(c_stringFile, "s_311837be");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday04a_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20e6f0a5");
                }
                utils.setScriptVar(player, "conversation.lifeday04a.branchId", 4);
                npcStartConversation(player, npc, "lifeday04a", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday04a_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_ada83d27");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday04a_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday04a_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_643fa34");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_acf458b4");
                }
                utils.setScriptVar(player, "conversation.lifeday04a.branchId", 6);
                npcStartConversation(player, npc, "lifeday04a", message, responses);
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
        if (!conversationId.equals("lifeday04a"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lifeday04a.branchId");
        if (branchId == 2 && lifeday04a_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && lifeday04a_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && lifeday04a_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lifeday04a_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lifeday04a.branchId");
        return SCRIPT_CONTINUE;
    }
}
