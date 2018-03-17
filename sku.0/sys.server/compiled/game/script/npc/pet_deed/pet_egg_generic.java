package script.npc.pet_deed;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.groundquests;
import script.library.money;
import script.library.create;
import script.library.pet_lib;
import script.library.utils;

public class pet_egg_generic extends script.base_script
{
    public pet_egg_generic()
    {
    }
    public static final String STF = "pet/pet_menu";
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
            String creatureName = getStringObjVar(self, "creature_attribs.type");
            String templateFromCreaturesTab = utils.dataTableGetString(create.CREATURE_TABLE, creatureName, "template");
            if (templateFromCreaturesTab == null || templateFromCreaturesTab.equals(""))
            {
                LOG("create", "CREATE PET EGG:Returned a NULL from Creatures Tab looking for " + creatureName);
                return SCRIPT_CONTINUE;
            }
            String controlTemplate = "object/intangible/pet/" + templateFromCreaturesTab;
            obj_id datapad = utils.getPlayerDatapad(player);
            obj_id petControlDevice = createObject(controlTemplate, datapad, "");
            string_id nameId = new string_id(create.CREATURE_NAME_FILE, creatureName);
            int petType = pet_lib.PET_TYPE_NON_AGGRO;
            setObjVar(petControlDevice, "pet.creatureName", creatureName);
            attachScript(petControlDevice, "ai.pet_control_device");
            setName(petControlDevice, nameId);
            setObjVar(petControlDevice, "ai.pet.type", petType);
            setObjVar(petControlDevice, "ai.petAdvance.growthStage", 1);
            int level = create.calcCreatureLevel(creatureName);
            if (hasObjVar(self, "creature_attribs.level"))
            {
                level = getIntObjVar(self, "creature_attribs.level");
            }
            setObjVar(petControlDevice, "ai.petAbility.toBeEarned", pet_lib.getMaxAbilitySlots(level));
            setObjVar(petControlDevice, "ai.petAbility.available", 0);
            sendSystemMessage(player, EGG_HATCHED);
            pet_lib.createPetFromData(petControlDevice);
            obj_id pet = callable.getCDCallable(petControlDevice);
            if (!exists(pet) || !isIdValid(pet))
            {
                return SCRIPT_CONTINUE;
            }
            dictionary petCommandList = utils.getDictionaryScriptVar(pet, "ai.pet.commandList");
            if (petCommandList == null)
            {
                petCommandList = new dictionary();
            }
            setObjVar(petControlDevice, "ai.pet.command." + pet_lib.COMMAND_FOLLOW, "follow");
            petCommandList.put("follow", pet_lib.COMMAND_FOLLOW);
            setObjVar(petControlDevice, "ai.pet.command." + pet_lib.COMMAND_STAY, "stay");
            petCommandList.put("stay", pet_lib.COMMAND_STAY);
            setObjVar(petControlDevice, "ai.pet.command." + pet_lib.COMMAND_ATTACK, "attack");
            petCommandList.put("attack", pet_lib.COMMAND_ATTACK);
            setObjVar(petControlDevice, "ai.pet.command." + pet_lib.COMMAND_TRANSFER, "transfer");
            petCommandList.put("transfer", pet_lib.COMMAND_TRANSFER);
            utils.setScriptVar(pet, "ai.pet.commandList", petCommandList);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
