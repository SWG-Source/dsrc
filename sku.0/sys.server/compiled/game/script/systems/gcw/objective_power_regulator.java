package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.xp;
import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.ai_lib;

public class objective_power_regulator extends script.faction_perk.hq.objective_object
{
    public objective_power_regulator()
    {
    }
    public static final string_id MNU_SET_OVERLOAD = new string_id("hq", "mnu_set_overload");
    public static final int NUM_SWITCHES = 7;
    public static final String VAR_SWITCH_BASE = "hq.objective.switch";
    public static final String VAR_SWITCH_START = VAR_SWITCH_BASE + ".start";
    public static final String VAR_SWITCH_RULES = VAR_SWITCH_BASE + ".rules";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "type", "terminal");
        setObjVar(self, "intTerminal", 1);
        randomizeSwitches(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_SET_OVERLOAD);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (pvpGetType(player) != PVPTYPE_DECLARED)
        {
            sendSystemMessageTestingOnly(player, "Only declared factional personnel may access this terminal!");
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (!pvpAreFactionsOpposed(sFac, pFac))
        {
            sendSystemMessageTestingOnly(player, "You are not an enemy of this structure. Why would you want to tamper?");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessageTestingOnly(player, "The power regulator has already been set to overload.");
            return SCRIPT_CONTINUE;
        }
        obj_id nextObjective = hq.getNextObjective(structure);
        if (nextObjective != self)
        {
            obj_id priorObjective = hq.getPriorObjective(structure, self);
            if (isIdValid(priorObjective))
            {
                prose_package ppDisableOther = prose.getPackage(hq.PROSE_DISABLE_OTHER, priorObjective, self);
                sendSystemMessageProse(player, ppDisableOther);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "Other objectives must be disabled prior to gaining access to this one.");
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            setForOverload(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void setForOverload(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessageTestingOnly(player, "You cannot align the power flow to overload if you are in combat!");
            return;
        }
        location here = getLocation(self);
        location there = getLocation(player);
        if (here.cell != there.cell)
        {
            sendSystemMessageTestingOnly(player, "You cannot align the power flow if you are not even in the same room!");
            return;
        }
        if (getDistance(here, there) > 15f)
        {
            sendSystemMessageTestingOnly(player, "You are too far away from the power regulator to continue the setup!");
            return;
        }
        String scriptvar = "switches." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_state = scriptvar + ".state";
        if (utils.hasScriptVar(self, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(self, scriptvar_pid);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(self, scriptvar_pid);
        }
        boolean[] states = null;
        if (utils.hasScriptVar(player, scriptvar_state))
        {
            states = utils.getBooleanArrayScriptVar(self, scriptvar_state);
        }
        else 
        {
            states = utils.getBooleanArrayScriptVar(self, VAR_SWITCH_START);
        }
        if (states == null || states.length == 0)
        {
            return;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < states.length; i++)
        {
            String entry = "Switch #" + i + ": ";
            if (states[i])
            {
                entry += "ON";
            }
            else 
            {
                entry += "OFF";
            }
            entries = utils.addElement(entries, entry);
        }
        String prompt = "To successfully align the power flow to overload, you must activate all the flow regulators to ON.\n\n";
        prompt += "Select the switch to toggle...";
        String title = sui.DEFAULT_TITLE;
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, entries, "handleOverloadSui");
        if (pid > -1)
        {
            utils.setScriptVar(self, scriptvar_pid, pid);
            utils.setScriptVar(self, scriptvar_state, states);
        }
    }
    public int handleOverloadSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String scriptvar = "switches." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_state = scriptvar + ".state";
        utils.removeScriptVar(self, scriptvar_pid);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        boolean[] states = utils.getBooleanArrayScriptVar(self, scriptvar_state);
        if (states == null || states.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int[] rules = getIntArrayObjVar(self, VAR_SWITCH_RULES);
        if (rules == null || rules.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        states = flipSwitch(states, rules, idx);
        if (states == null || states.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, scriptvar_state, states);
        boolean litmus = true;
        for (int i = 0; i < states.length; i++)
        {
            litmus &= states[i];
        }
        if (litmus)
        {
            sendSystemMessageTestingOnly(player, "Alignment complete! The facility may now be set to overload from the primary terminal!");
            hq.disableObjective(self);
            xp.grant(player, "combat_rangedspecialize_heavy", 1000);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setForOverload(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void randomizeSwitches(obj_id self) throws InterruptedException
    {
        int[] rules = createSwitchRules();
        setObjVar(self, VAR_SWITCH_RULES, rules);
        boolean[] states = new boolean[NUM_SWITCHES];
        for (int i = 0; i < states.length; i++)
        {
            states[i] = true;
        }
        int numFlops = 2 * rand(1, 3) + 1;
        for (int i = 0; i < numFlops; i++)
        {
            states = flipSwitch(states, rules, rand(0, states.length - 1));
        }
        utils.setScriptVar(self, VAR_SWITCH_START, states);
    }
    public int[] createSwitchRules() throws InterruptedException
    {
        int[] rules = new int[NUM_SWITCHES];
        for (int i = 0; i < rules.length; i++)
        {
            rules[i] = rand(0, rules.length - 1);
        }
        return rules;
    }
    public boolean[] flipSwitch(boolean[] states, int[] rules, int idx) throws InterruptedException
    {
        if (states == null || states.length == 0)
        {
            return null;
        }
        if (rules == null || rules.length == 0)
        {
            return null;
        }
        if (states[idx])
        {
            states[idx] = false;
        }
        else 
        {
            states[idx] = true;
        }
        int affected = rules[idx];
        if (states[affected])
        {
            states[affected] = false;
        }
        else 
        {
            states[affected] = true;
        }
        return states;
    }
}
