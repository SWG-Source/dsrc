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

public class biogenic_scientist_geonosian extends script.base_script
{
    public biogenic_scientist_geonosian()
    {
    }
    public static String c_stringFile = "conversation/biogenic_scientist_geonosian";
    public boolean biogenic_scientist_geonosian_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_scientist_geonosian_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.scientist_geonosian_convo");
        if (tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void biogenic_scientist_geonosian_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_scientist_geonosian_action_give_schematic(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.scientist_geonosian_convo", 1);
    }
    public void biogenic_scientist_geonosian_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_scientist_geonosian");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "npc.conversation.biogenic_scientist_geonosian");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_scientist_geonosian_condition_get_tracker_1(player, self))
        {
            biogenic_scientist_geonosian_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_5d99d708");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
        {
            biogenic_scientist_geonosian_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_a59fc522");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f612da3");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_720b306c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ab9b9023");
                }
                setObjVar(player, "conversation.biogenic_scientist_geonosian.branchId", 2);
                npcStartConversation(player, self, "biogenic_scientist_geonosian", message, responses);
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
        if (!conversationId.equals("biogenic_scientist_geonosian"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
        if (branchId == 2 && response.equals("s_f612da3"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                biogenic_scientist_geonosian_action_give_schematic(player, self);
                string_id message = new string_id(c_stringFile, "s_3b597458");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52a88e99");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c6bb27a");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_geonosian.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank... thank you for rescuing me, but... I've been hurt... I don't know... if I can make it out...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_720b306c"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                biogenic_scientist_geonosian_action_give_schematic(player, self);
                string_id message = new string_id(c_stringFile, "s_7ddd5bf5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52a88e99");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c6bb27a");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_geonosian.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank... thank you for rescuing me, but... I've been hurt... I don't know... if I can make it out...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_ab9b9023"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                biogenic_scientist_geonosian_action_give_schematic(player, self);
                string_id message = new string_id(c_stringFile, "s_71f20c60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52a88e99");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c6bb27a");
                    }
                    setObjVar(player, "conversation.biogenic_scientist_geonosian.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank... thank you for rescuing me, but... I've been hurt... I don't know... if I can make it out...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_52a88e99"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30d18e8f");
                removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no. But that's OK... I was trying to salvage data from... what's left of the computers. This was... all I could get... before the monsters attacked. You take it... put it to good use...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_4c6bb27a"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30d18e8f");
                removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, no. But that's OK... I was trying to salvage data from... what's left of the computers. This was... all I could get... before the monsters attacked. You take it... put it to good use...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_52a88e99"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30d18e8f");
                removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, I doubt that. But it's OK... I was trying to salvage data from... what's left of the computers. This was... all I could get... before the monsters attacked. You take it... put it to good use...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_4c6bb27a"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30d18e8f");
                removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, I doubt that. But it's OK... I was trying to salvage data from... what's left of the computers. This was... all I could get... before the monsters attacked. You take it... put it to good use...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_52a88e99"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30d18e8f");
                removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wait, wait... Take this... I was trying to salvage data from... what's left of the computers. This was... all I could get... before the monsters attacked... Put it to good use...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_4c6bb27a"))
        {
            if (biogenic_scientist_geonosian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30d18e8f");
                removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wait, wait... Take this... I was trying to salvage data from... what's left of the computers. This was... all I could get... before the monsters attacked... Put it to good use...' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_scientist_geonosian.branchId");
        return SCRIPT_CONTINUE;
    }
}
