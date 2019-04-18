package script.space.combat;

import script.*;
import script.library.*;

import java.util.Vector;

public class combat_ship_boss extends script.base_script
{
    public combat_ship_boss()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String bossType = null;
        if (hasObjVar(self, "bossType"))
        {
            bossType = getStringObjVar(self, "bossType");
        }
        if (bossType == null || bossType.length() <= 0)
        {
            bossType = "default_boss";
        }
        String dataTable = "datatables/space_command/boss_ships/" + bossType + ".iff";
        if (dataTable == null || dataTable.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        String[] abilityList = dataTableGetStringColumn(dataTable, "abilities");
        if (abilityList == null || abilityList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "abilityList", abilityList);
        for (String s : abilityList) {
            dictionary dctAbilityInfo = dataTableGetRow(dataTable, s);
            if (dctAbilityInfo != null) {
                String ability = dctAbilityInfo.getString("abilities");
                utils.setScriptVar(self, "boss." + ability, 1);
                String handler = dctAbilityInfo.getString("handler");
                utils.setScriptVar(self, "boss." + ability + ".handler", handler);
                int target = dctAbilityInfo.getInt("target");
                utils.setScriptVar(self, "boss." + ability + ".target", target);
                int duration = dctAbilityInfo.getInt("duration");
                utils.setScriptVar(self, "boss." + ability + ".duration", duration);
                int effect = dctAbilityInfo.getInt("effect");
                utils.setScriptVar(self, "boss." + ability + ".effect", effect);
                int cooldown = dctAbilityInfo.getInt("cooldown");
                utils.setScriptVar(self, "boss." + ability + ".cooldown", cooldown);
                int onShipHit = dctAbilityInfo.getInt("onShipHit");
                utils.setScriptVar(self, "boss." + ability + ".onShipHit", onShipHit);
                int onHittingTarget = dctAbilityInfo.getInt("onHittingTarget");
                utils.setScriptVar(self, "boss." + ability + ".onHittingTarget", onHittingTarget);
                int onShieldDepleted = dctAbilityInfo.getInt("onShieldDepleted");
                utils.setScriptVar(self, "boss." + ability + ".onShieldDepleted", onShieldDepleted);
                int onArmorDepleted = dctAbilityInfo.getInt("onArmorDepleted");
                utils.setScriptVar(self, "boss." + ability + ".onArmorDepleted", onArmorDepleted);
                int onDisabled = dctAbilityInfo.getInt("onDisabled");
                utils.setScriptVar(self, "boss." + ability + ".onDisabled", onDisabled);
                int onDestroy = dctAbilityInfo.getInt("onDestroy");
                utils.setScriptVar(self, "boss." + ability + ".onDestroy", onDestroy);
                int randomChance = dctAbilityInfo.getInt("randomChance");
                utils.setScriptVar(self, "boss." + ability + ".randomChance", randomChance);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        checkForAbility(self, "onShipHit");
        return SCRIPT_CONTINUE;
    }
    public int OnShipIsHitting(obj_id self, obj_id target, int weaponIndex, int intSlot) throws InterruptedException
    {
        checkForAbility(self, "onHittingTarget");
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitEnterCombat(obj_id self, obj_id objTarget) throws InterruptedException
    {
        if (isIdValid(objTarget) || space_utils.isShip(objTarget))
        {
            dictionary params = new dictionary();
            utils.setScriptVar(self, "combatLoopTimer", getGameTime());
            params.put("combatLoopTimer", getGameTime());
            messageTo(self, "combatLoop", params, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        checkForAbility(self, "onDestroy");
        return SCRIPT_CONTINUE;
    }
    public int OnShipDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        checkForAbility(self, "onDisabled");
        return SCRIPT_CONTINUE;
    }
    public int shieldDepleted(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "shieldDepleted", 1);
        checkForAbility(self, "onShieldDepleted");
        return SCRIPT_CONTINUE;
    }
    public int armorDepleted(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "armorDepleted", 1);
        checkForAbility(self, "onArmorDepleted");
        return SCRIPT_CONTINUE;
    }
    public int combatLoop(obj_id self, dictionary params) throws InterruptedException
    {
        int timeSent = utils.getIntScriptVar(self, "combatLoopTimer");
        int timeSentParams = params.getInt("combatLoopTimer");
        if (timeSent != timeSentParams)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id enemy = ship_ai.unitGetPrimaryAttackTarget(self);
        if (!isIdValid(enemy) || !space_utils.isShip(enemy))
        {
            return SCRIPT_CONTINUE;
        }
        checkForAbility(self, "randomChance");
        utils.setScriptVar(self, "combatLoopTimer", getGameTime());
        params.put("combatLoopTimer", getGameTime());
        messageTo(self, "combatLoop", params, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void checkForAbility(obj_id self, String trig) throws InterruptedException
    {
        if (!isIdValid(self) || trig == null || trig.length() <= 0)
        {
            return;
        }
        String[] abilityList = utils.getStringArrayScriptVar(self, "abilityList");
        for (String s : abilityList) {
            if (s == null || s.length() <= 0) {
                return;
            }
            String sv = "boss." + s + "." + trig;
            int chance = utils.getIntScriptVar(self, sv);
            if (chance > 0) {
                int randRoll = rand(1, 100);
                if (chance >= randRoll) {
                    activateAbility(self, s);
                }
            }
        }
        return;
    }
    public boolean isOnCooldown(obj_id self, String ability) throws InterruptedException
    {
        if (!isIdValid(self) || ability == null || ability.length() <= 0)
        {
            return true;
        }
        int currentTime = getGameTime();
        String activateString = "boss." + ability + ".activateTime";
        String cooldownString = "boss." + ability + ".cooldown";
        int cooldown = utils.getIntScriptVar(self, cooldownString);
        if (utils.hasScriptVar(self, activateString))
        {
            int activateTime = utils.getIntScriptVar(self, activateString);
            if (currentTime < (activateTime + cooldown))
            {
                return true;
            }
        }
        return false;
    }
    public void activateAbility(obj_id self, String ability) throws InterruptedException
    {
        if (!isIdValid(self) || ability == null || ability.length() <= 0)
        {
            return;
        }
        if (isOnCooldown(self, ability))
        {
            return;
        }
        String durationString = "boss." + ability + ".duration";
        String effectString = "boss." + ability + ".effect";
        String targetString = "boss." + ability + ".target";
        if (!utils.hasScriptVar(self, targetString))
        {
            return;
        }
        int targetType = utils.getIntScriptVar(self, targetString);
        dictionary outparams = new dictionary();
        outparams.put("duration", utils.getIntScriptVar(self, durationString));
        outparams.put("effect", utils.getIntScriptVar(self, effectString));
        Vector targetList = new Vector();
        targetList.setSize(0);
        if (targetType == 0)
        {
            utils.addElement(targetList, self);
        }
        if (targetType == 1)
        {
            utils.addElement(targetList, ship_ai.unitGetPrimaryAttackTarget(self));
        }
        if (targetType == 2)
        {
            obj_id[] hateList = ship_ai.unitGetAttackTargetList(self);
            if (hateList == null || hateList.length <= 0)
            {
                return;
            }
            utils.addElement(targetList, hateList[rand(0, hateList.length - 1)]);
        }
        if (targetType == 3)
        {
            obj_id[] hateList = ship_ai.unitGetAttackTargetList(self);
            if (hateList == null || hateList.length <= 0)
            {
                return;
            }
            for (obj_id obj_id : hateList) {
                if (!isIdValid(obj_id) || !exists(obj_id) || !space_utils.isShip(obj_id)) {
                    return;
                }
                utils.addElement(targetList, obj_id);
            }
        }
        if (targetList == null || targetList.size() <= 0)
        {
            return;
        }
        String activateString = "boss." + ability + ".activateTime";
        String handler = utils.getStringScriptVar(self, "boss." + ability + ".handler");
        utils.setScriptVar(self, activateString, getGameTime());
        outparams.put("targetList", targetList);
        if (isIdValid(self) && space_utils.isShip(self))
        {
            messageTo(self, handler, outparams, 0.0f, false);
        }
        return;
    }
    public int clearDroidCommands(obj_id self, dictionary params) throws InterruptedException
    {
        if (!space_utils.isPlayerControlledShip(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_cleared_droid_commands");
        pp.stringId = strSpam;
        for (obj_id obj_id : targetList) {
            space_combat.normalizeAllComponents(obj_id);
            space_combat.flightDroidVocalize(obj_id, 1, pp);
            sendSystemMessage(self, "All droid commands have been reset.", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int stunEngine(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_stunned_engines");
        pp.stringId = strSpam;
        int duration = params.getInt("duration");
        for (obj_id obj_id : targetList) {
            space_pilot_command.doSubSystemStun(obj_id, ship_chassis_slot_type.SCST_engine, duration);
            sendSystemMessage(obj_id, "Your engine has been disabled for a short period..", null);
            space_combat.flightDroidVocalize(obj_id, 1, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int stunWeapons(obj_id self, dictionary params) throws InterruptedException
    {
        if (!space_utils.isShip(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_stunned_weapons");
        pp.stringId = strSpam;
        int duration = params.getInt("duration");
        for (obj_id obj_id : targetList) {
            for (int j = ship_chassis_slot_type.SCST_weapon_first; j < ship_chassis_slot_type.SCST_weapon_last; j++) {
                if (isShipSlotInstalled(obj_id, j)) {
                    space_pilot_command.doSubSystemStun(obj_id, j, duration);
                }
            }
            sendSystemMessage(obj_id, "Your weapons have been disabled for a short period.", null);
            space_combat.flightDroidVocalize(obj_id, 1, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int missileSwarm(obj_id self, dictionary params) throws InterruptedException
    {
        if (!space_utils.isShip(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int leachShieldEnergy(obj_id self, dictionary params) throws InterruptedException
    {
        int duration = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_leached_shield");
        pp.stringId = strSpam;
        for (obj_id obj_id : targetList) {
            float preFrontEnergy = space_combat.getShipShieldHitpointsFrontCurrent(obj_id);
            float preBackEnergy = space_combat.getShipShieldHitpointsBackCurrent(obj_id);
            space_combat.drainEnergyFromShield(obj_id, 0, effect, false);
            space_combat.drainEnergyFromShield(obj_id, 1, effect, false);
            float energyDrainedFront = preFrontEnergy - space_combat.getShipShieldHitpointsFrontCurrent(obj_id);
            float energyDrainedBack = preBackEnergy - space_combat.getShipShieldHitpointsBackCurrent(obj_id);
            space_combat.addEnergyToShield(self, 0, energyDrainedFront, true);
            space_combat.addEnergyToShield(self, 1, energyDrainedBack, true);
            space_combat.flightDroidVocalize(obj_id, 2, pp);
            sendSystemMessage(obj_id, "Energy has been drained from your shield and transferred to your opponent.", null);
        }
        duration--;
        if (duration > 0)
        {
            params.put("duration", duration);
            messageTo(self, "leachShieldEnergy", params, 5.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeShieldEnergy(obj_id self, dictionary params) throws InterruptedException
    {
        int duration = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_drained_shields");
        pp.stringId = strSpam;
        for (obj_id obj_id : targetList) {
            space_combat.drainEnergyFromShield(obj_id, 0, effect, false);
            space_combat.drainEnergyFromShield(obj_id, 1, effect, false);
            sendSystemMessage(obj_id, "Energy has been drained from your shield.", null);
            space_combat.flightDroidVocalize(obj_id, 2, pp);
        }
        duration--;
        if (duration > 0)
        {
            params.put("duration", duration);
            messageTo(self, "removeShieldEnergy", params, 5.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int restoreShieldEnergy(obj_id self, dictionary params) throws InterruptedException
    {
        int duration = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_restore_shield");
        pp.stringId = strSpam;
        for (obj_id obj_id : targetList) {
            space_combat.addEnergyToShield(obj_id, 0, effect, false);
            space_combat.addEnergyToShield(obj_id, 1, effect, false);
            obj_id[] attackers = ship_ai.unitGetWhoIsTargetingMe(obj_id);
            for (obj_id attacker : attackers) {
                if (space_utils.isPlayerControlledShip(attacker)) {
                    space_combat.flightDroidVocalize(attacker, 3, pp);
                    sendSystemMessage(attacker, "Your target has activated a rapid shield restoration system.", null);
                }
            }
        }
        duration--;
        if (duration > 0)
        {
            params.put("duration", duration);
            messageTo(self, "restoreShieldEnergy", params, 5.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeCapacitorEnergy(obj_id self, dictionary params) throws InterruptedException
    {
        int duration = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_drained_capacitor");
        pp.stringId = strSpam;
        for (obj_id obj_id : targetList) {
            float fltCurrentEnergy = space_combat.getShipCapacitorEnergyCurrent(obj_id);
            if (effect > fltCurrentEnergy) {
                effect = fltCurrentEnergy;
            }
            space_combat.drainEnergyFromCapacitor(obj_id, effect, true);
            space_combat.flightDroidVocalize(obj_id, 2, pp);
            sendSystemMessage(obj_id, "Energy has been drained from your capacitor.", null);
        }
        duration--;
        if (duration > 0)
        {
            params.put("duration", duration);
            messageTo(self, "removeCapacitorEnergy", params, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int repairComponents(obj_id self, dictionary params) throws InterruptedException
    {
        int duration = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = new prose_package();
        string_id strSpam = new string_id("space/space_interaction", "boss_repair");
        pp.stringId = strSpam;
        for (int i = 0; i < targetList.length; i++)
        {
            obj_id[] attackers = ship_ai.unitGetWhoIsTargetingMe(self);
            for (obj_id attacker : attackers) {
                if (space_utils.isPlayerControlledShip(attacker)) {
                    sendSystemMessage(attacker, "Your target has activated a rapid self repair system.", null);
                    space_combat.flightDroidVocalize(attacker, 3, pp);
                }
            }
        }
        duration--;
        if (duration > 0)
        {
            params.put("duration", duration);
            messageTo(self, "repairComponents", params, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int selfDestructExplosion(obj_id self, dictionary params) throws InterruptedException
    {
        int range = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = params.getObjIdArray("targetList");
        if (targetList == null || targetList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id obj_id : targetList) {
            int intSide = 0;
            playClientEffectLoc(self, "appearance/pt_ig88_droid_bomb.prt", getLocation(obj_id), 0.0f);
            obj_id[] shipObjects = getAllObjectsWithScript(getLocation(obj_id), range, "space.combat.combat_ship");
            for (obj_id shipObject : shipObjects) {
                if (!isIdValid(shipObject) || !exists(shipObject)) {
                    continue;
                }
                float fltDamage = effect;
                transform attackerTransform_w = getTransform_o2w(obj_id);
                transform defenderTransform_w = getTransform_o2w(shipObject);
                vector hitDirection_o = defenderTransform_w.rotateTranslate_p2l(attackerTransform_w.getPosition_p());
                if (hitDirection_o.z < 0.0f) {
                    intSide = 1;
                }
                int intWeaponSlot = space_crafting.WEAPON_0;
                fltDamage = space_combat.doShieldDamage(obj_id, shipObject, intWeaponSlot, fltDamage, intSide);
                if (fltDamage > 0) {
                    fltDamage = space_combat.doArmorDamage(obj_id, shipObject, intWeaponSlot, fltDamage, intSide);
                }
                if (fltDamage > 0) {
                    fltDamage = space_combat.doComponentDamage(obj_id, shipObject, intWeaponSlot, space_combat.SHIP, fltDamage, intSide);
                }
                if (fltDamage > 0) {
                    space_combat.doChassisDamage(obj_id, shipObject, 0, fltDamage);
                }
                if (fltDamage > 0) {
                    messageTo(shipObject, "megaDamage", null, 0.0f, false);
                }
            }
            messageTo(obj_id, "megaDamage", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int capShipMineDrop(obj_id self, dictionary params) throws InterruptedException
    {
        int duration = params.getInt("duration");
        float effect = params.getInt("effect");
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int numMines = 3;
        obj_id[] mineArray = new obj_id[numMines];
        for (int i = 0; i < mineArray.length; i++)
        {
            float dist = -350.0f;
            transform gloc = getTransform_o2w(self);
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-20.0f, 20.0f));
            vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-20.0f, 20.0f));
            vector vd = vi.add(vj);
            gloc = gloc.move_p(vd);
            obj_id spaceMine = createObject("object/tangible/space/mission_objects/cap_ship_mine.iff", gloc, null);
            playClientEffectLoc(self, "appearance/pr_green_fire_base.prt", getLocation(spaceMine), 0.0f);
            mineArray[i] = spaceMine;
        }
        params.put("mineArray", mineArray);
        params.put("bossId", self);
        messageTo(self, "capShipMineExplosion", params, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int capShipMineExplosion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] mineArray = params.getObjIdArray("mineArray");
        if (mineArray == null || mineArray.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        float effect = params.getInt("effect");
        obj_id bossId = params.getObjId("bossId");
        float range = 100.0f;
        for (obj_id obj_id : mineArray) {
            obj_id[] shipObjects = getAllObjectsWithScript(getLocation(obj_id), range, "space.combat.combat_ship");
            playClientEffectLoc(self, "appearance/pt_ig88_droid_bomb.prt", getLocation(obj_id), 0.0f);
            for (obj_id shipObject : shipObjects) {
                if (!isIdValid(shipObject) || !exists(shipObject) || !space_utils.isShip(shipObject)) {
                    continue;
                }
                int intSide = 0;
                float fltDamage = effect;
                transform attackerTransform_w = getTransform_o2w(bossId);
                transform defenderTransform_w = getTransform_o2w(shipObject);
                vector hitDirection_o = defenderTransform_w.rotateTranslate_p2l(attackerTransform_w.getPosition_p());
                if (hitDirection_o.z < 0.0f) {
                    intSide = 1;
                }
                int intWeaponSlot = space_crafting.WEAPON_0;
                fltDamage = space_combat.doShieldDamage(self, shipObject, intWeaponSlot, fltDamage, intSide);
                if (fltDamage > 0) {
                    fltDamage = space_combat.doArmorDamage(self, shipObject, intWeaponSlot, fltDamage, intSide);
                }
                if (fltDamage > 0) {
                    fltDamage = space_combat.doComponentDamage(self, shipObject, intWeaponSlot, space_combat.SHIP, fltDamage, intSide);
                }
                if (fltDamage > 0) {
                    space_combat.doChassisDamage(self, shipObject, 0, fltDamage);
                }
                if (fltDamage > 0) {
                    messageTo(shipObject, "megaDamage", null, 0.0f, false);
                }
            }
            destroyObject(obj_id);
        }
        return SCRIPT_CONTINUE;
    }
}
