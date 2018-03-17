package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npe;
import script.library.utils;
import script.library.xp;

public class npe_walk_point_object extends script.base_script
{
    public npe_walk_point_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int setupWalkTrigger(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume("npeWalkPoint", 2.5f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id player) throws InterruptedException
    {
        if (volumeName.equals("npeWalkPoint"))
        {
            if (isPlayer(player))
            {
                destroyClientPath(player);
                obj_id building = getTopMostContainer(player);
                removeTriggerVolume("npeWalkPoint");
                xp.grantXpByTemplate(player, 10);
                messageTo(building, "continueMainTable", null, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkGod(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            sendSystemMessageTestingOnly(self, "Please turn off god mode when moving between npe locations. God mode and instances do not get along");
            return true;
        }
        return false;
    }
}
