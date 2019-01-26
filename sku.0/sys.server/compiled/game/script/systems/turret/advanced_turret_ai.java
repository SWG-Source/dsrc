package script.systems.turret;

import script.dictionary;
import script.library.advanced_turret;
import script.library.utils;
import script.obj_id;

public class advanced_turret_ai extends script.systems.combat.combat_base
{
    public advanced_turret_ai()
    {
    }
    public static final int INTERVAL_MAX = 10;
    public static final int FARTHEST_TRIGGER = INTERVAL_MAX - 1;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        advanced_turret.activateTurret(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (isGod(who))
        {
            return SCRIPT_CONTINUE;
        }
        doLogging("OnTriggerEnt", "" + getName(who) + " has entered volume " + volumeName + " which is a valid volume name: " + volumeName.startsWith(advanced_turret.ALERT_VOLUME_NAME));
        if (volumeName.startsWith(advanced_turret.ALERT_VOLUME_NAME) && who != self)
        {
            advanced_turret.addTarget(self, who);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        doLogging("OnTriggerExit", "" + getName(who) + " has exited volume name " + volumeName + " which is the farthest: " + volumeName.endsWith(advanced_turret.ALERT_VOLUME_NAME + FARTHEST_TRIGGER));
        if (volumeName.endsWith(advanced_turret.ALERT_VOLUME_NAME + FARTHEST_TRIGGER) && who != self)
        {
            advanced_turret.removeTarget(self, who);
        }
        else 
        {
            advanced_turret.addTarget(self, who);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        if (!advanced_turret.handleTurretDamage(self, attacker, weapon, damage))
        {
            int maxCondition = getMaxHitpoints(self);
            setHitpoints(self, maxCondition);
        }
        return SCRIPT_CONTINUE;
    }
    public int effectManager(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "playingEffect");
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyTurret(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int createTriggerVolume(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            advanced_turret.explodeTurret(self, self);
            return SCRIPT_CONTINUE;
        }
        float range = advanced_turret.DEFAULT_TURRET_RANGE;
        if (params.containsKey("range"))
        {
            range = params.getFloat("range");
        }
        float interval = range / INTERVAL_MAX;
        for (int i = 0; i < INTERVAL_MAX; i++)
        {
            createTriggerVolume(advanced_turret.ALERT_VOLUME_NAME + i, interval * (i + 1), true);
        }
        return SCRIPT_CONTINUE;
    }
    public int turretShot(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && isIdValid(self) && exists(self))
        {
            obj_id target = params.getObjId("target");
            if (!combatStandardAction("turretShot", self, target, advanced_turret.getTurretWeapon(self), "", null, true))
            {
                advanced_turret.removeTarget(self, target);
            }
            advanced_turret.attackPulse(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
    }
}
