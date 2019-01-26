package script.systems.elevator;

import script.location;
import script.obj_id;

public class hospital_elevators extends script.base_script
{
    public hospital_elevators()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id elevatora = getCellId(self, "elevatora");
        if (isIdValid(elevatora))
        {
            here.x = 0.122f;
            here.y = 0.25f;
            here.z = -2.16f;
            here.cell = elevatora;
            obj_id elevatorTerminalA1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalA1, 50);
            here.x = 0.14f;
            here.y = 7.25f;
            here.z = -2.19f;
            obj_id elevatorTerminalA2a = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            here.x = 0.29f;
            here.z = -2.34f;
            obj_id elevatorTerminalA2b = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalA2a, 47.39f);
            setYaw(elevatorTerminalA2b, 38.19f);
            here.y = 13.5f;
            obj_id elevatorTerminalA3 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalA3, 50);
            setObjVar(self, "terminal1", elevatorTerminalA1);
            setObjVar(self, "terminalA1", elevatorTerminalA2a);
            setObjVar(self, "terminalA2", elevatorTerminalA2b);
            setObjVar(self, "terminal3", elevatorTerminalA3);
        }
        else 
        {
            LOG("elevator", "hospital_elevators could not get 'elevatora' cell");
        }
        obj_id elevatorb = getCellId(self, "elevatorb");
        if (isIdValid(elevatorb))
        {
            here.x = -0.14f;
            here.y = 0.25f;
            here.z = 2.24f;
            here.cell = elevatorb;
            obj_id elevatorTerminalB1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalB1, -134);
            here.x = -0.13f;
            here.y = 7.25f;
            here.z = 2.18f;
            obj_id elevatorTerminalB2a = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            here.x = -0.29f;
            here.z = 2.34f;
            obj_id elevatorTerminalB2b = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalB2a, -129.85f);
            setYaw(elevatorTerminalB2b, -144.72f);
            here.y = 13.5f;
            obj_id elevatorTerminalB3 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalB3, -134);
            setObjVar(self, "terminal4", elevatorTerminalB1);
            setObjVar(self, "terminalB1", elevatorTerminalB2a);
            setObjVar(self, "terminalB2", elevatorTerminalB2b);
            setObjVar(self, "terminal6", elevatorTerminalB3);
        }
        else 
        {
            LOG("elevator", "hospital_elevators could not get 'elevatorb' cell");
        }
        setObjVar(self, "alreadySpawned", 1);
        attachScript(elevatora, "systems.elevator.button_check");
        attachScript(elevatorb, "systems.elevator.button_check_2");
        return SCRIPT_CONTINUE;
    }
}
