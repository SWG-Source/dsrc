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

public class biogenic_scientist_generic_02 extends script.base_script
{
    public biogenic_scientist_generic_02()
    {
    }
    public static String c_stringFile = "conversation/biogenic_scientist_generic_02";
    public boolean biogenic_scientist_generic_02_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_scientist_generic_02_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_generic_two_convo");
        if (tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_scientist_generic_02_condition_get_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_generic_two_convo");
        if (tracker == 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void biogenic_scientist_generic_02_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_scientist_generic_02_action_set_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_generic_two_convo", 1);
    }
    public void biogenic_scientist_generic_02_action_set_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_generic_two_convo", 2);
    }
    public void biogenic_scientist_generic_02_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_scientist_generic_02");
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
        detachScript(self, "npc.conversation.biogenic_scientist_generic_02");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_scientist_generic_02_condition_get_tracker_1(player, self))
        {
            biogenic_scientist_generic_02_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_1efc7a3c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_generic_02_condition_get_tracker_2(player, self))
        {
            biogenic_scientist_generic_02_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_4a7c5f71");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ff058355");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a31ce3f3");
                }
                setObjVar(player, "conversation.biogenic_scientist_generic_02.branchId", 2);
                npcStartConversation(player, self, "biogenic_scientist_generic_02", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
        {
            biogenic_scientist_generic_02_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_25dab86a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3cfc62f2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f2a471b5");
                }
                setObjVar(player, "conversation.biogenic_scientist_generic_02.branchId", 5);
                npcStartConversation(player, self, "biogenic_scientist_generic_02", message, responses);
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
        if (!conversationId.equals("biogenic_scientist_generic_02"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
        if (branchId == 2 && response.equals("s_ff058355"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_02_action_set_tracker_1(player, self);
                string_id message = new string_id(c_stringFile, "s_ab3423ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f89f44ba");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787513b2");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_02.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm sorry that you've decided not to help us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_a31ce3f3"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5f8b04be");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm sorry that you've decided not to help us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_f89f44ba"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_729e1a36");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, thank you. We are all in your debt. Many of the creatures have been locked inside the main cage area, but someone needs to go in there and finish them off. You can unlock the security door with the code 52577.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_787513b2"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fe7a5e25");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, thank you. We are all in your debt. Many of the creatures have been locked inside the main cage area, but someone needs to go in there and finish them off. You can unlock the security door with the code 52577.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_3cfc62f2"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_02_action_set_tracker_1(player, self);
                string_id message = new string_id(c_stringFile, "s_ab3423ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f89f44ba");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787513b2");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_02.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you. Many thanks indeed. But there are still more of those creatures. You must help us kill them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_f2a471b5"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_02_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_c0da7db8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_138d31dc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8da0f5e3");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_02.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you. Many thanks indeed. But there are still more of those creatures. You must help us kill them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_f89f44ba"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_729e1a36");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, thank you. We are all in your debt. Many of the creatures have been locked inside the main cage area, but someone needs to go in there and finish them off. You can unlock the security door with the code 52577.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_787513b2"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fe7a5e25");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, thank you. We are all in your debt. Many of the creatures have been locked inside the main cage area, but someone needs to go in there and finish them off. You can unlock the security door with the code 52577.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_138d31dc"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_02_action_set_tracker_1(player, self);
                string_id message = new string_id(c_stringFile, "s_ab3423ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f89f44ba");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787513b2");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_02.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, unfortunately we did create this problem. But now it has gotten out of control, and the lives of everyone in this complex are in the balance. Won't you please help us?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_8da0f5e3"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7394ff2a");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, unfortunately we did create this problem. But now it has gotten out of control, and the lives of everyone in this complex are in the balance. Won't you please help us?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_f89f44ba"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_729e1a36");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, thank you. We are all in your debt. Many of the creatures have been locked inside the main cage area, but someone needs to go in there and finish them off. You can unlock the security door with the code 52577.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_787513b2"))
        {
            if (biogenic_scientist_generic_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fe7a5e25");
                removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, thank you. We are all in your debt. Many of the creatures have been locked inside the main cage area, but someone needs to go in there and finish them off. You can unlock the security door with the code 52577.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_scientist_generic_02.branchId");
        return SCRIPT_CONTINUE;
    }
}
