package script.theme_park.nym;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.library.trial;
import script.obj_id;

public class nym_ambush_ai extends script.base_script
{
    public nym_ambush_ai()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_trigger";
    public static final String FAIL_SIGNAL = "fail_signal";
    public static final String DISTANCE_CHECK = "distance_check";
    public static final String WAVE_GROUP_ID = "waveEventGroupId";
    public static final int RADIUS = 100;
    public static final int MIN_DIST = 50;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("nym_themepark_log", "nym_ambush_ai: OnAttach: attaching script");
        messageTo(self, "handleIntelligenceLoop", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (!hasObjVar(self, "invln"))
        {
            return SCRIPT_CONTINUE;
        }
        setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        if (!hasObjVar(self, "invln"))
        {
            return SCRIPT_CONTINUE;
        }
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleIntelligenceLoop(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("nym_themepark_log", "nym_ambush_ai: handleIntelligenceLoop: loop start");
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("nym_themepark_log", "nym_ambush_ai: handleIntelligenceLoop: getting enemy");
        getRandomCombatTarget(self, parent);
        messageTo(self, "handleIntelligenceLoop", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public boolean getRandomCombatTarget(obj_id self, obj_id parent) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return false;
        }
        if (!isIdValid(self) || !exists(self))
        {
            return false;
        }
        if (!isIdValid(parent) || !exists(parent))
        {
            return false;
        }
        obj_id[] targets = getNPCsInRange(getLocation(self), 60);
        if (targets == null || targets.length == 0)
        {
            return false;
        }
        String myFaction = factions.getFaction(self);
        for (obj_id target : targets) {
            if (!isIdValid(target)) {
                continue;
            }
            if (target == self) {
                continue;
            }
            if (isInvulnerable(target)) {
                continue;
            }
            if (hasScript(target, "theme_park.nym.nym_ambush_ai")) {
                continue;
            }
            if (ai_lib.isDead(target)) {
                continue;
            }
            String targetFaction = factions.getFaction(target);
            if (targetFaction == null || targetFaction.length() <= 0) {
                continue;
            }
            if (factions.areCreaturesAllied(self, target)) {
                continue;
            }
            startCombat(self, target);
            return true;
        }
        return false;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        messageTo(self, "destroySelf", null, 10, false);
        return SCRIPT_CONTINUE;
    }
}
