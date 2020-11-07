package script.library;

import script.dictionary;
import script.location;
import script.obj_id;

import java.util.Arrays;
import java.util.List;

public class fishing extends script.base_script {

    public fishing()
    {
    }

    /*
    Config options for customizing fishing system via localOptions.cfg (values represent defaults)

    [Fishing]
    elusiveFishEnabled=true
    elusiveFishChance=0.005
    elusiveFishMaxPerGalaxy=70
    elusiveFishOverrideOneTypePerGalaxyLimit=false
    elusiveFishShowDuplicatesOnLeaderboard=false
     */

    public static final int ELUSIVE_FISH_MAX_GALAXY = 70;
    public static final double ELUSIVE_FISH_CHANCE = 0.005d; // 1% chance default
    public static final String ELUSIVE_FISH_TABLE = "datatables/fishing/fish/elusive_fish.iff";
    public static final obj_id FISHING_OBJECT = getObjIdObjVar(getPlanetByName("tatooine"), "master_fishing_object");
    public static final String OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT = "fishing.elusive_fish_caught"; // count also used for guild/city travel point perk
    public static final String OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL = "elusive_fish.leaderboard_total";
    public static final String OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE = "elusive_fish.leaderboard.e_";
    public static final String OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE = "elusive_fish.count_table.fish_";
    public static final String OBJVAR_ELUSIVE_FISH_COUNT_TABLE_MADE = "elusive_fish.count_table_made";
    public static final String OBJVAR_ELUSIVE_FISH_COUNT_TOTAL= "elusive_fish.count_rewarded_total";

    /**
     * getMasterFishingObject
     * The Master Fishing Object is an invisible ghost object which exists in the world to store information about fishing records and data (as ObjVars).
     * It is in the tatooine_5_5 buildout and initialized via fishing.master_object script
     * @return The obj_id of the master fishing object
     */
    public static obj_id getMasterFishingObject() throws InterruptedException {
        return getObjIdObjVar(getPlanetByName("tatooine"), "master_fishing_object");
    }

    /**
     * isFish
     * Validates by template if the object specified is a fish GOT
     * @param fish the fish to check
     * @return true if the object is a fish
     */
    public static boolean isFish(obj_id fish) throws InterruptedException {
        String template = getTemplateName(fish);
        return template.contains("fishing/fish/") && !template.contains("tank");
    }

    /**
     * getFishSpecies
     * Parses the raw fish species/type (e.g. "Blackfish") from the stored name that includes other information
     * Works on basic, non-rare fish only
     * @param fish the fish to get the species of
     * @return the species of the fish
     */
    public static String getFishSpecies(obj_id fish) throws InterruptedException {
        String name = getStringObjVar(fish, "fish.name");
        return name.substring(0, name.indexOf("_"));
    }

    //TODO getFishSpecies and getFishPlanet need handling for parsing for "rare" fish

    /**
     * getFishPlanet
     * Parses the raw origin planet of the fish (the planet the fish was caught on)
     * Works on basic, non-rare fish only
     * @param fish the fish to get the origin planet of
     * @return the origin planet of the fish
     */
    public static String getFishPlanet(obj_id fish) throws InterruptedException {
        String name = getStringObjVar(fish, "fish.name");
        return name.substring(name.indexOf("_") + 1);
    }

    /**
     * getFishLength
     * @param fish the fish to get the length of
     * @return the length of the fish
     */
    public static float getFishLength(obj_id fish) throws InterruptedException {
        return getFloatObjVar(fish, "fish.length");
    }

    /**
     * getFishCaughtLocation
     * @param fish the fish
     * @return the location object of where it was caught
     */
    public static location getFishCaughtLocation(obj_id fish) throws InterruptedException {
        return getLocationObjVar(fish, "fish.location");
    }

    /**
     * getFishCaughtTime
     * @param fish the fish
     * @return the unix timestamp of when it was caught
     */
    public static int getFishCaughtTime(obj_id fish) throws InterruptedException {
        return getIntObjVar(fish, "fish.time");
    }

    /**
     * getFishCaughtTimeReadable
     * @param fish the fish
     * @return the caught time of the fish in a readable GMT format
     */
    public static String getFishCaughtTimeReadable(obj_id fish) throws InterruptedException {
        return getCalendarTimeStringGMT(getFishCaughtTime(fish));
    }

    /**
     * getFishCatcher
     * @param fish the fish
     * @return the name of the player who caught the fish
     */
    public static String getFishCatcher(obj_id fish) throws InterruptedException {
        return getStringObjVar(fish, "fish.catcher");
    }

    /**
     * getFishCatcherObjectId
     * @param fish the fish
     * @return the ObjID of the player who caught the fish
     */
    public static obj_id getFishCatcherObjectId(obj_id fish) throws InterruptedException {
        return getPlayerIdFromFirstName(getFishCatcher((fish)));
    }


    // ***********************************************
    // ** Elusive Fish Stuff
    // ***********************************************
    // Elusive Fish are non-collection fish divided per planet (10 base planets)
    // Striped, Ray, Laa, Faa, Bluefish, Blowfish, and Blackfish (7*10 = 70 total)
    // Limited to 1 caught of each type per galaxy

    /**
     * isElusiveFish
     * @param fish the fish to check
     * @return true if the fish is elusive
     */
    public static boolean isElusiveFish(obj_id fish) throws InterruptedException {
        return hasScript(fish, "fishing.elusive_fish");
    }

    /**
     * isElusiveFishTypeEligible
     * @param fish the fish to check
     * @return true if the fish is of an allowed type that can become elusive (from ELUSIVE_FISH_TABLE).
     */
    public static boolean isElusiveFishTypeEligible(obj_id fish) throws InterruptedException {
        List<String> fishTable = Arrays.asList(dataTableGetStringColumn(ELUSIVE_FISH_TABLE, "fish"));
        String fishType = getStringObjVar(fish, "fish.name");
        return fishTable.contains(fishType);
    }

    /**
     * hasElusiveFishBeenCollected
     * @param fish the fish to check
     * @return true if the elusive version of the fish type (e.g. faa_talus) has already been found/awarded
     */
    public static boolean hasElusiveFishBeenCollected(obj_id fish) throws InterruptedException {
        String fishType = getStringObjVar(fish, "fish.name");
        return getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fishType) >= 1;
    }

    /**
     * isWinningElusiveFish - To be initiated by fishing process when player catches a fish
     * This is triggered at the point of the fish actually being caught and transferred to the player, then the system rolls to
     * decide if it should make this an elusive fish.
     * @param player the player fishing
     * @param fish the fish the player caught
     */
    public static void handleElusiveFishRollAndWin(obj_id player, obj_id fish) throws InterruptedException {
        if(!isIdValid(FISHING_OBJECT)) {
            return;
        }
        if(!utils.checkConfigFlag("Fishing", "elusiveFishEnabled")) {
            return;
        }
        if(!utils.checkConfigFlag("Fishing", "elusiveFishOverrideOneTypePerGalaxyLimit")) {
            if(hasReachedMaxGalaxyElusiveFish() || hasElusiveFishBeenCollected(fish) || !isElusiveFishTypeEligible(fish)) {
                return;
            }
        } else {
            if(!isElusiveFishTypeEligible(fish)) {
                return;
            }
        }
        String cfgChance = getConfigSetting("Fishing", "elusiveFishChance");
        double chance = cfgChance == null ? ELUSIVE_FISH_CHANCE : Double.parseDouble(cfgChance) / 100;
        double rollValue = Math.random();
        if(rollValue > chance){
            return;
        }
        giveElusiveFishRewards(player, fish);
    }

    /**
     * giveElusiveFishRewards - Gives rewards and handles converting the caught fish to Elusive
     * @param player the player who caught the fish
     * @param fish the fish the player caught
     */
    public static void giveElusiveFishRewards(obj_id player, obj_id fish) throws InterruptedException {

        String fishType = getStringObjVar(fish, "fish.name");
        if(!badge.hasBadge(player, "bdg_fishing_elusive_fish_"+fishType)) {
            badge.grantBadge(player, "bdg_fishing_elusive_fish_"+fishType);
        }
        // make the fish elusive
        attachScript(fish, "fishing.elusive_fish");
        // count this specific fish as caught
        setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fishType, getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fishType)+1);
        // count the overall number of elusive fish caught
        setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TOTAL, getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TOTAL)+1);
        // update the player's personal count of caught elusive fish (this is also used for granting travel point perks)
        if(!hasObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT)) {
            setObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT, 1);
        } else {
            setObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT, getIntObjVar(player, "elusive_fish_caught")+1);
        }
        // pack fish information and send to leaderboard, then log the catch
        String species = getFishSpecies(fish);
        String planet = getFishPlanet(fish);
        String[] elusiveFishLeaderboardItems = {
                "a "+species.substring(0, 1).toUpperCase() + species.substring(1),
                planet.substring(0, 1).toUpperCase() + planet.substring(1),
                getFishLength(fish)+"m",
                getPlayerFullName(getFishCatcherObjectId(fish)),
                (int)getFishCaughtLocation(fish).x+", "+(int)getFishCaughtLocation(fish).z,
                getFishCaughtTimeReadable(fish),
        };
        if(hasElusiveFishBeenCollected(fish)) {
            if (utils.checkConfigFlag("Fishing", "elusiveFishShowDuplicatesOnLeaderboard")) {
                handleUpdateElusiveFishLeaderboard(elusiveFishLeaderboardItems);
            }
        } else {
            handleUpdateElusiveFishLeaderboard(elusiveFishLeaderboardItems);
        }
        CustomerServiceLog("elusive_fish", getPlayerName(player)+" ("+player+") caught elusive fish "+fishType+" ("+fish+")");
    }

    /**
     * hasReachedMaxGalaxyElusiveFish
     * @return true if the galaxy has already reached its max limit on catchable elusive fish
     */
    public static boolean hasReachedMaxGalaxyElusiveFish() throws InterruptedException {
        return getElusiveFishRewardedCount() >= getMaxElusiveFishAllowedInGalaxy();
    }

    /**
     * getMaxElusiveFishAllowedInGalaxy
     * @return the max number of elusive fish that can be found in the galaxy (70 is SOE default, or use config to override)
     */
    public static int getMaxElusiveFishAllowedInGalaxy() throws InterruptedException {
        int configMaxElusiveFish = utils.getIntConfigSetting("Fishing", "elusiveFishMaxPerGalaxy");
        if (configMaxElusiveFish > 0) {
            return configMaxElusiveFish;
        } else {
            return ELUSIVE_FISH_MAX_GALAXY;
        }
    }

    /**
     * getElusiveFishRewardedCount
     * @return the number of elusive fish that have been caught on the cluster
     */
    public static int getElusiveFishRewardedCount() throws InterruptedException {
        return getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TOTAL);
    }

    /**
     * getCountOfSpecificElusiveFishRewarded
     * Returns the amount of elusive fish that have been rewarded for the the fish type specified
     * @param fish the name of the elusive fish (reference elusive fish datatable) e.g. bluefish_dantooine
     */
    public static int getCountOfSpecificElusiveFishRewarded(String fish) throws InterruptedException {
        return getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fish);
    }

    /**
     * showElusiveFishLeaderboard
     * Constructs and then shows the player a SUI Table containing the Elusive Fish Leaderboard
     * @param player the player who should get the SUI window
     * @param params currently unused dictionary of params for future implementation
     */
    public static void showElusiveFishLeaderboard(obj_id player, dictionary params) throws InterruptedException {
        int fishCount = getElusiveFishRewardedCount();
        String prompt = "Showing all caught ELUSIVE Fish on the " + getClusterName() + " Galaxy as of " + getCalendarTimeStringGMT(getCalendarTime()) + "\n\n";
        String[] columns = { "Type", "Planet", "Length", "Angler", "Location", "Time Caught" };
        String[] columnTypes = { "text", "text", "text", "text", "text", "text" };
        String[][] leaderboardData = new String[fishCount][columns.length];
        for (int i = 0; i < fishCount; i++) {
            String[] leaderboardEntry = getStringArrayObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+i);
            for (int a = 0; a < leaderboardEntry.length; a++) {
                leaderboardData[i][a] = leaderboardEntry[a];
            }
        }
        sui.table(player, player, sui.OK_ONLY, "ELUSIVE Fish Leaderboard", "noHandler", prompt, columns, columnTypes, leaderboardData, true, true);
    }

    /**
     * handleUpdateElusiveFishLeaderboard
     * Adds an array of information to the elusive fish leaderboard as passed in giveElusiveFishRewards
     * @param fishInfo the packaged array of fish info to add to the leaderboard
     */
    public static void handleUpdateElusiveFishLeaderboard(String[] fishInfo) throws InterruptedException {
        if(!hasObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL)) {
            setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL, 1);
            setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+"0", fishInfo);
        } else {
            int leaderboardEntries = getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL);
            setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+leaderboardEntries, fishInfo);
            setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL, ++leaderboardEntries);
        }
    }

    /**
     * handleConstructElusiveFishTable
     * This is used to setup the ObjVars for all elusive fish (as a part of master fish object creation)
     */
    public static void handleConstructElusiveFishTable(obj_id masterObject) throws InterruptedException {
        if(hasObjVar(masterObject, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_MADE)) {
            return;
        }
        String[] elusiveFishTable = dataTableGetStringColumn(ELUSIVE_FISH_TABLE, "fish");
        for (String fish : elusiveFishTable) {
            setObjVar(masterObject, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fish, 0);
        }
        setObjVar(masterObject, OBJVAR_ELUSIVE_FISH_COUNT_TOTAL, 0);
        setObjVar(masterObject, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_MADE, 1);
    }

    /**
     * getElusiveFishCountTableTree
     * Returns a dictionary containing the counts of each elusive fish capture
     */
    public static dictionary getElusiveFishCountTableTree() throws InterruptedException {
        dictionary d = new dictionary();
        String[] fishes = dataTableGetStringColumn(ELUSIVE_FISH_TABLE, "fish");
        for (String fish : fishes) {
            d.put(fish, getCountOfSpecificElusiveFishRewarded(fish));
        }
        return d;
    }

    /**
     * clearElusiveFishLeaderboardAndTableDebugTestingOnly (for debug use only)
     * Resets the Elusive Fish table and leaderboard as though you're on a brand new server
     */
    public static void clearElusiveFishLeaderboardAndTableDebugTestingOnly() throws InterruptedException {
        String[] elusiveFishTable = dataTableGetStringColumn(ELUSIVE_FISH_TABLE, "fish");
        for (String fish : elusiveFishTable) {
            setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fish, 0);
        }
        setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TOTAL, 0);
        setObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_COUNT_TABLE_MADE, 1);
        int leaderboardEntries = getIntObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL);
        for (int i = 0; i <= leaderboardEntries; i++) {
            removeObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+i);
        }
        removeObjVar(FISHING_OBJECT, OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL);
    }


}
