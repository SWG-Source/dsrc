package script.theme_park.utils;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class droid_name extends script.base_script
{
    public droid_name()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        getDroidName(self);
        return SCRIPT_CONTINUE;
    }
    public void getDroidName(obj_id self) throws InterruptedException
    {
        String type = getName(self);
        if (type.equals("droid_name:3po_base"))
        {
            String firstLetter = getLetter();
            int firstNum = rand(0, 9);
            setName(self, firstLetter + firstNum + "-P0");
        }
        if (type.equals("droid_name:r2_base"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R2-" + secondLetter + secondNum);
        }
        if (type.equals("droid_name:r3_base"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R3-" + secondLetter + secondNum);
        }
        if (type.equals("droid_name:r4_base"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R4-" + secondLetter + secondNum);
        }
        if (type.equals("droid_name:r5_base"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "R5-" + secondLetter + secondNum);
        }
        if (type.equals("droid_name:eg_6_power_droid_base"))
        {
            String secondLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, "E" + secondLetter + "-" + secondNum);
        }
        if (type.equals("droid_name:wed_treadwell_base"))
        {
            String fourthLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "WED-" + fourthLetter + num);
        }
        if (type.equals("droid_name:le_repair_droid"))
        {
            String thirdLetter = getLetter();
            String fourthLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "LE-" + thirdLetter + fourthLetter + num);
        }
        if (type.equals("droid_name:ra7_bug_droid_base"))
        {
            String thirdLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "RA7-" + thirdLetter + num);
        }
        if (type.equals("droid_name:2_1b_surgical_droid_base"))
        {
            int firstNum = rand(0, 9);
            String firstLetter = getLetter();
            int secondNum = rand(0, 9);
            setName(self, firstNum + "-" + secondNum + firstLetter);
        }
        if (type.equals("droid_name:3po_base"))
        {
            String firstLetter = getLetter();
            int num = rand(0, 9);
            setName(self, firstLetter + num + "-P0");
        }
        if (type.equals("droid_name:cll_8_binary_load_lifter_base"))
        {
            setName(self, "Binary Load Lifter");
        }
        if (type.equals("droid_name:droideka_base"))
        {
            setName(self, "Droideka");
        }
        if (type.equals("droid_name:imperial_probot_base"))
        {
            setName(self, "Imperial Probe Droid");
        }
        if (type.equals("droid_name:lin_demolitionmech_base"))
        {
            String firstLetter = getLetter();
            int num = rand(0, 9);
            setName(self, "LIN-" + firstLetter + num);
        }
        if (type.equals("droid_name:mse_6_base"))
        {
            setName(self, "MSE-" + rand(0, 9));
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
}
