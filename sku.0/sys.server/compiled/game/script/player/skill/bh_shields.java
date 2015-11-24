package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;

public class bh_shields extends script.base_script
{
    public bh_shields()
    {
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        String shield_buff = "bh_shields";
        String shield_charge = "bh_shields_charged";
        if (!buff.hasBuff(self, shield_buff))
        {
            detachScript(self, "player.skill.bh_shields");
            return SCRIPT_CONTINUE;
        }
        int stackCount = (int)buff.getBuffStackCount(self, shield_buff);
        int timeLeft = (int)buff.getBuffTimeRemaining(self, "bh_shields");
        if (stackCount < 2 || timeLeft < 2)
        {
            buff.applyBuff(self, "bh_shields_block");
            detachScript(self, "player.skill.bh_shields");
            return SCRIPT_CONTINUE;
        }
        buff.decrementBuffStack(self, shield_buff, 1);
        buff.applyBuff(self, shield_charge);
        return SCRIPT_CONTINUE;
    }
}
