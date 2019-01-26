package script.systems.crafting.droid;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.pet_lib;
import script.obj_id;

public class crafting_base_droid extends script.systems.crafting.crafting_base
{
    public crafting_base_droid()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final int COMBAT_MODULE_PROTECT_LVL = 500;
    public static final int DEFENSE_MODULE_PROTECT_LVL = 1000;
    public static final int DEFAULT_DROID_LEVEL = 1;
    public static final String TBL_MOB_STAT_BALANCE = "datatables/mob/stat_balance.iff";
    public static final String TBL_DROID_COMBAT = "datatables/combat/droid_combat_capabilities.iff";
    public static final String DROID_RANGED_WEAPON = "object/weapon/ranged/vehicle/droid_weapon.iff";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        obj_id deed = prototype;
        int tempCreatureLevel = 0;
        int tempCombatModule = 0;
        int tempArmorModule = 0;
        int tempCreatureDamageLevel = 0;
        int tempCreatureDefenseLevel = 0;
        int tempCreatureProtectionLevel = 0;
        dictionary droidCombatStats = new dictionary();
        int powerLevel = 0;
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                switch (((itemAttribute.name).getAsciiId())) {
                    case "mechanism_quality":
                        setObjVar(prototype, "mechanism_quality", (int) (itemAttribute.currentValue));
                        break;
                    case "storage_module":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "storageModuleRating", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "data_module":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "dataModuleRating", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "personality_module":
                        int tempPersonality = (int) (itemAttribute.currentValue);
                        if (tempPersonality > 0) {
                            if (tempPersonality <= 5) {
                                setObjVar(prototype, "ai.diction", "droid_stupid");
                            } else if (tempPersonality <= 10) {
                                setObjVar(prototype, "ai.diction", "droid_sarcastic");
                            } else if (tempPersonality <= 15) {
                                setObjVar(prototype, "ai.diction", "droid_prissy");
                            } else if (tempPersonality <= 20) {
                                setObjVar(prototype, "ai.diction", "droid_worshipful");
                            } else if (tempPersonality <= 25) {
                                setObjVar(prototype, "ai.diction", "droid_slang");
                            } else if (tempPersonality <= 30) {
                                setObjVar(prototype, "ai.diction", "droid_geek");
                            }
                        } else {
                            setObjVar(prototype, "ai.diction", "droid_default");
                        }
                        break;
                    case "medical_module":
                        int medPower = (int) (itemAttribute.currentValue);
                        if (medPower > 0) {
                            if (medPower <= 2) {
                                setObjVar(prototype, "medpower", 0.55f);
                            } else if (medPower <= 4) {
                                setObjVar(prototype, "medpower", 0.65f);
                            } else if (medPower <= 6) {
                                setObjVar(prototype, "medpower", 0.75f);
                            } else if (medPower <= 8) {
                                setObjVar(prototype, "medpower", 0.85f);
                            } else if (medPower <= 10) {
                                setObjVar(prototype, "medpower", 1.00f);
                            } else if (medPower > 10) {
                                setObjVar(prototype, "medpower", 1.10f);
                            }
                        }
                        break;
                    case "repair_module":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "repair_module", itemAttribute.currentValue);
                            setObjVar(prototype, "ai.pet.isRepairDroid", true);
                        }
                        break;
                    case "armor_module":
                        tempArmorModule = (int) itemAttribute.currentValue;
                        break;
                    case "armorEffectiveness":
                        break;
                    case "armor_toughness":
                        break;
                    case "power_level":
                        if (itemAttribute.currentValue > 0) {
                            powerLevel = (int) (itemAttribute.currentValue);
                        }
                        break;
                    case "crafting_module":
                        if ((itemAttribute.currentValue > 0) && (itemAttribute.currentValue < 100000)) {
                            setObjVar(prototype, "craftingStation", true);
                            int craftingModuleValue = (int) (itemAttribute.currentValue);
                            if (craftingModuleValue >= 10000) {
                                setObjVar(prototype, "craftingStationSpace", true);
                                craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 10000) * 10000));
                            }
                            if (craftingModuleValue >= 1000) {
                                setObjVar(prototype, "craftingStationStructure", true);
                                craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 1000) * 1000));
                            }
                            if (craftingModuleValue >= 100) {
                                setObjVar(prototype, "craftingStationClothing", true);
                                craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 100) * 100));
                            }
                            if (craftingModuleValue >= 10) {
                                setObjVar(prototype, "craftingStationFood", true);
                                craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 10) * 10));
                            }
                            if (craftingModuleValue >= 1) {
                                setObjVar(prototype, "craftingStationWeapon", true);
                            }
                        }
                        break;
                    case "merchant_barker":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.merchant_barker", true);
                        }
                        break;
                    case "bomb_level":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.bomb_level", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "stimpack_capacity":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.stimpack_capacity", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "stimpack_speed":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.stimpack_speed", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "auto_repair_power":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.auto_repair_power", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "playback_module":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.playback.modules", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "harvest_power":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.harvest_power", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "entertainer_effects":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.entertainer_effects", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "struct_module":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.struct_maint", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "trap_bonus":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.trap_bonus", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "cmbt_module":
                        tempCombatModule = (int) itemAttribute.currentValue;
                        break;
                    case "fire_potency":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.fire_potency", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "arc_projector":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.arc_projector", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "shield_heatsink":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.shield_heatsink", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "pain_inducer":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.pain_inducer", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "quickset_metal":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.quickset_metal", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "dump_capacitors":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.dump_capacitors", (int) (itemAttribute.currentValue));
                        }
                        break;
                    case "sampling_power":
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, "module_data.sampling_power", (int) (itemAttribute.currentValue));
                        }
                        break;
                    default:
                        if (itemAttribute.currentValue > 0) {
                            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                        }
                        break;
                }
            }
        }
        String droidName = getCreatureName();
        setObjVar(prototype, "creature_attribs.type", droidName);
        if (tempArmorModule > 0 || tempCombatModule > 0)
        {
            droidCombatStats.put("defaultDroidLevel", DEFAULT_DROID_LEVEL);
            droidCombatStats.put("armorModuleValue", tempArmorModule);
            droidCombatStats.put("combatModuleValue", tempCombatModule);
            pet_lib.initDroidCombatStats(deed, droidCombatStats);
        }
        else 
        {
            pet_lib.initDroidDefaultStats(deed);
        }
    }
    public String getCreatureName() throws InterruptedException
    {
        return null;
    }
}
