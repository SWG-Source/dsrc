package script.working.wwallace;

import script.location;
import script.obj_id;

public class yt1300_helper extends script.base_script
{
    public yt1300_helper()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("getInYT1300"))
        {
            obj_id objShip = getLookAtTarget(self);
            if (!isIdValid(objShip))
            {
                sendSystemMessageTestingOnly(self, "No look at target");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objCells = getContents(objShip);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = getCellId(objShip, "storage6");
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
        }
        if (strCommands[0].equalsIgnoreCase("unstickYT1300"))
        {
            obj_id ship = getTopMostContainer(self);
            obj_id objCell = getCellId(ship, "storage6");
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("leave"))
        {
            location locTest = new location();
            locTest.area = "tatooine";
            setLocation(self, locTest);
        }
        return SCRIPT_CONTINUE;
    }
}
