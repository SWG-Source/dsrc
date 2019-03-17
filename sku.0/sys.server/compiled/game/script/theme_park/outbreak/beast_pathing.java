package script.theme_park.outbreak;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class beast_pathing extends script.base_script
{
    public beast_pathing()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_pathing";
    public static final String PATHING_NODE = "pathing_node";
    public static final String SPAWNER_PATH = "spawner_path";
    public static final String SUCCESS_SIGNAL = "success_signal";
    public static final String FAIL_SIGNAL = "fail_signal";
    public static final String UPDATE_SIGNAL = "update_signal";
    public static final String STORMTROOPER_RETREAT = "stormtrooper_retreat";
    public static final String ENEMY_LIST = "enemyList";
    public static final String WAYPOINT_LIST = "wayPointList";
    public static final String WAYPOINT_LOCS = "wayPtLocs";
    public static final int RADIUS = 2000;
    public static final int MAXDIST = 30;
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final string_id SID_YOU_WENT_TOO_FAR = new string_id("theme_park/outbreak/outbreak", "delivery_you_went_too_far");
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "blowUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (!hasObjVar(self, "invln"))
        {
            return SCRIPT_CONTINUE;
        }
        setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        if (!hasObjVar(self, "invln"))
        {
            return SCRIPT_CONTINUE;
        }
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int blowUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] playerTargets = trial.getValidPlayersInRadius(self, 7);
        obj_id[] npcTargets = getNPCsInRange(getLocation(self), 10);
        playClientEffectLoc(self, "clienteffect/combat_explosion_lair_large.cef", getLocation(self), 0.4f);
        if (playerTargets != null && playerTargets.length > 0)
        {
            for (obj_id playerTarget : playerTargets) {
                setPosture(playerTarget, POSTURE_INCAPACITATED);
                int damageAmount = getAttrib(playerTarget, HEALTH) + 1000;
                damage(playerTarget, DAMAGE_KINETIC, HIT_LOCATION_BODY, damageAmount);
            }
        }
        if (npcTargets != null && npcTargets.length > 0)
        {
            for (obj_id npcTarget : npcTargets) {
                if ((factions.getFaction(npcTarget)).equals("afflicted")) {
                    setPosture(npcTarget, POSTURE_INCAPACITATED);
                }
            }
        }
        failBeastDeliveryQuest(self);
        messageTo(self, "cleanUpDeliveryBeast", null, 5, true);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        setMovementRun(self);
        setBaseRunSpeed(self, (getBaseRunSpeed(self) - 10));
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "objParent"))
        {
            obj_id objParent = getObjIdObjVar(self, "objParent");
            if (isValidId(objParent))
            {
                CustomerServiceLog("outbreak_themepark", "beast_pathing.OnDestroy() SENDING spawner the message to spawn a new delivery volunteer.");
                messageTo(objParent, "spawnVolunteerNPC", null, 5, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startBeastDeliveryPathing(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "owner"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "questName"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.startBeastDeliveryPathing() Mob: " + self + " FAILED TO FIND OWNER OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, "questName");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.startBeastDeliveryPathing() Mob: " + self + " FAILED TO FIND questName OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, WAYPOINT_LOCS))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.startBeastDeliveryPathing() Mob: " + self + " FAILED TO FIND waypointPathNodeLocations OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        location[] wayPtLocs = getLocationArrayObjVar(self, WAYPOINT_LOCS);
        if (wayPtLocs == null || wayPtLocs.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.startBeastDeliveryPathing() Mob: " + self + " FAILED TO FIND waypointPathNodeLocations OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "beast_pathing.startBeastDeliveryPathing() Beast " + self + " found next point. Player " + owner + " is within range. Moving beast to next delivery node!");
        for (int i = 0; i < wayPtLocs.length; i++)
        {
            if (i > 1 && i != wayPtLocs.length - 1)
            {
                spawnUndead(self, wayPtLocs[i]);
            }
        }
        setMovementRun(self);
        setBaseRunSpeed(self, (getBaseRunSpeed(self) - 10));
        patrolOnce(self, wayPtLocs, 0);
        messageTo(self, "checkOwnerValidity", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpDeliveryBeast(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] enemyArray = utils.getObjIdArrayScriptVar(self, ENEMY_LIST);
        if (enemyArray != null && enemyArray.length > 0)
        {
            for (obj_id obj_id : enemyArray) {
                messageTo(obj_id, "destroySelf", null, 1, false);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int checkOwnerValidity(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "owner"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " FAILED TO FIND OWNER OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, "questName");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " FAILED TO FIND questName OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        location ownerLoc = getLocation(owner);
        float distanceCheck = utils.getDistance2D(getLocation(self), ownerLoc);
        if (distanceCheck > MAXDIST)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " has been left alone and is destroying self, failing quest for player. The distance between player and NPC was: " + distanceCheck);
            sendSystemMessage(owner, SID_YOU_WENT_TOO_FAR);
            failBeastDeliveryQuest(self);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is checking their current location to see if they are stuck.");
        if (!ai_lib.isInCombat(self))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is not in combat.");
            if (!hasObjVar(self, "lastLocation"))
            {
                CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is checking its very first location. Will check again later to see if stuck.");
                setObjVar(self, "lastLocation", getLocation(self));
            }
            else 
            {
                CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " has a location objvar.");
                location currentLocation = getLocation(self);
                location lastLocation = getLocationObjVar(self, "lastLocation");
                if (Math.abs(currentLocation.x - lastLocation.x) < 0.5 && Math.abs(currentLocation.z - lastLocation.z) < 0.5)
                {
                    CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK!!! Calculating new path.");
                    if (!hasObjVar(self, WAYPOINT_LOCS))
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and doesnt have waypoint location list. Blowing up this NPC!");
                        messageTo(self, "blowUp", null, 2, false);
                    }
                    location waypointLocList[] = getLocationArrayObjVar(self, WAYPOINT_LOCS);
                    if (waypointLocList == null || waypointLocList.length <= 0)
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and doesnt have a valid waypoint location list. Blowing up this NPC!");
                        messageTo(self, "blowUp", null, 2, false);
                    }
                    float smallestDist = 300;
                    location closestLoc = null;
                    boolean modified = false;
                    for (location location : waypointLocList) {
                        float npcAndWaypointDist = getDistance(getLocation(self), location);
                        if (npcAndWaypointDist > smallestDist) {
                            continue;
                        } else {
                            CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and has found a node that is closer than any previously found node.");
                            smallestDist = npcAndWaypointDist;
                            closestLoc = location;
                            modified = true;
                        }
                    }
                    if (closestLoc == null)
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and the closest pathNode has not pathNode number. Blowing up this NPC!");
                        messageTo(self, "blowUp", null, 2, false);
                    }
                    int pathNodePriority = getLocationPosition(waypointLocList, closestLoc);
                    if (pathNodePriority < 0)
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and the pathNode priority was: " + pathNodePriority + " while the waypoint length was: " + waypointLocList.length + ". subtracting the 2 gave an invalid number. Blowing up this NPC!");
                        messageTo(self, "cleanUpDeliveryBeast", null, 1, true);
                    }
                    int newArrayLength = (waypointLocList.length - (pathNodePriority));
                    CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() pathNodePriority: " + pathNodePriority);
                    CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() newArrayLength: " + newArrayLength);
                    if (newArrayLength < 0)
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and the pathNode priority was: " + pathNodePriority + " while the waypoint length was: " + waypointLocList.length + ". subtracting the 2 gave an invalid number. Blowing up this NPC!");
                        messageTo(self, "blowUp", null, 2, false);
                    }
                    else if (newArrayLength == 0 || newArrayLength == 1)
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is STUCK and pathing to the LAST NODE in the list");
                        pathTo(self, waypointLocList[waypointLocList.length - 1]);
                    }
                    else 
                    {
                        CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() successfully found a new path list. The list will be of length: " + newArrayLength);
                        location[] newPathLocs = new location[newArrayLength];
                        System.arraycopy(waypointLocList, (pathNodePriority), newPathLocs, 0, newArrayLength);
                        if (newPathLocs != null && newPathLocs.length > 1)
                        {
                            patrolOnce(self, newPathLocs);
                        }
                    }
                }
                else 
                {
                    CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " location has changed. Check again later.");
                    setObjVar(self, "lastLocation", getLocation(self));
                }
            }
        }
        else 
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.checkOwnerValidity() Mob: " + self + " is in combat. Will check for being stuck later.");
        }
        messageTo(self, "checkOwnerValidity", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpCampNpcAndParent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "owner"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "questName"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "escortTask"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "successSignalName"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "collectionSlot"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.cleanUpCampNpcAndParent() Mob: " + self + " FAILED TO FIND OWNER OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        String collectionSlot = getStringObjVar(self, "collectionSlot");
        if (collectionSlot == null || collectionSlot.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.startBeastDeliveryPathing() Mob: " + self + " FAILED TO FIND collectionSlot OBJVAR.");
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, "questName");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.cleanUpCampNpcAndParent() Mob: " + self + " FAILED TO FIND QUESTNAME OBJVAR for player: " + owner);
            return SCRIPT_CONTINUE;
        }
        String escortTask = getStringObjVar(self, "escortTask");
        if (escortTask == null || escortTask.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.cleanUpCampNpcAndParent() Mob: " + self + " FAILED TO FIND ESCORT TASK OBJVAR for player: " + owner);
            return SCRIPT_CONTINUE;
        }
        String successSignalName = getStringObjVar(self, "successSignalName");
        if (escortTask == null || escortTask.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.cleanUpCampNpcAndParent() Mob: " + self + " FAILED TO FIND success TASK OBJVAR for player: " + owner);
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(owner, questName, escortTask))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.cleanUpCampNpcAndParent() Mob: " + self + " FAILED TO FIND success TASK OBJVAR for player: " + owner);
            return SCRIPT_CONTINUE;
        }
        groundquests.sendSignal(owner, successSignalName);
        messageTo(self, "cleanUpDeliveryBeast", null, 5, true);
        if (!hasCompletedCollection(owner, collectionSlot))
        {
            modifyCollectionSlotValue(owner, collectionSlot, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean failBeastDeliveryQuest(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "owner"))
        {
            return false;
        }
        if (!hasObjVar(self, "questName"))
        {
            return false;
        }
        if (!hasObjVar(self, "escortTask"))
        {
            return false;
        }
        if (!hasObjVar(self, "failSignalName"))
        {
            return false;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() Mob: " + self + " FAILED TO FIND OWNER OBJVAR.");
            return false;
        }
        String questName = getStringObjVar(self, "questName");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() Mob: " + self + " FAILED TO FIND QUESTNAME OBJVAR for player: " + owner);
            return false;
        }
        String escortTask = getStringObjVar(self, "escortTask");
        if (escortTask == null || escortTask.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() Mob: " + self + " FAILED TO FIND ESCORT TASK OBJVAR for player: " + owner);
            return false;
        }
        String failSignalName = getStringObjVar(self, "failSignalName");
        if (escortTask == null || escortTask.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() Mob: " + self + " FAILED TO FIND FAIL TASK OBJVAR for player: " + owner);
            return false;
        }
        CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() creature: " + self + " has failSignalName: " + failSignalName);
        if (!groundquests.isTaskActive(owner, questName, escortTask))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() Mob: " + self + " FAILED TO FIND FAIL TASK OBJVAR for player: " + owner);
            return false;
        }
        CustomerServiceLog("outbreak_themepark", "beast_pathing.failBeastDeliveryQuest() creature: " + self + " is sending fail signal to player " + owner + "  quest");
        groundquests.sendSignal(owner, failSignalName);
        messageTo(self, "cleanUpDeliveryBeast", null, 1, false);
        return true;
    }
    public boolean spawnUndead(obj_id self, location wayptLoc) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() Init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (wayptLoc == null)
        {
            return false;
        }
        if (!hasObjVar(self, "owner"))
        {
            return false;
        }
        if (!hasObjVar(self, "combatLevel"))
        {
            return false;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() Mob: " + self + " FAILED TO FIND OWNER OBJVAR.");
            return false;
        }
        CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() Initial validation completed.");
        int combatLevel = getIntObjVar(self, "combatLevel") - 5;
        CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() NPC combatLevel: " + combatLevel);
        int spawnCount = 0;
        int spawnDecider = rand(0, 4);
        if (spawnDecider == 2 || spawnDecider == 3)
        {
            spawnCount = 1;
        }
        else if (spawnDecider == 4)
        {
            spawnCount = 2;
        }
        String creatureName = "outbreak_afflicted_defense";
        for (int i = 0; i < spawnCount; i++)
        {
            location randomLoc = utils.getRandomLocationInRing(wayptLoc, 5.0f, 10.0f);
            obj_id mob = create.object(creatureName, randomLoc);
            if (!isValidId(mob) || !exists(mob))
            {
                CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() Mob: " + mob + " could not be created!");
                continue;
            }
            CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() Mob level is set to match player level");
            setObjVar(mob, create.INITIALIZE_CREATURE_DO_NOT_SCALE_OBJVAR, 1);
            dictionary creatureDict = utils.dataTableGetRow(CREATURE_TABLE, creatureName);
            if (creatureDict != null)
            {
                create.initializeCreature(mob, creatureName, creatureDict, combatLevel);
            }
            CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() Mob level is set to match player level.");
            setInvulnerable(mob, false);
            clearCondition(mob, CONDITION_CONVERSABLE);
            setAttributeAttained(mob, attrib.OUTBREAK_AFFLICTED);
            setObjVar(mob, "owner", self);
            attachScript(mob, "theme_park.outbreak.dynamic_enemy");
            CustomerServiceLog("outbreak_themepark", "beast_pathing.spawnUndead() the NPC is spawned for beast: " + self);
            if (!utils.hasScriptVar(self, ENEMY_LIST))
            {
                Vector enemies = null;
                enemies = utils.addElement(enemies, mob);
                utils.setScriptVar(self, ENEMY_LIST, enemies);
            }
            else 
            {
                Vector enemyVector = null;
                obj_id[] enemyArray = utils.getObjIdArrayScriptVar(self, ENEMY_LIST);
                Vector enemies = utils.concatArrays(enemyVector, enemyArray);
                enemies = utils.addElement(enemies, mob);
                utils.setScriptVar(self, ENEMY_LIST, enemies);
            }
        }
        return true;
    }
    public int getLocationPosition(location[] list, location key) throws InterruptedException
    {
        if (list == null || list.length <= 0)
        {
            return -1;
        }
        if (key == null)
        {
            return -1;
        }
        for (int i = 0; i < list.length - 1; i++)
        {
            if (list[i] == null || !list[i].equals(key))
            {
                continue;
            }
            return i;
        }
        return -1;
    }
}
