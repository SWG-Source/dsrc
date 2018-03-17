package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class colorize_item extends script.base_script
{
    public colorize_item()
    {
    }
    public static final String USE_STRING = "use_string";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id topMost = getTopMostContainer(self);
        if (!player_structure.isBuilding(topMost))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self) && (getOwner(self) == player || player_structure.isAdmin(topMost, player)))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            String use_string = getStringObjVar(self, USE_STRING);
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", use_string));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id topMost = getTopMostContainer(self);
        if (!player_structure.isBuilding(topMost))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self) && (getOwner(self) == player || player_structure.isAdmin(topMost, player)))
        {
            custom_var[] allVars = getAllCustomVars(self);
            if (allVars == null || allVars.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(player, "veteranRewardItemColor.color_setting", true);
            if (allVars.length == 1)
            {
                openCustomizationWindow(player, self, allVars[0].getVarName(), -1, -1, "", -1, -1, "", -1, -1, "", -1, -1);
            }
            else if (allVars.length == 2)
            {
                openCustomizationWindow(player, self, allVars[0].getVarName(), 0, 10, allVars[1].getVarName(), -1, -1, "", -1, -1, "", -1, -1);
            }
            else if (allVars.length == 3)
            {
                openCustomizationWindow(player, self, allVars[0].getVarName(), -1, -1, allVars[1].getVarName(), -1, -1, allVars[2].getVarName(), -1, -1, "", -1, -1);
            }
            else if (allVars.length == 4)
            {
                openCustomizationWindow(player, self, allVars[0].getVarName(), -1, -1, allVars[1].getVarName(), -1, -1, allVars[2].getVarName(), -1, -1, allVars[3].getVarName(), -1, -1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
