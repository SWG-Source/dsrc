package script.library;

import script.*;

import java.util.Vector;

public class gcw extends script.base_script
{
    public gcw()
    {
    }
    public static final int GCW_UPDATE_PULSE = 300;
    public static final float DECAY_PER_UPDATE = 0.02f;
    public static final String SCRIPTVAR_SCAN_INTEREST = "scan.interest";
    public static final String REGION_SPAWN_WEIGHT_PATH = "datatables/spawning/imperial_presence/presence_weight/";
    public static final String TABLE_PLAYER_BASE_CAP = "datatables/faction_perk/hq/hq_planetary_placement_cap.iff";
    public static final int INTEREST_FACTION = 0;
    public static final int INTEREST_SOMETHING = 1;
    public static final int INTEREST_SPICE = 2;
    public static final int INTEREST_SLICED_ITEMS = 3;
    public static final int INTEREST_FACTION_ITEMS = 4;
    public static final int INTEREST_CONTRABAND = 28;
    public static final String TEMPLATE_LAMBDA_SHUTTLE = "object/creature/npc/theme_park/lambda_shuttle.iff";
    public static final int AC_NONE = 0;
    public static final int AC_ATTACK = 1;
    public static final int AC_SCAN = 2;
    public static final String GCW_BASE_MANAGER = "gcw.base_manager.master_object";
    public static final String GCW_BASE_COUNT_REBEL = "gcw.player_base_count.rebel";
    public static final String GCW_BASE_COUNT_IMPERIAL = "gcw.player_base_count.imperial";
    public static final String VAR_BASE_HACK_DICTIONARY = "gcw.player_base_hack.dictionary";
    public static final String GCW_SEQUENCER_KEREN = "-292452502778781373";
    public static final String GCW_SEQUENCER_DEARIC = "-348184546868074204";
    public static final String GCW_SEQUENCER_BESTINE = "-233342756915162113";
    public static final int NO_CONTROL = 0;
    public static final int IMPERIAL_CONTROL = 1;
    public static final int REBEL_CONTROL = 2;
    public static final int FACTION_NEUTRAL = 0;
    public static final int FACTION_REBEL = 1;
    public static final int FACTION_IMPERIAL = 2;
    public static final String LIST_CREDIT_FOR_KILLS = "gcw_tracking.credit_for_kills";
    public static final String LIST_DAILY_KILL_VALUES = "gcw_tracking.daily_kill_credit";
    public static final int NORMAL_GCW_VALUE = 5;
    public static final int ELITE_GCW_VALUE = 10;
    public static final int BOSS_GCW_VALUE = 25;
    public static final double SPACE_GCW_VALUE = 4.0;
    public static final int GCW_PLAYER_BASE_DESTRUCTION = 100;
    public static final String GCW_PLAYER_BASE_POINT_VALUE = "datatables/faction_perk/hq/hq_point_values.iff";
    public static final int GCW_PLAYER_PVP_MODIFIER = 100;
    public static final int BATTLEFIELD_VICTORY_POINT_VALUE = 500;
    public static final int BATTLEFIELD_DEFEAT_POINT_VALUE = 100;
    public static final int BATTLEFIELD_TIE_POINT_VALUE = 200;
    public static final int BATTLEFIELD_TERMINAL_CAPTURE_MULTIPLIER = 100;
    public static final int ACTIVITY_CONSTANT = 100;
    public static final int MAX_DEATH_BY_PLAYER = 15;
    public static final double MIN_PVP_LEVEL_RATIO_LIMIT = 0.7;
    public static final double MIN_NPC_LEVEL_RATIO_LIMIT = 0.86;
    public static final string_id SID_GENERIC_POINT_GRANT = new string_id("gcw", "gcw_rank_generic_point_grant");
    public static final string_id SID_PVP_KILL_POINT_GRANT = new string_id("gcw", "gcw_rank_pvp_kill_point_grant");
    public static final String REGION_CONTROLLER = "gcw_pvp_region_controller";
    public static final String PVP_REGION_ACTIVITY_PERFORMED = "gcw_pvp_region.attack_performed";
    public static final int GCW_POINT_TYPE_GROUND_PVE = 0;
    public static final int GCW_POINT_TYPE_GROUND_PVP = 1;
    public static final int GCW_POINT_TYPE_GROUND_PVP_REGION = 2;
    public static final int GCW_POINT_TYPE_BASE_BUSTING = 3;
    public static final int GCW_POINT_TYPE_SPACE_PVE = 4;
    public static final int GCW_POINT_TYPE_SPACE_PVP = 5;
    public static final int GCW_POINT_TYPE_GROUND_QUEST = 6;
    public static final int GCW_POINT_TYPE_TRADING = 7;
    public static final int GCW_POINT_TYPE_ENTERTAINING = 8;
    public static final int GCW_POINT_TYPE_MAX = 9;
    public static final String validScenes[] =
    {
        "tatooine",
        "corellia",
        "dantooine",
        "dathomir",
        "endor",
        "lok",
        "naboo",
        "rori",
        "talus",
        "yavin4",
        "space_tatooine",
        "space_corellia",
        "space_dantooine",
        "space_dathomir",
        "space_endor",
        "space_lok",
        "space_naboo",
        "space_yavin4"
    };
    public static final String defaultRegions[] =
    {
        "gcw_region_tatooine_13",
        "gcw_region_corellia_13",
        "gcw_region_dantooine_16",
        "gcw_region_dathomir_13",
        "gcw_region_endor_15",
        "gcw_region_lok_13",
        "gcw_region_naboo_13",
        "gcw_region_rori_13",
        "gcw_region_talus_16",
        "gcw_region_yavin4_17",
        "gcw_region_tatooine_12",
        "gcw_region_corellia_14",
        "gcw_region_dantooine_17",
        "gcw_region_dathomir_12",
        "gcw_region_endor_16",
        "gcw_region_lok_14",
        "gcw_region_naboo_14",
        "gcw_region_yavin4_18"
    };
    public static final String pointTypes[] =
    {
        "pve",
        "pvp",
        "pvp_battlefield",
        "pvp",
        "space_pve",
        "space_pvp",
        "pve",
        "trading",
        "entertaining"
    };
    public static final float COLLECTION_DAMAGE_RATIO_MIN = 0.41f;
    public static final String PVP_PUSHBACK_REGION = "pvp_pushback";
    public static final String PVP_BATTLEFIELD_REGION = "pvp_battlefield";
    public static final int GCW_ENTERTAINMENT_TIME = 30;
    public static final String GCW_SCRIPTVAR_PARENT = "gcw_entertainment";
    public static final String GCW_ENTERTAINMENT_FLAG = "gcw_entertain_npc";
    public static final String GCW_NPC_SCRIPTVAR_FLAG = "entertainment_npc";
    public static final String GCW_PATROL_OBJ = "patrol_point_object";
    public static final String GCW_NPC_CLEANUP_FLAG = "entertainment_npc_cleanup";
    public static final String GCW_NPC_BEING_ENTERTAINED = "entertainment_npc";
    public static final String ENTERTAIN_GCW_TROOPS_PID = GCW_SCRIPTVAR_PARENT + ".gcw_entertainment_pid";
    public static final String GCW_STOPPED_ENTERTAINING = GCW_SCRIPTVAR_PARENT + ".gcw_player_stopped_entertaining";
    public static final String GCW_ENTERTAINER_PATROL_QUEST = "gcw_entertain_patrol";
    public static final String GCW_REPAIR_PATROL_QUEST = "gcw_repair_patrol";
    public static final String GCW_REPAIR_TURRET_QUEST = "gcw_repair_turret";
    public static final String GCW_REPAIR_BARRICADE_QUEST = "gcw_repair_barricade";
    public static final String GCW_SPY_PATROL_DESTROY_QUEST = "gcw_spy_destroy_patrol";
    public static final String GCW_SPY_PATROL_SCOUT_QUEST = "gcw_spy_scout_patrol";
    public static final String GCW_REPAIR_VEHICLE_PATROL_QUEST = "gcw_repair_vehicle";
    public static final String GCW_REPAIR_DAMAGED_VEHICLE_QUEST = "gcw_repair_vehicle";
    public static final String GCW_MEDIC_HEAL_QUEST = "gcw_medic_heal";
    public static final String GCW_DEFEND_BARRICADE_REBEL = "gcw_defend_rebel_barricade";
    public static final String GCW_DEFEND_BARRICADE_IMPERIAL = "gcw_defend_imperial_barricade";
    public static final String GCW_DEFEND_TURRET_REBEL = "gcw_defend_rebel_turret";
    public static final String GCW_DEFEND_TURRET_IMPERIAL = "gcw_defend_imperial_turret";
    public static final String GCW_DEFEND_TOWER_REBEL = "gcw_defend_rebel_tower";
    public static final String GCW_DEFEND_TOWER_IMPERIAL = "gcw_defend_imperial_tower";
    public static final String GCW_DESTROY_BARRICADE = "gcw_destroy_barricade";
    public static final String GCW_DESTROY_TURRET = "gcw_destroy_turret";
    public static final String GCW_ENTERTAIN_RALLY = "gcw_entertain_rallying";
    public static final String GCW_ENTERTAIN_FATIGUE = "gcw_entertain_fatigue";
    public static final String GCW_SMUGGLER_SLICING = "gcw_smuggler_slicing";
    public static final String GCW_ELIMINATE_REBELS = "gcw_eliminate_rebels";
    public static final String GCW_ELIMINATE_IMPERIALS = "gcw_eliminate_imperials";
    public static final String BUFF_SPY_EXPLOSIVES = "gcw_spy_destroy_patrol_explosive_stack";
    public static final String BUFF_GENERAL_RESIST_STACK_IMP = "gcw_general_resistance_stack_imperial";
    public static final String BUFF_GENERAL_RESIST_STACK_REB = "gcw_general_resistance_stack_rebel";
    public static final String BUFF_PLAYER_FATIGUE = "gcw_fatigue";
    public static final String SPY_DESTROY_PID = "spyDestroyPid";
    public static final String SPY_SCOUT_PID = "spyScoutPid";
    public static final String TRADER_REPAIR_PID = "traderRepairPid";
    public static final String OBJECT_TO_REPAIR = "objectToRepair";
    public static final String GCW_REPAIR_QUEST = "gcwRepairQuest";
    public static final String GCW_REPAIR_RESOURCE_COUNT = "gcwRepairResourceCount";
    public static final String GCW_OBJECT_REPAIR_COUNT = "gcwObjectRepairCount";
    public static final string_id SID_COUNTDOWN_LOCOMOTION = new string_id("gcw", "locomotion_stopped_countdown");
    public static final string_id SID_INTERRUPTED_INCAPACITATED = new string_id("gcw", "incap_stopped_countdown");
    public static final string_id SID_INTERRUPTED_DAMAGED = new string_id("gcw", "combat_stopped_countdown");
    public static final string_id SID_YOU_NEED_TO_BE_COMBATANT = new string_id("gcw", "you_need_to_be_combatant_to_entertain");
    public static final string_id SID_YOU_NEED_ENTERTAINMENT_QUEST = new string_id("gcw", "you_need_entertainment_quest_to_entertain");
    public static final string_id SID_ENTERTAIN_TROOPS_TIMER = new string_id("gcw", "entertain_troops_timer");
    public static final string_id SID_DOESNT_NEED_REPAIR = new string_id("gcw", "gcw_object_doesnt_need_repair");
    public static final string_id SID_DOESNT_NEED_HEALING = new string_id("gcw", "gcw_object_doesnt_need_healing");
    public static final string_id SID_YOU_NEED_RESOURCES_REPAIR = new string_id("gcw", "you_need_resources_to_repair");
    public static final string_id SID_YOU_NEED_TOOL_REPAIR = new string_id("gcw", "gcw_asset_repair_resource_needed");
    public static final string_id SID_RESOURCES_NEEDED = new string_id("gcw", "pylon_resources_needed");
    public static final string_id SID_SCOUT_PP_TIMER = new string_id("gcw", "reporting_patrol_point_position");
    public static final string_id SID_REPAIR_PP_TIMER = new string_id("gcw", "reparing_gcw_object");
    public static final string_id SID_HEAL_PP_TIMER = new string_id("gcw", "healing_gcw_object");
    public static final string_id SID_DESTROY_PP_TIMER = new string_id("gcw", "placing_patrol_point_explosives");
    public static final String GCW_RESOURCE_TYPE_BARRICADE = "ore";
    public static final String GCW_RESOURCE_TYPE_TURRET = "metal";
    public static final String GCW_RESOURCE_TYPE_PATROL = "metal";
    public static final String GCW_RESOURCE_TYPE_VEHICLE = "metal";
    public static final String GCW_RESOURCE_TYPE_DAMAGED_VEHICLE = "metal";
    public static final String GCW_SPAWN_ID_OBJVAR = "spawn_id";
    public static final String GCW_DEFENSE_PATROL_OBJVAR = "defense_quest_asset";
    public static final String GCW_OFFENSE_PATROL_OBJVAR = "offense_quest_asset";
    public static final String GCW_TOOL_TEMPLATE_OBJVAR = "tool_template";
    public static final String GCW_DEF_TOOL_TEMPLATE_OBJVAR = "defense_tool_template";
    public static final String GCW_OFF_TOOL_TEMPLATE_OBJVAR = "offense_tool_template";
    public static final String GCW_CONSTRUCTION_TASK = "resourcesUsed";
    public static final String GCW_CONSTRUCTION_SIGNAL = "resourcesUsed";
    public static final String GCW_IMP_DESTROY_BARRICADE_TASK = "destroyBarricadeImperial";
    public static final String GCW_IMP_DESTROY_BARRICADE_SIGNAL = "destroyBarricadeImperial";
    public static final String GCW_REB_DESTROY_BARRICADE_TASK = "destroyBarricadeRebel";
    public static final String GCW_REB_DESTROY_BARRICADE_SIGNAL = "destroyBarricadeRebel";
    public static final String GCW_IMP_DESTROY_TURRET_TASK = "destroyTurretImperial";
    public static final String GCW_IMP_DESTROY_TURRET_SIGNAL = "destroyTurretImperial";
    public static final String GCW_REB_DESTROY_TURRET_TASK = "destroyTurretRebel";
    public static final String GCW_REB_DESTROY_TURRET_SIGNAL = "destroyTurretRebel";
    public static final String GCW_REB_ENTERTAIN_PATROL_TASK = "entertainedPostRebel";
    public static final String GCW_REB_ENTERTAIN_PATROL_SIGNAL = "entertainedPostRebel";
    public static final String GCW_IMP_ENTERTAIN_PATROL_TASK = "entertainedPostImperial";
    public static final String GCW_IMP_ENTERTAIN_PATROL_SIGNAL = "entertainedPostImperial";
    public static final String GCW_REB_DESTROY_PATROL_SIGNAL = "destroyedPatrolPointRebel";
    public static final String GCW_IMP_DESTROY_PATROL_SIGNAL = "destroyedPatrolPointImperial";
    public static final String GCW_REB_FIND_PATROL_SIGNAL = "findPatrolPointRebel";
    public static final String GCW_IMP_FIND_PATROL_SIGNAL = "findPatrolPointImperial";
    public static final String GCW_PARTICIPATION_FLAG = "gcw_participation_flag";
    public static final String GCW_POINT_OVERRIDE = "gcw_point_override";
    public static final String GCW_REBEL_TOKEN = "item_gcw_rebel_token";
    public static final String GCW_IMPERIAL_TOKEN = "item_gcw_imperial_token";
    public static final String GCW_NPC_ENTERTAINED_BUFF = "gcw_entertained_patrol_npc";
    public static final int REPAIR_COUNT_MAX = 100;
    public static final int QUEST_COUNT_MAX = 100;
    public static final int MIN_REPAIR_AMOUNT = 10000;
    public static final int GCW_REPAIR_AMOUNT = 50000;
    public static final int GCW_RESOURCE_COUNT_BARRICADE = 50000;
    public static final int GCW_RESOURCE_COUNT_TURRET = 50000;
    public static final int GCW_RESOURCE_COUNT_PATROL = 50000;
    public static final int GCW_RESOURCE_COUNT_VEHICLE = 50000;
    public static final int GCW_RESOURCE_COUNT_DAMAGED_VEHICLE = 50000;
    public static final int GCW_CITY_PHASE_UNKNOWN = 0;
    public static final int GCW_CITY_PHASE_CONSTRUCTION = 1;
    public static final int GCW_CITY_PHASE_COMBAT = 2;
    public static final int GCW_CONSTRUCTION_MAXIMUM = 100;
    public static final int GCW_FATIGUE_DIVISOR = 10;
    public static final int GCW_FATIGUE_TIMER_MAX = 100;
    public static final int GCW_PULSE_TIMER = 10800;
    public static final int GCW_CONSTRUCTION_END_TIMER = 1800;
    public static final int GCW_COMBAT_END_TIMER = 1800;
    public static final int GCW_GENERAL_GCW_BONUS_MULTIPLIER = 5;
    public static final int GCW_GENERAL_GCW_BASE_AMOUNT = 1000;
    public static final int GCW_GENERAL_TOKEN_BONUS_DIVISOR = 1000;
    public static final int GCW_POINTS_WINNER_PARTICIPANTS = 1000;
    public static final int GCW_POINTS_LOSER_PARTICIPANTS = 100;
    public static final int GCW_TOKENS_WINNER_PARTICIPANTS = 40;
    public static final int GCW_TOKENS_LOSER_PARTICIPANTS = 10;
    public static final int GCW_POINTS_CONSTRUCTION_PHASE = 100;
    public static final int GCW_TOKENS_CONSTRUCTION_PHASE = 20;
    public static final String[] INVASION_CITIES = {"dearic", "keren", "bestine"};
    public static final String DEARIC_CITY_TABLE = "datatables/gcw/gcw_city_dearic.iff";
    public static final String KEREN_CITY_TABLE = "datatables/gcw/gcw_city_keren.iff";
    public static final String BESTINE_CITY_TABLE = "datatables/gcw/gcw_city_bestine.iff";
    public static final String CITY_DEARIC = "dearic";
    public static final String CITY_BESTINE = "bestine";
    public static final String CITY_KEREN = "keren";
    public static final String GCW_TUTORIAL_FLAG = "gcw_tutorial_flag.has_received_tutorial";
    public static final String COLOR_REBELS = "\\" + colors_hex.COLOR_REBELS;
    public static final String COLOR_IMPERIALS = "\\" + colors_hex.COLOR_IMPERIALS;
    public static void assignScanInterests(obj_id npc) throws InterruptedException
    {
        if (!isIdValid(npc) || isPlayer(npc) || pet_lib.isPet(npc))
        {
            return;
        }
        int interests = 0;
        location here = getLocation(npc);
        if (here == null)
        {
            return;
        }
        int spawnWeight = Integer.MIN_VALUE;
        String tbl = REGION_SPAWN_WEIGHT_PATH + here.area + ".iff";
        String[] regionNames = dataTableGetStringColumn(tbl, "REGION_NAME");
        if (regionNames != null && regionNames.length > 0)
        {
            region[] r = getRegionsWithSpawnableAtPoint(here, regions.SPAWN_TRUE);
            if (r != null && r.length > 0)
            {
                for (region region : r) {
                    String rName = region.getName();
                    int idx = utils.getElementPositionInArray(regionNames, rName);
                    if (idx > -1) {
                        spawnWeight = dataTableGetInt(tbl, idx, "WEIGHT");
                        break;
                    }
                }
            }
        }
        if (spawnWeight == Integer.MIN_VALUE)
        {
            spawnWeight = rand(-10, 10);
        }
        if (rand(0, 10) <= rand(1, 10) - spawnWeight)
        {
            interests = utils.setBit(interests, INTEREST_FACTION);
        }
        if (rand(1, 100) <= 10)
        {
            interests |= INTEREST_CONTRABAND;
        }
        else
        {
            if (rand(0, 10) <= rand(1, 10) + spawnWeight)
            {
                interests = utils.setBit(interests, INTEREST_SPICE);
            }
            if (rand(-5, 5) <= rand(-3, 3) + spawnWeight)
            {
                interests = utils.setBit(interests, INTEREST_SLICED_ITEMS);
            }
            if (rand(0, 10) <= rand(1, 10) - spawnWeight)
            {
                interests = utils.setBit(interests, INTEREST_FACTION_ITEMS);
            }
        }
        if (interests == 0)
        {
            interests = utils.setBit(interests, rand(INTEREST_FACTION, INTEREST_FACTION_ITEMS));
        }
        utils.setScriptVar(npc, SCRIPTVAR_SCAN_INTEREST, interests);
    }
    public static float scan(obj_id scanner, obj_id target) throws InterruptedException
    {
        if (!isIdValid(scanner) || !isIdValid(target))
        {
            return -1.0f;
        }
        int interests = utils.getIntScriptVar(scanner, SCRIPTVAR_SCAN_INTEREST);
        if (interests == 0)
        {
            return -1.0f;
        }
        return getContrabandRating(target, interests);
    }
    public static boolean isContrabandItem(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        return (getContrabandRating(item, INTEREST_CONTRABAND) > 0.0f);
    }
    public static float getContrabandRating(obj_id target, int interests) throws InterruptedException
    {
        if (!isIdValid(target) || interests == 0)
        {
            return 0.0f;
        }
        int got = getGameObjectType(target);
        if (isGameObjectTypeOf(got, GOT_creature) || isPlayer(target))
        {
            obj_id self = getSelf();
            if (isIdValid(self))
            {
                int total = 0;
                obj_id[] items = getInventoryAndEquipment(target);
                if (items != null && items.length > 0)
                {
                    int min_check = 1;
                    float dist = getDistance(self, target);
                    if (dist < 64.0f)
                    {
                        min_check -= Math.round(dist / 15.0f);
                    }
                    if (min_check < 1)
                    {
                        min_check = 1;
                    }
                    int i = 0;
                    while (i < items.length)
                    {
                        total += getContrabandRating(items[i], interests);
                        i += rand(1, min_check);
                    }
                }
                int myFac = pvpGetAlignedFaction(self);
                int tFac = pvpGetAlignedFaction(target);
                if (tFac == myFac)
                {
                    total -= pvpGetCurrentGcwRank(target);
                    if (total < 0)
                    {
                        total = 0;
                    }
                }
                else
                {
                    if (utils.checkBit(interests, INTEREST_FACTION))
                    {
                        if (pvpAreFactionsOpposed(myFac, tFac))
                        {
                            if (isPlayer(target))
                            {
                                total += pvpGetCurrentGcwRank(target);
                            }
                            else
                            {
                                total += 5;
                            }
                        }
                    }
                }
                return total;
            }
        }
        else
        {
            if (isSpice(target) && utils.checkBit(interests, INTEREST_SPICE))
            {
                int cnt = getCount(target);
                if (cnt > 0)
                {
                    return cnt;
                }
                return 6.0f;
            }
            if (isSlicedEquipment(target) && utils.checkBit(interests, INTEREST_SLICED_ITEMS))
            {
                if (utils.isEquipped(target))
                {
                    return 4.0f;
                }
                return 2.0f;
            }
            if (isNonImperialFactionItem(target) && utils.checkBit(interests, INTEREST_FACTION_ITEMS))
            {
                if (utils.isEquipped(target))
                {
                    return 6.0f;
                }
                return 3.0f;
            }
        }
        return 0.0f;
    }
    public static float getContrabandRating(obj_id target) throws InterruptedException
    {
        obj_id self = getSelf();
        int interests = utils.getIntScriptVar(self, SCRIPTVAR_SCAN_INTEREST);
        return getContrabandRating(target, interests);
    }
    public static boolean isSpice(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        return (getGameObjectType(target) == GOT_misc_food && hasScript(target, "item.comestible.spice"));
    }
    public static boolean isSlicedEquipment(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        int got = getGameObjectType(target);
        return (hasObjVar(target, "slicing.hack") && (isGameObjectTypeOf(got, GOT_weapon) || isGameObjectTypeOf(got, GOT_armor)));
    }
    public static boolean isNonImperialFactionItem(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (hasScript(target, faction_perk.SCRIPT_FACTION_ITEM))
        {
            if (hasObjVar(target, faction_perk.VAR_FACTION))
            {
                String iFac = getStringObjVar(target, faction_perk.VAR_FACTION);
                if (!iFac.equals("imperial"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static void harass(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!isIdValid(npc))
        {
            return;
        }
        if (hasScript(npc, "ai.imperial_presence.harass"))
        {
            return;
        }
        attachScript(npc, "ai.imperial_presence.harass");
        if (isIdValid(target) && target.isLoaded())
        {
            dictionary d = new dictionary();
            d.put("harassTarget", target);
            messageTo(npc, "handleNewHarassTarget", d, 1.0f, false);
        }
        else
        {
            messageTo(npc, "handleCheckpointMode", null, 1.0f, false);
        }
    }
    public static boolean spawnViaLambda(location loc, String tbl, String column, dictionary params) throws InterruptedException
    {
        if (loc == null || tbl == null || tbl.equals("") || column == null || column.equals(""))
        {
            return false;
        }
        String[] spawnNames = dataTableGetStringColumn(tbl, column);
        return createLambdaDropship(loc, spawnNames, params);
    }
    public static boolean spawnViaLambda(location loc, String tbl, int col, dictionary params) throws InterruptedException
    {
        if (loc == null || tbl == null || tbl.equals("") || col < 0)
        {
            return false;
        }
        String[] spawnNames = dataTableGetStringColumn(tbl, col);
        return createLambdaDropship(loc, spawnNames, params);
    }
    public static boolean createLambdaDropship(location loc, String[] spawnNames, dictionary params) throws InterruptedException
    {
        if (loc == null || spawnNames == null || spawnNames.length == 0)
        {
            return false;
        }
        if (!loc.area.equals(getCurrentSceneName()))
        {
            return false;
        }
        obj_id lambda = create.object("object/creature/npc/theme_park/lambda_shuttle.iff", loc);
        if (!isIdValid(lambda))
        {
            return false;
        }
        setYaw(lambda, rand(-180.0f, 180.0f));
        attachScript(lambda, "systems.spawning.dropship.lambda");
        utils.setScriptVar(lambda, "spawnNames", spawnNames);
        if (params != null && !params.isEmpty())
        {
            utils.setScriptVar(lambda, "spawnParameters", params);
        }
        return true;
    }
    public static boolean spawnViaLambdaPerGeo(location loc, dictionary params) throws InterruptedException
    {
        if (loc == null)
        {
            return false;
        }
        String[] spawnNames = getGeoSpawnNames(loc);
        return createLambdaDropship(loc, spawnNames, params);
    }
    public static String[] getGeoSpawnNames(location loc, boolean allowHeavy) throws InterruptedException
    {
        if (loc == null)
        {
            return null;
        }
        region[] r = getRegionsAtPoint(loc);
        if (r == null || r.length == 0)
        {
            return null;
        }
        String tbl = "datatables/imperial_presence/geo/general.iff";
        if (allowHeavy && rand(0, 10) < 2)
        {
            tbl = "datatables/imperial_presence/geo/general_heavy.iff";
        }
        for (region region : r) {
            int geoType = region.getGeographicalType();
            if (geoType == regions.GEO_DESERT || geoType == regions.GEO_OASIS || geoType == regions.GEO_WASTELAND) {
                tbl = "datatables/imperial_presence/geo/desert.iff";
            } else if (geoType == regions.GEO_FOREST || geoType == regions.GEO_JUNGLE || geoType == regions.GEO_WASTELAND) {
                tbl = "datatables/imperial_presence/geo/forest.iff";
            } else if (geoType == regions.GEO_SWAMP) {
                tbl = "datatables/imperial_presence/geo/swamp.iff";
            }
        }
        int numCol = dataTableGetNumColumns(tbl);
        if (numCol <= 0)
        {
            return null;
        }
        return dataTableGetStringColumn(tbl, rand(0, numCol - 1));
    }
    public static String[] getGeoSpawnNames(location loc) throws InterruptedException
    {
        return getGeoSpawnNames(loc, false);
    }
    public static obj_id getGCWMasterObject(obj_id objObject) throws InterruptedException
    {
        return getGCWMasterObject(getLocation(objObject));
    }
    public static obj_id getGCWMasterObject(location locTest) throws InterruptedException
    {
        String strPlanet = locTest.area;
        dictionary dctObjectInfo = dataTableGetRow("datatables/gcw/gcw_master_objects.iff", strPlanet);
        if (dctObjectInfo == null)
        {
            LOG("gcw", "No object for " + strPlanet);
            return null;
        }
        String strObjId = dctObjectInfo.getString("strObjId");
        Long lngId;
        try
        {
            lngId = Long.valueOf(strObjId);
        }
        catch(NumberFormatException err)
        {
            LOG("gcw", "Bad object id format for " + strPlanet);
            return null;
        }
        obj_id objMaster = obj_id.getObjId(lngId.longValue());
        return objMaster;
    }
    public static void changeGCWScore(location locTest, int intValue, String strFaction) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("intScoreChange", intValue);
        dctParams.put("strFaction", strFaction);
        obj_id objParent = getGCWMasterObject(locTest);
        messageTo(objParent, "updateGCWScore", dctParams, 0, true);
    }
    public static void incrementGCWScore(obj_id objObject) throws InterruptedException
    {
        location locTest = getLocation(objObject);
        int intScoreChange = faction_perk.grabFactionBasePointValue(objObject);
        String strFaction = getStringObjVar(objObject, "faction");
        changeGCWScore(locTest, intScoreChange, strFaction);
    }
    public static void decrementGCWScore(obj_id objObject) throws InterruptedException
    {
        location locTest = getLocation(objObject);
        int intScoreChange = faction_perk.grabFactionBasePointValue(objObject);
        intScoreChange = intScoreChange * -1;
        String strFaction = getStringObjVar(objObject, "faction");
        changeGCWScore(locTest, intScoreChange, strFaction);
        return;
    }
    public static boolean canPlaceFactionBaseByPlanet(obj_id player, obj_id deed, String planet) throws InterruptedException
    {
        if (isGcwRestrictedSceneName(planet))
        {
            return false;
        }
        if (!canPlaceFactionBaseByScore(player, deed, planet))
        {
            return false;
        }
        return true;
    }
    public static boolean canPlaceFactionBaseByScore(obj_id player, obj_id deed, String planet) throws InterruptedException
    {
        return (getCurrentPlanetaryFactionBaseCountPlanet(planet) < getCurrentPlanetaryFactionBaseMaxPlanet(planet));
    }
    public static int getCurrentPlanetaryFactionBaseCountPlanet(String planet) throws InterruptedException
    {
        obj_id planetId = getPlanetByName(planet);
        if (!isIdValid(planetId))
        {
            return Integer.MAX_VALUE;
        }
        return getRebelBaseCount(planetId) + getImperialBaseCount(planetId);
    }
    public static int getCurrentPlanetaryFactionBaseMaxPlanet(String planet) throws InterruptedException
    {
        obj_id planetId = getPlanetByName(planet);
        if (!isIdValid(planetId))
        {
            return -1;
        }
        if (!dataTableOpen(TABLE_PLAYER_BASE_CAP))
        {
            return -1;
        }
        int row = dataTableSearchColumnForString(planet, "planet", TABLE_PLAYER_BASE_CAP);
        return (row == -1 ? -1 : dataTableGetInt(TABLE_PLAYER_BASE_CAP, planet, "base_max"));
    }
    public static int getImperialBaseCount(obj_id planet) throws InterruptedException
    {
        return (utils.hasScriptVar(planet, GCW_BASE_COUNT_IMPERIAL) ? utils.getIntScriptVar(planet, GCW_BASE_COUNT_IMPERIAL) : 0);
    }
    public static int getRebelBaseCount(obj_id planet) throws InterruptedException
    {
        return (utils.hasScriptVar(planet, GCW_BASE_COUNT_REBEL) ? utils.getIntScriptVar(planet, GCW_BASE_COUNT_REBEL) : 0);
    }
    public static void modifyPlanetaryBaseCount(obj_id base, int faction, int delta) throws InterruptedException
    {
        obj_id planetId = getPlanetByName(getLocation(base).area);
        obj_id planetRegister = utils.getObjIdScriptVar(planetId, GCW_BASE_MANAGER);
        if (!isIdValid(planetRegister))
        {
            return;
        }
        dictionary dict = new dictionary();
        dict.put("faction", faction);
        dict.put("delta", delta);
        messageTo(planetRegister, "alterBaseCount", dict, 0, false);
    }
    public static boolean isGcwRestrictedSceneName(String scene) throws InterruptedException
    {
        String[] restrictedScene =
        {
            "kashyyyk_main",
            "kashyyyk_north_dungeons",
            "kashyyyk_pob_dungeons",
            "kashyyyk_south_dungeons",
            "kashyyyk_rryatt_trail",
            "kashyyyk_hunting",
            "space_light1",
            "space_heavy1",
            "dathomir",
            "yavin4",
            "endor",
            "mustafar"
        };
        for (String s : restrictedScene) {
            if (s.equals(scene)) {
                return true;
            }
        }
        return false;
    }
    public static dictionary getGCWDictionary(obj_id self) throws InterruptedException
    {
        int intImperialControlScore = getIntObjVar(self, "Imperial.controlScore");
        int intRebelControlScore = getIntObjVar(self, "Rebel.controlScore");
        dictionary dctParams = new dictionary();
        LOG("gcw", "Putting Imperial " + intImperialControlScore);
        LOG("gcw", "Putting Rebel " + intRebelControlScore);
        dctParams.put("intImperialControlScore", intImperialControlScore);
        dctParams.put("intRebelControlScore", intRebelControlScore);
        return dctParams;
    }
    public static float getImperialRatio(obj_id objNPC) throws InterruptedException
    {
        float fltImperialControlScore = getIntObjVar(objNPC, "Imperial.controlScore");
        float fltRebelControlScore = getIntObjVar(objNPC, "Rebel.controlScore");
        if ((fltImperialControlScore == 0) && (fltRebelControlScore == 0))
        {
            return 1.0f;
        }
        if (fltImperialControlScore == 0)
        {
            return 0.0f;
        }
        float fltScore = fltImperialControlScore / fltRebelControlScore;
        return fltScore;
    }
    public static float getRebelRatio(obj_id objNPC) throws InterruptedException
    {
        float fltImperialControlScore = getIntObjVar(objNPC, "Imperial.controlScore");
        float fltRebelControlScore = getIntObjVar(objNPC, "Rebel.controlScore");
        if ((fltImperialControlScore == 0) && (fltRebelControlScore == 0))
        {
            return 1.0f;
        }
        if (fltRebelControlScore == 0)
        {
            return 0.0f;
        }
        LOG("gcw", "Rebel score is " + fltRebelControlScore);
        LOG("gcw", "Imperial score is " + fltImperialControlScore);
        float fltScore = fltRebelControlScore / fltImperialControlScore;
        return fltScore;
    }
    public static void incrementGCWStanding(obj_id killer, obj_id target) throws InterruptedException
    {
        obj_id self = killer;
        final int MINIMUM_TIME_BETWEEN_KILLS = 900;
        if (!factions.isDeclared(killer))
        {
            return;
        }
        int intScore = 1;
        if (isPlayer(target))
        {
            intScore = 25;
        }
        int intTime = getGameTime();
        int intLastKill = utils.getIntScriptVar(target, "intLastGCWDeath");
        if ((intTime - intLastKill < MINIMUM_TIME_BETWEEN_KILLS))
        {
        }
        obj_id objStructure = getTopMostContainer(killer);
        if (!isPlayer(objStructure))
        {
            if (!permissionsIsPublic(objStructure))
            {
                return;
            }
        }
        utils.setScriptVar(target, "intLastGCWDeath", intTime);
        dictionary dctParams = new dictionary();
        dctParams.put("intScore", intScore);
        messageTo(killer, "updateGCWStanding", dctParams, 0, false);
    }
    public static void checkAndUpdateGCWStanding(obj_id self, int intAddedPoints) throws InterruptedException
    {
        String area = getLocation(self).area;
        if (area.equals("dungeon1") || area.equals("adventure1") || area.equals("adventure2"))
        {
            return;
        }
        final int SCORE_THRESHOLD = 25;
        final int POINT_EXCHANGE_RATE = 25;
        int intCurrentScore = getIntObjVar(self, "gcw.intKillScore");
        intCurrentScore = intCurrentScore + intAddedPoints;
        if (intCurrentScore >= SCORE_THRESHOLD)
        {
            int intPlanetPoints = intCurrentScore / POINT_EXCHANGE_RATE;
            intCurrentScore = intCurrentScore - (intPlanetPoints * POINT_EXCHANGE_RATE);
            prose_package ppTest = new prose_package();
            string_id strSpam = new string_id("faction_perk", "earned_gcw_points");
            ppTest = prose.setStringId(ppTest, strSpam);
            ppTest = prose.setDI(ppTest, intPlanetPoints);
            sendSystemMessageProse(self, ppTest);
            String strFaction = "";
            if (factions.isRebel(self))
            {
                playClientEffectObj(self, "clienteffect/holoemote_rebel.cef", self, "head");
                play2dNonLoopingSound(self, "sound/music_themequest_victory_rebel.snd");
                strFaction = "Rebel";
            }
            else
            {
                playClientEffectObj(self, "clienteffect/holoemote_imperial.cef", self, "head");
                play2dNonLoopingSound(self, "sound/music_themequest_victory_imperial.snd");
                strFaction = "Imperial";
            }
            changeGCWScore(getLocation(self), intPlanetPoints, strFaction);
        }
        setObjVar(self, "gcw.intKillScore", intCurrentScore);
    }
    public static obj_id getPub30StaticBaseControllerId(obj_id subject) throws InterruptedException
    {
        return getFirstObjectWithScript(getLocation(trial.getTop(subject)), 500.0f, "systems.gcw.static_base.master");
    }
    public static int getPub30StaticBaseControllingFaction(obj_id subject) throws InterruptedException
    {
        obj_id controller = getPub30StaticBaseControllerId(subject);
        return getIntObjVar(controller, "gcw.static_base.base_status");
    }
    public static int getPub30StaticBaseTimeSinceLastCapture(obj_id subject) throws InterruptedException
    {
        obj_id controller = getPub30StaticBaseControllerId(subject);
        if (!hasObjVar(controller, "gcw.static_base.last_capture"))
        {
            return -1;
        }
        else
        {
            return getIntObjVar(controller, "gcw.static_base.last_capture");
        }
    }
    public static void setPub30StaticBaseTimeSinceLastCapture(obj_id subject, int time) throws InterruptedException
    {
        obj_id controller = getPub30StaticBaseControllerId(subject);
        setObjVar(controller, "gcw.static_base.last_capture", time);
    }
    public static final int PHASE_5 = 259200;
    public static final int PHASE_4 = 86400;
    public static final int PHASE_3 = 28800;
    public static final int PHASE_2 = 14400;
    public static int getPub30StaticBaseCapturePhase(obj_id subject) throws InterruptedException
    {
        int lastCapture = getPub30StaticBaseTimeSinceLastCapture(subject);
        if (lastCapture == -1)
        {
            return 0;
        }
        int difference = getGameTime() - lastCapture;
        if (difference >= 259200)
        {
            return 5;
        }
        if (difference >= 86400)
        {
            return 4;
        }
        if (difference >= 28800)
        {
            return 3;
        }
        if (difference >= 14400)
        {
            return 2;
        }
        return 1;
    }
    public static int getPub30TimeToNextPhaseInt(obj_id subject) throws InterruptedException
    {
        int lastCapture = getPub30StaticBaseTimeSinceLastCapture(subject);
        int inPhase = getPub30StaticBaseCapturePhase(subject);
        switch (inPhase)
        {
            case 1:
            return (PHASE_2 + lastCapture) - getGameTime();
            case 2:
            return (PHASE_3 + lastCapture) - getGameTime();
            case 3:
            return (PHASE_4 + lastCapture) - getGameTime();
            case 4:
            return (PHASE_5 + lastCapture) - getGameTime();
            default:
            return -1;
        }
    }
    public static String getPub30TimeToNextPhaseString(obj_id subject) throws InterruptedException
    {
        int timeToNext = getPub30TimeToNextPhaseInt(subject);
        return utils.formatTimeVerbose(timeToNext);
    }
    public static int advancePub30StaticBaseCapturePhase(obj_id subject) throws InterruptedException
    {
        int currentPhase = getPub30StaticBaseCapturePhase(subject);
        if (currentPhase == 5)
        {
            return 5;
        }
        int newTime = 0;
        switch (currentPhase)
        {
            case 1:
            newTime = getGameTime() - PHASE_2;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 2;
            case 2:
            newTime = getGameTime() - PHASE_3;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 3;
            case 3:
            newTime = getGameTime() - PHASE_4;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 4;
            case 4:
            newTime = getGameTime() - PHASE_5;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 5;
            default:
            return -1;
        }
    }
    public static int regressPub30StaticBaseCapturePhase(obj_id subject) throws InterruptedException
    {
        int currentPhase = getPub30StaticBaseCapturePhase(subject);
        if (currentPhase == 1)
        {
            return 1;
        }
        int newTime = 0;
        switch (currentPhase)
        {
            case 2:
            newTime = getGameTime();
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 1;
            case 3:
            newTime = getGameTime() - PHASE_2;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 2;
            case 4:
            newTime = getGameTime() - PHASE_3;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 3;
            case 5:
            newTime = getGameTime() - PHASE_4;
            setPub30StaticBaseTimeSinceLastCapture(subject, newTime);
            return 4;
            default:
            return -1;
        }
    }
    public static void clearCreditForKills(obj_id player) throws InterruptedException
    {
        utils.removeBatchScriptVar(player, LIST_CREDIT_FOR_KILLS);
    }
    public static int findAttackerInArray(Vector attackList, obj_id attacker) throws InterruptedException
    {
        if (attackList == null || attackList.size() == 0)
        {
            return -1;
        }
        for (int i = 0; i < attackList.size(); i++)
        {
            String[] splitEntry = split(((String)attackList.get(i)), '-');
            obj_id idAtPoint = utils.stringToObjId(splitEntry[0]);
            if (idAtPoint == attacker)
            {
                return i;
            }
        }
        return -1;
    }
    public static int getDamageFromAttacker(Vector attackList, obj_id attacker) throws InterruptedException
    {
        if (attackList == null || attackList.size() == 0)
        {
            return 0;
        }
        for (Object o : attackList) {
            String[] splitEntry = split(((String) o), '-');
            obj_id idAtPoint = utils.stringToObjId(splitEntry[0]);
            if (idAtPoint == attacker) {
                return utils.stringToInt(splitEntry[1]);
            }
        }
        return 0;
    }
    public static String packAttackerDamage(obj_id attacker, int damage) throws InterruptedException
    {
        return ("" + attacker + "-" + damage);
    }
    public static boolean releaseGcwPointCredit(obj_id player) throws InterruptedException
    {
        obj_id[] gcwEnemiesList = new obj_id[0];
        if (verifyPvpRegionStatus(player))
        {
            gcwEnemiesList = getPlayerCreaturesInRange(getLocation(player), 80.0f);
        }
        boolean isImperialPlayer = factions.isImperial(player);
        boolean isRebelPlayer = factions.isRebel(player);
        obj_id landedDeathBlow = getObjIdObjVar(player, pclib.VAR_DEATHBLOW_KILLER);
        if (beast_lib.isBeast(landedDeathBlow) || pet_lib.isPet(landedDeathBlow))
        {
            landedDeathBlow = getMaster(landedDeathBlow);

        }
        if (gcwEnemiesList != null && gcwEnemiesList.length > 0)
        {
            removeDayOldEntries(player);
            for (obj_id obj_id : gcwEnemiesList) {
                if (!isIdValid(obj_id) || !exists(obj_id) || !verifyPvpRegionStatus(obj_id)) {
                    continue;
                }
                if ((float) getLevel(player) / getLevel(obj_id) >= MIN_PVP_LEVEL_RATIO_LIMIT && ((isImperialPlayer && factions.isRebel(obj_id)) || (factions.isImperial(obj_id) && isRebelPlayer))) {
                    int points = distributeIndividualContribution(player, obj_id, 0, GCW_POINT_TYPE_GROUND_PVP);
                    pvpModifyCurrentPvpKills(obj_id, 1);
                    incrementKillMeter(obj_id, 1);
                }
            }
            Vector attackerList = utils.getResizeableStringBatchScriptVar(player, gcw.LIST_CREDIT_FOR_KILLS);
            if (attackerList == null || attackerList.size() == 0)
            {
                return false;
            }
            for (Object o : attackerList) {
                String[] parseKiller = split(((String) o), '-');
                obj_id killer = utils.stringToObjId(parseKiller[0]);
                if (beast_lib.isBeast(killer) || pet_lib.isPet(killer)) {
                    killer = getMaster(killer);
                }
                double vLev = getLevel(player);
                double kLev = getLevel(killer);
                boolean isOfLevel = (vLev / kLev) >= MIN_PVP_LEVEL_RATIO_LIMIT;
                if (isOfLevel && killer != landedDeathBlow) {
                    pvp.bfCreditForAssist(killer);
                    gcwInvasionCreditForAssist(killer);
                }
            }
            utils.removeBatchScriptVar(player, gcw.LIST_CREDIT_FOR_KILLS);
            return true;
        }
        Vector attackerList = utils.getResizeableStringBatchScriptVar(player, gcw.LIST_CREDIT_FOR_KILLS);
        if (attackerList == null || attackerList.size() == 0)
        {
            return false;
        }
        int totalDamage = 0;
        for (Object o1 : attackerList) {
            String[] damageSplit = split(((String) o1), '-');
            totalDamage += utils.stringToInt(damageSplit[1]);
        }
        removeDayOldEntries(player);
        for (Object o : attackerList) {
            String[] parseKiller = split(((String) o), '-');
            obj_id killer = utils.stringToObjId(parseKiller[0]);
            double vLev = getLevel(player);
            double kLev = getLevel(killer);
            boolean isOfLevel = (vLev / kLev) >= MIN_PVP_LEVEL_RATIO_LIMIT;
            if (isOfLevel) {
                int points = distributeIndividualContribution(player, ((String) o), totalDamage, GCW_POINT_TYPE_GROUND_PVP);
                if (isIdValid(killer) && exists(killer)) {
                    pvpModifyCurrentPvpKills(killer, 1);
                    incrementKillMeter(killer, 1);
                }
            }
        }
        utils.removeBatchScriptVar(player, gcw.LIST_CREDIT_FOR_KILLS);
        return true;
    }
    public static void notifyPvpRegionWatcherOfDeath(obj_id player) throws InterruptedException
    {
        obj_id pvpRegionController = gcw.getPvpRegionControllerIdByPlayer(player);
        dictionary dict = new dictionary();
        dict.put("player", player);
        if (isIdValid(pvpRegionController))
        {
            messageTo(pvpRegionController, "diedInPvpRegion", dict, 1, false);
        }
    }
    public static boolean isAlreadyInArray(String[] orderedList, String attackerEntry) throws InterruptedException
    {
        for (String s : orderedList) {
            if (s.equals(attackerEntry)) {
                return true;
            }
        }
        return false;
    }
    public static int distributeIndividualContribution(obj_id victim, String playerString, int totalDamage, int point_type) throws InterruptedException
    {
        String[] parse = split(playerString, '-');
        obj_id killer = utils.stringToObjId(parse[0]);
        if (!isIdValid(killer) || !exists(killer))
        {
            return 0;
        }
        return distributeIndividualContribution(victim, killer, totalDamage, point_type);
    }
    public static int distributeIndividualContribution(obj_id victim, obj_id enemy, int totalDamage, int point_type) throws InterruptedException
    {
        if (!isIdValid(victim) || !exists(victim) || !isIdValid(enemy) || !exists(enemy))
        {
            return 0;
        }
        int targetRank = pvpGetCurrentGcwRank(enemy);
        int selfRank = pvpGetCurrentGcwRank(victim);
        int finalReward = (int)((targetRank + selfRank) * (GCW_PLAYER_PVP_MODIFIER) / 10);
        if (finalReward < 0)
        {
            return 0;
        }
        boolean inRegion = verifyPvpRegionStatus(enemy);
        if (inRegion)
        {
            finalReward += (int)(finalReward * 0.5f);
        }
        loot.rollRandomFactionalCollectible(victim, enemy, selfRank);
        obj_id relic = loot.chroniclesPvpLootDrop(enemy);
        finalReward = addToDailyGcwPointAllotment(victim, enemy, finalReward);
        String information = getName(victim);
        grantModifiedGcwPoints(victim, enemy, finalReward, true, point_type, information);
        dictionary webster = new dictionary();
        webster.put("victim", victim);
        webster.put("victimRank", selfRank);
        messageTo(enemy, "recivedGcwCreditForKill", webster, 0.0f, false);
        return finalReward;
    }
    public static int addToDailyGcwPointAllotment(obj_id victim, obj_id killer, int gcwPoint) throws InterruptedException
    {
        Vector dailyKills = new Vector();
        dailyKills.setSize(0);
        if (utils.hasResizeableStringBatchObjVar(victim, LIST_DAILY_KILL_VALUES))
        {
            dailyKills = utils.getResizeableStringBatchObjVar(victim, LIST_DAILY_KILL_VALUES);
            utils.removeBatchObjVar(victim, LIST_DAILY_KILL_VALUES);
        }
        int targetRank = pvpGetCurrentGcwRank(killer);
        int selfRank = pvpGetCurrentGcwRank(victim);
        int maxInterval = (int)(targetRank + selfRank) * GCW_PLAYER_PVP_MODIFIER;
        int positionInArray = findAttackerInArray(dailyKills, killer);
        if (positionInArray == -1)
        {
            if (gcwPoint > maxInterval)
            {
                gcwPoint = maxInterval;
            }
            String newEntry = packKillerDailyPoints(killer, 1, getGameTime());
            utils.addElement(dailyKills, newEntry);
        }
        else
        {
            int accruedPoints = getAccruedPoints(((String)dailyKills.get(positionInArray)));
            int timeAtFirstAward = getTimeOfFirstAward(((String)dailyKills.get(positionInArray)));
            gcwPoint = gcwPoint > maxInterval ? maxInterval : gcwPoint;
            if (getGameTime() > timeAtFirstAward + 86400)
            {
                timeAtFirstAward = getGameTime();
                accruedPoints = 1;
            }
            else
            {
                accruedPoints++;
                if (accruedPoints > 4)
                {
                    gcwPoint = (int)(gcwPoint * 0.5f);
                }
                else
                {
                    gcwPoint = (int)(gcwPoint * (float)((10.0f - (accruedPoints - 1.0f)) / 10.0f));
                }
            }
            String newEntry = packKillerDailyPoints(killer, accruedPoints, timeAtFirstAward);
            dailyKills.set(positionInArray, newEntry);
        }
        utils.setResizeableBatchObjVar(victim, LIST_DAILY_KILL_VALUES, dailyKills);
        return gcwPoint;
    }
    public static void removeDayOldEntries(obj_id player) throws InterruptedException
    {
        if (!utils.hasResizeableStringBatchObjVar(player, LIST_DAILY_KILL_VALUES))
        {
            return;
        }
        Vector dailyList = utils.getResizeableStringBatchObjVar(player, LIST_DAILY_KILL_VALUES);
        utils.removeBatchObjVar(player, LIST_DAILY_KILL_VALUES);
        int dayPast = getGameTime() - 86400;
        for (int i = 0; i < dailyList.size(); i++)
        {
            String[] parse = split(((String)dailyList.get(i)), '-');
            int timeHack = utils.stringToInt(parse[2]);
            if (dayPast > timeHack)
            {
                utils.removeElementAt(dailyList, i);
            }
        }
        utils.setResizeableBatchObjVar(player, LIST_DAILY_KILL_VALUES, dailyList);
    }
    public static String packKillerDailyPoints(obj_id player, int gcwPoint, int time) throws InterruptedException
    {
        return ("" + player + "-" + gcwPoint + "-" + time);
    }
    public static int getTimeOfFirstAward(String killRecord) throws InterruptedException
    {
        String[] parse = split(killRecord, '-');
        return utils.stringToInt(parse[2]);
    }
    public static int getAccruedPoints(Vector attackList, obj_id attacker) throws InterruptedException
    {
        if (attackList == null || attackList.size() == 0)
        {
            return 0;
        }
        for (Object o : attackList) {
            String[] splitEntry = split(((String) o), '-');
            obj_id idAtPoint = utils.stringToObjId(splitEntry[0]);
            if (idAtPoint == attacker) {
                return utils.stringToInt(splitEntry[1]);
            }
        }
        return 0;
    }
    public static int getAccruedPoints(String killRecord) throws InterruptedException
    {
        String[] parse = split(killRecord, '-');
        return utils.stringToInt(parse[1]);
    }
    public static void grantModifiedGcwPoints(obj_id victim, obj_id[] attackers, boolean pvpKill, int point_type, String information) throws InterruptedException
    {
        if (!isIdValid(victim))
        {
            return;
        }
        if (attackers == null || attackers.length == 0)
        {
            return;
        }
        for (obj_id attacker : attackers) {
            if (isIdValid(attacker) && exists(attacker) && getDistance(victim, attacker) < 120.0f) {
                grantModifiedGcwPoints(victim, attacker, pvpKill, point_type, information);
            }
        }
    }
    public static void grantModifiedGcwPoints(obj_id attacker, int pointValue, int point_type, String information) throws InterruptedException
    {
        pointValue = getModifiedGcwPointValue(attacker, pointValue);
        _grantGcwPoints(null, attacker, pointValue, false, point_type, information);
    }
    public static void grantModifiedGcwPoints(obj_id victim, obj_id attacker, boolean pvpKill, int point_type, String information) throws InterruptedException
    {
        int pointValue = getNpcKillCredit(victim, attacker);
        grantModifiedGcwPoints(victim, attacker, pointValue, pvpKill, point_type, information);
    }
    public static void grantModifiedGcwPoints(obj_id victim, obj_id attacker, int pointValue, boolean pvpKill, int point_type, String information) throws InterruptedException
    {
        pointValue = getModifiedGcwPointValue(attacker, pointValue);
        _grantGcwPoints(victim, attacker, pointValue, pvpKill, point_type, information);
    }
    public static void grantUnmodifiedGcwPoints(obj_id attacker, int pointValue) throws InterruptedException
    {
        _grantGcwPoints(null, attacker, pointValue, false, -1, "");
    }
    public static void _grantGcwPoints(obj_id victim, obj_id attacker, int pointValue, boolean pvpKill, int pointType, String information) throws InterruptedException
    {
        if (!isIdValid(attacker) || !exists(attacker) || pointValue < 1 || pet_lib.isPet(victim))
        {
            return;
        }
        float multiplier = utils.stringToFloat(getConfigSetting("GameServer", "gcwPointBonus"));
        if (multiplier > 1)
        {
            pointValue *= multiplier;
        }
        pvpModifyCurrentGcwPoints(attacker, pointValue);
        prose_package pp = new prose_package();
        pp.target.set(getName(victim));
        pp.digitInteger = pointValue;
        if (pvpKill)
        {
            pp.stringId = SID_PVP_KILL_POINT_GRANT;
            sendSystemMessageProse(attacker, pp);
        }
        else
        {
            pp.stringId = SID_GENERIC_POINT_GRANT;
            sendSystemMessageProse(attacker, pp);
        }
        doGcwPointCsLogging(attacker, pointValue, pointType, information);
        gcwInvasionCreditForGCW(attacker, pointValue);
        grantGcwPointsToRegion(attacker, pointValue, pointType);
    }
    public static void doGcwPointCsLogging(obj_id player, int pointValue, int pointType, String information) throws InterruptedException
    {
        switch (pointType)
        {
            case GCW_POINT_TYPE_GROUND_PVE:
            CustomerServiceLog("GCW_points_ground_pve", "%TU has acquired " + pointValue + " points for defeating " + information + " in pve action", player);
            break;
            case GCW_POINT_TYPE_GROUND_PVP:
            CustomerServiceLog("GCW_points_ground_pvp", "%TU has acquired " + pointValue + " points for defeating " + information + " in combat", player);
            break;
            case GCW_POINT_TYPE_GROUND_PVP_REGION:
            CustomerServiceLog("GCW_points_ground_pvp_region", "%TU has acquired " + pointValue + " points in the " + information + " battlefield region.", player);
            break;
            case GCW_POINT_TYPE_BASE_BUSTING:
            CustomerServiceLog("GCW_points_player_base_busting", "%TU has acquired " + pointValue + " points for destroying " + information, player);
            break;
            case GCW_POINT_TYPE_SPACE_PVE:
            CustomerServiceLog("GCW_points_space_pve", "%TU has acquired " + pointValue + " points for destroying " + information, player);
            break;
            case GCW_POINT_TYPE_SPACE_PVP:
            CustomerServiceLog("GCW_points_space_pvp", "%TU has acquired " + pointValue + " points for defeating " + information + " in space combat", player);
            break;
            case GCW_POINT_TYPE_GROUND_QUEST:
            CustomerServiceLog("GCW_points_ground_quest", "%TU has acquired " + pointValue + " points for completing the " + information + " groundquest", player);
            default:
            break;
        }
    }
    public static String getGcwCategory(int pointType) throws InterruptedException
    {
        if (pointType < 0 || pointType >= GCW_POINT_TYPE_MAX)
        {
            return null;
        }
        String pointCategory = pointTypes[pointType];
        String scene = getCurrentSceneName();
        int sceneIndex = utils.getElementPositionInArray(validScenes, scene);
        if (sceneIndex < 0)
        {
            return null;
        }
        return scene + "_" + pointCategory;
    }
    public static String getGcwRegion(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target))
        {
            return null;
        }
        region[] regionList = getRegionsAtPoint(getLocation(target));
        String regionName = "";
        if (regionList != null && regionList.length > 0)
        {
            for (region currentRegion : regionList) {
                if (currentRegion == null) {
                    continue;
                }
                String currentRegionName = currentRegion.getName();
                if (currentRegionName != null && currentRegionName.length() > 0 && currentRegionName.startsWith("gcw_region")) {
                    regionName = currentRegionName;
                    break;
                }
            }
        }
        if (regionName.length() <= 0)
        {
            String scene = getCurrentSceneName();
            int sceneIndex = utils.getElementPositionInArray(validScenes, scene);
            if (sceneIndex >= 0 && sceneIndex < defaultRegions.length)
            {
                regionName = defaultRegions[sceneIndex];
            }
        }
        return regionName;
    }
    public static void grantGcwPointsToRegion(obj_id player, int pointValue, int pointType) throws InterruptedException
    {
        if (!isIdValid(player) || pointValue < 0 || pointType < 0 || pointType >= GCW_POINT_TYPE_MAX)
        {
            return;
        }
        String category = getGcwCategory(pointType);
        String regionName = getGcwRegion(player);
        if (category == null || category.length() <= 0 || regionName == null || regionName.length() <= 0)
        {
            return;
        }
        if (factions.isImperial(player))
        {
            adjustGcwImperialScore("grantGcwPointsToCategory", player, category, pointValue);
            adjustGcwImperialScore("grantGcwPointsToRegion", player, regionName, pointValue);
            LOG("gcw_region_points", "Imperial gain.  regionName: " + regionName + " category: " + category + " pointValue: " + pointValue);
        }
        else if (factions.isRebel(player))
        {
            adjustGcwRebelScore("grantGcwPointsToCategory", player, category, pointValue);
            adjustGcwRebelScore("grantGcwPointsToRegion", player, regionName, pointValue);
            LOG("gcw_region_points", "Rebel gain.  regionName: " + regionName + " category: " + category + " pointValue: " + pointValue);
        }
    }
    public static int getImperialPercentileByRegion(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target))
        {
            return 50;
        }
        String category = getGcwRegion(target);
        if (category == null || category.length() <= 0)
        {
            return 50;
        }
        return getGcwImperialScorePercentile(category);
    }
    public static int getRebelPercentileByRegion(obj_id target) throws InterruptedException
    {
        return 100 - getImperialPercentileByRegion(target);
    }
    public static String getRegionFactionOwner(obj_id target) throws InterruptedException
    {
        String category = getGcwRegion(target);
        if (category == null || category.length() <= 0)
        {
            return null;
        }
        int imperial = getImperialPercentileByRegion(target);
        if (imperial > 50)
        {
            return "imperial";
        }
        if (imperial < 50)
        {
            return "rebel";
        }
        return null;
    }
    public static boolean verifyPvpRegionStatus(obj_id player) throws InterruptedException
    {
        obj_id region_controller = getPvpRegionControllerIdByPlayer(player);
        if (!isIdValid(region_controller))
        {
            return false;
        }
        utils.setScriptVar(player, PVP_REGION_ACTIVITY_PERFORMED, true);
        return true;
    }
    public static int getNpcKillCredit(obj_id npc, obj_id player) throws InterruptedException
    {
        if (isPlayer(npc) || pet_lib.isPet(npc))
        {
            return 0;
        }
        String faction = factions.getFaction(npc);
        if (faction == null || faction.equals(""))
        {
            return 0;
        }
        if (!faction.equals("Rebel") && !faction.equals("Imperial"))
        {
            return 0;
        }
        if (faction.equals("Rebel") && !factions.isImperial(player))
        {
            return 0;
        }
        if (faction.equals("Imperial") && !factions.isRebel(player))
        {
            return 0;
        }
        double npcLev = getLevel(npc);
        double playLev = getLevel(player);
        if ((npcLev / playLev) < MIN_NPC_LEVEL_RATIO_LIMIT)
        {
            return 0;
        }
        int difficultyClass = getIntObjVar(npc, "difficultyClass");
        switch (difficultyClass)
        {
            case 0:
            return NORMAL_GCW_VALUE;
            case 1:
            return ELITE_GCW_VALUE;
            case 2:
            return BOSS_GCW_VALUE;
            default:
            return 0;
        }
    }
    public static int getModifiedGcwPointValue(obj_id player, int passedValue) throws InterruptedException
    {
        float mod = 1.0f;
        if (utils.hasScriptVar(player, "buff.xpBonus.value"))
        {
            mod += utils.getFloatScriptVar(player, "buff.xpBonus.value");
        }
        if (utils.hasScriptVar(player, "buff.xpBonusGeneral.value"))
        {
            mod += utils.getFloatScriptVar(player, "buff.xpBonusGeneral.value");
        }
        if (utils.hasScriptVar(player, "buff.gcwBonusGeneral.value"))
        {
            mod += utils.getFloatScriptVar(player, "buff.gcwBonusGeneral.value");
        }
        if (isSpaceBattlefieldZone())
        {
            mod += 0.1f;
        }
        return (int)(mod * passedValue);
    }
    public static void registerPvpRegionControllerWithPlanet(obj_id controlObject, String regionName) throws InterruptedException
    {
        obj_id planetId = getPlanetByName(getLocation(controlObject).area);
        utils.setScriptVar(planetId, REGION_CONTROLLER + "." + regionName, controlObject);
    }
    public static obj_id getPvpRegionControllerIdByName(obj_id player, String regionName) throws InterruptedException
    {
        obj_id planetId = getPlanetByName(getLocation(player).area);
        String packedRegion = REGION_CONTROLLER + "." + regionName;
        return utils.hasScriptVar(planetId, packedRegion) ? utils.getObjIdScriptVar(planetId, packedRegion) : null;
    }
    public static obj_id getPvpRegionControllerIdByPlayer(obj_id player) throws InterruptedException
    {
        region[] regionList = getRegionsAtPoint(getLocation(player));
        obj_id planetId = getPlanetByName(getLocation(player).area);
        obj_id regionController = null;
        if (regionList == null || regionList.length == 0)
        {
            return null;
        }
        for (region region : regionList) {
            String regionName = region.getName();
            String packedRegion = REGION_CONTROLLER + "." + regionName;
            if (utils.hasScriptVar(planetId, packedRegion)) {
                regionController = utils.getObjIdScriptVar(planetId, packedRegion);
            }
        }
        return regionController;
    }
    public static void notifyPvpRegionControllerOfPlayerEnter(obj_id controllerId, obj_id player) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("player", player);
        messageTo(controllerId, "newPlayerNotify", dict, 0, false);
        doLogging("GCW_points_ground_pvp", "notifyPvpRegionControllerOfPlayerEnter" + getName(player) + "/" + player + " has entered the region. Notifying controller object " + controllerId);
    }
    public static void makeBattlefieldRegion(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            doLogging("GCW_points_ground_pvp", "makeBattlefieldRegion controller not valid");
            return;
        }
        String regionName = getBattlefieldRegionName(controller);
        if (regionName == null || regionName.length() < 1)
        {
            doLogging("GCW_points_ground_pvp", "makeBattlefieldRegion regionName invalid");
            return;
        }
        location whereIam = getLocation(controller);
        region battlefieldRegion = getRegion(whereIam.area, regionName);
        if (battlefieldRegion != null)
        {
            doLogging("GCW_points_ground_pvp", "makeBattlefieldRegion registerPvpRegionControllerWithPlanet battlefieldRegion exists");
            registerPvpRegionControllerWithPlanet(controller, regionName);
            return;
        }
        doLogging("GCW_points_ground_pvp", "makeBattlefieldRegion registerPvpRegionControllerWithPlanet failed");
    }
    public static void makePushbackRegion(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            doLogging("GCW_points_ground_pvp", "makePushbackRegion controller not valid");
            return;
        }
        String regionName = getPushbackRegionName(controller);
        if (regionName == null || regionName.length() < 1)
        {
            doLogging("GCW_points_ground_pvp", "makePushbackRegion regionName invalid");
            return;
        }
        location whereIam = getLocation(controller);
        region pushbackRegion = getRegion(whereIam.area, regionName);
        if (pushbackRegion != null)
        {
            doLogging("GCW_points_ground_pvp", "makePushbackRegion registerPvpRegionControllerWithPlanet pushbackRegion exists");
            registerPvpRegionControllerWithPlanet(controller, regionName);
            return;
        }
        doLogging("GCW_points_ground_pvp", "makePushbackRegion registerPvpRegionControllerWithPlanet failed");
    }
    public static obj_id getPushbackControllerByPlayer(obj_id player) throws InterruptedException
    {
        return getPvpRegionControllerIdByPlayer(player);
    }
    public static void grantSpacePvpKillCredit(obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        Vector validAttackers = new Vector();
        validAttackers.setSize(0);
        for (obj_id attacker : attackers) {
            if (factions.isImperial(defender)) {
                if (factions.isRebel(attacker) && validateSpaceTier(defender, attacker)) {
                    utils.addElement(validAttackers, attacker);
                } else {
                    LOG("doLogging", "Faction or tier error");
                }
            }
            if (factions.isRebel(defender)) {
                if (factions.isImperial(attacker) && validateSpaceTier(defender, attacker)) {
                    utils.addElement(validAttackers, attacker);
                } else {
                    LOG("doLogging", "Faction or tier error");
                }
            }
        }
        if (validAttackers == null || validAttackers.size() < 1)
        {
            return;
        }
        for (int k = 0; k < validAttackers.size(); k++)
        {
            String packedAttacker = "" + ((obj_id)validAttackers.get(k)) + "-" + 1;
            int reward = distributeIndividualContribution(defender, packedAttacker, validAttackers.size(), GCW_POINT_TYPE_SPACE_PVP);
            pvpModifyCurrentPvpKills(((obj_id)validAttackers.get(k)), 1);
        }
    }
    public static boolean validateSpaceTier(obj_id defender, obj_id attacker) throws InterruptedException
    {
        int defLevel = space_flags.getPilotTier(defender);
        int atkLevel = space_flags.getPilotTier(attacker);
        switch (defLevel)
        {
            case 0:
            return false;
            case 1:
            if (atkLevel > 2)
            {
                return false;
            }
            case 2:
            if (atkLevel > 3)
            {
                return false;
            }
            case 3:
            break;
            case 4:
            break;
            case 5:
            break;
        }
        return true;
    }
    public static int getSpaceKillCredit(int player_level, String targetTier)
    {
        if(targetTier.startsWith("tier")) {
            int target_level = Integer.parseInt(targetTier.replace("tier", ""));
            if (player_level - target_level == 1) {
                return Double.valueOf(SPACE_GCW_VALUE * (player_level / 2.0f)).intValue();
            } else if (player_level == target_level) {
                return Double.valueOf(SPACE_GCW_VALUE * player_level).intValue();
            } else if (player_level < target_level) {
                return Double.valueOf(SPACE_GCW_VALUE * (player_level + 0.5)).intValue();
            }
        }
        return 0;
    }
    public static void grantBaseDestructionPoints(obj_id base) throws InterruptedException
    {
        obj_id[] interiorPlayers = trial.getPlayersInDungeon(base);
        obj_id[] exteriorPlayers = getPlayerCreaturesInRange(getLocation(base), 120.0f);
        String baseFac = factions.getFaction(base);
        if (baseFac == null || (!baseFac.equals("Rebel") && !baseFac.equals("Imperial")))
        {
            CustomerServiceLog("GCW_points_player_base_busting", "Base(" + getName(base) + "/" + base + "/" + baseFac + ") did not have a Rebel or Imperial faction");
            return;
        }
        Vector filteredList = new Vector();
        filteredList.setSize(0);
        if (interiorPlayers != null && interiorPlayers.length > 0)
        {
            for (obj_id interiorPlayer : interiorPlayers) {
                if (isIdValid(interiorPlayer) && exists(interiorPlayer)) {
                    String playerFac = factions.getFaction(interiorPlayer);
                    if (playerFac == null) {
                        continue;
                    }
                    if (baseFac.equals("Rebel") && playerFac.equals("Imperial")) {
                        utils.addElement(filteredList, interiorPlayer);
                    }
                    if (baseFac.equals("Imperial") && playerFac.equals("Rebel")) {
                        utils.addElement(filteredList, interiorPlayer);
                    }
                }
            }
        }
        if (exteriorPlayers != null && exteriorPlayers.length > 0)
        {
            for (obj_id exteriorPlayer : exteriorPlayers) {
                if (isIdValid(exteriorPlayer) && exists(exteriorPlayer)) {
                    String playerFac = factions.getFaction(exteriorPlayer);
                    if (playerFac == null) {
                        continue;
                    }
                    if (baseFac.equals("Rebel") && playerFac.equals("Imperial")) {
                        if (filteredList.size() > 0) {
                            if (utils.getElementPositionInArray(filteredList, exteriorPlayer) == -1) {
                                utils.addElement(filteredList, exteriorPlayer);
                            }
                        } else {
                            utils.addElement(filteredList, exteriorPlayer);
                        }
                    }
                    if (baseFac.equals("Imperial") && playerFac.equals("Rebel")) {
                        if (filteredList.size() > 0) {
                            if (utils.getElementPositionInArray(filteredList, exteriorPlayer) == -1) {
                                utils.addElement(filteredList, exteriorPlayer);
                            }
                        } else {
                            utils.addElement(filteredList, exteriorPlayer);
                        }
                    }
                }
            }
        }
        String table = GCW_PLAYER_BASE_POINT_VALUE;
        if (!dataTableOpen(table))
        {
            CustomerServiceLog("GCW_points_player_base_busting", "Could not open the " + GCW_PLAYER_BASE_POINT_VALUE + " data table");
            return;
        }
        String template = getTemplateName(base);
        int value = dataTableGetInt(table, template, "point_value");
        if (value < -1)
        {
            CustomerServiceLog("GCW_points_player_base_busting", "template " + template + " could not be found in the dataTable " + GCW_PLAYER_BASE_POINT_VALUE);
            return;
        }
        value *= GCW_PLAYER_BASE_DESTRUCTION;
        if (filteredList == null || filteredList.size() == 0)
        {
            CustomerServiceLog("GCW_points_player_base_busting", "There are no valid players in the filtered list");
            return;
        }
        for (Object o : filteredList) {
            grantModifiedGcwPoints(((obj_id) o), value, gcw.GCW_POINT_TYPE_BASE_BUSTING, template);
        }
    }
    public static int getGcwGroundQuestAward(obj_id player, int quest_tier) throws InterruptedException
    {
        if (!factions.isImperial(player) && !factions.isRebel(player))
        {
            return 0;
        }
        float tierValue = getGroundQuestTierBonus(quest_tier);
        int rank = pvpGetCurrentGcwRank(player);
        String gcwRankTable = "datatables/gcw/gcw_rank.iff";
        int rankCapBase = dataTableGetInt(gcwRankTable, 0, "RatingEarningCap");
        int rankCap = dataTableGetInt(gcwRankTable, rank - 1, "RatingEarningCap");
        float groundQuestGcwValue = tierValue * 5000;
        float capDeltaBonus = rankCapBase / rankCap;
        groundQuestGcwValue *= capDeltaBonus;
        return (int)groundQuestGcwValue;
    }
    private static void triggerOnPvpRankingChanged(obj_id player, int oldRank, int newRank)
    {
        Object [] params = new Object[] {
                player,
                oldRank,
                newRank
        };
        script_entry.runScripts("OnPvpRankingChanged", params);
    }
    public static void increaseGcwRatingToNextRank(obj_id player)
    {
        int MAX_GCW_RANK = 12;
        int rank = Math.min(pvpGetCurrentGcwRank(player), MAX_GCW_RANK);
        if(rank < MAX_GCW_RANK) {
            String gcwRankTable = "datatables/gcw/gcw_rank.iff";
            int maxRating = dataTableGetInt(gcwRankTable, rank - 1, "MaxRating");
            ctsUseOnlySetGcwInfo(player, pvpGetCurrentGcwPoints(player), maxRating + 1, pvpGetCurrentPvpKills(player), pvpGetLifetimeGcwPoints(player), pvpGetMaxGcwImperialRating(player), pvpGetMaxGcwRebelRating(player), pvpGetLifetimePvpKills(player), pvpGetNextGcwRatingCalcTime(player));
            triggerOnPvpRankingChanged(player, rank, rank + 1);
        }
    }
    public static void decreaseGcwRatingToPreviousRank(obj_id player)
    {
        int rank = Math.max(pvpGetCurrentGcwRank(player), 0);
        if(rank > 0) {
            String gcwRankTable = "datatables/gcw/gcw_rank.iff";
            int minRating = dataTableGetInt(gcwRankTable, rank - 1, "MinRating");
            ctsUseOnlySetGcwInfo(player, pvpGetCurrentGcwPoints(player), minRating - 1, pvpGetCurrentPvpKills(player), pvpGetLifetimeGcwPoints(player), pvpGetMaxGcwImperialRating(player), pvpGetMaxGcwRebelRating(player), pvpGetLifetimePvpKills(player), pvpGetNextGcwRatingCalcTime(player));
            triggerOnPvpRankingChanged(player, rank, rank - 1);
        }
    }
    public static float getGroundQuestTierBonus(int quest_tier) throws InterruptedException
    {
        float tierValue = 0.0f;
        switch (quest_tier)
        {
            case 1:
            tierValue = 0.03f;
            break;
            case 2:
            tierValue = 0.039f;
            break;
            case 3:
            tierValue = 0.047f;
            break;
            case 4:
            tierValue = 0.054f;
            break;
            case 5:
            tierValue = 0.06f;
            break;
            default:
            break;
        }
        return tierValue;
    }
    public static void doLogging(String section, String message) throws InterruptedException
    {
        CustomerServiceLog(section, message);
    }
    public static region getPvPRegion(obj_id controller) throws InterruptedException
    {
        region[] regions = getRegionsAtPoint(getLocation(controller));
        if (regions == null || regions.length == 0)
        {
            return null;
        }
        region pvpRegion = null;
        for (region region : regions) {
            if (isNotifyRegion(region)) {
                pvpRegion = region;
            }
        }
        return pvpRegion;
    }
    public static void getRegionToRegister(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return;
        }
        region pvpRegion = getPvPRegion(controller);
        if (pvpRegion == null)
        {
            return;
        }
        String regionName = pvpRegion.getName();
        if (regionName != null)
        {
            utils.setScriptVar(controller, "pvp_region", regionName);
            registerPvpRegionControllerWithPlanet(controller, regionName);
        }
        return;
    }
    public static boolean isPlayerValidOnBattlefield(obj_id player, obj_id controller) throws InterruptedException
    {
        return true;
    }
    public static String getBattlefieldRegionName(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return null;
        }
        location loc = getLocation(controller);
        if (loc == null)
        {
            return null;
        }
        return PVP_BATTLEFIELD_REGION + "_" + loc.x + "_" + loc.z;
    }
    public static String getPushbackRegionName(obj_id controller) throws InterruptedException
    {
        if (!isIdValid(controller))
        {
            return null;
        }
        location loc = getLocation(controller);
        if (loc == null)
        {
            return null;
        }
        return PVP_PUSHBACK_REGION + "_" + loc.x + "_" + loc.z;
    }
    public static boolean canEntertainGcwNonPlayingCharacter(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(npc) || !exists(npc))
        {
            return false;
        }
        if (!factions.isSameFactionorFactionHelper(player, npc))
        {
            return false;
        }
        String entertainerFaction = factions.getFaction(player);
        if (!factions.isImperialHelper(player) && !factions.isRebelHelper(player) && (entertainerFaction == null || entertainerFaction.length() <= 0))
        {
            sendSystemMessage(player, SID_YOU_NEED_TO_BE_COMBATANT);
            doAnimationAction(npc, "shake_head_no");
            return false;
        }
        if (!groundquests.isQuestActive(player, GCW_ENTERTAINER_PATROL_QUEST))
        {
            sendSystemMessage(player, SID_YOU_NEED_ENTERTAINMENT_QUEST);
            return false;
        }
        if (buff.hasBuff(npc, GCW_NPC_ENTERTAINED_BUFF))
        {
            return false;
        }
        return true;
    }
    public static boolean setEntertainGcwNonPlayerCharacter(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(npc) || !exists(npc))
        {
            return false;
        }
        if (!canEntertainGcwNonPlayingCharacter(player, npc))
        {
            return false;
        }
        int flags = 0;
        flags |= sui.CD_EVENT_INCAPACITATE;
        flags |= sui.CD_EVENT_DAMAGED;
        flags |= sui.CD_EVENT_COMBAT;
        flags |= sui.CD_EVENT_LOCOMOTION;
        flags |= sui.CD_EVENT_STEALTHED;
        stealth.testInvisNonCombatAction(player, npc);
        utils.setScriptVar(player, GCW_SCRIPTVAR_PARENT + ".playerEntertainmentStart", getGameTime());
        utils.setScriptVar(player, GCW_SCRIPTVAR_PARENT + ".gcwNpc", npc);
        utils.setScriptVar(npc, GCW_SCRIPTVAR_PARENT + ".isGcwEntertained", 1);
        LOG("gcw_entertainer", "gcwNpc: " + npc);
        int pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_ENTERTAIN_TROOPS_TIMER, 0, GCW_ENTERTAINMENT_TIME, "handleEntertainingGcwTroops", 0, flags);
        sui.setPid(player, pid, ENTERTAIN_GCW_TROOPS_PID);
        return true;
    }
    public static boolean canGcwObjectBeRepaired(obj_id object) throws InterruptedException
    {
        LOG("gcw_patrol_point", "canGcwObjectBeRepaired");
        if (!isValidId(object) || !exists(object))
        {
            return false;
        }
        if (hasObjVar(object, "repairCount") && getIntObjVar(object, "repairCount") >= REPAIR_COUNT_MAX)
        {
            return false;
        }
        int currentHp = getHitpoints(object);
        if (currentHp <= 0)
        {
            return false;
        }
        int maxHp = getMaxHitpoints(object);
        if (maxHp <= 0)
        {
            return false;
        }
        if (currentHp == maxHp)
        {
            return false;
        }
        int threshold = maxHp - currentHp;
        if (threshold <= MIN_REPAIR_AMOUNT)
        {
            return false;
        }
        LOG("gcw_patrol_point", "canGcwObjectBeRepaired passed all validation");
        return true;
    }
    public static boolean useGcwObjectForQuest(obj_id player, obj_id gcwObject, String questName) throws InterruptedException
    {
        LOG("gcw_patrol_point", "useGcwObjectForQuest init");
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(gcwObject) || !exists(gcwObject))
        {
            return false;
        }
        int flags = 0;
        flags |= sui.CD_EVENT_INCAPACITATE;
        flags |= sui.CD_EVENT_LOCOMOTION;
        flags |= sui.CD_EVENT_STEALTHED;
        stealth.testInvisNonCombatAction(player, gcwObject);
        int timeToRepair = 10 + getFatigueTimerMod(player);
        if (timeToRepair > GCW_FATIGUE_TIMER_MAX)
        {
            timeToRepair = GCW_FATIGUE_TIMER_MAX;
        }
        int pid = -1;
        switch (questName) {
            case GCW_SPY_PATROL_DESTROY_QUEST:
                timeToRepair = 5;
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleOpposingFactionDestroyQuest");
                utils.setScriptVar(player, "spyPatrolPoint", gcwObject);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_DESTROY_PP_TIMER, 0, timeToRepair, "handleOpposingFactionDestroyQuest", 0, flags);
                sui.setPid(player, pid, gcw.SPY_DESTROY_PID);
                break;
            case GCW_SPY_PATROL_SCOUT_QUEST:
                timeToRepair = 5;
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleOpposingFactionScoutQuest");
                utils.setScriptVar(player, "spyPatrolPoint", gcwObject);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_SCOUT_PP_TIMER, 0, timeToRepair, "handleOpposingFactionScoutQuest", 0, flags);
                sui.setPid(player, pid, gcw.SPY_SCOUT_PID);
                break;
            case GCW_REPAIR_PATROL_QUEST:
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleTraderRepairQuest");
                utils.setScriptVar(player, gcw.OBJECT_TO_REPAIR, gcwObject);
                utils.setScriptVar(player, gcw.GCW_REPAIR_QUEST, gcw.GCW_REPAIR_PATROL_QUEST);
                utils.setScriptVar(player, gcw.GCW_REPAIR_RESOURCE_COUNT, gcw.GCW_RESOURCE_COUNT_PATROL);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_REPAIR_PP_TIMER, 0, timeToRepair, "handleTraderRepairQuest", 0, flags);
                sui.setPid(player, pid, gcw.TRADER_REPAIR_PID);
                break;
            case GCW_REPAIR_TURRET_QUEST:
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleTraderRepairQuest");
                utils.setScriptVar(player, gcw.OBJECT_TO_REPAIR, gcwObject);
                utils.setScriptVar(player, gcw.GCW_REPAIR_QUEST, gcw.GCW_REPAIR_TURRET_QUEST);
                utils.setScriptVar(player, gcw.GCW_REPAIR_RESOURCE_COUNT, gcw.GCW_RESOURCE_COUNT_TURRET);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_REPAIR_PP_TIMER, 0, timeToRepair, "handleTraderRepairQuest", 0, flags);
                sui.setPid(player, pid, gcw.TRADER_REPAIR_PID);
                break;
            case GCW_REPAIR_BARRICADE_QUEST:
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleTraderRepairQuest");
                utils.setScriptVar(player, gcw.OBJECT_TO_REPAIR, gcwObject);
                utils.setScriptVar(player, gcw.GCW_REPAIR_QUEST, gcw.GCW_REPAIR_BARRICADE_QUEST);
                utils.setScriptVar(player, gcw.GCW_REPAIR_RESOURCE_COUNT, gcw.GCW_RESOURCE_COUNT_BARRICADE);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_REPAIR_PP_TIMER, 0, timeToRepair, "handleTraderRepairQuest", 0, flags);
                sui.setPid(player, pid, gcw.TRADER_REPAIR_PID);
                break;
            case GCW_REPAIR_VEHICLE_PATROL_QUEST:
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleTraderRepairQuest");
                utils.setScriptVar(player, gcw.OBJECT_TO_REPAIR, gcwObject);
                utils.setScriptVar(player, gcw.GCW_REPAIR_QUEST, gcw.GCW_REPAIR_VEHICLE_PATROL_QUEST);
                utils.setScriptVar(player, gcw.GCW_REPAIR_RESOURCE_COUNT, gcw.GCW_RESOURCE_COUNT_DAMAGED_VEHICLE);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_REPAIR_PP_TIMER, 0, timeToRepair, "handleTraderRepairQuest", 0, flags);
                sui.setPid(player, pid, gcw.TRADER_REPAIR_PID);
                break;
            case GCW_MEDIC_HEAL_QUEST:
                if (isGod(player)) {
                    timeToRepair = 3;
                }
                LOG("gcw_patrol_point", "useGcwObjectForQuest sending to messageHandler handleTraderRepairQuest");
                utils.setScriptVar(player, OBJECT_TO_REPAIR, gcwObject);
                utils.setScriptVar(player, GCW_REPAIR_QUEST, GCW_MEDIC_HEAL_QUEST);
                utils.setScriptVar(player, GCW_REPAIR_RESOURCE_COUNT, GCW_RESOURCE_COUNT_DAMAGED_VEHICLE);
                pid = sui.smartCountdownTimerSUI(player, player, "quest_countdown_timer", SID_HEAL_PP_TIMER, 0, timeToRepair, "handleTraderRepairQuest", 0, flags);
                sui.setPid(player, pid, gcw.TRADER_REPAIR_PID);
                break;
        }
        if (pid < 0)
        {
            return false;
        }
        return true;
    }
    public static boolean repairGcwObject(obj_id object, obj_id player, int resourceCount) throws InterruptedException
    {
        if (!isValidId(object) || !exists(object))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (resourceCount <= 0)
        {
            return false;
        }
        if (hasObjVar(object, GCW_OBJECT_REPAIR_COUNT) && getIntObjVar(object, GCW_OBJECT_REPAIR_COUNT) >= REPAIR_COUNT_MAX)
        {
            return false;
        }
        if (!isMob(object))
        {
            int currentHp = getHitpoints(object);
            if (currentHp <= 0)
            {
                return false;
            }
            int maxHp = getMaxHitpoints(object);
            if (maxHp <= 0)
            {
                return false;
            }
            int threshold = maxHp - currentHp;
            if (threshold <= MIN_REPAIR_AMOUNT)
            {
                sendSystemMessage(player, SID_DOESNT_NEED_REPAIR);
                return false;
            }
            double subtract = resourceCount * 0.2f;
            if ((int)subtract <= 0)
            {
                return false;
            }
            setMaxHitpoints(object, maxHp - (int)subtract);
            setHitpoints(object, currentHp + (int)(resourceCount * 0.8f));
        }
        int repairCount = getIntObjVar(object, GCW_OBJECT_REPAIR_COUNT);
        setObjVar(object, GCW_OBJECT_REPAIR_COUNT, repairCount + 1);
        messageTo(object, "repairComplete", null, 1.0f, false);
        return true;
    }
    public static void playQuestIconParticle(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        location loc = getLocation(self);
        params.put("particleLoc", loc);
        int playIconTime = getGameTime();
        params.put("iconMessageTime", playIconTime);
        utils.setScriptVar(self, "iconMessageTime", playIconTime);
        messageTo(self, "playQuestIcon", params, 1.0f, false);
    }
    public static void playQuestIconHandler(obj_id self, dictionary params) throws InterruptedException
    {
        int playIconTime = utils.getIntScriptVar(self, "iconMessageTime");
        int messageTime = params.getInt("iconMessageTime");
        if (playIconTime != messageTime)
        {
            return;
        }
        location loc = params.getLocation("particleLoc");
        float offset = params.getFloat("offset");
        if (loc != null)
        {
            String particleName = utils.getStringScriptVar(self, "gcwQuestIcon");
            if (particleName != null && particleName.length() > 0)
            {
                playClientEffectLoc(self, "appearance/" + particleName, loc, offset);
            }
            else
            {
                playClientEffectLoc(self, "appearance/pt_icon_quest_red.prt", loc, offset);
            }
            playIconTime = getGameTime();
            params.put("iconMessageTime", playIconTime);
            utils.setScriptVar(self, "iconMessageTime", playIconTime);
            messageTo(self, "playQuestIcon", params, 6.0f, false);
        }
    }
    public static boolean signalAllParticipantsForDamage(obj_id victimObject, String questName, String signalHasName, String signalSendName) throws InterruptedException
    {
        LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage init");
        if (!isValidId(victimObject) || !exists(victimObject))
        {
            return false;
        }
        if (questName == null || questName.length() <= 0)
        {
            return false;
        }
        if (signalHasName == null || signalHasName.length() <= 0)
        {
            return false;
        }
        if (signalSendName == null || signalSendName.length() <= 0)
        {
            return false;
        }
        obj_id[] attackerList = utils.getObjIdBatchScriptVar(victimObject, "creditForKills.attackerList.attackers");
        if (attackerList == null || attackerList.length <= 0)
        {
            LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage attackerList NULL");
            return false;
        }
        LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage attackerList.length: " + attackerList.length);
        for (obj_id obj_id : attackerList) {
            LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage attackerList[i]: " + obj_id);
            if (!isValidId(obj_id)) {
                continue;
            }
            trial.addNonInstanceFactionParticipant(obj_id, victimObject);
            LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage attackerList[i]: " + obj_id + " is a valid OID");
            if (!groundquests.isQuestActive(obj_id, questName)) {
                continue;
            }
            LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage attackerList[i]: " + obj_id + " has quest active!");
            if (!groundquests.isTaskActive(obj_id, questName, signalHasName)) {
                continue;
            }
            LOG("signalAllParticipantsForDamage", "signalAllParticipantsForDamage signal being sent to: " + obj_id);
            groundquests.sendSignal(obj_id, signalSendName);
            gcw.gcwInvasionCreditForDestroy(obj_id);
        }
        return true;
    }
    public static boolean hasConstructionOrRepairTool(obj_id player, obj_id object) throws InterruptedException
    {
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 1");
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 2");
        if (!isValidId(object) || !exists(object))
        {
            return false;
        }
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 3");
        obj_id playerInventory = utils.getInventoryContainer(player);
        if (!isValidId(playerInventory) || !exists(playerInventory))
        {
            return false;
        }
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 4");
        if (!hasObjVar(object, GCW_SPAWN_ID_OBJVAR))
        {
            return false;
        }
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 5");
        String spawnId = getStringObjVar(object, GCW_SPAWN_ID_OBJVAR);
        if (spawnId == null || spawnId.length() <= 0)
        {
            return false;
        }
        String desiredTemplate = "";
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 6 spawnId: " + spawnId);
        if (!hasObjVar(object, GCW_TOOL_TEMPLATE_OBJVAR))
        {
            return false;
        }
        desiredTemplate = getStringObjVar(object, GCW_TOOL_TEMPLATE_OBJVAR);
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 7 desiredTemplate: " + desiredTemplate);
        if (desiredTemplate == null || desiredTemplate.length() <= 0)
        {
            return false;
        }
        LOG("gcw_patrol_point", "hasConstructionOrRepairTool 8");
        return utils.playerHasItemByTemplateInInventoryOrEquipped(player, desiredTemplate);
    }
    public static boolean useConstructionOrRepairTool(obj_id player, obj_id object) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(object) || !exists(object))
        {
            return false;
        }
        if (!hasObjVar(object, GCW_SPAWN_ID_OBJVAR))
        {
            return false;
        }
        String spawnId = getStringObjVar(object, GCW_SPAWN_ID_OBJVAR);
        if (spawnId == null || spawnId.length() <= 0)
        {
            return false;
        }
        String desiredTemplate = "";
        if (spawnId.equals(GCW_DEFENSE_PATROL_OBJVAR) || spawnId.equals(GCW_OFFENSE_PATROL_OBJVAR))
        {
            if (!hasObjVar(object, GCW_DEF_TOOL_TEMPLATE_OBJVAR) || !hasObjVar(object, GCW_OFF_TOOL_TEMPLATE_OBJVAR))
            {
                return false;
            }
            if (spawnId.equals(GCW_DEFENSE_PATROL_OBJVAR))
            {
                desiredTemplate = getStringObjVar(object, GCW_DEF_TOOL_TEMPLATE_OBJVAR);
            }
            if (spawnId.equals(GCW_OFFENSE_PATROL_OBJVAR))
            {
                desiredTemplate = getStringObjVar(object, GCW_OFF_TOOL_TEMPLATE_OBJVAR);
            }
        }
        else
        {
            if (!hasObjVar(object, GCW_TOOL_TEMPLATE_OBJVAR))
            {
                return false;
            }
            desiredTemplate = getStringObjVar(object, GCW_TOOL_TEMPLATE_OBJVAR);
        }
        obj_id toolObject = utils.getItemByTemplateInInventoryOrEquipped(player, desiredTemplate);
        if (!isValidId(toolObject) || !exists(toolObject))
        {
            return false;
        }
        gcwInvasionCreditForCrafting(player);
        return decrementCount(toolObject);
    }
    public static int getGcwCityInvasionPhase(obj_id childObject) throws InterruptedException
    {
        if (!isValidId(childObject) || !exists(childObject))
        {
            return GCW_CITY_PHASE_UNKNOWN;
        }
        obj_id parent = trial.getParent(childObject);
        if (!isValidId(parent))
        {
            return GCW_CITY_PHASE_UNKNOWN;
        }
        if (utils.hasScriptVar(parent, "gcw.constructionEnded"))
        {
            return GCW_CITY_PHASE_COMBAT;
        }
        if (utils.hasScriptVar(parent, "gcw.invasionRunning"))
        {
            return GCW_CITY_PHASE_CONSTRUCTION;
        }
        return GCW_CITY_PHASE_UNKNOWN;
    }
    public static boolean playerSystemMessageResourceNeeded(obj_id player, obj_id object, boolean construction) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(object) || !exists(object))
        {
            return false;
        }
        if (!hasObjVar(object, gcw.GCW_TOOL_TEMPLATE_OBJVAR) && !hasObjVar(object, gcw.GCW_DEF_TOOL_TEMPLATE_OBJVAR) && !hasObjVar(object, gcw.GCW_OFF_TOOL_TEMPLATE_OBJVAR))
        {
            return false;
        }
        String toolTemplate = "";
        if (hasObjVar(object, GCW_DEF_TOOL_TEMPLATE_OBJVAR) && hasObjVar(object, GCW_OFF_TOOL_TEMPLATE_OBJVAR))
        {
            if (hasObjVar(object, GCW_SPAWN_ID_OBJVAR) && (getStringObjVar(object, GCW_SPAWN_ID_OBJVAR)).equals(GCW_DEFENSE_PATROL_OBJVAR))
            {
                toolTemplate = getStringObjVar(object, gcw.GCW_DEF_TOOL_TEMPLATE_OBJVAR);
            }
            else if (hasObjVar(object, GCW_SPAWN_ID_OBJVAR) && (getStringObjVar(object, GCW_SPAWN_ID_OBJVAR)).equals(GCW_OFFENSE_PATROL_OBJVAR))
            {
                toolTemplate = getStringObjVar(object, gcw.GCW_OFF_TOOL_TEMPLATE_OBJVAR);
            }
        }
        else
        {
            toolTemplate = getStringObjVar(object, gcw.GCW_TOOL_TEMPLATE_OBJVAR);
        }
        if (toolTemplate == null || toolTemplate.length() <= 0)
        {
            return false;
        }
        string_id objName = getNameFromTemplate(toolTemplate);
        if (objName == null)
        {
            return false;
        }
        prose_package pp = new prose_package();
        string_id msg = new string_id();
        if (construction)
        {
            msg = SID_RESOURCES_NEEDED;
        }
        else
        {
            msg = SID_YOU_NEED_TOOL_REPAIR;
        }
        pp = prose.setStringId(pp, msg);
        pp = prose.setTO(pp, objName);
        sendSystemMessageProse(player, pp);
        return true;
    }
    public static int getFatigueTimerMod(obj_id player) throws InterruptedException
    {
        int fatigueStack = (int)buff.getBuffStackCount(player, BUFF_PLAYER_FATIGUE);
        int fatigueMod = 0;
        if (fatigueStack > 0)
        {
            fatigueMod = fatigueStack / GCW_FATIGUE_DIVISOR;
        }
        return fatigueMod;
    }
    public static obj_id getInvasionSequencerNearby(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target))
        {
            return null;
        }
        obj_id keren = utils.stringToObjId(GCW_SEQUENCER_KEREN);
        obj_id bestine = utils.stringToObjId(GCW_SEQUENCER_BESTINE);
        obj_id dearic = utils.stringToObjId(GCW_SEQUENCER_DEARIC);
        if (isIdValid(keren) && exists(keren))
        {
            return keren;
        }
        if (isIdValid(bestine) && exists(bestine))
        {
            return bestine;
        }
        if (isIdValid(dearic) && exists(dearic))
        {
            return dearic;
        }
        return null;
    }
    public static String getCityFromTable(obj_id self) throws InterruptedException
    {
        String cityName = "";
        String datatable = getStringObjVar(self, "instance.data_table");
        if (datatable == null || datatable.length() < 1)
        {
            return null;
        }
        switch (datatable) {
            case DEARIC_CITY_TABLE:
                return CITY_DEARIC;
            case KEREN_CITY_TABLE:
                return CITY_KEREN;
            case BESTINE_CITY_TABLE:
                return CITY_BESTINE;
        }
        return null;
    }
    public static boolean awardGcwInvasionParticipants(Vector participantList, int factionFlag, int gcwTokenAmt, int gcwPointAmt, dictionary finalAnnouncementParams) throws InterruptedException
    {
        if (participantList == null || participantList.size() <= 0)
        {
            CustomerServiceLog("gcw_city_invasion", "gcw.awardInvasionParticipants: participantList array invalid. Returning False.");
            return false;
        }
        if (factionFlag != factions.FACTION_FLAG_REBEL && factionFlag != factions.FACTION_FLAG_IMPERIAL)
        {
            CustomerServiceLog("gcw_city_invasion", "gcw.awardInvasionParticipants: Faction Flag invalid. Returning False.");
            return false;
        }
        if (gcwTokenAmt <= 0 && gcwPointAmt <= 0)
        {
            CustomerServiceLog("gcw_city_invasion", "gcw.awardInvasionParticipants: participantList awards were equal to or less than 0. Returning False.");
            return false;
        }
        String tokenStaticName = "";
        obj_id[] lootList = new obj_id[1];
        if (factionFlag == factions.FACTION_FLAG_IMPERIAL)
        {
            tokenStaticName = GCW_IMPERIAL_TOKEN;
        }
        else if (factionFlag == factions.FACTION_FLAG_REBEL)
        {
            tokenStaticName = GCW_REBEL_TOKEN;
        }
        float multiplier = utils.stringToFloat(getConfigSetting("GameServer", "gcwTokenBonus"));
        if (multiplier > 1)
        {
            gcwTokenAmt *= multiplier;
        }
        for (Object o : participantList) {
            if (!isValidId(((obj_id) o)) || !exists(((obj_id) o))) {
                CustomerServiceLog("gcw_city_invasion", "gcw.awardGcwInvasionParticipants: Player: " + ((obj_id) o) + " is NOT receiving " + gcwTokenAmt + " " + tokenStaticName + " tokens or " + gcwPointAmt + " " + factionFlag + " GCW points (GCW points only awarded to pure faction players, not factionalHelpers) because this player OID is invalid or doesn't exist. Probably due to the player exiting the battle field, traveling or moving to another server process (crossing server boundary).");
                continue;
            }
            if (!isPlayerConnected(((obj_id) o))) {
                CustomerServiceLog("gcw_city_invasion", "gcw.awardGcwInvasionParticipants: Player: " + ((obj_id) o) + " is NOT receiving " + gcwTokenAmt + " " + tokenStaticName + " tokens or " + gcwPointAmt + " " + factionFlag + " GCW points (GCW points only awarded to pure faction players, not factionalHelpers) because this player is NOT CURRENTLY CONNECTED.");
                continue;
            }
            grantUnmodifiedGcwPoints(((obj_id) o), gcwPointAmt);
            if (tokenStaticName == null || tokenStaticName.length() <= 0) {
                CustomerServiceLog("gcw_city_invasion", "gcw.awardGcwInvasionParticipants: Player: " + ((obj_id) o) + " is NOT receiving " + gcwTokenAmt + " " + tokenStaticName + " tokens or " + gcwPointAmt + " " + factionFlag + " GCW points (GCW points only awarded to pure faction players, not factionalHelpers) because the token string name is INVALID. This is probably due to an edge case where the player changed their faction status to be neutral.");
                continue;
            }
            obj_id playerInv = getObjectInSlot(((obj_id) o), utils.SLOT_INVENTORY);
            if (isValidId(playerInv)) {
                obj_id lootCreated = static_item.createNewItemFunction(tokenStaticName, playerInv, gcwTokenAmt);
                lootList[0] = lootCreated;
                showLootBox(((obj_id) o), lootList);
                groundquests.sendPlacedMoreThanOneInInventorySystemMessage(((obj_id) o), lootCreated, gcwTokenAmt);
                if (finalAnnouncementParams != null) {
                    messageTo(((obj_id) o), "playIconicGCWWrapUpMessage", finalAnnouncementParams, 13, false);
                }
                CustomerServiceLog("gcw_city_invasion", "GCW CITY SYSTEM HAS REWARDED PLAYER: " + ((obj_id) o) + " with " + gcwTokenAmt + " " + tokenStaticName + " static item tokens for PARTICIPATING IN A GCW CITY CONFLICT.");
            }
        }
        return true;
    }
    public static boolean invasionIsValidAndEngaged() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self) || !exists(self))
        {
            return false;
        }
        obj_id sequencer = getInvasionSequencerNearby(self);
        if (!isIdValid(sequencer) || !exists(sequencer))
        {
            return false;
        }
        if (!utils.hasScriptVar(sequencer, "gcw.invasionRunning"))
        {
            return false;
        }
        return true;
    }
    public static void gcwSetCredits(obj_id who, int playerGCW, int playerPvpKills, int playerKills, int playerAssists, int playerCraftedItems, int playerDestroyedItems) throws InterruptedException
    {
        if (!isIdValid(who) || !exists(who) || !isPlayer(who))
        {
            return;
        }
        gcw_score.gcw_data currentData = gcw_score.getPlayerGcwData(who);
        String playerName = getName(who);
        String playerFaction = factions.getFaction(who);
        String factionColor = "";
        if (factions.getFactionFlag(who) == factions.FACTION_FLAG_REBEL)
        {
            factionColor = COLOR_REBELS;
        }
        else if (factions.getFactionFlag(who) == factions.FACTION_FLAG_IMPERIAL)
        {
            factionColor = COLOR_IMPERIALS;
        }
        if (playerFaction == null || playerFaction.length() <= 0)
        {
            if (factions.isRebelHelper(who))
            {
                factionColor = COLOR_REBELS;
                playerFaction = "Rebel Helper";
            }
            else if (factions.isImperialHelper(who))
            {
                factionColor = COLOR_IMPERIALS;
                playerFaction = "Imperial Helper";
            }
            else
            {
                return;
            }
        }
        playerFaction = factionColor + playerFaction;
        String playerProfession = "@ui_roadmap:" + skill.getProfessionName(getSkillTemplate(who));
        int playerLevel = getLevel(who);
        if (currentData != null)
        {
            playerGCW = currentData.playerGCW + playerGCW;
            playerPvpKills = currentData.playerPvpKills + playerPvpKills;
            playerKills = currentData.playerKills + playerKills;
            playerAssists = currentData.playerAssists + playerAssists;
            playerCraftedItems = currentData.playerCraftedItems + playerCraftedItems;
            playerDestroyedItems = currentData.playerDestroyedItems + playerDestroyedItems;
        }
        gcw_score.setPlayerGcwData(who, playerName, playerFaction, playerProfession, playerLevel, playerGCW, playerPvpKills, playerKills, playerAssists, playerCraftedItems, playerDestroyedItems);
    }
    public static void gcwInvasionCreditForGCW(obj_id who, int gcw) throws InterruptedException
    {
        if (!invasionIsValidAndEngaged())
        {
            return;
        }
        gcwSetCredits(who, gcw, 0, 0, 0, 0, 0);
    }
    public static void gcwInvasionCreditForPVPKill(obj_id who) throws InterruptedException
    {
        if (!invasionIsValidAndEngaged())
        {
            return;
        }
        gcwSetCredits(who, 0, 1, 0, 0, 0, 0);
    }
    public static void gcwInvasionCreditForKill(obj_id who) throws InterruptedException
    {
        if (!invasionIsValidAndEngaged())
        {
            return;
        }
        gcwSetCredits(who, 0, 0, 1, 0, 0, 0);
    }
    public static void gcwInvasionCreditForAssist(obj_id who) throws InterruptedException
    {
        if (!invasionIsValidAndEngaged())
        {
            return;
        }
        gcwSetCredits(who, 0, 0, 0, 1, 0, 0);
    }
    public static void gcwInvasionCreditForCrafting(obj_id who) throws InterruptedException
    {
        if (!invasionIsValidAndEngaged())
        {
            return;
        }
        gcwSetCredits(who, 0, 0, 0, 0, 1, 0);
    }
    public static void gcwInvasionCreditForDestroy(obj_id who) throws InterruptedException
    {
        if (!invasionIsValidAndEngaged())
        {
            return;
        }
        gcwSetCredits(who, 0, 0, 0, 0, 0, 1);
    }
    public static String getFormattedInvasionTime(obj_id self, int phase) throws InterruptedException
    {
        obj_id planet = getPlanetByName("tatooine");
        if (!isIdValid(planet) || !exists(planet))
        {
            return "";
        }
        int currentTime = getCalendarTime();
        String cityName = getCityFromTable(self);
        if (cityName == null || cityName.length() <= 0)
        {
            return "";
        }
        int lastPulse = utils.getIntScriptVar(planet, "gcw.calendar_time." + cityName);
        int nextPulseMultiplier = utils.getIntScriptVar(planet, "gcw.calendar_time_next." + cityName);
        if (phase == GCW_CITY_PHASE_CONSTRUCTION)
        {
            int nextPulse = (lastPulse + GCW_CONSTRUCTION_END_TIMER);
            return utils.padTimeHM(nextPulse - currentTime);
        }
        else if (phase == GCW_CITY_PHASE_COMBAT)
        {
            int nextPulse = (lastPulse + GCW_CONSTRUCTION_END_TIMER + GCW_COMBAT_END_TIMER);
            return utils.padTimeHM(nextPulse - currentTime);
        }
        else if (phase == GCW_CITY_PHASE_UNKNOWN)
        {
            int nextPulse = gcwGetNextInvasionTime(cityName);
            return utils.padTimeHM(nextPulse);
        }
        return "";
    }
    public static int gcwGetTimeToInvasion() throws InterruptedException
    {
        String timeConfig = getConfigSetting("GameServer", "gcwInvasionCycleTime");
        if (timeConfig == null || timeConfig.length() <= 0)
        {
            return 3;
        }
        int time = utils.stringToInt(timeConfig);
        if (time > 6)
        {
            time = 6;
        }
        if (time < 1)
        {
            time = 1;
        }
        return time;
    }
    public static int gcwGetInvasionMaximumRunning() throws InterruptedException
    {
        String maxConfig = getConfigSetting("GameServer", "gcwInvasionCityMaximumRunning");
        int max = 3;
        if (maxConfig == null || maxConfig.length() <= 0)
        {
            return max;
        }
        return utils.stringToInt(maxConfig);
    }
    public static boolean gcwIsInvasionCityOn(String city) throws InterruptedException
    {
        if (city == null || city.length() <= 0)
        {
            return false;
        }
        String cityConfig = getConfigSetting("GameServer", "gcwcity" + city);
        if (cityConfig == null || (!cityConfig.equals("1") && !cityConfig.toLowerCase().equals("true")))
        {
            CustomerServiceLog("gcw_city_invasion", "gcw.gcwIsInvasionCityOn: GCW City: " + city + " is not configured to run a city invasion. Function returning False.");
            return false;
        }
        return true;
    }
    public static int gcwGetNextInvasionHour(String cityName) throws InterruptedException
    {
        int activeCityCount = gcwGetActiveCityCount();
        int currentCycle = gcwCalculateInvasionCycle();
        int invasionInterval = gcwGetTimeToInvasion();
        int currentHour = player_structure.convertSecondsTime(getCalendarTime())[1];

        // loop through possible cycles to find when the city will be next active.
        int checkCycle = currentCycle;
        int checkHour = currentHour;
        for(int i = 0; i < activeCityCount; i++){
            checkCycle++;
            checkHour = checkHour + invasionInterval;
            if(checkCycle >= activeCityCount) checkCycle = 0;
            if(gcwHasInvasionInCycle(cityName, checkCycle)) {
                break;
            }
        }
        int nextHour = checkHour;

        if(nextHour > 23)
            nextHour = nextHour - 24;
        return nextHour;
    }
    public static boolean gcwHasInvasionInCycle(String cityName, int cycle) throws InterruptedException
    {

        // if the max is set to 0 then nothing should happen.
        int maxRunning = gcwGetInvasionMaximumRunning();
        if(maxRunning == 0){
            LOG("gcwlog","maxrunning is 0");
            return false;
        }

        // if the total active cities is 0 or if the cycle is larger that the cities that are active
        // then nothing should happen.
        String[] activeCities = gcwGetActiveCities();
        if(activeCities.length == 0 || cycle >= activeCities.length)
        {
            LOG("gcwlog","no active cities or cycle is bigger than active cities.  Active city length is " + activeCities.length + " and cycle is " + cycle);
            return false;
        }

        int cityIndex = -1;
        for(int i = 0; i < activeCities.length; i++){
            if(activeCities[i].equals(cityName)){
                cityIndex = i;
                break;
            }
        }
        // if the city in question was not found in the active cities then nothing should happen.
        if(cityIndex == -1)
        {
            LOG("gcwlog","hmmm... " + cityName + " wasn't found in active cities...");
            return false;
        }

        // make sure total active cities is capped at total available to be active.
        if(maxRunning > activeCities.length)
            maxRunning = INVASION_CITIES.length;

        LOG("gcwlog","Checking if city (" + cityName + ") is active in cycle (" + cycle + ") with active city count of (" + activeCities.length + ") and max running (" + maxRunning + ") with city index of (" + cityIndex + ").");

        switch(activeCities.length){
            case 1:
                return true;
            case 2:
                if(maxRunning == 1){
                    return cityIndex == cycle;
                }
                return true;
            case 3:
                switch(maxRunning){
                    case 1:
                        return cityIndex == cycle;
                    case 2:
                        return ((cycle == 0 && (cityIndex == 0 || cityIndex == 1))
                                || (cycle == 1 && (cityIndex == 1 || cityIndex == 2))
                                || (cycle == 2 && (cityIndex == 2 || cityIndex == 0)));
                    case 3:
                        return true;
                }
                break;
            default:
                return false;
        }
        return false;
        /*
        int totalActiveCities = gcwGetActiveCityCount();
        if (cycle < 0 || cycle >= totalActiveCities)
        {
            return false;
        }
        int maximumRunning = gcwGetInvasionMaximumRunning();
        boolean[] bestineSchedule = null;
        boolean[] dearicSchedule = null;
        boolean[] kerenSchedule = null;
        switch (maximumRunning)
        {
            case 0:
                return false;
            case 1:
                bestineSchedule = new boolean[]
                        {
                                true,
                                false,
                                false
                        };
                dearicSchedule = new boolean[]
                        {
                                false,
                                true,
                                false
                        };
                kerenSchedule = new boolean[]
                        {
                                false,
                                false,
                                true
                        };
                break;
            case 2:
                bestineSchedule = new boolean[]
                        {
                                true,
                                false,
                                true
                        };
                dearicSchedule = new boolean[]
                        {
                                true,
                                true,
                                false
                        };
                kerenSchedule = new boolean[]
                        {
                                false,
                                true,
                                true
                        };
                break;
            case 3:
                return true;
            default:
                return false;
        }
        if (cityName.equals("bestine"))
        {
            if(gcwIsInvasionCityOn(cityName))
                return bestineSchedule[cycle];
        }
        else if (cityName.equals("dearic"))
        {
            if(gcwIsInvasionCityOn(cityName))
                return dearicSchedule[cycle];
        }
        else if (cityName.equals("keren"))
        {
            if(gcwIsInvasionCityOn(cityName))
                return kerenSchedule[cycle];
        }
        return false;
        */
    }
    public static String[] gcwGetActiveCities() throws InterruptedException
    {
        Vector<String> activeCities = new Vector<>();
        for (String cityName : INVASION_CITIES){
            String value = getConfigSetting("GameServer", "gcwcity" + cityName);
            if (value != null && (value.equals("1") || value.toLowerCase().equals("true"))) {
                activeCities.add(cityName);
            }
        }
        return activeCities.toArray(new String[activeCities.size()]);
    }
    public static int gcwGetActiveCityCount() throws InterruptedException
    {
        return gcwGetActiveCities().length;
    }
    public static int gcwCalculateInvasionCycle() throws InterruptedException
    {
        int invasionInterval = gcwGetTimeToInvasion();  // from config setting.
        int totalActiveCities = gcwGetActiveCityCount();
        if (invasionInterval <= 0)
        {
            invasionInterval = 3;
        }

        int[] convertedCalendarTime = player_structure.convertSecondsTime(getCalendarTime());
        int hour = convertedCalendarTime[1];
        return ((hour / invasionInterval) % totalActiveCities);
    }
    // GMT
    // Returns seconds until the next invasion
    public static int gcwGetNextInvasionTime(String cityName) throws InterruptedException
    {
        LOG("gcwlog", "gcwGetNextInvasionTime cityName: " + cityName);
        if (cityName == null || cityName.length() <= 0 || !gcwIsInvasionCityOn(cityName))
        {
            LOG("gcwlog", "cityName: " + cityName + " !gcwIsInvasionCityOn(cityName): " + !gcwIsInvasionCityOn(cityName));
            return -1;
        }

        // Get the invasion interval.  Interval defines the amount of time between invasion cycles.
        int invasionInterval = gcwGetTimeToInvasion();

        // Divide by zero?
        if (invasionInterval <= 0)
        {
            invasionInterval = 3; // Default to an invasion every three hours; 8 per day per city, if all cities are on
        }
        int calendarTime = getCalendarTime();

        int[] convertedCalendarTime = player_structure.convertSecondsTime(calendarTime);
        int hour = convertedCalendarTime[1];

        // get what cycle we are currently in.  A cycle is defined as a single invasion and where it occurs in the day.
        // Depending on configuration, each city may have 1 invasion per cycle.  Interval determines the amount of time
        // (in hours) between the start of each cycle.
        int cycle = gcwCalculateInvasionCycle();

        int nextCycle = cycle + 1;
        int totalActiveCities = gcwGetActiveCityCount();
        if (nextCycle >= totalActiveCities)
        {
            nextCycle = 0;
        }

        // Find out if the given city is running in the next cycle or not.
        boolean invasionOnNextInterval = gcwHasInvasionInCycle(cityName, nextCycle);
        LOG("gcwlog", "XXXXXXXX cityName: " + cityName + (invasionOnNextInterval ? " does" : " does not") + " have an invasion next cycle.  Cycle: " + cycle + " hour: " + hour + " nextCycle: " + nextCycle);

        int nextHour;
        // Next interval is skipped?  Return the time to the next interval plus another interval cycle.
        if (!invasionOnNextInterval)
        {
            // next hour should be the next time the given city should run.
            nextHour = gcwGetNextInvasionHour(cityName);
        }
        else
        {
            // next hour should be the next time the given city should run.
            nextHour = hour + invasionInterval;
        }

        if (nextHour > 23)
        {
            nextHour = nextHour - 24;
        }

        LOG("gcwlog", "cityName: " + cityName + " nextInterval: " + invasionOnNextInterval + " nextHour: " + nextHour);
        return secondsUntilNextDailyTime(nextHour, 0, 0);
    }
    public static boolean gcwTutorialCheck(obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (hasObjVar(player, GCW_TUTORIAL_FLAG))
        {
            return false;
        }
        gcwCityHelpText(player);
        setObjVar(player, GCW_TUTORIAL_FLAG, 1);
        return true;
    }
    public static boolean gcwCityHelpText(obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        String gcwHelpData = localize(new string_id("gcw", "gcw_tutorial_text"));
        String uiTitle = "GCW City Conflict Tutorial";
        int page = createSUIPage("/Script.messageBox", player, player);
        setSUIProperty(page, "Prompt.lblPrompt", "LocalText", gcwHelpData);
        setSUIProperty(page, "bg.caption.lblTitle", "Text", uiTitle);
        setSUIProperty(page, "Prompt.lblPrompt", "Editable", "false");
        setSUIProperty(page, "Prompt.lblPrompt", "GetsInput", "false");
        setSUIProperty(page, "btnCancel", "Visible", "false");
        setSUIProperty(page, "btnRevert", "Visible", "false");
        setSUIProperty(page, "btnOk", sui.PROP_TEXT, "Get Nearest GCW City Waypoints");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedOk, "%button0%", "handleGcwCityHelpUi");
        showSUIPage(page);
        flushSUIPage(page);
        return true;
    }
}
