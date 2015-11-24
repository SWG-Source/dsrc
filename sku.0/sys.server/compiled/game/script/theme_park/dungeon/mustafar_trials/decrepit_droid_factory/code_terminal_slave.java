package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;
import script.library.sui;

public class code_terminal_slave extends script.base_script
{
    public code_terminal_slave()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_GET_ACCESS_SNIP = new string_id(DECREPIT_STF, "get_access_snip");
    public static final string_id SID_NOT_LOCKED = new string_id(DECREPIT_STF, "decrepit_not_locked");
    public static final string_id SID_TITLE = new string_id(DECREPIT_STF, "slave_text_title");
    public static final string_id SID_TEXT = new string_id(DECREPIT_STF, "slave_text_body");
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        boolean isUnlocked = trial.isCellPublic(self, trial.DECREPIT_ONE_TWO_STAIR);
        if (!isUnlocked)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_GET_ACCESS_SNIP);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_NOT_LOCKED);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!trial.isCellPublic(self, trial.DECREPIT_ONE_TWO_STAIR))
            {
                showAccessSnipitSui(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showAccessSnipitSui(obj_id terminal, obj_id player) throws InterruptedException
    {
        obj_id top = getTopMostContainer(terminal);
        if (getGameTime() >= trial.getResetTime(top))
        {
            trial.generateNewKeyCode(top);
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, terminal, player, "defaultHandler");
        string_id sid_title = SID_TITLE;
        string_id sid_body = SID_TEXT;
        String title = getString(sid_title);
        String body = getString(sid_body);
        if (title == null || title.equals(""))
        {
            title = "NO TITLE";
        }
        if (body == null || body.equals(""))
        {
            body = "Error displaying text";
        }
        body += getCodeSnipit(terminal);
        setSUIProperty(pid, "", "Size", "450,375");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, body);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        sui.showSUIPage(pid);
    }
    public String getCodeSnipit(obj_id terminal) throws InterruptedException
    {
        obj_id top = getTopMostContainer(terminal);
        int section = getIntObjVar(terminal, "sequence");
        String code = trial.getKeyCode(top);
        String sub = "";
        switch (section)
        {
            case 1:
            sub = code.substring(0, 2);
            return sub + "XXXX";
            case 2:
            sub = code.substring(2, 4);
            return "XX" + sub + "XX";
            case 3:
            sub = code.substring(4, 6);
            return "XXXX" + sub;
        }
        return sub;
    }
    public int handleResetTimer(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("timer", 10);
        messageTo(self, "showStatusFlyText", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int showStatusFlyText(obj_id self, dictionary params) throws InterruptedException
    {
        int timer = params.getInt("timer");
        if (timer == 0)
        {
            return SCRIPT_CONTINUE;
        }
        trial.doCountdownTimerFlyText(self, timer);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/code_terminal_slave/" + section, message);
        }
    }
}
