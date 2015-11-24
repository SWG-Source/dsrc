package script.item.gcw_buff_banner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.create;
import script.library.trial;
import script.library.static_item;
import script.library.utils;

public class pvp_lieutenant_comm_link extends script.base_script
{
    public pvp_lieutenant_comm_link()
    {
    }
    public static final int FACTION_NONE = -1;
    public static final int FACTION_REBEL = 0;
    public static final int FACTION_IMPERIAL = 1;
    public static final string_id SID_ALREADY_HAVE = new string_id("gcw", "comm_already_used");
    public static final string_id SID_WRONG_FACTION = new string_id("gcw", "comm_wrong_faction");
    public static final string_id SID_TOO_LOW_LEVEL = new string_id("gcw", "player_too_low");
    public static final string_id SID_INDOORS = new string_id("gcw", "player_is_indoors");
    public static final string_id SID_NOT_DECLARED = new string_id("gcw", "sf_restricted");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
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
            int faction = FACTION_NONE;
            if (factions.isRebel(player))
            {
                faction = FACTION_REBEL;
                String commlink = getStaticItemName(self);
                if (commlink.equals("item_pvp_lieutenant_comm_link_imperial_reward_04_01"))
                {
                    sendSystemMessage(player, SID_WRONG_FACTION);
                    return SCRIPT_CONTINUE;
                }
            }
            else if (factions.isImperial(player))
            {
                faction = FACTION_IMPERIAL;
                String commlink = getStaticItemName(self);
                if (commlink.equals("item_pvp_lieutenant_comm_link_rebel_reward_04_01"))
                {
                    sendSystemMessage(player, SID_WRONG_FACTION);
                    return SCRIPT_CONTINUE;
                }
            }
            else if (faction == FACTION_NONE)
            {
                return SCRIPT_CONTINUE;
            }
            if (!factions.isDeclared(player))
            {
                sendSystemMessage(player, SID_NOT_DECLARED);
                return SCRIPT_CONTINUE;
            }
            queueCommand(player, (-447180069), player, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
}
