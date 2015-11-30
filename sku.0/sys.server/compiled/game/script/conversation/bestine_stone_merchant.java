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
import script.library.weapons;

public class bestine_stone_merchant extends script.base_script
{
    public bestine_stone_merchant()
    {
    }
    public static String c_stringFile = "conversation/bestine_stone_merchant";
    public boolean bestine_stone_merchant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean bestine_stone_merchant_condition_SearchForStones(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/carved_stone.iff"))
        {
            return true;
        }
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/smooth_stone.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean bestine_stone_merchant_condition_SearchforSmooth(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/smooth_stone.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean bestine_stone_merchant_condition_SearchforCarved(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/carved_stone.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean bestine_stone_merchant_condition_SearchforStoneObjs(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.carvedstonereward"))
        {
            return true;
        }
        if (hasObjVar(player, "bestine.smoothstonereward"))
        {
            return true;
        }
        return false;
    }
    public void bestine_stone_merchant_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void bestine_stone_merchant_action_GiveCarvedStoneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        String REWARD = "object/tangible/wearables/armor/marauder/armor_marauder_s02_chest_plate_quest.iff";
        if (isIdValid(player))
        {
            obj_id objInventory = utils.getInventoryContainer(player);
            if (isIdValid(objInventory))
            {
                obj_id[] objContents = utils.getContents(objInventory);
                if (objContents != null)
                {
                    for (int intI = 0; intI < objContents.length; intI++)
                    {
                        if (isIdValid(objContents[intI]))
                        {
                            String strItemTemplate = getTemplateName(objContents[intI]);
                            if (strItemTemplate.equals("object/tangible/loot/quest/carved_stone.iff"))
                            {
                                destroyObject(objContents[intI]);
                                obj_id item = createObject(REWARD, objInventory, "");
                                setObjVar(player, "bestine.carvedstonereward", true);
                                return;
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public void bestine_stone_merchant_action_GiveSmoothStoneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        String REWARD = "object/weapon/ranged/carbine/carbine_e11_victor_quest.iff";
        if (isIdValid(player))
        {
            obj_id objInventory = utils.getInventoryContainer(player);
            if (isIdValid(objInventory))
            {
                obj_id[] objContents = utils.getContents(objInventory);
                if (objContents != null)
                {
                    for (int intI = 0; intI < objContents.length; intI++)
                    {
                        if (isIdValid(objContents[intI]))
                        {
                            String strItemTemplate = getTemplateName(objContents[intI]);
                            if (strItemTemplate.equals("object/tangible/loot/quest/smooth_stone.iff"))
                            {
                                destroyObject(objContents[intI]);
                                obj_id item = weapons.createWeapon(REWARD, objInventory, rand(0.8f, 1.1f));
                                setObjVar(player, "bestine.carvedstonereward", true);
                                return;
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.bestine_stone_merchant");
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
        detachScript(self, "npc.conversation.bestine_stone_merchant");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (bestine_stone_merchant_condition_SearchforStoneObjs(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f56c7c9f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (bestine_stone_merchant_condition_SearchforCarved(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (bestine_stone_merchant_condition_SearchforSmooth(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b6e7bea1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_af25c425");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eaee4954");
                }
                setObjVar(player, "conversation.bestine_stone_merchant.branchId", 1);
                npcStartConversation(player, self, "bestine_stone_merchant", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (bestine_stone_merchant_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8762f9f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (bestine_stone_merchant_condition_SearchForStones(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!bestine_stone_merchant_condition_SearchForStones(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_98e55c89");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2b5e768f");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4242e25b");
                }
                setObjVar(player, "conversation.bestine_stone_merchant.branchId", 3);
                npcStartConversation(player, self, "bestine_stone_merchant", message, responses);
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
        if (!conversationId.equals("bestine_stone_merchant"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.bestine_stone_merchant.branchId");
        if (branchId == 1 && response.equals("s_b6e7bea1"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cc49587f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (bestine_stone_merchant_condition_SearchforCarved(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (bestine_stone_merchant_condition_SearchforSmooth(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (bestine_stone_merchant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211ab5b4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38d8cf30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c741032");
                    }
                    setObjVar(player, "conversation.bestine_stone_merchant.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Do you have more stones for me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_af25c425"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cc49587f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (bestine_stone_merchant_condition_SearchforCarved(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (bestine_stone_merchant_condition_SearchforSmooth(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (bestine_stone_merchant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211ab5b4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38d8cf30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c741032");
                    }
                    setObjVar(player, "conversation.bestine_stone_merchant.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Do you have more stones for me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_eaee4954"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b04b4ef5");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Do you have more stones for me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_98e55c89"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e21cea57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (bestine_stone_merchant_condition_SearchforCarved(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (bestine_stone_merchant_condition_SearchforSmooth(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6f71f8a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_af35650e");
                    }
                    setObjVar(player, "conversation.bestine_stone_merchant.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in the business of collecting rare stones. Unfortunately, I haven't had much business lately. I guess I should find another business.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_2b5e768f"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9e6e06eb");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in the business of collecting rare stones. Unfortunately, I haven't had much business lately. I guess I should find another business.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_4242e25b"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1cb401b5");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in the business of collecting rare stones. Unfortunately, I haven't had much business lately. I guess I should find another business.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_b6f71f8a"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cc49587f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (bestine_stone_merchant_condition_SearchforCarved(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (bestine_stone_merchant_condition_SearchforSmooth(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (bestine_stone_merchant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211ab5b4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38d8cf30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c741032");
                    }
                    setObjVar(player, "conversation.bestine_stone_merchant.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You do?! What kind of stones?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_af35650e"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cc49587f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (bestine_stone_merchant_condition_SearchforCarved(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (bestine_stone_merchant_condition_SearchforSmooth(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (bestine_stone_merchant_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211ab5b4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38d8cf30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c741032");
                    }
                    setObjVar(player, "conversation.bestine_stone_merchant.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You do?! What kind of stones?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_211ab5b4"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                bestine_stone_merchant_action_GiveCarvedStoneReward(player, self);
                string_id message = new string_id(c_stringFile, "s_bb2c2468");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wow! That's just what I'm looking for! I can take it off your hands if you want. I need to test every one very carefully. So what do you say? Which one do you want to give me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_38d8cf30"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                bestine_stone_merchant_action_GiveSmoothStoneReward(player, self);
                string_id message = new string_id(c_stringFile, "s_bb2c2468");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wow! That's just what I'm looking for! I can take it off your hands if you want. I need to test every one very carefully. So what do you say? Which one do you want to give me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_8c741032"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d7a9bace");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wow! That's just what I'm looking for! I can take it off your hands if you want. I need to test every one very carefully. So what do you say? Which one do you want to give me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_211ab5b4"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                bestine_stone_merchant_action_GiveCarvedStoneReward(player, self);
                string_id message = new string_id(c_stringFile, "s_bb2c2468");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wow! That's just what I'm looking for! I can take it off your hands if you want. I need to test every one very carefully. So what do you say? Which one do you want to give me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_38d8cf30"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                bestine_stone_merchant_action_GiveSmoothStoneReward(player, self);
                string_id message = new string_id(c_stringFile, "s_bb2c2468");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wow! That's just what I'm looking for! I can take it off your hands if you want. I need to test every one very carefully. So what do you say? Which one do you want to give me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_8c741032"))
        {
            if (bestine_stone_merchant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d7a9bace");
                removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Wow! That's just what I'm looking for! I can take it off your hands if you want. I need to test every one very carefully. So what do you say? Which one do you want to give me?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.bestine_stone_merchant.branchId");
        return SCRIPT_CONTINUE;
    }
}
