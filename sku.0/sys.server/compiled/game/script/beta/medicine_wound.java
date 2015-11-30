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

public class medicine_wound extends script.base_script
{
    public medicine_wound()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String template_name = getTemplateName(self);
        int wound_type = -1;
        if (template_name.equals("object/tangible/medicine/medpack_wound_health.iff"))
        {
            wound_type = 0;
        }
        if (template_name.equals("object/tangible/medicine/medpack_wound_constitution.iff"))
        {
            wound_type = 2;
        }
        if (template_name.equals("object/tangible/medicine/medpack_wound_action.iff"))
        {
            wound_type = 3;
        }
        if (template_name.equals("object/tangible/medicine/medpack_wound_stamina.iff"))
        {
            wound_type = 5;
        }
        int power = rand(25, 150);
        int charges = rand(1, 3);
        if (wound_type == -1)
        {
            wound_type = rand(0, NUM_ATTRIBUTES - NUM_ATTRIBUTES_PER_GROUP - 1);
        }
        attrib_mod[] am = new attrib_mod[1];
        am[0] = utils.createHealWoundAttribMod(wound_type, power);
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
        int skill_req = rand(25, 100);
        int[] skill_value = 
        {
            skill_req
        };
        setObjVar(self, consumable.VAR_SKILL_MOD_MIN, skill_value);
        LOG("LOG_CHANNEL", "Medicine created!");
        return SCRIPT_CONTINUE;
    }
}
