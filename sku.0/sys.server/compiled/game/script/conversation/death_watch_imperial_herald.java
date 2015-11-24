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
import script.library.factions;
import script.library.utils;

public class death_watch_imperial_herald extends script.base_script
{
    public death_watch_imperial_herald()
    {
    }
    public static String c_stringFile = "conversation/death_watch_imperial_herald";
    public boolean death_watch_imperial_herald_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_imperial_herald_condition_ImperialCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_imperial_herald_condition_BloodCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/death_watch_bunker/blood_vial.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_imperial_herald_condition_RewardCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.imperialfinish"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_imperial_herald_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Death_Watch");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_imperial_herald_condition_QuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch_herald.imperialfinish"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_imperial_herald_condition_ReturnCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch_herald.imperialquest"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_imperial_herald_condition_notImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || !playerFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public void death_watch_imperial_herald_action_RewardGive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.imperialfinish"))
        {
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
                                if (strItemTemplate.equals("object/tangible/loot/dungeon/death_watch_bunker/blood_vial.iff"))
                                {
                                    destroyObject(objContents[intI]);
                                    factions.addFactionStanding(player, "Imperial", 500);
                                    setObjVar(player, "death_watch_herald.imperialfinish", true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void death_watch_imperial_herald_action_Questaccept(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "death_watch_herald.imperialquest", true);
        setObjVar(player, "death_watch_herald.destroy", true);
    }
    public void death_watch_imperial_herald_action_QuestRemove(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "death_watch_herald.imperialquest");
        removeObjVar(player, "death_watch_herald.destroy");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.death_watch_imperial_herald");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Commander D`krn");
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
        detachScript(self, "npc.conversation.death_watch_imperial_herald");
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
        if (death_watch_imperial_herald_condition_dungeonInactive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2e8ff1b4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_imperial_herald_condition_QuestComplete(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_68732e54");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aaed8520");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5bc39fc9");
                }
                setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 2);
                npcStartConversation(player, self, "death_watch_imperial_herald", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_imperial_herald_condition_ReturnCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1a897bb0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_imperial_herald_condition_BloodCheck(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4f1e472e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55aa1278");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f70821a3");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dc839e59");
                }
                setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 5);
                npcStartConversation(player, self, "death_watch_imperial_herald", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_imperial_herald_condition_notImperial(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d441c51a");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_imperial_herald_condition_ImperialCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_527f9e64");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8107be6d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_210b4c5b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ecc5dd3c");
                }
                setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 12);
                npcStartConversation(player, self, "death_watch_imperial_herald", message, responses);
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
        if (!conversationId.equals("death_watch_imperial_herald"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.death_watch_imperial_herald.branchId");
        if (branchId == 2 && response.equals("s_aaed8520"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_406804c7");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Soldier. How may I help you? You are well viewed in the eyes of the Empire for your help earlier.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5bc39fc9"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_16f71658");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Soldier. How may I help you? You are well viewed in the eyes of the Empire for your help earlier.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_4f1e472e"))
        {
            if (!death_watch_imperial_herald_condition_RewardCheck(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b2cb2cbf");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_imperial_herald_condition_RewardCheck(player, self))
            {
                death_watch_imperial_herald_action_RewardGive(player, self);
                string_id message = new string_id(c_stringFile, "s_2c258e47");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Did you complete your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_55aa1278"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4e028860");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Did you complete your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_f70821a3"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                death_watch_imperial_herald_action_QuestRemove(player, self);
                string_id message = new string_id(c_stringFile, "s_79b531ae");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Did you complete your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_dc839e59"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_382404f");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Did you complete your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_8107be6d"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71f459c7");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, a fellow member of the glorious Empire! You came just in time. We need one of our trusted soldiers to fetch something for us. It is a chance, not only to fulfill a difficult mission and receive praise from the Empire, but to gather information regarding the Death Watch and their point of operations. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_210b4c5b"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_126d6e08");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43908466");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e62fc90c");
                    }
                    setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, a fellow member of the glorious Empire! You came just in time. We need one of our trusted soldiers to fetch something for us. It is a chance, not only to fulfill a difficult mission and receive praise from the Empire, but to gather information regarding the Death Watch and their point of operations. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_ecc5dd3c"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1b104a9f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6decea2a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6405e2a0");
                    }
                    setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, a fellow member of the glorious Empire! You came just in time. We need one of our trusted soldiers to fetch something for us. It is a chance, not only to fulfill a difficult mission and receive praise from the Empire, but to gather information regarding the Death Watch and their point of operations. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_43908466"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_622c3f31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6b25f13");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f61ed984");
                    }
                    setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent, soldier! You'll need to find the Death Watch hideout on Endor. It can be located in the northwestern part of the planet. We need for you to find their leader and bring a sample of his blood back to us. You will be well rewarded.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_e62fc90c"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                death_watch_imperial_herald_action_Questaccept(player, self);
                string_id message = new string_id(c_stringFile, "s_e478107c");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent, soldier! You'll need to find the Death Watch hideout on Endor. It can be located in the northwestern part of the planet. We need for you to find their leader and bring a sample of his blood back to us. You will be well rewarded.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_b6b25f13"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                death_watch_imperial_herald_action_Questaccept(player, self);
                string_id message = new string_id(c_stringFile, "s_732f795f");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A soldier shouldn't ask his commanding officer questions! But if you must know... The Empire seems to think that leader of the Death Watch would make a good candidate for a Prime Clone for a new batch of stormtroopers. However, we'll need for you to kill this so-called leader. There's no sense in keeping him around. We've learned our lesson from the past. Well. Will you accept your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_f61ed984"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2979d13");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A soldier shouldn't ask his commanding officer questions! But if you must know... The Empire seems to think that leader of the Death Watch would make a good candidate for a Prime Clone for a new batch of stormtroopers. However, we'll need for you to kill this so-called leader. There's no sense in keeping him around. We've learned our lesson from the past. Well. Will you accept your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_6decea2a"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_126d6e08");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43908466");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e62fc90c");
                    }
                    setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A well-trained group of killers and thieves. If I were you, I'd be careful. It would be smart to bring along a few of your friends, but it's up to you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_6405e2a0"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71f459c7");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A well-trained group of killers and thieves. If I were you, I'd be careful. It would be smart to bring along a few of your friends, but it's up to you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_43908466"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_622c3f31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_imperial_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6b25f13");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f61ed984");
                    }
                    setObjVar(player, "conversation.death_watch_imperial_herald.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent, soldier! You'll need to find the Death Watch hideout on Endor. It can be located in the northwestern part of the planet. We need for you to find their leader and bring a sample of his blood back to us. You will be well rewarded.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_e62fc90c"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                death_watch_imperial_herald_action_Questaccept(player, self);
                string_id message = new string_id(c_stringFile, "s_e478107c");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent, soldier! You'll need to find the Death Watch hideout on Endor. It can be located in the northwestern part of the planet. We need for you to find their leader and bring a sample of his blood back to us. You will be well rewarded.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_b6b25f13"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                death_watch_imperial_herald_action_Questaccept(player, self);
                string_id message = new string_id(c_stringFile, "s_732f795f");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A soldier shouldn't ask his commanding officer questions! But if you must know... The Empire seems to think that leader of the Death Watch would make a good candidate for a Prime Clone for a new batch of stormtroopers. However, we'll need for you to kill this so-called leader. There's no sense in keeping him around. We've learned our lesson from the past. Well. Will you accept your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_f61ed984"))
        {
            if (death_watch_imperial_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2979d13");
                removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A soldier shouldn't ask his commanding officer questions! But if you must know... The Empire seems to think that leader of the Death Watch would make a good candidate for a Prime Clone for a new batch of stormtroopers. However, we'll need for you to kill this so-called leader. There's no sense in keeping him around. We've learned our lesson from the past. Well. Will you accept your mission?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.death_watch_imperial_herald.branchId");
        return SCRIPT_CONTINUE;
    }
}
