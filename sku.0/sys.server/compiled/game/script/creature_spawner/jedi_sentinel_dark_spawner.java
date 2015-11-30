package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.create;
import script.library.ai_lib;
import script.library.utils;

public class jedi_sentinel_dark_spawner extends script.base_script
{
    public jedi_sentinel_dark_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnCreatures(obj_id self) throws InterruptedException
    {
        String whatToSpawn = "dark_jedi_sentinel";
        location here = getLocation(self);
        obj_id spawned = create.object(whatToSpawn, here);
        attachScript(spawned, "theme_park.npc_died.npc_died");
        setObjVar(spawned, "spawner", self);
        setObjVar(spawned, "useOnIncapTrigger", true);
        float spawnedYaw = getYaw(self);
        setYaw(spawned, spawnedYaw);
        
        {
            ai_lib.setDefaultCalmBehavior(spawned, ai_lib.BEHAVIOR_SENTINEL);
        }
        return;
    }
    public int npcDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
}
