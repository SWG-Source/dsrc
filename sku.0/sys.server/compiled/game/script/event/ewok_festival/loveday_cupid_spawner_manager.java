package script.event.ewok_festival;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.holiday;
import script.library.utils;

public class loveday_cupid_spawner_manager extends script.base_script
{
    public loveday_cupid_spawner_manager()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeCupidSpawnerManager", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeCupidSpawnerManager", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeCupidSpawnerManager(obj_id self, dictionary params) throws InterruptedException
    {
        int hourlyCupidNpcsTime = createHourlyAlarmClock(self, "triggerHourlyCupidNPCs", null, 00, 0);
        return SCRIPT_CONTINUE;
    }
    public int triggerHourlyCupidNPCs(obj_id self, dictionary params) throws InterruptedException
    {
        getLovedayCupidSpawnerIds(self);
        int hourlyCupidNpcsTime = createHourlyAlarmClock(self, "triggerHourlyCupidNPCs", null, 00, 0);
        return SCRIPT_CONTINUE;
    }
    public void getLovedayCupidSpawnerIds(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, holiday.GETTING_CUPID_SPAWNER_IDS, true);
        getClusterWideData(holiday.LOVEDAY_CUPID_MANAGER_NAME, holiday.LOVEDAY_CUPID_ELEMENT_NAME + "*", true, self);
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        if (manage_name.equals(holiday.LOVEDAY_CUPID_MANAGER_NAME))
        {
            if (utils.hasScriptVar(self, holiday.GETTING_CUPID_SPAWNER_IDS))
            {
                utils.removeScriptVar(self, holiday.GETTING_CUPID_SPAWNER_IDS);
                if (data.length > 0)
                {
                    dictionary webster = new dictionary();
                    int choice = rand(0, holiday.LOVEDAY_LOCATIONS.length - 1);
                    for (int i = 0; i < holiday.LOVEDAY_LOCATIONS.length; i++)
                    {
                        if (data[i] != null && !data[i].isEmpty())
                        {
                            obj_id spawner_id = obj_id.NULL_ID;
                            String lovedayLoc = holiday.LOVEDAY_LOCATIONS[i];
                            if (data[i].containsKey(holiday.LOVEDAY_CUPID_ELEMENT_NAME + lovedayLoc))
                            {
                                spawner_id = data[i].getObjId(holiday.LOVEDAY_CUPID_ELEMENT_NAME + lovedayLoc);
                                if (isIdValid(spawner_id))
                                {
                                    boolean cityChosen = false;
                                    if (choice == i)
                                    {
                                        cityChosen = true;
                                    }
                                    webster.put("chosen_" + lovedayLoc, cityChosen);
                                    messageTo(spawner_id, "spawnHourlyCupidNPCs", webster, 1, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker) || !hasObjVar(speaker, "cupidTestingAuthorized"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("cupid_testing_manager"))
        {
            getLovedayCupidSpawnerIds(self);
        }
        return SCRIPT_CONTINUE;
    }
}
