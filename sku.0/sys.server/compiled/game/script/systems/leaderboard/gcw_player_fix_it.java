package script.systems.leaderboard;

import script.dictionary;
import script.library.*;
import script.obj_id;

/**
 * This script is used as a tool to drop all possible leaderboard records from a player
 * in the event their leaderboard tracking is somehow bugged.
 *
 * Usage: /script attach (player) systems.leaderboard.gcw_player_fix_it
 * The script will do the rest.
 */
public class gcw_player_fix_it extends script.base_script {

    public gcw_player_fix_it()
    {
    }

    public static final String[] BADGE_LIST = {
            "bdg_gcw_leaderboard_rebel_winner_rank1",
            "bdg_gcw_leaderboard_rebel_winner_1x",
            "bdg_gcw_leaderboard_rebel_winner_5x",
            "bdg_gcw_leaderboard_rebel_winner_10x",
            "bdg_gcw_leaderboard_rebel_winner_15x",
            "bdg_gcw_leaderboard_rebel_winner_20x",
            "bdg_gcw_leaderboard_rebel_winner_25x",
            "bdg_gcw_leaderboard_rebel_winner_30x",
            "bdg_gcw_leaderboard_rebel_winner_35x",
            "bdg_gcw_leaderboard_rebel_winner_40x",
            "bdg_gcw_leaderboard_rebel_winner_45x",
            "bdg_gcw_leaderboard_rebel_winner_50x",
            "bdg_gcw_leaderboard_rebel_winner_55x",
            "bdg_gcw_leaderboard_rebel_winner_60x",
            "bdg_gcw_leaderboard_rebel_winner_65x",
            "bdg_gcw_leaderboard_rebel_winner_70x",
            "bdg_gcw_leaderboard_rebel_winner_75x",
            "bdg_gcw_leaderboard_rebel_winner_80x",
            "bdg_gcw_leaderboard_rebel_winner_85x",
            "bdg_gcw_leaderboard_rebel_winner_90x",
            "bdg_gcw_leaderboard_rebel_winner_95x",
            "bdg_gcw_leaderboard_rebel_winner_100x",
            "bdg_gcw_leaderboard_imperial_winner_rank1",
            "bdg_gcw_leaderboard_imperial_winner_1x",
            "bdg_gcw_leaderboard_imperial_winner_5x",
            "bdg_gcw_leaderboard_imperial_winner_10x",
            "bdg_gcw_leaderboard_imperial_winner_15x",
            "bdg_gcw_leaderboard_imperial_winner_20x",
            "bdg_gcw_leaderboard_imperial_winner_25x",
            "bdg_gcw_leaderboard_imperial_winner_30x",
            "bdg_gcw_leaderboard_imperial_winner_35x",
            "bdg_gcw_leaderboard_imperial_winner_40x",
            "bdg_gcw_leaderboard_imperial_winner_45x",
            "bdg_gcw_leaderboard_imperial_winner_50x",
            "bdg_gcw_leaderboard_imperial_winner_55x",
            "bdg_gcw_leaderboard_imperial_winner_60x",
            "bdg_gcw_leaderboard_imperial_winner_65x",
            "bdg_gcw_leaderboard_imperial_winner_70x",
            "bdg_gcw_leaderboard_imperial_winner_75x",
            "bdg_gcw_leaderboard_imperial_winner_80x",
            "bdg_gcw_leaderboard_imperial_winner_85x",
            "bdg_gcw_leaderboard_imperial_winner_90x",
            "bdg_gcw_leaderboard_imperial_winner_95x",
            "bdg_gcw_leaderboard_imperial_winner_100x",
            "bdg_gcw_leaderboard_rebel_guild_winner_rank1",
            "bdg_gcw_leaderboard_rebel_guild_winner_1x",
            "bdg_gcw_leaderboard_rebel_guild_winner_5x",
            "bdg_gcw_leaderboard_rebel_guild_winner_10x",
            "bdg_gcw_leaderboard_rebel_guild_winner_15x",
            "bdg_gcw_leaderboard_rebel_guild_winner_20x",
            "bdg_gcw_leaderboard_rebel_guild_winner_25x",
            "bdg_gcw_leaderboard_rebel_guild_winner_30x",
            "bdg_gcw_leaderboard_rebel_guild_winner_35x",
            "bdg_gcw_leaderboard_rebel_guild_winner_40x",
            "bdg_gcw_leaderboard_rebel_guild_winner_45x",
            "bdg_gcw_leaderboard_rebel_guild_winner_50x",
            "bdg_gcw_leaderboard_rebel_guild_winner_55x",
            "bdg_gcw_leaderboard_rebel_guild_winner_60x",
            "bdg_gcw_leaderboard_rebel_guild_winner_65x",
            "bdg_gcw_leaderboard_rebel_guild_winner_70x",
            "bdg_gcw_leaderboard_rebel_guild_winner_75x",
            "bdg_gcw_leaderboard_rebel_guild_winner_80x",
            "bdg_gcw_leaderboard_rebel_guild_winner_85x",
            "bdg_gcw_leaderboard_rebel_guild_winner_90x",
            "bdg_gcw_leaderboard_rebel_guild_winner_95x",
            "bdg_gcw_leaderboard_rebel_guild_winner_100x",
            "bdg_gcw_leaderboard_imperial_guild_winner_rank1",
            "bdg_gcw_leaderboard_imperial_guild_winner_1x",
            "bdg_gcw_leaderboard_imperial_guild_winner_5x",
            "bdg_gcw_leaderboard_imperial_guild_winner_10x",
            "bdg_gcw_leaderboard_imperial_guild_winner_15x",
            "bdg_gcw_leaderboard_imperial_guild_winner_20x",
            "bdg_gcw_leaderboard_imperial_guild_winner_25x",
            "bdg_gcw_leaderboard_imperial_guild_winner_30x",
            "bdg_gcw_leaderboard_imperial_guild_winner_35x",
            "bdg_gcw_leaderboard_imperial_guild_winner_40x",
            "bdg_gcw_leaderboard_imperial_guild_winner_45x",
            "bdg_gcw_leaderboard_imperial_guild_winner_50x",
            "bdg_gcw_leaderboard_imperial_guild_winner_55x",
            "bdg_gcw_leaderboard_imperial_guild_winner_60x",
            "bdg_gcw_leaderboard_imperial_guild_winner_65x",
            "bdg_gcw_leaderboard_imperial_guild_winner_70x",
            "bdg_gcw_leaderboard_imperial_guild_winner_75x",
            "bdg_gcw_leaderboard_imperial_guild_winner_80x",
            "bdg_gcw_leaderboard_imperial_guild_winner_85x",
            "bdg_gcw_leaderboard_imperial_guild_winner_90x",
            "bdg_gcw_leaderboard_imperial_guild_winner_95x",
            "bdg_gcw_leaderboard_imperial_guild_winner_100x",
            "bdg_gcw_leaderboard_rebel_city_winner_rank1",
            "bdg_gcw_leaderboard_rebel_city_winner_1x",
            "bdg_gcw_leaderboard_rebel_city_winner_5x",
            "bdg_gcw_leaderboard_rebel_city_winner_10x",
            "bdg_gcw_leaderboard_rebel_city_winner_15x",
            "bdg_gcw_leaderboard_rebel_city_winner_20x",
            "bdg_gcw_leaderboard_rebel_city_winner_25x",
            "bdg_gcw_leaderboard_rebel_city_winner_30x",
            "bdg_gcw_leaderboard_rebel_city_winner_35x",
            "bdg_gcw_leaderboard_rebel_city_winner_40x",
            "bdg_gcw_leaderboard_rebel_city_winner_45x",
            "bdg_gcw_leaderboard_rebel_city_winner_50x",
            "bdg_gcw_leaderboard_rebel_city_winner_55x",
            "bdg_gcw_leaderboard_rebel_city_winner_60x",
            "bdg_gcw_leaderboard_rebel_city_winner_65x",
            "bdg_gcw_leaderboard_rebel_city_winner_70x",
            "bdg_gcw_leaderboard_rebel_city_winner_75x",
            "bdg_gcw_leaderboard_rebel_city_winner_80x",
            "bdg_gcw_leaderboard_rebel_city_winner_85x",
            "bdg_gcw_leaderboard_rebel_city_winner_90x",
            "bdg_gcw_leaderboard_rebel_city_winner_95x",
            "bdg_gcw_leaderboard_rebel_city_winner_100x",
            "bdg_gcw_leaderboard_imperial_city_winner_rank1",
            "bdg_gcw_leaderboard_imperial_city_winner_1x",
            "bdg_gcw_leaderboard_imperial_city_winner_5x",
            "bdg_gcw_leaderboard_imperial_city_winner_10x",
            "bdg_gcw_leaderboard_imperial_city_winner_15x",
            "bdg_gcw_leaderboard_imperial_city_winner_20x",
            "bdg_gcw_leaderboard_imperial_city_winner_25x",
            "bdg_gcw_leaderboard_imperial_city_winner_30x",
            "bdg_gcw_leaderboard_imperial_city_winner_35x",
            "bdg_gcw_leaderboard_imperial_city_winner_40x",
            "bdg_gcw_leaderboard_imperial_city_winner_45x",
            "bdg_gcw_leaderboard_imperial_city_winner_50x",
            "bdg_gcw_leaderboard_imperial_city_winner_55x",
            "bdg_gcw_leaderboard_imperial_city_winner_60x",
            "bdg_gcw_leaderboard_imperial_city_winner_65x",
            "bdg_gcw_leaderboard_imperial_city_winner_70x",
            "bdg_gcw_leaderboard_imperial_city_winner_75x",
            "bdg_gcw_leaderboard_imperial_city_winner_80x",
            "bdg_gcw_leaderboard_imperial_city_winner_85x",
            "bdg_gcw_leaderboard_imperial_city_winner_90x",
            "bdg_gcw_leaderboard_imperial_city_winner_95x",
            "bdg_gcw_leaderboard_imperial_city_winner_100x"
    };

    public int OnAttach(obj_id self) throws InterruptedException {
        messageTo(self, "runFixIt", null, 3f, true);
        return SCRIPT_CONTINUE;
    }

    public int OnInitialize(obj_id self) throws InterruptedException {
        messageTo(self, "runFixIt", null, 3f, true);
        return SCRIPT_CONTINUE;
    }

    public int runFixIt(obj_id self, dictionary params) throws InterruptedException {

        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PLAYER_PERIOD);
        removeObjVar(self, leaderboard.OBJVAR_PLAYER_TRACKING_PLAYER_ALREADY);
        removeObjVar(self, leaderboard.OBJVAR_PLAYER_TRACKING_GUILD_ALREADY);
        removeObjVar(self, leaderboard.OBJVAR_PLAYER_TRACKING_CITY_ALREADY);
        removeObjVar(self, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_PLAYER);
        removeObjVar(self, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_GUILD);
        removeObjVar(self, leaderboard.OBJVAR_LAST_NOTIFIED_RANK_GCW_CITY);
        removeObjVar(self, leaderboard.OBJVAR_PLAYER_IMPERIAL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_PLAYER_REBEL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_PLAYER_REBEL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_GUILD_IMPERIAL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_GUILD_REBEL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_CITY_IMPERIAL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_CITY_REBEL_CURRENT_TEMP_SCORE);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_IMPERIAL);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PLAYER_SCORE_REBEL);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_IMPERIAL);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_CITY_SCORE_REBEL);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_IMPERIAL);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_GUILD_SCORE_REBEL);
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PLAYER_WIN_COUNT + "imperial");
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_PLAYER_WIN_COUNT + "rebel");
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + "imperial");
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_GUILD_WIN_COUNT + "rebel");
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_CITY_WIN_COUNT + "imperial");
        removeObjVar(self, leaderboard.OBJVAR_LEADERBOARD_CITY_WIN_COUNT + "rebel");
        removeObjVar(self, city.VAR_LEADERBOARD_PERIOD_ON_JOIN);
        removeObjVar(self, guild.VAR_LEADERBOARD_PERIOD_ON_JOIN);

        utils.removeScriptVar(self, "leaderboard_factional_presence_tracking");
        leaderboard.purgeReigningChampions(self);
        leaderboard.purgeWarPlanners(self);
        for (String b : BADGE_LIST) {
            badge.revokeBadge(self, b, false);
        }

        detachScript(self, "systems.leaderboard.gcw_player_fix_it");
        return SCRIPT_CONTINUE;
    }
}
