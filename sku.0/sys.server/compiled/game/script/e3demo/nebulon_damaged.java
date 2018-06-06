package script.e3demo;

import script.obj_id;

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
