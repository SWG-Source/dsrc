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
import script.library.fs_dyn_village;
import script.library.fs_quests_cc;
import script.library.quests;
import script.library.sui;
import script.library.utils;

public class qtqc_phase_2 extends script.base_script
{
    public qtqc_phase_2()
    {
    }
    public static String c_stringFile = "conversation/qtqc_phase_2";
    public boolean qtqc_phase_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean qtqc_phase_2_condition_isPlayerCommunityCrafting(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            if (community_crafting.isSessionActive(craftingTracker) && community_crafting.isPlayerCrafting(craftingTracker, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean qtqc_phase_2_condition_isCommunityCraftingEnabled(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_cc.testCommunityCraftingEnabled(npc, 2);
    }
    public boolean qtqc_phase_2_condition_canAddPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            if (community_crafting.isSessionActive(craftingTracker) && community_crafting.getNumPlayersCrafting(craftingTracker) < community_crafting.MAX_PLAYERS_PER_PROJECT && !community_crafting.isPlayerCrafting(player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean qtqc_phase_2_condition_isTrackingQuality(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, fs_quests_cc.SCRIPTVAR_TRACKING_QUALITY);
    }
    public boolean qtqc_phase_2_condition_isTrackingQuantity(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, fs_quests_cc.SCRIPTVAR_TRACKING_QUANTITY);
    }
    public boolean qtqc_phase_2_condition_isTrackingSlots(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, fs_quests_cc.SCRIPTVAR_TRACKING_SLOTS);
    }
    public boolean qtqc_phase_2_condition_playerNeedsSchematics(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            if (quests.isActive(quharek_phase_2.QUEST_STEP_01, player) && !community_crafting.isPlayerCrafting(craftingTracker, player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean qtqc_phase_2_condition_needsOneMoreItem(obj_id player, obj_id npc) throws InterruptedException
    {
        return qtqc_phase_2_tokenDI_ingredientsNeeded(player, npc) == 1;
    }
    public boolean qtqc_phase_2_condition_needsNoMoreItems(obj_id player, obj_id npc) throws InterruptedException
    {
        return qtqc_phase_2_tokenDI_ingredientsNeeded(player, npc) < 1;
    }
    public boolean qtqc_phase_2_condition_needsMoreThanOneItem(obj_id player, obj_id npc) throws InterruptedException
    {
        return qtqc_phase_2_tokenDI_ingredientsNeeded(player, npc) > 1;
    }
    public void qtqc_phase_2_action_addPlayerToProject(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            boolean result = community_crafting.grantSchematicToPlayer(craftingTracker, player);
        }
    }
    public void qtqc_phase_2_action_openMyInventoryToPlayer(obj_id player, obj_id npc) throws InterruptedException
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
    public void qtqc_phase_2_action_showPlayerSlot1Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 1, true, 2);
    }
    public void qtqc_phase_2_action_showPlayerProjectAttributes(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectAttribs(npc, player, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot2Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 2, true, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot4Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 4, true, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot5Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 5, true, 2);
    }
    public void qtqc_phase_2_action_showPlayerProjectQuality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 0, true, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot3Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 3, true, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot1Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 1, false, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot2Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 2, false, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot4Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 4, false, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot5Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 5, false, 2);
    }
    public void qtqc_phase_2_action_showPlayerProjectQuantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 0, false, 2);
    }
    public void qtqc_phase_2_action_showPlayerSlot3Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 3, false, 2);
    }
    public int qtqc_phase_2_tokenDI_ingredientsNeeded(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            return community_crafting.getNumIngredientsNeededByPlayer(craftingTracker, player);
        }
        return 0;
    }
    public static final String OBJVAR_LAST_PHASE = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + ".lastPhase";
    public static final String SCRIPTVAR_MY_ID = "community_crafting.qtqc";
    public static final String SCRIPT_INVENTORY = "systems.crafting.community_crafting.npc_inventory";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.qtqc_phase_2");
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
        detachScript(self, "conversation.qtqc_phase_2");
        return SCRIPT_CONTINUE;
    }
    public void initializeMe(obj_id self) throws InterruptedException
    {
        setName(self, new string_id("npc_name", "qtqc"));
        obj_id inventory = utils.getInventoryContainer(self);
        if (isIdValid(inventory))
        {
            if (!hasScript(inventory, SCRIPT_INVENTORY))
            {
                attachScript(inventory, SCRIPT_INVENTORY);
            }
        }
        if (!utils.hasScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER))
        {
            requestVillageMaster(self);
        }
        else 
        {
            if (initializeCraftingQuest(self))
            {
                cacheProjectData(self);
            }
        }
    }
    public void requestVillageMaster(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER))
        {
            messageTo(self, "handleRetryGetVillageManager", null, 60.0f, false);
            fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "handleGetVillageManager", self);
        }
    }
    public int handleGetVillageManager(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        String key = "";
        if (params.containsKey("key"))
        {
            key = params.getString("key");
        }
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        if (rslt && key != null && key.length() > 0)
        {
            obj_id id = null;
            if (params.containsKey(key))
            {
                id = params.getObjId(key);
                if (isIdValid(id))
                {
                    utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER, id);
                    debugServerConsoleMsg(self, "Object key '" + key + "' was registered as " + id);
                    if (initializeCraftingQuest(self))
                    {
                        cacheProjectData(self);
                    }
                    return SCRIPT_CONTINUE;
                }
            }
        }
        debugServerConsoleMsg(self, "Failed to find obj id '" + key + "' cluster wide data. Id might not have been registered yet.");
        messageTo(self, "handleRetryGetVillageManager", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRetryGetVillageManager(obj_id self, dictionary params) throws InterruptedException
    {
        requestVillageMaster(self);
        return SCRIPT_CONTINUE;
    }
    public boolean initializeCraftingQuest(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER))
        {
            debugServerConsoleMsg(null, "WARNING: initializeCraftingQuest no crafting tracker");
            return false;
        }
        obj_id villageId = utils.getObjIdScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (!isIdValid(villageId))
        {
            debugServerConsoleMsg(null, "WARNING: initializeCraftingQuest no village");
            return false;
        }
        if (!villageId.isInitialized())
        {
            messageTo(self, "handleReinitialize", null, 5, false);
            return false;
        }
        utils.setScriptVar(villageId, SCRIPTVAR_MY_ID, self);
        obj_id inventory = utils.getInventoryContainer(self);
        if (isIdValid(inventory))
        {
            setObjVar(inventory, community_crafting.OBJVAR_COMMUNITY_CRAFTING_TRACKER, villageId);
        }
        int currentPhase = getIntObjVar(villageId, fs_dyn_village.OBJVAR_CURRENT_PHASE);
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_VILLAGE_PHASE, currentPhase);
        return currentPhase == 2;
    }
    public int handleReinitialize(obj_id self, dictionary params) throws InterruptedException
    {
        if (initializeCraftingQuest(self))
        {
            cacheProjectData(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void cacheProjectData(obj_id self) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (!isIdValid(craftingTracker))
        {
            return;
        }
        if (!community_crafting.isSessionActive(craftingTracker))
        {
            messageTo(self, "handleReinitialize", null, 30, false);
            return;
        }
        obj_id inventory = utils.getInventoryContainer(self);
        if (isIdValid(inventory))
        {
            setObjVar(inventory, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER, craftingTracker);
        }
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_QUALITY, community_crafting.getIsTrackingQuality(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_QUANTITY, community_crafting.getIsTrackingQuantity(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_SLOTS, community_crafting.getIsTrackingSlots(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_NUM_SLOTS, community_crafting.getNumSlots(craftingTracker));
    }
    public int getNumSlots(obj_id self) throws InterruptedException
    {
        return utils.getIntScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_NUM_SLOTS);
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
        if (qtqc_phase_2_condition_isCommunityCraftingEnabled(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_67afc98f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (qtqc_phase_2_condition_playerNeedsSchematics(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (qtqc_phase_2_condition_isPlayerCommunityCrafting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (qtqc_phase_2_condition_isPlayerCommunityCrafting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (qtqc_phase_2_condition_isPlayerCommunityCrafting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (qtqc_phase_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a6af75d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20141151");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ebcb651f");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2ce1f012");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_baf57f52");
                }
                setObjVar(player, "conversation.qtqc_phase_2.branchId", 1);
                npcStartConversation(player, self, "qtqc_phase_2", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!qtqc_phase_2_condition_isCommunityCraftingEnabled(player, self))
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
        if (!conversationId.equals("qtqc_phase_2"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.qtqc_phase_2.branchId");
        if (branchId == 1 && response.equals("s_3a6af75d"))
        {
            qtqc_phase_2_action_addPlayerToProject(player, self);
            if (qtqc_phase_2_condition_isPlayerCommunityCrafting(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6ef148af");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_adcf90d8");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (!qtqc_phase_2_condition_isPlayerCommunityCrafting(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_31b4836a");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_20141151"))
        {
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4f3fd2ab");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_ebcb651f"))
        {
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                qtqc_phase_2_action_openMyInventoryToPlayer(player, self);
                string_id message = new string_id(c_stringFile, "s_739cd87c");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_2ce1f012"))
        {
            if (qtqc_phase_2_condition_needsOneMoreItem(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9750fd54");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (qtqc_phase_2_condition_needsMoreThanOneItem(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bf623b9");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.digitInteger = qtqc_phase_2_tokenDI_ingredientsNeeded(player, self);
                npcSpeak(player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (qtqc_phase_2_condition_needsNoMoreItems(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_74be10c7");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_baf57f52"))
        {
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_adcf90d8"))
        {
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cdaf165d");
                removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have been given the schematics needed for the project. They can be accessed via the 'mission/community crafting' categories of your crafting tool.  When you have an ingredient ready, see me to add it to the project.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_ff02a401"))
        {
            qtqc_phase_2_action_showPlayerProjectAttributes(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_39019ea3"))
        {
            if (qtqc_phase_2_condition_isTrackingSlots(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4ccd023a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a94bc85");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca02afa6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87a5e151");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cd5966");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_574683dc");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a489b2f6");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_c57c793"))
        {
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4ccd023a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a94bc85");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca02afa6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87a5e151");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36cd5966");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_574683dc");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212d314f");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_2a94bc85"))
        {
            qtqc_phase_2_action_showPlayerSlot1Quality(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_ca02afa6"))
        {
            qtqc_phase_2_action_showPlayerSlot2Quality(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_87a5e151"))
        {
            qtqc_phase_2_action_showPlayerSlot3Quality(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_36cd5966"))
        {
            qtqc_phase_2_action_showPlayerSlot4Quality(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_574683dc"))
        {
            qtqc_phase_2_action_showPlayerSlot5Quality(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_a489b2f6"))
        {
            qtqc_phase_2_action_showPlayerProjectQuality(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_2a94bc85"))
        {
            qtqc_phase_2_action_showPlayerSlot1Quantity(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_ca02afa6"))
        {
            qtqc_phase_2_action_showPlayerSlot2Quantity(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_87a5e151"))
        {
            qtqc_phase_2_action_showPlayerSlot3Quantity(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_36cd5966"))
        {
            qtqc_phase_2_action_showPlayerSlot4Quantity(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_574683dc"))
        {
            qtqc_phase_2_action_showPlayerSlot5Quantity(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_212d314f"))
        {
            qtqc_phase_2_action_showPlayerProjectQuantity(player, self);
            if (qtqc_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (qtqc_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (qtqc_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (qtqc_phase_2_condition_isTrackingQuantity(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39019ea3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c57c793");
                    }
                    setObjVar(player, "conversation.qtqc_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.qtqc_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.qtqc_phase_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
