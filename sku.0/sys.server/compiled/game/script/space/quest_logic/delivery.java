package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.space_transition;
import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;
import script.library.ship_ai;
import script.library.prose;

public class delivery extends script.base_script
{
    public delivery()
    {
    }
    public static final string_id SID_ABANDONED_MISSION = new string_id("space/quest", "mission_abandoned");
    public static final string_id SID_TARGETS_REMAINING = new string_id("space/quest", "destroy_duty_targets_remaining");
    public static final String SOUND_SPAWN_ESCORT = "clienteffect/ui_quest_spawn_escort.cef";
    public static final String SOUND_SPAWN_WAVE = "clienteffect/ui_quest_spawn_wave.cef";
    public static final String SOUND_DESTROYED_WAVE = "clienteffect/ui_quest_destroyed_wave.cef";
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
        setObjVar(self, "pickupShip", questInfo.getString("pickupShip"));
        setObjVar(self, "deliveryShip", questInfo.getString("deliveryShip"));
        setObjVar(self, "pickupPoint", questInfo.getString("pickupPoint"));
        setObjVar(self, "deliveryPoint", questInfo.getString("deliveryPoint"));
        int numAttackShipLists = questInfo.getInt("numAttackShipLists");
        setObjVar(self, "numAttackShipLists", numAttackShipLists);
        for (int i = 0; i < numAttackShipLists; i++)
        {
            String[] attackShips = dataTableGetStringColumn(qTable, "attackShips" + (i + 1));
            space_quest.cleanArray(self, "attackShips" + (i + 1), attackShips);
        }
        setObjVar(self, "attackPeriod", questInfo.getInt("attackPeriod"));
        setObjVar(self, "attackListType", questInfo.getInt("attackListType"));
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
        String questZone = null, point = null;
        obj_id player = params.getObjId("player");
        java.util.StringTokenizer st;
        obj_id containing_ship = space_transition.getContainingShip(player);
        if (isIdValid(containing_ship))
        {
            String strChassisType = getShipChassisType(containing_ship);
            if ((strChassisType != null) && strChassisType.equals("player_sorosuub_space_yacht"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        String pp_scene = null, pp_destPoint = null;
        if (!hasObjVar(self, "delivery_nopickup"))
        {
            point = getStringObjVar(self, "pickupPoint");
            st = new java.util.StringTokenizer(point, ":");
            pp_scene = st.nextToken();
            pp_destPoint = st.nextToken();
        }
        point = getStringObjVar(self, "deliveryPoint");
        st = new java.util.StringTokenizer(point, ":");
        String dp_scene = st.nextToken();
        String dp_destPoint = st.nextToken();
        boolean rightzone = false;
        if (!hasObjVar(self, "delivery_nopickup"))
        {
            rightzone = (getCurrentSceneName()).startsWith(pp_scene) || (getCurrentSceneName()).startsWith(dp_scene);
        }
        else 
        {
            rightzone = (getCurrentSceneName()).startsWith(dp_scene);
        }
        if (!rightzone)
        {
            if (space_quest.isQuestInProgress(self))
            {
                clearMissionWaypoint(self);
                space_quest.showQuestUpdate(self, SID_ABANDONED_MISSION);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        String scene = null, destPoint = null;
        if (hasObjVar(self, "delivering") || hasObjVar(self, "dropoff"))
        {
            scene = dp_scene;
            destPoint = dp_destPoint;
        }
        else 
        {
            scene = pp_scene;
            destPoint = pp_destPoint;
        }
        if ((getCurrentSceneName()).startsWith(scene))
        {
            registerQuestLocation(self, player, destPoint);
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void registerQuestLocation(obj_id self, obj_id player, String destNav) throws InterruptedException
    {
        obj_id navPoint = space_quest.findQuestLocation(self, player, destNav, "nav");
        if (isIdValid(navPoint))
        {
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            location loc = getLocation(navPoint);
            transform wptrans = getTransform_o2w(navPoint);
            String waypoint_prefix = null, wp = null;
            if (hasObjVar(self, "delivering"))
            {
                waypoint_prefix = "Delivery";
                wp = "d";
                int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
                if ((questid != 0) && hasObjVar(self, "delivery_nopickup"))
                {
                    setObjVar(self, "taskId", 1);
                    questActivateTask(questid, 1, player);
                }
            }
            else 
            {
                waypoint_prefix = "Pickup";
                wp = "p";
                int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
                if (questid != 0)
                {
                    setObjVar(self, "taskId", 1);
                    questActivateTask(questid, 1, player);
                }
            }
            obj_id waypoint = getObjIdObjVar(self, "pickup_waypoint");
            if (!isIdValid(waypoint))
            {
                waypoint = createWaypointInDatapad(player, loc);
            }
            if (isIdValid(waypoint))
            {
                setWaypointVisible(waypoint, true);
                setWaypointActive(waypoint, true);
                setWaypointLocation(waypoint, loc);
                setWaypointName(waypoint, waypoint_prefix + " Rendezvous");
                setWaypointColor(waypoint, "space");
                setObjVar(self, "pickup_waypoint", waypoint);
            }
            setObjVar(self, "pickup_transform", wptrans);
            int taskId = getIntObjVar(self, "taskId");
            questSetQuestTaskLocation(player, "spacequest/" + questType + "/" + questName, taskId, loc);
            space_quest._setQuestInProgress(self);
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "found_loc_" + wp);
            questUpdate(self, foundLocation);
            String name = questName + ":" + destNav;
            setObjVar(self, "loc", name);
            addLocationTarget3d(player, name, loc, space_quest.PATROL_NAV_RADIUS);
        }
        setObjVar(self, "initialized", 1);
        messageTo(self, "deliveryNoPickupAttack", null, 10.f, false);
    }
    public int arrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        location loc = params.getLocation("loc");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (hasObjVar(self, "delivering"))
        {
            int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
            if (questid != 0)
            {
                if (hasObjVar(self, "delivery_nopickup"))
                {
                    setObjVar(self, "taskId", 2);
                    questActivateTask(questid, 2, player);
                    if (questIsTaskActive(questid, 1, player))
                    {
                        questCompleteTask(questid, 1, player);
                    }
                }
                else 
                {
                    setObjVar(self, "taskId", 4);
                    questActivateTask(questid, 4, player);
                    if (questIsTaskActive(questid, 3, player))
                    {
                        questCompleteTask(questid, 3, player);
                    }
                }
            }
            String destNav = getStringObjVar(self, "deliveryPoint");
            java.util.StringTokenizer st = new java.util.StringTokenizer(destNav, ":");
            String scene = st.nextToken();
            destNav = st.nextToken();
            destNav = questName + ":" + destNav;
            if (destNav.equals(name))
            {
                string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "arrived_at_delivery");
                questUpdate(self, foundLocation);
                messageTo(self, "warpDeliveryShipDelay", params, 5 + rand(1, 5), false);
            }
        }
        else if (!hasObjVar(self, "pickup"))
        {
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
            String destNav = getStringObjVar(self, "pickupPoint");
            try {
                java.util.StringTokenizer st = new java.util.StringTokenizer(destNav, ":");
                String scene = st.nextToken();
                destNav = st.nextToken();
                destNav = questName + ":" + destNav;
            }
            catch(Exception e){
                LOG("space_quest","Bad quest logic:  Quest (space/quest/" + questType + "/" + questName + ") contains invalid pickup point while trying to parse string (" +
                getStringObjVar(self, "pickupPoint") + ") for player (" + player + ":" + getPlayerFullName(player) + "), name (" + name + "), going to location (" + loc + ").");
            }
            if (destNav.equals(name))
            {
                string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "arrived_at_pickup");
                questUpdate(self, foundLocation);
                messageTo(self, "warpPickupShipDelay", params, 5 + rand(1, 5), false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int warpPickupShipDelay(obj_id self, dictionary params) throws InterruptedException
    {
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        location loc = params.getLocation("loc");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        space_quest._setQuestInProgress(self);
        setObjVar(self, "pickup", 1);
        obj_id ship = warpInShip(self, "pickup");
        ship_ai.unitSetLeashDistance(ship, 16000);
        attachScript(ship, "space.quest_logic.delivery_ship");
        setObjVar(ship, "quest", self);
        setObjVar(self, "pickup_ship", ship);
        setObjVar(self, "ship", ship);
        utils.setScriptVar(ship, "dockable", 1);
        setLookAtTarget(player, ship);
        space_quest._addMissionCriticalShip(player, self, ship);
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        setObjVar(ship, "objMissionOwner", player);
        ship_ai.unitAddExclusiveAggro(ship, player);
        obj_id playership = space_transition.getContainingShip(player);
        ship_ai.unitSetAutoAggroImmuneTime(playership, rand(3.0f, 6.0f));
        dictionary oparams = new dictionary();
        oparams.put("delivery", false);
        messageTo(self, "sayHello", oparams, 2.f, false);
        playClientEffectObj(player, SOUND_SPAWN_ESCORT, player, "");
        clearMissionWaypoint(self);
        return SCRIPT_CONTINUE;
    }
    public obj_id warpInShip(obj_id self, String var) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String shipType = getStringObjVar(self, var + "Ship");
        transform trans = getTransformObjVar(self, "pickup_transform");
        transform spawnLoc = space_quest.getRandomPositionInSphere(trans, 100, 200);
        return space_create.createShipHyperspace(shipType, spawnLoc);
    }
    public int warpDeliveryShipDelay(obj_id self, dictionary params) throws InterruptedException
    {
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        location loc = params.getLocation("loc");
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        space_quest._setQuestInProgress(self);
        removeObjVar(self, "delivering");
        setObjVar(self, "dropoff", 1);
        obj_id ship = warpInShip(self, "delivery");
        ship_ai.unitSetLeashDistance(ship, 16000);
        attachScript(ship, "space.quest_logic.delivery_ship");
        setObjVar(ship, "quest", self);
        setObjVar(self, "delivery_ship", ship);
        setObjVar(self, "ship", ship);
        utils.setScriptVar(ship, "dockable", 1);
        setLookAtTarget(player, ship);
        space_quest._addMissionCriticalShip(player, self, ship);
        ship_ai.unitSetAttackOrders(ship, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        setObjVar(ship, "objMissionOwner", player);
        ship_ai.unitAddExclusiveAggro(ship, player);
        dictionary oparams = new dictionary();
        oparams.put("delivery", true);
        messageTo(self, "sayHello", oparams, 2.f, false);
        playClientEffectObj(player, SOUND_SPAWN_ESCORT, player, "");
        clearMissionWaypoint(self);
        return SCRIPT_CONTINUE;
    }
    public int sayHello(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = getObjIdObjVar(self, "ship");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        string_id hello = null;
        if (params.getBoolean("delivery"))
        {
            hello = new string_id("spacequest/" + questType + "/" + questName, "hello_d");
        }
        else 
        {
            hello = new string_id("spacequest/" + questType + "/" + questName, "hello_p");
        }
        prose_package pp = prose.getPackage(hello, 0);
        space_quest.groupTaunt(ship, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int dockingStarted(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (hasObjVar(self, "pickup"))
        {
            obj_id target = params.getObjId("target");
            obj_id ship = getObjIdObjVar(self, "pickup_ship");
            if (target != ship)
            {
                return SCRIPT_CONTINUE;
            }
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "docking_started_pickup");
            prose_package pp = prose.getPackage(status_update, 0);
            space_quest.groupTaunt(ship, player, pp);
        }
        else if (hasObjVar(self, "dropoff"))
        {
            obj_id target = params.getObjId("target");
            obj_id ship = getObjIdObjVar(self, "delivery_ship");
            if (target != ship)
            {
                return SCRIPT_CONTINUE;
            }
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "docking_started_delivery");
            prose_package pp = prose.getPackage(status_update, 0);
            space_quest.groupTaunt(ship, player, pp);
        }
        return SCRIPT_OVERRIDE;
    }
    public int dockingComplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        if (hasObjVar(self, "pickup"))
        {
            obj_id target = params.getObjId("target");
            obj_id ship = getObjIdObjVar(self, "pickup_ship");
            if (target != ship)
            {
                return SCRIPT_CONTINUE;
            }
            removeObjVar(self, "pickup");
            setObjVar(self, "delivering", 1);
            utils.removeScriptVar(ship, "dockable");
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "docking_complete_pickup");
            prose_package pp = prose.getPackage(status_update, 0);
            space_quest.groupTaunt(ship, player, pp);
            transform t = getTransform_o2w(ship);
            transform goalLoc = space_quest.getRandomPositionInSphere(t, 1000, 1200);
            ship_ai.unitMoveTo(ship, goalLoc);
            messageTo(ship, "lightspeedJump", null, 15.f, false);
            messageTo(self, "findDeliveryPoint", null, 1.f, false);
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
            int attackPeriod = getIntObjVar(self, "attackPeriod");
            if (attackPeriod > 0)
            {
                messageTo(self, "launchAttack", null, attackPeriod, false);
            }
        }
        else if (hasObjVar(self, "dropoff"))
        {
            obj_id target = params.getObjId("target");
            obj_id ship = getObjIdObjVar(self, "delivery_ship");
            if (target != ship)
            {
                return SCRIPT_CONTINUE;
            }
            removeObjVar(self, "dropoff");
            utils.removeScriptVar(ship, "dockable");
            string_id status_update = new string_id("spacequest/" + questType + "/" + questName, "docking_complete_delivery");
            prose_package pp = prose.getPackage(status_update, 0);
            space_quest.groupTaunt(ship, player, pp);
            int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
            if (questid != 0)
            {
                if (hasObjVar(self, "delivery_nopickup"))
                {
                    if (questIsTaskActive(questid, 2, player))
                    {
                        questCompleteTask(questid, 2, player);
                    }
                }
                else 
                {
                    if (questIsTaskActive(questid, 4, player))
                    {
                        questCompleteTask(questid, 4, player);
                    }
                }
            }
            transform t = getTransform_o2w(ship);
            transform goalLoc = space_quest.getRandomPositionInSphere(t, 1000, 1200);
            ship_ai.unitMoveTo(ship, goalLoc);
            messageTo(ship, "lightspeedJump", null, 15.f, false);
            messageTo(self, "finishQuest", null, 2.f, false);
        }
        return SCRIPT_OVERRIDE;
    }
    public int findDeliveryPoint(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        messageTo(self, "initializedQuestPlayer", outparams, 1.f, false);
        return SCRIPT_OVERRIDE;
    }
    public void clearMissionWaypoint(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        obj_id waypoint = getObjIdObjVar(self, "pickup_waypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(self, "pickup_waypoint");
        dictionary params = new dictionary();
        String loc = getStringObjVar(self, "loc");
        if (loc != null)
        {
            removeLocationTarget(player, loc);
        }
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
    public void questAborted(obj_id self) throws InterruptedException
    {
        cleanUpTransports(self, false);
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestAborted(player, self);
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        cleanUpTransports(self, true);
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestWon(player, self);
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
    public int finishQuest(obj_id self, dictionary params) throws InterruptedException
    {
        questCompleted(self);
        return SCRIPT_CONTINUE;
    }
    public void cleanUpTransports(obj_id self, boolean keepDelivery) throws InterruptedException
    {
        obj_id ship = getObjIdObjVar(self, "pickup_ship");
        if (isIdValid(ship) && exists(ship))
        {
            destroyObjectHyperspace(ship);
        }
        ship = getObjIdObjVar(self, "delivery_ship");
        if (isIdValid(ship) && exists(ship) && !keepDelivery)
        {
            destroyObjectHyperspace(ship);
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
    public int launchAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "initialized"))
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
            transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
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
            dictionary dict = new dictionary();
            dict.put("player", space_transition.getContainingShip(player));
            dict.put("newship", newship);
            messageTo(self, "attackPlayerShip", dict, 4.0f, false);
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
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        messageTo(self, "launchAttack", null, attackPeriod, false);
        return SCRIPT_CONTINUE;
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id ship = getObjIdObjVar(self, "ship");
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id deadship = params.getObjId("ship");
        obj_id targets[] = getObjIdArrayObjVar(self, "targets");
        int deadships = getIntObjVar(self, "deadships");
        boolean launchWave = false;
        for (int i = 0; i < targets.length; i++)
        {
            if (deadship == targets[i])
            {
                deadships++;
                setObjVar(self, "deadships", deadships);
                space_quest._removeMissionCriticalShip(player, self, deadship);
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
                    int attackCount = getIntObjVar(self, "attackCount");
                    attackCount++;
                    setObjVar(self, "attackCount", attackCount);
                    checkSpecialEvent(self, player, attackCount);
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
            messageTo(self, "launchAttack", null, 0, false);
            removeObjVar(self, "new_wave_pending");
        }
        return SCRIPT_OVERRIDE;
    }
    public boolean checkSpecialEvent(obj_id self, obj_id player, int attackCount) throws InterruptedException
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
        if (triggerOn != attackCount)
        {
            return false;
        }
        dictionary params = new dictionary();
        params.put("quest", self);
        messageTo(player, "doSpecialEvent", params, triggerDelay, false);
        return false;
    }
    public int deliveryTargetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = params.getObjId("ship");
        obj_id ship1 = getObjIdObjVar(self, "pickup_ship");
        obj_id ship2 = getObjIdObjVar(self, "delivery_ship");
        if ((hasObjVar(self, "pickup") && (ship == ship1)) || (hasObjVar(self, "dropoff") && (ship == ship2)))
        {
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            string_id foundLocation = new string_id("spacequest/" + questType + "/" + questName, "failed_destroyed");
            questUpdate(self, foundLocation);
            questFailed(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void questFailed(obj_id self) throws InterruptedException
    {
        cleanUpTransports(self, false);
        clearMissionWaypoint(self);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestFailed(player, self);
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            questFailed(self);
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
}
