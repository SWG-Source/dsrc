package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;

public class static_guard extends script.base_script
{
    public static_guard()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "cleanup"))
        {
            obj_id top = getTopMostContainer(self);
            String datatable = getStringObjVar(top, "spawn_table");
            obj_id mom = getObjIdObjVar(self, "mom");
            if (mom == null)
            {
                return SCRIPT_OVERRIDE;
            }
            int spawnNum = getIntObjVar(self, "spawn_number");
            int respawnTime = dataTableGetInt(datatable, spawnNum, "respawn_time");
            if (respawnTime == 0)
            {
                respawnTime = 30;
            }
            dictionary info = new dictionary();
            info.put("spawnNumber", spawnNum);
            info.put("spawnMob", self);
            messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public boolean isWithinHeightDifference(location selfLoc, location targetLoc) throws InterruptedException
    {
        float heightDifference = Math.abs(targetLoc.y - selfLoc.y);
        if (heightDifference < 6)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/patrol_guard/" + section, message);
        }
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
