package script.quest.task.ground.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class enter_room_signal extends script.base_script
{
    public enter_room_signal()
    {
    }
    public static final String SIGNAL = "signal_name";
    public static final String ENTERED = "entered_";
    public int OnAboutToReceiveItem(obj_id self, obj_id roomComingFrom, obj_id roomGoingTo, obj_id player) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String signal = "";
            String signalObj = "";
            obj_id bldg = getTopMostContainer(self);
            String roomName = getCellName(self);
            if (hasObjVar(self, SIGNAL))
            {
                signalObj = getStringObjVar(self, SIGNAL);
            }
            else if (hasObjVar(bldg, SIGNAL))
            {
                signalObj = getStringObjVar(bldg, SIGNAL);
            }
            else 
            {
                groundquests.questOutputDebugInfo(player, "quest.task.ground.util.enter_room_signal", "OnAboutToReceiveItem", "No objvar defined on building or cell, but tried to call signal");
                groundquests.questOutputDebugLog("quest.task.ground.util.enter_room_signal", "OnAboutToReceiveItem", "No objvar defined on building or cell, but tried to call signal");
                return SCRIPT_CONTINUE;
            }
            signal = ENTERED + signalObj + "_" + roomName;
            groundquests.sendSignal(player, signal);
        }
        return SCRIPT_CONTINUE;
    }
}
