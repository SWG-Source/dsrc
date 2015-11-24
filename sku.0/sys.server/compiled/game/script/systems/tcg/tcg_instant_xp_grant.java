package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class tcg_instant_xp_grant extends script.base_script
{
    public tcg_instant_xp_grant()
    {
    }
    public static final String TBL_PLAYER_LEVEL_XP = "datatables/player/player_level.iff";
    public static final string_id SID_REWARD_XP = new string_id("collection", "reward_xp_amount");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
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
            if (hasObjVar(self, "grant_xp_percent"))
            {
                int playerLevel = getLevel(player);
                if (playerLevel >= 90)
                {
                    CustomerServiceLog("tcg", "TCG Item(" + self + ")used by player: (" + player + ")" + getFirstName(player) + " Player Level: " + playerLevel + ". Since player is 90th Lvl, this player receives a random collection object.");
                    obj_id collectionItem = collection.grantRandomCollectionItem(player, "datatables/loot/loot_items/collectible/magseal_loot.iff", "collections");
                    if (!isValidId(collectionItem) || !exists(collectionItem))
                    {
                        CustomerServiceLog("tcg", "TCG Item (" + self + ") used by player: (" + player + ")" + getFirstName(player) + " Player Level: " + playerLevel + ". The collection system reports that a collectible object WAS NOT recieved by the player due to an internal error. Please notify SWG design.");
                        return SCRIPT_CONTINUE;
                    }
                    CustomerServiceLog("buff", "TCG Item (" + self + ") used by player: (" + player + ")" + getFirstName(player) + " Player Level: " + playerLevel + ". Collection Item(" + collectionItem + ") was received by the player.");
                    decrementCount(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    float xpPercent = getFloatObjVar(self, "grant_xp_percent");
                    int xpForCurrentLevel = dataTableGetInt(TBL_PLAYER_LEVEL_XP, playerLevel - 1, "xp_required");
                    int xpForNextLevel = dataTableGetInt(TBL_PLAYER_LEVEL_XP, playerLevel, "xp_required");
                    int xpForLevel = xpForNextLevel - xpForCurrentLevel;
                    int xpToGrant = (int)(xpPercent * xpForLevel);
                    if (xpToGrant > 0)
                    {
                        playClientEffectObj(player, "clienteffect/tcg_t16_skyhopper_toy_flyby.cef", player, "root");
                        prose_package pp = new prose_package();
                        prose.setStringId(pp, SID_REWARD_XP);
                        prose.setDI(pp, xpToGrant);
                        sendSystemMessageProse(player, pp);
                        xp.grantXpByTemplate(player, xpToGrant);
                        CustomerServiceLog("tcg", "Player " + getFirstName(player) + "(" + player + ") was granted XP: " + xpToGrant + " XP from TCG Item: (" + self + ")");
                        decrementCount(self);
                    }
                    else 
                    {
                        CustomerServiceLog("tcg", "Player " + getFirstName(player) + "(" + player + ") attempted to receive XP from TCG Item: (" + self + ") but failed because XP amount was less than Zero.");
                    }
                }
            }
            else 
            {
                CustomerServiceLog("tcg", "Player " + getFirstName(player) + "(" + player + ") attempted to use TCG Item(" + self + ") but can not - item is missing 'grant_xp_percent' objvar)");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
