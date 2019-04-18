package script.theme_park.warren;

import script.library.colors;
import script.library.pet_lib;
import script.library.utils;
import script.*;

public class elevator_one extends script.base_script
{
    public elevator_one()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String ACTIVATE = "elev_one_secure";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu2 = mi.addRootMenu(menu_info_types.ELEVATOR_DOWN, new string_id("elevator_text", "down"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 3.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_OVERRIDE;
        }
        if (item == menu_info_types.ELEVATOR_DOWN)
        {
            if (isSecurityActive(self))
            {
                showFlyText(self, new string_id(SYSTEM_MESSAGES, ACTIVATE), 1.75f, colors.GREENYELLOW);
                return SCRIPT_OVERRIDE;
            }
            movePartyDown(self, player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isSecurityActive(obj_id elevator) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(elevator);
        for (int i = 1; i < 5; i++)
        {
            if (!utils.hasScriptVar(bldg, "warren.passkeyNumberDeactivated" + i))
            {
                return true;
            }
        }
        return false;
    }
    public void movePartyDown(obj_id self, obj_id player) throws InterruptedException
    {
        location elevLoc = getLocation(self);
        obj_id cell = elevLoc.cell;
        obj_id[] contents = getContents(cell);
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                playClientEffectObj(content, "clienteffect/elevator_descend.cef", content, null);
                if (elevatorMove(content, -1) == 0) {
                    sendSystemMessage(content, new string_id(SYSTEM_MESSAGES, "elev_toolow"));
                    return;
                }
            } else if (pet_lib.isPet(content)) {
                elevatorMove(content, -1);
            }
        }
    }
}
