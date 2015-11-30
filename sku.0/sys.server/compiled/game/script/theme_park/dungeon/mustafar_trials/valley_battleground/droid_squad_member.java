package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.ai_lib;
import script.library.trial;

public class droid_squad_member extends script.base_script
{
    public droid_squad_member()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setInterest(self);
        trial.markAsDroidArmy(self);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        trial.prepareCorpse(self);
        utils.setScriptVar(self, trial.BATTLEFIELD_DROID_CORPSE, true);
        return SCRIPT_CONTINUE;
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolPoints");
        ai_lib.setPatrolOncePath(self, patrolPoints);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/droid_squad_member/" + section, message);
        }
    }
}
