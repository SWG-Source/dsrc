package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.metrics;
import script.library.prose;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class pvp_spec_ops_imperial_armor_dye_kit extends script.base_script
{
    public pvp_spec_ops_imperial_armor_dye_kit()
    {
    }
    public static final String[] COLORS = 
    {
        "@tool/customizer:imppvp_color_white",
        "@tool/customizer:imppvp_color_black"
    };
    public static final String[] COLORS_CHEST = 
    {
        "@tool/customizer:imppvp_color_white_orange",
        "@tool/customizer:imppvp_color_white_white",
        "@tool/customizer:imppvp_color_white_black",
        "@tool/customizer:imppvp_color_white_red",
        "@tool/customizer:imppvp_color_white_blue",
        "@tool/customizer:imppvp_color_white_yellow",
        "@tool/customizer:imppvp_color_black_orange",
        "@tool/customizer:imppvp_color_black_white",
        "@tool/customizer:imppvp_color_black_black",
        "@tool/customizer:imppvp_color_black_red",
        "@tool/customizer:imppvp_color_black_blue",
        "@tool/customizer:imppvp_color_black_yellow"
    };
    public static final String[] ARMOR_TYPES = 
    {
        "belt",
        "bicep_l",
        "bicep_r",
        "boots",
        "bracer_l",
        "bracer_r",
        "chest_plate",
        "gloves",
        "helmet",
        "leggings"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, 10);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
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
            beginArmorColorization(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void beginArmorColorization(obj_id self, obj_id player) throws InterruptedException
    {
        Vector wornItems = new Vector();
        Vector items = new Vector();
        obj_id[] equippedItems = metrics.getWornItems(player);
        if (equippedItems != null && equippedItems.length > 0)
        {
            for (int i = 0; i < equippedItems.length; i++)
            {
                String name = getStaticItemName(equippedItems[i]);
                if (name == null || name.equals(""))
                {
                    continue;
                }
                if (name.startsWith("armor_pvp_spec_ops_imperial"))
                {
                    wornItems.addElement(equippedItems[i]);
                }
            }
        }
        obj_id[] invItems = utils.getContents(utils.getInventoryContainer(player), true);
        if (invItems != null && invItems.length > 0)
        {
            for (int i = 0; i < invItems.length; i++)
            {
                String name = getStaticItemName(invItems[i]);
                if (name == null || name.equals(""))
                {
                    continue;
                }
                if (name.startsWith("armor_pvp_spec_ops_imperial"))
                {
                    items.addElement(invItems[i]);
                }
            }
        }
        if (wornItems.isEmpty() && items.isEmpty())
        {
            sendSystemMessage(player, new string_id("tool/customizer", "imppvp_no_crusader_armor"));
            return;
        }
        String title = "@tool/customizer:imppvp_sui_title";
        String prompt = "@tool/customizer:imppvp_prompt1";
        Vector armor = new Vector();
        Vector armorNames = new Vector();
        if (!wornItems.isEmpty())
        {
            for (int i = 0; i < wornItems.size(); i++)
            {
                obj_id piece = (obj_id)wornItems.get(i);
                utils.setScriptVar(piece, "imppvp_worn", 1);
                armor.addElement(piece);
                String name = "@" + getName(piece) + "  ( @tool/customizer:imppvp_worn )";
                armorNames.addElement(name);
            }
        }
        if (!items.isEmpty())
        {
            for (int i = 0; i < items.size(); i++)
            {
                obj_id piece = (obj_id)items.get(i);
                armor.addElement(piece);
                String name = "@" + getName(piece);
                armorNames.addElement(name);
            }
        }
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, armorNames, "handleArmorSelection", true, false);
        if (pid < 0)
        {
            cleanup(self, player);
        }
        else 
        {
            utils.setScriptVar(self, "imppvp_armor", armor);
        }
    }
    public int handleArmorSelection(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] armor = utils.getObjIdArrayScriptVar(self, "imppvp_armor");
        if (armor == null || armor.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx >= armor.length)
        {
            return SCRIPT_CONTINUE;
        }
        String title = "@tool/customizer:imppvp_sui_title";
        String prompt = "@tool/customizer:imppvp_prompt2";
        String name = getStaticItemName(armor[idx]);
        if (utils.isEquipped(armor[idx]))
        {
            prose_package pp = new prose_package();
            prose.setTT(pp, "@" + getName(armor[idx]));
            prose.setStringId(pp, new string_id("tool/customizer", "unequip_first"));
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        if (name.startsWith("armor_pvp_spec_ops_imperial_black_chest") || name.startsWith("armor_pvp_spec_ops_imperial_white_chest"))
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, COLORS_CHEST, "handleColorSelection", true, false);
            if (pid < 0)
            {
                cleanup(self, player);
            }
            else 
            {
                utils.setScriptVar(self, "imppvp_piece", armor[idx]);
            }
        }
        else 
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, COLORS, "handleColorSelection", true, false);
            if (pid < 0)
            {
                cleanup(self, player);
            }
            else 
            {
                utils.setScriptVar(self, "imppvp_piece", armor[idx]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleColorSelection(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id piece = utils.getObjIdScriptVar(self, "imppvp_piece");
        if (utils.isEquipped(piece))
        {
            prose_package pp = new prose_package();
            prose.setTT(pp, "@" + getName(piece));
            prose.setStringId(pp, new string_id("tool/customizer", "unequip_first"));
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        boolean worn = false;
        if (utils.hasScriptVar(piece, "imppvp_worn"))
        {
            worn = true;
        }
        String newArmorName = constructNewArmorString(piece, idx);
        if (newArmorName == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id newArmor = static_item.createNewItemFunction(newArmorName, player);
        cleanup(self, player);
        if (isIdValid(newArmor))
        {
            sendSystemMessage(player, new string_id("tool/customizer", "imppvp_success"));
            destroyObject(piece);
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(newArmor, "delayedEquip", d, 0.0f, false);
            int count = getCount(self);
            count--;
            if (count > 0)
            {
                setCount(self, count);
            }
            else 
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id self, obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(self, "imppvp_armor");
        utils.removeScriptVar(self, "imppvp_piece");
        obj_id[] equippedItems = metrics.getWornItems(player);
        if (equippedItems != null && equippedItems.length > 0)
        {
            for (int i = 0; i < equippedItems.length; i++)
            {
                utils.removeScriptVar(equippedItems[i], "imppvp_worn");
            }
        }
    }
    public String constructNewArmorString(obj_id armor, int idx) throws InterruptedException
    {
        String name = getStaticItemName(armor);
        String type = null;
        String color = null;
        if (name.startsWith("armor_pvp_spec_ops_imperial_black_chest") || name.startsWith("armor_pvp_spec_ops_imperial_white_chest"))
        {
            for (int i = 0; i < ARMOR_TYPES.length; i++)
            {
                if (name.indexOf(ARMOR_TYPES[i]) > -1)
                {
                    type = ARMOR_TYPES[i];
                }
            }
            switch (idx)
            {
                case 0:
                color = "white_chest_plate_orange_pad";
                break;
                case 1:
                color = "white_chest_plate_white_pad";
                break;
                case 2:
                color = "white_chest_plate_black_pad";
                break;
                case 3:
                color = "white_chest_plate_red_pad";
                break;
                case 4:
                color = "white_chest_plate_blue_pad";
                break;
                case 5:
                color = "white_chest_plate_yellow_pad";
                break;
                case 6:
                color = "black_chest_plate_orange_pad";
                break;
                case 7:
                color = "black_chest_plate_white_pad";
                break;
                case 8:
                color = "black_chest_plate_black_pad";
                break;
                case 9:
                color = "black_chest_plate_red_pad";
                break;
                case 10:
                color = "black_chest_plate_blue_pad";
                break;
                case 11:
                color = "black_chest_plate_yellow_pad";
                break;
            }
            if (type == null || color == null)
            {
                return null;
            }
            return "armor_pvp_spec_ops_imperial_" + color + "_05_01";
        }
        else 
        {
            for (int i = 0; i < ARMOR_TYPES.length; i++)
            {
                if (name.indexOf(ARMOR_TYPES[i]) > -1)
                {
                    type = ARMOR_TYPES[i];
                }
            }
            switch (idx)
            {
                case 0:
                color = "white_";
                break;
                case 1:
                color = "black_";
                break;
            }
            if (type == null || color == null)
            {
                return null;
            }
            return "armor_pvp_spec_ops_imperial_" + color + type + "_05_01";
        }
    }
}
