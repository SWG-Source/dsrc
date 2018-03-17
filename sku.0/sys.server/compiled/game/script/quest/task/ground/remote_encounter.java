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
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.prose;
import script.library.utils;

public class remote_encounter extends script.quest.task.ground.base_task
{
    public remote_encounter()
    {
    }
    public static final String taskType = "remote_encounter";
    public static final String dataTableColumnCreatureName = "CREATURE_NAME";
    public static final String dataTableColumnEncounterSceneName = "ENCOUNTER_SCENE_NAME";
    public static final String dataTableColumnMinDifficulty = "MIN_DIFFICULTY";
    public static final String dataTableColumnMaxDifficulty = "MAX_DIFFICULTY";
    public static final String dataTableColumnInSpace = "IN_SPACE";
    public static final String dataTableColumnEncounterDuration = "ENCOUNTER_DURATION";
    public static final String dataTableColumnVisible = "IS_VISIBLE";
    public static final String dataTableColumnEncounterWhereMessage = "ENCOUNTER_WHERE_MESSAGE";
    public static final String dataTableColumnItemUsedSystemMessage = "ITEM_USED_SYSTEM_MESSAGE";
    public static final String dataTableColumnAttachScript = "ATTACH_SCRIPT";
    public static final String remoteEncounterScriptName = "quest.task.ground.remote_encounter";
    public static final String MISSION_CRITICAL_ADD_OBJECTS = "addObjects";
    public static final String MISSION_CRITICAL_REMOVE_OBJECTS = "removeObjects";
    public static final String QUEST_CRC = "questCrc";
    public static final String TASK_ID = "taskId";
    public static final String REMOTE_OBJECT_ID = "remoteObjectId";
    public static final String MESSAGE_SEND_QUEST_SYSTEM_MESSAGE = "messageSendQuestSystemMessage";
    public static final String MESSAGE_UPDATE_MISSION_CIRTICAL_OBJECTS = "messageUpdateMissionCriticalObjects";
    public static final String REMOTE_ENCOUNTER_CLEANUP = "remoteEncounterCleanup";
    public static final String REMOTE_ENCOUNTER_CREATURE_DIED = "remoteEncounterCreatureDied";
    public static final String REMOTE_ENCOUNTER_CREATURE_ESCAPED = "remoteEncounterCreatureEscaped";
    public static final String REMOTE_ENCOUNTER_ESCAPE = "remoteEncounterEscape";
    public static final String REMOTE_ENCOUNTER_FAILSAFE_TIMEOUT = "remoteEncounterFailsafeTimeout";
    public static final String REMOTE_ENCOUNTER_SIGNALED = "remoteEncounterSignaled";
    public static final String dataTableSceneDifficulty = "datatables/quest/ground/scene_difficulty.iff";
    public static final int FAILSAFE_TIMEOUT = 5 * 60;
    public static final String REMOTE_ENCOUNTER_CREATURE_SCRIPT = "quest.task.ground.util.remote_encounter_creature";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        int requestId = remoteCreate();
        setQuestCrcScriptVar(self, requestId, questCrc);
        setTaskIdScriptVar(self, requestId, taskId);
        dictionary params = new dictionary();
        params.put(QUEST_CRC, questCrc);
        params.put(TASK_ID, taskId);
        int encounterDuration = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnEncounterDuration);
        messageTo(self, REMOTE_ENCOUNTER_FAILSAFE_TIMEOUT, params, encounterDuration + FAILSAFE_TIMEOUT, false);
        int isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible);
        if (isVisible != 0)
        {
            float playerPlayedTimeWhenTimerEnds = (float)getPlayerPlayedTime(self) + encounterDuration;
            questSetQuestTaskTimer(self, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", (int)playerPlayedTimeWhenTimerEnds);
        }
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + " task failed.");
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int remoteEncounterFailsafeTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        log("remoteEncounterFailsafeTimeout");
        int questCrc = params.getInt(QUEST_CRC);
        int taskId = params.getInt(TASK_ID);
        if (questIsTaskActive(questCrc, taskId, self))
        {
            logError("Failsafe timeout caused quest to fail!");
            questFailTask(questCrc, taskId, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int questItemUsed(obj_id self, dictionary params) throws InterruptedException
    {
        log("questItemUsed");
        int questCrc = params.getInt(QUEST_CRC);
        int taskId = params.getInt(TASK_ID);
        if (questIsTaskActive(questCrc, taskId, self) && isRemoteEncounter(questCrc, taskId))
        {
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "questItemUsed", "Quest item used. Sending message to remote object.");
            messageToRemote(self, questCrc, taskId, REMOTE_ENCOUNTER_SIGNALED);
            int isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible);
            if (isVisible != 0)
            {
                String itemUsedSystemMessage = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnItemUsedSystemMessage);
                string_id itemUsedSystemMessageId = utils.unpackString(itemUsedSystemMessage);
                sendQuestSystemMessage(self, itemUsedSystemMessageId);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int createRemoteObjectResponse(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt(QUEST_CRC);
        int taskId = params.getInt(TASK_ID);
        if (questIsTaskActive(questCrc, taskId, self))
        {
            obj_id remoteObject = params.getObjId(REMOTE_OBJECT);
            if (isValidId(remoteObject))
            {
                setRemoteObjectScriptVar(self, questCrc, taskId, remoteObject);
                setRemoteObjectAsMissionCriticalToGroup(self, remoteObject);
                String sceneName = params.getString(SCENE_NAME);
                float x = params.getFloat(X);
                float y = params.getFloat(Y);
                float z = params.getFloat(Z);
                groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "createRemoteObjectResponse", "Remote Object created in " + sceneName + " at [" + x + "," + y + "," + z + "]");
                sendEncounterWhereMessageToGroup(self, questCrc, taskId, sceneName);
            }
            else 
            {
                questFailTask(questCrc, taskId, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int remoteEncounterCreatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt(QUEST_CRC);
        int taskId = params.getInt(TASK_ID);
        if (questIsTaskActive(questCrc, taskId, self))
        {
            questCompleteTask(questCrc, taskId, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int remoteEncounterCreatureEscaped(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt(QUEST_CRC);
        int taskId = params.getInt(TASK_ID);
        if (questIsTaskActive(questCrc, taskId, self))
        {
            questFailTask(questCrc, taskId, self);
        }
        return SCRIPT_CONTINUE;
    }
    public String getQuestCrcVarName(int id) throws InterruptedException
    {
        return taskType + "." + QUEST_CRC + "." + id;
    }
    public String getTaskIdVarName(int id) throws InterruptedException
    {
        return taskType + "." + TASK_ID + "." + id;
    }
    public String getRemoteObjectVarName(int questCrc, int taskId) throws InterruptedException
    {
        return taskType + "." + REMOTE_OBJECT_ID + "." + questCrc + "." + taskId;
    }
    public int getQuestCrcScriptVar(obj_id self, int id) throws InterruptedException
    {
        return utils.getIntScriptVar(self, getQuestCrcVarName(id));
    }
    public int getTaskIdScriptVar(obj_id self, int id) throws InterruptedException
    {
        return utils.getIntScriptVar(self, getTaskIdVarName(id));
    }
    public obj_id getRemoteObjectScriptVar(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        return utils.getObjIdScriptVar(self, getRemoteObjectVarName(questCrc, taskId));
    }
    public void setQuestCrcScriptVar(obj_id self, int id, int questCrc) throws InterruptedException
    {
        utils.setScriptVar(self, getQuestCrcVarName(id), questCrc);
    }
    public void setTaskIdScriptVar(obj_id self, int id, int taskId) throws InterruptedException
    {
        utils.setScriptVar(self, getTaskIdVarName(id), taskId);
    }
    public void setRemoteObjectScriptVar(obj_id self, int questCrc, int taskId, obj_id objectId) throws InterruptedException
    {
        utils.setScriptVar(self, getRemoteObjectVarName(questCrc, taskId), objectId);
    }
    public boolean hasValidScriptVars(obj_id self, int id) throws InterruptedException
    {
        return utils.hasScriptVar(self, getQuestCrcVarName(id)) && utils.hasScriptVar(self, getTaskIdVarName(id));
    }
    public void cleanup(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        messageToRemote(self, questCrc, taskId, REMOTE_ENCOUNTER_CLEANUP);
        obj_id remoteObject = getRemoteObjectScriptVar(self, questCrc, taskId);
        if (isValidId(remoteObject))
        {
            removeRemoteObjectFromMissionCriticalToGroup(self, remoteObject);
        }
        utils.removeScriptVar(self, getRemoteObjectVarName(questCrc, taskId));
        groundquests.clearBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
    }
    public void messageToRemote(obj_id self, int questCrc, int taskId, String message) throws InterruptedException
    {
        obj_id remoteObject = getRemoteObjectScriptVar(self, questCrc, taskId);
        if (isValidId(remoteObject))
        {
            messageTo(remoteObject, message, null, 0, false);
        }
    }
    public dictionary getRemoteCreateParams(obj_id self, int requestId) throws InterruptedException
    {
        dictionary createParams = new dictionary();
        int questCrc = getQuestCrcScriptVar(self, requestId);
        int taskId = getTaskIdScriptVar(self, requestId);
        String creatureName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnCreatureName);
        createParams.put(REQUESTER, self);
        createParams.put(OBJECT_NAME, creatureName);
        createParams.put(USE_RANDOM_LOCATION, true);
        createParams.put(ATTACH_SCRIPTS, REMOTE_ENCOUNTER_CREATURE_SCRIPT);
        createParams.put(QUEST_CRC, questCrc);
        createParams.put(TASK_ID, taskId);
        return createParams;
    }
    public void removeRemoteCreateScriptVars(obj_id self, int requestId) throws InterruptedException
    {
        super.removeRemoteCreateScriptVars(self, requestId);
        utils.removeScriptVar(self, getQuestCrcVarName(requestId));
        utils.removeScriptVar(self, getTaskIdVarName(requestId));
    }
    public boolean hasValidCreateScriptVars(obj_id self, int requestId) throws InterruptedException
    {
        int questCrc = getQuestCrcScriptVar(self, requestId);
        int taskId = getTaskIdScriptVar(self, requestId);
        if (!questIsTaskActive(questCrc, taskId, self))
        {
            return false;
        }
        return hasValidScriptVars(self, requestId);
    }
    public obj_id getRemoteObjectCreator(obj_id self, int requestId, String[] elementNameList, dictionary[] dictionaryList) throws InterruptedException
    {
        int questCrc = getQuestCrcScriptVar(self, requestId);
        int taskId = getTaskIdScriptVar(self, requestId);
        int minDifficulty = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinDifficulty);
        int maxDifficulty = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxDifficulty);
        boolean inSpace = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableColumnInSpace, true);
        String encounterSceneName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnEncounterSceneName);
        if (encounterSceneName != null && encounterSceneName.length() > 0)
        {
            for (int i = 0; i < elementNameList.length; ++i)
            {
                if (encounterSceneName.equals(elementNameList[i]))
                {
                    log("getRemoteObjectCreator - using specific scene: " + encounterSceneName);
                    return dictionaryList[i].getObjId(CREATOR);
                }
            }
        }
        Vector remoteCreatorsSpace = new Vector();
        remoteCreatorsSpace.setSize(0);
        Vector remoteCreatorsSpaceDifficulty = new Vector();
        remoteCreatorsSpaceDifficulty.setSize(0);
        for (int i = 0; i < dictionaryList.length; ++i)
        {
            obj_id creator = dictionaryList[i].getObjId(CREATOR);
            boolean creatorInSpace = dictionaryList[i].getBoolean(IN_SPACE);
            int difficultyRow = dataTableSearchColumnForString(elementNameList[i], "SCENE_NAME", dataTableSceneDifficulty);
            int difficulty = dataTableGetInt(dataTableSceneDifficulty, difficultyRow, "DIFFICULTY");
            if (creatorInSpace == inSpace)
            {
                remoteCreatorsSpace = utils.addElement(remoteCreatorsSpace, creator);
                if (difficulty >= minDifficulty && difficulty <= maxDifficulty)
                {
                    remoteCreatorsSpaceDifficulty = utils.addElement(remoteCreatorsSpaceDifficulty, creator);
                }
            }
        }
        int size = remoteCreatorsSpaceDifficulty.size();
        if (size > 0)
        {
            return ((obj_id)remoteCreatorsSpaceDifficulty.get(rand(0, size - 1)));
        }
        size = remoteCreatorsSpace.size();
        if (size > 0)
        {
            return ((obj_id)remoteCreatorsSpace.get(rand(0, size - 1)));
        }
        logError("Failed to find remote creator!");
        questFailTask(questCrc, taskId, self);
        return null;
    }
    public void setRemoteObjectAsMissionCriticalToGroup(obj_id self, obj_id remoteObject) throws InterruptedException
    {
        dictionary params = new dictionary();
        obj_id[] addObjects = new obj_id[1];
        addObjects[0] = remoteObject;
        params.put(MISSION_CRITICAL_ADD_OBJECTS, addObjects);
        sendMessageToGroup(self, params, MESSAGE_UPDATE_MISSION_CIRTICAL_OBJECTS);
    }
    public void removeRemoteObjectFromMissionCriticalToGroup(obj_id self, obj_id remoteObject) throws InterruptedException
    {
        dictionary params = new dictionary();
        obj_id[] removeObjects = new obj_id[1];
        removeObjects[0] = remoteObject;
        params.put(MISSION_CRITICAL_REMOVE_OBJECTS, removeObjects);
        sendMessageToGroup(self, params, MESSAGE_UPDATE_MISSION_CIRTICAL_OBJECTS);
    }
    public void sendEncounterWhereMessageToGroup(obj_id self, int questCrc, int taskId, String sceneName) throws InterruptedException
    {
        String encounterWhereMessage = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnEncounterWhereMessage);
        string_id encounterWhereMessageId = utils.unpackString(encounterWhereMessage);
        string_id planetId = new string_id("planet_n", sceneName);
        dictionary params = new dictionary();
        params.put("sid", encounterWhereMessageId);
        params.put("to", planetId);
        sendMessageToGroup(self, params, MESSAGE_SEND_QUEST_SYSTEM_MESSAGE);
    }
    public void sendMessageToGroup(obj_id self, dictionary params, String message) throws InterruptedException
    {
        obj_id groupObj = getGroupObject(self);
        if (isIdValid(groupObj))
        {
            obj_id[] groupMembers = getGroupMemberIds(groupObj);
            int numGroupMembers = groupMembers.length;
            for (int i = 0; i < numGroupMembers; i++)
            {
                obj_id groupMember = groupMembers[i];
                if (isIdValid(groupMember))
                {
                    messageTo(groupMember, message, params, 0, false);
                }
            }
        }
        else 
        {
            messageTo(self, message, params, 0, false);
        }
    }
    public boolean isRemoteEncounter(int questCrc, int taskId) throws InterruptedException
    {
        return (groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnAttachScript)).equals(remoteEncounterScriptName);
    }
}
