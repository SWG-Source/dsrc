package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.lang.Math;
import script.library.trace;
import script.library.create;
import script.library.quests;
import script.library.player_structure;
import script.library.turret;
import script.library.fs_counterstrike;

public class fs_dyn_village extends script.base_script
{
    public fs_dyn_village()
    {
    }
    public static final String CITY_STAGES_DATATABLE = "datatables/fs_quests/fs_dyn_city.iff";
    public static final String CITY_BAD_GUY_SPAWN_TABLE = "datatables/quest/force_sensitive/base_spawn.iff";
    public static final String CITY_DESTRUCTION_COORDS = "datatables/quest/force_sensitive/village_destruction_coords.iff";
    public static final String CITY_DEFENSES_DATATABLE = "datatables/fs_quests/fs_defenses.iff";
    public static final String DT_COL_PHASE = "phase";
    public static final String DT_COL_AREA = "area";
    public static final String DT_COL_TEMPLATE = "object_template";
    public static final String DT_COL_OBJECT_X = "object_x";
    public static final String DT_COL_OBJECT_Y = "object_y";
    public static final String DT_COL_OBJECT_Z = "object_z";
    public static final String DT_COL_IS_DELTA = "is_coord_delta";
    public static final String DT_COL_HEADING = "heading";
    public static final String DT_COL_GAME_CRITICAL = "game_critical";
    public static final String DT_COL_SCRIPT = "script";
    public static final String DT_COL_PHASE_NAME = "phase_name";
    public static final String DT_COL_SPECIAL_OBJ_MOD = "special_obj_mod";
    public static final String DT_COL_WAVE_PREFIX = "fs_village_";
    public static final String DT_COL_WAVE_SMALL = "small";
    public static final String DT_COL_WAVE_MEDIUM = "medium";
    public static final String DT_COL_WAVE_LARGE = "large";
    public static final String DT_COL_WAVE_EASY = "easy";
    public static final String DT_COL_WAVE_NORMAL = "normal";
    public static final String DT_COL_WAVE_HARD = "hard";
    public static final String DT_COL_WAVE_ABYSS = "abyss";
    public static final String START_LOC = "START_LOC";
    public static final String LOG_CHAN = "fs_quests";
    public static final String OBJ_MOD_IS_SPAWNER = "spawner";
    public static final String OBJ_MOD_IS_HEALING_STRUCT = "healing";
    public static final String DYNVILLAGE_CLUSTER_NAME = "cluster.dynVillage";
    public static final String CLUSTER_SET_FS = "cluster.dynVillage.fs_data";
    public static final String CLUSTER_OBJID_KEY_MASTER = "cluster.dynVillage.masterObject";
    public static final String CLUSTER_INT_KEY_CUR_PHASE = "cluster.dynVillage.curPhase";
    public static final String CLUSTER_INT_KEY_PHASE_UID = "cluster.dynVillage.curPhaseUID";
    public static final int DATAMODE_ADD_OBJID = 1;
    public static final int DATAMODE_GET_OBJID = 2;
    public static final int DATAMODE_ADD_INT = 3;
    public static final int DATAMODE_GET_INT = 4;
    public static final int VILLAGE_MIN_ENEMY_RADIUS = 150;
    public static final int VILLAGE_MAX_ENEMY_RADIUS = 250;
    public static final int VILLAGE_SMALL_WAVE = 1;
    public static final int VILLAGE_MEDIUM_WAVE = 2;
    public static final int VILLAGE_LARGE_WAVE = 3;
    public static final int VILLAGE_UBER_WAVE = 4;
    public static final int VILLAGE_MEDIUM_THRESHOLD = 10;
    public static final int VILLAGE_LARGE_THRESHOLD = 20;
    public static final int VILLAGE_UBER_THRESHOLD = 35;
    public static final int VILLAGE_MIN_ENEMY_SPAWN_PULSE = 5000;
    public static final int VILLAGE_MAX_ENEMY_SPAWN_PULSE = 7000;
    public static final int VILLAGE_ENEMY_MAX_LIFE = 1200;
    public static final int DEFAULT_PHASE_DURATION = 1814400;
    public static final int MAX_PHASE_NUMBER = 4;
    public static final String TEMPLATE_NO_DRAW = "object/tangible/spawning/fs_village_npc_spawner.iff";
    public static final String SCRIPT_VILLAGE_NPC_SPAWNER = "systems.fs_quest.fs_village_npc_spawner";
    public static final String SCRIPT_DYN_VILLAGE_THING = "systems.fs_quest.fs_dynamic_village_object";
    public static final String SCRIPT_FS_DATAHANDLER = "systems.fs_quest.fs_datahandler";
    public static final String SCRIPT_FS_VILLAGE_MASTER = "systems.fs_quest.fs_village_master";
    public static final String OBJVAR_CRITICAL_OBJECT = "fs_quest.isGameCritical";
    public static final String OBJVAR_IS_HEALING_STRUCT = "healing.canhealwound";
    public static final String OBJVAR_CURRENT_PHASE = "fs_quest.phase.current";
    public static final String OBJVAR_NEXT_PHASE = "fs_quest.phase.next";
    public static final String OBJVAR_PHASE_VERSION = "fs_quest.phase.version";
    public static final String OBJVAR_PHASE_START_TIME = "fs_quest.phase.startTime";
    public static final String OBJVAR_MY_SPAWNER = "fs_quest.mySpawner";
    public static final String OBJVAR_MY_NPC_TEMPLATE = "fs_quest.myNpcTemplate";
    public static final String OBJVAR_MY_NPC_SCRIPTS = "fs_quest.myNpcScripts";
    public static final String OBJVAR_MY_SPAWNED_NPC = "fs_quest.mySpawnedNpc";
    public static final String OBJVAR_VILLAGE_SPAWNERS = "fs_quest.spawners";
    public static final String OBJVAR_VILLAGE_OBJECTS = "fs_quest.objects";
    public static final String OBJVAR_MY_MASTER_OBJECT = "fs_quest.myMaster";
    public static final String OBJVAR_LOCK_VILLAGE = "fs_quest.village.locked";
    public static final String OBJVAR_CAN_ATTACK = "turret.validTargetObjVar";
    public static final String OBJVAR_TURRET_TARGET = "fs_quest.turret.isTarget";
    public static final String OBJVAR_PLAYER_COUNT = "fs_quest.players.count";
    public static final String OBJVAR_VILLAGE_DEFENSES = "crafted_village_defenses";
    public static final String OBJVAR_VILLAGE_SHIELDS = "crafted_village_shields";
    public static final String OBJVAR_TURRET_ACCURACY = "turret_accuracy";
    public static final String OBJVAR_TURRET_SPEED = "turret_speed";
    public static final String OBJVAR_TURRET_POWER = "turret_power";
    public static final String SCRIPT_VAR_DATA_REQUEST = "fs_quest.data_request";
    public static final String SCRIPT_VAR_PARAMS_REQUEST = "fs_quest.data_request_params";
    public static final String SCRIPT_VAR_HANDLER_REQ = "fs_quest.handler_request";
    public static Vector getCityDestructionCoords(String id) throws InterruptedException
    {
        int num_items = dataTableGetNumRows(CITY_DESTRUCTION_COORDS);
        Vector loc = new Vector();
        int idx = dataTableSearchColumnForString(id, "coord_type", CITY_DESTRUCTION_COORDS);
        dictionary line = null;
        float yaw = 0.0f;
        for (int i = idx + 1; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(CITY_DESTRUCTION_COORDS, i);
            if ((row.getString("coord_type")).length() > 0)
            {
                break;
            }
            else 
            {
                line = dataTableGetRow(CITY_DESTRUCTION_COORDS, i);
                if (line == null || !line.containsKey("x") || !line.containsKey("y") || !line.containsKey("z"))
                {
                    continue;
                }
                else 
                {
                    if (line.containsKey("yaw"))
                    {
                        yaw = line.getFloat("yaw");
                    }
                    loc.add(new location(line.getFloat("x"), line.getFloat("y"), line.getFloat("z")));
                    loc.add(new Float(yaw));
                }
            }
        }
        return loc;
    }
    public static location[] getVillageLocs(String id) throws InterruptedException
    {
        Vector locs = getCityDestructionCoords(id);
        if (locs.size() < 1 || locs.size() % 2 != 0)
        {
            return new location[0];
        }
        location[] aLocs = new location[locs.size() / 2];
        int count = 0;
        for (int i = 0; i < locs.size(); i += 2)
        {
            aLocs[count] = (location)locs.get(i);
            count++;
        }
        return aLocs;
    }
    public static void checkPhaseExpiration(obj_id master) throws InterruptedException
    {
        if (!isIdValid(master) || !exists(master))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::checkPhaseExpiration: -> Village master object (" + master + ") is not valid / doesn't exist.");
            return;
        }
        if (!hasObjVar(master, OBJVAR_PHASE_START_TIME))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::checkPhaseExpiration: -> Missing phase start time.  Adding now, but will probably be incorrect.");
            setObjVar(master, OBJVAR_PHASE_START_TIME, getGameTime());
            return;
        }
        int forcedPhase = getStartupPhaseFromConfig();
        if (forcedPhase > 0)
        {
            trace.log(LOG_CHAN, "fs_dyn_village::checkPhaseExpiration: -> Village has been forced into phase " + forcedPhase + " via server config.");
            setNextPhaseAuth(master, forcedPhase);
            return;
        }
        int curPhase = getCurrentPhaseAuth(master);
        int duration = getPhaseDuration(curPhase);
        int startTime = getIntObjVar(master, OBJVAR_PHASE_START_TIME);
        if ((startTime + duration) <= getGameTime())
        {
            trace.log(LOG_CHAN, "fs_dyn_village::checkPhaseExpiration: -> Phase " + curPhase + " has expired. Moving to next phase.");
            int nextPhase = curPhase + 1;
            if (nextPhase > MAX_PHASE_NUMBER)
            {
                nextPhase = 1;
            }
            setNextPhaseAuth(master, nextPhase);
        }
        return;
    }
    public static int getPhaseDuration(int phase) throws InterruptedException
    {
        if (phase < 1 || phase > MAX_PHASE_NUMBER)
        {
            trace.log(LOG_CHAN, "fs_dyn_village::getPhaseDuration: -> Invalid phase passed in (" + phase + "). Bailing.", null, trace.TL_ERROR_LOG);
            return -1;
        }
        int duration = 0;
        String cfgDuration = getConfigSetting("GameServer", "FsVillagePhaseDuration_Phase_" + phase);
        if (cfgDuration != null && cfgDuration.length() > 0)
        {
            duration = utils.stringToInt(cfgDuration);
        }
        if (duration <= 0)
        {
            cfgDuration = getConfigSetting("GameServer", "FsVillagePhaseDuration");
            if (cfgDuration != null && cfgDuration.length() > 0)
            {
                duration = utils.stringToInt(cfgDuration);
                if (duration < 1)
                {
                    duration = DEFAULT_PHASE_DURATION;
                }
            }
        }
        if (duration <= 0)
        {
            duration = DEFAULT_PHASE_DURATION;
        }
        return duration;
    }
    public static int getStartupPhaseFromConfig() throws InterruptedException
    {
        String cfgPhase = getConfigSetting("GameServer", "FsVillage_ForcePhase");
        int phase = -1;
        if (cfgPhase != null && cfgPhase.length() > 0)
        {
            phase = utils.stringToInt(cfgPhase);
        }
        if (phase < 1 || phase > MAX_PHASE_NUMBER)
        {
            phase = -1;
        }
        return phase;
    }
    public static void destroySpawnersNpc(obj_id spawner) throws InterruptedException
    {
        if (!isIdValid(spawner) || !exists(spawner))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::destroySpawnersNpc: -> Spawner " + spawner + " not valid / doesn't exist. Can't destroy npc.", spawner, trace.TL_WARNING);
            return;
        }
        if (hasObjVar(spawner, OBJVAR_MY_SPAWNED_NPC))
        {
            obj_id npc = getObjIdObjVar(spawner, OBJVAR_MY_SPAWNED_NPC);
            destroyObject(npc);
        }
        return;
    }
    public static boolean spawnNpcAuth(obj_id spawner, boolean createIfExists, boolean destroyOldIfExists) throws InterruptedException
    {
        if (!isIdValid(spawner) || !exists(spawner))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::spawnNpcAuth: -> Spawner " + spawner + " not valid / doesn't exist. Can't create npc.", spawner, trace.TL_ERROR_LOG);
            return false;
        }
        if (!hasObjVar(spawner, OBJVAR_MY_NPC_TEMPLATE) || !hasObjVar(spawner, OBJVAR_MY_NPC_SCRIPTS))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::spawnNpcAuth: -> Spawner " + spawner + " missing " + OBJVAR_MY_NPC_TEMPLATE + " or " + OBJVAR_MY_NPC_SCRIPTS + " objvar(s). Can't create npc.", spawner, trace.TL_ERROR_LOG);
            return false;
        }
        if (hasObjVar(spawner, OBJVAR_MY_SPAWNED_NPC))
        {
            obj_id curMob = getObjIdObjVar(spawner, OBJVAR_MY_SPAWNED_NPC);
            if (isIdValid(curMob) && exists(curMob))
            {
                if (createIfExists)
                {
                    if (destroyOldIfExists)
                    {
                        destroyObject(curMob);
                    }
                }
                else 
                {
                    return true;
                }
            }
        }
        String template = getStringObjVar(spawner, OBJVAR_MY_NPC_TEMPLATE);
        obj_id mob = create.object(template, getLocation(spawner));
        if (!isIdValid(mob))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::spawnNpcAuth: -> Unable to spawn " + template + " from spawner " + spawner, spawner, trace.TL_ERROR_LOG);
            return false;
        }
        setObjVar(mob, OBJVAR_MY_SPAWNER, spawner);
        setObjVar(spawner, OBJVAR_MY_SPAWNED_NPC, mob);
        setObjVar(mob, OBJVAR_MY_MASTER_OBJECT, getObjIdObjVar(spawner, OBJVAR_MY_MASTER_OBJECT));
        String scriptDat = getStringObjVar(spawner, OBJVAR_MY_NPC_SCRIPTS);
        scriptDat = scriptDat.trim();
        if (scriptDat != null && scriptDat.length() > 0)
        {
            String[] scripts = null;
            if (scriptDat.indexOf("|") > -1)
            {
                scripts = split(scriptDat, '|');
            }
            else 
            {
                scripts = new String[]
                {
                    scriptDat
                };
            }
            for (int i = 0; i < scripts.length; i++)
            {
                if (scripts[i] != null && scripts[i].length() > 0 && !scripts[i].equals("none"))
                {
                    attachScript(mob, scripts[i]);
                }
            }
        }
        setHomeLocation(mob);
        return true;
    }
    public static void spawnTurrets(obj_id master) throws InterruptedException
    {
        final float MAX_ATTRIB_VALUE = 1000.0f;
        boolean useAttributes = false;
        float kinetic_resistance = 0;
        float armor_integrity = 0;
        float armor_effectiveness = 0;
        float accuracy = 0;
        float speed = 0;
        float power = 0;
        if (hasObjVar(master, OBJVAR_VILLAGE_DEFENSES))
        {
            obj_var_list defenses = getObjVarList(master, OBJVAR_VILLAGE_DEFENSES);
            kinetic_resistance = defenses.getFloatObjVar("kinetic_resistance") / MAX_ATTRIB_VALUE;
            if (kinetic_resistance > 1.0f)
            {
                kinetic_resistance = 1.0f;
            }
            armor_integrity = defenses.getFloatObjVar("armor_integrity") / MAX_ATTRIB_VALUE;
            if (armor_integrity > 1.0f)
            {
                armor_integrity = 1.0f;
            }
            armor_effectiveness = defenses.getFloatObjVar("armor_effectiveness") / MAX_ATTRIB_VALUE;
            if (armor_effectiveness > 1.0f)
            {
                armor_effectiveness = 1.0f;
            }
            accuracy = defenses.getFloatObjVar("accuracy") / MAX_ATTRIB_VALUE;
            if (accuracy > 1.0f)
            {
                accuracy = 1.0f;
            }
            speed = defenses.getFloatObjVar("speed") / MAX_ATTRIB_VALUE;
            if (speed > 1.0f)
            {
                speed = 1.0f;
            }
            power = defenses.getFloatObjVar("power") / MAX_ATTRIB_VALUE;
            if (power > 1.0f)
            {
                power = 1.0f;
            }
            int[] attribNames = dataTableGetIntColumn(CITY_DEFENSES_DATATABLE, "attribute");
            float[] attribMins = dataTableGetFloatColumn(CITY_DEFENSES_DATATABLE, "min");
            float[] attribMaxs = dataTableGetFloatColumn(CITY_DEFENSES_DATATABLE, "max");
            if (attribNames != null && attribMins != null && attribMaxs != null && attribNames.length == attribMins.length && attribNames.length == attribMaxs.length)
            {
                int count = 0;
                for (int i = 0; i < attribNames.length; ++i)
                {
                    switch (attribNames[i])
                    {
                        case (-2012680909):
                        kinetic_resistance = attribMins[i] + (attribMaxs[i] - attribMins[i]) * kinetic_resistance;
                        LOG("fs_village", "village defenses phase 3,4 kinetic resistance = " + kinetic_resistance);
                        ++count;
                        break;
                        case (1565604133):
                        armor_integrity = attribMins[i] + (attribMaxs[i] - attribMins[i]) * armor_integrity;
                        LOG("fs_village", "village defenses phase 3,4 armor integrity = " + armor_integrity);
                        ++count;
                        break;
                        case (1067833669):
                        armor_effectiveness = attribMins[i] + (attribMaxs[i] - attribMins[i]) * armor_effectiveness;
                        LOG("fs_village", "village defenses phase 3,4 armor effectiveness = " + armor_effectiveness);
                        ++count;
                        break;
                        case (918920898):
                        accuracy = attribMins[i] + (attribMaxs[i] - attribMins[i]) * accuracy;
                        LOG("fs_village", "village defenses phase 3,4 accuracy = " + accuracy);
                        ++count;
                        break;
                        case (731299553):
                        speed = attribMins[i] + (attribMaxs[i] - attribMins[i]) * speed;
                        LOG("fs_village", "village defenses phase 3,4 speed = " + speed);
                        ++count;
                        break;
                        case (-1437509112):
                        power = attribMins[i] + (attribMaxs[i] - attribMins[i]) * power;
                        LOG("fs_village", "village defenses phase 3,4 power = " + power);
                        ++count;
                        break;
                        default:
                        CustomerServiceLog("fs_village", "WARNING: Defenses datatable has unknown attribute at index " + i);
                        break;
                    }
                }
                if (count == attribNames.length)
                {
                    useAttributes = true;
                }
            }
        }
        if (!useAttributes)
        {
            CustomerServiceLog("fs_village", "WARNING: Creating village turrets with default attributes");
        }
        Vector turretLocs = getCityDestructionCoords("village_turrets");
        if (turretLocs.size() < 2 || turretLocs.size() % 2 != 0)
        {
            return;
        }
        for (int i = 0; i < turretLocs.size(); i += 2)
        {
            obj_id oturret = createObject("object/installation/turret/fs_village_turret.iff", (location)turretLocs.get(i));
            if (isIdValid(oturret))
            {
                setObjVar(oturret, turret.OBJVAR_CAN_ATTACK, fs_dyn_village.OBJVAR_TURRET_TARGET);
                float yaw = (((Float)turretLocs.get(i + 1))).floatValue();
                setYaw(oturret, yaw);
                if (useAttributes)
                {
                    armor.removeAllArmorData(oturret);
                    armor.setAbsoluteArmorData(oturret, AL_advanced, AC_assault, (int)armor_effectiveness, (int)armor_integrity);
                    armor.setArmorSpecialProtectionPercent(oturret, 0, kinetic_resistance / 100.0f);
                    setObjVar(oturret, OBJVAR_TURRET_ACCURACY, accuracy);
                    setObjVar(oturret, OBJVAR_TURRET_SPEED, speed);
                    setObjVar(oturret, OBJVAR_TURRET_POWER, power);
                }
            }
        }
        return;
    }
    public static void doEnemySpawn(obj_id struct_master, float minDistance, float maxDistance, String[] spawnWaves) throws InterruptedException
    {
        if (!isIdValid(struct_master) || !exists(struct_master))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::doEnemySpawn: -> Error creating enemy spawn.  Structure master (" + struct_master + ") was invalid or didn't exist.", struct_master, trace.TL_ERROR_LOG);
            return;
        }
        if (spawnWaves == null || spawnWaves.length < 1)
        {
            trace.log(LOG_CHAN, "fs_dyn_village::doEnemySpawn: -> Error creating enemy spawn.  SpawnWaves is not defined.", struct_master, trace.TL_ERROR_LOG);
            return;
        }
        String wave = "";
        float heading = 0.0f;
        location spawnerLoc = null;
        location masterLoc = getLocation(struct_master);
        obj_id spawner = null;
        Vector spawnLocations = new Vector();
        spawnLocations.add(new location());
        ((location)spawnLocations.get(0)).area = "dathomir";
        ((location)spawnLocations.get(0)).x = 5240;
        ((location)spawnLocations.get(0)).z = -4335;
        spawnLocations.add(new location());
        ((location)spawnLocations.get(1)).area = "dathomir";
        ((location)spawnLocations.get(1)).x = 5374;
        ((location)spawnLocations.get(1)).z = -3947;
        spawnLocations.add(new location());
        ((location)spawnLocations.get(2)).area = "dathomir";
        ((location)spawnLocations.get(2)).x = 5415;
        ((location)spawnLocations.get(2)).z = -3959;
        spawnLocations.add(new location());
        ((location)spawnLocations.get(3)).area = "dathomir";
        ((location)spawnLocations.get(3)).x = 5331;
        ((location)spawnLocations.get(3)).z = -4361;
        spawnLocations.add(new location());
        ((location)spawnLocations.get(4)).area = "dathomir";
        ((location)spawnLocations.get(4)).x = 5187;
        ((location)spawnLocations.get(4)).z = 4369;
        Vector spawners = null;
        if (hasObjVar(struct_master, "attack_spawners"))
        {
            spawners = getResizeableObjIdArrayObjVar(struct_master, "attack_spawners");
        }
        else 
        {
            spawners = new Vector();
        }
        for (int i = 0; i < spawnWaves.length; i++)
        {
            if (i >= spawnLocations.size())
            {
                break;
            }
            wave = spawnWaves[i];
            spawnerLoc = ((location)spawnLocations.get(i));
            LOG("fs_quest", "Sending enemy spawner to " + spawnerLoc.toString());
            spawners.add(quests.createSpawner(wave, spawnerLoc, CITY_BAD_GUY_SPAWN_TABLE, struct_master));
        }
        if (spawners.size() > 0)
        {
            setObjVar(struct_master, "attack_spawners", spawners);
        }
        else 
        {
            removeObjVar(struct_master, "attack_spawners");
        }
        return;
    }
    public static int getNecessarySpawnLevel(obj_id master) throws InterruptedException
    {
        int numPlayers = numPlayersInVillage(master);
        int level = VILLAGE_SMALL_WAVE;
        if (numPlayers > VILLAGE_MEDIUM_THRESHOLD)
        {
            level = VILLAGE_MEDIUM_WAVE;
        }
        if (numPlayers > VILLAGE_LARGE_THRESHOLD)
        {
            level = VILLAGE_LARGE_WAVE;
        }
        if (numPlayers > VILLAGE_UBER_THRESHOLD)
        {
            level = VILLAGE_UBER_WAVE;
        }
        return level;
    }
    public static int numPlayersInVillage(obj_id struct_master) throws InterruptedException
    {
        int num = 0;
        if (!hasObjVar(struct_master, OBJVAR_PLAYER_COUNT))
        {
            return num;
        }
        return getIntObjVar(struct_master, OBJVAR_PLAYER_COUNT);
    }
    public static void spawnNpcAsynch(obj_id spawner, boolean createIfExists, boolean destroyOldIfExists) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("createIfExists", createIfExists);
        d.put("destroyOldIfExists", destroyOldIfExists);
        messageTo(spawner, "msgSpawnNpc", d, 0.0f, false);
        return;
    }
    public static void notifySpawnerOfDeath(obj_id deadNpc) throws InterruptedException
    {
        if (!hasObjVar(deadNpc, OBJVAR_MY_SPAWNER))
        {
            trace.log(LOG_CHAN, "fs_village_npc::notifyMasterOfDeath: -> Missing " + OBJVAR_MY_SPAWNER + " objvar.  Can't notify spawner to create a new version of " + getTemplateName(deadNpc), deadNpc, trace.TL_ERROR_LOG);
            return;
        }
        obj_id mySpawner = getObjIdObjVar(deadNpc, OBJVAR_MY_SPAWNER);
        dictionary d = new dictionary();
        d.put("victim", deadNpc);
        messageTo(mySpawner, "msgNpcDestroyed", d, 0.0f, false);
        return;
    }
    public static void setNextPhaseAuth(obj_id village_master, int value) throws InterruptedException
    {
        if (!isIdValid(village_master) || !exists(village_master))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::setNextPhaseAuth: -> Village master object (" + village_master + ") is not valid / doesn't exist.");
            return;
        }
        setObjVar(village_master, OBJVAR_NEXT_PHASE, value);
        return;
    }
    public static int getPhaseFromVersionUid(int uid) throws InterruptedException
    {
        String suid = "" + uid;
        int phase = utils.stringToInt(suid.substring(0, 1));
        if (phase < 1 || phase > MAX_PHASE_NUMBER)
        {
            return 0;
        }
        return phase;
    }
    public static int getVersionFromVersionUid(int uid) throws InterruptedException
    {
        String suid = "" + uid;
        int ver = utils.stringToInt(suid.substring(1));
        return ver;
    }
    public static int getCurrentVersionAuth(obj_id village_master) throws InterruptedException
    {
        int curVersion = 0;
        if (!isIdValid(village_master) || !exists(village_master))
        {
            return curVersion;
        }
        if (hasObjVar(village_master, OBJVAR_PHASE_VERSION))
        {
            curVersion = getIntObjVar(village_master, OBJVAR_PHASE_VERSION);
        }
        return curVersion;
    }
    public static int getCurrentPhaseAuth(obj_id village_master) throws InterruptedException
    {
        if (!isIdValid(village_master) || !exists(village_master))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::getCurrentPhase: -> Village master object (" + village_master + ") is not valid / doesn't exist.");
            return -1;
        }
        if (!hasObjVar(village_master, OBJVAR_CURRENT_PHASE))
        {
            return -1;
        }
        return getIntObjVar(village_master, OBJVAR_CURRENT_PHASE);
    }
    public static int getCurrentUidAuth(obj_id village_master) throws InterruptedException
    {
        if (!isIdValid(village_master) || !exists(village_master))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::getCurrentUidAuth: -> Village master object (" + village_master + ") is not valid / doesn't exist.");
            return -1;
        }
        int phase = getCurrentPhaseAuth(village_master);
        int version = getCurrentVersionAuth(village_master);
        if (phase == -1 || version == -1)
        {
            return -1;
        }
        String phaseString = Integer.toString(phase);
        String versionString = Integer.toString(version);
        String uidString = phaseString + versionString;
        int uid = utils.stringToInt(uidString);
        return uid;
    }
    public static void doEnemySpawnPulse(obj_id master, String msgHandler) throws InterruptedException
    {
        int level = getNecessarySpawnLevel(master);
        String[] spawnWaves = null;
        String enemyLevelString = getConfigSetting("GameServer", "fsVillageEnemyDifficulty");
        if (enemyLevelString != null && enemyLevelString.length() > 0)
        {
            level = utils.stringToInt(enemyLevelString);
        }
        switch (level)
        {
            case VILLAGE_SMALL_WAVE:
            spawnWaves = new String[]
            {
                "fs_village_easy_small",
                "fs_village_easy_small",
                "fs_village_easy_small"
            };
            break;
            case VILLAGE_MEDIUM_WAVE:
            spawnWaves = new String[]
            {
                "fs_village_easy_medium",
                "fs_village_normal_medium",
                "fs_village_normal_medium",
                "fs_village_abyss_medium"
            };
            break;
            case VILLAGE_LARGE_WAVE:
            spawnWaves = new String[]
            {
                "fs_village_normal_large",
                "fs_village_normal_large",
                "fs_village_hard_large",
                "fs_village_abyss_large"
            };
            break;
            case VILLAGE_UBER_WAVE:
            spawnWaves = new String[]
            {
                "fs_village_normal_large",
                "fs_village_hard_large",
                "fs_village_hard_large",
                "fs_village_abyss_large",
                "fs_village_abyss_large"
            };
            break;
            default:
            spawnWaves = new String[]
            {
                "fs_village_normal_large",
                "fs_village_hard_large",
                "fs_village_hard_large",
                "fs_village_abyss_large",
                "fs_village_abyss_large"
            };
            break;
        }
        int minDistance = VILLAGE_MIN_ENEMY_RADIUS;
        int maxDistance = VILLAGE_MAX_ENEMY_RADIUS;
        if (spawnWaves != null)
        {
            doEnemySpawn(master, minDistance, maxDistance, spawnWaves);
            spawnVillageVictims();
        }
        String spawnPulseSecondsString = getConfigSetting("GameServer", "fsVillageEnemyRespawnPulseTime");
        float spawnPulseSeconds = (float)rand(VILLAGE_MIN_ENEMY_SPAWN_PULSE, VILLAGE_MAX_ENEMY_SPAWN_PULSE);
        if (spawnPulseSecondsString != null && spawnPulseSecondsString.length() > 0)
        {
            spawnPulseSeconds = utils.stringToFloat(spawnPulseSecondsString);
            if (spawnPulseSeconds < 2500.0f)
            {
                spawnPulseSeconds = 2500.0f;
            }
        }
        messageTo(master, msgHandler, null, spawnPulseSeconds, false);
        return;
    }
    public static void spawnVillageVictims() throws InterruptedException
    {
        LOG("quests", "fs_dyn_village.spawnVillageVictims()");
        location[] victimLocations = getVillageLocs("village_victims");
        if (victimLocations != null)
        {
            int iter = 0;
            for (iter = 0; iter < victimLocations.length; ++iter)
            {
                LOG("quests", "fs_dyn_village.spawnVillageVictims() - spawning at " + victimLocations[iter]);
                quests.createSpawner("fs_villagers", victimLocations[iter], CITY_BAD_GUY_SPAWN_TABLE);
            }
        }
    }
    public static void doPostPhaseInitTasks(obj_id master) throws InterruptedException
    {
        if (hasObjVar(master, OBJVAR_LOCK_VILLAGE))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::doPostPhaseInitTasks: -> Master object locked. Not performing tasks.", master, trace.TL_ERROR_LOG);
            return;
        }
        int curPhase = getCurrentPhaseAuth(master);
        if (curPhase == 3 || curPhase == 4)
        {
            spawnTurrets(master);
            if (curPhase == 4)
            {
                doEnemySpawnPulse(master, "msgEnemySpawnPulse");
            }
        }
        return;
    }
    public static void destroyPhase3Camps(obj_id villageMaster) throws InterruptedException
    {
        LOG("fs_quest", "DESTROY PHASE 3 CAMPS");
        obj_id[] campIds = null;
        if (utils.hasScriptVar(villageMaster, fs_counterstrike.OBJVAR_CREATED_CAMP_IDS))
        {
            campIds = utils.getObjIdArrayScriptVar(villageMaster, fs_counterstrike.OBJVAR_CREATED_CAMP_IDS);
        }
        if (campIds != null && campIds.length > 0)
        {
            for (int i = 0; i < campIds.length; i++)
            {
                if (campIds[i] == null)
                {
                    continue;
                }
                messageTo(campIds[i], "msgSelfDestruct", null, 0.0f, false);
            }
        }
        removeObjVar(villageMaster, "fs_cs");
        utils.removeScriptVarTree(villageMaster, "fs_cs");
        return;
    }
    public static void doPrePhaseShiftCleanup(int oldPhase, int newPhase, obj_id villageMaster) throws InterruptedException
    {
        trace.log(LOG_CHAN, "****fs_dyn_village::doPrePhaseShiftCleanup; -> previous phase was " + oldPhase + ", new phase is " + newPhase);
        destroyPhase3Camps(villageMaster);
        return;
    }
    public static boolean pushAndInitPhase(obj_id master) throws InterruptedException
    {
        trace.log(LOG_CHAN, "---!!! pushAndInitPhase !!!---");
        if (hasObjVar(master, OBJVAR_NEXT_PHASE))
        {
            int curPhase = getIntObjVar(master, OBJVAR_CURRENT_PHASE);
            int nextPhase = getIntObjVar(master, OBJVAR_NEXT_PHASE);
            int nextRow = getRowIndexForPhase(nextPhase);
            if (nextRow > -1)
            {
                if (nextPhase < curPhase)
                {
                    int curVersion = 0;
                    if (hasObjVar(master, OBJVAR_PHASE_VERSION))
                    {
                        curVersion = getIntObjVar(master, OBJVAR_PHASE_VERSION);
                    }
                    curVersion++;
                    trace.log(LOG_CHAN, "fs_dyn_village::pushAndInitPhase: -> FS Village Version advanced to " + curVersion, master, trace.TL_CS_LOG);
                    setObjVar(master, OBJVAR_PHASE_VERSION, curVersion);
                }
                if (curPhase != nextPhase)
                {
                    doPrePhaseShiftCleanup(curPhase, nextPhase, master);
                    setObjVar(master, OBJVAR_PHASE_START_TIME, getGameTime());
                }
                setObjVar(master, OBJVAR_CURRENT_PHASE, nextPhase);
            }
            else 
            {
                trace.log(LOG_CHAN, "fs_dyn_village::pushAndInitPhase: -> Specified 'nextPhase' (" + nextPhase + ") does not have any build data in data table.  Not pushing to nextPhase.", master, trace.TL_CS_LOG);
            }
        }
        int phaseAuth = getCurrentPhaseAuth(master);
        int uidAuth = getCurrentUidAuth(master);
        registerIntegerInClusterWideData(phaseAuth, CLUSTER_INT_KEY_CUR_PHASE, "msgIntRegisteredPhase", master);
        registerIntegerInClusterWideData(uidAuth, CLUSTER_INT_KEY_PHASE_UID, "msgIntRegisteredUID", master);
        if (hasObjVar(master, OBJVAR_LOCK_VILLAGE))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::pushAndInitPhase: -> Master object locked. Not creating phase objects.", master, trace.TL_ERROR_LOG);
            return false;
        }
        int row = getRowIndexForPhase(phaseAuth);
        if (row < 0)
        {
            trace.log(LOG_CHAN, "fs_dyn_village::pushAndInitPhase: -> Unable to find data entry point for phase " + phaseAuth + ".  Can't create phase objects.", master, trace.TL_ERROR_LOG);
            return false;
        }
        if (!createPhaseObjects(master, row))
        {
            return false;
        }
        doPostPhaseInitTasks(master);
        return true;
    }
    public static boolean createPhaseObjects(obj_id master_object, int tableRowIdxStart) throws InterruptedException
    {
        destroyDynamicVillage(master_object);
        int totalRows = dataTableGetNumRows(CITY_STAGES_DATATABLE);
        if (totalRows < tableRowIdxStart + 1)
        {
            trace.log(LOG_CHAN, "fs_dyn_city::createPhaseObjects: -> Gave starting row of " + tableRowIdxStart + ", but " + CITY_STAGES_DATATABLE + " only has " + totalRows + " rows.", null, trace.TL_ERROR_LOG);
            return false;
        }
        String marker = dataTableGetString(CITY_STAGES_DATATABLE, tableRowIdxStart, DT_COL_TEMPLATE);
        if (!marker.equals(START_LOC))
        {
            trace.log(LOG_CHAN, "fs_dyn_city::createPhaseObjects: -> Row Index " + tableRowIdxStart + " is not the starting row for any phase.", null, trace.TL_ERROR_LOG);
            return false;
        }
        Vector villageObjects = new Vector();
        Vector villageSpawners = new Vector();
        for (int i = tableRowIdxStart + 1; i < totalRows; i++)
        {
            marker = dataTableGetString(CITY_STAGES_DATATABLE, i, DT_COL_TEMPLATE);
            if (marker.equals(START_LOC))
            {
                trace.log(LOG_CHAN, "fs_dyn_scriptlib::createPhaseObjects: -> Done creating items for this phase.  Stopped at data table row " + i);
                break;
            }
            dictionary object_row = dataTableGetRow(CITY_STAGES_DATATABLE, i);
            String obj_template = object_row.getString(DT_COL_TEMPLATE);
            float x = object_row.getFloat(DT_COL_OBJECT_X);
            float y = object_row.getFloat(DT_COL_OBJECT_Y);
            float z = object_row.getFloat(DT_COL_OBJECT_Z);
            int delta = object_row.getInt(DT_COL_IS_DELTA);
            float heading = object_row.getFloat(DT_COL_HEADING);
            int game_critical = object_row.getInt(DT_COL_GAME_CRITICAL);
            String script = object_row.getString(DT_COL_SCRIPT);
            String obj_mod = object_row.getString(DT_COL_SPECIAL_OBJ_MOD);
            String planet = getCurrentSceneName();
            location obj_loc = null;
            obj_id createdObj = null;
            boolean isSpawner = false;
            if (delta == 1)
            {
                location city_loc = getLocation(master_object);
                obj_loc = new location(city_loc.x - x, city_loc.y - y, city_loc.z - z, planet);
            }
            else 
            {
                obj_loc = new location(x, y, z, planet);
            }
            if (obj_mod != null && (toLower(obj_mod)).equals(OBJ_MOD_IS_SPAWNER))
            {
                createdObj = createObject(TEMPLATE_NO_DRAW, obj_loc);
                isSpawner = true;
            }
            else 
            {
                createdObj = createObject(obj_template, obj_loc);
            }
            if (createdObj == null)
            {
                if (isSpawner)
                {
                    trace.log(LOG_CHAN, "fs_dyn_city::createPhaseObjects: -> Null Object Pointer -> Unable to create NPC spawner " + TEMPLATE_NO_DRAW, master_object, trace.TL_ERROR_LOG);
                }
                else 
                {
                    trace.log(LOG_CHAN, "fs_dyn_city::createPhaseObjects: -> Null Object Pointer -> Unable to create object " + obj_template, master_object, trace.TL_ERROR_LOG);
                }
                continue;
            }
            else 
            {
                setObjVar(createdObj, OBJVAR_MY_MASTER_OBJECT, master_object);
                if (isSpawner)
                {
                    villageSpawners.add(createdObj);
                    setObjVar(createdObj, OBJVAR_MY_NPC_TEMPLATE, obj_template);
                    setObjVar(createdObj, OBJVAR_MY_NPC_SCRIPTS, script);
                    attachScript(createdObj, SCRIPT_VILLAGE_NPC_SPAWNER);
                }
                else 
                {
                    villageObjects.add(createdObj);
                }
                if (heading != 0.0f)
                {
                    
                }
                
                {
                    setYaw(createdObj, heading);
                }
                if (obj_mod != null && obj_mod.length() > 0)
                {
                    if ((toLower(obj_mod)).equals(OBJ_MOD_IS_HEALING_STRUCT))
                    {
                        setObjVar(createdObj, OBJVAR_IS_HEALING_STRUCT, 1);
                    }
                }
                if (game_critical == 1)
                {
                    setObjVar(createdObj, OBJVAR_CRITICAL_OBJECT, 1);
                }
                attachScript(createdObj, SCRIPT_DYN_VILLAGE_THING);
            }
        }
        utils.setResizeableBatchObjVar(master_object, OBJVAR_VILLAGE_OBJECTS, villageObjects);
        utils.setResizeableBatchObjVar(master_object, OBJVAR_VILLAGE_SPAWNERS, villageSpawners);
        return true;
    }
    public static void initFsClusterData() throws InterruptedException
    {
        replaceClusterWideData(DYNVILLAGE_CLUSTER_NAME, CLUSTER_SET_FS, new dictionary(), false, -1);
        return;
    }
    public static boolean registerObjIdInClusterWideData(obj_id id, String key, String msgHandler, obj_id source) throws InterruptedException
    {
        return _updateClusterData(source, DATAMODE_ADD_OBJID, key + "|" + id.toString(), msgHandler, true);
    }
    public static boolean getRegisteredObjIdFromClusterWideData(String key, String msgHandler, obj_id source) throws InterruptedException
    {
        return _updateClusterData(source, DATAMODE_GET_OBJID, key, msgHandler, false);
    }
    public static boolean registerIntegerInClusterWideData(int value, String key, String msgHandler, obj_id source) throws InterruptedException
    {
        return _updateClusterData(source, DATAMODE_ADD_INT, key + "|" + value, msgHandler, true);
    }
    public static boolean getRegisteredIntegerFromClusterWideData(String key, String msgHandler, obj_id source) throws InterruptedException
    {
        return _updateClusterData(source, DATAMODE_GET_INT, key, msgHandler, false);
    }
    public static boolean _updateClusterData(obj_id source, int data_mode, String params, String handler, boolean lock_data) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            trace.log(LOG_CHAN, "fs_dyn_village::_updateClusterData: -> source is invalid.", source, trace.TL_ERROR_LOG);
            return false;
        }
        if (params == null)
        {
            trace.log(LOG_CHAN, "fs_dyn_village::_updateClusterData: -> params is null.", source, trace.TL_ERROR_LOG);
            return false;
        }
        if (!hasScript(source, SCRIPT_FS_DATAHANDLER))
        {
            attachScript(source, SCRIPT_FS_DATAHANDLER);
        }
        int request_id = getClusterWideData(DYNVILLAGE_CLUSTER_NAME, CLUSTER_SET_FS, lock_data, source);
        utils.setScriptVar(source, SCRIPT_VAR_DATA_REQUEST + "." + request_id, data_mode);
        utils.setScriptVar(source, SCRIPT_VAR_PARAMS_REQUEST + "." + request_id, params);
        utils.setScriptVar(source, SCRIPT_VAR_HANDLER_REQ + "." + request_id, handler);
        return true;
    }
    public static int getRowIndexForPhase(int phase) throws InterruptedException
    {
        if (phase < 1)
        {
            return -1;
        }
        return dataTableSearchColumnForInt(phase, DT_COL_PHASE, CITY_STAGES_DATATABLE);
    }
    public static void destroyDynamicVillage(obj_id villageMaster) throws InterruptedException
    {
        destroyDynamicVillageObjects(villageMaster);
        destroyDynamicVillageSpawners(villageMaster);
        return;
    }
    public static void destroyDynamicVillageObjects(obj_id villageMaster) throws InterruptedException
    {
        obj_id[] objs = new obj_id[0];
        if (hasObjVar(villageMaster, OBJVAR_VILLAGE_OBJECTS))
        {
            objs = utils.getObjIdBatchObjVar(villageMaster, OBJVAR_VILLAGE_OBJECTS);
        }
        for (int i = 0; i < objs.length; i++)
        {
            if (isIdValid(objs[i]) && exists(objs[i]))
            {
                destroyObject(objs[i]);
            }
        }
        removeObjVar(villageMaster, OBJVAR_VILLAGE_OBJECTS);
        return;
    }
    public static void destroyDynamicVillageSpawners(obj_id villageMaster) throws InterruptedException
    {
        obj_id[] objs = new obj_id[0];
        if (hasObjVar(villageMaster, OBJVAR_VILLAGE_SPAWNERS))
        {
            objs = utils.getObjIdBatchObjVar(villageMaster, OBJVAR_VILLAGE_SPAWNERS);
        }
        for (int i = 0; i < objs.length; i++)
        {
            if (isIdValid(objs[i]) && exists(objs[i]))
            {
                destroyObject(objs[i]);
            }
        }
        removeObjVar(villageMaster, OBJVAR_VILLAGE_SPAWNERS);
        return;
    }
}
