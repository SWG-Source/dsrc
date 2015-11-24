package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class invulnerable extends script.base_script
{
    public invulnerable()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        obj_id target = getLookAtTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        if (st.hasMoreTokens())
        {
            String soid = st.nextToken();
            obj_id tmp = utils.stringToObjId(soid);
            if (isIdValid(tmp) && tmp.isLoaded())
            {
                target = tmp;
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (arg.equals("invulnerable"))
        {
            if (setInvulnerable(target, true))
            {
                sendSystemMessageTestingOnly(self, "Target(" + target + ") is INVULNERABLE.");
                return SCRIPT_OVERRIDE;
            }
        }
        else if (arg.equals("vulnerable"))
        {
            if (setInvulnerable(target, false))
            {
                sendSystemMessageTestingOnly(self, "Target(" + target + ") is vulnerable.");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
