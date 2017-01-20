package script.systems.crafting.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.craftinglib;
import script.library.pet_lib;
import script.library.utils;
import script.library.skill;

public class crafting_gcw_tools extends script.base_script
{
    public crafting_gcw_tools()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        int power = (int)getFloatObjVar(self, "crafting_components.power");
        int fatigue = 1;
        String skillTemplate = getSkillTemplate(player);
        String profession = skill.getProfessionName(skillTemplate);
        if(profession == null){
            LOG("gcw-crafting","Unable to get player's (" + player + ":" + getPlayerFullName(player) + ") profession!  Skill template is " + skillTemplate + ".");
        }
        else if (!profession.equals("trader"))
        {
            fatigue = 5;
        }
        if (getCount(self) > 1)
        {
            names[idx] = "charges";
            attribs[idx] = "" + count;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "crafting_components.power"))
        {
            names[idx] = "power";
            attribs[idx] = "" + power;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = "fatigue";
        attribs[idx] = "" + fatigue;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
