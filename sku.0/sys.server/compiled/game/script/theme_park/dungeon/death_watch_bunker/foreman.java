package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.utils;
import script.library.create;

public class foreman extends script.base_script
{
    public foreman()
    {
    }
    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";
    public static final string_id BATTERY_CLEANED = new string_id("dungeon/death_watch", "battery_cleaned");
    public static final string_id NEED_BATTERY = new string_id("dungeon/death_watch", "need_battery");
    public static final string_id NOT_AUTHORIZED = new string_id("dungeon/death_watch", "not_authorized");
    public static final String CONVO = "dungeon/death_watch";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "conversation.death_watch_foreman");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
        setName(self, "Japer Witter (a mine foreman)");
        messageTo(self, "handleMissionCleanUp", null, 30f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (hasObjVar(structure, "death_watch.haldo"))
        {
            removeObjVar(structure, "death_watch.haldo");
            removeObjVar(structure, "death_watch.haldo_player");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnHaldo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, "death_watch.drill_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id top = getTopMostContainer(self);
        setObjVar(top, "death_watch.haldo_player", player);
        int randCounter = rand(1, 3);
        if (randCounter == 1)
        {
            float xCoord = -181.6f;
            float yCoord = -60.34f;
            float zCoord = -229.7f;
            location myself = getLocation(self);
            String planet = myself.area;
            obj_id room = getCellId(top, "largeroom62");
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object("mand_bunker_crazed_miner", spawnPoint);
            setObjVar(top, "death_watch.haldo", spawnedCreature);
        }
        else if (randCounter == 2)
        {
            float xCoord = -180.4f;
            float yCoord = -60f;
            float zCoord = -152.3f;
            location myself = getLocation(self);
            String planet = myself.area;
            obj_id room = getCellId(top, "medroom55");
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object("mand_bunker_crazed_miner", spawnPoint);
            setObjVar(top, "death_watch.haldo", spawnedCreature);
        }
        else if (randCounter == 3)
        {
            float xCoord = -44.4f;
            float yCoord = -60f;
            float zCoord = -128.6f;
            location myself = getLocation(self);
            String planet = myself.area;
            obj_id room = getCellId(top, "largeroom48");
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object("mand_bunker_crazed_miner", spawnPoint);
            setObjVar(top, "death_watch.haldo", spawnedCreature);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMissionCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (hasObjVar(structure, "death_watch.haldo"))
        {
            removeObjVar(structure, "death_watch.haldo");
            removeObjVar(structure, "death_watch.haldo_player");
        }
        if (hasObjVar(structure, "death_watch.water_pressure_mission"))
        {
            removeObjVar(structure, "death_watch.water_pressure_mission");
        }
        return SCRIPT_CONTINUE;
    }
}
