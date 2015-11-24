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
import script.library.money;
import script.library.space_dungeon;
import script.library.utils;

public class corvette_bronell_neutral_assassin extends script.base_script
{
    public corvette_bronell_neutral_assassin()
    {
    }
    public static String c_stringFile = "conversation/corvette_bronell_neutral_assassin";
    public boolean corvette_bronell_neutral_assassin_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_bronell_neutral_assassin_condition_knowsLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation3");
    }
    public boolean corvette_bronell_neutral_assassin_condition_knowsLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation2");
    }
    public boolean corvette_bronell_neutral_assassin_condition_knowsLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation1");
    }
    public boolean corvette_bronell_neutral_assassin_condition_knowsAllLocations(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "corl_corvette.heardLocation1")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation2")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation3")));
    }
    public boolean corvette_bronell_neutral_assassin_condition_hasStuff(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_intel.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler01.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler02.iff")));
    }
    public boolean corvette_bronell_neutral_assassin_condition_hasObject01(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_intel.iff");
    }
    public boolean corvette_bronell_neutral_assassin_condition_hasObject02(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler01.iff");
    }
    public boolean corvette_bronell_neutral_assassin_condition_hasObject03(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler02.iff");
    }
    public boolean corvette_bronell_neutral_assassin_condition_isGm(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "gm");
    }
    public boolean corvette_bronell_neutral_assassin_condition_hasTravelTicket(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((!corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, npc)) && (!corvette_bronell_neutral_assassin_condition_hasStuff(player, npc)));
    }
    public boolean corvette_bronell_neutral_assassin_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corl_corvette.neutral_assassin");
    }
    public boolean corvette_bronell_neutral_assassin_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Neutral");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_bronell_neutral_assassin_condition_onDifferentCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            if (!hasObjVar(player, "corl_corvette.neutral_assassin"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean corvette_bronell_neutral_assassin_condition_earnedCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corvette.neutral_assassin.finished");
    }
    public boolean corvette_bronell_neutral_assassin_condition_hasInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_bronell_neutral_assassin_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void corvette_bronell_neutral_assassin_action_heardLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation1", 1);
        return;
    }
    public void corvette_bronell_neutral_assassin_action_heardLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation2", 1);
        return;
    }
    public void corvette_bronell_neutral_assassin_action_heardLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation3", 1);
        return;
    }
    public void corvette_bronell_neutral_assassin_action_acceptsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "corl_corvette.neutral_assassin", 1);
        String custLogMsg = "*Corvette Ground Quest: Player %TU has started the neutral assassin quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        return;
    }
    public void corvette_bronell_neutral_assassin_action_clearLocations(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_bronell_neutral_assassin_action_giveTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_intel.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            obj_id ticket = space_dungeon.createTicket(player, "tatooine", "corvette_neutral_pilot", "corvette_neutral");
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "neutral_assassin");
            setObjVar(ticket, "corl_corvette.ticket_owner", player);
            setObjVar(ticket, "noTrade", true);
            attachScript(ticket, "item.special.nomove");
            attachScript(ticket, "theme_park.dungeon.corvette.corvette_quest_cleanup");
            String custLogMsg = "*Corvette Ground Quest: Player %TU finished the neutral assassin quest and received a neutral corvette ticket.";
            CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        }
        return;
    }
    public void corvette_bronell_neutral_assassin_action_giveDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        
        {
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_intel.iff", playerInv, "");
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler01.iff", playerInv, "");
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler02.iff", playerInv, "");
        }
    }
    public void corvette_bronell_neutral_assassin_action_quitCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            removeObjVar(player, "corl_corvette");
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the neutral assassin quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        obj_id document = null;
        String intelTemplate = "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_intel.iff";
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
        String filler01Template = "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler01.iff";
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
        String filler02Template = "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler02.iff";
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
    public void corvette_bronell_neutral_assassin_action_removeTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, npc);
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
                        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the neutral assassin quest and the neutal corvette ticket was revoked.";
                        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
                    }
                }
            }
        }
        return;
    }
    public void corvette_bronell_neutral_assassin_action_takeDoc2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler02.iff");
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
    public void corvette_bronell_neutral_assassin_action_takeDoc1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_assassin_filler01.iff");
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
    public void corvette_bronell_neutral_assassin_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public void corvette_bronell_neutral_assassin_action_giveCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            createObject("object/tangible/loot/loot_schematic/corellian_corvette_landspeeder_av21_schematic.iff", playerInv, "");
            removeObjVar(player, "corvette.neutral_assassin.finished");
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Reward: Player %TU has been given an AV-21 schematic.", player);
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.corvette_bronell_neutral_assassin");
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
        setName(self, "Bronell");
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
        detachScript(self, "npc.conversation.corvette_bronell_neutral_assassin");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_bronell_neutral_assassin_condition_dungeonInactive(player, self))
        {
            corvette_bronell_neutral_assassin_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_382a91bd");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_bronell_neutral_assassin_condition_earnedCorvetteReward(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1779cf3b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b5022ce5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ffc988c3");
                }
                setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 2);
                npcStartConversation(player, self, "corvette_bronell_neutral_assassin", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_bronell_neutral_assassin_condition_onDifferentCorvetteQuest(player, self))
        {
            corvette_bronell_neutral_assassin_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_dc53bb6d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
        {
            corvette_bronell_neutral_assassin_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_6f1692bf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_bronell_neutral_assassin_condition_hasStuff(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4a365e4b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d27dcffe");
                }
                setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 7);
                npcStartConversation(player, self, "corvette_bronell_neutral_assassin", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_bronell_neutral_assassin_condition_isOnQuest(player, self))
        {
            corvette_bronell_neutral_assassin_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_491ecf2b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bronell_neutral_assassin_condition_hasStuff(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24dbc07d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9b8c1f57");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3e2e8616");
                }
                setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 13);
                npcStartConversation(player, self, "corvette_bronell_neutral_assassin", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
        {
            corvette_bronell_neutral_assassin_action_facePlayer(player, self);
            string_id message = new string_id(c_stringFile, "s_8c30ea7c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d62f7d90");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bd40a944");
                }
                setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 23);
                npcStartConversation(player, self, "corvette_bronell_neutral_assassin", message, responses);
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
        if (!conversationId.equals("corvette_bronell_neutral_assassin"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
        if (branchId == 2 && response.equals("s_b5022ce5"))
        {
            if (corvette_bronell_neutral_assassin_condition_hasInventorySpace(player, self))
            {
                corvette_bronell_neutral_assassin_action_giveCorvetteReward(player, self);
                string_id message = new string_id(c_stringFile, "s_249447d6");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corvette_bronell_neutral_assassin_condition_hasInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_73f00073");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Lord Jabba is pleased to hear that the CorSec officer has been eliminated. Let this be a lesson to all who fail to pay their debts to Lord Jabba the Hutt, yes? Are you here to receive your reward, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_ffc988c3"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_44fa5eb9");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Lord Jabba is pleased to hear that the CorSec officer has been eliminated. Let this be a lesson to all who fail to pay their debts to Lord Jabba the Hutt, yes? Are you here to receive your reward, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_2972f7c"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3ffc96c6");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Your still here? I thought I made clear time was not to be wasted looking at the dancers in the palace, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_4a365e4b"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f8c79456");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79855d10");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bdde1d3a");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Your still here? I thought I made clear time was not to be wasted looking at the dancers in the palace, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_d27dcffe"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_dcf8e793");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Your still here? I thought I made clear time was not to be wasted looking at the dancers in the palace, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_79855d10"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_removeTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_969790d9");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Our bountiful Lord will be most disappointed by your attitude, maybe a bit too disappointed, yes? I'll be forced to take all documents from you that relate to this endeavor, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_bdde1d3a"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce8f15d7");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Our bountiful Lord will be most disappointed by your attitude, maybe a bit too disappointed, yes? I'll be forced to take all documents from you that relate to this endeavor, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_465d7ab2"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_c79b5a4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh? Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_3f04cefb"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_e9499aa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh? Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_fecfb00f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_8c845e29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh? Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_3c2e92a"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1d18ea0");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh? Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_c5ae9239"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_502c2a8d");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh? Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_24dbc07d"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6333f589");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You are back already, yes? You have the documents, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_9b8c1f57"))
        {
            corvette_bronell_neutral_assassin_action_clearLocations(player, self);
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f9bdde1d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You are back already, yes? You have the documents, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_3e2e8616"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_88ef705e");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You are back already, yes? You have the documents, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_465d7ab2"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_c79b5a4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_3f04cefb"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_e9499aa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_fecfb00f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_8c845e29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_3c2e92a"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1d18ea0");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_c5ae9239"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_502c2a8d");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see that, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_465d7ab2"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_c79b5a4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Splendid, let me see. Hmm, no, no...wait, yes! There he is! So that's where his ship is, yes? You must intercept this corvette and arrange his demise immediately, yes? If you succeed in this, Lord Jabba has allowed me to reward you generously, yes? Yes!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_3f04cefb"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_e9499aa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Splendid, let me see. Hmm, no, no...wait, yes! There he is! So that's where his ship is, yes? You must intercept this corvette and arrange his demise immediately, yes? If you succeed in this, Lord Jabba has allowed me to reward you generously, yes? Yes!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_fecfb00f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_8c845e29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Splendid, let me see. Hmm, no, no...wait, yes! There he is! So that's where his ship is, yes? You must intercept this corvette and arrange his demise immediately, yes? If you succeed in this, Lord Jabba has allowed me to reward you generously, yes? Yes!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_3c2e92a"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1d18ea0");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Splendid, let me see. Hmm, no, no...wait, yes! There he is! So that's where his ship is, yes? You must intercept this corvette and arrange his demise immediately, yes? If you succeed in this, Lord Jabba has allowed me to reward you generously, yes? Yes!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_c5ae9239"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_502c2a8d");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Splendid, let me see. Hmm, no, no...wait, yes! There he is! So that's where his ship is, yes? You must intercept this corvette and arrange his demise immediately, yes? If you succeed in this, Lord Jabba has allowed me to reward you generously, yes? Yes!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_465d7ab2"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_c79b5a4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see. Hmm, yes, yes...No! Did you even read this? Oh well, guess I have to compensate you for your time, yes? Now check the other hideouts!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_3f04cefb"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_e9499aa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see. Hmm, yes, yes...No! Did you even read this? Oh well, guess I have to compensate you for your time, yes? Now check the other hideouts!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_fecfb00f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_8c845e29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see. Hmm, yes, yes...No! Did you even read this? Oh well, guess I have to compensate you for your time, yes? Now check the other hideouts!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_3c2e92a"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1d18ea0");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see. Hmm, yes, yes...No! Did you even read this? Oh well, guess I have to compensate you for your time, yes? Now check the other hideouts!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_c5ae9239"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_502c2a8d");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see. Hmm, yes, yes...No! Did you even read this? Oh well, guess I have to compensate you for your time, yes? Now check the other hideouts!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_465d7ab2"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_giveTicket(player, self);
                string_id message = new string_id(c_stringFile, "s_c79b5a4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm, yes, yes, completely useless. Here's some credits as compensation for you time. Anything else, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_3f04cefb"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc1(player, self);
                string_id message = new string_id(c_stringFile, "s_e9499aa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm, yes, yes, completely useless. Here's some credits as compensation for you time. Anything else, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_fecfb00f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_takeDoc2(player, self);
                string_id message = new string_id(c_stringFile, "s_8c845e29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_bronell_neutral_assassin_condition_hasObject03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition_hasTravelTicket(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition_hasNoObjectsAndNoTicket(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_465d7ab2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f04cefb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fecfb00f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c2e92a");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5ae9239");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm, yes, yes, completely useless. Here's some credits as compensation for you time. Anything else, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_3c2e92a"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1d18ea0");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm, yes, yes, completely useless. Here's some credits as compensation for you time. Anything else, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_c5ae9239"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_502c2a8d");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmm, yes, yes, completely useless. Here's some credits as compensation for you time. Anything else, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_94bc6e8f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_f7203ef1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah! Fine. Talus, Naboo, or Lok... which planet, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_2ad476a3"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_469c9070");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah! Fine. Talus, Naboo, or Lok... which planet, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_ba696f5d"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_e1341a5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah! Fine. Talus, Naboo, or Lok... which planet, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_76e2b2f6"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_acceptsQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_b1e7cb8c");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah! Fine. Talus, Naboo, or Lok... which planet, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_12eb211c"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_ecc1d177");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah! Fine. Talus, Naboo, or Lok... which planet, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_d62f7d90"))
        {
            corvette_bronell_neutral_assassin_action_clearLocations(player, self);
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bef20f69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f5dcf8ec");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85bcb083");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You there. I must demand your assistance with this mission of utmost importance. You will help, yes? This mission is vital to Lord Jabba and I am only speaking on his behalf. I, of course, get nothing out of this myself. So you will help, yes yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_bd40a944"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d29adc4");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You there. I must demand your assistance with this mission of utmost importance. You will help, yes? This mission is vital to Lord Jabba and I am only speaking on his behalf. I, of course, get nothing out of this myself. So you will help, yes yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_f5dcf8ec"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_560a7d6e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eece1fb8");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, indeed. Lord Hutt has a problem with a person who owes more than he could possibly pay, yes? You know what our venerable Lord does to those who can't pay debts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_85bcb083"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ecc1d177");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, indeed. Lord Hutt has a problem with a person who owes more than he could possibly pay, yes? You know what our venerable Lord does to those who can't pay debts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_eece1fb8"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_832183d2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Quiet! I was not done yet, yes? This person is an officer in the Corellian Security Forces and thinks that will protect him, yes? He has incurred debts all over the galaxy, though, and I know a way to find him, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_94bc6e8f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_f7203ef1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Didn't I just say I have, yes? There are three lowly gangs of pirates, and this CorSec officer owes money to each of them, yes? These pirates can be found on Talus, Naboo, and Lok. Any of them may have documented his whereabouts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_2ad476a3"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_469c9070");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Didn't I just say I have, yes? There are three lowly gangs of pirates, and this CorSec officer owes money to each of them, yes? These pirates can be found on Talus, Naboo, and Lok. Any of them may have documented his whereabouts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_ba696f5d"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_e1341a5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Didn't I just say I have, yes? There are three lowly gangs of pirates, and this CorSec officer owes money to each of them, yes? These pirates can be found on Talus, Naboo, and Lok. Any of them may have documented his whereabouts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_76e2b2f6"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_acceptsQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_b1e7cb8c");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Didn't I just say I have, yes? There are three lowly gangs of pirates, and this CorSec officer owes money to each of them, yes? These pirates can be found on Talus, Naboo, and Lok. Any of them may have documented his whereabouts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_12eb211c"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_ecc1d177");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Didn't I just say I have, yes? There are three lowly gangs of pirates, and this CorSec officer owes money to each of them, yes? These pirates can be found on Talus, Naboo, and Lok. Any of them may have documented his whereabouts, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_94bc6e8f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_f7203ef1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'On Talus resides the despicable gang of pirates called Binayre. Scum they are, yes? Well below our notice normally, but this CorSec officer owes them a small amount of money. Perhaps they have information on CorSec operations that would tell us his location, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_2ad476a3"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_469c9070");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'On Talus resides the despicable gang of pirates called Binayre. Scum they are, yes? Well below our notice normally, but this CorSec officer owes them a small amount of money. Perhaps they have information on CorSec operations that would tell us his location, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_ba696f5d"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_e1341a5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'On Talus resides the despicable gang of pirates called Binayre. Scum they are, yes? Well below our notice normally, but this CorSec officer owes them a small amount of money. Perhaps they have information on CorSec operations that would tell us his location, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_76e2b2f6"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_acceptsQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_b1e7cb8c");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'On Talus resides the despicable gang of pirates called Binayre. Scum they are, yes? Well below our notice normally, but this CorSec officer owes them a small amount of money. Perhaps they have information on CorSec operations that would tell us his location, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_12eb211c"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_ecc1d177");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'On Talus resides the despicable gang of pirates called Binayre. Scum they are, yes? Well below our notice normally, but this CorSec officer owes them a small amount of money. Perhaps they have information on CorSec operations that would tell us his location, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_94bc6e8f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_f7203ef1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A ragtag bunch of pirates has a hideout there, yes? They are mostly known for kidnapping Naboo royalty, but they indulge in gambling from time to time as well. This CorSec officer owes them money, yes? He has quite a gambling problem, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_2ad476a3"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_469c9070");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A ragtag bunch of pirates has a hideout there, yes? They are mostly known for kidnapping Naboo royalty, but they indulge in gambling from time to time as well. This CorSec officer owes them money, yes? He has quite a gambling problem, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_ba696f5d"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_e1341a5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A ragtag bunch of pirates has a hideout there, yes? They are mostly known for kidnapping Naboo royalty, but they indulge in gambling from time to time as well. This CorSec officer owes them money, yes? He has quite a gambling problem, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_76e2b2f6"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_acceptsQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_b1e7cb8c");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A ragtag bunch of pirates has a hideout there, yes? They are mostly known for kidnapping Naboo royalty, but they indulge in gambling from time to time as well. This CorSec officer owes them money, yes? He has quite a gambling problem, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_12eb211c"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_ecc1d177");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A ragtag bunch of pirates has a hideout there, yes? They are mostly known for kidnapping Naboo royalty, but they indulge in gambling from time to time as well. This CorSec officer owes them money, yes? He has quite a gambling problem, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_94bc6e8f"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation1(player, self);
                string_id message = new string_id(c_stringFile, "s_f7203ef1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I learned of a third option from an informant... well, a former informant. While his tongue was still attached, yes? The Canyon Corsairs on Lok have also dealt with this gambling maniac, yes? They might have the correct information as well, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_2ad476a3"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation2(player, self);
                string_id message = new string_id(c_stringFile, "s_469c9070");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I learned of a third option from an informant... well, a former informant. While his tongue was still attached, yes? The Canyon Corsairs on Lok have also dealt with this gambling maniac, yes? They might have the correct information as well, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_ba696f5d"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_heardLocation3(player, self);
                string_id message = new string_id(c_stringFile, "s_e1341a5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_bronell_neutral_assassin_condition_knowsLocation3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94bc6e8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ad476a3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba696f5d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76e2b2f6");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12eb211c");
                    }
                    setObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I learned of a third option from an informant... well, a former informant. While his tongue was still attached, yes? The Canyon Corsairs on Lok have also dealt with this gambling maniac, yes? They might have the correct information as well, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_76e2b2f6"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_acceptsQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_b1e7cb8c");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I learned of a third option from an informant... well, a former informant. While his tongue was still attached, yes? The Canyon Corsairs on Lok have also dealt with this gambling maniac, yes? They might have the correct information as well, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_12eb211c"))
        {
            if (corvette_bronell_neutral_assassin_condition__defaultCondition(player, self))
            {
                corvette_bronell_neutral_assassin_action_quitCorvetteQuest(player, self);
                string_id message = new string_id(c_stringFile, "s_ecc1d177");
                removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I learned of a third option from an informant... well, a former informant. While his tongue was still attached, yes? The Canyon Corsairs on Lok have also dealt with this gambling maniac, yes? They might have the correct information as well, yes?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corvette_bronell_neutral_assassin.branchId");
        return SCRIPT_CONTINUE;
    }
}
