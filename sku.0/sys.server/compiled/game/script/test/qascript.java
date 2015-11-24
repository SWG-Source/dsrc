package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.qa;
import script.library.sui;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

public class qascript extends script.base_script
{
    public qascript()
    {
    }
    public static final String TOOL_TITLE = "QA SCRIPT TOOL";
    public static final String TOOL_PROMPT = "This tool allows the tester to see what Scripts are currently attached to their character and attach/detach each script individually to troubleshoot problems or test functionality.\n\n\rCurrent Scripts Attached:\n";
    public static final String SCRIPTVAR = "qascript";
    public static final String ERROR_MSG_STR = "There was an error in the tool.";
    public static final String[] ERROR_MSG_ARRAY = 
    {
        "There was an error in the tool."
    };
    public static final String[] TOOL_MENU = 
    {
        "Attach Script",
        "Detach Script"
    };
    public static final String[] COMMON_SCRIPTS = 
    {
        "csr.get_resource_crate",
        "e3demo.e3_demoer",
        "gm.cmd",
        "player.player_gm",
        "player.yavin_e3",
        "quest.utility.quest_test",
        "space.content_tools.content_generation",
        "space.content_tools.missiontest",
        "test.qa_cybernetic",
        "test.qa_damage",
        "test.qa_dynamic",
        "test.qa_enhancements",
        "test.qa_helper",
        "test.qa_jtl_tools",
        "test.qa_quest_skipper",
        "test.qa_resource_reward",
        "test.qa_resources",
        "test.qabackpack",
        "test.qabadge",
        "test.qabuff",
        "test.qacube",
        "test.qadatapad",
        "test.qadna",
        "test.qafaction",
        "test.qaham",
        "test.qainventory",
        "test.qaitem",
        "test.qange",
        "test.qaprofession",
        "test.qascript",
        "test.qasetup",
        "test.qaspace",
        "test.qatool",
        "test.qaweapon",
        "test.qawearables",
        "test.qaxp",
        "test.ttyson_test",
        "working.dantest"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qascript");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qascript");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals("qascript"))
            {
                toolMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(player, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    String[] tool_options = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
                    qa.refreshMenu(self, "Choose the tool you want to use", "QA Tools", tool_options, "toolMainMenu", true, "qatool.pid");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case 0:
                        toolAttachMenu(self);
                        break;
                        case 1:
                        toolDetachMenu(self);
                        break;
                        default:
                        removePlayer(player, "");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAttachScriptOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(player, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "qascript.attachMenu");
                String previousSelection = previousMainMenuArray[idx];
                if (previousSelection.equals(""))
                {
                    removePlayer(player, "");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    attachScript(self, previousSelection);
                    sendSystemMessageTestingOnly(player, previousSelection + " script attached.");
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has attached script (" + previousSelection + ") using the QA Script Tool.");
                    toolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDetachScriptOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(player, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "qascript.detachMenu");
                String previousSelection = previousMainMenuArray[idx];
                if (previousSelection.equals(""))
                {
                    removePlayer(player, "");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    detachScript(self, previousSelection);
                    sendSystemMessageTestingOnly(player, previousSelection + " script detached.");
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has detached script (" + previousSelection + ") using the QA Script Tool.");
                    toolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id player) throws InterruptedException
    {
        String toolPrompt = scriptToolPromptMaker(player);
        qa.refreshMenu(player, toolPrompt, TOOL_TITLE, TOOL_MENU, "handleMainMenuOptions", "qascript.pid", "qascript.mainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void toolAttachMenu(obj_id player) throws InterruptedException
    {
        String toolPrompt = "ATTACH MODE\n\n" + scriptToolPromptMaker(player);
        String[] attachMenu = getAttachableCharacterScripts(player);
        if (attachMenu.length > -1)
        {
            qa.refreshMenu(player, toolPrompt, TOOL_TITLE, attachMenu, "handleAttachScriptOptions", "qascript.pid", "qascript.attachMenu", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "The menu could not be built because there were no menu items to display");
        }
    }
    public void toolDetachMenu(obj_id player) throws InterruptedException
    {
        String toolPrompt = "DETACH MODE\n\n" + scriptToolPromptMaker(player);
        String[] detachMenu = getCharacterScripts(player);
        if (detachMenu.length > -1)
        {
            qa.refreshMenu(player, toolPrompt, TOOL_TITLE, detachMenu, "handleDetachScriptOptions", "qascript.pid", "qascript.detachMenu", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "The menu could not be built because there were no menu items to display");
        }
    }
    public String scriptToolPromptMaker(obj_id player) throws InterruptedException
    {
        String scriptList = getCharacterScriptsPrompt(player);
        return TOOL_PROMPT + scriptList;
    }
    public String[] filterCharacterScripts(obj_id player) throws InterruptedException
    {
        String strScripts = "";
        String[] scriptArray = getScriptList(player);
        if (scriptArray.length > -1)
        {
            HashSet theSet = new HashSet();
            for (int i = 0; i < scriptArray.length; i++)
            {
                String script = scriptArray[i];
                if (script.indexOf("script.") > -1)
                {
                    script = script.substring(7);
                    theSet.add(script);
                }
            }
            String[] menuArray = new String[theSet.size()];
            theSet.toArray(menuArray);
            Arrays.sort(menuArray);
            return menuArray;
        }
        return ERROR_MSG_ARRAY;
    }
    public String getCharacterScriptsPrompt(obj_id player) throws InterruptedException
    {
        String strScripts = "";
        String[] scriptArray = filterCharacterScripts(player);
        if (scriptArray.length > -1)
        {
            if (!scriptArray[0].equals(ERROR_MSG_ARRAY[0]))
            {
                for (int i = 0; i < scriptArray.length; i++)
                {
                    if (scriptArray[i].startsWith("test."))
                    {
                        strScripts += scriptArray[i] + "\r\n";
                    }
                    else 
                    {
                        for (int s = 0; s < COMMON_SCRIPTS.length; s++)
                        {
                            if (scriptArray[i].equals(COMMON_SCRIPTS[s]))
                            {
                                strScripts += scriptArray[i] + "\r\n";
                            }
                        }
                    }
                }
                return strScripts;
            }
        }
        return ERROR_MSG_STR;
    }
    public String[] getCharacterScripts(obj_id player) throws InterruptedException
    {
        HashSet theSet = new HashSet();
        String[] scriptArray = filterCharacterScripts(player);
        if (scriptArray.length > -1)
        {
            if (!scriptArray[0].equals(ERROR_MSG_ARRAY[0]))
            {
                for (int i = 0; i < scriptArray.length; i++)
                {
                    if (scriptArray[i].startsWith("test."))
                    {
                        theSet.add(scriptArray[i]);
                    }
                    else 
                    {
                        for (int s = 0; s < COMMON_SCRIPTS.length; s++)
                        {
                            if (scriptArray[i].equals(COMMON_SCRIPTS[s]))
                            {
                                theSet.add(scriptArray[i]);
                            }
                        }
                    }
                }
                String[] menuArray = new String[theSet.size()];
                theSet.toArray(menuArray);
                Arrays.sort(menuArray);
                return menuArray;
            }
        }
        return ERROR_MSG_ARRAY;
    }
    public String[] getAttachableCharacterScripts(obj_id player) throws InterruptedException
    {
        HashSet theSet = new HashSet();
        for (int i = 0; i < COMMON_SCRIPTS.length; i++)
        {
            if (findAttached(player, COMMON_SCRIPTS[i]) == true)
            {
                theSet.add(COMMON_SCRIPTS[i]);
            }
        }
        String[] menuArray = new String[theSet.size()];
        theSet.toArray(menuArray);
        Arrays.sort(menuArray);
        return menuArray;
    }
    public boolean findAttached(obj_id player, String scriptAttached) throws InterruptedException
    {
        String[] arrayOfCurrentScripts = getCharacterScripts(player);
        if (arrayOfCurrentScripts.length > -1)
        {
            for (int x = 0; x < arrayOfCurrentScripts.length; x++)
            {
                if (arrayOfCurrentScripts[x].equals(scriptAttached))
                {
                    return false;
                }
            }
        }
        return true;
    }
    public void removePlayer(obj_id player, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(player, err);
        qa.removeScriptVars(player, SCRIPTVAR);
        utils.removeScriptVarTree(player, SCRIPTVAR);
    }
}
