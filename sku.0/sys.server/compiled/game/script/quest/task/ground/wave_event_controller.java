package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.groundquests;
import script.library.prose;
import script.library.spawning;
import script.library.stealth;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class wave_event_controller extends script.base_script
{
    public wave_event_controller()
    {
    }
    public static final String dataTableWaveEventPrimaryTargetPre = "PRIMARY_TARGET_WAVE_";
    public static final String dataTableWaveEventGuardPre = "GUARDS_SPAWNED_WAVE_";
    public static final String dataTableWaveEventNumGuardsPre = "NUM_GUARDS_WAVE_";
    public static final String dataTableWaveEventDelayPre = "DELAY_WAVE_";
    public static final String dataTableWaveEventRadiusPre = "RADIUS_WAVE_";
    public static final String dataTableWaveEventUtterancePre = "UTTERANCE_WAVE_";
    public static final String dataTableWaveEventStartMessage = "START_MESSAGE";
    public static final String TASK_TYPE = "wave_event";
    public static final String WAVE_SCRIPT_VAR = "waveEventCurrentWave";
    public static final String PLAYER_SCRIPT_VAR = "waveEventPlayer";
    public static final String CHILDRENLIST_SCRIPT_VAR = "waveEventChildrenList";
    public static final boolean LOGGING = false;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        int menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        if (isGod(player))
        {
            menu = menuInfo.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("ui_radial", "wave_event_reset"));
        }
        if (hasScript(player, "quest.task.ground.wave_event_player"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self, TASK_TYPE))
            {
                String menuText = groundquests.getRetrieveMenuText(player, self, TASK_TYPE);
                string_id menuStringId = utils.unpackString(menuText);
                if (isMob(self))
                {
                    menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, menuStringId);
                }
                else 
                {
                    menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, menuStringId);
                }
            }
        }
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasScript(player, "quest.task.ground.wave_event_player"))
            {
                if (groundquests.playerNeedsToRetrieveThisItem(player, self, TASK_TYPE))
                {
                    if (groundquests.gcwActiveDutyCheck(self, player))
                    {
                        int countdownTimer = groundquests.getQuestCountdownTime(player, self);
                        if (countdownTimer > 0)
                        {
                            String menuText = groundquests.getRetrieveMenuText(player, self, TASK_TYPE);
                            string_id menuStringId = utils.unpackString(menuText);
                            String handler = "handleQuestCountdownTimer";
                            int startTime = 0;
                            int range = 3;
                            int flags = 0;
                            flags |= sui.CD_EVENT_INCAPACITATE;
                            flags |= sui.CD_EVENT_DAMAGED;
                            stealth.testInvisNonCombatAction(player, self);
                            int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", menuStringId, startTime, countdownTimer, handler, range, flags);
                        }
                        else 
                        {
                            if (utils.hasScriptVar(self, WAVE_SCRIPT_VAR))
                            {
                                int wave = utils.getIntScriptVar(self, WAVE_SCRIPT_VAR);
                                if (wave > 0)
                                {
                                    sendSystemMessage(player, new string_id("quest/groundquests", "wave_event_already_underway"));
                                    return SCRIPT_CONTINUE;
                                }
                            }
                            waveEventBegin(self, player);
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_not_active_duty"));
                    }
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (isGod(player))
            {
                sendSystemMessage(player, "Reseting wave event controller...", "");
                clearEventArea(self);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_no_interest"));
        return SCRIPT_CONTINUE;
    }
    public int handleQuestCountdownTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_locomotion"));
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_incapacitated"));
            }
            else if (event == sui.CD_EVENT_DAMAGED)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_damaged"));
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        if (utils.hasScriptVar(self, WAVE_SCRIPT_VAR))
        {
            int wave = utils.getIntScriptVar(self, WAVE_SCRIPT_VAR);
            if (wave > 0)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "wave_event_already_underway"));
                return SCRIPT_CONTINUE;
            }
        }
        waveEventBegin(self, player);
        return SCRIPT_CONTINUE;
    }
    public void waveEventBegin(obj_id self, obj_id player) throws InterruptedException
    {
        int wave = 1;
        float delay = waveEventGetWaveDelay(self, player, wave);
        if (delay > -1)
        {
            clearEventArea(self);
            dictionary webster = trial.getSessionDict(self);
            messageTo(self, "defaultEventReset", webster, 600, false);
            utils.setScriptVar(self, WAVE_SCRIPT_VAR, wave);
            utils.setScriptVar(self, PLAYER_SCRIPT_VAR, player);
            prose_package pp = new prose_package();
            pp.stringId = new string_id("quest/groundquests", "wave_event_starting");
            dictionary questData = getwaveEventQuestData(self, player);
            if (questData != null && !questData.isEmpty())
            {
                boolean taskFound = questData.getBoolean("waveEventTaskFound");
                if (taskFound)
                {
                    int questCrc = questData.getInt("waveEventQuestCrc");
                    int taskId = questData.getInt("waveEventTaskId");
                    String newMsg = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableWaveEventStartMessage);
                    if (newMsg != null && !newMsg.equals(""))
                    {
                        pp.stringId = utils.unpackString(newMsg);
                    }
                }
            }
            sendSystemMessageProse(player, pp);
            messageTo(self, "waveEventSpawnNextWave", webster, delay, false);
            if (hasObjVar(self, "questFlavorObject"))
            {
                messageTo(self, "handleQuestFlavorObject", null, 0, false);
            }
        }
        return;
    }
    public dictionary getwaveEventQuestData(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary webster = new dictionary();
        int questCrc = -1;
        int taskId = -1;
        boolean taskFound = false;
        String itemTemplateName = getTemplateName(self);
        dictionary tasks = groundquests.getActiveTasksForTaskType(player, TASK_TYPE);
        if ((tasks != null) && !tasks.isEmpty())
        {
            java.util.Enumeration keys = tasks.keys();
            while (keys.hasMoreElements() || !taskFound)
            {
                String questCrcString = (String)keys.nextElement();
                int tempQuestCrc = utils.stringToInt(questCrcString);
                int[] tasksForCurrentQuest = tasks.getIntArray(questCrcString);
                for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                {
                    int tempTaskId = tasksForCurrentQuest[i];
                    String baseObjVar = groundquests.getBaseObjVar(player, TASK_TYPE, questGetQuestName(tempQuestCrc), tempTaskId);
                    String retrieveTemplateName = groundquests.getTaskStringDataEntry(tempQuestCrc, tempTaskId, "SERVER_TEMPLATE");
                    if (retrieveTemplateName != null && itemTemplateName.equals(retrieveTemplateName))
                    {
                        questCrc = tempQuestCrc;
                        taskId = tempTaskId;
                        taskFound = true;
                    }
                }
            }
        }
        webster.put("waveEventTaskFound", taskFound);
        webster.put("waveEventQuestCrc", questCrc);
        webster.put("waveEventTaskId", taskId);
        return webster;
    }
    public float waveEventGetWaveDelay(obj_id self, obj_id player, int wave) throws InterruptedException
    {
        int delay = -1;
        dictionary webster = getwaveEventQuestData(self, player);
        if (webster != null && !webster.isEmpty())
        {
            boolean taskFound = webster.getBoolean("waveEventTaskFound");
            if (taskFound)
            {
                int questCrc = webster.getInt("waveEventQuestCrc");
                int taskId = webster.getInt("waveEventTaskId");
                String dataTableWaveEventDelay = dataTableWaveEventDelayPre + wave;
                delay = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableWaveEventDelay);
            }
        }
        return delay;
    }
    public int waveEventSpawnNextWave(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        int wave = utils.getIntScriptVar(self, WAVE_SCRIPT_VAR);
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_SCRIPT_VAR);
        if (!isValidId(player))
        {
            messageTo(self, "cleanupEvent", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        dictionary webster = getwaveEventQuestData(self, player);
        if (webster != null && !webster.isEmpty())
        {
            boolean taskFound = webster.getBoolean("waveEventTaskFound");
            if (taskFound)
            {
                int questCrc = webster.getInt("waveEventQuestCrc");
                int taskId = webster.getInt("waveEventTaskId");
                String dataTableWaveEventPrimaryTarget = dataTableWaveEventPrimaryTargetPre + wave;
                String waveEventPrimaryTarget = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableWaveEventPrimaryTarget);
                if (waveEventPrimaryTarget != null && waveEventPrimaryTarget.length() > 0)
                {
                    String dataTableWaveEventRadius = dataTableWaveEventRadiusPre + wave;
                    float radius = groundquests.getTaskFloatDataEntry(questCrc, taskId, dataTableWaveEventRadius);
                    location spawnLoc = spawning.getRandomLocationAtDistance(getLocation(self), radius);
                    obj_id spawnedPrimaryTarget = create.object(waveEventPrimaryTarget, spawnLoc);
                    if (!isIdValid(spawnedPrimaryTarget))
                    {
                        messageTo(self, "cleanupEvent", null, 2, false);
                        return SCRIPT_CONTINUE;
                    }
                    setHibernationDelay(spawnedPrimaryTarget, 3600.0f);
                    trial.setParent(self, spawnedPrimaryTarget, true);
                    trial.setInterest(spawnedPrimaryTarget);
                    String dataTableWaveEventUtterance = dataTableWaveEventUtterancePre + wave;
                    String utterance = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableWaveEventUtterance);
                    if (utterance != null && utterance.length() > 0)
                    {
                        utils.setScriptVar(spawnedPrimaryTarget, "waveEventUtterance", utterance);
                        utils.setScriptVar(spawnedPrimaryTarget, "waveEventPlayer", player);
                    }
                    attachScript(spawnedPrimaryTarget, "quest.task.ground.wave_event_on_creature");
                    Vector waveEventChildrenList = new Vector();
                    waveEventChildrenList.setSize(0);
                    utils.addElement(waveEventChildrenList, spawnedPrimaryTarget);
                    utils.setScriptVar(self, CHILDRENLIST_SCRIPT_VAR, waveEventChildrenList);
                    String dataTableWaveEventGuard = dataTableWaveEventGuardPre + wave;
                    String waveEventGuard = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableWaveEventGuard);
                    if (waveEventGuard != null && waveEventGuard.length() > 0)
                    {
                        String dataTableWaveEventNumGuards = dataTableWaveEventNumGuardsPre + wave;
                        int waveEventNumGuards = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableWaveEventNumGuards);
                        if (waveEventNumGuards > 0)
                        {
                            for (int i = 0; i < waveEventNumGuards; i++)
                            {
                                location guardLoc = spawning.getRandomLocationAtDistance(getLocation(self), radius);
                                obj_id spawnedGuard = create.object(waveEventGuard, guardLoc);
                                if (!isIdValid(spawnedGuard))
                                {
                                    return SCRIPT_CONTINUE;
                                }
                                setHibernationDelay(spawnedGuard, 3600.0f);
                                trial.setParent(self, spawnedGuard, true);
                                trial.setInterest(spawnedGuard);
                                attachScript(spawnedGuard, "quest.task.ground.wave_event_on_creature");
                                if (!utils.hasScriptVar(self, CHILDRENLIST_SCRIPT_VAR))
                                {
                                    messageTo(self, "cleanupEvent", null, 2, false);
                                    return SCRIPT_CONTINUE;
                                }
                                waveEventChildrenList = utils.getResizeableObjIdArrayScriptVar(self, CHILDRENLIST_SCRIPT_VAR);
                                if (waveEventChildrenList != null)
                                {
                                    utils.addElement(waveEventChildrenList, spawnedGuard);
                                    if (waveEventChildrenList != null && waveEventChildrenList.size() > 0)
                                    {
                                        utils.setScriptVar(self, CHILDRENLIST_SCRIPT_VAR, waveEventChildrenList);
                                    }
                                }
                                else 
                                {
                                    messageTo(self, "cleanupEvent", null, 2, false);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                }
                else 
                {
                    groundquests.questOutputDebugInfo(self, TASK_TYPE, "waveEventSpawnNextWave", "No more waves to spawn...the wave event has ended.");
                    messageTo(self, "cleanupEvent", null, 2, false);
                }
            }
            else 
            {
                groundquests.questOutputDebugInfo(self, TASK_TYPE, "waveEventSpawnNextWave", "No active wave event task was found for this player(" + player + ") and this object(" + getTemplateName(self) + ").");
                messageTo(self, "cleanupEvent", null, 2, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int waveEventChildDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id player = utils.getObjIdScriptVar(self, PLAYER_SCRIPT_VAR);
            if (isIdValid(player))
            {
                obj_id child = params.getObjId("waveEventChild");
                if (utils.hasScriptVar(self, CHILDRENLIST_SCRIPT_VAR))
                {
                    Vector waveEventChildrenList = utils.getResizeableObjIdArrayScriptVar(self, CHILDRENLIST_SCRIPT_VAR);
                    
                    if (waveEventChildrenList != null)
                    {
                        if (waveEventChildrenList.contains(child))
                        {
                            waveEventChildrenList.removeElement(child);
                        }
                        if (waveEventChildrenList.size() > 0)
                        {
                            utils.setScriptVar(self, CHILDRENLIST_SCRIPT_VAR, waveEventChildrenList);
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            utils.removeScriptVar(self, CHILDRENLIST_SCRIPT_VAR);
                            int previousWave = utils.getIntScriptVar(self, WAVE_SCRIPT_VAR);
                            int nextWave = previousWave + 1;
                            float delay = waveEventGetWaveDelay(self, player, nextWave);
                            if (delay > -1)
                            {
                                dictionary webster = trial.getSessionDict(self);
                                utils.setScriptVar(self, WAVE_SCRIPT_VAR, nextWave);
                                messageTo(self, "waveEventSpawnNextWave", webster, delay, false);
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
            }
        }
        messageTo(self, "cleanupEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public void spoutChildrenListScriptVar(obj_id self, obj_id player) throws InterruptedException
    {
        if (isGod(player))
        {
            sendSystemMessage(player, "Spouting waveEventChildrenList scriptvar value...", "");
            Vector testList = utils.getResizeableObjIdArrayScriptVar(self, CHILDRENLIST_SCRIPT_VAR);
            if (testList != null)
            {
                for (int j = 0; j < testList.size(); j++)
                {
                    sendSystemMessage(player, "testList[" + j + "] = " + ((obj_id)testList.get(j)), "");
                }
            }
        }
    }
    public int defaultEventReset(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_SCRIPT_VAR);
        if (!isValidId(player))
        {
            clearEventArea(self);
            return SCRIPT_CONTINUE;
        }
        dictionary webster = getwaveEventQuestData(self, player);
        if (webster != null && !webster.isEmpty())
        {
            boolean taskFound = webster.getBoolean("waveEventTaskFound");
            if (taskFound && !hasObjVar(self, "waveEventDoNotComplete"))
            {
                int questCrc = webster.getInt("waveEventQuestCrc");
                int taskId = webster.getInt("waveEventTaskId");
                questCompleteTask(questCrc, taskId, player);
                groundquests.completeTaskForGroupMembersInRange(self, player, questCrc, taskId);
                clearEventArea(self, player, true);
                return SCRIPT_CONTINUE;
            }
        }
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id self) throws InterruptedException
    {
        clearEventArea(self, obj_id.NULL_ID, false);
    }
    public void clearEventArea(obj_id self, obj_id player, boolean taskCompleted) throws InterruptedException
    {
        utils.setScriptVar(self, WAVE_SCRIPT_VAR, 0);
        utils.removeScriptVar(self, PLAYER_SCRIPT_VAR);
        utils.removeScriptVar(self, CHILDRENLIST_SCRIPT_VAR);
        trial.bumpSession(self);
        obj_id[] objects = trial.getChildrenInRange(self, self, 1000.0f);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            obj_id child = objects[i];
            if (isIdValid(child) && child != self && !isPlayer(child))
            {
                if (!hasScript(child, "corpse.ai_corpse"))
                {
                    trial.cleanupObject(child);
                }
            }
        }
        if (hasObjVar(self, "questFlavorObject"))
        {
            dictionary webster = new dictionary();
            webster.put("taskCompleted", taskCompleted);
            webster.put("player", player);
            messageTo(self, "handleQuestFlavorObjectCleanup", webster, 0, false);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/wave_spawner/" + section, message);
        }
    }
}
