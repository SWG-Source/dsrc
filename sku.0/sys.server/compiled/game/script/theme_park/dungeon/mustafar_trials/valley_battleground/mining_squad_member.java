package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.library.ai_lib;
import script.library.pet_lib;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class mining_squad_member extends script.base_script
{
    public mining_squad_member()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        trial.setInterest(self);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "parent"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id parent = utils.getObjIdScriptVar(self, "parent");
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(parent, "troopEngaged", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (isPlayer(attacker) || pet_lib.isPet(attacker))
        {
            setHate(self, attacker, -5000.0f);
            int total = 0;
            for (int i : damage) {
                total += i;
            }
            addToHealth(self, total);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttackerCombatAction(obj_id self, obj_id weapon, obj_id defender) throws InterruptedException
    {
        if (isPlayer(defender))
        {
            setHate(self, defender, -5000.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        if (isPlayer(target) || pet_lib.isPet(target))
        {
            setHate(self, target, -5000.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/mining_squad_member/" + section, message);
        }
    }
}
