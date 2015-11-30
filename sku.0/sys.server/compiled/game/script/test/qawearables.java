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
import java.util.Arrays;
import java.util.Vector;
import java.util.HashSet;
import java.util.StringTokenizer;
import script.library.qa;

public class qawearables extends script.base_script
{
    public qawearables()
    {
    }
    public static final String DATATABLE_LOCATION = "datatables/test/qa_wearables.iff";
    public String[] populateArray(obj_id player, String datatableName, String choice, String column1, String column2, boolean filtered, boolean allFunction) throws InterruptedException
    {
        String[] errorZeroLengthArray = 
        {
            "The Array was empty, could be that you passed the wrong type"
        };
        String[] firstColumnArray = dataTableGetStringColumn(datatableName, column1);
        String[] secondColumnArray = dataTableGetStringColumn(datatableName, column2);
        int listingLength = firstColumnArray.length;
        Vector rowNumVector = new Vector();
        Vector wearablesListVector = new Vector();
        int listCounter = 0;
        if (listingLength == 0)
        {
            sendSystemMessageTestingOnly(player, "Tool Not Functioning because the Datatable Rows equal ZERO!");
            Thread.dumpStack();
            return errorZeroLengthArray;
        }
        else 
        {
            for (int y = 0; y < listingLength; y++)
            {
                if (firstColumnArray[y].equals(choice))
                {
                    rowNumVector.addElement(new Integer(y));
                    listCounter++;
                }
            }
        }
        Integer[] rowNumArray = (Integer[])rowNumVector.toArray(new Integer[listCounter]);
        int arrayLength = rowNumArray.length;
        if (!filtered)
        {
            String previousString = secondColumnArray[rowNumArray[0].intValue()];
            wearablesListVector.addElement(previousString);
            for (int i = 0; i < arrayLength; i++)
            {
                if (!secondColumnArray[rowNumArray[i].intValue()].equals(previousString))
                {
                    wearablesListVector.addElement(secondColumnArray[rowNumArray[i].intValue()]);
                    previousString = secondColumnArray[rowNumArray[i].intValue()];
                }
            }
        }
        else 
        {
            for (int i = 0; i < arrayLength; i++)
            {
                wearablesListVector.addElement(secondColumnArray[rowNumArray[i].intValue()] + " Ref.# " + rowNumArray[i]);
            }
            if (!allFunction)
            {
            }
            else 
            {
                wearablesListVector.addElement("All Items Displayed");
            }
        }
        String[] wearablesArray = (String[])wearablesListVector.toArray(new String[wearablesListVector.size()]);
        return wearablesArray;
    }
    public String[] populateArray(obj_id player, String datatableName, String column) throws InterruptedException
    {
        String[] errorZeroLengthArray = 
        {
            "The Array was empty, could be that you passed the wrong type"
        };
        String[] columnArray = dataTableGetStringColumn(datatableName, column);
        int listingLength = columnArray.length;
        if (listingLength == 0)
        {
            sendSystemMessageTestingOnly(player, "Tool Not Functioning because the Datatable Rows equal ZERO!");
            return errorZeroLengthArray;
        }
        else 
        {
            HashSet theSet = new HashSet();
            for (int y = 0; y < listingLength; y++)
            {
                theSet.add(columnArray[y]);
            }
            String[] menuArray = new String[theSet.size()];
            theSet.toArray(menuArray);
            Arrays.sort(menuArray);
            return menuArray;
        }
    }
    public void constructSUI(obj_id player, String prompt, String title, String[] menuArray, String nextHandler, String scriptVarName, boolean backButton) throws InterruptedException
    {
        if (!backButton)
        {
            int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL, title, menuArray, nextHandler, true, false);
            setWindowPid(player, pid);
            utils.setScriptVar(player, scriptVarName, menuArray);
        }
        else 
        {
            int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL_REFRESH, title, menuArray, nextHandler, false, false);
            sui.listboxUseOtherButton(pid, "Back");
            sui.showSUIPage(pid);
            setWindowPid(player, pid);
            utils.setScriptVar(player, scriptVarName, menuArray);
        }
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qawearables");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qawearables");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        if (isGod(player))
        {
            if ((toLower(text)).equals("qawearables"))
            {
                String[] mainMenuArray = populateArray(player, DATATABLE_LOCATION, "wearable_specie");
                if (mainMenuArray.length < 1)
                {
                    sendSystemMessageTestingOnly(player, "Species UI creation failed.");
                }
                else 
                {
                    constructSUI(player, "Choose the species", "Wearables Spawner", mainMenuArray, "wearableTypeOptionSelect", "qawearable.mainMenu", false);
                }
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int wearableTypeOptionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (isGod(player))
        {
            if (utils.hasScriptVar(self, "qawearable.pid"))
            {
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "qawearable.mainMenu");
                if ((params == null) || (params.isEmpty()))
                {
                    sendSystemMessageTestingOnly(player, "Failing, params empty");
                    utils.removeScriptVarTree(player, "qawearable");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                int btn = sui.getIntButtonPressed(params);
                int idx = sui.getListboxSelectedRow(params);
                switch (btn)
                {
                    case sui.BP_CANCEL:
                    utils.removeScriptVarTree(player, "qawearable");
                    utils.removeScriptVarTree(player, "qatool");
                    closeOldWindow(player);
                    return SCRIPT_CONTINUE;
                    case sui.BP_REVERT:
                    String[] options = utils.getStringArrayScriptVar(player, "qatool.toolMainMenu");
                    String mainTitle = utils.getStringScriptVar(player, "qatool.title");
                    String mainPrompt = utils.getStringScriptVar(player, "qatool.prompt");
                    if (options == null)
                    {
                        sendSystemMessageTestingOnly(player, "You didn't start from the main tool menu");
                        String[] mainMenuArray = populateArray(player, DATATABLE_LOCATION, "wearable_specie");
                        qa.refreshMenu(player, "Choose the species", "Wearables Spawner", mainMenuArray, "wearableTypeOptionSelect", true, "qawearable.pid");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        qa.refreshMenu(self, mainPrompt, mainTitle, options, "toolMainMenu", true, "qatool.pid");
                        utils.removeScriptVarTree(player, "qawearable");
                        return SCRIPT_CONTINUE;
                    }
                    case sui.BP_OK:
                    if (idx < 0)
                    {
                        utils.removeScriptVarTree(player, "qawearable");
                        utils.removeScriptVarTree(player, "qatool");
                        sendSystemMessageTestingOnly(player, "You didnt have anything selected");
                        return SCRIPT_CONTINUE;
                    }
                    break;
                }
                String specieChoice = previousMainMenuArray[idx];
                if (specieChoice.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "The Script failed because the previous menu did not pass a string.");
                }
                else 
                {
                    utils.setScriptVar(player, "qawearable.specieChoiceVar", specieChoice);
                    String[] wearablesArray = populateArray(player, DATATABLE_LOCATION, specieChoice, "wearable_specie", "wearable_type", false, false);
                    if (wearablesArray.length < 1)
                    {
                        sendSystemMessageTestingOnly(player, "Wearables UI creation failed.");
                    }
                    else 
                    {
                        constructSUI(player, "Choose the wearable type", "Wearables Spawner", wearablesArray, "wearablesOptionSelect", "qawearable.wearablesMenu", true);
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Godmode needed for this command.");
        }
        return SCRIPT_CONTINUE;
    }
    public int wearablesOptionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (isGod(player))
        {
            if (utils.hasScriptVar(self, "qawearable.pid"))
            {
                String previousWearablesArray[] = utils.getStringArrayScriptVar(self, "qawearable.wearablesMenu");
                if ((params == null) || (params.isEmpty()))
                {
                    sendSystemMessageTestingOnly(player, "Failing, params empty");
                    utils.removeScriptVarTree(player, "qawearable");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                int btn = sui.getIntButtonPressed(params);
                int idx = sui.getListboxSelectedRow(params);
                switch (btn)
                {
                    case sui.BP_REVERT:
                    String[] mainMenuArray = populateArray(player, DATATABLE_LOCATION, "wearable_specie");
                    if (mainMenuArray.length < 1)
                    {
                        sendSystemMessageTestingOnly(player, "Specie UI creation failed.");
                        utils.removeScriptVarTree(player, "qawearable");
                        utils.removeScriptVarTree(player, "qatool");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        qa.refreshMenu(player, "Choose the species", "Wearables Spawner", mainMenuArray, "wearableTypeOptionSelect", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    }
                    return SCRIPT_CONTINUE;
                    case sui.BP_OK:
                    if (idx < 0)
                    {
                        utils.removeScriptVarTree(player, "qawearable");
                        utils.removeScriptVarTree(player, "qatool");
                        sendSystemMessageTestingOnly(player, "You didnt have anything selected");
                        return SCRIPT_CONTINUE;
                    }
                    break;
                    case sui.BP_CANCEL:
                    utils.removeScriptVarTree(player, "qawearable");
                    utils.removeScriptVarTree(player, "qatool");
                    closeOldWindow(player);
                    return SCRIPT_CONTINUE;
                }
                String wearableChoice = previousWearablesArray[idx];
                if (!wearableChoice.equals(""))
                {
                    String[] wearablesNameArray = populateArray(player, DATATABLE_LOCATION, wearableChoice, "wearable_type", "wearable_name", true, true);
                    if (wearablesNameArray.length < 1)
                    {
                        sendSystemMessageTestingOnly(player, "Wearables Name UI creation failed.");
                    }
                    else 
                    {
                        constructSUI(player, "Choose the wearable item", "Wearables Spawner", wearablesNameArray, "wearablesTemplateSelect", "qawearable.spawnItem", true);
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Godmode needed for this command.");
        }
        return SCRIPT_CONTINUE;
    }
    public int wearablesTemplateSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (isGod(player))
        {
            if (utils.hasScriptVar(self, "qawearable.pid"))
            {
                String previousWearablesNameArray[] = utils.getStringArrayScriptVar(self, "qawearable.spawnItem");
                if ((params == null) || (params.isEmpty()))
                {
                    sendSystemMessageTestingOnly(player, "Failing, params empty");
                    utils.removeScriptVarTree(player, "qawearable");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                obj_id pInv = utils.getInventoryContainer(player);
                int btn = sui.getIntButtonPressed(params);
                int idx = sui.getListboxSelectedRow(params);
                switch (btn)
                {
                    case sui.BP_REVERT:
                    String specieChoice = utils.getStringScriptVar(player, "qawearable.specieChoiceVar");
                    if (specieChoice != null)
                    {
                        String[] wearablesArray = populateArray(player, DATATABLE_LOCATION, specieChoice, "wearable_specie", "wearable_type", false, false);
                        if (wearablesArray.length < 1)
                        {
                            sendSystemMessageTestingOnly(player, "Specie UI creation failed.");
                        }
                        else 
                        {
                            constructSUI(player, "Choose the wearable type", "Wearables Spawner", wearablesArray, "wearablesOptionSelect", "qawearable.wearablesMenu", true);
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "The previous specie selection could not be retrieved.");
                    }
                    return SCRIPT_CONTINUE;
                    case sui.BP_OK:
                    if (idx < 0)
                    {
                        utils.removeScriptVarTree(player, "qawearable");
                        utils.removeScriptVarTree(player, "qatool");
                        sendSystemMessageTestingOnly(player, "You didnt have anything selected");
                        return SCRIPT_CONTINUE;
                    }
                    break;
                    case sui.BP_CANCEL:
                    utils.removeScriptVarTree(player, "qawearable");
                    utils.removeScriptVarTree(player, "qatool");
                    closeOldWindow(player);
                    return SCRIPT_CONTINUE;
                }
                String wearableNameChoice = previousWearablesNameArray[idx];
                String wearablesName = new String();
                String indexNum = new String();
                String refString = new String();
                String templateData = new String();
                if (!wearableNameChoice.equals(""))
                {
                    String[] wearable_name = dataTableGetStringColumn(DATATABLE_LOCATION, 2);
                    String[] wearable_template = dataTableGetStringColumn(DATATABLE_LOCATION, 3);
                    StringTokenizer stg = new StringTokenizer(wearableNameChoice);
                    wearablesName = stg.nextToken();
                    refString = stg.nextToken();
                    indexNum = stg.nextToken();
                    int listingLength = wearable_name.length;
                    int z = 0;
                    boolean haveFound = false;
                    boolean allSelected = false;
                    while (haveFound == false)
                    {
                        if (z < listingLength)
                        {
                            if (z == utils.stringToInt(indexNum))
                            {
                                templateData = wearable_template[z];
                                haveFound = true;
                            }
                            z++;
                        }
                        else 
                        {
                            allSelected = true;
                            haveFound = true;
                        }
                    }
                    if (!templateData.equals(""))
                    {
                        qa.templateObjectSpawner(player, templateData);
                    }
                    else if (allSelected == true)
                    {
                        for (int x = 0; x < (previousWearablesNameArray.length - 1); x++)
                        {
                            StringTokenizer sto = new StringTokenizer(previousWearablesNameArray[x]);
                            wearablesName = sto.nextToken();
                            refString = sto.nextToken();
                            indexNum = sto.nextToken();
                            int theIndex = utils.stringToInt(indexNum);
                            qa.templateObjectSpawner(player, wearable_template[theIndex]);
                        }
                    }
                    String[] mainMenuArray = populateArray(player, DATATABLE_LOCATION, "wearable_specie");
                    if (mainMenuArray.length < 1)
                    {
                        sendSystemMessageTestingOnly(player, "Specie UI creation failed.");
                    }
                    else 
                    {
                        qa.refreshMenu(player, "Choose the species", "Wearables Spawner", mainMenuArray, "wearableTypeOptionSelect", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "The script failed to pass the string from the previous menu.");
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Godmode needed for this command.");
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanScriptVars(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVarTree(player, "qawearable");
        utils.removeScriptVarTree(self, "qawearable");
        setObjVar(player, "qawearable", true);
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        String playerPath = "qawearable.";
        if (utils.hasScriptVar(player, "qawearable.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "qawearable.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "qawearable.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "qawearable.pid", pid);
        }
    }
}
