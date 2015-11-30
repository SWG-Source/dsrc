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

public class x34_deed extends script.base_script
{
    public x34_deed()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
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
        if (item == menu_info_types.VEHICLE_GENERATE)
        {
            if (vehicle.hasMaxStoredVehicles(player))
            {
                sendSystemMessage(player, vehicle.SID_SYS_HAS_MAX_VEHICLE);
                return SCRIPT_CONTINUE;
            }
            debugServerConsoleMsg(self, "+++ VEHICLE_DEED . onObjectMenuSelect +++ Attempting to 'tame' the vehicular prototype deed object.");
            obj_id pet = createCraftedCreatureDevice(player, self);
            if (isIdValid(pet))
            {
                CustomerServiceLog("vehicle_deed", "vehicle deed used: deed=" + self + " pcd=" + pet + " player=" + player + "(" + getPlayerName(player) + ")");
                destroyObject(self);
            }
            else 
            {
                debugServerConsoleMsg(self, "+++ VEHICLE_DEED . onObjectMenuSelect +++ Failed to make PCD for Vehicle Prototype.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id createCraftedCreatureDevice(obj_id player, obj_id deed) throws InterruptedException
    {
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ Entered PCD/Pet creation function.");
        obj_id datapad = utils.getPlayerDatapad(player);
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ player datapad objide detected as: #" + datapad);
        if (!isIdValid(datapad))
        {
            return null;
        }
        String controlTemplate = "object/intangible/vehicle/landspeeder_x34_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        if (!isIdValid(petControlDevice))
        {
            sendSystemMessageTestingOnly(player, "Failed to create pet control device for vehicle");
            return null;
        }
        else 
        {
            setObjVar(petControlDevice, "pet.crafted", true);
            setObjVar(petControlDevice, "vehicle.template", "landspeeder_x34");
            attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
            debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ successfully created PCD object for vehicular prototype. Obj_id is #" + petControlDevice);
        }
        obj_id pet = create.object("object/mobile/vehicle/landspeeder_x34.iff", getLocation(player));
        if (!isIdValid(pet))
        {
            debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ OBJ_ID Validity check on Vehicular_Prototype pet object came back as NOT VALID");
            return petControlDevice;
        }
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ SUCCESSFULLY created the vehicular_prototype pet object. Prepping to attach objvars to it");
        callable.setCallableCD(pet, petControlDevice);
        setObjVar(pet, "vehicularTestBed", 1);
        if (couldPetBeMadeMountable(pet) != 0)
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
        callable.setCallableLinks(player, petControlDevice, pet);
        return petControlDevice;
    }
}
