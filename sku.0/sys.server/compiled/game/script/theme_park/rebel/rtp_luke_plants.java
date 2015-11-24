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

public class rtp_luke_plants extends script.base_script
{
    public rtp_luke_plants()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "dyingPlantSpawned"))
        {
            removeObjVar(self, "dyingPlantSpawned");
        }
        if (hasObjVar(self, "healthyPlantSpawned"))
        {
            removeObjVar(self, "healthyPlantSpawned");
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doDyingPlantEvent", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "dyingPlantSpawned"))
        {
            removeObjVar(self, "dyingPlantSpawned");
        }
        if (hasObjVar(self, "healthyPlantSpawned"))
        {
            removeObjVar(self, "healthyPlantSpawned");
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doDyingPlantEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int doDyingPlantEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "dyingPlantSpawned"))
        {
            String dyingPlant = "object/tangible/quest/rebel/rtp_luke_dying_plant.iff";
            location here = getLocation(self);
            obj_id objTemplate = createObject(dyingPlant, here);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(self, "dyingPlantSpawned", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int doHealthyPlantEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "healthyPlantSpawned"))
        {
            String healedPlant = "object/tangible/quest/rebel/rtp_luke_healed_plant.iff";
            location here = getLocation(self);
            obj_id objTemplate = createObject(healedPlant, here);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(self, "healthyPlantSpawned", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "healthyPlantSpawned"))
        {
            removeObjVar(self, "healthyPlantSpawned");
            messageTo(self, "doDyingPlantEvent", null, 0, false);
        }
        else if (hasObjVar(self, "dyingPlantSpawned"))
        {
            removeObjVar(self, "dyingPlantSpawned");
            messageTo(self, "doHealthyPlantEvent", null, 0, false);
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
