package script.base;

import script.*;
import script.library.create;
import script.library.space_create;
import script.library.space_quest;
import script.library.utils;

public class remote_object_creator extends script.base.remote_object
{
    public remote_object_creator()
    {
    }
    public void registerForRemoteObjectCreation(obj_id self) throws InterruptedException
    {
        log("registerForRemoteObjectCreation: setting cluster wide data.");
        dictionary objectData = new dictionary();
        objectData.put(CREATOR, self);
        objectData.put(IN_SPACE, isSpaceScene());
        replaceClusterWideData(REMOTE_OBJECT_MANAGER, getCurrentSceneName(), objectData, false, 0);
    }
    public int createRemoteObject(obj_id self, dictionary params) throws InterruptedException
    {
        log("createRemoteObject: message received.");
        log("createRemoteObject: " + params.toString());
        obj_id requester = params.getObjId(REQUESTER);
        String objectName = params.getString(OBJECT_NAME);
        boolean useRandomLocation = params.getBoolean(USE_RANDOM_LOCATION);
        float x, y, z;
        if (!useRandomLocation)
        {
            x = params.getFloat(X);
            y = params.getFloat(Y);
            z = params.getFloat(Z);
        }
        else 
        {
            x = y = z = 0;
        }
        obj_id created;
        if (isSpaceScene())
        {
            if (!useRandomLocation)
            {
                created = space_create.createShip(objectName, transform.identity.setPosition_p(x, y, z));
            }
            else 
            {
                transform randomTransform = space_quest.getRandomPositionInSphere(transform.identity, 512, 2048);
                vector pos = randomTransform.getPosition_p();
                params.put(X, pos.x);
                params.put(Y, pos.y);
                params.put(Z, pos.z);
                created = space_create.createShipHyperspace(objectName, randomTransform);
            }
        }
        else 
        {
            created = create.object(objectName, new location(x, y, z));
        }
        params.put(SCENE_NAME, getCurrentSceneName());
        if (isValidId(created))
        {
            log("createRemoteObject: creation successful. [" + objectName + "][" + created + "]");
            utils.setScriptVar(created, REQUESTER, requester);
            utils.setScriptVar(created, CREATE_PARAMS, params);
            if (params.containsKey(ATTACH_SCRIPTS))
            {
                String[] scripts = split(params.getString(ATTACH_SCRIPTS), ':');
                for (String script : scripts) {
                    if (!hasScript(created, script)) {
                        attachScript(created, script);
                    }
                }
            }
        }
        else 
        {
            logError("createRemoteObject: creation failed.");
        }
        params.put(REMOTE_OBJECT, created);
        messageTo(requester, CREATE_REMOTE_OBJECT_RESPONSE, params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        registerForRemoteObjectCreation(self);
        return SCRIPT_CONTINUE;
    }
}
