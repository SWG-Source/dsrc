package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.spawning;
import script.library.utils;

public class combat_mine_spawner extends script.base_script
{
    public combat_mine_spawner()
    {
    }
    public static final String dataTable = "datatables/combat/npc_landmines.iff";
    public static final boolean doLogging = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "handleSpawnMines", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "handleSpawnMines", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnMines(obj_id self, dictionary params) throws InterruptedException
    {
        if (!canSpawnNewMine(self))
        {
            return SCRIPT_CONTINUE;
        }
        String mineType = getStringObjVar(self, "mineField.mineType");
        float spawnRadius = getFloatObjVar(self, "mineField.fieldRadius");
        location spawnPoint = spawning.getRandomLocationInCircle(getLocation(self), spawnRadius);
        obj_id mine = createMine(mineType, spawnPoint);
        doLogging("handleSpawnMines", "Spawned Mine(" + getName(mine) + "/" + mine + ")");
        doLogging("handleSpawnMines", "Current count is " + getCurrentMineCount(self));
        messageTo(self, "handleSpawnMines", null, getRespawn(self), false);
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnNewMine(obj_id self) throws InterruptedException
    {
        return (getCurrentMineCount(self) < getIntObjVar(self, "mineField.mineCount"));
    }
    public int getRespawn(obj_id self) throws InterruptedException
    {
        return getIntObjVar(self, "mineField.mineRespawn");
    }
    public obj_id createMine(String mineType, location spawnPoint) throws InterruptedException
    {
        String mineTemplate = dataTableGetString(dataTable, mineType, "mineTemplate");
        obj_id mine = createObject(mineTemplate, spawnPoint);
        removeAllObjVars(mine);
        detachAllScripts(mine);
        setObjVar(mine, "mineType", mineType);
        attachScript(mine, "systems.combat.combat_mine");
        setObjVar(mine, "parentSpawner", getSelf());
        setObjVar(mine, "targetFlag", "targetFlag.playerAndPet");
        incrimentMineCount(getSelf());
        return mine;
    }
    public int getCurrentMineCount(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "currentMineCount"))
        {
            return utils.getIntScriptVar(self, "currentMineCount");
        }
        else 
        {
            return 0;
        }
    }
    public int incrimentMineCount(obj_id self) throws InterruptedException
    {
        int current = getCurrentMineCount(self);
        current += 1;
        utils.setScriptVar(self, "currentMineCount", current);
        return getCurrentMineCount(self);
    }
    public int decrimentMineCount(obj_id self) throws InterruptedException
    {
        int current = getCurrentMineCount(self);
        if (current > 0)
        {
            current -= 1;
        }
        utils.setScriptVar(self, "currentMineCount", current);
        return getCurrentMineCount(self);
    }
    public int handleMineRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        doLogging("handleMineRespawn", "Mine respawn call recieved");
        decrimentMineCount(self);
        messageTo(self, "handleSpawnMines", null, getRespawn(self), false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/combat_mine_spawner/" + section, message);
        }
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException
    {
        String disableSpawners = getConfigSetting("GameServer", "disableMineFields");
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
