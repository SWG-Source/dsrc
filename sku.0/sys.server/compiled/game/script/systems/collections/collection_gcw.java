package script.systems.collections;

import script.*;
import script.library.factions;
import script.library.sui;
import script.library.utils;

public class collection_gcw extends script.base_script
{
    public collection_gcw()
    {
    }
    public static final String PID_NAME = "gcw_consume";
    public static final string_id SID_GCW_CONSUME_PROMPT = new string_id("collection", "consume_gcw_prompt");
    public static final string_id SID_GCW_CONSUME_TITLE = new string_id("collection", "consume_gcw_title");
    public static final string_id SID_GCW_CONSUME_ITEM = new string_id("collection", "consume_gcw_item");
    public static final string_id WRONG_FACTION = new string_id("collection", "wrong_faction");
    public static final string_id USED_ITEM = new string_id("collection", "gcw_point_item_used");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id gcw_item = self;
        String faction = factions.getFaction(player);
        if (faction == null || faction.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(gcw_item))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU10, SID_GCW_CONSUME_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        obj_id gcw_item = self;
        if (item == menu_info_types.SERVER_MENU10 && utils.isNestedWithinAPlayer(gcw_item))
        {
            if (sui.hasPid(player, PID_NAME))
            {
                int pid = sui.getPid(player, PID_NAME);
                forceCloseSUIPage(pid);
            }
            String faction = factions.getFaction(player);
            if (faction == null || faction.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String itemFaction = "";
            if (hasObjVar(gcw_item, "faction"))
            {
                itemFaction = getStringObjVar(gcw_item, "faction");
            }
            if ((toLower(faction)).equals("rebel") && itemFaction.equals(toLower(faction)))
            {
                int pid = sui.msgbox(self, player, "@" + SID_GCW_CONSUME_PROMPT, sui.YES_NO, "@" + SID_GCW_CONSUME_TITLE, "handlerSuiGrantGcwPoints");
                sui.setPid(player, pid, PID_NAME);
                return SCRIPT_CONTINUE;
            }
            else if ((toLower(faction)).equals("imperial") && itemFaction.equals(toLower(faction)))
            {
                int pid = sui.msgbox(self, player, "@" + SID_GCW_CONSUME_PROMPT, sui.YES_NO, "@" + SID_GCW_CONSUME_TITLE, "handlerSuiGrantGcwPoints");
                sui.setPid(player, pid, PID_NAME);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, WRONG_FACTION);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiGrantGcwPoints(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gcw_item = self;
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(gcw_item, "collection.gcw_point_value"))
        {
            int gcw_amount = getIntObjVar(gcw_item, "collection.gcw_point_value");
            pvpModifyCurrentGcwPoints(player, gcw_amount);
            sendSystemMessage(player, USED_ITEM);
            decrementCount(gcw_item);
            String playerName = getPlayerName(player);
            if (!exists(gcw_item))
            {
                CustomerServiceLog("CollectionLootChannel", playerName + "(" + player + ") has acquired " + gcw_amount + " GCW points by consuming a GCW Collection Reward (" + gcw_item + ") was deleted(consumed) *by design*.");
            }
            else 
            {
                CustomerServiceLog("CollectionLootChannel", playerName + "(" + player + ") has acquired " + gcw_amount + " GCW points by consuming a GCW Collection Reward (" + gcw_item + ") was NOT DELETED!!! CONTACT SWG DESIGN(jmichener@soe.sony.com)");
            }
        }
        else 
        {
            String playerName = getPlayerName(player);
            CustomerServiceLog("CollectionLootChannel: ", "BrokenLoot: " + gcw_item + " did not give " + playerName + "(" + player + ") GCW Points due to a bad objvar");
        }
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
}
