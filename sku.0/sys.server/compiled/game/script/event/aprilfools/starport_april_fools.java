package script.event.aprilfools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;

public class starport_april_fools extends script.base_script
{
    public starport_april_fools()
    {
    }
    public static final String CITY_OBJVAR = "aprilFools.city";
    public static final String LAST_SPAWN = "aprilFools.lastSpawn";
    public static final String CREATURE_LIST = "aprilFools.creatures";
    public static final String SPAWN_DATATABLE = "datatables/event/aprilfools/aprilfools09.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, LAST_SPAWN, 0);
        messageTo(self, "heartbeat", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, LAST_SPAWN, 0);
        messageTo(self, "heartbeat", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int heartbeat(obj_id self, dictionary params) throws InterruptedException
    {
        String foolsDayRunning = getConfigSetting("GameServer", "foolsDay");
        if (foolsDayRunning == null || foolsDayRunning.length() <= 0)
        {
            messageTo(self, "heartbeat", null, 60.0f, false);
            return SCRIPT_CONTINUE;
        }
        int currentTime = getCalendarTime();
        int midnightOnAprilFoolsDay = getCalendarTime(2010, 4, 1, 0, 0, 0);
        int midnightOnDayAfterAprilFools = getCalendarTime(2010, 4, 2, 0, 0, 0);
        String forceAprilFools = getConfigSetting("GameServer", "forceFoolsDay");
        if (forceAprilFools == null || forceAprilFools.length() <= 0)
        {
            if (currentTime < midnightOnAprilFoolsDay || currentTime > midnightOnDayAfterAprilFools)
            {
                messageTo(self, "cleanupCreatures", null, 0.0f, false);
                messageTo(self, "heartbeat", null, 60.0f, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (!hasObjVar(self, CITY_OBJVAR))
        {
            LOG("AprilFools", "No City ObjVar, bailing.");
            messageTo(self, "heartbeat", null, 60.0f, false);
            return SCRIPT_CONTINUE;
        }
        String cityName = getStringObjVar(self, CITY_OBJVAR);
        String spawnDatatable = SPAWN_DATATABLE;
        if (!dataTableOpen(spawnDatatable))
        {
            LOG("AprilFools", "Failed to open table " + spawnDatatable);
            return SCRIPT_CONTINUE;
        }
        int spawnTime = 60 * 60;
        int configTime = utils.stringToInt(getConfigSetting("GameServer", "foolsDayTimer"));
        if (configTime >= 0)
        {
            spawnTime = configTime * 60;
        }
        int lastSpawnTime = getIntObjVar(self, LAST_SPAWN);
        if (currentTime - lastSpawnTime < spawnTime)
        {
            messageTo(self, "heartbeat", null, 60.0f, false);
            return SCRIPT_CONTINUE;
        }
        String planetName = getCurrentSceneName();
        Vector creatureList = new Vector();
        creatureList.setSize(0);
        int numRows = dataTableGetNumRows(spawnDatatable);
        for (int i = 0; i < numRows; ++i)
        {
            String dtPlanetName = dataTableGetString(spawnDatatable, i, 0);
            String dtCityName = dataTableGetString(spawnDatatable, i, 1);
            if (planetName.equals(dtPlanetName) == false || cityName.equals(dtCityName) == false)
            {
                continue;
            }
            int maxCreatures = dataTableGetInt(spawnDatatable, i, 6);
            int totalCreatures = 0;
            if (hasObjVar(self, CREATURE_LIST))
            {
                obj_id[] currentCreatures = getObjIdArrayObjVar(self, CREATURE_LIST);
                if (currentCreatures != null && currentCreatures.length > 0)
                {
                    for (int j = 0; j < currentCreatures.length; ++j)
                    {
                        if (isIdValid(currentCreatures[j]) && exists(currentCreatures[j]) && !isDead(currentCreatures[j]))
                        {
                            ++totalCreatures;
                            utils.addElement(creatureList, currentCreatures[j]);
                        }
                    }
                }
            }
            if (totalCreatures == maxCreatures)
            {
                setObjVar(self, CREATURE_LIST, creatureList);
                messageTo(self, "heartbeat", null, 60.0f, false);
                return SCRIPT_CONTINUE;
            }
            String templateName = dataTableGetString(spawnDatatable, i, 2);
            float cx = dataTableGetFloat(spawnDatatable, i, 3);
            float cy = dataTableGetFloat(spawnDatatable, i, 4);
            float cz = dataTableGetFloat(spawnDatatable, i, 5);
            location currentLoc = getLocation(self);
            currentLoc.x = cx;
            currentLoc.y = cy;
            currentLoc.z = cz;
            for (int j = totalCreatures; j < maxCreatures; ++j)
            {
                currentLoc.x += (10 * j);
                obj_id newCreature = create.object(templateName, currentLoc);
                utils.addElement(creatureList, newCreature);
            }
            setObjVar(self, CREATURE_LIST, creatureList);
            setObjVar(self, LAST_SPAWN, getCalendarTime());
        }
        messageTo(self, "heartbeat", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupCreatures(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, CREATURE_LIST))
        {
            obj_id[] currentCreatures = getObjIdArrayObjVar(self, CREATURE_LIST);
            if (currentCreatures != null && currentCreatures.length > 0)
            {
                for (int j = 0; j < currentCreatures.length; ++j)
                {
                    if (isIdValid(currentCreatures[j]) && exists(currentCreatures[j]) && !isDead(currentCreatures[j]))
                    {
                        if (ai_lib.isInCombat(currentCreatures[j]))
                        {
                            if (!utils.hasScriptVar(self, "destroyDelay"))
                            {
                                utils.setScriptVar(self, "destroyDelay", 1);
                                return SCRIPT_CONTINUE;
                            }
                            destroyObject(currentCreatures[j]);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
