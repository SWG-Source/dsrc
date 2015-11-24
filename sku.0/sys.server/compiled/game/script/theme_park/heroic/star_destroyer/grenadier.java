package script.theme_park.heroic.star_destroyer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class grenadier extends script.base_script
{
    public grenadier()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "ai.combat.oneShotAction", "non_attack");
        setThrowSpeed(self, 4.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        setMovementPercent(self, 0.0f);
        messageTo(self, "doThrow", trial.getSessionDict(self, "throw"), getThrowSpeed(self), false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self, "throw");
        return SCRIPT_CONTINUE;
    }
    public int doThrow(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "throw"))
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(self, "ai.combat.oneShotAction");
        if (hasObjVar(self, "oneShotActionComplete"))
        {
            removeObjVar(self, "oneShotActionComplete");
        }
        obj_id target = utils.hasScriptVar(self, "focus") ? utils.getObjIdScriptVar(self, "focus") : getHateTarget(self);
        queueCommand(self, (-2101389617), target, "", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "doThrow", trial.getSessionDict(self, "throw"), getThrowSpeed(self), false);
        return SCRIPT_CONTINUE;
    }
    public int grenadier_throw_random(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String[] abilityList = 
        {
            "grenadier_cold",
            "grenadier_heat",
            "grenadier_acid",
            "grenadier_kinetic"
        };
        String random_ability = abilityList[rand(0, abilityList.length - 1)];
        location loc = getLocation(target);
        String targetLoc = "" + loc.x + " " + loc.y + " " + loc.z + " " + loc.cell + " " + loc.x + " " + loc.y + " " + loc.z;
        queueCommand(self, getStringCrc(random_ability.toLowerCase()), target, targetLoc, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int resetHate(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "focus"))
        {
            utils.removeScriptVar(self, "focus");
        }
        obj_id[] hateList = getHateList(self);
        if (hateList == null || hateList.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id focus = hateList[rand(0, hateList.length - 1)];
        for (int i = 0; i < hateList.length; i++)
        {
            if (hateList[i] == focus)
            {
                setHate(self, focus, 10000.0f);
            }
            else 
            {
                setHate(self, hateList[i], 1.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public float getThrowSpeed(obj_id self) throws InterruptedException
    {
        return getFloatObjVar(self, "throw_speed");
    }
    public void setThrowSpeed(obj_id self, float speed) throws InterruptedException
    {
        setObjVar(self, "throw_speed", speed);
    }
    public float hastenThrowSpeed(obj_id self, float delta) throws InterruptedException
    {
        float speed = getThrowSpeed(self);
        speed = speed - delta > 0.1f ? speed - delta : 0.1f;
        setThrowSpeed(self, speed);
        return speed;
    }
    public int hastenThrow(obj_id self, dictionary params) throws InterruptedException
    {
        float newSpeed = hastenThrowSpeed(self, 0.2f);
        return SCRIPT_CONTINUE;
    }
}
