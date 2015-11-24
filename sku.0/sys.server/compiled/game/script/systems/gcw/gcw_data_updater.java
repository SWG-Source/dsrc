package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.gcw;

public class gcw_data_updater extends script.base_script
{
    public gcw_data_updater()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "updateGCWInfo", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "updateGCWInfo", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int updateGCWInfo(obj_id self, dictionary params) throws InterruptedException
    {
        int imperial = gcw.getImperialPercentileByRegion(self);
        int rebel = gcw.getRebelPercentileByRegion(self);
        int oldImperial = getIntObjVar(self, "Imperial.controlScore");
        if ((oldImperial < 50 && imperial > 50) || (oldImperial > 50 && imperial < 50))
        {
            messageTo(self, "destroyChildren", null, 1.0f, false);
        }
        setObjVar(self, "Imperial.controlScore", imperial);
        setObjVar(self, "Rebel.controlScore", rebel);
        messageTo(self, "updateGCWInfo", null, gcw.GCW_UPDATE_PULSE + rand(1, 100), false);
        return SCRIPT_CONTINUE;
    }
}
