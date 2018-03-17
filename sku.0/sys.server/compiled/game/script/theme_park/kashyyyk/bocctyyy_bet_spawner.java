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
import script.library.spawning;
import script.library.create;

public class bocctyyy_bet_spawner extends script.base_script
{
    public bocctyyy_bet_spawner()
    {
    }
    public int doBocctyyySpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || !params.containsKey("questName"))
        {
            return SCRIPT_CONTINUE;
        }
        String questName = params.getString("questName");
        float radius = 3;
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
        location locTest = spawning.getRandomLocationInCircle(getLocation(self), radius);
        createMob(questName, locTest, radius, self);
        return SCRIPT_CONTINUE;
    }
    public void createMob(String questName, location here, float radius, obj_id self) throws InterruptedException
    {
        String spawnType = getSpawnType(questName);
        if (spawnType == null || spawnType.equals(""))
        {
            LOG("bocctyyy_bet_spawner", "spawnType was null");
        }
        obj_id npc = create.object(spawnType, here);
        if (!isIdValid(npc))
        {
            setName(self, "BAD MOB OF TYPE " + spawnType);
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
        attachScript(npc, "theme_park.kashyyyk.bocctyyy_bet_spawned_tracker");
        int maxPop = 1;
        if (hasObjVar(self, "intSpawnCount"))
        {
            maxPop = getIntObjVar(self, "intSpawnCount");
        }
        if (numSpawned < maxPop)
        {
            dictionary webster = new dictionary();
            webster.put("questName", questName);
            messageTo(self, "doBocctyyySpawnEvent", webster, 2, false);
        }
        return;
    }
    public String getSpawnType(String questName) throws InterruptedException
    {
        String spawnType = "";
        if (questName.equals("ep3_hunt_sordaan_uller_bet"))
        {
            spawnType = "ep3_etyyy_uller_warhoof";
        }
        else if (questName.equals("ep3_hunt_sordaan_walluga_bet"))
        {
            spawnType = "ep3_etyyy_walluga_frenzied";
        }
        else if (questName.equals("ep3_hunt_sordaan_mouf_bet"))
        {
            spawnType = "ep3_etyyy_mouf_roarlord";
        }
        else if (questName.equals("ep3_hunt_sordaan_webweaver_bet"))
        {
            spawnType = "ep3_etyyy_webweaver_spiker";
        }
        return spawnType;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadNpc = params.getObjId("deadNpc");
        if (!hasObjVar(self, "myCreations"))
        {
            return SCRIPT_CONTINUE;
        }
        Vector spawnedList = getResizeableObjIdArrayObjVar(self, "myCreations");
        if (spawnedList.contains(deadNpc))
        {
            spawnedList.remove(deadNpc);
            if (spawnedList != null && spawnedList.size() > 0)
            {
                setObjVar(self, "myCreations", spawnedList);
            }
            else 
            {
                removeObjVar(self, "myCreations");
            }
        }
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
