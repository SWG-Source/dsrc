package script.object;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.regions;
import script.library.utils;

public class sign extends script.base_script
{
    public sign()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        region rgnTest = getSmallestVisibleRegionAtPoint(here);
        if (rgnTest == null)
        {
            debugServerConsoleMsg(self, "DAN FAILED");
            debugServerConsoleMsg(self, "location is " + here);
            region[] rgnFoos = getRegionsAtPoint(here);
            if (rgnFoos == null || rgnFoos.length == 0)
            {
                debugServerConsoleMsg(self, "fucked");
            }
            for (int intI = 0; intI < rgnFoos.length; intI++)
            {
                debugServerConsoleMsg(self, "rgnFooes[" + intI + "] is " + rgnFoos[intI].getName());
            }
            return SCRIPT_OVERRIDE;
        }
        String strTest = rgnTest.getName();
        string_id city = utils.unpackString(strTest);
        if (city == null)
        {
            setObjVar(self, "city", "none");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "cityName", strTest);
        messageTo(self, "setCityName", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        region rgnTest = getSmallestVisibleRegionAtPoint(here);
        if (rgnTest == null)
        {
            return SCRIPT_OVERRIDE;
        }
        String strTest = rgnTest.getName();
        string_id city = utils.unpackString(strTest);
        if (city == null)
        {
            setObjVar(self, "city", "none");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "cityName", strTest);
        messageTo(self, "setCityName", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int setCityName(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "cityName"))
        {
            return SCRIPT_CONTINUE;
        }
        String cityName = utils.getStringScriptVar(self, "cityName");
        string_id city = utils.unpackString(cityName);
        if (city == null)
        {
            setObjVar(self, "city", "none");
            return SCRIPT_CONTINUE;
        }
        if (!setName(self, cityName))
        {
        }
        return SCRIPT_CONTINUE;
    }
}
