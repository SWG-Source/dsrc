package script.working.asommers;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class transport extends script.base_script
{
    public transport()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        detachScript(self, "skeleton.humanoid");
        detachScript(self, "systems.combat.combat_actions");
        detachScript(self, "systems.combat.credit_for_kills");
        stop(self);
        setName(self, "Hutt Cargo Transport");
        setPosture(self, POSTURE_UPRIGHT);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "changePosture", null, 60, true);
        return SCRIPT_CONTINUE;
    }
    public int changePosture(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_PRONE);
        queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "changePostureAgain", null, 30, true);
        return SCRIPT_CONTINUE;
    }
    public int changePostureAgain(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_UPRIGHT);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "changePosture", null, 60, true);
        return SCRIPT_CONTINUE;
    }
}
