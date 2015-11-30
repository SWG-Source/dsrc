package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class instance_reset extends script.base_script
{
    public instance_reset()
    {
    }
    public static final String PID_NAME = "collectionConsume";
    public static final String SCRIPT_VAR_INSTANCE_LIST = "instance.list";
    public static final string_id SID_LIST_PROMPT = new string_id("spam", "instance_reset_list_prompt");
    public static final string_id SID_LIST_TITLE = new string_id("spam", "instance_reset_list_title");
    public static final string_id SID_CONSUME_PROMPT = new string_id("spam", "instance_reset_consume_prompt");
    public static final string_id SID_CONSUME_TITLE = new string_id("spam", "instance_reset_consume_title");
    public static final string_id SID_CONSUME_ITEM = new string_id("spam", "reset_instance");
    public static final string_id SID_NO_INSTANCE_LOCKOUT = new string_id("spam", "no_instance_lockout");
    public static final string_id SID_NO_LOCKOUT_FOR_INSTANCE = new string_id("spam", "no_lockout_for_instance");
    public static final string_id SID_INSTANCE_WAS_RESET = new string_id("spam", "instance_was_reset");
    public static final string_id SID_INSTANCE_GENERIC_ERROR = new string_id("spam", "instance_generic_error");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_CONSUME_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.ITEM_USE && utils.isNestedWithinAPlayer(self))
        {
            if (sui.hasPid(player, PID_NAME))
            {
                int pid = sui.getPid(player, PID_NAME);
                forceCloseSUIPage(pid);
            }
            obj_var_list varListinstance = getObjVarList(player, instance.PLAYER_INSTANCE);
            if (varListinstance == null)
            {
                sendSystemMessage(player, SID_NO_INSTANCE_LOCKOUT);
                CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "Player " + getPlayerName(player) + "(" + player + ") tried to use an instance reset device, but had no instances to reset.");
                return SCRIPT_CONTINUE;
            }
            String[] instanceList = varListinstance.getAllObjVarNames();
            if (instanceList == null || instanceList.length <= 0)
            {
                sendSystemMessage(player, SID_NO_INSTANCE_LOCKOUT);
                CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "Player " + getPlayerName(player) + "(" + player + ") tried to use an instance reset device, but had no instances to reset.");
                return SCRIPT_CONTINUE;
            }
            String[] localizedList = new String[instanceList.length];
            for (int i = 0; i < localizedList.length; ++i)
            {
                localizedList[i] = "@instance:" + instanceList[i];
            }
            utils.setScriptVar(self, SCRIPT_VAR_INSTANCE_LIST, instanceList);
            int pid = sui.listbox(self, player, "@" + SID_LIST_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onInstanceResetItemResponse", true, false);
            sui.setPid(player, pid, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int onInstanceResetItemResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_INSTANCE_LIST))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || idx == -1)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "item (" + self + ") was not consumed by Player " + getPlayerName(player) + "(" + player + ") this is because the item was no longer in their inventory.");
            return SCRIPT_CONTINUE;
        }
        String[] instanceList = utils.getStringArrayScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
        if (instanceList == null || instanceList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
        String instanceSelected = instanceList[idx];
        if (instanceSelected == null || instanceSelected.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SCRIPT_VAR_INSTANCE_LIST, instanceSelected);
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "Player " + getPlayerName(player) + "(" + player + ") elected to reset the instance timer for " + instanceSelected + ".");
        int newPid = sui.msgbox(self, player, "@" + SID_CONSUME_PROMPT, sui.YES_NO, "@" + SID_CONSUME_TITLE, "handlerConfirmResetInstance");
        sui.setPid(player, newPid, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handlerConfirmResetInstance(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_INSTANCE_LIST))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "item (" + self + ") was not consumed by Player " + getPlayerName(player) + "(" + player + ") this is because the item was no longer in their inventory.");
            return SCRIPT_CONTINUE;
        }
        String instanceToRemove = utils.getStringScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
        if (instanceToRemove == null || instanceToRemove.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_INSTANCE_LIST);
        dictionary instance_data = instance.getLockoutData(player, instanceToRemove);
        if (instance_data == null || instance_data.isEmpty())
        {
            sendSystemMessage(player, SID_NO_LOCKOUT_FOR_INSTANCE);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "item (" + self + ") was NOT consumed by Player " + getPlayerName(player) + "(" + player + ") this is because they were not locked out of " + instanceToRemove + ".");
            return SCRIPT_CONTINUE;
        }
        int newStartTime = instance_data.getInt("start_time");
        obj_id instance_id = instance_data.getObjId("instance_id");
        obj_id owner = instance_data.getObjId("owner");
        int startTime = instance_data.getInt("start_time");
        int resetAt = getCalendarTime();
        setObjVar(player, instance.PLAYER_INSTANCE + "." + instanceToRemove, "" + resetAt + "_" + instance_id + "_" + owner + "_" + newStartTime);
        instance_data = instance.getLockoutData(player, instanceToRemove);
        if (instance_data != null && !instance_data.isEmpty())
        {
            sendSystemMessage(player, SID_INSTANCE_GENERIC_ERROR);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "item (" + self + ") was NOT consumed by Player " + getPlayerName(player) + "(" + player + ") for some reason the instance data was not cleared for instance " + instanceToRemove + ".");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "item (" + self + ") was consumed by Player " + getPlayerName(player) + "(" + player + ") and the lockout timer for instance " + instanceToRemove + " was reset.");
        prose_package pp = new prose_package();
        prose.setStringId(pp, SID_INSTANCE_WAS_RESET);
        prose.setTU(pp, "@instance:" + instanceToRemove);
        sendSystemMessageProse(player, pp);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
