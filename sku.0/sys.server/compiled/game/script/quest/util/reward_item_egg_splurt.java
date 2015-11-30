package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.player_structure;

public class reward_item_egg_splurt extends script.base_script
{
    public reward_item_egg_splurt()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        int menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            sendSystemMessage(player, "This item can only be used after being placed within a player structure.", null);
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        obj_id container = getContainedBy(self);
        if (!isIdValid(loc.cell) || !isIdValid(container))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id topContainer = getTopMostContainer(self);
            if (isIdValid(topContainer) && topContainer != self)
            {
                if (player_structure.isBuilding(topContainer))
                {
                    if (loc.cell == container)
                    {
                        playClientEffectLoc(player, "clienteffect/item_egg_splurt.cef", loc, 1f);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
