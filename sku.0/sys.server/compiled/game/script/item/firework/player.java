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

public class player extends script.base_script
{
    public player()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, firework.SCRIPT_PLAYER);
        return SCRIPT_CONTINUE;
    }
    public int handleFireworkPrepare(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            firework.dud(self);
            return SCRIPT_CONTINUE;
        }
        int posture = getPosture(self);
        params.put("posture", posture);
        float delay = 2f;
        if (posture != POSTURE_CROUCHED)
        {
            queueCommand(self, (28609318), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        else 
        {
            delay = 0f;
        }
        float yaw = getYaw(self);
        params.put("yaw", yaw);
        messageTo(self, firework.HANDLER_FIREWORK_LAUNCH, params, delay, false);
        return SCRIPT_CONTINUE;
    }
    public int handleFireworkLaunch(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            firework.dud(self);
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, "manipulate_low");
        location there = utils.findLocInFrontOfTarget(self, 0.75f);
        if (there == null)
        {
            there = getLocation(self);
        }
        params.put("loc", there);
        String fx = params.getString("fx");
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
        messageTo(self, firework.HANDLER_FIREWORK_CLEANUP, params, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleFireworkCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            firework.dud(self);
            return SCRIPT_CONTINUE;
        }
        int posture = params.getInt("posture");
        if (posture != POSTURE_CROUCHED)
        {
            queueCommand(self, (-1465754503), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        firework.decrementFireworkCount(self);
        return SCRIPT_CONTINUE;
    }
}
