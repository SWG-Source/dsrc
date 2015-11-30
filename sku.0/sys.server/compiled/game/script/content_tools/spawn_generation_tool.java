package script.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.trial;
import script.library.datatable;

public class spawn_generation_tool extends script.base_script
{
    public spawn_generation_tool()
    {
    }
    public static final String MASTER_SPAWN_OBJECT = "spawn.master_object";
    public static final String ASSOCIATED_SPAWN_TABLE = "spawn.associated_table";
    public static final String NOT_ASSIGNED = "Not Assigned";
    public static final String TOOL_TITLE = "Spawn Creation Tool";
    public static final String MASTER_SPAWN_SCRIPT = "systems.spawning.content_spawning_master";
    public static final String LIST_MSO_SELECT = "list.mso_selection_array";
    public static final boolean LOGGING = true;
    public int showSpawnManagerSui(obj_id self, dictionary params) throws InterruptedException
    {
        createSpawnGenerationSui(self);
        return SCRIPT_CONTINUE;
    }
    public int handleMasterListSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if (sui.getIntButtonPressed(params) == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        obj_id mso = getObjIdObjVar(player, MASTER_SPAWN_OBJECT);
        String ast = getStringObjVar(player, ASSOCIATED_SPAWN_TABLE);
        if (!isIdValid(mso) || ast.equals(NOT_ASSIGNED))
        {
            if (idx < 0 || idx > 1)
            {
                doLogging("handleMasterListSelection", "Unsigned mso/ast invalid selection occured");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case 0:
                setMasterSpawnObject(player);
                break;
                case 1:
                loadExistingSpawnData(player);
                break;
            }
        }
        else 
        {
            if (idx < 0 || idx > 2)
            {
                doLogging("handleMasterListSelection", "Signed mso/ast invalid selection occured");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case 0:
                showStandardSpawnCreationSUI(player);
                break;
                case 1:
                showCleanSlateSpawnCreationSUI(player);
                break;
                case 2:
                loadExistingSpawnData(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnGenerationSuiUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleSetMso(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            doLogging("handleSetMso", "Params were null");
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id[] objectList = utils.getObjIdArrayScriptVar(self, LIST_MSO_SELECT);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_OK:
            if (idx > -1 && idx < objectList.length)
            {
                obj_id masterObject = objectList[idx];
                if (isIdValid(masterObject))
                {
                    attachScript(masterObject, MASTER_SPAWN_SCRIPT);
                    setObjVar(player, MASTER_SPAWN_OBJECT, masterObject);
                    for (int i = 0; i < objectList.length; i++)
                    {
                        if (objectList[i] != objectList[idx])
                        {
                            trial.cleanupNpc(objectList[i]);
                        }
                    }
                }
            }
            else 
            {
                doLogging("handleSetMso", "Idx was out of bounds");
                return SCRIPT_CONTINUE;
            }
            break;
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
        }
        setAssociatedSpawnTable(player);
        return SCRIPT_CONTINUE;
    }
    public int handleAstChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            doLogging("handleAstChoice", "Params were null");
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            doLogging("handleAstChoice", "Index was invalid");
        }
        switch (bp)
        {
            case sui.BP_OK:
            if (idx == 0)
            {
                presentNewAstSUI(player);
            }
            else if (idx == 1)
            {
                presentExistingTableSui(player);
            }
            break;
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNameNewTable(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            doLogging("handleNameNewTable", "Params were null");
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String tableName = sui.getInputBoxText(params);
        if (tableName == null || tableName.equals(""))
        {
            sendSystemMessageTestingOnly(player, "You have entered a null or invalid name");
            setAssociatedSpawnTable(player);
            return SCRIPT_CONTINUE;
        }
        if (createNewAssociatedSpawnTable(player, tableName))
        {
            setObjVar(player, ASSOCIATED_SPAWN_TABLE, tableName);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Failed to create table with specified name");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void createSpawnGenerationSui(obj_id player) throws InterruptedException
    {
        obj_id mso = null;
        String ast = NOT_ASSIGNED;
        if (hasObjVar(player, MASTER_SPAWN_OBJECT))
        {
            mso = getObjIdObjVar(player, MASTER_SPAWN_OBJECT);
        }
        if (hasObjVar(player, ASSOCIATED_SPAWN_TABLE))
        {
            ast = getStringObjVar(player, ASSOCIATED_SPAWN_TABLE);
        }
        else 
        {
            setObjVar(player, ASSOCIATED_SPAWN_TABLE, NOT_ASSIGNED);
        }
        String prompt = getMsoAndAstDisplayData(player);
        int pid = createSUIPage(sui.SUI_LISTBOX, player, player, "handleMasterListSelection");
        setSUIProperty(pid, "", "Size", "900,700");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, TOOL_TITLE);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@ok");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        if (!isIdValid(mso) || ast.equals(NOT_ASSIGNED))
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 0);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 0, sui.PROP_TEXT, "Create New Spawn Data");
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 1);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 1, sui.PROP_TEXT, "Load Existing Spawn Data");
            subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
            subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
            showSUIPage(pid);
            flushSUIPage(pid);
            return;
        }
        else 
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 0);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 0, sui.PROP_TEXT, "Use Existing Data");
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 1);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 1, sui.PROP_TEXT, "Create New Spawn Data");
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 2);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 2, sui.PROP_TEXT, "Load Existing Spawn Data");
            subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
            subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
            showSUIPage(pid);
            flushSUIPage(pid);
            return;
        }
    }
    public void setMasterSpawnObject(obj_id player) throws InterruptedException
    {
        String prompt = getMsoAndAstDisplayData(player);
        prompt += "Identify Master Spawning Object\nObjects with * have the master spawn script already present";
        prompt += "\nSelect an object from the menu below, if the script is not attached it will be attached for you.";
        prompt += "\nALL OTHER OBJECTS WILL BE DESTROYED TO PREVENT INADVERTANT CAPTURE IN THE SPAWNING TABLE";
        int pid = createSUIPage(sui.SUI_LISTBOX, player, player, "handleSetMso");
        setSUIProperty(pid, "", "Size", "900,700");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, TOOL_TITLE);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@ok");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        obj_id[] allObjects = sortAllObjectsByMasterSpawnScript(getObjectsInRange(player, 50.0f));
        String[] sortedList = formatMsoSelectionSortedObjectList(allObjects);
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        if (sortedList == null || sortedList.length == 0)
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 0);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 0, sui.PROP_TEXT, "No Available Objects In Range");
        }
        else 
        {
            for (int i = 0; i < sortedList.length; i++)
            {
                addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
                setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, sortedList[i]);
            }
        }
        subscribeToSUIEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, "createSpawnGenerationSui");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        utils.setScriptVar(player, LIST_MSO_SELECT, allObjects);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
    }
    public void setAssociatedSpawnTable(obj_id player) throws InterruptedException
    {
        String prompt = getMsoAndAstDisplayData(player);
        prompt += "Load existing Table or Create new Table?";
        prompt += "\nExisting table Master Spawn Object must match your Master Spawn Object.";
        int pid = createSUIPage(sui.SUI_LISTBOX, player, player, "handleAstChoice");
        setSUIProperty(pid, "", "Size", "900,700");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, TOOL_TITLE);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@ok");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 0);
        setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 0, sui.PROP_TEXT, "Create New Associated Spawn Table");
        addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + 1);
        setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + 1, sui.PROP_TEXT, "Load Exisisting Associated Spawn Table");
        subscribeToSUIEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, "createSpawnGenerationSui");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
    }
    public void loadExistingSpawnData(obj_id player) throws InterruptedException
    {
    }
    public void showStandardSpawnCreationSUI(obj_id player) throws InterruptedException
    {
    }
    public void showCleanSlateSpawnCreationSUI(obj_id player) throws InterruptedException
    {
    }
    public void presentNewAstSUI(obj_id player) throws InterruptedException
    {
        String prompt = getMsoAndAstDisplayData(player);
        prompt += "datatables/spawning/spawn_manager/associated_master_list.tab is required for this operation. Open now";
        prompt += "\ndatatables/spawning/spawn_manager/...";
        prompt += "\nName the new table";
        int pid = createSUIPage(sui.SUI_INPUTBOX, player, player, "handleNameNewTable");
        setSUIProperty(pid, "", "Size", "900,700");
        setSUIProperty(pid, sui.INPUTBOX_TITLE, sui.PROP_TEXT, TOOL_TITLE);
        setSUIProperty(pid, sui.INPUTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.inputboxButtonSetup(pid, sui.OK_CANCEL);
        sui.inputboxStyleSetup(pid, sui.INPUT_NORMAL);
        clearSUIDataSource(pid, sui.INPUTBOX_DATASOURCE);
        subscribeToSUIProperty(pid, sui.INPUTBOX_PROMPT, sui.PROP_TEXT);
        subscribeToSUIProperty(pid, sui.INPUTBOX_TITLE, sui.PROP_TEXT);
        subscribeToSUIProperty(pid, sui.INPUTBOX_INPUT, sui.PROP_LOCALTEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
    }
    public void presentExistingTableSui(obj_id player) throws InterruptedException
    {
    }
    public boolean createNewAssociatedSpawnTable(obj_id player, String tableName) throws InterruptedException
    {
        return true;
    }
    public obj_id[] sortAllObjectsByMasterSpawnScript(obj_id[] allObjects) throws InterruptedException
    {
        Vector objectsWithScript = new Vector();
        objectsWithScript.setSize(0);
        Vector objectsWithoutScript = new Vector();
        objectsWithoutScript.setSize(0);
        for (int i = 0; i < allObjects.length; i++)
        {
            if (!isPlayer(allObjects[i]) && !isMob(allObjects[i]))
            {
                if (hasScript(allObjects[i], MASTER_SPAWN_SCRIPT))
                {
                    utils.addElement(objectsWithScript, allObjects[i]);
                }
                else 
                {
                    utils.addElement(objectsWithoutScript, allObjects[i]);
                }
            }
        }
        obj_id[] completeList = new obj_id[objectsWithScript.size() + objectsWithoutScript.size()];
        int r = 0;
        for (int k = 0; k < objectsWithScript.size(); k++)
        {
            completeList[r] = ((obj_id)objectsWithScript.get(k));
            r++;
        }
        for (int i = 0; i < objectsWithoutScript.size(); i++)
        {
            completeList[r] = ((obj_id)objectsWithoutScript.get(i));
            r++;
        }
        return completeList;
    }
    public String[] formatMsoSelectionSortedObjectList(obj_id[] allObjects) throws InterruptedException
    {
        String[] sortedList = new String[allObjects.length];
        for (int i = 0; i < allObjects.length; i++)
        {
            if (hasScript(allObjects[i], MASTER_SPAWN_SCRIPT))
            {
                sortedList[i] = "* " + getTemplateName(allObjects[i]) + " : " + allObjects[i];
            }
            else 
            {
                sortedList[i] = getTemplateName(allObjects[i]) + " : " + allObjects[i];
            }
        }
        return sortedList;
    }
    public String getMsoAndAstDisplayData(obj_id player) throws InterruptedException
    {
        obj_id mso = null;
        String ast = NOT_ASSIGNED;
        String template = "";
        if (hasObjVar(player, MASTER_SPAWN_OBJECT))
        {
            mso = getObjIdObjVar(player, MASTER_SPAWN_OBJECT);
            template = getTemplateName(mso);
        }
        if (hasObjVar(player, ASSOCIATED_SPAWN_TABLE))
        {
            ast = getStringObjVar(player, ASSOCIATED_SPAWN_TABLE);
        }
        return "Master Spawn Object: " + mso + " - " + template + "\n" + "Associated Spawn Table: " + ast + "\n\n";
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/spawn_generation_tool/" + section, message);
        }
    }
}
