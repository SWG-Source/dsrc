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

public class pup extends script.base_script
{
    public pup()
    {
    }
    public static final int LATEST_CONVERTED_VERSION = 2;
    public static final String TEMPLATE_NAME_EXPLOSIVE = "object/tangible/powerup/weapon/thrown_explosive.iff";
    public static final String TEMPLATE_NAME_WIRING = "object/tangible/powerup/weapon/thrown_wiring.iff";
    public static final String TEMPLATE_NAME_DURABLE_LATHE = "object/tangible/powerup/weapon/fs_quest_sad/melee_speed_quest.iff";
    public int OnApplyPowerup(obj_id self, obj_id player, obj_id target) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        powerup.getWeaponAttributes(player, self, names, attribs, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id container = getContainedBy(self);
        if (!isIdValid(container) || !isGameObjectTypeOf(getGameObjectType(container), GOT_misc_factory_crate))
        {
            destroyObject(self);
        }
        else 
        {
            destroyObject(container);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
