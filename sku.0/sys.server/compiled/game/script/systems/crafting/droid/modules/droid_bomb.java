package script.systems.crafting.droid.modules;

import script.combat_engine.hit_result;
import script.combat_engine.weapon_data;
import script.*;
import script.library.*;

public class droid_bomb extends script.base_script
{
    public droid_bomb()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == getMaster(self))
        {
            if (hasSkill(player, "class_smuggler_phase1_novice") || hasSkill(player, "class_bountyhunter_phase1_novice"))
            {
                int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id(STF_FILE, "detonate_droid"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.SERVER_MENU5)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "not_enough_power"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            if (hasSkill(player, "class_smuggler_phase1_novice") || hasSkill(player, "class_bountyhunter_phase1_novice"))
            {
                queueCommand(player, (603112878), self, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDetonationCountdown(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            showFlyText(self, new string_id(STF_FILE, "detonation_disabled"), 1.5f, 255, 65, 65);
            obj_id master = getMaster(self);
            if (isIdValid(master))
            {
                messageTo(master, "clearBombDroidTimer", null, 0, false);
            }
            return SCRIPT_CONTINUE;
        }
        int count = params.getInt("count");
        if (count > 0)
        {
            String countdown_name = "countdown_" + count;
            showFlyText(self, new string_id(STF_FILE, countdown_name), 1.5f, 255, 65, 65);
            count -= 1;
            params.put("count", count);
            messageTo(self, "msgDetonationCountdown", params, 2.5f, false);
        }
        else 
        {
            detonateDroid(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDetonationWarmup(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "module_data.detonation_warmup");
        return SCRIPT_CONTINUE;
    }
    public void detonateDroid(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            LOG("droid_module", "crafting.droid.modules.droid_bomb.detonateDroid -- droid is invalid.");
            return;
        }
        obj_id master = getMaster(droid);
        if (!isIdValid(master))
        {
            LOG("droid_module", "crafting.droid.modules.droid_bomb.detonateDroid -- master is invalid for " + droid);
            return;
        }
        messageTo(master, "clearBombDroidTimer", null, 0, false);
        if (!master.isAuthoritative())
        {
            LOG("droid_module", "crafting.droid.modules.droid_bomb.detonateDroid -- master is not authoritative");
            return;
        }
        int bomb_level = getIntObjVar(droid, "module_data.bomb_level");
        if (bomb_level < 1)
        {
            LOG("droid_module", "crafting.droid.modules.droid_bomb.detonateDroid -- can't find a bomb level on " + droid);
            return;
        }
        int bomb_level_bonus = getIntObjVar(droid, "module_data.bomb_level_bonus");
        if (bomb_level_bonus > 0)
        {
            bomb_level += bomb_level_bonus;
        }
        float min_dam = pet_lib.DETONATION_DROID_MIN_DAMAGE * bomb_level;
        float max_dam = pet_lib.DETONATION_DROID_MAX_DAMAGE * bomb_level;
        int radius = 20 + (int)(0.3f * bomb_level);
        obj_id pet_control = callable.getCallableCD(droid);
        if (!isIdValid(pet_control))
        {
            LOG("droid_module", "crafting.droid.modules.droid_bomb.detonateDroid -- can't find pet control device for " + droid);
            return;
        }
        location loc = getLocation(droid);
        playClientEffectLoc(master, "clienteffect/combat_explosion_lair_large.cef", loc, 0);
        obj_id[] targets = utils.getAttackableTargetsInRadius(droid, radius, true);
        if (targets != null)
        {
            prose_package pp = new prose_package();
            for (obj_id target : targets) {
                int[] hit_loc =
                        {
                                HIT_LOCATION_HEAD,
                                HIT_LOCATION_BODY,
                                HIT_LOCATION_BODY,
                                HIT_LOCATION_R_ARM,
                                HIT_LOCATION_L_ARM,
                                HIT_LOCATION_R_LEG,
                                HIT_LOCATION_L_LEG
                        };
                if (isPlayer(target)) {
                    float level = getLevel(target);
                    float mult = level / 90.0f;
                    min_dam *= mult;
                    max_dam *= mult;
                }
                int final_damage = rand((int) min_dam, (int) max_dam);
                weapon_data weaponData = new weapon_data();
                hit_result hitData = new hit_result();
                weaponData.id = getDefaultWeapon(droid);
                weaponData.weaponName = getNameStringId(droid);
                weaponData.minDamage = (int) min_dam;
                weaponData.maxDamage = (int) max_dam;
                weaponData.weaponType = WEAPON_TYPE_UNARMED;
                weaponData.attackType = ATTACK_TYPE_MELEE;
                weaponData.damageType = DAMAGE_KINETIC;
                weaponData.elementalType = DAMAGE_ELEMENTAL_HEAT;
                weaponData.elementalValue = final_damage / 10;
                weaponData.attackSpeed = 1.0f;
                weaponData.woundChance = 10.0f;
                hitData.success = true;
                hitData.damage = final_damage;
                hitData.damageType = DAMAGE_KINETIC;
                hitData.elementalDamage = final_damage / 10;
                hitData.elementalDamageType = DAMAGE_ELEMENTAL_HEAT;
                hitData.blockedDamage = combat.applyArmorProtection(droid, target, weaponData, hitData, 0.0f);
                addHate(master, target, 0.0f);
                addHate(target, master, final_damage);
                doDamage(droid, target, weaponData.id, hitData);
                combat.assignDamageCredit(master, target, weaponData, hitData.damage);
                pp.stringId = new string_id(STF_FILE, "hit_by_detonation_master");
                pp.actor.set(master);
                pp.target.set(target);
                pp.digitInteger = final_damage;
                combat.sendCombatSpamMessageProse(master, pp);
                if (isPlayer(target)) {
                    pvpAttackPerformed(master, target);
                    pp.stringId = new string_id(STF_FILE, "hit_by_detonation");
                    pp.actor.set(target);
                    pp.target.set((String) null);
                    pp.digitInteger = final_damage;
                    combat.sendCombatSpamMessageProse(target, pp);
                } else {
                    xp.updateCombatXpList(target, master, xp.PERMISSIONS_ONLY, final_damage);
                    if (!ai_lib.isInCombat(target)) {
                        dictionary d = new dictionary();
                        d.put("attacker", master);
                        messageTo(target, "handleDefenderCombatAction", d, 0.0f, true);
                    }
                }
            }
        }
        destroyObject(pet_control);
        return;
    }
}
