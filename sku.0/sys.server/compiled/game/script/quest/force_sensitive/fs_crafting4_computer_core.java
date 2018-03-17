package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.prose;
import script.library.quests;
import script.library.sui;
import script.library.utils;

public class fs_crafting4_computer_core extends script.base_script
{
    public fs_crafting4_computer_core()
    {
    }
    public static final int NO_WIRE = 0;
    public static final int RED_WIRE = 1;
    public static final int BLUE_WIRE = 2;
    public static final int YELLOW_WIRE = 3;
    public static final int PUZZLE_LENGTH = 4;
    public static final String PUZZLE_SOLUTION_OBJVAR = "fs_crafting4.puzzle.solution";
    public static final String PUZZLE_GUESS_OBJVAR = "fs_crafting4.puzzle.guess";
    public static final String PUZZLE_CONFIGURED_OBJVAR = "fs_crafting4.puzzle.configured";
    public static final String PUZZLE_INTEGRITY_OBJVAR = "fs_crafting4.puzzle.integrity";
    public static final String PUZZLE_LAST_TESTED_OBJVAR = "fs_crafting4.puzzle.lastTested";
    public static final String PUZZLE_LAST_WIRES_CORRECT_OBJVAR = "fs_crafting4.puzzle.lastWiresCorrect";
    public static final String PUZZLE_LAST_WIRES_USED_OBJVAR = "fs_crafting4.puzzle.lastWiresUsed";
    public static final string_id SID_MENU_COMPUTER_CORE_CONFIGURE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_configure");
    public static final string_id SID_MENU_COMPUTER_CORE_STATUS = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_status");
    public static final string_id SID_MENU_COMPUTER_CORE_TEST = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_test");
    public static final string_id SID_COMPUTER_CORE_ERROR = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_error");
    public static final string_id SID_MENU_CONNECTION_ONE_NOTHING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_one_nothing");
    public static final string_id SID_MENU_CONNECTION_ONE_RED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_one_red");
    public static final string_id SID_MENU_CONNECTION_ONE_BLUE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_one_blue");
    public static final string_id SID_MENU_CONNECTION_ONE_YELLOW = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_one_yellow");
    public static final string_id SID_MENU_CONNECTION_TWO_NOTHING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_two_nothing");
    public static final string_id SID_MENU_CONNECTION_TWO_RED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_two_red");
    public static final string_id SID_MENU_CONNECTION_TWO_BLUE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_two_blue");
    public static final string_id SID_MENU_CONNECTION_TWO_YELLOW = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_two_yellow");
    public static final string_id SID_MENU_CONNECTION_THREE_NOTHING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_three_nothing");
    public static final string_id SID_MENU_CONNECTION_THREE_RED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_three_red");
    public static final string_id SID_MENU_CONNECTION_THREE_BLUE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_three_blue");
    public static final string_id SID_MENU_CONNECTION_THREE_YELLOW = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_three_yellow");
    public static final string_id SID_MENU_CONNECTION_FOUR_NOTHING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_four_nothing");
    public static final string_id SID_MENU_CONNECTION_FOUR_RED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_four_red");
    public static final string_id SID_MENU_CONNECTION_FOUR_BLUE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_four_blue");
    public static final string_id SID_MENU_CONNECTION_FOUR_YELLOW = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_menu_four_yellow");
    public static final string_id SID_COMPUTER_CORE_SET_CONNECTION_ONE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_set_connection_one");
    public static final string_id SID_COMPUTER_CORE_SET_CONNECTION_TWO = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_set_connection_two");
    public static final string_id SID_COMPUTER_CORE_SET_CONNECTION_THREE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_set_connection_three");
    public static final string_id SID_COMPUTER_CORE_SET_CONNECTION_FOUR = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_set_connection_four");
    public static final string_id SID_COMPUTER_CORE_CONNECTION_ONE_SET = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_connection_one_set");
    public static final string_id SID_COMPUTER_CORE_CONNECTION_TWO_SET = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_connection_two_set");
    public static final string_id SID_COMPUTER_CORE_CONNECTION_THREE_SET = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_connection_three_set");
    public static final string_id SID_COMPUTER_CORE_CONNECTION_FOUR_SET = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_connection_four_set");
    public static final string_id SID_NOTHING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_nothing");
    public static final string_id SID_A_RED_WIRE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_a_red_wire");
    public static final string_id SID_A_BLUE_WIRE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_a_blue_wire");
    public static final string_id SID_A_YELLOW_WIRE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_a_yellow_wire");
    public static final string_id SID_NO_SELECTION_MADE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_no_selection_made");
    public static final string_id SID_COMPUTER_CORE_CONFIG_INSTRUCTIONS = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_config_help");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_01 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_01");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_02 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_02");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_03 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_03");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_04 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_04");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_05 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_05");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_06 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_06");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_07 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_07");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_08 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_08");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_09 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_09");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_10 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_10");
    public static final string_id SID_COMPUTER_CORE_CURRENT_STATUS_11 = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_status_11");
    public static final string_id SID_COMPUTER_CORE_CURRENT_NO_TESTING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_no_testing");
    public static final string_id SID_COMPUTER_CORE_COMPLETED_STATUS = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_completed_status");
    public static final string_id SID_COMPUTER_CORE_MSGBOX_TITLE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_msgbox_title");
    public static final string_id SID_CLOSE_BUTTON = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_close_button");
    public static final string_id SID_COMPUTER_CORE_CURRENT_RESULT = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_current_result");
    public static final string_id SID_COMPUTER_CORE_COMPLETED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_completed");
    public static final string_id SID_COMPUTER_CORE_INDICATORS_LIT = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_indicators_lit");
    public static final string_id SID_COMPUTER_CORE_INDICATORS_FLICKERING = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_flickering");
    public static final string_id SID_COMPUTER_CORE_INTEGRITY = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_integrity");
    public static final string_id SID_COMPUTER_CORE_RUINED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_ruined");
    public static final string_id SID_COMPUTER_CORE_DESTROYED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_destroyed");
    public static final string_id SID_COMPUTER_CORE_DESTROYED_OVER = new string_id("quest/force_sensitive/fs_crafting", "crafting4_core_destroyed_over");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlePuzzleSetup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canUse(player))
        {
            if (!isConfigured(self))
            {
                int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SID_MENU_COMPUTER_CORE_CONFIGURE);
                mi.addSubMenu(menuOption, menu_info_types.SERVER_MENU1, getConnectionMenuName(self, 0));
                mi.addSubMenu(menuOption, menu_info_types.SERVER_MENU2, getConnectionMenuName(self, 1));
                mi.addSubMenu(menuOption, menu_info_types.SERVER_MENU3, getConnectionMenuName(self, 2));
                mi.addSubMenu(menuOption, menu_info_types.SERVER_MENU4, getConnectionMenuName(self, 3));
                mi.addSubMenu(menuOption, menu_info_types.SERVER_MENU5, SID_MENU_COMPUTER_CORE_TEST);
            }
        }
        int menuExtras = mi.addRootMenu(menu_info_types.SERVER_MENU6, SID_MENU_COMPUTER_CORE_STATUS);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            String str_msg = utils.packStringId(SID_COMPUTER_CORE_CONFIG_INSTRUCTIONS);
            oneButtonMsgBox(self, player, "noHandler", str_msg);
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            if (canUse(player))
            {
                setConnection(self, player, 0);
            }
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            if (canUse(player))
            {
                setConnection(self, player, 1);
            }
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            if (canUse(player))
            {
                setConnection(self, player, 2);
            }
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            if (canUse(player))
            {
                setConnection(self, player, 3);
            }
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            if (canUse(player))
            {
                checkPuzzleSolutionAttempt(self, player);
            }
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            string_id sid_wire1 = getWireSid(self, PUZZLE_GUESS_OBJVAR, 0);
            string_id sid_wire2 = getWireSid(self, PUZZLE_GUESS_OBJVAR, 1);
            string_id sid_wire3 = getWireSid(self, PUZZLE_GUESS_OBJVAR, 2);
            string_id sid_wire4 = getWireSid(self, PUZZLE_GUESS_OBJVAR, 3);
            String str_wire1 = utils.packStringId(sid_wire1);
            String str_wire2 = utils.packStringId(sid_wire2);
            String str_wire3 = utils.packStringId(sid_wire3);
            String str_wire4 = utils.packStringId(sid_wire4);
            String str_msg = utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_01);
            str_msg = str_msg + " \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_02) + " \\#pcontrast2 " + str_wire1;
            str_msg = str_msg + "  \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_03) + " \\#pcontrast2 " + str_wire2;
            str_msg = str_msg + "  \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_04) + " \\#pcontrast2 " + str_wire3;
            str_msg = str_msg + "  \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_05) + " \\#pcontrast2 " + str_wire4;
            if (hasObjVar(self, PUZZLE_LAST_TESTED_OBJVAR))
            {
                string_id sid_test_wire1 = getWireSid(self, PUZZLE_LAST_TESTED_OBJVAR, 0);
                string_id sid_test_wire2 = getWireSid(self, PUZZLE_LAST_TESTED_OBJVAR, 1);
                string_id sid_test_wire3 = getWireSid(self, PUZZLE_LAST_TESTED_OBJVAR, 2);
                string_id sid_test_wire4 = getWireSid(self, PUZZLE_LAST_TESTED_OBJVAR, 3);
                String str_test_wire1 = utils.packStringId(sid_test_wire1);
                String str_test_wire2 = utils.packStringId(sid_test_wire2);
                String str_test_wire3 = utils.packStringId(sid_test_wire3);
                String str_test_wire4 = utils.packStringId(sid_test_wire4);
                str_msg = str_msg + " \\#. " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_06);
                str_msg = str_msg + " \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_07) + " \\#pcontrast2 " + str_test_wire1;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_08) + " \\#pcontrast2 " + str_test_wire2;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_09) + " \\#pcontrast2 " + str_test_wire3;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_10) + " \\#pcontrast2 " + str_test_wire4;
                int numWiresConnectedCorrectly = getIntObjVar(self, PUZZLE_LAST_WIRES_CORRECT_OBJVAR);
                int numCorrectWiresUsed = getIntObjVar(self, PUZZLE_LAST_WIRES_USED_OBJVAR);
                int integrity = getIntObjVar(self, PUZZLE_INTEGRITY_OBJVAR);
                str_msg = str_msg + " \\#. " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_STATUS_11);
                str_msg = str_msg + " " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_RESULT);
                str_msg = str_msg + " \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_INDICATORS_LIT) + " \\#pcontrast2 " + numWiresConnectedCorrectly;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_INDICATORS_FLICKERING) + " \\#pcontrast2 " + numCorrectWiresUsed;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_INTEGRITY) + " \\#pcontrast2 " + integrity + "% \\#. ";
            }
            else 
            {
                str_msg = str_msg + " \\#. " + utils.packStringId(SID_COMPUTER_CORE_CURRENT_NO_TESTING);
            }
            if (isConfigured(self))
            {
                str_msg = utils.packStringId(SID_COMPUTER_CORE_COMPLETED_STATUS);
            }
            oneButtonMsgBox(self, player, "noHandler", str_msg);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePuzzleSetup(obj_id self, dictionary params) throws InterruptedException
    {
        resetTheFlightRecorder(self);
        return SCRIPT_CONTINUE;
    }
    public int handleSetConnection(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            int connectionNum = utils.getIntScriptVar(self, "crafting4.puzzle.connectionNum");
            string_id sid_message = SID_COMPUTER_CORE_ERROR;
            switch (connectionNum)
            {
                case 0:
                sid_message = SID_COMPUTER_CORE_CONNECTION_ONE_SET;
                break;
                case 1:
                sid_message = SID_COMPUTER_CORE_CONNECTION_TWO_SET;
                break;
                case 2:
                sid_message = SID_COMPUTER_CORE_CONNECTION_THREE_SET;
                break;
                case 3:
                sid_message = SID_COMPUTER_CORE_CONNECTION_FOUR_SET;
                break;
            }
            string_id sid_wire = SID_COMPUTER_CORE_ERROR;
            switch (idx)
            {
                case 0:
                sid_wire = SID_NOTHING;
                break;
                case 1:
                sid_wire = SID_A_RED_WIRE;
                break;
                case 2:
                sid_wire = SID_A_BLUE_WIRE;
                break;
                case 3:
                sid_wire = SID_A_YELLOW_WIRE;
                break;
            }
            int[] puzzleGuess = getIntArrayObjVar(self, PUZZLE_GUESS_OBJVAR);
            if (puzzleGuess == null || puzzleGuess.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            puzzleGuess[connectionNum] = idx;
            setObjVar(self, PUZZLE_GUESS_OBJVAR, puzzleGuess);
            prose_package msg = prose.getPackage(sid_message, sid_wire);
            sendSystemMessageProse(player, msg);
            sendDirtyObjectMenuNotification(self);
        }
        else 
        {
            sendSystemMessage(player, SID_NO_SELECTION_MADE);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUpSelf(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "cleaningUp", true);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            if (groundquests.isQuestActive(player, "star_destroyer_intro_imperial"))
            {
                groundquests.clearQuest(player, "star_destroyer_intro_imperial");
            }
            if (groundquests.isQuestActive(player, "star_destroyer_intro_rebel"))
            {
                groundquests.clearQuest(player, "star_destroyer_intro_rebel");
            }
            if (groundquests.isQuestActive(player, "star_destroyer_intro_neutral"))
            {
                groundquests.clearQuest(player, "star_destroyer_intro_neutral");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canUse(obj_id player) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "star_destroyer_intro_imperial", "star_destroyer_intro_04") || groundquests.isTaskActive(player, "star_destroyer_intro_rebel", "star_destroyer_intro_04") || groundquests.isTaskActive(player, "star_destroyer_intro_neutral", "star_destroyer_intro_04"))
        {
            return true;
        }
        return false;
    }
    public boolean isConfigured(obj_id self) throws InterruptedException
    {
        return hasObjVar(self, PUZZLE_CONFIGURED_OBJVAR);
    }
    public void resetTheFlightRecorder(obj_id self) throws InterruptedException
    {
        obj_id owner = utils.getContainingPlayer(self);
        if (isIdValid(owner))
        {
            setObjVar(self, "owner", owner);
        }
        removeObjVar(self, "fs_crafting4");
        int[] puzzleGuess = 
        {
            NO_WIRE,
            NO_WIRE,
            NO_WIRE,
            NO_WIRE
        };
        int[] puzzleSolution = new int[PUZZLE_LENGTH];
        for (int i = 0; i < PUZZLE_LENGTH; i++)
        {
            int wire = rand(RED_WIRE, YELLOW_WIRE);
            puzzleSolution[i] = wire;
        }
        if (puzzleSolution != null && puzzleSolution.length > 0)
        {
            setObjVar(self, PUZZLE_SOLUTION_OBJVAR, puzzleSolution);
            setObjVar(self, PUZZLE_GUESS_OBJVAR, puzzleGuess);
            setObjVar(self, PUZZLE_INTEGRITY_OBJVAR, rand(93, 99));
        }
        return;
    }
    public void setConnection(obj_id self, obj_id player, int connectionNum) throws InterruptedException
    {
        string_id sid_message = SID_COMPUTER_CORE_ERROR;
        switch (connectionNum)
        {
            case 0:
            sid_message = SID_COMPUTER_CORE_SET_CONNECTION_ONE;
            break;
            case 1:
            sid_message = SID_COMPUTER_CORE_SET_CONNECTION_TWO;
            break;
            case 2:
            sid_message = SID_COMPUTER_CORE_SET_CONNECTION_THREE;
            break;
            case 3:
            sid_message = SID_COMPUTER_CORE_SET_CONNECTION_FOUR;
            break;
        }
        string_id sid_wire = getWireSid(self, PUZZLE_GUESS_OBJVAR, connectionNum);
        if (utils.hasScriptVar(player, "crafting4.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "crafting4.openSui");
            utils.removeScriptVar(player, "crafting4.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String str_msg = utils.packStringId(sid_message) + " " + utils.packStringId(sid_wire);
        String str_title = utils.packStringId(SID_COMPUTER_CORE_MSGBOX_TITLE);
        String[] connectionList = 
        {
            "No Wire",
            "Red Wire",
            "Blue Wire",
            "Yellow Wire"
        };
        int pid = sui.listbox(self, player, str_msg, sui.OK_CANCEL, str_title, connectionList, "handleSetConnection", false, false);
        if (pid > -1)
        {
            utils.setScriptVar(player, "crafting4.openSui", pid);
            utils.setScriptVar(self, "crafting4.puzzle.connectionNum", connectionNum);
            setSUIProperty(pid, "", "Size", "500,250");
            showSUIPage(pid);
        }
        return;
    }
    public string_id getWireSid(obj_id self, String objvarName, int connectionNum) throws InterruptedException
    {
        string_id sid_wire = SID_COMPUTER_CORE_ERROR;
        int[] puzzleGuess = getIntArrayObjVar(self, objvarName);
        if (puzzleGuess == null || puzzleGuess.length == 0)
        {
            return sid_wire;
        }
        int currentSetting = puzzleGuess[connectionNum];
        switch (currentSetting)
        {
            case 0:
            sid_wire = SID_NOTHING;
            break;
            case 1:
            sid_wire = SID_A_RED_WIRE;
            break;
            case 2:
            sid_wire = SID_A_BLUE_WIRE;
            break;
            case 3:
            sid_wire = SID_A_YELLOW_WIRE;
            break;
        }
        return sid_wire;
    }
    public void checkPuzzleSolutionAttempt(obj_id self, obj_id player) throws InterruptedException
    {
        int[] puzzleGuess = getIntArrayObjVar(self, PUZZLE_GUESS_OBJVAR);
        if (puzzleGuess == null || puzzleGuess.length == 0)
        {
            return;
        }
        int[] puzzleSolution = getIntArrayObjVar(self, PUZZLE_SOLUTION_OBJVAR);
        if (puzzleSolution == null || puzzleSolution.length == 0)
        {
            return;
        }
        boolean[] completeResults = 
        {
            false,
            false,
            false,
            false
        };
        int numWiresConnectedCorrectly = 0;
        for (int j = 0; j < puzzleSolution.length; j++)
        {
            int correctConnection = puzzleSolution[j];
            int guessWire = puzzleGuess[j];
            if (guessWire == correctConnection)
            {
                numWiresConnectedCorrectly = numWiresConnectedCorrectly + 1;
                completeResults[j] = true;
            }
        }
        if (numWiresConnectedCorrectly == 4)
        {
            groundquests.sendSignal(player, "star_destroyer_intro_04");
            String custLogMsg = "Star Destroyer Intro Quest: Player %TU has successfully reconfigured the flight recorder.";
            CustomerServiceLog("Star_Destroyer_Intro", custLogMsg, player);
            setObjVar(self, PUZZLE_CONFIGURED_OBJVAR, true);
            sendDirtyObjectMenuNotification(self);
            String str_message = utils.packStringId(SID_COMPUTER_CORE_COMPLETED);
            oneButtonMsgBox(self, player, "noHandler", str_message);
        }
        else 
        {
            int numCorrectWiresUsed = 0;
            boolean[] partialResults = new boolean[completeResults.length];
            for (int i = 0; i < completeResults.length; i++)
            {
                partialResults[i] = completeResults[i];
            }
            for (int q = 0; q < puzzleSolution.length; q++)
            {
                boolean alreadySolved = completeResults[q];
                if (!alreadySolved)
                {
                    int guess = puzzleGuess[q];
                    for (int k = 0; k < puzzleSolution.length; k++)
                    {
                        boolean alreadyUsed = partialResults[k];
                        if (!alreadyUsed)
                        {
                            int correctWire = puzzleSolution[k];
                            if (guess == correctWire)
                            {
                                numCorrectWiresUsed = numCorrectWiresUsed + 1;
                                partialResults[k] = true;
                                break;
                            }
                        }
                    }
                }
            }
            setObjVar(self, PUZZLE_LAST_TESTED_OBJVAR, puzzleGuess);
            setObjVar(self, PUZZLE_LAST_WIRES_CORRECT_OBJVAR, numWiresConnectedCorrectly);
            setObjVar(self, PUZZLE_LAST_WIRES_USED_OBJVAR, numCorrectWiresUsed);
            int integrity = getIntObjVar(self, PUZZLE_INTEGRITY_OBJVAR);
            integrity = integrity - (rand(12, 14));
            setObjVar(self, PUZZLE_INTEGRITY_OBJVAR, integrity);
            if (integrity <= 0)
            {
                sendSystemMessage(player, SID_COMPUTER_CORE_RUINED);
                String custLogMsg = "Star Destroyer Intro: Player %TU has failed to reconfigure the flight recorder and must obtain another one.";
                CustomerServiceLog("Star_Destroyer_Intro", custLogMsg, player);
                resetTheFlightRecorder(self);
            }
            else 
            {
                String str_msg = utils.packStringId(SID_COMPUTER_CORE_CURRENT_RESULT);
                str_msg = str_msg + " \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_INDICATORS_LIT) + " \\#pcontrast2 " + numWiresConnectedCorrectly;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_INDICATORS_FLICKERING) + " \\#pcontrast2 " + numCorrectWiresUsed;
                str_msg = str_msg + " \\#. \\#pcontrast1 " + utils.packStringId(SID_COMPUTER_CORE_INTEGRITY) + " \\#pcontrast2 " + integrity + "% \\#. ";
                oneButtonMsgBox(self, player, "noHandler", str_msg);
            }
        }
        return;
    }
    public string_id getConnectionMenuName(obj_id self, int connectionNum) throws InterruptedException
    {
        string_id menuName = SID_COMPUTER_CORE_ERROR;
        int[] puzzleGuess = getIntArrayObjVar(self, PUZZLE_GUESS_OBJVAR);
        if (puzzleGuess == null || puzzleGuess.length == 0)
        {
            return menuName;
        }
        int currentSetting = puzzleGuess[connectionNum];
        switch (connectionNum)
        {
            case 0:
            menuName = SID_MENU_CONNECTION_ONE_NOTHING;
            if (currentSetting == RED_WIRE)
            {
                menuName = SID_MENU_CONNECTION_ONE_RED;
            }
            else if (currentSetting == BLUE_WIRE)
            {
                menuName = SID_MENU_CONNECTION_ONE_BLUE;
            }
            else if (currentSetting == YELLOW_WIRE)
            {
                menuName = SID_MENU_CONNECTION_ONE_YELLOW;
            }
            break;
            case 1:
            menuName = SID_MENU_CONNECTION_TWO_NOTHING;
            if (currentSetting == RED_WIRE)
            {
                menuName = SID_MENU_CONNECTION_TWO_RED;
            }
            else if (currentSetting == BLUE_WIRE)
            {
                menuName = SID_MENU_CONNECTION_TWO_BLUE;
            }
            else if (currentSetting == YELLOW_WIRE)
            {
                menuName = SID_MENU_CONNECTION_TWO_YELLOW;
            }
            break;
            case 2:
            menuName = SID_MENU_CONNECTION_THREE_NOTHING;
            if (currentSetting == RED_WIRE)
            {
                menuName = SID_MENU_CONNECTION_THREE_RED;
            }
            else if (currentSetting == BLUE_WIRE)
            {
                menuName = SID_MENU_CONNECTION_THREE_BLUE;
            }
            else if (currentSetting == YELLOW_WIRE)
            {
                menuName = SID_MENU_CONNECTION_THREE_YELLOW;
            }
            break;
            case 3:
            menuName = SID_MENU_CONNECTION_FOUR_NOTHING;
            if (currentSetting == RED_WIRE)
            {
                menuName = SID_MENU_CONNECTION_FOUR_RED;
            }
            else if (currentSetting == BLUE_WIRE)
            {
                menuName = SID_MENU_CONNECTION_FOUR_BLUE;
            }
            else if (currentSetting == YELLOW_WIRE)
            {
                menuName = SID_MENU_CONNECTION_FOUR_YELLOW;
            }
            break;
        }
        return menuName;
    }
    public int oneButtonMsgBox(obj_id controller, obj_id player, String handler, String textMsg) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "crafting4.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "crafting4.openSui");
            utils.removeScriptVar(player, "crafting4.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String str_title = utils.packStringId(SID_COMPUTER_CORE_MSGBOX_TITLE);
        String str_ok_button = utils.packStringId(SID_CLOSE_BUTTON);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Size", "500,200");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, str_title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, textMsg);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, str_ok_button);
        utils.setScriptVar(player, "crafting4.openSui", pid);
        sui.showSUIPage(pid);
        return pid;
    }
    public void printBooleanArray(boolean[] stuff, obj_id player, String msg) throws InterruptedException
    {
        for (int i = 0; i < stuff.length; i++)
        {
            boolean one = stuff[i];
            msg = msg + one + " ";
        }
        sendSystemMessage(player, msg, "");
        return;
    }
    public void printIntArray(int[] stuff, obj_id player, String msg) throws InterruptedException
    {
        for (int i = 0; i < stuff.length; i++)
        {
            int one = stuff[i];
            msg = msg + one + " ";
        }
        sendSystemMessage(player, msg, "");
        return;
    }
}
