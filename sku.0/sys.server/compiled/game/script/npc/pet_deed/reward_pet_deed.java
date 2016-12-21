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
    public obj_id generatePet(obj_id deed, obj_id player, String controlTemplate, String creatureType, String datapadItemName, boolean noTrade, boolean noStuff) throws InterruptedException{
        obj_id datapad = utils.getPlayerDatapad(player);
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.creatureName", creatureType);
        attachScript(petControlDevice, "ai.pet_control_device");
        setName(petControlDevice, datapadItemName);
        int petType = pet_lib.PET_TYPE_MOUNT;
        setObjVar(petControlDevice, "ai.pet.type", petType);
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        if(noTrade) setObjVar(petControlDevice, "noTrade", 1);
        if(noStuff) setObjVar(petControlDevice, "noStuff", 1);
        destroyObject(deed);
        return petControlDevice;
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
            else {
                obj_id petControlDevice;
                switch (creatureType) {
                    case "ep3_mount_varactyl":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/varactyl.iff", creatureType, "(varactyl)", false, false);
                        setObjVar(petControlDevice, "storageUnlimited", 1);
                        break;
                    case "ep3_mount_bolotaur":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/bolotaur.iff", creatureType, "(bolotaur)", false, false);
                        break;
                    case "ep3_mount_kashyyyk_bantha":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/kashyyyk_bantha.iff", creatureType, "(kashyyyk bantha)", false, false);
                        break;
                    case "carrion_spat":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/carrion_spat_hue.iff", creatureType, "(Kartha's Cliff-Jumper)", false, false);
                        break;
                    case "som_lava_flea_preorder":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/lava_flea.iff", creatureType, "(Lava Flea)", false, false);
                        break;
                    case "walker_at_rt_camo":
                        petControlDevice = generatePet(self, player, "object/intangible/vehicle/walker_at_rt_camo_pcd.iff", creatureType, "(AT-RT)", false, false);
                        break;
                    case "tcg_armored_bantha":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/bantha_armored.iff", creatureType, "(Armored Bantha)", true, true);
                        break;
                    case "tcg_peko_peko_mount":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/tcg_peko_peko_mount.iff", creatureType, "(Peko Peko)", true, true);
                        break;
                    case "tcg_gualaar":
                        petControlDevice = generatePet(self, player, "object/intangible/pet/gualaar_pcd.iff", creatureType, "(Gualaar)", true, true);
                        break;
                    default:
                        break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
