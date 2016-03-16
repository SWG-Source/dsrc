package script.library;

import script.*;

public class poi extends script.base_script
{
    public poi()
    {
    }
    public static final int POI_INCOMPLETE = 0;
    public static final int POI_SUCCESS = 1;
    public static final int POI_FAIL = 2;
    public static final int DIFF_UNKNOWN = -1;
    public static final int DIFF_VEASY = 0;
    public static final int DIFF_EASY = 1;
    public static final int DIFF_MEDIUM = 2;
    public static final int DIFF_HARD = 3;
    public static final int DIFF_VHARD = 4;
    public static final String POI_BASE = "poi";
    public static final String POI_STRING_LIST = "poi.stringList";
    public static final String POI_STRING_LIST_INDEXNAME = "poi.stringListIndex";
    public static final String POI_CREDIT_LIST = "poi.creditForCompletion";
    public static final String POI_DENY_LIST = "poi.deniedCreditForCompletion";
    public static final String POI_DESTROY_MESSAGE = "poi.destroyMessageHandler";
    public static final String POI_DESTROY_MESSAGE_DELAY = "poi.destroyMessageDelay";
    public static final String POI_BASE_OBJECT = "poi.baseObject";
    public static final String POI_NO_MESSAGE_ON_DESTROY = "poi.NoMsgOnDestroy";
    public static final String POI_COMPLETED = "poi.completed";
    public static final String POI_DIFFICULTY = "poi.difficulty";
    public static final String POI_FACTION = "poi.faction";
    public static final String POI_FACTION_VALUE = "poi.factionValue";
    public static final String POI_OBJECTIVE = "poi.objective";
    public static final String POI_OBJECT_SCRIPT = "theme_park.poi.poi_object";
    public static obj_id createObject(obj_id poiObject, String template, float x, float z) throws InterruptedException
    {
        return createObject(poiObject, template, x, z, -1);
    }
    public static obj_id createObject(obj_id poiObject, String template, float x, float z, int level) throws InterruptedException
    {
        PROFILER_START("poi.createObject" + template);
        if (poiObject == null || poiObject == obj_id.NULL_ID)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to create " + template + " because poiObject is NULL!");
            PROFILER_STOP("poi.createObject" + template);
            return null;
        }
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            PROFILER_STOP("poi.createObject" + template);
            return null;
        }
        location loc = new location(getLocation(poiBaseObject));
        loc.x += x;
        loc.z += z;
        obj_id element = create.object(template, loc, level);
        if (element == null)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to create " + template);
            PROFILER_STOP("poi.createObject" + template);
            return null;
        }
        setObjVar(element, POI_BASE_OBJECT, poiBaseObject);
        if (hasScript(element, POI_OBJECT_SCRIPT))
        {
            debugServerConsoleMsg(getSelf(), "WARNING:  " + getName(element) + " already has the POI OBJECT script attached.  Don't.");
        }
        else 
        {
            attachScript(element, POI_OBJECT_SCRIPT);
        }
        PROFILER_STOP("poi.createObject" + template);
        return element;
    }
    public static obj_id createObject(String template, float x, float z) throws InterruptedException
    {
        return createObject(template, x, z, -1);
    }
    public static obj_id createObject(String template, float x, float z, int level) throws InterruptedException
    {
        return createObject(getSelf(), template, x, z, level);
    }
    public static obj_id createObject(obj_id poiObject, String name, String template, float x, float y) throws InterruptedException
    {
        return createObject(poiObject, name, template, x, y, -1);
    }
    public static obj_id createObject(obj_id poiObject, String name, String template, float x, float y, int level) throws InterruptedException
    {
        obj_id element = createObject(poiObject, template, x, y, level);
        addToStringList(element, name);
        return element;
    }
    public static obj_id createObject(String name, String template, float x, float y) throws InterruptedException
    {
        return createObject(name, template, x, y, -1);
    }
    public static obj_id createObject(String name, String template, float x, float y, int level) throws InterruptedException
    {
        return createObject(getSelf(), name, template, x, y, level);
    }
    public static obj_id createObject(obj_id poiObject, String strName, location locCenter) throws InterruptedException
    {
        return createObject(poiObject, strName, locCenter, -1);
    }
    public static obj_id createObject(obj_id poiObject, String strName, location locCenter, int level) throws InterruptedException
    {
        location locHome = getLocation(poiObject);
        return createObject(poiObject, strName, locCenter.x - locHome.x, locCenter.z - locHome.z, level);
    }
    public static obj_id createNpc(obj_id poiObject, String type, float x, float z) throws InterruptedException
    {
        return createNpc(poiObject, type, x, z, -1);
    }
    public static obj_id createNpc(obj_id poiObject, String type, float x, float z, int level) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return null;
        }
        location here = getLocation(poiObject);
        here.x += x;
        here.z += z;
        obj_id poiNpc = create.object(type, here, level);
        if (poiNpc == null)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to create " + type);
            return null;
        }
        setObjVar(poiNpc, POI_BASE_OBJECT, poiBaseObject);
        if (hasScript(poiNpc, POI_OBJECT_SCRIPT))
        {
            debugServerConsoleMsg(getSelf(), "WARNING:  " + getName(poiNpc) + " already has the POI OBJECT script attached.  Don't.");
        }
        else 
        {
            attachScript(poiNpc, POI_OBJECT_SCRIPT);
        }
        return poiNpc;
    }
    public static obj_id createNpc(String type, float x, float z) throws InterruptedException
    {
        return createNpc(type, x, z, -1);
    }
    public static obj_id createNpc(String type, float x, float z, int level) throws InterruptedException
    {
        return createNpc(getSelf(), type, x, z, level);
    }
    public static obj_id createNpc(obj_id poiObject, String ident, String type, float x, float z) throws InterruptedException
    {
        return createNpc(poiObject, ident, type, x, z, -1);
    }
    public static obj_id createNpc(obj_id poiObject, String ident, String type, float x, float z, int level) throws InterruptedException
    {
        obj_id poiNpc = createNpc(poiObject, type, x, z, level);
        addToStringList(poiNpc, ident);
        return poiNpc;
    }
    public static obj_id createNpc(obj_id poiObject, String ident, String type, location loc) throws InterruptedException
    {
        return createNpc(poiObject, ident, type, loc, -1);
    }
    public static obj_id createNpc(obj_id poiObject, String ident, String type, location loc, int level) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return null;
        }
        obj_id poiNpc = create.object(type, loc, level);
        if (poiNpc == null)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to create " + type);
            return null;
        }
        setObjVar(poiNpc, POI_BASE_OBJECT, poiBaseObject);
        if (hasScript(poiNpc, POI_OBJECT_SCRIPT))
        {
            debugServerConsoleMsg(getSelf(), "WARNING:  " + getName(poiNpc) + " already has the POI OBJECT script attached.  Don't.");
        }
        else 
        {
            attachScript(poiNpc, POI_OBJECT_SCRIPT);
        }
        addToStringList(poiNpc, ident);
        return poiNpc;
    }
    public static obj_id createNpcInStructure(obj_id poiObject, String ident, String type, obj_id structureId, String cellName) throws InterruptedException
    {
        return createNpcInStructure(poiObject, ident, type, structureId, cellName, -1);
    }
    public static obj_id createNpcInStructure(obj_id poiObject, String ident, String type, obj_id structureId, String cellName, int level) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return null;
        }
        location spawnLoc = getGoodLocation(structureId, cellName);
        obj_id poiNpc = create.object(type, spawnLoc, level);
        if (poiNpc == null)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to create " + type);
            return null;
        }
        setObjVar(poiNpc, POI_BASE_OBJECT, poiBaseObject);
        if (hasScript(poiNpc, POI_OBJECT_SCRIPT))
        {
            debugServerConsoleMsg(getSelf(), "WARNING:  " + getName(poiNpc) + " already has the POI OBJECT script attached.  Don't.");
        }
        else 
        {
            attachScript(poiNpc, POI_OBJECT_SCRIPT);
        }
        addToStringList(poiNpc, ident);
        return poiNpc;
    }
    public static obj_id createNpcInStructure(obj_id poiObject, String ident, String type, obj_id structureId) throws InterruptedException
    {
        return createNpcInStructure(poiObject, ident, type, structureId, -1);
    }
    public static obj_id createNpcInStructure(obj_id poiObject, String ident, String type, obj_id structureId, int level) throws InterruptedException
    {
        String cellName = structure.getRandomCell(structureId);
        if (cellName == null || cellName.equals(""))
        {
            return null;
        }
        return createNpcInStructure(poiObject, ident, type, structureId, cellName, level);
    }
    public static obj_id createNpc(String ident, String type, float x, float z) throws InterruptedException
    {
        return createNpc(ident, type, x, z, -1);
    }
    public static obj_id createNpc(String ident, String type, float x, float z, int level) throws InterruptedException
    {
        return createNpc(getSelf(), ident, type, x, z, level);
    }
    public static obj_id findObject(obj_id poiObject, String name) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return null;
        }
        return getObjIdObjVar(poiBaseObject, POI_STRING_LIST + "." + name);
    }
    public static obj_id findObject(String name) throws InterruptedException
    {
        return findObject(getSelf(), name);
    }
    public static String findObjectName(obj_id poiObject) throws InterruptedException
    {
        return getStringObjVar(poiObject, POI_STRING_LIST_INDEXNAME);
    }
    public static String findObjectName() throws InterruptedException
    {
        return findObjectName(getSelf());
    }
    public static void setDestroyMessage(obj_id poiObject, String name, String messageHandlerName, int delay) throws InterruptedException
    {
        obj_id element = findObject(poiObject, name);
        if (element == null)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to locate " + name);
            return;
        }
        setObjVar(element, POI_DESTROY_MESSAGE, messageHandlerName);
        if (delay != 0)
        {
            setObjVar(element, POI_DESTROY_MESSAGE_DELAY, delay);
        }
    }
    public static void setDestroyMessage(obj_id poiObject, String name, String messageHandlerName) throws InterruptedException
    {
        setDestroyMessage(poiObject, name, messageHandlerName, 0);
    }
    public static void setDestroyMessage(String name, String messageHandlerName, int delay) throws InterruptedException
    {
        obj_id poiObject = getSelf();
        setDestroyMessage(poiObject, name, messageHandlerName, delay);
    }
    public static void setDestroyMessage(String name, String messageHandlerName) throws InterruptedException
    {
        setDestroyMessage(getSelf(), name, messageHandlerName, 0);
    }
    public static void setDestroyMessage(obj_id poiObject, String messageHandlerName, int delay) throws InterruptedException
    {
        setObjVar(poiObject, POI_DESTROY_MESSAGE, messageHandlerName);
        if (delay != 0)
        {
            setObjVar(poiObject, POI_DESTROY_MESSAGE_DELAY, delay);
        }
    }
    public static void setDestroyMessage(obj_id poiObject, String messageHandlerName) throws InterruptedException
    {
        setObjVar(poiObject, POI_DESTROY_MESSAGE, messageHandlerName);
    }
    public static void setDestroyMessage(String messageHandlerName, int delay) throws InterruptedException
    {
        setDestroyMessage(getSelf(), messageHandlerName, delay);
    }
    public static void setDestroyMessage(String messageHandlerName) throws InterruptedException
    {
        setDestroyMessage(getSelf(), messageHandlerName, 0);
    }
    public static void grantCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        String objVarName = player.toString();
        if (isDeniedCredit(poiBaseObject, objVarName))
        {
            return;
        }
        if (isGrantedCredit(poiBaseObject, objVarName))
        {
            return;
        }
        setObjVar(poiBaseObject, POI_CREDIT_LIST + "." + objVarName, player);
    }
    public static void grantCredit(obj_id player) throws InterruptedException
    {
        grantCredit(getSelf(), player);
    }
    public static void removeCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        String objVarName = player.toString();
        if (isGrantedCredit(poiBaseObject, objVarName))
        {
            removeObjVar(poiBaseObject, POI_CREDIT_LIST + "." + objVarName);
        }
    }
    public static void removeCredit(obj_id player) throws InterruptedException
    {
        removeCredit(getSelf(), player);
    }
    public static void denyCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        removeCredit(poiObject, player);
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        String objVarName = player.toString();
        if (isDeniedCredit(poiBaseObject, objVarName))
        {
            return;
        }
        setObjVar(poiBaseObject, POI_DENY_LIST + "." + objVarName, player);
    }
    public static void denyCredit(obj_id player) throws InterruptedException
    {
        denyCredit(getSelf(), player);
    }
    public static boolean isGrantedCredit(obj_id poiObject, obj_id player) throws InterruptedException {
        obj_id poiBaseObject = getBaseObject(poiObject);
        return !(poiBaseObject == null || poiBaseObject == obj_id.NULL_ID) && isGrantedCredit(poiBaseObject, player.toString());
    }
    public static boolean isGrantedCredit(obj_id player) throws InterruptedException
    {
        return isGrantedCredit(getSelf(), player);
    }
    public static boolean isGrantedCredit(obj_id poiBaseObject, String objVarName) throws InterruptedException
    {
        return hasObjVar(poiBaseObject, POI_CREDIT_LIST + "." + objVarName);
    }
    public static boolean isDeniedCredit(obj_id poiObject, obj_id player) throws InterruptedException {
        obj_id poiBaseObject = getBaseObject(poiObject);
        return !(poiBaseObject == null || poiBaseObject == obj_id.NULL_ID) && isDeniedCredit(poiBaseObject, player.toString());
    }
    public static boolean isDeniedCredit(obj_id player) throws InterruptedException
    {
        return isDeniedCredit(getSelf(), player);
    }
    public static boolean isDeniedCredit(obj_id poiBaseObject, String objVarName) throws InterruptedException
    {
        return hasObjVar(poiBaseObject, POI_DENY_LIST + "." + objVarName);
    }
    public static void addToStringList(obj_id element, String name) throws InterruptedException
    {
        if (element == null || element == obj_id.NULL_ID)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to add " + name + " to string list");
            return;
        }
        obj_id poiBaseObject = getBaseObject(element);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        setObjVar(poiBaseObject, POI_STRING_LIST + "." + name, element);
        setObjVar(element, POI_STRING_LIST_INDEXNAME, name);
    }
    public static void addToStringList(String name) throws InterruptedException
    {
        addToStringList(getSelf(), name);
    }
    public static void removeFromStringList(obj_id element) throws InterruptedException
    {
        String name = getStringObjVar(element, POI_STRING_LIST_INDEXNAME);
        obj_id existingElement = findObject(element, name);
        if (existingElement == null || existingElement == obj_id.NULL_ID)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to remove " + element + " from string list");
            return;
        }
        removeFromStringList(element, name);
    }
    public static void removeFromStringList() throws InterruptedException
    {
        removeFromStringList(getSelf());
    }
    public static void removeFromStringList(obj_id poiObject, String name) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        obj_id element = getObjIdObjVar(poiBaseObject, POI_STRING_LIST + "." + name);
        if (element == null)
        {
            debugServerConsoleMsg(getSelf(), "Error: Unable to remove " + name + " from string list");
            return;
        }
        removeObjVar(poiBaseObject, POI_STRING_LIST + "." + name);
        removeObjVar(element, POI_STRING_LIST_INDEXNAME);
    }
    public static void removeFromStringList(String name) throws InterruptedException
    {
        removeFromStringList(getSelf(), name);
    }
    public static void addToMasterList(obj_id poiBaseObject, obj_id elementToAdd) throws InterruptedException
    {
    }
    public static void removeFromMasterList(obj_id poiBaseObject, obj_id elementToRemove) throws InterruptedException
    {
    }
    public static boolean isInMasterList(obj_id poiBaseObject, obj_id element) throws InterruptedException
    {
        return false;
    }
    public static boolean isInPersistedList(obj_id poiBaseObject, obj_id element) throws InterruptedException
    {
        return false;
    }
    public static obj_id getBaseObject(obj_id poiObject) throws InterruptedException
    {
        if (!isIdValid(poiObject) || !hasObjVar(poiObject, POI_BASE_OBJECT))
        {
            debugServerConsoleMsg(poiObject, "Error: unable to locate poi object!");
            return null;
        }
        return getObjIdObjVar(poiObject, POI_BASE_OBJECT);
    }
    public static obj_id getBaseObject() throws InterruptedException
    {
        return getBaseObject(getSelf());
    }
    public static void baseObjectDestroyed(obj_id poiBaseObject) throws InterruptedException
    {
        setObjVar(poiBaseObject, "ignoreRemoval", true);
        dictionary parms = new dictionary();
        parms.put("noMessageOnDestroy", 1);
        parms.put("baseObjectUnloading", 0);
        broadcastMessage("cleanUpRoutine", parms);
    }
    public static void objectDestroyed(obj_id element) throws InterruptedException
    {
        if (hasObjVar(element, POI_NO_MESSAGE_ON_DESTROY))
        {
            return;
        }
        obj_id baseObject = getBaseObject(element);
        if (baseObject == null || baseObject == obj_id.NULL_ID)
        {
            return;
        }
        dictionary parms = new dictionary();
        String name = findObjectName(element);
        if (name != null)
        {
            parms.put("name", name);
        }
        messageTo(baseObject, "objectDestroyed", parms, 0, isObjectPersisted(baseObject));
    }
    public static String getDifficulty(obj_id poiObject) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return "easy";
        }
        if (hasObjVar(poiBaseObject, POI_DIFFICULTY))
        {
            return getStringObjVar(poiBaseObject, POI_DIFFICULTY);
        }
        int difficulty = getIntObjVar(poiBaseObject, "intDifficulty");
        String diff;
        if (difficulty <= 4)
        {
            diff = "veryEasy";
        }
        else if (difficulty <= 8)
        {
            diff = "easy";
        }
        else if (difficulty <= 12)
        {
            diff = "medium";
        }
        else if (difficulty <= 16)
        {
            diff = "hard";
        }
        else 
        {
            diff = "veryHard";
        }
        setObjVar(poiBaseObject, POI_DIFFICULTY, diff);
        return diff;
    }
    public static String getDifficulty() throws InterruptedException
    {
        return getDifficulty(getSelf());
    }
    public static int getIntDifficulty() throws InterruptedException
    {
        switch (getDifficulty(getSelf())) {
            case "veryEasy":
                return DIFF_VEASY;
            case "easy":
                return DIFF_EASY;
            case "medium":
                return DIFF_MEDIUM;
            case "hard":
                return DIFF_HARD;
            case "veryHard":
                return DIFF_VHARD;
            default:
                return DIFF_UNKNOWN;
        }
    }
    public static String getFaction(obj_id poiObject) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return null;
        }
        return getStringObjVar(poiBaseObject, POI_FACTION);
    }
    public static String getFaction() throws InterruptedException
    {
        return getFaction(getSelf());
    }
    public static float getFactionValue(obj_id poiObject) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return 0.0f;
        }
        return getFloatObjVar(poiBaseObject, POI_FACTION_VALUE);
    }
    public static float getFactionValue() throws InterruptedException
    {
        return getFactionValue(getSelf());
    }
    public static String getObjective(obj_id poiObject) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return null;
        }
        return getStringObjVar(poiBaseObject, POI_OBJECTIVE);
    }
    public static String getObjective() throws InterruptedException
    {
        return getObjective(getSelf());
    }
    public static void setFaction(obj_id poiObject, String faction) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        setObjVar(poiBaseObject, POI_FACTION, faction);
    }
    public static void setFaction(String faction) throws InterruptedException
    {
        setFaction(getSelf(), faction);
    }
    public static void setFactionValue(obj_id poiObject, float factionValue) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        setObjVar(poiBaseObject, POI_FACTION_VALUE, factionValue);
    }
    public static void setFactionValue(float factionValue) throws InterruptedException
    {
        setFactionValue(getSelf(), factionValue);
    }
    public static void complete(obj_id poiObject, int status) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        if (isComplete(poiBaseObject))
        {
            return;
        }
        if (status == POI_SUCCESS) {
            awardFactionStanding(poiBaseObject);
        }
        sendMissionStatus(poiBaseObject, status);
        messageTo(poiBaseObject, "cleanUpRoutine", null, 60, false);
        setObjVar(poiBaseObject, POI_COMPLETED, true);
    }
    public static void complete(int status) throws InterruptedException
    {
        complete(getSelf(), status);
    }
    public static void complete(obj_id poiObject) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return;
        }
        if (isComplete(poiBaseObject))
        {
            return;
        }
        sendMissionStatus(poiBaseObject, POI_INCOMPLETE);
        messageTo(poiBaseObject, "cleanUpRoutine", null, 60, false);
        setObjVar(poiBaseObject, POI_COMPLETED, true);
    }
    public static void complete() throws InterruptedException
    {
        complete(getSelf());
    }
    public static void awardFactionStanding(obj_id poiBaseObject) throws InterruptedException
    {
        String faction = getFaction(poiBaseObject);
        if (faction == null)
        {
            return;
        }
        obj_var_list playerList = getObjVarList(poiBaseObject, POI_CREDIT_LIST);
        if (playerList == null)
        {
            return;
        }

        for (int i = 0; i < playerList.getNumItems(); ++i)
        {
            factions.addFactionStanding(utils.stringToObjId(playerList.getObjVar(i).getName()), faction, getFactionValue(poiBaseObject));
        }
    }
    public static void sendMissionStatus(obj_id poiBaseObject, int status) throws InterruptedException
    {
        if (!hasObjVar(poiBaseObject, "objMission"))
        {
            return;
        }
        obj_id objMission = getObjIdObjVar(poiBaseObject, "objMission");
        switch (status)
        {
            case POI_SUCCESS:
                messageTo(objMission, "destructionSuccess", null, 0, true);
                break;
            case POI_INCOMPLETE:
                messageTo(objMission, "destructionIncomplete", null, 0, true);
                break;
            case POI_FAIL:
                messageTo(objMission, "destructionFail", null, 0, true);
                break;
            default:
                messageTo(objMission, "destructionIncomplete", null, 0, true);
                break;
        }
    }
    public static void baseObjectRemovedFromWorld(obj_id poiBaseObject) throws InterruptedException
    {
        if (isComplete(poiBaseObject))
        {
            destroyObject(poiBaseObject);
            return;
        }
        if (hasObjVar(poiBaseObject, "ignoreRemoval"))
        {
            return;
        }
        dictionary parms = new dictionary();
        parms.put("noMessageOnDestroy", 1);
        parms.put("baseObjectUnloading", 1);
        broadcastMessage("cleanUpRoutine", parms);
        removeObjVar(poiBaseObject, POI_STRING_LIST);
    }
    public static boolean isComplete(obj_id poiObject) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiObject);
        if (!isIdValid(poiBaseObject))
        {
            return false;
        }
        if (hasObjVar(poiObject, "npc_lair.target"))
        {
            if (!exists(getObjIdObjVar(poiObject, "npc_lair.target")))
            {
                return true;
            }
        }
        return hasObjVar(poiBaseObject, POI_COMPLETED);
    }
    public static boolean isComplete() throws InterruptedException
    {
        return isComplete(getSelf());
    }
    public static boolean setTitle(obj_id npc, String title) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(npc);
        if (poiBaseObject == null || poiBaseObject == obj_id.NULL_ID)
        {
            return false;
        }
        String convo = getStringObjVar(poiBaseObject, scenario.VAR_SCENARIO_CONVO);
        if (convo == null || convo.equals(""))
        {
            return false;
        }
        setName(npc, getString(new string_id(convo, title)) + " " + getName(npc));
        return true;
    }
    public static boolean isNpcTagged(obj_id npc, String tag) throws InterruptedException {
        obj_id found = findObject(tag);
        return !((found == null) || (found == obj_id.NULL_ID)) && found == npc;
    }
    public static boolean quickSay(obj_id npc, String speech) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(npc);
        if ((poiBaseObject == obj_id.NULL_ID))
        {
            return false;
        }
        String convo = getStringObjVar(poiBaseObject, scenario.VAR_SCENARIO_CONVO);
        if (convo == null || convo.equals(""))
        {
            return false;
        }
        scenario.sayNoAct(npc, convo, speech);
        return true;
    }
    public static boolean quickSay(obj_id npc, String chatType, String moodType, String speech) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(npc);
        if ((poiBaseObject == null) || (poiBaseObject == obj_id.NULL_ID))
        {
            return false;
        }
        String convo = getStringObjVar(poiBaseObject, scenario.VAR_SCENARIO_CONVO);
        if (convo == null || convo.equals(""))
        {
            return false;
        }
        chat.chat(npc, chatType, moodType, new string_id(convo, speech));
        return true;
    }
    public static obj_id getRandomMediator(obj_id poiobj) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiobj);
        if ((poiBaseObject == null) || (poiBaseObject == obj_id.NULL_ID))
        {
            return null;
        }
        return poi.findObject(scenario.MEDIATOR + "_" + rand(0, getIntObjVar(poiBaseObject, scenario.VAR_MEDIATOR_COUNT) - 1));
    }
    public static obj_id getRandomAntagonist(obj_id poiobj) throws InterruptedException
    {
        obj_id poiBaseObject = getBaseObject(poiobj);
        if ((poiBaseObject == null) || (poiBaseObject == obj_id.NULL_ID))
        {
            return null;
        }
        return poi.findObject(scenario.ANTAGONIST + "_" + rand(0, getIntObjVar(poiBaseObject, scenario.VAR_ANTAGONIST_COUNT) - 1));
    }
}
