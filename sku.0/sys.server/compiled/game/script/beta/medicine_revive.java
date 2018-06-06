package script.beta;

import script.attrib_mod;
import script.library.consumable;
import script.library.utils;
import script.obj_id;

public class medicine_revive extends script.base_script
{
    public medicine_revive()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[6];
        for (int i = 0; i < 3; i++)
        {
            int attrib = i * 3;
            am[i * 2] = utils.createHealWoundAttribMod(attrib, rand(10, 50));
            am[i * 2 + 1] = utils.createHealDamageAttribMod(attrib, rand(50, 150));
        }
        setObjVar(self, consumable.VAR_CONSUMABLE_MODS, am);
        setObjVar(self, consumable.VAR_CONSUMABLE_MEDICINE, true);
        int charges = rand(1, 3);
        setCount(self, charges);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(self, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
        String[] skill_mod = 
        {
            "healing_efficiency"
        };
        setObjVar(self, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        int[] skill_value = 
        {
            30
        };
        setObjVar(self, consumable.VAR_SKILL_MOD_MIN, skill_value);
        return SCRIPT_CONTINUE;
    }
}
