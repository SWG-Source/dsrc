package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class world_delta extends script.base_script
{
    public world_delta()
    {
    }
    public static final String VAR_DELTA_TARGET = "delta_target";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "world delta script attached!");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.startsWith("delta"))
        {
            obj_id target = getLookAtTarget(self);
            boolean badTarget = true;
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            if (st.countTokens() == 1)
            {
                if ((target == null) || (target == obj_id.NULL_ID))
                {
                    if (hasObjVar(self, VAR_DELTA_TARGET))
                    {
                        target = getObjIdObjVar(self, VAR_DELTA_TARGET);
                    }
                }
                else 
                {
                    debugSpeakMsg(self, "using my lookAtTarget: " + target);
                }
                if (target != null)
                {
                    badTarget = false;
                    location pLoc = getLocation(self);
                    location oLoc = getLocation(target);
                    float dx = pLoc.x - oLoc.x;
                    float dy = pLoc.y - oLoc.y;
                    float dz = pLoc.z - oLoc.z;
                    debugSpeakMsg(self, "(" + target + ") world delta = " + dx + " " + dy + " " + dz);
                }
            }
            else 
            {
                st.nextToken();
                String param = st.nextToken();
                if (param.equals("target"))
                {
                    if (st.hasMoreTokens())
                    {
                        param = st.nextToken();
                        target = utils.stringToObjId(param);
                        if (target != null)
                        {
                            setObjVar(self, VAR_DELTA_TARGET, target);
                            debugSpeakMsg(self, "world delta target set to object " + target);
                            badTarget = false;
                        }
                    }
                }
            }
            if (badTarget)
            {
                debugSpeakMsg(self, "I need to set a delta target using 'delta target <obj_id>'");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
