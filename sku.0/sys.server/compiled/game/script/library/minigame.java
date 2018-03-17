package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.loot;
import script.library.utils;
import script.library.prose;
import script.library.colors;
import script.library.resource;
import script.library.session;
import script.library.static_item;

public class minigame extends script.base_script
{
    public minigame()
    {
    }
    public static final String SCRIPT_FISHING_PLAYER = "fishing.player";
    public static final String SCRIPT_FISHING_NPC = "fishing.npc";
    public static final String SCRIPT_FISHING_POLE = "fishing.pole";
    public static final int ROLL_FISH_OR_JUNK = 94;
    public static final String VAR_FISH_BASE = "fish";
    public static final String VAR_FISH_NAME = VAR_FISH_BASE + ".name";
    public static final String VAR_FISH_LENGTH = VAR_FISH_BASE + ".length";
    public static final String VAR_FISH_LOCATION = VAR_FISH_BASE + ".location";
    public static final String VAR_FISH_STAMP = VAR_FISH_BASE + ".stamp";
    public static final String VAR_FISH_TIME_STAMP = VAR_FISH_BASE + ".time";
    public static final String VAR_FISH_CATCHER = VAR_FISH_BASE + ".catcher";
    public static final float FISHING_TICK = 7f;
    public static final float SPOOL_RANGE = 20f;
    public static final String SPLASH_EFFECT = "object/tangible/fishing/splash.iff";
    public static final String TBL_TEMPLATE = "datatables/fishing/fish/fish_template.iff";
    public static final String COL_NAME = "name";
    public static final String COL_TEMPLATE = "template";
    public static final String COL_HUE1 = "hueColor1";
    public static final String COL_HUE2 = "hueColor2";
    public static final String COL_LENGTH_NORM = "lengthNorm";
    public static final String COL_LENGTH_MIN = "lengthMin";
    public static final String COL_LENGTH_MAX = "lengthMax";
    public static final String COL_RESOURCE_TYPE = "resourceType";
    public static final String COL_RESOURCE_NORM = "resourceNorm";
    public static final String COL_RESOURCE_MIN = "resourceMin";
    public static final String COL_RESOURCE_MAX = "resourceMax";
    public static final String COL_CHUM_NORM = "chumNorm";
    public static final String COL_TROPHY_TEMPLATE = "trophyTemplate";
    public static final String STF_FISH = "fishing";
    public static final string_id SID_START_FISHING = new string_id(STF_FISH, "start_fishing");
    public static final string_id SID_STOP_FISHING = new string_id(STF_FISH, "stop_fishing");
    public static final string_id SID_BAD_CAST = new string_id(STF_FISH, "bad_cast");
    public static final string_id SID_LOCATION_UNFISHABLE = new string_id(STF_FISH, "loc_unfishable");
    public static final string_id PROSE_TOSS_BAIT = new string_id(STF_FISH, "prose_toss_bait");
    public static final string_id SID_TOSS_BAIT = new string_id(STF_FISH, "toss_bait");
    public static final string_id SID_FS_NIBBLE = new string_id(STF_FISH, "fs_nibble");
    public static final string_id SID_FS_BITE = new string_id(STF_FISH, "fs_bite");
    public static final string_id SID_FS_CAUGHT = new string_id(STF_FISH, "fs_caught");
    public static final string_id SID_FS_SNAG = new string_id(STF_FISH, "fs_snag");
    public static final string_id SID_FS_LOOT = new string_id(STF_FISH, "fs_loot");
    public static final string_id SID_LINE_FREE = new string_id(STF_FISH, "line_free");
    public static final string_id SID_LINE_SNAP = new string_id(STF_FISH, "line_snap");
    public static final string_id SID_HARD_NIBBLE = new string_id(STF_FISH, "hard_nibble");
    public static final string_id SID_HARD_BITE = new string_id(STF_FISH, "hard_bite");
    public static final string_id SID_LOST_BAIT = new string_id(STF_FISH, "lost_bait");
    public static final string_id SID_LOST_CATCH = new string_id(STF_FISH, "lost_catch");
    public static final string_id SID_REEL_IN = new string_id(STF_FISH, "reel_in");
    public static final string_id SID_LINE_SPOOLED = new string_id(STF_FISH, "line_spooled");
    public static final string_id SID_FISH_FIGHT_EASY = new string_id(STF_FISH, "fish_fight_easy");
    public static final string_id SID_FISH_FIGHT_HARD = new string_id(STF_FISH, "fish_fight_hard");
    public static final string_id SID_FISH_FIGHT_AWAY = new string_id(STF_FISH, "fish_fight_away");
    public static final string_id SID_FISH_FIGHT_CLOSER = new string_id(STF_FISH, "fish_fight_closer");
    public static final string_id SID_FISH_RUN = new string_id(STF_FISH, "fish_run");
    public static final string_id SID_FISH_CHARGE = new string_id(STF_FISH, "fish_charge");
    public static final string_id SID_LOOT_BEACHED = new string_id(STF_FISH, "loot_beached");
    public static final string_id SID_REEL_LOOT = new string_id(STF_FISH, "reel_loot");
    public static final string_id PROSE_NOTIFY_CATCH = new string_id(STF_FISH, "prose_notify_catch");
    public static final string_id FLY_NIBBLE = new string_id(STF_FISH, "fly_nibble");
    public static final string_id FLY_BITE = new string_id(STF_FISH, "fly_bite");
    public static final string_id FLY_CATCH = new string_id(STF_FISH, "fly_catch");
    public static final string_id FLY_SNAG = new string_id(STF_FISH, "fly_snag");
    public static final String SCRIPTVAR_OLD_MOOD = "fishing.oldMood";
    public static final String SCRIPTVAR_STATUS = "fishing.status";
    public static final String SCRIPTVAR_SUI = "fishing.sui";
    public static final String SCRIPTVAR_POLE = "fishing.pole";
    public static final String SCRIPTVAR_LOCATION = "fishing.location";
    public static final String SCRIPTVAR_ACTION = "fishing.action";
    public static final String SCRIPTVAR_BONUS = "fishing.bonus";
    public static final String SCRIPTVAR_CAUGHT = "fishing.caught";
    public static final String SCRIPTVAR_MARKER = "fishing.marker";
    public static final String TEMPLATE_MARKER = "object/tangible/fishing/marker.iff";
    public static final int FS_NONE = 0;
    public static final int FS_CAST = 1;
    public static final int FS_WAIT = 2;
    public static final int FS_NIBBLE = 3;
    public static final int FS_BITE = 4;
    public static final int FS_CAUGHT = 5;
    public static final int FS_SNAG = 6;
    public static final int FS_LOOT = 7;
    public static final String[] FISHING_STATUS = 
    {
        "None",
        "Just Cast Line...",
        "Waiting.....",
        "Nibble...",
        "BITE!",
        "CAUGHT SOMETHING!",
        "Line Snagged?",
        "CAUGHT SOMETHING!"
    };
    public static final int FA_NONE = 0;
    public static final int FA_TUG_UP = 1;
    public static final int FA_TUG_RIGHT = 2;
    public static final int FA_TUG_LEFT = 3;
    public static final int FA_SMALL_REEL = 4;
    public static final int FA_STOP_FISHING = 5;
    public static final int FA_MAX = 5;
    public static final String[] FISHING_ACTIONS = 
    {
        "None",
        "Tug Up",
        "Tug Right",
        "Tug Left",
        "Small Reel",
        "Stop Fishing"
    };
    public static final String[] ENCODED_FISHING_ACTIONS = 
    {
        "@" + STF_FISH + ":fa_tug_up",
        "@" + STF_FISH + ":fa_tug_right",
        "@" + STF_FISH + ":fa_tug_left",
        "@" + STF_FISH + ":fa_small_reel",
        "@" + STF_FISH + ":fa_stop"
    };
    public static final String SCRIPTVAR_IN_USE = "fishing.inUse";
    public static final String VAR_FISHING_BAIT_BASE = "fishing.bait";
    public static final String VAR_FISHING_BAIT_STATUS = VAR_FISHING_BAIT_BASE + ".status";
    public static final int BS_NONE = 0;
    public static final int BS_FRESH = 1;
    public static final int BS_SOGGY = 2;
    public static final int BS_MUSH = 3;
    public static final String[] BAIT_STATUS = 
    {
        "None",
        "Fresh",
        "Soggy",
        "Mush"
    };
    public static final String HANDLER_FISHING_TICK = "handleFishingTick";
    public static final String HANDLER_FISHING_SUI = "handleFishingSui";
    public static final String HANDLER_PLAY_CAST_SPLASH = "handlePlayCastSplash";
    public static final String HANDLER_REEL_IN = "handleReelIn";
    public static final String HANDLER_CAUGHT_SOMETHING = "handleCaughtSomething";
    public static final String HANDLER_BAIT_SUI = "handleBaitSui";
    public static boolean startFishing(obj_id target) throws InterruptedException
    {
        LOG("fishing", "************ startFishing: now = " + getGameTime() + " ****************");
        LOG("fishing", "startFishing: entered... target = " + target);
        if (!isIdValid(target))
        {
            return false;
        }
        if (isFishing(target))
        {
            return false;
        }
        obj_id pole = getObjectInSlot(target, "hold_r");
        if (!isIdValid(pole))
        {
            return false;
        }
        if (!isFishingPole(pole))
        {
            sendSystemMessage(target, new string_id(STF_FISH, "must_have_pole"));
            return false;
        }
        if (!isFishingPoleBaited(pole))
        {
            sendSystemMessage(target, new string_id(STF_FISH, "bait_your_pole"));
            return false;
        }
        obj_id playerCurrentMount = getMountId(target);
        boolean isMounted = isIdValid(playerCurrentMount);
        boolean isOnYodaChair = false;
        if (isMounted)
        {
            String template = getTemplateName(playerCurrentMount);
            if (!template.equals("object/mobile/vehicle/hover_chair.iff"))
            {
                sendSystemMessage(target, new string_id(STF_FISH, "not_this_vehicle"));
                return false;
            }
            else 
            {
                isOnYodaChair = true;
            }
        }
        if (getState(target, STATE_SWIMMING) == 1 && !isOnYodaChair)
        {
            sendSystemMessage(target, new string_id(STF_FISH, "no_fish_swim"));
            return false;
        }
        location castLoc = getFishingCastLocation(target);
        if (castLoc == null)
        {
            return false;
        }
        if (isPlayer(target))
        {
            attachScript(target, SCRIPT_FISHING_PLAYER);
            sendSystemMessage(target, new string_id(STF_FISH, "cast_line"));
        }
        else 
        {
            attachScript(target, SCRIPT_FISHING_NPC);
        }
        LOG("fishing", "startFishing: casting line...");
        float range = getDistance(getLocation(target), castLoc);
        float castTime = getFishingCastTime(range);
        LOG("fishing", "startFishing: casting range = " + range + " time = " + castTime);
        if (castTime < 0f)
        {
            return false;
        }
        session.logActivity(target, session.ACTIVITY_FISHING);
        utils.setScriptVar(pole, SCRIPTVAR_IN_USE, target);
        chat.setTempAnimationMood(target, "fishing");
        doAnimationAction(target, "fishing_cast");
        LOG("fishing", "startFishing: setting initial state to FS_CAST");
        setFishingState(target, FS_CAST);
        LOG("fishing", "startFishing: storing initial data...");
        initializeFishingData(target, pole, castLoc);
        dictionary params = new dictionary();
        params.put("pole", pole);
        params.put("castLoc", castLoc);
        LOG("fishing", "startFishing: messaging HANDLER_PLAY_CAST_SPLASH...");
        messageTo(target, HANDLER_PLAY_CAST_SPLASH, params, castTime, false);
        return true;
    }
    public static void stopFishing(obj_id target, boolean animate) throws InterruptedException
    {
        LOG("fishing", "************ stopFishing: now = " + getGameTime() + " ****************");
        LOG("fishing", "stopFishing: entered... target = " + target);
        if (!isIdValid(target))
        {
            return;
        }
        if (utils.hasScriptVar(target, SCRIPTVAR_MARKER))
        {
            obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
            if (isIdValid(marker))
            {
                destroyObject(marker);
            }
            utils.removeScriptVar(target, SCRIPTVAR_MARKER);
        }
        if (isPlayer(target))
        {
            if (!animate)
            {
                utils.setScriptVar(target, "noAnimate", true);
            }
            detachScript(target, SCRIPT_FISHING_PLAYER);
            sendSystemMessage(target, new string_id(STF_FISH, "stop_fishing"));
        }
        else 
        {
            detachScript(target, SCRIPT_FISHING_NPC);
        }
    }
    public static void stopFishing(obj_id target) throws InterruptedException
    {
        stopFishing(target, true);
    }
    public static boolean isPoleInUse(obj_id pole) throws InterruptedException
    {
        if (!isIdValid(pole))
        {
            return false;
        }
        return utils.hasScriptVar(pole, SCRIPTVAR_IN_USE);
    }
    public static void playCastSplash(obj_id target, dictionary params) throws InterruptedException
    {
        if (!isIdValid(target) || (params == null) || (params.isEmpty()))
        {
            return;
        }
        location castLoc = params.getLocation("castLoc");
        if (castLoc == null)
        {
            return;
        }
        playClientEffectLoc(getPlayerCreaturesInRange(castLoc, 100.0f), "appearance/pt_splash.prt", castLoc, 0.0f);
        if (utils.hasScriptVar(target, SCRIPTVAR_MARKER))
        {
            obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
            if (isIdValid(marker))
            {
                destroyObject(marker);
            }
            utils.removeScriptVar(target, SCRIPTVAR_MARKER);
        }
        obj_id marker = createObject(TEMPLATE_MARKER, castLoc);
        if (isIdValid(marker))
        {
            setLocation(marker, castLoc);
            faceTo(marker, target);
            utils.setScriptVar(target, SCRIPTVAR_MARKER, marker);
        }
    }
    public static void initializeFishingData(obj_id target, obj_id pole, location castLoc) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(pole) || (castLoc == null))
        {
            return;
        }
        LOG("fishing", "initializeFishingData: setting initial fishing scriptvars on target...");
        utils.setScriptVar(target, SCRIPTVAR_STATUS, FS_CAST);
        utils.setScriptVar(target, SCRIPTVAR_POLE, pole);
        utils.setScriptVar(target, SCRIPTVAR_LOCATION, castLoc);
        obj_id bait = getBait(pole);
        LOG("fishing", "initializeFishingData: bait = " + bait);
        if (!isIdValid(bait))
        {
            return;
        }
        if (!hasObjVar(bait, VAR_FISHING_BAIT_STATUS))
        {
            LOG("fishing", "initializeFishingData: bait lacks objvar VAR_FISHING_BAIT_STATUS. initializing...");
            setObjVar(bait, VAR_FISHING_BAIT_STATUS, BS_FRESH);
        }
    }
    public static void cleanupFishing(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        obj_id pole = utils.getObjIdScriptVar(target, SCRIPTVAR_POLE);
        if (isIdValid(pole))
        {
            cleanupBait(target, pole);
            utils.removeScriptVar(pole, SCRIPTVAR_IN_USE);
        }
        if (!utils.hasScriptVar(target, "noAnimate"))
        {
            doAnimationAction(target, "fishing_reel");
        }
        else 
        {
            utils.removeScriptVar(target, "noAnimate");
        }
        if (utils.hasScriptVar(target, SCRIPTVAR_MARKER))
        {
            obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
            if (isIdValid(marker))
            {
                destroyObject(marker);
            }
        }
        utils.removeScriptVar(target, SCRIPTVAR_STATUS);
        utils.removeScriptVar(target, SCRIPTVAR_POLE);
        utils.removeScriptVar(target, SCRIPTVAR_LOCATION);
        utils.removeScriptVar(target, SCRIPTVAR_ACTION);
        utils.removeScriptVar(target, SCRIPTVAR_BONUS);
        utils.removeScriptVar(target, SCRIPTVAR_CAUGHT);
        utils.removeScriptVar(target, SCRIPTVAR_MARKER);
        utils.removeScriptVar(target, SCRIPTVAR_OLD_MOOD);
        closeFishingSui(target);
        chat.resetTempAnimationMood(target);
    }
    public static boolean isFishing(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        return hasScript(target, SCRIPT_FISHING_PLAYER) || hasScript(target, SCRIPT_FISHING_NPC);
    }
    public static boolean isFishingPole(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (!hasScript(target, SCRIPT_FISHING_POLE))
        {
            return false;
        }
        return true;
    }
    public static void updateBaitStatus(obj_id target) throws InterruptedException
    {
        LOG("fishing", "updateBaitStatus: entering...");
        if (!isIdValid(target))
        {
            return;
        }
        if (!isFishing(target))
        {
            return;
        }
        LOG("fishing", "updateBaitStatus: getting target's (" + target + ") fishing pole");
        obj_id pole = utils.getObjIdScriptVar(target, SCRIPTVAR_POLE);
        if (!isIdValid(pole))
        {
            return;
        }
        if (!isFishingPole(pole))
        {
            return;
        }
        LOG("fishing", "updateBaitStatus: target = " + target);
        obj_id bait = getBait(pole);
        if (!isIdValid(bait))
        {
            return;
        }
        LOG("fishing", "updateBaitStatus: bait = " + bait);
        if (!hasObjVar(bait, VAR_FISHING_BAIT_STATUS))
        {
            return;
        }
        int status = getIntObjVar(bait, VAR_FISHING_BAIT_STATUS);
        LOG("fishing", "updateBaitStatus: bait status = " + status);
        int roll = rand(0, 100);
        if (isGod(target))
        {
            roll = 100;
            debugSpeakMsg(target, "God Mode is keeping your bait fresh!");
        }
        LOG("fishing", "updateBaitStatus: roll = " + roll);
        if (roll < 20)
        {
            status++;
        }
        if (status > BS_MUSH)
        {
            LOG("fishing", "updateBaitStatus: bait just went from mush to nothing...");
            lostBait(target);
        }
        else 
        {
            setObjVar(bait, VAR_FISHING_BAIT_STATUS, status);
        }
    }
    public static void cleanupBait(obj_id target, obj_id pole) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(pole))
        {
            return;
        }
        if (!isFishingPole(pole))
        {
            return;
        }
        obj_id bait = getBait(pole);
        if (!isIdValid(bait))
        {
            return;
        }
        int baitStatus = getIntObjVar(pole, VAR_FISHING_BAIT_STATUS);
        if (baitStatus > BS_FRESH)
        {
            incrementCount(bait, -1);
            String stringStatus = getEncodedBaitStatus(baitStatus);
            if (stringStatus != null)
            {
                prose_package ppTossBait = prose.getPackage(PROSE_TOSS_BAIT, bait, stringStatus);
                sendSystemMessageProse(target, ppTossBait);
            }
            else 
            {
                sendSystemMessage(target, SID_TOSS_BAIT);
            }
            if (getCount(bait) < 1)
            {
                sendSystemMessage(target, new string_id(STF_FISH, "out_of_bait"));
                destroyObject(bait);
            }
        }
        else if (baitStatus == BS_NONE)
        {
            removeObjVar(bait, VAR_FISHING_BAIT_STATUS);
        }
    }
    public static boolean isFishingPoleBaited(obj_id pole) throws InterruptedException
    {
        if (!isIdValid(pole))
        {
            return false;
        }
        obj_id bait = getBait(pole);
        if (isIdValid(bait))
        {
            return true;
        }
        return false;
    }
    public static obj_id getBait(obj_id pole) throws InterruptedException
    {
        if (!isIdValid(pole))
        {
            return null;
        }
        obj_id[] contents = getContents(pole);
        if (contents != null && contents.length == 1)
        {
            return contents[0];
        }
        return null;
    }
    public static int getBaitStatus(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (!isFishing(target))
        {
            return -1;
        }
        obj_id pole = utils.getObjIdScriptVar(target, SCRIPTVAR_POLE);
        if (!isIdValid(pole))
        {
            return -1;
        }
        if (!isFishingPole(pole))
        {
            return -1;
        }
        obj_id bait = getBait(pole);
        if (!isIdValid(bait))
        {
            return -1;
        }
        if (!hasObjVar(bait, VAR_FISHING_BAIT_STATUS))
        {
            return -1;
        }
        return getIntObjVar(bait, VAR_FISHING_BAIT_STATUS);
    }
    public static String getEncodedBaitStatus(int status) throws InterruptedException
    {
        if (status < 0 || status > BAIT_STATUS.length - 1)
        {
            return null;
        }
        String baitStatus = BAIT_STATUS[status];
        string_id sidStatus = new string_id(STF_FISH, baitStatus);
        return utils.packStringId(sidStatus);
    }
    public static location getFishingCastLocation(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        location here = getLocation(target);
        float yaw = getYaw(target);
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        for (int i = 2; i < 10; i++)
        {
            float range = i + 0.5f;
            location testLoc = utils.rotatePointXZ(here, range, yaw);
            testLoc.y = getHeightAtLocation(testLoc.x, testLoc.z);
            if (isLocationFishable(testLoc))
            {
                if (range < min)
                {
                    min = range;
                }
                if (range > max)
                {
                    max = range;
                }
            }
            else 
            {
                if (i == 2)
                {
                    sendSystemMessage(target, new string_id(STF_FISH, "too_far"));
                    return null;
                }
            }
        }
        if (min == Float.POSITIVE_INFINITY)
        {
            sendSystemMessage(target, new string_id(STF_FISH, "invalid_location"));
            return null;
        }
        LOG("fishing", "getFishingCastLocation: range min = " + min + " max = " + max);
        float castRange = rand(min, max);
        LOG("fishing", "getFishingCastLocation: randomized cast range = " + castRange);
        location there = utils.rotatePointXZ(here, castRange, yaw);
        LOG("fishing", "getFishingCastLocation: straight cast loc = " + there);
        location castLoc = utils.getRandomLocationInRing(there, 0f, 0.5f);
        LOG("fishing", "getFishingCastLocation: ringed cast loc = " + castLoc);
        if (!isLocationFishable(castLoc))
        {
            sendSystemMessage(target, SID_BAD_CAST);
            return null;
        }
        castLoc.y = getWaterTableHeight(castLoc);
        return castLoc;
    }
    public static float getFishingCastTime(float range) throws InterruptedException
    {
        if (range < 0f)
        {
            return -1f;
        }
        return 3f;
    }
    public static boolean isLocationFishable(location loc) throws InterruptedException
    {
        if (loc != null)
        {
            location testLoc = (location)loc.clone();
            testLoc.y = getHeightAtLocation(loc.x, loc.z);
            return isBelowWater(testLoc);
        }
        return false;
    }
    public static int showFishingSui(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || !isFishing(target))
        {
            return -1;
        }
        closeFishingSui(target);
        int status = utils.getIntScriptVar(target, SCRIPTVAR_STATUS);
        obj_id pole = utils.getObjIdScriptVar(target, SCRIPTVAR_POLE);
        obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
        if (!isIdValid(pole) || !isIdValid(marker))
        {
            cleanupFishing(target);
            return -1;
        }
        location loc = getLocation(marker);
        if (loc == null)
        {
            cleanupFishing(target);
            return -1;
        }
        float lineRange = getDistance(getLocation(target), loc);
        obj_id bait = getBait(pole);
        if (!isIdValid(bait) || !hasObjVar(bait, VAR_FISHING_BAIT_STATUS))
        {
            LOG("fishing", "showFishingSui: bad bait data... stop fishing!");
            LOG("fishing", "showFishingSui:  - bait = " + bait);
            stopFishing(target);
            return -1;
        }
        int baitStatus = getIntObjVar(bait, VAR_FISHING_BAIT_STATUS);
        int action = utils.getIntScriptVar(target, SCRIPTVAR_ACTION);
        String title = "@" + STF_FISH + ":sui_title";
        String prompt = "";
        prompt += "STATUS      : " + FISHING_STATUS[status] + "\n";
        prompt += "BAIT STATUS : " + BAIT_STATUS[baitStatus] + "\n";
        if (lineRange >= 10f)
        {
            prompt += "LINE RANGE  : " + ((String.valueOf(lineRange))).substring(0, 5) + " \n";
        }
        else 
        {
            prompt += "LINE RANGE  : " + ((String.valueOf(lineRange))).substring(0, 4) + " \n";
        }
        prompt += "FISH DENSITY: " + getStars(getFishEfficiency(loc)) + "\n";
        prompt += "VEGETATION  : " + getStars(getVegetationSnagFactor(loc)) + "\n";
        prompt += "NEXT ACTION : " + FISHING_ACTIONS[action] + "\n";
        int pid = sui.listbox(target, prompt, title, ENCODED_FISHING_ACTIONS, HANDLER_FISHING_SUI);
        if (pid > -1)
        {
            utils.setScriptVar(target, SCRIPTVAR_SUI, pid);
        }
        return pid;
    }
    public static String getStars(float eff) throws InterruptedException
    {
        if (eff < 0.16f)
        {
            return "";
        }
        else if (eff < 0.32f)
        {
            return "*";
        }
        else if (eff < 0.48f)
        {
            return "**";
        }
        else if (eff < 0.64f)
        {
            return "***";
        }
        else if (eff < 0.80f)
        {
            return "****";
        }
        return "*****";
    }
    public static void closeFishingSui(obj_id target) throws InterruptedException
    {
        if (utils.hasScriptVar(target, SCRIPTVAR_SUI))
        {
            int pid = utils.getIntScriptVar(target, SCRIPTVAR_SUI);
            forceCloseSUIPage(pid);
            utils.removeScriptVar(target, SCRIPTVAR_SUI);
        }
    }
    public static void defaultFishingUpdate(obj_id target, int action) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        int status = utils.getIntScriptVar(target, SCRIPTVAR_STATUS);
        boolean setWait = true;
        switch (action)
        {
            case FA_TUG_UP:
            case FA_TUG_RIGHT:
            case FA_TUG_LEFT:
            if (status == FS_NIBBLE)
            {
                if (checkForBite(target, -0.25f))
                {
                    setWait = false;
                }
            }
            else if (status == FS_BITE)
            {
                if (checkForCatch(target, -0.25f))
                {
                    setWait = false;
                }
            }
            break;
            case FA_SMALL_REEL:
            updateFishingLocation(target, action);
            break;
            case FA_NONE:
            default:
            updateFishingWaitState(target);
            break;
        }
        if (setWait)
        {
            utils.setScriptVar(target, SCRIPTVAR_STATUS, FS_WAIT);
        }
    }
    public static void updateFishingLocation(obj_id target, int action, boolean caught) throws InterruptedException
    {
        LOG("fishing", "updateFishingLocation: entered...");
        if (!isIdValid(target))
        {
            return;
        }
        location here = getLocation(target);
        obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
        if (!isIdValid(marker))
        {
            stopFishing(target);
            return;
        }
        location there = getLocation(marker);
        if (there == null)
        {
            stopFishing(target);
            return;
        }
        float currentDistance = getDistance(here, there);
        LOG("fishing", "updateFishingLocation: here = " + here.toString());
        LOG("fishing", "updateFishingLocation: there = " + there.toString());
        LOG("fishing", "updateFishingLocation: pre-update distance = " + currentDistance);
        float dist = 0f;
        switch (action)
        {
            case FA_TUG_UP:
            case FA_TUG_RIGHT:
            case FA_TUG_LEFT:
            dist = 0.25f;
            break;
            case FA_SMALL_REEL:
            default:
            dist = 0.75f;
            break;
        }
        switch (action)
        {
            case FA_TUG_UP:
            doAnimationAction(target, "fishing_tug_back");
            break;
            case FA_TUG_RIGHT:
            doAnimationAction(target, "fishing_tug_right");
            break;
            case FA_TUG_LEFT:
            doAnimationAction(target, "fishing_tug_left");
            break;
            case FA_SMALL_REEL:
            doAnimationAction(target, "fishing_reel");
            break;
        }
        LOG("fishing", "updateFishingLocation: action distance = " + dist);
        if (currentDistance < dist && !caught)
        {
            LOG("fishing", "updateFishingLocation: current distance > action distance & caught = false... stop fishing!");
            stopFishing(target);
            return;
        }
        float theta = getYaw(marker);
        float dTheta = 0f;
        switch (action)
        {
            case FA_TUG_RIGHT:
            dTheta = rand(-45f, -15f);
            break;
            case FA_TUG_LEFT:
            dTheta = rand(15f, 45f);
            break;
            case FA_TUG_UP:
            case FA_SMALL_REEL:
            default:
            dTheta = rand(-5f, 5f);
            break;
        }
        LOG("fishing", "updateFishingLocation: heading to player = " + theta);
        LOG("fishing", "updateFishingLocation: action heading delta = " + dTheta);
        theta += dTheta;
        LOG("fishing", "updateFishingLocation: revised heading = " + theta);
        location loc = utils.rotatePointXZ(there, dist, theta);
        if (loc == null)
        {
            LOG("fishing", "updateFishingLocation: revised location = null");
            stopFishing(target);
            return;
        }
        loc.y = getWaterTableHeight(loc);
        LOG("fishing", "updateFishingLocation: revised location = " + loc.toString());
        if (!isLocationFishable(loc))
        {
            LOG("fishing", "updateFishingLocation: revised location not fishable.. stop fishing...");
            sendSystemMessage(target, SID_LOCATION_UNFISHABLE);
            stopFishing(target);
            return;
        }
        LOG("fishing", "updateFishingLocation: updating scriptvar location ...");
        setLocation(marker, loc);
        faceTo(marker, target);
        checkForSnag(target, loc);
    }
    public static void updateFishingLocation(obj_id target, int action) throws InterruptedException
    {
        updateFishingLocation(target, action, false);
    }
    public static void setFishingState(obj_id target, int state) throws InterruptedException
    {
        LOG("fishing", "setFishingState: entered... target = " + target + " state = " + state);
        if (!isIdValid(target))
        {
            return;
        }
        utils.setScriptVar(target, SCRIPTVAR_STATUS, state);
        switch (state)
        {
            case FS_WAIT:
            break;
            case FS_NIBBLE:
            markerFlyText(target, FLY_NIBBLE, colors.GREEN);
            sendSystemMessage(target, SID_FS_NIBBLE);
            break;
            case FS_BITE:
            markerFlyText(target, FLY_BITE, colors.SALMON);
            sendSystemMessage(target, SID_FS_BITE);
            break;
            case FS_CAUGHT:
            markerFlyText(target, FLY_CATCH, colors.GOLDYELLOW);
            sendSystemMessage(target, SID_FS_CAUGHT);
            break;
            case FS_SNAG:
            markerFlyText(target, FLY_SNAG, colors.ORANGERED);
            sendSystemMessage(target, SID_FS_SNAG);
            break;
            case FS_LOOT:
            markerFlyText(target, FLY_CATCH, colors.GOLDYELLOW);
            sendSystemMessage(target, SID_FS_LOOT);
            break;
            default:
            break;
        }
        if (state == FS_LOOT || state == FS_CAUGHT)
        {
            utils.setScriptVar(target, SCRIPTVAR_CAUGHT, true);
        }
    }
    public static void updateFishingWaitState(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        float bonus = 0f;
        if (utils.hasScriptVar(target, SCRIPTVAR_BONUS))
        {
            bonus = utils.getFloatScriptVar(target, SCRIPTVAR_BONUS);
        }
        bonus += rand(0f, 0.05f);
        utils.setScriptVar(target, SCRIPTVAR_BONUS, bonus);
    }
    public static boolean checkForNibble(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        int baitStatus = getBaitStatus(target);
        if (baitStatus <= BS_NONE)
        {
            return false;
        }
        float bonus = 0f;
        if (utils.hasScriptVar(target, SCRIPTVAR_BONUS))
        {
            bonus = utils.getFloatScriptVar(target, SCRIPTVAR_BONUS);
        }
        obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
        if (!isIdValid(marker))
        {
            stopFishing(target);
            return false;
        }
        location there = getLocation(marker);
        if (there == null)
        {
            stopFishing(target);
            return false;
        }
        float eff = getFishEfficiency(there);
        float chance = eff + bonus;
        float roll = rand(0f, 1f);
        if (roll == 0f)
        {
            bonus += rand(0.1f, 0.2f);
            utils.setScriptVar(target, SCRIPTVAR_BONUS, bonus);
            if (!checkForBite(target, 0.3f))
            {
                sendSystemMessage(target, SID_HARD_NIBBLE);
                utils.setScriptVar(target, SCRIPTVAR_STATUS, FS_NIBBLE);
            }
            return true;
        }
        if (roll < chance)
        {
            bonus += rand(0.05f, 0.1f);
            utils.setScriptVar(target, SCRIPTVAR_BONUS, bonus);
            setFishingState(target, FS_NIBBLE);
            return true;
        }
        return false;
    }
    public static boolean checkForBite(obj_id target, float superBonus) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        int baitStatus = getBaitStatus(target);
        if (baitStatus <= BS_NONE)
        {
            return false;
        }
        float bonus = 0f;
        if (utils.hasScriptVar(target, SCRIPTVAR_BONUS))
        {
            bonus = utils.getFloatScriptVar(target, SCRIPTVAR_BONUS);
        }
        obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
        if (!isIdValid(marker))
        {
            stopFishing(target);
            return false;
        }
        location there = getLocation(marker);
        if (there == null)
        {
            stopFishing(target);
            return false;
        }
        float eff = getFishEfficiency(there);
        float poleBonus = getPoleBonus(target);
        float chance = eff + bonus + superBonus + poleBonus;
        float roll = rand(0f, 1f);
        if (roll < 0.05f)
        {
            sendSystemMessage(target, SID_HARD_BITE);
            utils.setScriptVar(target, SCRIPTVAR_BONUS, Float.POSITIVE_INFINITY);
            utils.setScriptVar(target, SCRIPTVAR_STATUS, FS_BITE);
            return true;
        }
        if (roll < chance)
        {
            bonus += rand(0.05f, 0.1f);
            utils.setScriptVar(target, SCRIPTVAR_BONUS, bonus);
            setFishingState(target, FS_BITE);
            return true;
        }
        return false;
    }
    public static boolean checkForBite(obj_id target) throws InterruptedException
    {
        return checkForBite(target, 0f);
    }
    public static boolean checkForCatch(obj_id target, float superBonus) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        int baitStatus = getBaitStatus(target);
        if (baitStatus <= BS_NONE)
        {
            return false;
        }
        float bonus = 0f;
        if (utils.hasScriptVar(target, SCRIPTVAR_BONUS))
        {
            bonus = utils.getFloatScriptVar(target, SCRIPTVAR_BONUS);
        }
        boolean caughtFish = false;
        if (bonus == Float.POSITIVE_INFINITY)
        {
            caughtFish = true;
        }
        else 
        {
            float poleBonus = getPoleBonus(target);
            float chance = 0.25f + bonus + superBonus + poleBonus;
            float roll = rand(0f, 1f);
            if (roll < chance)
            {
                caughtFish = true;
            }
        }
        if (caughtFish)
        {
            setFishingState(target, FS_CAUGHT);
            return true;
        }
        return false;
    }
    public static boolean checkForCatch(obj_id target) throws InterruptedException
    {
        return checkForCatch(target, 0f);
    }
    public static boolean checkForSnag(obj_id target, location loc) throws InterruptedException
    {
        if (!isIdValid(target) || loc == null)
        {
            return false;
        }
        float eff = getVegetationSnagFactor(loc);
        if (eff > rand(0.3f, 1f))
        {
            setFishingState(target, FS_SNAG);
            utils.setScriptVar(target, SCRIPTVAR_BONUS, eff);
            return true;
        }
        return false;
    }
    public static float getFishEfficiency(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return -1f;
        }
        String rType = "seafood_fish_" + loc.area;
        LOG("fishing", "getFishEfficiency: rType = " + rType);
        obj_id resourceId = pickRandomNonDepeletedResource(rType);
        if ((resourceId == null) || (resourceId == obj_id.NULL_ID))
        {
            return -1f;
        }
        return getResourceEfficiency(resourceId, loc);
    }
    public static float getVegetationSnagFactor(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return -1f;
        }
        obj_id resourceId = pickRandomNonDepeletedResource("vegetable_greens");
        if ((resourceId == null) || (resourceId == obj_id.NULL_ID))
        {
            return -1f;
        }
        return getResourceEfficiency(resourceId, loc);
    }
    public static void freeSnaggedLine(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        sendSystemMessage(target, SID_LINE_FREE);
        setFishingState(target, FS_WAIT);
        utils.removeScriptVar(target, SCRIPTVAR_BONUS);
    }
    public static void snapFishingLine(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        sendSystemMessage(target, SID_LINE_SNAP);
        decrementBait(target);
        stopFishing(target);
    }
    public static void spoolFishingLine(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        sendSystemMessage(target, SID_LINE_SPOOLED);
        decrementBait(target);
        stopFishing(target);
    }
    public static void caughtLoot(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        setFishingState(target, FS_LOOT);
        utils.removeScriptVar(target, SCRIPTVAR_BONUS);
    }
    public static void lostBait(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (!isFishing(target))
        {
            return;
        }
        decrementBait(target);
        sendSystemMessage(target, SID_LOST_BAIT);
        stopFishing(target);
    }
    public static void decrementBait(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (!isFishing(target))
        {
            return;
        }
        obj_id pole = utils.getObjIdScriptVar(target, SCRIPTVAR_POLE);
        if (!isIdValid(pole))
        {
            return;
        }
        if (!isFishingPole(pole))
        {
            return;
        }
        obj_id bait = getBait(pole);
        if (!isIdValid(bait))
        {
            return;
        }
        if (!hasObjVar(bait, VAR_FISHING_BAIT_STATUS))
        {
            return;
        }
        int baitStatus = getIntObjVar(bait, VAR_FISHING_BAIT_STATUS);
        if (baitStatus != BS_NONE)
        {
            setObjVar(bait, VAR_FISHING_BAIT_STATUS, BS_NONE);
            incrementCount(bait, -1);
            if (getCount(bait) < 1)
            {
                sendSystemMessage(target, new string_id(STF_FISH, "out_of_bait"));
                destroyObject(bait);
            }
        }
    }
    public static int[] getGoodActions() throws InterruptedException
    {
        int numActions = rand(1, 2);
        int[] ret = new int[numActions];
        for (int i = 0; i < numActions; i++)
        {
            ret[i] = rand(FA_TUG_UP, FA_TUG_LEFT);
        }
        return ret;
    }
    public static void confirmReelIn(obj_id target, location castLoc) throws InterruptedException
    {
        if (!isIdValid(target) || (castLoc == null))
        {
            return;
        }
        if (!isFishing(target))
        {
            return;
        }
        int status = utils.getIntScriptVar(target, SCRIPTVAR_STATUS);
        if (status != FS_CAUGHT && status != FS_LOOT)
        {
            return;
        }
        int base_chance = 40;
        float bonus = utils.getFloatScriptVar(target, SCRIPTVAR_BONUS);
        int chance = base_chance + (int)(bonus * 100);
        boolean lostCatch = false;
        int roll = rand(0, 100);
        if (isGod(target))
        {
            roll = 1;
            debugSpeakMsg(target, "Bypassing -minigame.loseCatch- due to God Mode!");
        }
        if (roll > 98)
        {
            lostCatch = true;
        }
        if (lostCatch)
        {
            loseCatch(target);
            return;
        }
        else 
        {
            sendSystemMessage(target, SID_REEL_IN);
            float delay = rand(1.5f, 5f);
            dictionary params = new dictionary();
            params.put("castLoc", castLoc);
            messageTo(target, HANDLER_REEL_IN, params, delay, false);
        }
    }
    public static void loseCatch(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        sendSystemMessage(target, SID_LOST_CATCH);
        lostBait(target);
    }
    public static void markerFlyText(obj_id target, string_id sid, color textColor) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (sid == null)
        {
            return;
        }
        if (textColor == null)
        {
            return;
        }
        obj_id marker = utils.getObjIdScriptVar(target, SCRIPTVAR_MARKER);
        if (!isIdValid(marker))
        {
            return;
        }
        showFlyText(marker, sid, 1.0f, textColor);
    }
    public static float getPoleBonus(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return 0f;
        }
        obj_id pole = utils.getObjIdScriptVar(target, SCRIPTVAR_POLE);
        if (!isIdValid(pole))
        {
            return 0f;
        }
        if (!isFishingPole(pole))
        {
            return 0f;
        }
        int useModifier = getIntObjVar(pole, "useModifier");
        float bonus = useModifier / 500f;
        return bonus;
    }
    public static obj_id spawnFishingFish(obj_id target, location castLoc) throws InterruptedException
    {
        LOG("fishingLog", "target = " + target);
        if (!isIdValid(target) || castLoc == null)
        {
            return null;
        }
        obj_id inv = utils.getInventoryContainer(target);
        if (!isIdValid(inv))
        {
            return null;
        }
        int volFree = getVolumeFree(inv);
        if (volFree < 1)
        {
            sendSystemMessage(target, new string_id(STF_FISH, "inventory_full"));
            return null;
        }
        dictionary row = getFishData(target, castLoc);
        if (row == null || row.isEmpty())
        {
            createVegetation(target, inv, castLoc);
            return null;
        }
        obj_id fish = obj_id.NULL_ID;
        String fishName = row.getString("generic");
        LOG("fishingLog", "fishName = " + fishName);
        if (static_item.isStaticItem(fishName))
        {
            fish = static_item.createNewItemFunction(fishName, inv);
            LOG("fishingLog", "Static Fish=  " + fish);
        }
        else 
        {
            dictionary templateRow = dataTableGetRow(TBL_TEMPLATE, fishName);
            String templateFish = templateRow.getString("template");
            LOG("fishingLog", "this is the templateFish - non-static -=  " + templateFish);
            if (templateFish == null || templateFish.equals(""))
            {
                createVegetation(target, inv, castLoc);
                return null;
            }
            fish = createObject(templateFish, inv, "");
            LOG("fishingLog", "this is the fish created -  " + fish);
        }
        if (!isIdValid(fish))
        {
            sendSystemMessage(target, new string_id(STF_FISH, "size_matters"));
            return null;
        }
        row = dataTableGetRow(TBL_TEMPLATE, fishName);
        if (row == null)
        {
            LOG("fishingLog", "row is null...wtf");
        }
        LOG("fishingLog", "datatable ROW - " + row);
        setFishData(fish, row, target, castLoc);
        return fish;
    }
    public static dictionary getFishData(obj_id target, location castLoc) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (castLoc == null)
        {
            return null;
        }
        int tableChance = rand(1, 100);
        String fishLootTable = "";
        if (tableChance <= ROLL_FISH_OR_JUNK)
        {
            String planetName = getCurrentSceneName();
            fishLootTable = "datatables/fishing/fish/" + planetName + ".iff";
        }
        else 
        {
            fishLootTable = "datatables/fishing/loot/generic.iff";
        }
        String[] names = dataTableGetStringColumnNoDefaults(fishLootTable, "generic");
        String name = "";
        if (names == null || names.length == 0)
        {
            name = "fish_generic";
            return null;
        }
        int idx = rand(0, names.length - 1);
        name = names[idx];
        if (name == null || name.equals(""))
        {
            name = "fish_generic";
        }
        return dataTableGetRow(fishLootTable, name);
    }
    public static boolean setFishData(obj_id fish, dictionary params, obj_id player, location castLoc) throws InterruptedException
    {
        LOG("fishingLog", "static fish - after passing to SetFishData -  " + fish);
        LOG("fishingLog", "params? -  " + params);
        if (!isIdValid(fish) || !isIdValid(player))
        {
            return false;
        }
        if (params == null || params.isEmpty())
        {
            return false;
        }
        if (castLoc == null)
        {
            castLoc = getLocation(player);
        }
        String name = params.getString(COL_NAME);
        if (name == null || name.equals(""))
        {
            return false;
        }
        string_id sid_name = new string_id("fish_n", name);
        if (getString(sid_name) != null)
        {
            setName(fish, sid_name);
        }
        int timeCaught = getCalendarTime();
        if (timeCaught > 0)
        {
            setObjVar(fish, VAR_FISH_TIME_STAMP, timeCaught);
        }
        String playerName = getPlayerFullName(player);
        if (playerName != null || !playerName.equals(""))
        {
            setObjVar(fish, VAR_FISH_CATCHER, playerName);
        }
        setObjVar(fish, VAR_FISH_NAME, name);
        setObjVar(fish, VAR_FISH_LOCATION, castLoc);
        setObjVar(fish, VAR_FISH_STAMP, getGameTime());
        float lengthMin = params.getFloat(COL_LENGTH_MIN);
        float lengthNorm = params.getFloat(COL_LENGTH_NORM);
        float absoluteLengthMax = params.getFloat(COL_LENGTH_MAX);
        float dLength = lengthNorm - lengthMin;
        float lengthMax = lengthNorm + dLength;
        float length = rand(lengthMin, lengthMax);
        float lengthPercentile = 1.0f;
        if (lengthMin != lengthMax)
        {
            lengthPercentile = (length - lengthMin) / (lengthMax - lengthMin);
            if (lengthPercentile > 0.95f)
            {
                float dPercentile = 0f;
                do
                {
                    float toAdd = rand(0f, dLength);
                    length += toAdd;
                    dPercentile = toAdd / dLength;
                } while (dPercentile > 0.95f && length < absoluteLengthMax);
            }
        }
        if (length > absoluteLengthMax)
        {
            length = absoluteLengthMax;
        }
        setObjVar(fish, VAR_FISH_LENGTH, length);
        int xp = (int)(90.f * (length / lengthNorm));
        utils.setScriptVar(fish, "fish_xp", xp);
        int resourceMin = params.getInt(COL_RESOURCE_MIN);
        int resourceNorm = params.getInt(COL_RESOURCE_NORM);
        int absoluteResourceMax = params.getInt(COL_RESOURCE_MAX);
        int dResource = resourceNorm - resourceMin;
        int resourceMax = resourceNorm + dResource;
        int resourceAmt = rand(resourceMin, resourceMax);
        float resourcePercentile = 1.0f;
        if (resourceMin != resourceMax)
        {
            resourcePercentile = (resourceAmt - resourceMin) / (resourceMax - resourceMin);
            if (resourcePercentile > 0.95f)
            {
                float dPercentile = 0f;
                do
                {
                    int toAdd = rand(0, dResource);
                    resourceAmt += toAdd;
                    dPercentile = toAdd / dResource;
                } while (dPercentile > 0.95f && resourceAmt < absoluteResourceMax);
            }
        }
        if (resourceAmt > absoluteResourceMax)
        {
            resourceAmt = absoluteResourceMax;
        }
        if (resourceAmt > 0)
        {
            String resourceType = params.getString(COL_RESOURCE_TYPE);
            if (resourceType != null && !resourceType.equals(""))
            {
                resource.createRandom(resourceType, resourceAmt, castLoc, fish, player, 1);
            }
        }
        int chumNorm = params.getInt(COL_CHUM_NORM);
        int chumAmt = chumNorm;
        if (resourceMax != resourceMin)
        {
            float percentile = (resourceAmt - resourceMin) / (resourceMax - resourceMin);
            chumAmt = chumNorm + (int)rand(-percentile, percentile);
        }
        if (chumAmt > 0)
        {
            obj_id chum = createObject("object/tangible/fishing/bait/bait_chum.iff", fish, "");
            if (isIdValid(chum))
            {
                setCount(chum, chumAmt);
            }
        }
        int hue1 = params.getInt(COL_HUE1);
        if (hue1 > -1)
        {
            hue.setColor(fish, 1, hue1);
        }
        int hue2 = params.getInt(COL_HUE2);
        if (hue2 > -1)
        {
            hue.setColor(fish, 2, hue2);
        }
        return true;
    }
    public static boolean filetFish(obj_id target, obj_id fish) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(fish))
        {
            return false;
        }
        obj_id[] contents = getContents(fish);
        if (contents == null || contents.length == 0)
        {
            sendSystemMessage(target, new string_id(STF_FISH, "already_fileted"));
            return false;
        }
        obj_id inv = utils.getInventoryContainer(target);
        if (!isIdValid(inv))
        {
            return false;
        }
        int volFree = getVolumeFree(inv);
        if (volFree < contents.length)
        {
            sendSystemMessageProse(target, prose.getPackage(new string_id(STF_FISH, "units_inventory"), contents.length));
            return false;
        }
        int cec = moveContents(fish, inv);
        if (cec == CEC_SUCCESS || cec == CEC_ADD_SELF)
        {
            sendSystemMessage(target, new string_id(STF_FISH, "good_filet"));
        }
        else 
        {
            sendSystemMessage(target, new string_id(STF_FISH, "no_resource"));
        }
        return trophyFish(target, fish);
    }
    public static boolean trophyFish(obj_id target, obj_id fish) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(fish))
        {
            return false;
        }
        return true;
    }
    public static void createVegetation(obj_id target, obj_id inv, location castLoc) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(inv) || castLoc == null)
        {
            return;
        }
        sendSystemMessage(target, new string_id(STF_FISH, "shucks_veggies"));
        resource.createRandom("vegetable_greens", 2, castLoc, inv, target, 1);
    }
    public static obj_id[] spawnFishingLoot(obj_id target, location castLoc) throws InterruptedException
    {
        if (!isIdValid(target) || castLoc == null)
        {
            return null;
        }
        obj_id inv = utils.getInventoryContainer(target);
        if (!isIdValid(inv))
        {
            return null;
        }
        int volFree = getVolumeFree(inv);
        if (volFree < 1)
        {
            sendSystemMessage(target, new string_id(STF_FISH, "toss_trash"));
            return null;
        }
        int lvl = rand(1, 10);
        while (lvl % 10 == 0)
        {
            lvl += rand(1, 10);
        }
        return null;
    }
}
