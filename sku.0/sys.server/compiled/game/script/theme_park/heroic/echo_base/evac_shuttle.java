package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.instance;
import script.library.trial;

public class evac_shuttle extends script.base_script
{
    public evac_shuttle()
    {
    }
    public static final String DECREPIT_STF = "sequencer_spam";
    public static final string_id SID_EXIT_TRIAL = new string_id(DECREPIT_STF, "exit_echo");
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
        mi.addRootMenu(menu_info_types.CONVERSE_START, SID_EXIT_TRIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.CONVERSE_START)
        {
            instance.requestExitPlayer(instance.getInstanceName(trial.getParent(self)), player);
        }
        return SCRIPT_CONTINUE;
    }
}
