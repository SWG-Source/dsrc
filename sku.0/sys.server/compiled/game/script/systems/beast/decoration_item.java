package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.create;
import script.library.hue;
import script.library.incubator;
import script.library.sui;
import script.library.utils;

public class decoration_item extends script.base_script
{
    public decoration_item()
    {
    }
    public static final string_id SID_CONVERT_PET_ITEM_TO_DNA = new string_id("incubator", "convert_pet_item_to_dna");
    public static final string_id SID_CONVERT_PROMPT = new string_id("incubator", "convert_pet_item_prompt");
    public static final string_id SID_CONVERT_TITLE = new string_id("incubator", "convert_pet_item_title");
    public static final string_id SID_REPORT_PET_CONVERSION_FAIL = new string_id("incubator", "report_pet_conversion_fail");
    public static final string_id PCOLOR = new string_id("sui", "set_primary_color");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self))
        {
            if (beast_lib.isBeastMaster(player))
            {
                int management_root = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_CONVERT_PET_ITEM_TO_DNA);
            }
        }
        if (!hasObjVar(self, beast_lib.OBJVAR_OLD_PET_REHUED) && getOwner(self) == player)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, PCOLOR);
        }
        if (!hasObjVar(self, beast_lib.OBJVAR_OLD_PET_RENAMED) && getOwner(self) == player)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("beast", "name_beast"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.SERVER_MENU1 && utils.isNestedWithinAPlayer(self))
        {
            if (beast_lib.isBeastMaster(player))
            {
                int pid = sui.msgbox(self, player, "@" + SID_CONVERT_PROMPT, sui.YES_NO, "@" + SID_CONVERT_TITLE, "handlerSuiConvertPetItemToDna");
                return SCRIPT_CONTINUE;
            }
        }
        if (item == menu_info_types.SERVER_MENU2 && getOwner(self) == player)
        {
            if (!hasObjVar(self, beast_lib.OBJVAR_OLD_PET_REHUED))
            {
                sui.colorize(self, player, self, hue.INDEX_1, "handlePrimaryColorize");
            }
        }
        if (item == menu_info_types.SERVER_MENU3 && !hasObjVar(self, beast_lib.OBJVAR_OLD_PET_RENAMED) && getOwner(self) == player)
        {
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiConvertPetItemToDna(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id petItem = self;
        if (beast_lib.isBeastMaster(player))
        {
            if (incubator.convertPetItemToDna(player, petItem))
            {
                CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was converted into a dna object, for player " + getFirstName(player) + "(" + player + "). PetItem(" + petItem + ") will now be destroyed.");
                destroyObject(petItem);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was NOT converted into a dna object, for player " + getFirstName(player) + "(" + player + "). PetItem(" + petItem + ") will NOT be destroyed.");
                sendSystemMessage(player, SID_REPORT_PET_CONVERSION_FAIL);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePrimaryColorize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            setObjVar(self, beast_lib.OBJVAR_BEAST_HUE, idx);
            hue.setColor(self, "/private/index_color_1", idx);
            setObjVar(self, beast_lib.OBJVAR_OLD_PET_REHUED, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetBeastName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String beastName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (beastName.equals("") || isNameReserved(beastName))
        {
            sendSystemMessage(player, new string_id("player_structure", "obscene"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        if (beastName.length() < 3)
        {
            sendSystemMessage(player, new string_id("beast", "name_too_short"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        if (beastName.length() > 40)
        {
            sendSystemMessage(player, new string_id("beast", "name_too_long"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        setName(self, beastName);
        setObjVar(self, beast_lib.OBJVAR_OLD_PET_RENAMED, 1);
        setObjVar(self, incubator.DNA_PARENT_NAME, beastName);
        return SCRIPT_CONTINUE;
    }
}
