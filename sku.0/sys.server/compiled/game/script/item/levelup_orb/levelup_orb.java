package script.item.levelup_orb;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.xp;

public class levelup_orb extends script.base_script
{
    public levelup_orb()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "item.special.nomove"))
        {
            attachScript(self, "item.special.nomove");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (canManipulate(player, self, true, true, 15, true))
            {
                int combatLevel = getLevel(player);
                if (combatLevel == 1)
                {
                    sendSystemMessage(player, new string_id("event_perk", "levelup_orb_badclass"));
                    return SCRIPT_CONTINUE;
                }
                else if (combatLevel == 90)
                {
                    sendSystemMessage(player, new string_id("event_perk", "levelup_orb_toohigh"));
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    raiseLevel(player);
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void raiseLevel(obj_id player) throws InterruptedException
    {
        int currentLevel = getLevel(player);
        int xpNeeded = dataTableGetInt("datatables/player/player_level.iff", currentLevel, "xp_required");
        int prevNeeded = dataTableGetInt("datatables/player/player_level.iff", currentLevel - 1, "xp_required");
        xpNeeded -= prevNeeded;
        if (getConfigSetting("GameServer", "xpMultiplier") != null)
        {
            int xpBonusValue = utils.stringToInt(getConfigSetting("GameServer", "xpMultiplier"));
            if (xpBonusValue > 1)
            {
                xpNeeded = xpNeeded / xpBonusValue;
                xpNeeded += 1;
            }
        }
        xp.grant(player, "combat_general", xpNeeded);
    }
}
