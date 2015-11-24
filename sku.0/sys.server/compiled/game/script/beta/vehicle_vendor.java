package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.pet_lib;
import script.library.utils;
import script.library.chat;
import script.library.create;

public class vehicle_vendor extends script.base_script
{
    public vehicle_vendor()
    {
    }
    public static final String VEHICLE_VENDOR_CONVO = "beta/vehicle_vendor";
    public static final int EXPIRATION_TIME = 60 * 60 * 24 * 16;
    public static final int VEHICLE_EXPIRATION_TIME = 60 * 60 * 24 * 16;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        persistObject(self);
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(self, "ai.diction", "townperson");
        setObjVar(self, "vehicle_vendor.create", (getGameTime() + EXPIRATION_TIME));
        setName(self, "Travelling Vehicle Merchant");
        messageTo(self, "expireAndCleanup", null, EXPIRATION_TIME, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int expirationTime = getIntObjVar(self, "vehicle_vendor.create");
        if (getGameTime() > expirationTime)
        {
            messageTo(self, "expireAndCleanup", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int expireAndCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        chat.setGoodMood(self);
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(VEHICLE_VENDOR_CONVO, "start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(VEHICLE_VENDOR_CONVO, "yes");
        response[1] = new string_id(VEHICLE_VENDOR_CONVO, "no");
        npcStartConversation(speaker, self, VEHICLE_VENDOR_CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(VEHICLE_VENDOR_CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("yes"))
        {
            if (getBooleanObjVar(player, "hasTestVehicle") == true)
            {
                string_id message = new string_id(VEHICLE_VENDOR_CONVO, "toomany");
                npcSpeak(player, message);
                npcEndConversation(player);
            }
            npcRemoveConversationResponse(player, new string_id(VEHICLE_VENDOR_CONVO, "no"));
            string_id message = new string_id(VEHICLE_VENDOR_CONVO, "choose");
            npcSpeak(player, message);
            string_id[] responses = new string_id[3];
            responses[0] = new string_id(VEHICLE_VENDOR_CONVO, "x34");
            responses[1] = new string_id(VEHICLE_VENDOR_CONVO, "bike");
            responses[2] = new string_id(VEHICLE_VENDOR_CONVO, "swoop");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("no"))
        {
            npcRemoveConversationResponse(player, new string_id(VEHICLE_VENDOR_CONVO, "yes"));
            string_id message = new string_id(VEHICLE_VENDOR_CONVO, "goodbye");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        String mountType = response.getAsciiId();
        String diction = "";
        if (mountType.equals("x34"))
        {
            mountType = "landspeeder_x34";
        }
        else if (mountType.equals("bike"))
        {
            mountType = "speederbike";
        }
        else if (mountType.equals("swoop"))
        {
            mountType = "speederbike_swoop";
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        obj_id mount = createCraftedCreatureDevice(player, mountType);
        if (isIdValid(mount))
        {
            setObjVar(player, "hasTestVehicle", true);
        }
        string_id message = new string_id(VEHICLE_VENDOR_CONVO, "goodbye2");
        npcSpeak(player, message);
        npcEndConversation(player);
        return SCRIPT_CONTINUE;
    }
    public obj_id createCraftedCreatureDevice(obj_id player, String vehicle) throws InterruptedException
    {
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ Entered PCD/Pet creation function.");
        obj_id datapad = utils.getPlayerDatapad(player);
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ player datapad objide detected as: #" + datapad);
        if (!isIdValid(datapad))
        {
            return null;
        }
        String controlTemplate = "object/intangible/vehicle/" + vehicle + "_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        attachScript(petControlDevice, "beta.free_vehicle");
        setObjVar(petControlDevice, "hasOwner", player);
        if (vehicle.equals("landspeeder_x34"))
        {
            setObjVar(petControlDevice, "vehicle_attribs.object_ref", "x34");
        }
        if (vehicle.equals("speederbike"))
        {
            setObjVar(petControlDevice, "vehicle_attribs.object_ref", "spdrbike");
        }
        if (vehicle.equals("speederbike_swoop"))
        {
            setObjVar(petControlDevice, "vehicle_attribs.object_ref", "swoop");
        }
        if (!isIdValid(petControlDevice))
        {
            sendSystemMessageTestingOnly(player, "Failed to create pet control device for vehicle");
            return null;
        }
        else 
        {
            setObjVar(petControlDevice, "pet.crafted", true);
            setObjVar(petControlDevice, "vehicle.template", vehicle);
            setObjVar(petControlDevice, "vehicle_vendor.create", (getGameTime() + VEHICLE_EXPIRATION_TIME));
            messageTo(petControlDevice, "expireAndCleanup", null, VEHICLE_EXPIRATION_TIME, true);
            attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
            debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ successfully created PCD object for vehicular prototype. Obj_id is #" + petControlDevice);
        }
        obj_id pet = create.object("object/mobile/vehicle/" + vehicle + ".iff", getLocation(player));
        if (!isIdValid(pet))
        {
            debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ OBJ_ID Validity check on Vehicular_Prototype pet object came back as NOT VALID");
            return petControlDevice;
        }
        debugServerConsoleMsg(player, "+++ VEHICLE_DEED . createCraftedCreatureDevice +++ SUCCESSFULLY created the vehicular_prototype pet object. Prepping to attach objvars to it");
        setObjVar(pet, "vehicularTestBed", 1);
        callable.setCallableLinks(player, petControlDevice, pet);
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
        return petControlDevice;
    }
}
