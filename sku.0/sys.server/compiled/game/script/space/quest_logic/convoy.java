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
import script.library.group;
import script.library.money;

public class convoy extends script.space.quest_logic.escort
{
    public convoy()
    {
    }
    public static final string_id SID_CONVOY_SURVIVED = new string_id("space/quest", "convoy_survived");
    public static final string_id SID_CONVOY_PERFECT = new string_id("space/quest", "convoy_perfect");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if ((questName == null) || (questType == null))
        {
            LOG("convoy_quest", "Failed to find basic quest info");
            return SCRIPT_CONTINUE;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            LOG("convoy_quest", "Failed to find more quest info");
            sendSystemMessageTestingOnly(player, "Debug: Failed to open quest table " + qTable);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "convoySize", questInfo.getInt("convoySize"));
        setObjVar(self, "originalConvoySize", questInfo.getInt("convoySize"));
        setObjVar(self, "arrivalDelay", questInfo.getInt("arrivalDelay"));
        setObjVar(self, "arrivalReward", questInfo.getInt("arrivalReward"));
        setObjVar(self, "perfectReward", questInfo.getInt("perfectReward"));
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
        LOG("convoy_quest", "Reached initialized quest player stage");
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
        LOG("convoy_quest", "OMG, we're initialized.");
        String[] escortPoints = getStringArrayObjVar(self, "escortPoints");
        LOG("convoy_quest", "Escort points are " + escortPoints);
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
        LOG("convoy_quest", "Escort Location registered");
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
    public int warpShipDelay(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int numShips = getIntObjVar(self, "numShips");
        int convoySize = getIntObjVar(self, "convoySize");
        if (numShips == convoySize)
        {
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "convoy_arrived");
            dutyUpdate(self, foundLocation);
            obj_id[] convoy = getObjIdArrayObjVar(self, "convoy");
            string_id hello = new string_id("spacequest/" + questType + "/" + questName, "ready_to_go");
            prose_package pp = prose.getPackage(hello, 0);
            space_quest.groupTaunt(convoy[0], player, pp);
            setObjVar(self, "convoy_arrived", 1);
            messageTo(self, "startConvoyPathing", null, 300.f, false);
            return SCRIPT_OVERRIDE;
        }
        String[] convoyShips = getStringArrayObjVar(self, "escortShipTypes");
        String shipType = convoyShips[numShips];
        obj_id[] convoy = getObjIdArrayObjVar(self, "convoy");
        transform spawnLoc;
        if (numShips == 0)
        {
            transform trans = getTransformObjVar(self, "escort_transform");
            spawnLoc = space_quest.getRandomPositionInSphere(trans, 100, 200);
            clearMissionWaypoint(self);
        }
        else 
        {
            transform leadTrans = getTransform_o2w(convoy[numShips - 1]);
            spawnLoc = leadTrans.move_l(new vector(0, 0, -200));
        }
        obj_id ship = space_create.createShipHyperspace(shipType, spawnLoc);
        ship_ai.unitSetLeashDistance(ship, 16000);
        setObjVar(ship, "intNoPlayerDamage", 1);
        setLookAtTarget(player, ship);
        space_quest._addMissionCriticalShip(player, self, ship);
        setObjVar(ship, "objMissionOwner", player);
        ship_ai.unitAddExclusiveAggro(ship, player);
        playClientEffectObj(player, SOUND_SPAWN_ESCORT, player, "");
        attachScript(ship, "space.quest_logic.convoy_ship");
        setObjVar(ship, "quest", self);
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        if (numShips == 0)
        {
            string_id hello = new string_id("spacequest/" + questType + "/" + questName, "arrival_greeting");
            prose_package pp = prose.getPackage(hello, 0);
            space_quest.groupTaunt(ship, player, pp);
        }
        setShipDifficulty(ship, "convoy_" + numShips);
        int dmult = getIntObjVar(self, "damageMultiplier");
        if (dmult < 1)
        {
            dmult = 1;
        }
        setObjVar(ship, "damageMultiplier", dmult);
        obj_id[] newConvoy;
        if (convoy == null)
        {
            newConvoy = new obj_id[1];
            newConvoy[0] = ship;
        }
        else 
        {
            newConvoy = new obj_id[convoy.length + 1];
            for (int i = 0; i < convoy.length; i++)
            {
                newConvoy[i] = convoy[i];
            }
            newConvoy[convoy.length] = ship;
        }
        setObjVar(self, "convoy", newConvoy);
        setObjVar(self, "numShips", ++numShips);
        messageTo(self, "warpShipDelay", null, 5, false);
        return SCRIPT_OVERRIDE;
    }
    public int startConvoyPathing(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if (hasObjVar(self, "underway"))
        {
            return SCRIPT_CONTINUE;
        }
        int squad = ship_ai.squadCreateSquadId();
        transform[] translist = getEscortTransforms(self);
        obj_id[] convoy = getObjIdArrayObjVar(self, "convoy");
        for (int i = 0; i < convoy.length; i++)
        {
            if (!isIdValid(convoy[i]) || !exists(convoy[i]))
            {
                continue;
            }
            ship_ai.unitSetSquadId(convoy[i], squad);
            dictionary outparams = new dictionary();
            outparams.put("quest", self);
            outparams.put("loc", getLocationObjVar(self, "last_loc"));
            outparams.put("player", player);
            messageTo(convoy[i], "registerDestination", outparams, 1.f, false);
        }
        setObjVar(self, "underway", 1);
        ship_ai.squadSetFormation(squad, ship_ai.FORMATION_DELTA);
        ship_ai.squadAddPatrolPath(squad, translist);
        string_id hello = new string_id("spacequest/" + questType + "/" + questName, "lets_move");
        prose_package pp = prose.getPackage(hello, 0);
        space_quest.groupTaunt(convoy[0], player, pp);
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        if (attackPeriod > 0)
        {
            dictionary outparamss = new dictionary();
            outparamss.put("code", getIntObjVar(self, "attackcode"));
            messageTo(self, "launchAttack", outparamss, attackPeriod, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanupShips(obj_id self) throws InterruptedException
    {
        obj_id[] convoy = getObjIdArrayObjVar(self, "convoy");
        if (convoy != null)
        {
            for (int i = 0; i < convoy.length; i++)
            {
                if (isIdValid(convoy[i]) && exists(convoy[i]))
                {
                    destroyObjectHyperspace(convoy[i]);
                }
            }
        }
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
        cleanupShips(self);
        questAborted(self);
        return SCRIPT_OVERRIDE;
    }
    public int shipArrived(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = params.getObjId("ship");
        string_id convoySafe = new string_id("spacequest/" + questType + "/" + questName, "convoy_safe");
        dutyUpdate(self, convoySafe);
        int numArrived = getIntObjVar(self, "numArrived");
        numArrived++;
        setObjVar(self, "numArrived", numArrived);
        int convoySize = getIntObjVar(self, "convoySize");
        convoySize--;
        setObjVar(self, "convoySize", convoySize);
        destroyObjectHyperspace(ship);
        if (convoySize == 0)
        {
            int originalConvoySize = getIntObjVar(self, "originalConvoySize");
            int numKilled = originalConvoySize - numArrived;
            float percentSurvived = (int)((numArrived / (float)originalConvoySize) * 100.f);
            int arrivalReward = getIntObjVar(self, "arrivalReward");
            int reward = arrivalReward * numArrived;
            prose_package pp = prose.getPackage(SID_CONVOY_SURVIVED, reward, percentSurvived);
            space_quest.sendQuestMessage(player, pp);
            if (numKilled == 0)
            {
                int perfectReward = getIntObjVar(self, "perfectReward");
                reward += perfectReward;
                pp = prose.getPackage(SID_CONVOY_PERFECT, perfectReward);
                space_quest.sendQuestMessage(player, pp);
            }
            if (group.isGrouped(player))
            {
                obj_id gid = getGroupObject(player);
                obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
                if (members != null && members.length >= 0)
                {
                    for (int i = 0; i < members.length; i++)
                    {
                        if (space_quest.isOnGroupQuest(members[i], questType, questName))
                        {
                            money.bankTo(money.ACCT_SPACE_QUEST_REWARD, members[i], reward);
                        }
                    }
                }
            }
            questCompleted(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int convoyShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id ship = params.getObjId("ship");
        int convoySize = getIntObjVar(self, "convoySize");
        convoySize--;
        setObjVar(self, "convoySize", convoySize);
        if (convoySize == 0)
        {
            string_id lostConvoy = new string_id("spacequest/" + questType + "/" + questName, "convoy_destroyed");
            dutyUpdate(self, lostConvoy);
            questFailed(self, false);
            return SCRIPT_CONTINUE;
        }
        string_id lostShip = new string_id("spacequest/" + questType + "/" + questName, "convoy_ship_lost");
        dutyUpdate(self, lostShip);
        obj_id[] convoy = getObjIdArrayObjVar(self, "convoy");
        for (int i = 0; i < convoy.length; i++)
        {
            if (!isIdValid(convoy[i]) || !exists(convoy[i]))
            {
                continue;
            }
            string_id panic = new string_id("spacequest/" + questType + "/" + questName, "panic_destroyed_" + rand(1, 5));
            prose_package pp = prose.getPackage(panic, 0);
            space_quest.groupTaunt(convoy[i], player, pp);
            break;
        }
        obj_id[] newConvoy = new obj_id[convoy.length - 1];
        int j = 0;
        for (int i = 0; i < convoy.length; i++)
        {
            if (convoy[i] != ship)
            {
                newConvoy[j] = convoy[i];
                j++;
            }
        }
        setObjVar(self, "convoy", newConvoy);
        return SCRIPT_CONTINUE;
    }
    public int launchAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (hasObjVar(self, "wave1") && hasObjVar(self, "wave2") && hasObjVar(self, "wave3") && hasObjVar(self, "wave4"))
        {
            setObjVar(self, "new_wave_pending", 1);
            return SCRIPT_OVERRIDE;
        }
        int wavenum = 0;
        if (!hasObjVar(self, "wave1"))
        {
            wavenum = 1;
        }
        else if (!hasObjVar(self, "wave2"))
        {
            wavenum = 2;
        }
        else if (!hasObjVar(self, "wave3"))
        {
            wavenum = 3;
        }
        else if (!hasObjVar(self, "wave4"))
        {
            wavenum = 4;
        }
        obj_id[] convoy = getObjIdArrayObjVar(self, "convoy");
        if (convoy == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int numAttackShipLists = getIntObjVar(self, "numAttackShipLists");
        if (numAttackShipLists == 0)
        {
            return SCRIPT_OVERRIDE;
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
        obj_id ship = convoy[rand(0, convoy.length - 1)];
        int squad = ship_ai.squadCreateSquadId();
        boolean behind = false;
        if (rand() < 0.25)
        {
            behind = true;
        }
        int j = 0;
        for (int i = k; i < count + k; i++)
        {
            transform gloc = getTransform_o2w(ship);
            if (!behind)
            {
                float dist = rand(1000.f, 1200.f);
                vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
                gloc = gloc.move_p(n);
                gloc = gloc.yaw_l(3.14f);
                vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-500.f, 500.f));
                vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-500.f, 500.f));
                vector vd = vi.add(vj);
                gloc = gloc.move_p(vd);
            }
            else 
            {
                float dist = rand(500.f, 750.f) * -1.f;
                vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
                gloc = gloc.move_p(n);
                vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-200.f, 200.f));
                vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-200.f, 200.f));
                vector vd = vi.add(vj);
                gloc = gloc.move_p(vd);
            }
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
            for (int x = 0; x < convoy.length; x++)
            {
                if (!isIdValid(convoy[x]) || !exists(convoy[x]))
                {
                    continue;
                }
                if (convoy[x] != ship)
                {
                    ship_ai.unitIncreaseHate(newship, convoy[x], 100.f, 10.0f, 20);
                }
            }
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
        return SCRIPT_OVERRIDE;
    }
}
