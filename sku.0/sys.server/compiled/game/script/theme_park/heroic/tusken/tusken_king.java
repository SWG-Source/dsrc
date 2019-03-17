package script.theme_park.heroic.tusken;

import script.dictionary;
import script.library.ai_lib;
import script.library.buff;
import script.library.factions;
import script.obj_id;

import java.util.Vector;

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
        for (obj_id obj_id : allObj) {
            if (isDead(obj_id)) {
                continue;
            }
            if (factions.getFaction(obj_id) == null || factions.getFaction(obj_id) == "") {
                continue;
            }
            if (!(factions.getFaction(obj_id)).equals("heroic_tusken")) {
                continue;
            }
            if (!isIdValid(getLocation(self).cell) && isIdValid(getLocation(obj_id).cell)) {
                continue;
            }
            if (isIdValid(getLocation(self).cell) && isIdValid(getLocation(obj_id).cell) && getLocation(self).cell != getLocation(obj_id).cell) {
                continue;
            }
            allies.add(obj_id);
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
