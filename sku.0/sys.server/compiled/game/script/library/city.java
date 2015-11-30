package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.planetary_map;
import script.library.debug;

public class city extends script.base_script
{
    public city()
    {
    }
    public static final int CITY_VERSION = 4;
    public static final String RANK_TABLE = "datatables/city/city_rank.iff";
    public static final String RANK_RADIUS = "RADIUS";
    public static final String RANK_POPULATION = "POPULATION";
    public static final String RANK_STRING = "STRING";
    public static final int RANK_MIN = 0;
    public static final int RANK_MAX = 4;
    public static final int CP_CITIZEN = 0;
    public static final int CP_MILITIA = (1 << 0);
    public static final int CP_ABSENT_WEEK_1 = (1 << 1);
    public static final int CP_ABSENT_WEEK_2 = (1 << 2);
    public static final int CP_ABSENT_WEEK_3 = (1 << 3);
    public static final int CP_ABSENT_WEEK_4 = (1 << 4);
    public static final int CP_ABSENT_WEEK_5 = (1 << 5);
    public static final int CP_INACTIVE_PROTECTED = (1 << 6);
    public static final int SF_PM_REGISTER = 1;
    public static final int SF_COST_CITY_HALL = 2;
    public static final int SF_COST_CITY_HI = 4;
    public static final int SF_COST_CITY_MED = 8;
    public static final int SF_COST_CITY_LOW = 16;
    public static final int SF_MISSION_TERMINAL = 32;
    public static final int SF_SKILL_TRAINER = 64;
    public static final int SF_DECORATION = 128;
    public static final int SF_SPEC_SAMPLE_RICH = 256;
    public static final int SF_SPEC_FARMING = 512;
    public static final int SF_SPEC_INDUSTRY = 1024;
    public static final int SF_SPEC_RESEARCH = 2048;
    public static final int SF_SPEC_CLONING = 4096;
    public static final int SF_SPEC_MISSIONS = 8192;
    public static final int SF_SPEC_ENTERTAINER = 16384;
    public static final int SF_SPEC_DOCTOR = 32768;
    public static final int SF_SPEC_STRONGHOLD = 65536;
    public static final int SF_SPEC_MASTER_MANUFACTURING = 131072;
    public static final int SF_SPEC_MASTER_HEALING = 262144;
    public static final int SF_SPEC_DECOR_INCREASE = 524288;
    public static final int SF_SPEC_STORYTELLER = 1048576;
    public static final int SF_SPEC_INCUBATOR = 2097152;
    public static final int SF_SPEC_UNKNOWN_6 = 4194304;
    public static final int SF_REQUIRE_ZONE_RIGHTS = 8388608;
    public static final int SF_COST_CITY_GARDEN_SMALL = 16777216;
    public static final int SF_COST_CITY_GARDEN_LARGE = 33554432;
    public static final int SPECIAL_STRUCTURE = SF_MISSION_TERMINAL | SF_SKILL_TRAINER | SF_DECORATION;
    public static final int PAY_STRUCTURE = SF_COST_CITY_HALL | SF_COST_CITY_HI | SF_COST_CITY_MED | SF_COST_CITY_LOW | SF_COST_CITY_GARDEN_SMALL | SF_COST_CITY_GARDEN_LARGE;
    public static final int SPEC_MASK = SF_SPEC_SAMPLE_RICH | SF_SPEC_FARMING | SF_SPEC_INDUSTRY | SF_SPEC_RESEARCH | SF_SPEC_CLONING | SF_SPEC_MISSIONS | SF_SPEC_ENTERTAINER | SF_SPEC_DOCTOR | SF_SPEC_STRONGHOLD | SF_SPEC_MASTER_MANUFACTURING | SF_SPEC_MASTER_HEALING | SF_SPEC_DECOR_INCREASE | SF_SPEC_STORYTELLER | SF_SPEC_INCUBATOR;
    public static final int CITY_MAINTENANCE_COSTS[] = 
    {
        2500,
        7500,
        2000,
        150,
        1000,
        3000
    };
    public static final string_id NEW_CITY_STRUCTURE_SUBJECT = new string_id("city/city", "new_city_structure_subject");
    public static final string_id NEW_CITY_STRUCTURE_BODY = new string_id("city/city", "new_city_structure_body");
    public static final string_id NEW_CITY_STRUCTURE_OTHER_SUBJECT = new string_id("city/city", "new_city_structure_other_subject");
    public static final string_id NEW_CITY_STRUCTURE_OTHER_BODY = new string_id("city/city", "new_city_structure_other_body");
    public static final string_id NEW_CITY_CITIZEN_SUBJECT = new string_id("city/city", "new_city_citizen_subject");
    public static final string_id NEW_CITY_CITIZEN_BODY = new string_id("city/city", "new_city_citizen_body");
    public static final string_id NEW_CITY_CITIZEN_OTHER_SUBJECT = new string_id("city/city", "new_city_citizen_other_subject");
    public static final string_id NEW_CITY_CITIZEN_OTHER_BODY = new string_id("city/city", "new_city_citizen_other_body");
    public static final string_id LOST_CITIZEN_SUBJECT = new string_id("city/city", "lost_citizen_subject");
    public static final string_id LOST_CITIZEN_BODY = new string_id("city/city", "lost_citizen_body");
    public static final string_id LOST_CITY_CITIZEN_SUBJECT = new string_id("city/city", "lost_city_citizen_subject");
    public static final string_id LOST_CITY_CITIZEN_BODY = new string_id("city/city", "lost_city_citizen_body");
    public static final string_id LOST_INACTIVE_CITIZEN_SUBJECT = new string_id("city/city", "lost_inactive_citizen_subject");
    public static final string_id LOST_INACTIVE_CITIZEN_BODY = new string_id("city/city", "lost_inactive_citizen_body");
    public static final string_id STRUCTURE_VALID_SUBJECT = new string_id("city/city", "structure_valid_subject");
    public static final string_id STRUCTURE_VALID_BODY = new string_id("city/city", "structure_valid_body");
    public static final string_id STRUCTURE_INVALID_SUBJECT = new string_id("city/city", "structure_invalid_subject");
    public static final string_id STRUCTURE_INVALID_BODY = new string_id("city/city", "structure_invalid_body");
    public static final string_id STRUCTURE_DESTROYED_SUBJECT = new string_id("city/city", "structure_destroyed_subject");
    public static final string_id STRUCTURE_DESTROYED_BODY = new string_id("city/city", "structure_destroyed_body");
    public static final string_id STRUCTURE_DESTROYED_MAINT_SUBJECT = new string_id("city/city", "structure_destroyed_maint_subject");
    public static final string_id STRUCTURE_DESTROYED_MAINT_BODY = new string_id("city/city", "structure_destroyed_maint_body");
    public static final string_id STRUCTURE_REPAIRED_SUBJECT = new string_id("city/city", "structure_repaired_subject");
    public static final string_id STRUCTURE_REPAIRED_BODY = new string_id("city/city", "structure_repaired_body");
    public static final string_id STRUCTURE_DAMAGED_SUBJECT = new string_id("city/city", "structure_damaged_subject");
    public static final string_id STRUCTURE_DAMAGED_BODY = new string_id("city/city", "structure_damaged_body");
    public static final string_id INCOME_TAX_PAID_SUBJECT = new string_id("city/city", "income_tax_paid_subject");
    public static final string_id INCOME_TAX_PAID_BODY = new string_id("city/city", "income_tax_paid_body");
    public static final string_id INCOME_TAX_NOPAY_SUBJECT = new string_id("city/city", "income_tax_nopay_subject");
    public static final string_id INCOME_TAX_NOPAY_BODY = new string_id("city/city", "income_tax_nopay_body");
    public static final string_id INCOME_TAX_NOPAY_MAYOR_SUBJECT = new string_id("city/city", "income_tax_nopay_mayor_subject");
    public static final string_id INCOME_TAX_NOPAY_MAYOR_BODY = new string_id("city/city", "income_tax_nopay_mayor_body");
    public static final string_id SID_NOT_IN_CITY = new string_id("city/city", "not_in_city");
    public static final string_id SID_MAYOR_RESIDENCE_CHANGE = new string_id("city/city", "mayor_residence_change");
    public static final string_id SID_OVER_MAX_LIMIT = new string_id("city/city", "must_remove_decorations_or_structures");
    public static final String CITY_SPECS = "datatables/city/specializations.iff";
    public static final string_id SID_NO_RIGHTS = new string_id("player_structure", "no_transfer_rights");
    public static final String RESERVED_CITY_NAMES[] = 
    {
        "bela vistal",
        "coronet",
        "doaba guerfel",
        "kor vella",
        "tyrena",
        "vreni island",
        "dee'ja peak",
        "deeja peak",
        "keren",
        "kadaara",
        "kaadara",
        "moenia",
        "theed",
        "narmle",
        "restuss",
        "dearic",
        "daeric",
        "nashal",
        "anchorhead",
        "bestine",
        "mos eisley",
        "mos espa",
        "mos entha",
        "mos taike",
        "wayfar",
        "lake retreat"
    };
    public static final String OBJVAR_DERANK_EXEMPT = "city.derank_exempt";
    public static final String CITIZEN_LIST_QUERIED = "cityhall.citizen_list_queried";
    public static final String CITIZEN_LIST_DATA = "cityhall.citizen_list_data";
    public static int canBuildCityHere(obj_id player, location loc) throws InterruptedException
    {
        int[] all_cities = getAllCityIds();
        for (int i = 0; i < all_cities.length; i++)
        {
            location other_loc = cityGetLocation(all_cities[i]);
            if (other_loc.area.equals(loc.area))
            {
                float dist = utils.getDistance2D(loc, other_loc);
                if (dist < 1100)
                {
                    return all_cities[i];
                }
            }
        }
        return -1;
    }
    public static boolean isInCity(obj_id obj) throws InterruptedException
    {
        return isInCity(getLocation(obj));
    }
    public static boolean isInCity(location loc) throws InterruptedException
    {
        int city_id = 0;
        if (isIdValid(loc.cell))
        {
            loc = getLocation(getTopMostContainer(loc.cell));
        }
        city_id = getCityAtLocation(loc, 0);
        if (cityExists(city_id))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isPartOfCity(obj_id structure, int city_id) throws InterruptedException
    {
        if (!isIdValid(structure))
        {
            return false;
        }
        obj_id[] city_structures = cityGetStructureIds(city_id);
        if (city_structures == null)
        {
            return false;
        }
        for (int i = 0; i < city_structures.length; i++)
        {
            if (city_structures[i] == structure)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isAMayor(obj_id player) throws InterruptedException
    {
        int[] cities = getAllCityIds();
        for (int i = 0; i < cities.length; i++)
        {
            obj_id mayor = cityGetLeader(cities[i]);
            if (mayor == player)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isTheCityMayor(obj_id player, int city_id) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        return mayor == player;
    }
    public static int getCityRank(int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return 0;
        }
        int rad = cityGetRadius(city_id);
        int[] radList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_RADIUS);
        int rank = 0;
        for (int i = 0; i < radList.length; i++)
        {
            if (rad >= radList[i])
            {
                rank++;
            }
        }
        return rank;
    }
    public static boolean addStructureToCity(obj_id structure) throws InterruptedException
    {
        if (!isIdValid(structure))
        {
            return false;
        }
        if (hasScript(structure, player_structure.SCRIPT_TEMPORARY_STRUCTURE))
        {
            return false;
        }
        int city_id = getCityAtLocation(getLocation(structure), 0);
        if ((city_id > 0) && (!isIdValid(getContainedBy(structure))))
        {
            return addStructureToCity(structure, city_id);
        }
        else 
        {
            return false;
        }
    }
    public static boolean addStructureToCity(obj_id structure, int city_id) throws InterruptedException
    {
        if (!isPartOfCity(structure, city_id))
        {
            boolean city_reg = isCityRegistered(city_id);
            int flags = 0;
            if (city_reg)
            {
                flags |= SF_PM_REGISTER;
                messageTo(structure, "cityMapRegister", null, 0.f, false);
            }
            int cost = player_structure.getStructureCityCost(structure);
            if (cost > 0)
            {
                switch (cost)
                {
                    case 1:
                    flags |= SF_COST_CITY_HALL;
                    break;
                    case 2:
                    flags |= SF_COST_CITY_HI;
                    break;
                    case 3:
                    flags |= SF_COST_CITY_MED;
                    break;
                    case 4:
                    flags |= SF_COST_CITY_LOW;
                    break;
                    case 5:
                    flags |= SF_COST_CITY_GARDEN_SMALL;
                    break;
                    case 6:
                    flags |= SF_COST_CITY_GARDEN_LARGE;
                    break;
                }
            }
            citySetStructureInfo(city_id, structure, flags, true);
            setStructureCityId(structure, city_id);
            obj_id mayor = cityGetLeader(city_id);
            String mayor_name = cityGetCitizenName(city_id, mayor);
            String structure_name = localize(getNameStringId(structure));
            prose_package bodypp = prose.getPackage(NEW_CITY_STRUCTURE_BODY, structure_name, player_structure.getStructureOwner(structure));
            utils.sendMail(NEW_CITY_STRUCTURE_SUBJECT, bodypp, mayor_name, "City Hall");
            String city_name = cityGetName(city_id);
            bodypp = prose.getPackage(NEW_CITY_STRUCTURE_OTHER_BODY, mayor_name, structure_name, city_name);
            utils.sendMail(NEW_CITY_STRUCTURE_OTHER_SUBJECT, bodypp, player_structure.getStructureOwner(structure), "City Hall");
            obj_id city_hall = cityGetCityHall(city_id);
            CustomerServiceLog("player_city", "Added structure to city.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structure);
            if (hasObjVar(structure, player_structure.VAR_RESIDENCE_BUILDING))
            {
                obj_id resident = getObjIdObjVar(structure, player_structure.VAR_RESIDENCE_BUILDING);
                addCitizen(resident, structure);
            }
            return true;
        }
        else 
        {
            if (getStructureCityId(structure) != city_id)
            {
                setStructureCityId(structure, city_id);
            }
        }
        return false;
    }
    public static boolean removeStructureFromCity(obj_id structure) throws InterruptedException
    {
        if (!isIdValid(structure))
        {
            return false;
        }
        int city_id = getCityAtLocation(getLocation(structure), 0);
        if (city_id > 0)
        {
            return removeStructureFromCity(city_id, structure);
        }
        return false;
    }
    public static boolean removeStructureFromCity(int city_id, obj_id structure) throws InterruptedException
    {
        if (isPartOfCity(structure, city_id))
        {
            cityRemoveStructure(city_id, structure);
            String city_name = cityGetName(city_id);
            obj_id city_hall = cityGetCityHall(city_id);
            CustomerServiceLog("player_city", "Removed structure from city.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structure);
            if (hasObjVar(structure, player_structure.VAR_RESIDENCE_BUILDING))
            {
                obj_id resident = getObjIdObjVar(structure, player_structure.VAR_RESIDENCE_BUILDING);
                obj_id mayor = cityGetLeader(city_id);
                if (resident != mayor)
                {
                    removeCitizen(resident, structure);
                }
            }
            if (player_structure.isCivic(structure))
            {
                detachScript(structure, "planet_map.map_loc_both");
            }
            else 
            {
                setStructureCityId(structure, 0);
            }
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static void validateCityStructure(obj_id structure) throws InterruptedException
    {
        if (!isIdValid(structure))
        {
            return;
        }
        int city_id = getCityAtLocation(getLocation(structure), 0);
        int oldCity = getStructureCityId(structure);
        if (oldCity > 0)
        {
            if (oldCity == city_id)
            {
            }
            else if ((oldCity != city_id) && (city_id > 0))
            {
                CustomerServiceLog("player_city", "Structure validated in a different city.  Old City ID: " + oldCity + " New City ID: " + city_id + " Structure: " + structure);
                if (cityExists(oldCity))
                {
                    cityRemoveStructure(oldCity, structure);
                }
                if (player_structure.isCivic(structure))
                {
                    player_structure.destroyStructure(structure);
                    return;
                }
                else 
                {
                    if (!isIdValid(getContainedBy(structure)))
                    {
                        addStructureToCity(structure, city_id);
                        setStructureCityId(structure, city_id);
                    }
                }
            }
            else if (city_id == 0)
            {
                CustomerServiceLog("player_city", "Structure validated in no city.  Old City ID: " + oldCity + " Structure: " + structure);
                if (cityExists(oldCity))
                {
                    cityRemoveStructure(oldCity, structure);
                }
                if (hasScript(structure, "planet_map.map_loc_both"))
                {
                    detachScript(structure, "planet_map.map_loc_both");
                }
                if (player_structure.isCivic(structure))
                {
                    player_structure.destroyStructure(structure);
                    return;
                }
            }
        }
        else 
        {
            if (city_id > 0)
            {
                if (!isIdValid(getContainedBy(structure)))
                {
                    CustomerServiceLog("player_city", "Structure validated in new city.  New City ID: " + city_id + " Structure: " + structure);
                    addStructureToCity(structure, city_id);
                }
            }
            else 
            {
            }
        }
        checkStructureValid(city_id, structure);
        city.updateRegistrationScript(city_id, structure);
    }
    public static boolean validateSpecialStructure(obj_id structure) throws InterruptedException
    {
        if (!isIdValid(structure))
        {
            return false;
        }
        int city_id = checkCity(structure, false);
        int oldCity = getIntObjVar(structure, "city_id");
        if (isNormalStructure(city_id, structure))
        {
            return false;
        }
        if (oldCity > 0)
        {
            if (oldCity == city_id)
            {
            }
            else if ((oldCity != city_id) && (city_id > 0))
            {
                CustomerServiceLog("player_city", "Special object loaded into a different city.  Old City ID: " + oldCity + " New City ID: " + city_id + " Object: " + structure);
                destroyObject(structure);
                return false;
            }
            else if (city_id == 0)
            {
                CustomerServiceLog("player_city", "Special object loaded into no city.  Old City ID: " + oldCity + " Object: " + structure);
                destroyObject(structure);
                return false;
            }
        }
        else 
        {
            CustomerServiceLog("player_city", "Special object loaded outside a city.  Removing object.  Object: " + structure);
            destroyObject(structure);
            return false;
        }
        return checkStructureValid(city_id, structure);
    }
    public static boolean isCitizenOfCity(obj_id player, int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens == null)
        {
            return false;
        }
        for (int i = 0; i < citizens.length; i++)
        {
            if (citizens[i] == player)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isMilitiaOfCity(obj_id player, int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens == null)
        {
            return false;
        }
        for (int i = 0; i < citizens.length; i++)
        {
            if ((citizens[i] == player) && hasMilitiaFlag(player, city_id))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasMilitiaFlag(obj_id player, int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int flags = cityGetCitizenPermissions(city_id, player);
        if (0 != (flags & CP_MILITIA))
        {
            return true;
        }
        return false;
    }
    public static void addCitizen(obj_id citizen, obj_id residence) throws InterruptedException
    {
        if (!isIdValid(residence))
        {
            return;
        }
        if (!isIdValid(citizen) || !citizen.isLoaded())
        {
            return;
        }
        String citizen_name = getName(citizen);
        if (citizen_name == null)
        {
            return;
        }
        int city_id = getCityAtLocation(getLocation(residence), 0);
        if (!cityExists(city_id))
        {
            return;
        }
        if (isCitizenOfCity(citizen, city_id))
        {
            return;
        }
        obj_id mayor = cityGetLeader(city_id);
        if (citizen == mayor)
        {
            return;
        }
        citySetCitizenInfo(city_id, citizen, citizen_name, null, CP_CITIZEN);
        removeObjVar(citizen, "city.income_tax_time");
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Added citizen to city.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen + " Residence: " + residence);
        removeBan(citizen, city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        prose_package bodypp = prose.getPackage(NEW_CITY_CITIZEN_BODY, citizen_name);
        utils.sendMail(NEW_CITY_CITIZEN_SUBJECT, bodypp, mayor_name, "City Hall");
        String cname = cityGetCitizenName(city_id, citizen);
        bodypp = prose.getPackage(NEW_CITY_CITIZEN_OTHER_BODY, mayor_name, citizen_name, city_name);
        utils.sendMail(NEW_CITY_CITIZEN_OTHER_SUBJECT, bodypp, cname, "City Hall");
    }
    public static void removeCitizen(obj_id citizen, obj_id residence) throws InterruptedException
    {
        if (!isIdValid(residence))
        {
            return;
        }
        int city_id = getCityAtLocation(getLocation(residence), 0);
        removeCitizen(citizen, city_id, residence);
    }
    public static void removeCitizen(obj_id citizen, int city_id, obj_id residence) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        obj_id mayor = cityGetLeader(city_id);
        if (citizen == mayor)
        {
            return;
        }
        if (!isCitizenOfCity(citizen, city_id))
        {
            return;
        }
        if (hasMayorProtectionFlag(citizen, city_id))
        {
            removeSafeHouseCitizen(city_id, citizen);
        }
        String citizen_name = cityGetCitizenName(city_id, citizen);
        if (citizen_name == null)
        {
            return;
        }
        String mayor_name = cityGetCitizenName(city_id, mayor);
        prose_package bodypp = prose.getPackage(LOST_CITY_CITIZEN_BODY, cityGetCitizenName(city_id, citizen));
        utils.sendMail(LOST_CITY_CITIZEN_SUBJECT, bodypp, mayor_name, "City Hall");
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        cityRemoveCitizen(city_id, citizen);
        if (isIdValid(residence))
        {
            CustomerServiceLog("player_city", "Removed citizen from city.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen + " Residence: " + residence);
        }
        else 
        {
            CustomerServiceLog("player_city", "Removed citizen from city.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen + " Residence: (none specified)");
        }
    }
    public static void addMilitia(int city_id, obj_id citizen) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        int flags = cityGetCitizenPermissions(city_id, citizen);
        flags = flags | CP_MILITIA;
        obj_id cit_all = cityGetCitizenAllegiance(city_id, citizen);
        citySetCitizenInfo(city_id, citizen, cityGetCitizenName(city_id, citizen), cit_all, flags);
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Added militia.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen);
    }
    public static void removeMilitia(int city_id, obj_id citizen) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        int flags = cityGetCitizenPermissions(city_id, citizen);
        flags = flags & ~CP_MILITIA;
        obj_id cit_all = cityGetCitizenAllegiance(city_id, citizen);
        citySetCitizenInfo(city_id, citizen, cityGetCitizenName(city_id, citizen), cit_all, flags);
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Removed militia.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen);
    }
    public static void setCityResidence(obj_id citizen, obj_id newresidence) throws InterruptedException
    {
        int new_city = getCityAtLocation(getLocation(newresidence), 0);
        int old_city = getCitizenOfCityId(citizen);
        if (old_city == new_city)
        {
            return;
        }
        obj_id new_mayor = cityGetLeader(new_city);
        obj_id old_mayor = cityGetLeader(old_city);
        if ((new_mayor == citizen) || (old_mayor == citizen))
        {
            sendSystemMessage(citizen, SID_MAYOR_RESIDENCE_CHANGE);
            return;
        }
        removeCitizen(citizen, old_city, null);
        addCitizen(citizen, newresidence);
    }
    public static void setMayor(int city_id, obj_id new_mayor) throws InterruptedException
    {
        citySetLeader(city_id, new_mayor);
    }
    public static void setRadius(int city_id, int new_radius) throws InterruptedException
    {
        citySetRadius(city_id, new_radius);
    }
    public static void setName(int city_id, String new_name) throws InterruptedException
    {
        citySetName(city_id, new_name);
    }
    public static void setCitizenAllegiance(int city_id, obj_id citizen, obj_id new_allegiance) throws InterruptedException
    {
        citySetCitizenInfo(city_id, citizen, cityGetCitizenName(city_id, citizen), new_allegiance, cityGetCitizenPermissions(city_id, citizen));
    }
    public static boolean addStarport(obj_id shuttleport, location cityTravelLoc, int cityTravelCost, boolean cityTravelIntp) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(shuttleport), 0);
        if (!cityExists(city_id))
        {
            return false;
        }
        int travelTax = cityGetTravelCost(city_id);
        if (travelTax > 0)
        {
            cityTravelCost = travelTax;
        }
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Adding starport.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + shuttleport);
        citySetTravelInfo(city_id, cityTravelLoc, cityTravelCost, cityTravelIntp);
        return true;
    }
    public static boolean removeStarport(obj_id shuttleport) throws InterruptedException
    {
        int city_id = getStructureCityId(shuttleport);
        if (!cityExists(city_id))
        {
            return false;
        }
        return removeStarport(shuttleport, city_id);
    }
    public static boolean removeStarport(obj_id shuttleport, int city_id) throws InterruptedException
    {
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Removing starport.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + shuttleport);
        citySetTravelInfo(city_id, new location(), 0, false);
        return true;
    }
    public static boolean addGarage(String template) throws InterruptedException
    {
        return true;
    }
    public static void removeGarage(obj_id structure) throws InterruptedException
    {
        return;
    }
    public static boolean isUniqueCityName(String newName) throws InterruptedException
    {
        int[] city_ids = getAllCityIds();
        if (city_ids == null || city_ids.length == 0)
        {
            return true;
        }
        for (int i = 0; i < city_ids.length; i++)
        {
            String city_name = cityGetName(city_ids[i]);
            if (city_name.equals(newName))
            {
                return false;
            }
        }
        String lowerNewName = newName.toLowerCase();
        for (int i = 0; i < RESERVED_CITY_NAMES.length; i++)
        {
            if (lowerNewName.indexOf(RESERVED_CITY_NAMES[i]) >= 0)
            {
                return false;
            }
        }
        return true;
    }
    public static boolean isCityBanned(obj_id player, int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        int[] bannedIds = getIntArrayObjVar(player, "city.banlist");
        if (bannedIds == null)
        {
            return false;
        }
        for (int i = 0; i < bannedIds.length; i++)
        {
            if (bannedIds[i] == city_id)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isCityBanned(obj_id player, obj_id structure) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(structure), 0);
        return isCityBanned(player, city_id);
    }
    public static boolean removeBan(obj_id player, int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        int[] banCities = getIntArrayObjVar(player, "city.banlist");
        if (banCities == null)
        {
            return false;
        }
        int found = 0;
        for (int i = 0; i < banCities.length; i++)
        {
            if (banCities[i] == city_id)
            {
                found = 1;
                break;
            }
        }
        if (found == 0)
        {
            return false;
        }
        if (banCities.length == 1)
        {
            removeObjVar(player, "city.banlist");
        }
        else 
        {
            int j = 0;
            int[] newBanCities = new int[banCities.length - 1];
            for (int i = 0; i < banCities.length; i++)
            {
                if (banCities[i] != city_id)
                {
                    newBanCities[j] = banCities[i];
                    j++;
                }
            }
            setObjVar(player, "city.banlist", newBanCities);
        }
        return true;
    }
    public static void removeTaxesSetRadius(int city_id, int new_radius) throws InterruptedException
    {
        citySetIncomeTax(city_id, 0);
        citySetPropertyTax(city_id, 0);
        citySetSalesTax(city_id, 0);
        if (new_radius < 1)
        {
            CustomerServiceLog("player_city", "ERROR: Attempted to set city radius of " + cityGetName(city_id) + " (City ID: " + city_id + ") to " + new_radius + "! Setting it to 150 instead");
            new_radius = 150;
        }
        citySetRadius(city_id, new_radius);
    }
    public static void removeTaxes(int city_id) throws InterruptedException
    {
        removeTaxesSetRadius(city_id, cityGetRadius(city_id));
    }
    public static void setIncomeTax(int city_id, int new_tax) throws InterruptedException
    {
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Changed income tax.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Value: " + new_tax);
        citySetIncomeTax(city_id, new_tax);
    }
    public static void setPropertyTax(int city_id, int new_tax) throws InterruptedException
    {
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Changed property tax.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Value: " + new_tax);
        citySetPropertyTax(city_id, new_tax);
    }
    public static void setSalesTax(int city_id, int new_tax) throws InterruptedException
    {
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Changed sales tax.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Value: " + new_tax);
        citySetSalesTax(city_id, new_tax);
    }
    public static void setTravelFee(int city_id, int new_fee) throws InterruptedException
    {
        location cityTravelLoc = cityGetTravelLocation(city_id);
        int cityTravelCost = new_fee;
        boolean cityTravelIntp = cityGetTravelInterplanetary(city_id);
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Changed travel fee.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Value: " + new_fee);
        citySetTravelInfo(city_id, cityTravelLoc, cityTravelCost, cityTravelIntp);
    }
    public static void setGarageFee(int city_id, int new_fee) throws InterruptedException
    {
        int garageUseCost = new_fee;
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        String garageCity = city_name + ".garageFee";
        setObjVar(city_hall, garageCity, garageUseCost);
    }
    public static void setCloneInfo(int city_id, location cityCloneLoc, location cityCloneRespawn, obj_id cityCloneId) throws InterruptedException
    {
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Changed clone info.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Clone Loc: " + cityCloneLoc + " Id: " + cityCloneId);
        citySetCloneInfo(city_id, cityCloneLoc, cityCloneRespawn, cityCloneId);
    }
    public static void updateLocation(int city_id, location new_loc) throws InterruptedException
    {
        obj_id cityHall = cityGetCityHall(city_id);
        location cityLoc = (location)new_loc.clone();
        citySetLocation(city_id, new_loc);
    }
    public static int getPropertyTax(obj_id structure) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(structure), 0);
        if (cityExists(city_id))
        {
            return cityGetPropertyTax(city_id);
        }
        return 0;
    }
    public static obj_id getCityHall(obj_id structure) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(structure), 0);
        if (cityExists(city_id))
        {
            return cityGetCityHall(city_id);
        }
        return null;
    }
    public static void setStructureType(int city_id, obj_id structure, int structureType) throws InterruptedException
    {
        boolean structureValid = cityGetStructureValid(city_id, structure);
        citySetStructureInfo(city_id, structure, structureType, structureValid);
    }
    public static void setStructureValid(int city_id, obj_id structure, boolean structureValid) throws InterruptedException
    {
        int structureType = cityGetStructureType(city_id, structure);
        citySetStructureInfo(city_id, structure, structureType, structureValid);
    }
    public static boolean isCityRegistered(int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        obj_id hall = cityGetCityHall(city_id);
        int flags = cityGetStructureType(city_id, hall);
        if (0 != (flags & SF_PM_REGISTER))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static void registerCity(int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (!isNormalStructure(city_id, structures[i]))
            {
                continue;
            }
            int flags = cityGetStructureType(city_id, structures[i]);
            flags |= city.SF_PM_REGISTER;
            setStructureType(city_id, structures[i], flags);
            String city_name = cityGetName(city_id);
            obj_id city_hall = cityGetCityHall(city_id);
            CustomerServiceLog("player_city", "Registered city on planetary map.  City: " + city_name + " (" + city_id + "/" + city_hall + ")");
            messageTo(structures[i], "cityMapRegister", null, 0.f, false);
        }
    }
    public static void unregisterCity(int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (!isNormalStructure(city_id, structures[i]))
            {
                continue;
            }
            int flags = cityGetStructureType(city_id, structures[i]);
            flags = flags & ~city.SF_PM_REGISTER;
            setStructureType(city_id, structures[i], flags);
            String city_name = cityGetName(city_id);
            obj_id city_hall = cityGetCityHall(city_id);
            CustomerServiceLog("player_city", "Unregistered city from planetary map.  City: " + city_name + " (" + city_id + "/" + city_hall + ")");
            messageTo(structures[i], "cityMapUnregister", null, 0.f, false);
        }
    }
    public static void updateRegistrationScript(int city_id, obj_id structure) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        if (!player_structure.isCivic(structure))
        {
            return;
        }
        int flags = cityGetStructureType(city_id, structure);
        if (0 != (flags & city.SF_PM_REGISTER))
        {
            if (!hasScript(structure, "planet_map.map_loc_both"))
            {
                attachScript(structure, "planet_map.map_loc_both");
            }
        }
        else 
        {
            if (hasScript(structure, "planet_map.map_loc_both"))
            {
                detachScript(structure, "planet_map.map_loc_both");
            }
        }
    }
    public static boolean checkStructureValid(int city_id, obj_id structure) throws InterruptedException
    {
        return checkStructureValid(city_id, structure, getCityRank(city_id));
    }
    public static boolean checkStructureValid(int city_id, obj_id structure, int city_rank) throws InterruptedException
    {
        if (!isNormalStructure(city_id, structure))
        {
            return checkSpecialValid(city_id, structure, city_rank);
        }
        if (!player_structure.isCivic(structure))
        {
            return false;
        }
        if (!cityExists(city_id))
        {
            player_structure.destroyStructure(structure);
            return false;
        }
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String structure_name = localize(getNameStringId(structure));
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        boolean valid = cityGetStructureValid(city_id, structure);
        int rank_req = player_structure.getStructureCityRank(structure);
        if (city_rank < rank_req)
        {
            if (valid)
            {
                setStructureValid(city_id, structure, false);
                prose_package bodypp = prose.getPackage(STRUCTURE_INVALID_BODY, structure_name, mayor_name);
                utils.sendMail(STRUCTURE_INVALID_SUBJECT, bodypp, mayor_name, "City Hall");
                setObjVar(structure, "city.invalid_timestamp", getGameTime());
                CustomerServiceLog("player_city", "City can't support valid structure.  Marking structure invalid.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structure);
            }
            else 
            {
                int invalid_time = getIntObjVar(structure, "city.invalid_timestamp");
                if (invalid_time == 0)
                {
                    setObjVar(structure, "city.invalid_timestamp", getGameTime());
                    return true;
                }
                int cur_time = getGameTime();
                if (cur_time - invalid_time < 7 * 24 * 60 * 60)
                {
                    return true;
                }
                CustomerServiceLog("player_city", "City destroying invalid structure that can't be supported.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structure);
                player_structure.destroyStructure(structure);
                prose_package bodypp = prose.getPackage(STRUCTURE_DESTROYED_BODY, structure_name, mayor_name);
                utils.sendMail(STRUCTURE_DESTROYED_SUBJECT, bodypp, mayor_name, "City Hall");
            }
        }
        else 
        {
            if (!valid)
            {
                setStructureValid(city_id, structure, true);
                prose_package bodypp = prose.getPackage(STRUCTURE_VALID_BODY, structure_name, mayor_name);
                utils.sendMail(STRUCTURE_VALID_SUBJECT, bodypp, mayor_name, "City Hall");
                CustomerServiceLog("player_city", "Invalid structure is now valid.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Structure: " + structure);
            }
        }
        return true;
    }
    public static boolean checkSpecialValid(int city_id, obj_id structure) throws InterruptedException
    {
        return checkSpecialValid(city_id, structure, getCityRank(city_id));
    }
    public static boolean checkSpecialValid(int city_id, obj_id structure, int rank) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String structure_name = localize(getNameStringId(structure));
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        int curc = 0, maxc = 0;
        String varstring = null;
        if (isMissionTerminal(city_id, structure))
        {
            varstring = "city.mt_count";
            curc = getMTCount(city_id) - utils.getIntScriptVar(city_hall, varstring);
            maxc = getMaxMTCount(city_id, rank);
        }
        else if (isSkillTrainer(city_id, structure))
        {
            varstring = "city.st_count";
            curc = getTrainerCount(city_id) - utils.getIntScriptVar(city_hall, varstring);
            maxc = getMaxTrainerCount(city_id, rank);
        }
        else if (isDecoration(city_id, structure))
        {
            varstring = "city.deco_count";
            curc = getDecorationCount(city_id) - utils.getIntScriptVar(city_hall, varstring);
            maxc = getMaxDecorationCount(city_id, rank);
        }
        if (curc > maxc)
        {
            CustomerServiceLog("player_city", "Destroying special because it is over our max allowed count.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + structure);
            prose_package bodypp = prose.getPackage(STRUCTURE_DESTROYED_BODY, structure_name, mayor_name);
            utils.sendMail(STRUCTURE_DESTROYED_SUBJECT, bodypp, mayor_name, "City Hall");
            if (isSkillTrainer(city_id, structure))
            {
                removeSkillTrainer(structure);
            }
            else 
            {
                destroyObject(structure);
            }
            utils.setScriptVar(city_hall, varstring, utils.getIntScriptVar(city_hall, varstring) + 1);
            return false;
        }
        else 
        {
            return true;
        }
    }
    public static int getStructureCost(int city_id, obj_id structure) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return 0;
        }
        if (getConfigSetting("GameServer", "disableStructureFees") != null)
        {
            return 0;
        }
        int structureType = cityGetStructureType(city_id, structure);
        if (0 != (structureType & SF_COST_CITY_HALL))
        {
            int cost = 1000 + CITY_MAINTENANCE_COSTS[0] * getCityRank(city_id);
            if (isCityRegistered(city_id))
            {
                cost += 5000;
            }
            int civic_count = city.getCivicCount(city_id);
            int max_civic = city.getMaxCivicCount(city_id);
            int diff = civic_count - max_civic;
            if (diff > 0)
            {
                if (hasObjVar(structure, "city.civic_cap_penalty"))
                {
                    cost += 75000 * diff;
                }
                else 
                {
                    setObjVar(structure, "city.civic_cap_penalty", 1);
                }
            }
            else if (hasObjVar(structure, "city.civic_cap_penalty"))
            {
                removeObjVar(structure, "city.civic_cap_penalty");
            }
            return cost;
        }
        else if (0 != (structureType & SF_COST_CITY_HI))
        {
            return CITY_MAINTENANCE_COSTS[1];
        }
        else if (0 != (structureType & SF_COST_CITY_MED))
        {
            return CITY_MAINTENANCE_COSTS[2];
        }
        else if (0 != (structureType & SF_COST_CITY_LOW))
        {
            return CITY_MAINTENANCE_COSTS[3];
        }
        else if (0 != (structureType & SF_COST_CITY_GARDEN_SMALL))
        {
            return CITY_MAINTENANCE_COSTS[4];
        }
        else if (0 != (structureType & SF_COST_CITY_GARDEN_LARGE))
        {
            return CITY_MAINTENANCE_COSTS[5];
        }
        else 
        {
            return 0;
        }
    }
    public static void initCitizen(obj_id citizen) throws InterruptedException
    {
        int city_id = getCitizenOfCityId(citizen);
        if (!cityExists(city_id))
        {
            return;
        }
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        if (citizen == mayor)
        {
            return;
        }
        city.clearAbsenceFlags(citizen, city_id);
        int last_tax_time = getIntObjVar(citizen, "city.income_tax_time");
        int cur_time = getGameTime();
        if (last_tax_time > 0)
        {
            if (cur_time - last_tax_time < 7 * 24 * 60 * 60)
            {
                return;
            }
        }
        setObjVar(citizen, "city.income_tax_time", cur_time);
        String citizen_name = cityGetCitizenName(city_id, citizen);
        obj_id city_hall = cityGetCityHall(city_id);
        int income_tax = cityGetIncomeTax(city_id);
        if (income_tax <= 0)
        {
            return;
        }
        if (!money.pay(citizen, city_hall, income_tax, "", null, false))
        {
            prose_package bodypp = prose.getPackage(INCOME_TAX_NOPAY_BODY, citizen_name, income_tax);
            utils.sendMail(INCOME_TAX_NOPAY_SUBJECT, bodypp, citizen_name, "City Hall");
            bodypp = prose.getPackage(INCOME_TAX_NOPAY_MAYOR_BODY, citizen_name, income_tax);
            utils.sendMail(INCOME_TAX_NOPAY_MAYOR_SUBJECT, bodypp, mayor_name, "City Hall");
            setObjVar(citizen, "city.income_tax_delinquent", 1);
        }
        else 
        {
            prose_package bodypp = prose.getPackage(INCOME_TAX_PAID_BODY, citizen_name, income_tax);
            utils.sendMail(INCOME_TAX_PAID_SUBJECT, bodypp, citizen_name, "City Hall");
            removeObjVar(citizen, "city.income_tax_delinquent");
        }
    }
    public static int getCivicCount(int city_id) throws InterruptedException
    {
        int count = 0;
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (isPayStructure(city_id, structures[i]) && isNormalStructure(city_id, structures[i]))
            {
                count++;
            }
        }
        return count;
    }
    public static int getMaxCivicCount(int city_id) throws InterruptedException
    {
        return getMaxCivicCount(city_id, getCityRank(city_id));
    }
    public static int getMaxCivicCount(int city_id, int rank) throws InterruptedException
    {
        int baseCivicCount = (1 + (rank * 9));
        int flag = cityGetSpec(city_id);
        if (flag == SF_SPEC_DECOR_INCREASE)
        {
            LOG("sissynoid", "City Has Increased Decoration Spec!  Increasing Civic Structures by 10%");
            baseCivicCount = (1 + (rank * 12));
            return baseCivicCount;
        }
        return baseCivicCount;
    }
    public static int getMTCount(int city_id) throws InterruptedException
    {
        int count = 0;
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            int flags = cityGetStructureType(city_id, structures[i]);
            if (0 != (flags & SF_MISSION_TERMINAL))
            {
                count++;
            }
        }
        return count;
    }
    public static int getMaxMTCount(int city_id) throws InterruptedException
    {
        return getMaxMTCount(city_id, getCityRank(city_id));
    }
    public static int getMaxMTCount(int city_id, int rank) throws InterruptedException
    {
        return rank * 3;
    }
    public static void addMissionTerminal(int city_id, obj_id player, String template) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        float yaw = getYaw(player);
        obj_id mt = createObject(template, getLocation(player));
        setYaw(mt, yaw);
        attachScript(mt, "terminal.terminal_city_mission");
        int flags = SF_COST_CITY_LOW | SF_MISSION_TERMINAL;
        citySetStructureInfo(city_id, mt, flags, true);
        setObjVar(mt, "city_id", city_id);
        persistObject(mt);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Adding mission terminal.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + mt);
    }
    public static void removeMissionTerminal(obj_id terminal) throws InterruptedException
    {
        int city_id = checkCity(terminal, false);
        if (!cityExists(city_id))
        {
            return;
        }
        cityRemoveStructure(city_id, terminal);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Removing mission terminal.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + terminal);
    }
    public static int getTrainerCount(int city_id) throws InterruptedException
    {
        int count = 0;
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            int flags = cityGetStructureType(city_id, structures[i]);
            if (0 != (flags & SF_SKILL_TRAINER))
            {
                count++;
            }
        }
        return count;
    }
    public static int getMaxTrainerCount(int city_id) throws InterruptedException
    {
        return getMaxTrainerCount(city_id, getCityRank(city_id));
    }
    public static int getMaxTrainerCount(int city_id, int rank) throws InterruptedException
    {
        return 0;
    }
    public static void addSkillTrainer(int city_id, obj_id player, String template) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        float yaw = getYaw(player);
        obj_id st = createObject(template, getLocation(player));
        setYaw(st, yaw);
        setObjVar(st, "creator", player);
        int flags = SF_COST_CITY_LOW | SF_SKILL_TRAINER;
        citySetStructureInfo(city_id, st, flags, true);
        setObjVar(st, "city_id", city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Adding skill trainer.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + st);
    }
    public static void removeSkillTrainer(obj_id trainer) throws InterruptedException
    {
        int city_id = checkCity(trainer, false);
        if (!cityExists(city_id))
        {
            return;
        }
        cityRemoveStructure(city_id, trainer);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Removing skill trainer.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + trainer);
    }
    public static int getDecorationCount(int city_id) throws InterruptedException
    {
        int count = 0;
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            int flags = cityGetStructureType(city_id, structures[i]);
            if (0 != (flags & SF_DECORATION))
            {
                count++;
            }
        }
        return count;
    }
    public static int getMaxDecorationCount(int city_id) throws InterruptedException
    {
        return getMaxDecorationCount(city_id, getCityRank(city_id));
    }
    public static int getMaxDecorationCount(int city_id, int rank) throws InterruptedException
    {
        int baseDecorCount = rank * 15;
        int flag = cityGetSpec(city_id);
        if (flag == SF_SPEC_DECOR_INCREASE)
        {
            LOG("sissynoid", "City Has Increased Decoration Spec!  Increasing Decorations by 20%");
            baseDecorCount = rank * 20;
            return baseDecorCount;
        }
        return baseDecorCount;
    }
    public static void addDecoration(int city_id, obj_id player, obj_id deco) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return;
        }
        int flags = SF_COST_CITY_LOW | SF_DECORATION;
        citySetStructureInfo(city_id, deco, flags, true);
        setObjVar(deco, "city_id", city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Adding decoration.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + deco);
    }
    public static void removeDecoration(obj_id deco) throws InterruptedException
    {
        int city_id = checkCity(deco, false);
        if (!cityExists(city_id))
        {
            return;
        }
        cityRemoveStructure(city_id, deco);
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Removing decoration.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Object: " + deco);
    }
    public static void setSpecialization(int city_id, String spec) throws InterruptedException
    {
        if (spec == null || spec.length() <= 0)
        {
            return;
        }
        if (spec.equals("city_spec_decor_increase"))
        {
            int rank = getCityRank(city_id);
            int maxBaseDec = rank * 15;
            int maxBaseCiv = 1 + (rank * 9);
            if (getDecorationCount(city_id) > maxBaseDec || getCivicCount(city_id) > maxBaseCiv)
            {
                obj_id mayor = cityGetLeader(city_id);
                sendSystemMessage(mayor, SID_OVER_MAX_LIMIT);
                return;
            }
        }
        obj_id city_hall = cityGetCityHall(city_id);
        int type = cityGetStructureType(city_id, city_hall);
        type = type & ~SPEC_MASK;
        if (spec.equals("null"))
        {
            setStructureType(city_id, city_hall, type);
            return;
        }
        int flag = 0;
        String[] specs = dataTableGetStringColumn(CITY_SPECS, "SPECIALIZATION");
        for (int i = 0; i < specs.length; i++)
        {
            if (specs[i].equals(spec))
            {
                flag = dataTableGetInt(CITY_SPECS, i, "BIT");
                break;
            }
        }
        if (flag == 0)
        {
            return;
        }
        type |= flag;
        setStructureType(city_id, city_hall, type);
        String city_name = cityGetName(city_id);
        LOG("sissynoid", "Setting specialization.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Spec: " + spec);
        CustomerServiceLog("player_city", "Setting specialization.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Spec: " + spec);
    }
    public static boolean cityHasSpec(int city_id, int spec_bit) throws InterruptedException
    {
        if (city_id <= 0)
        {
            return false;
        }
        obj_id city_hall = cityGetCityHall(city_id);
        int flags = cityGetStructureType(city_id, city_hall);
        return (0 != (flags & spec_bit));
    }
    public static int cityGetSpec(int city_id) throws InterruptedException
    {
        obj_id city_hall = cityGetCityHall(city_id);
        int flags = cityGetStructureType(city_id, city_hall);
        return flags & SPEC_MASK;
    }
    public static String cityGetSpecString(int city_id) throws InterruptedException
    {
        int spec = cityGetSpec(city_id);
        int[] bits = dataTableGetIntColumn(CITY_SPECS, "BIT");
        for (int i = 0; i < bits.length; i++)
        {
            if (bits[i] == spec)
            {
                return dataTableGetString(CITY_SPECS, i, "SPECIALIZATION");
            }
        }
        return null;
    }
    public static int cityGetSpecCost(int city_id) throws InterruptedException
    {
        int spec = cityGetSpec(city_id);
        int[] bits = dataTableGetIntColumn(CITY_SPECS, "BIT");
        for (int i = 0; i < bits.length; i++)
        {
            if (bits[i] == spec)
            {
                return dataTableGetInt(CITY_SPECS, i, "COST");
            }
        }
        return 0;
    }
    public static boolean isCityZoned(int city_id) throws InterruptedException
    {
        obj_id city_hall = cityGetCityHall(city_id);
        int flags = cityGetStructureType(city_id, city_hall);
        return (0 != (flags & SF_REQUIRE_ZONE_RIGHTS));
    }
    public static void setCityZoned(int city_id, boolean zoned) throws InterruptedException
    {
        obj_id city_hall = cityGetCityHall(city_id);
        int flags = cityGetStructureType(city_id, city_hall);
        if (zoned)
        {
            flags |= SF_REQUIRE_ZONE_RIGHTS;
        }
        else 
        {
            flags = flags & ~SF_REQUIRE_ZONE_RIGHTS;
        }
        setStructureType(city_id, city_hall, flags);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Setting zoning requirement.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Zoning: " + zoned);
    }
    public static int checkCity(obj_id self, boolean notify) throws InterruptedException
    {
        obj_id checkobj = self;
        obj_id structure = getTopMostContainer(self);
        if (structure != null)
        {
            checkobj = structure;
        }
        int city_id = getCityAtLocation(getLocation(checkobj), 0);
        if (!cityExists(city_id))
        {
            city_id = getCityAtLocation(getLocation(getTopMostContainer(checkobj)), 0);
            if (!cityExists(city_id))
            {
                if (notify)
                {
                    sendSystemMessage(self, SID_NOT_IN_CITY);
                }
                return 0;
            }
        }
        return city_id;
    }
    public static int checkMayorCity(obj_id self, boolean notify) throws InterruptedException
    {
        int city_id = checkCity(self, notify);
        if (city_id == 0)
        {
            return 0;
        }
        obj_id mayor = cityGetLeader(city_id);
        if (mayor != self)
        {
            return 0;
        }
        return city_id;
    }
    public static boolean isNormalStructure(int city_id, obj_id structure) throws InterruptedException
    {
        int flags = cityGetStructureType(city_id, structure);
        if (0 != (flags & SPECIAL_STRUCTURE))
        {
            return false;
        }
        return true;
    }
    public static boolean isPayStructure(int city_id, obj_id structure) throws InterruptedException
    {
        int flags = cityGetStructureType(city_id, structure);
        if (0 == (flags & PAY_STRUCTURE))
        {
            return false;
        }
        return true;
    }
    public static boolean isMissionTerminal(int city_id, obj_id structure) throws InterruptedException
    {
        int flags = cityGetStructureType(city_id, structure);
        if (0 != (flags & SF_MISSION_TERMINAL))
        {
            return true;
        }
        return false;
    }
    public static boolean isSkillTrainer(int city_id, obj_id structure) throws InterruptedException
    {
        int flags = cityGetStructureType(city_id, structure);
        if (0 != (flags & SF_SKILL_TRAINER))
        {
            return true;
        }
        return false;
    }
    public static boolean isDecoration(int city_id, obj_id structure) throws InterruptedException
    {
        int flags = cityGetStructureType(city_id, structure);
        if (0 != (flags & SF_DECORATION))
        {
            return true;
        }
        return false;
    }
    public static int setStructureCityId(obj_id structure, int city_id) throws InterruptedException
    {
        if (player_structure.isBuilding(structure))
        {
            setBuildingCityId(structure, city_id);
        }
        else 
        {
            setObjVar(structure, "city_id", city_id);
        }
        return city_id;
    }
    public static int getStructureCityId(obj_id structure) throws InterruptedException
    {
        if (player_structure.isBuilding(structure))
        {
            return getBuildingCityId(structure);
        }
        else 
        {
            return getIntObjVar(structure, "city_id");
        }
    }
    public static void clearAbsenceFlags(obj_id citizen, int city_id) throws InterruptedException
    {
        String citname = cityGetCitizenName(city_id, citizen);
        obj_id citall = cityGetCitizenAllegiance(city_id, citizen);
        int flags = cityGetCitizenPermissions(city_id, citizen);
        flags = flags & ~CP_ABSENT_WEEK_1;
        flags = flags & ~CP_ABSENT_WEEK_2;
        flags = flags & ~CP_ABSENT_WEEK_3;
        flags = flags & ~CP_ABSENT_WEEK_4;
        flags = flags & ~CP_ABSENT_WEEK_5;
        citySetCitizenInfo(city_id, citizen, citname, citall, flags);
    }
    public static void doMayoralStructureTransfer(obj_id new_mayor, obj_id ousted_mayor, int city_id) throws InterruptedException
    {
        if (!isIdValid(new_mayor))
        {
            CustomerServiceLog("player_city_transfer", "New Mayor Structure Transfer Failed! New Mayor ID is not valid. City: (" + city_id + ") " + cityGetName(city_id) + ".");
            return;
        }
        if (!cityExists(city_id))
        {
            CustomerServiceLog("player_city_transfer", "New Mayor Structure Transfer Failed! City does not exist: (" + city_id + ") " + cityGetName(city_id) + ". Mayor is (" + new_mayor + ") " + cityGetCitizenName(city_id, new_mayor));
            return;
        }
        dictionary outparams = new dictionary();
        outparams.put("mayor", new_mayor);
        outparams.put("ousted_mayor", ousted_mayor);
        outparams.put("newMayor", true);
        obj_id[] structures = cityGetStructureIds(city_id);
        if (structures == null || structures.length <= 0)
        {
            CustomerServiceLog("player_city_transfer", "New Mayor Structure Transfer Failed! (" + new_mayor + ") " + cityGetCitizenName(city_id, new_mayor) + " of City (" + city_id + ") " + cityGetName(city_id) + " array was null or empty.");
            return;
        }
        for (int i = 0; i < structures.length; i++)
        {
            if (city.isNormalStructure(city_id, structures[i]))
            {
                if (!messageTo(structures[i], "setNewMayor", outparams, 5.0f, true))
                {
                    CustomerServiceLog("player_city_transfer", "City Civic Structure Transfer - MessageTo Failed: Structure(" + structures[i] + ") should be owned by Mayor(" + new_mayor + ") " + cityGetCitizenName(city_id, new_mayor) + ".");
                }
            }
            if (city.isDecoration(city_id, structures[i]))
            {
                if (!messageTo(structures[i], "setNewMayor", outparams, 5.0f, true))
                {
                    CustomerServiceLog("player_city_transfer", "City Decoration Transfer - MessageTo Failed: Structure(" + structures[i] + ") should be owned by Mayor(" + new_mayor + ") " + cityGetCitizenName(city_id, new_mayor) + ".");
                }
            }
        }
    }
    public static void moveNoTradeItemsInStructureToOwner(obj_id building, obj_id ousted_mayor, int city_id) throws InterruptedException
    {
        obj_id buildingOwner = getOwner(building);
        if ((isIdValid(buildingOwner) && ousted_mayor == buildingOwner))
        {
            obj_id[] cellList = getContents(building);
            if (cellList != null && cellList.length > 0)
            {
                for (int i = 0; i < cellList.length; i++)
                {
                    if ((getTemplateName(cellList[i])).equals(structure.TEMPLATE_CELL))
                    {
                        checkItemsForNoTradeTransfer(cellList[i], buildingOwner, city_id);
                    }
                }
            }
        }
        else 
        {
            CustomerServiceLog("player_city_transfer", "Mayor Change: Attempted to move NoTrade Items to Ousted Mayor's Inventory but failed: Structure Owner(" + buildingOwner + ") " + cityGetCitizenName(city_id, buildingOwner) + " was not the same as the Ousted Mayor(" + ousted_mayor + ") " + cityGetCitizenName(city_id, ousted_mayor) + ".");
        }
    }
    public static void checkItemsForNoTradeTransfer(obj_id container, obj_id player, int city_id) throws InterruptedException
    {
        if (!isIdValid(container))
        {
            CustomerServiceLog("player_city_transfer", "Mayoral NoTrade Item Transfer stopped - ContainerID was Invalid - " + container + ".");
            return;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("player_city_transfer", "Mayoral NoTrade Item Transfer stopped - PlayerID(Ousted Mayor) was Invalid - " + container + ".");
            return;
        }
        obj_id[] cellContents = getContents(container);
        if (cellContents != null && cellContents.length > 0)
        {
            for (int j = 0; j < cellContents.length; j++)
            {
                obj_id object = cellContents[j];
                if (isIdValid(object))
                {
                    boolean isValidPlayerOwnedObject = true;
                    obj_id objectOwner = getOwner(object);
                    if (player != objectOwner)
                    {
                        if (hasScript(object, "item.special.nomove"))
                        {
                            CustomerServiceLog("player_city_transfer", "Mayoral NoTrade Item Transfer - (" + object + ") " + getEncodedName(object) + " is NOT OWNED BY (" + player + ") " + cityGetCitizenName(city_id, player) + "(the ousted mayor) But was inside the City Hall/Cloning Center at the time of transfer of City: " + cityGetName(city_id) + ". Item is actually owned by (" + objectOwner + ") " + getEncodedName(objectOwner) + ".");
                            isValidPlayerOwnedObject = false;
                        }
                    }
                    if (!isObjectPersisted(object))
                    {
                        isValidPlayerOwnedObject = false;
                    }
                    if (isPlayer(object))
                    {
                        isValidPlayerOwnedObject = false;
                    }
                    if (isMob(object))
                    {
                        isValidPlayerOwnedObject = false;
                    }
                    if (hasCondition(object, CONDITION_VENDOR))
                    {
                        isValidPlayerOwnedObject = false;
                    }
                    if (isValidPlayerOwnedObject)
                    {
                        if (getContainerType(object) != 0 && getGameObjectType(object) != GOT_misc_factory_crate && !hasScript(object, "item.special.nomove"))
                        {
                            checkItemsForNoTradeTransfer(object, player, city_id);
                        }
                        if (hasScript(object, "item.special.nomove"))
                        {
                            CustomerServiceLog("player_city_transfer", "Mayoral NoTrade Item Transfer - NoTrade Item(" + object + ") " + getEncodedName(object) + " moved to ousted Mayor's(" + player + ") " + cityGetCitizenName(city_id, player) + " Inventory.  The item was located in a CityHall or Cloning Center when the City: (" + cityGetName(city_id) + ")" + cityGetName(city_id) + " transferred.");
                            moveToOfflinePlayerInventoryAndUnload(object, player);
                        }
                    }
                }
            }
        }
    }
    public static boolean hasZoningRights(obj_id player, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || city_id <= 0)
        {
            return false;
        }
        int cur_time = getGameTime();
        if (cityGetLeader(city_id) == player || isMilitiaOfCity(player, city_id))
        {
            return true;
        }
        if (hasObjVar(player, "city.zoning_rights") && hasObjVar(player, "city.zoning_rights_time"))
        {
            int city_identification_objvar = getIntObjVar(player, "city.zoning_rights");
            if (city_id != city_identification_objvar)
            {
                return false;
            }
            int rights_time = getIntObjVar(player, "city.zoning_rights_time");
            if (cur_time - rights_time > (24 * 60 * 60))
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasStorytellerZoningRights(obj_id player, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || city_id <= 0)
        {
            return false;
        }
        if (cityGetLeader(city_id) == player || isMilitiaOfCity(player, city_id))
        {
            return true;
        }
        int cur_time = getGameTime();
        if (hasObjVar(player, "city.zoning_rights") && hasObjVar(player, "city.zoning_rights_time"))
        {
            int city_identification_objvar = getIntObjVar(player, "city.zoning_rights");
            if (city_id == city_identification_objvar)
            {
                int rights_time = getIntObjVar(player, "city.zoning_rights_time");
                if (cur_time - rights_time <= (24 * 60 * 60))
                {
                    return true;
                }
            }
        }
        if (hasObjVar(player, "city.st_zoning_rights") && hasObjVar(player, "city.st_zoning_rights_time"))
        {
            int[] zoning_rights_array = getIntArrayObjVar(player, "city.st_zoning_rights");
            int[] zoning_times_array = getIntArrayObjVar(player, "city.st_zoning_rights_time");
            for (int i = 0; i < zoning_rights_array.length; i++)
            {
                if (zoning_rights_array[i] != city_id)
                {
                    continue;
                }
                if (cur_time - zoning_times_array[i] > (24 * 60 * 60))
                {
                    break;
                }
                else 
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasMayorProtectionFlag(obj_id player, int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int flags = cityGetCitizenPermissions(city_id, player);
        if (0 != (flags & CP_INACTIVE_PROTECTED))
        {
            return true;
        }
        return false;
    }
    public static int getMaxSafeHouseCount(int city_id) throws InterruptedException
    {
        int currentRank = getCityRank(city_id);
        int safeCount = 0;
        switch (currentRank)
        {
            case 1:
            safeCount = 1;
            break;
            case 2:
            safeCount = 2;
            break;
            case 3:
            safeCount = 3;
            break;
            case 4:
            safeCount = 6;
            break;
            case 5:
            safeCount = 8;
            break;
            default:
            break;
        }
        return safeCount;
    }
    public static int getCurrentSafeHouseCount(int city_id) throws InterruptedException
    {
        obj_id[] citizens = cityGetCitizenIds(city_id);
        int tracker = 0;
        if (citizens == null || citizens.length <= 0)
        {
            return 0;
        }
        for (int i = 0; i < citizens.length; i++)
        {
            if (!hasMayorProtectionFlag(citizens[i], city_id))
            {
                continue;
            }
            else 
            {
                tracker++;
            }
        }
        return tracker;
    }
    public static void addSafeHouseCitizen(int city_id, obj_id citizen) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        int flags = cityGetCitizenPermissions(city_id, citizen);
        flags = flags | CP_INACTIVE_PROTECTED;
        obj_id cit_all = cityGetCitizenAllegiance(city_id, citizen);
        citySetCitizenInfo(city_id, citizen, cityGetCitizenName(city_id, citizen), cit_all, flags);
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Added Safe House.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen + " is now protected from City Packup Rules.");
    }
    public static void removeSafeHouseCitizen(int city_id, obj_id citizen) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        int flags = cityGetCitizenPermissions(city_id, citizen);
        flags = flags & ~CP_INACTIVE_PROTECTED;
        obj_id cit_all = cityGetCitizenAllegiance(city_id, citizen);
        citySetCitizenInfo(city_id, citizen, cityGetCitizenName(city_id, citizen), cit_all, flags);
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        CustomerServiceLog("player_city", "Removed Safe House.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizen + " is no longer protected from City Packup Rules");
    }
    public static obj_id[] getCitySafeHouseCitizenList(int city_id) throws InterruptedException
    {
        Vector safeHouseCitizens = new Vector();
        safeHouseCitizens.setSize(0);
        if (!cityExists(city_id))
        {
            LOG("sissynoid", "Attempting to remove Safe House Citizen(s) - but City does not exist: City ID: " + city_id);
            CustomerServiceLog("player_city", "Attempting to remove Safe House Citizen(s) - but City does not exist: City ID: " + city_id);
            return null;
        }
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens == null || citizens.length <= 0)
        {
            LOG("sissynoid", "Attempting to remove Safe House Citizen(s) - but City does not Citizens(citizen array was null): CityID:" + city_id);
            CustomerServiceLog("player_city", "Attempting to remove Safe House Citizen(s) - but City does not Citizens(citizen array was null): CityID:" + city_id);
            return null;
        }
        for (int i = 0; i < citizens.length; i++)
        {
            if (!hasMayorProtectionFlag(citizens[i], city_id))
            {
                continue;
            }
            else 
            {
                utils.addElement(safeHouseCitizens, citizens[i]);
            }
        }
        obj_id[] returnArray = utils.toStaticObjIdArray(safeHouseCitizens);
        return returnArray;
    }
}
