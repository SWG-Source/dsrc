package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.consumable;
import script.library.player_stomach;
import script.library.healing;

public class foraged extends script.item.comestible.comestible
{
    public foraged()
    {
    }
    public static final String FORAGED_DATA = "datatables/foraging/forage_global.iff";
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        attrib_mod[] am = consumable.getForagedFoodMods(self);
        int n = utils.getValidAttributeIndex(names);
        if (n == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[n] = EXAM_ATTRIB_MOD;
        if ((am == null) || (am.length == 0))
        {
            attribs[n] = EXAM_NONE;
            n++;
            if (n > names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            int numMods = am.length;
            attribs[n] = "" + numMods;
            n++;
            if (n > names.length)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < numMods; i++)
            {
                int attrib = am[i].getAttribute();
                int val = am[i].getValue();
                int newVal = Integer.MIN_VALUE;
                int duration = (int)(am[i].getDuration());
                String sVal = "";
                if (newVal > 0)
                {
                    sVal = "+" + newVal;
                }
                else if (newVal < 0)
                {
                    sVal = "" + newVal;
                }
                names[n] = toLower(consumable.STAT_NAME[attrib]);
                attribs[n] = sVal + "%, " + duration + "s";
                n++;
                if (n > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
