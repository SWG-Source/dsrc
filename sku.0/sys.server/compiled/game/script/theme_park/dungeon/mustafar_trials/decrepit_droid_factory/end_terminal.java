package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.sui;
import script.library.space_dungeon;
import script.library.instance;

public class end_terminal extends script.base_script
{
    public end_terminal()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_ACTIVATE_FACTORY = new string_id(DECREPIT_STF, "activate_factory");
    public static final string_id SID_FACTORY_ACTIVE = new string_id(DECREPIT_STF, "factory_active");
    public static final string_id SID_EXIT_TRIAL = new string_id(DECREPIT_STF, "decrepit_exit");
    public static final string_id SID_EXIT_TRIAL_CONFIRM = new string_id(DECREPIT_STF, "decrepit_exit_confirm");
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
        boolean decrepitDefeated = trial.isDecrepitDefeated(self);
        if (!decrepitDefeated)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_FACTORY);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_FACTORY_ACTIVE);
            mi.addRootMenu(menu_info_types.ITEM_USE_SELF, SID_EXIT_TRIAL);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!trial.isDecrepitDefeated(self))
            {
                winTrial(self);
            }
        }
        if (item == menu_info_types.ITEM_USE_SELF)
        {
            if (trial.isDecrepitDefeated(self))
            {
                confirmEject(player, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void winTrial(obj_id terminal) throws InterruptedException
    {
        obj_id top = trial.getTop(terminal);
        messageTo(top, "winTrial", null, 0, false);
    }
    public void confirmEject(obj_id player, obj_id terminal) throws InterruptedException
    {
        String confirm = "@" + SID_EXIT_TRIAL_CONFIRM;
        int pid = sui.msgbox(terminal, player, confirm, sui.YES_NO, "msgDungeonEjectConfirmed");
    }
    public int msgDungeonEjectConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        String button = params.getString("buttonPressed");
        obj_id player = params.getObjId("player");
        if (button.equals("Cancel"))
        {
            return SCRIPT_CONTINUE;
        }
        instance.requestExitPlayer("decrepit_droid_factory", player);
        return SCRIPT_CONTINUE;
    }
}
