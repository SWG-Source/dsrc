package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.colors;
import java.util.StringTokenizer;
import script.library.utils;
import script.library.sui;
import java.lang.Math;
import script.library.prose;

public class lottery_droid extends script.base_script
{
    public lottery_droid()
    {
    }
    public static final String DATATABLE = "datatables/event_perk/lottery_setup_data.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "storytellerid"))
        {
            messageTo(self, "cleanupProp", null, 3, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event_perk.lottery.initialized", 0);
        setObjVar(self, "event_perk.lottery.ticket_price", 0);
        setObjVar(self, "event_perk.lottery.payout_percent", 0.0f);
        setObjVar(self, "event_perk.lottery.game_duration", 0);
        setObjVar(self, "event_perk.lottery.winner_paid", 0);
        setObjVar(self, "event_perk.lottery.owner_paid", 0);
        setObjVar(self, "event_perk.no_redeed", 1);
        float terminalRegistration = 999999;
        setObjVar(self, "event_perk.terminal_registration", terminalRegistration);
        String me = getName(self);
        String[] playerNameList = 
        {
            me
        };
        obj_id[] playerOIDList = 
        {
            self
        };
        setObjVar(self, "event_perk.lottery.player_name_list", playerNameList);
        setObjVar(self, "event_perk.lottery.player_OID_list", playerOIDList);
        setObjVar(self, "event_perk.lottery.player_count", 1);
        persistObject(self);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "storytellerid"))
        {
            utils.setScriptVar(self, "cleaningUp", true);
            messageTo(self, "cleanupProp", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "lotteryTimerPing", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "cleaningUp"))
        {
            return SCRIPT_CONTINUE;
        }
        int initialized = getIntObjVar(self, "event_perk.lottery.initialized");
        int winnerPaid = getIntObjVar(self, "event_perk.lottery.winner_paid");
        int ownerPaid = getIntObjVar(self, "event_perk.lottery.owner_paid");
        obj_id owner = getObjIdObjVar(self, "storytellerid");
        obj_id winner = getObjIdObjVar(self, "event_perk.lottery.winner_OID");
        int duration = getIntObjVar(self, "event_perk.lottery.game_duration");
        float payoutPercent = getFloatObjVar(self, "event_perk.lottery.payout_percent");
        int ticketCost = getIntObjVar(self, "event_perk.lottery.ticket_price");
        if (initialized == 0 && owner == player)
        {
            int menu3 = mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("event_perk", "lottery_menu_setup"));
            int menu4 = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("event_perk", "lottery_menu_add_creds"));
            int menu5 = mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id("event_perk", "lottery_menu_instructions"));
            if (duration > 1 || payoutPercent > 40 || ticketCost > 0)
            {
                int menu7 = mi.addRootMenu(menu_info_types.SERVER_MENU7, new string_id("event_perk", "lottery_menu_start_game"));
            }
        }
        if (initialized == 1)
        {
            int menu6_1 = mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id("event_perk", "lottery_menu_lottery_info"));
            int menu8 = mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("event_perk", "lottery_menu_register"));
        }
        if (initialized == 1 && owner == player)
        {
            int menu4_1 = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("event_perk", "lottery_menu_add_creds"));
        }
        if (initialized == 2)
        {
            int menu9 = mi.addRootMenu(menu_info_types.SERVER_MENU9, new string_id("event_perk", "lottery_menu_show_winner"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.lottery.initialized");
        if (item == menu_info_types.SERVER_MENU3)
        {
            if (initialized == 1 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showDurationList(self, player);
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            if (initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showAddCreditsToPot(self, player);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            if (initialized == 1 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showLotteryInstructions(self, player);
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            if (initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showLotteryInfo(self, player);
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            if (initialized == 1 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            initializeLottery(self, player);
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            if (initialized == 0 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showPurchaseRegistration(self, player);
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            if (initialized == 0 || initialized == 1)
            {
                return SCRIPT_CONTINUE;
            }
            String winner = getStringObjVar(self, "event_perk.lottery.winner_name");
            string_id winMessage = new string_id("event_perk", "lottery_complete_show_winner");
            String message = getString(winMessage) + winner;
            sendSystemMessage(player, message, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int selectDuration(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            sendSystemMessage(player, new string_id("event_perk", "race_setup_canceled"));
            return SCRIPT_CONTINUE;
        }
        int[] durationTable = dataTableGetIntColumn(DATATABLE, 0);
        int duration = durationTable[idx];
        setObjVar(self, "event_perk.lottery.game_duration", duration);
        string_id durationFeedback = new string_id("event_perk", "lottery_dur_feedback");
        string_id durationSID = new string_id("event_perk", "lottery_dur_hours_" + duration);
        sendSystemMessage(player, getString(durationFeedback) + getString(durationSID), null);
        showPayoutPercentList(self, player);
        return SCRIPT_CONTINUE;
    }
    public int selectPayout(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            sendSystemMessage(player, new string_id("event_perk", "race_setup_canceled"));
            return SCRIPT_CONTINUE;
        }
        int[] payoutTable = dataTableGetIntColumn(DATATABLE, 1);
        int payout = payoutTable[idx];
        float payoutFloat = (float)payout;
        setObjVar(self, "event_perk.lottery.payout_percent", payoutFloat);
        string_id payoutFeedback = new string_id("event_perk", "lottery_payout_feedback");
        string_id payoutSID = new string_id("event_perk", "lottery_payout_percent_" + payout);
        sendSystemMessage(player, getString(payoutFeedback) + getString(payoutSID), null);
        showTicketCostSUI(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleTicketCost(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        int ticketCost = utils.stringToInt(text);
        if (bp == sui.BP_CANCEL)
        {
            sendSystemMessage(player, new string_id("event_perk", "race_setup_canceled"));
            return SCRIPT_CONTINUE;
        }
        if (text == null || text.equals("") || ticketCost < 1)
        {
            params.put("theGuy", player);
            sendSystemMessage(player, new string_id("event_perk", "race_setup_nothing_entered"));
            messageTo(self, "handleShowTicketCostSUIAgain", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        string_id ticketCostFeedback = new string_id("event_perk", "ticket_cost_feedback");
        setObjVar(self, "event_perk.lottery.ticket_price", ticketCost);
        sendSystemMessage(player, ticketCost + getString(ticketCostFeedback), null);
        return SCRIPT_CONTINUE;
    }
    public int handleShowTicketCostSUIAgain(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("theGuy");
        showTicketCostSUI(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleAddCredits(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        int addedCredits = utils.stringToInt(text);
        int totalMoney = getTotalMoney(player);
        if (addedCredits > totalMoney)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_nsf"));
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_cancel"));
            return SCRIPT_CONTINUE;
        }
        if (text == null || text.equals("") || addedCredits < 1)
        {
            params.put("theGuy", player);
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_error"));
            messageTo(self, "handleShowAddCreditsSUIAgain", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event_perk.lottery.creditsToAdd", addedCredits);
        showAddCreditsConfirm(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePurchaseRegistration(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        int totalMoney = getTotalMoney(player);
        int ticketCost = getIntObjVar(self, "event_perk.lottery.ticket_price");
        obj_id[] playerOIDList = getObjIdArrayObjVar(self, "event_perk.lottery.player_OID_list");
        for (int i = 0; i < playerOIDList.length; i++)
        {
            if (playerOIDList[i] == player)
            {
                sendSystemMessage(player, new string_id("event_perk", "lottery_reg_purchase_already"));
                return SCRIPT_CONTINUE;
            }
        }
        if (ticketCost > totalMoney)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_nsf"));
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_OK)
        {
            String playerName = getName(player);
            String[] playerNameList = getStringArrayObjVar(self, "event_perk.lottery.player_name_list");
            String[] newPlayerNameList = new String[playerNameList.length + 1];
            obj_id[] newPlayerOIDList = new obj_id[playerOIDList.length + 1];
            newPlayerNameList[playerNameList.length] = playerName;
            newPlayerOIDList[playerNameList.length] = player;
            for (int i = 0; i < playerNameList.length; i++)
            {
                newPlayerNameList[i] = playerNameList[i];
                newPlayerOIDList[i] = playerOIDList[i];
            }
            setObjVar(self, "event_perk.lottery.player_name_list", newPlayerNameList);
            setObjVar(self, "event_perk.lottery.player_OID_list", newPlayerOIDList);
            money.pay(player, self, ticketCost, "doNothingInParticular", null);
            sendSystemMessage(player, new string_id("event_perk", "lottery_reg_purchase_confirm"));
            CustomerServiceLog("EventPerk", "(Lottery Droid - [" + self + "]) Player [" + player + "] has registered at a cost of [" + ticketCost + "] credits");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleShowCreditsConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        int creditsToAdd = getIntObjVar(self, "event_perk.lottery.creditsToAdd");
        obj_id player = sui.getPlayerId(params);
        int totalMoney = getTotalMoney(player);
        if (creditsToAdd > totalMoney)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_nsf"));
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_cancel"));
            return SCRIPT_CONTINUE;
        }
        money.pay(player, self, creditsToAdd, "doNothingInParticular", null);
        removeObjVar(self, "event_perk.lottery.creditsToAdd");
        CustomerServiceLog("EventPerk", "(Lottery Droid - [" + self + "]) Player [" + player + "] has added money to the pot in the sum of [" + creditsToAdd + "] credits.");
        return SCRIPT_CONTINUE;
    }
    public int handleShowAddCreditsSUIAgain(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("theGuy");
        showAddCreditsToPot(self, player);
        return SCRIPT_CONTINUE;
    }
    public int lotteryTimerPing(obj_id self, dictionary params) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.lottery.initialized");
        checkTimeLimit(self);
        if (initialized == 1 || initialized == 2)
        {
            messageTo(self, "lotteryTimerPing", null, 3600, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int transferWinningsToWinner(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id winner = getObjIdObjVar(self, "event_perk.lottery.winner_OID");
        float basePayoutPercent = getFloatObjVar(self, "event_perk.lottery.payout_percent");
        float actualPayoutPercent = basePayoutPercent / 100;
        int myMoney = getTotalMoney(self);
        float jackpotFloat = myMoney * actualPayoutPercent;
        int jackpot = (int)jackpotFloat;
        money.bankTo(self, winner, jackpot);
        string_id mailSubject = new string_id("event_perk", "lottery_mail_winner_sub");
        string_id mailBodySID = new string_id("event_perk", "lottery_mail_winner_body");
        String mailFrom = getName(self);
        prose_package pp = prose.getPackage(mailBodySID, "", jackpot);
        utils.sendMail(mailSubject, pp, winner, mailFrom);
        CustomerServiceLog("EventPerk", "(Lottery Droid - [" + self + "]) Player [" + winner + "] was paid [" + jackpot + "] credits for the jackpot.");
        messageTo(self, "transferTakeToOwner", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int transferTakeToOwner(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "storytellerid");
        int myMoney = getTotalMoney(self);
        money.bankTo(self, owner, myMoney);
        string_id mailSubject = new string_id("event_perk", "lottery_mail_owner_sub");
        string_id mailBodySID = new string_id("event_perk", "lottery_mail_owner_body");
        String mailFrom = getName(self);
        prose_package pp = prose.getPackage(mailBodySID, "", myMoney);
        utils.sendMail(mailSubject, pp, owner, mailFrom);
        CustomerServiceLog("EventPerk", "(Lottery Droid - [" + self + "]) Owner [" + owner + "] was paid [" + myMoney + "] credits as their take of the jackpot.");
        return SCRIPT_CONTINUE;
    }
    public void performLotteryDrawing(obj_id self) throws InterruptedException
    {
        obj_id[] playerOIDList = getObjIdArrayObjVar(self, "event_perk.lottery.player_OID_list");
        String[] playerNameList = getStringArrayObjVar(self, "event_perk.lottery.player_name_list");
        float rightNow = getGameTime();
        string_id noWinner = new string_id("event_perk", "lottery_no_winner");
        String noWinnerName = getString(noWinner);
        int numberRegistered = playerNameList.length - 1;
        if (numberRegistered < 1)
        {
            obj_id owner = getObjIdObjVar(self, "storytellerid");
            setObjVar(self, "event_perk.lottery.winner_name", noWinnerName);
            setObjVar(self, "event_perk.lottery.winner_paid", 1);
            setObjVar(self, "event_perk.lottery.winner_OID", self);
            setObjVar(self, "event_perk.lottery.game_duration", 72);
            setObjVar(self, "event_perk.lottery.game_start_time", rightNow);
        }
        else 
        {
            int luckyWinnuh = rand(1, numberRegistered);
            setObjVar(self, "event_perk.lottery.winner_name", playerNameList[luckyWinnuh]);
            setObjVar(self, "event_perk.lottery.winner_OID", playerOIDList[luckyWinnuh]);
            setObjVar(self, "event_perk.lottery.game_duration", 72);
            setObjVar(self, "event_perk.lottery.game_start_time", rightNow);
        }
        messageTo(self, "transferWinningsToWinner", null, 30, false);
        String winnerName = getStringObjVar(self, "event_perk.lottery.winner_name");
        CustomerServiceLog("EventPerk", "(Lottery Droid - [" + self + "]) The selected winner for this lottery was [" + winnerName + "]");
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.lottery.initialized");
        if (initialized == 1)
        {
            int duration = getIntObjVar(self, "event_perk.lottery.game_duration");
            float gameDuration = duration * 3600;
            float rightNow = getGameTime();
            float gameStartTime = getFloatObjVar(self, "event_perk.lottery.game_start_time");
            float delta = rightNow - gameStartTime;
            if (delta > gameDuration)
            {
                setObjVar(self, "event_perk.lottery.initialized", 2);
                performLotteryDrawing(self);
            }
        }
        if (initialized == 2)
        {
            int duration = getIntObjVar(self, "event_perk.lottery.game_duration");
            float gameDuration = duration * 3600;
            float rightNow = getGameTime();
            float gameStartTime = getFloatObjVar(self, "event_perk.lottery.game_start_time");
            float delta = rightNow - gameStartTime;
            if (delta > gameDuration)
            {
                destroyObject(self);
            }
        }
    }
    public void initializeLottery(obj_id self, obj_id player) throws InterruptedException
    {
        int duration = getIntObjVar(self, "event_perk.lottery.game_duration");
        float payoutPercent = getFloatObjVar(self, "event_perk.lottery.payout_percent");
        int ticketCost = getIntObjVar(self, "event_perk.lottery.ticket_price");
        int initialized = getIntObjVar(self, "event_perk.lottery.initialized");
        if (duration < 1 || payoutPercent < 1 || ticketCost < 1 || initialized != 0)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_initialize_error"));
            return;
        }
        if (initialized == 0)
        {
            setObjVar(self, "event_perk.lottery.initialized", 1);
            float rightNow = getGameTime();
            setObjVar(self, "event_perk.lottery.game_start_time", rightNow);
            sendSystemMessage(player, new string_id("event_perk", "lottery_initialize_success"));
            messageTo(self, "lotteryTimerPing", null, 300, false);
            return;
        }
        return;
    }
    public void showDurationList(obj_id self, obj_id player) throws InterruptedException
    {
        int[] rawDuration = dataTableGetIntColumn(DATATABLE, 0);
        String[] duration = new String[rawDuration.length];
        for (int i = 0; i < rawDuration.length; i++)
        {
            duration[i] = "@event_perk:lottery_dur_hours_" + rawDuration[i];
        }
        sui.listbox(self, player, "@event_perk:lottery_dur_description", sui.OK_CANCEL, "@event_perk:lottery_dur_title", duration, "selectDuration", true);
        return;
    }
    public void showPayoutPercentList(obj_id self, obj_id player) throws InterruptedException
    {
        int[] rawPayout = dataTableGetIntColumn(DATATABLE, 1);
        String[] payout = new String[rawPayout.length];
        for (int i = 0; i < rawPayout.length; i++)
        {
            payout[i] = "@event_perk:lottery_payout_percent_" + rawPayout[i];
        }
        sui.listbox(self, player, "@event_perk:lottery_payout_description", sui.OK_CANCEL, "@event_perk:lottery_payout_title", payout, "selectPayout", true);
        return;
    }
    public int showTicketCostSUI(obj_id self, obj_id player) throws InterruptedException
    {
        string_id ticketCostDesc = new string_id("event_perk", "ticket_cost_desc");
        string_id ticketCostTitle = new string_id("event_perk", "ticket_cost_title");
        int totalMoney = getTotalMoney(player);
        int ticketCost = getIntObjVar(self, "event_perk.lottery.ticket_price");
        if (ticketCost > totalMoney)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_nsf"));
            return SCRIPT_CONTINUE;
        }
        int pid = sui.inputbox(self, player, getString(ticketCostDesc), getString(ticketCostTitle), "handleTicketCost", 7, false, "");
        return pid;
    }
    public int showAddCreditsToPot(obj_id self, obj_id player) throws InterruptedException
    {
        string_id addCreditsDesc = new string_id("event_perk", "lottery_add_credits_desc");
        string_id addCreditsTitle = new string_id("event_perk", "lottery_add_credits_title");
        int pid = sui.inputbox(self, player, getString(addCreditsDesc), getString(addCreditsTitle), "handleAddCredits", 7, false, "");
        return pid;
    }
    public int showLotteryInfo(obj_id self, obj_id player) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.lottery.initialized");
        int duration = getIntObjVar(self, "event_perk.lottery.game_duration");
        float payoutPercent = getFloatObjVar(self, "event_perk.lottery.payout_percent");
        int ticketCost = getIntObjVar(self, "event_perk.lottery.ticket_price");
        String message = "";
        if (duration < 1 || payoutPercent < 1 || ticketCost < 1 || initialized == 0)
        {
            string_id messageSID = new string_id("event_perk", "lottery_info_not_setup");
            message = getString(messageSID);
        }
        if (initialized == 1)
        {
            String[] playerList = getStringArrayObjVar(self, "event_perk.lottery.player_name_list");
            int numPlayers = playerList.length - 1;
            int myBankBalance = getBankBalance(self);
            float payoutFloat = myBankBalance * (payoutPercent / 100);
            int payout = (int)payoutFloat;
            float gameStartTime = getFloatObjVar(self, "event_perk.lottery.game_start_time");
            float rightNow = getGameTime();
            float elapsed = rightNow - gameStartTime;
            float remaining = (duration * 60 * 60) - elapsed;
            String remainingStr = estimateTime(remaining);
            string_id infoSID1 = new string_id("event_perk", "lottery_info_parse1");
            string_id infoSID2 = new string_id("event_perk", "lottery_info_parse2");
            string_id infoSID3 = new string_id("event_perk", "lottery_info_parse3");
            string_id infoSID4 = new string_id("event_perk", "lottery_info_parse4");
            string_id infoSID5 = new string_id("event_perk", "lottery_info_parse5");
            String info1 = getString(infoSID1);
            String info2 = getString(infoSID2);
            String info3 = getString(infoSID3);
            String info4 = getString(infoSID4);
            String info5 = getString(infoSID5);
            message += info1;
            message += numPlayers;
            message += info2;
            message += payout;
            message += info3;
            message += remainingStr;
            message += info4;
            message += ticketCost;
            message += info5;
        }
        if (initialized == 2)
        {
            String winner = getStringObjVar(self, "event_perk.lottery.winner");
        }
        int pid = sui.msgbox(self, player, message, "fugettabotit");
        return pid;
    }
    public int showLotteryInstructions(obj_id self, obj_id player) throws InterruptedException
    {
        string_id messageSID = new string_id("event_perk", "lottery_setup_instructions");
        String message = getString(messageSID);
        int pid = sui.msgbox(self, player, message, "fugettabotit");
        return pid;
    }
    public int showAddCreditsConfirm(obj_id self, obj_id player) throws InterruptedException
    {
        int creditsToAdd = getIntObjVar(self, "event_perk.lottery.creditsToAdd");
        string_id messageSID = new string_id("event_perk", "lottery_add_credits_confirm");
        String message = creditsToAdd + getString(messageSID);
        string_id titleSID = new string_id("event_perk", "lottery_add_credits_confirm_t");
        String title = getString(titleSID);
        int pid = sui.msgbox(self, player, message, sui.OK_CANCEL, title, sui.MSG_NORMAL, "handleShowCreditsConfirm");
        return pid;
    }
    public int showPurchaseRegistration(obj_id self, obj_id player) throws InterruptedException
    {
        int ticketPrice = getIntObjVar(self, "event_perk.lottery.ticket_price");
        int totalMoney = getTotalMoney(player);
        obj_id[] playerOIDList = getObjIdArrayObjVar(self, "event_perk.lottery.player_OID_list");
        for (int i = 0; i < playerOIDList.length; i++)
        {
            if (playerOIDList[i] == player)
            {
                sendSystemMessage(player, new string_id("event_perk", "lottery_reg_purchase_already"));
                return SCRIPT_CONTINUE;
            }
        }
        if (ticketPrice > totalMoney)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_nsf"));
            return SCRIPT_CONTINUE;
        }
        string_id messageSID1 = new string_id("event_perk", "lottery_reg_purchase_desc1");
        string_id messageSID2 = new string_id("event_perk", "lottery_reg_purchase_desc2");
        string_id messageSID3 = new string_id("event_perk", "lottery_reg_purchase_desc3");
        String message = getString(messageSID1) + ticketPrice + getString(messageSID2) + ticketPrice + getString(messageSID3);
        string_id titleSID = new string_id("event_perk", "lottery_reg_purchase_desc_t");
        String title = getString(titleSID);
        int pid = sui.msgbox(self, player, message, sui.OK_CANCEL, title, sui.MSG_NORMAL, "handlePurchaseRegistration");
        return pid;
    }
    public String estimateTime(float remaining) throws InterruptedException
    {
        float hours = remaining / 3600;
        int minHours = 0;
        if (hours < 168)
        {
            minHours = 168;
        }
        if (hours < 144)
        {
            minHours = 144;
        }
        if (hours < 120)
        {
            minHours = 120;
        }
        if (hours < 96)
        {
            minHours = 96;
        }
        if (hours < 72)
        {
            minHours = 72;
        }
        if (hours < 48)
        {
            minHours = 48;
        }
        if (hours < 24)
        {
            minHours = 24;
        }
        if (hours < 8)
        {
            minHours = 8;
        }
        if (hours < 4)
        {
            minHours = 4;
        }
        if (hours < 2)
        {
            minHours = 2;
        }
        if (hours < 1)
        {
            minHours = 1;
        }
        string_id remainingSID = new string_id("event_perk", "lottery_dur_hours_" + minHours);
        String remainingStr = getString(remainingSID);
        return remainingStr;
    }
}
