package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.sui;
import script.library.chat;
import script.library.money;
import script.library.utils;

public class npc_vendor extends script.base_script
{
    public npc_vendor()
    {
    }
    public static final String STF = "npe";
    public int npeHandleBuy(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int price = 1000;
        if ((params == null) || (params.isEmpty()))
        {
            sendSystemMessageTestingOnly(player, "Failing, params were empty!");
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        int totalMoney = getTotalMoney(player);
        dictionary d = new dictionary();
        d.put("price", price);
        obj_id pInv = utils.getInventoryContainer(player);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            obj_id stimpack = static_item.createNewItemFunction("item_stimpack_a_02_01", player);
            if (isIdValid(stimpack))
            {
                d.put("player", player);
                d.put("item", stimpack);
                d.put("npc", self);
                money.requestPayment(player, "Tyrral", price, "handleTransaction", d, true);
                break;
            }
            else 
            {
                CustomerServiceLog("NPE_VENDOR: ", "tried to create a item_stimpack_a_02_01 that had an invalid id.");
                break;
            }
            case 1:
            boolean playerHasItem = utils.playerHasStaticItemInBankOrInventory(player, "item_npe_tatooine_bug_juice_01_01");
            if (!playerHasItem)
            {
                obj_id bugJuice = static_item.createNewItemFunction("item_npe_tatooine_bug_juice_01_01", player);
                if (isIdValid(bugJuice))
                {
                    d.put("player", player);
                    d.put("item", bugJuice);
                    d.put("npc", self);
                    money.requestPayment(player, "Tyrral", price, "handleTransaction", d, true);
                    break;
                }
                else 
                {
                    CustomerServiceLog("NPE_VENDOR: ", "tried to create a item_npe_tatooine_bug_juice_01_01 that had an invalid id.");
                    break;
                }
            }
            string_id msgHasItem = new string_id(STF, "has_item");
            chat.publicChat(self, player, msgHasItem);
            break;
            default:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTransaction(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            debugSpeakMsg(self, "null?");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id item = params.getObjId("item");
        int price = params.getInt("price");
        if (!isIdValid(player) || !isIdValid(item) || price < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            chat.publicChat(self, player, new string_id(STF, "no_money"));
            destroyObject(item);
            return SCRIPT_CONTINUE;
        }
        string_id msgBoughtItem = new string_id(STF, "bought_item");
        chat.publicChat(self, player, msgBoughtItem);
        playMusic(player, "sound/music_acq_academic.snd");
        return SCRIPT_CONTINUE;
    }
}
