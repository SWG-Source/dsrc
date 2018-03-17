package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.chat;
import script.library.combat;
import script.library.healing;
import script.library.trial;
import script.library.utils;

public class exar_wordbringer extends script.base_script
{
    public exar_wordbringer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, 644000);
        return SCRIPT_CONTINUE;
    }
    public int kill_command(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r7");
        obj_id closest = trial.getClosest(self, players);
        startCombat(self, closest);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "left_zap", trial.getSessionDict(self, "tesla"), 3.0f, false);
        messageTo(self, "right_zap", trial.getSessionDict(self, "tesla"), 6.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int left_zap(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "tesla"))
        {
            return SCRIPT_CONTINUE;
        }
        location leftLoc = getRandomLeftLocation();
        location rightLoc = getLocation(getRandomHateTarget(self));
        int timeSlice = 5;
        location blastLoc = leftLoc;
        float xInterval = ((rightLoc.x - leftLoc.x) / (float)timeSlice);
        float zInterval = ((rightLoc.z - leftLoc.z) / (float)timeSlice);
        dictionary dict = trial.getSessionDict(self, "tesla");
        dict.put("side", 0);
        for (int i = 0; i < timeSlice; i++)
        {
            blastLoc.x += xInterval;
            blastLoc.z += zInterval;
            dict.put("blastLoc", blastLoc);
            messageTo(self, "execute_blast", dict, i, false);
        }
        messageTo(self, "left_zap", trial.getSessionDict(self, "tesla"), (float)timeSlice, false);
        return SCRIPT_CONTINUE;
    }
    public int right_zap(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "tesla"))
        {
            return SCRIPT_CONTINUE;
        }
        location leftLoc = getRandomRightLocation();
        location rightLoc = getLocation(getRandomHateTarget(self));
        int timeSlice = 5;
        location blastLoc = leftLoc;
        float xInterval = ((rightLoc.x - leftLoc.x) / (float)timeSlice);
        float zInterval = ((rightLoc.z - leftLoc.z) / (float)timeSlice);
        dictionary dict = trial.getSessionDict(self, "tesla");
        dict.put("side", 1);
        for (int i = 0; i < timeSlice; i++)
        {
            blastLoc.x += xInterval;
            blastLoc.z += zInterval;
            dict.put("blastLoc", blastLoc);
            messageTo(self, "execute_blast", dict, i, false);
        }
        messageTo(self, "right_zap", dict, (float)timeSlice, false);
        return SCRIPT_CONTINUE;
    }
    public location getRandomLeftLocation() throws InterruptedException
    {
        location loc = getLocation(getSelf());
        loc.x = rand(-16.0f, 16.0f);
        loc.z = rand(55.0f, 111.0f);
        return loc;
    }
    public location getRandomRightLocation() throws InterruptedException
    {
        location loc = getLocation(getSelf());
        loc.x = rand(16.0f, 48.0f);
        loc.z = rand(55.0f, 111.0f);
        return loc;
    }
    public int execute_blast(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "tesla"))
        {
            return SCRIPT_CONTINUE;
        }
        location blastLoc = params.getLocation("blastLoc");
        String executeLoc = "" + blastLoc.x + " " + blastLoc.y + " " + blastLoc.z + " " + blastLoc.cell + " " + blastLoc.x + " " + blastLoc.y + " " + blastLoc.z;
        queueCommand(self, (-1533989887), getHateTarget(self), executeLoc, COMMAND_PRIORITY_DEFAULT);
        location tesla = getLocation(self);
        int side = params.getInt("side");
        switch (side)
        {
            case 0:
            tesla.x = -13.5f;
            tesla.y = 15.0f;
            tesla.z = 94.0f;
            break;
            case 1:
            tesla.x = 42.5f;
            tesla.y = 15.0f;
            tesla.z = 94.0f;
            break;
        }
        float travelDistance = getDistance(blastLoc, tesla);
        float speed = travelDistance / 1.0f;
        createClientProjectile(getHateTarget(self), "object/weapon/ranged/pistol/shared_pistol_green_bolt.iff", tesla, blastLoc, speed, 1.0f, false, 32, 178, 170, 255);
        return SCRIPT_CONTINUE;
    }
    public obj_id getRandomHateTarget(obj_id subject) throws InterruptedException
    {
        obj_id[] hateList = getHateList(subject);
        return hateList[rand(0, hateList.length - 1)];
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (hasObjVar(self, "stop_recording"))
        {
            return SCRIPT_CONTINUE;
        }
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= 0.1)
        {
            goP3(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void goP3(obj_id self) throws InterruptedException
    {
        setObjVar(self, "stop_recording", 1);
        setInvulnerable(self, true);
        detachScript(self, "ai.creature_combat");
        trial.bumpSession(self, "tesla");
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "exar_p3");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        location deathLoc = getLocation(self);
        deathLoc.x = 15.0f;
        deathLoc.z = 85.0f;
        pathTo(self, deathLoc);
        messageTo(self, "handle_death", null, 5.0f, false);
    }
    public int handle_death(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        obj_id professor = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.heroic.exar_kun.exar_professor");
        obj_id swordsman = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.heroic.exar_kun.exar_swordsman");
        obj_id exar_kun = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.heroic.exar_kun.exar_kun");
        setLocation(professor, getLocation(self));
        setLocation(swordsman, getLocation(self));
        setLocation(exar_kun, getLocation(self));
        setYaw(professor, getYaw(self));
        setCreatureCoverVisibility(professor, true);
        setCreatureCoverVisibility(swordsman, true);
        setCreatureCoverVisibility(exar_kun, true);
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "execute_split");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
}
