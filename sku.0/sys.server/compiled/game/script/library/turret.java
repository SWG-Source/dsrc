package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.factions;
import script.library.battlefield;

public class turret extends script.base_script
{
    public turret()
    {
    }
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final float ALERT_VOLUME_SIZE = 120.0f;
    public static final float FACTION_TURRET_RANGE = 96.0f;
    public static final float TURRET_RANGE = 80.0f;
    public static final String VOL_TOO_CLOSE = "volTooClose";
    public static final float RANGE_TOO_CLOSE = 10f;
    public static final String VAR_TURRET_BASE = "turret";
    public static final String VAR_IS_ACTIVE = VAR_TURRET_BASE + ".isActive";
    public static final String VAR_IS_GENERIC = VAR_TURRET_BASE + ".isGeneric";
    public static final String SCRIPTVAR_ENGAGED = VAR_TURRET_BASE + ".engaged";
    public static final String SCRIPTVAR_TARGETS = VAR_TURRET_BASE + ".targetList";
    public static final String OBJVAR_CAN_ATTACK = "turret.validTargetObjVar";
    public static final String OBJVAR_TURRET_FRIEND = "turret.isFriend";
    public static void activateTurret(obj_id turret) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return;
        }
        if (!hasObjVar(turret, "objWeapon"))
        {
            if (!createWeapon(turret))
            {
                return;
            }
        }
        setObjVar(turret, VAR_IS_ACTIVE, true);
    }
    public static void deactivateTurret(obj_id turret) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return;
        }
        removeObjVar(turret, VAR_IS_ACTIVE);
    }
    public static boolean isActive(obj_id turret) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return false;
        }
        if (hasObjVar(turret, VAR_IS_ACTIVE))
        {
            return true;
        }
        return false;
    }
    public static void engageTarget(obj_id turret, obj_id target) throws InterruptedException
    {
        if (!isIdValid(turret) || !isIdValid(target))
        {
            return;
        }
        if (!isActive(turret))
        {
            return;
        }
        utils.setScriptVar(turret, "ai.combat.isInCombat", true);
        utils.setScriptVar(turret, SCRIPTVAR_ENGAGED, target);
        setCombatTarget(turret, target);
    }
    public static void disengage(obj_id turret) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return;
        }
        utils.removeScriptVar(turret, "ai.combat.isInCombat");
        utils.removeScriptVar(turret, SCRIPTVAR_ENGAGED);
        setCombatTarget(turret, null);
    }
    public static boolean isEngaged(obj_id turret) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return false;
        }
        if (utils.hasScriptVar(turret, "ai.combat.isInCombat"))
        {
            return true;
        }
        if (utils.hasScriptVar(turret, SCRIPTVAR_ENGAGED))
        {
            return true;
        }
        return false;
    }
    public static boolean createWeapon(obj_id turret) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return false;
        }
        if (hasObjVar(turret, "objWeapon"))
        {
            return false;
        }
        String strWeaponTemplate;
        if (hasObjVar(turret, "strWeaponTemplate"))
        {
            strWeaponTemplate = getStringObjVar(turret, "strWeaponTemplate");
        }
        else 
        {
            strWeaponTemplate = "object/weapon/ranged/turret/turret_block_large.iff";
        }
        obj_id objWeapon = createObject(strWeaponTemplate, turret, "");
        if (!isIdValid(objWeapon))
        {
            return false;
        }
        return setObjVar(turret, "objWeapon", objWeapon);
    }
    public static boolean isValidTargetGeneric(obj_id turret, obj_id target) throws InterruptedException
    {
        if (!isIdValid(turret) || !isIdValid(target))
        {
            return false;
        }
        if (target == turret)
        {
            return false;
        }
        location there = getLocation(target);
        location here = getLocation(turret);
        if (!isIdValid(here.cell))
        {
            if (there == null || isIdValid(there.cell))
            {
                return false;
            }
        }
        else 
        {
            if (there.cell != here.cell)
            {
                return false;
            }
        }
        float dist = getDistance(getLocation(turret), getLocation(target));
        if (dist > TURRET_RANGE + 1)
        {
            return false;
        }
        return canGenericTurretAttackTarget(target);
    }
    public static boolean isGenericTurret(obj_id turret) throws InterruptedException
    {
        return hasObjVar(turret, VAR_IS_GENERIC);
    }
    public static boolean canGenericTurretAttackTarget(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target))
        {
            return false;
        }
        if (hasObjVar(target, OBJVAR_TURRET_FRIEND))
        {
            return false;
        }
        obj_id self = getSelf();
        if (!hasObjVar(self, OBJVAR_CAN_ATTACK))
        {
            return false;
        }
        String objvar = getStringObjVar(self, OBJVAR_CAN_ATTACK);
        if (hasObjVar(target, objvar) || objvar.equals("all") || (objvar.equals("allPlayers") && isPlayer(target)))
        {
            return true;
        }
        return false;
    }
    public static boolean isValidTarget(obj_id turret, obj_id target) throws InterruptedException
    {
        if (!isIdValid(turret) || !isIdValid(target))
        {
            return false;
        }
        if (target == turret)
        {
            return false;
        }
        location there = getLocation(target);
        location here = getLocation(turret);
        if (!isIdValid(here.cell))
        {
            if (there == null || isIdValid(there.cell))
            {
                return false;
            }
        }
        else 
        {
            if (there.cell != here.cell)
            {
                return false;
            }
        }
        region btlField = battlefield.getBattlefield(turret);
        if (btlField != null)
        {
            if (pvpIsEnemy(turret, target))
            {
                return true;
            }
            return false;
        }
        float dist = getDistance(getLocation(turret), getLocation(target));
        if (dist > TURRET_RANGE + 1)
        {
            return false;
        }
        if (isPlayer(target))
        {
            return pvpCanAttack(turret, target);
        }
        else 
        {
            return pvpIsEnemy(turret, target);
        }
    }
    public static void addTarget(obj_id turret, obj_id target) throws InterruptedException
    {
        if (!isIdValid(turret) || !isIdValid(target))
        {
            return;
        }
        if (target == turret)
        {
            return;
        }
        if (!isGenericTurret(turret))
        {
            if (!isValidTarget(turret, target))
            {
                return;
            }
        }
        else 
        {
            if (!isValidTargetGeneric(turret, target))
            {
                return;
            }
        }
        float dist = getDistance(getLocation(turret), getLocation(target));
        if (dist > TURRET_RANGE + 1)
        {
            return;
        }
        if (!utils.hasScriptVar(turret, SCRIPTVAR_TARGETS))
        {
            Vector targets = utils.addElement(null, target);
            utils.setBatchScriptVar(turret, SCRIPTVAR_TARGETS, targets);
        }
        else 
        {
            Vector targets = utils.getResizeableObjIdBatchScriptVar(turret, SCRIPTVAR_TARGETS);
            if (targets != null && targets.size() > 0)
            {
                if (!utils.isElementInArray(targets, target))
                {
                    targets = utils.addElement(targets, target);
                    utils.setBatchScriptVar(turret, SCRIPTVAR_TARGETS, targets);
                }
            }
        }
        if (!isEngaged(turret))
        {
            utils.setScriptVar(turret, "ai.combat.isInCombat", true);
            messageTo(turret, "handleTurretAttack", null, 1, false);
        }
    }
    public static void addTargets(obj_id turret, obj_id[] target) throws InterruptedException
    {
        if (!isIdValid(turret) || (target == null) || (target.length == 0))
        {
            return;
        }
        Vector targets = utils.getResizeableObjIdBatchScriptVar(turret, SCRIPTVAR_TARGETS);
        for (int i = 0; i < target.length; i++)
        {
            if (isValidTarget(turret, target[i]) && !utils.isElementInArray(targets, target))
            {
                targets = utils.addElement(targets, target[i]);
            }
        }
        if ((targets != null) && (targets.size() > 0))
        {
            utils.setBatchScriptVar(turret, SCRIPTVAR_TARGETS, targets);
        }
        if (!isEngaged(turret))
        {
            utils.setScriptVar(turret, "ai.combat.isInCombat", true);
            messageTo(turret, "handleTurretAttack", null, 1, false);
        }
    }
    public static void removeTarget(obj_id turret, obj_id target) throws InterruptedException
    {
        if (!isIdValid(turret) || !isIdValid(target))
        {
            return;
        }
        if (!utils.hasScriptVar(turret, SCRIPTVAR_TARGETS))
        {
            return;
        }
        Vector targets = utils.getResizeableObjIdBatchScriptVar(turret, SCRIPTVAR_TARGETS);
        if ((targets == null) || (targets.size() == 0))
        {
            return;
        }
        obj_id engageTarget = utils.getObjIdScriptVar(turret, SCRIPTVAR_ENGAGED);
        int idx = utils.getElementPositionInArray(targets, target);
        if (idx > -1)
        {
            if (isIdValid(engageTarget))
            {
                if (target == engageTarget)
                {
                    disengage(turret);
                }
            }
            targets = utils.removeElementAt(targets, idx);
            if ((targets == null) || (targets.size() == 0))
            {
                utils.removeBatchScriptVar(turret, SCRIPTVAR_TARGETS);
                return;
            }
            utils.setBatchScriptVar(turret, SCRIPTVAR_TARGETS, targets);
        }
    }
}
