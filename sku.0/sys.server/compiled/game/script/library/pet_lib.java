package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import java.util.Arrays;
import java.util.Vector;
import script.library.ai_lib;
import script.library.beast_lib;
import script.library.buff;
import script.library.callable;
import script.library.camping;
import script.library.chat;
import script.library.colors;
import script.library.consumable;
import script.library.create;
import script.library.group;
import script.library.locations;
import script.library.pclib;
import script.library.player_structure;
import script.library.space_dungeon;
import script.library.space_transition;
import script.library.stealth;
import script.library.utils;

public class pet_lib extends script.base_script
{
    public pet_lib()
    {
    }
    public static final int CUSTOMIZATION_COUNT = 2000;
    public static final int MAX_NUMBER_OF_FRIENDS = 5;
    public static final int MAX_WAYPOINTS = 10;
    public static final int COMMAND_FOLLOW = 0;
    public static final int COMMAND_STAY = 1;
    public static final int COMMAND_GUARD = 2;
    public static final int COMMAND_FRIEND = 3;
    public static final int COMMAND_ATTACK = 4;
    public static final int COMMAND_PATROL = 5;
    public static final int COMMAND_SET_PATROL_POINT = 6;
    public static final int COMMAND_CLEAR_PATROL_POINTS = 7;
    public static final int COMMAND_ASSUME_FORMATION_1 = 8;
    public static final int COMMAND_ASSUME_FORMATION_2 = 9;
    public static final int COMMAND_TRANSFER = 10;
    public static final int COMMAND_RELEASE = 11;
    public static final int COMMAND_TRICK_1 = 12;
    public static final int COMMAND_TRICK_2 = 13;
    public static final int COMMAND_TRICK_3 = 14;
    public static final int COMMAND_TRICK_4 = 15;
    public static final int COMMAND_GROUP = 16;
    public static final int COMMAND_FOLLOW_OTHER = 17;
    public static final int COMMAND_SPECIALATTACK_ONE = 18;
    public static final int COMMAND_SPECIALATTACK_TWO = 19;
    public static final int COMMAND_RANGED_ATTACK = 20;
    public static final int COMMAND_TARGET_HARVEST = 21;
    public static final int COMMAND_TRAP_THROW_ONE = 22;
    public static final int NUM_COMMANDS = 23;
    public static final int PET_TYPE_NON_AGGRO = 0;
    public static final int PET_TYPE_AGGRO = 1;
    public static final int PET_TYPE_NPC = 2;
    public static final int PET_TYPE_DROID = 3;
    public static final int PET_TYPE_FAMILIAR = 4;
    public static final int PET_TYPE_MOUNT = 5;
    public static final int MAX_NPC_PETS = 1;
    public static final int MAX_DROID_PETS = 1;
    public static final int MAX_FAMILIAR_PETS = 1;
    public static final int MAX_MOUNT_PETS = 1;
    public static final int MAX_UNTRAINED_PETS = 1;
    public static final int MAX_UNTRAINED_STORED_PETS = 2;
    public static final int MAX_NONCH_PET_LEVEL = 10;
    public static final int MAX_REGEN_STAT = 4000;
    public static final int MAX_PET_LEVELS = 70;
    public static final int MAX_PET_LEVELS_ABOVE_CALLER = 5;
    public static final int MAX_PET_MOUNT_DISTANCE = 4;
    public static final int MAX_PET_MOUNT_OFFER_DISTANCE = 32;
    public static final int MAX_VITALITY_LOSS = 100;
    public static final int COMBATMODE_NONE = 0;
    public static final int COMBATMODE_DEFAULT = 1;
    public static final int COMBATMODE_EQUIPPED = 2;
    public static final int COMBATMODE_SPECIAL = 3;
    public static final int PET_ABILITY_INNATE = 1;
    public static final int PET_ABILITY_TRIGGERED = 2;
    public static final int PET_ABILITY_ACTION = 3;
    public static final float XP_CURVE_STEEPNESS = 2.2f;
    public static final float XP_LOW_END_REWARD = 100.0f;
    public static final String BATTERY = "object/tangible/droid_battery/battery.iff";
    public static final int OUT_OF_POWER = 100;
    public static final float PET_GROWTH_CYCLE = 43200.0f;
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String PET_ABILITY_TABLE = "datatables/pet/pet_ability.iff";
    public static final String WILD_ABILITY_TABLE = "datatables/pet/wild_ability.iff";
    public static final String MENU_FILE = "pet/pet_menu";
    public static final int CALL_DELAY = 0;
    public static final int BUTTON_MASHING_DELAY = 3;
    public static final int POST_COMBAT_DELAY = 30;
    public static final String COMBAT_ENDED = "player.combatEnded";
    public static final String CALL_FINISH = "player.callFinish";
    public static final int BASE_MAX_TRAP_LOAD = 20;
    public static final String VAR_PALVAR_BASE = "ai.pet.palvar";
    public static final String VAR_PALVAR_VARS = VAR_PALVAR_BASE + ".vars";
    public static final String VAR_PALVAR_CNT = VAR_PALVAR_BASE + ".cnt";
    public static final String[] LIGHTING_EFFECTS = 
    {
        "droid_effect_foam",
        "droid_effect_electric_fog",
        "droid_effect_confetti",
        "droid_effect_mind_bloom",
        "droid_effect_jawa_dance",
        "droid_effect_doves"
    };
    public static final string_id SID_SYS_CANT_TAME = new string_id("pet/pet_menu", "sys_cant_tame");
    public static final string_id SID_SYS_LACK_SKILL = new string_id("pet/pet_menu", "sys_lack_skill");
    public static final string_id SID_MAX_PETS = new string_id("error_message", "too_many_pets");
    public static final string_id SID_PET_DIED_MASTER = new string_id("error_message", "pet_died_master");
    public static final string_id SID_PET_DIED_OTHER = new string_id("error_message", "pet_died_other");
    public static final string_id SID_SYS_CANT_TRICK = new string_id("pet/pet_menu", "sys_cant_trick");
    public static final string_id SID_SYS_LEARN_COMMAND = new string_id("pet/pet_menu", "pet_learn");
    public static final string_id SID_SYS_CANT_LEARN = new string_id("pet/pet_menu", "pet_nolearn");
    public static final string_id SID_SYS_NOT_HANDLER = new string_id("pet/pet_menu", "pet_nothandler");
    public static final string_id SID_SYS_XFER_SUCCESS = new string_id("pet/pet_menu", "pet_transfer_succeed");
    public static final string_id SID_SYS_TOO_MANY_PETS = new string_id("pet/pet_menu", "pet_too_many_pets");
    public static final string_id SID_SYS_TOO_MANY_STORED_PETS = new string_id("pet/pet_menu", "sys_too_many_stored");
    public static final string_id SID_PRIVATE_HOUSE = new string_id("pet/pet_menu", "private_house");
    public static final string_id SID_INVALID_CRAFTED_PET = new string_id("pet/pet_menu", "invalid_crafted_pet");
    public static final string_id SID_SYS_PATROL_ADDED = new string_id("pet/pet_menu", "patrol_added");
    public static final string_id SID_SYS_PATROL_REMOVED = new string_id("pet/pet_menu", "patrol_removed");
    public static final string_id SID_SYS_CANT_CALL = new string_id("pet/pet_menu", "cant_call");
    public static final string_id SID_SYS_CANT_CALL_YET = new string_id("pet/pet_menu", "prose_cant_call_yet");
    public static final string_id SID_SYS_CANT_CALL_LEVEL = new string_id("pet/pet_menu", "cant_call_level");
    public static final string_id SID_SYS_CANT_GENERATE_YET = new string_id("pet/pet_menu", "prose_cant_generate_yet");
    public static final string_id SID_SYS_CALL_DELAY_FINISH_VEHICLE = new string_id("pet/pet_menu", "call_delay_finish_vehicle");
    public static final string_id SID_SYS_CALL_DELAY_FINISH_PET = new string_id("pet/pet_menu", "call_delay_finish_pet");
    public static final string_id MOUNTED_CALL_WARNING = new string_id("pet/pet_menu", "mounted_call_warning");
    public static final string_id SID_SYS_CANT_CALL_ANOTHER_RIDEABLE = new string_id("pet/pet_menu", "cannot_call_another_rideable");
    public static final string_id SID_SYS_CANT_CALL_ANOTHER_FAMILIAR = new string_id("pet/pet_menu", "cannot_call_another_familiar");
    public static final string_id SID_SYS_PACKED_DROID = new string_id("pet/pet_menu", "droid_packed");
    public static final string_id SID_SYS_CANT_CALL_VEHICLE = new string_id("pet/pet_menu", "cant_call_vehicle");
    public static final string_id SID_SYS_VEHICLE_RESTRICTED_SCENE = new string_id("pet/pet_menu", "vehicle_restricted_scene");
    public static final string_id SID_SYS_MOUNT_RESTRICTED_SCENE = new string_id("pet/pet_menu", "mount_restricted_scene");
    public static final string_id SID_SYS_CALL_VEHICLE_DELAY = new string_id("pet/pet_menu", "call_vehicle_delay");
    public static final string_id SID_SYS_CALL_PET_DELAY = new string_id("pet/pet_menu", "call_pet_delay");
    public static final string_id SID_SYS_CALL_MAX_DATAPAD = new string_id("pet/pet_menu", "max_npc_in_datapad");
    public static final string_id SID_PET_HAS_NOTRADE = new string_id("pet/pet_menu", "pet_has_notrade");
    public static final string_id SID_SYS_CANT_MOUNT = new string_id("pet/pet_menu", "cant_mount");
    public static final string_id SID_SYS_CANT_DISMOUNT = new string_id("pet/pet_menu", "cant_dismount");
    public static final String PET_CTRL_DEVICE_TEMPLATE = "object/intangible/pet/pet_control.iff";
    public static final string_id SID_PET_INVITED = new string_id("pet/pet_menu", "pet_invited");
    public static final string_id SID_PET_NO_GROUP_IN_SPACE = new string_id("pet/pet_menu", "pet_no_group_in_space");
    public static final string_id SID_GROWTH_CHOICE_TITLE = new string_id("pet/pet_menu", "mount_growth_title");
    public static final string_id SID_GROWTH_CHOICE_PROMPT = new string_id("pet/pet_menu", "mount_growth_prompt");
    public static final string_id SID_SYS_CANT_MOUNT_VEHICLE_DISABLED = new string_id("pet/pet_menu", "cant_mount_veh_disabled");
    public static final string_id SID_SYS_VEHICLE_DISABLED = new string_id("pet/pet_menu", "veh_disabled");
    public static final String TEMPLATE_PREFIX = "object/mobile/";
    public static final String CREATURE_NAME_FILE = "mob/creature_names";
    public static final String NPC_CUSTOMIZATION_PREFIX = "datatables/npc_customization/";
    public static final String DRESSED_NPC_TABLE = "datatables/npc_customization/dressed_species.iff";
    public static final String MOUNT_VALID_SCALE_RANGE = "datatables/mount/valid_scale_range.iff";
    public static final string_id SID_SYS_CUST_FADING = new string_id("pet/pet_menu", "customization_fading");
    public static final string_id SID_SYS_CUST_GONE = new string_id("pet/pet_menu", "customization_gone");
    public static final string_id SID_SYS_VEH_CUST_FADING = new string_id("pet/pet_menu", "customization_fading_veh");
    public static final string_id SID_SYS_VEH_CUST_GONE = new string_id("pet/pet_menu", "customization_gone_veh");
    public static final string_id SID_MOUNT_STATUS_CODE_ERROR = new string_id("pet/pet_menu", "mount_status_code_error");
    public static final String DROID_COMBAT_TABLE = "datatables/combat/droid_combat_capabilities.iff";
    public static final int MAX_DROID_COMBAT_VALUE = 880;
    public static final String PROBOT_DROID_NAME = "probot";
    public static final String DZ70_DROID_NAME = "dz70_fugitive_tracker_droid";
    public static final string_id SID_SYS_RANGED_ATTACK_TOGGLED = new string_id("pet/pet_menu", "ranged_attack_toggled");
    public static final string_id SID_SYS_RANGED_ATTACK_TOGGLED_OFF = new string_id("pet/pet_menu", "ranged_attack_toggled_off");
    public static final int DEFAULT_DROID_LEVEL = 1;
    public static final String TBL_MOB_STAT_BALANCE = "datatables/mob/stat_balance.iff";
    public static final String TBL_DROID_COMBAT = "datatables/combat/droid_combat_capabilities.iff";
    public static final String DROID_RANGED_WEAPON = "object/weapon/ranged/vehicle/droid_weapon.iff";
    public static final int COMBAT_MODULE_PROTECT_LVL = 500;
    public static final int DEFENSE_MODULE_PROTECT_LVL = 1000;
    public static final int DETONATION_DROID_MIN_DAMAGE = 65;
    public static final int DETONATION_DROID_MAX_DAMAGE = 97;
    public static final String OBJVAR_PET_PATROL_POINTS = "pet.patrolPoints";
    public static final String DROID_HARVEST_ARRAY = "droid.toHarvest";
    public static final String[] MOUNTABILITY_STATUS_CODES = 
    {
        "MSC_CREATURE_MOUNTABLE",
        "MSC_SPECIES_UNMOUNTABLE",
        "MSC_SPECIES_MOUNTABLE_SEATING_CAPACITY_UNSUPPORTED",
        "MSC_SPECIES_MOUNTABLE_SCALE_OUT_OF_RANGE",
        "MSC_SPECIES_MOUNTABLE_MISSING_RIDER_SLOT"
    };
    public static final int HEALTH_DAMAGE = 0;
    public static final int ACTION_DAMAGE = 1;
    public static final int MIND_DAMAGE = 2;
    public static final int HEALTH_WOUND = 3;
    public static final int ACTION_WOUND = 4;
    public static final int MIND_WOUND = 5;
    public static final String FAMILAR_DATA_DATATABLE = "datatables/pet/non_combat_familiar.iff";
    public static void petFollow(obj_id pet, obj_id master) throws InterruptedException
    {
        final float collisionRadius = getObjectCollisionRadius(pet) * 2.0f;
        ai_lib.aiFollow(pet, master, collisionRadius, collisionRadius);
        setMovementRun(pet);
    }
    public static void setUpPetControlDevice(obj_id petControlDevice, obj_id pet) throws InterruptedException
    {
        setObjVar(petControlDevice, "pet.creatureName", ai_lib.getCreatureName(pet));
        callable.setCDCallable(petControlDevice, pet);
        callable.setCallableCD(pet, petControlDevice);
        attachScript(petControlDevice, "ai.pet_control_device");
        setControllerName(petControlDevice, pet);
        int petType = getPetType(pet);
        setObjVar(petControlDevice, "ai.pet.type", petType);
        if (couldPetBeMadeMountable(pet) == MSC_SPECIES_UNMOUNTABLE)
        {
            setObjVar(petControlDevice, "ai.petAdvance.cannotBeMount", true);
        }
        markExpansionPets(petControlDevice, pet);
    }
    public static obj_id makeControlDevice(obj_id master, obj_id pet) throws InterruptedException
    {
        if (callable.hasCallableCD(pet))
        {
            return callable.getCallableCD(pet);
        }
        obj_id datapad = utils.getPlayerDatapad(master);
        if (!isIdValid(datapad))
        {
            return null;
        }
        String creatureName = ai_lib.getCreatureName(pet);
        if (creatureName == null || creatureName.equals(""))
        {
            return null;
        }
        String controlTemplate = "object/intangible/pet/" + utils.dataTableGetString(create.CREATURE_TABLE, creatureName, "template");
        if (!controlTemplate.endsWith(".iff"))
        {
            controlTemplate = pet_lib.PET_CTRL_DEVICE_TEMPLATE;
        }
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        if (!isIdValid(petControlDevice))
        {
            petControlDevice = createObject(pet_lib.PET_CTRL_DEVICE_TEMPLATE, datapad, "");
            if (!isIdValid(petControlDevice))
            {
                sendSystemMessage(master, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
                return null;
            }
        }
        setUpPetControlDevice(petControlDevice, pet);
        if (hasObjVar(pet, "ai.petAbility.wildAbility"))
        {
            String wildAbility = getStringObjVar(pet, "ai.petAbility.wildAbility");
            int[] abilityList = new int[1];
            abilityList[0] = getStringCrc(wildAbility.toLowerCase());
            
            setObjVar(petControlDevice, "ai.petAbility.abilityList", abilityList);
            setObjVar(petControlDevice, "ai.petAbility.wildAbility", wildAbility);
        }
        if (hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
        {
            pet_lib.setPetStatsByGrowth(pet, getIntObjVar(petControlDevice, "ai.petAdvance.growthStage"));
        }
        else 
        {
            pet_lib.setPetStatsByGrowth(pet, 10);
        }
        callable.setCallableCD(pet, petControlDevice);
        return petControlDevice;
    }
    public static void makePet(obj_id pet, obj_id master) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(master) || !exists(pet) || !exists(master))
        {
            return;
        }
        if (!hasScript(master, "ai.pet_master"))
        {
            attachScript(master, "ai.pet_master");
        }
        dictionary params = new dictionary();
        params.put("pet", pet);
        params.put("master", master);
        petFollow(pet, master);
        int petSpecies = ai_lib.aiGetSpecies(pet);
        if (petSpecies == -1)
        {
            return;
        }
        String masterFaction = factions.getFaction(master);
        if (masterFaction != null && (masterFaction.equals("Rebel") || masterFaction.equals("Imperial")))
        {
            setObjVar(pet, factions.FACTION, masterFaction);
        }
        if (!utils.hasScriptVar(pet, "petBeingInitialized") || (callable.hasCallable(master, callable.getCallableType(pet)) && callable.getCallable(master, callable.getCallableType(pet)) != pet))
        {
            return;
        }
        obj_id controlDevice = obj_id.NULL_ID;
        if (callable.hasCallableCD(pet))
        {
            controlDevice = callable.getCallableCD(pet);
        }
        int petType = pet_lib.getPetType(pet);
        setObjVar(pet, "ai.pet.type", petType);
        if (!pet_lib.hasMaxPets(master, petType, pet) || (pet_lib.hasMaxPets(master, petType) && hasObjVar(controlDevice, "ai.pet.trainedMount")))
        {
            controlDevice = makeControlDevice(master, pet);
            ai_lib.setDefaultCalmBehavior(pet, ai_lib.BEHAVIOR_STOP);
            if (!isIdValid(controlDevice) || !exists(controlDevice))
            {
                return;
            }
            pet_lib.addToPetList(master, pet);
            params.put("controlDevice", controlDevice);
            messageTo(pet, "handleAddMaster", params, 0, false);
        }
        else 
        {
            if (callable.hasCallableCD(pet))
            {
                destroyObject(pet);
            }
        }
        if (isIdValid(controlDevice) && hasMountName(controlDevice))
        {
            setName(pet, getMountName(controlDevice));
        }
    }
    public static boolean hasMaster(obj_id pet) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet) || !isMob(pet) || isPlayer(pet))
        {
            return false;
        }
        obj_id master = getMaster(pet);
        if (!isIdValid(master) || !exists(master))
        {
            return false;
        }
        return true;
    }
    public static void removeFromPetList(obj_id pet) throws InterruptedException
    {
        if (!isIdValid(pet) || !hasMaster(pet))
        {
            return;
        }
        obj_id master = getMaster(pet);
        int petSpecies = ai_lib.aiGetSpecies(pet);
        if (!isValidId(master) || !exists(master) || petSpecies == -1 || callable.getCallable(master, callable.getCallableType(pet)) != pet)
        {
            return;
        }
        callable.setCallable(master, null, callable.getCallableType(pet));
        return;
    }
    public static void releasePet(obj_id pet) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("pet", pet);
        params.put("species", ai_lib.aiGetSpecies(pet));
        String creatureName = getCreatureName(pet);
        if (creatureName != null)
        {
            params.put("ai.creatureName", creatureName);
        }
        if (callable.hasCallableCD(pet))
        {
            obj_id controller = callable.getCallableCD(pet);
            if (isIdValid(controller))
            {
                obj_id currentPet = callable.getCDCallable(controller);
                if (isIdValid(currentPet) && currentPet == pet)
                {
                    savePetInfo(pet, controller);
                    setObjVar(controller, "pet.timeStored", getGameTime());
                }
            }
        }
        messageTo(pet, "handlePackRequest", null, 0, false);
    }
    public static int getMaxPets(obj_id master, int petType) throws InterruptedException
    {
        if (petType == PET_TYPE_NPC)
        {
            return MAX_NPC_PETS;
        }
        else if (petType == PET_TYPE_DROID)
        {
            return MAX_DROID_PETS;
        }
        else if (petType == PET_TYPE_FAMILIAR)
        {
            return MAX_FAMILIAR_PETS;
        }
        else if (petType == PET_TYPE_MOUNT)
        {
            return MAX_MOUNT_PETS;
        }
        return 1;
    }
    public static int countPetsOfType(obj_id master, int petType) throws InterruptedException
    {
        int petCount = 0;
        if (hasMaxPets(master, petType))
        {
            petCount++;
        }
        return petCount;
    }
    public static boolean hasMaxPets(obj_id master, int petType) throws InterruptedException
    {
        return hasMaxPets(master, petType, obj_id.NULL_ID);
    }
    public static boolean hasMaxPets(obj_id master, int petType, obj_id pet) throws InterruptedException
    {
        int callableType = callable.CALLABLE_TYPE_COMBAT_PET;
        if (petType == PET_TYPE_FAMILIAR)
        {
            callableType = callable.CALLABLE_TYPE_FAMILIAR;
        }
        else if (petType == PET_TYPE_MOUNT)
        {
            callableType = callable.CALLABLE_TYPE_RIDEABLE;
        }
        if (!isIdValid(pet))
        {
            if (callable.hasCallable(master, callableType))
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            if (callable.hasCallable(master, callableType, pet))
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
    }
    public static void initializePetCommandList(obj_id pet, obj_id pcd) throws InterruptedException
    {
        dictionary petCommandList = new dictionary();
        obj_var_list commandList = getObjVarList(pcd, "ai.pet.command");
        if (commandList != null && commandList.getNumItems() > 0)
        {
            int numItems = commandList.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = commandList.getObjVar(i);
                petCommandList.put(ov.getStringData(), Integer.parseInt(ov.getName()));
            }
        }
        utils.setScriptVar(pet, "ai.pet.commandList", petCommandList);
    }
    public static int getDroidModuleCommandLevel(obj_id player, int modulePotency) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return 0;
        }
        String profession = skill.getProfessionName(getSkillTemplate(player));
        if(profession == null){
            LOG("pet","Cannot get the profession for player (" + player + ") while getting droid command level.");
            return 0;
        }
        boolean isTrader = false;
        if (profession.equals("trader"))
        {
            isTrader = true;
        }
        int playerLevel = getLevel(player);
        int commandLevel = 0;
        if (modulePotency >= 100 && isTrader && playerLevel >= 60)
        {
            commandLevel = 3;
        }
        else if (modulePotency >= 50 && isTrader && playerLevel >= 30)
        {
            commandLevel = 2;
        }
        else if (modulePotency > 0)
        {
            commandLevel = 1;
        }
        return commandLevel;
    }
    public static void learnCommand(obj_id pet, int commandNum, String text, obj_id master) throws InterruptedException
    {
        if (utils.isExtendedASCII(text))
        {
            sendSystemMessage(master, new string_id(MENU_FILE, "invalid_command"));
            return;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        String command = getStringObjVar(petControlDevice, "ai.pet.command." + commandNum);
        String oldCommand = null;
        if (command == null || command.equals(""))
        {
            if (isCreaturePet(pet))
            {
                String creatureName = getCreatureName(pet);
                if (creatureName == null)
                {
                    return;
                }
                int petType = pet_lib.getPetType(pet);
                int tamingSkillMod = 0;
                if (petType == pet_lib.PET_TYPE_AGGRO)
                {
                    tamingSkillMod = getEnhancedSkillStatisticModifier(master, "tame_aggro");
                }
                else 
                {
                    tamingSkillMod = getEnhancedSkillStatisticModifier(master, "tame_non_aggro");
                }
                float chance = 0;
                if (tamingSkillMod > 0)
                {
                    chance = 50 + tamingSkillMod;
                    chance -= getAdultLevel(pet) * 2;
                    if (chance < 20)
                    {
                        chance = 20;
                    }
                }
                int attempts = utils.getIntScriptVar(pet, "ai.pet.trainCommand" + commandNum);
                int roll = rand(1, 100);
                if (roll > chance && attempts < 3)
                {
                    doConfusedEmote(pet);
                    attempts++;
                    sendSystemMessage(master, SID_SYS_CANT_LEARN);
                    utils.setScriptVar(pet, "ai.pet.trainCommand" + commandNum, attempts);
                    return;
                }
            }
        }
        else 
        {
            oldCommand = getStringObjVar(petControlDevice, "ai.pet.command." + commandNum);
        }
        command = text;
        setObjVar(petControlDevice, "ai.pet.command." + commandNum, command);
        dictionary petCommandList = utils.getDictionaryScriptVar(pet, "ai.pet.commandList");
        if (petCommandList == null)
        {
            petCommandList = new dictionary();
        }
        petCommandList.put(command, commandNum);
        if (oldCommand != null)
        {
            petCommandList.remove(oldCommand);
        }
        utils.setScriptVar(pet, "ai.pet.commandList", petCommandList);
        utils.removeScriptVar(pet, "ai.pet.learnCommand");
        if (!pet_lib.isSpeakingPet(pet) || pet_lib.isDroidPet(pet))
        {
            if (!pet_lib.isDroidPet(pet))
            {
                sendSystemMessage(master, SID_SYS_LEARN_COMMAND);
                pet_lib.openLearnCommandSui(pet, master);
            }
            color textColor = colors.SILVER;
            showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "threaten"), .75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
            checkForName(pet, petControlDevice);
        }
        else 
        {
            String myStringFile = "hireling/hireling";
            if (hasObjVar(pet, "ai.diction"))
            {
                String myDiction = getStringObjVar(pet, "ai.diction");
                if (myDiction != null && !myDiction.equals("townperson"))
                {
                    myStringFile = "hireling/hireling" + "_" + myDiction;
                }
            }
            chat.chat(pet, new string_id(myStringFile, "end_convo"));
        }
        savePetInfo(pet, petControlDevice);
    }
    public static int getCommandNum(obj_id pet, String text) throws InterruptedException
    {
        if (!utils.hasScriptVar(pet, "ai.pet.commandList"))
        {
            return -1;
        }
        dictionary petCommandList = utils.getDictionaryScriptVar(pet, "ai.pet.commandList");
        String myName = toLower(getName(pet));
        int suffix = myName.indexOf(")");
        if (suffix > 1)
        {
            myName = myName.substring(1, suffix);
        }
        int commandNum = -1;
        if (petCommandList.containsKey(text))
        {
            commandNum = petCommandList.getInt(text);
        }
        else if (petCommandList.containsKey(myName + " " + text))
        {
            commandNum = petCommandList.getInt(myName + " " + text);
        }
        if (callable.getCallableType(pet) == callable.CALLABLE_TYPE_RIDEABLE)
        {
            return -1;
        }
        if (commandNum > -1)
        {
            color textColor = colors.BLUE;
            showFlyTextPrivate(pet, getMaster(pet), new string_id("npc_reaction/flytext", "threaten"), 1.75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
        }
        return commandNum;
    }
    public static void doCommand(obj_id pet, String text, obj_id master) throws InterruptedException
    {
        int commandNum = getCommandNum(pet, text);
        if (commandNum == -1)
        {
            return;
        }
        doCommandNum(pet, commandNum, master);
    }
    public static boolean doCommandNum(obj_id master, int commandNum) throws InterruptedException
    {
        obj_id pet = callable.getCallable(master, callable.CALLABLE_TYPE_COMBAT_PET);
        if (!isIdValid(pet) || !exists(pet) || !pet_lib.isDroidPet(pet))
        {
            return false;
        }
        if (getMaster(pet) != master)
        {
            return false;
        }
        return doPetCommand(pet, commandNum, master);
    }
    public static void doCommandNum(obj_id pet, int commandNum, obj_id master) throws InterruptedException
    {
        doPetCommand(pet, commandNum, master);
    }
    public static boolean doPetCommand(obj_id pet, int commandNum, obj_id master) throws InterruptedException
    {
        if (ai_lib.isInCombat(pet))
        {
            if (commandNum >= COMMAND_FOLLOW && commandNum < NUM_COMMANDS && commandNum != COMMAND_STAY && commandNum != COMMAND_FOLLOW && commandNum != COMMAND_FOLLOW_OTHER && commandNum != COMMAND_ATTACK && commandNum != COMMAND_SPECIALATTACK_ONE && commandNum != COMMAND_SPECIALATTACK_TWO)
            {
                return false;
            }
        }
        if (getMountsEnabled())
        {
            obj_id petsCurrentRider = getRiderId(pet);
            if (isIdValid(petsCurrentRider))
            {
                if (commandNum != COMMAND_TRICK_1 && commandNum != COMMAND_TRICK_2 && commandNum != COMMAND_RELEASE && commandNum != COMMAND_GROUP)
                {
                    return false;
                }
            }
        }
        if (isDroidPet(pet))
        {
            if (isLowOnPower(pet))
            {
                color textColor = colors.MEDIUMPURPLE;
                showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "low_power"), 0.75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
                if (commandNum != COMMAND_FOLLOW && commandNum != COMMAND_STAY && commandNum != COMMAND_RELEASE)
                {
                    return false;
                }
            }
        }
        switch (commandNum)
        {
            case COMMAND_FOLLOW:
            doFollowCommand(pet, master);
            break;
            case COMMAND_FOLLOW_OTHER:
            doFollowOtherCommand(pet, master);
            break;
            case COMMAND_STAY:
            doStayCommand(pet, master);
            break;
            case COMMAND_GUARD:
            doGuardCommand(pet, master);
            break;
            case COMMAND_FRIEND:
            doFriendCommand(pet, master);
            break;
            case COMMAND_ATTACK:
            doAttackCommand(pet, master);
            break;
            case COMMAND_PATROL:
            doPatrol(pet, master);
            break;
            case COMMAND_SET_PATROL_POINT:
            doSetPatrolPoint(pet, master);
            break;
            case COMMAND_CLEAR_PATROL_POINTS:
            doClearPatrolPoints(pet, master);
            break;
            case COMMAND_ASSUME_FORMATION_1:
            doPetFormation(pet, master, ai_lib.FORMATION_COLUMN);
            break;
            case COMMAND_ASSUME_FORMATION_2:
            doPetFormation(pet, master, ai_lib.FORMATION_WEDGE);
            break;
            case COMMAND_RELEASE:
            doPetRelease(pet);
            break;
            case COMMAND_TRANSFER:
            doPetTransfer(pet, master);
            break;
            case COMMAND_TRICK_1:
            doStupidPetTrick(pet, master, COMMAND_TRICK_1);
            break;
            case COMMAND_TRICK_2:
            doStupidPetTrick(pet, master, COMMAND_TRICK_2);
            break;
            case COMMAND_TRICK_3:
            doStupidPetTrick(pet, master, COMMAND_TRICK_3);
            break;
            case COMMAND_TRICK_4:
            doStupidPetTrick(pet, master, COMMAND_TRICK_4);
            break;
            case COMMAND_GROUP:
            doPetGroup(pet, master);
            break;
            case COMMAND_SPECIALATTACK_ONE:
            doPetSpecialAttack(pet, master, 0);
            break;
            case COMMAND_SPECIALATTACK_TWO:
            doPetSpecialAttack(pet, master, 1);
            break;
            case COMMAND_TARGET_HARVEST:
            doTargetHarvest(pet, master);
            break;
            case COMMAND_TRAP_THROW_ONE:
            doPetThrowTrap(pet, master, commandNum);
            break;
            default:
            doTriggeredPetAbility(pet, master, commandNum);
            break;
        }
        return true;
    }
    public static void prepToLearn(obj_id pet, int commandNum) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet))
        {
            return;
        }
        utils.setScriptVar(pet, "ai.pet.learnCommand", commandNum);
        if (!hasMaster(pet))
        {
            return;
        }
        obj_id master = getMaster(pet);
        if (!pet_lib.isSpeakingPet(pet))
        {
            color textColor = colors.GOLD;
            showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "alert"), 1.25f, textColor.getR(), textColor.getG(), textColor.getB(), true);
        }
        else 
        {
            String myStringFile = "hireling/hireling";
            if (hasObjVar(pet, "ai.diction"))
            {
                String myDiction = getStringObjVar(pet, "ai.diction");
                if (myDiction != null && !myDiction.equals("townperson"))
                {
                    myStringFile = "hireling/hireling" + "_" + myDiction;
                }
            }
            chat.chat(pet, new string_id(myStringFile, "start_convo_4"));
        }
    }
    public static String createLearnCommandListHeader(string_id header) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = new string_id("pet/pet_ability", "learn_command_list_header");
        pp.actor.set(header);
        return " \0" + packOutOfBandProsePackage(null, pp);
    }
    public static String createLearnCommandListEntry(string_id entry, String command) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = new string_id("pet/pet_ability", "learn_command_list_entry");
        pp.actor.set(entry);
        pp.target.set(command);
        return " \0" + packOutOfBandProsePackage(null, pp);
    }
    public static String getCommandString(obj_id pcd, int command) throws InterruptedException
    {
        return getCommandString(getObjVarList(pcd, "ai.pet.command"), command);
    }
    public static String getCommandString(obj_var_list commandList, int command) throws InterruptedException
    {
        String commandString = " ";
        if (commandList != null && commandList.hasObjVar("" + command))
        {
            commandString = " -  " + commandList.getStringObjVar("" + command);
        }
        return commandString;
    }
    public static boolean getLearnableCommandList(obj_id pet) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (!isIdValid(master))
        {
            return false;
        }
        obj_id pcd = callable.getCallableCD(pet);
        if (!isIdValid(pcd))
        {
            return false;
        }
        int manageSkillMod = 100;
        if (!pet_lib.isDroidPet(pet))
        {
            manageSkillMod = getSkillStatisticModifier(master, "private_creature_management");
        }
        int empathySkillMod = 100;
        if (!pet_lib.isDroidPet(pet))
        {
            empathySkillMod = getSkillStatisticModifier(master, "private_creature_empathy");
        }
        int trainingSkillMod = 100;
        if (!pet_lib.isDroidPet(pet))
        {
            trainingSkillMod = getSkillStatisticModifier(master, "private_creature_training");
        }
        Vector commandNameList = new Vector();
        commandNameList.setSize(0);
        Vector commandIndexList = new Vector();
        commandIndexList.setSize(0);
        obj_var_list commandList = getObjVarList(pcd, "ai.pet.command");
        commandIndexList = utils.addElement(commandIndexList, 0);
        commandNameList = utils.addElement(commandNameList, createLearnCommandListHeader(new string_id("obj_attr_n", "pet_command")));
        
        {
            commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_RELEASE);
            commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_release"), getCommandString(commandList, COMMAND_RELEASE)));
            if (hasSkill(master, "outdoors_creaturehandler_master"))
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_TRANSFER);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_transfer"), getCommandString(commandList, COMMAND_TRANSFER)));
            }
            if (pet_lib.isCreaturePet(pet))
            {
                if (empathySkillMod >= 30)
                {
                    commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_TRICK_2);
                    commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_trick_2"), getCommandString(commandList, COMMAND_TRICK_2)));
                }
                if (empathySkillMod >= 10)
                {
                    commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_TRICK_1);
                    commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_trick_1"), getCommandString(commandList, COMMAND_TRICK_1)));
                }
            }
            else if (ai_lib.aiGetSpecies(pet) == 215)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_TRICK_1);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_recharge_other"), getCommandString(commandList, COMMAND_TRICK_1)));
            }
            else if (ai_lib.aiGetSpecies(pet) == 212)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_TRICK_1);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_solicit_tip"), getCommandString(commandList, COMMAND_TRICK_1)));
            }
            if (!pet_lib.isCreaturePet(pet) && pet_lib.isRepairDroid(pet))
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_TRICK_2);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_repair_other"), getCommandString(commandList, COMMAND_TRICK_2)));
            }
            if (manageSkillMod >= 10)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_GROUP);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_group"), getCommandString(commandList, COMMAND_GROUP)));
            }
            if (trainingSkillMod >= 30)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_CLEAR_PATROL_POINTS);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_clear_patrol_points"), getCommandString(commandList, COMMAND_CLEAR_PATROL_POINTS)));
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_GET_PATROL_POINT);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_get_patrol_point"), getCommandString(commandList, COMMAND_SET_PATROL_POINT)));
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_PATROL);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_patrol"), getCommandString(commandList, COMMAND_PATROL)));
            }
            if (trainingSkillMod >= 40)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_ASSUME_FORMATION_1);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_assume_formation_1"), getCommandString(commandList, COMMAND_ASSUME_FORMATION_1)));
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_ASSUME_FORMATION_2);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_assume_formation_2"), getCommandString(commandList, COMMAND_ASSUME_FORMATION_2)));
            }
            if (manageSkillMod >= 30)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_FRIEND);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_friend"), getCommandString(commandList, COMMAND_FRIEND)));
            }
            if (!pet_lib.isDroidPet(pet) || pet_lib.isCombatDroid(pet))
            {
                if (trainingSkillMod >= 20)
                {
                    commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_GUARD);
                    commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_guard"), getCommandString(commandList, COMMAND_GUARD)));
                }
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_ATTACK);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_attack"), getCommandString(commandList, COMMAND_ATTACK)));
            }
            if (trainingSkillMod >= 10)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_STAY);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_stay"), getCommandString(commandList, COMMAND_STAY)));
            }
            if (manageSkillMod >= 20)
            {
                commandIndexList = utils.addElement(commandIndexList, menu_info_types.DICE_ROLL);
                commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_follow_other"), getCommandString(commandList, COMMAND_FOLLOW_OTHER)));
            }
            commandIndexList = utils.addElement(commandIndexList, menu_info_types.PET_FOLLOW);
            commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_follow"), getCommandString(commandList, COMMAND_FOLLOW)));
            if (getMountsEnabled() && (!hasObjVar(pcd, "ai.pet.trainedMount")))
            {
                if (pet_lib.canTrainAsMount(pet, master))
                {
                    commandIndexList = utils.addElement(commandIndexList, menu_info_types.SERVER_PET_TRAIN_MOUNT);
                    commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id(MENU_FILE, "menu_train_mount"), " "));
                }
            }
        }
        commandIndexList = utils.addElement(commandIndexList, 0);
        commandNameList = utils.addElement(commandNameList, createLearnCommandListHeader(new string_id("obj_attr_n", "pet_ability")));
        
        {
            int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
            if (abilityList != null && abilityList.length > 0)
            {
                for (int i = 0; i < abilityList.length; i++)
                {
                    int row = dataTableSearchColumnForInt(abilityList[i], "abilityCrc", PET_ABILITY_TABLE);
                    if (row == -1)
                    {
                        continue;
                    }
                    int type = dataTableGetInt(PET_ABILITY_TABLE, row, "type");
                    if (type == PET_ABILITY_TRIGGERED || type == PET_ABILITY_ACTION)
                    {
                        String abilityName = dataTableGetString(PET_ABILITY_TABLE, row, "abilityName");
                        commandIndexList = utils.addElement(commandIndexList, abilityList[i]);
                        commandNameList = utils.addElement(commandNameList, createLearnCommandListEntry(new string_id("pet/pet_ability", abilityName), getCommandString(commandList, abilityList[i])));
                    }
                }
            }
        }
        if (commandIndexList.size() > 0 && commandNameList.size() > 0 && commandIndexList.size() == commandNameList.size())
        {
            utils.setScriptVar(pet, "ai.learnCommand.commandIndexList", commandIndexList);
            utils.setScriptVar(pet, "ai.learnCommand.commandNameList", commandNameList);
            return true;
        }
        return false;
    }
    public static void learnPetCommand(obj_id pet, int command) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (!isIdValid(master))
        {
            return;
        }
        obj_id pcd = callable.getCallableCD(pet);
        if (!isIdValid(pcd))
        {
            return;
        }
        if (command == 0)
        {
            sendSystemMessage(master, new string_id("pet/pet_ability", "learn_command_error"));
            return;
        }
        int manageSkillMod = 100;
        if (!pet_lib.isDroidPet(pet))
        {
            manageSkillMod = getSkillStatisticModifier(master, "private_creature_management");
        }
        int empathySkillMod = 100;
        if (!pet_lib.isDroidPet(pet))
        {
            empathySkillMod = getSkillStatisticModifier(master, "private_creature_empathy");
        }
        int trainingSkillMod = 100;
        if (!pet_lib.isDroidPet(pet))
        {
            trainingSkillMod = getSkillStatisticModifier(master, "private_creature_training");
        }
        if (command > menu_info_types.UNKNOWN && command < menu_info_types.MENU_LAST)
        {
            if (command == menu_info_types.SERVER_PET_TRAIN_MOUNT)
            {
                if (getMountsEnabled() && (!hasObjVar(pcd, "ai.pet.trainedMount")))
                {
                    if (pet_lib.canTrainAsMount(pet, master))
                    {
                        pet_lib.trainMount(pet, master);
                    }
                }
            }
            else if (command == menu_info_types.PET_FOLLOW)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_FOLLOW);
            }
            else if (command == menu_info_types.DICE_ROLL && (manageSkillMod >= 20))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_FOLLOW_OTHER);
            }
            else if (command == menu_info_types.PET_STAY && (trainingSkillMod >= 10))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_STAY);
            }
            else if (command == menu_info_types.PET_RELEASE)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_RELEASE);
            }
            else if (command == menu_info_types.PET_TRANSFER && (manageSkillMod >= 40))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_TRANSFER);
            }
            else if (command == menu_info_types.PET_TRICK_4)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_TRICK_4);
            }
            else if (command == menu_info_types.PET_TRICK_3)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_TRICK_3);
            }
            else if (command == menu_info_types.PET_TRICK_2 && (empathySkillMod >= 30))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_TRICK_2);
            }
            else if (command == menu_info_types.PET_TRICK_1 && (empathySkillMod >= 10))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_TRICK_1);
            }
            else if (command == menu_info_types.PET_GROUP && (manageSkillMod >= 10))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_GROUP);
            }
            else if (command == menu_info_types.PET_CLEAR_PATROL_POINTS && (trainingSkillMod >= 30))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_CLEAR_PATROL_POINTS);
            }
            else if (command == menu_info_types.PET_GET_PATROL_POINT && (trainingSkillMod >= 30))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_SET_PATROL_POINT);
            }
            else if (command == menu_info_types.PET_PATROL && (trainingSkillMod >= 30))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_PATROL);
            }
            else if (command == menu_info_types.PET_ASSUME_FORMATION_1 && (trainingSkillMod >= 40))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_ASSUME_FORMATION_1);
            }
            else if (command == menu_info_types.PET_ASSUME_FORMATION_2 && (trainingSkillMod >= 40))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_ASSUME_FORMATION_2);
            }
            else if (command == menu_info_types.PET_FRIEND && (manageSkillMod >= 30))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_FRIEND);
            }
            else if (command == menu_info_types.PET_GUARD && (trainingSkillMod >= 20))
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_GUARD);
            }
            else if (command == menu_info_types.PET_ATTACK)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_ATTACK);
            }
            else if (command == menu_info_types.PET_SPECIAL_ATTACK_ONE)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_SPECIALATTACK_ONE);
            }
            else if (command == menu_info_types.PET_SPECIAL_ATTACK_TWO)
            {
                pet_lib.prepToLearn(pet, pet_lib.COMMAND_SPECIALATTACK_TWO);
            }
        }
        else 
        {
            pet_lib.prepToLearn(pet, command);
        }
    }
    public static void doTriggeredPetAbility(obj_id pet, obj_id master, int commandNum) throws InterruptedException
    {
        int row = dataTableSearchColumnForInt(commandNum, "abilityCrc", PET_ABILITY_TABLE);
        if (row == -1)
        {
            return;
        }
        dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
        if (abilityData == null)
        {
            return;
        }
        int[] actionCost = combat.getActionCost(pet, getWeaponData(getCurrentWeapon(pet)), abilityData);
        if (combat.canDrainCombatActionAttributes(pet, actionCost))
        {
            int type = abilityData.getInt("type");
            if (type == PET_ABILITY_TRIGGERED)
            {
                combat.drainCombatActionAttributes(pet, actionCost);
                String selfBuff = abilityData.getString("selfBuffName");
                String targetBuff = abilityData.getString("targetBuffName");
                String masterBuff = abilityData.getString("masterBuffName");
                int duration = abilityData.getInt("duration");
                if (selfBuff != null && !selfBuff.equals(""))
                {
                    if (duration > 0)
                    {
                        buff.applyBuff(pet, selfBuff, duration);
                    }
                    else 
                    {
                        buff.applyBuff(pet, selfBuff);
                    }
                }
                if (targetBuff != null && !targetBuff.equals(""))
                {
                    obj_id target = getTarget(pet);
                    if (isIdValid(target))
                    {
                        if (duration > 0)
                        {
                            buff.applyBuff(target, targetBuff, duration);
                        }
                        else 
                        {
                            buff.applyBuff(target, targetBuff);
                        }
                    }
                    else 
                    {
                        doConfusedEmote(pet);
                    }
                }
                if (masterBuff != null && !masterBuff.equals(""))
                {
                    if (isIdValid(master) && exists(master))
                    {
                        if (duration > 0)
                        {
                            buff.applyBuff(master, masterBuff, duration);
                        }
                        else 
                        {
                            buff.applyBuff(master, masterBuff);
                        }
                    }
                    else 
                    {
                        doConfusedEmote(pet);
                    }
                }
            }
            else if (type == PET_ABILITY_ACTION)
            {
                obj_id target = getTarget(master);
                String actionName = abilityData.getString("actionName");
                int idx = actionName.indexOf('_');
                if (idx > 0)
                {
                    actionName = actionName.substring(0, idx);
                }
                if (isIdValid(target) && pvpCanAttack(pet, target) && pvpCanAttack(master, target))
                {
                    setObjVar(pet, "ai.combat.forcedAction", actionName);
                    if (!ai_lib.isInCombat(pet))
                    {
                        startCombat(pet, target);
                    }
                    else 
                    {
                        float hate = getMaxHate(pet) + 100;
                        setHate(pet, target, hate);
                    }
                }
                else 
                {
                    doConfusedEmote(pet);
                }
            }
        }
        else 
        {
            doConfusedEmote(pet);
        }
    }
    public static void doFollowCommand(obj_id pet, obj_id master) throws InterruptedException
    {
        if (ai_lib.isInCombat(pet))
        {
            utils.removeScriptVar(pet, "ai.combat.target");
            utils.setScriptVar(pet, "petIgnoreAttacks", getGameTime());
            utils.setScriptVar(pet, "pet.combatEnded", getGameTime());
            stopCombat(pet);
        }
        ai_lib.clearPatrolPath(pet);
        removeObjVar(pet, "ai.inFormation");
        removeObjVar(pet, "ai.formationType");
        removeObjVar(pet, "ai.formationPosition");
        utils.removeScriptVar(pet, "ai.pet.staying");
        if (hasObjVar(pet, "ai.wounded"))
        {
            removeObjVar(pet, "ai.wounded");
        }
        int myPosture = getPosture(pet);
        if (myPosture != POSTURE_UPRIGHT && myPosture != POSTURE_SITTING)
        {
            stop(pet);
            removeObjVar(pet, "ai.combat.moveMode");
            queueCommand(pet, (-1465754503), pet, "", COMMAND_PRIORITY_FRONT);
        }
        petFollow(pet, master);
    }
    public static void doFollowOtherCommand(obj_id pet, obj_id master) throws InterruptedException
    {
        obj_id target = getIntendedTarget(master);
        if (isIdValid(target) == false || target == pet)
        {
            target = master;
        }
        if (ai_lib.isInCombat(pet))
        {
            utils.removeScriptVar(pet, "ai.combat.target");
            utils.setScriptVar(pet, "petIgnoreAttacks", getGameTime());
            utils.setScriptVar(pet, "pet.combatEnded", getGameTime());
            stopCombat(pet);
        }
        ai_lib.clearPatrolPath(pet);
        removeObjVar(pet, "ai.inFormation");
        removeObjVar(pet, "ai.formationType");
        removeObjVar(pet, "ai.formationPosition");
        utils.removeScriptVar(pet, "ai.pet.staying");
        setMovementRun(pet);
        ai_lib.aiFollow(pet, target);
    }
    public static void doStayCommand(obj_id pet, obj_id master) throws InterruptedException
    {
        ai_lib.clearPatrolPath(pet);
        removeObjVar(pet, "ai.inFormation");
        removeObjVar(pet, "ai.formationType");
        removeObjVar(pet, "ai.formationPosition");
        setMovementWalk(pet);
        if (ai_lib.isInCombat(pet))
        {
            utils.removeScriptVar(pet, "ai.combat.target");
            utils.setScriptVar(pet, "petIgnoreAttacks", getGameTime());
            utils.setScriptVar(pet, "pet.combatEnded", getGameTime());
            stopCombat(pet);
        }
        utils.setScriptVar(pet, "ai.pet.staying", true);
        ai_lib.aiStopFollowing(pet);
        location myLoc = getLocation(pet);
        setHomeLocation(pet, myLoc);
        setMovementWalk(pet);
        if (ai_lib.isMonster(pet))
        {
            doAnimationAction(pet, "vocalize");
            ai_lib.setDefaultCalmMood(pet, "bored");
        }
        else if (pet_lib.isDroidPet(pet))
        {
            doAnimationAction(pet, "emt_nod_head_once");
        }
        else 
        {
            doAnimationAction(pet, "salute1");
        }
    }
    public static void doGuardCommand(obj_id pet, obj_id master) throws InterruptedException
    {
        if (!isMob(master) && !isPlayer(master))
        {
            doConfusedEmote(pet);
            return;
        }
        utils.removeScriptVar(pet, "ai.pet.staying");
        utils.setScriptVar(pet, "ai.pet.guarding", master);
        setWantSawAttackTriggers(pet, true);
    }
    public static boolean isGuarding(obj_id pet, obj_id target) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet) || !isIdValid(target) || !exists(target) || !hasMaster(pet))
        {
            return false;
        }
        obj_id guarding = utils.getObjIdScriptVar(pet, "ai.pet.guarding");
        if (isIdValid(guarding) && exists(guarding))
        {
            return (guarding == target);
        }
        return false;
    }
    public static void doFriendCommand(obj_id pet, obj_id master) throws InterruptedException
    {
        obj_id target = getIntendedTarget(master);
        if (isIdValid(target) == false || isPlayer(target) == false)
        {
            doConfusedEmote(pet);
            return;
        }
        Vector friendList = new Vector();
        friendList.setSize(0);
        if (hasObjVar(pet, "ai.pet.friendList"))
        {
            friendList = getResizeableObjIdArrayObjVar(pet, "ai.pet.friendList");
        }
        int indexNum = utils.getElementPositionInArray(friendList, target);
        if (indexNum != -1)
        {
            utils.removeElementAt(friendList, indexNum);
            return;
        }
        if (friendList.size() > MAX_NUMBER_OF_FRIENDS)
        {
            utils.removeElementAt(friendList, 0);
        }
        friendList = utils.addElement(friendList, target);
        setObjVar(pet, "ai.pet.friendList", friendList);
    }
    public static boolean isFriend(obj_id pet, obj_id target) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (!isIdValid(master))
        {
            return false;
        }
        if (master == target)
        {
            return true;
        }
        if (!hasObjVar(pet, "ai.pet.friendList"))
        {
            return false;
        }
        obj_id[] friendList = getObjIdArrayObjVar(pet, "ai.pet.friendList");
        int indexNum = utils.getElementPositionInArray(friendList, target);
        if (indexNum != -1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static void doConfusedEmote(obj_id pet) throws InterruptedException
    {
        if (!pet_lib.isSpeakingPet(pet))
        {
            showFlyText(pet, new string_id("npc_reaction/flytext", "alert"), 3.0f, colors.COPPER);
            doAnimationAction(pet, "emt_stand_confused");
        }
        else 
        {
            String myStringFile = "hireling/hireling";
            if (hasObjVar(pet, "ai.diction"))
            {
                String myDiction = getStringObjVar(pet, "ai.diction");
                if (myDiction != null && !myDiction.equals("townperson"))
                {
                    myStringFile = "hireling/hireling" + "_" + myDiction;
                }
            }
            chat.chat(pet, chat.CHAT_COMPLAIN, chat.MOOD_CONFUSED, new string_id(myStringFile, "confused"));
        }
    }
    public static void doAttackCommand(obj_id pet, obj_id master) throws InterruptedException
    {
        if (!isIdValid(master))
        {
            return;
        }
        utils.removeScriptVar(pet, "petIgnoreAttacks");
        obj_id target = getIntendedTarget(master);
        if (isIdValid(target) == false || target == pet)
        {
            doConfusedEmote(pet);
            return;
        }
        if (!pvpCanAttack(pet, target))
        {
            doConfusedEmote(pet);
            return;
        }
        if (!pvpCanAttack(master, target))
        {
            doConfusedEmote(pet);
            return;
        }
        if (master.isLoaded())
        {
            setHomeLocation(pet, getLocation(master));
        }
        else if (pet.isLoaded())
        {
            setHomeLocation(pet, getLocation(pet));
        }
        utils.removeScriptVar(pet, "ai.pet.staying");
        chat.setBadMood(pet);
        if (!ai_lib.isInCombat(pet))
        {
            if (!pet_lib.cancelAttackDueToFactionalRestrictions(pet, target))
            {
                startCombat(pet, target);
                utils.setScriptVar(pet, "ai.combat.target", target);
            }
        }
        else if (!pet_lib.cancelAttackDueToFactionalRestrictions(pet, target))
        {
            utils.setScriptVar(pet, "ai.combat.target", target);
            float maxHate = getMaxHate(pet);
            setHate(pet, target, maxHate + 5000);
        }
    }
    public static void doPatrol(obj_id pet, obj_id master) throws InterruptedException
    {
        if (!hasObjVar(pet, OBJVAR_PET_PATROL_POINTS))
        {
            doConfusedEmote(pet);
        }
        else 
        {
            removeObjVar(pet, "ai.inFormation");
            removeObjVar(pet, "ai.formationType");
            removeObjVar(pet, "ai.formationPosition");
            location[] patrolLoc = getLocationArrayObjVar(pet, OBJVAR_PET_PATROL_POINTS);
            if (patrolLoc != null)
            {
                ai_lib.setPatrolPath(pet, patrolLoc);
            }
            utils.removeScriptVar(pet, "ai.pet.staying");
        }
    }
    public static void doSetPatrolPoint(obj_id pet, obj_id master) throws InterruptedException
    {
        location[] patrolLoc = getLocationArrayObjVar(pet, OBJVAR_PET_PATROL_POINTS);
        if (patrolLoc == null)
        {
            patrolLoc = new location[1];
        }
        else if (patrolLoc.length < MAX_WAYPOINTS)
        {
            location[] temp = new location[patrolLoc.length + 1];
            for (int i = 0; i < patrolLoc.length; ++i)
            {
                temp[i] = patrolLoc[i];
            }
            patrolLoc = temp;
        }
        else 
        {
            doConfusedEmote(pet);
            return;
        }
        patrolLoc[patrolLoc.length - 1] = getLocation(master);
        setObjVar(pet, OBJVAR_PET_PATROL_POINTS, patrolLoc);
        sendSystemMessage(master, SID_SYS_PATROL_ADDED);
    }
    public static void doClearPatrolPoints(obj_id pet, obj_id master) throws InterruptedException
    {
        if (hasObjVar(pet, OBJVAR_PET_PATROL_POINTS))
        {
            removeObjVar(pet, OBJVAR_PET_PATROL_POINTS);
            sendSystemMessage(master, SID_SYS_PATROL_REMOVED);
            ai_lib.clearPatrolPath(pet);
            stop(pet);
            messageTo(pet, "resumeDefaultCalmBehavior", null, 1, false);
        }
        else 
        {
            doConfusedEmote(pet);
        }
    }
    public static void doPetFormation(obj_id pet, obj_id master, int formationType) throws InterruptedException
    {
        int position = 0;
        switch (callable.getCallableType(pet))
        {
            case callable.CALLABLE_TYPE_COMBAT_OTHER:
            position = 0;
            break;
            case callable.CALLABLE_TYPE_COMBAT_PET:
            position = 1;
            break;
            case callable.CALLABLE_TYPE_FAMILIAR:
            position = 2;
            break;
            default:
            return;
        }
        setMovementRun(pet);
        ai_lib.followInFormation(pet, master, formationType, position);
        utils.removeScriptVar(pet, "ai.pet.staying");
    }
    public static void doPetRelease(obj_id pet) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        CustomerServiceLog("Pets", "*Pet Release: (" + master + " sid:" + getPlayerObject(master) + ") " + getName(master) + ": has requested that their pet, " + pet + getName(pet) + " be released. Script is attempting to do so.");
        if (isIdValid(master) && !wasInCombatRecently(pet, master, true))
        {
            ai_lib.clearPatrolPath(pet);
            removeObjVar(pet, "ai.inFormation");
            removeObjVar(pet, "ai.formationType");
            removeObjVar(pet, "ai.formationPosition");
            releasePet(pet);
        }
    }
    public static void doPetTransfer(obj_id pet, obj_id master) throws InterruptedException
    {
        obj_id target = getIntendedTarget(master);
        if (isIdValid(target) == false || !isPlayer(target))
        {
            doConfusedEmote(pet);
            return;
        }
        if (!hasScript(target, "ai.pet_master"))
        {
            attachScript(target, "ai.pet_master");
        }
        int petType = pet_lib.getPetType(pet);
        String creatureName = getCreatureName(pet);
        if (creatureName == null || creatureName.equals(""))
        {
            destroyObject(pet);
            return;
        }
        int niche = ai_lib.aiGetNiche(pet);
        if (niche == NICHE_NPC || niche == NICHE_VEHICLE)
        {
            sendSystemMessage(master, new string_id("pet/pet_menu", "bad_type"));
            sendSystemMessage(target, new string_id("pet/pet_menu", "bad_type"));
            return;
        }
        if (hasMaxPets(target, petType))
        {
            sendSystemMessage(master, new string_id("pet/pet_menu", "targ_too_many"));
            sendSystemMessage(target, SID_SYS_TOO_MANY_PETS);
            doConfusedEmote(pet);
            return;
        }
        if (hasMaxStoredPetsOfType(target, petType))
        {
            sendSystemMessage(master, new string_id("pet/pet_menu", "targ_too_many_stored"));
            sendSystemMessage(target, SID_SYS_TOO_MANY_STORED_PETS);
            doConfusedEmote(pet);
            return;
        }
        int petLevel = getLevel(pet);
        if (!pet_lib.canControlPetsOfLevel(target, petType, petLevel, creatureName))
        {
            sendSystemMessage(master, new string_id("pet/pet_menu", "no_chance"));
            doConfusedEmote(pet);
            return;
        }
        obj_id pcd = null;
        if (callable.hasCallableCD(pet))
        {
            pcd = callable.getCallableCD(pet);
        }
        obj_id[] petArray = null;
        if (isIdValid(pcd) && exists(pcd))
        {
            petArray = new obj_id[2];
            petArray[0] = pet;
            petArray[1] = pcd;
        }
        else 
        {
            petArray = new obj_id[1];
            petArray[0] = pet;
        }
        obj_id notradeItem = utils.findNoTradeItem(petArray, false);
        if (isIdValid(notradeItem))
        {
            prose_package pp = new prose_package();
            pp.stringId = SID_PET_HAS_NOTRADE;
            pp.target.id = notradeItem;
            sendSystemMessageProse(master, pp);
            doConfusedEmote(pet);
            return;
        }
        obj_id group = getGroupObject(pet);
        if (isIdValid(group))
        {
            queueCommand(pet, (1348589140), group, "", COMMAND_PRIORITY_IMMEDIATE);
        }
        obj_id controller = null;
        if (callable.hasCallableCD(pet))
        {
            controller = callable.getCallableCD(pet);
            obj_id newMasterPad = utils.getPlayerDatapad(target);
            if (!isIdValid(controller) || !isIdValid(newMasterPad))
            {
                sendSystemMessage(master, new string_id("pet/pet_menu", "targ_too_many_stored"));
                sendSystemMessage(target, SID_SYS_TOO_MANY_STORED_PETS);
                doConfusedEmote(pet);
                return;
            }
            if (!putIn(controller, newMasterPad))
            {
                sendSystemMessage(master, new string_id("pet/pet_menu", "targ_too_many_stored"));
                sendSystemMessage(target, SID_SYS_TOO_MANY_STORED_PETS);
                doConfusedEmote(pet);
                CustomerServiceLog("Pets", "***Pet Transfer: (" + master + " sid:" + getPlayerObject(master) + ") " + getName(master) + ": Pet FAILED to transfer " + target + " sid:" + getPlayerObject(target) + ") " + getName(target));
                return;
            }
        }
        sendSystemMessage(master, SID_SYS_XFER_SUCCESS);
        sendSystemMessage(target, SID_SYS_XFER_SUCCESS);
        CustomerServiceLog("Pets", "***Pet Transferrer: Player " + getName(master) + "(" + master + ") StationId(" + getPlayerObject(master) + ") successfully transfered PetControlDevice " + getName(controller) + " (" + controller + ")  of template: " + getTemplateName(controller) + " to receiving player " + getName(target) + "(" + target + ") StationId(" + getPlayerObject(target) + ")");
    }
    public static void doStupidPetTrick(obj_id pet, obj_id master, int trickNum) throws InterruptedException
    {
        if (ai_lib.aiGetSpecies(pet) == SPECIES_POWERDROID && trickNum == COMMAND_TRICK_1)
        {
            rechargeOtherDroid(pet, master);
            return;
        }
        if (ai_lib.aiGetSpecies(pet) == SPECIES_POWERDROID && trickNum == COMMAND_TRICK_1)
        {
            solicitTipsForMaster(pet, master);
            return;
        }
        if (isRepairDroid(pet) && trickNum == COMMAND_TRICK_2)
        {
            repairOtherDroid(pet, master);
            return;
        }
        int myPosture = getPosture(pet);
        if (myPosture != POSTURE_UPRIGHT && myPosture != POSTURE_SITTING)
        {
            stop(pet);
            removeObjVar(pet, "ai.combat.moveMode");
            queueCommand(pet, (-1465754503), pet, "", COMMAND_PRIORITY_FRONT);
            dictionary params = new dictionary();
            params.put("master", master);
            params.put("trickNum", trickNum);
            messageTo(pet, "resumePetTrick", params, 2, false);
            return;
        }
        int action = getAttrib(master, ACTION);
        int actionCost = 100;
        if (action < actionCost)
        {
            sendSystemMessage(master, SID_SYS_CANT_TRICK);
            return;
        }
        drainAttributes(master, actionCost, 0);
        stop(pet);
        float maxHealing = .2f;
        switch (trickNum)
        {
            case COMMAND_TRICK_1:
            if (myPosture == POSTURE_SITTING)
            {
                doAnimationAction(pet, "sit_trick_1");
            }
            else 
            {
                doAnimationAction(pet, "trick_1");
            }
            break;
            case COMMAND_TRICK_2:
            if (myPosture == POSTURE_SITTING)
            {
                doAnimationAction(pet, "sit_trick_2");
            }
            else 
            {
                doAnimationAction(pet, "trick_2");
            }
            maxHealing = .4f;
            break;
        }
        healShockWound(pet, getShockWound(pet));
        attribute[] maxAttribs = getUnmodifiedMaxAttribs(pet);
        int healAmount = (int)(maxAttribs[MIND].getValue() * maxHealing);
        addAttribModifier(pet, maxAttribs[MIND].getType(), healAmount, 0, 0, MOD_POOL);
        if (getBehavior(pet) == BEHAVIOR_CALM)
        {
            messageTo(pet, "resumeDefaultCalmBehavior", null, 8, false);
        }
    }
    public static void rechargeOtherDroid(obj_id pet, obj_id master) throws InterruptedException
    {
        obj_id target = getIntendedTarget(master);
        if (isIdValid(target) == false || !isDroidPet(target))
        {
            doConfusedEmote(pet);
            return;
        }
        if (pet == target)
        {
            doConfusedEmote(pet);
            return;
        }
        if (getDistance(pet, target) > 10f)
        {
            doConfusedEmote(pet);
            return;
        }
        follow(pet, target, 1, 2);
        color textColor = colors.PURPLE;
        showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "recharged"), .75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
        obj_id targetPetControlDevice = callable.getCallableCD(target);
        removeObjVar(targetPetControlDevice, "ai.pet.powerLevel");
        healShockWound(target, getShockWound(target));
        if (getBehavior(pet) == BEHAVIOR_CALM)
        {
            messageTo(pet, "resumeDefaultCalmBehavior", null, 8, false);
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
        powerLevel++;
        if (powerLevel == pet_lib.OUT_OF_POWER)
        {
            messageTo(pet, "powerWarningText", null, 30, false);
        }
        if (powerLevel < pet_lib.OUT_OF_POWER)
        {
            setObjVar(petControlDevice, "ai.pet.powerLevel", powerLevel);
        }
        else 
        {
            setObjVar(petControlDevice, "ai.pet.powerLevel", (pet_lib.OUT_OF_POWER + 1));
        }
    }
    public static void repairOtherDroid(obj_id pet, obj_id master) throws InterruptedException
    {
        obj_id target = getIntendedTarget(master);
        if (isIdValid(target) == false || !isDroidPet(target))
        {
            doConfusedEmote(pet);
            return;
        }
        if (pet == target)
        {
            doConfusedEmote(pet);
            return;
        }
        if (getDistance(pet, target) > 10f)
        {
            doConfusedEmote(pet);
            return;
        }
        follow(pet, target, 1, 2);
        showFlyText(target, new string_id("npc_reaction/flytext", "repaired"), .75f, colors.PURPLE);
        healShockWound(target, getShockWound(target));
        if (getBehavior(pet) == BEHAVIOR_CALM)
        {
            messageTo(pet, "resumeDefaultCalmBehavior", null, 8, false);
        }
    }
    public static void solicitTipsForMaster(obj_id pet, obj_id master) throws InterruptedException
    {
    }
    public static void doPetGroup(obj_id pet, obj_id master) throws InterruptedException
    {
        if (group.isGrouped(pet))
        {
            obj_id group = getGroupObject(pet);
            if (isIdValid(group))
            {
                queueCommand(pet, (1348589140), group, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        else 
        {
            obj_id groupLeader = group.getLeader(master);
            if (!isIdValid(groupLeader) || !exists(groupLeader))
            {
                groupLeader = master;
            }
            queueCommand(groupLeader, (-2007999144), pet, "", COMMAND_PRIORITY_DEFAULT);
            messageTo(pet, "handleGroupInvite", null, 2, false);
            sendSystemMessage(master, SID_PET_INVITED);
        }
    }
    public static void checkForName(obj_id pet, obj_id pcd) throws InterruptedException
    {
        if (!isCreaturePet(pet) && !isDroidPet(pet))
        {
            return;
        }
        obj_id master = getMaster(pet);
        if (!isIdValid(master))
        {
            return;
        }
        Vector nameList = new Vector();
        nameList.setSize(0);
        Vector nameCount = new Vector();
        nameCount.setSize(0);
        obj_var_list commandList = getObjVarList(pcd, "ai.pet.command");
        if (commandList != null && commandList.getNumItems() > 0)
        {
            int numItems = commandList.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = commandList.getObjVar(i);
                int commandNum = Integer.parseInt(ov.getName());
                String commandName = ov.getStringData();
                if (commandName.indexOf(" ") > -1)
                {
                    String name = commandName.substring(0, commandName.indexOf(" "));
                    int indexNum = utils.getElementPositionInArray(nameList, name);
                    if (indexNum == -1)
                    {
                        nameList = utils.addElement(nameList, name);
                        nameCount = utils.addElement(nameCount, 0);
                    }
                    else 
                    {
                        nameCount.set(indexNum, new Integer(((Integer)nameCount.get(indexNum)).intValue() + 1));
                    }
                }
            }
        }
        String highName = new String();
        int highCount = 0;
        for (int i = 0; i < nameList.size(); i++)
        {
            if (((Integer)nameCount.get(i)).intValue() > highCount)
            {
                highName = ((String)nameList.get(i));
                highCount = ((Integer)nameCount.get(i)).intValue();
            }
        }
        if (highCount < 2 || highName == null)
        {
            return;
        }
        if (highName.length() < 2)
        {
            highName = highName.toUpperCase();
        }
        else 
        {
            String firstLetter = highName.substring(0, 1);
            firstLetter = firstLetter.toUpperCase();
            String theName = highName.substring(1);
            highName = firstLetter + theName;
        }
        String oldName = getName(pet);
        int suffix = oldName.indexOf(")");
        if (suffix > 2)
        {
            oldName = oldName.substring(1, suffix);
        }
        String learnedName = "(" + highName + ")";
        if (highCount > 2 && !learnedName.equals(oldName) && !oldName.equals(highName) && isNameReserved(highName) == false)
        {
            setName(pet, learnedName);
            chat.thinkTo(pet, master, "?");
        }
    }
    public static void feedPet(obj_id pet, obj_id master) throws InterruptedException
    {
        if (isDroidPet(pet))
        {
            if (!eatBattery(master))
            {
                color textColor = colors.YELLOW;
                showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "nobattery"), .75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
                return;
            }
            color textColor = colors.MEDIUMPURPLE;
            showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "recharged"), 0.75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
            obj_id petControlDevice = callable.getCallableCD(pet);
            removeObjVar(petControlDevice, "ai.pet.powerLevel");
        }
        else 
        {
            if (!isWounded(pet))
            {
                color textColor = colors.YELLOW;
                showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "nothungry"), .75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
                return;
            }
            if (!eatFood(master))
            {
                color textColor = colors.YELLOW;
                showFlyTextPrivate(pet, master, new string_id("npc_reaction/flytext", "nofood"), .75f, textColor.getR(), textColor.getG(), textColor.getB(), true);
                return;
            }
        }
        stop(pet);
        faceTo(pet, master);
        faceTo(master, pet);
        doAnimationAction(master, "manipulate_medium");
        if (isCreaturePet(pet))
        {
            doAnimationAction(pet, "eat");
        }
        healShockWound(pet, getShockWound(pet));
        if (getBehavior(pet) == BEHAVIOR_CALM)
        {
            messageTo(pet, "resumeDefaultCalmBehavior", null, 8, false);
        }
    }
    public static boolean isWounded(obj_id pet) throws InterruptedException
    {
        if (getShockWound(pet) > 0)
        {
            return true;
        }
        return false;
    }
    public static boolean eatFood(obj_id master) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(master);
        if (!isIdValid(inv) || !exists(inv))
        {
            return false;
        }
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            int got = getGameObjectType(contents[i]);
            if (isGameObjectTypeOf(got, GOT_misc_food))
            {
                consumable.decrementCharges(contents[i], master);
                return true;
            }
        }
        return false;
    }
    public static boolean eatBattery(obj_id master) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(master);
        if (!isIdValid(inv) || !exists(inv))
        {
            return false;
        }
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            if ((getTemplateName(contents[i])).equals(BATTERY))
            {
                consumable.decrementCharges(contents[i], master);
                return true;
            }
        }
        return false;
    }
    public static void storePet(obj_id pet) throws InterruptedException
    {
        storePet(pet, null);
    }
    public static void storePet(obj_id pet, obj_id master) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet))
        {
            return;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (isIdValid(petControlDevice) && petControlDevice.isLoaded())
        {
            obj_id currentPet = callable.getCDCallable(petControlDevice);
            if (isIdValid(currentPet) && currentPet == pet && currentPet.isLoaded())
            {
                savePetInfo(currentPet, petControlDevice);
                setObjVar(petControlDevice, "pet.timeStored", getGameTime());
                callable.setCDCallable(petControlDevice, null);
                setCount(petControlDevice, 0);
            }
        }
        messageTo(pet, "handlePackRequest", null, 0, false);
    }
    public static boolean hasMaxStoredPetsOfType(obj_id player, int petType) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return true;
        }
        int maxStored = getMaxStoredPetsOfType(player, petType);
        int numStored = getNumStoredPetsOfType(player, petType);
        return (numStored >= maxStored);
    }
    public static int getNumStoredPetsOfType(obj_id player, int petType) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            return 0;
        }
        obj_id[] myStuff = getContents(datapad);
        if (myStuff == null)
        {
            return 0;
        }
        int callableType = callable.CALLABLE_TYPE_UNKNOWN;
        switch (petType)
        {
            case PET_TYPE_NON_AGGRO:
            case PET_TYPE_AGGRO:
            case PET_TYPE_NPC:
            case PET_TYPE_DROID:
            callableType = callable.CALLABLE_TYPE_COMBAT_PET;
            break;
            case PET_TYPE_FAMILIAR:
            callableType = callable.CALLABLE_TYPE_FAMILIAR;
            break;
            case PET_TYPE_MOUNT:
            callableType = callable.CALLABLE_TYPE_RIDEABLE;
            break;
            default:
            break;
        }
        if (callableType == callable.CALLABLE_TYPE_UNKNOWN)
        {
            return 0;
        }
        return callable.getNumStoredCDByType(player, callableType);
    }
    public static int getMaxStoredPetsOfType(obj_id master, int petType) throws InterruptedException
    {
        if (!isIdValid(master) || !exists(master))
        {
            return 0;
        }
        switch (petType)
        {
            case PET_TYPE_NON_AGGRO:
            case PET_TYPE_AGGRO:
            case PET_TYPE_NPC:
            case PET_TYPE_DROID:
            return callable.MAX_STORED_COMBAT_PETS;
            case PET_TYPE_FAMILIAR:
            return callable.MAX_STORED_FAMILIARS;
            case PET_TYPE_MOUNT:
            return callable.getMaxAllowedStoredRideables(master);
        }
        return 0;
    }
    public static int getMaxStoredPets(obj_id pet) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (!isIdValid(master))
        {
            return 1;
        }
        int petType = pet_lib.getPetType(pet);
        return getMaxStoredPetsOfType(master, petType);
    }
    public static boolean isPet(obj_id npc) throws InterruptedException
    {
        if (isValidId(npc) && exists(npc))
        {
            return hasObjVar(npc, "ai.pet");
        }
        else 
        {
            return false;
        }
    }
    public static boolean isCreaturePet(obj_id npc) throws InterruptedException
    {
        return (ai_lib.isMonster(npc) && hasObjVar(npc, "ai.pet"));
    }
    public static boolean isNpcPet(obj_id npc) throws InterruptedException
    {
        int niche = ai_lib.aiGetNiche(npc);
        if (niche == NICHE_VEHICLE)
        {
            niche = NICHE_NPC;
        }
        return (niche == NICHE_NPC && hasObjVar(npc, "ai.pet"));
    }
    public static boolean isVehiclePet(obj_id npc) throws InterruptedException
    {
        int niche = ai_lib.aiGetNiche(npc);
        return (niche == NICHE_VEHICLE && hasObjVar(npc, "ai.pet"));
    }
    public static boolean isMyPet(obj_id npc, obj_id player) throws InterruptedException
    {
        if (!isIdValid(npc) || !isIdValid(player) || !hasMaster(npc))
        {
            return false;
        }
        return (getMaster(npc) == player);
    }
    public static boolean isDroidPet(obj_id npc) throws InterruptedException
    {
        int gotType = getGameObjectType(npc);
        int niche = ai_lib.aiGetNiche(npc);
        if (gotType == GOT_data_pet_control_device || gotType == GOT_data_droid_control_device)
        {
            if (hasObjVar(npc, "pet.creatureName"))
            {
                String creatureName = getStringObjVar(npc, "pet.creatureName");
                int row = dataTableSearchColumnForString(creatureName, "creatureName", create.CREATURE_TABLE);
                if (row >= 0)
                {
                    dictionary dict = dataTableGetRow(create.CREATURE_TABLE, row);
                    niche = dict.getInt("niche");
                }
            }
        }
        return ((niche == NICHE_DROID || niche == NICHE_ANDROID) && hasObjVar(npc, "ai.pet"));
    }
    public static boolean isSpeakingPet(obj_id npc) throws InterruptedException
    {
        if (pet_lib.getPetType(npc) == pet_lib.PET_TYPE_FAMILIAR)
        {
            return false;
        }
        if (isNpcPet(npc) || ai_lib.aiGetNiche(npc) == NICHE_VEHICLE)
        {
            return true;
        }
        if (isDroidPet(npc) && ai_lib.aiGetSpecies(npc) != -1)
        {
            int speaking = dataTableGetInt("datatables/ai/species.iff", ai_lib.aiGetSpecies(npc), "Speaking");
            if (speaking == 1)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasContainer(obj_id pet) throws InterruptedException
    {
        if (isDroidPet(pet))
        {
            return (hasObjVar(pet, "ai.pet.hasContainer"));
        }
        return false;
    }
    public static boolean hasDatapad(obj_id pet) throws InterruptedException
    {
        if (isDroidPet(pet))
        {
            return (hasObjVar(pet, "ai.pet.hasDatapad"));
        }
        return false;
    }
    public static boolean isCombatDroid(obj_id npc) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(npc);
        if (!isIdValid(npc) && exists(petControlDevice))
        {
            return false;
        }
        if (ai_lib.aiGetNiche(npc) != NICHE_DROID && ai_lib.aiGetNiche(npc) != NICHE_ANDROID)
        {
            return false;
        }
        if (hasObjVar(petControlDevice, "pet.combatDroid"))
        {
            return true;
        }
        else if (hasObjVar(petControlDevice, "pet.creatureName"))
        {
            String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
            if (creatureName.equals(PROBOT_DROID_NAME) || creatureName.equals(DZ70_DROID_NAME))
            {
                if (hasObjVar(petControlDevice, "pet.nonCombatDroid"))
                {
                    return false;
                }
                else 
                {
                    return true;
                }
            }
            return false;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isCombatDroidPCD(obj_id petControlDevice) throws InterruptedException
    {
        if (!isIdValid(petControlDevice))
        {
            return false;
        }
        if (hasObjVar(petControlDevice, "pet.combatDroid"))
        {
            return true;
        }
        else if (hasObjVar(petControlDevice, "pet.creatureName"))
        {
            String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
            if (creatureName.equals(PROBOT_DROID_NAME) || creatureName.equals(DZ70_DROID_NAME))
            {
                if (hasObjVar(petControlDevice, "pet.nonCombatDroid"))
                {
                    return false;
                }
                else 
                {
                    return true;
                }
            }
            return false;
        }
        else 
        {
            return false;
        }
    }
    public static void openPetContainer(obj_id pet, obj_id master) throws InterruptedException
    {
        stop(pet);
        obj_id petControlDevice = callable.getCallableCD(pet);
        obj_id petInv = utils.getInventoryContainer(petControlDevice);
        if (!isIdValid(petInv) || !exists(petInv) || !isIdValid(master) || !exists(master))
        {
            return;
        }
        setOwner(petInv, master);
        utils.requestContainerOpen(master, petInv);
    }
    public static void openPetDatapad(obj_id pet, obj_id master) throws InterruptedException
    {
        stop(pet);
        obj_id petControlDevice = callable.getCallableCD(pet);
        obj_id petDPad = utils.getDatapad(petControlDevice);
        if (!isIdValid(petDPad) || !exists(petDPad) || !isIdValid(master) || !exists(master))
        {
            return;
        }
        if (isLowOnPower(pet))
        {
            sendSystemMessage(master, new string_id("pet/pet_menu", "droid_out_of_power"));
            return;
        }
        setOwner(petDPad, master);
        utils.requestContainerOpen(master, petDPad);
    }
    public static boolean isLowOnPower(obj_id pet) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(pet);
        int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
        if (powerLevel >= OUT_OF_POWER)
        {
            return true;
        }
        return false;
    }
    public static boolean findMaster(obj_id pet) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet) || !pet.isLoaded() || !hasMaster(pet))
        {
            return false;
        }
        obj_id master = getMaster(pet);
        if (!isIdValid(master) || !exists(master) || !master.isLoaded())
        {
            return false;
        }
        return true;
    }
    public static void removeFromList(obj_id master, obj_id pet) throws InterruptedException
    {
        if (!isIdValid(master) || !exists(master) || !isIdValid(pet) || !exists(pet))
        {
            return;
        }
        if (hasObjVar(master, "pet.petList"))
        {
            removeObjVar(master, "pet.petList");
        }
        callable.setCallable(master, null, callable.getCallableType(pet));
    }
    public static void addToPetList(obj_id master, obj_id pet) throws InterruptedException
    {
        if (!isIdValid(master) || !exists(master) || !isIdValid(pet) || !exists(pet))
        {
            return;
        }
        if (hasObjVar(master, "pet.petList"))
        {
            removeObjVar(master, "pet.petList");
        }
        if (callable.hasCallable(master, callable.getCallableType(pet), pet))
        {
            callable.storeCallable(master, callable.getCallable(master, callable.getCallableType(pet)));
        }
        callable.setCallable(master, pet, callable.getCallableType(pet));
    }
    public static void storeAllPets(obj_id master) throws InterruptedException
    {
        callable.storeCallables(master);
    }
    public static boolean isNearMedicalDroid(obj_id master, float range) throws InterruptedException
    {
        obj_id[] critters = getCreaturesInRange(getLocation(master), range);
        if (critters == null || critters.length <= 0)
        {
            return false;
        }
        for (int i = 0; i < critters.length; ++i)
        {
            if (hasObjVar(critters[i], "medpower"))
            {
                if (isMyPet(critters[i], master) && isLowOnPower(critters[i]) == false)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static float getMedicalDroidMod(obj_id master, float range) throws InterruptedException
    {
        float medMod = 1.0f;
        obj_id[] critters = getCreaturesInRange(getLocation(master), range);
        if (critters == null || critters.length <= 0)
        {
            return medMod;
        }
        for (int i = 0; i < critters.length; ++i)
        {
            if (hasObjVar(critters[i], "medpower"))
            {
                if (isMyPet(critters[i], master) && isLowOnPower(critters[i]) == false)
                {
                    medMod = getFloatObjVar(critters[i], "medpower");
                    return medMod;
                }
            }
        }
        return medMod;
    }
    public static void setupDefaultCommands(obj_id pet) throws InterruptedException
    {
        if (utils.hasScriptVar(pet, "ai.pet.commandList"))
        {
            return;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        int restrictCommands = getIntObjVar(pet, "pet.petRestriction");
        dictionary petCommandList = utils.getDictionaryScriptVar(pet, "ai.pet.commandList");
        if (petCommandList == null)
        {
            petCommandList = new dictionary();
        }
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_FOLLOW, "follow");
        petCommandList.put("follow", COMMAND_FOLLOW);
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_STAY, "stay");
        petCommandList.put("stay", COMMAND_STAY);
        if (!isNewPlayerHelperDroid(pet))
        {
            if (restrictCommands > 0)
            {
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_PATROL, "patrol");
                petCommandList.put("patrol", COMMAND_PATROL);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_SET_PATROL_POINT, "get patrol point");
                petCommandList.put("get patrol point", COMMAND_SET_PATROL_POINT);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_CLEAR_PATROL_POINTS, "clear patrol point");
                petCommandList.put("clear patrol point", COMMAND_CLEAR_PATROL_POINTS);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_ASSUME_FORMATION_1, "column formation");
                petCommandList.put("column formation", COMMAND_ASSUME_FORMATION_1);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_ASSUME_FORMATION_2, "wedge formation");
                petCommandList.put("wedge formation", COMMAND_ASSUME_FORMATION_2);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_FOLLOW_OTHER, "chase");
                petCommandList.put("chase", COMMAND_FOLLOW_OTHER);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_FRIEND, "friend");
                petCommandList.put("friend", COMMAND_FRIEND);
                setObjVar(petControlDevice, "ai.pet.command." + COMMAND_TRANSFER, "transfer");
                petCommandList.put("transfer", COMMAND_TRANSFER);
            }
            setObjVar(petControlDevice, "ai.pet.command." + COMMAND_RELEASE, "release");
            petCommandList.put("release", COMMAND_RELEASE);
            setObjVar(petControlDevice, "ai.pet.command." + COMMAND_GROUP, "group");
            petCommandList.put("group", COMMAND_GROUP);
        }
        if (pet_lib.isRepairDroid(pet))
        {
            setObjVar(petControlDevice, "ai.pet.command." + COMMAND_TRICK_2, "repair");
            petCommandList.put("repair", COMMAND_TRICK_2);
        }
        if (isCombatDroid(pet) || !isDroidPet(pet))
        {
            setObjVar(petControlDevice, "ai.pet.command." + COMMAND_ATTACK, "attack");
            petCommandList.put("attack", COMMAND_ATTACK);
            setObjVar(petControlDevice, "ai.pet.command." + COMMAND_GUARD, "guard");
            petCommandList.put("guard", COMMAND_GUARD);
        }
        utils.setScriptVar(pet, "ai.pet.commandList", petCommandList);
        setObjVar(pet, "alreadyTrained", true);
    }
    public static void setupOfficerPetCommands(obj_id pet) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(pet);
        dictionary petCommandList = utils.getDictionaryScriptVar(pet, "ai.pet.commandList");
        if (petCommandList == null)
        {
            petCommandList = new dictionary();
        }
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_ATTACK, "attack");
        petCommandList.put("attack", COMMAND_ATTACK);
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_GUARD, "guard");
        petCommandList.put("guard", COMMAND_GUARD);
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_FOLLOW, "follow");
        petCommandList.put("follow", COMMAND_FOLLOW);
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_STAY, "stay");
        petCommandList.put("stay", COMMAND_STAY);
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_RELEASE, "release");
        petCommandList.put("release", COMMAND_RELEASE);
        setObjVar(petControlDevice, "ai.pet.command." + COMMAND_GROUP, "group");
        petCommandList.put("group", COMMAND_GROUP);
        utils.setScriptVar(pet, "ai.pet.commandList", petCommandList);
        setObjVar(pet, "alreadyTrained", true);
        return;
    }
    public static void markExpansionPets(obj_id petControlDevice, obj_id pet) throws InterruptedException
    {
        if (hasObjVar(pet, "storageUnlimited"))
        {
            setObjVar(petControlDevice, "storageUnlimited", 1);
        }
    }
    public static boolean isExpansionPet(obj_id petControlDevice) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "storageUnlimited"))
        {
            return true;
        }
        return false;
    }
    public static boolean isMountPcd(obj_id pcd) throws InterruptedException
    {
        if (hasObjVar(pcd, "ai.pet.trainedMount"))
        {
            return true;
        }
        return false;
    }
    public static boolean isPetType(obj_id pcd, int petType) throws InterruptedException
    {
        int pcdsPetType = getPcdPetType(pcd);
        if (petType == pcdsPetType)
        {
            return true;
        }
        return false;
    }
    public static int getPcdPetType(obj_id pcd) throws InterruptedException
    {
        if (hasObjVar(pcd, "ai.pet.type"))
        {
            return getIntObjVar(pcd, "ai.pet.type");
        }
        return -1;
    }
    public static int getPetType(obj_id pet) throws InterruptedException
    {
        return getPetType(pet, obj_id.NULL_ID);
    }
    public static int getPetType(obj_id pet, obj_id pcd) throws InterruptedException
    {
        if (isFamiliarPetType(pet))
        {
            return PET_TYPE_FAMILIAR;
        }
        if (!isValidId(pcd))
        {
            pcd = getPetControlDevice(pet);
            if (isValidId(pcd) && exists(pcd))
            {
                if (hasObjVar(pcd, "ai.pet.trainedMount"))
                {
                    return PET_TYPE_MOUNT;
                }
            }
        }
        if (ai_lib.isMonster(pet))
        {
            String creatureName = getCreatureName(pet);
            if (creatureName == null)
            {
                return PET_TYPE_NPC;
            }
            if (utils.dataTableGetFloat(CREATURE_TABLE, creatureName, "aggressive") > 0.0f)
            {
                return PET_TYPE_AGGRO;
            }
            else 
            {
                return PET_TYPE_NON_AGGRO;
            }
        }
        else if (pet_lib.isDroidPet(pet))
        {
            return PET_TYPE_DROID;
        }
        return PET_TYPE_NPC;
    }
    public static int getPetType(String pet) throws InterruptedException
    {
        int niche = utils.dataTableGetInt(CREATURE_TABLE, pet, "niche");
        if (niche == NICHE_DROID || niche == NICHE_ANDROID)
        {
            return PET_TYPE_DROID;
        }
        else if (niche == NICHE_MONSTER || niche == NICHE_HERBIVORE || niche == NICHE_CARNIVORE || niche == NICHE_PREDATOR)
        {
            if (utils.dataTableGetFloat(CREATURE_TABLE, pet, "aggressive") > 0.0f)
            {
                return PET_TYPE_AGGRO;
            }
            else 
            {
                return PET_TYPE_NON_AGGRO;
            }
        }
        return PET_TYPE_NPC;
    }
    public static boolean isFamiliarPetType(obj_id pet) throws InterruptedException
    {
        if (hasObjVar(pet, "ai.pet.type"))
        {
            int petType = getIntObjVar(pet, "ai.pet.type");
            if (petType == PET_TYPE_FAMILIAR)
            {
                return true;
            }
        }
        else 
        {
            String creatureName = getCreatureName(pet);
            if (creatureName != null && creatureName.length() > 0)
            {
                if (isFamiliarPetTypeByCreatureName(creatureName))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isFamiliarPetTypeByCreatureName(String creatureName) throws InterruptedException
    {
        if (creatureName != null && creatureName.length() > 0)
        {
            int dataRow = dataTableSearchColumnForString(creatureName, "creatureName", FAMILAR_DATA_DATATABLE);
            if (dataRow >= 0)
            {
                return true;
            }
        }
        return false;
    }
    public static obj_id generatePetOfTypeFamiliar(String creatureName, obj_id player) throws InterruptedException
    {
        obj_id familiarControlDevice = obj_id.NULL_ID;
        dictionary familiarData = dataTableGetRow(FAMILAR_DATA_DATATABLE, creatureName);
        if (familiarData != null && !familiarData.isEmpty())
        {
            obj_id playerDatapad = utils.getPlayerDatapad(player);
            if (isIdValid(playerDatapad))
            {
                String controlDeviceTemplate = familiarData.getString("controlDeviceTemplate");
                familiarControlDevice = createObject(controlDeviceTemplate, playerDatapad, "");
                if (isIdValid(familiarControlDevice))
                {
                    setObjVar(familiarControlDevice, "pet.creatureName", creatureName);
                    attachScript(familiarControlDevice, "ai.pet_control_device");
                    setName(familiarControlDevice, getString(new string_id("mob/creature_names", creatureName)));
                    setObjVar(familiarControlDevice, "ai.pet.type", pet_lib.PET_TYPE_FAMILIAR);
                    int hasTricks = familiarData.getInt("hasTricks");
                    if (hasTricks <= 0)
                    {
                        setObjVar(familiarControlDevice, "familiar_noTricks", true);
                    }
                    return familiarControlDevice;
                }
            }
        }
        return familiarControlDevice;
    }
    public static boolean isRepairDroid(obj_id pet) throws InterruptedException
    {
        return (hasObjVar(pet, "ai.pet.isRepairDroid"));
    }
    public static boolean hasTamingMenuOption(obj_id pet, obj_id player) throws InterruptedException
    {
        if (pet_lib.hasMaster(pet))
        {
            return false;
        }
        if (utils.hasScriptVar(player, "pet.tameLoopNum"))
        {
            return false;
        }
        if (ai_lib.isInCombat(pet) || ai_lib.isAiDead(pet))
        {
            return false;
        }
        if (!hasSkill(player, "outdoors_creaturehandler_novice"))
        {
            return false;
        }
        if (!ai_lib.isMonster(pet))
        {
            detachScript(pet, "ai.pet_advance");
            return false;
        }
        if (pet_lib.getChanceToTame(pet, player) <= 0)
        {
            return false;
        }
        return true;
    }
    public static boolean isInValidUnpackLocation(obj_id master) throws InterruptedException
    {
        if (isIdValid(space_transition.getContainingShip(master)))
        {
            if (space_utils.isInStation(master))
            {
                return false;
            }
        }
        if (hasObjVar(master, "fasttame"))
        {
            if (isGod(master))
            {
                sendSystemMessageTestingOnly(master, "GODMODE MSG: You can call a pet because you have the fasttame objvar set");
            }
            return true;
        }
        return true;
    }
    public static boolean isInRestrictedScene(obj_id master) throws InterruptedException
    {
        String scene = getCurrentSceneName();
        String restrictionTable = "datatables/vehicle/vehicle_restrictions.iff";
        String[] restrictedScenes = dataTableGetStringColumn(restrictionTable, 0);
        int[] restrictions = dataTableGetIntColumn(restrictionTable, 2);
        if (isIdValid(instance.getAreaInstanceController(master)) && !instance.vehicleAllowedInInstance(instance.getAreaInstanceController(master)))
        {
            sendSystemMessage(master, new string_id("instance", "no_vehicle"));
            return true;
        }
        if (hasObjVar(master, space_dungeon.VAR_DUNGEON_ID) || (locations.isInRegion(master, "@dathomir_region_names:black_mesa") && buff.hasBuff(master, "death_troopers_no_vehicle")))
        {
            return true;
        }
        for (int i = 0; i < restrictedScenes.length; i++)
        {
            if (restrictedScenes[i].equals(scene))
            {
                if (restrictions[i] == 1)
                {
                    if (isGod(master))
                    {
                        sendSystemMessageTestingOnly(master, "GODMODE MSG:  You are in a mount restricted scene.");
                    }
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean canQuickUnpack(obj_id master) throws InterruptedException
    {
        if (isGod(master))
        {
            sendSystemMessageTestingOnly(master, "GODMODE MSG: You can quickUnpack because you are in GOD MODE:");
            return true;
        }
        if (hasObjVar(master, "fasttame"))
        {
            if (isGod(master))
            {
                sendSystemMessageTestingOnly(master, "GODMODE MSG: You can call a pet because you have the fasttame objvar set");
            }
            return true;
        }
        location yourLoc = getLocation(master);
        if (locations.isInMissionCity(yourLoc))
        {
            if (isIdValid(yourLoc.cell) && !city.isInCity(yourLoc))
            {
                return false;
            }
            if (isGod(master))
            {
                sendSystemMessageTestingOnly(master, "GODMODE MSG: You can call a pet because locations.isInMissionCity() return TRUE");
            }
            return true;
        }
        else if (isIdValid(yourLoc.cell))
        {
            if (isGod(master))
            {
                sendSystemMessageTestingOnly(master, "GODMODE MSG: You can call a pet because you are in a building, not in town");
            }
            return true;
        }
        obj_id yourCamp = camping.getCurrentCamp(master);
        if (isIdValid(yourCamp) && yourCamp.isLoaded())
        {
            if (isGod(master))
            {
                sendSystemMessageTestingOnly(master, "GODMODE MSG: You can call a pet because camping.getCurrentCamp() return TRUE");
            }
            return true;
        }
        if (isNearResidence(master))
        {
            if (isGod(master))
            {
                sendSystemMessageTestingOnly(master, "GODMODE MSG: You can call a pet because isNearResidence() returned true");
            }
            return true;
        }
        return false;
    }
    public static int getChanceToTame(obj_id pet, obj_id player) throws InterruptedException
    {
        final String creatureName = getCreatureName(pet);
        int petType = pet_lib.getPetType(pet);
        int tamingSkillMod = 0;
        if (petType == pet_lib.PET_TYPE_AGGRO)
        {
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_aggro");
        }
        else 
        {
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_non_aggro");
        }
        tamingSkillMod += getEnhancedSkillStatisticModifier(player, "tame_bonus");
        if (tamingSkillMod > 80)
        {
            tamingSkillMod = 80;
        }
        if (tamingSkillMod <= 0)
        {
            return 0;
        }
        int tameLevelSkillMod = getMaxTameLevel(player);
        int level = create.calcCreatureLevel(creatureName);
        return getChanceToTame(level, tamingSkillMod, tameLevelSkillMod);
    }
    public static int getChanceToTame(String petName, obj_id player) throws InterruptedException
    {
        int petType = pet_lib.getPetType(petName);
        int tamingSkillMod = 0;
        if (petType == pet_lib.PET_TYPE_AGGRO)
        {
            LOG("creature_taming", "tame_aggro = " + tamingSkillMod);
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_aggro");
        }
        else 
        {
            LOG("creature_taming", "tame_non_aggro = " + tamingSkillMod);
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_non_aggro");
        }
        tamingSkillMod += getEnhancedSkillStatisticModifier(player, "tame_bonus");
        LOG("creature_taming", "final tamingSkillMod = " + tamingSkillMod);
        if (tamingSkillMod > 80)
        {
            tamingSkillMod = 80;
        }
        if (tamingSkillMod <= 0)
        {
            return 0;
        }
        int level = create.calcCreatureLevel(petName);
        LOG("creature_taming", "tamingSkillMod = " + tamingSkillMod);
        LOG("creature_taming", "creatureLevel = " + level);
        int tameLevelSkillMod = getMaxTameLevel(player);
        return getChanceToTame(level, tamingSkillMod, tameLevelSkillMod);
    }
    public static int getChanceToTame(int level, int petType, obj_id player) throws InterruptedException
    {
        int tamingSkillMod = 0;
        if (petType == pet_lib.PET_TYPE_AGGRO)
        {
            LOG("creature_taming", "tame_aggro = " + tamingSkillMod);
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_aggro");
        }
        else 
        {
            LOG("creature_taming", "tame_non_aggro = " + tamingSkillMod);
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_non_aggro");
        }
        tamingSkillMod += getEnhancedSkillStatisticModifier(player, "tame_bonus");
        LOG("creature_taming", "final tamingSkillMod = " + tamingSkillMod);
        if (tamingSkillMod > 80)
        {
            tamingSkillMod = 80;
        }
        if (tamingSkillMod <= 0)
        {
            return 0;
        }
        LOG("creature_taming", "tamingSkillMod = " + tamingSkillMod);
        LOG("creature_taming", "creatureLevel = " + level);
        int tameLevelSkillMod = getMaxTameLevel(player);
        return getChanceToTame(level, tamingSkillMod, tameLevelSkillMod);
    }
    public static int getChanceToTame(int level, int skillMod, int tameLevelSkillMod) throws InterruptedException
    {
        if (level > tameLevelSkillMod)
        {
            return 0;
        }
        float chance = 0;
        chance = 40 + (33 * (skillMod / 30.0f));
        if (level > 30)
        {
            level *= 1.5;
        }
        chance -= level;
        if (chance < 5)
        {
            chance = 5;
        }
        LOG("creature_taming", "tameChance = " + chance);
        return (int)chance;
    }
    public static void setControllerName(obj_id petControlDevice, obj_id pet) throws InterruptedException
    {
        String myName = getAssignedName(pet);
        if (myName != null && !myName.equals(""))
        {
            if (!myName.equals("null"))
            {
                setName(petControlDevice, myName);
            }
            else 
            {
                String crName = getCreatureName(pet);
                debugServerConsoleMsg(petControlDevice, "WARNING: " + crName + " has an assigned name of Null - why is that?");
            }
        }
        else 
        {
            if (!myName.equals("null"))
            {
                setName(petControlDevice, getString(getNameStringId(pet)));
            }
            else 
            {
                String crName = getCreatureName(pet);
                debugServerConsoleMsg(petControlDevice, "WARNING: " + crName + " has an assigned name of Null - why is that?");
            }
        }
    }
    public static void grantPetGrowthXp(obj_id pet, int xpTotal) throws InterruptedException
    {
        obj_id pcd = callable.getCallableCD(pet);
        int growthStage = 10;
        if (hasObjVar(pcd, "ai.petAdvance.growthStage"))
        {
            growthStage = getIntObjVar(pcd, "ai.petAdvance.growthStage");
        }
        int curXp = getIntObjVar(pcd, "ai.petAdvance.xpEarned");
        int xpToLevel = getPetXpForNextLevel(pet);
        curXp += xpTotal;
        if (curXp > xpToLevel)
        {
            curXp -= xpToLevel;
            growthStage += 1;
            if (growthStage >= 10)
            {
                removeObjVar(pcd, "ai.petAdvance.growthStage");
            }
            else 
            {
                setObjVar(pcd, "ai.petAdvance.growthStage", growthStage);
            }
            if (rand(0, 100) < growthStage * 10)
            {
                earnPetTrainingAbility(pet);
            }
            earnPetTrainingPoints(pet);
        }
        setObjVar(pcd, "ai.petAdvance.xpEarned", curXp);
        if (!hasObjVar(pcd, "pet.crafted"))
        {
            float chance = (growthStage * growthStage * growthStage) / 10f;
            if (rand(0f, 100f) < chance)
            {
                learnPetAbility(pet);
            }
        }
    }
    public static int getPetXpForNextLevel(obj_id pet) throws InterruptedException
    {
        int level = getLevel(pet);
        return getXpForNextLevel(level);
    }
    public static int getPcdXpForNextLevel(obj_id pcd) throws InterruptedException
    {
        String creatureName = getStringObjVar(pcd, "pet.creatureName");
        int level = pet_lib.getAdultLevel(creatureName);
        if (hasObjVar(pcd, "creature_attribs.level"))
        {
            level = getIntObjVar(pcd, "creature_attribs.level");
        }
        return getXpForNextLevel(level);
    }
    public static int getXpForNextLevel(int level) throws InterruptedException
    {
        float modifiedLevel = (level + 4) / 2f;
        float levelSquared = modifiedLevel * modifiedLevel;
        float total = levelSquared * 300;
        return (int)total - 500;
    }
    public static int getMaxAbilitySlots(int level) throws InterruptedException
    {
        return (int)((level + 25) / 8f + 0.5f);
    }
    public static void earnPetTrainingAbility(obj_id pet) throws InterruptedException
    {
        obj_id pcd = callable.getCallableCD(pet);
        int toBeEarned = getIntObjVar(pcd, "ai.petAbility.toBeEarned");
        int available = getIntObjVar(pcd, "ai.petAbility.available");
        if (toBeEarned == 0)
        {
            return;
        }
        toBeEarned--;
        available++;
        setObjVar(pcd, "ai.petAbility.toBeEarned", toBeEarned);
        setObjVar(pcd, "ai.petAbility.available", available);
        obj_id master = getMaster(pet);
        sendSystemMessage(master, new string_id("pet/pet_ability", "earn_new_ability"));
    }
    public static void earnPetTrainingPoints(obj_id pet) throws InterruptedException
    {
        obj_id pcd = callable.getCallableCD(pet);
        int available = getIntObjVar(pcd, "ai.petAbility.available");
        int toBeEarned = getIntObjVar(pcd, "ai.petAbility.toBeEarned");
        if (available == 0 && toBeEarned == 0)
        {
            return;
        }
        int level = getLevel(pet);
        int newPts = (int)((level + 55) / 8f + 0.5f);
        int trainingPts = getIntObjVar(pcd, "ai.petAbility.trainingPts");
        trainingPts += newPts;
        setObjVar(pcd, "ai.petAbility.trainingPts", trainingPts);
        obj_id master = getMaster(pet);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("pet/pet_ability", "earn_training_points");
        pp.digitInteger = newPts;
        sendSystemMessageProse(master, pp);
    }
    public static void learnPetAbility(obj_id pet) throws InterruptedException
    {
        obj_id pcd = callable.getCallableCD(pet);
        obj_id master = getMaster(pet);
        String petAbility = getStringObjVar(pcd, "ai.petAbility.wildAbility");
        if (petAbility == null || petAbility.equals(""))
        {
            return;
        }
        int abilityCrc = getStringCrc(petAbility.toLowerCase());
        int[] abilityList = getIntArrayObjVar(master, "ch.petAbility.abilityList");
        int row = dataTableSearchColumnForString(petAbility, "abilityName", PET_ABILITY_TABLE);
        if (row == -1)
        {
            LOG("pet_ability", "pet_lib::learnPetAbility() - ERROR: Unable to find pet ability in datatable");
            return;
        }
        int[] newAbilityList = null;
        if (abilityList != null && abilityList.length > 0)
        {
            if (utils.getElementPositionInArray(abilityList, abilityCrc) > -1)
            {
                return;
            }
            newAbilityList = new int[abilityList.length + 1];
            for (int i = 0; i < abilityList.length; i++)
            {
                newAbilityList[i] = abilityList[i];
            }
            newAbilityList[newAbilityList.length - 1] = abilityCrc;
        }
        else 
        {
            newAbilityList = new int[1];
            newAbilityList[0] = abilityCrc;
        }
        setObjVar(master, "ch.petAbility.abilityList", newAbilityList);
        removeObjVar(pcd, "ai.petAbility.wildAbility");
        String msg = utils.packStringId(new string_id("pet/pet_ability", "learn_pet_ability_begin"));
        msg += " \\#pcontrast2 " + utils.packStringId(new string_id("pet/pet_ability", petAbility));
        msg += " \\#. " + utils.packStringId(new string_id("pet/pet_ability", "learn_pet_ability_end"));
        sendSystemMessage(master, msg, null);
    }
    public static void initializeWildPetAbility(obj_id pet) throws InterruptedException
    {
        String creatureName = ai_lib.getCreatureName(pet);
        if (creatureName == null || creatureName.equals(""))
        {
            return;
        }
        String wildListName = dataTableGetString(create.CREATURE_TABLE, creatureName, "wildAbilityList");
        if (wildListName == null || wildListName.equals("") || wildListName.equals("none"))
        {
            return;
        }
        dictionary abilityData = utils.dataTableGetRow(WILD_ABILITY_TABLE, wildListName);
        if (abilityData == null)
        {
            return;
        }
        float wildAbilityChance = abilityData.getFloat("wildAbilityChance");
        if (rand(0f, 100f) < wildAbilityChance)
        {
            String wildAbilityList = abilityData.getString("wildAbilityList");
            String[] wildList = split(wildAbilityList, ',');
            if (wildList != null && wildList.length > 0)
            {
                int idx = rand(0, wildList.length - 1);
                int row = dataTableSearchColumnForString(wildList[idx], "abilityName", PET_ABILITY_TABLE);
                if (row == -1)
                {
                    return;
                }
                setObjVar(pet, "ai.petAbility.wildAbility", wildList[idx]);
            }
        }
    }
    public static void validateFollowTarget(obj_id pet, obj_id target) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (target == master)
        {
            return;
        }
        if (stealth.hasServerCoverState(target))
        {
            doStayCommand(pet, master);
            doConfusedEmote(pet);
        }
        return;
    }
    public static void trainPetAbility(obj_id pet, String abilityName) throws InterruptedException
    {
        int ability = getStringCrc(abilityName.toLowerCase());
        trainPetAbility(pet, ability);
    }
    public static void trainPetAbility(obj_id pet, int ability) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet))
        {
            sendSystemMessage(getSelf(), new string_id("pet/pet_ability", "train_ability_failed"));
            return;
        }
        obj_id pcd = callable.getCallableCD(pet);
        obj_id master = getMaster(pet);
        if (!hasSkill(master, "outdoors_creaturehandler_novice"))
        {
            sendSystemMessage(master, new string_id("pet/pet_ability", "train_ability_ch_only"));
            return;
        }
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList == null)
        {
            abilityList = new int[0];
        }
        if (utils.getElementPositionInArray(abilityList, ability) > -1)
        {
            sendSystemMessage(master, new string_id("pet/pet_ability", "train_ability_already_known"));
            return;
        }
        int row = dataTableSearchColumnForInt(ability, "abilityCrc", PET_ABILITY_TABLE);
        if (row == -1)
        {
            LOG("pet_ability", "pet_lib::trainPetAbility() - ERROR: Unable to find pet ability in datatable");
            return;
        }
        dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
        int available = getIntObjVar(pcd, "ai.petAbility.available");
        if (available < 1)
        {
            sendSystemMessage(master, new string_id("pet/pet_ability", "train_ability_no_slots"));
            return;
        }
        int trainingCost = abilityData.getInt("trainingCost");
        int trainingPts = getIntObjVar(pcd, "ai.petAbility.trainingPts");
        if (trainingPts < trainingCost)
        {
            sendSystemMessage(master, new string_id("pet/pet_ability", "train_ability_no_points"));
            return;
        }
        String prereq = abilityData.getString("prereq");
        int[] newAbilityList = null;
        if (prereq == null || prereq.equals(""))
        {
            newAbilityList = new int[abilityList.length + 1];
            for (int i = 0; i < abilityList.length; i++)
            {
                newAbilityList[i] = abilityList[i];
            }
            newAbilityList[newAbilityList.length - 1] = ability;
        }
        else 
        {
            int prereqCrc = getStringCrc(prereq.toLowerCase());
            int idx = -1;
            for (int i = 0; i < abilityList.length; i++)
            {
                if (abilityList[i] == prereqCrc)
                {
                    idx = i;
                }
            }
            if (idx == -1)
            {
                String msg = utils.packStringId(new string_id("pet/pet_ability", "need_prereq_begin"));
                msg += " \\#pcontrast2 " + utils.packStringId(new string_id("pet/pet_ability", prereq));
                msg += " \\#. " + utils.packStringId(new string_id("pet/pet_ability", "need_prereq_end"));
                sendSystemMessage(master, msg, null);
                return;
            }
            newAbilityList = abilityList;
            newAbilityList[idx] = ability;
        }
        setObjVar(pcd, "ai.petAbility.abilityList", newAbilityList);
        setObjVar(pcd, "ai.petAbility.available", available - 1);
        setObjVar(pcd, "ai.petAbility.trainingPts", trainingPts - trainingCost);
        String msg = utils.packStringId(new string_id("pet/pet_ability", "train_ability_begin"));
        msg += " \\#pcontrast1 " + utils.packStringId(new string_id("pet/pet_ability", abilityData.getString("abilityName")));
        msg += " \\#. " + utils.packStringId(new string_id("pet/pet_ability", "train_ability_end"));
        sendSystemMessage(master, msg, null);
    }
    public static String getPetAbilityFromCrc(int ability) throws InterruptedException
    {
        int row = dataTableSearchColumnForInt(ability, "abilityCrc", PET_ABILITY_TABLE);
        if (row == -1)
        {
            return "";
        }
        return dataTableGetString(PET_ABILITY_TABLE, row, "abilityName");
    }
    public static void initializeTrainedPetAbilities(obj_id pcd, obj_id pet) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList == null)
        {
            return;
        }
        if (abilityList != null && abilityList.length > 0)
        {
            for (int i = 0; i < abilityList.length; i++)
            {
                int row = dataTableSearchColumnForInt(abilityList[i], "abilityCrc", PET_ABILITY_TABLE);
                if (row == -1)
                {
                    continue;
                }
                dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
                if (abilityData == null)
                {
                    continue;
                }
                switch (abilityData.getInt("type"))
                {
                    case PET_ABILITY_INNATE:
                    addInnateAbilityStatBonus(pet, abilityData);
                    addInnateScriptVar(pet, abilityData);
                    addInnateBuff(pet, abilityData);
                    break;
                    case PET_ABILITY_TRIGGERED:
                    addTriggeredAbility(pet, abilityData);
                    break;
                    case PET_ABILITY_ACTION:
                    addActionAbility(pet, abilityData);
                    break;
                }
            }
        }
    }
    public static void addInnateAbilityStatBonus(obj_id pet, dictionary abilityData) throws InterruptedException
    {
        int stat = abilityData.getInt("statMod");
        if (stat == -1)
        {
            return;
        }
        int statValBonus = abilityData.getInt("statModVal");
        float statPctBonus = abilityData.getFloat("statModPct");
        int curStat = getMaxAttrib(pet, stat);
        int newStat = curStat + (int)(curStat * statPctBonus) + statValBonus;
        setMaxAttrib(pet, stat, newStat);
        setAttrib(pet, stat, newStat);
    }
    public static void addInnateScriptVar(obj_id pet, dictionary abilityData) throws InterruptedException
    {
        String varName = abilityData.getString("scriptVar");
        if (varName == null || varName.equals(""))
        {
            return;
        }
        float varValue = abilityData.getFloat("scriptVarVal");
        deltadictionary dd = pet.getScriptVars();
        if (pet.hasScriptVar(varName))
        {
            Object curValue = dd.getObject(varName);
            if (curValue instanceof Integer)
            {
                int newValue = (((Integer)curValue)).intValue() + (int)varValue;
                dd.put(varName, varValue);
            }
            else if (curValue instanceof Float)
            {
                float newValue = (((Float)curValue)).floatValue() + varValue;
                dd.put(varName, varValue);
            }
            else 
            {
                LOG("pet_ability", "Scriptvar name already present with unknown type, leaving current scriptvar");
            }
        }
        else 
        {
            dd.put(varName, varValue);
        }
    }
    public static void addInnateBuff(obj_id pet, dictionary abilityData) throws InterruptedException
    {
        String buffName = abilityData.getString("selfBuffName");
        if (buffName == null || buffName.equals(""))
        {
            return;
        }
        int duration = abilityData.getInt("duration");
        if (duration > 0)
        {
            buff.applyBuff(pet, buffName, duration);
        }
        else 
        {
            buff.applyBuff(pet, buffName);
        }
    }
    public static void addTriggeredAbility(obj_id pet, dictionary abilityData) throws InterruptedException
    {
        String actionName = abilityData.getString("actionName");
        if (actionName != null && !actionName.equals(""))
        {
            grantCommand(pet, actionName);
        }
    }
    public static void addActionAbility(obj_id pet, dictionary abilityData) throws InterruptedException
    {
        String actionName = abilityData.getString("actionName");
        if (actionName != null && !actionName.equals(""))
        {
            grantCommand(pet, actionName);
        }
    }
    public static int getPetAbilityHealthBonus(obj_id pcd, int baseHealth) throws InterruptedException
    {
        int healthBonus = 0;
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList != null && abilityList.length > 0)
        {
            for (int i = 0; i < abilityList.length; i++)
            {
                int row = dataTableSearchColumnForInt(abilityList[i], "abilityCrc", PET_ABILITY_TABLE);
                if (row == -1)
                {
                    continue;
                }
                dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
                if (abilityData == null)
                {
                    continue;
                }
                if (abilityData.getInt("statMod") == HEALTH)
                {
                    int statValBonus = abilityData.getInt("statModVal");
                    float statPctBonus = abilityData.getFloat("statModPct");
                    healthBonus += (int)(baseHealth * statPctBonus) + statValBonus;
                    baseHealth += healthBonus;
                }
            }
        }
        return healthBonus;
    }
    public static int getPetAbilityRegenBonus(obj_id pcd, int baseRegen) throws InterruptedException
    {
        int regenBonus = 0;
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList != null && abilityList.length > 0)
        {
            for (int i = 0; i < abilityList.length; i++)
            {
                int row = dataTableSearchColumnForInt(abilityList[i], "abilityCrc", PET_ABILITY_TABLE);
                if (row == -1)
                {
                    continue;
                }
                dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
                if (abilityData == null)
                {
                    continue;
                }
                if (abilityData.getInt("statMod") == CONSTITUTION)
                {
                    int statValBonus = abilityData.getInt("statModVal");
                    float statPctBonus = abilityData.getFloat("statModPct");
                    regenBonus += (int)(baseRegen * statPctBonus) + statValBonus;
                    baseRegen += regenBonus;
                }
            }
        }
        return regenBonus;
    }
    public static int[] getPetAbilityDamageBonus(obj_id pcd, int baseMinDamage, int baseMaxDamage) throws InterruptedException
    {
        int[] damageBonus = 
        {
            0,
            0
        };
        float dmgPctBonus = 0f;
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList != null && abilityList.length > 0)
        {
            for (int i = 0; i < abilityList.length; i++)
            {
                int row = dataTableSearchColumnForInt(abilityList[i], "abilityCrc", PET_ABILITY_TABLE);
                if (row == -1)
                {
                    continue;
                }
                dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
                if (abilityData == null)
                {
                    continue;
                }
                String scriptVar = abilityData.getString("scriptVar");
                if (scriptVar != null && scriptVar.equals("ai.combat.damagePctBonus"))
                {
                    dmgPctBonus += abilityData.getFloat("scriptVarVal");
                }
            }
        }
        damageBonus[0] = (int)(baseMinDamage * dmgPctBonus);
        damageBonus[1] = (int)(baseMaxDamage * dmgPctBonus);
        return damageBonus;
    }
    public static float getPetAbilityAttackSpdBonus(obj_id pcd, float baseSpeed) throws InterruptedException
    {
        float speedBonus = 0f;
        float spdPctBonus = 0f;
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList != null && abilityList.length > 0)
        {
            for (int i = 0; i < abilityList.length; i++)
            {
                int row = dataTableSearchColumnForInt(abilityList[i], "abilityCrc", PET_ABILITY_TABLE);
                if (row == -1)
                {
                    continue;
                }
                dictionary abilityData = dataTableGetRow(PET_ABILITY_TABLE, row);
                if (abilityData == null)
                {
                    continue;
                }
                String scriptVar = abilityData.getString("scriptVar");
                if (scriptVar != null && scriptVar.equals("ai.combat.attackSpdPctBonus"))
                {
                    spdPctBonus += abilityData.getFloat("scriptVarVal");
                }
            }
        }
        speedBonus = -1 * (baseSpeed * spdPctBonus);
        return speedBonus;
    }
    public static boolean isPetFrenzied(obj_id pet) throws InterruptedException
    {
        return (utils.hasScriptVar(pet, "buff.petFrenzy_1.value") || utils.hasScriptVar(pet, "buff.petFrenzy_2.value") || utils.hasScriptVar(pet, "buff.petFrenzy_3.value"));
    }
    public static float getPetFrenzyAmount(obj_id pet) throws InterruptedException
    {
        if (utils.hasScriptVar(pet, "buff.petFrenzy_3.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petFrenzy_3.value");
        }
        if (utils.hasScriptVar(pet, "buff.petFrenzy_2.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petFrenzy_2.value");
        }
        if (utils.hasScriptVar(pet, "buff.petFrenzy_1.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petFrenzy_1.value");
        }
        return 0f;
    }
    public static boolean isPetDefenseStance(obj_id pet) throws InterruptedException
    {
        return (utils.hasScriptVar(pet, "buff.petDefense_1.value") || utils.hasScriptVar(pet, "buff.petDefense_2.value") || utils.hasScriptVar(pet, "buff.petDefense_3.value"));
    }
    public static float getPetDefenseStanceAmount(obj_id pet) throws InterruptedException
    {
        if (utils.hasScriptVar(pet, "buff.petDefense_3.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petDefense_3.value");
        }
        if (utils.hasScriptVar(pet, "buff.petDefense_2.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petDefense_2.value");
        }
        if (utils.hasScriptVar(pet, "buff.petDefense_1.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petDefense_1.value");
        }
        return 0f;
    }
    public static boolean isPetDefenseBreak(obj_id pet) throws InterruptedException
    {
        return (utils.hasScriptVar(pet, "buff.petDefenseBreakAttack.value") || utils.hasScriptVar(pet, "buff.petDefenseBreakAttack_1.value"));
    }
    public static float getPetDefenseBreakAmount(obj_id pet) throws InterruptedException
    {
        if (utils.hasScriptVar(pet, "buff.petDefenseBreakAttack_1.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petDefenseBreakAttack_1.value");
        }
        if (utils.hasScriptVar(pet, "buff.petDefenseBreakAttack.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petDefenseBreakAttack.value");
        }
        return 0f;
    }
    public static obj_id getShieldingPet(obj_id player) throws InterruptedException
    {
        float closestPet = 10f;
        obj_id pet = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
        if (!isIdValid(pet) || !exists(pet) || !isPetShielding(pet))
        {
            return null;
        }
        return pet;
    }
    public static boolean isPetShielding(obj_id pet) throws InterruptedException
    {
        return (utils.hasScriptVar(pet, "buff.petShield_1.value") || utils.hasScriptVar(pet, "buff.petShield_2.value") || utils.hasScriptVar(pet, "buff.petShield_3.value"));
    }
    public static float getPetShieldAmount(obj_id pet) throws InterruptedException
    {
        if (utils.hasScriptVar(pet, "buff.petShield_3.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petShield_3.value");
        }
        if (utils.hasScriptVar(pet, "buff.petShield_2.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petShield_2.value");
        }
        if (utils.hasScriptVar(pet, "buff.petShield_1.value"))
        {
            return utils.getFloatScriptVar(pet, "buff.petShield_1.value");
        }
        return 0f;
    }
    public static void setPetStatsByGrowth(obj_id pet, int growthStage) throws InterruptedException
    {
    }
    public static void setCraftedPetStatsByGrowth(obj_id petControlDevice, obj_id pet, int growthStage) throws InterruptedException
    {
        setObjVar(pet, "combat.intCombatXP", 0);
        if (growthStage < 1)
        {
            growthStage = 1;
        }
        else if (growthStage > 10)
        {
            growthStage = 10;
        }
        String creatureName = getCreatureName(pet);
        if (creatureName == null)
        {
            return;
        }
        dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, creatureName);
        if (creatureDict == null)
        {
            return;
        }
        int level = getIntObjVar(petControlDevice, "creature_attribs.level");
        if (level == 0)
        {
            level = getLevel(pet);
        }
        if (level < 1)
        {
            level = 1;
        }
        obj_id player = utils.getContainingPlayer(petControlDevice);
        int myHealth = getIntObjVar(petControlDevice, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]);
        int healthRegen = getIntObjVar(petControlDevice, "creature_attribs." + create.MAXATTRIBNAMES[CONSTITUTION]);
        int[] armorData = new int[10];
        armorData[1] = getIntObjVar(petControlDevice, "creature_attribs.general_protection");
        float stateResist = getFloatObjVar(petControlDevice, "creature_attribs.stateResist");
        float critChance = getFloatObjVar(petControlDevice, "creature_attribs.critChance");
        float critSave = getFloatObjVar(petControlDevice, "creature_attribs.critSave");
        float aggroBonus = getFloatObjVar(petControlDevice, "creature_attribs.aggroBonus");
        int toHit = getIntObjVar(petControlDevice, "creature_attribs.toHitChance") - 5;
        int defenseValue = getIntObjVar(petControlDevice, "creature_attribs.defenseValue") - 5;
        float wpnSpeed = getFloatObjVar(petControlDevice, "creature_attribs.attackSpeed");
        float spdBonus = getPetAbilityAttackSpdBonus(petControlDevice, wpnSpeed);
        int minDamage = getIntObjVar(petControlDevice, "creature_attribs.minDamage");
        int maxDamage = getIntObjVar(petControlDevice, "creature_attribs.maxDamage");
        if (isIdValid(player) && exists(player))
        {
            if (level > 60 && !craftinglib.isTrader(player))
            {
                level = 60;
            }
            dictionary droidDefaultStatsDict = dataTableGetRow(TBL_MOB_STAT_BALANCE, level - 1);
            myHealth = droidDefaultStatsDict.getInt("HP");
            toHit = droidDefaultStatsDict.getInt("ToHit") - 5;
            defenseValue = droidDefaultStatsDict.getInt("Def") - 5;
            float creatureAttribs_dps = droidDefaultStatsDict.getFloat("damagePerSecond");
            minDamage = (int)creatureAttribs_dps;
            maxDamage = (int)creatureAttribs_dps * 3;
        }
        setLevel(pet, level);
        utils.setScriptVar(pet, "ai.level", level);
        if (hasObjVar(pet, "difficultyClass"))
        {
            removeObjVar(pet, "difficultyClass");
        }
        if (myHealth < 1)
        {
            myHealth = 1;
        }
        setMaxAttrib(pet, HEALTH, myHealth);
        setAttrib(pet, HEALTH, myHealth);
        setMaxAttrib(pet, ACTION, 1000);
        setAttrib(pet, ACTION, 1000);
        setMaxAttrib(pet, MIND, 1000);
        setAttrib(pet, MIND, 1000);
        fixMinRegenStats(pet);
        if (!ai_lib.isDroid(pet) && !ai_lib.isAndroid(pet))
        {
            if (healthRegen < 1)
            {
                healthRegen = 1;
            }
            setRegenRate(pet, CONSTITUTION, 150);
        }
        if (armorData != null)
        {
            create.initializeArmor(pet, armorData);
        }
        float baseScale = 1.0f;
        if (utils.hasScriptVar(pet, "ai.baseScale"))
        {
            baseScale = utils.getFloatScriptVar(pet, "ai.baseScale");
        }
        float minScale = 0;
        float maxScale = 9999;
        float scaleAdjust = 1;
        if (hasObjVar(petControlDevice, "creature_attribs.scale"))
        {
            scaleAdjust = getFloatObjVar(petControlDevice, "creature_attribs.scale");
        }
        else 
        {
            minScale = creatureDict.getFloat("minScale");
            maxScale = creatureDict.getFloat("maxScale");
            scaleAdjust = (minScale + maxScale) / 2.0f;
        }
        float realScale = baseScale * scaleAdjust;
        float babyScale = realScale * .3f;
        float scaleIncrement = (realScale - babyScale) / 10.0f;
        float finalScale = babyScale + (scaleIncrement * growthStage);
        if (finalScale > maxScale)
        {
            finalScale = maxScale;
        }
        if (finalScale < babyScale)
        {
            finalScale = babyScale;
        }
        setScale(pet, finalScale);
        utils.setScriptVar(pet, "ai.combat.stateResist", stateResist);
        utils.setScriptVar(pet, "ai.combat.critChance", critChance);
        utils.setScriptVar(pet, "ai.combat.critSave", critSave);
        utils.setScriptVar(pet, "ai.combat.aggroBonus", aggroBonus);
        create.applySkillStatisticModifiers(pet, toHit, defenseValue);
        if (wpnSpeed <= 1.0f)
        {
            wpnSpeed = 1.0f;
        }
        wpnSpeed += spdBonus;
        int[] dmgBonus = getPetAbilityDamageBonus(petControlDevice, minDamage, maxDamage);
        minDamage += dmgBonus[0];
        maxDamage += dmgBonus[1];
        if (minDamage < 5)
        {
            minDamage = 5;
        }
        if (maxDamage < 10)
        {
            maxDamage = 10;
        }
        obj_id creatureWeapon = getCurrentWeapon(pet);
        setWeaponMaxDamage(creatureWeapon, maxDamage);
        setWeaponMinDamage(creatureWeapon, minDamage);
        setWeaponAttackSpeed(creatureWeapon, wpnSpeed);
        weapons.setWeaponData(creatureWeapon);
        creatureWeapon = aiGetPrimaryWeapon(pet);
        if (isIdValid(creatureWeapon))
        {
            setWeaponMaxDamage(creatureWeapon, maxDamage);
            setWeaponMinDamage(creatureWeapon, minDamage);
            setWeaponAttackSpeed(creatureWeapon, wpnSpeed);
            weapons.setWeaponData(creatureWeapon);
        }
        creatureWeapon = aiGetSecondaryWeapon(pet);
        if (isIdValid(creatureWeapon))
        {
            setWeaponMaxDamage(creatureWeapon, maxDamage);
            setWeaponMinDamage(creatureWeapon, minDamage);
            setWeaponAttackSpeed(creatureWeapon, wpnSpeed);
            weapons.setWeaponData(creatureWeapon);
        }
        String rangedWeaponName = getStringObjVar(petControlDevice, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON);
        if (rangedWeaponName != null && !rangedWeaponName.equals(""))
        {
            obj_id inventory = getObjectInSlot(pet, "inventory");
            if (isIdValid(inventory))
            {
                obj_id rangedWeapon = createObject(rangedWeaponName, inventory, "");
                if (isIdValid(rangedWeapon))
                {
                    setWeaponAttackSpeed(rangedWeapon, wpnSpeed);
                    setWeaponMaxDamage(rangedWeapon, maxDamage);
                    setWeaponMinDamage(rangedWeapon, minDamage);
                    weapons.setWeaponData(rangedWeapon);
                }
            }
        }
        int[] specialAttacks = new int[2];
        specialAttacks[0] = getIntObjVar(petControlDevice, "creature_attribs." + bio_engineer.ATTRIB_DICT_SPECIAL_ATTACK_1);
        specialAttacks[1] = getIntObjVar(petControlDevice, "creature_attribs." + bio_engineer.ATTRIB_DICT_SPECIAL_ATTACK_2);
        utils.setScriptVar(pet, "ai.combat.creatureSpecialAttacks", specialAttacks);
        if (!isNewPlayerHelperDroid(pet))
        {
            int colorIdx = hue.getVarColorIndex(petControlDevice, hue.INDEX_1);
            if (hasObjVar(petControlDevice, "creature_attribs.hue"))
            {
                colorIdx = getIntObjVar(petControlDevice, "creature_attribs.hue");
            }
            hue.setColor(pet, hue.INDEX_1, colorIdx);
        }
    }
    public static void fixMinRegenStats(obj_id pet) throws InterruptedException
    {
        int healthAttrib = 0;
        if (!ai_lib.isDroid(pet) && !ai_lib.isAndroid(pet))
        {
            healthAttrib = calcRegenStat(getMaxAttrib(pet, HEALTH));
        }
        setRegenRate(pet, CONSTITUTION, 150);
        setRegenRate(pet, STAMINA, 150);
        setRegenRate(pet, WILLPOWER, 150);
    }
    public static int calcRegenStat(int poolStat) throws InterruptedException
    {
        int regenStat = poolStat / 10;
        if (regenStat > MAX_REGEN_STAT)
        {
            regenStat = MAX_REGEN_STAT;
        }
        else if (regenStat < 1)
        {
            regenStat = 1;
        }
        return regenStat;
    }
    public static boolean isNearResidence(obj_id master) throws InterruptedException
    {
        obj_id yourHome = player_structure.getResidence(master);
        if (!isIdValid(yourHome))
        {
            return false;
        }
        if (!yourHome.isLoaded())
        {
            return false;
        }
        return (getDistance(master, yourHome) < 60.0f);
    }
    public static boolean wasInCombatRecently(obj_id currentPet, obj_id master, boolean systemMessage) throws InterruptedException
    {
        if (!utils.hasScriptVar(currentPet, "pet.combatEnded"))
        {
            return false;
        }
        int combatEnded = utils.getIntScriptVar(currentPet, "pet.combatEnded");
        int canStoreAgain = combatEnded + 60;
        int currentTime = getGameTime();
        if (currentTime >= canStoreAgain)
        {
            return false;
        }
        if (systemMessage)
        {
            int timeLeft = canStoreAgain - currentTime;
            if (timeLeft > 1)
            {
                prose_package pp = prose.getPackage(new string_id("pet/pet_menu", "prose_cant_store_yet"), timeLeft);
                sendSystemMessageProse(master, pp);
            }
            else 
            {
                sendSystemMessage(master, new string_id("pet/pet_menu", "cant_store_1sec"));
            }
        }
        return true;
    }
    public static boolean wasInCombat(obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, COMBAT_ENDED))
        {
            return false;
        }
        int combatEnded = utils.getIntScriptVar(player, COMBAT_ENDED);
        int callPetDelay = combatEnded + POST_COMBAT_DELAY;
        int currentTime = getGameTime();
        if (currentTime >= callPetDelay)
        {
            return false;
        }
        int timeLeft = callPetDelay - currentTime;
        return true;
    }
    public static int modifyCallTime(obj_id player, String combatEnded, int callDelay, int combatDelay) throws InterruptedException
    {
        int combatEnd = utils.getIntScriptVar(player, combatEnded);
        int combatEndDifference = getGameTime() - combatEnd;
        int combatDelayDifference = combatDelay - combatEndDifference;
        if (combatDelayDifference < callDelay)
        {
            return callDelay;
        }
        if (combatDelayDifference > combatDelay)
        {
            return combatDelay;
        }
        return combatDelayDifference;
    }
    public static int getTimeLeft(obj_id player, String toCheckFor, String modifiedTime) throws InterruptedException
    {
        int currentTime = getGameTime();
        int timeCalled = utils.getIntScriptVar(player, toCheckFor);
        if (timeCalled < 1)
        {
            return -1;
        }
        int timePenalty = utils.getIntScriptVar(player, modifiedTime);
        int timeDone = timeCalled + timePenalty;
        int timeLeft = timeDone - currentTime;
        return timeLeft;
    }
    public static int getCurrentPetLevels(obj_id master) throws InterruptedException
    {
        if (!isIdValid(master))
        {
            return 0;
        }
        if (!isPlayer(master))
        {
            return 0;
        }
        if (!callable.hasCallable(master, callable.CALLABLE_TYPE_COMBAT_PET))
        {
            return 0;
        }
        obj_id pet = callable.getCallable(master, callable.CALLABLE_TYPE_COMBAT_PET);
        return getLevel(pet);
    }
    public static boolean canControlPetsOfLevel(obj_id player, int petType, int petLevel, String creatureName) throws InterruptedException
    {
        if (!hasSkill(player, "outdoors_creaturehandler_novice"))
        {
            return true;
        }
        if (petType != PET_TYPE_NON_AGGRO && petType != PET_TYPE_AGGRO)
        {
            return true;
        }
        if (creatureName == null)
        {
            return false;
        }
        int tameLevelSkillMod = getMaxTameLevel(player);
        int tamingSkillMod = 0;
        if (petType == pet_lib.PET_TYPE_AGGRO)
        {
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_aggro");
        }
        else 
        {
            tamingSkillMod = getEnhancedSkillStatisticModifier(player, "tame_non_aggro");
        }
        tamingSkillMod += getEnhancedSkillStatisticModifier(player, "tame_bonus");
        int chanceToTame = getChanceToTame(petLevel, tamingSkillMod, tameLevelSkillMod);
        if (chanceToTame < 1)
        {
            sendSystemMessage(player, new string_id("pet/pet_menu", "sys_lack_skill"));
            return false;
        }
        int numOutNow = getCurrentPetLevels(player);
        if ((numOutNow + petLevel) > tameLevelSkillMod)
        {
            if (numOutNow == 0)
            {
                sendSystemMessage(player, new string_id("pet/pet_menu", "too_hard"));
            }
            else 
            {
                sendSystemMessage(player, new string_id("pet/pet_menu", "control_exceeded"));
            }
            return false;
        }
        return true;
    }
    public static int getMaxTameLevel(obj_id player) throws InterruptedException
    {
        int baseTameLevel = getSkillStatMod(player, "tame_level");
        int bonusTameLevel = getEnhancedSkillStatisticModifier(player, "tame_level_bonus");
        int playerLevel = getLevel(player);
        if (baseTameLevel + bonusTameLevel > playerLevel)
        {
            if (baseTameLevel > playerLevel)
            {
                return baseTameLevel;
            }
            bonusTameLevel = playerLevel - baseTameLevel;
            if (bonusTameLevel < 0)
            {
                bonusTameLevel = 0;
            }
        }
        return (baseTameLevel + bonusTameLevel);
    }
    public static int getModifiedAttribDamage(obj_id target, int attrib) throws InterruptedException
    {
        int max = getWoundedMaxAttrib(target, attrib);
        int current = getAttrib(target, attrib);
        if (max == ATTRIB_ERROR || current == ATTRIB_ERROR)
        {
            return ATTRIB_ERROR;
        }
        return max - current;
    }
    public static void storeDamage(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(petControlDevice))
        {
            return;
        }
        int[] mainAttribs = new int[6];
        mainAttribs[HEALTH_DAMAGE] = getModifiedAttribDamage(pet, HEALTH);
        mainAttribs[ACTION_DAMAGE] = getModifiedAttribDamage(pet, ACTION);
        mainAttribs[MIND_DAMAGE] = getModifiedAttribDamage(pet, MIND);
        mainAttribs[HEALTH_WOUND] = getAttribWound(pet, HEALTH);
        if (petControlDevice.isLoaded())
        {
            setObjVar(petControlDevice, "ai.pet.currentStats", mainAttribs);
            setObjVar(petControlDevice, "pet.timeStored", getGameTime());
        }
        else 
        {
            dictionary parms = new dictionary();
            parms.put("attribs", mainAttribs);
            parms.put("time", getGameTime());
            messageTo(petControlDevice, "handleStatUpdate", parms, 0, true);
        }
    }
    public static void restoreDamage(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(petControlDevice))
        {
            return;
        }
        if (!hasObjVar(petControlDevice, "ai.pet.currentStats"))
        {
            return;
        }
        int[] mainAttribs = getIntArrayObjVar(petControlDevice, "ai.pet.currentStats");
        int timeStored = getIntObjVar(petControlDevice, "pet.timeStored");
        if (timeStored > 0)
        {
            timeStored = getGameTime() - timeStored;
            int amountHealed = (timeStored / 5) * 2;
            mainAttribs[HEALTH_DAMAGE] -= amountHealed;
            mainAttribs[ACTION_DAMAGE] -= amountHealed;
            mainAttribs[MIND_DAMAGE] -= amountHealed;
            removeObjVar(petControlDevice, "pet.timeStored");
        }
        int currentHealth = getMaxAttrib(pet, HEALTH);
        if (mainAttribs[HEALTH_DAMAGE] > 0)
        {
            currentHealth = currentHealth - mainAttribs[HEALTH_DAMAGE];
            if (currentHealth < 1)
            {
                currentHealth = 1;
            }
            setAttrib(pet, HEALTH, currentHealth);
        }
        int currentAction = getMaxAttrib(pet, ACTION);
        if (mainAttribs[ACTION_DAMAGE] > 0)
        {
            currentAction = currentAction - mainAttribs[ACTION_DAMAGE];
            if (currentAction < 1)
            {
                currentAction = 1;
            }
            setAttrib(pet, ACTION, currentAction);
        }
        int currentMind = getMaxAttrib(pet, MIND);
        if (mainAttribs[MIND_DAMAGE] > 0)
        {
            currentMind = currentMind - mainAttribs[MIND_DAMAGE];
            if (currentMind < 1)
            {
                currentMind = 1;
            }
            setAttrib(pet, MIND, currentMind);
        }
    }
    public static void killPet(obj_id pet) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (isIdValid(petControlDevice))
        {
            dictionary params = new dictionary();
            params.put("killer", utils.getObjIdScriptVar(pet, "killer"));
            messageTo(petControlDevice, "handlePetDeathblow", params, 0, false);
        }
        else 
        {
            pclib.coupDeGrace(pet, pet);
        }
        return;
    }
    public static void reduceStatsForVitality(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        int maxVitality = 100 - getIntObjVar(petControlDevice, "pet.maxVitality");
        int vitality = maxVitality - getIntObjVar(petControlDevice, "pet.vitality");
        if (vitality == 100)
        {
            return;
        }
        if (vitality > 75)
        {
            return;
        }
        else if (vitality > 50)
        {
            vitality = 75;
        }
        else if (vitality > 25)
        {
            vitality = 50;
        }
        else 
        {
            vitality = 25;
        }
        int currentHealth = getMaxAttrib(pet, HEALTH);
        setMaxAttrib(pet, HEALTH, getReducedStatValue(currentHealth, vitality));
        int currentAction = getMaxAttrib(pet, ACTION);
        setMaxAttrib(pet, ACTION, getReducedStatValue(currentAction, vitality));
        int currentMind = getMaxAttrib(pet, MIND);
        setMaxAttrib(pet, MIND, getReducedStatValue(currentMind, vitality));
    }
    public static int getReducedStatValue(int oldValue, int vitality) throws InterruptedException
    {
        if (vitality == 100)
        {
            return oldValue;
        }
        if (vitality > 75)
        {
            return oldValue;
        }
        else if (vitality > 50)
        {
            vitality = 75;
        }
        else if (vitality > 25)
        {
            vitality = 50;
        }
        else 
        {
            vitality = 25;
        }
        float reduction = vitality / 100f;
        int newValue = (int)(oldValue * reduction);
        return newValue;
    }
    public static boolean performVitalityHeal(obj_id player, obj_id pet, obj_id petControlDevice, obj_id pet_med) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(petControlDevice) || !isIdValid(pet_med))
        {
            return false;
        }
        int vitality = getIntObjVar(petControlDevice, "pet.vitality");
        int maxVitality = getIntObjVar(petControlDevice, "pet.maxVitality");
        int strength = getIntObjVar(pet_med, "consumable.strength");
        int tmpStrength = (int)strength / 25;
        if (tmpStrength < 1)
        {
            tmpStrength = 1;
        }
        int penalty = (int)vitality / 5;
        penalty = (int)((penalty / tmpStrength) + .99f);
        if (penalty < 1)
        {
            penalty = 1;
        }
        vitality = 0;
        maxVitality += penalty;
        if (maxVitality > MAX_VITALITY_LOSS)
        {
            maxVitality = MAX_VITALITY_LOSS;
        }
        setObjVar(petControlDevice, "pet.vitality", vitality);
        setObjVar(petControlDevice, "pet.maxVitality", maxVitality);
        if (isIdValid(pet))
        {
            savePetInfo(pet, petControlDevice);
            int growthStage = 10;
            if (hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
            {
                growthStage = getIntObjVar(petControlDevice, "ai.petAdvance.growthStage");
            }
            if (hasObjVar(petControlDevice, "pet.crafted"))
            {
                setCraftedPetStatsByGrowth(petControlDevice, pet, growthStage);
            }
            else 
            {
                setPetStatsByGrowth(pet, growthStage);
            }
            initializeTrainedPetAbilities(petControlDevice, pet);
            reduceStatsForVitality(pet, petControlDevice);
            restoreDamage(pet, petControlDevice);
        }
        return true;
    }
    public static void savePetInfo(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(petControlDevice))
        {
            return;
        }
        if (!pet.isLoaded() || !petControlDevice.isLoaded())
        {
            return;
        }
        obj_id master = getMaster(pet);
        if (isIdValid(master) && master.isLoaded())
        {
            setHomeLocation(pet, getLocation(master));
        }
        setName(petControlDevice, getAssignedName(pet));
        setName(petControlDevice, getNameStringId(pet));
        storeDamage(pet, petControlDevice);
        setObjVar(petControlDevice, "pet.timeStored", getGameTime());
        if (!isNewPlayerHelperDroid(pet))
        {
            ranged_int_custom_var[] ri = hue.getPalcolorVars(pet);
            if (ri != null && ri.length > 0)
            {
                boolean wasUpdated = false;
                for (int i = 0; i < ri.length; i++)
                {
                    int val = ri[i].getValue();
                    if (val > -1)
                    {
                        String varpath = VAR_PALVAR_VARS + "." + ri[i].getVarName();
                        int cur = getIntObjVar(petControlDevice, varpath);
                        if (cur != val)
                        {
                            setObjVar(petControlDevice, varpath, val);
                        }
                    }
                }
                if (utils.hasScriptVar(pet, "customizationUpdated"))
                {
                    setObjVar(petControlDevice, VAR_PALVAR_CNT, CUSTOMIZATION_COUNT);
                }
            }
            int varValue = getRangedIntCustomVarValue(pet, "/private/index_texture_1");
            setObjVar(petControlDevice, VAR_PALVAR_VARS + "./private/index_texture_1", varValue);
        }
    }
    public static void processSpeech(obj_id pet, obj_id master, String text) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            return;
        }
        if (!isIdValid(master))
        {
            return;
        }
        if (ai_lib.isAiDead(pet))
        {
            return;
        }
        if (text.equals(""))
        {
            return;
        }
        if (master != getMaster(pet))
        {
            if (!pet_lib.isFriend(pet, master))
            {
                return;
            }
        }
        if (utils.hasScriptVar(pet, "stored"))
        {
            destroyObject(pet);
            return;
        }
        if (callable.hasCallableCD(pet))
        {
            obj_id controller = callable.getCallableCD(pet);
            if (isIdValid(controller) && controller.isLoaded())
            {
                obj_id currentPet = callable.getCDCallable(controller);
                if (isIdValid(currentPet) && currentPet != pet)
                {
                    destroyObject(pet);
                    return;
                }
            }
        }
        location masterLoc = getLocation(master);
        location petLoc = getLocation(pet);
        if (masterLoc == null || petLoc == null)
        {
            return;
        }
        if (((masterLoc.cell != null) && isIdValid(masterLoc.cell)) && ((petLoc.cell == null) || (!isIdValid(petLoc.cell))))
        {
            return;
        }
        if (text == null || text.equals(""))
        {
            return;
        }
        if (text.length() > 30)
        {
            return;
        }
        if (utils.hasScriptVar(pet, "ai.pet.learnCommand"))
        {
            int commandNum = utils.getIntScriptVar(pet, "ai.pet.learnCommand");
            pet_lib.learnCommand(pet, commandNum, text, master);
        }
        else if (isSameFaction(pet, master))
        {
            pet_lib.doCommand(pet, text, master);
        }
        else 
        {
            releasePet(pet);
        }
        return;
    }
    public static int getAdultLevel(obj_id pet) throws InterruptedException
    {
        String creatureName = getCreatureName(pet);
        if (creatureName != null)
        {
            int level = create.calcCreatureLevel(creatureName);
            return level;
        }
        return 1;
    }
    public static int getAdultLevel(String creatureName) throws InterruptedException
    {
        if (creatureName != null)
        {
            int level = create.calcCreatureLevel(creatureName);
            return level;
        }
        return 1;
    }
    public static boolean canTrainAsMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ isIdValid failed for(pet)");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ isIdValid failed for(player)");
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!isIdValid(petControlDevice))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ isIdValid failed for(petControlDevice)");
            return false;
        }
        if (hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            return false;
        }
        if (!(couldPetBeMadeMountable(pet) == MSC_CREATURE_MOUNTABLE))
        {
            return false;
        }
        if (!hasSkill(player, "outdoors_creaturehandler_support_04"))
        {
            return false;
        }
        return true;
    }
    public static boolean trainMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        if (!canTrainAsMount(pet, player))
        {
            return false;
        }
        if (!makePetAMount(pet, player))
        {
            return false;
        }
        return true;
    }
    public static boolean makePetAMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        if (!(couldPetBeMadeMountable(pet) == 0))
        {
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!isIdValid(petControlDevice))
        {
            return false;
        }
        if (hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            return false;
        }
        if (!makePetMountable(pet))
        {
            return false;
        }
        else 
        {
            setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        }
        return true;
    }
    public static boolean mountablePetInit(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(player))
        {
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!isIdValid(petControlDevice))
        {
            return false;
        }
        if (!hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            return false;
        }
        int mountableFlag = couldPetBeMadeMountable(pet);
        if (mountableFlag != 0)
        {
            if (mountableFlag == 3)
            {
                String appearance = getAppearance(pet);
                int row = dataTableSearchColumnForString(appearance, "appearance_name", MOUNT_VALID_SCALE_RANGE);
                dictionary dict = dataTableGetRow(MOUNT_VALID_SCALE_RANGE, row);
                float validMinRange = dict.getFloat("valid_scale_min");
                float validMaxRange = dict.getFloat("valid_scale_max");
                float currentScale = getScale(pet);
                if (currentScale > validMaxRange)
                {
                    setScale(pet, validMaxRange);
                }
                else if (currentScale < validMinRange)
                {
                    setScale(pet, validMinRange);
                }
                mountableFlag = couldPetBeMadeMountable(pet);
                if (mountableFlag != 0)
                {
                    if (isGod(player))
                    {
                        prose_package pp = new prose_package();
                        prose.setTO(pp, MOUNTABILITY_STATUS_CODES[mountableFlag]);
                        prose.setStringId(pp, SID_MOUNT_STATUS_CODE_ERROR);
                        sendSystemMessageProse(player, pp);
                    }
                    return false;
                }
                else 
                {
                    if (!makePetMountable(pet))
                    {
                        return false;
                    }
                    return true;
                }
            }
            if (isGod(player))
            {
                prose_package pp = new prose_package();
                prose.setTO(pp, MOUNTABILITY_STATUS_CODES[mountableFlag]);
                prose.setStringId(pp, SID_MOUNT_STATUS_CODE_ERROR);
                sendSystemMessageProse(player, pp);
            }
            return false;
        }
        if (!makePetMountable(pet))
        {
            return false;
        }
        return true;
    }
    public static boolean isMount(obj_id pet) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet))
        {
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!isIdValid(petControlDevice) || !hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            return false;
        }
        return true;
    }
    public static boolean canMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB.canMount +++ isIdValid failed for(pet)");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB.canMount +++ isIdValid failed for(player)");
            return false;
        }
        obj_id playerCurrentMount = getMountId(player);
        if (isIdValid(playerCurrentMount))
        {
            return false;
        }
        if (isDead(pet) || ai_lib.isIncapacitated(pet))
        {
            return false;
        }
        int mountDistance = (int)(getDistance(pet, player));
        int maxDistance = (getOwner(pet) == player) ? MAX_PET_MOUNT_DISTANCE : MAX_PET_MOUNT_OFFER_DISTANCE;
        if (mountDistance > maxDistance)
        {
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!isIdValid(petControlDevice))
        {
            return false;
        }
        if (!hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            return false;
        }
        if (utils.hasScriptVar(player, "mountBlock"))
        {
            int blockTime = utils.getIntScriptVar(player, "mountBlock");
            if (getPlayerPlayedTime(player) < blockTime)
            {
                return false;
            }
        }
        if (getMovementPercent(player) == 0.0f)
        {
            return false;
        }
        return true;
    }
    public static boolean isMountedOnCreatureQueried(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet) || !isIdValid(player) || !exists(player))
        {
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!isIdValid(petControlDevice) || !hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            return false;
        }
        obj_id playerCurrentMount = getMountId(player);
        if (!isIdValid(playerCurrentMount) || playerCurrentMount != pet)
        {
            return false;
        }
        return true;
    }
    public static boolean isSameFaction(obj_id pet, obj_id master) throws InterruptedException
    {
        String creatureName = getCreatureName(pet);
        if (creatureName == null || creatureName.equals(""))
        {
            return false;
        }
        String petFaction = utils.dataTableGetString(CREATURE_TABLE, creatureName, "pvpFaction");
        if (petFaction == null || petFaction.equals(""))
        {
            return true;
        }
        if (!petFaction.equals("Imperial") && !petFaction.equals("Rebel"))
        {
            return true;
        }
        String masterFaction = factions.getFaction(master);
        if (masterFaction == null || !masterFaction.equals(petFaction))
        {
            return false;
        }
        if (pvpGetType(master) == PVPTYPE_NEUTRAL)
        {
            return false;
        }
        return true;
    }
    public static boolean cancelAttackDueToFactionalRestrictions(obj_id pet, obj_id target) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (!isIdValid(master) || !exists(master) || !master.isLoaded())
        {
            return true;
        }
        String creatureName = getCreatureName(pet);
        if (creatureName == null || creatureName.equals(""))
        {
            return true;
        }
        if (!pvpCanAttack(pet, target))
        {
            return true;
        }
        String petFaction = utils.dataTableGetString(CREATURE_TABLE, creatureName, "pvpFaction");
        if (petFaction == null || petFaction.equals(""))
        {
            return false;
        }
        if (!petFaction.equals("Imperial") && !petFaction.equals("Rebel"))
        {
            return false;
        }
        int intAlignedFaction = pvpGetAlignedFaction(target);
        if (intAlignedFaction == 0)
        {
            return true;
        }
        if (!pvpCanAttack(master, target))
        {
            return true;
        }
        return false;
    }
    public static int showGrowthChoiceSui(obj_id owner, obj_id target, String targetName) throws InterruptedException
    {
        if (!isIdValid(owner) || !isIdValid(target) || (targetName == null) || (targetName.equals("")))
        {
            return -1;
        }
        obj_id petControlDevice = null;
        if (utils.hasScriptVar(target, "mountgrowthcheck.pcd"))
        {
            petControlDevice = utils.getObjIdScriptVar(target, "mountgrowthcheck.pcd");
        }
        LOG("pet_lib.showGrowthChoiceSui", "calling mount-related show growth choice sui...");
        String title = utils.packStringId(SID_GROWTH_CHOICE_TITLE);
        String prompt = utils.packStringId(SID_GROWTH_CHOICE_PROMPT);
        int pid = sui.msgbox(petControlDevice, target, prompt, sui.YES_NO, title, "handleGrowthChoice");
        return pid;
    }
    public static int doMountOutOfScaleCheck(obj_id petControlDevice, String creatureName) throws InterruptedException
    {
        if (creatureName == null)
        {
            LOG("create", "create.createCreature called with NULL creatureName!");
            return 13;
        }
        dictionary creatureDict = utils.dataTableGetRow(CREATURE_TABLE, creatureName);
        if (creatureDict == null)
        {
            debugServerConsoleMsg(null, "WARNING: Unable to spawn invalid creatureType: " + creatureName);
            return 13;
        }
        String templateName = creatureDict.getString("template");
        if (templateName.equals("") || templateName == null)
        {
            debugServerConsoleMsg(null, "WARNING: Unable to spawn invalid creatureType: " + creatureName + " - bad templateName?");
            return 13;
        }
        if (!templateName.endsWith(".iff"))
        {
            if (dataTableHasColumn(DRESSED_NPC_TABLE, templateName))
            {
                String[] templateList = dataTableGetStringColumnNoDefaults(DRESSED_NPC_TABLE, templateName);
                if (templateList.length < 1 || templateList == null)
                {
                    LOG("create", templateName + " column in dressed_species is empty!");
                    return 13;
                }
                templateName = templateList[rand(0, templateList.length - 1)];
            }
            else 
            {
                String[] templateList = dataTableGetStringColumnNoDefaults(NPC_CUSTOMIZATION_PREFIX + templateName + ".iff", rand(0, 1));
                if (templateList == null)
                {
                    debugServerConsoleMsg(null, "WARNING: Unable to spawn " + creatureName + " - bad templateName? " + templateName);
                    LOG("create", templateName + " does not exist!");
                    return 13;
                }
                if (templateList.length == 0)
                {
                    debugServerConsoleMsg(null, "WARNING: Unable to spawn " + creatureName + " - bad templateName? " + templateName);
                    LOG("create", templateName + " File was Empty!");
                    return 13;
                }
                templateName = templateList[rand(0, templateList.length - 1)] + ".iff";
            }
        }
        int growthStage = 10;
        if (hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
        {
            growthStage = getIntObjVar(petControlDevice, "ai.petAdvance.growthStage");
        }
        float baseScale = getDefaultScaleFromObjectTemplate("object/mobile/" + templateName);
        float minScale = creatureDict.getFloat("minScale");
        float maxScale = creatureDict.getFloat("maxScale");
        float scaleAdjust = (minScale + maxScale) / 2.0f;
        float realScale = baseScale * scaleAdjust;
        float babyScale = realScale * .3f;
        float scaleIncrement = (realScale - babyScale) / 10.0f;
        float finalScale = babyScale + (scaleIncrement * growthStage);
        if (finalScale > maxScale)
        {
            finalScale = maxScale;
        }
        if (couldObjectTemplateBeMadeMountable("object/mobile/" + templateName, finalScale) != MSC_SPECIES_UNMOUNTABLE)
        {
            if (couldObjectTemplateBeMadeMountable("object/mobile/" + templateName, finalScale) == MSC_CREATURE_MOUNTABLE)
            {
                setObjVar(petControlDevice, "ai.petAdvance.rdyToMnt", 1);
                return 1;
            }
            else if ((couldObjectTemplateBeMadeMountable("object/mobile/" + templateName, finalScale) == MSC_SPECIES_MOUNTABLE_SCALE_OUT_OF_RANGE))
            {
                while ((couldObjectTemplateBeMadeMountable("object/mobile/" + templateName, finalScale) == MSC_SPECIES_MOUNTABLE_SCALE_OUT_OF_RANGE) && (growthStage > 0))
                {
                    growthStage = growthStage - 1;
                    finalScale = babyScale + (scaleIncrement * growthStage);
                }
                if ((couldObjectTemplateBeMadeMountable("object/mobile/" + templateName, finalScale) == MSC_CREATURE_MOUNTABLE))
                {
                    setObjVar(petControlDevice, "ai.petAdvance.lrgMnt", growthStage);
                    return 2;
                }
                else 
                {
                    debugServerConsoleMsg(null, "Pet_Lib script: doMountOutOfScaleCheck: **  Was unable to find a smaller version that is ready to be a mount as of yet.");
                    debugServerConsoleMsg(null, "Pet_Lib script: doMountOutOfScaleCheck: **  Since this pet IS mountable, it simply hasn't grown up enough yet.");
                    debugServerConsoleMsg(null, "Pet_Lib script: doMountOutOfScaleCheck: **  We'll have to run this check again next time it's called.");
                    return 3;
                }
            }
            else 
            {
                debugServerConsoleMsg(null, "Pet_Lib script: doMountOutOfScaleCheck: **  I should NEVER see this message. It means that we've snuck through a hole in the possible results. ");
            }
            debugServerConsoleMsg(null, "Pet_Lib script: doMountOutOfScaleCheck: **  the template returned as MOUNTABLE-ScaleOutOfRange at the current finalScale, and has the ai.petAdvance.rdyToMnt objvar");
        }
        else if (couldObjectTemplateBeMadeMountable("object/mobile/" + templateName, finalScale) == MSC_SPECIES_UNMOUNTABLE)
        {
            debugServerConsoleMsg(null, "Pet_Lib script: doMountOutOfScaleCheck: **  the template came in as UNMOUNTABLE, and not just OUT OF SCALE. It was all for naught! This is the Fall-Through case.");
            return 5;
        }
        return 13;
    }
    public static dictionary getMountMovementInfo(obj_id objMount) throws InterruptedException
    {
        String strSkeleton = ai_lib.getSkeleton(objMount);
        if (strSkeleton != null)
        {
            dictionary dctMountInfo = dataTableGetRow("datatables/mount/mount_movement_speeds.iff", strSkeleton);
            if (dctMountInfo != null)
            {
                return dctMountInfo;
            }
        }
        return null;
    }
    public static void setMountedMovementRate(obj_id objPlayer, obj_id objTarget) throws InterruptedException
    {
        dictionary dctMountInfo = getMountMovementInfo(objTarget);
        if (dctMountInfo != null)
        {
            float fltBaseRunSpeed = dctMountInfo.getFloat("fltBaseRunSpeed");
            setBaseRunSpeed(objTarget, fltBaseRunSpeed);
        }
    }
    public static void setUnmountedMovementRate(obj_id self, obj_id objTarget) throws InterruptedException
    {
        dictionary dctMountInfo = getMountMovementInfo(objTarget);
        if (dctMountInfo != null)
        {
            float fltBurstRunSpeed = dctMountInfo.getFloat("fltBurstRunSpeed");
            setBaseRunSpeed(objTarget, fltBurstRunSpeed);
        }
    }
    public static void setMountBurstRunMovementRate(obj_id objPlayer, obj_id objTarget) throws InterruptedException
    {
        dictionary dctMountInfo = getMountMovementInfo(objTarget);
        obj_id objControlDevice = callable.getCallableCD(objTarget);
        if (dctMountInfo != null)
        {
            float fltBurstRunSpeed = dctMountInfo.getFloat("fltBurstRunSpeed");
            float fltBaseRunSpeed = dctMountInfo.getFloat("fltBaseRunSpeed");
            float fltBurstRunDuration = dctMountInfo.getFloat("fltBurstRunDuration");
            float fltBurstRunRecycleTime = dctMountInfo.getFloat("fltBurstRunRecycleTime");
            LOG("pet", "setting run speed to " + fltBurstRunSpeed);
            LOG("pet", "fltBurstRunDuration is " + fltBurstRunDuration);
            LOG("pet", "fltBurstRunRecycleTiem is " + fltBurstRunRecycleTime);
            setBaseRunSpeed(objTarget, fltBurstRunSpeed);
            utils.setScriptVar(objControlDevice, "mount.intGalloping", 1);
            utils.setScriptVar(objControlDevice, "mount.intTired", 1);
            messageTo(objControlDevice, "removeGallop", null, fltBurstRunDuration, false);
            messageTo(objControlDevice, "removeGallopTired", null, fltBurstRunRecycleTime, false);
            string_id strSpam = new string_id("combat_effects", "gallop_start");
            sendSystemMessage(objPlayer, strSpam);
        }
    }
    public static void setMountCombatMoveSpeed(obj_id objRider, obj_id objMount) throws InterruptedException
    {
        LOG("pet", "setting run speed of " + objMount + " to 6.0f");
        setBaseRunSpeed(objMount, 6.0f);
        string_id strSpam = new string_id("combat_effects", "mount_slow_for_combat");
        sendSystemMessage(objRider, strSpam);
        return;
    }
    public static boolean isGalloping(obj_id objCreature) throws InterruptedException
    {
        if (!isIdValid(objCreature) || !exists(objCreature))
        {
            return false;
        }
        if (!callable.hasCallableCD(objCreature))
        {
            return false;
        }
        obj_id objControlDevice = callable.getCallableCD(objCreature);
        if (!isIdValid(objControlDevice))
        {
            return false;
        }
        if (utils.hasScriptVar(objControlDevice, "mount.intGalloping"))
        {
            return true;
        }
        return false;
    }
    public static boolean isTameable(obj_id objCreature) throws InterruptedException
    {
        final String strCreatureName = getCreatureName(objCreature);
        if (strCreatureName != null)
        {
            dictionary dctCreatureInfo = dataTableGetRow("datatables/mob/creatures.iff", strCreatureName);
            if (dctCreatureInfo != null)
            {
                float fltCanTame = dctCreatureInfo.getFloat("canTame");
                if (fltCanTame > 0.0f)
                {
                    return true;
                }
                return false;
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    public static void doPetSpecialAttack(obj_id pet, obj_id master, int attackNum) throws InterruptedException
    {
        debugServerConsoleMsg(null, "PET_LIB:doPetSpecialAttack *** entered doPetSpecialAttack ");
        switch (attackNum)
        {
            case 2:
            obj_id currentWeapon = getCurrentWeapon(pet);
            obj_id defaultWeapon = getDefaultWeapon(pet);
            int wpnType = getWeaponType(defaultWeapon);
            int crrntWpnType = getWeaponType(currentWeapon);
            String defaultWeaponTemplateName = getTemplateName(defaultWeapon);
            String currentWeaponTemplateName = getTemplateName(currentWeapon);
            debugServerConsoleMsg(null, "PET_LIB:doPetSpecialAttack *** defaultWeapon type comes in as :" + wpnType + " and being of template: " + defaultWeaponTemplateName);
            debugServerConsoleMsg(null, "PET_LIB:doPetSpecialAttack *** currentWeapon type comes in as :" + crrntWpnType + " and being of template: " + currentWeaponTemplateName);
            break;
            case 1:
            case 0:
            if (!ai_lib.isInCombat(pet))
            {
                doAttackCommand(pet, master);
            }
            if (ai_lib.isInCombat(pet))
            {
                final obj_id hateTarget = getHateTarget(pet);
            }
            break;
        }
    }
    public static boolean hasRangedAttack(obj_id pet) throws InterruptedException
    {
        if (isCombatDroid(pet))
        {
            final String droidType = getCreatureName(pet);
            int droidRangedCapable = utils.dataTableGetInt(DROID_COMBAT_TABLE, droidType, "hasRangedAttack");
            if (droidRangedCapable != 1)
            {
                return false;
            }
        }
        if (!ai_lib.isMonster(pet) && !isCombatDroid(pet))
        {
            return false;
        }
        String creatureName = getCreatureName(pet);
        if (creatureName != null)
        {
            String weaponOne = dataTableGetString(CREATURE_TABLE, creatureName, "primary_weapon");
            String weaponTwo = dataTableGetString(CREATURE_TABLE, creatureName, "secondary_weapon");
            boolean rangedWeapon = false;
            if (weaponOne != null && !weaponOne.equals("") && weaponOne.indexOf("_spit_") != -1)
            {
                rangedWeapon = true;
            }
            else if (weaponTwo != null && !weaponTwo.equals("") && weaponTwo.indexOf("_spit_") != -1)
            {
                rangedWeapon = true;
            }
            else 
            {
                obj_id pcd = callable.getCallableCD(pet);
                if (isIdValid(pcd) && hasObjVar(pcd, "pet.crafted"))
                {
                    String rangedWeaponName = getStringObjVar(pcd, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON);
                    if (rangedWeaponName == null || rangedWeaponName.equals(""))
                    {
                        rangedWeapon = false;
                    }
                    else 
                    {
                        rangedWeapon = true;
                    }
                }
                else 
                {
                    rangedWeapon = false;
                }
            }
            return rangedWeapon;
        }
        return true;
    }
    public static void packAllCurrentPets(obj_id player, string_id optionalMsg) throws InterruptedException
    {
        callable.storeCallables(player);
        if (optionalMsg != null)
        {
            sendSystemMessage(player, optionalMsg);
        }
    }
    public static boolean doDismountNow(obj_id riderId, boolean notify) throws InterruptedException
    {
        if (!isIdValid(riderId))
        {
            return false;
        }
        if (!getMountsEnabled())
        {
            return false;
        }
        boolean dismountOccurred = false;
        obj_id playerCurrentMount = getMountId(riderId);
        if (isIdValid(playerCurrentMount) && exists(playerCurrentMount))
        {
            queueClear(riderId);
            dismountCreature(riderId);
            dismountOccurred = true;
            if (hasScript(playerCurrentMount, "ai.mount_combat"))
            {
                detachScript(playerCurrentMount, "ai.mount_combat");
            }
            int vehicleBuff = buff.getBuffOnTargetFromGroup(riderId, "vehicle");
            if (vehicleBuff != 0)
            {
                buff.removeBuff(riderId, vehicleBuff);
            }
            int vehicleHasBuff = buff.getBuffOnTargetFromGroup(playerCurrentMount, "vehicle");
            if (vehicleHasBuff != 0)
            {
                buff.removeBuff(playerCurrentMount, vehicleHasBuff);
            }
            int petType = getPetType(playerCurrentMount);
            if (isPetType(playerCurrentMount, PET_TYPE_MOUNT))
            {
                storePet(playerCurrentMount);
            }
        }
        else 
        {
            if (notify)
            {
                sendSystemMessage(riderId, pet_lib.SID_SYS_CANT_DISMOUNT);
            }
        }
        return dismountOccurred;
    }
    public static boolean doDismountNow(obj_id player) throws InterruptedException
    {
        return doDismountNow(player, true);
    }
    public static void initPCDCraftedStats(obj_id device, obj_id deed) throws InterruptedException
    {
        setObjVar(device, "creature_attribs.level", getIntObjVar(deed, "creature_attribs.level"));
        setObjVar(device, "creature_attribs.maxHealth", getIntObjVar(deed, "creature_attribs.maxHealth"));
        setObjVar(device, "creature_attribs.maxConstitution", getIntObjVar(deed, "creature_attribs.maxConstitution"));
        setObjVar(device, "creature_attribs.general_protection", getIntObjVar(deed, "creature_attribs.general_protection"));
        setObjVar(device, "creature_attribs.toHitChance", getIntObjVar(deed, "creature_attribs.toHitChance"));
        setObjVar(device, "creature_attribs.defenseValue", getIntObjVar(deed, "creature_attribs.defenseValue"));
        setObjVar(device, "creature_attribs.minDamage", getIntObjVar(deed, "creature_attribs.minDamage"));
        setObjVar(device, "creature_attribs.maxDamage", getIntObjVar(deed, "creature_attribs.maxDamage"));
        setObjVar(device, "creature_attribs.aggroBonus", getFloatObjVar(deed, "creature_attribs.aggroBonus"));
        setObjVar(device, "creature_attribs.critChance", getFloatObjVar(deed, "creature_attribs.critChance"));
        setObjVar(device, "creature_attribs.critSave", getFloatObjVar(deed, "creature_attribs.critSave"));
        setObjVar(device, "creature_attribs.scale", getFloatObjVar(deed, "creature_attribs.scale"));
        setObjVar(device, "creature_attribs.stateResist", getFloatObjVar(deed, "creature_attribs.stateResist"));
        setObjVar(device, "creature_attribs." + bio_engineer.ATTRIB_DICT_SPECIAL_ATTACK_1, getIntObjVar(deed, "creature_attribs." + bio_engineer.ATTRIB_DICT_SPECIAL_ATTACK_1));
        setObjVar(device, "creature_attribs." + bio_engineer.ATTRIB_DICT_SPECIAL_ATTACK_2, getIntObjVar(deed, "creature_attribs." + bio_engineer.ATTRIB_DICT_SPECIAL_ATTACK_2));
        setObjVar(device, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON, getStringObjVar(deed, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON));
        setObjVar(device, "postCuPCD", 2);
    }
    public static void createPetFromData(obj_id petControlDevice) throws InterruptedException
    {
        createPetFromData(petControlDevice, null);
    }
    public static void createPetFromData(obj_id petControlDevice, obj_id objContainer) throws InterruptedException
    {
        String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
        if (creatureName == null || creatureName.equals(""))
        {
            return;
        }
        obj_id datapad = getContainedBy(petControlDevice);
        if (!isIdValid(datapad))
        {
            return;
        }
        obj_id master = getContainedBy(datapad);
        if (!isIdValid(master))
        {
            return;
        }
        if (!hasScript(master, "ai.pet_master"))
        {
            attachScript(master, "ai.pet_master");
        }
        if (getMountsEnabled() && (!hasObjVar(petControlDevice, "ai.petAdvance.cannotBeMount")))
        {
            if (hasObjVar(petControlDevice, "ai.petAdvance.freezegrowth"))
            {
                int freezeGrowthValue = getIntObjVar(petControlDevice, "ai.petAdvance.freezegrowth");
                if (freezeGrowthValue == 10)
                {
                    debugServerConsoleMsg(null, "PCD script: createPetFromData: **  skipping growthstage objvar reset/rewrite, as this creature is an adult, with freezegrowth value of 10");
                }
                else 
                {
                    setObjVar(petControlDevice, "ai.petAdvance.growthStage", freezeGrowthValue);
                }
            }
            if ((!hasObjVar(petControlDevice, "ai.petAdvance.letPetGrow")) && (!hasObjVar(petControlDevice, "ai.petAdvance.freezegrowth")))
            {
                int mountOutOfScaleReturnValue = pet_lib.doMountOutOfScaleCheck(petControlDevice, creatureName);
                if (mountOutOfScaleReturnValue == 5)
                {
                    setObjVar(petControlDevice, "ai.petAdvance.cannotBeMount", 1);
                }
                else if (mountOutOfScaleReturnValue == 2)
                {
                    utils.setScriptVar(master, "mountgrowthcheck.pcd", petControlDevice);
                    pet_lib.showGrowthChoiceSui(petControlDevice, master, creatureName);
                    return;
                }
                else if (mountOutOfScaleReturnValue == 1)
                {
                    if (!hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
                    {
                        setObjVar(petControlDevice, "ai.petAdvance.freezegrowth", 10);
                    }
                }
                else if (mountOutOfScaleReturnValue == 3)
                {
                    debugServerConsoleMsg(null, "PCD script: createPetFromData: **  pet_lib.doMountOutOfScaleCheck returned 3... the pet hasn't grown up enough yet to be a mount.");
                }
                else 
                {
                    debugServerConsoleMsg(null, "PCD script: createPetFromData: **  pet_lib.doMountOutOfScaleCheck FALLTHROUGH... Perhaps there was a data error. It didn't return 1, 2, 3, or 5.");
                }
            }
        }
        obj_id pet = null;
        if (getPosture(master) != POSTURE_UPRIGHT)
        {
            sendSystemMessage(master, SID_SYS_CANT_CALL);
            return;
        }
        if (!isIdValid(objContainer))
        {
            location loc = getLocation(master);
            pet = create.object(creatureName, loc, true, true);
        }
        else 
        {
            pet = create.object(creatureName, objContainer, false, true);
        }
        if (!isIdValid(pet))
        {
            sendSystemMessage(master, pet_lib.SID_PRIVATE_HOUSE);
            return;
        }
        setObjVar(pet, "ai.pet", true);
        callable.setCallableCD(pet, petControlDevice);
        if (!hasScript(pet, "ai.pet"))
        {
            
            {
                attachScript(pet, "ai.pet");
            }
        }
        sendDirtyObjectMenuNotification(petControlDevice);
        String masterFaction = factions.getFaction(master);
        if (masterFaction != null && !masterFaction.equals(""))
        {
            if (masterFaction.equals("Rebel") || masterFaction.equals("Imperial"))
            {
                setObjVar(pet, factions.FACTION, masterFaction);
            }
        }
        pet_lib.makePet(pet, master);
        if (hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
        {
            int growthStage = getIntObjVar(petControlDevice, "ai.petAdvance.growthStage");
            if (hasObjVar(petControlDevice, "pet.crafted"))
            {
                pet_lib.setCraftedPetStatsByGrowth(petControlDevice, pet, growthStage);
            }
            else 
            {
                pet_lib.setPetStatsByGrowth(pet, growthStage);
            }
            int level = getLevel(pet);
            utils.setScriptVar(pet, "ai.level", level);
        }
        else 
        {
            if (hasObjVar(petControlDevice, "pet.crafted"))
            {
                setCraftedPetStatsByGrowth(petControlDevice, pet, 10);
                if (ai_lib.aiGetNiche(pet) == NICHE_DROID || ai_lib.aiGetNiche(pet) == NICHE_ANDROID)
                {
                    initDroidCraftedInventoryDroid(pet, petControlDevice);
                    initDroidCraftedDatapadDroid(pet, petControlDevice);
                    initDroidPersonality(pet, petControlDevice);
                    initDroidMedicalPower(pet, petControlDevice);
                    initDroidCraftingPower(pet, petControlDevice, master);
                    initDroidRepairPower(pet, petControlDevice);
                    reviseCombatDroidPCD(petControlDevice, master, pet);
                }
            }
            else 
            {
                setPetStatsByGrowth(pet, 10);
            }
        }
        initializeTrainedPetAbilities(petControlDevice, pet);
        pet_lib.reduceStatsForVitality(pet, petControlDevice);
        pet_lib.restoreDamage(pet, petControlDevice);
        if (hasObjVar(petControlDevice, "ai.pet.command"))
        {
            initializePetCommandList(pet, petControlDevice);
        }
        if (hasObjVar(petControlDevice, "alreadyTrained"))
        {
            setObjVar(pet, "alreadyTrained", true);
        }
        if (hasObjVar(petControlDevice, "ai.pet.trainedMount"))
        {
            pet_lib.mountablePetInit(pet, master);
        }
        if (!ai_lib.isDroid(pet) && !ai_lib.isAndroid(pet))
        {
            restoreCustomization(pet, petControlDevice);
        }
        else if (hasObjVar(petControlDevice, pet_lib.VAR_PALVAR_BASE))
        {
            restoreCustomization(pet, petControlDevice);
            if (hasObjVar(petControlDevice, pet_lib.VAR_PALVAR_CNT))
            {
                int cnt = getIntObjVar(petControlDevice, pet_lib.VAR_PALVAR_CNT);
                if (cnt > -1)
                {
                    cnt--;
                    setObjVar(petControlDevice, pet_lib.VAR_PALVAR_CNT, cnt);
                    if (cnt < 6)
                    {
                        switch (cnt)
                        {
                            case 5:
                            customizationFadeToWhite(pet, 0.15f);
                            sendSystemMessage(master, SID_SYS_CUST_FADING);
                            break;
                            case 4:
                            customizationFadeToWhite(pet, 0.20f);
                            sendSystemMessage(master, SID_SYS_CUST_FADING);
                            break;
                            case 3:
                            customizationFadeToWhite(pet, 0.25f);
                            sendSystemMessage(master, SID_SYS_CUST_FADING);
                            break;
                            case 2:
                            customizationFadeToWhite(pet, 0.30f);
                            sendSystemMessage(master, SID_SYS_CUST_FADING);
                            break;
                            case 1:
                            customizationFadeToWhite(pet, 0.35f);
                            sendSystemMessage(master, SID_SYS_CUST_FADING);
                            break;
                            case 0:
                            customizationFadeToWhite(pet, 0.40f);
                            sendSystemMessage(master, SID_SYS_CUST_GONE);
                            removeObjVar(petControlDevice, pet_lib.VAR_PALVAR_CNT);
                            break;
                        }
                    }
                }
            }
        }
        if (ai_lib.isDroid(pet) || ai_lib.isAndroid(pet))
        {
            copyObjVar(petControlDevice, pet, "module_data");
            if (hasObjVar(petControlDevice, "module_data.merchant_barker"))
            {
                attachScript(pet, "systems.crafting.droid.modules.merchant_barker");
            }
            if (hasObjVar(petControlDevice, "module_data.bomb_level"))
            {
                attachScript(pet, "systems.crafting.droid.modules.droid_bomb");
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
            if (hasObjVar(petControlDevice, "module_data.entertainer_effects"))
            {
                attachScript(pet, "systems.crafting.droid.modules.entertainer_effect");
            }
            if (hasObjVar(petControlDevice, "module_data.struct_maint"))
            {
                attachScript(pet, "systems.crafting.droid.modules.structure_maintenance");
            }
            if (hasObjVar(petControlDevice, "module_data.trap_bonus"))
            {
                attachScript(pet, "systems.crafting.droid.modules.trap_thrower");
            }
            if (hasObjVar(petControlDevice, "module_data.dancing_droid"))
            {
                attachScript(pet, "ai.dancing_droid");
            }
            if (hasObjVar(petControlDevice, "module_data.sampling_power"))
            {
                attachScript(pet, "ai.hand_sampling");
            }
        }
        String name = getAssignedName(petControlDevice);
        setName(pet, name);
        callable.setCallableLinks(master, petControlDevice, pet);
    }
    public static void customizationFadeToWhite(obj_id pet, float percent) throws InterruptedException
    {
        ranged_int_custom_var[] ricv = hue.getPalcolorVars(pet);
        Vector pcv = new Vector();
        pcv.setSize(0);
        if (ricv == null || ricv.length == 0)
        {
            return;
        }
        for (int i = 0; i < ricv.length; i++)
        {
            if (ricv[i].isPalColor())
            {
                pcv = utils.addElement(pcv, (palcolor_custom_var)ricv[i]);
            }
        }
        if (pcv == null || pcv.size() == 0)
        {
            return;
        }
        for (int j = 0; j < pcv.size(); j++)
        {
            color old_color = ((palcolor_custom_var)pcv.get(j)).getSelectedColor();
            int old_r = old_color.getR();
            int old_g = old_color.getG();
            int old_b = old_color.getB();
            int old_a = old_color.getA();
            float d_r = (255 - old_r) * percent;
            float d_g = (255 - old_g) * percent;
            float d_b = (255 - old_b) * percent;
            float d_a = (255 - old_a) * percent;
            int new_r = old_r + (int)d_r;
            int new_g = old_g + (int)d_g;
            int new_b = old_b + (int)d_b;
            int new_a = old_a + (int)d_a;
            color new_color = new color(new_r, new_g, new_b, new_a);
            ((palcolor_custom_var)pcv.get(j)).setToClosestColor(new_color);
        }
    }
    public static int modifyPaletteIndex(int idx) throws InterruptedException
    {
        int series_idx = idx % 8;
        if (series_idx == 0)
        {
            return idx;
        }
        return idx - 1;
    }
    public static void initDroidCraftedInventoryDroid(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        obj_id inv = getObjectInSlot(petControlDevice, "inventory");
        if (isIdValid(inv))
        {
            setObjVar(pet, "ai.pet.hasContainer", true);
        }
        else 
        {
            LOG("LOG_CHANNEL", "droid_deed::initDroidCraftedInventoryDroid -- No inventory detected on PCD intangible object.");
        }
    }
    public static void initDroidCraftedDatapadDroid(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        obj_id dpad = getObjectInSlot(petControlDevice, "datapad");
        if (isIdValid(dpad))
        {
            setObjVar(pet, "ai.pet.hasDatapad", true);
        }
    }
    public static void initDroidPersonality(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "ai.diction"))
        {
            String personalityModuleType = getStringObjVar(petControlDevice, "ai.diction");
            if (!personalityModuleType.equals("droid_default"))
            {
                setObjVar(pet, "ai.diction", personalityModuleType);
            }
            else 
            {
                removeObjVar(petControlDevice, "ai.diction");
            }
        }
    }
    public static void initDroidMedicalPower(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "medpower"))
        {
            float medModuleValue = getFloatObjVar(petControlDevice, "medpower");
            setObjVar(pet, "medpower", medModuleValue);
        }
    }
    public static void initDroidCraftingPower(obj_id pet, obj_id petControlDevice, obj_id master) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "craftingStation"))
        {
            setObjVar(pet, "crafting.station", 1);
            setObjVar(pet, "crafting.private", 2);
            if (hasObjVar(petControlDevice, "craftingStationSpace"))
            {
                if (hasObjVar(pet, "crafting.type"))
                {
                    int craftingTypeScratchpad = getIntObjVar(pet, "crafting.type");
                    setObjVar(pet, "crafting.type", craftingTypeScratchpad | CT_space | CT_reverseEngineering);
                }
                else 
                {
                    setObjVar(pet, "crafting.type", CT_space | CT_reverseEngineering);
                }
            }
            if (hasObjVar(petControlDevice, "craftingStationStructure"))
            {
                if (hasObjVar(pet, "crafting.type"))
                {
                    int craftingTypeScratchpad = getIntObjVar(pet, "crafting.type");
                    setObjVar(pet, "crafting.type", craftingTypeScratchpad | CT_installation | CT_furniture);
                }
                else 
                {
                    setObjVar(pet, "crafting.type", CT_installation | CT_furniture);
                }
            }
            if (hasObjVar(petControlDevice, "craftingStationClothing"))
            {
                if (hasObjVar(pet, "crafting.type"))
                {
                    int craftingTypeScratchpad = getIntObjVar(pet, "crafting.type");
                    setObjVar(pet, "crafting.type", craftingTypeScratchpad | CT_clothing | CT_armor);
                }
                else 
                {
                    setObjVar(pet, "crafting.type", CT_clothing | CT_armor);
                }
            }
            if (hasObjVar(petControlDevice, "craftingStationFood"))
            {
                if (hasObjVar(pet, "crafting.type"))
                {
                    int craftingTypeScratchpad = getIntObjVar(pet, "crafting.type");
                    setObjVar(pet, "crafting.type", craftingTypeScratchpad | CT_food | CT_chemical);
                }
                else 
                {
                    setObjVar(pet, "crafting.type", CT_food | CT_chemical);
                }
            }
            if (hasObjVar(petControlDevice, "craftingStationWeapon"))
            {
                if (hasObjVar(pet, "crafting.type"))
                {
                    int craftingTypeScratchpad = getIntObjVar(pet, "crafting.type");
                    setObjVar(pet, "crafting.type", craftingTypeScratchpad | CT_weapon | CT_droid | CT_misc);
                }
                else 
                {
                    setObjVar(pet, "crafting.type", CT_weapon | CT_droid | CT_misc);
                }
            }
        }
    }
    public static void initDroidRepairPower(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "ai.pet.isRepairDroid"))
        {
            setObjVar(pet, "ai.pet.isRepairDroid", true);
        }
    }
    public static void restoreCustomization(obj_id pet, obj_id petControlDevice) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, pet_lib.VAR_PALVAR_BASE))
        {
            obj_var_list ovl = getObjVarList(petControlDevice, pet_lib.VAR_PALVAR_VARS);
            if (ovl != null)
            {
                int numItem = ovl.getNumItems();
                for (int i = 0; i < numItem; i++)
                {
                    obj_var ov = ovl.getObjVar(i);
                    String var = ov.getName();
                    int idx = ov.getIntData();
                    hue.setColor(pet, var, idx);
                }
            }
            int idx = getIntObjVar(petControlDevice, VAR_PALVAR_VARS + "./private/index_texture_1");
            setRangedIntCustomVarValue(pet, "/private/index_texture_1", idx);
        }
    }
    public static void doTargetHarvest(obj_id pet, obj_id master) throws InterruptedException
    {
        dictionary parms = new dictionary();
        parms.put("droid", pet);
        parms.put("player", master);
        parms.put("target", getLookAtTarget(master));
        messageTo(pet, "doTargetHarvest", parms, 0, false);
        return;
    }
    public static void doPetThrowTrap(obj_id pet, obj_id master, int commandNum) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("droid", pet);
        params.put("player", master);
        params.put("trapNum", commandNum);
        messageTo(pet, "doPetThrowTrap", params, 0, false);
        return;
    }
    public static boolean isNewPlayerHelperDroid(obj_id pet) throws InterruptedException
    {
        if (isIdValid(pet))
        {
            String name = getCreatureName(pet);
            if (name != null && name.equals("nhelper_droid"))
            {
                return true;
            }
            if (name.equals("tutorial_droid"))
            {
                return true;
            }
        }
        return false;
    }
    public static String[] getLightingEffects(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return null;
        }
        if (!hasObjVar(droid, "module_data.entertainer_effects"))
        {
            return null;
        }
        int raw_effects = getIntObjVar(droid, "module_data.entertainer_effects");
        if (raw_effects < 1)
        {
            return null;
        }
        int i = 1;
        Vector available_effects = new Vector();
        available_effects.setSize(0);
        while (i <= LIGHTING_EFFECTS.length)
        {
            int result = (int)((raw_effects % Math.pow(10, i)) / Math.pow(10, i - 1));
            if (result >= 1)
            {
                available_effects = utils.addElement(available_effects, LIGHTING_EFFECTS[i - 1]);
            }
            i++;
        }
        if (available_effects.size() < 1)
        {
            return null;
        }
        else 
        {
            String[] _available_effects = new String[0];
            if (available_effects != null)
            {
                _available_effects = new String[available_effects.size()];
                available_effects.toArray(_available_effects);
            }
            return _available_effects;
        }
    }
    public static int calculateMaxTrapLoad(obj_id objItem) throws InterruptedException
    {
        if (hasObjVar(objItem, "module_data.trap_bonus"))
        {
            return (int)BASE_MAX_TRAP_LOAD + (getIntObjVar(objItem, "module_data.trap_bonus") / 4);
        }
        return (int)BASE_MAX_TRAP_LOAD + (getIntObjVar(callable.getCallableCD(objItem), "module_data.trap_bonus") / 4);
    }
    public static void cleanOldDroidDeed(obj_id deed) throws InterruptedException
    {
        if (hasObjVar(deed, "armorEffectiveness"))
        {
            removeObjVar(deed, "armorEffectiveness");
        }
        if (hasObjVar(deed, "armorToughness"))
        {
            removeObjVar(deed, "armorToughness");
        }
        for (int attribNum = 0; attribNum < NUM_ATTRIBUTES; attribNum++)
        {
            if (hasObjVar(deed, "creature_attribs." + create.MAXATTRIBNAMES[attribNum]))
            {
                if (("creature_attribs." + create.MAXATTRIBNAMES[attribNum]) != "creature_attribs.maxHealth")
                {
                    removeObjVar(deed, "creature_attribs." + create.MAXATTRIBNAMES[attribNum]);
                }
            }
        }
        return;
    }
    public static int getDroidCapLevel(obj_id who, int level) throws InterruptedException
    {
        if (craftinglib.isTrader(who))
        {
            return 90;
        }
        return 60;
    }
    public static void initDroidDefaultStats(obj_id deed) throws InterruptedException
    {
        dictionary droidDefaultStatsDict = dataTableGetRow(TBL_MOB_STAT_BALANCE, DEFAULT_DROID_LEVEL - 1);
        int creature_attribs_level = DEFAULT_DROID_LEVEL;
        int creature_attribs_maxHealth = droidDefaultStatsDict.getInt("HP");
        int creature_attribs_maxConstitution = 0;
        int creature_attribs_toHitChance = droidDefaultStatsDict.getInt("ToHit");
        int creature_attribs_defenseValue = droidDefaultStatsDict.getInt("Def");
        float creatureAttribs_dps = droidDefaultStatsDict.getFloat("damagePerSecond");
        int creature_attribs_minDamage = (int)creatureAttribs_dps;
        int creature_attribs_maxDamage = (int)creatureAttribs_dps * 3;
        float creature_attribs_scale = 1.0f;
        String creature_attribs_rangedWeapon = null;
        int creature_attribs_general_protection = getIntObjVar(deed, "creature_attribs.general_protection");
        if (creature_attribs_general_protection == 0)
        {
            creature_attribs_general_protection = droidDefaultStatsDict.getInt("Elite_Armor");
        }
        dictionary droidDeedStats = new dictionary();
        droidDeedStats.put("creature_attribs.level", creature_attribs_level);
        droidDeedStats.put("creature_attribs.maxHealth", creature_attribs_maxHealth);
        droidDeedStats.put("creature_attribs.maxConstitution", creature_attribs_maxConstitution);
        droidDeedStats.put("creature_attribs.general_protection", creature_attribs_general_protection);
        droidDeedStats.put("creature_attribs.toHitChance", creature_attribs_toHitChance);
        droidDeedStats.put("creature_attribs.defenseValue", creature_attribs_defenseValue);
        droidDeedStats.put("creature_attribs.minDamage", creature_attribs_minDamage);
        droidDeedStats.put("creature_attribs.maxDamage", creature_attribs_maxDamage);
        droidDeedStats.put("creature_attribs.rangedWeapon", "");
        writeOutNewDroidDeedAttribs(deed, droidDeedStats);
    }
    public static void initDroidCombatStats(obj_id deed, dictionary droidCombatStats) throws InterruptedException
    {
        int tempCreatureLevel = 0;
        int tempCreatureDamageLevel = 0;
        int tempCreatureDefenseLevel = 0;
        int tempCreatureProtectionLevel = 0;
        int tempArmorModule = droidCombatStats.getInt("armorModuleValue");
        if (tempArmorModule > 0)
        {
            if (tempArmorModule <= 2)
            {
                tempCreatureLevel = 5;
                tempCreatureDefenseLevel = 5;
                tempCreatureProtectionLevel = DEFENSE_MODULE_PROTECT_LVL;
            }
            else if (tempArmorModule <= 4)
            {
                tempCreatureLevel = 10;
                tempCreatureDefenseLevel = 10;
                tempCreatureProtectionLevel = (2 * DEFENSE_MODULE_PROTECT_LVL);
            }
            else if (tempArmorModule <= 6)
            {
                tempCreatureLevel = 15;
                tempCreatureDefenseLevel = 15;
                tempCreatureProtectionLevel = (3 * DEFENSE_MODULE_PROTECT_LVL);
            }
            else if (tempArmorModule <= 8)
            {
                tempCreatureLevel = 20;
                tempCreatureDefenseLevel = 20;
                tempCreatureProtectionLevel = (4 * DEFENSE_MODULE_PROTECT_LVL);
            }
            else if (tempArmorModule <= 10)
            {
                tempCreatureLevel = 25;
                tempCreatureDefenseLevel = 25;
                tempCreatureProtectionLevel = (5 * DEFENSE_MODULE_PROTECT_LVL);
            }
            else 
            {
                tempCreatureLevel = 30;
                tempCreatureDefenseLevel = 30;
                tempCreatureProtectionLevel = (6 * DEFENSE_MODULE_PROTECT_LVL);
            }
        }
        int tempCombatModule = droidCombatStats.getInt("combatModuleValue");
        if (tempCombatModule > 0)
        {
            if (tempCombatModule <= 50)
            {
                tempCreatureLevel += 5;
                tempCreatureDamageLevel += 5;
                tempCreatureProtectionLevel += COMBAT_MODULE_PROTECT_LVL;
            }
            else 
            {
                tempCreatureLevel += tempCombatModule / 10;
                tempCreatureDamageLevel += tempCombatModule / 10;
                tempCreatureProtectionLevel += (COMBAT_MODULE_PROTECT_LVL * (int)(tempCombatModule / 100));
            }
        }
        if (tempCreatureLevel < 1 || tempCreatureDamageLevel < 0)
        {
            tempCreatureLevel = 1;
            tempCreatureDamageLevel = 1;
        }
        else if (tempCreatureLevel > 90 || tempCreatureDamageLevel > 90)
        {
            tempCreatureLevel = 90;
            tempCreatureDamageLevel = 90;
        }
        dictionary droidDefaultStatsDict = dataTableGetRow(TBL_MOB_STAT_BALANCE, tempCreatureLevel - 1);
        int creature_attribs_level = tempCreatureLevel;
        int creature_attribs_maxHealth = droidDefaultStatsDict.getInt("HP");
        int creature_attribs_toHitChance = droidDefaultStatsDict.getInt("ToHit");
        int creature_attribs_defenseValue = droidDefaultStatsDict.getInt("Def");
        float creature_attribs_dps = droidDefaultStatsDict.getFloat("damagePerSecond");
        int creature_attribs_minDamage = Math.round(creature_attribs_dps);
        int creature_attribs_maxDamage = Math.round(creature_attribs_dps * 3.0f);
        int creature_attribs_general_protection = droidCombatStats.getInt("creature_attribs.general_protection");
        if (creature_attribs_general_protection == 0)
        {
            creature_attribs_general_protection = droidDefaultStatsDict.getInt("Elite_Armor");
        }
        String creature_attribs_rangedWeapon = null;
        if (tempCreatureLevel > 1)
        {
            setObjVar(deed, "combatModule", tempCombatModule);
            creature_attribs_maxHealth = dataTableGetInt(TBL_MOB_STAT_BALANCE, tempCreatureLevel - 1, "HP");
            if (tempCreatureDefenseLevel > 1)
            {
                int temp_droid_attribs_defenseValue = dataTableGetInt(TBL_MOB_STAT_BALANCE, tempCreatureDefenseLevel - 1, "Def");
                if (temp_droid_attribs_defenseValue > creature_attribs_defenseValue)
                {
                    creature_attribs_defenseValue = temp_droid_attribs_defenseValue;
                }
            }
            if (tempCreatureProtectionLevel > creature_attribs_general_protection)
            {
                creature_attribs_general_protection = tempCreatureProtectionLevel;
            }
            if (tempCreatureDamageLevel > 1)
            {
                if (hasObjVar(deed, "pet.nonCombatDroid"))
                {
                    removeObjVar(deed, "pet.nonCombatDroid");
                }
                setObjVar(deed, "pet.combatDroid", 1);
                String droidName = getStringObjVar(deed, "creature_attribs.type");
                dictionary droidModDamageStatsDict = dataTableGetRow(TBL_MOB_STAT_BALANCE, tempCreatureDamageLevel - 1);
                int temp_droidMod_MinDamage = Math.round(droidModDamageStatsDict.getFloat("damagePerSecond"));
                if (temp_droidMod_MinDamage > creature_attribs_minDamage)
                {
                    creature_attribs_minDamage = temp_droidMod_MinDamage;
                }
                int temp_droidMod_MaxDamage = Math.round(droidModDamageStatsDict.getFloat("damagePerSecond") * 3.0f);
                if (temp_droidMod_MaxDamage > creature_attribs_maxDamage)
                {
                    creature_attribs_maxDamage = temp_droidMod_MaxDamage;
                }
                int temp_droidMod_ToHit = droidModDamageStatsDict.getInt("ToHit");
                if (temp_droidMod_ToHit > creature_attribs_toHitChance)
                {
                    creature_attribs_toHitChance = temp_droidMod_ToHit;
                }
                int droidRangedCapable = utils.dataTableGetInt(TBL_DROID_COMBAT, droidName, "hasRangedAttack");
                if (droidRangedCapable == 1)
                {
                    creature_attribs_rangedWeapon = DROID_RANGED_WEAPON;
                }
            }
        }
        else 
        {
            setObjVar(deed, "pet.nonCombatDroid", 1);
        }
        dictionary droidDeedStats = new dictionary();
        droidDeedStats.put("creature_attribs.level", creature_attribs_level);
        droidDeedStats.put("creature_attribs.maxHealth", creature_attribs_maxHealth);
        droidDeedStats.put("creature_attribs.general_protection", creature_attribs_general_protection);
        droidDeedStats.put("creature_attribs.toHitChance", creature_attribs_toHitChance);
        droidDeedStats.put("creature_attribs.defenseValue", creature_attribs_defenseValue);
        droidDeedStats.put("creature_attribs.minDamage", creature_attribs_minDamage);
        droidDeedStats.put("creature_attribs.maxDamage", creature_attribs_maxDamage);
        if (creature_attribs_rangedWeapon != null && !creature_attribs_rangedWeapon.equals(""))
        {
            droidDeedStats.put("creature_attribs.rangedWeapon", creature_attribs_rangedWeapon);
        }
        writeOutNewDroidDeedAttribs(deed, droidDeedStats);
        return;
    }
    public static void writeOutNewDroidDeedAttribs(obj_id deed, dictionary droidDeedStats) throws InterruptedException
    {
        int creature_attribs_level = droidDeedStats.getInt("creature_attribs.level");
        int creature_attribs_maxHealth = droidDeedStats.getInt("creature_attribs.maxHealth");
        int creature_attribs_general_protection = droidDeedStats.getInt("creature_attribs.general_protection");
        int creature_attribs_toHitChance = droidDeedStats.getInt("creature_attribs.toHitChance");
        int creature_attribs_defenseValue = droidDeedStats.getInt("creature_attribs.defenseValue");
        int creature_attribs_minDamage = droidDeedStats.getInt("creature_attribs.minDamage");
        int creature_attribs_maxDamage = droidDeedStats.getInt("creature_attribs.maxDamage");
        String creature_attribs_rangedWeapon = droidDeedStats.getString("creature_attribs.rangedWeapon");
        setObjVar(deed, "creature_attribs.level", creature_attribs_level);
        setObjVar(deed, "creature_attribs.maxHealth", creature_attribs_maxHealth);
        setObjVar(deed, "creature_attribs.maxConstitution", 0);
        setObjVar(deed, "creature_attribs.general_protection", creature_attribs_general_protection);
        setObjVar(deed, "creature_attribs.toHitChance", creature_attribs_toHitChance);
        setObjVar(deed, "creature_attribs.defenseValue", creature_attribs_defenseValue);
        setObjVar(deed, "creature_attribs.minDamage", creature_attribs_minDamage);
        setObjVar(deed, "creature_attribs.maxDamage", creature_attribs_maxDamage);
        setObjVar(deed, "creature_attribs.aggroBonus", 0.0f);
        setObjVar(deed, "creature_attribs.critChance", 0.0f);
        setObjVar(deed, "creature_attribs.critSave", 0.0f);
        setObjVar(deed, "creature_attribs.scale", 1.0f);
        setObjVar(deed, "creature_attribs.stateResist", 0.0f);
        if (creature_attribs_rangedWeapon != null && !creature_attribs_rangedWeapon.equals(""))
        {
            setObjVar(deed, "creature_attribs.rangedWeapon", creature_attribs_rangedWeapon);
        }
        return;
    }
    public static void reviseCombatDroidPCD(obj_id petControlDevice, obj_id master, obj_id pet) throws InterruptedException
    {
        if (!hasObjVar(petControlDevice, "postCuPCD"))
        {
            int combatStrength = 0;
            if (hasObjVar(petControlDevice, "pet.combatDroid"))
            {
                combatStrength = (int)(getFloatObjVar(petControlDevice, "pet.combatDroid"));
                cleanOldDroidDeed(petControlDevice);
                dictionary droidCombatStats = new dictionary();
                if (hasObjVar(petControlDevice, "armorModule"))
                {
                    droidCombatStats.put("armorModuleValue", getIntObjVar(petControlDevice, "armorModule"));
                }
                if (combatStrength > 0)
                {
                    droidCombatStats.put("combatModuleValue", combatStrength);
                }
                initDroidCombatStats(petControlDevice, droidCombatStats);
                messageTo(pet, "handlePackRequest", null, 1, false);
                dictionary respawnParams = new dictionary();
                respawnParams.put("master", master);
                messageTo(petControlDevice, "respawnDroid", respawnParams, 5, false);
            }
            else 
            {
                initDroidDefaultStats(petControlDevice);
            }
            setObjVar(petControlDevice, "postCuPCD", 2);
        }
        else if (getIntObjVar(petControlDevice, "postCuPCD") == 1)
        {
            if (hasObjVar(petControlDevice, "pet.nonCombatDroid"))
            {
                dictionary droidCombatStats = new dictionary();
                if (hasObjVar(petControlDevice, "armorModule"))
                {
                    droidCombatStats.put("armorModuleValue", getIntObjVar(petControlDevice, "armorModule"));
                }
                droidCombatStats.put("combatModuleValue", 300);
                initDroidCombatStats(petControlDevice, droidCombatStats);
                messageTo(pet, "handlePackRequest", null, 1, false);
                dictionary respawnParams = new dictionary();
                respawnParams.put("master", master);
                messageTo(petControlDevice, "respawnDroid", respawnParams, 5, false);
            }
            setObjVar(petControlDevice, "postCuPCD", 2);
        }
        return;
    }
    public static void openLearnCommandSui(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!pet_lib.getLearnableCommandList(pet))
        {
            sendSystemMessage(player, new string_id("pet/pet_Ability", "no_learnable_commands"));
            return;
        }
        String title = utils.packStringId(new string_id("pet/pet_ability", "learn_command_title"));
        String prompt = utils.packStringId(new string_id("pet/pet_ability", "learn_command_header"));
        int pid = createSUIPage(sui.SUI_LISTBOX, pet, player, "handleLearnCommandDialog");
        setSUIProperty(pid, "", "Size", "450,575");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@train");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        Vector commandList = utils.getResizeableStringArrayScriptVar(pet, "ai.learnCommand.commandNameList");
        for (int i = 0; i < commandList.size(); i++)
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, ((String)commandList.get(i)));
        }
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
        utils.setScriptVar(pet, "ai.learnCommand.pid", pid);
        utils.setScriptVar(pet, "ai.learnCommand.pet", pet);
    }
    public static boolean hasPetControlDevice(obj_id pet) throws InterruptedException
    {
        if (isValidId(pet) && exists(pet))
        {
            return callable.hasCallableCD(pet);
        }
        else 
        {
            return false;
        }
    }
    public static obj_id getPetControlDevice(obj_id pet) throws InterruptedException
    {
        obj_id petControlDevice = obj_id.NULL_ID;
        if (exists(pet) && isValidId(pet) && callable.hasCallableCD(pet))
        {
            petControlDevice = callable.getCallableCD(pet);
        }
        return petControlDevice;
    }
    public static void destroyOfficerPets(obj_id player) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad) || !exists(datapad))
        {
            return;
        }
        obj_id[] dataItems = getContents(datapad);
        if (dataItems == null || dataItems.length < 1)
        {
            return;
        }
        for (int i = 0; i < dataItems.length; i++)
        {
            if (hasObjVar(dataItems[i], "pet.creatureName"))
            {
                if ((getStringObjVar(dataItems[i], "pet.creatureName")).startsWith("officer_reinforcement_"))
                {
                    destroyObject(dataItems[i]);
                }
            }
        }
        return;
    }
    public static void addToHarvestDroidArray(obj_id target, obj_id[] players) throws InterruptedException
    {
        if (!isIdValid(target) || players == null || players.length == 0)
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            if (callable.hasCallable(players[i], callable.CALLABLE_TYPE_COMBAT_PET))
            {
                obj_id objCallable = callable.getCallable(players[i], callable.CALLABLE_TYPE_COMBAT_PET);
                if (!isIdValid(objCallable) || !exists(objCallable) || !hasScript(objCallable, "systems.crafting.droid.modules.harvest_module"))
                {
                    continue;
                }
                Vector toHarvest = new Vector();
                toHarvest.setSize(0);
                if (utils.hasScriptVar(objCallable, DROID_HARVEST_ARRAY))
                {
                    toHarvest = utils.getResizeableObjIdArrayScriptVar(objCallable, DROID_HARVEST_ARRAY);
                }
                utils.addElement(toHarvest, target);
                utils.setScriptVar(objCallable, DROID_HARVEST_ARRAY, toHarvest);
                messageTo(objCallable, "runHarvestRoutine", null, 1.0f, false);
            }
        }
    }
    public static boolean hasMountName(obj_id pcd) throws InterruptedException
    {
        if (!isIdValid(pcd) || !exists(pcd))
        {
            return true;
        }
        if (!hasObjVar(pcd, "pet.mountName"))
        {
            return false;
        }
        return true;
    }
    public static void setMountName(obj_id pcd, String mountName) throws InterruptedException
    {
        if (!isIdValid(pcd) || !exists(pcd))
        {
            return;
        }
        setName(pcd, mountName);
        setObjVar(pcd, "pet.mountName", mountName);
        obj_id yourPad = getContainedBy(pcd);
        obj_id player = getContainedBy(yourPad);
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        obj_id mount = callable.getCDCallable(pcd);
        if (!isIdValid(mount) || !exists(mount))
        {
            return;
        }
        setName(mount, mountName);
    }
    public static String getMountName(obj_id pcd) throws InterruptedException
    {
        if (hasObjVar(pcd, "pet.mountName"))
        {
            return getStringObjVar(pcd, "pet.mountName");
        }
        return null;
    }
    public static boolean isMounted(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            if (getState(player, STATE_RIDING_MOUNT) > 0)
            {
                return true;
            }
        }
        return false;
    }
    public static obj_id[] getPcdsForType(obj_id master, int type) throws InterruptedException
    {
        obj_id pDataPad = utils.getPlayerDatapad(master);
        obj_id[] dPadContents = utils.getContents(pDataPad, false);
        Vector resizedContents = new Vector();
        resizedContents.setSize(0);
        for (int i = 0; i < dPadContents.length; ++i)
        {
            if (isPetType(dPadContents[i], type))
            {
                utils.addElement(resizedContents, dPadContents[i]);
            }
        }
        obj_id[] pcdsForType = new obj_id[resizedContents.size()];
        resizedContents.toArray(pcdsForType);
        return pcdsForType;
    }
    public static obj_id validateDroidCommand(obj_id player) throws InterruptedException
    {
        obj_id droid = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
        if (!isIdValid(droid) || !exists(droid))
        {
            return null;
        }
        if (!pet_lib.isDroidPet(droid))
        {
            return null;
        }
        if (isIncapacitated(droid) || isDead(droid))
        {
            return null;
        }
        obj_id petControlDevice = callable.getCallableCD(droid);
        if (!isIdValid(petControlDevice) || !exists(petControlDevice))
        {
            return null;
        }
        int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
        if (powerLevel >= OUT_OF_POWER)
        {
            return null;
        }
        return droid;
    }
}
