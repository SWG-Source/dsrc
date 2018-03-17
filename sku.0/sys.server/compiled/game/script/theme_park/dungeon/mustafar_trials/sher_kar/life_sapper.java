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
import script.library.healing;
import script.library.prose;
import script.library.combat;

public class life_sapper extends script.base_script
{
    public life_sapper()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_SHER_KAR_LIFESAPPER);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(trial.getTop(self), "lifeSapperDied", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "lifeTap", trial.getSessionDict(self), 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int doAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInDungeon(trial.getTop(self));
        obj_id closest = trial.getClosest(self, players);
        startCombat(self, closest);
        return SCRIPT_CONTINUE;
    }
    public int lifeTap(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id defender = getTarget(self);
        if (!isIdValid(defender))
        {
            return SCRIPT_CONTINUE;
        }
        int healthDamage = 200;
        obj_id sherKar = utils.getObjIdScriptVar(trial.getTop(self), trial.MONSTER_SHER_KAR);
        healing.healDamage(sherKar, healthDamage);
        healing.healDamage(self, healthDamage);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "symbiot_drain");
        pp.actor.set(self);
        pp.target.set(defender);
        pp.digitInteger = healthDamage;
        combat.sendCombatSpamMessageProse(defender, self, pp, true, true, false, COMBAT_RESULT_HIT);
        damage(defender, DAMAGE_ELEMENTAL_ELECTRICAL, HIT_LOCATION_BODY, healthDamage);
        messageTo(self, "lifeTap", trial.getSessionDict(self), 2, false);
        return SCRIPT_CONTINUE;
    }
}
