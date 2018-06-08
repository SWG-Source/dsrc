package script.npe;

import script.library.weapons;
import script.obj_id;

public class weak_dungeon extends script.base_script
{
    public weak_dungeon()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id weapon = aiGetPrimaryWeapon(self);
        setWeaponMinDamage(weapon, 30);
        setWeaponMaxDamage(weapon, 60);
        weapons.setWeaponData(weapon);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id weapon = aiGetPrimaryWeapon(self);
        setWeaponMinDamage(weapon, 30);
        setWeaponMaxDamage(weapon, 60);
        weapons.setWeaponData(weapon);
        return SCRIPT_CONTINUE;
    }
}
