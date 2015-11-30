package script.systems.turret;

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
import script.library.list;
import script.library.pet_lib;
import script.library.pclib;
import script.library.player_structure;
import script.library.turret;
import script.library.utils;
import script.library.xp;

public class generic_turret_ai extends script.systems.combat.combat_base_old
{
    public generic_turret_ai()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        turret.activateTurret(self);
        if (getHitpoints(self) < 1)
        {
            setHitpoints(self, 1);
        }
        setObjVar(self, turret.VAR_IS_GENERIC, true);
        utils.setScriptVar(self, "ai.level", 100);
        setTurretAttributes(self);
        setWantSawAttackTriggers(self, true);
        if (hasObjVar(self, "objWeapon"))
        {
            obj_id wpn = getObjIdObjVar(self, "objWeapon");
            if (!isIdValid(wpn))
            {
                removeObjVar(self, "objWeapon");
                turret.createWeapon(self);
            }
            else 
            {
                if (!utils.isNestedWithin(wpn, self))
                {
                    removeObjVar(self, "objWeapon");
                    turret.createWeapon(self);
                }
            }
        }
        else 
        {
            turret.createWeapon(self);
        }
        ai_lib.setAttackable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (getHitpoints(self) < 1)
        {
            setHitpoints(self, 1);
        }
        setObjVar(self, turret.VAR_IS_GENERIC, true);
        utils.setScriptVar(self, "ai.level", 100);
        setTurretAttributes(self);
        setWantSawAttackTriggers(self, true);
        if (hasObjVar(self, "objWeapon"))
        {
            obj_id wpn = getObjIdObjVar(self, "objWeapon");
            if (!isIdValid(wpn))
            {
                removeObjVar(self, "objWeapon");
                turret.createWeapon(self);
            }
            else 
            {
                if (!utils.isNestedWithin(wpn, self))
                {
                    removeObjVar(self, "objWeapon");
                    turret.createWeapon(self);
                }
            }
        }
        else 
        {
            turret.createWeapon(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void setTurretAttributes(obj_id self) throws InterruptedException
    {
        if (hasTriggerVolume(self, turret.ALERT_VOLUME_NAME))
        {
            removeTriggerVolume(turret.ALERT_VOLUME_NAME);
        }
        if (hasTriggerVolume(self, turret.VOL_TOO_CLOSE))
        {
            removeTriggerVolume(turret.VOL_TOO_CLOSE);
        }
        clearAttributeInterested(self, attrib.ALL);
        setAttributeInterested(self, attrib.ALL);
        createTriggerVolume(turret.ALERT_VOLUME_NAME, turret.TURRET_RANGE, true);
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "lair.deadLair"))
        {
            destroyObject(self);
        }
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
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (!isIdValid(who))
        {
            return SCRIPT_CONTINUE;
        }
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            explodeTurret(self, who);
            return SCRIPT_CONTINUE;
        }
        if (!turret.canGenericTurretAttackTarget(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(turret.ALERT_VOLUME_NAME) && who != self)
        {
            turret.addTarget(self, who);
        }
        else if (volumeName.equals(turret.VOL_TOO_CLOSE) && who != self)
        {
            turret.removeTarget(self, who);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (volumeName.equals(turret.ALERT_VOLUME_NAME) && who != self)
        {
            turret.removeTarget(self, who);
            if (turret.isEngaged(self))
            {
                obj_id target = utils.getObjIdScriptVar(self, turret.SCRIPTVAR_ENGAGED);
                if (target == who)
                {
                    turret.disengage(self);
                }
            }
        }
        else if (volumeName.equals(turret.VOL_TOO_CLOSE) && who != self)
        {
            turret.addTarget(self, who);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            explodeTurret(self, attacker);
            return SCRIPT_CONTINUE;
        }
        turret.addTarget(self, attacker);
        if (!utils.hasScriptVar(self, "playingEffect"))
        {
            int smolder = 2000;
            int fire = 1000;
            if (curHP < smolder)
            {
                if (curHP < fire)
                {
                    location death = getLocation(self);
                    playClientEffectLoc(attacker, "clienteffect/lair_hvy_damage_fire.cef", death, 0);
                    utils.setScriptVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                }
                else 
                {
                    location death = getLocation(self);
                    playClientEffectLoc(attacker, "clienteffect/lair_med_damage_smoke.cef", death, 0);
                    utils.setScriptVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "disableAICombat") != null)
        {
            setWantSawAttackTriggers(self, false);
            return SCRIPT_CONTINUE;
        }
        int numAtt = attackers.length;
        for (int i = 0; i < numAtt; i++)
        {
            obj_id attacker = attackers[i];
            if (turret.canGenericTurretAttackTarget(attacker))
            {
                turret.addTarget(self, attacker);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getGoodTurretTarget() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!utils.hasScriptVar(self, turret.SCRIPTVAR_TARGETS))
        {
            return null;
        }
        obj_id[] old_targets = utils.getObjIdBatchScriptVar(self, turret.SCRIPTVAR_TARGETS);
        if ((old_targets == null) || (old_targets.length == 0))
        {
            utils.removeBatchScriptVar(self, turret.SCRIPTVAR_TARGETS);
            return null;
        }
        obj_id[] good_targets = cullInvalidTargets(self, old_targets);
        obj_id[] targets = removeIncapacitatedTargets(self, old_targets);
        if (targets.length == 0)
        {
            targets = good_targets;
            for (int i = 0; i < targets.length; i++)
            {
                if (isIncapacitated(targets[i]) && !isDead(targets[i]) && getDistance(self, targets[i]) <= turret.TURRET_RANGE && !pet_lib.isPet(targets[i]))
                {
                    pclib.coupDeGrace(targets[i], self);
                }
            }
            return obj_id.NULL_ID;
        }
        if (utils.hasScriptVar(self, xp.VAR_ATTACKER_LIST))
        {
            Vector attackerList = utils.getResizeableObjIdBatchScriptVar(self, xp.VAR_ATTACKER_LIST);
            if (attackerList != null && attackerList.size() > 0)
            {
                obj_var[] ovs = new obj_var[attackerList.size()];
                for (int i = 0; i < attackerList.size(); i++)
                {
                    String scriptVarPath = xp.VAR_ATTACKER_LIST + "." + ((obj_id)attackerList.get(i)) + ".damage";
                    ovs[i] = new obj_var((attackerList.elementAt(i)).toString(), utils.getIntScriptVar(self, scriptVarPath));
                }
                ovs = list.quickSort(0, ovs.length - 1, ovs);
                if ((ovs != null) && (ovs.length > 0))
                {
                    for (int i = 0; i < ovs.length; i++)
                    {
                        String ovName = ovs[i].getName();
                        obj_id tmp = utils.stringToObjId(ovName);
                        if (isIdValid(tmp))
                        {
                            if (utils.getElementPositionInArray(targets, tmp) > -1)
                            {
                                if (canSee(self, tmp))
                                {
                                    return tmp;
                                }
                            }
                        }
                    }
                }
            }
        }
        int idx = rand(0, targets.length - 1);
        if (!turret.canGenericTurretAttackTarget(targets[idx]))
        {
            turret.removeTarget(self, targets[idx]);
            return self;
        }
        return targets[idx];
    }
    public obj_id[] cullInvalidTargets(obj_id self, obj_id[] old_targets) throws InterruptedException
    {
        if (old_targets == null || old_targets.length == 0)
        {
            return null;
        }
        Vector toRemove = new Vector();
        toRemove.setSize(0);
        for (int i = 0; i < old_targets.length; i++)
        {
            if (!turret.canGenericTurretAttackTarget(old_targets[i]) || getDistance(self, old_targets[i]) > turret.TURRET_RANGE)
            {
                toRemove = utils.addElement(toRemove, old_targets[i]);
            }
        }
        if (toRemove != null && toRemove.size() > 0)
        {
            Vector targets = new Vector();
            targets = utils.removeElements(targets, toRemove);
            obj_id[] _targets = new obj_id[0];
            if (targets != null)
            {
                _targets = new obj_id[targets.size()];
                targets.toArray(_targets);
            }
            return _targets;
        }
        return old_targets;
    }
    public obj_id[] removeIncapacitatedTargets(obj_id self, obj_id[] targets) throws InterruptedException
    {
        if (!isIdValid(self) || (targets == null) || (targets.length == 0))
        {
            return null;
        }
        Vector newTargets = new Vector();
        newTargets.setSize(0);
        for (int i = 0; i < targets.length; i++)
        {
            if (!ai_lib.isAiDead(targets[i]) && getDistance(self, targets[i]) <= turret.TURRET_RANGE)
            {
                newTargets = utils.addElement(newTargets, targets[i]);
            }
        }
        obj_id[] _newTargets = new obj_id[0];
        if (newTargets != null)
        {
            _newTargets = new obj_id[newTargets.size()];
            newTargets.toArray(_newTargets);
        }
        return _newTargets;
    }
    public void explodeTurret(obj_id turretid, obj_id killer) throws InterruptedException
    {
        obj_id[] enemies = getWhoIsTargetingMe(turretid);
        if (enemies != null)
        {
            for (int i = 0; i < enemies.length; i++)
            {
                queueClearCommandsFromGroup(enemies[i], (-506878646));
                queueClearCommandsFromGroup(enemies[i], (-1170591580));
                queueClearCommandsFromGroup(enemies[i], (391413347));
                setTarget(enemies[i], null);
            }
        }
        location death = getLocation(turretid);
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        if (hasObjVar(turretid, player_structure.VAR_PLAYER_STRUCTURE))
        {
            turret.deactivateTurret(turretid);
            player_structure.destroyStructure(turretid, false);
        }
        else 
        {
            turret.deactivateTurret(turretid);
            messageTo(turretid, "handleDestroyTurret", null, 2, false);
        }
        messageTo(turretid, "handleDestroyTurret", null, 10f, false);
    }
    public float doAttack() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!turret.isActive(self))
        {
            return -1.0f;
        }
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            messageTo(self, "handleDestroyTurret", null, 2, false);
            return -1.0f;
        }
        obj_id target = utils.getObjIdScriptVar(self, turret.SCRIPTVAR_ENGAGED);
        if (!isIdValid(target) || !target.isLoaded() || (ai_lib.isAiDead(target)) || !canSee(self, target))
        {
            obj_id tmptarget = getGoodTurretTarget();
            if (tmptarget == null)
            {
                return -2.0f;
            }
            else if (tmptarget == obj_id.NULL_ID)
            {
                return 5.0f;
            }
            else if (tmptarget == self)
            {
                return 1f;
            }
            else if (tmptarget == target)
            {
                return 2f;
            }
            target = tmptarget;
            turret.engageTarget(self, target);
        }
        return doAttack(target);
    }
    public float doAttack(obj_id target) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self) || !isIdValid(target))
        {
            return -1.0f;
        }
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            messageTo(self, "handleDestroyTurret", null, 2, false);
            return -1.0f;
        }
        if (!hasObjVar(self, "objWeapon"))
        {
            return -1.0f;
        }
        if (!canSee(self, target))
        {
            obj_id tmptarget = getGoodTurretTarget();
            if (isIdValid(tmptarget) && tmptarget != target)
            {
                target = tmptarget;
                turret.engageTarget(self, target);
            }
            else 
            {
                return 1.5f;
            }
        }
        if (!turret.canGenericTurretAttackTarget(target))
        {
            turret.removeTarget(self, target);
            return 1.5f;
        }
        messageTo(self, "reconfirmTarget", null, 5, false);
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
            return -1.0f;
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
        combat.doBasicCombatSpam("shoot", cbtAttackerResults, cbtDefenderResults, cbtHitData);
        return cbtWeaponData.attackSpeed;
    }
    public int handleTurretAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!turret.isActive(self))
        {
            return SCRIPT_CONTINUE;
        }
        float delay = doAttack();
        if (delay > 0f)
        {
            messageTo(self, "handleTurretAttack", null, delay, false);
        }
        else 
        {
            utils.removeScriptVar(self, turret.SCRIPTVAR_ENGAGED);
            utils.removeScriptVar(self, "ai.combat.isInCombat");
        }
        return SCRIPT_CONTINUE;
    }
    public int effectManager(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "playingEffect");
        return SCRIPT_CONTINUE;
    }
    public int expireLair(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int enemyDecloaked(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        if (isIdValid(target))
        {
            if (getDistance(self, target) <= turret.TURRET_RANGE)
            {
                turret.addTarget(self, target);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyTurret(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
