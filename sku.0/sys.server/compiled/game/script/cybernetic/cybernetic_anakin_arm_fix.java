package script.cybernetic;

import script.dictionary;
import script.obj_id;

public class cybernetic_anakin_arm_fix extends script.base_script
{
    public cybernetic_anakin_arm_fix()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "delayedCyberneticForearmFix", null, 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "delayedCyberneticForearmFix", null, 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int delayedCyberneticForearmFix(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "cybernetic_reward_fixed"))
        {
            clearBioLink(self);
            detachScript(self, "item.armor.biolink_item_non_faction");
            removeObjVar(self, "biolink.id");
            setObjVar(self, "cybernetic_reward_fixed", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
