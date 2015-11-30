package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import script.library.collection;
import script.library.prose;
import script.library.static_item;
import script.library.sui;
import script.library.utils;
import script.library.factions;
import script.library.groundquests;
import script.library.gcw;
import script.library.buff;

public class gcw_supply_terminal extends script.base_script
{
    public gcw_supply_terminal()
    {
    }
    public static final String SLICE = "You slice the ";
    public static final int BASE_SLICING_TIME = 5;
    public static final String STRING_FILE = "gcw";
    public static final string_id SID_SLICE = new string_id(STRING_FILE, "slice_terminal");
    public static final String[] SLICE_OPTIONS = 
    {
        "Data Pipeline",
        "Instruction Handler",
        "Operations Core",
        "Reset"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "decreaseSliced", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void initiateSlicing(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        utils.setScriptVar(player, "gcw.tier", 0);
        utils.setScriptVar(player, "gcw.maxTier", 0);
        makeSliceSequence(player);
    }
    public void makeSliceSequence(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        int[] sliceSeqence = new int[10];
        for (int i = 0; i < sliceSeqence.length; i++)
        {
            sliceSeqence[i] = rand(1, 3);
            if (isIdValid(player))
            {
                sendSystemMessage(player, "sequence nr." + i + " is set to:" + sliceSeqence[i], null);
            }
            utils.setScriptVar(player, "gcw.sliceSequence", sliceSeqence);
        }
    }
    public int startSlicing(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!hasQuest(player))
        {
            return SCRIPT_CONTINUE;
        }
        stageMenu(self, player, "" + getText(self, player), "Slice Supply Terminal", SLICE_OPTIONS, "slicingChoice", true, "PIDvar", "scriptVar.scriptVar");
        return SCRIPT_CONTINUE;
    }
    public void correctChoice(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !isIdValid(self) || !exists(self))
        {
            return;
        }
        int sliced = getIntObjVar(self, "gcw.contraband");
        int tier = utils.getIntScriptVar(player, "gcw.tier");
        int maxTier = utils.getIntScriptVar(player, "gcw.maxTier");
        tier++;
        if (tier > sliced)
        {
            setObjVar(self, "gcw.contraband", tier);
        }
        if (tier > maxTier)
        {
            groundquests.sendSignal(player, "sliceTerminal");
            utils.setScriptVar(player, "gcw.maxTier", tier);
            if (tier == 4 || tier == 6 || tier == 8 || tier == 9 || tier == 10)
            {
                obj_id pInv = utils.getInventoryContainer(player);
                if (factions.isImperialorImperialHelper(player))
                {
                    sendSystemMessage(player, "You have recived an Imperial Token for your effort", null);
                    float multiplier = utils.stringToFloat(getConfigSetting("GameServer", "gcwTokenBonus"));
                    int count = 1;
                    if (multiplier > 1)
                    {
                        count = (int)((float)count * (float)multiplier);
                    }
                    if (count > 1)
                    {
                        for (int i = 0; i < count; i++)
                        {
                            static_item.createNewItemFunction(gcw.GCW_IMPERIAL_TOKEN, pInv);
                        }
                    }
                    else 
                    {
                        static_item.createNewItemFunction(gcw.GCW_IMPERIAL_TOKEN, pInv);
                    }
                    if (!collection.hasCompletedCollectionSlot(player, "smuggler_slice_terminal_imperial") && (tier == 6))
                    {
                        collection.modifyCollectionSlotValue(player, "smuggler_slice_terminal_imperial", 1);
                    }
                }
                if (factions.isRebelorRebelHelper(player))
                {
                    sendSystemMessage(player, "You have recived an Rebel Token for your effort", null);
                    float multiplier = utils.stringToFloat(getConfigSetting("GameServer", "gcwTokenBonus"));
                    int count = 1;
                    if (multiplier > 1)
                    {
                        count = (int)((float)count * (float)multiplier);
                    }
                    if (count > 1)
                    {
                        for (int i = 0; i < count; i++)
                        {
                            static_item.createNewItemFunction(gcw.GCW_REBEL_TOKEN, pInv);
                        }
                    }
                    else 
                    {
                        static_item.createNewItemFunction(gcw.GCW_REBEL_TOKEN, pInv);
                    }
                    if (!collection.hasCompletedCollectionSlot(player, "smuggler_slice_terminal_rebel") && (tier == 6))
                    {
                        collection.modifyCollectionSlotValue(player, "smuggler_slice_terminal_rebel", 1);
                    }
                }
            }
        }
        utils.setScriptVar(player, "gcw.tier", tier);
    }
    public void applyFatigue(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        buff.applyBuffWithStackCount(player, "gcw_fatigue", 5);
        sendSystemMessage(player, "You trigger the defense system, resulting in a deblitating shock", null);
    }
    public int slicingChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if ((!utils.hasScriptVar(player, "gcw.sliceSequence")) || (!utils.hasScriptVar(player, "gcw.tier")))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasQuest(player))
        {
            return SCRIPT_CONTINUE;
        }
        idx++;
        if (idx > 3)
        {
            utils.setScriptVar(player, "gcw.tier", 0);
            stageMenu(self, player, "" + getText(self, player), "Slice Supply Terminal", SLICE_OPTIONS, "slicingChoice", true, "PIDvar", "scriptVar.scriptVar");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(player, "gcw.slicing_idx", idx);
        int gameTime = getGameTime();
        int flags = 0;
        flags |= sui.CD_EVENT_LOCOMOTION;
        flags |= sui.CD_EVENT_INCAPACITATE;
        flags |= sui.CD_EVENT_DAMAGED;
        flags |= sui.CD_EVENT_STEALTHED;
        flags |= sui.CD_EVENT_DAMAGE_IMMUNE;
        int captureTime = BASE_SLICING_TIME + gcw.getFatigueTimerMod(player);
        if (captureTime > gcw.GCW_FATIGUE_TIMER_MAX)
        {
            captureTime = gcw.GCW_FATIGUE_TIMER_MAX;
        }
        if (isGod(player))
        {
            captureTime = 3;
        }
        int pid = sui.smartCountdownTimerSUI(self, player, "gcwPylonConstruction", SID_SLICE, 0, captureTime, "handleSlicingAttemptResults", 4.0f, flags);
        return SCRIPT_CONTINUE;
    }
    public int handleSlicingAttemptResults(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !exists(player) || factions.isOnLeave(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasQuest(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_COMBAT)
            {
                sendSystemMessage(player, "Entered Combat", null);
            }
            else if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(player, "Moved", null);
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(player, "Incapped", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        int idx = utils.getIntScriptVar(player, "gcw.slicing_idx");
        int tier = utils.getIntScriptVar(player, "gcw.tier");
        int[] sliceSequence = utils.getIntArrayScriptVar(player, "gcw.sliceSequence");
        if (idx == sliceSequence[tier])
        {
            correctChoice(self, player);
        }
        else 
        {
            utils.setScriptVar(player, "gcw.tier", 0);
            applyFatigue(player);
        }
        if (tier >= 9)
        {
            sendSystemMessage(player, "You have sliced all 10 levels of security", null);
            return SCRIPT_CONTINUE;
        }
        stageMenu(self, player, "" + getText(self, player), "Slice Supply Terminal", SLICE_OPTIONS, "slicingChoice", true, "PIDvar", "scriptVar.scriptVar");
        return SCRIPT_CONTINUE;
    }
    public String getText(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !isIdValid(self) || !exists(self))
        {
            return "Bzzzzt Not working!";
        }
        int txtTier = 0;
        int txtMaxTier = 0;
        int txtSliced = 0;
        if (utils.hasScriptVar(player, "gcw.tier"))
        {
            txtTier = utils.getIntScriptVar(player, "gcw.tier");
        }
        if (utils.hasScriptVar(player, "gcw.maxTier"))
        {
            txtMaxTier = utils.getIntScriptVar(player, "gcw.maxTier");
        }
        if (hasObjVar(self, "sliced"))
        {
            txtSliced = getIntObjVar(self, "sliced");
        }
        String txt = "Current security level: " + txtTier + "\nYour Highest Security Breach: " + txtMaxTier + "\nTerminal Security Levels Disabled: " + txtSliced;
        return txt;
    }
    public int decreaseSliced(obj_id self, dictionary params) throws InterruptedException
    {
        int sliced = 0;
        if (hasObjVar(self, "gcw.contraband"))
        {
            
        }
        
        {
            sliced = getIntObjVar(self, "gcw.contraband");
        }
        if (sliced > 0)
        {
            sliced--;
            setObjVar(self, "gcw.contraband", sliced);
        }
        messageTo(self, "decreaseSliced", null, 120.0f, false);
        return SCRIPT_CONTINUE;
    }
    public boolean hasQuest(obj_id player) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, gcw.GCW_SMUGGLER_SLICING))
        {
            return true;
        }
        return false;
    }
    public void stageMenu(obj_id self, obj_id player, String prompt, String title, String[] options, String myHandler, boolean cancel, String PIDVar, String scriptVar) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, false, false);
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
        utils.setScriptVar(player, scriptVar, options);
    }
    public void closeOldWindow(obj_id player, String scriptVar) throws InterruptedException
    {
        if (utils.hasScriptVar(player, scriptVar))
        {
            int oldpid = utils.getIntScriptVar(player, scriptVar);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVarTree(player, scriptVar);
        }
    }
    public void setWindowPid(obj_id player, int pid, String scriptVar) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, scriptVar, pid);
        }
    }
}
