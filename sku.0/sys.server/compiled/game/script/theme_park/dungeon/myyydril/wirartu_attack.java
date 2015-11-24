package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.combat;
import script.ai.ai_combat;
import script.library.utils;

public class wirartu_attack extends script.base_script
{
    public wirartu_attack()
    {
    }
    public static final String STF_FILE = "quest/pirates";
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        final float DAMAGE_THRESHOLD = 0.50f;
        boolean check = false;
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio < DAMAGE_THRESHOLD)
        {
            setInvulnerable(self, true);
            ai_lib.clearCombatData();
            clearHateList(self);
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
            attachScript(self, "conversation.ep3_forest_wirartu_attack");
            chat.chat(self, new string_id(STF_FILE, "dont_hurt_me"));
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanMeUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
