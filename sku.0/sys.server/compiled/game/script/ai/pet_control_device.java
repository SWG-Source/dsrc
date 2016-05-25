package script.ai;

import script.*;
import script.library.*;

import java.util.Vector;

public class pet_control_device extends script.base_script
{
    public pet_control_device()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String PCDPING_PCD_SCRIPT_NAME = "ai.pcd_ping_response";
    public static final String MESSAGE_PET_ID = "petId";
    public static final String CALLED_FOR_PET = "player.calledForPet";
    public static final string_id SID_NO_VALID_MEDICINE = new string_id("pet/pet_menu", "no_valid_medicine");
    public static final string_id SID_MUST_DISMOUNT = new string_id("pet/pet_menu", "must_dismount");
    public static final string_id SID_CONVERT_PROMPT = new string_id(pet_lib.MENU_FILE, "convert_pet_prompt");
    public static final string_id SID_CONVERT_TITLE = new string_id(pet_lib.MENU_FILE, "convert_pet_title");
    public static final string_id SID_NO_MOUNT_WHILE_STEALTH = new string_id(pet_lib.MENU_FILE, "no_mount_stealth");
    public static final string_id SHAPECHANGE = new string_id("spam", "not_while_shapechanged");
    public static final boolean debug = false;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pet_control_device.OnInitialize(): self=[" + self + "] entered");
        }
        if (!hasScript(self, PCDPING_PCD_SCRIPT_NAME))
        {
            if (debug)
            {
                LOG("pcdping-debug", "pet_control_device.OnInitialize(): attaching script [" + PCDPING_PCD_SCRIPT_NAME + "] to PCD id=[" + self + "]");
            }
            attachScript(self, PCDPING_PCD_SCRIPT_NAME);
        }
        if (debug)
        {
            LOG("pcdping-debug", "pet_control_device.OnInitialize(): self=[" + self + "] exited");
        }
        if (hasObjVar(self, "module_data.struct_maint"))
        {
            int struct = getIntObjVar(self, "module_data.struct_maint");
            if (struct > 15)
            {
                struct = 15;
                setObjVar(self, "module_data.struct_maint", struct);
            }
        }
        if (hasObjVar(self, "ai.pet.commandList"))
        {
            String[] commandList = getStringArrayObjVar(self, "ai.pet.commandList");
            if (commandList != null && commandList.length > 0)
            {
                for (int i = 0; i < commandList.length; i++)
                {
                    if (!commandList[i].equals(""))
                    {
                        setObjVar(self, "ai.pet.command." + i, commandList[i]);
                    }
                }
            }
            removeObjVar(self, "ai.pet.commandList");
        }
        if (!hasObjVar(self, "ai.petAbility.available"))
        {
            int petType = getIntObjVar(self, "ai.pet.type");
            if (petType == pet_lib.PET_TYPE_AGGRO || petType == pet_lib.PET_TYPE_NON_AGGRO)
            {
                int level = getLevelFromPetControlDevice(self);
                int maxSlots = pet_lib.getMaxAbilitySlots(level);
                if (hasObjVar(self, "pet.crafted"))
                {
                    maxSlots = (int)(maxSlots * 0.7f);
                }
                else 
                {
                    maxSlots = (int)(maxSlots * 0.9f);
                }
                setObjVar(self, "ai.petAbility.toBeEarned", maxSlots);
                setObjVar(self, "ai.petAbility.available", 0);
            }
        }
        if (hasObjVar(self, "ai.pet.trainedMount"))
        {
            setObjVar(self, "ai.pet.type", pet_lib.PET_TYPE_MOUNT);
        }
        if (hasObjVar(self, "dancingDroid.soundObject"))
        {
            obj_id soundObject = getObjIdObjVar(self, "dancingDroid.soundObject");
            dictionary dict = new dictionary();
            dict.put("pcd", self);
            messageTo(soundObject, "destroyDancingDroidSoundObject", dict, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myContainer = getContainedBy(self);
        obj_id yourPad = utils.getPlayerDatapad(player);
        if (myContainer != yourPad)
        {
            if (hasScript(myContainer, "ai.pet_control_device"))
            {
                putIn(self, yourPad);
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, beast_lib.OBJVAR_OLD_PET_IDENTIFIER) && !pet_lib.isPetType(self, pet_lib.PET_TYPE_FAMILIAR) && !pet_lib.isPetType(self, pet_lib.PET_TYPE_NPC) && !pet_lib.isPetType(self, pet_lib.PET_TYPE_DROID))
        {
            if (!hasObjVar(self, "noStuff"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(pet_lib.MENU_FILE, "convert_pet"));
            }
        }
        if (pet_lib.isMountPcd(self) && callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE && !pet_lib.hasMountName(self))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("pet/pet_menu", "name_mount"));
        }
        obj_id currentPet = callable.getCDCallable(self);
        if (!isIdValid(currentPet))
        {
            if (callable.hasCDCallable(self))
            {
                if (currentPet.isLoaded())
                {
                    destroyObject(currentPet);
                    callable.setCDCallable(self, null);
                }
                else 
                {
                    messageTo(currentPet, "handlePackRequest", null, 1, false);
                }
            }
            if (!isSpaceScene() || getState(player, STATE_SHIP_INTERIOR) == 1)
            {
                setCount(self, 0);
                mi.addRootMenu(menu_info_types.PET_CALL, new string_id(pet_lib.MENU_FILE, "menu_call"));
            }
        }
        else 
        {
            if (currentPet.isLoaded() && exists(currentPet))
            {
                if (!hasObjVar(currentPet, battlefield.VAR_CONSTRUCTED))
                {
                    mi.addRootMenu(menu_info_types.PET_CALL, new string_id(pet_lib.MENU_FILE, "menu_store"));
                }
            }
            else 
            {
                mi.addRootMenu(menu_info_types.PET_CALL, new string_id(pet_lib.MENU_FILE, "menu_store"));
            }
        }
        int maxVitality = pet_lib.MAX_VITALITY_LOSS - getIntObjVar(self, "pet.maxVitality");
        if (maxVitality < 1)
        {
            setObjVar(self, "pet.maxVitality", pet_lib.MAX_VITALITY_LOSS);
            maxVitality = 0;
        }
        int vitality = maxVitality - getIntObjVar(self, "pet.vitality");
        if (vitality < 1)
        {
            setObjVar(self, "pet.vitality", pet_lib.MAX_VITALITY_LOSS);
            vitality = 0;
        }
        if (vitality < maxVitality)
        {
            mi.addRootMenu(menu_info_types.SERVER_HEAL_WOUND, new string_id(pet_lib.MENU_FILE, "menu_vitapack"));
        }
        if (hasObjVar(self, "ai.pet.command"))
        {
            if (hasObjVar(self, "ai.pet.type"))
            {
                int petType = getIntObjVar(self, "ai.pet.type");
                if (petType == 4)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            mi.addRootMenu(menu_info_types.PET_COMMAND, new string_id(pet_lib.MENU_FILE, "menu_commandlist"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player) || getPerformanceType(player) != 0)
        {
            sendSystemMessage(player, new string_id("spam", "cant_do_it_state"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.PET_COMMAND)
        {
            listAllCommands(self, player);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.PET_CALL)
        {
            int petType = getIntObjVar(self, "ai.pet.type");
            if ((petType == pet_lib.PET_TYPE_NON_AGGRO || petType == pet_lib.PET_TYPE_AGGRO) && !hasObjVar(self, "ai.pet.trainedMount"))
            {
                return SCRIPT_CONTINUE;
            }
            String petObjVar = getStringObjVar(self, "pet.creatureName");
            if (utils.hasScriptVar(player, "petCreationPending") && utils.getIntScriptVar(player, "petCreationPending") > getGameTime())
            {
                return SCRIPT_CONTINUE;
            }
            if (isSpaceScene() && getState(player, STATE_SHIP_INTERIOR) != 1)
            {
                string_id strSpam = new string_id("space/space_interaction", "no_action_station");
                sendSystemMessage(player, strSpam);
                return SCRIPT_CONTINUE;
            }
            if (isOnMaintRun(self))
            {
                sendSystemMessage(player, new string_id("pet/droid_modules", "droid_maint_on_maint_run"));
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, CALLED_FOR_PET))
            {
                int timeLeft = pet_lib.getTimeLeft(player, CALLED_FOR_PET, pet_lib.CALL_FINISH);
                if (timeLeft < 0)
                {
                    utils.removeScriptVar(player, CALLED_FOR_PET);
                    return SCRIPT_CONTINUE;
                }
                prose_package pp = prose.getPackage(pet_lib.SID_SYS_CALL_DELAY_FINISH_PET, timeLeft);
                sendSystemMessageProse(player, pp);
                return SCRIPT_CONTINUE;
            }
            String creatureName = getStringObjVar(self, "pet.creatureName");
            if (creatureName == null || creatureName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id currentPet = obj_id.NULL_ID;
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_RIDEABLE) && callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE)
            {
                currentPet = callable.getCallable(player, callable.CALLABLE_TYPE_RIDEABLE);
                obj_id cd = callable.getCallableCD(currentPet);
                if (cd != self)
                {
                    sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL_ANOTHER_RIDEABLE);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (pet_lib.isMountedOnCreatureQueried(currentPet, player))
                    {
                        dismountCreature(player);
                        pet_lib.setUnmountedMovementRate(player, currentPet);
                        utils.removeScriptVar(player, callable.OBJVAR_CALLABLE_TYPE_RIDEABLE);
                    }
                    destroyCurrentPet(self);
                    callable.restoreCallable(player);
                    return SCRIPT_CONTINUE;
                }
            }
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_RIDEABLE) && callable.getControlDeviceType(self) != callable.CALLABLE_TYPE_RIDEABLE)
            {
                obj_id rideable = callable.getCallable(player, callable.CALLABLE_TYPE_RIDEABLE);
                if (isIdValid(rideable) && exists(rideable))
                {
                    callable.storeCallable(player, rideable);
                }
            }
            if (callable.hasCDCallable(self) && !ai_lib.isInCombat(player))
            {
                callable.storeCallable(player, callable.getCDCallable(self));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (ai_lib.isInCombat(player))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_FAMILIAR) && callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_FAMILIAR)
            {
                currentPet = callable.getCallable(player, callable.CALLABLE_TYPE_FAMILIAR);
                obj_id cd = callable.getCallableCD(currentPet);
                if (cd != self)
                {
                    sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL_ANOTHER_FAMILIAR);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    destroyCurrentPet(self);
                }
                return SCRIPT_CONTINUE;
            }
            if (callable.hasCDCallable(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (!pet_lib.isInValidUnpackLocation(player))
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                return SCRIPT_CONTINUE;
            }
            if (pet_lib.isMountPcd(self) && pet_lib.isInRestrictedScene(player))
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                return SCRIPT_CONTINUE;
            }
            if ((getLocation(player).area).startsWith("kashyyyk_pob") && getTopMostContainer(player) == player)
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                return SCRIPT_CONTINUE;
            }
            if (!validatePetStats(self, player))
            {
                return SCRIPT_CONTINUE;
            }
            if (petIsDead(self, player, petType))
            {
                return SCRIPT_CONTINUE;
            }
            if (pet_lib.hasMaxPets(player, petType))
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                return SCRIPT_CONTINUE;
            }
            int petLevel = getLevelFromPetControlDevice(self);
            if (getLevel(player) < petLevel - pet_lib.MAX_PET_LEVELS_ABOVE_CALLER && !pet_lib.isMountPcd(self))
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL_LEVEL);
                return SCRIPT_CONTINUE;
            }
            if (!isSameFaction(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE && getMovementPercent(player) == 0.0f)
            {
                sendSystemMessage(player, new string_id("pet/pet_menu", "cant_mount_rooted"));
                return SCRIPT_CONTINUE;
            }
            if (callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE && getPosture(player) != POSTURE_UPRIGHT)
            {
                setPosture(player, POSTURE_UPRIGHT);
            }
            if (callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE && callable.hasCallable(player, callable.CALLABLE_TYPE_COMBAT_PET))
            {
                obj_id objCallable = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
                if (beast_lib.isBeast(objCallable))
                {
                    utils.setScriptVar(player, callable.SCRIPTVAR_RIDEABLE_PACKED_CALLABLE, callable.getCallableCD(objCallable));
                }
                callable.storeCallable(player, objCallable);
            }
            if (callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE && callable.hasCallable(player, callable.CALLABLE_TYPE_FAMILIAR))
            {
                obj_id objCallable = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
                callable.storeCallable(player, objCallable);
            }
            int callTime = pet_lib.CALL_DELAY;
            if (pet_lib.wasInCombat(player))
            {
                callTime = pet_lib.modifyCallTime(player, pet_lib.COMBAT_ENDED, pet_lib.CALL_DELAY, pet_lib.POST_COMBAT_DELAY);
            }
            prose_package pp = prose.getPackage(pet_lib.SID_SYS_CALL_PET_DELAY, callTime);
            sendSystemMessageProse(player, pp);
            dictionary data = new dictionary();
            data.put("player", player);
            data.put("pcd", self);
            utils.setScriptVar(player, CALLED_FOR_PET, getGameTime());
            utils.setScriptVar(player, pet_lib.CALL_FINISH, callTime);
            utils.setScriptVar(player, "petCreationPending", (getGameTime() + pet_lib.BUTTON_MASHING_DELAY));
            messageTo(self, "delayCreatePet", data, callTime, false);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_HEAL_WOUND)
        {
            obj_id pet_med = healing.findPetVitalityMed(player);
            if (!isIdValid(pet_med))
            {
                obj_id pet = callable.getCDCallable(self);
                if (pet_lib.performVitalityHeal(player, pet, self, pet_med))
                {
                    consumable.decrementCharges(pet_med, player);
                }
            }
            else 
            {
                sendSystemMessage(self, SID_NO_VALID_MEDICINE);
            }
        }
        else if (item == menu_info_types.SERVER_MENU1 && !hasObjVar(self, beast_lib.OBJVAR_OLD_PET_IDENTIFIER) && !pet_lib.isPetType(self, pet_lib.PET_TYPE_FAMILIAR) && !pet_lib.isPetType(self, pet_lib.PET_TYPE_NPC) && !pet_lib.isPetType(self, pet_lib.PET_TYPE_DROID))
        {
            int pid = sui.msgbox(self, player, "@" + SID_CONVERT_PROMPT, sui.YES_NO, "@" + SID_CONVERT_TITLE, "handleConvertPetSui");
        }
        if (item == menu_info_types.SERVER_MENU2 && callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE && !pet_lib.hasMountName(self))
        {
            sui.inputbox(self, player, "@pet/pet_menu:name_d", sui.OK_CANCEL, "@pet/pet_menu:name_t", sui.INPUT_NORMAL, null, "handleSetMountName", null);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetMountName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String mountName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (mountName.equals("") || isNameReserved(mountName))
        {
            sendSystemMessage(player, new string_id("player_structure", "obscene"));
            sui.inputbox(self, player, "@pet/pet_menu:name_d", sui.OK_CANCEL, "@pet/pet_menu:name_t", sui.INPUT_NORMAL, null, "handleSetMountName", null);
            return SCRIPT_CONTINUE;
        }
        if (mountName.length() < 3)
        {
            sendSystemMessage(player, new string_id("pet/pet_menu", "mount_name_too_short"));
            sui.inputbox(self, player, "@pet/pet_menu:name_d", sui.OK_CANCEL, "@pet/pet_menu:name_t", sui.INPUT_NORMAL, null, "handleSetMountName", null);
            return SCRIPT_CONTINUE;
        }
        if (mountName.length() > 40)
        {
            sendSystemMessage(player, new string_id("pet/pet_menu", "mount_name_too_long"));
            sui.inputbox(self, player, "@pet/pet_menu:name_d", sui.OK_CANCEL, "@pet/pet_menu:name_t", sui.INPUT_NORMAL, null, "handleSetMountName", null);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        pet_lib.setMountName(self, mountName);
        return SCRIPT_CONTINUE;
    }
    public int delayCreatePet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id pcd = params.getObjId("pcd");
        if (ai_lib.isInCombat(player) || pet_lib.wasInCombat(player))
        {
            utils.removeScriptVar(player, CALLED_FOR_PET);
            return SCRIPT_CONTINUE;
        }
        if (!isGod(player))
        {
            obj_id mount = getMountId(player);
            if (vehicle.isRidingVehicle(player) || isIdValid(mount))
            {
                if (!vehicle.canQuickUnpack(player))
                {
                    utils.removeScriptVar(player, CALLED_FOR_PET);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (!pet_lib.isInValidUnpackLocation(player))
        {
            sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
            utils.removeScriptVar(player, CALLED_FOR_PET);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(pcd, "pet.creatureName"))
        {
            int petType = pet_lib.getPetType(getStringObjVar(pcd, "pet.creatureName"));
            if (hasObjVar(pcd, "ai.pet.trainedMount"))
            {
                petType = pet_lib.PET_TYPE_MOUNT;
            }
            if (pet_lib.countPetsOfType(self, petType) >= pet_lib.getMaxPets(self, petType))
            {
                utils.removeScriptVar(player, CALLED_FOR_PET);
                return SCRIPT_CONTINUE;
            }
        }
        pet_lib.createPetFromData(pcd);
        obj_id pet = callable.getCDCallable(pcd);
        if (pet_lib.isMountPcd(pcd))
        {
            if (!hasObjVar(pcd, "pet.species"))
            {
                int species = getSpecies(pet);
                if (species >= 0)
                {
                    setObjVar(pcd, "pet.species", species);
                }
            }
        }
        if (isIdValid(pet))
        {
            dictionary parms = new dictionary();
            parms.put("calledPet", pet);
            messageTo(pcd, "handleValidatePet", parms, 5, false);
        }
        utils.removeScriptVar(player, CALLED_FOR_PET);
        if (hasObjVar(pcd, "ai.pet.trainedMount"))
        {
            String invis = stealth.getInvisBuff(player);
            if (invis != null && !invis.equals(""))
            {
                sendSystemMessage(player, SID_NO_MOUNT_WHILE_STEALTH);
                callable.storeCallable(player, pet);
                return SCRIPT_CONTINUE;
            }
            dictionary dict = new dictionary();
            dict.put("player", player);
            dict.put("pet", pet);
            if (hasObjVar(self, beast_lib.OBJVAR_BEAST_HUE))
            {
                int mountHue = getIntObjVar(self, beast_lib.OBJVAR_BEAST_HUE);
                hue.setColor(pet, hue.INDEX_1, mountHue);
            }
            messageTo(self, "delayMountPet", dict, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int delayMountPet(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id pet = params.getObjId("pet");
        if (!isIdValid(player) || !exists(player) || !isIdValid(pet) || !exists(pet))
        {
            return SCRIPT_CONTINUE;
        }
        if (getPosture(player) != POSTURE_UPRIGHT || ai_lib.isInCombat(player) || getPerformanceType(player) != 0)
        {
            destroyCurrentPet(self);
            return SCRIPT_CONTINUE;
        }
        int shapechange = buff.getBuffOnTargetFromGroup(player, "shapechange");
        if (shapechange != 0)
        {
            sendSystemMessage(player, SHAPECHANGE);
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isMounted(player))
        {
            dismountCreature(player);
        }
        mountCreature(player, pet);
        pet_lib.setMountedMovementRate(player, pet);
        return SCRIPT_CONTINUE;
    }
    public void destroyCurrentPet(obj_id petControlDevice) throws InterruptedException
    {
        obj_id datapad = getContainedBy(petControlDevice);
        obj_id player = getContainedBy(datapad);
        obj_id currentPet = callable.getCDCallable(petControlDevice);
        if (isIdValid(currentPet))
        {
            if (currentPet.isLoaded() && exists(currentPet))
            {
                if (!destroyObject(currentPet))
                {
                    sendSystemMessage(player, SID_MUST_DISMOUNT);
                    return;
                }
            }
            else 
            {
                messageTo(currentPet, "handlePackRequest", null, 1, false);
            }
        }
        setTimeStored(petControlDevice);
        callable.setCDCallable(petControlDevice, null);
    }
    public int handleStorePetRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id currentPet = callable.getCDCallable(self);
        if (!isIdValid(currentPet))
        {
            destroyCurrentPet(self);
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("master");
        if (player.isLoaded() && currentPet.isLoaded())
        {
            if (pet_lib.wasInCombatRecently(currentPet, player, true))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (currentPet.isLoaded() && ai_lib.isInCombat(currentPet))
        {
            return SCRIPT_CONTINUE;
        }
        destroyCurrentPet(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasScript(item, "ai.pet_control_device"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "pet.releasingPet"))
        {
            destroyCurrentPet(self);
        }
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getContainedBy(datapad);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "pet.isDead"))
        {
            int petType = getIntObjVar(self, "ai.pet.type");
            if (petType == pet_lib.PET_TYPE_NON_AGGRO || petType == pet_lib.PET_TYPE_AGGRO)
            {
                sendSystemMessage(master, new string_id("pet/pet_menu", "pet_released"));
            }
        }
        if (callable.hasCDCallable(self))
        {
            obj_id pet = callable.getCDCallable(self);
            obj_id rider = getRiderId(pet);
            if (isIdValid(rider) && exists(rider))
            {
                dismountCreature(rider);
                utils.removeScriptVar(rider, callable.OBJVAR_CALLABLE_TYPE_RIDEABLE);
            }
            destroyObject(pet);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        destroyCurrentPet(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGrowthChoice(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        obj_id petControlDevice = null;
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (utils.hasScriptVar(player, "mountgrowthcheck.pcd"))
        {
            petControlDevice = utils.getObjIdScriptVar(player, "mountgrowthcheck.pcd");
            utils.removeScriptVar(player, "mountgrowthcheck.pcd");
        }
        if (bp == sui.BP_CANCEL)
        {
            setObjVar(petControlDevice, "ai.petAdvance.letPetGrow", 1);
            return SCRIPT_CONTINUE;
        }
        int growthStage;
        if (!hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
        {
            growthStage = 10;
        }
        else 
        {
            growthStage = getIntObjVar(petControlDevice, "ai.petAdvance.growthStage");
        }
        int largestMountableGrowthStage = getIntObjVar(petControlDevice, "ai.petAdvance.lrgMnt");
        setObjVar(petControlDevice, "ai.petAdvance.growthStage", largestMountableGrowthStage);
        setObjVar(petControlDevice, "ai.petAdvance.freezegrowth", largestMountableGrowthStage);
        pet_lib.createPetFromData(petControlDevice);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getContainedBy(datapad);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(master, new string_id("pet/pet_menu", "device_added"));
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        String creatureName = getStringObjVar(self, "pet.creatureName");
        if (creatureName == null || creatureName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (callable.getControlDeviceType(self) == callable.CALLABLE_TYPE_RIDEABLE)
        {
            names[idx] = "rideables_current_of_max";
            int currentRideables = callable.getNumStoredCDByType(player, callable.CALLABLE_TYPE_RIDEABLE);
            int maxRideables = callable.getMaxAllowedStoredRideables(player);
            attribs[idx] = "" + target_dummy.BLUE + currentRideables + " of " + maxRideables;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        boolean isMount = pet_lib.isMountPcd(self);
        if (pet_lib.isDroidPet(self))
        {
            int level = getLevelFromPetControlDevice(self);
            names[idx] = "challenge_level";
            int adultLevel = pet_lib.getAdultLevel(creatureName);
            if (hasObjVar(self, "creature_attribs.level"))
            {
                adultLevel = getIntObjVar(self, "creature_attribs.level");
            }
            if (adultLevel != level)
            {
                attribs[idx] = Integer.toString(level) + " (" + Integer.toString(adultLevel) + ")";
            }
            else 
            {
                attribs[idx] = Integer.toString(level);
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "ai.petAbility.available"))
            {
                int available = getIntObjVar(self, "ai.petAbility.available");
                int toBeEarned = getIntObjVar(self, "ai.petAbility.toBeEarned");
                names[idx] = "ability_slots";
                if (available == 0 && toBeEarned == 0)
                {
                    attribs[idx] = utils.packStringId(new string_id("obj_attr_n", "ability_slot_max"));
                }
                else 
                {
                    attribs[idx] = "" + available;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int trainingPts = getIntObjVar(self, "ai.petAbility.trainingPts");
                    names[idx] = "ability_points";
                    attribs[idx] = "" + trainingPts;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int xpEarned = getIntObjVar(self, "ai.petAdvance.xpEarned");
                    int xpToLevel = pet_lib.getPcdXpForNextLevel(self);
                    names[idx] = "ability_xp";
                    attribs[idx] = "" + xpEarned + "/" + xpToLevel;
                }
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            int maxVitality = pet_lib.MAX_VITALITY_LOSS - getIntObjVar(self, "pet.maxVitality");
            if (maxVitality < 1)
            {
                setObjVar(self, "pet.maxVitality", pet_lib.MAX_VITALITY_LOSS);
                maxVitality = 0;
            }
            int vitality = maxVitality - getIntObjVar(self, "pet.vitality");
            if (vitality < 1)
            {
                setObjVar(self, "pet.vitality", pet_lib.MAX_VITALITY_LOSS);
                vitality = 0;
            }
            names[idx] = "creature_vitality";
            attribs[idx] = "" + vitality + "/" + maxVitality;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "pet.crafted"))
            {
                if (hasObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]))
                {
                    names[idx] = "pet_stats.creature_health";
                    int health = getIntObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]);
                    health += pet_lib.getPetAbilityHealthBonus(self, health);
                    if (vitality < pet_lib.MAX_VITALITY_LOSS)
                    {
                        attribs[idx] = "" + pet_lib.getReducedStatValue(health, vitality) + "/";
                    }
                    else 
                    {
                        attribs[idx] = "";
                    }
                    attribs[idx] += "" + health;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[CONSTITUTION]))
                {
                    names[idx] = "pet_stats.creature_health_regen";
                    int regen = getIntObjVar(self, "creature_attribs." + create.MAXATTRIBNAMES[CONSTITUTION]);
                    regen += pet_lib.getPetAbilityRegenBonus(self, regen);
                    if (vitality < pet_lib.MAX_VITALITY_LOSS)
                    {
                        attribs[idx] = "" + pet_lib.getReducedStatValue(regen, vitality) + "/";
                    }
                    else 
                    {
                        attribs[idx] = "";
                    }
                    attribs[idx] += "" + regen;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "creature_attribs.general_protection"))
                {
                    int general_protection = getIntObjVar(self, "creature_attribs.general_protection");
                    names[idx] = "pet_stats.dna_comp_armor_effectiveness";
                    attribs[idx] = "" + general_protection;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                int niche = dataTableGetInt(CREATURE_TABLE, creatureName, "niche");
                if (niche != NICHE_DROID || pet_lib.isCombatDroidPCD(self))
                {
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
                }
                if (hasObjVar(self, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON))
                {
                    String weapon = getStringObjVar(self, "creature_attribs." + bio_engineer.ATTRIB_DICT_RANGED_WEAPON);
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
                if (niche == NICHE_DROID || niche == NICHE_ANDROID)
                {
                    int powerLevel = getIntObjVar(self, "ai.pet.powerLevel");
                    float percentRemaining = (((float)pet_lib.OUT_OF_POWER - (float)powerLevel) / (float)pet_lib.OUT_OF_POWER) * 100.0f;
                    names[idx] = "pet_stats.battery_power";
                    attribs[idx] = " " + (int)percentRemaining + "%";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "medpower"))
                {
                    names[idx] = "pet_stats.medpower";
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
                    names[idx] = "pet_stats.is_repair_droid";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "storageModuleRating"))
                {
                    names[idx] = "pet_stats.storage_module_rating";
                    int storage = getIntObjVar(self, "storageModuleRating");
                    attribs[idx] = "" + storage;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "ai.pet.hasContainer"))
                {
                    names[idx] = "pet_stats.storage_module_rating";
                    int storage = getIntObjVar(self, "ai.pet.hasContainer");
                    attribs[idx] = "" + storage;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    obj_id petInv = utils.getInventoryContainer(self);
                    if (isIdValid(petInv))
                    {
                        storage = getTotalVolume(petInv);
                        if (storage > 0)
                        {
                            names[idx] = "pet_stats.inventory_capacity";
                            attribs[idx] = "" + storage;
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
                if (hasObjVar(self, "dataModuleRating"))
                {
                    int datastorage = getIntObjVar(self, "dataModuleRating");
                    if (space_crafting.isUsableAstromechPet(self))
                    {
                        obj_id[] loadedDroidCommands = space_crafting.getDatapadDroidCommands(self);
                        if ((loadedDroidCommands != null) && (loadedDroidCommands.length > 0))
                        {
                            names[idx] = "pet_stats.droid_program_loaded";
                            attribs[idx] = " ";
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
                            for (int i = 0; i < loadedDroidCommands.length; i++)
                            {
                                if (hasObjVar(loadedDroidCommands[i], "strDroidCommand"))
                                {
                                    String programName = getStringObjVar(loadedDroidCommands[i], "strDroidCommand");
                                    names[idx] = "pet_stats.droid_program";
                                    attribs[idx] = " " + localize(new string_id("space/droid_commands", programName));
                                    idx++;
                                    if (idx >= names.length)
                                    {
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
                        attribs[idx] = "" + datastorage;
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        obj_id petInv = utils.getObjectInSlot(self, "datapad");
                        if (isIdValid(petInv))
                        {
                            int storage = getTotalVolume(petInv);
                            if (storage > 0)
                            {
                                names[idx] = "pet_stats.datapad_slots";
                                attribs[idx] = "" + storage;
                                idx++;
                                if (idx >= names.length)
                                {
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                }
                if (hasObjVar(self, "craftingStation"))
                {
                    names[idx] = "pet_stats.crafting_station";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "craftingStationSpace"))
                {
                    names[idx] = "pet_stats.crafting_station_space";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "craftingStationStructure"))
                {
                    names[idx] = "pet_stats.crafting_station_structure";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "craftingStationClothing"))
                {
                    names[idx] = "pet_stats.crafting_station_clothing";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "craftingStationFood"))
                {
                    names[idx] = "pet_stats.crafting_station_food";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "craftingStationWeapon"))
                {
                    names[idx] = "pet_stats.crafting_station_weapon";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "ai.diction"))
                {
                    names[idx] = "pet_stats.ai_diction";
                    String personality = getStringObjVar(self, "ai.diction");
                    attribs[idx] = " " + localize(new string_id("npc_reaction/npc_diction", personality));
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "module_data.bomb_level"))
                {
                    names[idx] = "pet_stats.bomb_level";
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
                    names[idx] = "pet_stats.merchant_barker";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "module_data.stimpack_capacity"))
                {
                    names[idx] = "pet_stats.stimpack_power";
                    int power = getIntObjVar(self, "module_data.stim_power");
                    int supply = getIntObjVar(self, "module_data.stimpack_supply");
                    int capacity = getIntObjVar(self, "module_data.stimpack_capacity");
                    int speed = getIntObjVar(self, "module_data.stimpack_speed");
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
                if (hasObjVar(self, "module_data.auto_repair_power"))
                {
                    names[idx] = "pet_stats.auto_repair_power";
                    int datastorage = getIntObjVar(self, "module_data.auto_repair_power");
                    attribs[idx] = " " + datastorage;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                if (hasObjVar(self, "module_data.playback.modules"))
                {
                    names[idx] = "pet_stats.playback_modules";
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
                    names[idx] = "pet_stats.harvest_power";
                    int bonusHarvest = getIntObjVar(self, "module_data.harvest_power");
                    attribs[idx] = " " + bonusHarvest;
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
                        for (int i = 0; i < effects.length; i++)
                        {
                            names[idx] = "pet_stats." + effects[i];
                            attribs[idx] = " installed";
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
                if (hasObjVar(self, "module_data.struct_maint"))
                {
                    names[idx] = "pet_stats.struct_module_rating";
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
                    names[idx] = "pet_stats.trap_bonus";
                    int trapBonus = getIntObjVar(self, "module_data.trap_bonus");
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
            else 
            {
                dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, creatureName);
                if (creatureDict == null)
                {
                    return SCRIPT_CONTINUE;
                }
                int baseLevel = creatureDict.getInt("BaseLevel");
                int dmgLevel = baseLevel + creatureDict.getInt("Damagelevelmodifier");
                int statLevel = baseLevel + creatureDict.getInt("StatLevelModifier");
                int toHitLevel = baseLevel + creatureDict.getInt("ToHitLevelModifier");
                int armorLevel = baseLevel + creatureDict.getInt("ArmorLevelModifier");
                if (dmgLevel <= 0)
                {
                    dmgLevel = 1;
                }
                if (statLevel <= 0)
                {
                    statLevel = 1;
                }
                if (toHitLevel <= 0)
                {
                    toHitLevel = 1;
                }
                if (armorLevel <= 0)
                {
                    armorLevel = 1;
                }
                int myHealth = dataTableGetInt(create.STAT_BALANCE_TABLE, statLevel - 1, "HP");
                if (myHealth < 1)
                {
                    myHealth = 1;
                }
                names[idx] = "pet_stats.creature_health";
                myHealth += pet_lib.getPetAbilityHealthBonus(self, myHealth);
                if (vitality < pet_lib.MAX_VITALITY_LOSS)
                {
                    attribs[idx] = "" + pet_lib.getReducedStatValue(myHealth, vitality) + "/";
                }
                else 
                {
                    attribs[idx] = "";
                }
                attribs[idx] += "" + myHealth;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "pet_stats.creature_attack";
                float attackSpeed = creatureDict.getInt("attackSpeed");
                attackSpeed = (float)((int)(attackSpeed * 100) / 100f);
                attribs[idx] = "" + attackSpeed;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "pet_stats.creature_tohit";
                int toHit = dataTableGetInt(create.STAT_BALANCE_TABLE, toHitLevel - 1, "ToHit");
                attribs[idx] = "" + toHit;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "pet_stats.creature_defense";
                int defenseValue = dataTableGetInt(create.STAT_BALANCE_TABLE, toHitLevel - 1, "Def");
                attribs[idx] = "" + defenseValue;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                float dps = dataTableGetFloat(create.STAT_BALANCE_TABLE, dmgLevel - 1, "damagePerSecond");
                int minDamage = Math.round((dps * attackSpeed) * 0.5f);
                int maxDamage = Math.round((dps * attackSpeed) * 1.5f);
                int[] dmgBonus = pet_lib.getPetAbilityDamageBonus(self, minDamage, maxDamage);
                minDamage += dmgBonus[0];
                maxDamage += dmgBonus[1];
                names[idx] = "pet_stats.creature_damage";
                attribs[idx] = "" + minDamage + " - " + maxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            int niche = dataTableGetInt(pet_lib.CREATURE_TABLE, creatureName, "niche");
            if ((niche == NICHE_DROID || niche == NICHE_ANDROID) && hasObjVar(self, pet_lib.VAR_PALVAR_CNT))
            {
                names[idx] = "pet_stats.customization_cnt";
                attribs[idx] = Integer.toString(getIntObjVar(self, pet_lib.VAR_PALVAR_CNT));
                idx++;
            }
            if (hasObjVar(self, "droid_trap"))
            {
                int maxTrapCount = pet_lib.calculateMaxTrapLoad(self);
                names[idx] = "pet_stats.max_trap_load";
                attribs[idx] = Integer.toString(maxTrapCount);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                String trapName = localize(new string_id("item_n", "trap_" + getStringObjVar(self, "droid_trap.trap_num.trap_name")));
                names[idx] = "pet_stats.droid_trap_type";
                attribs[idx] = trapName;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                int numTraps = getIntObjVar(self, "droid_trap.trap_num.charges");
                names[idx] = "pet_stats.droid_trap_number";
                attribs[idx] = Integer.toString(numTraps);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, "ai.petAbility.abilityList"))
            {
                int[] abilityList = getIntArrayObjVar(self, "ai.petAbility.abilityList");
                if (abilityList != null && abilityList.length > 0)
                {
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    for (int i = 0; i < abilityList.length; i++)
                    {
                        if (abilityList[i] != 0)
                        {
                            names[idx] = "pet_ability." + pet_lib.getPetAbilityFromCrc(abilityList[i]);
                            attribs[idx] = " ";
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
            }
            if (hasObjVar(self, "ai.pet.command"))
            {
                if (hasObjVar(self, "ai.pet.type"))
                {
                    int petType = getIntObjVar(self, "ai.pet.type");
                    if (petType == 4)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                obj_var_list commandList = getObjVarList(self, "ai.pet.command");
                if (commandList != null && commandList.getNumItems() > 0)
                {
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int numItems = commandList.getNumItems();
                    for (int i = 0; i < numItems; i++)
                    {
                        obj_var ov = commandList.getObjVar(i);
                        String command = ov.getName();
                        int commandNum = Integer.parseInt(command);
                        if (commandNum >= pet_lib.COMMAND_FOLLOW && commandNum < pet_lib.NUM_COMMANDS)
                        {
                            names[idx] = "pet_command.pet_command_" + ov.getName();
                            attribs[idx] = ov.getStringData();
                            idx++;
                            if (idx >= names.length)
                            {
                                return SCRIPT_CONTINUE;
                            }
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
        if (isMount)
        {
            int speciesNum = getIntObjVar(self, "pet.species");
            String strSkeleton = ai_lib.getSkeleton(speciesNum);
            if (strSkeleton != null && !strSkeleton.equals(""))
            {
                dictionary dctMountInfo = dataTableGetRow("datatables/mount/mount_movement_speeds.iff", strSkeleton);
                if (dctMountInfo != null)
                {
                    float fltBaseWalkSpeed = dctMountInfo.getFloat("fltBaseWalkSpeed");
                    float fltBurstRunSpeed = dctMountInfo.getFloat("fltBaseRunSpeed");
                    names[idx] = "mount";
                    attribs[idx++] = "" + pet_lib.isMountPcd(self);
                    if (hasObjVar(self, "mount_type"))
                    {
                        int mountType = getIntObjVar(self, "mount_type");
                        if (mountType == 0)
                        {
                            names[idx] = "walkspeed";
                            attribs[idx++] = "" + fltBaseWalkSpeed;
                            names[idx] = "runspeed";
                            attribs[idx++] = "" + fltBurstRunSpeed;
                        }
                        else if (mountType == 1)
                        {
                            names[idx] = "glide_speed";
                            attribs[idx++] = "" + fltBaseWalkSpeed;
                            names[idx] = "fly_speed";
                            attribs[idx++] = "" + fltBurstRunSpeed;
                        }
                        else 
                        {
                            names[idx] = "walkspeed";
                            attribs[idx++] = "" + fltBaseWalkSpeed;
                            names[idx] = "runspeed";
                            attribs[idx++] = "" + fltBurstRunSpeed;
                        }
                    }
                    else 
                    {
                        names[idx] = "walkspeed";
                        attribs[idx++] = "" + fltBaseWalkSpeed;
                        names[idx] = "runspeed";
                        attribs[idx++] = "" + fltBurstRunSpeed;
                    }
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isSameFaction(obj_id petControlDevice) throws InterruptedException
    {
        obj_id datapad = getContainedBy(petControlDevice);
        if (!isIdValid(datapad))
        {
            return false;
        }
        obj_id master = getContainedBy(datapad);
        if (!isIdValid(master))
        {
            return false;
        }
        String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
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
        if (pvpGetType(master) == PVPTYPE_NEUTRAL)
        {
            sendSystemMessage(master, new string_id(pet_lib.MENU_FILE, "not_declared"));
            return false;
        }
        String masterFaction = factions.getFaction(master);
        if (masterFaction == null || !masterFaction.equals(petFaction))
        {
            sendSystemMessageTestingOnly(master, "masterFaction is " + masterFaction + " pet is " + petFaction);
            sendSystemMessage(master, new string_id(pet_lib.MENU_FILE, "not_declared"));
            return false;
        }
        return true;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id newMaster = getContainedBy(destContainer);
        if (!isPlayer(newMaster))
        {
            sendSystemMessage(transferer, new string_id("pet/pet_menu", "cant_transfer_bad_container"));
            return SCRIPT_OVERRIDE;
        }
        if (isIdValid(transferer))
        {
            obj_id currentContainer = getContainedBy(self);
            if (isIdValid(currentContainer))
            {
                obj_id currentContOwner = getContainedBy(currentContainer);
                if (isIdValid(currentContOwner))
                {
                    if (hasScript(currentContOwner, "ai.pet_control_device"))
                    {
                        obj_id yourPad = utils.getPlayerDatapad(transferer);
                        if (destContainer == yourPad)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        if (!hasScript(newMaster, "ai.pet_master"))
        {
            attachScript(newMaster, "ai.pet_master");
        }
        int petType = getIntObjVar(self, "ai.pet.type");
        if (pet_lib.hasMaxStoredPetsOfType(newMaster, petType))
        {
            sendSystemMessage(newMaster, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
            sendSystemMessage(transferer, new string_id("pet/pet_menu", "targ_too_many_stored"));
            return SCRIPT_OVERRIDE;
        }
        if (!canBeTransferred(self, newMaster, transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int getLevelFromPetControlDevice(obj_id petControlDevice) throws InterruptedException
    {
        int growthStage = 10;
        if (hasObjVar(petControlDevice, "ai.petAdvance.growthStage"))
        {
            growthStage = getIntObjVar(petControlDevice, "ai.petAdvance.growthStage");
        }
        String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
        if (creatureName == null)
        {
            return 1;
        }
        int maxDiff = create.calcCreatureLevel(creatureName);
        if (hasObjVar(petControlDevice, "creature_attribs.level"))
        {
            maxDiff = getIntObjVar(petControlDevice, "creature_attribs.level");
        }
        if (maxDiff < 1)
        {
            maxDiff = 1;
        }
        return maxDiff;
    }
    public int handlePetDeathblow(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "lastDeathBlow"))
        {
            int lastDeathBlow = utils.getIntScriptVar(self, "lastDeathBlow");
            int nextDeathBlow = lastDeathBlow + 300;
            if (nextDeathBlow > getGameTime())
            {
                return SCRIPT_CONTINUE;
            }
        }
        utils.setScriptVar(self, "lastDeathBlow", getGameTime());
        obj_id killer = params.getObjId("killer");
        if (!isPlayer(killer))
        {
            int vitality = getIntObjVar(self, "pet.vitality");
            vitality += 2;
            if (vitality > pet_lib.MAX_VITALITY_LOSS)
            {
                vitality = pet_lib.MAX_VITALITY_LOSS;
            }
            setObjVar(self, "pet.vitality", vitality);
        }
        return SCRIPT_CONTINUE;
    }
    public void setTimeStored(obj_id PCD) throws InterruptedException
    {
        setObjVar(PCD, "pet.timeStored", getGameTime());
    }
    public int handleFlagDeadCreature(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "pet.isDead", true);
        return SCRIPT_CONTINUE;
    }
    public boolean petIsDead(obj_id petControlDevice, obj_id player, int petType) throws InterruptedException
    {
        if (hasObjVar(petControlDevice, "pet.isDead"))
        {
            if (petType == pet_lib.PET_TYPE_NON_AGGRO || petType == pet_lib.PET_TYPE_AGGRO || petType == pet_lib.PET_TYPE_DROID)
            {
                removeObjVar(petControlDevice, "pet.isDead");
                return false;
            }
            sendSystemMessage(player, new string_id("pet/pet_menu", "dead_pet"));
            return true;
        }
        return false;
    }
    public boolean canBeTransferred(obj_id petControlDevice, obj_id newMaster, obj_id transferer) throws InterruptedException
    {
        String creatureName = getStringObjVar(petControlDevice, "pet.creatureName");
        if (creatureName == null || creatureName.equals(""))
        {
            return false;
        }
        if (isVetRewardPet(creatureName))
        {
            return true;
        }
        int niche = dataTableGetInt(pet_lib.CREATURE_TABLE, creatureName, "niche");
        if (niche == NICHE_NPC || niche == NICHE_VEHICLE)
        {
            sendSystemMessage(newMaster, new string_id("pet/pet_menu", "bad_type"));
            sendSystemMessage(transferer, new string_id("pet/pet_menu", "bad_type"));
            return false;
        }
        int tameLevelSkillMod = pet_lib.getMaxTameLevel(transferer);
        int petType = getIntObjVar(petControlDevice, "ai.pet.type");
        int level = create.calcCreatureLevel(creatureName);
        if (level > tameLevelSkillMod)
        {
            return true;
        }
        if (petType == pet_lib.PET_TYPE_NON_AGGRO && level <= pet_lib.MAX_NONCH_PET_LEVEL)
        {
            return true;
        }
        if (!hasObjVar(petControlDevice, "ai.pet.command"))
        {
            sendSystemMessage(newMaster, new string_id("pet/pet_menu", "no_xfer_command"));
            sendSystemMessage(transferer, new string_id("pet/pet_menu", "no_xfer_command"));
            return false;
        }
        String xferCommand = getStringObjVar(petControlDevice, "ai.pet.command." + pet_lib.COMMAND_TRANSFER);
        if (xferCommand == null || xferCommand.equals(""))
        {
            sendSystemMessage(newMaster, new string_id("pet/pet_menu", "no_xfer_command"));
            sendSystemMessage(transferer, new string_id("pet/pet_menu", "no_xfer_command"));
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean validatePetStats(obj_id pcd, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(pcd, "pet.crafted"))
        {
            return true;
        }
        if (hasObjVar(pcd, "ai.pet.trainedMount"))
        {
            return true;
        }
        final int MAX_HAM_VALUE = 20000;
        final int MAX_HIT_VALUE = 500;
        final int MAX_DAM_VALUE = 1500;
        if (getIntObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]) > MAX_HAM_VALUE || getIntObjVar(pcd, "creature_attribs.toHitChance") > MAX_HIT_VALUE || getIntObjVar(pcd, "creature_attribs.defenseValue") > MAX_HIT_VALUE || getIntObjVar(pcd, "creature_attribs.minDamage") > MAX_DAM_VALUE || getIntObjVar(pcd, "creature_attribs.maxDamage") > MAX_DAM_VALUE)
        {
            sendSystemMessage(player, pet_lib.SID_INVALID_CRAFTED_PET);
            return false;
        }
        if (!bio_engineer.validatePcdLevel(pcd, player))
        {
            return false;
        }
        return true;
    }
    public int handleValidatePet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id currentPet = callable.getCDCallable(self);
        obj_id calledPet = params.getObjId("calledPet");
        if (!isIdValid(currentPet))
        {
            if (isIdValid(calledPet))
            {
                if (calledPet.isLoaded())
                {
                    destroyObject(calledPet);
                }
                else 
                {
                    messageTo(calledPet, "handlePackRequest", null, 1, false);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(calledPet))
        {
            return SCRIPT_CONTINUE;
        }
        int petType = pet_lib.getPetType(calledPet, self);
        if (pet_lib.countPetsOfType(self, petType) > pet_lib.getMaxPets(self, petType))
        {
            messageTo(calledPet, "handlePackRequest", null, 1, false);
        }
        if (calledPet == currentPet)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(calledPet))
        {
            if (calledPet.isLoaded())
            {
                destroyObject(calledPet);
            }
            else 
            {
                messageTo(calledPet, "handlePackRequest", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int removeGallop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPet = callable.getCDCallable(self);
        utils.removeScriptVar(self, "mount.intGalloping");
        obj_id objDataPad = getContainedBy(self);
        obj_id objRider = getContainedBy(objDataPad);
        if (!isIdNull(objRider) && !isIdNull(objPet))
        {
            string_id strSpam = new string_id("combat_effects", "gallop_stop");
            LOG("pet", "sending System Message of " + strSpam + " to " + objRider);
            sendSystemMessage(objRider, strSpam);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        int intState = getState(objRider, STATE_COMBAT);
        if (intState > 0)
        {
            pet_lib.setMountCombatMoveSpeed(objRider, objPet);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            pet_lib.setMountedMovementRate(objRider, objPet);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeGallopTired(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "mount.intTired");
        obj_id objDataPad = getContainedBy(self);
        obj_id objRider = getContainedBy(objDataPad);
        if (!isIdNull(objRider))
        {
            string_id strSpam = new string_id("combat_effects", "mount_not_tired");
            sendSystemMessage(objRider, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public void listAllCommands(obj_id petControlDevice, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(petControlDevice, "ai.pet.command"))
        {
            return;
        }
        Vector commandList = new Vector();
        commandList.setSize(0);
        commandList = utils.addElement(commandList, pet_lib.createLearnCommandListHeader(new string_id("obj_attr_n", "pet_command")));
        Vector abilityList = new Vector();
        abilityList.setSize(0);
        abilityList = utils.addElement(abilityList, pet_lib.createLearnCommandListHeader(new string_id("obj_attr_n", "pet_ability")));
        obj_var_list petCommandList = getObjVarList(petControlDevice, "ai.pet.command");
        if (petCommandList != null && petCommandList.getNumItems() > 0)
        {
            int numItems = petCommandList.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = petCommandList.getObjVar(i);
                String command = ov.getName();
                int commandNum = Integer.parseInt(command);
                if (commandNum >= pet_lib.COMMAND_FOLLOW && commandNum < pet_lib.NUM_COMMANDS)
                {
                    String display = pet_lib.createLearnCommandListEntry(new string_id("obj_attr_n", "pet_command_" + command), " -  " + ov.getStringData());
                    commandList = utils.addElement(commandList, display);
                }
                else 
                {
                    String display = pet_lib.createLearnCommandListEntry(new string_id("obj_attr_n", pet_lib.getPetAbilityFromCrc(commandNum)), " -  " + ov.getStringData());
                    abilityList = utils.addElement(abilityList, display);
                }
            }
            Vector displayList = utils.concatArrays(commandList, abilityList);
            sui.listbox(player, player, "", displayList);
        }
    }
    public int handleStatUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        int[] mainAttribs = params.getIntArray("attribs");
        setObjVar(self, "ai.pet.currentStats", mainAttribs);
        int timeStored = params.getInt("time");
        setObjVar(self, "pet.timeStored", timeStored);
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveCurrentPet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id sendingPetId = params.getObjId(MESSAGE_PET_ID);
        if (!isIdValid(sendingPetId))
        {
            if (debug)
            {
                LOG("pcd-debug", "pet_control_device.handleRemoveCurrentPet(): vcd id=[" + self + "]: sending pet's ID is invalid, ignoring");
            }
            return SCRIPT_CONTINUE;
        }
        if (callable.hasCDCallable(self))
        {
            sendDirtyObjectMenuNotification(self);
            obj_id controlledPetId = callable.getCDCallable(self);
            if (controlledPetId == sendingPetId)
            {
                callable.setCDCallable(self, null);
            }
            else 
            {
                LOG("pcd", "pet_control_device.handleRemoveCurrentPet(): vcd id=[" + self + "]: ignoring request from pet id=[" + sendingPetId + "]: PCD thinks it is controlling a different pet, id=[" + controlledPetId + "]");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePetFixDialog(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            if (revert != null && !revert.equals(""))
            {
                bio_engineer.adjustBrokenPetLevel(self, player);
            }
            else 
            {
                bio_engineer.adjustBrokenPetStats(self, player);
            }
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            sendSystemMessage(player, pet_lib.SID_INVALID_CRAFTED_PET);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isOnMaintRun(obj_id pet) throws InterruptedException
    {
        if (hasObjVar(pet, "module_data.maint_run_time"))
        {
            int curTime = getGameTime();
            int runTime = getIntObjVar(pet, "module_data.maint_run_time");
            if (curTime < runTime)
            {
                return true;
            }
            removeObjVar(pet, "module_data.maint_run_time");
        }
        return false;
    }
    public boolean isVetRewardPet(String creatureName) throws InterruptedException
    {
        return creatureName.equals("ep3_mount_varactyl");
    }
    public int respawnDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("master");
        obj_id currentPet = callable.getCDCallable(self);
        if (!isIdValid(currentPet))
        {
            if (callable.hasCDCallable(self))
            {
                if (currentPet.isLoaded())
                {
                    destroyObject(currentPet);
                    callable.setCDCallable(self, null);
                }
                else 
                {
                    messageTo(currentPet, "handlePackRequest", null, 1, false);
                }
            }
            if (!isSpaceScene() || getState(player, STATE_SHIP_INTERIOR) == 1)
            {
                pet_lib.createPetFromData(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConvertPetSui(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (incubator.convertPcdIntoPetItem(player, self))
        {
            CustomerServiceLog("BeastPetConversion: ", "Player (" + player + ") has converted Old Pcd (" + self + ")" + " we are now going to store its pet and destroy the pcd.");
            obj_id currentPet = callable.getCDCallable(self);
            if (isValidId(currentPet) && exists(currentPet))
            {
                pet_lib.storePet(currentPet);
            }
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            CustomerServiceLog("BeastPetConversion: ", "Player (" + player + ") tried to convert Old Pcd (" + self + ")." + " Conversion was not a success. New Item was NOT created and pcd was NOT destroyed.");
            return SCRIPT_CONTINUE;
        }
    }
    public int dancingDroidSoundObjectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "dancingDroid.soundObject");
        return SCRIPT_CONTINUE;
    }
}
