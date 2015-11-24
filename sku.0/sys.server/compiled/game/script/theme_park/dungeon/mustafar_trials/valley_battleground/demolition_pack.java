package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.combat;
import script.library.colors;
import script.library.attrib;
import script.library.trial;

public class demolition_pack extends script.base_script
{
    public demolition_pack()
    {
    }
    public static final String DATA_TABLE = "datatables/combat/npc_landmines.iff";
    public static final String STF = "npc_landmines";
    public static final string_id SHOW_PAGE_TEXT = new string_id(STF, "charge_page_text");
    public static final boolean LOGGING = false;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        verifyMine(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        verifyMine(self);
        trial.markAsTempObject(self, true);
        return SCRIPT_CONTINUE;
    }
    public int detonateCharge(obj_id self, dictionary params) throws InterruptedException
    {
        applyChargeEffects();
        return SCRIPT_CONTINUE;
    }
    public int pageCharge(obj_id self, dictionary params) throws InterruptedException
    {
        showFlyText(getSelf(), SHOW_PAGE_TEXT, 1.0f, colors.GREEN);
        return SCRIPT_CONTINUE;
    }
    public void verifyMine(obj_id landMine) throws InterruptedException
    {
        if (!utils.hasScriptVar(landMine, "mineType"))
        {
            doLogging("verifyMine", "charge destroyed itself due to invalid mineType script");
            destroyObject(landMine);
            return;
        }
        String chargeType = utils.getStringScriptVar(landMine, "mineType");
        String testValid = dataTableGetString(DATA_TABLE, chargeType, "mineType");
        if (testValid == null || testValid.equals(""))
        {
            doLogging("verifyMine", "charge destroyed itself due to invalid entry(" + chargeType + ") in datatable(" + DATA_TABLE + ")");
            destroyObject(landMine);
            return;
        }
    }
    public obj_id[] getTargetsInBlastRadius() throws InterruptedException
    {
        obj_id landMine = getSelf();
        String mineType = utils.getStringScriptVar(landMine, "mineType");
        float blastRadius = dataTableGetFloat(DATA_TABLE, mineType, "blastRadius");
        location loc = getLocation(landMine);
        if (loc == null)
        {
            doLogging("getTargetsInBlastRadius", "Failed to determine my location, returning null");
            return null;
        }
        obj_id[] objects = getObjectsInRange(loc, blastRadius);
        Vector targetsInRadius = new Vector();
        targetsInRadius.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            if (isPlayer(objects[i]) || isMob(objects[i]))
            {
                if (!isIncapacitated(objects[i]) && !isDead(objects[i]))
                {
                    targetsInRadius = utils.addElement(targetsInRadius, objects[i]);
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
    public void applyChargeEffects() throws InterruptedException
    {
        obj_id detonationCharge = getSelf();
        location mineLoc = getLocation(getSelf());
        String chargeType = utils.getStringScriptVar(detonationCharge, "mineType");
        int minDamage = dataTableGetInt(DATA_TABLE, chargeType, "minDamage");
        int maxDamage = dataTableGetInt(DATA_TABLE, chargeType, "maxDamage");
        String stringDamageType = dataTableGetString(DATA_TABLE, chargeType, "damageType");
        String mineDetonationEffect = dataTableGetString(DATA_TABLE, chargeType, "effectOnExplode");
        int damageType = getDamageTypeFromString(stringDamageType);
        if (damageType == -1)
        {
            doLogging("applyMineEffects", "getDamageTypeFromString returned -1 when sent " + stringDamageType + " for mine " + chargeType);
            destroyObject(detonationCharge);
            return;
        }
        obj_id[] targets = getTargetsInBlastRadius();
        if (targets == null || targets.length == 0)
        {
            playClientEffectLoc(detonationCharge, mineDetonationEffect, getLocation(detonationCharge), 0.4f);
            destroyObject(detonationCharge);
            return;
        }
        playClientEffectLoc(detonationCharge, mineDetonationEffect, getLocation(detonationCharge), 0.4f);
        for (int i = 0; i < targets.length; i++)
        {
            int damageToApply = rand(minDamage, maxDamage);
            damage(targets[i], damageType, HIT_LOCATION_BODY, damageToApply);
        }
        destroyObject(detonationCharge);
    }
    public int getDamageTypeFromString(String damageType) throws InterruptedException
    {
        if (damageType.equals("blast"))
        {
            return DAMAGE_ELEMENTAL_HEAT;
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
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/demolition_pack/" + section, message);
        }
    }
}
