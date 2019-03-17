package script.player.skill;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

public class taming extends script.base_script
{
    public taming()
    {
    }
    public static final string_id SHAPECHANGE = new string_id("spam", "not_while_shapechanged");
    public int cmdTellPet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!callable.hasAnyCallable(self) || params == null || params.length() > 30)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] callableList = callable.getCallables(self);
        for (obj_id obj_id : callableList) {
            if (getDistance(obj_id, self) < 200.0f && !ai_lib.aiIsDead(obj_id) && !beast_lib.isBeast(obj_id)) {
                dictionary parms = new dictionary();
                parms.put("text", params);
                parms.put("master", self);
                messageTo(obj_id, "handleTellPet", parms, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int tellPetAttack(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (callable.hasAnyCallable(self))
        {
            obj_id[] callableList = callable.getCallables(self);
            if (callableList.length > 0 && callableList != null)
            {
                for (obj_id obj_id : callableList) {
                    if (getDistance(obj_id, self) < 200.0f && !ai_lib.aiIsDead(obj_id) && !beast_lib.isBeast(obj_id)) {
                        pet_lib.doCommandNum(obj_id, pet_lib.COMMAND_ATTACK, self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int tellPetFollow(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (callable.hasAnyCallable(self))
        {
            obj_id[] callableList = callable.getCallables(self);
            if (callableList.length > 0 && callableList != null)
            {
                for (obj_id obj_id : callableList) {
                    if (getDistance(obj_id, self) < 200.0f && !ai_lib.aiIsDead(obj_id) && !beast_lib.isBeast(obj_id)) {
                        pet_lib.doCommandNum(obj_id, pet_lib.COMMAND_FOLLOW, self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int mount(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("mount", "mount(): enter self: " + self + " target: " + target);
        debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ just entered commandHandler mount()");
        location here = getLocation(self);
        location term = getLocation(target);
        float dist = getDistance(here, term);
        if (!isIdValid(self) || !isIdValid(target))
        {
            debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ either isIdValid(self) or isIdValid(target) failed");
            LOG("mount", "mount(): player or mount id is not valid");
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(self, "instance_exiting"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!pet_lib.isPet(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || isIncapacitated(self) || dist > 5.0)
        {
            return SCRIPT_CONTINUE;
        }
        int shapechange = buff.getBuffOnTargetFromGroup(self, "shapechange");
        obj_id playerCurrentMount = getMountId(self);
        if (isIdValid(playerCurrentMount) && exists(playerCurrentMount))
        {
            String mountName = getTemplateName(playerCurrentMount);
            if (vehicle.isJetPackVehicle(playerCurrentMount))
            {
                if (shapechange != 0)
                {
                    utils.dismountRiderJetpackCheck(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (shapechange != 0)
        {
            sendSystemMessage(self, SHAPECHANGE);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(playerCurrentMount))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (self != getMaster(target))
        {
            if (!doesMountHaveRoom(target))
            {
                return SCRIPT_CONTINUE;
            }
            else if (!vehicle.mountPermissionCheck(target, self, true))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (pet_lib.isInRestrictedScene(self))
        {
            sendSystemMessage(self, pet_lib.SID_SYS_MOUNT_RESTRICTED_SCENE);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(target, "vehicularTestBed"))
        {
            if (!hasScript(target, "ai.pet"))
            {
                debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ ai.pet isn't present on the target pet");
                sendSystemMessage(self, pet_lib.SID_SYS_CANT_MOUNT);
                return SCRIPT_CONTINUE;
            }
        }
        if (getMountsEnabled() && pet_lib.canMount(target, self))
        {
            debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ pet_lib.canMount(target, self) returned TRUE");
            if (vehicle.isVehicle(target) && isDisabled(target))
            {
                sendSystemMessage(self, pet_lib.SID_SYS_CANT_MOUNT_VEHICLE_DISABLED);
                debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ disabled vehicle");
                return SCRIPT_CONTINUE;
            }
            queueClear(self);
            if (!mountCreature(self, target))
            {
                debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ mountCreature (self,target) returned FALSE");
                sendSystemMessage(self, pet_lib.SID_SYS_CANT_MOUNT);
            }
            else 
            {
                pet_lib.setMountedMovementRate(self, target);
                debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ mountCreature (self,target) returned TRUE");
                messageTo(self, "applyMountBuff", null, 1, false);
                if (!hasObjVar(target, "vehicularTestBed"))
                {
                    if (!hasScript(target, "ai.mount_combat"))
                    {
                        debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ mounted pet doesn't have ai.mount_combat script. adding it now");
                        attachScript(target, "ai.mount_combat");
                    }
                }
            }
        }
        else 
        {
            debugServerConsoleMsg(self, "+++ PLAYER.skill.taming +++ commandHandler mount() +++ pet_lib.canMount(target, self) returned FALSE");
        }
        return SCRIPT_CONTINUE;
    }
    public int battlefieldMount(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id mount = target;
        if (!isIdValid(self) || !isIdValid(mount))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(self, "instance_exiting"))
        {
            return SCRIPT_CONTINUE;
        }
        int shapechange = buff.getBuffOnTargetFromGroup(self, "shapechange");
        if (shapechange != 0)
        {
            sendSystemMessage(self, SHAPECHANGE);
            return SCRIPT_CONTINUE;
        }
        obj_id playerCurrentMount = getMountId(self);
        if (isIdValid(playerCurrentMount))
        {
            return SCRIPT_CONTINUE;
        }
        if (!doesMountHaveRoom(mount))
        {
            return SCRIPT_CONTINUE;
        }
        queueClear(self);
        if (!mountCreature(self, mount))
        {
            debugSpeakMsg(self, "Epic fail");
        }
        else 
        {
            obj_id pilot = getRiderId(mount);
            if (!isIdValid(getMaster(mount)) || (pilot != getMaster(mount) && pilot == getObjectInSlot(mount, "rider")))
            {
                setMaster(mount, pilot);
            }
            pet_lib.setMountedMovementRate(self, mount);
            setState(self, STATE_RIDING_MOUNT, true);
            messageTo(self, "applyMountBuff", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int dismount(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "+++ player.skill.taming.commandHandler dismount +++ just entered the dismount command handler");
        obj_id playerCurrentMount = getMountId(self);
        if (!isIdValid(playerCurrentMount))
        {
            return SCRIPT_CONTINUE;
        }
        if (vehicle.isBattlefieldVehicle(playerCurrentMount))
        {
            return SCRIPT_CONTINUE;
        }
        boolean dismountSuccess = pet_lib.doDismountNow(self);
        if (!dismountSuccess)
        {
            LOG("mounts-bug", "taming.script.commandHandler dismount(): pet_lib.doDismountNow() failed for rider [" + self + "]");
        }
        int vehicleBuff = buff.getBuffOnTargetFromGroup(self, "vehicle");
        if (vehicleBuff != 0)
        {
            buff.removeBuff(self, vehicleBuff);
        }
        String mountName = getTemplateName(playerCurrentMount);
        if (vehicle.isJetPackVehicle(playerCurrentMount))
        {
            string_id jetDismount = new string_id("pet/pet_menu", "jetpack_dismount");
            sendSystemMessage(self, jetDismount);
            obj_id petControlDevice = callable.getCallableCD(playerCurrentMount);
            vehicle.storeVehicle(petControlDevice, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int battlefieldDismount(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id mount = getMountId(self);
        vehicle.setHoverHeight(mount, 0.5f);
        dismountCreature(self);
        setState(self, STATE_RIDING_MOUNT, false);
        obj_id master = getMaster(mount);
        if (isIdValid(master) && master == self)
        {
            obj_id[] passengerList = utils.getAllRidersInVehicle(self, mount);
            if (passengerList != null && passengerList.length > 0)
            {
                for (obj_id obj_id : passengerList) {
                    if (isIdValid(obj_id)) {
                        dismountCreature(obj_id);
                    }
                }
            }
            setMaster(mount, null);
        }
        else 
        {
            if (isIdValid(master))
            {
                buff.removeBuff(mount, "vehicle_passenger");
            }
        }
        int vehicleBuff = buff.getBuffOnTargetFromGroup(self, "vehicle");
        if (vehicleBuff != 0)
        {
            buff.removeBuff(self, vehicleBuff);
        }
        int vehicleHasBuff = buff.getBuffOnTargetFromGroup(mount, "vehicle");
        if (vehicleHasBuff != 0)
        {
            buff.removeBuff(mount, vehicleHasBuff);
        }
        return SCRIPT_CONTINUE;
    }
    public int dismountandstore(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(self) || !getMountsEnabled())
        {
            return SCRIPT_CONTINUE;
        }
        boolean dismountResult = pet_lib.doDismountNow(self);
        if (!dismountResult)
        {
            LOG("mounts-bug", "dismountandstore called on rider [" + self + "] but pet_lib.doDismountNow() returned false; aborting store but essential state is already destroyed.");
            sendSystemMessage(self, pet_lib.SID_SYS_CANT_DISMOUNT);
            return SCRIPT_CONTINUE;
        }
        obj_id playerCurrentMount = getMountId(self);
        if (isIdValid(playerCurrentMount))
        {
            obj_id petControlDevice = callable.getCallableCD(playerCurrentMount);
            setObjVar(petControlDevice, "pet.timeStored", getGameTime());
            destroyObject(playerCurrentMount);
        }
        else 
        {
            sendSystemMessage(self, pet_lib.SID_SYS_CANT_DISMOUNT);
        }
        return SCRIPT_CONTINUE;
    }
}
