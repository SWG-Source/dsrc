package script.systems.elevator;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class button_check_3 extends script.base_script
{
    public button_check_3()
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
        if (building.equals("object/building/tatooine/lucky_despot.iff"))
        {
            messageTo(self, "luckyDespot", buttons, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int luckyDespot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id home = params.getObjId("home");
        obj_id item = params.getObjId("item");
        obj_id elevator = getObjIdObjVar(home, "terminal5");
        obj_id elevator2 = getObjIdObjVar(home, "terminal6");
        if (exists(elevator))
        {
            obj_id cell = getContainedBy(elevator);
            if (cell != self)
            {
                destroyObject(elevator);
                obj_id elevatorCell = getCellId(home, "elevator03");
                location here = getLocation(home);
                here.x = .5f;
                here.y = 9.01f;
                here.z = -21.44f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
                setObjVar(home, "terminal5", elevatorTerminalA);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator03");
            location here = getLocation(home);
            here.x = .5f;
            here.y = 9.01f;
            here.z = -21.44f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalA = createObject("object/tangible/terminal/terminal_elevator_down.iff", here);
            setObjVar(home, "terminal5", elevatorTerminalA);
        }
        if (exists(elevator2))
        {
            obj_id cell = getContainedBy(elevator2);
            if (cell != self)
            {
                destroyObject(elevator2);
                obj_id elevatorCell = getCellId(home, "elevator03");
                location here = getLocation(home);
                here.x = .5f;
                here.y = .01f;
                here.z = -21.44f;
                here.cell = elevatorCell;
                obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
                setObjVar(home, "terminal6", elevatorTerminalB);
            }
        }
        else 
        {
            obj_id elevatorCell = getCellId(home, "elevator03");
            location here = getLocation(home);
            here.x = .5f;
            here.y = .01f;
            here.z = -21.44f;
            here.cell = elevatorCell;
            obj_id elevatorTerminalB = createObject("object/tangible/terminal/terminal_elevator_up.iff", here);
            setObjVar(home, "terminal6", elevatorTerminalB);
        }
        return SCRIPT_CONTINUE;
    }
}
