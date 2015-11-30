package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mission_terminal_bounty extends script.base_script
{
    public mission_terminal_bounty()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "intBounty", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int intMissionLevel = getSkillStatMod(player, "bounty_mission_level");
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
