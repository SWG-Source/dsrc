package script.systems.armor_rehue;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.sui;
import script.library.utils;

public class composite_rehue extends script.base_script
{
    public composite_rehue()
    {
    }
    public static final string_id MNU_COLOR = new string_id("sui", "set_color");
    public static final String SCRIPTVAR_ARMOR_PID = "armor_rehue.pid";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_COLOR);
        if (mnuColor > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
        {
            String template = utils.getTemplateFilenameNoPath(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (getContainedBy(self) == getOwner(self))
            {
                string_id sysmessage = new string_id("armor_rehue", "equipped");
                sendSystemMessage(player, sysmessage);
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(self, SCRIPTVAR_ARMOR_PID))
            {
                int oldpid = utils.getIntScriptVar(self, SCRIPTVAR_ARMOR_PID);
                sui.closeSUI(player, oldpid);
                utils.removeScriptVar(self, SCRIPTVAR_ARMOR_PID);
            }
            String index = hue.INDEX_1;
            if (index != null && !index.equals("") && !index.equals("none"))
            {
                int pid_armor = sui.colorize(self, player, self, index, "handleColorize");
                if (pid_armor > -1)
                {
                    utils.setScriptVar(self, SCRIPTVAR_ARMOR_PID, pid_armor);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleColorize(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_ARMOR_PID);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        String index = hue.INDEX_1;
        if (index != null && !index.equals("") && !index.equals("none"))
        {
            int idx = sui.getColorPickerIndex(params);
            if (idx > -1)
            {
                hue.setColor(self, index, idx);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
