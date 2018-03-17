package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.colors;
import script.library.combat;
import script.library.create;
import script.library.groundquests;
import script.library.hue;
import script.library.money;
import script.library.pet_lib;
import script.library.prose;
import script.library.quests;
import script.library.sui;
import script.library.utils;

public class esebesta_test extends script.base_script
{
    public esebesta_test()
    {
    }
    public static final String PROP_TEXT = "Text";
    public static final String SUI_TRANSFER = "Script.transfer";
    public static final String TRANSFER_PAGE_PROMPT = "Prompt";
    public static final String TRANSFER_PAGE_CAPTION = "bg.caption";
    public static final String TRANSFER_PAGE_TRANSACTION = "transaction";
    public static final String TRANSFER_BTN_OK = "btnOk";
    public static final String TRANSFER_BTN_CANCEL = "btnCancel";
    public static final String TRANSFER_BTN_REVERT = "btnRevert";
    public static final String TRANSFER_TITLE = TRANSFER_PAGE_CAPTION + ".lblTitle";
    public static final String TRANSFER_PROMPT = TRANSFER_PAGE_PROMPT + ".lblPrompt";
    public static final String TRANSFER_INPUT_FROM = TRANSFER_PAGE_TRANSACTION + ".txtInputFrom";
    public static final String TRANSFER_INPUT_TO = TRANSFER_PAGE_TRANSACTION + ".txtInputTo";
    public static final String TRANSFER_FROM = TRANSFER_PAGE_TRANSACTION + ".lblStartingFrom";
    public static final String TRANSFER_TO = TRANSFER_PAGE_TRANSACTION + ".lblStartingTo";
    public static final String TRANSFER_FROM_TEXT = TRANSFER_PAGE_TRANSACTION + ".lblFrom";
    public static final String TRANSFER_TO_TEXT = TRANSFER_PAGE_TRANSACTION + ".lblTo";
    public static final String TRANSFER_SLIDER = TRANSFER_PAGE_TRANSACTION + ".slider";
    public static final String TRANSFER_INPUT_FROM_TEXT = TRANSFER_INPUT_FROM + "." + PROP_TEXT;
    public static final String TRANSFER_INPUT_TO_TEXT = TRANSFER_INPUT_TO + "." + PROP_TEXT;
    public void colorize(obj_id player, obj_id target, String customizationVar) throws InterruptedException
    {
        int pId = createSUIPage("Script.ColorPicker", player, player, "ColorizeCallback");
        setSUIProperty(pId, "ColorPicker", "TargetNetworkId", target.toString());
        setSUIProperty(pId, "ColorPicker", "TargetVariable", customizationVar);
        setSUIProperty(pId, "ColorPicker", "TargetRangeMin", "0");
        setSUIProperty(pId, "ColorPicker", "TargetRangeMax", "500");
        subscribeToSUIProperty(pId, "ColorPicker", "SelectedIndex");
        setSUIAssociatedObject(pId, target);
        showSUIPage(pId);
    }
    public void transfer(obj_id player, int from, int to, float conversionRatio) throws InterruptedException
    {
        int pid = createSUIPage("Script.Transfer", player, player, "TransferCallback");
        setSUIProperty(pid, "Transaction", "ConversionRatio", Float.toString(conversionRatio));
        setSUIProperty(pid, TRANSFER_FROM, PROP_TEXT, Integer.toString(from));
        setSUIProperty(pid, TRANSFER_TO, PROP_TEXT, Integer.toString(to));
        setSUIProperty(pid, TRANSFER_INPUT_FROM, PROP_TEXT, Integer.toString(from));
        setSUIProperty(pid, TRANSFER_INPUT_TO, PROP_TEXT, Integer.toString(to));
        subscribeToSUIProperty(pid, TRANSFER_INPUT_FROM, PROP_TEXT);
        subscribeToSUIProperty(pid, TRANSFER_INPUT_TO, PROP_TEXT);
        showSUIPage(pid);
    }
    public void keypad(obj_id player) throws InterruptedException
    {
        int pid = createSUIPage("Script.Keypad", player, player, "KeypadCallback");
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        setSUIProperty(pid, "buttonSlice", "enabled", "false");
        showSUIPage(pid);
    }
    public int progressBar(obj_id player) throws InterruptedException
    {
        int pid = createSUIPage("Script.ProgressBar", player, player, "ProgressBarCallback");
        setSUIProperty(pid, "comp.pText.text", PROP_TEXT, "Doing something...");
        showSUIPage(pid);
        return pid;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("tauntme"))
        {
            prose_package pp = new prose_package();
            pp.stringId = new string_id("ui", "test_pp");
            pp.actor.set(self);
            pp.target.set(self);
            pp.other.set("other_here");
            pp.digitInteger = 666;
            pp.digitFloat = 0.333f;
            commPlayer(self, speaker, pp, "object/mobile/dressed_imperial_trainer_space_01.iff");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            debugConsoleMsg(self, "esebesta_test OnSpeaking: " + text);
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                debugConsoleMsg(self, "command is: " + command);
                if (command.equals("eas_harvest"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String harvesterIdString = tok.nextToken();
                        obj_id harvester = obj_id.getObjId(java.lang.Long.parseLong(harvesterIdString));
                        activateHarvesterExtractionPage(self, harvester);
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Not enough arguments: eas_harvest <harvester_id>");
                    }
                }
                else if (command.equals("eas_wphere"))
                {
                    debugConsoleMsg(self, "hit eas_wphere");
                    obj_id wp = createWaypointInDatapad(self, getLocation(self));
                    _setWaypointColorNative(wp, "space");
                }
                else if (command.equals("eas_setShipWingName"))
                {
                    debugConsoleMsg(self, "hit eas_setShipWingName");
                    obj_id ship = getLookAtTarget(self);
                    setShipWingName(ship, "test_wing");
                }
                else if (command.equals("eas_giveTestQuest"))
                {
                    debugConsoleMsg(self, "hit eas_giveTestQuest");
                    int questId = questGetQuestId("quest/kill_3_womprats");
                    questActivateQuest(questId, self, self);
                }
                else if (command.equals("eas_clearTestQuest"))
                {
                    debugConsoleMsg(self, "hit eas_clearTestQuest");
                    int questId = questGetQuestId("quest/kill_3_womprats");
                    questClearQuest(questId, self);
                }
                else if (command.equals("eas_giveTestQuest2"))
                {
                    debugConsoleMsg(self, "hit eas_giveTestQuest2");
                    int questId = questGetQuestId("quest/kill_3_valarian_assassins");
                    questActivateQuest(questId, self, self);
                }
                else if (command.equals("eas_clearTestQuest2"))
                {
                    debugConsoleMsg(self, "hit eas_clearTestQuest2");
                    int questId = questGetQuestId("quest/kill_3_valarian_assassins");
                    questClearQuest(questId, self);
                }
                else if (command.equals("eas_giveQuest"))
                {
                    debugConsoleMsg(self, "hit eas_giveQuest");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        int result = questActivateQuest(questId, self, self);
                        if (result == 1)
                        {
                            String s = "Error: Quest already active.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 2)
                        {
                            String s = "Error: No such quest.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 3)
                        {
                            String s = "Error: No such task.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 4)
                        {
                            String s = "Error: Quest already completed, and not repeatable.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 5)
                        {
                            String s = "Error: Failed prerequisites.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 6)
                        {
                            String s = "Error: Failed exclusions.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 7)
                        {
                            String s = "Error: Quest Not active.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 8)
                        {
                            String s = "Error: Task not active.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 9)
                        {
                            String s = "Error: No such player.";
                            debugSpeakMsg(self, s);
                        }
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: eas_giveQuest <questname>. i.e. \"eas_giveQuest quest/loot_5_widgets\"");
                        debugSpeakMsg(self, "Usage: eas_giveQuest <questname>. i.e. \"eas_giveQuest quest/loot_5_widgets\"");
                    }
                }
                else if (command.equals("eas_clearQuest"))
                {
                    debugConsoleMsg(self, "hit eas_clearQuest");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        questClearQuest(questId, self);
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: eas_clearQuest <questname>. i.e. \"eas_giveQuest quest/loot_5_widgets\"");
                        debugSpeakMsg(self, "Usage: eas_clearQuest <questname>. i.e. \"eas_giveQuest quest/loot_5_widgets\"");
                    }
                }
                else if (command.equals("eas_completeTask"))
                {
                    debugConsoleMsg(self, "hit eas_completeTask");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        int taskId = utils.stringToInt(tok.nextToken());
                        questCompleteTask(questId, taskId, self);
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: eas_completeTask <questname> <taskid>. i.e. \"eas_completeTask quest/loot_5_widgets 0\"");
                        debugSpeakMsg(self, "Usage: eas_completeTask <questname> <taskid>. i.e. \"eas_completeTask quest/loot_5_widgets 0\"");
                    }
                }
                else if (command.equals("eas_emitSignal"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String signal = tok.nextToken();
                        groundquests.sendSignal(self, signal);
                    }
                }
                else if (command.equals("eas_testWaitForSignal"))
                {
                    debugConsoleMsg(self, "hit eas_testWaitForSignal");
                    groundquests.sendSignal(self, "testsignal");
                }
                else if (command.equals("eas_testTaskNames"))
                {
                    debugConsoleMsg(self, "hit eas_testTaskNames");
                    String questName = "quest/test_encounter";
                    if (groundquests.isValidQuestName(questName))
                    {
                        debugConsoleMsg(self, "valid quest name");
                    }
                    int questCrc = questGetQuestId(questName);
                    int taskId = groundquests.getTaskId(questCrc, "killed_them_rats");
                    debugConsoleMsg(self, "questCrc=[" + questCrc + "] taskId=[" + taskId + "]");
                    if (questIsTaskComplete(questCrc, taskId, self))
                    {
                        groundquests.sendSignal(self, "testSignal");
                        debugConsoleMsg(self, "sending signal");
                    }
                    else 
                    {
                        debugConsoleMsg(self, "not sending signal");
                    }
                }
                else if (command.equals("eas_testRandomLocation"))
                {
                    for (int i = 0; i < 50; ++i)
                    {
                        location l = groundquests.getRandom2DLocationAroundPlayer(self, 3, 5);
                        obj_id object = create.object("object/tangible/gravestone/gravestone01.iff", l);
                    }
                }
                else if (command.equals("eas_setPendingEscortTarget"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String targetString = tok.nextToken();
                        obj_id target = obj_id.getObjId(java.lang.Long.parseLong(targetString));
                        if (!groundquests.isEscortTargetReadyForStaticEscortTask(target))
                        {
                            debugConsoleMsg(self, "target busy");
                        }
                        else 
                        {
                            groundquests.setPendingStaticEscortTarget(self, target);
                            debugConsoleMsg(self, "target target set");
                        }
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Usage: eas_setPendingEscortTarget <targetid>");
                    }
                }
                else if (command.equals("eas_prose1"))
                {
                    debugSpeakMsg(self, "hit eas_prose1");
                    obj_id target = getLookAtTarget(self);
                    prose_package pp = prose.getPackage(pet_lib.SID_SYS_CANT_CALL_YET, 5);
                    showFlyText(target, pp, 1.0f, 255, 0, 0);
                    prose_package pp2 = prose.getPackage(pet_lib.SID_SYS_CANT_CALL_YET, 10);
                    showCombatText(target, self, pp2, 1.0f, 0, 0, 255);
                }
                else if (command.equals("eas_requestActivateQuest"))
                {
                    debugSpeakMsg(self, "hit eas_requestActivateQuest");
                    int questId = questGetQuestId("quest/ep3_gursan_slay_hsskas");
                    requestActivateQuest(questId, self, self);
                }
                else if (command.equals("eas_requestCompleteQuest"))
                {
                    debugSpeakMsg(self, "hit eas_requestCompleteQuest");
                    int questId = questGetQuestId("quest/ep3_gursan_slay_hsskas");
                    requestCompleteQuest(questId, self);
                }
                else if (command.equals("eas_cancelQuest"))
                {
                    debugSpeakMsg(self, "hit eas_cancelQuest");
                    int questId = questGetQuestId("quest/ep3_gursan_slay_hsskas");
                    questClearQuest(questId, self);
                }
                else if (command.equals("eas_showroadmap"))
                {
                    debugSpeakMsg(self, "hit eas_showroadmap");
                    newbieTutorialHighlightUIElement(self, "/GroundHUD.RoadMap", 7.0f);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPermissionListModify(obj_id self, obj_id actor, String playerName, String list, String action) throws InterruptedException
    {
        if (isGod(self))
        {
            debugConsoleMsg(self, "OnPermissionListModify called with: " + playerName + ", " + list + ", " + action);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestCompleted(obj_id self, int questCrc) throws InterruptedException
    {
        if (isGod(self))
        {
            debugSpeakMsg(self, "OnQuestCompleted called with: " + questCrc);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questCrc) throws InterruptedException
    {
        if (isGod(self))
        {
            debugSpeakMsg(self, "OnQuestActivated called with: " + questCrc);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSurveyDataReceived(obj_id self, float[] xVals, float[] zVals, float[] efficiencies) throws InterruptedException
    {
        if (isGod(self))
        {
            if (xVals == null)
            {
                debugConsoleMsg(self, "xVals NULL");
                return SCRIPT_CONTINUE;
            }
            if (zVals == null)
            {
                debugConsoleMsg(self, "zVals NULL");
                return SCRIPT_CONTINUE;
            }
            if (efficiencies == null)
            {
                debugConsoleMsg(self, "efficiencies NULL");
                return SCRIPT_CONTINUE;
            }
            if (xVals.length != zVals.length || zVals.length != efficiencies.length)
            {
                debugConsoleMsg(self, "vectors vary in size, bad");
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < xVals.length; ++i)
            {
                debugConsoleMsg(self, "point:" + i + " x: " + xVals[i] + " z: " + zVals[i] + " eff: " + efficiencies[i]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int TestMessage(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "TestMessage messageHandler hit");
        return SCRIPT_CONTINUE;
    }
    public int ColorizeCallback(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int TransferCallback(obj_id self, dictionary params) throws InterruptedException
    {
        final int to = utils.stringToInt(params.getString(TRANSFER_INPUT_TO + "." + PROP_TEXT));
        final int from = utils.stringToInt(params.getString(TRANSFER_INPUT_FROM + "." + PROP_TEXT));
        debugSpeakMsg(self, "Transfer result: from:" + from + ", to:" + to);
        return SCRIPT_CONTINUE;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
        final int result = utils.stringToInt(params.getString("result.numberBox" + "." + "localtext"));
        final String button = params.getString("buttonEnter.ButtonPressed");
        debugSpeakMsg(self, "Keypad result: " + result + "\nButton Pressed: " + button);
        return SCRIPT_CONTINUE;
    }
    public int ProgressBarCallback(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "ProgressBarCallback");
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int componentSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        string_id strFlyTextTarget = new string_id("ship_test", "washit_target");
        color colFlyText = colors.VIOLET;
        showFlyText(self, strFlyTextTarget, 1.0f, colFlyText);
        LOG("esebesta-debug", self + " WAS HIT by " + attacker + " in slot [" + ship_chassis_slot_type.getNameByType(componentSlot) + "]");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.esebesta_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFormCreateObject(obj_id self, String templateName, float xLoc, float yLoc, float zLoc, obj_id cell, String[] keys, String[] values) throws InterruptedException
    {
        debugConsoleMsg(self, "OnFormCreateObject hit");
        debugConsoleMsg(self, "Template: " + templateName + " Loc: (" + xLoc + "," + yLoc + "," + zLoc + ")");
        debugConsoleMsg(self, "Form data:");
        for (int i = 0; i < keys.length && i < values.length; ++i)
        {
            String key = keys[i];
            String value = values[i];
            debugConsoleMsg(self, "[" + key + "]" + value);
        }
        location loc = new location(xLoc, yLoc, zLoc, "tatooine");
        
        obj_id object = createObject(templateName, loc);
        debugConsoleMsg(self, "New object is ID:" + object);
        return SCRIPT_CONTINUE;
    }
    public int OnFormEditObject(obj_id self, obj_id editObject, String[] keys, String[] values) throws InterruptedException
    {
        debugConsoleMsg(self, "OnFormEditObject hit");
        debugConsoleMsg(self, "Object: " + editObject);
        debugConsoleMsg(self, "Form data:");
        for (int i = 0; i < keys.length && i < values.length; ++i)
        {
            String key = keys[i];
            String value = values[i];
            debugConsoleMsg(self, "[" + key + "]" + value);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFormRequestEditObject(obj_id self, obj_id objectToEdit, String[] keys) throws InterruptedException
    {
        debugConsoleMsg(self, "OnFormRequestEditObject hit");
        String[] ourKeys = 
        {
            "spawns",
            "minSpawnDistance",
            "maxSpawnDistance",
            "minSpawnTime",
            "maxSpawnTime",
            "spawnCount",
            "minLoiterDistance",
            "maxLoiterDistance"
        };
        String[] ourValues = 
        {
            "xwing,ywing,bwing",
            "0",
            "50",
            "1",
            "5",
            "8",
            "5",
            "15"
        };
        boolean result = editFormData(self, objectToEdit, ourKeys, ourValues);
        if (result)
        {
            debugConsoleMsg(self, "edit success");
        }
        else 
        {
            debugConsoleMsg(self, "edit failure");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDroppedItemOntoShipComponent(obj_id self, int slot, obj_id droppedItem, obj_id dropper) throws InterruptedException
    {
        debugServerConsoleMsg(self, "OnDroppedItemOntoShipComponent hit");
        debugServerConsoleMsg(self, "params: slot[" + slot + "] droppedItem[" + droppedItem + "] dropper[" + dropper + "]");
        debugSpeakMsg(self, "OnDroppedItemOntoShipComponent hit");
        debugSpeakMsg(self, "params: slot[" + slot + "] droppedItem[" + droppedItem + "] dropper[" + dropper + "]");
        return SCRIPT_CONTINUE;
    }
    public int OnTryToEquipDroidControlDeviceInShip(obj_id self, obj_id actor, obj_id droidControlDevice) throws InterruptedException
    {
        associateDroidControlDeviceWithShip(self, droidControlDevice);
        return SCRIPT_CONTINUE;
    }
    public int OnCommitDroidProgramCommands(obj_id self, obj_id droidControlDevice, String[] commands, obj_id[] chipsToAdd, obj_id[] chipsToRemove) throws InterruptedException
    {
        debugSpeakMsg(self, "OnCommitDroidProgramCommands hit");
        debugSpeakMsg(self, "params: droidControlDevice[" + droidControlDevice + "]\n");
        obj_id objDataPad = utils.getDatapad(droidControlDevice);
        debugSpeakMsg(self, "commands:");
        for (int i = 0; i < commands.length; ++i)
        {
            String command = commands[i];
            debugSpeakMsg(self, "[" + command + "]");
        }
        debugSpeakMsg(self, "\nchipsToAdd:");
        for (int i = 0; i < chipsToAdd.length; ++i)
        {
            obj_id chip = chipsToAdd[i];
            debugSpeakMsg(self, "[" + chip + "]");
        }
        debugSpeakMsg(self, "\nchipsToRemove:");
        for (int i = 0; i < chipsToRemove.length; ++i)
        {
            obj_id chip = chipsToRemove[i];
            debugSpeakMsg(self, "[" + chip + "]");
        }
        return SCRIPT_CONTINUE;
    }
}
