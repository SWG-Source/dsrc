package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.sui;
import script.library.utils;
import script.library.pclib;
import script.library.prose;
import script.library.pet_lib;
import java.util.Enumeration;

public class droid_customizer extends script.base_script
{
    public droid_customizer()
    {
    }
    public static final String SCRIPTVAR_PID = "tool.pid";
    public static final String SCRIPTVAR_PLAYER = "tool.player";
    public static final String SCRIPTVAR_TARGET = "tool.target";
    public static final String SCRIPTVAR_OPT = "tool.opt";
    public static final String STF = "tool/customizer";
    public static final string_id MNU_CUSTOMIZE = new string_id(STF, "mnu_customize");
    public static final string_id OPT_COLOR_TRIM = new string_id(STF, "opt_color_trim");
    public static final string_id OPT_COLOR_FRAME = new string_id(STF, "opt_color_frame");
    public static final string_id SID_DROID_PET_ONLY = new string_id(STF, "droid_pet_only");
    public static final string_id SID_NEED_CONSENT = new string_id(STF, "need_consent");
    public static final string_id SID_NO_DATA = new string_id(STF, "no_data");
    public static final string_id PROSE_NO_CUSTOMIZATION = new string_id(STF, "prose_no_customization");
    public static final string_id VAR_SELECT_PROMPT = new string_id(STF, "var_select_prompt");
    public static final string_id PROSE_COMPLETE = new string_id(STF, "prose_complete");
    public static final string_id PROSE_COMPLETE_OWNER = new string_id(STF, "prose_complete_owner");
    public static final string_id PROSE_CANCELLED = new string_id(STF, "prose_cancelled");
    public static final string_id PROSE_CANCELLED_OWNER = new string_id(STF, "prose_cancelled_owner");
    public static final string_id PROSE_FAIL = new string_id(STF, "prose_fail");
    public static final string_id PROSE_FAIL_OWNER = new string_id(STF, "prose_fail_owner");
    public static final String TBL = "datatables/item/droid_customizer/indices.iff";
    public static final String[] CUSTOMIZATION_SKILL_MODS = 
    {
        "droid_customization"
    };
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
            obj_id lookat = getLookAtTarget(player);
            if (!isIdValid(lookat) || !pet_lib.isDroidPet(lookat))
            {
                lookat = getIntendedTarget(player);
                if (!isIdValid(lookat))
                {
                    sendSystemMessage(player, SID_DROID_PET_ONLY);
                    return SCRIPT_CONTINUE;
                }
            }
            queueCommand(player, (129295030), self, lookat.toString(), COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdCustomizeDroid(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pet = utils.stringToObjId(params);
        if (!isIdValid(pet) || !pet_lib.isDroidPet(pet))
        {
            sendSystemMessage(target, SID_DROID_PET_ONLY);
            return SCRIPT_CONTINUE;
        }
        obj_id master = pet_lib.getMaster(pet);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if ((master != target) && (!pclib.hasConsent(target, master)))
        {
            sendSystemMessage(target, SID_NEED_CONSENT);
            return SCRIPT_CONTINUE;
        }
        cleanupCustomizationMenu(target);
        String template = utils.getTemplateFilenameNoPath(pet);
        dictionary row = dataTableGetRow(TBL, template);
        if (row == null || row.isEmpty())
        {
            prose_package ppNoCustomization = prose.getPackage(PROSE_NO_CUSTOMIZATION, pet);
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
            String title = utils.getStringName(pet);
            String prompt = utils.packStringId(VAR_SELECT_PROMPT);
            int pid = sui.listbox(self, target, prompt, sui.OK_CANCEL, title, entries, "handleSelectionSui");
            if (pid > -1)
            {
                utils.setScriptVar(self, SCRIPTVAR_PID, pid);
                utils.setScriptVar(self, SCRIPTVAR_PLAYER, target);
                utils.setScriptVar(self, SCRIPTVAR_TARGET, pet);
                utils.setScriptVar(self, SCRIPTVAR_OPT, opt);
            }
            return SCRIPT_CONTINUE;
        }
        prose_package ppNoCustomization = prose.getPackage(PROSE_NO_CUSTOMIZATION, pet);
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
        int playerCustomizationMod = 0;
        int[] mods = getSkillStatisticModifiers(player, CUSTOMIZATION_SKILL_MODS);
        for (int i = 0; i < mods.length; ++i)
        {
            playerCustomizationMod += mods[i];
        }
        if (playerCustomizationMod < 32)
        {
            playerCustomizationMod = 32;
        }
        if (playerCustomizationMod > 64)
        {
            playerCustomizationMod = 64;
        }
        String rangeMax = String.valueOf(playerCustomizationMod);
        String customizationVar = opt[idx];
        customizationVar = customizationVar.trim();
        if (customizationVar.startsWith("/"))
        {
            customizationVar = customizationVar.substring(1);
        }
        int pid = createSUIPage(sui.SUI_COLORPICKER, self, player, "handleColorSelection");
        if (pid > -1)
        {
            setSUIProperty(pid, sui.COLORPICKER_COLORPICKER, sui.PROP_TARGETID, target.toString());
            setSUIProperty(pid, sui.COLORPICKER_COLORPICKER, sui.PROP_TARGETVAR, customizationVar);
            setSUIProperty(pid, sui.COLORPICKER_COLORPICKER, sui.PROP_TARGETRANGEMAX, rangeMax);
            setSUIProperty(pid, sui.COLORPICKER_TITLE, sui.PROP_TEXT, sui.DEFAULT_TITLE);
            subscribeToSUIProperty(pid, sui.COLORPICKER_COLORPICKER, sui.PROP_SELECTEDINDEX);
            showSUIPage(pid);
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
