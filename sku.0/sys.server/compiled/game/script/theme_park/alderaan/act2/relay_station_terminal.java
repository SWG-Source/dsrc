package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.factions;
import script.library.badge;

public class relay_station_terminal extends script.base_script
{
    public relay_station_terminal()
    {
    }
    public static final String REBEL_STF = "theme_park/alderaan/act2/rebel_missions";
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act2/shared_rebel_missions";
    public static final string_id MESSAGE_SENT = new string_id(REBEL_SHARED_STF, "message_sent");
    public static final string_id MISSION_COMPLETE = new string_id(REBEL_SHARED_STF, "mission_complete");
    public static final string_id TERMINAL_LOCKED = new string_id(REBEL_SHARED_STF, "terminal_locked");
    public static final string_id SID_USE = new string_id(REBEL_SHARED_STF, "use_terminal");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id cell = loc.cell;
        if (hasObjVar(cell, "coa2.rebel.unlocked"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (utils.playerHasItemByTemplate(player, "object/tangible/theme_park/alderaan/act2/interface_override_device.iff"))
            {
                obj_id device = utils.getItemPlayerHasByTemplate(player, "object/tangible/theme_park/alderaan/act2/interface_override_device.iff");
                destroyObject(device);
                setObjVar(player, "coa2.progress", 14);
                sendSystemMessage(player, MESSAGE_SENT);
                factions.awardFactionStanding(player, "Rebel", 500);
                sendSystemMessage(player, MISSION_COMPLETE);
                badge.grantBadge(player, "event_coa2_rebel");
                playMusic(player, "sound/music_lando_theme.snd");
                removeObjVar(player, "coa2.rebel");
            }
            else 
            {
                sendSystemMessage(player, TERMINAL_LOCKED);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
