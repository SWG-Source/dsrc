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

public class biogenic_scientist_generic_03 extends script.base_script
{
    public biogenic_scientist_generic_03()
    {
    }
    public static String c_stringFile = "conversation/biogenic_scientist_generic_03";
    public boolean biogenic_scientist_generic_03_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_scientist_generic_03_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_generic_three_convo");
        if (tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_scientist_generic_03_condition_get_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_generic_three_convo");
        if (tracker == 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void biogenic_scientist_generic_03_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_scientist_generic_03_action_set_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_generic_three_convo", 1);
    }
    public void biogenic_scientist_generic_03_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public void biogenic_scientist_generic_03_action_set_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_generic_three_convo", 2);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_scientist_generic_03");
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
        detachScript(self, "npc.conversation.biogenic_scientist_generic_03");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_scientist_generic_03_condition_get_tracker_2(player, self))
        {
            biogenic_scientist_generic_03_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_b5665d3a");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_generic_03_condition_get_tracker_1(player, self))
        {
            biogenic_scientist_generic_03_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_3a1a4e80");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b4e9b11d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5b104c75");
                }
                setObjVar(player, "conversation.biogenic_scientist_generic_03.branchId", 2);
                npcStartConversation(player, self, "biogenic_scientist_generic_03", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
        {
            biogenic_scientist_generic_03_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_fda0157f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87bb58ec");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e82d7c74");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95fa4e8");
                }
                setObjVar(player, "conversation.biogenic_scientist_generic_03.branchId", 5);
                npcStartConversation(player, self, "biogenic_scientist_generic_03", message, responses);
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
        if (!conversationId.equals("biogenic_scientist_generic_03"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
        if (branchId == 2 && response.equals("s_b4e9b11d"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a55d1241");
                removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My fault... it's all my fault. I've killed them... I locked them in. They don't know the code...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5b104c75"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_03_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_aed85251");
                removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My fault... it's all my fault. I've killed them... I locked them in. They don't know the code...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_87bb58ec"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_03_action_set_tracker_1(player, self);
                string_id message = new string_id(c_stringFile, "s_611a9cf7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b4e9b11d");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_03.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All my fault... This is all my fault...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_e82d7c74"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_03_action_set_tracker_1(player, self);
                string_id message = new string_id(c_stringFile, "s_64f42fb0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b4e9b11d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5b104c75");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_generic_03.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All my fault... This is all my fault...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_95fa4e8"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5a0551a4");
                removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All my fault... This is all my fault...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b4e9b11d"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a55d1241");
                removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All of this. These creatures... shouldn't have made them so powerful... I was too confident. But I locked them in... with the code...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_b4e9b11d"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a55d1241");
                removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'These creatures... the cages. Shouldn't have made them so powerful. Should have made the cages stronger. But I locked them in... with the code...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_5b104c75"))
        {
            if (biogenic_scientist_generic_03_condition__defaultCondition(player, self))
            {
                biogenic_scientist_generic_03_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_aed85251");
                removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'These creatures... the cages. Shouldn't have made them so powerful. Should have made the cages stronger. But I locked them in... with the code...' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_scientist_generic_03.branchId");
        return SCRIPT_CONTINUE;
    }
}
