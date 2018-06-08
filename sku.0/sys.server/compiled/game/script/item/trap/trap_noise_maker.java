package script.item.trap;

import script.dictionary;
import script.library.ai_lib;
import script.library.prose;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class trap_noise_maker extends script.item.trap.trap_base
{
    public trap_noise_maker()
    {
    }
    public static final int TRAP_DIFF = 15;
    public static final string_id SID_SYS_EFFECT = new string_id("trap/trap", "trap_noise_maker_effect");
    public static final string_id SID_NO_EFFECT = new string_id("trap/trap", "trap_noise_maker_effect_no");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            setObjVar(self, "trapDiff", TRAP_DIFF);
            setObjVar(self, "trapName", "noise_maker");
            setCount(self, 8);
        }
        return SCRIPT_CONTINUE;
    }
    public int trapHit(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return super.trapHit(self, params);
        }
        obj_id target = params.getObjId("target");
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return super.trapHit(self, params);
        }
        if (!ai_lib.isMonster(target) || isIncapacitated(target) || isDead(target))
        {
            return super.trapHit(self, params);
        }
        obj_id player = params.getObjId("player");
        if (1 == getState(target, STATE_STUNNED))
        {
            prose_package pp = prose.getPackage(SID_NO_EFFECT, target);
            sendSystemMessageProse(player, pp);
            return super.trapHit(self, params);
        }
        prose_package pp = prose.getPackage(SID_SYS_EFFECT, target);
        sendSystemMessageProse(player, pp);
        if (!hasObjVar(self, "droid_trap"))
        {
            if (!ai_lib.isInCombat(target))
            {
                startCombat(target, player);
            }
        }
        else 
        {
            if (!ai_lib.isInCombat(target))
            {
                startCombat(target, self);
            }
        }
        float chance = params.getFloat("chance");
        grantTrapXP(player, target, 1.2f);
        return super.trapHit(self, params);
    }
}
