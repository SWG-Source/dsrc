package script.item.medicine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.groundquests;
import script.library.sui;
import script.library.utils;
import script.library.healing;

public class stimpack_other extends script.base_script
{
    public stimpack_other()
    {
    }
    public static final string_id SID_ITEM_YOU_MUST_TARGET = new string_id("healing", "item_must_target");
    public static final string_id SID_ITEM_ONLY_OTHERS = new string_id("healing", "item_only_on_others");
    public static final String ACTION_NAME = "bactaShot";
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
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(player, "feign_death"))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            final obj_id target = getLookAtTarget(player);
            if (target == null)
            {
                sendSystemMessage(player, SID_ITEM_YOU_MUST_TARGET);
                return SCRIPT_CONTINUE;
            }
            if (target == player)
            {
                sendSystemMessage(player, SID_ITEM_ONLY_OTHERS);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.questActionCompleted(player, target, ACTION_NAME))
            {
                decrementCount(self);
                healing.doHealingAnimationAndEffect(player, target);
            }
            else 
            {
                healing.useHealDamageItem(player, target, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
