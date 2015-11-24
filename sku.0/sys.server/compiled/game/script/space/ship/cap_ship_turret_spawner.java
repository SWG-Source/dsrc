package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_combat;

public class cap_ship_turret_spawner extends script.base_script
{
    public cap_ship_turret_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupTurrets", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setupTurrets(obj_id self, dictionary params) throws InterruptedException
    {
        self = getSelf();
        String strChassisType = getShipChassisType(self);
        space_combat.setupCapitalShipFromTurretDefinition(self, strChassisType);
        return SCRIPT_CONTINUE;
    }
}
