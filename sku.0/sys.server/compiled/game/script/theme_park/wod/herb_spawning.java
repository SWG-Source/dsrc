package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class herb_spawning extends script.base_script
{
    public herb_spawning()
    {
    }
    public static final String[] HERBS = 
    {
        "object/tangible/furniture/wod_themepark/wod_themepark_herb_01.iff",
        "object/tangible/furniture/wod_themepark/wod_themepark_herb_02.iff",
        "object/tangible/furniture/wod_themepark/wod_themepark_herb_03.iff",
        "object/tangible/furniture/wod_themepark/wod_themepark_herb_04.iff",
        "object/tangible/furniture/wod_themepark/wod_themepark_herb_05.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("wod_themepark", "herb_spawning.OnAttach() Initializing spawning functionality for spawner: " + self);
        messageTo(self, "spawnObject", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "child"))
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("wod_themepark", "herb_spawning.spawnObject() Spawning functionality is initialized for spawner: " + self);
        obj_id parentObject = getObjIdObjVar(self, "objParent");
        if (!isValidId(parentObject))
        {
            CustomerServiceLog("wod_themepark", "herb_spawning.spawnObject() Master Object does not exist");
            return SCRIPT_CONTINUE;
        }
        int randomSpawnObject = rand(0, 4);
        if (randomSpawnObject < 0)
        {
            randomSpawnObject = 0;
        }
        obj_id object = obj_id.NULL_ID;
        String signalName = "";
        String taskName = "";
        switch (randomSpawnObject)
        {
            case 0:
            object = create.object(HERBS[randomSpawnObject], getLocation(self));
            signalName = "rinorLeavesFound";
            taskName = "rinorLeaves";
            break;
            case 1:
            object = create.object(HERBS[randomSpawnObject], getLocation(self));
            signalName = "redWeedLeavesFound";
            taskName = "redWeedLeaves";
            break;
            case 2:
            object = create.object(HERBS[randomSpawnObject], getLocation(self));
            signalName = "greyBushLeavesFound";
            taskName = "greyBushLeaves";
            break;
            case 3:
            object = create.object(HERBS[randomSpawnObject], getLocation(self));
            signalName = "ongmuelPlantLeavesFound";
            taskName = "ongmuelPlantLeaves";
            break;
            case 4:
            object = create.object(HERBS[randomSpawnObject], getLocation(self));
            signalName = "lessetPlantLeavesFound";
            taskName = "lessetPlantLeaves";
            break;
            default:
            object = create.object(HERBS[0], getLocation(self));
            break;
        }
        if (!isValidId(object) || !exists(object))
        {
            CustomerServiceLog("wod_themepark", "herb_spawning.spawnObject() object: " + object + " could not be found. Critical failure. Aborting.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("wod_themepark", "herb_spawning.spawnObject() Mob: " + object + " WAS SUCCESSFULLY CREATED for spawner: " + self);
        setObjVar(object, "mySpawner", self);
        setObjVar(object, "parentNode", parentObject);
        setObjVar(object, "objParent", parentObject);
        setObjVar(object, "signalName", signalName);
        setObjVar(object, "taskName", taskName);
        setObjVar(self, "child", object);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "child"))
        {
            obj_id child = getObjIdObjVar(self, "child");
            if (isValidId(child) && exists(child))
            {
                destroyObject(child);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
