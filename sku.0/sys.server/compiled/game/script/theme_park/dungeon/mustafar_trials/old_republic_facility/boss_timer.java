package script.theme_park.dungeon.mustafar_trials.old_republic_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;

public class boss_timer extends script.base_script
{
    public boss_timer()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
