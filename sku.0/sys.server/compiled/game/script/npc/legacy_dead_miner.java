package script.npc;

import script.obj_id;

public class legacy_dead_miner extends script.base_script
{
    public legacy_dead_miner()
    {
    }
    public static int INITIAL_DELAY = 1;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "ai.ai"))
        {
            detachScript(self, "ai.ai");
        }
        if (hasScript(self, "ai.creature_combat"))
        {
            detachScript(self, "ai.creature_combat");
        }
        if (hasScript(self, "systems.combat.credit_for_kills"))
        {
            detachScript(self, "systems.combat.credit_for_kills");
        }
        if (hasScript(self, "systems.combat.combat_actions"))
        {
            detachScript(self, "systems.combat.combat_actions");
        }
        setPosture(self, POSTURE_KNOCKED_DOWN);
        return SCRIPT_CONTINUE;
    }
}
