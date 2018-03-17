package script.item.medicine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.healing;

public class enhancer extends script.base_script
{
    public enhancer()
    {
    }
    public static final string_id CANNOT_USE_ITEM = new string_id("base_player", "cannot_use_item");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != player || !hasObjVar(self, "commandName"))
        {
            return SCRIPT_CONTINUE;
        }
        String command = getStringObjVar(self, "commandName");
        if (!hasCommand(player, command))
        {
            healing.sendMedicalSpam(player, CANNOT_USE_ITEM, COMBAT_RESULT_MEDICAL);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            int commandCrc = getStringCrc(toLower(command));
            queueCommand(player, commandCrc, getLookAtTarget(player), "" + self, COMMAND_PRIORITY_NORMAL);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "healing.enhancement"))
        {
            names[idx] = "enhancer_power";
            attribs[idx] = "" + Math.floor(getFloatObjVar(self, "healing.enhancement"));
        }
        return SCRIPT_CONTINUE;
    }
}
