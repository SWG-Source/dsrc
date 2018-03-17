package script.systems.vehicle_system;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.pet_lib;
import script.library.bio_engineer;
import script.library.utils;
import script.library.create;
import script.library.vehicle;
import script.library.ai_lib;

public class vehicle_deed extends script.base_script
{
    public vehicle_deed()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public static final string_id SID_NO_GROUND_VEHICLE_IN_SPACE = new string_id("space/space_interaction", "no_ground_vehicle_in_space");
    public static final string_id SID_NOT_BIO_LINKED = new string_id("spam", "vehicle_not_biolink");
    public static final string_id SID_FAILED_PCD_CREATION = new string_id("spam", "failed_pcd_creation");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            mi.addRootMenu(menu_info_types.VEHICLE_GENERATE, new string_id(MENU_FILE, "menu_generate"));
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
        if (ai_lib.aiIsDead(player) || ai_lib.isInCombat(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.VEHICLE_GENERATE)
        {
            obj_id bioLink = getBioLink(self);
            if (isIdValid(bioLink))
            {
                if (bioLink != player)
                {
                    sendSystemMessage(player, SID_NOT_BIO_LINKED);
                    return SCRIPT_CONTINUE;
                }
            }
            if (getLocomotion(player) == LOCOMOTION_RUNNING)
            {
                string_id noMove = new string_id("pet/pet_menu", "no_moving");
                sendSystemMessage(player, noMove);
                return SCRIPT_CONTINUE;
            }
            if (pet_lib.wasInCombat(player))
            {
                return SCRIPT_CONTINUE;
            }
            if (vehicle.hasMaxStoredVehicles(player))
            {
                sendSystemMessage(player, vehicle.SID_SYS_HAS_MAX_VEHICLE);
                return SCRIPT_CONTINUE;
            }
            debugServerConsoleMsg(self, "+++ VEHICLE_DEED . onObjectMenuSelect +++ Attempting to 'tame' the vehicular prototype deed object.");
            if (!vehicle.isInValidUnpackLocation(player))
            {
                sendSystemMessage(player, vehicle.SID_SYS_CANT_CALL_LOC);
                return SCRIPT_CONTINUE;
            }
            if (vehicle.isInRestrictedScene(player))
            {
                sendSystemMessage(player, pet_lib.SID_SYS_VEHICLE_RESTRICTED_SCENE);
                return SCRIPT_CONTINUE;
            }
            obj_id pet = createCraftedCreatureDevice(player, self);
            if (isIdValid(pet))
            {
                CustomerServiceLog("vehicle_deed", "vehicle deed used: deed=" + self + " pcd=" + pet + " player=" + player + "(" + getPlayerName(player) + ")");
                destroyObject(self);
            }
            else 
            {
                CustomerServiceLog("vehicle_deed", "Player could not create a valid vehicle with deed: " + self + " deed name: " + getName(self) + " player:" + player + "(" + getPlayerName(player) + ")");
                debugServerConsoleMsg(self, "+++ VEHICLE_DEED . onObjectMenuSelect +++ Failed to make PCD for Vehicle Prototype.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id createCraftedCreatureDevice(obj_id player, obj_id deed) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return null;
        }
        if (!isIdValid(deed) || !exists(deed))
        {
            return null;
        }
        if (!canManipulate(player, deed, true, true, 15, true))
        {
            return null;
        }
        if (!hasScript(player, "ai.pet_master"))
        {
            attachScript(player, "ai.pet_master");
        }
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ Entered PCD/Pet creation function.");
        String objectRef = getStringObjVar(deed, "vehicle_attribs.object_ref");
        if (objectRef == null || objectRef.equals(""))
        {
            CustomerServiceLog("vehicle_deed", "Player could not create a valid vehicle with deed: " + deed + " deed name: " + getName(deed) + " player:" + player + "(" + getPlayerName(player) + ")" + ") REASON: no vehicle deed objevar on the deed object.");
            return null;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ player datapad objId detected as: #" + datapad);
        if (!isIdValid(datapad))
        {
            return null;
        }
        String pcdTemplate = utils.dataTableGetString(create.VEHICLE_TABLE, objectRef, "PCD_TEMPLATE");
        if (pcdTemplate == null || pcdTemplate.equals(""))
        {
            CustomerServiceLog("vehicle_deed", "Player could not create a valid vehicle with deed: " + deed + " deed name: " + getName(deed) + " player:" + player + "(" + getPlayerName(player) + ")" + ") REASON: no vehicle PCD template found when looking up the vehicle tables.");
            return null;
        }
        obj_id petControlDevice = createObject(pcdTemplate, datapad, "");
        if (!isIdValid(petControlDevice))
        {
            sendSystemMessage(player, SID_FAILED_PCD_CREATION);
            CustomerServiceLog("vehicle_deed", "Player could not create a valid Player Control Device when using deed: " + deed + " deed name: " + getName(deed) + " player:" + player + "(" + getPlayerName(player) + ")");
            return null;
        }
        else 
        {
            setObjVar(petControlDevice, "pet.crafted", true);
            setObjVar(petControlDevice, "vehicle_attribs.object_ref", objectRef);
            if (hasObjVar(deed, "noTrade"))
            {
                setObjVar(petControlDevice, "noTrade", 1);
                attachScript(petControlDevice, "item.special.nomove");
            }
            attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
            debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ successfully created PCD object for vehicular prototype. Obj_id is #" + petControlDevice);
            newbieTutorialSetToolbarElement(player, -1, petControlDevice);
        }
        if (callable.hasAnyCallable(player))
        {
            callable.storeCallables(player);
        }
        String objectTemplate = utils.dataTableGetString(create.VEHICLE_TABLE, objectRef, "OBJECT_TEMPLATE");
        obj_id pet = create.object(objectTemplate, getLocation(player));
        initCraftedStats(petControlDevice, deed, pet);
        if (!isIdValid(pet))
        {
            debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ OBJ_ID Validity check on Vehicular_Prototype pet object came back as NOT VALID");
            return petControlDevice;
        }
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ SUCCESSFULLY created the vehicular_prototype pet object. Prepping to attach objvars to it");
        callable.setCallableCD(pet, petControlDevice);
        setObjVar(pet, "vehicularTestBed", 1);
        setObjVar(pet, "ai.pet.masterName", getEncodedName(player));
        if (!(couldPetBeMadeMountable(pet) == 0))
        {
            debugServerConsoleMsg(null, "+++ VEHICLE . onAttach +++ couldPetBeMadeMountable(pet) returned FALSE.");
            return pet;
        }
        if (!makePetMountable(pet))
        {
            debugServerConsoleMsg(null, "+++ VEHICLE . onAttach +++ makePetMountable(pet) returned FALSE.");
            return pet;
        }
        else 
        {
            setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        }
        messageTo(pet, "handleVehicleDecay", null, 100, false);
        vehicle.initializeVehicle(pet, player);
        dictionary web = new dictionary();
        web.put("player", player);
        messageTo(pet, "checkForJetpack", web, 1, false);
        if (vehicle.isJetPackVehicle(pet))
        {
            vehicle.setHoverHeight(pet, 4);
        }
        callable.setCallableLinks(player, petControlDevice, pet);
        return petControlDevice;
    }
    public void initCraftedStats(obj_id device, obj_id deed, obj_id pet) throws InterruptedException
    {
        debugServerConsoleMsg(null, "+++ VEHICLE_DEED . initCraftedStats +++ entered initCraftedStats while generating inital deed-derived vehicle.");
        int hitPoints = 1500;
        if (isIdValid(deed) && hasObjVar(deed, "hit_points"))
        {
            hitPoints = (int)getFloatObjVar(deed, "hit_points");
        }
        debugServerConsoleMsg(null, "+++ VEHICLE_DEED . initCraftedStats +++ Retrieved hit_points objvar value from deed object. Value is: " + hitPoints);
        if (hitPoints <= 1)
        {
            hitPoints = 1500;
        }
        if (isIdValid(device))
        {
            setObjVar(device, "attrib.max_hp", hitPoints);
            setObjVar(device, "attrib.hit_points", hitPoints);
        }
        if (isIdValid(pet))
        {
            setMaxHitpoints(pet, hitPoints);
            setHitpoints(pet, hitPoints);
        }
    }
}
