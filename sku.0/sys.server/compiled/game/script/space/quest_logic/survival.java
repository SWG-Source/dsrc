package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.space_transition;
import script.library.utils;
import script.library.ship_ai;
import script.library.prose;

public class survival extends script.base_script
{
    public survival()
    {
    }
    public static final string_id SID_TARGETS_REMAINING = new string_id("space/quest", "destroy_duty_targets_remaining");
    public static final string_id SID_ABANDONED_SURVIVAL = new string_id("space/quest", "survival_abandoned");
    public static final String SOUND_SPAWN_WAVE = "clienteffect/ui_quest_spawn_wave.cef";
    public static final String SOUND_DESTROYED_WAVE = "clienteffect/ui_quest_destroyed_wave.cef";
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
        setObjVar(self, "transPoint", questInfo.getString("transmissionPoint"));
        setObjVar(self, "totalTransTime", questInfo.getInt("totalTransmissionTime"));
        setObjVar(self, "transStart", questInfo.getInt("transmissionStartDelay"));
        setObjVar(self, "delayToAttack", questInfo.getInt("delayToFirstAttack"));
        setObjVar(self, "numUpdates", questInfo.getInt("timesToUpdateProgress"));
        int numAttackShipLists = questInfo.getInt("numAttackShipLists");
        setObjVar(self, "numAttackShipLists", numAttackShipLists);
        for (int i = 0; i < numAttackShipLists; i++)
        {
            String[] attackShips = dataTableGetStringColumn(qTable, "attackShips" + (i + 1));
            space_quest.cleanArray(self, "attackShips" + (i + 1), attackShips);
        }
        setObjVar(self, "attackPeriod", questInfo.getInt("attackPeriod"));
        setObjVar(self, "attackListType", questInfo.getInt("attackListType"));
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
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
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
                clearMissionWaypoint(self);
                space_quest.showQuestUpdate(self, SID_ABANDONED_SURVIVAL);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_CONTINUE;
        }
        String transPoint = getStringObjVar(self, "transPoint");
        if (transPoint == null)
        {
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(transPoint, ":");
        String scene = st.nextToken();
        transPoint = st.nextToken();
        if ((getCurrentSceneName()).startsWith(scene))
        {
            registerTransLocation(self, player, transPoint);
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 1, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void registerTransLocation(obj_id self, obj_id player, String destNav) throws InterruptedException
    {
        obj_id navPoint = space_quest.findQuestLocation(self, player, destNav, "nav");
        if (isIdValid(navPoint))
        {
            location loc = getLocation(navPoint);
            transform wptrans = getTransform_o2w(navPoint);
            obj_id waypoint = getObjIdObjVar(self, "survival_waypoint");
            if (!isIdValid(waypoint))
            {
                waypoint = createWaypointInDatapad(player, loc);
            }
            if (isIdValid(waypoint))
            {
                setWaypointVisible(waypoint, true);
                setWaypointActive(waypoint, true);
                setWaypointLocation(waypoint, loc);
                setWaypointName(waypoint, "Mission Objective");
                setWaypointColor(waypoint, "space");
                setObjVar(self, "survival_waypoint", waypoint);
            }
            setObjVar(self, "survival_transform", wptrans);
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "found_loc");
            questUpdate(self, foundLocation);
            questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, 1, loc);
            String name = questName + ":" + destNav;
            setObjVar(self, "loc", name);
            setObjVar(self, "locl", loc);
            addLocationTarget3d(player, name, loc, space_quest.PATROL_NAV_RADIUS);
        }
        setObjVar(self, "initialized", 1);
    }
    public int arrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        String name = params.getString("name");
        if (!name.equals(getStringObjVar(self, "loc")))
        {
            return SCRIPT_CONTINUE;
        }
        space_quest._setQuestInProgress(self, true);
        string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "arrived_at_loc");
        questUpdate(self, foundLocation);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 2, player);
            if (questIsTaskActive(questid, 1, player))
            {
                questCompleteTask(questid, 1, player);
            }
        }
        messageTo(self, "beginTransmission", params, getIntObjVar(self, "transStart"), false);
        messageTo(self, "launchFirstAttack", params, getIntObjVar(self, "delayToAttack"), false);
        messageTo(self, "validateDistance", null, 30.f, false);
        return SCRIPT_CONTINUE;
    }
    public int beginTransmission(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id transStart = new string_id("spacequest/" + questType + "/" + questName, "transmission_start");
        space_quest.sendQuestMessage(player, transStart);
        int transLength = getIntObjVar(self, "totalTransTime");
        int fifthLength = transLength / getIntObjVar(self, "numUpdates");
        dictionary outp = new dictionary();
        outp.put("time", 0);
        outp.put("fifth", fifthLength);
        outp.put("total", transLength);
        messageTo(self, "updateTrans", outp, fifthLength, false);
        return SCRIPT_CONTINUE;
    }
    public int updateTrans(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int time = params.getInt("time");
        int fifth = params.getInt("fifth");
        int total = params.getInt("total");
        int updates = params.getInt("updates") + 1;
        if (updates < getIntObjVar(self, "numUpdates"))
        {
            time += fifth;
            float pct_received = (float)time / (float)total;
            int ipct_received = (int)(pct_received * 100.f);
            string_id trans_update = new string_id("spacequest/" + questType + "/" + questName, "trans_update");
            prose_package pp = prose.getPackage(trans_update, ipct_received);
            space_quest.sendQuestMessage(player, pp);
            params.put("updates", updates);
            params.put("time", time);
            messageTo(self, "updateTrans", params, fifth, false);
        }
        else 
        {
            string_id transmissionReceived = new string_id("spacequest/" + questType + "/" + questName, "transmission_received");
            space_quest.sendQuestMessage(player, transmissionReceived);
            messageTo(self, "endMission", null, 5.f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int endMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id done = new string_id("spacequest/" + questType + "/" + questName, "mission_complete");
        questUpdate(self, done);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        questCompleted(self);
        return SCRIPT_CONTINUE;
    }
    public int validateDistance(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id pship = space_transition.getContainingShip(player);
        location loc = getLocationObjVar(self, "locl");
        float dist = getDistance(getLocation(pship), loc);
        boolean ok = true;
        if (dist > 2000.f)
        {
            ok = false;
        }
        if (hasObjVar(self, "outofrange"))
        {
            if (ok)
            {
                removeObjVar(self, "outofrange");
            }
            else 
            {
                string_id rangemsg = new string_id("spacequest/" + questType + "/" + questName, "outofrange_failed");
                space_quest.sendQuestMessage(player, rangemsg);
                questFailed(self);
            }
        }
        else 
        {
            if (!ok)
            {
                string_id rangemsg = new string_id("spacequest/" + questType + "/" + questName, "outofrange");
                space_quest.sendQuestMessage(player, rangemsg);
                setObjVar(self, "outofrange", 1);
            }
        }
        messageTo(self, "validateDistance", null, 30.f, false);
        return SCRIPT_CONTINUE;
    }
    public void clearMissionWaypoint(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id waypoint = getObjIdObjVar(self, "survival_waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(self, "survival_waypoint");
        dictionary params = new dictionary();
        String loc = getStringObjVar(self, "loc");
        if (loc != null)
        {
            removeLocationTarget(player, loc);
        }
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
    }
    public void questFailed(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestFailed(player, self);
    }
    public void questAborted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestAborted(player, self);
    }
    public int removeQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if ((questid != 0) && questIsQuestActive(questid, player))
        {
            questCompleteQuest(questid, player);
        }
        space_quest._removeQuest(player, self);
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
        questAborted(self);
        return SCRIPT_CONTINUE;
    }
    public void questUpdate(obj_id self, string_id update_id) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id update_prefix = new string_id("spacequest/" + questType + "/" + questName, "quest_update");
        prose_package pp = prose.getPackage(update_prefix, update_id);
        space_quest.sendQuestMessage(player, pp);
    }
    public int launchFirstAttack(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "first_attack");
        questUpdate(self, foundLocation);
        messageTo(self, "launchAttack", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int launchAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "initialized"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (hasObjVar(self, "wave1") && hasObjVar(self, "wave2"))
        {
            setObjVar(self, "new_wave_pending", 1);
            return SCRIPT_CONTINUE;
        }
        int wavenum = 0;
        if (hasObjVar(self, "wave1"))
        {
            wavenum = 2;
        }
        else 
        {
            wavenum = 1;
        }
        int numAttackShipLists = getIntObjVar(self, "numAttackShipLists");
        int attack = getIntObjVar(self, "attackindex");
        if ((attack < 1) || (attack > numAttackShipLists))
        {
            attack = 1;
        }
        setObjVar(self, "attackindex", attack + 1);
        if (getIntObjVar(self, "attackListType") == 1)
        {
            attack = rand(1, numAttackShipLists);
        }
        String[] shipList = getStringArrayObjVar(self, "attackShips" + attack);
        int count = shipList.length;
        int k = 0;
        obj_id[] targets = null;
        obj_id[] oldtargets = getObjIdArrayObjVar(self, "targets");
        if (oldtargets == null)
        {
            targets = new obj_id[count];
        }
        else 
        {
            targets = new obj_id[count + oldtargets.length];
            k = oldtargets.length;
            for (int i = 0; i < oldtargets.length; i++)
            {
                targets[i] = oldtargets[i];
            }
        }
        int squad = ship_ai.squadCreateSquadId();
        int j = 0;
        for (int i = k; i < count + k; i++)
        {
            transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
            float dist = rand(1000.f, 1200.f);
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            gloc = gloc.yaw_l(3.14f);
            vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
            vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
            vector vd = vi.add(vj);
            gloc = gloc.move_p(vd);
            obj_id newship = space_create.createShipHyperspace(shipList[j], gloc);
            ship_ai.unitSetLeashDistance(newship, 16000);
            if (count > 3)
            {
                ship_ai.unitSetSquadId(newship, squad);
            }
            attachScript(newship, "space.quest_logic.dynamic_ship");
            attachScript(newship, "space.quest_logic.destroyduty_ship");
            targets[i] = newship;
            setObjVar(newship, "quest", self);
            space_quest._addMissionCriticalShip(player, self, newship);
            setObjVar(newship, "objMissionOwner", player);
            ship_ai.unitAddExclusiveAggro(newship, player);
            obj_id playership = space_transition.getContainingShip(player);
            dictionary dict = new dictionary();
            dict.put("player", space_transition.getContainingShip(player));
            dict.put("newship", newship);
            messageTo(self, "attackPlayerShip", dict, 4.0f, false);
            setObjVar(newship, "wave", wavenum);
            j++;
            if (j >= shipList.length)
            {
                j = 0;
            }
        }
        setObjVar(self, "targets", targets);
        setObjVar(self, "wave" + wavenum, count);
        if (count > 3)
        {
            ship_ai.squadSetFormationRandom(squad);
        }
        string_id attack_notify = new string_id("spacequest/" + questType + "/" + questName, "attack_notify");
        space_quest.sendQuestMessage(player, attack_notify);
        playClientEffectObj(player, SOUND_SPAWN_WAVE, player, "");
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        messageTo(self, "launchAttack", null, attackPeriod, false);
        return SCRIPT_CONTINUE;
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id ship = getObjIdObjVar(self, "target");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id deadship = params.getObjId("ship");
        obj_id targets[] = getObjIdArrayObjVar(self, "targets");
        int deadships = getIntObjVar(self, "deadships");
        boolean launchWave = false;
        for (int i = 0; i < targets.length; i++)
        {
            if (deadship == targets[i])
            {
                deadships++;
                setObjVar(self, "deadships", deadships);
                space_quest._removeMissionCriticalShip(player, self, deadship);
                int shipswave = getIntObjVar(deadship, "wave");
                int wavecount = getIntObjVar(self, "wave" + shipswave);
                wavecount--;
                if (wavecount <= 0)
                {
                    removeObjVar(self, "wave" + shipswave);
                    launchWave = true;
                }
                else 
                {
                    setObjVar(self, "wave" + shipswave, wavecount);
                }
                if (deadships == targets.length)
                {
                    playClientEffectObj(player, SOUND_DESTROYED_WAVE, player, "");
                    int rewardships = getIntObjVar(self, "rewardships");
                    rewardships += deadships;
                    setObjVar(self, "rewardships", rewardships);
                    removeObjVar(self, "targets");
                    removeObjVar(self, "deadships");
                    string_id attack_notify = new string_id("spacequest/" + questType + "/" + questName, "attack_stopped");
                    space_quest.sendQuestMessage(player, attack_notify);
                    int attackCount = getIntObjVar(self, "attackCount");
                    attackCount++;
                    setObjVar(self, "attackCount", attackCount);
                    return SCRIPT_CONTINUE;
                }
                break;
            }
        }
        if (launchWave && hasObjVar(self, "new_wave_pending"))
        {
            messageTo(self, "launchAttack", null, 0, false);
            removeObjVar(self, "new_wave_pending");
        }
        return SCRIPT_OVERRIDE;
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            cleanupShips(self);
            questFailed(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int attackPlayerShip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id playerShip = params.getObjId("player");
        obj_id attackingShip = params.getObjId("newship");
        ship_ai.spaceAttack(attackingShip, playerShip);
        return SCRIPT_CONTINUE;
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
}
