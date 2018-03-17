package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class combat_lightsource extends script.base_script
{
    public combat_lightsource()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float fltRange = getIntObjVar(self, "combat.lightRange");
        createTriggerVolume(self.toString(), fltRange, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(self.toString()))
        {
            if (isMob(breacher))
            {
                setObjVar(breacher, "combat.intNearFlare", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(self.toString()))
        {
            if (isMob(breacher))
            {
                removeObjVar(breacher, "combat.intNearFlare");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
