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
import script.library.community_crafting;
import script.library.sui;
import script.library.utils;

public class community_crafter extends script.base_script
{
    public community_crafter()
    {
    }
    public static String c_stringFile = "conversation/community_crafter";
    public boolean community_crafter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean community_crafter_condition_isPlayerCommunityCrafting(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = getObjIdObjVar(npc, OBJVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            if (community_crafting.isSessionActive(craftingTracker) && community_crafting.isPlayerCrafting(craftingTracker, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean community_crafter_condition_isCommunityCraftingEnabled(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = getObjIdObjVar(npc, OBJVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            return community_crafting.isSessionActive(craftingTracker);
        }
        return false;
    }
    public boolean community_crafter_condition_canAddPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = getObjIdObjVar(npc, OBJVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            if (community_crafting.isSessionActive(craftingTracker) && community_crafting.getNumPlayersCrafting(craftingTracker) < community_crafting.MAX_PLAYERS_PER_PROJECT && !community_crafting.isPlayerCrafting(player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean community_crafter_condition_isTrackingQuality(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, SCRIPTVAR_TRACKING_QUALITY);
    }
    public boolean community_crafter_condition_isTrackingQuantity(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, SCRIPTVAR_TRACKING_QUANTITY);
    }
    public boolean community_crafter_condition_isTrackingSlots(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, SCRIPTVAR_TRACKING_SLOTS);
    }
    public boolean community_crafter_condition_hasSlot1(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 1;
    }
    public boolean community_crafter_condition_hasSlot2(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 2;
    }
    public boolean community_crafter_condition_hasSlot3(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 3;
    }
    public boolean community_crafter_condition_hasSlot4(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 4;
    }
    public boolean community_crafter_condition_hasSlot5(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 5;
    }
    public boolean community_crafter_condition_hasSlot6(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 6;
    }
    public boolean community_crafter_condition_hasSlot7(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 7;
    }
    public boolean community_crafter_condition_hasSlot8(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 8;
    }
    public boolean community_crafter_condition_hasSlot9(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 9;
    }
    public boolean community_crafter_condition_hasSlot10(obj_id player, obj_id npc) throws InterruptedException
    {
        return getNumSlots(npc) >= 10;
    }
    public void community_crafter_action_addPlayerToProject(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = getObjIdObjVar(npc, OBJVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            boolean result = community_crafting.grantSchematicToPlayer(craftingTracker, player);
        }
    }
    public void community_crafter_action_openMyInventoryToPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(npc);
        if (isIdValid(inventory))
        {
            if (queueCommand(player, (1880585606), inventory, "", COMMAND_PRIORITY_IMMEDIATE))
            {
                dictionary params = new dictionary();
                params.put("npc", npc);
                messageTo(player, "handleViewingNpcInventory", params, 15, false);
            }
        }
    }
    public void community_crafter_action_showPlayerSlot1Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 1, true);
    }
    public void community_crafter_action_showPlayerProjectAttributes(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectAttribs(npc, player);
    }
    public void community_crafter_action_showPlayerSlot2Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 2, true);
    }
    public void community_crafter_action_showPlayerSlot4Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 4, true);
    }
    public void community_crafter_action_showPlayerSlot5Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 5, true);
    }
    public void community_crafter_action_showPlayerSlot6Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 6, true);
    }
    public void community_crafter_action_showPlayerSlot7Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 7, true);
    }
    public void community_crafter_action_showPlayerSlot8Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 8, true);
    }
    public void community_crafter_action_showPlayerSlot9Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 9, true);
    }
    public void community_crafter_action_showPlayerSlot10Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 10, true);
    }
    public void community_crafter_action_showPlayerProjectQuality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 0, true);
    }
    public void community_crafter_action_showPlayerSlot3Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 3, true);
    }
    public void community_crafter_action_showPlayerSlot1Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 1, false);
    }
    public void community_crafter_action_showPlayerSlot2Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 2, false);
    }
    public void community_crafter_action_showPlayerSlot4Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 4, false);
    }
    public void community_crafter_action_showPlayerSlot5Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 5, false);
    }
    public void community_crafter_action_showPlayerSlot6Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 6, false);
    }
    public void community_crafter_action_showPlayerSlot7Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 7, false);
    }
    public void community_crafter_action_showPlayerSlot8Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 8, false);
    }
    public void community_crafter_action_showPlayerSlot9Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 9, false);
    }
    public void community_crafter_action_showPlayerSlot10Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 10, false);
    }
    public void community_crafter_action_showPlayerProjectQuantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 0, false);
    }
    public void community_crafter_action_showPlayerSlot3Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        showPlayerProjectStats(npc, player, 3, false);
    }
    public static final java.text.NumberFormat qualityFormat = new java.text.DecimalFormat("####.00");
    public static final String OBJVAR_CRAFTING_TRACKER = community_crafting.OBJVAR_COMMUNITY_CRAFTING_TRACKER;
    public static final String SCRIPTVAR_TRACKING_QUALITY = "community_crafting.quality";
    public static final String SCRIPTVAR_TRACKING_QUANTITY = "community_crafting.quantity";
    public static final String SCRIPTVAR_TRACKING_SLOTS = "community_crafting.slots";
    public static final String SCRIPTVAR_TRACKING_NUM_SLOTS = "community_crafting.numSlots";
    public static final string_id SID_RANKINGS_TITLE = new string_id("crafting", "player_rankings");
    public static final string_id SID_ATTRIBUTES_TITLE = new string_id("crafting", "project_attributes");
    public static final string_id SID_NO_PLAYERS = new string_id("crafting", "no_players");
    public static final String SCRIPT_INVENTORY = "systems.crafting.community_crafting.npc_inventory";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.community_crafter");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        initializeMe(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        initializeMe(self);
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
        detachScript(self, "conversation.community_crafter");
        return SCRIPT_CONTINUE;
    }
    public void initializeMe(obj_id self) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(self);
        if (isIdValid(inventory))
        {
            if (!hasScript(inventory, SCRIPT_INVENTORY))
            {
                attachScript(inventory, SCRIPT_INVENTORY);
            }
            setObjVar(inventory, OBJVAR_CRAFTING_TRACKER, getObjIdObjVar(self, OBJVAR_CRAFTING_TRACKER));
        }
        cacheProjectData(self);
    }
    public void cacheProjectData(obj_id self) throws InterruptedException
    {
        if (community_crafter_condition_isCommunityCraftingEnabled(obj_id.NULL_ID, self))
        {
            obj_id craftingTracker = getObjIdObjVar(self, OBJVAR_CRAFTING_TRACKER);
            if (!isIdValid(craftingTracker))
            {
                return;
            }
            utils.setScriptVar(self, SCRIPTVAR_TRACKING_QUALITY, community_crafting.getIsTrackingQuality(craftingTracker));
            utils.setScriptVar(self, SCRIPTVAR_TRACKING_QUANTITY, community_crafting.getIsTrackingQuantity(craftingTracker));
            utils.setScriptVar(self, SCRIPTVAR_TRACKING_SLOTS, community_crafting.getIsTrackingSlots(craftingTracker));
            utils.setScriptVar(self, SCRIPTVAR_TRACKING_NUM_SLOTS, community_crafting.getNumSlots(craftingTracker));
        }
    }
    public int getNumSlots(obj_id self) throws InterruptedException
    {
        return utils.getIntScriptVar(self, SCRIPTVAR_TRACKING_NUM_SLOTS);
    }
    public void showPlayerProjectAttribs(obj_id self, obj_id player) throws InterruptedException
    {
        if (community_crafter_condition_isCommunityCraftingEnabled(obj_id.NULL_ID, self))
        {
            obj_id craftingTracker = getObjIdObjVar(self, OBJVAR_CRAFTING_TRACKER);
            if (!isIdValid(craftingTracker))
            {
                return;
            }
            Vector names = new Vector();
            Vector values = new Vector();
            if (!community_crafting.getProjectAttributes(craftingTracker, names, values))
            {
                return;
            }
            String[] attributes = new String[names.size()];
            for (int i = 0; i < attributes.length; ++i)
            {
                attributes[i] = "@" + names.get(i) + " \\>200" + values.get(i);
            }
            int pid = sui.listbox(player, "", "@" + SID_ATTRIBUTES_TITLE, sui.OK_ONLY, attributes);
        }
    }
    public void showPlayerProjectStats(obj_id self, obj_id player, int slot, boolean quality) throws InterruptedException
    {
        if (community_crafter_condition_isCommunityCraftingEnabled(obj_id.NULL_ID, self))
        {
            obj_id craftingTracker = getObjIdObjVar(self, OBJVAR_CRAFTING_TRACKER);
            if (!isIdValid(craftingTracker))
            {
                return;
            }
            Vector playerIds = new Vector();
            Vector playerNames = new Vector();
            Vector values = new Vector();
            if (!community_crafting.getPlayerRanking(craftingTracker, playerIds, playerNames, values, !quality, slot))
            {
                return;
            }
            if (quality)
            {
                for (int i = 0; i < values.size(); ++i)
                {
                    double f = (((Float)(values.get(i)))).doubleValue();
                    values.set(i, qualityFormat.format(f));
                }
            }
            Vector rankings = new Vector();
            for (int i = 0; i < playerIds.size(); ++i)
            {
                if (isIdValid((obj_id)(playerIds.get(i))))
                {
                    if (playerIds.get(i) == player)
                    {
                        rankings.add("\\#pcontrast1 " + getPlayerName((obj_id)(playerIds.get(i))) + "\\>200" + values.get(i) + "\\#.");
                    }
                    else 
                    {
                        rankings.add(getPlayerName((obj_id)(playerIds.get(i))) + "\\>200" + values.get(i));
                    }
                }
            }
            if (rankings.size() == 0)
            {
                rankings.add("@" + SID_NO_PLAYERS);
            }
            int pid = sui.listbox(player, "", "@" + SID_RANKINGS_TITLE, sui.OK_ONLY, rankings);
        }
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
        if (community_crafter_condition_isCommunityCraftingEnabled(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_67afc98f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (community_crafter_condition_canAddPlayer(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (community_crafter_condition_isPlayerCommunityCrafting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (community_crafter_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19fc7d2c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_be3f098d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_df409550");
                }
                setObjVar(player, "conversation.community_crafter.branchId", 1);
                npcStartConversation(player, self, "community_crafter", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!community_crafter_condition_isCommunityCraftingEnabled(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bc2ccc0e");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("community_crafter"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.community_crafter.branchId");
        if (branchId == 1 && response.equals("s_19fc7d2c"))
        {
            community_crafter_action_addPlayerToProject(player, self);
            if (community_crafter_condition_isPlayerCommunityCrafting(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ee1783df");
                removeObjVar(player, "conversation.community_crafter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!community_crafter_condition_isPlayerCommunityCrafting(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_834bacde");
                removeObjVar(player, "conversation.community_crafter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_be3f098d"))
        {
            if (community_crafter_condition__defaultCondition(player, self))
            {
                community_crafter_action_openMyInventoryToPlayer(player, self);
                string_id message = new string_id(c_stringFile, "s_739cd87c");
                removeObjVar(player, "conversation.community_crafter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_df409550"))
        {
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_ff02a401"))
        {
            community_crafter_action_showPlayerProjectAttributes(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_7e041771"))
        {
            if (community_crafter_condition_isTrackingSlots(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_167908ad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition_hasSlot1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_hasSlot2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_hasSlot3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (community_crafter_condition_hasSlot4(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (community_crafter_condition_hasSlot5(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (community_crafter_condition_hasSlot6(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (community_crafter_condition_hasSlot7(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (community_crafter_condition_hasSlot8(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (community_crafter_condition_hasSlot9(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                boolean hasResponse9 = false;
                if (community_crafter_condition_hasSlot10(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse9 = true;
                }
                boolean hasResponse10 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse10 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_939f367c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9edc10a5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9a1d0d12");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_845a5d17");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_809b40a0");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8dd86679");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89197bce");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b156c673");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b597dbc4");
                    }
                    if (hasResponse9)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d2ad4c9c");
                    }
                    if (hasResponse10)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a489b2f6");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_ebaa218a"))
        {
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_167908ad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition_hasSlot1(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_hasSlot2(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_hasSlot3(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (community_crafter_condition_hasSlot4(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (community_crafter_condition_hasSlot5(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (community_crafter_condition_hasSlot6(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (community_crafter_condition_hasSlot7(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (community_crafter_condition_hasSlot8(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (community_crafter_condition_hasSlot9(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                boolean hasResponse9 = false;
                if (community_crafter_condition_hasSlot10(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse9 = true;
                }
                boolean hasResponse10 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse10 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_939f367c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9edc10a5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9a1d0d12");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_845a5d17");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_809b40a0");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8dd86679");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89197bce");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b156c673");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b597dbc4");
                    }
                    if (hasResponse9)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d2ad4c9c");
                    }
                    if (hasResponse10)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212d314f");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_939f367c"))
        {
            community_crafter_action_showPlayerSlot1Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_9edc10a5"))
        {
            community_crafter_action_showPlayerSlot2Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_9a1d0d12"))
        {
            community_crafter_action_showPlayerSlot3Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_845a5d17"))
        {
            community_crafter_action_showPlayerSlot4Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_809b40a0"))
        {
            community_crafter_action_showPlayerSlot5Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_8dd86679"))
        {
            community_crafter_action_showPlayerSlot6Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_89197bce"))
        {
            community_crafter_action_showPlayerSlot7Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b156c673"))
        {
            community_crafter_action_showPlayerSlot8Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b597dbc4"))
        {
            community_crafter_action_showPlayerSlot9Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_d2ad4c9c"))
        {
            community_crafter_action_showPlayerSlot10Quality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_a489b2f6"))
        {
            community_crafter_action_showPlayerProjectQuality(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_939f367c"))
        {
            community_crafter_action_showPlayerSlot1Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_9edc10a5"))
        {
            community_crafter_action_showPlayerSlot2Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_9a1d0d12"))
        {
            community_crafter_action_showPlayerSlot3Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_845a5d17"))
        {
            community_crafter_action_showPlayerSlot4Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_809b40a0"))
        {
            community_crafter_action_showPlayerSlot5Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_8dd86679"))
        {
            community_crafter_action_showPlayerSlot6Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_89197bce"))
        {
            community_crafter_action_showPlayerSlot7Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_b156c673"))
        {
            community_crafter_action_showPlayerSlot8Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_b597dbc4"))
        {
            community_crafter_action_showPlayerSlot9Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_d2ad4c9c"))
        {
            community_crafter_action_showPlayerSlot10Quantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_212d314f"))
        {
            community_crafter_action_showPlayerProjectQuantity(player, self);
            if (community_crafter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_crafter_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_crafter_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_crafter_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff02a401");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e041771");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebaa218a");
                    }
                    setObjVar(player, "conversation.community_crafter.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.community_crafter.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which slot would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.community_crafter.branchId");
        return SCRIPT_CONTINUE;
    }
}
