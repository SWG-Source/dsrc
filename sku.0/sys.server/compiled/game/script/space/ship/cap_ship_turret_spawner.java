package script.space.ship;

import script.dictionary;
import script.library.space_combat;
import script.obj_id;

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
