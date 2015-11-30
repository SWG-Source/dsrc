package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.space_dungeon;
import script.library.instance;

public class exit_terminal extends script.base_script
{
    public exit_terminal()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
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
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_EXIT_TRIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            confirmEject(player, self);
        }
        return SCRIPT_CONTINUE;
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
        LOG("space_dungeon", "Eject player was called");
        instance.requestExitPlayer("mustafar_volcano", player);
        return SCRIPT_CONTINUE;
    }
}
