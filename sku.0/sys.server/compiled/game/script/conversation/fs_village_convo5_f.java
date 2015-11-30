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

public class fs_village_convo5_f extends script.base_script
{
    public fs_village_convo5_f()
    {
    }
    public static String c_stringFile = "conversation/fs_village_convo5_f";
    public boolean fs_village_convo5_f_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_village_convo5_f");
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
        detachScript(self, "npc.conversation.fs_village_convo5_f");
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
        if (fs_village_convo5_f_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_afe7471");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5f65644b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f02cea2");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1aebf838");
                }
                setObjVar(player, "conversation.fs_village_convo5_f.branchId", 1);
                npcStartConversation(player, self, "fs_village_convo5_f", message, responses);
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
        if (!conversationId.equals("fs_village_convo5_f"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_village_convo5_f.branchId");
        if (branchId == 1 && response.equals("s_5f65644b"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fb77af66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6bbd64da");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you really from another planet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_f02cea2"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_55a1b0c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4998e85");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f8f7c983");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you really from another planet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_1aebf838"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e6772f13");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you really from another planet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_6bbd64da"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_de00afa9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4ca664e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71d67ffc");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yes. I want to be a spaceship pilot. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_c4ca664e"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1c688d82");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well I'm just a farmer, I've never heard of a farmer becoming a spaceship pilot.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_71d67ffc"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_42584442");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well I'm just a farmer, I've never heard of a farmer becoming a spaceship pilot.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_f4998e85"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_38f5722b");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're not from the village, I would have seen you before.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_f8f7c983"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ef3f73aa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_409c13d6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66242968");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're not from the village, I would have seen you before.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_409c13d6"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_38602b56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126e325b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f04bc948");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f6fc4ef7");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I bet. not much here except for the prison and the nightsisters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_66242968"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_97efc39d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98eb2667");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63df3a78");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I bet. not much here except for the prison and the nightsisters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_126e325b"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eca871b0");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There is a prison Northwest of here. when I was real little they say a man came here from the prison. We helped him and now he is safe.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_f04bc948"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d6ef87a");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There is a prison Northwest of here. when I was real little they say a man came here from the prison. We helped him and now he is safe.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_f6fc4ef7"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_900f01d1");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'There is a prison Northwest of here. when I was real little they say a man came here from the prison. We helped him and now he is safe.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_98eb2667"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_df5087e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo5_f_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3b00b466");
                    }
                    setObjVar(player, "conversation.fs_village_convo5_f.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They are witches that use evil magic. I hear the adults talking about them once in a while. They always say they are going to force us to do something, but I can never figure out what.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_63df3a78"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a78b5781");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They are witches that use evil magic. I hear the adults talking about them once in a while. They always say they are going to force us to do something, but I can never figure out what.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_3b00b466"))
        {
            if (fs_village_convo5_f_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cb9faa");
                removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I don't know. I just keep hearing them say force this, and force that. I think the adults would stop them from forcing us to do anything though. Sometimes they can do tricks...' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_village_convo5_f.branchId");
        return SCRIPT_CONTINUE;
    }
}
