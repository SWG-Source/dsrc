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

public class village_elder_4 extends script.base_script
{
    public village_elder_4()
    {
    }
    public static String c_stringFile = "conversation/village_elder_4";
    public boolean village_elder_4_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean village_elder_4_condition_needCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplateInBankOrInventory(player, "object/tangible/loot/quest/force_sensitive/force_crystal.iff"))
        {
            return false;
        }
        return true;
    }
    public void village_elder_4_action_giveCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        createObjectInInventoryAllowOverload("object/tangible/loot/quest/force_sensitive/force_crystal.iff", player);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.village_elder_4");
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
        detachScript(self, "npc.conversation.village_elder_4");
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
        if (village_elder_4_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8aa931e3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (village_elder_4_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e5a75b57");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a098d669");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9dc8bf5d");
                }
                setObjVar(player, "conversation.village_elder_4.branchId", 1);
                npcStartConversation(player, self, "village_elder_4", message, responses);
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
        if (!conversationId.equals("village_elder_4"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.village_elder_4.branchId");
        if (branchId == 1 && response.equals("s_e5a75b57"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_448b67d0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aba5c1a8");
                    }
                    setObjVar(player, "conversation.village_elder_4.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_4.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Be careful, we've received information that the Sith Shadows will attack soon.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_a098d669"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d1431da3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de1f69ee");
                    }
                    setObjVar(player, "conversation.village_elder_4.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_4.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Be careful, we've received information that the Sith Shadows will attack soon.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_9dc8bf5d"))
        {
            if (village_elder_4_condition_needCrystal(player, self))
            {
                village_elder_4_action_giveCrystal(player, self);
                string_id message = new string_id(c_stringFile, "s_bdd3bae5");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!village_elder_4_condition_needCrystal(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_199fe2af");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Be careful, we've received information that the Sith Shadows will attack soon.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_aba5c1a8"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_12b92ec4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17446822");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f24ae96");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d4da25b");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97092ac2");
                    }
                    setObjVar(player, "conversation.village_elder_4.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_4.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They seem to be our bane. For some reason they assaulted our village many days ago. We've been rebuilding and reinforcing our village ever since.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_17446822"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ae755ac3");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_f24ae96"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e0741a0c");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_4d4da25b"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8c109ae0");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_97092ac2"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_48fd623");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_de1f69ee"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_12b92ec4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (village_elder_4_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17446822");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f24ae96");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d4da25b");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97092ac2");
                    }
                    setObjVar(player, "conversation.village_elder_4.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_4.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Apparently not. I think they are looking to finish us off this time.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_17446822"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ae755ac3");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_f24ae96"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e0741a0c");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_4d4da25b"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8c109ae0");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_97092ac2"))
        {
            if (village_elder_4_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_48fd623");
                removeObjVar(player, "conversation.village_elder_4.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I sure hope so, but I think we're going to need more help than just the shield generator and blaster turrets.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.village_elder_4.branchId");
        return SCRIPT_CONTINUE;
    }
}
