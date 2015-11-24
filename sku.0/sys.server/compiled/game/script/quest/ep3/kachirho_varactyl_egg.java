package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.money;
import script.library.create;
import script.library.pet_lib;
import script.library.utils;

public class kachirho_varactyl_egg extends script.base_script
{
    public kachirho_varactyl_egg()
    {
    }
    public static final String STF = "ep3/sidequests";
    public static final string_id HATCH_EGG = new string_id(STF, "hatch_egg");
    public static final string_id EGG_HATCHED = new string_id(STF, "egg_hatched");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, HATCH_EGG);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String controlTemplate = "object/intangible/pet/varactyl.iff";
            obj_id datapad = utils.getPlayerDatapad(player);
            obj_id petControlDevice = createObject(controlTemplate, datapad, "");
            setObjVar(petControlDevice, "pet.creatureName", "ep3_qst_reward_varactyl");
            attachScript(petControlDevice, "ai.pet_control_device");
            setName(petControlDevice, "(varactyl)");
            int petType = pet_lib.PET_TYPE_AGGRO;
            setObjVar(petControlDevice, "ai.pet.type", petType);
            setObjVar(petControlDevice, "ai.petAdvance.growthStage", 1);
            int level = create.calcCreatureLevel("ep3_qst_reward_varactyl");
            setObjVar(petControlDevice, "ai.petAbility.toBeEarned", pet_lib.getMaxAbilitySlots(level));
            setObjVar(petControlDevice, "ai.petAbility.available", 0);
            sendSystemMessage(player, EGG_HATCHED);
            pet_lib.createPetFromData(petControlDevice);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
