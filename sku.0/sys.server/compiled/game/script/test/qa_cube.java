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

public class qa_cube extends script.base_script
{
    public qa_cube()
    {
    }
    public static final String SCRIPTVAR = "qa_cube";
    public static final String CUBE_DATATABLE_1 = "datatables/item/loot_cube/republic_assembly_tool.iff";
    public static final String CHU_GON_DAR_CUBE = "object/tangible/container/loot/som_cube.iff";
    public static final String CHU_GON_DAR_TITLE = "Chu-Gon Dar Cube Tool";
    public static final String CHU_GON_DAR_PROMPT = "This tool allows you to quickly obtain items needed to create the items listed below.\n**If you do not have a Cube, one will be created for you.**\n\nSelect an item to create.";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_cube");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_cube");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR) || (toLower(text)).equals("qacube"))
            {
                getNamesArray(self);
                ChuGonMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChuGonOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.qaToolMainMenu(player);
                utils.removeScriptVarTree(player, SCRIPTVAR);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            String[] itemList = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".codeStringArray");
            String itemToSpawnFor = itemList[idx];
            spawnBaseItems(player, itemToSpawnFor, idx);
        }
        return SCRIPT_CONTINUE;
    }
    public void ChuGonMainMenu(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, SCRIPTVAR + ".codeStringArray") && utils.hasScriptVar(player, SCRIPTVAR + ".showNamesArray"))
        {
            String[] showNamesArray = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".showNamesArray");
            qa.refreshMenu(player, CHU_GON_DAR_PROMPT, CHU_GON_DAR_TITLE, showNamesArray, "handleChuGonOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".ChuGonMainMenu", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "An error has occurred, please try again.");
        }
    }
    public void getNamesArray(obj_id player) throws InterruptedException
    {
        String[] codeStringArray = dataTableGetStringColumn(CUBE_DATATABLE_1, "finalTemplate");
        String[] showNamesArray = new String[codeStringArray.length];
        for (int i = 0; i < codeStringArray.length; i++)
        {
            if (codeStringArray[i].endsWith(".iff"))
            {
                int idxSlash = codeStringArray[i].lastIndexOf("/") + 1;
                int idxPeriod = codeStringArray[i].lastIndexOf(".");
                String lookUp = codeStringArray[i].substring(idxSlash, idxPeriod) + "_n";
                showNamesArray[i] = localize(new string_id("som/som_item", lookUp));
            }
            else 
            {
                showNamesArray[i] = localize(new string_id("static_item_n", codeStringArray[i]));
            }
            showNamesArray[i] = showNamesArray[i] + ": (" + codeStringArray[i] + ")";
        }
        utils.setScriptVar(player, SCRIPTVAR + ".codeStringArray", codeStringArray);
        utils.setScriptVar(player, SCRIPTVAR + ".showNamesArray", showNamesArray);
    }
    public void checkForCube(obj_id player) throws InterruptedException
    {
        checkInventory(player);
        boolean hasCube = false;
        obj_id[] invAndEquip = getInventoryAndEquipment(player);
        for (int i = 0; i < invAndEquip.length; i++)
        {
            String templateName = getTemplateName(invAndEquip[i]);
            if (templateName.equals("object/tangible/container/loot/som_cube.iff"))
            {
                hasCube = true;
            }
        }
        if (hasCube == false)
        {
            obj_id myCube = createObjectInInventoryAllowOverload("object/tangible/container/loot/som_cube.iff", player);
        }
    }
    public void spawnBaseItems(obj_id player, String itemToSpawnFor, int idx) throws InterruptedException
    {
        checkForCube(player);
        obj_id inventory = utils.getInventoryContainer(player);
        for (int i = 0; i < 3; i++)
        {
            String subComponent = dataTableGetString(CUBE_DATATABLE_1, idx, i);
            obj_id newObj = createObject(subComponent, inventory, "");
        }
        sendSystemMessageTestingOnly(player, "The components have been successfully created in your inventory.");
        qa.removePlayer(player, SCRIPTVAR, "");
    }
    public void checkInventory(obj_id player) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] invItems = getContents(inventory);
        if (invItems.length > 75)
        {
            sendSystemMessageTestingOnly(player, "Please delete some items from your Inventory and try again.");
        }
    }
}
