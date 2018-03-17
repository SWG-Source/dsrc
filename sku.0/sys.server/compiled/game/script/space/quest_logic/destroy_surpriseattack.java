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
import script.library.space_create;
import script.library.space_transition;
import script.library.ship_ai;

public class destroy_surpriseattack extends script.base_script
{
    public destroy_surpriseattack()
    {
    }
    public static final string_id SID_REMAINDER_UPDATE = new string_id("space/quest", "destroy_remainder_update");
    public static final string_id SID_ABANDONED_DESTROY = new string_id("space/quest", "destroy_surprise_abandoned");
    public static final string_id SID_SURPRISE_ATTACK = new string_id("space/quest", "destroy_surprise_attack");
    public static final string_id WARPOUT_FAILURE = new string_id("space/quest", "warpout_failure");
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
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "leaveEarlyTimer", questInfo.getInt("leaveEarlyTimer"));
        String[] ships = dataTableGetStringColumn(qTable, "shipType");
        space_quest.cleanArray(self, "shipTypes", ships);
        int[] counts = dataTableGetIntColumn(qTable, "shipCount");
        setObjVar(self, "shipCounts", counts);
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
            questClearQuestTask(questid, 0, player);
            questActivateTask(questid, 1, player);
        }
        return SCRIPT_OVERRIDE;
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
                space_quest.showQuestUpdate(self, SID_ABANDONED_DESTROY);
                setObjVar(self, "failed", 1);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        String[] ships = getStringArrayObjVar(self, "shipTypes");
        int[] counts = getIntArrayObjVar(self, "shipCounts");
        int count = 0;
        for (int i = 0; i < ships.length; i++)
        {
            count += counts[i];
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        questSetQuestTaskCounter(player, "spacequest/" + questType + "/" + questName, 1, "quest/groundquests:destroy_counter", 0, count);
        obj_id[] targets = new obj_id[count];
        int squad = ship_ai.squadCreateSquadId();
        int j = 0;
        int k = 0;
        for (int i = 0; i < count; i++)
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
            obj_id newship = space_create.createShipHyperspace(ships[j], gloc);
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
            dictionary outparams = new dictionary();
            outparams.put("object", self);
            space_utils.notifyObject(targets[i], "notifyOnDestroy", outparams);
            setObjVar(targets[i], "objMissionOwner", player);
            ship_ai.unitAddExclusiveAggro(targets[i], player);
            obj_id playership = space_transition.getContainingShip(player);
            dictionary dict = new dictionary();
            dict.put("player", space_transition.getContainingShip(player));
            dict.put("newship", newship);
            messageTo(self, "attackPlayerShip", dict, 4.0f, false);
            k++;
            if (k > counts[j])
            {
                k = 0;
                j++;
            }
        }
        if (count > 3)
        {
            ship_ai.squadSetFormationRandom(squad);
        }
        space_quest.showQuestUpdate(self, SID_SURPRISE_ATTACK, count);
        setObjVar(self, "targets", targets);
        setObjVar(self, "killcount", count);
        setObjVar(self, "count", count);
        space_quest._setQuestInProgress(self);
        int leaveEarlyTimer = getIntObjVar(self, "leaveEarlyTimer");
        if (leaveEarlyTimer > 0)
        {
            messageTo(self, "leaveEarly", params, leaveEarlyTimer, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int leaveEarly(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id[] targets = getObjIdArrayObjVar(self, "targets");
        for (int i = 0; i < targets.length; i++)
        {
            space_quest._removeMissionCriticalShip(player, self, targets[i]);
        }
        setObjVar(self, "leaveEarly", 1);
        cleanupShips(self);
        string_id left = new string_id("spacequest/" + questType + "/" + questName, "leave_early");
        sendQuestSystemMessage(player, left);
        questCompleted(self);
        return SCRIPT_CONTINUE;
    }
    public int shipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "failed") || hasObjVar(self, "leaveEarly"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "critical_warped"))
        {
            space_utils.notifyObject(self, "warpoutFailure", null);
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = params.getObjId("object");
        obj_id[] targets = getObjIdArrayObjVar(self, "targets");
        int killcount = getIntObjVar(self, "killcount");
        int count = getIntObjVar(self, "count");
        for (int i = 0; i < targets.length; i++)
        {
            if (ship == targets[i])
            {
                space_quest._removeMissionCriticalShip(player, self, ship);
                killcount--;
                setObjVar(self, "killcount", killcount);
                break;
            }
        }
        if (checkSpecialEvent(self, player, (targets.length - killcount)))
        {
            return SCRIPT_CONTINUE;
        }
        if (killcount == 0)
        {
            string_id complete = new string_id("spacequest/" + questType + "/" + questName, "complete");
            sendQuestSystemMessage(player, complete);
            questCompleted(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            questSetQuestTaskCounter(player, "spacequest/" + questType + "/" + questName, 1, "quest/groundquests:destroy_counter", count - killcount, count);
        }
        space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, killcount);
        return SCRIPT_CONTINUE;
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestAborted(player, self);
        return SCRIPT_CONTINUE;
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
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            setObjVar(self, "failed", 1);
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
    public boolean checkSpecialEvent(obj_id self, obj_id player, int killCount) throws InterruptedException
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
        if (triggerOn != killCount)
        {
            return false;
        }
        dictionary params = new dictionary();
        params.put("quest", self);
        messageTo(player, "doSpecialEvent", params, triggerDelay, false);
        return false;
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
        space_quest.setQuestFailed(player, self, false);
        return SCRIPT_CONTINUE;
    }
}
