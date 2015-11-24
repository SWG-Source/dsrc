package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.healing;
import script.library.consumable;
import script.library.player_stomach;

public class medicine extends script.base_script
{
    public medicine()
    {
    }
    public static final String SCRIPT_BETA_MEDICINE = "beta.medicine";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int type = rand(1, 3);
        int numItems = rand(1, 4);
        attrib_mod[] am = new attrib_mod[numItems];
        for (int i = 0; i < numItems; i++)
        {
            attrib_mod tmp = new attrib_mod(0, 0, 0.0f, 0.0f, 0.0f);
            switch (type)
            {
                case 1:
                tmp = utils.createHealDamageAttribMod(rand(0, 2) * 3, rand(50, 200));
                break;
                case 2:
                tmp = utils.createAntidoteAttribMod(rand(HEALTH, WILLPOWER));
                break;
                case 3:
                tmp = utils.createHealWoundAttribMod(rand(HEALTH, WILLPOWER), rand(10, 35));
                break;
                case 4:
                tmp = utils.createHealShockAttribMod(rand(10, 35));
                break;
            }
            am[i] = tmp;
        }
        if ((am == null) || (am.length == 0))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, consumable.VAR_CONSUMABLE_MODS, am);
        int[] stomach = new int[player_stomach.STOMACH_MAX];
        for (int i = 0; i < player_stomach.STOMACH_MAX; i++)
        {
            stomach[i] = rand(1, 50);
        }
        if (stomach != null && stomach.length > 0)
        {
            setObjVar(self, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
        }
        return SCRIPT_CONTINUE;
    }
}
