package script.theme_park.meatlump;

import script.*;
import script.library.collection;
import script.library.stealth;
import script.library.sui;
import script.library.utils;

public class destroy_weapon_cachev2 extends script.base_script
{
    public destroy_weapon_cachev2()
    {
    }
    public static final String VAR_PREFIX = "meatlump_weapon_cache";
    public static final String PID_NAME = VAR_PREFIX + ".pid";
    public static final String CURRENTLY_CALIBRATION = VAR_PREFIX + ".calibration";
    public static final String CALIBRATION_GOAL = VAR_PREFIX + ".goal";
    public static final String CALIBRATION_CURRENT = VAR_PREFIX + ".current";
    public static final String CALIBRATION_TRIES = VAR_PREFIX + ".tries";
    public static final String CALIBRATION_MAX_TRIES = VAR_PREFIX + ".max_tries";
    public static final String STATUS_VAR = VAR_PREFIX + ".status";
    public static final String COMPONENT_VAR = VAR_PREFIX + ".component";
    public static final String CAPTION = "Calibrate Power Cell Abatement";
    public static final String OBJVAR_SLOT_NAME = "collection.slotName";
    public static final string_id SID_OPEN = new string_id("meatlump/meatlump", "weapon_calibration_use");
    public static final String CALIBRATION_ATTEMPTS_REMAINING = "@meatlump/meatlump:weapon_calibration_attempts_remaining";
    public static final String CALIBRATION_SUCCESS = "@meatlump/meatlump:weapon_calibration_success";
    public static final String CALIBRATION_FAILURE = "@meatlump/meatlump:weapon_calibration_failure";
    public static final String CALIBRATION_DESCRIPTION = "@meatlump/meatlump:weapon_calibration_description";
    public static final string_id SID_ALREADY_FINISHED_COLLECTION = new string_id("collection", "already_finished_collection");
    public static final string_id SID_NOT_CLOSE_ENOUGH = new string_id("collection", "not_close_enough");
    public static final string_id SID_ALREADY_HAVE_SLOT = new string_id("collection", "already_have_slot");
    public static final string_id SID_REPORT_CONSUME_ITEM_FAIL = new string_id("collection", "report_consume_item_fail");
    public static final string_id MSG_CALIBRATION_ABORTED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_calibration_aborted");
    public static final int MAX_RANGE_TO_COLLECT = 3;
    public static final int DEFAULT_TRIES = 10;
    public static final String[] CONFIG_PLAYER_BUTTONS = 
    {
        "top.triangles.player.right.1",
        "top.triangles.player.right.2",
        "top.triangles.player.right.3",
        "top.triangles.player.left.2",
        "top.triangles.player.left.3",
        "top.triangles.player.left.1"
    };
    public static final String[] CONFIG_SERVER_BUTTONS = 
    {
        "top.triangles.server.right.1",
        "top.triangles.server.right.2",
        "top.triangles.server.right.3",
        "top.triangles.server.left.2",
        "top.triangles.server.left.3",
        "top.triangles.server.left.1"
    };
    public static final int[] DEFAULT_GOAL_CURRENT_ARRAY = 
    {
        0,
        0,
        0,
        0,
        0,
        0
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id collectionItem = self;
        if (!hasObjVar(collectionItem, OBJVAR_SLOT_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        String baseSlotName = getStringObjVar(collectionItem, OBJVAR_SLOT_NAME);
        String[] splitSlotNames = split(baseSlotName, ':');
        String slotName = splitSlotNames[1];
        String collectionName = splitSlotNames[0];
        if (!hasCompletedCollectionSlotPrereq(player, slotName))
        {
            sendSystemMessage(player, collection.SID_NEED_TO_ACTIVATE_COLLECTION);
            return SCRIPT_CONTINUE;
        }
        else if (hasCompletedCollection(player, collectionName))
        {
            sendSystemMessage(player, SID_ALREADY_FINISHED_COLLECTION);
            return SCRIPT_CONTINUE;
        }
        else if ((slotName == null || slotName.equals("")) || hasCompletedCollectionSlot(player, slotName))
        {
            sendSystemMessage(player, SID_ALREADY_HAVE_SLOT);
            return SCRIPT_CONTINUE;
        }
        closeOldWindow(player);
        blog("OnObjectMenuSelect");
        createConfigUI(collectionItem, player);
        return SCRIPT_CONTINUE;
    }
    public boolean createConfigUI(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        blog("createConfigUI - INIT");
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        else if (!exists(collectionItem) || !exists(player))
        {
            return false;
        }
        dictionary params = new dictionary();
        int pid = createSUIPage("/Script.calibration.game4", collectionItem, player);
        sui.setPid(player, pid, PID_NAME);
        setSUIAssociatedLocation(pid, collectionItem);
        setSUIMaxRangeToObject(pid, 8);
        params.put("callingPid", pid);
        blog("createConfigUI - sending to initializeCalibration");
        if (!initializeCalibration(collectionItem, player))
        {
            return false;
        }
        setSUIProperty(pid, "bg.caption.lbltitle", "Text", CAPTION);
        setSUIProperty(pid, "top.description.desc", "Text", CALIBRATION_DESCRIPTION);
        setSUIProperty(pid, "top.description.attempts", "Text", CALIBRATION_ATTEMPTS_REMAINING + " 100%");
        int[] goal = utils.getIntArrayScriptVar(player, CALIBRATION_GOAL);
        if (goal == null)
        {
            return false;
        }
        blog("createConfigUI - goal.length: " + goal.length);
        for (int i = 0; i < goal.length; i++)
        {
            if (goal[i] == 1)
            {
                setSUIProperty(pid, CONFIG_SERVER_BUTTONS[i], "Color", "#000000");
            }
            setSUIProperty(pid, CONFIG_SERVER_BUTTONS[i], "IsCancelButton", "false");
            setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "IsCancelButton", "false");
        }
        setSUIProperty(pid, "bg.mmc.close", "IsCancelButton", "true");
        for (String configPlayerButton : CONFIG_PLAYER_BUTTONS) {
            subscribeToSUIEvent(pid, sui_event_type.SET_onButton, configPlayerButton, "configProcessorPuzzleCallback");
        }
        setSUIAssociatedObject(pid, player);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        return true;
    }
    public boolean initializeCalibration(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        blog("initializeCalibration - INIT");
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        blog("initializeCalibration - creating stuff");
        int[] goal = DEFAULT_GOAL_CURRENT_ARRAY;
        int[] current = DEFAULT_GOAL_CURRENT_ARRAY;
        int lastr = -1;
        int r = -1;
        int tries = DEFAULT_TRIES;
        boolean mixed = false;
        while (!mixed)
        {
            for (int h = 0; h < 6; h++)
            {
                goal[h] = 0;
            }
            for (int i = 0; i < 6; i++)
            {
                do
                {
                    r = rand(0, 5);
                } while (r == lastr);
                lastr = r;
                goal = toggleButton(goal, r);
            }
            for (int j = 0; j < 6; j++)
            {
                if (goal[j] != current[j])
                {
                    mixed = true;
                }
            }
        }
        blog("initializeCalibration - saving data");
        utils.setScriptVar(player, CALIBRATION_GOAL, goal);
        utils.setScriptVar(player, CALIBRATION_CURRENT, current);
        utils.setScriptVar(player, CALIBRATION_TRIES, tries);
        utils.setScriptVar(player, CALIBRATION_MAX_TRIES, tries);
        return true;
    }
    public int[] toggleButton(int[] config, int button) throws InterruptedException
    {
        if (config == null || button < 0)
        {
            return null;
        }
        blog("toggleButton - init");
        int secondary1 = -1;
        int secondary2 = -1;
        switch (button)
        {
            case 0:
            secondary1 = 3;
            secondary2 = 4;
            break;
            case 1:
            secondary1 = 4;
            secondary2 = 5;
            break;
            case 2:
            secondary1 = 3;
            secondary2 = 5;
            break;
            case 3:
            secondary1 = 0;
            secondary2 = 2;
            break;
            case 4:
            secondary1 = 0;
            secondary2 = 1;
            break;
            case 5:
            secondary1 = 1;
            secondary2 = 2;
            break;
        }
        if (secondary1 == -1 || secondary2 == -1)
        {
            return null;
        }
        if (config[button] == 0)
        {
            config[button] = 1;
        }
        else 
        {
            config[button] = 0;
        }
        if (config[secondary1] == 0)
        {
            config[secondary1] = 1;
        }
        else 
        {
            config[secondary1] = 0;
        }
        if (config[secondary2] == 0)
        {
            config[secondary2] = 1;
        }
        else 
        {
            config[secondary2] = 0;
        }
        blog("toggleButton - returning config");
        return config;
    }
    public int configProcessorPuzzleCallback(obj_id self, dictionary params) throws InterruptedException
    {
        blog("configProcessorPuzzleCallback - INIT");
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player))
        {
            blog("configProcessorPuzzleCallback - NO PLAYER");
            return SCRIPT_CONTINUE;
        }
        String widgetName = params.getString("eventWidgetName");
        blog("configProcessorPuzzleCallback widgetName - " + widgetName);
        int pid = sui.getPid(player, PID_NAME);
        if (pid <= 0)
        {
            blog("configProcessorPuzzleCallback pid FAILED " + pid);
            return SCRIPT_CONTINUE;
        }
        blog("configProcessorPuzzleCallback pid - " + pid);
        if (widgetName == null || widgetName.equals(""))
        {
            blog("configProcessorPuzzleCallback widgetName = null ");
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_VAR);
            if (!hasObjVar(component, STATUS_VAR))
            {
                setObjVar(component, STATUS_VAR, -1);
                sendSystemMessage(self, MSG_CALIBRATION_ABORTED);
            }
            removePlayerVars(player);
            forceCloseSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        blog("configProcessorPuzzleCallback widget != null");
        int index = -1;
        for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
        {
            setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "Color", "#FFFFFF");
            if (widgetName.equalsIgnoreCase(CONFIG_PLAYER_BUTTONS[i]))
            {
                index = i;
            }
        }
        blog("configProcessorPuzzleCallback index: " + index);
        if (index < 0 || index > 5)
        {
            return SCRIPT_CONTINUE;
        }
        int[] current = utils.getIntArrayScriptVar(player, CALIBRATION_CURRENT);
        int[] goal = utils.getIntArrayScriptVar(player, CALIBRATION_GOAL);
        int tries = utils.getIntScriptVar(player, CALIBRATION_TRIES);
        int max_tries = utils.getIntScriptVar(player, CALIBRATION_MAX_TRIES);
        if (current == null || goal == null)
        {
            blog("configProcessorPuzzleCallback current or goal == null");
            removePlayerVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("configProcessorPuzzleCallback - About to toggle buttons");
        current = toggleButton(current, index);
        if (current == null)
        {
            blog("configProcessorPuzzleCallback current is NULL");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
        {
            if (current[i] == 1)
            {
                blog("configProcessorPuzzleCallback setting to black: " + CONFIG_PLAYER_BUTTONS[i]);
                setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "Color", "#000000");
            }
        }
        tries--;
        int integrity = (int)(((float)tries / max_tries) * 100);
        blog("configProcessorPuzzleCallback integrity: " + integrity);
        boolean win = true;
        for (int i = 0; i < current.length; i++)
        {
            if (current[i] != goal[i])
            {
                win = false;
            }
        }
        blog("configProcessorPuzzleCallback win: " + win);
        if (win)
        {
            blog("configProcessorPuzzleCallback THE WIN ");
            obj_id component = utils.getObjIdScriptVar(player, COMPONENT_VAR);
            setObjVar(component, STATUS_VAR, 1);
            setSUIProperty(pid, "top.description.desc", "Text", CALIBRATION_SUCCESS);
            for (String configPlayerButton : CONFIG_PLAYER_BUTTONS) {
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, configPlayerButton, "noCallback");
                setSUIProperty(pid, configPlayerButton, "GetsInput", "false");
            }
            rewardPlayer(self, player);
        }
        else if (tries <= 0)
        {
            blog("configProcessorPuzzleCallback YOU LOSE ");
            obj_id component = utils.getObjIdScriptVar(player, COMPONENT_VAR);
            setObjVar(component, STATUS_VAR, -1);
            setSUIProperty(pid, "top.description.attempts", "Text", CALIBRATION_ATTEMPTS_REMAINING + " " + integrity + "%");
            setSUIProperty(pid, "top.description.desc", "Text", CALIBRATION_FAILURE);
            for (String configPlayerButton : CONFIG_PLAYER_BUTTONS) {
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, configPlayerButton, "noCallback");
                setSUIProperty(pid, configPlayerButton, "GetsInput", "false");
            }
            removePlayerVars(player);
        }
        else 
        {
            blog("configProcessorPuzzleCallback DECREMENT ");
            setSUIProperty(pid, "top.description.attempts", "Text", CALIBRATION_ATTEMPTS_REMAINING + " " + integrity + "%");
        }
        utils.setScriptVar(player, CALIBRATION_CURRENT, current);
        utils.setScriptVar(player, CALIBRATION_TRIES, tries);
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public boolean rewardPlayer(obj_id collectionItem, obj_id player) throws InterruptedException
    {
        if (!isValidId(collectionItem) || !isValidId(player))
        {
            return false;
        }
        String baseSlotName = getStringObjVar(collectionItem, OBJVAR_SLOT_NAME);
        String[] splitSlotNames = split(baseSlotName, ':');
        String slotName = splitSlotNames[1];
        String collectionName = splitSlotNames[0];
        if (!hasCompletedCollectionSlotPrereq(player, slotName))
        {
            sendSystemMessage(player, collection.SID_NEED_TO_ACTIVATE_COLLECTION);
            return false;
        }
        else if (hasCompletedCollection(player, collectionName))
        {
            sendSystemMessage(player, SID_ALREADY_FINISHED_COLLECTION);
            return false;
        }
        else if ((slotName == null || slotName.equals("")) || hasCompletedCollectionSlot(player, slotName))
        {
            sendSystemMessage(player, SID_ALREADY_HAVE_SLOT);
            return false;
        }
        boolean currentState = collection.checkState(player);
        if (!currentState)
        {
            return false;
        }
        boolean isCloseEnough = collection.checkDistance(collectionItem, player, MAX_RANGE_TO_COLLECT);
        if (!isCloseEnough)
        {
            blog("Not Close ENOUGH");
            sendSystemMessage(player, SID_NOT_CLOSE_ENOUGH);
            return false;
        }
        blog("Player collecting " + slotName);
        stealth.testInvisNonCombatAction(player, collectionItem);
        collection.giveAreaMobsHate(collectionItem, player);
        closeOldWindow(player);
        removePlayerVars(player);
        if (modifyCollectionSlotValue(player, slotName, 1))
        {
            CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was consumed into a collection, for player " + getFirstName(player) + "(" + player + ").");
            return false;
        }
        else 
        {
            CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was NOT consumed into a collection, for player " + getFirstName(player) + "(" + player + ").");
            sendSystemMessage(player, SID_REPORT_CONSUME_ITEM_FAIL);
        }
        return false;
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        blog("closeOldWindow - init");
        int pid = sui.getPid(player, PID_NAME);
        blog("closeOldWindow - pid: " + pid);
        if (pid > -1)
        {
            blog("closeOldWindow - force closing: " + pid);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
        }
    }
    public void removePlayerVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, VAR_PREFIX);
        utils.removeObjVar(player, VAR_PREFIX);
    }
    public boolean blog(String msg) throws InterruptedException
    {
        LOG("minigame", msg);
        return true;
    }
}
