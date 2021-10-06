package script.theme_park.wod;

import script.dictionary;
import script.library.*;
import script.obj_id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class corrupted_rancor_mutation_npc extends script.base_script
{
    public corrupted_rancor_mutation_npc()
    {
    }

    public static final String BUFF_RANCOR_ARMOR = "wod_rancor_mutation";
    public static final String BUFF_RANCOR_DAMAGE = "wod_rancor_super_strength";
    public static final String BUFF_RANCOR_BYPASS_ARMOR = "wod_rancor_razor_claws";
    public static final String DEBUFF_FINAL_ACID = "wod_decompose";
    public static final String[] RANDOM_DEBUFFS = {
            "obfuscation",
            "confusion",
            "wod_agony",
            "wavering",
            "corrosion"
    };

    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setYaw(self, -156.1289f);
        ai_lib.setDefaultCalmBehavior(self,  ai_lib.BEHAVIOR_SENTINEL);
        recurringMessageTo(self, "runGlowAppearanceLoop", null, 10f);
        handleBuffValidation(self);
        handleControllerSetup(self);
        utils.setScriptVar(self, "home", getTopMostContainer(self));
        return SCRIPT_CONTINUE;
    }

    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        handleBuffValidation(self);
        recurringMessageTo(self, "runRandomPlayerDebuffLoop", null, rand(35f, 55f));
        recurringMessageTo(self, "handleLocationValidation", null, 5f);
        recurringMessageTo(self, "handleRandomCombatTarget", null, rand(25f, 35f));
        return SCRIPT_CONTINUE;
    }

    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if(!isDead(self))
        {
            final obj_id kyrisa = utils.getObjIdScriptVar(self, "wod.kyrisa");
            messageTo(kyrisa, "endFightNoDefeat", null, 5f, false);
            setInvulnerable(self, true);
            cancelRecurringMessageTo(self, "runRandomPlayerDebuffLoop");
            cancelRecurringMessageTo(self, "handleLocationValidation");
            cancelRecurringMessageTo(self, "handleRandomCombatTarget");
            playClientEffectLoc(self, "appearance/pt_fog_barrier.prt", getLocation(self), 0f);
            playClientEffectLoc(self, "appearance/pt_acidfield_explode.prt", getLocation(self), 0.0f);
            setCreatureCoverVisibility(self, false);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }

    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        for (obj_id attacker : utils.getObjIdArrayScriptVar(self, "groupMembers"))
        {
            if(getDistance(self, attacker) < 40f)
            {
                if(!luck.isLucky(attacker))
                {
                    playClientEffectLoc(attacker, "appearance/pt_acidfield_explode.prt", getLocation(attacker), 0.0f);
                    buff.applyBuff(attacker, DEBUFF_FINAL_ACID);
                }
                if(groundquests.isTaskActive(attacker, "wod_kyrisa_boss_fight", "defeatRancor"))
                {
                    groundquests.completeTask(attacker, "wod_kyrisa_boss_fight", "defeatRancor");
                }
                else if(groundquests.isTaskActive(attacker, "wod_kyrisa_boss_fight_ns", "defeatRancor"))
                {
                    groundquests.completeTask(attacker, "wod_kyrisa_boss_fight_ns", "defeatRancor");
                }
                if(badge.hasBadge(attacker, "bdge_wod_rancor_boss"))
                {
                    badge.grantBadge(attacker, "bdge_wod_rancor_boss");
                }
            }
        }
        messageTo(utils.getObjIdScriptVar(self, "wod.kyrisa"), "endFightWithDefeat", null, 0f, false);
        return SCRIPT_CONTINUE;
    }

    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        final obj_id[] groupMembers = utils.getObjIdArrayScriptVar(self, "groupMembers");
        if(!Arrays.asList(groupMembers).contains(attacker))
        {
            sendSystemMessageTestingOnly(attacker, "You are not a group participant in this attack so your combat damage does not harm the creature.");
            addToHealth(self, IntStream.of(damage).sum());
        }
        return SCRIPT_CONTINUE;
    }

    public int OnDestroy(obj_id self) throws InterruptedException
    {
        cancelRecurringMessageTo(self, "runGlowAppearanceLoop");
        return SCRIPT_CONTINUE;
    }

    public int startBossAttack(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, "threaten");
        setInvulnerable(self, false);
        final obj_id[] group = params.getObjIdArray("groupMembers");
        utils.setScriptVar(self, "groupMembers", group);
        if(group != null && group.length > 0)
        {
            for (obj_id player : group) {
                setHate(self, player, 10000f);
                startCombat(self, player);
            }
        }
        messageTo(self, "endCombatTimerExpired", null, 600f, false);
        return SCRIPT_CONTINUE;
    }

    public int endCombatTimerExpired(obj_id self, dictionary params) throws InterruptedException
    {
        if(exists(self) && !isDead(self))
        {
            stopCombat(self);
        }
        return SCRIPT_CONTINUE;
    }

    public static void handleBuffValidation(obj_id self) throws InterruptedException
    {
        buff.applyBuffWithStackCount(self, BUFF_RANCOR_ARMOR, 10);
        buff.applyBuffWithStackCount(self, BUFF_RANCOR_DAMAGE, 1);
        buff.applyBuffWithStackCount(self, BUFF_RANCOR_BYPASS_ARMOR, 1);
    }

    public static void handleControllerSetup(obj_id self) throws InterruptedException
    {
        // find Kyrisa (there will only ever be 1, so we can take a shortcut here)
        obj_id[] creatures = getAllObjectsWithObjVar(getLocation(self), 30f, "wod.kyrisa");
        if(creatures != null && creatures.length > 0)
        {
            utils.setScriptVar(self, "wod.kyrisa", creatures[0]);
            utils.setScriptVar(creatures[0], "wod.corrupted_rancor_mutation", self);
        }
        // find Effect controllers
        utils.setScriptVar(self, "wod.effect_randoms",
                getAllObjectsWithTemplate(getLocation(self), 40f, "object/tangible/ground_spawning/patrol_waypoint.iff"));
    }

    public int runGlowAppearanceLoop(obj_id self, dictionary params) throws InterruptedException
    {
        playClientEffectObj(self, "appearance/pt_light_indoor_glow_blue.prt", self, "muzzle");
        playClientEffectObj(self, "appearance/pt_light_indoor_glow_blue.prt", self, "l_arm");
        playClientEffectObj(self, "appearance/pt_light_indoor_glow_blue.prt", self, "r_arm");
        playClientEffectObj(self, "appearance/pt_light_indoor_glow_blue.prt", self, "l_thigh");
        playClientEffectObj(self, "appearance/pt_light_indoor_glow_blue.prt", self, "r_thigh");
        return SCRIPT_CONTINUE;
    }

    public int handleLocationValidation(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id home = utils.getObjIdScriptVar(self, "home");
        final obj_id container = getContainedBy(self);
        if(container != getCellId(home, "r11") && container != getCellId(home, "r8"))
        {
            stopCombat(self);
        }
        return SCRIPT_CONTINUE;
    }

    public int runRandomPlayerDebuffLoop(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id[] players = getPlayerCreaturesInRange(self, 45f);
        final List<obj_id> potentialTargets = new ArrayList<>();
        for (obj_id player : players)
        {
            if(getHate(self, player) >= 1)
            {
                if(!luck.isLucky(player))
                {
                    potentialTargets.add(player);
                }
            }
        }
        if(potentialTargets.size() > 0)
        {
            final obj_id target = potentialTargets.get(rand(0, potentialTargets.size()-1));
            utils.setScriptVar(self, "wod_mutant_rancor.lastRandomDebuffPlayer", target);
            playClientEffectLoc(target, "appearance/pt_acidfield_explode.prt", getLocation(target), 0.0f);
            playClientEffectLoc(target, "appearance/pt_acidfield_medium.prt", getLocation(target), 0.0f);
            buff.applyBuff(target, RANDOM_DEBUFFS[rand(0, RANDOM_DEBUFFS.length-1)]);
        }
        return SCRIPT_CONTINUE;
    }

    public int handleRandomCombatTarget(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id[] group = params.getObjIdArray("groupMembers");
        final List<obj_id> potentialTargets = new ArrayList<>();
        if(group != null && group.length > 1)
        {
            for(obj_id player : group)
            {
                if(utils.getDistance2D(self, player) < 30)
                {
                    potentialTargets.add(player);
                }
            }
        }
        if(potentialTargets.size() > 0)
        {
            startCombat(self, potentialTargets.get(rand(0, potentialTargets.size()-1)));
        }
        return SCRIPT_CONTINUE;
    }

}
