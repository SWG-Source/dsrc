package script.theme_park.heroic.tusken;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.ai_lib;

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
        for (int i = 0; i < allObj.length; i++)
        {
            if (hasObjVar(allObj[i], "faction"))
            {
                
            }
            
            {
                String fac = factions.getFaction(allObj[i]);
                if (fac == null || fac.equals(""))
                {
                    continue;
                }
                if (fac.equals("espa") || fac.equals("espa_guard"))
                {
                    allies.add(allObj[i]);
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
