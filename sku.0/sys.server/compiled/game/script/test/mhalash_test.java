package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;

public class mhalash_test extends script.base_script
{
    public mhalash_test()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            String command = tok.nextToken();
            if (command.equalsIgnoreCase("d_item"))
            {
                String strName = tok.nextToken();
                String levelStr = tok.nextToken();
                int intLevel = utils.stringToInt(levelStr);
                if (strName == null || strName.equals("") || levelStr == null || levelStr.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "Use d_item itemCat intLevel");
                    return SCRIPT_CONTINUE;
                }
                obj_id inventory = utils.getInventoryContainer(self);
                static_item.makeDynamicObject(strName, inventory, intLevel);
            }
            if (command.equalsIgnoreCase("num_combos"))
            {
                String numberStr1 = tok.nextToken();
                String numberStr2 = tok.nextToken();
                int number1 = utils.stringToInt(numberStr1);
                int number2 = utils.stringToInt(numberStr2);
                if (numberStr1 == null || numberStr1.equals("") || numberStr2 == null || numberStr2.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "num_combos number1 number2");
                    return SCRIPT_CONTINUE;
                }
                int numCombos = 0;
                for (int i = 1; i < 71; i++)
                {
                    for (int j = 1; j < 71; j++)
                    {
                        if ((i * j) >= number1 && (i * j) <= number2)
                        {
                            numCombos++;
                            sendSystemMessageTestingOnly(self, "Valid hit " + i + "*" + j + "= " + (i * j));
                        }
                    }
                }
                sendSystemMessageTestingOnly(self, "numbCombos =" + numCombos);
            }
            if (command.equalsIgnoreCase("buildabuff"))
            {
                utils.removeScriptVar(self, "performance.buildabuff.modNames");
                utils.removeScriptVar(self, "performance.buildabuff.modValues");
                String modName1 = tok.nextToken();
                String modValStr1 = tok.nextToken();
                String modName2 = tok.nextToken();
                String modValStr2 = tok.nextToken();
                int modVal1 = utils.stringToInt(modValStr1);
                int modVal2 = utils.stringToInt(modValStr2);
                String[] modNames = 
                {
                    modName1,
                    modName2
                };
                float[] modVals = 
                {
                    modVal1,
                    modVal2
                };
                utils.setScriptVar(self, "performance.buildabuff.modNames", modNames);
                utils.setScriptVar(self, "performance.buildabuff.modValues", modVals);
                sendSystemMessageTestingOnly(self, "buildabuff!" + modName1 + modVal1 + modName2 + modVal2);
            }
            if (command.equalsIgnoreCase("token_vendor_sui"))
            {
                dictionary d = new dictionary();
                d.put("player", self);
                messageTo(self, "showInventorySUI", d, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
