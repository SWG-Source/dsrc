package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.hue;
import script.library.sui;
import script.library.utils;
import script.library.pclib;
import script.library.prose;
import script.library.pet_lib;
import script.library.vehicle;
import java.util.Enumeration;

public class vehicle_customizer extends script.base_script
{
    public vehicle_customizer()
    {
    }
    public static final String SCRIPTVAR_PID = "vehicletool.pid";
    public static final String SCRIPTVAR_PLAYER = "vehicletool.player";
    public static final String SCRIPTVAR_TARGET = "vehicletool.target";
    public static final String SCRIPTVAR_OPT = "vehicletool.opt";
    public static final String STF = "tool/customizer";
    public static final string_id MNU_CUSTOMIZE = new string_id(STF, "mnu_customize");
    public static final string_id OPT_COLOR_TRIM = new string_id(STF, "opt_color_trim");
    public static final string_id OPT_COLOR_FRAME = new string_id(STF, "opt_color_frame");
    public static final string_id SID_VEHICLE_ONLY = new string_id(STF, "vehicle_only");
    public static final string_id SID_NEED_CONSENT = new string_id(STF, "need_consent_vehicle");
    public static final string_id SID_NO_DATA = new string_id(STF, "no_data");
    public static final string_id PROSE_NO_CUSTOMIZATION = new string_id(STF, "prose_no_customization");
    public static final string_id VAR_SELECT_PROMPT = new string_id(STF, "var_select_prompt");
    public static final string_id PROSE_COMPLETE = new string_id(STF, "prose_complete");
    public static final string_id PROSE_COMPLETE_OWNER = new string_id(STF, "prose_complete_owner");
    public static final string_id PROSE_CANCELLED = new string_id(STF, "prose_cancelled");
    public static final string_id PROSE_CANCELLED_OWNER = new string_id(STF, "prose_cancelled_owner");
    public static final string_id PROSE_FAIL = new string_id(STF, "prose_fail");
    public static final string_id PROSE_FAIL_OWNER = new string_id(STF, "prose_fail_owner");
    public static final String TBL = "datatables/item/vehicle_customizer/indices.iff";
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        cleanupCustomizationMenu();
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1 || item == menu_info_types.ITEM_USE)
        {
            queueCommand(player, (-531683321), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdCustomizeVehicle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target))
        {
            sendSystemMessage(target, SID_VEHICLE_ONLY);
            return SCRIPT_CONTINUE;
        }
        obj_id vehicleobject = callable.getCallable(target, callable.CALLABLE_TYPE_RIDEABLE);
        if (!isIdValid(vehicleobject) || !(vehicle.isDriveableVehicle(vehicleobject)))
        {
            sendSystemMessage(target, SID_VEHICLE_ONLY);
            return SCRIPT_CONTINUE;
        }
        obj_id master = pet_lib.getMaster(vehicleobject);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (master != target)
        {
            sendSystemMessage(target, SID_NEED_CONSENT);
            return SCRIPT_CONTINUE;
        }
        cleanupCustomizationMenu(target);
        String template = utils.getTemplateFilenameNoPath(vehicleobject);
        dictionary row = dataTableGetRow(TBL, template);
        if (row == null || row.isEmpty())
        {
            prose_package ppNoCustomization = prose.getPackage(PROSE_NO_CUSTOMIZATION, vehicleobject);
            sendSystemMessageProse(target, ppNoCustomization);
            return SCRIPT_CONTINUE;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        Vector opt = new Vector();
        opt.setSize(0);
        Enumeration keys = row.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            if (!key.equals("TEMPLATE"))
            {
                String var = row.getString(key);
                if (var != null && !var.equals("") && !var.equals("none"))
                {
                    entries = utils.addElement(entries, "@" + STF + ":opt_color_" + toLower(key));
                    opt = utils.addElement(opt, var);
                }
            }
        }
        if (entries != null && entries.size() > 0 && opt != null && opt.size() > 0)
        {
            String title = utils.getStringName(vehicleobject);
            String prompt = utils.packStringId(VAR_SELECT_PROMPT);
            int pid = sui.listbox(self, target, prompt, sui.OK_CANCEL, title, entries, "handleSelectionSui");
            if (pid > -1)
            {
                utils.setScriptVar(self, SCRIPTVAR_PID, pid);
                utils.setScriptVar(self, SCRIPTVAR_PLAYER, target);
                utils.setScriptVar(self, SCRIPTVAR_TARGET, vehicleobject);
                utils.setScriptVar(self, SCRIPTVAR_OPT, opt);
            }
            return SCRIPT_CONTINUE;
        }
        prose_package ppNoCustomization = prose.getPackage(PROSE_NO_CUSTOMIZATION, vehicleobject);
        sendSystemMessageProse(target, ppNoCustomization);
        return SCRIPT_CONTINUE;
    }
    public void cleanupCustomizationMenu(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(self, SCRIPTVAR_PID))
        {
            int oldpid = utils.getIntScriptVar(self, SCRIPTVAR_PID);
            sui.closeSUI(player, oldpid);
            clearTrackingScriptVars(self);
        }
    }
    public void cleanupCustomizationMenu() throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(self, SCRIPTVAR_PLAYER))
        {
            obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_PLAYER);
            if (isIdValid(player))
            {
                cleanupCustomizationMenu(player);
            }
        }
    }
    public void clearTrackingScriptVars(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_PID);
        utils.removeScriptVar(self, SCRIPTVAR_PLAYER);
        utils.removeScriptVar(self, SCRIPTVAR_TARGET);
        utils.removeScriptVar(self, SCRIPTVAR_OPT);
    }
    public int handleSelectionSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_PLAYER);
        obj_id target = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        String[] opt = utils.getStringArrayScriptVar(self, SCRIPTVAR_OPT);
        clearTrackingScriptVars(self);
        if (!isIdValid(player) || !isIdValid(target) || opt == null || opt.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = pet_lib.getMaster(target);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || idx == -1 || idx >= opt.length)
        {
            prose_package ppCancel = prose.getPackage(PROSE_CANCELLED, target);
            sendSystemMessageProse(player, ppCancel);
            if (master != player)
            {
                prose_package ppCancelOwner = prose.getPackage(PROSE_CANCELLED_OWNER, player, target);
                sendSystemMessageProse(master, ppCancelOwner);
            }
            return SCRIPT_CONTINUE;
        }
        int pid = sui.colorize(self, player, target, opt[idx], "handleColorSelection");
        if (pid > -1)
        {
            utils.setScriptVar(self, SCRIPTVAR_PID, pid);
            utils.setScriptVar(self, SCRIPTVAR_PLAYER, player);
            utils.setScriptVar(self, SCRIPTVAR_TARGET, target);
            utils.setScriptVar(self, SCRIPTVAR_OPT, opt[idx]);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleColorSelection(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_PLAYER);
        obj_id target = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        String var = utils.getStringScriptVar(self, SCRIPTVAR_OPT);
        clearTrackingScriptVars(self);
        if (!isIdValid(player) || !isIdValid(target) || var == null || var.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = pet_lib.getMaster(target);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            prose_package ppCancel = prose.getPackage(PROSE_CANCELLED, target);
            sendSystemMessageProse(player, ppCancel);
            if (master != player)
            {
                prose_package ppCancelOwner = prose.getPackage(PROSE_CANCELLED_OWNER, player, target);
                sendSystemMessageProse(master, ppCancelOwner);
            }
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getColorPickerIndex(params);
        if (idx > -1)
        {
            dictionary d = new dictionary();
            d.put("player", player);
            d.put("target", target);
            d.put("master", master);
            d.put("tool", self);
            dictionary dc = new dictionary();
            dc.put(var, idx);
            d.put("dc", dc);
            messageTo(target, "handleSetCustomization", d, 0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int customizationSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id player = params.getObjId("player");
            obj_id target = params.getObjId("target");
            obj_id master = params.getObjId("master");
            if (isIdValid(player) && isIdValid(target) && isIdValid(master))
            {
                prose_package ppComplete = prose.getPackage(PROSE_COMPLETE, target);
                sendSystemMessageProse(player, ppComplete);
                if (master != player && exists(master) && master.isLoaded())
                {
                    prose_package ppCompleteOwner = prose.getPackage(PROSE_COMPLETE_OWNER, player, target);
                    sendSystemMessageProse(master, ppCompleteOwner);
                }
                utils.setScriptVar(target, "customizationUpdated", player);
            }
        }
        incrementCount(self, -1);
        if (getCount(self) < 1)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int customizationFailed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id player = params.getObjId("player");
            obj_id target = params.getObjId("target");
            obj_id master = params.getObjId("master");
            if (isIdValid(player) && isIdValid(target) && isIdValid(master))
            {
                prose_package ppFail = prose.getPackage(PROSE_FAIL, target);
                sendSystemMessageProse(player, ppFail);
                if (master != player && exists(master) && master.isLoaded())
                {
                    prose_package ppFailOwner = prose.getPackage(PROSE_FAIL_OWNER, player, target);
                    sendSystemMessageProse(master, ppFailOwner);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
