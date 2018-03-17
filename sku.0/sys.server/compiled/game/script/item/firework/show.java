package script.item.firework;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.firework;

public class show extends script.base_script
{
    public show()
    {
    }
    public static final string_id MNU_LAUNCH = new string_id(firework.STF, "mnu_launch");
    public static final string_id MNU_SHOW_DATA = new string_id(firework.STF, "mnu_show_data");
    public static final string_id MNU_ADD_EVENT = new string_id(firework.STF, "mnu_add_event");
    public static final string_id MNU_MODIFY_EVENT = new string_id(firework.STF, "mnu_modify_event");
    public static final string_id MNU_REMOVE_EVENT = new string_id(firework.STF, "mnu_remove_event");
    public static final string_id MNU_REORDER_SHOW = new string_id(firework.STF, "mnu_reorder_show");
    public static final string_id SID_NO_FIREWORKS_IN_SPACE = new string_id("space/space_interaction", "no_fireworks_in_space");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int eventCount = getCount(self);
        Vector show_fx = getResizeableStringArrayObjVar(self, firework.VAR_SHOW_FX);
        if(show_fx == null || (eventCount > 0 && show_fx.size() != eventCount))
        {
            setCount(self, 0);
            removeObjVar(self, firework.VAR_SHOW_FX);
            return SCRIPT_CONTINUE;
        }
        Vector show_delay = getResizeableFloatArrayObjVar(self, firework.VAR_SHOW_DELAY);
        if (show_delay == null || show_delay.size() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (show_fx.size() != show_delay.size())
        {
            setCount(self, 0);
            removeObjVar(self, firework.VAR_SHOW_FX);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < show_delay.size(); i++)
        {
            if (((Float)show_delay.get(i)).floatValue() < firework.SHOW_DELAY_MIN)
            {
                show_delay.set(i, new Float(firework.SHOW_DELAY_MIN));
            }
        }
        setObjVar(self, firework.VAR_SHOW_DELAY, show_delay);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (isIdValid(inv) && utils.isNestedWithin(self, inv))
        {
            int eventCount = getCount(self);
            if (eventCount > 0)
            {
                int mnuLaunch = mi.addRootMenu(menu_info_types.ITEM_USE, MNU_LAUNCH);
            }
            int mnuShowData = -1;
            if (!hasScript(self, "item.firework.premade_show"))
            {
                mnuShowData = mi.addRootMenu(menu_info_types.SERVER_MENU2, MNU_SHOW_DATA);
            }
            if (eventCount > 0 && mnuShowData > -1 && !hasScript(self, "item.firework.premade_show") || isGod(player))
            {
                int mnuRemove = mi.addSubMenu(mnuShowData, menu_info_types.SERVER_MENU3, MNU_REMOVE_EVENT);
                int mnuModify = mi.addSubMenu(mnuShowData, menu_info_types.SERVER_MENU4, MNU_MODIFY_EVENT);
                int mnuReorder = mi.addSubMenu(mnuShowData, menu_info_types.SERVER_MENU6, MNU_REORDER_SHOW);
            }
            if (eventCount < firework.SHOW_EVENT_MAX && !hasScript(self, "item.firework.premade_show"))
            {
                int mnuAdd = mi.addSubMenu(mnuShowData, menu_info_types.SERVER_MENU5, MNU_ADD_EVENT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_FIREWORKS_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1 || item == menu_info_types.ITEM_USE)
        {
            obj_id inv = utils.getInventoryContainer(player);
            if (isIdValid(inv) && utils.isNestedWithin(self, inv))
            {
                queueCommand(player, (-309362530), self, "show", COMMAND_PRIORITY_DEFAULT);
                return SCRIPT_CONTINUE;
            }
        }
        else if (item == menu_info_types.SERVER_MENU2 && !hasScript(self, "item.firework.premade_show"))
        {
            firework.showEventDataSUI(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU3 && !hasScript(self, "item.firework.premade_show"))
        {
            firework.showRemoveEventSUI(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU4 && !hasScript(self, "item.firework.premade_show"))
        {
            firework.showModifyEventIndexSUI(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU5 && !hasScript(self, "item.firework.premade_show"))
        {
            if (utils.hasScriptVar(self, "addEvent.pid"))
            {
                int oldpid = utils.getIntScriptVar(self, "addEvent.pid");
                obj_id oldplayer = utils.getObjIdScriptVar(self, "addEvent.player");
                if (isIdValid(oldplayer))
                {
                    sui.closeSUI(oldplayer, oldpid);
                }
                utils.removeScriptVarTree(self, "addEvent");
            }
            obj_id inv = utils.getInventoryContainer(player);
            if (isIdValid(inv))
            {
                obj_id[] tmpFireworks = utils.getContainedGOTObjects(inv, GOT_misc_firework, true, false);
                if (tmpFireworks != null)
                {
                    Vector fireworks = new Vector();
                    fireworks.setSize(0);
                    Vector entries = new Vector();
                    entries.setSize(0);
                    for (int i = 0; i < tmpFireworks.length; i++)
                    {
                        if (hasObjVar(tmpFireworks[i], firework.VAR_FIREWORK_FX))
                        {
                            fireworks = utils.addElement(fireworks, tmpFireworks[i]);
                            entries = utils.addElement(entries, utils.getStringName(tmpFireworks[i]) + " (" + getCount(tmpFireworks[i]) + ")");
                        }
                    }
                    if (entries != null && fireworks != null && entries.size() == fireworks.size())
                    {
                        String title = "Select Show Addition";
                        String prompt = "Select the firework to append to the end of the show package.";
                        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, entries, "handleAddEventSui");
                        if (pid > -1)
                        {
                            utils.setScriptVar(self, "addEvent.pid", pid);
                            utils.setScriptVar(self, "addEvent.player", player);
                            utils.setBatchScriptVar(self, "addEvent.fireworks", fireworks);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            firework.showReorderEventsSUI(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAddEventSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] fireworks = utils.getObjIdBatchScriptVar(self, "addEvent.fireworks");
        utils.removeScriptVarTree(self, "addEvent");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (fireworks == null || fireworks.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        firework.addShowEvent(player, self, fireworks[idx]);
        return SCRIPT_CONTINUE;
    }
    public int handleEventDataSUI(obj_id self, dictionary params) throws InterruptedException
    {
        firework.removeSUIScriptVars(self);
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveEventSUI(obj_id self, dictionary params) throws InterruptedException
    {
        firework.removeSUIScriptVars(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        firework.removeShowEvent(player, self, idx);
        firework.showRemoveEventSUI(player, self);
        return SCRIPT_CONTINUE;
    }
    public int handleReorderEventsSUI(obj_id self, dictionary params) throws InterruptedException
    {
        firework.removeSUIScriptVars(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_OK)
        {
            firework.swapEvents(player, self, idx, idx - 1);
        }
        else if (bp == sui.BP_REVERT)
        {
            firework.swapEvents(player, self, idx, idx + 1);
        }
        firework.showReorderEventsSUI(player, self);
        return SCRIPT_CONTINUE;
    }
    public int handleModifyEventIndexSUI(obj_id self, dictionary params) throws InterruptedException
    {
        firework.removeSUIScriptVars(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        firework.showModifyEventTimeSUI(player, self, idx);
        return SCRIPT_CONTINUE;
    }
    public int handleModifyEventTimeSUI(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "firework.idx"))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getIntScriptVar(self, "firework.idx");
        firework.removeSUIScriptVars(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        float delay = sui.getTransferInputTo(params) / 10f;
        firework.setEventDelay(player, self, idx, delay);
        firework.showModifyEventIndexSUI(player, self);
        return SCRIPT_CONTINUE;
    }
    public void addDebugRandomEvent(obj_id show) throws InterruptedException
    {
        String[] fxs = dataTableGetStringColumn(firework.TBL_FX, "name");
        if (fxs != null && fxs.length > 0)
        {
            Vector show_fx = getResizeableStringArrayObjVar(show, firework.VAR_SHOW_FX);
            Vector show_delay = getResizeableFloatArrayObjVar(show, firework.VAR_SHOW_DELAY);
            show_fx = utils.addElement(show_fx, fxs[rand(0, fxs.length - 1)]);
            show_delay = utils.addElement(show_delay, 0.1f);
            if (show_fx == null || show_fx.size() == 0 || show_delay == null || show_delay.size() == 0)
            {
                return;
            }
            if (show_fx.size() != show_delay.size())
            {
                return;
            }
            setCount(show, show_fx.size());
            setObjVar(show, firework.VAR_SHOW_FX, show_fx);
            setObjVar(show, firework.VAR_SHOW_DELAY, show_delay);
        }
    }
}
