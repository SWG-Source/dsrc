package script.systems.crafting.droid.modules;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.pet_lib;
import script.library.performance;
import script.library.sui;
import script.library.utils;

public class entertainer_effect extends script.base_script
{
    public entertainer_effect()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final float EFFECT_RANGE = 30.0f;
    public static final String SCRIPT_VAR_EFFECTS_ON = "module_data.effects_on";
    public static final String SCRIPT_VAR_EFFECTS_SUI = "module_data.effects_sui";
    public static final String SCRIPT_VAR_EFFECTS_SUI_CHANGE = "module_data.effects_sui_change";
    public static final String SCRIPT_VAR_EFFECTS_SUI_DELAY = "module_data.effects_sui_delay";
    public static final String SCRIPT_VAR_EFFECTS_LIST = "module_data.effects_list";
    public static final String SCRIPT_VAR_SLOT_SELECTED = "module_data.slot_selected";
    public static final String SCRIPT_VAR_EFFECTS_DELAY = "module_data.effects_delay";
    public static final String SCRIPT_VAR_NEXT_EFFECT = "module_date.next_effect";
    public static final String VAR_EFFECTS = "module_data.entertainer_effects";
    public static final String VAR_PROGRAMMED_EFFECTS = "module_data.programmed_effects";
    public static final String VAR_PROGRAMMED_EFFECT_DELAYS = "module_data.programmed_effect_delays";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == master)
        {
            if (utils.isProfession(player, utils.ENTERTAINER))
            {
                int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU7, new string_id(STF_FILE, "toggle_effects"));
                mi.addSubMenu(mnu, menu_info_types.SERVER_MENU8, new string_id(STF_FILE, "effects_set_up"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.SERVER_MENU7 && item != menu_info_types.SERVER_MENU8)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "not_enough_power"));
            return SCRIPT_CONTINUE;
        }
        if (!utils.isProfession(player, utils.ENTERTAINER))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "no_skill_for_effects"));
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            if (player != master)
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(self, SCRIPT_VAR_EFFECTS_ON))
            {
                utils.removeScriptVar(self, SCRIPT_VAR_EFFECTS_ON);
                sendSystemMessage(player, new string_id(STF_FILE, "effects_off"));
            }
            else 
            {
                if (!hasObjVar(self, VAR_PROGRAMMED_EFFECTS))
                {
                    sendSystemMessage(player, new string_id(STF_FILE, "no_effects_program"));
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(self, SCRIPT_VAR_EFFECTS_ON, getGameTime());
                sendSystemMessage(player, new string_id(STF_FILE, "effects_started"));
                dictionary d = new dictionary();
                d.put("time", getGameTime());
                messageTo(self, "msgNextEntertainerDroidEffect", d, 5.0f, false);
            }
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            if (player != master)
            {
                return SCRIPT_CONTINUE;
            }
            displayCurrentEffects(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEntertainerDroidEffectChange(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            closeAllEffectSUI(master);
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected != -1)
        {
            String[] available_effects = getAvailableEffects(self);
            if (available_effects == null)
            {
                sendSystemMessage(master, new string_id(STF_FILE, "no_available_effects"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (row_selected >= 10)
                {
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(master, SCRIPT_VAR_SLOT_SELECTED, row_selected);
                utils.setScriptVar(master, SCRIPT_VAR_EFFECTS_LIST, available_effects);
                String[] dsrc = new String[available_effects.length];
                for (int i = 0; i < dsrc.length; i++)
                {
                    dsrc[i] = localize(new string_id(STF_FILE, available_effects[i]));
                }
                LOG("droid_module", "Displaying select effectSUI.");
                int pid = sui.listbox(self, master, "Select an effect.", sui.OK_CANCEL, "Effect Selection", dsrc, "msgEntertainerDroidEffectSlot");
            }
        }
        else 
        {
            closeAllEffectSUI(master);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEntertainerDroidEffectSlot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            closeEffectSelectSUI(master);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(master, SCRIPT_VAR_EFFECTS_LIST))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(master, SCRIPT_VAR_SLOT_SELECTED))
        {
            return SCRIPT_CONTINUE;
        }
        String[] available_effects = utils.getStringArrayScriptVar(master, SCRIPT_VAR_EFFECTS_LIST);
        utils.removeScriptVar(master, SCRIPT_VAR_EFFECTS_LIST);
        if (available_effects == null)
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected != -1)
        {
            if (row_selected >= available_effects.length)
            {
                return SCRIPT_CONTINUE;
            }
            String effect_selected = available_effects[row_selected];
            if (effect_selected.equals("empty_slot"))
            {
                String[] effect_list = getProgrammedEffects(self);
                int[] delay_list = getProgrammedDelays(self);
                if (effect_list == null || delay_list == null)
                {
                    return SCRIPT_CONTINUE;
                }
                obj_id pet_control = callable.getCallableCD(self);
                if (!isIdValid(pet_control))
                {
                    LOG("droid_module", "merchant_barker.msgEntertainerDroidEffectSlot -- pet control device is invalid for droid " + self);
                    return SCRIPT_CONTINUE;
                }
                int slot = utils.getIntScriptVar(master, SCRIPT_VAR_SLOT_SELECTED);
                effect_list[slot] = effect_selected;
                delay_list[slot] = 0;
                setObjVar(self, VAR_PROGRAMMED_EFFECTS, effect_list);
                setObjVar(self, VAR_PROGRAMMED_EFFECT_DELAYS, delay_list);
                setObjVar(pet_control, VAR_PROGRAMMED_EFFECTS, effect_list);
                setObjVar(pet_control, VAR_PROGRAMMED_EFFECT_DELAYS, delay_list);
                displayCurrentEffects(self);
            }
            else 
            {
                utils.setScriptVar(master, SCRIPT_VAR_EFFECTS_DELAY, effect_selected);
                int pid = sui.inputbox(self, master, "Input the time delay until the next effect is played.", "Delay Setting", "msgEntertainerDroidEffectDelay", 6, false, "");
            }
        }
        else 
        {
            closeEffectSelectSUI(master);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEntertainerDroidEffectDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            closeEffectSelectSUI(master);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(master, SCRIPT_VAR_SLOT_SELECTED))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(master, SCRIPT_VAR_EFFECTS_DELAY))
        {
            return SCRIPT_CONTINUE;
        }
        String text = sui.getInputBoxText(params);
        int delay = utils.stringToInt(text);
        if (delay < 3)
        {
            sendSystemMessage(master, new string_id(STF_FILE, "invalid_delay_time"));
            displayCurrentEffects(self);
            return SCRIPT_CONTINUE;
        }
        else if (delay > 60)
        {
            sendSystemMessage(master, new string_id(STF_FILE, "invalid_delay_time"));
            displayCurrentEffects(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id pet_control = callable.getCallableCD(self);
            if (!isIdValid(pet_control))
            {
                LOG("droid_module", "merchant_barker.msgEntertainerDroidEffectDelay -- pet control device is invalid for droid " + self);
                return SCRIPT_CONTINUE;
            }
            String[] effect_list = getProgrammedEffects(self);
            int[] delay_list = getProgrammedDelays(self);
            if (effect_list == null || delay_list == null)
            {
                return SCRIPT_CONTINUE;
            }
            int slot = utils.getIntScriptVar(master, SCRIPT_VAR_SLOT_SELECTED);
            String effect = utils.getStringScriptVar(master, SCRIPT_VAR_EFFECTS_DELAY);
            effect_list[slot] = effect;
            delay_list[slot] = delay;
            setObjVar(self, VAR_PROGRAMMED_EFFECTS, effect_list);
            setObjVar(self, VAR_PROGRAMMED_EFFECT_DELAYS, delay_list);
            setObjVar(pet_control, VAR_PROGRAMMED_EFFECTS, effect_list);
            setObjVar(pet_control, VAR_PROGRAMMED_EFFECT_DELAYS, delay_list);
            displayCurrentEffects(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgNextEntertainerDroidEffect(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_EFFECTS_ON);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_EFFECTS_ON))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.getIntScriptVar(self, SCRIPT_VAR_EFFECTS_ON) != params.getInt("time"))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_EFFECTS_ON);
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        String[] effect_list = getProgrammedEffects(self);
        int[] delay_list = getProgrammedDelays(self);
        if (effect_list == null || delay_list == null)
        {
            return SCRIPT_CONTINUE;
        }
        int slot = 0;
        if (params.containsKey("slot"))
        {
            slot = params.getInt("slot");
            if (slot > 9)
            {
                slot = 0;
            }
        }
        String effect = "empty_slot";
        int delay = 0;
        boolean start_loop = false;
        int i = slot;
        while (i < 10)
        {
            if (start_loop && i > slot)
            {
                break;
            }
            if (i == 0)
            {
                start_loop = true;
            }
            if (!effect_list[i].equals("empty_slot") && delay_list[i] > 0)
            {
                effect = effect_list[i];
                delay = delay_list[i];
                slot = i + 1;
                break;
            }
            if (i == 9 && !start_loop)
            {
                i = 0;
            }
            else 
            {
                i++;
            }
        }
        if (!effect.equals("empty_slot") && delay > 0)
        {
            params.put("slot", slot);
            messageTo(self, "msgNextEntertainerDroidEffect", params, delay, false);
            playEntertainerEffect(self, effect);
        }
        else 
        {
            sendSystemMessage(master, new string_id(STF_FILE, "no_effects_program"));
            utils.removeScriptVar(self, SCRIPT_VAR_EFFECTS_ON);
        }
        return SCRIPT_CONTINUE;
    }
    public String[] getAvailableEffects(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return null;
        }
        if (!hasObjVar(droid, VAR_EFFECTS))
        {
            return null;
        }
        int raw_effects = getIntObjVar(droid, VAR_EFFECTS);
        if (raw_effects < 1)
        {
            return null;
        }
        int i = 1;
        Vector available_effects = new Vector();
        available_effects.setSize(0);
        while (i <= pet_lib.LIGHTING_EFFECTS.length)
        {
            int result = (int)((raw_effects % Math.pow(10, i)) / Math.pow(10, i - 1));
            if (result >= 1)
            {
                available_effects = utils.addElement(available_effects, pet_lib.LIGHTING_EFFECTS[i - 1]);
            }
            i++;
        }
        if (available_effects.size() < 1)
        {
            return null;
        }
        else 
        {
            available_effects = utils.addElement(available_effects, "empty_slot");
            String[] _available_effects = new String[0];
            if (available_effects != null)
            {
                _available_effects = new String[available_effects.size()];
                available_effects.toArray(_available_effects);
            }
            return _available_effects;
        }
    }
    public void displayCurrentEffects(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return;
        }
        obj_id master = getMaster(droid);
        if (!isIdValid(master))
        {
            return;
        }
        closeAllEffectSUI(master);
        String[] current_effects = new String[10];
        int[] current_delays = new int[10];
        if (hasObjVar(droid, VAR_PROGRAMMED_EFFECTS))
        {
            current_effects = getStringArrayObjVar(droid, VAR_PROGRAMMED_EFFECTS);
        }
        if (hasObjVar(droid, VAR_PROGRAMMED_EFFECT_DELAYS))
        {
            current_delays = getIntArrayObjVar(droid, VAR_PROGRAMMED_EFFECT_DELAYS);
        }
        if (current_effects.length != current_delays.length)
        {
            current_effects = new String[10];
            current_delays = new int[10];
        }
        String[] dsrc = new String[10];
        for (int i = 0; i < current_effects.length; i++)
        {
            String effect = current_effects[i];
            String effect_loc = localize(new string_id(STF_FILE, effect));
            String text = "#" + (i + 1) + ": Empty Slot";
            if (effect != null && effect.length() > 0 && !effect.equals("empty_slot"))
            {
                text = "#" + (i + 1) + ": " + effect_loc + " -- " + current_delays[i] + " sec.";
            }
            dsrc[i] = text;
        }
        int pid = sui.listbox(droid, master, "Select an effect slot that you which to change.", sui.OK_CANCEL, "Effect Configuration", dsrc, "msgEntertainerDroidEffectChange");
        utils.setScriptVar(master, SCRIPT_VAR_EFFECTS_SUI, pid);
        return;
    }
    public void closeAllEffectSUI(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_EFFECTS_SUI))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_EFFECTS_SUI));
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_SUI);
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_CHANGE))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_CHANGE));
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_CHANGE);
            utils.removeScriptVar(player, SCRIPT_VAR_SLOT_SELECTED);
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_LIST);
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY));
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY);
        }
        return;
    }
    public void closeEffectSelectSUI(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_CHANGE))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_CHANGE));
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_CHANGE);
            utils.removeScriptVar(player, SCRIPT_VAR_SLOT_SELECTED);
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_LIST);
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY));
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY);
        }
        return;
    }
    public void closeEffectDelaySUI(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY));
            utils.removeScriptVar(player, SCRIPT_VAR_EFFECTS_SUI_DELAY);
        }
        return;
    }
    public String[] getProgrammedEffects(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return null;
        }
        String[] effects_list = getStringArrayObjVar(droid, VAR_PROGRAMMED_EFFECTS);
        if (effects_list == null)
        {
            effects_list = new String[10];
            for (int i = 0; i < effects_list.length; i++)
            {
                effects_list[i] = "empty_slot";
            }
        }
        return effects_list;
    }
    public int[] getProgrammedDelays(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return null;
        }
        int[] delay_list = getIntArrayObjVar(droid, VAR_PROGRAMMED_EFFECT_DELAYS);
        if (delay_list == null)
        {
            delay_list = new int[10];
        }
        return delay_list;
    }
    public void playEntertainerEffect(obj_id droid, String effect) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return;
        }
        if (effect == null || effect.length() < 1)
        {
            return;
        }
        obj_id master = getMaster(droid);
        if (!isIdValid(master))
        {
            return;
        }
        String cef_name = "clienteffect/" + effect + ".cef";
        location loc = getLocation(droid);
        playClientEffectLoc(master, cef_name, loc, 0.0f);
        return;
    }
}
