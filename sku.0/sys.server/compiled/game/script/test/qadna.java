package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.incubator;
import script.library.qa;
import script.library.beast_lib;

public class qadna extends script.base_script
{
    public qadna()
    {
    }
    public static final String INCUBATOR_TEMPLATES = "datatables/beast/incubator_templates.iff";
    public static final String SCRIPTVAR = "qadna";
    public static final String DNA_PROMPT = "Choose the creature you want to get DNA from. \nThe chosen DNA will be created in your inventory.";
    public static final String DNA_TITLE = "QA DNA Tool";
    public static final String[] QATOOL_MAIN_MENU = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
    public static final String QATOOL_TITLE = "QA Tools";
    public static final String QATOOL_PROMPT = "Choose the tool you want to use";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "test.qadna");
        }
        else if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qadna");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals("qadna"))
            {
                getCreatureList(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePetOptionsTool(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
        {
            qa.checkParams(params, SCRIPTVAR);
            obj_id player = sui.getPlayerId(params);
            int idx = sui.getListboxSelectedRow(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.refreshMenu(self, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
                utils.removeScriptVarTree(player, "qadna");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.refreshMenu(self, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
                utils.removeScriptVarTree(player, "qadna");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                switch (idx)
                {
                    case 0:
                    getCreatureList(self);
                    break;
                    case 1:
                    sendSystemMessageTestingOnly(self, "These pet options are not yet available.");
                    qa.refreshMenu(self, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
                    utils.removeScriptVarTree(player, "qadna");
                    break;
                    default:
                    removePlayer(player, "Default Option on Switch");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDnaOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
        {
            qa.checkParams(params, SCRIPTVAR);
            obj_id player = sui.getPlayerId(params);
            int idx = sui.getListboxSelectedRow(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.refreshMenu(self, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
                utils.removeScriptVarTree(player, "qadna");
                return SCRIPT_CONTINUE;
            }
            else if (btn == sui.BP_REVERT)
            {
                qa.refreshMenu(self, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
                utils.removeScriptVarTree(player, "qadna");
                return SCRIPT_CONTINUE;
            }
            else if (idx < 0)
            {
                removePlayer(player, "Index less than zero");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String chosenDnaItem = dataTableGetString(INCUBATOR_TEMPLATES, idx, "initial_template");
                String creature = getDisplayName(chosenDnaItem);
                setUpDnaWithDummyData(self, chosenDnaItem, creature);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id self, String[] dnaCreatureArray) throws InterruptedException
    {
        qa.refreshMenu(self, DNA_PROMPT, DNA_TITLE, dnaCreatureArray, "handleDnaOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".mainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void getCreatureList(obj_id self) throws InterruptedException
    {
        Vector dnaCreatures = new Vector();
        String[] dnaCreatureStringColumn = dataTableGetStringColumn(INCUBATOR_TEMPLATES, "initial_template");
        if (dnaCreatureStringColumn.length > -1)
        {
            for (int i = 0; i < dnaCreatureStringColumn.length; i++)
            {
                String creatureDisplayName = getDisplayName(dnaCreatureStringColumn[i]);
                dnaCreatures.add(creatureDisplayName);
            }
            if (dnaCreatures.size() >= 1)
            {
                String[] dnaCreatureArray = new String[dnaCreatures.size()];
                dnaCreatures.toArray(dnaCreatureArray);
                toolMainMenu(self, dnaCreatureArray);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "There is an error with this tool, if the issue persists, please contact the tool team.");
                removePlayer(self, "");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "There is an error with this tool, if the issue persists, please contact the tool team.");
            removePlayer(self, "");
        }
    }
    public String getDisplayName(String creatureName) throws InterruptedException
    {
        if (creatureName.indexOf("/") > -1)
        {
            String[] splitType = split(creatureName, '/');
            creatureName = splitType[3];
        }
        if (creatureName.indexOf(".") > -1)
        {
            String[] splitAtDot = split(creatureName, '.');
            creatureName = splitAtDot[0];
        }
        creatureName = beast_lib.stripBmFromType(creatureName);
        return creatureName;
    }
    public void setUpDnaWithDummyData(obj_id self, String creatureTemplate, String creature) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(self);
        obj_id dnaContainer = createObjectOverloaded("object/tangible/loot/beast/dna_container.iff", pInv);
        incubator.initializeDna(dnaContainer, self);
        setObjVar(dnaContainer, incubator.DNA_PARENT_TEMPLATE, creature);
        int row = dataTableSearchColumnForString(creatureTemplate, "initial_template", INCUBATOR_TEMPLATES);
        int hashTemplate = dataTableGetInt(INCUBATOR_TEMPLATES, row, "hash_initial_template");
        setObjVar(dnaContainer, incubator.DNA_TEMPLATE_OBJVAR, hashTemplate);
        sendSystemMessageTestingOnly(self, "A " + creature + " DNA sample has been placed in your inventory.");
        removePlayer(self, "");
    }
    public void removePlayer(obj_id self, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, err);
        qa.removeScriptVars(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, SCRIPTVAR);
    }
}
