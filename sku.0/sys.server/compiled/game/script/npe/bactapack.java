package script.npe;

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

public class bactapack extends script.base_script
{
    public bactapack()
    {
    }
    public static final string_id SID_ITEM_YOU_MUST_TARGET = new string_id("healing", "item_must_target");
    public static final string_id SID_ITEM_ONLY_OTHERS = new string_id("healing", "item_only_on_others");
    public static final string_id SID_ITEM_NO_NEED = new string_id("healing", "item_no_need");
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
            if (!hasObjVar(target, "quest.questName"))
            {
                sendSystemMessage(player, SID_ITEM_NO_NEED);
                return SCRIPT_CONTINUE;
            }
            String questName = getStringObjVar(target, "quest.questName");
            if (!questName.equals("npe_medic") && !questName.equals("npe_medic2"))
            {
                sendSystemMessage(player, SID_ITEM_NO_NEED);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.questActionCompleted(player, target, ACTION_NAME))
            {
                decrementCount(self);
                healing.doHealingAnimationAndEffect(player, target);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
