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
import script.library.utils;

public class biogenic_assistant extends script.base_script
{
    public biogenic_assistant()
    {
    }
    public static String c_stringFile = "conversation/biogenic_assistant";
    public boolean biogenic_assistant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_assistant_condition_get_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.assistant_convo");
        if (tracker == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_assistant_condition_get_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.assistant_convo");
        if (tracker >= 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_assistant_condition_get_construct_tracker_3(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.construction_convo");
        if (tracker == 3)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_assistant_condition_get_engineer_tracker_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.engineer_convo");
        if (tracker == 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_assistant_condition_get_engineer_tracker_3(obj_id player, obj_id npc) throws InterruptedException
    {
        int tracker = getIntObjVar(player, "biogenic.engineer_convo");
        if (tracker == 3)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean biogenic_assistant_condition_get_multi_tracker(obj_id player, obj_id npc) throws InterruptedException
    {
        int eng_tracker = getIntObjVar(player, "biogenic.engineer_convo");
        int con_tracker = getIntObjVar(player, "biogenic.construction_convo");
        int tracker = getIntObjVar(player, "biogenic.assistant_convo");
        if (tracker >= 2)
        {
            if (eng_tracker > 0)
            {
                return false;
            }
            else 
            {
                if (con_tracker == 3)
                {
                    return false;
                }
                else 
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void biogenic_assistant_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_assistant_action_give_datapad(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id datapad = createObject("object/tangible/loot/dungeon/geonosian_mad_bunker/engineering_datapad.iff", playerInv, "");
        setObjVar(player, "biogenic.assistant_convo", 2);
        if (datapad == null)
        {
            debugSpeakMsg(npc, "Failed to create datapad");
        }
    }
    public void biogenic_assistant_action_set_tracker_1(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "biogenic.assistant_convo", 1);
    }
    public void biogenic_assistant_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public void biogenic_assistant_action_give_datapad_3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id datapad = createObject("object/tangible/loot/dungeon/geonosian_mad_bunker/engineering_datapad.iff", playerInv, "");
        setObjVar(player, "biogenic.assistant_convo", 3);
        if (datapad == null)
        {
            debugSpeakMsg(npc, "Failed to create datapad");
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_assistant");
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
        detachScript(self, "npc.conversation.biogenic_assistant");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_assistant_condition_get_engineer_tracker_3(player, self))
        {
            biogenic_assistant_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_25e7c7e7");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_assistant_condition_get_multi_tracker(player, self))
        {
            biogenic_assistant_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_b5c70166");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_assistant_condition_get_tracker_2(player, self))
        {
            biogenic_assistant_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_ae1d4fd6");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_assistant_condition_get_tracker_1(player, self))
        {
            biogenic_assistant_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_bf513acb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_assistant_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80dc0120");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eeaf9e0b");
                }
                setObjVar(player, "conversation.biogenic_assistant.branchId", 4);
                npcStartConversation(player, self, "biogenic_assistant", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (biogenic_assistant_condition__defaultCondition(player, self))
        {
            biogenic_assistant_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_7dfa7256");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (biogenic_assistant_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ea2d9bb0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82bc35bf");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_457410bd");
                }
                setObjVar(player, "conversation.biogenic_assistant.branchId", 11);
                npcStartConversation(player, self, "biogenic_assistant", message, responses);
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
        if (!conversationId.equals("biogenic_assistant"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_assistant.branchId");
        if (branchId == 4 && response.equals("s_80dc0120"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_be849f7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_assistant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114e560");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33eb85b5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eeaf9e0b");
                    }
                    setObjVar(player, "conversation.biogenic_assistant.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_assistant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, hey, you're back. I wonder if I could ask you for a favor?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_eeaf9e0b"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_aa495893");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, hey, you're back. I wonder if I could ask you for a favor?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_114e560"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_b856cb2e");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have these computer codes that I need to get to the engineer, but the creatures attacked me before I could get back to him. I wonder if you could bring the codes to him for me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_33eb85b5"))
        {
            if (biogenic_assistant_condition_get_construct_tracker_3(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_49ef3c27");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad_3(player, self);
                string_id message = new string_id(c_stringFile, "s_c251c56d");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have these computer codes that I need to get to the engineer, but the creatures attacked me before I could get back to him. I wonder if you could bring the codes to him for me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_eeaf9e0b"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_aa495893");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have these computer codes that I need to get to the engineer, but the creatures attacked me before I could get back to him. I wonder if you could bring the codes to him for me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_ea2d9bb0"))
        {
            biogenic_assistant_action_set_tracker_1(player, self);
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c3bd7233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_assistant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89ca9e86");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_761ee240");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72510ac3");
                    }
                    setObjVar(player, "conversation.biogenic_assistant.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_assistant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you for killing those creatures. They've escaped from their cages and they're out of control.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_82bc35bf"))
        {
            biogenic_assistant_action_set_tracker_1(player, self);
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_255de7f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_assistant_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_assistant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_950188fd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ea2d9bb0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81b17df9");
                    }
                    setObjVar(player, "conversation.biogenic_assistant.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_assistant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you for killing those creatures. They've escaped from their cages and they're out of control.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_457410bd"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b2c2992");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you for killing those creatures. They've escaped from their cages and they're out of control.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_89ca9e86"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_b856cb2e");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have the computer codes that the head engineer needs to get the reactor back online. I was on my way back to the engineering room, but I can't possibly make it there with all these creatures loose.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_761ee240"))
        {
            if (biogenic_assistant_condition_get_construct_tracker_3(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_49ef3c27");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad_3(player, self);
                string_id message = new string_id(c_stringFile, "s_c251c56d");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have the computer codes that the head engineer needs to get the reactor back online. I was on my way back to the engineering room, but I can't possibly make it there with all these creatures loose.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_72510ac3"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bbbd5d0f");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have the computer codes that the head engineer needs to get the reactor back online. I was on my way back to the engineering room, but I can't possibly make it there with all these creatures loose.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_950188fd"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_d8a95d57");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not much. This is a genetic research facility, but I'm an engineering assistant. I don't know much about that stuff. But it would seem that when that earthquake hit, it somehow released the creatures from their cages.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_ea2d9bb0"))
        {
            biogenic_assistant_action_set_tracker_1(player, self);
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c3bd7233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!biogenic_assistant_condition_get_engineer_tracker_2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_assistant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89ca9e86");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_761ee240");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72510ac3");
                    }
                    setObjVar(player, "conversation.biogenic_assistant.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_assistant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not much. This is a genetic research facility, but I'm an engineering assistant. I don't know much about that stuff. But it would seem that when that earthquake hit, it somehow released the creatures from their cages.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_81b17df9"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b2c2992");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not much. This is a genetic research facility, but I'm an engineering assistant. I don't know much about that stuff. But it would seem that when that earthquake hit, it somehow released the creatures from their cages.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_89ca9e86"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_b856cb2e");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have the computer codes that the head engineer needs to get the reactor back online. I was on my way back to the engineering room, but I can't possibly make it there with all these creatures loose.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_761ee240"))
        {
            if (biogenic_assistant_condition_get_construct_tracker_3(player, self))
            {
                biogenic_assistant_action_give_datapad(player, self);
                string_id message = new string_id(c_stringFile, "s_49ef3c27");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                biogenic_assistant_action_give_datapad_3(player, self);
                string_id message = new string_id(c_stringFile, "s_c251c56d");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have the computer codes that the head engineer needs to get the reactor back online. I was on my way back to the engineering room, but I can't possibly make it there with all these creatures loose.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_72510ac3"))
        {
            if (biogenic_assistant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bbbd5d0f");
                removeObjVar(player, "conversation.biogenic_assistant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I have the computer codes that the head engineer needs to get the reactor back online. I was on my way back to the engineering room, but I can't possibly make it there with all these creatures loose.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_assistant.branchId");
        return SCRIPT_CONTINUE;
    }
}
