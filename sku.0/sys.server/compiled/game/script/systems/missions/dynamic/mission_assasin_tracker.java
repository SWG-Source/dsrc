package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mission_assasin_tracker extends script.systems.missions.base.mission_dynamic_base
{
    public mission_assasin_tracker()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id objMission = getAssassinMission(player);
            if (objMission == null)
            {
                string_id strSpam = new string_id("mission/mission_generic", "no_assassin_mission");
                sendSystemMessage(player, strSpam);
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(objMission, "objNPC"))
            {
                string_id strSpam = new string_id("mission/mission_generic", "no_assassin_target");
                sendSystemMessage(player, strSpam);
                return SCRIPT_CONTINUE;
            }
            obj_id objTarget = getObjIdObjVar(objMission, "objNPC");
            if (objTarget == null)
            {
                return SCRIPT_CONTINUE;
            }
            if (!exists(objTarget))
            {
                string_id strSpam = new string_id("mission/mission_generic", "no_assassin_target");
                sendSystemMessage(player, strSpam);
                return SCRIPT_CONTINUE;
            }
            queueCommand(player, (-1640894567), objTarget, "", COMMAND_PRIORITY_FRONT);
        }
        return SCRIPT_CONTINUE;
    }
}
