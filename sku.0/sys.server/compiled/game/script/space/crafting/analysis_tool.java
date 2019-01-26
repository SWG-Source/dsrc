package script.space.crafting;

import script.library.create;
import script.library.space_crafting;
import script.library.sui;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class analysis_tool extends script.base_script
{
    public analysis_tool()
    {
    }
    public static final string_id LOOT_LIST = new string_id("sui", "analyze_loot");
    public static final String LOOT = "datatables/space_loot/reverse_engineering/reverse_loot.iff";
    public static final String LOOT_LOOKUP = "datatables/space_loot/reverse_engineering/reverse_loot_lookup.iff";
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
        if (!hasObjVar(item, "firespray_schematic.part"))
        {
            if (space_crafting.isValidShipComponent(item) == false)
            {
                string_id errormessage2 = new string_id(space_crafting.STF_COMPONENT_TOOL, "wrong_component_type");
                sendSystemMessage(transferer, errormessage2);
                return SCRIPT_OVERRIDE;
            }
        }
        if (hasObjVar(item, "ship_comp.flags"))
        {
            int flags = getIntObjVar(item, "ship_comp.flags");
            boolean isBitSet = (flags & ship_component_flags.SCF_reverse_engineered) != 0;
            if (isBitSet == true)
            {
                string_id errormessage = new string_id(space_crafting.STF_COMPONENT_TOOL, "already_engineered");
                sendSystemMessage(transferer, errormessage);
                return SCRIPT_OVERRIDE;
            }
        }
        obj_id[] toolContents = getContents(self);
        if (toolContents != null && toolContents.length > 0)
        {
            if (isIdValid(toolContents[0]))
            {
                if (hasObjVar(toolContents[0], "firespray_schematic.part"))
                {
                    if (hasObjVar(item, "firespray_schematic.part"))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        string_id errormessage = new string_id(space_crafting.STF_COMPONENT_TOOL, "invalid");
                        sendSystemMessage(transferer, errormessage);
                        return SCRIPT_OVERRIDE;
                    }
                }
                String componentType = space_crafting.getShipComponentStringType(toolContents[0]);
                int level = space_crafting.getReverseEngineeringLevel(toolContents[0]);
                if (hasObjVar(item, "firespray_schematic.part") || space_crafting.getShipComponentStringType(item) != componentType || space_crafting.getReverseEngineeringLevel(item) != level)
                {
                    string_id errormessage = new string_id(space_crafting.STF_COMPONENT_TOOL, "invalid");
                    sendSystemMessage(transferer, errormessage);
                    return SCRIPT_OVERRIDE;
                }
            }
            return SCRIPT_CONTINUE;
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
        boolean giveFiresprayPiece = false;
        obj_id newComponent = null;
        if (item == menu_info_types.SERVER_MENU1)
        {
            int charges = getIntObjVar(self, "reverse_engineering.charges");
            obj_id[] toolContents = getContents(self);
            obj_id playerInv = utils.getInventoryContainer(player);
            String temp = "";
            if (toolContents == null || toolContents.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (toolContents[0] != null)
                {
                    int level = space_crafting.getReverseEngineeringLevel(toolContents[0]);
                    if (hasObjVar(toolContents[0], "firespray_schematic.part"))
                    {
                        level = 8;
                    }
                    if (level > toolContents.length)
                    {
                        string_id errortoomany = new string_id(space_crafting.STF_COMPONENT_TOOL, "too_few");
                        sendSystemMessage(player, errortoomany);
                        return SCRIPT_OVERRIDE;
                    }
                    if (level < toolContents.length)
                    {
                        string_id errortoomany = new string_id(space_crafting.STF_COMPONENT_TOOL, "too_many");
                        sendSystemMessage(player, errortoomany);
                        return SCRIPT_OVERRIDE;
                    }
                    String componentType = space_crafting.getShipComponentStringType(toolContents[0]);
                    giveFiresprayPiece = calculateFiresprayGrant(toolContents, player);
                    if (hasObjVar(toolContents[0], "firespray_schematic.part"))
                    {
                        componentType = "firespray_schematic";
                    }
                    switch (componentType) {
                        case "armor":
                            newComponent = reverseEngineerArmor(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "booster":
                            newComponent = reverseEngineerBooster(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "capacitor":
                            newComponent = reverseEngineerCapacitor(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "droid_interface":
                            newComponent = reverseEngineerDroidInterface(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "engine":
                            newComponent = reverseEngineerEngine(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "reactor":
                            newComponent = reverseEngineerReactor(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "shield":
                            newComponent = reverseEngineerShield(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "weapon":
                            newComponent = reverseEngineerWeapon(toolContents, player, level);
                            if (newComponent != null) {
                                charges--;
                                setObjVar(self, "reverse_engineering.charges", charges);
                                int flags = getIntObjVar(newComponent, "ship_comp.flags");
                                flags |= ship_component_flags.SCF_reverse_engineered;
                                setObjVar(newComponent, "ship_comp.flags", flags);
                            }
                            break;
                        case "firespray_schematic":
                            if (toolContents.length != 8) {
                                string_id notEnough = new string_id(space_crafting.STF_COMPONENT_TOOL, "not_enough");
                                sendSystemMessage(player, notEnough);
                                return SCRIPT_CONTINUE;
                            }
                            boolean[] allPieces = new boolean[8];
                            allPieces[0] = false;
                            allPieces[1] = false;
                            allPieces[2] = false;
                            allPieces[3] = false;
                            allPieces[4] = false;
                            allPieces[5] = false;
                            allPieces[6] = false;
                            allPieces[7] = false;
                            for (obj_id toolContent1 : toolContents) {
                                int piece = utils.getIntObjVar(toolContent1, "firespray_schematic.part");
                                allPieces[piece - 1] = true;
                            }
                            if (allPieces[0] == true && allPieces[1] == true && allPieces[2] == true && allPieces[3] == true && allPieces[4] == true && allPieces[5] == true && allPieces[6] == true && allPieces[7] == true) {
                                obj_id schematic = create.object("object/tangible/space/special_loot/firespray_schematic.iff", playerInv, false, false);
                                string_id gotSchematic = new string_id(space_crafting.STF_COMPONENT_TOOL, "gotschematic");
                                sendSystemMessage(player, gotSchematic);
                                for (obj_id toolContent : toolContents) {
                                    destroyObject(toolContent);
                                }
                                return SCRIPT_CONTINUE;
                            } else {
                                string_id notUniquePieces = new string_id(space_crafting.STF_COMPONENT_TOOL, "notunique");
                                sendSystemMessage(player, notUniquePieces);
                                return SCRIPT_CONTINUE;
                            }
                        default:
                            string_id sysmessage = new string_id(space_crafting.STF_COMPONENT_TOOL, "not_a_component");
                            sendSystemMessage(player, sysmessage);
                            return SCRIPT_OVERRIDE;
                    }
                }
            }
            if (giveFiresprayPiece == true)
            {
                if (isIdValid(newComponent))
                {
                    int pieceNum = rand(1, 8);
                    obj_id schematicPiece = createObjectOverloaded("object/tangible/space/special_loot/firespray_schematic_part" + pieceNum + ".iff", playerInv);
                    string_id gotPiece = new string_id(space_crafting.STF_COMPONENT_TOOL, "gotpiece");
                    sendSystemMessage(player, gotPiece);
                }
            }
            if (charges <= 0)
            {
                string_id outofcharges = new string_id(space_crafting.STF_COMPONENT_TOOL, "out_of_charges");
                sendSystemMessage(player, outofcharges);
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean calculateFiresprayGrant(obj_id[] contents, obj_id player) throws InterruptedException
    {
        int numKuat = 0;
        for (obj_id content : contents) {
            String template = utils.getTemplateName(content);
            int isKuat = dataTableGetInt(LOOT, template, "isKuat");
            if (isKuat == 1) {
                numKuat++;
            }
        }
        int pctChance = 0;
        if (numKuat == 1 || numKuat == 2)
        {
            pctChance = 1;
        }
        if (numKuat == 3 || numKuat == 4)
        {
            pctChance = 2;
        }
        if (numKuat == 5 || numKuat == 6)
        {
            pctChance = 3;
        }
        if (numKuat == 7 || numKuat == 8 || numKuat == 9)
        {
            pctChance = 4;
        }
        if (numKuat == 10)
        {
            pctChance = 5;
        }
        int roll = rand(0, 100);
        if (roll <= pctChance)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public float getReverseEngineeringExpertiseBonus(obj_id player) throws InterruptedException
    {
        float skillMod = getEnhancedSkillStatisticModifier(player, "expertise_reverse_engineering_bonus");
        if (skillMod > 10)
        {
            skillMod = 10.0f;
        }
        return skillMod;
    }
    public float getLevelBonus(obj_id player, int level) throws InterruptedException
    {
        float fltBonus = 0;
        if (level == 1)
        {
            fltBonus = 0.01f;
        }
        else if (level == 2 || level == 3)
        {
            fltBonus = 0.02f;
        }
        else if (level == 4 || level == 5)
        {
            fltBonus = 0.03f;
        }
        else if (level == 6 || level == 7)
        {
            fltBonus = 0.04f;
        }
        else if (level == 8 || level == 9)
        {
            fltBonus = 0.05f;
        }
        else if (level == 10)
        {
            fltBonus = 0.06f;
        }
        fltBonus += Math.round(getReverseEngineeringExpertiseBonus(player)) * 0.001;
        return fltBonus;
    }
    public obj_id createLegendaryLoot(obj_id player, obj_id tool, String category) throws InterruptedException
    {
        String[] items = dataTableGetStringColumn(LOOT_LOOKUP, category);
        int pick = rand(0, items.length - 1);
        String rare = dataTableGetString(LOOT_LOOKUP, pick, category);
        String msg = dataTableGetString(LOOT_LOOKUP, pick, (category + "_strings"));
        string_id sysmessage = new string_id(space_crafting.STF_COMPONENT_TOOL, msg);
        sendSystemMessage(player, sysmessage);
        destroyObject(tool);
        return null;
    }
    public obj_id reverseEngineerArmor(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "engineering_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage && mass > 0) {
                fltMassAverage = mass;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/armor/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            String entries[] = new String[3];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerBooster(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "propulsion_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltBoosterEnergyAverage = space_crafting.getBoosterMaximumEnergy(objComponents[0]);
        float fltBoosterRechargeAverage = space_crafting.getBoosterEnergyRechargeRate(objComponents[0]);
        float fltBoosterConsumptionAverage = space_crafting.getboosterEnergyConsumptionRate(objComponents[0]);
        float fltBoosterAccelAverage = space_crafting.getBoosterAcceleration(objComponents[0]);
        float fltBoosterSpeedAverage = space_crafting.getBoosterMaximumSpeed(objComponents[0]);
        float fltEnergyMaintenance = space_crafting.getComponentEnergyMaintenance(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float energy = space_crafting.getBoosterMaximumEnergy(objComponent);
            if (energy > fltBoosterEnergyAverage) {
                fltBoosterEnergyAverage = energy;
            }
            float recharge = space_crafting.getBoosterEnergyRechargeRate(objComponent);
            if (recharge > fltBoosterRechargeAverage) {
                fltBoosterRechargeAverage = recharge;
            }
            float consumption = space_crafting.getboosterEnergyConsumptionRate(objComponent);
            if (consumption < fltBoosterConsumptionAverage) {
                fltBoosterConsumptionAverage = consumption;
            }
            float accel = space_crafting.getBoosterAcceleration(objComponent);
            if (accel > fltBoosterAccelAverage) {
                fltBoosterAccelAverage = accel;
            }
            float speed = space_crafting.getBoosterMaximumSpeed(objComponent);
            if (speed > fltBoosterSpeedAverage) {
                fltBoosterSpeedAverage = speed;
            }
            float maintenance = space_crafting.getComponentEnergyMaintenance(objComponent);
            if (maintenance < fltEnergyMaintenance) {
                fltEnergyMaintenance = maintenance;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltBoosterEnergy = fltBoosterEnergyAverage + (fltBoosterEnergyAverage * fltBonus);
        float fltBoosterRecharge = fltBoosterRechargeAverage + (fltBoosterRechargeAverage * fltBonus);
        float fltBoosterConsumption = fltBoosterConsumptionAverage - (fltBoosterConsumptionAverage * fltBonus);
        float fltBoosterAccel = fltBoosterAccelAverage + (fltBoosterAccelAverage * fltBonus);
        float fltBoosterSpeed = fltBoosterSpeedAverage + (fltBoosterSpeedAverage * fltBonus);
        float fltEnergyMaintenanceFinal = fltEnergyMaintenance - (fltEnergyMaintenance * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/booster/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setComponentEnergyMaintenance(newComponent, fltEnergyMaintenanceFinal);
            space_crafting.setBoosterCurrentEnergy(newComponent, fltBoosterEnergy);
            space_crafting.setBoosterMaximumEnergy(newComponent, fltBoosterEnergy);
            space_crafting.setBoosterEnergyRechargeRate(newComponent, fltBoosterRecharge);
            space_crafting.setBoosterEnergyConsumptionRate(newComponent, fltBoosterConsumption);
            space_crafting.setBoosterAcceleration(newComponent, fltBoosterAccel);
            space_crafting.setBoosterMaximumSpeed(newComponent, fltBoosterSpeed);
            String entries[] = new String[9];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strEnergyMaintenance = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "energymaintenance"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strBoosterEnergy = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "boosterenergy"));
            String strBoosterRecharge = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "boosterrecharge"));
            String strBoosterConsumption = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "boosterconsumption"));
            String strBoosterAccel = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "boosteraccel"));
            String strBoosterSpeed = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "boosterspeed"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strEnergyMaintenance + " " + fltEnergyMaintenance + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnergyMaintenanceFinal;
            entries[4] = strBoosterEnergy + " " + fltBoosterEnergyAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltBoosterEnergy;
            entries[5] = strBoosterRecharge + " " + fltBoosterRechargeAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltBoosterRecharge;
            entries[6] = strBoosterConsumption + " " + fltBoosterConsumptionAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltBoosterConsumption;
            entries[7] = strBoosterAccel + " " + fltBoosterAccelAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltBoosterAccel;
            entries[8] = strBoosterSpeed + " " + fltBoosterSpeedAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltBoosterSpeed;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerCapacitor(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "systems_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltEnergyMaintenance = space_crafting.getComponentEnergyMaintenance(objComponents[0]);
        float fltCapEnergyAverage = space_crafting.getWeaponCapacitorMaximumEnergy(objComponents[0]);
        float fltCapRechargeAverage = space_crafting.getWeaponCapacitorRechargeRate(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float maintenance = space_crafting.getComponentEnergyMaintenance(objComponent);
            if (maintenance < fltEnergyMaintenance) {
                fltEnergyMaintenance = maintenance;
            }
            float energy = space_crafting.getWeaponCapacitorMaximumEnergy(objComponent);
            if (energy > fltCapEnergyAverage) {
                fltCapEnergyAverage = energy;
            }
            float recharge = space_crafting.getWeaponCapacitorRechargeRate(objComponent);
            if (recharge > fltCapRechargeAverage) {
                fltCapRechargeAverage = recharge;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltEnergyMaintenanceFinal = fltEnergyMaintenance - (fltEnergyMaintenance * fltBonus);
        float fltCapEnergy = fltCapEnergyAverage + (fltCapEnergyAverage * fltBonus);
        float fltCapRecharge = fltCapRechargeAverage + (fltCapRechargeAverage * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/weapon_capacitor/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setComponentEnergyMaintenance(newComponent, fltEnergyMaintenanceFinal);
            space_crafting.setWeaponCapacitorRechargeRate(newComponent, fltCapRecharge);
            space_crafting.setWeaponCapacitorMaximumEnergy(newComponent, fltCapEnergy);
            space_crafting.setWeaponCapacitorCurrentEnergy(newComponent, fltCapEnergy);
            String entries[] = new String[6];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strEnergyMaintenance = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "energymaintenance"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strCapEnergy = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "capenergy"));
            String strCapRecharge = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "caprecharge"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strEnergyMaintenance + " " + fltEnergyMaintenance + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnergyMaintenanceFinal;
            entries[4] = strCapEnergy + " " + fltCapEnergyAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltCapEnergy;
            entries[5] = strCapRecharge + " " + fltCapRechargeAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltCapRecharge;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerDroidInterface(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "systems_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltEnergyMaintenance = space_crafting.getComponentEnergyMaintenance(objComponents[0]);
        float fltDroidInterfaceSpeedAverage = space_crafting.getDroidInterfaceCommandSpeed(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float maintenance = space_crafting.getComponentEnergyMaintenance(objComponent);
            if (maintenance < fltEnergyMaintenance) {
                fltEnergyMaintenance = maintenance;
            }
            float speed = space_crafting.getDroidInterfaceCommandSpeed(objComponent);
            if (speed < fltDroidInterfaceSpeedAverage) {
                fltDroidInterfaceSpeedAverage = speed;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltEnergyMaintenanceFinal = fltEnergyMaintenance - (fltEnergyMaintenance * fltBonus);
        float fltDroidInterfaceSpeed = fltDroidInterfaceSpeedAverage - (fltDroidInterfaceSpeedAverage * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/droid_interface/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setComponentEnergyMaintenance(newComponent, fltEnergyMaintenanceFinal);
            space_crafting.setDroidInterfaceCommandSpeed(newComponent, fltDroidInterfaceSpeed);
            String entries[] = new String[5];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strEnergyMaintenance = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "energymaintenance"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strDroidInterfaceSpeed = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "commandspeed"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strEnergyMaintenance + " " + fltEnergyMaintenance + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnergyMaintenanceFinal;
            entries[4] = strDroidInterfaceSpeed + " " + fltDroidInterfaceSpeedAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltDroidInterfaceSpeed;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerEngine(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "propulsion_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltEnergyMaintenance = space_crafting.getComponentEnergyMaintenance(objComponents[0]);
        float fltEngineAccelAvg = space_crafting.getEngineAcceleration(objComponents[0]);
        float fltEngineDecelAvg = space_crafting.getEngineDeceleration(objComponents[0]);
        float fltEnginePitchAvg = space_crafting.getEngineMaximumPitch(objComponents[0]);
        float fltEngineYawAvg = space_crafting.getEngineMaximumYaw(objComponents[0]);
        float fltEngineRollAvg = space_crafting.getEngineMaximumRoll(objComponents[0]);
        float fltEngineSpeedAvg = space_crafting.getEngineMaximumSpeed(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float maintenance = space_crafting.getComponentEnergyMaintenance(objComponent);
            if (maintenance < fltEnergyMaintenance) {
                fltEnergyMaintenance = maintenance;
            }
            float accel = space_crafting.getEngineAcceleration(objComponent);
            if (accel > fltEngineAccelAvg) {
                fltEngineAccelAvg = accel;
            }
            float decel = space_crafting.getEngineDeceleration(objComponent);
            if (decel > fltEngineDecelAvg) {
                fltEngineDecelAvg = decel;
            }
            float pitch = space_crafting.getEngineMaximumPitch(objComponent);
            if (pitch > fltEnginePitchAvg) {
                fltEnginePitchAvg = pitch;
            }
            float yaw = space_crafting.getEngineMaximumYaw(objComponent);
            if (yaw > fltEngineYawAvg) {
                fltEngineYawAvg = yaw;
            }
            float roll = space_crafting.getEngineMaximumRoll(objComponent);
            if (roll > fltEngineRollAvg) {
                fltEngineRollAvg = roll;
            }
            float speed = space_crafting.getEngineMaximumSpeed(objComponent);
            if (speed > fltEngineSpeedAvg) {
                fltEngineSpeedAvg = speed;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltEngineAccel = fltEngineAccelAvg + (fltEngineAccelAvg * fltBonus);
        float fltEngineDecel = fltEngineDecelAvg + (fltEngineDecelAvg * fltBonus);
        float fltEnginePitch = fltEnginePitchAvg + (fltEnginePitchAvg * fltBonus);
        float fltEngineYaw = fltEngineYawAvg + (fltEngineYawAvg * fltBonus);
        float fltEngineRoll = fltEngineRollAvg + (fltEngineRollAvg * fltBonus);
        float fltEngineSpeed = fltEngineSpeedAvg + (fltEngineSpeedAvg * fltBonus);
        float fltEnergyMaintenanceFinal = fltEnergyMaintenance - (fltEnergyMaintenance * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/engine/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setComponentEnergyMaintenance(newComponent, fltEnergyMaintenanceFinal);
            space_crafting.setEngineAcceleration(newComponent, fltEngineAccel);
            space_crafting.setEngineDeceleration(newComponent, fltEngineDecel);
            space_crafting.setEngineMaximumPitch(newComponent, fltEnginePitch);
            space_crafting.setEngineMaximumYaw(newComponent, fltEngineYaw);
            space_crafting.setEngineMaximumRoll(newComponent, fltEngineRoll);
            space_crafting.setEngineMaximumSpeed(newComponent, fltEngineSpeed);
            String entries[] = new String[8];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strEnergyMaintenance = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "energymaintenance"));
            String strEnginePitch = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "engpitch"));
            String strEngineYaw = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "engyaw"));
            String strEngineRoll = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "engroll"));
            String strEngineSpeed = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "engspeed"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strEnergyMaintenance + " " + fltEnergyMaintenance + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnergyMaintenanceFinal;
            entries[4] = strEnginePitch + " " + fltEnginePitchAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnginePitch;
            entries[5] = strEngineYaw + " " + fltEngineYawAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEngineYaw;
            entries[6] = strEngineRoll + " " + fltEngineRollAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEngineRoll;
            entries[7] = strEngineSpeed + " " + fltEngineSpeedAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEngineSpeed;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerReactor(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "systems_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltReactorEnergyAvg = space_crafting.getReactorEnergyGeneration(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float energy = space_crafting.getReactorEnergyGeneration(objComponent);
            if (energy > fltReactorEnergyAvg) {
                fltReactorEnergyAvg = energy;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltReactorEnergy = fltReactorEnergyAvg + (fltReactorEnergyAvg * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/reactor/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setReactorEnergyGeneration(newComponent, fltReactorEnergy);
            String entries[] = new String[4];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strReactorEnergy = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "reactorenergy"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strReactorEnergy + " " + fltReactorEnergyAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltReactorEnergy;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerShield(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "defense_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltEnergyMaintenance = space_crafting.getComponentEnergyMaintenance(objComponents[0]);
        float fltShdBackHpAvg = space_crafting.getShieldGeneratorMaximumBackHitpoints(objComponents[0]);
        float fltShdFrontHpAvg = space_crafting.getShieldGeneratorMaximumFrontHitpoints(objComponents[0]);
        float fltShdRechargeAvg = space_crafting.getShieldGeneratorRechargeRate(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float maintenance = space_crafting.getComponentEnergyMaintenance(objComponent);
            if (maintenance < fltEnergyMaintenance) {
                fltEnergyMaintenance = maintenance;
            }
            float back = space_crafting.getShieldGeneratorMaximumBackHitpoints(objComponent);
            if (back > fltShdBackHpAvg) {
                fltShdBackHpAvg = back;
            }
            float front = space_crafting.getShieldGeneratorMaximumFrontHitpoints(objComponent);
            if (front > fltShdFrontHpAvg) {
                fltShdFrontHpAvg = front;
            }
            float recharge = space_crafting.getShieldGeneratorRechargeRate(objComponent);
            if (recharge > fltShdRechargeAvg) {
                fltShdRechargeAvg = recharge;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltEnergyMaintenanceFinal = fltEnergyMaintenance - (fltEnergyMaintenance * fltBonus);
        float fltShdBackHp = fltShdBackHpAvg + (fltShdBackHpAvg * fltBonus);
        float fltShdFrontHp = fltShdFrontHpAvg + (fltShdFrontHpAvg * fltBonus);
        float fltShdRecharge = fltShdRechargeAvg + (fltShdRechargeAvg * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/shield_generator/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setComponentEnergyMaintenance(newComponent, fltEnergyMaintenanceFinal);
            space_crafting.setShieldGeneratorMaximumFrontHitpoints(newComponent, fltShdFrontHp);
            space_crafting.setShieldGeneratorCurrentFrontHitpoints(newComponent, fltShdFrontHp);
            space_crafting.setShieldGeneratorMaximumBackHitpoints(newComponent, fltShdBackHp);
            space_crafting.setShieldGeneratorCurrentBackHitpoints(newComponent, fltShdBackHp);
            space_crafting.setShieldGeneratorRechargeRate(newComponent, fltShdRecharge);
            String entries[] = new String[7];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strEnergyMaintenance = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "energymaintenance"));
            String strShdBackHp = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "shdbackhp"));
            String strShdFrontHp = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "shdfronthp"));
            String strShdRecharge = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "shdrecharge"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strEnergyMaintenance + " " + fltEnergyMaintenance + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnergyMaintenanceFinal;
            entries[4] = strShdBackHp + " " + fltShdBackHpAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltShdBackHp;
            entries[5] = strShdFrontHp + " " + fltShdFrontHpAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltShdFrontHp;
            entries[6] = strShdRecharge + " " + fltShdRechargeAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltShdRecharge;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public obj_id reverseEngineerWeapon(obj_id[] objComponents, obj_id player, int level) throws InterruptedException
    {
        int revSkill = getSkillStatisticModifier(player, "defense_reverse");
        if (level > revSkill)
        {
            string_id insufficientLevel = new string_id(space_crafting.STF_COMPONENT_TOOL, "level");
            sendSystemMessage(player, insufficientLevel);
            return null;
        }
        float fltMassAverage = space_crafting.getComponentMass(objComponents[0]);
        float fltArmorHpAverage = space_crafting.getComponentMaximumArmorHitpoints(objComponents[0]);
        float fltHpAverage = space_crafting.getComponentMaximumHitpoints(objComponents[0]);
        float fltEnergyMaintenance = space_crafting.getComponentEnergyMaintenance(objComponents[0]);
        float fltWpnMaxDamageAvg = space_crafting.getWeaponMaximumDamage(objComponents[0]);
        float fltWpnMinDamageAvg = space_crafting.getWeaponMinimumDamage(objComponents[0]);
        float fltWpnShdEffectivenessAvg = space_crafting.getWeaponShieldEffectiveness(objComponents[0]);
        float fltWpnArmEffectivenessAvg = space_crafting.getWeaponArmorEffectiveness(objComponents[0]);
        float fltWpnEnergyPerShotAvg = space_crafting.getWeaponEnergyPerShot(objComponents[0]);
        float fltWpnRefireRateAvg = space_crafting.getWeaponRefireRate(objComponents[0]);
        for (obj_id objComponent : objComponents) {
            float armor = space_crafting.getComponentMaximumArmorHitpoints(objComponent);
            if (armor > fltArmorHpAverage) {
                fltArmorHpAverage = armor;
            }
            float hp = space_crafting.getComponentMaximumHitpoints(objComponent);
            if (hp > fltHpAverage) {
                fltHpAverage = hp;
            }
            float mass = space_crafting.getComponentMass(objComponent);
            if (mass < fltMassAverage) {
                fltMassAverage = mass;
            }
            float maintenance = space_crafting.getComponentEnergyMaintenance(objComponent);
            if (maintenance < fltEnergyMaintenance) {
                fltEnergyMaintenance = maintenance;
            }
            float max = space_crafting.getWeaponMaximumDamage(objComponent);
            if (max > fltWpnMaxDamageAvg) {
                fltWpnMaxDamageAvg = max;
            }
            float min = space_crafting.getWeaponMinimumDamage(objComponent);
            if (min > fltWpnMinDamageAvg) {
                fltWpnMinDamageAvg = min;
            }
            float shd = space_crafting.getWeaponShieldEffectiveness(objComponent);
            if (shd > fltWpnShdEffectivenessAvg) {
                fltWpnShdEffectivenessAvg = shd;
            }
            float arm = space_crafting.getWeaponArmorEffectiveness(objComponent);
            if (arm > fltWpnArmEffectivenessAvg) {
                fltWpnArmEffectivenessAvg = arm;
            }
            float energy = space_crafting.getWeaponEnergyPerShot(objComponent);
            if (energy < fltWpnEnergyPerShotAvg) {
                fltWpnEnergyPerShotAvg = energy;
            }
            float refire = space_crafting.getWeaponRefireRate(objComponent);
            if (refire < fltWpnRefireRateAvg) {
                fltWpnRefireRateAvg = refire;
            }
        }
        float fltBonus = getLevelBonus(player, level);
        float fltMass = fltMassAverage - (fltMassAverage * fltBonus);
        float fltArmorHp = fltArmorHpAverage + (fltArmorHpAverage * fltBonus);
        float fltHp = fltHpAverage + (fltHpAverage * fltBonus);
        float fltEnergyMaintenanceFinal = fltEnergyMaintenance - (fltEnergyMaintenance * fltBonus);
        float fltWpnMaxDamage = fltWpnMaxDamageAvg + (fltWpnMaxDamageAvg * fltBonus);
        float fltWpnMinDamage = fltWpnMinDamageAvg + (fltWpnMinDamageAvg * fltBonus);
        float fltWpnShdEffectiveness = fltWpnShdEffectivenessAvg + (fltWpnShdEffectivenessAvg * fltBonus);
        float fltWpnArmEffectiveness = fltWpnArmEffectivenessAvg + (fltWpnArmEffectivenessAvg * fltBonus);
        float fltWpnEnergyPerShot = fltWpnEnergyPerShotAvg - (fltWpnEnergyPerShotAvg * fltBonus);
        float fltWpnRefireRate = fltWpnRefireRateAvg - (fltWpnRefireRateAvg * fltBonus);
        String template = utils.getTemplateFilenameNoPath(objComponents[0]);
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id newComponent = create.object("object/tangible/ship/components/weapon/" + template, playerInv, false, false);
        if (isIdValid(newComponent))
        {
            space_crafting.setComponentMaximumArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentCurrentArmorHitpoints(newComponent, fltArmorHp);
            space_crafting.setComponentMaximumHitpoints(newComponent, fltHp);
            space_crafting.setComponentCurrentHitpoints(newComponent, fltHp);
            space_crafting.setComponentMass(newComponent, fltMass);
            space_crafting.setComponentEnergyMaintenance(newComponent, fltEnergyMaintenanceFinal);
            space_crafting.setWeaponMaximumDamage(newComponent, fltWpnMaxDamage);
            space_crafting.setWeaponMinimumDamage(newComponent, fltWpnMinDamage);
            space_crafting.setWeaponShieldEffectiveness(newComponent, fltWpnShdEffectiveness);
            space_crafting.setWeaponArmorEffectiveness(newComponent, fltWpnArmEffectiveness);
            space_crafting.setWeaponEnergyPerShot(newComponent, fltWpnEnergyPerShot);
            space_crafting.setWeaponRefireRate(newComponent, fltWpnRefireRate);
            String entries[] = new String[10];
            String strMassAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "massavg"));
            String strHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "hpavg"));
            String strArmorHpAvg = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "armorhpavg"));
            String strFinalOutput = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "finaloutput"));
            String strBonusApplied = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "bonusapplied"));
            String strEnergyMaintenance = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "energymaintenance"));
            String strWpnMaxDamage = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "wpnmaxdmg"));
            String strWpnMinDamage = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "wpnmindmg"));
            String strWpnShdEffectiveness = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "wpnshdeff"));
            String strWpnArmEffectiveness = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "wpnarmeff"));
            String strWpnEnergyPerShot = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "wpnenergypershot"));
            String strWpnRefireRate = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "wpnrate"));
            entries[0] = strMassAvg + " " + fltMassAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltMass;
            entries[1] = strHpAvg + " " + fltHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltHp;
            entries[2] = strArmorHpAvg + " " + fltArmorHpAverage + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltArmorHp;
            entries[3] = strEnergyMaintenance + " " + fltEnergyMaintenance + " --- " + strBonusApplied + " " + (int)(fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltEnergyMaintenanceFinal;
            entries[4] = strWpnMaxDamage + " " + fltWpnMaxDamageAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltWpnMaxDamage;
            entries[5] = strWpnMinDamage + " " + fltWpnMinDamageAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltWpnMinDamage;
            
            entries[6] = strWpnShdEffectiveness + " " + fltWpnShdEffectivenessAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltWpnShdEffectiveness;
            entries[7] = strWpnArmEffectiveness + " " + fltWpnArmEffectivenessAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltWpnArmEffectiveness;
            entries[8] = strWpnEnergyPerShot + " " + fltWpnEnergyPerShotAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltWpnEnergyPerShot;
            entries[9] = strWpnRefireRate + " " + fltWpnRefireRateAvg + " --- " + strBonusApplied + " " + (fltBonus * 100) + "%" + " --- " + strFinalOutput + " " + fltWpnRefireRate;
            finishReverseEngineering(newComponent, player, objComponents, entries);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "invfull");
            sendSystemMessage(player, error);
            return null;
        }
        return newComponent;
    }
    public void finishReverseEngineering(obj_id newComponent, obj_id player, obj_id[] objComponents, String[] entries) throws InterruptedException
    {
        String prompt = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "prompt"));
        String title = utils.packStringId(new string_id(space_crafting.STF_COMPONENT_TOOL, "title"));
        if (!hasScript(newComponent, space_crafting.SCRIPT_COMPONENT_NAME_HANDLER))
        {
            attachScript(newComponent, space_crafting.SCRIPT_COMPONENT_NAME_HANDLER);
        }
        int pid = sui.listbox(newComponent, player, prompt, sui.OK_ONLY, title, entries, "handleComponentNameSui", false, false);
        if (pid > -1)
        {
            showSUIPage(pid);
        }
        else 
        {
            string_id error = new string_id(space_crafting.STF_COMPONENT_TOOL, "bad_data");
            sendSystemMessage(player, error);
        }
        for (obj_id objComponent : objComponents) {
            destroyObject(objComponent);
        }
    }
}
