package script.npe;

import script.library.npe;
import script.library.utils;
import script.obj_id;

public class npe_player_end_object extends script.base_script
{
    public npe_player_end_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("npeFalconRamp", 2.0f, true);
        obj_id[] contents = getTriggerVolumeContents(self, "npeFalconRamp");
        for (obj_id content : contents) {
            if (isIdValid(content) && isPlayer(content)) {
                obj_id building = getTopMostContainer(content);
                utils.removeScriptVar(content, "npe.hangarEventStarted");
                if (checkGod(content)) {
                    return SCRIPT_CONTINUE;
                }
                utils.removeScriptVar(content, "npe.falconEventStarted");
                boolean moved = npe.movePlayerFromHangarToFalcon(content);
                if (moved) {
                    detachScript(content, "npe.han_solo_experience_player");
                    attachScript(content, "npe.npe_falcon_player");
                }
                removeTriggerVolume("npeFalconRamp");
                LOG("npe_falcon_ramp", "Player ID " + content + " is trying to board the falcon in hangar ID " + building + ".  Transition function returned " + moved);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id player) throws InterruptedException
    {
        if (volumeName.equals("npeFalconRamp"))
        {
            if (isPlayer(player))
            {
                obj_id building = getTopMostContainer(player);
                utils.removeScriptVar(player, "npe.hangarEventStarted");
                if (checkGod(player))
                {
                    return SCRIPT_CONTINUE;
                }
                utils.removeScriptVar(player, "npe.falconEventStarted");
                boolean moved = npe.movePlayerFromHangarToFalcon(player);
                removeTriggerVolume("npeFalconRamp");
                if (moved)
                {
                    detachScript(player, "npe.han_solo_experience_player");
                    attachScript(player, "npe.npe_falcon_player");
                }
                LOG("npe_falcon_ramp", "Player ID " + player + " is trying to board the falcon in hangar ID " + building + ".  Transition function returned " + moved);
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
