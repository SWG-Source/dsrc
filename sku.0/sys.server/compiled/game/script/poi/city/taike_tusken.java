package script.poi.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class taike_tusken extends script.base_script
{
    public taike_tusken()
    {
    }
    public int goToTheSpot(obj_id self, dictionary params) throws InterruptedException
    {
        location there = new location(3839f, 22f, 2351f, "tatooine", null);
        pathTo(self, there);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "I DON'T KNOW WHERE TO GO!");
        return SCRIPT_CONTINUE;
    }
}
