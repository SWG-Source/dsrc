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
import script.library.battlefield;
import script.library.combat;
import script.library.factions;
import script.library.hq;
import script.library.list;
import script.library.pclib;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.turret;
import script.library.utils;
import script.library.xp;

public class turret_ai extends script.systems.combat.combat_base_old
{
    public turret_ai()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        turret.activateTurret(self);
        ai_lib.setAttackable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String template = getTemplateName(self);
        if (template.indexOf("adv") > -1)
        {
            return SCRIPT_CONTINUE;
        }
        explodeTurret(self, self);
        return SCRIPT_CONTINUE;
    }
    public int OnPvpFactionChanged(obj_id self, int oldFaction, int newFaction) throws InterruptedException
    {
        setTurretAttributes(self, newFaction);
        return SCRIPT_CONTINUE;
    }
    public void setTurretAttributes(obj_id self, int turretFaction) throws InterruptedException
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
        clearAttributeInterested(self, attrib.IMPERIAL);
        clearAttributeInterested(self, attrib.REBEL);
        clearAttributeInterested(self, attrib.HUTT);
        createTriggerVolume(turret.ALERT_VOLUME_NAME, turret.ALERT_VOLUME_SIZE, true);
        switch (turretFaction)
        {
            case (370444368):
            setAttributeInterested(self, attrib.ALL);
            setAttributeInterested(self, attrib.IMPERIAL);
            setAttributeInterested(self, attrib.HUTT);
            pvpSetAlignedFaction(self, (370444368));
            pvpMakeCovert(self);
            break;
            case (-615855020):
            setAttributeInterested(self, attrib.ALL);
            setAttributeInterested(self, attrib.REBEL);
            setAttributeInterested(self, attrib.HUTT);
            pvpSetAlignedFaction(self, (-615855020));
            pvpMakeCovert(self);
            break;
            default:
            setAttributeInterested(self, attrib.ALL);
            break;
        }
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
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
        if (!pvpCanAttack(self, who))
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
            if (pvpCanAttack(self, attacker))
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
                if (isIncapacitated(targets[i]) && !isDead(targets[i]) && getDistance(self, targets[i]) <= turret.FACTION_TURRET_RANGE && !pet_lib.isPet(targets[i]))
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
        if (!turret.isValidTarget(self, targets[idx]))
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
            if (!turret.isValidTarget(self, old_targets[i]) || getDistance(self, old_targets[i]) > turret.FACTION_TURRET_RANGE)
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
            if (!ai_lib.isAiDead(targets[i]) && getDistance(self, targets[i]) <= turret.FACTION_TURRET_RANGE)
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
        obj_id base = getObjIdObjVar(turretid, hq.VAR_DEFENSE_PARENT);
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
            hq.validateDefenseTracking(base);
            messageTo(turretid, "handleDestroyTurret", null, 2, false);
        }
        hq.validateDefenseTracking(base);
        messageTo(turretid, "handleDestroyTurret", null, 10f, false);
    }
    public float doAttack() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id base = getObjIdObjVar(self, hq.VAR_DEFENSE_PARENT);
        if (!turret.isActive(self))
        {
            return -1.0f;
        }
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            hq.validateDefenseTracking(base);
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
                if (utils.hasScriptVar(self, "NULL_ID Counter"))
                {
                    if (utils.getIntScriptVar(self, "NULL_ID Counter") > 5)
                    {
                        return -2.0f;
                    }
                    else 
                    {
                        int searchLoopCounter = utils.getIntScriptVar(self, "NULL_ID Counter");
                        utils.setScriptVar(self, "NULL_ID Counter", ++searchLoopCounter);
                    }
                }
                else 
                {
                    utils.setScriptVar(self, "NULL_ID Counter", 1);
                }
                return 5.0f;
            }
            else if (tmptarget == self)
            {
                return 1f;
            }
            else if (tmptarget == target)
            {
                return 5.0f;
            }
            target = tmptarget;
            turret.engageTarget(self, target);
        }
        return doAttack(target);
    }
    public float doAttack(obj_id target) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id base = getObjIdObjVar(self, hq.VAR_DEFENSE_PARENT);
        if (!isIdValid(self) || !isIdValid(target))
        {
            return -1.0f;
        }
        int curHP = getHitpoints(self);
        if (curHP < 1)
        {
            hq.validateDefenseTracking(base);
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
                return 2.0f;
            }
        }
        if (!turret.isValidTarget(self, target))
        {
            turret.removeTarget(self, target);
            return 2.0f;
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
        return cbtWeaponData.attackSpeed + rand(0, 2);
    }
    public int handleTurretAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "messageToSerialNum"))
        {
            int storedSerialNumber = utils.getIntScriptVar(self, "messageToSerialNum");
            int sentSerialNumber = params.getInt("messageToSerialNum");
            if (storedSerialNumber != sentSerialNumber)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!turret.isActive(self))
        {
            return SCRIPT_CONTINUE;
        }
        float delay = doAttack();
        if (delay > 0f)
        {
            int serialNumber = (int)rand(0, 100);
            params.put("messageToSerialNum", serialNumber);
            utils.setScriptVar(self, "messageToSerialNum", serialNumber);
            messageTo(self, "handleTurretAttack", params, delay, false);
        }
        else 
        {
            utils.removeScriptVar(self, turret.SCRIPTVAR_ENGAGED);
            utils.removeScriptVar(self, "messageToSerialNum");
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
            if (getDistance(self, target) <= turret.FACTION_TURRET_RANGE)
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
