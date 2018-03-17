package script.player.cmd;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.firework;

public class misc extends script.base_script
{
    public misc()
    {
    }
    public static final string_id SID_LAUNCH_FIREWORKS_INDOORS = new string_id("firework", "launch_fireworks_indoors");
    public int cmdLaunchFirework(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(target, firework.VAR_FIREWORK_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        if (isIdValid(here.cell))
        {
            sendSystemMessage(self, SID_LAUNCH_FIREWORKS_INDOORS);
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equals(""))
        {
            firework.launch(self, target);
        }
        else 
        {
            if (params.equalsIgnoreCase("show"))
            {
                firework.launchShow(self, target);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
