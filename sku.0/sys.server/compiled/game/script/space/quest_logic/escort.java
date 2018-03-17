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
import script.library.space_transition;
import script.library.utils;
import script.library.ship_ai;
import script.library.prose;

public class escort extends script.space.quest_logic.escort_duty
{
    public escort()
    {
    }
    public static final string_id SID_ABANDONED_ESCORT = new string_id("space/quest", "escort_abandoned");
    public static final int TRIGGER_OVERRIDE = -99;
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
                space_quest.showQuestUpdate(self, SID_ABANDONED_ESCORT);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_OVERRIDE;
        }
        String[] escortPoints = getStringArrayObjVar(self, "escortPoints");
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
        return SCRIPT_OVERRIDE;
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
        checkSpecialEvent(self, player, TRIGGER_OVERRIDE, false);
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
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        if (!hasObjVar(self, space_quest.QUEST_DUTY) && hasObjVar(self, "destroyIsSuccess"))
        {
            questFailed(self, true);
        }
        else 
        {
            questCompleted(self);
        }
        return SCRIPT_OVERRIDE;
    }
    public void dutyUpdate(obj_id self, string_id update_id) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id update_prefix = new string_id("spacequest/" + questType + "/" + questName, "quest_update");
        prose_package pp = prose.getPackage(update_prefix, update_id);
        space_quest.sendQuestMessage(player, pp);
    }
    public boolean checkSpecialEvent(obj_id self, obj_id player, int attackCount) throws InterruptedException
    {
        return checkSpecialEvent(self, player, attackCount, true);
    }
    public boolean checkSpecialEvent(obj_id self, obj_id player, int attackCount, boolean useDelay) throws InterruptedException
    {
        String BOOL_SPECIAL_TRIGGERED = "eventAlreadyTriggered";
        if (!hasObjVar(self, space_quest.QUEST_TRIGGER_EVENT))
        {
            return false;
        }
        if (getBooleanObjVar(self, BOOL_SPECIAL_TRIGGERED) == true)
        {
            return false;
        }
        int triggerEvent = getIntObjVar(self, space_quest.QUEST_TRIGGER_EVENT);
        int triggerSC = getIntObjVar(self, space_quest.QUEST_TRIGGER_SC);
        int triggerOn = getIntObjVar(self, space_quest.QUEST_TRIGGER_ON);
        int triggerDelay = 0;
        if (useDelay)
        {
            triggerDelay = getIntObjVar(self, space_quest.QUEST_TRIGGER_DELAY);
        }
        if (triggerEvent == 0)
        {
            return false;
        }
        if (triggerSC != 0)
        {
            return false;
        }
        if (triggerOn != attackCount && triggerOn != TRIGGER_OVERRIDE)
        {
            return false;
        }
        dictionary params = new dictionary();
        params.put("quest", self);
        messageTo(player, "doSpecialEvent", params, triggerDelay, false);
        setObjVar(self, BOOL_SPECIAL_TRIGGERED, true);
        return false;
    }
    public void questAborted(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        clearMissionWaypoint(self);
        space_quest.setQuestAborted(player, self);
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
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
        return SCRIPT_OVERRIDE;
    }
}
