package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.money;
import java.util.StringTokenizer;

public class promoter extends script.base_script
{
    public promoter()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        if (!hasScript(self, "systems.storyteller.storyteller_vendor"))
        {
            attachScript(self, "systems.storyteller.storyteller_vendor");
        }
        if (hasScript(self, "conversation.event_promoter"))
        {
            detachScript(self, "conversation.event_promoter");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        if (!hasScript(self, "systems.storyteller.storyteller_vendor"))
        {
            attachScript(self, "systems.storyteller.storyteller_vendor");
        }
        if (hasScript(self, "conversation.event_promoter"))
        {
            detachScript(self, "conversation.event_promoter");
        }
        return SCRIPT_CONTINUE;
    }
    public int showPromoterItemList(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String table = params.getString("table");
        utils.setScriptVar(player, "promoter.table_select", table);
        int[] cost = dataTableGetIntColumn(table, 2);
        String[] rawName = dataTableGetStringColumn(table, 1);
        String[] derivedName = new String[rawName.length];
        String[] concatMsg = new String[rawName.length];
        for (int i = 0; i < rawName.length; i++)
        {
            derivedName[i] = "@event_perk:" + rawName[i];
            string_id sid_name = utils.unpackString(derivedName[i]);
            String name = getString(sid_name);
            concatMsg[i] = "[" + cost[i] + "] " + name;
        }
        sui.listbox(self, player, "@event_perk:pro_show_list_desc", sui.OK_CANCEL, "@event_perk:pro_show_list_title", concatMsg, "selectPromoterItem", true);
        return SCRIPT_CONTINUE;
    }
    public int selectPromoterItem(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String table = utils.getStringScriptVar(player, "promoter.table_select");
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] templateTable = dataTableGetStringColumn(table, 0);
        int[] costTable = dataTableGetIntColumn(table, 2);
        if (templateTable == null || templateTable.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (costTable == null || costTable.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] allContents = utils.getAllItemsInBankAndInventory(player);
        int perkCount = 0;
        for (int i = 0; i < allContents.length; i++)
        {
            if (hasObjVar(allContents[i], "event_perk"))
            {
                perkCount++;
            }
            if (perkCount >= 5)
            {
                sendSystemMessage(player, new string_id("event_perk", "pro_too_many_perks"));
                return SCRIPT_CONTINUE;
            }
        }
        String template = templateTable[idx];
        int cost = costTable[idx];
        for (int i = 0; i < allContents.length; i++)
        {
            String whatsThis = getTemplateName(allContents[i]);
            if (whatsThis.equals("object/tangible/deed/event_perk/shuttle_beacon.iff") && whatsThis.equals(template))
            {
                sendSystemMessage(player, new string_id("event_perk", "promoter_only_one"));
                return SCRIPT_CONTINUE;
            }
        }
        if (cost < 1)
        {
            sendSystemMessage(player, new string_id("event_perk", "pro_error_cost"));
            return SCRIPT_CONTINUE;
        }
        int totalMoney = getTotalMoney(player);
        if (cost > totalMoney)
        {
            sendSystemMessage(player, new string_id("event_perk", "lottery_add_credits_nsf"));
            return SCRIPT_CONTINUE;
        }
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id purchasedItem = createObject(template, playerInventory, "");
        if (isIdValid(purchasedItem))
        {
            money.pay(player, self, cost, "derpDerpDerp", null);
            CustomerServiceLog("EventPerk", "(Promoter - [" + self + "]) Player [" + player + "] purchased " + purchasedItem + " at a cost of " + cost);
        }
        else 
        {
            sendSystemMessage(player, new string_id("event_perk", "promoter_full_inv"));
        }
        return SCRIPT_CONTINUE;
    }
}
