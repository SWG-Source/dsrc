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

public class village_elder_1 extends script.base_script
{
    public village_elder_1()
    {
    }
    public static String c_stringFile = "conversation/village_elder_1";
    public boolean village_elder_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean village_elder_1_condition_needCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplateInBankOrInventory(player, "object/tangible/loot/quest/force_sensitive/force_crystal.iff"))
        {
            return false;
        }
        return true;
    }
    public void village_elder_1_action_giveCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        createObjectInInventoryAllowOverload("object/tangible/loot/quest/force_sensitive/force_crystal.iff", player);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.village_elder_1");
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
        detachScript(self, "npc.conversation.village_elder_1");
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
        if (village_elder_1_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_55273aaf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (village_elder_1_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b6101821");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9dc8bf5d");
                }
                setObjVar(player, "conversation.village_elder_1.branchId", 1);
                npcStartConversation(player, self, "village_elder_1", message, responses);
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
        if (!conversationId.equals("village_elder_1"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.village_elder_1.branchId");
        if (branchId == 1 && response.equals("s_b6101821"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_72752682");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5357267d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e477ab86");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did you see them?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_9dc8bf5d"))
        {
            if (village_elder_1_condition_needCrystal(player, self))
            {
                village_elder_1_action_giveCrystal(player, self);
                string_id message = new string_id(c_stringFile, "s_bdd3bae5");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!village_elder_1_condition_needCrystal(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_199fe2af");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did you see them?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5357267d"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_455d6edc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0e7a84");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Those Sith animals! They destroyed our village! They came out of nowhere... overwhelmed our defenses. We thought we were protected... but, we stood no chance.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_e477ab86"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9ce75ab2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a43672b0");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Those Sith animals! They destroyed our village! They came out of nowhere... overwhelmed our defenses. We thought we were protected... but, we stood no chance.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_f0e7a84"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f8ff425b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83ac5888");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2eaf06cc");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_854985e0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_858f16f4");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They aren't real Sith. They just have some strange devotion to that evil race. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_83ac5888"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ab969608");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They sure got it. We're devastated. Most everything is in ruins, and many of our people are hurt.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_2eaf06cc"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7587a16a");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They sure got it. We're devastated. Most everything is in ruins, and many of our people are hurt.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_854985e0"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_47f6c42");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They sure got it. We're devastated. Most everything is in ruins, and many of our people are hurt.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_858f16f4"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_959b429b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c6f20b4");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They sure got it. We're devastated. Most everything is in ruins, and many of our people are hurt.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_3c6f20b4"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_43fe619a");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What we really need is someone to find our captured villagers. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_a43672b0"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f0d5416");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83ac5888");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2eaf06cc");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22163d24");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_858f16f4");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We were actually quite well defended. But they burned right through our shield. The turrets couldn't stop them and were destroyed. Then they reached the walls near the entrances. After that, all that was left was the pillaging and looting.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_83ac5888"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ded598ce");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That they are and the ruins you see here bear witness to that statement.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_2eaf06cc"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7587a16a");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That they are and the ruins you see here bear witness to that statement.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_22163d24"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_47f6c42");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That they are and the ruins you see here bear witness to that statement.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_858f16f4"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_959b429b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (village_elder_1_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c6f20b4");
                    }
                    setObjVar(player, "conversation.village_elder_1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.village_elder_1.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That they are and the ruins you see here bear witness to that statement.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_3c6f20b4"))
        {
            if (village_elder_1_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_43fe619a");
                removeObjVar(player, "conversation.village_elder_1.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What we really need is someone to find our captured villagers. ' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.village_elder_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
