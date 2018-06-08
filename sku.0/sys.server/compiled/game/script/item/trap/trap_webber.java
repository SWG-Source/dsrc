package script.item.trap;

import script.dictionary;
import script.library.ai_lib;
import script.library.prose;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class trap_webber extends script.item.trap.trap_base
{
    public trap_webber()
    {
    }
    public static final int TRAP_DIFF = 25;
    public static final string_id SID_SYS_EFFECT = new string_id("trap/trap", "trap_webber_effect");
    public static final string_id SID_NO_EFFECT = new string_id("trap/trap", "trap_webber_effect_no");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            setObjVar(self, "trapDiff", TRAP_DIFF);
            setObjVar(self, "trapName", "webber");
            setCount(self, 2);
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
        prose_package pp = prose.getPackage(SID_SYS_EFFECT, target);
        sendSystemMessageProse(player, pp);
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        outparams.put("duration", rand(30, 60));
        messageTo(target, "rootEnable", outparams, 0, false);
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
        grantTrapXP(player, target, 1.4f);
        return super.trapHit(self, params);
    }
}
