package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.static_item;
import script.library.target_dummy;
import script.library.utils;
import java.util.Enumeration;

public class target_creature_deed extends script.base_script
{
    public target_creature_deed()
    {
    }
    public static final int PERIODIC_PURGE_CHECK = 600;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlePeriodicallyPurgeData", null, PERIODIC_PURGE_CHECK, false);
        target_dummy.setTargetDummyOwner(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlePeriodicallyPurgeData", null, PERIODIC_PURGE_CHECK, false);
        if (hasObjVar(self, target_dummy.BASE_TARGET_DUMMY_VAR))
        {
            removeObjVar(self, target_dummy.BASE_TARGET_DUMMY_VAR);
        }
        obj_id owner = target_dummy.getTargetDummyOwnerFromController(self);
        if (!isIdValid(owner))
        {
            owner = target_dummy.setTargetDummyOwner(self);
        }
        if (hasObjVar(self, target_dummy.TARGET_DUMMY_ID_OBJVAR))
        {
            obj_id previousTargetDummy = getObjIdObjVar(self, target_dummy.TARGET_DUMMY_ID_OBJVAR);
            if (isIdValid(previousTargetDummy))
            {
                if (!exists(previousTargetDummy))
                {
                    target_dummy.removeTargetDummyFromPermissionsViaController(self, previousTargetDummy);
                    if (target_dummy.controllerContainmentCheck(self))
                    {
                        target_dummy.recreateTargetDummy(self, owner);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        removeObjVar(self, target_dummy.TARGET_DUMMY_ID_OBJVAR);
                    }
                }
            }
            else 
            {
                removeObjVar(self, target_dummy.TARGET_DUMMY_ID_OBJVAR);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isInWorldCell(self) || isInWorldCell(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = target_dummy.getTargetDummyOwnerFromController(self);
        if (isIdValid(owner) && player == owner)
        {
            if (target_dummy.controllerContainmentCheck(self))
            {
                String creatureName = getStringObjVar(self, target_dummy.CREATURE_NAME_OBJVAR);
                if (creatureName == null || creatureName.equals(""))
                {
                    return SCRIPT_CONTINUE;
                }
                if (hasObjVar(self, target_dummy.TARGET_DUMMY_ID_OBJVAR))
                {
                    int menuPlacement = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("target_dummy", "menu_cleanup_" + creatureName));
                }
                else 
                {
                    int menuPlacement = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("target_dummy", "menu_place_" + creatureName));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isInWorldCell(self) || isInWorldCell(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = target_dummy.getTargetDummyOwnerFromController(self);
        if (isIdValid(owner) && player == owner)
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                if (target_dummy.controllerContainmentCheck(self))
                {
                    if (target_dummy.createTargetDummy(self, player) == null)
                    {
                        if (exists(player))
                        {
                            sendSystemMessage(player, new string_id("target_dummy", "failed_to_create_npc"));
                        }
                    }
                    else 
                    {
                        sendDirtyObjectMenuNotification(self);
                    }
                }
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                obj_id targetDummy = target_dummy.getTargetDummyId(self);
                if (isIdValid(targetDummy))
                {
                    target_dummy.cleanupTargetDummy(self, targetDummy);
                    sendDirtyObjectMenuNotification(self);
                }
            }
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!target_dummy.controllerContainmentCheck(self))
        {
            obj_id targetDummy = target_dummy.getTargetDummyId(self);
            if (isIdValid(targetDummy))
            {
                target_dummy.cleanupTargetDummy(self, targetDummy);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id targetDummy = target_dummy.getTargetDummyId(self);
        if (isIdValid(targetDummy))
        {
            target_dummy.cleanupTargetDummy(self, targetDummy);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePeriodicallyPurgeData(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handlePeriodicallyPurgeData", null, PERIODIC_PURGE_CHECK, false);
        deltadictionary vars = self.getScriptVars();
        Enumeration keys = vars.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)(keys.nextElement());
            int stringCheck = key.indexOf(target_dummy.LAST_ATTACK_TIME_VAR);
            if (stringCheck > -1)
            {
                int elapsedTime = getGameTime() - utils.getIntScriptVar(self, key);
                if (elapsedTime > target_dummy.PURGE_DATA_INTERVAL)
                {
                    String[] data = split(key, '.');
                    utils.removeScriptVarTree(self, data[0] + "." + data[1]);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
