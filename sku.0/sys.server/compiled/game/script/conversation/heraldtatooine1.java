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

public class heraldtatooine1 extends script.base_script
{
    public heraldtatooine1()
    {
    }
    public static String c_stringFile = "conversation/heraldtatooine1";
    public boolean heraldtatooine1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void heraldtatooine1_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void heraldtatooine1_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location fort = new location(-3960, 0, 6233);
        obj_id waypoint = createWaypointInDatapad(player, fort);
        setWaypointName(waypoint, "Fort Tusken");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldtatooine1_action_waypoint2(obj_id player, obj_id npc) throws InterruptedException
    {
        location village = new location(-5322, 0, -4444);
        obj_id waypoint = createWaypointInDatapad(player, village);
        setWaypointName(waypoint, "Tusken Village");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldtatooine1_action_shake(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_no");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.heraldtatooine1");
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
        detachScript(self, "npc.conversation.heraldtatooine1");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (heraldtatooine1_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_70ffe9b9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldtatooine1_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7481bfea");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11c73436");
                }
                setObjVar(player, "conversation.heraldtatooine1.branchId", 1);
                npcStartConversation(player, self, "heraldtatooine1", message, responses);
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
        if (!conversationId.equals("heraldtatooine1"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.heraldtatooine1.branchId");
        if (branchId == 1 && response.equals("s_7481bfea"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7133e831");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8026269");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e03237fd");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You seem a bit... weak. Are you a petty commoner? You must be. Otherwise, you'd have enough sense to stay away from a bounty hunter. What do you want, then?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_11c73436"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c5e5fb33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3cf68ea6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d00b322a");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You seem a bit... weak. Are you a petty commoner? You must be. Otherwise, you'd have enough sense to stay away from a bounty hunter. What do you want, then?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_8026269"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_shake(player, self);
                string_id message = new string_id(c_stringFile, "s_65daca4c");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have every reason to be. You have not suffered like I have suffered.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_e03237fd"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_74f40f3a");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have every reason to be. You have not suffered like I have suffered.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_3cf68ea6"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c7330dee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3ce72c0");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excuse me? You are daft, aren't you? In fact, I'd bet you're so daft you'd be allied with those Tuskens. Tell me I'm right. Please.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_d00b322a"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fd76ac2c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9dbca073");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excuse me? You are daft, aren't you? In fact, I'd bet you're so daft you'd be allied with those Tuskens. Tell me I'm right. Please.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_e3ce72c0"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_shake(player, self);
                string_id message = new string_id(c_stringFile, "s_65daca4c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14495ea");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f48827a8");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm a tired bounty hunter and my patience is lacking. I've been so consumed with killing the Tuskens that I've been left empty, exhausted. But I must destroy them... it is my destiny.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_14495ea"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_21aa21ca");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39f16ceb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66f743cb");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's a painful subject, but if you must know: the Tuskens destroyed my family and I have brought it upon myself to rid Tatooine of their worthless existence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_f48827a8"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1421ed7f");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's a painful subject, but if you must know: the Tuskens destroyed my family and I have brought it upon myself to rid Tatooine of their worthless existence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_39f16ceb"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_845c935e");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How could you help? Unless... you help me fight them. I have two locations in which I heavily hunt these desert fiends. Fort Tusken and the Tusken Village.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_66f743cb"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_waypoint2(player, self);
                string_id message = new string_id(c_stringFile, "s_ef7c85cf");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How could you help? Unless... you help me fight them. I have two locations in which I heavily hunt these desert fiends. Fort Tusken and the Tusken Village.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_9dbca073"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b88d9d9f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c370ec78");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ca645a4");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You may actually have more sense than a kreetle. All right. I suppose I'll let you live. Filthy Tuskens, you say? I have information regarding the whereabouts of several. Interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_36a4e374"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_67880d8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dca89f7e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8ac3feb5");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You may actually have more sense than a kreetle. All right. I suppose I'll let you live. Filthy Tuskens, you say? I have information regarding the whereabouts of several. Interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_c370ec78"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68c8ab4e");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nothing. I despise the Tuskens with every moment of my existence. I wouldn't mind hiring a few commoners to ease my woes in destroying them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_1ca645a4"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_886ada99");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dca89f7e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8ac3feb5");
                    }
                    setObjVar(player, "conversation.heraldtatooine1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.heraldtatooine1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Nothing. I despise the Tuskens with every moment of my existence. I wouldn't mind hiring a few commoners to ease my woes in destroying them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_dca89f7e"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_845c935e");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You've made a tired bounty hunter glad. Which location do you wish to hear of? Tusken Village or Fort Tusken?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_8ac3feb5"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_waypoint2(player, self);
                string_id message = new string_id(c_stringFile, "s_ef7c85cf");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You've made a tired bounty hunter glad. Which location do you wish to hear of? Tusken Village or Fort Tusken?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_dca89f7e"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_845c935e");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which location? Tusken Village? Or the bigger target, Fort Tusken.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_8ac3feb5"))
        {
            if (heraldtatooine1_condition__defaultCondition(player, self))
            {
                heraldtatooine1_action_waypoint2(player, self);
                string_id message = new string_id(c_stringFile, "s_ef7c85cf");
                removeObjVar(player, "conversation.heraldtatooine1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which location? Tusken Village? Or the bigger target, Fort Tusken.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.heraldtatooine1.branchId");
        return SCRIPT_CONTINUE;
    }
}
