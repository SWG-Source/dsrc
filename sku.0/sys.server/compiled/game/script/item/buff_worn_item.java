package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.buff;
import script.library.static_item;
import script.library.prose;
import script.library.armor;

public class buff_worn_item extends script.base_script
{
    public buff_worn_item()
    {
    }
    public static final String NON_ENHANCEMENT_BUFF = "buff.non_enhancement";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (isPlayer(getContainedBy(self)))
        {
            static_item.applyWornBuffs(self, getContainedBy(self));
            static_item.checkForAddSetBonus(self, getContainedBy(self));
        }
        else if (isAPlayerAppearanceInventoryContainer(getContainedBy(self)) && hasObjVar(self, NON_ENHANCEMENT_BUFF))
        {
            obj_id player = utils.getContainingPlayer(self);
            static_item.applyWornBuffs(self, player);
            static_item.checkForAddSetBonus(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id player = destContainer;
        if (isIdValid(destContainer) && isPlayer(destContainer))
        {
            static_item.applyWornBuffs(self, destContainer);
            static_item.checkForAddSetBonus(self, destContainer);
        }
        else if (isIdValid(destContainer) && isAPlayerAppearanceInventoryContainer(destContainer) && hasObjVar(self, NON_ENHANCEMENT_BUFF))
        {
            player = utils.getContainingPlayer(destContainer);
            if (!isValidId(player))
            {
                return SCRIPT_CONTINUE;
            }
            static_item.applyWornBuffs(self, player);
            static_item.checkForAddSetBonus(self, player);
        }
        else if (isIdValid(sourceContainer) && isPlayer(sourceContainer))
        {
            player = sourceContainer;
            static_item.removeWornBuffs(self, sourceContainer);
            static_item.checkForRemoveSetBonus(self, sourceContainer);
        }
        else if (isIdValid(sourceContainer) && isAPlayerAppearanceInventoryContainer(sourceContainer))
        {
            player = utils.getContainingPlayer(sourceContainer);
            if (!isValidId(player))
            {
                return SCRIPT_CONTINUE;
            }
            static_item.removeWornBuffs(self, player);
            static_item.checkForRemoveSetBonus(self, player);
        }
        if (isIdValid(self) && hasObjVar(self, "armor.general_protection_clothing") && (getContainedBy(self) == sourceContainer || getContainedBy(self) == destContainer))
        {
            armor.recalculatePseudoArmorForPlayer(player, self, isPlayer(destContainer));
        }
        if (isIdValid(self) && hasObjVar(self, "armor.fake_armor") && (getContainedBy(self) == sourceContainer || getContainedBy(self) == destContainer))
        {
            armor.recalculateArmorForPlayer(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isIdValid(item))
        {
            obj_id player = utils.getContainingPlayer(item);
            static_item.removeWornBuffs(item, player);
            static_item.checkForRemoveSetBonus(item, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        static_item.removeWornBuffs(self, player);
        static_item.checkForRemoveSetBonus(self, player);
        if (getContainedBy(self) == player)
        {
            armor.recalculatePseudoArmorForPlayer(player, self, false);
        }
        return SCRIPT_CONTINUE;
    }
}
