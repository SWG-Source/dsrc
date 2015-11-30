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

public class corvette_colonel_imperial1 extends script.base_script
{
    public corvette_colonel_imperial1()
    {
    }
    public static String c_stringFile = "conversation/corvette_colonel_imperial1";
    public boolean corvette_colonel_imperial1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_colonel_imperial1_condition_knowsLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation3");
    }
    public boolean corvette_colonel_imperial1_condition_knowsLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation2");
    }
    public boolean corvette_colonel_imperial1_condition_knowsLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation1");
    }
    public boolean corvette_colonel_imperial1_condition_knowsAllLocations(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "corl_corvette.heardLocation1")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation2")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation3")));
    }
    public boolean corvette_colonel_imperial1_condition_hasStuff(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_intel.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler01.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler02.iff")));
    }
    public boolean corvette_colonel_imperial1_condition_hasObject01(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_intel.iff");
    }
    public boolean corvette_colonel_imperial1_condition_hasObject02(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler01.iff");
    }
    public boolean corvette_colonel_imperial1_condition_hasObject03(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler02.iff");
    }
    public boolean corvette_colonel_imperial1_condition_notImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || !playerFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_colonel_imperial1_condition_hasTravelTicket(obj_id player, obj_id npc) throws InterruptedException
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
                    if ((getStringObjVar(ticket, "space_dungeon.ticket.dungeon")).equals("corvette_imperial"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!corvette_colonel_imperial1_condition_hasTravelTicket(player, npc) && (!corvette_colonel_imperial1_condition_hasStuff(player, npc)));
    }
    public boolean corvette_colonel_imperial1_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corl_corvette.imperial_rescue");
    }
    public boolean corvette_colonel_imperial1_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Imperial");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_colonel_imperial1_condition_onDifferentCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            if (!hasObjVar(player, "corl_corvette.imperial_rescue"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean corvette_colonel_imperial1_condition_earnedCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corvette.imperial_rescue.finished");
    }
    public boolean corvette_colonel_imperial1_condition_hasInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_colonel_imperial1_action_heardLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation1", 1);
        return;
    }
    public void corvette_colonel_imperial1_action_heardLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation2", 1);
        return;
    }
    public void corvette_colonel_imperial1_action_heardLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation3", 1);
        return;
    }
    public void corvette_colonel_imperial1_action_acceptsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "corl_corvette.imperial_rescue", 1);
        String custLogMsg = "*Corvette Ground Quest: Player %TU has started the imperial rescue quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        return;
    }
    public void corvette_colonel_imperial1_action_clearLocations(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_colonel_imperial1_action_giveTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_intel.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            obj_id ticket = space_dungeon.createTicket(player, "naboo", "corvette_imperial_pilot", "corvette_imperial");
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "imperial_rescue");
            setObjVar(ticket, "corl_corvette.ticket_owner", player);
            setObjVar(ticket, "noTrade", true);
            attachScript(ticket, "item.special.nomove");
            attachScript(ticket, "theme_park.dungeon.corvette.corvette_quest_cleanup");
            String custLogMsg = "*Corvette Ground Quest: Player %TU finished the imperial rescue quest and received an imperial corvette ticket.";
            CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        }
        return;
    }
    public void corvette_colonel_imperial1_action_quitCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            removeObjVar(player, "corl_corvette");
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the imperial rescue quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        obj_id document = null;
        String intelTemplate = "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_intel.iff";
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
        String filler01Template = "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler01.iff";
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
        String filler02Template = "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler02.iff";
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
    public void corvette_colonel_imperial1_action_takeObject2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler01.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            String factionReward = "Imperial";
            if (!factionReward.equals("none"))
            {
                int factionAmt = 100;
                if (factionAmt != 0)
                {
                    factions.addFactionStanding(player, factionReward, factionAmt);
                }
            }
        }
    }
    public void corvette_colonel_imperial1_action_takeObject3(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/imperial_rescue_filler02.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            String factionReward = "Imperial";
            if (!factionReward.equals("none"))
            {
                int factionAmt = 100;
                if (factionAmt != 0)
                {
                    factions.addFactionStanding(player, factionReward, factionAmt);
                }
            }
        }
    }
    public void corvette_colonel_imperial1_action_removeTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        corvette_colonel_imperial1_action_quitCorvetteQuest(player, npc);
        String ticketTemplate = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
        obj_id[] spaceTickets = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(player, ticketTemplate);
        if (spaceTickets != null && spaceTickets.length > 0)
        {
            for (int i = 0; i < spaceTickets.length; i++)
            {
                obj_id ticket = spaceTickets[i];
                if (isIdValid(ticket))
                {
                    if ((getStringObjVar(ticket, "space_dungeon.ticket.quest_type")).equals("imperial_rescue"))
                    {
                        destroyObject(ticket);
                        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the imperial rescue quest and the imperial corvette ticket was revoked.";
                        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
                    }
                }
            }
        }
        return;
    }
    public void corvette_colonel_imperial1_action_giveCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            createObject("object/tangible/loot/loot_schematic/corellian_corvette_landspeeder_av21_schematic.iff", playerInv, "");
            removeObjVar(player, "corvette.imperial_rescue.finished");
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Reward: Player %TU has been given an AV-21 schematic.", player);
        }
        return;
    }
    public int corvette_colonel_imperial1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21550a19"))
        {
            if (corvette_colonel_imperial1_condition_hasInventorySpace(player, npc))
            {
                corvette_colonel_imperial1_action_giveCorvetteReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_81ff39a5");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corvette_colonel_imperial1_condition_hasInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8e06b4e8");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a38773c1"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dc4a40d6");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4002f794");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d5ab67dd"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80d92f17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd109d64");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7a2a05c4");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2f26b4c4"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffa3808c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dd109d64"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_removeTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_180bc00a");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7a2a05c4"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f769f479");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bc1d51e"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6e45415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c2f40cdd"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_adecb4e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6ef1e0c"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61a71fef"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a973e2d");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33656cd3");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1b0601e4"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3c50285d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4fd6735c"))
        {
            corvette_colonel_imperial1_action_clearLocations(player, npc);
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ec42069a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5dad02b"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_bcecb501");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bc1d51e"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6e45415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c2f40cdd"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_adecb4e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6ef1e0c"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61a71fef"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a973e2d");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33656cd3");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bc1d51e"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6e45415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c2f40cdd"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_adecb4e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6ef1e0c"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61a71fef"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a973e2d");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33656cd3");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bc1d51e"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6e45415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c2f40cdd"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_adecb4e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6ef1e0c"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61a71fef"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a973e2d");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33656cd3");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bc1d51e"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6e45415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c2f40cdd"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject2(player, npc);
                string_id message = new string_id(c_stringFile, "s_adecb4e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6ef1e0c"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_takeObject3(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_colonel_imperial1_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc1d51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2f40cdd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6ef1e0c");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61a71fef");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128e067");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61a71fef"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a973e2d");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a128e067"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33656cd3");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_458f152b"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_56fe6ddb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41506e32"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b10534d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2177029"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc068f1e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_bd942cc0");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a8874c3"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74edd9cc");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_df607c49"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a5e941d1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1944f1e6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157ef5eb");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5200ce80"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1944f1e6"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a342e94c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_157ef5eb"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28586fd5");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_458f152b"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_56fe6ddb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41506e32"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b10534d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2177029"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc068f1e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_bd942cc0");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a8874c3"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74edd9cc");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_458f152b"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_56fe6ddb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41506e32"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b10534d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2177029"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc068f1e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_bd942cc0");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a8874c3"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74edd9cc");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_458f152b"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_56fe6ddb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41506e32"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b10534d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2177029"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc068f1e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_bd942cc0");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a8874c3"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74edd9cc");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_colonel_imperial1_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_458f152b"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_56fe6ddb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41506e32"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b10534d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2177029"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc068f1e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_colonel_imperial1_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458f152b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41506e32");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2177029");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5591c27");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a8874c3");
                    }
                    utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a5591c27"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                corvette_colonel_imperial1_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_bd942cc0");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a8874c3"))
        {
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74edd9cc");
                utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
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
            detachScript(self, "conversation.corvette_colonel_imperial1");
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
        setName(self, "Colonel Darkstone");
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
        detachScript(self, "conversation.corvette_colonel_imperial1");
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
        if (corvette_colonel_imperial1_condition_dungeonInactive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_979b7ad4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_colonel_imperial1_condition_notImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_684f9e1");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_colonel_imperial1_condition_earnedCorvetteReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c48932d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21550a19");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a38773c1");
                }
                utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 3);
                npcStartConversation(player, npc, "corvette_colonel_imperial1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_colonel_imperial1_condition_onDifferentCorvetteQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33f4749f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_colonel_imperial1_condition_hasTravelTicket(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5c4b594c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_colonel_imperial1_condition_hasStuff(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d5ab67dd");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2f26b4c4");
                }
                utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 8);
                npcStartConversation(player, npc, "corvette_colonel_imperial1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_colonel_imperial1_condition_isOnQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18d01770");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_colonel_imperial1_condition_hasStuff(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1b0601e4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4fd6735c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5dad02b");
                }
                utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 14);
                npcStartConversation(player, npc, "corvette_colonel_imperial1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
        {
            corvette_colonel_imperial1_action_clearLocations(player, npc);
            string_id message = new string_id(c_stringFile, "s_d630c3b6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_colonel_imperial1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_df607c49");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5200ce80");
                }
                utils.setScriptVar(player, "conversation.corvette_colonel_imperial1.branchId", 23);
                npcStartConversation(player, npc, "corvette_colonel_imperial1", message, responses);
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
        if (!conversationId.equals("corvette_colonel_imperial1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
        if (branchId == 3 && corvette_colonel_imperial1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corvette_colonel_imperial1_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corvette_colonel_imperial1_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && corvette_colonel_imperial1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corvette_colonel_imperial1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && corvette_colonel_imperial1_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && corvette_colonel_imperial1_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corvette_colonel_imperial1_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && corvette_colonel_imperial1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && corvette_colonel_imperial1_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && corvette_colonel_imperial1_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && corvette_colonel_imperial1_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corvette_colonel_imperial1_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corvette_colonel_imperial1_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && corvette_colonel_imperial1_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && corvette_colonel_imperial1_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corvette_colonel_imperial1.branchId");
        return SCRIPT_CONTINUE;
    }
}
