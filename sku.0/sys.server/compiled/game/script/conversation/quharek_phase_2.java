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
import script.library.fs_quests;
import script.library.fs_quests_cc;
import script.library.quests;
import script.library.sui;
import script.library.utils;

public class quharek_phase_2 extends script.base_script
{
    public quharek_phase_2()
    {
    }
    public static String c_stringFile = "conversation/quharek_phase_2";
    public boolean quharek_phase_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quharek_phase_2_condition_isPlayerCommunityCrafting(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean quharek_phase_2_condition_isCommunityCraftingEnabled(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_cc.testCommunityCraftingEnabled(npc, 2);
    }
    public boolean quharek_phase_2_condition_canAddPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            if (community_crafting.isSessionActive(craftingTracker) && getIntObjVar(craftingTracker, OBJVAR_CRAFTERS_COUNT) < community_crafting.getMaxPlayersPerProject() && !community_crafting.isPlayerCrafting(player))
            {
                return true;
            }
        }
        return false;
    }
    public boolean quharek_phase_2_condition_isTrackingQuality(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, fs_quests_cc.SCRIPTVAR_TRACKING_QUALITY);
    }
    public boolean quharek_phase_2_condition_isTrackingQuantity(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, fs_quests_cc.SCRIPTVAR_TRACKING_QUANTITY);
    }
    public boolean quharek_phase_2_condition_isTrackingSlots(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, fs_quests_cc.SCRIPTVAR_TRACKING_SLOTS);
    }
    public boolean quharek_phase_2_condition_isPlayerCraftingOrQuesting(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isGod(player) && !fs_quests.isVillageEligible(player))
        {
            return true;
        }
        boolean result = quharek_phase_2_condition_isPlayerCommunityCrafting(player, npc);
        if (result)
        {
            return true;
        }
        if (quests.isActive(QUEST_MAIN, player) || quests.isComplete(QUEST_MAIN, player))
        {
            return true;
        }
        if (fs_quests.hasQuestAccepted(player))
        {
            return true;
        }
        return false;
    }
    public boolean quharek_phase_2_condition_isPlayerSkilled(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_artisan_novice");
    }
    public boolean quharek_phase_2_condition_canAddPlayerAndIsSkilled(obj_id player, obj_id npc) throws InterruptedException
    {
        return quharek_phase_2_condition_canAddPlayer(player, npc) && quharek_phase_2_condition_isPlayerSkilled(player, npc);
    }
    public boolean quharek_phase_2_condition_hasPlayerFinishedQuestAndNotCrafting(obj_id player, obj_id npc) throws InterruptedException
    {
        return isQuestComplete(player, quests.getQuestId(QUEST_STEP_02)) && !quharek_phase_2_condition_isPlayerCommunityCrafting(player, npc);
    }
    public boolean quharek_phase_2_condition_hasPlayerFinishedQuestAndIsCrafting(obj_id player, obj_id npc) throws InterruptedException
    {
        return isQuestComplete(player, quests.getQuestId(QUEST_STEP_02)) && quharek_phase_2_condition_isPlayerCommunityCrafting(player, npc);
    }
    public void quharek_phase_2_action_showPlayerSlot1Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 1, true, 2);
    }
    public void quharek_phase_2_action_showPlayerProjectAttributes(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectAttribs(npc, player, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot2Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 2, true, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot4Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 4, true, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot5Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 5, true, 2);
    }
    public void quharek_phase_2_action_showPlayerProjectQuality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 0, true, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot3Quality(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 3, true, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot1Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 1, false, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot2Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 2, false, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot4Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 4, false, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot5Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 5, false, 2);
    }
    public void quharek_phase_2_action_showPlayerProjectQuantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 0, false, 2);
    }
    public void quharek_phase_2_action_showPlayerSlot3Quantity(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests_cc.showPlayerProjectStats(npc, player, 3, false, 2);
    }
    public void quharek_phase_2_action_grantPlayerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate(QUEST_MAIN, player, npc);
        attachScript(player, QUEST_CLEANUP);
        fs_quests.setQuestAccepted(player);
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            int currentCrafters = getIntObjVar(craftingTracker, OBJVAR_CRAFTERS_COUNT);
            setObjVar(craftingTracker, OBJVAR_CRAFTERS_COUNT, currentCrafters + 1);
        }
        if (utils.hasWaypoint(player, "@npc_name:qtqc") == null)
        {
            obj_id waypoint = createWaypointInDatapad(player, utils.getObjIdScriptVar(npc, SCRIPTVAR_QTQC));
            if (isIdValid(waypoint))
            {
                setWaypointName(waypoint, "@npc_name:qtqc");
                setWaypointColor(waypoint, "yellow");
                setWaypointActive(waypoint, true);
            }
        }
    }
    public int quharek_phase_2_tokenDI_minIngredientsRequired(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(npc, fs_quests_cc.SCRIPTVAR_MIN_INGREDIENTS);
    }
    public static final String QUEST_MAIN = "fs_phase_2_craft_defenses_main";
    public static final String QUEST_STEP_01 = "fs_phase_2_craft_defenses_01";
    public static final String QUEST_STEP_02 = "fs_phase_2_craft_defenses_02";
    public static final String QUEST_CLEANUP = "quest.force_sensitive.fs_craft_village_defenses";
    public static final String DATATABLE_CRAFTING_QUESTS = "datatables/quest/force_sensitive/community_crafting.iff";
    public static final String DATATABLE_CQ_PHASE = "phase";
    public static final String DATATABLE_CQ_SCHEMATIC = "schematic";
    public static final String DATATABLE_CQ_PRIZE = "prize";
    public static final String DATATABLE_CQ_TRACK_QUANTITY = "track_quantity";
    public static final String DATATABLE_CQ_TRACK_QUALTITY = "track_quality";
    public static final String DATATABLE_CQ_TRACK_SLOTS = "track_slots";
    public static final String DATATABLE_CQ_MIN_INGREDIENTS = "min_ingredients";
    public static final String OBJVAR_CRAFTERS_COUNT = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + ".numCrafters";
    public static final String OBJVAR_LAST_PHASE = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + ".lastPhase";
    public static final String SCRIPTVAR_QTQC = "community_crafting.qtqc";
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
        detachScript(self, "conversation.quharek_phase_2");
        return SCRIPT_CONTINUE;
    }
    public void initializeMe(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER))
        {
            debugServerConsoleMsg(self, "quharek initMe asking for village manager");
            requestVillageMaster(self);
        }
        else 
        {
            debugServerConsoleMsg(self, "quharek initMe found village manager");
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
            debugServerConsoleMsg(self, "quharek requestVillageMaster asking for village manager");
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
        if (!utils.hasScriptVar(villageId, SCRIPTVAR_QTQC))
        {
            messageTo(self, "handleReinitialize", null, 15, false);
            return false;
        }
        utils.setScriptVar(self, SCRIPTVAR_QTQC, utils.getObjIdScriptVar(villageId, SCRIPTVAR_QTQC));
        int currentPhase = getIntObjVar(villageId, fs_dyn_village.OBJVAR_CURRENT_PHASE);
        int lastPhase = getIntObjVar(villageId, OBJVAR_LAST_PHASE);
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_VILLAGE_PHASE, currentPhase);
        debugServerConsoleMsg(null, "Quharek current phase = " + currentPhase + ", last phase = " + lastPhase);
        if ((currentPhase != lastPhase && currentPhase != 0) || (!quharek_phase_2_condition_isCommunityCraftingEnabled(obj_id.NULL_ID, self) && currentPhase == 2))
        {
            if (currentPhase == 2)
            {
                community_crafting.cleanup(villageId);
                removeObjVar(villageId, fs_dyn_village.OBJVAR_VILLAGE_DEFENSES);
                int row = dataTableSearchColumnForInt(2, DATATABLE_CQ_PHASE, DATATABLE_CRAFTING_QUESTS);
                if (row < 0)
                {
                    debugServerConsoleMsg(null, "WARNING: initializeCraftingQuest could not find datatable entry for phase 2");
                    return false;
                }
                String schematic = dataTableGetString(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_SCHEMATIC);
                if (schematic == null || schematic.length() == 0)
                {
                    debugServerConsoleMsg(null, "WARNING: initializeCraftingQuest could not find schematic entry for phase 2");
                    return false;
                }
                String prize = dataTableGetString(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_PRIZE);
                boolean trackQuantity = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_TRACK_QUANTITY) != 0;
                boolean trackQuality = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_TRACK_QUALTITY) != 0;
                boolean trackSlots = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_TRACK_SLOTS) != 0;
                boolean result = community_crafting.setMasterSchematic(villageId, schematic, trackQuantity, trackQuality, trackSlots, prize);
                if (!result)
                {
                    debugServerConsoleMsg(null, "WARNING: initializeCraftingQuest error creating phase 2 crafting quest");
                    return false;
                }
                int minIngredients = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_MIN_INGREDIENTS);
                debugServerConsoleMsg(null, "Initialized crafting quest for phase 2, setting min ingredient count to " + minIngredients);
                community_crafting.setMinimumQuantity(villageId, minIngredients);
                setObjVar(villageId, OBJVAR_CRAFTERS_COUNT, 0);
                setObjVar(villageId, OBJVAR_LAST_PHASE, currentPhase);
            }
        }
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
        debugServerConsoleMsg(null, "cacheProjectData enter");
        obj_id craftingTracker = utils.getObjIdScriptVar(self, fs_quests_cc.SCRIPTVAR_CRAFTING_TRACKER);
        if (!isIdValid(craftingTracker))
        {
            return;
        }
        if (!community_crafting.isSessionActive(craftingTracker))
        {
            return;
        }
        String[] scripts = community_crafting.getSchematicScripts(craftingTracker);
        if (scripts != null)
        {
            for (int i = 0; i < scripts.length; ++i)
            {
                if (scripts[i] != null && !hasScript(craftingTracker, scripts[i]))
                {
                    attachScript(craftingTracker, scripts[i]);
                }
            }
        }
        int myCrafters = getIntObjVar(craftingTracker, OBJVAR_CRAFTERS_COUNT);
        int registeredCrafters = community_crafting.getNumPlayersCrafting(craftingTracker);
        if (registeredCrafters > myCrafters)
        {
            setObjVar(craftingTracker, OBJVAR_CRAFTERS_COUNT, registeredCrafters);
        }
        debugServerConsoleMsg(null, "cacheProjectData caching data");
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_QUALITY, community_crafting.getIsTrackingQuality(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_QUANTITY, community_crafting.getIsTrackingQuantity(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_SLOTS, community_crafting.getIsTrackingSlots(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_TRACKING_NUM_SLOTS, community_crafting.getNumSlots(craftingTracker));
        utils.setScriptVar(self, fs_quests_cc.SCRIPTVAR_MIN_INGREDIENTS, community_crafting.getMinimumQuantity(craftingTracker));
        debugServerConsoleMsg(null, "Caching min ingredient count to " + community_crafting.getMinimumQuantity(craftingTracker));
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
        if (quharek_phase_2_condition_hasPlayerFinishedQuestAndIsCrafting(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_da07ca0e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!quharek_phase_2_condition_isPlayerCraftingOrQuesting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quharek_phase_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_955b2ddb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_baf57f52");
                }
                setObjVar(player, "conversation.quharek_phase_2.branchId", 3);
                npcStartConversation(player, self, "quharek_phase_2", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quharek_phase_2_condition_hasPlayerFinishedQuestAndNotCrafting(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_15c7f70f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!quharek_phase_2_condition_isPlayerCraftingOrQuesting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quharek_phase_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_955b2ddb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_baf57f52");
                }
                setObjVar(player, "conversation.quharek_phase_2.branchId", 3);
                npcStartConversation(player, self, "quharek_phase_2", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quharek_phase_2_condition_isCommunityCraftingEnabled(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_c1dac897");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!quharek_phase_2_condition_isPlayerCraftingOrQuesting(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quharek_phase_2_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_955b2ddb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_baf57f52");
                }
                setObjVar(player, "conversation.quharek_phase_2.branchId", 3);
                npcStartConversation(player, self, "quharek_phase_2", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!quharek_phase_2_condition_isCommunityCraftingEnabled(player, self))
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
        if (!conversationId.equals("quharek_phase_2"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.quharek_phase_2.branchId");
        if (branchId == 1 && response.equals("s_955b2ddb"))
        {
            if (quharek_phase_2_condition_canAddPlayerAndIsSkilled(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cf89ace1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99347a8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e25e215d");
                    }
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.digitInteger = quharek_phase_2_tokenDI_minIngredientsRequired(player, self);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.digitInteger = quharek_phase_2_tokenDI_minIngredientsRequired(player, self);
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (!quharek_phase_2_condition_isPlayerSkilled(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ef01f5ee");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!quharek_phase_2_condition_canAddPlayer(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a1a54741");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thanks for helping out with the defenses. You can still supply more ingredients if you'd like.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_baf57f52"))
        {
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thanks for helping out with the defenses. You can still supply more ingredients if you'd like.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_955b2ddb"))
        {
            if (quharek_phase_2_condition_canAddPlayerAndIsSkilled(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cf89ace1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99347a8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e25e215d");
                    }
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.digitInteger = quharek_phase_2_tokenDI_minIngredientsRequired(player, self);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.digitInteger = quharek_phase_2_tokenDI_minIngredientsRequired(player, self);
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (!quharek_phase_2_condition_isPlayerSkilled(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ef01f5ee");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!quharek_phase_2_condition_canAddPlayer(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a1a54741");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thanks for helping out with the defenses.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_baf57f52"))
        {
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thanks for helping out with the defenses.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_955b2ddb"))
        {
            if (quharek_phase_2_condition_canAddPlayerAndIsSkilled(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cf89ace1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99347a8f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e25e215d");
                    }
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.digitInteger = quharek_phase_2_tokenDI_minIngredientsRequired(player, self);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.digitInteger = quharek_phase_2_tokenDI_minIngredientsRequired(player, self);
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (!quharek_phase_2_condition_isPlayerSkilled(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ef01f5ee");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!quharek_phase_2_condition_canAddPlayer(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a1a54741");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How may I help you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_baf57f52"))
        {
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How may I help you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_99347a8f"))
        {
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a9474f4a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f68f375");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f9d5461c");
                    }
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, we need people to help create defenses for the village. You'll need to contribute at least %DI ingredients to the project. If you do, it will help you gain insight into how the Force can be used to improve your crafting technique. We're also giving awards to whoever brings the most or best ingredients. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_e25e215d"))
        {
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6f1f7b10");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, we need people to help create defenses for the village. You'll need to contribute at least %DI ingredients to the project. If you do, it will help you gain insight into how the Force can be used to improve your crafting technique. We're also giving awards to whoever brings the most or best ingredients. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_4f68f375"))
        {
            quharek_phase_2_action_grantPlayerQuest(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e0c642e");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ok. You should know that if you help out with the defenses, you won't be able to do any other work here in the village until later. Is that ok?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_f9d5461c"))
        {
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6f1f7b10");
                removeObjVar(player, "conversation.quharek_phase_2.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ok. You should know that if you help out with the defenses, you won't be able to do any other work here in the village until later. Is that ok?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_ff02a401"))
        {
            quharek_phase_2_action_showPlayerProjectAttributes(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            if (quharek_phase_2_condition_isTrackingSlots(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4ccd023a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4ccd023a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot1Quality(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot2Quality(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot3Quality(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot4Quality(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot5Quality(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerProjectQuality(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot1Quantity(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot2Quantity(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot3Quantity(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot4Quantity(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerSlot5Quantity(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
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
            quharek_phase_2_action_showPlayerProjectQuantity(player, self);
            if (quharek_phase_2_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quharek_phase_2_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quharek_phase_2_condition_isTrackingQuality(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quharek_phase_2_condition_isTrackingQuantity(player, self))
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
                    setObjVar(player, "conversation.quharek_phase_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quharek_phase_2.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Which ingredient would you like to know about?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.quharek_phase_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
