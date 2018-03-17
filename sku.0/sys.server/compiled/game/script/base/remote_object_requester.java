package script.base;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class remote_object_requester extends script.base.remote_object
{
    public remote_object_requester()
    {
    }
    public int OnClusterWideDataResponse(obj_id self, String managerName, String elementNameExp, int requestId, String[] elementNameList, dictionary[] dictionaryList, int lockKey) throws InterruptedException
    {
        if (logsEnabled())
        {
            log("OnClusterWideDataResponse() manager name (" + managerName + ") element name (" + elementNameExp + ") request id (" + requestId + ") match count (" + elementNameList.length + ") lock key (" + lockKey + ")");
            for (int i = 0; i < elementNameList.length; ++i)
            {
                log("element " + (i + 1) + " is (" + elementNameList[i] + ")");
                log("dictionary " + dictionaryList[i].toString());
            }
        }
        if (managerName.equals(REMOTE_OBJECT_MANAGER))
        {
            if (dictionaryList.length < 1)
            {
                logError("No object available for remote creation. [" + elementNameExp + "]");
            }
            else if (!hasValidCreateScriptVars(self, requestId))
            {
                logError("Missing script vars for this request! [" + requestId + "]");
            }
            else if (utils.hasScriptVar(self, getSceneNameVarName(requestId)) && !elementNameExp.equals(getSceneName(self, requestId)))
            {
                logError("Wrong scene! [" + elementNameExp + "][" + getSceneName(self, requestId) + "]");
            }
            else 
            {
                obj_id remoteObjectCreator = getRemoteObjectCreator(self, requestId, elementNameList, dictionaryList);
                if (isValidId(remoteObjectCreator))
                {
                    messageTo(remoteObjectCreator, CREATE_REMOTE_OBJECT, getRemoteCreateParams(self, requestId), 0.0f, false);
                    log("Remote object creation request sent!");
                }
                else 
                {
                    logError("No valid remote object creator found!");
                }
            }
            removeRemoteCreateScriptVars(self, requestId);
        }
        return SCRIPT_CONTINUE;
    }
    public int createRemoteObjectResponse(obj_id self, dictionary params) throws InterruptedException
    {
        log("createRemoteObjectResponse: " + params.toString());
        obj_id remoteObject = params.getObjId(REMOTE_OBJECT);
        if (isValidId(remoteObject))
        {
            log("Successfully created " + remoteObject);
        }
        else 
        {
            logError("Creation failed!");
        }
        return SCRIPT_CONTINUE;
    }
    public void remoteCreate(String sceneName, String objectName, float x, float y, float z) throws InterruptedException
    {
        obj_id self = getSelf();
        int requestId = getClusterWideData(REMOTE_OBJECT_MANAGER, sceneName, false, self);
        utils.setScriptVar(self, getSceneNameVarName(requestId), sceneName);
        utils.setScriptVar(self, getObjectNameVarName(requestId), objectName);
        utils.setScriptVar(self, getUseRandomLocationVarName(requestId), false);
        utils.setScriptVar(self, getXVarName(requestId), x);
        utils.setScriptVar(self, getYVarName(requestId), y);
        utils.setScriptVar(self, getZVarName(requestId), z);
    }
    public void remoteCreate(String sceneName, String objectName) throws InterruptedException
    {
        obj_id self = getSelf();
        int requestId = getClusterWideData(REMOTE_OBJECT_MANAGER, sceneName, false, self);
        utils.setScriptVar(self, getSceneNameVarName(requestId), sceneName);
        utils.setScriptVar(self, getObjectNameVarName(requestId), objectName);
        utils.setScriptVar(self, getUseRandomLocationVarName(requestId), true);
    }
    public int remoteCreate() throws InterruptedException
    {
        obj_id self = getSelf();
        return getClusterWideData(REMOTE_OBJECT_MANAGER, "*", false, self);
    }
    public String getSceneNameVarName(int requestId) throws InterruptedException
    {
        return SCENE_NAME + "." + requestId;
    }
    public String getSceneName(obj_id self, int requestId) throws InterruptedException
    {
        return utils.getStringScriptVar(self, getSceneNameVarName(requestId));
    }
    public String getObjectNameVarName(int requestId) throws InterruptedException
    {
        return OBJECT_NAME + "." + requestId;
    }
    public String getObjectName(obj_id self, int requestId) throws InterruptedException
    {
        return utils.getStringScriptVar(self, getObjectNameVarName(requestId));
    }
    public String getXVarName(int requestId) throws InterruptedException
    {
        return X + "." + requestId;
    }
    public float getX(obj_id self, int requestId) throws InterruptedException
    {
        return utils.getFloatScriptVar(self, getXVarName(requestId));
    }
    public String getYVarName(int requestId) throws InterruptedException
    {
        return Y + "." + requestId;
    }
    public float getY(obj_id self, int requestId) throws InterruptedException
    {
        return utils.getFloatScriptVar(self, getYVarName(requestId));
    }
    public String getZVarName(int requestId) throws InterruptedException
    {
        return Z + "." + requestId;
    }
    public float getZ(obj_id self, int requestId) throws InterruptedException
    {
        return utils.getFloatScriptVar(self, getZVarName(requestId));
    }
    public String getUseRandomLocationVarName(int requestId) throws InterruptedException
    {
        return USE_RANDOM_LOCATION + "." + requestId;
    }
    public boolean getUseRandomLocationVarName(obj_id self, int requestId) throws InterruptedException
    {
        return utils.getBooleanScriptVar(self, getUseRandomLocationVarName(requestId));
    }
    public void removeRemoteCreateScriptVars(obj_id self, int requestId) throws InterruptedException
    {
        utils.removeScriptVar(self, getSceneNameVarName(requestId));
        utils.removeScriptVar(self, getObjectNameVarName(requestId));
        utils.removeScriptVar(self, getXVarName(requestId));
        utils.removeScriptVar(self, getYVarName(requestId));
        utils.removeScriptVar(self, getZVarName(requestId));
    }
    public boolean hasValidCreateScriptVars(obj_id self, int requestId) throws InterruptedException
    {
        return utils.hasScriptVar(self, getSceneNameVarName(requestId)) && utils.hasScriptVar(self, getObjectNameVarName(requestId));
    }
    public dictionary getRemoteCreateParams(obj_id self, int requestId) throws InterruptedException
    {
        dictionary createParams = new dictionary();
        createParams.put(REQUESTER, self);
        createParams.put(SCENE_NAME, getSceneName(self, requestId));
        createParams.put(OBJECT_NAME, getObjectName(self, requestId));
        createParams.put(X, getX(self, requestId));
        createParams.put(Y, getY(self, requestId));
        createParams.put(Z, getZ(self, requestId));
        return createParams;
    }
    public obj_id getRemoteObjectCreator(obj_id self, int requestId, String[] elementNameList, dictionary[] dictionaryList) throws InterruptedException
    {
        int randomIndex = rand(0, dictionaryList.length);
        return dictionaryList[randomIndex].getObjId(CREATOR);
    }
}
