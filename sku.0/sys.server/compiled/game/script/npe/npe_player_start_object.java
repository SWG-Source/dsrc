package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class npe_player_start_object extends script.base_script
{
    public npe_player_start_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int setupTriggerVolume(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume("npePlayerMoved", 1, true);
        obj_id[] contents = getTriggerVolumeContents(self, "npePlayerMoved");
        boolean playerInside = false;
        for (int i = 0; i < contents.length; i++)
        {
            if (isPlayer(contents[i]))
            {
                playerInside = true;
            }
        }
        if (playerInside == false)
        {
            obj_id building = getTopMostContainer(self);
            messageTo(building, "continueMainTable", null, 1.2f, false);
            removeTriggerVolume("npePlayerMoved");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id player) throws InterruptedException
    {
        if (volumeName.equals("npePlayerMoved"))
        {
            if (isPlayer(player))
            {
                obj_id building = getTopMostContainer(player);
                messageTo(building, "continueMainTable", null, 0, false);
                removeTriggerVolume("npePlayerMoved");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
