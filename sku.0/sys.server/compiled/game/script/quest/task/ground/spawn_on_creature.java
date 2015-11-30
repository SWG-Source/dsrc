package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.quest.task.ground.spawn;

public class spawn_on_creature extends script.base_script
{
    public spawn_on_creature()
    {
    }
    public static final int maxRetries = 5;
    public static final String retryCountName = "retryCount";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put(retryCountName, 0);
        messageTo(self, "messageSpawnCreatureCleanup", params, spawn.cleanupTime, false);
        return SCRIPT_CONTINUE;
    }
    public int messageSpawnCreatureCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            LOG("QUEST_SPAWN_LOG", "Creature is dead!");
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            int retryCount = params.getInt(retryCountName) + 1;
            if (retryCount <= maxRetries)
            {
                LOG("QUEST_SPAWN_LOG", "Creature in combat - trying again. retryCount = " + retryCount + "/" + maxRetries);
                params.put(retryCountName, retryCount);
                messageTo(self, "messageSpawnCreatureCleanup", params, spawn.cleanupRetryTime, false);
            }
            else 
            {
                LOG("QUEST_SPAWN_LOG", "Creature in combat - no more retries left.");
            }
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
