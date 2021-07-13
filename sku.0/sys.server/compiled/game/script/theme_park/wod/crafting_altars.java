package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class crafting_altars extends script.base_script
{
    public crafting_altars()
    {
    }
    public static final string_id schematicGranted = new string_id("theme_park/wod", "schematic_learned_altar");
    public static final string_id noAltarItem = new string_id("theme_park/wod", "no_altar_item");
    public static final string_id noRepair = new string_id("theme_park/wod", "no_repair");
    public static final string_id notValidItem = new string_id("theme_park/wod", "not_a_valid_key");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, "altar_number"))
            {
                int altarNumber = getIntObjVar(self, "altar_number");
                if (groundquests.isTaskActive(player, "wod_ns_repair_alter_0" + altarNumber, "investigateAltar") || groundquests.isTaskActive(player, "wod_sm_repair_alter_0" + altarNumber, "investigateAltar"))
                {
                    groundquests.sendSignal(player, "activateAltar");
                    if (!hasSchematic(player, "object/draft_schematic/item/theme_park/wod_crafting_alter_" + altarNumber + ".iff"))
                    {
                        grantSchematic(player, "object/draft_schematic/item/theme_park/wod_crafting_alter_" + altarNumber + ".iff");
                        sendSystemMessage(player, schematicGranted);
                    }
                }
                else if (groundquests.isTaskActive(player, "wod_ns_repair_alter_0" + altarNumber, "repairAltar") || groundquests.isTaskActive(player, "wod_sm_repair_alter_0" + altarNumber, "repairAltar"))
                {
                    obj_id itemWeGot = utils.getItemPlayerHasByTemplate(player, "object/tangible/theme_park/wod/wod_crafting_alter_key_" + altarNumber + ".iff");
                    if (isValidId(itemWeGot))
                    {
                        checkItem(player, itemWeGot, altarNumber);
                    }
                    else 
                    {
                        sendSystemMessage(player, noAltarItem);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        int altarNumber = getIntObjVar(self, "altar_number");
        if (groundquests.isTaskActive(transferer, "wod_ns_repair_alter_0" + altarNumber, "repairAltar") || groundquests.isTaskActive(transferer, "wod_sm_repair_alter_0" + altarNumber, "repairAltar"))
        {
            if (getTemplateName(item) == "object/tangible/theme_park/wod/wod_crafting_alter_key_" + altarNumber + ".iff")
            {
                checkItem(transferer, item, altarNumber);
            }
            else 
            {
                sendSystemMessage(transferer, notValidItem);
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public void checkItem(obj_id player, obj_id itemToCheck, int altarNumber) throws InterruptedException
    {
        int value1 = Math.round(getFloatObjVar(itemToCheck, "crafting_components.wod_crafting_1"));
        int value2 = Math.round(getFloatObjVar(itemToCheck, "crafting_components.wod_crafting_2"));
        int value3 = Math.round(getFloatObjVar(itemToCheck, "crafting_components.wod_crafting_3"));
        int value4 = 0;
        boolean hasValue4 = hasObjVar(itemToCheck, "crafting_components.wod_crafting_4");
        if (hasValue4)
        {
            value4 = Math.round(getFloatObjVar(itemToCheck, "crafting_components.wod_crafting_4"));
        }
        int toCompare1 = 3;
        int toCompare2 = 3;
        int toCompare3 = 6;
        int toCompare4 = 0;
        if (altarNumber == 2)
        {
            toCompare1 = 3;
            toCompare2 = 3;
            toCompare3 = 3;
            toCompare4 = 3;
        }
        else if (altarNumber == 3)
        {
            toCompare1 = 3;
            toCompare2 = 6;
            toCompare3 = 3;
            toCompare4 = 0;
        }
        else if (altarNumber == 4)
        {
            toCompare1 = 6;
            toCompare2 = 2;
            toCompare3 = 2;
            toCompare4 = 2;
        }
        debugSpeakMsg(player, "values: 1;" + value1 + ";" + toCompare1 + ", 2;" + value2 + ";" + toCompare2 + ", 3;" + value3 + ";" + toCompare3 + ", 4;" + value4 + ";" + toCompare4);
        if (value1 >= toCompare1 && value2 >= toCompare2 && value3 >= toCompare3 && value4 >= toCompare4)
        {
            groundquests.sendSignal(player, "repairedAltar");
            destroyObject(itemToCheck);
        }
        else 
        {
            sendSystemMessage(player, noRepair);
        }
    }
}
