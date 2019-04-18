package script.working.breinhart;

import script.library.dot;
import script.location;
import script.obj_id;

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
        double fac = Math.sqrt(-2.0 * StrictMath.log(r) / r);
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
        switch (text) {
            case "meganuke":
                obj_id[] objects = getObjectsInRange(playerLocation, 250.0f);
                for (obj_id object : objects) {
                    if (!isPlayer(object)) {
                        LOG("LOG_CHANNEL", "object deleted ->" + object);
                        destroyObject(object);
                    }
                }
                break;
            case "test":
                obj_id t = getLookAtTarget(self);
                setCraftedId(t, self);
                break;
            case "blindme":
                setState(self, STATE_BLINDED, true);
                setObjVar(self, "combat.intEffects.13", 1);
                break;
            case "poisonme":
                sendSystemMessageTestingOnly(self, "poisoning");
                dot.applyDotEffect(self, self, "poison", "poison1", HEALTH, -1, 5, 60, true, null);
                break;
        }
        return SCRIPT_CONTINUE;
    }
}
