package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.holiday;

public class hidden_content_spawning extends script.base_script
{
    public hidden_content_spawning()
    {
    }
    public static final int RADIUS = 1000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.OnAttach() Initializing spawning functionality for spawner: " + self);
        if (!hasScript(self, holiday.EVENT_TRACKER_SCRIPT))
        {
            attachScript(self, holiday.EVENT_TRACKER_SCRIPT);
        }
        int randomTime = rand(3, 25);
        if (randomTime < 0 || randomTime > 25)
        {
            randomTime = 3;
        }
        CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.OnAttach() Spawning functionality beginning in: " + randomTime + " frames for spawner: " + self);
        messageTo(self, "spawnObject", null, randomTime, false);
        messageTo(self, "destroySelf", null, 6000, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "child"))
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Spawning functionality is initialized for spawner: " + self);
        obj_id parentObject = getObjIdObjVar(self, "objParent");
        if (!isValidId(parentObject))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Master Object does not exist");
            return SCRIPT_CONTINUE;
        }
        int randomSpawnObject = rand(1, 5);
        obj_id object = obj_id.NULL_ID;
        String questName = "";
        if (randomSpawnObject == 1)
        {
            questName = getStringObjVar(self, "strQuestBetsy");
            if (questName == null || questName.length() <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Master Object: " + parentObject + " does not have a valid strQuestBetsy.");
                return SCRIPT_CONTINUE;
            }
            object = create.object("outbreak_carrion_spat_betsy", getLocation(self));
            if (!isValidId(object) || !exists(object))
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() object: " + object + " could not be created! outbreak_carrion_spat_betsy");
                return SCRIPT_CONTINUE;
            }
        }
        else if (randomSpawnObject == 2)
        {
            questName = getStringObjVar(self, "strQuestPerfume");
            if (questName == null || questName.length() <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Master Object: " + parentObject + " does not have a valid strQuestPerfume.");
                return SCRIPT_CONTINUE;
            }
            object = create.object("object/tangible/item/item_outbreak_perfume_hidden_content.iff", getLocation(self));
            if (!isValidId(object) || !exists(object))
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() object: " + object + " could not be created! object/tangible/item/item_outbreak_perfume_hidden_content.iff");
                return SCRIPT_CONTINUE;
            }
        }
        else if (randomSpawnObject == 3)
        {
            questName = getStringObjVar(self, "strQuestStunBaton");
            if (questName == null || questName.length() <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Master Object: " + parentObject + " does not have a valid strQuestStunBaton.");
                return SCRIPT_CONTINUE;
            }
            object = create.object("object/tangible/item/item_outbreak_stun_baton_hidden_content.iff", getLocation(self));
            if (!isValidId(object) || !exists(object))
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() object: " + object + " could not be created! object/tangible/item/item_outbreak_stun_baton_hidden_content.iff");
                return SCRIPT_CONTINUE;
            }
        }
        else if (randomSpawnObject == 4)
        {
            questName = getStringObjVar(self, "strQuestBomberHelmet");
            if (questName == null || questName.length() <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Master Object: " + parentObject + " does not have a valid strQuestBomberHelmet.");
                return SCRIPT_CONTINUE;
            }
            object = create.object("object/tangible/item/item_outbreak_tie_helmet_hidden_content.iff", getLocation(self));
            if (!isValidId(object) || !exists(object))
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() object: " + object + " could not be created! object/tangible/item/item_outbreak_tie_helmet_hidden_content.iff");
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            questName = getStringObjVar(self, "strQuestScoutCamera");
            if (questName == null || questName.length() <= 0)
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Master Object: " + parentObject + " does not have a valid strQuestScoutCamera.");
                return SCRIPT_CONTINUE;
            }
            object = create.object("object/tangible/item/item_outbreak_scout_camera_hidden_content.iff", getLocation(self));
            if (!isValidId(object) || !exists(object))
            {
                CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() object: " + object + " could not be created! object/tangible/item/item_outbreak_scout_camera_hidden_content.iff");
                return SCRIPT_CONTINUE;
            }
        }
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() questName: " + questName + " could not be found. Critical failure. Aborting");
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(object) || !exists(object))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() object: " + object + " could not be found. Critical failure. Aborting.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "hidden_content_spawning.spawnSurvivor() Mob: " + object + " WAS SUCCESSFULLY CREATED for spawner: " + self);
        setObjVar(object, "mySpawner", self);
        setObjVar(object, "parentNode", parentObject);
        setObjVar(object, "objParent", parentObject);
        setObjVar(object, "questName", questName);
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
