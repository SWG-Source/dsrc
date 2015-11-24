package script.space.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;
import script.library.static_item;
import script.library.utils;

public class interdiction_data_disk extends script.base_script
{
    public interdiction_data_disk()
    {
    }
    public static final string_id SID_READY_ITEM = new string_id("space/space_terminal", "encode_item");
    public static final String[] VALID_REGIONS = 
    {
        "space_tatooine",
        "space_corellia",
        "space_dantooine",
        "space_dathomir",
        "space_endor",
        "space_lok",
        "space_naboo",
        "space_yavin4"
    };
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (hasObjVar(self, "difficulty"))
        {
            names[idx] = "difficulty";
            int difficulty = getIntObjVar(self, "difficulty");
            attribs[idx] = " " + difficulty;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_READY_ITEM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self) || item <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasObjVar(self, "difficulty") || !utils.isNestedWithin(self, player))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id term = getTerminal(player);
            if (isIdValid(term) && exists(term))
            {
                encodeTerminal(self, player);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, "You must have an unencoded Interdiction Burst Generator in your inventory to use this item", null);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getTerminal(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String strItemTemplate = getTemplateName(inventoryContents[i]);
            
            {
                if (strItemTemplate.equals("object/tangible/space/mission_objects/interdiction_terminal.iff"))
                {
                    if (!hasObjVar(inventoryContents[i], "difficulty"))
                    {
                        return inventoryContents[i];
                    }
                }
            }
        }
        return null;
    }
    public void encodeTerminal(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id terminal = getTerminal(player);
        if (!isIdValid(self) || !isIdValid(terminal))
        {
            return;
        }
        int i = rand(0, VALID_REGIONS.length - 1);
        String zone = VALID_REGIONS[i];
        setObjVar(terminal, "region", zone);
        float x = rand(-5000.f, 5000.f);
        float y = rand(-5000.f, 5000.f);
        float z = rand(-5000.f, 5000.f);
        location loc = new location(x, y, z, zone);
        setObjVar(terminal, "location", loc);
        int dif = getIntObjVar(self, "difficulty");
        if (dif > 0 && dif <= 5)
        {
            setObjVar(terminal, "difficulty", dif);
        }
        static_item.decrementStaticItem(self);
        return;
    }
}
