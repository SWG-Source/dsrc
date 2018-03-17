package script.theme_park.dungeon.mustafar_trials.sher_kar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.trial;
import script.library.space_dungeon;
import script.library.combat;
import script.library.prose;

public class malfosa extends script.base_script
{
    public malfosa()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_SHER_KAR_CONSORT);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
}
