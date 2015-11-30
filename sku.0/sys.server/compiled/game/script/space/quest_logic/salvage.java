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

public class salvage extends script.space.quest_logic.recovery
{
    public salvage()
    {
    }
    public static final string_id SID_ABANDONED_RESCUE = new string_id("space/quest", "rescue_abandoned");
    public static final string_id SID_REMAINDER_UPDATE = new string_id("space/quest", "mining_remainder_update");
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
        setObjVar(self, "salvageResources", questInfo.getInt("salvageResources"));
        setObjVar(self, "salvageResourceType", questInfo.getString("salvageResourceType"));
        setObjVar(self, "currentCount", 0);
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
        setObjVar(self, "firstTimeSalvaged", true);
        setObjVar(self, "msgCount", 3);
        String[] recoveryPath = dataTableGetStringColumn(qTable, "spawnLocs");
        space_quest.cleanArray(self, "recoveryPath", recoveryPath);
        setObjVar(self, "attackcode", 1);
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
        int pick = rand(0, recoveryPoints.length - 1);
        String destPoint = recoveryPoints[pick];
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
        setLookAtTarget(player, ship);
        setObjVar(ship, "quest", self);
        attachScript(ship, "space.quest_logic.rescue_target");
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        setObjVar(ship, "objMissionOwner", player);
        ship_ai.unitAddExclusiveAggro(ship, player);
        dictionary outp = new dictionary();
        outp.put("objShip", space_transition.getContainingShip(player));
        messageTo(ship, "disableSelf", outp, 1.f, false);
        attachScript(ship, "space_mining.mining_salvage_hulk");
        int hp = getIntObjVar(self, "salvageResources");
        setMaxHitpoints(ship, hp);
        setHitpoints(ship, hp);
        String type = getStringObjVar(self, "salvageResourceType");
        setObjVar(ship, "strAsteroidType", type);
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
    public int wasSalvaged(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        String typeSalvaged = params.getString("resourceType");
        String typeDesired = getStringObjVar(self, "salvageResourceType");
        int salvageCount = getIntObjVar(self, "salvageResources");
        int currentCount = getIntObjVar(self, "currentCount");
        if (typeSalvaged.equals(typeDesired))
        {
            int amt = params.getInt("resourceAmt");
            currentCount = currentCount + amt;
            setObjVar(self, "currentCount", currentCount);
            if (currentCount >= salvageCount)
            {
                questCompleted(self);
            }
            int msgCount = getIntObjVar(self, "msgCount");
            if (currentCount > (.50 * salvageCount) && currentCount < (.75 * salvageCount) && msgCount == 1)
            {
                setObjVar(self, "msgCount", 0);
                messageTo(self, "launchAttack", null, 0, false);
                space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, salvageCount - currentCount);
            }
            else if (currentCount > (.25 * salvageCount) && currentCount < (.50 * salvageCount) && msgCount == 2)
            {
                setObjVar(self, "msgCount", 1);
                messageTo(self, "launchAttack", null, 0, false);
                space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, salvageCount - currentCount);
            }
            else if (currentCount > (0) && currentCount < (.25 * salvageCount) && msgCount == 3)
            {
                setObjVar(self, "msgCount", 2);
                messageTo(self, "launchAttack", null, 0, false);
                space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, salvageCount - currentCount);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int launchAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "initialized") || !hasObjVar(self, "target"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
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
            obj_id playerShip = getPilotedShip(player);
            ship_ai.spaceAttack(newship, playerShip);
            j++;
            if (j >= shipList.length)
            {
                j = 0;
            }
        }
        setObjVar(self, "targets", targets);
        if (count > 3)
        {
            ship_ai.squadSetFormationRandom(squad);
        }
        string_id attack_notify = new string_id("spacequest/" + questType + "/" + questName, "attack_notify");
        space_quest.sendQuestMessage(player, attack_notify);
        playClientEffectObj(player, SOUND_SPAWN_WAVE, player, "");
        return SCRIPT_CONTINUE;
    }
    public void questAborted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestAborted(player, self);
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
}
