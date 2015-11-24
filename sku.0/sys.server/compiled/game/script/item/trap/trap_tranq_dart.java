package script.item.trap;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.prose;
import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.utils;
import script.library.buff;
import script.library.colors;

public class trap_tranq_dart extends script.item.trap.trap_base
{
    public trap_tranq_dart()
    {
    }
    public static final int TRAP_DIFF = 20;
    public static final int REUSE_TIMER = 10;
    public static final string_id SID_SYS_EFFECT = new string_id("trap/trap", "trap_tranq_dart_effect");
    public static final string_id SID_NO_EFFECT = new string_id("trap/trap", "trap_tranq_dart_effect_no");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            setObjVar(self, "trapDiff", TRAP_DIFF);
            setObjVar(self, "trapName", "tranq_dart");
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
        if (!buff.canApplyBuff(target, "tranqDart"))
        {
            prose_package pp = prose.getPackage(SID_NO_EFFECT, target);
            sendSystemMessageProse(player, pp);
            return super.trapHit(self, params);
        }
        if (buff.hasBuff(target, "tranqDart"))
        {
            return super.trapHit(self, params);
        }
        prose_package pp = prose.getPackage(SID_SYS_EFFECT, target);
        sendSystemMessageProse(player, pp);
        buff.applyBuff(target, "tranqDart");
        string_id strFlyText = new string_id("combat_effects", "go_snare");
        color colFlyText = colors.LAWNGREEN;
        showFlyText(target, strFlyText, 1.0f, colFlyText);
        if (!hasObjVar(self, "droid_trap"))
        {
            addToMentalStateToward(target, player, ANGER, 30);
            if (!ai_lib.isInCombat(target))
            {
                startCombat(target, player);
            }
        }
        else 
        {
            addToMentalStateToward(target, self, ANGER, 30);
            if (!ai_lib.isInCombat(target))
            {
                startCombat(target, self);
            }
        }
        float chance = params.getFloat("chance");
        grantTrapXP(player, target, 1.3f);
        return super.trapHit(self, params);
    }
}
