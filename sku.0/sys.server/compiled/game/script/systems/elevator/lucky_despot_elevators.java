package script.systems.elevator;

import script.location;
import script.obj_id;

public class lucky_despot_elevators extends script.base_script
{
    public lucky_despot_elevators()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id elevator1 = getCellId(self, "elevator01");
        if (isIdValid(elevator1))
        {
            here.x = -7.53f;
            here.y = 9.01f;
            here.z = -21.44f;
            here.cell = elevator1;
            obj_id elevatorTerminalA1 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            here.x = -7.53f;
            here.y = 0.01f;
            here.z = -21.44f;
            here.cell = elevator1;
            obj_id elevatorTerminalB1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(self, "terminal1", elevatorTerminalA1);
            setObjVar(self, "terminal2", elevatorTerminalB1);
        }
        else 
        {
            LOG("elevator", "lucky_despot_elevators could not get 'elevator01' cell");
        }
        obj_id elevator2 = getCellId(self, "elevator02");
        if (isIdValid(elevator2))
        {
            here.x = -3.45f;
            here.y = 9.01f;
            here.z = -21.44f;
            here.cell = elevator2;
            obj_id elevatorTerminalA2 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            here.x = -3.45f;
            here.y = 0.01f;
            here.z = -21.44f;
            here.cell = elevator2;
            obj_id elevatorTerminalB2 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(self, "terminal3", elevatorTerminalA2);
            setObjVar(self, "terminal4", elevatorTerminalB2);
        }
        else 
        {
            LOG("elevator", "lucky_despot_elevators could not get 'elevator02' cell");
        }
        obj_id elevator3 = getCellId(self, "elevator03");
        if (isIdValid(elevator3))
        {
            here.x = 0.5f;
            here.y = 9.01f;
            here.z = -21.44f;
            here.cell = elevator3;
            obj_id elevatorTerminalA3 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            here.x = 0.5f;
            here.y = 0.01f;
            here.z = -21.44f;
            here.cell = elevator3;
            obj_id elevatorTerminalB3 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(self, "terminal5", elevatorTerminalA3);
            setObjVar(self, "terminal6", elevatorTerminalB3);
        }
        else 
        {
            LOG("elevator", "lucky_despot_elevators could not get 'elevator03' cell");
        }
        obj_id elevator4 = getCellId(self, "elevator04");
        if (isIdValid(elevator4))
        {
            here.x = 12.04f;
            here.y = 7.01f;
            here.z = -16.44f;
            here.cell = elevator4;
            obj_id elevatorTerminalA4 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalA4, 90);
            here.x = 12.04f;
            here.y = 15.01f;
            here.z = -16.44f;
            here.cell = elevator4;
            obj_id elevatorTerminalB4 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalB4, 90);
            setObjVar(self, "terminal7", elevatorTerminalA4);
            setObjVar(self, "terminal8", elevatorTerminalB4);
        }
        else 
        {
            LOG("elevator", "lucky_despot_elevators could not get 'elevator04' cell");
        }
        setObjVar(self, "alreadySpawned", 1);
        attachScript(elevator1, "systems.elevator.button_check");
        attachScript(elevator2, "systems.elevator.button_check_2");
        attachScript(elevator3, "systems.elevator.button_check_3");
        attachScript(elevator4, "systems.elevator.button_check_4");
        return SCRIPT_CONTINUE;
    }
}
