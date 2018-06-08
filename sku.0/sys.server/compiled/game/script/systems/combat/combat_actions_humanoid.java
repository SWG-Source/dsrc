package script.systems.combat;

import script.obj_id;

public class combat_actions_humanoid extends script.base_script
{
    public combat_actions_humanoid()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "systems.combat.combat_actions"))
        {
            attachScript(self, "systems.combat.combat_actions");
        }
        detachScript(self, "systems.combat.combat_actions_humanoid");
        return SCRIPT_CONTINUE;
    }
}
