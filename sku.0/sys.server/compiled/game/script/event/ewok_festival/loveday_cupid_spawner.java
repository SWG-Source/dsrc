package script.event.ewok_festival;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.holiday;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class loveday_cupid_spawner extends script.base_script
{
    public loveday_cupid_spawner()
    {
    }
    public static final int CUPID_APPEARANCE_LENGTH = 900;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerLovedayCupidSpawner", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerLovedayCupidSpawner", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int registerLovedayCupidSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        registerCupidSpawner(self);
        return SCRIPT_CONTINUE;
    }
    public void registerCupidSpawner(obj_id self) throws InterruptedException
    {
        String spawnerId = holiday.LOVEDAY_CUPID_ELEMENT_NAME + getStringObjVar(self, holiday.LOVEDAY_CUPID_SPAWNER_CITY_VAR);
        getClusterWideData(holiday.LOVEDAY_CUPID_MANAGER_NAME, spawnerId, true, self);
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        if (manage_name.equals(holiday.LOVEDAY_CUPID_MANAGER_NAME))
        {
            if (!utils.hasScriptVar(self, "alreadyRegisteredCupidSpawner"))
            {
                if (hasObjVar(self, holiday.LOVEDAY_CUPID_SPAWNER_CITY_VAR))
                {
                    String spawnerId = holiday.LOVEDAY_CUPID_ELEMENT_NAME + getStringObjVar(self, holiday.LOVEDAY_CUPID_SPAWNER_CITY_VAR);
                    dictionary webster = new dictionary();
                    webster.put(spawnerId, self);
                    replaceClusterWideData(manage_name, spawnerId, webster, true, lock_key);
                    utils.setScriptVar(self, "alreadyRegisteredCupidSpawner", true);
                }
            }
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public int spawnHourlyCupidNPCs(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, holiday.LOVEDAY_CUPID_SPAWNER_CITY_VAR))
        {
            String lovedayLoc = getStringObjVar(self, holiday.LOVEDAY_CUPID_SPAWNER_CITY_VAR);
            Vector theSpawned = new Vector();
            theSpawned.setSize(0);
            location here = getLocation(self);
            if (!utils.hasScriptVar(self, "cupidSpawned"))
            {
                obj_id herald = create.object("loveday_ewok_cupid_herald", here);
                if (isIdValid(herald))
                {
                    ai_lib.setDefaultCalmBehavior(herald, ai_lib.BEHAVIOR_SENTINEL);
                    setYaw(herald, getYaw(self));
                    utils.addElement(theSpawned, herald);
                }
                if (params != null && !params.isEmpty())
                {
                    boolean locChosen = params.getBoolean("chosen_" + lovedayLoc);
                    if (locChosen)
                    {
                        String datatable = "datatables/spawning/holiday/love_day_cupid_" + lovedayLoc + ".iff";
                        int numRows = dataTableGetNumRows(datatable);
                        int chosenRow = rand(0, numRows - 1);
                        dictionary cupidData = dataTableGetRow(datatable, chosenRow);
                        float x = cupidData.getFloat("loc_x");
                        float y = cupidData.getFloat("loc_y");
                        float z = cupidData.getFloat("loc_z");
                        float yaw = cupidData.getFloat("yaw");
                        location spawnLoc = new location(x, y, z, here.area, null);
                        obj_id cupid = create.object("loveday_ewok_cupid", spawnLoc);
                        if (isIdValid(cupid))
                        {
                            ai_lib.setDefaultCalmBehavior(cupid, ai_lib.BEHAVIOR_SENTINEL);
                            setYaw(cupid, yaw);
                            utils.addElement(theSpawned, cupid);
                        }
                    }
                }
                if (theSpawned.size() > 0)
                {
                    utils.setScriptVar(self, "cupidSpawned", theSpawned);
                    messageTo(self, "despawnHourlyCupidNPCs", null, CUPID_APPEARANCE_LENGTH, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int despawnHourlyCupidNPCs(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "cupidSpawned"))
        {
            Vector theSpawned = utils.getResizeableObjIdArrayScriptVar(self, "cupidSpawned");
            if (theSpawned != null && theSpawned.size() > 0)
            {
                obj_id spawnedNpc;
                for (Object spawnedItem : theSpawned) {
                    spawnedNpc = ((obj_id) spawnedItem);
                    if (isIdValid(spawnedNpc) && exists(spawnedNpc)) {
                        messageTo(spawnedNpc, "prepareForDespawn", null, 1, false);
                    }
                }
            }
            utils.removeScriptVar(self, "cupidSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker) || !hasObjVar(speaker, "cupidTestingAuthorized"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("cupid_testing_spawner"))
        {
            utils.removeScriptVar(self, "alreadyRegisteredCupidSpawner");
            registerCupidSpawner(self);
        }
        if (text.equals("cupid_spawner_cleanup"))
        {
            messageTo(self, "despawnHourlyCupidNPCs", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
