package script.space.content_tools;

import script.dictionary;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.utils;
import script.obj_id;

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
        for (obj_id objMember : objMembers) {
            if (isIdValid(objMember)) {
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
            for (obj_id objMember : objMembers) {
                if (isIdValid(objMember) && (objMember != self)) {
                    setObjVar(objMember, "noNotify", 1);
                }
            }
            if (!hasObjVar(self, "noNotify"))
            {
                obj_id objParent = getObjIdObjVar(self, "objParent");
                dictionary dctParams = new dictionary();
                if(isIdValid(objParent) && exists(objParent))
                    space_utils.notifyObject(objParent, "childDestroyed", dctParams);
                setObjVar(self, "noNotify", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
