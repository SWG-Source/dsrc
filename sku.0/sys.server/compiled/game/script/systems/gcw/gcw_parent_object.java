package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.gcw;

public class gcw_parent_object extends script.base_script
{
    public gcw_parent_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "GCW Parent Object! DO NOT DELETE");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "GCW Parent Object! DO NOT DELETE");
        messageTo(self, "updateGCWData", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int updateGCWData(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "Imperial.controlScore", gcw.getImperialPercentileByRegion(self));
        setObjVar(self, "Rebel.controlScore", gcw.getRebelPercentileByRegion(self));
        messageTo(self, "updateGCWData", null, gcw.GCW_UPDATE_PULSE + rand(1, 100), false);
        return SCRIPT_CONTINUE;
    }
    public int updateGCWScore(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "Imperial.controlScore", gcw.getImperialPercentileByRegion(self));
        setObjVar(self, "Rebel.controlScore", gcw.getRebelPercentileByRegion(self));
        return SCRIPT_CONTINUE;
    }
}
