package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;

public class money extends script.base_script
{
    public money()
    {
    }
    public static final String SCRIPT_PAY_ACTOR = "money.pay.actor";
    public static final String SCRIPT_ACCT_PAY = "money.acct_pay.target";
    public static final String VAR_MONEY_BASE = "money";
    public static final String VAR_MONEY_CASH = "money.cash";
    public static final String VAR_MONEY_BANK = "money.bank";
    public static final String VAR_PAY_TALLY = "money.pay.tally";
    public static final int MT_CASH = 0;
    public static final int MT_BANK = 1;
    public static final int MT_TOTAL = 2;
    public static final int MT_MAX = 3;
    public static final String[] MT_NAME = 
    {
        "cash",
        "bank",
        "total"
    };
    public static final String HANDLER_NONE = "noHandler";
    public static final String HANDLER_BANK_SUCCESS = "handleBankSuccess";
    public static final String HANDLER_BANK_UNKNOWN_ERROR = "handleBankUnknownError";
    public static final String HANDLER_BANK_WITHDRAW_ERROR = "handleBankWithdrawError";
    public static final String HANDLER_BANK_DEPOSIT_ERROR = "handleBankDepositError";
    public static final String HANDLER_BANK_TRANSFER_ERROR = "handleBankTransferError";
    public static final String HANDLER_CREDIT_TRANSFER_ERROR = "handleCreditTransferError";
    public static final String HANDLER_COVERT_SUCCESS = "handleCovertTransactionPass";
    public static final String HANDLER_COVERT_ERROR = "handleCovertTransactionFail";
    public static final String HANDLER_PAYMENT_REQUEST = "handlePaymentRequest";
    public static final String HANDLER_PAY_DEPOSIT = "handlePayDeposit";
    public static final String HANDLER_PAY_PASS = "handlePayPass";
    public static final String HANDLER_PAY_FAIL = "handlePayFail";
    public static final String MSG_BANK_SUCCESS = "@base_player:bank_success";
    public static final String MSG_BANK_ERROR = "@error_message:bank_error";
    public static final String MSG_BANK_WITHDRAW_ERROR = "@error_message:bank_withdraw";
    public static final String MSG_BANK_DEPOSIT_ERROR = "@error_message:bank_deposit";
    public static final String MSG_BANK_TRANSFER_ERROR = "@error_message:bank_transfer";
    public static final String MSG_CASH_TRANSFER_ERROR = "@error_message:cash_transfer";
    public static final string_id SID_BANK_SUCCESS = new string_id("base_player", "bank_success");
    public static final string_id SID_BANK_ERROR = new string_id("error_message", "bank_error");
    public static final string_id SID_BANK_WITHDRAW_ERROR = new string_id("error_message", "bank_withdraw");
    public static final string_id SID_BANK_DEPOSIT_ERROR = new string_id("error_message", "bank_deposit");
    public static final string_id SID_BANK_TRANSFER_ERROR = new string_id("error_message", "bank_transfer");
    public static final string_id SID_CASH_TRANSFER_ERROR = new string_id("error_message", "cash_transfer");
    public static final string_id SID_NULL_TRANSACTION_ERROR = new string_id("error_message", "bank_null_transaction");
    public static final string_id PROSE_PAY_NSF = new string_id("error_message", "prose_pay_nsf");
    public static final string_id PROSE_PAY_FAIL = new string_id("error_message", "prose_pay_fail");
    public static final string_id PROSE_PAY_ACCT_FAIL = new string_id("error_message", "prose_pay_fail");
    public static final string_id PROSE_PAY_SUCCESS = new string_id("base_player", "prose_pay_success");
    public static final string_id PROSE_PAY_SUCCESS_NO_TARGET = new string_id("base_player", "prose_pay_success_no_target");
    public static final string_id PROSE_PAY_ACCT_SUCCESS = new string_id("base_player", "prose_pay_acct_success");
    public static final string_id PROSE_DEPOSIT_SUCCESS = new string_id("base_player", "prose_deposit_success");
    public static final string_id PROSE_WITHDRAW_SUCCESS = new string_id("base_player", "prose_withdraw_success");
    public static final string_id PROSE_TRANSFER_SUCCESS = new string_id("base_player", "prose_transfer_success");
    public static final String DICT_PLAYER_ID = "player_id";
    public static final String DICT_TARGET_ID = "target_id";
    public static final String DICT_TARGET_NAME = "target_name";
    public static final String DICT_ACCT_NAME = "account_name";
    public static final String DICT_AMOUNT = "amount";
    public static final String DICT_TOTAL = "total";
    public static final String DICT_MSG_PAYER = "msg_payer";
    public static final String DICT_NOTIFY = "notify";
    public static final String DICT_HANDLER = "returnHandler";
    public static final String DICT_CODE = "returnCode";
    public static final String DICT_PAY_HANDLER = "payHandler";
    public static final String DICT_OTHER = "other";
    public static final String DICT_BANK_ACTION = "bankAction";
    public static final int BA_NONE = 0;
    public static final int BA_DEPOSIT = 1;
    public static final int BA_WITHDRAW = 2;
    public static final int BA_TRANSFER = 3;
    public static final int RET_SUCCESS = 0;
    public static final int RET_FAIL = 1;
    public static final String HANDLER_REQUEST_LOOT_ADD = "handleRequestLootAdd";
    public static final String HANDLER_LOOT_ADD_PASS = "handleLootAddPass";
    public static final String HANDLER_LOOT_ADD_FAIL = "handleLootAddFail";
    public static final String HANDLER_LOOT_WITHDRAW_PASS = "handleLootWithdrawPass";
    public static final String HANDLER_LOOT_WITHDRAW_FAIL = "handleLootWithdrawFail";
    public static final String STF_ACCT_N = "money/acct_n";
    public static final String ACCT_CHARACTER_CREATION = "characterCreation";
    public static final String ACCT_NEWBIE_TUTORIAL = "newbieTutorial";
    public static final String ACCT_NEW_PLAYER_QUESTS = "newPlayerQuests";
    public static final String ACCT_CUSTOMER_SERVICE = "customerService";
    public static final String ACCT_MISSION_DYNAMIC = "missionSystemDynamic";
    public static final String ACCT_MISSION_PLAYER = "missionSystemPlayer";
    public static final String ACCT_BOUNTY = "bountySystem";
    public static final String ACCT_BOUNTY_CHECK = "bountyCheckSystem";
    public static final String ACCT_CLONING = "cloningSystem";
    public static final String ACCT_INSURANCE = "insuranceSystem";
    public static final String ACCT_TRAVEL = "travelSystem";
    public static final String ACCT_SHIPPING = "shippingSystem";
    public static final String ACCT_BAZAAR = "bazaarSystem";
    public static final String ACCT_DISPENSER = "dispenserSystem";
    public static final String ACCT_SKILL_TRAINING = "skillTrainingSystem";
    public static final String ACCT_DEED_RECLAIM = "deedReclaim";
    public static final String ACCT_STRUCTURE_DESTROYED = "structureDestroyed";
    public static final String ACCT_VEHICLE_REPAIRS = "vehicleRepairs";
    public static final String ACCT_REBEL = "rebelFaction";
    public static final String ACCT_IMPERIAL = "imperialFaction";
    public static final String ACCT_JABBA = "jabbasPalace";
    public static final String ACCT_POI = "POISystem";
    public static final String ACCT_CRAFTING_JOB = "craftingJob";
    public static final String ACCT_CROWD_PLEASER = "crowdPleaser";
    public static final String ACCT_EP3_HUNTING_GROUNDS = "ep3HuntingGrounds";
    public static final String ACCT_GROUND_QUEST = "groundQuest";
    public static final String ACCT_PGC_PLAYER_DONATED = "pgc_player_donated_credits";
    public static final String ACCT_CORPSE_EXPIRATION = "corpseExpiration";
    public static final String ACCT_TEST = "testing";
    public static final String ACCT_STRUCTURE_MAINTENANCE = "structureMaintanence";
    public static final String ACCT_TIP_SURCHARGE = "tipSurcharge";
    public static final String ACCT_VENDOR = "vendorMaintanence";
    public static final String ACCT_CITY = "cityMaint";
    public static final String ACCT_JEDI_DEATH = "jediDeath";
    public static final String ACCT_TIP_ESCROW = "tipEscrow";
    public static final String ACCT_NPC_LOOT = "npcLoot";
    public static final String ACCT_JUNK_DEALER = "junkDealer";
    public static final String ACCT_RELIC_DEALER = "relicDealer";
    public static final String ACCT_CANTINA_DRINK = "cantinaDrink";
    public static final String ACCT_BETA_TEST = "betaTest";
    public static final String ACCT_BAD_SPLIT = "badGroupCoinSplit";
    public static final String ACCT_SLOT_STANDARD = "gamblingSlotStandard";
    public static final String ACCT_ROULETTE = "gamblingRoulette";
    public static final String ACCT_CARD_SABACC = "gamblingCardSabacc";
    public static final String ACCT_FINES = "contraband_fines";
    public static final String ACCT_ST_FINES = "stormtrooper_fines";
    public static final String ACCT_PERFORM_ESCROW = "tipescrow";
    public static final String ACCT_SPACE_QUEST_REWARD = "spaceQuestReward";
    public static final int MILLION = 1000000;
    public static final int BILLION = 1000000000;
    public static final int HUNDRED_MILLION = 100000000;
    public static boolean deposit(obj_id player, int amt) throws InterruptedException
    {
        if (player != null && amt != 0)
        {
            dictionary d = new dictionary();
            d.put(DICT_PLAYER_ID, player);
            d.put(DICT_AMOUNT, amt);
            d.put(DICT_BANK_ACTION, BA_DEPOSIT);
            return depositCashToBank(player, amt, HANDLER_BANK_SUCCESS, HANDLER_BANK_DEPOSIT_ERROR, d);
        }
        else if (player != null)
        {
            return true;
        }
        debugServerConsoleMsg(null, "ERROR in money.scriptlib:deposit - player == null!");
        return false;
    }
    public static boolean withdraw(obj_id player, int amt) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put(DICT_PLAYER_ID, player);
        d.put(DICT_AMOUNT, amt);
        d.put(DICT_BANK_ACTION, BA_WITHDRAW);
        return withdrawCashFromBank(player, amt, HANDLER_BANK_SUCCESS, HANDLER_BANK_WITHDRAW_ERROR, d);
    }
    public static boolean cashTo(obj_id player, obj_id target, int amt) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put(DICT_PLAYER_ID, player);
        d.put(DICT_TARGET_ID, target);
        d.put(DICT_AMOUNT, amt);
        return transferCashTo(player, target, amt, HANDLER_BANK_SUCCESS, HANDLER_CREDIT_TRANSFER_ERROR, d);
    }
    public static boolean bankTo(obj_id player, obj_id target, int amt) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put(DICT_PLAYER_ID, player);
        d.put(DICT_TARGET_ID, target);
        d.put(DICT_AMOUNT, amt);
        return transferBankCreditsTo(player, target, amt, HANDLER_BANK_SUCCESS, HANDLER_BANK_TRANSFER_ERROR, d);
    }
    public static boolean bankTo(String acct, obj_id target, int amt) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put(DICT_TARGET_ID, target);
        d.put(DICT_ACCT_NAME, acct);
        d.put(DICT_AMOUNT, amt);
        d.put(DICT_BANK_ACTION, BA_TRANSFER);
        boolean boolReturn = transferBankCreditsFromNamedAccount(acct, target, amt, HANDLER_BANK_SUCCESS, HANDLER_BANK_TRANSFER_ERROR, d);
        utils.moneyInMetric(target, acct, amt);
        return boolReturn;
    }
    public static boolean bankTo(obj_id player, String acct, int amt) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put(DICT_PLAYER_ID, player);
        d.put(DICT_ACCT_NAME, acct);
        d.put(DICT_AMOUNT, amt);
        boolean boolReturn = transferBankCreditsToNamedAccount(player, acct, amt, HANDLER_BANK_SUCCESS, HANDLER_BANK_TRANSFER_ERROR, d);
        utils.moneyOutMetric(player, acct, amt);
        return boolReturn;
    }
    public static boolean bankTo(obj_id player, String acct, int amt, dictionary dctParams) throws InterruptedException
    {
        dctParams.put(DICT_PLAYER_ID, player);
        dctParams.put(DICT_ACCT_NAME, acct);
        dctParams.put(DICT_AMOUNT, amt);
        obj_id self = getSelf();
        obj_id objSender = null;
        if (utils.hasScriptVar(self, "objSender"))
        {
            objSender = utils.getObjIdScriptVar(self, "objSender");
        }
        if (isIdValid(objSender))
        {
            sendSystemMessageTestingOnly(objSender, "Sending money");
        }
        boolean boolReturn = transferBankCreditsToNamedAccount(player, acct, amt, HANDLER_BANK_SUCCESS, HANDLER_BANK_TRANSFER_ERROR, dctParams);
        if (isIdValid(objSender))
        {
            sendSystemMessageTestingOnly(objSender, "Sent money, return was " + boolReturn);
            sendSystemMessageTestingOnly(objSender, "Player : " + player);
            sendSystemMessageTestingOnly(objSender, "Acct is " + acct);
            sendSystemMessageTestingOnly(objSender, "Amt is " + amt);
            if (dctParams != null)
            {
                sendSystemMessageTestingOnly(objSender, "Params " + dctParams.toString());
            }
            else 
            {
                sendSystemMessageTestingOnly(objSender, "Params NULL!");
            }
        }
        utils.moneyOutMetric(player, acct, amt);
        return boolReturn;
    }
    public static boolean hasFunds(obj_id player, int type, int amt) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int bal = 0;
        switch (type)
        {
            case MT_CASH:
            bal = getCashBalance(player);
            break;
            case MT_BANK:
            bal = getBankBalance(player);
            break;
            case MT_TOTAL:
            bal = getTotalMoney(player);
            break;
            default:
            debugSpeakMsg(player, "money:hasFunds was passed an out of bounds money type");
            return false;
        }
        if (bal >= amt)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean covertDeposit(obj_id player, int amt, String returnHandler, dictionary oldparams) throws InterruptedException
    {
        dictionary params = new dictionary();
        if (oldparams != null && !oldparams.isEmpty())
        {
            java.util.Enumeration keys = oldparams.keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                params.put(key, oldparams.get(key));
            }
        }
        params.put(DICT_PLAYER_ID, player);
        params.put(DICT_AMOUNT, amt);
        params.put(DICT_HANDLER, returnHandler);
        LOG("money", player + " is attempting to covert deposit " + amt + " credits");
        return depositCashToBank(player, amt, HANDLER_COVERT_SUCCESS, HANDLER_COVERT_ERROR, params);
    }
    public static boolean requestPayment(obj_id payer, obj_id target, int amt, String returnHandler, dictionary oldparams, boolean notifySuccess) throws InterruptedException
    {
        if (!isIdValid(payer) || !payer.isLoaded() || !isIdValid(target) || amt < 1)
        {
            return false;
        }
        if (!hasScript(payer, "player.player_money"))
        {
            attachScript(payer, "player.player_money");
        }
        dictionary params = new dictionary();
        if (oldparams != null && !oldparams.isEmpty())
        {
            java.util.Enumeration keys = oldparams.keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                params.put(key, oldparams.get(key));
            }
        }
        params.put(DICT_PLAYER_ID, payer);
        params.put(DICT_TARGET_ID, target);
        params.put(DICT_AMOUNT, amt);
        params.put(DICT_TOTAL, amt);
        params.put(DICT_HANDLER, returnHandler);
        params.put(DICT_NOTIFY, notifySuccess);
        int total = getTotalMoney(payer);
        if (total < amt)
        {
            params.put(DICT_CODE, RET_FAIL);
            return messageTo(target, returnHandler, params, 0, isObjectPersisted(target));
        }
        return messageTo(payer, HANDLER_PAYMENT_REQUEST, params, 0, isObjectPersisted(payer));
    }
    public static boolean requestPayment(obj_id payer, obj_id target, int amt, String returnHandler, dictionary params) throws InterruptedException
    {
        return requestPayment(payer, target, amt, returnHandler, params, true);
    }
    public static boolean requestPayment(obj_id payer, String acct, int amt, String returnHandler, dictionary oldparams, boolean notifySuccess) throws InterruptedException
    {
        if (!isIdValid(payer) || !payer.isLoaded() || acct == null || acct.equals("") || amt < 1)
        {
            return false;
        }
        if (!hasScript(payer, "player.player_money"))
        {
            attachScript(payer, "player.player_money");
        }
        dictionary params = new dictionary();
        if (oldparams != null && !oldparams.isEmpty())
        {
            java.util.Enumeration keys = oldparams.keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                params.put(key, oldparams.get(key));
            }
        }
        params.put(DICT_PLAYER_ID, payer);
        params.put(DICT_TARGET_ID, obj_id.NULL_ID);
        params.put(DICT_ACCT_NAME, acct);
        params.put(DICT_AMOUNT, amt);
        params.put(DICT_TOTAL, amt);
        params.put(DICT_HANDLER, returnHandler);
        params.put(DICT_NOTIFY, notifySuccess);
        int total = getTotalMoney(payer);
        if (total < amt)
        {
            obj_id npc = params.getObjId("npc");
            params.put(DICT_CODE, RET_FAIL);
            if (returnHandler != null && !returnHandler.equals(""))
            {
                messageTo(npc, returnHandler, params, 0, isObjectPersisted(npc));
            }
            return false;
        }
        return messageTo(payer, HANDLER_PAYMENT_REQUEST, params, 0, isObjectPersisted(payer));
    }
    public static boolean requestPayment(obj_id payer, String acct, int amt, String returnHandler, dictionary params) throws InterruptedException
    {
        return requestPayment(payer, acct, amt, returnHandler, params, true);
    }
    public static boolean pay(obj_id self, obj_id target, int amt, String returnHandler, dictionary oldparams, boolean notifySuccess) throws InterruptedException
    {
        if (!isIdValid(self) || !self.isLoaded() || target == null || amt < 1)
        {
            return false;
        }
        obj_id callingSelf = getSelf();
        if (isIdValid(callingSelf) && callingSelf != self)
        {
            return requestPayment(self, target, amt, returnHandler, oldparams, notifySuccess);
        }
        if (!hasScript(self, "player.player_money"))
        {
            attachScript(self, "player.player_money");
        }
        dictionary params = new dictionary();
        if (oldparams != null && !oldparams.isEmpty())
        {
            java.util.Enumeration keys = oldparams.keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                params.put(key, oldparams.get(key));
            }
        }
        params.put(DICT_PLAYER_ID, self);
        params.put(DICT_TARGET_ID, target);
        params.put(DICT_AMOUNT, amt);
        params.put(DICT_TOTAL, amt);
        params.put(DICT_PAY_HANDLER, returnHandler);
        params.put(DICT_NOTIFY, notifySuccess);
        int total = getTotalMoney(self);
        int bank = getBankBalance(self);
        bank -= getIntObjVar(self, VAR_PAY_TALLY);
        if (total < amt)
        {
            prose_package pp = prose.getPackage(PROSE_PAY_NSF, self, target, amt);
            sendSystemMessageProse(self, pp);
            params.put(DICT_CODE, RET_FAIL);
            return messageTo(target, returnHandler, params, 0, isObjectPersisted(target));
        }
        if (bank >= amt)
        {
            return transferBankCreditsTo(self, target, amt, HANDLER_PAY_PASS, HANDLER_PAY_FAIL, params);
        }
        else 
        {
            int diff = amt - bank;
            incrementPayTally(self, diff);
            return covertDeposit(self, diff, HANDLER_PAY_DEPOSIT, params);
        }
    }
    public static boolean pay(obj_id self, obj_id target, int amt, String returnHandler, dictionary params) throws InterruptedException
    {
        return pay(self, target, amt, returnHandler, params, true);
    }
    public static boolean pay(obj_id self, String acct, int amt, String returnHandler, dictionary oldparams, boolean notifySuccess) throws InterruptedException
    {
        if (!isIdValid(self) || !self.isLoaded() || acct == null || acct.equals("") || amt < 1)
        {
            return false;
        }
        obj_id callingSelf = getSelf();
        if (isIdValid(callingSelf) && callingSelf != self)
        {
            return requestPayment(self, acct, amt, returnHandler, oldparams, notifySuccess);
        }
        if (!hasScript(self, "player.player_money"))
        {
            attachScript(self, "player.player_money");
        }
        dictionary params = new dictionary();
        if (oldparams != null && !oldparams.isEmpty())
        {
            java.util.Enumeration keys = oldparams.keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                params.put(key, oldparams.get(key));
            }
        }
        params.put(DICT_PLAYER_ID, self);
        params.put(DICT_ACCT_NAME, acct);
        params.put(DICT_AMOUNT, amt);
        params.put(DICT_TOTAL, amt);
        params.put(DICT_PAY_HANDLER, returnHandler);
        params.put(DICT_NOTIFY, notifySuccess);
        int total = getTotalMoney(self);
        int bank = getBankBalance(self);
        bank -= getIntObjVar(self, VAR_PAY_TALLY);
        if (total < amt)
        {
            params.put(DICT_CODE, RET_FAIL);
            return messageTo(self, returnHandler, params, 0, isObjectPersisted(self));
        }
        if (bank >= amt)
        {
            LOG("money", "PAY: " + self + " is transfering " + amt + " bank credits to acct: " + acct);
            boolean boolReturn = transferBankCreditsToNamedAccount(self, acct, amt, HANDLER_PAY_PASS, HANDLER_PAY_FAIL, params);
            utils.moneyOutMetric(self, acct, amt);
            return boolReturn;
        }
        else 
        {
            LOG("money", "PAY: " + self + " NSF bank credits to xfer " + amt + " credits to acct: " + acct + " -> covert deposit");
            int diff = amt - bank;
            incrementPayTally(self, diff);
            return covertDeposit(self, diff, HANDLER_PAY_DEPOSIT, params);
        }
    }
    public static boolean pay(obj_id self, String acct, int amt, String returnHandler, dictionary params) throws InterruptedException
    {
        return pay(self, acct, amt, returnHandler, params, true);
    }
    public static void incrementPayTally(obj_id self, int amt) throws InterruptedException
    {
        if (!isIdValid(self) || amt < 1)
        {
            return;
        }
        int tally = getIntObjVar(self, VAR_PAY_TALLY);
        setObjVar(self, VAR_PAY_TALLY, tally + amt);
    }
    public static void decrementPayTally(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self) || params == null || params.isEmpty())
        {
            return;
        }
        int amt = params.getInt(DICT_AMOUNT);
        int tally = getIntObjVar(self, VAR_PAY_TALLY);
        tally -= amt;
        if (tally <= 0)
        {
            removeObjVar(self, VAR_PAY_TALLY);
        }
        else 
        {
            setObjVar(self, VAR_PAY_TALLY, tally);
        }
    }
    public static boolean systemPayout(String acct, obj_id target, int amt, String returnHandler, dictionary oldparams) throws InterruptedException
    {
        if ((acct == null) || (acct.equals("")))
        {
            return false;
        }
        obj_id self = getSelf();
        if (!hasScript(self, "player.player_money"))
        {
            attachScript(self, "player.player_money");
        }
        if (!isIdValid(target))
        {
            return false;
        }
        if (amt < 1)
        {
            return false;
        }
        dictionary params = new dictionary();
        if (oldparams != null && !oldparams.isEmpty())
        {
            java.util.Enumeration keys = oldparams.keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                params.put(key, oldparams.get(key));
            }
        }
        if (returnHandler == null)
        {
            returnHandler = "test";
        }
        params.put(DICT_PLAYER_ID, self);
        params.put(DICT_ACCT_NAME, acct);
        params.put(DICT_TARGET_ID, target);
        params.put(DICT_AMOUNT, amt);
        params.put(DICT_PAY_HANDLER, returnHandler);
        boolean boolReturn = transferBankCreditsFromNamedAccount(acct, target, amt, "acctToPass", "xferFail", params);
        utils.moneyInMetric(target, acct, amt);
        return boolReturn;
    }
    public static boolean bankSuccess(obj_id player, dictionary params) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (params == null || params.isEmpty())
        {
            sendSystemMessage(player, SID_BANK_SUCCESS);
            return true;
        }
        prose_package pp = null;
        int bankAction = params.getInt("bankAction");
        int amt = params.getInt(DICT_AMOUNT);
        if (amt > 0)
        {
            switch (bankAction)
            {
                case BA_DEPOSIT:
                pp = prose.getPackage(PROSE_DEPOSIT_SUCCESS, amt);
                break;
                case BA_WITHDRAW:
                pp = prose.getPackage(PROSE_WITHDRAW_SUCCESS, amt);
                break;
                case BA_TRANSFER:
                pp = prose.getPackage(PROSE_TRANSFER_SUCCESS, amt);
                break;
            }
        }
        if (pp == null)
        {
            sendSystemMessage(player, SID_BANK_SUCCESS);
            return true;
        }
        sendSystemMessageProse(player, pp);
        return true;
    }
    public static boolean bankSuccess(obj_id player) throws InterruptedException
    {
        return bankSuccess(player, null);
    }
    public static boolean bankError(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        sendSystemMessage(player, SID_BANK_ERROR);
        return true;
    }
    public static boolean bankWithdrawError(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        sendSystemMessage(player, SID_BANK_WITHDRAW_ERROR);
        return true;
    }
    public static boolean bankDepositError(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        sendSystemMessage(player, SID_BANK_DEPOSIT_ERROR);
        return true;
    }
    public static boolean bankTransferError(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        sendSystemMessage(player, SID_BANK_TRANSFER_ERROR);
        return true;
    }
    public static boolean cashTransferError(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        sendSystemMessage(player, SID_CASH_TRANSFER_ERROR);
        return true;
    }
    public static boolean nullTransactionError(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        sendSystemMessage(player, SID_NULL_TRANSACTION_ERROR);
        return true;
    }
    public static boolean requestLootAdd(obj_id creature, int amt) throws InterruptedException
    {
        if (!isIdValid(creature) || !isMob(creature) || isPlayer(creature))
        {
            return false;
        }
        dictionary d = new dictionary();
        d.put(DICT_AMOUNT, amt);
        return messageTo(creature, HANDLER_REQUEST_LOOT_ADD, d, 0, false);
    }
    public static boolean addCreatureLoot(obj_id creature, int amt) throws InterruptedException
    {
        if (!isIdValid(creature) || !isMob(creature) || isPlayer(creature) || amt <= 0)
        {
            return false;
        }
        dictionary d = new dictionary();
        d.put(DICT_AMOUNT, amt);
        boolean boolReturn = transferBankCreditsFromNamedAccount(ACCT_NPC_LOOT, creature, amt, HANDLER_LOOT_ADD_PASS, HANDLER_LOOT_ADD_FAIL, d);
        utils.moneyInMetric(creature, ACCT_NPC_LOOT, amt);
        return boolReturn;
    }
    public static int getReturnCode(dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return -1;
        }
        java.util.Enumeration e = params.keys();
        while (e.hasMoreElements())
        {
            String key = (String)e.nextElement();
            if (key.equals(DICT_CODE))
            {
                return params.getInt(key);
            }
        }
        return -1;
    }
    public static void clearTotalPlayerCredits(obj_id player, String acct) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || acct == null || acct.equals(""))
        {
            return;
        }
        dictionary d = new dictionary();
        d.put("acct", acct);
        messageTo(player, "handleCovertDepositReturn", d, 1f, false);
    }
}
