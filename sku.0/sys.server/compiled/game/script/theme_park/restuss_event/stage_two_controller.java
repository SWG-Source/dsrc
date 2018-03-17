package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_dungeon;
import script.library.create;
import script.library.ai_lib;
import script.library.trial;
import script.library.groundquests;
import script.library.buff;
import script.library.factions;
import script.library.restuss_event;

public class stage_two_controller extends script.base_script
{
    public stage_two_controller()
    {
    }
    public static final String STAGE = "currentStage";
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
    public static final boolean LOGGING = true;
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
        for (int i = 0; i < objects.length; i++)
        {
            if (objects[i] != self && !isPlayer(objects[i]))
            {
                if (trial.isTempObject(objects[i]))
                {
                    trial.cleanupObject(objects[i]);
                }
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
        int[] allStages = dataTableGetIntColumn(restuss_event.STAGE_TWO_DATA, "stage");
        if (allStages == null || allStages.length == 0)
        {
            doLogging("spawnNextStage", "Failed to get the stage column");
            return SCRIPT_CONTINUE;
        }
        boolean moreStages = false;
        int nextStage = (int)Float.POSITIVE_INFINITY;
        float timeToNext = 0;
        for (int i = 0; i < allStages.length; i++)
        {
            if (allStages[i] > stage && allStages[i] < nextStage)
            {
                nextStage = allStages[i];
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
            messageTo(self, "cleanupEvent", null, 10.0f, false);
            return SCRIPT_CONTINUE;
        }
    }
    public void spawnActors(obj_id controller, int stage) throws InterruptedException
    {
        spawnActors(controller, stage, null);
    }
    public void spawnActors(obj_id controller, int stage, String override) throws InterruptedException
    {
        int rows = dataTableGetNumRows(restuss_event.STAGE_TWO_DATA);
        if (rows == 0)
        {
            doLogging("spawnActors", "Your table has no rows: " + restuss_event.STAGE_TWO_DATA);
            return;
        }
        if (stage == 2)
        {
            restuss_event.playMusicInArea(controller, restuss_event.MUS_BATTLE_IMPERIAL_INTRO);
        }
        if (stage == 10)
        {
            restuss_event.playMusicInArea(controller, restuss_event.MUS_BATTLE_IMPERIAL_INTRO);
        }
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(restuss_event.STAGE_TWO_DATA, i);
            if (dict.getInt("stage") == stage || (dict.getString("triggerId")).equals(override))
            {
                utils.setScriptVar(controller, STAGE, stage);
                location here = getLocation(controller);
                float locx = dict.getFloat("locx");
                float locy = dict.getFloat("locy");
                float locz = dict.getFloat("locz");
                float locX = here.x + locx;
                float locY = here.y + locy;
                float locZ = here.z + locz;
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
                else 
                {
                    newObject = create.object(object, spawnLoc);
                    if (!isIdValid(newObject))
                    {
                        doLogging("spawnActors", "Tried to create invalid creature(" + object + ")");
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
                attachSpawnScripts(newObject, spawnScript, objType);
                if (object.indexOf("patrol_waypoint.iff") > -1)
                {
                    addToWaypointData(controller, newObject);
                }
            }
        }
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
                attachScript(subject, "theme_park.restuss_event.ai_controller");
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
        for (int q = 0; q < scripts.length; q++)
        {
            attachScript(subject, scripts[q]);
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
        for (int i = 0; i < parse.length; i++)
        {
            String[] typeDataSplit = split(parse[i], ':');
            String type = typeDataSplit[0];
            String data = typeDataSplit[1];
            String[] nameValueSplit = split(data, '=');
            String name = nameValueSplit[0];
            String value = nameValueSplit[1];
            if (type.equals("int"))
            {
                setObjVar(newObject, name, utils.stringToInt(value));
            }
            if (type.equals("float"))
            {
                setObjVar(newObject, name, utils.stringToFloat(value));
            }
            if (type.equals("string"))
            {
                setObjVar(newObject, name, value);
            }
            if (type.equals("boolean") && (value.equals("true") || value.equals("1")))
            {
                setObjVar(newObject, name, true);
            }
            if (type.equals("boolean") && (value.equals("false") || value.equals("0")))
            {
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
        for (int r = 0; r < players.length; r++)
        {
            playMusic(players[r], parse[1]);
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanupEvent", null, 3, false);
        getClusterWideData("event", "restuss_event", true, self);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_name, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        String name = "restuss_event";
        String scene = getCurrentSceneName();
        location loc = getLocation(self);
        dictionary dungeon_info = new dictionary();
        dungeon_info.put("dungeon_id", self);
        dungeon_info.put("scene", scene);
        dungeon_info.put("position_x", loc.x);
        dungeon_info.put("position_y", loc.y);
        dungeon_info.put("position_z", loc.z);
        replaceClusterWideData(manage_name, name, dungeon_info, true, lock_key);
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/stage_two_controller/" + section, message);
        }
    }
}
