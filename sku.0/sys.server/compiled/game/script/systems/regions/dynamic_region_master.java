package script.systems.regions;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.regions;
import script.library.utils;

public class dynamic_region_master extends script.base_script
{
    public dynamic_region_master()
    {
    }
    public static final String BIRTH = "dynamic_region.birth";
    public static final String PLANET = "dynamic_region.planet";
    public static final String NAME = "dynamic_region.name";
    public static final String DURATION = "dynamic_region.duration";
    public static final String MAX_DURATION = "dynamic_region.maxDuration";
    public static final String CREATURE_LIST = "dynamic_region.creatureList";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, BIRTH))
        {
            messageTo(self, "destroySelf", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "heartbeat", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        int currentEpoch = getCalendarTime();
        int birth = getIntObjVar(self, BIRTH);
        int maxDuration = getIntObjVar(self, MAX_DURATION);
        obj_id[] myCreatures = getObjIdArrayObjVar(self, CREATURE_LIST);
        if (currentEpoch < (birth + maxDuration * 60) && myCreatures != null && myCreatures.length > 0)
        {
            for (int i = 0; i < myCreatures.length; ++i)
            {
                if (isIdValid(myCreatures[i]) && ai_lib.isInCombat(myCreatures[i]))
                {
                    messageTo(self, "destroySelf", null, 60.0f, false);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (myCreatures != null && myCreatures.length > 0)
        {
            for (int i = 0; i < myCreatures.length; ++i)
            {
                if (isIdValid(myCreatures[i]))
                {
                    destroyObject(myCreatures[i]);
                }
            }
        }
        String regionName;
        String regionPlanet;
        regionName = getStringObjVar(self, NAME);
        regionPlanet = getStringObjVar(self, PLANET);
        region regionSelf = getRegion(regionPlanet, regionName);
        deleteRegion(regionSelf);
        return SCRIPT_CONTINUE;
    }
    public int heartbeat(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, DURATION))
        {
            messageTo(self, "destroySelf", null, 0.0f, false);
        }
        int duration = getIntObjVar(self, DURATION);
        int birth = getIntObjVar(self, BIRTH);
        if (duration == 0 || birth == 0)
        {
            messageTo(self, "destroySelf", null, 0.0f, false);
        }
        int currentEpoch = getCalendarTime();
        if (currentEpoch > (birth + (duration * 60)))
        {
            messageTo(self, "destroySelf", null, 0.0f, false);
        }
        else 
        {
            int totalTimeSecondsLeft = (birth + (duration * 60)) - currentEpoch;
            if (totalTimeSecondsLeft > 60)
            {
                messageTo(self, "heartbeat", null, 60.0f, false);
            }
            else 
            {
                messageTo(self, "heartbeat", null, totalTimeSecondsLeft, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDynamicSpawnRegionCreated(obj_id self, obj_id regionObject, String spawnDatatable, float x, float y, float z) throws InterruptedException
    {
        LOG("DynamicSpawn", "Hit OnDynamicSpawnRegionCreated with paramers: " + regionObject + " " + spawnDatatable + " " + x + " " + y + " " + z);
        if (spawnDatatable == null || spawnDatatable.equals(""))
        {
            LOG("DynamicSpawn", "Spawn table was empty");
            return SCRIPT_CONTINUE;
        }
        if (!dataTableOpen(spawnDatatable))
        {
            LOG("DynamicSpawn", "Failed to open table " + spawnDatatable);
            return SCRIPT_CONTINUE;
        }
        Vector creatureList = new Vector();
        creatureList.setSize(0);
        int numRows = dataTableGetNumRows(spawnDatatable);
        LOG("DynamicSpawn", "Datatable had " + numRows + " number of rows.");
        for (int i = 0; i < numRows; ++i)
        {
            String templateName = dataTableGetString(spawnDatatable, i, 0);
            float cx = dataTableGetFloat(spawnDatatable, i, 1);
            float cy = dataTableGetFloat(spawnDatatable, i, 2);
            float cz = dataTableGetFloat(spawnDatatable, i, 3);
            location creatureLoc = new location();
            creatureLoc.x = x + cx;
            creatureLoc.y = y + cy;
            creatureLoc.z = z + cz;
            obj_id newCreature = create.object(templateName, creatureLoc);
            utils.addElement(creatureList, newCreature);
        }
        setObjVar(self, CREATURE_LIST, creatureList);
        return SCRIPT_CONTINUE;
    }
}
