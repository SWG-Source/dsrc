package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.utils;
import script.library.beast_lib;
import script.library.static_item;
import script.library.sui;

public class beast_dye extends script.base_script
{
    public beast_dye()
    {
    }
    public static final string_id SID_DYE_BEAST = new string_id("beast", "menu_dye");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int management_root = mi.addRootMenu(menu_info_types.ITEM_USE, SID_DYE_BEAST);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id beast = beast_lib.getBeastOnPlayer(player);
            if (!isIdValid(beast))
            {
                sendSystemMessage(player, beast_lib.SID_NO_BEAST);
                return SCRIPT_CONTINUE;
            }
            if (isDead(beast))
            {
                sendSystemMessage(player, new string_id("beast", "beast_cant_when_dead"));
                return SCRIPT_CONTINUE;
            }
            ranged_int_custom_var[] palColors = hue.getPalcolorVars(beast);
            if (palColors == null || palColors.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            String[] palettes = 
            {
                "",
                "",
                "",
                ""
            };
            for (int i = 0; i < palColors.length; i++)
            {
                ranged_int_custom_var ri = palColors[i];
                String customizationVar = ri.getVarName();
                if (customizationVar.startsWith("/"))
                {
                    palettes[i] = customizationVar;
                }
            }
            utils.setScriptVar(beast, "beast.tool_oid", self);
            openCustomizationWindow(player, beast, palettes[0], -1, -1, palettes[1], -1, -1, palettes[2], -1, -1, palettes[3], -1, -1);
        }
        return SCRIPT_CONTINUE;
    }
}
