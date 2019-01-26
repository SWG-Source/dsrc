package script.library;

import script.*;

import java.util.Vector;

public class space_crafting extends script.base_script
{
    public space_crafting()
    {
    }
    public static final int CHASSIS = space_combat.SHIP;
    public static final int HULL = -1;
    public static final int LIFE_SUPPORT = -2;
    public static final int PLASMA_CONDUIT = -3;
    public static final int AMMO_BAY = -4;
    public static final int HYPERDRIVE = -5;
    public static final int HULL_BREACH_DAMAGE = 50;
    public static final float HULL_BREACH_DAMAGE_TIMER = 5;
    public static final String SHIP_DROID_SLOT_NAME = "ship_droid";
    public static final String STF_COMPONENT_TOOL = "reverse_engineering_tool";
    public static final String SCRIPT_COMPONENT_NAME_HANDLER = "space.crafting.component_name_handler";
    public static final int DAMAGE_CHANCE = 5;
    public static final int[] NON_COMPONENT_DAMAGEABLES_LIGHT = 
    {
        PLASMA_CONDUIT
    };
    public static final int[] NON_COMPONENT_DAMAGEABLES_MEDIUM = 
    {
        PLASMA_CONDUIT
    };
    public static final int[] NON_COMPONENT_DAMAGEABLES_HEAVY = 
    {
        PLASMA_CONDUIT
    };
    public static final int REACTOR = ship_chassis_slot_type.SCST_reactor;
    public static final int ENGINE = ship_chassis_slot_type.SCST_engine;
    public static final int SHIELD_GENERATOR = ship_chassis_slot_type.SCST_shield_0;
    public static final int ARMOR_0 = ship_chassis_slot_type.SCST_armor_0;
    public static final int ARMOR_1 = ship_chassis_slot_type.SCST_armor_1;
    public static final int CAPACITOR = ship_chassis_slot_type.SCST_capacitor;
    public static final int BOOSTER = ship_chassis_slot_type.SCST_booster;
    public static final int DROID_INTERFACE = ship_chassis_slot_type.SCST_droid_interface;
    public static final int TARGETING_STATION = ship_chassis_slot_type.SCST_targeting_station;
    public static final int WEAPON_0 = ship_chassis_slot_type.SCST_weapon_0;
    public static final int WEAPON_1 = ship_chassis_slot_type.SCST_weapon_1;
    public static final int WEAPON_2 = ship_chassis_slot_type.SCST_weapon_2;
    public static final int WEAPON_3 = ship_chassis_slot_type.SCST_weapon_3;
    public static final int WEAPON_4 = ship_chassis_slot_type.SCST_weapon_4;
    public static final int WEAPON_5 = ship_chassis_slot_type.SCST_weapon_5;
    public static final int WEAPON_6 = ship_chassis_slot_type.SCST_weapon_6;
    public static final int WEAPON_7 = ship_chassis_slot_type.SCST_weapon_7;
    public static final int CARGO = ship_chassis_slot_type.SCST_cargo_hold;
    public static final String COLLECTION_REWARD_REACTOR_MK1_V2 = "item_collection_reward_reactor_01_mk1_schematic";
    public static final String COLLECTION_REWARD_REACTOR_MK2_V2 = "item_collection_reward_reactor_01_mk2_schematic";
    public static final String COLLECTION_REWARD_REACTOR_MK3_V2 = "item_collection_reward_reactor_01_mk3_schematic";
    public static final String COLLECTION_REWARD_REACTOR_MK4_V2 = "item_collection_reward_reactor_01_mk4_schematic";
    public static final String COLLECTION_REACTOR_CHECKED = "collectionReactorChecked";
    public static final String NEW_REACTOR_ISSUED_OBJVAR = "reactorAlreadyReplaced";
    public static final String ALLOW_VENDOR_BAZAAR = "allowSaleVendorBazaar";
    public static final String TCG_SHIP_TYPE = "ship_chassis.tcg_ship";
    public static final String TCG_SHIP_DEED_STATIC_ITEM_NAME = "ship_chassis.tcg_ship_deed_name";
    public static final float MK1_ENERGY_MAX = 14274.00f;
    public static final float MK2_MASS_MIN = 3087.00f;
    public static final float MK2_ENERGY_MAX = 15680.30f;
    public static final float MK3_MASS_MIN = 6906.6360f;
    public static final float MK3_ENERGY_MAX = 17242.80f;
    public static final float MK4_MASS_MIN = 10400.520f;
    public static final float MK4_ENERGY_MAX = 20318.80f;
    public static final string_id SID_COLLECTION_REACTOR_REPLACED = new string_id("player_structure", "collection_reactor_replaced");
    public static final string_id SID_INCORRECTLY_CONFIGURED_ITEM = new string_id("space/space_interaction", "incorrectly_configured_item");
    public static final String STARSHIP_DROID_TABLE = "datatables/space_combat/ship_droid_assignments.iff";
    public static final String SHIP_COMPONENT_TABLE = "datatables/space/ship_components.iff";
    public static final String SHIP_WEAPON_TABLE = "datatables/ship/components/weapon.iff";
    public static double randBell(double avg, double var)
    {
        var = var / 2;
        double r, v1, v2;
        do
        {
            v1 = 2.0 * rand() - 1.0;
            v2 = 2.0 * rand() - 1.0;
            r = v1 * v1 + v2 * v2;
        } while (r >= 1.0 || r == 0.0);
        double fac = Math.sqrt(-2.0 * StrictMath.log(r) / r);
        double value = (avg + (v1 * fac) * (avg * var));
        if (value < 0)
        {
            return 0;
        }
        else 
        {
            return value;
        }
    }
    public static float getBellValue(float fltValue, float fltModifier)
    {
        return (float)(randBell(fltValue, fltModifier));
    }
    public static float getModifiedValue(float fltValue, float fltModifier)
    {
        fltValue = fltValue + rand(-1 * fltModifier, fltModifier);
        return fltValue;
    }
    public static float getRawModifiedValue(float fltModifier)
    {
        return rand(-1 * fltModifier, fltModifier);
    }
    public static String getShipComponentStringType(obj_id objComponent) {
        int intComponentType = getShipComponentDescriptorType(objComponent);
        return getShipComponentStringType(intComponentType);
    }
    public static String getShipComponentStringType(int intComponentType)
    {
        if (intComponentType < ship_component_type.names.length)
        {
            return ship_component_type.names[intComponentType];
        }
        LOG("space", "type is " + intComponentType);
        return null;
    }
    public static int getComponentSlotInt(String strName)
    {
        return ship_chassis_slot_type.getTypeByName(strName);
    }
    public static String getComponentSlotNameString(int intSlot)
    {
        return ship_chassis_slot_type.getNameByType(intSlot);
    }
    public static obj_id setupStandardStatistics(obj_id objComponent, dictionary dctParams) throws InterruptedException
    {
        float fltEnergyMaintenanceModifier = dctParams.getFloat("fltEnergyMaintenanceModifier");
        float fltEnergyMaintenance = dctParams.getFloat("fltEnergyMaintenance");
        fltEnergyMaintenance = getBellValue(fltEnergyMaintenance, fltEnergyMaintenanceModifier);

        float fltMassModifier = dctParams.getFloat("fltMassModifier");
        float fltMass = dctParams.getFloat("fltMass");
        fltMass = getBellValue(fltMass, fltMassModifier);

        float fltEfficiency = dctParams.getFloat("fltEfficiency");

        float fltArmor = dctParams.getFloat("fltMaximumArmorHitpoints");
        float fltArmorMod = dctParams.getFloat("fltMaximumArmorHitpointsMod");
        fltArmor = getBellValue(fltArmor, fltArmorMod);

        setComponentCurrentArmorHitpoints(objComponent, fltArmor);
        setComponentMaximumArmorHitpoints(objComponent, fltArmor);

        setComponentMaximumConduitHitpoints(objComponent, fltArmor);
        setComponentCurrentConduitHitpoints(objComponent, fltArmor);

        setComponentCurrentHitpoints(objComponent, fltArmor);
        setComponentMaximumHitpoints(objComponent, fltArmor);

        setComponentMass(objComponent, fltMass);

        setComponentEnergyMaintenance(objComponent, fltEnergyMaintenance);
        setComponentEnergyEfficiency(objComponent, fltEfficiency);
        setComponentGeneralEfficiency(objComponent, fltEfficiency);

        return objComponent;
    }
    public static int getReverseEngineeringLevel(obj_id objComponent) throws InterruptedException
    {
        if (hasObjVar(objComponent, "character.builder") || (getName(objComponent)).startsWith("Test "))
        {
            if (!hasObjVar(objComponent, "character.builder"))
            {
                setObjVar(objComponent, "character.builder", 1);
            }
            return 0;
        }
        String strComponentType = getShipComponentStringType(objComponent);
        LOG("CARGO_HOLD", "COMPONENT IS OF TYPE " + strComponentType);
        if ((strComponentType != null) && (!strComponentType.equals("")))
        {
            dictionary dctParams = dataTableGetRow("datatables/ship/components/" + strComponentType + ".iff", getTemplateName(objComponent));
            if (dctParams == null)
            {
                LOG("ERROR", "TEMPLATE OF TYPE " + getTemplateName(objComponent) + " HAS BEEN PASSED TO SETUP SPACE COMPONENT. THIS DOES NOT EXIST IN THE DATATBLE of " + "datatables/ship/components/" + strComponentType + ".iff");
                return 0;
            }
            return dctParams.getInt("reverseEngineeringLevel");
        }
        else 
        {
            LOG("space", "MEGA FUCKUP WITH " + objComponent + " template is " + objComponent);
        }
        return 0;
    }
    public static float filterFireRateForObject(float weaponFireRate, int reverseEngineeringLevel)
    {
        if (weaponFireRate == 0.44625f || weaponFireRate == 0.425f || weaponFireRate == 0.408f || weaponFireRate == 0.357f || weaponFireRate == 0.34f)
        {
            switch (reverseEngineeringLevel)
            {
                case 0:
                return 0.4f;
                case 1:
                return 0.3f;
                case 2:
                return 0.38f;
                case 3:
                return 0.3f;
                case 4:
                return 0.36f;
                case 5:
                return 0.3f;
                case 6:
                return 0.36f;
                case 7:
                return 0.3f;
                case 8:
                return 0.36f;
                case 9:
                return 0.3f;
                case 10:
                return 0.35f;
            }
        }
        return weaponFireRate;
    }
    public static float filterFireRateForShip(float weaponFireRate, int reverseEngineeringLevel) {
        if (weaponFireRate == 0.44625f || weaponFireRate == 0.45f || weaponFireRate == 0.425f || weaponFireRate == 0.43f || weaponFireRate == 0.408f || weaponFireRate == 0.41f || weaponFireRate == 0.357f || weaponFireRate == 0.36f || weaponFireRate == 0.34f)
        {
            switch (reverseEngineeringLevel)
            {
                case 0:
                return 0.4f;
                case 1:
                return 0.3f;
                case 2:
                return 0.38f;
                case 3:
                return 0.3f;
                case 4:
                return 0.36f;
                case 5:
                return 0.3f;
                case 6:
                return 0.36f;
                case 7:
                return 0.3f;
                case 8:
                return 0.36f;
                case 9:
                return 0.3f;
                case 10:
                return 0.35f;
            }
        }
        return weaponFireRate;
    }
    public static void recalculateFireRateFromObject(obj_id player, obj_id weapon) throws InterruptedException
    {
        if (getGameObjectType(weapon) != GOT_ship_component_weapon)
        {
            return;
        }
        String template = getTemplateName(weapon);
        String descName = getName(weapon);
        if (template.equals("") || template.length() <= 0)
        {
            return;
        }
        float weaponFireRate = getWeaponRefireRate(weapon);
        int reverseEngineeringLevel = dataTableGetInt(SHIP_WEAPON_TABLE, template, "reverseEngineeringLevel");
        float newWeaponFireRate = filterFireRateForObject(weaponFireRate, reverseEngineeringLevel);

        CustomerServiceLog("space_loot", "Possible fire rate fix on weapon. |player: " + player + "|ship: null|weapon name: " + descName + "|weaponFireRate: " + weaponFireRate + "|newWeaponFireRate: " + newWeaponFireRate + "|reverseEngineeringLevel: " + reverseEngineeringLevel);
        if (newWeaponFireRate != weaponFireRate)
        {
            CustomerServiceLog("space_loot", "Weapon fire rate fix on weapon. |player: " + player + "|ship: null|weapon name: " + descName + "|weaponFireRate: " + weaponFireRate + "|newWeaponFireRate: " + newWeaponFireRate + "|reverseEngineeringLevel: " + reverseEngineeringLevel);
            setWeaponRefireRate(weapon, newWeaponFireRate);
        }
    }

    public static obj_id initializeSpaceShipComponent(obj_id objComponent) throws InterruptedException
    {
        String strComponentType = getShipComponentStringType(objComponent);
        if (strComponentType == null)
        {
            LOG("space", "MAJOR FUCKUP! " + objComponent + " is 9 kinds of fucked up");
            setName(objComponent, "BAD COMPONENT: " + getTemplateName(objComponent) + " : PLEASE REPORT BUG");
            return null;
        }
        if (!strComponentType.equals(""))
        {
            dictionary dctParams = dataTableGetRow("datatables/ship/components/" + strComponentType + ".iff", getTemplateName(objComponent));
            if (dctParams == null)
            {
                LOG("ERROR", "TEMPLATE OF TYPE " + getTemplateName(objComponent) + " HAS BEEN PASSED TO SETUP SPACE COMPONENT. THIS DOES NOT EXIST IN THE DATATBLE of " + "datatables/ship/components/" + strComponentType + ".iff");
                return null;
            }
            objComponent = setupStandardStatistics(objComponent, dctParams);
            if (!strComponentType.equals("armor")) {
                switch (strComponentType) {
                    case "booster": {
                        float fltCurrentEnergy = dctParams.getFloat("fltCurrentEnergy");
                        float fltMaximumEnergyModifier = dctParams.getFloat("fltMaximumEnergyModifier");
                        float fltMaximumEnergy = dctParams.getFloat("fltMaximumEnergy");
                        float fltRoll = rand(-1 * fltMaximumEnergyModifier, fltMaximumEnergyModifier);
                        fltMaximumEnergy = fltMaximumEnergy + fltRoll;
                        fltCurrentEnergy = fltCurrentEnergy + fltRoll;
                        setBoosterMaximumEnergy(objComponent, fltMaximumEnergy);
                        setBoosterCurrentEnergy(objComponent, fltCurrentEnergy);
                        float fltRechargeRateModifier = dctParams.getFloat("fltRechargeRateModifier");
                        float fltRechargeRate = dctParams.getFloat("fltRechargeRate");
                        fltRechargeRate = getBellValue(fltRechargeRate, fltRechargeRateModifier);
                        setBoosterEnergyRechargeRate(objComponent, fltRechargeRate);
                        float fltConsumptionRateModifier = dctParams.getFloat("fltConsumptionRateModifier");
                        float fltConsumptionRate = dctParams.getFloat("fltConsumptionRate");
                        fltConsumptionRate = getBellValue(fltConsumptionRate, fltConsumptionRateModifier);
                        setBoosterEnergyConsumptionRate(objComponent, fltConsumptionRate);
                        float fltAccelerationModifier = dctParams.getFloat("fltAccelerationModifier");
                        float fltAcceleration = dctParams.getFloat("fltAcceleration");
                        fltAcceleration = getBellValue(fltAcceleration, fltAccelerationModifier);
                        setBoosterAcceleration(objComponent, fltAcceleration);
                        float fltMaxSpeedModifier = dctParams.getFloat("fltMaxSpeedModifier");
                        float fltMaxSpeed = dctParams.getFloat("fltMaxSpeed");
                        fltMaxSpeed = getBellValue(fltMaxSpeed, fltMaxSpeedModifier);
                        setBoosterMaximumSpeed(objComponent, fltMaxSpeed);
                        break;
                    }
                    case "cargo_hold":
                        int intCargoHoldCapacity = dctParams.getInt("intCargoHoldCapacity");
                        setCargoHoldMaxCapacity(objComponent, intCargoHoldCapacity);
                        break;
                    case "droid_interface":
                        float fltCommandSpeedModifier = dctParams.getFloat("fltCommandSpeedModifier");
                        float fltCommandSpeed = dctParams.getFloat("fltCommandSpeed");
                        fltCommandSpeed = getBellValue(fltCommandSpeed, fltCommandSpeedModifier);
                        setDroidInterfaceCommandSpeed(objComponent, fltCommandSpeed);
                        break;
                    case "engine": {
                        float fltMaxSpeedModifier = dctParams.getFloat("fltMaxSpeedModifier");
                        float fltMaxSpeed = dctParams.getFloat("fltMaxSpeed");
                        fltMaxSpeed = getBellValue(fltMaxSpeed, fltMaxSpeedModifier);
                        setEngineMaximumSpeed(objComponent, fltMaxSpeed);
                        float fltMaxPitchModifier = dctParams.getFloat("fltMaxPitchModifier");
                        float fltMaxPitch = dctParams.getFloat("fltMaxPitch");
                        fltMaxPitch = getBellValue(fltMaxPitch, fltMaxPitchModifier);
                        setEngineMaximumPitch(objComponent, fltMaxPitch);
                        float fltMaxRollModifier = dctParams.getFloat("fltMaxRollModifier");
                        float fltMaxRoll = dctParams.getFloat("fltMaxRoll");
                        fltMaxRoll = getBellValue(fltMaxRoll, fltMaxRollModifier);
                        setEngineMaximumRoll(objComponent, fltMaxRoll);
                        float fltMaxYawModifier = dctParams.getFloat("fltMaxYawModifier");
                        float fltMaxYaw = dctParams.getFloat("fltMaxYaw");
                        fltMaxYaw = getBellValue(fltMaxYaw, fltMaxYawModifier);
                        setEngineMaximumYaw(objComponent, fltMaxYaw);
                        break;
                    }
                    case "reactor":
                        float fltEnergyGenerationModifier = dctParams.getFloat("fltEnergyGenerationModifier");
                        float fltEnergyGeneration = dctParams.getFloat("fltEnergyGeneration");
                        fltEnergyGeneration = getBellValue(fltEnergyGeneration, fltEnergyGenerationModifier);
                        setReactorEnergyGeneration(objComponent, fltEnergyGeneration);
                        break;
                    case "shield":
                        float fltShieldHitpointsMaximumFrontModifier = dctParams.getFloat("fltShieldHitpointsMaximumFrontModifier");
                        float fltShieldHitpointsMaximumFront = dctParams.getFloat("fltShieldHitpointsMaximumFront");
                        fltShieldHitpointsMaximumFront = getBellValue(fltShieldHitpointsMaximumFront, fltShieldHitpointsMaximumFrontModifier);
                        float fltShieldHitpointsMaximumBackModifier = dctParams.getFloat("fltShieldHitpointsMaximumBackModifier");
                        float fltShieldHitpointsMaximumBack = dctParams.getFloat("fltShieldHitpointsMaximumFront");
                        fltShieldHitpointsMaximumBack = getBellValue(fltShieldHitpointsMaximumBack, fltShieldHitpointsMaximumBackModifier);
                        setShieldGeneratorCurrentFrontHitpoints(objComponent, 0.0f);
                        setShieldGeneratorCurrentBackHitpoints(objComponent, 0.0f);
                        setShieldGeneratorMaximumFrontHitpoints(objComponent, fltShieldHitpointsMaximumFront);
                        setShieldGeneratorMaximumBackHitpoints(objComponent, fltShieldHitpointsMaximumBack);
                        float fltShieldRechargeRateModifier = dctParams.getFloat("fltShieldRechargeRateModifier");
                        float fltShieldRechargeRate = dctParams.getFloat("fltShieldRechargeRate");
                        fltShieldRechargeRate = getBellValue(fltShieldRechargeRate, fltShieldRechargeRateModifier);
                        setShieldGeneratorRechargeRate(objComponent, fltShieldRechargeRate);
                        break;
                    case "weapon":
                        float fltMinDamageModifier = dctParams.getFloat("fltMinDamageModifier");
                        float fltMinDamage = dctParams.getFloat("fltMinDamage");
                        fltMinDamage = getBellValue(fltMinDamage, fltMinDamageModifier);
                        setWeaponMinimumDamage(objComponent, fltMinDamage);
                        float fltMaxDamageModifier = dctParams.getFloat("fltMaxDamageModifier");
                        float fltMaxDamage = dctParams.getFloat("fltMaxDamage");
                        fltMaxDamage = getBellValue(fltMaxDamage, fltMaxDamageModifier);
                        setWeaponMaximumDamage(objComponent, fltMaxDamage);
                        float fltShieldEffectivenessModifier = dctParams.getFloat("fltShieldEffectivenessModifier");
                        float fltShieldEffectiveness = dctParams.getFloat("fltShieldEffectiveness");
                        fltShieldEffectiveness = getBellValue(fltShieldEffectiveness, fltShieldEffectivenessModifier);
                        setWeaponShieldEffectiveness(objComponent, fltShieldEffectiveness);
                        float fltArmorEffectivenessModifier = dctParams.getFloat("fltArmorEffectivenessModifier");
                        float fltArmorEffectiveness = dctParams.getFloat("fltArmorEffectiveness");
                        fltArmorEffectiveness = getBellValue(fltArmorEffectiveness, fltArmorEffectivenessModifier);
                        setWeaponArmorEffectiveness(objComponent, fltArmorEffectiveness);
                        float fltEnergyPerShotModifier = dctParams.getFloat("fltEnergyPerShotModifier");
                        float fltEnergyPerShot = dctParams.getFloat("fltEnergyPerShot");
                        fltEnergyPerShot = getBellValue(fltEnergyPerShot, fltEnergyPerShotModifier);
                        setWeaponEnergyPerShot(objComponent, fltEnergyPerShot);
                        float fltRefireRateModifier = dctParams.getFloat("fltRefireRateModifier");
                        float fltRefireRate = dctParams.getFloat("fltRefireRate");
                        fltRefireRate = getBellValue(fltRefireRate, fltRefireRateModifier);
                        setWeaponRefireRate(objComponent, fltRefireRate);
                        break;
                    case "capacitor": {
                        float fltCurrentEnergy = dctParams.getFloat("fltCurrentEnergy");
                        float fltMaxEnergyModifier = dctParams.getFloat("fltMaxEnergyModifier");
                        float fltMaxEnergy = dctParams.getFloat("fltMaxEnergy");
                        fltMaxEnergy = getBellValue(fltMaxEnergy, fltMaxEnergyModifier);
                        setWeaponCapacitorMaximumEnergy(objComponent, fltMaxEnergy);
                        setWeaponCapacitorCurrentEnergy(objComponent, fltMaxEnergy);
                        float fltRechargeRateModifier = dctParams.getFloat("fltRechargeRateModifier");
                        float fltRechargeRate = dctParams.getFloat("fltRechargeRate");
                        fltRechargeRate = getBellValue(fltRechargeRate, fltRechargeRateModifier);
                        setWeaponCapacitorRechargeRate(objComponent, fltRechargeRate);
                        break;
                    }
                }
            }
            return objComponent;
        }
        else 
        {
            return objComponent;
        }
    }
    public static void uninstallAll(obj_id ship)
    {
        if (isIdValid(ship))
        {
            for (int i = 0; i < ship_chassis_slot_type.SCST_num_types; i++)
            {
                if (isShipSlotInstalled(ship, i))
                {
                    obj_id kill = shipUninstallComponent(null, ship, i, null);
                }
            }
        }
    }
    public static boolean setComponentCurrentHitpoints(obj_id objComponent, float fltValue)
    {
        String OBJVAR_NAME = "ship_comp.hitpoints_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentMaximumHitpoints(obj_id objComponent, float fltValue)
    {
        String OBJVAR_NAME = "ship_comp.hitpoints_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentCurrentArmorHitpoints(obj_id objComponent, float fltValue)
    {
        String OBJVAR_NAME = "ship_comp.armor_hitpoints_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentMaximumArmorHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.armor_hitpoints_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentGeneralEfficiency(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.efficiency_general";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentEnergyEfficiency(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.efficiency_energy";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentEnergyMaintenance(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.energy_maintenance_requirement";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentMass(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.mass";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentCurrentConduitHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.conduit_hitpoints_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setComponentMaximumConduitHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.conduit_hitpoints_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setBoosterCurrentEnergy(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.booster.energy_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setBoosterMaximumEnergy(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.booster.energy_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setBoosterEnergyRechargeRate(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.booster.energy_recharge_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setBoosterEnergyConsumptionRate(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.booster.energy_consumption_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setBoosterAcceleration(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.booster.acceleration";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setBoosterMaximumSpeed(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.booster.speed_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setCargoHoldMaxCapacity(obj_id objComponent, int intValue) {
        String OBJVAR_NAME = "ship_comp.cargo_hold.contents_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, intValue);
    }
    public static boolean setCargoHoldCurrentContents(obj_id objComponent, int intValue) {
        String OBJVAR_NAME = "ship_comp.cargo_hold.contents_current";
        return setObjVar(objComponent, OBJVAR_NAME, intValue);
    }
    public static boolean setWeaponCapacitorCurrentEnergy(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.capacitor.energy_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponCapacitorMaximumEnergy(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.capacitor.energy_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponCapacitorRechargeRate(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.capacitor.energy_recharge_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setDroidInterfaceCommandSpeed(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.droid_interface.command_speed";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineAcceleration(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.acceleration_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineDeceleration(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.deceleration_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEnginePitch(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.pitch_acceleration_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineYaw(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.yaw_acceleration_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineRoll(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.roll_acceleration_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineSpeedRotationFactorMaximum(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.speed_rotation_factor_max";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineSpeedRotationFactorMinimum(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.speed_rotation_factor_min";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineSpeedRotationFactorOptimal(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.speed_rotation_factor_optimal";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineMaximumPitch(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.pitch_rate_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineMaximumYaw(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.yaw_rate_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineMaximumRoll(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.roll_rate_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setEngineMaximumSpeed(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.engine.speed_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setReactorEnergyGeneration(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.reactor.energy_generation_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setShieldGeneratorCurrentFrontHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_front_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setShieldGeneratorMaximumFrontHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_front_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setShieldGeneratorCurrentBackHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_back_current";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setShieldGeneratorMaximumBackHitpoints(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_back_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setShieldGeneratorRechargeRate(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.shield.recharge_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponMaximumDamage(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.damage_maximum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponMinimumDamage(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.damage_minimum";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponShieldEffectiveness(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.effectiveness_shields";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponArmorEffectiveness(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.effectiveness_armor";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponEnergyPerShot(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.energy_per_shot";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponRefireRate(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.refire_rate";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static boolean setWeaponProjectileSpeed(obj_id objComponent, float fltValue) {
        String OBJVAR_NAME = "ship_comp.weapon.projectile_speed";
        return setObjVar(objComponent, OBJVAR_NAME, fltValue);
    }
    public static float getComponentCurrentHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.hitpoints_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentMaximumHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.hitpoints_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentCurrentArmorHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.armor_hitpoints_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentMaximumArmorHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.armor_hitpoints_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentEnergyEfficiency(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.efficiency_energy";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentGeneralEfficiency(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.efficiency_general";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentEnergyMaintenance(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.energy_maintenance_requirement";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentMass(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.mass";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentCurrentConduitHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.conduit_hitpoints_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getComponentMaximumConduitHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.conduit_hitpoints_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getBoosterCurrentEnergy(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.booster.energy_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getBoosterMaximumEnergy(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.booster.energy_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getBoosterEnergyRechargeRate(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.booster.energy_recharge_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getboosterEnergyConsumptionRate(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.booster.energy_consumption_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getBoosterAcceleration(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.booster.acceleration";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getBoosterMaximumSpeed(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.booster.speed_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponCapacitorCurrentEnergy(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.capacitor.energy_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponCapacitorMaximumEnergy(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.capacitor.energy_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponCapacitorRechargeRate(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.capacitor.energy_recharge_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getDroidInterfaceCommandSpeed(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.droid_interface.command_speed";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineAcceleration(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.acceleration_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineDeceleration(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.deceleration_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEnginePitch(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.pitch_acceleration_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineYaw(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.yaw_acceleration_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineRoll(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.roll_acceleration_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineSpeedRotationFactorMaximum(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.speed_rotation_factor_max";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineSpeedRotationFactorMinimum(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.speed_rotation_factor_min";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineSpeedRotationFactorOptimal(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.speed_rotation_factor_optimal";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineMaximumPitch(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.pitch_rate_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineMaximumYaw(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.yaw_rate_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineMaximumRoll(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.roll_rate_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getEngineMaximumSpeed(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.engine.speed_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getReactorEnergyGeneration(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.reactor.energy_generation_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getShieldGeneratorCurrenFrontHitpointst(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_front_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getShieldGeneratorMaximumFrontHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_front_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getShieldGeneratorCurrentBackHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_back_current";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getShieldGeneratorMaximumBackHitpoints(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.shield.hitpoints_back_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getShieldGeneratorRechargeRate(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.shield.recharge_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponMaximumDamage(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.damage_maximum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponMinimumDamage(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.damage_minimum";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponShieldEffectiveness(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.effectiveness_shields";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponArmorEffectiveness(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.effectiveness_armor";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponEnergyPerShot(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.energy_per_shot";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponRefireRate(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.refire_rate";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static float getWeaponProjectileSpeed(obj_id objComponent) {
        String OBJVAR_NAME = "ship_comp.weapon.projectile_speed";
        return getFloatObjVar(objComponent, OBJVAR_NAME);
    }
    public static Vector getRepairKitsForItem(obj_id objPlayer, int intSlot) throws InterruptedException
    {
        String strComponent;
        if (intSlot == CHASSIS)
        {
            strComponent = "chassis";
        }
        else if (intSlot == PLASMA_CONDUIT)
        {
            strComponent = "plasma_conduit";
        }
        else 
        {
            int intType = ship_chassis_slot_type.getComponentTypeForSlot(intSlot);
            strComponent = getShipComponentStringType(intType);
            LOG("space", "strComponent is " + strComponent);
        }
        return getRepairKitsForItem(objPlayer, strComponent);
    }
    public static Vector getRepairKitsForItem(obj_id objPlayer, String strComponent) throws InterruptedException
    {
        obj_id[] objContents = getInventoryAndEquipment(objPlayer);
        Vector objKits = new Vector();
        objKits.setSize(0);
        for (obj_id objContent : objContents) {
            if (hasObjVar(objContent, "strKitType")) {
                String strKitType = getStringObjVar(objContent, "strKitType");
                if ((strKitType.equals(strComponent)) || (strKitType.equals("all_purpose"))) {
                    objKits = utils.addElement(objKits, objContent);
                }
            }
        }
        if (objKits.size() > 0)
        {
            return objKits;
        }
        return null;
    }
    public static boolean repairComponentOnShip(int intSlot, Vector objRepairKits, obj_id objPlayer, obj_id objShip) throws InterruptedException
    {
        if ((objRepairKits == null) || (objRepairKits.size() < 1))
        {
            return false;
        }
        boolean boolHealed = false;

        obj_id objRepairKit = ((obj_id)objRepairKits.get(0));
        if (intSlot == CHASSIS)
        {
            float fltMaximumHitpoints = getShipMaximumChassisHitPoints(objShip);
            float fltCurrentHitpoints = getShipCurrentChassisHitPoints(objShip);
            if (fltCurrentHitpoints == fltMaximumHitpoints)
            {
                string_id strSpam = new string_id("space/space_interaction", "no_damage_to_repair");
                sendSystemMessage(objPlayer, strSpam);
                return false;
            }
            else 
            {
                float fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
                float fltRemainingRepairPoints = getCount(objRepairKit);
                if (fltDifference > fltRemainingRepairPoints)
                {
                    fltCurrentHitpoints = fltCurrentHitpoints + fltRemainingRepairPoints;
                    setShipCurrentChassisHitPoints(objShip, fltCurrentHitpoints);
                    string_id strSpam = new string_id("space/space_interaction", "repaired_x_chassis_damage_destroy_kit");
                    prose_package ppRepairInfo = prose.getPackage(strSpam, (int)fltRemainingRepairPoints);
                    sendSystemMessageProse(objPlayer, ppRepairInfo);
                    destroyObject(objRepairKit);
                    objRepairKits = utils.removeElementAt(objRepairKits, 0);
                    if (objRepairKits.size() > 0)
                    {
                        repairComponentOnShip(intSlot, objRepairKits, objPlayer, objShip);
                        return true;
                    }
                    else 
                    {
                        strSpam = new string_id("space/space_interaction", "damage_left_chassis");
                        int intDifference = (int)fltDifference - (int)fltRemainingRepairPoints;
                        ppRepairInfo = prose.getPackage(strSpam, intDifference);
                        sendSystemMessageProse(objPlayer, ppRepairInfo);
                        return true;
                    }
                }
                else 
                {
                    fltCurrentHitpoints = fltCurrentHitpoints + fltDifference;
                    setShipCurrentChassisHitPoints(objShip, fltCurrentHitpoints);
                    string_id strSpam = new string_id("space/space_interaction", "repaired_x_chassis_damage");
                    prose_package ppRepairInfo = prose.getPackage(strSpam, (int)fltDifference);
                    sendSystemMessageProse(objPlayer, ppRepairInfo);
                    fltRemainingRepairPoints = fltRemainingRepairPoints - fltDifference;
                    setCount(objRepairKit, (int)fltRemainingRepairPoints);
                    return true;
                }
            }
        }
        float fltMaximumHitpoints = getShipComponentHitpointsMaximum(objShip, intSlot);
        float fltCurrentHitpoints = getShipComponentHitpointsCurrent(objShip, intSlot);
        float fltMaximumArmorHitpoints = getShipComponentArmorHitpointsMaximum(objShip, intSlot);
        float fltCurrentArmorHitpoints = getShipComponentArmorHitpointsCurrent(objShip, intSlot);

        if ((fltCurrentHitpoints == fltMaximumHitpoints) && (fltMaximumArmorHitpoints == fltCurrentArmorHitpoints))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_damage_to_repair");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        float fltRemainingRepairPoints = getCount(objRepairKit);
        float fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
        if (fltDifference > fltRemainingRepairPoints)
        {
            fltCurrentHitpoints = fltCurrentHitpoints + fltRemainingRepairPoints;
            setShipComponentHitpointsCurrent(objShip, intSlot, fltCurrentHitpoints);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_component_damage_destroy_kit");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getShipComponentName(objShip, intSlot), (int)fltRemainingRepairPoints);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            destroyObject(objRepairKit);
            space_utils.setComponentDisabled(objShip, intSlot, false);
            space_combat.recalculateEfficiency(intSlot, objShip);
            objRepairKits = utils.removeElementAt(objRepairKits, 0);
            if (objRepairKits.size() > 0)
            {
                repairComponentOnShip(intSlot, objRepairKits, objPlayer, objShip);
                return true;
            }
            else 
            {
                strSpam = new string_id("space/space_interaction", "damage_left_component");
                int intDifference = (int)fltDifference - (int)fltRemainingRepairPoints;
                ppRepairInfo = prose.getPackage(strSpam, intDifference);
                sendSystemMessageProse(objPlayer, ppRepairInfo);
                return true;
            }
        }
        else if (fltDifference > 0)
        {
            setShipComponentHitpointsCurrent(objShip, intSlot, fltMaximumHitpoints);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_component_damage");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getShipComponentName(objShip, intSlot), (int)fltDifference);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            fltRemainingRepairPoints = fltRemainingRepairPoints - fltDifference;
            setCount(objRepairKit, (int)fltRemainingRepairPoints);
            space_utils.setComponentDisabled(objShip, intSlot, false);
            space_combat.recalculateEfficiency(intSlot, objShip);
            boolHealed = true;
        }
        fltDifference = fltMaximumArmorHitpoints - fltCurrentArmorHitpoints;
        if (fltDifference > fltRemainingRepairPoints)
        {
            fltCurrentHitpoints = fltCurrentHitpoints + fltRemainingRepairPoints;
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, fltCurrentHitpoints);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_armor_component_damage_destroy_kit");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getShipComponentName(objShip, intSlot), (int)fltRemainingRepairPoints);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            destroyObject(objRepairKit);
            space_utils.setComponentDisabled(objShip, intSlot, false);
            space_combat.recalculateEfficiency(intSlot, objShip);
            objRepairKits = utils.removeElementAt(objRepairKits, 0);
            if (objRepairKits.size() > 0)
            {
                repairComponentOnShip(intSlot, objRepairKits, objPlayer, objShip);
                return true;
            }
            else 
            {
                strSpam = new string_id("space/space_interaction", "damage_left_component_armor");
                int intDifference = (int)fltDifference - (int)fltRemainingRepairPoints;
                ppRepairInfo = prose.getPackage(strSpam, intDifference);
                sendSystemMessageProse(objPlayer, ppRepairInfo);
                return true;
            }
        }
        else if (fltDifference > 0)
        {
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, fltMaximumArmorHitpoints);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_armor_component_damage");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getShipComponentName(objShip, intSlot), (int)fltDifference);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            fltRemainingRepairPoints = fltRemainingRepairPoints - fltDifference;
            setCount(objRepairKit, (int)fltRemainingRepairPoints);
            space_utils.setComponentDisabled(objShip, intSlot, false);
            space_combat.recalculateEfficiency(intSlot, objShip);
            boolHealed = true;
        }
        return boolHealed;
    }
    public static boolean repairComponentInInventory(obj_id objComponent, Vector objRepairKits, obj_id objPlayer) throws InterruptedException
    {
        if ((objRepairKits == null) || (objRepairKits.size() < 1))
        {
            return false;
        }
        obj_id objRepairKit = ((obj_id)objRepairKits.get(0));
        boolean boolHealed = false;

        float fltMaximumHitpoints = getComponentMaximumHitpoints(objComponent);
        float fltCurrentHitpoints = getComponentCurrentHitpoints(objComponent);
        float fltMaximumArmorHitpoints = getComponentMaximumArmorHitpoints(objComponent);
        float fltCurrentArmorHitpoints = getComponentCurrentArmorHitpoints(objComponent);

        if ((fltCurrentHitpoints == fltMaximumHitpoints) && (fltMaximumArmorHitpoints == fltCurrentArmorHitpoints))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_damage_to_repair");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        float fltRemainingRepairPoints = getCount(objRepairKit);
        float fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
        if (fltDifference > fltRemainingRepairPoints)
        {
            fltCurrentHitpoints = fltCurrentHitpoints + fltRemainingRepairPoints;
            setComponentCurrentHitpoints(objComponent, fltCurrentHitpoints);
            clearComponentDisabledFlag(objComponent);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_component_damage_destroy_kit");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getEncodedName(objComponent), (int)fltRemainingRepairPoints);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            objRepairKits = utils.removeElementAt(objRepairKits, 0);
            destroyObject(objRepairKit);
            repairComponentInInventory(objComponent, objRepairKits, objPlayer);
            return true;
        }
        else if (fltDifference > 0)
        {
            setComponentCurrentHitpoints(objComponent, fltMaximumHitpoints);
            clearComponentDisabledFlag(objComponent);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_component_damage");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getEncodedName(objComponent), (int)fltDifference);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            fltRemainingRepairPoints = fltRemainingRepairPoints - fltDifference;
            setCount(objRepairKit, (int)fltRemainingRepairPoints);
            boolHealed = true;
        }
        fltDifference = fltMaximumArmorHitpoints - fltCurrentArmorHitpoints;
        if (fltDifference > fltRemainingRepairPoints)
        {
            fltCurrentArmorHitpoints = fltCurrentArmorHitpoints + fltRemainingRepairPoints;
            setComponentCurrentArmorHitpoints(objComponent, fltCurrentArmorHitpoints);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_armor_component_damage_destroy_kit");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getEncodedName(objComponent), (int)fltRemainingRepairPoints);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            objRepairKits = utils.removeElementAt(objRepairKits, 0);
            destroyObject(objRepairKit);
            repairComponentInInventory(objComponent, objRepairKits, objPlayer);
            return true;
        }
        else if (fltDifference > 0)
        {
            setComponentCurrentArmorHitpoints(objComponent, fltMaximumArmorHitpoints);
            string_id strSpam = new string_id("space/space_interaction", "repaired_x_armor_component_damage");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getEncodedName(objComponent), (int)fltDifference);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            fltRemainingRepairPoints = fltRemainingRepairPoints - fltDifference;
            setCount(objRepairKit, (int)fltRemainingRepairPoints);
            boolHealed = true;
        }
        return boolHealed;
    }
    public static boolean isRepairKit(obj_id objItem) {
        String strTest = getTemplateName(objItem);

        int intIndex = strTest.indexOf("object/tangible/ship/crafted/repair");

        return intIndex >= 0;
    }
    public static obj_id getDroidContainer(obj_id objShip) {
        return getObjectInSlot(objShip, SHIP_DROID_SLOT_NAME);
    }
    public static obj_id[] getDatapadDroidCommands(obj_id objControlDevice) throws InterruptedException
    {
        obj_id objDatapad = utils.getDatapad(objControlDevice);
        if (!isIdValid(objDatapad))
        {
            debugServerConsoleMsg(null, "SPACE_CRAFTING.getDatapadDroidCommands  ***   DATAPAD OBJ-ID WAS NULL!!! ARGH! ");
            return null;
        }
        obj_id[] objContents = utils.getContents(objDatapad);
        Vector objCommands = new Vector();
        objCommands.setSize(0);
        for (obj_id objContent : objContents) {
            if (hasObjVar(objContent, "strDroidCommand")) {
                objCommands = utils.addElement(objCommands, objContent);
            }
        }
        if (objCommands.size() > 0)
        {
            return new obj_id[objCommands.size()];
        }
        else 
        {
            return null;
        }
    }
    public static int getDroidCommandLevelFromDatapad(obj_id objControlDevice) throws InterruptedException
    {
        obj_id objDatapad = utils.getDatapad(objControlDevice);
        if (!isIdValid(objDatapad))
        {
            return 1;
        }
        String strName = getTemplateName(objDatapad);
        String strRaw = "object/tangible/datapad/droid_datapad_";
        for (int intI = 1; intI <= 6; intI++)
        {
            String strTest = strRaw + intI + ".iff";
            if (strName.equals(strTest))
            {
                return intI;
            }
        }
        return -1;
    }
    public static boolean isUsableAstromechPet(obj_id objControlDevice) throws InterruptedException
    {
        int intDroidLevel = getDroidCommandLevelFromDatapad(objControlDevice);
        if (intDroidLevel == -1)
        {
            return false;
        }
        String[] strDroidTypes = 
        {
            "r2",
            "r2_crafted",
            "r2d2",
            "r3",
            "r3_crafted",
            "r4",
            "r4_crafted",
            "r5",
            "r5_crafted",
            "navicomputer_1",
            "navicomputer_2",
            "navicomputer_3",
            "navicomputer_4",
            "navicomputer_5",
            "navicomputer_6"
        };
        String strName = getStringObjVar(objControlDevice, "pet.creatureName");
        if (strName != null)
        {
            int intIndex = utils.getElementPositionInArray(strDroidTypes, strName);
            LOG("space", "intIndex: " + intIndex + " strName: " + strName);

            return intIndex > -1;
        }
        else 
        {
            if (hasObjVar(objControlDevice, "item.objectName"))
            {
                strName = getStringObjVar(objControlDevice, "item.objectName");
                int intIndex = utils.getElementPositionInArray(strDroidTypes, strName);
                LOG("space", "intIndex: " + intIndex + " strName: " + strName);
                return intIndex > -1;
            }
            else 
            {
                LOG("space", "NULL!");
            }
        }
        return false;
    }
    public static boolean isFlightComputerController(obj_id controlDevice) throws InterruptedException
    {
        String[] computerTypes = 
        {
            "navicomputer_1",
            "navicomputer_2",
            "navicomputer_3",
            "navicomputer_4",
            "navicomputer_5",
            "navicomputer_6"
        };
        if (hasObjVar(controlDevice, "item.objectName"))
        {
            String name = getStringObjVar(controlDevice, "item.objectName");
            int intIndex = utils.getElementPositionInArray(computerTypes, name);
            return intIndex > -1;
        }
        return false;
    }
    public static boolean isCertifiedForAstromech(obj_id objControlDevice, obj_id objPlayer) throws InterruptedException
    {
        int intDroidLevel = getDroidCommandLevelFromDatapad(objControlDevice);
        LOG("space", "level is " + intDroidLevel + " Looking for command cert_droid_astromech_" + intDroidLevel);
        if (!hasCommand(objPlayer, "cert_droid_astromech_" + intDroidLevel))
        {
            LOG("space", objPlayer + " doesnt have the cert!");
            return false;
        }
        return true;
    }
    public static obj_id getAstromechDroidForShip(obj_id objPlayer) throws InterruptedException
    {
        obj_id objDataPad = utils.getDatapad(objPlayer);
        obj_id[] objContents = utils.getContents(objDataPad);
        if (objContents == null)
        {
            return null;
        }
        for (obj_id objContent : objContents) {
            if (hasObjVar(objContent, "objActiveShipDroid")) {
                obj_id objTest = getObjIdObjVar(objContent, "objActiveShipDroid");
                if (objTest != objPlayer) {
                    removeObjVar(objTest, "objActiveShipDroid");
                } else {
                    return objContent;
                }
            }
        }
        return null;
    }
    public static boolean isUsingCorrectComputer(obj_id objControlDevice, obj_id ship) throws InterruptedException {
        return !usesFlightComputerNotAstromech(ship) && hasObjVar(objControlDevice, "pet.creatureName") || usesFlightComputerNotAstromech(ship) && !hasObjVar(objControlDevice, "pet.creatureName");
    }
    public static boolean usesFlightComputerNotAstromech(obj_id ship) throws InterruptedException
    {
        String templateName = utils.getTemplateFilenameNoPath(ship);
        String shipUsesThis = dataTableGetString(STARSHIP_DROID_TABLE, templateName, "usesDroidOrFlightComputer");
        debugServerConsoleMsg(null, "+++ SPACE_CRAFTING . Got shipUsesThis. It was: " + shipUsesThis);
        if (shipUsesThis == null || shipUsesThis.equals(""))
        {
            debugServerConsoleMsg(null, "SPACE_CRAFTING.usesFlightComputerNotAstromech: shipUsesThis failed out as being -1");
            return false;
        }
        return shipUsesThis.equals("flight_computer");
    }
    public static boolean isMissileSlot(obj_id objShip, int intSlot) {
        if (isShipSlotInstalled(objShip, intSlot))
        {
            int intCrc = getShipComponentCrc(objShip, intSlot);
            return getShipComponentDescriptorWeaponIsMissile(intCrc);
        }
        return false;
    }
    public static boolean isCounterMeasureSlot(obj_id objShip, int intSlot) {
        if (isShipSlotInstalled(objShip, intSlot))
        {
            int intCrc = getShipComponentCrc(objShip, intSlot);
            return getShipComponentDescriptorWeaponIsCountermeasure(intCrc);
        }
        return false;
    }
    public static boolean isProperAmmoForWeapon(obj_id objAmmo, obj_id objShip, int intSlot) {
        int intWeaponAmmoType = getShipWeaponAmmoType(objShip, intSlot);
        int intWeaponType = getIntObjVar(objAmmo, "weapon.intAmmoType");
        int intCrc = getShipComponentCrc(objShip, intSlot);
        String descName = getShipComponentDescriptorCrcName(intCrc);
        if (intWeaponAmmoType == 0 && intWeaponType == 0 && !descName.equals("countermeasure_chaff_launcher"))
        {
            return false;
        }
        return intWeaponAmmoType == intWeaponType;
    }
    public static boolean applyAmmoToWeapon(obj_id objShip, obj_id objAmmo, int intSlot, obj_id objPlayer, boolean boolVerbose) throws InterruptedException
    {
        int intCurrentAmmo = getShipWeaponAmmoCurrent(objShip, intSlot);
        int intCount = getCount(objAmmo);
        float fltMinDamage = getFloatObjVar(objAmmo, "fltMinDamage");
        float fltMaxDamage = getFloatObjVar(objAmmo, "fltMaxDamage");
        float fltRefireRate = getFloatObjVar(objAmmo, "fltRefireRate");
        float fltWeaponEffectivenessShields = getFloatObjVar(objAmmo, "fltShieldEffectiveness");
        float fltWeaponEffectivenessArmor = getFloatObjVar(objAmmo, "fltArmorEffectiveness");
        if (intCurrentAmmo < intCount || fltMinDamage > getShipWeaponDamageMinimum(objShip, intSlot) || fltMaxDamage > getShipWeaponDamageMaximum(objShip, intSlot) || fltRefireRate > getShipWeaponRefireRate(objShip, intSlot) || fltWeaponEffectivenessArmor > getShipWeaponEffectivenessArmor(objShip, intSlot) || fltWeaponEffectivenessShields > getShipWeaponEffectivenessShields(objShip, intSlot))
        {
            setShipWeaponDamageMaximum(objShip, intSlot, fltMaxDamage);
            setShipWeaponDamageMinimum(objShip, intSlot, fltMinDamage);
            setShipWeaponRefireRate(objShip, intSlot, fltRefireRate);
            setShipWeaponEffectivenessArmor(objShip, intSlot, fltWeaponEffectivenessArmor);
            setShipWeaponEffectivenessShields(objShip, intSlot, fltWeaponEffectivenessShields);
            setShipWeaponAmmoMaximum(objShip, intSlot, intCount);
            setShipWeaponAmmoCurrent(objShip, intSlot, intCount);
            string_id strSpam = new string_id("space/space_interaction", "reloaded_x_missile_ammo");
            prose_package ppRepairInfo = prose.getPackage(strSpam, getName(objAmmo), (int)intCount);
            sendSystemMessageProse(objPlayer, ppRepairInfo);
            destroyObject(objAmmo);
        }
        return true;
    }
    public static boolean applyCountermeasuresToLauncher(obj_id objAmmo, obj_id objShip, int intSlot, obj_id objPlayer, boolean boolVerbose) throws InterruptedException
    {
        int intCurrentAmmo = getShipWeaponAmmoCurrent(objShip, intSlot);
        if (intCurrentAmmo != 0)
        {
            if (boolVerbose)
            {
                string_id strSpam = new string_id("space/space_interaction", "countermeasure_full_no_reload");
                sendSystemMessage(objPlayer, strSpam);
            }
            return false;
        }
        int intCount = getCount(objAmmo);
        float fltMinDamage = getFloatObjVar(objAmmo, "fltMinDamage");
        float fltMaxDamage = getFloatObjVar(objAmmo, "fltMaxDamage");
        float fltRefireRate = getFloatObjVar(objAmmo, "fltRefireRate");
        float fltEnergyPerShot = getFloatObjVar(objAmmo, "fltEnergyPerShot");
        setShipWeaponDamageMaximum(objShip, intSlot, fltMaxDamage);
        setShipWeaponDamageMinimum(objShip, intSlot, fltMinDamage);
        setShipWeaponRefireRate(objShip, intSlot, fltRefireRate);
        setShipWeaponAmmoMaximum(objShip, intSlot, intCount);
        setShipWeaponAmmoCurrent(objShip, intSlot, intCount);
        setShipWeaponEnergyPerShot(objShip, intSlot, fltEnergyPerShot);
        string_id strSpam = new string_id("space/space_interaction", "reloaded_x_countermeasure_ammo");
        prose_package ppRepairInfo = prose.getPackage(strSpam, getName(objAmmo), (int)intCount);
        sendSystemMessageProse(objPlayer, ppRepairInfo);
        destroyObject(objAmmo);
        return true;
    }
    public static boolean isWeaponAmmo(obj_id objAmmo) {
        return hasObjVar(objAmmo, "weapon.intAmmoType");
    }
    public static boolean isDamaged(obj_id objPlayer) throws InterruptedException
    {
        float fltDamage = getDamageTotalModified(space_transition.getContainingShip(objPlayer), 1.0f);
        return fltDamage > 0;
    }
    public static float getDamageTotal(obj_id objPlayer, obj_id objShip) throws InterruptedException
    {
        return getDamageTotalModified(objShip, 1.0f);
    }
    public static float getDamageTotalModified(obj_id objShip, float fltPercentage) throws InterruptedException
    {
        float fltMaximumHitpoints = getShipMaximumChassisHitPoints(objShip);
        float fltCurrentHitpoints = getShipCurrentChassisHitPoints(objShip);
        float fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
        float fltRepairValue = 0;
        fltRepairValue = fltRepairValue + fltDifference;
        int[] intSlots = getShipInstalledSlots(objShip);
        for (int intSlot : intSlots) {
            fltMaximumHitpoints = getShipComponentHitpointsMaximum(objShip, intSlot);
            fltCurrentHitpoints = getShipComponentHitpointsCurrent(objShip, intSlot);
            float fltMaximumArmorHitpoints = getShipComponentArmorHitpointsMaximum(objShip, intSlot);
            float fltCurrentArmorHitpoints = getShipComponentArmorHitpointsCurrent(objShip, intSlot);
            fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
            fltRepairValue = fltRepairValue + fltDifference;
            fltDifference = fltMaximumArmorHitpoints - fltCurrentArmorHitpoints;
            fltRepairValue = fltRepairValue + fltDifference;
        }
        fltRepairValue = fltRepairValue * fltPercentage;
        LOG("space", "Raw damage pre % of " + fltPercentage + "is " + fltRepairValue + " Modified damage POst % of " + fltPercentage + " is " + fltRepairValue);
        return fltRepairValue;
    }
    public static boolean repairDamageToPercentage(obj_id objPlayer, obj_id objShip, float fltPercentage) throws InterruptedException
    {
        LOG("space", "Repairing ship of " + objPlayer + " to " + fltPercentage);
        if (fltPercentage > 1.0f)
        {
            fltPercentage = 1.0f;
        }
        float fltMaximumHitpoints = getShipMaximumChassisHitPoints(objShip);
        float fltCurrentHitpoints = getShipCurrentChassisHitPoints(objShip);
        float fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
        LOG("space", "Chassis difference is " + fltDifference);
        fltDifference = fltDifference * fltPercentage;
        if (fltDifference > 0)
        {
            fltCurrentHitpoints = fltCurrentHitpoints + fltDifference;
            setShipCurrentChassisHitPoints(objShip, fltCurrentHitpoints);
        }

        int[] intSlots = getShipInstalledSlots(objShip);
        for (int intSlot : intSlots) {
            fltMaximumHitpoints = getShipComponentHitpointsMaximum(objShip, intSlot);
            fltCurrentHitpoints = getShipComponentHitpointsCurrent(objShip, intSlot);
            float fltMaximumArmorHitpoints = getShipComponentArmorHitpointsMaximum(objShip, intSlot);
            float fltCurrentArmorHitpoints = getShipComponentArmorHitpointsCurrent(objShip, intSlot);
            fltDifference = fltMaximumHitpoints - fltCurrentHitpoints;
            fltDifference = fltDifference * fltPercentage;
            LOG("space", "RAW for " + fltDifference + " worth of points on " + intSlot + " Repairing " + fltDifference + " worth of points");
            if (fltDifference > 0) {
                fltCurrentHitpoints = fltCurrentHitpoints + fltDifference;
                setShipComponentHitpointsCurrent(objShip, intSlot, fltCurrentHitpoints);
            } else {
                clearCondition(objShip, CONDITION_EJECT);
                space_utils.setComponentDisabled(objShip, intSlot, false);
            }

            fltDifference = fltMaximumArmorHitpoints - fltCurrentArmorHitpoints;
            fltDifference = fltDifference * fltPercentage;
            LOG("space", "RAW ARMOR for " + fltDifference + " worth of points on " + intSlot + " Repairing " + fltDifference + " worth of points");
            if (fltDifference > 0) {
                fltCurrentArmorHitpoints = fltCurrentArmorHitpoints + fltDifference;
                setShipComponentArmorHitpointsCurrent(objShip, intSlot, fltCurrentArmorHitpoints);
                clearCondition(objShip, CONDITION_EJECT);
                space_utils.setComponentDisabled(objShip, intSlot, false);
            }
            space_combat.recalculateEfficiency(intSlot, objShip);
        }
        space_combat.normalizeAllComponents(objShip);
        return true;
    }
    public static boolean repairDamage(obj_id objPlayer, obj_id objShip, float fltPercentage) throws InterruptedException
    {
        LOG("space", "Calling repairDamage ");
        return repairDamage(objPlayer, objShip, fltPercentage, 0.0f);
    }
    public static boolean repairDamage(obj_id objPlayer, obj_id objShip, float fltPercentage, float decayRate) throws InterruptedException
    {
        return repairDamageToPercentage(objPlayer, objShip, fltPercentage);
    }
    public static int[] getShipInstalledSlots(obj_id objShip) throws InterruptedException
    {
        int[] intRawSlots = getShipChassisSlots(objShip);
        Vector intInstalledSlots = new Vector();
        intInstalledSlots.setSize(0);
        for (int intRawSlot : intRawSlots) {
            if (isShipSlotInstalled(objShip, intRawSlot)) {
                intInstalledSlots = utils.addElement(intInstalledSlots, intRawSlot);
            }
        }
        int[] _intInstalledSlots = new int[0];
        if (intInstalledSlots != null)
        {
            _intInstalledSlots = new int[intInstalledSlots.size()];
            for (int _i = 0; _i < intInstalledSlots.size(); ++_i)
            {
                _intInstalledSlots[_i] = (Integer) intInstalledSlots.get(_i);
            }
        }
        return _intInstalledSlots;
    }
    public static boolean canAffordShipRepairs(obj_id objPlayer, obj_id objStation, float fltPercentage) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(objPlayer);
        if (!isIdValid(objShip))
        {
            return false;
        }
        float fltDamageTotal = getDamageTotal(objPlayer, objShip);
        fltDamageTotal = fltDamageTotal * fltPercentage;
        float fltCostPerPoint = getFloatObjVar(objStation, "fltCostPerDamagePoint");
        int intTotalCost = (int)(fltCostPerPoint * fltDamageTotal);
        if (space_utils.isBasicShip(objShip) || space_utils.isPrototypeShip(objShip))
        {
            intTotalCost = (int)(fltCostPerPoint * (100.0f * fltPercentage));
        }
        if (money.hasFunds(objPlayer, money.MT_TOTAL, intTotalCost))
        {
            LOG("space", "CANAFFORD");
            return true;
        }
        return false;
    }
    public static int getStationRepairCost(obj_id objPlayer, obj_id objStation, float fltPercentage) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(objPlayer);
        if (!isIdValid(objShip))
        {
            LOG("Space", "Returning asdsa");
            return 0;
        }
        float fltDamageTotal = getDamageTotal(objPlayer, objShip);
        LOG("space", "Total 1 damage i s" + fltDamageTotal);
        float fltCostPerPoint = getFloatObjVar(objStation, "fltCostPerDamagePoint");
        fltDamageTotal = fltDamageTotal * fltPercentage;
        LOG("space", "Total damage 2 is" + fltDamageTotal);
        int intTotalCost = (int)(fltCostPerPoint * fltDamageTotal);
        if (space_utils.isBasicShip(objShip) || space_utils.isPrototypeShip(objShip))
        {
            intTotalCost = (int)(fltCostPerPoint * (100.0f * fltPercentage));
        }
        LOG("space", "returning " + intTotalCost);
        return intTotalCost;
    }
    public static void doStationToShipRepairs(obj_id objPlayer, obj_id objStation, float fltPercentage) throws InterruptedException
    {
        LOG("space", "Doing station to ship repairs");
        float fltDistance = getDistance(objStation, space_transition.getContainingShip(objPlayer));
        if (fltDistance > space_transition.STATION_COMM_MAX_DISTANCE)
        {
            string_id strSpam = new string_id("space/space_interaction", "too_far");
            sendSystemMessage(objPlayer, strSpam);
            return;
        }
        fixAllPlasmaConduits(space_transition.getContainingShip(objPlayer));
        doStationToShipRepairs(objPlayer, objStation, fltPercentage, 0.0f);
    }
    public static void doStationToShipRepairs(obj_id objPlayer, obj_id objStation, float fltPercentage, float decayRate) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(objPlayer);
        LOG("space", "Doing station to ship repairs2 ");
        if (!isIdValid(objShip))
        {
            return;
        }
        float fltDamageTotal = getDamageTotal(objPlayer, objShip);
        float fltCostPerPoint = getFloatObjVar(objStation, "fltCostPerDamagePoint");
        fltDamageTotal = fltDamageTotal * fltPercentage;
        int intTotalCost = (int)(fltCostPerPoint * fltDamageTotal);
        if (space_utils.isBasicShip(objShip) || space_utils.isPrototypeShip(objShip))
        {
            intTotalCost = (int)(fltCostPerPoint * (100.0f * fltPercentage));
        }
        LOG("space", "Doing station to ship repairs3, charginh " + intTotalCost);
        if (money.hasFunds(objPlayer, money.MT_TOTAL, intTotalCost))
        {
            money.requestPayment(objPlayer, "SPACE_STATION_REPAIR", intTotalCost, "repairSuccess", null, false);
            LOG("space", "Taking cash");
            CustomerServiceLog("space_repair", "Taking " + intTotalCost + " from %TU for repairs", getOwner(objShip));
            repairDamage(objPlayer, objShip, fltPercentage);
        }
        else 
        {
            LOG("space", "NOFUNDS");
        }
    }
    public static float getDamagePercentage(obj_id objShip, int intSlot) {
        float fltMaximumHitpoints = getShipComponentArmorHitpointsMaximum(objShip, intSlot) + getShipComponentHitpointsMaximum(objShip, intSlot);
        float fltCurrentHitpoints = getShipComponentArmorHitpointsCurrent(objShip, intSlot) + getShipComponentHitpointsCurrent(objShip, intSlot);
        // protect against less than zero.
        if (fltCurrentHitpoints <= 0 || fltMaximumHitpoints <= 0)
        {
            return 0.0f;
        }
        // protect against more than one.
        if(fltCurrentHitpoints > fltMaximumHitpoints)
        {
            return 1.0f;
        }
        return fltCurrentHitpoints / fltMaximumHitpoints;
    }
    public static int getExtendedComponentType(String strSlot) {
        int intSlot = ship_chassis_slot_type.getTypeByName(strSlot);
        if (intSlot == ship_chassis_slot_type.SCST_num_types)
        {
            if (strSlot.equals("hull"))
            {
                return HULL;
            }
            if (strSlot.equals("life_support"))
            {
                return LIFE_SUPPORT;
            }
            if (strSlot.equals("plasma_conduit"))
            {
                return PLASMA_CONDUIT;
            }
            if (strSlot.equals("ammo_bay"))
            {
                return AMMO_BAY;
            }
            if (strSlot.equals("hyperdrive"))
            {
                return HYPERDRIVE;
            }
        }
        return intSlot;
    }
    public static String getExtendedComponentType(int intSlot) {
        String strType = ship_chassis_slot_type.getNameByType(intSlot);
        if (strType.equals("none"))
        {
            switch (intSlot) {
                case HULL:
                    return "hull";
                case LIFE_SUPPORT:
                    return "life_support";
                case PLASMA_CONDUIT:
                    return "plasma_conduit";
                case AMMO_BAY:
                    return "ammo_bay";
                case HYPERDRIVE:
                    return "hyperdrive";
                default:
                    return null;
            }
        }
        return strType;
    }
    public static void breakHullPanel(obj_id objPanel, obj_id objShip, location locTest, boolean boolAddToList) throws InterruptedException
    {
        int intHitpoints = getHitpoints(objPanel);
        if (intHitpoints == 1)
        {
            return;
        }
        if (boolAddToList)
        {
            Vector locBrokenComponents = new Vector();
            locBrokenComponents.setSize(0);
            if (hasObjVar(objShip, "locBrokenCompents"))
            {
                locBrokenComponents = getResizeableLocationArrayObjVar(objShip, "locBrokenComponents");
            }
            locBrokenComponents = utils.addElement(locBrokenComponents, locTest);
            setObjVar(objShip, "locBrokenComponents", locBrokenComponents);
        }
        setInvulnerableHitpoints(objPanel, 1);
        if (hasObjVar(objShip, "intHullBreached"))
        {
            float intDamage = getIntObjVar(objShip, "intHullBreached");
            intDamage = intDamage + HULL_BREACH_DAMAGE;
            setObjVar(objShip, "intHullBreached", intDamage);
        }
        else 
        {
            setObjVar(objShip, "intHullBreached", HULL_BREACH_DAMAGE);
            messageTo(objShip, "doHullBreach", null, HULL_BREACH_DAMAGE_TIMER, false);
            turnOnInteriorAlarms(objShip);
            string_id strSpam = new string_id("space/space_interaction", "hull_breach");
            space_utils.sendSystemMessageShip(objShip, strSpam, true, true, true, true);
        }
        float fltDamage = 0;
        if (hasShipInternalDamageOverTime(objShip, ship_chassis_slot_type.SCST_num_types))
        {
            fltDamage = getShipInternalDamageOverTimeDamageRate(objShip, ship_chassis_slot_type.SCST_num_types);
        }
        fltDamage = fltDamage + 0.5f;
        setShipInternalDamageOverTime(objShip, ship_chassis_slot_type.SCST_num_types, fltDamage, 0);
    }
    public static void fixHullPanel(obj_id objPanel, obj_id objShip, location locTest) throws InterruptedException
    {
        int intHitpoints = getHitpoints(objPanel);
        if (intHitpoints == getMaxHitpoints(objPanel))
        {
            return;
        }
        if (!hasObjVar(objShip, "locBrokenComponents"))
        {
            LOG("space", "ship dot system has faled on " + objShip);
            return;
        }
        Vector locBrokenComponents = getResizeableLocationArrayObjVar(objShip, "locBrokenComponents");
        int intIndex = utils.getElementPositionInArray(locBrokenComponents, locTest);
        locBrokenComponents = utils.removeElementAt(locBrokenComponents, intIndex);
        if (locBrokenComponents != null && locBrokenComponents.size() > 0)
        {
            setObjVar(objShip, "locBrokenComponents", locBrokenComponents);
        }
        else 
        {
            removeObjVar(objShip, "locBrokenComponents");
        }
        float intHullBreached = getIntObjVar(objShip, "intHullBreached");
        intHullBreached = intHullBreached - HULL_BREACH_DAMAGE;
        if (intHullBreached <= 0)
        {
            removeObjVar(objShip, "intHullBreached");
            turnOffInteriorAlarms(objShip);
        }
        else 
        {
            setObjVar(objShip, "intHullBreached", intHullBreached);
        }
        float fltDamage = 0;
        if (hasShipInternalDamageOverTime(objShip, ship_chassis_slot_type.SCST_num_types))
        {
            fltDamage = getShipInternalDamageOverTimeDamageRate(objShip, ship_chassis_slot_type.SCST_num_types);
        }
        fltDamage = fltDamage - 0.5f;
        if (fltDamage < 0)
        {
            fltDamage = 0;
        }
        setShipInternalDamageOverTime(objShip, ship_chassis_slot_type.SCST_num_types, fltDamage, 0);
        setInvulnerableHitpoints(objPanel, getMaxHitpoints(objPanel));
    }
    public static void breakPlasmaConduit(obj_id objConduit, obj_id objShip, location locTest, boolean boolAddToList) throws InterruptedException
    {
        if (locTest == null)
        {
            return;
        }
        locTest.area = "";
        LOG("space", "Breaking " + objConduit + " boolAddToList is " + boolAddToList);
        LOG("space", "locTest is " + locTest);
        obj_id objCell = locTest.cell;
        if (!isIdValid(objCell))
        {
            LOG("space", "Bad Cell!");
            return;
        }
        if (hasCondition(objConduit, CONDITION_ON))
        {
            LOG("space", "Already on!!");
            return;
        }
        if (!hasScript(objCell, "space.ship.ship_cell_manager"))
        {
            attachScript(objCell, "space.ship.ship_cell_manager");
        }
        turnOnInteriorAlarms(objShip);
        if (boolAddToList)
        {
            LOG("space", "adding " + locTest + " to list");
            Vector locBrokenComponents = new Vector();
            locBrokenComponents.setSize(0);
            if (hasObjVar(objShip, "locBrokenComponents"))
            {
                locBrokenComponents = getResizeableLocationArrayObjVar(objShip, "locBrokenComponents");
            }
            locBrokenComponents = utils.addElement(locBrokenComponents, locTest);
            LOG("space", "locBrokenComponetns is a resizeable array of length " + locBrokenComponents.size());
            setObjVar(objShip, "locBrokenComponents", locBrokenComponents);
        }
        if (hasObjVar(objConduit, "intConduitSlot"))
        {
            LOG("space", "has slot and breaking it ");
            int intConduitSlot = getIntObjVar(objConduit, "intConduitSlot");
            if (isShipSlotInstalled(objShip, intConduitSlot))
            {
                float fltDamage = 0;
                if (hasShipInternalDamageOverTime(objShip, intConduitSlot))
                {
                    fltDamage = getShipInternalDamageOverTimeDamageRate(objShip, intConduitSlot);
                }
                fltDamage = fltDamage + 0.5f;
                setShipInternalDamageOverTime(objShip, intConduitSlot, fltDamage, 0);
            }
            string_id strSpam = new string_id("space/space_interaction", "conduit_burst");
            prose_package pp = prose.getPackage(strSpam, getEncodedName(objConduit));
            space_utils.sendSystemMessageShip(objShip, pp, true, true, true, true);
        }
        dictionary dctParams = new dictionary();
        dctParams.put("intSlot", space_crafting.PLASMA_CONDUIT);
        LOG("space", "Notifying cell");
        space_utils.notifyObject(objCell, "incrementDotDamage", dctParams);
        setCondition(objConduit, CONDITION_ON);
    }
    public static void fixPlasmaConduit(obj_id objConduit, obj_id objShip, location locTest) throws InterruptedException
    {
        locTest.area = "";
        obj_id objCell = getLocation(objConduit).cell;
        LOG("space", "fixPlasmaConduit() Cell is " + objCell);
        if (!hasObjVar(objShip, "locBrokenComponents"))
        {
            LOG("space", "fixPlasmaConduit() ship dot system has faled on " + objShip);
            return;
        }
        Vector locBrokenComponents = getResizeableLocationArrayObjVar(objShip, "locBrokenComponents");
        int intIndex = utils.getElementPositionInArray(locBrokenComponents, locTest);
        LOG("space", "fixPlasmaConduit() conduit loc: " + locTest + " intIndex: " + intIndex);
        if (locBrokenComponents == null)
        {
            LOG("space", "fixPlasmaConduit() Broken components is null for some reason");
        }
        if (intIndex > -1)
        {
            locBrokenComponents = utils.removeElementAt(locBrokenComponents, intIndex);
            LOG("space", "fixPlasmaConduit() Removing " + intIndex + " locBrokenComponents.length is " + locBrokenComponents.size());
        }
        if (locBrokenComponents != null && locBrokenComponents.size() > 0)
        {
            setObjVar(objShip, "locBrokenComponents", locBrokenComponents);
        }
        else 
        {
            turnOffInteriorAlarms(objShip);
            removeObjVar(objShip, "intAlarmsOn");
            removeObjVar(objShip, "locBrokenComponents");
        }
        if (hasObjVar(objConduit, "intConduitSlot"))
        {
            int intConduitSlot = getIntObjVar(objConduit, "intConduitSlot");
            float fltDamage = 0;
            if (isShipSlotInstalled(objShip, intConduitSlot))
            {
                if (hasShipInternalDamageOverTime(objShip, intConduitSlot))
                {
                    fltDamage = getShipInternalDamageOverTimeDamageRate(objShip, intConduitSlot);
                }
                fltDamage = fltDamage - 0.5f;
                if (fltDamage < 0)
                {
                    fltDamage = 0;
                }
                setShipInternalDamageOverTime(objShip, intConduitSlot, fltDamage, 0);
            }
        }
        dictionary dctParams = new dictionary();
        dctParams.put("intSlot", space_crafting.PLASMA_CONDUIT);
        LOG("space", "fixPlasmaConduit() Notifying " + objCell);
        space_utils.notifyObject(objCell, "decrementDotDamage", dctParams);
        clearCondition(objConduit, CONDITION_ON);
    }
    public static boolean grantAmmoBay(obj_id objShip, int intSlot, transform trTest, obj_id objCell, int intLevel) {
        String strContainer = "object/tangible/container/drum/warren_drum_loot.iff";
        obj_id objContainer = createObject(strContainer, trTest, objCell);
        if (!isIdValid(objContainer))
        {
            return false;
        }
        setObjVar(objShip, "weapon.objAmmoBay" + intSlot, objContainer);
        persistObject(objContainer);
        setObjVar(objContainer, "intWeaponSlot", intSlot);
        attachScript(objContainer, "space.crafting.ammo_bay");
        setObjVar(objContainer, "intBayLevel", intLevel);
        return true;
    }
    public static boolean removeAmmoBay(obj_id objShip, int intSlot) {
        obj_id objBay = getObjIdObjVar(objShip, "weapon.objAmmoBay" + intSlot);
        if (isIdValid(objBay))
        {
            destroyObject(objBay);
            return true;
        }
        return false;
    }
    public static void reloadWeaponSlot(obj_id objShip, obj_id objItem, int intSlot, obj_id objPlayer, boolean boolVerbose) throws InterruptedException
    {
        if (space_crafting.isMissileSlot(objShip, intSlot))
        {
            space_crafting.applyAmmoToWeapon(objShip, objItem, intSlot, objPlayer, boolVerbose);
        }
        else if (space_crafting.isCounterMeasureSlot(objShip, intSlot))
        {
            space_crafting.applyCountermeasuresToLauncher(objShip, objItem, intSlot, objPlayer, boolVerbose);
        }
        else 
        {
            sendSystemMessage(objPlayer, SID_INCORRECTLY_CONFIGURED_ITEM);
        }
    }
    public static Vector getAllInstalledComponents(obj_id objShip) throws InterruptedException
    {
        int[] intRawSlots = getShipChassisSlots(objShip);
        Vector intSlots = new Vector();
        intSlots.setSize(0);
        for (int intRawSlot : intRawSlots) {
            if (isShipSlotInstalled(objShip, intRawSlot)) {
                intSlots = utils.addElement(intSlots, intRawSlot);
            }
        }
        return intSlots;
    }
    public static void turnOnInteriorAlarms(obj_id objShip) throws InterruptedException
    {
        if (utils.hasScriptVar(objShip, "objAlarms"))
        {
            setObjVar(objShip, "intAlarmsOn", 1);
            obj_id[] objAlarms = utils.getObjIdArrayScriptVar(objShip, "objAlarms");
            for (obj_id objAlarm : objAlarms) {
                setCondition(objAlarm, CONDITION_ON);
            }
        }
    }
    public static void turnOffInteriorAlarms(obj_id objShip) throws InterruptedException
    {
        if (utils.hasScriptVar(objShip, "objAlarms"))
        {
            removeObjVar(objShip, "intAlarmsOn");
            obj_id[] objAlarms = utils.getObjIdArrayScriptVar(objShip, "objAlarms");
            for (obj_id objAlarm : objAlarms) {
                clearCondition(objAlarm, CONDITION_ON);
            }
        }
    }
    public static boolean isValidShipComponent(obj_id objShipComponent) throws InterruptedException
    {
        if (isIdValid(objShipComponent)) {
            String type = getShipComponentStringType(objShipComponent);
            return type != null && !type.equals("") && (
                    getShipComponentStringType(objShipComponent).equals("armor") ||
                            getShipComponentStringType(objShipComponent).equals("booster") ||
                            getShipComponentStringType(objShipComponent).equals("capacitor") ||
                            getShipComponentStringType(objShipComponent).equals("droid_interface") ||
                            getShipComponentStringType(objShipComponent).equals("engine") ||
                            getShipComponentStringType(objShipComponent).equals("reactor") ||
                            getShipComponentStringType(objShipComponent).equals("shield") ||
                            getShipComponentStringType(objShipComponent).equals("weapon")
            );
        }
        return false;
    }
    public static void setShipEngineRotationRate(obj_id objPlayer, obj_id objShip, float fltRateMax, float fltRateMin, float fltOptimal) {
        setShipEngineSpeedRotationFactorMaximum(objShip, fltRateMax);
        setShipEngineSpeedRotationFactorMinimum(objShip, fltRateMin);
        setShipEngineSpeedRotationFactorOptimal(objShip, fltOptimal);
    }
    public static void setShipChassisPerformanceVariations(obj_id objShip, String strType, obj_id objPlayer) throws InterruptedException
    {
        String strFileName = "datatables/ship/chassis_modifiers.iff";
        dictionary dctShipInfo = dataTableGetRow(strFileName, strType);
        if (dctShipInfo == null)
        {
            sendSystemMessageTestingOnly(objPlayer, "No Ship Type of " + strType + " in " + strFileName);
            LOG("space", "Bad ship type of " + strType);
            return;
        }
        setShipEngineRotationRate(objPlayer, objShip, dctShipInfo.getFloat("speed_rotation_factor_max"), dctShipInfo.getFloat("speed_rotation_factor_min"), dctShipInfo.getFloat("speed_rotation_factor_optimal"));
    }
    public static void purchaseChassisFromBroker(obj_id player, obj_id broker) throws InterruptedException
    {
        final String SHIP_TABLE = "datatables/space_crafting/chassis_npc.iff";
        final String BROKER_STF = "chassis_npc";
        final String SCRIPTVAR_CHASSIS_SUI = "chassis_npc.sui";
        final String BUY_SHIP_TITLE = "@" + BROKER_STF + ":buy_ship_title";
        final String BUY_SHIP_PROMPT = "@" + BROKER_STF + ":buy_ship_prompt";
        final String BTN_BUY = "@" + BROKER_STF + ":btn_buy";
        int rows = dataTableGetNumRows(SHIP_TABLE);

        int shipPrices[] = new int[rows];
        int shipCount = 0;
        obj_id[] inv = getInventoryAndEquipment(player);
        obj_id[] shipDeeds = new obj_id[inv.length];
        String entries[] = new String[inv.length];
        for (obj_id anInv : inv) {
            if (hasObjVar(anInv, "shiptype")) {
                if (!hasObjVar(getContainedBy(anInv), "crafting.tool")) {
                    if (utils.getContainingPlayer(anInv) == player) {
                        shipDeeds[shipCount] = anInv;
                        String type = getStringObjVar(anInv, "shiptype");
                        int price = dataTableGetInt(SHIP_TABLE, type, 1);
                        String name = dataTableGetString(SHIP_TABLE, type, 2);
                        entries[shipCount] = ("[" + price + "] " + " --- " + name + " --- " + getName(anInv));
                        shipCount++;
                    }
                }
            }
        }
        String realEntries[] = new String[shipCount];
        System.arraycopy(entries, 0, realEntries, 0, realEntries.length);
        if (entries[0] != null && entries.length > 0)
        {
            int pid = sui.listbox(broker, player, BUY_SHIP_PROMPT, sui.OK_CANCEL, BUY_SHIP_TITLE, realEntries, "handleCheckShip", false, false);
            if (pid > -1)
            {
                setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_BUY);
                showSUIPage(pid);
                utils.setScriptVar(player, SCRIPTVAR_CHASSIS_SUI, pid);
                utils.setScriptVar(player, "chassis_npc.price", shipPrices);
                utils.setScriptVar(player, "chassis_npc.deed", shipDeeds);
            }
        }
        else 
        {
            string_id msgNoDeeds = new string_id(BROKER_STF, "no_deeds");
            chat.publicChat(broker, player, msgNoDeeds);
            utils.removeScriptVar(player, SCRIPTVAR_CHASSIS_SUI);
            utils.removeScriptVar(player, "chassis_npc.price");
            utils.removeBatchScriptVar(player, "chassis_npc.type");
            utils.removeScriptVar(player, "chassis_npc.deed");
        }
    }
    public static boolean sellLootItem(obj_id objPlayer, obj_id objBroker, obj_id objItem) throws InterruptedException
    {
        if (utils.hasScriptVar(objItem, "intSold"))
        {
            return false;
        }
        obj_id objOwner = utils.getContainingPlayer(objItem);
        if (objOwner != objPlayer)
        {
            return false;
        }
        float fltDistance = getDistance(objPlayer, objBroker);
        if (fltDistance > 24)
        {
            return false;
        }
        utils.setScriptVar(objItem, "intSold", 1);
        int intValue = getComponentSellPrice(objItem);
        CustomerServiceLog("chassis_broker", "%TU Sold item " + objItem + " (" + getTemplateName(objItem) + ")  To Dealer " + objBroker + " FOR " + intValue, objPlayer);
        string_id strSpam = new string_id("space/space_loot", "sold_item");
        prose_package proseTest = new prose_package();
        proseTest = prose.setStringId(proseTest, strSpam);
        proseTest = prose.setTO(proseTest, getEncodedName(objItem));
        proseTest = prose.setDI(proseTest, intValue);
        sendSystemMessageProse(objPlayer, proseTest);
        destroyObject(objItem);
        money.bankTo("space_component_sale", objPlayer, intValue);
        return true;
    }
    public static boolean hasLootToSell(obj_id objPlayer) {
        obj_id[] objContents = getInventoryAndEquipment(objPlayer);
        for (obj_id objContent : objContents) {
            if (hasScript(objContent, "space.crafting.component_loot")) {
                return true;
            }
        }
        return false;
    }
    public static int getComponentSellPrice(obj_id objComponent) throws InterruptedException
    {
        final String[] strLootLevels = 
        {
            "cert_ordnance_level1",
            "cert_ordnance_level2",
            "cert_ordnance_level3",
            "cert_ordnance_level4",
            "cert_ordnance_level5",
            "cert_ordnance_level6",
            "cert_ordnance_level7",
            "cert_ordnance_level8",
            "cert_ordnance_level9",
            "cert_ordnance_levelten"
        };
        final int[] intValue = 
        {
            1000,
            2000,
            3000,
            4000,
            5000,
            6000,
            7000,
            8000,
            9000,
            10000
        };
        String[] strCerts = getRequiredCertifications(objComponent);
        if ((strCerts != null) && (strCerts.length > 0))
        {
            int intIndex = utils.getElementPositionInArray(strLootLevels, strCerts[0]);
            if (intIndex < 0)
            {
                return 500;
            }
            return intValue[intIndex];
        }
        else 
        {
            return 500;
        }
    }
    public static void sellComponentsToBroker(obj_id objPlayer, obj_id objBroker) throws InterruptedException
    {
        String strTitle = "@space/space_loot:sell_loot_title";
        String strPrompt = "@space/space_loot:sell_loot_prompt";
        String strSellButton = "@space/space_loot:sell_loot_button";
        obj_id[] objContents = getInventoryAndEquipment(objPlayer);
        Vector objLoot = new Vector();
        objLoot.setSize(0);
        Vector ppEntries = new Vector();
        ppEntries.setSize(0);
        string_id strSpam = new string_id("space/space_loot", "item_list");
        for (obj_id objContent : objContents) {
            if (hasScript(objContent, "space.crafting.component_loot")) {
                int intValue = getComponentSellPrice(objContent);
                prose_package ppTest = new prose_package();
                prose.setTO(ppTest, getEncodedName(objContent));
                prose.setDI(ppTest, intValue);
                prose.setStringId(ppTest, strSpam);
                objLoot = utils.addElement(objLoot, objContent);
                ppEntries = utils.addElement(ppEntries, ppTest);
            }
        }
        if (ppEntries != null && ppEntries.size() > 0)
        {
            prose_package[] ppRawEntries = new prose_package[ppEntries.size()];
            for (int intI = 0; intI < ppEntries.size(); intI++)
            {
                prose_package ppTest = ((prose_package)ppEntries.get(intI));
                ppRawEntries[intI] = ppTest;
            }
            int pid = sui.listbox(objBroker, objPlayer, strPrompt, sui.OK_CANCEL, strTitle, ppRawEntries, "sellLootComponents", false, false);
            if (pid > -1)
            {
                setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, strSellButton);
                showSUIPage(pid);
                utils.setScriptVar(objPlayer, "objLootToSell", objLoot);
            }
        }
        else 
        {
            strSpam = new string_id("space/space_loot", "nothing_to_sell");
            sendSystemMessage(objPlayer, strSpam);
        }
    }
    public static obj_id createDeedFromBlueprints(obj_id player, String type, obj_id inventory, float mass, float hp) {
        if (isIdValid(player))
        {
            obj_id newDeed = createObject("object/tangible/ship/crafted/chassis/" + type + "_deed.iff", inventory, "");
            setObjVar(newDeed, "ship_chassis.mass", mass);
            setObjVar(newDeed, "ship_chassis.hp", hp);
            setObjVar(newDeed, "ship_chassis.type", type);
            return newDeed;
        }
        else 
        {
            return null;
        }
    }
    public static boolean setupChassisDifferentiation(obj_id objShip) throws InterruptedException
    {
        String strChassis = getShipChassisType(objShip);
        return setupChassisDifferentiation(objShip, strChassis);
    }
    public static boolean setupChassisDifferentiation(obj_id objShip, String strChassis) throws InterruptedException
    {
        if (space_utils.isPlayerControlledShip(objShip))
        {
            LOG("space", "setting modifier to " + strChassis);
            String strFileName = "datatables/ship/chassis_modifiers.iff";
            dictionary dctShipInfo = dataTableGetRow(strFileName, strChassis);
            if (dctShipInfo == null)
            {
                LOG("space", "No entry for " + strChassis);
                return false;
            }
            LOG("space", "setting modifier to " + dctShipInfo);
            setShipEngineAccelerationRate(objShip, dctShipInfo.getFloat("engine_accel"));
            setShipEngineDecelerationRate(objShip, dctShipInfo.getFloat("engine_decel"));
            setShipEnginePitchAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_pitch_accel"));
            setShipEngineYawAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_yaw_accel"));
            setShipEngineRollAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_roll_accel"));
            setShipEngineSpeedRotationFactorMaximum(objShip, dctShipInfo.getFloat("speed_rotation_factor_max"));
            setShipEngineSpeedRotationFactorMinimum(objShip, dctShipInfo.getFloat("speed_rotation_factor_min"));
            setShipEngineSpeedRotationFactorOptimal(objShip, dctShipInfo.getFloat("speed_rotation_factor_optimal"));
            setShipSlideDampener(objShip, dctShipInfo.getFloat("slideDamp"));
            setShipChassisSpeedMaximumModifier(objShip, dctShipInfo.getFloat("fltMaxSpeedModifier"));
        }
        else 
        {
            LOG("space", "NO ROTATIONAL RESET");
            return false;
        }
        return true;
    }
    public static void resetChassisRotationalVelocity(obj_id objShip) throws InterruptedException
    {
        if (space_utils.isPlayerControlledShip(objShip))
        {
            String strChassis = getShipChassisType(objShip);
            String strFileName = "datatables/ship/chassis_modifiers.iff";
            float fltRotationSpeedFactorMaximum = dataTableGetFloat(strFileName, strChassis, "speed_rotation_factor");
            if (fltRotationSpeedFactorMaximum <= 0)
            {
                fltRotationSpeedFactorMaximum = 1.0f;
            }
            setShipEngineSpeedRotationFactorMaximum(objShip, fltRotationSpeedFactorMaximum);
        }
    }
    public static void clearComponentDisabledFlag(obj_id objComponent) {
        int flags = getIntObjVar(objComponent, "ship_comp.flags");
        flags &= ~(base_class.ship_component_flags.SCF_disabled);
        flags &= ~(base_class.ship_component_flags.SCF_demolished);
        setObjVar(objComponent, "ship_comp.flags", flags);
    }
    public static obj_id createChassisFromDeed(obj_id player, obj_id deed, float hp, float currentHp, float mass, String type) throws InterruptedException
    {
        final String STF = "chassis_npc";
        if (isIdValid(player))
        {
            obj_id datapad = utils.getDatapad(player);
            if(!isIdValid(datapad) || !exists(datapad) || getVolumeFree(datapad) <= 0){
                debugServerConsoleMsg(player, "Unable to get datapad to create chassis OR datapad is full.");
                debugServerConsoleMsg(player, "Player was " + getName(player));
                debugServerConsoleMsg(player, "Players location is " + getLocation(player).toString());
                debugServerConsoleMsg(player, "Players area is " + getLocation(player).area);
                debugServerConsoleMsg(player, "Datapad is: " + datapad.toString());
                debugServerConsoleMsg(player, "Datapad free space is " + getVolumeFree(datapad));
                return null;
            }
            obj_id pcd = createObject("object/intangible/ship/" + type + "_pcd.iff", datapad, "");
            if (!isIdValid(pcd))
            {
                return null;
            }
            else 
            {
                obj_id ship = createObject("object/ship/player/player_" + type + ".iff", pcd, "");
                if (!hasCertificationsForItem(player, ship))
                {
                    destroyObject(ship);
                    destroyObject(pcd);
                    sendSystemMessage(player, new string_id(STF, "not_certified"));
                    return null;
                }
                if (hasObjVar(deed, "ship_chassis.badgeRestricted") && !isGod(player))
                {
                    int badgeReq = getIntObjVar(deed, "ship_chassis.badgeRestricted");
                    String badgeName = getCollectionSlotName(badgeReq);
                    if ((badgeName == null) || (badgeName.length() == 0) || (!badge.hasBadge(player, badgeName)))
                    {
                        destroyObject(ship);
                        destroyObject(pcd);
                        sendSystemMessage(player, new string_id(STF, "no_badge"));
                        return null;
                    }
                }
                if (hasObjVar(deed, "ship_chassis.groundQuestRestricted") && !isGod(player))
                {
                    String reqGroundQuest = getStringObjVar(deed, "ship_chassis.groundQuestRestricted");
                    if (!groundquests.hasCompletedQuest(player, reqGroundQuest))
                    {
                        destroyObject(ship);
                        destroyObject(pcd);
                        sendSystemMessage(player, new string_id(STF, "no_quest"));
                        return null;
                    }
                }
                if (hasObjVar(deed, "ship_chassis.spaceQuestRestricted") && !isGod(player))
                {
                    String reqSpaceQuest = getStringObjVar(deed, "ship_chassis.spaceQuestRestricted");
                    String reqSpaceQuestType = getStringObjVar(deed, "ship_chassis.spaceQuestRestrictedType");
                    if (!space_quest.hasReceivedReward(player, reqSpaceQuestType, reqSpaceQuest) && !space_quest.hasWonQuest(player, reqSpaceQuestType, reqSpaceQuest))
                    {
                        destroyObject(ship);
                        destroyObject(pcd);
                        sendSystemMessage(player, new string_id(STF, "no_quest"));
                        return null;
                    }
                }
                if (hasObjVar(deed, TCG_SHIP_TYPE))
                {
                    setObjVar(pcd, TCG_SHIP_TYPE, true);
                    setObjVar(pcd, TCG_SHIP_DEED_STATIC_ITEM_NAME, getStaticItemName(deed));
                }
                if (isIdValid(ship))
                {
                    CustomerServiceLog("ship_deed", "ship deed used: deed=" + deed + " scd=" + pcd + " ship=" + ship + " player=" + player + "(" + getPlayerName(player) + ")");
                    if (space_utils.isPobType(type))
                    {
                        if (!hasObjVar(pcd, "lotReqRemoved"))
                        {
                            setObjVar(pcd, "lotReqRemoved", true);
                        }
                    }
                    setOwner(ship, player);
                    if (!type.equals("sorosuub_space_yacht"))
                    {
                        space_crafting.uninstallAll(ship);
                    }
                    setShipMaximumChassisHitPoints(ship, hp);
                    if (currentHp > hp)
                    {
                        currentHp = hp;
                    }
                    setShipCurrentChassisHitPoints(ship, currentHp);
                    setChassisComponentMassMaximum(ship, mass);
                    if (hasObjVar(deed, "noTrade"))
                    {
                        setObjVar(pcd, "noTrade", true);
                    }
                    if (!hasScript(deed, "item.special.nodestroy"))
                    {
                        destroyObject(deed);
                    }
                    string_id successMessage = new string_id(STF, "succeed");
                    sendSystemMessage(player, successMessage);
                }
                else 
                {
                    string_id message3 = new string_id(STF, "failed");
                    sendSystemMessage(player, message3);
                }
            }
        }
        return null;
    }
    public static void fixAllPlasmaConduits(obj_id objShip) throws InterruptedException
    {
        LOG("space", "FIXING ALL PLASMA CONDUITS");
        if (utils.hasScriptVar(objShip, "objPlasmaConduits"))
        {
            Vector objPlasmaConduits = utils.getResizeableObjIdArrayScriptVar(objShip, "objPlasmaConduits");
            for (int intI = 0; intI < objPlasmaConduits.size(); intI++)
            {
                LOG("space", "Plasma conduit [" + intI + "]");
                if (hasCondition(((obj_id)objPlasmaConduits.get(intI)), CONDITION_ON))
                {
                    LOG("space", "Conduit is on");
                    fixPlasmaConduit(((obj_id)objPlasmaConduits.get(intI)), objShip, getLocation(((obj_id)objPlasmaConduits.get(intI))));
                }
            }
        }
    }
    public static void sellResourcesToSpaceStation(obj_id player, obj_id npc) {
        String priceList = getStringObjVar(npc, "space_mining.priceList");
        if (isIdValid(player) && isIdValid(npc))
        {
            openSpaceMiningUi(player, npc, priceList);
        }
    }
    public static boolean checkForCollectionReactor(obj_id self, obj_id ship) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        if (!isIdValid(ship))
        {
            return false;
        }
        if (!isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_reactor))
        {
            setCollectionReactorChecked(self);
            return false;
        }
        String shipControlDeviceName = getName(self);
        CustomerServiceLog("ShipComponents", "Collection reactor check for: (" + self + ") " + shipControlDeviceName + " has found that the ship (" + ship + ") has a reactor installed, resuming check.");
        blog("component_fix", "SCD - SHIP " + ship + " HAS A REACTOR");
        String shipType = getShipChassisType(ship);
        if (shipType.equals("player_sorosuub_space_yacht"))
        {
            return false;
        }
        blog("component_fix", "SCD - SHIP " + ship + " NOT SOROSUUB");
        obj_id datapad = utils.getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return false;
        }
        CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ")");
        blog("component_fix", "SCD - TOPCONTAINER, hopefully a datapad: " + datapad);
        obj_id player = utils.getContainedBy(datapad);
        if (!isIdValid(datapad))
        {
            return false;
        }
        blog("component_fix", "SCD - TOPCONTAINER, hopefully a player: " + player);
        CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ")");
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isIdValid(pInv))
        {
            return false;
        }
        blog("component_fix", "SCD - Inventory container " + player);
        CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  That player's inventory is: (" + pInv + ")");
        blog("component_fix", "SCD - player: " + player);
        blog("component_fix", "SCD - PpInv: " + pInv);
        blog("component_fix", "SCD - Ship: " + ship + " Type: " + shipType);
        int[] intRawSlots = getShipChassisSlots(ship);
        blog("component_fix", "SCD - Ship Control Device: " + getName(self));
        blog("component_fix", "SCD - first content: " + getName(ship));
        blog("component_fix", "SCD - slots: " + intRawSlots.length);
        blog("component_fix", "SCD - chassisType: " + getShipChassisType(ship));
        CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  The ship has: " + intRawSlots + " component slots that will be looped through to find a collection reactor.");
        boolean reactorFound = false;
        for (int intRawSlot : intRawSlots) {
            String component = space_crafting.getShipComponentStringType(intRawSlot);
            if (component == null || component.equals("")) {
                continue;
            }
            if (!component.equals("reactor")) {
                continue;
            }
            CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  We are in the reactor slot.");
            blog("component_fix", "SCD - FOUND REACTOR");
            int crc = getShipComponentCrc(ship, intRawSlot);
            blog("component_fix", "SCD - REACTOR CRC: " + crc);
            String crcName = getShipComponentDescriptorCrcName(crc);
            blog("component_fix", "SCD - REACTOR NAME: " + crcName);
            if (!crcName.startsWith("collection_reward_reactor_01_mk") || crcName.equals("collection_reward_reactor_01_mk5")) {
                setCollectionReactorChecked(self);
                break;
            }
            CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  We are in the reactor slot and WE HAVE FOUND A COLLECTION REACTOR WE NEED TO DESTROY: " + crcName);
            blog("component_fix", "SCD - THIS REACTOR NEEDS TO BE DESTROYED: " + crcName);
            boolean success = uninstallDeleteReplaceCollectionReactor(self, player, ship, crcName, pInv);
            if (success) {
                blog("component_fix", "SCD - SUCCESS MESSAGE RECEIVED, LOOP CONTINUES/ENDS: ");
                CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  The reactor (" + crcName + ") was ejected from the ship and should now be destroyed.");
                reactorFound = true;
                setCollectionReactorChecked(self);
            } else {
                blog("component_fix", "SCD - FAIL MESSAGE RECEIVED, THIS ISN'T WORKING: ");
                CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  The reactor (" + crcName + ") FAILED TO EJECT AND COULD NOT BE DESTORYED.");
            }
            break;
        }
        if (!reactorFound)
        {
            CustomerServiceLog("ShipComponents", "Collection reactor NOT FOUND in ship (" + ship + ") in datapad:(" + datapad + ") which belongs to player (" + player + "). EXITING.");
            return false;
        }
        CustomerServiceLog("ShipComponents", "Collection reactor check for (" + self + ") has found ship (" + ship + ") is in datapad:(" + datapad + ") which belongs to player (" + player + ").  The collection reactor process is now complete.");
        return true;
    }
    public static boolean uninstallDeleteReplaceCollectionReactor(obj_id self, obj_id player, obj_id ship, String crcName, obj_id pInv) throws InterruptedException
    {
        blog("component_fix", "SCD - uninstallDeleteReplaceCollectionReactor function");
        if (!isIdValid(player))
        {
            return false;
        }
        if (!isIdValid(ship))
        {
            return false;
        }
        if (crcName == null || crcName.equals(""))
        {
            return false;
        }
        if (!isIdValid(pInv))
        {
            return false;
        }
        blog("component_fix", "SCD - uninstallDeleteReplaceCollectionReactor VALIDATION");
        CustomerServiceLog("ShipComponents", "Collection reactor uninstall for (" + self + ") in ship (" + ship + ") belonging to player: (" + player + "). We found reactor: (" + crcName + ") and will attempt to uninstall, replace it with a schematic and delete the reactor while it is in player inventory (" + pInv + ").");
        CustomerServiceLog("ShipComponents", "BEGIN UNINSTALL for (" + self + ") in ship (" + ship + ") belonging to player: (" + player + "). We found reactor: (" + crcName + ") and will attempt to uninstall, replace it with a schematic and delete the reactor while it is in player inventory (" + pInv + ").");
        blog("component_fix", "SCD - uninstallDeleteReplaceCollectionReactor ABOUT TO UNINSTALL");
        obj_id unistalledComponent = shipUninstallComponentAllowOverload(player, ship, ship_chassis_slot_type.SCST_reactor, pInv);
        if (!isIdValid(unistalledComponent))
        {
            CustomerServiceLog("ShipComponents", "FAILED TO UNINSTALL COMPONENT for (" + self + ") in ship (" + ship + ") belonging to player: (" + player + "). We found reactor: (" + crcName + ") and for some reason CANNOT UNINSTALL. We are aborting replacement with schematic as we cannot delete the reactor in player inventory (" + pInv + ").");
            blog("component_fix", "SCD - FAILED TO UNINSTALL COMPONENT");
            return false;
        }
        blog("component_fix", "SCD - SUCCESS WITH COMPONENT UNINSTALL");
        CustomerServiceLog("ShipComponents", "SUCCESS WITH UNINSTALL for (" + self + ") in ship (" + ship + ") belonging to player: (" + player + "). We found reactor: (" + crcName + ") and UNINSTALLED IT. We are are relying on the component (component: " + unistalledComponent + ") to replace itself with a schematic and delete itself in player inventory (" + pInv + ").");
        sendSystemMessage(player, SID_COLLECTION_REACTOR_REPLACED);
        return true;
    }
    public static boolean setCollectionReactorChecked(obj_id self) {
        setObjVar(self, COLLECTION_REACTOR_CHECKED, true);
        return true;
    }
    public static boolean blog(String category, String msg) {
        return true;
    }
}
