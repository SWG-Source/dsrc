package script.item.trap;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.utils;

public class trap_sonic_pulse extends script.item.trap.trap_base
{
    public trap_sonic_pulse()
    {
    }
    public static final int TRAP_DIFF = 45;
    public static final string_id SID_SYS_EFFECT = new string_id("trap/trap", "trap_sonic_pulse_effect");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            setObjVar(self, "areaEffect", true);
            setObjVar(self, "trapDiff", TRAP_DIFF);
            setObjVar(self, "trapName", "sonic_pulse");
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
        obj_id player = params.getObjId("player");
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return super.trapHit(self, params);
        }
        if (ai_lib.isNpc(target) || ai_lib.isAndroid(target))
        {
            return super.trapHit(self, params);
        }
        int hits = utils.getIntScriptVar(self, "hits");
        if (hits == 0)
        {
            sendSystemMessage(player, SID_SYS_EFFECT);
            float chance = params.getFloat("chance");
            grantTrapXP(player, target, 1.7f);
        }
        hits++;
        utils.setScriptVar(self, "hits", hits);
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
        return super.trapHit(self, params);
    }
}
