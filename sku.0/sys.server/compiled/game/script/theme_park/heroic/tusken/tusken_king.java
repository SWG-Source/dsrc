package script.theme_park.heroic.tusken;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.factions;
import script.library.utils;

public class tusken_king extends script.base_script
{
    public tusken_king()
    {
    }
    public int setupSquad(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "establishUnity", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "establishUnity", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int establishUnity(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] allObj = getNPCsInRange(getLocation(self), 150.0f);
        Vector allies = new Vector();
        allies.setSize(0);
        for (int i = 0; i < allObj.length; i++)
        {
            if (isDead(allObj[i]))
            {
                continue;
            }
            if (factions.getFaction(allObj[i]) == null || factions.getFaction(allObj[i]) == "")
            {
                continue;
            }
            if (!(factions.getFaction(allObj[i])).equals("heroic_tusken"))
            {
                continue;
            }
            if (!isIdValid(getLocation(self).cell) && isIdValid(getLocation(allObj[i]).cell))
            {
                continue;
            }
            if (isIdValid(getLocation(self).cell) && isIdValid(getLocation(allObj[i]).cell) && getLocation(self).cell != getLocation(allObj[i]).cell)
            {
                continue;
            }
            allies.add(allObj[i]);
        }
        ai_lib.establishSharedHealth(allies);
        obj_id[] someList = new obj_id[0];
        if (allies != null)
        {
            someList = new obj_id[allies.size()];
            allies.toArray(someList);
        }
        ai_lib.establishAgroLink(self, someList);
        buff.applyBuff(someList, "tusken_unity");
        return SCRIPT_CONTINUE;
    }
}
