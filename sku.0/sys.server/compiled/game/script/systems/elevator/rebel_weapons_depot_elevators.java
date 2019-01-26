package script.systems.elevator;

import script.location;
import script.obj_id;

public class rebel_weapons_depot_elevators extends script.base_script
{
    public rebel_weapons_depot_elevators()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id elevator = getCellId(self, "elevator1");
        if (isIdValid(elevator))
        {
            here.x = -8.4f;
            here.y = -12.0f;
            here.z = 59.0f;
            here.cell = elevator;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalA, 90);
            here.y = -20.0f;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalB, 90);
            setObjVar(self, "terminal1", elevatorTerminalA);
            setObjVar(self, "terminal2", elevatorTerminalB);
        }
        else 
        {
            LOG("elevator", "rebel_weapons_depot could not get 'elevator1' cell");
        }
        obj_id secondElevator = getCellId(self, "elevator2");
        if (isIdValid(secondElevator))
        {
            here.x = 75.4f;
            here.y = -20.0f;
            here.z = 59.0f;
            here.cell = secondElevator;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalC, -90);
            here.y = -50.0f;
            obj_id elevatorTerminalD = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalD, -90);
            setObjVar(self, "terminal3", elevatorTerminalC);
            setObjVar(self, "terminal4", elevatorTerminalD);
        }
        else 
        {
            LOG("elevator", "rebel_weapons_depot could not get 'elevator2' cell");
        }
        setObjVar(self, "alreadySpawned", 1);
        attachScript(elevator, "systems.elevator.button_check");
        attachScript(secondElevator, "systems.elevator.button_check_2");
        return SCRIPT_CONTINUE;
    }
}
