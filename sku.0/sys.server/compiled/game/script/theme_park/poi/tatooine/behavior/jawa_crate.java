package script.theme_park.poi.tatooine.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;

public class jawa_crate extends script.theme_park.poi.base
{
    public jawa_crate()
    {
    }
    public int OnAboutToLoseItem(obj_id self, obj_id dest, obj_id grabber, obj_id item) throws InterruptedException
    {
        obj_id jawa1 = getObjIdObjVar(self, "jawa1");
        obj_id jawa2 = getObjIdObjVar(self, "jawa2");
        obj_id jawa3 = getObjIdObjVar(self, "jawa3");
        startCombat(jawa1, grabber);
        startCombat(jawa2, grabber);
        startCombat(jawa3, grabber);
        return SCRIPT_CONTINUE;
    }
}
