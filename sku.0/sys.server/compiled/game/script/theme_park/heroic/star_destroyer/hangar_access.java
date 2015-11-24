package script.theme_park.heroic.star_destroyer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class hangar_access extends script.base_script
{
    public hangar_access()
    {
    }
    public static final int STATE_NONE = 0;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DONE = 2;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        int state = getEventState(self);
        switch (state)
        {
            case STATE_NONE:
            item.addRootMenu(menu_info_types.ITEM_USE, new string_id("instance", "hangar_state_none"));
            break;
            case STATE_ACTIVE:
            item.addRootMenu(menu_info_types.ITEM_USE, new string_id("instance", "hangar_state_active"));
            break;
            case STATE_DONE:
            item.addRootMenu(menu_info_types.ITEM_USE, new string_id("instance", "hangar_state_complete"));
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        int state = getEventState(self);
        trial.bumpSession(self, "key");
        sendDirtyObjectMenuNotification(self);
        switch (state)
        {
            case STATE_NONE:
            messageTo(self, "kickoff_event", trial.getSessionDict(self, "key"), 1.0f, false);
            break;
            case STATE_ACTIVE:
            break;
            case STATE_DONE:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int getEventState(obj_id self) throws InterruptedException
    {
        return getIntObjVar(self, "event_state");
    }
    public boolean isInactive(obj_id self) throws InterruptedException
    {
        return getEventState(self) == STATE_NONE;
    }
    public boolean isActive(obj_id self) throws InterruptedException
    {
        return getEventState(self) == STATE_ACTIVE;
    }
    public boolean isComplete(obj_id self) throws InterruptedException
    {
        return getEventState(self) == STATE_DONE;
    }
    public int kickoff_event(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "key"))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "spawn_krix");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
}
