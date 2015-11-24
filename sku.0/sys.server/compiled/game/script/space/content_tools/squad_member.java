package script.space.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.space_create;

public class squad_member extends script.base_script
{
    public squad_member()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "intCleaningUp"))
        {
            return SCRIPT_CONTINUE;
        }
        int intStartPopulation = utils.getIntScriptVar(self, "intStartPopulation");
        if (intStartPopulation == 0)
        {
            return SCRIPT_CONTINUE;
        }
        float fltRemainingPercentage = 0;
        int intMembers = 0;
        int intSquadId = ship_ai.unitGetSquadId(self);
        obj_id[] objMembers = ship_ai.squadGetUnitList(intSquadId);
        for (int intI = 0; intI < objMembers.length; intI++)
        {
            if (isIdValid(objMembers[intI]))
            {
                intMembers = intMembers + 1;
            }
        }
        intMembers = intMembers - 1;
        if (intMembers > 0)
        {
            fltRemainingPercentage = (float)intMembers / (float)intStartPopulation;
        }
        float fltThresholdPercentage = utils.getFloatScriptVar(self, "fltDestroyPercentage");
        if (fltRemainingPercentage <= fltThresholdPercentage)
        {
            for (int intI = 0; intI < objMembers.length; intI++)
            {
                if (isIdValid(objMembers[intI]) && (objMembers[intI] != self))
                {
                    setObjVar(objMembers[intI], "noNotify", 1);
                }
            }
            if (!hasObjVar(self, "noNotify"))
            {
                obj_id objParent = getObjIdObjVar(self, "objParent");
                dictionary dctParams = new dictionary();
                space_utils.notifyObject(objParent, "childDestroyed", dctParams);
                setObjVar(self, "noNotify", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
