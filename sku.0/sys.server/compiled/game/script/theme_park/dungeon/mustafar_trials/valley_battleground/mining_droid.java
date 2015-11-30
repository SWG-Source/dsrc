package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.trial;
import script.library.pet_lib;
import script.library.factions;

public class mining_droid extends script.base_script
{
    public mining_droid()
    {
    }
    public static final String STF = "mustafar/valley_battlefield";
    public static final string_id ACTIVATE_DROID = new string_id(STF, "activate_droid");
    public static final boolean LOGGING = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDroidEngineer(player) && isDeactivated(self))
        {
            int root_menu = mi.addRootMenu(menu_info_types.ITEM_USE, ACTIVATE_DROID);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            activateDroid(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (isPlayer(attacker) || pet_lib.isPet(attacker))
        {
            int total = 0;
            for (int x = 0; x < damage.length; x++)
            {
                total += damage[x];
            }
            addToHealth(self, total);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        if (isPlayer(target) || pet_lib.isPet(target))
        {
            removeHateTarget(self, target);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findWayPoints(self);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        trial.setInterest(self);
        messageTo(self, "startPathing", null, 8, false);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public int startPathing(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "deactivated"))
        {
            factions.setIgnorePlayer(self);
            messageTo(self, "pathToNextPoint", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isDeactivated(obj_id droid) throws InterruptedException
    {
        return utils.hasScriptVar(droid, "deactivated");
    }
    public boolean isDroidEngineer(obj_id player) throws InterruptedException
    {
        return hasSkill(player, "class_engineering_phase2_novice");
    }
    public void activateDroid(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "deactivated"))
        {
            utils.removeScriptVar(self, "deactivated");
        }
        location playLoc = getLocation(self);
        String effect = "clienteffect/space_command/emergency_power_on.cef";
        playClientEffectLoc(self, effect, playLoc, 0.4f);
        messageTo(self, "startPathing", null, 3, false);
    }
    public void findWayPoints(obj_id self) throws InterruptedException
    {
        location baseLoc = getLocation(self);
        obj_id[] objects = getObjectsInRange(baseLoc, 500);
        if (objects == null || objects.length == 0)
        {
            doLogging("findWayPoints", "Contents list was empty, exiting");
            return;
        }
        Vector waypoints = new Vector();
        waypoints.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            if (hasObjVar(objects[i], "wp_name"))
            {
                if ((getStringObjVar(objects[i], "wp_name")).startsWith("droid"))
                {
                    utils.addElement(waypoints, getLocation(objects[i]));
                }
            }
        }
        if (waypoints == null)
        {
            doLogging("findWayPoints", "No waypoints were found, exiting");
            return;
        }
        location[] patrolPoints = new location[0];
        if (waypoints != null)
        {
            patrolPoints = new location[waypoints.size()];
            waypoints.toArray(patrolPoints);
        }
        if (patrolPoints.length == 0)
        {
            doLogging("findWayPoints", "Patrol Point list was empty, exiting");
            return;
        }
        utils.setScriptVar(self, "patrolPoints", patrolPoints);
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolPoints");
        ai_lib.setPatrolRandomPath(self, patrolPoints);
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/mining_droid/" + section, message);
        }
    }
}
