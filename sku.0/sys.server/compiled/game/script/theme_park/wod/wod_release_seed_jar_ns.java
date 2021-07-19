package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;

public class wod_release_seed_jar_ns extends script.base_script
{
    public wod_release_seed_jar_ns()
    {
    }
    public static final string_id SID_MNU_USE = new string_id("spam", "open");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("spam", "cannot_use_not_in_inv");
    public static final String[] TREES = 
    {
        "item_schematic_wod_ns_potted_plant_01",
        "item_schematic_wod_ns_potted_plant_02",
        "item_schematic_wod_ns_potted_plant_03",
        "item_schematic_wod_ns_potted_plant_04",
        "item_schematic_wod_ns_potted_plant_05",
        "item_schematic_wod_ns_potted_plant_06",
        "item_schematic_wod_ns_potted_plant_07",
        "item_schematic_wod_ns_potted_plant_08",
        "item_schematic_wod_ns_potted_plant_09"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.wod.pro_seed_jar_ns");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!contains(inventory, self))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_INV);
            return SCRIPT_CONTINUE;
        }
        int treeSelect = rand(0, 8);
        obj_id createdSchematic = static_item.createNewItemFunction(TREES[treeSelect], player);
        if (isIdValid(createdSchematic))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
