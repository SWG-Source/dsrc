package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class combat_consts extends script.base_script
{
    public combat_consts()
    {
    }
    public static final int toHitNumber = 75;
    public static final float baseToHit = 90.0f;
    public static final float maxToHit = 95.0f;
    public static final float minToHit = 60.0f;
    public static final float toHitScale = 50.0f;
    public static final float toHitStep = 5.0f;
    public static final float damageScale = 1500.0f;
    public static final int[] aimAttackMods = 
    {
        10,
        25,
        45
    };
    public static final float[] weaponTypeAttackLocomotionMultipliers = 
    {
        2.5f,
        2.0f,
        1.5f,
        3.0f,
        1.0f,
        1.0f,
        1.0f,
        1.0f,
        1.0f,
        1.0f,
        1.0f,
        1.0f
    };
    public static final int RANGED_STANDING_ATTACK_MOD = 0;
    public static final int RANGED_SNEAKING_ATTACK_MOD = -6;
    public static final int RANGED_WALKING_ATTACK_MOD = -40;
    public static final int RANGED_RUNNING_ATTACK_MOD = -60;
    public static final int RANGED_KNEELING_ATTACK_MOD = 15;
    public static final int RANGED_CROUCH_SNEAKING_ATTACK_MOD = 0;
    public static final int RANGED_CROUCH_WALKING_ATTACK_MOD = 0;
    public static final int RANGED_PRONE_ATTACK_MOD = 30;
    public static final int RANGED_CRAWLING_ATTACK_MOD = -80;
    public static final int RANGED_CLIMBING_STATIONARY_ATTACK_MOD = 0;
    public static final int RANGED_CLIMBING_ATTACK_MOD = 0;
    public static final int RANGED_FLOATING_ATTACK_MOD = 0;
    public static final int RANGED_SWIMMING_SURFACE_ATTACK_MOD = 0;
    public static final int RANGED_SWIMMING_UNDERWATER_ATTACK_MOD = 0;
    public static final int RANGED_HOVERING_ATTACK_MOD = 0;
    public static final int RANGED_FLYING_ATTACK_MOD = -10;
    public static final int RANGED_LYING_DOWN_ATTACK_MOD = 0;
    public static final int RANGED_SITTING_ATTACK_MOD = 0;
    public static final int RANGED_SKILL_ANIMATING_ATTACK_MOD = 0;
    public static final int RANGED_DRIVING_VEHICLE_ATTACK_MOD = 0;
    public static final int RANGED_RIDING_CREATURE_ATTACK_MOD = 0;
    public static final int RANGED_KNOCKED_DOWN_ATTACK_MOD = 0;
    public static final int RANGED_INCAPACITATED_ATTACK_MOD = 0;
    public static final int RANGED_BLOCKING_ATTACK_MOD = 0;
    public static final int RANGED_DEAD_ATTACK_MOD = 0;
    public static final int[] rangedLocomotionAttackMod = 
    {
        RANGED_STANDING_ATTACK_MOD,
        RANGED_SNEAKING_ATTACK_MOD,
        RANGED_WALKING_ATTACK_MOD,
        RANGED_RUNNING_ATTACK_MOD,
        RANGED_KNEELING_ATTACK_MOD,
        RANGED_CROUCH_SNEAKING_ATTACK_MOD,
        RANGED_CROUCH_WALKING_ATTACK_MOD,
        RANGED_PRONE_ATTACK_MOD,
        RANGED_CRAWLING_ATTACK_MOD,
        RANGED_CLIMBING_STATIONARY_ATTACK_MOD,
        RANGED_CLIMBING_ATTACK_MOD,
        RANGED_FLOATING_ATTACK_MOD,
        RANGED_SWIMMING_SURFACE_ATTACK_MOD,
        RANGED_SWIMMING_UNDERWATER_ATTACK_MOD,
        RANGED_HOVERING_ATTACK_MOD,
        RANGED_FLYING_ATTACK_MOD,
        RANGED_LYING_DOWN_ATTACK_MOD,
        RANGED_SITTING_ATTACK_MOD,
        RANGED_SKILL_ANIMATING_ATTACK_MOD,
        RANGED_DRIVING_VEHICLE_ATTACK_MOD,
        RANGED_RIDING_CREATURE_ATTACK_MOD,
        RANGED_KNOCKED_DOWN_ATTACK_MOD,
        RANGED_INCAPACITATED_ATTACK_MOD,
        RANGED_DEAD_ATTACK_MOD,
        RANGED_BLOCKING_ATTACK_MOD
    };
    public static final int RANGED_STANDING_DEFENSE_MOD = -10;
    public static final int RANGED_SNEAKING_DEFENSE_MOD = 5;
    public static final int RANGED_WALKING_DEFENSE_MOD = 25;
    public static final int RANGED_RUNNING_DEFENSE_MOD = 45;
    public static final int RANGED_KNEELING_DEFENSE_MOD = 0;
    public static final int RANGED_CROUCH_SNEAKING_DEFENSE_MOD = 0;
    public static final int RANGED_CROUCH_WALKING_DEFENSE_MOD = 0;
    public static final int RANGED_PRONE_DEFENSE_MOD = 25;
    public static final int RANGED_CRAWLING_DEFENSE_MOD = 5;
    public static final int RANGED_CLIMBING_STATIONARY_DEFENSE_MOD = 0;
    public static final int RANGED_CLIMBING_DEFENSE_MOD = 0;
    public static final int RANGED_FLOATING_DEFENSE_MOD = 0;
    public static final int RANGED_SWIMMING_SURFACE_DEFENSE_MOD = 0;
    public static final int RANGED_SWIMMING_UNDERWATER_DEFENSE_MOD = 0;
    public static final int RANGED_HOVERING_DEFENSE_MOD = -5;
    public static final int RANGED_FLYING_DEFENSE_MOD = -10;
    public static final int RANGED_LYING_DOWN_DEFENSE_MOD = 0;
    public static final int RANGED_SITTING_DEFENSE_MOD = 0;
    public static final int RANGED_SKILL_ANIMATING_DEFENSE_MOD = 0;
    public static final int RANGED_DRIVING_VEHICLE_DEFENSE_MOD = 0;
    public static final int RANGED_RIDING_CREATURE_DEFENSE_MOD = 0;
    public static final int RANGED_KNOCKED_DOWN_DEFENSE_MOD = -15;
    public static final int RANGED_INCAPACITATED_DEFENSE_MOD = 0;
    public static final int RANGED_DEAD_DEFENSE_MOD = 0;
    public static final int RANGED_BLOCKING_DEFENSE_MOD = 0;
    public static final int[] rangedLocomotionDefenseMod = 
    {
        RANGED_STANDING_DEFENSE_MOD,
        RANGED_SNEAKING_DEFENSE_MOD,
        RANGED_WALKING_DEFENSE_MOD,
        RANGED_RUNNING_DEFENSE_MOD,
        RANGED_KNEELING_DEFENSE_MOD,
        RANGED_CROUCH_SNEAKING_DEFENSE_MOD,
        RANGED_CROUCH_WALKING_DEFENSE_MOD,
        RANGED_PRONE_DEFENSE_MOD,
        RANGED_CRAWLING_DEFENSE_MOD,
        RANGED_CLIMBING_STATIONARY_DEFENSE_MOD,
        RANGED_CLIMBING_DEFENSE_MOD,
        RANGED_FLOATING_DEFENSE_MOD,
        RANGED_SWIMMING_SURFACE_DEFENSE_MOD,
        RANGED_SWIMMING_UNDERWATER_DEFENSE_MOD,
        RANGED_HOVERING_DEFENSE_MOD,
        RANGED_FLYING_DEFENSE_MOD,
        RANGED_LYING_DOWN_DEFENSE_MOD,
        RANGED_SITTING_DEFENSE_MOD,
        RANGED_SKILL_ANIMATING_DEFENSE_MOD,
        RANGED_DRIVING_VEHICLE_DEFENSE_MOD,
        RANGED_RIDING_CREATURE_DEFENSE_MOD,
        RANGED_KNOCKED_DOWN_DEFENSE_MOD,
        RANGED_INCAPACITATED_DEFENSE_MOD,
        RANGED_DEAD_DEFENSE_MOD,
        RANGED_BLOCKING_DEFENSE_MOD
    };
    public static final int MELEE_STANDING_ATTACK_MOD = 0;
    public static final int MELEE_SNEAKING_ATTACK_MOD = -20;
    public static final int MELEE_WALKING_ATTACK_MOD = 10;
    public static final int MELEE_RUNNING_ATTACK_MOD = 25;
    public static final int MELEE_KNEELING_ATTACK_MOD = -20;
    public static final int MELEE_CROUCH_SNEAKING_ATTACK_MOD = 0;
    public static final int MELEE_CROUCH_WALKING_ATTACK_MOD = 0;
    public static final int MELEE_PRONE_ATTACK_MOD = -90;
    public static final int MELEE_CRAWLING_ATTACK_MOD = -90;
    public static final int MELEE_CLIMBING_STATIONARY_ATTACK_MOD = 0;
    public static final int MELEE_CLIMBING_ATTACK_MOD = 0;
    public static final int MELEE_FLOATING_ATTACK_MOD = 0;
    public static final int MELEE_SWIMMING_SURFACE_ATTACK_MOD = 0;
    public static final int MELEE_SWIMMING_UNDERWATER_ATTACK_MOD = 0;
    public static final int MELEE_HOVERING_ATTACK_MOD = 0;
    public static final int MELEE_FLYING_ATTACK_MOD = -10;
    public static final int MELEE_LYING_DOWN_ATTACK_MOD = 0;
    public static final int MELEE_SITTING_ATTACK_MOD = 0;
    public static final int MELEE_SKILL_ANIMATING_ATTACK_MOD = 0;
    public static final int MELEE_DRIVING_VEHICLE_ATTACK_MOD = 0;
    public static final int MELEE_RIDING_CREATURE_ATTACK_MOD = 0;
    public static final int MELEE_KNOCKED_DOWN_ATTACK_MOD = 0;
    public static final int MELEE_INCAPACITATED_ATTACK_MOD = 0;
    public static final int MELEE_BLOCKING_ATTACK_MOD = 0;
    public static final int MELEE_DEAD_ATTACK_MOD = 0;
    public static final int[] meleeLocomotionAttackMod = 
    {
        MELEE_STANDING_ATTACK_MOD,
        MELEE_SNEAKING_ATTACK_MOD,
        MELEE_WALKING_ATTACK_MOD,
        MELEE_RUNNING_ATTACK_MOD,
        MELEE_KNEELING_ATTACK_MOD,
        MELEE_CROUCH_SNEAKING_ATTACK_MOD,
        MELEE_CROUCH_WALKING_ATTACK_MOD,
        MELEE_PRONE_ATTACK_MOD,
        MELEE_CRAWLING_ATTACK_MOD,
        MELEE_CLIMBING_STATIONARY_ATTACK_MOD,
        MELEE_CLIMBING_ATTACK_MOD,
        MELEE_FLOATING_ATTACK_MOD,
        MELEE_SWIMMING_SURFACE_ATTACK_MOD,
        MELEE_SWIMMING_UNDERWATER_ATTACK_MOD,
        MELEE_HOVERING_ATTACK_MOD,
        MELEE_FLYING_ATTACK_MOD,
        MELEE_LYING_DOWN_ATTACK_MOD,
        MELEE_SITTING_ATTACK_MOD,
        MELEE_SKILL_ANIMATING_ATTACK_MOD,
        MELEE_DRIVING_VEHICLE_ATTACK_MOD,
        MELEE_RIDING_CREATURE_ATTACK_MOD,
        MELEE_KNOCKED_DOWN_ATTACK_MOD,
        MELEE_INCAPACITATED_ATTACK_MOD,
        MELEE_DEAD_ATTACK_MOD,
        MELEE_BLOCKING_ATTACK_MOD
    };
    public static final int MELEE_STANDING_DEFENSE_MOD = 0;
    public static final int MELEE_SNEAKING_DEFENSE_MOD = 0;
    public static final int MELEE_WALKING_DEFENSE_MOD = 10;
    public static final int MELEE_RUNNING_DEFENSE_MOD = 25;
    public static final int MELEE_KNEELING_DEFENSE_MOD = -20;
    public static final int MELEE_CROUCH_SNEAKING_DEFENSE_MOD = 0;
    public static final int MELEE_CROUCH_WALKING_DEFENSE_MOD = 0;
    public static final int MELEE_PRONE_DEFENSE_MOD = -80;
    public static final int MELEE_CRAWLING_DEFENSE_MOD = -95;
    public static final int MELEE_CLIMBING_STATIONARY_DEFENSE_MOD = 0;
    public static final int MELEE_CLIMBING_DEFENSE_MOD = 0;
    public static final int MELEE_FLOATING_DEFENSE_MOD = 0;
    public static final int MELEE_SWIMMING_SURFACE_DEFENSE_MOD = 0;
    public static final int MELEE_SWIMMING_UNDERWATER_DEFENSE_MOD = 0;
    public static final int MELEE_HOVERING_DEFENSE_MOD = -5;
    public static final int MELEE_FLYING_DEFENSE_MOD = -40;
    public static final int MELEE_LYING_DOWN_DEFENSE_MOD = 0;
    public static final int MELEE_SITTING_DEFENSE_MOD = 0;
    public static final int MELEE_SKILL_ANIMATING_DEFENSE_MOD = 0;
    public static final int MELEE_DRIVING_VEHICLE_DEFENSE_MOD = 0;
    public static final int MELEE_RIDING_CREATURE_DEFENSE_MOD = 0;
    public static final int MELEE_KNOCKED_DOWN_DEFENSE_MOD = -15;
    public static final int MELEE_INCAPACITATED_DEFENSE_MOD = 0;
    public static final int MELEE_DEAD_DEFENSE_MOD = 0;
    public static final int MELEE_BLOCKING_DEFENSE_MOD = 0;
    public static final int[] meleeLocomotionDefenseMod = 
    {
        MELEE_STANDING_DEFENSE_MOD,
        MELEE_SNEAKING_DEFENSE_MOD,
        MELEE_WALKING_DEFENSE_MOD,
        MELEE_RUNNING_DEFENSE_MOD,
        MELEE_KNEELING_DEFENSE_MOD,
        MELEE_CROUCH_SNEAKING_DEFENSE_MOD,
        MELEE_CROUCH_WALKING_DEFENSE_MOD,
        MELEE_PRONE_DEFENSE_MOD,
        MELEE_CRAWLING_DEFENSE_MOD,
        MELEE_CLIMBING_STATIONARY_DEFENSE_MOD,
        MELEE_CLIMBING_DEFENSE_MOD,
        MELEE_FLOATING_DEFENSE_MOD,
        MELEE_SWIMMING_SURFACE_DEFENSE_MOD,
        MELEE_SWIMMING_UNDERWATER_DEFENSE_MOD,
        MELEE_HOVERING_DEFENSE_MOD,
        MELEE_FLYING_DEFENSE_MOD,
        MELEE_LYING_DOWN_DEFENSE_MOD,
        MELEE_SITTING_DEFENSE_MOD,
        MELEE_SKILL_ANIMATING_DEFENSE_MOD,
        MELEE_DRIVING_VEHICLE_DEFENSE_MOD,
        MELEE_RIDING_CREATURE_DEFENSE_MOD,
        MELEE_KNOCKED_DOWN_DEFENSE_MOD,
        MELEE_INCAPACITATED_DEFENSE_MOD,
        MELEE_DEAD_DEFENSE_MOD,
        MELEE_BLOCKING_DEFENSE_MOD
    };
    public static final int[][] locomotionAttackMod = 
    {
        meleeLocomotionAttackMod,
        rangedLocomotionAttackMod,
        rangedLocomotionAttackMod,
        rangedLocomotionAttackMod
    };
    public static final int[][] locomotionDefenseMod = 
    {
        meleeLocomotionDefenseMod,
        rangedLocomotionDefenseMod,
        rangedLocomotionDefenseMod,
        rangedLocomotionDefenseMod
    };
}
