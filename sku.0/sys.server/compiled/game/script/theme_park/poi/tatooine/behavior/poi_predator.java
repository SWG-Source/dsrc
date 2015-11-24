package script.theme_park.poi.tatooine.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai;

public class poi_predator extends script.base_script
{
    public poi_predator()
    {
    }
    public int killTarget(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "That's it! Now you die!");
        obj_id target = params.getObjId("target");
        follow(self, target, 5.0f, 10.0f);
        return SCRIPT_CONTINUE;
    }
}
