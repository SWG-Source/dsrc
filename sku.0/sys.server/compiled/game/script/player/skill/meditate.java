package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class meditate extends script.base_script
{
    public meditate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.skill.meditate");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.skill.meditate");
        return SCRIPT_CONTINUE;
    }
}
