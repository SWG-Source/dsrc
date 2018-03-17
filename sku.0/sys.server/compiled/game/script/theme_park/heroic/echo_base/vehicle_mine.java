package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.stealth;
import script.library.trial;
import script.library.utils;

public class vehicle_mine extends script.base_script
{
    public vehicle_mine()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("hoth_vehicle_mine", 10.0f, true);
        addSkillModModifier(self, "strength_modified", "strength_modified", 1000, -1, false, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isIdValid(breacher) || !isMob(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("hoth_vehicle_mine"))
        {
            if (!isDead(breacher) && pvpCanAttack(self, breacher))
            {
                stealth.checkForAndMakeVisible(breacher);
                queueCommand(self, (-1220440242), breacher, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        queueCommand(self, (-1220440242), target, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        removeTriggerVolume("hoth_vehicle_mine");
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        removeTriggerVolume("hoth_vehicle_mine");
        return SCRIPT_CONTINUE;
    }
}
