package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.player_structure;

public class battlefield_object extends script.base_script
{
    public battlefield_object()
    {
    }
    public int OnFollowMoving(obj_id self, obj_id target) throws InterruptedException
    {
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.battlefield_object::OnIncapacitated -- " + self + "   " + killer);
        if (battlefield.isBattlefieldSpawned(self))
        {
            region bf = battlefield.getBattlefield(self);
            obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
            battlefield.registerBattlefieldKill(killer, self, master_object);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (battlefield.isBattlefieldSpawned(self))
        {
            obj_id master_object = getObjIdObjVar(self, battlefield.VAR_SPAWNED_BATTLEFIELD);
            if (master_object == null)
            {
                LOG("LOG_CHANNEL", "battlefield.battlefield_object::OnDestroy (" + self + ") -- Battlefield object is null.");
                return SCRIPT_CONTINUE;
            }
            if (battlefield.isBattlefieldActive(master_object))
            {
                obj_id spawner = getObjIdObjVar(self, battlefield.VAR_SPAWNED_BY);
                if (spawner == null)
                {
                    LOG("LOG_CHANNEL", "battlefield.battlefield_object::OnDestroy (" + self + ") -- Spawned_by is null.");
                }
                else 
                {
                    if (spawner.isLoaded())
                    {
                        if (hasObjVar(spawner, battlefield.VAR_SPAWNER_CURRENT_POPULATION))
                        {
                            int population = getIntObjVar(spawner, battlefield.VAR_SPAWNER_CURRENT_POPULATION);
                            setObjVar(spawner, battlefield.VAR_SPAWNER_CURRENT_POPULATION, population - 1);
                            LOG("LOG_CHANNEL", "population ->" + population);
                        }
                    }
                    else 
                    {
                        LOG("LOG_CHANNEL", "battlefield.battlefield_object::OnDestroy -- spawner " + spawner + " is not loaded.");
                    }
                }
                battlefield.removeFromBattlefieldObjectList(master_object, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDestroyBattlefieldObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (player_structure.isBuilding(self))
        {
            obj_id[] players = player_structure.getPlayersInBuilding(self);
            if (players != null)
            {
                for (int i = 0; i < players.length; i++)
                {
                    expelFromBuilding(players[i]);
                }
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
