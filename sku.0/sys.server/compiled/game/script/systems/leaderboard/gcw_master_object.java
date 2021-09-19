package script.systems.leaderboard;

import script.*;
import script.library.*;
import java.util.*;

@SuppressWarnings("unused")
public class gcw_master_object extends script.base_script {

    public gcw_master_object()
    {
    }

    public static final int TYPE = leaderboard.LEADERBOARD_TYPE_GCW;
    private static final float CURRENT_PERIOD_HEARTBEAT = utils.getFloatConfigSetting("GameServer", "leaderboardPeriodHeartbeat", 900f);
    private static final boolean VERBOSE_LOGGING = utils.checkConfigFlag("GameServer", "leaderboardVerboseLogging");

    public int OnAttach(obj_id self) throws InterruptedException {
        messageTo(self, "handleLeaderboardInitialization", null, 60f, true);
        return SCRIPT_CONTINUE;
    }

    public int OnInitialize(obj_id self) throws InterruptedException {
        registerObjectToUniverse(self);
        messageTo(self, "handleLeaderboardInitialization", null, 60f, true);
        return SCRIPT_CONTINUE;
    }

    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException {
        sendSystemMessageTestingOnly(transferer, "You cannot move this item!");
        return SCRIPT_OVERRIDE;
    }

    public int OnDestroy(obj_id self) throws InterruptedException {
        removeObjVar(getPlanetByName("tatooine"), "leaderboard.master_object");
        return SCRIPT_CONTINUE;
    }

    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException {
        if(!isGod(objSpeaker)) {
            return SCRIPT_CONTINUE;
        }
        if(strText.equalsIgnoreCase("gcwLeaderboardUpdate")) {
            messageTo(self, "handleCurrentPeriodUpdateHeartbeat", null, 0f, false);
            sendSystemMessageTestingOnly(objSpeaker, "Requesting a forced leaderboard period update...");

        }
        if(strText.equalsIgnoreCase("gcwLeaderboardReset")) {
            messageTo(self, "handleLeaderboardPeriodReset", null, 0f, false);
            sendSystemMessageTestingOnly(objSpeaker, "Requesting a forced leaderboard period reset...");
        }
        return SCRIPT_CONTINUE;
    }

    public int handleLeaderboardInitialization(obj_id self, dictionary params) {
        setName(self, "Master GCW Leaderboard Object");
        final obj_id tatooine = getPlanetByName("tatooine");

        // First Time Setup
        if(!hasObjVar(tatooine, "leaderboard.master_object")) {
            setObjVar(tatooine, "leaderboard.master_object", self);
            messageTo(self, "handleLeaderboardFirstTimeSetup", null, 0f, true);
            leaderboard.debugMsg("Handling board initialization as first time setup...");
        } else {
            messageTo(self, "handleLeaderboardStartupChecks", null, 0f, true);
            leaderboard.debugMsg("Handling board initialization as startup check...");
        }
        return SCRIPT_CONTINUE;
    }

    public int handleLeaderboardFirstTimeSetup(obj_id self, dictionary params) {
        setObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD, 1);
        setObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD_START, getCalendarTime());
        setObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD_END, leaderboard.getNextLeaderboardPeriodEndTime());
        messageTo(self, "handleLeaderboardStartupChecks", null, 0f, true);
        return SCRIPT_CONTINUE;
    }

    public int handleLeaderboardStartupChecks(obj_id self, dictionary params) {
        // If we've been offline too long and missed an alarm, force a period reset (+15 min for startup buffer)
        if(getCalendarTime() > leaderboard.getCurrentLeaderboardPeriodEndTime()) {
            messageTo(self, "handleLeaderboardPeriodReset", null, 900f, true);
            LOG("leaderboard_object", "Running a forced handleLeaderboardPeriodReset() because calendar time > period time + period length.");
            return SCRIPT_CONTINUE;
        } else {
            // Set reset time clock (Thursdays at 19:00 GMT)
            timedMessageToWeeklyOnCycle(self, "handleLeaderboardPeriodReset", null);
            // Start current period update heartbeat
            recurringMessageTo(self, "handleCurrentPeriodUpdateHeartbeat", null, CURRENT_PERIOD_HEARTBEAT);
            leaderboard.debugMsg("Starting alarm clock and period update heartbeat.");
        }
        return SCRIPT_CONTINUE;
    }

    /**
     * Called on heartbeat to update the data for the "current" period.
     * This is a stupidly expensive operation so it should be limited.
     * By default, it only runs every 15 minutes. Depending on the size
     * and resources of your server, you may want to increase/decrease.
     * Change CURRENT_PERIOD_HEARTBEAT config value to adjust the time
     * (in seconds). But if you change that, be sure to look at the
     * Period Buffer inside the leaderboard lib to ensure the heartbeat
     * does not run while a period reset is in progress.
     *
     * Note: This calculates the cluster-wide entity scores. Meaning it
     * is calculating the % of points earned by a single player/guild/city
     * compared to every other player/guild/city on the cluster cumulatively.
     * But this isn't what the leaderboard actually determines rank value by.
     * The leaderboard displays the top 25 players and top 10 cities/guilds
     * and their % score compared to the other top ranked entities. So a %
     * display in the leaderboard table is the % of scores compared to other
     * ranked entities, not the % compared to the cluster-wide data. However,
     * the top window of the SUI table will display for the entity *both*
     * their cluster-wide % score and their % score compared to the top of
     * the ranks so that entity can see their relative contributions.
     */
    public int handleCurrentPeriodUpdateHeartbeat(obj_id self, dictionary params) throws InterruptedException {

        // don't run if we're already running
        if(utils.hasScriptVar(self, "heartbeat_is_in_progress") || utils.hasScriptVar(self, "reset_is_in_progress")) {
            leaderboard.debugMsg("Skipping period heartbeat because we're already in the middle of running one or in a reset...");
            return SCRIPT_CONTINUE;
        }

        // don't run in a buffer, grab all participants
        if(leaderboard.isWithinPeriodBuffer() || leaderboard.isWithinStartupBuffer()) {
            leaderboard.debugMsg("Skipping period heartbeat because we're within a period or startup buffer...");
            return SCRIPT_CONTINUE;
        }
        final Set<obj_id> participants = leaderboard.getPlayerParticipantsForCurrentPeriod(TYPE);
        if(participants == null) {
            return SCRIPT_CONTINUE;
        }
        leaderboard.debugMsg("Starting current period heartbeat...");
        utils.setScriptVar(self, "heartbeat_is_in_progress", true);

        // get total points earned by everyone for everything
        float totalImperialPlayerPoints = 0.0f;
        float totalRebelPlayerPoints = 0.0f;
        float totalImperialGuildPoints = 0.0f;
        float totalRebelGuildPoints = 0.0f;
        float totalImperialCityPoints = 0.0f;
        float totalRebelCityPoints = 0.0f;
        for(obj_id player : participants) {
            totalImperialPlayerPoints += getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_IMPERIAL);
            totalRebelPlayerPoints += getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_REBEL);
            totalImperialGuildPoints += getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_IMPERIAL);
            totalRebelGuildPoints += getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_REBEL);
            totalImperialCityPoints += getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_IMPERIAL);
            totalRebelCityPoints += getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_REBEL);
            leaderboard.debugMsg("Grabbing earned points from player " + getPlayerName(player) + " (" + player + ") *******************");
            if(VERBOSE_LOGGING) {
                leaderboard.debugMsg("Imperial Player Points: " + getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_IMPERIAL));
                leaderboard.debugMsg("Rebel Player Points: " + getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_REBEL));
                leaderboard.debugMsg("Imperial Guild Points: " + getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_IMPERIAL));
                leaderboard.debugMsg("Rebel Guild Points: " + getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_REBEL));
                leaderboard.debugMsg("Imperial City Points: " + getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_IMPERIAL));
                leaderboard.debugMsg("Rebel City Points: " + getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_REBEL));
                leaderboard.debugMsg("*******************");
            }
        }
        leaderboard.debugMsg("Finished Score Calculations...");
        leaderboard.debugMsg("Point Groups: IP: "+totalImperialPlayerPoints+" RP: "+totalRebelPlayerPoints+" IG: "+totalImperialGuildPoints+" RG: "+totalRebelGuildPoints+" IC: "+totalImperialCityPoints+" RC: "+totalRebelCityPoints);

        // maps to hold our score data <Player/Entity, Raw Score>
        HashMap<Long, Float> scoresImperialPlayers = new HashMap<>();
        HashMap<Long, Float> scoresRebelPlayers = new HashMap<>();
        HashMap<Integer, Float> scoresImperialGuild = new HashMap<>();
        HashMap<Integer, Float> scoresRebelGuild = new HashMap<>();
        HashMap<Integer, Float> scoresImperialCity = new HashMap<>();
        HashMap<Integer, Float> scoresRebelCity = new HashMap<>();

        // calculate and store player scores
        float tempScore;
        for (obj_id player : participants) {
            if(getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_IMPERIAL) > 0) {
                tempScore = (getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_IMPERIAL) / totalImperialPlayerPoints) * 100;
                leaderboard.addPlayerGcwLeaderboardTempTrackingScore(player, FACTION_HASH_IMPERIAL, tempScore);
                scoresImperialPlayers.put(player.getValue(), tempScore);
                leaderboard.debugMsg("Adding Cluster Score "+tempScore+" for Imperial Player "+player+" to dictionary.");
            }
            if(getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_REBEL) > 0) {
                tempScore = (getFloatObjVar(player, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_REBEL) / totalRebelPlayerPoints) * 100;
                leaderboard.addPlayerGcwLeaderboardTempTrackingScore(player, FACTION_HASH_REBEL, tempScore);
                scoresRebelPlayers.put(player.getValue(), tempScore);
                leaderboard.debugMsg("Adding Cluster Score " + tempScore + " for Rebel Player " + player + " to dictionary.");
            }
        }

        // calculate guild and city scores
        int faction;
        float tempEntityPoints = 0.0f;
        obj_id[] citizens;
        final Set<Integer> cityParticipants = leaderboard.getEntityParticipantsForCurrentPeriod(TYPE, true);
        final Set<Integer> guildParticipants = leaderboard.getEntityParticipantsForCurrentPeriod(TYPE, false);
        if(cityParticipants != null) {
            for(int city : cityParticipants) {
                faction = cityGetFaction(city);
                citizens = cityGetCitizenIds(city);
                for(obj_id citizen : citizens) {
                    if(participants.contains(citizen)) {
                        if(faction == FACTION_HASH_IMPERIAL) {
                            if(getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_IMPERIAL) > 0) {
                                tempEntityPoints += getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_IMPERIAL);
                            }
                        } else if (faction == FACTION_HASH_REBEL) {
                            if(getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_REBEL) > 0) {
                                tempEntityPoints += getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_REBEL);
                            }
                        }
                    }
                }
                if(tempEntityPoints > 0) {
                    if(faction == FACTION_HASH_IMPERIAL) {
                        tempScore = tempEntityPoints / totalImperialCityPoints * 100;
                        scoresImperialCity.put(city, tempScore);
                        leaderboard.addEntityGcwLeaderboardTempTrackingScore(city, FACTION_HASH_IMPERIAL, tempScore, true);
                        leaderboard.debugMsg("Adding Cluster Score " + tempScore + " for Imperial City " + city + " to dictionary.");
                    } else if (faction == FACTION_HASH_REBEL) {
                        tempScore = tempEntityPoints / totalRebelCityPoints * 100;
                        scoresRebelCity.put(city, tempScore);
                        leaderboard.addEntityGcwLeaderboardTempTrackingScore(city, FACTION_HASH_REBEL, tempScore, true);
                        leaderboard.debugMsg("Adding Cluster Score " + tempScore + " for Rebel City " + city + " to dictionary.");
                    }
                }
                tempEntityPoints = 0;
            }
        }
        if(guildParticipants != null) {
            for(int guild : guildParticipants) {
                faction = guildGetCurrentFaction(guild);
                citizens = guildGetMemberIds(guild);
                for(obj_id citizen : citizens) {
                    if(participants.contains(citizen)) {
                        if(faction == FACTION_HASH_IMPERIAL) {
                            if(getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_IMPERIAL) > 0) {
                                tempEntityPoints += getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_IMPERIAL);
                            }
                        } else if (faction == FACTION_HASH_REBEL) {
                            if(getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_REBEL) > 0) {
                                tempEntityPoints += getFloatObjVar(citizen, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_REBEL);
                            }
                        }
                    }
                }
                if(tempEntityPoints > 0) {
                    if(faction == FACTION_HASH_IMPERIAL) {
                        tempScore = tempEntityPoints / totalImperialGuildPoints * 100;
                        scoresImperialGuild.put(guild, tempScore);
                        leaderboard.addEntityGcwLeaderboardTempTrackingScore(guild, FACTION_HASH_IMPERIAL, tempScore, false);
                        leaderboard.debugMsg("Adding Cluster Score " + tempScore + " for Imperial Guild " + guild + " to dictionary.");

                    } else if (faction == FACTION_HASH_REBEL) {
                        tempScore = tempEntityPoints / totalRebelGuildPoints * 100;
                        scoresRebelGuild.put(guild, tempScore);
                        leaderboard.addEntityGcwLeaderboardTempTrackingScore(guild, FACTION_HASH_REBEL, tempScore, false);
                        leaderboard.debugMsg("Adding Cluster Score " + tempScore + " for Rebel Guild " + guild + " to dictionary.");
                    }
                }
            }
        }

        // sort, pair, and store data
        if(scoresImperialPlayers.size() > 0) {
            rank_pair winnersPlayerImperials = rank_pair.sortForPlayers(scoresImperialPlayers, 25);
            leaderboard.setCurrentPeriodScoreData(1, winnersPlayerImperials.getPlayerEntrants, winnersPlayerImperials.getScores);
            leaderboard.debugMsg("rank_pair'ing scoresImperialPlayers because scoresImperialPlayers.size() > 0");
        }
        if(scoresRebelPlayers.size() > 0) {
            rank_pair winnersPlayerRebels = rank_pair.sortForPlayers(scoresRebelPlayers, 25);
            leaderboard.setCurrentPeriodScoreData(2, winnersPlayerRebels.getPlayerEntrants, winnersPlayerRebels.getScores);
            leaderboard.debugMsg("rank_pair'ing scoresRebelPlayers because scoresRebelPlayers.size() > 0");
        }
        if(scoresImperialGuild.size() > 0) {
            rank_pair winnersGuildImperials = rank_pair.sortForEntity(scoresImperialGuild, 10);
            leaderboard.setCurrentPeriodScoreData(3, winnersGuildImperials.getEntityEntrants, winnersGuildImperials.getScores);
            leaderboard.debugMsg("rank_pair'ing scoresImperialGuild because scoresImperialGuild.size() > 0");
        }
        if(scoresRebelGuild.size() > 0) {
            rank_pair winnersGuildRebels = rank_pair.sortForEntity(scoresRebelGuild, 10);
            leaderboard.setCurrentPeriodScoreData(4, winnersGuildRebels.getEntityEntrants, winnersGuildRebels.getScores);
            leaderboard.debugMsg("rank_pair'ing scoresRebelGuild because scoresRebelGuild.size() > 0");
        }
        if(scoresImperialCity.size() > 0) {
            rank_pair winnersCityImperials = rank_pair.sortForEntity(scoresImperialCity, 10);
            leaderboard.setCurrentPeriodScoreData(5, winnersCityImperials.getEntityEntrants, winnersCityImperials.getScores);
            leaderboard.debugMsg("rank_pair'ing scoresImperialCity because scoresImperialCity.size() > 0");
        }
        if(scoresRebelCity.size() > 0) {
            rank_pair winnersCityRebels = rank_pair.sortForEntity(scoresRebelCity, 10);
            leaderboard.setCurrentPeriodScoreData(6, winnersCityRebels.getEntityEntrants, winnersCityRebels.getScores);
            leaderboard.debugMsg("rank_pair'ing scoresRebelCity because scoresRebelCity.size() > 0");
        }

        setObjVar(self, leaderboard.OBJVAR_CURRENT_PERIOD_LAST_UPDATED, getCalendarTime());
        messageTo(self, "handleFinishedCurrentPeriodUpdate", null, 30f, false);
        LOG("gcw_leaderboard_object", "Finished running handleCurrentPeriodUpdateHeartbeat() at "+getCalendarTime());
        return SCRIPT_CONTINUE;
    }

    /**
     * Called when a period update finishes for any necessary handlers.
     * Mainly this handles notifying the Guild/City if they have
     * "ranked" (top 25 players, top 10 guild/cities) or if their rank
     * position has changed (but only if it has changed to a value that
     * isn't what they were most recently notified about, e.g. to prevent
     * spamming players every 15 minutes with position updates that they
     * already know about.
     *
     * todo the patch notes for these chat messages say the player is
     *  notified in the "GCW Channel" but that doesn't exist and sending to
     *  faction channels would mean notifying potentially 100s of players
     *  about a single player's rank position change, so this has been
     *  implemented as a system message for now.
     */
    public int handleFinishedCurrentPeriodUpdate(obj_id self, dictionary params) throws InterruptedException {

        //todo @pre-production-deployment: refactor for compression

        final int period = leaderboard.LEADERBOARD_PERIOD_CURRENT;
        final obj_id[] imperialPlayers = leaderboard.getRankedPlayersForPeriod(TYPE, FACTION_HASH_IMPERIAL, period);
        final obj_id[] rebelPlayers = leaderboard.getRankedPlayersForPeriod(TYPE, FACTION_HASH_REBEL, period);
        final int[] imperialCities = leaderboard.getRankedCitiesForPeriod(TYPE, FACTION_HASH_IMPERIAL, period);
        final int[] rebelCities = leaderboard.getRankedCitiesForPeriod(TYPE, FACTION_HASH_REBEL, period);
        final int[] imperialGuilds = leaderboard.getRankedGuildsForPeriod(TYPE, FACTION_HASH_IMPERIAL, period);
        final int[] rebelGuilds = leaderboard.getRankedGuildsForPeriod(TYPE, FACTION_HASH_REBEL, period);

        int rank = 1;
        String channel;
        obj_id leader;
        if(imperialPlayers != null) {
            for (obj_id i : imperialPlayers) {
                if(getIntObjVar(i, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_PLAYER) != rank && isIdValid(i) && isPlayerConnected(i)) {
                    sendSystemMessageTestingOnly(i, "[GCW Leaderboard] Congratulations! You are now ranked in position " + rank + " on the Current Imperial Players GCW Leaderboard.");
                    leaderboard.announceGcwLeaderboardRankChangeToDiscord(getPlayerFullName(i)+" is now ranked in position "+rank+" on the Current Imperial Players GCW Leaderboard.");
                    setObjVar(i, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_PLAYER, rank);
                }
                ++rank;
            }
        }
        if(rebelPlayers != null) {
            rank = 1;
            for (obj_id i : rebelPlayers) {
                if(getIntObjVar(i, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_PLAYER) != rank && isIdValid(i) && isPlayerConnected(i)) {
                    sendSystemMessageTestingOnly(i, "[GCW Leaderboard] Congratulations! You are now ranked in position " + rank + " on the Current Rebel Players GCW Leaderboard.");
                    leaderboard.announceGcwLeaderboardRankChangeToDiscord(getPlayerFullName(i)+" is now ranked in position "+rank+" on the Current Rebel Players GCW Leaderboard.");
                    setObjVar(i, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_PLAYER, rank);
                }
                ++rank;
            }
        }
        if(imperialCities != null) {
            rank = 1;
            for (int i : imperialCities) {
                leader = cityGetLeader(i);
                if(getIntObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_CITY) != rank && isIdValid(leader)) {
                    channel = city.getChatChannelForCity(i);
                    chatSendToRoom(channel, "[GCW Leaderboard] Congratulations! Your city is now ranked in position " + rank + " on the Current Imperial Cities GCW Leaderboard.", "");
                    leaderboard.announceGcwLeaderboardRankChangeToDiscord(cityGetName(i)+" is now ranked in position "+rank+" on the Current Imperial Cities GCW Leaderboard.");
                    setObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_CITY, rank);
                }
                ++rank;
            }
        }
        if(rebelCities != null) {
            rank = 1;
            for (int i : rebelCities) {
                leader = cityGetLeader(i);
                if(getIntObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_CITY) != rank && isIdValid(leader)) {
                    channel = city.getChatChannelForCity(i);
                    chatSendToRoom(channel, "[GCW Leaderboard] Congratulations! Your city is now ranked in position " + rank + " on the Current Rebel Cities GCW Leaderboard.", "");
                    leaderboard.announceGcwLeaderboardRankChangeToDiscord(cityGetName(i)+" is now ranked in position "+rank+" on the Current Rebel Cities GCW Leaderboard.");
                    setObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_CITY, rank);
                }
                ++rank;
            }
        }
        if(imperialGuilds != null) {
            rank = 1;
            for (int i : imperialGuilds) {
                leader = guildGetLeader(i);
                if(getIntObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_GUILD) != rank && isIdValid(leader)) {
                    channel = guild.getChatChannelForGuild(i);
                    chatSendToRoom(channel, "[GCW Leaderboard] Congratulations! Your guild is now ranked in position " + rank + " on the Current Imperial Guild GCW Leaderboard.", "");
                    leaderboard.announceGcwLeaderboardRankChangeToDiscord(guildGetName(i)+" ("+guildGetAbbrev(i)+") is now ranked in position "+rank+" on the Current Imperial Guild GCW Leaderboard.");
                    setObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_GUILD, rank);
                }
                ++rank;
            }
        }
        if(rebelGuilds != null) {
            rank = 1;
            for (int i : rebelGuilds) {
                leader = guildGetLeader(i);
                if(getIntObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_GUILD) != rank && isIdValid(leader)) {
                    channel = guild.getChatChannelForGuild(i);
                    chatSendToRoom(channel, "[GCW Leaderboard] Congratulations! Your guild is now ranked in position " + rank + " on the Current Rebel Guild GCW Leaderboard.", "");
                    leaderboard.announceGcwLeaderboardRankChangeToDiscord(guildGetName(i)+" ("+guildGetAbbrev(i)+") is now ranked in position "+rank+" on the Current Rebel Guild GCW Leaderboard.");
                    setObjVar(leader, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_GUILD, rank);
                }
                ++rank;
            }
        }

        utils.removeScriptVar(self, "heartbeat_is_in_progress");
        LOG("gcw_leaderboard_object", "Finished running handleFinishedCurrentPeriodUpdate() at "+getCalendarTime());
        return SCRIPT_CONTINUE;
    }

    /**
     * Called when the weekly "reset" period happens to handle purging all of
     * the oldest data and revoking slots from players as applicable
     */
    public int handleLeaderboardPeriodReset(obj_id self, dictionary params) throws InterruptedException {

        // don't reset in the middle of a reset
        if(utils.hasScriptVar(self, "reset_is_in_progress")) {
            leaderboard.debugMsg("Skipping period reset because we're already in the middle of running one...");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "reset_is_in_progress", true);

        // Stop heartbeat while we move through this process
        cancelRecurringMessageTo(self, "handleCurrentPeriodUpdateHeartbeat");
        leaderboard.debugMsg("Period Reset Started... Cancelling heartbeat message.");

        // Purge "War Planner" slots from players in the "2 Weeks Ago" board who are falling off
        leaderboard.handlePurgeWarPlanners();

        // Purge Reigning Champion slots from players in the "Previous Week" board who are getting pushed back
        leaderboard.handlePurgeReigningChampions();

        // Add a time buffer for the above to process before we start pushing data around
        messageTo(self, "handleLeaderboardDataPushBack", null, 30f, true);
        LOG("gcw_leaderboard_object", "Finished running handleLeaderboardPeriodReset() at "+getCalendarTime());

        return SCRIPT_CONTINUE;
    }


    public int handleLeaderboardDataPushBack(obj_id self, dictionary params) throws InterruptedException {

        // Purge all 2 Weeks Ago Data by moving Previous Week Data into it
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_IMPERIAL_ID, getObjIdArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_ID));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_IMPERIAL_SCORE, getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_SCORE));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_REBEL_ID, getObjIdArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_ID));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_PLAYERS_REBEL_SCORE, getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_SCORE));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_IMPERIAL_ID, getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_ID));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_IMPERIAL_SCORE, getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_SCORE));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_REBEL_ID, getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_ID));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_GUILD_REBEL_SCORE, getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_SCORE));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_IMPERIAL_ID, getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_ID));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_IMPERIAL_SCORE, getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_SCORE));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_REBEL_ID, getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_ID));
        setObjVar(self, leaderboard.OBJVAR_DATA_2WEEKSAGO_PERIOD_CITY_REBEL_SCORE, getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_SCORE));

        // Grab new top ranked list
        final obj_id[] rankedImperialPlayerIds = getObjIdArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_ID);
        final float[] rankedImperialPlayerScores = getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_SCORE);
        final obj_id[] rankedRebelPlayerIds = getObjIdArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_ID);
        final float[] rankedRebelPlayerScores = getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_SCORE);
        final int[] rankedImperialGuildIds = getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_ID);
        final float[] rankedImperialGuildScores = getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_SCORE);
        final int[] rankedRebelGuildIds = getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_ID);
        final float[] rankedRebelGuildScores = getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_SCORE);
        final int[] rankedImperialCityIds = getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_ID);
        final float[] rankedImperialCityScores = getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_SCORE);
        final int[] rankedRebelCityIds = getIntArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_ID);
        final float[] rankedRebelCityScores = getFloatArrayObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_SCORE);
        int[] winnerImperialCityIds = {};
        int[] winnerRebelCityIds = {};
        int[] winnerImperialGuildIds  = {};
        int[] winnerRebelGuildIds = {};
        if(rankedImperialCityIds != null) { // use Math.min to protect from copying in null values when grabbing only winners
            winnerImperialCityIds = Arrays.copyOfRange(rankedImperialCityIds, 0, Math.min(rankedImperialCityIds.length, 5));
        }
        if(rankedRebelCityIds != null) {
            winnerRebelCityIds = Arrays.copyOfRange(rankedRebelCityIds, 0, Math.min(rankedRebelCityIds.length, 5));
        }
        if(rankedImperialGuildIds != null) {
            winnerImperialGuildIds = Arrays.copyOfRange(rankedImperialGuildIds, 0, Math.min(rankedImperialGuildIds.length, 5));
        }
        if(rankedRebelGuildIds != null) {
            winnerRebelGuildIds = Arrays.copyOfRange(rankedRebelGuildIds, 0, Math.min(rankedRebelGuildIds.length, 5));
        }

        // Store the history of guilds and cities
        final Set<Integer> guildParticipants = leaderboard.getEntityParticipantsForCurrentPeriod(TYPE, false);
        final Set<Integer> cityParticipants = leaderboard.getEntityParticipantsForCurrentPeriod(TYPE, true);
        int entityFaction;
        int winner = 0;
        int champion = 0;
        if(guildParticipants != null) {
            for (int guildId : guildParticipants) {
                entityFaction = guildGetCurrentFaction(guildId);
                if(entityFaction == FACTION_HASH_IMPERIAL) {
                    if (Arrays.stream(winnerImperialGuildIds).anyMatch(x -> x == guildId)) {
                        winner = 1;
                    }
                    if (winnerImperialGuildIds[0] == guildId) {
                        champion = 1;
                    }
                } else if (entityFaction == FACTION_HASH_REBEL) {
                    if (Arrays.stream(winnerRebelGuildIds).anyMatch(x -> x == guildId)) {
                        winner = 1;
                    }
                    if (winnerRebelGuildIds[0] == guildId) {
                        champion = 1;
                    }
                }
                String[] data = {
                        Integer.toString(leaderboard.getCurrentLeaderboardPeriodEndTime()),
                        Integer.toString(entityFaction),
                        Float.toString(leaderboard.getEntityRelativeScoreToTransposedLeaderboardScores(guildId, entityFaction, leaderboard.LEADERBOARD_PERIOD_CURRENT, false)),
                        Integer.toString(winner),
                        Integer.toString(champion)
                };
                leaderboard.packGcwEntityHistoryData(guildId, data, false);
                winner = 0;
                champion = 0;
            }
        }
        if(cityParticipants != null) {
            for (int cityId : cityParticipants) {
                entityFaction = cityGetFaction(cityId);
                if(entityFaction == FACTION_HASH_IMPERIAL) {
                    if (Arrays.stream(winnerImperialGuildIds).anyMatch(x -> x == cityId)) {
                        winner = 1;
                    }
                    if (winnerImperialGuildIds[0] == cityId) {
                        champion = 1;
                    }
                } else if (entityFaction == FACTION_HASH_REBEL) {
                    if (Arrays.stream(winnerRebelGuildIds).anyMatch(x -> x == cityId)) {
                        winner = 1;
                    }
                    if (winnerRebelGuildIds[0] == cityId) {
                        champion = 1;
                    }
                }
                String[] data = {
                        Integer.toString(leaderboard.getCurrentLeaderboardPeriodEndTime()),
                        Integer.toString(entityFaction),
                        Float.toString(leaderboard.getEntityRelativeScoreToTransposedLeaderboardScores(cityId, entityFaction, leaderboard.LEADERBOARD_PERIOD_CURRENT, true)),
                        Integer.toString(winner),
                        Integer.toString(champion)
                };
                leaderboard.packGcwEntityHistoryData(cityId, data, true);
                winner = 0;
                champion = 0;
            }
        }
        leaderboard.debugMsg("Finished packing guild/city entity history...");

        // Purge all Previous Week Data by moving Current into it
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_ID, rankedImperialPlayerIds);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_IMPERIAL_SCORE, rankedImperialPlayerScores);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_ID, rankedRebelPlayerIds);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_PLAYERS_REBEL_SCORE, rankedRebelPlayerScores);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_ID, rankedImperialGuildIds);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_IMPERIAL_SCORE, rankedImperialGuildScores);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_ID, rankedRebelGuildIds);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_GUILD_REBEL_SCORE, rankedRebelGuildScores);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_ID, rankedImperialCityIds);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_IMPERIAL_SCORE, rankedImperialCityScores);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_ID, rankedRebelCityIds);
        setObjVar(self, leaderboard.OBJVAR_DATA_PREVIOUS_PERIOD_CITY_REBEL_SCORE, rankedRebelCityScores);

        // Grant winner rewards
        if(rankedRebelPlayerIds != null && rankedRebelPlayerScores != null) {
            leaderboard.handleGcwLeaderboardPlayerWinners(FACTION_HASH_REBEL,
                    Arrays.copyOfRange(rankedRebelPlayerIds, 0, Math.min(rankedRebelPlayerIds.length, 10)),
                    Arrays.copyOfRange(rankedRebelPlayerScores, 0, Math.min(rankedRebelPlayerScores.length, 10)));
        }
        if(rankedImperialPlayerIds != null && rankedImperialPlayerScores != null) {
            leaderboard.handleGcwLeaderboardPlayerWinners(FACTION_HASH_IMPERIAL,
                    Arrays.copyOfRange(rankedImperialPlayerIds, 0, Math.min(rankedImperialPlayerIds.length, 10)),
                    Arrays.copyOfRange(rankedImperialPlayerScores, 0, Math.min(rankedImperialPlayerScores.length, 10)));
        }
        if(rankedRebelCityScores != null) {
            leaderboard.handleGcwLeaderboardCityWinners(FACTION_HASH_REBEL,
                    winnerRebelCityIds,
                    Arrays.copyOfRange(rankedRebelCityScores, 0, Math.min(rankedRebelCityScores.length, 5)));
        }
        if(rankedImperialCityScores != null) {
            leaderboard.handleGcwLeaderboardCityWinners(FACTION_HASH_IMPERIAL,
                    winnerImperialCityIds,
                    Arrays.copyOfRange(rankedImperialCityScores, 0, Math.min(rankedImperialCityScores.length, 5)));
        }
        if(rankedRebelGuildScores != null) {
            leaderboard.handleGcwLeaderboardGuildWinners(FACTION_HASH_REBEL,
                    winnerRebelGuildIds,
                    Arrays.copyOfRange(rankedRebelGuildScores, 0, Math.min(rankedRebelGuildScores.length, 5)));
        }
        if(rankedImperialGuildScores != null) {
            leaderboard.handleGcwLeaderboardGuildWinners(FACTION_HASH_IMPERIAL,
                    winnerImperialGuildIds,
                    Arrays.copyOfRange(rankedImperialGuildScores, 0, Math.min(rankedImperialGuildScores.length, 5)));
        }

        // Add a time buffer for the above to process before we are done
        messageTo(self, "handleLeaderboardFinishReset", null, 30f, true);
        LOG("gcw_leaderboard_object", "Finished running handleLeaderboardDataPushBack() at "+getCalendarTime());

        return SCRIPT_CONTINUE;
    }

    public int handleLeaderboardFinishReset(obj_id self, dictionary params) throws InterruptedException {

        // Clear Current Week Data
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_ID);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_IMPERIAL_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_ID);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_PLAYERS_REBEL_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_ID);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_IMPERIAL_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_ID);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_GUILD_REBEL_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_ID);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_IMPERIAL_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_ID);
        removeObjVar(self, leaderboard.OBJVAR_DATA_CURRENT_PERIOD_CITY_REBEL_SCORE);

        // Clear tracking vars and message existing participants we've finished
        leaderboard.clearGcwLeaderboardTrackingVars(leaderboard.getPlayerParticipantsForCurrentPeriod(TYPE));

        // Clear participant data
        leaderboard.clearAllCurrentPeriodParticipants();

        // +1 the Period Data
        setObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD, getIntObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD+1));
        setObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD_START, getCalendarTime());
        setObjVar(self, leaderboard.OBJVAR_CURRENT_PERIOD_LAST_UPDATED, getCalendarTime());
        setObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PERIOD_END, leaderboard.getNextLeaderboardPeriodEndTime());

        // Resume Heartbeat
        recurringMessageTo(self, "handleCurrentPeriodUpdateHeartbeat", null, CURRENT_PERIOD_HEARTBEAT);

        utils.removeScriptVar(self, "reset_is_in_progress");
        LOG("gcw_leaderboard_object", "Finished running handleLeaderboardFinishReset() at "+getCalendarTime());
        return SCRIPT_CONTINUE;
    }
}
