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

public class random_encounter extends script.base_script
{
    public random_encounter()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "random_encounter - OnQuestActivated(+ " + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.random_encounter"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            int successChanceInt = 0;
            String objvarname = "quest." + questName + ".parameter";
            if (hasObjVar(self, objvarname))
            {
                successChanceInt = getIntObjVar(self, objvarname);
            }
            else 
            {
                String successChance = quests.getDataEntry(questRow, "PARAMETER");
                successChanceInt = utils.stringToInt(successChance);
            }
            int dieRoll = rand(0, 99);
            boolean success = true;
            LOG("newquests", "random_encounter - questName = " + questName + " successChance = " + successChanceInt + " dieRoll = " + dieRoll);
            if (dieRoll < successChanceInt)
            {
                success = quests.doEncounterSpawn(self, questRow);
            }
            LOG("newquests", "random_encounter - success = " + success);
            quests.complete(questName, self, success);
        }
        return SCRIPT_CONTINUE;
    }
}
