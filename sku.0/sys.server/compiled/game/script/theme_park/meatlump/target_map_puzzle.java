package script.theme_park.meatlump;

import script.*;
import script.library.*;

public class target_map_puzzle extends script.base_script
{
    public target_map_puzzle()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String VAR_PREFIX = "meatlump_target_map";
    public static final String NONIMPERATIVE_VAR_PREFIX = "meatlump_target_map_nonimperative";
    public static final String PID_NAME = NONIMPERATIVE_VAR_PREFIX + ".pid";
    public static final String CURRENTLY_DECIPHERING = NONIMPERATIVE_VAR_PREFIX + ".slicing";
    public static final String PREFIX_SCRAMBLED = VAR_PREFIX + ".prefix_scrambled";
    public static final String TARGET_SCRAMBLED = VAR_PREFIX + ".target_scrambled";
    public static final String TRAIL_SCRAMBLED = VAR_PREFIX + ".trail_scrambled";
    public static final String PLANET_SCRAMBLED = VAR_PREFIX + ".planet_scrambled";
    public static final String PREFIX = VAR_PREFIX + ".prefix";
    public static final String TARGET = VAR_PREFIX + ".target";
    public static final String TRAIL = VAR_PREFIX + ".trail";
    public static final String PLANET = VAR_PREFIX + ".planet";
    public static final String TEXT_TO_BE_SOLVED = VAR_PREFIX + ".text_to_be_solved";
    public static final String TEXT_TARGET = VAR_PREFIX + ".text_target";
    public static final String TEXT_LOCATION = VAR_PREFIX + ".text_location";
    public static final String TEXT_PLANET = VAR_PREFIX + ".text_planet";
    public static final String HAS_BUFF = NONIMPERATIVE_VAR_PREFIX + ".has_buff";
    public static final String BUFF_USED = VAR_PREFIX + ".buff_used";
    public static final String CAPTION = "Target Map";
    public static final String DEVICE_TEMPLATE = "object/tangible/meatlump/event/slicing_device_meatlump_map.iff";
    public static final String DEVICE_OBJVAR = "puzzle";
    public static final String TARGET_MAP_BUFF = "target_map_puzzle_buff";
    public static final String TEXT_DATATABLE = "datatables/theme_park/meatlump_target_map_text.iff";
    public static final string_id SID_OPEN = new string_id("meatlump/meatlump", "meatlump_decipher_map");
    public static final string_id MAP_INTRO_TEXT = new string_id("meatlump/meatlump", "decipher_map_text");
    public static final string_id YOU_CANCELED_EARLY = new string_id("meatlump/meatlump", "you_canceled_early");
    public static final string_id YOU_HAVE_DEBUFF = new string_id("meatlump/meatlump", "you_have_debuff");
    public static final string_id YOU_NEED_DEVICE = new string_id("meatlump/meatlump", "you_need_device");
    public static final string_id YOU_BUFFED_LATE = new string_id("meatlump/meatlump", "you_buff_late");
    public static final char[][] CIPHER_ARRAY_1 = 
    {
        
        {
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        },
        
        {
            '!',
            '@',
            '#',
            '$',
            '3',
            '^',
            '&',
            '*',
            '(',
            ')',
            '_',
            '+',
            '-',
            '=',
            '[',
            ']',
            '/',
            '|',
            '?',
            '/',
            '>',
            '<',
            '`',
            '~',
            ';',
            '2',
            '!',
            '@',
            '#',
            '$',
            '3',
            '^',
            '&',
            '*',
            '(',
            ')',
            '_',
            '+',
            '-',
            '=',
            '[',
            ']',
            '/',
            '|',
            '?',
            '/',
            '>',
            '<',
            '`',
            '~',
            ';',
            '2'
        }
    };
    public static final char[][] CIPHER_ARRAY_2 = 
    {
        
        {
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        },
        
    {
            '¤',
            'þ',
            '#',
            'ð',
            'ê',
            'f',
            '&',
            '*',
            'î',
            'j',
            '_',
            '¶',
            '-',
            'n',
            'Ø',
            '¿',
            '/',
            '®',
            's',
            '^',
            'ü',
            '<',
            '`',
            '~',
            '¥',
            'z',
            '¤',
            'þ',
            '#',
            'ð',
            'ê',
            'f',
            '&',
            '*',
            'î',
            'j',
            '_',
            '¶',
            '-',
            'n',
            'Ø',
            '¿',
            '/',
            '®',
            's',
            '^',
            'ü',
            '<',
            '`',
            '~',
            '¥',
            'z'
        }
    };
    public static final char[][] CIPHER_ARRAY_3 = 
    {
        
        {
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        },
        
        {
            '4',
            '8',
            '©',
            'd',
            '3',
            'f',
            '6',
            '#',
            '1',
            'j',
            '_',
            '|',
            'm',
            'n',
            '0',
            'Þ',
            'ð',
            '®',
            '5',
            '+',
            'ü',
            '<',
            'w',
            '~',
            '/',
            '2',
            '4',
            '8',
            '©',
            'd',
            '3',
            'f',
            '6',
            '#',
            '1',
            'j',
            '_',
            '|',
            'm',
            'n',
            '0',
            'Þ',
            'ð',
            '®',
            '5',
            '+',
            'ü',
            '<',
            'w',
            '~',
            '/',
            '2'
        }
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id collectionItem = self;
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (!collection.canCollectCollectible(player, self))
        {
            return SCRIPT_CONTINUE;
        }
        else if (buff.hasBuff(player, "target_map_puzzle_downer"))
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
        createMapText(collectionItem, player);
        return SCRIPT_CONTINUE;
    }
    public boolean createMapText(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (!collection.checkState(player))
        {
            return false;
        }
        blog("createMapText");
        if (rerollPuzzleNeeded(collectionItem, player))
        {
            blog("setting up password for slicing session");
            boolean varsSet = getRandomText(collectionItem, player);
            if (!varsSet)
            {
                blog("createMapText - Varset is FALSE!!!!!!!!!!!!!!!!!.");
                return false;
            }
        }
        else 
        {
            closeOldWindow(player);
        }
        blog("createMapText - Setting var on player showing he is slicing.");
        utils.setScriptVar(player, CURRENTLY_DECIPHERING + "_" + collectionItem, true);
        if (!utils.hasScriptVar(player, PREFIX_SCRAMBLED + "_" + collectionItem) || !utils.hasScriptVar(player, TARGET_SCRAMBLED + "_" + collectionItem) || !utils.hasScriptVar(player, TRAIL_SCRAMBLED + "_" + collectionItem) || !utils.hasScriptVar(player, PLANET_SCRAMBLED + "_" + collectionItem) || !utils.hasScriptVar(player, PREFIX + "_" + collectionItem) || !utils.hasScriptVar(player, TARGET + "_" + collectionItem) || !utils.hasScriptVar(player, TRAIL + "_" + collectionItem) || !utils.hasScriptVar(player, PLANET + "_" + collectionItem) || !utils.hasScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem))
        {
            blog("createMapText - failed script var validation");
            return false;
        }
        String textToBeSolved = utils.getStringScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem);
        String prefixScrambled = utils.getStringScriptVar(player, PREFIX_SCRAMBLED + "_" + collectionItem);
        String targetScrambled = utils.getStringScriptVar(player, TARGET_SCRAMBLED + "_" + collectionItem);
        String trailScrambled = utils.getStringScriptVar(player, TRAIL_SCRAMBLED + "_" + collectionItem);
        String planetScrambled = utils.getStringScriptVar(player, PLANET_SCRAMBLED + "_" + collectionItem);
        String questionnaireText = sui.colorWhite() + localize(MAP_INTRO_TEXT);
        questionnaireText += sui.newLine(2);
        questionnaireText += sui.colorGreen() + prefixScrambled + sui.colorWhite() + sui.newLine(2);
        questionnaireText += "Target: ";
        if (textToBeSolved.equals(TEXT_TARGET + "_" + collectionItem))
        {
            questionnaireText += sui.colorRed();
        }
        questionnaireText += targetScrambled + sui.colorWhite() + sui.newLine(2);
        questionnaireText += "Target Location: ";
        if (textToBeSolved.equals(TEXT_LOCATION + "_" + collectionItem))
        {
            questionnaireText += sui.colorRed();
        }
        questionnaireText += trailScrambled + sui.colorWhite() + sui.newLine(2);
        questionnaireText += "Target Planet: ";
        if (textToBeSolved.equals(TEXT_PLANET + "_" + collectionItem))
        {
            questionnaireText += sui.colorRed();
        }
        questionnaireText += planetScrambled + sui.colorWhite() + sui.newLine(2);
        questionnaireText += "Solve the " + sui.colorRed() + "RED" + sui.colorWhite() + " text.";
        dictionary params = new dictionary();
        int pid = createSUIPage("/Script.questionnaire", collectionItem, player);
        setSUIAssociatedLocation(pid, collectionItem);
        setSUIMaxRangeToObject(pid, 8);
        params.put("callingPid", pid);
        sui.setPid(player, pid, PID_NAME);
        setSUIProperty(pid, "Prompt.lblPrompt", "LocalText", questionnaireText);
        setSUIProperty(pid, "bg.caption.lblTitle", "Text", CAPTION);
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
        if (!utils.hasScriptVar(player, PREFIX_SCRAMBLED + "_" + self) || !utils.hasScriptVar(player, TARGET_SCRAMBLED + "_" + self) || !utils.hasScriptVar(player, TRAIL_SCRAMBLED + "_" + self) || !utils.hasScriptVar(player, PLANET_SCRAMBLED + "_" + self) || !utils.hasScriptVar(player, PREFIX + "_" + self) || !utils.hasScriptVar(player, TARGET + "_" + self) || !utils.hasScriptVar(player, TRAIL + "_" + self) || !utils.hasScriptVar(player, PLANET + "_" + self) || !utils.hasScriptVar(player, TEXT_TO_BE_SOLVED + "_" + self))
        {
            blog("createMapText - failed script var validation");
            return SCRIPT_CONTINUE;
        }
        String textToBeSolved = utils.getStringScriptVar(player, TEXT_TO_BE_SOLVED + "_" + self);
        String correctPhrase = "";
        if (textToBeSolved.equals(TEXT_TARGET + "_" + self))
        {
            correctPhrase = utils.getStringScriptVar(player, TARGET + "_" + self);
        }
        else if (textToBeSolved.equals(TEXT_LOCATION + "_" + self))
        {
            correctPhrase = utils.getStringScriptVar(player, TRAIL + "_" + self);
        }
        else if (textToBeSolved.equals(TEXT_PLANET + "_" + self))
        {
            correctPhrase = utils.getStringScriptVar(player, PLANET + "_" + self);
        }
        playerGuess = toLower(playerGuess);
        correctPhrase = toLower(correctPhrase);
        if (playerGuess.equals(correctPhrase))
        {
            blog("handleDialogInput - Password Solved!! Rewardign Player");
            rewardPlayer(self, player);
            return SCRIPT_CONTINUE;
        }
        createMapText(self, player);
        return SCRIPT_CONTINUE;
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
        boolean mapExists = false;
        if (buff.hasBuff(player, TARGET_MAP_BUFF))
        {
            hasBuff = true;
        }
        if (utils.hasScriptVar(player, BUFF_USED + "_" + collectionItem))
        {
            alreadyBuffed = true;
        }
        if (utils.hasScriptVar(player, CURRENTLY_DECIPHERING + "_" + collectionItem))
        {
            slicing = true;
        }
        if (utils.hasScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem))
        {
            mapExists = true;
        }
        blog("rerollPuzzleNeeded - hasBuff: " + hasBuff + " alreadyBuffed: " + alreadyBuffed + " slicing: " + slicing + " mapExists: " + mapExists);
        if (!slicing && !mapExists)
        {
            blog("rerollPuzzleNeeded - player is using this for the first time and has buff. Reroll Needed.");
            return true;
        }
        else if (!slicing && hasBuff && !alreadyBuffed)
        {
            blog("rerollPuzzleNeeded - player isn't slicing, has a buff and didn't have a buff in a previous try.");
            return true;
        }
        else if (slicing && alreadyBuffed && hasBuff && mapExists)
        {
            blog("rerollPuzzleNeeded - player is slicing, has a buff and has already been flagged as having a buff for this map and has the map text. NO REROLL.");
            return false;
        }
        else if (slicing && alreadyBuffed && hasBuff && !mapExists)
        {
            blog("rerollPuzzleNeeded - player is slicing, has a buff and has already been flagged as having a buff for this map but has no map text. REROLL.");
            return true;
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
    public boolean getRandomText(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        int datatableLength = dataTableGetNumRows(TEXT_DATATABLE);
        int randomNum = rand(0, datatableLength - 1);
        if (randomNum < 0)
        {
            return false;
        }
        String targetString = (dataTableGetString(TEXT_DATATABLE, randomNum, "target_text")).trim();
        String prefixString = (dataTableGetString(TEXT_DATATABLE, randomNum, "prefix_text")).trim();
        String trailingString = (dataTableGetString(TEXT_DATATABLE, randomNum, "trailing_text")).trim();
        String planetString = (dataTableGetString(TEXT_DATATABLE, randomNum, "planet_text")).trim();
        blog("targetString: " + targetString);
        blog("prefixString: " + prefixString);
        blog("trailingString: " + trailingString);
        blog("planetString: " + planetString);
        if (targetString == null || targetString.equals("") || prefixString == null || prefixString.equals("") || trailingString == null || trailingString.equals(""))
        {
            return false;
        }
        int targetLength = targetString.length();
        if (targetLength < 0)
        {
            return false;
        }
        boolean hasBuff = false;
        if (buff.hasBuff(player, TARGET_MAP_BUFF))
        {
            hasBuff = true;
            utils.setScriptVar(player, BUFF_USED + "_" + collectionItem, true);
        }
        blog("HASBUFF: " + hasBuff);
        char targetChar[] = targetString.toCharArray();
        int arrayInt = rand(1, 2);
        if (hasBuff)
        {
            arrayInt = 3;
        }
        char[][] copiedArray = new char[2][52];
        switch (arrayInt)
        {
            case 1:
            for (int a = 0; a < CIPHER_ARRAY_1.length; a++)
            {
                System.arraycopy(CIPHER_ARRAY_1[a], 0, copiedArray[a], 0, CIPHER_ARRAY_1[a].length);
            }
            break;
            case 2:
            for (int a = 0; a < CIPHER_ARRAY_2.length; a++)
            {
                System.arraycopy(CIPHER_ARRAY_2[a], 0, copiedArray[a], 0, CIPHER_ARRAY_2[a].length);
            }
            break;
            case 3:
            for (int a = 0; a < CIPHER_ARRAY_3.length; a++)
            {
                System.arraycopy(CIPHER_ARRAY_3[a], 0, copiedArray[a], 0, CIPHER_ARRAY_3[a].length);
            }
            break;
            default:
            for (int a = 0; a < CIPHER_ARRAY_1.length; a++)
            {
                System.arraycopy(CIPHER_ARRAY_1[a], 0, copiedArray[a], 0, CIPHER_ARRAY_1[a].length);
            }
        }
        if (copiedArray == null)
        {
            blog("array copy must have failed ");
            return false;
        }
        String listOfChars = "";
        for (int t = 0; t < targetChar.length; t++)
        {
            if (targetChar[t] == ' ')
            {
                listOfChars += " ";
                continue;
            }
            for (int i = 0; i < copiedArray[0].length; i++)
            {
                if (targetChar[t] != copiedArray[0][i])
                {
                    continue;
                }
                else 
                {
                    listOfChars += "" + copiedArray[1][i];
                }
            }
        }
        blog("listOfChars: " + listOfChars);
        blog("listOfChars length: " + listOfChars.length());
        char cipherChar[] = listOfChars.toCharArray();
        blog("targetChar length: " + targetChar.length);
        blog("cipherChar length: " + cipherChar.length);
        if (cipherChar.length != targetChar.length)
        {
            blog("length of cipherChar.length & targetChar.length are not equal.");
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "QA Message: failed to convert the target: " + targetString);
            }
            return false;
        }
        String newTarget = targetString;
        for (int i = 0; i < copiedArray[0].length; i++)
        {
            newTarget = newTarget.replace(copiedArray[0][i], copiedArray[1][i]);
        }
        blog("newTarget: " + newTarget);
        String newPrefix = prefixString;
        for (int s = 0; s < targetLength; s++)
        {
            newPrefix = newPrefix.replace(targetChar[s], cipherChar[s]);
        }
        String newTrail = trailingString;
        for (int s = 0; s < targetLength; s++)
        {
            newTrail = newTrail.replace(targetChar[s], cipherChar[s]);
        }
        String newPlanet = planetString;
        for (int s = 0; s < targetLength; s++)
        {
            newPlanet = newPlanet.replace(targetChar[s], cipherChar[s]);
        }
        int selectPuzzle = 1;
        if (hasBuff)
        {
            selectPuzzle = rand(2, 3);
        }
        blog("selectPuzzle: " + selectPuzzle);
        switch (selectPuzzle)
        {
            case 1:
            utils.setScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem, TEXT_TARGET + "_" + collectionItem);
            break;
            case 2:
            utils.setScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem, TEXT_LOCATION + "_" + collectionItem);
            break;
            case 3:
            utils.setScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem, TEXT_PLANET + "_" + collectionItem);
            break;
            default:
            utils.setScriptVar(player, TEXT_TO_BE_SOLVED + "_" + collectionItem, TEXT_TARGET + "_" + collectionItem);
        }
        blog("setting script var newPrefix: " + newPrefix);
        blog("setting script var newTarget: " + newTarget);
        blog("setting script var newTrail: " + newTrail);
        blog("setting script var newPlanet: " + newPlanet);
        utils.setScriptVar(player, PREFIX_SCRAMBLED + "_" + collectionItem, newPrefix);
        utils.setScriptVar(player, TARGET_SCRAMBLED + "_" + collectionItem, newTarget);
        utils.setScriptVar(player, TRAIL_SCRAMBLED + "_" + collectionItem, newTrail);
        utils.setScriptVar(player, PLANET_SCRAMBLED + "_" + collectionItem, newPlanet);
        utils.setScriptVar(player, PREFIX + "_" + collectionItem, prefixString);
        utils.setScriptVar(player, TARGET + "_" + collectionItem, targetString);
        utils.setScriptVar(player, TRAIL + "_" + collectionItem, trailingString);
        utils.setScriptVar(player, PLANET + "_" + collectionItem, planetString);
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
            removePlayerImperativeVars(player);
            return false;
        }
        blog("rewardPlayer - rewardPlayerCollectionSlot = SUCCESS");
        closeOldWindow(player);
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
        blog("closeSui - init");
        if (buff.applyBuff(player, "target_map_puzzle_downer"))
        {
            
        }
        
        {
            sendSystemMessage(player, YOU_CANCELED_EARLY);
        }
        closeOldWindow(player);
        blog("closeSui - removing removePlayerNonImperativeVars.");
        removePlayerNonImperativeVars(player);
        return SCRIPT_CONTINUE;
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
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
