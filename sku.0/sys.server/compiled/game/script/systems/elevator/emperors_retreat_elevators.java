package script.systems.elevator;

import script.location;
import script.obj_id;

public class emperors_retreat_elevators extends script.base_script
{
    public emperors_retreat_elevators()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id elevator = getCellId(self, "empelevator");
        if (isIdValid(elevator))
        {
            here.x = 13.01f;
            here.y = 0.6f;
            here.z = -35.918f;
            here.cell = elevator;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            here.y = 20.0f;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setObjVar(self, "terminal1", elevatorTerminalA);
            setObjVar(self, "terminal2", elevatorTerminalB);
        }
        else 
        {
            LOG("elevator", "emperors_retreat_elevators could not get 'elevator' cell");
        }
        obj_id backelevator = getCellId(self, "backelevator");
        if (isIdValid(backelevator))
        {
            here.x = -52.983f;
            here.y = 0.2f;
            here.z = -12.49f;
            here.cell = backelevator;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalC, 90);
            here.y = -9.8f;
            obj_id elevatorTerminalD = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalD, 90);
            setObjVar(self, "terminal3", elevatorTerminalC);
            setObjVar(self, "terminal4", elevatorTerminalD);
        }
        else 
        {
            LOG("elevator", "emperors_retreat_elevators could not get 'backelevator' cell");
        }
        setObjVar(self, "alreadySpawned", 1);
        attachScript(elevator, "systems.elevator.button_check");
        attachScript(backelevator, "systems.elevator.button_check_2");
        return SCRIPT_CONTINUE;
    }
}
