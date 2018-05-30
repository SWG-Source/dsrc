package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.utils;

public class requestloctest extends script.base_script
{
    public requestloctest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            sendSystemMessage(self, "You are not authorized to use this script.", "");
            detachScript(self, "working.requestloctest");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        int stringCheck = text.indexOf("get_location_32");
        if (stringCheck > -1)
        {
            boolean checkSlope = true;
            boolean checkWater = true;
            stringCheck = text.indexOf("allow_slope");
            if (stringCheck > -1)
            {
                checkSlope = false;
            }
            stringCheck = text.indexOf("allow_water");
            if (stringCheck > -1)
            {
                checkWater = false;
            }
            location here = getLocation(self);
            requestLocation(self, "get_location_32", here, 500.0f, 32.0f, checkWater, checkSlope);
            sendSystemMessage(self, "requestingLocation: get_location_32 - water: " + checkWater + ", slope: " + checkSlope, "");
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("get_location_48");
        if (stringCheck > -1)
        {
            boolean checkSlope = true;
            boolean checkWater = true;
            stringCheck = text.indexOf("allow_slope");
            if (stringCheck > -1)
            {
                checkSlope = false;
            }
            stringCheck = text.indexOf("allow_water");
            if (stringCheck > -1)
            {
                checkWater = false;
            }
            location here = getLocation(self);
            requestLocation(self, "get_location_48", here, 500.0f, 48.0f, checkWater, checkSlope);
            sendSystemMessage(self, "requestingLocation: get_location_48 - water: " + checkWater + ", slope: " + checkSlope, "");
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("get_location_64");
        if (stringCheck > -1)
        {
            boolean checkSlope = true;
            boolean checkWater = true;
            stringCheck = text.indexOf("allow_slope");
            if (stringCheck > -1)
            {
                checkSlope = false;
            }
            stringCheck = text.indexOf("allow_water");
            if (stringCheck > -1)
            {
                checkWater = false;
            }
            location here = getLocation(self);
            requestLocation(self, "get_location_64", here, 500.0f, 64.0f, checkWater, checkSlope);
            sendSystemMessage(self, "requestingLocation: get_location_64 - water: " + checkWater + ", slope: " + checkSlope, "");
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("get_location_80");
        if (stringCheck > -1)
        {
            boolean checkSlope = true;
            boolean checkWater = true;
            stringCheck = text.indexOf("allow_slope");
            if (stringCheck > -1)
            {
                checkSlope = false;
            }
            stringCheck = text.indexOf("allow_water");
            if (stringCheck > -1)
            {
                checkWater = false;
            }
            location here = getLocation(self);
            requestLocation(self, "get_location_80", here, 500.0f, 80.0f, checkWater, checkSlope);
            sendSystemMessage(self, "requestingLocation: get_location_80 - water: " + checkWater + ", slope: " + checkSlope, "");
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("get_location_96");
        if (stringCheck > -1)
        {
            boolean checkSlope = true;
            boolean checkWater = true;
            stringCheck = text.indexOf("allow_slope");
            if (stringCheck > -1)
            {
                checkSlope = false;
            }
            stringCheck = text.indexOf("allow_water");
            if (stringCheck > -1)
            {
                checkWater = false;
            }
            location here = getLocation(self);
            requestLocation(self, "get_location_96", here, 500.0f, 96.0f, checkWater, checkSlope);
            sendSystemMessage(self, "requestingLocation: get_location_96 - water: " + checkWater + ", slope: " + checkSlope, "");
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("clear_last_location");
        if (stringCheck > -1)
        {
            if (hasObjVar(self, "locTest.objects"))
            {
                Vector locObjects = getResizeableObjIdArrayObjVar(self, "locTest.objects");
                if (locObjects != null && locObjects.size() > 0)
                {
                    int lastObjectIndx = locObjects.size() - 1;
                    obj_id locObject = ((obj_id)locObjects.get(lastObjectIndx));
                    utils.removeElementAt(locObjects, lastObjectIndx);
                    setObjVar(self, "locTest.objects", locObjects);
                    if (isIdValid(locObject))
                    {
                        locations.destroyLocationObject(locObject);
                    }
                }
            }
            if (hasObjVar(self, "locTest.waypoints"))
            {
                Vector locWaypoints = getResizeableObjIdArrayObjVar(self, "locTest.waypoints");
                if (locWaypoints != null && locWaypoints.size() > 0)
                {
                    int lastWaypointIndx = locWaypoints.size() - 1;
                    obj_id locWaypoint = ((obj_id)locWaypoints.get(lastWaypointIndx));
                    utils.removeElementAt(locWaypoints, lastWaypointIndx);
                    setObjVar(self, "locTest.waypoints", locWaypoints);
                    if (isIdValid(locWaypoint))
                    {
                        destroyWaypointInDatapad(locWaypoint, self);
                    }
                }
            }
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("clear_all_locations");
        if (stringCheck > -1)
        {
            getRidOfLocObjects(self);
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("detach_script");
        if (stringCheck > -1)
        {
            getRidOfLocObjects(self);
            detachScript(self, "working.requestloctest");
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("help");
        if (stringCheck > -1)
        {
            String msg1 = "Type get_location_32, get_location_48, get_location_64, get_location_80, or get_location_96 to request a location.";
            String msg1b = " Include allow_slope or allow_water to not block for slope or for water.";
            String msg2 = " Type clear_last_location or clear_all_locations to clear reserved location objects.";
            String msg3 = " Type detach_script to clear current reserved location objects and detach this script.";
            sendSystemMessage(self, msg1 + msg1b + msg2 + msg3, "");
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLocationReceived(obj_id self, String locationId, obj_id locationObject, location locationLocation, float locationRadius) throws InterruptedException
    {
        if (!isIdValid(locationObject))
        {
            sendSystemMessage(self, "locationObject was invalid for search location: " + locationLocation + " locationId: " + locationId, "");
            return SCRIPT_CONTINUE;
        }
        if (locationLocation == null)
        {
            sendSystemMessage(self, "locationLocation was null (??!!?) for search location: " + locationLocation + " locationId: " + locationId, "");
            return SCRIPT_CONTINUE;
        }
        Vector locObjects = new Vector();
        locObjects.setSize(0);
        if (hasObjVar(self, "locTest.objects"))
        {
            locObjects = getResizeableObjIdArrayObjVar(self, "locTest.objects");
        }
        utils.addElement(locObjects, locationObject);
        setObjVar(self, "locTest.objects", locObjects);
        obj_id waypoint = createWaypointInDatapad(self, locationLocation);
        setWaypointName(waypoint, locationId);
        setWaypointActive(waypoint, true);
        Vector locWaypoints = new Vector();
        locWaypoints.setSize(0);
        if (hasObjVar(self, "locTest.waypoints"))
        {
            locWaypoints = getResizeableObjIdArrayObjVar(self, "locTest.waypoints");
        }
        utils.addElement(locWaypoints, waypoint);
        setObjVar(self, "locTest.waypoints", locWaypoints);
        sendSystemMessage(self, "OnLocationReceived parameters - Id: " + locationId + ", location: " + locationLocation + ", object: " + locationObject + ", radius:" + locationRadius, "");
        return SCRIPT_CONTINUE;
    }
    public void getRidOfLocObjects(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "locTest.objects"))
        {
            Vector locObjects = getResizeableObjIdArrayObjVar(self, "locTest.objects");
            removeObjVar(self, "locTest.objects");
            if (locObjects != null && locObjects.size() > 0)
            {
                for (int i = 0; i < locObjects.size(); i++)
                {
                    obj_id locObject = ((obj_id)locObjects.get(i));
                    if (isIdValid(locObject))
                    {
                        locations.destroyLocationObject(locObject);
                    }
                }
            }
        }
        if (hasObjVar(self, "locTest.waypoints"))
        {
            Vector locWaypoints = getResizeableObjIdArrayObjVar(self, "locTest.waypoints");
            removeObjVar(self, "locTest.waypoints");
            if (locWaypoints != null && locWaypoints.size() > 0)
            {
                for (int i = 0; i < locWaypoints.size(); i++)
                {
                    obj_id locWaypoint = ((obj_id)locWaypoints.get(i));
                    if (isIdValid(locWaypoint))
                    {
                        destroyWaypointInDatapad(locWaypoint, self);
                    }
                }
            }
        }
        return;
    }
}
