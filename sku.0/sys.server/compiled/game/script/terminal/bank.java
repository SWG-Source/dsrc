package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.utils;
import script.library.money;
import script.library.sui;

public class bank extends script.terminal.base.base_terminal
{
    public bank()
    {
    }
    public static final String SCRIPTVAR_BANK = "bank";
    public static final string_id SID_BANK_OPTIONS = new string_id("sui", "mnu_bank");
    public static final string_id SID_BANK_CREDITS = new string_id("sui", "bank_credits");
    public static final string_id SID_BANK_ITEMS = new string_id("sui", "bank_items");
    public static final string_id SID_BANK_DEPOSITALL = new string_id("sui", "bank_depositall");
    public static final string_id SID_BANK_WITHDRAWALL = new string_id("sui", "bank_withdrawall");
    public static final string_id SID_BANK_JOIN = new string_id("sui", "bank_join");
    public static final string_id SID_BANK_QUIT = new string_id("sui", "bank_quit");
    public static final string_id SID_CITY_BANK_BANNED = new string_id("city/city", "bank_banned");
    public static final string_id SID_ON_MOUNT = new string_id("error_message", "survey_on_mount");
    public static final string_id SID_BANK_MENU_OPEN = new string_id("error_message", "bank_menu_open");
    public static final string_id SID_GALACTIC_RESERVE = new string_id("sui", "bank_galactic_reserve");
    public static final string_id SID_GALACTIC_RESERVE_DEPOSIT = new string_id("sui", "bank_galactic_reserve_deposit");
    public static final string_id SID_GALACTIC_RESERVE_WITHDRAW = new string_id("sui", "bank_galactic_reserve_withdraw");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "banking_bankid", getCurrentSceneName());
        return super.OnInitialize(self);
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        setObjVar(player, "banking_bankid", getCurrentSceneName());
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        int mnu = mid.getId();
        int subBankCredits = mi.addSubMenu(mnu, menu_info_types.SERVER_MENU1, SID_BANK_CREDITS);
        obj_id bankTerminal = self;
        int subBankItems = mi.addSubMenu(mnu, menu_info_types.SERVER_MENU2, SID_BANK_ITEMS);
        int cash = getCashBalance(player);
        if (cash > 0)
        {
            int subBankDepositAll = mi.addSubMenu(mnu, menu_info_types.SERVER_MENU3, SID_BANK_DEPOSITALL);
        }
        int bank = getBankBalance(player);
        if (bank > 0)
        {
            int subBankWithdrawAll = mi.addSubMenu(mnu, menu_info_types.SERVER_MENU4, SID_BANK_WITHDRAWALL);
        }
        if (canAccessGalacticReserve(player))
        {
            int galactic_reserve = mi.addRootMenu(menu_info_types.SERVER_MENU50, SID_GALACTIC_RESERVE);
            mi.addSubMenu(galactic_reserve, menu_info_types.SERVER_MENU49, SID_GALACTIC_RESERVE_DEPOSIT);
            mi.addSubMenu(galactic_reserve, menu_info_types.SERVER_MENU48, SID_GALACTIC_RESERVE_WITHDRAW);
        }
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        setObjVar(player, "banking_bankid", getCurrentSceneName());
        obj_id bankTerminal = self;
        int city_id = getCityAtLocation(getLocation(self), 0);
        if ((city_id > 0) && city.isCityBanned(player, city_id))
        {
            sendSystemMessage(player, SID_CITY_BANK_BANNED);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            openBankMenu(player);
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            openBankMenu(player);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            openBankContainer(bankTerminal, player);
            obj_id inv = utils.getInventoryContainer(player);
            if (isIdValid(inv))
            {
                utils.requestContainerOpen(player, inv);
            }
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            int amt = getCashBalance(player);
            if (amt == 0)
            {
                money.nullTransactionError(player);
            }
            if (!money.deposit(player, amt))
            {
                debugSpeakMsg(player, "transaction request aborted by system...");
            }
            else 
            {
                closeBankTransferSui(self, player);
            }
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            int amt = getBankBalance(player);
            if (amt == 0)
            {
                money.nullTransactionError(player);
            }
            if (!money.withdraw(player, amt))
            {
                debugSpeakMsg(player, "transaction request aborted by system...");
            }
            else 
            {
                closeBankTransferSui(self, player);
            }
        }
        else if (item == menu_info_types.SERVER_MENU49)
        {
            depositToGalacticReserve(player);
        }
        else if (item == menu_info_types.SERVER_MENU48)
        {
            withdrawFromGalacticReserve(player);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean openBankMenu(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(player))
        {
            return false;
        }
        String scriptvar = SCRIPTVAR_BANK + "." + player;
        if (utils.hasScriptVar(self, scriptvar))
        {
            int oldPid = utils.getIntScriptVar(self, scriptvar);
            sui.closeSUI(player, oldPid);
            utils.removeScriptVar(self, scriptvar);
        }
        else 
        {
            if (utils.hasScriptVar(player, SCRIPTVAR_BANK + ".terminal"))
            {
                obj_id other = utils.getObjIdScriptVar(player, SCRIPTVAR_BANK + ".terminal");
                if (isIdValid(other) && (other != self))
                {
                    dictionary closer = new dictionary();
                    closer.put("player", player);
                    messageTo(other, "forceCloseBankSui", closer, 0f, false);
                }
            }
        }
        int pid = sui.bank(player);
        if (pid > -1)
        {
            utils.setScriptVar(player, SCRIPTVAR_BANK + ".terminal", self);
            utils.setScriptVar(self, scriptvar, pid);
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(self, "forceCloseBankSui", d, 30f, false);
            return true;
        }
        return false;
    }
    public int forceCloseBankSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            closeBankTransferSui(self, player);
            obj_id current = utils.getObjIdScriptVar(player, SCRIPTVAR_BANK + ".terminal");
            if (isIdValid(current) && (current == self))
            {
                utils.removeScriptVar(player, SCRIPTVAR_BANK + ".terminal");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void closeBankTransferSui(obj_id self, obj_id player) throws InterruptedException
    {
        String scriptvar = SCRIPTVAR_BANK + "." + player;
        if (utils.hasScriptVar(self, scriptvar))
        {
            int pid = utils.getIntScriptVar(self, scriptvar);
            sui.closeSUI(player, pid);
            utils.removeScriptVar(self, scriptvar);
        }
    }
    public int handleBankSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPTVAR_BANK + "." + player);
            messageTo(player, money.HANDLER_BANK_SUCCESS, params, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBankUnknownError(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPTVAR_BANK + "." + player);
            messageTo(player, money.HANDLER_BANK_UNKNOWN_ERROR, params, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBankDepositError(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPTVAR_BANK + "." + player);
            messageTo(player, money.HANDLER_BANK_DEPOSIT_ERROR, params, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBankWithdrawError(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPTVAR_BANK + "." + player);
            messageTo(player, money.HANDLER_BANK_WITHDRAW_ERROR, params, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
}
