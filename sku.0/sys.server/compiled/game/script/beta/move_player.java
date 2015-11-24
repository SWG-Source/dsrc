package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class move_player extends script.base_script
{
    public move_player()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        String[] elems = split(text, ' ');
        if (elems.length < 4)
        {
            return SCRIPT_CONTINUE;
        }
        if (elems[0] == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!elems[0].equals("move"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.stringToObjId(elems[1]);
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(target))
        {
            return SCRIPT_CONTINUE;
        }
        float x = utils.stringToFloat(elems[2]);
        float z = utils.stringToFloat(elems[3]);
        location destLoc = new location(getLocation(self));
        destLoc.x = x;
        destLoc.z = z;
        if (elems.length > 4)
        {
            destLoc.area = elems[4];
        }
        warpPlayer(target, destLoc.area, destLoc.x, 0.0f, destLoc.z, null, 0.0f, 0.0f, 0.0f);
        return SCRIPT_CONTINUE;
    }
}
