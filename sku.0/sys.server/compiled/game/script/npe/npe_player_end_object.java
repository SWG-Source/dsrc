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

public class npe_player_end_object extends script.base_script
{
    public npe_player_end_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("npeFalconRamp", 2.0f, true);
        obj_id[] contents = getTriggerVolumeContents(self, "npeFalconRamp");
        for (int i = 0; i < contents.length; i++)
        {
            if (isIdValid(contents[i]) && isPlayer(contents[i]))
            {
                obj_id building = getTopMostContainer(contents[i]);
                utils.removeScriptVar(contents[i], "npe.hangarEventStarted");
                if (checkGod(contents[i]))
                {
                    return SCRIPT_CONTINUE;
                }
                utils.removeScriptVar(contents[i], "npe.falconEventStarted");
                boolean moved = npe.movePlayerFromHangarToFalcon(contents[i]);
                if (moved)
                {
                    detachScript(contents[i], "npe.han_solo_experience_player");
                    attachScript(contents[i], "npe.npe_falcon_player");
                }
                removeTriggerVolume("npeFalconRamp");
                LOG("npe_falcon_ramp", "Player ID " + contents[i] + " is trying to board the falcon in hangar ID " + building + ".  Transition function returned " + moved);
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
