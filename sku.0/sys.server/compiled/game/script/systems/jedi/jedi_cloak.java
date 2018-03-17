package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.jedi;
import script.library.prose;
import script.library.static_item;
import script.library.utils;
import script.library.reverse_engineering;

public class jedi_cloak extends script.base_script
{
    public jedi_cloak()
    {
    }
    public static final string_id SID_USE_HOOD_UP = new string_id("spam", "hood_up");
    public static final string_id SID_USE_HOOD_DOWN = new string_id("spam", "hood_down");
    public static final string_id SID_NO_TWO_CLOAK = new string_id("spam", "no_two_cloak");
    public static final String OBJVAR_BEING_REPLACED = "aboutToBeReplaced";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_BEING_REPLACED))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(destContainer);
        if (isIdValid(player))
        {
            if (jedi.hasAnyUltraCloak(player))
            {
                obj_id existingCloak = utils.getStaticItemInBankOrInventory(player, jedi.JEDI_CLOAK_LIGHT_HOOD_UP);
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInBankOrInventory(player, jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN);
                }
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInBankOrInventory(player, jedi.JEDI_CLOAK_DARK_HOOD_UP);
                }
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInBankOrInventory(player, jedi.JEDI_CLOAK_DARK_HOOD_DOWN);
                }
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInAppearanceInventory(player, jedi.JEDI_CLOAK_LIGHT_HOOD_UP);
                }
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInAppearanceInventory(player, jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN);
                }
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInAppearanceInventory(player, jedi.JEDI_CLOAK_DARK_HOOD_UP);
                }
                if (!isIdValid(existingCloak) || existingCloak == self)
                {
                    existingCloak = utils.getStaticItemInAppearanceInventory(player, jedi.JEDI_CLOAK_DARK_HOOD_DOWN);
                }
                if (exists(existingCloak) && isIdValid(existingCloak) && existingCloak != self)
                {
                    if (!hasObjVar(existingCloak, OBJVAR_BEING_REPLACED) && !hasObjVar(self, OBJVAR_BEING_REPLACED))
                    {
                        sendSystemMessage(player, SID_NO_TWO_CLOAK);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isEquipped(self))
        {
            String itemName = getStaticItemName(self);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
            else 
            {
                if (itemName.equals(jedi.JEDI_CLOAK_LIGHT_HOOD_UP) || itemName.equals(jedi.JEDI_CLOAK_DARK_HOOD_UP))
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE_HOOD_DOWN);
                }
                else if (itemName.equals(jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN) || itemName.equals(jedi.JEDI_CLOAK_DARK_HOOD_DOWN))
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE_HOOD_UP);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isEquipped(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id pInv = utils.getInventoryContainer(player);
            String itemName = getStaticItemName(self);
            boolean isAppearanceItem = isAPlayerAppearanceInventoryContainer(getContainedBy(self));
            setObjVar(self, OBJVAR_BEING_REPLACED, true);
            if (itemName.equals(jedi.JEDI_CLOAK_LIGHT_HOOD_UP) || itemName.equals(jedi.JEDI_CLOAK_DARK_HOOD_UP))
            {
                obj_id newRobe = null;
                if (itemName.equals(jedi.JEDI_CLOAK_LIGHT_HOOD_UP))
                {
                    newRobe = static_item.createNewItemFunction(jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN, pInv);
                }
                if (itemName.equals(jedi.JEDI_CLOAK_DARK_HOOD_UP))
                {
                    newRobe = static_item.createNewItemFunction(jedi.JEDI_CLOAK_DARK_HOOD_DOWN, pInv);
                }
                if (isIdValid(newRobe))
                {
                    if (reverse_engineering.isPoweredUpItem(self) && !isAppearanceItem)
                    {
                        float remainingTime = getFloatObjVar(self, reverse_engineering.ENGINEERING_TIMESTAMP);
                        reverse_engineering.removePlayerPowerUpMods(player, self);
                        reverse_engineering.addModsAndScript(player, self, newRobe, remainingTime);
                    }
                    utils.setScriptVar(newRobe, static_item.NO_SET_ITEM_EQUIP_SPAM, 1);
                    putInOverloaded(self, pInv);
                    equip(newRobe, player);
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
            }
            if (itemName.equals(jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN) || itemName.equals(jedi.JEDI_CLOAK_DARK_HOOD_DOWN))
            {
                obj_id hat = getObjectInSlot(player, "hat");
                if (isIdValid(hat) && !isAppearanceItem)
                {
                    putInOverloaded(hat, pInv);
                }
                if (isAppearanceItem)
                {
                    obj_id appearanceHat = getObjectInSlot(getAppearanceInventory(player), "hat");
                    if (isIdValid(appearanceHat))
                    {
                        putInOverloaded(appearanceHat, pInv);
                    }
                }
                obj_id newRobe = null;
                if (itemName.equals(jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN))
                {
                    newRobe = static_item.createNewItemFunction(jedi.JEDI_CLOAK_LIGHT_HOOD_UP, pInv);
                }
                if (itemName.equals(jedi.JEDI_CLOAK_DARK_HOOD_DOWN))
                {
                    newRobe = static_item.createNewItemFunction(jedi.JEDI_CLOAK_DARK_HOOD_UP, pInv);
                }
                if (isIdValid(newRobe))
                {
                    if (reverse_engineering.isPoweredUpItem(self) && !isAppearanceItem)
                    {
                        float remainingTime = getFloatObjVar(self, reverse_engineering.ENGINEERING_TIMESTAMP);
                        reverse_engineering.removePlayerPowerUpMods(player, self);
                        reverse_engineering.addModsAndScript(player, self, newRobe, remainingTime);
                    }
                    utils.setScriptVar(newRobe, static_item.NO_SET_ITEM_EQUIP_SPAM, 1);
                    putInOverloaded(self, pInv);
                    equip(newRobe, player);
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
