package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class som_lava_beetle_explosion_script extends script.base_script
{
    public som_lava_beetle_explosion_script()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        int explodeRoll = rand(1, 100);
        if (explodeRoll < 25)
        {
            playClientEffectObj(self, trial.PRT_KUBAZA_WARNING, self, "");
            messageTo(self, "nukeSelf", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int nukeSelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] targets = trial.getValidPlayersInRadius(self, 7);
        playClientEffectLoc(self, trial.PRT_KUBAZA_EXPLODE, getLocation(self), 0.4f);
        destroyObject(self);
        if (targets == null || targets.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 500);
        }
        return SCRIPT_CONTINUE;
    }
}
