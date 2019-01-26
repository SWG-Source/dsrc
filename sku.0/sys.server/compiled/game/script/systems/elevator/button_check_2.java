package script.systems.elevator;

import script.dictionary;
import script.location;
import script.obj_id;

public class button_check_2 extends script.base_script
{
    public button_check_2()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id home = getTopMostContainer(self);
        String building = getTemplateName(home);
        if (building.equals(""))
        {
            return SCRIPT_OVERRIDE;
        }
        dictionary buttons = new dictionary();
        buttons.put("home", home);
        buttons.put("item", item);
        if (building.equals("object/building/naboo/emperors_retreat.iff"))
        {
            messageTo(self, "emperorsRetreatBack", buttons, 1, true);
        }
        int hospitalCheck = building.indexOf("hospital");
        if (hospitalCheck > -1)
        {
            messageTo(self, "hospital", buttons, 1, true);
        }
        if (building.equals("object/building/tatooine/lucky_despot.iff"))
        {
            messageTo(self, "luckyDespot", buttons, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int emperorsRetreatBack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal3");
        obj_id elevator2 = getObjIdObjVar(home, "terminal4");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "backelevator");
                location here = getLocation(home);
                here.x = -52.983f;
                here.y = 0.2f;
                here.z = -12.49f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setYaw(elevatorTerminalA, 90);
                setObjVar(home, "terminal3", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "backelevator");
            location here = getLocation(home);
            here.x = -52.983f;
            here.y = 0.2f;
            here.z = -12.49f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalA, 90);
            setObjVar(home, "terminal3", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "backelevator");
                location here = getLocation(home);
                here.x = -52.983f;
                here.y = -9.8f;
                here.z = -12.49f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setYaw(elevatorTerminalB, 90);
                setObjVar(home, "terminal4", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "backelevator");
            location here = getLocation(home);
            here.x = -52.983f;
            here.y = -9.8f;
            here.z = -12.49f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalB, 90);
            setObjVar(home, "terminal4", elevatorTerminalB);
        }
        return SCRIPT_CONTINUE;
    }
    public int hospital(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal4");
        obj_id elevator2a = getObjIdObjVar(home, "terminalB1");
        obj_id elevator2b = getObjIdObjVar(home, "terminalB2");
        obj_id elevator3 = getObjIdObjVar(home, "terminal6");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevatorb");
                location here = getLocation(home);
                here.x = -0.14f;
                here.y = 0.25f;
                here.z = 2.24f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setYaw(elevatorTerminalA, -134);
                setObjVar(home, "terminal4", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevatorb");
            location here = getLocation(home);
            here.x = -0.14f;
            here.y = 0.25f;
            here.z = 2.24f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalA, -134);
            setObjVar(home, "terminal4", elevatorTerminalA);
        }
        if (exists(elevator2a) && exists(elevator2b))
        {
            obj_id cell = getContainedBy(elevator2a);
            if (cell != self)
            {
                destroyObject(elevator2a);
                destroyObject(elevator2b);
                obj_id elevatorCell = getCellId(home, "elevatorb");
                location here = getLocation(home);
                here.x = -0.13f;
                here.y = 7.25f;
                here.z = 2.18f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                here.x = -0.29f;
                here.z = 2.34f;
                obj_id elevatorTerminalB2 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setYaw(elevatorTerminalB1, -129.85f);
                setYaw(elevatorTerminalB2, -144.72f);
                setObjVar(home, "terminalB1", elevatorTerminalB1);
                setObjVar(home, "terminalB2", elevatorTerminalB2);
            }
        }
        else 
        {
            if (exists(elevator2a))
            {
                destroyObject(elevator2a);
            }
            if (exists(elevator2b))
            {
                destroyObject(elevator2b);
            }
            obj_id elevatorCell = getCellId(home, "elevatorb");
            location here = getLocation(home);
            here.x = -0.13f;
            here.y = 7.25f;
            here.z = 2.18f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            here.x = -0.29f;
            here.z = 2.34f;
            obj_id elevatorTerminalB2 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalB1, -129.85f);
            setYaw(elevatorTerminalB2, -144.72f);
            setObjVar(home, "terminalB1", elevatorTerminalB1);
            setObjVar(home, "terminalB2", elevatorTerminalB2);
        }
        if (exists(elevator3))
        {
            obj_id cell = getContainedBy(elevator3);
            if (cell != self)
            {
                destroyObject(elevator3);
                obj_id elevatorCell = getCellId(home, "elevatorb");
                location here = getLocation(home);
                here.x = -0.14f;
                here.y = 13.5f;
                here.z = 2.24f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setYaw(elevatorTerminalC, -134);
                setObjVar(home, "terminal6", elevatorTerminalC);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevatorb");
            location here = getLocation(home);
            here.x = -0.14f;
            here.y = 13.5f;
            here.z = 2.24f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalC, -134);
            setObjVar(home, "terminal6", elevatorTerminalC);
        }
        return SCRIPT_CONTINUE;
    }
    public int luckyDespot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal3");
        obj_id elevator2 = getObjIdObjVar(home, "terminal4");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevator02");
                location here = getLocation(home);
                here.x = -3.45f;
                here.y = 9.01f;
                here.z = -21.44f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setObjVar(home, "terminal3", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator02");
            location here = getLocation(home);
            here.x = -3.45f;
            here.y = 9.01f;
            here.z = -21.44f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setObjVar(home, "terminal3", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "elevator02");
                location here = getLocation(home);
                here.x = -3.45f;
                here.y = 0.01f;
                here.z = -21.44f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setObjVar(home, "terminal4", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator02");
            location here = getLocation(home);
            here.x = -3.45f;
            here.y = 0.01f;
            here.z = -21.44f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(home, "terminal4", elevatorTerminalB);
        }
        return SCRIPT_CONTINUE;
    }
}
