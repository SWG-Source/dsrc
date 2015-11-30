package script.creature;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;

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
        String type = getName(self);
        if (type.equals("mob/creature_names:protocol_droid_3po"))
        {
            String firstLetter = getLetter();
            int firstNum = rand(0, 9);
            setName(self, firstLetter + firstNum + "-P0");
        }
        if (type.equals("mob/creature_names:r2"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R2-" + secondLetter + secondNum);
        }
        if (type.equals("mob/creature_names:r3"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R3-" + secondLetter + secondNum);
        }
        if (type.equals("mob/creature_names:r4"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R4-" + secondLetter + secondNum);
        }
        if (type.equals("mob/creature_names:r5"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R5-" + secondLetter + secondNum);
        }
        if (type.equals("mob/creature_names:eg6_power_droid"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "E" + secondLetter + "-" + secondNum);
        }
        if (type.equals("mob/creature_names:wed_treadwell"))
        {
            String fourthLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "WED-" + fourthLetter + num);
        }
        if (type.equals("mob/creature_names:le_repair_droid"))
        {
            String thirdLetter = getLetter();
            String fourthLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "LE-" + thirdLetter + fourthLetter + num);
        }
        if (type.equals("mob/creature_names:ra7_bug_droid"))
        {
            String thirdLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "RA7-" + thirdLetter + num);
        }
        return;
    }
    public String getLetter() throws InterruptedException
    {
        int which = rand(1, 26);
        String letter = "A";
        switch (which)
        {
            case 1:
            letter = "A";
            break;
            case 2:
            letter = "B";
            break;
            case 3:
            letter = "C";
            break;
            case 4:
            letter = "D";
            break;
            case 5:
            letter = "E";
            break;
            case 6:
            letter = "F";
            break;
            case 7:
            letter = "G";
            break;
            case 8:
            letter = "H";
            break;
            case 9:
            letter = "I";
            break;
            case 10:
            letter = "J";
            break;
            case 11:
            letter = "K";
            break;
            case 12:
            letter = "L";
            break;
            case 13:
            letter = "M";
            break;
            case 14:
            letter = "N";
            break;
            case 15:
            letter = "O";
            break;
            case 16:
            letter = "P";
            break;
            case 17:
            letter = "Q";
            break;
            case 18:
            letter = "R";
            break;
            case 19:
            letter = "S";
            break;
            case 20:
            letter = "T";
            break;
            case 21:
            letter = "U";
            break;
            case 22:
            letter = "V";
            break;
            case 23:
            letter = "W";
            break;
            case 24:
            letter = "X";
            break;
            case 25:
            letter = "Y";
            break;
            case 26:
            letter = "Z";
            break;
        }
        return letter;
    }
    public void setDroidHue(obj_id self) throws InterruptedException
    {
        ranged_int_custom_var[] c = hue.getPalcolorVars(self);
        if (c != null)
        {
            for (int i = 0; i < c.length; i++)
            {
                int huevar = rand(0, 63);
                c[i].setValue(huevar);
            }
        }
        return;
    }
}
