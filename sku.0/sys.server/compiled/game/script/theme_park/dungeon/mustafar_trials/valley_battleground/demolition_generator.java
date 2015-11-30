package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.trial;

public class demolition_generator extends script.base_script
{
    public demolition_generator()
    {
    }
    public static final String DATA_TABLE = "datatables/combat/npc_landmines.iff";
    public static final String STF = "npc_landmines";
    public static final string_id PLACE_CHARGE = new string_id(STF, "place_charge");
    public static final string_id PICK_UP = new string_id("mustafar/valley_battlefield", "pick_up_demo_pack");
    public static final String DET_TEMPLATE = "object/tangible/dungeon/mustafar/valley_battlefield/demo_detonator.iff";
    public static final String DET_GENERATOR = "object/tangible/dungeon/mustafar/valley_battlefield/demo_pack.iff";
    public static final boolean LOGGING = false;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.verifyLocationBasedDestructionAnchor(self, 500))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!hasBeenPickedUp(self))
        {
            int root_menu = mi.addRootMenu(menu_info_types.ITEM_USE, PICK_UP);
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, PLACE_CHARGE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!hasBeenPickedUp(self))
        {
            regenerateInPlayerInventory(player, self);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            placeDetonationCharge(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void regenerateInPlayerInventory(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id detPack = createObjectInInventoryAllowOverload(DET_GENERATOR, player);
        if (!isIdValid(detPack))
        {
            doLogging("regenerateInPlayerInventory", "Failed to create controller in player inventory");
            return;
        }
        utils.verifyLocationBasedDestructionAnchor(detPack, getLocation(player), 500);
        utils.setScriptVar(detPack, "currentMineCount", utils.getIntScriptVar(self, "currentMineCount"));
        destroyObject(self);
    }
    public boolean hasBeenPickedUp(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "inWorld"))
        {
            return false;
        }
        return true;
    }
    public void placeDetonationCharge(obj_id player) throws InterruptedException
    {
        location chargePoint = getLocation(player);
        obj_id charge = createCharge(chargePoint, player);
        if (!isIdValid(charge))
        {
            doLogging("placeDetonationCharge", "Charge Obj_Id was invalid, destroying detonator");
            return;
        }
        generateDetonationDevice(player, charge);
        return;
    }
    public obj_id createCharge(location spawnPoint, obj_id player) throws InterruptedException
    {
        String chargeLevel = "demolitionCharge_" + getCommandoModifyLevel(player);
        String chargeTemplate = dataTableGetString(DATA_TABLE, chargeLevel, "mineTemplate");
        obj_id charge = createObject(chargeTemplate, spawnPoint);
        if (!isIdValid(charge))
        {
            doLogging("createCharge", "Failed to create charge, returning null");
            return obj_id.NULL_ID;
        }
        removeAllObjVars(charge);
        detachAllScripts(charge);
        utils.setScriptVar(charge, "mineType", chargeLevel);
        utils.setScriptVar(charge, trial.TEMP_OBJECT, true);
        attachScript(charge, "theme_park.dungeon.mustafar_trials.valley_battleground.demolition_pack");
        return charge;
    }
    public int getCommandoModifyLevel(obj_id player) throws InterruptedException
    {
        int level = 0;
        String[] commandoLevel = 
        {
            "class_commando_phase1_novice",
            "class_commando_phase2_novice",
            "class_commando_phase3_novice",
            "class_commando_phase4_novice",
            "class_commando_phase4_master"
        };
        for (int i = 0; i < commandoLevel.length; i++)
        {
            if (hasSkill(player, commandoLevel[i]))
            {
                level += 1;
            }
        }
        return level;
    }
    public void generateDetonationDevice(obj_id player, obj_id charge) throws InterruptedException
    {
        obj_id detonator = createObjectInInventoryAllowOverload(DET_TEMPLATE, player);
        if (!isIdValid(detonator))
        {
            doLogging("generateDetonationDevice", "Failed to generate detonator, returning");
            return;
        }
        attachScript(detonator, "theme_park.dungeon.mustafar_trials.valley_battleground.demolition_detonator");
        utils.setScriptVar(detonator, "chargeId", charge);
        location here = getLocation(player);
        utils.verifyLocationBasedDestructionAnchor(detonator, here, 500);
        decrimentMineCount(getSelf());
    }
    public int getCurrentMineCount(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "currentMineCount"))
        {
            return utils.getIntScriptVar(self, "currentMineCount");
        }
        else 
        {
            return 0;
        }
    }
    public int decrimentMineCount(obj_id self) throws InterruptedException
    {
        int current = getCurrentMineCount(self);
        current = current - 1;
        if (current <= 0)
        {
            destroyObject(self);
            doLogging("decrimentMineCount", "Pack is out of mines, destroying self");
            return -1;
        }
        utils.setScriptVar(self, "currentMineCount", current);
        return getCurrentMineCount(self);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/demolition_generator/" + section, message);
        }
    }
}
