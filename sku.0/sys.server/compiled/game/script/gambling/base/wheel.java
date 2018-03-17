package script.gambling.base;

import script.*;
import script.library.*;

import java.util.Vector;

public class wheel extends script.gambling.base.default_interface
{
    public wheel()
    {
    }
    private static final int TIMER_BETTING = 120;
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
        if (!hasObjVar(self, gambling.VAR_GAME_BASE))
        {
            startWheelGame(self);
        }
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
        if (players != null && players.length > 0)
        {
            prose_package ppJoinOther = prose.getPackage(gambling.PROSE_PLAYER_JOIN_OTHER, player);
            for (obj_id player1 : players) {
                if (player1 != player) {
                    sendSystemMessageProse(player1, ppJoinOther);
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
                    showBetUi(self, player);
                }
            }
            int timeLeft = utils.getIntScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT) - getGameTime();
            prose_package ppBetTime = prose.getPackage(gambling.PROSE_STARTING_IN, timeLeft);
            sendSystemMessageProse(player, ppBetTime);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Please wait a moment for next game to begin.");
        }
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
        if (!hasObjVar(self, gambling.VAR_TABLE_PLAYERS))
        {
            stopWheelGame(self);
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
            spinWheel(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGamblingPayout(obj_id self, dictionary params) throws InterruptedException
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
        int payout = params.getInt("payout");
        CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " receives wheel payout for " + payout + " from (" + self + ") " + utils.getStringName(self));
        sendSystemMessageProse(player, prose.getPackage(gambling.PROSE_PAYOUT, payout));
        showFlyText(player, gambling.FLY_WINNER, 1f, colors.RED);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedRestart(obj_id self, dictionary params) throws InterruptedException
    {
        startWheelGame(self);
        return SCRIPT_CONTINUE;
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
        String prompt = "The following is a summary of your current bets...\n\nUse /bet <amount> <1-36,0,00,red,black,odd,even,high,low> to wager.\n";
        prompt += "Example:  '/bet 5 black' to wager 5 credits on black\n\n";
        prompt += "Cash : " + getCashBalance(player) + "\n";
        prompt += "Bank : " + getBankBalance(player) + "\n";
        prompt += "Total: " + getTotalMoney(player) + "\n";
        prompt += "\nNOTE: if you leave the table after placing a bet, all of your outstanding bets will be forfeit.";
        Vector entries = new Vector();
        entries.setSize(0);
        int total = 0;
        int playerIdx = gambling.getGamePlayerIndex(self, player);
        if (playerIdx == -1)
        {
            return;
        }
        String betVar = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet";
        if (hasObjVar(self, betVar))
        {
            obj_var_list ovl = getObjVarList(self, betVar);
            if (ovl != null)
            {
                int numItems = ovl.getNumItems();
                obj_var ov;
                String name;
                String entry;
                for (int i = 0; i < numItems; i++)
                {
                    ov = ovl.getObjVar(i);
                    name = ov.getName();
                    int val = ov.getIntData();
                    entry = name + ":";
                    for (int x = name.length(); x < 8; x++)
                    {
                        entry += " ";
                    }
                    entry += Integer.toString(val);
                    entries = utils.addElement(entries, entry);
                    total += val;
                }
                entries = utils.alphabetizeStringArray(entries);
                entries = utils.addElement(entries, " ");
            }
        }
        entries = utils.addElement(entries, "Total Bet : " + total);
        int pid = sui.listbox(self, player, prompt, sui.REFRESH_LEAVE_GAME, title, entries, "handleBetUi");
        if (pid == -1)
        {
            sendSystemMessageTestingOnly(player, "The wheel was unable to create an interface for you.");
            return;
        }
        utils.setScriptVar(self, gambling.VAR_GAME_PLAYERS + "." + player + ".pid", pid);
    }
    public void startWheelGame(obj_id self) throws InterruptedException
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
    public void stopWheelGame(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        removeObjVar(self, gambling.VAR_GAME_BASE);
    }
    private void spinWheel(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return;
        }
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_GAME_PLAYERS_IDS);
        if (players == null || players.length == 0)
        {
            return;
        }
        utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        for (obj_id player : players) {
            sendSystemMessage(player, new string_id(gambling.STF_INTERFACE, "wheel_spin"));
        }
        dictionary d = new dictionary();
        d.put("cnt", 3);
        messageTo(self, "handleWheelSpinning", d, 10f, false);
    }
}
