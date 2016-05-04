package script.gambling.base;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.prose_package;
import script.string_id;

import java.util.Vector;

public class table extends script.gambling.base.default_interface
{
    public table()
    {
    }
    private static final int TIMER_BETTING = 60;
    protected static final String SCRIPT_VAR_GAME_ACTIVE = "gambling.game.active";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, gambling.VAR_TABLE_PLAYERS);
        removeObjVar(self, gambling.VAR_GAME_BASE);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerAdded(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_GAME_ACTIVE))
        {
            obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
            if (players != null && players.length > 0)
            {
                prose_package ppJoinOther = prose.getPackage(gambling.PROSE_PLAYER_JOIN_OTHER, player);
                for (obj_id player1 : players) {
                    if (player1 != player) {
                        sendSystemMessageProse(player1, ppJoinOther);
                    }
                }
                if (!utils.hasScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT))
                {
                    if (players.length >= getIntObjVar(self, gambling.VAR_TABLE_PLAYER_LIMIT_MIN))
                    {
                        startTableBetting(self);
                    }
                }
            }
            if (utils.hasScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT))
            {
                if (players != null && players.length > 1)
                {
                    sendSystemMessage(player, gambling.SID_PLACE_BETS);
                }
                Vector gamePlayers = getResizeableObjIdArrayObjVar(self, gambling.VAR_GAME_PLAYERS_IDS);
                if (gamePlayers != null && gamePlayers.size() > 0)
                {
                    if (utils.getElementPositionInArray(gamePlayers, player) == -1)
                    {
                        gamePlayers = utils.addElement(gamePlayers, player);
                        setObjVar(self, gambling.VAR_GAME_PLAYERS_IDS, gamePlayers);
                    }
                }
                int timeLeft = utils.getIntScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT) - getGameTime();
                prose_package ppBetTime = prose.getPackage(gambling.PROSE_STARTING_IN, timeLeft);
                sendSystemMessageProse(player, ppBetTime);
            }
            else 
            {
                int players_needed = getIntObjVar(self, gambling.VAR_TABLE_PLAYER_LIMIT_MIN) - players.length;
                if (players_needed == 1)
                {
                    sendSystemMessageTestingOnly(player, "The game needs 1 more player before it can begin.");
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "The game needs " + players_needed + " more players before it can begin.");
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "A game is currently in progress. You must wait until it has ended.");
        }
        showBetUi(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerRemoved(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String ovpath = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(self, ovpath))
        {
            int pid = utils.getIntScriptVar(self, ovpath);
            sui.closeSUI(player, pid);
            utils.removeScriptVar(self, ovpath);
        }
        int player_idx = gambling.getGamePlayerIndex(self, player);
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_idx + ".bet";
        if (hasObjVar(self, bet_path))
        {
            removeObjVar(self, bet_path);
        }
        if (!hasObjVar(self, gambling.VAR_TABLE_PLAYERS))
        {
            stopTableGame(self);
        }
        else 
        {
            obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
            if (players != null && players.length > 0)
            {
                prose_package ppLeftOther = prose.getPackage(gambling.PROSE_PLAYER_LEAVE_OTHER, player);
                for (obj_id player1 : players) {
                    sendSystemMessageProse(player1, ppLeftOther);
                }
            }
            if (players.length < getIntObjVar(self, gambling.VAR_TABLE_PLAYER_LIMIT_MIN))
            {
                if (!utils.hasScriptVar(self, SCRIPT_VAR_GAME_ACTIVE))
                {
                    utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
                    int players_needed = getIntObjVar(self, gambling.VAR_TABLE_PLAYER_LIMIT_MIN) - players.length;
                    if (players_needed == 1)
                    {
                        sendSystemMessageTestingOnly(player, "The game needs 1 more player before it can begin.");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "The game needs " + players_needed + " more players before it can begin.");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBetFailed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, gambling.SID_BET_FAILED);
        return SCRIPT_CONTINUE;
    }
    public int handleRequestUpdatedUI(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String ovpath = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(self, ovpath))
        {
            int pid = utils.getIntScriptVar(self, ovpath);
            sui.closeSUI(player, pid);
        }
        showBetUi(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleBetUi(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            queueCommand(player, (947362646), self, "", COMMAND_PRIORITY_DEFAULT);
            return SCRIPT_CONTINUE;
        }
        showBetUi(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleBetTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_GAME_PLAYERS_IDS);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int now = getGameTime();
        int stamp = utils.getIntScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        int dstamp = params.getInt("stamp");
        if (stamp != dstamp)
        {
            return SCRIPT_CONTINUE;
        }
        int diff = stamp - now;
        if (diff > 0)
        {
            prose_package ppTimeLeft = prose.getPackage(gambling.PROSE_STARTING_IN, diff);
            for (obj_id player : players) {
                sendSystemMessageProse(player, ppTimeLeft);
            }
        }
        if (diff > 30)
        {
            messageTo(self, "handleBetTimer", params, 30, false);
        }
        else if (diff <= 30 && diff >= 5)
        {
            messageTo(self, "handleBetTimer", params, 5, false);
        }
        else 
        {
            startTableGame(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void showBetUi(obj_id self, obj_id player) throws InterruptedException
    {
    }
    protected void startTableBetting(obj_id self) throws InterruptedException
    {
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
        if (players == null || players.length == 0)
        {
            return;
        }
        setObjVar(self, gambling.VAR_GAME_PLAYERS_IDS, players);
        int stampTime = getGameTime() + TIMER_BETTING;
        utils.setScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT, stampTime);
        for (obj_id player : players) {
            sendSystemMessage(player, gambling.SID_PLACE_BETS);
            showBetUi(self, player);
        }
        dictionary d = new dictionary();
        d.put("stamp", stampTime);
        messageTo(self, "handleBetTimer", d, 30f, false);
    }
    public void startTableGame(obj_id self) throws InterruptedException
    {
    }
    protected void stopTableGame(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        removeObjVar(self, gambling.VAR_GAME_BASE);
    }
    protected boolean updateBetSUI(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player))
        {
            return false;
        }
        String scriptvar_pid = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(table, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(table, scriptvar_pid);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(table, scriptvar_pid);
        }
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(table, "handleRequestUpdatedUI", d, 0f, false);
        return true;
    }
    public boolean closeBetSUI(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table) || !isIdValid(player))
        {
            return false;
        }
        String scriptvar_pid = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(table, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(table, scriptvar_pid);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(table, scriptvar_pid);
        }
        return true;
    }
    protected boolean sendTableMessage(obj_id table, prose_package pp, obj_id ommit_player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (pp == null)
        {
            return false;
        }
        obj_id[] players = getObjIdArrayObjVar(table, gambling.VAR_TABLE_PLAYERS);
        if (players != null)
        {
            for (obj_id player : players) {
                if (player != ommit_player) {
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        else 
        {
            return false;
        }
        return true;
    }
    protected boolean sendTableMessage(obj_id table, prose_package pp) throws InterruptedException
    {
        return sendTableMessage(table, pp, null);
    }
    public boolean sendTableMessage(obj_id table, string_id message, obj_id ommit_player) throws InterruptedException
    {
        return sendTableMessage(table, prose.getPackage(message, table), ommit_player);
    }
    protected boolean sendTableMessage(obj_id table, string_id message) throws InterruptedException
    {
        return sendTableMessage(table, prose.getPackage(message, table), null);
    }
}
