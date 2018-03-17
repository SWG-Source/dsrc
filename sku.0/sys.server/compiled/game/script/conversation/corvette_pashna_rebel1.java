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
import script.library.space_dungeon;
import script.library.utils;

public class corvette_pashna_rebel1 extends script.base_script
{
    public corvette_pashna_rebel1()
    {
    }
    public static String c_stringFile = "conversation/corvette_pashna_rebel1";
    public boolean corvette_pashna_rebel1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_pashna_rebel1_condition_knowsLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation3");
    }
    public boolean corvette_pashna_rebel1_condition_knowsLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation2");
    }
    public boolean corvette_pashna_rebel1_condition_knowsLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation1");
    }
    public boolean corvette_pashna_rebel1_condition_knowsAllLocations(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "corl_corvette.heardLocation1")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation2")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation3")));
    }
    public boolean corvette_pashna_rebel1_condition_hasStuff(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_intel.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler01.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler02.iff")));
    }
    public boolean corvette_pashna_rebel1_condition_hasObject01(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_intel.iff");
    }
    public boolean corvette_pashna_rebel1_condition_hasObject02(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler01.iff");
    }
    public boolean corvette_pashna_rebel1_condition_hasObject03(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler02.iff");
    }
    public boolean corvette_pashna_rebel1_condition_notRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || !playerFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_pashna_rebel1_condition_hasTravelTicket(obj_id player, obj_id npc) throws InterruptedException
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
                    if ((getStringObjVar(ticket, "space_dungeon.ticket.dungeon")).equals("corvette_rebel"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((!corvette_pashna_rebel1_condition_hasTravelTicket(player, npc)) && (!corvette_pashna_rebel1_condition_hasStuff(player, npc)));
    }
    public boolean corvette_pashna_rebel1_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corl_corvette.rebel_assassin");
    }
    public boolean corvette_pashna_rebel1_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Rebel");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_pashna_rebel1_condition_onDifferentCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            if (!hasObjVar(player, "corl_corvette.rebel_assassin"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean corvette_pashna_rebel1_condition_earnedCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corvette.rebel_assassin.finished");
    }
    public boolean corvette_pashna_rebel1_condition_hasInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_pashna_rebel1_action_heardLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation1", 1);
        return;
    }
    public void corvette_pashna_rebel1_action_heardLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation2", 1);
        return;
    }
    public void corvette_pashna_rebel1_action_heardLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation3", 1);
        return;
    }
    public void corvette_pashna_rebel1_action_acceptsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "corl_corvette.rebel_assassin"))
        {
            setObjVar(player, "corl_corvette.rebel_assassin", 1);
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU has started the rebel assassin quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        return;
    }
    public void corvette_pashna_rebel1_action_clearLocations(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_pashna_rebel1_action_giveTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_intel.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            obj_id ticket = space_dungeon.createTicket(player, "corellia", "corvette_rebel_pilot", "corvette_rebel");
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "rebel_assassin");
            setObjVar(ticket, "corl_corvette.ticket_owner", player);
            setObjVar(ticket, "noTrade", true);
            attachScript(ticket, "item.special.nomove");
            attachScript(ticket, "theme_park.dungeon.corvette.corvette_quest_cleanup");
            String custLogMsg = "*Corvette Ground Quest: Player %TU finished the rebel assassin quest and received a rebel corvette ticket.";
            CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        }
        return;
    }
    public void corvette_pashna_rebel1_action_takeObject2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler01.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            String factionReward = "Rebel";
            if (!factionReward.equals("none"))
            {
                int factionAmt = 100;
                if (factionAmt != 0)
                {
                    factions.addFactionStanding(player, factionReward, factionAmt);
                }
            }
        }
        return;
    }
    public void corvette_pashna_rebel1_action_takeObject3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler02.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            String factionReward = "Rebel";
            if (!factionReward.equals("none"))
            {
                int factionAmt = 100;
                if (factionAmt != 0)
                {
                    factions.addFactionStanding(player, factionReward, factionAmt);
                }
            }
        }
        return;
    }
    public void corvette_pashna_rebel1_action_removeTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
        String ticketTemplate = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
        obj_id[] spaceTickets = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, ticketTemplate);
        if (spaceTickets != null && spaceTickets.length > 0)
        {
            for (int i = 0; i < spaceTickets.length; i++)
            {
                obj_id ticket = spaceTickets[i];
                if (isIdValid(ticket))
                {
                    if ((getStringObjVar(ticket, "space_dungeon.ticket.quest_type")).equals("rebel_assassin"))
                    {
                        destroyObject(ticket);
                        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the rebel assassin quest and the rebel corvette ticket was revoked.";
                        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
                    }
                }
            }
        }
        return;
    }
    public void corvette_pashna_rebel1_action_quitCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            removeObjVar(player, "corl_corvette");
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the rebel assassin quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        obj_id document = null;
        String intelTemplate = "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_intel.iff";
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
        String filler01Template = "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler01.iff";
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
        String filler02Template = "object/tangible/loot/dungeon/corellian_corvette/rebel_assassin_filler02.iff";
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
    public void corvette_pashna_rebel1_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public void corvette_pashna_rebel1_action_giveCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            createObject("object/tangible/loot/loot_schematic/corellian_corvette_landspeeder_av21_schematic.iff", playerInv, "");
            removeObjVar(player, "corvette.rebel_assassin.finished");
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Reward: Player %TU has been given an AV-21 schematic.", player);
        }
        return;
    }
    public int corvette_pashna_rebel1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cb13589"))
        {
            if (corvette_pashna_rebel1_condition_hasInventorySpace(player, npc))
            {
                corvette_pashna_rebel1_action_giveCorvetteReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_5b812563");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corvette_pashna_rebel1_condition_hasInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a4d0c46");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_615637f6"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8a5c56ae");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aaee7ac8");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32abdf15"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2d18a202");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ffef947e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7cd62c6d");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2f26b4c4"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffa3808c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ffef947e"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_removeTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_2be3fa16");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7cd62c6d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_eccf8e31");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb2081d2"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_1cce61b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8b325a7c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0c99345");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f62f461c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6992624d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47eb05e");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_697542b9");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aa504b9e"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9463a903");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_278c1c25"))
        {
            corvette_pashna_rebel1_action_clearLocations(player, npc);
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89df7355");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1729866a"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_29542d1b");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb2081d2"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_1cce61b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8b325a7c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0c99345");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f62f461c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6992624d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47eb05e");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_697542b9");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb2081d2"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_1cce61b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8b325a7c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0c99345");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f62f461c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6992624d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47eb05e");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_697542b9");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb2081d2"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_1cce61b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8b325a7c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0c99345");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f62f461c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6992624d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47eb05e");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_697542b9");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb2081d2"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_1cce61b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8b325a7c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0c99345");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f62f461c"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_pashna_rebel1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb2081d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8b325a7c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f62f461c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6992624d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6992624d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47eb05e");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_697542b9");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26d40ca6"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e92b0e11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98df29e5"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe36e67a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2534c3e3"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_a1ce5a7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_49437190");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36cb0506"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc2ec42b");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d3412303"))
        {
            corvette_pashna_rebel1_action_clearLocations(player, npc);
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77da0881");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e10e18f5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7e9bbb3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b89af325"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fe450b8a");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e10e18f5"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73f241bd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ba7150b1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_436d636d");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7e9bbb3"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7384b0d4");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ba7150b1"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffb796fa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_436d636d"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8f4b486e");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26d40ca6"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e92b0e11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98df29e5"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe36e67a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2534c3e3"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_a1ce5a7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_49437190");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36cb0506"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc2ec42b");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26d40ca6"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e92b0e11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98df29e5"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe36e67a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2534c3e3"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_a1ce5a7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_49437190");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36cb0506"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc2ec42b");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26d40ca6"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e92b0e11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98df29e5"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe36e67a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2534c3e3"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_a1ce5a7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_49437190");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36cb0506"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc2ec42b");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_pashna_rebel1_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26d40ca6"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_e92b0e11");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98df29e5"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe36e67a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2534c3e3"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_a1ce5a7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_pashna_rebel1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26d40ca6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98df29e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2534c3e3");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cb0506");
                    }
                    utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_49437190");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36cb0506"))
        {
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                corvette_pashna_rebel1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc2ec42b");
                utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.corvette_pashna_rebel1");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Pashna Starkiller");
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
        detachScript(self, "conversation.corvette_pashna_rebel1");
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
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_pashna_rebel1_condition_dungeonInactive(player, npc))
        {
            corvette_pashna_rebel1_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_ed0270a5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_pashna_rebel1_condition_notRebel(player, npc))
        {
            corvette_pashna_rebel1_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_dbef483d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_pashna_rebel1_condition_earnedCorvetteReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_fd5fb0cf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cb13589");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_615637f6");
                }
                utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 3);
                npcStartConversation(player, npc, "corvette_pashna_rebel1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_pashna_rebel1_condition_onDifferentCorvetteQuest(player, npc))
        {
            corvette_pashna_rebel1_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_ecc372ff");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_pashna_rebel1_condition_hasTravelTicket(player, npc))
        {
            corvette_pashna_rebel1_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_2f662226");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_pashna_rebel1_condition_hasStuff(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32abdf15");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2f26b4c4");
                }
                utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 8);
                npcStartConversation(player, npc, "corvette_pashna_rebel1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_pashna_rebel1_condition_isOnQuest(player, npc))
        {
            corvette_pashna_rebel1_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_c26ad0e8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_pashna_rebel1_condition_hasStuff(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aa504b9e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_278c1c25");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1729866a");
                }
                utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 14);
                npcStartConversation(player, npc, "corvette_pashna_rebel1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
        {
            corvette_pashna_rebel1_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_bbd30f60");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_pashna_rebel1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d3412303");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b89af325");
                }
                utils.setScriptVar(player, "conversation.corvette_pashna_rebel1.branchId", 23);
                npcStartConversation(player, npc, "corvette_pashna_rebel1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corvette_pashna_rebel1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
        if (branchId == 3 && corvette_pashna_rebel1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corvette_pashna_rebel1_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corvette_pashna_rebel1_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && corvette_pashna_rebel1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corvette_pashna_rebel1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && corvette_pashna_rebel1_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && corvette_pashna_rebel1_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corvette_pashna_rebel1_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && corvette_pashna_rebel1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && corvette_pashna_rebel1_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && corvette_pashna_rebel1_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && corvette_pashna_rebel1_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corvette_pashna_rebel1_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corvette_pashna_rebel1_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && corvette_pashna_rebel1_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && corvette_pashna_rebel1_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && corvette_pashna_rebel1_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corvette_pashna_rebel1.branchId");
        return SCRIPT_CONTINUE;
    }
}
