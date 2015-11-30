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

public class encounter extends script.base_script
{
    public encounter()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "encounter - OnQuestActivated(+ " + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.encounter"))
        {
            boolean success = quests.doEncounterSpawn(self, questRow);
            String questName = quests.getDataEntry(questRow, "NAME");
            quests.complete(questName, self, success);
        }
        return SCRIPT_CONTINUE;
    }
}
