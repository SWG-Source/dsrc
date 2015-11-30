package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class door_lock_crafting_tailor extends script.base_script
{
    public door_lock_crafting_tailor()
    {
    }
    public static final String MSGS = "dungeon/death_watch";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, "room_in_use"))
            {
                string_id occupied = new string_id(MSGS, "room_in_use");
                sendSystemMessage(player, occupied);
                messageTo(self, "unlockTerminal", null, 30, false);
                return SCRIPT_CONTINUE;
            }
            if (hasSkill(player, "class_domestics_phase4_master"))
            {
                if (checkForIngredients(player))
                {
                    obj_id top = getTopMostContainer(self);
                    String roomName = getStringObjVar(self, "room");
                    obj_id room = getCellId(top, roomName);
                    dictionary webster = new dictionary();
                    webster.put("player", player);
                    messageTo(room, "unlockYourself", webster, 1, false);
                    string_id unlock = new string_id(MSGS, "unlock_door");
                    sendSystemMessage(player, unlock);
                    setObjVar(self, "room_in_use", 1);
                }
                else 
                {
                    string_id ingredients = new string_id(MSGS, "not_enough_ingredients");
                    sendSystemMessage(player, ingredients);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                string_id skill = new string_id(MSGS, "not_enough_tailor_skill");
                sendSystemMessage(player, skill);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForIngredients(obj_id player) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/dungeon/death_watch_bunker/mining_drill_reward.iff"))
        {
            if (utils.playerHasItemByTemplate(player, "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_belt.iff") || utils.playerHasItemByTemplate(player, "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_leggings.iff") || utils.playerHasItemByTemplate(player, "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_gloves.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public int unlockTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        boolean empty = true;
        obj_id mom = getTopMostContainer(self);
        String roomName = getStringObjVar(self, "room");
        if (roomName == null || roomName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id room = getCellId(mom, roomName);
        if (room == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] cell_contents = getContents(room);
        if (cell_contents == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numInCell = cell_contents.length;
        if (numInCell > 0)
        {
            for (int i = 0; i < numInCell; i++)
            {
                if (isIdValid(cell_contents[i]) && isPlayer(cell_contents[i]))
                {
                    empty = false;
                }
            }
        }
        if (empty == true)
        {
            removeObjVar(self, "room_in_use");
        }
        return SCRIPT_CONTINUE;
    }
}
