package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class path extends script.base_script
{
    public path()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "larkAbout", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public String getRoomName(obj_id building, obj_id cell) throws InterruptedException
    {
        String[] allCells = getCellNames(building);
        int numberOfCells = allCells.length;
        for (int i = 0; i < numberOfCells; i++)
        {
            String cellName = allCells[i];
            obj_id thisCell = getCellId(building, cellName);
            if (thisCell == cell)
            {
                return cellName;
            }
        }
        return "no_match";
    }
    public int larkAbout(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        location here = getLocation(self);
        obj_id room = here.cell;
        if (room == null)
        {
            return SCRIPT_CONTINUE;
        }
        String whereTo = getRoomName(top, room);
        location randomLoc = getGoodLocation(top, whereTo);
        ai_lib.aiPathTo(self, randomLoc);
        addLocationTarget("target", randomLoc, 1);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("target"))
        {
            messageTo(self, "larkAbout", null, 4, false);
        }
        return SCRIPT_CONTINUE;
    }
}
