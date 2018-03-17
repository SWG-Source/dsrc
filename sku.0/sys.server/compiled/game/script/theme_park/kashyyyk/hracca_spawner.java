package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.space_utils;
import script.library.space_dungeon;
import script.library.spawning;
import script.library.create;

public class hracca_spawner extends script.base_script
{
    public hracca_spawner()
    {
    }
    public int doHraccaSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int maxPop = 1;
        if (hasObjVar(self, "intSpawnCount"))
        {
            maxPop = getIntObjVar(self, "intSpawnCount");
        }
        if (hasObjVar(self, "myCreations"))
        {
            Vector theList = getResizeableObjIdArrayObjVar(self, "myCreations");
            if (theList.size() >= maxPop)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (params.containsKey("controllerObject"))
        {
            obj_id controllerObject = params.getObjId("controllerObject");
            utils.setScriptVar(self, "controllerObject", controllerObject);
        }
        location locTest = getLocation(self);
        String spawnType = getStringObjVar(self, "spawnType");
        String whatToSpawn = getSpawnType(spawnType);
        if ((whatToSpawn == null))
        {
            return SCRIPT_CONTINUE;
        }
        if (params.containsKey("spawnChissOnly") && spawnType.equals("chiss"))
        {
            createMob(whatToSpawn, locTest, self);
        }
        else if (params.containsKey("spawnKkorrwrot") && spawnType.equals("kkorrwrot"))
        {
            createMob(whatToSpawn, locTest, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void createMob(String whatToSpawn, location here, obj_id self) throws InterruptedException
    {
        obj_id npc = create.object(whatToSpawn, here);
        if (!isIdValid(npc))
        {
            setName(self, "BAD MOB OF TYPE " + whatToSpawn);
            return;
        }
        int numSpawned = 1;
        if (!hasObjVar(self, "myCreations"))
        {
            Vector myCreations = new Vector();
            myCreations.setSize(0);
            utils.addElement(myCreations, npc);
            if (myCreations != null && myCreations.size() > 0)
            {
                setObjVar(self, "myCreations", myCreations);
            }
        }
        else 
        {
            Vector spawnedList = getResizeableObjIdArrayObjVar(self, "myCreations");
            spawnedList.add(npc);
            if (spawnedList != null && spawnedList.size() > 0)
            {
                setObjVar(self, "myCreations", spawnedList);
                numSpawned = spawnedList.size();
            }
        }
        ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_LOITER);
        setObjVar(npc, "objParent", self);
        attachScript(npc, "theme_park.kashyyyk.hracca_spawned_tracker");
        int maxPop = 1;
        if (hasObjVar(self, "intSpawnCount"))
        {
            maxPop = getIntObjVar(self, "intSpawnCount");
        }
        if (numSpawned < maxPop)
        {
            messageTo(self, "doHraccaSpawnEvent", null, 2, false);
        }
        if (utils.hasScriptVar(self, "controllerObject") && whatToSpawn.equals("ep3_hracca_chiss_poacher_hunter"))
        {
            obj_id controllerObject = utils.getObjIdScriptVar(self, "controllerObject");
            if (isIdValid(controllerObject))
            {
                dictionary webster = new dictionary();
                webster.put("chissPoacher", npc);
                messageTo(controllerObject, "registerChissPoacher", webster, 1, false);
            }
            String fogTemplate = "object/static/particle/pt_fog_kashyyyk_hracca_chiss_fog.iff";
            obj_id fogParticleObj = createObject(fogTemplate, here);
            setObjVar(self, "fogParticleObj", fogParticleObj);
        }
        return;
    }
    public String getSpawnType(String spawnType) throws InterruptedException
    {
        String whatToSpawn = "ep3_hracca_chiss_poacher_hunter";
        if (spawnType.equals("kkorrwrot"))
        {
            whatToSpawn = "ep3_hracca_kkorrwrot";
        }
        return whatToSpawn;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.containsKey("deadNpc"))
        {
            obj_id deadNpc = params.getObjId("deadNpc");
            if (isIdValid(deadNpc))
            {
                if (hasObjVar(self, "fogParticleObj"))
                {
                    obj_id fogParticleObj = getObjIdObjVar(self, "fogParticleObj");
                    if (isIdValid(fogParticleObj))
                    {
                        location fogLoc = getLocation(fogParticleObj);
                        obj_id controllerObject = utils.getObjIdScriptVar(self, "controllerObject");
                        if (isIdValid(controllerObject))
                        {
                            obj_id[] playersInInstance = space_dungeon.getPlayersInInstance(controllerObject);
                            if (playersInInstance != null && playersInInstance.length > 0)
                            {
                                playClientEffectLoc(playersInInstance, "appearance/pt_miasma_fog_chiss_orange_end.prt", fogLoc, 0.0f);
                                messageTo(self, "handleDeleteChissFog", null, 2, false);
                            }
                        }
                    }
                }
                String spawnType = getStringObjVar(self, "spawnType");
                if (utils.hasScriptVar(self, "controllerObject") && spawnType.equals("chiss"))
                {
                    obj_id controllerObject = utils.getObjIdScriptVar(self, "controllerObject");
                    if (isIdValid(controllerObject))
                    {
                        dictionary webster = new dictionary();
                        webster.put("deadChissPoacher", deadNpc);
                        messageTo(controllerObject, "handleChissPoacherDestroyed", webster, 1, false);
                    }
                }
            }
        }
        else if (params.containsKey("destroyedNpc"))
        {
            obj_id destroyedNpc = params.getObjId("destroyedNpc");
            if (hasObjVar(self, "myCreations"))
            {
                Vector spawnedList = getResizeableObjIdArrayObjVar(self, "myCreations");
                if (spawnedList.contains(destroyedNpc))
                {
                    spawnedList.remove(destroyedNpc);
                    if (spawnedList != null && spawnedList.size() > 0)
                    {
                        setObjVar(self, "myCreations", spawnedList);
                    }
                    else 
                    {
                        removeObjVar(self, "myCreations");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeleteChissFog(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id fogParticleObj = getObjIdObjVar(self, "fogParticleObj");
        if (isIdValid(fogParticleObj))
        {
            destroyObject(fogParticleObj);
        }
        removeObjVar(self, "fogParticleObj");
        return SCRIPT_CONTINUE;
    }
    public int doCleanupEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "myCreations"))
        {
            return SCRIPT_CONTINUE;
        }
        Vector spawnedList = getResizeableObjIdArrayObjVar(self, "myCreations");
        for (int i = 0; i < spawnedList.size(); i++)
        {
            obj_id spawnedNpc = ((obj_id)spawnedList.get(i));
            if (isIdValid(spawnedNpc))
            {
                setObjVar(spawnedNpc, "cleaningUp", true);
                destroyObject(spawnedNpc);
            }
        }
        if (hasObjVar(self, "fogParticleObj"))
        {
            obj_id fogParticleObj = getObjIdObjVar(self, "fogParticleObj");
            if (isIdValid(fogParticleObj))
            {
                destroyObject(fogParticleObj);
                removeObjVar(self, "fogParticleObj");
            }
        }
        removeObjVar(self, "myCreations");
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
