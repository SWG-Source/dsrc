package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.trial;
import script.library.create;
import script.library.restuss_event;
import script.library.factions;

public class trigger_controller extends script.base_script
{
    public trigger_controller()
    {
    }
    public static final boolean LOGGING = true;
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!isDesiredTargetType(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        if (!canTriggerFire(self))
        {
            return SCRIPT_CONTINUE;
        }
        executeTrigger(self, whoTriggeredMe);
        return SCRIPT_CONTINUE;
    }
    public int triggerSetup(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume(params.getString("name"), utils.stringToFloat(params.getString("size")), true);
        setName(self, params.getString("name"));
        setObjVar(self, restuss_event.TRIGGER_NAME, params.getString("name"));
        setObjVar(self, restuss_event.TRIGGER_INTEREST, params.getString("triggerInterest"));
        setObjVar(self, restuss_event.TRIGGER_OCCURANCE, utils.stringToInt(params.getString("occurance")));
        setObjVar(self, restuss_event.TRIGGER_DELAY, utils.stringToFloat(params.getString("triggerDelay")));
        setObjVar(self, restuss_event.TRIGGER_TYPE, params.getString("triggerType"));
        return SCRIPT_CONTINUE;
    }
    public boolean isDesiredTargetType(obj_id target) throws InterruptedException
    {
        String targetType = getStringObjVar(getSelf(), restuss_event.TRIGGER_INTEREST);
        if (isIncapacitated(target) && isDead(target))
        {
            return false;
        }
        if (targetType.equals("omni"))
        {
            return true;
        }
        if (targetType.startsWith("player"))
        {
            return isPlayer(target);
        }
        if (targetType.equals("npc"))
        {
            return ai_lib.isNpc(target);
        }
        if (targetType.startsWith("faction"))
        {
            String[] factionParse = split(targetType, '-');
            if (factionParse == null || factionParse.length < 2)
            {
                doLogging("isDesiredTargetType", "Faction Parse was null or less than 2 entries in length");
                return false;
            }
            String specified = "omni";
            if (factionParse.length == 3)
            {
                specified = factionParse[1];
            }
            if (isPlayer(target) && (specified.equals("omni") || specified.equals("player")))
            {
                if (factionParse[2].equals("rebel"))
                {
                    return factions.isRebel(target) && !factions.isOnLeave(target);
                }
                if (factionParse[2].equals("imperial"))
                {
                    return factions.isImperial(target) && !factions.isOnLeave(target);
                }
                if (factionParse[2].equals("neutral"))
                {
                    return factions.isNeutral(target);
                }
            }
            else if (!isPlayer(target) && (specified.equals("omni") || specified.equals("npc")))
            {
                String socialGroup = getStringObjVar(target, "socialGroup");
                if (socialGroup != null && !socialGroup.equals(""))
                {
                    if (factionParse[2].equals(socialGroup))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean canTriggerFire(obj_id self) throws InterruptedException
    {
        int occurance = getIntObjVar(self, restuss_event.TRIGGER_OCCURANCE);
        if (occurance == -1)
        {
            if (utils.hasScriptVar(self, "trigger.lastFire"))
            {
                return false;
            }
            else 
            {
                utils.setScriptVar(self, "trigger.lastFire", getGameTime());
                return true;
            }
        }
        if (occurance == 0)
        {
            return true;
        }
        if (!utils.hasScriptVar(self, "trigger.lastFire"))
        {
            utils.setScriptVar(self, "trigger.lastFire", getGameTime());
            return true;
        }
        int lastFire = utils.getIntScriptVar(self, "trigger.lastFire");
        if (getGameTime() - lastFire >= occurance)
        {
            utils.setScriptVar(self, "trigger.lastFire", getGameTime());
            return true;
        }
        return false;
    }
    public void executeTrigger(obj_id self, obj_id perp) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = trial.getSessionDict(parent);
        dict.put("triggerName", getStringObjVar(self, restuss_event.TRIGGER_NAME));
        dict.put("triggerType", getStringObjVar(self, restuss_event.TRIGGER_TYPE));
        dict.put("perp", perp);
        float triggerDelay = getFloatObjVar(self, restuss_event.TRIGGER_DELAY);
        String triggerType = getStringObjVar(self, restuss_event.TRIGGER_TYPE);
        if (triggerType.equals("triggerId"))
        {
            messageTo(parent, "triggerFired", dict, triggerDelay, false);
        }
        if (triggerType.equals("signalMaster"))
        {
            messageTo(parent, dict.getString("triggerName"), dict, triggerDelay, false);
        }
        if (triggerType.equals("targetMessage"))
        {
            messageTo(parent, "sendProxySignal", dict, triggerDelay, false);
        }
        if (triggerType.equals("taskId"))
        {
            dict = trial.getSessionDict(trial.getParent(self), "wfc_signal");
            dict.put("waitForComplete", getStringObjVar(self, restuss_event.TRIGGER_NAME));
            messageTo(trial.getParent(self), "waitForComplete", dict, 0.0f, false);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/trigger_controller/" + section, message);
        }
    }
}
