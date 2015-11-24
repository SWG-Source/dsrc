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
import script.library.space_transition;
import script.library.space_utils;
import script.library.utils;
import script.library.ship_ai;
import script.library.prose;
import script.library.money;

public class escort_duty extends script.base_script
{
    public escort_duty()
    {
    }
    public static final string_id SID_ABANDONED_MISSION = new string_id("space/quest", "destroy_abandoned");
    public static final string_id SID_ABANDONED_DUTY = new string_id("space/quest", "mission_abandoned");
    public static final string_id SID_TARGETS_REMAINING = new string_id("space/quest", "destroy_duty_targets_remaining");
    public static final string_id SID_ESCORT_REWARD = new string_id("space/quest", "escort_reward");
    public static final String SOUND_SPAWN_ESCORT = "clienteffect/ui_quest_spawn_escort.cef";
    public static final String SOUND_SPAWN_WAVE = "clienteffect/ui_quest_spawn_wave.cef";
    public static final String SOUND_DESTROYED_WAVE = "clienteffect/ui_quest_destroyed_wave.cef";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
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
        String[] rawEscortShipTypes = dataTableGetStringColumn(qTable, "escortShipTypes");
        if (rawEscortShipTypes == null)
        {
            rawEscortShipTypes = new String[1];
            rawEscortShipTypes[0] = questInfo.getString("escortShipType");
            if (rawEscortShipTypes == null)
            {
                sendSystemMessageTestingOnly(player, "Debug: escortShipTypes are missing from " + qTable);
                return SCRIPT_CONTINUE;
            }
        }
        space_quest.cleanArray(self, "escortShipTypes", rawEscortShipTypes);
        String[] rawEscortPoints = dataTableGetStringColumn(qTable, "escortPoints");
        if (rawEscortPoints == null)
        {
            rawEscortPoints = dataTableGetStringColumn(qTable, "escortPath");
            if (rawEscortPoints == null)
            {
                sendSystemMessageTestingOnly(player, "Debug: escortPoints are missing from " + qTable);
                return SCRIPT_CONTINUE;
            }
        }
        space_quest.cleanArray(self, "escortPoints", rawEscortPoints);
        setObjVar(self, "numResponses", questInfo.getInt("numResponses"));
        int numAttackShipLists = questInfo.getInt("numAttackShipLists");
        setObjVar(self, "numAttackShipLists", numAttackShipLists);
        for (int i = 0; i < numAttackShipLists; i++)
        {
            String[] attackShips = dataTableGetStringColumn(qTable, "attackShips" + (i + 1));
            space_quest.cleanArray(self, "attackShips" + (i + 1), attackShips);
        }
        setObjVar(self, "attackPeriod", questInfo.getInt("attackPeriod"));
        setObjVar(self, "attackCount", 0);
        setObjVar(self, "attackListType", questInfo.getInt("attackListType"));
        setObjVar(self, "reward", questInfo.getInt("reward"));
        setObjVar(self, "killReward", questInfo.getInt("killReward"));
        setObjVar(self, "noDistancePolling", questInfo.getInt("noDistancePolling"));
        int destroyIsSuccess = questInfo.getInt("destroyIsSuccess");
        if (destroyIsSuccess == 1)
        {
            setObjVar(self, "destroyIsSuccess", 1);
        }
        setObjVar(self, "customDistanceMessages", questInfo.getInt("customDistanceMessages"));
        setObjVar(self, "attackcode", 1);
        setObjVar(self, "damageMultiplier", questInfo.getInt("damageMultiplier"));
        setObjVar(self, "hateModifier", questInfo.getInt("hateModifier"));
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
                clearMissionWaypoint(self);
                if (hasObjVar(self, space_quest.QUEST_DUTY))
                {
                    space_quest.showQuestUpdate(self, SID_ABANDONED_DUTY);
                    space_quest.setQuestWon(player, self);
                }
                else 
                {
                    space_quest.showQuestUpdate(self, SID_ABANDONED_MISSION);
                    space_quest.setQuestFailed(player, self, false);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_CONTINUE;
        }
        buildRandomNavList(self);
        String[] escortPoints = getStringArrayObjVar(self, "escortPoints");
        if (escortPoints == null)
        {
            return SCRIPT_CONTINUE;
        }
        String destPoint = escortPoints[0];
        java.util.StringTokenizer st = new java.util.StringTokenizer(destPoint, ":");
        String scene = st.nextToken();
        destPoint = st.nextToken();
        if ((getCurrentSceneName()).startsWith(scene))
        {
            registerEscortLocation(self, player, destPoint);
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
        return SCRIPT_CONTINUE;
    }
    public void buildRandomNavList(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String[] escortPoints = getStringArrayObjVar(self, "escortPoints");
        if (escortPoints == null)
        {
            return;
        }
        int i = rand(0, escortPoints.length - 1);
        int k = 0;
        String[] preSortPoints = new String[escortPoints.length];
        for (int j = i; j < escortPoints.length; j++)
        {
            preSortPoints[k++] = escortPoints[j];
        }
        for (int j = 0; j < i; j++)
        {
            preSortPoints[k++] = escortPoints[j];
        }
        if (rand() < 0.25)
        {
            String[] sortedPoints = new String[escortPoints.length];
            for (int j = preSortPoints.length - 1; j >= 0; j--)
            {
                sortedPoints[j] = preSortPoints[j];
            }
            setObjVar(self, "escortPoints", sortedPoints);
        }
        else 
        {
            setObjVar(self, "escortPoints", preSortPoints);
        }
    }
    public void registerEscortLocation(obj_id self, obj_id player, String destNav) throws InterruptedException
    {
        obj_id navPoint = space_quest.findQuestLocation(self, player, destNav, "nav");
        if (isIdValid(navPoint))
        {
            location loc = getLocation(navPoint);
            transform wptrans = getTransform_o2w(navPoint);
            obj_id waypoint = getObjIdObjVar(self, "escort_waypoint");
            if (!isIdValid(waypoint))
            {
                waypoint = createWaypointInDatapad(player, loc);
            }
            if (isIdValid(waypoint))
            {
                setWaypointVisible(waypoint, true);
                setWaypointActive(waypoint, true);
                setWaypointLocation(waypoint, loc);
                setWaypointName(waypoint, "Escort Rendezvous");
                setWaypointColor(waypoint, "space");
                setObjVar(self, "escort_waypoint", waypoint);
            }
            setObjVar(self, "escort_transform", wptrans);
            space_quest._setQuestInProgress(self);
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "found_loc");
            dutyUpdate(self, foundLocation);
            questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, 1, loc);
            removeObjVar(self, "escorting");
            String name = questName + ":" + destNav;
            setObjVar(self, "loc", name);
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
        if (hasObjVar(self, "escorting"))
        {
            return SCRIPT_CONTINUE;
        }
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        location loc = params.getLocation("loc");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        String[] escortPoints = getStringArrayObjVar(self, "escortPoints");
        String destNav = escortPoints[0];
        java.util.StringTokenizer st = new java.util.StringTokenizer(destNav, ":");
        String scene = st.nextToken();
        destNav = st.nextToken();
        destNav = questName + ":" + destNav;
        if (destNav.equals(name))
        {
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "arrived_at_loc");
            dutyUpdate(self, foundLocation);
            int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
            if (questid != 0)
            {
                questActivateTask(questid, 2, player);
                if (questIsTaskActive(questid, 1, player))
                {
                    questCompleteTask(questid, 1, player);
                }
            }
            play2dNonLoopingMusic(player, space_quest.MUSIC_QUEST_ESCORT_ARRIVAL);
            messageTo(self, "warpShipDelay", params, 5 + rand(1, 5), false);
        }
        return SCRIPT_CONTINUE;
    }
    public int warpShipDelay(obj_id self, dictionary params) throws InterruptedException
    {
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        location loc = params.getLocation("loc");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        clearMissionWaypoint(self);
        obj_id ship = warpInEscortShip(self);
        ship_ai.unitSetLeashDistance(ship, 16000);
        setObjVar(self, "ship", ship);
        setObjVar(ship, "intNoPlayerDamage", 1);
        setLookAtTarget(player, ship);
        space_quest._addMissionCriticalShip(player, self, ship);
        setObjVar(ship, "objMissionOwner", player);
        ship_ai.unitAddExclusiveAggro(ship, player);
        playClientEffectObj(player, SOUND_SPAWN_ESCORT, player, "");
        int dmult = getIntObjVar(self, "damageMultiplier");
        if (dmult < 1)
        {
            dmult = 1;
        }
        setObjVar(ship, "damageMultiplier", dmult);
        space_utils.matchEngineSpeed(player, ship, .5f, true);
        transform[] translist = getEscortTransforms(self);
        ship_ai.spacePatrol(ship, translist);
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        attachScript(ship, "space.quest_logic.escort_target");
        dictionary outparams = new dictionary();
        outparams.put("quest", self);
        outparams.put("loc", getLocationObjVar(self, "last_loc"));
        outparams.put("player", player);
        messageTo(ship, "registerDestination", outparams, 1.f, false);
        messageTo(self, "sayHello", null, 2.f, false);
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        if (attackPeriod > 0)
        {
            dictionary outparamss = new dictionary();
            outparamss.put("code", getIntObjVar(self, "attackcode"));
            messageTo(self, "launchAttack", outparamss, attackPeriod, false);
        }
        setObjVar(self, "escorting", 1);
        return SCRIPT_CONTINUE;
    }
    public int sayHello(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "ship");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int reasons = getIntObjVar(self, "numResponses");
        string_id hello = new string_id("spacequest/" + questType + "/" + questName, "reason_" + rand(1, reasons));
        String cargo = getStringObjVar(ship, "cargo");
        string_id cargo_id = new string_id("space/cargo", cargo);
        if (localize(cargo_id) == null)
        {
            cargo_id = new string_id("space/cargo", "some_cargo");
        }
        prose_package pp = prose.getPackage(hello, cargo_id);
        space_quest.groupTaunt(ship, player, pp);
        return SCRIPT_CONTINUE;
    }
    public obj_id warpInEscortShip(obj_id self) throws InterruptedException
    {
        String[] shipTypes = getStringArrayObjVar(self, "escortShipTypes");
        String shipType = shipTypes[rand(0, shipTypes.length - 1)];
        transform trans = getTransformObjVar(self, "escort_transform");
        transform spawnLoc = space_quest.getRandomPositionInSphere(trans, 100, 200);
        return space_create.createShipHyperspace(shipType, spawnLoc);
    }
    public transform[] getEscortTransforms(obj_id self) throws InterruptedException
    {
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return null;
        }
        obj_id[] points = utils.getObjIdArrayScriptVar(questManager, "nav_list");
        String[] escortPoints = getStringArrayObjVar(self, "escortPoints");
        transform[] translist = new transform[escortPoints.length];
        for (int j = 0; j < escortPoints.length; j++)
        {
            for (int i = 0; i < points.length; i++)
            {
                String pointName = getStringObjVar(points[i], "nav_name");
                String eName = escortPoints[j];
                java.util.StringTokenizer st = new java.util.StringTokenizer(eName, ":");
                String scene = st.nextToken();
                eName = st.nextToken();
                if ((pointName != null) && pointName.equals(eName))
                {
                    translist[j] = getTransform_o2w(points[i]);
                    if (j + 1 == escortPoints.length)
                    {
                        setObjVar(self, "last_loc", getLocation(points[i]));
                    }
                    break;
                }
            }
        }
        return translist;
    }
    public int escortComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id ship = params.getObjId("ship");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        messageTo(ship, "missionAbort", null, 2.f, false);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int reasons = getIntObjVar(self, "numResponses");
        string_id bye = new string_id("spacequest/" + questType + "/" + questName, "goodbye_" + rand(1, reasons));
        prose_package pp = prose.getPackage(bye, 0);
        space_quest.groupTaunt(ship, player, pp);
        string_id complete = new string_id("spacequest/" + questType + "/" + questName, "complete");
        dutyUpdate(self, complete);
        removeObjVar(self, "ship");
        removeObjVar(self, "escorting");
        removeObjVar(self, "initialized");
        removeObjVar(self, "wave1");
        removeObjVar(self, "wave2");
        setObjVar(self, "attackcode", getIntObjVar(self, "attackcode") + 1);
        obj_id[] targets = getObjIdArrayObjVar(self, "targets");
        if (targets != null)
        {
            for (int i = 0; i < targets.length; i++)
            {
                if (isIdValid(targets[i]))
                {
                    destroyObjectHyperspace(targets[i]);
                }
            }
            removeObjVar(self, "targets");
        }
        removeObjVar(self, "deadships");
        int reward = getIntObjVar(self, "reward");
        int rewardships = getIntObjVar(self, "rewardships");
        removeObjVar(self, "rewardships");
        int killreward = getIntObjVar(self, "killReward");
        rewardships *= killreward;
        reward += rewardships;
        money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, reward);
        pp = prose.getPackage(SID_ESCORT_REWARD, reward);
        sendQuestSystemMessage(player, pp);
        int escortCount = getIntObjVar(self, "escortCount");
        escortCount++;
        setObjVar(self, "escortCount", escortCount);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if ((getCurrentSceneName()).startsWith(questZone))
        {
            dictionary outparams = new dictionary();
            outparams.put("player", player);
            messageTo(self, "initializedQuestPlayer", outparams, 10.f, false);
        }
        return SCRIPT_OVERRIDE;
    }
    public int escortFailed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id ship = params.getObjId("ship");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int reason = params.getInt("reason");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (reason == 0)
        {
            string_id failed = new string_id("spacequest/" + questType + "/" + questName, "failed_protect");
            dutyUpdate(self, failed);
            questFailed(self, false);
        }
        else if (reason == 1)
        {
            string_id failed = new string_id("spacequest/" + questType + "/" + questName, "failed_destroy");
            dutyUpdate(self, failed);
            if (!hasObjVar(self, space_quest.QUEST_DUTY) && hasObjVar(self, "destroyIsSuccess"))
            {
                questFailed(self, true);
            }
            else 
            {
                questFailed(self, true);
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public void clearMissionWaypoint(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id waypoint = getObjIdObjVar(self, "escort_waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(self, "escort_waypoint");
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
    public void questFailed(obj_id self, boolean split) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestFailed(player, self, split);
    }
    public void questAborted(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearMissionWaypoint(self);
        space_quest.setQuestWon(player, self);
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = getObjIdObjVar(self, "ship");
        if (isIdValid(ship) && (ship != null) && exists(ship))
        {
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            string_id abort = new string_id("spacequest/" + questType + "/" + questName, "abort");
            prose_package pp = prose.getPackage(abort, 0);
            space_quest.groupTaunt(ship, player, pp);
            messageTo(ship, "missionAbort", null, 2.f, false);
        }
        questAborted(self);
        return SCRIPT_CONTINUE;
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
    public int launchAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "initialized") || !hasObjVar(self, "escorting") || !hasObjVar(self, "ship"))
        {
            return SCRIPT_CONTINUE;
        }
        int code = params.getInt("code");
        if (code != getIntObjVar(self, "attackcode"))
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
        obj_id ship = getObjIdObjVar(self, "ship");
        int numAttackShipLists = getIntObjVar(self, "numAttackShipLists");
        if ((ship == null) || !isIdValid(ship) || !exists(ship))
        {
            return SCRIPT_CONTINUE;
        }
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
            transform gloc = getTransform_o2w(ship);
            float dist = rand(1000.f, 1200.f);
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            gloc = gloc.yaw_l(3.14f);
            vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
            vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
            vector vd = vi.add(vj);
            gloc = gloc.move_p(vd);
            obj_id newship = space_create.createShipHyperspace(shipList[j], gloc);
            float fltHateMod = 500.0f;
            if (hasObjVar(newship, "hateModifier"))
            {
                int hateMod = getIntObjVar(newship, "hateModifier");
                fltHateMod = (float)(hateMod);
                if (fltHateMod <= 0)
                {
                    fltHateMod = 500.0f;
                }
            }
            ship_ai.unitSetLeashDistance(newship, 16000);
            if (count > 3)
            {
                ship_ai.unitSetSquadId(newship, squad);
            }
            attachScript(newship, "space.quest_logic.dynamic_ship");
            attachScript(newship, "space.quest_logic.destroyduty_ship");
            targets[i] = newship;
            ship_ai.unitIncreaseHate(newship, ship, fltHateMod, 10.0f, 20);
            setObjVar(newship, "quest", self);
            space_quest._addMissionCriticalShip(player, self, newship);
            setObjVar(newship, "objMissionOwner", player);
            ship_ai.unitAddExclusiveAggro(newship, player);
            setObjVar(newship, "wave", wavenum);
            j++;
            if (j >= shipList.length)
            {
                j = 0;
            }
        }
        setObjVar(self, "wave" + wavenum, count);
        if (count > 3)
        {
            ship_ai.squadSetFormationRandom(squad);
        }
        setObjVar(self, "targets", targets);
        string_id attack_notify = new string_id("spacequest/" + questType + "/" + questName, "attack_notify");
        space_quest.sendQuestMessage(player, attack_notify);
        playClientEffectObj(player, SOUND_SPAWN_WAVE, player, "");
        int reasons = getIntObjVar(self, "numResponses");
        string_id hello = new string_id("spacequest/" + questType + "/" + questName, "panic_" + rand(1, reasons));
        prose_package pp = prose.getPackage(hello, 0);
        space_quest.groupTaunt(ship, player, pp);
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        dictionary outparamss = new dictionary();
        outparamss.put("code", getIntObjVar(self, "attackcode"));
        messageTo(self, "launchAttack", outparamss, attackPeriod, false);
        return SCRIPT_CONTINUE;
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id ship = getObjIdObjVar(self, "ship");
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
                    int reasons = getIntObjVar(self, "numResponses");
                    string_id hello = new string_id("spacequest/" + questType + "/" + questName, "thanks_" + rand(1, reasons));
                    prose_package pp = prose.getPackage(hello, 0);
                    space_quest.groupTaunt(ship, player, pp);
                    int attackCount = getIntObjVar(self, "attackCount");
                    attackCount++;
                    setObjVar(self, "attackCount", attackCount);
                    checkSpecialEvent(self, player, attackCount);
                    return SCRIPT_CONTINUE;
                }
                break;
            }
        }
        int remaining = targets.length - deadships;
        prose_package pp = prose.getPackage(SID_TARGETS_REMAINING, remaining);
        space_quest.sendQuestMessage(player, pp);
        if (launchWave && hasObjVar(self, "new_wave_pending"))
        {
            dictionary outparamss = new dictionary();
            outparamss.put("code", getIntObjVar(self, "attackcode"));
            messageTo(self, "launchAttack", outparamss, 0, false);
            removeObjVar(self, "new_wave_pending");
        }
        return SCRIPT_OVERRIDE;
    }
    public boolean checkSpecialEvent(obj_id self, obj_id player, int attackCount) throws InterruptedException
    {
        return false;
    }
    public int removeQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearMissionWaypoint(self);
        obj_id ship = getObjIdObjVar(self, "ship");
        destroyObjectHyperspace(ship);
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
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            cleanupShips(self);
            questFailed(self, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanupShips(obj_id self) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "ship");
        if (isIdValid(ship) && exists(ship))
        {
            destroyObjectHyperspace(ship);
        }
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
