package script.systems.crafting.droid.modules;

import script.*;
import script.library.*;

import java.util.Vector;

public class stimpack_dispensor extends script.base_script
{
    public stimpack_dispensor()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final String SCRIPT_VAR_STIMPACK_LIST = "droid_module.stimpack_list";
    public static final String SCRIPT_VAR_STIMPACK_DROID = "droid_module.stimpack_droid";
    public static final String SCRIPT_VAR_STIMPACK_SUI = "droid_module.stimpack_sui";
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
        if (player == master || group.inSameGroup(master, player))
        {
            int mnu = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, new string_id(STF_FILE, "request_stimpack"));
            if (hasSkill(player, "science_medic_ability_04"))
            {
                mi.addSubMenu(mnu, menu_info_types.SERVER_HEAL_WOUND_HEALTH, new string_id(STF_FILE, "load_stimpack"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.SERVER_ITEM_OPTIONS && item != menu_info_types.SERVER_HEAL_WOUND_HEALTH)
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
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            queueCommand(player, (934778071), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        if (item == menu_info_types.SERVER_HEAL_WOUND_HEALTH)
        {
            if (!hasSkill(player, "science_medic_ability_04"))
            {
                return SCRIPT_CONTINUE;
            }
            if (player != getMaster(self) && group.inSameGroup(master, player))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id inventory = getObjectInSlot(player, "inventory");
            if (!isIdValid(inventory))
            {
                LOG("droid_module", "systems.crafting.droid.modules.stimpack_dispensor.OnObjectMenuSelect -- " + player + "'s inventory is null.");
                return SCRIPT_CONTINUE;
            }
            obj_id[] items = utils.getContents(inventory, false);
            Vector stim_a = new Vector();
            stim_a.setSize(0);
            Vector dsrc = new Vector();
            dsrc.setSize(0);
            if (items != null)
            {
                for (obj_id item1 : items) {
                    if ((getTemplateName(item1)).equals("object/tangible/medicine/instant_stimpack/stimpack_a.iff")) {
                        stim_a = utils.addElement(stim_a, item1);
                        int power = getIntObjVar(item1, "healing.power");
                        int charges = getCount(item1);
                        String text = "Power :" + power + "   Charges :" + charges;
                        dsrc = utils.addElement(dsrc, text);
                    }
                }
            }
            if (stim_a.size() < 1)
            {
                sendSystemMessage(player, new string_id(STF_FILE, "no_stimpacks"));
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(player, SCRIPT_VAR_STIMPACK_LIST, stim_a);
            utils.setScriptVar(player, SCRIPT_VAR_STIMPACK_DROID, self);
            if (utils.hasScriptVar(player, SCRIPT_VAR_STIMPACK_SUI))
            {
                forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_STIMPACK_SUI));
            }
            int pid = sui.listbox(player, player, "Select the stimpack you wish to load into the droid.", sui.OK_CANCEL, "Stimpack Selection", dsrc, "msgStimpackLoaded");
            utils.setScriptVar(player, SCRIPT_VAR_STIMPACK_SUI, pid);
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
        if (hasObjVar(self, "module_data.stimpack_capacity"))
        {
            names[idx] = "stimpack_power";
            int power = getIntObjVar(self, "module_data.stim_power");
            int supply = getIntObjVar(self, "module_data.stimpack_supply");
            int capacity = getIntObjVar(self, "module_data.stimpack_capacity");
            int speed = getIntObjVar(self, "module_data.stimpack_speed");
            if (power < 0)
            {
                power = 0;
            }
            if (supply < 0)
            {
                supply = 0;
            }
            attribs[idx] = " " + power;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "stimpack_capacity";
            attribs[idx] = " " + supply + " / " + capacity;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "stimpack_speed";
            attribs[idx] = " " + speed;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgStimpackDispensorRecharged(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "module_data.stimpack_recharging");
        return SCRIPT_CONTINUE;
    }
}
