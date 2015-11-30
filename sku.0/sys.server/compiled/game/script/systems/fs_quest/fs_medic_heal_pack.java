package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.utils;

public class fs_medic_heal_pack extends script.base_script
{
    public fs_medic_heal_pack()
    {
    }
    public static final String CRAFT_VAR = "crafting_components.";
    public static final String CURE_SYMPTOM_ONE = "cureSymptomOne";
    public static final String CURE_SYMPTOM_TWO = "cureSymptomTwo";
    public static final String GIVE_SYMPTOM_ONE = "giveSymptomOne";
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, CRAFT_VAR + CURE_SYMPTOM_ONE))
        {
            names[idx] = "fs_medic_pack_cure_one";
            attribs[idx] = _getCureName(self, CRAFT_VAR + CURE_SYMPTOM_ONE);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, CRAFT_VAR + CURE_SYMPTOM_TWO))
        {
            int cureOne = (int)getFloatObjVar(self, CRAFT_VAR + CURE_SYMPTOM_ONE);
            int cureTwo = (int)getFloatObjVar(self, CRAFT_VAR + CURE_SYMPTOM_TWO);
            if (cureOne != cureTwo)
            {
                names[idx] = "fs_medic_pack_cure_two";
                attribs[idx] = _getCureName(self, CRAFT_VAR + CURE_SYMPTOM_TWO);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, CRAFT_VAR + GIVE_SYMPTOM_ONE))
        {
            int cureOne = (int)getFloatObjVar(self, CRAFT_VAR + CURE_SYMPTOM_ONE);
            int cureTwo = (int)getFloatObjVar(self, CRAFT_VAR + CURE_SYMPTOM_TWO);
            int symptomOne = (int)getFloatObjVar(self, CRAFT_VAR + GIVE_SYMPTOM_ONE);
            if (symptomOne != cureOne && symptomOne != cureTwo)
            {
                names[idx] = "fs_medic_pack_symptom_one";
                attribs[idx] = _getCureName(self, CRAFT_VAR + GIVE_SYMPTOM_ONE);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String _getCureName(obj_id item, String cureVarLoc) throws InterruptedException
    {
        int cureNumber = (int)utils.getFloatObjVar(item, cureVarLoc);
        string_id cure = new string_id("quest/force_sensitive/fs_medic", "symptom" + cureNumber);
        return localize(cure);
    }
}
