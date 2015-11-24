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

public class rescue extends script.space.quest_logic.recovery
{
    public rescue()
    {
    }
    public static final string_id SID_ABANDONED_RESCUE = new string_id("space/quest", "rescue_abandoned");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if ((questName == null) || (questType == null))
        {
            return SCRIPT_OVERRIDE;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            sendSystemMessageTestingOnly(player, "Debug: Failed to open quest table " + qTable);
            return SCRIPT_OVERRIDE;
        }
        setObjVar(self, "targetArrivalDelay", questInfo.getInt("targetArrivalDelay"));
        setObjVar(self, "targetRecoverTime", questInfo.getInt("targetRecoverTime"));
        String targetShip = questInfo.getString("targetShipType");
        if (targetShip != null)
        {
            setObjVar(self, "targetShipType", targetShip);
        }
        int numAttackShipLists = questInfo.getInt("numAttackShipLists");
        setObjVar(self, "numAttackShipLists", numAttackShipLists);
        for (int i = 0; i < numAttackShipLists; i++)
        {
            String[] attackShips = dataTableGetStringColumn(qTable, "attackShips" + (i + 1));
            space_quest.cleanArray(self, "attackShips" + (i + 1), attackShips);
        }
        setObjVar(self, "attackPeriod", questInfo.getInt("attackPeriod"));
        setObjVar(self, "numResponses", questInfo.getInt("numResponses"));
        String[] recoveryPath = dataTableGetStringColumn(qTable, "recoveryPath");
        space_quest.cleanArray(self, "recoveryPath", recoveryPath);
        setObjVar(self, "damageMultiplier", questInfo.getInt("damageMultiplier"));
        String rescueApp = questInfo.getString("rescueAppearance");
        if (rescueApp != null)
        {
            setObjVar(self, "rescueAppearance", rescueApp);
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
        return SCRIPT_OVERRIDE;
    }
    public int initializedQuestPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
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
                space_quest.showQuestUpdate(self, SID_ABANDONED_RESCUE);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_OVERRIDE;
        }
        String[] recoveryPoints = getStringArrayObjVar(self, "recoveryPath");
        if (recoveryPoints == null)
        {
            return SCRIPT_OVERRIDE;
        }
        String destPoint = recoveryPoints[0];
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
        return SCRIPT_OVERRIDE;
    }
    public int warpInTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if (!(getCurrentSceneName()).startsWith(questZone))
        {
            return SCRIPT_OVERRIDE;
        }
        int squad = ship_ai.squadCreateSquadId();
        obj_id ship = createTargetShip(self);
        space_quest._addMissionCriticalShip(player, self, ship);
        ship_ai.unitSetSquadId(ship, squad);
        setObjVar(self, "target", ship);
        setObjVar(ship, "noNotifyDisable", 1);
        utils.setScriptVar(ship, "dockable", 1);
        setLookAtTarget(player, ship);
        setObjVar(ship, "quest", self);
        attachScript(ship, "space.quest_logic.rescue_target");
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
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
        dictionary outp = new dictionary();
        outp.put("objShip", space_transition.getContainingShip(player));
        messageTo(ship, "disableSelf", outp, 1.f, false);
        String rescueAppearance = getStringObjVar(self, "rescueAppearance");
        if (rescueAppearance != null)
        {
            setObjVar(ship, "convo.appearance", rescueAppearance);
        }
        messageTo(self, "updateTargetWaypoint", null, 1.f, false);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "arrival_phase_2");
        questUpdate(self, status_update);
        return SCRIPT_OVERRIDE;
    }
    public int recoverShipDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "target");
        utils.setScriptVar(ship, "dockable", 1);
        return SCRIPT_OVERRIDE;
    }
    public int dockingStarted(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        obj_id ship = getObjIdObjVar(self, "target");
        if (target != ship)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "docking_started");
        questUpdate(self, status_update);
        status_update = new string_id("spacequest/" + questType + "/" + questName, getCapturePhrase1(self));
        prose_package pp = prose.getPackage(status_update, 0);
        space_quest.groupTaunt(ship, player, pp);
        return SCRIPT_OVERRIDE;
    }
    public String getCapturePhrase1(obj_id self) throws InterruptedException
    {
        return "rescue_phase_1";
    }
    public int dockingComplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        obj_id ship = getObjIdObjVar(self, "target");
        if (target != ship)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "docking_complete");
        questUpdate(self, status_update);
        status_update = new string_id("spacequest/" + questType + "/" + questName, getCapturePhrase2(self));
        prose_package pp = prose.getPackage(status_update, 0);
        space_quest.groupTaunt(ship, player, pp);
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
        utils.removeScriptVar(ship, "dockable");
        messageTo(self, "startCapturedShipPathing", null, 10.f, false);
        return SCRIPT_OVERRIDE;
    }
    public String getCapturePhrase2(obj_id self) throws InterruptedException
    {
        return "rescue_phase_2";
    }
    public int recoveryComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
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
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        string_id abort = new string_id("spacequest/" + questType + "/" + questName, getCompletePhrase(self));
        prose_package pp = prose.getPackage(abort, 0);
        space_quest.groupTaunt(ship, player, pp);
        messageTo(self, "cleanupShipsMsg", null, 2.f, false);
        messageTo(self, "completeQuestMsg", null, 3.f, false);
        return SCRIPT_OVERRIDE;
    }
}
