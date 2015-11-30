package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.player_structure;
import script.library.utils;

public class filter_dispenser extends script.base_script
{
    public filter_dispenser()
    {
    }
    public static final string_id MNU_ISSUE_FILTER = new string_id("dungeon/death_watch", "mnu_issue_filter");
    public static final string_id ISSUE_FILTER = new string_id("dungeon/death_watch", "issue_filter");
    public static final string_id ALREADY_HAS_FILTER = new string_id("dungeon/death_watch", "already_has_filter");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (checkForFilter(player) == true)
        {
            sendSystemMessage(player, ALREADY_HAS_FILTER);
            return SCRIPT_CONTINUE;
        }
        int mnuControl = mi.addRootMenu(menu_info_types.ITEM_USE, MNU_ISSUE_FILTER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            giveFilter(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForFilter(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/filter.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void giveFilter(obj_id player) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id item = createObject("object/tangible/dungeon/death_watch_bunker/filter.iff", playerInv, "");
            if (isIdValid(item))
            {
                sendSystemMessage(player, ISSUE_FILTER);
                return;
            }
        }
        return;
    }
}
