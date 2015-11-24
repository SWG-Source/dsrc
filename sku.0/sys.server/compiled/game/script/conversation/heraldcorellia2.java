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

public class heraldcorellia2 extends script.base_script
{
    public heraldcorellia2()
    {
    }
    public static String c_stringFile = "conversation/heraldcorellia2";
    public boolean heraldcorellia2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void heraldcorellia2_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location corsec = new location(5306, 0, 1524);
        obj_id waypoint = createWaypointInDatapad(player, corsec);
        setWaypointName(waypoint, "Rogue Corsec");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "shrug_shoulders");
    }
    public void heraldcorellia2_action_waypoint2(obj_id player, obj_id npc) throws InterruptedException
    {
        location drall = new location(1042, 0, 4193);
        obj_id waypoint = createWaypointInDatapad(player, drall);
        setWaypointName(waypoint, "Drall Patriots Hideout");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "point_away");
    }
    public void heraldcorellia2_action_snap2(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "snap_finger2");
    }
    public void heraldcorellia2_action_dismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave_on_dismissing");
    }
    public void heraldcorellia2_action_check(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "check_wrist_device");
    }
    public void heraldcorellia2_action_shrug(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shrug_shoulders");
    }
    public void heraldcorellia2_action_hair(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "hair_flip");
    }
    public int heraldcorellia2_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34578735"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                heraldcorellia2_action_snap2(player, npc);
                string_id message = new string_id(c_stringFile, "s_9e3a2d26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e8ba751");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ae8d99dc");
                    }
                    utils.setScriptVar(player, "conversation.heraldcorellia2.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_365cae75"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                heraldcorellia2_action_check(player, npc);
                string_id message = new string_id(c_stringFile, "s_e8e88020");
                utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldcorellia2_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e8ba751"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_467e5760");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5f670c9a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd53304");
                    }
                    utils.setScriptVar(player, "conversation.heraldcorellia2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ae8d99dc"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                heraldcorellia2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_cd3a7aff");
                utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldcorellia2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5f670c9a"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7ef6e01");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6152d272");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6fe071a8");
                    }
                    utils.setScriptVar(player, "conversation.heraldcorellia2.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fd53304"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b21d503f");
                utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldcorellia2_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6152d272"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                heraldcorellia2_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_1540303f");
                utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6fe071a8"))
        {
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                heraldcorellia2_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_47d10547");
                utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
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
            detachScript(self, "conversation.heraldcorellia2");
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
        detachScript(self, "conversation.heraldcorellia2");
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
        if (heraldcorellia2_condition__defaultCondition(player, npc))
        {
            heraldcorellia2_action_hair(player, npc);
            string_id message = new string_id(c_stringFile, "s_17f4a48e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldcorellia2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldcorellia2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34578735");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_365cae75");
                }
                utils.setScriptVar(player, "conversation.heraldcorellia2.branchId", 1);
                npcStartConversation(player, npc, "heraldcorellia2", message, responses);
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
        if (!conversationId.equals("heraldcorellia2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.heraldcorellia2.branchId");
        if (branchId == 1 && heraldcorellia2_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && heraldcorellia2_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && heraldcorellia2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && heraldcorellia2_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.heraldcorellia2.branchId");
        return SCRIPT_CONTINUE;
    }
}
