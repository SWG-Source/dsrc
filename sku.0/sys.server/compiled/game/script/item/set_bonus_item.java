package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;

public class set_bonus_item extends script.base_script
{
    public set_bonus_item()
    {
    }
    public static final String SET_BONUS_TABLE = "datatables/item/item_sets.iff";
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int setBonusIndex = static_item.getSetBonusIndex(self);
        if (setBonusIndex == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int numTableRows = dataTableGetNumRows(static_item.SET_BONUS_TABLE);
        if (!static_item.isStaticItem(self))
        {
            for (int i = 0; i < numTableRows; i++)
            {
                int thisRowSetBonusIndex = dataTableGetInt(static_item.SET_BONUS_TABLE, i, "SETID");
                int numberOfSetItems = dataTableGetInt(static_item.SET_BONUS_TABLE, i, "NUM_ITEMS");
                if (setBonusIndex == thisRowSetBonusIndex)
                {
                    String setBonusBuffName = dataTableGetString(static_item.SET_BONUS_TABLE, i, "EFFECT");
                    names[free] = utils.packStringId(new string_id("set_bonus", "piece_bonus_count_" + numberOfSetItems));
                    attribs[free++] = utils.packStringId(new string_id("set_bonus", setBonusBuffName));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
