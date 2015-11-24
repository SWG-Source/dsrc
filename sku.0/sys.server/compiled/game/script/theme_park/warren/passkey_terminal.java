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

public class passkey_terminal extends script.base_script
{
    public passkey_terminal()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String ACTIVE = "turret_pass_active";
    public static final String INACTIVE = "turret_pass_inactive";
    public static final String DEACTIVATE = "turret_pass_deactivated";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "turret_term_on"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 5.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        String stringName = ACTIVE;
        obj_id bldg = getTopMostContainer(self);
        if (utils.hasScriptVar(bldg, "warren.deactivated"))
        {
            stringName = INACTIVE;
        }
        sui.msgbox(player, new string_id(SYSTEM_MESSAGES, stringName));
        return SCRIPT_OVERRIDE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isPlayer(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bldg = getTopMostContainer(self);
        if (utils.hasScriptVar(bldg, "warren.deactivated"))
        {
            return SCRIPT_CONTINUE;
        }
        String turretCodeSequence = getStringObjVar(bldg, "warren.turretCodeSequence");
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
            setName(self, "");
            setName(self, new string_id(SYSTEM_MESSAGES, "turret_term_off"));
            utils.setScriptVar(bldg, "warren.deactivated", true);
            showFlyText(self, new string_id(SYSTEM_MESSAGES, DEACTIVATE), 1.75f, colors.GREENYELLOW);
            messageTo(self, "reactivateTurret", null, 1800, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int reactivateTurret(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        utils.removeScriptVar(bldg, "warren.deactivated");
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "turret_term_on"));
        return SCRIPT_CONTINUE;
    }
}
