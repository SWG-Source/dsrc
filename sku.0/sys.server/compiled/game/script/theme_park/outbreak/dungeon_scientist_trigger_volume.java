package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.stealth;
import script.library.utils;

public class dungeon_scientist_trigger_volume extends script.base_script
{
    public dungeon_scientist_trigger_volume()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_trigger";
    public static final String TRIGGER_NAME_PREFIX = "scientist_spawning_trigger_";
    public static final String CLIENT_EFFECT = "appearance/pt_smoke_puff.prt";
    public static final String SCIENTIST_SPAWN_OBJVAR = "scientist_spawn_point";
    public static final String QUEST_IMPERIAL = "outbreak_quest_facility_05_imperial";
    public static final String QUEST_REBEL = "outbreak_quest_facility_05_rebel";
    public static final String QUEST_NEUTRAL = "outbreak_quest_facility_05_neutral";
    public static final String RESCUE_TASK_01 = "mentorComm";
    public static final String RESCUE_TASK_02 = "coverSurvivors";
    public static final String RESCUE_TASK_03 = "moveToTask";
    public static final float TRIGGER_RADIUS = 3f;
    public static final float SPAWNER_RADIUS = 6f;
    public static final int TOTAL_SPAWNERS = 5;
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        blog("Spawner: heard speach");
        if (!isGod(speaker))
        {
            blog("Spawner: player not in godmode");
            return SCRIPT_CONTINUE;
        }
        if (!text.equals("spawn_scientists"))
        {
            blog("Spawner: not the command spawner needs");
            return SCRIPT_CONTINUE;
        }
        blog("Spawner: command received!!");
        if (!sendMessageToSpawnActors(self, speaker))
        {
            blog("Spawner: command FAILED!!");
        }
        blog("Spawner: command success");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume(TRIGGER_NAME_PREFIX + self, TRIGGER_RADIUS, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        blog("OnTriggerVolumeEntered INIT: " + whoTriggeredMe);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        blog("Exited trigger volume: " + whoTriggeredMe);
        if (!isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        blog("Spawner: " + getIntObjVar(self, "spawner"));
        if (!hasObjVar(self, "scientist_spawner"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(whoTriggeredMe, QUEST_IMPERIAL) && !groundquests.isQuestActive(whoTriggeredMe, QUEST_REBEL) && !groundquests.isQuestActive(whoTriggeredMe, QUEST_NEUTRAL))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.OnTriggerVolumeExited() Ground Quest not found for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(whoTriggeredMe, QUEST_IMPERIAL, RESCUE_TASK_01) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_REBEL, RESCUE_TASK_01) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_NEUTRAL, RESCUE_TASK_01) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_IMPERIAL, RESCUE_TASK_02) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_REBEL, RESCUE_TASK_02) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_NEUTRAL, RESCUE_TASK_02) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_IMPERIAL, RESCUE_TASK_03) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_REBEL, RESCUE_TASK_03) && !groundquests.isTaskActive(whoTriggeredMe, QUEST_NEUTRAL, RESCUE_TASK_03))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.OnTriggerVolumeExited() Ground Quest, with task, not found for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(whoTriggeredMe, "survivorsRescued"))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.OnTriggerVolumeExited() Already spawned scientists for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.OnTriggerVolumeExited() Ground Quest and task was found for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
        if (!sendMessageToSpawnActors(self, whoTriggeredMe))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.OnTriggerVolumeExited() the function: sendMessageToSpawnActors() reports that the actors had a problem spawning for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean sendMessageToSpawnActors(obj_id self, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(whoTriggeredMe) || !exists(whoTriggeredMe))
        {
            return false;
        }
        obj_id[] survivorSpawnLocs = getAllObjectsWithObjVar(getLocation(self), SPAWNER_RADIUS, SCIENTIST_SPAWN_OBJVAR);
        if (survivorSpawnLocs == null || survivorSpawnLocs.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "rescue_trigger_volume.sendMessageToSpawnActors() Ground Quest found for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
            return false;
        }
        if (survivorSpawnLocs.length != TOTAL_SPAWNERS)
        {
            if (survivorSpawnLocs.length < TOTAL_SPAWNERS)
            {
                CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.sendMessageToSpawnActors() The trigger object retrieved too many spawners for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
                return false;
            }
            else 
            {
                CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.sendMessageToSpawnActors() The trigger object failed to get all spawners for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ")");
                return false;
            }
        }
        dictionary parms = new dictionary();
        parms.put("owner", whoTriggeredMe);
        for (int i = 0; i < survivorSpawnLocs.length; i++)
        {
            messageTo(survivorSpawnLocs[i], "spawnRescuedActor", parms, 1, false);
        }
        utils.setScriptVar(whoTriggeredMe, "survivorsRescued", true);
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_trigger_volume.OnTriggerVolumeExited() Nodes were messaged to spawn actors for Player: " + getPlayerName(whoTriggeredMe) + " (" + whoTriggeredMe + ").");
        return true;
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
