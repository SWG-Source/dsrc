package script.gambling.table;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.gambling;
import script.library.prose;
import script.library.utils;
import script.library.sui;
import script.library.player_structure;

public class pazaak extends script.gambling.base.table
{
    public pazaak()
    {
    }
    public static final int[] PLAY_DECK = 
    {
        1,
        1,
        2,
        2,
        3,
        3,
        4,
        4,
        5,
        5,
        6,
        6,
        7,
        7,
        8,
        8,
        9,
        9,
        10,
        10
    };
    public static final int[] DEFAULT_SIDE_DECK = 
    {
        1,
        1,
        2,
        2,
        3,
        3,
        4,
        4,
        5,
        5
    };
    public static final String SCRIPT_VAR_HAND = "gambling.game.players.hand";
    public static final String SCRIPT_VAR_BOARD = "gambling.game.players.board";
    public static final String SCRIPT_VAR_DECK = "gambling.game.deck";
    public static final String SCRIPT_VAR_TURN = "gambling.game.turn.player";
    public static final String SCRIPT_VAR_TURN_PID = "gambling.game.turn.pid";
    public static final String SCRIPT_VAR_ACTIVE_PLAYERS = "gambling.game.players.active";
    public static final String SCRIPT_VAR_POT_TOTAL = "gambling.game.pot_total";
    public static final String VAR_HOUSE_RAKE = "gambling.rake";
    public static final string_id SID_MAX_BET = new string_id("gambling/pazaak", "max_bet");
    public static final string_id SID_MIN_BET = new string_id("gambling/pazaak", "min_bet");
    public static final string_id SID_BET_REFUND_OVERBET = new string_id("gambling/pazaak", "bet_refund_overbet");
    public static final string_id SID_NOT_ENOUGH_PLAYERS = new string_id("gambling/pazaak", "not_enough_players");
    public static final string_id SID_PAZAAK_BEGIN = new string_id("gambling/pazaak", "pazaak_begin");
    public static final string_id SID_PAZAAK_ERROR = new string_id("gambling/pazaak", "pazaak_error");
    public static final string_id SID_NEXT_TURN_SELF = new string_id("gambling/pazaak", "next_turn_self");
    public static final string_id SID_NEXT_TURN_OTHER = new string_id("gambling/pazaak", "next_turn_other");
    public static final string_id SID_DRAW_CARD_SELF = new string_id("gambling/pazaak", "draw_card_self");
    public static final string_id SID_DRAW_CARD_OTHER = new string_id("gambling/pazaak", "draw_card_other");
    public static final string_id SID_PLAY_CARD_SELF = new string_id("gambling/pazaak", "play_card_self");
    public static final string_id SID_PLAY_CARD_OTHER = new string_id("gambling/pazaak", "play_card_other");
    public static final string_id SID_PLAYER_BUSTED_SELF = new string_id("gambling/pazaak", "player_busted_self");
    public static final string_id SID_PLAYER_BUSTED_OTHER = new string_id("gambling/pazaak", "player_busted_other");
    public static final string_id SID_PLAYER_STAND_SELF = new string_id("gambling/pazaak", "player_stand_self");
    public static final string_id SID_PLAYER_STAND_OTHER = new string_id("gambling/pazaak", "player_stand_other");
    public static final string_id SID_GAME_OVER = new string_id("gambling/pazaak", "game_over");
    public static final string_id SID_PLAYER_WIN_SELF = new string_id("gambling/pazaak", "player_win_self");
    public static final string_id SID_PLAYER_WIN_OTHER = new string_id("gambling/pazaak", "player_win_other");
    public static final string_id SID_PLAYER_INACTIVE_SELF = new string_id("gambling/pazaak", "player_inactive_self");
    public static final string_id SID_PLAYER_INACTIVE_OTHER = new string_id("gambling/pazaak", "player_inactive_other");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        reseed(getGameTime());
        obj_id structure = player_structure.getStructure(self);
        int table_balance = getBankBalance(self);
        if (isIdValid(structure))
        {
            transferBankCreditsTo(self, structure, table_balance, "noHandler", "noHandler", new dictionary());
        }
        return super.OnInitialize(self);
    }
    public int handleBetPlaced(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "pazaak::handleBetPlaced");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int ret = params.getInt(money.DICT_CODE);
        int amt = params.getInt(money.DICT_TOTAL);
        if (ret == money.RET_FAIL || amt < 1)
        {
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(self, "handleBetFailed", d, 0, false);
            return SCRIPT_CONTINUE;
        }
        boolean bet_success = true;
        String arg = params.getString("arg");
        String scriptvar_pid = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(self, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(self, scriptvar_pid);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(self, scriptvar_pid);
        }
        int minBet = getIntObjVar(self, gambling.VAR_TABLE_BET_MIN);
        if (minBet > 0 && amt < minBet)
        {
            sendSystemMessageTestingOnly(player, " The minimum bet for this station is " + minBet + " credits.");
            bet_success = false;
        }
        int playerIdx = gambling.getGamePlayerIndex(self, player);
        if (playerIdx < 0)
        {
            bet_success = false;
        }
        String ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet";
        if (hasObjVar(self, ovpath))
        {
            amt += getIntObjVar(self, ovpath);
            int maxBet = getIntObjVar(self, gambling.VAR_TABLE_BET_MAX);
            if (maxBet > 0 && amt > maxBet)
            {
                int refund = amt - maxBet;
                prose_package pp = prose.getPackage(SID_MAX_BET, maxBet);
                sendSystemMessageProse(player, pp);
                pp = prose.getPackage(SID_BET_REFUND_OVERBET, refund);
                sendSystemMessageProse(player, pp);
                transferBankCreditsTo(self, player, refund, "noHandler", "noHandler", new dictionary());
                amt = maxBet;
            }
        }
        if (bet_success)
        {
            setObjVar(self, ovpath, amt);
            addToPazaakPot(self, amt);
            obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
            if (players != null && players.length > 0)
            {
                for (int i = 0; i < players.length; i++)
                {
                    LOG("LOG_CHANNEL", "players ->" + players[i]);
                    updateBetSUI(self, players[i]);
                }
            }
        }
        else 
        {
            transferBankCreditsTo(self, player, amt, "noHandler", "noHandler", new dictionary());
            updateBetSUI(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBetVerifiedFailed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::handleBetVerifiedFailed");
        obj_id player = params.getObjId("player");
        if (isIdValid(player) && player.isLoaded())
        {
            LOG("LOG_CHANNEL", player + " -> You have insufficent funds.");
        }
        int amt = params.getInt("amt");
        if (amt > 0)
        {
            transferBankCreditsTo(self, player, amt, "noHandler", "noHandler", new dictionary());
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerRemoved(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return super.handlePlayerRemoved(self, params);
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return super.handlePlayerRemoved(self, params);
        }
        String ovpath = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(self, ovpath))
        {
            int pid = utils.getIntScriptVar(self, ovpath);
            sui.closeSUI(player, pid);
            utils.removeScriptVar(self, ovpath);
        }
        if (!hasObjVar(self, gambling.VAR_TABLE_PLAYERS))
        {
            stopTableGame(self);
        }
        else 
        {
            prose_package pp = prose.getPackage(gambling.PROSE_PLAYER_LEAVE_OTHER, player);
            sendTableMessage(self, pp);
            if (utils.hasScriptVar(self, SCRIPT_VAR_GAME_ACTIVE))
            {
                removeActivePlayer(self, player);
                addToPazaakBoard(self, player, -999);
                int player_idx = gambling.getGamePlayerIndex(self, player);
                String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_idx + ".bet";
                if (hasObjVar(self, bet_path))
                {
                    removeObjVar(self, bet_path);
                }
                sendSystemMessage(player, SID_PLAYER_INACTIVE_SELF);
                pp = prose.getPackage(SID_PLAYER_INACTIVE_OTHER, player, player);
                sendTableMessage(self, pp, player);
                obj_id players[] = getPazaakPlayers(self);
                LOG("LOG_CHANNEL", "removePlayer -- players ->" + players.length);
                if (players != null && players.length == 1)
                {
                    obj_id next_turn = utils.getObjIdScriptVar(self, SCRIPT_VAR_TURN);
                    if (isIdValid(next_turn))
                    {
                        int pid = utils.getIntScriptVar(self, SCRIPT_VAR_TURN_PID);
                        if (pid > -1)
                        {
                            sui.closeSUI(next_turn, pid);
                        }
                    }
                    endPazaakGame(self);
                }
                else 
                {
                    obj_id next_turn = utils.getObjIdScriptVar(self, SCRIPT_VAR_TURN);
                    if (isIdValid(next_turn))
                    {
                        if (next_turn == player)
                        {
                            int pid = utils.getIntScriptVar(self, SCRIPT_VAR_TURN_PID);
                            if (pid > -1)
                            {
                                sui.closeSUI(next_turn, pid);
                            }
                            startNextPazaakTurn(self);
                        }
                    }
                }
            }
            else 
            {
                int bet = getPazaakPlayerBet(self, player);
                if (bet > -1)
                {
                    transferBankCreditsTo(self, player, bet, "noHandler", "noHandler", new dictionary());
                    addToPazaakPot(self, -bet);
                }
                int player_idx = gambling.getGamePlayerIndex(self, player);
                String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_idx + ".bet";
                if (hasObjVar(self, bet_path))
                {
                    removeObjVar(self, bet_path);
                }
            }
        }
        return super.handlePlayerRemoved(self, params);
    }
    public int msgPlayPazaakCard(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            startNextPazaakTurn(self);
            return SCRIPT_CONTINUE;
        }
        if (!validatePlayer(self, player))
        {
            startNextPazaakTurn(self);
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            displayStandUI(self, player);
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected > -1)
        {
            int[] hand = getPazaakHand(self, player);
            if (hand != null && hand.length > 0)
            {
                int card_selected = hand[row_selected];
                playPazaakCardFromHand(self, player, card_selected);
                String card_selected_str = displayPazaakCard(card_selected);
                int board_total = getPazaakBoardTotal(self, player);
                prose_package pp = prose.getPackage(SID_PLAY_CARD_SELF, player, null, card_selected_str, board_total);
                sendSystemMessageProse(player, pp);
                pp = prose.getPackage(SID_PLAY_CARD_OTHER, player, null, card_selected_str, board_total);
                sendTableMessage(self, pp, player);
                if (!verifyPazaakBoard(self, player))
                {
                    startNextPazaakTurn(self);
                    return SCRIPT_CONTINUE;
                }
                updatePazaakBetSUI(self);
            }
        }
        displayStandUI(self, player);
        return SCRIPT_CONTINUE;
    }
    public int msgStandPazaakHand(obj_id self, dictionary params) throws InterruptedException
    {
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            startNextPazaakTurn(self);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            startNextPazaakTurn(self);
            return SCRIPT_CONTINUE;
        }
        if (!validatePlayer(self, player))
        {
            startNextPazaakTurn(self);
            return SCRIPT_CONTINUE;
        }
        if (verifyPazaakBoard(self, player))
        {
            int board_total = getPazaakBoardTotal(self, player);
            prose_package pp = prose.getPackage(SID_PLAYER_STAND_SELF, new string_id("", ""), board_total);
            sendSystemMessageProse(player, pp);
            pp = prose.getPackage(SID_PLAYER_STAND_OTHER, player, player, new string_id("", ""), board_total);
            sendTableMessage(self, pp, player);
            removeActivePlayer(self, player);
            updatePazaakBetSUI(self);
        }
        startNextPazaakTurn(self);
        return SCRIPT_CONTINUE;
    }
    public int msgStartPazaakGame(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, gambling.VAR_GAME_BASE);
        utils.removeScriptVarTree(self, "gambling.game");
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                updateBetSUI(self, players[i]);
            }
        }
        if (players.length < getIntObjVar(self, gambling.VAR_TABLE_PLAYER_LIMIT_MIN))
        {
            utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
            sendTableMessage(self, SID_NOT_ENOUGH_PLAYERS);
        }
        else 
        {
            startTableBetting(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void startTableGame(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "hasObjVar ->" + hasObjVar(self, gambling.VAR_GAME_BASE));
        utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        utils.setScriptVar(self, SCRIPT_VAR_GAME_ACTIVE, 1);
        obj_id[] players = getPazaakPlayers(self);
        if (players != null)
        {
            LOG("LOG_CHANNEL", "players ->" + players.length);
            if (players.length < 2)
            {
                sendTableMessage(self, SID_NOT_ENOUGH_PLAYERS);
                if (players != null)
                {
                    for (int i = 0; i < players.length; i++)
                    {
                        int bet_amt = getPazaakPlayerBet(self, players[i]);
                        LOG("LOG_CHANNEL", "startTableGame: " + players[i] + " amt ->" + bet_amt);
                        removePazaakPlayerBet(self, players[i]);
                        if (bet_amt > 0)
                        {
                            transferBankCreditsTo(self, players[i], bet_amt, "noHandler", "noHandler", new dictionary());
                        }
                    }
                }
            }
            for (int i = 0; i < players.length; i++)
            {
                int[] hand = drawPazaakHand(self, players[i]);
            }
        }
        sendTableMessage(self, SID_PAZAAK_BEGIN);
        LOG("LOG_CHANNEL", "players ->" + players);
        LOG("LOG_CHANNEL", "hasObjVar ->" + hasObjVar(self, gambling.VAR_GAME_BASE));
        utils.setScriptVar(self, SCRIPT_VAR_ACTIVE_PLAYERS, players);
        obj_id player = getNextPlayerTurn(self);
        if (player == null)
        {
            sendTableMessage(self, SID_PAZAAK_ERROR);
            stopTableGame(self);
        }
        for (int i = 0; i < players.length; i++)
        {
            updateBetSUI(self, players[i]);
        }
        startPazaakTurn(self, player);
        return;
    }
    public void showBetUi(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player))
        {
            return;
        }
        String gameType = getStringObjVar(self, gambling.VAR_TABLE_TYPE);
        if (gameType == null || gameType.equals(""))
        {
            return;
        }
        String title = "@gambling/game_n:" + gameType;
        String prompt = "The following is a summary of current table bets...\n\n";
        prompt += "Use /bet <amount> to wager.\n\n";
        prompt += "Bet Per Hand : " + getIntObjVar(self, gambling.VAR_TABLE_BET_MIN) + "\n";
        prompt += "Cash : " + getCashBalance(player) + "\n";
        prompt += "Bank : " + getBankBalance(player) + "\n";
        prompt += "Total: " + getTotalMoney(player) + "\n";
        prompt += "\nNOTE: if you leave the table after placing a bet, all of your outstanding bets will be forfeit.";
        Vector entries = new Vector();
        entries.setSize(0);
        int total = 0;
        if (utils.hasScriptVar(self, SCRIPT_VAR_TURN))
        {
            obj_id turn_player = utils.getObjIdScriptVar(self, SCRIPT_VAR_TURN);
            if (isIdValid(turn_player))
            {
                if (turn_player == player)
                {
                    entries = utils.addElement(entries, "It is your turn.");
                }
                else 
                {
                    entries = utils.addElement(entries, "It is " + getFirstName(turn_player) + "'s turn.");
                }
            }
        }
        obj_id[] bet_players = getObjIdArrayObjVar(self, gambling.VAR_GAME_PLAYERS_IDS);
        if (bet_players != null && bet_players.length > 0)
        {
            if (utils.hasScriptVar(self, SCRIPT_VAR_GAME_ACTIVE))
            {
                for (int i = 0; i < bet_players.length; i++)
                {
                    boolean active = isActivePlayer(self, bet_players[i]);
                    int[] hand = getPazaakHand(self, bet_players[i]);
                    if (hand != null)
                    {
                        if (bet_players[i] == player)
                        {
                            if (active)
                            {
                                entries = utils.addElement(entries, "Your Hand: " + displayPazaakCards(hand));
                            }
                            else 
                            {
                                entries = utils.addElement(entries, "Your Hand: Standing");
                            }
                        }
                        else 
                        {
                            if (active)
                            {
                                int hand_size = getPazaakHand(self, bet_players[i]).length;
                                entries = utils.addElement(entries, getFirstName(bet_players[i]) + "'s Hand: " + hand_size + " cards");
                            }
                            else 
                            {
                                entries = utils.addElement(entries, getFirstName(bet_players[i]) + "'s Hand: Standing");
                            }
                        }
                    }
                    int[] board = getPazaakBoard(self, bet_players[i]);
                    int board_total = getPazaakBoardTotal(self, bet_players[i]);
                    if (board != null)
                    {
                        if (bet_players[i] == player)
                        {
                            if (board_total <= -99 || (board_total > 20 && !active))
                            {
                                entries = utils.addElement(entries, "Your Board: Out");
                            }
                            else 
                            {
                                entries = utils.addElement(entries, "Your Board: " + displayPazaakCards(board) + " (Total: " + board_total + ")");
                            }
                        }
                        else 
                        {
                            if (board_total <= -99 || (board_total > 20 && !active))
                            {
                                entries = utils.addElement(entries, getFirstName(bet_players[i]) + "'s Board: Out");
                            }
                            else 
                            {
                                entries = utils.addElement(entries, getFirstName(bet_players[i]) + "'s Board: " + displayPazaakCards(board) + " (Total: " + board_total + ")");
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < bet_players.length; i++)
            {
                String betVar = gambling.VAR_GAME_PLAYERS + "." + i + ".bet";
                if (bet_players[i].isLoaded())
                {
                    String entry;
                    if (hasObjVar(self, betVar))
                    {
                        int amt = getIntObjVar(self, betVar);
                        if (bet_players[i] == player)
                        {
                            entry = "Your bet : " + amt;
                        }
                        else 
                        {
                            entry = getFirstName(bet_players[i]) + "'s bet : " + amt;
                        }
                        entries = utils.addElement(entries, entry);
                        total += amt;
                    }
                    else 
                    {
                        if (bet_players[i] == player)
                        {
                            entry = "You : 0";
                        }
                        else 
                        {
                            entry = getFirstName(bet_players[i]) + " : 0";
                        }
                        entries = utils.addElement(entries, entry);
                    }
                }
                entries = utils.alphabetizeStringArray(entries);
                entries = utils.addElement(entries, " ");
            }
        }
        int table_pot = utils.getIntScriptVar(self, SCRIPT_VAR_POT_TOTAL);
        entries = utils.addElement(entries, "Total Bet : " + table_pot);
        int pid = sui.listbox(self, player, prompt, sui.REFRESH_LEAVE_GAME, title, entries, "handleBetUi");
        if (pid == -1)
        {
            sendSystemMessageTestingOnly(player, "The table was unable to create an interface for you.");
            return;
        }
        utils.setScriptVar(self, gambling.VAR_GAME_PLAYERS + "." + player + ".pid", pid);
    }
    public boolean startPazaakTurn(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int drawn_card = drawPazaakCardFromDeck(table, player);
        String drawn_card_str = displayPazaakCard(drawn_card);
        addToPazaakBoard(table, player, drawn_card);
        int board_total = getPazaakBoardTotal(table, player);
        prose_package pp = prose.getPackage(SID_DRAW_CARD_SELF, player, null, drawn_card_str, board_total);
        sendSystemMessageProse(player, pp);
        pp = prose.getPackage(SID_DRAW_CARD_OTHER, player, null, drawn_card_str, board_total);
        sendTableMessage(table, pp, player);
        obj_id[] players = getPazaakPlayers(table);
        if (players == null)
        {
            return false;
        }
        for (int i = 0; i < players.length; i++)
        {
            updateBetSUI(table, players[i]);
        }
        int[] hand = getPazaakHand(table, player);
        if (hand != null && hand.length > 0)
        {
            displayPlayCardUI(table, player);
            return true;
        }
        else 
        {
            if (verifyPazaakBoard(table, player))
            {
                displayStandUI(table, player);
            }
        }
        return true;
    }
    public boolean startNextPazaakTurn(obj_id table) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "startNextPazaakTurn");
        if (!isIdValid(table))
        {
            return false;
        }
        obj_id player = getNextPlayerTurn(table);
        LOG("LOG_CHANNEL", "startNextPazaakTurn -- player ->" + player);
        if (player == null)
        {
            endPazaakGame(table);
            return false;
        }
        else 
        {
            return startPazaakTurn(table, player);
        }
    }
    public boolean endPazaakGame(obj_id table) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "endPazaakGame -- table ->" + table);
        if (!isIdValid(table))
        {
            return false;
        }
        obj_id[] players = getPazaakPlayers(table);
        LOG("LOG_CHANNEL", "endPazaakGame -- players ->" + players);
        if (players != null && players.length > 0)
        {
            int[] board_totals = new int[players.length];
            int winning_total = -99;
            for (int i = 0; i < board_totals.length; i++)
            {
                int total = getPazaakBoardTotal(table, players[i]);
                if (total > winning_total && total < 21)
                {
                    winning_total = total;
                }
                board_totals[i] = total;
            }
            Vector winners = new Vector();
            if (winning_total > -99)
            {
                for (int i = 0; i < players.length; i++)
                {
                    LOG("LOG_CHANNEL", "endGame -- player ->" + players[i] + " board ->" + board_totals[i] + " win ->" + winning_total);
                    if (board_totals[i] == winning_total)
                    {
                        winners.add(players[i]);
                    }
                }
                prose_package pp = prose.getPackage(SID_GAME_OVER, winning_total);
                sendTableMessage(table, pp);
            }
            else 
            {
                winners.add(players[0]);
            }
            if (winners.size() > 0)
            {
                obj_id[] winners_array = new obj_id[winners.size()];
                winners.toArray(winners_array);
                int table_balance = getBankBalance(table);
                int rake = 0;
                if (hasObjVar(table, VAR_HOUSE_RAKE))
                {
                    rake = getIntObjVar(table, VAR_HOUSE_RAKE);
                }
                int pay_out = (int)(table_balance * (100 - rake) / 100) / winners_array.length;
                for (int i = 0; i < winners_array.length; i++)
                {
                    prose_package pp = prose.getPackage(SID_PLAYER_WIN_SELF, pay_out);
                    sendSystemMessageProse(winners_array[i], pp);
                    pp = prose.getPackage(SID_PLAYER_WIN_OTHER, winners_array[i], winners_array[i], pay_out);
                    sendTableMessage(table, pp, winners_array[i]);
                    transferBankCreditsTo(table, winners_array[i], pay_out, "noHandler", "noHandler", new dictionary());
                    table_balance -= pay_out;
                }
                obj_id structure = player_structure.getStructure(table);
                if (isIdValid(structure))
                {
                    transferBankCreditsTo(table, structure, table_balance, "noHandler", "noHandler", new dictionary());
                }
            }
        }
        messageTo(table, "msgStartPazaakGame", null, 10, false);
        return true;
    }
    public int getPazaakPlayerBet(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        if (!isIdValid(player))
        {
            return -1;
        }
        int idx = gambling.getGamePlayerIndex(table, player);
        String betVar = gambling.VAR_GAME_PLAYERS + "." + idx + ".bet";
        LOG("LOG_CHANNEL", "getPazaakPlayerBet: betVar ->" + betVar + " amt ->" + getIntObjVar(table, betVar));
        if (hasObjVar(table, betVar))
        {
            return getIntObjVar(table, betVar);
        }
        else 
        {
            return -1;
        }
    }
    public boolean removePazaakPlayerBet(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int idx = gambling.getGamePlayerIndex(table, player);
        String betVar = gambling.VAR_GAME_PLAYERS + "." + idx + ".bet";
        if (hasObjVar(table, betVar))
        {
            removeObjVar(table, betVar);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public obj_id[] getPazaakPlayers(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        obj_id[] players = getObjIdArrayObjVar(table, gambling.VAR_GAME_PLAYERS_IDS);
        Vector valid_players = new Vector();
        valid_players.setSize(0);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (getPazaakPlayerBet(table, players[i]) > 0)
                {
                    valid_players.add(players[i]);
                }
            }
        }
        obj_id[] _valid_players = new obj_id[0];
        if (valid_players != null)
        {
            _valid_players = new obj_id[valid_players.size()];
            valid_players.toArray(_valid_players);
        }
        return _valid_players;
    }
    public boolean verifyPazaakBoard(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int board_total = getPazaakBoardTotal(table, player);
        if (board_total > 20)
        {
            prose_package pp = prose.getPackage(SID_PLAYER_BUSTED_SELF, new string_id("", ""), board_total);
            sendSystemMessageProse(player, pp);
            pp = prose.getPackage(SID_PLAYER_BUSTED_OTHER, player, player, new string_id("", ""), board_total);
            sendTableMessage(table, pp, player);
            removeActivePlayer(table, player);
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean isActivePlayer(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id[] active_players = utils.getObjIdArrayScriptVar(table, SCRIPT_VAR_ACTIVE_PLAYERS);
        int idx = utils.getElementPositionInArray(active_players, player);
        if (idx == -1)
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean removeActivePlayer(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return true;
        }
        if (!isIdValid(player))
        {
            return true;
        }
        Vector active_players = utils.getResizeableObjIdArrayScriptVar(table, SCRIPT_VAR_ACTIVE_PLAYERS);
        if (active_players != null)
        {
            int idx = utils.getElementPositionInArray(active_players, player);
            if (idx == -1)
            {
                return false;
            }
            else 
            {
                active_players.removeElementAt(idx);
                obj_id[] array = new obj_id[active_players.size()];
                active_players.toArray(array);
                utils.setScriptVar(table, SCRIPT_VAR_ACTIVE_PLAYERS, array);
                return true;
            }
        }
        else 
        {
            return false;
        }
    }
    public boolean validatePlayer(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        if (!gambling.isTablePlayer(table, player) || !player.isLoaded())
        {
            removeActivePlayer(table, player);
            addToPazaakBoard(table, player, -999);
            sendSystemMessage(player, SID_PLAYER_INACTIVE_SELF);
            prose_package pp = prose.getPackage(SID_PLAYER_INACTIVE_OTHER, player, player);
            sendTableMessage(table, pp, player);
            return false;
        }
        return true;
    }
    public int[] getPazaakBets(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        obj_id[] players = getObjIdArrayObjVar(table, gambling.VAR_GAME_PLAYERS_IDS);
        if (players != null && players.length > 0)
        {
            Vector bets = new Vector();
            bets.setSize(0);
            for (int i = 0; i < players.length; i++)
            {
                String betVar = gambling.VAR_GAME_PLAYERS + "." + i + ".bet";
                if (players[i].isLoaded())
                {
                    if (hasObjVar(table, betVar))
                    {
                        int amt = getIntObjVar(table, betVar);
                        if (amt > 0)
                        {
                            bets = utils.addElement(bets, amt);
                        }
                    }
                }
            }
            int[] _bets = new int[0];
            if (bets != null)
            {
                _bets = new int[bets.size()];
                for (int _i = 0; _i < bets.size(); ++_i)
                {
                    _bets[_i] = ((Integer)bets.get(_i)).intValue();
                }
            }
            return _bets;
        }
        else 
        {
            return null;
        }
    }
    public int[] drawPazaakHand(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        if (!isIdValid(player))
        {
            return null;
        }
        Vector default_deck = new Vector();
        default_deck.setSize(0);
        for (int i = 0; i < DEFAULT_SIDE_DECK.length; i++)
        {
            default_deck = utils.addElement(default_deck, DEFAULT_SIDE_DECK[i]);
        }
        int[] hand = new int[4];
        for (int i = 0; i < 4; i++)
        {
            if (default_deck.size() < 1)
            {
                break;
            }
            int rand_idx = rand(0, default_deck.size() - 1);
            int card = ((Integer)default_deck.get(rand_idx)).intValue();
            hand[i] = card;
            default_deck.removeElementAt(rand_idx);
        }
        int player_idx = gambling.getGamePlayerIndex(table, player);
        if (player_idx > -1)
        {
            String objvar_name = SCRIPT_VAR_HAND + "." + player_idx;
            utils.setScriptVar(table, objvar_name, hand);
        }
        return hand;
    }
    public int drawPazaakCardFromDeck(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        if (!isIdValid(player))
        {
            return -1;
        }
        Vector deck = new Vector();
        deck.setSize(0);
        if (utils.hasScriptVar(table, SCRIPT_VAR_DECK))
        {
            deck = utils.getResizeableIntArrayScriptVar(table, SCRIPT_VAR_DECK);
        }
        else 
        {
            for (int i = 0; i < PLAY_DECK.length; i++)
            {
                deck = utils.addElement(deck, PLAY_DECK[i]);
            }
        }
        if (deck == null || deck.size() < 1)
        {
            return -1;
        }
        int rand_idx = rand(0, deck.size() - 1);
        int card = ((Integer)deck.get(rand_idx)).intValue();
        deck.removeElementAt(rand_idx);
        utils.setScriptVar(table, SCRIPT_VAR_DECK, deck);
        return card;
    }
    public int[] getPazaakHand(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        if (!isIdValid(player))
        {
            return null;
        }
        int player_idx = gambling.getGamePlayerIndex(table, player);
        String objvar_name = SCRIPT_VAR_HAND + "." + player_idx;
        if (utils.hasScriptVar(table, objvar_name))
        {
            return utils.getIntArrayScriptVar(table, objvar_name);
        }
        else 
        {
            return null;
        }
    }
    public int[] getPazaakBoard(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        if (!isIdValid(player))
        {
            return null;
        }
        int player_idx = gambling.getGamePlayerIndex(table, player);
        String objvar_name = SCRIPT_VAR_BOARD + "." + player_idx;
        if (utils.hasScriptVar(table, objvar_name))
        {
            return utils.getIntArrayScriptVar(table, objvar_name);
        }
        else 
        {
            return null;
        }
    }
    public int getPazaakBoardTotal(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -999;
        }
        if (!isIdValid(player))
        {
            return -999;
        }
        int[] board = getPazaakBoard(table, player);
        if (board != null)
        {
            int total = 0;
            for (int i = 0; i < board.length; i++)
            {
                total += board[i];
            }
            return total;
        }
        else 
        {
            return -999;
        }
    }
    public int addToPazaakPot(obj_id table, int amt) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        int table_pot = 0;
        if (utils.hasScriptVar(table, SCRIPT_VAR_POT_TOTAL))
        {
            table_pot = utils.getIntScriptVar(table, SCRIPT_VAR_POT_TOTAL);
        }
        table_pot += amt;
        utils.setScriptVar(table, SCRIPT_VAR_POT_TOTAL, table_pot);
        return table_pot;
    }
    public String displayPazaakCards(int[] cards) throws InterruptedException
    {
        if (cards == null || cards.length < 1)
        {
            return null;
        }
        String cards_str = new String();
        for (int i = 0; i < cards.length; i++)
        {
            if (cards[i] >= 0)
            {
                if (cards[i] / 100 > 0)
                {
                    cards_str += "+/- " + cards[i] / 100;
                }
                else 
                {
                    cards_str += "+" + cards[i];
                }
            }
            else 
            {
                cards_str += Integer.toString(cards[i]);
            }
            if (i < cards.length - 1)
            {
                cards_str += ", ";
            }
            else if (i != cards.length - 1)
            {
                cards_str += " ";
            }
        }
        return cards_str;
    }
    public String displayPazaakCard(int card) throws InterruptedException
    {
        int[] cards = new int[1];
        cards[0] = card;
        return displayPazaakCards(cards);
    }
    public String[] displayPazaakCardsAsArray(int[] cards) throws InterruptedException
    {
        if (cards == null || cards.length < 1)
        {
            return null;
        }
        Vector cards_str = new Vector();
        for (int i = 0; i < cards.length; i++)
        {
            if (cards[i] >= 0)
            {
                if (cards[i] / 100 > 0)
                {
                    utils.addElement(cards_str, "+/- " + cards[i] / 100);
                }
                else 
                {
                    utils.addElement(cards_str, "+" + cards[i]);
                }
            }
            else 
            {
                utils.addElement(cards_str, Integer.toString(cards[i]));
            }
        }
        String[] card_array = new String[cards_str.size()];
        cards_str.toArray(card_array);
        return card_array;
    }
    public boolean playPazaakCardFromHand(obj_id table, obj_id player, int card) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int player_idx = gambling.getGamePlayerIndex(table, player);
        String objvar_name = SCRIPT_VAR_HAND + "." + player_idx;
        int[] hand = getPazaakHand(table, player);
        if (hand == null || hand.length < 1)
        {
            return false;
        }
        int card_idx = utils.getElementPositionInArray(hand, card);
        if (card_idx == -1)
        {
            return false;
        }
        Vector new_hand = utils.getResizeableIntArrayScriptVar(table, objvar_name);
        new_hand.removeElementAt(card_idx);
        utils.setScriptVar(table, objvar_name, new_hand);
        addToPazaakBoard(table, player, card);
        return true;
    }
    public boolean addToPazaakBoard(obj_id table, obj_id player, int card) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int player_idx = gambling.getGamePlayerIndex(table, player);
        String objvar_name = SCRIPT_VAR_BOARD + "." + player_idx;
        Vector boards = new Vector();
        boards.setSize(0);
        if (utils.hasScriptVar(table, objvar_name))
        {
            boards = utils.getResizeableIntArrayScriptVar(table, objvar_name);
        }
        boards = utils.addElement(boards, card);
        utils.setScriptVar(table, objvar_name, boards);
        return true;
    }
    public obj_id getNextPlayerTurn(obj_id table) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "pazaak::getNextPlayerTurn");
        if (!isIdValid(table))
        {
            return null;
        }
        obj_id[] active_players = utils.getObjIdArrayScriptVar(table, SCRIPT_VAR_ACTIVE_PLAYERS);
        if (active_players == null || active_players.length == 0)
        {
            return null;
        }
        if (active_players.length == 1)
        {
            obj_id next_player = active_players[0];
            utils.setScriptVar(table, SCRIPT_VAR_TURN, active_players[0]);
            sendSystemMessage(next_player, SID_NEXT_TURN_SELF);
            prose_package pp = prose.getPackage(SID_NEXT_TURN_OTHER, next_player);
            sendTableMessage(table, pp, next_player);
            return next_player;
        }
        obj_id[] players = getPazaakPlayers(table);
        if (players != null && players.length > 0)
        {
            LOG("LOG_CHANNEL", "players ->" + players.length);
            obj_id next_player = obj_id.NULL_ID;
            if (utils.hasScriptVar(table, SCRIPT_VAR_TURN))
            {
                obj_id last_turn = utils.getObjIdScriptVar(table, SCRIPT_VAR_TURN);
                int idx = utils.getElementPositionInArray(players, last_turn);
                if (idx != -1)
                {
                    boolean end_list = false;
                    while (next_player == obj_id.NULL_ID)
                    {
                        idx += 1;
                        if (idx > players.length - 1)
                        {
                            if (end_list)
                            {
                                break;
                            }
                            else 
                            {
                                idx = 0;
                                end_list = true;
                            }
                        }
                        if (isActivePlayer(table, players[idx]))
                        {
                            utils.setScriptVar(table, SCRIPT_VAR_TURN, players[idx]);
                            next_player = players[idx];
                        }
                    }
                }
                else 
                {
                    sendTableMessage(table, SID_PAZAAK_ERROR);
                    stopTableGame(table);
                }
            }
            else 
            {
                int idx = rand(0, players.length - 1);
                utils.setScriptVar(table, SCRIPT_VAR_TURN, players[idx]);
                next_player = players[idx];
            }
            if (isIdValid(next_player))
            {
                sendSystemMessage(next_player, SID_NEXT_TURN_SELF);
                prose_package pp = prose.getPackage(SID_NEXT_TURN_OTHER, next_player);
                sendTableMessage(table, pp, next_player);
                return next_player;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public boolean displayPlayCardUI(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        int[] hand = getPazaakHand(table, player);
        String[] hand_str = new String[1];
        if (hand != null && hand.length > 0)
        {
            hand_str = displayPazaakCardsAsArray(hand);
        }
        else 
        {
            hand_str[0] = "@gambling/pazaak:no_hand_remaining";
        }
        Vector entries = new Vector();
        entries.setSize(0);
        String title = "@gambling/game_n:pazaak";
        String prompt = "Select a card to play.";
        int pid = sui.listbox(table, player, prompt, sui.OK_CANCEL, title, hand_str, "msgPlayPazaakCard");
        utils.setScriptVar(table, SCRIPT_VAR_TURN_PID, pid);
        return true;
    }
    public boolean displayStandUI(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        String title = "@gambling/game_n:pazaak";
        int board_total = getPazaakBoardTotal(table, player);
        String prompt = "You have a board total of " + board_total + ". Do you wish to stand?";
        int pid = sui.msgbox(table, player, prompt, sui.YES_NO, "msgStandPazaakHand");
        utils.setScriptVar(table, SCRIPT_VAR_TURN_PID, pid);
        return true;
    }
    public boolean updatePazaakBetSUI(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        obj_id[] players = getObjIdArrayObjVar(table, gambling.VAR_TABLE_PLAYERS);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                LOG("LOG_CHANNEL", "players ->" + players[i]);
                updateBetSUI(table, players[i]);
            }
        }
        return true;
    }
}
