package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.space_combat;
import script.library.space_create;

public class nebulon_damaged extends script.base_script
{
    public nebulon_damaged()
    {
    }
    public int OnShipWasHit(obj_id self, obj_id objAttacker, int intWeaponIndex, boolean isMissile, int missileType, int intTargetedComponent, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
