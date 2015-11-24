package script.event.gcwlaunch;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.chat;
import script.library.ai_lib;
import script.library.locations;

public class gcwlaunch extends script.base_script
{
    public gcwlaunch()
    {
    }
    public static final String SHUTTLE = "object/creature/npc/theme_park/player_shuttle.iff";
    public static final String ESCORT = "wookiee_brawler";
    public static final String CELEB = "trandoshan_slavemaster";
    public static final String STF_FILE = "event/ep3_trando_herald";
    public static final float ONE_HOUR = 60 * 60;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String myRegion = locations.getGuardSpawnerRegionName(here);
        if (myRegion.equals("@naboo_region_names:theed") || myRegion.equals("@tatooine_region_names:bestine") || myRegion.equals("@corellia_region_names:coronet") || myRegion.equals("@tatooine_region_names:anchorhead"))
        {
            removeObjVar(self, "event");
            removeObjVar(self, "auto_invasion");
            messageTo(self, "repurposeToNewGCWEvents", null, 30, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int repurposeToNewGCWEvents(obj_id self, dictionary params) throws InterruptedException
    {
        attachScript(self, "event.gcwraids.gcwraid");
        messageTo(self, "detachGCWLaunchScript", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int detachGCWLaunchScript(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "event.gcwlaunch.gcwlaunch");
        return SCRIPT_CONTINUE;
    }
}
