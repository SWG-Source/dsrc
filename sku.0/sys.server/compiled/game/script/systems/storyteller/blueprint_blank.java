package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.storyteller;
import script.library.trial;
import script.library.static_item;

public class blueprint_blank extends script.base_script
{
    public blueprint_blank()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("storyteller", "blueprint_record_data"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_SWIMMING) == 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_while_swimming"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        location yourLoc = getLocation(player);
        if (isIdValid(yourLoc.cell))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_inside"));
            return SCRIPT_CONTINUE;
        }
        obj_id whatAmIStandingOn = getStandingOn(player);
        if (isIdValid(whatAmIStandingOn) && player_structure.isBuilding(whatAmIStandingOn))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_on_buildings"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!createBlueprint(self, player))
            {
                sendSystemMessage(player, new string_id("storyteller", "blueprint_failed_to_create"));
            }
            else 
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasBlueprintData(obj_id blueprint) throws InterruptedException
    {
        return utils.hasStringBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
    }
    public boolean createBlueprint(obj_id blueprint, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id[] storytellerObjects = getAllObjectsWithObjVar(getLocation(player), storyteller.BLUEPRINT_DEFAULT_RADIUS, "storytellerid");
        if (storytellerObjects == null || storytellerObjects.length < 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_objects_found"));
            return false;
        }
        String[] blueprintObjects = storyteller.recordBlueprintData(blueprint, storytellerObjects, player);
        if (blueprintObjects == null || blueprintObjects.length < 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_objects_found_of_yours"));
            return false;
        }
        obj_id usedBlueprint = static_item.createNewItemFunction("st_o_buildout_blueprint", player);
        utils.setBatchObjVar(usedBlueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR, blueprintObjects);
        setObjVar(usedBlueprint, storyteller.BLUEPRINT_AUTHOR_OBJVAR, player);
        setObjVar(usedBlueprint, storyteller.BLUEPRINT_AUTHOR_NAME_OBJVAR, getName(player));
        setObjVar(usedBlueprint, storyteller.BLUEPRINT_VERSION_OBJVAR, storyteller.BLUEPRINT_VERSION_NUM);
        sendSystemMessage(player, new string_id("storyteller", "blueprint_saved"));
        return true;
    }
}
