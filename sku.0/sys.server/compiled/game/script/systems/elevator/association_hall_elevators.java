package script.systems.elevator;

import script.location;
import script.obj_id;

public class association_hall_elevators extends script.base_script
{
    public association_hall_elevators()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id elevator = getCellId(self, "elevator");
        if (isIdValid(elevator))
        {
            here.x = 0.008f;
            here.y = 2.25f;
            here.z = 13.7f;
            here.cell = elevator;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalA, 180);
            here.y = -9.0f;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalB, 180);
            setObjVar(self, "terminal1", elevatorTerminalA);
            setObjVar(self, "terminal2", elevatorTerminalB);
        }
        else 
        {
            LOG("elevator", "association_hall_elevators could not get 'elevator' cell");
        }
        setObjVar(self, "alreadySpawned", 1);
        attachScript(elevator, "systems.elevator.button_check");
        return SCRIPT_CONTINUE;
    }
}
