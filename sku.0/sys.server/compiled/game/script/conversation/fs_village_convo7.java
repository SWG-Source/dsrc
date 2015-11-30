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

public class fs_village_convo7 extends script.base_script
{
    public fs_village_convo7()
    {
    }
    public static String c_stringFile = "conversation/fs_village_convo7";
    public boolean fs_village_convo7_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_village_convo7");
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
        detachScript(self, "npc.conversation.fs_village_convo7");
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
        if (fs_village_convo7_condition__defaultCondition(player, self))
        {
            doAnimationAction(self, "forage");
            string_id message = new string_id(c_stringFile, "s_6cf0908b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_village_convo7_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2c9520b6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c7fdb62e");
                }
                setObjVar(player, "conversation.fs_village_convo7.branchId", 1);
                npcStartConversation(player, self, "fs_village_convo7", message, responses);
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
        if (!conversationId.equals("fs_village_convo7"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_village_convo7.branchId");
        if (branchId == 1 && response.equals("s_2c9520b6"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_a9fdde5e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52cf32e2");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We're never going to get our planting done in time with everything else that's going on.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_c7fdb62e"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_76ef710f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a232b8a1");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We're never going to get our planting done in time with everything else that's going on.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_52cf32e2"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_20f19cdd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c031923");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, but we'll never make the planting in time for this season now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_c031923"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_77e48d5b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_af8eece8");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We manage. It takes plenty of high nutrient fertilizer and quite a bit of water, but it can be done.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_af8eece8"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_bc23678f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e00ac98e");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What do you mean?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_e00ac98e"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_c24ab601");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f2c88cd");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'This was too when we arrived. Green and fertile as the eye could see. You should have seen it, as lush a land that has ever been in the galaxy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_9f2c88cd"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_5d7bd117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1bb44cf9");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well we don't really know, but the Elder believes some sort climatological shift occurred and we ended up like this.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_1bb44cf9"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "whisper");
                string_id message = new string_id(c_stringFile, "s_bf08330a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76887815");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well... No, I shouldn't speak of it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_76887815"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fb0e2a0");
                removeObjVar(player, "conversation.fs_village_convo7.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ok, ok. I will tell you. I think we were cursed by those evil Nightsisters. They found out we were in tune with the Force and wanted to destroy us, or at least drive us off.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_a232b8a1"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_50b2b480");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_village_convo7_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ecdfe4b4");
                    }
                    setObjVar(player, "conversation.fs_village_convo7.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_village_convo7.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've been attacked by a group of mercenaries calling themselves the Sith Shadows.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_ecdfe4b4"))
        {
            if (fs_village_convo7_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d948941a");
                removeObjVar(player, "conversation.fs_village_convo7.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I don't think they are really Sith, but they sure believe in the evil ideals of that race. They seem to want us all dead.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_village_convo7.branchId");
        return SCRIPT_CONTINUE;
    }
}
