package script.ai;

import script.*;
import script.library.*;

import java.util.Vector;

public class pet extends script.base_script
{
    public pet()
    {
    }
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public static final float CORPSE_CLEANUP_DELAY = 30.0f;
    public static final String STRING_FILE = "hireling/hireling";
    public static final String MENU_FILE = "pet/pet_menu";
    public static final String CONVO = "HIRELING_CONVO";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String PCDPING_PET_SCRIPT_NAME = "ai.pet_ping";
    public static final String MESSAGE_PET_ID = "petId";
    public static final boolean debug = false;
    public static final String BARK_TRIGGER_VOLUME = "module.bark_trigger_volume";
    public static final String CAN_BARK = "module.can_bark";
    public static final float BARK_RANGE = 20.0f;
    public static final float BARK_DELAY = 60.0f;
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static final string_id SID_HELPER_DEFAULT_REMINDER = new string_id(MENU_FILE, "helper_default_reminder");
    public static final string_id SID_MUST_DISMOUNT = new string_id("pet/pet_menu", "must_dismount");
    public static final string_id PCOLOR = new string_id("sui", "set_primary_color");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (pet_lib.getPetType(self) == pet_lib.PET_TYPE_FAMILIAR)
        {
            attachScript(self, "ai.familiar");
            detachScript(self, "ai.pet");
            detachScript(self, "systems.combat.combat_actions");
            detachScript(self, "ai.ai");
        }
        else 
        {
            messageTo(self, "handleSetupPet", null, 1, false);
        }
        setObjVar(self, "alreadyTamed", true);
        if (hasObjVar(self, "poi"))
        {
            obj_id poiBaseObject = poi.getBaseObject(self);
            poi.removeFromMasterList(poiBaseObject, self);
            removeObjVar(self, "poi");
            if (hasObjVar(self, "npc_lair"))
            {
                removeObjVar(self, "npc_lair");
            }
        }
        if (pet_lib.isSpeakingPet(self))
        {
            setCondition(self, CONDITION_CONVERSABLE);
        }
        if (!hasScript(self, PCDPING_PET_SCRIPT_NAME))
        {
            if (debug)
            {
                LOG("pcdping-debug", "pet.OnInitialize(): attaching script [" + PCDPING_PET_SCRIPT_NAME + "] to vehicle id=[" + self + "]");
            }
            attachScript(self, PCDPING_PET_SCRIPT_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "ai.patrolPoint"))
        {
            obj_var_list oldPath = getObjVarList(self, "ai.patrolPoint");
            if (oldPath != null)
            {
                location[] newPath = new location[oldPath.getNumItems()];
                for (int i = 0; i < newPath.length; ++i)
                {
                    location point = oldPath.getLocationObjVar(String.valueOf(i));
                    newPath[i] = point;
                }
                setObjVar(self, pet_lib.OBJVAR_PET_PATROL_POINTS, newPath);
                removeObjVar(self, "ai.patrolPoint");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        if (isDead(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleSetupPet", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (isIdValid(getMaster(self)))
        {
            names[idx] = "owner";
            attribs[idx] = getName(getMaster(self));
            idx++;
        }
        if (pet_lib.isDroidPet(self))
        {
            names[idx] = "pet_stats.level";
            attribs[idx] = String.valueOf(getLevel(self));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id petControlDevice = callable.getCallableCD(self);
            if (isIdValid(petControlDevice) && exists(petControlDevice))
            {
                int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
                float percentRemaining = (((float)pet_lib.OUT_OF_POWER - powerLevel) / pet_lib.OUT_OF_POWER) * 100.0f;
                names[idx] = "pet_stats.battery_power";
                attribs[idx] = " " + (int)percentRemaining + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                if (hasObjVar(petControlDevice, "creature_attribs.general_protection"))
                {
                    int general_protection = getIntObjVar(petControlDevice, "creature_attribs.general_protection");
                    names[idx] = "pet_stats.dna_comp_armor_effectiveness";
                    attribs[idx] = String.valueOf(general_protection);
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
                if (creatureName == null || creatureName.equals(""))
                {
                    return SCRIPT_CONTINUE;
                }
                int niche = dataTableGetInt(CREATURE_TABLE, creatureName, "niche");
                if (niche != NICHE_DROID || pet_lib.isCombatDroidPCD(petControlDevice))
                {
                    if (hasObjVar(petControlDevice, "creature_attribs.toHitChance"))
                    {
                        names[idx] = "pet_stats.creature_tohit";
                        int value = getIntObjVar(petControlDevice, "creature_attribs.toHitChance");
                        attribs[idx] = String.valueOf(value);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs.defenseValue"))
                    {
                        names[idx] = "pet_stats.creature_defense";
                        int value = getIntObjVar(petControlDevice, "creature_attribs.defenseValue");
                        attribs[idx] = String.valueOf(value);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs.stateResist"))
                    {
                        names[idx] = "pet_stats.creature_state_resist";
                        float value = getFloatObjVar(petControlDevice, "creature_attribs.stateResist");
                        attribs[idx] = String.valueOf(value * 100) + "%";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs.critChance"))
                    {
                        names[idx] = "pet_stats.creature_crit_chance";
                        float value = getFloatObjVar(petControlDevice, "creature_attribs.critChance");
                        attribs[idx] = String.valueOf(value * 100) + "%";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs.critSave"))
                    {
                        names[idx] = "pet_stats.creature_crit_save";
                        float value = getFloatObjVar(petControlDevice, "creature_attribs.critSave");
                        attribs[idx] = String.valueOf(value * 100) + "%";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs.aggroBonus"))
                    {
                        names[idx] = "pet_stats.creature_aggro_bonus";
                        float value = getFloatObjVar(petControlDevice, "creature_attribs.aggroBonus");
                        attribs[idx] = String.valueOf(value * 100) + "%";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs.minDamage"))
                    {
                        names[idx] = "pet_stats.creature_damage";
                        obj_id creatureWeapon = getCurrentWeapon(self);
                        int min = getWeaponMinDamage(creatureWeapon);
                        int max = getWeaponMaxDamage(creatureWeapon);
                        int[] dmgBonus = pet_lib.getPetAbilityDamageBonus(petControlDevice, min, max);
                        min += dmgBonus[0];
                        max += dmgBonus[1];
                        attribs[idx] = min + " - " + max;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON))
                    {
                        String weapon = getStringObjVar(petControlDevice, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON);
                        if (weapon != null)
                        {
                            names[idx] = "pet_stats.dna_comp_ranged_attack";
                            if (weapon.equals(""))
                            {
                                attribs[idx] = "No";
                            }
                            else 
                            {
                                attribs[idx] = "Yes";
                            }
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                    if (hasObjVar(petControlDevice, "ai.pet.isRepairDroid"))
                    {
                        names[idx] = "pet_stats.is_repair_droid";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "storageModuleRating"))
                    {
                        names[idx] = "pet_stats.storage_module_rating";
                        int storage = getIntObjVar(petControlDevice, "storageModuleRating");
                        attribs[idx] = String.valueOf(storage);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "ai.pet.hasContainer"))
                    {
                        names[idx] = "pet_stats.storage_module_rating";
                        int storage = getIntObjVar(petControlDevice, "ai.pet.hasContainer");
                        attribs[idx] = String.valueOf(storage);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        obj_id petInv = utils.getInventoryContainer(petControlDevice);
                        if (isIdValid(petInv))
                        {
                            storage = getTotalVolume(petInv);
                            if (storage > 0)
                            {
                                names[idx] = "pet_stats.inventory_capacity";
                                attribs[idx] = String.valueOf(storage);
                                idx++;
                                if (idx >= names.length)
                                {
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                    if (hasObjVar(petControlDevice, "dataModuleRating"))
                    {
                        int datastorage = getIntObjVar(petControlDevice, "dataModuleRating");
                        if (space_crafting.isUsableAstromechPet(petControlDevice))
                        {
                            obj_id[] loadedDroidCommands = space_crafting.getDatapadDroidCommands(petControlDevice);
                            if ((loadedDroidCommands != null) && (loadedDroidCommands.length > 0))
                            {
                                names[idx] = "pet_stats.droid_program_loaded";
                                attribs[idx] = " ";
                                idx++;
                                if (idx >= names.length)
                                {
                                    return SCRIPT_CONTINUE;
                                }
                                for (obj_id loadedDroidCommand : loadedDroidCommands) {
                                    if (hasObjVar(loadedDroidCommand, "strDroidCommand")) {
                                        String programName = getStringObjVar(loadedDroidCommand, "strDroidCommand");
                                        names[idx] = "pet_stats.droid_program";
                                        attribs[idx] = " " + localize(new string_id("space/droid_commands", programName));
                                        idx++;
                                        if (idx >= names.length) {
                                            return SCRIPT_CONTINUE;
                                        }
                                    }
                                }
                            }
                            names[idx] = "pet_stats.data_module_cert_needed";
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
                            names[idx] = "pet_stats.data_module_rating";
                            attribs[idx] = String.valueOf(datastorage);
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
                            obj_id petInv = utils.getObjectInSlot(petControlDevice, "datapad");
                            if (isIdValid(petInv))
                            {
                                int storage = getTotalVolume(petInv);
                                if (storage > 0)
                                {
                                    names[idx] = "pet_stats.datapad_slots";
                                    attribs[idx] = String.valueOf(storage);
                                    idx++;
                                    if (idx >= names.length)
                                    {
                                        return SCRIPT_CONTINUE;
                                    }
                                }
                            }
                        }
                    }
                    if (hasObjVar(petControlDevice, "craftingStation"))
                    {
                        names[idx] = "pet_stats.crafting_station";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "craftingStationSpace"))
                    {
                        names[idx] = "pet_stats.crafting_station_space";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "craftingStationStructure"))
                    {
                        names[idx] = "pet_stats.crafting_station_structure";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "craftingStationClothing"))
                    {
                        names[idx] = "pet_stats.crafting_station_clothing";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "craftingStationFood"))
                    {
                        names[idx] = "pet_stats.crafting_station_food";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "craftingStationWeapon"))
                    {
                        names[idx] = "pet_stats.crafting_station_weapon";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "ai.diction"))
                    {
                        names[idx] = "pet_stats.ai_diction";
                        String personality = getStringObjVar(petControlDevice, "ai.diction");
                        attribs[idx] = " " + localize(new string_id("npc_reaction/npc_diction", personality));
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.bomb_level"))
                    {
                        names[idx] = "pet_stats.bomb_level";
                        int datastorage = getIntObjVar(petControlDevice, "module_data.bomb_level");
                        if (hasObjVar(petControlDevice, "module_data.bomb_level_bonus"))
                        {
                            datastorage += getIntObjVar(petControlDevice, "module_data.bomb_level_bonus");
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
                    if (hasObjVar(petControlDevice, "module_data.merchant_barker"))
                    {
                        names[idx] = "pet_stats.merchant_barker";
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.stimpack_capacity"))
                    {
                        names[idx] = "pet_stats.stimpack_power";
                        int power = getIntObjVar(petControlDevice, "module_data.stim_power");
                        int supply = getIntObjVar(petControlDevice, "module_data.stimpack_supply");
                        int capacity = getIntObjVar(petControlDevice, "module_data.stimpack_capacity");
                        int speed = getIntObjVar(petControlDevice, "module_data.stimpack_speed");
                        if (power < 0)
                        {
                            power = 0;
                        }
                        if (supply < 0)
                        {
                            supply = 0;
                        }
                        attribs[idx] = " " + power;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        names[idx] = "pet_stats.stimpack_capacity";
                        attribs[idx] = " " + supply + " / " + capacity;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        names[idx] = "pet_stats.stimpack_speed";
                        attribs[idx] = " " + speed;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.auto_repair_power"))
                    {
                        names[idx] = "pet_stats.auto_repair_power";
                        int datastorage = getIntObjVar(petControlDevice, "module_data.auto_repair_power");
                        attribs[idx] = " " + datastorage;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.playback.modules"))
                    {
                        names[idx] = "pet_stats.playback_modules";
                        int numModules = getIntObjVar(petControlDevice, "module_data.playback.modules");
                        attribs[idx] = " " + numModules;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.harvest_power"))
                    {
                        names[idx] = "pet_stats.harvest_power";
                        int bonusHarvest = getIntObjVar(petControlDevice, "module_data.harvest_power");
                        attribs[idx] = " " + bonusHarvest;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.entertainer_effects"))
                    {
                        String[] effects = pet_lib.getLightingEffects(petControlDevice);
                        if (effects != null)
                        {
                            for (String effect : effects) {
                                names[idx] = "pet_stats." + effect;
                                attribs[idx] = " installed";
                                idx++;
                                if (idx >= names.length) {
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.struct_maint"))
                    {
                        names[idx] = "pet_stats.struct_module_rating";
                        int struct = getIntObjVar(petControlDevice, "module_data.struct_maint");
                        attribs[idx] = " " + struct;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (hasObjVar(petControlDevice, "module_data.trap_bonus"))
                    {
                        names[idx] = "pet_stats.trap_bonus";
                        int trapBonus = getIntObjVar(petControlDevice, "module_data.trap_bonus");
                        attribs[idx] = " " + trapBonus;
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
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetupPet(obj_id self, dictionary params) throws InterruptedException
    {
        if(!pet_lib.hasMaster(self)){
            return SCRIPT_CONTINUE;
        }
        LOGC(aiLoggingEnabled(self), "debug_ai", "pet::handleSetupPet() self(" + self + ":" + getName(self) + ") master(" + getMaster(self) + ":" + getName(getMaster(self)) + ")");
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, "theme_park.poi.poi_object"))
        {
            detachScript(self, "theme_park.poi.poi_object");
        }
        
        // Handle script loading order issue (where one script method trumps another script method due to the order in which it was attached)
        // with Love Day familiars (matchmaking quest)
        obj_id petControl = callable.getCallableCD(self);
        String creatureName = getStringObjVar(petControl, "pet.creatureName");
        if (creatureName.startsWith("loveday_romance_seeker_familiar"))
        {
            attachScript(self, "conversation.loveday_romance_seeker");
        }
        
        setOwner(utils.getInventoryContainer(self), master);
        managePowerConsumption(self);
        if (!pet_lib.findMaster(self))
        {
            messageTo(self, "handleLostMaster", null, 1800, false);
        }
        if (hasScript(self, "ai.droid"))
        {
            detachScript(self, "ai.droid");
        }
        // Only start follow if we are not already following this master.
        // makePet already issued petFollow; a second stop+follow freezes mounts.
        if (!(ai_lib.isFollowing(self) && ai_lib.getFollowTarget(self) == master))
        {
            pet_lib.petFollow(self, master);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLostMaster(obj_id self, dictionary params) throws InterruptedException
    {
        if (!pet_lib.findMaster(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master) && exists(master))
        {
            pet_lib.removeFromPetList(self);
        }
        removeObjVar(self, "ai.pet");
        if (!ai_lib.isAiDead(self))
        {
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_WANDER);
        }
        setOwner(utils.getInventoryContainer(self), obj_id.NULL_ID);
        setObjVar(self, "alreadyTrained", true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "pet.controlDestroyed") || !isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            pet_lib.removeFromPetList(self);
        }
        boolean isFactionPet = (ai_lib.isNpc(self) || ai_lib.aiGetNiche(self) == NICHE_VEHICLE || ai_lib.isAndroid(self));
        obj_id petControlDevice = callable.getCallableCD(self);
        if (hasObjVar(self, battlefield.VAR_CONSTRUCTED) || (isFactionPet && ai_lib.aiIsDead(self)))
        {
            if (isIdValid(petControlDevice))
            {
                messageTo(petControlDevice, "handleFlagDeadCreature", null, 0, false);
            }
        }
        if (isIdValid(petControlDevice) && petControlDevice.isLoaded())
        {
            obj_id currentPet = callable.getCDCallable(petControlDevice);
            if (isIdValid(currentPet) && currentPet == self)
            {
                pet_lib.savePetInfo(self, petControlDevice);
                setObjVar(petControlDevice, "pet.timeStored", getGameTime());
                callable.setCDCallable(petControlDevice, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!pet_lib.findMaster(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ai.conversing"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            setHomeLocation(self, getLocation(master));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(BARK_TRIGGER_VOLUME))
        {
            if (!isPlayer(breacher) || !isIdValid(breacher))
            {
                return SCRIPT_CONTINUE;
            }
            if (!utils.hasScriptVar(self, CAN_BARK))
            {
                utils.setScriptVar(self, CAN_BARK, TRUE);
            }
            if (TRUE == utils.getIntScriptVar(self, CAN_BARK))
            {
                utils.setScriptVar(self, CAN_BARK, FALSE);
                messageTo(self, "msgMerchantBark", null, 1.0f, false);
            }
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME) && breacher == getMaster(self))
        {
            chat.setGoodMood(self);
            ai_lib.barkString(self, "hi_nice");
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME) && breacher == getMaster(self))
        {
            chat.setGoodMood(self);
            ai_lib.barkString(self, "bye_nice");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowWaiting(obj_id self, obj_id target) throws InterruptedException
    {
        pet_lib.validateFollowTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowMoving(obj_id self, obj_id target) throws InterruptedException
    {
        if (getLocomotion(self) != LOCOMOTION_RUNNING)
        {
            
        }
        setMovementRun(self);
        pet_lib.validateFollowTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (newBehavior <= oldBehavior)
        {
            if (doCalmerBehavior(self, newBehavior, oldBehavior) == SCRIPT_CONTINUE)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
        else 
        {
            if (doAgitatedBehavior(self, newBehavior, oldBehavior) == SCRIPT_CONTINUE)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
    }
    public int doCalmerBehavior(obj_id npc, int newBehavior, int oldBehavior) throws InterruptedException
    {
        switch (newBehavior)
        {
            case BEHAVIOR_CALM:
            break;
            case BEHAVIOR_ALERT:
            break;
            case BEHAVIOR_THREATEN:
            break;
            case BEHAVIOR_FLEE:
            break;
            case BEHAVIOR_PANIC:
            break;
            case BEHAVIOR_ATTACK:
            break;
            case BEHAVIOR_FRENZY:
            break;
            default:
            break;
        }
        return SCRIPT_OVERRIDE;
    }
    public int doAgitatedBehavior(obj_id npc, int newBehavior, int oldBehavior) throws InterruptedException
    {
        switch (newBehavior)
        {
            case BEHAVIOR_CALM:
            break;
            case BEHAVIOR_ALERT:
            break;
            case BEHAVIOR_THREATEN:
            break;
            case BEHAVIOR_FLEE:
            break;
            case BEHAVIOR_PANIC:
            break;
            case BEHAVIOR_ATTACK:
            break;
            case BEHAVIOR_FRENZY:
            break;
            default:
            break;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "pet.combatEnded", getGameTime());
        // Stay put while incapacitated / dead — do not path home or restore follow
        if (isIncapacitated(self) || isDead(self) || ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "ai.pet.staying"))
        {
            obj_id master = getMaster(self);
            if (isIdValid(master) && master.isLoaded())
            {
                aiSetHomeLocation(self, getLocation(master));
            }
            else
            {
                aiSetHomeLocation(self, getLocation(self));
            }
            // Always schedule follow resume from the pet script itself.
            // creature_combat also does this when present, but not all pets
            // reliably keep that script; postCombatPathHome is idempotent.
            utils.removeScriptVar(self, "pet.postCombatAttempts");
            messageTo(self, "postCombatPathHome", null, 6, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePetTargetKilled(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isMonster(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isNpcPet(self) || pet_lib.isDroidPet(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasSkill(master, "outdoors_creaturehandler_novice"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id defender = params.getObjId("defender");
        if (!isIdValid(defender))
        {
            return SCRIPT_CONTINUE;
        }
        if (getDistance(self, defender) > 30.0f)
        {
            return SCRIPT_CONTINUE;
        }
        int defLevel = ai_lib.getLevel(defender);
        int level = ai_lib.getLevel(self);
        int adultLevel = pet_lib.getAdultLevel(self);
        if (level < (adultLevel / 2))
        {
            level = (adultLevel / 2);
        }
        int amount = (defLevel - level) + 10;
        if (level > (defLevel * 2))
        {
            amount = 1;
        }
        else if (amount < 1)
        {
            amount = 1;
        }
        else 
        {
            if (level < 5)
            {
                amount *= 7;
            }
            else if (level < 10)
            {
                amount *= 10;
            }
            else if (level < 15)
            {
                amount *= 13;
            }
            else 
            {
                amount *= 20;
            }
        }
        if (amount > 2000)
        {
            amount = 2000;
        }
        int numPets = pet_lib.countPetsOfType(master, pet_lib.getPetType(self));
        if (numPets > 1)
        {
        amount = amount / numPets;
        }
        xp.grant(master, xp.CREATUREHANDLER, amount);
        // Pre-CU pet growth — same amount drives stage 1→10 on the PCD.
        if (isIdValid(callable.getCallableCD(self)))
        {
            pet_lib.grantPetGrowthXp(self, amount);
        }
        return SCRIPT_CONTINUE;
    }
    public int lairThreatened(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        utils.setScriptVar(self, "recapTimer", getGameTime());
        if (pet_lib.isPet(attacker))
        {
            attacker = getMaster(attacker);
        }
        utils.setScriptVar(self, "killer", attacker);
        xp.cleanupCreditForKills();
        obj_id master = getMaster(self);
        int petRestriction = getIntObjVar(self, "pet.petRestriction");
        if (petRestriction == 1)
        {
            reallyKill(self);
            return SCRIPT_CONTINUE;
        }
        if ((master.isLoaded()) && (isIdValid(master)))
        {
            if (pet_lib.isDroidPet(self) || pet_lib.isCombatDroid(self))
            {
//                sendSystemMessageTestingOnly(master, "TAMEDEBUG: CALLSITE storePet from pet.OnIncapacitated (droid)");
                pet_lib.storePet(self, master);
                sendSystemMessage(master, pet_lib.SID_SYS_PACKED_DROID);
                return SCRIPT_CONTINUE;
            }
        }
        if (isIdValid(master) && isDead(master))
        {
            pet_lib.killPet(self);
            return SCRIPT_CONTINUE;
        }
        else if (!pet_lib.findMaster(self))
        {
            pet_lib.killPet(self);
            return SCRIPT_CONTINUE;
        }
        if ((!master.isLoaded()) || getDistance(self, master) > combat_engine.getMaxCombatRange())
        {
            pet_lib.killPet(self);
            return SCRIPT_CONTINUE;
        }
        boolean isFactionPet = (ai_lib.isNpc(self) || ai_lib.aiGetNiche(self) == NICHE_VEHICLE || ai_lib.isAndroid(self));
        if (hasObjVar(self, battlefield.VAR_CONSTRUCTED) || isFactionPet)
        {
            reallyKill(self);
            return SCRIPT_CONTINUE;
        }
        dictionary dictIncap = new dictionary();
        int incapTimer = getGameTime();
        utils.setScriptVar(self, "incapTimer", incapTimer);
        dictIncap.put("incapTimer", incapTimer);
        messageTo(self, "handlePetIncappedDecay", dictIncap, 120, false);
        deltadictionary dict = self.getScriptVars();
        dict.put("pet.regenMultiplier", 0);
        // Hard-zero regen so combat-exit handlers cannot stand the pet back up
        setRegenRate(self, HEALTH, 0);
        setRegenRate(self, ACTION, 0);
        stop(self);
        return SCRIPT_CONTINUE;
    }
    public int handlePetIncappedDecay(obj_id self, dictionary params) throws InterruptedException
    {
        int incapTimer = utils.getIntScriptVar(self, "incapTimer");
        int messageTimer = params.getInt("incapTimer");
        if (incapTimer != messageTimer)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            // Flag PCD dead so Call is blocked until a revive path clears pet.isDead
            obj_id petControlDevice = callable.getCallableCD(self);
            if (isIdValid(petControlDevice))
            {
                messageTo(petControlDevice, "handleFlagDeadCreature", null, 0, false);
            }
            reallyKill(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRecapacitated(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "pet::OnRecapacitated() self(" + self + ":" + getName(self) + ") master(" + getMaster(self) + ":" + getName(getMaster(self)) + ")");
        debugSpeakMsgc(aiLoggingEnabled(self), self, "OnRecapacitated");
        utils.removeScriptVar(self, "recapTimer");
        utils.removeScriptVar(self, "incapTimer");
        float healthRegen = getMaxAttrib(self, HEALTH) / 10.0f;
        float actionRegen = getMaxAttrib(self, ACTION) / 10.0f;
        if (healthRegen < 1.0f)
        {
            healthRegen = 1.0f;
        }
        if (actionRegen < 1.0f)
        {
            actionRegen = 1.0f;
        }
        setRegenRate(self, HEALTH, (int)healthRegen);
        setRegenRate(self, ACTION, (int)actionRegen);
        deltadictionary dict = self.getScriptVars();
        dict.put("pet.regenMultiplier", 1);
        if (!utils.hasScriptVar(self, "ai.pet.staying"))
        {
            obj_id master = getMaster(self);
            if (isIdValid(master) && master.isLoaded())
            {
                pet_lib.doFollowCommand(self, master);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isAiDead(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!pet_lib.isSpeakingPet(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (speaker != getMaster(self))
        {
            return SCRIPT_OVERRIDE;
        }
        chat.setGoodMood(self);
        String myStringFile = STRING_FILE;
        if (hasObjVar(self, "ai.diction"))
        {
            String myDiction = getStringObjVar(self, "ai.diction");
            if (myDiction != null && !myDiction.equals("townperson"))
            {
                myStringFile = STRING_FILE + "_" + myDiction;
            }
        }
        string_id greeting = new string_id(myStringFile, "start_convo_1");
        string_id response[] = new string_id[4];
        response[0] = new string_id(myStringFile, "command_reply_1");
        if (!group.inSameGroup(self, speaker))
        {
            response[1] = new string_id(myStringFile, "command_group_1");
        }
        else 
        {
            response[1] = new string_id(myStringFile, "command_group_2");
        }
        response[2] = new string_id(myStringFile, "command_release");
        response[3] = new string_id(myStringFile, "command_clear_patrol_points");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        String myStringFile = STRING_FILE;
        if (hasObjVar(self, "ai.diction"))
        {
            String myDiction = getStringObjVar(self, "ai.diction");
            if (myDiction != null && !myDiction.equals("townperson"))
            {
                myStringFile = STRING_FILE + "_" + myDiction;
            }
        }
        if ((response.getAsciiId()).equals("command_reply_1"))
        {
            string_id message = new string_id(myStringFile, "start_convo_2");
            string_id[] nextResponse;
            boolean addCombatOption = true;
            if (pet_lib.isDroidPet(self) && !pet_lib.isCombatDroid(self))
            {
                nextResponse = new string_id[4];
                addCombatOption = false;
            }
            else 
            {
                nextResponse = new string_id[5];
            }
            nextResponse[0] = new string_id(myStringFile, "move_commands");
            nextResponse[1] = new string_id(myStringFile, "patrolling_commands");
            nextResponse[2] = new string_id(myStringFile, "formation_commands");
            nextResponse[3] = new string_id(myStringFile, "friendship_commands");
            if (addCombatOption)
            {
                nextResponse[4] = new string_id(myStringFile, "combat_commands");
            }
            npcSpeak(player, message);
            npcSetConversationResponses(player, nextResponse);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("move_commands"))
        {
            string_id message = new string_id(myStringFile, "which_command");
            if (pet_lib.isRepairDroid(self))
            {
                string_id nextResponse[] = new string_id[4];
                nextResponse[0] = new string_id(myStringFile, "command_stay");
                nextResponse[1] = new string_id(myStringFile, "command_follow");
                nextResponse[2] = new string_id(myStringFile, "command_follow_other");
                nextResponse[3] = new string_id(myStringFile, "menu_repair_other");
                npcSpeak(player, message);
                npcSetConversationResponses(player, nextResponse);
            }
            else 
            {
                string_id nextResponse[] = new string_id[3];
                nextResponse[0] = new string_id(myStringFile, "command_stay");
                nextResponse[1] = new string_id(myStringFile, "command_follow");
                nextResponse[2] = new string_id(myStringFile, "command_follow_other");
                npcSpeak(player, message);
                npcSetConversationResponses(player, nextResponse);
            }
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("combat_commands"))
        {
            string_id message = new string_id(myStringFile, "which_command");
            string_id nextResponse[] = new string_id[2];
            nextResponse[0] = new string_id(myStringFile, "command_attack");
            nextResponse[1] = new string_id(myStringFile, "command_guard");
            npcSpeak(player, message);
            npcSetConversationResponses(player, nextResponse);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("patrolling_commands"))
        {
            string_id message = new string_id(myStringFile, "which_command");
            string_id nextResponse[] = new string_id[3];
            nextResponse[0] = new string_id(myStringFile, "command_patrol");
            nextResponse[1] = new string_id(myStringFile, "command_get_patrol_point");
            nextResponse[2] = new string_id(myStringFile, "command_clear_patrol_points");
            npcSpeak(player, message);
            npcSetConversationResponses(player, nextResponse);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("formation_commands"))
        {
            string_id message = new string_id(myStringFile, "which_command");
            string_id nextResponse[] = new string_id[2];
            nextResponse[0] = new string_id(myStringFile, "command_assume_formation_1");
            nextResponse[1] = new string_id(myStringFile, "command_assume_formation_2");
            npcSpeak(player, message);
            npcSetConversationResponses(player, nextResponse);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("friendship_commands"))
        {
            string_id message = new string_id(myStringFile, "which_command");
            string_id nextResponse[] = new string_id[2];
            nextResponse[0] = new string_id(myStringFile, "command_transfer");
            nextResponse[1] = new string_id(myStringFile, "command_friend");
            npcSpeak(player, message);
            npcSetConversationResponses(player, nextResponse);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("command_group_1"))
        {
            pet_lib.doCommandNum(self, pet_lib.COMMAND_GROUP, player);
        }
        else if ((response.getAsciiId()).equals("command_group_2"))
        {
            pet_lib.doCommandNum(self, pet_lib.COMMAND_GROUP, player);
        }
        else if ((response.getAsciiId()).equals("command_clear_patrol_points"))
        {
            pet_lib.doCommandNum(self, pet_lib.COMMAND_CLEAR_PATROL_POINTS, player);
        }
        else if ((response.getAsciiId()).equals("command_release"))
        {
            pet_lib.doCommandNum(self, pet_lib.COMMAND_RELEASE, player);
        }
        else if ((response.getAsciiId()).equals("command_stay"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_STAY);
        }
        else if ((response.getAsciiId()).equals("command_follow"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_FOLLOW);
        }
        else if ((response.getAsciiId()).equals("command_follow_other"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_FOLLOW_OTHER);
        }
        else if ((response.getAsciiId()).equals("menu_repair_other") && pet_lib.isRepairDroid(self))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_TRICK_2);
        }
        else if ((response.getAsciiId()).equals("command_guard"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_GUARD);
        }
        else if ((response.getAsciiId()).equals("command_friend"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_FRIEND);
        }
        else if ((response.getAsciiId()).equals("command_attack"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_ATTACK);
        }
        else if ((response.getAsciiId()).equals("command_patrol"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_PATROL);
        }
        else if ((response.getAsciiId()).equals("command_get_patrol_point"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_SET_PATROL_POINT);
        }
        else if ((response.getAsciiId()).equals("command_assume_formation_1"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_ASSUME_FORMATION_1);
        }
        else if ((response.getAsciiId()).equals("command_assume_formation_2"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_ASSUME_FORMATION_2);
        }
        else if ((response.getAsciiId()).equals("command_transfer"))
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_TRANSFER);
        }
        npcEndConversation(player);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        if (hasObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_IDENTIFIER) && !hasObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_REHUED))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, PCOLOR);
        }
        if (getMountsEnabled())
        {
            if (pet_lib.canMount(self, player))
            {
                debugServerConsoleMsg(player, "+++ pet.onObjectMenuRequest +++ SERVER_PET_MOUNT menu object ADDED to request Menu");
                mi.addRootMenu(menu_info_types.SERVER_PET_MOUNT, new string_id(MENU_FILE, "menu_mount"));
            }
            if (pet_lib.isMountedOnCreatureQueried(self, player))
            {
                debugServerConsoleMsg(player, "+++ pet.onObjectMenuRequest +++ SERVER_PET_DISMOUNT menu object ADDED to request Menu");
                mi.addRootMenu(menu_info_types.SERVER_PET_DISMOUNT, new string_id(MENU_FILE, "menu_dismount"));
            }
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        int opt_menu = 0;
        String name = getCreatureName(self);
        if (name.equals("tutorial_droid"))
        {
            handleTutorialDroidSetUp(player, mi, self);
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isDroidPet(self))
        {
            opt_menu = mi.addRootMenu(menu_info_types.VOTE, new string_id(MENU_FILE, "droid_options"));
        }
        if (!hasObjVar(self, battlefield.VAR_CONSTRUCTED) && !ai_lib.isInCombat(player))
        {
            if (pet_lib.isDroidPet(self))
            {
                mi.addSubMenu(opt_menu, menu_info_types.PET_STORE, new string_id(MENU_FILE, "menu_store"));
            }
            else 
            {
                if (!pet_lib.isMountedOnCreatureQueried(self, player))
                {
                    mi.addRootMenu(menu_info_types.PET_STORE, new string_id(MENU_FILE, "menu_store"));
                }
            }
        }
        if (isIncapacitated(self) && !ai_lib.isInCombat(player))
        {
            mi.addRootMenu(menu_info_types.ITEM_ACTIVATE, new string_id(MENU_FILE, "awaken"));
        }
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.hasContainer(self))
        {
            mi.addSubMenu(opt_menu, menu_info_types.SERVER_PET_OPEN, new string_id(MENU_FILE, "menu_command_open"));
        }
        if (pet_lib.hasDatapad(self))
        {
            mi.addSubMenu(opt_menu, menu_info_types.SERVER_PET_DPAD, new string_id(MENU_FILE, "menu_dpad"));
        }
        if (pet_lib.isDroidPet(self) && !pet_lib.isNewPlayerHelperDroid(self))
        {
            mi.addSubMenu(opt_menu, menu_info_types.PET_FEED, new string_id(MENU_FILE, "menu_recharge"));
        }
        int manageSkillMod = 100;
        if (!pet_lib.isDroidPet(self))
        {
            manageSkillMod = getSkillStatisticModifier(player, "private_creature_management");
        }
        int empathySkillMod = 100;
        if (!pet_lib.isDroidPet(self))
        {
            empathySkillMod = getSkillStatisticModifier(player, "private_creature_empathy");
        }
        int trainingSkillMod = 100;
        if (!pet_lib.isDroidPet(self))
        {
            trainingSkillMod = getSkillStatisticModifier(player, "private_creature_training");
        }
        int mnu = 0;
        if (!pet_lib.isNewPlayerHelperDroid(self))
        {
            if (pet_lib.isDroidPet(self))
            {
                mnu = mi.addRootMenu(menu_info_types.PET_COMMAND, new string_id(MENU_FILE, "menu_command_droid"));
            }
            else 
            {
                if (!hasSkill(player, "outdoors_creaturehandler_novice"))
                {
                    return SCRIPT_CONTINUE;
                }
                mnu = mi.addRootMenu(menu_info_types.PET_COMMAND, new string_id(MENU_FILE, "menu_command"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        if (item == menu_info_types.PET_STORE)
        {
            if (pet_lib.isMountedOnCreatureQueried(self, player))
            {
                sendSystemMessage(player, SID_MUST_DISMOUNT);
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, battlefield.VAR_CONSTRUCTED) && !ai_lib.isInCombat(player))
            {
                if (!pet_lib.wasInCombatRecently(self, player, true))
                {
//                    sendSystemMessageTestingOnly(player, "TAMEDEBUG: CALLSITE storePet from pet.OnObjectMenuSelect PET_STORE");
                    pet_lib.storePet(self, player);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_ACTIVATE)
        {
            if (isDead(self))
            {
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "awakenPet", null, 0.0f, false);
        }
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isNpcPet(self))
        {
            return SCRIPT_CONTINUE;
        }
        String creature_name = getName(self);
        if (item == menu_info_types.SERVER_PET_MOUNT)
        {
            debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ SERVER_PET_MOUNT menu object selected");
            if (getMountsEnabled())
            {
                debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ getMountsEneabled returnted TRUE");
                if (pet_lib.canMount(self, player))
                {
                    debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ pet_lib.canMount(self,player) returned TRUE");
                    queueCommand(player, (-536363215), self, creature_name, COMMAND_PRIORITY_FRONT);
                    debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ just attempted to Enqueue MOUNT command");
                }
            }
            else 
            {
                debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ getMountsEneabled returnted FALSE");
            }
        }
        else if (item == menu_info_types.SERVER_PET_DISMOUNT)
        {
            debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ SERVER_PET_DISMOUNT menu object selected");
            if (getMountsEnabled())
            {
                debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ have selected to DISMOUNT and getMountsEnabled has returned true, so proceed");
                if (pet_lib.isMountedOnCreatureQueried(self, player))
                {
                    debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ pet_lib.isMountedOnCreatureQueried has returned true, so we're going to enqueue a dismount command");
                    queueCommand(player, (117012717), self, creature_name, COMMAND_PRIORITY_FRONT);
                    debugServerConsoleMsg(player, "+++ pet.onObjectMenuSelect +++ dismount command was just enqueued");
                }
            }
        }
        if (item == menu_info_types.VOTE)
        {
            if (!pet_lib.isNewPlayerHelperDroid(self))
            {
                pet_lib.feedPet(self, player);
            }
            else 
            {
                sendSystemMessage(player, SID_HELPER_DEFAULT_REMINDER);
            }
        }
        if (ai_lib.isInCombat(player) || ai_lib.isAiDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.PET_FEED)
        {
            pet_lib.feedPet(self, player);
        }
        else if (item == menu_info_types.SERVER_PET_OPEN)
        {
            if (pet_lib.hasContainer(self))
            {
                pet_lib.openPetContainer(self, player);
            }
        }
        else if (item == menu_info_types.SERVER_PET_DPAD)
        {
            if (pet_lib.hasDatapad(self))
            {
                pet_lib.openPetDatapad(self, player);
            }
        }
        else if (item == menu_info_types.PET_COMMAND)
        {
            pet_lib.openLearnCommandSui(self, player);
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_IDENTIFIER) && !hasObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_REHUED))
            {
                sui.colorize(self, player, self, hue.INDEX_1, "handlePrimaryColorize");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePrimaryColorize(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pcd = callable.getCallableCD(self);
        if (!isValidId(pcd))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            setObjVar(pcd, beast_lib.OBJVAR_BEAST_HUE, idx);
            hue.setColor(self, "/private/index_color_1", idx);
            setObjVar(pcd, beast_lib.OBJVAR_OLD_PET_REHUED, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLearnCommandDialog(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            utils.removeScriptVarTree(self, "ai.learnCommand");
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id pet = utils.getObjIdScriptVar(self, "ai.learnCommand.pet");
        Vector commandIndexList = utils.getResizeableIntArrayScriptVar(pet, "ai.learnCommand.commandIndexList");
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_OK:
            
            {
                if (idx > -1)
                {
                    int cmdIndex = (Integer) commandIndexList.get(idx);
                    pet_lib.learnPetCommand(pet, cmdIndex);
                }
                break;
            }
            case sui.BP_CANCEL:
            
            {
                utils.removeScriptVarTree(self, "ai.learnCommand");
                return SCRIPT_CONTINUE;
            }
        }
        utils.removeScriptVarTree(self, "ai.learnCommand");
        return SCRIPT_CONTINUE;
    }
    public int handleLearnCommandUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            utils.removeScriptVarTree(self, "ai.learnCommand");
            return SCRIPT_CONTINUE;
        }
        obj_id pet = utils.getObjIdScriptVar(self, "ai.learnCommand.pet");
        int pid = utils.getIntScriptVar(self, "ai.learnCommand.pid");
        int idx = sui.getListboxSelectedRow(params);
        Vector commandIndexList = utils.getResizeableIntArrayScriptVar(pet, "ai.learnCommand.commandIndexList");
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int command = (Integer) commandIndexList.get(idx);
        String prompt = utils.packStringId(new string_id("pet/pet_ability", "learn_command_header"));
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id master, String text) throws InterruptedException
    {
        pet_lib.processSpeech(self, master, text);
        return SCRIPT_CONTINUE;
    }
    public int handleTellPet(obj_id self, dictionary params) throws InterruptedException
    {
        String text = params.getString("text");
        obj_id master = params.getObjId("master");
        if (isIdValid(master))
        {
            if (text != null && !text.equals(""))
            {
                pet_lib.processSpeech(self, master, text);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGroupInvite(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (-1449236473), null, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int awakenPet(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "recapTimer");
        utils.removeScriptVar(self, "incapTimer");
        if (getAttrib(self, HEALTH) < 1)
        {
            setAttrib(self, HEALTH, 1);
        }
        // Restore regen that OnIncapacitated zeroed
        float healthRegen = getMaxAttrib(self, HEALTH) / 10.0f;
        float actionRegen = getMaxAttrib(self, ACTION) / 10.0f;
        if (healthRegen < 1.0f)
        {
            healthRegen = 1.0f;
        }
        if (actionRegen < 1.0f)
        {
            actionRegen = 1.0f;
        }
        setRegenRate(self, HEALTH, (int)healthRegen);
        setRegenRate(self, ACTION, (int)actionRegen);
        deltadictionary dict = self.getScriptVars();
        dict.put("pet.regenMultiplier", 1);
        // Resume follow after recovery
        if (!utils.hasScriptVar(self, "ai.pet.staying") && !ai_lib.isInCombat(self))
        {
            messageTo(self, "postCombatPathHome", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int postCombatPathHome(obj_id self, dictionary params) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "pet::postCombatPathHome() self(" + self + ":" + getName(self) + ") master(" + getMaster(self) + ":" + getName(getMaster(self)) + ")");
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self) || isDead(self) || ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "ai.pet.staying"))
        {
            return SCRIPT_CONTINUE;
        }
        // Both pet.java's OnExitedCombat and creature_combat.java's OnExitedCombat
        // fire on the same real combat-exit event and each independently call
        // this method — two competing native follow-attach sequences landing
        // back-to-back can corrupt the follow state machine (pet freezes at
        // loco=0 permanently). Debounce: skip the redundant duplicate call.
        // NOTE: this check used to be gated on !isInCombat(self), which meant
        // it did nothing to stop creature_combat.java's doCombatFrame() from
        // spamming this method every combat tick whenever the pet is nominally
        // still in combat but has no valid target (its own unthrottled
        // messageTo call in the !isIdValid(target) branch). That produced the
        // observed attempts=20 spam with no fresh OnExitedCombat in between,
        // and left the pet jittering in place — stuck re-entering the combat
        // retry block every tick instead of ever reaching real follow-restore.
        // Throttle applies regardless of combat state now.
        {
            int now = getGameTime();
            int lastRun = utils.getIntScriptVar(self, "pet.lastPathHomeRun");
            if (lastRun > 0 && (now - lastRun) < 2)
            {
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "pet.lastPathHomeRun", now);
        }
        // Combat flag can lag after the last kill. Retry a few times instead of
        // giving up forever (that left guarded pets frozen until a manual Follow).
        if (ai_lib.isInCombat(self))
        {
            int attempts = utils.getIntScriptVar(self, "pet.postCombatAttempts");
            if (attempts < 20)
            {
                // Force combat clear after a few tries — flag can lag after last kill.
                if (attempts >= 3)
                {
                    utils.removeScriptVar(self, "ai.combat.target");
                    utils.setScriptVar(self, "petIgnoreAttacks", getGameTime());
                    stopCombat(self);
                }
                utils.setScriptVar(self, "pet.postCombatAttempts", attempts + 1);
                messageTo(self, "postCombatPathHome", null, 2, false);
            }
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, "pet.postCombatAttempts");
        obj_id guardTarget = utils.getObjIdScriptVar(self, "ai.pet.guarding");
        obj_id master = getMaster(self);
        obj_id followTarget = master;
        if (isIdValid(followTarget) && followTarget.isLoaded())
        {
            boolean wasGuarding = isIdValid(guardTarget);
            // Do NOT call stop() here — this runs right after combat, and
            // stop() leaves mounts/pets with isFollowing=true but loco=0
            // forever (same failure mode petFollow()'s fromCombat guard
            // exists to avoid — but that guard only protects petFollow's
            // OWN stop() call, not this one made a level up, before it).
            utils.setScriptVar(self, "pet.combatEnded", getGameTime());
            pet_lib.doFollowCommand(self, followTarget);
            // Removed the external pathTo() nudge that used to sit here.
            // pathTo() is a one-shot order to a SNAPSHOT location, not a
            // moving target — issuing it here (on top of the nudge
            // petFollow() already does internally, WITH a live distance
            // check, right after aiFollow()) caused the pet to path to
            // wherever the master was standing at that instant, arrive,
            // and then sit idle forever once that one-shot order completed
            // — even though real continuous follow had just been attached.
            // pet_lib.doFollowCommand() -> petFollow() already handles the
            // "still standing, still in-band" nudge safely; do not duplicate it.
            utils.removeScriptVar(self, "pet.forceFollowPulses");
            messageTo(self, "handleForceFollow", null, 1.5f, false);
            if (wasGuarding)
            {
                utils.setScriptVar(self, "ai.pet.guarding", guardTarget);
                setWantSawAttackTriggers(self, true);
            }
        }
        else if (hasObjVar(self, "ai.inFormation"))
        {
            ai_lib.resumeFormationFollowing(self);
        }
        else
        {
            stop(self);
        }
        checkForWounds(self);
        return SCRIPT_OVERRIDE;
    }
public int handleForceFollow(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self) || isIncapacitated(self) || isDead(self) || ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "ai.pet.staying") || ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master) || !master.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        float dist = getDistance(self, master);
        // Only force a fresh native follow attach if the pet isn't already
        // properly following this master. Re-issuing doFollowCommand() (and
        // therefore ai_lib.aiFollow()->follow()) on every pulse, even once the
        // pet has already caught up and is idling in-band, resets the native
        // follow state machine mid-transition and can leave it permanently
        // idle right at the moment it arrives — no further OnFollowWaiting/
        // OnFollowMoving ever fires until a fresh manual Follow command.
        boolean alreadyFollowing = ai_lib.isFollowing(self) && ai_lib.getFollowTarget(self) == master;
        // loco can legitimately read 0 for a single frame while actively
        // following (mid-turn, path recalculation) — reacting to that one
        // frame and forcing a fresh doFollowCommand()/aiFollow() re-attach
        // was corrupting genuinely-working follow (proven: loco=3 for
        // several pulses, one loco=0 blip, re-attach fired, never recovered,
        // pulse budget ran out, pet frozen forever with isFollowing=true).
        // Require two consecutive zero-loco readings before treating it as
        // a real stall.
        boolean locoZeroNow = (getLocomotion(self) == 0 && dist > 3.0f);
        boolean locoZeroLastTime = utils.hasScriptVar(self, "pet.forceFollowLocoZero");
        boolean stuck = !alreadyFollowing || (locoZeroNow && locoZeroLastTime);
        if (locoZeroNow)
        {
            utils.setScriptVar(self, "pet.forceFollowLocoZero", 1);
        }
        else
        {
            utils.removeScriptVar(self, "pet.forceFollowLocoZero");
        }
        if (stuck)
        {
            removeObjVar(self, "ai.persistantFollowing");
            utils.removeScriptVar(self, "ai.pet.staying");
            utils.setScriptVar(self, "pet.combatEnded", getGameTime());
            pet_lib.doFollowCommand(self, master);
            dist = getDistance(self, master);
        }
        int pulses = utils.getIntScriptVar(self, "pet.forceFollowPulses");
        if (pulses < 6 && dist > 3.0f)
        {
            utils.setScriptVar(self, "pet.forceFollowPulses", pulses + 1);
            messageTo(self, "handleForceFollow", null, 1.5f, false);
        }
        else
        {
            utils.removeScriptVar(self, "pet.forceFollowPulses");
            // Start the persistent follow watchdog if it isn't already
            // running (it re-schedules itself indefinitely).
            if (!utils.hasScriptVar(self, "pet.watchdogRunning"))
            {
                utils.setScriptVar(self, "pet.watchdogRunning", 1);
                messageTo(self, "followHeartbeat", null, 1.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int followHeartbeat(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (!isIdValid(master) || isIncapacitated(self) || isDead(self) || ai_lib.isAiDead(self))
        {
            utils.removeScriptVar(self, "pet.watchdogRunning");
            return SCRIPT_CONTINUE;
        }
        // Stop the watchdog cleanly if staying or actively fighting —
        // combat-exit will kick it off again via postCombatPathHome/
        // handleForceFollow when appropriate. Must clear watchdogRunning
        // here too (same as the master-invalid branch above), otherwise
        // the stale flag permanently blocks handleForceFollow from ever
        // re-arming a fresh watchdog loop later.
        if (utils.hasScriptVar(self, "ai.pet.staying") || ai_lib.isInCombat(self))
        {
            utils.removeScriptVar(self, "pet.heartbeatLocoZero");
            utils.removeScriptVar(self, "pet.watchdogRunning");
            return SCRIPT_CONTINUE;
        }
        float dist = getDistance(self, master);
        int loco = getLocomotion(self);

        // Cheap fast-path check every pulse: isFollowing/getFollowTarget/
        // getDistance are lightweight native reads, not worth throttling.
        // Only the actual re-attach below (pet_lib.doFollowCommand) is
        // costly, and that's already gated behind two-consecutive-bad-
        // reading confirmation — so we keep this poll itself always fast
        // instead of trading detection latency for savings that don't
        // really exist at this scale.
        if (ai_lib.isFollowing(self) && ai_lib.getFollowTarget(self) == master && dist <= ai_lib.DEFAULT_FOLLOW_MAX)
        {
            utils.removeScriptVar(self, "pet.heartbeatLocoZero");
            messageTo(self, "followHeartbeat", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }

        // Native continuous follow can silently drop out sometime after a
        // successful re-attach, well outside handleForceFollow's short
        // post-combat pulse burst. This watchdog runs continuously and
        // self-heals using the same validated two-consecutive-zero check
        // (a single loco=0 frame is normal mid-follow; two in a row while
        // still out of band means it actually stopped).
        boolean locoZeroNow = (loco == 0 && dist > 3.0f);
        boolean locoZeroLastTime = utils.hasScriptVar(self, "pet.heartbeatLocoZero");
        if (locoZeroNow && locoZeroLastTime)
        {
            removeObjVar(self, "ai.persistantFollowing");
            utils.setScriptVar(self, "pet.combatEnded", getGameTime());
            pet_lib.doFollowCommand(self, master);
            utils.removeScriptVar(self, "pet.heartbeatLocoZero");
        }
        else if (locoZeroNow)
        {
            utils.setScriptVar(self, "pet.heartbeatLocoZero", 1);
        }
        else
        {
            utils.removeScriptVar(self, "pet.heartbeatLocoZero");
        }
        messageTo(self, "followHeartbeat", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }   
    public void checkForWounds(obj_id pet) throws InterruptedException
    {
    }
    public void managePowerConsumption(obj_id pet) throws InterruptedException
    {
        if (!pet_lib.isNewPlayerHelperDroid(pet))
        {
            messageTo(pet, "handleAbandonment", null, 86400, false);
            if (pet_lib.isDroidPet(pet))
            {
                messageTo(pet, "consumePower", null, 360, false);
            }
        }
    }
    public int handleAbandonment(obj_id self, dictionary params) throws InterruptedException
    {
        int ignoredForDays = getIntObjVar(self, "ai.pet.ignoredForDays");
        ignoredForDays++;
        if (ignoredForDays > 3)
        {
            pet_lib.releasePet(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setObjVar(self, "ai.pet.ignoredForDays", ignoredForDays);
        }
        messageTo(self, "handleAbandonment", null, 86400, false);
        return SCRIPT_CONTINUE;
    }
    public int consumePower(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(self);
        int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
        powerLevel++;
        if (powerLevel == pet_lib.OUT_OF_POWER)
        {
            messageTo(self, "powerWarningText", null, 30, false);
        }
        if (powerLevel < pet_lib.OUT_OF_POWER)
        {
            setObjVar(petControlDevice, "ai.pet.powerLevel", powerLevel);
        }
        else 
        {
            setObjVar(petControlDevice, "ai.pet.powerLevel", (pet_lib.OUT_OF_POWER + 1));
        }
        messageTo(self, "consumePower", null, 360, false);
        return SCRIPT_CONTINUE;
    }
    public int powerWarningText(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(self);
        int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
        if (powerLevel < 10)
        {
            return SCRIPT_CONTINUE;
        }
        showFlyText(self, new string_id("npc_reaction/flytext", "low_power"), 0.75f, colors.DARKORANGE);
        messageTo(self, "powerWarningText", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int resumePetTrick(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId("master");
        int trickNum = params.getInt("trickNum");
        pet_lib.doStupidPetTrick(self, master, trickNum);
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        int pet_food = getIntObjVar(item, "race_restriction");
        if (pet_lib.isCreaturePet(self) && (pet_food == -2) && (player == master))
        {
            food.petEatFood(self, master, item, player);
            return SCRIPT_CONTINUE;
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        obj_id petInv = utils.getInventoryContainer(petControlDevice);
        if (!isIdValid(petInv))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != master)
        {
            return SCRIPT_CONTINUE;
        }
        if (isMob(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isDroidPet(self))
        {
            if (!pet_lib.hasContainer(self))
            {
                return SCRIPT_CONTINUE;
            }
        }
        else if (!ai_lib.isNpc(self) || !ai_lib.isAndroid(self))
        {
            return SCRIPT_CONTINUE;
        }
        putIn(item, petInv);
        return SCRIPT_CONTINUE;
    }
    public int handleMoveToMaster(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            location spawnLoc = new location(getLocation(master));
            spawnLoc.x += rand(-4.0f, +4.0f);
            spawnLoc.z += rand(-4.0f, +4.0f);
            spawnLoc.y = getHeightAtLocation(spawnLoc.x, spawnLoc.z);
            setLocation(self, getLocation(master));
            pathTo(self, spawnLoc);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePackRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id debugMaster = getMaster(self);
//        sendSystemMessageTestingOnly(debugMaster, "TAMEDEBUG: handlePackRequest ENTER self=" + self + " master=" + debugMaster);
        debugServerConsoleMsg(null, "+++ ai.pet.messageHandler handlePackRequest +++ entered HANDLEPACKREQUEST message handler");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id rider = getRiderId(self);
        if (isIdValid(rider))
        {
            boolean dismountSuccess = pet_lib.doDismountNow(rider);
            if (!dismountSuccess)
            {
                LOG("mounts-bug", "pet.messageHandler handlePackRequest(): creature [" + self + "], rider [" + rider + "] failed to dismount, aborting pack request.  This mount probably is in an invalid state now.");
                return SCRIPT_CONTINUE;
            }
        }
        debugServerConsoleMsg(null, "+++ pet.messageHandler handlePackRequest +++ destroying the pet now");
        obj_id petControlDevice = callable.getCallableCD(self);
        utils.setScriptVar(self, "stored", true);
        dictionary messageData = new dictionary();
        messageData.put(MESSAGE_PET_ID, self);
        if (isIdValid(petControlDevice) && petControlDevice.isLoaded() && exists(petControlDevice))
        {
            sendDirtyObjectMenuNotification(petControlDevice);
        }
        if (destroyObject(self))
        {
            messageTo(petControlDevice, "handleRemoveCurrentPet", messageData, 1, false);
        }
        else 
        {
            debugServerConsoleMsg(null, "+++ pet.messageHandler handlePackRequest +++ WARNINGWARNING - FAILED TO DESTROY SELF");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAddToStomach(obj_id self, dictionary params) throws InterruptedException
    {
        int stomach = params.getInt("stomach");
        int vol = params.getInt("vol");
        player_stomach.addToStomach(self, stomach, vol);
        return SCRIPT_CONTINUE;
    }
    public int handleSetColors(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        setColors(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleSetCustomization(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dc = params.getDictionary("dc");
        boolean updatedColors = setColors(self, dc);
        params.remove("dc");
        obj_id tool = params.getObjId("tool");
        if (!isIdValid(tool))
        {
            return SCRIPT_CONTINUE;
        }
        if (updatedColors)
        {
            messageTo(tool, "customizationSuccess", params, 0.0f, false);
        }
        else 
        {
            messageTo(tool, "customizationFailed", params, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean setColors(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return false;
        }
        boolean litmus = true;
        java.util.Enumeration keys = params.keys();
        while (keys.hasMoreElements())
        {
            String var = (String)keys.nextElement();
            int idx = params.getInt(var);
            litmus &= hue.setColor(self, var, idx);
        }
        return litmus;
    }
    public void reallyKill(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            pet_lib.removeFromPetList(self);
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        if (isIdValid(petControlDevice))
        {
            messageTo(petControlDevice, "handleFlagDeadCreature", null, 0, false);
        }
        kill(self);
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master) && master.isLoaded())
        {
            aiSetHomeLocation(self, getLocation(master));
        }
        else 
        {
            aiSetHomeLocation(self, getLocation(self));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "petIgnoreAttacks"))
        {
            int timeStarted = utils.getIntScriptVar(self, "petIgnoreAttacks");
            if (getGameTime() < (timeStarted + 20))
            {
                return SCRIPT_OVERRIDE;
            }
            utils.removeScriptVar(self, "petIgnoreAttacks");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToGroup(obj_id self, obj_id groupId) throws InterruptedException
    {
        if (!hasScript(self, group.SCRIPT_GROUP_MEMBER))
        {
            attachScript(self, group.SCRIPT_GROUP_MEMBER);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovedFromGroup(obj_id self, obj_id groupId) throws InterruptedException
    {
        detachScript(self, group.SCRIPT_GROUP_MEMBER);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id objSource, obj_id objTransferer, obj_id objItem) throws InterruptedException
    {
        if (isPlayer(objItem))
        {
            if (!pet_lib.isGalloping(self))
            {
                int intState = getState(objItem, STATE_COMBAT);
                if (intState > 0)
                {
                    setBaseRunSpeed(self, 6.0f);
                    return SCRIPT_CONTINUE;
                }
            }
            obj_id objControlDevice = callable.getCallableCD(self);
            if (!utils.hasScriptVar(objControlDevice, "mount.intGalloping"))
            {
                pet_lib.setMountedMovementRate(objItem, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id objDestinationContainer, obj_id objTransferer, obj_id objItem) throws InterruptedException
    {
        if (isPlayer(objItem))
        {
            pet_lib.setUnmountedMovementRate(objItem, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id performer, String emote) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (performer != master)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(master))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id petsCurrentRider = getRiderId(self);
        if (isIdValid(petsCurrentRider))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(master) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (getLookAtTarget(master) != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (emote.startsWith("pet") || emote.equals("reassure") || emote.equals("nuzzle") || emote.equals("hug"))
        {
            if (rand(1, 2) == 1)
            {
                stop(self);
                if (ai_lib.canSit(self))
                {
                    if (rand(1, 2) == 1)
                    {
                        ai_lib.aiSetPosture(self, POSTURE_SITTING);
                    }
                    else 
                    {
                        ai_lib.aiSetPosture(self, POSTURE_LYING_DOWN);
                    }
                }
                else if (ai_lib.canLieDown(self))
                {
                    ai_lib.aiSetPosture(self, POSTURE_LYING_DOWN);
                }
            }
            else 
            {
                ai_lib.doAction(self, "happy");
            }
        }
        else if (emote.equals("bonk") || emote.equals("whap") || emote.equals("scold") || emote.equals("bad") || emote.equals("slap"))
        {
            ai_lib.doAction(self, "ashamed");
        }
        else if (emote.equals("pointat") || emote.equals("tap"))
        {
            ai_lib.doAction(self, ai_lib.ACTION_ALERT);
        }
        else if (emote.equals("beckon") || emote.equals("summon"))
        {
            if (rand(1, 2) == 1)
            {
                ai_lib.doAction(self, "confused");
            }
            else 
            {
                pet_lib.doFollowCommand(self, master);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePetTradeInStore(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pet = params.getObjId("pet");
        obj_id master = params.getObjId("master");
        obj_id petControlDevice = callable.getCallableCD(pet);
        boolean isMount = params.getBoolean("isMount");
        if (isMount == true)
        {
            setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        }
//        sendSystemMessageTestingOnly(master, "TAMEDEBUG: CALLSITE storePet from pet.handlePetTradeInStore");
        pet_lib.storePet(pet, master);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id master = getMaster(self);
        CustomerServiceLog("Pets", "*Pet DEATH: (" + self + ": " + getName(self) + ") has died. Their master was, (" + master + " sid:" + getPlayerObject(master));
        return SCRIPT_CONTINUE;
    }
    public void handleTutorialDroidSetUp(obj_id player, menu_info mi, obj_id self) throws InterruptedException
    {
        int opt_menu = 0;
        int mnu = 0;
        opt_menu = mi.addRootMenu(menu_info_types.VOTE, new string_id(MENU_FILE, "droid_options"));
        mnu = mi.addRootMenu(menu_info_types.PET_COMMAND, new string_id(MENU_FILE, "menu_command_droid"));
        mi.addSubMenu(opt_menu, menu_info_types.PET_STORE, new string_id(MENU_FILE, "menu_store"));
        mi.addSubMenu(mnu, menu_info_types.PET_GROUP, new string_id(MENU_FILE, "menu_group"));
        mi.addSubMenu(mnu, menu_info_types.PET_STAY, new string_id(MENU_FILE, "menu_stay"));
        mi.addSubMenu(mnu, menu_info_types.PET_FOLLOW, new string_id(MENU_FILE, "menu_follow"));
        return;
    }
    public int receiveCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        if (group.isGrouped(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            String creatureName = params.getString("creatureName");
            params.put("col_faction", dataTableGetString("datatables/mob/creatures.iff", creatureName, "col_faction"));
            params.put("col_faction", dataTableGetInt("datatables/mob/creatures.iff", creatureName, "difficultyClass"));
            messageTo(master, "receiveCreditForKill", params, 0.0f, false);
            // Pre-CU pet growth: XP was never wired — grantPetGrowthXp had zero callers.
            if (isIdValid(callable.getCallableCD(self)))
            {
                obj_id target = params.getObjId("target");
                int defLevel = 1;
                if (isIdValid(target) && exists(target))
                {
                    defLevel = getLevel(target);
                }
                if (defLevel < 1)
                {
                    defLevel = 1;
                }
                int petLevel = getLevel(self);
                if (petLevel < 1)
                {
                    petLevel = 1;
                }
                int amount = (defLevel - petLevel) + 10;
                if (amount < 1)
                {
                    amount = 1;
                }
                if (petLevel > (defLevel * 2))
                {
                    amount = 1;
                }
                amount *= 10;
                if (amount > 2000)
                {
                    amount = 2000;
                }
                pet_lib.grantPetGrowthXp(self, amount);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
