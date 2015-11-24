package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.chat;
import script.library.prose;
import script.library.money;
import script.library.ai_lib;
import script.library.utils;

public class junk_compactor extends script.base_script
{
    public junk_compactor()
    {
    }
    public static final String TBL = "datatables/npc/junk_dealer/ep3_myyydril_compactor.iff";
    public static final String SCRIPTVAR_JUNK_SUI = "relicdealer.biogenic.sui";
    public static final String SCRIPTVAR_JUNK_IDS = "relicdealer.biogenic.ids";
    public static final String SCRIPTVAR_SOLD = "soldAsJunk";
    public static final String STF = "loot_dealer";
    public static final string_id PROSE_NO_BUY = new string_id(STF, "prose_no_buy");
    public static final string_id PROSE_NO_BUY_ALL = new string_id(STF, "prose_no_buy_all");
    public static final string_id PROSE_SOLD_JUNK = new string_id(STF, "prose_sold_junk");
    public static final string_id PROSE_NO_SALE = new string_id(STF, "prose_no_sale");
    public static final string_id PROSE_SOLD_ALL_JUNK = new string_id(STF, "prose_sold_all_junk");
    public static final string_id PROSE_NO_ALL_SALE = new string_id(STF, "prose_no_all_sale");
    public static final string_id PROSE_JUNK_SOLD = new string_id(STF, "prose_junk_sold");
    public static final string_id MENU_SELL = new string_id("junk_dealer", "mnu_sell_junk");
    public static final String SELL_TITLE = "@" + STF + ":sell_title";
    public static final String SELL_PROMPT = "@" + STF + ":sell_prompt";
    public static final String NO_ITEMS_PROMPT = "@" + STF + ":no_items";
    public static final String BTN_SELL = "@" + STF + ":btn_sell";
    public static final String BTN_SELL_ALL = "@" + STF + ":btn_sell_all";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getAllJunkItems(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        obj_id[] contents = utils.getFilteredPlayerContents(player);
        if ((contents != null) && (contents.length > 0))
        {
            Vector junk = new Vector();
            junk.setSize(0);
            for (int i = 0; i < contents.length; i++)
            {
                if (!isCrafted(contents[i]) && !utils.isEquipped(contents[i]))
                {
                    String template = getTemplateName(contents[i]);
                    if ((template != null) && (!template.equals("")))
                    {
                        if (dataTableGetInt(TBL, template, "price") >= 0)
                        {
                            junk = utils.addElement(junk, contents[i]);
                        }
                    }
                }
            }
            if ((junk != null) && (junk.size() > 0))
            {
                return utils.toStaticObjIdArray(junk);
            }
        }
        return null;
    }
    public void showSellJunkSui(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPTVAR_JUNK_SUI))
        {
            int pidClose = utils.getIntScriptVar(player, SCRIPTVAR_JUNK_SUI);
            cleanupSellJunkSui(player);
            sui.closeSUI(player, pidClose);
        }
        obj_id[] junk = getAllJunkItems(player);
        if (junk != null && junk.length > 0)
        {
            String[] entries = new String[junk.length];
            for (int i = 0; i < junk.length; i++)
            {
                String template = getTemplateName(junk[i]);
                int price = dataTableGetInt(TBL, template, "price");
                String entry = "[" + price + "] " + getString(getNameStringId(junk[i]));
                entries[i] = entry;
            }
            if (entries != null && entries.length > 0)
            {
                int pid = sui.listbox(target, player, SELL_PROMPT, sui.OK_CANCEL_ALL, SELL_TITLE, entries, "handleSellJunkSui", false, false);
                if (pid > -1)
                {
                    setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_SELL);
                    setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, BTN_SELL_ALL);
                    showSUIPage(pid);
                    utils.setScriptVar(player, SCRIPTVAR_JUNK_SUI, pid);
                    utils.setBatchScriptVar(player, SCRIPTVAR_JUNK_IDS, junk);
                }
            }
        }
        else 
        {
            int msgPid = sui.msgbox(target, player, NO_ITEMS_PROMPT, sui.OK_ONLY, SELL_TITLE, "noHandler");
            cleanupSellJunkSui(player);
        }
    }
    public void cleanupSellJunkSui(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, SCRIPTVAR_JUNK_SUI);
        utils.removeBatchScriptVar(player, SCRIPTVAR_JUNK_IDS);
    }
    public int startDealing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        showSellJunkSui(player, self);
        return SCRIPT_CONTINUE;
    }
    public int handleSellJunkSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        obj_id[] junk = utils.getObjIdBatchScriptVar(player, SCRIPTVAR_JUNK_IDS);
        cleanupSellJunkSui(player);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            sellAllJunk(player, self);
        }
        else 
        {
            if (idx < 0)
            {
                return SCRIPT_CONTINUE;
            }
            if ((junk == null) || (junk.length == 0))
            {
                return SCRIPT_CONTINUE;
            }
            if (idx > junk.length - 1)
            {
                return SCRIPT_CONTINUE;
            }
            sellJunkItem(player, junk[idx], true);
        }
        return SCRIPT_CONTINUE;
    }
    public void sellJunkItem(obj_id player, obj_id item, boolean reshowSui) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(item))
        {
            return;
        }
        if (utils.hasScriptVar(item, SCRIPTVAR_SOLD))
        {
            prose_package ppSold = prose.getPackage(PROSE_JUNK_SOLD, item);
            sendSystemMessageProse(player, ppSold);
            return;
        }
        obj_id self = getSelf();
        String name = getTemplateName(item);
        int price = dataTableGetInt(TBL, name, "price");
        if (price > 0 && !hasObjVar(item, "quest_item"))
        {
            utils.setScriptVar(item, SCRIPTVAR_SOLD, getGameTime());
            dictionary params = new dictionary();
            params.put("item", item);
            params.put("price", price);
            params.put("reshowSui", reshowSui);
            money.systemPayout(money.ACCT_RELIC_DEALER, player, price, "handleSoldJunk", params);
        }
        else 
        {
            prose_package ppNoBuy = prose.getPackage(PROSE_NO_BUY, self, item);
            sendSystemMessageProse(player, ppNoBuy);
        }
    }
    public void sellJunkItem(obj_id player, obj_id item) throws InterruptedException
    {
        sellJunkItem(player, item, false);
    }
    public int handleSoldJunk(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id item = params.getObjId("item");
        if (!isIdValid(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_TARGET_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int retCode = params.getInt(money.DICT_CODE);
        if (retCode == money.RET_FAIL)
        {
            prose_package ppNoSale = prose.getPackage(PROSE_NO_SALE, self, item);
            sendSystemMessageProse(player, ppNoSale);
            utils.removeScriptVar(item, SCRIPTVAR_SOLD);
            return SCRIPT_CONTINUE;
        }
        int price = params.getInt("price");
        prose_package ppSoldJunk = prose.getPackage(PROSE_SOLD_JUNK, item, price);
        sendSystemMessageProse(player, ppSoldJunk);
        destroyObject(item);
        boolean reshowSui = params.getBoolean("reshowSui");
        if (reshowSui)
        {
            showSellJunkSui(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void sellAllJunk(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPTVAR_JUNK_SUI))
        {
            int pidClose = utils.getIntScriptVar(player, SCRIPTVAR_JUNK_SUI);
            cleanupSellJunkSui(player);
            sui.closeSUI(player, pidClose);
        }
        obj_id[] tmp = getAllJunkItems(player);
        if (tmp == null || tmp.length == 0)
        {
            int msgPid = sui.msgbox(target, player, NO_ITEMS_PROMPT, sui.OK_ONLY, SELL_TITLE, "noHandler");
            return;
        }
        Vector junk = new Vector();
        Vector toRemove = new Vector();
        toRemove.setSize(0);
        if (junk != null && junk.size() > 0)
        {
            int now = getGameTime();
            int total = 0;
            for (int i = 0; i < junk.size(); i++)
            {
                if (utils.hasScriptVar(((obj_id)junk.get(i)), SCRIPTVAR_SOLD))
                {
                    toRemove = utils.addElement(toRemove, ((obj_id)junk.get(i)));
                }
                else 
                {
                    String template = getTemplateName(((obj_id)junk.get(i)));
                    int price = dataTableGetInt(TBL, template, "price");
                    total += price;
                    utils.setScriptVar(((obj_id)junk.get(i)), SCRIPTVAR_SOLD, now);
                }
            }
            junk = utils.removeElements(junk, toRemove);
            if (total > 0 && junk != null && junk.size() > 0)
            {
                dictionary params = new dictionary();
                params.put("items", junk);
                params.put("total", total);
                money.systemPayout(money.ACCT_RELIC_DEALER, player, total, "handleSoldAllJunk", params);
            }
            else 
            {
                prose_package ppNoBuy = prose.getPackage(PROSE_NO_BUY_ALL, target);
                sendSystemMessageProse(player, ppNoBuy);
            }
        }
    }
    public int handleSoldAllJunk(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] junk = params.getObjIdArray("items");
        if (junk == null || junk.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_TARGET_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int retCode = params.getInt(money.DICT_CODE);
        if (retCode == money.RET_FAIL)
        {
            prose_package ppNoSale = prose.getPackage(PROSE_NO_ALL_SALE, self);
            sendSystemMessageProse(player, ppNoSale);
            for (int i = 0; i < junk.length; i++)
            {
                utils.removeScriptVar(junk[i], SCRIPTVAR_SOLD);
            }
            return SCRIPT_CONTINUE;
        }
        int total = params.getInt("total");
        prose_package ppSoldJunk = prose.getPackage(PROSE_SOLD_ALL_JUNK, self, total);
        sendSystemMessageProse(player, ppSoldJunk);
        utils.destroyObjects(junk);
        return SCRIPT_CONTINUE;
    }
}
