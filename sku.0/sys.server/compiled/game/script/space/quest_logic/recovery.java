package script.space.quest_logic;

import script.*;
import script.library.*;

public class recovery extends script.base_script
{
    public recovery()
    {
    }
    public static final string_id SID_ABANDONED_MISSION = new string_id("space/quest", "mission_abandoned");
    public static final string_id SID_ABANDONED_RECOVERY = new string_id("space/quest", "recovery_abandoned");
    public static final string_id SID_TARGETS_REMAINING = new string_id("space/quest", "destroy_duty_targets_remaining");
    public static final String SOUND_SPAWN_ESCORT = "clienteffect/ui_quest_spawn_escort.cef";
    public static final String SOUND_SPAWN_WAVE = "clienteffect/ui_quest_spawn_wave.cef";
    public static final String SOUND_DESTROYED_ALL = "clienteffect/ui_quest_destroyed_all.cef";
    public static final String SOUND_DESTROYED_WAVE = "clienteffect/ui_quest_destroyed_wave.cef";
    public static final string_id WARPOUT_FAILURE = new string_id("space/quest", "warpout_failure");
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
        setObjVar(self, "targetArrivalDelay", questInfo.getInt("targetArrivalDelay"));
        setObjVar(self, "targetRecoverTime", questInfo.getInt("targetRecoverTime"));
        String recoverFaction = questInfo.getString("recoverFaction");
        setObjVar(self, "recoverFaction", recoverFaction);
        setObjVar(self, "recoveryAppearance", questInfo.getString("recoveryAppearance"));
        String targetShip = questInfo.getString("targetShipType");
        if (targetShip != null)
        {
            setObjVar(self, "targetShipType", targetShip);
        }
        String[] escortShips = dataTableGetStringColumn(qTable, "targetEscort");
        space_quest.cleanArray(self, "escortShips", escortShips);
        int numAttackShipLists = questInfo.getInt("numAttackShipLists");
        setObjVar(self, "numAttackShipLists", numAttackShipLists);
        for (int i = 0; i < numAttackShipLists; i++)
        {
            String[] attackShips = dataTableGetStringColumn(qTable, "attackShips" + (i + 1));
            space_quest.cleanArray(self, "attackShips" + (i + 1), attackShips);
        }
        setObjVar(self, "attackPeriod", questInfo.getInt("attackPeriod"));
        setObjVar(self, "attackListType", questInfo.getInt("attackListType"));
        setObjVar(self, "numResponses", questInfo.getInt("numResponses"));
        String[] escortPath = dataTableGetStringColumn(qTable, "escortPath");
        space_quest.cleanArray(self, "escortPath", escortPath);
        String[] recoveryPath = dataTableGetStringColumn(qTable, "recoveryPath");
        space_quest.cleanArray(self, "recoveryPath", recoveryPath);
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
                space_quest.showQuestUpdate(self, SID_ABANDONED_MISSION);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_CONTINUE;
        }
        String[] escortPoints = getStringArrayObjVar(self, "escortPath");
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
            findTargetStart(self, player, destPoint);
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            setObjVar(self, "taskId", 1);
            questActivateTask(questid, 1, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void findTargetStart(obj_id self, obj_id player, String destNav) throws InterruptedException
    {
        obj_id navPoint = space_quest.findQuestLocation(self, player, destNav, "nav");
        if (isIdValid(navPoint))
        {
            space_quest._setQuestInProgress(self);
            location loc = getLocation(navPoint);
            transform wptrans = getTransform_o2w(navPoint);
            setObjVar(self, "escort_transform", wptrans);
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "arrival_phase_1");
            questUpdate(self, status_update);
            messageTo(self, "warpInTarget", null, getIntObjVar(self, "targetArrivalDelay"), false);
        }
        setObjVar(self, "initialized", 1);
    }
    public int warpInTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if (!(getCurrentSceneName()).startsWith(questZone))
        {
            return SCRIPT_CONTINUE;
        }
        int squad = ship_ai.squadCreateSquadId();
        int squad2 = ship_ai.squadCreateSquadId();
        obj_id ship = createTargetShip(self);
        if (ship == null)
        {
            debugServerConsoleMsg(self, "SCRIPT EXCEPTION: Target ship failed to be created. Type: " + questType + " Name: " + questName);
            Thread.dumpStack();
            throw new InterruptedException();
        }
        ship_ai.unitSetLeashDistance(ship, 16000);
        ship_ai.unitSetSquadId(ship, squad);
        setObjVar(self, "target", ship);
        setLookAtTarget(player, ship);
        setObjVar(ship, "quest", self);
        attachScript(ship, "space.quest_logic.recovery_target");
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        space_quest._addMissionCriticalShip(player, self, ship);
        setObjVar(ship, "objMissionOwner", player);
        ship_ai.unitAddExclusiveAggro(ship, player);
        int dmult = getIntObjVar(self, "damageMultiplier");
        if (dmult < 1)
        {
            dmult = 1;
        }
        setObjVar(ship, "damageMultiplier", dmult);
        float maxspeed = getShipEngineSpeedMaximum(ship);
        if (maxspeed < 15.f)
        {
            setShipEngineSpeedMaximum(ship, 15.f + rand() * 10.f);
        }
        else if (maxspeed > 25.f)
        {
            setShipEngineSpeedMaximum(ship, 23.f + rand() * 5.f);
        }
        messageTo(self, "updateTargetWaypoint", null, 1.f, false);
        transform[] translist = getPathTransforms(self, true);
        ship_ai.unitAddPatrolPath(ship, translist);
        dictionary outparams = new dictionary();
        outparams.put("quest", self);
        outparams.put("loc", getLocationObjVar(self, "last_loc"));
        outparams.put("player", player);
        outparams.put("dest", "escape");
        messageTo(ship, "registerDestination", outparams, 1.f, false);
        playClientEffectObj(player, SOUND_SPAWN_ESCORT, player, "");
        String[] shipTypes = getStringArrayObjVar(self, "escortShips");
        if (shipTypes != null)
        {
            obj_id[] escorts = new obj_id[shipTypes.length];
            obj_id playerShip = space_transition.getContainingShip(player);
            for (int i = 0; i < shipTypes.length; i++)
            {
                transform t = getTransform_o2w(ship);
                transform spawnLoc = space_quest.getRandomPositionInSphere(t, 10, 20);
                escorts[i] = space_create.createShipHyperspace(shipTypes[i], spawnLoc);
                ship_ai.unitSetLeashDistance(escorts[i], 16000);
                ship_ai.unitSetSquadId(escorts[i], squad2);
                space_quest._addMissionCriticalShip(player, self, escorts[i]);
                setObjVar(escorts[i], "objMissionOwner", player);
                ship_ai.unitAddExclusiveAggro(escorts[i], player);
                ship_ai.unitSetAutoAggroImmuneTime(playerShip, 3.0f);
                attachScript(escorts[i], "space.quest_logic.recovery_escort");
            }
            setObjVar(self, "escorts", escorts);
            ship_ai.squadSetLeader(squad2, escorts[0]);
            ship_ai.squadFollow(squad2, ship, new vector(0, 0, 1), 15);
            ship_ai.squadSetGuardTarget(squad2, squad);
            ship_ai.squadSetAttackOrders(squad2, ship_ai.ATTACK_ORDERS_RETURN_FIRE);
            ship_ai.squadSetFormationRandom(squad2);
        }
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "arrival_phase_2");
        questUpdate(self, status_update);
        return SCRIPT_CONTINUE;
    }
    public obj_id createTargetShip(obj_id self) throws InterruptedException
    {
        String shipType = getStringObjVar(self, "targetShipType");
        transform trans = getTransformObjVar(self, "escort_transform");
        transform spawnLoc = space_quest.getRandomPositionInSphere(trans, 100, 200);
        return space_create.createShipHyperspace(shipType, spawnLoc);
    }
    public transform[] getPathTransforms(obj_id self, boolean escort) throws InterruptedException
    {
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return null;
        }
        obj_id[] points = utils.getObjIdArrayScriptVar(questManager, "nav_list");
        String[] pathPoints;
        if (escort)
        {
            pathPoints = getStringArrayObjVar(self, "escortPath");
        }
        else 
        {
            pathPoints = getStringArrayObjVar(self, "recoveryPath");
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int b = 0;
        transform[] translist = new transform[pathPoints.length];
        for (int j = 0; j < pathPoints.length; j++)
        {
            boolean found = false;
            for (int i = 0; i < points.length; i++)
            {
                String pointName = getStringObjVar(points[i], "nav_name");
                String eName = pathPoints[j];
                java.util.StringTokenizer st = new java.util.StringTokenizer(eName, ":");
                String scene = st.nextToken();
                eName = st.nextToken();
                if ((pointName != null) && pointName.equals(eName))
                {
                    found = true;
                    b++;
                    translist[j] = getTransform_o2w(points[i]);
                    if (j + 1 == pathPoints.length)
                    {
                        setObjVar(self, "last_loc", getLocation(points[i]));
                    }
                    break;
                }
            }
            if (!found)
            {
                sendSystemMessageTestingOnly(player, "WARNING: Failed to find nav point " + pathPoints[j] + " specified in nav point list.");
            }
        }
        if (b != translist.length)
        {
            sendSystemMessageTestingOnly(player, "WARNING: Failed to find " + translist.length + " valid nav points for this mission!");
        }
        return translist;
    }
    public int recoverShipDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "target");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "target_disabled");
        space_quest.sendQuestMessage(player, status_update);
        String recoveryAppearance = getStringObjVar(self, "recoveryAppearance");
        if (recoveryAppearance != null)
        {
            string_id angry_disable = new string_id("spacequest/" + questType + "/" + questName, "angry_disable");
            String sad = localize(angry_disable);
            if ((sad != null) && (!sad.equals("")))
            {
                prose_package pp = prose.getPackage(angry_disable, 0);
                space_quest.groupTaunt(ship, player, pp);
            }
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            setObjVar(self, "taskId", 2);
            questActivateTask(questid, 2, player);
            if (questIsTaskActive(questid, 1, player))
            {
                questCompleteTask(questid, 1, player);
            }
        }
        messageTo(self, "recoverPhase1", null, getIntObjVar(self, "targetRecoverTime"), false);
        return SCRIPT_CONTINUE;
    }
    public int recoverPhase1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "target");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if (hasObjVar(self, "recoveryAppearance"))
        {
            setObjVar(ship, "convo.appearance", getStringObjVar(self, "recoveryAppearance"));
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, getCapturePhrase1(self));
        prose_package pp = prose.getPackage(status_update, 0);
        space_quest.groupTaunt(ship, player, pp);
        play2dNonLoopingMusic(player, space_quest.MUSIC_QUEST_CAPTURE_OP);
        ship_ai.squadRemoveUnit(ship);
        status_update = new string_id("spacequest/" + questType + "/" + questName, "capture_started");
        space_quest.sendQuestMessage(player, status_update);
        messageTo(self, "recoverPhase2", null, getIntObjVar(self, "targetRecoverTime"), false);
        return SCRIPT_CONTINUE;
    }
    public String getCapturePhrase1(obj_id self) throws InterruptedException
    {
        return "capture_phase_1";
    }
    public int recoverPhase2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "target");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, getCapturePhrase2(self));
        prose_package pp = prose.getPackage(status_update, 0);
        space_quest.groupTaunt(ship, player, pp);
        status_update = new string_id("spacequest/" + questType + "/" + questName, "capture_complete");
        space_quest.sendQuestMessage(player, status_update);
        obj_id[] targets = ship_ai.unitGetAttackTargetList(ship);
        if (targets != null)
        {
            for (int i = 0; i < targets.length; i++)
            {
                ship_ai.spaceStopAttack(ship, targets[i]);
            }
        }
        if (hasObjVar(self, "recoverFaction"))
        {
            int[] allies = shipGetSpaceFactionAllies(ship);
            int[] enemies = shipGetSpaceFactionEnemies(ship);
            shipSetSpaceFaction(ship, getStringCrc(getStringObjVar(self, "recoverFaction")));
            shipSetSpaceFactionAllies(ship, enemies);
            shipSetSpaceFactionEnemies(ship, allies);
        }
        setObjVar(self, "captured", 1);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            setObjVar(self, "taskId", 3);
            questActivateTask(questid, 3, player);
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        messageTo(self, "startCapturedShipPathing", null, 10.f, false);
        return SCRIPT_CONTINUE;
    }
    public String getCapturePhrase2(obj_id self) throws InterruptedException
    {
        return "capture_phase_2";
    }
    public int startCapturedShipPathing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "target");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_crafting.repairDamage(null, ship, 1.0f);
        attachScript(ship, "space.ai.space_ai");
        transform[] translist = getPathTransforms(self, false);
        ship_ai.unitClearPatrolPath(ship);
        ship_ai.unitAddPatrolPath(ship, translist);
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        space_utils.matchEngineSpeed(player, ship, .5f, true);
        dictionary outparams = new dictionary();
        outparams.put("quest", self);
        outparams.put("loc", getLocationObjVar(self, "last_loc"));
        outparams.put("player", player);
        outparams.put("dest", "recovery");
        messageTo(ship, "registerDestination", outparams, 1.f, false);
        setObjVar(ship, "intNoPlayerDamage", 1);
        setObjVar(self, "attacksok", 1);
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        if (attackPeriod > 0)
        {
            dictionary outparamss = new dictionary();
            outparamss.put("code", getIntObjVar(self, "attackcode"));
            messageTo(self, "launchAttack", outparamss, attackPeriod, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int recoveryComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = params.getObjId("ship");
        string_id update = new string_id("spacequest/" + questType + "/" + questName, "recovery_success");
        questUpdate(self, update);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 3, player))
            {
                questCompleteTask(questid, 3, player);
            }
        }
        string_id abort = new string_id("spacequest/" + questType + "/" + questName, getCompletePhrase(self));
        prose_package pp = prose.getPackage(abort, 0);
        space_quest.groupTaunt(ship, player, pp);
        messageTo(self, "cleanupShipsMsg", null, 2.f, false);
        messageTo(self, "completeQuestMsg", null, 3.f, false);
        return SCRIPT_CONTINUE;
    }
    public String getCompletePhrase(obj_id self) throws InterruptedException
    {
        return "complete";
    }
    public int recoveryFailed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = params.getObjId("target");
        int reason = params.getInt("reason");
        if (reason == 0)
        {
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "failed_escape");
            questUpdate(self, status_update);
        }
        else if (reason == 1)
        {
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "failed_destroy");
            questUpdate(self, status_update);
        }
        cleanupShips(self);
        questFailed(self);
        return SCRIPT_CONTINUE;
    }
    public int updateTargetWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        if(self == null || self == obj_id.NULL_ID || !isIdValid(self)){
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = getObjIdObjVar(self, "target");
        obj_id waypoint = getObjIdObjVar(self, "waypoint");
        location loc = getLocation(ship);
        if (!isIdValid(waypoint))
        {
            waypoint = createWaypointInDatapad(player, loc);
        }
        if (isIdValid(waypoint))
        {
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setWaypointLocation(waypoint, loc);
            setWaypointName(waypoint, "Mission Target");
            setWaypointColor(waypoint, "space");
            setObjVar(self, "waypoint", waypoint);
        }
        int taskId = getIntObjVar(self, "taskId");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, taskId, loc);
        messageTo(self, "updateTargetWaypoint", null, 30.f, false);
        return SCRIPT_CONTINUE;
    }
    public void clearMissionWaypoint(obj_id self) throws InterruptedException
    {
        if(isIdValid(self)) {
            obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
            obj_id waypoint = getObjIdObjVar(self, "waypoint");
            if (isIdValid(waypoint) && isIdValid(player)) {
                destroyWaypointInDatapad(waypoint, player);
            }
        }
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
    }
    public int completeQuestMsg(obj_id self, dictionary params) throws InterruptedException
    {
        questCompleted(self);
        return SCRIPT_CONTINUE;
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
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = getObjIdObjVar(self, "target");
        if (isIdValid(ship) && exists(ship))
        {
            if (hasObjVar(self, "captured"))
            {
                String questName = getStringObjVar(self, space_quest.QUEST_NAME);
                String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
                string_id abort = new string_id("spacequest/" + questType + "/" + questName, "abort");
                prose_package pp = prose.getPackage(abort, 0);
                space_quest.groupTaunt(ship, player, pp);
                messageTo(ship, "missionAbort", null, 2.f, false);
            }
            else 
            {
                messageTo(ship, "missionAbort", null, 10.f, false);
            }
            obj_id[] escorts = getObjIdArrayObjVar(self, "escorts");
            if (escorts != null)
            {
                for (int i = 0; i < escorts.length; i++)
                {
                    if (isIdValid(escorts[i]) && exists(escorts[i]))
                    {
                        messageTo(escorts[i], "missionAbort", null, 10.f, false);
                    }
                }
            }
        }
        questAborted(self);
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
            questFailed(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id destroyed = params.getObjId("ship");
        obj_id[] escorts = getObjIdArrayObjVar(self, "escorts");
        int dead_escorts = getIntObjVar(self, "dead_escorts");
        if (escorts == null)
        {
            return SCRIPT_OVERRIDE;
        }
        for (int i = 0; i < escorts.length; i++)
        {
            if (escorts[i] == destroyed)
            {
                escorts[i] = obj_id.NULL_ID;
                dead_escorts++;
                int remaining = escorts.length - dead_escorts;
                if (remaining == 0)
                {
                    string_id update = new string_id("spacequest/" + questType + "/" + questName, "escort_wiped_out");
                    space_quest.sendQuestMessage(player, update);
                    playClientEffectObj(player, SOUND_DESTROYED_ALL, player, "");
                }
                else 
                {
                    string_id update = new string_id("spacequest/" + questType + "/" + questName, "escort_remaining");
                    prose_package pp = prose.getPackage(update, remaining);
                    space_quest.sendQuestMessage(player, pp);
                }
            }
            else if (escorts[i] == null)
            {
                escorts[i] = obj_id.NULL_ID;
            }
        }
        setObjVar(self, "escorts", escorts);
        setObjVar(self, "dead_escorts", dead_escorts);
        return SCRIPT_CONTINUE;
    }
    public void cleanupShips(obj_id self) throws InterruptedException
    {
        space_quest.recoveryCleanUpShips(self);
    }
    public int cleanupShipsMsg(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupShips(self);
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
    public int launchAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "initialized") || !hasObjVar(self, "target"))
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
        obj_id ship = getObjIdObjVar(self, "target");
        if (!isIdValid(ship) || !exists(ship))
        {
            return SCRIPT_CONTINUE;
        }
        int numAttackShipLists = getIntObjVar(self, "numAttackShipLists");
        if (numAttackShipLists == 0)
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
        if (shipList == null)
        {
            return SCRIPT_CONTINUE;
        }
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
            ship_ai.unitSetAutoAggroImmuneTime(newship, 3.0f);
            //ship_ai.unitSetAutoAggroImmuneTime(player, 3.0f);
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
        int reasons = getIntObjVar(self, "numResponses");
        string_id hello = new string_id("spacequest/" + questType + "/" + questName, "panic_" + rand(1, reasons));
        prose_package pp = prose.getPackage(hello, 0);
        space_quest.groupTaunt(ship, player, pp);
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        dictionary outparams = new dictionary();
        outparams.put("code", getIntObjVar(self, "attackcode"));
        messageTo(self, "launchAttack", outparams, attackPeriod, false);
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
        if (targets == null)
        {
            return SCRIPT_CONTINUE;
        }
        int deadships = getIntObjVar(self, "deadships");
        boolean launchWave = false;
        for (int i = 0; i < targets.length; i++)
        {
            if (deadship == targets[i])
            {
                deadships++;
                setObjVar(self, "deadships", deadships);
                space_quest._removeMissionCriticalShip(player, self, deadship);
                targets[i] = obj_id.NULL_ID;
                setObjVar(self, "targets", targets);
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
                    if (isIdValid(ship) && exists(ship))
                    {
                        space_quest.groupTaunt(ship, player, pp);
                    }
                    int attackCount = getIntObjVar(self, "attackCount");
                    attackCount++;
                    setObjVar(self, "attackCount", attackCount);
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
            dictionary outparams = new dictionary();
            outparams.put("code", getIntObjVar(self, "attackcode"));
            messageTo(self, "launchAttack", outparams, 0, false);
            removeObjVar(self, "new_wave_pending");
        }
        return SCRIPT_OVERRIDE;
    }
    public int warpoutFailureRecovery(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "handling_warpout_failure"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "handling_warpout_failure", 1);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        sendQuestSystemMessage(player, WARPOUT_FAILURE);
        cleanupShips(self);
        questFailed(self);
        return SCRIPT_CONTINUE;
    }
}
