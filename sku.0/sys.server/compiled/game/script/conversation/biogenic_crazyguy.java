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

public class biogenic_crazyguy extends script.base_script
{
    public biogenic_crazyguy()
    {
    }
    public static String c_stringFile = "conversation/biogenic_crazyguy";
    public boolean biogenic_crazyguy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_crazyguy_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int convo_tracker = getIntObjVar(player, "biogenic.crazy_convo");
        if (convo_tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_crazyguy_condition_get_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int convo_tracker = getIntObjVar(player, "biogenic.crazy_convo");
        if (convo_tracker >= 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void biogenic_crazyguy_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_crazyguy_action_set_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.crazy_convo", 1);
    }
    public void biogenic_crazyguy_action_set_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.crazy_convo", 2);
    }
    public void biogenic_crazyguy_action_set_mood(obj_id player, obj_id npc) throws InterruptedException
    {
        setAnimationMood(npc, chat.MOOD_NERVOUS);
        faceToBehavior(npc, player);
    }
    public void biogenic_crazyguy_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_crazyguy");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "npc.conversation.biogenic_crazyguy");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_crazyguy_condition_get_tracker_2(player, self))
        {
            biogenic_crazyguy_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_3a0e3163");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_crazyguy_condition_get_tracker_1(player, self))
        {
            biogenic_crazyguy_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_4e52ced2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a64c5d9e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9499432");
                }
                setObjVar(player, "conversation.biogenic_crazyguy.branchId", 2);
                npcStartConversation(player, self, "biogenic_crazyguy", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (biogenic_crazyguy_condition__defaultCondition(player, self))
        {
            biogenic_crazyguy_action_set_mood(player, self);
            string_id message = new string_id(c_stringFile, "s_80c190c3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e7953fb5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f6dd63d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e5f4a149");
                }
                setObjVar(player, "conversation.biogenic_crazyguy.branchId", 15);
                npcStartConversation(player, self, "biogenic_crazyguy", message, responses);
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
        if (!conversationId.equals("biogenic_crazyguy"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_crazyguy.branchId");
        if (branchId == 2 && response.equals("s_a64c5d9e"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b11e13b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9f69fb7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64bc92a0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc90aebb");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey, I remember you... but I've never seen you before.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_9499432"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c182d8fb");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey, I remember you... but I've never seen you before.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_a9f69fb7"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d166b780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1fbb5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5e04dc5");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, I'm glad you're back. Now go.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_64bc92a0"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b18aca61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be321a24");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, I'm glad you're back. Now go.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_cc90aebb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_40626e0f");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, I'm glad you're back. Now go.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_e1fbb5ed"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14b63e24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a82cfa");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_d5e04dc5"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_51edfa93");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_1c8bddbb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                biogenic_crazyguy_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_91c86ae2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b830d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd7a670e");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_f0a82cfa"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c875e4f0");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_6b830d"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1300ed43");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_fd7a670e"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68d073fe");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_be321a24"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d4f8520");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What isn't happening? You, that's what... why? Go now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_e7953fb5"))
        {
            biogenic_crazyguy_action_set_tracker_1(player, self);
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_74fbbad2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9c653067");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_daa5a74d");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '*gurk* Why... you're here? Did they send you? I see that you're here.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_f6dd63d"))
        {
            biogenic_crazyguy_action_set_tracker_1(player, self);
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eb2f9960");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5d16248c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e34d6272");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8cf80a2");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '*gurk* Why... you're here? Did they send you? I see that you're here.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_e5f4a149"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d3b52482");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '*gurk* Why... you're here? Did they send you? I see that you're here.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_9c653067"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eb2f9960");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5d16248c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e34d6272");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8cf80a2");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look around... do you see it? That's, uh... what is?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_daa5a74d"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_23bcaf58");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look around... do you see it? That's, uh... what is?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_5d16248c"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_524005c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9f69fb7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64bc92a0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc90aebb");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'HELP! HELP! Are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_e34d6272"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1c4a552c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d4e91e1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_564ba87");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70a572af");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'HELP! HELP! Are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_a8cf80a2"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3e250a");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'HELP! HELP! Are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_a9f69fb7"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d166b780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1fbb5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5e04dc5");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_64bc92a0"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b18aca61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be321a24");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_cc90aebb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_40626e0f");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_e1fbb5ed"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14b63e24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a82cfa");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_d5e04dc5"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_51edfa93");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_1c8bddbb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                biogenic_crazyguy_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_91c86ae2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b830d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd7a670e");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_f0a82cfa"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c875e4f0");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_6b830d"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1300ed43");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_fd7a670e"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68d073fe");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_be321a24"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d4f8520");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What isn't happening? You, that's what... why? Go now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_7d4e91e1"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_524005c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9f69fb7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64bc92a0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc90aebb");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, who is not here. That is... the problem. I think? Are you ...who?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_564ba87"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_469eaf3a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d87072f3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3157939");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, who is not here. That is... the problem. I think? Are you ...who?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_70a572af"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bc3d132b");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, who is not here. That is... the problem. I think? Are you ...who?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_a9f69fb7"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d166b780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1fbb5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5e04dc5");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_64bc92a0"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b18aca61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be321a24");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_cc90aebb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_40626e0f");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_e1fbb5ed"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14b63e24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a82cfa");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_d5e04dc5"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_51edfa93");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_1c8bddbb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                biogenic_crazyguy_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_91c86ae2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b830d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd7a670e");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_f0a82cfa"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c875e4f0");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && response.equals("s_6b830d"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1300ed43");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && response.equals("s_fd7a670e"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68d073fe");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && response.equals("s_be321a24"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d4f8520");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What isn't happening? You, that's what... why? Go now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && response.equals("s_d87072f3"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5d995d79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20413fbf");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, not going on... going in, and when you've done that... we all go out.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && response.equals("s_3157939"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_28eb431a");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, not going on... going in, and when you've done that... we all go out.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && response.equals("s_20413fbf"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_72870486");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Life is not all about... rhyming. And soon it will be over.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && response.equals("s_5d16248c"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_524005c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9f69fb7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64bc92a0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc90aebb");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'HELP! HELP! Are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && response.equals("s_e34d6272"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1c4a552c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d4e91e1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_564ba87");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70a572af");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'HELP! HELP! Are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && response.equals("s_a8cf80a2"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3e250a");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'HELP! HELP! Are you here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && response.equals("s_a9f69fb7"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d166b780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1fbb5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5e04dc5");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && response.equals("s_64bc92a0"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b18aca61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be321a24");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && response.equals("s_cc90aebb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_40626e0f");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && response.equals("s_e1fbb5ed"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14b63e24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a82cfa");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && response.equals("s_d5e04dc5"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_51edfa93");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && response.equals("s_1c8bddbb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                biogenic_crazyguy_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_91c86ae2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b830d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd7a670e");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && response.equals("s_f0a82cfa"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c875e4f0");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && response.equals("s_6b830d"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1300ed43");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && response.equals("s_fd7a670e"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68d073fe");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && response.equals("s_be321a24"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d4f8520");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What isn't happening? You, that's what... why? Go now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && response.equals("s_7d4e91e1"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_524005c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9f69fb7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64bc92a0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc90aebb");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, who is not here. That is... the problem. I think? Are you ...who?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && response.equals("s_564ba87"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_469eaf3a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d87072f3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3157939");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, who is not here. That is... the problem. I think? Are you ...who?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && response.equals("s_70a572af"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bc3d132b");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, who is not here. That is... the problem. I think? Are you ...who?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && response.equals("s_a9f69fb7"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d166b780");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1fbb5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5e04dc5");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && response.equals("s_64bc92a0"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b18aca61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be321a24");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && response.equals("s_cc90aebb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_40626e0f");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! Yes you are! I see you... now you must go!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && response.equals("s_e1fbb5ed"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14b63e24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a82cfa");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && response.equals("s_d5e04dc5"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_51edfa93");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Go? No, I want you to leave... in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && response.equals("s_1c8bddbb"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                biogenic_crazyguy_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_91c86ae2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b830d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd7a670e");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && response.equals("s_f0a82cfa"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c875e4f0");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes! I see you are... the truth. When you leave... we all can go. But I must tell you...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && response.equals("s_6b830d"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1300ed43");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && response.equals("s_fd7a670e"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68d073fe");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'For the monsters... 32281 is it. You see, 32281... monsters. There are 32281 monsters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && response.equals("s_be321a24"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d4f8520");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What isn't happening? You, that's what... why? Go now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && response.equals("s_d87072f3"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5d995d79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_crazyguy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20413fbf");
                    }
                    setObjVar(player, "conversation.biogenic_crazyguy.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, not going on... going in, and when you've done that... we all go out.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && response.equals("s_3157939"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_28eb431a");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, not going on... going in, and when you've done that... we all go out.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && response.equals("s_20413fbf"))
        {
            if (biogenic_crazyguy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_72870486");
                removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Life is not all about... rhyming. And soon it will be over.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_crazyguy.branchId");
        return SCRIPT_CONTINUE;
    }
}
