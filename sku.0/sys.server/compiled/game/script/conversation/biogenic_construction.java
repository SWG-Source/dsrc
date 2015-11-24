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

public class biogenic_construction extends script.base_script
{
    public biogenic_construction()
    {
    }
    public static String c_stringFile = "conversation/biogenic_construction";
    public boolean biogenic_construction_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_construction_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.construction_convo");
        if (tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_construction_condition_get_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.construction_convo");
        if (tracker >= 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_construction_condition_get_ass_tracker_3(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.assistant_convo");
        if (tracker == 3)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_construction_condition_get_multi_tracker(obj_id player, obj_id npc) throws InterruptedException
    {
        int ass_tracker = getIntObjVar(player, "biogenic.assistant_convo");
        int tracker = getIntObjVar(player, "biogenic.construction_convo");
        if (tracker > 0 && ass_tracker == 3)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void biogenic_construction_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_construction_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public void biogenic_construction_action_give_drill(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_construction_action_set_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
        setObjVar(player, "biogenic.construction_convo", 2);
    }
    public void biogenic_construction_action_set_tracker_3(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
        setObjVar(player, "biogenic.construction_convo", 3);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_construction");
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
        detachScript(self, "npc.conversation.biogenic_construction");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_construction_condition_get_multi_tracker(player, self))
        {
            biogenic_construction_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_575de8f1");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_construction_condition_get_tracker_2(player, self))
        {
            biogenic_construction_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_dc2d689c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_construction_condition_get_tracker_1(player, self))
        {
            biogenic_construction_action_set_tracker_3(player, self);
            string_id message = new string_id(c_stringFile, "s_23041b9a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_construction_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82e89d37");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_add15db6");
                }
                setObjVar(player, "conversation.biogenic_construction.branchId", 3);
                npcStartConversation(player, self, "biogenic_construction", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (biogenic_construction_condition__defaultCondition(player, self))
        {
            biogenic_construction_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_1df6adfc");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e4b06f76");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_134aacec");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_355bc632");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_457410bd");
                }
                setObjVar(player, "conversation.biogenic_construction.branchId", 6);
                npcStartConversation(player, self, "biogenic_construction", message, responses);
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
        if (!conversationId.equals("biogenic_construction"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_construction.branchId");
        if (branchId == 3 && response.equals("s_82e89d37"))
        {
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d8420164");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you have a chance, you might want to go check out the main engineering area. It looks like the power core might be damaged or offline. If the door is locked, the code 51892 will get you in.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_add15db6"))
        {
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2453dda8");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you have a chance, you might want to go check out the main engineering area. It looks like the power core might be damaged or offline. If the door is locked, the code 51892 will get you in.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_e4b06f76"))
        {
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_give_drill(player, self);
                string_id message = new string_id(c_stringFile, "s_fc129e0e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4136c2df");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5fe23666");
                    }
                    setObjVar(player, "conversation.biogenic_construction.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_construction.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank goodness you got here in time! That spider was going to kill me. I thought for sure I was a goner. How can I ever repay you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_134aacec"))
        {
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_give_drill(player, self);
                string_id message = new string_id(c_stringFile, "s_eb771a95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4136c2df");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5fe23666");
                    }
                    setObjVar(player, "conversation.biogenic_construction.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_construction.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank goodness you got here in time! That spider was going to kill me. I thought for sure I was a goner. How can I ever repay you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_355bc632"))
        {
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_give_drill(player, self);
                string_id message = new string_id(c_stringFile, "s_75118515");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4136c2df");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5fe23666");
                    }
                    setObjVar(player, "conversation.biogenic_construction.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_construction.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank goodness you got here in time! That spider was going to kill me. I thought for sure I was a goner. How can I ever repay you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_457410bd"))
        {
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_give_drill(player, self);
                string_id message = new string_id(c_stringFile, "s_c30b797d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_construction_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4136c2df");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5fe23666");
                    }
                    setObjVar(player, "conversation.biogenic_construction.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_construction.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank goodness you got here in time! That spider was going to kill me. I thought for sure I was a goner. How can I ever repay you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_4136c2df"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_de8e8527");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_1d440dae");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well I'm only interested in getting out of here now.  I'll do that as soon as I get my wits back.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_5fe23666"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_c1d58076");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_67f9970");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well I'm only interested in getting out of here now.  I'll do that as soon as I get my wits back.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_4136c2df"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_de8e8527");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_1d440dae");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Actually, I don't have much to give you. Perhaps I'll think of something later.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_5fe23666"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_c1d58076");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_67f9970");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Actually, I don't have much to give you. Perhaps I'll think of something later.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_4136c2df"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_de8e8527");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_1d440dae");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was working on excavating this passage when the earthquake hit. Not long after, the spiders attacked. Now I just want to get out of here. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_5fe23666"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_c1d58076");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_67f9970");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was working on excavating this passage when the earthquake hit. Not long after, the spiders attacked. Now I just want to get out of here. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_4136c2df"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_de8e8527");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_1d440dae");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yea, I'm getting out of here too. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_5fe23666"))
        {
            if (biogenic_construction_condition_get_ass_tracker_3(player, self))
            {
                biogenic_construction_action_set_tracker_2(player, self);
                string_id message = new string_id(c_stringFile, "s_c1d58076");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_construction_condition__defaultCondition(player, self))
            {
                biogenic_construction_action_set_tracker_3(player, self);
                string_id message = new string_id(c_stringFile, "s_67f9970");
                removeObjVar(player, "conversation.biogenic_construction.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yea, I'm getting out of here too. ' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_construction.branchId");
        return SCRIPT_CONTINUE;
    }
}
