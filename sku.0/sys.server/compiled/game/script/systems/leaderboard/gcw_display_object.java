package script.systems.leaderboard;

import script.library.leaderboard;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

/**
 * Attach this script to any object which you want to have the radial options
 * for accessing the GCW Leaderboard Menus. By default, this is the War Terminal,
 * War IntelPad, and War/Invasion Planning Tables.
 */
public class gcw_display_object extends script.terminal.base.base_terminal {

    public gcw_display_object()
    {
    }

    public static final string_id SID_MENU_LEADERBOARD = new string_id("gcw", "gcw_war_terminal_leaderboard_menu");
    public static final string_id SID_LEADERBOARD_CITY_CURRENT_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_imp_current_menu");
    public static final string_id SID_LEADERBOARD_CITY_CURRENT_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_reb_current_menu");
    public static final string_id SID_LEADERBOARD_CITY_PREVIOUS_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_imp_prev1_menu");
    public static final string_id SID_LEADERBOARD_CITY_PREVIOUS_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_reb_prev1_menu");
    public static final string_id SID_LEADERBOARD_CITY_2WEEKSAGO_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_imp_prev2_menu");
    public static final string_id SID_LEADERBOARD_CITY_2WEEKSAGO_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_reb_prev2_menu");
    public static final string_id SID_LEADERBOARD_CITY_HISTORICAL = new string_id("gcw", "gcw_war_terminal_leaderboard_city_historical_menu");
    public static final string_id SID_LEADERBOARD_GUILD_CURRENT_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_imp_current_menu");
    public static final string_id SID_LEADERBOARD_GUILD_CURRENT_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_reb_current_menu");
    public static final string_id SID_LEADERBOARD_GUILD_PREVIOUS_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_imp_prev1_menu");
    public static final string_id SID_LEADERBOARD_GUILD_PREVIOUS_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_reb_prev1_menu");
    public static final string_id SID_LEADERBOARD_GUILD_2WEEKSAGO_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_imp_prev2_menu");
    public static final string_id SID_LEADERBOARD_GUILD_2WEEKSAGO_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_reb_prev2_menu");
    public static final string_id SID_LEADERBOARD_GUILD_HISTORICAL = new string_id("gcw", "gcw_war_terminal_leaderboard_guild_historical_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_CURRENT_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_imp_current_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_CURRENT_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_reb_current_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_PREVIOUS_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_imp_prev1_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_PREVIOUS_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_reb_prev1_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_2WEEKSAGO_IMPERIAL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_imp_prev2_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_2WEEKSAGO_REBEL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_reb_prev2_menu");
    public static final string_id SID_LEADERBOARD_PLAYER_HISTORICAL = new string_id("gcw", "gcw_war_terminal_leaderboard_player_historical_menu");

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {

        int leaderboardMenu = mi.addRootMenu(menu_info_types.SERVER_MENU20, SID_MENU_LEADERBOARD);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU21, SID_LEADERBOARD_PLAYER_CURRENT_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU22, SID_LEADERBOARD_PLAYER_CURRENT_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU23, SID_LEADERBOARD_GUILD_CURRENT_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU24, SID_LEADERBOARD_GUILD_CURRENT_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU25, SID_LEADERBOARD_CITY_CURRENT_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU26, SID_LEADERBOARD_CITY_CURRENT_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU27, SID_LEADERBOARD_PLAYER_PREVIOUS_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU28, SID_LEADERBOARD_PLAYER_PREVIOUS_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU29, SID_LEADERBOARD_GUILD_PREVIOUS_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU30, SID_LEADERBOARD_GUILD_PREVIOUS_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU31, SID_LEADERBOARD_CITY_PREVIOUS_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU32, SID_LEADERBOARD_CITY_PREVIOUS_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU33, SID_LEADERBOARD_PLAYER_2WEEKSAGO_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU34, SID_LEADERBOARD_PLAYER_2WEEKSAGO_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU35, SID_LEADERBOARD_GUILD_2WEEKSAGO_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU36, SID_LEADERBOARD_GUILD_2WEEKSAGO_IMPERIAL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU37, SID_LEADERBOARD_CITY_2WEEKSAGO_REBEL);
        mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU38, SID_LEADERBOARD_CITY_2WEEKSAGO_IMPERIAL);
        if(getGuildId(player) > 0) {
            mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU39, SID_LEADERBOARD_GUILD_HISTORICAL);
        }
        if(getCitizenOfCityId(player) > 0) {
            mi.addSubMenu(leaderboardMenu, menu_info_types.SERVER_MENU40, SID_LEADERBOARD_CITY_HISTORICAL);
        }

        return super.OnObjectMenuRequest(self, player, mi);
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException {

        if(item == menu_info_types.SERVER_MENU21) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 1);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU22) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 2);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU23) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 3);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU24) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 4);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU25) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 5);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU26) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 6);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU27) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 7);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU28) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 8);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU29) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 9);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU30) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 10);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU31) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 11);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU32) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 12);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU33) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 13);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU34) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 14);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU35) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 15);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU36) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 16);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU37) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 17);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU38) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardDataToPlayer(player, self, 18);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU39 && getGuildId(player) > 0) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardEntityHistory(player, self, false);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        if(item == menu_info_types.SERVER_MENU40 && getCitizenOfCityId(player) > 0) {
            if(isLeaderboardAvailable()) {
                leaderboard.renderGcwLeaderboardEntityHistory(player, self, true);
            } else {
                notifyPlayerBoardUnavailable(player);
            }
        }
        return SCRIPT_CONTINUE;
    }

    public static boolean isLeaderboardAvailable() throws InterruptedException {
        final obj_id masterObject = leaderboard.getMasterLeaderboardObject();
        return !utils.hasScriptVar(masterObject, "heartbeat_is_in_progress") &&
                !utils.hasScriptVar(masterObject, "reset_is_in_progress") &&
                !leaderboard.isWithinPeriodBuffer();
    }

    public static void notifyPlayerBoardUnavailable(obj_id player) {
        sendSystemMessageTestingOnly(player, "The GCW Leaderboard is not available right now because an update process is ongoing. Please try again later.");
    }
}
