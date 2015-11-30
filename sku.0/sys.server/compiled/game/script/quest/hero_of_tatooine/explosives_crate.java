package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class explosives_crate extends script.base_script
{
    public explosives_crate()
    {
    }
    public static final String ALTRUISM_OBJVAR = "quest.hero_of_tatooine.altruism";
    public static final String ALTRUISM_COMPLETE = ALTRUISM_OBJVAR + ".complete";
    public static final string_id SEARCH = new string_id("quest/hero_of_tatooine/system_messages", "menu_search");
    public static final string_id INVENTORY_FULL = new string_id("quest/hero_of_tatooine/system_messages", "altruism_inventory_full");
    public static final string_id NULL_OBJECT = new string_id("quest/hero_of_tatooine/system_messages", "altruism_null_object");
    public static final string_id GOT_OBJECT = new string_id("quest/hero_of_tatooine/system_messages", "altruism_got_object");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(player, ALTRUISM_OBJVAR) && !hasObjVar(player, ALTRUISM_COMPLETE))
        {
            int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SEARCH);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            if (!hasObjVar(player, ALTRUISM_OBJVAR) || hasObjVar(player, ALTRUISM_COMPLETE))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id playerInv = utils.getInventoryContainer(player);
            if (isIdValid(playerInv))
            {
                int free_space = getVolumeFree(playerInv);
                if (free_space <= 0)
                {
                    sendSystemMessage(player, INVENTORY_FULL);
                    return SCRIPT_CONTINUE;
                }
                obj_id object = createObject("object/tangible/item/quest/hero_of_tatooine/explosives.iff", playerInv, "");
                if (!isIdValid(object))
                {
                    sendSystemMessage(player, NULL_OBJECT);
                    return SCRIPT_CONTINUE;
                }
                sendSystemMessage(player, GOT_OBJECT);
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
