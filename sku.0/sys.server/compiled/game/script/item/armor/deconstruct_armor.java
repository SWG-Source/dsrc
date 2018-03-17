package script.item.armor;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.craftinglib;
import script.library.metrics;
import script.library.utils;
import script.library.prose;
import script.library.static_item;
import script.library.sui;

public class deconstruct_armor extends script.base_script
{
    public deconstruct_armor()
    {
    }
    public static final String PID_NAME = "armorDeconstruct";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isEquipped(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (static_item.isDynamicItem(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bioLink = getBioLink(self);
        if (isIdValid(bioLink) && utils.stringToLong(bioLink.toString()) != 1)
        {
            if (bioLink != player)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int management_root = item.addRootMenu(menu_info_types.SERVER_MENU5, armor.SID_ARMOR_TO_SCHEM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU5)
        {
            if (!utils.isNestedWithinAPlayer(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.isEquipped(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (static_item.isDynamicItem(self))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id bioLink = getBioLink(self);
            if (isIdValid(bioLink) && utils.stringToLong(bioLink.toString()) != 1)
            {
                if (bioLink != player)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (sui.hasPid(player, PID_NAME))
            {
                int pid = sui.getPid(player, PID_NAME);
                forceCloseSUIPage(pid);
            }
            int pid = sui.inputbox(self, player, "@" + armor.SID_CONVERT_PROMPT, sui.OK_CANCEL, "@" + armor.SID_CONVERT_TITLE, sui.INPUT_NORMAL, null, "handleConvertSchemSui");
            sui.setPid(player, pid, PID_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConvertSchemSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            forceCloseSUIPage(pageId);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") has somehow gotten two conversion windows while attempting to convert their old armor(" + self + ") to a new cored schematic. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") has somehow gotten two conversion windows while attempting to convert their old armor(" + self + ") to a new cored schematic. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") has moved their weapon into a container that is not a player while attempting to convert their old armor(" + self + ") to a new cored schematic. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        if (utils.isEquipped(self))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") has equipped their old armor(" + self + ") while attempting to convert it int a new cored schematic. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        obj_id playerContained = utils.getContainingPlayer(self);
        if (playerContained != player)
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") traded their old armor(" + self + ") to " + getFirstName(playerContained) + "(" + playerContained + ") while attempting to convert it int a new cored schematic. They are prolly trying to exploit, bailing out now");
            return SCRIPT_CONTINUE;
        }
        obj_id bioLink = getBioLink(self);
        if (isIdValid(bioLink) && utils.stringToLong(bioLink.toString()) != 1)
        {
            if (bioLink != player)
            {
                forceCloseSUIPage(pid);
                sui.removePid(player, PID_NAME);
                CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") tried to deconstruct an armor piece(" + self + ") that is biolinked to " + getFirstName(bioLink) + "(" + bioLink + "). They are prolly trying to exploit, bailing out now");
                return SCRIPT_CONTINUE;
            }
        }
        String response = sui.getInputBoxText(params);
        if (!(response.toLowerCase()).equals("deconstruct"))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            sendSystemMessage(player, armor.SID_CONVERT_INVALID_RESPONSE);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") has entered '" + response + "' instead of 'deconstruct' so the armor will not be deconstructed.");
            return SCRIPT_CONTINUE;
        }
        if (armor.turnArmorIntoSchem(player, self))
        {
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") has decided to convert their old armor(" + self + ") to a new cored schematic.");
            sui.removePid(player, PID_NAME);
            sendSystemMessage(player, armor.SID_CONVERT_CONVERT_SUCCESS);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, armor.SID_CONVERT_CONVERT_FAIL);
            CustomerServiceLog("armor_conversion", "Player " + getFirstName(player) + "(" + player + ") attempted to convert their old armor(" + self + ") to a new cored schematic, and it failed.");
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
    }
}
