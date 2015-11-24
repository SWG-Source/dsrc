package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ship_ai;
import script.quest.task.ground.spawn;

public class spawn_on_ship extends script.base_script
{
    public spawn_on_ship()
    {
    }
    public static final int maxRetries = 5;
    public static final String retryCountName = "retryCount";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "pendingWarp", null, spawn.cleanupTime, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnAttackPlayerShip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id playerShip = params.getObjId("player");
        LOG("QUEST_SPAWN_LOG", "Attack! " + playerShip + ":" + self);
        ship_ai.spaceAttack(self, playerShip);
        return SCRIPT_CONTINUE;
    }
    public int pendingWarp(obj_id self, dictionary params) throws InterruptedException
    {
        if (ship_ai.spaceIsInCombat(self))
        {
            int retryCount = params.getInt(retryCountName) + 1;
            if (retryCount <= maxRetries)
            {
                LOG("QUEST_SPAWN_LOG", "Ship in combat - trying again. retryCount = " + retryCount + "/" + maxRetries);
                params.put(retryCountName, retryCount);
                messageTo(self, "pendingWarp", params, spawn.cleanupRetryTime, false);
            }
            else 
            {
                LOG("QUEST_SPAWN_LOG", "Ship in combat - no more retries left.");
            }
            return SCRIPT_CONTINUE;
        }
        LOG("QUEST_SPAWN_LOG", "Destroying self = " + self);
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
