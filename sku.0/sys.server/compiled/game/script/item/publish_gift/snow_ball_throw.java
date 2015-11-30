package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class snow_ball_throw extends script.base_script
{
    public snow_ball_throw()
    {
    }
    public static final string_id SID_SYS_NO_MOUNT = new string_id("spam", "snowball_not_while_mounted");
    public static final string_id SID_CANT_SEE_TARGET = new string_id("spam", "snowball_cant_see_target");
    public static final string_id SID_TARGET_SITTING = new string_id("spam", "snowball_target_sitting");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        if (planetName.equals("mustafar"))
        {
            setObjVar(self, "item.lifespan", 60);
            return SCRIPT_CONTINUE;
        }
        if (planetName.equals("tatooine"))
        {
            setObjVar(self, "item.lifespan", 300);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "item.lifespan", 900);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setLabel(new string_id("spam", "throw_snowball"));
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_RIDING_MOUNT) == 1)
        {
            sendSystemMessage(player, SID_SYS_NO_MOUNT);
            return SCRIPT_CONTINUE;
        }
        obj_id myTarget = getIntendedTarget(player);
        if (myTarget == null || !isIdValid(myTarget))
        {
            sendSystemMessage(player, new string_id("spam", "need_intended_target"));
            return SCRIPT_CONTINUE;
        }
        int targetNiche = getNiche(myTarget);
        if (targetNiche < 0 || targetNiche == 4)
        {
            sendSystemMessage(player, new string_id("spam", "invalid_target"));
            return SCRIPT_CONTINUE;
        }
        if (hasScript(myTarget, "systems.skills.performance.active_dance") || hasScript(myTarget, "systems.skills.performance.active_music"))
        {
            sendSystemMessage(player, new string_id("spam", "ent_dancing"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE && utils.isNestedWithinAPlayer(self))
        {
            if (myTarget == player)
            {
                sendSystemMessage(player, new string_id("spam", "not_at_self"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                throwMe(myTarget, player, self);
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("spam", "must_be_in_inventory"));
        }
        return SCRIPT_CONTINUE;
    }
    public void throwMe(obj_id target, obj_id player, obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "snowball_pause"))
        {
            sendSystemMessage(player, new string_id("spam", "snowball_not_ready"));
            return;
        }
        if (getPosture(target) == POSTURE_SITTING)
        {
            sendSystemMessage(player, SID_TARGET_SITTING);
            return;
        }
        if (!canSee(player, target))
        {
            sendSystemMessage(player, SID_CANT_SEE_TARGET);
            return;
        }
        else 
        {
            float travelDistance = getDistance(target, player);
            if (travelDistance > 30f)
            {
                sendSystemMessage(player, new string_id("spam", "out_of_snowball_range"));
                return;
            }
            if (travelDistance <= 30f && travelDistance > 20f)
            {
                attacker_results cbtAttackerResults = makeDummyAttackerResults(player);
                defender_results[] cbtDefenderResults = makeDummyDefenderResults(target);
                doCombatResults("throw_snow_ball_long", cbtAttackerResults, cbtDefenderResults);
            }
            if (travelDistance <= 20f && travelDistance > 10f)
            {
                attacker_results cbtAttackerResults = makeDummyAttackerResults(player);
                defender_results[] cbtDefenderResults = makeDummyDefenderResults(target);
                doCombatResults("throw_snow_ball_medium", cbtAttackerResults, cbtDefenderResults);
            }
            if (travelDistance <= 10f && travelDistance > 0.0f)
            {
                attacker_results cbtAttackerResults = makeDummyAttackerResults(player);
                defender_results[] cbtDefenderResults = makeDummyDefenderResults(target);
                doCombatResults("throw_snow_ball_short", cbtAttackerResults, cbtDefenderResults);
            }
            setObjVar(self, "snowball_pause", 1);
            messageTo(self, "snowballDelay", null, 3.0f, false);
            decrementCount(self);
        }
    }
    public int snowballDelay(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "snowball_pause"))
        {
            removeObjVar(self, "snowball_pause");
        }
        return SCRIPT_CONTINUE;
    }
    public attacker_results makeDummyAttackerResults(obj_id objAttacker) throws InterruptedException
    {
        attacker_results cbtAttackerResults = new attacker_results();
        cbtAttackerResults.id = objAttacker;
        obj_id snowWeapon = utils.getStaticItemInInventory(objAttacker, "publish_gift_chapter_11_snow_balls_02_01");
        cbtAttackerResults.weapon = snowWeapon;
        cbtAttackerResults.endPosture = getPosture(objAttacker);
        return cbtAttackerResults;
    }
    public defender_results[] makeDummyDefenderResults(obj_id objDefender) throws InterruptedException
    {
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtDefenderResults[0].id = objDefender;
        cbtDefenderResults[0].endPosture = 0;
        cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
        cbtDefenderResults[0].damageAmount = 0;
        cbtDefenderResults[0].hitLocation = 0;
        return cbtDefenderResults;
    }
}
