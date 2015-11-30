package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class terminal_food extends script.terminal.base.terminal_add_use
{
    public terminal_food()
    {
    }
    public static final string_id SID_ORDER_FOOD = new string_id("sui", "order_food");
    public static final String[] TEMPLATES = 
    {
        "object/tangible/food/bread_loaf_full_s1.iff",
        "object/tangible/food/fruit_melon.iff",
        "object/tangible/food/meat_kabob.iff"
    };
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                obj_id inv = getObjectInSlot(player, "inventory");
                int idx = rand(0, TEMPLATES.length - 1);
                obj_id food = createObject(TEMPLATES[idx], inv, "");
                if ((food == null) || (food == obj_id.NULL_ID))
                {
                    sendSystemMessageTestingOnly(player, "Unable to create food. Make sure you have space in your inventory.");
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "A random foodstuff has been created in your inventory.");
                }
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
}
