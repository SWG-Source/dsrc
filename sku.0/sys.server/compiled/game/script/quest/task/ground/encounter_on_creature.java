package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.utils;
import script.library.groundquests;

public class encounter_on_creature extends script.base_script
{
    public encounter_on_creature()
    {
    }
    public static final String objvarOnCreatureOwner = "quest.owner";
    public static final String objvarOnCreatureQuestCrc = "quest.questCrc";
    public static final String objvarOnCreatureTaskId = "quest.taskId";
    public static final String objvarAttackOnSpawn = "quest.attackOnSpawn";
    public static final String scriptVarNumAttempts = "quest.encounterOnCreaturePathCheck";
    public static final String objvarOnCreatureStartLocation = "quest.startLocation";
    public static final String objvarOnCreatureStartDistanceToPlayer = "quest.startDistanceToPlayer";
    public static final String taskType = "encounter";
    public static float combatDistance = 64;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, objvarOnCreatureOwner);
        location currentLocation = getLocation(self);
        location playerLocation = getLocation(player);
        float distanceToPlayer = currentLocation.distance(playerLocation);
        setObjVar(self, objvarOnCreatureStartLocation, currentLocation);
        setObjVar(self, objvarOnCreatureStartDistanceToPlayer, distanceToPlayer);
        dictionary params = new dictionary();
        messageTo(self, "messageEncounterTaskCheckCreatureDistance", params, 15, false);
        return SCRIPT_CONTINUE;
    }
    public int messageEncounterTaskCheckCreatureDistance(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, objvarOnCreatureOwner);
        int numTries = 0;
        if (utils.hasScriptVar(self, scriptVarNumAttempts))
        {
            numTries = utils.getIntScriptVar(self, scriptVarNumAttempts);
        }
        location currentLocation = getLocation(self);
        location playerLocation = getLocation(player);
        if(currentLocation == null && playerLocation == null){
            utils.setScriptVar(self, scriptVarNumAttempts, ++numTries);
            if (numTries < 4)
            {
                messageTo(self, "messageEncounterTaskCheckCreatureDistance", null, 15, false);
            }
            return SCRIPT_CONTINUE;
        }
        float distanceToPlayer = currentLocation.distance(playerLocation);
        boolean isDead = ai_lib.isAiDead(self);
        if (isDead)
        {
            return SCRIPT_CONTINUE;
        }
        boolean isConversing = hasCondition(self, CONDITION_CONVERSABLE);
        if ((distanceToPlayer > combatDistance) || distanceToPlayer == -1)
        {
            location startLocation = getLocationObjVar(self, objvarOnCreatureStartLocation);
            float startDistanceToPlayer = getFloatObjVar(self, objvarOnCreatureStartDistanceToPlayer);
            if (startDistanceToPlayer < .1f)
            {
                startDistanceToPlayer = .1f;
            }
            float distanceTraveled = startLocation.distance(currentLocation);
            if (((distanceTraveled == 0) || (distanceToPlayer / startDistanceToPlayer) > 0.9) && combatDistance > distanceToPlayer)
            {
                int questCrc = getIntObjVar(self, objvarOnCreatureQuestCrc);
                int taskId = getIntObjVar(self, objvarOnCreatureTaskId);
                groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "messageEncounterTaskCheckCreatureDistance", "[" + self + "] not closing on player [" + player + "], warping to player location.");
                setLocation(self, playerLocation);
                if (!isConversing && isTangible(player))
                {
                    startCombat(self, player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (!isConversing && isTangible(player))
        {
            startCombat(self, player);
        }
        setObjVar(self, objvarOnCreatureStartLocation, currentLocation);
        setObjVar(self, objvarOnCreatureStartDistanceToPlayer, distanceToPlayer);
        numTries++;
        utils.setScriptVar(self, scriptVarNumAttempts, numTries);
        if (numTries < 4)
        {
            messageTo(self, "messageEncounterTaskCheckCreatureDistance", null, 15, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        if (hasObjVar(self, objvarOnCreatureOwner) && hasObjVar(self, objvarOnCreatureQuestCrc) && hasObjVar(self, objvarOnCreatureTaskId))
        {
            obj_id player = getObjIdObjVar(self, objvarOnCreatureOwner);
            int questCrc = getIntObjVar(self, objvarOnCreatureQuestCrc);
            int taskId = getIntObjVar(self, objvarOnCreatureTaskId);
            dictionary params = new dictionary();
            params.put("source", self);
            params.put("questCrc", questCrc);
            params.put("taskId", taskId);
            messageTo(player, "messageEncounterTaskCreatureDied", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int messageEncounterCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
