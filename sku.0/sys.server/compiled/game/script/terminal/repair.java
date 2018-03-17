package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.cloninglib;
import script.library.money;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class repair extends script.terminal.base.base_terminal
{
    public repair()
    {
    }
    public static final string_id SID_MNU_REPAIR = new string_id("sui", "mnu_repair");
    public static final string_id SID_MNU_REPAIR_ALL = new string_id("sui", "mnu_repair_all");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isMob(self))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        int mnu = mid.getId();
        int repair_all = mi.addSubMenu(mnu, menu_info_types.SERVER_MENU1, SID_MNU_REPAIR_ALL);
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            openRepairSui(player);
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            confirmRepairAll(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConfirmRepairAll(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int pid = utils.getIntScriptVar(player, "repair.sui");
        if (btn == sui.BP_CANCEL)
        {
            closeOldRepairSui(player);
            return SCRIPT_CONTINUE;
        }
        int totalCost = utils.getIntScriptVar(player, "repair.totalCost");
        cloninglib.payRepairFee(player, self, totalCost);
        return SCRIPT_CONTINUE;
    }
    public void confirmRepairAll(obj_id player) throws InterruptedException
    {
        closeOldRepairSui(player);
        obj_id[] repairList = cloninglib.getAllRepairItems(player);
        int totalCost = cloninglib.getTotalRepairCost(player, repairList);
        if (totalCost == 0)
        {
            sendSystemMessage(player, new string_id("terminal_ui", "no_repair_items"));
            return;
        }
        prose_package prompt = prose.getPackage(new string_id("terminal_ui", "repair_all_confirm"), totalCost);
        String title = "@terminal_ui:repair_all_title";
        int pid = sui.msgbox(getSelf(), player, prompt, sui.YES_NO, title, sui.MSG_NORMAL, "handleConfirmRepairAll");
        utils.setScriptVar(player, "repair.sui", pid);
        utils.setScriptVar(player, "repair.totalCost", totalCost);
        utils.setScriptVar(player, "repair.repairList", repairList);
    }
    public void openRepairSui(obj_id player) throws InterruptedException
    {
        closeOldRepairSui(player);
        obj_id[] repairList = cloninglib.getAllRepairItems(player);
        if (repairList == null || repairList.length == 0)
        {
            sendSystemMessage(player, new string_id("terminal_ui", "no_repair_items"));
            return;
        }
        int[] costList = cloninglib.getItemRepairCostList(player, repairList);
        int totalCost = 0;
        for (int i = 0; i < costList.length; i++)
        {
            totalCost += costList[i];
        }
        utils.setScriptVar(player, "repair.itemList", repairList);
        utils.setScriptVar(player, "repair.costList", costList);
        utils.setScriptVar(player, "repair.totalCost", totalCost);
        String title = "@terminal_ui:repair_title";
        String prompt = buildRepairPrompt(player, -1);
        int pid = createSUIPage(sui.SUI_LISTBOX, getSelf(), player, "handleRepairSuiSelect");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL_REFRESH);
        sui.listboxUseOtherButton(pid, utils.packStringId(SID_MNU_REPAIR_ALL));
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        for (int i = 0; i < repairList.length; i++)
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, buildRepairEntry(repairList[i], costList[i]));
        }
        subscribeToSUIEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, "handleRepairSuiUpdate");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
        utils.setScriptVar(player, "repair.sui", pid);
    }
    public int handleRepairSuiUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int pid = utils.getIntScriptVar(player, "repair.sui");
        String prompt = buildRepairPrompt(player, idx);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int handleRepairSuiSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        int pid = utils.getIntScriptVar(player, "repair.sui");
        if (btn == sui.BP_REVERT)
        {
            confirmRepairAll(player);
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            closeOldRepairSui(player);
            return SCRIPT_CONTINUE;
        }
        obj_id[] itemList = utils.getObjIdArrayScriptVar(player, "repair.itemList");
        int[] costList = utils.getIntArrayScriptVar(player, "repair.costList");
        obj_id[] repairList = new obj_id[1];
        repairList[0] = itemList[idx];
        utils.setScriptVar(player, "repair.repairList", repairList);
        cloninglib.payRepairFee(player, self, costList[idx]);
        return SCRIPT_CONTINUE;
    }
    public String buildRepairEntry(obj_id item, int cost) throws InterruptedException
    {
        String entry = getEncodedName(item);
        entry += " \\%070( @terminal_ui:repair_prompt_cost " + cost + ")";
        return entry;
    }
    public String buildRepairPrompt(obj_id player, int index) throws InterruptedException
    {
        int totalCost = utils.getIntScriptVar(player, "repair.totalCost");
        String prompt = "@terminal_ui:repair_prompt_header " + totalCost + "\\#.";
        if (index > -1)
        {
            obj_id[] repairList = utils.getObjIdArrayScriptVar(player, "repair.itemList");
            int[] costList = utils.getIntArrayScriptVar(player, "repair.costList");
            obj_id item = repairList[index];
            int curHp = getHitpoints(item);
            int maxHp = getMaxHitpoints(item);
            prompt += "\n\n@terminal_ui:repair_prompt_item " + getEncodedName(item);
            prompt += "\n@terminal_ui:repair_prompt_damage " + (maxHp - curHp) + " (" + curHp + "/" + maxHp + ")";
            prompt += "\n@terminal_ui:repair_prompt_cost \\#pcontrast1 " + costList[index] + "\\#.";
        }
        return prompt;
    }
    public void closeOldRepairSui(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "repair.sui"))
        {
            int pid = utils.getIntScriptVar(player, "repair.sui");
            sui.closeSUI(player, pid);
        }
        utils.removeScriptVarTree(player, "repair");
    }
    public int handlePayRepairSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] repairList = utils.getObjIdArrayScriptVar(player, "repair.repairList");
        dictionary data = new dictionary();
        data.put("items", repairList);
        messageTo(player, "handleRepairItems", data, 0f, true);
        closeOldRepairSui(player);
        obj_id[] itemList = cloninglib.getAllRepairItems(player);
        if (itemList != null && itemList.length > 0)
        {
            openRepairSui(player);
        }
        return SCRIPT_CONTINUE;
    }
}
