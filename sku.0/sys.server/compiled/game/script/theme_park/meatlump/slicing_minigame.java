package script.theme_park.meatlump;

import script.*;
import script.library.*;

public class slicing_minigame extends script.base_script
{
    public slicing_minigame()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String VAR_PREFIX = "meatlump_safe";
    public static final String NONIMPERATIVE_VAR_PREFIX = "meatlump_safe_nonimperative";
    public static final String PID_NAME = NONIMPERATIVE_VAR_PREFIX + ".pid";
    public static final String PASSWORD = VAR_PREFIX + ".password";
    public static final String PASSWORD_SCRAMBLED = VAR_PREFIX + ".password_scrambled";
    public static final String PASSWORD_ROW = VAR_PREFIX + ".password_row";
    public static final String PASSWORD_POINTS_NEEDED = VAR_PREFIX + ".password_points_needed";
    public static final String WRONG_ANSWER_THRESHOLD = VAR_PREFIX + ".wrong_answer_threshold";
    public static final String UNSCRAMBLED_PASSWORD_ROW = VAR_PREFIX + ".unscrambled_password_row";
    public static final String HAS_BUFF = VAR_PREFIX + ".has_buff";
    public static final String PASSWORD_POINTS_CURRENT = NONIMPERATIVE_VAR_PREFIX + ".password_points_current";
    public static final String CURRENTLY_SLICING = NONIMPERATIVE_VAR_PREFIX + ".slicing";
    public static final String ANAGRAM_GUESS_LIST = NONIMPERATIVE_VAR_PREFIX + ".anagram_guess_list";
    public static final String WRONG_ANSWER_COUNT = NONIMPERATIVE_VAR_PREFIX + ".wrong_answer_count";
    public static final String TOTAL_ANSWER_COUNT = NONIMPERATIVE_VAR_PREFIX + ".total_answer_count";
    public static final String OBJVAR_SLOT_NAME = "collection.slotName";
    public static final String SAFE_CAPTION = "OLD SAFE";
    public static final String DEVICE_TEMPLATE = "object/tangible/meatlump/event/slicing_device_meatlump_safe.iff";
    public static final String DEVICE_OBJVAR = "puzzle";
    public static final String SAFE_PUZZLE_BUFF = "safe_puzzle_buff";
    public static final String PASSWORD_TABLE = "datatables/theme_park/meatlump_safe_passwords.iff";
    public static final string_id SID_OPEN_SAFE = new string_id("meatlump/meatlump", "meatlump_safe_open");
    public static final string_id SAFE_INTRO_TEXT = new string_id("meatlump/meatlump", "safe_minigame_text");
    public static final string_id SID_ALREADY_GUESSED_PREVIOUS = new string_id("meatlump/meatlump", "safe_guessed_previous");
    public static final string_id SID_YOU_FAILED_TO_SOLVE = new string_id("meatlump/meatlump", "you_failed_to_solve_slicing_puzzle");
    public static final string_id YOU_FAILED = new string_id("meatlump/meatlump", "you_failed");
    public static final string_id YOU_CANCELED_EARLY = new string_id("meatlump/meatlump", "you_canceled_early");
    public static final string_id YOU_HAVE_DEBUFF = new string_id("meatlump/meatlump", "you_have_debuff");
    public static final string_id YOU_NEED_DEVICE = new string_id("meatlump/meatlump", "you_need_device");
    public static final string_id YOU_BUFFED_LATE = new string_id("meatlump/meatlump", "you_buff_late");
    public static final int SKIPPED_COLUMNS = 2;
    public static final int DEFAULT_ANAGRAM_POINT = 1;
    public static final int BUFF_THRESHOLD_INCREASE = 3;
    public static final int BUFF_POINTS_NEEDED_DECREASE = 1;
    public static final String[] RESERVED_RULES_TO_IGNORE = new String[]
    {
        "name_declined_number",
        "name_declined_syntax",
        "name_declined_fictionally_reserved",
        "name_declined_reserved"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN_SAFE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id collectionItem = self;
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (!collection.canCollectCollectible(player, self))
        {
            return SCRIPT_CONTINUE;
        }
        else if (buff.hasBuff(player, "safe_puzzle_downer"))
        {
            sendSystemMessage(player, YOU_HAVE_DEBUFF);
            return SCRIPT_CONTINUE;
        }
        else if (!consumable.decrementObjectInventoryOrEquipped(player, DEVICE_TEMPLATE, DEVICE_OBJVAR))
        {
            sendSystemMessage(player, YOU_NEED_DEVICE);
            return SCRIPT_CONTINUE;
        }
        closeOldWindow(player);
        blog("OnObjectMenuSelect");
        createSafeSui(collectionItem, player);
        return SCRIPT_CONTINUE;
    }
    public boolean createSafeSui(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (!collection.checkState(player))
        {
            return false;
        }
        blog("createSafeSui - init");
        if (rerollPuzzleNeeded(collectionItem, player))
        {
            blog("setting up password for slicing session");
            boolean varsSet = getRandomPassword(collectionItem, player);
            if (!varsSet)
            {
                return false;
            }
        }
        else 
        {
            closeOldWindow(player);
        }
        blog("createSafeSui - CURRENTLY_SLICING set to true");
        utils.setScriptVar(player, CURRENTLY_SLICING, true);
        if (utils.hasScriptVar(player, TOTAL_ANSWER_COUNT))
        {
            blog("createSafeSui - INCREMENTING TOTATL COUNT");
            int count = utils.getIntScriptVar(player, TOTAL_ANSWER_COUNT);
            utils.setScriptVar(player, TOTAL_ANSWER_COUNT, count++);
        }
        else 
        {
            utils.setScriptVar(player, TOTAL_ANSWER_COUNT, 0);
        }
        String jumbledPassword = utils.getStringScriptVar(player, PASSWORD_SCRAMBLED + "_" + collectionItem);
        int datatableRow = utils.getIntScriptVar(player, PASSWORD_ROW + "_" + collectionItem);
        int passwordPointsNeeded = utils.getIntScriptVar(player, PASSWORD_POINTS_NEEDED + "_" + collectionItem);
        int passwordPointsCurrent = utils.getIntScriptVar(player, PASSWORD_POINTS_CURRENT);
        int wrongAnswerCount = utils.getIntScriptVar(player, WRONG_ANSWER_COUNT);
        int wrongAnswerThreshold = utils.getIntScriptVar(player, WRONG_ANSWER_THRESHOLD + "_" + collectionItem);
        String questionnaireText = sui.colorWhite() + localize(SAFE_INTRO_TEXT);
        String guessList = utils.getStringScriptVar(player, ANAGRAM_GUESS_LIST);
        blog("createSafeSui - displaying SUI data");
        questionnaireText += sui.newLine(2);
        questionnaireText += "Scrambled Password: ";
        questionnaireText += sui.colorRed() + jumbledPassword + sui.colorWhite() + sui.newLine();
        questionnaireText += "Total Fail Attempts: " + sui.colorRed() + wrongAnswerCount + sui.colorWhite() + sui.newLine();
        questionnaireText += "Maximum Fail Attempts Allowed: " + sui.colorRed() + (wrongAnswerThreshold + 1) + sui.colorWhite() + sui.newLine();
        questionnaireText += "Points Needed: ";
        questionnaireText += sui.colorRed() + passwordPointsNeeded + sui.colorWhite() + sui.newLine();
        questionnaireText += "Current Points: ";
        questionnaireText += sui.colorRed() + passwordPointsCurrent + sui.colorWhite() + sui.newLine(2);
        if (guessList != null && !guessList.equals(""))
        {
            questionnaireText += sui.colorGreen() + guessList + sui.colorWhite() + sui.newLine();
        }
        dictionary params = new dictionary();
        int pid = createSUIPage("/Script.questionnaire", collectionItem, player);
        setSUIAssociatedLocation(pid, collectionItem);
        setSUIMaxRangeToObject(pid, 8);
        params.put("callingPid", pid);
        sui.setPid(player, pid, PID_NAME);
        setSUIProperty(pid, "Prompt.lblPrompt", "LocalText", questionnaireText);
        setSUIProperty(pid, "bg.caption.lblTitle", "Text", SAFE_CAPTION);
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "false");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "false");
        setSUIProperty(pid, "txtInput", "Editable", "true");
        setSUIProperty(pid, "txtInput", "GetsInput", "true");
        setSUIProperty(pid, "btnOk", "Visible", "true");
        setSUIProperty(pid, "btnCancel", "Visible", "true");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onClosedOk, "%button2%", "txtInput", "LocalText");
        subscribeToSUIEvent(pid, sui_event_type.SET_onClosedOk, "%button2%", "handleDialogInput");
        subscribeToSUIEvent(pid, sui_event_type.SET_onClosedCancel, "%button0%", "closeSui");
        showSUIPage(pid);
        flushSUIPage(pid);
        return true;
    }
    public int handleDialogInput(obj_id self, dictionary params) throws InterruptedException
    {
        blog("handleDialogInput");
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player))
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - player validated");
        blog("handleDialogInput - params: " + params);
        if (params.getString("txtInput.LocalText") == null)
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - validation completed: ");
        String playerGuess = params.getString("txtInput.LocalText");
        blog("handleDialogInput - playerGuess: " + playerGuess);
        String correctPassword = utils.getStringScriptVar(player, PASSWORD + "_" + self);
        if (playerGuess.equals(correctPassword))
        {
            blog("handleDialogInput - Password Solved!! Rewardign Player");
            rewardPlayer(self, player);
            return SCRIPT_CONTINUE;
        }
        else if (isNameReserved(playerGuess, RESERVED_RULES_TO_IGNORE))
        {
            blog("handleDialogInput - reserved or foul language");
            if (!incrementWrongAnswer(self, player))
            {
                applyPuzzleDebuff(self, player);
                return SCRIPT_CONTINUE;
            }
            createSafeSui(self, player);
            return SCRIPT_CONTINUE;
        }
        else if (utils.hasScriptVar(player, ANAGRAM_GUESS_LIST) && !checkGuessList(self, player, playerGuess))
        {
            blog("handleDialogInput - player attempted to reuse a previously used guess: " + playerGuess);
            sendSystemMessage(player, SID_ALREADY_GUESSED_PREVIOUS);
            if (!incrementWrongAnswer(self, player))
            {
                applyPuzzleDebuff(self, player);
                return SCRIPT_CONTINUE;
            }
            createSafeSui(self, player);
            return SCRIPT_CONTINUE;
        }
        else if (playerGuess.length() > 4)
        {
            blog("handleDialogInput - USED AN ENTRY LONGER THAN 4");
            if (!incrementWrongAnswer(self, player))
            {
                applyPuzzleDebuff(self, player);
                return SCRIPT_CONTINUE;
            }
            createSafeSui(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - Guess != password");
        int datatableRow = utils.getIntScriptVar(player, PASSWORD_ROW + "_" + self);
        if (datatableRow < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int totalCols = (dataTableGetNumColumns(PASSWORD_TABLE) - SKIPPED_COLUMNS);
        if (totalCols < 0)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dictRow = dataTableGetRow(PASSWORD_TABLE, datatableRow);
        if (dictRow == null)
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - Datatable Validation Complete");
        boolean recreateSui = true;
        boolean incrementWrong = true;
        for (int i = 1; i < totalCols; i++)
        {
            String anagram = dictRow.getString("anagram" + i);
            if (anagram == null || anagram.equals(""))
            {
                continue;
            }
            else if (!playerGuess.equals(anagram))
            {
                continue;
            }
            else 
            {
                blog("handleDialogInput - match found for: " + playerGuess);
                recreateSui = givePoints(self, player, anagram);
                incrementWrong = false;
                break;
            }
        }
        blog("handleDialogInput - Almost done, about to recreate SUI.");
        if (incrementWrong)
        {
            if (!incrementWrongAnswer(self, player))
            {
                applyPuzzleDebuff(self, player);
                return SCRIPT_CONTINUE;
            }
            else if (recreateSui)
            {
                createSafeSui(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean applyPuzzleDebuff(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        closeOldWindow(player);
        removePlayerNonImperativeVars(player);
        return true;
    }
    public boolean incrementWrongAnswer(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        blog("incrementWrongAnswer - INIT");
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        blog("incrementWrongAnswer - Player and Collection Obj are valid");
        if (!utils.hasScriptVar(player, WRONG_ANSWER_COUNT))
        {
            blog("incrementWrongAnswer - WRONG_ANSWER_COUNT var not found. Must have been a cancel and restart");
            utils.setScriptVar(player, WRONG_ANSWER_COUNT, 0);
        }
        else if (!utils.hasScriptVar(player, WRONG_ANSWER_THRESHOLD + "_" + collectionItem))
        {
            blog("incrementWrongAnswer - WRONG_ANSWER_THRESHOLD var not found");
            return false;
        }
        int currentCount = utils.getIntScriptVar(player, WRONG_ANSWER_COUNT);
        int threshold = utils.getIntScriptVar(player, WRONG_ANSWER_THRESHOLD + "_" + collectionItem);
        blog("incrementWrongAnswer - WRONG_ANSWER_COUNT: " + currentCount);
        blog("incrementWrongAnswer - WRONG_ANSWER_THRESHOLD: " + threshold);
        if (currentCount < 0 || threshold < 0)
        {
            return false;
        }
        blog("incrementWrongAnswer - currentCount: " + currentCount);
        blog("incrementWrongAnswer - threshold: " + threshold);
        if (currentCount >= threshold)
        {
            if (buff.applyBuff(player, "safe_puzzle_downer"))
            {
                
            }
            
            {
                sendSystemMessage(player, YOU_FAILED);
            }
            return false;
        }
        blog("incrementWrongAnswer - INCREMENTING " + currentCount);
        utils.setScriptVar(player, WRONG_ANSWER_COUNT, currentCount + 1);
        blog("incrementWrongAnswer - INCREMENTED " + utils.getIntScriptVar(player, WRONG_ANSWER_COUNT));
        return true;
    }
    public boolean getRandomPassword(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !exists(collectionItem))
        {
            return false;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        blog("getRandomPassword - init");
        int datatableLength = dataTableGetNumRows(PASSWORD_TABLE);
        int randomNum = rand(0, datatableLength - 1);
        if (randomNum < 0)
        {
            return false;
        }
        String password = dataTableGetString(PASSWORD_TABLE, randomNum, "password");
        if (password == null || password.equals(""))
        {
            return false;
        }
        int pointsNeeded = dataTableGetInt(PASSWORD_TABLE, randomNum, "points_needed");
        if (pointsNeeded < 0)
        {
            return false;
        }
        int threshold = dataTableGetInt(PASSWORD_TABLE, randomNum, "threshold");
        if (threshold < 0)
        {
            return false;
        }
        int iteration = 0;
        String scrambled = password;
        while (scrambled.equals(password) && iteration < 2)
        {
            scrambled = scramblePassword(password);
            if (scrambled == null || scrambled.equals(""))
            {
                return false;
            }
            iteration++;
        }
        boolean hasBuff = false;
        if (buff.hasBuff(player, SAFE_PUZZLE_BUFF))
        {
            blog("getRandomPassword - Giving player buff var");
            utils.setScriptVar(player, HAS_BUFF + "_" + collectionItem, true);
            if (pointsNeeded > 1)
            {
                pointsNeeded -= BUFF_POINTS_NEEDED_DECREASE;
            }
            threshold += BUFF_THRESHOLD_INCREASE;
        }
        utils.setScriptVar(player, PASSWORD + "_" + collectionItem, password);
        utils.setScriptVar(player, PASSWORD_SCRAMBLED + "_" + collectionItem, scrambled);
        utils.setScriptVar(player, PASSWORD_ROW + "_" + collectionItem, randomNum);
        utils.setScriptVar(player, PASSWORD_POINTS_NEEDED + "_" + collectionItem, pointsNeeded);
        utils.setScriptVar(player, PASSWORD_POINTS_CURRENT, 0);
        utils.setScriptVar(player, WRONG_ANSWER_THRESHOLD + "_" + collectionItem, threshold);
        utils.setScriptVar(player, WRONG_ANSWER_COUNT, 0);
        utils.setScriptVar(player, TOTAL_ANSWER_COUNT, 0);
        utils.setScriptVar(player, UNSCRAMBLED_PASSWORD_ROW + "_" + collectionItem, randomNum);
        return true;
    }
    public boolean rerollPuzzleNeeded(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !exists(collectionItem))
        {
            return false;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        blog("rerollPuzzleNeeded - init");
        boolean hasBuff = false;
        boolean alreadyBuffed = false;
        boolean slicing = false;
        boolean counting = false;
        boolean hasPassword = false;
        if (buff.hasBuff(player, SAFE_PUZZLE_BUFF))
        {
            hasBuff = true;
        }
        if (utils.hasScriptVar(player, HAS_BUFF + "_" + collectionItem))
        {
            alreadyBuffed = true;
        }
        if (utils.hasScriptVar(player, CURRENTLY_SLICING))
        {
            slicing = true;
        }
        if (utils.hasScriptVar(player, TOTAL_ANSWER_COUNT))
        {
            counting = true;
        }
        if (utils.hasScriptVar(player, PASSWORD + "_" + collectionItem))
        {
            hasPassword = true;
        }
        blog("rerollPuzzleNeeded - hasBuff: " + hasBuff + " alreadyBuffed: " + alreadyBuffed + " slicing: " + slicing + " counting: " + counting + " hasPassword: " + hasPassword);
        if (!counting && slicing && hasPassword)
        {
            blog("rerollPuzzleNeeded - Player clicked on the puzzle more than once without cancel. We DO NOT need Reroll.");
            return false;
        }
        else if (counting && !slicing)
        {
            blog("rerollPuzzleNeeded - ERROR: player is counting but has no slicing var. We need Reroll.");
            return true;
        }
        else if (!counting && !slicing && !hasPassword)
        {
            blog("rerollPuzzleNeeded - player is not counting, slicing and has no password.  We need Reroll.");
            return true;
        }
        else if (counting && slicing && alreadyBuffed && hasPassword)
        {
            blog("rerollPuzzleNeeded - player is counting, slicing and already has buff. NOT DISTURBING.");
            return false;
        }
        else if (!counting && !slicing && hasBuff && !alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player is not counting, not slicing, doesnt already have buff but has a buff now. We need Reroll.");
            return true;
        }
        else if (counting && slicing && hasBuff && !alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player has used buff while solving puzzle. NOT DISTURBING.");
            sendSystemMessage(player, YOU_BUFFED_LATE);
            return false;
        }
        else if (!counting && !slicing && !hasBuff && alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player had a buff at one time but stopped solving puzzle and has come back. Reroll needed.");
            utils.removeScriptVar(player, HAS_BUFF + "_" + collectionItem);
            return true;
        }
        blog("rerollPuzzleNeeded - None of the conditions met. NOT DISTURBING.");
        return false;
    }
    public String scramblePassword(String password) throws InterruptedException
    {
        if (password == null || password.equals(""))
        {
            return null;
        }
        String reversedPassword = "";
        String scrambled = "";
        java.util.Random r;
        r = new java.util.Random();
        for (int i = 0; i < password.length(); i++)
        {
            if (r.nextBoolean())
            {
                scrambled = scrambled + password.charAt(i);
            }
            else 
            {
                scrambled = password.charAt(i) + scrambled;
            }
        }
        for (int d = (password.length() - 1); d >= 0; d--)
        {
            reversedPassword += scrambled.charAt(d);
        }
        if (reversedPassword == null || reversedPassword.equals("") || reversedPassword.length() < 0)
        {
            return null;
        }
        else if (reversedPassword.length() != password.length())
        {
            return null;
        }
        return scrambled;
    }
    public boolean checkGuessList(obj_id collectionItem, obj_id player, String anagram) throws InterruptedException
    {
        blog("checkGuessList");
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (anagram == null || anagram.equals(""))
        {
            return false;
        }
        blog("checkGuessList validation completed. anagram = " + anagram);
        if (!utils.hasScriptVar(player, ANAGRAM_GUESS_LIST))
        {
            return true;
        }
        blog("checkGuessList anagram list found");
        String guessList = utils.getStringScriptVar(player, ANAGRAM_GUESS_LIST);
        if (guessList == null || guessList.equals(""))
        {
            return false;
        }
        blog("checkGuessList guessList populated with previous list: " + guessList);
        String[] splitAnagramList = split(guessList, ',');
        if (splitAnagramList == null || splitAnagramList.length < 0)
        {
            return false;
        }
        int listLength = splitAnagramList.length;
        blog("checkGuessList listLength: " + listLength);
        for (String s : splitAnagramList) {
            blog("checkGuessList splitAnagramList:" + s.trim() + "* anagram:" + anagram);
            if ((s.trim()).equals(anagram)) {
                return false;
            }
        }
        blog("checkGuessList went through the entire list and found no match.");
        return true;
    }
    public boolean givePoints(obj_id collectionItem, obj_id player, String anagram) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (anagram == null || anagram.equals(""))
        {
            return false;
        }
        int passwordPointsNeeded = utils.getIntScriptVar(player, PASSWORD_POINTS_NEEDED + "_" + collectionItem);
        int passwordPointsCurrent = utils.getIntScriptVar(player, PASSWORD_POINTS_CURRENT);
        if (passwordPointsNeeded < 0 || passwordPointsCurrent < 0)
        {
            return false;
        }
        else if (passwordPointsNeeded == passwordPointsCurrent)
        {
            rewardPlayer(collectionItem, player);
            return false;
        }
        else if (passwordPointsCurrent >= passwordPointsNeeded)
        {
            rewardPlayer(collectionItem, player);
            return false;
        }
        String guessList = utils.getStringScriptVar(player, ANAGRAM_GUESS_LIST);
        if (guessList != null && !guessList.equals(""))
        {
            utils.setScriptVar(player, ANAGRAM_GUESS_LIST, guessList + ", " + anagram);
        }
        else 
        {
            utils.setScriptVar(player, ANAGRAM_GUESS_LIST, anagram);
        }
        int point = DEFAULT_ANAGRAM_POINT;
        if ((passwordPointsCurrent + point) >= passwordPointsNeeded)
        {
            rewardPlayer(collectionItem, player);
            return false;
        }
        utils.setScriptVar(player, PASSWORD_POINTS_CURRENT, passwordPointsCurrent + point);
        createSafeSui(collectionItem, player);
        return true;
    }
    public boolean rewardPlayer(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        blog("rewardPlayer - init");
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (!exists(collectionItem) || !exists(player))
        {
            return false;
        }
        blog("rewardPlayer - validation complete");
        if (!collection.rewardPlayerCollectionSlot(player, collectionItem))
        {
            blog("rewardPlayer - rewardPlayerCollectionSlot = FAIL!!!");
            closeOldWindow(player);
            removePlayerNonImperativeVars(player);
            return false;
        }
        blog("rewardPlayer - rewardPlayerCollectionSlot = GIVE LOOT");
        loot.giveMeatlumpPuzzleLoot(player, true, false);
        blog("rewardPlayer - rewardPlayerCollectionSlot = SUCCESS");
        closeOldWindow(player);
        removePlayerNonImperativeVars(player);
        removePlayerImperativeVars(player);
        return true;
    }
    public int closeSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.applyBuff(player, "safe_puzzle_downer"))
        {
            
        }
        
        {
            sendSystemMessage(player, YOU_CANCELED_EARLY);
        }
        blog("closeSui");
        closeOldWindow(player);
        blog("closeSui - removePlayerNonImperativeVars");
        removePlayerNonImperativeVars(player);
        return SCRIPT_CONTINUE;
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        int pid = sui.getPid(player, PID_NAME);
        if (pid > -1)
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
        }
    }
    public void removePlayerNonImperativeVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, NONIMPERATIVE_VAR_PREFIX);
        utils.removeObjVar(player, NONIMPERATIVE_VAR_PREFIX);
    }
    public void removePlayerImperativeVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, VAR_PREFIX);
        utils.removeObjVar(player, VAR_PREFIX);
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG("minigame", msg);
        }
        return true;
    }
}
