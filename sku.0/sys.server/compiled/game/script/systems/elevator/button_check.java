package script.systems.elevator;

import script.dictionary;
import script.location;
import script.obj_id;

public class button_check extends script.base_script
{
    public button_check()
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
        if (building.equals("object/building/tatooine/housing_tatt_style01_small.iff"))
        {
            messageTo(self, "tatSmallPlayer", buttons, 1, true);
        }
        if (building.equals("object/building/naboo/emperors_retreat.iff"))
        {
            messageTo(self, "emperorsRetreat", buttons, 1, true);
        }
        if (building.equals("object/building/tatooine/lucky_despot.iff"))
        {
            messageTo(self, "luckyDespot", buttons, 1, true);
        }
        int hospitalCheck = building.indexOf("hospital");
        if (hospitalCheck > -1)
        {
            messageTo(self, "hospital", buttons, 1, true);
        }
        int guildCheck = building.indexOf("guildhall");
        if (guildCheck > -1)
        {
            messageTo(self, "guildhall", buttons, 1, true);
        }
        int hallCheck = building.indexOf("association_hall");
        if (hallCheck > -1)
        {
            messageTo(self, "guildhall", buttons, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int emperorsRetreat(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal1");
        obj_id elevator2 = getObjIdObjVar(home, "terminal2");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "empelevator");
                location here = getLocation(home);
                here.x = 13.01f;
                here.y = 0.6f;
                here.z = -35.918f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setObjVar(home, "terminal1", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "empelevator");
            location here = getLocation(home);
            here.x = 13.01f;
            here.y = 0.6f;
            here.z = -35.918f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(home, "terminal1", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "empelevator");
                location here = getLocation(home);
                here.x = 13.01f;
                here.y = 20.0f;
                here.z = -35.918f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setObjVar(home, "terminal2", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "empelevator");
            location here = getLocation(home);
            here.x = 13.01f;
            here.y = 20.0f;
            here.z = -35.918f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setObjVar(home, "terminal2", elevatorTerminalB);
        }
        return SCRIPT_CONTINUE;
    }
    public int tatSmallPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal1");
        obj_id elevator2 = getObjIdObjVar(home, "terminal2");
        obj_id elevator3 = getObjIdObjVar(home, "terminal3");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevator1");
                location here = getLocation(home);
                here.x = -1.01f;
                here.y = 0.26f;
                here.z = 1.37f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator.iff", here);
                setYaw(elevatorTerminalA, 90);
                setObjVar(home, "terminal1", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator1");
            location here = getLocation(home);
            here.x = -1.01f;
            here.y = 0.26f;
            here.z = 1.37f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalA, 90);
            setObjVar(home, "terminal1", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "elevator1");
                location here = getLocation(home);
                here.x = -1.01f;
                here.y = -4.87f;
                here.z = 1.37f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator.iff", here);
                setYaw(elevatorTerminalB, 90);
                setObjVar(home, "terminal2", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator1");
            location here = getLocation(home);
            here.x = -1.01f;
            here.y = -4.87f;
            here.z = 1.37f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalB, 90);
            setObjVar(home, "terminal2", elevatorTerminalB);
        }
        if (exists(elevator3))
        {
            obj_id cell = getContainedBy(elevator3);
            if (cell != self)
            {
                destroyObject(elevator3);
                obj_id elevatorCell = getCellId(home, "elevator1");
                location here = getLocation(home);
                here.x = -1.01f;
                here.y = -10.03f;
                here.z = 1.37f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator.iff", here);
                setYaw(elevatorTerminalC, 90);
                setObjVar(home, "terminal3", elevatorTerminalC);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator1");
            location here = getLocation(home);
            here.x = -1.01f;
            here.y = -10.03f;
            here.z = 1.37f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator.iff", here);
            setYaw(elevatorTerminalC, 90);
            setObjVar(home, "terminal3", elevatorTerminalC);
        }
        return SCRIPT_CONTINUE;
    }
    public int luckyDespot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal1");
        obj_id elevator2 = getObjIdObjVar(home, "terminal2");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevator01");
                location here = getLocation(home);
                here.x = -7.53f;
                here.y = 9.01f;
                here.z = -21.44f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setObjVar(home, "terminal1", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator01");
            location here = getLocation(home);
            here.x = -7.53f;
            here.y = 9.01f;
            here.z = -21.44f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setObjVar(home, "terminal1", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "elevator01");
                location here = getLocation(home);
                here.x = -7.53f;
                here.y = 0.01f;
                here.z = -21.44f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setObjVar(home, "terminal2", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator01");
            location here = getLocation(home);
            here.x = -7.53f;
            here.y = 0.01f;
            here.z = -21.44f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(home, "terminal2", elevatorTerminalB);
        }
        return SCRIPT_CONTINUE;
    }
    public int hospital(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal1");
        obj_id elevator2a = getObjIdObjVar(home, "terminalA1");
        obj_id elevator2b = getObjIdObjVar(home, "terminalA2");
        obj_id elevator3 = getObjIdObjVar(home, "terminal3");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevatora");
                location here = getLocation(home);
                here.x = 0.314f;
                here.y = 0.25f;
                here.z = -1.99f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setYaw(elevatorTerminalA, 50);
                setObjVar(home, "terminal1", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevatora");
            location here = getLocation(home);
            here.x = 0.314f;
            here.y = 0.25f;
            here.z = -1.99f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalA, 50);
            setObjVar(home, "terminal1", elevatorTerminalA);
        }
        if (exists(elevator2a) && exists(elevator2b))
        {
            obj_id cell = getContainedBy(elevator2a);
            if (cell != self)
            {
                destroyObject(elevator2a);
                destroyObject(elevator2b);
                obj_id elevatorCell = getCellId(home, "elevatora");
                location here = getLocation(home);
                here.x = 0.14f;
                here.y = 7.25f;
                here.z = -2.19f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                here.x = 0.29f;
                here.z = -2.34f;
                obj_id elevatorTerminalB2 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setYaw(elevatorTerminalB2, 38.19f);
                setYaw(elevatorTerminalB1, 47.39f);
                setObjVar(home, "terminalA1", elevatorTerminalB1);
                setObjVar(home, "terminalA2", elevatorTerminalB2);
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
            obj_id elevatorCell = getCellId(home, "elevatora");
            location here = getLocation(home);
            here.x = 0.14f;
            here.y = 7.25f;
            here.z = -2.19f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB1 = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            here.x = 0.29f;
            here.z = -2.34f;
            obj_id elevatorTerminalB2 = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalB1, 47.39f);
            setYaw(elevatorTerminalB2, 38.19f);
            setObjVar(home, "terminalA1", elevatorTerminalB1);
            setObjVar(home, "terminalA2", elevatorTerminalB2);
        }
        if (exists(elevator3))
        {
            obj_id cell = getContainedBy(elevator3);
            if (cell != self)
            {
                destroyObject(elevator3);
                obj_id elevatorCell = getCellId(home, "elevatora");
                location here = getLocation(home);
                here.x = 0.314f;
                here.y = 13.5f;
                here.z = -1.99f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setYaw(elevatorTerminalC, 50);
                setObjVar(home, "terminal3", elevatorTerminalC);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevatora");
            location here = getLocation(home);
            here.x = 0.314f;
            here.y = 13.5f;
            here.z = -1.99f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalC = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalC, 50);
            setObjVar(home, "terminal3", elevatorTerminalC);
        }
        return SCRIPT_CONTINUE;
    }
    public int guildhall(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal1");
        obj_id elevator2 = getObjIdObjVar(home, "terminal2");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevator");
                location here = getLocation(home);
                here.x = 0.008f;
                here.y = 2.25f;
                here.z = 13.7f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setYaw(elevatorTerminalA, 180);
                setObjVar(home, "terminal1", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator");
            location here = getLocation(home);
            here.x = 0.008f;
            here.y = 2.25f;
            here.z = 13.7f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setYaw(elevatorTerminalA, 180);
            setObjVar(home, "terminal1", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "elevator");
                location here = getLocation(home);
                here.x = 0.008f;
                here.y = -9.0f;
                here.z = 13.7f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setYaw(elevatorTerminalB, 180);
                setObjVar(home, "terminal2", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator");
            location here = getLocation(home);
            here.x = 0.008f;
            here.y = -9.0f;
            here.z = 13.7f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setYaw(elevatorTerminalB, 180);
            setObjVar(home, "terminal2", elevatorTerminalB);
        }
        return SCRIPT_CONTINUE;
    }
}
