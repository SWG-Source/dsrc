package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mouse2 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public mouse2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "moveToLocationOne", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int moveToLocationOne(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationOne(self);
        messageTo(self, "moveToLocationTwo", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public void pathToLocationOne(obj_id mouse) throws InterruptedException
    {
        setObjVar(mouse, "newbie.movingToLocation", 1);
        obj_id bldg = getTopMostContainer(mouse);
        location destLoc = new location(getLocation(mouse));
        destLoc.x = MOUSE_DROID2_DEST_X;
        destLoc.y = MOUSE_DROID2_DEST_Y;
        destLoc.z = MOUSE_DROID2_DEST_Z;
        destLoc.cell = getCellId(bldg, MOUSE_DROID2_DEST_CELL);
        pathTo(mouse, destLoc);
    }
    public int moveToLocationTwo(obj_id self, dictionary params) throws InterruptedException
    {
        pathToLocationTwo(self);
        messageTo(self, "moveToLocationOne", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public void pathToLocationTwo(obj_id mouse) throws InterruptedException
    {
        location destLoc = getHomeLocation(mouse);
        pathTo(mouse, destLoc);
        setObjVar(mouse, "newbie.movingToLocation", 2);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        int dest = getIntObjVar(self, "newbie.movingToLocation");
        if (dest == 1)
        {
            pathToLocationTwo(self);
        }
        else 
        {
            pathToLocationOne(self);
        }
        return SCRIPT_CONTINUE;
    }
}
