package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class hanger_elevator extends script.base_script
{
    public hanger_elevator()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "an elevator terminal");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        location myLoc = getLocation(player);
        obj_id building = getTopMostContainer(player);
        String currentCell = getCellName(myLoc.cell);
        if (currentCell.equals("elevator_e3_down"))
        {
            playClientEffectObj(player, "clienteffect/elevator_rise.cef", player, null);
            obj_id objCell = getCellId(building, "elevator_e3_up");
            myLoc = new location();
            myLoc.cell = objCell;
            myLoc.x = 19.3f;
            myLoc.y = 453.6f;
            myLoc.z = 341.8f;
            setLocation(player, myLoc);
        }
        else 
        {
            playClientEffectObj(player, "clienteffect/elevator_descend.cef", player, null);
            obj_id objCell = getCellId(building, "elevator_e3_down");
            myLoc = new location();
            myLoc.cell = objCell;
            myLoc.x = -74.4f;
            myLoc.y = 204.9f;
            myLoc.z = 330.2f;
            setLocation(player, myLoc);
        }
        return SCRIPT_CONTINUE;
    }
}
