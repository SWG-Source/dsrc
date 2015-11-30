package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class stormtrooper_formation extends script.theme_park.poi.base
{
    public stormtrooper_formation()
    {
    }
    public static final int FORMATION_COLUMN = 0;
    public static final int FORMATION_WEDGE = 1;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        launchPoi(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        launchPoi(self);
        return SCRIPT_CONTINUE;
    }
    public void launchPoi(obj_id poiBaseObject) throws InterruptedException
    {
        location myLoc = new location(getLocation(poiBaseObject));
        obj_id leader = poiCreateNpc("stormtrooper", 0, 0);
        dictionary parms = new dictionary();
        parms.put("leader", leader);
        for (int i = 1; i < 8; i++)
        {
            parms.put("pos", i);
            messageTo(poiBaseObject, "makeAnother", parms, (5 + (i * 2)), true);
        }
    }
    public int makeAnother(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id leader = params.getObjId("leader");
        int pos = params.getInt("pos");
        location myLoc = new location(getLocation(self));
        obj_id trooper = poiCreateNpc("stormtrooper", 0, 0);
        ai_lib.followInFormation(trooper, leader, FORMATION_COLUMN, pos);
        return SCRIPT_CONTINUE;
    }
}
