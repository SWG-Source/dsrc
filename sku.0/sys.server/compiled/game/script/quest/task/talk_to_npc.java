package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.utils;

public class talk_to_npc extends script.base_script
{
    public talk_to_npc()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "talk_to_npc - OnQuestActivated(" + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.talk_to_npc"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            obj_id target = null;
            String objvarName = "quest." + questName + ".target";
            if (hasObjVar(self, objvarName))
            {
                target = getObjIdObjVar(self, objvarName);
            }
            else 
            {
                String targetName = quests.getDataEntry(questRow, "TARGET");
                if (targetName != null && targetName.length() > 0)
                {
                    target = quests.getTargetForQuest(self, targetName);
                }
                else 
                {
                    target = quests.getQuestGiver(questRow, self);
                }
            }
            objvarName = "quest." + questName + ".parameter";
            boolean parameter = true;
            if (hasObjVar(self, objvarName))
            {
                parameter = getBooleanObjVar(self, objvarName);
            }
            else 
            {
                String parameterName = quests.getDataEntry(questRow, "PARAMETER");
                if (parameterName != null && parameterName.length() > 0)
                {
                    if (parameterName.equals("false"))
                    {
                        parameter = false;
                    }
                    else if (utils.stringToInt(parameterName) == 0)
                    {
                        parameter = false;
                    }
                }
            }
            if (isIdValid(target))
            {
                attachScript(target, "quest.utility.npc_start_conversation_notification");
                objvarName = "quest." + self;
                setObjVar(target, objvarName, questName);
                LOG("newquests", "talk_to_npc - OnQuestActivated() - quest activated");
                if (parameter)
                {
                    dictionary locationRequestParameters = new dictionary();
                    locationRequestParameters.put("source", self);
                    locationRequestParameters.put("quest_name", questName);
                    messageTo(target, "locationRequest", locationRequestParameters, 0.0f, false);
                    LOG("newquests", "talk_to_npc - OnQuestActivated() - requesting npc(" + target + ") location to activate a waypoint");
                }
            }
            else 
            {
                LOG("newquests", "talk_to_npc - OnQuestActivated() failed to setup quest because the target could not be determined, failing the quest");
                quests.complete(questName, self, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int receiveLocationResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            String questName = params.getString("quest_name");
            if (questName != null && questName.length() > 0)
            {
                if (quests.isActive(questName, self))
                {
                    location npcLocation = params.getLocation("location");
                    if (npcLocation != null)
                    {
                        obj_id waypoint = createWaypointInDatapad(self, npcLocation);
                        String summary = quests.getDataEntry(questName, "JOURNAL_ENTRY_SUMMARY");
                        if (summary != null && summary.length() > 0)
                        {
                            setWaypointName(waypoint, summary);
                        }
                        else 
                        {
                            setWaypointName(waypoint, "missing task summary for " + questName);
                        }
                        setWaypointColor(waypoint, "yellow");
                        setWaypointActive(waypoint, true);
                        String objvarName = "quest." + questName + ".waypoint";
                        setObjVar(self, objvarName, waypoint);
                    }
                    else 
                    {
                        LOG("newquests", "talk_to_npc - receiveLocationResponse() - the location sent by the NPC was null, waypoint not activated");
                    }
                }
                else 
                {
                    LOG("newquests", "talk_to_npc - receiveLocationResponse() - " + questName + " is no longer active, a waypoint will not be created");
                }
            }
            else 
            {
                LOG("newquests", "talk_to_npc - receiveLocationResponse() - could not extract quest_name from params, failing to activate a waypoint");
            }
        }
        else 
        {
            LOG("newquests", "talk_to_npc - receiveLocationResponse() - params was null, cannot create a waypoint");
        }
        return SCRIPT_CONTINUE;
    }
    public int npcStartConversationNotification(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id source = params.getObjId("source");
            if (source != null)
            {
                String questName = params.getString("quest_name");
                if (questName != null && questName.length() > 0)
                {
                    String objvarName = "quest." + questName + ".waypoint";
                    if (hasObjVar(self, objvarName))
                    {
                        obj_id waypoint = getObjIdObjVar(self, objvarName);
                        if (isIdValid(waypoint))
                        {
                            destroyWaypointInDatapad(waypoint, self);
                        }
                    }
                    quests.complete(questName, self, true);
                }
                else 
                {
                    LOG("newquests", "talk_to_npc - npcStartConversationNotification() - could not determine quest name, ignoring message");
                }
            }
            else 
            {
                LOG("newquests", "talk_to_npc - npcStartConversationNotification() - source object id is null, ignoring message");
            }
        }
        else 
        {
            LOG("newquests", "talk_to_npc - npcStartConversationNotification() - params is null, ignoring message");
        }
        return SCRIPT_CONTINUE;
    }
}
