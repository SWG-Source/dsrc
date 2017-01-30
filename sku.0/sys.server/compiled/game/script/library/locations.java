package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.regions;
import script.library.trial;

public class locations extends script.base_script
{
    public locations()
    {
    }
    public static final float GOOD_LOCATION_SEARCH_SIZE = 200;
    public static final String[] PLANETS = 
    {
        "tatooine",
        "naboo",
        "talus",
        "corellia",
        "yavin4",
        "dantooine",
        "dathomir",
        "lok",
        "rori",
        "endor"
    };
    public static final String NO_AREA = "no_area";
    public static final String PLANET_LEVEL_TABLE = "datatables/spawning/planetary_data/planet_level.iff";
    public static float getRegionExtents(region rgnTest) throws InterruptedException
    {
        location locLowerLeft = new location();
        location locUpperRight = new location();
        location[] locExtents = getRegionExtent(rgnTest);
        locLowerLeft = (location)locExtents[0].clone();
        locUpperRight = (location)locExtents[1].clone();
        LOG("mission", "Lower Left is " + locLowerLeft.toString());
        LOG("mission", "Upper Right is " + locUpperRight.toString());
        float fltXDistance = locUpperRight.x - locLowerLeft.x;
        float fltZDistance = locUpperRight.z - locLowerLeft.z;
        if (fltXDistance > fltZDistance)
        {
            LOG("mission", "fltXDistance returning with a value of " + fltXDistance);
            return fltXDistance;
        }
        else 
        {
            LOG("mission", "fltZDistance returning with a value of " + fltZDistance);
            return fltZDistance;
        }
    }
    public static region getSmallestRegion(region[] rgnRegions) throws InterruptedException
    {
        if (rgnRegions == null)
        {
            return null;
        }
        if (rgnRegions.length == 0)
        {
            return null;
        }
        region rgnSmallestRegion = rgnRegions[0];
        float fltExtents = 1000000000;
        for (int intI = 0; intI < rgnRegions.length; intI++)
        {
            float fltRegionExtents = getRegionExtents(rgnRegions[intI]);
            if (fltRegionExtents < fltExtents)
            {
                rgnSmallestRegion = rgnRegions[intI];
                fltExtents = fltRegionExtents;
            }
        }
        return rgnSmallestRegion;
    }
    public static float getRegionXSize(region rgnSearchRegion) throws InterruptedException
    {
        location[] locExtents = getRegionExtent(rgnSearchRegion);
        location locLowerLeft = (location)locExtents[0].clone();
        location locUpperRight = (location)locExtents[1].clone();
        LOG("mission", "Lower Left is " + locLowerLeft.toString());
        LOG("mission", "Upper Right is " + locUpperRight.toString());
        float fltXDistance = locUpperRight.x - locLowerLeft.x;
        return fltXDistance;
    }
    public static float getRegionZSize(region rgnSearchRegion) throws InterruptedException
    {
        location[] locExtents = getRegionExtent(rgnSearchRegion);
        location locLowerLeft = (location)locExtents[0].clone();
        location locUpperRight = (location)locExtents[1].clone();
        LOG("mission", "Lower Left is " + locLowerLeft.toString());
        LOG("mission", "Upper Right is " + locUpperRight.toString());
        float fltZDistance = locUpperRight.z - locLowerLeft.z;
        return fltZDistance;
    }
    public static float[] getRegionSize(region rgnSearchRegion) throws InterruptedException
    {
        location[] locExtents = getRegionExtent(rgnSearchRegion);
        location locLowerLeft = (location)locExtents[0].clone();
        location locUpperRight = (location)locExtents[1].clone();
        LOG("mission", "Lower Left is " + locLowerLeft.toString());
        LOG("mission", "Upper Right is " + locUpperRight.toString());
        float fltXDistance = locUpperRight.x - locLowerLeft.x;
        float fltZDistance = locUpperRight.z - locLowerLeft.z;
        float[] fltRegionSize = new float[2];
        fltRegionSize[0] = fltXDistance;
        fltRegionSize[1] = fltZDistance;
        return fltRegionSize;
    }
    public static location getRegionCenter(region rgnSearchRegion) throws InterruptedException
    {
        location locLowerLeft = new location();
        location locUpperRight = new location();
        location[] locExtents = getRegionExtent(rgnSearchRegion);
        locLowerLeft = (location)locExtents[0].clone();
        locUpperRight = (location)locExtents[1].clone();
        location locRegionCenter = (location)locLowerLeft.clone();
        locRegionCenter.x = (locLowerLeft.x + locUpperRight.x) / 2;
        locRegionCenter.z = (locLowerLeft.z + locUpperRight.z) / 2;
        LOG("missions", "locRegionCenter is " + locRegionCenter.toString());
        return locRegionCenter;
    }
    public static location getGoodLocationOutsideOfRegion(region rgnSearchRegion, float fltXSize, float fltZSize, float fltDistance) throws InterruptedException
    {
        if (rgnSearchRegion == null)
        {
            LOG("mission", "getting good location outside of region returned NULL");
            return null;
        }
        debugServerConsoleMsg(null, "getting good locoutsideofregion");
        LOG("mission", "getting good location outside of region");
        float fltRegionExtent = getRegionExtents(rgnSearchRegion);
        location locRegionCenter = getRegionCenter(rgnSearchRegion);
        LOG("mission", "got centers and extents");
        location locDestination = utils.getRandomLocationInRing(locRegionCenter, fltRegionExtent, fltRegionExtent + fltDistance);
        location locLowerLeft = (location)locDestination.clone();
        locLowerLeft.x = locLowerLeft.x - GOOD_LOCATION_SEARCH_SIZE + fltXSize;
        locLowerLeft.z = locLowerLeft.z - GOOD_LOCATION_SEARCH_SIZE + fltZSize;
        location locUpperRight = (location)locDestination.clone();
        locUpperRight.x = locUpperRight.x + GOOD_LOCATION_SEARCH_SIZE + fltXSize;
        locUpperRight.z = locUpperRight.z + GOOD_LOCATION_SEARCH_SIZE + fltZSize;
        location locGoodLocation = getGoodLocation(fltXSize, fltZSize, locLowerLeft, locUpperRight, false, false);
        LOG("mission", "gotGoodLocation");
        return locGoodLocation;
    }
    public static location getGoodLocationOutsideOfRegion(region rgnSearchRegion, float fltXSize, float fltZSize, float fltDistance, boolean boolIgnoreWater, boolean boolIgnoreSlope) throws InterruptedException
    {
        debugServerConsoleMsg(null, "getting good locoutsideofregion");
        LOG("mission", "getting good location outside of region");
        float fltRegionExtent = getRegionExtents(rgnSearchRegion);
        location locRegionCenter = getRegionCenter(rgnSearchRegion);
        LOG("mission", "got centers and extents");
        location locDestination = utils.getRandomLocationInRing(locRegionCenter, fltRegionExtent, fltRegionExtent + fltDistance);
        location locLowerLeft = (location)locDestination.clone();
        locLowerLeft.x = locLowerLeft.x - GOOD_LOCATION_SEARCH_SIZE + fltXSize;
        locLowerLeft.z = locLowerLeft.z - GOOD_LOCATION_SEARCH_SIZE + fltZSize;
        location locUpperRight = (location)locDestination.clone();
        locUpperRight.x = locUpperRight.x + GOOD_LOCATION_SEARCH_SIZE + fltXSize;
        locUpperRight.z = locUpperRight.z + GOOD_LOCATION_SEARCH_SIZE + fltZSize;
        location locGoodLocation = getGoodLocation(fltXSize, fltZSize, locLowerLeft, locUpperRight, boolIgnoreWater, boolIgnoreSlope);
        LOG("mission", "gotGoodLocation");
        return locGoodLocation;
    }
    public static region getClosestCityRegion(region rgnStartRegion) throws InterruptedException
    {
        String strName = rgnStartRegion.getName();
        LOG("mission", "in getclosestcityregion, name is " + strName);
        region[] rgnCities = getRegionsWithMunicipal(rgnStartRegion.getPlanetName(), regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            return null;
        }
        region rgnClosestRegion = rgnCities[0];
        float fltDistance;
        float fltLowestDistance = 500000000;
        location locCenterPoint;
        location locCurrentCenter;
        locCurrentCenter = getRegionCenter(rgnStartRegion);
        int intI = 0;
        while (intI < rgnCities.length)
        {
            LOG("mission", "StartRegion is " + rgnStartRegion);
            String strNewName = rgnCities[intI].getName();
            LOG("missions", "strNewName is " + strNewName);
            if (!strNewName.equals(strName))
            {
                fltDistance = getDistance(locCurrentCenter, getRegionCenter(rgnCities[intI]));
                LOG("mission", "fltDistance is " + fltDistance);
                LOG("mission", "fltLowestDistance is " + fltLowestDistance);
                LOG("mission", "closest region is " + rgnClosestRegion);
                if (fltDistance < fltLowestDistance)
                {
                    fltLowestDistance = fltDistance;
                    rgnClosestRegion = rgnCities[intI];
                }
            }
            intI = intI + 1;
        }
        LOG("mission", "returned region is " + rgnClosestRegion);
        return rgnClosestRegion;
    }
    public static region getDeliverCityRegion(region rgnStartRegion) throws InterruptedException
    {
        Vector strPlanets = new Vector();
        strPlanets.setSize(0);
        String strPlanet = rgnStartRegion.getPlanetName();
        region[] rgnCities = getRegionsWithMunicipal(strPlanet, regions.MUNI_TRUE);
        Vector rgnNewCities = new Vector();
        rgnNewCities.setSize(0);
        if (rgnCities == null)
        {
            return null;
        }
        if (rgnCities.length == 0)
        {
            return null;
        }
        LOG("locations", "length is " + rgnCities.length);
        if (rgnCities.length < 3)
        {
            for (int intI = 0; intI < PLANETS.length; intI++)
            {
                String strTestString = PLANETS[intI];
                LOG("locations", "strTestString is " + strTestString);
                LOG("locations", "strPlanet is " + strPlanet);
                if (!strTestString.equals(strPlanet))
                {
                    strPlanets = utils.addElement(strPlanets, strTestString);
                }
            }
            String strNewPlanet = ((String)strPlanets.get(rand(0, strPlanets.size() - 1)));
            LOG("locations", "new planet is " + strNewPlanet);
            rgnCities = getRegionsWithMunicipal(strNewPlanet, regions.MUNI_TRUE);
            if ((rgnCities != null) && (rgnCities.length > 0))
            {
                region rgnCity = rgnCities[rand(0, rgnCities.length - 1)];
                LOG("locations", "rgnCity is " + rgnCity.getName());
                return rgnCity;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            int intI = 0;
            String strStartName = rgnStartRegion.getName();
            while (intI < rgnCities.length)
            {
                String strNewName = rgnCities[intI].getName();
                if (!strStartName.equals(strNewName))
                {
                    rgnNewCities = utils.addElement(rgnNewCities, rgnCities[intI]);
                }
                intI = intI + 1;
            }
            region rgnCity = ((region)rgnNewCities.get(rand(0, rgnNewCities.size() - 1)));
            return rgnCity;
        }
    }
    public static region getCityRegion(region rgnStartRegion) throws InterruptedException
    {
        region[] rgnCitiesArray = getRegionsWithMunicipal(rgnStartRegion.getPlanetName(), regions.MUNI_TRUE);
        Vector rgnCities = new Vector(Arrays.asList(rgnCitiesArray));
        if (rgnCities == null)
        {
            return null;
        }
        if (rgnCitiesArray == null)
        {
            return null;
        }
        if (rgnCities.size() == 0)
        {
            return null;
        }
        if (rgnCitiesArray.length == 0)
        {
            return null;
        }
        int intI = 0;
        String strStartName = rgnStartRegion.getName();
        while (intI < rgnCities.size())
        {
            String strNewName = ((region)rgnCities.get(intI)).getName();
            if (strStartName.equals(strNewName))
            {
                rgnCities = utils.removeElementAt(rgnCities, intI);
                intI = rgnCities.size() + 10;
            }
            intI = intI + 1;
        }
        region rgnCity = ((region)rgnCities.get(rand(0, rgnCities.size() - 1)));
        return rgnCity;
    }
    public static region getCityRegion(location locCurrentLocation) throws InterruptedException
    {
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locCurrentLocation, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            return null;
        }
        region rgnCity = rgnCities[rand(0, rgnCities.length - 1)];
        return rgnCity;
    }
    public static boolean isInCity(location locTestLocation) throws InterruptedException
    {
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locTestLocation, regions.MUNI_TRUE);
        if (rgnCities != null)
        {
            return true;
        }
        return false;
    }
    public static boolean isInMissionCity(location locTestLocation) throws InterruptedException
    {
        if (city.isInCity(locTestLocation))
        {
            return true;
        }
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locTestLocation, regions.MUNI_TRUE);
        if (rgnCities != null)
        {
            return true;
        }
        rgnCities = getRegionsWithGeographicalAtPoint(locTestLocation, regions.GEO_CITY);
        if (rgnCities != null)
        {
            return true;
        }
        return false;
    }
    public static location getGoodLocationInRegion(region rgnSearchRegion, float fltXSize, float fltZSize) throws InterruptedException
    {
        location locLowerLeft = new location();
        location locUpperRight = new location();
        location[] locExtents = getRegionExtent(rgnSearchRegion);
        locLowerLeft = (location)locExtents[0].clone();
        locUpperRight = (location)locExtents[1].clone();
        location locGoodLocation = getGoodLocation(fltXSize, fltZSize, locLowerLeft, locUpperRight, false, false);
        return locGoodLocation;
    }
    public static location getGoodLocationInRegion(region rgnSearchRegion, float fltXSize, float fltZSize, boolean boolIgnoreWater, boolean boolIgnoreSlope) throws InterruptedException
    {
        location locLowerLeft = new location();
        location locUpperRight = new location();
        location[] locExtents = getRegionExtent(rgnSearchRegion);
        locLowerLeft = (location)locExtents[0].clone();
        locUpperRight = (location)locExtents[1].clone();
        location locGoodLocation = getGoodLocation(fltXSize, fltZSize, locLowerLeft, locUpperRight, boolIgnoreWater, boolIgnoreSlope);
        return locGoodLocation;
    }
    public static location getRandomGoodLocation(location start, float searchMin, float searchMax, float bestSize) throws InterruptedException
    {
        location result = null;
        location l = utils.getRandomLocationInRing(start, searchMin, searchMax);
        location lowerLeft = new location();
        location upperRight = new location();
        lowerLeft.x = l.x - bestSize;
        lowerLeft.z = l.z - bestSize;
        upperRight.x = l.x + bestSize;
        upperRight.z = l.z + bestSize;
        float radius = bestSize;
        while (result == null && radius > 2.0f)
        {
            result = getGoodLocation(radius, radius, lowerLeft, upperRight, false, false);
            radius = radius * 0.5f;
        }
        return result;
    }
    public static location getGoodLocationAroundLocation(location locSearchLocation, float fltXSize, float fltZSize, float fltXSearchSize, float fltZSearchSize) throws InterruptedException
    {
        location locLowerLeft = (location)locSearchLocation.clone();
        locLowerLeft.x = locLowerLeft.x - fltXSearchSize;
        locLowerLeft.z = locLowerLeft.z - fltZSearchSize;
        LOG("mission", "locLowerLeft is " + locLowerLeft.toString());
        location locUpperRight = (location)locSearchLocation.clone();
        locUpperRight.x = locUpperRight.x + fltXSearchSize;
        locUpperRight.z = locUpperRight.z + fltZSearchSize;
        LOG("mission", "locUpperRight is " + locUpperRight.toString());
        location locGoodLocation = getGoodLocation(fltXSize, fltZSize, locLowerLeft, locUpperRight, false, false);
        return locGoodLocation;
    }
    public static location getGoodLocationAroundLocation(location locSearchLocation, float fltXSize, float fltZSize, float fltXSearchSize, float fltZSearchSize, boolean boolIgnoreWater, boolean boolIgnoreSlope) throws InterruptedException
    {
        location locLowerLeft = (location)locSearchLocation.clone();
        locLowerLeft.x = locLowerLeft.x - fltXSearchSize;
        locLowerLeft.z = locLowerLeft.z - fltZSearchSize;
        LOG("mission", "locLowerLeft is " + locLowerLeft.toString());
        location locUpperRight = (location)locSearchLocation.clone();
        locUpperRight.x = locUpperRight.x + fltXSearchSize;
        locUpperRight.z = locUpperRight.z + fltZSearchSize;
        LOG("mission", "locUpperRight is " + locUpperRight.toString());
        location locGoodLocation = getGoodLocation(fltXSize, fltZSize, locLowerLeft, locUpperRight, boolIgnoreWater, boolIgnoreSlope);
        if (locGoodLocation == null)
        {
            LOG("DESIGNER_FATAL", "X search size is " + fltXSearchSize);
            LOG("DESIGNER_FATAL", "Z search size is " + fltZSearchSize);
            LOG("DESIGNER_FATAL", "size of thing is " + fltZSize);
            LOG("DESIGNER_FATAL", "Lower left is " + locLowerLeft);
            LOG("DESIGNER_FATAL", "Upper Right is " + locUpperRight);
            LOG("DESIGNER_FATAL", "Start location is " + locSearchLocation);
        }
        return locGoodLocation;
    }
    public static location getGoodLocationAroundLocationAvoidCollidables(location locSearchLocation, float fltXSize, float fltZSize, float fltXSearchSize, float fltZSearchSize, boolean boolIgnoreWater, boolean boolIgnoreSlope, float staticObjDistance) throws InterruptedException
    {
        location locLowerLeft = (location)locSearchLocation.clone();
        locLowerLeft.x = locLowerLeft.x - fltXSearchSize;
        locLowerLeft.z = locLowerLeft.z - fltZSearchSize;
        LOG("mission", "locLowerLeft is " + locLowerLeft.toString());
        location locUpperRight = (location)locSearchLocation.clone();
        locUpperRight.x = locUpperRight.x + fltXSearchSize;
        locUpperRight.z = locUpperRight.z + fltZSearchSize;
        LOG("mission", "locUpperRight is " + locUpperRight.toString());
        location locGoodLocation = getGoodLocationAvoidCollidables(fltXSize, fltZSize, locLowerLeft, locUpperRight, boolIgnoreWater, boolIgnoreSlope, staticObjDistance);
        if (locGoodLocation == null)
        {
            LOG("DESIGNER_FATAL", "X search size is " + fltXSearchSize);
            LOG("DESIGNER_FATAL", "Z search size is " + fltZSearchSize);
            LOG("DESIGNER_FATAL", "size of thing is " + fltZSize);
            LOG("DESIGNER_FATAL", "Lower left is " + locLowerLeft);
            LOG("DESIGNER_FATAL", "Upper Right is " + locUpperRight);
            LOG("DESIGNER_FATAL", "Start location is " + locSearchLocation);
        }
        return locGoodLocation;
    }
    public static String getCityName(location locCurrentLocation) throws InterruptedException
    {
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locCurrentLocation, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            return null;
        }
        region rgnCity = rgnCities[0];
        string_id strFictionalName = utils.unpackString(rgnCity.getName());
        String strAsciiId = strFictionalName.getAsciiId();
        return strAsciiId;
    }
    public static String getGuardSpawnerRegionName(location locCurrentLocation) throws InterruptedException
    {
        if (locCurrentLocation == null)
        {
            return null;
        }
        region[] rgnCities = getRegionsWithGeographicalAtPoint(locCurrentLocation, regions.GEO_CITY);
        if (rgnCities == null)
        {
            return null;
        }
        region rgnCity = rgnCities[0];
        String strName = rgnCity.getName();
        return strName;
    }
    public static location getBountyLocation(String strPlanet) throws InterruptedException
    {
        region[] rgnCities = getRegionsWithMunicipal(strPlanet, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            return null;
        }
        if (rgnCities.length > 0)
        {
            int intRoll = rand(0, rgnCities.length - 1);
            region rgnCity = rgnCities[intRoll];
            location locDestination = getGoodCityLocation(rgnCity, strPlanet);
            location locCenter = getRegionCenter(rgnCities[intRoll]);
            return locCenter;
        }
        else 
        {
            location locDestination = new location();
            locDestination.x = rand(-6500, 6500);
            locDestination.z = rand(-6500, 6500);
            locDestination.area = strPlanet;
            locDestination = locations.getGoodLocationAroundLocation(locDestination, 1, 1, 400, 400, false, true);
            if (locDestination == null)
            {
                LOG("DESIGNER_FATAL", "No good location found for planet " + strPlanet);
                locDestination = new location();
                locDestination.area = strPlanet;
                locDestination.x = 0;
                locDestination.y = 0;
                locDestination.z = 0;
            }
            return locDestination;
        }
    }
    public static location moveLocationTowardsLocation(location locStartLocation, location locEndLocation, float fltDistance) throws InterruptedException
    {
        location locNewLocation = (location)locStartLocation.clone();
        float fltTotalDistance = getDistance(locStartLocation, locEndLocation);
        if (fltTotalDistance <= 0)
        {
            return locEndLocation;
        }
        float fltNewX = ((locEndLocation.x - locStartLocation.x) / fltTotalDistance);
        fltNewX = fltNewX * fltDistance;
        locNewLocation.x = locNewLocation.x + fltNewX;
        float fltNewZ = ((locEndLocation.z - locStartLocation.z) / fltTotalDistance);
        fltNewZ = fltNewZ * fltDistance;
        locNewLocation.z = locNewLocation.z + fltNewZ;
        return locNewLocation;
    }
    public static boolean isCityRegion(region rgnTest) throws InterruptedException
    {
        if (rgnTest.getMunicipalType() == regions.MUNI_TRUE)
        {
            return true;
        }
        return false;
    }
    public static location getDifferentGoodCityLocation(location locStartLocation) throws InterruptedException
    {
        LOG("missions", "getting different city location");
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locStartLocation, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            LOG("missions", "no cities found at locStartLocation ");
            return null;
        }
        region rgnCity = rgnCities[0];
        location locGoodLocation = new location();
        string_id strFictionalName = utils.unpackString(rgnCity.getName());
        String strAsciiId = strFictionalName.getAsciiId();
        LOG("mission_spam", "strAsciiId is " + strAsciiId);
        int regionType = regions.getDeliverMissionRegionType(strAsciiId);
        LOG("mission_spam", "Intregion type is " + regionType);
        rgnCities = getRegionsWithMissionAtPoint(locStartLocation, regionType);
        if (rgnCities == null)
        {
            LOG("missions", "at start location of " + locStartLocation + ", we found no mission types of bestine. ");
            return null;
        }
        String strOldName = rgnCities[0].getName();
        region[] rgnGoodLocationsArray = getRegionsWithMission(locStartLocation.area, regionType);
        Vector rgnGoodLocations = new Vector(Arrays.asList(rgnGoodLocationsArray));
        LOG("missions", "strOldName is " + strOldName);
        if (rgnGoodLocations == null)
        {
            LOG("missions", "no regions found with mission type on planet " + locStartLocation.area);
            LOG("mission_spam", "No regions were foind of type " + regionType);
            return null;
        }
        int intI = 0;
        while (intI < rgnGoodLocations.size())
        {
            String strNewName = ((region)rgnGoodLocations.get(intI)).getName();
            if (strNewName.equals(strOldName))
            {
                rgnGoodLocations = utils.removeElementAt(rgnGoodLocations, intI);
                intI = rgnGoodLocations.size() + 19;
            }
            intI = intI + 1;
        }
        region rgnSpawnRegion = ((region)rgnGoodLocations.get(rand(0, rgnGoodLocations.size() - 1)));
        locGoodLocation = findPointInRegion(rgnSpawnRegion);
        LOG("mission_spam", "Got good location of " + locGoodLocation.toString() + " in locations.getGoodCityLocation");
        return locGoodLocation;
    }
    public static location getDifferentGoodCityRegionLocation(location locStartLocation) throws InterruptedException
    {
        LOG("missions", "getting different city location");
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locStartLocation, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            return null;
        }
        region rgnCity = rgnCities[0];
        location locGoodLocation = new location();
        string_id strFictionalName = utils.unpackString(rgnCity.getName());
        String strAsciiId = strFictionalName.getAsciiId();
        LOG("mission_spam", "strAsciiId is " + strAsciiId);
        int regionType = regions.getDeliverMissionRegionType(strAsciiId);
        LOG("mission_spam", "Intregion type is " + regionType);
        rgnCities = getRegionsWithMissionAtPoint(locStartLocation, regionType);
        if (rgnCities == null)
        {
            LOG("missions", "at start location of " + locStartLocation + ", we found no mission types of bestine. ");
            return null;
        }
        String strOldName = rgnCities[0].getName();
        region[] rgnGoodLocationsArray = getRegionsWithMission(locStartLocation.area, regionType);
        Vector rgnGoodLocations = new Vector(Arrays.asList(rgnGoodLocationsArray));
        LOG("missions", "strOldName is " + strOldName);
        if (rgnGoodLocations == null)
        {
            LOG("missions", "no regions found with mission type on planet " + locStartLocation.area);
            LOG("mission_spam", "No regions were foind of type " + regionType);
            return null;
        }
        int intI = 0;
        while (intI < rgnGoodLocations.size())
        {
            String strNewName = ((region)rgnGoodLocations.get(intI)).getName();
            if (strNewName.equals(strOldName))
            {
                rgnGoodLocations = utils.removeElementAt(rgnGoodLocations, intI);
                intI = rgnGoodLocations.size() + 19;
            }
            intI = intI + 1;
        }
        region rgnSpawnRegion = ((region)rgnGoodLocations.get(rand(0, rgnGoodLocations.size() - 1)));
        locGoodLocation = getRegionCenter(rgnSpawnRegion);
        LOG("mission_spam", "Got good location of " + locGoodLocation.toString() + " in locations.getGoodCityLocation");
        return locGoodLocation;
    }
    public static location getGoodCityLocation(region rgnCity, String strPlanet) throws InterruptedException
    {
        if (rgnCity == null || strPlanet == null)
        {
            LOG("mission_spam", "getGoodCityLocation(line 883) returned NULL");
            return null;
        }
        location locGoodLocation = new location();
        string_id strFictionalName = utils.unpackString(rgnCity.getName());
        if(strFictionalName == null)
        {
            LOG("mission_spam", "getGoodCityLocation was unable to get the Fictional name from rgnCity (" + rgnCity.getName() + ")");
            return null;
        }
        String strAsciiId = strFictionalName.getAsciiId();
        LOG("mission_spam", "strAsciiId is " + strAsciiId);
        int regionType = regions.getDeliverMissionRegionType(strAsciiId);
        LOG("mission_spam", "Intregion type is " + regionType);
        region[] rgnGoodLocations = getRegionsWithMission(strPlanet, regionType);
        LOG("mission_spam", "strPlanet is " + strPlanet);
        if (rgnGoodLocations == null)
        {
            LOG("mission_spam", "No regions were foind of type " + regionType);
            return null;
        }
        region rgnSpawnRegion = rgnGoodLocations[rand(0, rgnGoodLocations.length - 1)];
        int intI = 0;
        while (intI < rgnGoodLocations.length)
        {
            LOG("locations", "rgnGoodLocationsRegion[intI] Type is " + rgnGoodLocations[intI].getMissionType());
            LOG("locations", "rgnGoodLocationsRegion[intI] planet is " + rgnGoodLocations[intI].getPlanetName());
            intI = intI + 1;
        }
        locGoodLocation = findPointInRegion(rgnSpawnRegion);
        LOG("mission_spam", "Got good location of " + locGoodLocation.toString() + " in locations.getGoodCityLocation");
        return locGoodLocation;
    }
    public static location getGoodCityRegionLocation(region rgnCity, String strPlanet) throws InterruptedException
    {
        if (rgnCity == null || strPlanet == null)
        {
            return null;
        }
        string_id strFictionalName = utils.unpackString(rgnCity.getName());
        if(strFictionalName == null){
            LOG("mission_spam", "Can't unpack city name (" + rgnCity.getName() + ") to get fictional name.");
            return null;
        }
        String strAsciiId = strFictionalName.getAsciiId();
        LOG("mission_spam", "strAsciiId is " + strAsciiId);
        int regionType = regions.getDeliverMissionRegionType(strAsciiId);
        LOG("mission_spam", "Intregion type is " + regionType);
        region[] rgnGoodLocations = getRegionsWithMission(strPlanet, regionType);
        LOG("mission_spam", "strPlanet is " + strPlanet);
        if (rgnGoodLocations == null)
        {
            LOG("mission_spam", "No regions were found of type " + regionType);
            return null;
        }
        region rgnSpawnRegion = rgnGoodLocations[rand(0, rgnGoodLocations.length - 1)];
        location locGoodLocation = new location();
        locGoodLocation = getRegionCenter(rgnSpawnRegion);
        return locGoodLocation;
    }
    public static int normalizeDifficultyForRegion(int intDifficulty, location locTest) throws InterruptedException
    {
        region[] rgnRegionsAtPoint = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_TRUE);
        if (rgnRegionsAtPoint == null || rgnRegionsAtPoint.length == 0)
        {
            rgnRegionsAtPoint = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_DEFAULT);
            if (rgnRegionsAtPoint == null)
            {
                LOG("DESIGNER_FATAL", "No regions at location " + locTest);
                return intDifficulty;
            }
        }
        region rgnSmallestRegion = getSmallestRegion(rgnRegionsAtPoint);
        if (rgnSmallestRegion == null)
        {
            LOG("locations", "Smallest region was null");
            return intDifficulty;
        }
        int intMinRegionDifficulty = rgnSmallestRegion.getMinDifficultyType();
        int intMaxRegionDifficulty = rgnSmallestRegion.getMaxDifficultyType();
        LOG("locations", "intMinRegionDifficulty: " + intMinRegionDifficulty + " intMaxRegionDifficulty: " + intMaxRegionDifficulty + " intDifficulty: " + intDifficulty);
        return rand(intMinRegionDifficulty, intMaxRegionDifficulty);
    }
    public static region[] getDifficultyRegionsAtLocation(location locTest) throws InterruptedException
    {
        region[] rgnRegionsAtPoint = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_TRUE);
        if (rgnRegionsAtPoint == null || rgnRegionsAtPoint.length == 0)
        {
            rgnRegionsAtPoint = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_DEFAULT);
            if (rgnRegionsAtPoint == null || rgnRegionsAtPoint.length == 0)
            {
                return null;
            }
        }
        return rgnRegionsAtPoint;
    }
    public static int getMinMissionDifficultyAtLocation(location locTest) throws InterruptedException
    {
        region[] rgnRegionsAtPoint = getRegionsWithMissionAtPoint(locTest, regions.MISSION_OTHER);
        if (rgnRegionsAtPoint == null || rgnRegionsAtPoint.length == 0)
        {
            rgnRegionsAtPoint = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_DEFAULT);
            if (rgnRegionsAtPoint == null || rgnRegionsAtPoint.length == 0)
            {
                return 10;
            }
        }
        int minDifficulty = 90;
        for (int i = 0; i < rgnRegionsAtPoint.length; i++)
        {
            int regionMinDifficulty = rgnRegionsAtPoint[i].getMinDifficultyType();
            if (minDifficulty > regionMinDifficulty)
            {
                minDifficulty = regionMinDifficulty;
            }
        }
        return minDifficulty;
    }
    public static int getMinDifficultyForPlanet(String planet) throws InterruptedException
    {
        int difficulty = 1;
        if (planet.equals("") || planet.length() <= 0)
        {
            return difficulty;
        }
        dictionary planetLevels = utils.dataTableGetRow(PLANET_LEVEL_TABLE, planet);
        if (planetLevels != null)
        {
            difficulty = planetLevels.getInt("minLevel");
        }
        else 
        {
            difficulty = -1;
        }
        if (difficulty < 1)
        {
            difficulty = 1;
        }
        return difficulty;
    }
    public static int getMaxDifficultyForPlanet(String planet) throws InterruptedException
    {
        int difficulty = 90;
        if (planet.equals("") || planet.length() <= 0)
        {
            return difficulty;
        }
        dictionary planetLevels = utils.dataTableGetRow(PLANET_LEVEL_TABLE, planet);
        if (planetLevels != null)
        {
            difficulty = planetLevels.getInt("maxLevel");
        }
        else 
        {
            difficulty = -1;
        }
        if (difficulty > 90)
        {
            difficulty = 90;
        }
        return difficulty;
    }
    public static int getMinDifficultyForLocation(location locTest) throws InterruptedException
    {
        region[] rgnRegionList = getDifficultyRegionsAtLocation(locTest);
        if (rgnRegionList == null || rgnRegionList.length == 0)
        {
            return 0;
        }
        int minDifficulty = Integer.MAX_VALUE;
        for (int i = 0; i < rgnRegionList.length; i++)
        {
            int regionMinDifficulty = rgnRegionList[i].getMinDifficultyType();
            if (minDifficulty > regionMinDifficulty)
            {
                minDifficulty = regionMinDifficulty;
            }
        }
        return minDifficulty;
    }
    public static int getMaxDifficultyForLocation(location locTest) throws InterruptedException
    {
        region[] rgnRegionList = getDifficultyRegionsAtLocation(locTest);
        if (rgnRegionList == null || rgnRegionList.length == 0)
        {
            return Integer.MAX_VALUE;
        }
        int maxDifficulty = Integer.MIN_VALUE;
        for (int i = 0; i < rgnRegionList.length; i++)
        {
            int regionMaxDifficulty = rgnRegionList[i].getMaxDifficultyType();
            if (maxDifficulty < regionMaxDifficulty)
            {
                maxDifficulty = regionMaxDifficulty;
            }
        }
        return maxDifficulty;
    }
    public static int capMinDifficultyForLocation(location locTest, int intDifficulty) throws InterruptedException
    {
        int intMinRegionDifficulty = getMinDifficultyForLocation(locTest);
        if (intDifficulty < intMinRegionDifficulty)
        {
            intDifficulty = intMinRegionDifficulty;
        }
        return intDifficulty;
    }
    public static int capMaxDifficultyForLocation(location locTest, int intDifficulty) throws InterruptedException
    {
        int intMaxRegionDifficulty = getMaxDifficultyForLocation(locTest);
        if (intDifficulty > intMaxRegionDifficulty)
        {
            intDifficulty = intMaxRegionDifficulty;
        }
        return intDifficulty;
    }
    public static boolean destroyLocationObject(obj_id locationObject) throws InterruptedException
    {
        if (isIdValid(locationObject))
        {
            messageTo(locationObject, "handlerDestroyLocationObject", null, 1, false);
            return true;
        }
        return false;
    }
    public static String getBuildoutAreaName(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object) || !exists(object))
        {
            return NO_AREA;
        }
        return getBuildoutAreaName(getLocation(trial.getTop(object)));
    }
    public static String getBuildoutAreaName(location loc) throws InterruptedException
    {
        if (isIdValid(loc.cell))
        {
            obj_id topBuilding = trial.getTop(loc.cell);
            loc = getLocation(topBuilding);
        }
        String buildout_table = "datatables/buildout/areas_" + loc.area + ".iff";
        float locX = loc.x;
        float locZ = loc.z;
        if (loc.area.indexOf("space_npe_falcon") >= 0 || !dataTableOpen(buildout_table))
        {
            return NO_AREA;
        }
        int rows = dataTableGetNumRows(buildout_table);
        String area_name = NO_AREA;
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(buildout_table, i);
            float xMin = dict.getFloat("x1");
            float xMax = dict.getFloat("x2");
            float zMin = dict.getFloat("z1");
            float zMax = dict.getFloat("z2");
            if (locX >= xMin && locX <= xMax && locZ >= zMin && locZ <= zMax)
            {
                area_name = dict.getString("area");
            }
        }
        return area_name;
    }
    public static int getBuildoutAreaRow(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object) || !exists(object))
        {
            return -1;
        }
        return getBuildoutAreaRow(getLocation(trial.getTop(object)));
    }
    public static int getBuildoutAreaRow(location loc) throws InterruptedException
    {
        if (isIdValid(loc.cell))
        {
            obj_id topBuilding = trial.getTop(loc.cell);
            loc = getLocation(topBuilding);
        }
        String buildout_table = "datatables/buildout/areas_" + loc.area + ".iff";
        float locX = loc.x;
        float locZ = loc.z;
        if (loc.area.indexOf("space_npe_falcon") >= 0 || !dataTableOpen(buildout_table))
        {
            return -1;
        }
        int rows = dataTableGetNumRows(buildout_table);
        int row = -1;
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(buildout_table, i);
            float xMin = dict.getFloat("x1");
            float xMax = dict.getFloat("x2");
            float zMin = dict.getFloat("z1");
            float zMax = dict.getFloat("z2");
            if (locX >= xMin && locX <= xMax && locZ >= zMin && locZ <= zMax)
            {
                row = i;
            }
        }
        return row;
    }
    public static boolean isInRegion(obj_id player, String regionName) throws InterruptedException
    {
        region[] regionList = getRegionsAtPoint(getLocation(player));
        obj_id planetId = getPlanetByName(getLocation(player).area);
        if (regionList == null || regionList.length == 0)
        {
            return false;
        }
        for (int i = 0; i < regionList.length; i++)
        {
            if (regionList[i] == null || (regionList[i].getName()).length() <= 0)
            {
                continue;
            }
            if (regionName.equals(regionList[i].getName()))
            {
                return true;
            }
        }
        return false;
    }
}
