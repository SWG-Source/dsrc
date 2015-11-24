package script.csr;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class cheater extends script.base_script
{
    public cheater()
    {
    }
    public static final String CHEATER_SCRIPT = "csr.cheater";
    public static final String CHEATER_OBJVAR = "cheater";
    public static final String DURATION_OBJVAR = "cheater_days";
    public static final int DEFAULT_DURATION = 7;
    public static final int MAX_DURATION = 30;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isPlayer(self))
        {
            detachScript(self, CHEATER_SCRIPT);
            CustomerServiceLog("SuspectedCheaterChannel", "Script Attach Failed: Tried to attach script '" + CHEATER_SCRIPT + "' to a non-player object (" + self + ")");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, CHEATER_OBJVAR, getGameTime());
        CustomerServiceLog("SuspectedCheaterChannel", "Begin Tracking: " + getName(self) + " (" + self + ") has been flagged as a suspected cheater");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, CHEATER_OBJVAR))
        {
            detachScript(self, CHEATER_SCRIPT);
            CustomerServiceLog("SuspectedCheaterChannel", "Script Clean-up: Script '" + CHEATER_SCRIPT + "' attached to player " + getName(self) + " (" + self + ") without " + CHEATER_OBJVAR + " obj var -- detaching script");
            return SCRIPT_CONTINUE;
        }
        int startTrackingTime;
        int deltaTime;
        int days;
        if (hasObjVar(self, DURATION_OBJVAR))
        {
            days = getIntObjVar(self, DURATION_OBJVAR);
            if (days > MAX_DURATION)
            {
                days = MAX_DURATION;
                setObjVar(self, DURATION_OBJVAR, MAX_DURATION);
            }
        }
        else 
        {
            days = DEFAULT_DURATION;
        }
        startTrackingTime = getIntObjVar(self, CHEATER_OBJVAR);
        deltaTime = days * 86400;
        if (getGameTime() > (startTrackingTime + deltaTime))
        {
            if (hasObjVar(self, DURATION_OBJVAR))
            {
                removeObjVar(self, DURATION_OBJVAR);
            }
            removeObjVar(self, CHEATER_OBJVAR);
            detachScript(self, CHEATER_SCRIPT);
            CustomerServiceLog("SuspectedCheaterChannel", "End Tracking: " + getName(self) + " (" + self + ") was tracked for " + days + " day(s) and is no longer flagged as a suspected cheater");
        }
        return SCRIPT_CONTINUE;
    }
}
