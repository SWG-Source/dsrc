package script.poi.city;

import script.dictionary;
import script.location;
import script.obj_id;

public class taike_tusken extends script.base_script
{
    public taike_tusken()
    {
    }
    public int goToTheSpot(obj_id self, dictionary params) throws InterruptedException
    {
        location there = new location(3839.0f, 22.0f, 2351.0f, "tatooine", null);
        pathTo(self, there);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "I DON'T KNOW WHERE TO GO!");
        return SCRIPT_CONTINUE;
    }
}
