package script.library;

import script.dictionary;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class ai_lib extends script.base_script
{
    public ai_lib()
    {
    }
    public static final float PLAYER_RUN_SPEED = 8.0f;
    public static final float AI_MAX_MOVEMENT_SPEED = PLAYER_RUN_SPEED;
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final String AGGRO_VOLUME_NAME = "aggroTriggerVolume";
    public static final String MOOD_CALM = "calm";
    public static final String MOOD_NERVOUS = "nervous";
    public static final String MOOD_THREATEN = "threaten";
    public static final int BEHAVIOR_WANDER = 0;
    public static final int BEHAVIOR_SENTINEL = 1;
    public static final int BEHAVIOR_LOITER = 2;
    public static final int BEHAVIOR_STOP = 3;
    //public static final String DIF_VERY_EASY = "veryEasy";
    //public static final String DIF_EASY = "easy";
    //public static final String DIF_MEDIUM = "medium";
    //public static final String DIF_HARD = "hard";
    //public static final String DIF_VERY_HARD = "veryHard";
    public static final String ALLY_LIST = "allyList";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String CREATURE_NAME_FILE = "mob/creature_names";
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public static final string_id SMUGGLER_SCAN_SUCCESS = new string_id("base_player", "smuggler_scan_success");
    public static final string_id SMUGGLER_SCAN_FAIL = new string_id("base_player", "smuggler_scan_fail");
    public static final float DEFAULT_FOLLOW_MIN = 4.0f;
    public static final float DEFAULT_FOLLOW_MAX = 8.0f;
    public static final String OBJVAR_ATTACKABLE_OVERRIDE = "ai_combat.attackable";
    public static final String SCRIPTVAR_CACHED_PATROL_PATH = "ai.patrolPath";
    public static final String SCRIPTVAR_CACHED_PATROL_NAMED_PATH = "ai.patrolNamedPath";
    public static final String SCRIPTVAR_CACHED_PATROL_TYPE = "ai.patrolType";
    public static final int PATROL_FLAG_RANDOM = 0x0001;
    public static final int PATROL_FLAG_FLIP = 0x0002;
    public static final int PATROL_FLAG_REPEAT = 0x0004;
    public static final int FORMATION_COLUMN = 0;
    public static final int FORMATION_WEDGE = 1;
    public static final int FORMATION_LINE = 2;
    public static final int FORMATION_BOX = 3;
    //public static final int DIFFICULTY_NORMAL = 0;
    //public static final int DIFFICULTY_ELITE = 1;
    public static final int DIFFICULTY_BOSS = 2;
    public static final String SHARED_HEALTH_LIST = "shared_health_list";
    public static boolean isWithinLeash(obj_id ai) throws InterruptedException
    {
        final float distanceToHome = getDistance(getLocation(ai), aiGetLeashAnchorLocation(ai));
        final float leashRadius = aiGetLeashRadius();
        return (distanceToHome <= leashRadius);
    }
    public static void resetAi() throws InterruptedException
    {
        final obj_id self = getSelf();
        LOGC(aiLoggingEnabled(self), "debug_ai", ("ai_lib::resetAi() --- BEGIN RESET AI --- self(" + self + ") getName(" + getName(self) + ")"));
        setMovementWalk(self);
        movement.refresh(self);
        resetCombatTriggerVolumes();
        messageTo(self, "resumeDefaultCalmBehavior", null, 1, false);
        LOGC(aiLoggingEnabled(self), "debug_ai", ("ai_lib::resetAi() --- END RESET AI --- self(" + self + ") getName(" + getName(self) + ")"));
    }
    public static void resetCombatTriggerVolumes() throws InterruptedException
    {
        final obj_id self = getSelf();
        LOGC(aiLoggingEnabled(self), "debug_ai", ("ai_lib::resetCombatTriggerVolumes() self(" + self + ") getName(" + getName(self) + ")"));
        boolean needsCombatTriggerVolumes = true;
        if (isInvulnerable(self))
        {
            needsCombatTriggerVolumes = false;
        }
        else if (isAnimal(self) && !aiIsAggressive(self))
        {
            needsCombatTriggerVolumes = false;
        }
        else if (isVehicleMine(self))
        {
            needsCombatTriggerVolumes = false;
        }
        else if (beast_lib.isBeast(self))
        {
            needsCombatTriggerVolumes = false;
        }
        if (needsCombatTriggerVolumes)
        {
            // Create ALERT trigger volume
            createTriggerVolume(ai_lib.ALERT_VOLUME_NAME, 64.0f, true);
            // Create AGGRO trigger volume, AI needs an aggro trigger volume because they could be factionally aggressive towards something
            createTriggerVolume(ai_lib.AGGRO_VOLUME_NAME, aiGetAggroRadius(self), false);
        }
    }
    public static void clearCombatData() throws InterruptedException
    {
        final obj_id self = getSelf();
        clearHateList(self);
        queueClear(self);
        pclib.clearCombatData(self);
        movement.removeAllModifiers(self);
        buff.removeAllBuffs(self);
        dot.removeAllDots(self);
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
    }
    public static boolean isInCombat(obj_id ai) throws InterruptedException
    {
        return (getState(ai, STATE_COMBAT) == 1);
    }
    public static void setAttackable(obj_id npc, boolean val) throws InterruptedException
    {
        if (val)
        {
            setObjVar(npc, OBJVAR_ATTACKABLE_OVERRIDE, 1);
        }
        else if (hasObjVar(npc, OBJVAR_ATTACKABLE_OVERRIDE))
        {
            removeObjVar(npc, OBJVAR_ATTACKABLE_OVERRIDE);
        }
    }
    public static boolean isAttackable(obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, OBJVAR_ATTACKABLE_OVERRIDE);
    }
    public static void setMood(obj_id npc, String mood) throws InterruptedException
    {
        if (getSelf() != npc)
        {
            dictionary parms = new dictionary();
            parms.put("animMood", mood);
            messageTo(npc, "handleAnimationAction", parms, 1, false);
            return;
        }
        if (ai_lib.isAiDead(npc))
        {
            return;
        }
        if (mood.equals(MOOD_CALM))
        {
            removeObjVar(npc, "ai.mood");
        }
        else 
        {
            setObjVar(npc, "ai.mood", mood);
        }
        if (mood.equals(MOOD_THREATEN))
        {
            chat.setAngryMood(npc);
            ai_lib.barkString(npc, "threat");
        }
        else if (mood.equals(MOOD_NERVOUS))
        {
            chat.setBadMood(npc);
            ai_lib.barkString(npc, "alert");
        }
        if (mood.equals(MOOD_CALM) && hasObjVar(npc, "ai.defaultCalmMood"))
        {
            mood = getStringObjVar(npc, "ai.defaultCalmMood");
        }
        setAnimationMood(npc, mood);
    }
    public static void doAction(obj_id npc, String anim) throws InterruptedException
    {
        if (getSelf() != npc)
        {
            dictionary parms = new dictionary();
            parms.put("anim", anim);
            messageTo(npc, "handleAnimationAction", parms, 1, false);
            return;
        }
        if (ai_lib.isAiDead(npc))
        {
            return;
        }
        int npcPosture = getPosture(npc);
        if (npcPosture != POSTURE_UPRIGHT)
        {
            if (npcPosture != POSTURE_KNOCKED_DOWN)
            {
                posture.stand(npc);
            }
            removeObjVar(npc, "ai.combat.moveMode");
        }
        else 
        {
            doAnimationAction(npc, anim);
        }
    }
    public static void setPatrolPath(obj_id npc, String[] waypoints) throws InterruptedException
    {
        setPatrolPath(npc, waypoints, 0);
    }
    public static void setPatrolPath(obj_id npc, String[] waypoints, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && waypoints != null)
        {
            location[] patrolLocs = parseWaypoints(npc, waypoints);
            if (patrolLocs != null)
            {
                setPatrolPath(npc, patrolLocs, startPoint);
            }
        }
    }
    public static void setPatrolOncePath(obj_id npc, String[] waypoints) throws InterruptedException
    {
        setPatrolOncePath(npc, waypoints, 0);
    }
    public static void setPatrolOncePath(obj_id npc, String[] waypoints, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && waypoints != null)
        {
            location[] patrolLocs = parseWaypoints(npc, waypoints);
            if (patrolLocs != null)
            {
                setPatrolOncePath(npc, patrolLocs, startPoint);
            }
        }
    }
    public static void setPatrolRandomPath(obj_id npc, String[] waypoints) throws InterruptedException
    {
        if (isIdValid(npc) && waypoints != null)
        {
            location[] patrolLocs = parseWaypoints(npc, waypoints);
            if (patrolLocs != null)
            {
                setPatrolRandomPath(npc, patrolLocs);
            }
        }
    }
    public static void setPatrolRandomOncePath(obj_id npc, String[] waypoints) throws InterruptedException
    {
        if (isIdValid(npc) && waypoints != null)
        {
            location[] patrolLocs = parseWaypoints(npc, waypoints);
            if (patrolLocs != null)
            {
                setPatrolRandomOncePath(npc, patrolLocs);
            }
        }
    }
    public static void setPatrolFlipPath(obj_id npc, String[] waypoints) throws InterruptedException
    {
        setPatrolFlipPath(npc, waypoints, 0);
    }
    public static void setPatrolFlipPath(obj_id npc, String[] waypoints, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && waypoints != null)
        {
            location[] patrolLocs = parseWaypoints(npc, waypoints);
            if (patrolLocs != null)
            {
                setPatrolFlipPath(npc, patrolLocs, startPoint);
            }
        }
    }
    public static void setPatrolFlipOncePath(obj_id npc, String[] waypoints) throws InterruptedException
    {
        setPatrolFlipOncePath(npc, waypoints, 0);
    }
    public static void setPatrolFlipOncePath(obj_id npc, String[] waypoints, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && waypoints != null)
        {
            location[] patrolLocs = parseWaypoints(npc, waypoints);
            if (patrolLocs != null)
            {
                setPatrolFlipOncePath(npc, patrolLocs, startPoint);
            }
        }
    }
    public static void setPatrolPath(obj_id npc, location[] patrolLoc) throws InterruptedException
    {
        setPatrolPath(npc, patrolLoc, 0);
    }
    public static void setPatrolPath(obj_id npc, location[] patrolLoc, int startPoint) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(npc), "debug_ai", ("ai_lib::setPatrolPath() self(" + npc + ") getName(" + getName(npc) + ") patrolLength(" + patrolLoc.length + ")"));
        if (isIdValid(npc))
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_REPEAT);
            patrol(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolOncePath(obj_id npc, location[] patrolLoc) throws InterruptedException
    {
        setPatrolOncePath(npc, patrolLoc, 0);
    }
    public static void setPatrolOncePath(obj_id npc, location[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, 0);
            patrolOnce(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolRandomPath(obj_id npc, location[] patrolLoc) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_RANDOM | PATROL_FLAG_REPEAT);
            patrolRandom(npc, patrolLoc);
        }
    }
    public static void setPatrolRandomOncePath(obj_id npc, location[] patrolLoc) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_RANDOM);
            patrolRandomOnce(npc, patrolLoc);
        }
    }
    public static void setPatrolFlipPath(obj_id npc, location[] patrolLoc) throws InterruptedException
    {
        setPatrolFlipPath(npc, patrolLoc, 0);
    }
    public static void setPatrolFlipPath(obj_id npc, location[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_FLIP | PATROL_FLAG_REPEAT);
            patrolFlip(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolFlipOncePath(obj_id npc, location[] patrolLoc) throws InterruptedException
    {
        setPatrolFlipOncePath(npc, patrolLoc, 0);
    }
    public static void setPatrolFlipOncePath(obj_id npc, location[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_FLIP);
            patrolFlipOnce(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolNamedPath(obj_id npc, String[] patrolLoc) throws InterruptedException
    {
        setPatrolNamedPath(npc, patrolLoc, 0);
    }
    public static void setPatrolNamedPath(obj_id npc, String[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_REPEAT);
            patrol(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolOnceNamedPath(obj_id npc, String[] patrolLoc) throws InterruptedException
    {
        setPatrolOnceNamedPath(npc, patrolLoc, 0);
    }
    public static void setPatrolOnceNamedPath(obj_id npc, String[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, 0);
            patrolOnce(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolRandomNamedPath(obj_id npc, String[] patrolLoc) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            if (!exists(npc))
            {
                LOG("debug_ai", "WARNING: ai_lib.setPatrolRandomNamedPath passed npc id " + npc + " that doesn't exist");
                return;
            }
            else if (!isGameObjectTypeOf(npc, GOT_creature))
            {
                LOG("debug_ai", "WARNING: ai_lib.setPatrolRandomNamedPath passed npc id " + npc + " that is not a creature. " + "Object template = " + getTemplateName(npc));
            }
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_RANDOM | PATROL_FLAG_REPEAT);
            patrolRandom(npc, patrolLoc);
        }
    }
    public static void setPatrolRandomOnceNamedPath(obj_id npc, String[] patrolLoc) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_RANDOM);
            patrolRandomOnce(npc, patrolLoc);
        }
    }
    public static void setPatrolFlipNamedPath(obj_id npc, String[] patrolLoc) throws InterruptedException
    {
        setPatrolFlipNamedPath(npc, patrolLoc, 0);
    }
    public static void setPatrolFlipNamedPath(obj_id npc, String[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_FLIP | PATROL_FLAG_REPEAT);
            patrolFlip(npc, patrolLoc, startPoint);
        }
    }
    public static void setPatrolFlipOnceNamedPath(obj_id npc, String[] patrolLoc) throws InterruptedException
    {
        setPatrolFlipOnceNamedPath(npc, patrolLoc, 0);
    }
    public static void setPatrolFlipOnceNamedPath(obj_id npc, String[] patrolLoc, int startPoint) throws InterruptedException
    {
        if (isIdValid(npc) && patrolLoc != null)
        {
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH, patrolLoc);
            utils.setScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE, PATROL_FLAG_FLIP);
            patrolFlipOnce(npc, patrolLoc, startPoint);
        }
    }
    public static void clearPatrolPath(obj_id npc) throws InterruptedException
    {
        if (isIdValid(npc))
        {
            utils.removeScriptVar(npc, SCRIPTVAR_CACHED_PATROL_PATH);
            utils.removeScriptVar(npc, SCRIPTVAR_CACHED_PATROL_NAMED_PATH);
            utils.removeScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE);
        }
    }
    public static void resumePatrol(obj_id npc) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(npc), "debug_ai", ("ai_lib::resumePatrol() self(" + npc + ") getName(" + getName(npc) + ")"));
        if (isIdValid(npc) && aiGetMovementState(npc) != MOVEMENT_PATROL && hasSuspendedMovement(npc))
        {
            resumeMovement(npc);
        }
    }
    public static boolean canPatrol(obj_id npc) throws InterruptedException {
        return isIdValid(npc) && utils.hasScriptVar(npc, SCRIPTVAR_CACHED_PATROL_TYPE);
    }
    public static location[] parseWaypoints(obj_id npc, String[] waypoints) throws InterruptedException
    {
        if (!isIdValid(npc) || waypoints == null)
        {
            return null;
        }
        location baseLoc = aiGetHomeLocation(npc);
        if ((baseLoc.x == 0.0f) && (baseLoc.z == 0.0f))
        {
            baseLoc = getLocation(npc);
            if ((baseLoc.x == 0.0f) && (baseLoc.z == 0.0f))
            {
                debugServerConsoleMsg(npc, "*  ERROR: TRYING TO GET LOCATION ON AN NPC NOT INSTANTIATED!  *");
                return null;
            }
        }
        location[] patrolLocs = new location[waypoints.length];
        location patrolLoc = new location(baseLoc);
        float[] coords;
        for (int i = 0; i < waypoints.length; i++)
        {
            coords = parseWaypoints(waypoints[i]);
            if (coords != null && coords.length == 2)
            {
                patrolLoc.x = baseLoc.x + coords[0];
                patrolLoc.z = baseLoc.z + coords[1];
            }
            else 
            {
                debugServerConsoleMsg(npc, "*  ERROR: COULD NOT PARSE WAYPOINT STRING <" + waypoints[i] + "> *");
            }
            patrolLocs[i] = patrolLoc;
        }
        return patrolLocs;
    }
    public static float[] parseWaypoints(String text) throws InterruptedException
    {
        float[] returnCoords = new float[2];
        returnCoords[0] = Float.parseFloat(text.split(",")[0]);
        returnCoords[1] = Float.parseFloat(text.split(",")[1]);
        return returnCoords;
    }
    public static void setIgnoreCombat(obj_id npc) throws InterruptedException
    {
        setWantSawAttackTriggers(npc, false);
    }
    public static void barkString(obj_id npc, String text) throws InterruptedException
    {
        if (!text.equals("death"))
        {
            if (ai_lib.isAiDead(npc))
            {
                return;
            }
        }
        if (!hasObjVar(npc, "ai.diction"))
        {
            return;
        }
        if (utils.hasScriptVar(npc, "ai.recentlyBarked"))
        {
            if (getGameTime() < utils.getIntScriptVar(npc, "ai.recentlyBarked"))
            {
                return;
            }
        }
        if (!ai_lib.isNpc(npc) && !pet_lib.isDroidPet(npc))
        {
            removeObjVar(npc, "ai.diction");
            return;
        }
        if (getSpecies(npc) == SPECIES_WOOKIEE)
        {
            removeObjVar(npc, "ai.diction");
            return;
        }
        utils.setScriptVar(npc, "ai.recentlyBarked", getGameTime() + rand(20, 45));
        if (locations.isInCity(getLocation(npc)))
        {
            if (rand(1, 100) != 1)
            {
                return;
            }
        }
        else 
        {
            if (rand(1, 10) != 1)
            {
                return;
            }
        }
        string_id speakString = new string_id("npc_reaction/" + getStringObjVar(npc, "ai.diction"), text + "_" + rand(1, 16));
        if (getString(speakString) == null)
        {
            debugServerConsoleMsg(npc, "WARNING: Npc_reaction " + speakString + " - does it exist?");
            return;
        }
        chat.chat(npc, speakString);
    }
    public static boolean isAnimal(obj_id npc) throws InterruptedException
    {
        return isMonster(npc);
    }
    public static boolean isMonster(obj_id npc) throws InterruptedException
    {
        int myNiche = aiGetNiche(npc);
        return myNiche == NICHE_MONSTER || myNiche == NICHE_HERBIVORE || myNiche == NICHE_CARNIVORE || myNiche == NICHE_PREDATOR;
    }
    public static boolean isDroid(obj_id npc) throws InterruptedException
    {
        return aiGetNiche(npc) == NICHE_DROID;
    }
    public static boolean isPredator(obj_id npc) throws InterruptedException
    {
        return aiGetNiche(npc) == NICHE_PREDATOR;
    }
    public static boolean isCarnivore(obj_id npc) throws InterruptedException
    {
        return aiGetNiche(npc) == NICHE_CARNIVORE;
    }
    public static boolean isHerbivore(obj_id npc) throws InterruptedException
    {
        return aiGetNiche(npc) == NICHE_HERBIVORE;
    }
    public static boolean isVehicle(obj_id npc) throws InterruptedException
    {
        return aiGetNiche(npc) == NICHE_VEHICLE;
    }
    public static boolean isAndroid(obj_id npc) throws InterruptedException
    {
        return aiGetNiche(npc) == NICHE_ANDROID;
    }
    public static boolean isHumanoid(obj_id target) throws InterruptedException
    {
        int niche = aiGetNiche(target);
        return isPlayer(target) || niche == NICHE_NPC || niche == NICHE_ANDROID;
    }
    public static boolean isNpc(obj_id npc) throws InterruptedException
    {
        return (aiGetNiche(npc) == NICHE_NPC);
    }
    public static boolean isTurret(obj_id npc) throws InterruptedException
    {
        return isGameObjectTypeOf(getGameObjectType(npc), GOT_installation_turret);
    }
    public static void setIgnoreCombat(obj_id npc, boolean b) throws InterruptedException
    {
        setWantSawAttackTriggers(npc, b);
    }
    public static void setDefaultCalmBehavior(obj_id npc, int behavior) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(npc), "debug_ai", "ai_lib::setDefaultCalmBehavior() self(" + npc + getName(npc) + ") behavior(" + behavior + ")");
        if (!isIdValid(npc))
        {
            return;
        }
        if (behavior == BEHAVIOR_WANDER)
        {
            behavior = BEHAVIOR_LOITER;
        }
        setObjVar(npc, "ai.defaultCalmBehavior", behavior);
        messageTo(npc, "handleSetDefaultBehavior", null, 1, false);
    }
    public static void greet(obj_id ai, obj_id player) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(ai), "debug_ai", "ai_lib::greet() ai(" + ai + ":" + getName(ai) + ") player(" + player + ":" + getName(player) + ")");
        if (isInCombat(ai))
        {
            return;
        }
        final int reaction = factions.getFactionReaction(ai, player);
        if (reaction < factions.REACTION_NEUTRAL)
        {
            chat.setAngryMood(ai);
            ai_lib.barkString(ai, "hi_mean");
        }
        else if (reaction > factions.REACTION_NEUTRAL)
        {
            chat.setGoodMood(ai);
            ai_lib.barkString(ai, "hi_nice");
        }
        else 
        {
            chat.setNeutralMood(ai);
            ai_lib.barkString(ai, "hi_mid");
        }
    }
    public static void dismiss(obj_id ai, obj_id player) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(ai), "debug_ai", "ai_lib::dismiss() ai(" + ai + ":" + getName(ai) + ") player(" + player + ":" + getName(player) + ")");
        if (isInCombat(ai))
        {
            return;
        }
        final int reaction = factions.getFactionReaction(ai, player);
        if (reaction < factions.REACTION_NEUTRAL)
        {
            chat.setBadMood(ai);
            ai_lib.barkString(ai, "bye_mean");
        }
        else if (reaction > factions.REACTION_NEUTRAL)
        {
            chat.setGoodMood(ai);
            ai_lib.barkString(ai, "bye_nice");
        }
        else 
        {
            chat.setNeutralMood(ai);
            ai_lib.barkString(ai, "bye_mid");
        }
    }
    public static boolean aiIsDead(obj_id npc) throws InterruptedException
    {
        return isAiDead(npc);
    }
    public static boolean isAiDead(obj_id npc) throws InterruptedException {
        if (!isMob(npc)) {
            return (isDisabled(npc));
        }
        return isIncapacitated(npc) || isDead(npc);
    }
    public static void aiFollow(obj_id npc, obj_id target) throws InterruptedException
    {
        aiFollow(npc, target, DEFAULT_FOLLOW_MIN, DEFAULT_FOLLOW_MAX);
    }
    public static void aiFollow(obj_id npc, obj_id target, float min_dist, float max_dist) throws InterruptedException
    {
        if (target == npc)
        {
            debugServerConsoleMsg(npc, npc + " was told to follow itself");
            return;
        }
        if (getPosture(npc) != POSTURE_UPRIGHT)
        {
            posture.stand(npc);
        }
        setObjVar(npc, "ai.persistantFollowing.target", target);
        if (min_dist < 1.0f)
        {
            min_dist = 1.0f;
        }
        if (max_dist < 1.0f)
        {
            max_dist = 1.0f;
        }
        if (max_dist <= min_dist)
        {
            max_dist = min_dist + 1.0f;
        }
        setObjVar(npc, "ai.persistantFollowing.min_dist", min_dist);
        setObjVar(npc, "ai.persistantFollowing.max_dist", max_dist);
        follow(npc, target, min_dist, max_dist);
    }
    public static boolean isFollowing(obj_id npc) throws InterruptedException {
        return isIdValid(npc) && hasObjVar(npc, "ai.persistantFollowing");
    }
    public static obj_id getFollowTarget(obj_id npc) throws InterruptedException
    {
        if (!isIdValid(npc))
        {
            return null;
        }
        return getObjIdObjVar(npc, "ai.persistantFollowing.target");
    }
    public static void resumeFollow(obj_id npc) throws InterruptedException
    {
        if (!isIdValid(npc))
        {
            return;
        }
        obj_id target = getObjIdObjVar(npc, "ai.persistantFollowing.target");
        if (isIdValid(target))
        {
            float min_dist = DEFAULT_FOLLOW_MIN;
            if (hasObjVar(npc, "ai.persistantFollowing.min_dist"))
            {
                min_dist = getFloatObjVar(npc, "ai.persistantFollowing.min_dist");
            }
            float max_dist = DEFAULT_FOLLOW_MAX;
            if (hasObjVar(npc, "ai.persistantFollowing.max_dist"))
            {
                max_dist = getFloatObjVar(npc, "ai.persistantFollowing.max_dist");
            }
            aiFollow(npc, target, min_dist, max_dist);
            return;
        }
        aiStopFollowing(npc);
    }
    public static void aiStopFollowing(obj_id npc) throws InterruptedException
    {
        stop(npc);
        removeObjVar(npc, "ai.persistantFollowing");
        if (getBehavior(npc) <= BEHAVIOR_CALM)
        {
            messageTo(npc, "resumeDefaultCalmBehavior", null, 5, false);
        }
    }
    public static String getMood(obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(npc, "ai.mood"))
        {
            return ai_lib.MOOD_CALM;
        }
        else 
        {
            return getStringObjVar(npc, "ai.mood");
        }
    }
    public static void pathNear(obj_id npc, location dest, float range) throws InterruptedException
    {
        location startLoc = getLocation(npc);
        if (dest.x < startLoc.x)
        {
            dest.x += range;
        }
        else 
        {
            dest.x -= range;
        }
        if (dest.z < startLoc.z)
        {
            dest.z += range;
        }
        else 
        {
            dest.z -= range;
        }
        pathTo(npc, dest);
    }
    public static void followInFormation(obj_id npc, obj_id leader, int formationType, int position) throws InterruptedException
    {
        if (npc == leader)
        {
            debugServerConsoleMsg(npc, npc + " was told to followInFormation itself");
            return;
        }
        setObjVar(npc, "ai.inFormation", leader);
        setObjVar(npc, "ai.formationType", formationType);
        setObjVar(npc, "ai.formationPosition", position);
        if (!isInCombat(npc))
        {
            stop(npc);
            resumeFormationFollowing(npc);
        }
    }
    public static void resumeFormationFollowing(obj_id npc) throws InterruptedException
    {
        if (isInCombat(npc))
        {
            return;
        }
        obj_id leader = getObjIdObjVar(npc, "ai.inFormation");
        if (!exists(leader) || !isInWorld(leader) || isAiDead(leader))
        {
            removeObjVar(npc, "ai.inFormation");
            return;
        }
        int position = getIntObjVar(npc, "ai.formationPosition");
        switch (getIntObjVar(npc, "ai.formationType"))
        {
            case FORMATION_WEDGE:
            followInWedgeFormation(npc, leader, position);
            break;
            case FORMATION_LINE:
            followInLineFormation(npc, leader, position);
            break;
            case FORMATION_BOX:
            followInBoxFormation(npc, leader, position);
            break;
            default:
            followInColumnFormation(npc, leader, position);
            break;
        }
    }
    public static void followInWedgeFormation(obj_id npc, obj_id leader, int position) throws InterruptedException
    {
        if (npc == leader)
        {
            debugServerConsoleMsg(npc, npc + " was told to followInWedgeFormation itself");
            return;
        }
        position = Math.max(1, position);
        location offset = new location();
        final float spacing = getObjectCollisionRadius(leader) * 2.25f;
        final float x = (spacing * (float)Math.ceil(position / 2.0f));
        offset.x = (position % 2 == 0) ? -x : x;
        offset.z = -x;
        follow(npc, leader, offset);
    }
    public static void followInBoxFormation(obj_id npc, obj_id leader, int position) throws InterruptedException
    {
        if (npc == leader)
        {
            debugServerConsoleMsg(npc, npc + " was told to followInLineFormation itself");
            return;
        }
        location offset = new location(0, 0, 0, "");
        if (position < 3)
        {
            offset.x += (position * 3);
        }
        else if (position == 4)
        {
            offset.x = 6;
        }
        else if (position != 3)
        {
            offset.x += ((position - 5) * 3);
        }
        if (position == 3 || position == 4)
        {
            offset.z += 3;
        }
        else if (position > 4)
        {
            offset.z += 6;
        }
        if (position < 1 || position > 7)
        {
            follow(npc, leader, rand(15, 20), rand(10, 18));
        }
        else 
        {
            follow(npc, leader, offset);
        }
    }
    public static void followInLineFormation(obj_id npc, obj_id leader, int position) throws InterruptedException
    {
        if (npc == leader)
        {
            debugServerConsoleMsg(npc, npc + " was told to followInLineFormation itself");
            return;
        }
        position = Math.max(1, position);
        location offset = new location();
        final float spacing = getObjectCollisionRadius(leader) * 2.25f;
        final float x = (spacing * (float)Math.ceil(position / 2.0f));
        offset.x = (position % 2 == 0) ? -x : x;
        follow(npc, leader, offset);
    }
    public static void followInColumnFormation(obj_id npc, obj_id leader, int position) throws InterruptedException
    {
        if (npc == leader)
        {
            debugServerConsoleMsg(npc, npc + " was told to followInColumnFormation itself");
            return;
        }
        position = Math.max(1, position);
        location offset = new location();
        final float spacing = getObjectCollisionRadius(leader) * 2.25f;
        offset.x = (position % 2 == 0) ? 0.0f : spacing;
        offset.z = -(spacing * (float)Math.ceil((position - 1) / 2.0f));
        follow(npc, leader, offset);
    }
    public static void pathAwayFrom(obj_id npc, obj_id target) throws InterruptedException
    {
        if (npc == target || !isIdValid(npc) || !isIdValid(target))
        {
            return;
        }
        if (hasObjVar(npc, "ai.pathingAwayFrom"))
        {
            obj_id oldTarget = getObjIdObjVar(npc, "ai.pathingAwayFrom");
            if (oldTarget == target)
            {
                return;
            }
            if (getDistance(npc, target) > getDistance(npc, oldTarget))
            {
                return;
            }
        }
        location myLoc = getLocation(npc);
        location targetLoc = getLocation(target);
        if(isValidLocation(myLoc) && isValidLocation(targetLoc)) {
            if (myLoc.x < targetLoc.x) {
                myLoc.x -= rand(20.0f, 40.0f);
            } else {
                myLoc.x += rand(20.0f, 40.0f);
            }
            if (myLoc.z < targetLoc.z) {
                myLoc.z -= rand(20.0f, 40.0f);
            } else {
                myLoc.z += rand(20.0f, 40.0f);
            }
            setObjVar(npc, "ai.pathingAwayFrom", target);
            pathTo(npc, myLoc);
        }
        else{
            LOG("debug_ai","Unable to get a valid location for myLoc (" + myLoc + ") or targetLoc (" + targetLoc + ") to make vendor path away.");
        }
        messageTo(npc, "handleClearPathingFlag", null, 30.0f, isObjectPersisted(npc));
    }
    public static void setLoiterRanges(obj_id npc, float minRange, float maxRange) throws InterruptedException
    {
        setObjVar(npc, "ai.loiterMinRange", minRange);
        setObjVar(npc, "ai.loiterMaxRange", maxRange);
    }
    public static int aiGetSpecies(obj_id npc) throws InterruptedException
    {
        return getSpecies(npc);
    }
    public static void setCustomIdleAnimation(obj_id npc, String animationString) throws InterruptedException
    {
        setDefaultCalmMood(npc, animationString);
    }
    public static void setDefaultCalmMood(obj_id npc, String mood) throws InterruptedException
    {
        setObjVar(npc, "ai.defaultCalmMood", mood);
        if (getBehavior(npc) <= BEHAVIOR_CALM)
        {
            setMood(npc, MOOD_CALM);
        }
    }
    public static int getDefaultCalmBehavior(obj_id ai) throws InterruptedException
    {
        int behavior = BEHAVIOR_LOITER;
        if (hasObjVar(ai, "ai.defaultCalmBehavior"))
        {
            behavior = getIntObjVar(ai, "ai.defaultCalmBehavior");
        }
        return behavior;
    }
    public static void aiPathTo(obj_id npc, location pathLoc) throws InterruptedException
    {
        if ((pathLoc.x == 0.0f) && (pathLoc.z == 0.0f))
        {
            return;
        }
        setObjVar(npc, "ai.persistentPathing", pathLoc);
        stop(npc);
        pathTo(npc, pathLoc);
    }
    public static int aiGetNiche(obj_id npc) throws InterruptedException
    {
        if (isPlayer(npc))
        {
            return NICHE_PC;
        }
        String creatureName = getCreatureName(npc);
        if (creatureName != null)
        {
            return (dataTableGetInt(CREATURE_TABLE, creatureName, "niche"));
        }
        else 
        {
            return NICHE_NONE;
        }
    }
    public static int aiGetNiche(String creatureName) throws InterruptedException
    {
        if (creatureName == null || creatureName.equals(""))
        {
            return NICHE_NONE;
        }
        return dataTableGetInt(CREATURE_TABLE, creatureName, "niche");
    }
    public static String getSocialGroup(obj_id npc) throws InterruptedException
    {
        String creatureName = getCreatureName(npc);
        if (creatureName != null || !beast_lib.isBeast(npc))
        {
            return dataTableGetString(CREATURE_TABLE, creatureName, "socialGroup");
        }
        return null;
    }
    public static boolean isSameSocialGroup(obj_id npc, obj_id target) throws InterruptedException
    {
        boolean result = false;
        if (!pet_lib.isPet(npc) && !pet_lib.isPet(target) && !beast_lib.isBeast(npc) && !beast_lib.isBeast(target))
        {
            if (getCreatureName(npc) != null && getCreatureName(target) != null)
            {
                String mySocialGroup = getSocialGroup(npc);
                String yourSocialGroup = getSocialGroup(target);
                if ((mySocialGroup != null) && (yourSocialGroup != null) && mySocialGroup.equals(yourSocialGroup))
                {
                    result = true;
                }
            }
            else if ((factions.getFaction(npc) != null) && (factions.getFaction(target) != null))
            {
                String mySocialGroup = factions.getFaction(npc);
                String yourSocialGroup = factions.getFaction(target);
                if ((mySocialGroup != null) && (yourSocialGroup != null) && mySocialGroup.equals(yourSocialGroup))
                {
                    result = true;
                }
            }
        }
        return result;
    }
    public static boolean isHerdingCreature(obj_id npc) throws InterruptedException {
        if (isNpc(npc) || isPlayer(npc) || isAndroid(npc)) {
            return false;
        }
        String creatureName = getCreatureName(npc);
        return creatureName != null && (dataTableGetInt(CREATURE_TABLE, creatureName, "herd") == 1);
    }
    public static boolean isStalkingCreature(obj_id ai) throws InterruptedException {
        if (!(isPlayer(ai) || aiGetNiche(ai) == NICHE_NPC || isAndroid(ai)))
            if (!hasScript(ai, "ai.pet_advance"))
                if (aiIsStalker(ai))
                    return true;
        return false;
    }
    public static boolean isAggroToward(obj_id npc, obj_id threat) throws InterruptedException
    {
        if (hasScript(npc, "ai.pet_advance"))
        {
            return false;
        }
        if (pvpIsEnemy(npc, threat))
        {
            return true;
        }
        if (ai_lib.isSameSocialGroup(npc, threat))
        {
            return false;
        }
        int reaction = factions.getFactionReaction(npc, threat);
        if (reaction == factions.REACTION_NEGATIVE)
        {
            return true;
        }
        if (!ai_lib.isMonster(npc))
        {
            if (ai_lib.isAggro(npc))
            {
                if (reaction <= factions.REACTION_NEUTRAL)
                {
                    return true;
                }
            }
            return false;
        }
        if (isPlayer(threat) && ai_lib.isAggro(npc))
        {
            return true;
        }
        if (ai_lib.aiGetNiche(npc) == NICHE_CARNIVORE && ai_lib.aiGetNiche(threat) == NICHE_HERBIVORE) {
            return true;
        }
        return false;
    }
    public static boolean isAggro(obj_id ai) throws InterruptedException
    {
        if (isPlayer(ai))
        {
            return false;
        }
        if (hasScript(ai, "ai.pet_advance"))
        {
            return false;
        }
        if (hasScript(ai, "systems.missions.dynamic.mission_bounty_target"))
        {
            return false;
        }
        return aiIsAggressive(ai);
    }
    public static boolean isHealingNpc(obj_id npc) throws InterruptedException {
        if (isPlayer(npc)) {
            return false;
        }
        int niche = aiGetNiche(npc);
        if (niche != NICHE_NPC && niche != NICHE_ANDROID) {
            return false;
        }
        String creatureName = getCreatureName(npc);
        return creatureName != null && (dataTableGetInt(CREATURE_TABLE, creatureName, "healer") != 0);
    }
    public static boolean incapacitateMob(obj_id target) throws InterruptedException
    {
        if (target == null || !isMob(target))
        {
            return false;
        }
        return setHealth(target, -50) && setAction(target, -50) && setMind(target, -50);
    }
    public static boolean isHumanSkeleton(obj_id npc) throws InterruptedException {
        if (isPlayer(npc)) {
            return true;
        }
        int speciesNum = ai_lib.aiGetSpecies(npc);
        return speciesNum != -1 && dataTableGetString("datatables/ai/species.iff", speciesNum, "Skeleton").equals("human");
    }
    public static String getSkeleton(int speciesNum) throws InterruptedException
    {
        if (speciesNum == -1)
        {
            return null;
        }
        return dataTableGetString("datatables/ai/species.iff", speciesNum, "Skeleton");
    }
    public static String getSkeleton(obj_id npc) throws InterruptedException
    {
        int speciesNum = ai_lib.aiGetSpecies(npc);
        LOG("mount", "speciesnum is " + speciesNum);
        if (speciesNum == -1)
        {
            return null;
        }
        String skeleton = dataTableGetString("datatables/ai/species.iff", speciesNum, "Skeleton");
        LOG("mount", "skeleton is " + skeleton);
        return skeleton;
    }
    public static void doThreatenAnimation(obj_id npc) throws InterruptedException
    {
        if (ai_lib.aiGetNiche(npc) != NICHE_NPC || !isAndroid(npc))
        {
            doAction(npc, ACTION_THREATEN);
            return;
        }
        String anim = ai_lib.ACTION_THREATEN;
        switch (rand(0, 13))
        {
            case 0:
                break;
            case 1:
                anim = "alert";
                break;
            case 2:
                anim = "angry";
                break;
            case 3:
                anim = "gesticulate_wildly";
                break;
            case 4:
                anim = "greet";
                break;
            case 5:
                anim = "look_casual";
                break;
            case 6:
                anim = "look_left";
                break;
            case 7:
                anim = "look_right";
                break;
            case 8:
                anim = "point_accusingly";
                break;
            case 9:
                anim = "pound_fist_chest";
                break;
            case 10:
                anim = "pound_fist_palm";
                break;
            case 11:
                anim = "taunt1";
                break;
            case 12:
                anim = "taunt2";
                break;
            case 13:
                anim = "taunt3";
                break;
        }
        ai_lib.doAction(npc, anim);
    }
    public static void doVictoryDance(obj_id npc) throws InterruptedException
    {
        if (ai_lib.isMonster(npc))
        {
            if (rand(1, 500) == 1)
            {
                if (rand(1, 2) == 1)
                {
                    doAnimationAction(npc, "trick_1");
                }
                else 
                {
                    doAnimationAction(npc, "trick_2");
                }
            }
            else 
            {
                doAction(npc, "vocalize");
            }
            return;
        }
        String anim = "celebrate";
        switch (rand(0, 24))
        {
            case 0:
                break;
            case 1:
                anim = "applause_excited";
                break;
            case 2:
                anim = "belly_laugh";
                break;
            case 3:
                anim = "celebrate1";
                break;
            case 4:
                anim = "coup_de_grace";
                break;
            case 5:
                anim = "dismiss";
                break;
            case 6:
                anim = "flex_biceps";
                break;
            case 7:
                anim = "laugh_pointing";
                break;
            case 8:
                anim = "point_accusingly";
                break;
            case 9:
                anim = "pound_fist_chest";
                break;
            case 10:
                anim = "pound_fist_palm";
                break;
            case 11:
                anim = "salute1";
                break;
            case 12:
                anim = "salute2";
                break;
            case 13:
                anim = "shrug_hands";
                break;
            case 14:
                anim = "shrug_shoulders";
                break;
            case 15:
                anim = "smack_self";
                break;
            case 16:
                anim = "shake_head_disgust";
                break;
            case 17:
                anim = "snap_finger1";
                break;
            case 18:
                anim = "snap_finger2";
                break;
            case 19:
                anim = "tap_head";
                break;
            case 20:
                anim = "taunt1";
                break;
            case 21:
                anim = "taunt2";
                break;
            case 22:
                anim = "taunt3";
                break;
            case 23:
                anim = "yawn";
                break;
            case 24:
                anim = "check_wrist_device";
                break;
        }
        doAction(npc, anim);
    }
    public static obj_id getLair(obj_id npc) throws InterruptedException
    {
        if (isPlayer(npc))
        {
            return null;
        }
        if (utils.hasScriptVar(npc, "ai.hasLair"))
        {
            return utils.getObjIdScriptVar(npc, "ai.hasLair");
        }
        obj_id myPOI = getObjIdObjVar(npc, "poi.baseObject");
        if (myPOI == null)
        {
            utils.setScriptVar(npc, "ai.noLiar", true);
            return null;
        }
        String buildingType = getStringObjVar(myPOI, "spawning.buildingType");
        if ((buildingType == null || buildingType.equals("none")) && !hasObjVar(myPOI, "spawning.target"))
        {
            utils.setScriptVar(npc, "ai.noLair", true);
            return null;
        }
        utils.setScriptVar(npc, "ai.hasLair", myPOI);
        return myPOI;
    }
    public static int aiGetApproachTriggerRange(obj_id npc) throws InterruptedException
    {
        return (int)aiGetAggroRadius(npc);
    }
    public static void aiSetPosture(obj_id npc, int newPosture) throws InterruptedException
    {
        if (getState(npc, STATE_COMBAT) != 1)
        {
            setPostureClientImmediate(npc, newPosture);
        }
        else 
        {
            attacker_results cbtAnimationResults = new attacker_results();
            cbtAnimationResults.endPosture = newPosture;
            cbtAnimationResults.id = npc;
            doCombatResults("change_posture", cbtAnimationResults, null);
        }
    }
    public static boolean canSit(obj_id npc) throws InterruptedException {
        int speciesNum = ai_lib.aiGetSpecies(npc);
        return speciesNum != -1 && dataTableGetInt("datatables/ai/species.iff", speciesNum, "CanSit") == 1;
    }
    public static boolean canLieDown(obj_id npc) throws InterruptedException {
        int speciesNum = ai_lib.aiGetSpecies(npc);
        return speciesNum != -1 && dataTableGetInt("datatables/ai/species.iff", speciesNum, "CanLieDown") == 1;
    }
    public static boolean isInSameBuilding(obj_id npc, obj_id target) throws InterruptedException {
        if (!isIdValid(npc) || !isIdValid(target)) {
            return false;
        }
        obj_id npcCell = getLocation(npc).cell;
        obj_id targetCell = getLocation(target).cell;
        if (!isIdValid(npcCell) && !isIdValid(targetCell)) {
            return true; //we're both outside
        }
        else if (!isIdValid(npcCell) || !isIdValid(targetCell)) {
            return false;
        }
        obj_id npcBldg = getTopMostContainer(npc);
        obj_id targetBldg = getTopMostContainer(target);
        return npcBldg == targetBldg && (canSee(npc, target));//we're in the same building (and can see each other)
    }
    public static String getAttackString(int hash) throws InterruptedException
    {
        if (hash == 0)
        {
            return "@combat_effects:none";
        }
        int[] attacks = dataTableGetIntColumn("datatables/ai/special_attack.iff", "ATTACK_HASH");
        for (int i = 0; i < attacks.length; i++)
        {
            if (hash == attacks[i])
            {
                return "@combat_effects:" + dataTableGetStringColumn("datatables/ai/special_attack.iff", "ATTACK_STRING")[i];
            }
        }
        return "@combat_effects:unknown_attack";
    }
    public static String getMilkType(obj_id npc) throws InterruptedException
    {
        String creatureName = getCreatureName(npc);
        if (creatureName == null)
        {
            return null;
        }
        return dataTableGetString(CREATURE_TABLE, creatureName, "milkType");
    }
    public static boolean checkForSmuggler(obj_id player) throws InterruptedException
    {
        if (!hasSkill(player, "class_smuggler_phase1_novice"))
        {
            return false;
        }
        int evadeScan = 0;
        if (hasSkill(player, "class_smuggler_phase1_novice"))
        {
            evadeScan += 15;
        }
        if (hasSkill(player, "class_smuggler_phase2_03"))
        {
            evadeScan += 15;
        }
        if (hasSkill(player, "class_smuggler_phase2_novice"))
        {
            evadeScan += 15;
        }
        if (hasSkill(player, "class_smuggler_phase3_novice"))
        {
            evadeScan += 15;
        }
        if (hasSkill(player, "class_smuggler_phase4_novice"))
        {
            evadeScan += 15;
        }
        if (hasSkill(player, "class_smuggler_phase4_master"))
        {
            evadeScan += 20;
        }
        if (rand(1, 100) > evadeScan)
        {
            sendSystemMessage(player, SMUGGLER_SCAN_FAIL);
            return false;
        }
        sendSystemMessage(player, SMUGGLER_SCAN_SUCCESS);
        return true;
    }
    public static boolean checkForJedi(obj_id player) throws InterruptedException
    {
        if (!utils.isProfession(player, utils.FORCE_SENSITIVE) || factions.getFactionFlag(player) == factions.FACTION_FLAG_NEUTRAL)
        {
            return false;
        }
        int mindTrick = 0;
        if (isGod(player))
        {
            return true;
        }
        else if (badge.hasBadge(player, "bdg_jedi_elder") || getState(player, STATE_GLOWING_JEDI) != 0)
        {
            mindTrick += 40;
        }
        else if (badge.hasBadge(player, "new_prof_jedi_master"))
        {
            mindTrick += 30;
        }
        else 
        {
            mindTrick += 10;
        }
        return rand(1, 100) <= mindTrick;
    }
    public static boolean setupNpc(obj_id npc, boolean conversable, boolean invulnerable) throws InterruptedException
    {
        return setupNpc(npc, null, conversable, invulnerable);
    }
    public static boolean setupNpc(obj_id npc, String name, boolean conversable, boolean invulnerable) throws InterruptedException
    {
        if (name != null && !name.equals(""))
        {
            setName(npc, name);
        }
        if (conversable)
        {
            setCondition(npc, CONDITION_CONVERSABLE);
        }
        setInvulnerable(npc, invulnerable);
        return true;
    }
    public static void establishAgroLink(obj_id subject, float range) throws InterruptedException
    {
        String socialGroup = getSocialGroup(subject);
        obj_id[] creatures = getCreaturesInRange(subject, range);
        if (creatures == null || creatures.length == 0)
        {
            return;
        }
        Vector linkedCreatures = new Vector();
        linkedCreatures.setSize(0);
        String creatureSocialGroup;
        for (obj_id creature : creatures) {
            creatureSocialGroup = getSocialGroup(creature);
            if (creatureSocialGroup != null && creatureSocialGroup.equals(socialGroup)) {
                utils.addElement(linkedCreatures, creature);
            }
        }
        if (linkedCreatures.size() == 0)
        {
            return;
        }
        obj_id[] creatureList = new obj_id[0];
        creatureList = new obj_id[linkedCreatures.size()];
        linkedCreatures.toArray(creatureList);

        if (creatureList.length == 0)
        {
            return;
        }
        utils.setScriptVar(subject, ALLY_LIST, creatureList);
    }
    public static void establishAgroLink(obj_id subject, obj_id[] allies) throws InterruptedException
    {
        if (allies == null || allies.length == 0)
        {
            return;
        }
        utils.setScriptVar(subject, ALLY_LIST, allies);
    }
    public static void triggerAgroLinks(obj_id subject, obj_id attacker) throws InterruptedException
    {
        if (!utils.hasScriptVar(subject, ALLY_LIST))
        {
            return;
        }
        obj_id[] allyList = utils.getObjIdArrayScriptVar(subject, ALLY_LIST);
        if (allyList == null || allyList.length == 0)
        {
            return;
        }
        for (obj_id ally : allyList) {
            if (isIdValid(ally) && exists(ally) && !isDead(ally)) {
                if (!isInCombat(ally)) {
                    startCombat(ally, attacker);
                } else {
                    addHate(ally, attacker, 1);
                }
            }
        }
    }
    public static void creatureLevelUp(obj_id creature, obj_id victim) throws InterruptedException
    {
        showFlyText(creature, new string_id("cbt_spam", "level_up"), 2.5f, colors.CORNFLOWERBLUE);
        playClientEffectObj(victim, "clienteffect/level_granted.cef", creature, null);
        String creatureName = getCreatureName(creature);
        if (creatureName.indexOf("elite_") < 1 && creatureName.indexOf("boss_") < 1)
        {
            create.initializeCreature(creature, creatureName, utils.dataTableGetRow(CREATURE_TABLE, creatureName), getLevel(creature) + 1);
        }
    }
    public static boolean mindTrick(obj_id player, obj_id target) throws InterruptedException
    {
        int niche = aiGetNiche(target);
        if (vehicle.isVehicle(target) || niche == NICHE_DROID || niche == NICHE_ANDROID || niche == NICHE_VEHICLE || isTurret(target))
        {
            return false;
        }
        if (!isPlayer(target) && isOnHateList(player, target))
        {
            int mt_resist = getEnhancedSkillStatisticModifierUncapped(target, "resistance_mind_trick");
            if (mt_resist >= 100)
            {
                showFlyTextPrivate(target, player, new string_id("cbt_spam", "immune"), 1.5f, colors.WHITE);
                return false;
            }
            if (rand(1, 100) > mt_resist)
            {
                playClientEffectObj(target, "appearance/pt_mind_trick.prt", target, "root");
                removeHateTarget(target, player);
                return true;
            }
            else 
            {
                showFlyTextPrivate(target, player, new string_id("cbt_spam", "resist"), 1.5f, colors.WHITE);
                return false;
            }
        }
        return false;
    }
    public static boolean isTauntable(obj_id creature) throws InterruptedException
    {
        return !utils.hasScriptVar(creature, "combat.immune.taunt");
    }
    public static void establishSharedHealth(Vector allies) throws InterruptedException
    {
        for (int i = 0; i < allies.size(); i++)
        {
            utils.setScriptVar(((obj_id)allies.get(i)), SHARED_HEALTH_LIST, allies);
        }
    }
    public static int triggerSharedHealthLink(obj_id self, int totalDamage) throws InterruptedException
    {
        if (utils.hasScriptVar(self, ai_lib.SHARED_HEALTH_LIST))
        {
            Vector allies = utils.getResizeableObjIdArrayScriptVar(self, ai_lib.SHARED_HEALTH_LIST);
            utils.removeScriptVar(self, ai_lib.SHARED_HEALTH_LIST);
            Vector validList = new Vector();
            validList.setSize(0);
            if (allies == null || allies.size() == 0)
            {
                return totalDamage;
            }
            for (Object ally : allies) {
                if (!isIdValid(((obj_id) ally)) || !exists(((obj_id) ally)) || isDead(((obj_id) ally)) || aiIsTethered(((obj_id) ally))) {
                    continue;
                }
                validList.add(((obj_id) ally));
            }
            utils.setScriptVar(self, ai_lib.SHARED_HEALTH_LIST, validList);
            if (validList.size() < 2)
            {
                return totalDamage;
            }
            int split = Math.round((float) totalDamage / validList.size());
            for (Object aValidList : validList) {
                if (aValidList == self) {
                    continue;
                }
                damage(((obj_id) aValidList), DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, split);
            }
            return split;
        }
        return totalDamage;
    }
    public static int getDifficultyClass(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target) || !hasObjVar(target, "difficultyClass"))
        {
            return -1;
        }
        return getIntObjVar(target, "difficultyClass");
    }
    public static boolean isVehicleMine(obj_id self) throws InterruptedException
    {
        return hasScript(self, "theme_park.heroic.echo_base.vehicle_mine");
    }
}
