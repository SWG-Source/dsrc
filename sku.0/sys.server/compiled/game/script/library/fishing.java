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
    public static final String OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT = "fishing.elusive_fish_caught"; // count also used for guild/city travel point perk
    public static final String OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL = "elusive_fish.leaderboard_total";
    public static final String OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE = "elusive_fish.leaderboard.e_";
    public static final String OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE = "elusive_fish.count_table.fish_";
    public static final String OBJVAR_ELUSIVE_FISH_COUNT_TABLE_MADE = "elusive_fish.count_table_made";
    public static final String OBJVAR_ELUSIVE_FISH_COUNT_TOTAL= "elusive_fish.count_rewarded_total";
    public static final String OBJVAR_PLANET_OBJECT_REFERENCE = "master_fishing_object";
    public static final String OBJVAR_LARGEST_FISH_CAUGHT_THIS_PERIOD = "fishing.record_fish_current_max";

    /**
     * getMasterFishingObject
     * The Master Fishing Object is an invisible ghost object which exists in the world to store information about fishing records and data (as ObjVars).
     * A controller in tatooine_5_5 buildout uses fishing.controller script to handle MFO creation.
     * The controller spawns the master object if it doesn't exist OnInitialize, attaches fishing.master_object, and persists to the world
     * @return The obj_id of the master fishing object
     */
    public static obj_id getMasterFishingObject() throws InterruptedException {
        return getObjIdObjVar(getPlanetByName("tatooine"), OBJVAR_PLANET_OBJECT_REFERENCE);
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
        return getIntObjVar(getMasterFishingObject(), OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fishType) >= 1;
    }

    /**
     * handleElusiveFishRollAndWin - To be initiated by fishing process when player catches a fish
     * This is triggered at the point of the fish actually being caught and transferred to the player, then the system rolls to
     * decide if it should make this an elusive fish.
     * @param player the player fishing
     * @param fish the fish the player caught
     */
    public static void handleElusiveFishRollAndWin(obj_id player, obj_id fish) throws InterruptedException {
        if(!isIdValid(player) || !isIdValid(fish)) {
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

        obj_id masterFishingObject = getMasterFishingObject();
        dictionary d = new dictionary();
        String fishType = getStringObjVar(fish, "fish.name");
        if(!badge.hasBadge(player, "bdg_fishing_elusive_fish_"+fishType)) {
            badge.grantBadge(player, "bdg_fishing_elusive_fish_"+fishType);
        }
        // make the fish elusive
        attachScript(fish, "fishing.elusive_fish");
        // update the player's personal count of caught elusive fish (this is also used for granting travel point perks)
        if(!hasObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT)) {
            setObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT, 1);
        } else {
            setObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT, getIntObjVar(player, OBJVAR_ELUSIVE_FISH_PLAYER_CAUGHT_COUNT)+1);
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
        d.put("fishType", fishType);
        d.put("elusiveFishLeaderboardItems", elusiveFishLeaderboardItems);
        if(hasElusiveFishBeenCollected(fish)) {
            if (utils.checkConfigFlag("Fishing", "elusiveFishShowDuplicatesOnLeaderboard")) {
                messageTo(masterFishingObject, "handleUpdateElusiveFishLeaderboard", d, 6f, true);
            }
        } else {
            messageTo(masterFishingObject, "handleUpdateElusiveFishLeaderboard", d, 6f, true);
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
        return getIntObjVar(getMasterFishingObject(), OBJVAR_ELUSIVE_FISH_COUNT_TOTAL);
    }

    /**
     * getCountOfSpecificElusiveFishRewarded
     * Returns the amount of elusive fish that have been rewarded for the the fish type specified
     * @param fish the name of the elusive fish (reference elusive fish datatable) e.g. bluefish_dantooine
     */
    public static int getCountOfSpecificElusiveFishRewarded(String fish) throws InterruptedException {
        return getIntObjVar(getMasterFishingObject(), OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fish);
    }

    /**
     * showElusiveFishLeaderboard
     * Constructs and then shows the player a SUI Table containing the Elusive Fish Leaderboard
     * @param player the player who should get the SUI window
     */
    public static void showElusiveFishLeaderboard(obj_id player) throws InterruptedException {
        int fishCount = getElusiveFishRewardedCount();
        String prompt = "Showing all caught ELUSIVE Fish on the " + getClusterName() + " Galaxy as of " + getCalendarTimeStringGMT(getCalendarTime()) + "\n\n";
        String[] columns = { "Type", "Planet", "Length", "Angler", "Location", "Time Caught" };
        String[] columnTypes = { "text", "text", "text", "text", "text", "text" };
        String[][] leaderboardData = new String[fishCount][columns.length];
        for (int i = 0; i < fishCount; i++) {
            String[] leaderboardEntry = getStringArrayObjVar(getMasterFishingObject(), OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+i);
            for (int a = 0; a < leaderboardEntry.length; a++) {
                leaderboardData[i][a] = leaderboardEntry[a];
            }
        }
        sui.table(player, player, sui.OK_ONLY, "ELUSIVE Fish Leaderboard", "noHandler", prompt, columns, columnTypes, leaderboardData, true, true);
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
     * Determines if you are eligible to contribute to your city's fishing score in the current period
     * Based on the requirement that: You must have a declared residence in the city, and you must not have moved during the current period
     */
    public static boolean isCityMemberEligibleToContributeToCityFishingScore(obj_id player) throws InterruptedException {
        return (getCitizenOfCityId(player) > 0) && (getIntObjVar(player, player_structure.VAR_RESIDENCE_CAN_DECLARE) > (getGameTime() + 25200));
    }

    /**
     * Determines if you are eligible to contribute to your guild's fishing score in the current period
     * Based on the requirement that you must be a member of a guild and held that membership for longer than the current period
     */
    public static boolean isGuildMemberEligibleToContributeToGuildFishingScore(obj_id player) throws InterruptedException {
        return (getGuildId(player) > 0) && (getIntObjVar(player, guild.VAR_TIME_JOINED_CURRENT_GUILD) > (getGameTime() + 25200));
    }

}
