package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.attrib;
import script.library.combat;
import script.library.pclib;
import script.library.turret;
import script.library.utils;

public class turret_operations extends script.systems.combat.combat_base_old
{
    public turret_operations()
    {
    }
    public static final String STF = "xx";
    public static final string_id takeControlObject = new string_id(STF, "take_control_object");
    public static final string_id alreadyInUse = new string_id(STF, "already_in_use");
    public static final String controllerTemplate = "object/tangible/dungeon/mustafar/valley_battlefield/turret_controller_object.iff";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        turret.activateTurret(self);
        doLogging("OnAttach", "Activating Turret");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        turret.activateTurret(self);
        doLogging("OnInitialize", "Activating Turret");
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (isIdValid(who) && !isGod(who))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isIdValid(transferer) && !isGod(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isIdValid(transferer) && !isGod(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            explodeTurret(self, attacker);
            doLogging("OnObjectDamage", "Hitpoints less than 0, destroying self");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int doFireAction(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id controller = params.getObjId("controller");
        obj_id target = params.getObjId("target");
        doLogging("doFireAction", "Recieved command to fire: player(" + getName(player) + ") controller(" + controller + ") target(" + getEncodedName(target) + ")");
        doAttack(self, target);
        return SCRIPT_CONTINUE;
    }
    public void explodeTurret(obj_id turret) throws InterruptedException
    {
        location viewLoc = getLocation(turret);
        obj_id[] viewers = getPlayerCreaturesInRange(viewLoc, 100);
        if (viewers == null || viewers.length == 0)
        {
            return;
        }
        explodeTurret(turret, viewers[0]);
    }
    public void explodeTurret(obj_id turretId, obj_id killer) throws InterruptedException
    {
        location death = getLocation(turretId);
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        destroyObject(turretId);
        doLogging("explodeTurret", "Turret has been destroyed");
        if (!utils.hasScriptVar(turretId, "controllerId"))
        {
            doLogging("explodeTurret", "Did not have the controllerId ObjVar");
            return;
        }
        obj_id controller = utils.getObjIdScriptVar(turretId, "controllerId");
        if (!isIdValid(controller))
        {
            doLogging("explodeTurret", "Controller ID was not valid");
            return;
        }
        messageTo(controller, "turretDisabled", null, 0, false);
    }
    public void doAttack(obj_id self, obj_id target) throws InterruptedException
    {
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            doLogging("doAttack", "Turret has negative hitpoints, destroying self");
            explodeTurret(self);
            return;
        }
        if (!isIdValid(target) || !canSee(self, target) || isDead(target))
        {
            doLogging("doAttack", "isIdValid(" + isIdValid(target) + " canSee(" + canSee(self, target) + " isDead(" + isDead(target) + ")");
            return;
        }
        if (!hasObjVar(self, "objWeapon"))
        {
            doLogging("doAttack", "hasObjVar(self, objWeapon): " + hasObjVar(self, "objWeapon"));
            return;
        }
        final float DAMAGE_MODIFIER = 2.0f;
        final float HEALTH_COST_MODIFIER = 0;
        
        final float ACTION_COST_MODIFIER = 0;
        final int BASE_TO_HIT_MODIFIER = 0;
        final float AMMO_COST_MODIFIER = 1.0f;
        String[] strTimeMods = 
        {
            ""
        };
        String[] strDamageMods = 
        {
            ""
        };
        String[] strCostMods = 
        {
        };
        String[] strToHitMods = 
        {
            ""
        };
        String[] strBlockMods = 
        {
            ""
        };
        String[] strEvadeMods = 
        {
            ""
        };
        String[] strCounterAttackMods = 
        {
            ""
        };
        int intBlockMod = 1000;
        int intEvadeMod = 1000;
        int intCounterAttackMod = 1000;
        int intAttackerEndPosture = POSTURE_NONE;
        int intDefenderEndPosture = POSTURE_NONE;
        String strPlaybackAction = "fire_turret";
        int[] intEffects = 
        {
        };
        float[] fltEffectDurations = 
        {
        };
        int intChanceToApplyEffect = 0;
        obj_id objWeapon = getObjIdObjVar(self, "objWeapon");
        attacker_data cbtAttackerData = new attacker_data();
        weapon_data cbtWeaponData = new weapon_data();
        obj_id[] objDefenders = new obj_id[1];
        objDefenders[0] = target;
        defender_data[] cbtDefenderData = new defender_data[objDefenders.length];
        if (!getCombatData(self, objDefenders, cbtAttackerData, cbtDefenderData, cbtWeaponData))
        {
            doLogging("doAttack", "Failed to get combat data");
            return;
        }
        cbtWeaponData = getWeaponData(objWeapon);
        hit_result[] cbtHitData = new hit_result[1];
        cbtHitData[0] = calculateHit(cbtAttackerData, cbtDefenderData[0], cbtWeaponData);
        attacker_results cbtAttackerResults = new attacker_results();
        cbtAttackerResults.id = self;
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtAttackerResults.endPosture = -1;
        debugServerConsoleMsg(self, "posture set");
        cbtAttackerResults.weapon = objWeapon;
        debugServerConsoleMsg(self, "weapon");
        cbtDefenderResults[0].id = target;
        debugServerConsoleMsg(self, "target");
        cbtDefenderResults[0].endPosture = getPosture(target);
        debugServerConsoleMsg(self, "defender posture");
        if (cbtHitData[0].success)
        {
            cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
        }
        else 
        {
            cbtDefenderResults[0].result = COMBAT_RESULT_MISS;
        }
        debugServerConsoleMsg(self, "hitdata");
        finalizeDamage(cbtAttackerData.id, cbtWeaponData, cbtDefenderData, cbtHitData, cbtDefenderResults, null);
        String[] strPlaybackNames = makePlaybackNames("fire_turret", cbtHitData, cbtWeaponData, cbtDefenderResults);
        doCombatResults(strPlaybackNames[0], cbtAttackerResults, cbtDefenderResults);
        String[] strEffects = makeStringArray(1);
        return;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/turret_operations/" + section, message);
        }
    }
}
