package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.groundquests;
import script.library.sui;
import script.library.utils;

public class star_destroyer_intro_player extends script.base_script
{
    public star_destroyer_intro_player()
    {
    }
    public static final string_id SID_NO_MONEY_MSG = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_no_money");
    public static final string_id SID_PURCHASE_MSG_01 = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_msg_01");
    public static final string_id SID_PURCHASE_MSG_02 = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_msg_02");
    public static final string_id SID_PURCHASE_MSG_03 = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_msg_03");
    public static final int TRACKING_DATA_COST = 1100;
    public int handlePurchaseTrackingData(obj_id self, dictionary params) throws InterruptedException
    {
        if (groundquests.isTaskActive(self, "star_destroyer_intro_rebel", "star_destroyer_intro_01") || groundquests.isTaskActive(self, "star_destroyer_intro_neutral", "star_destroyer_intro_01"))
        {
            if (!money.hasFunds(self, money.MT_TOTAL, TRACKING_DATA_COST))
            {
                String str_no_money = utils.packStringId(SID_NO_MONEY_MSG);
                sui.msgbox(self, str_no_money);
            }
            else 
            {
                obj_id starportDroid = utils.getObjIdScriptVar(self, "crafting4.starportDroid");
                utils.moneyOutMetric(self, "FS_QUESTS", TRACKING_DATA_COST);
                money.requestPayment(self, starportDroid, TRACKING_DATA_COST, "pass_fail", null, true);
                sendSystemMessage(self, SID_PURCHASE_MSG_01);
                messageTo(self, "handleTrackingDataPurchaseMsg02", null, rand(1, 4), false);
            }
            utils.removeScriptVar(self, "crafting4.starportDroid");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTrackingDataPurchaseMsg02(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, SID_PURCHASE_MSG_02);
        messageTo(self, "handleTrackingDataPurchaseMsg03", null, rand(1, 2), false);
        return SCRIPT_CONTINUE;
    }
    public int handleTrackingDataPurchaseMsg03(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, SID_PURCHASE_MSG_03);
        groundquests.sendSignal(self, "star_destroyer_intro_01");
        messageTo(self, "handleTrackingDataScriptCleanup", null, rand(1, 2), false);
        return SCRIPT_CONTINUE;
    }
    public int handleTrackingDataScriptCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "theme_park.dathomir.aurilia.star_destroyer_intro_player");
        return SCRIPT_CONTINUE;
    }
}
