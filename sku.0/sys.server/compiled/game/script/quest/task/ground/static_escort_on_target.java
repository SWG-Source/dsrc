package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class static_escort_on_target extends script.base_script
{
    public static_escort_on_target()
    {
    }
    public static final String dataTableColumnPathToPointPre = "PATH_TO_POINT_POINT_";
    public static final String dataTableColumnLocationXPre = "LOCATION_X_POINT_";
    public static final String dataTableColumnLocationYPre = "LOCATION_Y_POINT_";
    public static final String dataTableColumnLocationZPre = "LOCATION_Z_POINT_";
    public static final String objvarQuest = "quest.static_escort";
    public static final String objvarOnCreatureOwner = "quest.static_escort.owner";
    public static final String objvarOnCreatureQuestCrc = "quest.static_escort.questCrc";
    public static final String objvarOnCreatureTaskId = "quest.static_escort.taskId";
    public static final String objvarPathToPoint = "quest.static_escort.pathToPoint";
    public static final String objvarStartingX = "quest.static_escort.startingX";
    public static final String objvarStartingY = "quest.static_escort.startingY";
    public static final String objvarStartingZ = "quest.static_escort.startingZ";
    public static final String objvarGoingHome = "quest.static_escort.goingHome";
    public static final String objvarOnEscort = "quest.static_escort.onEscort";
    public static final String taskType = "static_escort_on_target";
    public static final String scriptNameEscortOnTarget = "quest.task.ground.static_escort_on_target";
    public int messageStartStaticEscort(obj_id self, dictionary params) throws InterruptedException
    {
        location l = getLocation(self);
        setObjVar(self, objvarStartingX, l.x);
        setObjVar(self, objvarStartingY, l.y);
        setObjVar(self, objvarStartingZ, l.z);
        setObjVar(self, objvarOnEscort, "true");
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        if (hasObjVar(self, objvarOnCreatureOwner) && hasObjVar(self, objvarOnCreatureQuestCrc) && hasObjVar(self, objvarOnCreatureTaskId))
        {
            obj_id player = getObjIdObjVar(self, objvarOnCreatureOwner);
            int questCrc = getIntObjVar(self, objvarOnCreatureQuestCrc);
            int taskId = getIntObjVar(self, objvarOnCreatureTaskId);
            dictionary params = new dictionary();
            params.put("source", self);
            params.put("questCrc", questCrc);
            params.put("taskId", taskId);
            messageTo(player, "messageEscortTaskCreatureDied", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        String goingHome = getStringObjVar(self, objvarGoingHome);
        if (goingHome != null && goingHome.equals("true"))
        {
            removeObjVar(self, objvarQuest);
            detachScript(self, scriptNameEscortOnTarget);
        }
        else 
        {
            obj_id player = getObjIdObjVar(self, objvarOnCreatureOwner);
            int questCrc = getIntObjVar(self, objvarOnCreatureQuestCrc);
            int taskId = getIntObjVar(self, objvarOnCreatureTaskId);
            int reachedPathPoint = getIntObjVar(self, objvarPathToPoint);
            int nextPathPoint = reachedPathPoint + 1;
            int useNextPoint = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnPathToPointPre + nextPathPoint);
            if (useNextPoint == 0)
            {
                groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "OnMovePathComplete", "The next escort point is flagged as unused, so completing the task");
                questCompleteTask(questCrc, taskId, player);
            }
            else 
            {
                String xLoc = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationXPre + nextPathPoint);
                String yLoc = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationYPre + nextPathPoint);
                String zLoc = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationZPre + nextPathPoint);
                location escortPoint = new location(utils.stringToFloat(xLoc), utils.stringToFloat(yLoc), utils.stringToFloat(zLoc), getCurrentSceneName());
                setObjVar(self, objvarPathToPoint, nextPathPoint);
                pathTo(self, escortPoint);
                groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "OnMovePathComplete", "escort target + " + self + " pathing to new point [" + escortPoint.x + ", " + escortPoint.y + ", " + escortPoint.z + "]");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int messageResetStaticEscortTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, objvarOnCreatureOwner);
        int questCrc = getIntObjVar(self, objvarOnCreatureQuestCrc);
        int taskId = getIntObjVar(self, objvarOnCreatureTaskId);
        float x = getFloatObjVar(self, objvarStartingX);
        float y = getFloatObjVar(self, objvarStartingY);
        float z = getFloatObjVar(self, objvarStartingZ);
        location startingPoint = new location(x, y, z, getCurrentSceneName());
        pathTo(self, startingPoint);
        groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "messageResetStaticEscortTarget", "escort target " + self + " going home to [" + startingPoint.x + ", " + startingPoint.y + ", " + startingPoint.z + "]");
        removeObjVar(self, objvarQuest);
        setObjVar(self, objvarGoingHome, "true");
        return SCRIPT_CONTINUE;
    }
}
