package script.gambling.base;

import script.dictionary;
import script.library.*;
import script.obj_id;

import java.util.Vector;

public class slot extends script.gambling.base.default_interface
{
    public slot()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int slotBalance = getBankBalance(self);
        if (slotBalance > 0)
        {
            transferBankCreditsToNamedAccount(self, money.ACCT_SLOT_STANDARD, slotBalance, "noHandler", "noHandler", new dictionary());
        }
        removeObjVar(self, gambling.VAR_TABLE_PLAYERS);
        removeObjVar(self, gambling.VAR_GAME_BASE);
        Vector reelOdds = gambling.calculateReelOdds(self);
        if (reelOdds != null && reelOdds.size() > 0)
        {
            utils.setBatchScriptVar(self, gambling.VAR_REEL_ODDS, reelOdds);
        }
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
        showPayoutSchedule(self, player);
        startSlotGame(self, player);
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
        }
        removeObjVar(self, gambling.VAR_GAME_BASE);
        return SCRIPT_CONTINUE;
    }
    public int handleBetPlaced(obj_id self, dictionary params) throws InterruptedException
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
        obj_id nplayer = getObjIdObjVar(self, gambling.VAR_GAME_TURN_PLAYER);
        if (!isIdValid(nplayer))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == nplayer)
        {
            removeObjVar(self, gambling.VAR_GAME_TURN_BASE);
        }
        int ret = params.getInt(money.DICT_CODE);
        int amt = params.getInt(money.DICT_AMOUNT);
        if (ret == money.RET_FAIL || amt < 1)
        {
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(self, "handleBetFailed", d, 0, false);
            return SCRIPT_CONTINUE;
        }
        String pidpath = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(self, pidpath))
        {
            int oldpid = utils.getIntScriptVar(self, pidpath);
            utils.removeScriptVar(self, pidpath);
            sui.closeSUI(player, oldpid);
        }
        CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " bets " + amt + "cr to (" + self + ") " + utils.getStringName(self));
        int balance = getBankBalance(self);
        int max = getIntObjVar(self, gambling.VAR_TABLE_BET_MAX);
        if (balance < max)
        {
            showSlotUi(self, player);
        }
        else 
        {
            spinReels(self, player, amt);
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
    public int handleBetTimer(obj_id self, dictionary params) throws InterruptedException
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
        obj_id nplayer = getObjIdObjVar(self, gambling.VAR_GAME_TURN_PLAYER);
        if (!isIdValid(nplayer))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != nplayer)
        {
            return SCRIPT_CONTINUE;
        }
        int pstamp = params.getInt("stamp");
        int nstamp = getIntObjVar(self, gambling.VAR_GAME_TURN_STAMP);
        if (nstamp != pstamp)
        {
            return SCRIPT_CONTINUE;
        }
        int balance = getBankBalance(self);
        if (balance > 0)
        {
            spinReels(self, player, balance);
        }
        else 
        {
            String svpath = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
            int oldpid = utils.getIntScriptVar(self, svpath);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(self, svpath);
            gambling.removeTablePlayer(self, player, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSlotUi(obj_id self, dictionary params) throws InterruptedException
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
        int balance = getBankBalance(self);
        int bp = sui.getIntButtonPressed(params);
        int max = getIntObjVar(self, gambling.VAR_TABLE_BET_MAX);
        switch (bp)
        {
            case sui.BP_OK:
                if (max > 0)
                {
                    money.requestPayment(player, self, max - balance, "handleBetPlaced", null);
                }
                else
                {
                    money.requestPayment(player, self, getTotalMoney(player), "handleBetPlaced", null);
                }
                break;
            case sui.BP_CANCEL:
                if (balance == 0)
                {
                    gambling.removeTablePlayer(self, player, "");
                }
                else
                {
                    spinReels(self, player, balance);
                }
                break;
            case sui.BP_REVERT:
                money.requestPayment(player, self, 1, "handleBetPlaced", null);
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleReelsSpinning(obj_id self, dictionary params) throws InterruptedException
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
        int[] results = params.getIntArray("results");
        if (results == null || results.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = params.getInt("idx");
        if (idx < 0 || idx >= results.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "alwaysWinner"))
        {
            switch (rand(0, 1))
            {
                case 0:
                    results[idx] = 0;
                    break;
                case 1:
                    Vector reelOdds = utils.getResizeableIntBatchScriptVar(self, gambling.VAR_REEL_ODDS);
                    if (reelOdds == null || reelOdds.size() == 0)
                    {
                        reelOdds = gambling.calculateReelOdds(self);
                        utils.setBatchScriptVar(self, gambling.VAR_REEL_ODDS, reelOdds);
                    }
                    int tmpIdx = rand(0, reelOdds.size() - 1);
                    results[idx] = (Integer) (reelOdds.elementAt(tmpIdx));
                    break;
            }
        }
        else 
        {
            Vector reelOdds = utils.getResizeableIntBatchScriptVar(self, gambling.VAR_REEL_ODDS);
            if (reelOdds == null || reelOdds.size() == 0)
            {
                results[idx] = 0;
            }
            else 
            {
                if (idx == 0)
                {
                    int tmpIdx = rand(0, reelOdds.size() - 1);
                    results[0] = (Integer) (reelOdds.elementAt(tmpIdx));
                }
                else 
                {
                    results[idx] = results[0];
                }
            }
        }
        idx++;
        params.put("results", results);
        params.put("idx", idx);
        String msg = "Slot Machine Pay Line\n";
        msg += "-- ";
        for (int i = 0; i < idx; i++)
        {
            msg += "| " + results[i] + " | ";
        }
        for (int i = idx; i < results.length; i++)
        {
            msg += "| " + getString(gambling.SID_ROLLING) + " | ";
        }
        msg += "--";
        sendSystemMessageTestingOnly(player, msg);
        if (idx >= results.length)
        {
            messageTo(self, "handleParseResults", params, 1f, false);
        }
        else 
        {
            messageTo(self, "handleReelsSpinning", params, 3f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleParseResults(obj_id self, dictionary params) throws InterruptedException
    {
        int slotBalance = getBankBalance(self);
        transferBankCreditsToNamedAccount(self, money.ACCT_SLOT_STANDARD, slotBalance, "noHandler", "noHandler", new dictionary());
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int[] results = params.getIntArray("results");
        if (results == null || results.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = params.getInt("amt");
        if (amt < 1)
        {
            return SCRIPT_CONTINUE;
        }
        String gameType = getStringObjVar(self, gambling.VAR_TABLE_TYPE);
        if (gameType == null || gameType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String tbl = "datatables/gambling/slot/" + gameType + ".iff";
        int payout = gambling.parsePaySchedule(self, player, results, amt, tbl);
        if (payout > 0)
        {
            CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " won: attempting to payout " + amt + "cr");
            dictionary d = new dictionary();
            d.put("player", player);
            d.put("payout", payout);
            transferBankCreditsFromNamedAccount(money.ACCT_SLOT_STANDARD, player, payout, "handleGamblingPayout", "noHandler", d);
        }
        else 
        {
            CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " lost");
            sendSystemMessageTestingOnly(player, "Sorry, you did not win this round. Please try again.");
            messageTo(self, "handleDelayedRestart", params, 3f, false);
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
        sendSystemMessageProse(player, prose.getPackage(gambling.PROSE_PAYOUT, payout));
        CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " receives " + payout + "cr payout from (" + self + ") " + utils.getStringName(self));
        showFlyText(self, gambling.FLY_WINNER, 2f, colors.RED);
        messageTo(self, "handleDelayedRestart", params, 3f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedRestart(obj_id self, dictionary params) throws InterruptedException
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
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
        if (utils.getElementPositionInArray(players, player) > -1)
        {
            startSlotGame(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    private void startSlotGame(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player))
        {
            return;
        }
        int totalMoney = getTotalMoney(player);
        if (totalMoney < 1)
        {
            sendSystemMessage(player, gambling.SID_PLAYER_BROKE);
            return;
        }
        showSlotUi(self, player);
    }
    private void showSlotUi(obj_id self, obj_id player) throws InterruptedException
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
        utils.setScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT, getGameTime());
        String title = "@gambling/game_n:" + gameType;
        String prompt = "Press the button corresponding to the desired action.";
        Vector entries = new Vector();
        entries.setSize(0);
        int slotBalance = getBankBalance(self);
        int playerCash = getCashBalance(player);
        int playerBank = getBankBalance(player);
        int playerTotal = getTotalMoney(player);
        entries = utils.addElement(entries, "Current Bet: " + slotBalance);
        int max = getIntObjVar(self, gambling.VAR_TABLE_BET_MAX);
        if (max < 1)
        {
            entries = utils.addElement(entries, "Max Bet:      " + playerTotal);
        }
        else 
        {
            entries = utils.addElement(entries, "Max Bet:      " + max);
        }
        entries = utils.addElement(entries, "Cash Balance: " + playerCash);
        entries = utils.addElement(entries, "Bank Balance: " + playerBank);
        entries = utils.addElement(entries, "Total Money : " + playerTotal);
        int pid = -1;
        if (slotBalance > 0)
        {
            pid = sui.listbox(self, player, prompt, sui.BET_MAX_BET_ONE_SPIN, title, entries, "handleSlotUi");
        }
        else 
        {
            pid = sui.listbox(self, player, prompt, sui.BET_MAX_BET_ONE_QUIT, title, entries, "handleSlotUi");
        }
        if (pid == -1)
        {
            sendSystemMessageTestingOnly(player, "The slot machine was unable to create an interface for you.");
            return;
        }
        utils.setScriptVar(self, gambling.VAR_GAME_PLAYERS + "." + player + ".pid", pid);
        int now = getGameTime();
        setObjVar(self, gambling.VAR_GAME_TURN_PLAYER, player);
        setObjVar(self, gambling.VAR_GAME_TURN_STAMP, now);
        dictionary d = new dictionary();
        d.put("player", player);
        d.put("stamp", now);
        messageTo(self, "handleBetTimer", d, 60f, false);
    }
    private void spinReels(obj_id self, obj_id player, int amt) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player) || amt < 1)
        {
            return;
        }
        String gameType = getStringObjVar(self, gambling.VAR_TABLE_TYPE);
        if (gameType == null || gameType.equals(""))
        {
            return;
        }
        String tbl = "datatables/gambling/slot/" + gameType + ".iff";
        String[] reel = dataTableGetStringColumnNoDefaults(tbl, "REEL");
        if (reel == null || reel.length == 0)
        {
            sendSystemMessageTestingOnly(player, "This machine appears to be broken. Please try again later.");
            return;
        }
        utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        dictionary d = new dictionary();
        d.put("player", player);
        d.put("amt", amt);
        d.put("results", new int[3]);
        d.put("idx", 0);
        d.put("reel", reel);
        sendSystemMessageTestingOnly(player, "You activate the machine and the reels start spinning...");
        messageTo(self, "handleReelsSpinning", d, 5f, false);
    }
    private void showPayoutSchedule(obj_id self, obj_id player) throws InterruptedException
    {
        String gameType = getStringObjVar(self, gambling.VAR_TABLE_TYPE);
        if (gameType == null || gameType.equals(""))
        {
            return;
        }
        String tbl = "datatables/gambling/slot/" + gameType + ".iff";
        String[] schedule = dataTableGetStringColumnNoDefaults(tbl, "SCHEDULE");
        if (schedule == null || schedule.length == 0)
        {
            return;
        }
        int[] payout_base = dataTableGetIntColumnNoDefaults(tbl, "PAYOUT_BASE");
        if (payout_base == null || payout_base.length == 0)
        {
            return;
        }
        int[] payout_max = dataTableGetIntColumnNoDefaults(tbl, "PAYOUT_MAX");
        if (payout_max == null || payout_max.length == 0)
        {
            return;
        }
        if (schedule.length != payout_base.length || schedule.length != payout_max.length)
        {
            return;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < schedule.length; i++)
        {
            entries = utils.addElement(entries, schedule[i] + " -> base:" + payout_base[i] + " max:" + payout_max[i]);
        }
        if (entries == null || entries.size() == 0)
        {
            return;
        }
        String prompt = "The following is the payout schedule for this slot machine.\n\nLegend:\nXXX: denotes any 3 of the same number\n*X|Y|Z: denotes any combination of the 3 numbers\n";
        sui.listbox(self, player, prompt, sui.OK_ONLY, "Payout Schedule", entries, "noHandler");
    }
}
