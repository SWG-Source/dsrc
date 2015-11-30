package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.regions;

public class top extends script.base_script
{
    public top()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("test"))
        {
            location here = getLocation(self);
            obj_id bldg = getTopMostContainer(self);
            debugSpeakMsg(self, "I'm contained by " + bldg);
            location there = getLocation(bldg);
            location target = new location();
            region[] rgnFoos = getRegionsWithGeographicalAtPoint(there, regions.GEO_CITY);
            if (rgnFoos == null)
            {
                debugSpeakMsg(self, "NULL FOOS");
                return SCRIPT_CONTINUE;
            }
            region quest = rgnFoos[0];
        }
        return SCRIPT_CONTINUE;
    }
}
