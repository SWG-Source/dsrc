package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.groundquests;
import script.library.ai_lib;

public class etyyy_broodmaster_hsskas_spawner extends script.base_script
{
    public etyyy_broodmaster_hsskas_spawner()
    {
    }
    public static final String triggerName = "triggerVolumeSpawnHsskas";
    public static final String actionTrigger = "triggerVolumeActionTrigger";
    public static final float triggerRange = 100f;
    public static final float actionRange = 50f;
    public static final String hsskasSpawned = "spawned.podSpawned";
    public static final String hsskasId = "spawned.podId";
    public static final String hsskasOwner = "spawned.podOwner";
    public static final String hsskasParent = "spawned.podParent";
    public static final boolean doLogging = false;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        establishTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        establishTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public void establishTriggerVolume(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, triggerName))
        {
            doLogging("establishTriggerVolume", "Trigger Volume(" + triggerName + "/" + triggerRange + ") set");
            createTriggerVolume(triggerName, triggerRange, true);
        }
        else 
        {
            doLogging("establishTriggerVolume", "Trigger Volume(" + triggerName + "/" + triggerRange + ") already established");
        }
        if (!hasTriggerVolume(self, actionTrigger))
        {
            doLogging("establishTriggerVolume", "Trigger Volume(" + actionTrigger + "/" + actionRange + ") set");
            createTriggerVolume(actionTrigger, actionRange, true);
        }
        else 
        {
            doLogging("establishTriggerVolume", "Trigger Volume(" + actionTrigger + "/" + actionRange + ") already established");
        }
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(triggerName))
        {
            if (canSpawnHsskas(breacher))
            {
                if (isHsskasAvailable(self))
                {
                    generateEscapePod(self, breacher);
                }
            }
        }
        if (volumeName.equals(actionTrigger))
        {
            if (canSpawnHsskas(breacher))
            {
                if (ownsThisSpawn(self, breacher))
                {
                    triggerEvent(breacher);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(triggerName))
        {
            if (ownsThisSpawn(self, breacher))
            {
                handlePlayerDepart(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnHsskas(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_gursan_slay_hsskas", "taskApproachHsskas");
    }
    public void generateEscapePod(obj_id self, obj_id player) throws InterruptedException
    {
        location spawnLoc = getLocation(self);
        obj_id pod = create.object("object/static/poi/scene/escape_pod.iff", spawnLoc);
        setYaw(pod, 117);
        utils.setScriptVar(self, hsskasSpawned, 1);
        utils.setScriptVar(self, hsskasId, pod);
        utils.setScriptVar(self, hsskasOwner, player);
        utils.setScriptVar(pod, hsskasParent, self);
        attachScript(pod, "theme_park.kashyyyk.etyyy_hsskas_pod");
        messageTo(self, "destroyEscapePod", null, 300, false);
    }
    public boolean ownsThisSpawn(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(self, hsskasOwner))
        {
            return (utils.getObjIdScriptVar(self, hsskasOwner) == player);
        }
        else 
        {
            return false;
        }
    }
    public void triggerEvent(obj_id player) throws InterruptedException
    {
        groundquests.sendSignal(player, "signalApproachHsskas");
    }
    public void handlePlayerDepart(obj_id self) throws InterruptedException
    {
        destroyObject(utils.getObjIdScriptVar(self, hsskasId));
        utils.removeScriptVar(self, hsskasSpawned);
        utils.removeScriptVar(self, hsskasId);
        utils.removeScriptVar(self, hsskasOwner);
    }
    public boolean isHsskasAvailable(obj_id self) throws InterruptedException
    {
        return (!utils.hasScriptVar(self, hsskasSpawned));
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/etyyy_hsskas_spawner/" + section, message);
        }
    }
    public int destroyEscapePod(obj_id self, dictionary params) throws InterruptedException
    {
        handlePlayerDepart(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (doLogging)
        {
            sendSystemMessageTestingOnly(speaker, "My objId is " + self);
            establishTriggerVolume(self);
        }
        return SCRIPT_CONTINUE;
    }
}
