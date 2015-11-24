package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.dot;
import script.library.utils;
import script.library.trial;

public class player_dot extends script.base_script
{
    public player_dot()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String[] dot_ids = dot.getAllDots(self);
        if (dot_ids != null)
        {
            for (int i = 0; i < dot_ids.length; i++)
            {
                String type = dot.getDotType(self, dot_ids[i]);
                if (!type.equals(dot.DOT_DISEASE))
                {
                    int pulse = dot.getDotPulse(self, dot_ids[i]);
                    dictionary d = new dictionary();
                    d.put("dot_id", dot_ids[i]);
                    d.put("pulse", pulse);
                    messageTo(self, "OnDotPulse", d, pulse, false);
                }
            }
        }
        else 
        {
            detachScript(self, dot.SCRIPT_PLAYER_DOT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, dot.SCRIPT_VAR_DOT_GRACE))
        {
            utils.removeScriptVar(self, dot.SCRIPT_VAR_DOT_GRACE);
        }
        setState(self, STATE_BLEEDING, false);
        setState(self, STATE_POISONED, false);
        setState(self, STATE_DISEASED, false);
        setState(self, STATE_ON_FIRE, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnterSwimming(obj_id self) throws InterruptedException
    {
        if (dot.isOnFire(self))
        {
            dot.reduceDotTypeStrength(self, dot.DOT_FIRE, 10000);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttribModDone(obj_id self, String modName, boolean isDead) throws InterruptedException
    {
        if (false)
        {
            String[] parts = split(modName, '.');
            if (parts.length < 3)
            {
                return SCRIPT_CONTINUE;
            }
            String dot_id = parts[2];
            dot.removeDotEffect(self, dot_id, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDotPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String dot_id;
        if (params.containsKey("dot_id"))
        {
            dot_id = params.getString("dot_id");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        int pulse;
        if (params.containsKey("pulse"))
        {
            pulse = params.getInt("pulse");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        if (dot.applyDotDamage(self, dot_id))
        {
            obj_id attacker = params.getObjId("attacker");
            if (isPlayer(attacker) && exists(attacker))
            {
                addHate(self, attacker, 0.0f);
                addHate(attacker, self, 0.0f);
            }
            messageTo(self, "OnDotPulse", params, pulse, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnBuffDotPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!trial.verifySession(self, params, params.getString("buffName")))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id caster = params.getObjId("caster");
        String buffName = params.getString("buffName");
        int strength = params.getInt("strength");
        String type = params.getString("type");
        if (!buff.hasBuff(self, buffName))
        {
            return SCRIPT_CONTINUE;
        }
        long stackCount = buff.getBuffStackCount(self, buffName);
        strength *= stackCount;
        if (dot.applyBuffDotDamage(self, caster, buffName, strength, type))
        {
            obj_id attacker = params.getObjId("attacker");
            if (isPlayer(attacker) && exists(attacker))
            {
                addHate(self, attacker, 0.0f);
                addHate(attacker, self, 0.0f);
            }
            messageTo(self, "OnBuffDotPulse", params, buff.BUFF_DOT_TICK, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int bleedingStopped(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDotGraceEnd(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, dot.SCRIPT_VAR_DOT_GRACE))
        {
            utils.removeScriptVar(self, dot.SCRIPT_VAR_DOT_GRACE);
        }
        return SCRIPT_CONTINUE;
    }
}
