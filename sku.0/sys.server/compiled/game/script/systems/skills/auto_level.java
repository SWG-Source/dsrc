package script.systems.skills;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.respec;
import script.library.sui;
import script.library.utils;

public class auto_level extends script.base_script
{
    public auto_level()
    {
    }
    public static final String PID_NAME = "autoLevel";
    public static final String OBJVAR_AUTO_LEVEL_TO = "autoLevelTo";
    public static final string_id SID_CONSUME_PROMPT = new string_id("spam", "consume_auto_level_prompt");
    public static final string_id SID_CONSUME_TITLE = new string_id("spam", "consume_auto_level_title");
    public static final string_id SID_CONSUME_ITEM = new string_id("spam", "consume_auto_level");
    public static final string_id SID_CONSUME_TOO_HIGH = new string_id("spam", "consume_auto_level_too_high");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_CONSUME_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.SERVER_MENU1 && utils.isNestedWithinAPlayer(self))
        {
            if (sui.hasPid(player, PID_NAME))
            {
                int pid = sui.getPid(player, PID_NAME);
                forceCloseSUIPage(pid);
            }
            int autoLevelTo = getIntObjVar(self, OBJVAR_AUTO_LEVEL_TO);
            int currentLevel = getLevel(player);
            if (currentLevel >= autoLevelTo)
            {
                sendSystemMessage(player, SID_CONSUME_TOO_HIGH);
                sui.removePid(player, PID_NAME);
                return SCRIPT_CONTINUE;
            }
            int pid = sui.msgbox(self, player, "@" + SID_CONSUME_PROMPT, sui.YES_NO, "@" + SID_CONSUME_TITLE, "handlerSuiAutoLevel");
            sui.setPid(player, pid, PID_NAME);
            return SCRIPT_CONTINUE;
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
        if (hasObjVar(self, OBJVAR_AUTO_LEVEL_TO))
        {
            names[idx] = "level";
            attribs[idx] = "" + getIntObjVar(self, OBJVAR_AUTO_LEVEL_TO);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiAutoLevel(obj_id self, dictionary params) throws InterruptedException
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
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        int autoLevelTo = getIntObjVar(self, OBJVAR_AUTO_LEVEL_TO);
        int currentLevel = getLevel(player);
        if (currentLevel >= autoLevelTo)
        {
            sendSystemMessage(player, SID_CONSUME_TOO_HIGH);
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("auto_level", "Player " + getPlayerName(player) + "(" + player + ") tried to use the auto-level holocron and level their character from " + currentLevel + " to " + autoLevelTo + ", but could not because their current level is higher than the auto-level.");
            return SCRIPT_CONTINUE;
        }
        if (respec.autoLevelPlayer(player, autoLevelTo, true))
        {
            CustomerServiceLog("auto_level", "Player " + getPlayerName(player) + "(" + player + ") chose to use the auto-level holocron and level their character from " + currentLevel + " to " + autoLevelTo + ".");
            destroyObject(self);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
