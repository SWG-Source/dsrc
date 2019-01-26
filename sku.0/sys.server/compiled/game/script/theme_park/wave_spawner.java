package script.theme_park;

import script.dictionary;
import script.library.create;
import script.library.restuss_event;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class wave_spawner extends script.base_script
{
    public wave_spawner()
    {
    }
    public static final String STAGE = "currentStage";
    public static final String DATA_TABLE = "wave_spawner.data_table";
    public static final String START_DELAY = "wave_spawner.start_delay";
    public static final String[] TRIGGER_DATA_TYPES = 
    {
        "name",
        "triggerInterest",
        "size",
        "occurance",
        "triggerDelay",
        "triggerType"
    };
    public static final int TYPE_AI = 0;
    public static final int TYPE_TRIGGER = 1;
    public static final int TYPE_EFFECT_MANAGER = 2;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float delay = 1.0f;
        if (hasObjVar(self, START_DELAY))
        {
            delay = getFloatObjVar(self, START_DELAY);
        }
        messageTo(self, "beginSpawning", null, delay, false);
        return SCRIPT_CONTINUE;
    }
    public int beginSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        dictionary dict = trial.getSessionDict(self);
        
        dict.put("stage", 1);
        messageTo(self, "spawnNextStage", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupEvent(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, STAGE, 0);
        trial.bumpSession(self);
        utils.removeScriptVar(self, restuss_event.MASTER_PATROL_ARRAY);
        obj_id[] objects = trial.getChildrenInRange(self, self, 1000.0f);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if (object != self && !isPlayer(object)) {
                trial.cleanupObject(object);
            }
        }
    }
    public int spawnNextStage(obj_id self, dictionary params) throws InterruptedException
    {
        int stage = params.getInt("stage");
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        spawnActors(self, stage);
        String spawnTable = getDataTable(self);
        int[] allStages = dataTableGetIntColumn(spawnTable, "stage");
        if (allStages == null || allStages.length == 0)
        {
            doLogging("spawnNextStage", "Failed to get the stage column");
            return SCRIPT_CONTINUE;
        }
        boolean moreStages = false;
        int nextStage = (int)Float.POSITIVE_INFINITY;
        float timeToNext = 0;
        for (int allStage : allStages) {
            if (allStage > stage && allStage < nextStage) {
                nextStage = allStage;
                timeToNext = nextStage - stage;
                moreStages = true;
            }
        }
        if (moreStages)
        {
            dictionary dict = trial.getSessionDict(self);
            dict.put("stage", nextStage);
            messageTo(self, "spawnNextStage", dict, timeToNext, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "cleanupWaveSpawner", null, 600, false);
            return SCRIPT_CONTINUE;
        }
    }
    public int cleanupWaveSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnActors(obj_id controller, int stage) throws InterruptedException
    {
        spawnActors(controller, stage, null);
    }
    public void spawnActors(obj_id controller, int stage, String override) throws InterruptedException
    {
        String spawnTable = getDataTable(controller);
        int rows = dataTableGetNumRows(spawnTable);
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(spawnTable, i);
            if (dict.getInt("stage") == stage || (dict.getString("triggerId")).equals(override))
            {
                utils.setScriptVar(controller, STAGE, stage);
                location here = getLocation(controller);
                float locX = dict.getFloat("locx");
                float locY = dict.getFloat("locy");
                float locZ = dict.getFloat("locz");
                int locationType = dict.getInt("location_type");
                if (locationType == 0)
                {
                    locX = here.x + locX;
                    locY = here.y + locY;
                    locZ = here.z + locZ;
                }
                float yaw = dict.getFloat("yaw");
                String spawnScript = dict.getString("script");
                String spawnObjVar = dict.getString("objVar");
                location spawnLoc = new location(locX, locY, locZ, here.area, here.cell);
                String object = dict.getString("object");
                int patrolType = dict.getInt("patrolType");
                obj_id newObject = null;
                int objType = -1;
                if (object.startsWith("object/"))
                {
                    newObject = createObject(object, spawnLoc);
                    if (!isIdValid(newObject))
                    {
                        doLogging("spawnActors", "Tried to create invalid item(" + object + ")");
                        return;
                    }
                }
                else if (object.startsWith("trigger"))
                {
                    dictionary triggerData = parseTriggerData(object);
                    newObject = createObject("object/tangible/theme_park/invisible_object.iff", spawnLoc);
                    trial.setInterest(newObject);
                    messageTo(newObject, "triggerSetup", triggerData, 1, false);
                    objType = TYPE_TRIGGER;
                }
                else if (object.startsWith("clientfx"))
                {
                    newObject = createObject("object/tangible/theme_park/invisible_object.iff", spawnLoc);
                    objType = TYPE_EFFECT_MANAGER;
                    setClientEffectData(newObject, object);
                }
                else if (object.startsWith("messageTo"))
                {
                    doMessageTo(object);
                    continue;
                }
                else if (object.startsWith("music"))
                {
                    doPlayMusicInArea(object);
                    continue;
                }
                else if (object.startsWith("endOfLine"))
                {
                    messageTo(controller, "cleanupWaveSpawner", null, 0, false);
                    break;
                }
                else 
                {
                    newObject = create.object(object, spawnLoc);
                    if (!isIdValid(newObject))
                    {
                        return;
                    }
                    if (patrolType != restuss_event.PATROL_NONE)
                    {
                        setObjVar(newObject, restuss_event.PATROL_TYPE, patrolType);
                        utils.setScriptVar(newObject, restuss_event.MASTER_PATROL_ARRAY, utils.getResizeableObjIdArrayScriptVar(controller, restuss_event.MASTER_PATROL_ARRAY));
                    }
                    else 
                    {
                        restuss_event.setIsStatic(newObject, true);
                    }
                    objType = TYPE_AI;
                    setHibernationDelay(newObject, 3600.0f);
                }
                setYaw(newObject, yaw);
                trial.markAsTempObject(newObject, true);
                trial.setParent(controller, newObject, true);
                trial.setInterest(newObject);
                setSpawnObjVar(newObject, spawnObjVar);
                copyObjVar(controller, newObject, DATA_TABLE);
                attachSpawnScripts(newObject, spawnScript, objType);
                if (object.contains("patrol_waypoint.iff"))
                {
                    addToWaypointData(controller, newObject);
                }
            }
        }
    }
    public String getDataTable(obj_id self) throws InterruptedException
    {
        String table = "";
        if (hasObjVar(self, DATA_TABLE))
        {
            table = getStringObjVar(self, DATA_TABLE);
        }
        return table;
    }
    public void addToWaypointData(obj_id controller, obj_id waypointObject) throws InterruptedException
    {
        Vector wp = new Vector();
        wp.setSize(0);
        if (utils.hasScriptVar(controller, restuss_event.MASTER_PATROL_ARRAY))
        {
            wp = utils.getResizeableObjIdArrayScriptVar(controller, restuss_event.MASTER_PATROL_ARRAY);
        }
        utils.addElement(wp, waypointObject);
        setName(waypointObject, getStringObjVar(waypointObject, "wp_name"));
        utils.setScriptVar(controller, restuss_event.MASTER_PATROL_ARRAY, wp);
    }
    public void transferWaypointData(obj_id controller, obj_id receiver) throws InterruptedException
    {
        if (!utils.hasScriptVar(controller, restuss_event.MASTER_PATROL_ARRAY))
        {
            doLogging("transferWaypointData", "Tried to set location data on subject but there is no master array");
            return;
        }
        setObjVar(receiver, restuss_event.MASTER_PATROL_ARRAY, utils.getResizeableObjIdArrayScriptVar(controller, restuss_event.MASTER_PATROL_ARRAY));
    }
    public void attachSpawnScripts(obj_id subject, String spawnScripts) throws InterruptedException
    {
        attachSpawnScripts(subject, spawnScripts, -1);
    }
    public void attachSpawnScripts(obj_id subject, String spawnScripts, int type) throws InterruptedException
    {
        if (type > -1)
        {
            switch (type)
            {
                case TYPE_AI:
                attachScript(subject, "theme_park.wave_spawner_ai_controller");
                break;
                case TYPE_TRIGGER:
                attachScript(subject, "theme_park.restuss_event.trigger_controller");
                break;
                case TYPE_EFFECT_MANAGER:
                attachScript(subject, "theme_park.restuss_event.restuss_clientfx_controller");
            }
        }
        if (spawnScripts == null || spawnScripts.equals("none"))
        {
            return;
        }
        String[] scripts = split(spawnScripts, ';');
        for (String script : scripts) {
            attachScript(subject, script);
        }
    }
    public void setSpawnObjVar(obj_id newObject, String objvarString) throws InterruptedException
    {
        if (objvarString == null || objvarString.equals("none"))
        {
            return;
        }
        String[] parse = split(objvarString, ';');
        if (parse == null || parse.length == 0)
        {
            return;
        }
        for (String s : parse) {
            String[] typeDataSplit = split(s, ':');
            String type = typeDataSplit[0];
            String data = typeDataSplit[1];
            String[] nameValueSplit = split(data, '=');
            String name = nameValueSplit[0];
            String value = nameValueSplit[1];
            if (type.equals("int")) {
                setObjVar(newObject, name, utils.stringToInt(value));
            }
            if (type.equals("float")) {
                setObjVar(newObject, name, utils.stringToFloat(value));
            }
            if (type.equals("string")) {
                setObjVar(newObject, name, value);
            }
            if (type.equals("boolean") && (value.equals("true") || value.equals("1"))) {
                setObjVar(newObject, name, true);
            }
            if (type.equals("boolean") && (value.equals("false") || value.equals("0"))) {
                setObjVar(newObject, name, false);
            }
        }
    }
    public dictionary parseTriggerData(String data) throws InterruptedException
    {
        if (data == null || data.equals(""))
        {
            doLogging("parseTriggerData", "Tried to parse an empty dataset");
            return null;
        }
        dictionary dict = new dictionary();
        String[] parse = split(data, ':');
        if (parse == null || parse.length == 0)
        {
            doLogging("parseTriggerData", "Failed to parse dataset");
            return null;
        }
        for (int i = 0; i < parse.length - 1; i++)
        {
            dict.put(TRIGGER_DATA_TYPES[i], parse[i + 1]);
        }
        return dict;
    }
    public void setClientEffectData(obj_id object, String passedString) throws InterruptedException
    {
        String[] parse = split(passedString, ':');
        if (parse == null || parse.length < 3)
        {
            return;
        }
        setObjVar(object, restuss_event.EFFECT_NAME, parse[1]);
        setObjVar(object, restuss_event.EFFECT_VISABILITY, parse[2]);
        if (parse.length == 4)
        {
            setObjVar(object, restuss_event.EFFECT_DELTA, parse[3]);
        }
        else 
        {
            setObjVar(object, restuss_event.EFFECT_DELTA, "0");
        }
    }
    public int triggerFired(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        String triggerName = params.getString("triggerName");
        obj_id player = params.getObjId("target");
        String triggerType = params.getString("triggerType");
        if (triggerType.equals("triggerId"))
        {
            spawnActors(self, 0, triggerName);
            sendSystemMessageTestingOnly(player, "Trigger recieved, player(" + player + ") tripped trigger(" + triggerName + ") of type (" + triggerType + ")");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void doMessageTo(String message) throws InterruptedException
    {
        String[] completeParse = split(message, ':');
        if (completeParse == null || completeParse.length == 0)
        {
            doLogging("doMessageTo", "Failed to parse message");
            return;
        }
        if (completeParse[1].startsWith("broadcastMessage"))
        {
            float range = utils.stringToFloat(completeParse[2]);
            obj_id[] objects = getObjectsInRange(getLocation(getSelf()), range);
            if (objects == null || objects.length == 0)
            {
                return;
            }
            utils.messageTo(objects, completeParse[3], null, 0, false);
        }
    }
    public void doPlayMusicInArea(String message) throws InterruptedException
    {
        String[] parse = split(message, ':');
        if (parse == null || parse.length == 0)
        {
            doLogging("doPlayMusicInArea", "message was null or empty");
            return;
        }
        float range = 200.0f;
        if (parse.length > 2)
        {
            range = utils.stringToFloat(parse[2]);
        }
        obj_id[] players = getPlayerCreaturesInRange(getSelf(), range);
        if (players == null || players.length == 0)
        {
            return;
        }
        for (obj_id player : players) {
            playMusic(player, parse[1]);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/wave_spawner/" + section, message);
        }
    }
}
