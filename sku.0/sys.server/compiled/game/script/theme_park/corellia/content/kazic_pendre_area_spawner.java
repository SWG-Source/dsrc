package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.create;

public class kazic_pendre_area_spawner extends script.base_script
{
    public kazic_pendre_area_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        String whatToSpawn = "corellia_chirq_kazic_pendre";
        float spawnYaw = -145;
        obj_id cell = getCellId(self, "storage1");
        location spawnLoc = new location(-10.7f, -20.0f, 8.8f, "corellia", cell);
        int choice = rand(0, 1);
        if (choice == 1)
        {
            spawnYaw = 52;
            cell = getCellId(self, "storage2");
            spawnLoc = new location(-8.3f, -20.0f, 66.1f, "corellia", cell);
        }
        float minSpawnTime = 210.0f;
        float maxSpawnTime = 300.0f;
        float respawnTime = rand(minSpawnTime, maxSpawnTime);
        obj_id npc = create.object(whatToSpawn, spawnLoc);
        if (isIdValid(npc))
        {
            ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
            setObjVar(npc, "objParent", self);
            setObjVar(npc, "fltRespawnTime", respawnTime);
            attachScript(npc, "systems.spawning.spawned_tracker");
            setYaw(npc, spawnYaw);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
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
}
