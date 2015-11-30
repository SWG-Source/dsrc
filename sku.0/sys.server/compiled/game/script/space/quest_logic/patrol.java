package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.space_transition;
import script.library.prose;

public class patrol extends script.base_script
{
    public patrol()
    {
    }
    public static final string_id SID_NEW_WAYPOINT = new string_id("space/quest", "patrol_new_waypoint");
    public static final string_id SID_WAYPOINT_ARRIVED = new string_id("space/quest", "patrol_waypoint_arrived");
    public static final string_id SID_CIRCUIT_COMPLETE = new string_id("space/quest", "patrol_circuit_complete");
    public static final string_id SID_TARGET_ELIMINATED = new string_id("space/quest", "patrol_target_eliminated");
    public static final string_id SID_ABANDONED_PATROL = new string_id("space/quest", "patrol_abandoned");
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
        String[] navPoints = dataTableGetStringColumn(qTable, "navPoints");
        space_quest.cleanArray(self, "navPoints", navPoints);
        setObjVar(self, "currentNavPoint", 0);
        setObjVar(self, "laps", questInfo.getInt("laps"));
        setObjVar(self, "currentLap", 1);
        String specTarget = questInfo.getString("specialTarget");
        if ((specTarget != null) && !specTarget.equals("none") && !specTarget.equals(""))
        {
            setObjVar(self, "specialTarget", specTarget);
        }
        if (questInfo.containsKey("navRadius"))
        {
            setObjVar(self, "navRadius", questInfo.getInt("navRadius"));
        }
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
                clearPatrolWaypoints(self);
                space_quest.showQuestUpdate(self, SID_ABANDONED_PATROL);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        String[] navPoints = getStringArrayObjVar(self, "navPoints");
        if (navPoints == null)
        {
            return SCRIPT_CONTINUE;
        }
        int currentNav = getIntObjVar(self, "currentNavPoint");
        String destNav = navPoints[currentNav];
        java.util.StringTokenizer st = new java.util.StringTokenizer(destNav, ":");
        String scene = st.nextToken();
        destNav = st.nextToken();
        if ((getCurrentSceneName()).startsWith(scene))
        {
            registerPatrolWaypoint(self, player, destNav);
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (hasObjVar(self, "specialTarget"))
            {
                questActivateTask(questid, 1, player);
            }
            questActivateTask(questid, 2, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void registerPatrolWaypoint(obj_id self, obj_id player, String destNav) throws InterruptedException
    {
        obj_id navPoint = space_quest.findQuestLocation(self, player, destNav, "nav");
        if (isIdValid(navPoint))
        {
            location loc = getLocation(navPoint);
            obj_id waypoint = getObjIdObjVar(self, "patrol_waypoint");
            if (!isIdValid(waypoint))
            {
                waypoint = createWaypointInDatapad(player, loc);
            }
            if (isIdValid(waypoint))
            {
                setWaypointVisible(waypoint, true);
                setWaypointActive(waypoint, true);
                setWaypointLocation(waypoint, loc);
                setWaypointName(waypoint, "Patrol Waypoint");
                setWaypointColor(waypoint, "space");
                setObjVar(self, "patrol_waypoint", waypoint);
            }
            int currentNav = getIntObjVar(self, "currentNavPoint");
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, 2 + currentNav, loc);
            space_quest.showQuestUpdate(self, SID_NEW_WAYPOINT);
            setObjVar(self, "loc", destNav);
            if (hasObjVar(self, "navRadius"))
            {
                addLocationTarget3d(player, destNav, loc, getIntObjVar(self, "navRadius"));
            }
            else 
            {
                addLocationTarget3d(player, destNav, loc, space_quest.PATROL_NAV_RADIUS);
            }
        }
    }
    public int arrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        String[] navPoints = getStringArrayObjVar(self, "navPoints");
        int currentNav = getIntObjVar(self, "currentNavPoint");
        String destNav = navPoints[currentNav];
        java.util.StringTokenizer st = new java.util.StringTokenizer(destNav, ":");
        String scene = st.nextToken();
        destNav = st.nextToken();
        st = new java.util.StringTokenizer(navPoints[0], ":");
        scene = st.nextToken();
        String navzero = st.nextToken();
        if (destNav.equals(name))
        {
            space_quest.showQuestUpdate(self, SID_WAYPOINT_ARRIVED);
            space_quest._setQuestInProgress(self, true);
            currentNav++;
            setObjVar(self, "currentNavPoint", currentNav);
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
            if (questid != 0)
            {
                if (questIsTaskActive(questid, 1 + currentNav, player))
                {
                    questCompleteTask(questid, 1 + currentNav, player);
                }
            }
            checkSpecialEvent(self, player, currentNav);
            if (currentNav == navPoints.length)
            {
                int laps = getIntObjVar(self, "laps");
                int curLap = getIntObjVar(self, "currentLap");
                if (curLap >= laps)
                {
                    if (hasObjVar(self, "specialTarget"))
                    {
                        if (!hasObjVar(self, "specialTargetDestroyed"))
                        {
                            setObjVar(self, "patrolComplete", 1);
                            setObjVar(self, "currentNavPoint", 0);
                            registerPatrolWaypoint(self, player, navzero);
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (!hasPendingSplit(self))
                    {
                        questCompleted(self);
                    }
                    else 
                    {
                        messageTo(self, "handleDelayedQuestComplete", null, 10, false);
                    }
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    prose_package pp = prose.getPackage(SID_CIRCUIT_COMPLETE, (laps - curLap));
                    space_quest.sendQuestMessage(player, pp);
                    setObjVar(self, "currentNavPoint", 0);
                    registerPatrolWaypoint(self, player, navzero);
                    setObjVar(self, "currentLap", (curLap + 1));
                }
            }
            else 
            {
                st = new java.util.StringTokenizer(navPoints[currentNav], ":");
                scene = st.nextToken();
                destNav = st.nextToken();
                registerPatrolWaypoint(self, player, destNav);
            }
            if (questid != 0)
            {
                questActivateTask(questid, 2 + currentNav, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasPendingSplit(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "pendingSplit"))
        {
            return getBooleanObjVar(self, "pendingSplit");
        }
        setObjVar(self, "pendingSplit", false);
        return false;
    }
    public void setPendingSplit(obj_id self, boolean state) throws InterruptedException
    {
        setObjVar(self, "pendingSplit", state);
    }
    public int handleDelayedQuestComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasPendingSplit(self))
        {
            messageTo(self, "handleDelayedQuestComplete", null, 10, false);
        }
        else 
        {
            questCompleted(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearPatrolWaypoints(self);
        space_quest.setQuestWon(player, self);
    }
    public int handleShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "specialTarget"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "specialTargetDestroyed"))
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String killType = params.getString("strShipType");
        String specTarget = getStringObjVar(self, "specialTarget");
        if (specTarget.equals(killType))
        {
            setObjVar(self, "specialTargetDestroyed", 1);
            int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
            if (questid != 0)
            {
                if (questIsTaskActive(questid, 1, player))
                {
                    questCompleteTask(questid, 1, player);
                }
            }
            if (!hasObjVar(self, "patrolComplete"))
            {
                space_quest.showQuestUpdate(self, SID_TARGET_ELIMINATED);
            }
            else 
            {
                questCompleted(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearPatrolWaypoints(self);
        space_quest.setQuestAborted(player, self);
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
            obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
            clearPatrolWaypoints(self);
            space_quest.setQuestFailed(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearPatrolWaypoints(self);
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
    public void clearPatrolWaypoints(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id waypoint = getObjIdObjVar(self, "patrol_waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        dictionary params = new dictionary();
        String loc = getStringObjVar(self, "loc");
        if (loc != null)
        {
            removeLocationTarget(player, loc);
        }
    }
    public boolean checkSpecialEvent(obj_id self, obj_id player, int currentNav) throws InterruptedException
    {
        if (!hasObjVar(self, space_quest.QUEST_TRIGGER_EVENT))
        {
            return false;
        }
        int triggerEvent = getIntObjVar(self, space_quest.QUEST_TRIGGER_EVENT);
        int triggerSC = getIntObjVar(self, space_quest.QUEST_TRIGGER_SC);
        int triggerOn = getIntObjVar(self, space_quest.QUEST_TRIGGER_ON);
        int triggerDelay = getIntObjVar(self, space_quest.QUEST_TRIGGER_DELAY);
        if (triggerEvent == 0)
        {
            return false;
        }
        if (triggerSC != 0)
        {
            return false;
        }
        if (triggerOn != currentNav)
        {
            return false;
        }
        setPendingSplit(self, true);
        dictionary params = new dictionary();
        params.put("quest", self);
        messageTo(player, "doSpecialEvent", params, triggerDelay, false);
        return false;
    }
}
