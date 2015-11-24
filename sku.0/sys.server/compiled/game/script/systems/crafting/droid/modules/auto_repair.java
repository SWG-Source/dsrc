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
import script.library.healing;
import script.library.group;
import script.library.sui;
import script.library.utils;

public class auto_repair extends script.base_script
{
    public auto_repair()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final String SCRIPT_VAR_REPAIR_ON = "module_data.repair_on";
    public static final String SCRIPT_VAR_LAST_REPAIR = "module_data.last_repair";
    public static final String VAR_REPAIR_POWER = "module_data.auto_repair_power";
    public static final String VAR_REPAIR_PULSE = "module_data.auto_repair_pulse";
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
            int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id(STF_FILE, "toggle_autorepair"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.SERVER_MENU6)
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
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            if (player != master)
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(self, SCRIPT_VAR_REPAIR_ON))
            {
                utils.removeScriptVar(self, SCRIPT_VAR_REPAIR_ON);
                sendSystemMessage(player, new string_id(STF_FILE, "autorepair_off"));
            }
            else 
            {
                utils.setScriptVar(self, SCRIPT_VAR_REPAIR_ON, getGameTime());
                sendSystemMessage(player, new string_id(STF_FILE, "autorepair_on"));
                dictionary d = new dictionary();
                d.put("time", getGameTime());
                messageTo(self, "msgAutoRepairPulse", d, healing.VAR_AUTO_REPAIR_PULSE_INTERVAL, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "module_data.auto_repair_power"))
        {
            names[idx] = "auto_repair_power";
            int datastorage = getIntObjVar(self, "module_data.auto_repair_power");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAutoRepairPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPT_VAR_REPAIR_ON))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pet_control = callable.getCallableCD(self);
        if (!isIdValid(pet_control))
        {
            LOG("droid_module", "systems.crafting.droid.modules.auto_repair.msgAutoRepairPulse -- pet controller is invalid for " + self);
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_REPAIR_ON);
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_REPAIR_ON);
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        int repair_power = getIntObjVar(self, VAR_REPAIR_POWER);
        if (repair_power < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int time = params.getInt("time");
        if (time != utils.getIntScriptVar(self, SCRIPT_VAR_REPAIR_ON))
        {
            return SCRIPT_CONTINUE;
        }
        int repair_pulses = 0;
        if (hasObjVar(self, VAR_REPAIR_PULSE))
        {
            repair_pulses = getIntObjVar(self, VAR_REPAIR_PULSE);
        }
        obj_id[] creatures = getCreaturesInRange(loc, healing.VAR_STIMPACK_DROID_RADIUS);
        if (creatures != null)
        {
            for (int i = 0; i < creatures.length; i++)
            {
                if (pet_lib.isDroidPet(creatures[i]))
                {
                    obj_id target_master = getMaster(creatures[i]);
                    if (isIdValid(target_master))
                    {
                        if (master != target_master && !group.inSameGroup(master, target_master))
                        {
                            continue;
                        }
                    }
                    else 
                    {
                        continue;
                    }
                    if (ai_lib.aiIsDead(creatures[i]))
                    {
                        continue;
                    }
                    int last_repair = 0;
                    if (utils.hasScriptVar(creatures[i], SCRIPT_VAR_LAST_REPAIR))
                    {
                        last_repair = utils.getIntScriptVar(creatures[i], SCRIPT_VAR_LAST_REPAIR);
                    }
                    int interval = getGameTime() - last_repair;
                    if (interval > healing.VAR_AUTO_REPAIR_MIN_INTERVAL)
                    {
                        if (healing.isDamaged(creatures[i]))
                        {
                            attrib_mod am = utils.createHealDamageAttribMod(HEALTH, repair_power);
                            utils.addAttribMod(creatures[i], am);
                            repair_pulses += 1;
                            healing.playHealDamageEffect(getLocation(creatures[i]));
                        }
                    }
                }
            }
        }
        healing.playHealDamageEffect(loc);
        repair_pulses += 1;
        if (repair_pulses > 20)
        {
            int power_level = getIntObjVar(pet_control, "ai.pet.powerLevel");
            power_level += repair_pulses / 20;
            setObjVar(pet_control, "ai.pet.powerLevel", power_level);
            repair_pulses = repair_pulses % 20;
        }
        setObjVar(self, VAR_REPAIR_PULSE, repair_pulses);
        setObjVar(pet_control, VAR_REPAIR_PULSE, repair_pulses);
        messageTo(self, "msgAutoRepairPulse", params, healing.VAR_AUTO_REPAIR_PULSE_INTERVAL, false);
        return SCRIPT_CONTINUE;
    }
}
