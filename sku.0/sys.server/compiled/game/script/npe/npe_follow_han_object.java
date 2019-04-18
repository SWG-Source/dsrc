package script.npe;

import script.dictionary;
import script.library.attrib;
import script.library.utils;
import script.obj_id;

public class npe_follow_han_object extends script.base_script
{
    public npe_follow_han_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "hanHere", false);
        setObjVar(self, "playerHere", false);
        return SCRIPT_CONTINUE;
    }
    public int setupTriggerVolume(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        createTriggerVolume("npeFollowHan", 10.0f, true);
        obj_id[] contents = getTriggerVolumeContents(self, "npeFollowHan");
        obj_id hanSolo = utils.getObjIdScriptVar(building, "objHanSolo");
        obj_id player = utils.getObjIdScriptVar(building, "npe.player");
        setAttributeInterested(self, attrib.ALL);
        setAttributeAttained(player, attrib.ALL);
        setAttributeAttained(hanSolo, attrib.ALL);
        for (obj_id content : contents) {
            if (isIdValid(content) && isPlayer(content)) {
                setObjVar(self, "playerHere", true);
                if (getBooleanObjVar(self, "playerHere") && getBooleanObjVar(self, "hanHere")) {
                    messageTo(building, "continueMainTable", null, 0, false);
                    destroyClientPath(content);
                    removeTriggerVolume("npeFollowHan");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id player) throws InterruptedException
    {
        if (volumeName.equals("npeFollowHan"))
        {
            if (hasObjVar(player, "isHanSolo"))
            {
                setObjVar(self, "hanHere", true);
                if (getBooleanObjVar(self, "playerHere") && getBooleanObjVar(self, "hanHere"))
                {
                    obj_id building = getTopMostContainer(player);
                    messageTo(building, "continueMainTable", null, 0, false);
                    destroyClientPath(player);
                    removeTriggerVolume("npeFollowHan");
                }
            }
            if (isPlayer(player))
            {
                setObjVar(self, "playerHere", true);
                if (getBooleanObjVar(self, "playerHere") && getBooleanObjVar(self, "hanHere"))
                {
                    obj_id building = getTopMostContainer(player);
                    messageTo(building, "continueMainTable", null, 0, false);
                    destroyClientPath(player);
                    removeTriggerVolume("npeFollowHan");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
