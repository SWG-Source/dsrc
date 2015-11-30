package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.dot;
import script.library.colors;

public class electric_trap extends script.base_script
{
    public electric_trap()
    {
    }
    public static final String STF_FILE = "quest/corvetter_trap";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("electric_trap", 4.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (hasObjVar(self, "trap_off"))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                int dmgLocation = 0;
                int dmgAmt = rand(15000, 19000);
                int hitLoc = rand(1, 4);
                switch (hitLoc)
                {
                    case 1:
                    dmgLocation = HIT_LOCATION_R_ARM;
                    break;
                    case 2:
                    dmgLocation = HIT_LOCATION_L_ARM;
                    break;
                    case 3:
                    dmgLocation = HIT_LOCATION_BODY;
                    break;
                    case 4:
                    dmgLocation = HIT_LOCATION_R_LEG;
                    break;
                    default:
                    dmgLocation = HIT_LOCATION_L_LEG;
                    break;
                }
                sendSystemMessage(whoTriggeredMe, new string_id(STF_FILE, "shocked"));
                damage(whoTriggeredMe, DAMAGE_ELEMENTAL_ELECTRICAL, dmgLocation, dmgAmt);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
