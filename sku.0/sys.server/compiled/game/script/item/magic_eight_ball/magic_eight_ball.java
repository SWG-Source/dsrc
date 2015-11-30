package script.item.magic_eight_ball;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class magic_eight_ball extends script.base_script
{
    public magic_eight_ball()
    {
    }
    public static final string_id SID_READ_OPTION = new string_id("sui", "read_option");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_READ_OPTION);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            openFortuneWindow(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void openFortuneWindow(obj_id self, obj_id player) throws InterruptedException
    {
        String title = "@magic_eight_ball:title";
        String prompt = "@magic_eight_ball:prompt1";
        int pid = sui.inputbox(self, player, prompt, title, "handleQuestion", 64, false, "");
    }
    public int handleQuestion(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String text = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (text == null || text.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int textCRC = getStringCrc(text);
        if (textCRC < 0)
        {
            textCRC *= -1;
        }
        int num = textCRC % 49;
        String title = "@magic_eight_ball:title";
        String prompt = "@magic_eight_ball:prompt2_1" + "\n  \\#pcontrast3 ";
        prompt += text + " \\#. ";
        prompt += "@magic_eight_ball:prompt2_2" + "\n  \\#pcontrast1 ";
        prompt += "@magic_eight_ball:message" + num;
        sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "noHandler");
        return SCRIPT_CONTINUE;
    }
}
