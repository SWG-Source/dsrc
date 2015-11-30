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

public class crusader_armor_dye_kit extends script.base_script
{
    public crusader_armor_dye_kit()
    {
    }
    public static final String[] COLORS = 
    {
        "@tool/customizer:cadk_color_original",
        "@tool/customizer:cadk_color_white",
        "@tool/customizer:cadk_color_black",
        "@tool/customizer:cadk_color_red",
        "@tool/customizer:cadk_color_blue",
        "@tool/customizer:cadk_color_green"
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
                if (name.startsWith("armor_mandalorian"))
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
                if (name.startsWith("armor_mandalorian"))
                {
                    items.addElement(invItems[i]);
                }
            }
        }
        if (wornItems.isEmpty() && items.isEmpty())
        {
            sendSystemMessage(player, new string_id("tool/customizer", "cadk_no_crusader_armor"));
            return;
        }
        String title = "@tool/customizer:cadk_sui_title";
        String prompt = "@tool/customizer:cadk_prompt1";
        Vector armor = new Vector();
        Vector armorNames = new Vector();
        if (!wornItems.isEmpty())
        {
            for (int i = 0; i < wornItems.size(); i++)
            {
                obj_id piece = (obj_id)wornItems.get(i);
                utils.setScriptVar(piece, "cadk_worn", 1);
                armor.addElement(piece);
                String name = "@" + getName(piece) + "  ( @tool/customizer:cadk_worn )";
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
            utils.setScriptVar(self, "cadk_armor", armor);
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
        obj_id[] armor = utils.getObjIdArrayScriptVar(self, "cadk_armor");
        if (armor == null || armor.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx >= armor.length)
        {
            return SCRIPT_CONTINUE;
        }
        String title = "@tool/customizer:cadk_sui_title";
        String prompt = "@tool/customizer:cadk_prompt2";
        String name = getStaticItemName(armor[idx]);
        if (utils.isEquipped(armor[idx]))
        {
            prose_package pp = new prose_package();
            prose.setTT(pp, "@" + getName(armor[idx]));
            prose.setStringId(pp, new string_id("tool/customizer", "unequip_first"));
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, COLORS, "handleColorSelection", true, false);
        if (pid < 0)
        {
            cleanup(self, player);
        }
        else 
        {
            utils.setScriptVar(self, "cadk_piece", armor[idx]);
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
        obj_id piece = utils.getObjIdScriptVar(self, "cadk_piece");
        if (utils.isEquipped(piece))
        {
            prose_package pp = new prose_package();
            prose.setTT(pp, "@" + getName(piece));
            prose.setStringId(pp, new string_id("tool/customizer", "unequip_first"));
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        boolean worn = false;
        if (utils.hasScriptVar(piece, "cadk_worn"))
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
            sendSystemMessage(player, new string_id("tool/customizer", "cadk_success"));
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
        utils.removeScriptVar(self, "cadk_armor");
        utils.removeScriptVar(self, "cadk_piece");
        obj_id[] equippedItems = metrics.getWornItems(player);
        if (equippedItems != null && equippedItems.length > 0)
        {
            for (int i = 0; i < equippedItems.length; i++)
            {
                utils.removeScriptVar(equippedItems[i], "cadk_worn");
            }
        }
    }
    public String constructNewArmorString(obj_id armor, int idx) throws InterruptedException
    {
        String name = getStaticItemName(armor);
        String faction = null;
        String type = null;
        String color = null;
        for (int i = 0; i < ARMOR_TYPES.length; i++)
        {
            if (name.indexOf(ARMOR_TYPES[i]) > -1)
            {
                type = ARMOR_TYPES[i];
            }
        }
        if (name.indexOf("imperial") > -1)
        {
            faction = "imperial_";
        }
        else if (name.indexOf("rebel") > -1)
        {
            faction = "rebel_";
        }
        switch (idx)
        {
            case 0:
            color = "";
            break;
            case 1:
            color = "white_";
            break;
            case 2:
            color = "black_";
            break;
            case 3:
            color = "red_";
            break;
            case 4:
            color = "blue_";
            break;
            case 5:
            color = "green_";
            break;
        }
        if (type == null || faction == null || color == null)
        {
            return null;
        }
        return "armor_mandalorian_" + faction + color + type + "_04_01";
    }
}
