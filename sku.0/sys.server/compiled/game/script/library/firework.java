package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;

public class firework extends script.base_script
{
    public firework()
    {
    }
    public static final int SHOW_EVENT_MAX = 20;
    public static final float SHOW_DELAY_MIN = 1f;
    public static final float SHOW_DELAY_MAX = 10f;
    public static final String SCRIPT_PLAYER = "item.firework.player";
    public static final String SCRIPT_FIREWORK_CLEANUP = "item.firework.cleanup";
    public static final String SCRIPT_LAUNCHER = "item.firework.launcher";
    public static final String VAR_FIREWORK_BASE = "firework";
    public static final String VAR_FIREWORK_FX = VAR_FIREWORK_BASE + ".fx";
    public static final String VAR_SHOW_BASE = VAR_FIREWORK_BASE + ".show";
    public static final String VAR_SHOW_FX = VAR_SHOW_BASE + ".fx";
    public static final String VAR_SHOW_DELAY = VAR_SHOW_BASE + ".delay";
    public static final String SCRIPTVAR_COUNT = VAR_FIREWORK_BASE + ".cnt";
    public static final String TBL_FX = "datatables/firework/fx.iff";
    public static final String HANDLER_FIREWORK_PREPARE = "handleFireworkPrepare";
    public static final String HANDLER_FIREWORK_IGNITE = "handleFireworkIgnite";
    public static final String HANDLER_FIREWORK_LAUNCH = "handleFireworkLaunch";
    public static final String HANDLER_FIREWORK_EXPLOSION = "handleFireworkExplosion";
    public static final String HANDLER_FIREWORK_CLEANUP = "handleFireworkCleanup";
    public static final String STF = "firework";
    public static final String TEMPLATE_SHOW_LAUNCHER = "object/static/firework/show_launcher.iff";
    public static final string_id SID_YOU_CANNOT_ADD = new string_id(STF, "you_cannot_add");
    public static final string_id SID_NO_SHOWS_TO_REMOVE = new string_id(STF, "no_shows_to_remove");
    public static final string_id SID_NO_SHOWS_TO_MODIFY = new string_id(STF, "no_shows_to_modify");
    public static final string_id SID_NO_SHOWS_TO_DISPLAY = new string_id(STF, "no_shows_to_display");
    public static final string_id SID_NO_NEED_TO_REORDER = new string_id(STF, "no_need_to_reorder");
    public static final string_id SID_DUD_FIREWORK = new string_id(STF, "dud_firework");
    public static boolean launch(obj_id target, obj_id firework) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(firework))
        {
            return false;
        }
        if (!hasObjVar(firework, VAR_FIREWORK_BASE))
        {
            makeRandomEffect(firework);
        }
        String fx = getStringObjVar(firework, VAR_FIREWORK_FX);
        if (fx == null || fx.equals(""))
        {
            fx = getRandomFireworkEffect();
        }
        dictionary params = new dictionary();
        params.put("fx", fx);
        useCharge(firework);
        int cnt = utils.getIntScriptVar(target, SCRIPTVAR_COUNT);
        cnt++;
        utils.setScriptVar(target, SCRIPTVAR_COUNT, cnt);
        if (!hasScript(target, SCRIPT_PLAYER))
        {
            attachScript(target, SCRIPT_PLAYER);
        }
        messageTo(target, HANDLER_FIREWORK_PREPARE, params, 0, false);
        return true;
    }
    public static void launchShow(obj_id target, obj_id show) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(show))
        {
            return;
        }
        Vector show_fx = getResizeableStringArrayObjVar(show, VAR_SHOW_FX);
        Vector show_delay = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (show_fx == null || show_fx.size() == 0 || show_delay == null || show_delay.size() == 0)
        {
            return;
        }
        if (show_fx.size() != show_delay.size())
        {
            return;
        }
        location there = utils.findLocInFrontOfTarget(target, 0.75f);
        if (there == null)
        {
            there = getLocation(target);
        }
        obj_id launcher = createObject(TEMPLATE_SHOW_LAUNCHER, there);
        if (isIdValid(launcher))
        {
            attachScript(launcher, SCRIPT_LAUNCHER);
            dictionary d = new dictionary();
            d.put("fx", show_fx);
            d.put("delay", show_delay);
            d.put("player", target);
            d.put("idx", 0);
            messageTo(launcher, HANDLER_FIREWORK_LAUNCH, d, ((Float)show_delay.get(0)).floatValue(), false);
            destroyObject(show);
        }
    }
    public static boolean addShowEvent(obj_id target, obj_id show, obj_id firework) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(show) || !isIdValid(firework))
        {
            return false;
        }
        if (getCount(show) >= SHOW_EVENT_MAX)
        {
            sendSystemMessage(target, SID_YOU_CANNOT_ADD);
            return false;
        }
        String fw_fx = getStringObjVar(firework, VAR_FIREWORK_FX);
        Vector show_fx = getResizeableStringArrayObjVar(show, VAR_SHOW_FX);
        show_fx = utils.addElement(show_fx, fw_fx);
        Vector show_delay = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        show_delay = utils.addElement(show_delay, SHOW_DELAY_MIN);
        boolean litmus = true;
        litmus &= setObjVar(show, VAR_SHOW_FX, show_fx);
        litmus &= setObjVar(show, VAR_SHOW_DELAY, show_delay);
        if (litmus)
        {
            useCharge(firework);
            incrementCount(show, 1);
        }
        return litmus;
    }
    public static void removeShowEvent(obj_id target, obj_id show, int idx) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(show) || idx < 0)
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(target, SID_NO_SHOWS_TO_REMOVE);
            return;
        }
        Vector show_fx = getResizeableStringArrayObjVar(show, VAR_SHOW_FX);
        Vector show_delay = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (show_fx == null || show_fx.size() == 0 || show_delay == null || show_delay.size() == 0)
        {
            return;
        }
        if (show_fx.size() != show_delay.size())
        {
            return;
        }
        if (idx >= show_fx.size())
        {
            return;
        }
        show_fx = utils.removeElementAt(show_fx, idx);
        show_delay = utils.removeElementAt(show_delay, idx);
        if (show_fx.size() != show_delay.size())
        {
            return;
        }
        setObjVar(show, VAR_SHOW_FX, show_fx);
        setObjVar(show, VAR_SHOW_DELAY, show_delay);
        incrementCount(show, -1);
    }
    public static void setShowEventDelay(obj_id target, obj_id show, int idx, float delay) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(show) || idx < 0)
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(target, SID_NO_SHOWS_TO_MODIFY);
            return;
        }
        Vector show_delay = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (show_delay == null || show_delay.size() == 0)
        {
            return;
        }
        if (idx >= show_delay.size())
        {
            return;
        }
        if (delay < 0.1f)
        {
            delay = 0.1f;
        }
        if (delay > SHOW_DELAY_MAX)
        {
            delay = SHOW_DELAY_MAX;
        }
        show_delay.set(idx, new Float(delay));
        setObjVar(show, VAR_SHOW_DELAY, show_delay);
    }
    public static void showEventDataSUI(obj_id player, obj_id show) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show))
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_DISPLAY);
            return;
        }
        cleanupSUIScriptVars(show);
        Vector entries = getShowListEntries(show);
        if (entries == null || entries.size() == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_DISPLAY);
            return;
        }
        String title = "@firework:data_title";
        String prompt = "@firework:data_prompt";
        int pid = sui.listbox(show, player, prompt, sui.OK_ONLY, title, entries, "handleEventDataSUI");
        if (pid > -1)
        {
            setSUIScriptVars(show, pid, player);
        }
    }
    public static void showRemoveEventSUI(obj_id player, obj_id show) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show))
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_REMOVE);
            return;
        }
        cleanupSUIScriptVars(show);
        Vector entries = getShowListEntries(show);
        if (entries == null || entries.size() == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_DISPLAY);
            return;
        }
        String title = "@firework:remove_title";
        String prompt = "@firework:remove_prompt";
        int pid = sui.listbox(show, player, prompt, sui.OK_CANCEL, title, entries, "handleRemoveEventSUI");
        if (pid > -1)
        {
            setSUIScriptVars(show, pid, player);
        }
    }
    public static void showReorderEventsSUI(obj_id player, obj_id show) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show))
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(player, SID_NO_NEED_TO_REORDER);
            return;
        }
        cleanupSUIScriptVars(show);
        Vector entries = getShowListEntries(show);
        if (entries == null || entries.size() < 0)
        {
            sendSystemMessage(player, SID_NO_NEED_TO_REORDER);
            return;
        }
        String title = "@firework:reorder_title";
        String prompt = "@firework:reorder_prompt";
        int pid = sui.listbox(show, player, prompt, sui.MOVEUP_MOVEDOWN_DONE, title, entries, "handleReorderEventsSUI");
        if (pid > -1)
        {
            setSUIScriptVars(show, pid, player);
        }
    }
    public static void showModifyEventIndexSUI(obj_id player, obj_id show) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show))
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_MODIFY);
            return;
        }
        cleanupSUIScriptVars(show);
        Vector entries = getShowListEntries(show);
        if (entries == null || entries.size() == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_MODIFY);
            return;
        }
        String title = "@firework:modify_index_title";
        String prompt = "@firework:modify_index_prompt";
        int pid = sui.listbox(show, player, prompt, sui.OK_CANCEL, title, entries, "handleModifyEventIndexSUI");
        if (pid > -1)
        {
            setSUIScriptVars(show, pid, player);
        }
    }
    public static void showModifyEventTimeSUI(obj_id player, obj_id show, int idx) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show) || idx < 0)
        {
            return;
        }
        if (getCount(show) == 0)
        {
            sendSystemMessage(player, SID_NO_SHOWS_TO_MODIFY);
            return;
        }
        cleanupSUIScriptVars(show);
        Vector delays = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (delays == null || delays.size() == 0 || idx >= delays.size())
        {
            return;
        }
        int current = (int)(((Float)delays.get(idx)).floatValue() * 10);
        int available = (int)(SHOW_DELAY_MAX * 10) - current;
        String title = "@firework:modify_delay_title";
        String prompt = "@firework:modify_delay_prompt";
        int pid = sui.transfer(show, player, prompt, title, "Available", available, "Delay", current, "handleModifyEventTimeSUI");
        if (pid > -1)
        {
            setSUIScriptVars(show, pid, player, idx);
        }
    }
    public static void setSUIScriptVars(obj_id show, int pid, obj_id player) throws InterruptedException
    {
        utils.setScriptVar(show, "firework.pid", pid);
        utils.setScriptVar(show, "firework.player", player);
    }
    public static void setSUIScriptVars(obj_id show, int pid, obj_id player, int idx) throws InterruptedException
    {
        utils.setScriptVar(show, "firework.pid", pid);
        utils.setScriptVar(show, "firework.player", player);
        utils.setScriptVar(show, "firework.idx", idx);
    }
    public static void removeSUIScriptVars(obj_id show) throws InterruptedException
    {
        utils.removeScriptVar(show, "firework.pid");
        utils.removeScriptVar(show, "firework.player");
        utils.removeScriptVar(show, "firework.idx");
    }
    public static void cleanupSUIScriptVars(obj_id show) throws InterruptedException
    {
        if (utils.hasScriptVar(show, "firework.pid"))
        {
            int oldpid = utils.getIntScriptVar(show, "firework.pid");
            obj_id player = utils.getObjIdScriptVar(show, "firework.player");
            if (isIdValid(player))
            {
                sui.closeSUI(player, oldpid);
            }
            removeSUIScriptVars(show);
        }
    }
    public static Vector getShowListEntries(obj_id show) throws InterruptedException
    {
        Vector show_fx = getResizeableStringArrayObjVar(show, VAR_SHOW_FX);
        Vector show_delay = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (show_fx == null || show_fx.size() == 0 || show_delay == null || show_delay.size() == 0)
        {
            return null;
        }
        if (show_fx.size() != show_delay.size())
        {
            return null;
        }
        float time = 0;
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < show_fx.size(); i++)
        {
            time += ((Float)show_delay.get(i)).floatValue();
            String sTime = utils.formatTime(time);
            entries = utils.addElement(entries, "(" + sTime + "s) " + getString(new string_id("firework_n", ((String)show_fx.get(i)))));
        }
        if (entries == null || entries.size() == 0)
        {
            return null;
        }
        return entries;
    }
    public static void swapEvents(obj_id player, obj_id show, int idx1, int idx2) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show))
        {
            return;
        }
        if (idx1 < 0 || idx2 < 0)
        {
            sendSystemMessage(player, new string_id(STF, "cannot_reorder"));
            return;
        }
        Vector show_fx = getResizeableStringArrayObjVar(show, VAR_SHOW_FX);
        Vector show_delay = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (show_fx == null || show_fx.size() == 0 || show_delay == null || show_delay.size() == 0)
        {
            return;
        }
        if (show_fx.size() != show_delay.size())
        {
            return;
        }
        String tmpFx = ((String)show_fx.get(idx1));
        float tmpDelay = ((Float)show_delay.get(idx1)).floatValue();
        show_fx.set(idx1, ((String)show_fx.get(idx2)));
        show_delay.set(idx1, new Float(((Float)show_delay.get(idx2)).floatValue()));
        show_fx.set(idx2, tmpFx);
        show_delay.set(idx2, new Float(tmpDelay));
        setObjVar(show, VAR_SHOW_FX, show_fx);
        setObjVar(show, VAR_SHOW_DELAY, show_delay);
    }
    public static void setEventDelay(obj_id player, obj_id show, int idx, float delay) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(show))
        {
            return;
        }
        if (delay < 0.1f)
        {
            delay = 0.1f;
        }
        if (delay > SHOW_DELAY_MAX)
        {
            delay = SHOW_DELAY_MAX;
        }
        Vector delays = getResizeableFloatArrayObjVar(show, VAR_SHOW_DELAY);
        if (delays == null || delays.size() == 0 || idx >= delays.size())
        {
            return;
        }
        delays.set(idx, new Float(delay));
        if (setObjVar(show, VAR_SHOW_DELAY, delays))
        {
            prose_package ppDelayUpdated = prose.getPackage(new string_id(STF, "prose_delay_update"), idx + 1, delay);
            sendSystemMessageProse(player, ppDelayUpdated);
        }
    }
    public static void setEffect(obj_id firework, String fx, color pColor) throws InterruptedException
    {
        if (!isIdValid(firework) || fx == null || fx.equals("") || pColor == null)
        {
            return;
        }
        setObjVar(firework, VAR_FIREWORK_FX, fx);
    }
    public static void makeRandomEffect(obj_id firework) throws InterruptedException
    {
        if (!isIdValid(firework))
        {
            return;
        }
        String fx = getRandomFireworkEffect();
        setObjVar(firework, VAR_FIREWORK_FX, fx);
    }
    public static String getRandomFireworkEffect() throws InterruptedException
    {
        String[] names = dataTableGetStringColumn(TBL_FX, "name");
        if (names == null || names.length == 0)
        {
            return null;
        }
        int idx = rand(0, names.length - 1);
        return names[idx];
    }
    public static void dud(obj_id target) throws InterruptedException
    {
        sendSystemMessage(target, SID_DUD_FIREWORK);
        decrementFireworkCount(target);
    }
    public static void decrementFireworkCount(obj_id target) throws InterruptedException
    {
        int cnt = utils.getIntScriptVar(target, firework.SCRIPTVAR_COUNT);
        cnt--;
        if (cnt < 1)
        {
            utils.removeScriptVar(target, firework.SCRIPTVAR_COUNT);
            detachScript(target, firework.SCRIPT_PLAYER);
        }
    }
    public static void useCharge(obj_id firework) throws InterruptedException
    {
        incrementCount(firework, -1);
        int cnt = getCount(firework);
        if (cnt < 1)
        {
            destroyObject(firework);
        }
    }
}
