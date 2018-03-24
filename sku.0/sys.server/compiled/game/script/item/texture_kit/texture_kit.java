package script.item.texture_kit;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.pet_lib;
import script.library.sui;
import script.library.utils;

public class texture_kit extends script.base_script
{
    public texture_kit()
    {
    }
    public static final String STF = "texture_kit";
    public static final string_id MNU_TEXTURE = new string_id("sui", "set_texture");
    public static final String BTN_TEXTURE = "@" + STF + ":btn_texture";
    public static final String PICK_A_TEXTURE_TITLE = "@" + STF + ":pick_a_texture_title";
    public static final String PICK_A_TEXTURE_PROMPT = "@" + STF + ":pick_a_texture_prompt";
    public static final String CONFIRM_TITLE = "@" + STF + ":confirm_title";
    public static final String CONFIRM_PROMPT = "@" + STF + ":confirm_prompt";
    public static final String PID_NAME = "texturePid";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getIntendedTarget(player);
        if (!isIdValid(target) || (!pet_lib.isDroidPet(target) && pet_lib.hasMaster(target) && getMaster(target) != player))
        {
            target = getLookAtTarget(player);
        }
        if (pet_lib.isDroidPet(target) && pet_lib.hasMaster(target) && getMaster(target) == player)
        {
            int mnuTexture = mi.addRootMenu(menu_info_types.ITEM_USE, MNU_TEXTURE);
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getIntendedTarget(player);
        if (!isIdValid(target) || (!pet_lib.isDroidPet(target) && pet_lib.hasMaster(target) && getMaster(target) != player))
        {
            target = getLookAtTarget(player);
        }
        if (pet_lib.isDroidPet(target) && pet_lib.hasMaster(target) && getMaster(target) == player)
        {
            if (item == menu_info_types.ITEM_USE)
            {
                custom_var myvar = getCustomVarByName(target, "/private/index_texture_1");
                if (myvar == null)
                {
                    string_id message = new string_id(STF, "not_paintable");
                    sendSystemMessage(player, message);
                    return SCRIPT_CONTINUE;
                }
                ranged_int_custom_var myRangedInt = (ranged_int_custom_var)myvar;
                int minRange = myRangedInt.getMinRangeInclusive();
                int maxRange = myRangedInt.getMaxRangeInclusive();
                if (sui.hasPid(player, PID_NAME))
                {
                    forceCloseSUIPage(sui.getPid(player, PID_NAME));
                    int oldValue = utils.getIntScriptVar(player, "texture.orginalValue");
                    setRangedIntCustomVarValue(target, "/private/index_texture_1", oldValue);
                }
                int varValue = getRangedIntCustomVarValue(target, "/private/index_texture_1");
                int pid = sui.transfer(self, player, PICK_A_TEXTURE_PROMPT, PICK_A_TEXTURE_TITLE, "min", minRange, "max", maxRange, "handleTextureChange");
                if (pid > -1)
                {
                    utils.setScriptVar(player, "texture.orginalValue", varValue);
                    utils.setScriptVar(player, "texture.target", target);
                    setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_TEXTURE);
                    showSUIPage(pid);
                    sui.setPid(player, pid, PID_NAME);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "charges";
        attribs[idx] = "" + getCount(self);
        idx++;
        return SCRIPT_CONTINUE;
    }
    public int handleTextureChange(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int newTextureIndex = sui.getTransferInputTo(params);
        int oldValue = utils.getIntScriptVar(player, "texture.orginalValue");
        utils.removeScriptVar(player, "texture.orginalValue");
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        obj_id playerContained = utils.getContainingPlayer(self);
        if (playerContained != player)
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.getObjIdScriptVar(player, "texture.target");
        sui.removePid(player, PID_NAME);
        setRangedIntCustomVarValue(target, "/private/index_texture_1", newTextureIndex);
        utils.setScriptVar(player, "texture.orginalValue", oldValue);
        pid = sui.msgbox(self, player, CONFIRM_PROMPT, sui.OK_CANCEL, CONFIRM_TITLE, "handleConfirmTexture");
        sui.setPid(player, pid, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handleConfirmTexture(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int oldValue = utils.getIntScriptVar(player, "texture.orginalValue");
        obj_id target = utils.getObjIdScriptVar(player, "texture.target");
        utils.removeScriptVarTree(player, "texture");
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            setRangedIntCustomVarValue(target, "/private/index_texture_1", oldValue);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            forceCloseSUIPage(pageId);
            setRangedIntCustomVarValue(target, "/private/index_texture_1", oldValue);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            setRangedIntCustomVarValue(target, "/private/index_texture_1", oldValue);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            setRangedIntCustomVarValue(target, "/private/index_texture_1", oldValue);
            return SCRIPT_CONTINUE;
        }
        obj_id playerContained = utils.getContainingPlayer(self);
        if (playerContained != player)
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            setRangedIntCustomVarValue(target, "/private/index_texture_1", oldValue);
            return SCRIPT_CONTINUE;
        }
        decrementCount(self);
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
}
