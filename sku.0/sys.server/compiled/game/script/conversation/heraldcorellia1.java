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

public class heraldcorellia1 extends script.base_script
{
    public heraldcorellia1()
    {
    }
    public static String c_stringFile = "conversation/heraldcorellia1";
    public boolean heraldcorellia1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void heraldcorellia1_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void heraldcorellia1_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location nyax = new location(1392, 0, -304);
        obj_id waypoint = createWaypointInDatapad(player, nyax);
        setWaypointName(waypoint, "Lord Nyax Followers");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "shush");
    }
    public void heraldcorellia1_action_waypoint2(obj_id player, obj_id npc) throws InterruptedException
    {
        location cave = new location(-2492, 0, 2906);
        obj_id waypoint = createWaypointInDatapad(player, cave);
        setWaypointName(waypoint, "Afarathu Cave");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldcorellia1_action_waypoint3(obj_id player, obj_id npc) throws InterruptedException
    {
        location imperial = new location(4664, 0, -5784);
        obj_id waypoint = createWaypointInDatapad(player, imperial);
        setWaypointName(waypoint, "Imperial Stronghold");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldcorellia1_action_abovehead(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "hands_above_head");
    }
    public void heraldcorellia1_action_shush(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shush");
    }
    public void heraldcorellia1_action_thoughtful(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public void heraldcorellia1_action_dismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave_on_dismissing");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.heraldcorellia1");
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
        detachScript(self, "npc.conversation.heraldcorellia1");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (heraldcorellia1_condition__defaultCondition(player, self))
        {
            heraldcorellia1_action_abovehead(player, self);
            string_id message = new string_id(c_stringFile, "s_62db6450");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldcorellia1_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7634b077");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fd0eff19");
                }
                setObjVar(player, "conversation.heraldcorellia1.branchId", 1);
                npcStartConversation(player, self, "heraldcorellia1", message, responses);
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
        if (!conversationId.equals("heraldcorellia1"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.heraldcorellia1.branchId");
        if (branchId == 1 && response.equals("s_7634b077"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_94914b83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f78edca");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92abbb2d");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'AHH! Stay away from me! I didn't mean to--oh. You're not a part of Lord Nyax's Clan. What a relief! You scared me there. I am right, aren't I? You're not a spy, are you? ... are you?!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_fd0eff19"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_thoughtful(player, self);
                string_id message = new string_id(c_stringFile, "s_b24dbaa5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_302a0829");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16ef1618");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'AHH! Stay away from me! I didn't mean to--oh. You're not a part of Lord Nyax's Clan. What a relief! You scared me there. I am right, aren't I? You're not a spy, are you? ... are you?!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_4f78edca"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2dc5fa6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb636d95");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f20cf001");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad to hear that. You had me scared there for a little bit.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_92abbb2d"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_60613aaf");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad to hear that. You had me scared there for a little bit.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_bb636d95"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_15b3396c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was one of them. They don't like the fact that I left just to cash in on their location by providing others with their whereabouts. Say, would you like to know? Free of charge! I also know a little bit about other activities on this planet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_f20cf001"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5933dda");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d004968e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4e268ec");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce93e2a4");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was one of them. They don't like the fact that I left just to cash in on their location by providing others with their whereabouts. Say, would you like to know? Free of charge! I also know a little bit about other activities on this planet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_d004968e"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_1350775c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_4e268ec"))
        {
            heraldcorellia1_action_waypoint2(player, self);
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a7c04702");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_ce93e2a4"))
        {
            heraldcorellia1_action_waypoint3(player, self);
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5bd24f96");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_302a0829"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a343f6b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb636d95");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f20cf001");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm... I'm suspicious. You don't look like someone Lord Nyax would recruit. Fine. If you are a part of the clan, then where is the hideout?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_16ef1618"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_43ff531e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14db17db");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8487276");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm... I'm suspicious. You don't look like someone Lord Nyax would recruit. Fine. If you are a part of the clan, then where is the hideout?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_bb636d95"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_15b3396c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's what I thought. I knew you weren't. I should know. I was a part of the clan myself. They don't like the fact that I left just to cash in on their location by providing others with their whereabouts. Say, would you like to know? Free of charge! I also know a little bit about other activities on this planet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_f20cf001"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5933dda");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d004968e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4e268ec");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce93e2a4");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's what I thought. I knew you weren't. I should know. I was a part of the clan myself. They don't like the fact that I left just to cash in on their location by providing others with their whereabouts. Say, would you like to know? Free of charge! I also know a little bit about other activities on this planet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_d004968e"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_1350775c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_4e268ec"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint2(player, self);
                string_id message = new string_id(c_stringFile, "s_a7c04702");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_ce93e2a4"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint3(player, self);
                string_id message = new string_id(c_stringFile, "s_5bd24f96");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_14db17db"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1abf1335");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b5cd3612");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eff34704");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Lies! I knew you weren't a part of the clan. I should know. I was one of them! I left because I didn't agree with what they were doing.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_8487276"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_dismiss(player, self);
                string_id message = new string_id(c_stringFile, "s_508d181c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7170206c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4a2753b");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Lies! I knew you weren't a part of the clan. I should know. I was one of them! I left because I didn't agree with what they were doing.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_b5cd3612"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_1350775c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Uhh.. I'm not going to tell you. You'd probably hurt me if I did. I did some of those things, you know. Why don't you go there yourself and find out. Unless, you'd rather do something else on this planet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_eff34704"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5933dda");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d004968e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4e268ec");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce93e2a4");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Uhh.. I'm not going to tell you. You'd probably hurt me if I did. I did some of those things, you know. Why don't you go there yourself and find out. Unless, you'd rather do something else on this planet.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_d004968e"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_1350775c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_4e268ec"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint2(player, self);
                string_id message = new string_id(c_stringFile, "s_a7c04702");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_ce93e2a4"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint3(player, self);
                string_id message = new string_id(c_stringFile, "s_5bd24f96");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_7170206c"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9d54ab3b");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hrmph! Fine! I was going to give you information, but now I'm not going to!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_f4a2753b"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_abab52df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f20cf001");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeb5275");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hrmph! Fine! I was going to give you information, but now I'm not going to!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_f20cf001"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5933dda");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldcorellia1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4e268ec");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce93e2a4");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d004968e");
                    }
                    setObjVar(player, "conversation.heraldcorellia1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldcorellia1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're lucky I'm desperate for help. I have some information regarding the Nyax Clan and their whereabouts. I also have information about other activities as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_8aeb5275"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_1350775c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're lucky I'm desperate for help. I have some information regarding the Nyax Clan and their whereabouts. I also have information about other activities as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_4e268ec"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint2(player, self);
                string_id message = new string_id(c_stringFile, "s_a7c04702");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_ce93e2a4"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint3(player, self);
                string_id message = new string_id(c_stringFile, "s_5bd24f96");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_d004968e"))
        {
            if (heraldcorellia1_condition__defaultCondition(player, self))
            {
                heraldcorellia1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_1350775c");
                removeObjVar(player, "conversation.heraldcorellia1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, there's the Afarathu Cave and the Imperial Strong hold. What are you interested in?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.heraldcorellia1.branchId");
        return SCRIPT_CONTINUE;
    }
}
