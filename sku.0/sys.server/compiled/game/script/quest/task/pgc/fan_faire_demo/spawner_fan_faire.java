package script.quest.task.pgc.fan_faire_demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.space_utils;
import script.library.spawning;
import script.library.create;

public class spawner_fan_faire extends script.base_script
{
    public spawner_fan_faire()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "registerWithController"))
        {
            setObjVar(self, "registerWithController", 1);
        }
        if (hasObjVar(self, "what_to_spawn"))
        {
            setName(self, "Fan Faire Spawner: " + getStringObjVar(self, "what_to_spawn"));
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "registerWithController"))
        {
            setObjVar(self, "registerWithController", 1);
        }
        if (hasObjVar(self, "what_to_spawn"))
        {
            setName(self, "Fan Faire Spawner: " + getStringObjVar(self, "what_to_spawn"));
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "spawned"))
        {
            return SCRIPT_CONTINUE;
        }
        String spawnType = getStringObjVar(self, "what_to_spawn");
        if (spawnType == null || spawnType.length() < 1)
        {
            setName(self, "Mangled spawner. spawnType is " + spawnType + ".");
            return SCRIPT_CONTINUE;
        }
        location locTest = getLocation(self);
        createMob(spawnType, locTest, self);
        return SCRIPT_CONTINUE;
    }
    public void createMob(String spawnType, location locLocation, obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "spawned"))
        {
            return;
        }
        float spawnerYaw = getYaw(self);
        float minRespawnTime = 9.0f;
        float maxRespawnTime = 19.0f;
        float respawnTime = rand(minRespawnTime, maxRespawnTime);
        obj_id objMob = create.object(spawnType, locLocation);
        if (!isIdValid(objMob))
        {
            setName(self, "BAD MOB OF TYPE " + spawnType);
            return;
        }
        int stringCheck = spawnType.indexOf("kreetle");
        if (stringCheck > -1)
        {
            ai_lib.setDefaultCalmBehavior(objMob, ai_lib.BEHAVIOR_LOITER);
        }
        else 
        {
            ai_lib.setDefaultCalmBehavior(objMob, ai_lib.BEHAVIOR_SENTINEL);
        }
        setObjVar(objMob, "objParent", self);
        setObjVar(objMob, "fltRespawnTime", respawnTime);
        attachScript(objMob, "systems.spawning.spawned_tracker");
        setYaw(objMob, spawnerYaw);
        utils.setScriptVar(self, "spawned", objMob);
        if (spawnType.equals("c_3po"))
        {
            attachScript(objMob, "conversation.fan_faire_pgc_c3po");
        }
        else 
        {
            attachScript(objMob, "quest.task.pgc.fan_faire_demo.quest_pinata");
        }
        return;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "spawned");
        messageTo(self, "doSpawnEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException
    {
        String disableSpawners = getConfigSetting("GameServer", "disableAreaSpawners");
        if (disableSpawners == null)
        {
            return true;
        }
        if (disableSpawners.equals("true") || disableSpawners.equals("1"))
        {
            return false;
        }
        return true;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "spawned"))
        {
            obj_id spawned = utils.getObjIdScriptVar(self, "spawned");
            messageTo(spawned, "selfDestruct", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
}
