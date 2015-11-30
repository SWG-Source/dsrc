package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dressnpc extends script.base_script
{
    public dressnpc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached!");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("done"))
        {
            debugSpeakMsg(self, "detaching jfreeman.dressnpc");
            detachScript(self, "jfreeman.dressnpc");
            return SCRIPT_CONTINUE;
        }
        else if (text.equals("dress"))
        {
            obj_id thingToWear = getLookAtTarget(speaker);
            if (thingToWear == null)
            {
                debugSpeakMsg(self, "you aren't looking at anything.");
            }
            else 
            {
                equip(thingToWear, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
