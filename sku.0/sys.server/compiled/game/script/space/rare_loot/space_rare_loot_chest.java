package script.space.rare_loot;

import script.dictionary;
import script.library.create;
import script.library.space_crafting;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class space_rare_loot_chest extends script.base_script
{
    public static final String ITEM_TYPE_WEAPON = "weapon";
    public static final String ITEM_TYPE_ENGINE = "engine";
    public static final String ITEM_TYPE_BOOSTER = "booster";
    public static final String ITEM_TYPE_REACTOR = "reactor";
    public static final String ITEM_TYPE_SHIELD = "shield";
    public static final String ITEM_TYPE_CAPACITOR = "capacitor";
    public static final String ITEM_TYPE_ARMOR = "armor";
    public static final String ITEM_TYPE_DROID_INTERFACE = "droid_interface";
    public static final String ARMOR_TABLE = "datatables/ship/components/armor.iff";
    public static final String BOOSTER_TABLE = "datatables/ship/components/booster.iff";
    public static final String CAPACITOR_TABLE = "datatables/ship/components/capacitor.iff";
    public static final String DROID_INTERFACE_TABLE = "datatables/ship/components/droid_interface.iff";
    public static final String DROID_INTERFACE_OVERRIDE_TABLE = space_rare_loot.SPACE_RLS_TABLE_LOCATION + "/droid_interface_override.iff";
    public static final String ENGINE_TABLE = "datatables/ship/components/engine.iff";
    public static final String REACTOR_TABLE = "datatables/ship/components/reactor.iff";
    public static final String SHIELD_TABLE = "datatables/ship/components/shield.iff";
    public static final String OVERRIDE_COLUMN_COMMAND_SPEED_MODIFIER = "commandSpeedModifier";
    public static final String OVERRIDE_COLUMN_HITPOINTS_MODIFIER = "hitpointsModifier";
    public static final String OVERRIDE_COLUMN_ARMOR_HITPOINTS_MODIFIER = "armorHitpointsModifier";
    public static final String OVERRIDE_COLUMN_ENERGY_MAINTENANCE_MODIFIER = "energyMaintenanceModifier";
    public static final String OVERRIDE_COLUMN_MASS_MODIFIER = "massModifier";

    public space_rare_loot_chest()
    {
    }

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("npe", "crate_use"));
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, space_rare_loot.VAR_CHEST_REWARD_QUALITY) || !hasObjVar(self, space_rare_loot.VAR_CHEST_REWARD_TIER))
        {
            space_rare_loot.debug(player, "space rare loot chest is missing reward objvars.");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, missing reward objvars. Player=" + player + " chest=" + self);
            return SCRIPT_CONTINUE;
        }
        int rewardQuality = getIntObjVar(self, space_rare_loot.VAR_CHEST_REWARD_QUALITY);
        int rewardTier = getIntObjVar(self, space_rare_loot.VAR_CHEST_REWARD_TIER);
        rewardTier = space_rare_loot.getClampedRewardTier(rewardTier);
        String rewardQualityName = space_rare_loot.getRewardQualityName(rewardQuality);
        float rewardQualityModifier = space_rare_loot.getRewardQualityModifier(rewardQuality);
        String rewardTable = space_rare_loot.getRewardTable(rewardTier);
        int rewardRow = space_rare_loot.getRandomRewardRow(rewardTable, rewardQualityName);
        if (rewardRow < 0)
        {
            space_rare_loot.debug(player, "opened chest " + self + " but found no enabled " + rewardQualityName + " or all reward rows in " + rewardTable + ".");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, no reward row. Player=" + player + " chest=" + self + " rewardTier=" + rewardTier + " rewardTable=" + rewardTable + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")");
            return SCRIPT_CONTINUE;
        }
        String itemName = dataTableGetString(rewardTable, rewardRow, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_NAME);
        String itemType = dataTableGetString(rewardTable, rewardRow, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_TYPE);
        obj_id rewardItem = createSpaceRareLootItem(player, itemName, itemType, rewardQuality, rewardTier, rewardQualityModifier);
        if (!isIdValid(rewardItem))
        {
            space_rare_loot.debug(player, "opened chest " + self + " but failed to create item " + itemName + " of type " + itemType + ".");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, item creation failed. Player=" + player + " chest=" + self + " rewardTier=" + rewardTier + " rewardTable=" + rewardTable + " rewardRow=" + rewardRow + " itemName=" + itemName + " itemType=" + itemType + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")");
            return SCRIPT_CONTINUE;
        }

        space_rare_loot.debug(player, "opened chest " + self + " with reward tier " + rewardTier + ", quality " + rewardQualityName + ", row " + rewardRow + ", item " + itemName + ".");
        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest opened. Player=" + player + " chest=" + self + " rewardItem=" + rewardItem + " rewardTier=" + rewardTier + " rewardTable=" + rewardTable + " rewardRow=" + rewardRow + " itemName=" + itemName + " itemType=" + itemType + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")" + " rewardQualityModifier=" + rewardQualityModifier);
        obj_id[] lootedItems = new obj_id[1];
        lootedItems[0] = rewardItem;
        showLootBox(player, lootedItems);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }

    public obj_id createSpaceRareLootItem(obj_id player, String itemName, String itemType, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        if (itemType == null)
        {
            return null;
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_WEAPON))
        {
            return stampRareSpaceLootItem(createWeaponReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_ENGINE))
        {
            return stampRareSpaceLootItem(createEngineReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_BOOSTER))
        {
            return stampRareSpaceLootItem(createBoosterReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_REACTOR))
        {
            return stampRareSpaceLootItem(createReactorReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_SHIELD))
        {
            return stampRareSpaceLootItem(createShieldReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_CAPACITOR))
        {
            return stampRareSpaceLootItem(createCapacitorReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_ARMOR))
        {
            return stampRareSpaceLootItem(createArmorReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_DROID_INTERFACE))
        {
            return stampRareSpaceLootItem(createDroidInterfaceReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier), rewardQuality, rewardTier);
        }
        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, unknown item type. Player=" + player + " itemName=" + itemName + " itemType=" + itemType + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
        return null;
    }

    public obj_id stampRareSpaceLootItem(obj_id item, int rewardQuality, int rewardTier) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return item;
        }
        setObjVar(item, space_rare_loot.VAR_ITEM_IS_RARE_SPACE_LOOT, true);
        setObjVar(item, space_rare_loot.VAR_ITEM_REWARD_QUALITY, rewardQuality);
        setObjVar(item, space_rare_loot.VAR_ITEM_REWARD_QUALITY_NAME, space_rare_loot.getRewardQualityName(rewardQuality));
        setObjVar(item, space_rare_loot.VAR_ITEM_REWARD_TIER, rewardTier);
        return item;
    }

    public obj_id createWeaponReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        if (!isIdValid(inventory) || getVolumeFree(inventory) <= 0)
        {
            return null;
        }
        obj_id weapon = create.object(itemName, inventory, false, false);
        if (!isIdValid(weapon))
        {
            return null;
        }
        dictionary weaponData = dataTableGetRow(space_crafting.SHIP_WEAPON_TABLE, itemName);
        if (weaponData == null)
        {
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot weapon creation failed, missing weapon data. Player=" + player + " weapon=" + weapon + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
            destroyObject(weapon);
            return null;
        }
        applyStandardComponentStats(weapon, weaponData, rewardQuality);

        float minDamage = getDirectedValue(weaponData.getFloat("fltMinDamage"), weaponData.getFloat("fltMinDamageModifier"), rewardQuality, false);
        float maxDamage = getDirectedValue(weaponData.getFloat("fltMaxDamage"), weaponData.getFloat("fltMaxDamageModifier"), rewardQuality, false);
        if (maxDamage < minDamage)
        {
            maxDamage = minDamage;
        }
        space_crafting.setWeaponMinimumDamage(weapon, minDamage);
        space_crafting.setWeaponMaximumDamage(weapon, maxDamage);
        space_crafting.setWeaponShieldEffectiveness(weapon, getDirectedValue(weaponData.getFloat("fltShieldEffectiveness"), weaponData.getFloat("fltShieldEffectivenessModifier"), rewardQuality, false));
        space_crafting.setWeaponArmorEffectiveness(weapon, getDirectedValue(weaponData.getFloat("fltArmorEffectiveness"), weaponData.getFloat("fltArmorEffectivenessModifier"), rewardQuality, false));
        space_crafting.setWeaponEnergyPerShot(weapon, getDirectedValue(weaponData.getFloat("fltEnergyPerShot"), weaponData.getFloat("fltEnergyPerShotModifier"), rewardQuality, true));
        space_crafting.setWeaponRefireRate(weapon, getDirectedValue(weaponData.getFloat("fltRefireRate"), weaponData.getFloat("fltRefireRateModifier"), rewardQuality, true));

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot weapon created. Player=" + player + " weapon=" + weapon + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier + " minDamage=" + minDamage + " maxDamage=" + maxDamage);
        return weapon;
    }

    public void applyStandardComponentStats(obj_id component, dictionary componentData, int rewardQuality) throws InterruptedException
    {
        float energyMaintenance = getDirectedValue(componentData.getFloat("fltEnergyMaintenance"), componentData.getFloat("fltEnergyMaintenanceModifier"), rewardQuality, true);
        float mass = getDirectedValue(componentData.getFloat("fltMass"), componentData.getFloat("fltMassModifier"), rewardQuality, true);
        float armorHitpoints = getDirectedValue(componentData.getFloat("fltMaximumArmorHitpoints"), componentData.getFloat("fltMaximumArmorHitpointsMod"), rewardQuality, false);
        float hitpoints = getDirectedValue(componentData.getFloat("fltMaximumHitpoints"), componentData.getFloat("fltMaximumArmorHitpointsMod"), rewardQuality, false);
        float efficiency = componentData.getFloat("fltEfficiency");

        space_crafting.setComponentCurrentArmorHitpoints(component, armorHitpoints);
        space_crafting.setComponentMaximumArmorHitpoints(component, armorHitpoints);
        space_crafting.setComponentCurrentConduitHitpoints(component, hitpoints);
        space_crafting.setComponentMaximumConduitHitpoints(component, hitpoints);
        space_crafting.setComponentCurrentHitpoints(component, hitpoints);
        space_crafting.setComponentMaximumHitpoints(component, hitpoints);
        space_crafting.setComponentMass(component, mass);
        space_crafting.setComponentEnergyMaintenance(component, energyMaintenance);
        space_crafting.setComponentEnergyEfficiency(component, efficiency);
        space_crafting.setComponentGeneralEfficiency(component, efficiency);
    }

    public float getDirectedValue(float baseValue, float statModifier, int rewardQuality, boolean lowerIsBetter) throws InterruptedException
    {
        float effectiveModifier = space_rare_loot.getEffectiveStatModifier(statModifier, rewardQuality);
        return space_crafting.getDirectedBellValue(baseValue, effectiveModifier, space_crafting.getRandomBellDeviation(), lowerIsBetter);
    }

    public obj_id createEngineReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id engine = createComponentFromTable(player, itemName, ENGINE_TABLE, rewardQuality, rewardTier);
        if (!isIdValid(engine))
        {
            return null;
        }
        dictionary engineData = dataTableGetRow(ENGINE_TABLE, itemName);
        space_crafting.setEngineMaximumSpeed(engine, getDirectedValue(engineData.getFloat("fltMaxSpeed"), engineData.getFloat("fltMaxSpeedModifier"), rewardQuality, false));
        space_crafting.setEngineMaximumPitch(engine, getDirectedValue(engineData.getFloat("fltMaxPitch"), engineData.getFloat("fltMaxPitchModifier"), rewardQuality, false));
        space_crafting.setEngineMaximumRoll(engine, getDirectedValue(engineData.getFloat("fltMaxRoll"), engineData.getFloat("fltMaxRollModifier"), rewardQuality, false));
        space_crafting.setEngineMaximumYaw(engine, getDirectedValue(engineData.getFloat("fltMaxYaw"), engineData.getFloat("fltMaxYawModifier"), rewardQuality, false));

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot engine created. Player=" + player + " engine=" + engine + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier);
        return engine;
    }

    public obj_id createBoosterReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id booster = createComponentFromTable(player, itemName, BOOSTER_TABLE, rewardQuality, rewardTier);
        if (!isIdValid(booster))
        {
            return null;
        }
        dictionary boosterData = dataTableGetRow(BOOSTER_TABLE, itemName);
        float maxEnergy = getDirectedValue(boosterData.getFloat("fltMaximumEnergy"), boosterData.getFloat("fltMaximumEnergyModifier"), rewardQuality, false);
        space_crafting.setBoosterMaximumEnergy(booster, maxEnergy);
        space_crafting.setBoosterCurrentEnergy(booster, maxEnergy);
        space_crafting.setBoosterEnergyRechargeRate(booster, getDirectedValue(boosterData.getFloat("fltRechargeRate"), boosterData.getFloat("fltRechargeRateModifier"), rewardQuality, false));
        space_crafting.setBoosterEnergyConsumptionRate(booster, getDirectedValue(boosterData.getFloat("fltConsumptionRate"), boosterData.getFloat("fltConsumptionRateModifier"), rewardQuality, true));
        space_crafting.setBoosterAcceleration(booster, getDirectedValue(boosterData.getFloat("fltAcceleration"), boosterData.getFloat("fltAccelerationModifier"), rewardQuality, false));
        space_crafting.setBoosterMaximumSpeed(booster, getDirectedValue(boosterData.getFloat("fltMaxSpeed"), boosterData.getFloat("fltMaxSpeedModifier"), rewardQuality, false));

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot booster created. Player=" + player + " booster=" + booster + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier + " maxEnergy=" + maxEnergy);
        return booster;
    }

    public obj_id createReactorReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id reactor = createComponentFromTable(player, itemName, REACTOR_TABLE, rewardQuality, rewardTier);
        if (!isIdValid(reactor))
        {
            return null;
        }
        dictionary reactorData = dataTableGetRow(REACTOR_TABLE, itemName);
        float energyGeneration = getDirectedValue(reactorData.getFloat("fltEnergyGeneration"), reactorData.getFloat("fltEnergyGenerationModifier"), rewardQuality, false);
        space_crafting.setReactorEnergyGeneration(reactor, energyGeneration);

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot reactor created. Player=" + player + " reactor=" + reactor + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier + " energyGeneration=" + energyGeneration);
        return reactor;
    }

    public obj_id createShieldReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id shield = createComponentFromTable(player, itemName, SHIELD_TABLE, rewardQuality, rewardTier);
        if (!isIdValid(shield))
        {
            return null;
        }
        dictionary shieldData = dataTableGetRow(SHIELD_TABLE, itemName);
        float frontHitpoints = getDirectedValue(shieldData.getFloat("fltShieldHitpointsMaximumFront"), shieldData.getFloat("fltShieldHitpointsMaximumFrontModifier"), rewardQuality, false);
        float backHitpoints = getDirectedValue(shieldData.getFloat("fltShieldHitpointsMaximumBack"), shieldData.getFloat("fltShieldHitpointsMaximumBackModifier"), rewardQuality, false);
        float rechargeRate = getDirectedValue(shieldData.getFloat("fltShieldRechargeRate"), shieldData.getFloat("fltShieldRechargeRateModifier"), rewardQuality, false);
        space_crafting.setShieldGeneratorCurrentFrontHitpoints(shield, 0.0f);
        space_crafting.setShieldGeneratorCurrentBackHitpoints(shield, 0.0f);
        space_crafting.setShieldGeneratorMaximumFrontHitpoints(shield, frontHitpoints);
        space_crafting.setShieldGeneratorMaximumBackHitpoints(shield, backHitpoints);
        space_crafting.setShieldGeneratorRechargeRate(shield, rechargeRate);

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot shield created. Player=" + player + " shield=" + shield + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier + " frontHitpoints=" + frontHitpoints + " backHitpoints=" + backHitpoints + " rechargeRate=" + rechargeRate);
        return shield;
    }

    public obj_id createCapacitorReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id capacitor = createComponentFromTable(player, itemName, CAPACITOR_TABLE, rewardQuality, rewardTier);
        if (!isIdValid(capacitor))
        {
            return null;
        }
        dictionary capacitorData = dataTableGetRow(CAPACITOR_TABLE, itemName);
        float maxEnergy = getDirectedValue(capacitorData.getFloat("fltMaxEnergy"), capacitorData.getFloat("fltMaxEnergyModifier"), rewardQuality, false);
        float rechargeRate = getDirectedValue(capacitorData.getFloat("fltRechargeRate"), capacitorData.getFloat("fltRechargeRateModifier"), rewardQuality, false);
        space_crafting.setWeaponCapacitorMaximumEnergy(capacitor, maxEnergy);
        space_crafting.setWeaponCapacitorCurrentEnergy(capacitor, maxEnergy);
        space_crafting.setWeaponCapacitorRechargeRate(capacitor, rechargeRate);

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot capacitor created. Player=" + player + " capacitor=" + capacitor + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier + " maxEnergy=" + maxEnergy + " rechargeRate=" + rechargeRate);
        return capacitor;
    }

    public obj_id createArmorReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id armor = createComponentFromTable(player, itemName, ARMOR_TABLE, rewardQuality, rewardTier);
        if (isIdValid(armor))
        {
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot armor created. Player=" + player + " armor=" + armor + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier);
        }
        return armor;
    }

    public obj_id createDroidInterfaceReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        if (!isIdValid(inventory) || getVolumeFree(inventory) <= 0)
        {
            return null;
        }
        obj_id droidInterface = create.object(itemName, inventory, false, false);
        if (!isIdValid(droidInterface))
        {
            return null;
        }
        dictionary droidData = dataTableGetRow(DROID_INTERFACE_TABLE, itemName);
        if (droidData == null)
        {
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot droid interface creation failed, missing droid interface data. Player=" + player + " droidInterface=" + droidInterface + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
            destroyObject(droidInterface);
            return null;
        }

        String rewardQualityName = space_rare_loot.getRewardQualityName(rewardQuality);
        int overrideRow = getDroidInterfaceOverrideRow(itemName, rewardQualityName);
        float commandSpeedModifier = getDroidInterfaceModifier(overrideRow, OVERRIDE_COLUMN_COMMAND_SPEED_MODIFIER, droidData.getFloat("fltCommandSpeedModifier"));
        float hitpointsModifier = getDroidInterfaceModifier(overrideRow, OVERRIDE_COLUMN_HITPOINTS_MODIFIER, droidData.getFloat("fltMaximumArmorHitpointsMod"));
        float armorHitpointsModifier = getDroidInterfaceModifier(overrideRow, OVERRIDE_COLUMN_ARMOR_HITPOINTS_MODIFIER, droidData.getFloat("fltMaximumArmorHitpointsMod"));
        float energyMaintenanceModifier = getDroidInterfaceModifier(overrideRow, OVERRIDE_COLUMN_ENERGY_MAINTENANCE_MODIFIER, droidData.getFloat("fltEnergyMaintenanceModifier"));
        float massModifier = getDroidInterfaceModifier(overrideRow, OVERRIDE_COLUMN_MASS_MODIFIER, droidData.getFloat("fltMassModifier"));

        float hitpoints = getDirectedValue(droidData.getFloat("fltMaximumHitpoints"), hitpointsModifier, rewardQuality, false);
        float armorHitpoints = getDirectedValue(droidData.getFloat("fltMaximumArmorHitpoints"), armorHitpointsModifier, rewardQuality, false);
        float energyMaintenance = getDirectedValue(droidData.getFloat("fltEnergyMaintenance"), energyMaintenanceModifier, rewardQuality, true);
        float mass = getDirectedValue(droidData.getFloat("fltMass"), massModifier, rewardQuality, true);
        float commandSpeed = getDirectedValue(droidData.getFloat("fltCommandSpeed"), commandSpeedModifier, rewardQuality, true);
        float efficiency = droidData.getFloat("fltEfficiency");

        space_crafting.setComponentCurrentArmorHitpoints(droidInterface, armorHitpoints);
        space_crafting.setComponentMaximumArmorHitpoints(droidInterface, armorHitpoints);
        space_crafting.setComponentCurrentConduitHitpoints(droidInterface, hitpoints);
        space_crafting.setComponentMaximumConduitHitpoints(droidInterface, hitpoints);
        space_crafting.setComponentCurrentHitpoints(droidInterface, hitpoints);
        space_crafting.setComponentMaximumHitpoints(droidInterface, hitpoints);
        space_crafting.setComponentMass(droidInterface, mass);
        space_crafting.setComponentEnergyMaintenance(droidInterface, energyMaintenance);
        space_crafting.setComponentEnergyEfficiency(droidInterface, efficiency);
        space_crafting.setComponentGeneralEfficiency(droidInterface, efficiency);
        space_crafting.setDroidInterfaceCommandSpeed(droidInterface, commandSpeed);

        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot droid interface created. Player=" + player + " droidInterface=" + droidInterface + " itemName=" + itemName + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardQualityModifier=" + rewardQualityModifier + " overrideRow=" + overrideRow + " commandSpeed=" + commandSpeed + " hitpoints=" + hitpoints + " armorHitpoints=" + armorHitpoints + " energyMaintenance=" + energyMaintenance + " mass=" + mass);
        return droidInterface;
    }

    public int getDroidInterfaceOverrideRow(String itemName, String rewardQuality) throws InterruptedException
    {
        int rowCount = dataTableGetNumRows(DROID_INTERFACE_OVERRIDE_TABLE);
        for (int row = 0; row < rowCount; row++)
        {
            if (dataTableGetInt(DROID_INTERFACE_OVERRIDE_TABLE, row, space_rare_loot.REWARD_TABLE_COLUMN_ENABLED) == 0)
            {
                continue;
            }
            String overrideItemName = dataTableGetString(DROID_INTERFACE_OVERRIDE_TABLE, row, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_NAME);
            if (overrideItemName == null || !overrideItemName.equals(itemName))
            {
                continue;
            }
            String overrideQuality = dataTableGetString(DROID_INTERFACE_OVERRIDE_TABLE, row, space_rare_loot.REWARD_TABLE_COLUMN_QUALITY);
            if (space_rare_loot.isRewardQualityMatch(overrideQuality, rewardQuality))
            {
                return row;
            }
        }
        return -1;
    }

    public float getDroidInterfaceModifier(int overrideRow, String modifierColumn, float defaultModifier) throws InterruptedException
    {
        if (overrideRow < 0)
        {
            return defaultModifier;
        }
        return dataTableGetFloat(DROID_INTERFACE_OVERRIDE_TABLE, overrideRow, modifierColumn);
    }

    public obj_id createComponentFromTable(obj_id player, String itemName, String componentTable, int rewardQuality, int rewardTier) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        if (!isIdValid(inventory) || getVolumeFree(inventory) <= 0)
        {
            return null;
        }
        obj_id component = create.object(itemName, inventory, false, false);
        if (!isIdValid(component))
        {
            return null;
        }
        dictionary componentData = dataTableGetRow(componentTable, itemName);
        if (componentData == null)
        {
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot component creation failed, missing component data. Player=" + player + " component=" + component + " itemName=" + itemName + " componentTable=" + componentTable + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
            destroyObject(component);
            return null;
        }
        applyStandardComponentStats(component, componentData, rewardQuality);
        return component;
    }
}
