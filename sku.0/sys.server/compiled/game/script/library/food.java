package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.dot;
import script.library.player_stomach;

public class food extends script.base_script
{
    public food()
    {
    }
    public static void applyInstantEffect(obj_id target, obj_id item) throws InterruptedException
    {
        if (!hasObjVar(item, "instant"))
        {
            return;
        }
        String type = getStringObjVar(item, "instant.type");
        if (type == null)
        {
            return;
        }
        int v1 = getIntObjVar(item, "instant.v1");
        int v2 = getIntObjVar(item, "instant.v2");
        if (type.equals("burst_run"))
        {
            instantBurstRun(target, item, v1, v2);
        }
        else if (type.equals("wookiee_roar"))
        {
            instantWookieeRoar(target, item, v1, v2);
        }
        else if (type.equals("slow_dot"))
        {
            instantSlowDot(target, item, v1, v2);
        }
        else if (type.equals("cure_fire"))
        {
            instantCureFire(target, item, v1, v2);
        }
        else if (type.equals("enhanced_regen"))
        {
            instantEnhancedRegen(target, item, v1, v2);
        }
        else if (type.equals("food_reduce"))
        {
            instantFoodReduce(target, item, v1, v2);
        }
    }
    public static void petEatFood(obj_id self, obj_id master, obj_id item, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "pet_food"))
        {
            int lastEatTime = utils.getIntScriptVar(self, "pet_food");
            if (getGameTime() - lastEatTime < 3600)
            {
                sendSystemMessage(master, new string_id("error_message", "no_pet_feed"));
                return;
            }
        }
        attrib_mod[] am = getAttribModArrayObjVar(item, consumable.VAR_CONSUMABLE_MODS);
        if (am != null)
        {
            utils.addAttribMod(self, am);
        }
        color textColor = colors.YELLOW;
        chat.thinkTo(self, master, "Yummy!");
        doAnimationAction(self, "eat");
        utils.setScriptVar(self, "pet_food", getGameTime());
        consumable.decrementCharges(item, self);
    }
    public static void instantBurstRun(obj_id target, obj_id item, int v1, int v2) throws InterruptedException
    {
        utils.setScriptVar(target, "food.burst_run.v1", v1);
        utils.setScriptVar(target, "food.burst_run.v2", v2);
        queueCommand(target, (-63103822), null, "", COMMAND_PRIORITY_FRONT);
        sendSystemMessage(target, new string_id("combat_effects", "instant_burst_run"));
    }
    public static void instantWookieeRoar(obj_id target, obj_id item, int v1, int v2) throws InterruptedException
    {
        utils.setScriptVar(target, "food.wookiee_roar.v1", v1);
        queueCommand(target, (-1223315403), null, "", COMMAND_PRIORITY_IMMEDIATE);
    }
    public static void instantSlowDot(obj_id target, obj_id item, int v1, int v2) throws InterruptedException
    {
        if (!utils.hasScriptVarTree(target, dot.VAR_DOT))
        {
            return;
        }
        if (utils.hasScriptVar(target, "slowDotTime"))
        {
            int time = utils.getIntScriptVar(target, "slowDotTime");
            if (getGameTime() - time < 900)
            {
                sendSystemMessage(target, new string_id("combat_effects", "slow_dot_wait"));
                return;
            }
        }
        float mod = 1f - (v1 / 100f);
        String[] dots = dot.getAllDots(target);
        if (dots != null)
        {
            for (int i = 0; i < dots.length; i++)
            {
                String varName = dot.VAR_DOT_ROOT + dots[i] + dot.VAR_DURATION;
                int duration = utils.getIntScriptVar(target, varName);
                duration *= mod;
                utils.setScriptVar(target, varName, duration);
            }
        }
        utils.setScriptVar(target, "slowDotTime", getGameTime());
        prose_package pp = prose.getPackage(new string_id("combat_effects", "slow_dot_done"), v1);
        sendSystemMessageProse(target, pp);
    }
    public static void instantCureFire(obj_id target, obj_id item, int v1, int v2) throws InterruptedException
    {
        if (!dot.isOnFire(target))
        {
            return;
        }
        if (rand(1, 100) > v1)
        {
            sendSystemMessage(target, new string_id("combat_effects", "fire_cure_no_effect"));
            return;
        }
        dot.reduceDotTypeStrength(target, dot.DOT_FIRE, 10000);
        sendSystemMessage(target, new string_id("combat_effects", "fire_cured"));
    }
    public static void instantEnhancedRegen(obj_id target, obj_id item, int v1, int v2) throws InterruptedException
    {
        utils.setScriptVar(target, "food.enhanced_regen.v1", v1);
        queueCommand(target, (1397846664), null, "", COMMAND_PRIORITY_IMMEDIATE);
    }
    public static void instantFoodReduce(obj_id target, obj_id item, int v1, int v2) throws InterruptedException
    {
        player_stomach.addToStomach(target, 0, -1 * v1);
        sendSystemMessage(target, new string_id("combat_effects", "feel_hungry"));
    }
}
