package script.event.auto_invasion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.locations;
import script.library.skill;
import script.library.chat;
import script.library.badge;
import java.util.StringTokenizer;

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
        location here = getLocation(self);
        String myRegion = locations.getGuardSpawnerRegionName(here);
        if (myRegion.equals("@tatooine_region_names:mos_eisley"))
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
