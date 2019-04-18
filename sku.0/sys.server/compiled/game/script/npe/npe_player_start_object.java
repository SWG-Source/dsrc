package script.npe;

import script.dictionary;
import script.obj_id;

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
        for (obj_id content : contents) {
            if (isPlayer(content)) {
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
