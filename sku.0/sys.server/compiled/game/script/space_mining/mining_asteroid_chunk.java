package script.space_mining;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.combat;
import script.library.features;
import script.library.money;
import script.library.pclib;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_utils;
import script.library.sui;
import script.library.quests;
import script.library.utils;
import script.library.vehicle;

public class mining_asteroid_chunk extends script.base_script
{
    public mining_asteroid_chunk()
    {
    }
    public static final int MAX_RESOURCE = 1000000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setHitpoints(self, 25);
        setMaxHitpoints(self, 25);
        LOG("space_mining", "OnAttach " + self);
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
    public boolean shipAbsorbAsteroid(obj_id asteroid, obj_id attacker, int amount) throws InterruptedException
    {
        LOG("space_mining", "shipAbsorbAsteroid " + asteroid + ", " + attacker + ", " + amount);
        if (!isShipSlotInstalled(attacker, ship_chassis_slot_type.SCST_cargo_hold))
        {
            LOG("space_mining", "no hold");
            space_utils.sendSystemMessageShip(attacker, new string_id("space_mining", "no_hold"), true, true, true, true);
            return false;
        }
        LOG("space_mining", "shipAbsorbAsteroid 0");
        obj_id attackingPilot = space_utils.getPilotForRealsies(attacker);
        String strAsteroidTable = "datatables/space_mining/mining_asteroids.iff";
        String strAsteroidType = getStringObjVar(asteroid, "strAsteroidType");
        int contentsMax = getShipCargoHoldContentsMaximum(attacker);
        int contentsCurrent = getShipCargoHoldContentsCurrent(attacker);
        if (contentsMax <= 0)
        {
            space_utils.sendSystemMessageShip(attacker, new string_id("space_mining", "no_hold"), true, true, true, true);
            return false;
        }
        LOG("space_mining", "shipAbsorbAsteroid 1");
        if (contentsCurrent >= contentsMax)
        {
            space_utils.sendSystemMessageShip(attacker, new string_id("space_mining", "hold_full"), true, true, true, true);
            return false;
        }
        LOG("space_mining", "shipAbsorbAsteroid 2");
        if (buff.hasBuff(attackingPilot, "tcg_series4_falleens_fist"))
        {
            amount = (int)(amount * 1.5f);
            LOG("space_mining", "Falleen's Fist Buff: Granting 50% increase in resources for Pilot: " + attackingPilot);
        }
        String resourceClassName = getResourceClassNameForAsteroid(strAsteroidType);
        int deltaAmount = modifyShipCargoHoldContent(attacker, resourceClassName, amount);
        LOG("space_mining", "shipAbsorbAsteroid deltaAmount=" + deltaAmount);
        dictionary d = new dictionary();
        d.put("resourceAmt", deltaAmount);
        d.put("player", attackingPilot);
        d.put("resourceType", strAsteroidType);
        messageTo(attackingPilot, "handleAsteroidMined", d, 0, false);
        return true;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int chassisSlot, boolean isPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        obj_id attackingPilot = space_utils.getPilotForRealsies(attacker);
        if (!features.hasEpisode3Expansion(attackingPilot))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("space_mining", "OnShipWasHit " + self + ", " + attacker + ", " + weaponIndex);
        int intWeaponSlot = weaponIndex + ship_chassis_slot_type.SCST_weapon_0;
        location attackingLocation = getLocation(attacker);
        vector attackingPosition_w = new vector(attackingLocation.x, attackingLocation.y, attackingLocation.z);
        transform selfTransform = getTransform_o2w(self);
        vector attackingLocation_o = selfTransform.rotateTranslate_p2l(attackingPosition_w);
        int weaponCrc = getShipComponentCrc(attacker, weaponIndex + ship_chassis_slot_type.SCST_weapon_first);
        if (getShipComponentDescriptorWeaponIsTractor(weaponCrc))
        {
            vector currentVelocity_w = getDynamicMiningAsteroidVelocity(self);
            vector directionToAttacker_w = new vector(attackingPosition_w);
            directionToAttacker_w = directionToAttacker_w.subtract(selfTransform.getPosition_p());
            directionToAttacker_w = directionToAttacker_w.normalize();
            directionToAttacker_w = directionToAttacker_w.multiply(30.0f);
            currentVelocity_w = currentVelocity_w.add(directionToAttacker_w);
            float velocityMagnitude = currentVelocity_w.magnitude();
            float MAX_VELOCITY_MAGNITUDE = 55.0f;
            if (velocityMagnitude > MAX_VELOCITY_MAGNITUDE)
            {
                float velocityMultiplier = MAX_VELOCITY_MAGNITUDE / velocityMagnitude;
                currentVelocity_w = currentVelocity_w.multiply(velocityMultiplier);
            }
            setDynamicMiningAsteroidVelocity(self, currentVelocity_w);
            location selfLoc = getLocation(self);
            location attackerLoc = getLocation(attacker);
            float distance = getDistance(selfLoc, attackerLoc);
            float radius = getObjectCollisionRadius(attacker);
            distance = distance - radius;
            if (distance < 100.0f)
            {
                if (shipAbsorbAsteroid(self, attacker, 40))
                {
                    destroyObject(self);
                    grantRareAsteroid(attackingPilot);
                }
            }
            return SCRIPT_CONTINUE;
        }
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
                shipAbsorbAsteroid(self, attacker, 20);
                grantRareAsteroid(attackingPilot);
                handleShipDestruction(self, 1.0f);
            }
            else 
            {
                vector hitLocation_o = new vector(hitLocationX_o, hitLocationY_o, hitLocationZ_o);
                notifyShipHit(self, attackingLocation_o, hitLocation_o, ship_hit_type.HT_chassis, 0.5f, 1.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getResourceStack(obj_id objContainer, obj_id objResource) throws InterruptedException
    {
        if (!isIdValid(objContainer))
        {
            return null;
        }
        obj_id[] objContents = getContents(objContainer);
        if (objContents == null)
        {
            return null;
        }
        for (int intI = 0; intI < objContents.length; intI++)
        {
            obj_id objType = getResourceContainerResourceType(objContents[intI]);
            if (objType == objResource)
            {
                int intCount = getResourceContainerQuantity(objContents[intI]);
                if (intCount < MAX_RESOURCE)
                {
                    return objContents[intI];
                }
            }
        }
        return null;
    }
    public obj_id grantRareAsteroid(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        String datatable = "datatables/space_mining/mining_rares.iff";
        int rows = dataTableGetNumRows(datatable);
        int row = rand(0, rows - 1);
        String template = dataTableGetString(datatable, row, 0);
        int rarity = dataTableGetInt(datatable, row, 1);
        int roll = rand(1, rarity);
        if (roll <= 1)
        {
            obj_id rare = createObject(template, playerInv, "");
            if (!isIdValid(rare))
            {
                string_id haHa = new string_id("space_mining", "invfull");
                sendSystemMessage(player, haHa);
                return null;
            }
            string_id gotRare = new string_id("space_mining", "gotrare");
            sendSystemMessage(player, gotRare);
            return rare;
        }
        else 
        {
            return null;
        }
    }
    public String getResourceClassNameForAsteroid(String strAsteroidType) throws InterruptedException
    {
        if (strAsteroidType.equals("iron"))
        {
            return "space_metal_iron";
        }
        else if (strAsteroidType.equals("carbonaceous"))
        {
            return "space_metal_carbonaceous";
        }
        else if (strAsteroidType.equals("silicaceous"))
        {
            return "space_metal_silicaceous";
        }
        else if (strAsteroidType.equals("ice"))
        {
            return "space_metal_ice";
        }
        else if (strAsteroidType.equals("obsidian"))
        {
            return "space_metal_obsidian";
        }
        else if (strAsteroidType.equals("diamond"))
        {
            return "space_gem_diamond";
        }
        else if (strAsteroidType.equals("crystal"))
        {
            return "space_gem_crystal";
        }
        else if (strAsteroidType.equals("petrochem"))
        {
            return "space_chemical_petrochem";
        }
        else if (strAsteroidType.equals("acid"))
        {
            return "space_chemical_acid";
        }
        else if (strAsteroidType.equals("cyanomethanic"))
        {
            return "space_chemical_cyanomethanic";
        }
        else if (strAsteroidType.equals("sulfuric"))
        {
            return "space_chemical_sulfuric";
        }
        else if (strAsteroidType.equals("methane"))
        {
            return "space_gas_methane";
        }
        else if (strAsteroidType.equals("organometallic"))
        {
            return "space_gas_organometallic";
        }
        return null;
    }
}
