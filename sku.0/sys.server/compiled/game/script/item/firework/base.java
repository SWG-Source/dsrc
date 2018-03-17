package script.item.firework;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.firework;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final string_id SID_NO_FIREWORKS_IN_SPACE = new string_id("space/space_interaction", "no_fireworks_in_space");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, 5);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenuOrServerNotify(menu_info_types.ITEM_USE, null);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_FIREWORKS_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id inv = utils.getInventoryContainer(player);
            if (isIdValid(inv) && utils.isNestedWithin(self, inv))
            {
                queueCommand(player, (-309362530), self, "", COMMAND_PRIORITY_DEFAULT);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        String fx = "";
        if (!hasObjVar(self, firework.VAR_FIREWORK_BASE))
        {
            fx = firework.getRandomFireworkEffect();
            if (fx != null && !fx.equals(""))
            {
                setObjVar(self, firework.VAR_FIREWORK_FX, fx);
            }
        }
        else 
        {
            fx = getStringObjVar(self, firework.VAR_FIREWORK_FX);
        }
        if (fx == null || fx.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "pattern";
        attribs[idx] = "@firework_n:" + fx;
        return SCRIPT_CONTINUE;
    }
}
