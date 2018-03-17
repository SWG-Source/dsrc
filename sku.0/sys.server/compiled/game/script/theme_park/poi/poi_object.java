package script.theme_park.poi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.battlefield;
import script.library.utils;
import script.library.pet_lib;
import script.library.locations;

public class poi_object extends script.theme_park.poi.base
{
    public poi_object()
    {
    }
    public static final String POI_SCRIPT = "poi.script";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (poiGetBaseObject(self) == self)
        {
            if (hasObjVar(self, "poi.instantiation"))
            {
                int intInstantiation = getIntObjVar(self, "poi.instantiation");
                intInstantiation = intInstantiation + 1;
                setObjVar(self, "poi.instantiation", intInstantiation);
            }
            else 
            {
                setObjVar(self, "poi.instantiation", 1);
            }
        }
        else 
        {
            int instantiation = getIntObjVar(poiGetBaseObject(self), "poi.instantiation");
            setObjVar(self, "poi.instantiation", instantiation);
        }
        obj_id baseObj = poiGetBaseObject(self);
        if (baseObj != self && isIdValid(baseObj))
        {
            listenToMessage(baseObj, "cleanUpRoutine");
        }
        else if (baseObj == self)
        {
            region rgnTest = battlefield.getBattlefield(getLocation(self));
            if ((locations.isInMissionCity(getLocation(self))) || (rgnTest != null))
            {
                if (!poiIsCompleted())
                {
                    poiComplete(POI_SUCCESS);
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    destroyObject(self);
                }
            }
            else 
            {
                setObjVar(self, "lastWokeUp", getGameTime());
                messageTo(self, "handlePOIExpiration", null, 36000, false);
                createTriggerVolume("playerBreachPOI", 120.0f, true);
                if (!utils.hasScriptVar(self, "sentValidator"))
                {
                    utils.setScriptVar(self, "sentValidator", 1);
                    messageTo(self, "doTriggerValidation", null, 900, false);
                }
                sendSleepModeMsg(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        obj_id baseObj = poiGetBaseObject(self);
        if (isIdValid(baseObj))
        {
            stopListeningToMessage(baseObj, "cleanUpRoutine");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (isMob(self))
        {
            if (ai_lib.isInCombat(self) && !ai_lib.isAiDead(self))
            {
                int sequenceNumber = getIntObjVar(self, "destructionSeqNumber");
                sequenceNumber++;
                if (sequenceNumber < 10)
                {
                    setObjVar(self, "destructionSeqNumber", sequenceNumber);
                    dictionary parms = new dictionary();
                    parms.put("sequenceNumber", sequenceNumber);
                    messageTo(self, "handleDelayedDestruction", parms, 120, isObjectPersisted(self));
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        if (poiGetBaseObject(self) == self)
        {
            poiBaseObjectDestroyed(self);
        }
        else 
        {
            poiObjectDestroyed(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUpRoutine(obj_id self, dictionary params) throws InterruptedException
    {
        if (isMob(self))
        {
            if (pet_lib.isPet(self))
            {
                detachScript(self, "theme_park.poi.poi_object");
                return SCRIPT_CONTINUE;
            }
            if (ai_lib.isAiDead(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (ai_lib.isInCombat(self))
            {
                removeObjVar(self, POI_BASE_OBJECT);
                messageTo(self, "cleanUpRoutine", params, 60, false);
                return SCRIPT_CONTINUE;
            }
        }
        int noMsg = params.getInt("noMessageOnDestroy");
        if (noMsg == 1)
        {
            setObjVar(self, POI_NO_MESSAGE_ON_DESTROY, true);
        }
        int cleanUp = params.getInt("baseObjectUnloading");
        if (cleanUp == 1)
        {
            setObjVar(self, POI_NO_MESSAGE_ON_DESTROY, true);
            if (!hasScript(self, "ai.pet"))
            {
                destroyObject(self);
            }
            else 
            {
                detachScript(self, "theme_park.poi_object");
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "intPersistent"))
        {
            if (poiGetBaseObject(self) != self)
            {
                detachScript(self, "theme_park.poi.poi_object");
            }
        }
        else 
        {
            if (!hasScript(self, "ai.pet"))
            {
                destroyObject(self);
            }
            else 
            {
                detachScript(self, "theme_park.poi_object");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id baseObj = poiGetBaseObject(self);
        if (baseObj != self && isIdValid(baseObj))
        {
            listenToMessage(baseObj, "cleanUpRoutine");
        }
        else if (baseObj == self)
        {
            region rgnTest = battlefield.getBattlefield(getLocation(self));
            if ((locations.isInMissionCity(getLocation(self))) || (rgnTest != null))
            {
                if (!poiIsCompleted())
                {
                    poiComplete(POI_SUCCESS);
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    destroyObject(self);
                }
            }
            else 
            {
                setObjVar(self, "lastWokeUp", getGameTime());
                createTriggerVolume("playerBreachPOI", 120.0f, true);
                sendSleepModeMsg(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int POIdeathMsgReceived(obj_id self, dictionary params) throws InterruptedException
    {
        String handlerName = params.getString("destroyMsgName");
        if (handlerName == null)
        {
            return SCRIPT_CONTINUE;
        }
        int currentInstantiation = getIntObjVar(self, "poi.instantiation");
        int originalInstantiation = params.getInt("instantiation");
        if (currentInstantiation == originalInstantiation)
        {
            messageTo(self, handlerName, params, 0, isObjectPersisted(self));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id objKiller) throws InterruptedException
    {
        obj_id baseObject = poiGetBaseObject(self);
        if (baseObject == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, POI_DESTROY_MESSAGE))
        {
            dictionary parms = new dictionary();
            String name = poiFindObjectName(self);
            if (name != null)
            {
                parms.put("name", name);
            }
            else 
            {
                final String creatureName = getCreatureName(self);
                if (creatureName != null)
                {
                    parms.put("creatureName", creatureName);
                }
            }
            String messageHandlerName = getStringObjVar(self, POI_DESTROY_MESSAGE);
            int destroyDelay = getIntObjVar(self, POI_DESTROY_MESSAGE_DELAY);
            int instantiation = getIntObjVar(baseObject, "poi.instantiation");
            parms.put("destroyMsgName", messageHandlerName);
            parms.put("instantiation", instantiation);
            messageTo(baseObject, "POIdeathMsgReceived", parms, destroyDelay, isObjectPersisted(baseObject));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id baseObject = poiGetBaseObject(self);
        if (baseObject == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            if (hasObjVar(self, POI_DESTROY_MESSAGE))
            {
                dictionary parms = new dictionary();
                String name = poiFindObjectName(self);
                if (name != null)
                {
                    parms.put("name", name);
                }
                final String creatureName = getCreatureName(self);
                if (creatureName != null)
                {
                    parms.put("creatureName", creatureName);
                }
                String messageHandlerName = getStringObjVar(self, POI_DESTROY_MESSAGE);
                int destroyDelay = getIntObjVar(self, POI_DESTROY_MESSAGE_DELAY);
                int instantiation = getIntObjVar(baseObject, "poi.instantiation");
                parms.put("destroyMsgName", messageHandlerName);
                parms.put("instantiation", instantiation);
                messageTo(baseObject, "POIdeathMsgReceived", parms, destroyDelay, isObjectPersisted(baseObject));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedDestruction(obj_id self, dictionary params) throws InterruptedException
    {
        int sequenceNumber = getIntObjVar(self, "destructionSeqNumber");
        if (sequenceNumber != params.getInt("sequenceNumber"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, "ai.pet"))
        {
            detachScript(self, "theme_park.poi_object");
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void beginSleepMode(obj_id self) throws InterruptedException
    {
        if (poiIsCompleted(self))
        {
            destroyObject(self);
            return;
        }
        if (hasObjVar(self, "npc_lair.target"))
        {
            removeObjVar(self, "npc_lair.target");
        }
        dictionary parms = new dictionary();
        parms.put("noMessageOnDestroy", 1);
        parms.put("baseObjectUnloading", 1);
        broadcastMessage("cleanUpRoutine", parms);
        String poiScript = getStringObjVar(self, POI_SCRIPT);
        detachScript(self, poiScript);
        setObjVar(self, "poi.sleepMode", true);
        utils.removeScriptVar(self, "npc_lair.numbabies");
        utils.removeScriptVar(self, "npc_lair.babeCheck");
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        LOG("poi", "ENTERING " + volumeName);
        LOG("poi", "breacher is " + breacher);
        LOG("poi", "template is " + getTemplateName(breacher));
        if (!volumeName.equals("playerBreachPOI"))
        {
            return SCRIPT_CONTINUE;
        }
        if (poiIsCompleted(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        int numPlayers = utils.getIntScriptVar(self, "poi.numPlayers");
        LOG("poi", "NumPlayers is " + numPlayers);
        numPlayers++;
        LOG("poi", "NumPlayers is " + numPlayers);
        utils.setScriptVar(self, "poi.numPlayers", numPlayers);
        if (!hasObjVar(self, "poi.sleepMode"))
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(self, "poi.sleepMode");
        String poiScript = getStringObjVar(self, POI_SCRIPT);
        if (hasScript(self, poiScript))
        {
            LOG("spawning", self + " already got one script");
        }
        else 
        {
            if (hasScript(self, "systems.spawning.spawn_template"))
            {
                if (!utils.checkServerSpawnLimits())
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
            }
            setObjVar(self, "lastWokeUp", getGameTime());
            if (!hasScript(self, poiScript))
            {
                int instantiation = getIntObjVar(self, "poi.instantiation");
                ++instantiation;
                setObjVar(self, "poi.instantiation", instantiation);
                attachScript(self, poiScript);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doTriggerValidation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objContents = getTriggerVolumeContents(self, "playerBreachPOI");
        int intPlayers = 0;
        if (objContents != null && objContents.length > 0)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                if (isPlayer(objContents[intI]))
                {
                    intPlayers++;
                }
            }
        }
        if (intPlayers > 0)
        {
            utils.setScriptVar(self, "poi.numPlayers", intPlayers);
        }
        else 
        {
            utils.removeScriptVar(self, "poi.numPlayers");
            sendSleepModeMsg(self);
        }
        messageTo(self, "doTriggerValidation", null, 900, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        LOG("poi", "EXITING" + volumeName);
        if (!volumeName.equals("playerBreachPOI"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        int numPlayers = utils.getIntScriptVar(self, "poi.numPlayers");
        LOG("poi", "numPlayers is " + numPlayers);
        numPlayers--;
        LOG("poi", "numPlayers is " + numPlayers);
        if (numPlayers < 1)
        {
            sendSleepModeMsg(self);
            utils.removeScriptVar(self, "poi.numPlayers");
        }
        else 
        {
            utils.setScriptVar(self, "poi.numPlayers", numPlayers);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePOIExpiration(obj_id self, dictionary params) throws InterruptedException
    {
        if (poiIsCompleted(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        int timesChecked = getIntObjVar(self, "poi.timesCheckedForExpire");
        timesChecked++;
        if (timesChecked > 20)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "poi.timesCheckedForExpire", timesChecked);
        if (!hasObjVar(self, "poi.sleepMode"))
        {
            messageTo(self, "handlePOIExpiration", null, 36000, false);
            return SCRIPT_CONTINUE;
        }
        int lastWakeTime = getIntObjVar(self, "lastWokeUp");
        int tenHoursAgo = (getGameTime() - 36000);
        if (lastWakeTime < tenHoursAgo)
        {
            destroyObject(self);
        }
        else 
        {
            messageTo(self, "handlePOIExpiration", null, 36000, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void sendSleepModeMsg(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "poi.sleepMode"))
        {
            return;
        }
        if (!utils.hasScriptVar(self, "npc_lair.sleepModeMessageSent"))
        {
            messageTo(self, "handleBeginSleepMode", null, 60, false);
            utils.setScriptVar(self, "npc_lair.sleepModeMessageSent", true);
        }
    }
    public int handleBeginSleepMode(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "npc_lair.sleepModeMessageSent");
        int numPlayers = utils.getIntScriptVar(self, "poi.numPlayers");
        if (numPlayers == 0)
        {
            beginSleepMode(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int proximityCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (!poiIsCompleted())
        {
            poiComplete(POI_SUCCESS);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
