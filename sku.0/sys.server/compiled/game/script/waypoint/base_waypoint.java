package script.waypoint;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class base_waypoint extends script.base_script
{
    public base_waypoint()
    {
    }
    public static final string_id MNU_SET_COLOR = new string_id("sui", "set_color");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnuSetColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_SET_COLOR);
        for (int i = 0; i < utils.WAYPOINT_COLORS.length; i++)
        {
            mi.addSubMenu(mnuSetColor, menu_info_types.SERVER_MENU1 + i + 1, new string_id("sui", utils.WAYPOINT_COLORS[i]));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int max = menu_info_types.SERVER_MENU1 + utils.WAYPOINT_COLORS.length + 1;
        if (item > menu_info_types.SERVER_MENU1 && item < max)
        {
            int idx = item - menu_info_types.SERVER_MENU1 - 1;
            setWaypointColor(self, utils.WAYPOINT_COLORS[idx]);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        clearClientPath(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        clearClientPath(self);
        return SCRIPT_CONTINUE;
    }
    public void clearClientPath(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "hasClientPath"))
        {
            obj_id target = getObjIdObjVar(self, "hasClientPath");
            if (isIdValid(target))
            {
                destroyClientPath(target);
            }
        }
    }
    public int OnSetWaypointActive(obj_id self, dictionary params) throws InterruptedException
    {
        boolean isActive = params.getBoolean("isActive");
        obj_id waypoint = params.getObjId("waypoint");
        if (waypoint != null)
        {
            _setWaypointActiveNative(waypoint, isActive);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSetWaypointRegion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id w = params.getObjId("waypoint");
        region r = params.getRegion("region");
        if (w != null)
        {
            if (r != null)
            {
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSetWaypoinVisible(obj_id self, dictionary params) throws InterruptedException
    {
        boolean isVisible = params.getBoolean("isVisible");
        obj_id waypoint = params.getObjId("waypoint");
        if (waypoint != null)
        {
        }
        return SCRIPT_CONTINUE;
    }
}
