package script.library;

import script.*;

import java.util.Vector;

public class planetary_map extends script.base_script
{
    public planetary_map()
    {
    }
    public static final byte NO_FLAG = 0;
    public static final float MAX_CLUMP_DISTANCE = 32f;
    public static final String TBL = "datatables/planet_map/map_locations.iff";
    public static final String COL_TEMPLATE = "TEMPLATE";
    public static final String COL_NAME = "PM_NAME";
    public static final String COL_CATEGORY = "PM_CATEGORY";
    public static final String COL_SUBCATEGORY = "PM_SUBCATEGORY";
    public static final String COL_TYPE = "PM_TYPE";
    public static final String COL_FLAGS = "PM_FLAG";
    public static final String TEMPLATE_LOCATION_BASE = "object/tangible/planet_map_location/map_location_base.iff";
    public static final String TEMPLATE_LOCATION_CITY = "object/tangible/planet_map_location/city.iff";
    public static final String CITY = "city";
    public static final String CITY_LOCATION_CATEGORY = "city";
    public static final String VAR_PM_BASE = "planetMap";
    public static final String VAR_PM_NAME = VAR_PM_BASE + ".name";
    public static final String VAR_PM_CATEGORY = VAR_PM_BASE + ".category";
    public static final String VAR_PM_SUBCATEGORY = VAR_PM_BASE + ".subcategory";
    public static final String VAR_PM_TYPE = VAR_PM_BASE + ".type";
    public static final String VAR_PM_FLAGS = VAR_PM_BASE + ".flags";
    public static final String STF_NAME = "map_loc_n";
    public static final String CAT_TRAINER = "trainer";
    public static final String CAT_VENDOR = "vendor";
    public static final String SUB_VENDOR_JUNK = "vendor_junk";
    public static final String STF_MAP_LOC = "map_loc";
    public static final String CAT_TERMINAL = "terminal";
    public static final String SCRIPTVAR_FIND_SUI = "find.sui";
    public static final String SCRIPTVAR_FIND_PARAMS = "find.params";
    public static final string_id SID_NO_REGISTERED_LOCS = new string_id("find_display", "no_registered_locs");
    public static final string_id SID_FIND_TITLE = new string_id("base_player", "find_title");
    public static final string_id SID_FIND_PROMPT = new string_id("base_player", "find_prompt");
    public static boolean addMapLocation(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        if (instance.isInInstanceArea(self))
        {
            removePlanetaryMapLocation(self);
            return false;
        }
        int got = getGameObjectType(self);
        if (isGameObjectTypeOf(got, GOT_creature))
        {
            return addCreatureLocation(self);
        }
        else if (isGameObjectTypeOf(got, GOT_terminal))
        {
            return addTerminalLocation(self);
        }
        String planet = getCurrentSceneName();
        if ((planet == null) || (planet.equals("")))
        {
            LOG("planetaryMapLocation", "planet scene name = " + planet);
            return false;
        }
        String template = getTemplateName(self);
        if ((template == null) || (template.equals("")))
        {
            return false;
        }
        location here = getLocation(self);
        if (isGameObjectTypeOf(got, GOT_building))
        {
            location ejection = getBuildingEjectLocation(self);
            if (ejection != null)
            {
                here = ejection;
            }
        }
        if (here == null)
        {
            return false;
        }
        String locationName = "";
        String category = "";
        String subCategory = "";
        int type = 0;
        int flags = 0;
        if (template.equals(TEMPLATE_LOCATION_CITY))
        {
            category = CITY;
            locationName = getCityRegionName(here);
        }
        else 
        {
            dictionary row = dataTableGetRow(TBL, template);
            if ((row == null) || (row.isEmpty()))
            {
                debugServerConsoleMsg(self, "WARNING: map_location -> obj_id " + self + " unable to locate template (" + template + ") in datatable!");
            }
            else 
            {
                locationName = row.getString(COL_NAME);
                category = row.getString(COL_CATEGORY);
                subCategory = row.getString(COL_SUBCATEGORY);
                type = row.getInt(COL_TYPE);
                flags = row.getInt(COL_FLAGS);
            }
        }
        if (hasObjVar(self, VAR_PM_BASE))
        {
            if (hasObjVar(self, VAR_PM_CATEGORY))
            {
                category = getStringObjVar(self, VAR_PM_CATEGORY);
            }
            if (hasObjVar(self, VAR_PM_SUBCATEGORY))
            {
                subCategory = getStringObjVar(self, VAR_PM_SUBCATEGORY);
            }
            if (hasObjVar(self, VAR_PM_NAME))
            {
                locationName = getStringObjVar(self, VAR_PM_NAME);
            }
            if (hasObjVar(self, VAR_PM_TYPE))
            {
                type = getIntObjVar(self, VAR_PM_TYPE);
            }
            if (hasObjVar(self, VAR_PM_FLAGS))
            {
                flags = getIntObjVar(self, VAR_PM_FLAGS);
            }
        }
        if (player_structure.isCivic(self))
        {
            locationName = cityGetName(getCityAtLocation(getLocation(self), 0));
            flags = MLF_INACTIVE;
        }
        else if (player_structure.isCommercial(self))
        {
            if (hasObjVar(self, player_structure.VAR_SIGN_NAME))
            {
                locationName = getStringObjVar(self, player_structure.VAR_SIGN_NAME);
            }
            else 
            {
                int city_id = getCityAtLocation(getLocation(self), 0);
                if (city_id > 0)
                {
                    locationName = cityGetName(city_id);
                }
            }
            flags = MLF_INACTIVE;
        }
        if (hasObjVar(self, "healing.canhealwound") || (hasObjVar(self, "healing.canhealshock")))
        {
            flags = MLF_INACTIVE;
        }
        if (category == null || category.equals("") || category.equals("unknown"))
        {
            String myFac = factions.getFaction(self);
            if (myFac != null)
            {
                if (myFac.equals(factions.FACTION_IMPERIAL) || myFac.equals(factions.FACTION_REBEL))
                {
                    category = toLower(myFac);
                }
            }
        }
        if (category != null && !category.equals("") && !category.equals("unknown"))
        {
            category = toLower(category);
            if ((locationName == null) || (locationName.equals("")))
            {
                locationName = getCityRegionName(here);
                if ((locationName == null) || (locationName.equals("")))
                {
                    locationName = getEncodedName(self);
                }
            }
            if (subCategory == null)
            {
                subCategory = "";
            }
            return addPlanetaryMapLocation(self, locationName, (int)here.x, (int)here.z, category, subCategory, type, (byte)flags);
        }
        return false;
    }
    public static String getCityRegionName(location here) throws InterruptedException
    {
        if (here == null)
        {
            return null;
        }
        region[] r = getRegionsWithGeographicalAtPoint(here, regions.GEO_CITY);
        if ((r == null) || (r.length == 0))
        {
            return null;
        }
        return r[0].getName();
    }
    public static void removeMapLocation(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return;
        }
        removePlanetaryMapLocation(self);
    }
    public static boolean addCreatureLocation(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        String planet = getCurrentSceneName();
        if ((planet == null) || (planet.equals("")))
        {
            return false;
        }
        location here = getWorldLocation(self);
        if (here == null)
        {
            return false;
        }
        int got = getGameObjectType(self);
        if (isGameObjectTypeOf(got, GOT_creature) || (got == GOT_vendor))
        {
            if (!isPlayer(self))
            {
                final String cType = getCreatureName(self);
                if ((cType == null) || (cType.equals("")))
                {
                    return false;
                }
                if (cType.equals("junk_dealer"))
                {
                    return addPlanetaryMapLocation(self, getEncodedName(self), (int)here.x, (int)here.z, CAT_VENDOR, SUB_VENDOR_JUNK, MLT_STATIC, NO_FLAG);
                }
                else if (cType.startsWith("trainer_"))
                {
                    return addPlanetaryMapLocation(self, getEncodedName(self), (int)here.x, (int)here.z, CAT_TRAINER, cType, MLT_STATIC, NO_FLAG);
                }
            }
        }
        return false;
    }
    public static boolean addTerminalLocation(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        String planet = getCurrentSceneName();
        if ((planet == null) || (planet.equals("")))
        {
            LOG("planetaryMapLocation", "(" + self + ") has unknown scene??");
            return false;
        }
        location here = getWorldLocation(self);
        if (here == null)
        {
            LOG("planetaryMapLocation", "(" + self + ") has null location??");
            return false;
        }
        obj_id topMost = getTopMostContainer(self);
        if (isIdValid(topMost))
        {
            if (isGameObjectTypeOf(getGameObjectType(topMost), GOT_building))
            {
                if (hasObjVar(topMost, hq.VAR_HQ_BASE))
                {
                    return false;
                }
            }
        }
        int got = getGameObjectType(self);
        if (isGameObjectTypeOf(got, GOT_terminal))
        {
            string_id sid_name = getNameStringId(self);
            if (sid_name != null && !sid_name.equals(""))
            {
                String subCat = sid_name.getAsciiId();
                if (dataTableSearchColumnForString(subCat, "name", "datatables/player/planet_map_cat.iff") < 0)
                {
                    LOG("planetaryMapLocation", "(" + self + ") unable to locate subCat '" + subCat + "' in the planet_map_cat.iff");
                    return false;
                }
                String cityName = getCityRegionName(here);
                if (cityName != null)
                {
                    map_location[] mapLocs = getPlanetaryMapLocations(CITY, null);
                    if (mapLocs != null && mapLocs.length > 0)
                    {
                        String cardinalString;
                        for (map_location mapLoc : mapLocs) {
                            if ((mapLoc.getLocationName()).equals(cityName)) {
                                cardinalString = utils.getStringCardinalDirection(new location(mapLoc.getX(), 0, mapLoc.getY()), here, true);
                                if (cardinalString != null && !cardinalString.equals("")) {
                                    return addPlanetaryMapLocation(self, getString(utils.unpackString(cityName)) + " (" + cardinalString + ")", (int) here.x, (int) here.z, CAT_TERMINAL, subCat, MLT_STATIC, NO_FLAG);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
                return addPlanetaryMapLocation(self, getEncodedName(self), (int)here.x, (int)here.z, CAT_TERMINAL, subCat, MLT_STATIC, NO_FLAG);
            }
        }
        LOG("planetaryMapLocation", "(" + self + ") has failed to register for an unknown reason!");
        return false;
    }
    public static map_location findClosestLocation(obj_id target, map_location[] map_locs) throws InterruptedException
    {
        if (!isIdValid(target) || (map_locs == null) || (map_locs.length == 0))
        {
            return null;
        }
        return findClosestLocation(getWorldLocation(target), map_locs);
    }
    public static map_location findClosestLocation(location here, map_location[] map_locs) throws InterruptedException
    {
        if ((map_locs == null) || (map_locs.length == 0))
        {
            return null;
        }
        float min = Float.POSITIVE_INFINITY;
        int locIdx = -1;
        float x;
        float z;
        location there;
        float dist;

        for (int n = 0; n < map_locs.length; n++)
        {
            x = (float)(map_locs[n].getX());
            z = (float)(map_locs[n].getY());
            there = new location(x, 0, z);
            dist = utils.getDistance2D(here, there);
            if (dist < min)
            {
                min = dist;
                locIdx = n;
            }
        }
        if (locIdx > -1)
        {
            return map_locs[locIdx];
        }
        return null;
    }
    public static map_location findClosestLocation(obj_id target, String category, String subcategory) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        return findClosestLocation(getWorldLocation(target), category, subcategory);
    }
    public static map_location findClosestLocation(location here, String category, String subcategory) throws InterruptedException
    {
        if ((category == null) || (category.equals("")))
        {
            return null;
        }
        if (subcategory == null)
        {
            subcategory = "";
        }
        map_location[] map_locs = getPlanetaryMapLocations(category, subcategory);
        if ((map_locs == null) || (map_locs.length == 0))
        {
            return null;
        }
        return findClosestLocation(here, map_locs);
    }
    public static int showFindSui() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self))
        {
            return -1;
        }
        closeFindSui();
        String[] cats = getUniquePlanetaryMapCategories(null);
        if ((cats == null) || (cats.length == 0))
        {
            sendSystemMessage(self, SID_NO_REGISTERED_LOCS);
            return -1;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        Vector params = new Vector();
        params.setSize(0);

        String[] subs;

        for (String cat : cats) {
            subs = getUniquePlanetaryMapCategories(cat);
            if ((subs != null) && (subs.length > 0)) {
                LOG("find", "showFindSui: cat = " + cat + " sub count = " + subs.length);
                for (String sub : subs) {
                    entries = utils.addElement(entries, getString(new string_id("map_loc_cat_n", cat)) + ": " + getString(new string_id("map_loc_cat_n", sub)));
                    params = utils.addElement(params, sub);
                }
            } else {
                entries = utils.addElement(entries, "@map_loc_cat_n:" + cat);
                params = utils.addElement(params, cat);
            }
        }
        if ((entries != null) && (entries.size() > 0))
        {
            int pid = sui.listbox(self, self, utils.packStringId(SID_FIND_PROMPT), sui.OK_CANCEL, utils.packStringId(SID_FIND_TITLE), entries, "handleFindSui");
            if (pid > -1)
            {
                utils.setScriptVar(self, SCRIPTVAR_FIND_SUI, pid);
                utils.setBatchScriptVar(self, SCRIPTVAR_FIND_PARAMS, params);
                return pid;
            }
        }
        return -1;
    }
    public static void closeFindSui() throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(self, SCRIPTVAR_FIND_SUI))
        {
            forceCloseSUIPage(utils.getIntScriptVar(self, SCRIPTVAR_FIND_SUI));
            cleanupFindSui();
        }
    }
    public static void cleanupFindSui() throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVar(self, SCRIPTVAR_FIND_SUI);
        utils.removeBatchScriptVar(self, SCRIPTVAR_FIND_PARAMS);
    }
    public static String[] getUniquePlanetaryMapCategories(String category) throws InterruptedException
    {
        String[] cats = getPlanetaryMapCategories(category);
        if ((cats == null) || (cats.length == 0))
        {
            return null;
        }
        Vector unique = new Vector();
        unique.setSize(0);
        for (String cat : cats) {
            if (utils.getElementPositionInArray(unique, cat) == -1) {
                unique = utils.addElement(unique, cat);
            }
        }
        if ((unique == null) || (unique.size() == 0))
        {
            return null;
        }
        String[] _unique = new String[unique.size()];
        unique.toArray(_unique);
        return _unique;
    }
    public static boolean updateFacilityActive(obj_id facility, boolean isActive) throws InterruptedException
    {
        if (!isIdValid(facility))
        {
            return false;
        }
        map_location maploc = getPlanetaryMapLocation(facility);
        if (maploc == null)
        {
            return false;
        }
        if (!maploc.isActive() && !maploc.isInactive())
        {
            return false;
        }
        if (maploc.isActive() && maploc.isInactive())
        {
            return false;
        }
        byte flags;
        if (isActive)
        {
            flags = MLF_ACTIVE;
        }
        else 
        {
            flags = MLF_INACTIVE;
        }
        removePlanetaryMapLocation(facility);
        return addPlanetaryMapLocation(facility, maploc.getLocationName(), (int)maploc.getX(), (int)maploc.getY(), maploc.getCategory(), maploc.getSubCategory(), MLT_STATIC, flags);
    }
}
