package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mission_terminal extends script.theme_park.newbie_tutorial.tutorial_base
{
    public mission_terminal()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id missionNpc = getObjIdObjVar(getTopMostContainer(self), MISSION_NPC);
        if (missionNpc != null)
        {
            messageTo(missionNpc, "handleYellAtNewbie", null, 1, false);
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
