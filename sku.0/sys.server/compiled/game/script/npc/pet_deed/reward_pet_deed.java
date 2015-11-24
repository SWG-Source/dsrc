package script.npc.pet_deed;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.pet_lib;
import script.library.bio_engineer;
import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.hue;

public class reward_pet_deed extends script.base_script
{
    public reward_pet_deed()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (!hasObjVar(self, "creatureName"))
            {
                return SCRIPT_CONTINUE;
            }
            String creatureType = getStringObjVar(self, "creatureName");
            if (pet_lib.isFamiliarPetTypeByCreatureName(creatureType))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(MENU_FILE, "familiar_unpack"));
            }
            else 
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(MENU_FILE, "ep3_unpack"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1 && hasObjVar(self, "creatureName"))
        {
            if (!hasObjVar(self, "creatureName"))
            {
                sendSystemMessage(player, "Invalid pet deed data: No Creature Type found.", "");
                return SCRIPT_CONTINUE;
            }
            String creatureType = getStringObjVar(self, "creatureName");
            if (pet_lib.isFamiliarPetTypeByCreatureName(creatureType))
            {
                obj_id controlDevice = pet_lib.generatePetOfTypeFamiliar(creatureType, player);
                if (isIdValid(controlDevice))
                {
                    destroyObject(self);
                }
            }
            if (creatureType.equals("ep3_mount_varactyl"))
            {
                String controlTemplate = "object/intangible/pet/varactyl.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "ep3_mount_varactyl");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(varactyl)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                setObjVar(petControlDevice, "storageUnlimited", 1);
                destroyObject(self);
            }
            if (creatureType.equals("ep3_mount_bolotaur"))
            {
                String controlTemplate = "object/intangible/pet/bolotaur.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "ep3_mount_bolotaur");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(bolotaur)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                destroyObject(self);
            }
            if (creatureType.equals("ep3_mount_kashyyyk_bantha"))
            {
                String controlTemplate = "object/intangible/pet/kashyyyk_bantha.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "ep3_mount_kashyyyk_bantha");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(kashyyyk bantha)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                destroyObject(self);
            }
            if (creatureType.equals("carrion_spat"))
            {
                String controlTemplate = "object/intangible/pet/carrion_spat_hue.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "carrion_spat");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(Kartha's Cliff-Jumper)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                destroyObject(self);
            }
            if (creatureType.equals("som_lava_flea_preorder"))
            {
                String controlTemplate = "object/intangible/pet/lava_flea.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "som_lava_flea_preorder");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(Lava Flea)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                destroyObject(self);
            }
            if (creatureType.equals("walker_at_rt_camo"))
            {
                String controlTemplate = "object/intangible/vehicle/walker_at_rt_camo_pcd.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "walker_at_rt_camo");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(AT-RT)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                destroyObject(self);
            }
            if (creatureType.equals("tcg_armored_bantha"))
            {
                String controlTemplate = "object/intangible/pet/bantha_armored.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "tcg_armored_bantha");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(Armored Bantha)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                setObjVar(petControlDevice, "noTrade", 1);
                setObjVar(petControlDevice, "noStuff", 1);
                destroyObject(self);
            }
            if (creatureType.equals("tcg_peko_peko_mount"))
            {
                String controlTemplate = "object/intangible/pet/tcg_peko_peko_mount.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "tcg_peko_peko_mount");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(Peko Peko)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                setObjVar(petControlDevice, "noTrade", 1);
                setObjVar(petControlDevice, "noStuff", 1);
                destroyObject(self);
            }
            if (creatureType.equals("tcg_gualaar"))
            {
                String controlTemplate = "object/intangible/pet/gualaar_pcd.iff";
                obj_id datapad = utils.getPlayerDatapad(player);
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                setObjVar(petControlDevice, "pet.creatureName", "tcg_gualaar");
                attachScript(petControlDevice, "ai.pet_control_device");
                setName(petControlDevice, "(Gualaar)");
                int petType = pet_lib.PET_TYPE_MOUNT;
                setObjVar(petControlDevice, "ai.pet.type", petType);
                setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                setObjVar(petControlDevice, "noTrade", 1);
                setObjVar(petControlDevice, "noStuff", 1);
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
