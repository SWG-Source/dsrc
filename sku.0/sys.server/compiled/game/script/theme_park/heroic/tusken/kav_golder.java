package script.theme_park.heroic.tusken;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.obj_id;

import java.util.Vector;

public class kav_golder extends script.base_script
{
    public kav_golder()
    {
    }
    public int OnArrivedAtLocation(obj_id self, String location) throws InterruptedException
    {
        if (location.equals("kav_final"))
        {
            messageTo(self, "findAllies", null, 10.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int findAllies(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] allObj = getObjectsInRange(getLocation(self), 30.0f);
        Vector allies = new Vector();
        allies.setSize(0);
        for (obj_id obj_id : allObj) {
            if (hasObjVar(obj_id, "faction")) {

            }

            {
                String fac = factions.getFaction(obj_id);
                if (fac == null || fac.equals("")) {
                    continue;
                }
                if (fac.equals("espa") || fac.equals("espa_guard")) {
                    allies.add(obj_id);
                }
            }
        }
        obj_id[] goodList = new obj_id[0];
        if (allies != null)
        {
            goodList = new obj_id[allies.size()];
            allies.toArray(goodList);
        }
        ai_lib.establishAgroLink(self, goodList);
        return SCRIPT_CONTINUE;
    }
}
