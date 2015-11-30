package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.fs_dyn_village;
import script.library.trace;
import script.library.utils;
import script.library.locations;

public class fs_counterstrike extends script.base_script
{
    public fs_counterstrike()
    {
    }
    public static final String MOUSE_DROID_NAME_ID = "name_mdroid_";
    public static final String SHIELD_TR_VOLUME = "fs_camp.tr_volume";
    public static final String CAMP_DOOR_TEMPLATE = "object/installation/battlefield/destructible/bfield_base_gate_impl.iff";
    public static final String CAMP_ANTENNA_TEMPLATE = "object/installation/battlefield/destructible/antenna_tatt_style_1.iff";
    public static final String CAMP_TURRET_TEMPLATE = "object/installation/turret/generic_turret_block_sm.iff";
    public static final String CAMP_TENT_TEMPLATE = "object/static/structure/corellia/corl_tent_hut_s01.iff";
    public static final float SHIELD_RADIUS = 50.0f;
    public static final float SHIELD_REMOTE_RANGE = 75.0f;
    public static final int MAX_DROIDS_FOR_CAMP = 5;
    public static final float MIN_DROID_DISTANCE = 75.0f;
    public static final float MAX_DROID_DISTANCE = 100.0f;
    public static final float CAMP_SHIELD_REBOOT_TIME = 7200.0f;
    public static final int CAMP_BOSS_DEATH_TIME = 3600;
    public static final int CAMP_REINFORCEMENT_MIN = 90;
    public static final int CAMP_REINFORCEMENT_MAX = 180;
    public static final int CAMP_LOOT_SPAWN_INTERVAL = 700;
    public static final int NUM_LOOT_SPAWNS_PER_CAMP = 2;
    public static final float MIN_LOOT_SPAWN_DISTANCE = 200f;
    public static final float MAX_LOOT_SPAWN_DISTANCE = 300f;
    public static final String DT_TABLE_NAME = "datatables/fs_quests/fs_counterstrike.iff";
    public static final String DT_TABLE_POI_NAME = "datatables/theater/fs_quest_combat3/fs_quest_combat3.iff";
    public static final String DT_COL_CAMP_NUM = "camp_number";
    public static final String DT_COL_CAMP_NAME = "camp_name";
    public static final String DT_COL_LOC_X = "loc_x";
    public static final String DT_COL_LOC_Y = "loc_y";
    public static final String DT_COL_LOC_Z = "loc_z";
    public static final String DT_COL_CAMP_RADIUS = "camp_radius";
    public static final String OBJVAR_CAMP_NAME = "fs_cs.camp.name";
    public static final String OBJVAR_MY_CAMP_ID = "fs_cs.camp.id";
    public static final String OBJVAR_CREATED_CAMP_IDS = "fs_cs.camps.created.ids";
    public static final String OBJVAR_CREATED_CAMP_LOCS = "fs_cs.camps.created.locs";
    public static final String OBJVAR_PHASE_CAMP_NAMES = "fs_cs.camps.hints.names";
    public static final String OBJVAR_PHASE_CAMP_LOC_HINTS = "fs_cs.camps.hints.locs";
    public static final String OBJVAR_CAMP_DOOR1 = "fs_cs.camp.door1";
    public static final String OBJVAR_CAMP_DOOR1_LOC = "fs_cs.camp.door1Loc";
    public static final String OBJVAR_CAMP_DOOR1_YAW = "fs_cs.camp.door1Yaw";
    public static final String OBJVAR_CAMP_DOOR2 = "fs_cs.camp.door2";
    public static final String OBJVAR_CAMP_DOOR2_LOC = "fs_cs.camp.door2Loc";
    public static final String OBJVAR_CAMP_DOOR2_YAW = "fs_cs.camp.door2Yaw";
    public static final String OBJVAR_CAMP_TURRET1 = "fs_cs.camp.turret1";
    public static final String OBJVAR_CAMP_TURRET1_LOC = "fs_cs.camp.turret1Loc";
    public static final String OBJVAR_CAMP_TURRET1_YAW = "fs_cs.camp.turret1Yaw";
    public static final String OBJVAR_CAMP_TURRET2 = "fs_cs.camp.turret2";
    public static final String OBJVAR_CAMP_TURRET2_LOC = "fs_cs.camp.turret2Loc";
    public static final String OBJVAR_CAMP_TURRET2_YAW = "fs_cs.camp.turret2Yaw";
    public static final String OBJVAR_CAMP_TURRET3 = "fs_cs.camp.turret3";
    public static final String OBJVAR_CAMP_TURRET3_LOC = "fs_cs.camp.turret3Loc";
    public static final String OBJVAR_CAMP_TURRET3_YAW = "fs_cs.camp.turret3Yaw";
    public static final String OBJVAR_CAMP_TENT_LOC = "fs_cs.camp.tentLoc";
    public static final String OBJVAR_CAMP_COMMANDER = "fs_cs.camp.commander";
    public static final String OBJVAR_CAMP_COMMANDER_TAKER = "fs_cs.camp.commanderTakers";
    public static final String OBJVAR_CAMP_SHIELD_KILLER = "fs_cs.camp.shieldKiller";
    public static final String OBJVAR_CAMP_ANTENNA = "fs_cs.camp.antenna";
    public static final String OBJVAR_CAMP_ANTENNA_LOC = "fs_cs.camp.antennaLoc";
    public static final String OBJVAR_CAMP_OBJS = "fs_cs.camp.allObjs";
    public static final String OBJVAR_CAMP_DROIDS = "fs_cs.camp.droids";
    public static final String OBJVAR_IS_REMOTE_RECEIVER = "fs_cs.camp.isReceiver";
    public static final String OBJVAR_STOP_DEFENSE_SPAWN = "fs_cs.camp.stopDefenses";
    public static final String OBJVAR_CAMP_GUARDS_KILLED = "fs_cs.campGuardKilled";
    public static final String OBJVAR_BORN_ON = "fs_cs.creationDate";
    public static final int MAX_NUM_CAMPS_PER_CYCLE = 20;
    public static void doCampLootSpawn(obj_id camp) throws InterruptedException
    {
        location here = getLocation(camp);
        location where = utils.getRandomAwayLocation(here, MIN_LOOT_SPAWN_DISTANCE, MAX_LOOT_SPAWN_DISTANCE);
        obj_id spawner = null;
        long seed = 0;
        for (int i = 0; i < NUM_LOOT_SPAWNS_PER_CAMP; i++)
        {
            spawner = quests.createSpawner("fs_counterstrike_loot", where, fs_dyn_village.CITY_BAD_GUY_SPAWN_TABLE, camp);
            if (isIdValid(spawner))
            {
                setObjVar(camp, "fs_cs.lastLootSpawnCreated", where);
                seed = spawner.getValue();
                while (seed > Integer.MAX_VALUE)
                {
                    seed /= 2;
                }
                trace.log(fs_dyn_village.LOG_CHAN, "Created camp loot spawner " + spawner + " at " + where.toString());
            }
            reseed((int)seed);
        }
        messageTo(camp, "msgDoLootSpawn", null, (float)CAMP_LOOT_SPAWN_INTERVAL, false);
    }
    public static void resetPlayer(obj_id player) throws InterruptedException
    {
        String[] possibleSteps = new String[]
        {
            "fs_cs_intro",
            "fs_cs_kill5_guards",
            "fs_cs_ensure_capture",
            "fs_cs_last_chance",
            "fs_cs_escort_commander_pri",
            "fs_cs_escort_commander_sec",
            "fs_cs_quest_done",
            "fs_cs_quest_failed_escort"
        };
        obj_id questGiver = null;
        for (int i = 0; i < possibleSteps.length; i++)
        {
            if (quests.isActive(possibleSteps[i], player))
            {
                trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::resetPlayer: -> System removed player from quest and quest step " + possibleSteps[i] + ". Quest timed out.", player, trace.TL_CS_LOG);
                questGiver = getCurrentQuestGiver(player, possibleSteps[i]);
                quests.deactivate(possibleSteps[i], player);
            }
        }
        for (int x = 0; x < possibleSteps.length; x++)
        {
            if (quests.isComplete(possibleSteps[x], player))
            {
                clearCompletedQuest(player, quests.getQuestId(possibleSteps[x]));
            }
        }
        if (hasObjVar(player, "fs_cs.returnCommanderWaypoint"))
        {
            destroyWaypointInDatapad(getObjIdObjVar(player, "fs_cs.returnCommanderWaypoint"), player);
        }
        removeObjVar(player, "fs_cs");
        if (!hasScript(player, "systems.fs_quest.fs_cs_player"))
        {
            attachScript(player, "systems.fs_quest.fs_cs_player");
        }
        return;
    }
    public static void resetPlayerToStart(obj_id player, obj_id questGiver) throws InterruptedException
    {
        String[] possibleSteps = new String[]
        {
            "fs_cs_intro",
            "fs_cs_kill5_guards",
            "fs_cs_ensure_capture",
            "fs_cs_last_chance",
            "fs_cs_escort_commander_pri",
            "fs_cs_escort_commander_sec",
            "fs_cs_quest_done",
            "fs_cs_quest_failed_escort"
        };
        for (int i = 0; i < possibleSteps.length; i++)
        {
            if (quests.isActive(possibleSteps[i], player))
            {
                trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::resetPlayerToStart: -> Player was still in active quest step " + possibleSteps[i], player, trace.TL_WARNING);
                questGiver = getCurrentQuestGiver(player, possibleSteps[i]);
                quests.complete(possibleSteps[i], player, false);
            }
            clearCompletedQuest(player, quests.getQuestId(possibleSteps[i]));
        }
        destroyCommanderRescueWaypoint(player);
        removeObjVar(player, "fs_cs");
        quests.activate("fs_cs_intro", player, questGiver);
        return;
    }
    public static void destroyCommanderRescueWaypoint(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "fs_cs.returnCommanderWaypoint"))
        {
            destroyWaypointInDatapad(getObjIdObjVar(player, "fs_cs.returnCommanderWaypoint"), player);
            removeObjVar(player, "fs_cs.returnCommanderWaypoint");
        }
        return;
    }
    public static boolean isShortCommanderRescue() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "FS_P3_ShortCommanderRescue");
        if (config != null)
        {
            if ((toLower(config)).equals("true") || config.equals("1"))
            {
                return true;
            }
        }
        return false;
    }
    public static float getCampResetTime() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "FS_P3_CampResetTime");
        if (config == null)
        {
            return CAMP_SHIELD_REBOOT_TIME;
        }
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return (float)value;
        }
        return CAMP_SHIELD_REBOOT_TIME;
    }
    public static float getCampBossDeathTime() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "FS_P3_CampBossDeathTime");
        if (config == null)
        {
            return CAMP_BOSS_DEATH_TIME;
        }
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return (float)value;
        }
        return CAMP_BOSS_DEATH_TIME;
    }
    public static void markPhaseItemCreated(obj_id item) throws InterruptedException
    {
        setObjVar(item, OBJVAR_BORN_ON, getGameTime());
        return;
    }
    public static void checkPhaseItemDisable(obj_id item, int phase) throws InterruptedException
    {
        if (getPhaseItemPercentDecay(item, phase) >= 100)
        {
            setCondition(item, CONDITION_DISABLED);
        }
        return;
    }
    public static int getPhaseItemPercentDecay(obj_id item, int phase) throws InterruptedException
    {
        if (!hasObjVar(item, OBJVAR_BORN_ON))
        {
            return 0;
        }
        int bornOn = getIntObjVar(item, OBJVAR_BORN_ON);
        int now = getGameTime();
        float phaseDuration = fs_dyn_village.getPhaseDuration(phase);
        float itemAge = now - bornOn;
        int percentDecay = (int)((itemAge / phaseDuration) * 100);
        if (percentDecay > 100)
        {
            percentDecay = 100;
        }
        return percentDecay;
    }
    public static Vector getAllCampHintsFromDT() throws InterruptedException
    {
        int numRows = dataTableGetNumRows(DT_TABLE_NAME);
        Vector locs = new Vector();
        Vector names = new Vector();
        dictionary curRow = null;
        location loc = null;
        for (int i = 0; i < numRows; i++)
        {
            curRow = dataTableGetRow(DT_TABLE_NAME, i);
            if (!curRow.containsKey("camp_names") || !curRow.containsKey("locs_x") || !curRow.containsKey("locs_y") || !curRow.containsKey("locs_z"))
            {
                trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::getAllFortHintsFromDT: -> Bad data table data. Skipping row " + i, null, trace.TL_ERROR_LOG);
                continue;
            }
            names.add(curRow.getString("camp_names"));
            loc = new location(curRow.getFloat("locs_x"), curRow.getFloat("locs_y"), curRow.getFloat("locs_z"));
            locs.add(loc);
        }
        Vector rslt = new Vector();
        rslt.add(locs);
        rslt.add(names);
        return rslt;
    }
    public static int getMaxCampsPerCycle() throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "FS_MaxPhase3EnemyCamps");
        int value = utils.stringToInt(config);
        if (value > 0)
        {
            return value;
        }
        return MAX_NUM_CAMPS_PER_CYCLE;
    }
    public static boolean isOnCsQuest(obj_id player) throws InterruptedException
    {
        return quests.isActive("fs_cs_intro", player) || quests.isActive("fs_cs_kill5_guards", player) || quests.isActive("fs_cs_ensure_capture", player) || quests.isActive("fs_cs_last_chance", player) || quests.isActive("fs_cs_escort_commander_sec", player) || quests.isActive("fs_cs_quest_done", player) || quests.isActive("fs_cs_escort_commander_pri", player);
    }
    public static void pickAndWriteCycleNamesAndLocs(obj_id villageMaster) throws InterruptedException
    {
        trace.log(fs_dyn_village.LOG_CHAN, "Updating location hints and names used for this cycle.");
        Vector phaseNames = new Vector();
        Vector phaseLocs = new Vector();
        if (hasObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES))
        {
            phaseNames = getResizeableStringArrayObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES);
        }
        if (hasObjVar(villageMaster, OBJVAR_PHASE_CAMP_LOC_HINTS))
        {
            phaseLocs = getResizeableLocationArrayObjVar(villageMaster, OBJVAR_PHASE_CAMP_LOC_HINTS);
        }
        if (phaseNames == null || phaseLocs == null || phaseNames.size() != phaseLocs.size())
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::pickAndWriteCycleNamesAndLocs: created camp names.length=" + phaseNames.size() + ", locs=" + phaseLocs.size() + ".", villageMaster, trace.TL_ERROR_LOG);
            return;
        }
        int maxCamps = getMaxCampsPerCycle();
        while (phaseNames.size() > maxCamps)
        {
            phaseNames.removeElementAt(0);
            phaseLocs.removeElementAt(0);
        }
        Vector campsDat = getAllCampHintsFromDT();
        if (campsDat.size() != 2)
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::pickAndWriteCycleNamesAndLocs: -> getAllCampHints returned badly structured data. Camps not determine camp info for this cycle.", null, trace.TL_ERROR_LOG);
            return;
        }
        Vector locs = (Vector)campsDat.get(0);
        Vector names = (Vector)campsDat.get(1);
        trace.log(fs_dyn_village.LOG_CHAN, "DT has " + locs.size() + " locs and " + names.size() + " names.");
        int idx = -1;
        for (int i = 0; i < phaseNames.size(); i++)
        {
            idx = names.indexOf(phaseNames.get(i));
            if (idx > -1)
            {
                names.removeElementAt(idx);
            }
            idx = locs.indexOf(phaseLocs.get(i));
            if (idx > -1)
            {
                locs.removeElementAt(idx);
            }
        }
        for (int x = 0; phaseNames.size() < maxCamps && names.size() > 0; x++)
        {
            idx = rand(0, locs.size() - 1);
            phaseLocs.add(locs.get(idx));
            trace.log(fs_dyn_village.LOG_CHAN, "Camp LOC " + phaseLocs.size() + " for this cycle is " + (locs.get(idx)).toString());
            locs.removeElementAt(idx);
            reseed(getGameTime() + idx + 222);
            idx = rand(0, names.size() - 1);
            phaseNames.add(names.get(idx));
            trace.log(fs_dyn_village.LOG_CHAN, "Camp NAME " + phaseNames.size() + " for this cycle is " + (names.get(idx)).toString());
            names.removeElementAt(idx);
            reseed(getGameTime() + idx + 777);
        }
        trace.log(fs_dyn_village.LOG_CHAN, "_picknames: phaseNames.size()=" + phaseNames.size() + ", phaseLocs.size()=" + phaseLocs.size());
        setObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES, phaseNames);
        setObjVar(villageMaster, OBJVAR_PHASE_CAMP_LOC_HINTS, phaseLocs);
        return;
    }
    public static void initializeEnemyCamps(obj_id villageMaster) throws InterruptedException
    {
        trace.log(fs_dyn_village.LOG_CHAN, "Init Enemy Camps.");
        pickAndWriteCycleNamesAndLocs(villageMaster);
        _createEnemyCamps(villageMaster);
        return;
    }
    public static void _createEnemyCamps(obj_id villageMaster) throws InterruptedException
    {
        trace.log(fs_dyn_village.LOG_CHAN, "Creating Enemy Camps from hint data.");
        Vector phaseLocs = new Vector();
        Vector phaseNames = new Vector();
        if (hasObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES))
        {
            phaseNames = getResizeableStringArrayObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES);
        }
        if (hasObjVar(villageMaster, OBJVAR_PHASE_CAMP_LOC_HINTS))
        {
            phaseLocs = getResizeableLocationArrayObjVar(villageMaster, OBJVAR_PHASE_CAMP_LOC_HINTS);
        }
        trace.log(fs_dyn_village.LOG_CHAN, "_createEnemeyCamps: phaseNames null=" + (phaseNames == null));
        trace.log(fs_dyn_village.LOG_CHAN, "_createEnemeyCamps: phaseLocs null=" + (phaseLocs == null));
        if (phaseNames == null || phaseLocs == null || phaseNames.size() != phaseLocs.size())
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::_createEnemyCamps: -> phaseNames.size != phaseLocs.size - cannot create enemy camp.  Data mismatch.", villageMaster, trace.TL_ERROR_LOG);
            return;
        }
        for (int i = 0; i < phaseLocs.size(); i++)
        {
            boolean rslt = createRemoteTheater(DT_TABLE_POI_NAME, (location)phaseLocs.get(i), "systems.fs_quest.fs_outpost_master", villageMaster, (String)phaseNames.get(i), TLT_none);
            trace.log(fs_dyn_village.LOG_CHAN, "Attempting (" + rslt + ") to create theatre " + (String)phaseNames.get(i) + " at idx " + i + " at location " + (location)phaseLocs.get(i));
        }
        return;
    }
    public static void theatreDestroyed(obj_id villageMaster, obj_id destroyedCamp) throws InterruptedException
    {
        return;
    }
    public static String setLocForCamp(obj_id villageMaster, obj_id campId, location loc, String campName) throws InterruptedException
    {
        String rslt = "";
        if (!isIdValid(villageMaster) || !exists(villageMaster))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::setLocForCamp: -> villageMaster is invalid or doesn't exist. location not set for campId " + campId + ".", villageMaster, trace.TL_ERROR_LOG);
            return rslt;
        }
        Vector names = new Vector();
        if (hasObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES))
        {
            names = getResizeableStringArrayObjVar(villageMaster, OBJVAR_PHASE_CAMP_NAMES);
        }
        int nameIdx = names.indexOf(campName);
        if (nameIdx < 0)
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::setLocForCamp: -> could not find camp name " + campName + ", not registering camp " + campId + ".", villageMaster, trace.TL_ERROR_LOG);
            return rslt;
        }
        location[] locs = new location[names.size()];
        obj_id[] campIds = new obj_id[names.size()];
        if (utils.hasScriptVar(villageMaster, OBJVAR_CREATED_CAMP_IDS))
        {
            campIds = utils.getObjIdArrayScriptVar(villageMaster, OBJVAR_CREATED_CAMP_IDS);
            if (campIds == null || campIds.length != names.size())
            {
                utils.removeScriptVar(villageMaster, OBJVAR_CREATED_CAMP_IDS);
                campIds = new obj_id[names.size()];
            }
        }
        if (utils.hasScriptVar(villageMaster, OBJVAR_CREATED_CAMP_LOCS))
        {
            locs = utils.getLocationArrayScriptVar(villageMaster, OBJVAR_CREATED_CAMP_LOCS);
            if (locs == null || locs.length != names.size())
            {
                utils.removeScriptVar(villageMaster, OBJVAR_CREATED_CAMP_LOCS);
                locs = new location[names.size()];
            }
        }
        if (isIdValid(campIds[nameIdx]))
        {
            return "";
        }
        trace.log(fs_dyn_village.LOG_CHAN, "Registering " + campId + " as " + campName + " at location " + loc.toString());
        locs[nameIdx] = loc;
        campIds[nameIdx] = campId;
        utils.setScriptVar(villageMaster, OBJVAR_CREATED_CAMP_LOCS, locs);
        utils.setScriptVar(villageMaster, OBJVAR_CREATED_CAMP_IDS, campIds);
        rslt = (String)names.get(nameIdx);
        return rslt;
    }
    public static void failQuestForCampers(obj_id campId) throws InterruptedException
    {
        obj_id commander = null;
        if (hasObjVar(campId, OBJVAR_CAMP_COMMANDER))
        {
            commander = getObjIdObjVar(campId, OBJVAR_CAMP_COMMANDER);
            messageTo(commander, "msgLifeExpired", null, 0.0f, false);
            return;
        }
        obj_id shieldKiller = null;
        if (hasObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER))
        {
            shieldKiller = getObjIdObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER);
            messageTo(shieldKiller, "msgShieldPowerup", null, 0.0f, false);
        }
        return;
    }
    public static void resetCamp(obj_id campId, boolean recreate) throws InterruptedException
    {
        trace.log(fs_dyn_village.LOG_CHAN, "Resetting camp " + campId);
        failQuestForCampers(campId);
        if (!hasObjVar(campId, OBJVAR_CAMP_DOOR1) || !hasObjVar(campId, OBJVAR_CAMP_DOOR1_LOC) || !hasObjVar(campId, OBJVAR_CAMP_DOOR1_YAW) || !hasObjVar(campId, OBJVAR_CAMP_DOOR2) || !hasObjVar(campId, OBJVAR_CAMP_DOOR2_LOC) || !hasObjVar(campId, OBJVAR_CAMP_DOOR2_YAW) || !hasObjVar(campId, OBJVAR_CAMP_TURRET1) || !hasObjVar(campId, OBJVAR_CAMP_TURRET1_LOC) || !hasObjVar(campId, OBJVAR_CAMP_TURRET1_YAW) || !hasObjVar(campId, OBJVAR_CAMP_TURRET2) || !hasObjVar(campId, OBJVAR_CAMP_TURRET2_LOC) || !hasObjVar(campId, OBJVAR_CAMP_TURRET2_YAW) || !hasObjVar(campId, OBJVAR_CAMP_TURRET3) || !hasObjVar(campId, OBJVAR_CAMP_TURRET3_LOC) || !hasObjVar(campId, OBJVAR_CAMP_TURRET3_YAW) || !hasObjVar(campId, OBJVAR_CAMP_ANTENNA) || !hasObjVar(campId, OBJVAR_CAMP_ANTENNA_LOC))
        {
            destroyObject(campId);
            return;
        }
        erectShield();
        obj_id turret1 = getObjIdObjVar(campId, OBJVAR_CAMP_TURRET1);
        if (isIdValid(turret1) && exists(turret1))
        {
            destroyObject(turret1);
        }
        if (recreate)
        {
            turret1 = create.object(CAMP_TURRET_TEMPLATE, getLocationObjVar(campId, OBJVAR_CAMP_TURRET1_LOC));
            if (isIdValid(turret1))
            {
                setYaw(turret1, getFloatObjVar(campId, OBJVAR_CAMP_TURRET1_YAW));
                setObjVar(campId, OBJVAR_CAMP_TURRET1, turret1);
                setInvulnerable(turret1, false);
                setObjVar(turret1, turret.OBJVAR_CAN_ATTACK, "allPlayers");
            }
            else 
            {
                destroyObject(campId);
            }
        }
        obj_id turret2 = getObjIdObjVar(campId, OBJVAR_CAMP_TURRET2);
        if (isIdValid(turret2) && exists(turret2))
        {
            destroyObject(turret2);
        }
        if (recreate)
        {
            turret2 = create.object(CAMP_TURRET_TEMPLATE, getLocationObjVar(campId, OBJVAR_CAMP_TURRET2_LOC));
            if (isIdValid(turret2))
            {
                setYaw(turret2, getFloatObjVar(campId, OBJVAR_CAMP_TURRET2_YAW));
                setObjVar(campId, OBJVAR_CAMP_TURRET2, turret2);
                setInvulnerable(turret2, false);
                setObjVar(turret2, turret.OBJVAR_CAN_ATTACK, "allPlayers");
            }
            else 
            {
                destroyObject(campId);
            }
        }
        obj_id turret3 = getObjIdObjVar(campId, OBJVAR_CAMP_TURRET3);
        if (isIdValid(turret3) && exists(turret3))
        {
            destroyObject(turret3);
        }
        if (recreate)
        {
            turret3 = create.object(CAMP_TURRET_TEMPLATE, getLocationObjVar(campId, OBJVAR_CAMP_TURRET3_LOC));
            if (isIdValid(turret3))
            {
                setYaw(turret3, getFloatObjVar(campId, OBJVAR_CAMP_TURRET3_YAW));
                setObjVar(campId, OBJVAR_CAMP_TURRET3, turret3);
                setInvulnerable(turret3, false);
                setObjVar(turret3, turret.OBJVAR_CAN_ATTACK, "allPlayers");
            }
            else 
            {
                destroyObject(campId);
            }
        }
        obj_id door1 = getObjIdObjVar(campId, OBJVAR_CAMP_DOOR1);
        if (isIdValid(door1) && exists(door1))
        {
            destroyObject(door1);
        }
        if (recreate)
        {
            door1 = create.object(CAMP_DOOR_TEMPLATE, getLocationObjVar(campId, OBJVAR_CAMP_DOOR1_LOC));
            if (isIdValid(door1))
            {
                setYaw(door1, getFloatObjVar(campId, OBJVAR_CAMP_DOOR1_YAW));
                setObjVar(campId, OBJVAR_CAMP_DOOR1, door1);
                setInvulnerable(door1, true);
                detachScript(door1, "systems.battlefield.destructible_building");
                attachScript(door1, "systems.fs_quest.destructible_obj");
            }
            else 
            {
                destroyObject(campId);
            }
        }
        obj_id door2 = getObjIdObjVar(campId, OBJVAR_CAMP_DOOR2);
        if (isIdValid(door2) && exists(door2))
        {
            destroyObject(door2);
        }
        if (recreate)
        {
            door2 = create.object(CAMP_DOOR_TEMPLATE, getLocationObjVar(campId, OBJVAR_CAMP_DOOR2_LOC));
            if (isIdValid(door1))
            {
                setYaw(door2, getFloatObjVar(campId, OBJVAR_CAMP_DOOR2_YAW));
                setObjVar(campId, OBJVAR_CAMP_DOOR2, door2);
                setInvulnerable(door2, true);
                detachScript(door2, "systems.battlefield.destructible_building");
                attachScript(door2, "systems.fs_quest.destructible_obj");
            }
            else 
            {
                destroyObject(campId);
            }
        }
        obj_id antenna = getObjIdObjVar(campId, OBJVAR_CAMP_ANTENNA);
        if (isIdValid(antenna) && exists(antenna))
        {
            destroyObject(antenna);
        }
        if (recreate)
        {
            antenna = create.object(CAMP_ANTENNA_TEMPLATE, getLocationObjVar(campId, OBJVAR_CAMP_ANTENNA_LOC));
            if (isIdValid(antenna))
            {
                setObjVar(campId, OBJVAR_CAMP_ANTENNA, antenna);
                setInvulnerable(antenna, true);
                detachScript(antenna, "systems.battlefield.destructible_building");
                attachScript(antenna, "systems.fs_quest.destructible_obj");
            }
            else 
            {
                destroyObject(campId);
            }
        }
        if (hasObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER))
        {
            removeObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER);
        }
        if (hasObjVar(campId, OBJVAR_STOP_DEFENSE_SPAWN))
        {
            removeObjVar(campId, OBJVAR_STOP_DEFENSE_SPAWN);
        }
        if (hasObjVar(campId, OBJVAR_CAMP_COMMANDER))
        {
            removeObjVar(campId, OBJVAR_CAMP_COMMANDER);
        }
        if (hasObjVar(campId, OBJVAR_CAMP_COMMANDER_TAKER))
        {
            removeObjVar(campId, OBJVAR_CAMP_COMMANDER_TAKER);
        }
        return;
    }
    public static boolean antennaExists(obj_id campId) throws InterruptedException
    {
        boolean rslt = false;
        if (hasObjVar(campId, OBJVAR_CAMP_ANTENNA))
        {
            obj_id antenna = getObjIdObjVar(campId, OBJVAR_CAMP_ANTENNA);
            if (isIdValid(antenna) && exists(antenna))
            {
                rslt = true;
            }
        }
        return rslt;
    }
    public static String[] getSpawnWaves(obj_id campId) throws InterruptedException
    {
        int numPlayers = 1;
        obj_id[] players = getPlayerCreaturesInRange(campId, 100.0f);
        if (players != null)
        {
            numPlayers = players.length;
        }
        String[] spawnWaves = new String[]
        {
            "fs_counterstrike_small"
        };
        if (numPlayers > 10)
        {
            if (antennaExists(campId))
            {
                spawnWaves = new String[]
                {
                    "fs_counterstrike_small",
                    "fs_counterstrike_medium"
                };
            }
            else 
            {
                spawnWaves = new String[]
                {
                    "fs_counterstrike_medium"
                };
            }
        }
        if (numPlayers > 20)
        {
            if (antennaExists(campId))
            {
                spawnWaves = new String[]
                {
                    "fs_counterstrike_small",
                    "fs_counterstrike_small",
                    "fs_counterstrike_medium"
                };
            }
            else 
            {
                spawnWaves = new String[]
                {
                    "fs_counterstrike_small",
                    "fs_counterstrike_medium"
                };
            }
        }
        if (numPlayers > 30)
        {
            if (antennaExists(campId))
            {
                spawnWaves = new String[]
                {
                    "fs_counterstrike_small",
                    "fs_counterstrike_small",
                    "fs_counterstrike_medium",
                    "fs_counterstrike_medium"
                };
            }
            else 
            {
                spawnWaves = new String[]
                {
                    "fs_counterstrike_small",
                    "fs_counterstrike_small",
                    "fs_counterstrike_medium"
                };
            }
        }
        return spawnWaves;
    }
    public static void doCampDefenseSpawn(obj_id campId, boolean scheduleNext) throws InterruptedException
    {
        if (hasObjVar(campId, OBJVAR_STOP_DEFENSE_SPAWN) || !hasObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER))
        {
            return;
        }
        if (!isIdValid(campId) || !exists(campId))
        {
            return;
        }
        long seed = campId.getValue();
        while (seed > Integer.MAX_VALUE)
        {
            seed /= 2;
        }
        reseed((int)seed);
        String wave = "";
        float heading = 0.0f;
        location spawnerLoc = null;
        location masterLoc = getLocation(campId);
        obj_id spawner = null;
        String[] spawnWaves = getSpawnWaves(campId);
        for (int i = 0; i < spawnWaves.length; i++)
        {
            wave = spawnWaves[i];
            spawnerLoc = utils.getRandomAwayLocation(masterLoc, 50.0f, 110.0f);
            spawner = quests.createSpawner(wave, spawnerLoc, fs_dyn_village.CITY_BAD_GUY_SPAWN_TABLE, campId);
            if (isIdValid(spawner))
            {
                seed = spawner.getValue();
                while (seed > Integer.MAX_VALUE)
                {
                    seed /= 2;
                }
                setObjVar(spawner, OBJVAR_MY_CAMP_ID, campId);
            }
            reseed((int)seed);
        }
        if (scheduleNext)
        {
            messageTo(campId, "msgDoCampDefenseSpawn", null, (float)rand(CAMP_REINFORCEMENT_MIN, CAMP_REINFORCEMENT_MAX), false);
        }
    }
    public static void erectShield() throws InterruptedException
    {
        obj_id me = getSelf();
        if (!hasScript(me, "systems.fs_quest.fs_outpost_master"))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::erectShield: -> can only erect shields for enemy camps. " + me + " is not a camp master.", me, trace.TL_ERROR_LOG);
            return;
        }
        location here = getLocation(me);
        if (!hasTriggerVolume(me, SHIELD_TR_VOLUME))
        {
            createTriggerVolume(SHIELD_TR_VOLUME, SHIELD_RADIUS, true);
        }
        obj_id[] intruders = getCreaturesInRange(here, SHIELD_RADIUS + 1);
        for (int i = 0; i < intruders.length; i++)
        {
            sendSystemMessage(intruders[i], new string_id("fs_quest_village", "expel_shield"));
            if (!isGod(intruders[i]) && !hasScript(intruders[i], "systems.fs_quest.fs_camp_commander_ai"))
            {
                expelFromTriggerVolume(me, SHIELD_TR_VOLUME, intruders[i]);
            }
        }
        return;
    }
    public static boolean arePlayersInSameGroup(obj_id player1, obj_id player2) throws InterruptedException
    {
        obj_id group = getGroupObject(player1);
        if (!isIdValid(group))
        {
            return false;
        }
        obj_id[] members = getGroupMemberIds(group);
        if (utils.getElementPositionInArray(members, player2) > -1)
        {
            return true;
        }
        return false;
    }
    public static boolean attemptPowerDownShield(obj_id player, String remoteId) throws InterruptedException
    {
        if (locations.isInCity(getLocation(player)))
        {
            sendSystemMessage(player, new string_id("fs_quest_village", "shield_remote_interference"));
            return false;
        }
        if (!allowedToPowerDownShield(player))
        {
            sendSystemMessage(player, new string_id("fs_quest_village", "shield_remote_cant_use"));
            return false;
        }
        obj_id[] stuff = getNonCreaturesInRange(getLocation(player), SHIELD_REMOTE_RANGE);
        for (int t = 0; t < stuff.length; t++)
        {
            if (hasObjVar(stuff[t], OBJVAR_IS_REMOTE_RECEIVER))
            {
                obj_id campMaster = getMyOutpostId(stuff[t]);
                if (campMaster == null)
                {
                    continue;
                }
                if (!hasTriggerVolume(campMaster, SHIELD_TR_VOLUME))
                {
                    sendSystemMessage(player, new string_id("fs_quest_village", "remote_shield_down_already"));
                    return false;
                }
                if (hasObjVar(campMaster, OBJVAR_CAMP_NAME))
                {
                    if ((getStringObjVar(campMaster, OBJVAR_CAMP_NAME)).equals(remoteId))
                    {
                        setObjVar(campMaster, OBJVAR_CAMP_SHIELD_KILLER, player);
                        if (group.isGrouped(player))
                        {
                            obj_id[] gang = null;
                            gang = getGroupMemberIds(getGroupObject(player));
                            if ((gang != null) && (gang.length > 0))
                            {
                                for (int g = 0; g < gang.length; g++)
                                {
                                    obj_id thisGuy = gang[g];
                                    if (isIdValid(thisGuy) && exists(thisGuy) && thisGuy != player)
                                    {
                                        sendSystemMessage(thisGuy, new string_id("fs_quest_village", "remote_powering_down"));
                                    }
                                }
                            }
                        }
                        sendSystemMessage(player, new string_id("fs_quest_village", "remote_powering_down"));
                        messageTo(campMaster, "msgPowerDownShield", null, 0.0f, false);
                        obj_id questGiver = getCurrentQuestGiver(player, "fs_cs_intro");
                        quests.complete("fs_cs_intro", player, true);
                        quests.activate("fs_cs_kill5_guards", player, questGiver);
                        setObjVar(player, OBJVAR_MY_CAMP_ID, campMaster);
                        sendSystemMessage(player, new string_id("fs_quest_village", "fs_cs_step_intro_complete"));
                        return true;
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("fs_quest_village", "shield_remote_wrong_camp"));
                        return false;
                    }
                }
            }
        }
        sendSystemMessage(player, new string_id("fs_quest_village", "remote_nothing_happens"));
        return false;
    }
    public static boolean allowedToPowerDownShield(obj_id player) throws InterruptedException
    {
        if (quests.isActive("fs_cs_intro", player))
        {
            return true;
        }
        return false;
    }
    public static obj_id createCommander(obj_id campId) throws InterruptedException
    {
        obj_id commander = null;
        if (hasObjVar(campId, OBJVAR_CAMP_COMMANDER))
        {
            commander = getObjIdObjVar(campId, OBJVAR_CAMP_COMMANDER);
            messageTo(campId, "msgLifeExpired", null, 0.0f, false);
        }
        obj_id shieldKiller = null;
        if (hasObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER))
        {
            shieldKiller = getObjIdObjVar(campId, OBJVAR_CAMP_SHIELD_KILLER);
        }
        if (!hasObjVar(campId, OBJVAR_CAMP_TENT_LOC))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::createCommnader: -> Error creating camp commander (missing " + OBJVAR_CAMP_TENT_LOC + ") for camp " + campId + "Quest cannot complete.  Person who powered down shield was " + shieldKiller, shieldKiller, trace.TL_ERROR_LOG | trace.TL_CS_LOG);
            return null;
        }
        location here = getLocationObjVar(campId, OBJVAR_CAMP_TENT_LOC);
        commander = create.object("shadow_mercenary_nofaction", here);
        if (isIdValid(commander))
        {
            setInvulnerable(commander, true);
            setHomeLocation(commander, here);
            setMovementRun(commander);
            applySkillStatisticModifier(commander, "slope_move", 50);
            setObjVar(campId, OBJVAR_CAMP_COMMANDER, commander);
            setObjVar(commander, OBJVAR_CAMP_SHIELD_KILLER, shieldKiller);
            setObjVar(commander, OBJVAR_MY_CAMP_ID, campId);
            attachScript(commander, "systems.fs_quest.fs_camp_commander_ai");
        }
        else 
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::createCommnader: -> Error creating camp commander for camp " + campId + "Quest cannot complete.  Person who powered down shield was " + shieldKiller, shieldKiller, trace.TL_ERROR_LOG | trace.TL_CS_LOG);
        }
        return commander;
    }
    public static obj_id getCurrentQuestGiver(obj_id player, String questName) throws InterruptedException
    {
        obj_id questGiver = null;
        String objvarname = "questlib." + questName + ".quest_giver";
        if (hasObjVar(player, objvarname))
        {
            questGiver = getObjIdObjVar(player, objvarname);
        }
        return questGiver;
    }
    public static void powerDownShield() throws InterruptedException
    {
        obj_id me = getSelf();
        if (!hasScript(me, "systems.fs_quest.fs_outpost_master"))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::powerDownShield: -> can only power down shields for enemy camps. " + me + " is not a camp master.", me, trace.TL_ERROR_LOG);
            return;
        }
        location here = getLocation(me);
        if (hasTriggerVolume(me, SHIELD_TR_VOLUME))
        {
            removeTriggerVolume(SHIELD_TR_VOLUME);
        }
        if (hasObjVar(me, OBJVAR_CAMP_DOOR1))
        {
            obj_id door1 = getObjIdObjVar(me, OBJVAR_CAMP_DOOR1);
            if (isIdValid(door1))
            {
                setInvulnerable(door1, false);
            }
        }
        if (hasObjVar(me, OBJVAR_CAMP_DOOR2))
        {
            obj_id door2 = getObjIdObjVar(me, OBJVAR_CAMP_DOOR2);
            if (isIdValid(door2))
            {
                setInvulnerable(door2, false);
            }
        }
        if (hasObjVar(me, OBJVAR_CAMP_ANTENNA))
        {
            obj_id antenna = getObjIdObjVar(me, OBJVAR_CAMP_ANTENNA);
            if (isIdValid(antenna))
            {
                setInvulnerable(antenna, false);
            }
        }
        return;
    }
    public static void spawnSurveillanceDroids(obj_id campMaster) throws InterruptedException
    {
        location here = getLocation(campMaster);
        Vector existingDroids = new Vector();
        if (hasObjVar(campMaster, OBJVAR_CAMP_DROIDS))
        {
            existingDroids = utils.getResizeableObjIdBatchObjVar(campMaster, OBJVAR_CAMP_DROIDS);
        }
        for (int x = 0; x < existingDroids.size(); x++)
        {
            messageTo((obj_id)existingDroids.get(x), "msgSilentSelfDestruct", null, 0.0f, false);
        }
        Vector newDroids = new Vector();
        obj_id curDroid = null;
        for (int i = 0; i < MAX_DROIDS_FOR_CAMP; i++)
        {
            curDroid = _createSingleDroid(campMaster, here);
            if (isIdValid(curDroid))
            {
                newDroids.add(curDroid);
            }
        }
        utils.setResizeableBatchObjVar(campMaster, OBJVAR_CAMP_DROIDS, newDroids);
        return;
    }
    public static obj_id _createSingleDroid(obj_id campMaster, location here) throws InterruptedException
    {
        location where = utils.getRandomAwayLocation(here, MIN_DROID_DISTANCE, MAX_DROID_DISTANCE);
        obj_id curDroid = create.object("mouse_droid", where);
        if (isIdValid(curDroid))
        {
            setObjVar(curDroid, OBJVAR_MY_CAMP_ID, campMaster);
            String name = "test";
            if (hasObjVar(campMaster, OBJVAR_CAMP_NAME))
            {
                name = getStringObjVar(campMaster, OBJVAR_CAMP_NAME);
            }
            setName(curDroid, new string_id("fs_quest_village", MOUSE_DROID_NAME_ID + name));
            attachScript(curDroid, "systems.fs_quest.fs_surveillance_droid");
        }
        return curDroid;
    }
    public static void droidDied(obj_id campMaster, obj_id deadDroid) throws InterruptedException
    {
        Vector existingDroids = new Vector();
        if (hasObjVar(campMaster, OBJVAR_CAMP_DROIDS))
        {
            existingDroids = utils.getResizeableObjIdBatchObjVar(campMaster, OBJVAR_CAMP_DROIDS);
        }
        int idx = existingDroids.indexOf(deadDroid);
        if (idx > -1)
        {
            existingDroids.removeElementAt(idx);
        }
        obj_id newDroid = _createSingleDroid(campMaster, getLocation(campMaster));
        if (isIdValid(newDroid))
        {
            existingDroids.add(newDroid);
        }
        utils.setResizeableBatchObjVar(campMaster, OBJVAR_CAMP_DROIDS, existingDroids);
        return;
    }
    public static void registerCampObjIds(obj_id campMaster) throws InterruptedException
    {
        if (!isIdValid(campMaster))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::registerCampObjIds: -> Error scrubbing " + OBJVAR_CAMP_OBJS + " for camp objids.  Camp master is not valid.", campMaster, trace.TL_ERROR_LOG);
            return;
        }
        if (!exists(campMaster))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::registerCampObjIds: -> Error scrubbing " + OBJVAR_CAMP_OBJS + " for camp objids.  Camp master does not exist on this server.", campMaster, trace.TL_ERROR_LOG);
            return;
        }
        if (!hasObjVar(campMaster, OBJVAR_CAMP_OBJS))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::registerCampObjIds: -> Error scrubbing " + OBJVAR_CAMP_OBJS + " for camp objids.  Objvar missing.  Nuking camp.", campMaster, trace.TL_ERROR_LOG);
            destroyObject(campMaster);
        }
        obj_id[] objs = utils.getObjIdBatchObjVar(campMaster, OBJVAR_CAMP_OBJS);
        String tName = "";
        boolean foundFirstDoor = false;
        int turretNum = 1;
        for (int i = 0; i < objs.length; i++)
        {
            if (!isIdValid(objs[i]) || !exists(objs[i]))
            {
                continue;
            }
            tName = getTemplateName(objs[i]);
            setObjVar(objs[i], OBJVAR_IS_REMOTE_RECEIVER, true);
            setObjVar(objs[i], OBJVAR_MY_CAMP_ID, campMaster);
            if (tName == null)
            {
                trace.log("fs_quest", "fs_counterstrike::registerCampObjIds: -> " + objs[i] + " has no template.");
                continue;
            }
            if (tName.equals(CAMP_DOOR_TEMPLATE))
            {
                if (!foundFirstDoor)
                {
                    setObjVar(campMaster, OBJVAR_CAMP_DOOR1, objs[i]);
                    setObjVar(campMaster, OBJVAR_CAMP_DOOR1_LOC, getLocation(objs[i]));
                    setObjVar(campMaster, OBJVAR_CAMP_DOOR1_YAW, getYaw(objs[i]));
                    foundFirstDoor = true;
                }
                else 
                {
                    setObjVar(campMaster, OBJVAR_CAMP_DOOR2, objs[i]);
                    setObjVar(campMaster, OBJVAR_CAMP_DOOR2_LOC, getLocation(objs[i]));
                    setObjVar(campMaster, OBJVAR_CAMP_DOOR2_YAW, getYaw(objs[i]));
                }
                detachScript(objs[i], "systems.battlefield.destructible_building");
                attachScript(objs[i], "systems.fs_quest.destructible_obj");
                setInvulnerable(objs[i], true);
            }
            else if (tName.equals(CAMP_ANTENNA_TEMPLATE))
            {
                setObjVar(campMaster, OBJVAR_CAMP_ANTENNA, objs[i]);
                setObjVar(campMaster, OBJVAR_CAMP_ANTENNA_LOC, getLocation(objs[i]));
                detachScript(objs[i], "systems.battlefield.destructible_building");
                attachScript(objs[i], "systems.fs_quest.destructible_obj");
                setInvulnerable(objs[i], true);
            }
            else if (tName.equals(CAMP_TURRET_TEMPLATE))
            {
                setObjVar(campMaster, "fs_cs.camp.turret" + turretNum, objs[i]);
                setObjVar(campMaster, "fs_cs.camp.turret" + turretNum + "Loc", getLocation(objs[i]));
                setObjVar(campMaster, "fs_cs.camp.turret" + turretNum + "Yaw", getYaw(objs[i]));
                setObjVar(objs[i], turret.OBJVAR_CAN_ATTACK, "allPlayers");
                turretNum++;
            }
            else if (tName.equals(CAMP_TENT_TEMPLATE))
            {
                setObjVar(campMaster, OBJVAR_CAMP_TENT_LOC, getLocation(objs[i]));
            }
        }
        return;
    }
    public static void setupCamp(obj_id campMaster, boolean registerLocs) throws InterruptedException
    {
        if (!hasObjVar(campMaster, OBJVAR_CAMP_OBJS) || !hasObjVar(campMaster, OBJVAR_CAMP_NAME))
        {
            return;
        }
        if (campMaster != getSelf())
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::setupCamp: -> only camps may call this method. caller (" + getSelf() + " is not a camp.", getSelf(), trace.TL_ERROR_LOG);
            return;
        }
        if (registerLocs)
        {
            registerCampObjIds(campMaster);
        }
        erectShield();
        spawnSurveillanceDroids(campMaster);
        return;
    }
    public static obj_id getMyOutpostId(obj_id outpostObject) throws InterruptedException
    {
        if (hasObjVar(outpostObject, OBJVAR_MY_CAMP_ID))
        {
            return getObjIdObjVar(outpostObject, OBJVAR_MY_CAMP_ID);
        }
        return null;
    }
}
