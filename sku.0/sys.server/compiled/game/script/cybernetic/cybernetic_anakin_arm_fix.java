package script.cybernetic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.cybernetic;
import script.library.utils;

public class cybernetic_anakin_arm_fix extends script.base_script
{
    public cybernetic_anakin_arm_fix()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id cybernetic = getSelf();
        messageTo(cybernetic, "delayedCyberneticForearmFix", null, 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id cybernetic = getSelf();
        messageTo(cybernetic, "delayedCyberneticForearmFix", null, 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int delayedCyberneticForearmFix(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id cybernetic = getSelf();
        if (!hasObjVar(cybernetic, "cybernetic_reward_fixed"))
        {
            clearBioLink(cybernetic);
            if (hasScript(cybernetic, "item.armor.biolink_item_non_faction"))
            {
                detachScript(cybernetic, "item.armor.biolink_item_non_faction");
            }
            if (hasObjVar(cybernetic, "biolink.id"))
            {
                removeObjVar(cybernetic, "biolink.id");
            }
            setObjVar(cybernetic, "cybernetic_reward_fixed", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
