package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class trigtest extends script.base_script
{
    public trigtest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            createTriggerVolume("trigTest", 3, true);
        }
        if (text.equals("end"))
        {
            removeTriggerVolume("trigTest");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String triggerVolumeName, obj_id breacher) throws InterruptedException
    {
        debugSpeakMsg(self, "Entered!");
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String triggerVolumeName, obj_id breacher) throws InterruptedException
    {
        debugSpeakMsg(self, "Exitted!");
        return SCRIPT_OVERRIDE;
    }
    public int OnSkillAboutToBeGranted(obj_id self, String name) throws InterruptedException
    {
        debugSpeakMsg(self, "Entered about to be granted!");
        if (hasObjVar(self, "override_test"))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillAboutToBeRevoked(obj_id self, String name) throws InterruptedException
    {
        debugSpeakMsg(self, "Entered about to be revoked!");
        if (hasObjVar(self, "override_test"))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillGranted(obj_id self, String name) throws InterruptedException
    {
        debugSpeakMsg(self, "Entered granted!");
        if (hasObjVar(self, "override_test"))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillRevoked(obj_id self, String name) throws InterruptedException
    {
        debugSpeakMsg(self, "Entered revoked!");
        if (hasObjVar(self, "override_test"))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
