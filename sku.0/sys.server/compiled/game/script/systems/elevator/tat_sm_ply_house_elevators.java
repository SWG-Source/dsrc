package script.systems.elevator;

import script.location;
import script.obj_id;

public class tat_sm_ply_house_elevators extends script.base_script
{
    public tat_sm_ply_house_elevators()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id elevator = getCellId(self, "elevator1");
        if (isIdValid(elevator))
        {
            here.x = -1.01f;
            here.y = 0.26f;
            here.z = 1.37f;
            here.cell = elevator;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalA, 90);
            here.y = -4.87f;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalB, 90);
            here.y = -10.03f;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalC, 90);
            setObjVar(self, "terminal1", elevatorTerminalA);
            setObjVar(self, "terminal2", elevatorTerminalB);
            setObjVar(self, "terminal3", elevatorTerminalC);
        }
        else 
        {
            LOG("elevator", "tat_sm_ply_house_elevators could not get 'elevator1' cell");
        }
        setObjVar(self, "alreadySpawned", 1);
        attachScript(elevator, "systems.elevator.button_check");
        return SCRIPT_CONTINUE;
    }
}
