package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.lang.Math;
import java.util.Random;
import script.vector;
import script.library.ship_ai;
import script.library.space_create;
import script.library.space_transition;
import script.library.load_test;

public class mbogue_test extends script.base_script
{
    public mbogue_test()
    {
    }
    public static final String s_logLabel = "space_debug_ai";
    public static final String s_tooFewParameters = "too few parameters\n";
    public static final String s_usageSpaceUnitAddPatrolPath = "Usage: spaceUnitAddPatrolPath <unitObjId>";
    public static final String s_usageSpaceUnitClearPatrolPath = "Usage: spaceUnitClearPatrolPath <unitObjId>";
    public static final String s_usageSpaceUnitFollow = "Usage: spaceUnitFollow <unitObjId> <targetObjId> <x> <y> <z> <offset>";
    public static final String s_usageAiAttack = "Usage: ai_attack <unitObjId> <targetObjId>";
    public static final String s_usageSpaceUnitSetIdle = "Usage: spaceUnitSetIdle <unitObjId>";
    public static final String s_usageSpaceUnitGetPilotType = "Usage: spaceUnitGetPilotType <unitObjId>";
    public static final String s_usageSpaceUnitGetBehavior = "Usage: spaceUnitGetBehavior <unitObjId>";
    public static final String s_usageSpaceUnitSetAttackOrders = "Usage: spaceUnitSetAttackOrders <unitObjId> <int>";
    public static final String s_usageSpaceUnitSetTargetOrders = "Usage: spaceUnitSetTargetOrders <unitObjId> <int>";
    public static final String s_usageSpaceUnitSetSquad = "Usage: spaceUnitSetSquad <unitObjId> <int>";
    public static final String s_usageSpaceSquadAddPatrolPath = "Usage: spaceSquadAddPatrolPath <int>";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.mbogue_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(text);
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();
                LOG("space_debug_ai", "command is: " + command + " ---------------------------------------");
                if (command.equalsIgnoreCase("ai"))
                {
                    debugConsoleMsg(self, "** ai usage **");
                    debugConsoleMsg(self, s_usageSpaceUnitAddPatrolPath);
                    debugConsoleMsg(self, s_usageSpaceUnitClearPatrolPath);
                    debugConsoleMsg(self, s_usageSpaceUnitFollow);
                    debugConsoleMsg(self, s_usageAiAttack);
                    debugConsoleMsg(self, s_usageSpaceUnitSetIdle);
                    debugConsoleMsg(self, s_usageSpaceUnitSetSquad);
                }
                else if (command.equalsIgnoreCase("spaceUnitAddPatrolPath"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        debugSpaceUnitAddPatrolPath(self, unit, ship_ai.createPatrolPathCircle(new vector(0.0f, 0.0f, 0.0f), 100.0f));
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitAddPatrolPath);
                    }
                }
                else if (command.equalsIgnoreCase("spaceUnitClearPatrolPath"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitClearPatrolPath(unitObjectId);
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitClearPatrolPath);
                    }
                }
                else if (command.equalsIgnoreCase("spaceUnitFollow"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        if (tokenizer.hasMoreTokens())
                        {
                            obj_id targetObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                            if (tokenizer.hasMoreTokens())
                            {
                                final float x = java.lang.Float.parseFloat(tokenizer.nextToken());
                                if (tokenizer.hasMoreTokens())
                                {
                                    final float y = java.lang.Float.parseFloat(tokenizer.nextToken());
                                    if (tokenizer.hasMoreTokens())
                                    {
                                        final float z = java.lang.Float.parseFloat(tokenizer.nextToken());
                                        if (tokenizer.hasMoreTokens())
                                        {
                                            error = false;
                                            float offset = java.lang.Float.parseFloat(tokenizer.nextToken());
                                            debugSpaceUnitFollow(self, unitObjectId, targetObjectId, new vector(x, y, z), offset);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitFollow);
                    }
                }
                else if (command.equalsIgnoreCase("ai_attack"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        Vector targetObjectIdList = new Vector();
                        while (tokenizer.hasMoreTokens())
                        {
                            targetObjectIdList.addElement(obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken())));
                        }
                        if (targetObjectIdList.size() == 1)
                        {
                            error = false;
                            obj_id targetObjectId = (obj_id)targetObjectIdList.elementAt(0);
                            ship_ai.unitAddDamageTaken(unitObjectId, targetObjectId, 100000.0f);
                            debugConsoleMsg(self, unitObjectId + " is attacking " + targetObjectId);
                        }
                        else if (targetObjectIdList.size() > 1)
                        {
                            error = false;
                            obj_id targets[] = new obj_id[targetObjectIdList.size()];
                            targetObjectIdList.toArray(targets);
                            ship_ai.spaceAttack(unitObjectId, targets);
                            debugConsoleMsg(self, unitObjectId + " is attacking " + targetObjectIdList.size() + " targets");
                        }
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageAiAttack);
                    }
                }
                else if (command.equalsIgnoreCase("ai_idle"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitIdle(unitObjectId);
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitSetIdle);
                    }
                }
                else if (command.equalsIgnoreCase("spaceUnitGetPilotType"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        debugConsoleMsg(self, "unit: " + unitObjectId + " pilotType: " + ship_ai.unitGetPilotType(unitObjectId));
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitGetPilotType);
                    }
                }
                else if (command.equalsIgnoreCase("spaceUnitGetBehavior"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        final int behavior = ship_ai.unitGetBehavior(unitObjectId);
                        debugConsoleMsg(self, "unit: " + unitObjectId + " behavior: " + behavior + " - " + ship_ai.unitGetBehaviorString(behavior));
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitGetBehavior);
                    }
                }
                else if (command.equalsIgnoreCase("spaceUnitSetAttackOrders"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        final obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        if (tokenizer.hasMoreTokens())
                        {
                            error = false;
                            final int attackOrders = java.lang.Integer.parseInt(tokenizer.nextToken());
                            ship_ai.unitSetAttackOrders(unitObjectId, attackOrders);
                            debugConsoleMsg(self, "unit: " + unitObjectId + " attack orders: " + attackOrders + " - " + ship_ai.unitGetAttackOrdersString(attackOrders));
                        }
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitSetAttackOrders);
                    }
                }
                else if (command.equalsIgnoreCase("spaceUnitSetSquad"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        final obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        if (tokenizer.hasMoreTokens())
                        {
                            error = false;
                            final int squad = java.lang.Integer.parseInt(tokenizer.nextToken());
                            ship_ai.unitSetSquadId(unitObjectId, squad);
                            debugConsoleMsg(self, "unit: " + unitObjectId + " squad: " + squad);
                        }
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceUnitSetSquad);
                    }
                }
                else if (command.equalsIgnoreCase("spaceSquadAddPatrolPath"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        final int squad = java.lang.Integer.parseInt(tokenizer.nextToken());
                        transform path[] = new transform[4];
                        path[0] = transform.identity.setPosition_p(-100, 0, -100);
                        path[1] = transform.identity.setPosition_p(100, 0, -100);
                        path[2] = transform.identity.setPosition_p(100, 0, 100);
                        path[3] = transform.identity.setPosition_p(-100, 0, 100);
                        ship_ai.squadAddPatrolPath(squad, path);
                        debugConsoleMsg(self, "spaceSquadAddPatrolPath - " + "squad: " + squad);
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters + s_usageSpaceSquadAddPatrolPath);
                    }
                }
                else if (command.equalsIgnoreCase("ai_superweapon"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        setShipWeaponDamageMinimum(unitObjectId, ship_chassis_slot_type.SCST_weapon_0, 400000);
                        setShipWeaponDamageMaximum(unitObjectId, ship_chassis_slot_type.SCST_weapon_0, 500000);
                        debugConsoleMsg(self, unitObjectId + " now has a super weapon");
                    }
                }
                else if (command.equalsIgnoreCase("ai_moveto"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        final obj_id unitObjectId = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        transform path[] = new transform[4];
                        path[0] = transform.identity.setPosition_p(50.0f, 0.0f, 0.0f);
                        path[1] = transform.identity.setPosition_p(00.0f, 50.0f, 0.0f);
                        path[2] = transform.identity.setPosition_p(00.0f, 0.0f, 50.0f);
                        path[3] = transform.identity.setPosition_p(50.0f, 0.0f, 0.0f);
                        ship_ai.unitMoveTo(unitObjectId, path);
                    }
                    if (error)
                    {
                    }
                }
                else if (command.equalsIgnoreCase("ai_patrol"))
                {
                    ship_ai.unitAddPatrolPath(createUnit(self, "tiefighter", 200.0f), ship_ai.createPatrolPathCircle(new vector(0.0f, 0.0f, 0.0f), 100.0f));
                    ship_ai.unitAddPatrolPath(createUnit(self, "tiefighter", 200.0f), ship_ai.createPatrolPathCircle(new vector(0.0f, 10.0f, 0.0f), 100.0f));
                }
                else if (command.equalsIgnoreCase("ai_single"))
                {
                    boolean error = true;
                    String shipName;
                    location selfLocation = getLocation(space_transition.getContainingShip(self));
                    vector anchorPosition_w = new vector(selfLocation.x, selfLocation.y, selfLocation.z);
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 1)
                    {
                        error = false;
                        shipName = tokenizer.nextToken();
                        final float createRadius = 100.0f;
                        obj_id unit = createUnit(self, shipName, createRadius, anchorPosition_w);
                        final float pathRadius = 200.0f;
                        debugSpaceUnitAddPatrolPath(self, unit, ship_ai.createPatrolPathCircle(anchorPosition_w, pathRadius));
                        ship_ai.unitSetLeashDistance(unit, 16000.0f);
                        debugConsoleMsg(self, "ai_single: Creating a single " + shipName);
                    }
                    if (error)
                    {
                        debugConsoleMsg(self, "************* ERROR *************");
                        debugConsoleMsg(self, "usage: ai_single <shipType>");
                    }
                }
                else if (command.equalsIgnoreCase("ai_squad"))
                {
                    boolean error = true;
                    final float createRadius = 200.0f;
                    String shipName;
                    location selfLocation = getLocation(space_transition.getContainingShip(self));
                    vector anchorPosition_w = new vector(selfLocation.x, selfLocation.y, selfLocation.z);
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount >= 1)
                    {
                        shipName = tokenizer.nextToken();
                        error = false;
                        int squadSize = 4;
                        if (tokenCount == 2)
                        {
                            squadSize = java.lang.Integer.parseInt(tokenizer.nextToken());
                        }
                        final obj_id squadLeader = createUnit(self, shipName, createRadius, anchorPosition_w);
                        for (int i = 0; i < squadSize - 1; ++i)
                        {
                            obj_id unit = createUnit(self, shipName, createRadius, anchorPosition_w);
                            ship_ai.unitSetSquadId(unit, _spaceUnitGetSquadId(squadLeader));
                        }
                        ship_ai.squadSetFormationRandom(_spaceUnitGetSquadId(squadLeader));
                        ship_ai.squadSetFormationSpacing(_spaceUnitGetSquadId(squadLeader), 1.0f);
                        ship_ai.squadPatrol(_spaceUnitGetSquadId(squadLeader), createPatrolPathSpiral(anchorPosition_w, 200.0f));
                        debugConsoleMsg(self, "ai_squad: Creating a squad of " + squadSize + " " + shipName);
                    }
                    if (error)
                    {
                        debugConsoleMsg(self, "************* ERROR *************");
                        debugConsoleMsg(self, "usage: ai_squad <shipType>");
                    }
                }
                else if (command.equalsIgnoreCase("ai_squadtest"))
                {
                    boolean error = true;
                    final float createRadius = 200.0f;
                    String shipName;
                    location selfLocation = getLocation(space_transition.getContainingShip(self));
                    vector anchorPosition_w = new vector(selfLocation.x, selfLocation.y, selfLocation.z);
                    int squadSize = 4;
                    final obj_id squadLeader = createUnit(self, "vortex_mission_4_shuttle", createRadius, anchorPosition_w);
                    for (int i = 0; i < squadSize - 1; ++i)
                    {
                        obj_id unit = createUnit(self, "vortex_mission_4_guard", createRadius, anchorPosition_w);
                        ship_ai.unitSetSquadId(unit, _spaceUnitGetSquadId(squadLeader));
                    }
                    ship_ai.squadSetFormationRandom(_spaceUnitGetSquadId(squadLeader));
                    ship_ai.squadSetFormationSpacing(_spaceUnitGetSquadId(squadLeader), 1.0f);
                    ship_ai.squadPatrol(_spaceUnitGetSquadId(squadLeader), createPatrolPathSpiral(anchorPosition_w, 200.0f));
                    debugConsoleMsg(self, "ai_squad: Creating a squad of " + squadSize + " ");
                }
                else if (command.equalsIgnoreCase("ai_clear_random"))
                {
                    obj_id objectList[] = getObjectsInRange(self, 16000.0f);
                    int count = 0;
                    for (int i = 0; i < objectList.length; ++i)
                    {
                        if (isGameObjectTypeOf(objectList[i], GOT_ship) && !isGameObjectTypeOf(objectList[i], GOT_ship_station) && !(getTemplateName(objectList[i])).startsWith("object/ship/player"))
                        {
                            Random random = new Random();
                            if ((Math.abs(random.nextInt()) % 2) == 0)
                            {
                                debugDestroyObject(self, objectList[i]);
                                ++count;
                            }
                        }
                    }
                    debugConsoleMsg(self, count + " object destroyed");
                }
                else if (command.equalsIgnoreCase("ai_clear"))
                {
                    obj_id objectList[] = getObjectsInRange(self, 16000.0f);
                    int count = 0;
                    for (int i = 0; i < objectList.length; ++i)
                    {
                        if (isGameObjectTypeOf(objectList[i], GOT_ship) && !isGameObjectTypeOf(objectList[i], GOT_ship_station) && !(getTemplateName(objectList[i])).startsWith("object/ship/player"))
                        {
                            debugDestroyObject(self, objectList[i]);
                            ++count;
                        }
                    }
                    debugConsoleMsg(self, count + " object destroyed");
                }
                else if (command.equalsIgnoreCase("ai_getDockTransform"))
                {
                    if (tokenizer.hasMoreTokens())
                    {
                        obj_id dockTarget = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        transform dockTransform = ship_ai.unitGetDockTransform(dockTarget, self);
                        debugConsoleMsg(self, "dockTarget: " + dockTarget + " dockingUnit: " + self + " dockTransform: " + dockTransform);
                    }
                }
                else if (command.equalsIgnoreCase("ai_test"))
                {
                    obj_id unit = createUnit(self, "xwing", 100.0f);
                    ship_ai.squadRemoveUnit(unit);
                }
                else if (command.equalsIgnoreCase("ai_guard"))
                {
                    final float createRadius = 200.0f;
                    final float pathRadius = 400.0f;
                    String formationShip;
                    if (tokenizer.hasMoreTokens())
                    {
                        formationShip = tokenizer.nextToken();
                    }
                    else 
                    {
                        formationShip = new String("tiefighter");
                    }
                    final obj_id guardedUnit = createUnit(self, "tiefighter", createRadius);
                    ship_ai.unitSetAttackOrders(guardedUnit, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
                    ship_ai.unitLoiter(guardedUnit, transform.identity, 50.0f, 50.0f);
                    
                    {
                        final int squadLeaderSquad = ship_ai.squadCreateSquadId();
                        final int squadCount = 1;
                        final int unitCount = 4;
                        for (int i = 0; i < unitCount; ++i)
                        {
                            obj_id unit = createUnit(self, formationShip, createRadius);
                            ship_ai.unitSetSquadId(unit, squadLeaderSquad);
                            ship_ai.unitSetAttackOrders(unit, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
                        }
                        ship_ai.squadSetFormation(squadLeaderSquad, ship_ai.FORMATION_WALL);
                        ship_ai.squadSetFormationSpacing(squadLeaderSquad, 1.0f);
                        ship_ai.squadAddPatrolPath(squadLeaderSquad, ship_ai.createPatrolPathCircle(new vector(2000.0f, 0.0f, 0.0f), 200.0f));
                        ship_ai.squadSetGuardTarget(squadLeaderSquad, ship_ai.unitGetSquadId(guardedUnit));
                    }
                }
                else if (command.equalsIgnoreCase("ai_dock"))
                {
                    boolean error = true;
                    if (tokenizer.hasMoreTokens())
                    {
                        error = false;
                        final obj_id spaceStation = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        String shipName;
                        if (tokenizer.hasMoreTokens())
                        {
                            shipName = tokenizer.nextToken();
                        }
                        else 
                        {
                            shipName = new String("yt1300");
                        }
                        final obj_id dockingUnit = createUnit(self, shipName, 200.0f);
                        ship_ai.unitDock(dockingUnit, spaceStation, 10.0f);
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters);
                    }
                }
                else if (command.equalsIgnoreCase("ai_dockWith"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 1)
                    {
                        obj_id playerShip = space_transition.getContainingShip(self);
                        final obj_id dockTarget = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitDock(playerShip, dockTarget, 10.0f);
                        error = false;
                    }
                    else if (tokenCount == 2)
                    {
                        final obj_id dockingUnit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        final obj_id dockTarget = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitDock(dockingUnit, dockTarget, 10.0f);
                        error = false;
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters);
                    }
                }
                else if (command.equalsIgnoreCase("ai_singleDockWith"))
                {
                    boolean error = true;
                    String shipName;
                    location selfLocation = getLocation(space_transition.getContainingShip(self));
                    vector anchorPosition_w = new vector(selfLocation.x, selfLocation.y, selfLocation.z);
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 2)
                    {
                        error = false;
                        shipName = tokenizer.nextToken();
                        final float createRadius = 100.0f;
                        obj_id unit = createUnit(self, shipName, createRadius, anchorPosition_w);
                        final float pathRadius = 200.0f;
                        debugSpaceUnitAddPatrolPath(self, unit, ship_ai.createPatrolPathCircle(anchorPosition_w, pathRadius));
                        final obj_id dockTarget = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitDock(unit, dockTarget, 10.0f);
                        debugConsoleMsg(self, "ai_singleDockWith: Creating a single " + shipName);
                    }
                    if (error)
                    {
                        debugConsoleMsg(self, "************* ERROR *************");
                        debugConsoleMsg(self, "usage: ai_singleDockWith <shipType> <dockTarget>");
                    }
                }
                else if (command.equalsIgnoreCase("ai_isAutoAggroImmune"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 1)
                    {
                        final obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        debugConsoleMsg(self, (ship_ai.unitIsAutoAggroImmune(unit) ? "[ENABLED]" : "[DISABLED]") + " ship aggro immunity " + unit);
                        error = false;
                    }
                    else 
                    {
                        obj_id playerShip = space_transition.getContainingShip(self);
                        debugConsoleMsg(self, (ship_ai.unitIsAutoAggroImmune(playerShip) ? "[ENABLED]" : "[DISABLED]") + " player ship aggro immunity " + playerShip);
                        error = false;
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters);
                    }
                }
                else if (command.equalsIgnoreCase("ai_aggroImmune"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 1)
                    {
                        final obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitSetAutoAggroImmune(unit, !ship_ai.unitIsAutoAggroImmune(unit));
                        debugConsoleMsg(self, (ship_ai.unitIsAutoAggroImmune(unit) ? "[ENABLED]" : "[DISABLED]") + " player ship aggro immunity " + unit);
                        error = false;
                    }
                    else 
                    {
                        obj_id playerShip = space_transition.getContainingShip(self);
                        ship_ai.unitSetAutoAggroImmune(playerShip, !ship_ai.unitIsAutoAggroImmune(playerShip));
                        debugConsoleMsg(self, (ship_ai.unitIsAutoAggroImmune(playerShip) ? "[ENABLED]" : "[DISABLED]") + " player ship aggro immunity " + playerShip);
                        error = false;
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters);
                    }
                }
                else if (command.equalsIgnoreCase("ai_immune"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 1)
                    {
                        final obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitRemoveFromAllAttackTargetLists(unit);
                        ship_ai.unitSetAutoAggroImmune(unit, true);
                        debugConsoleMsg(self, (ship_ai.unitIsAutoAggroImmune(unit) ? "[ENABLED]" : "[DISABLED]") + " player ship aggro immunity " + unit);
                        error = false;
                    }
                    else 
                    {
                        obj_id playerShip = space_transition.getContainingShip(self);
                        ship_ai.unitRemoveFromAllAttackTargetLists(playerShip);
                        ship_ai.unitSetAutoAggroImmune(playerShip, true);
                        debugConsoleMsg(self, (ship_ai.unitIsAutoAggroImmune(playerShip) ? "[ENABLED]" : "[DISABLED]") + " player ship aggro immunity " + playerShip);
                        error = false;
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters);
                    }
                }
                else if (command.equalsIgnoreCase("ai_holdFire"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount == 1)
                    {
                        final obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitSetAttackOrders(unit, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
                        error = false;
                    }
                    if (error)
                    {
                        LOG(s_logLabel, s_tooFewParameters);
                    }
                }
                else if (command.equalsIgnoreCase("ai_testGuard"))
                {
                    boolean error = true;
                    final float squadSpacingRadius = 1200.0f;
                    String unitName;
                    location selfLocation = getLocation(space_transition.getContainingShip(self));
                    vector anchorPosition_w = new vector(selfLocation.x, selfLocation.y, selfLocation.z);
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount >= 1)
                    {
                        error = false;
                        unitName = tokenizer.nextToken();
                        obj_id unitToGuard = createUnit(self, unitName, 0.0f, anchorPosition_w);
                        ship_ai.unitSetAttackOrders(unitToGuard, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
                        debugSpaceUnitAddPatrolPath(self, unitToGuard, ship_ai.createPatrolPathCircle(anchorPosition_w, 200.0f));
                        final int unitToGuardSquadId = ship_ai.unitGetSquadId(unitToGuard);
                        final int squadCount = 1;
                        int squadSize = 4;
                        if (tokenCount == 2)
                        {
                            squadSize = java.lang.Integer.parseInt(tokenizer.nextToken());
                        }
                        for (int index = 0; index < squadCount; ++index)
                        {
                            final float radian = (float)Math.PI * 2.0f * ((float)index / (float)squadCount);
                            final float x = anchorPosition_w.x + (float)Math.sin(radian) * squadSpacingRadius;
                            final float y = anchorPosition_w.y;
                            final float z = anchorPosition_w.z + (float)Math.cos(radian) * squadSpacingRadius;
                            final int newSquad = createPatrollingSquad(self, unitName, squadSize, new vector(x, y, z));
                            ship_ai.squadSetGuardTarget(newSquad, unitToGuardSquadId);
                        }
                    }
                    if (error)
                    {
                        debugConsoleMsg(self, "************* ERROR *************");
                        debugConsoleMsg(self, "usage: ai_testGuard <shipType> <guardSquadSize>");
                    }
                }
                else if (command.equalsIgnoreCase("ai_addExclusiveAggro"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount >= 2)
                    {
                        error = false;
                        final obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        final obj_id pilot = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitAddExclusiveAggro(unit, pilot);
                    }
                    if (error)
                    {
                        debugConsoleMsg(self, "************* ERROR *************");
                        debugConsoleMsg(self, "usage: ai_addExclusiveAggro <unit> <pilot>");
                    }
                }
                else if (command.equalsIgnoreCase("ai_removeExclusiveAggro"))
                {
                    boolean error = true;
                    final int tokenCount = tokenizer.countTokens();
                    if (tokenCount >= 2)
                    {
                        error = false;
                        final obj_id unit = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        final obj_id pilot = obj_id.getObjId(java.lang.Long.parseLong(tokenizer.nextToken()));
                        ship_ai.unitRemoveExclusiveAggro(unit, pilot);
                    }
                    if (error)
                    {
                        debugConsoleMsg(self, "************* ERROR *************");
                        debugConsoleMsg(self, "usage: ai_removeExclusiveAggro <unit> <pilot>");
                    }
                }
                else if (command.equalsIgnoreCase("mb_overt"))
                {
                    space_transition.setPlayerOvert(self);
                }
                else if (command.equalsIgnoreCase("mb_rebel"))
                {
                    load_test.revokeSkills(self, load_test.NEUTRAL_PILOT);
                    load_test.revokeSkills(self, load_test.IMPERIAL_PILOT);
                    load_test.grantSkills(self, load_test.REBEL_PILOT);
                }
                else if (command.equalsIgnoreCase("mb_imperial"))
                {
                    load_test.revokeSkills(self, load_test.NEUTRAL_PILOT);
                    load_test.revokeSkills(self, load_test.REBEL_PILOT);
                    load_test.grantSkills(self, load_test.IMPERIAL_PILOT);
                }
                else if (command.equalsIgnoreCase("mb_neutral"))
                {
                    load_test.revokeSkills(self, load_test.REBEL_PILOT);
                    load_test.revokeSkills(self, load_test.IMPERIAL_PILOT);
                    load_test.grantSkills(self, load_test.NEUTRAL_PILOT);
                }
                else if (command.equalsIgnoreCase("isOn"))
                {
                    obj_id lookAtTarget = getLookAtTarget(self);
                    debugConsoleMsg(self, lookAtTarget + ": hasCondition(CONDITION_ON): " + (hasCondition(lookAtTarget, CONDITION_ON) ? "yes" : "no"));
                }
                else 
                {
                    LOG("space_debug_ai", "unknown command");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public vector getRandomPosition(float radius) throws InterruptedException
    {
        final float x = radius * 2.0f * (0.5f - (float)Math.random());
        final float y = radius * 2.0f * (0.5f - (float)Math.random());
        final float z = radius * 2.0f * (0.5f - (float)Math.random());
        return new vector(x, y, z);
    }
    public obj_id createUnit(obj_id self, String unitName, float radius, vector position) throws InterruptedException
    {
        final vector randomPosition = new vector(getRandomPosition(radius));
        final float x = position.x + randomPosition.x;
        final float y = position.y + randomPosition.y;
        final float z = position.z + randomPosition.z;
        obj_id unitId = space_create.createShip(unitName, transform.identity.move_p(new vector(x, y, z)));
        return unitId;
    }
    public obj_id createUnit(obj_id self, String unitName, float radius) throws InterruptedException
    {
        obj_id unitId = createUnit(self, unitName, radius, vector.zero);
        return unitId;
    }
    public void debugSpaceUnitFollow(obj_id self, obj_id unit, obj_id followedUnit, vector direction, float offset) throws InterruptedException
    {
        debugConsoleMsg(self, "spaceUnitFollow() unit: " + unit + " followedUnit: " + followedUnit + " (" + direction.x + ", " + direction.y + ", " + direction.z + ") offset: " + offset);
        ship_ai.unitFollow(unit, followedUnit, direction, offset);
    }
    public void debugDestroyObject(obj_id self, obj_id object) throws InterruptedException
    {
        destroyObject(object);
    }
    public void debugSpaceUnitAddPatrolPath(obj_id self, obj_id unit, transform[] path) throws InterruptedException
    {
        debugConsoleMsg(self, "spaceUnitAddPatrolPath() unit: " + unit + " path.length: " + path.length);
        ship_ai.spacePatrol(unit, path);
    }
    public transform[] createPatrolPathSpiral(vector position_w, float radius) throws InterruptedException
    {
        final int points = 60;
        transform path[] = new transform[points];
        for (int i = 0; i < points; ++i)
        {
            final float halfHeightPercent = 2.0f;
            final float yOffset = ((float)i / (float)points) * radius * (halfHeightPercent * 2.0f) - radius * halfHeightPercent;
            final float radian = (float)Math.PI * 4.0f * ((float)i / (float)points);
            final float x = position_w.x + (float)Math.sin(radian) * radius;
            final float y = position_w.y + yOffset;
            final float z = position_w.z + (float)Math.cos(radian) * radius;
            path[i] = transform.identity.setPosition_p(x, y, z);
        }
        return path;
    }
    public int createPatrollingSquad(obj_id self, String unitName, int squadSize, vector position_w) throws InterruptedException
    {
        final float createRadius = 200.0f;
        final obj_id squadLeader = createUnit(self, unitName, createRadius, position_w);
        final int squadLeaderSquadId = ship_ai.unitGetSquadId(squadLeader);
        for (int i = 0; i < squadSize - 1; ++i)
        {
            ship_ai.unitSetSquadId(createUnit(self, unitName, createRadius, position_w), squadLeaderSquadId);
        }
        ship_ai.squadSetAttackOrders(squadLeaderSquadId, ship_ai.ATTACK_ORDERS_RETURN_FIRE);
        ship_ai.squadSetFormationRandom(squadLeaderSquadId);
        ship_ai.squadSetFormationSpacing(squadLeaderSquadId, 1.0f);
        ship_ai.squadPatrol(squadLeaderSquadId, createPatrolPathSpiral(position_w, 200.0f));
        debugConsoleMsg(self, "ai_squad: Creating a squad of " + squadSize + " " + unitName + " at " + position_w);
        return squadLeaderSquadId;
    }
}
