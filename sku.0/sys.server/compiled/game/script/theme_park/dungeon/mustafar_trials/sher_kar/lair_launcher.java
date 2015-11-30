package script.theme_park.dungeon.mustafar_trials.sher_kar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.utils;

public class lair_launcher extends script.base_script
{
    public lair_launcher()
    {
    }
    public static final string_id SID_SHER_KAR = new string_id("travel/zone_transition", "sher_kar_cave");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (getDistance(player, self) > 25.0f)
        {
            return SCRIPT_CONTINUE;
        }
        item.addRootMenu(menu_info_types.ITEM_USE, SID_SHER_KAR);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            doBackflagging(player);
            instance.requestInstanceMovement(player, "sher_kar_cave");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean doBackflagging(obj_id player) throws InterruptedException
    {
        if (utils.playerHasItemByTemplateInInventoryOrEquipped(player, "object/tangible/item/som/sher_kar_syringe.iff"))
        {
            instance.flagPlayerForInstance(player, "sher_kar_cave");
            return true;
        }
        return false;
    }
}
