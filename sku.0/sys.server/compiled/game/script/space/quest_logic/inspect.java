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

public class inspect extends script.base_script
{
    public inspect()
    {
    }
    public static final string_id SID_TARGET_WAYPOINTS = new string_id("space/quest", "inspect_target_waypoints");
    public static final string_id SID_ABANDONED_INSPECT = new string_id("space/quest", "inspect_abandoned");
    public static final string_id SID_RECOVERED_CARGO = new string_id("space/quest", "inspect_recovered_cargo");
    public static final string_id SID_FOUND_CARGO = new string_id("space/quest", "inspect_found_cargo");
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
        String[] ships = dataTableGetStringColumn(qTable, "shipList");
        space_quest.cleanArray(self, "shipTypes", ships);
        String[] spawns = dataTableGetStringColumn(qTable, "validSpawns");
        space_quest.cleanArray(self, "validSpawns", spawns);
        int give_waypoint = questInfo.getInt("giveSpawnWaypoint");
        setObjVar(self, "give_waypoint", give_waypoint);
        String cargo = questInfo.getString("cargo");
        if (cargo != null)
        {
            setObjVar(self, "cargo", cargo);
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
                clearTargetWaypoints(self);
                space_quest.showQuestUpdate(self, SID_ABANDONED_INSPECT);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return SCRIPT_CONTINUE;
        }
        int give_waypoint = getIntObjVar(self, "give_waypoint");
        String[] spawns = getStringArrayObjVar(self, "validSpawns");
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 1, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        space_quest._setQuestInProgress(self);
        if (spawns == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "target_waypoints"))
        {
            return SCRIPT_CONTINUE;
        }
        int added = 0;
        for (int i = 0; i < spawns.length; i++)
        {
            if (give_waypoint == 1)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(spawns[i], ":");
                String scene = st.nextToken();
                String point = st.nextToken();
                added++;
                dictionary outparams = new dictionary();
                outparams.put("spawner", point);
                outparams.put("player", player);
                outparams.put("name", "Possible Target Location");
                outparams.put("quest", self);
                outparams.put("taskId", i + 3);
                outparams.put("questId", questid);
                outparams.put("questName", "spacequest/" + questType + "/" + questName);
                space_utils.notifyObject(questManager, "createWaypointToSpawner", outparams);
            }
        }
        if (added > 0)
        {
            dictionary outparams = new dictionary();
            outparams.put("added", added);
            messageTo(self, "notifyTargetWaypoints", outparams, 1.f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int notifyTargetWaypoints(obj_id self, dictionary params) throws InterruptedException
    {
        int added = params.getInt("added");
        space_quest.showQuestUpdate(self, SID_TARGET_WAYPOINTS, added);
        return SCRIPT_CONTINUE;
    }
    public int createdWaypointToSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id newpoint = params.getObjId("waypoint");
        obj_id[] waypoints = getObjIdArrayObjVar(self, "target_waypoints");
        if (waypoints == null)
        {
            waypoints = new obj_id[1];
            waypoints[0] = newpoint;
            setObjVar(self, "target_waypoints", waypoints);
        }
        else 
        {
            obj_id[] newlist = new obj_id[waypoints.length + 1];
            for (int i = 0; i < waypoints.length; i++)
            {
                newlist[i] = waypoints[i];
            }
            newlist[waypoints.length] = newpoint;
            setObjVar(self, "target_waypoints", newlist);
        }
        return SCRIPT_CONTINUE;
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        clearTargetWaypoints(self);
        space_quest.setQuestWon(player, self);
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            clearTargetWaypoints(self);
            obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
            space_quest.setQuestFailed(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
        clearTargetWaypoints(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestAborted(player, self);
        return SCRIPT_CONTINUE;
    }
    public void clearTargetWaypoints(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id[] waypoints = getObjIdArrayObjVar(self, "target_waypoints");
        if (waypoints != null)
        {
            for (int i = 0; i < waypoints.length; i++)
            {
                if (isIdValid(waypoints[i]))
                {
                    destroyWaypointInDatapad(waypoints[i], player);
                }
            }
        }
    }
    public int removeQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearTargetWaypoints(self);
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
    public boolean checkSpecialEvent(obj_id self, obj_id player, int inspectCount) throws InterruptedException
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
        if (triggerOn != inspectCount)
        {
            return false;
        }
        dictionary params = new dictionary();
        params.put("quest", self);
        messageTo(player, "doSpecialEvent", params, triggerDelay, false);
        return false;
    }
    public int inspectedShip(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String shiptype = params.getString("type");
        obj_id player = params.getObjId("player");
        int inspectCount = getIntObjVar(self, "inspectCount");
        inspectCount++;
        setObjVar(self, "inspectCount", inspectCount);
        checkSpecialEvent(self, player, inspectCount);
        String cargo = params.getString("cargo");
        String cargoType = getStringObjVar(self, "cargo");
        if ((cargo != null) && cargo.equals(cargoType))
        {
            sendQuestSystemMessage(player, SID_FOUND_CARGO);
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
            if (questid != 0)
            {
                questActivateTask(questid, 2, player);
                if (questIsTaskActive(questid, 1, player))
                {
                    questCompleteTask(questid, 1, player);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int recoveredCargo(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String shiptype = params.getString("type");
        String cargo = params.getString("cargo");
        obj_id player = params.getObjId("player");
        String[] shipTypes = getStringArrayObjVar(self, "shipTypes");
        String spawnerName = params.getString("spawner");
        for (int j = 0; j < shipTypes.length; j++)
        {
            String type = shipTypes[j];
            if (type.equals(shiptype))
            {
                if (hasObjVar(self, "validSpawns"))
                {
                    String[] spawns = getStringArrayObjVar(self, "validSpawns");
                    if (spawns != null)
                    {
                        boolean match = false;
                        for (int i = 0; i < spawns.length; i++)
                        {
                            java.util.StringTokenizer st = new java.util.StringTokenizer(spawns[i], ":");
                            String scene = st.nextToken();
                            String point = st.nextToken();
                            if ((spawnerName != null) && spawnerName.equals(point))
                            {
                                match = true;
                                break;
                            }
                        }
                        if (!match)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
                String cargoType = getStringObjVar(self, "cargo");
                if ((cargo != null) && cargo.equals(cargoType))
                {
                    space_quest.showQuestUpdate(self, SID_RECOVERED_CARGO);
                    questCompleted(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
