package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.locations;
import script.library.quests;
import script.library.utils;

public class give_object_to_player extends script.base_script
{
    public give_object_to_player()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "give_object_to_player - OnQuestActivated(+ " + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.give_object_to_player"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            if (questName != null)
            {
                String attachedScriptName;
                String parameterObjVarName = "quest." + questName + ".parameter";
                if (hasObjVar(self, parameterObjVarName))
                {
                    attachedScriptName = getStringObjVar(self, parameterObjVarName);
                }
                else 
                {
                    attachedScriptName = quests.getDataEntry(questRow, "PARAMETER");
                }
                String templateName;
                String targetObjVarName = "quest." + questName + ".target";
                if (hasObjVar(self, targetObjVarName))
                {
                    templateName = getStringObjVar(self, targetObjVarName);
                }
                else 
                {
                    templateName = quests.getDataEntry(questRow, "TARGET");
                }
                LOG("newquests", "give_object_to_player - questName = " + questName + " templateName = " + templateName + " attachedScriptName = " + attachedScriptName);
                boolean success = true;
                obj_id createdObject = createObjectInInventoryAllowOverload(templateName, self);
                if (createdObject != null)
                {
                    if ((attachedScriptName != null) && !attachedScriptName.equals(""))
                    {
                        attachScript(createdObject, attachedScriptName);
                    }
                }
                else 
                {
                    CustomerServiceLog("fs_quests", "give_object_to_player: failed to create " + templateName + " in inventory for %TU ", self, null);
                    success = false;
                }
                LOG("newquests", "give_object_to_player - success = " + success);
                quests.complete(questName, self, success);
            }
            else 
            {
                CustomerServiceLog("fs_quests", "give_object_to_player: failed to retrieve quest name for quest id" + questRow + " for %TU ", self, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
