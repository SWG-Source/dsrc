package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class equip_weapon extends script.base_script
{
    public equip_weapon()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!aiUsingPrimaryWeapon(self))
        {
            aiEquipPrimaryWeapon(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!aiUsingPrimaryWeapon(self))
        {
            aiEquipPrimaryWeapon(self);
        }
        return SCRIPT_CONTINUE;
    }
}
