package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.pclib;
import script.library.prose;
import script.library.utils;

public class jedi_holocron extends script.base_script
{
    public jedi_holocron()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, "intUsed"))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
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
            if (hasObjVar(self, "intUsed"))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, "jedi.holcron_used"))
            {
                sendSystemMessage(player, new string_id("jedi_spam", "holocron_no_effect"));
                return SCRIPT_CONTINUE;
            }
            int max_force = getMaxForcePower(player);
            int current_force = getForcePower(player);
            if (max_force < 1)
            {
                sendSystemMessage(player, new string_id("jedi_spam", "holocron_no_effect"));
                return SCRIPT_CONTINUE;
            }
            if (current_force >= max_force)
            {
                sendSystemMessage(player, new string_id("jedi_spam", "holocron_force_max"));
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, new string_id("jedi_spam", "holocron_force_replenish"));
            location loc = getLocation(player);
            playClientEffectLoc(player, "clienteffect/pl_force_heal_self.cef", loc, 0);
            utils.setScriptVar(player, "jedi.holcron_used", 1);
            setForcePower(player, max_force);
            setObjVar(self, "intUsed", 1);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
