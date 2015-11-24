package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class animate_object extends script.base_script
{
    public animate_object()
    {
    }
    public static final String LOCKOUT = "animation_lockout";
    public static final String LOCKOUT_TIME = "lockout_time";
    public static final String USE_STRING = "use_string";
    public static final String ANIMATION_NAME = "animation_name";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            String use_string = getStringObjVar(self, USE_STRING);
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", use_string));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, LOCKOUT))
        {
            sendSystemMessage(player, new string_id("spam", "in_use"));
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            String animationName = getStringObjVar(self, ANIMATION_NAME);
            doAnimationAction(self, animationName);
            if (hasObjVar(self, LOCKOUT_TIME))
            {
                int lockoutTime = getIntObjVar(self, LOCKOUT_TIME);
                utils.setScriptVar(self, LOCKOUT, 1);
                messageTo(self, "animationLockout", null, lockoutTime, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int animationLockout(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, LOCKOUT))
        {
            utils.removeScriptVar(self, LOCKOUT);
        }
        return SCRIPT_CONTINUE;
    }
}
