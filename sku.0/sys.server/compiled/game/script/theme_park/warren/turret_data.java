package script.theme_park.warren;

import script.library.sui;
import script.menu_info;
import script.obj_id;

public class turret_data extends script.base_script
{
    public turret_data()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String turretCodeSequence = getStringObjVar(self, "warren.turretCodeSequence");
        if (turretCodeSequence == null)
        {
            obj_id bldg = getTopMostContainer(self);
            turretCodeSequence = getStringObjVar(bldg, "warren.turretCodeSequence");
            if (turretCodeSequence == null)
            {
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                setObjVar(self, "warren.turretCodeSequence", turretCodeSequence);
            }
        }
        sui.msgbox(player, turretCodeSequence);
        return SCRIPT_OVERRIDE;
    }
}
