package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.quests;
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.prose;
import script.library.utils;

public class static_escort extends script.quest.task.ground.base_task
{
    public static_escort()
    {
    }
    public static final String dataTableColumnServerTemplate = "SERVER_TEMPLATE";
    public static final String dataTableColumnMovementType = "MOVEMENT_TYPE";
    public static final String dataTableColumnMaxAllowedDistance = "MAX_ALLOWED_ESCORT_DISTANCE";
    public static final String dataTableColumnPathToPointPre = "PATH_TO_POINT_POINT_";
    public static final String dataTableColumnLocationXPre = "LOCATION_X_POINT_";
    public static final String dataTableColumnLocationYPre = "LOCATION_Y_POINT_";
    public static final String dataTableColumnLocationZPre = "LOCATION_Z_POINT_";
    public static final String objvarEscortTarget = "target";
    public static final String objvarBeenWarnedDistance = "beenWarned";
    public static final String scriptNameEscortOnTarget = "quest.task.ground.static_escort_on_target";
    public static final String objvarOnCreatureOwner = "quest.static_escort.owner";
    public static final String objvarOnCreatureQuestCrc = "quest.static_escort.questCrc";
    public static final String objvarOnCreatureTaskId = "quest.static_escort.taskId";
    public static final String objvarPathToPoint = "quest.static_escort.pathToPoint";
    public static final String taskType = "static_escort";
    public static final String dot = ".";
    public static final float escortDistanceTimeCheck = 10;
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        String serverTemplate = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnServerTemplate);
        String movementType = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMovementType);
        int pointIndex = 1;
        obj_id escortTarget = groundquests.getPendingStaticEscortTarget(self);
        groundquests.clearPendingStaticEscortTarget(self);
        if (escortTarget == null)
        {
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Could not get static escort target for this player from objvar, did you call groundquests.setPendingStaticEscortTarget(player, escortTarget) first?");
            questCompleteTask(questCrc, taskId, self);
        }
        attachScript(escortTarget, scriptNameEscortOnTarget);
        dictionary params = new dictionary();
        messageTo(escortTarget, "messageStartStaticEscort", params, 0, false);
        detachScript(escortTarget, "ai.ai");
        setObjVar(escortTarget, objvarOnCreatureOwner, self);
        setObjVar(escortTarget, objvarOnCreatureQuestCrc, questCrc);
        setObjVar(escortTarget, objvarOnCreatureTaskId, taskId);
        setObjVar(escortTarget, objvarPathToPoint, pointIndex);
        setObjVar(self, baseObjVar + dot + objvarEscortTarget, escortTarget);
        int useFirstPoint = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnPathToPointPre + pointIndex);
        if (useFirstPoint == 0)
        {
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "The first escort point is flagged as unused, so autocomplete the task");
            questCompleteTask(questCrc, taskId, self);
        }
        else 
        {
            String xLoc = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationXPre + pointIndex);
            String yLoc = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationYPre + pointIndex);
            String zLoc = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationZPre + pointIndex);
            location escortPoint = new location(utils.stringToFloat(xLoc), utils.stringToFloat(yLoc), utils.stringToFloat(zLoc), getCurrentSceneName());
            pathTo(escortTarget, escortPoint);
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "pathing to new point " + escortPoint.x + ", " + escortPoint.y + ", " + escortPoint.z);
        }
        dictionary distanceCheckParams = new dictionary();
        distanceCheckParams.put("player", self);
        distanceCheckParams.put("escortTarget", escortTarget);
        distanceCheckParams.put("questCrc", questCrc);
        distanceCheckParams.put("taskId", taskId);
        messageTo(self, "messageStaticEscortCheckDistance", distanceCheckParams, escortDistanceTimeCheck, false);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int messageStaticEscortCheckDistance(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id escortTarget = params.getObjId("escortTarget");
        int questCrc = params.getInt("questCrc");
        int taskId = params.getInt("taskId");
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        if (isEscortNearby(questCrc, taskId, player, escortTarget))
        {
            if (hasObjVar(player, baseObjVar + objvarBeenWarnedDistance))
            {
                removeObjVar(player, baseObjVar + objvarBeenWarnedDistance);
            }
        }
        else 
        {
            failEscortDistanceCheck(player, questCrc, taskId, escortTarget);
        }
        messageTo(self, "messageStaticEscortCheckDistance", params, escortDistanceTimeCheck, false);
        return SCRIPT_CONTINUE;
    }
    public int messageEscortTaskCreatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadCreature = params.getObjId("source");
        int questCrc = params.getInt("questCrc");
        int taskId = params.getInt("taskId");
        String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        obj_id escortTarget = getObjIdObjVar(self, baseObjVar + dot + objvarEscortTarget);
        if (escortTarget == deadCreature)
        {
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "messageEscortTaskCreatureDied", "[" + deadCreature + "] being tracked for a static_escort has died.");
            questFailTask(questCrc, taskId, self);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isEscortNearby(int questCrc, int taskId, obj_id player, obj_id escortTarget) throws InterruptedException
    {
        boolean result = false;
        if (isIdValid(player) && isIdValid(escortTarget))
        {
            int maxEscortDistance = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxAllowedDistance);
            result = getDistance(player, escortTarget) <= maxEscortDistance;
        }
        return result;
    }
    public void failEscortDistanceCheck(obj_id player, int questCrc, int taskId, obj_id escortTarget) throws InterruptedException
    {
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        if (!hasObjVar(player, baseObjVar + objvarBeenWarnedDistance))
        {
            setObjVar(player, baseObjVar + objvarBeenWarnedDistance, "true");
            string_id message = new string_id("quest/groundquests", "static_escort_distance_warning");
            prose_package pp = prose.getPackage(message, player, player, 0);
            prose.setDF(pp, escortDistanceTimeCheck);
            sendSystemMessageProse(player, pp);
        }
        else 
        {
            string_id message = new string_id("quest/groundquests", "static_escort_failed_due_to_distance");
            prose_package pp = prose.getPackage(message, player, player, 0);
            sendSystemMessageProse(player, pp);
            questFailTask(questCrc, taskId, player);
        }
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        cleanup(self, questCrc, taskId);
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + " task failed.");
        cleanup(self, questCrc, taskId);
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        cleanup(self, questCrc, taskId);
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        String escortTargetObjVarName = baseObjVar + dot + objvarEscortTarget;
        obj_id escortTarget = getObjIdObjVar(player, escortTargetObjVarName);
        if (escortTarget != null)
        {
            dictionary params = new dictionary();
            messageTo(escortTarget, "messageResetStaticEscortTarget", params, 0, false);
        }
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
}
