package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.corpse;
import script.library.utils;
import script.library.weapons;

public class weak_droid extends script.base_script
{
    public weak_droid()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id weapon = aiGetPrimaryWeapon(self);
        setWeaponMinDamage(weapon, 0);
        setWeaponMaxDamage(weapon, 1);
        weapons.setWeaponData(weapon);
        setRegenRate(self, HEALTH, 3);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id weapon = aiGetPrimaryWeapon(self);
        setWeaponMinDamage(weapon, 0);
        setWeaponMaxDamage(weapon, 1);
        weapons.setWeaponData(weapon);
        setRegenRate(self, HEALTH, 3);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        kill(self);
        playClientEffectObj(killer, "clienteffect/npe_training_droid_explode.cef", self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        messageTo(self, "cleanUpSelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetChanged(obj_id self, obj_id target) throws InterruptedException
    {
        removeHateTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpSelf(obj_id self, dictionary params) throws InterruptedException
    {
        location myLoc = getLocation(self);
        if (!utils.hasScriptVar(self, "npe.hasDied"))
        {
            create.object("npe_training_droid", myLoc, false);
            utils.setScriptVar(self, "npe.hasDied", true);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
