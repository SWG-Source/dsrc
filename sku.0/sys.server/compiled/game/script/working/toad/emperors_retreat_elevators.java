package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class emperors_retreat_elevators extends script.base_script
{
    public emperors_retreat_elevators()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "elevators"))
        {
            location here = getLocation(self);
            obj_id elevator = getCellId(self, "empelevator");
            obj_id backelevator = getCellId(self, "backelevator");
            here.x = 12.88f;
            here.y = .6f;
            here.z = -35.47f;
            here.cell = elevator;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            here.y = 20f;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            here.x = -52.49f;
            here.y = .2f;
            here.z = -12.33f;
            here.cell = backelevator;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalC, 90);
            here.y = -9.8f;
            obj_id elevatorTerminalD = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalD, 90);
            setObjVar(self, "elevators", "done");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
}
