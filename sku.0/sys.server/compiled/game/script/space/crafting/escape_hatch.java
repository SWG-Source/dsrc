package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.sui;
import script.library.space_utils;
import script.library.utils;
import script.library.space_transition;

public class escape_hatch extends script.base_script
{
    public escape_hatch()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        string_id strSpam = new string_id("space/space_interaction", "eject");
        mi.addRootMenu(menu_info_types.ITEM_USE, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id objPlayer, int item) throws InterruptedException
    {
        LOG("space", "ESCAPE POD");
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id objShip = space_transition.getContainingShip(objPlayer);
            obj_id objOwner = getOwner(objShip);
            LOG("space", "objOwner is " + objOwner + " player is " + objPlayer);
            if (objOwner == objPlayer)
            {
                LOG("space", "Queing command ");
                string_id strSpam = new string_id("space/space_interaction", "ejecting");
                sendSystemMessage(objPlayer, strSpam);
                utils.setLocalVar(objShip, "intEjecting", 1);
                dictionary dctParams = new dictionary();
                dctParams.put("objShip", objShip);
                messageTo(objShip, "megaDamage", dctParams, 2, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id strSpam = new string_id("space/space_interaction", "ejecting");
                sendSystemMessage(objPlayer, strSpam);
                space_transition.teleportPlayerToLaunchLoc(objPlayer);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
