package script.theme_park.poi.general.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class pounce_creature extends script.base_script
{
    public pounce_creature()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        debugSpeakMsg(self, "INCAPACITATED WENT OFF DAMMIT");
        obj_id vic1 = getObjIdObjVar(self, "vic1");
        obj_id vic2 = getObjIdObjVar(self, "vic2");
        obj_id vic3 = getObjIdObjVar(self, "vic3");
        messageTo(vic1, "creatureDied", null, 0, true);
        messageTo(vic2, "creatureDied", null, 0, true);
        messageTo(vic3, "creatureDied", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
