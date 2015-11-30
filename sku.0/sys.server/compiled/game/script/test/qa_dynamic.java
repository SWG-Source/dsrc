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
import script.library.static_item;

public class qa_dynamic extends script.base_script
{
    public qa_dynamic()
    {
    }
    public static final String SCRIPTVAR = "qadynamic";
    public static final String DYNAMIC_DESCRIPTION = "This tool allows a tester to spawn armor, clothing and weapons based on a selected level.  The items spawned resemble what a player would find on a mob in-game as random loot.";
    public static final String[] DATA_SOURCE_MENU_LIST = 
    {
        "spawn dynamic armor",
        "spawn dynamic clothing",
        "spawn dynamic weapons"
    };
    public static final int DYNAMIC_ARMOR = 0;
    public static final int DYNAMIC_CLOTHING = 1;
    public static final int DYNAMIC_WEAPONS = 2;
    public static final String DYNAMIC_ARMOR_TABLE = "datatables/item/dynamic_item/types/armor.iff";
    public static final String DYNAMIC_CLOTHING_TABLE = "datatables/item/dynamic_item/types/clothing.iff";
    public static final String DYNAMIC_WEAPONS_TABLE = "datatables/item/dynamic_item/types/weapons.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_dynamic");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_dynamic");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                toolArmorMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMainOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            int idx = sui.getListboxSelectedRow(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.qaToolMainMenu(self);
                utils.removeScriptVarTree(player, SCRIPTVAR);
                return SCRIPT_CONTINUE;
            }
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case DYNAMIC_ARMOR:
                utils.setScriptVar(player, SCRIPTVAR + ".dynamicChoice", DYNAMIC_ARMOR_TABLE);
                sui.inputbox(player, player, "Enter the Armor Level.", "handleLevelSelect");
                break;
                case DYNAMIC_CLOTHING:
                utils.setScriptVar(player, SCRIPTVAR + ".dynamicChoice", DYNAMIC_CLOTHING_TABLE);
                sui.inputbox(player, player, "Enter the Clothing Level.", "handleLevelSelect");
                break;
                case DYNAMIC_WEAPONS:
                utils.setScriptVar(player, SCRIPTVAR + ".dynamicChoice", DYNAMIC_WEAPONS_TABLE);
                sui.inputbox(player, player, "Enter the Weapon Level.", "handleLevelSelect");
                break;
                default:
                qa.qaToolMainMenu(self);
                qa.removePlayer(player, SCRIPTVAR, "This Armor is not currently available.");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLevelSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SCRIPTVAR + ".dynamicChoice"))
        {
            obj_id player = sui.getPlayerId(params);
            String text = sui.getInputBoxText(params);
            int level = utils.stringToInt(text);
            if (level < 1 || level > 90)
            {
                sendSystemMessageTestingOnly(player, "Invalid level entered!");
            }
            else 
            {
                utils.setScriptVar(player, SCRIPTVAR + ".dynamicLevel", level);
                getColumnToDisplay(player);
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "There was an error with the previous selection - please try again");
            qa.removePlayer(self, SCRIPTVAR, "");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDynamicSpawn(obj_id self, dictionary params) throws InterruptedException
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
                toolArmorMainMenu(player);
                utils.removeScriptVarTree(player, SCRIPTVAR);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, SCRIPTVAR + ".listTracking"))
            {
                String[] dataTableNameColumn = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".listTracking");
                String spawnNameChoice = dataTableNameColumn[idx];
                createStatics(player, spawnNameChoice);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "There was an error with the previous selection - please try again");
                qa.removePlayer(player, SCRIPTVAR, "");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void columnNamesDisplay(obj_id player, String[] dataTableNames) throws InterruptedException
    {
        qa.refreshMenu(player, DYNAMIC_DESCRIPTION, "Dynamic Spawner", dataTableNames, "handleDynamicSpawn", SCRIPTVAR + ".pid", SCRIPTVAR + ".columnsMenu", sui.OK_CANCEL_REFRESH);
    }
    public void toolArmorMainMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, DYNAMIC_DESCRIPTION, "Dynamic Spawner", DATA_SOURCE_MENU_LIST, "handleMainOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".dynamicMainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void getColumnToDisplay(obj_id player) throws InterruptedException
    {
        String[] dataTableNameColumn = dataTableGetStringColumn(utils.getStringScriptVar(player, SCRIPTVAR + ".dynamicChoice"), "strName");
        Arrays.sort(dataTableNameColumn);
        if (dataTableNameColumn.length >= 1)
        {
            utils.setScriptVar(player, SCRIPTVAR + ".listTracking", dataTableNameColumn);
            columnNamesDisplay(player, dataTableNameColumn);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "An error has occurred, please contact the Tool Team.");
            qa.removePlayer(player, SCRIPTVAR, "");
        }
    }
    public void createStatics(obj_id player, String spawnNameChoice) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        if (utils.hasScriptVar(player, SCRIPTVAR + ".dynamicLevel"))
        {
            int chosenLevel = utils.getIntScriptVar(player, SCRIPTVAR + ".dynamicLevel");
            int number = 40;
            for (int i = 0; i < number; i++)
            {
                obj_id myObj = static_item.makeDynamicObject(spawnNameChoice, inventory, chosenLevel);
            }
            qa.findOrCreateAndEquipQABag(player, inventory, true);
            sendSystemMessageTestingOnly(player, "Spawning of dynamic items is complete");
            qa.removePlayer(player, SCRIPTVAR, "");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "There was an error with the previous selection - please try again");
            qa.removePlayer(player, SCRIPTVAR, "");
        }
    }
}
