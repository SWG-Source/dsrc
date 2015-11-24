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
import script.library.conversation;
import script.library.factions;
import script.library.gcw;
import script.library.groundquests;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class imperial_defensive_supply_terminal extends script.base_script
{
    public imperial_defensive_supply_terminal()
    {
    }
    public static String c_stringFile = "conversation/imperial_defensive_supply_terminal";
    public boolean imperial_defensive_supply_terminal_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean imperial_defensive_supply_terminal_condition_isTrader(obj_id player, obj_id npc) throws InterruptedException
    {
        int level = utils.getLevel(player);
        if (utils.isProfession(player, utils.TRADER) && (level > 45))
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_isRebelPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isRebelorRebelHelper(player));
    }
    public boolean imperial_defensive_supply_terminal_condition_isImperialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) && !factions.isOnLeave(player))
        {
            return true;
        }
        if (factions.isImperialHelper(player))
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_isSliced(obj_id player, obj_id npc) throws InterruptedException
    {
        int cband = getIntObjVar(npc, "gcw.contraband");
        if ((cband > 0) && (utils.isProfession(player, utils.TRADER)))
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_canGetCband(obj_id player, obj_id npc) throws InterruptedException
    {
        int time = getGameTime();
        int oldTime = utils.getIntScriptVar(player, "gcw.gotCbandTime");
        time = (time - oldTime);
        if (time > 30)
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_isGodCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public boolean imperial_defensive_supply_terminal_condition_isSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.SMUGGLER);
    }
    public boolean imperial_defensive_supply_terminal_condition_isMaxScanLevel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return false;
        }
        if (utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            int scanTier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
            if (scanTier >= 10)
            {
                return true;
            }
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_isSlicing(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_maxScanLevel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return false;
        }
        if (utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            int scanTier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
            if (scanTier >= 10)
            {
                return false;
            }
        }
        return true;
    }
    public boolean imperial_defensive_supply_terminal_condition_missingSequence(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            groundquests.clearQuest(player, gcw.GCW_SMUGGLER_SLICING);
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_noSlicingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_notMissingCombination(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_sliceInstruction(obj_id player, obj_id npc) throws InterruptedException
    {
        int scanTier = 0;
        if (utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            scanTier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
        }
        if (!utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            return false;
        }
        int[] sliceSequence = utils.getIntArrayScriptVar(player, "gcw.sliceSequence");
        if (sliceSequence[scanTier] == 2)
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_sliceOpCore(obj_id player, obj_id npc) throws InterruptedException
    {
        int scanTier = 0;
        if (utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            scanTier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
        }
        if (!utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            return false;
        }
        int[] sliceSequence = utils.getIntArrayScriptVar(player, "gcw.sliceSequence");
        if (sliceSequence[scanTier] == 3)
        {
            return true;
        }
        return false;
    }
    public boolean imperial_defensive_supply_terminal_condition_slicePipeline(obj_id player, obj_id npc) throws InterruptedException
    {
        int scanTier = 0;
        if (utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            scanTier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
        }
        if (!utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            return false;
        }
        int[] sliceSequence = utils.getIntArrayScriptVar(player, "gcw.sliceSequence");
        if (sliceSequence[scanTier] == 1)
        {
            return true;
        }
        return false;
    }
    public void imperial_defensive_supply_terminal_action_giveBarricadeItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_energy_absorbant", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_high_density_plating", pInv);
    }
    public void imperial_defensive_supply_terminal_action_giveTowerItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_heat_resistant_wiring", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_high_performance_circuit_board", pInv);
    }
    public void imperial_defensive_supply_terminal_action_giveReinforcementItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_adrenalin_stimulant", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_bacta_injectors", pInv);
    }
    public void imperial_defensive_supply_terminal_action_giveTurretItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_rapid_firing_mech", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_heat_absorbent_casing", pInv);
    }
    public void imperial_defensive_supply_terminal_action_giveContraband(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (hasObjVar(npc, "gcw.contraband"))
        {
            int cband = getIntObjVar(npc, "gcw.contraband");
            if (cband > 0)
            {
                float fcband = (int)cband;
                obj_id obj = createObject("object/tangible/gcw/crafting_quest/gcw_material_enhancers.iff", pInv, "");
                setObjVar(obj, "crafting_components.charge", fcband);
                setObjVar(obj, "crafting_components.power", fcband);
            }
            utils.setScriptVar(player, "gcw.gotCbandTime", getGameTime());
        }
    }
    public void imperial_defensive_supply_terminal_action_closeSliceSUI(obj_id player, obj_id npc) throws InterruptedException
    {
        int pid = utils.getIntScriptVar(player, "PIDvar");
        sui.closeSUI(player, pid);
    }
    public void imperial_defensive_supply_terminal_action_increaseCharge(obj_id player, obj_id npc) throws InterruptedException
    {
        int charges;
        if (!hasObjVar(npc, "gcw.contraband"))
        {
            charges = 0;
            setObjVar(npc, "gcw.contraband", charges);
        }
        charges = getIntObjVar(npc, "gcw.contraband");
        charges++;
        setObjVar(npc, "gcw.contraband", charges);
    }
    public void imperial_defensive_supply_terminal_action_resetCharges(obj_id player, obj_id npc) throws InterruptedException
    {
        int charges = 0;
        setObjVar(npc, "gcw.contraband", charges);
    }
    public void imperial_defensive_supply_terminal_action_rebelAction(obj_id player, obj_id npc) throws InterruptedException
    {
        chat.chat(npc, player, "Your identity has been associated with a known Rebel collaborator. Please discontinue any use of this terminal and report to the nearest imperial representative for arrest.", 0);
    }
    public void imperial_defensive_supply_terminal_action_correctSlice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            utils.setScriptVar(player, "gcw.terminalScanTier", 0);
        }
        int tier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
        tier++;
        utils.setScriptVar(player, "gcw.terminalScanTier", tier);
    }
    public void imperial_defensive_supply_terminal_action_grantSlicingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, gcw.GCW_SMUGGLER_SLICING))
        {
            groundquests.clearQuest(player, gcw.GCW_SMUGGLER_SLICING);
        }
        utils.setScriptVar(player, "gcw.tier", 0);
        utils.setScriptVar(player, "gcw.maxTier", 0);
        utils.setScriptVar(player, "gcw.terminalScanTier", 0);
        int[] sliceSeqence = new int[10];
        for (int i = 0; i < sliceSeqence.length; i++)
        {
            sliceSeqence[i] = rand(1, 3);
            if (isGod(player))
            {
                sendSystemMessage(player, "sequence nr." + i + " is set to:" + sliceSeqence[i], null);
            }
            utils.setScriptVar(player, "gcw.sliceSequence", sliceSeqence);
        }
        groundquests.grantQuest(player, gcw.GCW_SMUGGLER_SLICING);
    }
    public void imperial_defensive_supply_terminal_action_incorrectSlice(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "gcw.terminalScanTier", 0);
    }
    public void imperial_defensive_supply_terminal_action_startSlicing(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "startSlicing", params, 1.0f, false);
    }
    public int imperial_defensive_supply_terminal_tokenDI_sliceTier(obj_id player, obj_id npc) throws InterruptedException
    {
        int tier = 0;
        if (utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            
        }
        
        {
            tier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
        }
        tier++;
        return tier;
    }
    public int imperial_defensive_supply_terminal_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (imperial_defensive_supply_terminal_condition_isRebelPlayer(player, npc))
            {
                imperial_defensive_supply_terminal_action_rebelAction(player, npc);
                string_id message = new string_id(c_stringFile, "s_227");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isImperialPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_229");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition_isTrader(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition_isSmuggler(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition_isSliced(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (imperial_defensive_supply_terminal_condition_isGodCheck(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_231");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_283");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_317");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_321");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_333");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_231"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_283"))
        {
            imperial_defensive_supply_terminal_action_grantSlicingQuest(player, npc);
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_89"))
        {
            if (imperial_defensive_supply_terminal_condition_canGetCband(player, npc))
            {
                imperial_defensive_supply_terminal_action_giveContraband(player, npc);
                string_id message = new string_id(c_stringFile, "s_91");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_93");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_317"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_319");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_321"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_323");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_325");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_329");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_235"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_239");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_245"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_247");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_255"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_257");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_259");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_263");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_265"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_267");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_269");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_273");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_275"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_277");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_239"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_giveBarricadeItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_241");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_243"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_249"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_giveReinforcementItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_251");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_253"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_259"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_giveTowerItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_261");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_263"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_269"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_giveTurretItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_271");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_273"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_287"))
        {
            if (imperial_defensive_supply_terminal_condition_slicePipeline(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_289");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_291");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 15);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_incorrectSlice(player, npc);
                string_id message = new string_id(c_stringFile, "s_295");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 16);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_297"))
        {
            if (imperial_defensive_supply_terminal_condition_sliceInstruction(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_299");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_301");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 17);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_incorrectSlice(player, npc);
                string_id message = new string_id(c_stringFile, "s_305");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 18);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_307"))
        {
            if (imperial_defensive_supply_terminal_condition_sliceOpCore(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_309");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_311");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 19);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_incorrectSlice(player, npc);
                string_id message = new string_id(c_stringFile, "s_315");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 20);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_startSlicing(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_291"))
        {
            imperial_defensive_supply_terminal_action_correctSlice(player, npc);
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_301"))
        {
            imperial_defensive_supply_terminal_action_correctSlice(player, npc);
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_311"))
        {
            imperial_defensive_supply_terminal_action_correctSlice(player, npc);
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                imperial_defensive_supply_terminal_action_startSlicing(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_325"))
        {
            imperial_defensive_supply_terminal_action_increaseCharge(player, npc);
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_327");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_329"))
        {
            imperial_defensive_supply_terminal_action_resetCharges(player, npc);
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_331");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int imperial_defensive_supply_terminal_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (imperial_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_285");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_307");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = imperial_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85"))
        {
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87");
                utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.imperial_defensive_supply_terminal");
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
        detachScript(self, "conversation.imperial_defensive_supply_terminal");
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
        if (imperial_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (imperial_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 1);
                npcStartConversation(player, npc, "imperial_defensive_supply_terminal", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (imperial_defensive_supply_terminal_condition_isSlicing(player, npc))
        {
            imperial_defensive_supply_terminal_action_closeSliceSUI(player, npc);
            string_id message = new string_id(c_stringFile, "s_78");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (imperial_defensive_supply_terminal_condition_notMissingCombination(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (imperial_defensive_supply_terminal_condition_missingSequence(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                }
                utils.setScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId", 32);
                npcStartConversation(player, npc, "imperial_defensive_supply_terminal", message, responses);
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
        if (!conversationId.equals("imperial_defensive_supply_terminal"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
        if (branchId == 1 && imperial_defensive_supply_terminal_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && imperial_defensive_supply_terminal_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && imperial_defensive_supply_terminal_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && imperial_defensive_supply_terminal_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && imperial_defensive_supply_terminal_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && imperial_defensive_supply_terminal_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && imperial_defensive_supply_terminal_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && imperial_defensive_supply_terminal_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && imperial_defensive_supply_terminal_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && imperial_defensive_supply_terminal_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && imperial_defensive_supply_terminal_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && imperial_defensive_supply_terminal_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && imperial_defensive_supply_terminal_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && imperial_defensive_supply_terminal_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && imperial_defensive_supply_terminal_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && imperial_defensive_supply_terminal_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && imperial_defensive_supply_terminal_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.imperial_defensive_supply_terminal.branchId");
        return SCRIPT_CONTINUE;
    }
}
