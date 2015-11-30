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

public class biogenic_scientist_generic_01 extends script.base_script
{
    public biogenic_scientist_generic_01()
    {
    }
    public static String c_stringFile = "conversation/biogenic_scientist_generic_01";
    public boolean biogenic_scientist_generic_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_scientist_generic_01_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_generic_one_convo");
        if (tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_scientist_generic_01_condition_get_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_generic_one_convo");
        if (tracker == 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void biogenic_scientist_generic_01_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_scientist_generic_01_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public void biogenic_scientist_generic_01_action_set_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_generic_one_convo", 1);
    }
    public void biogenic_scientist_generic_01_action_set_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_generic_one_convo", 2);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_scientist_generic_01");
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
        detachScript(self, "npc.conversation.biogenic_scientist_generic_01");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_scientist_generic_01_condition_get_tracker_2(player, self))
        {
            biogenic_scientist_generic_01_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_7c08824f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_generic_01_condition_get_tracker_1(player, self))
        {
            biogenic_scientist_generic_01_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_7014bfc2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_431dec6f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a5b3f77");
                }
                setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 4);
                npcStartConversation(player, self, "biogenic_scientist_generic_01", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
        {
            biogenic_scientist_generic_01_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_a1480651");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cc366e42");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e2ab2c6d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_432d5b3d");
                }
                setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 3);
                npcStartConversation(player, self, "biogenic_scientist_generic_01", message, responses);
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
        if (!conversationId.equals("biogenic_scientist_generic_01"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
        if (branchId == 2 && response.equals("s_431dec6f"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6cabcaaf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9bb201");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f78511c");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Please help them... they're trapped in Lab Two. There are monsters in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_a5b3f77"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c6a7e0ad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_431dec6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d153d91");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Please help them... they're trapped in Lab Two. There are monsters in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_cc366e42"))
        {
            biogenic_scientist_generic_01_action_set_tracker_1(player, self);
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4d2b85dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_431dec6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5b3f77");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've got to help them! They're trapped in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_e2ab2c6d"))
        {
            biogenic_scientist_generic_01_action_set_tracker_1(player, self);
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6157f01f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_431dec6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5b3f77");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've got to help them! They're trapped in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_432d5b3d"))
        {
            biogenic_scientist_generic_01_action_set_tracker_1(player, self);
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9acbce38");
                removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've got to help them! They're trapped in there!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_431dec6f"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6cabcaaf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9bb201");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f78511c");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Help them... in Lab Two. They're locked in, with those monsters!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_a5b3f77"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c6a7e0ad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_431dec6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d153d91");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Help them... in Lab Two. They're locked in, with those monsters!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_1e9bb201"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2ebb30d8");
                removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll need the code! 11380... that should open the door. Please go help them!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_4f78511c"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_295ff7c4");
                removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll need the code! 11380... that should open the door. Please go help them!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_431dec6f"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6cabcaaf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9bb201");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f78511c");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No... they'll be dead. Unless we can help. Will you help them?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_4d153d91"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_28b15c94");
                removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No... they'll be dead. Unless we can help. Will you help them?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_431dec6f"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6cabcaaf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9bb201");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f78511c");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The others, in Lab Two. They're locked in, with those monsters!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_a5b3f77"))
        {
            if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c6a7e0ad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_431dec6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d153d91");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_01.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The others, in Lab Two. They're locked in, with those monsters!' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_scientist_generic_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
