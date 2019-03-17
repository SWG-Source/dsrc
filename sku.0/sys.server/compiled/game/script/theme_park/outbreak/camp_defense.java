package script.theme_park.outbreak;

import script.dictionary;
import script.library.ai_lib;
import script.library.groundquests;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class camp_defense extends script.base_script
{
    public camp_defense()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String TRIGGER_NAME_PREFIX = "camp_defense_trigger_";
    public static final String SCRIPT_LOG = "outbreak_trigger";
    public static final String CLIENT_EFFECT = "appearance/pt_smoke_puff.prt";
    public static final String WAITING_TO_START = "waitingAtPost";
    public static final String BEGIN_DEFENSE_SIGNAL = "hasWaitedAtPost";
    public static final String END_DEFENSE_SIGNAL = "hasKilledWave";
    public static final String FAIL_DEFENSE_SIGNAL = "campDefenseFailed";
    public static final String SPAWNER_OBJVAR = "spawnerObject";
    public static final String GUARD_OCCUPIED = "occupied";
    public static final float TRIGGER_RADIUS = 10.0f;
    public static final float SEARCH_RADIUS = 100.0f;
    public static final int WAIT_DELAY = 5;
    public static final int FAIL_TIMER = 480;
    public static final String MENU_STRING_FILE = "theme_park/outbreak/outbreak";
    public static final string_id SID_COME_BACK_LATER = new string_id(MENU_STRING_FILE, "guard_post_occupied_come_back_later");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "camp_defense.OnInitialize() initializing a camp defense controller.");
        messageTo(self, "findPathNodes", null, 2, false);
        createTriggerVolume(TRIGGER_NAME_PREFIX + self, TRIGGER_RADIUS, true);
        if (!hasTriggerVolume(self, TRIGGER_NAME_PREFIX + self))
        {
            messageTo(self, "triggerVolFailsafe", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!hasObjVar(self, "questObject"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "ourSpawner"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.OnTriggerVolumeEntered() the controller, " + self + ", has no spawner object.");
            return SCRIPT_CONTINUE;
        }
        obj_id ourSpawner = utils.getObjIdScriptVar(self, "ourSpawner");
        if (!isValidId(ourSpawner) || !exists(ourSpawner))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.OnTriggerVolumeEntered() the controller, " + self + ", has an invalid spawner object.");
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, "questObject");
        if (questName == null || questName.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(whoTriggeredMe, questName))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(whoTriggeredMe, questName, "goToPost") && !groundquests.isTaskActive(whoTriggeredMe, questName, WAITING_TO_START))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, GUARD_OCCUPIED))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.OnTriggerVolumeEntered() Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ") is attempting to occupy a post that is already occupied.");
            sendSystemMessage(whoTriggeredMe, SID_COME_BACK_LATER);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "camp_defense.OnTriggerVolumeEntered() Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ") has triggered the volume and is ready to defend camp.");
        int combatLevel = getLevel(whoTriggeredMe);
        if (combatLevel < 0 || combatLevel > 90)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.OnTriggerVolumeEntered() Player " + whoTriggeredMe + " has a level that is invalid. Player level is: " + combatLevel + ". Aborting the camp defense.");
            return SCRIPT_CONTINUE;
        }
        int campDefenseKey = 0;
        if (!utils.hasScriptVar(whoTriggeredMe, "campDefenseKey"))
        {
            utils.setScriptVar(whoTriggeredMe, "campDefenseKey", 0);
        }
        else 
        {
            campDefenseKey = utils.getIntScriptVar(whoTriggeredMe, "campDefenseKey");
            if (campDefenseKey >= 0)
            {
                campDefenseKey = campDefenseKey + 1;
            }
            else 
            {
                campDefenseKey = 0;
            }
            utils.setScriptVar(whoTriggeredMe, "campDefenseKey", campDefenseKey);
        }
        dictionary webster = new dictionary();
        webster.put("player", whoTriggeredMe);
        webster.put("questName", questName);
        webster.put("combatLevel", combatLevel);
        webster.put("ourSpawner", ourSpawner);
        webster.put("campDefenseKey", campDefenseKey);
        webster.put("tookTooLong", true);
        messageTo(self, "updatePlayerDefense", webster, WAIT_DELAY, false);
        messageTo(self, "failPlayerDefense", webster, FAIL_TIMER, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!hasObjVar(self, "questObject"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, "questObject");
        if (questName == null || questName.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, GUARD_OCCUPIED))
        {
            obj_id occupyingPlayer = utils.getObjIdScriptVar(self, GUARD_OCCUPIED);
            if (!isValidId(whoTriggeredMe) || !exists(whoTriggeredMe) || occupyingPlayer == whoTriggeredMe)
            {
                utils.removeScriptVar(self, GUARD_OCCUPIED);
            }
        }
        // added "isLoaded()" to attempt to prevent situation where isQuestActive could not find the player.
        if (whoTriggeredMe.isLoaded() && !groundquests.isQuestActive(whoTriggeredMe, questName))
        {
            return SCRIPT_CONTINUE;
        }
        // added "isLoaded()" to attempt to prevent situation where isQuestActive could not find the player.
        if (whoTriggeredMe.isLoaded() && !groundquests.isTaskActive(whoTriggeredMe, questName, "surviveTimer") && !groundquests.isTaskActive(whoTriggeredMe, questName, "waitToCleanUpAll"))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = new dictionary();
        webster.put("player", whoTriggeredMe);
        webster.put("questName", questName);
        webster.put("tookTooLong", false);
        messageTo(self, "failPlayerDefense", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int updatePlayerDefense(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() initialized.");
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() Params invalid.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() player param not found.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("questName"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() questName param not found.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("combatLevel"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() questName param not found.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("ourSpawner"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() ourSpawner param not found.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() validation completed.");
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() player OID invalid.");
            return SCRIPT_CONTINUE;
        }
        String questName = params.getString("questName");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() player " + player + " has received an invalid quest name.");
            return SCRIPT_CONTINUE;
        }
        int combatLevel = params.getInt("combatLevel");
        if (combatLevel < 0 || combatLevel > 90)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() Player " + player + " has a level that is invalid. Player level is: " + combatLevel + ". Aborting the camp defense.");
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(player, questName))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() Player " + player + " did not have the appropriate quest active. Aborting the camp defense.");
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(player, questName, WAITING_TO_START))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() Player " + player + " did not have the appropriate quest TASK active. Aborting the camp defense.");
            return SCRIPT_CONTINUE;
        }
        obj_id ourSpawner = params.getObjId("ourSpawner");
        if (!isValidId(ourSpawner) || !exists(ourSpawner))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() ourSpawner OID invalid.");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, "enemyList"))
        {
            utils.removeScriptVar(player, "enemyList");
        }
        groundquests.sendSignal(player, BEGIN_DEFENSE_SIGNAL);
        CustomerServiceLog("outbreak_themepark", "camp_defense.updatePlayerDefense() messaging Spawner.");
        messageTo(ourSpawner, "startSpawning", params, 1, false);
        utils.setScriptVar(self, GUARD_OCCUPIED, player);
        utils.setScriptVar(player, GUARD_OCCUPIED, self);
        return SCRIPT_CONTINUE;
    }
    public int failPlayerDefense(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() Params invalid.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() player param not found.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("questName"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() questName param not found.");
            return SCRIPT_CONTINUE;
        }
        int campDefenseKey = 0;
        if (params.containsKey("campDefenseKey"))
        {
            campDefenseKey = params.getInt("campDefenseKey");
        }
        boolean tookTooLong = true;
        if (params.containsKey("tookTooLong"))
        {
            tookTooLong = params.getBoolean("tookTooLong");
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() player OID invalid.");
            return SCRIPT_CONTINUE;
        }
        String questName = params.getString("questName");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() player " + player + " has received an invalid quest name. Aborthing quest failure.");
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(player, questName))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() Player " + player + " did not receive the appropriate quest in a payload for a messageHandler. Aborting the camp defense quest failure.");
            return SCRIPT_CONTINUE;
        }
        if (groundquests.hasCompletedTask(player, questName, "waitToCleanUpAll"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() Player " + player + " has completed the task, abort the removal!");
            return SCRIPT_CONTINUE;
        }
        if (tookTooLong)
        {
            int playerCampDefenseKey = 0;
            if (!utils.hasScriptVar(player, "campDefenseKey"))
            {
                CustomerServiceLog("outbreak_themepark", "camp_defense.failPlayerDefense() Player " + player + " did not have the appropriate quest campDefenseKey on their character. Continuing anyway.");
                playerCampDefenseKey = campDefenseKey;
            }
            else 
            {
                playerCampDefenseKey = utils.getIntScriptVar(player, "campDefenseKey");
                if (playerCampDefenseKey < 0)
                {
                    playerCampDefenseKey = campDefenseKey;
                }
            }
            if (playerCampDefenseKey != campDefenseKey)
            {
                return SCRIPT_CONTINUE;
            }
            if (!utils.hasScriptVar(player, "givenMoreDefenseTime") && ai_lib.isInCombat(player) && utils.hasScriptVar(player, "enemyList") && utils.hasScriptVar(self, "ourSpawner"))
            {
                obj_id ourSpawner = utils.getObjIdScriptVar(self, "ourSpawner");
                if (isValidId(ourSpawner))
                {
                    int spawnAmt = getIntObjVar(ourSpawner, "numberOfEnemies");
                    if (spawnAmt > 0)
                    {
                        obj_id[] enemyList = utils.getObjIdArrayScriptVar(player, "enemyList");
                        if (enemyList != null && enemyList.length > 0)
                        {
                            if (spawnAmt > enemyList.length)
                            {
                                utils.setScriptVar(player, "givenMoreDefenseTime", true);
                                messageTo(self, "failPlayerDefense", params, FAIL_TIMER, false);
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
            }
        }
        groundquests.sendSignal(player, FAIL_DEFENSE_SIGNAL);
        utils.removeScriptVar(self, GUARD_OCCUPIED);
        utils.removeScriptVar(player, "givenMoreDefenseTime");
        return SCRIPT_CONTINUE;
    }
    public int triggerVolFailsafe(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "camp_defense.triggerVolFailsafe() initializing triggerVolFailsafe to make sure the object has a volume trigger on " + self);
        createTriggerVolume(TRIGGER_NAME_PREFIX + self, TRIGGER_RADIUS, true);
        if (!hasTriggerVolume(self, TRIGGER_NAME_PREFIX + self))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.triggerVolFailsafe() initializing triggerVolFailsafe has failed for " + self + ". Critical failure.");
            messageTo(self, "triggerVolFailsafe", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int findPathNodes(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "questObject"))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findPathNodes() initializing failed. The controller, " + self + " had no questObject objvar. Location: " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, "questObject");
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findPathNodes() initializing failed. The controller, " + self + " had no questName objvar. Location: " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        obj_id[] spawnerList = getAllObjectsWithObjVar(getLocation(self), SEARCH_RADIUS, SPAWNER_OBJVAR);
        if (spawnerList == null || spawnerList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findPathNodes() the controller, " + self + " failed to find any valid spawner objects within " + SEARCH_RADIUS + " of " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        obj_id ourSpawner = obj_id.NULL_ID;
        for (obj_id obj_id : spawnerList) {
            String spawnerQuestNameMatch = getStringObjVar(obj_id, SPAWNER_OBJVAR);
            if (spawnerQuestNameMatch == null || spawnerQuestNameMatch.length() <= 0) {
                CustomerServiceLog("outbreak_themepark", "camp_defense.findPathNodes() the controller, " + self + " found a spawner object that did not have a quest string. Spawner: " + obj_id);
                continue;
            }
            if (!spawnerQuestNameMatch.equals(questName)) {
                continue;
            }
            ourSpawner = obj_id;
            break;
        }
        if (!isValidId(ourSpawner) || !exists(ourSpawner))
        {
            CustomerServiceLog("outbreak_themepark", "camp_defense.findPathNodes() the controller, " + self + " failed to find any valid spawner objects (after a loop of all objects) within " + SEARCH_RADIUS + " of " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "ourSpawner", ourSpawner);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(SCRIPT_LOG, msg);
        }
        return true;
    }
}
