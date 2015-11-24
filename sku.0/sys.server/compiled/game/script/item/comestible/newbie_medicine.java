package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.consumable;
import script.library.healing;
import script.library.utils;

public class newbie_medicine extends script.base_script
{
    public newbie_medicine()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, consumable.VAR_CONSUMABLE_BASE))
        {
            removeObjVar(self, consumable.VAR_CONSUMABLE_BASE);
        }
        String template = getTemplateName(self);
        String attribute = "";
        int charges = 1;
        if (template.equals("object/tangible/medicine/newbie_medpack_damage.iff"))
        {
            charges = 5;
            attribute = "damage";
        }
        else if (template.equals("object/tangible/medicine/newbie_medpack_wound_action.iff"))
        {
            attribute = "action";
        }
        else if (template.equals("object/tangible/medicine/newbie_medpack_wound_health.iff"))
        {
            attribute = "health";
        }
        else 
        {
            LOG("LOG_CHANNEL", "newbie_medicine::OnAttach -- illegal template for " + self);
            return SCRIPT_CONTINUE;
        }
        LOG("LOG_CHANNEL", "attribute ->" + attribute);
        Vector am = new Vector();
        am.setSize(0);
        if (attribute.equals("damage"))
        {
            attrib_mod tmp = new attrib_mod(0, 0, 0.0f, 0.0f, 0.0f);
            for (int i = 0; i < 3; i++)
            {
                tmp = utils.createHealDamageAttribMod(i * 3, 50);
                am = utils.addElement(am, tmp);
            }
        }
        else 
        {
            int attrib_type = healing.stringToAttribute(attribute.toUpperCase());
            if (attrib_type == -1)
            {
                LOG("LOG_CHANNEL", "newbie_medicine::OnAttach -- illegal attribute type for " + self);
                return SCRIPT_CONTINUE;
            }
            attrib_mod tmp = new attrib_mod(0, 0, 0.0f, 0.0f, 0.0f);
            tmp = utils.createHealWoundAttribMod(attrib_type, 10);
            am = utils.addElement(am, tmp);
        }
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
        int[] skill_mod_min = 
        {
            5
        };
        setObjVar(self, consumable.VAR_SKILL_MOD_MIN, skill_mod_min);
        detachScript(self, "item.comestible.newbie_medicine");
        return SCRIPT_CONTINUE;
    }
}
