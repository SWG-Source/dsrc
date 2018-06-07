package script.systems.missions.base;

import script.library.factions;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;

public class mission_terminal_imperial extends script.base_script
{
    public mission_terminal_imperial()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "strFaction", factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        float fltDistance = getDistance(player, self);
        if (fltDistance > 10)
        {
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(self, "mission_terminal OnObjectMenuRequest");
        mi.addRootMenu(menu_info_types.MISSION_TERMINAL_LIST, null);
        return SCRIPT_CONTINUE;
    }
}
