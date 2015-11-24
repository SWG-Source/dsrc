package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.Vector;
import script.library.create;
import script.library.player_structure;
import script.library.static_item;
import script.library.utils;

public class house_pet extends script.base_script
{
    public house_pet()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "massiff_pet";
    public static final String SARLACC_LOOT_TABLE = "datatables/loot/sarlacc_cts_housepet_loot.iff";
    public static final String SARLACC_CTS_CRC_TABLE = "datatables/minigame/sarlacc_cts_crc.iff";
    public static final String HOUSE_PET_TEMPLATE_OBJVAR = "house_pet";
    public static final String CONTROLLER_PREFIX = "controller";
    public static final String STATIC_ITEM_DEED_NAME = CONTROLLER_PREFIX + ".static_item_deed_name";
    public static final String CHILD_OBJ_ID = CONTROLLER_PREFIX + ".child_id";
    public static final String PET_HAS_BEEN_CALLED = CONTROLLER_PREFIX + ".pet_called";
    public static final String PET_HUE_INDEX1 = CONTROLLER_PREFIX + ".pet_hue_index1";
    public static final String HOUSEPET_PREFIX = "house_pet";
    public static final String PARENT_OBJ_ID = HOUSEPET_PREFIX + ".parent_id";
    public static final String SARLACC_BASE_LOCATION = HOUSEPET_PREFIX + ".last_location";
    public static final String PET_NAME = HOUSEPET_PREFIX + ".name";
    public static final String PET_MOVING = HOUSEPET_PREFIX + ".is_moving";
    public static final String BEING_FED = HOUSEPET_PREFIX + ".being_fed";
    public static final String MASSIFF_CURRENT_PHASE = HOUSEPET_PREFIX + ".current_phase";
    public static final String MASSIFF_LAST_FED = HOUSEPET_PREFIX + ".last_fed";
    public static final String MASSIFF_FOOD_OBJ = HOUSEPET_PREFIX + ".food_object";
    public static final String MASSIFF_EGG_COUNT = HOUSEPET_PREFIX + ".egg_count";
    public static final int SCURRIER_SNACK_DELAY = 259200;
    public static final String SCURRIER_ITEM_01 = "item_tcg_scurrier_trash_item_02_01";
    public static final String SCURRIER_ITEM_02 = "item_tcg_scurrier_trash_item_02_02";
    public static final String SCURRIER_ITEM_03 = "item_tcg_scurrier_trash_item_02_03";
    public static final String SCURRIER_ITEM_04 = "item_tcg_scurrier_trash_item_02_04";
    public static final String SCURRIER_SNACK_LAST_FED = "scurrier_last_snack";
    public static final String PET_SNACK_OID = CONTROLLER_PREFIX + ".pet_snack_oid";
    public static final String SCURRIER_SNACK_OBJ = HOUSEPET_PREFIX + ".snack_object";
    public static final int SARLACC_FED = 0;
    public static final int SARLACC_HUNGRY = 1;
    public static final int SARLACC_STARVING = 2;
    public static final int SARLACC_DEATH = 3;
    public static final String[] SARLACC_PHASES = 
    {
        "phase_fed",
        "phase_hungry",
        "phase_starving",
        "phase_death"
    };
    public static final String[] MASSIFF_PHASES = 
    {
        "phase_fed",
        "phase_hungry",
        "dirt_nap"
    };
    public static final String SARLACC_PREFIX = "sarlacc_minigame";
    public static final String SARLACC_BORN = SARLACC_PREFIX + ".born_time";
    public static final String EDIBLES_PREFIX = SARLACC_PREFIX + ".edibles";
    public static final String EDIBLES_SUI = EDIBLES_PREFIX + ".sui";
    public static final String EDIBLES_IDS = EDIBLES_PREFIX + ".ids";
    public static final String SARLACC_CRC = SARLACC_PREFIX + ".sarlacc_stomach";
    public static final String SARLACC_FEED_ARRAY = SARLACC_PREFIX + ".feed_array";
    public static final String SARLACC_CURRENT_PHASE = SARLACC_PREFIX + ".current_phase";
    public static final String SARLACC_LAST_FED = SARLACC_PREFIX + ".last_fed";
    public static final String SARLACC_FEEDING_ITERATION = SARLACC_PREFIX + ".feeding_iteration";
    public static final String SARLACC_PHASE_START = SARLACC_PREFIX + ".phase_start";
    public static final String SARLACC_PHASE_WEEK = SARLACC_PREFIX + ".phase_week";
    public static final String SARLACC_OWNER_AFFECTION = SARLACC_PREFIX + ".last_affection";
    public static final String SARLACC_REWARD_AVAILABLE = SARLACC_PREFIX + ".reward_radial";
    public static final String SARLACC_REWARD_COLUMN = SARLACC_PREFIX + ".reward_column";
    public static final String SARLACC_PENALTY_VAR = SARLACC_PREFIX + ".penalty_delivered";
    public static final String SARLACC_AVOID_REPEATED_UPDATES = SARLACC_PREFIX + ".no_updated_duplicates";
    public static final String SARLACC_FAST_UPDATE_RATE = SARLACC_PREFIX + ".god_hax";
    public static final String SARLACC_DAILY_UPDATE_RATE = SARLACC_PREFIX + ".god_hax_daily";
    public static final String MASSIFF_FIVE_MIN_UPDATE_RATE = SARLACC_PREFIX + ".god_hax_five_min";
    public static final int SARLACC_UPDATE_WEEK = 604800;
    public static final int SARLACC_UPDATE_DAY = 86400;
    public static final int SARLACC_PHASE_WEEKS = 4;
    public static final int SARLACC_DAYS_IN_WEEK = 7;
    public static final int SARLACC_REWARD_WEEK = SARLACC_PHASE_WEEKS + 1;
    public static final int SARLACC_UPDATED_SECONDS = 300;
    public static final int SARLACC_GOD_WEEK = 35;
    public static final int SARLACC_GOD_DAY = 5;
    public static final int SARLACC_GOD_DAILY_WEEK = 86400;
    public static final int SARLACC_GOD_DAILY_DAY = 12342;
    public static final int MASSIFF_GOD_DAILY_WEEK = 300;
    public static final int MASSIFF_GOD_DAILY_DAY = 30;
    public static final String SOUND_SILENCE = "sound/music_silence.snd";
    public static final String MUSIC_RANGE = "music_range";
    public static final String PET_TRIG_VOLUME = "petTriggerVol";
    public static final String PET_SOUND_LABEL = "petSoundLabel";
    public static final String HOUSE_PET_MENU_PID = HOUSEPET_PREFIX + "housePetMenuPID";
    public static final String MASSIFF_SCRIPT = "systems.tcg.tcg_massif_creature";
    public static final String[] ALL_MASSIF_ANIMATIONS = 
    {
        "alert",
        "ashamed",
        "confused",
        "drink",
        "eat",
        "fidget",
        "happy",
        "look",
        "look_left",
        "look_right",
        "nervous",
        "scratch",
        "sniff",
        "stretch",
        "threaten",
        "trick_1",
        "trick_2",
        "vocalize"
    };
    public static final String MASSIF_ANIMATION_ALERT = "alert";
    public static final String MASSIF_ANIMATION_ASHAMED = "ashamed";
    public static final String MASSIF_ANIMATION_CONFUSED = "confused";
    public static final String MASSIF_ANIMATION_DRINK = "drink";
    public static final String MASSIF_ANIMATION_EAT = "eat";
    public static final String MASSIF_ANIMATION_FIDGET = "fidget";
    public static final String MASSIF_ANIMATION_HAPPY = "happy";
    public static final String MASSIF_ANIMATION_LOOK = "look";
    public static final String MASSIF_ANIMATION_LOOK_LEFT = "look_left";
    public static final String MASSIF_ANIMATION_LOOK_RIGHT = "look_right";
    public static final String MASSIF_ANIMATION_NERVOUS = "nervous";
    public static final String MASSIF_ANIMATION_SCRATCH = "scratch";
    public static final String MASSIF_ANIMATION_SNIFF = "sniff";
    public static final String MASSIF_ANIMATION_STRETCH = "stretch";
    public static final String MASSIF_ANIMATION_THREATEN = "threaten";
    public static final String MASSIF_ANIMATION_TRICK1 = "trick_1";
    public static final String MASSIF_ANIMATION_TRICK2 = "trick_2";
    public static final String MASSIF_ANIMATION_VOCALIZE = "vocalize";
    public static final String[] ALL_MASSIF_STATES = 
    {
        "none",
        "sleeping",
        "lying",
        "sitting_ground",
        "test"
    };
    public static final int SARLACC_VERSION = 2;
    public static final String SARLACC_VERSION_VAR = SARLACC_PREFIX + ".sarlacc_version";
    public static final String SARLACC_FREE_CHANCE = SARLACC_PREFIX + ".used_free_chance";
    public static final String NO_SARLACC_SOUND = SARLACC_PREFIX + ".no_sound";
    public static final String MASSIF_STATE_COMBAT = "combat";
    public static final String MASSIF_STATE_SLEEPING = "sleeping";
    public static final String MASSIF_STATE_LYING = "lying";
    public static final String MASSIF_STATE_SITTING = "sitting_ground";
    public static final String SIT = "sit";
    public static final String TRICK = "trick";
    public static final String EAT = "eat";
    public static final String DRINK = "drink";
    public static final String BAD = "bad";
    public static final String COME = "come";
    public static final String GOOD = "good";
    public static final String HEEL = "heel";
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String SLEEP = "sleep";
    public static final String TAKE = "take";
    public static final String BED = "bed";
    public static final String QUIET = "quiet";
    public static final String STAY = "stay";
    public static final String HERE = "here";
    public static final String BACK = "back";
    public static final String POTTY = "potty";
    public static final String BREAK = "break";
    public static final String POODOO = "poodoo";
    public static final String POOP = "poop";
    public static final String FETCH = "fetch";
    public static final String SPEAK = "speak";
    public static final String BARK = "bark";
    public static final String DEAD = "dead";
    public static final String PLAYDEAD = "playdead";
    public static final String DIE = "die";
    public static final String CONFUSED = "confused";
    public static final String BEG = "beg";
    public static final String SOUND_GROWL_RANDOM_MASSIFF = "sound/cr_massiff_random_growl.snd";
    public static final String SOUND_WHINE_RANDOM_MASSIFF = "sound/cr_massiff_random_whine.snd";
    public static final String SOUND_GROWL_RANDOM_NUNA = "sound/cr_nuna_random_growl.snd";
    public static final String SOUND_WHINE_RANDOM_NUNA = "sound/cr_nuna_vocalize.snd";
    public static final String SOUND_GROWL_RANDOM_SCURRIER = "sound/cr_blistmok_emote.snd";
    public static final String SOUND_WHINE_RANDOM_SCURRIER = "sound/cr_blistmok_attack_light.snd";
    public static final String HOUSE_PET_OBJVAR = "house_pet_creature";
    public static final String HOUSE_PET_SCRIPT = "house_pet_script";
    public static final String MASSIFF_CREATURE = "tcg_massiff_pet";
    public static final String PET_FEEDABLE = "pet_feedable";
    public static final String PET_FOOD_OID = CONTROLLER_PREFIX + ".pet_food_oid";
    public static final int MASSIFF_FED = 0;
    public static final int MASSIFF_HUNGRY = 1;
    public static final int MASSIFF_DEAD = 2;
    public static final String PET_KNOCKED_OUT = CONTROLLER_PREFIX + ".pet_knocked_out";
    public static final String MASSIFF_HUNGRY_EMOTE = "appearance/pt_massiff_hungry_emote.prt";
    public static final String NUNA_HUNGRY_EMOTE = "appearance/pt_nuna_hungry_emote.prt";
    public static final String SCURRIER_HUNGRY_EMOTE = "appearance/pt_scurrier_hungry_emote.prt";
    public static final String MASSIFF_POO_STATIC_ITEM = "item_tcg_nuna_rotten_egg_02_01";
    public static boolean setObjectOwner(obj_id controller) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!utils.isNestedWithinAPlayer(controller))
        {
            return false;
        }
        obj_id owner = utils.getContainingPlayer(controller);
        if (!isValidId(owner) && !exists(owner))
        {
            return false;
        }
        setOwner(controller, owner);
        return true;
    }
    public static obj_id getObjectOwner(obj_id controller) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return null;
        }
        obj_id owner = getOwner(controller);
        if (!isValidId(owner))
        {
            if (!setObjectOwner(controller))
            {
                return null;
            }
        }
        return owner;
    }
    public static obj_id recreateSarlaccObjectAtLocationFromObjVar(obj_id controller) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return null;
        }
        location where = getLocation(controller);
        if (where == null)
        {
            return null;
        }
        if (!hasObjVar(controller, HOUSE_PET_TEMPLATE_OBJVAR))
        {
            return null;
        }
        String mobileTemplate = getStringObjVar(controller, HOUSE_PET_TEMPLATE_OBJVAR);
        if (mobileTemplate == null || mobileTemplate.equals(""))
        {
            return null;
        }
        obj_id housePetObj = create.staticObject(mobileTemplate, where);
        if (!isValidId(housePetObj) || !exists(housePetObj))
        {
            return null;
        }
        setObjVar(controller, CHILD_OBJ_ID, housePetObj);
        setObjVar(housePetObj, PARENT_OBJ_ID, controller);
        setInvulnerable(housePetObj, true);
        if (mobileTemplate.indexOf("s02.iff") > -1)
        {
            attachScript(housePetObj, "event.housepackup.sarlacc_mini_game");
        }
        return housePetObj;
    }
    public static boolean recreateObjectFromObjVar(obj_id controller) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        location where = getLocation(controller);
        if (where == null)
        {
            return false;
        }
        String npcType = "";
        if (!hasObjVar(controller, HOUSE_PET_TEMPLATE_OBJVAR))
        {
            return false;
        }
        String mobileTemplate = getStringObjVar(controller, HOUSE_PET_TEMPLATE_OBJVAR);
        if (mobileTemplate == null || mobileTemplate.equals(""))
        {
            return false;
        }
        return buildHousePetInPlayerStructure(controller, mobileTemplate, where);
    }
    public static boolean buildHousePetInPlayerStructure(obj_id controller, String mobileTemplate, location spawnLoc) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (mobileTemplate == null || mobileTemplate.equals(""))
        {
            return false;
        }
        if (spawnLoc == null)
        {
            return false;
        }
        if (!validateNpcPlacementInStructure(controller))
        {
            return false;
        }
        obj_id housePetObj = create.staticObject(mobileTemplate, spawnLoc);
        if (!isValidId(housePetObj) || !exists(housePetObj))
        {
            return false;
        }
        setObjVar(controller, CHILD_OBJ_ID, housePetObj);
        setObjVar(housePetObj, PARENT_OBJ_ID, controller);
        setInvulnerable(housePetObj, true);
        if (mobileTemplate.indexOf("s02.iff") > -1)
        {
            attachScript(housePetObj, "event.housepackup.sarlacc_mini_game");
        }
        return true;
    }
    public static boolean validateNpcPlacementInStructure(obj_id object) throws InterruptedException
    {
        return validateNpcPlacementInStructure(object, obj_id.NULL_ID);
    }
    public static boolean validateNpcPlacementInStructure(obj_id object, obj_id operator) throws InterruptedException
    {
        if (!isValidId(object) || !exists(object))
        {
            return false;
        }
        obj_id structure = getTopMostContainer(object);
        if (!isValidId(structure) || !exists(structure))
        {
            return false;
        }
        if (!player_structure.isPlayerStructure(structure) || player_structure.isCivic(structure))
        {
            return false;
        }
        if (isIdNull(operator))
        {
            return true;
        }
        if (player_structure.isOwner(structure, operator))
        {
            return true;
        }
        if (player_structure.isAdmin(structure, operator))
        {
            return true;
        }
        return false;
    }
    public static boolean cleanUpHousePet(obj_id housePet) throws InterruptedException
    {
        return cleanUpHousePet(obj_id.NULL_ID, housePet);
    }
    public static boolean cleanUpHousePet(obj_id controller, obj_id housePet) throws InterruptedException
    {
        if (!isValidId(housePet) || !exists(housePet))
        {
            return false;
        }
        if (hasTriggerVolume(housePet, PET_TRIG_VOLUME))
        {
            removeTriggerVolume(PET_TRIG_VOLUME);
        }
        stopPlayingMusic(housePet);
        destroyObject(housePet);
        if (isValidId(controller) && exists(controller))
        {
            removeObjVar(controller, house_pet.CHILD_OBJ_ID);
        }
        return true;
    }
    public static int getUpdateWeekly(obj_id controller) throws InterruptedException
    {
        if (hasObjVar(controller, house_pet.SARLACC_FAST_UPDATE_RATE))
        {
            return house_pet.SARLACC_GOD_WEEK;
        }
        else if (hasObjVar(controller, house_pet.SARLACC_DAILY_UPDATE_RATE))
        {
            return house_pet.SARLACC_GOD_DAILY_WEEK;
        }
        else if (hasObjVar(controller, house_pet.MASSIFF_FIVE_MIN_UPDATE_RATE))
        {
            return house_pet.MASSIFF_GOD_DAILY_WEEK;
        }
        else 
        {
            return house_pet.SARLACC_UPDATE_WEEK;
        }
    }
    public static int getUpdateDaily(obj_id controller) throws InterruptedException
    {
        if (hasObjVar(controller, house_pet.SARLACC_FAST_UPDATE_RATE))
        {
            return house_pet.SARLACC_GOD_DAY;
        }
        else if (hasObjVar(controller, house_pet.SARLACC_DAILY_UPDATE_RATE))
        {
            return house_pet.SARLACC_GOD_DAILY_DAY;
        }
        else if (hasObjVar(controller, house_pet.MASSIFF_FIVE_MIN_UPDATE_RATE))
        {
            return house_pet.MASSIFF_GOD_DAILY_DAY;
        }
        else 
        {
            return house_pet.SARLACC_UPDATE_DAY;
        }
    }
    public static boolean stopPlayingMusic(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, MUSIC_RANGE))
        {
            return false;
        }
        location here = getLocation(self);
        if (here == null)
        {
            return false;
        }
        float range = getFloatObjVar(self, MUSIC_RANGE) + 10;
        if (range <= 0)
        {
            return false;
        }
        obj_id[] playersList = getPlayerCreaturesInRange(here, range);
        if (playersList == null || playersList.length <= 0)
        {
            return false;
        }
        for (int i = 0; i < playersList.length; i++)
        {
            obj_id player = playersList[i];
            if (!isIdValid(player))
            {
                continue;
            }
            stopClientEffectObjByLabel(player, self, PET_SOUND_LABEL);
        }
        return true;
    }
    public static boolean isInAPlayerHouse(obj_id device) throws InterruptedException
    {
        if (!isValidId(device) || !exists(device))
        {
            return false;
        }
        if (utils.isNestedWithinAPlayer(device))
        {
            return false;
        }
        obj_id selfContainer = getContainedBy(device);
        obj_id ship = space_transition.getContainingShip(selfContainer);
        if (isIdValid(ship))
        {
            String templateName = getTemplateName(ship);
            if (space_utils.isPobType(templateName))
            {
                return true;
            }
            return false;
        }
        obj_id house = getTopMostContainer(device);
        if (isIdValid(house) && !player_structure.isBuilding(house))
        {
            return false;
        }
        if (player_structure.isPackedUp(house))
        {
            return false;
        }
        if (utils.isInHouseCellSpace(device))
        {
            return true;
        }
        return false;
    }
    public static boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
