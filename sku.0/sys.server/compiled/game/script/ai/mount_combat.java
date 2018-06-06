package script.ai;

import script.dictionary;
import script.obj_id;

public class mount_combat extends script.systems.combat.combat_base
{
    public mount_combat()
    {
    }
    public static final int RANGE_NEAR = 0;
    public static final int RANGE_MID_NEAR = 1;
    public static final int RANGE_MID_FAR = 2;
    public static final int RANGE_FAR = 3;
    public static final int COMBATMODE_NONE = 0;
    public static final int COMBATMODE_DEFAULT = 1;
    public static final int COMBATMODE_EQUIPPED = 2;
    public static final int COMBATMODE_SPECIAL = 3;
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public int aiCombatLoop(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int defenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int handleDefenderCombatAction(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int vocalizeEndCombat(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int postCombatPathHome(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id target) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setWantSawAttackTriggers(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        setWantSawAttackTriggers(self, true);
        return SCRIPT_CONTINUE;
    }
}
