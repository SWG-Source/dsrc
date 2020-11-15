package script.fishing;

import script.dictionary;
import script.library.fishing;
import script.location;
import script.obj_id;

public class controller extends script.base_script {

    public controller()
    {
    }

    /*
    * The purpose of the controller is really only to spawn and setup the master object
    * Everything fishing related that is stored or updated should be going in the master object via messageTo fishing.getMasterFishingObject()
     */

    public static final String CONTROLLER = "object/tangible/ground_spawning/patrol_waypoint.iff";

    public int OnInitialize(obj_id self) throws InterruptedException {
        setName(self, "Fishing Controller");
        location selfLoc = getLocation(self);
        obj_id objects[] = getObjectsInRange(selfLoc, 0.1f);
        boolean exists = false;
        if (objects != null || objects.length > 0)
        {
            for (obj_id object : objects) {
                if ((getTemplateName(object)).equals(CONTROLLER)) {
                    exists = true;
                }
            }
        }
        if (!exists)
        {
            createMasterObject(self);
        }
        return SCRIPT_CONTINUE;
    }

    // Make sure CSRs can't take the master object on a stroll down Amidala's Beach when they should be home doing their homework
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException {
        sendSystemMessageTestingOnly(transferer, "You cannot move this item!");
        return SCRIPT_OVERRIDE;
    }

    public int createMasterObject(obj_id self, dictionary params) throws InterruptedException {
        createMasterObject(self);
        return SCRIPT_CONTINUE;
    }

    /**
     * createMasterObject
     * Spawns the fishing master object and persists it to the database
     * We need to persist this object because it contains records of fishing (e.g. leaderboard)
     * and should not be deleted at restart (that would be very bad)
     */
    public void createMasterObject(obj_id self) throws InterruptedException {
        obj_id object = createObject(CONTROLLER, getLocation(self));
        attachScript(object, "fishing.master_object");
        persistObject(object);
    }

}
