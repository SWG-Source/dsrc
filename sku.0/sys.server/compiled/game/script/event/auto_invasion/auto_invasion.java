package script.event.auto_invasion;

import script.dictionary;
import script.library.locations;
import script.obj_id;

public class auto_invasion extends script.base_script
{
    public auto_invasion()
    {
    }
    public static final String DATATABLE = "datatables/event/invasion/city_data.iff";
    public static final String LEVEL_TABLE = "datatables/event/invasion/level_threshold.iff";
    public static final float THIRTY_SIX_HOURS = 60 * 60 * 36;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (locations.getGuardSpawnerRegionName(getLocation(self)).equals("@tatooine_region_names:mos_eisley"))
        {
            removeObjVar(self, "event");
            removeObjVar(self, "auto_invasion");
            messageTo(self, "repurposeToTuskenRaid", null, 30, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int repurposeToTuskenRaid(obj_id self, dictionary params) throws InterruptedException
    {
        attachScript(self, "event.generic_raid.generic_raid");
        messageTo(self, "detachAutoInvasionScript", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int detachAutoInvasionScript(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "event.auto_invasion.auto_invasion");
        return SCRIPT_CONTINUE;
    }
}
