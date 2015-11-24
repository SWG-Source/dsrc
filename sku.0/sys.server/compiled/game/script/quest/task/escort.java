package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.quests;

public class escort extends script.base_script
{
    public escort()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.escort"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            LOG("newquests", "escort - OnQuestActivated() - initializing escort task " + questName);
            String objvarname = "quest." + questName + ".target";
            obj_id target = null;
            float radius = 32.0f;
            if (hasObjVar(self, objvarname))
            {
                target = getObjIdObjVar(self, objvarname);
            }
            else 
            {
                String targetName = quests.getDataEntry(questRow, "TARGET");
                target = quests.getTargetForQuest(self, targetName);
            }
            if (target != null)
            {
                String locationName = null;
                location npcDestination = null;
                objvarname = "quest." + questName + ".parameter";
                if (hasObjVar(self, objvarname))
                {
                    npcDestination = getLocationObjVar(self, objvarname);
                    addLocationTarget(questName, npcDestination, radius);
                    locationName = questName;
                }
                else 
                {
                    String parameter = quests.getDataEntry(questRow, "PARAMETER");
                    if (parameter != null && parameter.length() > 0)
                    {
                        quests.activate(parameter, self, quests.getQuestGiver(questRow, self));
                        locationName = parameter;
                        objvarname = "quest." + parameter + ".selected_location";
                        npcDestination = getLocationObjVar(self, objvarname);
                        radius = 64.0f;
                        objvarname = "quest." + parameter + ".parameter";
                        if (hasObjVar(self, objvarname))
                        {
                            radius = getFloatObjVar(self, objvarname);
                        }
                    }
                    else 
                    {
                        LOG("newquests", "escort - OnQuestActivated() -  could not determine escort target location!");
                    }
                }
                if (locationName != null && locationName.length() > 0)
                {
                    objvarname = questName + ".task_questname";
                    setObjVar(self, objvarname, questName);
                }
                if (npcDestination != null)
                {
                    objvarname = "quest." + questName + ".npc_destination";
                    setObjVar(self, objvarname, npcDestination);
                    addLocationTarget(questName, npcDestination, radius);
                }
                objvarname = "quest." + questName + ".escort_target";
                setObjVar(self, objvarname, target);
                setMaster(target, self);
                ai_lib.aiFollow(target, self, 1.0f, 8.0f);
                attachScript(target, "quest.utility.destroy_notification");
                attachScript(target, "quest.utility.escort_npc");
                dictionary params = new dictionary();
                params.put("target", self);
                params.put("parameter", questName);
                messageTo(target, "addNotification", params, 0.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getEscortTarget(obj_id self, String questName) throws InterruptedException
    {
        obj_id result = null;
        String objvarname = "quest." + questName + ".escort_target";
        LOG("newquests", "escort - getEscortTarget(" + self + ", " + questName + ")");
        if (hasObjVar(self, objvarname))
        {
            result = getObjIdObjVar(self, objvarname);
            LOG("newquests", "escort - getEscortTarget - " + objvarname + "=" + result);
        }
        return result;
    }
    public boolean isEscortNearby(obj_id self, String questName) throws InterruptedException
    {
        boolean result = false;
        obj_id escortTarget = getEscortTarget(self, questName);
        if (isIdValid(escortTarget))
        {
            location escortLocation = getLocation(escortTarget);
            location myLocation = getLocation(self);
            float distance = myLocation.distance(escortLocation);
            LOG("newquests", "escort - isEscortNearby - distance between player and target is " + distance);
            if (distance > 0.0f && distance < 128.0f)
            {
                result = true;
                LOG("newquests", "escort - isEscortNearby - the escort target is in range");
            }
            else 
            {
                LOG("newquests", "escort - isEscortNearby - the escort target is not in range");
            }
        }
        else 
        {
            LOG("newquests", "escort - isEscortNearby - escortTarget(" + escortTarget + ") is invalid");
        }
        LOG("newquests", "escort - isEscortNearby - returning " + result);
        return result;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        LOG("newquests", "escort - OnArrivedAtLocation(" + locationName + ")");
        String objvarname = locationName + ".task_questname";
        if (hasObjVar(self, objvarname))
        {
            String questName = getStringObjVar(self, objvarname);
            if (questName != null && questName.length() > 0)
            {
                LOG("newquests", "escort - OnArrivedAtLocation - arrived at a location specific to this task");
                obj_id target = getEscortTarget(self, questName);
                ai_lib.aiStopFollowing(target);
                setMaster(target, null);
                messageTo(target, "handleTaskDestroyOnArrival", null, 60.0f, false);
                messageTo(target, "handleTaskArrived", null, 1.0f, false);
                objvarname = "quest." + questName + ".npc_destination";
                location l = getLocationObjVar(self, objvarname);
                if (l != null)
                {
                    ai_lib.aiPathTo(target, l);
                }
                quests.complete(locationName, self, isEscortNearby(self, questName));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyNotification(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newquests", "escort - destroyNotification()");
        if (params != null)
        {
            obj_id source = params.getObjId("source");
            LOG("newquests", "escort - destroyNotification() - source=" + source);
            if (source != null && isIdValid(source))
            {
                String parameter = params.getString("parameter");
                LOG("newquests", "escort - destroyNotification() - parameter=" + parameter);
                if (parameter != null && parameter.length() > 0)
                {
                    if (quests.isMyQuest(quests.getQuestId(parameter), "quest.task.escort"))
                    {
                        if (quests.isActive(parameter, self))
                        {
                            quests.complete(parameter, self, false);
                            LOG("newquests", "escort - destroyNotification() - failing task");
                        }
                        else 
                        {
                            LOG("newquests", "escort - destroyNotification() - quest no longer active");
                        }
                    }
                    else 
                    {
                        LOG("newquests", "escort - destroyNotification() - notification isn't for me");
                    }
                }
                else 
                {
                    LOG("newquests", "escort - destroyNotification() - parameter unavailable");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
