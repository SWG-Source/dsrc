package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class ai_combat_damage extends script.base_script
{
    public ai_combat_damage()
    {
    }
    public static void addAttack(obj_id attacker) throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        final int attackCount = dict.getInt("ai.combat.attack_count." + attacker);
        dict.put("ai.combat.attack_count." + attacker, attackCount + 1);
        dict.put("ai.combat.last_attack_time." + attacker, getGameTime());
    }
    public static int getTimeSinceLastAttack(obj_id attacker) throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        return getGameTime() - dict.getInt("ai.combat.last_attack_time." + attacker);
    }
    public static int getAttackCount(obj_id attacker) throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        return dict.getInt("ai.combat.attack_count." + attacker);
    }
    public static void clearAttackerList() throws InterruptedException
    {
        final obj_id self = getSelf();
        utils.removeScriptVarTree(self, "ai.combat.attack_count");
        utils.removeScriptVarTree(self, "ai.combat.last_attack_time");
    }
}
