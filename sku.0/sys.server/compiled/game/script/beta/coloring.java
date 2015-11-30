package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.colors;
import script.library.utils;

public class coloring extends script.base_script
{
    public coloring()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        int stringCheck = text.indexOf("color");
        int twoCheck = text.indexOf("shade");
        if (stringCheck > -1)
        {
            obj_id thing = getLookAtTarget(self);
            String hue = text.substring(text.indexOf(" ") + 1, text.length());
            int col = utils.stringToInt(hue);
            hueClothes(thing, col);
            return SCRIPT_CONTINUE;
        }
        if (twoCheck > -1)
        {
            obj_id thing = getLookAtTarget(self);
            String hue = text.substring(text.indexOf(" ") + 2, text.length());
            int col = utils.stringToInt(hue);
            hueClothes(thing, col);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void hueClothes(obj_id newClothes, int col) throws InterruptedException
    {
        custom_var[] allVars = getAllCustomVars(newClothes);
        for (int i = 0; i < allVars.length; i++)
        {
            custom_var cv = allVars[i];
            ranged_int_custom_var ri = (ranged_int_custom_var)cv;
            if (cv.isPalColor())
            {
                ri.setValue(col);
            }
        }
        return;
    }
}
