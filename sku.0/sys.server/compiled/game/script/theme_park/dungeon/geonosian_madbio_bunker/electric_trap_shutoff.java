package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class electric_trap_shutoff extends script.base_script
{
    public electric_trap_shutoff()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id trap = getObjIdObjVar(self, "trap");
            shutoffTrap(trap, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void shutoffTrap(obj_id power, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(power, "trap_off"))
        {
            dictionary test = new dictionary();
            test.put("player", player);
            messageTo(power, "trapShutOff", test, 1, true);
            string_id powerOff = new string_id(MSGS, "power_off");
            sendSystemMessage(player, powerOff);
        }
        else 
        {
            string_id alreadyOff = new string_id(MSGS, "power_already_off");
            sendSystemMessage(player, alreadyOff);
        }
        return;
    }
}
