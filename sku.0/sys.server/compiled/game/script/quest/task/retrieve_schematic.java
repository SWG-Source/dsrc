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

public class retrieve_schematic extends script.base_script
{
    public retrieve_schematic()
    {
    }
    public int OnGrantedSchematic(obj_id self, int schematicCrc, boolean fromSkill) throws InterruptedException
    {
        String[] activeTasks = quests.getActiveQuestsWithScript("quest.task.retrieve_schematic", self);
        int i;
        for (i = 0; i < activeTasks.length; ++i)
        {
            LOG("newquests", "checking " + activeTasks[i]);
            boolean taskComplete = false;
            if (hasObjVar(self, "quest." + activeTasks[i] + ".target_object_template"))
            {
                String targetObjectTemplate = getStringObjVar(self, "quest." + activeTasks[i] + ".target_object_template");
                LOG("newquests", "the TARGET column is overidden by target_object_template(" + targetObjectTemplate + ")");
                if (schematicCrc == getStringCrc(targetObjectTemplate))
                {
                    LOG("newquests", "this schematic(" + schematicCrc + ") is this retrieve_schematic task");
                    taskComplete = true;
                }
            }
            else 
            {
                String targetObjectTemplate = quests.getDataEntry(activeTasks[i], "TARGET");
                int targetObjectCrc = getStringCrc(targetObjectTemplate);
                LOG("newquests", "the data table for " + activeTasks[i] + " specifies the schematic " + targetObjectCrc + "(" + targetObjectTemplate + ") to complete this task. The player has received " + schematicCrc);
                if (schematicCrc == targetObjectCrc)
                {
                    LOG("newquests", "this schematic(" + schematicCrc + ") is this retrieve_schematic task");
                    taskComplete = true;
                }
            }
            if (taskComplete)
            {
                LOG("newquests", "This retrieve_schematic quest completing the task");
                quests.complete(activeTasks[i], self, true);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
