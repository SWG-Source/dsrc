package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class quest_creature extends script.base_script
{
    public quest_creature()
    {
    }
    public static final String VAR_SPAWNER_CURRENT_POPULATION = "quest_spawner.current_pop";
    public static final String VAR_SPAWNED_BY = "quest_spawner.spawned_by";
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        LOG("quests", "quest_creature.OnIncapacitated(" + self + ", " + attacker + ") - notifying spawner");
        notifySpawner();
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        LOG("quests", "quest_creature.OnDestroy(" + self + ") - notifying spawner");
        if (!isIncapacitated(self))
        {
            notifySpawner();
        }
        return SCRIPT_CONTINUE;
    }
    public void notifySpawner() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id spawner = getObjIdObjVar(self, VAR_SPAWNED_BY);
        if (isIdValid(spawner))
        {
            if (spawner.isLoaded())
            {
                if (hasObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION))
                {
                    int population = getIntObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION);
                    setObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION, population - 1);
                }
                else 
                {
                    LOG("quests", "quest_creature.notifySpawner(" + self + ") - spawner doesn't have objvar " + VAR_SPAWNER_CURRENT_POPULATION);
                }
            }
            else 
            {
                LOG("quests", "quest_creature.notifySpawner(" + self + ") - spawner " + spawner + " isn't loaded");
            }
        }
        else 
        {
            LOG("quests", "quest_creature.notifySpawner(" + self + ") - invalid spawner id");
        }
    }
}
