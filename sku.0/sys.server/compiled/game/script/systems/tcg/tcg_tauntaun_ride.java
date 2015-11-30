package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class tcg_tauntaun_ride extends script.base_script
{
    public tcg_tauntaun_ride()
    {
    }
    public static final string_id COLLECT_MONEY = new string_id("spam", "collect_money");
    public static final string_id COIN_BOX_TITLE = new string_id("spam", "coin_box_title");
    public static final string_id COIN_BOX_PROMPT = new string_id("spam", "coin_box_prompt");
    public static final String SOUND_01 = new String("sound/tcg_tauntaun_carnival_ride.snd");
    public static final String ANIMATION_ACTIVE_RIDE = "trick_2";
    public static final String LOCKOUT = "tauntaun_lockout";
    public static final String MONEY_TOTAL = "money_total";
    public static final int RIDE_COST = 25;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == getOwner(self))
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, COLLECT_MONEY);
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "insert_credits"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (player == getOwner(self))
            {
                activateTauntaunRide(player, self);
            }
            else 
            {
                if (utils.hasScriptVar(self, LOCKOUT))
                {
                    sendSystemMessage(player, new string_id("spam", "tauntaun_ride_already_active"));
                    return SCRIPT_CONTINUE;
                }
                if (money.hasFunds(player, money.MT_TOTAL, RIDE_COST))
                {
                    sui.msgbox(self, player, "@spam:tauntaun_ride_pay", sui.OK_CANCEL, "@spam:tauntaun_ride_pay_title", sui.MSG_QUESTION, "handlePayForTauntaunRide");
                }
                else 
                {
                    sendSystemMessage(player, new string_id("spam", "not_enough_credits"));
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasObjVar(self, MONEY_TOTAL))
            {
                int money = getIntObjVar(self, MONEY_TOTAL);
                int pid = sui.transfer(self, player, getString(COIN_BOX_PROMPT), getString(COIN_BOX_TITLE), "Available", money, "Amount", 0, "transferTauntaunEarnings");
                sui.showSUIPage(pid);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int TauntaunAnimationLockout(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, LOCKOUT))
        {
            utils.removeScriptVar(self, LOCKOUT);
        }
        return SCRIPT_CONTINUE;
    }
    public int transferTauntaunEarnings(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int collectionAmount = sui.getTransferInputTo(params);
        if (player != getOwner(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (collectionAmount > 0)
            {
                int objvarAmount = getIntObjVar(self, MONEY_TOTAL);
                if (collectionAmount > objvarAmount)
                {
                    sendSystemMessage(player, new string_id("spam", "credit_problem"));
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "success_tauntaun_collect"));
                    pp = prose.setDI(pp, collectionAmount);
                    sendSystemMessageProse(player, pp);
                    money.pay(self, player, collectionAmount, "", null, false);
                    objvarAmount -= collectionAmount;
                    setObjVar(self, MONEY_TOTAL, objvarAmount);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateTauntaunRide(obj_id player, obj_id ride) throws InterruptedException
    {
        doAnimationAction(ride, ANIMATION_ACTIVE_RIDE);
        playClientEffectObj(player, "clienteffect/tcg_tauntaun_carnival_ride.cef", ride, "");
        utils.setScriptVar(ride, LOCKOUT, 1);
        messageTo(ride, "TauntaunAnimationLockout", null, 60.0f, false);
    }
    public int handlePayForTauntaunRide(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        money.pay(player, self, RIDE_COST, "", null, true);
        if (hasObjVar(self, MONEY_TOTAL))
        {
            int moneyTotal = getIntObjVar(self, MONEY_TOTAL);
            moneyTotal += RIDE_COST;
            setObjVar(self, MONEY_TOTAL, moneyTotal);
        }
        else 
        {
            setObjVar(self, MONEY_TOTAL, RIDE_COST);
        }
        activateTauntaunRide(player, self);
        return SCRIPT_CONTINUE;
    }
}
