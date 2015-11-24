package script.guild;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.prose;
import script.library.utils;
import script.library.money;
import script.library.xp;
import script.library.objvar_mangle;
import script.library.player_structure;
import script.library.debug;

public class master_city_object extends script.base_script
{
    public master_city_object()
    {
    }
    public static final int CITY_PROCESS_INTERVAL = 60 * 60;
    public static final int CITY_UPDATE_INTERVAL = 60 * 60 * 24 * 7;
    public static final string_id ELECTION_INCUMBENT_WIN_SUBJECT = new string_id("city/city", "election_incumbent_win_subject");
    public static final string_id ELECTION_INCUMBENT_WIN_BODY = new string_id("city/city", "election_incumbent_win_body");
    public static final string_id ELECTION_INCUMBENT_LOST_SUBJECT = new string_id("city/city", "election_incumbent_lost_subject");
    public static final string_id ELECTION_INCUMBENT_LOST_BODY = new string_id("city/city", "election_incumbent_lost_body");
    public static final string_id ELECTION_NEW_MAYOR_SUBJECT = new string_id("city/city", "election_new_mayor_subject");
    public static final string_id ELECTION_NEW_MAYOR_BODY = new string_id("city/city", "election_new_mayor_body");
    public static final string_id CITY_EXPAND_SUBJECT = new string_id("city/city", "city_expand_subject");
    public static final string_id CITY_EXPAND_BODY = new string_id("city/city", "city_expand_body");
    public static final string_id CITY_CONTRACT_SUBJECT = new string_id("city/city", "city_contract_subject");
    public static final string_id CITY_CONTRACT_BODY = new string_id("city/city", "city_contract_body");
    public static final string_id CITY_INVALID_SUBJECT = new string_id("city/city", "city_invalid_subject");
    public static final string_id CITY_INVALID_BODY = new string_id("city/city", "city_invalid_body");
    public static final string_id CITY_EXPAND_CAP_SUBJECT = new string_id("city/city", "city_expand_cap_subject");
    public static final string_id CITY_EXPAND_CAP_BODY = new string_id("city/city", "city_expand_cap_body");
    public static final string_id PUBLIC_ELECTION_INC_SUBJECT = new string_id("city/city", "public_election_inc_subject");
    public static final string_id PUBLIC_ELECTION_INC_BODY = new string_id("city/city", "public_election_inc_body");
    public static final string_id PUBLIC_ELECTION_SUBJECT = new string_id("city/city", "public_election_subject");
    public static final string_id PUBLIC_ELECTION_BODY = new string_id("city/city", "public_election_body");
    public static final string_id SID_CITY_UPDATE_ETA = new string_id("city/city", "city_update_eta");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "onCityUpdatePulse", null, CITY_PROCESS_INTERVAL, false);
        return SCRIPT_CONTINUE;
    }
    public int onCityUpdatePulse(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("player_city", "--- City Update ---");
        updateCities(self, false);
        messageTo(self, "onCityUpdatePulse", null, CITY_PROCESS_INTERVAL, false);
        return SCRIPT_CONTINUE;
    }
    public void updateCities(obj_id self, boolean force) throws InterruptedException
    {
        int[] cityUpdateIds = objvar_mangle.getMangledIntArrayObjVar(self, "cityUpdate.ids");
        int[] cityUpdateTimes = objvar_mangle.getMangledIntArrayObjVar(self, "cityUpdate.times");
        String strCityUpdateInterval = getConfigSetting("GameServer", "cityUpdateInterval");
        int cityUpdateInterval = 0;
        if (strCityUpdateInterval != null)
        {
            cityUpdateInterval = utils.stringToInt(strCityUpdateInterval);
        }
        if (cityUpdateInterval < CITY_PROCESS_INTERVAL)
        {
            cityUpdateInterval = CITY_UPDATE_INTERVAL;
        }
        int currentTime = getGameTime();
        int nextTime = currentTime + cityUpdateInterval;
        if (cityUpdateIds != null && cityUpdateTimes != null && cityUpdateIds.length == cityUpdateTimes.length)
        {
            for (int i = 0; i < cityUpdateIds.length; ++i)
            {
                if (force || cityUpdateTimes[i] <= currentTime)
                {
                    cityUpdateTimes[i] = nextTime;
                    cityUpdate(cityUpdateIds[i], self, cityUpdateInterval, true);
                }
            }
        }
        else 
        {
            cityUpdateIds = null;
            cityUpdateTimes = null;
        }
        int[] cityIds = getAllCityIds();
        int[] newCityUpdateTimes = new int[cityIds.length];
        for (int i = 0; i < cityIds.length; ++i)
        {
            int pos = findIntOffsetInTable(cityUpdateIds, cityIds[i]);
            if (pos == -1)
            {
                newCityUpdateTimes[i] = nextTime;
            }
            else 
            {
                newCityUpdateTimes[i] = cityUpdateTimes[pos];
            }
        }
        objvar_mangle.setMangledIntArrayObjVar(self, "cityUpdate.ids", cityIds);
        objvar_mangle.setMangledIntArrayObjVar(self, "cityUpdate.times", newCityUpdateTimes);
    }
    public int forceUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        int no_citizen_cleanup = params.getInt("no_citizen_cleanup");
        cityUpdate(city_id, self, CITY_UPDATE_INTERVAL, (no_citizen_cleanup == 0));
        return SCRIPT_CONTINUE;
    }
    public int forceElection(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        electMayor(city_id, self, CITY_UPDATE_INTERVAL);
        return SCRIPT_CONTINUE;
    }
    public int forceRank(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        int rank_change = params.getInt("rank_change");
        int rank = city.getCityRank(city_id) - 1;
        if (rank_change > 0)
        {
            increaseRank(city_id, rank, true);
        }
        else if (rank_change < 0)
        {
            decreaseRank(city_id, rank);
        }
        return SCRIPT_CONTINUE;
    }
    public void cityUpdate(int city_id, obj_id self, int interval, boolean cleanup) throws InterruptedException
    {
        obj_id cityHall = cityGetCityHall(city_id);
        String name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Updating City: " + name + " (" + city_id + "/" + cityHall + ")");
        int cityVoteInterval = getIntObjVar(self, "vote_interval." + city_id);
        cityVoteInterval++;
        if (cityVoteInterval == 3)
        {
            electMayor(city_id, self, interval);
            cityVoteInterval = 0;
        }
        setObjVar(self, "vote_interval." + city_id, cityVoteInterval);
        dictionary outparams = new dictionary();
        outparams.put("cityVoteInterval", cityVoteInterval);
        if (!messageTo(cityHall, "setVoteInterval", outparams, 0.f, true))
        {
            CustomerServiceLog("player_city", "Updating City: Vote Interval not Updated - Should have been set to " + cityVoteInterval + ". City: " + name + " (" + city_id + "/" + cityHall + ")");
        }
        obj_id incumbent_mayor = cityGetLeader(city_id);
        grantExperiencePoints(incumbent_mayor, xp.POLITICAL, 750);
        validateCityRadius(city_id, self);
        checkCivicCap(city_id, self);
        collectTaxes(city_id, self);
        cityMaintenance(city_id, self);
        obj_var_list ovl = getObjVarList(self, "vote_interval");
        if (ovl != null)
        {
            for (int i = 0; i < ovl.getNumItems(); i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String test_name = ov.getName();
                int test_id = utils.stringToInt(test_name);
                if (!cityExists(test_id))
                {
                    removeObjVar(self, "vote_interval." + test_name);
                }
            }
        }
    }
    public void electMayor(int city_id, obj_id self, int interval) throws InterruptedException
    {
        obj_id cityHall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "Election Cycle: " + city_name + " (" + city_id + "/" + cityHall + ")");
        Vector vote_ids = new Vector();
        vote_ids.setSize(0);
        Vector vote_counts = new Vector();
        vote_counts.setSize(0);
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens.length <= 0)
        {
            CustomerServiceLog("player_city", "Election Cycle for City: " + city_name + " (" + city_id + "/" + cityHall + ") INVALID Citizen Array - Allowing to continue momentarily");
        }
        for (int i = 0; i < citizens.length; i++)
        {
            if (!isIdValid(citizens[i]))
            {
                CustomerServiceLog("player_city", "Election Cycle for City: " + city_name + " (" + city_id + "/" + cityHall + ").  Citizen: (" + citizens[i] + ") is Invalid.");
            }
            boolean found = false;
            obj_id vote = cityGetCitizenAllegiance(city_id, citizens[i]);
            if (isIdValid(vote) && city.isCitizenOfCity(vote, city_id))
            {
                for (int j = 0; (j < vote_ids.size()); j++)
                {
                    if (((obj_id)vote_ids.get(j)) == vote)
                    {
                        vote_counts.set(j, new Integer(((Integer)vote_counts.get(j)).intValue() + 1));
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    utils.addElement(vote_ids, vote);
                    utils.addElement(vote_counts, 1);
                }
            }
            else 
            {
                CustomerServiceLog("player_city", "Election Cycle for City: " + city_name + " (" + city_id + "/" + cityHall + ").  Citizen: (" + citizens[i] + ") has 'Voted for/allegience to' an Invalid ID(" + vote + ")  This vote won't count.");
            }
        }
        obj_id incumbent_mayor = cityGetLeader(city_id);
        obj_id new_mayor = null;
        int max_votes = 0;
        int incumbent_votes = 0;
        int xp_granted = 0;
        int csTotal = 0;
        if (vote_ids.size() == 0)
        {
            CustomerServiceLog("player_city", "Election Cycle: No one Voted in this election.  City: " + city_name + " (" + city_id + "/" + cityHall + "). Incumbent will remain Mayor - ***NULL*** CS Log will be generated below.");
        }
        for (int i = 0; i < vote_ids.size(); i++)
        {
            if (isIdValid(((obj_id)vote_ids.get(i))))
            {
                CustomerServiceLog("player_city", "Election Cycle: Player has recieved " + ((Integer)vote_counts.get(i)).intValue() + " votes granting " + ((Integer)vote_counts.get(i)).intValue() * 100 + " points of " + xp.POLITICAL + " XP for running in the " + city_name + " (" + city_id + "/" + cityHall + ") election. " + ((obj_id)vote_ids.get(i)));
                grantExperiencePoints(((obj_id)vote_ids.get(i)), xp.POLITICAL, ((Integer)vote_counts.get(i)).intValue() * 300);
                xp_granted++;
            }
            else 
            {
                CustomerServiceLog("player_city", "Election Cycle: Invalid ID (" + ((obj_id)vote_ids.get(i)) + ") has recieved " + ((Integer)vote_counts.get(i)).intValue() + " votes in the City Election: " + city_name + " (" + city_id + "/" + cityHall + "). No Experience was granted due to Invalid ID.");
            }
            if (((obj_id)vote_ids.get(i)) == incumbent_mayor)
            {
                incumbent_votes = ((Integer)vote_counts.get(i)).intValue();
            }
            if (((Integer)vote_counts.get(i)).intValue() > max_votes)
            {
                max_votes = ((Integer)vote_counts.get(i)).intValue();
                new_mayor = ((obj_id)vote_ids.get(i));
            }
            csTotal += ((Integer)vote_counts.get(i)).intValue();
        }
        if (new_mayor == null)
        {
            CustomerServiceLog("player_city_transfer", "Incumbent Mayor (" + incumbent_mayor + ")" + cityGetCitizenName(city_id, incumbent_mayor) + " Of City(" + city_id + ")" + cityGetName(city_id) + " Wins due to NEW MAYOR obj_id being - **** NULL **** - ");
            new_mayor = incumbent_mayor;
        }
        boolean incumbent_win = false;
        if ((new_mayor == incumbent_mayor) || (new_mayor == null) || (max_votes == incumbent_votes) || city.isAMayor(new_mayor))
        {
            CustomerServiceLog("player_city", "Incumbent Win: " + incumbent_mayor + " Votes: " + incumbent_votes + " City: " + city_name + " (" + city_id + "/" + cityHall + ")");
            String imayor_name = cityGetCitizenName(city_id, incumbent_mayor);
            prose_package bodypp = prose.getPackage(ELECTION_INCUMBENT_WIN_BODY, incumbent_mayor, city_name);
            utils.sendMail(ELECTION_INCUMBENT_WIN_SUBJECT, bodypp, imayor_name, "City Hall");
            incumbent_win = true;
        }
        else 
        {
            CustomerServiceLog("player_city_transfer", "New Mayor: " + new_mayor + " Votes: " + max_votes + " City: " + city_name + " (" + city_id + "/" + cityHall + ")");
            city.setMayor(city_id, new_mayor);
            String nmayor_name = cityGetCitizenName(city_id, new_mayor);
            prose_package bodypp = prose.getPackage(ELECTION_NEW_MAYOR_BODY, new_mayor, city_name);
            utils.sendMail(ELECTION_NEW_MAYOR_SUBJECT, bodypp, nmayor_name, "City Hall");
            String imayor_name = cityGetCitizenName(city_id, incumbent_mayor);
            bodypp = prose.getPackage(ELECTION_INCUMBENT_LOST_BODY, new_mayor, city_name);
            utils.sendMail(ELECTION_INCUMBENT_LOST_SUBJECT, bodypp, imayor_name, "City Hall");
            city.doMayoralStructureTransfer(new_mayor, incumbent_mayor, city_id);
        }
        for (int i = 0; i < citizens.length; i++)
        {
            city.setCitizenAllegiance(city_id, citizens[i], null);
        }
        dictionary outparams = new dictionary();
        outparams.put("lastUpdateTime", getGameTime());
        outparams.put("currentInterval", interval);
        if (!messageTo(cityGetCityHall(city_id), "resetVoteTerminal", outparams, 0.f, true))
        {
            CustomerServiceLog("player_city_transfer", "ResetVotingTerminal for City (" + city_id + ") " + cityGetName(city_id) + " FAILED - MessageTo Failed to send: Candidate List has not been removed properly.");
        }
        String nmayor_name = cityGetCitizenName(city_id, new_mayor);
        for (int i = 0; i < citizens.length; i++)
        {
            String citizen_name = cityGetCitizenName(city_id, citizens[i]);
            if (incumbent_win)
            {
                prose_package bodypp = prose.getPackage(PUBLIC_ELECTION_INC_BODY, city_name, nmayor_name);
                utils.sendMail(PUBLIC_ELECTION_INC_SUBJECT, bodypp, citizen_name, "City Hall");
            }
            else 
            {
                prose_package bodypp = prose.getPackage(PUBLIC_ELECTION_BODY, city_name, nmayor_name);
                utils.sendMail(PUBLIC_ELECTION_SUBJECT, bodypp, citizen_name, "City Hall");
            }
        }
    }
    public void validateCityRadius(int city_id, obj_id self) throws InterruptedException
    {
        obj_id cityHall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "City Validation: " + city_name + " (" + city_id + "/" + cityHall + ")");
        int[] radiusList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_RADIUS);
        int[] popList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_POPULATION);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        int currentRadius = cityGetRadius(city_id);
        int currentPopReq = 0;
        int nextPopReq = 0;
        int rank = 0;
        for (int i = 0; i < popList.length; i++)
        {
            if (radiusList[i] == currentRadius)
            {
                rank = i;
                currentPopReq = popList[i];
                if (i + 1 < popList.length)
                {
                    nextPopReq = popList[i + 1];
                }
                break;
            }
        }
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens.length < popList[0])
        {
            rank = 0;
            int newRadius = radiusList[rank];
            dictionary params = new dictionary();
            params.put("rank", rank);
            params.put("radius", newRadius);
            messageTo(cityHall, "contractCity", params, 0.f, true);
            prose_package bodypp = prose.getPackage(CITY_INVALID_BODY, city_name, rank + 1);
            utils.sendMail(CITY_INVALID_SUBJECT, bodypp, mayor_name, "City Hall");
            messageTo(cityHall, "validateCity", null, 259200.f, true);
            CustomerServiceLog("player_city", "City Below Min Citizen Requirement!  Population: " + citizens.length + " City: " + city_name + " (" + city_id + "/" + cityHall + ")");
        }
        else if (citizens.length < currentPopReq)
        {
            if (!hasObjVar(cityHall, city.OBJVAR_DERANK_EXEMPT))
            {
                decreaseRank(city_id, rank);
            }
        }
        else if ((nextPopReq > 0) && (citizens.length >= nextPopReq))
        {
            increaseRank(city_id, rank);
        }
        else 
        {
            CustomerServiceLog("player_city", "City: " + city_name + " (" + city_id + "/" + cityHall + ")" + " Rank: " + rank + " CITY POP: " + citizens.length + " POP REQ: " + currentPopReq + " NEXT REQ: " + nextPopReq);
        }
    }
    public void decreaseRank(int city_id, int rank) throws InterruptedException
    {
        obj_id cityHall = cityGetCityHall(city_id);
        if (hasObjVar(cityHall, city.OBJVAR_DERANK_EXEMPT))
        {
            return;
        }
        int[] radiusList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_RADIUS);
        int[] popList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_POPULATION);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String city_name = cityGetName(city_id);
        rank--;
        if (rank < 0)
        {
            rank = 0;
        }
        int newRadius = radiusList[rank];
        CustomerServiceLog("player_city", "The city shrank. Rank: " + rank + " City: " + city_name + " (" + city_id + "/" + cityHall + ")");
        dictionary params = new dictionary();
        params.put("rank", rank);
        params.put("radius", newRadius);
        messageTo(cityHall, "contractCity", params, 0.f, true);
        prose_package bodypp = prose.getPackage(CITY_CONTRACT_BODY, city_name, rank + 1);
        utils.sendMail(CITY_CONTRACT_SUBJECT, bodypp, mayor_name, "City Hall");
    }
    public void increaseRank(int city_id, int rank, boolean forced) throws InterruptedException
    {
        int[] radiusList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_RADIUS);
        int[] popList = dataTableGetIntColumn(city.RANK_TABLE, city.RANK_POPULATION);
        obj_id cityHall = cityGetCityHall(city_id);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String city_name = cityGetName(city_id);
        location our_loc = cityGetLocation(city_id);
        String planet_name = our_loc.area;
        rank++;
        if (rank > city.RANK_MAX)
        {
            rank = city.RANK_MAX;
        }
        int newRadius = radiusList[rank];
        int medium_city_limit = 0;
        int big_city_limit = 0;
        String[] planets = dataTableGetStringColumn("datatables/city/city_limits.iff", "SCENE");
        for (int i = 0; i < planets.length; i++)
        {
            if (planets[i].equals(planet_name))
            {
                medium_city_limit = dataTableGetInt("datatables/city/city_limits.iff", i, "MEDIUM_CITY_LIMIT");
                big_city_limit = dataTableGetInt("datatables/city/city_limits.iff", i, "BIG_CITY_LIMIT");
                break;
            }
        }
        if (rank == 2)
        {
            int mid_size_cities = 0;
            int[] city_ids = getAllCityIds();
            for (int i = 0; i < city_ids.length; i++)
            {
                location city_loc = cityGetLocation(city_ids[i]);
                if (city_loc.area.equals(planet_name) && (city.getCityRank(city_ids[i]) >= 3))
                {
                    mid_size_cities++;
                }
            }
            if (!forced)
            {
                if (mid_size_cities >= medium_city_limit)
                {
                    prose_package bodypp = prose.getPackage(CITY_EXPAND_CAP_BODY, city_name, rank + 1);
                    utils.sendMail(CITY_EXPAND_CAP_SUBJECT, bodypp, mayor_name, "Planetary Civic Authority");
                    return;
                }
            }
        }
        else if (rank == 3)
        {
            int large_size_cities = 0;
            int[] city_ids = getAllCityIds();
            for (int i = 0; i < city_ids.length; i++)
            {
                location city_loc = cityGetLocation(city_ids[i]);
                if (city_loc.area.equals(planet_name) && (city.getCityRank(city_ids[i]) >= 4))
                {
                    large_size_cities++;
                }
            }
            if (!forced)
            {
                if (large_size_cities >= big_city_limit)
                {
                    prose_package bodypp = prose.getPackage(CITY_EXPAND_CAP_BODY, city_name, rank + 1);
                    utils.sendMail(CITY_EXPAND_CAP_SUBJECT, bodypp, mayor_name, "Planetary Civic Authority");
                    return;
                }
            }
        }
        CustomerServiceLog("player_city", "The city grew. Rank: " + rank + " City: " + city_name + " (" + city_id + "/" + cityHall + ")");
        dictionary params = new dictionary();
        params.put("rank", rank);
        params.put("radius", newRadius);
        messageTo(cityHall, "expandCity", params, 0.f, true);
        prose_package bodypp = prose.getPackage(CITY_EXPAND_BODY, city_name, rank + 1);
        utils.sendMail(CITY_EXPAND_SUBJECT, bodypp, mayor_name, "City Hall");
    }
    public void increaseRank(int city_id, int rank) throws InterruptedException
    {
        increaseRank(city_id, rank, false);
    }
    public void collectTaxes(int city_id, obj_id self) throws InterruptedException
    {
    }
    public void cityMaintenance(int city_id, obj_id self) throws InterruptedException
    {
        obj_id city_hall = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        CustomerServiceLog("player_city", "City Maintenance: " + city_name + " (" + city_id + "/" + city_hall + ")");
        messageTo(city_hall, "payMaintenance", null, 0.f, true);
    }
    public int findIntOffsetInTable(int[] from, int find) throws InterruptedException
    {
        if (from != null)
        {
            for (int i = 0; i < from.length; ++i)
            {
                if (from[i] == find)
                {
                    return i;
                }
            }
        }
        return -1;
    }
    public int confirmCityRemoved(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id city_hall = params.getObjId("city_hall");
        if (city_hall == null)
        {
            return SCRIPT_CONTINUE;
        }
        int city_id = findCityByCityHall(city_hall);
        if (cityExists(city_id))
        {
            String city_name = cityGetName(city_id);
            debug.debugAllMsg("PLAYER CITY", self, "A city continued to exist after its hall was deleted!  Attempting to remove the city.  City: " + city_id + " Hall: " + city_hall);
            CustomerServiceLog("player_city", "A city continued to exist after its hall was deleted!  Attempting to remove the city.  City: " + city_name + " (" + city_id + "/" + city_hall + ")");
            removeCity(city_id);
            return SCRIPT_CONTINUE;
        }
        debug.debugAllMsg("PLAYER CITY", self, "City master object confirmed removal of city.  City: " + city_id + " Hall: " + city_hall);
        CustomerServiceLog("player_city", "City master object confirmed removal of city.  City: " + " (" + city_id + "/" + city_hall + ")");
        return SCRIPT_CONTINUE;
    }
    public int reportUpdateEstimate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int city_id = params.getInt("city_id");
        int[] cityUpdateIds = objvar_mangle.getMangledIntArrayObjVar(self, "cityUpdate.ids");
        int[] cityUpdateTimes = objvar_mangle.getMangledIntArrayObjVar(self, "cityUpdate.times");
        if (cityUpdateIds != null && cityUpdateTimes != null && cityUpdateIds.length == cityUpdateTimes.length)
        {
            for (int i = 0; i < cityUpdateIds.length; ++i)
            {
                if (cityUpdateIds[i] == city_id)
                {
                    int curt = getGameTime();
                    int diff = cityUpdateTimes[i] - curt;
                    String time_remaining = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(diff));
                    if (time_remaining.equals("error"))
                    {
                        time_remaining = "Update Pending: Your city will update with the next server city pulse.";
                    }
                    dictionary outparams = new dictionary();
                    outparams.put("time_remaining", time_remaining);
                    messageTo(player, "displayCityUpdateTime", outparams, 0.f, false);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkCivicCap(int city_id, obj_id self) throws InterruptedException
    {
        obj_id city_hall = cityGetCityHall(city_id);
        messageTo(city_hall, "checkCivicCap", null, 0.f, true);
    }
    public void cleanupCitizens(int city_id, obj_id self) throws InterruptedException
    {
        obj_id mayor = cityGetLeader(city_id);
        obj_id[] citizens = cityGetCitizenIds(city_id);
        for (int i = 0; i < citizens.length; i++)
        {
            if (citizens[i] == mayor)
            {
                continue;
            }
            int flags = cityGetCitizenPermissions(city_id, citizens[i]);
            if ((flags & city.CP_ABSENT_WEEK_5) != 0)
            {
                String mayor_name = cityGetCitizenName(city_id, mayor);
                prose_package bodypp = prose.getPackage(city.LOST_INACTIVE_CITIZEN_BODY, cityGetCitizenName(city_id, citizens[i]));
                utils.sendMail(city.LOST_INACTIVE_CITIZEN_SUBJECT, bodypp, mayor_name, "City Hall");
                String city_name = cityGetName(city_id);
                obj_id city_hall = cityGetCityHall(city_id);
                cityRemoveCitizen(city_id, citizens[i]);
                CustomerServiceLog("player_city", "Removed citizen due to inactivity.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + citizens[i]);
                continue;
            }
            else if ((flags & city.CP_ABSENT_WEEK_4) != 0)
            {
                flags = flags | city.CP_ABSENT_WEEK_5;
            }
            else if ((flags & city.CP_ABSENT_WEEK_3) != 0)
            {
                flags = flags | city.CP_ABSENT_WEEK_4;
            }
            else if ((flags & city.CP_ABSENT_WEEK_2) != 0)
            {
                flags = flags | city.CP_ABSENT_WEEK_3;
            }
            else if ((flags & city.CP_ABSENT_WEEK_1) != 0)
            {
                flags = flags | city.CP_ABSENT_WEEK_2;
            }
            else 
            {
                flags = flags | city.CP_ABSENT_WEEK_1;
            }
            String citname = cityGetCitizenName(city_id, citizens[i]);
            obj_id citall = cityGetCitizenAllegiance(city_id, citizens[i]);
            citySetCitizenInfo(city_id, citizens[i], citname, citall, flags);
        }
    }
}
