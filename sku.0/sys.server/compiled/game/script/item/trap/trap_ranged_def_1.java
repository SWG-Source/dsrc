package script.item.trap;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.combat;
import script.library.prose;
import script.library.ai_lib;
import script.library.utils;

public class trap_ranged_def_1 extends script.item.trap.trap_base
{
    public trap_ranged_def_1()
    {
    }
    public static final int TRAP_DIFF = 10;
    public static final string_id SID_SYS_EFFECT = new string_id("trap/trap", "trap_ranged_def_1_effect");
    public static final string_id SID_NO_EFFECT = new string_id("trap/trap", "trap_effect_no");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            setObjVar(self, "trapDiff", TRAP_DIFF);
            setObjVar(self, "trapName", "ranged_def_1");
            setCount(self, 5);
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
        if (utils.hasScriptVar(target, "trapmod.ranged_defense"))
        {
            prose_package pp = prose.getPackage(SID_NO_EFFECT, target);
            sendSystemMessageProse(player, pp);
            return super.trapHit(self, params);
        }
        dictionary outparams = new dictionary();
        outparams.put("ranged_defense", 30);
        outparams.put("prefix", "ranged_def_1");
        messageTo(target, "applyModTrap", outparams, 0.f, false);
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
        grantTrapXP(player, target, 1.15f);
        return super.trapHit(self, params);
    }
}
