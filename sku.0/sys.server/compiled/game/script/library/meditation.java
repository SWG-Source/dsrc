package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.dot;
import script.library.chat;
import script.library.utils;
import script.library.consumable;
import script.library.trial;

public class meditation extends script.base_script
{
    public meditation()
    {
    }
    public static final float TIME_TICK = 5.0f;
    public static final float DELAY_MIN = 3f;
    public static final float DELAY_MAX = 7f;
    public static final float RAND_MODIFIER = 0.33f;
    public static final float POWERBOOST_RAMP = 60f;
    public static final String MOD_MEDITATE = "meditate";
    public static final String VAR_MEDITATION_BASE = "meditation";
    public static final String VAR_POWERBOOST_ACTIVE = "meditation.powerBoost";
    public static final String VAR_FORCE_OF_WILL_ACTIVE = "meditation.forceOfWill";
    public static final String SCRIPT_MEDITATE = "player.skill.meditate";
    public static final String HANDLER_MEDITATION_TICK = "handleMeditationTick";
    public static final String HANDLER_POWERBOOST_WANE = "handlePowerBoostWane";
    public static final String HANDLER_POWERBOOST_END = "handlePowerBoostEnd";
    public static final String STF_TERASKASI = "teraskasi";
    public static final string_id SID_MED_BEGIN = new string_id(STF_TERASKASI, "med_begin");
    public static final string_id SID_MED_END = new string_id(STF_TERASKASI, "med_end");
    public static final string_id SID_MED_FAIL = new string_id(STF_TERASKASI, "med_fail");
    public static final string_id SID_POWERBOOST_BEGIN = new string_id(STF_TERASKASI, "powerboost_begin");
    public static final string_id SID_POWERBOOST_WANE = new string_id(STF_TERASKASI, "powerboost_wane");
    public static final string_id SID_POWERBOOST_END = new string_id(STF_TERASKASI, "powerboost_end");
    public static final string_id SID_POWERBOOST_FAIL = new string_id(STF_TERASKASI, "powerboost_fail");
    public static final string_id SID_POWERBOOST_ACTIVE = new string_id(STF_TERASKASI, "powerboost_active");
    public static final string_id SID_POWERBOOST_MIND = new string_id(STF_TERASKASI, "powerboost_mind");
    public static final string_id SID_FORCEOFWILL = new string_id(STF_TERASKASI, "forceofwill");
    public static final string_id SID_FORCEOFWILL_UNSUCCESSFUL = new string_id(STF_TERASKASI, "forceofwill_unsuccessful");
    public static final string_id SID_FORCEOFWILL_FAIL = new string_id(STF_TERASKASI, "forceofwill_fail");
    public static final string_id SID_FORCEOFWILL_LOST = new string_id(STF_TERASKASI, "forceofwill_lost");
    public static final string_id SID_FORCEOFWILL_UNAVAILABLE = new string_id(STF_TERASKASI, "forceofwill_unavailable");
    public static final string_id SID_MUST_BE_MEDITATING = new string_id(STF_TERASKASI, "must_be_meditating");
    public static final string_id SID_STATE_PREVENTS_POWERBOOST = new string_id(STF_TERASKASI, "state_prevent_powerboost");
    public static final string_id SID_MIND_POOL_TOO_LOW = new string_id(STF_TERASKASI, "mind_pool_too_low");
    public static final string_id PROSE_CUREWOUND = new string_id(STF_TERASKASI, "prose_curewound");
    public static final String[] MEDITATE_BUFFS = 
    {
        "fs_meditate_1",
        "fs_meditate_2",
        "fs_meditate_3"
    };
    public static int getMeditationSkillMod(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return -1;
        }
        int meditate = getEnhancedSkillStatisticModifierUncapped(player, MOD_MEDITATE);
        return meditate;
    }
    public static boolean startMeditation(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        setState(player, STATE_MEDITATE, true);
        chat.setTempAnimationMood(player, "meditating");
        messageTo(player, HANDLER_MEDITATION_TICK, trial.getSessionDict(player, meditation.HANDLER_MEDITATION_TICK), TIME_TICK, false);
        sendSystemMessage(player, SID_MED_BEGIN);
        return true;
    }
    public static void endMeditation(obj_id player, boolean verbose) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        setState(player, STATE_MEDITATE, false);
        chat.resetTempAnimationMood(player);
        utils.removeScriptVar(player, VAR_MEDITATION_BASE);
        trial.bumpSession(player, meditation.HANDLER_MEDITATION_TICK);
        if (verbose)
        {
            sendSystemMessage(player, SID_MED_END);
        }
    }
    public static void endMeditation(obj_id player) throws InterruptedException
    {
        endMeditation(player, true);
    }
    public static boolean isMeditating(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (getState(player, STATE_MEDITATE) == 1)
        {
            return true;
        }
        return false;
    }
    public static float trance(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return -1f;
        }
        int modval = getMeditationSkillMod(player);
        if ((modval < 1) || !isMeditating(player))
        {
            return -1f;
        }
        float delay = 0f;
        LOG("meditate", "trance: slotDot -> DOT_BLEEDING");
        delay = slowDOT(player, modval, dot.DOT_BLEEDING);
        if (modval > 20 && delay == 0f)
        {
            LOG("meditate", "trance: slotDot -> DOT_POISON");
            delay = slowDOT(player, modval, dot.DOT_POISON);
        }
        if (modval > 40 && delay == 0f)
        {
            LOG("meditate", "trance: slotDot -> DOT_DISEASE");
            delay = slowDOT(player, modval, dot.DOT_DISEASE);
        }
        if (modval > 60 && delay == 0f)
        {
            LOG("meditate", "trance: cureWounds...");
            delay = cureWounds(player, modval);
        }
        LOG("meditate", "trance: pre-ret delay = " + delay);
        if (delay > 0f)
        {
            if (delay < DELAY_MIN)
            {
                delay = DELAY_MIN;
            }
            if (delay > DELAY_MAX)
            {
                delay = DELAY_MAX;
            }
        }
        return delay;
    }
    public static float slowDOT(obj_id player, int modval, String dotType) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return 0f;
        }
        int totalCured = 0;
        int toCure = getDOTReductionAmount(player);
        if (toCure > 0)
        {
            totalCured = dot.reduceDotTypeStrength(player, dotType, toCure);
        }
        if (totalCured < 0f)
        {
            return 0f;
        }
        return 5f;
    }
    public static int getDOTReductionAmount(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return -1;
        }
        int dotMod = getSkillStatMod(player, "private_med_dot");
        float ret = (float)dotMod * (1f + rand(-RAND_MODIFIER, RAND_MODIFIER));
        return Math.round(ret);
    }
    public static int getWoundReductionAmount(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return -1;
        }
        int dotMod = getSkillStatMod(player, "private_med_wound");
        float ret = (float)dotMod * (1f + rand(-RAND_MODIFIER, RAND_MODIFIER));
        return Math.round(ret);
    }
    public static float cureWounds(obj_id player, int modval) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return 0f;
        }
        dictionary toHeal = new dictionary();
        if ((toHeal == null) || (toHeal.isEmpty()))
        {
            LOG("meditate", "cureWounds: no wounds...");
            return 0f;
        }
        return 5f;
    }
    public static boolean forceOfWill(obj_id player, int delta) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (delta < 0)
        {
            delta = 0;
        }
        int modval = getMeditationSkillMod(player);
        if ((modval < 1) || getPosture(player) != POSTURE_INCAPACITATED)
        {
            return false;
        }
        if (delta < 10)
        {
            addShockWound(player, 100);
            sendSystemMessage(player, new string_id(STF_TERASKASI, "forceofwill_crit_fail"));
        }
        else if (delta < 40)
        {
            for (int i = 0; i < 3; i++)
            {
                addAttribModifier(player, i * 3, -200, 300, 0f, 0f);
            }
            addShockWound(player, 100);
            sendSystemMessage(player, new string_id(STF_TERASKASI, "forceofwill_marginal"));
        }
        else if (delta < 70)
        {
            for (int i = 0; i < 3; i++)
            {
                addAttribModifier(player, i * 3, -100, 300, 0f, 0f);
            }
            sendSystemMessage(player, new string_id(STF_TERASKASI, "forceofwill_normal"));
        }
        else 
        {
            sendSystemMessage(player, new string_id(STF_TERASKASI, "forceofwill_exceptional"));
        }
        innate.equalizeEffect(player);
        return true;
    }
    public static boolean forceOfWill(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        return forceOfWill(player, rand(1, getMeditationSkillMod(player)));
    }
    public static boolean powerBoost(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int modval = getMeditationSkillMod(player);
        if ((modval < 1))
        {
            return false;
        }
        if (!isMeditating(player))
        {
            sendSystemMessage(player, SID_MUST_BE_MEDITATING);
            return false;
        }
        if (hasObjVar(player, VAR_POWERBOOST_ACTIVE))
        {
            sendSystemMessage(player, SID_POWERBOOST_ACTIVE);
            return false;
        }
        int now = getGameTime();
        float duration = 300f + (float)(modval) * 3 * rand(0.95f, 1.05f);
        int maxMind = utils.getUnbuffedWoundedMaxAttrib(player, MIND);
        if (maxMind < 10)
        {
            sendSystemMessage(player, SID_STATE_PREVENTS_POWERBOOST);
            return false;
        }
        int boost = maxMind / 2;
        if (getAttrib(player, MIND) <= boost)
        {
            sendSystemMessage(player, SID_MIND_POOL_TOO_LOW);
            return false;
        }
        addAttribModifier(player, MIND, -boost, POWERBOOST_RAMP, 0f, 0f);
        addAttribModifier(player, "meditation.powerboost.mind", MIND, boost, duration, POWERBOOST_RAMP, POWERBOOST_RAMP, false, false, true);
        addAttribModifier(player, "meditation.powerboost.health", HEALTH, boost, duration, POWERBOOST_RAMP, POWERBOOST_RAMP, false, false, false);
        addAttribModifier(player, "meditation.powerboost.action", ACTION, boost, duration, POWERBOOST_RAMP, POWERBOOST_RAMP, false, false, false);
        int wane_time = (int)(duration + POWERBOOST_RAMP);
        int expire_time = wane_time + (int)POWERBOOST_RAMP;
        int expiration = now + expire_time;
        setObjVar(player, VAR_POWERBOOST_ACTIVE, expiration);
        dictionary d = new dictionary();
        d.put("expiration", expiration);
        messageTo(player, HANDLER_POWERBOOST_WANE, d, wane_time, false);
        messageTo(player, HANDLER_POWERBOOST_END, d, expire_time, true);
        messageTo(player, "handlePowerBoostLog", null, POWERBOOST_RAMP * 2, false);
        sendSystemMessage(player, SID_POWERBOOST_BEGIN);
        return true;
    }
}
