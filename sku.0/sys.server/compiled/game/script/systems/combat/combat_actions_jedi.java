package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class combat_actions_jedi extends script.base_script
{
    public combat_actions_jedi()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.combat.combat_actions_jedi");
        return SCRIPT_CONTINUE;
    }
}
