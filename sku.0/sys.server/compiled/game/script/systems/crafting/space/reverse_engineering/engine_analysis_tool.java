package script.systems.crafting.space.reverse_engineering;

import script.library.create;
import script.library.space_crafting;
import script.library.utils;
import script.library.xp;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class engine_analysis_tool extends script.base_script
{
    public engine_analysis_tool()
    {
    }
    public static final string_id LOOT_LIST = new string_id("sui", "analyze_loot");
    public static final String TOOL = "reverse_engineering_tool";
    public static final String LOOT = "datatables/space_loot/reverse_engineering/reverse_loot.iff";
    public static final String LOOT_LOOKUP = "datatables/space_loot/reverse_engineering/reverse_loot_lookup.iff";
    public static final String TABLE = "datatables/ship/components/engine.iff";
    public static final String SCRIPTVAR_ANALYZE_SUI = "analyzer.sui";
    public static final String SCRIPTVAR_ANALYZE_IDS = "analyzer.ids";
    public static final String STF = "component_analyzer";
    public static final String ANALYZE_TITLE = "@" + STF + ":analyze_title";
    public static final String ANALYZE_PROMPT = "@" + STF + ":analyze_prompt";
    public static final String NO_ITEMS_PROMPT = "@" + STF + ":no_items";
    public static final String BTN_ANALYZE = "@" + STF + ":analyze";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "ctsBeingUnpacked"))
        {
            return SCRIPT_CONTINUE;
        }
        if (space_crafting.getShipComponentStringType(item) != "engine")
        {
            string_id errormessage2 = new string_id(TOOL, "wrong_component_type");
            sendSystemMessage(transferer, errormessage2);
            return SCRIPT_OVERRIDE;
        }
        if (getBooleanObjVar(item, "cannotReverseEngineer") == true)
        {
            string_id errormessage = new string_id(TOOL, "already_engineered");
            sendSystemMessage(transferer, errormessage);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "reverse_engineering.charges"))
        {
            int value = getIntObjVar(self, "reverse_engineering.charges");
            names[idx] = "charges";
            attribs[idx] = Integer.toString(value);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int lootList = mi.addRootMenu(menu_info_types.SERVER_MENU1, LOOT_LIST);
        if (lootList > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
        {
            String template = utils.getTemplateFilenameNoPath(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            int charges = getIntObjVar(self, "reverse_engineering.charges");
            obj_id[] x = getContents(self);
            int countX = 0;
            String temp = "";
            if (x[0] != null)
            {
                temp = getTemplateName(x[0]);
            }
            for (obj_id x2 : x) {
                if (space_crafting.getShipComponentStringType(x2) != "engine") {
                    string_id errormessage2 = new string_id(TOOL, "wrong_component_type");
                    sendSystemMessage(player, errormessage2);
                    return SCRIPT_CONTINUE;
                }
                if ((getTemplateName(x2)).equals(temp)) {
                    countX++;
                }
                if (getBooleanObjVar(x2, "cannotReverseEngineer") == true) {
                    string_id errormessage = new string_id(TOOL, "already_engineered");
                    sendSystemMessage(player, errormessage);
                    return SCRIPT_CONTINUE;
                }
            }
            int level = space_crafting.getReverseEngineeringLevel(x[0]);
            if (level == countX)
            {
                int revSkill = getSkillStatisticModifier(player, "reverse_engineering");
                debugSpeakMsg(player, "Skill = " + revSkill);
                if (revSkill < level)
                {
                    string_id sysmessage = new string_id(TOOL, "not_skilled");
                    sendSystemMessage(player, sysmessage);
                    return SCRIPT_CONTINUE;
                }
                float pct = dataTableGetFloat(LOOT, temp, 1);
                int intPct = (int)(pct * 10);
                int pctCheck = rand(0, 1000);
                if (intPct >= pctCheck)
                {
                    String category1 = dataTableGetString(LOOT, temp, 2);
                    createLegendaryLoot(player, self, category1);
                    return SCRIPT_CONTINUE;
                }
                int roll = rand(0, 100);
                if (roll < revSkill)
                {
                    createSpecialLoot(player, temp);
                    string_id sysmessage = new string_id(TOOL, "special");
                    sendSystemMessage(player, sysmessage);
                }
                else 
                {
                    createImprovedLoot(player, temp);
                    string_id sysmessage = new string_id(TOOL, "improved");
                    sendSystemMessage(player, sysmessage);
                }
                int xpGranted = 0;
                if (level == 1)
                {
                    xpGranted = level * 20;
                }
                else if (level == 2 || level == 3)
                {
                    xpGranted = level * 25;
                }
                else if (level == 4 || level == 5)
                {
                    xpGranted = level * 30;
                }
                else if (level == 6 || level == 7)
                {
                    xpGranted = level * 35;
                }
                else if (level == 8 || level == 9)
                {
                    xpGranted = level * 40;
                }
                else if (level == 10)
                {
                    xpGranted = level * 45;
                }
                xp.grant(player, "reverse_engineering", xpGranted);
                charges--;
                setObjVar(self, "reverse_engineering.charges", charges);
                if (charges > 0)
                {
                    for (obj_id x1 : x) {
                        destroyObject(x1);
                    }
                }
                if (charges <= 0)
                {
                    destroyObject(self);
                }
            }
            if (level < countX)
            {
                string_id errortoomany = new string_id(TOOL, "too_many");
                sendSystemMessage(player, errortoomany);
                return SCRIPT_CONTINUE;
            }
            if (level > countX)
            {
                string_id errortoofew = new string_id(TOOL, "too_few");
                sendSystemMessage(player, errortoofew);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id createLegendaryLoot(obj_id player, obj_id tool, String category) throws InterruptedException
    {
        String[] items = dataTableGetStringColumn(LOOT_LOOKUP, category);
        int pick = rand(0, items.length - 1);
        String rare = dataTableGetString(LOOT_LOOKUP, pick, category);
        String msg = dataTableGetString(LOOT_LOOKUP, pick, (category + "_strings"));
        obj_id newLoot = createSpecialLoot(player, rare);
        string_id sysmessage = new string_id(TOOL, msg);
        sendSystemMessage(player, sysmessage);
        destroyObject(tool);
        return newLoot;
    }
    public obj_id createImprovedLoot(obj_id player, String template) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newWeapon = create.object(template, playerInv, false, false);
        float energyMaintenance = dataTableGetFloat(TABLE, template, "fltEnergyMaintenance");
        float energyMaintenanceModifier = dataTableGetFloat(TABLE, template, "fltEnergyMaintenanceModifier");
        energyMaintenance = energyMaintenance - energyMaintenanceModifier;
        float mass = dataTableGetFloat(TABLE, template, "fltMass");
        float massModifier = dataTableGetFloat(TABLE, template, "fltMassModifier");
        mass = mass - massModifier;
        space_crafting.setComponentMass(newWeapon, mass);
        space_crafting.setComponentEnergyMaintenance(newWeapon, energyMaintenance);
        float armorHp = dataTableGetFloat(TABLE, template, "fltMaximumArmorHitpoints");
        float armorHpMod = dataTableGetFloat(TABLE, template, "fltMaximumArmorHitpointsModifier");
        armorHp = armorHp + armorHpMod;
        space_crafting.setComponentCurrentArmorHitpoints(newWeapon, armorHp);
        space_crafting.setComponentMaximumArmorHitpoints(newWeapon, armorHp);
        float fltAccelerationModifier = dataTableGetFloat(TABLE, template, "fltAccelerationModifier");
        float fltAcceleration = dataTableGetFloat(TABLE, template, "fltAcceleration");
        fltAcceleration = fltAcceleration + fltAccelerationModifier;
        space_crafting.setEngineAcceleration(newWeapon, fltAcceleration);
        float fltDecelerationModifier = dataTableGetFloat(TABLE, template, "fltDecelerationModifier");
        float fltDeceleration = dataTableGetFloat(TABLE, template, "fltDeceleration");
        fltDeceleration = fltDeceleration + fltDecelerationModifier;
        space_crafting.setEngineDeceleration(newWeapon, fltDeceleration);
        float fltPitchModifier = dataTableGetFloat(TABLE, template, "fltPitchModifier");
        float fltPitch = dataTableGetFloat(TABLE, template, "fltPitch");
        fltPitch = fltPitch + fltPitchModifier;
        space_crafting.setEnginePitch(newWeapon, fltPitch);
        float fltYawModifier = dataTableGetFloat(TABLE, template, "fltYawModifier");
        float fltYaw = dataTableGetFloat(TABLE, template, "fltYaw");
        fltYaw = fltYaw + fltYawModifier;
        space_crafting.setEngineYaw(newWeapon, fltYaw);
        float fltRollModifier = dataTableGetFloat(TABLE, template, "fltRollModifier");
        float fltRoll = dataTableGetFloat(TABLE, template, "fltRoll");
        fltRoll = fltRoll + fltRollModifier;
        space_crafting.setEngineRoll(newWeapon, fltRoll);
        float fltMaxSpeedModifier = dataTableGetFloat(TABLE, template, "fltMaxSpeedModifier");
        float fltMaxSpeed = dataTableGetFloat(TABLE, template, "fltMaxSpeed");
        fltMaxSpeed = fltMaxSpeed + fltMaxSpeedModifier;
        space_crafting.setEngineMaximumSpeed(newWeapon, fltMaxSpeed);
        float fltMaxPitchModifier = dataTableGetFloat(TABLE, template, "fltMaxPitchModifier");
        float fltMaxPitch = dataTableGetFloat(TABLE, template, "fltMaxPitch");
        fltMaxPitch = fltMaxPitch + fltMaxPitchModifier;
        space_crafting.setEngineMaximumPitch(newWeapon, fltMaxPitch);
        float fltMaxRollModifier = dataTableGetFloat(TABLE, template, "fltMaxRollModifier");
        float fltMaxRoll = dataTableGetFloat(TABLE, template, "fltMaxRoll");
        fltMaxRoll = fltMaxRoll + fltMaxRollModifier;
        space_crafting.setEngineMaximumRoll(newWeapon, fltMaxRoll);
        float fltMaxYawModifier = dataTableGetFloat(TABLE, template, "fltMaxYawModifier");
        float fltMaxYaw = dataTableGetFloat(TABLE, template, "fltMaxYaw");
        fltMaxYaw = fltMaxYaw + fltMaxYawModifier;
        space_crafting.setEngineMaximumYaw(newWeapon, fltMaxYaw);
        setObjVar(newWeapon, "cannotReverseEngineer", true);
        return newWeapon;
    }
    public obj_id createSpecialLoot(obj_id player, String template) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newWeapon = create.object(template, playerInv, false, false);
        float energyMaintenance = dataTableGetFloat(TABLE, template, "fltEnergyMaintenance");
        float energyMaintenanceModifier = dataTableGetFloat(TABLE, template, "fltEnergyMaintenanceModifier");
        energyMaintenance = energyMaintenance - energyMaintenanceModifier;
        energyMaintenance = energyMaintenance - ((0.05f) * energyMaintenance);
        float mass = dataTableGetFloat(TABLE, template, "fltMass");
        float massModifier = dataTableGetFloat(TABLE, template, "fltMassModifier");
        mass = mass - massModifier;
        mass = mass - ((0.05f) * mass);
        space_crafting.setComponentMass(newWeapon, mass);
        space_crafting.setComponentEnergyMaintenance(newWeapon, energyMaintenance);
        float armorHp = dataTableGetFloat(TABLE, template, "fltMaximumArmorHitpoints");
        float armorHpMod = dataTableGetFloat(TABLE, template, "fltMaximumArmorHitpointsModifier");
        armorHp = armorHp + armorHpMod;
        space_crafting.setComponentCurrentArmorHitpoints(newWeapon, armorHp);
        space_crafting.setComponentMaximumArmorHitpoints(newWeapon, armorHp);
        float fltAccelerationModifier = dataTableGetFloat(TABLE, template, "fltAccelerationModifier");
        float fltAcceleration = dataTableGetFloat(TABLE, template, "fltAcceleration");
        fltAcceleration = fltAcceleration + fltAccelerationModifier;
        fltAcceleration = fltAcceleration + ((0.05f) * fltAcceleration);
        space_crafting.setEngineAcceleration(newWeapon, fltAcceleration);
        float fltDecelerationModifier = dataTableGetFloat(TABLE, template, "fltDecelerationModifier");
        float fltDeceleration = dataTableGetFloat(TABLE, template, "fltDeceleration");
        fltDeceleration = fltDeceleration + fltDecelerationModifier;
        fltDeceleration = fltDeceleration + ((0.05f) * fltDeceleration);
        space_crafting.setEngineDeceleration(newWeapon, fltDeceleration);
        float fltPitchModifier = dataTableGetFloat(TABLE, template, "fltPitchModifier");
        float fltPitch = dataTableGetFloat(TABLE, template, "fltPitch");
        fltPitch = fltPitch + fltPitchModifier;
        fltPitch = fltPitch + ((0.05f) * fltPitch);
        space_crafting.setEnginePitch(newWeapon, fltPitch);
        float fltYawModifier = dataTableGetFloat(TABLE, template, "fltYawModifier");
        float fltYaw = dataTableGetFloat(TABLE, template, "fltYaw");
        fltYaw = fltYaw + fltYawModifier;
        fltYaw = fltYaw + ((0.05f) * fltYaw);
        space_crafting.setEngineYaw(newWeapon, fltYaw);
        float fltRollModifier = dataTableGetFloat(TABLE, template, "fltRollModifier");
        float fltRoll = dataTableGetFloat(TABLE, template, "fltRoll");
        fltRoll = fltRoll + fltRollModifier;
        fltRoll = fltRoll + ((0.05f) * fltRoll);
        space_crafting.setEngineRoll(newWeapon, fltRoll);
        float fltMaxSpeedModifier = dataTableGetFloat(TABLE, template, "fltMaxSpeedModifier");
        float fltMaxSpeed = dataTableGetFloat(TABLE, template, "fltMaxSpeed");
        fltMaxSpeed = fltMaxSpeed + fltMaxSpeedModifier;
        fltMaxSpeed = fltMaxSpeed + ((0.05f) * fltMaxSpeed);
        space_crafting.setEngineMaximumSpeed(newWeapon, fltMaxSpeed);
        float fltMaxPitchModifier = dataTableGetFloat(TABLE, template, "fltMaxPitchModifier");
        float fltMaxPitch = dataTableGetFloat(TABLE, template, "fltMaxPitch");
        fltMaxPitch = fltMaxPitch + fltMaxPitchModifier;
        fltMaxPitch = fltMaxPitch + ((0.05f) * fltMaxSpeed);
        space_crafting.setEngineMaximumPitch(newWeapon, fltMaxPitch);
        float fltMaxRollModifier = dataTableGetFloat(TABLE, template, "fltMaxRollModifier");
        float fltMaxRoll = dataTableGetFloat(TABLE, template, "fltMaxRoll");
        fltMaxRoll = fltMaxRoll + fltMaxRollModifier;
        fltMaxRoll = fltMaxRoll + ((0.05f) * fltMaxRoll);
        space_crafting.setEngineMaximumRoll(newWeapon, fltMaxRoll);
        float fltMaxYawModifier = dataTableGetFloat(TABLE, template, "fltMaxYawModifier");
        float fltMaxYaw = dataTableGetFloat(TABLE, template, "fltMaxYaw");
        fltMaxYaw = fltMaxYaw + fltMaxYawModifier;
        fltMaxYaw = fltMaxYaw + ((0.05f) * fltMaxYaw);
        space_crafting.setEngineMaximumYaw(newWeapon, fltMaxYaw);
        setObjVar(newWeapon, "cannotReverseEngineer", true);
        return newWeapon;
    }
}
