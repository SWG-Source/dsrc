package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.stealth;
import script.library.sui;

public class trap extends script.systems.combat.combat_base
{
    public trap()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "armed") && getIntObjVar(self, "armed") == 1)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "armed"))
        {
            sendSystemMessage(transferer, new string_id("spam", "cant_pick_up_armed"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        stealth.onGetTrapAttributes(self, player, names, attribs, getFirstFreeIndex(names));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, true, "trap."))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemLevelRequirements(player, self, true, "trap."))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, true, "trap.trigger."))
        {
            LOG("stealth", "Failed trigger ability requirement check");
            return SCRIPT_CONTINUE;
        }
        if (!utils.testItemLevelRequirements(player, self, true, "trap.trigger."))
        {
            LOG("stealth", "Failed trigger skill requirement check");
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        int triggerType = getIntObjVar(self, stealth.TRIGGER_TYPE);
        switch (triggerType)
        {
            case stealth.TRIGGER_TYPE_TIMER:
            mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id("spam", "set_time"));
            break;
            case stealth.TRIGGER_TYPE_PROXIMITY:
            mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id("spam", "set_trigger_distance"));
            break;
            case stealth.TRIGGER_TYPE_REMOTE:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, false, "trap."))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.testItemLevelRequirements(player, self, false, "trap."))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasObjVar(self, stealth.TRIGGER_TYPE))
            {
                sendSystemMessage(player, new string_id("spam", "trap_no_trigger_installed"));
                return SCRIPT_CONTINUE;
            }
            if (!stealth.canSetTrap(player, self))
            {
                return SCRIPT_CONTINUE;
            }
            stealth.setTrap(player, self);
            return SCRIPT_CONTINUE;
        }
        int triggerType = getIntObjVar(self, stealth.TRIGGER_TYPE);
        if (item == menu_info_types.SERVER_MENU5 && triggerType == stealth.TRIGGER_TYPE_TIMER)
        {
            String[] items = new String[]
            {
                "@spam:seconds_3",
                "@spam:seconds_5",
                "@spam:seconds_7",
                "@spam:seconds_10",
                "@spam:seconds_15",
                "@spam:seconds_20",
                "@spam:seconds_30",
                "@spam:seconds_45",
                "@spam:seconds_60"
            };
            forceCloseSUIPage(utils.getIntScriptVar(player, "trapSui"));
            utils.setScriptVar(player, "trapSui", sui.listbox(self, player, "@spam:select_timer", "@spam:trigger_setup", items, "msgTimerSetup"));
        }
        else if (item == menu_info_types.SERVER_MENU6 && triggerType == stealth.TRIGGER_TYPE_PROXIMITY)
        {
            String[] items = new String[]
            {
                "@spam:meters_1",
                "@spam:meters_5",
                "@spam:meters_10"
            };
            forceCloseSUIPage(utils.getIntScriptVar(player, "trapSui"));
            utils.setScriptVar(player, "trapSui", sui.listbox(self, player, "@spam:proximity_range", "@spam:trigger_setup", items, "msgProximitySetup"));
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSelfDestructTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        stealth.playSelfDestruct(self);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int msgTryDetonateTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int msgArmedAt = params.getInt("armed");
        int trapArmedAt = -1;
        if (hasObjVar(self, "armed"))
        {
            trapArmedAt = getIntObjVar(self, "armed");
        }
        if (msgArmedAt != trapArmedAt)
        {
            return SCRIPT_CONTINUE;
        }
        stealth.tryDetonateTrap(self, null);
        return SCRIPT_CONTINUE;
    }
    public int msgTryRemoteDetonate(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, stealth.TRAP_MY_PROXIMITY_TRIGGER))
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getObjId("sender") != getObjIdObjVar(self, stealth.TRAP_MY_PROXIMITY_TRIGGER))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id trig = getObjIdObjVar(self, stealth.TRAP_MY_PROXIMITY_TRIGGER);
        if (stealth.tryDetonateTrap(self, null))
        {
            messageTo(trig, "msgDetonated", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgArmProximity(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume(stealth.TRAP_PROXIMITY_VOLUME, getIntObjVar(self, stealth.PROXIMITY_RANGE), true);
        messageTo(self, "msgSetArmed", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int msgSetArmed(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "armed", params.getInt("armed"));
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!volumeName.equals(stealth.TRAP_PROXIMITY_VOLUME))
        {
            return SCRIPT_CONTINUE;
        }
        if (!stealth.validateTrapTarget(self, breacher))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id filter = stealth.getBioProbeTarget(self);
        if (isIdValid(filter) && filter != breacher)
        {
            return SCRIPT_CONTINUE;
        }
        stealth.tryDetonateTrap(self, breacher);
        return SCRIPT_CONTINUE;
    }
    public int msgProximitySetup(obj_id self, dictionary params) throws InterruptedException
    {
        int triggerType = getIntObjVar(self, stealth.TRIGGER_TYPE);
        if (triggerType != stealth.TRIGGER_TYPE_PROXIMITY)
        {
            return SCRIPT_CONTINUE;
        }
        int row = sui.getListboxSelectedRow(params);
        int distance = 1;
        switch (row)
        {
            case 0:
            distance = 1;
            break;
            case 1:
            distance = 5;
            break;
            case 2:
            distance = 10;
            break;
        }
        setObjVar(self, stealth.PROXIMITY_RANGE, distance);
        obj_id container = getContainedBy(self);
        if (isIdValid(container) && hasObjVar(container, stealth.TRAP_TYPE))
        {
            setObjVar(container, stealth.PROXIMITY_RANGE, distance);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgTimerSetup(obj_id self, dictionary params) throws InterruptedException
    {
        int triggerType = getIntObjVar(self, stealth.TRIGGER_TYPE);
        if (triggerType != stealth.TRIGGER_TYPE_TIMER)
        {
            return SCRIPT_CONTINUE;
        }
        int row = sui.getListboxSelectedRow(params);
        int seconds = 10;
        switch (row)
        {
            case 0:
            seconds = 3;
            break;
            case 1:
            seconds = 5;
            break;
            case 2:
            seconds = 7;
            break;
            case 3:
            seconds = 10;
            break;
            case 4:
            seconds = 15;
            break;
            case 5:
            seconds = 20;
            break;
            case 6:
            seconds = 30;
            break;
            case 7:
            seconds = 45;
            break;
            case 8:
            seconds = 60;
            break;
            case -1:
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, stealth.TIMER_TIME, seconds);
        obj_id container = getContainedBy(self);
        if (isIdValid(container) && hasObjVar(container, stealth.TRAP_TYPE))
        {
            setObjVar(container, stealth.TIMER_TIME, seconds);
        }
        return SCRIPT_CONTINUE;
    }
    public int co_mine_hx2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        obj_id mineOwner = params.getObjId("owner");
        combatStandardAction("co_mine_hx2", mineOwner, target, self, "", null, false);
        return SCRIPT_CONTINUE;
    }
}
