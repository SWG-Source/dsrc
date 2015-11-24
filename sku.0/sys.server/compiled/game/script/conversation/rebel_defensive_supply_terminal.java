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

public class rebel_defensive_supply_terminal extends script.base_script
{
    public rebel_defensive_supply_terminal()
    {
    }
    public static String c_stringFile = "conversation/rebel_defensive_supply_terminal";
    public boolean rebel_defensive_supply_terminal_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean rebel_defensive_supply_terminal_condition_isTrader(obj_id player, obj_id npc) throws InterruptedException
    {
        int level = utils.getLevel(player);
        if (utils.isProfession(player, utils.TRADER) && (level > 45))
        {
            return true;
        }
        return false;
    }
    public boolean rebel_defensive_supply_terminal_condition_isRebelPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player) && !factions.isOnLeave(player))
        {
            return true;
        }
        if (factions.isRebelHelper(player))
        {
            return true;
        }
        return false;
    }
    public boolean rebel_defensive_supply_terminal_condition_isImperialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isImperialorImperialHelper(player));
    }
    public boolean rebel_defensive_supply_terminal_condition_isSliced(obj_id player, obj_id npc) throws InterruptedException
    {
        int cband = getIntObjVar(npc, "gcw.contraband");
        if ((cband > 0) && (utils.isProfession(player, utils.TRADER)))
        {
            return true;
        }
        return false;
    }
    public boolean rebel_defensive_supply_terminal_condition_canGetCband(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean rebel_defensive_supply_terminal_condition_isGodCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public boolean rebel_defensive_supply_terminal_condition_isMaxScanLevel(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean rebel_defensive_supply_terminal_condition_isSlicing(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return true;
        }
        return false;
    }
    public boolean rebel_defensive_supply_terminal_condition_missingSequence(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            groundquests.clearQuest(player, gcw.GCW_SMUGGLER_SLICING);
            return true;
        }
        return false;
    }
    public boolean rebel_defensive_supply_terminal_condition_noSlicingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return true;
        }
        return false;
    }
    public boolean rebel_defensive_supply_terminal_condition_slicePipeline(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean rebel_defensive_supply_terminal_condition_sliceInstruction(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean rebel_defensive_supply_terminal_condition_sliceOpCore(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean rebel_defensive_supply_terminal_condition_maxScanLevel(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean rebel_defensive_supply_terminal_condition_isSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.SMUGGLER);
    }
    public boolean rebel_defensive_supply_terminal_condition_notMissingCombination(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "gcw.sliceSequence"))
        {
            return true;
        }
        return false;
    }
    public void rebel_defensive_supply_terminal_action_giveBarricadeItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_energy_absorbant", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_high_density_plating", pInv);
    }
    public void rebel_defensive_supply_terminal_action_giveTowerItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_heat_resistant_wiring", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_high_performance_circuit_board", pInv);
    }
    public void rebel_defensive_supply_terminal_action_giveReinforcementItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_adrenalin_stimulant", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_bacta_injectors", pInv);
    }
    public void rebel_defensive_supply_terminal_action_giveTurretItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id obj1 = static_item.createNewItemFunction("item_gcw_rapid_firing_mech", pInv);
        obj_id obj2 = static_item.createNewItemFunction("item_gcw_heat_absorbent_casing", pInv);
    }
    public void rebel_defensive_supply_terminal_action_startSlicing(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "startSlicing", params, 1.0f, false);
    }
    public void rebel_defensive_supply_terminal_action_closeSliceSUI(obj_id player, obj_id npc) throws InterruptedException
    {
        int pid = utils.getIntScriptVar(player, "PIDvar");
        sui.closeSUI(player, pid);
    }
    public void rebel_defensive_supply_terminal_action_increaseCharge(obj_id player, obj_id npc) throws InterruptedException
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
    public void rebel_defensive_supply_terminal_action_resetCharges(obj_id player, obj_id npc) throws InterruptedException
    {
        int charges = 0;
        setObjVar(npc, "gcw.contraband", charges);
    }
    public void rebel_defensive_supply_terminal_action_giveContraband(obj_id player, obj_id npc) throws InterruptedException
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
    public void rebel_defensive_supply_terminal_action_correctSlice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "gcw.terminalScanTier"))
        {
            utils.setScriptVar(player, "gcw.terminalScanTier", 0);
        }
        int tier = utils.getIntScriptVar(player, "gcw.terminalScanTier");
        tier++;
        utils.setScriptVar(player, "gcw.terminalScanTier", tier);
    }
    public void rebel_defensive_supply_terminal_action_incorrectSlice(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "gcw.terminalScanTier", 0);
    }
    public void rebel_defensive_supply_terminal_action_grantSlicingQuest(obj_id player, obj_id npc) throws InterruptedException
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
    public int rebel_defensive_supply_terminal_tokenDI_sliceTier(obj_id player, obj_id npc) throws InterruptedException
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
    public int rebel_defensive_supply_terminal_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (rebel_defensive_supply_terminal_condition_isImperialPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isRebelPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition_isTrader(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition_isSmuggler(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition_isSliced(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_defensive_supply_terminal_condition_isGodCheck(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_271");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_271"))
        {
            rebel_defensive_supply_terminal_action_grantSlicingQuest(player, npc);
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_72"))
        {
            if (rebel_defensive_supply_terminal_condition_canGetCband(player, npc))
            {
                rebel_defensive_supply_terminal_action_giveContraband(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_135"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_144"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_147");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_152");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_192"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_196");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_202"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_212"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_214");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_216");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_222"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_giveBarricadeItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_188");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_190"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_196"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_giveReinforcementItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_198");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_200"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_giveTowerItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_210"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_216"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_giveTurretItems(player, npc);
                string_id message = new string_id(c_stringFile, "s_218");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_220"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_212");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_318"))
        {
            if (rebel_defensive_supply_terminal_condition_slicePipeline(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_320");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_322");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 15);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_incorrectSlice(player, npc);
                string_id message = new string_id(c_stringFile, "s_324");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 16);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_328"))
        {
            if (rebel_defensive_supply_terminal_condition_sliceInstruction(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_330");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_332");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 17);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_incorrectSlice(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 18);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_338"))
        {
            if (rebel_defensive_supply_terminal_condition_sliceOpCore(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_340");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_342");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 19);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_incorrectSlice(player, npc);
                string_id message = new string_id(c_stringFile, "s_344");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_346");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 20);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_348"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_startSlicing(player, npc);
                string_id message = new string_id(c_stringFile, "s_350");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_322"))
        {
            rebel_defensive_supply_terminal_action_correctSlice(player, npc);
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_326"))
        {
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_332"))
        {
            rebel_defensive_supply_terminal_action_correctSlice(player, npc);
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_342"))
        {
            rebel_defensive_supply_terminal_action_correctSlice(player, npc);
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_346"))
        {
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_354"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                rebel_defensive_supply_terminal_action_startSlicing(player, npc);
                string_id message = new string_id(c_stringFile, "s_356");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_147"))
        {
            rebel_defensive_supply_terminal_action_increaseCharge(player, npc);
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_149");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_152"))
        {
            rebel_defensive_supply_terminal_action_resetCharges(player, npc);
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_defensive_supply_terminal_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_357"))
        {
            if (rebel_defensive_supply_terminal_condition_maxScanLevel(player, npc))
            {
                rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = rebel_defensive_supply_terminal_tokenDI_sliceTier(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_isMaxScanLevel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_359"))
        {
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_360");
                utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
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
            detachScript(self, "conversation.rebel_defensive_supply_terminal");
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
        detachScript(self, "conversation.rebel_defensive_supply_terminal");
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
        if (rebel_defensive_supply_terminal_condition_noSlicingQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_defensive_supply_terminal_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 1);
                npcStartConversation(player, npc, "rebel_defensive_supply_terminal", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rebel_defensive_supply_terminal_condition_isSlicing(player, npc))
        {
            rebel_defensive_supply_terminal_action_closeSliceSUI(player, npc);
            string_id message = new string_id(c_stringFile, "s_312");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_defensive_supply_terminal_condition_notMissingCombination(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_defensive_supply_terminal_condition_missingSequence(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_357");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_359");
                }
                utils.setScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId", 32);
                npcStartConversation(player, npc, "rebel_defensive_supply_terminal", message, responses);
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
        if (!conversationId.equals("rebel_defensive_supply_terminal"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
        if (branchId == 1 && rebel_defensive_supply_terminal_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && rebel_defensive_supply_terminal_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && rebel_defensive_supply_terminal_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && rebel_defensive_supply_terminal_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && rebel_defensive_supply_terminal_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && rebel_defensive_supply_terminal_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && rebel_defensive_supply_terminal_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && rebel_defensive_supply_terminal_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && rebel_defensive_supply_terminal_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && rebel_defensive_supply_terminal_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && rebel_defensive_supply_terminal_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && rebel_defensive_supply_terminal_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && rebel_defensive_supply_terminal_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && rebel_defensive_supply_terminal_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && rebel_defensive_supply_terminal_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && rebel_defensive_supply_terminal_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && rebel_defensive_supply_terminal_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rebel_defensive_supply_terminal.branchId");
        return SCRIPT_CONTINUE;
    }
}
