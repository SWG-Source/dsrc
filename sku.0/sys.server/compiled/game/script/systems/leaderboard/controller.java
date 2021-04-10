package script.systems.leaderboard;

import script.dictionary;
import script.location;
import script.obj_id;

public class controller extends script.base_script {

    public controller()
    {
    }

    /**
     * Controller which handles spawning the master object if it does not exist.
     * Should only run the first time the server is started after a fresh installation.
     */
    public static final String CONTROLLER = "object/tangible/ground_spawning/patrol_waypoint.iff";

    public int OnInitialize(obj_id self) throws InterruptedException {
        setName(self, "Leaderboard Controller");
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

    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException {
        sendSystemMessageTestingOnly(transferer, "You cannot move this item!");
        return SCRIPT_OVERRIDE;
    }

    public int createMasterObject(obj_id self, dictionary params) throws InterruptedException {
        createMasterObject(self);
        return SCRIPT_CONTINUE;
    }

    public void createMasterObject(obj_id self) throws InterruptedException {
        obj_id object = createObject(CONTROLLER, getLocation(self));
        attachScript(object, "systems.leaderboard.gcw_master_object");
        persistObject(object);
        WARNING("Creating Master Leaderboard Object ("+object+")...");
    }

}
