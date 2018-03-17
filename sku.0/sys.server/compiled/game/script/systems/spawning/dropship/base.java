package script.systems.spawning.dropship;

import script.obj_id;

public class base extends script.base_script
{
    public base()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        detachScript(self, "skeleton.humanoid");
        detachScript(self, "systems.combat.combat_actions");
        detachScript(self, "systems.combat.credit_for_kills");
        return SCRIPT_CONTINUE;
    }
}
