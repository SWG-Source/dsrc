package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class grav_unit_repair_kit extends script.base_script
{
    public grav_unit_repair_kit()
    {
    }
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act3/shared_rebel_missions";
    public static final string_id GRAV_UNIT_REPAIRED = new string_id(REBEL_SHARED_STF, "repair_grav_unit");
    public static final string_id REPAIR_FAILED = new string_id(REBEL_SHARED_STF, "repair_failed");
    public static final string_id SID_REPAIR = new string_id(REBEL_SHARED_STF, "repair");
    public static final String BROKEN_GRAV_UNIT = "object/tangible/theme_park/alderaan/act3/broken_grav_unit.iff";
    public static final String REPAIRED_GRAV_UNIT = "object/tangible/theme_park/alderaan/act3/repaired_grav_unit.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, BROKEN_GRAV_UNIT))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_REPAIR);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            repairGravUnit(self, player);
        }
        return SCRIPT_OVERRIDE;
    }
    public void repairGravUnit(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, BROKEN_GRAV_UNIT))
        {
            obj_id inventory = getObjectInSlot(player, "inventory");
            obj_id gravUnit = createObject(REPAIRED_GRAV_UNIT, inventory, "");
            if (isIdValid(gravUnit))
            {
                sendSystemMessage(player, GRAV_UNIT_REPAIRED);
                obj_id brokenUnit = utils.getItemPlayerHasByTemplate(player, BROKEN_GRAV_UNIT);
                if (isIdValid(brokenUnit))
                {
                    destroyObject(brokenUnit);
                }
                destroyObject(self);
            }
            else 
            {
                sendSystemMessage(player, REPAIR_FAILED);
            }
        }
        else 
        {
            sendSystemMessage(player, REPAIR_FAILED);
        }
    }
}
