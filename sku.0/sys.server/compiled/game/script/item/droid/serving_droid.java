package script.item.droid;

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

public class serving_droid extends script.base_script
{
    public serving_droid()
    {
    }
    public static final string_id PCOLOR = new string_id("sui", "set_primary_color");
    public static final string_id SCOLOR = new string_id("sui", "set_secondary_color");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id droid = self;
        if (!utils.isNestedWithin(droid, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuPrimaryColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, PCOLOR);
        int mnuSecondaryColor = mi.addRootMenu(menu_info_types.SERVER_MENU2, SCOLOR);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id droid = self;
        if (!utils.isNestedWithin(droid, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            sui.colorize(droid, player, droid, hue.INDEX_1, "handlePrimaryColorize");
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            sui.colorize(droid, player, droid, hue.INDEX_2, "handleSecondaryColorize");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePrimaryColorize(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = self;
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            custom_var myVar = getCustomVarByName(droid, hue.INDEX_1);
            if (myVar != null && myVar.isPalColor())
            {
                palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                pcVar.setValue(idx);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSecondaryColorize(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = self;
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            custom_var myVar = getCustomVarByName(droid, hue.INDEX_2);
            if (myVar != null && myVar.isPalColor())
            {
                palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                pcVar.setValue(idx);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
