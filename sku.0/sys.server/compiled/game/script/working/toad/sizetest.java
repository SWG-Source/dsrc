package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class sizetest extends script.base_script
{
    public sizetest()
    {
    }
    public static final String datatable = "datatables/mob/creatures.iff";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        int stringCheck = text.indexOf("testsize ");
        if (stringCheck > -1)
        {
            String toCreate = text.substring(text.indexOf(" ") + 1, text.length());
            String[] column = dataTableGetStringColumn(datatable, "template");
            dictionary creatureDict = utils.dataTableGetRow(datatable, toCreate);
            if (creatureDict == null)
            {
                return SCRIPT_OVERRIDE;
            }
            float min = creatureDict.getFloat("minScale");
            float max = creatureDict.getFloat("maxScale");
            float small = min;
            float big = max;
            String alternate = "";
            String templateName = creatureDict.getString("template");
            if (templateName.indexOf(".iff") == -1)
            {
                return SCRIPT_OVERRIDE;
            }
            int hueCheck = templateName.indexOf("_hue");
            if (hueCheck > -1)
            {
                alternate = templateName.substring(0, hueCheck - 1);
                alternate = alternate + ".iff";
            }
            else 
            {
                alternate = templateName.substring(0, text.indexOf(".") - 1);
                alternate = alternate + "_hue.iff";
            }
            int x = 0;
            for (x = 0; x < column.length; x++)
            {
                if (column[x].equals(templateName) || column[x].equals(alternate))
                {
                    min = dataTableGetFloat(datatable, x, "minScale");
                    max = dataTableGetFloat(datatable, x, "maxScale");
                    if (min < small)
                    {
                        small = min;
                    }
                    if (max > big)
                    {
                        big = max;
                    }
                }
            }
            location here = getLocation(self);
            here.x = here.x + 2;
            obj_id guy = create.object(toCreate, here);
            setScale(guy, big);
            debugSpeakMsg(guy, "I'm set to " + big);
            here.x = here.x + 2;
            obj_id guy2 = create.object(toCreate, here);
            setScale(guy2, small);
            debugSpeakMsg(guy2, "I'm set to " + small);
        }
        return SCRIPT_CONTINUE;
    }
}
