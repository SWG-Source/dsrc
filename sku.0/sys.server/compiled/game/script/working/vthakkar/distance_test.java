package script.working.vthakkar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class distance_test extends script.base_script
{
    public distance_test()
    {
    }
    
    public int checkDistance(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "vthakkar.distance.actor") && utils.hasScriptVar(self, "vthakkar.distance.time"))
        {
            float time = utils.getFloatScriptVar(self, "vthakkar.distance.time");
            obj_id actor = utils.getObjIdScriptVar(self, "vthakkar.distance.actor");
            float distance = getDistance(self, actor);
            if (distance < 0)
            {
                utils.removeScriptVarTree(self, "vthakkar.distance");
                return SCRIPT_CONTINUE;
            }
            location me = getLocation(self);
            location him = getLocation(actor);
            int mx = (int)me.x;
            int my = (int)me.y;
            int mz = (int)me.z;
            int hx = (int)him.x;
            int hy = (int)him.y;
            int hz = (int)him.z;
            sendSystemMessageTestingOnly(self, ("D: " + distance + " M: " + mx + " " + my + " " + mz + " " + " H: " + hx + " " + hy + " " + hz));
            messageTo(self, "checkDistance", null, time, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        if (arg.equals("startDistance") && st.countTokens() == 2)
        {
            float time = utils.stringToFloat(st.nextToken());
            obj_id actor = utils.stringToObjId(st.nextToken());
            utils.setScriptVar(self, "vthakkar.distance.actor", actor);
            utils.setScriptVar(self, "vthakkar.distance.time", time);
            messageTo(self, "checkDistance", null, time, false);
        }
        else if (arg.equals("resetDistance"))
        {
            if (st.countTokens() == 0)
            {
                utils.removeScriptVarTree(self, "vthakkar.distance");
            }
            else 
            {
                obj_id actor = utils.stringToObjId(st.nextToken());
                utils.removeScriptVarTree(actor, "vthakkar.distance");
            }
        }
        else if (arg.equals("create"))
        {
            if (st.countTokens() == 1)
            {
                String creature = st.nextToken();
                obj_id actor = create.createCreature(creature, getLocation(self), true);
                utils.setScriptVar(self, "vthakkar.distance.actor", actor);
                utils.setScriptVar(self, "vthakkar.distance.time", 0.2f);
                messageTo(self, "checkDistance", null, 0.2f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }

}
