package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;

public class bocctyyy_bet_controller extends script.base_script
{
    public bocctyyy_bet_controller()
    {
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, space_dungeon.VAR_QUEST_TYPE))
        {
            LOG("bocctyyy_path", "Dungeon controller did not have quest type objvar.");
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_dungeon.VAR_QUEST_TYPE);
        if (questName == null || questName.equals("") || questName.equals("none"))
        {
            LOG("bocctyyy_path", "Dungeon controller: quest name was null, empty, or none.");
            return SCRIPT_CONTINUE;
        }
        dictionary webster = new dictionary();
        webster.put("questName", questName);
        obj_id[] spawners = space_dungeon.getRegisteredObjects(self);
        if (spawners != null && spawners.length > 0)
        {
            for (int i = 0; i < spawners.length; i++)
            {
                obj_id spawner = spawners[i];
                messageTo(spawner, "doBocctyyySpawnEvent", webster, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSpaceDungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] spawners = space_dungeon.getRegisteredObjects(self);
        if (spawners != null && spawners.length > 0)
        {
            for (int i = 0; i < spawners.length; i++)
            {
                obj_id spawner = spawners[i];
                messageTo(spawner, "doCleanupEvent", null, 1, false);
            }
        }
        if (hasObjVar(self, space_dungeon.VAR_QUEST_TYPE))
        {
            removeObjVar(self, space_dungeon.VAR_QUEST_TYPE);
        }
        return SCRIPT_CONTINUE;
    }
}
