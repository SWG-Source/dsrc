package script.faction_perk.hq;

import script.*;
import script.library.*;

import java.util.Vector;

public class objective_power_regulator extends script.faction_perk.hq.objective_object
{
    public objective_power_regulator()
    {
    }
    private static final string_id MNU_SET_OVERLOAD = new string_id("hq", "mnu_set_overload");
    private static final int NUM_SWITCHES = 7;
    private static final String VAR_SWITCH_BASE = "hq.objective.switch";
    private static final String VAR_SWITCH_START = VAR_SWITCH_BASE + ".start";
    private static final String VAR_SWITCH_RULES = VAR_SWITCH_BASE + ".rules";
    public static final String STF = "faction/faction_hq/faction_hq_response";
    private static final string_id SID_NO_STEALTH = new string_id("hq", "no_stealth");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        randomizeSwitches(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int intState = getState(player, STATE_FEIGN_DEATH);
        if (isDead(player) || isIncapacitated(player) || intState > 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, MNU_SET_OVERLOAD);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            pFac = 0;
        }
        if (!pvpAreFactionsOpposed(sFac, pFac))
        {
            sendSystemMessage(player, new string_id(STF, "no_tamper"));
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessage(player, new string_id(STF, "already_overloading"));
            return SCRIPT_CONTINUE;
        }
        obj_id nextObjective = hq.getNextObjective(structure);
        if (nextObjective != self)
        {
            obj_id priorObjective = hq.getPriorObjective(structure, self);
            if (isIdValid(priorObjective))
            {
                sendSystemMessageProse(player, prose.getPackage(hq.PROSE_DISABLE_OTHER, priorObjective, self));
            }
            else 
            {
                sendSystemMessage(player, new string_id(STF, "other_objectives"));
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasSkill(player, "class_commando_phase1_novice"))
            {
                sendSystemMessage(player, new string_id(STF, "commando_only"));
                return SCRIPT_CONTINUE;
            }
            setForOverload(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    private void setForOverload(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, new string_id(STF, "power_not_in_combat"));
            return;
        }
        if (!canSee(self, player))
        {
            sendSystemMessage(player, new string_id(STF, "power_not_in_room"));
            return;
        }
        if (getDistance(getLocation(self), getLocation(player)) > 15f)
        {
            sendSystemMessage(player, new string_id(STF, "power_too_far"));
            return;
        }
        String scriptvar = "switches." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_state = scriptvar + ".state";
        if (utils.hasScriptVar(self, scriptvar_pid))
        {
            sui.closeSUI(player, utils.getIntScriptVar(self, scriptvar_pid));
            utils.removeScriptVar(self, scriptvar_pid);
        }
        boolean[] states;
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
        String entry;
        for (int i = 0; i < states.length; i++)
        {
            entry = "Switch #" + i + ": ";
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
        String prompt = "To successfully align the power flow to overload, you must activate all the flow regulators to ON.\n\nSelect the switch to toggle...";
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, sui.DEFAULT_TITLE, entries, "handleOverloadSui");
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
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        String scriptvar = "switches." + player;
        String scriptvar_pid = scriptvar + ".pid";
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
        String scriptvar_state = scriptvar + ".state";
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
        for (boolean state : states) {
            litmus &= state;
        }
        if (litmus)
        {
            CustomerServiceLog("faction_hq", getName(player) + "(" + player + "), " + "overloaded Power Regulator Terminal at " + getGameTime() + ".");
            sendSystemMessage(player, new string_id(STF, "alignment_complete"));
            hq.disableObjective(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setForOverload(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    private void randomizeSwitches(obj_id self) throws InterruptedException
    {
        int[] rules = createSwitchRules();
        setObjVar(self, VAR_SWITCH_RULES, rules);
        boolean[] states = new boolean[NUM_SWITCHES];
        for (int i = 0; i < states.length; i++)
        {
            states[i] = true;
        }
        int numFlops = 2 * rand(2, 4) + 1;
        for (int i = 0; i < numFlops; i++)
        {
            states = flipSwitch(states, rules, rand(0, states.length - 1));
        }
        boolean litmus = true;
        for (boolean state : states) {
            litmus &= state;
        }
        if (litmus)
        {
            states = flipSwitch(states, rules, rand(0, states.length - 1));
        }
        utils.setScriptVar(self, VAR_SWITCH_START, states);
    }
    private int[] createSwitchRules() throws InterruptedException
    {
        int[] rules = new int[NUM_SWITCHES];
        for (int i = 0; i < rules.length; i++)
        {
            rules[i] = rand(0, rules.length - 1);
        }
        return rules;
    }
    private boolean[] flipSwitch(boolean[] states, int[] rules, int idx) throws InterruptedException
    {
        if (states == null || states.length == 0)
        {
            return null;
        }
        else if (rules == null || rules.length == 0)
        {
            return null;
        }
        states[idx] = !states[idx];
        int affected = rules[idx];
        states[affected] = !states[affected];
        return states;
    }
}
