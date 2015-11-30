package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.combat;
import script.library.utils;
import script.library.prose;
import script.library.meditation;
import script.library.player_structure;

public class teraskasi extends script.systems.combat.combat_base
{
    public teraskasi()
    {
    }
    public int cmdForceOfWill(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int modval = meditation.getMeditationSkillMod(self);
        if (modval < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (getPosture(self) != POSTURE_INCAPACITATED)
        {
            return SCRIPT_CONTINUE;
        }
        int stamp = getIntObjVar(self, meditation.VAR_FORCE_OF_WILL_ACTIVE);
        if (stamp < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int now = getGameTime();
        int delta = now - (stamp + 3600);
        if (delta < 0)
        {
            String time_string = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(-delta));
            prose_package ppUnavailable = prose.getPackage(meditation.SID_FORCEOFWILL_UNAVAILABLE, time_string);
            return SCRIPT_CONTINUE;
        }
        int roll = rand(0, 100);
        if (roll < 5 || modval < roll)
        {
            setObjVar(self, meditation.VAR_FORCE_OF_WILL_ACTIVE, -1);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            meditation.forceOfWill(self, modval - roll);
            setObjVar(self, meditation.VAR_FORCE_OF_WILL_ACTIVE, now);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdForceOfWillFail(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int cmdPowerBoost(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_OVERRIDE;
        }
        int modval = meditation.getMeditationSkillMod(self);
        if ((modval < 1))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!meditation.isMeditating(self))
        {
            combat.sendCombatSpamMessage(self, meditation.SID_POWERBOOST_FAIL);
            return SCRIPT_OVERRIDE;
        }
        if (buff.hasBuff(self, "powerBuff"))
        {
            combat.sendCombatSpamMessage(self, meditation.SID_POWERBOOST_ACTIVE);
            return SCRIPT_OVERRIDE;
        }
        float duration = 300.0f + ((float)(modval) * 3.0f);
        if (!combatStandardAction("powerBoost", self, self, null, "", ""))
        {
            return SCRIPT_OVERRIDE;
        }
        buff.applyBuff(self, "powerBoost", duration);
        combat.sendCombatSpamMessage(self, meditation.SID_POWERBOOST_BEGIN);
        return SCRIPT_CONTINUE;
    }
    public int cmdPowerBoostFail(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        combat.sendCombatSpamMessage(self, meditation.SID_POWERBOOST_FAIL);
        return SCRIPT_CONTINUE;
    }
}
