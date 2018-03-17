package script.quest.task.pgc.fan_faire_demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.combat;
import script.library.create;
import script.library.static_item;
import script.library.stealth;
import script.library.trial;
import script.library.utils;

public class quest_pinata extends script.base_script
{
    public quest_pinata()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeQuestPinata", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeQuestPinata(obj_id self, dictionary params) throws InterruptedException
    {
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
        if (hasScript(self, "ai.creature_combat"))
        {
            detachScript(self, "ai.creature_combat");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (rand(0, 1) == 1)
        {
            dictionary webster = new dictionary();
            String creatureName = getCreatureName(self);
            String socialGroup = ai_lib.getSocialGroup(self);
            webster.put("creatureName", creatureName);
            webster.put("location", getLocation(attacker));
            webster.put("socialGroup", socialGroup);
            messageTo(attacker, "receiveCreditForKill", webster, 0.0f, false);
        }
        removeHateTarget(self, attacker);
        removeHateTarget(attacker, self);
        stopCombat(attacker);
        if (currentHealth <= maxHealth * 0.5)
        {
            setAttrib(self, HEALTH, maxHealth);
        }
        return SCRIPT_CONTINUE;
    }
}
