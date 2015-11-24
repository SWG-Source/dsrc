package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.space_transition;
import script.library.space_crafting;
import script.library.skill;
import script.library.vehicle;

public class yavin_e3 extends script.base_script
{
    public yavin_e3()
    {
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
