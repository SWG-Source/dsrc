package script.space.crafting;

import script.library.space_utils;
import script.library.utils;
import script.obj_id;

public class chassis_blueprints extends script.base_script
{
    public chassis_blueprints()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ship_chassis.mass"))
        {
            float value = getFloatObjVar(self, "ship_chassis.mass");
            names[idx] = "chassisMassMax";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "ship_chassis.hp"))
        {
            float value = getFloatObjVar(self, "ship_chassis.hp");
            names[idx] = "hitPointsMax";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "shiptype"))
        {
            names[idx] = "pilotSkillRequired";
            attribs[idx] = space_utils.getSkillRequiredForShip(self);
            idx++;
        }
        names[idx] = "ship_assembly_n";
        String text = " \\#pcontrast2 " + "$@obj_attr_n:ship_assembly_d$" + " \\# ";
        attribs[idx] = text;
        return SCRIPT_CONTINUE;
    }
}
