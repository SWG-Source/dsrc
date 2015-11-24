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
import script.library.buff;
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.utils;
import script.library.ship_ai;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_create;
import script.library.space_transition;

public class spawn extends script.quest.task.ground.base_task
{
    public spawn()
    {
    }
    public static final String dataTableColumnCreatureType = "CREATURE_TYPE";
    public static final String dataTableColumnMinCount = "MIN_COUNT";
    public static final String dataTableColumnMaxCount = "MAX_COUNT";
    public static final String dataTableColumnMinDistance = "MIN_DISTANCE";
    public static final String dataTableColumnMaxDistance = "MAX_DISTANCE";
    public static final String dataTableColumnMinLevelDifference = "MIN_LEVEL_DIFFERENCE";
    public static final String dataTableColumnMaxLevelDifference = "MAX_LEVEL_DIFFERENCE";
    public static final String dataTableColumnGroundStartCombat = "GROUND_START_COMBAT";
    public static final String dataTableColumnDismountPlayer = "DISMOUNT_PLAYER";
    public static final String dataTableColumnMountBlockDuration = "MOUNT_BLOCK_DURATION";
    public static final String dataTableColumnBuffName = "BUFF_NAME";
    public static final String dataTableColumnShipType = "SHIP_TYPE";
    public static final String dataTableColumnShipMinCount = "SHIP_MIN_COUNT";
    public static final String dataTableColumnShipMaxCount = "SHIP_MAX_COUNT";
    public static final String dataTableColumnSpaceStartCombat = "SPACE_START_COMBAT";
    public static final String dataTableColumnHyperspaceBlockDuration = "HYPERSPACE_BLOCK_DURATION";
    public static final String dataTableColumnMinStartDelay = "MIN_START_DELAY";
    public static final String dataTableColumnMaxStartDelay = "MAX_START_DELAY";
    public static final String dataTableColumnChanceToSkip = "CHANCE_TO_SKIP";
    public static final String objvarPlayedTimeEnd = "playedTimeEnd";
    public static final String objvarOnCreatureOwner = "quest.owner";
    public static final String objvarOnCreatureQuestCrc = "quest.questCrc";
    public static final String objvarOnCreatureTaskId = "quest.taskId";
    public static final String objvarOnCreatureCount = "quest.count";
    public static final String taskType = "spawn";
    public static final String dot = ".";
    public static final float cleanupTime = 300.f;
    public static final float cleanupRetryTime = 60.f;
    public static final String scriptSpawnOnCreature = "quest.task.ground.spawn_on_creature";
    public static final String scriptSpawnOnShip = "quest.task.ground.spawn_on_ship";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        int minStartDelay = Math.max(0, groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinStartDelay));
        int maxStartDelay = Math.max(minStartDelay, groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxStartDelay));
        int timeLeft = 0;
        if (minStartDelay > 0 && maxStartDelay > 0)
        {
            timeLeft = rand(minStartDelay, maxStartDelay);
        }
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Spawn will start in " + timeLeft + " seconds.");
        dictionary params = new dictionary();
        params.put("questCrc", questCrc);
        params.put("taskId", taskId);
        float playerPlayedTimeWhenTimerEnds = (float)getPlayerPlayedTime(self) + timeLeft;
        setObjVar(self, baseObjVar + "." + objvarPlayedTimeEnd, playerPlayedTimeWhenTimerEnds);
        messageTo(self, "messageStartQuestSpawn", params, timeLeft, true);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int messageStartQuestSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt("questCrc");
        int taskId = params.getInt("taskId");
        if (questIsTaskActive(questCrc, taskId, self))
        {
            String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
            float playerPlayedTimeWhenTimerEnds = getFloatObjVar(self, baseObjVar + "." + objvarPlayedTimeEnd);
            float timeLeft = playerPlayedTimeWhenTimerEnds - (float)getPlayerPlayedTime(self);
            if (timeLeft > 1)
            {
                messageTo(self, "messageStartQuestSpawn", params, timeLeft, true);
                return SCRIPT_CONTINUE;
            }
            int chanceToSkip = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnChanceToSkip);
            int chanceRoll = rand(0, 99);
            if (chanceToSkip > chanceRoll)
            {
                questCompleteTask(questCrc, taskId, self);
                return SCRIPT_CONTINUE;
            }
            if (isSpaceScene())
            {
                startSpaceQuestSpawn(self, questCrc, taskId);
            }
            else 
            {
                startGroundQuestSpawn(self, questCrc, taskId);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void startGroundQuestSpawn(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String creatureType = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnCreatureType);
        int minCount = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinCount);
        int maxCount = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxCount);
        float minRadius = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinDistance);
        float maxRadius = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxDistance);
        boolean shouldDismountPlayer = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableColumnDismountPlayer, false);
        int mountBlockDuration = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMountBlockDuration);
        String buffName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnBuffName);
        int minLevelDifference = Math.max(0, groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinLevelDifference));
        int maxLevelDifference = Math.max(0, groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxLevelDifference));
        boolean doAutoLeveling = minLevelDifference > 0 || maxLevelDifference > 0;
        location l = null;
        location locTest = getLocation(player);
        if (!isIdValid(locTest.cell))
        {
            location locA = locTest;
            location locB = locTest;
            locA.x -= 5;
            locA.y -= 5;
            locA.z -= 5;
            locB.x += 5;
            locB.y += 5;
            locB.z += 5;
            l = getGoodLocation(1, 1, locA, locB, false, true);
            if (getDistance(l, locTest) < 0.0f || getDistance(l, locTest) > 10.0f)
            {
                questCompleteTask(questCrc, taskId, player);
                return;
            }
        }
        if (shouldDismountPlayer)
        {
            utils.dismountRiderJetpackCheck(player);
        }
        if (mountBlockDuration > 0)
        {
            utils.setScriptVar(player, "mountBlock", getPlayerPlayedTime(player) + mountBlockDuration);
        }
        if (buffName != null && buffName.length() > 0)
        {
            buff.applyBuff(player, buffName);
        }
        if (maxCount < minCount)
        {
            maxCount = minCount;
        }
        int count = rand(minCount, maxCount);
        for (int i = 0; i < count; ++i)
        {
            l = null;
            if (!isIdValid(locTest.cell))
            {
                l = groundquests.getRandom2DLocationAroundPlayer(player, minRadius, maxRadius);
            }
            else 
            {
                String strCell = getCellName(locTest.cell);
                obj_id objBuilding = getTopMostContainer(player);
                l = getGoodLocation(objBuilding, strCell);
            }
            obj_id creature;
            int creatureLevel = 0;
            if (doAutoLeveling)
            {
                int minLevel = Math.max(1, getLevel(player) - minLevelDifference);
                int maxLevel = Math.max(minLevel, getLevel(player) + maxLevelDifference);
                creatureLevel = rand(minLevel, maxLevel);
                if (creatureLevel > 90)
                {
                    creatureLevel = 90;
                }
                else if (creatureLevel < 1)
                {
                    creatureLevel = 1;
                }
                creature = create.createCreature(creatureType, l, creatureLevel, true);
            }
            else 
            {
                creature = create.createCreature(creatureType, l, true);
            }
            setObjVar(creature, objvarOnCreatureOwner, player);
            setObjVar(creature, objvarOnCreatureQuestCrc, questCrc);
            setObjVar(creature, objvarOnCreatureTaskId, taskId);
            setObjVar(creature, objvarOnCreatureCount, i);
            attachScript(creature, scriptSpawnOnCreature);
            if (!hasCondition(creature, CONDITION_CONVERSABLE))
            {
                groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "startGroundQuestSpawn", "Spawned " + creature + "(" + creatureLevel + ") at [" + l.x + ", " + l.y + ", " + l.z + "] to attack " + player);
                boolean shouldStartCombat = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableColumnGroundStartCombat, false);
                if (shouldStartCombat)
                {
                    startCombat(creature, player);
                }
            }
        }
        questCompleteTask(questCrc, taskId, player);
        return;
    }
    public void startSpaceQuestSpawn(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "startSpaceQuestSpawn", "Space spawn started!");
        boolean shouldStartCombat = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableColumnSpaceStartCombat, false);
        if (shouldStartCombat && !space_utils.isPlayerShipAttackable(player))
        {
            groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "startSpaceQuestSpawn", "Spawn failed due to player's ship being unattackable.");
            questFailTask(questCrc, taskId, player);
            return;
        }
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        String shipType = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnShipType);
        if (shipType == null || shipType.length() == 0)
        {
            groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "startSpaceQuestSpawn", "Spawn failed due to starting in space with no shipType defined.");
            questFailTask(questCrc, taskId, player);
            return;
        }
        int hyperspaceBlockDuration = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnHyperspaceBlockDuration);
        if (hyperspaceBlockDuration > 0)
        {
            space_utils.setHyperspaceBlock(player, hyperspaceBlockDuration, true);
        }
        int minCount = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnShipMinCount);
        int maxCount = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnShipMaxCount);
        if (maxCount < minCount)
        {
            maxCount = minCount;
        }
        int squad = ship_ai.squadCreateSquadId();
        int count = rand(minCount, maxCount);
        for (int i = 0; i < count; ++i)
        {
            transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
            float dist = rand(700.f, 800.f);
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            gloc = gloc.yaw_l(3.14f);
            vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
            vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
            vector vd = vi.add(vj);
            gloc = gloc.move_p(vd);
            obj_id newship = space_create.createShipHyperspace(shipType, gloc);
            ship_ai.unitSetLeashDistance(newship, 16000);
            if (count > 3)
            {
                ship_ai.unitSetSquadId(newship, squad);
            }
            attachScript(newship, scriptSpawnOnShip);
            if (shouldStartCombat)
            {
                setObjVar(newship, "objMissionOwner", player);
                ship_ai.unitAddExclusiveAggro(newship, player);
                dictionary dict = new dictionary();
                dict.put("player", space_transition.getContainingShip(player));
                messageTo(newship, "spawnAttackPlayerShip", dict, 4.0f, false);
            }
        }
        if (count > 3)
        {
            ship_ai.squadSetFormationRandom(squad);
        }
        questCompleteTask(questCrc, taskId, player);
        return;
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + " task failed.");
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
