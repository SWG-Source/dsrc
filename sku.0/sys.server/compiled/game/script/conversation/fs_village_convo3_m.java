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

public class fs_village_convo3_m extends script.base_script
{
    public fs_village_convo3_m()
    {
    }
    public static String c_stringFile = "conversation/fs_village_convo3_m";
    public boolean fs_village_convo3_m_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_village_convo3_m");
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
        detachScript(self, "npc.conversation.fs_village_convo3_m");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (fs_village_convo3_m_condition__defaultCondition(player, self))
        {
            doAnimationAction(self, "greet");
            string_id message = new string_id(c_stringFile, "s_a6600b37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ce4f7c2b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_351f9414");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29e5bb06");
                }
                setObjVar(player, "conversation.fs_village_convo3_m.branchId", 1);
                npcStartConversation(player, self, "fs_village_convo3_m", message, responses);
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
        if (!conversationId.equals("fs_village_convo3_m"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_village_convo3_m.branchId");
        if (branchId == 1 && response.equals("s_ce4f7c2b"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_9cabeaee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7ae6df2");
                    }
                    setObjVar(player, "conversation.fs_village_convo3_m.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. What brings you to our village?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_351f9414"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c2be1811");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30d6ee6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b466883");
                    }
                    setObjVar(player, "conversation.fs_village_convo3_m.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. What brings you to our village?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_29e5bb06"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e1ee18d4");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. What brings you to our village?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_f7ae6df2"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a9268707");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Force? Are you on a pilgrimage son? The only Force you'll find around here are those blasted nightsisters and they sure won't welcome any adherents. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_30d6ee6"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_93ec538d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76a2d8f9");
                    }
                    setObjVar(player, "conversation.fs_village_convo3_m.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well we work the land, trade with some few freighter captains, but mostly we stay out of the way.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_2b466883"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4ac0f748");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e14b17f4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da63f16c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_538ba7a9");
                    }
                    setObjVar(player, "conversation.fs_village_convo3_m.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well we work the land, trade with some few freighter captains, but mostly we stay out of the way.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_76a2d8f9"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d31f978");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3683f06");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf2480fb");
                    }
                    setObjVar(player, "conversation.fs_village_convo3_m.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The war mostly. Most of us have lost family or friends to the war. We've resolved to not lose anyone else.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_d3683f06"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_116fa06f");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Most of us have. We've paid our dues in blood and pain. We're done with it. It's one thing to risk your own life in a war, but once you have children it becomes something else entirely to let them do the same.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_cf2480fb"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_60c9d96a");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Most of us have. We've paid our dues in blood and pain. We're done with it. It's one thing to risk your own life in a war, but once you have children it becomes something else entirely to let them do the same.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_e14b17f4"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bf567533");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Would we need a reason other than the war? I mean they blew up a planet! ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_da63f16c"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_671177b7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo3_m_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_21fd07e3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d652c1bf");
                    }
                    setObjVar(player, "conversation.fs_village_convo3_m.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Would we need a reason other than the war? I mean they blew up a planet! ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_538ba7a9"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_42d40a3f");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Would we need a reason other than the war? I mean they blew up a planet! ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_21fd07e3"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5cca5b41");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're right, it might. Then what? Beg them not to fire? Jump in our squadron of X-wings? The only power we have right now is anonymity.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_d652c1bf"))
        {
            if (fs_village_convo3_m_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_69bb013d");
                removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're right, it might. Then what? Beg them not to fire? Jump in our squadron of X-wings? The only power we have right now is anonymity.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_village_convo3_m.branchId");
        return SCRIPT_CONTINUE;
    }
}
