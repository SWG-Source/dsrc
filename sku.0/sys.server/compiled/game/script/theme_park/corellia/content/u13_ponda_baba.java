package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;
import script.library.xp;

public class u13_ponda_baba extends script.base_script
{
    public u13_ponda_baba()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        chat.setChatMood(self, chat.MOOD_PETULANT);
        chat.setChatType(self, chat.CHAT_COMPLAIN);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        float damageThreshold = 0.15f;
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= (maxHealth * damageThreshold))
        {
            detachScript(self, "ai.creature_combat");
            setAttrib(self, HEALTH, maxHealth);
            setInvulnerable(self, true);
            chat.publicChat(self, attacker, new string_id("quest2", "u13_ponda_baba_beaten"));
            messageTo(self, "makeNpcStopCombat", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int makeNpcStopCombat(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        clearHateList(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        xp.assessCombatResults(self);
        xp.applyHealingCredit(self);
        messageTo(self, "makeNpcDisappear", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int makeNpcDisappear(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "appearance/pt_smoke_puff_noloop.prt", here, 0.0f);
        messageTo(self, "handleDestroySelf", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        stopCombat(self);
        clearHateList(self);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
