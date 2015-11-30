package script.gambling.saarlac;

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
import script.sort;

public class saarlac_wheel extends script.gambling.base.wheel
{
    public saarlac_wheel()
    {
    }
    public static final String[] WHEEL_OUTCOMES = 
    {
        "Bestine",
        "Anchorhead",
        "Mos Eisely",
        "Arch Mesa",
        "Grand Arena Flats",
        "Dune Sea",
        "Spice Fields",
        "Mines",
        "Jabba's Palace",
        "Fort Tusken",
        "Jawa Stronghold",
        "Oasis",
        "Imperial Raid",
        "Krayt Dragon Attack",
        "Saarlac Pit",
        "Bestine(2 Push)",
        "Bestine(1 Push)",
        "Anchorhead(1 Push)",
        "Arch Mesa(2 Push)",
        "Arch Mesa(1 Push)",
        "Grand Arena Flats(1 Push)"
    };
    public static final int[] WHEEL_PROBABILITIES = 
    {
        500,
        1000,
        2000,
        500,
        1000,
        2000,
        500,
        500,
        100,
        500,
        500,
        100,
        400,
        400,
        1
    };
    public static final float[] PAYOUTS = 
    {
        8.0f,
        2.67f,
        1.16f,
        8.0f,
        2.67f,
        1.16f,
        3.0f,
        3.0f,
        250.0f,
        3.0f,
        3.0f,
        250.0f,
        22.0f,
        22.0f,
        10000.0f,
        1.16f,
        2.67f,
        1.16f,
        1.16f,
        2.67f,
        1.16f
    };
    public static final int TIMER_BETTING = 40;
    public static final String VAR_JABBA_POOL = "gambling.table.jabba_pool";
    public static final String VAR_OASIS_POOL = "gambling.table.oasis_pool";
    public static final String VAR_SAARLAC_POOL = "gambling.table.saarlac_pool";
    public static final String VAR_MONEY_IN = "gambling.table.money_in";
    public static final String VAR_MONEY_OUT = "gambling.table.money_out";
    public static final String VAR_JABBA_POOL_MIN = "gambling.table.jabba_pool_min";
    public static final String VAR_OASIS_POOL_MIN = "gambling.table.oasis_pool_min";
    public static final String VAR_SAARLAC_POOL_MIN = "gambling.table.saarlac_pool_min";
    public static final String VAR_TOTAL_BETS = "gambling.table.total_bets";
    public static final String VAR_ADMIN_SUI = "gambling.admin_sui";
    public static final string_id SID_WHEEL_SPIN = new string_id("gambling/saarlac_wheel", "wheel_spin");
    public static final string_id SID_INVALID_BET = new string_id("gambling/saarlac_wheel", "invalid_bet");
    public static final string_id SID_BET_ALREADY_PLACED = new string_id("gambling/saarlac_wheel", "bet_already_placed");
    public static final string_id SID_BET_PLACED_SELF = new string_id("gambling/saarlac_wheel", "bet_placed_self");
    public static final string_id SID_BET_PLACED_OTHER = new string_id("gambling/saarlac_wheel", "bet_placed_other");
    public static final string_id SID_BET_JABBA_POOL_EMPTY = new string_id("gambling/saarlac_wheel", "jabba_pool_empty");
    public static final string_id SID_BET_OASIS_POOL_EMPTY = new string_id("gambling/saarlac_wheel", "oasis_pool_empty");
    public static final string_id SID_BET_SAARLAC_POOL_EMPTY = new string_id("gambling/saarlac_wheel", "saarlac_pool_empty");
    public static final string_id SID_BET_JABBA_POOL_MAX = new string_id("gambling/saarlac_wheel", "jabba_pool_max");
    public static final string_id SID_BET_OASIS_POOL_MAX = new string_id("gambling/saarlac_wheel", "oasis_pool_max");
    public static final string_id SID_BET_SAARLAC_POOL_MAX = new string_id("gambling/saarlac_wheel", "saarlac_pool_max");
    public static final string_id SID_BET_PAYED_SELF = new string_id("gambling/saarlac_wheel", "bet_payed_self");
    public static final string_id SID_BET_LOST_SELF = new string_id("gambling/saarlac_wheel", "bet_lost_self");
    public static final string_id SID_BET_PAYED_OTHER = new string_id("gambling/saarlac_wheel", "bet_payed_other");
    public static final string_id SID_BET_LOST_OTHER = new string_id("gambling/saarlac_wheel", "bet_lost_other");
    public static final string_id SID_JACKPOT_WON_SELF = new string_id("gambling/saarlac_wheel", "jackpot_won_self");
    public static final string_id SID_JACKPOT_WON_OTHER = new string_id("gambling/saarlac_wheel", "jackpot_won_other");
    public static final string_id SID_BET_PUSH_SELF = new string_id("gambling/saarlac_wheel", "bet_push_self");
    public static final string_id SID_BET_PUSH_OTHER = new string_id("gambling/saarlac_wheel", "bet_push_other");
    public static final string_id SID_BET_PUSH_TO_PIT_SELF = new string_id("gambling/saarlac_wheel", "bet_push_to_pit_self");
    public static final string_id SID_BET_PUSH_TO_PIT_OTHER = new string_id("gambling/saarlac_wheel", "bet_push_to_pit_other");
    public static final string_id SID_BET_PUSH_TO_JABBA_SELF = new string_id("gambling/saarlac_wheel", "bet_push_to_jabba_self");
    public static final string_id SID_BET_PUSH_TO_JABBA_OTHER = new string_id("gambling/saarlac_wheel", "bet_push_to_jabba_other");
    public static final string_id SID_BET_PUSH_TO_OASIS_SELF = new string_id("gambling/saarlac_wheel", "bet_push_to_oasis_self");
    public static final string_id SID_BET_PUSH_TO_OASIS_OTHER = new string_id("gambling/saarlac_wheel", "bet_push_to_oasis_other");
    public static final string_id SID_NO_CASINO = new string_id("gambling/saarlac_wheel", "no_casino");
    public static final string_id SID_INSUFFICIENT_FUNDS = new string_id("gambling/saarlac_wheel", "insufficient_funds");
    public static final string_id SID_INSUFFICIENT_FUNDS_START = new string_id("gambling/saarlac_wheel", "insufficient_funds_start");
    public static final string_id SID_NOT_AN_ADMIN = new string_id("gambling/saarlac_wheel", "not_an_admin");
    public static final string_id SID_POOLS_RESET = new string_id("gambling/saarlac_wheel", "pools_reset");
    public static final string_id SID_CANT_DO_MAINTENANCE = new string_id("gambling/saarlac_wheel", "cant_do_maintenance");
    public static final string_id SID_JABBA_POOL_MIN_SET = new string_id("gambling/saarlac_wheel", "jabba_pool_min_set");
    public static final string_id SID_OASIS_POOL_MIN_SET = new string_id("gambling/saarlac_wheel", "oasis_pool_min_set");
    public static final string_id SID_SAARLAC_POOL_MIN_SET = new string_id("gambling/saarlac_wheel", "saarlac_pool_min_set");
    public static final string_id SID_MNU_ADMIN = new string_id("gambling/saarlac_wheel", "menu_admin");
    public static final string_id SID_MNU_POOL_RESET = new string_id("gambling/saarlac_wheel", "menu_pool_reset");
    public static final string_id SID_MNU_POOL_MINIMUM_JABBA = new string_id("gambling/saarlac_wheel", "menu_pool_minimum_jabba");
    public static final string_id SID_MNU_POOL_MINIMUM_OASIS = new string_id("gambling/saarlac_wheel", "menu_pool_minimum_oasis");
    public static final string_id SID_MNU_POOL_MINIMUM_SAARLAC = new string_id("gambling/saarlac_wheel", "menu_pool_minimum_saarlac");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        reseed(getGameTime());
        return super.OnInitialize(self);
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isAdmin(structure, player))
        {
            int admin_root = mi.addRootMenu(menu_info_types.SERVER_MENU6, SID_MNU_ADMIN);
            mi.addSubMenu(admin_root, menu_info_types.SERVER_MENU7, SID_MNU_POOL_RESET);
            mi.addSubMenu(admin_root, menu_info_types.SERVER_MENU8, SID_MNU_POOL_MINIMUM_JABBA);
            mi.addSubMenu(admin_root, menu_info_types.SERVER_MENU9, SID_MNU_POOL_MINIMUM_OASIS);
            mi.addSubMenu(admin_root, menu_info_types.SERVER_MENU10, SID_MNU_POOL_MINIMUM_SAARLAC);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isAdmin(structure, player))
        {
            sendSystemMessage(player, SID_NOT_AN_ADMIN);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            String[] dsrc = new String[16];
            dsrc[0] = "Money In: " + getIntObjVar(self, VAR_MONEY_IN);
            dsrc[1] = "Money Out: " + getIntObjVar(self, VAR_MONEY_OUT);
            dsrc[2] = "";
            dsrc[3] = "Min Bet: " + getIntObjVar(self, gambling.VAR_TABLE_BET_MIN);
            dsrc[4] = "Max Bet: " + getIntObjVar(self, gambling.VAR_TABLE_BET_MAX);
            dsrc[5] = "";
            dsrc[6] = "Jabba Pool: " + getJabbaPool(self);
            dsrc[7] = "Oasis Pool: " + getOasisPool(self);
            dsrc[8] = "Saarlac Pool: " + getSaarlacPool(self);
            dsrc[9] = "";
            dsrc[10] = "Jabba Pool Min: " + getIntObjVar(self, VAR_JABBA_POOL_MIN);
            dsrc[11] = "Oasis Pool Min: " + getIntObjVar(self, VAR_OASIS_POOL_MIN);
            dsrc[12] = "Saarlac Pool Min: " + getIntObjVar(self, VAR_SAARLAC_POOL_MIN);
            dsrc[13] = "";
            dsrc[14] = "Structure Credits: " + getBankBalance(structure);
            dsrc[15] = "Table Credits: " + getBankBalance(self);
            sui.listbox(player, "@gambling/saarlac_wheel:stats", "@gambling/saarlac_wheel:stats", sui.OK_CANCEL, dsrc);
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            if (hasObjVar(self, gambling.VAR_GAME_BASE))
            {
                sendSystemMessage(player, SID_CANT_DO_MAINTENANCE);
                return SCRIPT_CONTINUE;
            }
            resetJabbaPool(self);
            resetOasisPool(self);
            resetSaarlacPool(self);
            sendSystemMessage(player, SID_POOLS_RESET);
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            if (hasObjVar(self, gambling.VAR_GAME_BASE))
            {
                sendSystemMessage(player, SID_CANT_DO_MAINTENANCE);
                return SCRIPT_CONTINUE;
            }
            int balance = getBankBalance(structure);
            utils.setScriptVar(self, VAR_ADMIN_SUI, player);
            int pid = sui.transfer(self, player, "@gambling/saarlac_wheel:set_jabba_pool_min", "@gambling/saarlac_wheel:set_jabba_pool_title", "@gambling/saarlac_wheel:total_funds", balance, "@gambling/saarlac_wheel:pool_minimum", 0, "msgSetJabbaMinimum");
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            if (hasObjVar(self, gambling.VAR_GAME_BASE))
            {
                sendSystemMessage(player, SID_CANT_DO_MAINTENANCE);
                return SCRIPT_CONTINUE;
            }
            int balance = getBankBalance(structure);
            utils.setScriptVar(self, VAR_ADMIN_SUI, player);
            int pid = sui.transfer(self, player, "@gambling/saarlac_wheel:set_oasis_pool_min", "@gambling/saarlac_wheel:set_oasis_pool_title", "@gambling/saarlac_wheel:total_funds", balance, "@gambling/saarlac_wheel:pool_minimum", 0, "msgSetOasisMinimum");
        }
        if (item == menu_info_types.SERVER_MENU10)
        {
            if (hasObjVar(self, gambling.VAR_GAME_BASE))
            {
                sendSystemMessage(player, SID_CANT_DO_MAINTENANCE);
                return SCRIPT_CONTINUE;
            }
            int balance = getBankBalance(structure);
            utils.setScriptVar(self, VAR_ADMIN_SUI, player);
            int pid = sui.transfer(self, player, "@gambling/saarlac_wheel:set_saarlac_pool_min", "@gambling/saarlac_wheel:set_saarlac_pool_title", "@gambling/saarlac_wheel:total_funds", balance, "@gambling/saarlac_wheel:pool_minimum", 0, "msgSetSaarlacMinimum");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWheelSpinning(obj_id self, dictionary params) throws InterruptedException
    {
        int spin = rand(1, 10001);
        int wheel_index = wheelRollToIndex(spin);
        if (wheel_index >= 0 && wheel_index <= 14)
        {
            if (params.containsKey("spin"))
            {
                wheel_index = params.getInt("spin");
            }
            String result = wheelIndexToName(wheel_index);
            debugSpeakMsg(self, "spin ->" + spin + " " + "result ->" + result);
            LOG("LOG_CHANNEL", "spin ->" + spin + " " + "result ->" + result);
            prose_package pp = prose.getPackage(SID_WHEEL_SPIN, result.toUpperCase());
            sendTableMessage(self, pp);
            resolveSpin(self, wheel_index);
            messageTo(self, "handleDelayedRestart", null, 5.0f, false);
        }
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
        LOG("LOG_CHANNEL", "arg ->" + arg);
        if (arg != null && !arg.equals(""))
        {
            int wheel_index = wheelNameToIndex(arg);
            LOG("LOG_CHANNEL", "wheel_index ->" + wheel_index);
            if (wheel_index != -1 && wheel_index <= 14)
            {
                int validated_amt = validateBet(self, wheel_index, amt, player);
                LOG("LOG_CHANNEL", "validated_amt ->" + validated_amt);
                if (validated_amt > 0)
                {
                    completeBet(self, wheel_index, validated_amt, player);
                }
                else if (validated_amt != -2)
                {
                    bet_success = false;
                }
            }
            else 
            {
                sendSystemMessage(player, SID_INVALID_BET);
                bet_success = false;
            }
        }
        else 
        {
            bet_success = false;
        }
        if (!bet_success)
        {
            transferBankCreditsTo(self, player, amt, "noHandler", "noHandler", new dictionary());
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBetVerified(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::handleBetVerified");
        obj_id player = params.getObjId("player");
        LOG("LOG_CHANNEL", "player ->" + player);
        if (!isIdValid(player) || !player.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        int amt = params.getInt("amt");
        if (amt < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int bet_index = params.getInt("bet_index");
        if (bet_index < 0 || bet_index > 14)
        {
            return SCRIPT_CONTINUE;
        }
        completeBet(self, bet_index, amt, player);
        return SCRIPT_CONTINUE;
    }
    public int handleBetVerifiedFailed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::handleBetVerifiedFailed");
        obj_id player = params.getObjId("player");
        if (isIdValid(player) && player.isLoaded())
        {
            sendSystemMessage(player, SID_INSUFFICIENT_FUNDS);
        }
        int amt = params.getInt("amt");
        if (amt > 0)
        {
            transferBankCreditsTo(self, player, amt, "noHandler", "noHandler", new dictionary());
        }
        return SCRIPT_CONTINUE;
    }
    public int tableTransferSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::tableTransferSuccess");
        startWheelGame(self);
        return SCRIPT_CONTINUE;
    }
    public int tableTransferFailed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::tableTransferFailed");
        sendTableMessage(self, SID_INSUFFICIENT_FUNDS_START, null);
        return SCRIPT_CONTINUE;
    }
    public int msgSetJabbaMinimum(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::msgSetJabbaMinimum");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = sui.getTransferInputTo(params);
        if (amt < 1)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, VAR_JABBA_POOL_MIN, amt);
        if (getJabbaPool(self) < amt)
        {
            resetJabbaPool(self);
        }
        if (utils.hasScriptVar(self, VAR_ADMIN_SUI))
        {
            obj_id player = utils.getObjIdScriptVar(self, VAR_ADMIN_SUI);
            if (isIdValid(player) && player.isLoaded())
            {
                prose_package pp = prose.getPackage(SID_JABBA_POOL_MIN_SET, amt);
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSetOasisMinimum(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::msgSetOasisMinimum");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = sui.getTransferInputTo(params);
        if (amt < 1)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, VAR_OASIS_POOL_MIN, amt);
        if (getOasisPool(self) < amt)
        {
            resetOasisPool(self);
        }
        if (utils.hasScriptVar(self, VAR_ADMIN_SUI))
        {
            obj_id player = utils.getObjIdScriptVar(self, VAR_ADMIN_SUI);
            if (isIdValid(player) && player.isLoaded())
            {
                prose_package pp = prose.getPackage(SID_OASIS_POOL_MIN_SET, amt);
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSetSaarlacMinimum(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::msgSetSaarlacMinimum");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = sui.getTransferInputTo(params);
        if (amt < 1)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, VAR_SAARLAC_POOL_MIN, amt);
        if (getSaarlacPool(self) < amt)
        {
            resetSaarlacPool(self);
        }
        if (utils.hasScriptVar(self, VAR_ADMIN_SUI))
        {
            obj_id player = utils.getObjIdScriptVar(self, VAR_ADMIN_SUI);
            if (isIdValid(player) && player.isLoaded())
            {
                prose_package pp = prose.getPackage(SID_SAARLAC_POOL_MIN_SET, amt);
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String wheelRollToName(int roll) throws InterruptedException
    {
        int low_roll = 0;
        for (int i = 0; i < WHEEL_PROBABILITIES.length; i++)
        {
            int high_roll = low_roll + WHEEL_PROBABILITIES[i];
            if (roll > low_roll && roll <= high_roll)
            {
                return WHEEL_OUTCOMES[i];
            }
            low_roll = high_roll;
        }
        return null;
    }
    public int wheelRollToIndex(int roll) throws InterruptedException
    {
        int low_roll = 0;
        for (int i = 0; i < WHEEL_PROBABILITIES.length; i++)
        {
            int high_roll = low_roll + WHEEL_PROBABILITIES[i];
            if (roll > low_roll && roll <= high_roll)
            {
                return i;
            }
            low_roll = high_roll;
        }
        return -1;
    }
    public String wheelIndexToName(int idx) throws InterruptedException
    {
        if (idx >= WHEEL_OUTCOMES.length)
        {
            return null;
        }
        else 
        {
            return WHEEL_OUTCOMES[idx];
        }
    }
    public int wheelNameToIndex(String name) throws InterruptedException
    {
        if (name == null)
        {
            return -1;
        }
        for (int i = 0; i < WHEEL_OUTCOMES.length; i++)
        {
            if ((WHEEL_OUTCOMES[i].toLowerCase()).startsWith(name.toLowerCase()))
            {
                return i;
            }
        }
        return -1;
    }
    public boolean sendTableMessage(obj_id table, prose_package pp, obj_id ommit_player) throws InterruptedException
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
            for (int i = 0; i < players.length; i++)
            {
                if (players[i] != ommit_player)
                {
                    sendSystemMessageProse(players[i], pp);
                }
            }
        }
        else 
        {
            return false;
        }
        return true;
    }
    public boolean sendTableMessage(obj_id table, prose_package pp) throws InterruptedException
    {
        return sendTableMessage(table, pp, null);
    }
    public boolean sendTableMessage(obj_id table, string_id message, obj_id ommit_player) throws InterruptedException
    {
        prose_package pp = prose.getPackage(message, table);
        return sendTableMessage(table, pp, ommit_player);
    }
    public int getJabbaPool(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        return getIntObjVar(table, VAR_JABBA_POOL);
    }
    public int getOasisPool(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        return getIntObjVar(table, VAR_OASIS_POOL);
    }
    public int getSaarlacPool(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        return getIntObjVar(table, VAR_SAARLAC_POOL);
    }
    public int adjustJabbaPool(obj_id table, int amt) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        int current_amt = 0;
        if (hasObjVar(table, VAR_JABBA_POOL))
        {
            current_amt = getIntObjVar(table, VAR_JABBA_POOL);
        }
        current_amt = current_amt + amt;
        if (current_amt < 0)
        {
            current_amt = 0;
        }
        setObjVar(table, VAR_JABBA_POOL, current_amt);
        return current_amt;
    }
    public int adjustOasisPool(obj_id table, int amt) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        int current_amt = 0;
        if (hasObjVar(table, VAR_OASIS_POOL))
        {
            current_amt = getIntObjVar(table, VAR_OASIS_POOL);
        }
        current_amt = current_amt + amt;
        if (current_amt < 0)
        {
            current_amt = 0;
        }
        setObjVar(table, VAR_OASIS_POOL, current_amt);
        return current_amt;
    }
    public int adjustSaarlacPool(obj_id table, int amt) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        int current_amt = 0;
        if (hasObjVar(table, VAR_SAARLAC_POOL))
        {
            current_amt = getIntObjVar(table, VAR_SAARLAC_POOL);
        }
        current_amt = current_amt + amt;
        if (current_amt < 0)
        {
            current_amt = 0;
        }
        setObjVar(table, VAR_SAARLAC_POOL, current_amt);
        return current_amt;
    }
    public boolean resetJabbaPool(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        int jabba_pool_min = 0;
        if (hasObjVar(table, VAR_JABBA_POOL_MIN))
        {
            jabba_pool_min = getIntObjVar(table, VAR_JABBA_POOL_MIN);
        }
        setObjVar(table, VAR_JABBA_POOL, jabba_pool_min);
        return true;
    }
    public boolean resetOasisPool(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        int oasis_pool_min = 0;
        if (hasObjVar(table, VAR_OASIS_POOL_MIN))
        {
            oasis_pool_min = getIntObjVar(table, VAR_OASIS_POOL_MIN);
        }
        setObjVar(table, VAR_OASIS_POOL, oasis_pool_min);
        return true;
    }
    public boolean resetSaarlacPool(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        int saarlac_pool_min = 0;
        if (hasObjVar(table, VAR_SAARLAC_POOL_MIN))
        {
            saarlac_pool_min = getIntObjVar(table, VAR_SAARLAC_POOL_MIN);
        }
        setObjVar(table, VAR_SAARLAC_POOL, saarlac_pool_min);
        return true;
    }
    public int getTotalBetAmount(obj_id table) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return -1;
        }
        if (utils.hasScriptVar(table, VAR_TOTAL_BETS))
        {
            return utils.getIntScriptVar(table, VAR_TOTAL_BETS);
        }
        else 
        {
            return 0;
        }
    }
    public boolean hasBetBeenPlaced(obj_id table, int wheel_index) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        String wheel_name = wheelIndexToName(wheel_index);
        if (wheel_name == null)
        {
            return false;
        }
        obj_id[] player_ids = getObjIdArrayObjVar(table, gambling.VAR_GAME_PLAYERS_IDS);
        if (player_ids != null)
        {
            for (int i = 0; i < player_ids.length; i++)
            {
                String[] bets = getPlayerBets(table, i);
                if (bets != null)
                {
                    for (int j = 0; j < bets.length; j++)
                    {
                        if (bets[j].equals(wheel_name))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public String[] getPlayerBets(obj_id table, int player_index) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return null;
        }
        if (player_index < 0)
        {
            return null;
        }
        String objvar_name = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet";
        if (hasObjVar(table, objvar_name))
        {
            obj_var_list bet_list = getObjVarList(table, objvar_name);
            if (bet_list != null)
            {
                String[] player_bets = new String[bet_list.getNumItems()];
                for (int i = 0; i < bet_list.getNumItems(); i++)
                {
                    obj_var bet = bet_list.getObjVar(i);
                    String bet_name = bet.getName();
                    player_bets[i] = bet.getName();
                }
                return player_bets;
            }
        }
        return null;
    }
    public Vector getPlayerBetIndices(obj_id table, int player_index) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "getPlayerBetIndicies -- table ->" + table + " player_index ->" + player_index);
        if (!isIdValid(table))
        {
            return null;
        }
        if (player_index < 0)
        {
            return null;
        }
        String[] bets = getPlayerBets(table, player_index);
        if (bets != null)
        {
            Vector bet_idx = new Vector();
            bet_idx.setSize(0);
            Vector push_bet_idx = new Vector();
            push_bet_idx.setSize(0);
            for (int i = 0; i < bets.length; i++)
            {
                int bet = wheelNameToIndex(bets[i]);
                if (bet <= 14)
                {
                    utils.addElement(bet_idx, bet);
                }
                else 
                {
                    utils.addElement(push_bet_idx, bet);
                }
            }
            if (bet_idx.size() > 1)
            {
                sort.sort(bet_idx);
            }
            if (push_bet_idx.size() > 1)
            {
                sort.sort(push_bet_idx);
            }
            Vector all_bets = utils.concatArrays(push_bet_idx, bet_idx);
            return all_bets;
        }
        else 
        {
            return null;
        }
    }
    public int validateBet(obj_id table, int wheel_index, int amt, obj_id player) throws InterruptedException
    {
        String wheel_name = wheelIndexToName(wheel_index);
        if (wheel_name == null)
        {
            return -1;
        }
        int player_index = gambling.getGamePlayerIndex(table, player);
        if (player_index < 0)
        {
            return -1;
        }
        obj_id structure = player_structure.getStructure(table);
        if (!isIdValid(structure))
        {
            sendSystemMessage(player, SID_NO_CASINO);
            return -1;
        }
        if (getOwner(structure) != getOwner(table))
        {
            sendSystemMessage(player, SID_NO_CASINO);
            return -1;
        }
        if (wheel_index >= 1 && wheel_index <= 6)
        {
        }
        else 
        {
            if (hasBetBeenPlaced(table, wheel_index))
            {
                prose_package pp = prose.getPackage(SID_BET_ALREADY_PLACED, wheel_name);
                sendSystemMessageProse(player, pp);
                return -1;
            }
            switch (wheel_index)
            {
                case 8:
                int jabba_pool = getIntObjVar(table, VAR_JABBA_POOL) / 2;
                if (jabba_pool < 1)
                {
                    sendSystemMessage(player, SID_BET_JABBA_POOL_EMPTY);
                    return -1;
                }
                if ((float)amt * PAYOUTS[8] > jabba_pool)
                {
                    int max_bet = (int)(jabba_pool / PAYOUTS[8]);
                    if (max_bet < 1)
                    {
                        max_bet = 1;
                    }
                    int refund = amt - max_bet;
                    transferBankCreditsTo(table, player, amt, "noHandler", "noHandler", new dictionary());
                    prose_package pp = prose.getPackage(SID_BET_JABBA_POOL_MAX, max_bet);
                    sendSystemMessageProse(player, pp);
                    amt = max_bet;
                }
                break;
                case 11:
                int oasis_pool = getIntObjVar(table, VAR_OASIS_POOL) / 2;
                if (oasis_pool < 1)
                {
                    sendSystemMessage(player, SID_BET_OASIS_POOL_EMPTY);
                    return -1;
                }
                if ((float)amt * PAYOUTS[11] > oasis_pool)
                {
                    int max_bet = (int)(oasis_pool / PAYOUTS[11]);
                    if (max_bet < 1)
                    {
                        max_bet = 1;
                    }
                    int refund = amt - max_bet;
                    transferBankCreditsTo(table, player, amt, "noHandler", "noHandler", new dictionary());
                    prose_package pp = prose.getPackage(SID_BET_OASIS_POOL_MAX, max_bet);
                    sendSystemMessageProse(player, pp);
                    amt = max_bet;
                }
                break;
                case 14:
                int saarlac_pool = getIntObjVar(table, VAR_SAARLAC_POOL);
                if (saarlac_pool < 1)
                {
                    sendSystemMessage(player, SID_BET_SAARLAC_POOL_EMPTY);
                    return -1;
                }
                if ((float)amt * PAYOUTS[14] > saarlac_pool)
                {
                    int max_bet = (int)(saarlac_pool / PAYOUTS[14]);
                    if (max_bet < 1)
                    {
                        max_bet = 1;
                    }
                    int refund = amt - max_bet;
                    transferBankCreditsTo(table, player, amt, "noHandler", "noHandler", new dictionary());
                    prose_package pp = prose.getPackage(SID_BET_SAARLAC_POOL_MAX, max_bet);
                    sendSystemMessageProse(player, pp);
                    amt = max_bet;
                }
                break;
            }
        }
        if (wheel_index == 8 || wheel_index == 11 || wheel_index == 14)
        {
            return amt;
        }
        else 
        {
            int max_payout = (int)(amt * PAYOUTS[wheel_index]);
            int bet_cover = (int)((getJabbaPool(table) + getOasisPool(table) + getSaarlacPool(table)) * 1.05) + getTotalBetAmount(table);
            int table_balance = getBankBalance(table);
            LOG("LOG_CHANNEL", "max_payout ->" + max_payout + " bet_cover ->" + bet_cover + " table_balance ->" + table_balance);
            if (bet_cover + max_payout > table_balance)
            {
                if (max_payout > 0)
                {
                    dictionary d = new dictionary();
                    d.put("player", player);
                    d.put("bet_index", wheel_index);
                    d.put("amt", amt);
                    transferBankCreditsTo(structure, table, max_payout, "handleBetVerified", "handleBetVerifiedFailed", d);
                }
                return -2;
            }
            else 
            {
                return amt;
            }
        }
    }
    public boolean completeBet(obj_id table, int bet_index, int amt, obj_id player) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "saarlac_wheel::completeBet");
        if (!isIdValid(table))
        {
            return false;
        }
        if (bet_index < 0 || bet_index > 14)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        String scriptvar_pid = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
        if (utils.hasScriptVar(table, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(table, scriptvar_pid);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(table, scriptvar_pid);
        }
        int playerIdx = gambling.getGamePlayerIndex(table, player);
        if (playerIdx < 0)
        {
            return false;
        }
        String ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet." + bet_name;
        if (hasObjVar(table, ovpath))
        {
            amt += getIntObjVar(table, ovpath);
            int maxBet = getIntObjVar(table, gambling.VAR_TABLE_BET_MAX);
            if (maxBet > 0 && amt > maxBet)
            {
                int refund = amt - maxBet;
                sendSystemMessageTestingOnly(player, "The maximum bet for this station is " + maxBet + " credits.");
                sendSystemMessageTestingOnly(player, "Bet Refund (over-bet): " + refund + " credits");
                transferBankCreditsTo(table, player, refund, "noHandler", "noHandler", new dictionary());
                amt = maxBet;
            }
        }
        setObjVar(table, ovpath, amt);
        int total_bets = getTotalBetAmount(table);
        total_bets += (int)(amt * PAYOUTS[bet_index]);
        utils.setScriptVar(table, VAR_TOTAL_BETS, total_bets);
        prose_package pp = prose.getPackage(SID_BET_PLACED_SELF, bet_name, amt);
        sendSystemMessageProse(player, pp);
        pp = prose.getPackage(SID_BET_PLACED_OTHER, player, null, bet_name, amt);
        sendTableMessage(table, pp, player);
        int money_in = 0;
        if (hasObjVar(table, VAR_MONEY_IN))
        {
            money_in = getIntObjVar(table, VAR_MONEY_IN);
        }
        money_in = money_in + amt;
        setObjVar(table, VAR_MONEY_IN, money_in);
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(table, "handleRequestUpdatedUI", d, 0f, false);
        return true;
    }
    public boolean resolveSpin(obj_id table, int wheel_index) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (wheel_index < 0 || wheel_index > 14)
        {
            LOG("LOG_CHANNEL", "saarlac_wheel::resolveSpin -- wheel_index value is invalid ->" + wheel_index);
            return false;
        }
        obj_id[] player_ids = getObjIdArrayObjVar(table, gambling.VAR_GAME_PLAYERS_IDS);
        if (player_ids != null)
        {
            for (int i = 0; i < player_ids.length; i++)
            {
                Vector bets = getPlayerBetIndices(table, gambling.getGamePlayerIndex(table, player_ids[i]));
                if (bets != null)
                {
                    for (int j = 0; j < bets.size(); j++)
                    {
                        if (((Integer)bets.get(j)).intValue() == -1)
                        {
                            continue;
                        }
                        LOG("LOG_CHANNEL", "bets[" + j + "] ->" + ((Integer)bets.get(j)).intValue() + " wheel_index ->" + wheel_index + " player ->" + player_ids[i]);
                        switch (wheel_index)
                        {
                            case 0:
                            if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 1:
                            if (((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 2:
                            if (((Integer)bets.get(j)).intValue() == 2)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 3:
                            if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 4:
                            if (((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 5:
                            if (((Integer)bets.get(j)).intValue() == 5)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 6:
                            if (((Integer)bets.get(j)).intValue() == 6)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 9 || ((Integer)bets.get(j)).intValue() == 10)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 7:
                            if (((Integer)bets.get(j)).intValue() == 7)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 9 || ((Integer)bets.get(j)).intValue() == 10)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 8:
                            if (((Integer)bets.get(j)).intValue() == 8)
                            {
                                payPoolBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 6 || ((Integer)bets.get(j)).intValue() == 7)
                            {
                                payPoolBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 9 || ((Integer)bets.get(j)).intValue() == 10)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 9:
                            if (((Integer)bets.get(j)).intValue() == 9)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 6 || ((Integer)bets.get(j)).intValue() == 7)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 10:
                            if (((Integer)bets.get(j)).intValue() == 10)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 6 || ((Integer)bets.get(j)).intValue() == 7)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 11:
                            if (((Integer)bets.get(j)).intValue() == 11)
                            {
                                payPoolBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 9 || ((Integer)bets.get(j)).intValue() == 10)
                            {
                                payPoolBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 6 || ((Integer)bets.get(j)).intValue() == 7)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 12:
                            if (((Integer)bets.get(j)).intValue() == 12)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 6 || ((Integer)bets.get(j)).intValue() == 7 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 13 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 13:
                            if (((Integer)bets.get(j)).intValue() == 13)
                            {
                                payBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 3 || ((Integer)bets.get(j)).intValue() == 4 || ((Integer)bets.get(j)).intValue() == 5 || ((Integer)bets.get(j)).intValue() == 9 || ((Integer)bets.get(j)).intValue() == 10 || ((Integer)bets.get(j)).intValue() == 18 || ((Integer)bets.get(j)).intValue() == 19 || ((Integer)bets.get(j)).intValue() == 20)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 0 || ((Integer)bets.get(j)).intValue() == 1 || ((Integer)bets.get(j)).intValue() == 2 || ((Integer)bets.get(j)).intValue() == 15 || ((Integer)bets.get(j)).intValue() == 16 || ((Integer)bets.get(j)).intValue() == 17)
                            {
                                pushBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12 || ((Integer)bets.get(j)).intValue() == 14)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                            case 14:
                            if (((Integer)bets.get(j)).intValue() == 14)
                            {
                                payPoolBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            else if (((Integer)bets.get(j)).intValue() == 8 || ((Integer)bets.get(j)).intValue() == 11 || ((Integer)bets.get(j)).intValue() == 12)
                            {
                                loseBet(table, ((Integer)bets.get(j)).intValue(), player_ids[i]);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean setBet(obj_id table, int bet_index, int player_index, int amt) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (bet_index < 0 || bet_index > 20)
        {
            return false;
        }
        if (amt < 1)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        if (bet_name == null)
        {
            return false;
        }
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet." + bet_name;
        if (hasObjVar(table, bet_path))
        {
            return false;
        }
        else 
        {
            setObjVar(table, bet_path, amt);
            int total_bets = getTotalBetAmount(table);
            total_bets += (int)(amt * PAYOUTS[bet_index]);
            utils.setScriptVar(table, VAR_TOTAL_BETS, total_bets);
            return true;
        }
    }
    public boolean removeBet(obj_id table, int bet_index, int player_index) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (bet_index < 0 || bet_index > 20)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        if (bet_name == null)
        {
            return false;
        }
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet." + bet_name;
        if (hasObjVar(table, bet_path))
        {
            int total_bets = getTotalBetAmount(table);
            total_bets -= (int)(getIntObjVar(table, bet_path) * PAYOUTS[bet_index]);
            utils.setScriptVar(table, VAR_TOTAL_BETS, total_bets);
            removeObjVar(table, bet_path);
            obj_id[] player_ids = getObjIdArrayObjVar(table, gambling.VAR_GAME_PLAYERS_IDS);
            if (player_ids != null)
            {
                obj_id player = player_ids[player_index];
                dictionary d = new dictionary();
                d.put("player", player);
                messageTo(table, "handleRequestUpdatedUI", d, 0f, false);
            }
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean payBet(obj_id table, int bet_index, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (bet_index < 0 || bet_index > 20)
        {
            return false;
        }
        if (!isIdValid(player) || !player.isLoaded())
        {
            return false;
        }
        int player_index = gambling.getGamePlayerIndex(table, player);
        if (player_index < 0)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        if (bet_name == null)
        {
            return false;
        }
        float payout = PAYOUTS[bet_index];
        if (payout <= 0)
        {
            return false;
        }
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet." + bet_name;
        if (hasObjVar(table, bet_path))
        {
            int amt = getIntObjVar(table, bet_path);
            if (amt > 0)
            {
                int winnings = (int)(amt * payout);
                int total_amt = amt + winnings;
                LOG("LOG_CHANNEL", "player ->" + player + " bet_index ->" + bet_index + " amt ->" + amt + " winnings ->" + winnings);
                transferBankCreditsTo(table, player, total_amt, "noHandler", "noHandler", new dictionary());
                removeBet(table, bet_index, player_index);
                int money_out = 0;
                if (hasObjVar(table, VAR_MONEY_OUT))
                {
                    money_out = getIntObjVar(table, VAR_MONEY_OUT);
                }
                money_out = money_out + winnings;
                setObjVar(table, VAR_MONEY_OUT, money_out);
                prose_package pp = prose.getPackage(SID_BET_PAYED_SELF, bet_name, winnings);
                sendSystemMessageProse(player, pp);
                pp = prose.getPackage(SID_BET_PAYED_OTHER, player, null, bet_name, winnings);
                sendTableMessage(table, pp, player);
                return true;
            }
            else 
            {
                removeBet(table, bet_index, player_index);
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    public boolean payPoolBet(obj_id table, int bet_index, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (bet_index < 0 || bet_index > 20)
        {
            return false;
        }
        if (!isIdValid(player) || !player.isLoaded())
        {
            return false;
        }
        int player_index = gambling.getGamePlayerIndex(table, player);
        if (player_index < 0)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        if (bet_name == null)
        {
            return false;
        }
        float payout = PAYOUTS[bet_index];
        if (payout <= 0)
        {
            return false;
        }
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet." + bet_name;
        if (hasObjVar(table, bet_path))
        {
            int amt = getIntObjVar(table, bet_path);
            if (amt > 0)
            {
                int winnings = (int)(amt * payout);
                if (bet_index == 6 || bet_index == 7 || bet_index == 8)
                {
                    int jabba_pool = getJabbaPool(table);
                    if (bet_index == 8)
                    {
                        jabba_pool = jabba_pool / 2;
                    }
                    else 
                    {
                        winnings = amt * 50;
                        jabba_pool = jabba_pool / 4;
                    }
                    if (winnings > jabba_pool)
                    {
                        winnings = jabba_pool;
                    }
                    adjustJabbaPool(table, -winnings);
                }
                if (bet_index == 9 || bet_index == 10 || bet_index == 11)
                {
                    int oasis_pool = getOasisPool(table);
                    if (bet_index == 11)
                    {
                        oasis_pool = oasis_pool / 2;
                    }
                    else 
                    {
                        winnings = amt * 50;
                        oasis_pool = oasis_pool / 4;
                    }
                    if (winnings > oasis_pool)
                    {
                        winnings = oasis_pool;
                    }
                    adjustOasisPool(table, -winnings);
                }
                if (bet_index == 14)
                {
                    int saarlac_pool = getSaarlacPool(table);
                    if (winnings > saarlac_pool)
                    {
                        winnings = saarlac_pool;
                    }
                    adjustSaarlacPool(table, -winnings);
                }
                int total_amt = amt + winnings;
                LOG("LOG_CHANNEL", "player ->" + player + " bet_index ->" + bet_index + " amt ->" + amt + " winnings ->" + winnings);
                removeBet(table, bet_index, player_index);
                transferBankCreditsTo(table, player, total_amt, "noHandler", "noHandler", new dictionary());
                prose_package pp = prose.getPackage(SID_BET_PAYED_SELF, bet_name, winnings);
                sendSystemMessageProse(player, pp);
                pp = prose.getPackage(SID_BET_PAYED_OTHER, player, null, bet_name, winnings);
                sendTableMessage(table, pp, player);
                return true;
            }
            else 
            {
                removeBet(table, bet_index, player_index);
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    public boolean loseBet(obj_id table, int bet_index, obj_id player) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "loseBet -- table ->" + table + " bet_index ->" + bet_index + " player ->" + player);
        if (!isIdValid(table))
        {
            return false;
        }
        if (bet_index < 0 || bet_index > 20)
        {
            return false;
        }
        if (!isIdValid(player) || !player.isLoaded())
        {
            return false;
        }
        int player_index = gambling.getGamePlayerIndex(table, player);
        if (player_index < 0)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        if (bet_name == null)
        {
            return false;
        }
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet." + bet_name;
        if (hasObjVar(table, bet_path))
        {
            int amt = getIntObjVar(table, bet_path);
            if (amt > 0)
            {
                prose_package pp = prose.getPackage(SID_BET_LOST_SELF, bet_name, amt);
                sendSystemMessageProse(player, pp);
                pp = prose.getPackage(SID_BET_LOST_OTHER, player, null, bet_name, amt);
                sendTableMessage(table, pp, player);
                if (bet_index == 6 || bet_index == 7)
                {
                    adjustOasisPool(table, (int)(amt * .1));
                    pp = prose.getPackage(SID_BET_PUSH_TO_OASIS_SELF, bet_name, (int)(amt * .1));
                    sendSystemMessageProse(player, pp);
                    pp = prose.getPackage(SID_BET_PUSH_TO_OASIS_SELF, player, null, bet_name, (int)(amt * .1));
                    sendTableMessage(table, pp, player);
                }
                if (bet_index == 9 || bet_index == 10)
                {
                    adjustJabbaPool(table, (int)(amt * .1));
                    pp = prose.getPackage(SID_BET_PUSH_TO_JABBA_SELF, bet_name, (int)(amt * .1));
                    sendSystemMessageProse(player, pp);
                    pp = prose.getPackage(SID_BET_PUSH_TO_JABBA_SELF, player, null, bet_name, (int)(amt * .1));
                    sendTableMessage(table, pp, player);
                }
                if (bet_index == 14)
                {
                    adjustSaarlacPool(table, (int)(amt * .5));
                    pp = prose.getPackage(SID_BET_PUSH_TO_PIT_SELF, bet_name, (int)(amt * .5));
                    sendSystemMessageProse(player, pp);
                    pp = prose.getPackage(SID_BET_PUSH_TO_PIT_OTHER, player, null, bet_name, (int)(amt * .5));
                    sendTableMessage(table, pp, player);
                }
                removeBet(table, bet_index, player_index);
                return true;
            }
            else 
            {
                removeBet(table, bet_index, player_index);
                return false;
            }
        }
        else 
        {
            removeBet(table, bet_index, player_index);
            return false;
        }
    }
    public boolean pushBet(obj_id table, int bet_index, obj_id player) throws InterruptedException
    {
        if (!isIdValid(table))
        {
            return false;
        }
        if (!isIdValid(player) || !player.isLoaded())
        {
            return false;
        }
        int player_index = gambling.getGamePlayerIndex(table, player);
        if (player_index < 0)
        {
            return false;
        }
        String bet_name = wheelIndexToName(bet_index);
        if (bet_name == null)
        {
            return false;
        }
        String bet_path = gambling.VAR_GAME_PLAYERS + "." + player_index + ".bet." + bet_name;
        if (hasObjVar(table, bet_path))
        {
            int amt = getIntObjVar(table, bet_path);
            if (amt > 0)
            {
                if (bet_index == 1 || bet_index == 2 || bet_index == 4 || bet_index == 5 || bet_index == 17 || bet_index == 20)
                {
                    removeBet(table, bet_index, player_index);
                    int pushed_bet = 0;
                    switch (bet_index)
                    {
                        case 1:
                        pushed_bet = 16;
                        setBet(table, pushed_bet, player_index, amt);
                        break;
                        case 2:
                        pushed_bet = 17;
                        setBet(table, pushed_bet, player_index, amt);
                        break;
                        case 4:
                        pushed_bet = 19;
                        setBet(table, pushed_bet, player_index, amt);
                        break;
                        case 5:
                        pushed_bet = 20;
                        setBet(table, pushed_bet, player_index, amt);
                        break;
                        case 17:
                        pushed_bet = 15;
                        setBet(table, pushed_bet, player_index, amt);
                        break;
                        case 20:
                        pushed_bet = 18;
                        setBet(table, pushed_bet, player_index, amt);
                        break;
                    }
                    String pushed_bet_name = wheelIndexToName(pushed_bet);
                    prose_package pp = prose.getPackage(SID_BET_PUSH_SELF, pushed_bet_name, amt);
                    sendSystemMessageProse(player, pp);
                    pp = prose.getPackage(SID_BET_PUSH_OTHER, player, null, pushed_bet_name, amt);
                    sendTableMessage(table, pp, player);
                    dictionary d = new dictionary();
                    d.put("player", player);
                    messageTo(table, "handleRequestUpdatedUI", d, 0f, false);
                }
                else 
                {
                    loseBet(table, bet_index, player);
                    float saarlac_mult = .20f;
                    float jabba_mult = 0.0f;
                    float oasis_mult = 0.0f;
                    if (hasBetBeenPlaced(table, 6) || hasBetBeenPlaced(table, 7))
                    {
                        saarlac_mult = saarlac_mult - .09f;
                        jabba_mult = .09f;
                    }
                    if (hasBetBeenPlaced(table, 9) || hasBetBeenPlaced(table, 10))
                    {
                        saarlac_mult = saarlac_mult - .09f;
                        oasis_mult = .09f;
                    }
                    int saarlac_pool_adjust = (int)(amt * saarlac_mult);
                    if (saarlac_pool_adjust > 0)
                    {
                        adjustSaarlacPool(table, saarlac_pool_adjust);
                        prose_package pp = prose.getPackage(SID_BET_PUSH_TO_PIT_SELF, bet_name, (int)(amt * saarlac_mult));
                        sendSystemMessageProse(player, pp);
                        pp = prose.getPackage(SID_BET_PUSH_TO_PIT_OTHER, player, null, bet_name, (int)(amt * saarlac_mult));
                        sendTableMessage(table, pp, player);
                    }
                    if (jabba_mult > 0.0f)
                    {
                        int jabba_pool_adjust = (int)(amt * jabba_mult);
                        if (jabba_pool_adjust > 0)
                        {
                            adjustJabbaPool(table, jabba_pool_adjust);
                            prose_package pp = prose.getPackage(SID_BET_PUSH_TO_JABBA_SELF, bet_name, (int)(amt * jabba_mult));
                            sendSystemMessageProse(player, pp);
                            pp = prose.getPackage(SID_BET_PUSH_TO_JABBA_OTHER, player, null, bet_name, (int)(amt * jabba_mult));
                            sendTableMessage(table, pp, player);
                        }
                    }
                    if (oasis_mult > 0.0f)
                    {
                        int oasis_pool_adjust = (int)(amt * oasis_mult);
                        if (oasis_pool_adjust > 0)
                        {
                            adjustOasisPool(table, oasis_pool_adjust);
                            prose_package pp = prose.getPackage(SID_BET_PUSH_TO_OASIS_SELF, bet_name, (int)(amt * oasis_mult));
                            sendSystemMessageProse(player, pp);
                            pp = prose.getPackage(SID_BET_PUSH_TO_OASIS_OTHER, player, null, bet_name, (int)(amt * oasis_mult));
                            sendTableMessage(table, pp, player);
                        }
                    }
                }
                return true;
            }
            else 
            {
                removeBet(table, bet_index, player_index);
                return false;
            }
        }
        else 
        {
            removeBet(table, bet_index, player_index);
            return false;
        }
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
        String prompt = "The following is a summary of your current bets...\n\n";
        prompt += "Use /bet <amount> <spot> to wager.\n\n";
        prompt += "Cash : " + getCashBalance(player) + "\n";
        prompt += "Bank : " + getBankBalance(player) + "\n";
        prompt += "Total: " + getTotalMoney(player) + "\n";
        prompt += "\n";
        prompt += "Jabba's Palace: " + getJabbaPool(self) + "\n";
        prompt += "Oasis         : " + getOasisPool(self) + "\n";
        prompt += "Saarlac Pit   : " + getSaarlacPool(self) + "\n";
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
                for (int i = 0; i < numItems; i++)
                {
                    obj_var ov = ovl.getObjVar(i);
                    String name = ov.getName();
                    int val = ov.getIntData();
                    String entry = name + ":";
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
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            sendTableMessage(self, SID_NO_CASINO, null);
            return;
        }
        if (getOwner(structure) != getOwner(self))
        {
            sendTableMessage(self, SID_NO_CASINO, null);
            return;
        }
        int pools = (int)((getJabbaPool(self) + getOasisPool(self) + getSaarlacPool(self)) * 1.05);
        int balance = getBankBalance(self);
        int transfer = balance - pools;
        LOG("LOG_CHANNEL", "startWheelGame -- pools ->" + pools + " balance ->" + balance + " transfer ->" + transfer);
        if (transfer < 0)
        {
            transferBankCreditsTo(structure, self, transfer * -1, "tableTransferSuccess", "tableTransferFailed", new dictionary());
            return;
        }
        setObjVar(self, gambling.VAR_GAME_PLAYERS_IDS, players);
        int stampTime = getGameTime() + TIMER_BETTING;
        utils.setScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT, stampTime);
        for (int i = 0; i < players.length; i++)
        {
            String ovpath = gambling.VAR_GAME_PLAYERS + "." + players[i] + ".pid";
            if (!utils.hasScriptVar(self, ovpath))
            {
                showBetUi(self, players[i]);
            }
            sendSystemMessage(players[i], gambling.SID_PLACE_BETS);
        }
        dictionary d = new dictionary();
        d.put("stamp", stampTime);
        messageTo(self, "handleBetTimer", d, 30f, false);
    }
    public void stopWheelGame(obj_id self) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        LOG("LOG_CHANNEL", "stopWheelGame -- structure ->" + structure);
        if (isIdValid(structure))
        {
            if (getOwner(structure) == getOwner(self))
            {
                int balance = getBankBalance(self);
                LOG("LOG_CHANNEL", "stopWheelGame -- balance ->" + balance);
                if (balance > 0)
                {
                    transferBankCreditsTo(self, structure, balance, "noHandler", "noHandler", new dictionary());
                }
            }
        }
        utils.removeScriptVar(self, VAR_TOTAL_BETS);
        utils.removeScriptVar(self, gambling.VAR_TABLE_BET_ACCEPT);
        removeObjVar(self, gambling.VAR_GAME_BASE);
    }
}
