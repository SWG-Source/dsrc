package script.powerup;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.powerup;
import script.library.utils;
import script.library.prose;

public class weapon extends script.base_script
{
    public weapon()
    {
    }
    public static final string_id MNU_REMOVE_POWERUP = new string_id("powerup", "mnu_remove_powerup");
    public static final string_id PROSE_REMOVE_POWERUP = new string_id("powerup", "prose_remove_powerup");
    public int OnApplyPowerup(obj_id self, obj_id player, obj_id target) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("powerup", "powerup.weapon.onAttach");
        if (hasObjVar(self, powerup.VAR_POWERUP_USES_LEFT))
        {
            LOG("powerup", "powerup.weapon.onAttach found the objvar" + powerup.VAR_POWERUP_USES_LEFT);
            setCondition(self, CONDITION_MAGIC_ITEM);
        }
        else if (setObjVar(self, powerup.VAR_POWERUP_USES_LEFT, powerup.DEFAULT_USE_COUNT))
        {
            setCondition(self, CONDITION_MAGIC_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        powerup.getWeaponAttributes(player, self, names, attribs, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU9, MNU_REMOVE_POWERUP);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("powerup", "Selected cleanup item " + item);
        if (item == menu_info_types.SERVER_MENU9)
        {
            LOG("powerup", "Selected cleanup.");
            if (utils.isNestedWithin(self, player))
            {
                if (powerup.cleanupWeaponPowerup(self))
                {
                    prose_package ppRemovePowerup = prose.getPackage(PROSE_REMOVE_POWERUP, self);
                    sendSystemMessageProse(player, ppRemovePowerup);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
