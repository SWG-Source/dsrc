package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.colors;

public class cell_terminal extends script.base_script
{
    public cell_terminal()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String ACTIVE = "cell_locked";
    public static final String INACTIVE = "cell_open";
    public static final String DEACTIVATE = "cell_unlocked";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String stringName = ACTIVE;
        obj_id bldg = getTopMostContainer(self);
        if (utils.hasScriptVar(bldg, "warren.cellOpened"))
        {
            stringName = INACTIVE;
        }
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 10.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        sui.msgbox(player, new string_id(SYSTEM_MESSAGES, stringName));
        return SCRIPT_OVERRIDE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "lockDoor", null, 1800, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isPlayer(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bldg = getTopMostContainer(self);
        if (utils.hasScriptVar(bldg, "warren.cellOpened"))
        {
            return SCRIPT_CONTINUE;
        }
        String turretCodeSequence = getStringObjVar(bldg, "warren.cellPassword");
        if (turretCodeSequence == null)
        {
            return SCRIPT_CONTINUE;
        }
        if ((toUpper(text)).equals(turretCodeSequence))
        {
            location here = getLocation(speaker);
            location term = getLocation(self);
            float range = getDistance(here, term);
            if (range > 10.0)
            {
                sendSystemMessage(speaker, new string_id(SYSTEM_MESSAGES, "elev_range"));
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(bldg, "warren.cellOpened", true);
            showFlyText(self, new string_id(SYSTEM_MESSAGES, INACTIVE), 1.75f, colors.GREENYELLOW);
            permissionsMakePublic(getCellId(getTopMostContainer(self), "smallroom47"));
            messageTo(self, "lockDoor", null, 1800, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int lockDoor(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        utils.removeScriptVar(bldg, "warren.cellOpened");
        permissionsMakePrivate(getCellId(getTopMostContainer(self), "smallroom47"));
        return SCRIPT_CONTINUE;
    }
}
