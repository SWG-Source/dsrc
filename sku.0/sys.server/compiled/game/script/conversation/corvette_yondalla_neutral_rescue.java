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

public class corvette_yondalla_neutral_rescue extends script.base_script
{
    public corvette_yondalla_neutral_rescue()
    {
    }
    public static String c_stringFile = "conversation/corvette_yondalla_neutral_rescue";
    public boolean corvette_yondalla_neutral_rescue_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_yondalla_neutral_rescue_condition_knowsLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation3");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_knowsLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation2");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_knowsLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "corl_corvette.heardLocation1");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_knowsAllLocations(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.hasScriptVar(player, "corl_corvette.heardLocation1")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation2")) && (utils.hasScriptVar(player, "corl_corvette.heardLocation3")));
    }
    public boolean corvette_yondalla_neutral_rescue_condition_hasStuff(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_intel.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler01.iff")) || (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler02.iff")));
    }
    public boolean corvette_yondalla_neutral_rescue_condition_hasObject01(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_intel.iff");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_hasObject02(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler01.iff");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_hasObject03(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler02.iff");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_isGm(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "gm");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_hasTravelTicket(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((!corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc)) && (!corvette_yondalla_neutral_rescue_condition_hasStuff(player, npc)));
    }
    public boolean corvette_yondalla_neutral_rescue_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corl_corvette.neutral_rescue");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Corellian_Corvette_Neutral");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public boolean corvette_yondalla_neutral_rescue_condition_onDifferentCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            if (!hasObjVar(player, "corl_corvette.neutral_rescue"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean corvette_yondalla_neutral_rescue_condition_earnedCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "corvette.neutral_rescue.finished");
    }
    public boolean corvette_yondalla_neutral_rescue_condition_hasInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_yondalla_neutral_rescue_action_heardLocation1(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation1", 1);
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_heardLocation2(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation2", 1);
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_heardLocation3(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "corl_corvette.heardLocation3", 1);
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_acceptsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "corl_corvette.neutral_rescue", 1);
        String custLogMsg = "*Corvette Ground Quest: Player %TU has started the neutral rescue quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_clearLocations(obj_id player, obj_id npc) throws InterruptedException
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
    public void corvette_yondalla_neutral_rescue_action_giveTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_intel.iff");
        if (isIdValid(document))
        {
            destroyObject(document);
            obj_id ticket = space_dungeon.createTicket(player, "tatooine", "corvette_neutral_pilot", "corvette_neutral");
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "neutral_rescue");
            setObjVar(ticket, "corl_corvette.ticket_owner", player);
            setObjVar(ticket, "noTrade", true);
            attachScript(ticket, "item.special.nomove");
            attachScript(ticket, "theme_park.dungeon.corvette.corvette_quest_cleanup");
            String custLogMsg = "*Corvette Ground Quest: Player %TU finished the neutral rescue quest and received a neutral corvette ticket.";
            CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        }
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_giveDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        
        {
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_intel.iff", playerInv, "");
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler01.iff", playerInv, "");
            createObject("object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler02.iff", playerInv, "");
        }
    }
    public void corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "corl_corvette"))
        {
            removeObjVar(player, "corl_corvette");
        }
        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the neutral rescue quest.";
        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
        obj_id document = null;
        String intelTemplate = "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_intel.iff";
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
        String filler01Template = "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler01.iff";
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
        String filler02Template = "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler02.iff";
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
    public void corvette_yondalla_neutral_rescue_action_removeTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
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
                        String custLogMsg = "*Corvette Ground Quest: Player %TU aborted the neutral rescue quest and the neutal corvette ticket was revoked.";
                        CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, player);
                    }
                }
            }
        }
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_takeDoc2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler02.iff");
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
    public void corvette_yondalla_neutral_rescue_action_takeDoc1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id document = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/neutral_rescue_filler01.iff");
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
    public void corvette_yondalla_neutral_rescue_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public void corvette_yondalla_neutral_rescue_action_giveCorvetteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            createObject("object/tangible/loot/loot_schematic/corellian_corvette_landspeeder_av21_schematic.iff", playerInv, "");
            removeObjVar(player, "corvette.neutral_rescue.finished");
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Reward: Player %TU has been given an AV-21 schematic.", player);
        }
        return;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9fb0ad73"))
        {
            if (corvette_yondalla_neutral_rescue_condition_hasInventorySpace(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_giveCorvetteReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_7e1ef500");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corvette_yondalla_neutral_rescue_condition_hasInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7604ad47");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c8d78390"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fe8eb1aa");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2972f7c"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b5494314");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5dc4acd"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49b0321c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_998ee768");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e2c6672");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4e2be81c"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c2bb14db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_998ee768"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_removeTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_54c95fe4");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3e2c6672"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4d118a1d");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9be41f8"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_562787e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58879de3"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc1(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0031bea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a00185a"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b82fdb5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92ca8435"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86991d8f");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6919fb6b"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fdc18de4");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aa693144"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7bf4c415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51573633"))
        {
            corvette_yondalla_neutral_rescue_action_clearLocations(player, npc);
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dc0cdd5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ec881702"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_2b741d06");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9be41f8"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_562787e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58879de3"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc1(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0031bea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a00185a"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b82fdb5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92ca8435"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86991d8f");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6919fb6b"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fdc18de4");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9be41f8"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_562787e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58879de3"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc1(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0031bea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a00185a"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b82fdb5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92ca8435"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86991d8f");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6919fb6b"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fdc18de4");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9be41f8"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_562787e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58879de3"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc1(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0031bea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a00185a"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b82fdb5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92ca8435"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86991d8f");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6919fb6b"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fdc18de4");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9be41f8"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_giveTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_562787e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58879de3"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc1(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0031bea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a00185a"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_takeDoc2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b82fdb5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasObject03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition_hasNoObjectsAndNoTicket(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9be41f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58879de3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a00185a");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ca8435");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6919fb6b");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92ca8435"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86991d8f");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6919fb6b"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fdc18de4");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9acd930"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_fd1dd7d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fa7b5bf6"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_d0344ca8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7837378f"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_7952bf9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7147ad"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c41dc");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3dfd3c32"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7bc000");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dfb8d44e"))
        {
            corvette_yondalla_neutral_rescue_action_clearLocations(player, npc);
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2bdf5b50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_259d132f"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_839794f9");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9acd930"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_fd1dd7d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fa7b5bf6"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_d0344ca8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7837378f"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_7952bf9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7147ad"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c41dc");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3dfd3c32"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7bc000");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9acd930"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_fd1dd7d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fa7b5bf6"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_d0344ca8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7837378f"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_7952bf9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7147ad"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c41dc");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3dfd3c32"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7bc000");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9acd930"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_fd1dd7d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fa7b5bf6"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_d0344ca8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7837378f"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_7952bf9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7147ad"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c41dc");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3dfd3c32"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7bc000");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corvette_yondalla_neutral_rescue_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9acd930"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_fd1dd7d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fa7b5bf6"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_d0344ca8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7837378f"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_heardLocation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_7952bf9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation1(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation2(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!corvette_yondalla_neutral_rescue_condition_knowsLocation3(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9acd930");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b5bf6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7837378f");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7147ad");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dfd3c32");
                    }
                    utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f7147ad"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_acceptsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c41dc");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3dfd3c32"))
        {
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                corvette_yondalla_neutral_rescue_action_quitCorvetteQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7bc000");
                utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
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
            detachScript(self, "conversation.corvette_yondalla_neutral_rescue");
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
        setName(self, "Yondalla");
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
        detachScript(self, "conversation.corvette_yondalla_neutral_rescue");
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
        if (corvette_yondalla_neutral_rescue_condition_dungeonInactive(player, npc))
        {
            corvette_yondalla_neutral_rescue_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_14b16106");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_yondalla_neutral_rescue_condition_earnedCorvetteReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b13f163d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9fb0ad73");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c8d78390");
                }
                utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 2);
                npcStartConversation(player, npc, "corvette_yondalla_neutral_rescue", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_yondalla_neutral_rescue_condition_onDifferentCorvetteQuest(player, npc))
        {
            corvette_yondalla_neutral_rescue_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_310d2ff3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_yondalla_neutral_rescue_condition_hasTravelTicket(player, npc))
        {
            corvette_yondalla_neutral_rescue_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_9fe2c46a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_yondalla_neutral_rescue_condition_hasStuff(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5dc4acd");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4e2be81c");
                }
                utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 7);
                npcStartConversation(player, npc, "corvette_yondalla_neutral_rescue", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_yondalla_neutral_rescue_condition_isOnQuest(player, npc))
        {
            corvette_yondalla_neutral_rescue_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_e248445b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_yondalla_neutral_rescue_condition_hasStuff(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aa693144");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51573633");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ec881702");
                }
                utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 13);
                npcStartConversation(player, npc, "corvette_yondalla_neutral_rescue", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
        {
            corvette_yondalla_neutral_rescue_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_f5664125");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corvette_yondalla_neutral_rescue_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dfb8d44e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_259d132f");
                }
                utils.setScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId", 23);
                npcStartConversation(player, npc, "corvette_yondalla_neutral_rescue", message, responses);
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
        if (!conversationId.equals("corvette_yondalla_neutral_rescue"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
        if (branchId == 2 && corvette_yondalla_neutral_rescue_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corvette_yondalla_neutral_rescue_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corvette_yondalla_neutral_rescue_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && corvette_yondalla_neutral_rescue_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && corvette_yondalla_neutral_rescue_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corvette_yondalla_neutral_rescue_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && corvette_yondalla_neutral_rescue_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && corvette_yondalla_neutral_rescue_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corvette_yondalla_neutral_rescue_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && corvette_yondalla_neutral_rescue_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && corvette_yondalla_neutral_rescue_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && corvette_yondalla_neutral_rescue_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corvette_yondalla_neutral_rescue_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corvette_yondalla_neutral_rescue_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && corvette_yondalla_neutral_rescue_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corvette_yondalla_neutral_rescue.branchId");
        return SCRIPT_CONTINUE;
    }
}
