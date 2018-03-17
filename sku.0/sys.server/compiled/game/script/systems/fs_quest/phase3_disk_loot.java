package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class phase3_disk_loot extends script.base_script
{
    public phase3_disk_loot()
    {
    }
    public static final String FREQUENCY_DISK = "object/tangible/loot/quest/force_sensitive/camp_frequency_datapad.iff";
    public static final String WAYPOINT_DISK = "object/tangible/loot/quest/force_sensitive/camp_waypoint_datapad.iff";
    public static final int PERCENT_CHANCE_OF_LOOT = 20;
    public static final int LIFE_SPAN = 1000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgSelfDestruct", null, (float)LIFE_SPAN, false);
        return SCRIPT_CONTINUE;
    }
    public int msgSelfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "dontDestroy"))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "dontDestroy", 1);
        createLoot(self);
        return SCRIPT_CONTINUE;
    }
    public void createLoot(obj_id self) throws InterruptedException
    {
        if (rand(1, 100) < 100 - PERCENT_CHANCE_OF_LOOT)
        {
            return;
        }
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return;
        }
        String[] loots = new String[]
        {
            FREQUENCY_DISK,
            FREQUENCY_DISK,
            WAYPOINT_DISK
        };
        obj_id item = createObject(loots[rand(0, loots.length - 1)], corpseInventory, "");
        return;
    }
}
