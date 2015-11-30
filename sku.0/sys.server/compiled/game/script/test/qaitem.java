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
import script.library.static_item;
import script.library.skill_template;

public class qaitem extends script.base_script
{
    public qaitem()
    {
    }
    public static final String SCRIPTVAR = "qaitem";
    public static final String EQUIPMENT_TOOL_TITLE = "QA MASTER ITEM TOOL";
    public static final String EQUIPMENT_TOOL_PROMPT = "This tool allows the tester to find items such as reward armor and weapons.";
    public static final String ROADMAP_TOOL_TITLE = "QA ROADMAP ITEM TOOL";
    public static final String ROADMAP_TOOL_PROMPT = "This tool allows the tester to spawn all profession roadmap items regardless of what their current test character profession is.";
    public static final String CATEGORY_TOOL_TITLE = "QA ITEM CATEGORIES";
    public static final String CATEGORY_TOOL_PROMPT = "This tool allows the tester to browse the master item table dynamically, based on item categories specified in the template name of the item.  To use this tool you may need to know what category the item belongs to.  For instance, a ranged weapon belongs to the weapon category.";
    public static final String[] EQUIPMENT_TOOL_MENU = 
    {
        "Armor",
        "Best Weapons",
        "Get all Certified Weapons",
        "Get Roadmap Items",
        "List Every Item by Category"
    };
    public static final String[] LENGTHY_CATEGORY_LIST = 
    {
        "armor",
        "loot",
        "weapon",
        "wearables",
        "ranged"
    };
    public static final String[] ERROR_MESSAGE_IN_ARRAY = 
    {
        "The list came back blank.  Please inform the tool team."
    };
    public static final String[] ARMOR_CLASSES = 
    {
        "Assault",
        "Battle",
        "Recon",
        "Robe",
        "Mandalorian (Restuss)",
        "Clothing"
    };
    public static final int ASSAULT = 0;
    public static final int BATTLE = 1;
    public static final int RECON = 2;
    public static final int ROBE = 3;
    public static final int MANDALORIAN = 4;
    public static final int CLOTHING = 5;
    public static final String[] MAND_COLORS = 
    {
        "Black",
        "Blue",
        "Green",
        "Red",
        "White"
    };
    public static final int BLACK = 0;
    public static final int BLUE = 1;
    public static final int GREEN = 2;
    public static final int RED = 3;
    public static final int WHITE = 4;
    public static final int NO_COLOR = 5;
    public static final String STAT_EXPLANATION = "If Applicable:\n'con' = constitution\n'sta' = stamina\n'stat' = multiple stats\n'pre' = precision\n'agi' = agility\n'lck' = luck";
    public static final String STATIC_LOOT_TABLE = "datatables/item/master_item/master_item.iff";
    public static final String ITEM_REWARD_TABLE = "datatables/roadmap/item_rewards.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qaitem");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qaitem");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
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
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    qa.qaToolMainMenu(player);
                    utils.removeScriptVarTree(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case 0:
                        toolArmorMainMenu(self);
                        break;
                        case 1:
                        getEquipment(self, 5);
                        break;
                        case 2:
                        getEquipment(self, 1);
                        break;
                        case 3:
                        String[] professionList = getProfessionList(self);
                        qa.refreshMenu(self, ROADMAP_TOOL_PROMPT, ROADMAP_TOOL_TITLE, professionList, "handleProfessionOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".professions", sui.OK_CANCEL_REFRESH);
                        break;
                        case 4:
                        String[] staticItemCategoryList = getAllStaticItemCategories(self);
                        qa.refreshMenu(self, CATEGORY_TOOL_PROMPT, CATEGORY_TOOL_TITLE, staticItemCategoryList, "handleCategorySelection", SCRIPTVAR + ".pid", SCRIPTVAR + ".categories", sui.OK_CANCEL_REFRESH);
                        break;
                        default:
                        qa.removePlayer(player, SCRIPTVAR, "Default Option on Switch");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleProfessionOptions(obj_id self, dictionary params) throws InterruptedException
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
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professions");
                String previousSelection = previousMainMenuArray[idx];
                if (!previousSelection.equals(""))
                {
                    getRoadmapItems(self, previousSelection);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEquipementOptions(obj_id self, dictionary params) throws InterruptedException
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
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".foundComboStrings");
                String codeStringArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".foundStrings");
                String previousSelection = previousMainMenuArray[idx];
                String theSpawnCode = codeStringArray[idx];
                if (previousMainMenuArray.length < 0)
                {
                    qa.removePlayer(player, SCRIPTVAR, "There was an error in the tool.  Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    qa.spawnStaticItemInInventory(self, theSpawnCode, previousSelection);
                    rebuildTheSUI(self, previousMainMenuArray, codeStringArray);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCategorySelection(obj_id self, dictionary params) throws InterruptedException
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
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".categories");
                String previousSelection = previousMainMenuArray[idx];
                if (!previousSelection.equals(""))
                {
                    boolean filterSelectionFurther = false;
                    String selection = "/" + previousSelection + "/";
                    for (int i = 0; i < LENGTHY_CATEGORY_LIST.length; i++)
                    {
                        if (previousSelection.equals(LENGTHY_CATEGORY_LIST[i]))
                        {
                            filterSelectionFurther = true;
                            break;
                        }
                    }
                    if (!filterSelectionFurther)
                    {
                        String[] allSelected = getSelectedCategory(self, selection);
                        qa.refreshMenu(self, "List of all Static Items labeled " + previousSelection, CATEGORY_TOOL_TITLE, allSelected, "handleStaticItemSpawn", SCRIPTVAR + ".pid", SCRIPTVAR + ".selectedList", sui.OK_CANCEL_REFRESH);
                    }
                    else 
                    {
                        filterSelection(self, selection);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStaticItemSpawn(obj_id self, dictionary params) throws InterruptedException
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
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".selectedList");
                String previousSelection = previousMainMenuArray[idx];
                if (!previousSelection.equals(""))
                {
                    int staticCodeIdx = previousSelection.indexOf("{") + 2;
                    int staticCodeEnd = previousSelection.length() - 2;
                    String staticCode = previousSelection.substring(staticCodeIdx, staticCodeEnd);
                    String staticString = previousSelection.substring(0, (staticCodeIdx - 3));
                    qa.spawnStaticItemInInventory(self, staticCode, staticString);
                    qa.refreshMenu(self, "List of all Static Items labeled " + previousSelection, CATEGORY_TOOL_TITLE, previousMainMenuArray, "handleStaticItemSpawn", SCRIPTVAR + ".pid", SCRIPTVAR + ".selectedList", sui.OK_CANCEL_REFRESH);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleArmorOptions(obj_id self, dictionary params) throws InterruptedException
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
                toolMainMenu(self);
                return SCRIPT_CONTINUE;
            }
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case ASSAULT:
                utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "armor_assault");
                getLevelsToDisplay(player, "armor_assault");
                break;
                case BATTLE:
                utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "armor_battle");
                getLevelsToDisplay(player, "armor_battle");
                break;
                case RECON:
                utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "armor_recon");
                getLevelsToDisplay(player, "armor_recon");
                break;
                case ROBE:
                utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "item_jedi_robe");
                getLevelsToDisplay(player, "item_jedi_robe");
                break;
                case MANDALORIAN:
                utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "armor_mandalorian");
                getLevelsToDisplay(player, "armor_mandalorian");
                break;
                case CLOTHING:
                utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "item_clothing");
                getLevelsToDisplay(player, "item_clothing");
                break;
                default:
                sendSystemMessageTestingOnly(player, "That Armor is not currently available.");
                toolArmorMainMenu(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLevelOptions(obj_id self, dictionary params) throws InterruptedException
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
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "Index less than zero");
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, SCRIPTVAR + ".armorChoice"))
            {
                String[] levelList = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".levelList");
                int idxValue = utils.stringToInt(levelList[idx]);
                getArmorList(player, idxValue);
            }
            else 
            {
                qa.removePlayer(player, SCRIPTVAR, "There was an error with the previous selection - please try again");
                toolArmorMainMenu(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnOptions(obj_id self, dictionary params) throws InterruptedException
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
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "Index less than zero");
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, SCRIPTVAR + ".armorChoice"))
            {
                String armorChoice = utils.getStringScriptVar(player, SCRIPTVAR + ".armorChoice");
                if (armorChoice.equals("armor_mandalorian"))
                {
                    String[] faction = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".statArray");
                    String factionString = faction[idx];
                    utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", armorChoice + "_" + factionString);
                    mandalorianColor(player);
                }
                else 
                {
                    String[] armorModSet = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".statArray");
                    String idxString = armorModSet[idx];
                    spawnItems(player, idxString);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "There was an error with the previous selection - please try again");
                qa.refreshMenu(player, "Select an Armor Class", "Armor Tool", ARMOR_CLASSES, "handleArmorOptions", SCRIPTVAR + ".armorType.pid", SCRIPTVAR + ".armorTypeMenu", sui.OK_CANCEL_REFRESH);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMandalorianColorOptions(obj_id self, dictionary params) throws InterruptedException
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
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "Index less than zero");
                return SCRIPT_CONTINUE;
            }
            String myColor = "";
            switch (idx)
            {
                case BLACK:
                myColor = "black";
                spawnItems(player, myColor);
                break;
                case BLUE:
                myColor = "blue";
                spawnItems(player, myColor);
                break;
                case GREEN:
                myColor = "green";
                spawnItems(player, myColor);
                break;
                case RED:
                myColor = "red";
                spawnItems(player, myColor);
                break;
                case WHITE:
                myColor = "white";
                spawnItems(player, myColor);
                break;
                default:
                sendSystemMessageTestingOnly(player, "This Armor is not currently available.");
                qa.refreshMenu(player, "Select an Armor Class", "Armor Tool", ARMOR_CLASSES, "handleArmorOptions", SCRIPTVAR + ".armorType.pid", SCRIPTVAR + ".armorTypeMenu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, EQUIPMENT_TOOL_PROMPT, EQUIPMENT_TOOL_TITLE, EQUIPMENT_TOOL_MENU, "handleMainMenuOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".mainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void getRoadmapItems(obj_id self, String previousSelection) throws InterruptedException
    {
        Vector foundRows = new Vector();
        String[] datatableProfessionNames = dataTableGetStringColumn(ITEM_REWARD_TABLE, "roadmapTemplateName");
        for (int i = 0; i < datatableProfessionNames.length; i++)
        {
            if (datatableProfessionNames[i].equals(previousSelection))
            {
                foundRows.add(dataTableGetString(ITEM_REWARD_TABLE, i, "itemDefault"));
            }
        }
        String[] allRoadmapItems = new String[foundRows.size()];
        foundRows.toArray(allRoadmapItems);
        for (int a = 0; a < allRoadmapItems.length; a++)
        {
            if (!allRoadmapItems[a].equals(""))
            {
                qa.spawnStaticItemInInventory(self, allRoadmapItems[a], "none");
            }
        }
    }
    public String[] getProfessionList(obj_id self) throws InterruptedException
    {
        HashSet professionNames = new HashSet();
        String[] datatableProfessionNames = dataTableGetStringColumn(ITEM_REWARD_TABLE, "roadmapTemplateName");
        for (int i = 0; i < datatableProfessionNames.length; i++)
        {
            professionNames.add(datatableProfessionNames[i]);
        }
        String[] oneOfEachProfession = new String[professionNames.size()];
        professionNames.toArray(oneOfEachProfession);
        Arrays.sort(oneOfEachProfession);
        return oneOfEachProfession;
    }
    public void getEquipment(obj_id self, int searchInt) throws InterruptedException
    {
        int combatLevel = getLevel(self);
        String classTemplate = getSkillTemplate(self);
        dictionary[] allRowsOfEquipment = getAllEquipmentOfType(self, searchInt);
        if (allRowsOfEquipment.length > -1)
        {
            dictionary[] allEquipmentOfLevel = getAllEquipmentOfCombatLevelOrBelow(self, allRowsOfEquipment, combatLevel);
            if (allEquipmentOfLevel.length > -1)
            {
                dictionary[] allEquipmentOfProfession = getAllEquipmentOfProfession(self, allEquipmentOfLevel, classTemplate);
                if (allEquipmentOfProfession.length > -1)
                {
                    if (searchInt == 1)
                    {
                        buildTheSUI(self, allEquipmentOfProfession);
                    }
                    else if (searchInt == 5)
                    {
                        dictionary[] allTheBestItems = getHighestTierItems(self, allEquipmentOfProfession);
                        if (allTheBestItems.length > -1)
                        {
                            buildTheSUI(self, allTheBestItems);
                        }
                    }
                }
                else 
                {
                    qa.removePlayer(self, SCRIPTVAR, "error");
                }
            }
            else 
            {
                qa.removePlayer(self, SCRIPTVAR, "error");
            }
        }
        else 
        {
            qa.removePlayer(self, SCRIPTVAR, "error");
        }
    }
    public void rebuildTheSUI(obj_id self, String[] comboArray, String[] codeStrings) throws InterruptedException
    {
        if (comboArray.length > -1 && codeStrings.length > -1)
        {
            utils.setScriptVar(self, SCRIPTVAR + ".foundStrings", codeStrings);
            qa.refreshMenu(self, EQUIPMENT_TOOL_PROMPT + "\n\nRECORDS RETURNED: " + comboArray.length, EQUIPMENT_TOOL_TITLE, comboArray, "handleEquipementOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".foundComboStrings", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            qa.removePlayer(self, SCRIPTVAR, "There was a tool failure.");
        }
    }
    public void buildTheSUI(obj_id self, dictionary[] allRows) throws InterruptedException
    {
        Vector codeStrings = new Vector();
        Vector comboStrings = new Vector();
        for (int a = 0; a < allRows.length; a++)
        {
            codeStrings.add(allRows[a].getString("name"));
        }
        String[] allCodeStrings = new String[codeStrings.size()];
        codeStrings.toArray(allCodeStrings);
        String[] arrayOfAllItemStringNames = new String[codeStrings.size()];
        for (int i = 0; i < allCodeStrings.length; i++)
        {
            arrayOfAllItemStringNames[i] = localize(new string_id("static_item_n", allCodeStrings[i]));
        }
        String[] suiArray = new String[codeStrings.size()];
        for (int z = 0; z < allCodeStrings.length; z++)
        {
            suiArray[z] = arrayOfAllItemStringNames[z] + "\t ( " + allCodeStrings[z] + " )";
        }
        if (suiArray.length > -1)
        {
            utils.setScriptVar(self, SCRIPTVAR + ".foundStrings", allCodeStrings);
            qa.refreshMenu(self, EQUIPMENT_TOOL_PROMPT + "\n\nRECORDS RETURNED: " + suiArray.length, EQUIPMENT_TOOL_TITLE, suiArray, "handleEquipementOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".foundComboStrings", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            qa.removePlayer(self, SCRIPTVAR, "There was a tool failure.");
        }
    }
    public dictionary[] searchForSpeciesArmor(obj_id self, dictionary[] allRows, String speciesSearch) throws InterruptedException
    {
        Vector foundRows = new Vector();
        String armorCodeString = "";
        for (int i = 0; i < allRows.length; i++)
        {
            armorCodeString = allRows[i].getString("name");
            if (armorCodeString.indexOf(speciesSearch) > -1)
            {
                armorCodeString = allRows[i].getString("loops");
                foundRows.add("" + i);
            }
        }
        String[] allArmorOfSpecies = new String[foundRows.size()];
        foundRows.toArray(allArmorOfSpecies);
        dictionary[] armorBasedOnSpecies = new dictionary[allArmorOfSpecies.length];
        for (int i = 0; i < allArmorOfSpecies.length; i++)
        {
            armorBasedOnSpecies[i] = allRows[utils.stringToInt(allArmorOfSpecies[i])];
        }
        return armorBasedOnSpecies;
    }
    public dictionary[] searchForProfessionArmorType(obj_id self, dictionary[] allRows, String armorType) throws InterruptedException
    {
        Vector foundRows = new Vector();
        String armorCodeString = "";
        for (int i = 0; i < allRows.length; i++)
        {
            armorCodeString = allRows[i].getString("name");
            if (armorCodeString.indexOf(armorType) > -1)
            {
                foundRows.add("" + i);
            }
        }
        String[] allArmorOfType = new String[foundRows.size()];
        foundRows.toArray(allArmorOfType);
        dictionary[] armorBasedOnType = new dictionary[allArmorOfType.length];
        for (int i = 0; i < allArmorOfType.length; i++)
        {
            armorBasedOnType[i] = allRows[utils.stringToInt(allArmorOfType[i])];
        }
        return armorBasedOnType;
    }
    public String getSpeciesSearchString(obj_id self) throws InterruptedException
    {
        String speciesSearchString = "";
        if (getSpecies(self) == SPECIES_ITHORIAN)
        {
            speciesSearchString = "ithor";
        }
        else if (getSpecies(self) == SPECIES_WOOKIEE)
        {
            speciesSearchString = "wook";
        }
        return speciesSearchString;
    }
    public dictionary[] getAllEquipmentOfProfession(obj_id self, dictionary[] allRows, String classTemplate) throws InterruptedException
    {
        Vector foundRows = new Vector();
        String reqProfession = "";
        for (int i = 0; i < allRows.length; i++)
        {
            reqProfession = allRows[i].getString("required_skill");
            if (reqProfession.equals("") || classTemplate.startsWith(reqProfession))
            {
                foundRows.add("" + i);
            }
        }
        String[] allEquipOfProfession = new String[foundRows.size()];
        foundRows.toArray(allEquipOfProfession);
        dictionary[] equipmentBasedOnProfession = new dictionary[allEquipOfProfession.length];
        for (int i = 0; i < allEquipOfProfession.length; i++)
        {
            equipmentBasedOnProfession[i] = allRows[utils.stringToInt(allEquipOfProfession[i])];
        }
        return equipmentBasedOnProfession;
    }
    public dictionary[] getAllEquipmentOfType(obj_id self, int searchInt) throws InterruptedException
    {
        if (searchInt == 4)
        {
            searchInt = 2;
        }
        else if (searchInt == 5)
        {
            searchInt = 1;
        }
        Vector foundItemRowNumbers = new Vector();
        int[] allTypesInDatatable = dataTableGetIntColumn(STATIC_LOOT_TABLE, "type");
        for (int a = 0; a < allTypesInDatatable.length; a++)
        {
            if (allTypesInDatatable[a] == searchInt)
            {
                foundItemRowNumbers.add("" + a);
            }
        }
        String[] allEquipRow = new String[foundItemRowNumbers.size()];
        foundItemRowNumbers.toArray(allEquipRow);
        dictionary[] equipmentRows = new dictionary[allEquipRow.length];
        for (int i = 0; i < allEquipRow.length; i++)
        {
            equipmentRows[i] = dataTableGetRow(STATIC_LOOT_TABLE, utils.stringToInt(allEquipRow[i]));
        }
        return equipmentRows;
    }
    public dictionary[] getAllEquipmentOfCombatLevelOrBelow(obj_id self, dictionary[] allRows, int combatLevel) throws InterruptedException
    {
        Vector foundRows = new Vector();
        int reqLevel = 0;
        for (int i = 0; i < allRows.length; i++)
        {
            reqLevel = allRows[i].getInt("required_level");
            if (reqLevel == 0 || reqLevel <= combatLevel)
            {
                foundRows.add("" + i);
            }
        }
        String[] allEquipOfCombatLvl = new String[foundRows.size()];
        foundRows.toArray(allEquipOfCombatLvl);
        dictionary[] equipmentPerCombatLevel = new dictionary[allEquipOfCombatLvl.length];
        for (int i = 0; i < allEquipOfCombatLvl.length; i++)
        {
            equipmentPerCombatLevel[i] = allRows[utils.stringToInt(allEquipOfCombatLvl[i])];
        }
        return equipmentPerCombatLevel;
    }
    public dictionary[] getHighestTierItems(obj_id self, dictionary[] listOfItems) throws InterruptedException
    {
        Vector foundRows = new Vector();
        int getHighest = 0;
        int tempTier = 0;
        if (listOfItems.length > -1)
        {
            for (int i = 0; i < listOfItems.length; i++)
            {
                if (listOfItems[i].getInt("tier") > getHighest)
                {
                    getHighest = listOfItems[i].getInt("tier");
                }
            }
        }
        if (getHighest > 0)
        {
            for (int a = 0; a < listOfItems.length; a++)
            {
                if (listOfItems[a].getInt("tier") == getHighest)
                {
                    foundRows.add("" + a);
                }
            }
        }
        String[] foundHighestTier = new String[foundRows.size()];
        foundRows.toArray(foundHighestTier);
        dictionary[] returnedHighestTier = new dictionary[foundHighestTier.length];
        for (int b = 0; b < foundHighestTier.length; b++)
        {
            returnedHighestTier[b] = listOfItems[utils.stringToInt(foundHighestTier[b])];
        }
        return returnedHighestTier;
    }
    public String[] getAllStaticItemCategories(obj_id self) throws InterruptedException
    {
        HashSet allCategories = new HashSet();
        String[] entireTemplateColumn = dataTableGetStringColumn(STATIC_LOOT_TABLE, "template_name");
        utils.setScriptVar(self, SCRIPTVAR + ".template_name", entireTemplateColumn);
        for (int i = 0; i < entireTemplateColumn.length; i++)
        {
            String armorSearch = "object/tangible/wearables/armor/";
            String tangibleSearch = "object/tangible/";
            String objectString = "object/";
            int stringLength = entireTemplateColumn[i].length();
            if (entireTemplateColumn[i].indexOf(armorSearch) == 0)
            {
                allCategories.add("armor");
            }
            else if (entireTemplateColumn[i].indexOf(tangibleSearch) == 0)
            {
                int tangibleSearchLength = tangibleSearch.length();
                String preParsedCategory = entireTemplateColumn[i].substring(tangibleSearchLength, stringLength);
                int nextSlash = preParsedCategory.indexOf("/");
                allCategories.add(preParsedCategory.substring(0, nextSlash));
            }
            else 
            {
                int objectStringLength = objectString.length();
                String preParsedCategory = entireTemplateColumn[i].substring(objectStringLength, stringLength);
                int nextSlash = preParsedCategory.indexOf("/");
                allCategories.add(preParsedCategory.substring(0, nextSlash));
            }
        }
        if (allCategories.size() > 0)
        {
            String[] suiMenuCategories = new String[allCategories.size()];
            allCategories.toArray(suiMenuCategories);
            Arrays.sort(suiMenuCategories);
            return suiMenuCategories;
        }
        return ERROR_MESSAGE_IN_ARRAY;
    }
    public String[] getSelectedCategory(obj_id self, String searchString) throws InterruptedException
    {
        HashSet staticNameList = new HashSet();
        HashSet staticStringList = new HashSet();
        String[] arrayOfTemplates = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".template_name");
        String[] entireStaticNameColumn = dataTableGetStringColumn(STATIC_LOOT_TABLE, "name");
        if (arrayOfTemplates.length == entireStaticNameColumn.length)
        {
            for (int i = 0; i < arrayOfTemplates.length; i++)
            {
                if (arrayOfTemplates[i].indexOf(searchString) > -1)
                {
                    staticNameList.add(localize(new string_id("static_item_n", entireStaticNameColumn[i])) + "  { " + entireStaticNameColumn[i] + " }");
                }
            }
            if (staticNameList.size() > 0)
            {
                String[] spawnMenu = new String[staticNameList.size()];
                staticNameList.toArray(spawnMenu);
                return spawnMenu;
            }
            else 
            {
                qa.removePlayer(self, SCRIPTVAR, "The tool failed to list your selection. Notify the tool team.");
            }
        }
        else 
        {
            qa.removePlayer(self, SCRIPTVAR, "The tool failed to list your selection. Notify the tool team.");
        }
        return ERROR_MESSAGE_IN_ARRAY;
    }
    public void filterSelection(obj_id self, String searchString) throws InterruptedException
    {
        HashSet templatesFound = new HashSet();
        HashSet staticStringList = new HashSet();
        int searchStringLength = searchString.length();
        String[] arrayOfTemplates = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".template_name");
        if (arrayOfTemplates.length > 0)
        {
            for (int i = 0; i < arrayOfTemplates.length; i++)
            {
                int indexNumber = arrayOfTemplates[i].indexOf(searchString);
                if (indexNumber > -1)
                {
                    int stringLength = arrayOfTemplates[i].length();
                    int subStringStart = indexNumber + searchStringLength;
                    String preParsedCategory = arrayOfTemplates[i].substring(subStringStart, stringLength);
                    int newCategoryEnd = preParsedCategory.indexOf("/");
                    if (newCategoryEnd > -1)
                    {
                        String newCategory = preParsedCategory.substring(0, newCategoryEnd);
                        templatesFound.add(newCategory);
                    }
                }
            }
            if (templatesFound.size() > 0)
            {
                String[] subMenu = new String[templatesFound.size()];
                templatesFound.toArray(subMenu);
                Arrays.sort(subMenu);
                qa.refreshMenu(self, CATEGORY_TOOL_PROMPT, CATEGORY_TOOL_TITLE, subMenu, "handleCategorySelection", SCRIPTVAR + ".pid", SCRIPTVAR + ".categories", sui.OK_CANCEL_REFRESH);
            }
            else 
            {
                qa.removePlayer(self, SCRIPTVAR, "The tool failed to list your selection. Notify the tool team.");
            }
        }
        else 
        {
            qa.removePlayer(self, SCRIPTVAR, "The tool failed to list your selection. Notify the tool team.");
        }
    }
    public void toolArmorMainMenu(obj_id player) throws InterruptedException
    {
        utils.setScriptVar(player, SCRIPTVAR + ".armorChoice", "main");
        qa.refreshMenu(player, "Select an Armor Class", "Armor Tool", ARMOR_CLASSES, "handleArmorOptions", SCRIPTVAR + ".armorType.pid", SCRIPTVAR + ".armorTypeMenu", sui.OK_CANCEL_REFRESH);
    }
    public void armorLevelMenu(obj_id player, String[] displayArray) throws InterruptedException
    {
        qa.refreshMenu(player, "Select what level Armor you want.", "Armor Tool", displayArray, "handleLevelOptions", SCRIPTVAR + ".armorLevel.pid", SCRIPTVAR + ".armorLevelMenu", sui.OK_CANCEL_REFRESH);
    }
    public void listArmorChoiceStats(obj_id player, String[] displayArray) throws InterruptedException
    {
        qa.refreshMenu(player, STAT_EXPLANATION, "Armor Tool", displayArray, "handleSpawnOptions", SCRIPTVAR + ".armorMod.pid", SCRIPTVAR + ".armorModMenu", sui.OK_CANCEL_REFRESH);
    }
    public void mandalorianColor(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "Mandalorian Color makes you look pretty", "Armor Tool", MAND_COLORS, "handleMandalorianColorOptions", SCRIPTVAR + ".mandMenu.pid", SCRIPTVAR + ".mandMenu", sui.OK_CANCEL_REFRESH);
    }
    public void getArmorList(obj_id player, int level) throws InterruptedException
    {
        Vector Armor = new Vector();
        String[] ArmorNameDataColumn = dataTableGetStringColumn(STATIC_LOOT_TABLE, "name");
        int[] ArmorLevelDataColumn = dataTableGetIntColumn(STATIC_LOOT_TABLE, "required_level");
        String armorType = utils.getStringScriptVar(player, SCRIPTVAR + ".armorChoice");
        for (int i = 0; i < ArmorNameDataColumn.length; i++)
        {
            if (ArmorNameDataColumn[i].startsWith(armorType) && ArmorLevelDataColumn[i] == level)
            {
                Armor.add(ArmorNameDataColumn[i]);
            }
        }
        if (Armor.size() >= 1)
        {
            String[] ArmorArray = new String[Armor.size()];
            Armor.toArray(ArmorArray);
            utils.setScriptVar(player, SCRIPTVAR + ".nameArray", ArmorArray);
            listSelectedArmor(player, ArmorArray, armorType);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "No Armor is available for this Class/Level Combination");
            toolArmorMainMenu(player);
        }
    }
    public void listSelectedArmor(obj_id player, String[] ArmorArray, String armorType) throws InterruptedException
    {
        String[] idxName = new String[ArmorArray.length];
        int count = armorType.length() + 1;
        for (int q = 0; q < ArmorArray.length; q++)
        {
            if (ArmorArray[q].indexOf(armorType) == 0)
            {
                String itemName = ArmorArray[q].substring(count);
                int idx = itemName.indexOf("_");
                idxName[q] = itemName.substring(0, idx);
            }
        }
        HashSet armorSets = new HashSet();
        for (int a = 0; a < idxName.length; a++)
        {
            armorSets.add(idxName[a]);
        }
        String[] displayArray = new String[armorSets.size()];
        armorSets.toArray(displayArray);
        utils.setScriptVar(player, SCRIPTVAR + ".statArray", displayArray);
        listArmorChoiceStats(player, displayArray);
    }
    public void spawnItems(obj_id player, String idxString) throws InterruptedException
    {
        String armorChoice = utils.getStringScriptVar(player, SCRIPTVAR + ".armorChoice");
        String[] ArmorNameArray = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".nameArray");
        String spawnString = armorChoice + "_" + idxString;
        obj_id inventory = utils.getInventoryContainer(player);
        qa.findOrCreateAndEquipQABag(player, inventory, true);
        for (int i = 0; i < ArmorNameArray.length; i++)
        {
            if (ArmorNameArray[i].startsWith(spawnString))
            {
                qa.spawnStaticItemInInventory(player, ArmorNameArray[i], "Your selection");
            }
        }
        qa.removePlayer(player, SCRIPTVAR, "");
    }
    public void getLevelsToDisplay(obj_id player, String armorChoice) throws InterruptedException
    {
        String[] namesFromTable = dataTableGetStringColumn(STATIC_LOOT_TABLE, "name");
        HashSet ChoiceRows = new HashSet();
        for (int i = 0; i < namesFromTable.length; i++)
        {
            if (namesFromTable[i].startsWith(armorChoice))
            {
                ChoiceRows.add("" + dataTableGetIntColumn(STATIC_LOOT_TABLE, "required_level")[i]);
            }
        }
        String[] stringLevelDisplayArray = new String[ChoiceRows.size()];
        ChoiceRows.toArray(stringLevelDisplayArray);
        Arrays.sort(stringLevelDisplayArray);
        utils.setScriptVar(player, SCRIPTVAR + ".levelList", stringLevelDisplayArray);
        armorLevelMenu(player, stringLevelDisplayArray);
    }
}
