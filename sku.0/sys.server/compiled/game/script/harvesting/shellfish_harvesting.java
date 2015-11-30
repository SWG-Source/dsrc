package script.harvesting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.resource;
import script.library.prose;
import script.library.colors;
import script.library.minigame;

public class shellfish_harvesting extends script.base_script
{
    public shellfish_harvesting()
    {
    }
    public static final string_id SID_USE = new string_id("sui", "use");
    public static final string_id SID_FOUND_NOTHING = new string_id("harvesting", "found_nothing");
    public static final string_id SID_FOUND_MOLLUSKS = new string_id("harvesting", "found_mollusks");
    public static final string_id SID_FOUND_CRUSTACEANS = new string_id("harvesting", "found_crustaceans");
    public static final string_id SID_SWIMMING = new string_id("harvesting", "swimming");
    public static final string_id SID_INSIDE = new string_id("harvesting", "inside");
    public static final string_id SID_IN_WATER = new string_id("harvesting", "in_water");
    public static final string_id SID_SAMPLE_MIND = new string_id("error_message", "sample_mind");
    public static final string_id SID_SURVEY_ON_MOUNT = new string_id("error_message", "survey_on_mount");
    public static final string_id SID_BUSY = new string_id("harvesting", "busy");
    public static final string_id SID_INV_FULL = new string_id("harvesting", "inv_full");
    public static final int SAMPLE_ACTION_COST = 50;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            use(self, player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void use(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        if (getVolumeFree(inventory) <= 0)
        {
            sendSystemMessage(player, SID_INV_FULL);
            return;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isIdValid(pInv))
        {
            return;
        }
        if (getState(player, STATE_SWIMMING) != 0)
        {
            sendSystemMessage(player, SID_SWIMMING);
            return;
        }
        obj_id playerCurrentMount = getMountId(player);
        if (isIdValid(playerCurrentMount))
        {
            sendSystemMessage(player, SID_SURVEY_ON_MOUNT);
            return;
        }
        location here = getLocation(player);
        if (here == null)
        {
            return;
        }
        if (isIdValid(here.cell))
        {
            sendSystemMessage(player, SID_INSIDE);
            return;
        }
        if (!minigame.isLocationFishable(here))
        {
            sendSystemMessage(player, SID_IN_WATER);
            return;
        }
        boolean harvesting = utils.hasScriptVar(player, "shellfish_harvesting");
        if (harvesting)
        {
            sendSystemMessage(player, SID_BUSY);
            return;
        }
        int action = getAttrib(player, ACTION);
        int actioncost = SAMPLE_ACTION_COST;
        if (!drainAttributes(player, actioncost, 0))
        {
            sendSystemMessage(player, SID_SAMPLE_MIND);
            return;
        }
        utils.setScriptVar(player, "shellfish_harvesting", 1);
        dictionary harvester = new dictionary();
        harvester.put("player", player);
        messageTo(self, "harvest", harvester, 5f, false);
    }
    public int harvest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isIdValid(pInv))
        {
            return SCRIPT_CONTINUE;
        }
        int searchRoll = rand(1, 100);
        if (searchRoll < 25)
        {
            sendSystemMessage(player, SID_FOUND_NOTHING);
        }
        else if (searchRoll < 70)
        {
            sendSystemMessage(player, SID_FOUND_MOLLUSKS);
            int amt = rand(8, 14);
            resource.createRandom("seafood_mollusk", amt, getLocation(self), pInv, player, 2);
        }
        else 
        {
            sendSystemMessage(player, SID_FOUND_CRUSTACEANS);
            int amt = rand(8, 14);
            resource.createRandom("seafood_crustacean", amt, getLocation(self), pInv, player, 2);
        }
        utils.removeScriptVar(player, "shellfish_harvesting");
        return SCRIPT_CONTINUE;
    }
}
