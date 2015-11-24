package script.quest.utility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class encounter_death_multi_notification extends script.base_script
{
    public encounter_death_multi_notification()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String objvarName = "quest.owner";
        if (hasObjVar(self, objvarName))
        {
            obj_id owner = getObjIdObjVar(self, objvarName);
            if (isIdValid(owner))
            {
                removeObjVar(self, objvarName);
                objvarName = "quest.name";
                if (hasObjVar(self, objvarName))
                {
                    String questName = getStringObjVar(self, objvarName);
                    removeObjVar(self, objvarName);
                    if (questName != null && questName.length() > 0)
                    {
                        dictionary destroyNotificationParameters = new dictionary();
                        destroyNotificationParameters.put("source", self);
                        destroyNotificationParameters.put("questName", questName);
                        messageTo(owner, "destroyNotification", destroyNotificationParameters, 0.0f, false);
                    }
                    else 
                    {
                        LOG("newquests", "encounter_death_multi_notification - OnDestroy() - questName is null or empty. Failint to send notification");
                    }
                }
                else 
                {
                    LOG("newquests", "encounter_death_multi_notification - OnDestroy() - could not retrieve objvar " + objvarName + " to identify the quest. Failing to send notification");
                }
            }
            else 
            {
                LOG("newquests", "encounter_death_multi_notification - OnDestroy() - " + objvarName + "=" + owner + " is invalid. Failing to send notification");
            }
        }
        else 
        {
            LOG("newquests", "encounter_death_multi_notification - OnDestroy() - could not retrieve " + objvarName + " objvar. Failing to send notification");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        String objvarName = "quest.owner";
        if (hasObjVar(self, objvarName))
        {
            obj_id owner = getObjIdObjVar(self, objvarName);
            if (isIdValid(owner))
            {
                objvarName = "quest.name";
                if (hasObjVar(self, objvarName))
                {
                    String questName = getStringObjVar(self, objvarName);
                    removeObjVar(self, objvarName);
                    if (questName != null && questName.length() > 0)
                    {
                        dictionary deathNotificationParameters = new dictionary();
                        deathNotificationParameters.put("source", self);
                        deathNotificationParameters.put("questName", questName);
                        messageTo(owner, "deathNotification", deathNotificationParameters, 0.0f, false);
                    }
                    else 
                    {
                        LOG("newquests", "encounter_death_multi_notification - OnDeath() - questName is null or empty. Failint to send notification");
                    }
                }
                else 
                {
                    LOG("newquests", "encounter_death_multi_notification - OnDeath() - could not retrieve objvar " + objvarName + " to identify the quest. Failing to send notification");
                }
            }
            else 
            {
                LOG("newquests", "encounter_death_multi_notification - OnDeath() - " + objvarName + "=" + owner + " is invalid. Failing to send notification");
            }
        }
        else 
        {
            LOG("newquests", "encounter_death_multi_notification - OnDeath() - could not retrieve " + objvarName + " objvar. Failing to send notification");
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
