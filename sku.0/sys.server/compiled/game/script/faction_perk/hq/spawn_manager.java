package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;

public class spawn_manager extends script.base_script
{
    public spawn_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        hq.loadInteriorHqSpawns(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        hq.cleanupHqSpawns(self);
        return SCRIPT_CONTINUE;
    }
    public int clearSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        hq.cleanupHqSpawns(self);
        return SCRIPT_CONTINUE;
    }
    public int doSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        hq.loadInteriorHqSpawns(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("hq", "(" + self + ") received handleTheaterComplete...");
        LOG("hq", "handleTheaterComplete: attempting to load exterior spawns...");
        return SCRIPT_CONTINUE;
    }
    public int handleDefenderRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("hq", "faction_perk.hq.spawn_manager::handleDefenderRespawn	--	ENTERED HANDLER. Figured out self. Self is: " + self);
        LOG("hq", "faction_perk.hq.spawn_manager::handleDefenderRespawn --  attempting to clear all spawns...");
        LOG("hq", "faction_perk.hq.spawn_manager::handleDefenderRespawn --  attempting to restart all spawns...");
        return SCRIPT_CONTINUE;
    }
}
