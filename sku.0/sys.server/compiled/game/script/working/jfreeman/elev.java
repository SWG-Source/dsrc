package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class elev extends script.base_script
{
    public elev()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("up"))
        {
            playClientEffectObj(self, "clienteffect/elevator_rise.cef", self, null);
            if (elevatorMove(self, 1) == 0)
            {
                sendSystemMessage(self, "You are already on the highest floor", null);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        else if (text.equals("down"))
        {
            playClientEffectObj(self, "clienteffect/elevator_descend.cef", self, null);
            if (elevatorMove(self, -1) == 0)
            {
                sendSystemMessage(self, "You are already on the lowest floor", null);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        else if (text.equals("out"))
        {
            obj_id building = getTopMostContainer(self);
            if (isIdValid(building) && building != self)
            {
                location outLoc = getBuildingEjectLocation(building);
                utils.warpPlayer(self, outLoc);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
