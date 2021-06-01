package script.library;

import script.DiscordWebhook;
import script.obj_id;

import java.awt.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

/**
 * The Leaderboard Library is used to manipulate the GCW and Fishing (or any additional)
 * Leaderboard displays in the game, as well as how their respective data is tabulated
 * and stored.
 *
 * System Nomenclature:
 *
 * >>> Boards are either for "players" or "entities" (which are guilds or cities)
 * >>> A board "type" is what system it's for. There are 2 right now: Fishing & GCW
 *     which are referenced by constants LEADERBOARD_TYPE_FISHING & LEADERBOARD_TYPE_GCW
 * >>> You can "rank" on a board or "win" a board. To rank means to simply be displayed when
 *     browsing the list. To win means you are in an upper threshold of those ranked & get prizes.
 * >>> Boards that are faction-affiliated always reference faction by their  base constants
 *     FACTION_HASH_REBEL or FACTION_HASH_IMPERIAL, the crc32 hash of the faction name (int).
 * >>>>>> Default Rank Qualification: Top 25 players, Top 10 Cities, Top 10 Guilds
 * >>>>>> Default Win Qualification: Top 10 players, Top 5 Cities, Top 5 Guilds
 * >>> The first place winner of the board is the "champion" (or "reigning champion")
 * >>> A "period" is the time from which the leaderboard starts to end (by default,
 *     each period is 1 week long and ends Thursdays at 19:00 GMT, same as GCW reset)
 * >>> There are 3 periods of record: "Current," "Previous," and "2 Weeks Ago"
 * >>>>>> The "Current" period is the active period in continuous update cycles (by
 *        default, the period update heartbeat is every 15 minutes). When a period
 *        update heartbeat happens, this is called the "Period Update." When a period
 *        ends and data is moved/purged, this is called the "Period Reset."
 * >>>>>> The "Previous" period is the most recently ended period from which the
 *        "reigning champions" are determined.
 * >>>>>> The "2 Weeks Ago" period is largely ornamental the falloff of which is,
 *        however, used to remove the "War Planner" title, which all winners of the
 *        Previous and 2 Weeks Ago periods get (or the leader of guilds/cities).
 * >>>>>> In general, periods are referenced by constants LEADERBOARD_PERIOD_*
 * >>> The "War Planner" title is a perk for past winners as explained above but when
 *     it is granted, it also grants access to the Rebel/Imperial War Room Chat Channels.
 * >>> The "Archive" is a history for only Guilds and Cities which displays the past
 *     26 weeks (6 months) of their score data, along with some other
 *
 * System Functionality Notes:
 *
 * >>> Each leaderboard has a "Master Object" in Mos Eisley which stores the data and
 *     is used to handle sequential/timed activities (the period updates and resets).
 *     **NOTE: If the game server has no authority on the master object (e.g. if the zone
 *     goes to sleep or isn't active because there aren't players there) any messages
 *     and timed sequence activities will pause until the master object can be reached.
 *     There is eventual plans to make the Mos Eisley zone always spin on to resolve this
 *     as it also impacts other controllers/master objects, but that has not been done yet.
 * >>> Note that leaderboard rankings are used to award additional Travel Points under the
 *     Waypoint Registry system.
 * >>> See the master objects for other notes at systems.leaderboard.*_master_object
 * >>> Admin Commands:
 * >>>>>> /admin gcwLeaderboard         opens a SUI window with GM/Debug Commands
 * >>>>>> /admin dumpLeaderboardInfo    provides a dump of leaderboard info for the player
 * >>> A Web API is implemented so you can send leaderboard data via POST or via JSON to an
 *     PHP script or a Discord Web Hook, respectively.
 *
 * Future Implementations (ideas on how this can be expanded outside of cloning the SOE version):
 *
 * >>> It would be fairly easy to implement tracking for Guilds/Cities to display an "MVP"
 *     meaning the player in the guild/city that has scored the most for the guild/city
 * >>> Not only is it annoying to transpose cluster-wide scores to localized scores (see those
 *     methods for what this refers to), it's actually more interesting to see a % score under
 *     the context of the entire galaxy versus compared to just the other ranked entities, so
 *     either adding that to the display, or ranking by it instead, would be nice.
 * >>> By SOE's design, the weighting of these calculations lean somewhat heavily on factional
 *     presence and PvP which doesn't seem ideal.
 * >>> Space is factored into the current boards by nature of the fact that factional presence
 *     and GCW points are earned in space, but separate space leaderboards might be enjoyed
 *     more by the pilot community.
 *
 * Most methods have JavaDoc notes and are well-commented to explain reasoning/logic where
 * applicable. But if you have any questions, or to report any bugs, please feel free to
 * message Aconite in the SWG Source Discord.
 *
 * SWG Source Addition - 2021
 * Authors: Aconite
 */
@SuppressWarnings({"rawtypes", "unused"})
public class leaderboard extends script.base_script {

    public leaderboard()
    {
    }

    public static final int LEADERBOARD_TYPE_FISHING = 1;
    public static final int LEADERBOARD_TYPE_GCW = 2;
    public static final int LEADERBOARD_PERIOD_CURRENT = 1;
    public static final int LEADERBOARD_PERIOD_PREVIOUS = 2;
    public static final int LEADERBOARD_PERIOD_2WEEKSAGO = 3;
    public static final int PERIOD_LENGTH = 604800;
    public static final int[] WIN_COUNTS_WITH_BADGES = {1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
    public static final String FISHING = "_fishing";
    public static final String GCW = "_gcw";
    public static final String GCW_LEADERBOARD_TITLE = "GCW Leaderboard";
    public static final String[] GCW_LEADERBOARD_COL_NAMES = {"Rank", "Name", "Rank Value"};
    public static final String[] GCW_LEADERBOARD_COL_TYPES = {"text", "text", "text"};
    public static final String[] GCW_LEADERBOARD_ARCHIVE_COL_NAMES = {"Period Ending", "Faction", "Rank Value", "Winner (Y/N)", "Champion (Y/N)"};
    public static final String[] GCW_LEADERBOARD_ARCHIVE_COL_TYPES = {"text", "text", "text", "text", "text"};
    public static final String OBJVAR_LEADERBOARD_PERIOD = "leaderboard.current_period";
    public static final String OBJVAR_LEADERBOARD_PERIOD_START = "leaderboard.current_period_start";
    public static final String OBJVAR_LEADERBOARD_PERIOD_END = "leaderboard.current_period_end";
    public static final String OBJVAR_LEADERBOARD_PARTICIPANT_GROUP = "leaderboard.current_participants_";
    public static final String OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS = "leaderboard.current_participants_guild_";
    public static final String OBJVAR_LEADERBOARD_CITY_PARTICIPANTS = "leaderboard.current_participants_city_";
    public static final String OBJVAR_LEADERBOARD_PLAYER_SCORE_IMPERIAL = "leaderboard.score.player_imperial";
    public static final String OBJVAR_LEADERBOARD_PLAYER_SCORE_REBEL = "leaderboard.score.player_rebel";
    public static final String OBJVAR_LEADERBOARD_GUILD_SCORE_IMPERIAL = "leaderboard.score.guild_imperial";
    public static final String OBJVAR_LEADERBOARD_GUILD_SCORE_REBEL = "leaderboard.score.guild_rebel";
    public static final String OBJVAR_LEADERBOARD_CITY_SCORE_IMPERIAL = "leaderboard.score.city_imperial";
    public static final String OBJVAR_LEADERBOARD_CITY_SCORE_REBEL = "leaderboard.score.city_rebel";
    public static final String OBJVAR_LEADERBOARD_PLAYER_PERIOD = "leaderboard.current_period_to_player";
    public static final String OBJVAR_LEADERBOARD_PLAYER_WIN_COUNT = "leaderboard.winner_count.player_"; // append faction
    public static final String OBJVAR_LEADERBOARD_GUILD_WIN_COUNT = "leaderboard.winner_count.guild_"; // append faction
    public static final String OBJVAR_LEADERBOARD_CITY_WIN_COUNT = "leaderboard.winner_count.city_"; // append faction
    public static final String OBJVAR_CURRENT_PERIOD_LAST_UPDATED = "leaderboard.data.current.last_updated";
    public static final String OBJVAR_LAST_NOTIFIED_RANK_GCW_GUILD = "leaderboard.notify.gcw_guild_rank";
    public static final String OBJVAR_LAST_NOTIFIED_RANK_GCW_CITY = "leaderboard.notify.gcw_city_rank";
    public static final String OBJVAR_LAST_NOTIFIED_RANK_GCW_PLAYER = "leaderboard.notify.gcw_player_rank";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_ID = "leaderboard.gcw_data.current.players_imperial_ids";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_SCORE = "leaderboard.gcw_data.current.players_imperial_score";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_ID = "leaderboard.gcw_data.current.players_rebel_ids";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_SCORE = "leaderboard.gcw_data.current.players_rebel_score";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_ID = "leaderboard.gcw_data.current.guild_imperial_ids";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_SCORE = "leaderboard.gcw_data.current.guild_imperial_score";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_ID = "leaderboard.gcw_data.current.guild_rebel_ids";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_SCORE = "leaderboard.gcw_data.current.guild_rebel_score";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_ID = "leaderboard.gcw_data.current.city_imperial_ids";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_SCORE = "leaderboard.gcw_data.current.city_imperial_score";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_ID = "leaderboard.gcw_data.current.city_rebel_ids";
    public static final String OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_SCORE = "leaderboard.gcw_data.current.city_rebel_score";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_ID = "leaderboard.gcw_data.previous.players_imperial_ids";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_SCORE = "leaderboard.gcw_data.previous.players_imperial_score";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_ID = "leaderboard.gcw_data.previous.players_rebel_ids";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_SCORE = "leaderboard.gcw_data.previous.players_rebel_score";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_ID = "leaderboard.gcw_data.previous.guild_imperial_ids";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_SCORE = "leaderboard.gcw_data.previous.guild_imperial_score";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_ID = "leaderboard.gcw_data.previous.guild_rebel_ids";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_SCORE = "leaderboard.gcw_data.previous.guild_rebel_score";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_ID = "leaderboard.gcw_data.previous.city_imperial_ids";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_SCORE = "leaderboard.gcw_data.previous.city_imperial_score";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_ID = "leaderboard.gcw_data.previous.city_rebel_ids";
    public static final String OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_SCORE = "leaderboard.gcw_data.previous.city_rebel_score";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_IMPERIAL_ID = "leaderboard.gcw_data.2weeksago.players_imperial_ids";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_IMPERIAL_SCORE = "leaderboard.gcw_data.2weeksago.players_imperial_score";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_REBEL_ID = "leaderboard.gcw_data.2weeksago.players_rebel_ids";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_REBEL_SCORE = "leaderboard.gcw_data.2weeksago.players_rebel_score";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_IMPERIAL_ID = "leaderboard.gcw_data.2weeksago.guild_imperial_ids";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_IMPERIAL_SCORE = "leaderboard.gcw_data.2weeksago.guild_imperial_score";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_REBEL_ID = "leaderboard.gcw_data.2weeksago.guild_rebel_ids";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_REBEL_SCORE = "leaderboard.gcw_data.2weeksago.guild_rebel_score";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_IMPERIAL_ID = "leaderboard.gcw_data.2weeksago.city_imperial_ids";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_IMPERIAL_SCORE = "leaderboard.gcw_data.2weeksago.city_imperial_score";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_REBEL_ID = "leaderboard.gcw_data.2weeksago.city_rebel_ids";
    public static final String OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_REBEL_SCORE = "leaderboard.gcw_data.2weeksago.city_rebel_score";
    public static final String OBJVAR_DATA_GCW_ENTITY_HISTORY = "leaderboard.gcw_data.entity_history_"; // append guild/cityId
    public static final String OBJVAR_PLAYER_TRACKING_PLAYER_ALREADY = "leaderboard.already_tracking_player";
    public static final String OBJVAR_PLAYER_TRACKING_GUILD_ALREADY = "leaderboard.already_tracking_guild";
    public static final String OBJVAR_PLAYER_TRACKING_CITY_ALREADY = "leaderboard.already_tracking_city";
    public static final String OBJVAR_PLAYER_IMPERIAL_CURRENT_TEMP_SCORE = "leaderboard.temp_score.player_imperial";
    public static final String OBJVAR_PLAYER_REBEL_CURRENT_TEMP_SCORE = "leaderboard.temp_score.player_rebel";
    public static final String OBJVAR_GUILD_IMPERIAL_CURRENT_TEMP_SCORE = "leaderboard.temp_score.guild_imperial";
    public static final String OBJVAR_GUILD_REBEL_CURRENT_TEMP_SCORE = "leaderboard.temp_score.guild_rebel";
    public static final String OBJVAR_CITY_IMPERIAL_CURRENT_TEMP_SCORE = "leaderboard.temp_score.city_imperial";
    public static final String OBJVAR_CITY_REBEL_CURRENT_TEMP_SCORE = "leaderboard.temp_score.city_rebel";
    public static final String OBJVAR_LEADERBOARD_LAST_DATA_PULL_TIME = "leaderboard.last_data_pull_time";
    public static final String SCRIPTVAR_LEADERBOARD_LOCAL_SCORE_CACHED = "leaderboard.cached_data.local_score";
    public static final String SCRIPTVAR_LEADERBOARD_SCORE_DATA_CACHED = "leaderboard.cached_data.score_data";
    private static final String ANNOUNCEMENT_WEBHOOK_URL = getConfigSetting("GameServer", "leaderboardDiscordWebhook");
    private static final boolean VERBOSE_LOGGING = utils.checkConfigFlag("GameServer", "leaderboardVerboseLogging");

    /**
     * @return obj_id of the master leaderboard object, if it exists
     */
    public static obj_id getMasterLeaderboardObject() {
        obj_id id = getObjIdObjVar(getPlanetByName("tatooine"), "leaderboard.master_object");
        return isIdValid(id) ? id : obj_id.NULL_ID;
    }

    /**
     * @return the current period number for the leaderboards (+1 for each week they run).
     */
    public static int getCurrentLeaderboardPeriod() {
        return getIntObjVar(getMasterLeaderboardObject(), OBJVAR_LEADERBOARD_PERIOD);
    }

    /**
     * @return the Unix Timestamp of when the current leaderboard period started
     */
    public static int getCurrentLeaderboardPeriodStartTime() {
        return getIntObjVar(getMasterLeaderboardObject(), OBJVAR_LEADERBOARD_PERIOD_START);
    }

    /**
     * @return the stored Unix Timestamp of when the current leaderboard period will end
     */
    public static int getCurrentLeaderboardPeriodEndTime() {
        return getIntObjVar(getMasterLeaderboardObject(), OBJVAR_LEADERBOARD_PERIOD_END);
    }

    /**
     * @return Unix Timestamp of when the next leaderboard period will end
     * Works by evaluating the time until the nearest upcoming Thursday at 19:00 GMT
     * and then converts it to Unix Timestamp
     */
    public static int getNextLeaderboardPeriodEndTime() {
        ZoneId zone = ZoneId.of("GMT");
        LocalDateTime now = LocalDateTime.now(zone).with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).withHour(19).withMinute(0).withSecond(0);
        return (int)now.atZone(zone).toEpochSecond();
    }

    /**
     * @return the Unix Timestamp of when the current period data was last updated
     */
    public static int getCurrentLeaderboardPeriodLastUpdated() {
        return getIntObjVar(getMasterLeaderboardObject(), OBJVAR_CURRENT_PERIOD_LAST_UPDATED);
    }

    /**
     * @return true if we are in the middle of the period buffer,
     * which is 15 minutes before the end of a period
     */
    public static boolean isWithinPeriodBuffer() {
        return getCalendarTime() >= (getCurrentLeaderboardPeriodEndTime() - 900);
    }

    /**
     * @return true if the server started less than 15 minutes ago
     * In which case we don't want to be tracking/pushing data
     * Note: This helps catch a few issues with load sync and authority
     */
    public static boolean isWithinStartupBuffer() {
        //todo remove before push
        return false;
        //return getCalendarTime() <= (getIntObjVar(getPlanetByName("tatooine"), "server_startup_time") + 900);
    }

    /**
     * Adds the provided player (and their guild/city) to the participant tracking list for
     * the given leaderboard type so that the master object will know to include their data
     * when tabulating scores and winners.
     * @param player obj_id of player to add
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     */
    public static void addLeaderboardParticipantToCurrentPeriod(obj_id player, int type) throws InterruptedException {
        if(isWithinPeriodBuffer() || isWithinStartupBuffer()) {
            return;
        }
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                final obj_id[] fishing_ids = objvar_mangle.getMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+FISHING);
                if(fishing_ids == null) {
                    objvar_mangle.setMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+FISHING, new obj_id[] {player});
                } else {
                    if(!Arrays.asList(fishing_ids).contains(player)) {
                        obj_id[] newIds = Arrays.copyOf(fishing_ids, fishing_ids.length + 1);
                        newIds[newIds.length - 1] = player;
                        objvar_mangle.setMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP + FISHING, newIds);
                    }
                }
                break;
            case LEADERBOARD_TYPE_GCW:
                if(!hasObjVar(player, OBJVAR_PLAYER_TRACKING_PLAYER_ALREADY)) {
                    final obj_id[] gcw_ids = objvar_mangle.getMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP + GCW);
                    if (gcw_ids == null) {
                        // actually confirm that it doesn't exist manually because of sync delay
                        if(!hasObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+GCW+"_mangled.count")) {
                            objvar_mangle.setMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP + GCW, new obj_id[]{player});
                        }
                    } else {
                        if (!Arrays.asList(gcw_ids).contains(player)) {
                            obj_id[] newIds = Arrays.copyOf(gcw_ids, gcw_ids.length + 1);
                            newIds[newIds.length - 1] = player;
                            objvar_mangle.setMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP + GCW, newIds);
                        }
                    }
                    setObjVar(player, OBJVAR_PLAYER_TRACKING_PLAYER_ALREADY, true);
                    debugMsg("Adding new participant to GCW Leaderboard Participant Group: "+getPlayerName(player)+" ("+player+")");
                }
                final int guildId = getGuildId(player);
                final int cityId = getCitizenOfCityId(player);
                if(guildId > 0 && gcw.canContributeToGcwLeaderboardForGuild(player)) {
                    if (!hasObjVar(player, OBJVAR_PLAYER_TRACKING_GUILD_ALREADY)) {
                        Vector guildIds = getResizeableIntArrayObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS + GCW);
                        if (guildIds == null) {
                            setObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS + GCW, new int[]{guildId});
                        } else {
                            if (!guildIds.contains(guildId)) {
                                utils.addElement(guildIds, guildId);
                                setObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS + GCW, guildIds);
                            }
                        }
                        setObjVar(player, OBJVAR_PLAYER_TRACKING_GUILD_ALREADY, true);
                        debugMsg("Adding new guild to GCW Leaderboard Participant Group: " + guildGetAbbrev(guildId) + " (" + guildId + ")");
                    }
                }
                if(cityId > 0 && gcw.canContributeToGcwLeaderboardForCity(player)) {
                    if (!hasObjVar(player, OBJVAR_PLAYER_TRACKING_CITY_ALREADY)) {
                        Vector cityIds = getResizeableIntArrayObjVar(masterObject, OBJVAR_LEADERBOARD_CITY_PARTICIPANTS + GCW);
                        if (cityIds == null) {
                            setObjVar(masterObject, OBJVAR_LEADERBOARD_CITY_PARTICIPANTS + GCW, new int[]{cityId});
                        } else {
                            if (!cityIds.contains(cityId)) {
                                utils.addElement(cityIds, cityId);
                                setObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS + GCW, cityIds);
                            }
                        }
                        setObjVar(player, OBJVAR_PLAYER_TRACKING_CITY_ALREADY, true);
                        debugMsg("Adding new city to GCW Leaderboard Participant Group: " + cityGetName(cityId) + " (" + cityId + ")");
                    }
                }
                break;
        }
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @return a Set of all players that have participated in the current leaderboard period
     */
    public static Set<obj_id> getPlayerParticipantsForCurrentPeriod(int type) throws InterruptedException {
        final obj_id masterObject = getMasterLeaderboardObject();
        obj_id[] temp;
        Set<obj_id> data = new HashSet<>();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                temp = objvar_mangle.getMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+FISHING);
                if(temp != null) {
                    data.addAll(Arrays.asList(temp));
                    return data;
                }
            case LEADERBOARD_TYPE_GCW:
                temp = objvar_mangle.getMangledObjIdArrayObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+GCW);
                if(temp != null) {
                    data.addAll(Arrays.asList(temp));
                    return data;
                }
        }
        return null;
    }

    /**
     * @param isCity true if a city, false if a guild (to avoid 2 methods)
     * @return Returns a list of all entities (guild/city) that have participated in the current
     * leaderboard period for the provided leaderboard type.
     */
    public static Set<Integer> getEntityParticipantsForCurrentPeriod(int type, boolean isCity)  {
        final obj_id masterObject = getMasterLeaderboardObject();
        int[] temp;
        Set<Integer> data = new HashSet<>();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                if(isCity) {
                    temp = getIntArrayObjVar(masterObject, OBJVAR_LEADERBOARD_CITY_PARTICIPANTS+FISHING);
                } else {
                    temp = getIntArrayObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS+FISHING);
                }
                if(temp != null) {
                    Arrays.stream(temp).forEach(data::add);
                }
                return data;
            case LEADERBOARD_TYPE_GCW:
                if(isCity) {
                    temp = getIntArrayObjVar(masterObject, OBJVAR_LEADERBOARD_CITY_PARTICIPANTS+GCW);
                } else {
                    temp = getIntArrayObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS+GCW);
                }
                if(temp != null) {
                    Arrays.stream(temp).forEach(data::add);
                }
                return data;
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @param faction = FACTION_HASH_IMPERIAL or FACTION_HASH_REBEL
     * @param period = LEADERBOARD_PERIOD_CURRENT, LEADERBOARD_PERIOD_PREVIOUS, or LEADERBOARD_PERIOD_2WEEKSAGO
     * @return obj_id[] players ranked for the given period given the type and faction
     * Players are in rank order from 1st place to 25th place
     */
    public static obj_id[] getRankedPlayersForPeriod(int type, int faction, int period) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                break;
            case LEADERBOARD_TYPE_GCW:
                switch(period) {
                    case LEADERBOARD_PERIOD_CURRENT:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getObjIdArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getObjIdArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_ID);
                        }
                        break;
                    case LEADERBOARD_PERIOD_PREVIOUS:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getObjIdArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getObjIdArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_ID);
                        }
                        break;
                    case LEADERBOARD_PERIOD_2WEEKSAGO:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getObjIdArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getObjIdArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_REBEL_ID);
                        }
                        break;
                }
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @param faction = FACTION_HASH_IMPERIAL or FACTION_HASH_REBEL
     * @param period = LEADERBOARD_PERIOD_CURRENT, LEADERBOARD_PERIOD_PREVIOUS, or LEADERBOARD_PERIOD_2WEEKSAGO
     * @return int[] guilds ranked for the given period given the type and faction
     * Entities are in rank order from 1st place to 10th place
     */
    public static int[] getRankedGuildsForPeriod(int type, int faction, int period) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                break;
            case LEADERBOARD_TYPE_GCW:
                switch(period) {
                    case LEADERBOARD_PERIOD_CURRENT:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_ID);
                        }
                        break;
                    case LEADERBOARD_PERIOD_PREVIOUS:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_ID);
                        }
                        break;
                    case LEADERBOARD_PERIOD_2WEEKSAGO:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_REBEL_ID);
                        }
                        break;
                }
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @param faction = FACTION_HASH_IMPERIAL or FACTION_HASH_REBEL
     * @param period = LEADERBOARD_PERIOD_CURRENT, LEADERBOARD_PERIOD_PREVIOUS, or LEADERBOARD_PERIOD_2WEEKSAGO
     * @return int[] cities ranked for the given period given the type and faction
     * Entities are in rank order from 1st place to 10th place
     */
    public static int[] getRankedCitiesForPeriod(int type, int faction, int period) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                break;
            case LEADERBOARD_TYPE_GCW:
                switch (period) {
                    case LEADERBOARD_PERIOD_CURRENT:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_ID);
                        }
                        break;
                    case LEADERBOARD_PERIOD_PREVIOUS:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_ID);
                        }
                        break;
                    case LEADERBOARD_PERIOD_2WEEKSAGO:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_IMPERIAL_ID);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getIntArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_REBEL_ID);
                        }
                        break;
                }
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @param faction = FACTION_HASH_IMPERIAL or FACTION_HASH_REBEL
     * @param period = LEADERBOARD_PERIOD_CURRENT, LEADERBOARD_PERIOD_PREVIOUS, or LEADERBOARD_PERIOD_2WEEKSAGO
     * @return float[] of scores of ranked players for the current period given the type and faction
     * Scores are in rank order from 1st to 25th to match the value pair of the getRankedPlayersForPeriod() method
     */
    public static float[] getRankedPlayerScoresForPeriod(int type, int faction, int period) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                break;
            case LEADERBOARD_TYPE_GCW:
                switch(period) {
                    case LEADERBOARD_PERIOD_CURRENT:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_SCORE);
                        }
                        break;
                    case LEADERBOARD_PERIOD_PREVIOUS:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_SCORE);
                        }
                        break;
                    case LEADERBOARD_PERIOD_2WEEKSAGO:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_REBEL_SCORE);
                        }
                        break;
                }
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @param faction = FACTION_HASH_IMPERIAL or FACTION_HASH_REBEL
     * @param period = LEADERBOARD_PERIOD_CURRENT, LEADERBOARD_PERIOD_PREVIOUS, or LEADERBOARD_PERIOD_2WEEKSAGO
     * @return float[] of scores of ranked guilds for the current period given the type and faction
     * Scores are in rank order from 1st to 10th to match the value pair of the getRankedGuildsForPeriod() method
     */
    public static float[] getRankedGuildScoresForPeriod(int type, int faction, int period) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                break;
            case LEADERBOARD_TYPE_GCW:
                switch(period) {
                    case LEADERBOARD_PERIOD_CURRENT:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_SCORE);
                        }
                        break;
                    case LEADERBOARD_PERIOD_PREVIOUS:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_SCORE);
                        }
                        break;
                    case LEADERBOARD_PERIOD_2WEEKSAGO:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_REBEL_SCORE);
                        }
                        break;
                }
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @param faction = FACTION_HASH_IMPERIAL or FACTION_HASH_REBEL
     * @param period = LEADERBOARD_PERIOD_CURRENT, LEADERBOARD_PERIOD_PREVIOUS, or LEADERBOARD_PERIOD_2WEEKSAGO
     * @return float[] of scores of ranked cities for the current period given the type and faction
     * Scores are in rank order from 1st to 10th to match the value pair of the getRankedCitiesForPeriod() method
     */
    public static float[] getRankedCityScoresForPeriod(int type, int faction, int period) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                break;
            case LEADERBOARD_TYPE_GCW:
                switch (period) {
                    case LEADERBOARD_PERIOD_CURRENT:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_SCORE);
                        }
                        break;
                    case LEADERBOARD_PERIOD_PREVIOUS:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_SCORE);
                        }
                        break;
                    case LEADERBOARD_PERIOD_2WEEKSAGO:
                        if (faction == FACTION_HASH_IMPERIAL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_IMPERIAL_SCORE);
                        } else if (faction == FACTION_HASH_REBEL) {
                            return getFloatArrayObjVar(masterObject, OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_REBEL_SCORE);
                        }
                        break;
                }
        }
        return null;
    }

    /**
     * @return a HashMap of the current Player Winners and their Scores for the given faction
     */
    public static HashMap<obj_id, Float> getGcwLeaderboardCurrentWinnersPlayerData(int faction) {
        HashMap<obj_id, Float> data = new HashMap<>();
        final obj_id[] players = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, faction, LEADERBOARD_PERIOD_PREVIOUS);
        final float[] scores = getRankedPlayerScoresForPeriod(LEADERBOARD_TYPE_GCW, faction, LEADERBOARD_PERIOD_PREVIOUS);
        if (scores != null && players != null) {
            for (int i = 0; i < 11; i++) {
                data.put(players[i], scores[i]);
            }
            return data;
        }
        return null;
    }

    /**
     * @param isCity true if asking for city data, false if asking for guild Data
     * @return a HashMap of the current Entity Winners and their Scores for the given faction and entity type
     */
    public static HashMap<Integer, Float> getGcwLeaderboardCurrentWinnersEntityData(int faction, boolean isCity) {
        HashMap<Integer, Float> data = new HashMap<>();
        final int[] entities = isCity ? getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, faction, LEADERBOARD_PERIOD_PREVIOUS) :
                getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, faction, LEADERBOARD_PERIOD_PREVIOUS);
        final float[] scores = isCity ? getRankedCityScoresForPeriod(LEADERBOARD_TYPE_GCW, faction, LEADERBOARD_PERIOD_PREVIOUS) :
                getRankedGuildScoresForPeriod(LEADERBOARD_TYPE_GCW, faction, LEADERBOARD_PERIOD_PREVIOUS);
        if (scores != null && entities != null) {
            for (int i = 0; i < 6; i++) {
                data.put(entities[i], scores[i]);
            }
            return data;
        }
        return null;
    }

    /**
     * @param type = LEADERBOARD_TYPE_FISHING or LEADERBOARD_TYPE_GCW
     * @return true if the given player is in the current participant list for the given type
     */
    public static boolean isPlayerInCurrentPeriodParticipantList(obj_id player, int type) throws InterruptedException {
        switch (type) {
            case LEADERBOARD_TYPE_FISHING:
                obj_id[] ids = objvar_mangle.getMangledObjIdArrayObjVar(getMasterLeaderboardObject(), OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+FISHING);
                if(ids != null) {
                    return Arrays.asList(ids).contains(player);
                } else {
                    return false;
                }
            case LEADERBOARD_TYPE_GCW:
                obj_id[] idsx = objvar_mangle.getMangledObjIdArrayObjVar(getMasterLeaderboardObject(), OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+GCW);
                if(idsx != null) {
                    return Arrays.asList(idsx).contains(player);
                } else {
                    return false;
                }
        }
        return false;
    }

    /**
     * @param player obj_id of player to check
     * @return true if the given player is in the current ranked list
     */
    public static boolean isPlayerInCurrentRankedList(obj_id player) {
        final obj_id[] ranked = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, pvpGetAlignedFaction(player), LEADERBOARD_PERIOD_CURRENT);
        if(ranked != null) {
            return Arrays.stream(ranked).anyMatch(x-> x == player);
        } else {
            return false;
        }
    }

    /**
     * @param entityId guild/city ID
     * @param isCity true if city, false if guild
     * @return true if the given entity is in the current ranked list
     */
    public static boolean isEntityInCurrentRankedList(int entityId, boolean isCity) {
        int[] ranked;
        if(isCity) {
            ranked = getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, cityGetFaction(entityId), LEADERBOARD_PERIOD_CURRENT);
        } else {
            ranked = getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, guildGetCurrentFaction(entityId), LEADERBOARD_PERIOD_CURRENT);
        }
        if(ranked != null) {
            return Arrays.stream(ranked).anyMatch(x-> x == entityId);
        } else {
            return false;
        }
    }

    /**
     * @return true if the player is currently a player/guild/city Reigning Champion
     */
    public static boolean isPlayerGcwLeaderboardReigningChampion(obj_id player) {
        final long impSlot = getCollectionSlotValue(player, "gcw_leaderboard_imperial_current_winner");
        final long rebSlot = getCollectionSlotValue(player, "gcw_leaderboard_rebel_current_winner");
        return (impSlot > 0 || rebSlot > 0);
    }

    /**
     * Handler for players who win the GCW Leaderboard Players Category
     * Winners = Top 10 Players
     */
    public static void handleGcwLeaderboardPlayerWinners(int faction, obj_id[] players, float[] scores) throws InterruptedException {
        if(players == null || scores == null || players.length < 1 || scores.length < 1) {
            return;
        }
        final String factionFormatted = factions.getFactionNameByHashCode(faction);
        final String badgeString = toLower(factionFormatted);
        // Craft Mail Message
        String message = "Congratulations.\n\n" +
                getPlayerFullName(players[0])+" has won the GCW "+factionFormatted+" leaderboard for the weekly period ending " +
                getCalendarTimeStringLocal(getCurrentLeaderboardPeriodEndTime())+".\n\n";
        StringBuilder winners = new StringBuilder();
        for (int i = 0; i < players.length; i++) {
            if(isIdValid(players[i])) { // so we don't add null entries popped from leaderboard object array copy
                winners.append(i + 1).append(". ").append(getPlayerFullName(players[i])).append(", ").append(scores[i]).append("%\n");
            }
        }
        String finalMessage = message + winners;

        // Handle First Place
        if(!badge.hasBadge(players[0], "bdg_gcw_leaderboard_" + badgeString + "_winner_rank1")) {
            badge.grantBadge(players[0], "bdg_gcw_leaderboard_" + badgeString + "_winner_rank1");
        }
        for (obj_id player : players) {
            // Grant Badges for Record X times won
            if (!hasObjVar(player, OBJVAR_LEADERBOARD_PLAYER_WIN_COUNT + badgeString)) {
                // first time win
                setObjVar(player, OBJVAR_LEADERBOARD_PLAYER_WIN_COUNT + badgeString, 1);
                badge.grantBadge(player, "bdg_gcw_leaderboard_"+badgeString+"_winner_1x");
            } else {
                // grant a badge if they've reached a win count that has a badge
                final int winCount = getIntObjVar(player, OBJVAR_LEADERBOARD_PLAYER_WIN_COUNT + badgeString) +1;
                if(Arrays.stream(WIN_COUNTS_WITH_BADGES).anyMatch(i-> i == winCount)) {
                    badge.grantBadge(player, "bdg_gcw_leaderboard_"+badgeString+"_winner_"+winCount+"x");
                }
                setObjVar(player, OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + badgeString, winCount);
            }
            // Handle Winner Flags & Mail Message
            // All player winners get War Planner, Reigning Champion, & Email
            modifyCollectionSlotValue(player, badgeString+"_gcw_war_planner", 1);
            modifyCollectionSlotValue(player, "gcw_leaderboard_"+ badgeString +"_current_winner", 1);
            chatSendPersistentMessage("gcw leaderboard", player, "Winner - Player - "+factionFormatted, finalMessage, null);
            LOG("gcw_leaderboard_winners", getPlayerName(player)+" ("+player+") was added to "+factionFormatted+" player winners list for period "+getCurrentLeaderboardPeriod());
        }
        announceGcwLeaderboardWinnersToDiscord(1, faction, players, null, scores);
    }

    /**
     * Handler for guilds who win the GCW Leaderboard
     * Winners = Top 5 Guilds
     */
    public static void handleGcwLeaderboardGuildWinners(int faction, int[] guildIds, float[] scores) throws InterruptedException {
        if(guildIds == null || scores == null || guildIds.length < 1 || scores.length < 1) {
            return;
        }
        final String factionFormatted = factions.getFactionNameByHashCode(faction);
        final String badgeString = toLower(factionFormatted);
        // Craft Mail Message
        String message = "Congratulations.\n\n" +
                guildGetName(guildIds[0])+" ("+guildGetAbbrev(guildIds[0])+") has won the GCW "+factionFormatted+" leaderboard for the weekly period ending " +
                getCalendarTimeStringLocal(getCurrentLeaderboardPeriodEndTime())+".\n\n";
        StringBuilder winners = new StringBuilder();
        for (int i = 0; i < guildIds.length; i++) {
            if(isIdValid(guildGetLeader(guildIds[i]))) { // so we don't add null entries popped from leaderboard object array copy
                winners.append(i + 1).append(". ").append(guildGetName(guildIds[i])).append(" (").append(guildGetAbbrev(guildIds[i])).append("), ").append(scores[i]).append("%\n");
            }
        }
        String finalMessage = message + winners;

        // Handle First Place
        obj_id[] firstPlaceIds = guildGetMemberIds(guildIds[0]);
        for (obj_id fp : firstPlaceIds) {
            if (!badge.hasBadge(fp, "bdg_gcw_leaderboard_" + badgeString + "_guild_winner_rank1")) {
                badge.grantBadge(fp, "bdg_gcw_leaderboard_" + badgeString + "_guild_winner_rank1");
                LOG("gcw_leaderboard_winners", getPlayerName(fp)+"("+fp+") was granted badge bdg_gcw_leaderboard_"+badgeString+"_guild_winner_rank1");
            }
        }
        for (int i : guildIds) {
            obj_id[] players = guildGetMemberIds(i);
            for (obj_id player : players) {
                // Grant Badges for Record X times won
                if (!hasObjVar(player, OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + badgeString)) {
                    // first time win
                    setObjVar(player, OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + badgeString, 1);
                    badge.grantBadge(player, "bdg_gcw_leaderboard_"+badgeString+"_guild_winner_1x");
                } else {
                    // grant a badge if they've reached a win count that has a badge
                    final int winCount = getIntObjVar(player, OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + badgeString) +1;
                    if(Arrays.stream(WIN_COUNTS_WITH_BADGES).anyMatch(x-> x == winCount)) {
                        badge.grantBadge(player, "bdg_gcw_leaderboard_"+badgeString+"_guild_winner_"+winCount+"x");
                    }
                    setObjVar(player, OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + badgeString, winCount);
                }
                // Handle Winner Flags & Mail Message
                // All members of the guild get Reigning Champion & email
                modifyCollectionSlotValue(player, "gcw_leaderboard_"+ badgeString +"_current_winner", 1);
                chatSendPersistentMessage("gcw leaderboard", player, "Winner - Guild - "+factionFormatted, finalMessage, null);
            }
            // Only guild leader gets War Planner Slot
            modifyCollectionSlotValue(guildGetLeader(i), badgeString+"_gcw_war_planner", 1);
            LOG("gcw_leaderboard_winners", guildGetAbbrev(i)+" ("+i+") was added to "+factionFormatted+" guild winners list for period "+getCurrentLeaderboardPeriod());
        }
        announceGcwLeaderboardWinnersToDiscord(2, faction, null, guildIds, scores);
    }

    /**
     * Handler for cities who win the GCW Leaderboard
     * Winners = Top 5 Cities
     */
    public static void handleGcwLeaderboardCityWinners(int faction, int[] cityIds, float[] scores) throws InterruptedException {
        if(cityIds == null || scores == null || cityIds.length < 1 || scores.length < 1) {
            return;
        }
        final String factionFormatted = factions.getFactionNameByHashCode(faction);
        final String badgeString = toLower(factionFormatted);
        // Craft Mail Message
        String message = "Congratulations.\n\n" +
                cityGetName(cityIds[0])+" has won the GCW "+factionFormatted+" leaderboard for the weekly period ending " +
                getCalendarTimeStringLocal(getCurrentLeaderboardPeriodEndTime())+".\n\n";
        StringBuilder winners = new StringBuilder();
        for (int i = 0; i < cityIds.length; i++) {
            if(isIdValid(cityGetLeader(cityIds[i]))) { // so we don't add null entries popped from leaderboard object array copy
                winners.append(i + 1).append(". ").append(cityGetName(cityIds[i])).append(", ").append(scores[i]).append("%\n");
            }
        }
        String finalMessage = message + winners;

        // Handle First Place
        obj_id[] firstPlaceIds = cityGetCitizenIds(cityIds[0]);
        for (obj_id fp : firstPlaceIds) {
            if (!badge.hasBadge(fp, "bdg_gcw_leaderboard_" + badgeString + "_city_winner_rank1")) {
                badge.grantBadge(fp, "bdg_gcw_leaderboard_" + badgeString + "_city_winner_rank1");
                LOG("gcw_leaderboard_winners", getPlayerName(fp)+"("+fp+") was granted badge bdg_gcw_leaderboard_"+badgeString+"_city_winner_rank1");
            }
        }
        for (int i : cityIds) {
            obj_id[] players = cityGetCitizenIds(i);
            for (obj_id player : players) {
                // Grant Badges for Record X times won
                if (!hasObjVar(player, OBJVAR_LEADERBOARD_CITY_WIN_COUNT + badgeString)) {
                    // first time win
                    setObjVar(player, OBJVAR_LEADERBOARD_CITY_WIN_COUNT + badgeString, 1);
                    badge.grantBadge(player, "bdg_gcw_leaderboard_"+badgeString+"_city_winner_1x");
                    LOG("gcw_leaderboard_winners", getPlayerName(player)+"("+player+") was granted badge bdg_gcw_leaderboard_"+badgeString+"_city_winner_1x");
                } else {
                    // grant a badge if they've reached a win count that has a badge
                    final int winCount = getIntObjVar(player, OBJVAR_LEADERBOARD_CITY_WIN_COUNT + badgeString) +1;
                    if(Arrays.stream(WIN_COUNTS_WITH_BADGES).anyMatch(x-> x == winCount)) {
                        badge.grantBadge(player, "bdg_gcw_leaderboard_"+badgeString+"_city_winner_"+winCount+"x");
                        LOG("gcw_leaderboard_winners", getPlayerName(player)+"("+player+") was granted badge bdg_gcw_leaderboard_"+badgeString+"_city_winner_"+winCount+"x");
                    }
                    setObjVar(player, OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + badgeString, winCount);
                }
                // Handle Winner Flags & Mail Message
                // All members of the city get Reigning Champion & email
                modifyCollectionSlotValue(player, "gcw_leaderboard_"+ badgeString +"_current_winner", 1);
                chatSendPersistentMessage("gcw leaderboard", player, "Winner - City - "+factionFormatted, finalMessage, null);
            }
            // Only city mayor gets War Planner Slot
            modifyCollectionSlotValue(guildGetLeader(i), badgeString+"_gcw_war_planner", 1);
            LOG("gcw_leaderboard_winners", cityGetName(i)+" ("+i+") was added to "+factionFormatted+" city winners list for period "+getCurrentLeaderboardPeriod());
        }
        announceGcwLeaderboardWinnersToDiscord(3, faction, null, cityIds, scores);
    }

    /**
     * Sets the score data for the current period after it has been rank_pair'ed (players)
     */
    public static void setCurrentPeriodScoreData(int type, obj_id[] entity, float[] scores) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch (type) {
            case 1: // Player Imperials
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_ID, entity);
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_SCORE, scores);
                break;
            case 2: // Player Rebels
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_ID, entity);
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_SCORE, scores);
                break;
        }
        debugMsg("setCurrentPeriodScoreData() called for type "+type+" and entities "+Arrays.toString(entity)+" and scores "+Arrays.toString(scores));
    }

    /**
     * Sets the score data for the current period after it has been rank_pair'ed (guild/city)
     */
    public static void setCurrentPeriodScoreData(int type, int[] entity, float[] scores) {
        final obj_id masterObject = getMasterLeaderboardObject();
        switch(type) {
            case 3: // Guild Imperials
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_ID, entity);
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_SCORE, scores);
                break;
            case 4: // Guild Rebels
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_ID, entity);
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_SCORE, scores);
                break;
            case 5: // City Imperials
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_ID, entity);
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_SCORE, scores);
                break;
            case 6: // City Rebels
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_ID, entity);
                setObjVar(masterObject, OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_SCORE, scores);
                break;
        }
        debugMsg("setCurrentPeriodScoreData() called for type "+type+" and entities "+Arrays.toString(entity)+" and scores "+Arrays.toString(scores));
    }

    /**
     * This is the method called when a player selects the "GCW Leaderboards..." radial menu options
     * to actually see a specific leaderboard on one of the objects that allow a player to request
     * them (War Terminal, War IntelPad, War Planning Table)
     *
     * This does not include the City/Guild Extended History Data as that is handled in the separate
     * method renderGcwLeaderboardEntityHistory()
     *
     * This method and the subsequent methods that access and/or store data within it identify the request
     * from a "render type" which is just an integer based on the index of the radial menu option chosen:
     * 1 - Player - Rebel - Current Week
     * 2 - Player - Imperial - Current Week
     * 3 - Guild - Rebel - Current Week
     * 4 - Guild - Imperial - Current Week
     * 5 - City - Rebel - Current Week
     * 6 - City - Imperial - Current Week
     * 7 - Player - Rebel - Previous Week
     * 8 - Player - Imperial - Previous Week
     * 9 - Guild - Rebel - Previous Week
     * 10 - Guild - Imperial - Previous Week
     * 11 - City - Rebel - Previous Week
     * 12 - City - Imperial - Previous Week
     * 13 - Player - Rebel - 2 Weeks Ago
     * 14 - Player - Imperial - 2 Weeks Ago
     * 15 - Guild - Rebel - 2 Weeks Ago
     * 16 - Guild - Imperial - 2 Weeks Ago
     * 17 - City - Rebel - 2 Weeks Ago
     * 18 - City - Imperial - 2 Weeks Ago
     */
    public static void renderGcwLeaderboardDataToPlayer(obj_id player, obj_id requestingObject, int renderType) throws InterruptedException {

        // Determine if a period update hasn't occurred since our last request for data, in which case use the cached data
        // that has been stored on the requesting object instead of pulling it all again to reduce the total load
        int lastUpdatedTime = getCurrentLeaderboardPeriodLastUpdated();
        boolean usedCachedData = false;
        if(hasObjVar(requestingObject, OBJVAR_LEADERBOARD_LAST_DATA_PULL_TIME)) {
            final int pullTime = getIntObjVar(requestingObject, OBJVAR_LEADERBOARD_LAST_DATA_PULL_TIME);
            if(pullTime == lastUpdatedTime ||
                    // always used cached data for the previous/2 weeks ago boards unless a reset has happened since we logged in
                    (renderType > 6 && getCurrentLeaderboardPeriodStartTime() < getPlayerLastLoginTime(player))) {
                usedCachedData = true;
                lastUpdatedTime = pullTime;
            }
        }
        setObjVar(requestingObject, OBJVAR_LEADERBOARD_LAST_DATA_PULL_TIME, getCurrentLeaderboardPeriodLastUpdated());
        final String topMessage = "Last Updated: "+getCalendarTimeStringLocal(lastUpdatedTime)+"\n\n";
        final int factionHash = renderType % 2 == 0 ? FACTION_HASH_IMPERIAL : FACTION_HASH_REBEL; // even renderType vs odd to determine faction
        final int weekType = renderType < 7 ? LEADERBOARD_PERIOD_CURRENT : (renderType < 13) ? LEADERBOARD_PERIOD_PREVIOUS : LEADERBOARD_PERIOD_2WEEKSAGO;
        final String weekName = renderType < 7 ? "(Current Week)" : (renderType < 13) ? "(Previous Week)" : "(2 Weeks Ago)";
        final String factionName = factions.getFactionNameByHashCode(factionHash);
        final int guildId = getGuildId(player);
        final int cityId = getCitizenOfCityId(player);
        // Handle logic relative to what type of entity we are fetching data for Player/Guild/City
        String entityName = getPlayerFullName(player);
        String entityType = "Player";
        switch (renderType) {
            case 3:
            case 4:
            case 9:
            case 10:
            case 15:
            case 16:
                entityType = "Guild";
                if(guildId > 0) {
                    entityName = guildGetName(guildId) + " ("+guildGetAbbrev(guildId)+")";
                } else {
                    entityName = "None";
                }
                break;
            case 5:
            case 6:
            case 11:
            case 12:
            case 17:
            case 18:
                entityType = "City";
                if(cityId > 0) {
                    entityName = cityGetName(cityId);
                } else {
                    entityName = "None";
                }
                break;
        }

        float score = -1.0f;
        String[][] data;
        String finalMessage;
        // Get Local Score (this person/entity's score relative to the ranked players)
        // unless requesting data for guild/city and you aren't in a guild/city, respectively
        if(entityType.contains("Player") || (entityType.contains("Guild") && guildId > 0) || (entityType.contains("City") && cityId > 0)) {
            if(usedCachedData && hasCachedLocalScore(requestingObject, renderType)) { // if we should use cached data & we have it
                score = getCachedLocalScore(requestingObject, renderType);
            } else { // actually pull the data and cache it
                if(entityType.contains("Player")) {
                    score = getPlayerRelativeScoreToTransposedLeaderboardScores(player, factionHash, weekType);
                } else if (entityType.contains("Guild")) {
                    score = getEntityRelativeScoreToTransposedLeaderboardScores(guildId, factionHash, weekType, false);
                } else {
                    score = getEntityRelativeScoreToTransposedLeaderboardScores(cityId, factionHash, weekType, true);
                }
                cacheLocalScore(requestingObject, renderType, score);
            }
        }

        // Get Score Data (actual ranked entities on the board)
        if(usedCachedData && hasCachedScoreData(requestingObject, renderType)) { // if we should use cached data & we have it
            data = getCachedScoreData(requestingObject, renderType);
        } else { // actually pull the data and cache it
            data = getFormattedGcwLeaderboardData(renderType, false);
            if(data != null) {
                cacheScoreData(requestingObject, renderType, data);
            }
        }
        if(data == null || data.length < 1) {
            sendSystemMessageTestingOnly(player, "There is no score data available for the requested GCW Leaderboard. Please try again later.");
            return;
        }
        String prompt = "GCW "+factionName +" "+ entityType+" Leaderboard "+weekName;
        if(score >= 0) {
            String scoreInfo = ". Rank Value for "+entityName+" = "+score+"%"; // append local score if we have one
            finalMessage = topMessage + prompt + scoreInfo;
        } else {
            finalMessage = topMessage + prompt;
        }
        sui.table(player, player, sui.OK_ONLY, GCW_LEADERBOARD_TITLE, "noHandler", finalMessage, GCW_LEADERBOARD_COL_NAMES, GCW_LEADERBOARD_COL_TYPES, data, true, true);
    }

    /**
     * Handles pulling ranked entities and their respective scores and compiles it
     * in a format suitable for rendering in a SUI Table
     * @return String[][] for sui table with ranked entities and their scores
     */
    public static String[][] getFormattedGcwLeaderboardData(int renderType, boolean debug) {

        final int factionHash = renderType % 2 == 0 ? FACTION_HASH_IMPERIAL : FACTION_HASH_REBEL;
        final int weekType = renderType < 7 ? LEADERBOARD_PERIOD_CURRENT : (renderType < 13) ? LEADERBOARD_PERIOD_PREVIOUS : LEADERBOARD_PERIOD_2WEEKSAGO;
        obj_id[] players;
        int[] entities;
        float[] scores;

        switch (renderType) {
            // Players
            case 1:
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
                players = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, factionHash, weekType);
                scores = getRankedPlayerScoresForPeriod(LEADERBOARD_TYPE_GCW, factionHash, weekType);
                if (scores != null && players != null) {
                    final float[] transposedScores = transposeClusterScoreDataToLocalLeaderboardScoreData(scores);
                    String[][] data = new String[players.length][GCW_LEADERBOARD_COL_NAMES.length];
                    for (int i = 0; i < players.length; i++) {
                        data[i][0] = String.valueOf(i+1);
                        data[i][1] = debug ? getPlayerFullName(players[i])+" ("+players[i]+")" : getPlayerFullName(players[i]);
                        data[i][2] = debug ? "Cluster: "+scores[i]+" / Transposed: "+transposedScores[i]+" %" : transposedScores[i] + "%";
                    }
                    return data;
                }
                break;
            // Guilds
            case 3:
            case 4:
            case 9:
            case 10:
            case 15:
            case 16:
                entities = getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, factionHash, weekType);
                scores = getRankedGuildScoresForPeriod(LEADERBOARD_TYPE_GCW, factionHash, weekType);
                if (scores != null && entities != null) {
                    final float[] transposedScores = transposeClusterScoreDataToLocalLeaderboardScoreData(scores);
                    String[][] data = new String[entities.length][GCW_LEADERBOARD_COL_NAMES.length];
                    for (int i = 0; i < entities.length; i++) {
                        data[i][0] = String.valueOf(i+1);
                        data[i][1] = debug ? guildGetName(entities[i])+" ("+entities[i]+")" : guildGetName(entities[i]);
                        data[i][2] = debug ? "Cluster: "+scores[i]+"% | Transposed: "+transposedScores[i]+"%" : transposedScores[i] + "%";
                    }
                    return data;
                }
                break;
            // Cities
            case 5:
            case 6:
            case 11:
            case 12:
            case 17:
            case 18:
                entities = getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, factionHash, weekType);
                scores = getRankedCityScoresForPeriod(LEADERBOARD_TYPE_GCW, factionHash, weekType);
                if (scores != null && entities != null) {
                    final float[] transposedScores = transposeClusterScoreDataToLocalLeaderboardScoreData(scores);
                    String[][] data = new String[entities.length][GCW_LEADERBOARD_COL_NAMES.length];
                    for (int i = 0; i < entities.length; i++) {
                        data[i][0] = String.valueOf(i+1);
                        data[i][1] = debug ? cityGetName(entities[i])+" ("+entities[i]+")" : cityGetName(entities[i]);
                        data[i][2] = debug ? "Cluster: "+scores[i]+"% | Transposed: "+transposedScores[i]+"%" : transposedScores[i] + "%";
                    }
                    return data;
                }
                break;
        }
        return null;
    }

    /**
     * Because the leaderboard does not display the % score of the ranked player/entity
     * relative to the entire cluster but rather relative to the other ranked entities,
     * i.e. the top score cluster-wide might only be 1.58%, but relative to the other
     * top-ranked entities, it might be 25% of their collective score, so we have to
     * transpose this data to display the scores relative to the other ranked scores
     * as opposed to displaying cluster-wide data.
     * @return float[] of transposed Cluster to Local scores
     */
    public static float[] transposeClusterScoreDataToLocalLeaderboardScoreData(float[] scoreData) {
        final double total = IntStream.range(0, scoreData.length).mapToDouble(i -> scoreData[i]).sum();
        float[] transposedData = new float[scoreData.length];
        for (int i = 0; i < scoreData.length; i++) {
            transposedData[i] = (float)(scoreData[i] / total) * 100;
        }
        debugMsg("transposeClusterScoreDataToLocalLeaderboardScoreData() took in scores param of "+Arrays.toString(scoreData));
        debugMsg("transposeClusterScoreDataToLocalLeaderboardScoreData() returned score sum "+total+" and transposed data: "+Arrays.toString(transposedData));
        return transposedData;
    }

    /**
     * Returns the % score of a player relative to the transposed local leaderboard
     * scores which shows how far the player is, in relation to the other ranked
     * players, from reaching a ranked position on the leaderboard.
     * @return float score relative to the transposed scores
     */
    public static float getPlayerRelativeScoreToTransposedLeaderboardScores(obj_id player, int faction, int period) {
        final float[] scores = getRankedPlayerScoresForPeriod(LEADERBOARD_TYPE_GCW, faction, period);
        final obj_id[] players = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, faction, period);
        if(players != null && scores != null) { // don't calculate a relative score for someone already ranked because that wouldn't make sense
            if (Arrays.stream(players).anyMatch(x-> x== player)) {
                return scores[Arrays.asList(players).indexOf(player)];
            }
        }
        if(scores != null) {
            double total = IntStream.range(0, scores.length).mapToDouble(i -> scores[i]).sum();
            float score = 0.0f;
            if(faction == FACTION_HASH_IMPERIAL) {
                score = getFloatObjVar(player, OBJVAR_PLAYER_IMPERIAL_CURRENT_TEMP_SCORE);
            } else if (faction == FACTION_HASH_REBEL) {
                score = getFloatObjVar(player, OBJVAR_PLAYER_REBEL_CURRENT_TEMP_SCORE);
            }
            if (score > 0) {
                float toReturn = (float)(score / (score + total)) * 100;
                debugMsg("getPlayerRelativeScoreToTransposedLeaderboardScores() searched for scores from faction "+faction+" and period "+period+" for score data: "+Arrays.toString(scores));
                debugMsg("getPlayerRelativeScoreToTransposedLeaderboardScores() returned transposed local score: "+toReturn);
                return toReturn;
            }
        }
        return 0.0f;
    }

    /**
     * See: getPlayerRelativeScoreToTransposedLeaderboardScores but for Guilds/Cities
     */
    public static float getEntityRelativeScoreToTransposedLeaderboardScores(int entity, int faction, int period, boolean isCity) {
        final float[] scores = isCity ? getRankedCityScoresForPeriod(LEADERBOARD_TYPE_GCW, faction, period) :
                getRankedGuildScoresForPeriod(LEADERBOARD_TYPE_GCW, faction, period);
        final int[] entities = isCity? getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, faction, period) :
                getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, faction, period);
        if(entities != null && scores != null) { // don't calculate a relative score for someone already ranked because that wouldn't make sense
            if (Arrays.stream(entities).anyMatch(x-> x== entity)) {
                return scores[Arrays.binarySearch(entities, entity)];
            }
        }
        if(scores != null) {
            final double total = IntStream.range(0, scores.length).mapToDouble(i -> scores[i]).sum();
            final obj_id masterObject = isCity ? getMasterCityObject() : getMasterGuildObject();
            String var;
            float score = 0.0f;
            if(faction == FACTION_HASH_IMPERIAL) {
                var = isCity ? OBJVAR_CITY_IMPERIAL_CURRENT_TEMP_SCORE : OBJVAR_GUILD_IMPERIAL_CURRENT_TEMP_SCORE;
                score = getFloatObjVar(masterObject, var+"_"+entity);
            } else if (faction == FACTION_HASH_REBEL) {
                var = isCity ? OBJVAR_CITY_REBEL_CURRENT_TEMP_SCORE : OBJVAR_GUILD_REBEL_CURRENT_TEMP_SCORE;
                score = getFloatObjVar(masterObject, var+"_"+entity);
            }
            if (score > 0) {
                float toReturn = (float)(score / (score + total)) * 100;
                debugMsg("getEntityRelativeScoreToTransposedLeaderboardScores() searched for scores from faction "+faction+" and period "+period+" for score data: "+Arrays.toString(scores));
                debugMsg("getEntityRelativeScoreToTransposedLeaderboardScores() returned transposed local score: "+toReturn);
                return toReturn;
            }
        }
        return 0;
    }

    /**
     * Used to render the Guild/City GCW Leaderboard Score Archive SUI Tables
     * See packGcwEntityHistoryData() for information on how this works
     */
    public static void renderGcwLeaderboardEntityHistory(obj_id player, obj_id requestingObject, boolean isCity) throws InterruptedException {
        final obj_id masterObject = isCity ? getMasterCityObject() : getMasterGuildObject();
        final int entityId = isCity ? getCitizenOfCityId(player) : getGuildId(player);
        final int renderType = isCity ? 20 : 19;
        final String type = isCity ? "City" : "Guild";
        final String ObjVar = OBJVAR_DATA_GCW_ENTITY_HISTORY + "_" + entityId;
        if(entityId < 1) {
            return;
        }
        final String[] packedData = getStringArrayObjVar(masterObject, ObjVar);
        if(packedData == null || packedData.length == 0) {
            sendSystemMessageTestingOnly(player, "There is no GCW Leaderboard History available for your "+type+" at this time.");
            return;
        }
        boolean useCachedData = true;
        // always used cached data for accessing archives because they're old unless a reset has happened since we've logged in
        if(getCurrentLeaderboardPeriodStartTime() > getPlayerLastLoginTime(player)) {
            useCachedData = false;
        }
        String[][] data;
        if(useCachedData && hasCachedScoreData(requestingObject, renderType)) {
            data = getCachedScoreData(requestingObject, renderType);
        } else {
            data = new String[packedData.length][GCW_LEADERBOARD_ARCHIVE_COL_NAMES.length];
            String[] temp;
            for(int i = 0; i < packedData.length; i++) {
                temp = packedData[i].split(",");
                data[i][0] = getCalendarTimeStringLocal(Integer.parseInt(temp[0]));
                data[i][1] = factions.getFactionNameByHashCode(Integer.parseInt(temp[1]));
                data[i][2] = temp[2]+"%";
                data[i][3] = Integer.parseInt(temp[3]) == 0 ? "No" : "Yes";
                data[i][4] = Integer.parseInt(temp[4]) == 0 ? "No" : "Yes";
            }
            cacheScoreData(requestingObject, renderType, data);
        }
        final String name = isCity ? cityGetName(entityId) : guildGetName(entityId) + " ("+guildGetAbbrev(entityId)+")";
        final String message = "GCW Leaderboard History for "+type+": "+name;
        sui.table(player, player, sui.OK_ONLY, GCW_LEADERBOARD_TITLE, "noHandler", message, GCW_LEADERBOARD_ARCHIVE_COL_NAMES, GCW_LEADERBOARD_ARCHIVE_COL_TYPES, data, true, true);
    }

    /**
     * Used to pack the historical data of a guild/city. The data is stored as a String Array ObjVar
     * on the Master Guild/City objects for each guild/city. Guild/City disband methods will remove
     * this data automatically if a guild or city is disbanded/destroyed.
     *
     * Each object in the array is structured like this:
     * Period End Unix Timestamp , Faction Hash , Percentile Score , Winner (0/1) , Champion (0/1)
     * example: 1617508148,-615855020,4.49242,1,0
     * Which would read in the SUI something like: "April 3 2020 | Imperial | 0.49242% | Yes | No"
     *
     * Up to 6 months (26 periods) of historical data is stored. If you wanted to expand this, you
     * could but it would probably be better to store data older than that on an external source,
     * such as a website so you don't have to mangle the ObjVars.
     *
     * @param isCity true if it's a city, false if it's a guild (just to save having to make 2 methods)
     */
    public static void packGcwEntityHistoryData(int entityId, String[] data, boolean isCity) {
        final obj_id masterObject = isCity ? getMasterCityObject() : getMasterGuildObject();
        final String ObjVar = OBJVAR_DATA_GCW_ENTITY_HISTORY + "_" + entityId;
        String[] packedData = new String[1];
        StringBuilder buildData = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buildData.append(data[i]);
            if(i != data.length -1) {
                buildData.append(",");
            }
        }
        packedData[0] = buildData.toString();
        if(!hasObjVar(masterObject, ObjVar)) {
            setObjVar(masterObject, ObjVar, packedData);
            debugMsg("packGcwEntityHistoryData added new packed history for entity "+entityId+ " as: "+ Arrays.toString(packedData));
        } else {
            String[] temp = getStringArrayObjVar(masterObject, ObjVar);
            if(temp != null) {
                Vector<String> vData = new Vector<>(Arrays.asList(temp));
                if(vData.size() > 25) {
                    vData.remove(0);
                }
                vData.add(packedData[0]);
                String[] newData = new String[vData.size()];
                for(int i = 0; i < newData.length; i++) {
                    newData[i] = vData.get(i);
                }
                setObjVar(masterObject, ObjVar, newData);
                debugMsg("packGcwEntityHistoryData added new packed history for entity "+entityId+ " as: "+Arrays.toString(newData));
            }
        }
    }

    /**
     * Caches on the requesting object a tabulated local score
     */
    public static void cacheLocalScore(obj_id requestingObject, int renderType, float score) throws InterruptedException {
        debugMsg("cacheLocalScore cached score "+score+" on object "+requestingObject+" for renderType "+renderType);
        utils.setScriptVar(requestingObject, SCRIPTVAR_LEADERBOARD_LOCAL_SCORE_CACHED+"_"+renderType, score);
    }

    /**
     * @return true if a local score has been cached for the respective renderType
     */
    public static boolean hasCachedLocalScore(obj_id requestingObject, int renderType) throws InterruptedException {
        return utils.hasScriptVar(requestingObject, SCRIPTVAR_LEADERBOARD_LOCAL_SCORE_CACHED+"_"+renderType);
    }

    /**
     * @return float cached local score
     */
    public static float getCachedLocalScore(obj_id requestingObject, int renderType) throws InterruptedException {
        return utils.getFloatScriptVar(requestingObject, SCRIPTVAR_LEADERBOARD_LOCAL_SCORE_CACHED+"_"+renderType);
    }

    /**
     * Caches on the requesting object a complete ranked score data for the respective renderType
     */
    public static void cacheScoreData(obj_id requestingObject, int renderType, String[][] scoreData) throws InterruptedException {
        debugMsg("cacheScoreData cached score data on object "+requestingObject+" for renderType "+renderType+" with data: "+Arrays.toString(scoreData));
        utils.setScriptVar(requestingObject, SCRIPTVAR_LEADERBOARD_SCORE_DATA_CACHED+"_"+renderType, scoreData);
    }

    /**
     * @return true if score data has been cached for the respective renderType
     */
    public static boolean hasCachedScoreData(obj_id requestingObject, int renderType) throws InterruptedException {
        return utils.hasScriptVar(requestingObject, SCRIPTVAR_LEADERBOARD_SCORE_DATA_CACHED+"_"+renderType);
    }

    /**
     * @return String[][] cached local score data
     */
    public static String[][] getCachedScoreData(obj_id requestingObject, int renderType) throws InterruptedException {
        return utils.getStringArrayArrayScriptVar(requestingObject, SCRIPTVAR_LEADERBOARD_SCORE_DATA_CACHED+"_"+renderType);
    }

    /**
     * Adds the temporary tracking score onto the player
     */
    public static void addPlayerGcwLeaderboardTempTrackingScore(obj_id player, int faction, float score) {
        if(faction == FACTION_HASH_IMPERIAL) {
            setObjVar(player, leaderboard.OBJVAR_PLAYER_IMPERIAL_CURRENT_TEMP_SCORE, score);
        } else if (faction == FACTION_HASH_REBEL) {
            setObjVar(player, leaderboard.OBJVAR_PLAYER_REBEL_CURRENT_TEMP_SCORE, score);
        }
    }

    /**
     * Adds the temporary tracking score for a guild/player to their
     * respective master objects
     */
    public static void addEntityGcwLeaderboardTempTrackingScore(int entity, int faction, float score, boolean isCity) {
        final obj_id masterObject = isCity ? getMasterCityObject() : getMasterGuildObject();
        if(faction == FACTION_HASH_IMPERIAL) {
            if(isCity) {
                setObjVar(masterObject, leaderboard.OBJVAR_CITY_IMPERIAL_CURRENT_TEMP_SCORE+"_"+entity, score);
            } else {
                setObjVar(masterObject, leaderboard.OBJVAR_GUILD_IMPERIAL_CURRENT_TEMP_SCORE+"_"+entity, score);
            }
        } else if (faction == FACTION_HASH_REBEL) {
            if(isCity) {
                setObjVar(masterObject, leaderboard.OBJVAR_CITY_REBEL_CURRENT_TEMP_SCORE+"_"+entity, score);
            } else {
                setObjVar(masterObject, leaderboard.OBJVAR_GUILD_REBEL_CURRENT_TEMP_SCORE+"_"+entity, score);
            }
        }
    }

    /**
     * Handles announcing Leaderboard Rank Changes to Discord, if a Web Hook URL has been set in config
     */
    public static void announceGcwLeaderboardRankChangeToDiscord(String message) {
        if(ANNOUNCEMENT_WEBHOOK_URL == null || ANNOUNCEMENT_WEBHOOK_URL.isEmpty()) {
            return;
        }
        DiscordWebhook hook = new DiscordWebhook(ANNOUNCEMENT_WEBHOOK_URL);
        hook.setContent(message);
        hook.execute();
    }

    /**
     * Handles announcing Leaderboard Winners to Discord, if a Web Hook URL has been set in config
     */
    public static void announceGcwLeaderboardWinnersToDiscord(int winnerType, int faction, obj_id[] players, int[] entities, float[] scores) {
        if(ANNOUNCEMENT_WEBHOOK_URL == null || ANNOUNCEMENT_WEBHOOK_URL.isEmpty()) {
            return;
        }
        DiscordWebhook hook = new DiscordWebhook(ANNOUNCEMENT_WEBHOOK_URL);
        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
        embed.setColor(faction == FACTION_HASH_IMPERIAL ? Color.BLUE : Color.RED);
        switch (winnerType) {
            case 1: // Players
                if(players != null && scores != null) {
                    embed.setTitle("GCW Leaderboard Winners - Players - "+factions.getFactionNameByHashCode(faction));
                    for(int i = 0; i < players.length; i++) {
                        if(isIdValid(players[i])) {
                            embed.addField(getPlayerFullName(players[i]), "Rank " + i+1 + " ("+scores[i]+"%)", false);
                        }
                    }
                }
                break;
            case 2: // Guilds
                if(entities != null && scores != null) {
                    embed.setTitle("GCW Leaderboard Winners - Guilds - "+factions.getFactionNameByHashCode(faction));
                    for(int i = 0; i < entities.length; i++) {
                        if(isIdValid(guildGetLeader(entities[i]))) {
                            embed.addField(guildGetName(entities[i]), "Rank " + i+1 + " ("+scores[i]+"%)", false);
                        }
                    }
                }
                break;
            case 3: // Cities
                if(entities != null && scores != null) {
                    embed.setTitle("GCW Leaderboard Winners - Cities - "+factions.getFactionNameByHashCode(faction));
                    for(int i = 0; i < entities.length; i++) {
                        if (isIdValid(cityGetLeader(entities[i]))) {
                            embed.addField(cityGetName(entities[i]), "Rank "+ i+1 +" ("+scores[i]+"%)", false);
                        }
                    }
                }
                break;
        }
        hook.addEmbed(embed);
        hook.execute();
    }

    /**
     * Removes tracking vars for participant lists after a period reset.
     * Note: the messageTo herein can be enabled to act as a script-trigger
     * equivalent for notifying players the period has reset. It is not
     * currently implemented given there was no need for it, but I left it
     * here as it's a great spot to implement a notification if needed.
     */
    public static void clearGcwLeaderboardTrackingVars(Set<obj_id> players) {
        if(players != null) {
            for (obj_id player : players) {
                removeObjVar(player, leaderboard.OBJVAR_PLAYER_TRACKING_PLAYER_ALREADY);
                removeObjVar(player, leaderboard.OBJVAR_PLAYER_TRACKING_GUILD_ALREADY);
                removeObjVar(player, leaderboard.OBJVAR_PLAYER_TRACKING_CITY_ALREADY);
                //messageTo(player, "OnGcwLeaderboardPeriodResetFinished", null, 30f, false);
            }
        }
    }

    /**
     * Handles removing the players who are no longer considered "Winners" (top 10)
     * because they haven't won in the most recent period.
     *
     * Note: This should run when a period reset occurs but before any new winners
     * are assigned (so you don't purge new winners after they won).
     */
    public static void handlePurgeReigningChampions() {
        final obj_id[] players1 = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_REBEL, LEADERBOARD_PERIOD_PREVIOUS);
        final obj_id[] players2 = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_IMPERIAL, LEADERBOARD_PERIOD_PREVIOUS);
        final int[] guild1 = getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_REBEL, LEADERBOARD_PERIOD_PREVIOUS);
        final int[] guild2 = getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_IMPERIAL, LEADERBOARD_PERIOD_PREVIOUS);
        final int[] city1 = getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_REBEL, LEADERBOARD_PERIOD_PREVIOUS);
        final int[] city2 = getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_IMPERIAL, LEADERBOARD_PERIOD_PREVIOUS);
        if(players1 != null) {
            purgeReigningChampions(players1[0]);
        }
        if(players2 != null) {
            purgeReigningChampions(players2[0]);
        }
        if(guild1 != null) {
            purgeReigningChampions(guildGetMemberIds(guild1[0]));
        }
        if(guild2 != null) {
            purgeReigningChampions(guildGetMemberIds(guild2[0]));
        }
        if(city1 != null) {
            purgeReigningChampions(cityGetCitizenIds(city1[0]));
        }
        if(city2 != null) {
            purgeReigningChampions(cityGetCitizenIds(city2[0]));
        }
    }
    public static void purgeReigningChampions(obj_id[] playersToRemove) {
        for(obj_id player : playersToRemove) {
            purgeReigningChampions(player);
        }
    }
    public static void purgeReigningChampions(obj_id player) {
        if(getCollectionSlotValue(player, "gcw_leaderboard_imperial_current_winner") > 0) {
            modifyCollectionSlotValue(player, "gcw_leaderboard_imperial_current_winner", -1);
            LOG("gcw_leaderboard_purge", "gcw_leaderboard_imperial_current_winner was removed from "+getPlayerName(player)+" ("+player+")");
        }
        if(getCollectionSlotValue(player, "gcw_leaderboard_rebel_current_winner") > 0) {
            modifyCollectionSlotValue(player, "gcw_leaderboard_rebel_current_winner", -1);
            LOG("gcw_leaderboard_purge", "gcw_leaderboard_rebel_current_winner was removed from "+getPlayerName(player)+" ("+player+")");
        }
    }

    /**
     * Called to remove the "War Planner" titles from players (anyone who is currently in the
     * "2 weeks ago" slot at a period reset and is getting pushed out). It will get re-applied
     * if they are back in a new period.
     *
     * Note: This should run when a period reset occurs but before any new winners
     * are assigned (so you don't purge new winners after they won).
     */
    public static void handlePurgeWarPlanners() {
        final obj_id[] players1 = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_REBEL, LEADERBOARD_PERIOD_2WEEKSAGO);
        final obj_id[] players2 = getRankedPlayersForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_IMPERIAL, LEADERBOARD_PERIOD_2WEEKSAGO);
        final int[] guild1 = getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_REBEL, LEADERBOARD_PERIOD_2WEEKSAGO);
        final int[] guild2 = getRankedGuildsForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_IMPERIAL, LEADERBOARD_PERIOD_2WEEKSAGO);
        final int[] city1 = getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_REBEL, LEADERBOARD_PERIOD_2WEEKSAGO);
        final int[] city2 = getRankedCitiesForPeriod(LEADERBOARD_TYPE_GCW, FACTION_HASH_IMPERIAL, LEADERBOARD_PERIOD_2WEEKSAGO);
        if(players1 != null) {
            purgeWarPlanners(players1);
        }
        if(players2 != null) {
            purgeWarPlanners(players2);
        }
        if(guild1 != null) {
            for(int id : guild1) {
                purgeWarPlanners(guildGetLeader(id));
            }
        }
        if(guild2 != null) {
            for(int id : guild2) {
                purgeWarPlanners(guildGetLeader(id));
            }
        }
        if(city1 != null) {
            for(int id : city1) {
                purgeWarPlanners(cityGetLeader(id));
            }
        }
        if(city2 != null) {
            for(int id : city2) {
                purgeWarPlanners(cityGetLeader(id));
            }
        }
    }
    public static void purgeWarPlanners(obj_id[] playersToRemove) {
        for(obj_id player : playersToRemove) {
            purgeWarPlanners(player);
        }
    }
    public static void purgeWarPlanners(obj_id player) {
        if(getCollectionSlotValue(player, "rebel_gcw_war_planner") > 0) {
            modifyCollectionSlotValue(player, "rebel_gcw_war_planner", -1);
            LOG("gcw_leaderboard_purge", "rebel_gcw_war_planner was removed from "+getPlayerName(player)+" ("+player+")");
        }
        if(getCollectionSlotValue(player, "imperial_gcw_war_planner") > 0) {
            modifyCollectionSlotValue(player, "imperial_gcw_war_planner", -1);
            LOG("gcw_leaderboard_purge", "imperial_gcw_war_planner was removed from "+getPlayerName(player)+" ("+player+")");
        }
    }

    /**
     * Drops all participant data from the current period
     */
    public static void clearAllCurrentPeriodParticipants() {
        final obj_id masterObject = getMasterLeaderboardObject();
        final int mangleCt = getIntObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+GCW+"_mangled.count");
        for(int i = 0; i < mangleCt; i++) {
            removeObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+GCW+"_mangled.segment."+i);
        }
        removeObjVar(masterObject, OBJVAR_LEADERBOARD_PARTICIPANT_GROUP+GCW+"_mangled.count");
        removeObjVar(masterObject, OBJVAR_LEADERBOARD_GUILD_PARTICIPANTS+GCW);
        removeObjVar(masterObject, OBJVAR_LEADERBOARD_CITY_PARTICIPANTS+GCW);
        debugMsg("Finished running leaderboard.clearAllCurrentPeriodParticipants()");
    }

    /**
     * Called on guild disband / city destruction to purge the history data from the master objects
     */
    public static void purgeEntityGcwLeaderboardHistory(int entityId, boolean isCity) {
        final obj_id masterObject = isCity ? getMasterCityObject() : getMasterGuildObject();
        final String ObjVar = OBJVAR_DATA_GCW_ENTITY_HISTORY + "_" + entityId;
        removeObjVar(masterObject, ObjVar);
    }

    /**
     * Shows an SUI List Box with available Debugging/Admin Data for the GCW Leaderboard
     * which is then passed to handler > renderGcwLeaderboardDataCsInternalOnly
     * CS Cmd: /admin gcwLeaderboard
     */
    public static void promptGcwLeaderboardDataRequestCsInternalOnly(obj_id player) throws InterruptedException {

        if(!isGod(player)) {
            return;
        }
        String[] menuOptions = {
                "Leaderboard Statistics",
                "Request Period Update",
                "Request Period Reset",
                "View Board: Current Player Imperial",
                "View Board: Current Player Rebel",
                "View Board: Current Guild Imperial",
                "View Board: Current Guild Rebel",
                "View Board: Current City Imperial",
                "View Board: Current City Rebel",
                "View Board: Previous Player Imperial",
                "View Board: Previous Player Rebel",
                "View Board: Previous Guild Imperial",
                "View Board: Previous Guild Rebel",
                "View Board: Previous City Imperial",
                "View Board: Previous City Rebel",
                "View Board: 2WksAgo Player Imperial",
                "View Board: 2WksAgo Player Rebel",
                "View Board: 2WksAgo Guild Imperial",
                "View Board: 2WksAgo Guild Rebel",
                "View Board: 2WksAgo City Imperial",
                "View Board: 2WksAgo City Rebel",
        };
        sui.listbox(player, player, "GM Resources for GCW Leaderboard. Please select an option below.", sui.OK_CANCEL, "GCW Leaderboard Debug", menuOptions, "handleCsLeaderboardDataMenuSelection", true, false);
    }

    /**
     * Handler for the response to promptGcwLeaderboardDataRequestCsInternalOnly
     * "View Board" items are identical to renderGcwLeaderboardDataToPlayer but includes debugging/CS info
     * ***NOTE*** if you add new switch options in front of the view leaderboard options, make sure you
     * adjust the subtraction inside getFormattedGcwLeaderboardData so it calls the correct board
     */
    public static void handleGcwLeaderboardDataRequestCsInternalOnly(obj_id player, int menuOptionIndex) throws InterruptedException {

        switch(menuOptionIndex) {

            case 0:
                sendSystemMessageTestingOnly(player, "Nothing is implemented for this option yet because Aconite couldn't decide what all would be useful here. If you have ideas, feel free to message him.");
                break;
            case 1:
                messageTo(getMasterLeaderboardObject(), "handleCurrentPeriodUpdateHeartbeat", null, 0f, false);
                sendSystemMessageTestingOnly(player, "Requesting a forced leaderboard period update...");
                LOG("leaderboard_gm", "GM "+getPlayerName(player)+" ("+player+") used /admin gcwLeaderboard to call handleCurrentPeriodUpdateHeartbeat");
                break;
            case 2:
                messageTo(getMasterLeaderboardObject(), "handleLeaderboardPeriodReset", null, 0f, false);
                sendSystemMessageTestingOnly(player, "Requesting a forced leaderboard period reset...");
                LOG("leaderboard_gm", "GM "+getPlayerName(player)+" ("+player+") used /admin gcwLeaderboard to call handleLeaderboardPeriodReset");
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                String[][] data = getFormattedGcwLeaderboardData(menuOptionIndex-3, true);
                sui.table(player, player, sui.OK_ONLY, GCW_LEADERBOARD_TITLE, "noHandler", "Debug GM Data", GCW_LEADERBOARD_COL_NAMES, GCW_LEADERBOARD_COL_TYPES, data, true, true);
                break;
        }
    }

    /**
     * Conditionally prints a debug message to console if debug mode is on
     */
    public static void debugMsg(String message) {
        if(VERBOSE_LOGGING) {
            LOG("leaderboard_debug", message);
        }
    }

} // end library.leaderboard
