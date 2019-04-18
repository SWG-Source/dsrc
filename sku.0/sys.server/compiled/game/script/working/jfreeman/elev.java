package script.working.jfreeman;

import script.library.utils;
import script.location;
import script.obj_id;

public class elev extends script.base_script
{
    public elev()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        switch (text) {
            case "up":
                playClientEffectObj(self, "clienteffect/elevator_rise.cef", self, null);
                if (elevatorMove(self, 1) == 0) {
                    sendSystemMessage(self, "You are already on the highest floor", null);
                    return SCRIPT_OVERRIDE;
                }
                return SCRIPT_CONTINUE;
            case "down":
                playClientEffectObj(self, "clienteffect/elevator_descend.cef", self, null);
                if (elevatorMove(self, -1) == 0) {
                    sendSystemMessage(self, "You are already on the lowest floor", null);
                    return SCRIPT_OVERRIDE;
                }
                return SCRIPT_CONTINUE;
            case "out":
                obj_id building = getTopMostContainer(self);
                if (isIdValid(building) && building != self) {
                    location outLoc = getBuildingEjectLocation(building);
                    utils.warpPlayer(self, outLoc);
                    return SCRIPT_OVERRIDE;
                }
                return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
