package script.npc.pet_deed;

import script.*;
import script.library.*;

public class droid_deed extends script.base_script
{
    public droid_deed()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "module_data.struct_maint"))
        {
            int struct = getIntObjVar(self, "module_data.struct_maint");
            if (struct > 15)
            {
                struct = 15;
                setObjVar(self, "module_data.struct_maint", struct);
            }
        }
        int reStatTheDeed = 0;
        if (!hasObjVar(self, "creature_attribs.level"))
        {
            reStatTheDeed = 1;
        }
        else if (hasObjVar(self, "combatModule") && hasObjVar(self, "crafting_components.cmbt_module"))
        {
            if ((getIntObjVar(self, "combatModule")) != ((int)(getFloatObjVar(self, "crafting_components.cmbt_module"))))
            {
                reStatTheDeed = 1;
            }
        }
        if (reStatTheDeed == 1)
        {
            pet_lib.cleanOldDroidDeed(self);
            dictionary droidCombatStats = new dictionary();
            if (hasObjVar(self, "armorModule"))
            {
                droidCombatStats.put("armorModuleValue", getIntObjVar(self, "armorModule"));
            }
            if (hasObjVar(self, "crafting_components.cmbt_module"))
            {
                int combatValue = (int)(getFloatObjVar(self, "crafting_components.cmbt_module"));
                droidCombatStats.put("combatModuleValue", combatValue);
            }
            pet_lib.initDroidCombatStats(self, droidCombatStats);
        }
        return SCRIPT_CONTINUE;
    }
    public int getStorageSlotsByRating(int rating) throws InterruptedException
    {
        int slots = 0;
        if (rating > 0 && rating <= 2)
        {
            slots = 1;
        }
        else if (rating <= 4)
        {
            slots = 2;
        }
        else if (rating <= 6)
        {
            slots = 4;
        }
        else if (rating <= 8)
        {
            slots = 6;
        }
        else if (rating <= 10)
        {
            slots = 8;
        }
        else if (rating <= 15)
        {
            slots = 10;
        }
        else if (rating <= 20)
        {
            slots = 15;
        }
        else if (rating <= 25)
        {
            slots = 20;
        }
        else if (rating < 30)
        {
            slots = 25;
        }
        else if (rating >= 30)
        {
            slots = 30;
        }
        return slots;
    }
    public int getDataSlotsByRating(int rating) throws InterruptedException
    {
        int slots = 0;
        if (rating > 0 && rating <= 2)
        {
            slots = 20;
        }
        else if (rating <= 4)
        {
            slots = 40;
        }
        else if (rating <= 6)
        {
            slots = 70;
        }
        else if (rating <= 8)
        {
            slots = 110;
        }
        else if (rating <= 10)
        {
            slots = 125;
        }
        else if (rating > 10)
        {
            slots = 150;
        }
        return slots;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality"))
        {
            names[idx] = "mechanism_quality";
            int value = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality");
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.level"))
        {
            names[idx] = "pet_stats.level";
            int value = getIntObjVar(self, "creature_attribs.level");
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]))
        {
            names[idx] = "pet_stats.creature_health";
            int value = getIntObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]);
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[ACTION]))
        {
            names[idx] = "pet_stats.creature_action";
            int value = getIntObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[ACTION]);
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.general_protection"))
        {
            names[idx] = "pet_stats.general_protection";
            int value = getIntObjVar(self, "creature_attribs.general_protection");
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.toHitChance"))
        {
            names[idx] = "pet_stats.creature_tohit";
            int value = getIntObjVar(self, "creature_attribs.toHitChance");
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.defenseValue"))
        {
            names[idx] = "pet_stats.creature_defense";
            int value = getIntObjVar(self, "creature_attribs.defenseValue");
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.stateResist"))
        {
            names[idx] = "pet_stats.creature_state_resist";
            float value = getFloatObjVar(self, "creature_attribs.stateResist");
            attribs[idx] = "" + value * 100 + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.critChance"))
        {
            names[idx] = "pet_stats.creature_crit_chance";
            float value = getFloatObjVar(self, "creature_attribs.critChance");
            attribs[idx] = "" + value * 100 + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.critSave"))
        {
            names[idx] = "pet_stats.creature_crit_save";
            float value = getFloatObjVar(self, "creature_attribs.critSave");
            attribs[idx] = "" + value * 100 + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.aggroBonus"))
        {
            names[idx] = "pet_stats.creature_aggro_bonus";
            float value = getFloatObjVar(self, "creature_attribs.aggroBonus");
            attribs[idx] = "" + value * 100 + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "creature_attribs.minDamage"))
        {
            names[idx] = "pet_stats.creature_damage";
            int min = getIntObjVar(self, "creature_attribs.minDamage");
            int max = getIntObjVar(self, "creature_attribs.maxDamage");
            int[] dmgBonus = pet_lib.getPetAbilityDamageBonus(self, min, max);
            min += dmgBonus[0];
            max += dmgBonus[1];
            attribs[idx] = "" + min + " - " + max;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.fire_potency"))
        {
            names[idx] = "pet_stats.fire_potency";
            int firePotency = getIntObjVar(self, "module_data.fire_potency");
            attribs[idx] = " " + firePotency;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pet_stats.flame_jet_level";
            int bonusFlameJetLevel = pet_lib.getDroidModuleCommandLevel(player, firePotency);
            attribs[idx] = " " + bonusFlameJetLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.arc_projector"))
        {
            names[idx] = "pet_stats.arc_projector";
            int attribValue = getIntObjVar(self, "module_data.arc_projector");
            attribs[idx] = " " + attribValue;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pet_stats.electrical_shock_level";
            int bonusCommandLevel = pet_lib.getDroidModuleCommandLevel(player, attribValue);
            attribs[idx] = " " + bonusCommandLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.shield_heatsink"))
        {
            names[idx] = "pet_stats.shield_heatsink";
            int attribValue = getIntObjVar(self, "module_data.shield_heatsink");
            attribs[idx] = " " + attribValue;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pet_stats.droideka_shield_level";
            int bonusCommandLevel = pet_lib.getDroidModuleCommandLevel(player, attribValue);
            attribs[idx] = " " + bonusCommandLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.pain_inducer"))
        {
            names[idx] = "pet_stats.pain_inducer";
            int attribValue = getIntObjVar(self, "module_data.pain_inducer");
            attribs[idx] = " " + attribValue;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pet_stats.torturous_needle_level";
            int bonusCommandLevel = pet_lib.getDroidModuleCommandLevel(player, attribValue);
            attribs[idx] = " " + bonusCommandLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.quickset_metal"))
        {
            names[idx] = "pet_stats.quickset_metal";
            int attribValue = getIntObjVar(self, "module_data.quickset_metal");
            attribs[idx] = " " + attribValue;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pet_stats.regenerative_plating_level";
            int bonusCommandLevel = pet_lib.getDroidModuleCommandLevel(player, attribValue);
            attribs[idx] = " " + bonusCommandLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.dump_capacitors"))
        {
            names[idx] = "pet_stats.dump_capacitors";
            int attribValue = getIntObjVar(self, "module_data.dump_capacitors");
            attribs[idx] = " " + attribValue;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pet_stats.battery_dump_level";
            int bonusCommandLevel = pet_lib.getDroidModuleCommandLevel(player, attribValue);
            attribs[idx] = " " + bonusCommandLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "medpower"))
        {
            names[idx] = "medpower";
            float med = getFloatObjVar(self, "medpower");
            attribs[idx] = " " + ((int)(med * 100));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "ai.pet.isRepairDroid"))
        {
            names[idx] = "is_repair_droid";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "storageModuleRating"))
        {
            names[idx] = "storage_module_rating";
            int rating = getIntObjVar(self, "storageModuleRating");
            attribs[idx] = "" + rating;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "inventory_capacity";
            int storage = getStorageSlotsByRating(rating);
            attribs[idx] = "" + storage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "dataModuleRating"))
        {
            int datastorage = getIntObjVar(self, "dataModuleRating");
            names[idx] = "data_module_cert_needed";
            String certNeeded = "Level 1";
            if ((datastorage > 0) && (datastorage <= 2))
            {
                certNeeded = "Level 1";
            }
            else if (datastorage <= 4)
            {
                certNeeded = "Level 2";
            }
            else if (datastorage <= 6)
            {
                certNeeded = "Level 3";
            }
            else if (datastorage <= 8)
            {
                certNeeded = "Level 4";
            }
            else if (datastorage <= 10)
            {
                certNeeded = "Level 5";
            }
            else if (datastorage > 10)
            {
                certNeeded = "Level 6";
            }
            attribs[idx] = " " + certNeeded;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "data_module_rating";
            attribs[idx] = "" + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "datapad_slots";
            int storage = getDataSlotsByRating(datastorage);
            attribs[idx] = "" + storage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "craftingStation"))
        {
            names[idx] = "crafting_station";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "craftingStationSpace"))
        {
            names[idx] = "crafting_station_space";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "craftingStationStructure"))
        {
            names[idx] = "crafting_station_structure";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "craftingStationClothing"))
        {
            names[idx] = "crafting_station_clothing";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "craftingStationFood"))
        {
            names[idx] = "crafting_station_food";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "craftingStationWeapon"))
        {
            names[idx] = "crafting_station_weapon";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "ai.diction"))
        {
            names[idx] = "ai_diction";
            String personality = getStringObjVar(self, "ai.diction");
            attribs[idx] = " " + localize(new string_id("npc_reaction/npc_diction", personality));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "crafting_components.cmbt_module"))
        {
            float combatStrength = getFloatObjVar(self, "crafting_components.cmbt_module");
            if (combatStrength > pet_lib.MAX_DROID_COMBAT_VALUE)
            {
                combatStrength = pet_lib.MAX_DROID_COMBAT_VALUE;
            }
            else if (combatStrength < 0.0f)
            {
                combatStrength = 1.0f;
            }
            names[idx] = "combat_rating";
            attribs[idx] = " " + (int)combatStrength;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.bomb_level"))
        {
            names[idx] = "bomb_level";
            int datastorage = getIntObjVar(self, "module_data.bomb_level");
            if (hasObjVar(self, "module_data.bomb_level_bonus"))
            {
                datastorage += getIntObjVar(self, "module_data.bomb_level_bonus");
            }
            int minDamage = datastorage * pet_lib.DETONATION_DROID_MIN_DAMAGE;
            int maxDamage = datastorage * pet_lib.DETONATION_DROID_MAX_DAMAGE;
            attribs[idx] = " " + datastorage + " (" + minDamage + " - " + maxDamage + ")";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.merchant_barker"))
        {
            names[idx] = "merchant_barker";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.stimpack_capacity"))
        {
            names[idx] = "stimpack_capacity";
            attribs[idx] = " " + getIntObjVar(self, "module_data.stimpack_capacity");
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "stimpack_speed";
            attribs[idx] = " " + getIntObjVar(self, "module_data.stimpack_speed");
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.auto_repair_power"))
        {
            names[idx] = "auto_repair_power";
            int datastorage = getIntObjVar(self, "module_data.auto_repair_power");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.entertainer_effects"))
        {
            String[] effects = pet_lib.getLightingEffects(self);
            if (effects != null)
            {
                for (String effect : effects) {
                    names[idx] = effect;
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length) {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        if (hasObjVar(self, "module_data.playback.modules"))
        {
            names[idx] = "playback_modules";
            int numModules = getIntObjVar(self, "module_data.playback.modules");
            attribs[idx] = " " + numModules;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.harvest_power"))
        {
            names[idx] = "harvest_power";
            int bonusHarvest = getIntObjVar(self, "module_data.harvest_power");
            attribs[idx] = " " + bonusHarvest;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.sampling_power"))
        {
            names[idx] = "sampling_power";
            int bonusHarvest = getIntObjVar(self, "module_data.sampling_power");
            attribs[idx] = " " + bonusHarvest;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.struct_maint"))
        {
            names[idx] = "struct_module_rating";
            int struct = getIntObjVar(self, "module_data.struct_maint");
            attribs[idx] = " " + struct;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "module_data.trap_bonus"))
        {
            names[idx] = "trap_bonus";
            int trapBonus = getIntObjVar(self, "module_data.trap_bonus");
            attribs[idx] = " " + trapBonus;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            int maxTrapCount = pet_lib.calculateMaxTrapLoad(self);
            names[idx] = "max_trap_load";
            attribs[idx] = Integer.toString(maxTrapCount);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            mi.addRootMenu(menu_info_types.PET_TAME, new string_id(MENU_FILE, "menu_unpack"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.PET_TAME)
        {
            String creatureName = getStringObjVar(self, "creature_attribs.type");
            int petType = pet_lib.getPetType(creatureName);
            int droidLevel = getIntObjVar(self, "creature_attribs.level");
            if (getLevel(player) < droidLevel - pet_lib.MAX_PET_LEVELS_ABOVE_CALLER)
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL_LEVEL);
                return SCRIPT_CONTINUE;
            }
            if (pet_lib.hasMaxStoredPetsOfType(player, petType))
            {
                sendSystemMessage(player, beast_lib.SID_MAXIMUM_COMBAT_CONTROL_DEVICES);
                return SCRIPT_CONTINUE;
            }
            if (pet_lib.hasMaxPets(player, petType))
            {
                sendSystemMessage(player, new string_id("pet/pet_menu", "sys_cant_generate"));
                return SCRIPT_CONTINUE;
            }
            obj_id pet = createCraftedCreatureDevice(player, self);
            if (isIdValid(pet))
            {
                CustomerServiceLog("droid_deed", "droid deed used: deed=" + self + " pcd=" + pet + " player=" + player + "(" + getPlayerName(player) + ")");
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id createCraftedCreatureDevice(obj_id player, obj_id deed) throws InterruptedException
    {
        if (!canManipulate(player, deed, true, true, 15, true))
        {
            return null;
        }
        String creatureName = getStringObjVar(deed, "creature_attribs.type");
        int petType = pet_lib.getPetType(creatureName);
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            return null;
        }
        String controlTemplate = "object/intangible/pet/" + utils.dataTableGetString(create.CREATURE_TABLE, creatureName, "template");
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        if (!isIdValid(petControlDevice))
        {
            sendSystemMessage(player, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
            return null;
        }
        else 
        {
            string_id nameId = new string_id(create.CREATURE_NAME_FILE, creatureName);
            setName(petControlDevice, nameId);
            setObjVar(petControlDevice, "pet.creatureName", creatureName);
            setObjVar(petControlDevice, "pet.crafted", true);
            attachScript(petControlDevice, "ai.pet_control_device");
            setObjVar(petControlDevice, "ai.pet.type", petType);
            if (hasObjVar(deed, "medpower"))
            {
                float deedMedModuleValue = getFloatObjVar(deed, "medpower");
                setObjVar(petControlDevice, "medpower", deedMedModuleValue);
            }
            if (hasObjVar(deed, "ai.pet.isRepairDroid"))
            {
                setObjVar(petControlDevice, "ai.pet.isRepairDroid", true);
            }
            if (hasObjVar(deed, "storageModuleRating"))
            {
                int deedStorageModuleValue = getIntObjVar(deed, "storageModuleRating");
                setObjVar(petControlDevice, "ai.pet.hasContainer", deedStorageModuleValue);
                initDroidCraftedInventoryPCD(petControlDevice, player);
            }
            if (hasObjVar(deed, "dataModuleRating"))
            {
                int deedDataStorageModuleValue = getIntObjVar(deed, "dataModuleRating");
                setObjVar(petControlDevice, "dataModuleRating", deedDataStorageModuleValue);
                initDroidCraftedDatapadPCD(petControlDevice, player);
            }
            if (hasObjVar(deed, "ai.diction"))
            {
                String deedPersonalityModuleValue = getStringObjVar(deed, "ai.diction");
                setObjVar(petControlDevice, "ai.diction", deedPersonalityModuleValue);
            }
            copyObjVar(deed, petControlDevice, "module_data");
            if (hasObjVar(deed, "armorModule"))
            {
                int deedArmorModuleValue = getIntObjVar(deed, "armorModule");
                setObjVar(petControlDevice, "armorModule", deedArmorModuleValue);
            }
            if (hasObjVar(deed, "craftingStation"))
            {
                setObjVar(petControlDevice, "craftingStation", true);
            }
            if (hasObjVar(deed, "craftingStationSpace"))
            {
                setObjVar(petControlDevice, "craftingStationSpace", true);
            }
            if (hasObjVar(deed, "craftingStationStructure"))
            {
                setObjVar(petControlDevice, "craftingStationStructure", true);
            }
            if (hasObjVar(deed, "craftingStationClothing"))
            {
                setObjVar(petControlDevice, "craftingStationClothing", true);
            }
            if (hasObjVar(deed, "craftingStationFood"))
            {
                setObjVar(petControlDevice, "craftingStationFood", true);
            }
            if (hasObjVar(deed, "craftingStationWeapon"))
            {
                setObjVar(petControlDevice, "craftingStationWeapon", true);
            }
            if (hasObjVar(deed, "pet.combatDroid"))
            {
                setObjVar(petControlDevice, "pet.combatDroid", 1);
            }
            else if (creatureName.equals(pet_lib.PROBOT_DROID_NAME) || creatureName.equals(pet_lib.DZ70_DROID_NAME))
            {
                setObjVar(petControlDevice, "pet.nonCombatDroid", 1);
            }
            pet_lib.initPCDCraftedStats(petControlDevice, deed);
        }
        if (!callable.hasCallable(player, callable.CALLABLE_TYPE_COMBAT_PET))
        {
            obj_id pet = create.object(creatureName, getLocation(player));
            if (!isIdValid(pet))
            {
                return petControlDevice;
            }
            pet_lib.setCraftedPetStatsByGrowth(petControlDevice, pet, 10);
            ranged_int_custom_var[] ri = hue.getPalcolorVars(deed);
            if (ri != null && ri.length > 0)
            {
                for (ranged_int_custom_var ranged_int_custom_var : ri) {
                    int val = ranged_int_custom_var.getValue();
                    if (val > -1) {
                        String varpath = pet_lib.VAR_PALVAR_VARS + "." + ranged_int_custom_var.getVarName();
                        setObjVar(petControlDevice, varpath, val);
                        hue.setColor(pet, ranged_int_custom_var.getVarName(), val);
                    }
                }
                setObjVar(petControlDevice, pet_lib.VAR_PALVAR_CNT, pet_lib.CUSTOMIZATION_COUNT);
            }
            callable.setCallableLinks(player, petControlDevice, pet);
            pet_lib.makePet(pet, player);
            pet_lib.initDroidCraftedInventoryDroid(pet, petControlDevice);
            pet_lib.initDroidCraftedDatapadDroid(pet, petControlDevice);
            pet_lib.initDroidPersonality(pet, petControlDevice);
            pet_lib.initDroidMedicalPower(pet, petControlDevice);
            pet_lib.initDroidCraftingPower(pet, petControlDevice, player);
            pet_lib.initDroidRepairPower(pet, petControlDevice);
            initDroidModuleData(pet, petControlDevice);
            dictionary params = new dictionary();
            params.put("pet", pet);
            params.put("master", player);
            messageTo(pet, "handleAddMaster", params, 1, false);
            String name = getAssignedName(petControlDevice);
            setName(pet, name);
            location destLoc = getLocation(player);
            destLoc.x += rand(2, 2);
            destLoc.z += rand(2, 2);
            pathTo(pet, destLoc);
        }
        return petControlDevice;
    }
    public void initDroidCraftedInventoryPCD(obj_id petControlDevice, obj_id player) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "ai.pet.hasContainer"))
        {
            int newDroidInventoryRating = getIntObjVar(petControlDevice, "ai.pet.hasContainer");
            String inventoryTemplate = "object/tangible/inventory/creature_inventory.iff";
            if ((newDroidInventoryRating > 0) && (newDroidInventoryRating <= 2))
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_1.iff";
            }
            else if (newDroidInventoryRating <= 4)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_2.iff";
            }
            else if (newDroidInventoryRating <= 6)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_3.iff";
            }
            else if (newDroidInventoryRating <= 8)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_4.iff";
            }
            else if (newDroidInventoryRating <= 10)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_5.iff";
            }
            else if (newDroidInventoryRating <= 15)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_6.iff";
            }
            else if (newDroidInventoryRating <= 20)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_7.iff";
            }
            else if (newDroidInventoryRating <= 25)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_8.iff";
            }
            else if (newDroidInventoryRating < 30)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_9.iff";
            }
            else if (newDroidInventoryRating >= 30)
            {
                inventoryTemplate = "object/tangible/inventory/droid_inventory_10.iff";
            }
            obj_id inv = getObjectInSlot(petControlDevice, "inventory");
            if (isIdValid(inv))
            {
                LOG("LOG_CHANNEL", "droid_deed::initDroidCraftedInventory -- Destroying an existing inventory on droid.");
                destroyObject(inv);
            }
            obj_id newerInventory = createObject(inventoryTemplate, petControlDevice, "inventory");
            if (isIdValid(newerInventory))
            {
                setOwner(newerInventory, player);
            }
            else 
            {
                LOG("LOG_CHANNEL", "droid_deed::initDroidCraftedInventory -- Unable to create valid datapad and/or unable to set owner on it.");
            }
        }
        else 
        {
            obj_id inv = getObjectInSlot(petControlDevice, "inventory");
            if (isIdValid(inv))
            {
                LOG("LOG_CHANNEL", "droid_deed::initDroidCraftedInventory -- Destroying an existing inventory on droid.");
                destroyObject(inv);
            }
        }
    }
    public void initDroidCraftedDatapadPCD(obj_id petControlDevice, obj_id player) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "dataModuleRating"))
        {
            int newDroidDatapadRating = getIntObjVar(petControlDevice, "dataModuleRating");
            String datapadTemplate = "object/tangible/datapad/character_datapad.iff";
            if ((newDroidDatapadRating > 0) && (newDroidDatapadRating <= 2))
            {
                datapadTemplate = "object/tangible/datapad/droid_datapad_1.iff";
            }
            else if (newDroidDatapadRating <= 4)
            {
                datapadTemplate = "object/tangible/datapad/droid_datapad_2.iff";
            }
            else if (newDroidDatapadRating <= 6)
            {
                datapadTemplate = "object/tangible/datapad/droid_datapad_3.iff";
            }
            else if (newDroidDatapadRating <= 8)
            {
                datapadTemplate = "object/tangible/datapad/droid_datapad_4.iff";
            }
            else if (newDroidDatapadRating <= 10)
            {
                datapadTemplate = "object/tangible/datapad/droid_datapad_5.iff";
            }
            else if (newDroidDatapadRating > 10)
            {
                datapadTemplate = "object/tangible/datapad/droid_datapad_6.iff";
            }
            obj_id dpad = getObjectInSlot(petControlDevice, "datapad");
            if (isIdValid(dpad))
            {
                destroyObject(dpad);
            }
            obj_id newerDatapad = createObject(datapadTemplate, petControlDevice, "datapad");
            if (isIdValid(newerDatapad))
            {
                setOwner(newerDatapad, player);
            }
            else 
            {
                LOG("LOG_CHANNEL", "droid_deed::initDroidCraftedDatapad -- Unable to create valid datapad and/or unable to set owner on it.");
            }
        }
        else 
        {
            obj_id dpad = getObjectInSlot(petControlDevice, "datapad");
            if (isIdValid(dpad))
            {
                debugServerConsoleMsg(null, "**********  The PCD doesn't have an objvar that means it should get an inventory, yet an obj_id check of the 'datapad' slot claims that it has one. Going to destroy that object.");
                LOG("LOG_CHANNEL", "droid_deed::initDroidCraftedDatapad -- Destroying existing datapad on droid.");
                destroyObject(dpad);
            }
        }
    }
    public void initDroidModuleData(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        copyObjVar(petControlDevice, pet, "module_data");
        if (hasObjVar(petControlDevice, "module_data.merchant_barker"))
        {
            attachScript(pet, "systems.crafting.droid.modules.merchant_barker");
        }
        if (hasObjVar(petControlDevice, "module_data.bomb_level"))
        {
            attachScript(pet, "systems.crafting.droid.modules.droid_bomb");
            utils.setScriptVar(pet, "module_data.detonation_warmup", 1);
            messageTo(pet, "msgDetonationWarmup", null, 10.0f, false);
        }
        if (hasObjVar(petControlDevice, "module_data.stimpack_capacity"))
        {
            attachScript(pet, "systems.crafting.droid.modules.stimpack_dispensor");
        }
        if (hasObjVar(petControlDevice, "module_data.auto_repair_power"))
        {
            attachScript(pet, "systems.crafting.droid.modules.auto_repair");
        }
        if (hasObjVar(petControlDevice, "module_data.playback.modules"))
        {
            attachScript(pet, "systems.crafting.droid.modules.playback");
        }
        if (hasObjVar(petControlDevice, "module_data.harvest_power"))
        {
            attachScript(pet, "systems.crafting.droid.modules.harvest_module");
        }
        if (hasObjVar(petControlDevice, "module_data.struct_maint"))
        {
            attachScript(pet, "systems.crafting.droid.modules.structure_maintenance");
        }
        if (hasObjVar(petControlDevice, "module_data.auto_repair_power"))
        {
            attachScript(pet, "systems.crafting.droid.modules.auto_repair");
        }
        if (hasObjVar(petControlDevice, "module_data.entertainer_effects"))
        {
            attachScript(pet, "systems.crafting.droid.modules.entertainer_effect");
        }
        if (hasObjVar(petControlDevice, "module_data.trap_bonus"))
        {
            attachScript(pet, "systems.crafting.droid.modules.trap_thrower");
        }
        if (hasObjVar(petControlDevice, "module_data.sampling_power"))
        {
            attachScript(pet, "ai.hand_sampling");
        }
    }
}
