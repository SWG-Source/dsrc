package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.colors;

public class switch_two extends script.base_script
{
    public switch_two()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "switch_name_2"));
        messageTo(self, "handleResetSwitches", null, 600, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "switch_name_2"));
        obj_id bldg = getTopMostContainer(self);
        if (!isIdValid(bldg))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 10.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(bldg, "warren.reactorRoomUnlocked"))
        {
            showFlyText(self, new string_id(SYSTEM_MESSAGES, "switch_done"), 1.75f, colors.RED);
            return SCRIPT_CONTINUE;
        }
        showFlyText(self, new string_id(SYSTEM_MESSAGES, "switch_two"), 1.75f, colors.GREENYELLOW);
        utils.setScriptVar(bldg, "warren.coreSwitchTwo", true);
        if (utils.hasScriptVar(bldg, "warren.coreSwitchOne"))
        {
            messageTo(self, "handleDelayedDoneMessage", null, 3, false);
            utils.setScriptVar(bldg, "warren.reactorRoomUnlocked", true);
            messageTo(self, "handleResetSwitches", null, 1800, false);
            permissionsMakePublic(getCellId(getTopMostContainer(self), "smallroom44"));
        }
        return SCRIPT_CONTINUE;
    }
    public int handleResetSwitches(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        if (!isIdValid(bldg))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(bldg, "warren.reactorRoomUnlocked");
        utils.removeScriptVar(bldg, "warren.coreSwitchOne");
        utils.removeScriptVar(bldg, "warren.coreSwitchTwo");
        permissionsMakePrivate(getCellId(getTopMostContainer(self), "smallroom44"));
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedDoneMessage(obj_id self, dictionary params) throws InterruptedException
    {
        showFlyText(self, new string_id(SYSTEM_MESSAGES, "switch_done"), 1.75f, colors.RED);
        return SCRIPT_CONTINUE;
    }
}
