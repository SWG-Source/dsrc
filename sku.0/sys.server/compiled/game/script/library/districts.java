package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.regions;

public class districts extends script.base_script
{
    public districts()
    {
    }
    public static final int DISTRICT_SIZE = 256;
    public static final int PLANET_X_MIN = -8192;
    public static final int PLANET_Z_MIN = -8192;
    public static final int PLANET_X_MAX = 8192;
    public static final int PLANET_Z_MAX = 8192;
    public static final int PLANET_DISTRICT_WIDTH = (PLANET_X_MAX - PLANET_X_MIN) / DISTRICT_SIZE;
    public static final int PLANET_DISTRICT_LENGTH = (PLANET_Z_MAX - PLANET_Z_MIN) / DISTRICT_SIZE;
    public static final int PLANET_DISTRICTS_TOTAL = PLANET_DISTRICT_WIDTH * PLANET_DISTRICT_LENGTH;
    public static final string_id DISTRICT_FICTIONAL_NAME = new string_id("region_names", "municipal_district");
    public static final String VAR_DISTRICT_ID = "district.id";
    public static final String VAR_DISTRICT_LOC = "district.loc";
    public static final String VAR_DISTRICT_POLLBOOTH = "district.pollbooth";
    public static final String VAR_DISTRICT_CITYHALL = "district.cityhall";
    public static final String VAR_DISTRICT_RESIDENTS = "district.residents";
    public static final String VAR_DISTRICT_MASTER_DISTRICT = "district.master_district";
    public static final String VAR_DISTRICT_JOINED_DISTRICTS = "district.joined_districts";
    public static final String VAR_DISTRICT_IS_MASTER_DISTRICT = "district.is_master";
    public static final String VAR_PLANET_DISTRICT_REGISTER = "districts.register";
    public static final String VAR_PLANET_DISTRICT_OBJ_IDS = "districts.obj_ids";
    public static final String SCRIPT_CITIES_PLANET = "systems.player_run_cities.planet";
    public static int calculateDistrictNumber(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return -1;
        }
        if ((loc.x > PLANET_X_MAX) || (loc.x < PLANET_X_MIN) || (loc.z > PLANET_Z_MAX) || (loc.z < PLANET_Z_MIN))
        {
            return -1;
        }
        int gridColumn = (int)(loc.x / DISTRICT_SIZE);
        int gridRow = (int)(loc.z / DISTRICT_SIZE);
        int districtNumber = (gridRow * PLANET_DISTRICT_WIDTH) + gridColumn;
        return districtNumber;
    }
    public static location calculateDistrictCoordinate(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return null;
        }
        if ((loc.x > PLANET_X_MAX) || (loc.x < PLANET_X_MIN) || (loc.z > PLANET_Z_MAX) || (loc.z < PLANET_Z_MIN))
        {
            return null;
        }
        int gridColumn = (int)(loc.x / DISTRICT_SIZE);
        int gridRow = (int)(loc.z / DISTRICT_SIZE);
        location retLoc = new location();
        retLoc.x = gridColumn * DISTRICT_SIZE;
        retLoc.z = gridRow * DISTRICT_SIZE;
        return retLoc;
    }
    public static location calculateDistrictCoordinate(int districtId) throws InterruptedException
    {
        if (districtId < 0)
        {
            return null;
        }
        int gridRow = districtId / PLANET_DISTRICT_WIDTH;
        int gridColumn = districtId % PLANET_DISTRICT_WIDTH;
        location retLoc = new location();
        retLoc.x = gridColumn * DISTRICT_SIZE;
        retLoc.z = gridRow * DISTRICT_SIZE;
        return retLoc;
    }
    public static boolean registerDistrictWithPlanet(region district) throws InterruptedException
    {
        return true;
    }
}
