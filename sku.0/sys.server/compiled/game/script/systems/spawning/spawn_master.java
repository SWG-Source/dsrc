package script.systems.spawning;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.regions;
import java.util.Set;
import java.util.Iterator;
import java.lang.Integer;

public class spawn_master extends script.systems.spawning.spawn_base
{
    public spawn_master()
    {
    }
    public int OnUniverseComplete(obj_id self) throws InterruptedException
    {
        String strPlanet = getNameForPlanetObject(self);
        if (!hasObjVar(self, "boolSpawnerIsOn"))
        {
            setObjVar(self, "boolSpawnerIsOn", true);
        }
        if (!hasObjVar(self, "intMaxPlanetSpawnCount"))
        {
            setObjVar(self, "intMaxPlanetSpawnCount", 10000);
        }
        else 
        {
            int intSpawnLimit = getIntObjVar(self, "intMaxPlanetSpawnCount");
            if (intSpawnLimit > 10000)
            {
                setObjVar(self, "intMaxPlanetSpawnCount", 10000);
            }
        }
        preLoadSpawnDataTables(self);
        setObjVar(self, "intCurrentPlanetSpawnCount", 0);
        setObjVar(self, "intMinSpawnDelay", SPAWN_PLAYER_DELAY_MIN);
        setObjVar(self, "intMaxSpawnDelay", SPAWN_PLAYER_DELAY_MAX);
        String strConfigSetting = getConfigSetting("GameServer", "spawningInitialState");
        if (strConfigSetting != null)
        {
            if (strConfigSetting.equals("on"))
            {
                setObjVar(self, "boolSpawnerIsOn", true);
            }
            else if (strConfigSetting.equals("off"))
            {
                setObjVar(self, "boolSpawnerIsOn", false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String strPlanet = getNameForPlanetObject(self);
        if (!hasObjVar(self, "boolSpawnerIsOn"))
        {
            setObjVar(self, "boolSpawnerIsOn", true);
        }
        if (!hasObjVar(self, "intMaxPlanetSpawnCount"))
        {
            setObjVar(self, "intMaxPlanetSpawnCount", 10000);
        }
        else 
        {
            int intSpawnLimit = getIntObjVar(self, "intMaxPlanetSpawnCount");
            if (intSpawnLimit > 10000)
            {
                setObjVar(self, "intMaxPlanetSpawnCount", 10000);
            }
        }
        preLoadSpawnDataTables(self);
        setObjVar(self, "intCurrentPlanetSpawnCount", 0);
        setObjVar(self, "intMinSpawnDelay", SPAWN_PLAYER_DELAY_MIN);
        setObjVar(self, "intMaxSpawnDelay", SPAWN_PLAYER_DELAY_MAX);
        String strConfigSetting = getConfigSetting("GameServer", "spawningInitialState");
        if (strConfigSetting != null)
        {
            if (strConfigSetting.equals("on"))
            {
                setObjVar(self, "boolSpawnerIsOn", true);
            }
            else if (strConfigSetting.equals("off"))
            {
                setObjVar(self, "boolSpawnerIsOn", false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
