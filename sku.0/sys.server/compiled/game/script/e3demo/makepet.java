package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.pet_lib;

public class makepet extends script.base_script
{
    public makepet()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createBantha(self);
        createLandSpeeder(self);
        messageTo(self, "handleDetachMakePet", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDetachMakePet(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "e3demo.makepet");
        return SCRIPT_CONTINUE;
    }
    public void createBantha(obj_id self) throws InterruptedException
    {
        String controlTemplate = "object/intangible/pet/bantha_hue.iff";
        obj_id datapad = utils.getPlayerDatapad(self);
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.creatureName", "bantha_e3");
        attachScript(petControlDevice, "ai.pet_control_device");
        setName(petControlDevice, "(bantha)");
        int petType = pet_lib.PET_TYPE_NON_AGGRO;
        setObjVar(petControlDevice, "ai.pet.type", petType);
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
    }
    public void createLandSpeeder(obj_id self) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(self);
        String controlTemplate = "object/intangible/vehicle/landspeeder_x34_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.crafted", true);
        setObjVar(petControlDevice, "vehicle_attribs.object_ref", "x34");
        attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        setName(petControlDevice, "(x34)");
    }
}
