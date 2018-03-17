package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.combat;
import script.library.trial;
import script.library.utils;

public class working_player extends script.base_script
{
    public working_player()
    {
    }
    public static final boolean DO_LOGGING = true;
    public int dualityDetonation(obj_id self, dictionary params) throws InterruptedException
    {
        final int DUALITY_DAMAGE = 8000;
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "duality_detonate_self");
        pp.actor.set(self);
        pp.target.set(self);
        pp.digitInteger = DUALITY_DAMAGE;
        combat.sendCombatSpamMessageProse(self, self, pp, true, true, false, COMBAT_RESULT_HIT);
        damage(self, DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, DUALITY_DAMAGE);
        obj_id[] targets = trial.getValidTargetsInRadius(self, 8.0f);
        if (targets == null || targets.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            if (targets[i] == self)
            {
                continue;
            }
            pp.stringId = new string_id("cbt_spam", "duality_detonate_other");
            pp.target.set(targets[i]);
            combat.sendCombatSpamMessageProse(self, targets[i], pp, true, true, false, COMBAT_RESULT_HIT);
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, DUALITY_DAMAGE);
        }
        playClientEffectObj(self, trial.PRT_WORKING_DUALITY_EXPLOSION, self, "root");
        return SCRIPT_CONTINUE;
    }
    public int aurek_diminish(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id cell = loc.cell;
        if (!isIdValid(cell) || (!(getCellName(cell)).equals("smallroom24") && !(getCellName(cell)).equals("smallroom21")))
        {
            messageTo(self, "dualityDetonation", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int besh_diminish(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id cell = loc.cell;
        if (!isIdValid(cell) || (!(getCellName(cell)).equals("smallroom24") && !(getCellName(cell)).equals("smallroom21")))
        {
            messageTo(self, "dualityDetonation", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (DO_LOGGING)
        {
            LOG("doLogging/working_player/" + section, message);
        }
    }
}
