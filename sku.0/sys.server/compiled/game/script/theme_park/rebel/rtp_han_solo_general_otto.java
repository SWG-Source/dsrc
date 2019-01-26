package script.theme_park.rebel;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

public class rtp_han_solo_general_otto extends script.base_script
{
    public rtp_han_solo_general_otto()
    {
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        final float DAMAGE_THRESHOLD = 0.20f;
        boolean check = false;
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= (maxHealth * DAMAGE_THRESHOLD))
        {
            check = true;
        }
        for (int i1 : damage) {
            currentHealth = currentHealth - i1;
        }
        if (currentHealth <= 0)
        {
            check = true;
        }
        if (check)
        {
            if (!hasObjVar(self, "rtp.general_otto_defeated"))
            {
                detachScript(self, "ai.creature_combat");
                setInvulnerable(self, true);
                chat.chat(self, new string_id("quest/pirates", "general_otto_defeated"));
                dictionary webster = new dictionary();
                webster.put("attacker", attacker);
                messageTo(self, "takeGeneralOttoOutOfCombat", null, 0, false);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int takeGeneralOttoOutOfCombat(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        buff.removeAllBuffs(self);
        clearHateList(self);
        obj_id attacker = params.getObjId("attacker");
        setMovementRun(self);
        if (isIdValid(attacker))
        {
            flee(self, attacker, 5, 15);
        }
        else 
        {
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        }
        setObjVar(self, "rtp.general_otto_defeated", true);
        messageTo(self, "deleteGeneralOtto", null, 9, false);
        xp.assessCombatResults(self);
        xp.applyHealingCredit(self);
        return SCRIPT_CONTINUE;
    }
    public int deleteGeneralOtto(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
