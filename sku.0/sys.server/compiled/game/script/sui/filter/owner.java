package script.sui.filter;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class owner extends script.base_script
{
    public owner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, sui.SCRIPT_FILTER_OWNER);
        return SCRIPT_CONTINUE;
    }
    public int handleFilterInput(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String returnHandler = utils.getStringScriptVar(self, sui.VAR_FILTER_BASE + "." + player + ".handler");
        String defaultText = utils.getStringScriptVar(self, sui.VAR_FILTER_BASE + "." + player + ".defaultText");
        sui.cleanupFilteredInputbox(self, player);
        boolean doSend = true;
        int bp = sui.getIntButtonPressed(params);
        if (bp != sui.BP_CANCEL)
        {
            String text = sui.getInputBoxText(params);
            if (text != null && !text.equals(""))
            {
                if (!isAppropriateText(text))
                {
                    doSend = false;
                    String title = sui.getInputBoxTitle(params);
                    String prompt = sui.getInputBoxPrompt(params);
                    int maxLength = sui.getInputBoxMaxLength(params);
                    sui.inputbox(self, player, prompt, title, returnHandler, maxLength, true, defaultText);
                    sendSystemMessageTestingOnly(player, "You have entered was considered inappropriate.\nPlease enter more suitable text");
                }
            }
        }
        if (returnHandler != null && !returnHandler.equals("") && doSend)
        {
            messageTo(self, returnHandler, params, 0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
