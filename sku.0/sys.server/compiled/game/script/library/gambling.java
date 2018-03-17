package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.money;

public class gambling extends script.base_script
{
    public gambling()
    {
    }
    public static final String SCRIPTVAR_GAMBLING = "isGambling";
    public static final String VAR_GAMBLE_BASE = "gambling";
    public static final String VAR_PREDEFINED_TYPE = VAR_GAMBLE_BASE + ".predefined";
    public static final String VAR_TABLE_BASE = VAR_GAMBLE_BASE + ".table";
    public static final String VAR_TABLE_TYPE = VAR_TABLE_BASE + ".type";
    public static final String VAR_TABLE_PLAYER_LIMIT_BASE = VAR_TABLE_BASE + ".playerLimit";
    public static final String VAR_TABLE_PLAYER_LIMIT_MIN = VAR_TABLE_PLAYER_LIMIT_BASE + ".min";
    public static final String VAR_TABLE_PLAYER_LIMIT_MAX = VAR_TABLE_PLAYER_LIMIT_BASE + ".max";
    public static final String VAR_TABLE_PLAYERS = VAR_TABLE_BASE + ".players";
    public static final String VAR_TABLE_BET_BASE = VAR_TABLE_BASE + ".bet";
    public static final String VAR_TABLE_BET_ANTE = VAR_TABLE_BET_BASE + ".ante";
    public static final String VAR_TABLE_BET_MIN = VAR_TABLE_BET_BASE + ".min";
    public static final String VAR_TABLE_BET_MAX = VAR_TABLE_BET_BASE + ".max";
    public static final String VAR_TABLE_BET_ACCEPT = VAR_TABLE_BET_BASE + ".accept";
    public static final String VAR_GAME_BASE = VAR_GAMBLE_BASE + ".game";
    public static final String VAR_GAME_STATE = VAR_GAME_BASE + ".state";
    public static final String VAR_GAME_ROUND = VAR_GAME_BASE + ".round";
    public static final String VAR_GAME_TURN_BASE = VAR_GAME_BASE + ".turn";
    public static final String VAR_GAME_TURN_PLAYER = VAR_GAME_TURN_BASE + ".player";
    public static final String VAR_GAME_TURN_STAMP = VAR_GAME_TURN_BASE + ".stamp";
    public static final String VAR_GAME_PLAYERS = VAR_GAME_BASE + ".players";
    public static final String VAR_GAME_PLAYERS_IDS = VAR_GAME_PLAYERS + ".ids";
    public static final String VAR_SLOT_BASE = VAR_GAME_BASE + ".slot";
    public static final String VAR_REEL_ODDS = VAR_SLOT_BASE + ".reelOdds";
    public static final String TBL_GAME_PARAMS = "datatables/gambling/params.iff";
    public static final String COL_GAME_NAME = "NAME";
    public static final String COL_PLAYERS_MIN = "PLAYERS_MIN";
    public static final String COL_PLAYERS_MAX = "PLAYERS_MAX";
    public static final String COL_BET_ANTE = "BET_ANTE";
    public static final String COL_BET_MIN = "BET_MIN";
    public static final String COL_BET_MAX = "BET_MAX";
    public static final String STF_INTERFACE = "gambling/default_interface";
    public static final string_id SID_BLANK = new string_id(STF_INTERFACE, "blank");
    public static final string_id SID_ROLLING = new string_id(STF_INTERFACE, "rolling");
    public static final string_id PROSE_STATION_FULL = new string_id(STF_INTERFACE, "prose_station_full");
    public static final string_id PROSE_PLAYER_JOIN = new string_id(STF_INTERFACE, "prose_player_join");
    public static final string_id PROSE_PLAYER_JOIN_OTHER = new string_id(STF_INTERFACE, "prose_player_join_other");
    public static final string_id PROSE_PLAYER_LEAVE = new string_id(STF_INTERFACE, "prose_player_leave");
    public static final string_id PROSE_PLAYER_LEAVE_OTHER = new string_id(STF_INTERFACE, "prose_player_leave_other");
    public static final string_id SID_PLAYER_BROKE = new string_id(STF_INTERFACE, "player_broke");
    public static final string_id SID_BET_FAILED = new string_id(STF_INTERFACE, "bet_failed");
    public static final string_id SID_BET_FAILED_AMOUNT = new string_id(STF_INTERFACE, "bet_failed_amt");
    public static final string_id PROSE_PAYOUT = new string_id(STF_INTERFACE, "prose_payout");
    public static final string_id SID_PLACE_BETS = new string_id(STF_INTERFACE, "place_bets");
    public static final string_id PROSE_STARTING_IN = new string_id(STF_INTERFACE, "prose_starting_in");
    public static final string_id FLY_WINNER = new string_id(STF_INTERFACE, "fly_winner");
    public static final String STF_GAME_N = "gambling/game_n";
    public static boolean initializeTable(obj_id table, String gameType) throws InterruptedException
    {
        if (!isIdValid(table) || gameType == null || gameType.equals(""))
        {
            return false;
        }
        dictionary row = dataTableGetRow(TBL_GAME_PARAMS, gameType);
        if (row == null || row.isEmpty())
        {
            return false;
        }
        boolean litmus = true;
        litmus &= setObjVar(table, VAR_TABLE_TYPE, gameType);
        litmus &= setObjVar(table, VAR_TABLE_PLAYER_LIMIT_MIN, row.getInt(COL_PLAYERS_MIN));
        litmus &= setObjVar(table, VAR_TABLE_PLAYER_LIMIT_MAX, row.getInt(COL_PLAYERS_MAX));
        litmus &= setObjVar(table, VAR_TABLE_BET_ANTE, row.getInt(COL_BET_ANTE));
        litmus &= setObjVar(table, VAR_TABLE_BET_MIN, row.getInt(COL_BET_MIN));
        litmus &= setObjVar(table, VAR_TABLE_BET_MAX, row.getInt(COL_BET_MAX));
        return litmus;
    }
    public static void addTablePlayer(obj_id table, obj_id player, String params) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player))
        {
            return;
        }
        if (!hasObjVar(table, gambling.VAR_GAMBLE_BASE) || utils.hasScriptVar(player, SCRIPTVAR_GAMBLING))
        {
            return;
        }
        String gameType = getStringObjVar(table, VAR_TABLE_TYPE);
        if (gameType == null || gameType.equals(""))
        {
            return;
        }
        int totalMoney = getTotalMoney(player);
        if (totalMoney < 1)
        {
            sendSystemMessage(player, gambling.SID_PLAYER_BROKE);
            return;
        }
        int max = getIntObjVar(table, VAR_TABLE_PLAYER_LIMIT_MAX);
        Vector players = getResizeableObjIdArrayObjVar(table, VAR_TABLE_PLAYERS);
        if (players != null && players.size() > 0)
        {
            if (utils.getElementPositionInArray(players, player) > -1)
            {
                return;
            }
            if (max > 0)
            {
                if (players.size() >= max)
                {
                    prose_package ppFull = prose.getPackage(gambling.PROSE_STATION_FULL, table);
                    sendSystemMessageProse(player, ppFull);
                    return;
                }
            }
        }
        players = utils.addElement(players, player);
        if (players == null || players.size() == 0)
        {
            return;
        }
        if (setObjVar(table, VAR_TABLE_PLAYERS, players))
        {
            utils.setScriptVar(player, SCRIPTVAR_GAMBLING, table);
            string_id sid_type = new string_id(STF_GAME_N, gameType);
            if (sid_type != null)
            {
                prose_package ppJoin = prose.getPackage(PROSE_PLAYER_JOIN, sid_type);
                sendSystemMessageProse(player, ppJoin);
            }
            else 
            {
                prose_package ppJoin = prose.getPackage(PROSE_PLAYER_JOIN, gameType);
                sendSystemMessageProse(player, ppJoin);
            }
            CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " has joined (" + table + ") " + utils.getStringName(table));
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(table, "handlePlayerAdded", d, 1f, false);
        }
    }
    public static void removeTablePlayer(obj_id table, obj_id player, String params) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player))
        {
            return;
        }
        utils.removeScriptVar(player, SCRIPTVAR_GAMBLING);
        if (!hasObjVar(table, VAR_GAMBLE_BASE))
        {
            return;
        }
        String gameType = getStringObjVar(table, VAR_TABLE_TYPE);
        if (gameType == null || gameType.equals(""))
        {
            return;
        }
        Vector players = getResizeableObjIdArrayObjVar(table, VAR_TABLE_PLAYERS);
        if (players == null || players.size() == 0)
        {
            return;
        }
        int idx = utils.getElementPositionInArray(players, player);
        if (idx == -1)
        {
            return;
        }
        players = utils.removeElementAt(players, idx);
        if (players == null || players.size() == 0)
        {
            removeObjVar(table, VAR_TABLE_PLAYERS);
        }
        else 
        {
            setObjVar(table, VAR_TABLE_PLAYERS, players);
        }
        string_id sid_type = new string_id(STF_GAME_N, gameType);
        if (sid_type != null)
        {
            prose_package ppJoin = prose.getPackage(PROSE_PLAYER_LEAVE, sid_type);
            sendSystemMessageProse(player, ppJoin);
        }
        else 
        {
            prose_package ppJoin = prose.getPackage(PROSE_PLAYER_LEAVE, gameType);
            sendSystemMessageProse(player, ppJoin);
        }
        CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " has left (" + table + ") " + utils.getStringName(table));
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(table, "handlePlayerRemoved", d, 1f, false);
    }
    public static boolean isTablePlayer(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player))
        {
            return false;
        }
        obj_id[] players = getObjIdArrayObjVar(table, VAR_TABLE_PLAYERS);
        if (players == null || players.length == 0)
        {
            return false;
        }
        return (utils.getElementPositionInArray(players, player) > -1);
    }
    public static void placeBet(obj_id table, obj_id player, String params) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player) || params == null || params.equals(""))
        {
            return;
        }
        if (!isTablePlayer(table, player))
        {
            sendSystemMessageTestingOnly(player, "You cannot bet on a table you have not joined!");
            return;
        }
        if (!utils.hasScriptVar(table, VAR_TABLE_BET_ACCEPT))
        {
            sendSystemMessageTestingOnly(player, "The table is not currently accepting bets!");
            return;
        }
        String[] tmp = split(params, ' ');
        int amt = utils.stringToInt(tmp[0]);
        if (amt < 1)
        {
            sendSystemMessage(player, SID_BET_FAILED_AMOUNT);
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(table, "handleBetFailed", d, 0, false);
            return;
        }
        int maxBet = getIntObjVar(table, VAR_TABLE_BET_MAX);
        if (maxBet > 0 && amt > maxBet)
        {
            sendSystemMessageTestingOnly(player, "The maximum bet for this station is " + maxBet + " credits.");
            return;
        }
        dictionary d = new dictionary();
        if (params.length() > tmp[0].length() + 1)
        {
            String arg = params.substring(tmp[0].length() + 1);
            d.put("arg", toLower(arg));
        }
        money.requestPayment(player, table, amt, "handleBetPlaced", d);
    }
    public static void refundAbortedGame(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return;
        }
    }
    public static int parsePaySchedule(obj_id table, obj_id player, int[] results, int amt, String tbl) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player) || amt < 1 || tbl == null || tbl.equals(""))
        {
            return -1;
        }
        if (results == null || results.length == 0)
        {
            return -1;
        }
        String result = "";
        for (int i = 0; i < results.length; i++)
        {
            result += Integer.toString(results[i]);
        }
        String[] schedule = dataTableGetStringColumnNoDefaults(tbl, "SCHEDULE");
        if (schedule == null || schedule.length == 0)
        {
            return 0;
        }
        int idx = utils.getElementPositionInArray(schedule, result);
        if (idx == -1)
        {
            for (int i = 0; i < schedule.length; i++)
            {
                if (schedule[i].startsWith("*"))
                {
                    String tmp = schedule[i].substring(1);
                    if (tmp != null && !tmp.equals(""))
                    {
                        String[] opt = split(tmp, '|');
                        if (opt != null && opt.length > 0)
                        {
                            Vector perms = new Vector();
                            perms.setSize(0);
                            for (int x = 0; x < opt.length; x++)
                            {
                                for (int y = 0; y < opt.length; y++)
                                {
                                    for (int z = 0; z < opt.length; z++)
                                    {
                                        perms = utils.addElement(perms, opt[x] + opt[y] + opt[z]);
                                    }
                                }
                            }
                            idx = utils.getElementPositionInArray(perms, result);
                            if (idx > -1)
                            {
                                LOG("gambling", "** found valid combination!");
                                LOG("gambling", "result = " + result + " combo = " + ((String)perms.get(idx)));
                                return getPayoutValue(tbl, i, amt);
                            }
                        }
                    }
                }
            }
        }
        else 
        {
            int max = getIntObjVar(table, VAR_TABLE_BET_MAX);
            if (amt >= max)
            {
                return dataTableGetInt(tbl, idx, "PAYOUT_MAX");
            }
            else 
            {
                return getPayoutValue(tbl, idx, amt);
            }
        }
        return 0;
    }
    public static int getPayoutValue(String tbl, int idx, int amt) throws InterruptedException
    {
        if (tbl == null || tbl.equals(""))
        {
            return -1;
        }
        if (idx < 0 || amt < 0)
        {
            return -1;
        }
        int base = dataTableGetInt(tbl, idx, "PAYOUT_BASE");
        int multiplier = dataTableGetInt(tbl, idx, "COIN_MULTIPLIER");
        return base * amt * multiplier;
    }
    public static Vector calculateReelOdds(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        String tableType = getStringObjVar(table, VAR_TABLE_TYPE);
        if (tableType == null || tableType.equals(""))
        {
            return null;
        }
        String tbl = "datatables/gambling/slot/" + tableType + ".iff";
        int[] virtualStops = dataTableGetIntColumnNoDefaults(tbl, "VIRTUAL");
        if (virtualStops == null || virtualStops.length == 0)
        {
            return null;
        }
        Vector reelOdds = new Vector();
        reelOdds.setSize(0);
        for (int i = 0; i < virtualStops.length; i++)
        {
            for (int x = 0; x < virtualStops[i]; x++)
            {
                reelOdds = utils.addElement(reelOdds, i + 1);
            }
        }
        return reelOdds;
    }
    public static int getGamePlayerIndex(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player))
        {
            return -1;
        }
        obj_id[] playerIds = getObjIdArrayObjVar(table, VAR_GAME_PLAYERS_IDS);
        if (playerIds == null || playerIds.length == 0)
        {
            return -1;
        }
        return utils.getElementPositionInArray(playerIds, player);
    }
}
