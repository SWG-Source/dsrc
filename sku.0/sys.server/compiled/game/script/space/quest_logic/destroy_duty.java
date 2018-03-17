package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.space_transition;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_create;
import script.library.ship_ai;
import script.library.utils;
import script.library.money;
import script.library.prose;
import script.library.group;

public class destroy_duty extends script.base_script
{
    public destroy_duty()
    {
    }
    public static final string_id SID_TARGET_LOCATED = new string_id("space/quest", "destroy_duty_target_located");
    public static final string_id SID_TARGET_DETECTED = new string_id("space/quest", "destroy_duty_target_detected");
    public static final string_id SID_BOSS_DETECTED = new string_id("space/quest", "destroy_duty_boss_detected");
    public static final string_id SID_TARGETS_REMAINING = new string_id("space/quest", "destroy_duty_targets_remaining");
    public static final string_id SID_ALL_TARGETS_DEAD = new string_id("space/quest", "destroy_duty_targets_dead");
    public static final string_id SID_BOSS_DEAD = new string_id("space/quest", "destroy_duty_boss_dead");
    public static final string_id SID_LEVEL_INCREASE = new string_id("space/quest", "destroy_duty_level_increase");
    public static final string_id SID_LEVEL_BOSS_DEAD = new string_id("space/quest", "destroy_duty_level_boss_dead");
    public static final string_id SID_LEVEL_BOSS = new string_id("space/quest", "destroy_duty_level_boss");
    public static final string_id SID_COMPLETE = new string_id("space/quest", "destroy_duty_complete");
    public static final string_id SID_ROUND_REWARD = new string_id("space/quest", "destroy_duty_round_reward");
    public static final string_id SID_BOSS_REWARD = new string_id("space/quest", "destroy_duty_boss_reward");
    public static final string_id SID_COMPLETE_REWARD = new string_id("space/quest", "destroy_duty_complete_reward");
    public static final string_id SID_TOKEN_REWARD = new string_id("space/quest", "destroy_duty_tokens_received");
    public static final string_id SID_ABANDONED_DESTROY = new string_id("space/quest", "destroy_abandoned");
    public static final string_id WARPOUT_FAILURE = new string_id("space/quest", "warpout_failure");
    public static final String SOUND_SPAWN_BOSS = "clienteffect/ui_quest_spawn_boss.cef";
    public static final String SOUND_SPAWN_WAVE = "clienteffect/ui_quest_spawn_wave.cef";
    public static final String SOUND_DESTROYED_WAVE = "clienteffect/ui_quest_destroyed_wave.cef";
    public static final String SOUND_DESTROYED_ALL = "clienteffect/ui_quest_destroyed_all.cef";
    public static final String MUSIC_BOSS_ARRIVE = "sound/music_com_enter_battle.snd";
    public static final String MUSIC_ROUND_WON = "sound/music_int_complete_neutral.snd";
    public static final String MUSIC_LEVEL_WON = "sound/music_combat_bfield_vict.snd";
    public static final String MUSIC_DANGER = "sound/music_event_danger.snd";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if ((questName == null) || (questType == null))
        {
            return SCRIPT_CONTINUE;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            sendSystemMessageTestingOnly(player, "Debug: Failed to open quest table " + qTable);
            return SCRIPT_CONTINUE;
        }
        int numShipTypes = questInfo.getInt("shipTypes");
        setObjVar(self, "numShipTypes", numShipTypes);
        for (int i = 0; i < numShipTypes; i++)
        {
            String[] ships = dataTableGetStringColumn(qTable, "shipType" + (i + 1));
            space_quest.cleanArray(self, "shipType" + (i + 1), ships);
        }
        setObjVar(self, "reward", questInfo.getInt("reward"));
        setObjVar(self, "wavesPerRound", questInfo.getInt("wavesPerRound"));
        setObjVar(self, "roundsPerLevel", questInfo.getInt("roundsPerLevel"));
        int levelsPerDuty = 5;
        setObjVar(self, "levelsPerDuty", levelsPerDuty);
        setObjVar(self, "bossType", questInfo.getString("bossType"));
        setObjVar(self, "bossFreq", questInfo.getString("bossFrequency"));
        setObjVar(self, "difficulty.count", 1);
        setObjVar(self, "difficulty.basecount", 1);
        setObjVar(self, "difficulty.ship", 0);
        setObjVar(self, "difficulty.level", 0);
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if ((getCurrentSceneName()).startsWith(questZone))
        {
            dictionary outparams = new dictionary();
            outparams.put("player", player);
            messageTo(self, "initializedQuestPlayer", outparams, 1.f, false);
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 0, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int initializedQuestPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        obj_id player = params.getObjId("player");
        obj_id containing_ship = space_transition.getContainingShip(player);
        if (isIdValid(containing_ship))
        {
            String strChassisType = getShipChassisType(containing_ship);
            if ((strChassisType != null) && strChassisType.equals("player_sorosuub_space_yacht"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!(getCurrentSceneName()).startsWith(questZone))
        {
            if (space_quest.isQuestInProgress(self))
            {
                clearTargetWaypoint(self);
                space_quest.showQuestUpdate(self, SID_ABANDONED_DESTROY);
                space_quest.setQuestWon(player, self);
            }
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 1, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        findDutyLocation(self, null);
        return SCRIPT_CONTINUE;
    }
    public int findDutyLocation(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        transform tloc = getTransform_o2w(space_transition.getContainingShip(player));
        location sloc = getLocation(space_transition.getContainingShip(player));
        location o = new location(0.f, 0.f, 0.f);
        location loc = null;
        transform strans = null;
        if (hasObjVar(self, "moredist"))
        {
            removeObjVar(self, "moredist");
            strans = space_quest.getRandomPositionInSphere(tloc, 2000, 2500);
        }
        else 
        {
            strans = space_quest.getRandomPositionInSphere(tloc, 1000, 1400);
        }
        vector v = strans.getPosition_p();
        loc = new location(v.x, v.y, v.z);
        float dist = getDistance(loc, o);
        if (dist >= 7500)
        {
            strans = space_quest.getRandomPositionInSphere(transform.identity, 3000, 7000);
        }
        v = strans.getPosition_p();
        loc = new location(v.x, v.y, v.z);
        setObjVar(self, "strans", strans);
        setObjVar(self, "loc", loc);
        dictionary outparams = new dictionary();
        String name = questName + ":destroy_duty";
        setObjVar(self, "loc", name);
        addLocationTarget3d(player, name, loc, 250);
        obj_id waypoint = getObjIdObjVar(self, "waypoint");
        if (!isIdValid(waypoint))
        {
            waypoint = createWaypointInDatapad(player, loc);
        }
        if (isIdValid(waypoint))
        {
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setWaypointLocation(waypoint, loc);
            setWaypointName(waypoint, "Target Location");
            setWaypointColor(waypoint, "space");
            setObjVar(self, "waypoint", waypoint);
        }
        space_quest._setQuestInProgress(self);
        questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, 1, loc);
        string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "found_loc");
        dutyUpdate(self, foundLocation);
        return SCRIPT_CONTINUE;
    }
    public int arrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String name = params.getString("name");
        if (name.equals(questName + ":destroy_duty"))
        {
            spawnDutyShips(self, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDutyShips(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int numshiptypes = getIntObjVar(self, "numShipTypes");
        String[][] ships = new String[numshiptypes][];
        for (int i = 0; i < numshiptypes; i++)
        {
            ships[i] = getStringArrayObjVar(self, "shipType" + (i + 1));
        }
        int count = getIntObjVar(self, "difficulty.count");
        int basecount = getIntObjVar(self, "difficulty.basecount");
        int ship = getIntObjVar(self, "difficulty.ship");
        transform tloc = getTransformObjVar(self, "strans");
        int squad = ship_ai.squadCreateSquadId();
        obj_id targets[] = null;
        if (hasObjVar(self, "currentBossLevel") && !dutyMission(getStringObjVar(self, "bossType")))
        {
            string_id bossInc = new string_id("spacequest/" + questType + "/" + questName, "boss_detected");
            space_quest.sendQuestMessage(player, bossInc);
            playClientEffectObj(player, SOUND_SPAWN_BOSS, player, "");
            play2dNonLoopingMusic(player, MUSIC_BOSS_ARRIVE);
            String bossType = getStringObjVar(self, "bossType");
            targets = new obj_id[basecount];
            for (int i = 0; i < basecount; i++)
            {
                transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
                float dist = rand(1200.f, 1500.f);
                vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
                gloc = gloc.move_p(n);
                gloc = gloc.yaw_l(3.14f);
                vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
                vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
                vector vd = vi.add(vj);
                gloc = gloc.move_p(vd);
                obj_id newship = space_create.createShipHyperspace(bossType, gloc);
                ship_ai.unitSetLeashDistance(newship, 16000);
                if (basecount > 3)
                {
                    ship_ai.unitSetSquadId(newship, squad);
                }
                attachScript(newship, "space.quest_logic.dynamic_ship");
                targets[i] = newship;
                space_quest._addMissionCriticalShip(player, self, newship);
                setObjVar(newship, "objMissionOwner", player);
                ship_ai.unitAddExclusiveAggro(newship, player);
                obj_id playership = space_transition.getContainingShip(player);
                dictionary dict = new dictionary();
                dict.put("player", space_transition.getContainingShip(player));
                dict.put("newship", newship);
                messageTo(self, "attackPlayerShip", dict, 4.0f, false);
                attachScript(newship, "space.quest_logic.destroyduty_boss");
                setObjVar(newship, "quest", self);
                setObjVar(newship, "player", player);
            }
            setObjVar(self, "targets", targets);
        }
        else if (hasObjVar(self, "currentBossLevel") && dutyMission(getStringObjVar(self, "bossType")))
        {
            string_id bossInc = new string_id("spacequest/" + questType + "/" + questName, "boss_detected");
            space_quest.sendQuestMessage(player, bossInc);
            playClientEffectObj(player, SOUND_SPAWN_BOSS, player, "");
            play2dNonLoopingMusic(player, MUSIC_BOSS_ARRIVE);
            String bossType = getStringObjVar(self, "bossType");
            targets = new obj_id[basecount];
            for (int i = 0; i < basecount; i++)
            {
                transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
                float dist = rand(1200.f, 1500.f);
                vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
                gloc = gloc.move_p(n);
                gloc = gloc.yaw_l(3.14f);
                vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
                vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
                vector vd = vi.add(vj);
                gloc = gloc.move_p(vd);
                String shipString = bossType;
                if (i != 0)
                {
                    shipString = ships[0][ship];
                }
                obj_id newship = space_create.createShipHyperspace(shipString, gloc);
                ship_ai.unitSetLeashDistance(newship, 16000);
                if (basecount > 3)
                {
                    ship_ai.unitSetSquadId(newship, squad);
                }
                attachScript(newship, "space.quest_logic.dynamic_ship");
                targets[i] = newship;
                space_quest._addMissionCriticalShip(player, self, newship);
                setObjVar(newship, "objMissionOwner", player);
                ship_ai.unitAddExclusiveAggro(newship, player);
                obj_id playership = space_transition.getContainingShip(player);
                dictionary dict = new dictionary();
                dict.put("player", space_transition.getContainingShip(player));
                dict.put("newship", newship);
                messageTo(self, "attackPlayerShip", dict, 4.0f, false);
                attachScript(newship, "space.quest_logic.destroyduty_boss");
                setObjVar(newship, "quest", self);
                setObjVar(newship, "player", player);
            }
            setObjVar(self, "targets", targets);
        }
        else 
        {
            boolean behind = false;
            if (rand() < 0.2f)
            {
                behind = true;
                string_id foundTargets = new string_id("spacequest/" + questType + "/" + questName, "targets_behind");
                space_quest.sendQuestMessage(player, foundTargets);
            }
            else 
            {
                string_id foundTargets = new string_id("spacequest/" + questType + "/" + questName, "targets_detected");
                space_quest.sendQuestMessage(player, foundTargets);
            }
            playClientEffectObj(player, SOUND_SPAWN_WAVE, player, "");
            play2dNonLoopingMusic(player, MUSIC_DANGER);
            targets = new obj_id[count];
            int j = 0;
            for (int i = 0; i < count; i++)
            {
                transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
                if (!behind)
                {
                    float dist = rand(600.f, 700.f);
                    vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
                    gloc = gloc.move_p(n);
                    gloc = gloc.yaw_l(3.14f);
                    vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
                    vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
                    vector vd = vi.add(vj);
                    gloc = gloc.move_p(vd);
                }
                else 
                {
                    float dist = rand(250.f, 300.f) * -1.f;
                    vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
                    gloc = gloc.move_p(n);
                    vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-75.f, 75.f));
                    vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-75.f, 75.f));
                    vector vd = vi.add(vj);
                    gloc = gloc.move_p(vd);
                }
                obj_id newship = space_create.createShipHyperspace(ships[j][ship], gloc);
                if(!isValidId(newship) || !isGameObjectTypeOf(getGameObjectType(newship),GOT_ship)) {
                    LOG("DESIGNER_FATAL","QUEST: spacequest/" + questType + "/" + questName + " New ship (" + ships[j][ship] + ":" + newship + ") which " + (isGameObjectTypeOf(getGameObjectType(newship),GOT_ship) ? "is" : "is not") + " a ship could not be created.");
                    continue;
                }
                ship_ai.unitSetLeashDistance(newship, 16000);
                if (count > 3)
                {
                    ship_ai.unitSetSquadId(newship, squad);
                }
                attachScript(newship, "space.quest_logic.dynamic_ship");
                targets[i] = newship;
                space_quest._addMissionCriticalShip(player, self, newship);
                setObjVar(newship, "objMissionOwner", player);
                ship_ai.unitAddExclusiveAggro(newship, player);
                obj_id playership = space_transition.getContainingShip(player);
                dictionary dict = new dictionary();
                dict.put("player", space_transition.getContainingShip(player));
                dict.put("newship", newship);
                messageTo(self, "attackPlayerShip", dict, 4.0f, false);
                attachScript(newship, "space.quest_logic.destroyduty_ship");
                setObjVar(newship, "quest", self);
                j++;
                if (j >= numshiptypes)
                {
                    j = 0;
                }
            }
            setObjVar(self, "targets", targets);
        }
        if ((targets != null) && (targets.length > 3))
        {
            ship_ai.squadSetFormationRandom(squad);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean dutyMission(String bossType) throws InterruptedException
    {
        if (bossType.endsWith("_duty_tier1") || bossType.endsWith("_duty_tier2") || bossType.endsWith("_duty_tier3") || bossType.endsWith("_duty_tier4") || bossType.endsWith("_duty_tier5"))
        {
            return true;
        }
        return false;
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id deadship = params.getObjId("ship");
        obj_id targets[] = getObjIdArrayObjVar(self, "targets");
        int deadships = getIntObjVar(self, "deadships");
        if (targets == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            if (deadship == targets[i])
            {
                deadships++;
                setObjVar(self, "deadships", deadships);
                space_quest._removeMissionCriticalShip(player, self, deadship);
                if (deadships == targets.length)
                {
                    int wavescomplete = getIntObjVar(self, "wavescomplete");
                    wavescomplete++;
                    setObjVar(self, "wavescomplete", wavescomplete);
                    if (hasObjVar(self, "currentBossLevel"))
                    {
                        space_quest.sendQuestMessage(player, SID_BOSS_DEAD);
                        playClientEffectObj(player, SOUND_DESTROYED_ALL, player, "");
                        play2dNonLoopingMusic(player, MUSIC_LEVEL_WON);
                    }
                    else 
                    {
                        space_quest.sendQuestMessage(player, SID_ALL_TARGETS_DEAD);
                        playClientEffectObj(player, SOUND_DESTROYED_WAVE, player, "");
                        play2dNonLoopingMusic(player, MUSIC_ROUND_WON);
                    }
                    nextWave(self, null);
                    return SCRIPT_CONTINUE;
                }
                break;
            }
        }
        int remaining = targets.length - deadships;
        prose_package pp = prose.getPackage(SID_TARGETS_REMAINING, remaining);
        space_quest.sendQuestMessage(player, pp);
        return SCRIPT_OVERRIDE;
    }
    public int nextWave(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int wavescomplete = getIntObjVar(self, "wavescomplete");
        int wavesperround = getIntObjVar(self, "wavesPerRound");
        int reward = getIntObjVar(self, "reward");
        int rounddeadships = getIntObjVar(self, "rounddeadships");
        rounddeadships += getIntObjVar(self, "deadships");
        setObjVar(self, "rounddeadships", rounddeadships);
        removeObjVar(self, "deadships");
        dictionary outparams = new dictionary();
        if ((wavescomplete == wavesperround) || hasObjVar(self, "currentBossLevel"))
        {
            setObjVar(self, "wavescomplete", 0);
            int roundscomplete = getIntObjVar(self, "roundscomplete");
            roundscomplete++;
            setObjVar(self, "roundscomplete", roundscomplete);
            int roundsperlevel = getIntObjVar(self, "roundsPerLevel");
            if ((roundscomplete == roundsperlevel) || hasObjVar(self, "currentBossLevel"))
            {
                if (!hasObjVar(self, "currentBossLevel"))
                {
                    int credits = rounddeadships * reward;
                    outparams.put("type", 1);
                    outparams.put("reward", credits);
                    messageTo(self, "grantReward", outparams, 1.f, false);
                    removeObjVar(self, "rounddeadships");
                    setObjVar(self, "currentBossLevel", 1);
                    outparams.put("type", 1);
                    messageTo(self, "notifyNextWave", outparams, 3.f, false);
                }
                else 
                {
                    int credits = 3 * reward;
                    outparams.put("type", 3);
                    outparams.put("reward", credits);
                    messageTo(self, "grantReward", outparams, 2.f, false);
                    removeObjVar(self, "rounddeadships");
                    int basecount = getIntObjVar(self, "difficulty.basecount");
                    basecount++;
                    setObjVar(self, "difficulty.basecount", basecount);
                    setObjVar(self, "difficulty.count", basecount);
                    setObjVar(self, "difficulty.ship", 0);
                    setObjVar(self, "roundscomplete", 0);
                    setObjVar(self, "moredist", 0);
                    removeObjVar(self, "currentBossLevel");
                    int level = getIntObjVar(self, "difficulty.level");
                    level++;
                    setObjVar(self, "difficulty.level", level);
                    int levelsperduty = getIntObjVar(self, "levelsPerDuty");
                    if (level == levelsperduty)
                    {
                        string_id complete = new string_id("spacequest/" + questType + "/" + questName, "complete");
                        dutyUpdate(self, complete);
                        credits = 25 * reward;
                        outparams.put("type", 2);
                        outparams.put("reward", credits);
                        messageTo(self, "grantReward", outparams, 2.f, false);
                        messageTo(self, "completeDuty", null, 3.f, false);
                        return SCRIPT_CONTINUE;
                    }
                    outparams.put("type", 2);
                    messageTo(self, "notifyNextWave", outparams, 3.f, false);
                }
            }
            else 
            {
                int credits = rounddeadships * reward;
                outparams.put("type", 1);
                outparams.put("reward", credits);
                messageTo(self, "grantReward", outparams, 1.f, false);
                removeObjVar(self, "rounddeadships");
                setObjVar(self, "difficulty.count", getIntObjVar(self, "difficulty.basecount"));
                String[] ships = getStringArrayObjVar(self, "shipType1");
                int ship = getIntObjVar(self, "difficulty.ship");
                ship++;
                if (ship >= ships.length)
                {
                    ship = 0;
                }
                setObjVar(self, "difficulty.ship", ship);
                outparams.put("type", 3);
                messageTo(self, "notifyNextWave", outparams, 3.f, false);
            }
            messageTo(self, "findDutyLocation", null, 6.f, false);
        }
        else 
        {
            int count = getIntObjVar(self, "difficulty.count");
            count++;
            setObjVar(self, "difficulty.count", count);
            messageTo(self, "spawnDutyShips", null, 6.f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int grantReward(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int reward = params.getInt("reward");
        int type = params.getInt("type");
        switch (type)
        {
            case 1:
            prose_package pp = prose.getPackage(SID_ROUND_REWARD, reward);
            sendQuestSystemMessage(player, pp);
            break;
            case 2:
            pp = prose.getPackage(SID_COMPLETE_REWARD, reward);
            sendQuestSystemMessage(player, pp);
            break;
            case 3:
            pp = prose.getPackage(SID_BOSS_REWARD, reward);
            sendQuestSystemMessage(player, pp);
            break;
            default:
            break;
        }
        money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, reward);
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id playerShip = space_transition.getContainingShip(player);
        int tokens = reward / 250;
        if (tokens < 1)
        {
            tokens = 1;
        }
        if (hasObjVar(playerShip, "spaceFaction.overt"))
        {
            int pvpTokens = tokens / 2;
            tokens = tokens + pvpTokens;
        }
        prose_package pt = prose.getPackage(SID_TOKEN_REWARD, tokens);
        sendQuestSystemMessage(player, pt);
        static_item.createNewItemFunction("item_token_duty_space_01_01", pInv, tokens);
        CustomerServiceLog("space_piracy", "Player " + player + " have received " + tokens + " Space Duty Tokens (item_token_duty_space_01_01)");
        return SCRIPT_CONTINUE;
    }
    public int notifyNextWave(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int type = params.getInt("type");
        switch (type)
        {
            case 1:
            string_id level = new string_id("spacequest/" + questType + "/" + questName, "level_boss");
            string_id bossTitle = new string_id("spacequest/" + questType + "/" + questName, "boss_title_" + getIntObjVar(self, "difficulty.basecount"));
            prose_package pp = prose.getPackage(level, bossTitle);
            space_quest.sendQuestMessage(player, pp);
            play2dNonLoopingMusic(player, space_quest.MUSIC_QUEST_BOSS_COMING);
            dutyUpdate(player, level);
            break;
            case 2:
            level = new string_id("spacequest/" + questType + "/" + questName, "level_boss_dead");
            dutyUpdate(player, level);
            break;
            case 3:
            level = new string_id("spacequest/" + questType + "/" + questName, "level_increase");
            dutyUpdate(player, level);
            break;
            default:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int completeDuty(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 1, player))
            {
                questCompleteTask(questid, 1, player);
            }
        }
        clearTargetWaypoint(self);
        space_quest.setQuestWon(player, self);
        return SCRIPT_CONTINUE;
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            clearTargetWaypoint(self);
            cleanupShips(self);
            obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
            space_quest.setQuestFailed(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int attackPlayerShip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id playerShip = params.getObjId("player");
        obj_id attackingShip = params.getObjId("newship");
        if (!isIdValid(attackingShip) || !exists(attackingShip) || !isIdValid(playerShip) || !exists(playerShip))
        {
            return SCRIPT_CONTINUE;
        }
        ship_ai.spaceAttack(attackingShip, playerShip);
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        clearTargetWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
        return SCRIPT_CONTINUE;
    }
    public void clearTargetWaypoint(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id waypoint = getObjIdObjVar(self, "waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(self, "waypoint");
        dictionary params = new dictionary();
        String loc = getStringObjVar(self, "loc");
        if (loc != null)
        {
            removeLocationTarget(player, loc);
        }
    }
    public void dutyUpdate(obj_id self, string_id update_id) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id update_prefix = new string_id("spacequest/" + questType + "/" + questName, "duty_update");
        prose_package pp = prose.getPackage(update_prefix, update_id);
        space_quest.sendQuestMessage(player, pp);
    }
    public void cleanupShips(obj_id self) throws InterruptedException
    {
        obj_id[] targets = getObjIdArrayObjVar(self, "targets");
        if (targets != null)
        {
            for (int i = 0; i < targets.length; i++)
            {
                if (isIdValid(targets[i]) && exists(targets[i]))
                {
                    destroyObjectHyperspace(targets[i]);
                }
            }
        }
    }
    public int warpoutFailure(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "handling_warpout_failure"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "handling_warpout_failure", 1);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        sendQuestSystemMessage(player, WARPOUT_FAILURE);
        clearTargetWaypoint(self);
        cleanupShips(self);
        space_quest.setQuestFailed(player, self);
        return SCRIPT_CONTINUE;
    }
}
