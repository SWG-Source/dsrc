package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.beast_lib;
import script.library.static_item;

public class beast_food extends script.base_script
{
    public beast_food()
    {
    }
    public static final string_id SID_FEED_BEAST = new string_id("beast", "menu_feed");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int management_root = mi.addRootMenu(menu_info_types.ITEM_USE, SID_FEED_BEAST);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        if (count > 0)
        {
            names[idx] = "count";
            attribs[idx] = "" + count;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id beast = beast_lib.getBeastOnPlayer(player);
            if (!isIdValid(beast))
            {
                sendSystemMessage(player, beast_lib.SID_NO_BEAST);
                return SCRIPT_CONTINUE;
            }
            if (isDead(beast))
            {
                sendSystemMessage(player, new string_id("beast", "beast_cant_when_dead"));
                return SCRIPT_CONTINUE;
            }
            beast_lib.applyFoodEffect(self, beast, player);
            static_item.decrementStaticItem(self);
        }
        return SCRIPT_CONTINUE;
    }
}
