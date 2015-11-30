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
import script.library.utils;
import script.library.space_utils;
import script.library.space_combat;

public class mining_salvage_hulk extends script.base_script
{
    public mining_salvage_hulk()
    {
    }
    public static final int MAX_RESOURCE = 1000000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnShipInternalDamageOverTimeRemoved(obj_id self, int chassisSlot, float damageRate, float damageThreshold) throws InterruptedException
    {
        obj_id pilot = space_utils.getPilotForRealsies(self);
        if (pilot != null)
        {
            sendSystemMessageTestingOnly(pilot, "jwatson_ship IDOT removed slot=" + chassisSlot + ", damageRate=" + damageRate + ", threshold=" + damageThreshold);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int chassisSlot, boolean isPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        obj_id attackingPilot = space_utils.getPilotForRealsies(attacker);
        String strAsteroidType = getStringObjVar(self, "strAsteroidType");
        int intWeaponSlot = weaponIndex + ship_chassis_slot_type.SCST_weapon_0;
        int weaponCrc = getShipComponentCrc(attacker, weaponIndex + ship_chassis_slot_type.SCST_weapon_first);
        location attackingLocation = getLocation(attacker);
        vector attackingPosition_w = new vector(attackingLocation.x, attackingLocation.y, attackingLocation.z);
        transform selfTransform = getTransform_o2w(self);
        if (getShipComponentDescriptorWeaponIsTractor(weaponCrc))
        {
            float fltDamage = 3.0f;
            int maxHitpoints = getMaxHitpoints(self);
            int oldHitpoints = getHitpoints(self);
            setHitpoints(self, oldHitpoints - (int)fltDamage);
            int newHitpoints = getHitpoints(self);
            int resourceAmt = oldHitpoints - newHitpoints;
            giveResourceReward(self, attacker, resourceAmt);
            dictionary d = new dictionary();
            d.put("resourceAmt", resourceAmt);
            d.put("player", attackingPilot);
            d.put("resourceType", strAsteroidType);
            messageTo(attackingPilot, "handleShipSalvage", d, 0, false);
            if (newHitpoints <= 0)
            {
                handleShipDestruction(self, 1.0f);
            }
            else 
            {
                vector hitLocation_o = new vector(hitLocationX_o, hitLocationY_o, hitLocationZ_o);
                notifyShipHit(self, attackingPosition_w, hitLocation_o, ship_hit_type.HT_chassis, 0.5f, 1.0f);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void giveResourceReward(obj_id objAsteroid, obj_id objAttacker, int intAmount) throws InterruptedException
    {
        String strAsteroidType = getStringObjVar(objAsteroid, "strAsteroidType");
        obj_id objPilot = space_utils.getPilotForRealsies(objAttacker);
        obj_id objContainer = null;
        if (space_utils.isShipWithInterior(objAttacker))
        {
            objContainer = getObjIdObjVar(objAttacker, "objLootBox");
        }
        else 
        {
            objPilot = space_utils.getPilotForRealsies(objAttacker);
            if (!isIdValid(objPilot))
            {
                return;
            }
            objContainer = utils.getInventoryContainer(objPilot);
        }
        if (!isIdValid(objContainer))
        {
            LOG("space", "null loot container");
            return;
        }
        String strResourceType = getResourceType(strAsteroidType);
        obj_id[] objResourceIds = getResourceTypes(strResourceType);
        obj_id objResourceId = null;
        if ((objResourceIds != null) && (objResourceIds.length > 0))
        {
            objResourceId = objResourceIds[rand(0, objResourceIds.length - 1)];
        }
        if (!isIdValid(objResourceId))
        {
            sendSystemMessageTestingOnly(objPilot, "No resources, bucko!");
        }
        obj_id objStack = getResourceStack(objContainer, objResourceId);
        if (isIdValid(objStack))
        {
            int intCount = getResourceContainerQuantity(objStack);
            intCount += intAmount;
            if (intCount > MAX_RESOURCE)
            {
                intCount = intCount - MAX_RESOURCE;
                sendSystemMessageTestingOnly(objPilot, "Add the Diff!  " + intCount);
                intAmount = intAmount - intCount;
                addResourceToContainer(objStack, objResourceId, intAmount, null);
                objStack = null;
            }
            else 
            {
                addResourceToContainer(objStack, objResourceId, intAmount, null);
                sendSystemMessageTestingOnly(objPilot, "Incrementing count!");
            }
        }
        else 
        {
            objStack = createResourceCrate(objResourceId, intAmount, objContainer);
            if (objStack == null)
            {
            }
        }
        return;
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
    public String getResourceType(String strAsteroidType) throws InterruptedException
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
