package script.working.breinhart;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.xp;
import script.library.factions;
import script.library.dot;
import script.library.city;
import script.library.loot;
import script.library.player_structure;
import script.library.player_stomach;
import script.library.space_quest;
import script.library.space_create;
import script.library.movement;

public class speaktest extends script.base_script
{
    public speaktest()
    {
    }
    public double randBell(double avg, double var) throws InterruptedException
    {
        var = var / 2;
        double r, v1, v2;
        do
        {
            v1 = 2.0 * rand() - 1.0;
            v2 = 2.0 * rand() - 1.0;
            r = v1 * v1 + v2 * v2;
        } while (r >= 1.0 || r == 0.0);
        double fac = Math.sqrt(-2.0 * Math.log(r) / r);
        double value = (avg + (v1 * fac) * (avg * var));
        if (value < 0)
        {
            return 0;
        }
        else 
        {
            return value;
        }
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location playerLocation = getLocation(self);
        if (text.equals("meganuke"))
        {
            obj_id[] objects = getObjectsInRange(playerLocation, 250.0f);
            for (int i = 0; i < objects.length; i++)
            {
                if (!isPlayer(objects[i]))
                {
                    LOG("LOG_CHANNEL", "object deleted ->" + objects[i]);
                    destroyObject(objects[i]);
                }
            }
        }
        else if (text.equals("test"))
        {
            obj_id t = getLookAtTarget(self);
            setCraftedId(t, self);
        }
        else if (text.equals("blindme"))
        {
            setState(self, STATE_BLINDED, true);
            setObjVar(self, "combat.intEffects.13", 1);
        }
        else if (text.equals("poisonme"))
        {
            sendSystemMessageTestingOnly(self, "poisoning");
            dot.applyDotEffect(self, self, "poison", "poison1", HEALTH, -1, 5, 60, true, null);
        }
        return SCRIPT_CONTINUE;
    }
}
