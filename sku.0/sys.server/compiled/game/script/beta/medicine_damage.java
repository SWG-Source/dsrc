package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.healing;
import script.library.consumable;
import script.library.player_stomach;

public class medicine_damage extends script.base_script
{
    public medicine_damage()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int power = rand(50, 300);
        int charges = rand(1, 10);
        if (healing.isRangedMedicine(self))
        {
            charges = 1;
        }
        attrib_mod[] am = createHealDamageMedicineMod(power);
        setObjVar(self, consumable.VAR_CONSUMABLE_MODS, am);
        setObjVar(self, consumable.VAR_CONSUMABLE_MEDICINE, true);
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
            "healing_ability"
        };
        setObjVar(self, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        int skill_req = rand(10, 50);
        int[] skill_value = 
        {
            skill_req
        };
        setObjVar(self, consumable.VAR_SKILL_MOD_MIN, skill_value);
        LOG("LOG_CHANNEL", "Medicine created!");
        return SCRIPT_CONTINUE;
    }
    public attrib_mod[] createHealDamageMedicineMod(int power) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[3];
        for (int i = 0; i < 3; i++)
        {
            am[i] = utils.createHealDamageAttribMod(i * 3, power);
        }
        return am;
    }
}
