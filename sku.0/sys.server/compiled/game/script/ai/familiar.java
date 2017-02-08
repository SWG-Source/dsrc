package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.callable;
import script.library.pet_lib;
import script.library.utils;

public class familiar extends script.base_script
{
    public familiar()
    {
    }
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final String MESSAGE_PET_ID = "petId";
    public static final String MENU_FILE = "pet/pet_menu";
    public static final boolean debug = false;
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static final int FAMILIAR_TRICK_1 = 1;
    public static final int FAMILIAR_TRICK_2 = 2;
    public static final int FAMILIAR_TRICK_3 = 3;
    public static final int FAMILIAR_TRICK_4 = 4;
    public static final int FAMILIAR_TRICK_5 = 5;
    public static final int FAMILIAR_TRICK_6 = 6;
    public void doFamiliarTrick(obj_id pet, int trickNum) throws InterruptedException
    {
        obj_id petControl = callable.getCallableCD(pet);
        String creatureName = getStringObjVar(petControl, "pet.creatureName");
        if (creatureName.startsWith("loveday_romance_seeker_familiar"))
        {
            return;
        }
        if (creatureName.equals("vr_mouse_droid"))
        {
            trickNum = trickNum + 2;
        }
        else if (creatureName.equals("loveday_ewok_cupid_familiar"))
        {
            trickNum = trickNum + 4;
        }
        obj_id master = getMaster(pet);
        switch (trickNum)
        {
            case FAMILIAR_TRICK_1:
            doAnimationAction(pet, "trick_1");
            break;
            case FAMILIAR_TRICK_2:
            doAnimationAction(pet, "trick_2");
            break;
            case FAMILIAR_TRICK_3:
            if (isIdValid(master))
            {
                playClientEffectObj(master, "clienteffect/droid_effect_fog_machine.cef", pet, "");
                playClientEffectObj(master, "clienteffect/droid_vocal/mouse_vocalize.cef", pet, "");
            }
            break;
            case FAMILIAR_TRICK_4:
            if (isIdValid(master))
            {
                playClientEffectObj(master, "clienteffect/npe_droid_static.cef", pet, "");
                playClientEffectObj(master, "sound/item_electronics_break.snd", pet, "");
            }
            break;
            case FAMILIAR_TRICK_5:
            String[] anims_01 = 
            {
                "stretch",
                "bow",
                "wave_item"
            };
            int chance_01 = rand(0, anims_01.length - 1);
            doAnimationAction(pet, anims_01[chance_01]);
            break;
            case FAMILIAR_TRICK_6:
            String[] anims_02 = 
            {
                "startle",
                "goodbye",
                "shrug"
            };
            int chance_02 = rand(0, anims_02.length - 1);
            doAnimationAction(pet, anims_02[chance_02]);
            break;
        }
        return;
    }
    public int doFamiliarTrick(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pet = params.getObjId("pet");
        int trickNum = params.getInt("trickNum");
        boolean heartBeat = params.getBoolean("heartBeat");
        if (!isIdValid(pet))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id petControl = callable.getCallableCD(pet);
        if (!isIdValid(petControl))
        {
            return SCRIPT_CONTINUE;
        }
        String creatureName = getStringObjVar(petControl, "pet.creatureName");
        if (creatureName == null || creatureName.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (creatureName.startsWith("loveday_romance_seeker_familiar") || hasObjVar(petControl, "familiar_noTricks"))
        {
            return SCRIPT_CONTINUE;
        }
        doFamiliarTrick(pet, trickNum);
        if (heartBeat)
        {
            int whichTrick = rand(1, 2);
            dictionary trickData = new dictionary();
            switch (whichTrick)
            {
                case 1:
                trickData.put("trickNum", FAMILIAR_TRICK_1);
                break;
                case 2:
                trickData.put("trickNum", FAMILIAR_TRICK_2);
                break;
            }
            trickData.put("pet", pet);
            int trickDelay = rand(300, 480);
            trickData.put("heartBeat", true);
            messageTo(pet, "doFamiliarTrick", trickData, trickDelay, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "alreadyTamed", true);
        messageTo(self, "handleSetupPet", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public boolean applyBuff(obj_id pet) throws InterruptedException
    {
        obj_id master = getMaster(pet);
        if (!isIdValid(master))
        {
            destroyObject(pet);
            return false;
        }
        String nameBuff = "";
        int levelUpBuff = 1;
        obj_id petControl = callable.getCallableCD(pet);
        String creatureName = getStringObjVar(petControl, "pet.creatureName");
        int row = dataTableSearchColumnForString(creatureName, "familiar_name", "datatables/familiar/familiar_buff.iff");
        if (row < 0)
        {
            return false;
        }
        nameBuff = dataTableGetString("datatables/familiar/familiar_buff.iff", row, "buff_name");
        levelUpBuff = dataTableGetInt("datatables/familiar/familiar_buff.iff", row, "level_up");
        if (nameBuff.length() > 0)
        {
            if (levelUpBuff == 1)
            {
                int level = getLevel(master) / 10;
                if (level < 1)
                {
                    level = 1;
                }
                if (level > 8)
                {
                    level = 8;
                }
                nameBuff += level;
            }
            buff.applyBuff(master, nameBuff);
        }
        return true;
    }
    public void removePetBuff(obj_id master) throws InterruptedException
    {
        int numbuff = buff.getBuffOnTargetFromGroup(master, "vr_familiar");
        buff.applyBuff(master, numbuff, 3600f);
        return;
    }
    public void repackPet(obj_id master, obj_id pet) throws InterruptedException
    {
        if (isIdValid(master))
        {
            removePetBuff(master);
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        if(!isIdValid(petControlDevice)){
            return;
        }
        utils.setScriptVar(pet, "stored", true);
        dictionary messageData = new dictionary();
        messageData.put(MESSAGE_PET_ID, pet);
        if (petControlDevice.isLoaded() && exists(petControlDevice))
        {
            sendDirtyObjectMenuNotification(petControlDevice);
        }
        if (destroyObject(pet))
        {
            messageTo(petControlDevice, "handleRemoveCurrentPet", messageData, 1, false);
        }
        return;
    }
    public int handleSetupPet(obj_id self, dictionary params) throws InterruptedException
    {
        applySkillStatisticModifier(self, "slope_move", 100);
        if(!pet_lib.hasMaster(self)){
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        setOwner(utils.getInventoryContainer(self), master);
        setObjVar(master, "familiar", self);
        pet_lib.addToPetList(master, self);
        if (!pet_lib.findMaster(self))
        {
            messageTo(self, "handleLostMaster", null, 1800, false);
        }
        final boolean promiscuous = true;
        final float alertRadius = 128.0f;
        createTriggerVolume(ai_lib.ALERT_VOLUME_NAME, alertRadius, promiscuous);
        messageTo(self, "cleanupCheck", null, 300, false);
        applyBuff(self);
        ai_lib.aiFollow(self, master, 3f, 6f);
        int whichTrick = rand(1, 2);
        dictionary trickData = new dictionary();
        switch (whichTrick)
        {
            case 1:
            trickData.put("trickNum", FAMILIAR_TRICK_1);
            break;
            case 2:
            trickData.put("trickNum", FAMILIAR_TRICK_2);
            break;
        }
        trickData.put("pet", self);
        trickData.put("heartBeat", true);
        int trickDelay = rand(300, 480);
        messageTo(self, "doFamiliarTrick", trickData, trickDelay, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (!pet_lib.findMaster(self))
        {
            destroyObject(self);
        }
        messageTo(self, "cleanupCheck", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME) && breacher == getMaster(self))
        {
            repackPet(breacher, self);
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
        if (!isIdValid(master))
        {
            removePetBuff(master);
        }
        pet_lib.removeFromPetList(self);
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
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            removeObjVar(master, "familiar");
            removePetBuff(master);
        }
        if (hasObjVar(self, "pet.controlDestroyed") || !isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        if (isIdValid(master))
        {
            pet_lib.removeFromPetList(self);
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
    public int handlePackRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            removePetBuff(master);
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        utils.setScriptVar(self, "stored", true);
        dictionary messageData = new dictionary();
        messageData.put(MESSAGE_PET_ID, self);
        if (petControlDevice.isLoaded() && exists(petControlDevice))
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
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
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
        mi.addRootMenu(menu_info_types.PET_STORE, new string_id(MENU_FILE, "menu_store"));
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
        if (item == menu_info_types.PET_STORE)
        {
            pet_lib.storePet(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
