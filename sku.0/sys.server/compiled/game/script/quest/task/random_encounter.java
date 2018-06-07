package script.quest.task;

import script.library.quests;
import script.library.utils;
import script.obj_id;

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
