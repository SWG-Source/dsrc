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

public class space_battle extends script.base_script
{
    public space_battle()
    {
    }
    public static final string_id SID_ABANDONED_BATTLE = new string_id("space/quest", "battle_abandoned");
    public static final string_id WARPOUT_FAILURE = new string_id("space/quest", "warpout_failure");
    public static final String SOUND_SPAWN_ALLIES = "clienteffect/ui_quest_spawn_friendly.cef";
    public static final String SOUND_SPAWN_ENEMIES = "clienteffect/ui_quest_spawn_enemy.cef";
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
        setObjVar(self, "allyArrivalDelay", questInfo.getInt("allyArrivalDelay"));
        setObjVar(self, "enemyArrivalDelay", questInfo.getInt("enemyArrivalDelay"));
        setObjVar(self, "allyOriginDist", questInfo.getInt("allyOriginDist"));
        setObjVar(self, "enemyOriginDist", questInfo.getInt("enemyOriginDist"));
        setObjVar(self, "allyArrivalDist", questInfo.getInt("allyArrivalDist"));
        setObjVar(self, "enemyArrivalDist", questInfo.getInt("enemyArrivalDist"));
        String[] allyShips = dataTableGetStringColumn(qTable, "allyForce");
        space_quest.cleanArray(self, "allyShips", allyShips);
        String[] enemyShips = dataTableGetStringColumn(qTable, "enemyForce");
        space_quest.cleanArray(self, "enemyShips", enemyShips);
        setObjVar(self, "battlePoint", questInfo.getString("battlePoint"));
        setObjVar(self, "damageMultiplier", questInfo.getInt("damageMultiplier"));
        int useConvo = questInfo.getInt("useConversation");
        if (useConvo == 1)
        {
            setObjVar(self, "useConvo", 1);
            String[] convoStrings = dataTableGetStringColumn(qTable, "convo");
            space_quest.cleanArray(self, "convo", convoStrings);
            int[] convoDelay = dataTableGetIntColumn(qTable, "convo_delay");
            space_quest.cleanArray(self, "convo_delay", convoDelay);
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
                space_quest.showQuestUpdate(self, SID_ABANDONED_BATTLE);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "initialized"))
        {
            return SCRIPT_CONTINUE;
        }
        String battlePoint = getStringObjVar(self, "battlePoint");
        if (battlePoint == null)
        {
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(battlePoint, ":");
        String scene = st.nextToken();
        battlePoint = st.nextToken();
        if ((getCurrentSceneName()).startsWith(scene))
        {
            findBattleLoc(self, player, battlePoint);
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
    public void findBattleLoc(obj_id self, obj_id player, String destNav) throws InterruptedException
    {
        obj_id navPoint = space_quest.findQuestLocation(self, player, destNav, "nav");
        if (isIdValid(navPoint))
        {
            location loc = getLocation(navPoint);
            transform wptrans = getTransform_o2w(navPoint);
            obj_id waypoint = getObjIdObjVar(self, "battle_waypoint");
            if (!isIdValid(waypoint))
            {
                waypoint = createWaypointInDatapad(player, loc);
            }
            if (isIdValid(waypoint))
            {
                setWaypointVisible(waypoint, true);
                setWaypointActive(waypoint, true);
                setWaypointLocation(waypoint, loc);
                setWaypointName(waypoint, "Mission Objective");
                setWaypointColor(waypoint, "space");
                setObjVar(self, "battle_waypoint", waypoint);
            }
            setObjVar(self, "battle_transform", wptrans);
            space_quest._setQuestInProgress(self);
            int enemyDist = getIntObjVar(self, "enemyOriginDist");
            int enemyArriveDist = getIntObjVar(self, "enemyArrivalDist");
            transform enemyOrigin = (wptrans.move_l(new vector(0, 0, enemyDist))).yaw_l((float)Math.PI);
            setObjVar(self, "enemyOrigin", enemyOrigin);
            transform enemyArrive = (wptrans.move_l(new vector(0, 0, enemyArriveDist))).yaw_l((float)Math.PI);
            setObjVar(self, "enemyArrive", enemyArrive);
            int allyDist = getIntObjVar(self, "allyOriginDist");
            int allyArriveDist = getIntObjVar(self, "allyArrivalDist");
            transform allyOrigin = wptrans.move_l(new vector(0, 0, allyDist));
            setObjVar(self, "allyOrigin", allyOrigin);
            transform allyArrive = wptrans.move_l(new vector(0, 0, allyArriveDist));
            setObjVar(self, "allyArrive", allyArrive);
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "found_loc");
            questUpdate(self, foundLocation);
            questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, 1, loc);
            dictionary outp = new dictionary();
            outp.put("enemy", 0);
            messageTo(self, "armyArrives", outp, getIntObjVar(self, "allyArrivalDelay"), false);
            outp.put("enemy", 1);
            messageTo(self, "armyArrives", outp, getIntObjVar(self, "enemyArrivalDelay"), false);
        }
        setObjVar(self, "initialized", 1);
    }
    public int armyArrives(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        transform armyArrive = null;
        transform armyOrigin = null;
        String[] army = null;
        String name = null;
        int enemy = params.getInt("enemy");
        if (enemy == 1)
        {
            name = "enemies";
            army = getStringArrayObjVar(self, "enemyShips");
            armyOrigin = getTransformObjVar(self, "enemyOrigin");
            armyArrive = getTransformObjVar(self, "enemyArrive");
            string_id noto = new string_id("spacequest/" + questType + "/" + questName, "enemies_arrived");
            questUpdate(self, noto);
            playClientEffectObj(player, SOUND_SPAWN_ENEMIES, player, "");
        }
        else 
        {
            name = "allies";
            army = getStringArrayObjVar(self, "allyShips");
            armyOrigin = getTransformObjVar(self, "allyOrigin");
            armyArrive = getTransformObjVar(self, "allyArrive");
            string_id noto = new string_id("spacequest/" + questType + "/" + questName, "allies_arrived");
            questUpdate(self, noto);
            playClientEffectObj(player, SOUND_SPAWN_ALLIES, player, "");
        }
        transform[] translist = new transform[1];
        translist[0] = armyArrive;
        int squad = ship_ai.squadCreateSquadId();
        setObjVar(self, "squad_" + name, squad);
        obj_id[] ships = new obj_id[army.length];
        for (int i = 0; i < army.length; i++)
        {
            ships[i] = space_create.createShipHyperspace(army[i], armyOrigin);
            ship_ai.unitSetLeashDistance(ships[i], 16000);
            attachScript(ships[i], "space.quest_logic.space_battle_ship");
            attachScript(ships[i], "space.quest_logic.destroyduty_ship");
            setObjVar(ships[i], "quest", self);
            setObjVar(ships[i], "name", name);
            ship_ai.unitSetSquadId(ships[i], squad);
            ship_ai.unitSetAttackOrders(ships[i], ship_ai.ATTACK_ORDERS_RETURN_FIRE);
            space_quest._addMissionCriticalShip(player, self, ships[i]);
            setObjVar(ships[i], "objMissionOwner", player);
            ship_ai.unitAddExclusiveAggro(ships[i], player);
            int dmult = getIntObjVar(self, "damageMultiplier");
            if (dmult < 1)
            {
                dmult = 1;
            }
            setObjVar(ships[i], "damageMultiplier", dmult);
        }
        setObjVar(self, name, ships);
        ship_ai.squadSetLeader(squad, ships[0]);
        ship_ai.squadSetFormationRandom(squad);
        ship_ai.squadMoveTo(squad, translist);
        return SCRIPT_CONTINUE;
    }
    public int shipArrived(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = params.getObjId("ship");
        String name = getStringObjVar(ship, "name");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (hasObjVar(self, "arrived"))
        {
            if (hasObjVar(self, "useConvo"))
            {
                dictionary outp = new dictionary();
                outp.put("line", 0);
                convo(self, outp);
            }
            else 
            {
                startBattle(self, null);
            }
        }
        else 
        {
            setObjVar(self, "arrived", name);
        }
        return SCRIPT_CONTINUE;
    }
    public int startBattle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
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
        string_id noto = new string_id("spacequest/" + questType + "/" + questName, "battle_started");
        questUpdate(self, noto);
        int asquad = getIntObjVar(self, "squad_allies");
        if (ship_ai.isSquadIdValid(asquad))
        {
            ship_ai.squadSetAttackOrders(asquad, ship_ai.ATTACK_ORDERS_ATTACK_FREELY);
        }
        int esquad = getIntObjVar(self, "squad_enemies");
        if (ship_ai.isSquadIdValid(esquad))
        {
            ship_ai.squadSetAttackOrders(esquad, ship_ai.ATTACK_ORDERS_ATTACK_FREELY);
        }
        return SCRIPT_CONTINUE;
    }
    public int convo(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id[] allies = getObjIdArrayObjVar(self, "allies");
        obj_id[] enemies = getObjIdArrayObjVar(self, "enemies");
        String[] convo = getStringArrayObjVar(self, "convo");
        int[] convo_delay = getIntArrayObjVar(self, "convo_delay");
        int line = params.getInt("line");
        obj_id ally = allies[0];
        obj_id enemy = enemies[0];
        if (line >= convo.length)
        {
            startBattle(self, null);
            return SCRIPT_CONTINUE;
        }
        int delay = convo_delay[line];
        String sline = convo[line];
        java.util.StringTokenizer st = new java.util.StringTokenizer(sline, ":");
        String side = st.nextToken();
        String id = st.nextToken();
        string_id phrase = new string_id("spacequest/" + questType + "/" + questName, id);
        prose_package pp = prose.getPackage(phrase, 0);
        if (side.equals("ally"))
        {
            space_quest.groupTaunt(ally, player, pp);
        }
        else 
        {
            space_quest.groupTaunt(enemy, player, pp);
        }
        params.put("line", line + 1);
        messageTo(self, "convo", params, delay, false);
        return SCRIPT_CONTINUE;
    }
    public void clearMissionWaypoint(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id waypoint = getObjIdObjVar(self, "battle_waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id deadship = params.getObjId("ship");
        String name = getStringObjVar(deadship, "name");
        obj_id targets[] = null;
        if (name.equals("enemies"))
        {
            targets = getObjIdArrayObjVar(self, "enemies");
        }
        else 
        {
            targets = getObjIdArrayObjVar(self, "allies");
        }
        int deadships = getIntObjVar(self, "dead_" + name);
        for (int i = 0; i < targets.length; i++)
        {
            if (deadship == targets[i])
            {
                deadships++;
                setObjVar(self, "dead_" + name, deadships);
                space_quest._removeMissionCriticalShip(player, self, deadship);
                if (deadships == targets.length)
                {
                    if (name.equals("enemies"))
                    {
                        string_id noto = new string_id("spacequest/" + questType + "/" + questName, "allies_win");
                        questUpdate(self, noto);
                        messageTo(self, "winMission", null, 2.f, false);
                    }
                    else 
                    {
                        string_id noto = new string_id("spacequest/" + questType + "/" + questName, "enemies_win");
                        questUpdate(self, noto);
                        messageTo(self, "loseMission", null, 2.f, false);
                    }
                    return SCRIPT_CONTINUE;
                }
                break;
            }
        }
        int remaining = targets.length - deadships;
        string_id noto = new string_id("spacequest/" + questType + "/" + questName, name + "_remain");
        prose_package pp = prose.getPackage(noto, remaining);
        space_quest.sendQuestMessage(player, pp);
        return SCRIPT_OVERRIDE;
    }
    public int winMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 2, player))
            {
                questCompleteTask(questid, 2, player);
            }
        }
        questCompleted(self);
        return SCRIPT_OVERRIDE;
    }
    public int loseMission(obj_id self, dictionary params) throws InterruptedException
    {
        questFailed(self);
        return SCRIPT_OVERRIDE;
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        warpOutShips(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
    }
    public void questFailed(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        warpOutShips(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestFailed(player, self);
    }
    public void questAborted(obj_id self) throws InterruptedException
    {
        clearMissionWaypoint(self);
        warpOutShips(self);
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
        questAborted(self);
        return SCRIPT_CONTINUE;
    }
    public void warpOutShips(obj_id self) throws InterruptedException
    {
        obj_id[] allies = getObjIdArrayObjVar(self, "allies");
        if (allies != null)
        {
            for (int i = 0; i < allies.length; i++)
            {
                if (isIdValid(allies[i]) && exists(allies[i]))
                {
                    messageTo(allies[i], "warpOut", null, 60.f, false);
                }
            }
        }
        obj_id[] enemies = getObjIdArrayObjVar(self, "enemies");
        if (enemies != null)
        {
            for (int i = 0; i < enemies.length; i++)
            {
                if (isIdValid(enemies[i]) && exists(enemies[i]))
                {
                    messageTo(enemies[i], "warpOut", null, 60.f, false);
                }
            }
        }
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
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            warpOutShips(self);
            questFailed(self);
        }
        return SCRIPT_CONTINUE;
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
        warpOutShips(self);
        questFailed(self);
        return SCRIPT_CONTINUE;
    }
}
