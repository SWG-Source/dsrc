package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class cincotest extends script.base_script
{
    public cincotest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("setupTerminal"))
        {
            obj_id objTarget = getLookAtTarget(self);
            if (isIdValid(objTarget))
            {
                location locTest = new location();
                locTest.area = "space_tatooine";
                setObjVar(objTarget, "space.destination", locTest);
                sendSystemMessageTestingOnly(self, "Set the coordinates to your thang!");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
