package script.systems.movement;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;

public class public_instance_setup extends script.base_script
{
    public public_instance_setup()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        getClusterWideData("public_instances", getStringObjVar(self, "instance_name") + "_" + self, true, self);
        doLogging("OnInitialize", "Requested cluster wide data for " + getStringObjVar(self, "instance_name") + "_" + self);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        int playersInZone = space_dungeon.pollZoneOccupantsForInstancePopulation(self);
        dictionary info = new dictionary();
        info.put("building_id", self);
        info.put("population", playersInZone);
        doLogging("OnCWDResponse", "Put into info my ID: " + self);
        replaceClusterWideData(manage_name, name, info, true, lock_key);
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public int getTargetCoordinates(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("return_address");
        obj_id player = params.getObjId("player");
        dictionary dict = new dictionary();
        dict.put("location", getLocation(self));
        dict.put("player", player);
        doLogging("getTargetCoordinates", "Got return address and player, sending my location: " + target + ", " + player + ", " + getLocation(self));
        messageTo(target, "recievedTargetCoordinates", dict, 1, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
    }
}
