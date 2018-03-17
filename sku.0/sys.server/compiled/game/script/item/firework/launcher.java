package script.item.firework;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.colors;
import script.library.firework;

public class launcher extends script.base_script
{
    public launcher()
    {
    }
    public int handleFireworkLaunch(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String[] fxs = params.getStringArray("fx");
        float[] delays = params.getFloatArray("delay");
        if (fxs == null || fxs.length == 0 || delays == null || delays.length == 0)
        {
            firework.dud(player);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (fxs.length != delays.length)
        {
            firework.dud(player);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        int idx = params.getInt("idx");
        if (idx >= fxs.length)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        location there = utils.getRandomLocationInRing(here, 0.5f, 1f);
        if (there == null)
        {
            there = (location)here.clone();
        }
        params.put("loc", there);
        String fx = fxs[idx];
        String template = dataTableGetString(firework.TBL_FX, fx, "template");
        if (template == null || template.equals(""))
        {
            template = dataTableGetString(firework.TBL_FX, "default", "template");
        }
        obj_id ignition = createObject(template, there);
        if (isIdValid(ignition))
        {
            attachScript(ignition, firework.SCRIPT_FIREWORK_CLEANUP);
        }
        float delay = delays[idx];
        if (delay < firework.SHOW_DELAY_MIN)
        {
            delay = firework.SHOW_DELAY_MIN;
        }
        idx++;
        params.put("idx", idx);
        messageTo(self, firework.HANDLER_FIREWORK_LAUNCH, params, delay, false);
        return SCRIPT_CONTINUE;
    }
}
