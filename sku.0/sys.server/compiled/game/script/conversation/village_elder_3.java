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

public class village_elder_3 extends script.base_script
{
    public village_elder_3()
    {
    }
    public static String c_stringFile = "conversation/village_elder_3";
    public boolean village_elder_3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean village_elder_3_condition_needCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplateInBankOrInventory(player, "object/tangible/loot/quest/force_sensitive/force_crystal.iff"))
        {
            return false;
        }
        return true;
    }
    public void village_elder_3_action_giveCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        createObjectInInventoryAllowOverload("object/tangible/loot/quest/force_sensitive/force_crystal.iff", player);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.village_elder_3");
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
        detachScript(self, "npc.conversation.village_elder_3");
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
        if (village_elder_3_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_b77f095d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (village_elder_3_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78eeb91e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32705167");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9dc8bf5d");
                }
                setObjVar(player, "conversation.village_elder_3.branchId", 1);
                npcStartConversation(player, self, "village_elder_3", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("village_elder_3"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.village_elder_3.branchId");
        if (branchId == 1 && response.equals("s_78eeb91e"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4d973713");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6857cea2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5d8d739e");
                    }
                    setObjVar(player, "conversation.village_elder_3.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Watch out! Someone dropped a box of phase relay filaments around here. They can get into your electronics and short them out. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_32705167"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cc50daed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_afcc98d2");
                    }
                    setObjVar(player, "conversation.village_elder_3.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Watch out! Someone dropped a box of phase relay filaments around here. They can get into your electronics and short them out. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_9dc8bf5d"))
        {
            if (village_elder_3_condition_needCrystal(player, self))
            {
                village_elder_3_action_giveCrystal(player, self);
                string_id message = new string_id(c_stringFile, "s_bdd3bae5");
                removeObjVar(player, "conversation.village_elder_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!village_elder_3_condition_needCrystal(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_199fe2af");
                removeObjVar(player, "conversation.village_elder_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Watch out! Someone dropped a box of phase relay filaments around here. They can get into your electronics and short them out. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_6857cea2"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6acff81");
                removeObjVar(player, "conversation.village_elder_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's what we're doing. Well, building one from scratch really. We've got the beginnings of a Mark 4 shield generator right over there.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5d8d739e"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f5d9b5d4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6aefa2ce");
                    }
                    setObjVar(player, "conversation.village_elder_3.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's what we're doing. Well, building one from scratch really. We've got the beginnings of a Mark 4 shield generator right over there.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_6aefa2ce"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ac2de13b");
                removeObjVar(player, "conversation.village_elder_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Supply convoys mostly. When they can get through the Sith Shadow attacks that is. The margin between what we have and what we need always stays slim, however.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_afcc98d2"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_893aaeea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1b39e83");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf4025df");
                    }
                    setObjVar(player, "conversation.village_elder_3.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We're building a Mark 4 shield generator to defend our village. They are used in that.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_e1b39e83"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_efa47d4e");
                removeObjVar(player, "conversation.village_elder_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Usually I'd laugh at that, friend. No, we actually do have need of it. We were recently assaulted by a gang calling itself the Sith Shadows. They killed many of our people and destroyed our crops and much of the village.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_cf4025df"))
        {
            if (village_elder_3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_abd57d77");
                removeObjVar(player, "conversation.village_elder_3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Usually I'd laugh at that, friend. No, we actually do have need of it. We were recently assaulted by a gang calling itself the Sith Shadows. They killed many of our people and destroyed our crops and much of the village.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.village_elder_3.branchId");
        return SCRIPT_CONTINUE;
    }
}
