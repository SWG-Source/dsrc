package script.theme_park.rebel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.spawning;
import script.library.create;

public class rtp_leia_crime_scene extends script.base_script
{
    public rtp_leia_crime_scene()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "stormtrooperHelmetSpawned"))
        {
            removeObjVar(self, "stormtrooperHelmetSpawned");
        }
        if (hasObjVar(self, "rebelHelmetSpawned"))
        {
            removeObjVar(self, "rebelHelmetSpawned");
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doRebelHelmetEvent", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "stormtrooperHelmetSpawned"))
        {
            removeObjVar(self, "stormtrooperHelmetSpawned");
        }
        if (hasObjVar(self, "rebelHelmetSpawned"))
        {
            removeObjVar(self, "rebelHelmetSpawned");
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doRebelHelmetEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int doStormtrooperHelmetEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "stormtrooperHelmetSpawned"))
        {
            String stormtrooperHelmet = "object/tangible/quest/rebel/rtp_leia_stormtrooper_helmet.iff";
            location here = getLocation(self);
            here.y = here.y - 1.5f;
            obj_id objTemplate = createObject(stormtrooperHelmet, here);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(self, "stormtrooperHelmetSpawned", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int doRebelHelmetEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "rebelHelmetSpawned"))
        {
            String rebelHelmet = "object/tangible/quest/rebel/rtp_leia_rebel_helmet.iff";
            location here = getLocation(self);
            here.y = here.y - 1.5f;
            obj_id objTemplate = createObject(rebelHelmet, here);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(self, "rebelHelmetSpawned", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "rebelHelmetSpawned"))
        {
            removeObjVar(self, "rebelHelmetSpawned");
            messageTo(self, "doStormtrooperHelmetEvent", null, 0, false);
        }
        else if (hasObjVar(self, "stormtrooperHelmetSpawned"))
        {
            removeObjVar(self, "stormtrooperHelmetSpawned");
            messageTo(self, "doRebelHelmetEvent", null, 0, false);
        }
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
