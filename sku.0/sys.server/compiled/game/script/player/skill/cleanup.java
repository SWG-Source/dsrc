package script.player.skill;

import script.dictionary;
import script.library.meditation;
import script.library.metrics;
import script.obj_id;

public class cleanup extends script.base_script
{
    public cleanup()
    {
    }
    public int OnRecapacitated(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, meditation.VAR_FORCE_OF_WILL_ACTIVE))
        {
            int stamp = getIntObjVar(self, meditation.VAR_FORCE_OF_WILL_ACTIVE);
            if (stamp < 0)
            {
                removeObjVar(self, meditation.VAR_FORCE_OF_WILL_ACTIVE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePowerBoostWane(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, meditation.VAR_POWERBOOST_ACTIVE))
        {
            return SCRIPT_CONTINUE;
        }
        int expire = getIntObjVar(self, meditation.VAR_POWERBOOST_ACTIVE);
        int d_expire = params.getInt("expiration");
        if (expire != d_expire)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, meditation.SID_POWERBOOST_WANE);
        return SCRIPT_CONTINUE;
    }
    public int handlePowerBoostEnd(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, meditation.VAR_POWERBOOST_ACTIVE))
        {
            return SCRIPT_CONTINUE;
        }
        int expire = getIntObjVar(self, meditation.VAR_POWERBOOST_ACTIVE);
        int d_expire = params.getInt("expiration");
        if (expire != d_expire)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, meditation.SID_POWERBOOST_END);
        removeObjVar(self, meditation.VAR_POWERBOOST_ACTIVE);
        return SCRIPT_CONTINUE;
    }
    public int handlePowerBoostLog(obj_id self, dictionary params) throws InterruptedException
    {
        metrics.logBuffStatus(self);
        return SCRIPT_CONTINUE;
    }
}
