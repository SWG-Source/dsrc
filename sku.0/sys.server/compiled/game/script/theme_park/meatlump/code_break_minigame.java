package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.collection;
import script.library.consumable;
import script.library.loot;
import script.library.sui;
import script.library.stealth;
import script.library.utils;
import java.util.Random;

public class code_break_minigame extends script.base_script
{
    public code_break_minigame()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String VAR_PREFIX = "meatlump_code";
    public static final String NONIMPERATIVE_VAR_PREFIX = "meatlump_safe_nonimperative";
    public static final String PID_NAME = NONIMPERATIVE_VAR_PREFIX + ".pid";
    public static final String NUMBER_OF_COMBOS = VAR_PREFIX + ".numberOfCombinations";
    public static final String GUESS_POSITION = NONIMPERATIVE_VAR_PREFIX + ".guessPostion";
    public static final String TOTAL_GUESSES = VAR_PREFIX + ".total_guesses";
    public static final String CURRENTLY_SLICING = NONIMPERATIVE_VAR_PREFIX + ".slicing";
    public static final String BUFF_USED = VAR_PREFIX + ".buff_used";
    public static final String NUMBER_VALUES = NONIMPERATIVE_VAR_PREFIX + ".number_values";
    public static final String OBJVAR_SLOT_NAME = "collection.slotName";
    public static final String SAFE_CAPTION = "LOCK BREAKER";
    public static final String DEVICE_TEMPLATE = "object/tangible/meatlump/event/slicing_device_meatlump_container.iff";
    public static final String DEVICE_OBJVAR = "puzzle";
    public static final String CONTAINER_PUZZLE_BUFF = "locked_container_puzzle_buff";
    public static final String THRESHOLD_TABLE = "datatables/theme_park/meatlump_threshold.iff";
    public static final string_id SID_OPEN_SAFE = new string_id("meatlump/meatlump", "meatlump_container_open");
    public static final string_id SAFE_INTRO_TEXT = new string_id("meatlump/meatlump", "slicing_minigame_text");
    public static final string_id SID_YOU_GUESSED_CORRECT = new string_id("meatlump/meatlump", "guessed_correct");
    public static final string_id SID_YOU_GUESSED_CORRECT_GOD = new string_id("meatlump/meatlump", "guessed_correct_godmode");
    public static final string_id SID_YOU_GUESSED_INCORRECT_GOD = new string_id("meatlump/meatlump", "guessed_incorrect_godmode");
    public static final string_id SID_ALL_THRESHOLDS_MET = new string_id("meatlump/meatlump", "all_thresholds_met");
    public static final string_id YOU_CANCELED_EARLY = new string_id("meatlump/meatlump", "you_canceled_early");
    public static final string_id YOU_HAVE_DEBUFF = new string_id("meatlump/meatlump", "you_have_debuff");
    public static final string_id YOU_NEED_DEVICE = new string_id("meatlump/meatlump", "you_need_device");
    public static final string_id YOU_BUFFED_LATE = new string_id("meatlump/meatlump", "you_buff_late");
    public static final int TOTAL_GUESS_DEFAULT = 0;
    public static final int BUFF_COMBO_AMOUNT = 3;
    public static final int MAX_INT_COMBO_1 = 10000;
    public static final int MAX_INT_COMBO_2 = 5000;
    public static final int MAX_INT_COMBO_3 = 2000;
    public static final int MAX_INT_COMBO_4 = 1000;
    public static final int MAX_INT_COMBO_5 = 500;
    public static final int MAX_INT_COMBO_6 = 100;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN_SAFE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(player))
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
        else if (buff.hasBuff(player, "locked_container_puzzle_downer"))
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
        createSui(collectionItem, player);
        return SCRIPT_CONTINUE;
    }
    public boolean createSui(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (!collection.checkState(player))
        {
            return false;
        }
        blog("createSui - init");
        if (rerollPuzzleNeeded(collectionItem, player))
        {
            blog("setting up password for slicing session");
            boolean varsSet = getRandomNumberCombinations(collectionItem, player);
            if (!varsSet)
            {
                return false;
            }
        }
        else 
        {
            closeOldWindow(player);
        }
        blog("createSui - setting slicing to true");
        utils.setScriptVar(player, CURRENTLY_SLICING + "_" + collectionItem, true);
        int numberOfCombos = utils.getIntScriptVar(player, NUMBER_OF_COMBOS + "_" + collectionItem);
        if (numberOfCombos <= 0)
        {
            blog("numberOfCombos failed: " + numberOfCombos);
            return false;
        }
        blog("numberOfCombos: " + numberOfCombos);
        blog("createSui - displaying SUI data");
        String questionnaireText = sui.colorWhite() + localize(SAFE_INTRO_TEXT);
        int guessPostion = 1;
        if (utils.hasScriptVar(player, GUESS_POSITION + "_" + collectionItem))
        {
            guessPostion = utils.getIntScriptVar(player, GUESS_POSITION + "_" + collectionItem);
        }
        else 
        {
            utils.setScriptVar(player, GUESS_POSITION + "_" + collectionItem, guessPostion);
        }
        blog("createSui - guessPostion: " + guessPostion);
        questionnaireText += sui.newLine(2);
        for (int i = 1; i < numberOfCombos + 1; i++)
        {
            questionnaireText += "Number combination " + i + ": ";
            if (guessPostion <= i)
            {
                blog("createSui - guessPostion <= " + i);
                questionnaireText += sui.colorRed() + utils.getStringScriptVar(player, VAR_PREFIX + ".number_combo_asterisk_" + i + "_" + collectionItem);
                if (utils.hasScriptVar(player, NUMBER_VALUES + "_" + collectionItem + ".number_combo_guess_" + i + "_" + collectionItem))
                {
                    blog("createSui - FOUND number_combo_guess_" + i);
                    questionnaireText += sui.newLine() + sui.colorBlue() + utils.getStringScriptVar(player, NUMBER_VALUES + "_" + collectionItem + ".number_combo_guess_" + i + "_" + collectionItem);
                    questionnaireText += sui.colorWhite();
                }
                else 
                {
                    questionnaireText += sui.newLine();
                }
            }
            else 
            {
                questionnaireText += sui.colorGreen() + utils.getIntScriptVar(player, VAR_PREFIX + ".number_combo_" + i + "_" + collectionItem) + sui.newLine();
            }
            questionnaireText += sui.colorWhite() + sui.newLine();
        }
        dictionary params = new dictionary();
        int pid = createSUIPage("/Script.sliceTerminal", collectionItem, player);
        setSUIAssociatedLocation(pid, collectionItem);
        setSUIMaxRangeToObject(pid, 8);
        params.put("callingPid", pid);
        sui.setPid(player, pid, PID_NAME);
        setSUIProperty(pid, "sliceInstructions.lblPrompt", "LocalText", questionnaireText);
        setSUIProperty(pid, "bg.caption.lblTitle", "Text", SAFE_CAPTION);
        setSUIProperty(pid, "sliceInstructions.lblPrompt", "Editable", "false");
        setSUIProperty(pid, "sliceInstructions.lblPrompt", "GetsInput", "false");
        setSUIProperty(pid, "result.numberBox", "Editable", "true");
        setSUIProperty(pid, "result.numberBox", "GetsInput", "true");
        setSUIProperty(pid, "btnOk", "Visible", "true");
        setSUIProperty(pid, "btnCancel", "Visible", "true");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onClosedOk, "%button2%", "result.numberBox", "LocalText");
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
        if (params.getString("result.numberBox.LocalText") == null)
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - validation completed: ");
        String playerGuess = params.getString("result.numberBox.LocalText");
        blog("handleDialogInput - playerGuess: " + playerGuess);
        int playerGuessInt = utils.stringToInt(playerGuess);
        int totalGuesses = TOTAL_GUESS_DEFAULT;
        if (utils.hasScriptVar(player, TOTAL_GUESSES + "_" + self))
        {
            totalGuesses = utils.getIntScriptVar(player, TOTAL_GUESSES + "_" + self);
        }
        blog("handleDialogInput - totalGuesses: " + totalGuesses);
        if (playerGuessInt < 0)
        {
            blog("handleDialogInput - playerGuessInt faled: " + playerGuessInt);
            utils.setScriptVar(player, TOTAL_GUESSES + "_" + self, totalGuesses++);
            createSui(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - playerGuessInt: " + playerGuessInt);
        if (!utils.hasScriptVar(player, GUESS_POSITION + "_" + self))
        {
            blog("handleDialogInput - no GUESS_POSITION");
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - GUESS_POSITION attained");
        int guessPostion = utils.getIntScriptVar(player, GUESS_POSITION + "_" + self);
        if (guessPostion <= 0)
        {
            blog("handleDialogInput - guessPostion: " + guessPostion);
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - guessPostion: " + guessPostion);
        int secretNumber = utils.getIntScriptVar(player, VAR_PREFIX + ".number_combo_" + guessPostion + "_" + self);
        if (secretNumber < 0)
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleDialogInput - secretNumber: " + secretNumber + " and the player guess was: " + playerGuessInt);
        if (playerGuessInt == secretNumber)
        {
            blog("handleDialogInput - Password Solved!! Rewardign Player");
            completeNumberGuess(self, player, guessPostion);
            return SCRIPT_CONTINUE;
        }
        else if (playerGuessInt > secretNumber)
        {
            blog("handleDialogInput Too High");
            utils.setScriptVar(player, NUMBER_VALUES + "_" + self + ".number_combo_guess_" + guessPostion + "_" + self, " (" + playerGuessInt + " too high)");
            utils.setScriptVar(player, TOTAL_GUESSES + "_" + self, totalGuesses++);
        }
        else 
        {
            blog("handleDialogInput Too Low");
            utils.setScriptVar(player, NUMBER_VALUES + "_" + self + ".number_combo_guess_" + guessPostion + "_" + self, " (" + playerGuessInt + " too low)");
            utils.setScriptVar(player, TOTAL_GUESSES + "_" + self, totalGuesses++);
        }
        createSui(self, player);
        return SCRIPT_CONTINUE;
    }
    public boolean getRandomNumberCombinations(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        boolean hasBuff = false;
        if (buff.hasBuff(player, CONTAINER_PUZZLE_BUFF))
        {
            hasBuff = true;
            utils.setScriptVar(player, BUFF_USED + "_" + collectionItem, true);
        }
        blog("HASBUFF: " + hasBuff);
        int amountOfNumbers = rand(4, 6);
        if (hasBuff)
        {
            amountOfNumbers = BUFF_COMBO_AMOUNT;
        }
        blog("getRandomNumberCombinations amountOfNumbers = " + amountOfNumbers);
        for (int i = 1; i < amountOfNumbers + 1; i++)
        {
            int randMax = MAX_INT_COMBO_1;
            if (i == 2)
            {
                randMax = MAX_INT_COMBO_2;
            }
            else if (i == 3)
            {
                randMax = MAX_INT_COMBO_3;
            }
            else if (i == 4)
            {
                randMax = MAX_INT_COMBO_4;
            }
            else if (i == 5)
            {
                randMax = MAX_INT_COMBO_5;
            }
            else if (i == 6)
            {
                randMax = MAX_INT_COMBO_6;
            }
            int tempRandVariable = rand(0, randMax);
            utils.setScriptVar(player, VAR_PREFIX + ".number_combo_" + i + "_" + collectionItem, tempRandVariable);
            int lengthOfVar = (("" + tempRandVariable)).length();
            String asterisks = "";
            for (int j = 0; j < lengthOfVar; j++)
            {
                asterisks += "*";
            }
            utils.setScriptVar(player, VAR_PREFIX + ".number_combo_asterisk_" + i + "_" + collectionItem, asterisks);
        }
        if (utils.hasScriptVar(player, GUESS_POSITION + "_" + collectionItem))
        {
            utils.removeScriptVar(player, GUESS_POSITION + "_" + collectionItem);
        }
        if (utils.hasScriptVar(player, NUMBER_VALUES + "_" + collectionItem))
        {
            utils.removeScriptVarTree(player, NUMBER_VALUES + "_" + collectionItem);
        }
        utils.setScriptVar(player, NUMBER_OF_COMBOS + "_" + collectionItem, amountOfNumbers);
        utils.setScriptVar(player, TOTAL_GUESSES + "_" + collectionItem, TOTAL_GUESS_DEFAULT);
        int thresholdRow = dataTableSearchColumnForInt(amountOfNumbers, "combinations", THRESHOLD_TABLE);
        if (thresholdRow < 0)
        {
            return false;
        }
        dictionary threshDict = dataTableGetRow(THRESHOLD_TABLE, thresholdRow);
        for (int t = 1; t < amountOfNumbers + 1; t++)
        {
            int threshold = threshDict.getInt("threshold" + t);
            utils.setScriptVar(player, VAR_PREFIX + ".number_combo_threshold_" + t + "_" + collectionItem, threshold);
            blog("getRandomNumberCombinations - .number_combo_threshold_" + t);
        }
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
        blog("noBuffAdjustmentNeeded - init");
        boolean hasBuff = false;
        boolean alreadyBuffed = false;
        boolean slicing = false;
        boolean combosExist = false;
        if (buff.hasBuff(player, CONTAINER_PUZZLE_BUFF))
        {
            hasBuff = true;
        }
        if (utils.hasScriptVar(player, BUFF_USED + "_" + collectionItem))
        {
            alreadyBuffed = true;
        }
        if (utils.hasScriptVar(player, CURRENTLY_SLICING + "_" + collectionItem))
        {
            slicing = true;
        }
        if (utils.hasScriptVar(player, NUMBER_OF_COMBOS + "_" + collectionItem))
        {
            combosExist = true;
        }
        blog("rerollPuzzleNeeded - hasBuff: " + hasBuff + " alreadyBuffed: " + alreadyBuffed + " slicing: " + slicing + " combosExist: " + combosExist);
        if (!slicing && !combosExist)
        {
            blog("rerollPuzzleNeeded - player is using this for the first time and has buff. Reroll Needed.");
            return true;
        }
        else if (!slicing && hasBuff && !alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player isn't slicing, has a buff and didn't have a buff in a previous try.");
            return true;
        }
        else if (slicing && alreadyBuffed && hasBuff && combosExist)
        {
            blog("rerollPuzzleNeeded - player is slicing, has a buff and has already been flagged as having a buff for this container. Ignoring. Do Not Reroll.");
            return false;
        }
        else if (slicing && hasBuff && !alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player has used buff while solving puzzle. NOT DISTURBING.");
            sendSystemMessage(player, YOU_BUFFED_LATE);
            return false;
        }
        else if (!slicing && !hasBuff && alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player had a buff at one time but stopped solving puzzle and has come back. Reroll needed.");
            utils.removeScriptVar(player, BUFF_USED + "_" + collectionItem);
            return true;
        }
        blog("rerollPuzzleNeeded - No special conditions found. NOT DISTURBING.");
        return false;
    }
    public boolean calculateThreshold(obj_id collectionItem, obj_id player, int guessPosition) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        int threshold = utils.getIntScriptVar(player, VAR_PREFIX + ".number_combo_threshold_" + guessPosition + "_" + collectionItem);
        int totalGuesses = utils.getIntScriptVar(player, TOTAL_GUESSES + "_" + collectionItem);
        if (threshold < 0 || totalGuesses < 0)
        {
            return false;
        }
        else if (totalGuesses < threshold)
        {
            blog("setting threshold to TRUE");
            utils.setScriptVar(player, VAR_PREFIX + ".under_threshold_" + guessPosition + "_" + collectionItem, true);
        }
        else 
        {
            blog("setting threshold to FALSE");
            utils.setScriptVar(player, VAR_PREFIX + ".under_threshold_" + guessPosition + "_" + collectionItem, false);
        }
        utils.setScriptVar(player, TOTAL_GUESSES + "_" + collectionItem, TOTAL_GUESS_DEFAULT);
        return true;
    }
    public boolean completeNumberGuess(obj_id collectionItem, obj_id player, int guessPosition) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        blog("completeNumberGuess - validation completed");
        int totalNumberOfCombos = utils.getIntScriptVar(player, NUMBER_OF_COMBOS + "_" + collectionItem);
        if (totalNumberOfCombos <= 0)
        {
            return false;
        }
        blog("completeNumberGuess - totalNumberOfCombos: " + totalNumberOfCombos);
        blog("completeNumberGuess - guessPosition: " + guessPosition);
        calculateThreshold(collectionItem, player, guessPosition);
        if (guessPosition < totalNumberOfCombos)
        {
            blog("completeNumberGuess - player still has more combinations to win.");
            utils.setScriptVar(player, GUESS_POSITION + "_" + collectionItem, guessPosition + 1);
            createSui(collectionItem, player);
            return true;
        }
        blog("completeNumberGuess - player completed all guesses.");
        boolean completedAllThresholds = true;
        for (int i = 1; i < totalNumberOfCombos + 1; i++)
        {
            if (!utils.hasScriptVar(player, VAR_PREFIX + ".under_threshold_" + i + "_" + collectionItem))
            {
                blog("completeNumberGuess - player did not have the proper thresholds saved.");
                return false;
            }
            boolean guessThreshold = utils.getBooleanScriptVar(player, VAR_PREFIX + ".under_threshold_" + i + "_" + collectionItem);
            if (!guessThreshold)
            {
                completedAllThresholds = false;
            }
        }
        if (!collection.rewardPlayerCollectionSlot(player, collectionItem))
        {
            blog("rewardPlayer - rewardPlayerCollectionSlot = FAIL!!!");
            closeOldWindow(player);
            removePlayerNonImperativeVars(player);
            removePlayerImperativeVars(player);
            return false;
        }
        blog("rewardPlayer - rewardPlayerCollectionSlot = GIVE LOOT");
        loot.giveMeatlumpPuzzleLoot(player, completedAllThresholds, false);
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
        blog("closeSui");
        if (buff.applyBuff(player, "locked_container_puzzle_downer"))
        {
            
        }
        
        {
            blog("closeSui - downer given to player");
            sendSystemMessage(player, YOU_CANCELED_EARLY);
        }
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
