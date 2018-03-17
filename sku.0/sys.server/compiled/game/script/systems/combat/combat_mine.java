package script.systems.combat;

import script.*;
import script.library.attrib;
import script.library.colors;
import script.library.utils;

import java.util.Vector;

public class combat_mine extends script.base_script
{
    public combat_mine()
    {
    }
    public static final String detonateVolName = "landMineDetonationRadius";
    public static final String mineDataTable = "datatables/combat/npc_landmines.iff";
    public static final String flagPlayer = "targetFlag.playerAndPet";
    public static final String flagNpc = "targetFlag.npc-SmugglerReversed";
    public static final String STF = "npc_landmines";
    public static final string_id mineHitMessage = new string_id(STF, "hit_by_mine_detonation");
    public static final string_id commandoDefuseAction = new string_id(STF, "commando_defuse");
    public static final string_id mineDefused = new string_id(STF, "mine_defused");
    public static final string_id smugglerReverseTrigger = new string_id(STF, "smuggler_reverse_trigger");
    public static final string_id mineTriggerReversed = new string_id(STF, "mine_trigger_reversed");
    public static final string_id deactivated = new string_id(STF, "deactivated");
    public static final string_id reversed = new string_id(STF, "reversed");
    public static final boolean loggingOn = false;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        verifyMine(self);
        setDetonateVolumeByType(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        verifyMine(self);
        setDetonateVolumeByType(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        messageTo(getObjIdObjVar(self, "parentSpawner"), "handleMineRespawn", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDeactivated() || isFlagSwitched())
        {
            return SCRIPT_CONTINUE;
        }
        if (isCommando(player))
        {
            doLogging("OnObjectMenuRequest", "Commando request the defuse option");
            mi.addRootMenu(menu_info_types.LANDMINE_DISARM, commandoDefuseAction);
        }
        if (isSmuggler(player))
        {
            doLogging("OnObjectMenuRequest", "Smuggler requested the flip trigger option");
            mi.addRootMenu(menu_info_types.LANDMINE_REVERSE_TRIGGER, smugglerReverseTrigger);
        }
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "You are getting the options because you are in god mode");
            mi.addRootMenu(menu_info_types.LANDMINE_DISARM, commandoDefuseAction);
            mi.addRootMenu(menu_info_types.LANDMINE_REVERSE_TRIGGER, smugglerReverseTrigger);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.LANDMINE_DISARM)
        {
            doCommandoDeactivateAction(player);
        }
        if (item == menu_info_types.LANDMINE_REVERSE_TRIGGER)
        {
            doSmugglerFlagSwitchAction(player);
        }
        return SCRIPT_CONTINUE;
    }
    public void doCommandoDeactivateAction(obj_id player) throws InterruptedException
    {
        doLogging("doCommandoDeactivationAction", "Deactivating mine, sending signal to destroy self in 10 minutes");
        obj_id mine = getSelf();
        setObjVar(mine, "deactivated", 1);
        messageTo(mine, "destroySelf", null, 600, false);
        sendSystemMessage(player, mineDefused);
        dictionary params = new dictionary();
        params.put("type", "commando");
        messageTo(mine, "showStatusFlyText", params, 0, false);
    }
    public void doSmugglerFlagSwitchAction(obj_id player) throws InterruptedException
    {
        doLogging("doSmugglerFlagSwitchAction", "Flipping mine flag, sending signal to destroy self in 10 minutes");
        obj_id mine = getSelf();
        setObjVar(mine, "targetFlag", flagNpc);
        messageTo(mine, "destroySelf", null, 600, false);
        sendSystemMessage(player, mineTriggerReversed);
        dictionary params = new dictionary();
        params.put("type", "smuggler");
        messageTo(mine, "showStatusFlyText", params, 0, false);
    }
    public int showStatusFlyText(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("commando"))
        {
            showFlyText(getSelf(), deactivated, 1.0f, colors.GREEN);
            messageTo(getSelf(), "showStatusFlyText", params, 5, false);
            return SCRIPT_CONTINUE;
        }
        if (type.equals("smuggler"))
        {
            showFlyText(getSelf(), reversed, 1.0f, colors.ORANGERED);
            messageTo(getSelf(), "showStatusFlyText", params, 5, false);
            return SCRIPT_CONTINUE;
        }
        doLogging("showStatusFlyText", "Fly text was called but neither commando or smuggler was the type");
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(getSelf());
        return SCRIPT_CONTINUE;
    }
    public boolean isDeactivated() throws InterruptedException
    {
        doLogging("isDeactivated", "Checking is deactivated " + hasObjVar(getSelf(), "deactivated"));
        return hasObjVar(getSelf(), "deactivated");
    }
    public boolean isFlagSwitched() throws InterruptedException
    {
        doLogging("isFlagSwitched", "Current flag is: " + getStringObjVar(getSelf(), "targetFlag"));
        return ((getStringObjVar(getSelf(), "targetFlag")).equals(flagNpc));
    }
    public void verifyMine(obj_id landMine) throws InterruptedException
    {
        if (!hasObjVar(landMine, "mineType"))
        {
            doLogging("verifyMine", "Mine destroyed itself due to invalid mineType objvar");
            destroyObject(landMine);
            return;
        }
        String mineType = getStringObjVar(landMine, "mineType");
        String testValid = dataTableGetString(mineDataTable, mineType, "mineType");
        if (testValid == null || testValid.equals(""))
        {
            doLogging("verifyMine", "Mine destroyed itself due to invalid entry(" + mineType + ") in datatable(" + mineDataTable + ")");
            destroyObject(landMine);
            return;
        }
    }
    public void setDetonateVolumeByType(obj_id landMine) throws InterruptedException
    {
        String mineType = getStringObjVar(landMine, "mineType");
        float detonateRange = dataTableGetFloat(mineDataTable, mineType, "detonateRange");
        setAttributeInterested(landMine, attrib.ALL);
        if (!hasTriggerVolume(landMine, detonateVolName))
        {
            doLogging("setDetonatevolumeByType", "Detonation range for mineType(" + mineType + ") set to: " + detonateRange);
            createTriggerVolume(detonateVolName, detonateRange, true);
        }
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if(!isIdValid(self) || !exists(self)){
            return SCRIPT_CONTINUE;
        }
        if (isDeactivated())
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(detonateVolName))
        {
            if (isValidTargetTypeByMineFlag(breacher))
            {
                if (willTriggerMineBlast(breacher))
                {
                    obj_id[] targets = getTargetsInBlastRadius();
                    if (targets == null)
                    {
                        doLogging("OnTriggerVolumeEntered", "Mine destroyed iself because no targets were returned from getTargetsInBlastRadius");
                        destroyObject(getSelf());
                        return SCRIPT_CONTINUE;
                    }
                    applyMineEffects(targets);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getTargetsInBlastRadius() throws InterruptedException
    {
        obj_id landMine = getSelf();
        String mineType = getStringObjVar(landMine, "mineType");
        float blastRadius = dataTableGetFloat(mineDataTable, mineType, "blastRadius");
        location loc = getLocation(landMine);
        if (loc == null)
        {
            return null;
        }
        obj_id[] objects = getObjectsInRange(loc, blastRadius);
        Vector targetsInRadius = new Vector();
        targetsInRadius.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            if (isValidTargetTypeByMineFlag(objects[i]))
            {
                if (!isIncapacitated(objects[i]) && !isDead(objects[i]))
                {
                    targetsInRadius = utils.addElement(targetsInRadius, objects[i]);
                    doLogging("getTargetsInBlastRadius", "Target(" + getName(objects[i]) + "/" + objects[i] + ") added to targetsInRadius array");
                }
            }
        }
        if (targetsInRadius.size() < 1)
        {
            doLogging("getTargetsInBlastRadius", "targetsInRadius is less than 1, returning null");
            return null;
        }
        else 
        {
            obj_id[] _targetsInRadius = new obj_id[0];
            if (targetsInRadius != null)
            {
                _targetsInRadius = new obj_id[targetsInRadius.size()];
                targetsInRadius.toArray(_targetsInRadius);
            }
            return _targetsInRadius;
        }
    }
    public void applyMineEffects(obj_id[] targets) throws InterruptedException
    {
        obj_id landMine = getSelf();
        location mineLoc = getLocation(getSelf());
        String mineType = getStringObjVar(landMine, "mineType");
        int minDamage = dataTableGetInt(mineDataTable, mineType, "minDamage");
        int maxDamage = dataTableGetInt(mineDataTable, mineType, "maxDamage");
        String stringDamageType = dataTableGetString(mineDataTable, mineType, "damageType");
        String mineDetonationEffect = dataTableGetString(mineDataTable, mineType, "effectOnExplode");
        int damageType = getDamageTypeFromString(stringDamageType);
        doLogging("applyMineEffects", "mineType: " + mineType + ", minDamage: " + minDamage + ", maxDamage: " + maxDamage + ", stringDamageType: " + stringDamageType + ", mineDetonationEffect: " + mineDetonationEffect);
        if (damageType == -1)
        {
            doLogging("applyMineEffects", "getDamageTypeFromString returned -1 when sent " + stringDamageType + " for mine " + mineType);
            destroyObject(landMine);
            return;
        }
        playClientEffectLoc(targets[0], mineDetonationEffect, mineLoc, 0.4f);
        for (int i = 0; i < targets.length; i++)
        {
            int damageToApply = rand(minDamage, maxDamage);
            if (damage(targets[i], damageType, HIT_LOCATION_BODY, damageToApply))
            {
                doLogging("applyMineEffects", "Applied " + damageToApply + " points of damage to " + getName(targets[i]));
            }
        }
        destroyObject(landMine);
    }
    public int getDamageTypeFromString(String damageType) throws InterruptedException
    {
        if (damageType.equals("blast"))
        {
            return DAMAGE_BLAST;
        }
        if (damageType.equals("energy"))
        {
            return DAMAGE_ENERGY;
        }
        if (damageType.equals("stun"))
        {
            return DAMAGE_STUN;
        }
        if (damageType.equals("heat"))
        {
            return DAMAGE_ELEMENTAL_HEAT;
        }
        if (damageType.equals("cold"))
        {
            return DAMAGE_ELEMENTAL_COLD;
        }
        if (damageType.equals("acid"))
        {
            return DAMAGE_ELEMENTAL_ACID;
        }
        if (damageType.equals("electrical"))
        {
            return DAMAGE_ELEMENTAL_ELECTRICAL;
        }
        if (damageType.equals("kinetic"))
        {
            return DAMAGE_KINETIC;
        }
        return -1;
    }
    public boolean isValidTargetTypeByMineFlag(obj_id subject) throws InterruptedException
    {
        String targetType = flagPlayer;
        if (hasObjVar(getSelf(), "targetFlag"))
        {
            targetType = getStringObjVar(getSelf(), "targetFlag");
        }
        if (targetType.equals(flagPlayer))
        {
            if (isPlayer(subject) || hasScript(subject, "ai.pet"))
            {
                return true;
            }
        }
        if (targetType.equals(flagNpc))
        {
            if (isMob(subject))
            {
                if (!isPlayer(subject) && !hasScript(subject, "ai.pet"))
                {
                    return true;
                }
            }
        }
        doLogging("isValidTargetTypeByMineFlag", "Subject bypassed all flag checks: " + getName(subject));
        return false;
    }
    public boolean willTriggerMineBlast(obj_id breacher) throws InterruptedException
    {
        if (getPosture(breacher) == POSTURE_PRONE)
        {
            if (!isCommandoOrSmuggler(breacher))
            {
                return (1 == rand(1, 20));
            }
            else 
            {
                return false;
            }
        }
        doLogging("willTriggerMineBlast", "willTrigger returned the default true for breacher " + getName(breacher));
        return true;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (loggingOn)
        {
            LOG("debug/combat_mine/" + section, message);
        }
    }
    public boolean isCommandoOrSmuggler(obj_id subject) throws InterruptedException
    {
        return (isCommando(subject) || isSmuggler(subject));
    }
    public boolean isCommando(obj_id subject) throws InterruptedException
    {
        return (hasSkill(subject, "class_commando_phase1_novice") || hasSkill(subject, "class_munitions_phase3_master"));
    }
    public boolean isSmuggler(obj_id subject) throws InterruptedException
    {
        return false;
    }
}
