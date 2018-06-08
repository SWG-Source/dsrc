package script.quest.task;

import script.library.quests;
import script.obj_id;

public class nothing extends script.base_script
{
    public nothing()
    {
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "nothing - OnQuestActivated(+ " + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.nothing"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            LOG("newquests", "task nothing is completing now");
            quests.complete(questName, self, true);
        }
        return SCRIPT_CONTINUE;
    }
}
