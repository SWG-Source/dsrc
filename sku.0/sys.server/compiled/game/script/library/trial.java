package script.library;

import script.dictionary;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class trial extends script.base_script
{
    public trial()
    {
    }
    public static final String MESSAGE_SESSION = "messageTo.session";
    public static final String WP_OBJECT = "object/tangible/ground_spawning/patrol_waypoint.iff";
    public static final String PARENT = "parent";
    public static final String ARRAY_CHILDREN = "parent.tempChildren";
    public static final String TEMP_OBJECT = "tempObject";
    public static final String WP_NAME = "wp_name";
    public static final boolean LOGGING = false;
    public static final String UPLINK_WIN_SIGNAL = "mustafar_uplink_established";
    public static final String DECREPIT_ENTER_SIGNAL = "mustafar_factory_found";
    public static final String DECREPIT_WIN_SIGNAL = "mustafar_factory_fixed";
    public static final String ARMY_WIN_SIGNAL = "mustafar_droidarmy_victory";
    public static final String WORKING_ENTER_SIGNAL = "mustafar_droidfactory_final";
    public static final String WORKING_WIN_SIGNAL = "mustafar_droidfactory_shutdown";
    public static final String VOLCANO_ENTER_SIGNAL = "volcano_arena_pilot";
    public static final String VOLCANO_WIN_SIGNAL = "volcano_arena_victory";
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_NOT_LOCKED = new string_id(DECREPIT_STF, "decrepit_not_locked");
    public static final string_id SID_ACCESS_GRANTED = new string_id(DECREPIT_STF, "access_granted");
    public static final String DECREPIT_MAIN_HALL = "mainroom27";
    public static final String DECREPIT_ONE_TWO_STAIR = "hall2";
    public static final String DECREPIT_TWO_THREE_STAIR = "hall5";
    public static final String DECREPIT_TRAP_ROOM = "smallroom4";
    public static final String DECREPIT_PRE_TRAP_ROOM = "smallroom5";
    public static final String DECREPIT_ENVIRONMENTAL = "smallroom12";
    public static final String DECREPIT_FIRE_CELL_EXIT = "hall15";
    public static final String DECREPIT_FINAL_ROOM = "centralroom28";
    public static final String[] FIRE_CELLS = 
    {
        "mediumroom10",
        "smallroom11",
        "smallroom12",
        "hall13"
    };
    public static final String SECOND_ACCESS_BASE = "second_floor_access";
    public static final String ACCESS_CODE = SECOND_ACCESS_BASE + ".code";
    public static final String RESET_CODE = SECOND_ACCESS_BASE + ".reset";
    public static final int RESET_TIMER = 20;
    public static final int SENSOR_RESET_TIME = 4;
    public static final String TRAP_STATE = "trap_state";
    public static final String SENSOR_STATE = "sensor_state";
    public static final String FIRE_CELL_STATE = "fire_cell_state";
    public static final String POWER_CORE_STATE = "power_core_state";
    public static final String GUARDIAN_LOCK_STATE = "guardian_lock_state";
    public static final String DECREPIT_VICTORY_STATE = "decrepit_victory_state";
    public static final String WORKING_STF = "mustafar/working_droid_factory";
    public static final string_id TWINS_RESPAWN = new string_id(WORKING_STF, "twin_respawn");
    public static final string_id TWINS_DEFEATED = new string_id(WORKING_STF, "twin_defeated");
    public static final string_id TWINS_LOCK_ACTIVE = new string_id(WORKING_STF, "access_denied");
    public static final string_id TWINS_KILL_CLUE = new string_id(WORKING_STF, "twin_kill_clue");
    public static final string_id WORKING_DEVISTATOR_DEFEATED = new string_id(WORKING_STF, "devistator_defeated");
    public static final string_id WORKING_INHIBITOR_NOCHARGE = new string_id(WORKING_STF, "inhibitor_lost_charge");
    public static final string_id WORKING_INHIBITOR_RECHARGE = new string_id(WORKING_STF, "inhibitor_recharging");
    public static final string_id WORKING_INHIBITOR_INACTIVE = new string_id(WORKING_STF, "inhibitor_inactive");
    public static final string_id WORKING_INHIBITOR_COLLECT = new string_id(WORKING_STF, "inhibitor_collect");
    public static final string_id WORKING_INHIBITOR_TAKEN = new string_id(WORKING_STF, "inhibitor_taken");
    public static final string_id WORKING_RRU_OP_CLUE = new string_id(WORKING_STF, "rru_clue");
    public static final string_id WORKING_RRU_INACTIVE = new string_id(WORKING_STF, "rru_inactive");
    public static final string_id WORKING_RRU_ACTIVE = new string_id(WORKING_STF, "rru_active");
    public static final string_id WORKING_RRU_FALTER = new string_id(WORKING_STF, "rru_falter");
    public static final string_id WORKING_RRU_RECHARGE = new string_id(WORKING_STF, "rru_recharge");
    public static final string_id WORKING_MDE_DEFEATED = new string_id(WORKING_STF, "mde_defeated");
    public static final string_id WORKING_MDE_TAUNT = new string_id(WORKING_STF, "mde_taunt");
    public static final string_id WORKING_DOOM_DEFEATED = new string_id(WORKING_STF, "doom_defeated");
    public static final string_id WORKING_DOOM_FAILURE = new string_id(WORKING_STF, "doom_failure");
    public static final string_id WORKING_HK47_TAUNT = new string_id(WORKING_STF, "hk47_taunt");
    public static final string_id WORKING_DUALITY_APPLY = new string_id(WORKING_STF, "duality_applied");
    public static final string_id WORKING_IMBALANCE_APPLY = new string_id(WORKING_STF, "imbalance_applied");
    public static final string_id WORKING_IMBALANCE_FADE = new string_id(WORKING_STF, "imbalance_fade");
    public static final string_id WORKING_OBSERVER_DETONATE = new string_id(WORKING_STF, "observer_detonate");
    public static final String WORKING_MAIN_HALL = "mainroom27";
    public static final String WORKING_ONE_TWO_STAIR = "hall2";
    public static final String WORKING_TWO_THREE_STAIR = "hall7";
    public static final String WORKING_DE_HALL = "hall13";
    public static final String WORKING_MASTER_CONTROL = "centralroom28";
    public static final String WORKING_CLONER_EXIT = "mdeClonerExit";
    public static final String OBJECT_INHIBITOR = "object/tangible/dungeon/mustafar/working_droid_factory/inhibitor_storage.iff";
    public static final String OBJECT_REACTIVE_REPAIR = "object/tangible/dungeon/mustafar/working_droid_factory/reactive_repair_module.iff";
    public static final String OBJECT_DE_CLONER = "object/tangible/dungeon/mustafar/working_droid_factory/rapid_assembly_station.iff";
    public static final String OBJECT_DECTRUCT_PILE = "object/tangible/dungeon/mustafar/working_droid_factory/radioactive_pile.iff";
    public static final String WORKING_AUREK_KILLED = "isAurekKilled";
    public static final String WORKING_BESH_KILLED = "isBeshKilled";
    public static final String WORKING_AUREK_BESH_ENGAGED = "isAurekBeckEngaged";
    public static final String AUREK_HEALTH = "health.aurek";
    public static final String BESH_HEALTH = "health.besh";
    public static final String WORKING_DEVISTATOR_KILLED = "isDevistatorKilled";
    public static final String WORKING_DEVISTATOR_ENGAGED = "isDevistatorEngaged";
    public static final String WORKING_INHIBITOR_TRACKER = "currentInhibitorSession";
    public static final String WORKING_RRU_STATE = "rapidRepairUnitState";
    public static final String WORKING_MDE_KILLED = "isMasterDroidEngineerKilled";
    public static final String WORKING_MDE_ENGAGED = "isMdeEngaged";
    public static final String WORKING_MDE_MOB = "isMdeMob";
    public static final String WORKING_ASSEMBLY_STAGE = "eventStage";
    public static final String WORKING_MDE_REVIVED = "wasRevived";
    public static final String WORKING_POWER_SHUT_DOWN = "isPowerCoreOff";
    public static final String WORKING_DOOM_BRINGER_KILLED = "isDoomBringerKilled";
    public static final String WORKING_ACTIVE_HOD = "doom.activeHandOfDoom";
    public static final String WORKING_PRIMARY_HAND = "doom.primaryHand";
    public static final String WORKING_HOD_TARGET = "doom.objectiveTarget";
    public static final String WORKING_HK_SPAWNED = "hk.isSpawned";
    public static final String WORKING_HK_BOMB = "hk.isDetonated";
    public static final String WORKING_AUREKBESH_KILL_TIME = "aurekBeshKillTime";
    public static final int WORKING_AUREKBESH_RESPAWN_DELAY = 15;
    public static final boolean WORKING_LOGGING = false;
    public static final boolean UPLINK_LOGGING = false;
    public static final String UPLINK_STF = "mustafar/uplink_cave";
    public static final String UPLINK_VICTORY_STATE = "isUplinkEstablished";
    public static final String UPLINK_DATA = "datatables/dungeon/mustafar_trials/link_establish/link_event_data.iff";
    public static final String RELAY_OBJECT = "object/tangible/dungeon/mustafar/uplink_trial/relay_object.iff";
    public static final String UPLINK_ESTABLISH_EFFECT = "blah.cef";
    public static final String UPLINK_ROOM = "mainroom";
    public static final String UPLINK_ACTIVE = "isUplinkTrialActive";
    public static final string_id UPLINK_FOREMAN_SPAWN = new string_id(UPLINK_STF, "foreman_spawn");
    public static final boolean VALLEY_LOGGING = false;
    public static final String VALLEY_DATA = "datatables/dungeon/mustafar_trials/valley_battlefield/valley_event_data.iff";
    public static final String BATTLEFIELD_VICTORY_STATE = "armyDefeated";
    public static final String BATTLEFIELD_DROID_ARMY = "isArmy";
    public static final String BATTLEFIELD_MINER = "isMiner";
    public static final String BATTLEFIELD_COMMANDER_KILLED = "isCommanderKilled";
    public static final String BATTLEFIELD_GENERATOR_DESTROYED = "isGeneratorDestoryed";
    public static final String BATTLEFIELD_COMMANDER_LOGIC = "assaultFinal";
    public static final String BATTLEFIELD_DROID_CORPSE = "droidCorpse";
    public static final String BATTLEFIELD_STF = "mustafar/valley_battlefield";
    public static final string_id BATTLEFIELD_GENERATOR_DEAD = new string_id(BATTLEFIELD_STF, "generator_destroyed");
    public static final string_id BATTLEFIELD_COMMANDER_DIED = new string_id(BATTLEFIELD_STF, "commander_killed");
    public static final string_id BATTLEFIELD_CMNDR_INTRO = new string_id(BATTLEFIELD_STF, "commander_intro");
    public static final string_id BATTLEFIELD_WIN_MESSAGE = new string_id(BATTLEFIELD_STF, "battlefield_win_message");
    public static final string_id BATTLEFIELD_LOSE_MESSAGE = new string_id(BATTLEFIELD_STF, "battlefield_lose_message");
    public static final string_id BATTLEFIELD_LOSE_1 = new string_id(BATTLEFIELD_STF, "about_to_lose_1");
    public static final string_id BATTLEFIELD_LOSE_2 = new string_id(BATTLEFIELD_STF, "about_to_lose_2");
    public static final string_id BATTLEFIELD_LOSE_3 = new string_id(BATTLEFIELD_STF, "about_to_lose_3");
    public static final int BATTLEFIELD_WAVE_DELAY = 150;
    public static final int BATTLEFIELD_COMM_REZ_DELAY = 18;
    public static final boolean VOLCANO_LOGGING = false;
    public static final String VOLCANO_STF = "mustafar/volcano_battlefield";
    public static final string_id VOLCANO_TASKMASTER_STRENGTHEN = new string_id(VOLCANO_STF, "taskmaster_strengthen");
    public static final string_id VOLCANO_CYM_BEETLE_NOTIFY = new string_id(VOLCANO_STF, "four_summon_add");
    public static final string_id VOLCANO_OPP_ADD_NOTIFY = new string_id(VOLCANO_STF, "five_summon_trio");
    public static final string_id VOLCANO_OPP_MIDGUARD = new string_id(VOLCANO_STF, "five_summon_midguard");
    public static final string_id VOLCANO_HK_SUMMON_AK = new string_id(VOLCANO_STF, "hk_summon_walker");
    public static final string_id VOLCANO_HK_TAUNT = new string_id(VOLCANO_STF, "hk_prefight_taunt");
    public static final String VOLCANO_THREE_IS_BOSS = "isCommander";
    public static final String VOLCANO_THREE_IS_CORPSE = "isCorpse";
    public static final String VOLCANO_FOUR_IS_BEETLE = "isKubazaBeetle";
    public static final String VOLCANO_FINAL_IS_CORPSE = "isCorpse";
    public static final boolean MONSTER_LOGGING = false;
    public static final String MONSTER_DATA = "datatables/dungeon/mustafar_trials/sher_kar/sher_kar_data";
    public static final String MONSTER_STF = "mustafar/sher_kar";
    public static final String MONSTER_AREA = "r1";
    public static final String MONSTER_WP = "spawn_point";
    public static final String MONSTER_SHER_KAR = "sherKar.id";
    public static final String MONSTER_KARLING = "som_sherkar_karling";
    public static final String MONSTER_PRAETORIAN = "som_sherkar_praetorian";
    public static final String MONSTER_SYMBIOT = "som_sherkar_symbiot";
    public static final string_id MONSTER_ENRAGE_WARNING = new string_id(MONSTER_STF, "enrage_warning");
    public static final string_id MONSTER_SK_DEFEATED = new string_id(MONSTER_STF, "sk_defeated");
    public static final String SND_POWERUP = "clienteffect/snd_forcefield_powerup.cef";
    public static final String SND_POWERDOWN = "clienteffect/snd_forcefield_powerdown.cef";
    public static final String PRT_CYM_DISEASE = "clienteffect/mus_cym_disease.cef";
    public static final String PRT_CYM_POISON = "clienteffect/mus_cym_poison.cef";
    public static final String PRT_DROID_HEAL = "clienteffect/mus_droid_heal.cef";
    public static final String PRT_DROID_REVIVE = "clienteffect/mus_droid_revive.cef";
    public static final String PRT_RELAY_ACTIVATE = "clienteffect/mus_relay_activate.cef";
    public static final String PRT_RELAY_CREATE = "clienteffect/mus_relay_create.cef";
    public static final String PRT_KUBAZA_EXPLODE = "clienteffect/exp_ap_landmine.cef";
    public static final String PRT_KUBAZA_WARNING = "clienteffect/mus_kubaza_warning.cef";
    public static final String PRT_INVULN_SHIELD = "appearance/pt_flash_shield.prt";
    public static final String PRT_TRAP_STUN = "clienteffect/mus_relay_activate.cef";
    public static final String PRT_WORKING_REPAIR_REZ = "clienteffect/mus_relay_create.cef";
    public static final String PRT_WORKING_REPAIR_COMPILE = "clienteffect/mus_relay_create.cef";
    public static final String PRT_WORKING_DUALITY_EXPLOSION = "appearance/pt_cmndo_shock_grenade5.prt";
    public static final String PRT_WORKING_HK_BOOM_1 = "clienteffect/avatar_explosion_01.cef";
    public static final String PRT_WORKING_HK_BOOM_2 = "clienteffect/avatar_explosion_02.cef";
    public static final String PRT_WORKING_HK_BOOM_3 = "clienteffect/avatar_room_explosion.cef";
    public static final String PRT_VOLCANO_WAVE_PRE = "appearance/pt_blast_wave_build_up.prt";
    public static final String PRT_VOLCANO_WAVE_EXE = "appearance/pt_blast_wave.prt";
    public static final String PRT_VOLCANO_AIR_PRE = "appearance/pt_rocket_barrage_wind_up.prt";
    public static final String PRT_VOLCANO_AIR_EXE = "appearance/pt_rocket_barrage.prt";
    public static final String PRT_VOLCANO_CONE_PRE = "appearance/pt_large_beam_warm_up.prt";
    public static final String PRT_VOLCANO_CONE_EXE = "appearance/pt_large_beam.prt";
    public static final String PRT_VOLCANO_YT_LANDING = "appearance/must_smoke_plume01.prt";
    public static final String PRT_SHER_KAR_CAVE_IN = "clienteffect/cave_in_roof.cef";
    public static final String MUS_BATTLEFIELD_DROID_ARMY_INTRO = "sound/mus_mustafar_droid_invasion_intro.snd";
    public static final String MUS_VOLCANO_HK_INTRO = "sound/mus_mustafar_hk47_intro.snd";
    public static final String MUS_MUST_QUEST_WIN = "sound/mus_mustafar_quest_success.snd";
    public static final String MUS_MUST_QUEST_FAIL = "sound/mus_mustafar_quest_fail.snd";
    public static final int HP_DECREPIT_COLONEL = 145000;
    public static final int HP_DECREPIT_GUARDIAN = 185250;
    public static final int HP_WORKING_AUREK_BESH = 448220;
    public static final int HP_WORKING_DEVISTATOR = 635425;
    public static final int HP_WORKING_MDE = 385225;
    public static final int HP_WORKING_FIXER_ONE = 855965;
    public static final int HP_WORKING_DOOM_HAND = 125000;
    public static final int HP_WORKING_DOOM_BRINGER = 705385;
    public static final int HP_DOOM_TARGET = 200000;
    public static final int HP_WORKING_ASSEMBLY = 50000;
    public static final int HP_BATTLEFIELD_GENERATOR = 65000;
    public static final int HP_VOLCANO_ONE_GUARD = 65000;
    public static final int HP_VOLCANO_ONE_BOSS = 545000;
    public static final int HP_VOLCANO_TWO_GUARD = 95250;
    public static final int HP_VOLCANO_TWO_BOSS = 655280;
    public static final int HP_VOLCANO_THREE_GUARD = 33500;
    public static final int HP_VOLCANO_THREE_RISEN = 60250;
    public static final int HP_VOLCANO_THREE_BOSS = 655250;
    public static final int HP_VOLCANO_FOUR_GUARD = 3000;
    public static final int HP_VOLCANO_FOUR_BOSS = 950485;
    public static final int HP_VOLCANO_FIVE_GUARD = 50000;
    public static final int HP_VOLCANO_FIVE_MIDGUARD = 100000;
    public static final int HP_VOLCANO_FIVE_BOSS = 220000;
    public static final int HP_VOLCANO_HK_SOLDIER = 14000;
    public static final int HP_VOLCANO_HK_SQUAD_LEADER = 20000;
    public static final int HP_VOLCANO_HK_RISEN_GUARD = 22525;
    public static final int HP_VOLCANO_HK_BEETLE = 9000;
    public static final int HP_VOLCANO_HK_SEPTIPOD = 75250;
    public static final int HP_VOLCANO_HK_CWW = 55855;
    public static final int HP_VOLCANO_HK47 = 545852;
    public static final int HP_SHER_KAR = 885000;
    public static final int HP_SHER_KAR_CONSORT = 225000;
    public static final int HP_SHER_KARLING = 65400;
    public static final int HP_SHER_KAR_PRAETORIAN = 120000;
    public static final int HP_SHER_KAR_LIFESAPPER = 95000;
    public static final int HP_AKXVA_NANDINA = 245440;
    public static final int HP_AXKVA_GORVO = 614525;
    public static final int HP_AXKVA_LILLI_HI = 599258;
    public static final int HP_AXKVA_KIMARU = 818125;
    public static final int HP_AXKVA_SUIN = 1012400;
    public static final int HP_AXKVA = 1305858;
    public static final int HP_EXAR_OPEN = 515325;
    public static final int HP_EXAR_MINDER = 623145;
    public static final int HP_EXAR_LURESH = 355245;
    public static final int HP_EXAR_VINRITH = 255454;
    public static final int HP_EXAR_CLOSED = 1485235;
    public static final int HP_UNCLE_JOE = 818125;
    public static final int HP_JOES_ADDS = 355325;
    public static final int TIME_UPLINK_WIN = 60 * 60 * 2;
    public static final int TIME_DECREPIT_ADD = 60 * 90;
    public static final int TIME_DECREPIT_WIN = 60 * 60 * 3;
    public static final int TIME_BATTLEFIELD_WIN = 60 * 60 * 3;
    public static final int TIME_WORKING_ADD = 60 * 60 * 2;
    public static final int TIME_WORKING_WIN = 60 * 60 * 6;
    public static final int TIME_VOLCANO_ADD = 60 * 60 * 2;
    public static final int TIME_VOLCANO_WIN = 60 * 60 * 12;
    public static final int TIME_MONSTER_WIN = 60 * 60 * 20;
    public static final String[] HEROIC_TOKENS = 
    {
        "item_heroic_token_axkva_01_01",
        "item_heroic_token_tusken_01_01",
        "item_heroic_token_ig88_01_01",
        "item_heroic_token_black_sun_01_01",
        "item_heroic_token_exar_01_01",
        "item_heroic_token_echo_base_01_01",
        "item_battlefield_rebel_token_massassi_isle",
        "item_battlefield_imperial_token_massassi_isle",
        "item_battlefield_rebel_token_battlefield2",
        "item_battlefield_imperial_token_battlefield2",
        "item_battlefield_rebel_token_battlefield3",
        "item_battlefield_imperial_token_battlefield3",
        "item_battlefield_rebel_token_battlefield4",
        "item_battlefield_imperial_token_battlefield4",
        "item_pgc_token_01",
        "item_pgc_token_02",
        "item_pgc_token_03",
        "item_gcw_rebel_token",
        "item_gcw_imperial_token",
        "item_token_duty_space_01_01"
    };
    public static final int NUM_HEROIC_TOKEN_TYPES = 20;
    public static final String KIMARU_HATE_LIST = "kimaru_hate_list";
    public static final String WP_DATA = "nearPoint";
    public static final String PROT_CHILD = "protected_data";
    public static final String PROT_CHILD_ARRAY = "protected_data.child_array";
    public static final String PROT_CHILD_KEY_LIST = "protected_data.key_list";
    public static final String PATROL_PATH_FULL_DATA = "all_instance_patrol_path";
    public static final String SEQUENCER_PATH_DATA = "sequencer_path_data";
    public static final String SPACE_DUTY_TOKEN = "item_token_duty_space_01_01";
    public static void initializeBox(obj_id self) throws InterruptedException
    {
        int[] tokenTypes = new int[trial.NUM_HEROIC_TOKEN_TYPES];
        for (int i = 0; i < trial.NUM_HEROIC_TOKEN_TYPES; i++)
        {
            tokenTypes[i] = 0;
        }
        setObjVar(self, "item.set.tokens_held", tokenTypes);
    }
    public static void verifyBox(obj_id self) throws InterruptedException
    {
        int[] tokenTypes = getIntArrayObjVar(self, "item.set.tokens_held");
        if (tokenTypes == null)
        {
            initializeBox(self);
        }
        else if (tokenTypes.length < trial.NUM_HEROIC_TOKEN_TYPES)
        {
            int[] newTokenTypes = new int[trial.NUM_HEROIC_TOKEN_TYPES];
            for (int i = 0; i < trial.NUM_HEROIC_TOKEN_TYPES; i++)
            {
                newTokenTypes[i] = 0;
            }
            System.arraycopy(tokenTypes, 0, newTokenTypes, 0, tokenTypes.length);
            setObjVar(self, "item.set.tokens_held", newTokenTypes);
        }
    }
    public static String generateNewKeyCode(obj_id dungeon) throws InterruptedException
    {
        int code = rand(100000, 999999);
        String stringCode = Integer.toString(code);
        int endTime = getGameTime() + RESET_TIMER;
        utils.setScriptVar(dungeon, ACCESS_CODE, stringCode);
        utils.setScriptVar(dungeon, RESET_CODE, endTime);
        messageTo(dungeon, "codeResetOccured", null, 0, false);
        return stringCode;
    }
    public static String initializeCode(obj_id dungeon) throws InterruptedException
    {
        int code = 0;
        String stringCode = Integer.toString(code);
        int endTime = 0;
        utils.setScriptVar(dungeon, ACCESS_CODE, stringCode);
        utils.setScriptVar(dungeon, RESET_CODE, endTime);
        return stringCode;
    }
    public static String getKeyCode(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!utils.hasScriptVar(dungeon, ACCESS_CODE))
        {
            return generateNewKeyCode(dungeon);
        }
        else 
        {
            return utils.getStringScriptVar(dungeon, ACCESS_CODE);
        }
    }
    public static int getResetTime(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!utils.hasScriptVar(dungeon, RESET_CODE))
        {
            generateNewKeyCode(dungeon);
            return utils.getIntScriptVar(dungeon, RESET_CODE);
        }
        else 
        {
            return utils.getIntScriptVar(dungeon, RESET_CODE);
        }
    }
    public static void doCountdownTimerFlyText(obj_id terminal, int timer) throws InterruptedException
    {
        dictionary dict = new dictionary();
        switch (timer)
        {
            case 10:
                showFlyText(terminal, new string_id(DECREPIT_STF, "ten"), 1.0f, colors.GREEN);
                dict.put("timer", 9);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 9:
                showFlyText(terminal, new string_id(DECREPIT_STF, "nine"), 1.0f, colors.GREEN);
                dict.put("timer", 8);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 8:
                showFlyText(terminal, new string_id(DECREPIT_STF, "eight"), 1.0f, colors.GREEN);
                dict.put("timer", 7);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 7:
                showFlyText(terminal, new string_id(DECREPIT_STF, "seven"), 1.0f, colors.GREEN);
                dict.put("timer", 6);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 6:
                showFlyText(terminal, new string_id(DECREPIT_STF, "six"), 1.0f, colors.GREEN);
                dict.put("timer", 5);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 5:
                showFlyText(terminal, new string_id(DECREPIT_STF, "five"), 1.0f, colors.GREEN);
                dict.put("timer", 4);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 4:
                showFlyText(terminal, new string_id(DECREPIT_STF, "four"), 1.0f, colors.GREEN);
                dict.put("timer", 3);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 3:
                showFlyText(terminal, new string_id(DECREPIT_STF, "three"), 1.0f, colors.GREEN);
                dict.put("timer", 2);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 2:
                showFlyText(terminal, new string_id(DECREPIT_STF, "two"), 1.0f, colors.GREEN);
                dict.put("timer", 1);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
                return;
            case 1:
                showFlyText(terminal, new string_id(DECREPIT_STF, "one"), 1.0f, colors.GREEN);
                dict.put("timer", 0);
                messageTo(terminal, "showStatusFlyText", dict, 1, false);
        }
    }
    public static boolean isTrapActive(obj_id dungeon) throws InterruptedException {
        dungeon = getTop(dungeon);
        return utils.hasScriptVar(dungeon, TRAP_STATE) && utils.getBooleanScriptVar(dungeon, TRAP_STATE);
    }
    public static boolean isTrapPrimed(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id[] sensors = getObjectsInDungeonWithScript(dungeon, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.trap_terminal_scanner");
        if (sensors == null || sensors.length == 0)
        {
            doLogging("isTrapPrimed", "Sensor list was null or empty");
            return false;
        }
        boolean primed = false;
        for (obj_id sensor : sensors) {
            if (isSensorActive(sensor)) {
                primed = true;
            }
        }
        return primed;
    }
    public static boolean isSensorActive(obj_id sensor) throws InterruptedException
    {
        if (!utils.hasScriptVar(sensor, SENSOR_STATE))
        {
            utils.setScriptVar(sensor, SENSOR_STATE, true);
            return true;
        }
        return utils.getBooleanScriptVar(sensor, SENSOR_STATE);
    }
    public static void setTrapState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, TRAP_STATE, state);
    }
    public static void setFireCellState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, FIRE_CELL_STATE, state);
    }
    public static boolean isPowerCoreOn(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, POWER_CORE_STATE))
        {
            return utils.getBooleanScriptVar(dungeon, POWER_CORE_STATE);
        }
        else 
        {
            utils.setScriptVar(dungeon, POWER_CORE_STATE, false);
            return false;
        }
    }
    public static void setPowerCoreState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, POWER_CORE_STATE, state);
    }
    public static void setGuardianLockState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, GUARDIAN_LOCK_STATE, state);
    }
    public static boolean isGuardianLockInPlace(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, GUARDIAN_LOCK_STATE))
        {
            return utils.getBooleanScriptVar(dungeon, GUARDIAN_LOCK_STATE);
        }
        else 
        {
            utils.setScriptVar(dungeon, GUARDIAN_LOCK_STATE, true);
            return true;
        }
    }
    public static boolean isDecrepitDefeated(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, DECREPIT_VICTORY_STATE))
        {
            return utils.getBooleanScriptVar(dungeon, DECREPIT_VICTORY_STATE);
        }
        else 
        {
            utils.setScriptVar(dungeon, DECREPIT_VICTORY_STATE, false);
            return false;
        }
    }
    public static void setDecrepitTrialState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, DECREPIT_VICTORY_STATE, state);
    }
    public static void setAurekKilled(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_AUREK_KILLED, state);
    }
    public static void setBeshKilled(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_BESH_KILLED, state);
    }
    public static void setDevistatorKilled(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_DEVISTATOR_KILLED, state);
    }
    public static void setMdeKilled(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_MDE_KILLED, state);
    }
    public static void setPowerCoreDeactivated(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_POWER_SHUT_DOWN, state);
    }
    public static void setDoomBringerKilled(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_DOOM_BRINGER_KILLED, state);
    }
    public static void setAurekBeshEngaged(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_AUREK_BESH_ENGAGED, state);
    }
    public static boolean isAurekBeshEngaged(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_AUREK_BESH_ENGAGED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_AUREK_BESH_ENGAGED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_AUREK_BESH_ENGAGED, false);
            return false;
        }
    }
    public static void setAurekBeshKillTime(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        int time = getGameTime();
        utils.setScriptVar(dungeon, WORKING_AUREKBESH_KILL_TIME, time);
    }
    public static int getAurekBeshKillTime(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_AUREKBESH_KILL_TIME))
        {
            return utils.getIntScriptVar(dungeon, WORKING_AUREKBESH_KILL_TIME);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_AUREKBESH_KILL_TIME, 0);
            return 0;
        }
    }
    public static void clearAurekBeshKillTime(obj_id dungeon) throws InterruptedException
    {
        if (utils.hasScriptVar(dungeon, WORKING_AUREKBESH_KILL_TIME))
        {
            utils.removeScriptVar(dungeon, WORKING_AUREKBESH_KILL_TIME);
        }
    }
    public static boolean isAurekKilled(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_AUREK_KILLED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_AUREK_KILLED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_AUREK_KILLED, false);
            return false;
        }
    }
    public static boolean isBeshKilled(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_BESH_KILLED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_BESH_KILLED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_BESH_KILLED, false);
            return false;
        }
    }
    public static boolean isAurekBeshKilled(obj_id dungeon) throws InterruptedException
    {
        return (isAurekKilled(dungeon) && isBeshKilled(dungeon));
    }
    public static boolean isDevistatorKilled(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_DEVISTATOR_KILLED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_DEVISTATOR_KILLED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_DEVISTATOR_KILLED, false);
            return false;
        }
    }
    public static boolean isMdeKilled(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_MDE_KILLED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_MDE_KILLED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_MDE_KILLED, false);
            return false;
        }
    }
    public static boolean isPowerCoreDeactivated(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_POWER_SHUT_DOWN))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_POWER_SHUT_DOWN);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_POWER_SHUT_DOWN, false);
            return false;
        }
    }
    public static boolean isDoomBringerKilled(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_DOOM_BRINGER_KILLED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_DOOM_BRINGER_KILLED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_DOOM_BRINGER_KILLED, false);
            return false;
        }
    }
    public static void setRruDeactivated(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_RRU_STATE, state);
    }
    public static boolean isRruDeactivated(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_RRU_STATE))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_RRU_STATE);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_RRU_STATE, false);
            return false;
        }
    }
    public static void setDevistatorEngaged(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_DEVISTATOR_ENGAGED, state);
    }
    public static boolean isDevistatorEngaged(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_DEVISTATOR_ENGAGED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_DEVISTATOR_ENGAGED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_DEVISTATOR_ENGAGED, false);
            return false;
        }
    }
    public static boolean isMdeEngaged(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, WORKING_MDE_ENGAGED))
        {
            return utils.getBooleanScriptVar(dungeon, WORKING_MDE_ENGAGED);
        }
        else 
        {
            utils.setScriptVar(dungeon, WORKING_MDE_ENGAGED, false);
            return false;
        }
    }
    public static void setMdeEngaged(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, WORKING_MDE_ENGAGED, state);
    }
    public static void healAssemblyUnit(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id[] assemblyUnits = getObjectsInDungeonWithScript(dungeon, "theme_park.dungeon.mustafar_trials.working_droid_factory.rapid_assembly_unit");
        if (assemblyUnits == null || assemblyUnits.length == 0)
        {
            return;
        }
        for (obj_id assemblyUnit : assemblyUnits) {
            if (isDisabled(assemblyUnit)) {
                clearCondition(assemblyUnit, CONDITION_DISABLED);
            }
            int maxHitPoints = getMaxHitpoints(assemblyUnit);
            setHitpoints(assemblyUnit, maxHitPoints);
            setInvulnerable(assemblyUnit, true);
            trial.bumpSession(assemblyUnit);
        }
    }
    public static void clearMdeArea(obj_id dungeon) throws InterruptedException
    {
        obj_id[] mdeCreatures = getObjectsInDungeonWithScriptVar(dungeon, WORKING_MDE_MOB);
        if (mdeCreatures == null || mdeCreatures.length == 0)
        {
            doLogging("xx", "There were no creatures to be found");
            return;
        }
        cleanupNpc(mdeCreatures);
    }
    public static void resetAssemblyStage(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id[] assemblyUnits = getObjectsInDungeonWithScript(dungeon, "theme_park.dungeon.mustafar_trials.working_droid_factory.rapid_assembly_unit");
        if (assemblyUnits == null || assemblyUnits.length == 0)
        {
            return;
        }
        for (obj_id assemblyUnit : assemblyUnits) {
            utils.setScriptVar(assemblyUnit, WORKING_ASSEMBLY_STAGE, 0);
        }
    }
    public static boolean isActiveHand(obj_id unit) throws InterruptedException
    {
        if (utils.hasScriptVar(unit, WORKING_ACTIVE_HOD))
        {
            return utils.getBooleanScriptVar(unit, WORKING_ACTIVE_HOD);
        }
        else 
        {
            utils.setScriptVar(unit, WORKING_ACTIVE_HOD, false);
            return false;
        }
    }
    public static boolean isPrimaryHand(obj_id unit) throws InterruptedException
    {
        if (utils.hasScriptVar(unit, WORKING_PRIMARY_HAND))
        {
            return utils.getBooleanScriptVar(unit, WORKING_PRIMARY_HAND);
        }
        else 
        {
            utils.setScriptVar(unit, WORKING_PRIMARY_HAND, false);
            return false;
        }
    }
    public static void setActiveHand(obj_id unit, boolean state) throws InterruptedException
    {
        utils.setScriptVar(unit, WORKING_ACTIVE_HOD, state);
    }
    public static void setPrimaryHand(obj_id unit, boolean state) throws InterruptedException
    {
        utils.setScriptVar(unit, WORKING_PRIMARY_HAND, state);
    }
    public static boolean isHkSpawned(obj_id unit) throws InterruptedException
    {
        if (utils.hasScriptVar(unit, WORKING_HK_SPAWNED))
        {
            return utils.getBooleanScriptVar(unit, WORKING_HK_SPAWNED);
        }
        else 
        {
            utils.setScriptVar(unit, WORKING_HK_SPAWNED, false);
            return false;
        }
    }
    public static boolean isHkDetonated(obj_id unit) throws InterruptedException
    {
        if (utils.hasScriptVar(unit, WORKING_HK_BOMB))
        {
            return utils.getBooleanScriptVar(unit, WORKING_HK_BOMB);
        }
        else 
        {
            utils.setScriptVar(unit, WORKING_HK_BOMB, false);
            return false;
        }
    }
    public static void setHkSpawned(obj_id unit, boolean state) throws InterruptedException
    {
        utils.setScriptVar(unit, WORKING_HK_SPAWNED, state);
    }
    public static void setHkDetonated(obj_id unit, boolean state) throws InterruptedException
    {
        utils.setScriptVar(unit, WORKING_HK_BOMB, state);
    }
    public static void setUplinkVictoryState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, UPLINK_VICTORY_STATE, state);
    }
    public static boolean isUplinkDefeated(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, UPLINK_VICTORY_STATE))
        {
            return utils.getBooleanScriptVar(dungeon, UPLINK_VICTORY_STATE);
        }
        else 
        {
            utils.setScriptVar(dungeon, UPLINK_VICTORY_STATE, false);
            return false;
        }
    }
    public static boolean isUplinkActive(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (utils.hasScriptVar(dungeon, UPLINK_ACTIVE))
        {
            return utils.getBooleanScriptVar(dungeon, UPLINK_ACTIVE);
        }
        else 
        {
            utils.setScriptVar(dungeon, UPLINK_ACTIVE, false);
            return false;
        }
    }
    public static void setUplinkActiveState(obj_id dungeon, boolean state) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        utils.setScriptVar(dungeon, UPLINK_ACTIVE, state);
    }
    public static void setIsDroidArmyDefeated(obj_id dungeon, boolean state) throws InterruptedException
    {
        utils.setScriptVar(dungeon, BATTLEFIELD_VICTORY_STATE, state);
    }
    public static boolean isDroidArmyDefeated(obj_id dungeon) throws InterruptedException
    {
        if (utils.hasScriptVar(dungeon, BATTLEFIELD_VICTORY_STATE))
        {
            return utils.getBooleanScriptVar(dungeon, BATTLEFIELD_VICTORY_STATE);
        }
        else 
        {
            utils.setScriptVar(dungeon, BATTLEFIELD_VICTORY_STATE, false);
            return false;
        }
    }
    public static void setIsCommanderKilled(obj_id dungeon, boolean state) throws InterruptedException
    {
        utils.setScriptVar(dungeon, BATTLEFIELD_COMMANDER_KILLED, state);
    }
    public static boolean isCommanderKilled(obj_id dungeon) throws InterruptedException
    {
        if (utils.hasScriptVar(dungeon, BATTLEFIELD_COMMANDER_KILLED))
        {
            return utils.getBooleanScriptVar(dungeon, BATTLEFIELD_COMMANDER_KILLED);
        }
        else 
        {
            utils.setScriptVar(dungeon, BATTLEFIELD_COMMANDER_KILLED, false);
            return false;
        }
    }
    public static void setIsGeneratorDestroyed(obj_id dungeon, boolean state) throws InterruptedException
    {
        utils.setScriptVar(dungeon, BATTLEFIELD_GENERATOR_DESTROYED, state);
    }
    public static boolean isGeneratorDestroyed(obj_id dungeon) throws InterruptedException
    {
        if (utils.hasScriptVar(dungeon, BATTLEFIELD_GENERATOR_DESTROYED))
        {
            return utils.getBooleanScriptVar(dungeon, BATTLEFIELD_GENERATOR_DESTROYED);
        }
        else 
        {
            utils.setScriptVar(dungeon, BATTLEFIELD_GENERATOR_DESTROYED, false);
            return false;
        }
    }
    public static void markAsDroidArmy(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, BATTLEFIELD_DROID_ARMY, true);
    }
    public static void markAsMiner(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, BATTLEFIELD_MINER, true);
    }
    public static void markAsDroidCorpse(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, BATTLEFIELD_DROID_CORPSE, true);
    }
    public static void markAsVolcanoCommander(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, VOLCANO_THREE_IS_BOSS, true);
    }
    public static boolean isVolcanoCommander(obj_id npc) throws InterruptedException {
        return utils.hasScriptVar(npc, VOLCANO_THREE_IS_BOSS) && utils.getBooleanScriptVar(npc, VOLCANO_THREE_IS_BOSS);
    }
    public static void markAsVolcanoCorpse(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, VOLCANO_THREE_IS_CORPSE, true);
    }
    public static boolean isVolcanoCorpse(obj_id npc) throws InterruptedException {
        return utils.hasScriptVar(npc, VOLCANO_THREE_IS_CORPSE) && utils.getBooleanScriptVar(npc, VOLCANO_THREE_IS_CORPSE);
    }
    public static void markAsVolcanoBeetle(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, VOLCANO_FOUR_IS_BEETLE, true);
    }
    public static boolean isVolcanoBeetle(obj_id npc) throws InterruptedException {
        return utils.hasScriptVar(npc, VOLCANO_FOUR_IS_BEETLE) && utils.getBooleanScriptVar(npc, VOLCANO_FOUR_IS_BEETLE);
    }
    public static void markAsHkCorpse(obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(npc, VOLCANO_FINAL_IS_CORPSE, true);
    }
    public static boolean isHkCorpse(obj_id npc) throws InterruptedException {
        return utils.hasScriptVar(npc, VOLCANO_FINAL_IS_CORPSE) && utils.getBooleanScriptVar(npc, VOLCANO_FINAL_IS_CORPSE);
    }
    public static dictionary getSessionDict(obj_id subject) throws InterruptedException
    {
        dictionary dict = new dictionary();
        int session = getSession(subject);
        dict.put(MESSAGE_SESSION, session);
        return dict;
    }
    public static dictionary getSessionDict(obj_id subject, String uniqueId) throws InterruptedException
    {
        dictionary dict = new dictionary();
        int session = getSession(subject, uniqueId);
        dict.put(MESSAGE_SESSION + "." + uniqueId, session);
        return dict;
    }
    public static int getSession(obj_id subject) throws InterruptedException
    {
        if (!isIdValid(subject) || !exists(subject))
        {
            return -1;
        }
        if (utils.hasScriptVar(subject, MESSAGE_SESSION))
        {
            return utils.getIntScriptVar(subject, MESSAGE_SESSION);
        }
        else 
        {
            utils.setScriptVar(subject, MESSAGE_SESSION, 0);
            return 0;
        }
    }
    public static int getSession(obj_id subject, String uniqueId) throws InterruptedException
    {
        if (!isIdValid(subject) || !exists(subject))
        {
            return -1;
        }
        String uniqueSession = MESSAGE_SESSION + "." + uniqueId;
        if (utils.hasScriptVar(subject, uniqueSession))
        {
            return utils.getIntScriptVar(subject, uniqueSession);
        }
        else 
        {
            utils.setScriptVar(subject, uniqueSession, 0);
            return 0;
        }
    }
    public static boolean verifySession(obj_id subject, dictionary dict) throws InterruptedException
    {
        int passed = dict.getInt(MESSAGE_SESSION);
        int current = getSession(subject);
        return (current == passed);
    }
    public static boolean verifySession(obj_id subject, dictionary dict, String uniqueId) throws InterruptedException
    {
        int passed = dict.getInt(MESSAGE_SESSION + "." + uniqueId);
        int current = getSession(subject, uniqueId);
        doLogging("verifySession", "passed / current: " + passed + " / " + current);
        return (current == passed);
    }
    public static void bumpSession(obj_id subject) throws InterruptedException
    {
        int session = 0;
        if (utils.hasScriptVar(subject, MESSAGE_SESSION))
        {
            session = utils.getIntScriptVar(subject, MESSAGE_SESSION);
        }
        session += 1;
        utils.setScriptVar(subject, MESSAGE_SESSION, session);
    }
    public static void bumpSession(obj_id subject, String uniqueId) throws InterruptedException
    {
        String uniqueSession = MESSAGE_SESSION + "." + uniqueId;
        int session = 0;
        if (utils.hasScriptVar(subject, uniqueSession))
        {
            session = utils.getIntScriptVar(subject, uniqueSession);
        }
        session += 1;
        utils.setScriptVar(subject, uniqueSession, session);
    }
    public static boolean isCellPublic(obj_id dungeon, String cellName) throws InterruptedException {
        dungeon = getTop(dungeon);
        obj_id cellId = getCellId(dungeon, cellName);
        return isIdValid(cellId) && permissionsIsPublic(cellId);
    }
    public static void makeCellPublic(obj_id dungeon, String cellName) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id cellId = getCellId(dungeon, cellName);
        if (!isIdValid(cellId))
        {
            return;
        }
        permissionsMakePublic(cellId);
    }
    public static void makeCellPrivate(obj_id dungeon, String cellName) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id cellId = getCellId(dungeon, cellName);
        if (!isIdValid(cellId))
        {
            return;
        }
        permissionsMakePrivate(cellId);
    }
    public static obj_id getTop(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return null;
        }
        obj_id top = getTopMostContainer(item);
        if (isIdValid(top) && top != item)
        {
            item = top;
        }
        return item;
    }
    public static obj_id[] getPlayersInDungeon(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id[] cellIds = getCellIds(dungeon);
        if (cellIds == null || cellIds.length == 0)
        {
            return null;
        }
        Vector eventPlayers = new Vector();
        eventPlayers.setSize(0);
        obj_id[] contents;
        for (obj_id cellId : cellIds) {
            contents = getContents(cellId);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    if (isPlayer(content)) {
                        eventPlayers = utils.addElement(eventPlayers, content);
                    }
                }
            }
        }
        if (eventPlayers == null)
        {
            return null;
        }
        obj_id[] convertedArray = new obj_id[eventPlayers.size()];
        eventPlayers.toArray(convertedArray);
        if (convertedArray.length > 0)
        {
            return convertedArray;
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getValidTargetsInDungeon(obj_id dungeon) throws InterruptedException
    {
        obj_id[] players = getPlayersInDungeon(dungeon);
        obj_id[] pets = getObjectsInDungeonWithScript(dungeon, "ai.pet");
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        if (players != null && players.length > 0)
        {
            for (obj_id player : players) {
                if (!isIncapacitated(player) && !isDead(player)) {
                    utils.addElement(validTargets, player);
                }
            }
        }
        if (pets != null && pets.length > 0)
        {
            for (obj_id pet : pets) {
                if (!isIncapacitated(pet) && !isDead(pet)) {
                    utils.addElement(validTargets, pet);
                }
            }
        }
        if (validTargets.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[validTargets.size()];
        validTargets.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getValidTargetsInCell(obj_id dungeon, String cell) throws InterruptedException
    {
        return getNonStealthedTargetsInCell(dungeon, cell);
    }
    public static obj_id[] getNonStealthedTargetsInCell(obj_id dungeon, String cell) throws InterruptedException
    {
        obj_id[] players = getPlayersInCell(dungeon, cell);
        if (players == null || players.length == 0)
        {
            return null;
        }
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        for (obj_id player : players) {
            if (!isIncapacitated(player) && !isDead(player) && !stealth.hasInvisibleBuff(player)) {
                utils.addElement(validTargets, player);
            }
        }
        if (validTargets.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[validTargets.size()];
        validTargets.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getPlayersInCell(obj_id dungeon, String cell) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id cellId = getCellId(dungeon, cell);
        if (!isIdValid(cellId))
        {
            return null;
        }
        Vector players = new Vector();
        players.setSize(0);
        obj_id[] contents = getContents(cellId);
        if (contents != null && contents.length > 0)
        {
            for (obj_id content : contents) {
                if (isPlayer(content)) {
                    utils.addElement(players, content);
                }
            }
        }
        if (players.size() == 0)
        {
            return null;
        }
        obj_id[] returnPlayers = new obj_id[players.size()];
        players.toArray(returnPlayers);
        return returnPlayers;
    }
    public static obj_id[] getPlayersInCell(obj_id dungeon, obj_id cell) throws InterruptedException
    {
        String cellName = getCellName(cell);
        if (cellName == null || cellName.equals(""))
        {
            return null;
        }
        return getPlayersInCell(dungeon, cellName);
    }
    public static obj_id[] getPlayersInCellList(obj_id dungeon, String[] cells) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        obj_id[] searchCells = new obj_id[cells.length];
        for (int j = 0; j < cells.length; j++)
        {
            searchCells[j] = getCellId(dungeon, cells[j]);
        }
        if (searchCells.length == 0)
        {
            return null;
        }
        Vector players = new Vector();
        players.setSize(0);
        obj_id[] contents;
        for (obj_id searchCell : searchCells) {
            contents = getContents(searchCell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    if (isPlayer(content)) {
                        utils.addElement(players, content);
                    }
                }
            }
        }
        if (players.size() == 0)
        {
            return null;
        }
        obj_id[] returnPlayers = new obj_id[players.size()];
        players.toArray(returnPlayers);
        return returnPlayers;
    }
    public static obj_id[] getObjectsInDungeonWithScript(obj_id dungeon, String script) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!isIdValid(dungeon))
        {
            return null;
        }
        if (script == null || script.equals(""))
        {
            return null;
        }
        obj_id[] cells = getCellIds(dungeon);
        if (cells == null || cells.length == 0)
        {
            return null;
        }
        Vector objectsWithScript = new Vector();
        objectsWithScript.setSize(0);
        obj_id[] contents;
        for (obj_id cell : cells) {
            contents = getContents(cell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    if (hasScript(content, script)) {
                        utils.addElement(objectsWithScript, content);
                    }
                }
            }
        }
        if (objectsWithScript.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[objectsWithScript.size()];
        objectsWithScript.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getAllObjectsInDungeon(obj_id dungeon) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!isIdValid(dungeon))
        {
            return null;
        }
        obj_id[] cells = getCellIds(dungeon);
        if (cells == null || cells.length == 0)
        {
            return null;
        }
        Vector objects = new Vector();
        objects.setSize(0);
        obj_id[] contents;
        for (obj_id cell : cells) {
            contents = getContents(cell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    utils.addElement(objects, content);
                }
            }
        }
        if (objects.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[objects.size()];
        objects.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getObjectsInDungeonWithObjVar(obj_id dungeon, String objvar) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!isIdValid(dungeon))
        {
            return null;
        }
        if (objvar == null || objvar.equals(""))
        {
            return null;
        }
        obj_id[] cells = getCellIds(dungeon);
        if (cells == null || cells.length == 0)
        {
            return null;
        }
        Vector objectsWithObjVar = new Vector();
        objectsWithObjVar.setSize(0);
        obj_id[] contents;
        for (obj_id cell : cells) {
            contents = getContents(cell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    if (hasObjVar(content, objvar)) {
                        utils.addElement(objectsWithObjVar, content);
                    }
                }
            }
        }
        if (objectsWithObjVar.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[objectsWithObjVar.size()];
        objectsWithObjVar.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getObjectsInCellWithObjVar(obj_id dungeon, String cellName, String objvar) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!isIdValid(dungeon))
        {
            return null;
        }
        if (objvar == null || objvar.equals(""))
        {
            return null;
        }
        obj_id checkCell = getCellId(dungeon, cellName);
        if (!isIdValid(checkCell))
        {
            return null;
        }
        Vector objectsWithObjVar = new Vector();
        objectsWithObjVar.setSize(0);
        obj_id[] contents = getContents(checkCell);
        if (contents != null && contents.length > 0)
        {
            for (obj_id content : contents) {
                if (hasObjVar(content, objvar)) {
                    utils.addElement(objectsWithObjVar, content);
                }
            }
        }
        if (objectsWithObjVar.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[objectsWithObjVar.size()];
        objectsWithObjVar.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getObjectsInDungeonWithScriptVar(obj_id dungeon, String scriptVar) throws InterruptedException
    {
        dungeon = getTop(dungeon);
        if (!isIdValid(dungeon))
        {
            return null;
        }
        if (scriptVar == null || scriptVar.equals(""))
        {
            return null;
        }
        obj_id[] cells = getCellIds(dungeon);
        if (cells == null || cells.length == 0)
        {
            return null;
        }
        Vector objectsWithScriptVar = new Vector();
        objectsWithScriptVar.setSize(0);
        obj_id[] contents;
        for (obj_id cell : cells) {
            contents = getContents(cell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    if (utils.hasScriptVar(content, scriptVar)) {
                        utils.addElement(objectsWithScriptVar, content);
                    }
                }
            }
        }
        if (objectsWithScriptVar.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[objectsWithScriptVar.size()];
        objectsWithScriptVar.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getValidTargetsInRadius(obj_id center, float range) throws InterruptedException
    {
        obj_id[] pets = getNPCsInRange(center, range);
        obj_id[] players = getPlayerCreaturesInRange(center, range);
        Vector targets = new Vector();
        targets.setSize(0);
        if (pets != null && pets.length != 0)
        {
            for (obj_id pet : pets) {
                if (isIdValid(pet) && exists(pet) && (hasScript(pet, "ai.pet") || beast_lib.isBeast(pet) && canSee(center, pet))) {
                    utils.addElement(targets, pet);
                }
            }
        }
        if (players != null && players.length != 0)
        {
            for (obj_id player : players) {
                if (isIdValid(player) && exists(player) && !isIncapacitated(player) && canSee(center, player)) {
                    utils.addElement(targets, player);
                }
            }
        }
        if (targets.size() == 0)
        {
            doLogging("getValidTargetsInRadius", "There were no valid pets or players in the radial area");
            return null;
        }
        obj_id[] validTargets = new obj_id[targets.size()];
        targets.toArray(validTargets);
        return validTargets;
    }
    public static obj_id[] getValidTargetsInRadiusIgnoreLOS(obj_id center, float range) throws InterruptedException
    {
        obj_id[] pets = getNPCsInRange(center, range);
        obj_id[] players = getPlayerCreaturesInRange(center, range);
        Vector targets = new Vector();
        targets.setSize(0);
        if (pets != null && pets.length != 0)
        {
            for (obj_id pet : pets) {
                if (isIdValid(pet) && exists(pet) && (hasScript(pet, "ai.pet") || beast_lib.isBeast(pet))) {
                    utils.addElement(targets, pet);
                }
            }
        }
        if (players != null && players.length != 0)
        {
            for (obj_id player : players) {
                if (isIdValid(player) && exists(player) && !isIncapacitated(player)) {
                    utils.addElement(targets, player);
                }
            }
        }
        if (targets.size() == 0)
        {
            doLogging("getValidTargetsInRadius", "There were no valid pets or players in the radial area");
            return null;
        }
        obj_id[] validTargets = new obj_id[targets.size()];
        targets.toArray(validTargets);
        return validTargets;
    }
    public static obj_id[] getValidTargetsInCone(obj_id self, obj_id target, float range, float cone) throws InterruptedException
    {
        obj_id[] pets = getNPCsInCone(self, target, range, cone);
        obj_id[] players = getPlayerCreaturesInCone(self, target, range, cone);
        Vector targets = new Vector();
        targets.setSize(0);
        if (pets != null && pets.length != 0)
        {
            for (obj_id pet : pets) {
                if (isIdValid(pet) && exists(pet) && hasScript(pet, "ai.pet")) {
                    utils.addElement(targets, pet);
                }
            }
        }
        if (players != null && players.length != 0)
        {
            for (obj_id player : players) {
                if (isIdValid(player) && exists(player) && !isIncapacitated(player)) {
                    utils.addElement(targets, player);
                }
            }
        }
        if (targets.size() == 0)
        {
            doLogging("getValidTargetsInCone", "There were no valid pets or players in the cone area");
            return null;
        }
        obj_id[] validTargets = new obj_id[targets.size()];
        targets.toArray(validTargets);
        return validTargets;
    }
    public static obj_id[] getValidPlayersInRadius(obj_id self, float range) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(self, range);
        Vector targets = new Vector();
        targets.setSize(0);
        if (players != null && players.length != 0)
        {
            for (obj_id player : players) {
                if (isIdValid(player) && exists(player) && !isIncapacitated(player) && canSee(self, player)) {
                    utils.addElement(targets, player);
                }
            }
        }
        if (targets.size() == 0)
        {
            doLogging("getValidPlayersInRadius", "There were no valid players in the radial area");
            return null;
        }
        obj_id[] validTargets = new obj_id[targets.size()];
        targets.toArray(validTargets);
        return validTargets;
    }
    public static void setDungeonCleanOutTimer(obj_id dungeon_controller) throws InterruptedException
    {
        setDungeonCleanOutTimer(dungeon_controller, 300);
    }
    public static void setDungeonCleanOutTimer(obj_id dungeon_controller, int timeAddition) throws InterruptedException
    {
        int newEndTime = getGameTime() + timeAddition;
        setObjVar(dungeon_controller, space_dungeon.VAR_DUNGEON_END_TIME, newEndTime);
        int sessionId = space_dungeon.getDungeonSessionId(dungeon_controller);
        dictionary dict = new dictionary();
        dict.put("sessionId", sessionId);
        messageTo(dungeon_controller, "handleSessionTimerUpdate", dict, 0, false);
        instance.setClock(dungeon_controller, 300);
    }
    public static void setInterest(obj_id npc) throws InterruptedException
    {
        if (!isIdValid(npc))
        {
            return;
        }
        setAttributeInterested(npc, attrib.ALL);
        setAttributeAttained(npc, attrib.ALL);
    }
    public static void sendCompletionSignal(obj_id dungeon, String signalName) throws InterruptedException
    {
        obj_id[] players = instance.getPlayersInInstanceArea(dungeon);
        groundquests.sendSignal(players, signalName);
    }
    public static void cleanupNpc(obj_id npc) throws InterruptedException
    {
        if (!isIdValid(npc))
        {
            return;
        }
        if (isPlayer(npc))
        {
            return;
        }
        if (isMob(npc))
        {
            detachAllScripts(npc);
            removeAllObjVars(npc);
            kill(npc);
        }
        else 
        {
            cleanupObject(npc);
        }
        destroyObject(npc);
    }
    public static void cleanupObject(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object))
        {
            return;
        }
        if (isPlayer(object))
        {
            return;
        }
        if (!hasScript(object, "systems.dungeon_sequencer.sequence_controller") && !vehicle.isBattlefieldVehicle(object))
        {
            detachAllScripts(object);
        }
        trial.unregisterObjectWithSequencer(trial.getParent(object), object);
        removeAllObjVars(object);
        if (isMob(object))
        {
            kill(object);
        }
        obj_id[] cells = getCellIds(object);
        if (cells != null)
        {
            obj_id[] objects = getAllObjectsInDungeon(object);
            if (objects != null && objects.length > 0)
            {
                for (obj_id object1 : objects) {
                    if (isPlayer(object1)) {
                        expelFromBuilding(object1);
                    } else {
                        cleanupObject(object1);
                    }
                }
            }
        }
        if (vehicle.isVehicle(object))
        {
            if (vehicle.isBattlefieldVehicle(object))
            {
                queueCommand(getRiderId(object), (1988230683), object, getName(object), COMMAND_PRIORITY_FRONT);
            }
            else 
            {
                queueCommand(getRiderId(object), (117012717), object, getName(object), COMMAND_PRIORITY_FRONT);
            }
        }
        destroyObject(object);
    }
    public static void cleanupNpc(obj_id[] npc) throws InterruptedException
    {
        cleanupObject(npc);
    }
    public static void cleanupObject(obj_id[] objects) throws InterruptedException
    {
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            cleanupObject(object);
        }
    }
    public static void setHp(obj_id target, int ammount) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (ammount < 0)
        {
            return;
        }
        if (isMob(target) && !vehicle.isVehicle(target))
        {
            setMaxAttrib(target, HEALTH, ammount);
            setAttrib(target, HEALTH, ammount);
        }
        else 
        {
            setMaxHitpoints(target, ammount);
            setHitpoints(target, ammount);
        }
    }
    public static void setParent(obj_id parent, obj_id child, boolean permenant) throws InterruptedException
    {
        if (!isIdValid(child) || !isIdValid(parent))
        {
            doLogging("setParent", "Parent or child object was null");
            return;
        }
        if (permenant)
        {
            setObjVar(child, PARENT, parent);
        }
        else 
        {
            utils.setScriptVar(child, PARENT, parent);
            addToChildArray(parent, child);
        }
    }
    public static void addToChildArray(obj_id parent, obj_id child) throws InterruptedException
    {
        Vector children = new Vector();
        children.setSize(0);
        if (utils.hasScriptVar(parent, ARRAY_CHILDREN))
        {
            children = utils.getResizeableObjIdArrayScriptVar(parent, ARRAY_CHILDREN);
            utils.removeScriptVar(parent, ARRAY_CHILDREN);
        }
        utils.addElement(children, child);
        utils.setScriptVar(parent, ARRAY_CHILDREN, children);
    }
    public static obj_id[] getChildArray(obj_id parent) throws InterruptedException
    {
        if (!utils.hasScriptVar(parent, ARRAY_CHILDREN))
        {
            return null;
        }
        Vector tempArray = utils.getResizeableObjIdArrayScriptVar(parent, ARRAY_CHILDREN);
        obj_id[] _tempArray = new obj_id[0];
        if (tempArray != null)
        {
            _tempArray = new obj_id[tempArray.size()];
            tempArray.toArray(_tempArray);
        }
        return _tempArray;
    }
    public static obj_id getParent(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object) || !exists(object))
        {
            doLogging("getParent", "Object was null");
            return null;
        }
        if (utils.hasScriptVar(object, PARENT))
        {
            return utils.getObjIdScriptVar(object, PARENT);
        }
        if (hasObjVar(object, PARENT))
        {
            return getObjIdObjVar(object, PARENT);
        }
        return object;
    }
    public static boolean isChild(obj_id parent, obj_id child) throws InterruptedException {
        if (!isIdValid(parent) || !isIdValid(child)) {
            return false;
        }
        if (utils.hasScriptVar(child, PARENT)) {
            return (utils.getObjIdScriptVar(child, PARENT) == parent);
        }
        return hasObjVar(child, PARENT) && (getObjIdObjVar(child, PARENT) == parent);
    }
    public static obj_id[] getAllChildrenInDungeon(obj_id dungeon, obj_id parent) throws InterruptedException
    {
        if (!isIdValid(dungeon) || !isIdValid(parent))
        {
            return null;
        }
        obj_id[] cells = getCellIds(dungeon);
        if (cells == null || cells.length == 0)
        {
            return null;
        }
        Vector objects = new Vector();
        objects.setSize(0);
        obj_id[] contents;
        for (obj_id cell : cells) {
            contents = getContents(cell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    if (isChild(parent, content)) {
                        utils.addElement(objects, content);
                    }
                }
            }
        }
        if (objects.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[objects.size()];
        objects.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getChildrenInRange(obj_id baseObject, obj_id parent, float range) throws InterruptedException
    {
        if (!isIdValid(baseObject) || !isIdValid(parent))
        {
            return null;
        }
        location baseLoc = getLocation(baseObject);
        return getChildrenInRange(baseLoc, parent, range);
    }
    public static obj_id[] getChildrenInRange(location baseLoc, obj_id parent, float range) throws InterruptedException
    {
        if (!isIdValid(parent))
        {
            return null;
        }
        obj_id[] objects = getObjectsInRange(baseLoc, range);
        if (objects == null || objects.length == 0)
        {
            return null;
        }
        Vector children = new Vector();
        children.setSize(0);
        for (obj_id object : objects) {
            if (isChild(parent, object)) {
                utils.addElement(children, object);
            }
        }
        if (children.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[children.size()];
        children.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getObjectsInRangeWithScriptVar(obj_id baseObj, String scriptVar, float range) throws InterruptedException
    {
        if (!isIdValid(baseObj) || scriptVar.equals(""))
        {
            return null;
        }
        return getObjectsInRangeWithScriptVar(getLocation(baseObj), scriptVar, range);
    }
    public static obj_id[] getObjectsInRangeWithScriptVar(location baseLoc, String scriptVar, float range) throws InterruptedException
    {
        if (scriptVar.equals(""))
        {
            return null;
        }
        obj_id[] objects = getObjectsInRange(baseLoc, range);
        if (objects == null || objects.length == 0)
        {
            return null;
        }
        Vector withVar = new Vector();
        withVar.setSize(0);
        for (obj_id object : objects) {
            if (utils.hasScriptVar(object, scriptVar)) {
                utils.addElement(withVar, object);
            }
        }
        if (withVar.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[withVar.size()];
        withVar.toArray(returnList);
        return returnList;
    }
    public static void prepareCorpse(obj_id corpse) throws InterruptedException
    {
        detachScript(corpse, "ai.ai");
    }
    public static void playMusicInInstance(obj_id dungeon, String sound) throws InterruptedException
    {
        space_dungeon.playMusicInInstance(dungeon, sound);
    }
    public static void sendInstanceSystemMessage(obj_id dungeon, string_id message) throws InterruptedException
    {
        space_dungeon.sendInstanceSystemMessage(dungeon, message);
    }
    public static obj_id getClosestValidTarget(obj_id base, obj_id[] targets) throws InterruptedException
    {
        return getClosest(base, targets);
    }
    public static obj_id getClosest(obj_id base, obj_id[] targets) throws InterruptedException
    {
        obj_id prefered = obj_id.NULL_ID;
        for (obj_id target : targets) {
            if (!isDead(target)) {
                if (!isIdValid(prefered)) {
                    prefered = target;
                } else {
                    if (getDistance(base, target) < getDistance(base, prefered) && canSee(base, target) && !stealth.hasInvisibleBuff(target)) {
                        prefered = target;
                    }
                }
            }
        }
        return prefered;
    }
    public static obj_id getSecondClosest(obj_id base, obj_id[] targets) throws InterruptedException
    {
        obj_id prefered = obj_id.NULL_ID;
        obj_id previous = obj_id.NULL_ID;
        for (obj_id target : targets) {
            if (!isDead(target)) {
                if (!isIdValid(prefered)) {
                    prefered = target;
                } else {
                    if (getDistance(base, target) < getDistance(base, prefered) && canSee(base, target)) {
                        previous = prefered;
                        prefered = target;
                    }
                }
            }
        }
        return previous;
    }
    public static obj_id getClosestObject(obj_id base, obj_id[] targets) throws InterruptedException
    {
        obj_id prefered = null;
        for (obj_id target : targets) {
            if (!isIdValid(prefered)) {
                prefered = target;
            } else {
                if (getDistance(base, target) < getDistance(base, prefered) && canSee(base, target)) {
                    prefered = target;
                }
            }
        }
        return prefered;
    }
    public static void markAsTempObject(obj_id object, boolean permenant) throws InterruptedException
    {
        if (permenant)
        {
            setObjVar(object, TEMP_OBJECT, true);
        }
        else 
        {
            utils.setScriptVar(object, TEMP_OBJECT, true);
        }
    }
    public static boolean isTempObject(obj_id object) throws InterruptedException {
        return utils.hasScriptVar(object, TEMP_OBJECT) || hasObjVar(object, TEMP_OBJECT);
    }
    public static obj_id[] getTempObjectsInDungeon(obj_id dungeon) throws InterruptedException
    {
        obj_id[] objects = getAllObjectsInDungeon(dungeon);
        if (objects == null || objects.length == 0)
        {
            return null;
        }
        Vector tempObjects = new Vector();
        tempObjects.setSize(0);
        for (obj_id object : objects) {
            if (isTempObject(object)) {
                utils.addElement(tempObjects, object);
            }
        }
        if (tempObjects.size() == 0)
        {
            return null;
        }
        obj_id[] _tempObjects = new obj_id[tempObjects.size()];
        tempObjects.toArray(_tempObjects);
        return _tempObjects;
    }
    public static obj_id[] getTempObjectsInBuildoutArea(obj_id baseObject) throws InterruptedException
    {
        obj_id[] objects = utils.getAllObjectsInBuildoutArea(baseObject);
        if (objects == null || objects.length == 0)
        {
            return null;
        }
        Vector tempObjects = new Vector();
        tempObjects.setSize(0);
        for (obj_id object : objects) {
            if (trial.isTempObject(object)) {
                tempObjects.add(object);
            }
        }
        if (tempObjects.size() == 0)
        {
            return null;
        }
        obj_id[] _tempObjects = new obj_id[tempObjects.size()];
        tempObjects.toArray(_tempObjects);
        return _tempObjects;
    }
    public static String getNearestPatrolPoint(obj_id actor, String[] patrolPointList) throws InterruptedException
    {
        int idx = getNearestPatrolPointIndex(actor, patrolPointList);
        return patrolPointList[idx];
    }
    public static int getNearestPatrolPointIndex(obj_id actor, String[] patrolPointList) throws InterruptedException
    {
        obj_id[] points = getAllObjectsWithObjVar(getLocation(actor), 2000.0f, "sequence_controller.patrolPointName");
        Vector ppl = new Vector(Arrays.asList(patrolPointList));
        float distance = 2000.0f;
        int idx = 0;
        String testName;
        for (obj_id point : points) {
            if (getParent(actor) != getParent(point)) {
                continue;
            }
            testName = getStringObjVar(point, "sequence_controller.patrolPointName");
            if (ppl.indexOf(testName) == -1) {
                continue;
            }
            if (getDistance(actor, point) < distance && canSee(actor, point)) {
                idx = ppl.lastIndexOf(testName);
                distance = getDistance(actor, point);
            }
        }
        return idx;
    }
    public static location[] convertPatrolPointNameToLoc(obj_id actor, String[] patrolPointList) throws InterruptedException
    {
        Vector locList = new Vector();
        locList.setSize(0);
        obj_id[] points;
        for (String aPatrolPointList : patrolPointList) {
            points = getAllObjectsWithObjVar(getLocation(actor), 2000.0f, "sequence_controller.patrolPointName");
            for (obj_id point : points) {
                if ((getStringObjVar(point, "sequence_controller.patrolPointName")).equals(aPatrolPointList) && trial.getParent(point) == trial.getParent(actor)) {
                    utils.addElement(locList, getLocation(point));
                }
            }
        }
        if (locList.size() == 0)
        {
            return null;
        }
        location[] returnList = new location[locList.size()];
        locList.toArray(returnList);
        location[] _locList = new location[locList.size()];
        locList.toArray(_locList);
        return _locList;
    }
    public static obj_id[] getObjectsInListWithObjVar(obj_id[] list, String objvar) throws InterruptedException
    {
        Vector targetObjects = new Vector();
        targetObjects.setSize(0);
        for (obj_id aList : list) {
            if (isIdValid(aList) && exists(aList) && hasObjVar(aList, objvar)) {
                utils.addElement(targetObjects, aList);
            }
        }
        if (targetObjects.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[targetObjects.size()];
        targetObjects.toArray(returnList);
        return returnList;
    }
    public static location[] shuffleByIndex(String type, location[] ppl, int idx) throws InterruptedException
    {
        location[] retarded = ppl.clone();
        Vector oldPpl = new Vector(Arrays.asList(retarded));
        Vector newPpl = new Vector();
        newPpl.setSize(0);
        if (type.equals("patrol"))
        {
            for (int i = idx; i < ppl.length; i++)
            {
                newPpl.add(ppl[i]);
                oldPpl.remove(ppl[i]);
            }
            for (int q = 0; q < idx; q++)
            {
                newPpl.addElement(oldPpl.get(q));
            }
        }
        if (type.equals("patrolOnce"))
        {
            newPpl.addAll(Arrays.asList(ppl).subList(idx, ppl.length));
        }
        if (type.equals("patrolFlip"))
        {
            newPpl.addAll(Arrays.asList(ppl).subList(idx, ppl.length));
            for (int q = ppl.length - 2; q > -1; q--)
            {
                newPpl.add(ppl[q]);
            }
            newPpl.addAll(Arrays.asList(ppl).subList(0, idx));
        }
        if (type.equals("patrolFlipOnce"))
        {
            newPpl.addAll(Arrays.asList(ppl).subList(idx, ppl.length));
        }
        if (type.equals("patrolRandom") || type.equals("patrolRandomOnce"))
        {
            newPpl = new Vector(Arrays.asList(ppl));
        }
        location[] _newPpl = new location[newPpl.size()];
        newPpl.toArray(_newPpl);
        return _newPpl;
    }
    public static obj_id[] getSpawnedChildren(obj_id object) throws InterruptedException
    {
        dictionary spawnList = utils.getDictionaryScriptVar(object, PROT_CHILD_ARRAY);
        Vector keyList = spawnList.getResizeableStringArray(PROT_CHILD_KEY_LIST);
        Vector allChildren = new Vector();
        allChildren.setSize(0);
        if (keyList == null || keyList.size() == 0)
        {
            return null;
        }
        Vector thisList;
        for (Object aKeyList : keyList) {
            thisList = spawnList.getResizeableObjIdArray(aKeyList);
            if (thisList == null || thisList.size() == 0) {
                continue;
            }
            allChildren.addAll(thisList);
        }
        obj_id[] returnList = new obj_id[allChildren.size()];
        allChildren.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getObjectsInInstanceBySpawnId(obj_id baseObject, String spawn_id) throws InterruptedException
    {
        dictionary spawnList = utils.getDictionaryScriptVar(trial.getParent(baseObject), PROT_CHILD_ARRAY);
        if (!spawnList.containsKey(spawn_id))
        {
            return null;
        }
        Vector thisList = spawnList.getResizeableObjIdArray(spawn_id);
        obj_id[] returnList = new obj_id[0];
        if (thisList != null)
        {
            returnList = new obj_id[thisList.size()];
            thisList.toArray(returnList);
        }
        return returnList;
    }
    public static obj_id[] getObjectsInInstanceBySpawnId(obj_id baseObject, String[] idList) throws InterruptedException
    {
        dictionary spawnList = utils.getDictionaryScriptVar(trial.getParent(baseObject), PROT_CHILD_ARRAY);
        Vector allSpawn = new Vector();
        allSpawn.setSize(0);
        for (String anIdList : idList) {
            if (!spawnList.containsKey(anIdList)) {
                continue;
            }
            Vector thisList = spawnList.getResizeableObjIdArray(anIdList);
            if (thisList == null || thisList.size() == 0) {
                continue;
            }
            allSpawn.addAll(thisList);
        }
        obj_id[] returnList = new obj_id[allSpawn.size()];
        allSpawn.toArray(returnList);
        return returnList;
    }
    public static obj_id[] getObjectsInInstanceByObjVar(obj_id baseObject, String obj_var) throws InterruptedException
    {
        obj_id[] allChildren = getSpawnedChildren(trial.getParent(baseObject));
        return getObjectsInListWithObjVar(allChildren, obj_var);
    }
    public static obj_id[] getObjectsInInstanceByObjVar(obj_id baseObject, String[] idList) throws InterruptedException
    {
        if (idList == null || idList.length == 0)
        {
            return null;
        }
        Vector allSpawns = new Vector();
        allSpawns.setSize(0);
        obj_id[] thisVector;
        for (String anIdList : idList) {
            thisVector = getObjectsInInstanceByObjVar(baseObject, anIdList);
            Collections.addAll(allSpawns, thisVector);
        }
        if (allSpawns.size() == 0)
        {
            return null;
        }
        obj_id[] _allSpawns = new obj_id[allSpawns.size()];
        allSpawns.toArray(_allSpawns);
        return _allSpawns;
    }
    public static void sendSequenceTrigger(String sequence_trigger) throws InterruptedException
    {
        obj_id instance_id = instance.getAreaInstanceController(getSelf());
        if (!isIdValid(instance_id) || !exists(instance_id))
        {
            return;
        }
        dictionary dict = trial.getSessionDict(instance_id);
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", sequence_trigger);
        messageTo(instance_id, "triggerFired", dict, 1.0f, false);
    }
    public static boolean registerObjectWithSequencer(obj_id object) throws InterruptedException {
        obj_id instance_id = instance.getAreaInstanceController(object);
        return isIdValid(instance_id) && registerObjectWithSequencer(instance_id, object);
    }
    public static boolean registerObjectWithSequencer(obj_id sequencer, obj_id object) throws InterruptedException
    {
        dictionary dict = trial.getSessionDict(sequencer);
        dict.put("object", object);
        messageTo(sequencer, "registerObjectWithSequencer", dict, 0.0f, false);
        return true;
    }
    public static boolean unregisterObjectWithSequencer(obj_id object) throws InterruptedException {
        obj_id instance_id = instance.getAreaInstanceController(object);
        return isIdValid(instance_id) && unregisterObjectWithSequencer(instance_id, object);
    }
    public static boolean unregisterObjectWithSequencer(obj_id sequencer, obj_id object) throws InterruptedException
    {
        if (!isIdValid(sequencer) && sequencer == getSelf())
        {
            return false;
        }
        dictionary dict = trial.getSessionDict(sequencer);
        dict.put("object", object);
        String spawn_id = "none";
        if (isIdValid(object) && exists(object))
        {
            spawn_id = hasObjVar(object, "spawn_id") ? getStringObjVar(object, "spawn_id") : "none";
        }
        dict.put("spawn_id", spawn_id);
        messageTo(sequencer, "unregisterObjectWithSequencer", dict, 0.0f, false);
        return true;
    }
    public static void storeSpawnedChild(obj_id self, obj_id child, String spawn_id) throws InterruptedException
    {
        dictionary childArray = utils.getDictionaryScriptVar(self, trial.PROT_CHILD_ARRAY);
        Vector thisList = new Vector();
        thisList.setSize(0);
        Vector all_spawn_id = new Vector();
        all_spawn_id.setSize(0);
        if (childArray.containsKey(spawn_id))
        {
            thisList = childArray.getResizeableObjIdArray(spawn_id);
        }
        if (childArray.containsKey(trial.PROT_CHILD_KEY_LIST))
        {
            all_spawn_id = childArray.getResizeableStringArray(trial.PROT_CHILD_KEY_LIST);
        }
        thisList.add(child);
        if (!all_spawn_id.contains(spawn_id))
        {
            all_spawn_id.add(spawn_id);
        }
        childArray.put(spawn_id, thisList);
        childArray.put(trial.PROT_CHILD_KEY_LIST, all_spawn_id);
        utils.setScriptVar(self, trial.PROT_CHILD_ARRAY, childArray);
    }
    public static void setFullPathData(obj_id object) throws InterruptedException
    {
        obj_id parent = getParent(object);
        String[] pathArray = getFullPathData(parent);
        if (pathArray == null)
        {
            return;
        }
        utils.setScriptVar(object, PATROL_PATH_FULL_DATA, pathArray);
    }
    public static String[] getFullPathData(obj_id parent) throws InterruptedException
    {
        if (!isIdValid(parent) || !exists(parent))
        {
            return null;
        }
        if (utils.hasScriptVar(parent, PATROL_PATH_FULL_DATA))
        {
            return utils.getStringArrayScriptVar(parent, PATROL_PATH_FULL_DATA);
        }
        String[] path_column = dataTableGetStringColumnNoDefaults(getStringObjVar(parent, "instance.data_table"), "paths");
        if (path_column == null || path_column.length == 0)
        {
            return null;
        }
        return path_column;
    }
    public static void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/library.trial/" + section, message);
        }
    }
    public static obj_id createSchedulerNPC(obj_id parent, obj_id patrolPoint, String npcName) throws InterruptedException
    {
        location loc = getLocation(parent);
        return createSchedulerNPC(parent, patrolPoint, npcName, loc);
    }
    public static obj_id createSchedulerNPC(obj_id parent, obj_id patrolPoint, String npcName, location loc) throws InterruptedException
    {
        if (!isIdValid(parent) || !exists(parent))
        {
            return null;
        }
        obj_id kitParent = trial.getParent(parent);
        if (!isIdValid(kitParent) || !exists(kitParent))
        {
            return null;
        }
        obj_id npc = create.object(npcName, loc);
        if (!isIdValid(npc) || !exists(npc))
        {
            return null;
        }
        trial.markAsTempObject(npc, true);
        trial.setParent(kitParent, npc, true);
        trial.setInterest(npc);
        setObjVar(npc, "spawn_id", "gcwPatrol");
        trial.storeSpawnedChild(kitParent, npc, "gcwPatrol");
        String patrol = getStringObjVar(parent, "patrolPoint");
        if (patrol != null && patrol.length() > 0)
        {
            dictionary path_data = utils.hasScriptVar(kitParent, trial.SEQUENCER_PATH_DATA) ? utils.getDictionaryScriptVar(kitParent, trial.SEQUENCER_PATH_DATA) : null;
            if (path_data != null && !path_data.isEmpty())
            {
                utils.setScriptVar(npc, trial.SEQUENCER_PATH_DATA, utils.getDictionaryScriptVar(kitParent, trial.SEQUENCER_PATH_DATA));
            }
            setObjVar(npc, "patrol_path", patrol);
            setHibernationDelay(npc, 3600.0f);
            removeObjVar(npc, gcw.GCW_ENTERTAINMENT_FLAG);
            aiEquipPrimaryWeapon(npc);
        }
        else 
        {
            utils.setScriptVar(patrolPoint, gcw.GCW_NPC_SCRIPTVAR_FLAG, npc);
        }
        attachScript(npc, "systems.dungeon_sequencer.ai_controller");
        return npc;
    }
    public static boolean addNonInstanceFactionParticipant(obj_id who, obj_id target) throws InterruptedException
    {
        if (!isIdValid(who) || !exists(who) || !isPlayer(who) || !isIdValid(target) || !exists(target))
        {
            return false;
        }
        if (gcw.getGcwCityInvasionPhase(target) <= 0)
        {
            return false;
        }
        obj_id parent = trial.getParent(target);
        if (!isIdValid(parent) || !exists(parent))
        {
            return false;
        }
        obj_id lastParent = null;
        while (!hasScript(parent, "systems.dungeon_sequencer.sequence_controller") && parent != lastParent)
        {
            lastParent = parent;
            parent = trial.getParent(parent);
            if (!isIdValid(parent) || !exists(parent))
            {
                return false;
            }
        }
        String participantScriptVar = "";
        if (factions.isRebelorRebelHelper(who))
        {
            participantScriptVar = "gcw.rebel.participants";
        }
        else if (factions.isImperialorImperialHelper(who))
        {
            participantScriptVar = "gcw.imperial.participants";
        }
        if (participantScriptVar.length() < 1)
        {
            CustomerServiceLog("gcw_city_invasion", "trial.addNonInstanceFactionParticipant: Player: " + who + " did not have the faction affiliation needed to be added to a participant list.");
            return false;
        }
        Vector sideParticipants = new Vector();
        sideParticipants.setSize(0);
        if (utils.hasScriptVar(parent, participantScriptVar))
        {
            sideParticipants = utils.getResizeableObjIdArrayScriptVar(parent, participantScriptVar);
            if (sideParticipants != null && utils.isElementInArray(sideParticipants, who))
            {
                CustomerServiceLog("gcw_city_invasion", "trial.addNonInstanceFactionParticipant: Player: " + who + " was already on the list as a paricipant so the operation was aborted.");
                return false;
            }
        }
        utils.addElement(sideParticipants, who);
        utils.setScriptVar(parent, participantScriptVar, sideParticipants);
        CustomerServiceLog("gcw_city_invasion", "trial.addNonInstanceFactionParticipant: Player: " + who + " has been added as a valid participant of the GCW city invasion for city object: " + parent);
        return true;
    }
    public static boolean removeNonInstanceFactionParticipant(obj_id who, obj_id target) throws InterruptedException
    {
        if (!isIdValid(who) || !exists(who) || !isPlayer(who) || !isIdValid(target) || !exists(target))
        {
            return false;
        }
        obj_id parent = trial.getParent(target);
        if (!isIdValid(parent) || !exists(parent))
        {
            return false;
        }
        obj_id lastParent = null;
        while (!hasScript(parent, "systems.dungeon_sequencer.sequence_controller") && parent != lastParent)
        {
            lastParent = parent;
            parent = trial.getParent(parent);
            if (!isIdValid(parent) || !exists(parent))
            {
                return false;
            }
        }
        String participantScriptVar = "";
        if (factions.isRebelorRebelHelper(who))
        {
            participantScriptVar = "gcw.rebel.participants";
        }
        else if (factions.isImperialorImperialHelper(who))
        {
            participantScriptVar = "gcw.imperial.participants";
        }
        if (participantScriptVar.length() < 1)
        {
            return false;
        }
        Vector sideParticipants = new Vector();
        sideParticipants.setSize(0);
        if (utils.hasScriptVar(parent, participantScriptVar))
        {
            sideParticipants = utils.getResizeableObjIdArrayScriptVar(parent, participantScriptVar);
            if (sideParticipants != null && !utils.isElementInArray(sideParticipants, who))
            {
                return false;
            }
        }
        utils.removeElement(sideParticipants, who);
        if (sideParticipants == null || sideParticipants.size() == 0)
        {
            utils.removeScriptVar(parent, participantScriptVar);
            CustomerServiceLog("gcw_city_invasion", "trial.removeNonInstanceFactionParticipant: Player: " + who + " has been REMOVED as a valid participant of the GCW city invasion for city object: " + parent);
            return true;
        }
        else if (utils.setScriptVar(parent, participantScriptVar, sideParticipants))
        {
            CustomerServiceLog("gcw_city_invasion", "trial.removeNonInstanceFactionParticipant: Player: " + who + " has been REMOVED as a valid participant of the GCW city invasion for city object: " + parent);
            return true;
        }
        LOG("participant_credit", "removeNonInstanceFactionParticipant failed to set script var");
        return false;
    }
    public static boolean isNonInstanceFactionParticipant(obj_id who, obj_id target) throws InterruptedException
    {
        if (!isIdValid(who) || !exists(who) || !isPlayer(who) || !isIdValid(target) || !exists(target))
        {
            return false;
        }
        obj_id parent = trial.getParent(target);
        if (!isIdValid(parent) || !exists(parent))
        {
            return false;
        }
        obj_id lastParent = null;
        while (!hasScript(parent, "systems.dungeon_sequencer.sequence_controller") && parent != lastParent)
        {
            lastParent = parent;
            parent = trial.getParent(parent);
            if (!isIdValid(parent) || !exists(parent))
            {
                return false;
            }
        }
        String participantScriptVar = "";
        if (factions.isRebelorRebelHelper(who))
        {
            participantScriptVar = "gcw.rebel.participants";
        }
        else if (factions.isImperialorImperialHelper(who))
        {
            participantScriptVar = "gcw.imperial.participants";
        }
        if (participantScriptVar.length() < 1)
        {
            return false;
        }
        Vector sideParticipants = new Vector();
        sideParticipants.setSize(0);
        if (utils.hasScriptVar(parent, participantScriptVar))
        {
            sideParticipants = utils.getResizeableObjIdArrayScriptVar(parent, participantScriptVar);
            if (sideParticipants == null || !utils.isElementInArray(sideParticipants, who))
            {
                return false;
            }
            for (Object sideParticipant : sideParticipants) {
                if (!isValidId(((obj_id) sideParticipant))) {
                    continue;
                }
                if (sideParticipant != who) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
    public static Vector getNonInstanceFactionParticipants(obj_id sequencer, int faction) throws InterruptedException
    {
        if (!isIdValid(sequencer) || !exists(sequencer))
        {
            CustomerServiceLog("gcw_city_invasion", "trial.getNonInstanceFactionParticipants: Sequencer was invalid. Returning NULL.");
            return null;
        }
        if (!hasScript(sequencer, "systems.dungeon_sequencer.sequence_controller"))
        {
            CustomerServiceLog("gcw_city_invasion", "trial.getNonInstanceFactionParticipants: Sequencer did not have the sequence controller script. Returning NULL.");
            return null;
        }
        String participantScriptVar = "";
        if (factions.FACTION_FLAG_REBEL == faction)
        {
            participantScriptVar = "gcw.rebel.participants";
        }
        else if (factions.FACTION_FLAG_IMPERIAL == faction)
        {
            participantScriptVar = "gcw.imperial.participants";
        }
        if (participantScriptVar.length() <= 0)
        {
            CustomerServiceLog("gcw_city_invasion", "trial.getNonInstanceFactionParticipants: Participant list not found. Returning NULL.");
            return null;
        }
        if (utils.hasScriptVar(sequencer, participantScriptVar))
        {
            return utils.getResizeableObjIdArrayScriptVar(sequencer, participantScriptVar);
        }
        CustomerServiceLog("gcw_city_invasion", "trial.getNonInstanceFactionParticipants: Participant list not found. Returning NULL.");
        return null;
    }
    public static void clearNonInstanceFactionParticipants(obj_id sequencer) throws InterruptedException
    {
        if (!isIdValid(sequencer) || !exists(sequencer))
        {
            CustomerServiceLog("gcw_city_invasion", "trial.clearNonInstanceFactionParticipants: Sequencer invalid. Aborting.");
            return;
        }
        if (!hasScript(sequencer, "systems.dungeon_sequencer.sequence_controller"))
        {
            CustomerServiceLog("gcw_city_invasion", "trial.clearNonInstanceFactionParticipants: Sequencer did not have sequence controller script. Aborting.");
            return;
        }
        utils.removeScriptVar(sequencer, "gcw.rebel.participants");
        utils.removeScriptVar(sequencer, "gcw.imperial.participants");
    }
    public static boolean purchaseTokenItem(obj_id player, int price, String tokenName) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || price < 0 || tokenName == null || tokenName.length() <= 0)
        {
            return false;
        }
        obj_id[] inventoryContents = getInventoryAndEquipment(player);
        if (inventoryContents == null || inventoryContents.length <= 0)
        {
            return false;
        }
        int tokensOwed = price;
        boolean foundTokenHolderBox = false;
        String itemName;
        for (obj_id inventoryContent : inventoryContents) {
            if (!isIdValid(inventoryContent) || !exists(inventoryContent)) {
                continue;
            }
            itemName = getStaticItemName(inventoryContent);
            if (itemName != null && !itemName.equals("")) {
                if (itemName.equals(tokenName)) {
                    if (getCount(inventoryContent) > 1) {
                        if (getCount(inventoryContent) > tokensOwed) {
                            setCount(inventoryContent, (getCount(inventoryContent) - tokensOwed));
                            tokensOwed = 0;
                        } else {
                            tokensOwed = tokensOwed - getCount(inventoryContent);
                            destroyObject(inventoryContent);
                        }
                    } else {
                        destroyObject(inventoryContent);
                        tokensOwed--;
                    }
                }
                if (!foundTokenHolderBox && itemName.equals("item_heroic_token_box_01_01")) {
                    foundTokenHolderBox = true;
                    int vTokens = 0;
                    if (hasObjVar(inventoryContent, "item.set.tokens_held")) {
                        int[] virtualTokens = getIntArrayObjVar(inventoryContent, "item.set.tokens_held");
                        int t = -1;
                        for (int k = 0; k < HEROIC_TOKENS.length; k++) {
                            if (HEROIC_TOKENS[k].equals(tokenName)) {
                                t = k;
                                vTokens = virtualTokens[t];
                            }
                        }
                        if (t > -1) {
                            if (vTokens > tokensOwed) {
                                vTokens = vTokens - tokensOwed;
                                virtualTokens[t] = vTokens;
                                tokensOwed = 0;
                            } else {
                                tokensOwed = tokensOwed - vTokens;
                                virtualTokens[t] = 0;
                            }
                            setObjVar(inventoryContent, "item.set.tokens_held", virtualTokens);
                        }
                    }
                }
            }
        }
        return tokensOwed == 0;
    }
    public static int getSpaceDutyTokenPrice(int level) throws InterruptedException
    {
        int price = level * 5;
        price = price + 50;
        return price;
    }
    public static int getTokenTotal(obj_id player, String token) throws InterruptedException
    {
        int tokenCount = 0;
        if (!isIdValid(player) || !exists(player) || token == null || token.length() <= 0)
        {
            return 0;
        }
        obj_id[] inventoryContents = getInventoryAndEquipment(player);
        if (inventoryContents == null || inventoryContents.length <= 0)
        {
            return 0;
        }
        boolean foundTokenHolderBox = false;
        String itemName;
        for (obj_id inventoryContent : inventoryContents) {
            if (!isIdValid(inventoryContent) || !exists(inventoryContent)) {
                continue;
            }
            itemName = getStaticItemName(inventoryContent);
            if (itemName != null && !itemName.equals("")) {
                if (itemName.equals(token)) {
                    if (getCount(inventoryContent) > 1) {
                        tokenCount = tokenCount + getCount(inventoryContent);
                    } else {
                        tokenCount++;
                    }
                }
                if (!foundTokenHolderBox && itemName.equals("item_heroic_token_box_01_01")) {
                    foundTokenHolderBox = true;
                    trial.verifyBox(inventoryContent);
                    int t = 0;
                    if (hasObjVar(inventoryContent, "item.set.tokens_held")) {
                        int[] virtualTokenArray = getIntArrayObjVar(inventoryContent, "item.set.tokens_held");
                        for (int k = 0; k < trial.HEROIC_TOKENS.length; k++) {
                            if (trial.HEROIC_TOKENS[k].equals(token)) {
                                t = k;
                            }
                        }
                        tokenCount = tokenCount + virtualTokenArray[t];
                    }
                }
            }
        }
        return tokenCount;
    }
}
