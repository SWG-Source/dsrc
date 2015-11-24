package script.space_mining;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.sui;
import script.library.quests;
import script.library.ai_lib;
import script.library.money;
import script.library.chat;
import script.library.pclib;
import script.library.vehicle;
import script.library.ship_ai;
import script.library.space_content;
import script.library.space_combat;
import script.library.space_transition;
import script.library.space_create;
import script.library.space_utils;
import script.library.features;

public class mining_asteroid_static extends script.base_script
{
    public mining_asteroid_static()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "mining_asteroid.numShipsSpawned", 0);
        setObjVar(self, "dynamicCount", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnShipInternalDamageOverTimeRemoved(obj_id self, int chassisSlot, float damageRate, float damageThreshold) throws InterruptedException
    {
        obj_id pilot = getPilotId(self);
        if (pilot != null)
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int chassisSlot, boolean isPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        String strAsteroidTable = "datatables/space_mining/mining_asteroids.iff";
        int intDangerLevel = getIntObjVar(self, "intDangerLevel");
        int intDangerPct = getIntObjVar(self, "intDangerPct");
        obj_id attackingPilot = space_utils.getPilotForRealsies(attacker);
        if (!isIdValid(attackingPilot) || !features.hasEpisode3Expansion(attackingPilot))
        {
            return SCRIPT_CONTINUE;
        }
        int intWeaponSlot = weaponIndex + ship_chassis_slot_type.SCST_weapon_0;
        String strAsteroidType = getStringObjVar(self, "strAsteroidType");
        location attackingLocation = getLocation(attacker);
        vector attackingPosition_w = new vector(attackingLocation.x, attackingLocation.y, attackingLocation.z);
        transform selfTransform = getTransform_o2w(self);
        vector attackingLocation_o = selfTransform.rotateTranslate_p2l(attackingPosition_w);
        int weaponCrc = getShipComponentCrc(attacker, weaponIndex + ship_chassis_slot_type.SCST_weapon_first);
        if (getShipComponentDescriptorWeaponIsMining(weaponCrc))
        {
            float fltDamage = space_combat.getShipWeaponDamage(attacker, self, intWeaponSlot);
            fltDamage = fltDamage / 100;
            int maxHitpoints = getMaxHitpoints(self);
            int oldHitpoints = getHitpoints(self);
            setHitpoints(self, oldHitpoints - (int)fltDamage);
            int newHitpoints = getHitpoints(self);
            if (newHitpoints <= 0)
            {
                handleShipDestruction(self, 1.0f);
            }
            else 
            {
                vector hitLocation_o = new vector(hitLocationX_o, hitLocationY_o, hitLocationZ_o);
                notifyShipHit(self, attackingLocation_o, hitLocation_o, ship_hit_type.HT_chassis, 0.5f, 1.0f);
                int newDamageBracket = newHitpoints / 50;
                int oldDamageBracket = oldHitpoints / 50;
                int damageBracketDifference = oldDamageBracket - newDamageBracket;
                vector direction_o = attackingLocation_o.approximateNormalize();
                location selfLocation = getLocation(self);
                for (int i = 0; i < damageBracketDifference; ++i)
                {
                    vector spawnDirection_o = new vector(direction_o.x * random.rand(), direction_o.y * random.rand(), direction_o.z * random.rand());
                    spawnDirection_o = spawnDirection_o.approximateNormalize();
                    vector spawnDirection_o_clone = spawnDirection_o;
                    vector spawnLocation_o = spawnDirection_o_clone.multiply(200.0f);
                    vector spawnLocation_w = (getTransform_o2w(self)).rotateTranslate_l2p(spawnLocation_o);
                    location spawnLoc = new location(spawnLocation_w.x, spawnLocation_w.y, spawnLocation_w.z);
                    if (intDangerLevel == 0 || intDangerLevel == 1)
                    {
                        spawnDirection_o = spawnDirection_o.multiply(20.0f + (random.rand() * 20.0f));
                    }
                    else if (intDangerLevel == 2 || intDangerLevel == 3)
                    {
                        spawnDirection_o = spawnDirection_o.multiply(30.0f + (random.rand() * 20.0f));
                    }
                    else if (intDangerLevel == 4)
                    {
                        spawnDirection_o = spawnDirection_o.multiply(40.0f + (random.rand() * 20.0f));
                    }
                    else if (intDangerLevel == 5)
                    {
                        spawnDirection_o = spawnDirection_o.multiply(45.0f + (random.rand() * 20.0f));
                    }
                    else 
                    {
                        spawnDirection_o = spawnDirection_o.multiply(40.0f + (random.rand() * 20.0f));
                    }
                    int choice = rand(3, 4);
                    int dynamicCount = getIntObjVar(self, "dynamicCount");
                    if (dynamicCount >= 3)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    String template = dataTableGetString(strAsteroidTable, strAsteroidType, choice);
                    obj_id spawnDynamicAsteroid = createObject(template, spawnLoc);
                    setObjVar(self, "dynamicCount", dynamicCount + 1);
                    if (null == spawnDynamicAsteroid)
                    {
                        space_utils.sendSystemMessageShip(attacker, new string_id("unlocalized", "FAILED TO CREATE DYNAMIC ASTEROID: " + template), true, true, true, true);
                        continue;
                    }
                    setObjVar(spawnDynamicAsteroid, "strAsteroidType", strAsteroidType);
                    setObjVar(spawnDynamicAsteroid, "objParentAsteroid", self);
                    setDynamicMiningAsteroidVelocity(spawnDynamicAsteroid, spawnDirection_o);
                    int roll = rand(1, 100);
                    if (roll <= intDangerPct && getIntObjVar(self, "mining_asteroid.numShipsSpawned") < 16)
                    {
                        int squad = ship_ai.squadCreateSquadId();
                        String attackTable = "datatables/space_mining/mining_threat/threat_tier_" + intDangerLevel + ".iff";
                        int rows = dataTableGetNumRows(attackTable);
                        int spawnGroup = rand(0, rows - 1);
                        for (int j = 1; j < 9; j++)
                        {
                            if (getIntObjVar(self, "mining_asteroid.numShipsSpawned") < 16)
                            {
                                String spawn = dataTableGetString(attackTable, spawnGroup, j);
                                if (spawn == null)
                                {
                                    return SCRIPT_CONTINUE;
                                }
                                transform playerLocation = getTransform_o2w(attacker);
                                float dist = rand(700.f, 800.f);
                                vector n = ((playerLocation.getLocalFrameK_p()).normalize()).multiply(dist);
                                playerLocation = playerLocation.move_p(n);
                                playerLocation = playerLocation.yaw_l(3.14f);
                                vector vi = ((playerLocation.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
                                vector vj = ((playerLocation.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
                                vector vd = vi.add(vj);
                                playerLocation = playerLocation.move_p(vd);
                                obj_id newship = space_create.createShipHyperspace(spawn, playerLocation);
                                attachScript(newship, "space_mining.mining_pirate");
                                setObjVar(newship, "space_mining.parentRoid", self);
                                int spawnCount = getIntObjVar(self, "mining_asteroid.numShipsSpawned");
                                setObjVar(self, "mining_asteroid.numShipsSpawned", spawnCount + 1);
                                if (isIdValid(newship))
                                {
                                    ship_ai.unitSetSquadId(newship, squad);
                                    ship_ai.spaceAttack(newship, attacker);
                                }
                            }
                        }
                        ship_ai.squadSetFormationRandom(squad);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "objParent"))
        {
            space_content.notifySpawner(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePirateKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int spawnCount = getIntObjVar(self, "mining_asteroid.numShipsSpawned");
        setObjVar(self, "mining_asteroid.numShipsSpawned", spawnCount - 1);
        return SCRIPT_CONTINUE;
    }
    public int decrementCount(obj_id self, dictionary params) throws InterruptedException
    {
        int count = getIntObjVar(self, "dynamicCount");
        setObjVar(self, "dynamicCount", count - 1);
        return SCRIPT_CONTINUE;
    }
}
