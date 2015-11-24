package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.instance;
import script.library.create;
import script.library.trial;
import script.library.badge;

public class volcano_event_manager extends script.base_script
{
    public volcano_event_manager()
    {
    }
    public static final String dataTable = "datatables/dungeon/mustafar_trials/volcano_battlefield/volcano_event_data.iff";
    public static final int[] events = 
    {
        0,
        1,
        2,
        3,
        4,
        5
    };
    public static final boolean doLogging = false;
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        spawnActors(self, 1);
        return SCRIPT_CONTINUE;
    }
    public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "idx"))
        {
            utils.removeScriptVar(self, "idx");
        }
        if (utils.hasScriptVar(self, "observer"))
        {
            utils.removeScriptVar(self, "observer");
        }
        obj_id[] objects = getObjectsInRange(self, 500);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (objects[i] != self)
            {
                if (!isPlayer(objects[i]))
                {
                    if (!isMob(objects[i]))
                    {
                        if (trial.isTempObject(objects[i]))
                        {
                            destroyObject(objects[i]);
                        }
                    }
                    else 
                    {
                        kill(objects[i]);
                        destroyObject(objects[i]);
                    }
                }
                else 
                {
                }
            }
        }
    }
    public void spawnActors(obj_id dungeon, int stage) throws InterruptedException
    {
        int rows = dataTableGetNumRows(dataTable);
        if (rows == 0)
        {
            doLogging("spawnActors", "The datatable was empty");
            return;
        }
        obj_id[] event_manager = new obj_id[events.length];
        int k = 0;
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(dataTable, i);
            if (dict.getInt("stage") == stage)
            {
                String battlePoint = dict.getString("wp_name");
                obj_id wpId = getWaypointId(dungeon, battlePoint);
                if (isIdValid(wpId))
                {
                    location here = getLocation(wpId);
                    int locx = dict.getInt("locx");
                    int locy = dict.getInt("locy");
                    int locz = dict.getInt("locz");
                    float locX = here.x + locx;
                    float locY = here.y + locy;
                    float locZ = here.z + locz;
                    float yaw = dict.getFloat("yaw");
                    String script = dict.getString("script");
                    location spawnLoc = new location(locX, locY, locZ, here.area, here.cell);
                    String objvar = dict.getString("objvar");
                    String object = dict.getString("object");
                    if (object.startsWith("object/"))
                    {
                        obj_id item = createObject(object, spawnLoc);
                        if (!isIdValid(item))
                        {
                            doLogging("spawnActors", "Tried to create invalid item(" + object + ")");
                            return;
                        }
                        if (object.equals("object/tangible/spawning/simple_spawn_item.iff"))
                        {
                            event_manager[k] = item;
                            k++;
                        }
                        setYaw(item, yaw);
                        trial.markAsTempObject(item, true);
                        setObjVar(item, "parent", dungeon);
                        if (!script.equals("none"))
                        {
                            attachScript(item, script);
                        }
                        if (!objvar.equals("none"))
                        {
                            setObjVar(item, "objvar", objvar);
                        }
                    }
                    else 
                    {
                        obj_id creature = create.object(object, spawnLoc);
                        if (!isIdValid(creature))
                        {
                            doLogging("spawnActors", "Tried to create invalid creature(" + object + ")");
                            return;
                        }
                        if (object.equals("som_volcano_observer"))
                        {
                            utils.setScriptVar(dungeon, "observer", creature);
                        }
                        if (!script.equals("none"))
                        {
                            attachScript(creature, script);
                        }
                        if (!objvar.equals("none"))
                        {
                            setObjVar(creature, "objvar", objvar);
                        }
                    }
                }
            }
        }
        if (event_manager != null && event_manager.length != 0)
        {
            utils.setScriptVar(dungeon, "event_manager", event_manager);
        }
    }
    public obj_id getWaypointId(obj_id dungeon, String wpName) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(getLocation(dungeon), 500);
        if (objects == null || objects.length == 0)
        {
            doLogging("getWaypointId", "Object list was null or empty, return null");
            return null;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (hasObjVar(objects[i], "battlePoint"))
            {
                if ((getStringObjVar(objects[i], "battlePoint")).equals(wpName))
                {
                    return objects[i];
                }
            }
        }
        doLogging("getWaypointId", "No objects had the matching string objvar");
        return null;
    }
    public int eventDefeated(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = 1;
        if (utils.hasScriptVar(self, "idx"))
        {
            idx = utils.getIntScriptVar(self, "idx");
        }
        if (!utils.hasScriptVar(self, "event_manager"))
        {
            doLogging("eventDefeated", "Cannot activate next event due to lack of an event manager");
            return SCRIPT_CONTINUE;
        }
        obj_id[] event_manager = utils.getObjIdArrayScriptVar(self, "event_manager");
        obj_id nextEvent = event_manager[idx];
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        messageTo(nextEvent, "activateEvent", null, 0, false);
        idx += 1;
        if (idx > event_manager.length - 1)
        {
            doLogging("eventDefeated", "Next event idx is out of bounds");
            idx = 0;
        }
        utils.setScriptVar(self, "idx", idx);
        return SCRIPT_CONTINUE;
    }
    public int doHkTaunt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id hk = params.getObjId("hk");
        utils.messagePlayer(hk, players, trial.VOLCANO_HK_TAUNT, "object/mobile/som/hk47.iff");
        instance.playMusicInInstance(self, trial.MUS_VOLCANO_HK_INTRO);
        return SCRIPT_CONTINUE;
    }
    public int landYt(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "yt"))
        {
            obj_id oldYt = utils.getObjIdScriptVar(self, "yt");
            if (isIdValid(oldYt))
            {
                destroyObject(oldYt);
            }
        }
        location spawnLoc = getLocation(self);
        spawnLoc.x += 31.6;
        spawnLoc.z += 2.2;
        obj_id yt2400 = create.object("object/creature/npc/theme_park/must_yt2400.iff", spawnLoc);
        if (!isIdValid(yt2400))
        {
            doLogging("landYt", "failed to create yt 2400");
            return SCRIPT_CONTINUE;
        }
        setPosture(yt2400, POSTURE_PRONE);
        setYaw(yt2400, -163);
        utils.setScriptVar(self, "yt", yt2400);
        utils.setScriptVar(self, "tempYt", true);
        trial.markAsTempObject(yt2400, true);
        detachScript(yt2400, "ai.ai");
        detachScript(yt2400, "ai.creature_combat");
        detachScript(yt2400, "skeleton.humanoid");
        detachScript(yt2400, "systems.combat.combat_actions");
        detachScript(yt2400, "systems.combat.credit_for_kills");
        detachScript(yt2400, "player.species_innate");
        attachScript(yt2400, "theme_park.dungeon.mustafar_trials.volcano_battlefield.yt_controller");
        dictionary dict = trial.getSessionDict(self, "yt");
        dict.put("landLoc", spawnLoc);
        messageTo(yt2400, "performLanding", null, 2, false);
        messageTo(self, "playSmoke", dict, 34, false);
        return SCRIPT_CONTINUE;
    }
    public int playSmoke(obj_id self, dictionary params) throws InterruptedException
    {
        location landLoc = params.getLocation("landLoc");
        obj_id[] viewer = instance.getPlayersInInstanceArea(self);
        if (viewer != null && viewer.length > 0 && isIdValid(viewer[0]))
        {
            playClientEffectLoc(viewer[0], trial.PRT_VOLCANO_YT_LANDING, landLoc, 1.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int replacePlaceholder(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id oldYt = utils.getObjIdScriptVar(self, "yt");
        location spawnLoc = getLocation(oldYt);
        float yaw = getYaw(oldYt);
        obj_id newYt = create.object("object/building/mustafar/structures/must_grounded_yt2400.iff", spawnLoc);
        obj_id bridge = getCellId(newYt, "bridge");
        setYaw(newYt, yaw);
        utils.setScriptVar(self, "yt", newYt);
        utils.setScriptVar(self, "tempYt", false);
        trial.markAsTempObject(newYt, true);
        dictionary dict = trial.getSessionDict(self, "yt");
        dict.put("yt", oldYt);
        messageTo(self, "destroyOld", dict, 2, false);
        location pilotLoc = new location(25, 5, 13, spawnLoc.area, bridge);
        obj_id pilot = create.object("som_volcano_autopilot", pilotLoc);
        attachScript(pilot, "conversation.trial_volcano_autopilot");
        return SCRIPT_CONTINUE;
    }
    public int destroyOld(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id yt = params.getObjId("yt");
        destroyObject(yt);
        return SCRIPT_CONTINUE;
    }
    public int destroyYt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id yt = utils.getObjIdScriptVar(self, "yt");
        boolean type = utils.getBooleanScriptVar(self, "tempYt");
        if (type)
        {
            if (!isIdValid(yt))
            {
                doLogging("destroyYt", "Controller had an invalid obj_id for the a YT-2400");
                return SCRIPT_CONTINUE;
            }
            messageTo(yt, "performTakeoff", null, 0, false);
            messageTo(yt, "selfDestruct", null, 20, false);
        }
        else 
        {
            if (!isIdValid(yt))
            {
                doLogging("destroyYt", "Controller had an invalid obj_id for the YT-2400");
                return SCRIPT_CONTINUE;
            }
            destroyObject(yt);
        }
        return SCRIPT_CONTINUE;
    }
    public int hkDefeated(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setDungeonCleanOutTimer(self);
        trial.sendCompletionSignal(self, trial.VOLCANO_WIN_SIGNAL);
        messageTo(self, "replacePlaceholder", null, 0, false);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        badge.grantBadge(players, "bdg_must_victory_volcano");
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("travis/volcano_event_manager/" + section, message);
        }
    }
}
