package script.systems.combat;

import script.obj_id;

public class combat_actions_turret extends script.systems.combat.combat_base
{
    public combat_actions_turret()
    {
    }
    public int turretShot(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!combatStandardAction("turretShot", self, target, params, "", ""))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
