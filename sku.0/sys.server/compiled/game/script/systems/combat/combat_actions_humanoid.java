package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
