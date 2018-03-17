package script.systems.spawning;

import script.dictionary;
import script.obj_id;

public class spawn_template extends script.systems.spawning.spawn_base
{
    public spawn_template()
    {
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int template_Cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "intCleaningUp", 1);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "intPersistent"))
        {
            destroyObject(self);
        }
        else 
        {
            setObjVar(self, "intUnloaded", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id objMasterSpawner;
        dictionary dctParams = new dictionary();
        String strTemplate;
        String strLairType;
        String strBuildingType;
        strTemplate = getTemplateName(self);
        if (hasObjVar(self, "spawning.lairType"))
        {
            strLairType = getStringObjVar(self, "spawning.lairType");
            if (!strLairType.equals(""))
            {
                strTemplate = strTemplate + "." + strLairType;
            }
        }
        if (hasObjVar(self, "spawning.buildingTrackingType"))
        {
            strBuildingType = getStringObjVar(self, "spawning.buildingTrackingType");
            if (!strBuildingType.equals(""))
            {
                strTemplate = strTemplate + "." + strBuildingType;
            }
        }
        String strRegionName = getStringObjVar(self, "strRegionName");
        String strPlanet = getStringObjVar(self, "strPlanet");
        debugServerConsoleMsg(self, "Template being cleaned up is " + strTemplate);
        objMasterSpawner = getObjIdObjVar(self, "objMasterSpawner");
        if (strTemplate != null)
        {
            dctParams.put("strTemplate", strTemplate);
        }
        if (strRegionName != null)
        {
            dctParams.put("strRegionName", strRegionName);
        }
        if (strPlanet != null)
        {
            dctParams.put("strPlanet", strPlanet);
        }
        debugServerConsoleMsg(self, "Sending Message");
        messageTo(objMasterSpawner, "template_Destroyed", dctParams, 0, false);
        debugServerConsoleMsg(self, "Message Sent");
        return SCRIPT_CONTINUE;
    }
    public boolean checkIfInCombat(obj_id objLair) throws InterruptedException
    {
        int intNumberOfMobiles = getIntObjVar(objLair, "npc_lair.numberOfMobiles");
        for (int intI = 0; intI < intNumberOfMobiles; intI++)
        {
            if (getState(getObjIdObjVar(objLair, "npc_lair.mobile." + intNumberOfMobiles), STATE_COMBAT) > 0)
            {
                return true;
            }
        }
        return false;
    }
}
