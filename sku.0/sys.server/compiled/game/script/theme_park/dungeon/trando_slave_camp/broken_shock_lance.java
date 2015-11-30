package script.theme_park.dungeon.trando_slave_camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.weapons;
import script.library.utils;

public class broken_shock_lance extends script.base_script
{
    public broken_shock_lance()
    {
    }
    public static final String weapon_smith = "class_munitions_phase1_master";
    public static final String STF = "ep3/sidequests";
    public static final string_id reverse_engineer = new string_id(STF, "re_broken_shock_lance");
    public static final String plans_name = "shock_lance";
    public static final string_id re_success = new string_id(STF, "re_success");
    public static final String weapon_hash = "object/weapon/melee/polearm/lance_shock.iff";
    public static final int num_uses = 50;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasSkill(player, weapon_smith))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, reverse_engineer);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU2)
        {
            obj_id inventory = utils.getInventoryContainer(player);
            obj_id schematic = weapons.createLimitedUseSchematic(weapon_hash, weapons.VIA_TEMPLATE, num_uses, inventory, plans_name, weapon_smith);
            if (isIdValid(schematic))
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
