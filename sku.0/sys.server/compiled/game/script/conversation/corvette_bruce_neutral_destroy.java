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
import script.library.money;
import script.library.space_dungeon;
import script.library.utils;

public class corvette_bruce_neutral_destroy extends script.base_script
{
    public corvette_bruce_neutral_destroy()
    {
    }
    public static String c_stringFile = "conversation/corvette_bruce_neutral_destroy";
    public boolean corvette_bruce_neutral_destroy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_bruce_neutral_destroy_condition_knowsLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation3");
    }
    public boolean corvette_bruce_neutral_destroy_condition_knowsLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation2");
    }
    public boolean corvette_bruce_neutral_destroy_condition_knowsLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation1");
    }
    public boolean corvette_bruce_neutral_destroy_condition_knowsAllLocations(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "corl_corvette.heardLocation1")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation2")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation3")));
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasStuff(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_intel.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler01.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler02.iff")));
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasObject01(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_intel.iff");
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasObject02(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler01.iff");
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasObject03(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler02.iff");
    }
    public boolean corvette_bruce_neutral_destroy_condition_isGm(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "gm");
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasTravelTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
        obj_id[] spaceTickets = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, ticketTemplate);
        if (spaceTickets != null && spaceTickets.length > 0)
        {
            for (int i = 0; i < spaceTickets.length; i++)
            {
                obj_id ticket = spaceTickets[i];
                if (isIdValid(ticket))
                {
                    if ((getStringObjVar(ticket, "space_dungeon.ticket.dungeon")).equals("corvette_neutral"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((!corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, npc)) && (!corvette_bruce_neutral_destroy_condition_hasStuff(player, npc)));
    }
    public boolean corvette_bruce_neutral_destroy_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corl_corvette.neutral_destroy");
    }
    public boolean corvette_bruce_neutral_destroy_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Neutral");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_bruce_neutral_destroy_condition_onDifferentCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            if (!hasObjVar(player, "corl_corvette.neutral_destroy"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean corvette_bruce_neutral_destroy_condition_hasInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space > 0)
            {
                return true;
            }
        }
        return false;
    }
    public boolean corvette_bruce_neutral_destroy_condition_earnedCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corvette.neutral_destroy.finished");
    }
    public void corvette_bruce_neutral_destroy_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void corvette_bruce_neutral_destroy_action_heardLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation1", 1);
        return;
    }
    public void corvette_bruce_neutral_destroy_action_heardLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation2", 1);
        return;
    }
    public void corvette_bruce_neutral_destroy_action_heardLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation3", 1);
        return;
    }
    public void corvette_bruce_neutral_destroy_action_acceptQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "corl_corvette.neutral_destroy"))
        {
            setObjVar(player, "corl_corvette.neutral_destroy", 1);
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU has started the neutral destroy quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        return;
    }
    public void corvette_bruce_neutral_destroy_action_clearLocations(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "corl_corvette.heardLocation1"))
        {
            utils.removeScriptVar(player, "corl_corvette.heardLocation1");
        }
        if (utils.hasScriptVar(player, "corl_corvette.heardLocation2"))
        {
            utils.removeScriptVar(player, "corl_corvette.heardLocation2");
        }
        if (utils.hasScriptVar(player, "corl_corvette.heardLocation3"))
        {
            utils.removeScriptVar(player, "corl_corvette.heardLocation3");
        }
        return;
    }
    public void corvette_bruce_neutral_destroy_action_giveTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_intel.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            obj_id ticket = space_dungeon.createTicket(player, "tatooine", "corvette_neutral_pilot", "corvette_neutral");
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "neutral_destroy");
            setObjVar(ticket, "corl_corvette.ticket_owner", player);
            setObjVar(ticket, "noTrade", true);
            attachScript(ticket, "item.special.nomove");
            attachScript(ticket, "theme_park.dungeon.corvette.corvette_quest_cleanup");
            String custLogMsg = "*Corvette Ground Quest: Player %TU finished the neutral destroy quest and received a neutral corvette ticket.";
            CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        }
        return;
    }
    public void corvette_bruce_neutral_destroy_action_takeDoc2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler02.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            int credits = 487;
            if (credits != 0)
            {
                money.bankTo(money.ACCT_JABBA, player, credits);
                sendSystemMessage(player, credits + " credits have been deposited in your bank account.", null);
            }
        }
        return;
    }
    public void corvette_bruce_neutral_destroy_action_takeDoc1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler01.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            int credits = 487;
            if (credits != 0)
            {
                money.bankTo(money.ACCT_JABBA, player, credits);
                sendSystemMessage(player, credits + " credits have been deposited in your bank account.", null);
            }
        }
        return;
    }
    public void corvette_bruce_neutral_destroy_action_quitCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            removeObjVar(player, "corl_corvette");
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the neutral destroy quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        obj_id document = null;
        String intelTemplate = "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_intel.iff";
        obj_id[] intelDocuments = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, intelTemplate);
        if (intelDocuments != null && intelDocuments.length > 0)
        {
            for (int i = 0; i < intelDocuments.length; i++)
            {
                document = intelDocuments[i];
                if (isIdValid(document))
                {
                    destroyObject(document);
                }
            }
        }
        String filler01Template = "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler01.iff";
        obj_id[] filler01Documents = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, filler01Template);
        if (filler01Documents != null && filler01Documents.length > 0)
        {
            for (int i = 0; i < filler01Documents.length; i++)
            {
                document = filler01Documents[i];
                if (isIdValid(document))
                {
                    destroyObject(document);
                }
            }
        }
        String filler02Template = "object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler02.iff";
        obj_id[] filler02Documents = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, filler02Template);
        if (filler02Documents != null && filler02Documents.length > 0)
        {
            for (int i = 0; i < filler02Documents.length; i++)
            {
                document = filler02Documents[i];
                if (isIdValid(document))
                {
                    destroyObject(document);
                }
            }
        }
        return;
    }
    public void corvette_bruce_neutral_destroy_action_removeTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, npc);
        String ticketTemplate = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
        obj_id[] spaceTickets = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, ticketTemplate);
        if (spaceTickets != null && spaceTickets.length > 0)
        {
            for (int i = 0; i < spaceTickets.length; i++)
            {
                obj_id ticket = spaceTickets[i];
                if (isIdValid(ticket))
                {
                    if ((getStringObjVar(ticket, "space_dungeon.ticket.dungeon")).equals("corvette_neutral"))
                    {
                        destroyObject(ticket);
                        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the neutral destroy quest and the neutal corvette ticket was revoked.";
                        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
                    }
                }
            }
        }
        return;
    }
    public void corvette_bruce_neutral_destroy_action_giveDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        
        {
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_intel.iff", playerInv, "");
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler01.iff", playerInv, "");
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_destroy_filler02.iff", playerInv, "");
        }
    }
    public void corvette_bruce_neutral_destroy_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public void corvette_bruce_neutral_destroy_action_giveCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            createObject("object/tangible/loot/loot_schematic/corellian_corvette_landspeeder_av21_schematic.iff", playerInv, "");
            removeObjVar(player, "corvette.neutral_destroy.finished");
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Reward: Player %TU has been given an AV-21 schematic.", player);
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.corvette_bruce_neutral_destroy");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Bruce McBrain");
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
        detachScript(self, "npc.conversation.corvette_bruce_neutral_destroy");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_bruce_neutral_destroy_condition_dungeonInactive(player, self))
        {
            corvette_bruce_neutral_destroy_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_33849d1f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_bruce_neutral_destroy_condition_earnedCorvetteReward(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2a75ee0f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e61c9f7a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a2329c74");
                }
                setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 2);
                npcStartConversation(player, self, "corvette_bruce_neutral_destroy", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_bruce_neutral_destroy_condition_onDifferentCorvetteQuest(player, self))
        {
            corvette_bruce_neutral_destroy_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_1ffa4e4b");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
        {
            corvette_bruce_neutral_destroy_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_28c81b92");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_bruce_neutral_destroy_condition_hasStuff(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2972f7c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dc1051b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3bfca8b3");
                }
                setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 7);
                npcStartConversation(player, self, "corvette_bruce_neutral_destroy", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_bruce_neutral_destroy_condition_isOnQuest(player, self))
        {
            corvette_bruce_neutral_destroy_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_cfec2ce3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bruce_neutral_destroy_condition_hasStuff(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ca5faf1b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8bd8c49d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_283ba480");
                }
                setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 13);
                npcStartConversation(player, self, "corvette_bruce_neutral_destroy", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
        {
            corvette_bruce_neutral_destroy_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_f7dab98e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_574dc833");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b6988a9c");
                }
                setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 23);
                npcStartConversation(player, self, "corvette_bruce_neutral_destroy", message, responses);
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
        if (!conversationId.equals("corvette_bruce_neutral_destroy"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
        if (branchId == 2 && response.equals("s_e61c9f7a"))
        {
            if (corvette_bruce_neutral_destroy_condition_hasInventorySpace(player, self))
            {
                corvette_bruce_neutral_destroy_action_giveCorvetteReward(player, self);
                string_id message = new string_id(c_stringFile, "s_40e26fa7");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corvette_bruce_neutral_destroy_condition_hasInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4af1600f");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yay! Jabba is happy with me... er... I mean ye. Right... ye. Dem CorSec and dat ship be destroyed! Good, good! Want yer reward?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_a2329c74"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_557298a9");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yay! Jabba is happy with me... er... I mean ye. Right... ye. Dem CorSec and dat ship be destroyed! Good, good! Want yer reward?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_2972f7c"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_224ca617");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ye haven't left yet? Whut ye still doing here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_dc1051b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_21328697");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77455336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d036761d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ye haven't left yet? Whut ye still doing here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_3bfca8b3"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14963f17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ye haven't left yet? Whut ye still doing here?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_77455336"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_removeTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_d725eac");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Whut the?! Ye giving up now, we almost done! If yer quit, I have ta take everything ye gotten up to dis time from ye.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_d036761d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_20f0bac1");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Whut the?! Ye giving up now, we almost done! If yer quit, I have ta take everything ye gotten up to dis time from ye.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_3e583623"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_3da0d379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me take a look.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_d3f9d91d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_c866c945");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me take a look.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_90650308"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_269fe8a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me take a look.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_77928d48"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_adbdb619");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me take a look.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_7e619810"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7a77d96c");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me take a look.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_ca5faf1b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68f1fe27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, yer back! Any luck?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_8bd8c49d"))
        {
            corvette_bruce_neutral_destroy_action_clearLocations(player, self);
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_dea1004c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, yer back! Any luck?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_283ba480"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_ed430e90");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, yer back! Any luck?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_3e583623"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_3da0d379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well fork dem over!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_d3f9d91d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_c866c945");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well fork dem over!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_90650308"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_269fe8a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well fork dem over!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_77928d48"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_adbdb619");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well fork dem over!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_7e619810"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7a77d96c");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well fork dem over!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_3e583623"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_3da0d379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Fantalistic! Now ye just need to fly up to that corvette and make it go boom! Take dis here travel form and show to Klaatu outside behind the palace. He'll hook ye up! Destroy dat ship den come see me for yer reward!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_d3f9d91d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_c866c945");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Fantalistic! Now ye just need to fly up to that corvette and make it go boom! Take dis here travel form and show to Klaatu outside behind the palace. He'll hook ye up! Destroy dat ship den come see me for yer reward!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_90650308"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_269fe8a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Fantalistic! Now ye just need to fly up to that corvette and make it go boom! Take dis here travel form and show to Klaatu outside behind the palace. He'll hook ye up! Destroy dat ship den come see me for yer reward!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_77928d48"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_adbdb619");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Fantalistic! Now ye just need to fly up to that corvette and make it go boom! Take dis here travel form and show to Klaatu outside behind the palace. He'll hook ye up! Destroy dat ship den come see me for yer reward!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_7e619810"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7a77d96c");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Fantalistic! Now ye just need to fly up to that corvette and make it go boom! Take dis here travel form and show to Klaatu outside behind the palace. He'll hook ye up! Destroy dat ship den come see me for yer reward!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_3e583623"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_3da0d379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not sure if dis any useful. I suppose me have to pay ye for yer troubles in getting it though.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_d3f9d91d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_c866c945");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not sure if dis any useful. I suppose me have to pay ye for yer troubles in getting it though.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_90650308"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_269fe8a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not sure if dis any useful. I suppose me have to pay ye for yer troubles in getting it though.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_77928d48"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_adbdb619");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not sure if dis any useful. I suppose me have to pay ye for yer troubles in getting it though.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_7e619810"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7a77d96c");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Not sure if dis any useful. I suppose me have to pay ye for yer troubles in getting it though.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_3e583623"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_3da0d379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Culd be interesting me suppose. Here's some creds fer it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_d3f9d91d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_c866c945");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Culd be interesting me suppose. Here's some creds fer it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_90650308"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_269fe8a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bruce_neutral_destroy_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition_hasNoObjectsAndNoTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e583623");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3f9d91d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90650308");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77928d48");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e619810");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Culd be interesting me suppose. Here's some creds fer it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_77928d48"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_adbdb619");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Culd be interesting me suppose. Here's some creds fer it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_7e619810"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7a77d96c");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Culd be interesting me suppose. Here's some creds fer it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_d6cf9803"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_5d72e2a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Have ye been out drinkin? Okay me go over it again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_47a242b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_bb218f7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Have ye been out drinkin? Okay me go over it again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_7f2cdc"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_7f918abd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Have ye been out drinkin? Okay me go over it again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_65ecd99d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_acceptQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_c58b6642");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Have ye been out drinkin? Okay me go over it again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_369d526d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_a5cbd64e");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Have ye been out drinkin? Okay me go over it again.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_574dc833"))
        {
            corvette_bruce_neutral_destroy_action_clearLocations(player, self);
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f87d1b5b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9dae0fe3");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'G'day! Jabba has me do a most urgent mission and me need yer help! I heard yer name whispered in the palace so me think, dis here fellow wuld be perfect fer dis kinda job.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_b6988a9c"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a5cbd64e");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'G'day! Jabba has me do a most urgent mission and me need yer help! I heard yer name whispered in the palace so me think, dis here fellow wuld be perfect fer dis kinda job.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_9dae0fe3"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_65c58ea3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well we have us a traitor who gave up important stuff to dem CorSec about Jabba. Dis stuff is, as me speak, being transported on a corvette. We must make dis here ship blow up before it reach its destination.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_d6cf9803"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_5d72e2a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well dat be the tricky part. Dere be these destruct sequences out dere somewhere but dey could be at any of three locations, me information wus not too specific. Me need ye to find it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_47a242b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_bb218f7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well dat be the tricky part. Dere be these destruct sequences out dere somewhere but dey could be at any of three locations, me information wus not too specific. Me need ye to find it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_7f2cdc"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_7f918abd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well dat be the tricky part. Dere be these destruct sequences out dere somewhere but dey could be at any of three locations, me information wus not too specific. Me need ye to find it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_65ecd99d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_acceptQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_c58b6642");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well dat be the tricky part. Dere be these destruct sequences out dere somewhere but dey could be at any of three locations, me information wus not too specific. Me need ye to find it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_369d526d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_a5cbd64e");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well dat be the tricky part. Dere be these destruct sequences out dere somewhere but dey could be at any of three locations, me information wus not too specific. Me need ye to find it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_d6cf9803"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_5d72e2a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well on the planet of Naboo, sum of dem Maulers have been planning a raid against dem CorSec. Jabba think dem may have whut we wunt and haven't used it yet. Dey no wunt to give it up though so go dere and take it from dem. It sure ta be in dem camp sumwhere.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_47a242b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_bb218f7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well on the planet of Naboo, sum of dem Maulers have been planning a raid against dem CorSec. Jabba think dem may have whut we wunt and haven't used it yet. Dey no wunt to give it up though so go dere and take it from dem. It sure ta be in dem camp sumwhere.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_7f2cdc"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_7f918abd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well on the planet of Naboo, sum of dem Maulers have been planning a raid against dem CorSec. Jabba think dem may have whut we wunt and haven't used it yet. Dey no wunt to give it up though so go dere and take it from dem. It sure ta be in dem camp sumwhere.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_65ecd99d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_acceptQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_c58b6642");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well on the planet of Naboo, sum of dem Maulers have been planning a raid against dem CorSec. Jabba think dem may have whut we wunt and haven't used it yet. Dey no wunt to give it up though so go dere and take it from dem. It sure ta be in dem camp sumwhere.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_369d526d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_a5cbd64e");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well on the planet of Naboo, sum of dem Maulers have been planning a raid against dem CorSec. Jabba think dem may have whut we wunt and haven't used it yet. Dey no wunt to give it up though so go dere and take it from dem. It sure ta be in dem camp sumwhere.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_d6cf9803"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_5d72e2a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well Jabba have a strong suspicion dem Rogue CorSec culd have dis here information. Dey after all former employees. Maybe dem stupid CorSec forget to change the sequence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_47a242b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_bb218f7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well Jabba have a strong suspicion dem Rogue CorSec culd have dis here information. Dey after all former employees. Maybe dem stupid CorSec forget to change the sequence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_7f2cdc"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_7f918abd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well Jabba have a strong suspicion dem Rogue CorSec culd have dis here information. Dey after all former employees. Maybe dem stupid CorSec forget to change the sequence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_65ecd99d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_acceptQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_c58b6642");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well Jabba have a strong suspicion dem Rogue CorSec culd have dis here information. Dey after all former employees. Maybe dem stupid CorSec forget to change the sequence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_369d526d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_a5cbd64e");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well Jabba have a strong suspicion dem Rogue CorSec culd have dis here information. Dey after all former employees. Maybe dem stupid CorSec forget to change the sequence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_d6cf9803"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_5d72e2a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well if all else fails, on the dreaded planet of Dathomir, there be dem Nightsisters. Jabba once sent a traitor to one of dem slave camps, since he wus a bit of a womanizer. Wonderful sense of humor he has Jabba. Anyway, the traitor probably dead but he had some information dat may have been wut we want and the Sisters may have it now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_47a242b"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_bb218f7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well if all else fails, on the dreaded planet of Dathomir, there be dem Nightsisters. Jabba once sent a traitor to one of dem slave camps, since he wus a bit of a womanizer. Wonderful sense of humor he has Jabba. Anyway, the traitor probably dead but he had some information dat may have been wut we want and the Sisters may have it now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_7f2cdc"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_7f918abd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bruce_neutral_destroy_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6cf9803");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47a242b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f2cdc");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65ecd99d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369d526d");
                    }
                    setObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well if all else fails, on the dreaded planet of Dathomir, there be dem Nightsisters. Jabba once sent a traitor to one of dem slave camps, since he wus a bit of a womanizer. Wonderful sense of humor he has Jabba. Anyway, the traitor probably dead but he had some information dat may have been wut we want and the Sisters may have it now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_65ecd99d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_acceptQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_c58b6642");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well if all else fails, on the dreaded planet of Dathomir, there be dem Nightsisters. Jabba once sent a traitor to one of dem slave camps, since he wus a bit of a womanizer. Wonderful sense of humor he has Jabba. Anyway, the traitor probably dead but he had some information dat may have been wut we want and the Sisters may have it now.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_369d526d"))
        {
            if (corvette_bruce_neutral_destroy_condition__defaultCondition(player, self))
            {
                corvette_bruce_neutral_destroy_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_a5cbd64e");
                removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well if all else fails, on the dreaded planet of Dathomir, there be dem Nightsisters. Jabba once sent a traitor to one of dem slave camps, since he wus a bit of a womanizer. Wonderful sense of humor he has Jabba. Anyway, the traitor probably dead but he had some information dat may have been wut we want and the Sisters may have it now.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corvette_bruce_neutral_destroy.branchId");
        return SCRIPT_CONTINUE;
    }
}
