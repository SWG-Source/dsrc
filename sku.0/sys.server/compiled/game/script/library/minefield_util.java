package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.anims;
import script.library.combat;

public class minefield_util extends script.base_script
{
    public minefield_util()
    {
    }
    public static final float WARNING_BUFFER = 16f;
    public static final float MINE_ACTION_RADIUS = 1.4f;
    public static final float TIME_DELAY = 8f;
    public static final float DESTROY_DELAY = 12f;
    public static final int WARNING_COUNT = 10;
    public static final int WIDTH_OF_FIELD = 8;
    public static final int LENGTH_OF_FIELD = 8;
    public static final int MINE_DAMAGE = 5;
    public static final int NUMBER_OF_MINES = 5;
    public static final int AMOUNT_OWNER_CAN_MAKE = 4;
    public static final float MINE_DAMAGE_RADIUS = 6f;
    public static final String VAR_AMOUNT_OWNER_CAN_MAKE = "amount_owner_can_make";
    public static final String VAR_BREACHED_LIST = "breached_list";
    public static final String VAR_OWNED_LIST = "owned_list";
    public static final String VAR_IS_CREATING = "is_creating";
    public static final String VAR_IS_DESTROYING = "is_destroying";
    public static final String VAR_IS_KNEELING = "is_kneeling";
    public static final String VAR_MARKER_CREATED = "marker_created";
    public static final String VAR_OK_FOR_MESSAGE = "ok_for_message";
    public static final String VAR_PLAYER_IS_ANIMATING = "player_is_animating";
    public static final String VAR_IS_RANDOMLY_BUILT = "is_randomly_built";
    public static final String VAR_IS_MARKER = "is_marker";
    public static final String VAR_IS_ACTIVATED = "is_activated";
    public static final String VAR_IS_BUILT = "is_built";
    public static final String VAR_OWNER = "minefield_owner";
    public static final String VAR_MINE_LOCATIONS = "mine_locations";
    public static final String VAR_ACTIVE_MINE_COUNT = "active_mine_count";
    public static final String VAR_NUMBER_OF_MINES = "number_of_mines";
    public static final String VAR_MINE_DAMAGE = "mine_damage";
    public static final String VAR_MINES_DETECTED = "mines_detected";
    public static final String VAR_MINE_DAMAGE_RADIUS = "mine_damage_radius";
    public static final String VAR_BLAST = "blast_blast_effect";
    public static final String VAR_MINE_OBJECTS = "mine_objects";
    public static final String VAR_WARNING_EFFECTS = "blast_smoke_effect";
    public static final String VAR_WARNING_COUNT = "warning_count";
    public static final String VAR_LENGTH_OF_FIELD = "length_of_field";
    public static final String VAR_WIDTH_OF_FIELD = "width_of_field";
    public static final String VAR_WARNING_RADIUS = "warning_radius";
    public static final String VAR_WARNING_EFFECTS_ACTIVE = "waring_effects_active";
    public static final String LAST_BOMB_LOCATION_NAME = "last_bomb_location_name";
    public static final String MINEFIELD_VOLUME_NAME = "minefield_warning_area_volume";
    public static final String MARKER_OBJ_TEMPLATE = "object/static/item/item_invisible.iff";
    public static final String MINE_OBJ_TEMPLATE = "object/weapon/mine/wp_mine_anti_vehicle.iff";
    public static final String MINEFIELD_SCRIPT = "structures.general.military.minefields.minefield";
    public static final String MINEFIELD_OWNER_SCRIPT = "structures.general.military.minefields.minefield_owner";
    public static final String MINEFIELD_BREACHER_SCRIPT = "structures.general.military.minefields.minefield_breacher";
    public static final String BLAST_PARTICLE_TEMPLATE = "object/static/particle/particle_sm_explosion.iff";
    public static final String WARNING_PARTICLE_TEMPLATE = "object/static/particle/particle_mine_warning.iff";
    public static boolean setMarkerData(obj_id marker) throws InterruptedException
    {
        boolean isSet = false;
        isSet = setObjVar(marker, VAR_IS_MARKER, true);
        isSet = setObjVar(marker, VAR_NUMBER_OF_MINES, NUMBER_OF_MINES);
        isSet = setObjVar(marker, VAR_LENGTH_OF_FIELD, LENGTH_OF_FIELD);
        isSet = setObjVar(marker, VAR_WIDTH_OF_FIELD, WIDTH_OF_FIELD);
        isSet = setObjVar(marker, VAR_AMOUNT_OWNER_CAN_MAKE, AMOUNT_OWNER_CAN_MAKE);
        isSet = setObjVar(marker, VAR_ACTIVE_MINE_COUNT, NUMBER_OF_MINES);
        isSet = setObjVar(marker, VAR_MINE_DAMAGE, MINE_DAMAGE);
        isSet = setObjVar(marker, VAR_MINE_DAMAGE_RADIUS, MINE_DAMAGE_RADIUS);
        isSet = setObjVar(marker, VAR_WARNING_COUNT, WARNING_COUNT);
        float radius = getFloatObjVar(marker, VAR_WIDTH_OF_FIELD) + WARNING_BUFFER;
        isSet = setObjVar(marker, VAR_WARNING_RADIUS, radius);
        
        if (isSet)
        {
            debugSpeakMsg(marker, "###############ALL data on marker was set successfully#################");
        }
        else 
        {
            debugSpeakMsg(marker, "###############ERROR: some data on the minefield marker was not set correctly#################");
        }

        return isSet;
    }
    public static boolean buildMineField(obj_id marker) throws InterruptedException
    {
        debugSpeakMsg(marker, "#############attempting to lay down mines#################");
        int numberOfMines = getIntObjVar(marker, VAR_NUMBER_OF_MINES);
        int width = getIntObjVar(marker, VAR_WIDTH_OF_FIELD);
        int length = getIntObjVar(marker, VAR_LENGTH_OF_FIELD);
        boolean isCreated = false;
        location markerLoc = getLocation(marker);
        location upperLeft = new location(markerLoc.x - (width / 2), markerLoc.y, markerLoc.z + (length / 2), "upperLeft");
        location lowerRight = new location(markerLoc.x + (width / 2), markerLoc.y, markerLoc.z - (length / 2), "lowerRight");
        isCreated = setObjVar(marker, LAST_BOMB_LOCATION_NAME, "default_name");
        location[] mineLocations = new location[numberOfMines];
        if (mineLocations.length == 0)
        {
            return false;
        }
        if (getBooleanObjVar(marker, VAR_IS_RANDOMLY_BUILT))
        {
            for (int i = 0; i < numberOfMines; ++i)
            {
                mineLocations[i] = new location(((float)rand((int)upperLeft.x, (int)lowerRight.x)), markerLoc.y, ((float)rand((int)lowerRight.z, (int)upperLeft.z)), markerLoc.area);
            }
            isCreated = setObjVar(marker, VAR_MINE_LOCATIONS, mineLocations);
        }
        else 
        {
            int totalLength = 2 * length;
            float separation = (float)totalLength / (float)numberOfMines;
            float mineSpot = markerLoc.x - length;
            for (int i = 0; i < numberOfMines; ++i)
            {
                mineLocations[i] = new location(mineSpot, markerLoc.y, markerLoc.z, markerLoc.area);
                mineSpot += separation;
            }
            isCreated = setObjVar(marker, VAR_MINE_LOCATIONS, mineLocations);
        }
        return isCreated;
    }
    public static boolean explodeMine(obj_id player, int mineNumber, obj_id marker) throws InterruptedException
    {
        boolean hasExploded = false;
        String particleTemplate = BLAST_PARTICLE_TEMPLATE;
        location[] mineLocations = getLocationArrayObjVar(marker, minefield_util.VAR_MINE_LOCATIONS);
        debugSpeakMsg(player, "########Particle should fire at this location " + mineLocations[mineNumber].toString() + "###########");
        obj_id blast = createObject(particleTemplate, mineLocations[mineNumber]);
        hasExploded = setObjVar(marker, VAR_BLAST + marker.toString() + mineNumber, blast);
        debugSpeakMsg(player, "############Blast ID: " + blast.toString() + "##################");
        int mineCount = getIntObjVar(marker, VAR_ACTIVE_MINE_COUNT);
        debugSpeakMsg(player, "#####I EXPLODED MINE #" + mineNumber + " and there are " + mineCount + " mines left##############");
        obj_id[] mineObjects = getObjIdArrayObjVar(marker, minefield_util.VAR_MINE_OBJECTS);
        obj_id thisMine = mineObjects[mineNumber];
        destroyObject(thisMine);
        string_id msg = new string_id("cbt_spam", "detonation_message_single");
        combat.sendCombatSpamMessage(player, msg);
        sendSystemMessage(player, msg);
        return hasExploded;
    }
    public static boolean dealDamage(obj_id player, obj_id marker) throws InterruptedException
    {
        boolean damageDealt = false;
        location detLoc = getLocation(player);
        int mineDamage = getIntObjVar(marker, VAR_MINE_DAMAGE);
        float damageRadius = getFloatObjVar(marker, VAR_MINE_DAMAGE_RADIUS);
        obj_id[] creatures = getCreaturesInRange(player, damageRadius);
        int health = getHealth(player);
        debugSpeakMsg(player, "#####" + player + " health before: " + health + "##############");
        health -= mineDamage;
        setHealth(player, health);
        debugSpeakMsg(player, "#####" + player + " health after: " + health + "##############");
        obj_id owner = getObjIdObjVar(marker, VAR_OWNER);
        damageAdjuster(creatures, player, damageRadius, mineDamage, owner);
        debugSpeakMsg(player, "#########ANIMATION STATUS in dealDamage(): " + getBooleanObjVar(player, minefield_util.VAR_PLAYER_IS_ANIMATING) + "##########");
        if (!getBooleanObjVar(player, minefield_util.VAR_PLAYER_IS_ANIMATING))
        {
            setObjVar(player, minefield_util.VAR_PLAYER_IS_ANIMATING, true);
        }
        return damageDealt;
    }
    public static void damageAdjuster(obj_id[] creatures, obj_id whoDoneIt, float damageRadius, int damage, obj_id owner) throws InterruptedException
    {
        for (int i = 0; i < creatures.length; ++i)
        {
            float distance = getDistance(creatures[i], whoDoneIt);
            debugSpeakMsg(whoDoneIt, "#####" + creatures[i] + " Distance from detonator: " + distance + "##############");
            if (creatures[i] != whoDoneIt && distance < damageRadius && pvpIsEnemy(owner, creatures[i]))
            {
                float percentDamage = 1 - (distance / damageRadius);
                debugSpeakMsg(creatures[i], "#####" + creatures[i] + " Percent Damage: " + percentDamage + "##############");
                int health = getHealth(creatures[i]);
                health -= damage * percentDamage;
                setHealth(creatures[i], health);
                string_id msg = new string_id("cbt_spam", "damage_message_single");
                combat.sendCombatSpamMessage(creatures[i], msg);
                sendSystemMessage(creatures[i], msg);
            }
        }
    }
    public static void animatePlayerDamage(obj_id player) throws InterruptedException
    {
        debugSpeakMsg(player, "#########ANIMATION STATUS in animatePlayerDamage(): " + getBooleanObjVar(player, minefield_util.VAR_PLAYER_IS_ANIMATING) + "##########");
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_KNOCKED_DOWN;
        cbtAnimationResults.id = player;
        doCombatResults("change_posture", cbtAnimationResults, null);
    }
    public static void removeBreacherScripts(obj_id marker) throws InterruptedException
    {
        float warningRadius = getFloatObjVar(marker, minefield_util.VAR_WARNING_RADIUS);
        obj_id[] creatures = getCreaturesInRange(marker, warningRadius);
        for (int i = 0; creatures != null && i < creatures.length; ++i)
        {
            obj_id[] breachedList = getObjIdArrayObjVar(creatures[i], VAR_BREACHED_LIST);
            if (breachedList.length > 0)
            {
                continue;
            }
            if (isPlayer(creatures[i]) || isMob(creatures[i]))
            {
                detachScript(creatures[i], minefield_util.MINEFIELD_BREACHER_SCRIPT);
            }
        }
    }
    public static boolean cleanUpBlastEffects(obj_id marker) throws InterruptedException
    {
        boolean isClean = false;
        int blastRemovedCount = 0;
        int mineCount = minefield_util.NUMBER_OF_MINES;
        debugSpeakMsg(marker, "###############Attempting to destory all remaining particle objects##################" + mineCount);
        for (int i = 0; i < mineCount; ++i)
        {
            obj_id particleObj = getObjIdObjVar(marker, minefield_util.VAR_BLAST + marker.toString() + i);
            if (particleObj != null)
            {
                isClean = destroyObject(particleObj);
                ++blastRemovedCount;
            }
        }
        debugSpeakMsg(marker, "################" + blastRemovedCount + " particle objects were destroyed################");
        return isClean;
    }
    public static int numberOfMinesDetected(obj_id player) throws InterruptedException
    {
        int mineCount = NUMBER_OF_MINES;
        return mineCount;
    }
}
