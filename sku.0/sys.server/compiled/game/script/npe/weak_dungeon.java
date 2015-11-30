package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.weapons;

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
