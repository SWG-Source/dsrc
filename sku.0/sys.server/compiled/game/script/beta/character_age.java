package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class character_age extends script.base_script
{
    public character_age()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        if (target == null)
        {
            target = self;
        }
        if (text.equals("age"))
        {
            int age = getCurrentBirthDate() - getPlayerBirthDate(target);
            sendSystemMessageTestingOnly(self, "Age ->" + age);
        }
        return SCRIPT_CONTINUE;
    }
}
