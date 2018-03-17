package script.systems.crafting.droid;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.consumable;
import script.library.player_stomach;
import script.library.bio_engineer;
import script.library.create;
import script.library.pet_lib;

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
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("mechanism_quality"))
                {
                    setObjVar(prototype, "mechanism_quality", (int)(itemAttributes[i].currentValue));
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("storage_module"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "storageModuleRating", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("data_module"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "dataModuleRating", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("personality_module"))
                {
                    int tempPersonality = (int)(itemAttributes[i].currentValue);
                    if (tempPersonality > 0)
                    {
                        if (tempPersonality <= 5)
                        {
                            setObjVar(prototype, "ai.diction", "droid_stupid");
                        }
                        else if (tempPersonality <= 10)
                        {
                            setObjVar(prototype, "ai.diction", "droid_sarcastic");
                        }
                        else if (tempPersonality <= 15)
                        {
                            setObjVar(prototype, "ai.diction", "droid_prissy");
                        }
                        else if (tempPersonality <= 20)
                        {
                            setObjVar(prototype, "ai.diction", "droid_worshipful");
                        }
                        else if (tempPersonality <= 25)
                        {
                            setObjVar(prototype, "ai.diction", "droid_slang");
                        }
                        else if (tempPersonality <= 30)
                        {
                            setObjVar(prototype, "ai.diction", "droid_geek");
                        }
                    }
                    else 
                    {
                        setObjVar(prototype, "ai.diction", "droid_default");
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("medical_module"))
                {
                    int medPower = (int)(itemAttributes[i].currentValue);
                    if (medPower > 0)
                    {
                        if (medPower <= 2)
                        {
                            setObjVar(prototype, "medpower", 0.55f);
                        }
                        else if (medPower <= 4)
                        {
                            setObjVar(prototype, "medpower", 0.65f);
                        }
                        else if (medPower <= 6)
                        {
                            setObjVar(prototype, "medpower", 0.75f);
                        }
                        else if (medPower <= 8)
                        {
                            setObjVar(prototype, "medpower", 0.85f);
                        }
                        else if (medPower <= 10)
                        {
                            setObjVar(prototype, "medpower", 1.00f);
                        }
                        else if (medPower > 10)
                        {
                            setObjVar(prototype, "medpower", 1.10f);
                        }
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("repair_module"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "repair_module", itemAttributes[i].currentValue);
                        setObjVar(prototype, "ai.pet.isRepairDroid", true);
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("armor_module"))
                {
                    tempArmorModule = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("armorEffectiveness"))
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("armor_toughness"))
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("power_level"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        powerLevel = (int)(itemAttributes[i].currentValue);
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("crafting_module"))
                {
                    if ((itemAttributes[i].currentValue > 0) && (itemAttributes[i].currentValue < 100000))
                    {
                        setObjVar(prototype, "craftingStation", true);
                        int craftingModuleValue = (int)(itemAttributes[i].currentValue);
                        if (craftingModuleValue >= 10000)
                        {
                            setObjVar(prototype, "craftingStationSpace", true);
                            craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 10000) * 10000));
                        }
                        if (craftingModuleValue >= 1000)
                        {
                            setObjVar(prototype, "craftingStationStructure", true);
                            craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 1000) * 1000));
                        }
                        if (craftingModuleValue >= 100)
                        {
                            setObjVar(prototype, "craftingStationClothing", true);
                            craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 100) * 100));
                        }
                        if (craftingModuleValue >= 10)
                        {
                            setObjVar(prototype, "craftingStationFood", true);
                            craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 10) * 10));
                        }
                        if (craftingModuleValue >= 1)
                        {
                            setObjVar(prototype, "craftingStationWeapon", true);
                        }
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("merchant_barker"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.merchant_barker", true);
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("bomb_level"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.bomb_level", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("stimpack_capacity"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.stimpack_capacity", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("stimpack_speed"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.stimpack_speed", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("auto_repair_power"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.auto_repair_power", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("playback_module"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.playback.modules", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("harvest_power"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.harvest_power", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("entertainer_effects"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.entertainer_effects", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("struct_module"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.struct_maint", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("trap_bonus"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.trap_bonus", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("cmbt_module"))
                {
                    tempCombatModule = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("fire_potency"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.fire_potency", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("arc_projector"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.arc_projector", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("shield_heatsink"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.shield_heatsink", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("pain_inducer"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.pain_inducer", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("quickset_metal"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.quickset_metal", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("dump_capacitors"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.dump_capacitors", (int)(itemAttributes[i].currentValue));
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("sampling_power"))
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, "module_data.sampling_power", (int)(itemAttributes[i].currentValue));
                    }
                }
                else 
                {
                    if (itemAttributes[i].currentValue > 0)
                    {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                    }
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
