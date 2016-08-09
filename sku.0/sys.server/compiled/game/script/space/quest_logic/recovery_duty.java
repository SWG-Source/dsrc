package script.space.quest_logic;

import script.*;
import script.library.*;

public class recovery_duty extends script.space.quest_logic.recovery
{
    public recovery_duty()
    {
    }
    public static final string_id SID_RECOVERY_REWARD = new string_id("space/quest", "recovery_reward");
    public static final string_id SID_ABANDONED_DUTY = new string_id("space/quest", "destroy_abandoned");
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
        String[] targetShips = dataTableGetStringColumn(qTable, "targetShipTypes");
        space_quest.cleanArray(self, "targetShipTypes", targetShips);
        setObjVar(self, "reward", questInfo.getInt("reward"));
        setObjVar(self, "killReward", questInfo.getInt("killReward"));
        String[] escortPath = dataTableGetStringColumn(qTable, "escortPath");
        space_quest.cleanArray(self, "escortPath", escortPath);
        String[] recoveryPath = dataTableGetStringColumn(qTable, "recoveryPath");
        space_quest.cleanArray(self, "recoveryPath", recoveryPath);
        // check to see if user is already in space.  If so, kick off the mission.
        if ((getCurrentSceneName()).startsWith(getStringObjVar(self, space_quest.QUEST_ZONE)))
        {
            dictionary outparams = new dictionary();
            outparams.put("player", player);
            messageTo(self, "initializedQuestPlayer", outparams, 1.f, false);
        }
        return SCRIPT_CONTINUE;
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
                space_quest.showQuestUpdate(self, SID_ABANDONED_DUTY);
                space_quest.setQuestWon(player, self);
            }
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_OVERRIDE;
        }
        removeObjVar(self, "target");
        removeObjVar(self, "targets");
        removeObjVar(self, "deadships");
        removeObjVar(self, "dead_escorts");
        buildRandomNavList(self, "escortPath");
        buildRandomNavList(self, "recoveryPath");
        String[] escortPoints = getStringArrayObjVar(self, "escortPath");
        if (escortPoints == null)
        {
            return SCRIPT_OVERRIDE;
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
            questActivateTask(questid, 1, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public void buildRandomNavList(obj_id self, String path) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String[] escortPoints = getStringArrayObjVar(self, path);
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
            System.arraycopy(preSortPoints, 0, sortedPoints, 0, preSortPoints.length - 1 + 1);
            setObjVar(self, path, sortedPoints);
        }
        else 
        {
            setObjVar(self, path, preSortPoints);
        }
    }
    public obj_id createTargetShip(obj_id self) throws InterruptedException
    {
        String[] shipTypes = getStringArrayObjVar(self, "targetShipTypes");
        String shipType = shipTypes[rand(0, shipTypes.length - 1)];
        transform trans = getTransformObjVar(self, "escort_transform");
        transform spawnLoc = space_quest.getRandomPositionInSphere(trans, 100, 200);
        return space_create.createShipHyperspace(shipType, spawnLoc);
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
        if ((questid != 0) && questIsTaskActive(questid, 3, player))
        {
            questCompleteTask(questid, 3, player);
        }
        string_id abort = new string_id("spacequest/" + questType + "/" + questName, "complete");
        prose_package pp = prose.getPackage(abort, 0);
        space_quest.groupTaunt(ship, player, pp);
        messageTo(self, "cleanupShipsMsg", null, 2.f, false);
        int reward = getIntObjVar(self, "reward");
        int rewardships = getIntObjVar(self, "rewardships");
        removeObjVar(self, "rewardships");
        int killreward = getIntObjVar(self, "killReward");
        rewardships *= killreward;
        reward += rewardships;
        money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, reward);
        pp = prose.getPackage(SID_RECOVERY_REWARD, reward);
        sendQuestSystemMessage(player, pp);
        removeObjVar(self, "initialized");
        removeObjVar(self, "attacksok");
        removeObjVar(self, "wave1");
        removeObjVar(self, "wave2");
        setObjVar(self, "attackcode", getIntObjVar(self, "attackcode") + 1);
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if ((getCurrentSceneName()).startsWith(questZone))
        {
            dictionary outparams = new dictionary();
            outparams.put("player", player);
            messageTo(self, "initializedQuestPlayer", outparams, 10.f, false);
        }
        return SCRIPT_OVERRIDE;
    }
    public String getCompletePhrase(obj_id self) throws InterruptedException
    {
        int reasons = getIntObjVar(self, "numResponses");
        return "complete_" + rand(1, reasons);
    }
    public String getCapturePhrase1(obj_id self) throws InterruptedException
    {
        int reasons = getIntObjVar(self, "numResponses");
        return "capture_phase_1_" + rand(1, reasons);
    }
    public String getCapturePhrase2(obj_id self) throws InterruptedException
    {
        int reasons = getIntObjVar(self, "numResponses");
        return "capture_phase_2_" + rand(1, reasons);
    }
    public void questAborted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
    }
}
