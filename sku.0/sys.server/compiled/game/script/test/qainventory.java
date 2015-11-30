package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.qa;
import script.library.sui;
import script.library.utils;

public class qainventory extends script.base_script
{
    public qainventory()
    {
    }
    public static final String PROMPT = "Choose an Option";
    public static final String TITLE = "QA Inventory Tool";
    public static final String[] MAIN_MENU = 
    {
        "Delete all in inventory",
        "Fill inventory with Junk"
    };
    public static final String SCRIPT_VAR = "qainv";
    public static final String FROG_STRING = "object/tangible/terminal/terminal_character_builder.iff";
    public static final String KASHYYYK_FROG_STRING = "object/tangible/terminal/terminal_kashyyyk_content.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qainventory");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qainventory");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        if (isGod(player))
        {
            if ((toLower(text)).equals("qainventory"))
            {
                qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU, "mainMenuOptions", true, SCRIPT_VAR + ".pid");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int mainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPT_VAR + ".pid"))
            {
                qa.checkParams(params, SCRIPT_VAR, false);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                obj_id inventory = utils.getInventoryContainer(player);
                if (btn == sui.BP_CANCEL)
                {
                    qa.removeScriptVars(player, SCRIPT_VAR);
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    String[] options = utils.getStringArrayScriptVar(player, "qatool.toolMainMenu");
                    String mainTitle = utils.getStringScriptVar(player, "qatool.title");
                    String mainPrompt = utils.getStringScriptVar(player, "qatool.prompt");
                    if (options == null)
                    {
                        sendSystemMessageTestingOnly(player, "You didn't start from the main tool menu");
                        qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU, "mainMenuOptions", true, SCRIPT_VAR + ".pid");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        qa.refreshMenu(self, mainPrompt, mainTitle, options, "toolMainMenu", true, "qatool.pid");
                        utils.removeScriptVarTree(player, SCRIPT_VAR);
                        return SCRIPT_CONTINUE;
                    }
                }
                switch (idx)
                {
                    case 0:
                    obj_id[] items = getContents(inventory);
                    for (int i = 0; i < items.length; i++)
                    {
                        String templateName = getTemplateName(items[i]);
                        if (templateName.equals(FROG_STRING))
                        {
                            sendSystemMessageTestingOnly(player, "The Frog will not be destroyed");
                        }
                        else if (templateName.equals(KASHYYYK_FROG_STRING))
                        {
                            sendSystemMessageTestingOnly(player, "The Kashyyyk Frog will not be destroyed");
                        }
                        else 
                        {
                            destroyObject(items[i]);
                        }
                    }
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has deleted the entire contents of their inventory (less any Character Builder Terminals) using the QA Inventory Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU, "mainMenuOptions", SCRIPT_VAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 1:
                    int freeSpace = getVolumeFree(inventory);
                    for (int i = 0; i < freeSpace; i++)
                    {
                        createObject("object/tangible/food/fruit_melon.iff", inventory, "");
                    }
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has filled the entire contents of their inventory using the QA Inventory Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU, "mainMenuOptions", SCRIPT_VAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    default:
                    qa.removeScriptVars(player, SCRIPT_VAR);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
