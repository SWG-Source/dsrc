package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.qa;
import script.library.utils;

public class qabuff extends script.base_script
{
    public qabuff()
    {
    }
    public static final String MY_SCRIPTVAR = "qabuff";
    public static final String BUFF_TABLE = "datatables/buff/buff.iff";
    public static final String BUFF_TOOL_PROMPT = "Select Spacial Attack or Buff to be performed on your test character.\n\nTo remove all buffs use the command:\n\n/qatool buff clear";
    public static final String BUFF_TOOL_TITLE = "Special Attack & Buff Tool";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qabuff");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qabuff");
        }
        return SCRIPT_CONTINUE;
    }
    public int buffOptionHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, MY_SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, "bufftool");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, MY_SCRIPTVAR + ".buffMenu");
                if (btn == sui.BP_CANCEL)
                {
                    utils.removeScriptVarTree(self, MY_SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    utils.removeScriptVarTree(self, MY_SCRIPTVAR);
                    String[] tool_options = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
                    qa.refreshMenu(self, "Choose the tool you want to use", "QA Tools", tool_options, "toolMainMenu", true, "qatool.pid");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String buffArg = previousMainMenuArray[idx];
                    String buffName = qa.getClientBuffName(self, buffArg);
                    if (!buffName.equals("null"))
                    {
                        qa.applyBuffOption(self, buffArg, buffName);
                        createMainMenu(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createMainMenu(obj_id self) throws InterruptedException
    {
        String[] allBuffs = dataTableGetStringColumn(BUFF_TABLE, "NAME");
        Arrays.sort(allBuffs);
        utils.setScriptVar(self, "qabuff.buffMenu", allBuffs);
        qa.refreshMenu(self, BUFF_TOOL_PROMPT, BUFF_TOOL_TITLE, allBuffs, "buffOptionHandler", "qabuff.pid", sui.OK_CANCEL_REFRESH);
    }
}
