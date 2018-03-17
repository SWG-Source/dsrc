package script.systems.vehicle_system;

import script.*;
import script.library.*;

public class vehicle_base extends script.base_script
{
    public vehicle_base()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public static final String VCDPING_VEHICLE_SCRIPT_NAME = "systems.vehicle_system.vehicle_ping";
    public static final String MESSAGE_VEHICLE_ID = "vehicleId";
    public static final string_id SID_CITY_GARAGE_BANNED = new string_id("city/city", "garage_banned");
    public static final string_id SID_NO_GROUND_VEHICLE_IN_SPACE = new string_id("space/space_interaction", "no_ground_vehicle_in_space");
    public static final boolean debug = false;
    public int revertVehicleMod(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || !params.containsKey("type"))
        {
            return SCRIPT_CONTINUE;
        }
        int type = params.getInt("type");
        if (type == vehicle.MOD_TYPE_MAX_SPEED)
        {
            float oldSpeed = 0.0f;
            if (hasObjVar(self, vehicle.OBJVAR_MOD_MAX_SPEED_OLD))
            {
                oldSpeed = getFloatObjVar(self, vehicle.OBJVAR_MOD_MAX_SPEED_OLD);
                removeObjVar(self, vehicle.OBJVAR_MOD_MAX_SPEED_OLD);
            }
            if (hasObjVar(self, vehicle.OBJVAR_MOD_MAX_SPEED_DURATION))
            {
                removeObjVar(self, vehicle.OBJVAR_MOD_MAX_SPEED_DURATION);
            }
            if (oldSpeed > 0.0f)
            {
                vehicle.setMaximumSpeed(self, oldSpeed);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAttributeAttained(self, attrib.VEHICLE);
        if (!hasScript(self, VCDPING_VEHICLE_SCRIPT_NAME))
        {
            if (debug)
            {
                LOG("vcdping-debug", "vehicle_base.OnInitialize(): attaching script [" + VCDPING_VEHICLE_SCRIPT_NAME + "] to vehicle id=[" + self + "]");
            }
            attachScript(self, VCDPING_VEHICLE_SCRIPT_NAME);
        }
        sendDestroyUnattendedVehicleSignal(self);
        return SCRIPT_CONTINUE;
    }
    public int checkForJetpack(obj_id self, dictionary params) throws InterruptedException
    {
        String creature_name = getTemplateName(self);
        obj_id player = params.getObjId("player");
        boolean storeJetpack = false;
        if (vehicle.isJetPackVehicle(self))
        {
            if (getMountsEnabled())
            {
                debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ getMountsEneabled returnted TRUE");
                if (pet_lib.canMount(self, player))
                {
                    debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ pet_lib.canMount(self,player) returned TRUE");
                    queueCommand(player, (-536363215), self, creature_name, COMMAND_PRIORITY_FRONT);
                    debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ just attempted to Enqueue MOUNT command");
                }
                else 
                {
                    storeJetpack = true;
                }
            }
            else 
            {
                storeJetpack = true;
            }
        }
        if (storeJetpack)
        {
            string_id jetpackStoredMsg = new string_id("pet/pet_menu", "jetpack_stored");
            sendSystemMessage(player, jetpackStoredMsg);
            obj_id petControlDevice = callable.getCallableCD(self);
            vehicle.storeVehicle(petControlDevice, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVehicleDecay(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player) || self == null || self == obj_id.NULL_ID || !isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        boolean isMountedOn = isMountedOnCreatureQueried(self, player);
        boolean isOwnedByPlayer = player == getMaster(self);
        sendDestroyUnattendedVehicleSignal(self);
        if (!isOwnedByPlayer && !isMountedOn)
        {
            LOG("special_sign", "!isOwnedByPlayer && !isMountedOn");
            if (!doesMountHaveRoom(self))
            {
                LOG("special_sign", "!doesMountHaveRoom(self)");
                return SCRIPT_CONTINUE;
            }
            else if (!vehicle.mountPermissionCheck(self, player, false))
            {
                LOG("special_sign", "vehicle.mountPermissionCheck ");
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            LOG("special_sign", "isOwnedByPlayer || isMountedOn");
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        String vehicle_name = getTemplateName(self);
        if (getMountsEnabled())
        {
            if (!vehicle.isJetPackVehicle(self))
            {
                if (pet_lib.canMount(self, player) || isMountedOn)
                {
                    mi.addRootMenu(menu_info_types.SERVER_VEHICLE_ENTER_EXIT, new string_id(MENU_FILE, "menu_enter_exit"));
                }
            }
        }
        if (!isOwnedByPlayer)
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(player) || pet_lib.wasInCombatRecently(self, player, false))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, battlefield.VAR_CONSTRUCTED) && !ai_lib.isInCombat(player))
        {
            mi.addRootMenu(menu_info_types.PET_STORE, new string_id(MENU_FILE, "menu_store"));
        }
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "inRepairZone") && (!isDisabled(self) || (vehicle.canRepairDisabledVehicle(petControlDevice) && isDisabled(self))))
        {
            obj_id garage = utils.getObjIdScriptVar(self, "inRepairZone");
            if (isIdValid(garage) && exists(garage) && garage.isLoaded())
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(MENU_FILE, "menu_repair_vehicle"));
            }
        }
        else if (isDisabled(self) && hasBarcRepairKit(player))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("barc_repair", "refurbish_barc"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_GROUND_VEHICLE_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(player) || pet_lib.wasInCombatRecently(self, player, false))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.SERVER_VEHICLE_ENTER_EXIT && pet_lib.isPet(self) && pet_lib.hasMaster(self) && player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isAiDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        String creature_name = getName(self);
        obj_id petControlDevice = callable.getCallableCD(self);
        if (item == menu_info_types.PET_STORE)
        {
            if (!hasObjVar(self, battlefield.VAR_CONSTRUCTED))
            {
                vehicle.storeVehicle(petControlDevice, player);
            }
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_VEHICLE_ENTER_EXIT)
        {
            debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ SERVER_VEHICLE_ENTER_EXIT menu object selected");
            if (getMountsEnabled())
            {
                debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ getMountsEneabled returnted TRUE");
                if (isMountedOnCreatureQueried(self, player))
                {
                    queueCommand(player, (117012717), self, creature_name, COMMAND_PRIORITY_FRONT);
                }
                else if (pet_lib.canMount(self, player))
                {
                    debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ pet_lib.canMount(self,player) returned TRUE");
                    queueCommand(player, (-536363215), self, creature_name, COMMAND_PRIORITY_FRONT);
                    debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ just attempted to Enqueue MOUNT command");
                }
            }
            else 
            {
                debugServerConsoleMsg(player, "+++ pet . onObjectMenuSelect +++ getMountsEneabled returnted FALSE");
            }
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            if (ai_lib.isInCombat(player))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(self, "inRepairZone") && (!isDisabled(self) || (vehicle.canRepairDisabledVehicle(petControlDevice) && isDisabled(self))))
            {
                int city_id = getCityAtLocation(getLocation(self), 0);
                if ((city_id > 0) && city.isCityBanned(player, city_id))
                {
                    sendSystemMessage(player, SID_CITY_GARAGE_BANNED);
                    return SCRIPT_CONTINUE;
                }
                vehicle.repairVehicle(player, self);
                sendDirtyObjectMenuNotification(self);
            }
            else if (isDisabled(self) && hasBarcRepairKit(player))
            {
                vehicle.restoreVehicle(player, self);
                sendDirtyObjectMenuNotification(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isMountedOnCreatureQueried(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id playerCurrentMount = getMountId(player);
        if (!isIdValid(playerCurrentMount))
        {
            return false;
        }
        if (playerCurrentMount != pet)
        {
            return false;
        }
        return true;
    }
    public boolean canTrainAsMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ isIdValid failed for (pet)");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ isIdValid failed for (player)");
            return false;
        }
        if (!(couldPetBeMadeMountable(pet) == MSC_CREATURE_MOUNTABLE))
        {
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ couldPetBeMadeMountable (pet) returned something other than MSC_CREATURE_MOUNTABLE ");
            debugServerConsoleMsg(player, "+++ PET_LIB . canTrainAsMount +++ couldPetBeMadeMountable (pet) returned " + couldPetBeMadeMountable(pet));
            return false;
        }
        return true;
    }
    public boolean trainMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(player))
        {
            return false;
        }
        if (!canTrainAsMount(pet, player))
        {
            return false;
        }
        if (!makePetAMount(pet, player))
        {
            debugServerConsoleMsg(player, "+++ VEHICLE . onAttach +++ makePetAMount(self,player) returned FALSE");
            return false;
        }
        else 
        {
            debugServerConsoleMsg(player, "+++ VEHICLE . onAttach +++ makePetAMount(self,player) returned TRUE. YEAH!");
        }
        return true;
    }
    public boolean makePetAMount(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet) || !isIdValid(player))
        {
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if (!(couldPetBeMadeMountable(pet) == 0))
        {
            debugServerConsoleMsg(player, "+++ VEHICLE . onAttach +++ couldPetBeMadeMountable(pet) returned FALSE.");
            return false;
        }
        if (!makePetMountable(pet))
        {
            debugServerConsoleMsg(player, "+++ VEHICLE . onAttach +++ makePetMountable(pet) returned FALSE.");
            return false;
        }
        else 
        {
            setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        }
        return true;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ai.pet.masterName"))
        {
            names[idx] = "owner";
            attribs[idx] = getStringObjVar(self, "ai.pet.masterName");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id owner = getMaster(self);
        if (isIdValid(owner))
        {
            obj_id vcd = callable.getCallableCD(self);
            if (isIdValid(vcd))
            {
                String killerDesc = null;
                if (isIdValid(killer))
                {
                    String killerName = getPlayerName(killer);
                    if (killerName != null && killerName.length() > 0)
                    {
                        killerDesc = "player " + killer + "(" + killerName + ")";
                    }
                    else 
                    {
                        killerDesc = "npc " + killer + "(" + getName(killer) + ")";
                    }
                }
                else if (getIntObjVar(vcd, "attrib.hit_points") > 0)
                {
                    killerDesc = "decay";
                }
                if (killerDesc != null)
                {
                    CustomerServiceLog("vehicle", "vehicle template:" + getTemplateName(self) + " vcd:" + vcd + " owner:" + owner + "(" + getName(owner) + ") disabled by " + killerDesc);
                }
            }
            sendSystemMessage(owner, pet_lib.SID_SYS_VEHICLE_DISABLED);
            LOG("vehicle_base", "It is destroyed");
            obj_id rider = getRiderId(self);
            if (isIdValid(rider))
            {
                utils.dismountRiderJetpackCheck(rider);
            }
            if (ai_lib.isInCombat(self))
            {
                vehicle.storeVehicle(vcd, rider, false);
            }
            else 
            {
                messageTo(self, "handleDisabledPackRequest", null, 120, false);
            }
            int vehicleBuff = buff.getBuffOnTargetFromGroup(rider, "vehicle");
            if (vehicleBuff != 0)
            {
                buff.removeBuff(rider, vehicleBuff);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "pet.controlDestroyed"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        boolean isFactionPet = (ai_lib.isNpc(self) || ai_lib.aiGetNiche(self) == NICHE_VEHICLE || ai_lib.isAndroid(self));
        obj_id petControlDevice = callable.getCallableCD(self);
        if (hasObjVar(self, battlefield.VAR_CONSTRUCTED) || (isFactionPet && ai_lib.aiIsDead(self)))
        {
            if (isIdValid(petControlDevice))
            {
                messageTo(petControlDevice, "handleFlagDeadCreature", null, 0, false);
            }
        }
        if (isIdValid(petControlDevice))
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
    public int handleDisabledPackRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDisabled(self))
        {
            messageTo(self, "handlePackRequest", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePackRequest(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(null, "+++ vehicle_base.messageHandler handlePackRequest +++ entered HANDLEPACKREQUEST message handler");
        obj_id rider = getRiderId(self);
        if (isIdValid(rider))
        {
            boolean dismountSuccess = pet_lib.doDismountNow(rider);
            if (!dismountSuccess)
            {
                LOG("mounts-bug", "vehicle_base.messageHandler handlePackRequest(): creature [" + self + "], rider [" + rider + "] failed to dismount, aborting pack request.  This mount/vehicle probably is in an invalid state now.");
                return SCRIPT_CONTINUE;
            }
        }
        debugServerConsoleMsg(null, "+++ vehicle_base.messageHandler handlePackRequest +++ destroying the vehicle now");
        obj_id vehicleControlDevice = callable.getCallableCD(self);
        vehicle.saveVehicleInfo(vehicleControlDevice, self);
        utils.setScriptVar(self, "stored", true);
        dictionary messageData = new dictionary();
        messageData.put(MESSAGE_VEHICLE_ID, self);
        sendDirtyObjectMenuNotification(vehicleControlDevice);
        if (destroyObject(self))
        {
            messageTo(vehicleControlDevice, "handleRemoveCurrentVehicle", messageData, 1, false);
        }
        else 
        {
            debugServerConsoleMsg(null, "+++ vehicle_base.messageHandler handlePackRequest +++ WARNINGWARNING - FAILED TO DESTROY SELF");
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyNow(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "pet.controlDestroyed", true);
        CustomerServiceLog("vehicle_bug", "vehicle_base - destroyNow::Recieved signal to destroy with ID: " + params.getInt("signalId"));
        destroyObject(self);
        return SCRIPT_OVERRIDE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        utils.setScriptVar(self, "pet.combatEnded", getGameTime());
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        utils.setScriptVar(self, "pet.combatEnded", getGameTime());
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
            messageTo(tool, "customizationSuccess", params, 0f, false);
        }
        else 
        {
            messageTo(tool, "customizationFailed", params, 0f, false);
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
    public boolean hasBarcRepairKit(obj_id player) throws InterruptedException
    {
        obj_id tool = utils.getItemPlayerHasByTemplate(player, "object/tangible/item/ep3/barc_repair_tool.iff");
        return (isIdValid(tool));
    }
    public void sendDestroyUnattendedVehicleSignal(obj_id vehicle) throws InterruptedException
    {
        trial.bumpSession(vehicle);
        messageTo(vehicle, "handleDestroyUnattended", trial.getSessionDict(vehicle), 600, false);
    }
    public int handleDestroyUnattended(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        if (vehicle.isBattlefieldVehicle(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id rider = getRiderId(self);
        if (isIdValid(rider))
        {
            sendDestroyUnattendedVehicleSignal(self);
            return SCRIPT_CONTINUE;
        }
        obj_id vcd = callable.getCallableCD(self);
        if (!isIdValid(vcd))
        {
            params.put("signalId", 0);
            messageTo(self, "destroyNow", params, 0, false);
            return SCRIPT_CONTINUE;
        }
        obj_id currentVehicle = callable.getCDCallable(vcd);
        if (!isIdValid(currentVehicle) || currentVehicle != self)
        {
            params.put("signalId", 1);
            messageTo(self, "destroyNow", params, 0, false);
            CustomerServiceLog("vehicle_bug", "vehicle_base - handleDestroyUnattended::Validating current vehicle(" + currentVehicle + ") vs self(" + self + "): " + params.getInt("signalId"));
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getContainingPlayer(vcd);
        vehicle.storeVehicle(vcd, player);
        return SCRIPT_CONTINUE;
    }
}
