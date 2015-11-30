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

public class heraldlok2 extends script.base_script
{
    public heraldlok2()
    {
    }
    public static String c_stringFile = "conversation/heraldlok2";
    public boolean heraldlok2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void heraldlok2_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location droid = new location(3272, 0, -4872);
        obj_id waypoint = createWaypointInDatapad(player, droid);
        setWaypointName(waypoint, "Droid Engineer's Cave");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldlok2_action_waypoint2(obj_id player, obj_id npc) throws InterruptedException
    {
        location ship = new location(3608, 0, 2184);
        obj_id waypoint = createWaypointInDatapad(player, ship);
        setWaypointName(waypoint, "Downed Smuggler's Ship");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public void heraldlok2_action_laugh(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "laugh_cackle");
    }
    public void heraldlok2_action_thoutful(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public void heraldlok2_action_dismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave_on_dismissing");
    }
    public void heraldlok2_action_check(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "check_wrist_device");
    }
    public int heraldlok2_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63fbc0e4"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_laugh(player, npc);
                string_id message = new string_id(c_stringFile, "s_471ddd18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a66ef7ad");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d17c998e");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok2.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7bf56007"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_391e8a36");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok2_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48566ef9"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6cfa2fa7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f395897");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9be34260");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a66ef7ad"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a9eda6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok2.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d17c998e"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_check(player, npc);
                string_id message = new string_id(c_stringFile, "s_657533b5");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3f395897"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2f559d1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7884d24e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4db27a02");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326fcc61");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok2.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9be34260"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46236858");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok2_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7884d24e"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc5dc465");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4db27a02"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_f8224468");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_326fcc61"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_4487b6cf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_248becfc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_372fb7c1");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248becfc"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e4e08f1f");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_372fb7c1"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8247a8b0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldlok2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.heraldlok2.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldlok2_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                heraldlok2_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
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
            detachScript(self, "conversation.heraldlok2");
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
        detachScript(self, "conversation.heraldlok2");
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
        if (heraldlok2_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_66d367c9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldlok2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldlok2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63fbc0e4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7bf56007");
                }
                utils.setScriptVar(player, "conversation.heraldlok2.branchId", 1);
                npcStartConversation(player, npc, "heraldlok2", message, responses);
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
        if (!conversationId.equals("heraldlok2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.heraldlok2.branchId");
        if (branchId == 1 && heraldlok2_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && heraldlok2_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && heraldlok2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && heraldlok2_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && heraldlok2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && heraldlok2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && heraldlok2_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.heraldlok2.branchId");
        return SCRIPT_CONTINUE;
    }
}
