package script.creature;

import script.library.hue;
import script.obj_id;
import script.ranged_int_custom_var;

public class droid_setup extends script.base_script
{
    public droid_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        getDroidName(self);
        setDroidHue(self);
        return SCRIPT_CONTINUE;
    }
    public void getDroidName(obj_id self) throws InterruptedException
    {
        switch (getName(self)) {
            case "mob/creature_names:protocol_droid_3po":
                setName(self, getLetter() + rand(0, 9) + "-P0");
                break;
            case "mob/creature_names:r2": {
                setName(self, "R2-" + getLetter() + rand(0, 9));
                break;
            }
            case "mob/creature_names:r3": {
                setName(self, "R3-" + getLetter() + rand(0, 9));
                break;
            }
            case "mob/creature_names:r4": {
                setName(self, "R4-" + getLetter() + rand(0, 9));
                break;
            }
            case "mob/creature_names:r5": {
                setName(self, "R5-" + getLetter() + rand(0, 9));
                break;
            }
            case "mob/creature_names:eg6_power_droid": {
                setName(self, "E" + getLetter() + "-" + rand(0, 9));
                break;
            }
            case "mob/creature_names:wed_treadwell": {
                setName(self, "WED-" + getLetter() + rand(0, 9));
                break;
            }
            case "mob/creature_names:le_repair_droid": {
                setName(self, "LE-" + getLetter() + getLetter() + rand(0, 9));
                break;
            }
            case "mob/creature_names:ra7_bug_droid": {
                setName(self, "RA7-" + getLetter() + rand(0, 9));
                break;
            }
        }
    }
    public char getLetter() throws InterruptedException
    {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return abc.charAt(rand(0, 25));
    }
    public void setDroidHue(obj_id self) throws InterruptedException
    {
        ranged_int_custom_var[] c = hue.getPalcolorVars(self);
        if (c != null)
        {
            for (ranged_int_custom_var aC : c) {
                aC.setValue(rand(0, 63));
            }
        }
    }
}
