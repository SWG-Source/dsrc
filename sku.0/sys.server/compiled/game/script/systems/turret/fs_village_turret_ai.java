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
import script.library.fs_dyn_village;
import script.library.list;
import script.library.pclib;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.turret;
import script.library.utils;
import script.library.xp;

public class fs_village_turret_ai extends script.systems.turret.generic_turret_ai
{
    public fs_village_turret_ai()
    {
    }
    public float doAttack(obj_id target) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self) || !isIdValid(target))
        {
            return -1.0f;
        }
        if (!hasObjVar(self, fs_dyn_village.OBJVAR_TURRET_ACCURACY))
        {
            return super.doAttack(target);
        }
        float accuracy = getFloatObjVar(self, fs_dyn_village.OBJVAR_TURRET_ACCURACY);
        float speed = getFloatObjVar(self, fs_dyn_village.OBJVAR_TURRET_SPEED);
        int power = (int)getFloatObjVar(self, fs_dyn_village.OBJVAR_TURRET_POWER);
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
        String strPlaybackAction = "fire_turret";
        obj_id objWeapon = getObjIdObjVar(self, "objWeapon");
        setWeaponMinDamage(objWeapon, power);
        setWeaponMaxDamage(objWeapon, power);
        attacker_data cbtAttackerData = new attacker_data();
        weapon_data cbtWeaponData = new weapon_data();
        obj_id[] objDefenders = new obj_id[1];
        objDefenders[0] = target;
        defender_data[] cbtDefenderData = new defender_data[objDefenders.length];
        if (!getCombatData(self, objDefenders, cbtAttackerData, cbtDefenderData, cbtWeaponData))
        {
            return -1.0f;
        }
        attacker_results cbtAttackerResults = new attacker_results();
        cbtAttackerResults.id = self;
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtAttackerResults.endPosture = -1;
        cbtAttackerResults.weapon = objWeapon;
        cbtDefenderResults[0].id = target;
        cbtDefenderResults[0].endPosture = getPosture(target);
        float toHitRoll = rand(1.0f, 100.0f);
        if (toHitRoll < accuracy)
        {
            cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
        }
        else 
        {
            cbtDefenderResults[0].result = COMBAT_RESULT_MISS;
        }
        hit_result[] cbtHitData = new hit_result[1];
        cbtHitData[0] = new hit_result();
        cbtHitData[0].success = false;
        cbtHitData[0].baseRoll = (int)toHitRoll;
        cbtHitData[0].finalRoll = cbtHitData[0].baseRoll;
        cbtHitData[0].canSee = true;
        cbtHitData[0].hitLocation = combat_engine.getHitLocation(cbtDefenderData[0]);
        cbtHitData[0].damage = power;
        cbtHitData[0].bleedingChance = 0;
        if (cbtDefenderResults[0].result == COMBAT_RESULT_HIT)
        {
            doDamage(self, target, objWeapon, cbtHitData[0]);
        }
        String[] strPlaybackNames = makePlaybackNames("fire_turret", cbtHitData, cbtWeaponData, cbtDefenderResults);
        doCombatResults(strPlaybackNames[0], cbtAttackerResults, cbtDefenderResults);
        combat.doBasicCombatSpam("shoot", cbtAttackerResults, cbtDefenderResults, cbtHitData);
        if (speed > 0)
        {
            return 1.0f / speed;
        }
        return cbtWeaponData.attackSpeed;
    }
}
