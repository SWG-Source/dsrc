package script.faction_perk.hq;

import script.dictionary;
import script.library.hq;
import script.obj_id;

public class terminal_manager extends script.base_script
{
    public terminal_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        hq.loadHqTerminals(self);
        hq.enableHqTerminals(self);
        hq.loadAlarmUnits(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        hq.cleanupBaseAlarmUnits(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMaintenanceLoop(obj_id self, dictionary params) throws InterruptedException
    {
        int hp = getHitpoints(self);
        boolean disabled = getBooleanObjVar(self, hq.VAR_TERMINAL_DISABLE);
        if (hp > 0 && disabled)
        {
            hq.enableHqTerminals(self);
        }
        else if (hp < 1 && !disabled)
        {
            hq.disableHqTerminals(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int terminalOff(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "hq.objective.tracking"))
        {
            setObjVar(self, "donateTerminalOff", getGameTime());
            messageTo(self, "terminalOn", null, 3600f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int terminalOn(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "donateTerminalOff");
        return SCRIPT_CONTINUE;
    }
}
