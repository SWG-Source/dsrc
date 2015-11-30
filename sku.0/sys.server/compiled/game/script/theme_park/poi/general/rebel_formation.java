package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class rebel_formation extends script.theme_park.poi.base
{
    public rebel_formation()
    {
    }
    public static final int FORMATION_COLUMN = 0;
    public static final int FORMATION_WEDGE = 1;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        launchSetup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        launchSetup(self);
        return SCRIPT_CONTINUE;
    }
    public void launchSetup(obj_id poiBaseObject) throws InterruptedException
    {
        obj_id leader = poiCreateNpc("rebel", 0, 0);
        dictionary parms = new dictionary();
        parms.put("leader", leader);
        for (int i = 1; i < 7; i++)
        {
            parms.put("pos", i);
            messageTo(poiBaseObject, "makeAnother", parms, (5 + (i * 2)), true);
        }
    }
    public int makeAnother(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id leader = params.getObjId("leader");
        int pos = params.getInt("pos");
        obj_id trooper = poiCreateNpc("rebel", 0, 0);
        ai_lib.followInFormation(trooper, leader, FORMATION_WEDGE, pos);
        return SCRIPT_CONTINUE;
    }
}
