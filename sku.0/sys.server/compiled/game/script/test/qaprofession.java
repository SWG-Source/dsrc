package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;
import script.library.qa;
import script.library.skill;
import script.library.skill_template;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class qaprofession extends script.base_script
{
    public qaprofession()
    {
    }
    public static final String SCRIPTVAR = "qaprofession";
    public static final String SKILL_TEMPLATE = "datatables/skill_template/skill_template.iff";
    public static final String SKILL_TABLE = "datatables/skill/skills.iff";
    public static final String COMMAND_TABLE = "datatables/command/command_table.iff";
    public static final String ROADMAP_TABLE = "datatables/roadmap/item_rewards.iff";
    public static final String PLAYER_XP_LEVEL_TABLE = "datatables/player/player_level.iff";
    public static final String PROFESSION_TOOL_TITLE = "QA PROFESSION ASSISTANT";
    public static final String PROFESSION_TOOL_PROMPT = "This tool allows the tester to research accurate profession data without having to create a new character or force a profession change.";
    public static final String PROFESSION_DETAILS_TITLE = "ABILITY DETAILS";
    public static final String PROFESSION_TRADER_SELECT = "Select Specific Trader Type\n\n*This is hard coded trader profession data.  Contact the tool team if it is incorrect*";
    public static final String ABILITY_TOOL_TITLE = "QA ABILITY TO PROFESSION CROSS-REFERENCE";
    public static final String ABILITY_TOOL_PROMPT = "This tool allows the tester to find an ability (AKA command) and trace it back to it's profession.";
    public static final String[] PROFESSION_TOOL_MENU = 
    {
        "Abilities Alphabetically",
        "Profession Details",
        "Export Profession Details"
    };
    public static final String[] TRADER_TOOL_MENU = 
    {
        "Domestics ( trader_1a )",
        "Structures ( trader_1b )",
        "Munitions ( trader_1c )",
        "Engineer ( trader_1d )"
    };
    public static final String[] PROFESSION_DETAILS_MENU = 
    {
        "Master this Profession",
        "Export this data"
    };
    public static final String[] ABILITY_TO_SKILL_FORMULA = 
    {
        "_phase1_novice",
        "_phase1_02",
        "_phase1_03",
        "_phase1_04",
        "_phase1_05",
        "_phase1_master",
        "_phase2_novice",
        "_phase2_02",
        "_phase2_03",
        "_phase2_04",
        "_phase2_05",
        "_phase2_master",
        "_phase3_novice",
        "_phase3_02",
        "_phase3_03",
        "_phase3_04",
        "_phase3_05",
        "_phase3_master",
        "_phase4_novice",
        "_phase4_02",
        "_phase4_03",
        "_phase4_04",
        "_phase4_05",
        "_phase4_master"
    };
    public static final int[] HARD_CODED_LEVELS = 
    {
        1,
        4,
        7,
        10,
        14,
        18,
        22,
        26,
        30,
        34,
        38,
        42,
        46,
        50,
        54,
        58,
        62,
        66,
        70,
        74,
        78,
        82,
        86,
        90
    };
    public static final String[] PROFESSION_NAME_LIST = 
    {
        "smuggler_1a",
        "bounty_hunter_1a",
        "officer_1a",
        "commando_1a",
        "force_sensitive_1a",
        "medic_1a",
        "spy_1a",
        "entertainer_2a",
        "trader_1a",
        "trader_1b",
        "trader_1c",
        "trader_1d"
    };
    public static final int SEARCH_ALL_ABILITIES = 0;
    public static final int REVIEW_SPECIFIC_PROFESSION = 1;
    public static final int EXPORT_ENTIRE_PROFESSION = 2;
    public static final int TRADER_DOMESTICS = 0;
    public static final int TRADER_STRUCTURES = 1;
    public static final int TRADER_MUNITIONS = 2;
    public static final int TRADER_ENGINEERING = 3;
    public static final int MASTER_PROFESSION = 0;
    public static final int EXPORT_DATA = 1;
    public static final String NONE_STRING = "None";
    public static final String FILEEXTENSION = "txt";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qaprofession");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qaprofession");
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
                    removePlayer(self, "");
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
                    String[] professionMenuCodeStrings = qa.populateArray(self, "startingTemplateName", SKILL_TEMPLATE, "");
                    utils.setScriptVar(self, SCRIPTVAR + ".professionMenuCodeStrings", professionMenuCodeStrings);
                    String[] professionTemplateNames = getProfessionTemplateNames(self, professionMenuCodeStrings);
                    utils.setScriptVar(self, SCRIPTVAR + ".professionTemplateNames", professionTemplateNames);
                    String[] professionMenu = getProfessionMenuStrings(self, professionMenuCodeStrings);
                    switch (idx)
                    {
                        case SEARCH_ALL_ABILITIES:
                        String[] allAbilityCodeStrings = getAllAbilityCodeStrings(self, professionMenuCodeStrings);
                        String[] allAbilityStrings = getAbilityStrings(self, allAbilityCodeStrings);
                        String[] abilityMenu = combineAndSortAbilities(self, allAbilityStrings, allAbilityCodeStrings);
                        qa.refreshMenu(self, ABILITY_TOOL_PROMPT, ABILITY_TOOL_TITLE, abilityMenu, "handleAbilitySelection", SCRIPTVAR + ".pid", SCRIPTVAR + ".abilitySelected", sui.OK_CANCEL_REFRESH);
                        break;
                        case REVIEW_SPECIFIC_PROFESSION:
                        if (professionMenu.length > 0)
                        {
                            qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, professionMenu, "handleProfessionOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".professionMenu", sui.OK_CANCEL_REFRESH);
                        }
                        else 
                        {
                            removePlayer(self, "Tool failed to get a menu. Exiting.");
                        }
                        break;
                        case EXPORT_ENTIRE_PROFESSION:
                        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, professionMenu, "handleExportProfession", SCRIPTVAR + ".pid", SCRIPTVAR + ".exportMenu", sui.OK_CANCEL_REFRESH);
                        break;
                        default:
                        removePlayer(self, "");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExportProfession(obj_id self, dictionary params) throws InterruptedException
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".exportMenu");
                String professionStringSelection = previousMainMenuArray[idx];
                if (!professionStringSelection.equals(""))
                {
                    String[] professionMenuCodeStrings = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professionMenuCodeStrings");
                    String professionCodeSelection = professionMenuCodeStrings[idx];
                    utils.setScriptVar(self, SCRIPTVAR + ".professionCodeSelection", professionCodeSelection);
                    utils.setScriptVar(self, SCRIPTVAR + ".professionStringSelection", professionStringSelection);
                    if (professionCodeSelection.equals("trader"))
                    {
                        qa.refreshMenu(self, PROFESSION_TRADER_SELECT, PROFESSION_TOOL_TITLE, TRADER_TOOL_MENU, "handleExportTrader", SCRIPTVAR + ".pid", SCRIPTVAR + ".traderCode", sui.OK_CANCEL_REFRESH);
                    }
                    else 
                    {
                        String dataReadyForExport = prepareDataForExport(self, professionCodeSelection);
                        saveDataToClient(self, professionCodeSelection, dataReadyForExport, FILEEXTENSION);
                        toolMainMenu(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExportTrader(obj_id self, dictionary params) throws InterruptedException
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".traderCode");
                String professionStringSelection = previousMainMenuArray[idx];
                if (previousMainMenuArray.length < 0)
                {
                    removePlayer(self, "There was an error in the tool.  Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case TRADER_DOMESTICS:
                        String trader_1aDataReadyForExport = prepareDataForExport(self, "trader_1a");
                        saveDataToClient(self, "trader_1a", trader_1aDataReadyForExport, FILEEXTENSION);
                        toolMainMenu(self);
                        break;
                        case TRADER_STRUCTURES:
                        String trader_1bDataReadyForExport = prepareDataForExport(self, "trader_1b");
                        saveDataToClient(self, "trader_1b", trader_1bDataReadyForExport, FILEEXTENSION);
                        toolMainMenu(self);
                        break;
                        case TRADER_MUNITIONS:
                        String trader_1cDataReadyForExport = prepareDataForExport(self, "trader_1c");
                        saveDataToClient(self, "trader_1c", trader_1cDataReadyForExport, FILEEXTENSION);
                        toolMainMenu(self);
                        break;
                        case TRADER_ENGINEERING:
                        String trader_1dDataReadyForExport = prepareDataForExport(self, "trader_1d");
                        saveDataToClient(self, "trader_1d", trader_1dDataReadyForExport, FILEEXTENSION);
                        toolMainMenu(self);
                        break;
                        default:
                        removePlayer(self, "");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbilitySelection(obj_id self, dictionary params) throws InterruptedException
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".abilitySelected");
                String professionStringSelection = previousMainMenuArray[idx];
                if (!professionStringSelection.equals(""))
                {
                    String skillSelectionCode = getSkillWithAbility(self, professionStringSelection);
                    int skillLevel = -1;
                    for (int i = 0; i < ABILITY_TO_SKILL_FORMULA.length; i++)
                    {
                        if (skillSelectionCode.indexOf(ABILITY_TO_SKILL_FORMULA[i]) > -1)
                        {
                            skillLevel = HARD_CODED_LEVELS[i];
                        }
                    }
                    if (!skillSelectionCode.equals(""))
                    {
                        String professionCodeSelection = getProfessionWithSkill(self, skillSelectionCode);
                        if (!professionCodeSelection.equals(""))
                        {
                            String professionStrSelection = localize(new string_id("ui_prof", professionCodeSelection + "_bdesc"));
                            if (!professionStrSelection.equals(""))
                            {
                                String skillSelectionString = localize(new string_id("skl_n", skillSelectionCode));
                                if (!skillSelectionString.equals(""))
                                {
                                    String[] professionSkillCodes = getProfessionSkillCodes(self, professionCodeSelection);
                                    if (!professionSkillCodes[0].equals(""))
                                    {
                                        utils.setScriptVar(self, SCRIPTVAR + ".professionCodeSelection", professionCodeSelection);
                                        utils.setScriptVar(self, SCRIPTVAR + ".professionStringSelection", professionStringSelection);
                                        utils.setScriptVar(self, SCRIPTVAR + ".professionSkillCodes", professionSkillCodes);
                                        dictionary skillRow = getEntireSkillRow(self, skillSelectionCode);
                                        if (skillRow != null)
                                        {
                                            String[] abilityCodes = getAbilityCodes(self, skillRow);
                                            if (!abilityCodes[0].equals(""))
                                            {
                                                String[] abilityStrings = getAbilityStrings(self, abilityCodes);
                                                if (!abilityStrings[0].equals(""))
                                                {
                                                    String skillXPCodeData = "";
                                                    if (skillLevel > -1)
                                                    {
                                                        skillXPCodeData = getSkillXPCodeData(self, skillRow, skillLevel);
                                                    }
                                                    else 
                                                    {
                                                        skillXPCodeData = getSkillXPCodeData(self, skillRow);
                                                    }
                                                    String[] skillMods = getSkillMods(self, skillRow);
                                                    String roadMapString = getRoadMapString(self, skillSelectionCode);
                                                    String roadMapSpawnStrings = getRoadMapSpawnStrings(self, skillSelectionCode);
                                                    String suiPrompt = createAPrompt(self, true, professionStrSelection, professionCodeSelection, skillSelectionString, skillSelectionCode, skillXPCodeData, skillMods, roadMapString, roadMapSpawnStrings, abilityStrings, abilityCodes);
                                                    if (!suiPrompt.equals(""))
                                                    {
                                                        utils.setScriptVar(self, SCRIPTVAR + ".suiPrompt", suiPrompt);
                                                        String fileTitle = professionCodeSelection + skillSelectionCode;
                                                        utils.setScriptVar(self, SCRIPTVAR + ".fileTitle", fileTitle);
                                                        qa.refreshMenu(self, suiPrompt, PROFESSION_DETAILS_TITLE, PROFESSION_DETAILS_MENU, "handleProfessionDetails", SCRIPTVAR + ".pid", SCRIPTVAR + ".theDataOptions", sui.OK_CANCEL_REFRESH);
                                                    }
                                                    else 
                                                    {
                                                        removePlayer(self, "Prompt text error. Exiting.");
                                                    }
                                                }
                                                else 
                                                {
                                                    removePlayer(self, "Skill ability strings were not received. Exiting.");
                                                }
                                            }
                                            else 
                                            {
                                                removePlayer(self, "There were no skill abilities codes received and this caused the application to malfunction. Exiting.");
                                            }
                                        }
                                        else 
                                        {
                                            removePlayer(self, "Skill row data not attained. Exiting.");
                                        }
                                    }
                                    else 
                                    {
                                        removePlayer(self, "A Profession Skill could not be found. Exiting.");
                                    }
                                }
                                else 
                                {
                                    removePlayer(self, "A Profession Skill could not be found. Exiting.");
                                }
                            }
                            else 
                            {
                                removePlayer(self, "A Profession Localized String could not be found. Exiting.");
                            }
                        }
                        else 
                        {
                            removePlayer(self, "A Profession Code String could not be found. Exiting.");
                        }
                    }
                    else 
                    {
                        removePlayer(self, "A Profession Ability could not be found.  Inform the tool team. Exiting.");
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professionMenu");
                String professionStringSelection = previousMainMenuArray[idx];
                if (!professionStringSelection.equals(""))
                {
                    String[] professionMenuCodeStrings = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professionMenuCodeStrings");
                    String professionCodeSelection = professionMenuCodeStrings[idx];
                    utils.setScriptVar(self, SCRIPTVAR + ".professionCodeSelection", professionCodeSelection);
                    utils.setScriptVar(self, SCRIPTVAR + ".professionStringSelection", professionStringSelection);
                    if (professionCodeSelection.equals("trader"))
                    {
                        qa.refreshMenu(self, PROFESSION_TRADER_SELECT, PROFESSION_TOOL_TITLE, TRADER_TOOL_MENU, "handleTraderSelection", SCRIPTVAR + ".pid", SCRIPTVAR + ".traderCode", sui.OK_CANCEL_REFRESH);
                    }
                    else 
                    {
                        String[] theProfessionSkillMenu = buildProfessionSkillMenu(self, professionCodeSelection);
                        Vector professionCombatLevelMenuResized = new Vector();
                        professionCombatLevelMenuResized.setSize(0);
                        if (theProfessionSkillMenu.length == HARD_CODED_LEVELS.length)
                        {
                            for (int i = 0; i < HARD_CODED_LEVELS.length; i++)
                            {
                                if (professionCodeSelection.startsWith("entertainer"))
                                {
                                    utils.addElement(professionCombatLevelMenuResized, theProfessionSkillMenu[i] + " - Level " + HARD_CODED_LEVELS[i]);
                                }
                                else 
                                {
                                    utils.addElement(professionCombatLevelMenuResized, theProfessionSkillMenu[i] + " - Combat Level " + HARD_CODED_LEVELS[i]);
                                }
                            }
                        }
                        else 
                        {
                            for (int i = 0; i < HARD_CODED_LEVELS.length; i++)
                            {
                                utils.addElement(professionCombatLevelMenuResized, theProfessionSkillMenu[i]);
                            }
                        }
                        String[] professionCombatLevelMenu = new String[professionCombatLevelMenuResized.size()];
                        professionCombatLevelMenuResized.toArray(professionCombatLevelMenu);
                        utils.setScriptVar(self, SCRIPTVAR + ".theProfessionSkillStrings", theProfessionSkillMenu);
                        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, professionCombatLevelMenu, "handleProfessionSkillNameList", SCRIPTVAR + ".pid", SCRIPTVAR + ".professionSkillCombatLevel", sui.OK_CANCEL_REFRESH);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleProfessionSkillNameList(obj_id self, dictionary params) throws InterruptedException
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".theProfessionSkillStrings");
                String skillSelectionString = previousMainMenuArray[idx];
                if (previousMainMenuArray.length < 0)
                {
                    removePlayer(self, "There was an error in the tool.  Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String[] professionSkillCodeStrings = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professionSkillCodes");
                    if (!professionSkillCodeStrings[0].equals(""))
                    {
                        String skillSelectionCode = professionSkillCodeStrings[idx];
                        int skillLevel = -1;
                        if (professionSkillCodeStrings.length == HARD_CODED_LEVELS.length)
                        {
                            skillLevel = HARD_CODED_LEVELS[idx];
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "professionSkillCodeStrings not same lenght as HARD_CODED_LEVELS");
                        }
                        String professionStringSelection = utils.getStringScriptVar(self, SCRIPTVAR + ".professionStringSelection");
                        String professionCodeSelection = utils.getStringScriptVar(self, SCRIPTVAR + ".professionCodeSelection");
                        dictionary skillRow = getEntireSkillRow(self, skillSelectionCode);
                        if (skillRow != null)
                        {
                            String[] abilityCodes = getAbilityCodes(self, skillRow);
                            if (!abilityCodes[0].equals(""))
                            {
                                String[] abilityStrings = getAbilityStrings(self, abilityCodes);
                                if (!abilityStrings[0].equals(""))
                                {
                                    String skillXPCodeData = "";
                                    if (skillLevel > -1)
                                    {
                                        skillXPCodeData = getSkillXPCodeData(self, skillRow, skillLevel);
                                    }
                                    else 
                                    {
                                        skillXPCodeData = getSkillXPCodeData(self, skillRow);
                                    }
                                    String[] skillMods = getSkillMods(self, skillRow);
                                    String roadMapString = getRoadMapString(self, skillSelectionCode);
                                    String roadMapSpawnStrings = getRoadMapSpawnStrings(self, skillSelectionCode);
                                    String suiPrompt = createAPrompt(self, true, professionStringSelection, professionCodeSelection, skillSelectionString, skillSelectionCode, skillXPCodeData, skillMods, roadMapString, roadMapSpawnStrings, abilityStrings, abilityCodes);
                                    utils.setScriptVar(self, SCRIPTVAR + ".suiPrompt", suiPrompt);
                                    String fileTitle = professionCodeSelection + skillSelectionCode;
                                    utils.setScriptVar(self, SCRIPTVAR + ".fileTitle", fileTitle);
                                    suiPrompt = suiPrompt + "\r\n\r\n\r\n* Unlocalized Data will not export correctly";
                                    qa.refreshMenu(self, suiPrompt, PROFESSION_DETAILS_TITLE, PROFESSION_DETAILS_MENU, "handleProfessionDetails", SCRIPTVAR + ".pid", SCRIPTVAR + ".theDataOptions", sui.OK_CANCEL_REFRESH);
                                }
                                else 
                                {
                                    removePlayer(self, "A Profession ability string could not be attained Inform the tool team. Exiting.");
                                }
                            }
                            else 
                            {
                                removePlayer(self, "A Profession ability code string could not be attained Inform the tool team. Exiting.");
                            }
                        }
                        else 
                        {
                            removePlayer(self, "A Profession skill code string could not be attained Inform the tool team. Exiting.");
                        }
                    }
                    else 
                    {
                        removePlayer(self, "A Profession skill code string could not be attained Inform the tool team. Exiting.");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleProfessionDetails(obj_id self, dictionary params) throws InterruptedException
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".theDataOptions");
                String previousSelection = previousMainMenuArray[idx];
                if (previousMainMenuArray.length < 0)
                {
                    removePlayer(self, "There was an error in the tool.  Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String fileTitle = utils.getStringScriptVar(self, SCRIPTVAR + ".fileTitle");
                    String suiPrompt = utils.getStringScriptVar(self, SCRIPTVAR + ".suiPrompt");
                    switch (idx)
                    {
                        case MASTER_PROFESSION:
                        String professionCodeSelection = utils.getStringScriptVar(self, SCRIPTVAR + ".professionCodeSelection");
                        String[] professionSkillCodes = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professionSkillCodes");
                        String[] professionTemplateNames = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".professionTemplateNames");
                        int arrayLength = professionSkillCodes.length;
                        String templateName = getTemplateCodeString(self, professionCodeSelection, professionSkillCodes, professionTemplateNames);
                        if (!templateName.equals(""))
                        {
                            qa.revokeAllSkills(self);
                            setSkillTemplate(self, templateName);
                            for (int i = 0; i < arrayLength; i++)
                            {
                                skill.grantSkillToPlayer(self, professionSkillCodes[i]);
                            }
                            setWorkingSkill(self, professionSkillCodes[arrayLength - 1]);
                            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has attained a Master Profession (" + templateName + ") using the QA Profession Tool.");
                        }
                        qa.refreshMenu(self, suiPrompt, PROFESSION_DETAILS_TITLE, PROFESSION_DETAILS_MENU, "handleProfessionDetails", SCRIPTVAR + ".pid", SCRIPTVAR + ".theDataOptions", sui.OK_CANCEL_REFRESH);
                        break;
                        case EXPORT_DATA:
                        saveDataToClient(self, fileTitle, suiPrompt, FILEEXTENSION);
                        qa.refreshMenu(self, suiPrompt, PROFESSION_DETAILS_TITLE, PROFESSION_DETAILS_MENU, "handleProfessionDetails", SCRIPTVAR + ".pid", SCRIPTVAR + ".theDataOptions", sui.OK_CANCEL_REFRESH);
                        break;
                        default:
                        removePlayer(self, "Default Option on Switch");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTraderSelection(obj_id self, dictionary params) throws InterruptedException
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
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".traderCode");
                String previousSelection = previousMainMenuArray[idx];
                if (previousMainMenuArray.length < 0)
                {
                    removePlayer(self, "There was an error in the tool.  Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case TRADER_DOMESTICS:
                        utils.setScriptVar(self, SCRIPTVAR + ".traderTemplate", "trader_1a");
                        String[] traderMenu_1a = buildProfessionSkillMenu(self, "trader_1a");
                        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, traderMenu_1a, "handleProfessionSkillNameList", SCRIPTVAR + ".pid", SCRIPTVAR + ".theProfessionSkillStrings", sui.OK_CANCEL_REFRESH);
                        break;
                        case TRADER_STRUCTURES:
                        utils.setScriptVar(self, SCRIPTVAR + ".traderTemplate", "trader_1b");
                        String[] traderMenu_1b = buildProfessionSkillMenu(self, "trader_1b");
                        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, traderMenu_1b, "handleProfessionSkillNameList", SCRIPTVAR + ".pid", SCRIPTVAR + ".theProfessionSkillStrings", sui.OK_CANCEL_REFRESH);
                        break;
                        case TRADER_MUNITIONS:
                        utils.setScriptVar(self, SCRIPTVAR + ".traderTemplate", "trader_1c");
                        String[] traderMenu_1c = buildProfessionSkillMenu(self, "trader_1c");
                        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, traderMenu_1c, "handleProfessionSkillNameList", SCRIPTVAR + ".pid", SCRIPTVAR + ".theProfessionSkillStrings", sui.OK_CANCEL_REFRESH);
                        break;
                        case TRADER_ENGINEERING:
                        utils.setScriptVar(self, SCRIPTVAR + ".traderTemplate", "trader_1d");
                        String[] traderMenu_1d = buildProfessionSkillMenu(self, "trader_1d");
                        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, traderMenu_1d, "handleProfessionSkillNameList", SCRIPTVAR + ".pid", SCRIPTVAR + ".theProfessionSkillStrings", sui.OK_CANCEL_REFRESH);
                        break;
                        default:
                        removePlayer(self, "");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id self) throws InterruptedException
    {
        qa.refreshMenu(self, PROFESSION_TOOL_PROMPT, PROFESSION_TOOL_TITLE, PROFESSION_TOOL_MENU, "handleMainMenuOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".mainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void removePlayer(obj_id self, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, err);
        qa.removeScriptVars(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, SCRIPTVAR);
    }
    public String[] buildProfessionSkillMenu(obj_id self, String professionCodeSelection) throws InterruptedException
    {
        String[] professionSkillCodes = getProfessionSkillCodes(self, professionCodeSelection);
        utils.setScriptVar(self, SCRIPTVAR + ".professionSkillCodes", professionSkillCodes);
        String[] professionSkillMenu = getProfessionSkillMenu(self, professionSkillCodes);
        return professionSkillMenu;
    }
    public String[] getProfessionSkillCodes(obj_id self, String strTemplate) throws InterruptedException
    {
        if (!strTemplate.startsWith("trader"))
        {
            int rowNum = dataTableSearchColumnForString(strTemplate, "startingTemplateName", SKILL_TEMPLATE);
            dictionary dictionaryRow = dataTableGetRow(SKILL_TEMPLATE, rowNum);
            if (dictionaryRow != null)
            {
                String arrayOfSkills[] = split(dictionaryRow.getString("template"), ',');
                return arrayOfSkills;
            }
            else 
            {
                removePlayer(self, "The Program failed to return Profession Code String data.  Inform the tool team.");
                return null;
            }
        }
        else 
        {
            int rowNum = dataTableSearchColumnForString(strTemplate, "templateName", SKILL_TEMPLATE);
            dictionary dictionaryRow = dataTableGetRow(SKILL_TEMPLATE, rowNum);
            if (dictionaryRow != null)
            {
                String arrayOfSkills[] = split(dictionaryRow.getString("template"), ',');
                return arrayOfSkills;
            }
            else 
            {
                removePlayer(self, "The Program failed to return Profession Code String data.  Inform the tool team.");
                return null;
            }
        }
    }
    public String[] getProfessionSkillMenu(obj_id self, String[] arrayOfSkills) throws InterruptedException
    {
        int arrayLength = arrayOfSkills.length;
        String arrayOfSkillNames[] = new String[arrayLength];
        if (arrayLength > 0)
        {
            for (int i = 0; i < arrayLength; i++)
            {
                arrayOfSkillNames[i] = localize(new string_id("skl_n", arrayOfSkills[i]));
            }
            return arrayOfSkillNames;
        }
        else 
        {
            removePlayer(self, "The Program failed to return Profession Skill String data.  Inform the tool team.");
            return null;
        }
    }
    public String[] getProfessionMenuStrings(obj_id self, String[] professionCodes) throws InterruptedException
    {
        int arrayLength = professionCodes.length;
        String arrayOfProfessionNames[] = new String[arrayLength];
        if (arrayLength > 0)
        {
            for (int i = 0; i < arrayLength; i++)
            {
                String roadmapProfession = professionCodes[i] + "_bdesc";
                arrayOfProfessionNames[i] = localize(new string_id("ui_prof", roadmapProfession));
            }
            return arrayOfProfessionNames;
        }
        else 
        {
            removePlayer(self, "The Program failed to return Profession Menu String data.  Inform the tool team.");
            return null;
        }
    }
    public String[] getAbilityStrings(obj_id self, String[] abilityCodes) throws InterruptedException
    {
        int arrayLength = abilityCodes.length;
        String arrayOfAbilityNames[] = new String[arrayLength];
        if (arrayLength > 0)
        {
            for (int i = 0; i < arrayLength; i++)
            {
                if (!abilityCodes[i].startsWith("private_") && !abilityCodes[i].equals(NONE_STRING) && abilityCodes[i].indexOf("_") > -1)
                {
                    arrayOfAbilityNames[i] = localize(new string_id("cmd_n", abilityCodes[i]));
                }
                else if (abilityCodes[i].equals(NONE_STRING))
                {
                    arrayOfAbilityNames[i] = NONE_STRING;
                }
                else 
                {
                    arrayOfAbilityNames[i] = abilityCodes[i];
                }
            }
            return arrayOfAbilityNames;
        }
        else 
        {
            removePlayer(self, "The Program failed to return Ability String data.  Inform the tool team.");
            return null;
        }
    }
    public String getSkillXPCodeData(obj_id self, dictionary skillRow) throws InterruptedException
    {
        String xpType = skillRow.getString("XP_TYPE");
        if (!xpType.equals(""))
        {
            return "XP Type:\t" + xpType;
        }
        else 
        {
            removePlayer(self, "The Program failed to return Skill XP Data.  Inform the tool team.");
            return null;
        }
    }
    public String getSkillXPCodeData(obj_id self, dictionary skillRow, int skillLevel) throws InterruptedException
    {
        int experienceRow = dataTableSearchColumnForInt(skillLevel, "level", PLAYER_XP_LEVEL_TABLE);
        int experienceAmount = dataTableGetInt(PLAYER_XP_LEVEL_TABLE, experienceRow, "xp_required");
        String xpType = skillRow.getString("XP_TYPE");
        if (!xpType.equals("") && experienceAmount > -1)
        {
            return "Level:\t" + skillLevel + "\r\n\t\tXP Required:\t" + experienceAmount + "\r\n\t\tXP Type:\t" + xpType;
        }
        else 
        {
            removePlayer(self, "The Program failed to return Skill XP Data.  Inform the tool team.");
            return null;
        }
    }
    public String[] getSkillMods(obj_id self, dictionary skillRow) throws InterruptedException
    {
        String skillModString = skillRow.getString("SKILL_MODS");
        if (!skillModString.equals(""))
        {
            String[] skillMods = split(skillRow.getString("SKILL_MODS"), ',');
            if (skillMods.length > 0)
            {
                return skillMods;
            }
            else 
            {
                removePlayer(self, "The Program failed to return Skill Mod data.  Inform the tool team.");
                return null;
            }
        }
        else 
        {
            String[] noneArray = new String[1];
            noneArray[0] = NONE_STRING;
            return noneArray;
        }
    }
    public String createAPrompt(obj_id self, boolean clientLocalized, String professionStringSelection, String professionCodeSelection, String skillSelectionString, String skillSelectionCode, String skillXPCodeData, String[] skillMods, String roadMapString, String roadMapSpawnStrings, String[] abilityString, String[] abilityCodes) throws InterruptedException
    {
        String prompt = "";
        if (clientLocalized == true)
        {
            prompt = "@ui:button_bar_skills - " + professionStringSelection;
        }
        else 
        {
            prompt = professionStringSelection;
        }
        prompt = prompt + "\t ( " + professionCodeSelection + " )\r\n\t" + skillSelectionString + " \t ( " + skillSelectionCode + " )\r\n\t\t" + skillXPCodeData;
        for (int x = 0; x < skillMods.length; x++)
        {
            prompt = prompt + "\r\n\t\tSkill Mod:\t" + skillMods[x];
        }
        prompt = prompt + "\r\n\r\n\t\tRoad Map Item:\t\t" + roadMapString;
        prompt = prompt + roadMapSpawnStrings;
        if (abilityString.length > 0)
        {
            if (abilityString.length == abilityCodes.length)
            {
                prompt = prompt + "\r\n\r\n\t\tAbilites Awarded:";
                for (int i = 0; i < abilityString.length; i++)
                {
                    prompt = prompt + "\r\n\t\t\t" + abilityString[i] + "\t ( " + abilityCodes[i] + " )";
                    if (abilityString[i].indexOf("Mitigation") == -1 && !professionCodeSelection.equals("entertainer") && !professionCodeSelection.startsWith("trader"))
                    {
                        String abilityData = getAbilityData(self, abilityCodes[i]);
                        if (!abilityData.equals(""))
                        {
                            prompt = prompt + abilityData;
                        }
                    }
                }
            }
            else 
            {
                removePlayer(self, "The Program failed to detail the Skill Ability Data.  Inform the tool team.");
            }
        }
        else 
        {
            prompt = prompt + "\r\n\r\n\t\tAbilites Awarded:\r\n\t\t\t" + abilityString[0] + "\t\tSlash Command: " + abilityCodes[0];
        }
        return prompt;
    }
    public String getRoadMapString(obj_id self, String skillSelection) throws InterruptedException
    {
        String stringId = "";
        int rowNum = dataTableSearchColumnForString(skillSelection, "roadmapSkillName", ROADMAP_TABLE);
        if (rowNum > -1)
        {
            dictionary dictionaryRow = dataTableGetRow(ROADMAP_TABLE, rowNum);
            if (dictionaryRow != null)
            {
                stringId = dictionaryRow.getString("stringId");
                if (!stringId.equals(""))
                {
                    if (stringId.startsWith("@obj_n"))
                    {
                        int obj_nStringIndex = stringId.indexOf(":");
                        String obj_nSubStringString = stringId.substring(obj_nStringIndex + 1);
                        stringId = "" + localize(new string_id("obj_n", obj_nSubStringString));
                    }
                    if (stringId.startsWith("@ui_roadmap"))
                    {
                        stringId = " " + stringId + " <-- UNLOCALIZED DATA";
                    }
                }
                else 
                {
                    stringId = NONE_STRING;
                    return stringId;
                }
            }
        }
        else 
        {
            stringId = skillSelection + " ( *No Roadmap Name Found* )";
        }
        return stringId;
    }
    public String getRoadMapSpawnStrings(obj_id self, String skillSelection) throws InterruptedException
    {
        String roadmapData = "";
        int rowNum = dataTableSearchColumnForString(skillSelection, "roadmapSkillName", ROADMAP_TABLE);
        if (rowNum > -1)
        {
            dictionary dictionaryRow = dataTableGetRow(ROADMAP_TABLE, rowNum);
            if (dictionaryRow != null)
            {
                String itemDefault = dictionaryRow.getString("itemDefault");
                if (!itemDefault.equals(""))
                {
                    if (itemDefault.indexOf(",") > -1)
                    {
                        String arrayOfCodes[] = split(dictionaryRow.getString("itemDefault"), ',');
                        for (int i = 0; i < arrayOfCodes.length; i++)
                        {
                            roadmapData = roadmapData + "\r\n\t\tSpawn String:\t\t" + arrayOfCodes[i];
                        }
                        return roadmapData;
                    }
                    else 
                    {
                        roadmapData = "\r\n\t\tSpawn String:\t\t" + itemDefault;
                        return roadmapData;
                    }
                }
                else 
                {
                    roadmapData = "\r\n\t\tSpawn String:\t\t" + NONE_STRING;
                    return roadmapData;
                }
            }
            else 
            {
                removePlayer(self, "The Program failed to return the Roadmap Item Row.  Inform the tool team.");
                return null;
            }
        }
        else 
        {
            roadmapData = "\r\n\t\tSpawn String:\t\t" + NONE_STRING;
            return roadmapData;
        }
    }
    public String getAbilityData(obj_id self, String abilityCode) throws InterruptedException
    {
        String abilityData = "";
        int rowNum = dataTableSearchColumnForString(abilityCode, "commandName", COMMAND_TABLE);
        if (rowNum > -1)
        {
            dictionary commandRow = dataTableGetRow(COMMAND_TABLE, rowNum);
            float warmupTime = commandRow.getFloat("warmupTime");
            float executeTime = commandRow.getFloat("executeTime");
            float cooldownTime = commandRow.getFloat("cooldownTime");
            String cooldownGroup = commandRow.getString("cooldownGroup");
            String cooldownGroup2 = commandRow.getString("cooldownGroup2");
            float cooldownTime2 = commandRow.getFloat("cooldownTime2");
            if (cooldownTime2 > 0)
            {
                abilityData = "\r\n\t\t\t\tWarm Up Time:\t\t" + warmupTime + "\r\n\t\t\t\tExecute Time:\t\t" + executeTime + "\r\n\t\t\t\tCool Down Time:\t\t" + cooldownTime + "\r\n\t\t\t\tCool Down Group:\t" + cooldownGroup + "\r\n\t\t\t\tSecond Cool Down Group:\t" + cooldownGroup2 + "\r\n\t\t\t\tSecond Cool Down Time:\t" + cooldownTime2;
            }
            else 
            {
                abilityData = "\r\n\t\t\t\tWarm Up Time:\t\t" + warmupTime + "\r\n\t\t\t\tExecute Time:\t\t" + executeTime + "\r\n\t\t\t\tCool Down Time:\t\t" + cooldownTime + "\r\n\t\t\t\tCool Down Group:\t" + cooldownGroup;
            }
        }
        else 
        {
            abilityData = "\r\n\t\t\t\tAbility Data:\t\t *This ability is not in the command table*";
        }
        return abilityData;
    }
    public void saveDataToClient(obj_id self, String fileTitle, String stringData, String fileExtension) throws InterruptedException
    {
        int parseUnlocalized = stringData.indexOf("@ui:button_bar_skills -");
        if (parseUnlocalized == 0)
        {
            String unlocalizedSubStringString = stringData.substring(24);
            stringData = unlocalizedSubStringString;
        }
        saveTextOnClient(self, fileTitle + "." + fileExtension, stringData);
    }
    public String[] getAllAbilityCodeStrings(obj_id self, String[] professionMenuCodeStrings) throws InterruptedException
    {
        int professionMenuLength = professionMenuCodeStrings.length;
        if (professionMenuLength > 0)
        {
            Vector allSkills = new Vector();
            Vector allAbilities = new Vector();
            Vector filteredAbilities = new Vector();
            for (int i = 0; i < professionMenuLength; i++)
            {
                int rowNum = dataTableSearchColumnForString(professionMenuCodeStrings[i], "startingTemplateName", SKILL_TEMPLATE);
                dictionary dictionaryRow = dataTableGetRow(SKILL_TEMPLATE, rowNum);
                if (dictionaryRow != null)
                {
                    String arrayOfSkills[] = split(dictionaryRow.getString("template"), ',');
                    int arrayLength = arrayOfSkills.length;
                    for (int x = 0; x < arrayLength; x++)
                    {
                        allSkills.add(arrayOfSkills[x]);
                    }
                }
            }
            int skillListSize = allSkills.size();
            String[] allSkillCodeStrings = new String[skillListSize];
            allSkills.toArray(allSkillCodeStrings);
            for (int y = 0; y < skillListSize; y++)
            {
                dictionary eachSkillRow = getEntireSkillRow(self, allSkillCodeStrings[y]);
                String[] arrayOfAbilities = getAbilityCodes(self, eachSkillRow);
                int abilityArrayLength = arrayOfAbilities.length;
                for (int z = 0; z < abilityArrayLength; z++)
                {
                    if (!arrayOfAbilities[z].equals("None"))
                    {
                        allAbilities.add(arrayOfAbilities[z]);
                    }
                }
            }
            int abilityListSize = allAbilities.size();
            String[] allAbilityCodeStrings = new String[abilityListSize];
            allAbilities.toArray(allAbilityCodeStrings);
            for (int a = 0; a < abilityListSize; a++)
            {
                if (dataTableSearchColumnForString(allAbilityCodeStrings[a], "commandName", COMMAND_TABLE) > -1)
                {
                    filteredAbilities.add(allAbilityCodeStrings[a]);
                }
            }
            int filteredListSize = filteredAbilities.size();
            String[] allCodeStrings = new String[filteredListSize];
            filteredAbilities.toArray(allCodeStrings);
            return allCodeStrings;
        }
        else 
        {
            removePlayer(self, "The Program failed to return a list of Abilites.  Inform the tool team.");
            return null;
        }
    }
    public dictionary getEntireSkillRow(obj_id self, String skillSelection) throws InterruptedException
    {
        int rowNum = dataTableSearchColumnForString(skillSelection, "NAME", SKILL_TABLE);
        dictionary dictionaryRow = dataTableGetRow(SKILL_TABLE, rowNum);
        if (dictionaryRow != null)
        {
            return dictionaryRow;
        }
        else 
        {
            removePlayer(self, "The Program failed to return the Data Source Row.  Inform the tool team.");
            return null;
        }
    }
    public String[] getAbilityCodes(obj_id self, dictionary skillRow) throws InterruptedException
    {
        String arrayOfAbilities[] = split(skillRow.getString("COMMANDS"), ',');
        int abilityArrayLength = arrayOfAbilities.length;
        String firstVar = arrayOfAbilities[0];
        if (abilityArrayLength > 0)
        {
            if (!firstVar.equals(""))
            {
                return arrayOfAbilities;
            }
            else 
            {
                String[] tempArray = new String[1];
                tempArray[0] = NONE_STRING;
                return tempArray;
            }
        }
        else 
        {
            removePlayer(self, "The Program failed to return Ability Code String data.  Inform the tool team.");
            return null;
        }
    }
    public String[] combineAndSortAbilities(obj_id self, String[] allAbilityStrings, String[] allAbilityCodeStrings) throws InterruptedException
    {
        int stringListLength = allAbilityStrings.length;
        int codeListLength = allAbilityCodeStrings.length;
        if (stringListLength == codeListLength)
        {
            String[] combinedArray = new String[codeListLength];
            for (int i = 0; i < codeListLength; i++)
            {
                combinedArray[i] = allAbilityStrings[i] + " ( " + allAbilityCodeStrings[i] + " )";
            }
            Arrays.sort(combinedArray);
            return combinedArray;
        }
        else 
        {
            removePlayer(self, "The Program failed to return the sorted and combined ability data.  Inform the tool team.");
            return null;
        }
    }
    public String getSkillWithAbility(obj_id self, String abilitySelection) throws InterruptedException
    {
        int firstChar = abilitySelection.indexOf("( ");
        int lastChar = abilitySelection.indexOf(" )");
        if (firstChar > 0)
        {
            String abilityCode = abilitySelection.substring(firstChar + 2, lastChar);
            int rowNum = -1;
            String[] entireCommandCol = dataTableGetStringColumn(SKILL_TABLE, "COMMANDS");
            int commandColLength = entireCommandCol.length;
            for (int i = 0; i < commandColLength; i++)
            {
                String arrayOfCommands[] = split(entireCommandCol[i], ',');
                int commandArrayLength = arrayOfCommands.length;
                for (int x = 0; x < commandArrayLength; x++)
                {
                    if (arrayOfCommands[x].equals(abilityCode))
                    {
                        rowNum = i;
                        break;
                    }
                }
                if (rowNum != -1)
                {
                    break;
                }
            }
            if (rowNum != -1)
            {
                String skillCode = dataTableGetString(SKILL_TABLE, rowNum, "NAME");
                return skillCode;
            }
        }
        removePlayer(self, "The Program failed to receive a valid string from another part of the application.  Inform the tool team.");
        return null;
    }
    public String getProfessionWithSkill(obj_id self, String skillCode) throws InterruptedException
    {
        if (!skillCode.equals(""))
        {
            int rowNum = -1;
            String[] entireSkillCol = dataTableGetStringColumn(SKILL_TEMPLATE, "template");
            int skillColLength = entireSkillCol.length;
            for (int i = 0; i < skillColLength; i++)
            {
                String arrayOfSkills[] = split(entireSkillCol[i], ',');
                int skillArrayLength = arrayOfSkills.length;
                for (int x = 0; x < skillArrayLength; x++)
                {
                    if (arrayOfSkills[x].equals(skillCode))
                    {
                        rowNum = i;
                        break;
                    }
                }
                if (rowNum != -1)
                {
                    break;
                }
            }
            if (rowNum != -1)
            {
                String professionCode = dataTableGetString(SKILL_TEMPLATE, rowNum, "startingTemplateName");
                if (!professionCode.equals(""))
                {
                    return professionCode;
                }
            }
            removePlayer(self, "The Program failed to receive a valid profession string.  Inform the tool team.");
            return null;
        }
        removePlayer(self, "The Program failed to receive a valid string from another part of the application.  Inform the tool team.");
        return null;
    }
    public String[] getProfessionTemplateNames(obj_id self, String[] professionMenuCodeStrings) throws InterruptedException
    {
        int professionMenuLength = professionMenuCodeStrings.length;
        if (professionMenuLength > 0)
        {
            Vector allTemplates = new Vector();
            String professionTemplateName = "";
            for (int i = 0; i < professionMenuLength; i++)
            {
                int rowNum = dataTableSearchColumnForString(professionMenuCodeStrings[i], "startingTemplateName", SKILL_TEMPLATE);
                professionTemplateName = dataTableGetString(SKILL_TEMPLATE, rowNum, "templateName");
                allTemplates.add(professionTemplateName);
            }
            int vectorSize = allTemplates.size();
            if (vectorSize != professionMenuLength)
            {
                removePlayer(self, "The Program failed to receive an accurate list of profession template strings.  Inform the tool team.");
                return null;
            }
            else 
            {
                String[] allProfessionTemplates = new String[vectorSize];
                allTemplates.toArray(allProfessionTemplates);
                return allProfessionTemplates;
            }
        }
        else 
        {
            removePlayer(self, "The Program failed to receive a list of profession template strings.  Inform the tool team.");
            return null;
        }
    }
    public String getTemplateCodeString(obj_id self, String professionCodeSelection, String[] professionSkillCodes, String[] professionTemplateNames) throws InterruptedException
    {
        int arrayLength = professionSkillCodes.length;
        int professionListLength = professionTemplateNames.length;
        String templateName = "";
        if (professionCodeSelection.equals("trader"))
        {
            String traderTemplate = utils.getStringScriptVar(self, SCRIPTVAR + ".traderTemplate");
            templateName = traderTemplate;
        }
        else 
        {
            for (int i = 0; i < professionListLength; i++)
            {
                if (professionTemplateNames[i].startsWith(professionCodeSelection))
                {
                    templateName = professionTemplateNames[i];
                    break;
                }
            }
        }
        return templateName;
    }
    public String buildPrompt(obj_id self, String skillSelectionCode, String skillSelectionString, int skillLevel) throws InterruptedException
    {
        if (!skillSelectionCode.equals(""))
        {
            String professionStringSelection = utils.getStringScriptVar(self, SCRIPTVAR + ".professionStringSelection");
            String professionCodeSelection = utils.getStringScriptVar(self, SCRIPTVAR + ".professionCodeSelection");
            dictionary skillRow = getEntireSkillRow(self, skillSelectionCode);
            String[] abilityCodes = getAbilityCodes(self, skillRow);
            if (!abilityCodes[0].equals(""))
            {
                String[] abilityStrings = getAbilityStrings(self, abilityCodes);
                if (!abilityStrings[0].equals(""))
                {
                    String skillXPCodeData = "";
                    if (skillLevel > -1)
                    {
                        skillXPCodeData = getSkillXPCodeData(self, skillRow, skillLevel);
                    }
                    else 
                    {
                        skillXPCodeData = getSkillXPCodeData(self, skillRow);
                    }
                    String[] skillMods = getSkillMods(self, skillRow);
                    String roadMapString = getRoadMapString(self, skillSelectionCode);
                    String roadMapSpawnStrings = getRoadMapSpawnStrings(self, skillSelectionCode);
                    String suiPrompt = createAPrompt(self, false, professionStringSelection, professionCodeSelection, skillSelectionString, skillSelectionCode, skillXPCodeData, skillMods, roadMapString, roadMapSpawnStrings, abilityStrings, abilityCodes);
                    suiPrompt = suiPrompt + "\r\n\r\n";
                    return suiPrompt;
                }
            }
        }
        return "Error in buildPrompt funciton!";
    }
    public String prepareDataForExport(obj_id self, String professionCodeSelection) throws InterruptedException
    {
        String[] theProfessionSkillList = getProfessionSkillCodes(self, professionCodeSelection);
        String[] theProfessionSkillMenu = buildProfessionSkillMenu(self, professionCodeSelection);
        if (theProfessionSkillList.length == theProfessionSkillMenu.length)
        {
            int skillLength = theProfessionSkillList.length;
            String fileData = "";
            if (skillLength == HARD_CODED_LEVELS.length)
            {
                for (int i = 0; i < skillLength; i++)
                {
                    fileData = fileData + buildPrompt(self, theProfessionSkillList[i], theProfessionSkillMenu[i], HARD_CODED_LEVELS[i]);
                }
            }
            else 
            {
                for (int i = 0; i < skillLength; i++)
                {
                    fileData = fileData + buildPrompt(self, theProfessionSkillList[i], theProfessionSkillMenu[i], -1);
                }
            }
            return fileData;
        }
        else 
        {
            removePlayer(self, "The export program failed to receive data correctly.");
        }
        return null;
    }
}
