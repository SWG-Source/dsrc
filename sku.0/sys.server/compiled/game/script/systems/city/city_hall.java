package script.systems.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.city;
import script.library.prose;
import script.library.planetary_map;
import script.library.money;

public class city_hall extends script.base_script
{
    public city_hall()
    {
    }
    public static final string_id NEW_CITY_SUBJECT = new string_id("city/city", "new_city_subject");
    public static final string_id NEW_CITY_BODY = new string_id("city/city", "new_city_body");
    public static final string_id NEW_CITY_SUCCESS_SUBJECT = new string_id("city/city", "new_city_success_subject");
    public static final string_id NEW_CITY_SUCCESS_BODY = new string_id("city/city", "new_city_success_body");
    public static final string_id NEW_CITY_FAIL_SUBJECT = new string_id("city/city", "new_city_fail_subject");
    public static final string_id NEW_CITY_FAIL_BODY = new string_id("city/city", "new_city_fail_body");
    public static final string_id STRUCTURE_DESTROYED_RADIUS_SUBJECT = new string_id("city/city", "structure_destroyed_subject");
    public static final string_id STRUCTURE_DESTROYED_RADIUS_BODY = new string_id("city/city", "structure_destroyed_radius_body");
    public static final string_id CITY_MAINT_SUBJECT = new string_id("city/city", "city_maint_subject");
    public static final string_id CITY_MAINT_BODY = new string_id("city/city", "city_maint_body");
    public static final string_id BUSTED_CIVIC_CAP_SUBJECT = new string_id("city/city", "busted_civic_cap_subject");
    public static final string_id BUSTED_CIVIC_CAP_BODY = new string_id("city/city", "busted_civic_cap_body");
    public static final string_id CITIZEN_OVERAGE_WARNING_SUBJECT = new string_id("city/city", "safe_citizen_overage_subject");
    public static final string_id CITIZEN_OVERAGE_WARNING_BODY = new string_id("city/city", "safe_citizen_overage_body");
    public static final int SAFE_HOUSE_GRACE_PERIOD = 60 * 60 * 24 * 4;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeNewCity", null, 3.f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        if (city.getCityRank(city_id) < 2)
        {
            city.removeTaxes(city_id);
        }
        if ((city.getCityRank(city_id) < 3) && city.isCityRegistered(city_id))
        {
            city.unregisterCity(city_id);
        }
        location hallLoc = getLocation(self);
        location cityLoc = cityGetLocation(city_id);
        float dx = cityLoc.x - hallLoc.x;
        float dz = cityLoc.z - hallLoc.z;
        if (dx * dx + dz * dz > 4.0f)
        {
            messageTo(self, "updateCityLocation", null, 30.f, false);
        }
        int CITY_VERSION = getIntObjVar(self, "CITY_VERSION");
        if (CITY_VERSION != city.CITY_VERSION)
        {
            setObjVar(self, "CITY_VERSION", city.CITY_VERSION);
            obj_id[] citizens = cityGetCitizenIds(city_id);
            if (citizens != null)
            {
                for (int i = 0; i < citizens.length; i++)
                {
                    String cname = cityGetCitizenName(city_id, citizens[i]);
                    prose_package bodypp = prose.getPackage(new string_id("city/city", "city_version_update_body_" + city.CITY_VERSION), city.CITY_VERSION);
                    utils.sendMail(new string_id("city/city", "city_version_update_subject_" + city.CITY_VERSION), bodypp, cname, "Planetary Civic Authority");
                }
            }
            if (city.CITY_VERSION == 3)
            {
                for (int i = 0; i < citizens.length; i++)
                {
                    city.setCitizenAllegiance(city_id, citizens[i], null);
                }
                removeObjVar(self, "candidate_list");
            }
            if (city.CITY_VERSION == 4)
            {
                if (hasObjVar(self, "spec_stamp"))
                {
                    removeObjVar(self, "spec_stamp");
                }
                city.setSpecialization(city_id, null);
            }
        }
        if (!hasObjVar(self, player_structure.VAR_ADMIN_LIST))
        {
            obj_id ownerId = player_structure.getStructureOwnerObjId(self);
            String[] adminList = new String[1];
            adminList[0] = "" + ownerId;
            setObjVar(self, player_structure.VAR_ADMIN_LIST, adminList);
        }
        validateSafeCitizenCount(self, city_id);
        int factionId = 0;
        if (hasObjVar(self, "cityFactionAlign"))
        {
            factionId = getIntObjVar(self, "cityFactionAlign");
        }
        citySetFaction(city_id, factionId, false);
        int numberOfRetry = 0;
        dictionary params = new dictionary();
        params.put("number_of_retry", numberOfRetry);
        messageTo(self, "retryDepersistCityFactionalAlignment", params, 60.0f, false);
        String region = null;
        int regionTimeStartDefend = 0;
        int regionTimeEndDefend = 0;
        if (hasObjVar(self, "cityGcwRegionDefender.region"))
        {
            region = getStringObjVar(self, "cityGcwRegionDefender.region");
        }
        if (hasObjVar(self, "cityGcwRegionDefender.timeBegin"))
        {
            regionTimeStartDefend = getIntObjVar(self, "cityGcwRegionDefender.timeBegin");
        }
        if (hasObjVar(self, "cityGcwRegionDefender.timeEnd"))
        {
            regionTimeEndDefend = getIntObjVar(self, "cityGcwRegionDefender.timeEnd");
        }
        if ((region != null) && (region.length() > 0) && (regionTimeStartDefend > 0) && (regionTimeEndDefend <= 0))
        {
            citySetGcwDefenderRegion(city_id, region, regionTimeStartDefend, false);
            numberOfRetry = 0;
            params = new dictionary();
            params.put("number_of_retry", numberOfRetry);
            messageTo(self, "retryDepersistCityGcwRegionDefender", params, 60.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("player_city", "Handling OnDestroy for a city hall.  Hall: " + self);
        obj_id cityMaster = getMasterCityObject();
        dictionary outparams = new dictionary();
        outparams.put("city_hall", self);
        messageTo(cityMaster, "confirmCityRemoved", outparams, 600, true);
        int city_id = findCityByCityHall(self);
        obj_id[] structures = cityGetStructureIds(city_id);
        if (structures != null)
        {
            for (int i = 0; i < structures.length; i++)
            {
                if (!isIdValid(structures[i]))
                {
                    continue;
                }
                if (structures[i] == self)
                {
                    continue;
                }
                if (player_structure.isCivic(structures[i]))
                {
                    messageTo(structures[i], "msgDestroyStructure", null, 0.f, true);
                }
                else if (!city.isNormalStructure(city_id, structures[i]))
                {
                    messageTo(structures[i], "requestDestroy", null, 0.f, true);
                }
            }
        }
        if (cityExists(city_id))
        {
            CustomerServiceLog("player_city", "Handling removal of city.  City: " + city_id + " Hall: " + self);
            removeCity(city_id);
        }
        return SCRIPT_CONTINUE;
    }
    public int updateCityLocation(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        city.updateLocation(city_id, getLocation(self));
        return SCRIPT_CONTINUE;
    }
    public int initializeNewCity(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("player_city", "Initializing new city for structure " + self);
        int max_radius = dataTableGetInt(city.RANK_TABLE, city.RANK_MAX, city.RANK_RADIUS);
        int city_id = getCityAtLocation(getLocation(self), max_radius);
        if (cityExists(city_id))
        {
            player_structure.destroyStructure(self, true);
            CustomerServiceLog("player_city", "Failed to create city for structure " + self + " because a city already existed here.");
            return SCRIPT_CONTINUE;
        }
        obj_id owner = player_structure.getStructureOwnerObjId(self);
        if (!isIdValid(owner))
        {
            LOG("PlayerCity", "Critical failure: No structure owner when initializing new city.");
            player_structure.destroyStructure(self, true);
            return SCRIPT_CONTINUE;
        }
        int radius = dataTableGetInt(city.RANK_TABLE, city.RANK_MIN, city.RANK_RADIUS);
        String newCityName = "A New City";
        if (hasObjVar(self, "cityName"))
        {
            newCityName = getStringObjVar(self, "cityName");
            removeObjVar(self, "cityName");
        }
        location cityLoc = getLocation(self);
        int new_city_id = createCity(newCityName, self, cityLoc, radius, owner, 0, 0, 0, null, 0, false, null, null, null);
        citySetStructureInfo(new_city_id, self, city.SF_COST_CITY_HALL, true);
        setObjVar(self, "lastUpdateTime", getGameTime());
        setObjVar(self, "currentInterval", 60 * 60 * 24 * 7);
        setBuildingCityId(self, new_city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Created a new city.  City hall: " + self + " Mayor: " + owner + " City: " + new_city_id);
        setObjVar(city_hall, "CITY_VERSION", city.CITY_VERSION);
        citySetCitizenInfo(new_city_id, owner, getName(owner), owner, city.CP_CITIZEN);
        obj_id declaredStructure = getHouseId(owner);
        if (isIdValid(declaredStructure))
        {
            setHouseId(owner, obj_id.NULL_ID);
            messageTo(declaredStructure, "removeResidentVar", null, 0.f, true);
        }
        prose_package bodypp = prose.getPackage(NEW_CITY_BODY, getName(owner));
        utils.sendMail(NEW_CITY_SUBJECT, bodypp, getName(owner), "Planetary Civic Authority");
        dictionary outparams = new dictionary();
        outparams.put("cityLoc", cityLoc);
        outparams.put("radius", radius);
        outparams.put("city_id", new_city_id);
        messageTo(self, "addNewCityStructures", outparams, 30.f, false);
        messageTo(self, "validateCity", null, 86400.f, true);
        setObjVar(self, "founder.id", getName(owner));
        setObjVar(self, "founder.time", getGameTime());
        return SCRIPT_CONTINUE;
    }
    public int addNewCityStructures(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        location cityLoc = params.getLocation("cityLoc");
        int radius = params.getInt("radius");
        obj_id[] range_objects = getObjectsInRange(cityLoc, radius);
        if (range_objects != null)
        {
            for (int i = 0; i < range_objects.length; i++)
            {
                if (isIdValid(range_objects[i]) && hasScript(range_objects[i], player_structure.SCRIPT_PERMANENT_STRUCTURE))
                {
                    if (getContainedBy(range_objects[i]) == obj_id.NULL_ID)
                    {
                        city.addStructureToCity(range_objects[i], city_id);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int validateCity(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        obj_id mayor = cityGetLeader(city_id);
        int total_citizens = 0;
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens != null)
        {
            total_citizens = citizens.length;
        }
        if (total_citizens >= dataTableGetInt(city.RANK_TABLE, 0, city.RANK_POPULATION))
        {
            String mname = cityGetCitizenName(city_id, mayor);
            prose_package bodypp = prose.getPackage(NEW_CITY_SUCCESS_BODY, mayor);
            utils.sendMail(NEW_CITY_SUCCESS_SUBJECT, bodypp, mname, "Planetary Civic Authority");
        }
        else
        {
            String mname = cityGetCitizenName(city_id, mayor);
            prose_package bodypp = prose.getPackage(NEW_CITY_FAIL_BODY, mayor);
            utils.sendMail(NEW_CITY_FAIL_SUBJECT, bodypp, mname, "Planetary Civic Authority");
            player_structure.destroyStructure(self, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int registerVoteTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = params.getObjId("terminal");
        setObjVar(self, "vote_terminal", terminal);
        return SCRIPT_CONTINUE;
    }
    public int resetVoteTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "currentInterval", params.getInt("currentInterval"));
        setObjVar(self, "lastUpdateTime", params.getInt("lastUpdateTime"));
        obj_id terminal = getObjIdObjVar(self, "vote_terminal");
        messageTo(terminal, "resetVoteTerminal", null, 0.f, true);
        return SCRIPT_CONTINUE;
    }
    public int expandCity(obj_id self, dictionary params) throws InterruptedException
    {
        int rank = params.getInt("rank") + 1;
        int radius = params.getInt("radius");
        int city_id = findCityByCityHall(self);
        location cityLoc = cityGetLocation(city_id);
        obj_id mayor = cityGetLeader(city_id);
        city.setRadius(city_id, radius);
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (!isIdValid(structures[i]))
            {
                continue;
            }
            if (!structures[i].isLoaded())
            {
                continue;
            }
            city.checkStructureValid(city_id, structures[i], rank);
        }
        obj_id[] range_objects = getObjectsInRange(cityLoc, radius);
        if (range_objects != null)
        {
            for (int i = 0; i < range_objects.length; i++)
            {
                if (isIdValid(range_objects[i]) && hasScript(range_objects[i], player_structure.SCRIPT_PERMANENT_STRUCTURE))
                {
                    if (getContainedBy(range_objects[i]) == obj_id.NULL_ID)
                    {
                        city.addStructureToCity(range_objects[i], city_id);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int contractCity(obj_id self, dictionary params) throws InterruptedException
    {
        int rank = params.getInt("rank") + 1;
        int radius = params.getInt("radius");
        int city_id = findCityByCityHall(self);
        location cityLoc = cityGetLocation(city_id);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        if (hasObjVar(city_hall, city.OBJVAR_DERANK_EXEMPT))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "city.mt_count", 0);
        utils.setScriptVar(self, "city.st_count", 0);
        utils.setScriptVar(self, "city.deco_count", 0);
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (!isIdValid(structures[i]))
            {
                continue;
            }
            if (!structures[i].isLoaded())
            {
                continue;
            }
            float dist = utils.getDistance2D(self, structures[i]);
            if (dist > radius)
            {
                if (!city.isNormalStructure(city_id, structures[i]))
                {
                    CustomerServiceLog("player_city", "City destroyed special outside new radius. City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + structures[i]);
                    destroyObject(structures[i]);
                    String structure_name = localize(getNameStringId(structures[i]));
                    prose_package bodypp = prose.getPackage(STRUCTURE_DESTROYED_RADIUS_BODY, structure_name, mayor_name);
                    utils.sendMail(STRUCTURE_DESTROYED_RADIUS_SUBJECT, bodypp, mayor_name, "City Hall");
                    continue;
                }
                city.removeStructureFromCity(city_id, structures[i]);
                if (player_structure.isCivic(structures[i]))
                {
                    CustomerServiceLog("player_city", "City destroyed structure outside new radius. City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structures[i]);
                    messageTo(structures[i], "msgDestroyStructure", null, 0.f, false);
                    String structure_name = localize(getNameStringId(structures[i]));
                    prose_package bodypp = prose.getPackage(STRUCTURE_DESTROYED_RADIUS_BODY, structure_name, mayor_name);
                    utils.sendMail(STRUCTURE_DESTROYED_RADIUS_SUBJECT, bodypp, mayor_name, "City Hall");
                }
            }
            else
            {
                city.checkStructureValid(city_id, structures[i], rank);
            }
        }
        utils.removeScriptVar(self, "city.mt_count");
        utils.removeScriptVar(self, "city.st_count");
        utils.removeScriptVar(self, "city.deco_count");
        if ((rank < 3) && city.isCityRegistered(city_id))
        {
            city.unregisterCity(city_id);
        }
        if (rank < 2)
        {
            city.removeTaxesSetRadius(city_id, radius);
        }
        else
        {
            city.setRadius(city_id, radius);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkCivicCap(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        int civic_count = city.getCivicCount(city_id);
        int max_civic = city.getMaxCivicCount(city_id);
        if (civic_count > max_civic)
        {
            int diff = civic_count - max_civic;
            prose_package bodypp = prose.getPackage(BUSTED_CIVIC_CAP_BODY, cityGetName(city_id), diff);
            utils.sendMail(BUSTED_CIVIC_CAP_SUBJECT, bodypp, mayor_name, "Planetary Civic Authority");
        }
        else if (hasObjVar(self, "city.civic_cap_penalty"))
        {
            removeObjVar(self, "city.civic_cap_penalty");
        }
        return SCRIPT_CONTINUE;
    }
    public int payMaintenance(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        utils.setScriptVar(self, "maint.paid", 0);
        payStructureMaint(self, city_id, self, true);
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (structures[i] == self)
            {
                continue;
            }
            if (city.isNormalStructure(city_id, structures[i]))
            {
                payStructureMaint(self, city_id, structures[i], false);
            }
            else
            {
                paySpecialMaint(self, city_id, structures[i]);
            }
        }
        messageTo(self, "reportTotalPaid", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int reportTotalPaid(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        int total_paid = utils.getIntScriptVar(self, "maint.paid");
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        prose_package bodypp = prose.getPackage(CITY_MAINT_BODY, mayor_name, total_paid);
        utils.sendMail(CITY_MAINT_SUBJECT, bodypp, mayor_name, "City Hall");
        return SCRIPT_CONTINUE;
    }
    public void payStructureMaint(obj_id self, int city_id, obj_id structure, boolean spec) throws InterruptedException
    {
        int cost = city.getStructureCost(city_id, structure);
        if (cost == 0)
        {
            return;
        }
        if (spec)
        {
            cost += city.cityGetSpecCost(city_id);
        }
        dictionary payparams = new dictionary();
        payparams.put("city_id", city_id);
        payparams.put("cost", cost);
        payparams.put("structure", structure);
        transferBankCreditsToNamedAccount(self, money.ACCT_CITY, cost, "handleMaintSuccess", "handleMaintFail", payparams);
    }
    public int handleMaintSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        int cost = params.getInt("cost");
        obj_id structure = params.getObjId("structure");
        messageTo(structure, "repairCityDamage", null, 0.f, true);
        int total_paid = utils.getIntScriptVar(self, "maint.paid");
        total_paid += cost;
        utils.setScriptVar(self, "maint.paid", total_paid);
        utils.moneyOutMetric(self, money.ACCT_CITY, cost);
        return SCRIPT_CONTINUE;
    }
    public int handleMaintFail(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        int cost = params.getInt("cost");
        obj_id structure = params.getObjId("structure");
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "City unable to pay for structure.  Damaging it.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structure);
        damageCityStructure(self, structure, cost);
        return SCRIPT_CONTINUE;
    }
    public void damageCityStructure(obj_id self, obj_id structure, int cost) throws InterruptedException
    {
        int city_id = findCityByCityHall(self);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String structure_name = localize(getNameStringId(structure));
        dictionary outparams = new dictionary();
        outparams.put("cost", cost);
        messageTo(structure, "takeCityDamage", outparams, 0.f, true);
    }
    public void paySpecialMaint(obj_id self, int city_id, obj_id structure) throws InterruptedException
    {
        int cost = city.getStructureCost(city_id, structure);
        if (cost == 0)
        {
            return;
        }
        dictionary payparams = new dictionary();
        payparams.put("city_id", city_id);
        payparams.put("cost", cost);
        payparams.put("structure", structure);
        transferBankCreditsToNamedAccount(self, money.ACCT_CITY, cost, "handleSpecMaintSuccess", "handleSpecMaintFail", payparams);
    }
    public int handleSpecMaintSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        int cost = params.getInt("cost");
        int total_paid = utils.getIntScriptVar(self, "maint.paid");
        total_paid += cost;
        utils.setScriptVar(self, "maint.paid", total_paid);
        utils.moneyOutMetric(self, money.ACCT_CITY, cost);
        return SCRIPT_CONTINUE;
    }
    public int handleSpecMaintFail(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        obj_id structure = params.getObjId("structure");
        String structure_name = localize(getNameStringId(structure));
        CustomerServiceLog("player_city", "City unable to pay for special.  Destroying it.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + structure);
        destroyObject(structure);
        prose_package bodypp = prose.getPackage(city.STRUCTURE_DESTROYED_MAINT_BODY, structure_name, mayor_name);
        utils.sendMail(city.STRUCTURE_DESTROYED_MAINT_SUBJECT, bodypp, mayor_name, "City Hall");
        return SCRIPT_CONTINUE;
    }
    public int setVoteInterval(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int cityVoteInterval = params.getInt("cityVoteInterval");
        setObjVar(self, "cityVoteInterval", cityVoteInterval);
        return SCRIPT_CONTINUE;
    }
    public int msgCheckMyCityMotd(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "city_citizen_message"))
        {
            String city_motd = getStringObjVar(self, "city_citizen_message");
            if (city_motd != null && city_motd.length() > 0)
            {
                obj_id player = params.getObjId("player");
                dictionary outparams = new dictionary();
                outparams.put("city_motd", city_motd);
                messageTo(player, "myCityMotdResponse", outparams, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int st_citySpecBonusCheck(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (city_id > 0 && city.cityHasSpec(city_id, city.SF_SPEC_STORYTELLER))
        {
            obj_id st_object = params.getObjId("queryObject");
            messageTo(st_object, "st_receivedCityResponse", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void validateSafeCitizenCount(obj_id city_hall, int city_id) throws InterruptedException
    {
        if (!isIdValid(city_hall))
        {
            CustomerServiceLog("player_city", "City Hall ID Invalid (" + city_hall + ") - Was attempting to Validate Safe Citizen Counts for City ID: " + city_id + ".");
            return;
        }
        int maxSafe = city.getMaxSafeHouseCount(city_id);
        int curSafe = city.getCurrentSafeHouseCount(city_id);
        if (curSafe > maxSafe)
        {
            CustomerServiceLog("player_city", "City: " + cityGetName(city_id) + "(" + city_id + ") has too many Safe Houses.  Max(" + maxSafe + "), Current Count(" + curSafe + ")");
            if (hasObjVar(city_hall, "city.safe_house_capacity_overload"))
            {
                int curTime = getCalendarTime();
                int overageTimeStamp = getIntObjVar(city_hall, "city.safe_house_capacity_overload");
                int gracePeriod = SAFE_HOUSE_GRACE_PERIOD;
                if (hasObjVar(city_hall, "qa.test_capacity_overload"))
                {
                    gracePeriod = 60 * 20;
                }
                if ((overageTimeStamp + gracePeriod) < curTime)
                {
                    int numCitizensToRemove = curSafe - maxSafe;
                    if (numCitizensToRemove > 0)
                    {
                        CustomerServiceLog("player_city", "City: " + cityGetName(city_id) + "(" + city_id + ") is still in violation of the Inactive Citizen Act after 4 days - We will be removing " + numCitizensToRemove + " Citizen(s) today");
                        forceRemoveSafeCitizen(city_id, numCitizensToRemove, city_hall);
                    }
                }
            }
            else
            {
                int curTime = getCalendarTime();
                setObjVar(city_hall, "city.safe_house_capacity_overload", curTime);
                obj_id mayor = cityGetLeader(city_id);
                String mayorName = cityGetCitizenName(city_id, mayor);
                prose_package bodypp = prose.getPackage(CITIZEN_OVERAGE_WARNING_BODY, mayor);
                utils.sendMail(CITIZEN_OVERAGE_WARNING_SUBJECT, bodypp, mayorName, "Planetary Civic Authority");
                CustomerServiceLog("player_city", "Mayor(" + mayor + ")" + cityGetCitizenName(city_id, mayor) + " of City: " + cityGetName(city_id) + "(" + city_id + ") has been send a Mail concerning the Safe House Overage -  Max Safe Count: (" + maxSafe + "), Current Safe Count: (" + curSafe + ")...Stamping City Hall");
            }
        }
    }
    public void forceRemoveSafeCitizen(int city_id, int numCitizensToRemove, obj_id city_hall) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            CustomerServiceLog("player_city", "Attempting to remove " + numCitizensToRemove + " Safe House Citizen(s) due to overages- but City does not exist: City ID: " + city_id);
            return;
        }
        if (numCitizensToRemove <= 0)
        {
            CustomerServiceLog("player_city", "Attempting to remove Safe Citizen Overages - but numCitizensToRemove is Zero or Less for City: " + city_id);
            return;
        }
        obj_id[] safeHouseCitizenList = city.getCitySafeHouseCitizenList(city_id);
        if (safeHouseCitizenList == null || safeHouseCitizenList.length <= 0)
        {
            CustomerServiceLog("player_city", "Attempting to remove Safe Citizen Overages but Safe House List returned NULL");
            return;
        }
        int[] safeHouseCitizenLoginTime = new int[safeHouseCitizenList.length];
        for (int i = 0; i < safeHouseCitizenList.length; i++)
        {
            safeHouseCitizenLoginTime[i] = getPlayerLastLoginTime(safeHouseCitizenList[i]);
            if (safeHouseCitizenLoginTime[i] == 0)
            {
                safeHouseCitizenLoginTime[i] = -1;
            }
        }
        int largestTime = 0;
        obj_id citizenToRemove = obj_id.NULL_ID;
        for (int j = 0; j < safeHouseCitizenList.length; j++)
        {
            if (largestTime == 0)
            {
                largestTime = safeHouseCitizenLoginTime[j];
                citizenToRemove = safeHouseCitizenList[j];
                continue;
            }
            if (safeHouseCitizenLoginTime[j] > largestTime)
            {
                largestTime = safeHouseCitizenLoginTime[j];
                citizenToRemove = safeHouseCitizenList[j];
            }
        }
        if (isIdValid(citizenToRemove))
        {
            CustomerServiceLog("player_city", "Safe House Citizen Removal: Citizen: " + cityGetCitizenName(city_id, citizenToRemove) + "(" + citizenToRemove + ") has been removed from City Protection due to the Mayor having too many Safe Houses.");
            city.removeSafeHouseCitizen(city_id, citizenToRemove);
            numCitizensToRemove--;
            if (numCitizensToRemove > 0)
            {
                dictionary d = new dictionary();
                d.put("city_id", city_id);
                d.put("citizen_count", numCitizensToRemove);
                d.put("city_hall", city_hall);
                messageTo(city_hall, "handleMultipleSafeHouseCitizenRemoval", d, 300.0f, false);
            }
            else
            {
                removeObjVar(city_hall, "city.safe_house_capacity_overload");
            }
        }
    }
    public int handleMultipleSafeHouseCitizenRemoval(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        int removeCount = params.getInt("citizen_count");
        obj_id city_hall = params.getObjId("city_hall");
        forceRemoveSafeCitizen(city_id, removeCount, city_hall);
        return SCRIPT_CONTINUE;
    }
    public int QaTestSafeHouseOverload(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(self), 0);
        obj_id city_hall = cityGetCityHall(city_id);
        validateSafeCitizenCount(city_hall, city_id);
        return SCRIPT_CONTINUE;
    }
    public int retryDepersistCityFactionalAlignment(obj_id self, dictionary params) throws InterruptedException
    {
        int numberOfRetry = params.getInt("number_of_retry");
        if (numberOfRetry >= 5)
        {
            return SCRIPT_CONTINUE;
        }
        int city_id = findCityByCityHall(self);
        if (city_id > 0)
        {
            int factionId = 0;
            if (hasObjVar(self, "cityFactionAlign"))
            {
                factionId = getIntObjVar(self, "cityFactionAlign");
            }
            if (cityGetFaction(city_id) == factionId)
            {
                return SCRIPT_CONTINUE;
            }
            else
            {
                citySetFaction(city_id, factionId, false);
            }
        }
        ++numberOfRetry;
        if (numberOfRetry < 5)
        {
            params.put("number_of_retry", numberOfRetry);
            messageTo(self, "retryDepersistCityFactionalAlignment", params, 60.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int retryDepersistCityGcwRegionDefender(obj_id self, dictionary params) throws InterruptedException
    {
        int numberOfRetry = params.getInt("number_of_retry");
        if (numberOfRetry >= 5)
        {
            return SCRIPT_CONTINUE;
        }
        String region = null;
        int regionTimeStartDefend = 0;
        int regionTimeEndDefend = 0;
        if (hasObjVar(self, "cityGcwRegionDefender.region"))
        {
            region = getStringObjVar(self, "cityGcwRegionDefender.region");
        }
        if (hasObjVar(self, "cityGcwRegionDefender.timeBegin"))
        {
            regionTimeStartDefend = getIntObjVar(self, "cityGcwRegionDefender.timeBegin");
        }
        if (hasObjVar(self, "cityGcwRegionDefender.timeEnd"))
        {
            regionTimeEndDefend = getIntObjVar(self, "cityGcwRegionDefender.timeEnd");
        }
        if ((region != null) && (region.length() > 0) && (regionTimeStartDefend > 0) && (regionTimeEndDefend <= 0))
        {
            int city_id = findCityByCityHall(self);
            if (city_id > 0)
            {
                final String gcwDefenderRegion = cityGetGcwDefenderRegion(city_id);
                if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0) && gcwDefenderRegion.equals(region))
                {
                    return SCRIPT_CONTINUE;
                }
                else
                {
                    citySetGcwDefenderRegion(city_id, region, regionTimeStartDefend, false);
                }
            }
            ++numberOfRetry;
            if (numberOfRetry < 5)
            {
                params.put("number_of_retry", numberOfRetry);
                messageTo(self, "retryDepersistCityGcwRegionDefender", params, 60.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
