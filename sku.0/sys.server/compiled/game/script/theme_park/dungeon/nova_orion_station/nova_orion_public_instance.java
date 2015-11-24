package script.theme_park.dungeon.nova_orion_station;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class nova_orion_public_instance extends script.base_script
{
    public nova_orion_public_instance()
    {
    }
    public static final boolean LOGGING = true;
    public static final float POPULATION_UPDATE_TIME = 20.0f;
    public static final float STATION_SCAN_RADIUS = 500.0f;
    public static final String VAR_STATION_INSTANCE_ID = "nova_orion_instance_id";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        getClusterWideData("public_instances", getStringObjVar(self, "instance_name") + "_" + self, true, self);
        doLogging("OnInitialize", "Requested cluster wide data for " + getStringObjVar(self, "instance_name") + "_" + self);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        Vector players = getPlayersInStation(self);
        String players_string = new String(utils.packObject(players));
        dictionary info = new dictionary();
        location wloc = getLocation(self);
        info.put("building_id", self);
        info.put("population_info", players_string);
        info.put("population_num", players.size());
        info.put("world_location.x", wloc.x);
        info.put("world_location.y", wloc.y);
        info.put("world_location.z", wloc.z);
        doLogging("OnClusterWideDataResponse", "Updated info for: " + self + " size: " + info.size());
        replaceClusterWideData(manage_name, name, info, true, lock_key);
        releaseClusterWideDataLock(manage_name, lock_key);
        dictionary params = new dictionary();
        messageTo(self, "updatePopulation", params, POPULATION_UPDATE_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int updatePopulation(obj_id self, dictionary params) throws InterruptedException
    {
        doLogging("updatePopulation", "Got message to updatePopulation");
        getClusterWideData("public_instances", getStringObjVar(self, "instance_name") + "_" + self, true, self);
        return SCRIPT_CONTINUE;
    }
    public Vector getPlayersInStation(obj_id self) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), STATION_SCAN_RADIUS);
        Vector players_ret = new Vector();
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (isIdValid(players[i]) && utils.hasScriptVar(players[i], VAR_STATION_INSTANCE_ID))
                {
                    obj_id station = utils.getObjIdScriptVar(players[i], VAR_STATION_INSTANCE_ID);
                    if (isIdValid(station) && station == self)
                    {
                        players_ret.add(players[i]);
                    }
                }
            }
        }
        return players_ret;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("npe_public_instance:" + section, message);
        }
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            removeObjVar(item, VAR_STATION_INSTANCE_ID);
            utils.removeScriptVar(item, VAR_STATION_INSTANCE_ID);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            removeObjVar(item, VAR_STATION_INSTANCE_ID);
            utils.setScriptVar(item, VAR_STATION_INSTANCE_ID, self);
        }
        return SCRIPT_CONTINUE;
    }
}
