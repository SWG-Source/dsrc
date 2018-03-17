package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.structure;
import script.library.utils;
import script.library.sui;
import script.library.money;
import script.library.cloninglib;
import script.library.prose;

public class cloning extends script.terminal.base.base_terminal
{
    public cloning()
    {
    }
    public static final String HANDLER_CLONE_CONFIRM = "handleCloneConfirm";
    public static final String HANDLER_CLONE_COUPON_CONFIRM = "handleCloneCouponConfirm";
    public static final string_id SID_SET_BIND_PT = new string_id("sui", "mnu_clone");
    public static final string_id SID_CONFIRM_PROMPT = new string_id("base_player", "clone_confirm_prompt");
    public static final string_id SID_CONFIRM_TITLE = new string_id("base_player", "clone_confirm_title");
    public static final string_id SID_CONFIRM_COUPON_PROMPT = new string_id("base_player", "clone_confirm_coupon_prompt");
    public static final string_id SID_CONFIRM_COUPON_TITLE = new string_id("base_player", "clone_confirm_coupon_title");
    public static final string_id SID_ALREADY_HAVE_CLONE_DATA = new string_id("base_player", "already_have_clone_data");
    public static final string_id SID_HAVE_COUPON_NOT_AUTH = new string_id("base_player", "have_coupon_not_auth");
    public static final string_id SID_HAVE_COUPON_NOT_VALID = new string_id("base_player", "have_coupon_not_valid");
    public static final String CLONING_COUPON = "object/tangible/item/new_player/new_player_cloning_coupon.iff";
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id bound = getObjIdObjVar(player, cloninglib.VAR_BIND_FACILITY);
            if (isIdValid(bound))
            {
                obj_id topMost = structure.getContainingBuilding(player);
                if (!isIdValid(topMost))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (topMost == bound)
                    {
                        sendSystemMessage(player, SID_ALREADY_HAVE_CLONE_DATA);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            obj_id clone_coupon = utils.getItemPlayerHasByTemplate(player, CLONING_COUPON);
            if (isIdValid(clone_coupon))
            {
                if (hasObjVar(clone_coupon, "owner"))
                {
                    obj_id owner = getObjIdObjVar(clone_coupon, "owner");
                    if (isIdValid(owner))
                    {
                        if (player == owner)
                        {
                            utils.setScriptVar(player, cloninglib.SCRVAR_CLONE_COUPON, clone_coupon);
                            String promptCoupon = utils.packStringId(SID_CONFIRM_COUPON_PROMPT);
                            String titleCoupon = utils.packStringId(SID_CONFIRM_COUPON_TITLE);
                            sui.msgbox(self, player, promptCoupon, sui.YES_NO, titleCoupon, HANDLER_CLONE_COUPON_CONFIRM);
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            String custLogMsg = "New Player Rewards: %TU tried to use a cloning facility coupon but isn't the owner. Rightful owner is %TT";
                            CustomerServiceLog("NEW_PLAYER_QUESTS", custLogMsg, player, owner);
                            if (isGod(player))
                            {
                                sendSystemMessage(player, SID_HAVE_COUPON_NOT_AUTH);
                            }
                        }
                    }
                }
                else 
                {
                    String custLogMsg = "New Player Rewards: %TU tried to use a cloning facility coupon it is invalid - does not have an owner.";
                    CustomerServiceLog("NEW_PLAYER_QUESTS", custLogMsg, player);
                    if (isGod(player))
                    {
                        sendSystemMessage(player, SID_HAVE_COUPON_NOT_VALID);
                    }
                }
            }
            if (!cloninglib.verifyFundsForCloning(player))
            {
                return SCRIPT_CONTINUE;
            }
            int cloneCost = cloninglib.getRegisterCloneCost(player);
            prose_package pp = prose.getPackage(SID_CONFIRM_PROMPT, cloneCost);
            String prompt = "\0" + packOutOfBandProsePackage(null, pp);
            String title = utils.packStringId(SID_CONFIRM_TITLE);
            int pid = utils.getIntScriptVar(player, "cloningTerminal.pid");
            if (pid != 0)
            {
                forceCloseSUIPage(pid);
            }
            pid = sui.msgbox(self, player, prompt, sui.YES_NO, title, HANDLER_CLONE_CONFIRM);
            utils.setScriptVar(player, "cloningTerminal.pid", pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCloneCouponConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if ((player == null) || (player == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        int btnPressed = sui.getIntButtonPressed(params);
        switch (btnPressed)
        {
            case sui.BP_OK:
            queueCommand(player, (727283009), self, "", COMMAND_PRIORITY_DEFAULT);
            break;
            case sui.BP_CANCEL:
            utils.removeScriptVar(player, cloninglib.SCRVAR_CLONE_COUPON);
            if (!cloninglib.verifyFundsForCloning(player))
            {
                return SCRIPT_CONTINUE;
            }
            String prompt = utils.packStringId(SID_CONFIRM_PROMPT);
            String title = utils.packStringId(SID_CONFIRM_TITLE);
            sui.msgbox(self, player, prompt, sui.YES_NO, title, HANDLER_CLONE_CONFIRM);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCloneConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        int btnPressed = sui.getIntButtonPressed(params);
        switch (btnPressed)
        {
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if ((player == null) || (player == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(player, (727283009), self, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int handleRequestedClone(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id facility = structure.getContainingBuilding(player);
        if (!isIdValid(facility) || !hasScript(facility, cloninglib.SCRIPT_CLONING_FACILITY))
        {
            sendSystemMessage(player, cloninglib.SID_BAD_CLONE_LOCATION);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, cloninglib.SCRVAR_CLONE_COUPON))
        {
            obj_id clone_coupon = utils.getObjIdScriptVar(player, cloninglib.SCRVAR_CLONE_COUPON);
            if (!isIdValid(clone_coupon))
            {
                return SCRIPT_CONTINUE;
            }
            utils.removeScriptVar(player, cloninglib.SCRVAR_CLONE_COUPON);
            destroyObject(clone_coupon);
            String custLogMsg = "New Player Rewards: %TU sucessfully used a cloning facility coupon.";
            CustomerServiceLog("NEW_PLAYER_QUESTS", custLogMsg, player);
            cloninglib.setBind(player, facility);
            return SCRIPT_CONTINUE;
        }
        int retVal = money.getReturnCode(params);
        int total = params.getInt(money.DICT_TOTAL);
        if (retVal == money.RET_SUCCESS)
        {
            if (money.bankTo(self, money.ACCT_CLONING, total))
            {
                cloninglib.setBind(player, facility);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
