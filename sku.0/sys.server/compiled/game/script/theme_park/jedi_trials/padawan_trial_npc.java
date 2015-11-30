package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.jedi_trials;

public class padawan_trial_npc extends script.base_script
{
    public padawan_trial_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("DestroyNpcVolume", 32.0f, true);
        createTriggerVolume("faceToTriggerVolume", 8.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isMob(self))
        {
            if (isPlayer(breacher))
            {
                if (volumeName.equals("faceToTriggerVolume"))
                {
                    if (!isInNpcConversation(self))
                    {
                        if (canSee(self, breacher))
                        {
                            faceTo(self, breacher);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isPlayer(breacher))
        {
            if (volumeName.equals("DestroyNpcVolume"))
            {
                obj_id trialPlayer = getObjIdObjVar(self, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
                if (trialPlayer == breacher)
                {
                    if (isInvulnerable(self))
                    {
                        if (hasObjVar(self, "padawan_trials.playerAccepted") || hasObjVar(self, "padawan_trials.playerSucceeded") || hasObjVar(self, "padawan_trials.playerFailed"))
                        {
                            destroyObject(self);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestStartAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        startCombat(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(self, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        dictionary webster = new dictionary();
        webster.put("attacker", attacker);
        webster.put("trialNpc", self);
        messageTo(trialPlayer, "handlerTrialNpcKilled", webster, 1, false);
        return SCRIPT_CONTINUE;
    }
}
